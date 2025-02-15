package com.autolink.dvr.common.media.codec;

import android.media.MediaCodec;
import android.os.SystemClock;
import com.autolink.dvr.common.media.MediaCodecConstant;
import com.autolink.dvr.common.utils.LogUtils2;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;

/* loaded from: classes.dex */
public class AudioThread extends Thread {
    private final String TAG;
    private MediaCodec audioCodec;
    private ArrayBlockingQueue<MuxerData> audioQueue;
    private boolean isClickStrop;
    private boolean isFinish;
    private boolean isStart;

    public AudioThread(MediaCodec mediaCodec, ArrayBlockingQueue<MuxerData> arrayBlockingQueue) {
        String str = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();
        this.TAG = str;
        LogUtils2.logI(str, "AudioThread ");
        this.audioCodec = mediaCodec;
        this.audioQueue = arrayBlockingQueue;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        super.run();
        LogUtils2.logI(this.TAG, "run()");
        this.isStart = true;
        while (true) {
            if (!this.isStart) {
                if (this.isFinish) {
                    LogUtils2.logI(this.TAG, "Stop, AudioThread Finish ");
                    return;
                }
                synchronized (this) {
                    try {
                        LogUtils2.logI(this.TAG, "Stop, AudioThread wait ");
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (this.audioCodec == null) {
                    LogUtils2.logI(this.TAG, "audioCodec == null ");
                    return;
                }
                MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                int dequeueOutputBuffer = this.audioCodec.dequeueOutputBuffer(bufferInfo, 0L);
                if (dequeueOutputBuffer < 0) {
                    if (dequeueOutputBuffer == -2) {
                        LogUtils2.logI(this.TAG, "run1: outputBufferIndex=" + dequeueOutputBuffer);
                        MediaCodecConstant.audioStart = true;
                    } else {
                        LogUtils2.logD(this.TAG, "run2: Index = " + dequeueOutputBuffer + " ,isClickStrop = " + this.isClickStrop);
                        SystemClock.sleep(10L);
                    }
                } else if (dequeueOutputBuffer >= 0 && this.isStart) {
                    ByteBuffer byteBuffer = this.audioCodec.getOutputBuffers()[dequeueOutputBuffer];
                    if (!this.isClickStrop) {
                        byteBuffer.position(bufferInfo.offset);
                        byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
                        bufferInfo.presentationTimeUs -= MediaEncodeManager.lastDequeuedPresentationTimeStampUs;
                        ByteBuffer allocate = ByteBuffer.allocate(byteBuffer.limit());
                        byteBuffer.rewind();
                        allocate.put(byteBuffer);
                        byteBuffer.rewind();
                        allocate.rewind();
                        MuxerData muxerData = new MuxerData(1, allocate, bufferInfo);
                        LogUtils2.logD(this.TAG, "音频秒数时间戳2 = " + (bufferInfo.presentationTimeUs / 1000000.0f) + " ,outputBuffer = " + byteBuffer.toString() + " , muxerData = " + muxerData.hashCode() + " ,Buffer hashCode = " + allocate.hashCode() + " , " + allocate.toString() + " , bufferInfo = " + bufferInfo.toString() + " ,offset = " + bufferInfo.offset + " ,size = " + bufferInfo.size + " ,presentationTimeUs = " + bufferInfo.presentationTimeUs);
                        try {
                            this.audioQueue.put(muxerData);
                        } catch (InterruptedException e2) {
                            LogUtils2.logE(this.TAG, "audioQueue InterruptedException = " + e2.toString());
                        }
                    }
                    this.audioCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                    LogUtils2.logD(this.TAG, "ClickStop sleep");
                    SystemClock.sleep(20L);
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

    public void startAudioThread() {
        LogUtils2.logI(this.TAG, "startAudioThread ");
        this.isClickStrop = false;
    }

    public void stopAudioThread() {
        LogUtils2.logI(this.TAG, "stopAudioThread ");
        this.isClickStrop = true;
    }

    public void finishAudioThread() {
        LogUtils2.logI(this.TAG, "finishAudioThread ");
        this.isFinish = true;
        this.isStart = false;
        MediaCodecConstant.audioStart = false;
    }
}
