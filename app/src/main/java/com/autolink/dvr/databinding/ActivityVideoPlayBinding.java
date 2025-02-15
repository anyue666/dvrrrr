package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.viewmodel.VideoViewModel;

/* loaded from: classes.dex */
public abstract class ActivityVideoPlayBinding extends ViewDataBinding {
    public final ImageView closeIv;
    public final ImageView delete;
    public final ImageView fullScreen;
    public final ImageView icCloseIcon;
    public final ImageView lastOne;

    @Bindable
    protected VideoViewModel mViewModel;
    public final ImageView nextOne;
    public final ImageView playIcon;
    public final SeekBar seekBar;
    public final RelativeLayout speedOver;
    public final ImageView speedOverImg;
    public final TextView speedOverTv;
    public final TextView sumTime;
    public final TextView time;
    public final TextView topTime;
    public final VideoView videoView;

    public abstract void setViewModel(VideoViewModel videoViewModel);

    protected ActivityVideoPlayBinding(Object obj, View view, int i, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, SeekBar seekBar, RelativeLayout relativeLayout, ImageView imageView8, TextView textView, TextView textView2, TextView textView3, TextView textView4, VideoView videoView) {
        super(obj, view, i);
        this.closeIv = imageView;
        this.delete = imageView2;
        this.fullScreen = imageView3;
        this.icCloseIcon = imageView4;
        this.lastOne = imageView5;
        this.nextOne = imageView6;
        this.playIcon = imageView7;
        this.seekBar = seekBar;
        this.speedOver = relativeLayout;
        this.speedOverImg = imageView8;
        this.speedOverTv = textView;
        this.sumTime = textView2;
        this.time = textView3;
        this.topTime = textView4;
        this.videoView = videoView;
    }

    public VideoViewModel getViewModel() {
        return this.mViewModel;
    }

    public static ActivityVideoPlayBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoPlayBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityVideoPlayBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_video_play, viewGroup, z, obj);
    }

    public static ActivityVideoPlayBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoPlayBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityVideoPlayBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_video_play, null, false, obj);
    }

    public static ActivityVideoPlayBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoPlayBinding bind(View view, Object obj) {
        return (ActivityVideoPlayBinding) bind(obj, view, C0903R.layout.activity_video_play);
    }
}
