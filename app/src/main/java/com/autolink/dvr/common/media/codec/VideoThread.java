package com.autolink.dvr.common.media.codec;

import android.media.MediaCodec;
import android.os.SystemClock;
import com.autolink.dvr.common.can.CanOperation;
import com.autolink.dvr.common.media.MediaCodecConstant;
import com.autolink.dvr.common.media.utils.FileUtils;
import com.autolink.dvr.common.utils.LogUtils2;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/* loaded from: classes.dex */
public class VideoThread extends Thread {
    private final String TAG;
    private boolean isClickStrop;
    private boolean isFinish;
    private boolean isStart;
    private VideoQueueListener mListener;
    private long pts;
    private List<MuxerData> videoCachelist;
    private MediaCodec videoCodec;
    private ArrayBlockingQueue<MuxerData> videoQueue;

    public VideoThread(MediaCodec mediaCodec, ArrayBlockingQueue<MuxerData> arrayBlockingQueue, List<MuxerData> list) {
        String str = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();
        this.TAG = str;
        LogUtils2.logI(str, "VideoThread");
        this.videoCodec = mediaCodec;
        this.videoQueue = arrayBlockingQueue;
        this.videoCachelist = list;
        this.pts = 0L;
    }

    public void setListener(VideoQueueListener videoQueueListener) {
        this.mListener = videoQueueListener;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        VideoQueueListener videoQueueListener;
        super.run();
        LogUtils2.logI(this.TAG, "run() tid : " + Thread.currentThread().getId());
        this.isStart = true;
        while (true) {
            if (!this.isStart) {
                if (this.isFinish) {
                    LogUtils2.logI(this.TAG, "Stop, VideoThread Finish ");
                    return;
                }
                synchronized (this) {
                    try {
                        LogUtils2.logI(this.TAG, "Stop, VideoThread wait ");
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (this.videoCodec == null) {
                    LogUtils2.logI(this.TAG, "videoCodec == null ");
                    return;
                }
                MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                int dequeueOutputBuffer = this.videoCodec.dequeueOutputBuffer(bufferInfo, 0L);
                if (dequeueOutputBuffer < 0) {
                    if (dequeueOutputBuffer == -2) {
                        LogUtils2.logI(this.TAG, "run1: outputBufferIndex=" + dequeueOutputBuffer);
                        MediaCodecConstant.videoStart = true;
                    } else {
                        LogUtils2.logTrace(this.TAG, "run2: Index = " + dequeueOutputBuffer + " ,isClickStrop = " + this.isClickStrop);
                        SystemClock.sleep(30L);
                    }
                } else if (dequeueOutputBuffer >= 0 && this.isStart) {
                    ByteBuffer byteBuffer = this.videoCodec.getOutputBuffers()[dequeueOutputBuffer];
                    byteBuffer.position(bufferInfo.offset);
                    byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
                    if (this.pts == 0) {
                        this.pts = bufferInfo.presentationTimeUs;
                    }
                    bufferInfo.presentationTimeUs -= this.pts;
                    MediaCodecConstant.reStartRecordPTS = bufferInfo.presentationTimeUs;
                    ByteBuffer allocate = ByteBuffer.allocate(byteBuffer.limit());
                    byteBuffer.rewind();
                    allocate.put(byteBuffer);
                    byteBuffer.rewind();
                    allocate.rewind();
                    MuxerData muxerData = new MuxerData(0, allocate, bufferInfo);
                    LogUtils2.logTrace(this.TAG, "视频秒数时间戳2 = " + (bufferInfo.presentationTimeUs / 1000000.0f) + " ,outputBuffer = " + byteBuffer.toString() + " , muxerData = " + muxerData.hashCode() + " ,Buffer hashCode = " + allocate.hashCode() + " , " + allocate.toString() + " , bufferInfo = " + bufferInfo.toString() + " ,offset = " + bufferInfo.offset + " ,size = " + bufferInfo.size + " ,presentationTimeUs = " + bufferInfo.presentationTimeUs);
                    this.videoCachelist.add(muxerData);
                    if (bufferInfo.presentationTimeUs - this.videoCachelist.get(0).bufferInfo.presentationTimeUs >= FileUtils.cacheTime) {
                        this.videoCachelist.remove(0);
                        LogUtils2.logTrace(this.TAG, "video videoList remove 0. size = " + this.videoCachelist.size());
                    }
                    if (!this.isClickStrop) {
                        try {
                            this.videoQueue.put(muxerData);
                            if (this.videoQueue.size() > 1990) {
                                this.videoQueue.take();
                                if (CanOperation.isNeedToast && (videoQueueListener = this.mListener) != null) {
                                    videoQueueListener.toast();
                                    CanOperation.isNeedToast = false;
                                }
                                LogUtils2.logE(this.TAG, "videoQueue is full , mediaMuxerState = " + MediaCodecConstant.mediaMuxerState);
                            }
                        } catch (InterruptedException e2) {
                            LogUtils2.logE(this.TAG, "videoQueue InterruptedException = " + e2.toString());
                        }
                    }
                    this.videoCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                    LogUtils2.logTrace(this.TAG, "ClickStop sleep");
                    SystemClock.sleep(30L);
                }
            }
        }
    }

    public void toNotify() {
        LogUtils2.logI(this.TAG, "toNotify ");
        synchronized (this) {
            LogUtils2.logI(this.TAG, "notify ");
            notify();
        }
    }

    public void startVideoThread() {
        LogUtils2.logI(this.TAG, "startVideoThread ");
        this.isClickStrop = false;
    }

    public void stopVideoThread() {
        LogUtils2.logI(this.TAG, "stopVideoThread ");
        this.isClickStrop = true;
    }

    public void finishVideoThread() {
        LogUtils2.logI(this.TAG, "finishVideoThread ");
        this.isFinish = true;
        this.isStart = false;
        MediaCodecConstant.videoStart = false;
    }
}
