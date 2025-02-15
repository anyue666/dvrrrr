package com.autolink.dvr.p003ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import androidx.core.view.ViewCompat;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.common.base.BaseMvvmActivity;
import com.autolink.dvr.common.can.CanOperation;
import com.autolink.dvr.common.receiver.USBReceiver;
import com.autolink.dvr.common.utils.CommonToolUtils;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.USBUtil;
import com.autolink.dvr.databinding.ActivityVideoPlayBinding;
import com.autolink.dvr.model.FileNormalListResult;
import com.autolink.dvr.p003ui.file.FileEventFragment;
import com.autolink.dvr.p003ui.file.FileNormalFragment;
import com.autolink.dvr.p003ui.setting.DeleteVideoWindow;
import com.autolink.dvr.viewmodel.VideoViewModel;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class VideoActivity extends BaseMvvmActivity<VideoViewModel, ActivityVideoPlayBinding> implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private static final int CLICK_CHECK_TIME = 500;
    private static final String TAG = "VideoActivity";
    private static final int UPDATE_PROGRESS = 1;
    private List<FileNormalListResult.Item> itemData;
    private long lastClickTime;
    private Bundle mBundle;
    private DeleteVideoWindow mDeleteVideoDialog;
    private MediaPlayer mMediaPlayer;
    int mPosition;
    public int ACTIVITY_FOR_RESULT_VIDEO_CODE = 202;
    private VideoActivityHandler mVideoActivityHandler = new VideoActivityHandler(this);
    private boolean soundValueState = false;
    private List<String> file = new ArrayList();
    boolean fullScreen = false;
    boolean isPlay = true;
    int outPosition = 0;
    private boolean mIsPlayError = false;
    private final String PLAY_POSITION = "position";
    private final String PLAY_PROGRESS = "progress";
    private final String IS_PLAYING = "isPlaying";
    BroadcastReceiver receiver = new BroadcastReceiver() { // from class: com.autolink.dvr.ui.VideoActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.e("receiver ", USBReceiver.USB_STATUS);
            VideoActivity.this.finish();
        }
    };
    BroadcastReceiver speedReceiver = new BroadcastReceiver() { // from class: com.autolink.dvr.ui.VideoActivity.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (CanOperation.ACTION_OVER_SPEED.equals(intent.getAction())) {
                LogUtils2.logI(VideoActivity.TAG, "onReceive  isSpeedOver15 " + CanOperation.isSpeedOver15);
                if (CanOperation.isSpeedOver15) {
                    ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).speedOver.setVisibility(0);
                    if (((ActivityVideoPlayBinding) VideoActivity.this.mBinding).videoView.isPlaying()) {
                        VideoActivity.this.isPlay = true;
                        ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).videoView.pause();
                        ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).playIcon.setImageDrawable(VideoActivity.this.getDrawable(C0903R.drawable.icon_pause));
                        return;
                    }
                    return;
                }
                ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).speedOver.setVisibility(8);
            }
        }
    };

    @Override // com.autolink.dvr.common.base.BaseMvvmActivity
    protected int getLayoutId() {
        return C0903R.layout.activity_video_play;
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseActivity, com.autolink.dvr.common.base.hmi.IBaseView
    public void initViewObservable() {
    }

    @Override // com.autolink.dvr.common.base.BaseMvvmActivity, com.autolink.dvr.common.base.hmi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        this.mBundle = getIntent().getBundleExtra("bundle");
        if (Build.VERSION.SDK_INT >= 28) {
            super.onCreate(bundle);
        }
        initData(bundle);
        registerUsbState();
        registerSpeedState();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("position", this.mPosition);
        bundle.putBoolean("isPlaying", this.isPlay);
        bundle.putInt("progress", this.outPosition);
        LogUtils2.logI(TAG, "onSaveInstanceState    PLAY_POSITION " + this.mPosition + "  IS_PLAYING " + this.isPlay + "  PLAY_PROGRESS " + this.outPosition);
    }

    private void registerUsbState() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(USBReceiver.USB_STATUS);
        registerReceiver(this.receiver, intentFilter);
    }

    private void registerSpeedState() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CanOperation.ACTION_OVER_SPEED);
        registerReceiver(this.speedReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUpdateProgress() {
        if (((ActivityVideoPlayBinding) this.mBinding).videoView.isPlaying()) {
            this.mVideoActivityHandler.removeMessages(1);
            updateProgress();
            this.mVideoActivityHandler.sendEmptyMessageDelayed(1, 200L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProgress() {
        ((ActivityVideoPlayBinding) this.mBinding).seekBar.setProgress(((ActivityVideoPlayBinding) this.mBinding).videoView.getCurrentPosition());
        String stringForTime = CommonToolUtils.INSTANCE.getSingleton().stringForTime(((ActivityVideoPlayBinding) this.mBinding).videoView.getCurrentPosition());
        Log.e("播放时间转换后当前播放时间是------", stringForTime);
        ((ActivityVideoPlayBinding) this.mBinding).time.setText(stringForTime);
        String stringForTime2 = CommonToolUtils.INSTANCE.getSingleton().stringForTime(((ActivityVideoPlayBinding) this.mBinding).videoView.getDuration());
        Log.e("播放时间总时长是------", stringForTime2);
        ((ActivityVideoPlayBinding) this.mBinding).sumTime.setText(stringForTime2);
    }

    public void initData(Bundle bundle) {
        String string = this.mBundle.getString("playtype");
        if (USBUtil.normal.equals(string)) {
            this.itemData = FileNormalFragment.allData;
        } else if ("event".equals(string)) {
            this.itemData = FileEventFragment.allData;
        }
        if (bundle != null) {
            this.mPosition = bundle.getInt("position");
            this.isPlay = bundle.getBoolean("isPlaying", true);
            this.outPosition = bundle.getInt("progress", 0);
        } else {
            this.mPosition = this.mBundle.getInt("position");
        }
        LogUtils2.logI(TAG, "initData mPosition = " + this.mPosition + " isPlay = " + this.isPlay + " outPosition = " + this.outPosition);
        FileNormalListResult.Item item = this.itemData.get(this.mPosition);
        removeFalseData(this.itemData);
        this.mPosition = this.itemData.indexOf(item);
        ((ActivityVideoPlayBinding) this.mBinding).topTime.setText(item.getName());
        if (this.itemData.size() <= 1) {
            ((ActivityVideoPlayBinding) this.mBinding).nextOne.setEnabled(false);
            ((ActivityVideoPlayBinding) this.mBinding).lastOne.setEnabled(false);
            ((ActivityVideoPlayBinding) this.mBinding).nextOne.setImageDrawable(getDrawable(C0903R.drawable.icon_bw_disable));
            ((ActivityVideoPlayBinding) this.mBinding).lastOne.setImageDrawable(getDrawable(C0903R.drawable.icon_fw_disable));
        } else {
            ((ActivityVideoPlayBinding) this.mBinding).nextOne.setEnabled(true);
            ((ActivityVideoPlayBinding) this.mBinding).lastOne.setEnabled(true);
            ((ActivityVideoPlayBinding) this.mBinding).nextOne.setImageDrawable(getDrawable(C0903R.drawable.icon_bw));
            ((ActivityVideoPlayBinding) this.mBinding).lastOne.setImageDrawable(getDrawable(C0903R.drawable.icon_fw));
        }
        if (Build.VERSION.SDK_INT >= 26) {
            ((ActivityVideoPlayBinding) this.mBinding).videoView.setAudioFocusRequest(0);
        }
        ((ActivityVideoPlayBinding) this.mBinding).videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.autolink.dvr.ui.VideoActivity.3
            @Override // android.media.MediaPlayer.OnPreparedListener
            public void onPrepared(MediaPlayer mediaPlayer) {
                LogUtils2.logI(VideoActivity.TAG, "videoView onPrepared");
                VideoActivity.this.mMediaPlayer = mediaPlayer;
                VideoActivity.this.mMediaPlayer.setOnPreparedListener(VideoActivity.this);
                VideoActivity.this.mMediaPlayer.setOnErrorListener(VideoActivity.this);
                VideoActivity.this.soundValueState = !r5.soundValueState;
                ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).videoView.seekTo(VideoActivity.this.outPosition);
                ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).seekBar.setMax(((ActivityVideoPlayBinding) VideoActivity.this.mBinding).videoView.getDuration());
                if (VideoActivity.this.isPlay) {
                    VideoActivity.this.startPlay();
                } else {
                    VideoActivity.this.updateProgress();
                    ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).playIcon.setImageDrawable(VideoActivity.this.getDrawable(C0903R.drawable.icon_pause));
                }
                ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).videoView.setBackgroundColor(0);
                VideoActivity.this.mVideoActivityHandler.sendEmptyMessageDelayed(1, 200L);
            }
        });
        ((ActivityVideoPlayBinding) this.mBinding).videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.autolink.dvr.ui.VideoActivity.4
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer) {
                LogUtils2.logI(VideoActivity.TAG, "videoView  onCompletion mIsPlayError = " + VideoActivity.this.mIsPlayError);
                VideoActivity.this.mVideoActivityHandler.removeMessages(1);
                ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).playIcon.setImageDrawable(VideoActivity.this.getDrawable(C0903R.drawable.icon_pause));
                VideoActivity.this.outPosition = 0;
                if (!VideoActivity.this.mIsPlayError) {
                    ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).seekBar.setProgress(((ActivityVideoPlayBinding) VideoActivity.this.mBinding).seekBar.getMax());
                    ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).time.setText(CommonToolUtils.INSTANCE.getSingleton().stringForTime(((ActivityVideoPlayBinding) VideoActivity.this.mBinding).videoView.getDuration()));
                } else {
                    ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).seekBar.setProgress(0);
                    ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).time.setText("00:00");
                }
            }
        });
        ((ActivityVideoPlayBinding) this.mBinding).videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.autolink.dvr.ui.VideoActivity.5
            @Override // android.media.MediaPlayer.OnErrorListener
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                LogUtils2.logE(VideoActivity.TAG, "videoView onError  what = " + i);
                VideoActivity.this.mIsPlayError = true;
                VideoActivity.this.outPosition = 0;
                ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).sumTime.setText("00:00:00");
                ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).videoView.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                return false;
            }
        });
        ((ActivityVideoPlayBinding) this.mBinding).delete.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.VideoActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (VideoActivity.this.isFastClick()) {
                    LogUtils2.logI(VideoActivity.TAG, "mBinding.delete.setOnClickListener return ");
                    return;
                }
                VideoActivity.this.mDeleteVideoDialog = new DeleteVideoWindow(VideoActivity.this);
                VideoActivity.this.mDeleteVideoDialog.setDeleteNumber(1);
                VideoActivity.this.mDeleteVideoDialog.setOnClickListener(new DeleteVideoWindow.ClickListener() { // from class: com.autolink.dvr.ui.VideoActivity.6.1
                    @Override // com.autolink.dvr.ui.setting.DeleteVideoWindow.ClickListener
                    public void onConfirm() {
                        if (VideoActivity.this.mViewModel == null) {
                            LogUtils2.logW(VideoActivity.TAG, "mViewModel == null");
                            if (VideoActivity.this.mDeleteVideoDialog != null) {
                                VideoActivity.this.mDeleteVideoDialog.finish();
                                return;
                            }
                            return;
                        }
                        LogUtils2.logI(VideoActivity.TAG, "delete video " + ((FileNormalListResult.Item) VideoActivity.this.itemData.get(VideoActivity.this.mPosition)).getPath());
                        VideoActivity.this.file.clear();
                        VideoActivity.this.file.add(((FileNormalListResult.Item) VideoActivity.this.itemData.get(VideoActivity.this.mPosition)).getPath());
                        ((VideoViewModel) VideoActivity.this.mViewModel).deleteFileList(VideoActivity.this.file);
                        VideoActivity.this.mDeleteVideoDialog.finish();
                        VideoActivity.this.mDeleteVideoDialog = null;
                        VideoActivity.this.setResult(VideoActivity.this.ACTIVITY_FOR_RESULT_VIDEO_CODE);
                        VideoActivity.this.finish();
                    }
                });
            }
        });
        ((ActivityVideoPlayBinding) this.mBinding).seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.autolink.dvr.ui.VideoActivity.7
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (((ActivityVideoPlayBinding) VideoActivity.this.mBinding).videoView.isPlaying()) {
                    ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).videoView.pause();
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                if (VideoActivity.this.mVideoActivityHandler != null) {
                    if (((ActivityVideoPlayBinding) VideoActivity.this.mBinding).videoView.isPlaying()) {
                        return;
                    }
                    ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).videoView.seekTo(progress);
                    VideoActivity.this.startPlay();
                    VideoActivity.this.mVideoActivityHandler.sendEmptyMessageDelayed(1, 200L);
                    return;
                }
                LogUtils2.logW(VideoActivity.TAG, "mVideoActivityHandler == null");
            }
        });
        initVideoView();
    }

    private void removeFalseData(List<FileNormalListResult.Item> list) {
        Iterator<FileNormalListResult.Item> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().isFalseData()) {
                it.remove();
            }
        }
    }

    public void initVideoView() {
        LogUtils2.logI(TAG, "initVideoView  isPlay = " + this.isPlay);
        ((ActivityVideoPlayBinding) this.mBinding).videoView.setVideoPath(this.itemData.get(this.mPosition).getPath());
        if (this.isPlay) {
            startPlay();
        }
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseActivity, com.autolink.dvr.common.base.hmi.IBaseView
    public void initView() {
        ((ActivityVideoPlayBinding) this.mBinding).nextOne.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.VideoActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (VideoActivity.this.isFastClick()) {
                    return;
                }
                VideoActivity.this.switchVideo(false);
            }
        });
        ((ActivityVideoPlayBinding) this.mBinding).lastOne.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.VideoActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (VideoActivity.this.isFastClick()) {
                    return;
                }
                VideoActivity.this.switchVideo(true);
            }
        });
        ((ActivityVideoPlayBinding) this.mBinding).playIcon.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.VideoActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (VideoActivity.this.isFastClick()) {
                    return;
                }
                if (((ActivityVideoPlayBinding) VideoActivity.this.mBinding).videoView.isPlaying()) {
                    ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).videoView.pause();
                    ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).playIcon.setImageDrawable(VideoActivity.this.getDrawable(C0903R.drawable.icon_pause));
                } else {
                    VideoActivity.this.startPlay();
                    ((ActivityVideoPlayBinding) VideoActivity.this.mBinding).playIcon.setImageDrawable(VideoActivity.this.getDrawable(C0903R.drawable.icon_play));
                    VideoActivity.this.startUpdateProgress();
                }
            }
        });
        ((ActivityVideoPlayBinding) this.mBinding).icCloseIcon.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.VideoActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VideoActivity.this.finish();
            }
        });
        ((ActivityVideoPlayBinding) this.mBinding).closeIv.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.VideoActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VideoActivity.this.finish();
            }
        });
        ((ActivityVideoPlayBinding) this.mBinding).fullScreen.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.VideoActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VideoActivity.this.fullScreen = true;
                VideoActivity.this.enterFullScreenOrExitFullScreen();
            }
        });
        ((ActivityVideoPlayBinding) this.mBinding).videoView.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.VideoActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (VideoActivity.this.fullScreen) {
                    VideoActivity.this.fullScreen = false;
                    VideoActivity.this.enterFullScreenOrExitFullScreen();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPlay() {
        LogUtils2.logI(TAG, "startPlay");
        if (this.mIsPlayError) {
            ((ActivityVideoPlayBinding) this.mBinding).videoView.setVideoPath(this.itemData.get(this.mPosition).getPath());
            ((ActivityVideoPlayBinding) this.mBinding).topTime.setText(this.itemData.get(this.mPosition).getName());
            this.mIsPlayError = false;
        }
        if (((ActivityVideoPlayBinding) this.mBinding).videoView.isPlaying()) {
            return;
        }
        ((ActivityVideoPlayBinding) this.mBinding).videoView.start();
        ((ActivityVideoPlayBinding) this.mBinding).playIcon.setImageDrawable(getDrawable(C0903R.drawable.icon_play));
    }

    public void switchVideo(boolean z) {
        LogUtils2.logI(TAG, "switchVideo next = " + z);
        this.mIsPlayError = false;
        this.outPosition = 0;
        if (z) {
            if (this.mPosition >= this.itemData.size() - 1) {
                this.mPosition = 0;
            } else {
                this.mPosition++;
            }
        } else {
            int i = this.mPosition;
            if (i <= 0) {
                this.mPosition = this.itemData.size() - 1;
            } else {
                this.mPosition = i - 1;
            }
        }
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null && this.itemData != null) {
            try {
                ((ActivityVideoPlayBinding) this.mBinding).topTime.setText(this.itemData.get(this.mPosition).getName());
                this.mMediaPlayer.reset();
                this.mMediaPlayer.setDataSource(this.itemData.get(this.mPosition).getPath());
                this.mMediaPlayer.prepareAsync();
                return;
            } catch (Exception e) {
                this.mIsPlayError = true;
                startPlay();
                e.printStackTrace();
                return;
            }
        }
        if (mediaPlayer != null || this.itemData == null) {
            return;
        }
        this.mIsPlayError = true;
        startPlay();
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        LogUtils2.logI(TAG, "onPause");
        if (((ActivityVideoPlayBinding) this.mBinding).videoView.isPlaying()) {
            this.isPlay = true;
            ((ActivityVideoPlayBinding) this.mBinding).videoView.pause();
            ((ActivityVideoPlayBinding) this.mBinding).playIcon.setImageDrawable(getDrawable(C0903R.drawable.icon_pause));
        } else {
            this.isPlay = false;
        }
        this.outPosition = ((ActivityVideoPlayBinding) this.mBinding).videoView.getCurrentPosition();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        LogUtils2.logI(TAG, "onStop");
        DeleteVideoWindow deleteVideoWindow = this.mDeleteVideoDialog;
        if (deleteVideoWindow != null) {
            deleteVideoWindow.finish();
            this.mDeleteVideoDialog = null;
        }
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        LogUtils2.logI(TAG, "onDestroy");
        this.mVideoActivityHandler.removeMessages(0);
        this.mVideoActivityHandler.removeMessages(1);
        this.mVideoActivityHandler.removeCallbacksAndMessages(null);
        this.mVideoActivityHandler = null;
        if (((ActivityVideoPlayBinding) this.mBinding).videoView.isPlaying()) {
            ((ActivityVideoPlayBinding) this.mBinding).videoView.stopPlayback();
        }
        unregisterReceiver(this.receiver);
        unregisterReceiver(this.speedReceiver);
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.setOnPreparedListener(null);
            this.mMediaPlayer.setOnErrorListener(null);
            this.mMediaPlayer = null;
        }
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enterFullScreenOrExitFullScreen() {
        if (this.fullScreen) {
            ((ActivityVideoPlayBinding) this.mBinding).icCloseIcon.setVisibility(8);
            ((ActivityVideoPlayBinding) this.mBinding).topTime.setVisibility(8);
            ((ActivityVideoPlayBinding) this.mBinding).delete.setVisibility(8);
            ((ActivityVideoPlayBinding) this.mBinding).time.setVisibility(8);
            ((ActivityVideoPlayBinding) this.mBinding).seekBar.setVisibility(8);
            ((ActivityVideoPlayBinding) this.mBinding).sumTime.setVisibility(8);
            ((ActivityVideoPlayBinding) this.mBinding).nextOne.setVisibility(8);
            ((ActivityVideoPlayBinding) this.mBinding).playIcon.setVisibility(8);
            ((ActivityVideoPlayBinding) this.mBinding).lastOne.setVisibility(8);
            ((ActivityVideoPlayBinding) this.mBinding).fullScreen.setVisibility(8);
            return;
        }
        ((ActivityVideoPlayBinding) this.mBinding).icCloseIcon.setVisibility(0);
        ((ActivityVideoPlayBinding) this.mBinding).topTime.setVisibility(0);
        ((ActivityVideoPlayBinding) this.mBinding).delete.setVisibility(0);
        ((ActivityVideoPlayBinding) this.mBinding).time.setVisibility(0);
        ((ActivityVideoPlayBinding) this.mBinding).seekBar.setVisibility(0);
        ((ActivityVideoPlayBinding) this.mBinding).sumTime.setVisibility(0);
        ((ActivityVideoPlayBinding) this.mBinding).nextOne.setVisibility(0);
        ((ActivityVideoPlayBinding) this.mBinding).playIcon.setVisibility(0);
        ((ActivityVideoPlayBinding) this.mBinding).lastOne.setVisibility(0);
        ((ActivityVideoPlayBinding) this.mBinding).fullScreen.setVisibility(0);
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) {
        int duration = mediaPlayer.getDuration();
        LogUtils2.logI(TAG, "MediaPlayer onPrepared, duration= " + duration);
        mediaPlayer.start();
        ((ActivityVideoPlayBinding) this.mBinding).playIcon.setImageDrawable(getDrawable(C0903R.drawable.icon_play));
        ((ActivityVideoPlayBinding) this.mBinding).seekBar.setMax(duration);
        this.mVideoActivityHandler.sendEmptyMessageDelayed(1, 200L);
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        LogUtils2.logI(TAG, "MediaPlayer  onError");
        this.mIsPlayError = true;
        this.outPosition = 0;
        ((ActivityVideoPlayBinding) this.mBinding).sumTime.setText("00:00:00");
        ((ActivityVideoPlayBinding) this.mBinding).videoView.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        startPlay();
        return false;
    }

    private static class VideoActivityHandler extends Handler {
        private WeakReference<VideoActivity> reference;

        VideoActivityHandler(VideoActivity videoActivity) {
            this.reference = new WeakReference<>(videoActivity);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1 && this.reference.get() != null) {
                this.reference.get().startUpdateProgress();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFastClick() {
        long currentTimeMillis = System.currentTimeMillis();
        boolean z = currentTimeMillis - this.lastClickTime < 500;
        this.lastClickTime = currentTimeMillis;
        LogUtils2.logI(TAG, "isFastClick = " + z);
        return z;
    }
}
