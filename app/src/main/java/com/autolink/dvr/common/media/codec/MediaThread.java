package com.autolink.dvr.common.media.codec;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaMuxer;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.autolink.adaptermanager.hardkey.KeyCodeExt;
import com.autolink.dvr.common.can.CanOperation;
import com.autolink.dvr.common.media.MediaCodecConstant;
import com.autolink.dvr.common.media.utils.FileUtils;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.SaveMsg;
import com.autolink.dvr.common.utils.USBUtil;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/* loaded from: classes.dex */
public class MediaThread extends Thread {
    private final String TAG;
    private long emergencyEventTime;
    private boolean isEmergencyEvent;
    private volatile boolean isFinish;
    private volatile boolean isStart;
    private boolean isTimeUp;
    private MediaCodec.BufferInfo keyBufferInfo;
    private long lastPTS;
    private long lastTime;
    private MediaMuxerChangeListener listener;
    private Context mContext;
    private int mExceptionTimes;
    private VideoQueueListener mListener;
    private String mSavedFilePath;
    private MediaMuxer mediaMuxer;
    private String path;
    private ByteBuffer ppsByteBuffer;
    private int saveTime;
    private ByteBuffer spsByteBuffer;
    private MediaCodec.BufferInfo videoBufferInfo;
    private List<MuxerData> videoCachelist;
    private MediaCodec videoCodec;
    private ArrayBlockingQueue<MuxerData> videoQueue;

    public MediaThread(String str, Context context, MediaCodec mediaCodec, MediaCodec mediaCodec2, MediaMuxerChangeListener mediaMuxerChangeListener) {
        String str2 = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();
        this.TAG = str2;
        this.lastTime = -1L;
        this.lastPTS = 0L;
        this.saveTime = 180000000;
        this.emergencyEventTime = -1L;
        this.mExceptionTimes = 0;
        this.mSavedFilePath = null;
        LogUtils2.logE(str2, "MediaThread");
        this.path = str;
        this.mContext = context;
        this.videoCodec = mediaCodec;
        this.listener = mediaMuxerChangeListener;
        this.videoQueue = new ArrayBlockingQueue<>(KeyCodeExt.KEYCODE_AL_R_LEFT);
        this.videoBufferInfo = new MediaCodec.BufferInfo();
        this.videoCachelist = Collections.synchronizedList(new ArrayList());
        this.keyBufferInfo = new MediaCodec.BufferInfo();
    }

    public void setListener(VideoQueueListener videoQueueListener) {
        this.mListener = videoQueueListener;
    }

    public ArrayBlockingQueue<MuxerData> getVideoQueue() {
        return this.videoQueue;
    }

    public List<MuxerData> getVideoCacheList() {
        return this.videoCachelist;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        VideoQueueListener videoQueueListener;
        super.run();
        LogUtils2.logE(this.TAG, "run() tid : " + Thread.currentThread().getId());
        this.isStart = true;
        if (!initMediaMuxer()) {
            this.mExceptionTimes++;
        }
        while (true) {
            if (this.mExceptionTimes > 100) {
                LogUtils2.logE(this.TAG, "thread error, exit");
                Process.killProcess(Process.myPid());
            }
            if (this.videoQueue.size() >= 1980) {
                try {
                    this.videoQueue.take();
                    if (CanOperation.isNeedToast && (videoQueueListener = this.mListener) != null) {
                        videoQueueListener.toast();
                        CanOperation.isNeedToast = false;
                    }
                    LogUtils2.logE(this.TAG, "videoQueue is full,  mExceptionTimes= " + this.mExceptionTimes + " ,videoQueue.size= " + this.videoQueue.size());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (!this.isStart) {
                LogUtils2.logE(this.TAG, "Stop, MediaThread ");
                stopMediaMuxer();
                this.lastTime = -1L;
                if (this.isFinish) {
                    LogUtils2.logE(this.TAG, "Stop, MediaThread Finish ");
                    return;
                }
                synchronized (this) {
                    try {
                        try {
                            LogUtils2.logE(this.TAG, "Stop, MediaThread wait ");
                            this.videoQueue.clear();
                            wait();
                            if (this.isFinish) {
                                LogUtils2.logE(this.TAG, "Stop, MediaThread Finish ");
                                return;
                            }
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                    } finally {
                    }
                }
            } else {
                if (this.videoCodec == null) {
                    LogUtils2.logE(this.TAG, "videoCodec or videoCodec == null ");
                    return;
                }
                long j = this.lastTime;
                if (j == -1) {
                    this.lastTime = this.lastPTS;
                    LogUtils2.logTrace(this.TAG, "run:0 time= " + this.lastTime);
                } else {
                    long j2 = this.lastPTS;
                    if (j2 - j >= this.saveTime) {
                        this.lastTime = j2;
                        LogUtils2.logTrace(this.TAG, "run:1 time= " + this.lastTime);
                        this.isTimeUp = true;
                    }
                }
                if (this.lastPTS - this.emergencyEventTime >= FileUtils.cacheTime && this.isEmergencyEvent && this.emergencyEventTime != -1) {
                    long j3 = this.lastPTS;
                    this.lastTime = j3;
                    this.emergencyEventTime = j3;
                    LogUtils2.logTrace(this.TAG, "run:2 time= " + this.lastTime);
                    this.isTimeUp = true;
                }
                if (!MediaCodecConstant.videoStart) {
                    LogUtils2.logTrace(this.TAG, "wait addTrack. videoStart = " + MediaCodecConstant.videoStart + " ,audioStart = " + MediaCodecConstant.audioStart);
                    SystemClock.sleep(10L);
                } else {
                    if (MediaCodecConstant.mediaMuxerState != 2) {
                        LogUtils2.logE(this.TAG, "to startMediaMuxer. mediaMuxerState = " + MediaCodecConstant.mediaMuxerState);
                        if (!startMediaMuxer()) {
                            this.mExceptionTimes++;
                        }
                    }
                    if (MediaCodecConstant.mediaMuxerState == 2) {
                        if (this.isEmergencyEvent && this.emergencyEventTime == -1) {
                            LogUtils2.logE(this.TAG, "handle EmergencyEvent");
                            stopMediaMuxer();
                            if (!initMediaMuxer()) {
                                this.mExceptionTimes++;
                            }
                            if (!startMediaMuxer()) {
                                this.mExceptionTimes++;
                            }
                            LogUtils2.logTrace(this.TAG, "handle EmergencyEvent keyBufferInfo = " + this.keyBufferInfo.presentationTimeUs);
                            this.mediaMuxer.writeSampleData(MediaCodecConstant.videoTrackIndex, this.spsByteBuffer, this.keyBufferInfo);
                            this.mediaMuxer.writeSampleData(MediaCodecConstant.videoTrackIndex, this.ppsByteBuffer, this.keyBufferInfo);
                            LogUtils2.logTrace(this.TAG, "handle EmergencyEvent videoList = " + this.videoCachelist.size());
                            for (int i = 0; i < this.videoCachelist.size(); i++) {
                                writeData(this.videoCachelist.get(i));
                            }
                            LogUtils2.logTrace(this.TAG, "handle EmergencyEvent lastPTS = " + this.lastPTS);
                            long j4 = this.lastPTS;
                            this.lastTime = j4;
                            this.emergencyEventTime = j4;
                        }
                        if (this.videoQueue.size() > 0) {
                            LogUtils2.logTrace(this.TAG, "videoQueue.size = " + this.videoQueue.size());
                            try {
                                MuxerData take = this.videoQueue.take();
                                this.videoBufferInfo = take.bufferInfo;
                                LogUtils2.logTrace(this.TAG, "video presentationTimeUs = " + take.bufferInfo.presentationTimeUs);
                                LogUtils2.logTrace(this.TAG, "video isTimeUp = " + this.isTimeUp + " , flags = " + this.videoBufferInfo.flags);
                                this.lastPTS = this.videoBufferInfo.presentationTimeUs;
                                LogUtils2.logTrace(this.TAG, "video1 muxerData.bufferInfo = " + take.bufferInfo.toString() + " ,offset = " + take.bufferInfo.offset + " , size = " + take.bufferInfo.size + " ,presentationTimeUs = " + take.bufferInfo.presentationTimeUs + " ,muxerData = " + take.hashCode() + " , byteBuf  = " + take.byteBuf.toString());
                                ByteBuffer allocate = ByteBuffer.allocate(take.byteBuf.limit());
                                allocate.put(take.byteBuf);
                                allocate.rewind();
                                new MuxerData(0, allocate, take.bufferInfo);
                                take.byteBuf.rewind();
                                if (this.spsByteBuffer == null) {
                                    this.spsByteBuffer = this.videoCodec.getOutputFormat().getByteBuffer("csd-0");
                                    this.keyBufferInfo.flags = 2;
                                    String charBuffer = Charset.forName("utf-8").decode(this.spsByteBuffer).toString();
                                    LogUtils2.logTrace(this.TAG, "sps = " + this.spsByteBuffer);
                                    LogUtils2.logTrace(this.TAG, "sps = " + charBuffer);
                                }
                                if (this.ppsByteBuffer == null) {
                                    this.ppsByteBuffer = this.videoCodec.getOutputFormat().getByteBuffer("csd-1");
                                    String charBuffer2 = StandardCharsets.UTF_8.decode(this.ppsByteBuffer).toString();
                                    LogUtils2.logTrace(this.TAG, "pps = " + this.ppsByteBuffer);
                                    LogUtils2.logTrace(this.TAG, "pps = " + charBuffer2);
                                }
                                if (this.isTimeUp && (this.videoBufferInfo.flags & 1) != 0) {
                                    LogUtils2.logTrace(this.TAG, "video1 mediaMuxerState = " + MediaCodecConstant.mediaMuxerState);
                                    this.emergencyEventTime = -1L;
                                    this.isEmergencyEvent = false;
                                    CanOperation.isHaveEmergencyEvent = false;
                                    ByteBuffer allocate2 = ByteBuffer.allocate(take.byteBuf.limit());
                                    allocate2.put(take.byteBuf);
                                    allocate2.rewind();
                                    take.byteBuf.rewind();
                                    MuxerData muxerData = new MuxerData(0, allocate2, take.bufferInfo);
                                    writeData(take);
                                    LogUtils2.logI(this.TAG, "video2 muxerData1.bufferInfo = " + muxerData.bufferInfo.toString() + " ,offset = " + muxerData.bufferInfo.offset + " , size = " + muxerData.bufferInfo.size + " ,presentationTimeUs = " + muxerData.bufferInfo.presentationTimeUs + " ,muxerData = " + muxerData.hashCode() + " , byteBuf  = " + muxerData.byteBuf.toString());
                                    this.isTimeUp = false;
                                    this.lastTime = this.lastPTS;
                                    stopMediaMuxer();
                                    String str = this.TAG;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("video2 mediaMuxerState = ");
                                    sb.append(MediaCodecConstant.mediaMuxerState);
                                    LogUtils2.logTrace(str, sb.toString());
                                    if (!initMediaMuxer()) {
                                        this.mExceptionTimes++;
                                    }
                                    if (!startMediaMuxer()) {
                                        this.mExceptionTimes++;
                                    }
                                    LogUtils2.logTrace(this.TAG, "video3 mediaMuxerState = " + MediaCodecConstant.mediaMuxerState);
                                    if (this.videoQueue.size() < 1980) {
                                        writeData(muxerData);
                                    }
                                } else {
                                    LogUtils2.logTrace(this.TAG, "video5 mediaMuxerState = " + MediaCodecConstant.mediaMuxerState);
                                    LogUtils2.logTrace(this.TAG, "video5 position = " + take.byteBuf.position() + " ,limit = " + take.byteBuf.limit() + " ,bufferInfo=" + take.bufferInfo.toString());
                                    writeData(take);
                                    LogUtils2.logTrace(this.TAG, "video6  position = " + take.byteBuf.position() + " ,limit = " + take.byteBuf.limit() + " ,bufferInfo=" + take.bufferInfo.toString());
                                }
                            } catch (InterruptedException e3) {
                                LogUtils2.logE(this.TAG, "videoQueue InterruptedException = " + e3.toString());
                            }
                        }
                        if (this.videoQueue.size() < 50) {
                            SystemClock.sleep(20L);
                        }
                    } else {
                        LogUtils2.logE(this.TAG, "MediaCodecConstant.mediaMuxerState  = " + MediaCodecConstant.mediaMuxerState);
                    }
                }
            }
        }
    }

    private void writeData(MuxerData muxerData) {
        LogUtils2.logTrace(this.TAG, "writeData, data = " + MediaCodecConstant.mediaMuxerState + " ,data.trackIndex = " + muxerData.trackIndex);
        if (muxerData.trackIndex == 0) {
            try {
                this.mediaMuxer.writeSampleData(MediaCodecConstant.videoTrackIndex, muxerData.byteBuf, muxerData.bufferInfo);
                return;
            } catch (Exception e) {
                Log.e(this.TAG, "writeSampleData video Exception =" + e);
                return;
            }
        }
        try {
            this.mediaMuxer.writeSampleData(MediaCodecConstant.audioTrackIndex, muxerData.byteBuf, muxerData.bufferInfo);
        } catch (Exception e2) {
            Log.e(this.TAG, "writeSampleData audio Exception =" + e2);
        }
    }

    private boolean initMediaMuxer() {
        String currentTimeToString;
        String str = USBUtil.normal;
        this.saveTime = FileUtils.getSaveTime();
        try {
            if (USBUtil.USBPath != null && SaveMsg.isMountedUSB) {
                this.path = USBUtil.USBPath;
            } else {
                this.path = FileUtils.getExternalPath(this.mContext);
            }
            File file = new File(this.path + File.separator + "event");
            if (!file.exists()) {
                LogUtils2.logI(this.TAG, "mkdir event");
                file.mkdir();
            }
            File file2 = new File(this.path + File.separator + USBUtil.normal);
            if (!file2.exists()) {
                LogUtils2.logI(this.TAG, "mkdir normal");
                file2.mkdir();
            }
            if (this.isEmergencyEvent) {
                currentTimeToString = FileUtils.getCurrentTimeToString2();
                this.mSavedFilePath = this.path + File.separator + "event" + File.separator + currentTimeToString;
                this.mediaMuxer = new MediaMuxer(this.mSavedFilePath, 0);
            } else {
                currentTimeToString = FileUtils.getCurrentTimeToString();
                this.mSavedFilePath = this.path + File.separator + USBUtil.normal + File.separator + currentTimeToString;
                this.mediaMuxer = new MediaMuxer(this.mSavedFilePath, 0);
            }
            FileUtils.recordVideoFileName = currentTimeToString;
            LogUtils2.logE(this.TAG, "initMediaMuxer: saveTime = " + this.saveTime + " ,name = " + currentTimeToString);
            MediaMuxerChangeListener mediaMuxerChangeListener = this.listener;
            if (this.isEmergencyEvent) {
                str = "event";
            }
            mediaMuxerChangeListener.onMediaMuxerChangeListener(4, str);
            return true;
        } catch (IOException e) {
            this.listener.onMediaMuxerChangeListener(3);
            this.listener.onMediaMuxerChangeListener2(3);
            LogUtils2.logE(this.TAG, "initMediaMuxer: 文件打开失败,e=" + e.toString());
            LogUtils2.logE(this.TAG, "initMediaMuxer: 文件打开失败,path=" + this.path);
            return false;
        }
    }

    private boolean startMediaMuxer() {
        LogUtils2.logE(this.TAG, "startMediaMuxer , name = " + FileUtils.recordVideoFileName + " mediaMuxerState = " + MediaCodecConstant.mediaMuxerState);
        if (this.mediaMuxer == null) {
            LogUtils2.logE(this.TAG, "startMediaMuxer, mediaMuxer is null");
            if (!initMediaMuxer()) {
                this.mExceptionTimes++;
            }
        }
        try {
            MediaCodecConstant.mediaMuxerState = 1;
            MediaCodecConstant.videoTrackIndex = this.mediaMuxer.addTrack(this.videoCodec.getOutputFormat());
            this.mediaMuxer.start();
            MediaCodecConstant.mediaMuxerState = 2;
            LogUtils2.logE(this.TAG, "startMediaMuxer , mediaMuxerState = " + MediaCodecConstant.mediaMuxerState);
            this.listener.onMediaMuxerChangeListener(1);
            return true;
        } catch (Exception e) {
            this.listener.onMediaMuxerChangeListener(3);
            LogUtils2.logE(this.TAG, "startMediaMuxer Exception = " + e.toString());
            return false;
        }
    }

    private void stopMediaMuxer() {
        LogUtils2.logE(this.TAG, "stopMediaMuxer , name = " + FileUtils.recordVideoFileName + " mediaMuxerState = " + MediaCodecConstant.mediaMuxerState);
        try {
            if (this.mediaMuxer != null) {
                MediaCodecConstant.mediaMuxerState = 3;
                this.mediaMuxer.stop();
                this.mediaMuxer.release();
                this.mediaMuxer = null;
            }
        } catch (Exception e) {
            this.mediaMuxer.release();
            this.mediaMuxer = null;
            LogUtils2.logE(this.TAG, "stopMediaMuxer e = " + e.toString());
        }
        MediaCodecConstant.mediaMuxerState = 4;
        LogUtils2.logE(this.TAG, "stopMediaMuxer , mediaMuxerState = " + MediaCodecConstant.mediaMuxerState);
        this.listener.onMediaMuxerChangeListener(2, this.mSavedFilePath);
    }

    public void toNotify() {
        LogUtils2.logE(this.TAG, "toNotify ");
        synchronized (this) {
            LogUtils2.logE(this.TAG, "notify ");
            notify();
        }
    }

    public void startMediaThread() {
        LogUtils2.logE(this.TAG, "startMediaThread ");
        this.isStart = true;
        this.lastPTS = MediaCodecConstant.reStartRecordPTS;
        if (!initMediaMuxer()) {
            this.mExceptionTimes++;
        }
        toNotify();
    }

    public void stopMediaThread() {
        LogUtils2.logE(this.TAG, "stopMediaThread ");
        this.isStart = false;
    }

    public void finishMediaThread() {
        LogUtils2.logE(this.TAG, "finishMediaThread ");
        this.isFinish = true;
        this.isStart = false;
        toNotify();
    }

    public void startEmergencyRecord() {
        LogUtils2.logE(this.TAG, "startEmergencyRecord ");
        if (this.isEmergencyEvent) {
            LogUtils2.logE(this.TAG, "startEmergencyRecord lastPTS = " + this.lastPTS);
            this.emergencyEventTime = this.lastPTS;
        }
        this.isEmergencyEvent = true;
    }
}
