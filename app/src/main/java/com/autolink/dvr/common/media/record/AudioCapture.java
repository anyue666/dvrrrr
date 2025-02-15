package com.autolink.dvr.common.media.record;

import android.media.AudioRecord;
import android.os.SystemClock;
import com.autolink.dvr.common.utils.LogUtils2;

/* loaded from: classes.dex */
public class AudioCapture {
    private static final int AUDIO_FORMAT = 2;
    private static final int AUDIO_SOURCE = 1;
    private static final int CHANNEL_CONFIGS = 12;
    private static final int SAMPLE_RATE = 44100;
    private AudioRecord audioRecord;
    private AudioCaptureListener captureListener;
    private Thread threadCapture;
    private final String TAG = "DVR_AudioCapture";
    private int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, 12, 2);
    private boolean isStartRecord = false;
    private boolean isStopRecord = false;
    private boolean isDebug = true;

    public interface AudioCaptureListener {
        void onCaptureListener(byte[] bArr, int i);
    }

    public void start() {
        start(1, SAMPLE_RATE, 12, 2);
    }

    public void start(int i, int i2, int i3, int i4) {
        if (this.isStartRecord) {
            if (this.isDebug) {
                LogUtils2.logI("DVR_AudioCapture", "音频录制已经开启");
                return;
            }
            return;
        }
        int minBufferSize = AudioRecord.getMinBufferSize(i2, i3, i4);
        this.bufferSize = minBufferSize;
        if (minBufferSize == -2) {
            if (this.isDebug) {
                LogUtils2.logI("DVR_AudioCapture", "无效参数");
                return;
            }
            return;
        }
        if (this.isDebug) {
            LogUtils2.logI("DVR_AudioCapture", "bufferSize = ".concat(String.valueOf(minBufferSize)).concat("byte"));
        }
        AudioRecord audioRecord = new AudioRecord(1, i2, i3, i4, this.bufferSize);
        this.audioRecord = audioRecord;
        audioRecord.startRecording();
        this.isStopRecord = false;
        Thread thread = new Thread(new CaptureRunnable());
        this.threadCapture = thread;
        thread.setName("threadCapture");
        this.threadCapture.start();
        this.isStartRecord = true;
        if (this.isDebug) {
            LogUtils2.logI("DVR_AudioCapture", "音频录制开启...");
        }
    }

    public void stop() {
        if (this.isStartRecord) {
            this.isStopRecord = true;
            this.threadCapture.interrupt();
            this.audioRecord.stop();
            this.audioRecord.release();
            this.isStartRecord = false;
            this.captureListener = null;
        }
    }

    private class CaptureRunnable implements Runnable {
        private CaptureRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            while (!AudioCapture.this.isStopRecord) {
                byte[] bArr = new byte[AudioCapture.this.bufferSize];
                int read = AudioCapture.this.audioRecord.read(bArr, 0, AudioCapture.this.bufferSize);
                if (read > 0) {
                    if (AudioCapture.this.captureListener != null) {
                        AudioCapture.this.captureListener.onCaptureListener(bArr, read);
                    }
                    if (AudioCapture.this.isDebug) {
                        LogUtils2.logD("DVR_AudioCapture", "音频采集数据源 -- ".concat(String.valueOf(read)).concat(" -- bytes"));
                    }
                } else if (AudioCapture.this.isDebug) {
                    LogUtils2.logD("DVR_AudioCapture", "录音采集异常");
                }
                SystemClock.sleep(10L);
            }
        }
    }

    public AudioCaptureListener getCaptureListener() {
        return this.captureListener;
    }

    public void setCaptureListener(AudioCaptureListener audioCaptureListener) {
        this.captureListener = audioCaptureListener;
    }
}
