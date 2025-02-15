package com.autolink.dvr.common.media.codec;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Surface;
import com.autolink.dvr.common.media.MediaCodecConstant;
import com.autolink.dvr.common.media.surface.EglSurfaceView;
import com.autolink.dvr.common.media.utils.DisplayUtils;
import com.autolink.dvr.common.media.utils.FileUtils;
import com.autolink.dvr.common.media.utils.MP4Utils;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.USBUtil;
import com.google.android.gms.common.Scopes;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes.dex */
public class MediaEncodeManager implements MediaMuxerChangeListener, VideoQueueListener {
    public static long lastDequeuedPresentationTimeStampUs;
    public static long lastQueuedPresentationTimeStampUs;
    private static String path;
    private final String TAG;
    private MediaCodec audioCodec;
    private int audioFormat;
    private String audioType;
    private Handler calcDecibel;
    private int channelCount;
    private EglSurfaceView.Render eglSurfaceRender;
    private EglRenderThread eglThread;
    private int height;
    private boolean isStartRecord;
    private Context mContext;
    private Handler mFileHandler;
    private Handler mHandler;
    private ExecutorService mMediaExecutor;
    private ExecutorService mVideoExecutor;
    private MediaThread mediaThread;
    private long presentationTimeUs;
    private int sampleRate;
    private Surface surface;
    private MediaCodec videoCodec;
    private VideoThread videoThread;
    private String videoType;
    private int width;

    @Override // com.autolink.dvr.common.media.codec.MediaMuxerChangeListener
    public void onMediaInfoListener(int i) {
    }

    private static class MediaEncodeManagerHolder {
        private static MediaEncodeManager INSTANCE = new MediaEncodeManager();

        private MediaEncodeManagerHolder() {
        }
    }

    public static final MediaEncodeManager getInstance() {
        return MediaEncodeManagerHolder.INSTANCE;
    }

    private MediaEncodeManager() {
        this.TAG = "DVR_MediaEncodeManager";
        this.isStartRecord = false;
        ThreadFactory build = new ThreadFactoryBuilder().setNameFormat("mediaThread").build();
        this.mVideoExecutor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("videoThread").build());
        this.mMediaExecutor = Executors.newSingleThreadExecutor(build);
        HandlerThread handlerThread = new HandlerThread("VideoFileHandlerThread");
        handlerThread.start();
        this.mFileHandler = new Handler(handlerThread.getLooper());
    }

    public void initMediaCodec(Context context, int i, int i2) {
        this.audioType = "audio/mp4a-latm";
        this.videoType = "video/avc";
        this.sampleRate = 44100;
        this.channelCount = 2;
        this.audioFormat = 16;
        path = FileUtils.getExternalPath(context);
        this.mContext = context;
        initVideoCodec(this.videoType, i, i2);
        this.width = i;
        this.height = i2;
    }

    public void setEglSurfaceRender(EglSurfaceView.Render render) {
        this.eglSurfaceRender = render;
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    public void initEglThread(int i) {
        EglRenderThread eglRenderThread = new EglRenderThread(this.surface, this.eglSurfaceRender, i, this.width, this.height);
        this.eglThread = eglRenderThread;
        eglRenderThread.setName("eglThread");
    }

    public void initCodecThread() {
        MediaThread mediaThread = new MediaThread(path, this.mContext, this.videoCodec, this.audioCodec, this);
        this.mediaThread = mediaThread;
        mediaThread.setName("mediaThread");
        this.mediaThread.setListener(this);
        VideoThread videoThread = new VideoThread(this.videoCodec, this.mediaThread.getVideoQueue(), this.mediaThread.getVideoCacheList());
        this.videoThread = videoThread;
        videoThread.setName("videoThread");
        this.videoThread.setListener(this);
    }

    private void initVideoCodec(String str, int i, int i2) {
        try {
            this.videoCodec = MediaCodec.createEncoderByType(str);
            MediaFormat createVideoFormat = MediaFormat.createVideoFormat(str, i, i2);
            createVideoFormat.setInteger("color-format", 2130708361);
            createVideoFormat.setInteger("frame-rate", 15);
            createVideoFormat.setInteger("bitrate", i * i2 * 2);
            createVideoFormat.setInteger("i-frame-interval", 1);
            if (Build.VERSION.SDK_INT >= 24) {
                createVideoFormat.setInteger(Scopes.PROFILE, 8);
                createVideoFormat.setInteger("level", 512);
            }
            this.videoCodec.configure(createVideoFormat, (Surface) null, (MediaCrypto) null, 1);
            this.surface = this.videoCodec.createInputSurface();
        } catch (IOException unused) {
            LogUtils2.logE("DVR_MediaEncodeManager", "initVideoCodec: 视频类型无效");
        }
    }

    public void startEncode(boolean z) {
        LogUtils2.logI("DVR_MediaEncodeManager", "startEncode: -----------------");
        if (this.surface == null) {
            LogUtils2.logI("DVR_MediaEncodeManager", "startEncode: createInputSurface创建失败");
            return;
        }
        this.isStartRecord = true;
        if (z) {
            this.videoCodec.start();
            this.eglThread.start();
            this.mMediaExecutor.execute(this.mediaThread);
            this.mVideoExecutor.execute(this.videoThread);
            MediaCodecConstant.surfaceCreate = true;
            MediaCodecConstant.surfaceChange = true;
            return;
        }
        this.mediaThread.startMediaThread();
        this.videoThread.startVideoThread();
    }

    public void stopEncode(boolean z) {
        LogUtils2.logI("DVR_MediaEncodeManager", "stopEncode: -----------------");
        this.isStartRecord = false;
        if (z) {
            VideoThread videoThread = this.videoThread;
            if (videoThread != null) {
                videoThread.finishVideoThread();
                this.videoThread = null;
            }
            MediaThread mediaThread = this.mediaThread;
            if (mediaThread != null) {
                mediaThread.finishMediaThread();
                this.mediaThread = null;
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MediaCodec mediaCodec = this.videoCodec;
            if (mediaCodec != null) {
                mediaCodec.stop();
                this.videoCodec.release();
                this.videoCodec = null;
            }
            EglRenderThread eglRenderThread = this.eglThread;
            if (eglRenderThread != null) {
                eglRenderThread.stopEglRender();
                this.eglThread = null;
            }
            MediaCodecConstant.surfaceCreate = false;
            MediaCodecConstant.surfaceChange = false;
            DisplayUtils.eglContext = null;
        } else {
            VideoThread videoThread2 = this.videoThread;
            if (videoThread2 != null) {
                videoThread2.stopVideoThread();
            }
            MediaThread mediaThread2 = this.mediaThread;
            if (mediaThread2 != null) {
                mediaThread2.stopMediaThread();
            }
        }
        lastQueuedPresentationTimeStampUs = 0L;
        lastDequeuedPresentationTimeStampUs = 0L;
    }

    public void startEmergency() {
        LogUtils2.logI("DVR_MediaEncodeManager", "startEmergency: -----------------");
        this.mediaThread.startEmergencyRecord();
    }

    @Override // com.autolink.dvr.common.media.codec.MediaMuxerChangeListener
    public void onMediaMuxerChangeListener(int i) {
        if (i == 1) {
            LogUtils2.logI("DVR_MediaEncodeManager", "onMediaMuxerChangeListener --- 视频录制开始了");
        } else if (i == 3) {
            Message message = new Message();
            message.what = 9;
            this.mHandler.sendMessage(message);
        }
    }

    @Override // com.autolink.dvr.common.media.codec.MediaMuxerChangeListener
    public void onMediaMuxerChangeListener(int i, final String str) {
        if (i == 2 && str != null) {
            this.mFileHandler.post(new Runnable() { // from class: com.autolink.dvr.common.media.codec.MediaEncodeManager.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (MP4Utils.isPlayable(str)) {
                            LogUtils2.logI("DVR_MediaEncodeManager", "onMediaMuxerChangeListener saveVideoThumbnail " + str);
                            File file = new File(str);
                            String replace = str.replace("tmp", "mp4");
                            file.renameTo(new File(replace));
                            FileUtils.saveVideoThumbnail(replace);
                        }
                    } catch (IOException e) {
                        LogUtils2.logE("DVR_MediaEncodeManager", "MUXER_STOP e= " + e.toString());
                    }
                }
            });
        } else if (i == 4) {
            this.mFileHandler.post(new Runnable() { // from class: com.autolink.dvr.common.media.codec.MediaEncodeManager.2
                @Override // java.lang.Runnable
                public void run() {
                    LogUtils2.logI("DVR_MediaEncodeManager", "onMediaMuxerChangeListener  type  MUXER_INIT " + str);
                    USBUtil.getUSBInfo(MediaEncodeManager.this.mContext);
                    if (USBUtil.normal.equals(str) && USBUtil.USBPath != null) {
                        long dirOrFileSize = USBUtil.getDirOrFileSize(USBUtil.USBPath + File.separator + USBUtil.normal);
                        long j = (long) (((double) USBUtil.totalSize) * 0.9d * 0.7d);
                        LogUtils2.logI("DVR_MediaEncodeManager", "percentage = " + USBUtil.percentage + " current size = " + dirOrFileSize + " limited size = " + j);
                        if (USBUtil.percentage < 10 || dirOrFileSize > j) {
                            USBUtil.deleteSomeVideo(USBUtil.USBPath + File.separator + USBUtil.normal, false);
                            return;
                        }
                        return;
                    }
                    if (!"event".equals(str) || USBUtil.USBPath == null) {
                        return;
                    }
                    long dirOrFileSize2 = USBUtil.getDirOrFileSize(USBUtil.USBPath + File.separator + "event");
                    long j2 = (long) (((double) USBUtil.totalSize) * 0.9d * 0.3d);
                    LogUtils2.logI("DVR_MediaEncodeManager", "percentage = " + USBUtil.percentage + " current size = " + dirOrFileSize2 + " limited size = " + j2);
                    if (USBUtil.percentage < 10 || dirOrFileSize2 > j2) {
                        USBUtil.deleteSomeVideo(USBUtil.USBPath + File.separator + "event", true);
                    }
                }
            });
        }
    }

    @Override // com.autolink.dvr.common.media.codec.MediaMuxerChangeListener
    public void onMediaMuxerChangeListener2(int i) {
        LogUtils2.logI("DVR_MediaEncodeManager", "onMediaMuxerChangeListener2 type =  " + i);
        if (i == 3) {
            if (this.mHandler.hasMessages(17)) {
                this.mHandler.removeMessages(17);
            }
            this.mHandler.sendEmptyMessage(17);
        }
    }

    @Override // com.autolink.dvr.common.media.codec.VideoQueueListener
    public void toast() {
        LogUtils2.logI("DVR_MediaEncodeManager", "send message to toast user change usb");
        if (this.mHandler.hasMessages(17)) {
            this.mHandler.removeMessages(17);
        }
        this.mHandler.sendEmptyMessage(17);
    }
}
