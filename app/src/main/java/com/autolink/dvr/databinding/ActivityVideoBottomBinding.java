package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.viewmodel.VideoViewModel;

/* loaded from: classes.dex */
public abstract class ActivityVideoBottomBinding extends ViewDataBinding {
    public final RelativeLayout activityVideoBottomNext;
    public final ImageView activityVideoBottomNextIv;
    public final RelativeLayout activityVideoBottomPlay;
    public final ImageView activityVideoBottomPlayIv;
    public final RelativeLayout activityVideoBottomPrevious;
    public final ImageView activityVideoBottomPreviousIv;
    public final RelativeLayout activityVideoBottomSeparation;
    public final RelativeLayout activityVideoBottomShrink;
    public final ImageView activityVideoBottomShrinkIv;
    public final RelativeLayout activityVideoBottomTime;
    public final TextView activityVideoBottomTimeIv;
    public final RelativeLayout activityVideoBottomVolume;
    public final ImageView activityVideoBottomVolumeIv;

    @Bindable
    protected VideoViewModel mViewModelChild;

    public abstract void setViewModelChild(VideoViewModel videoViewModel);

    protected ActivityVideoBottomBinding(Object obj, View view, int i, RelativeLayout relativeLayout, ImageView imageView, RelativeLayout relativeLayout2, ImageView imageView2, RelativeLayout relativeLayout3, ImageView imageView3, RelativeLayout relativeLayout4, RelativeLayout relativeLayout5, ImageView imageView4, RelativeLayout relativeLayout6, TextView textView, RelativeLayout relativeLayout7, ImageView imageView5) {
        super(obj, view, i);
        this.activityVideoBottomNext = relativeLayout;
        this.activityVideoBottomNextIv = imageView;
        this.activityVideoBottomPlay = relativeLayout2;
        this.activityVideoBottomPlayIv = imageView2;
        this.activityVideoBottomPrevious = relativeLayout3;
        this.activityVideoBottomPreviousIv = imageView3;
        this.activityVideoBottomSeparation = relativeLayout4;
        this.activityVideoBottomShrink = relativeLayout5;
        this.activityVideoBottomShrinkIv = imageView4;
        this.activityVideoBottomTime = relativeLayout6;
        this.activityVideoBottomTimeIv = textView;
        this.activityVideoBottomVolume = relativeLayout7;
        this.activityVideoBottomVolumeIv = imageView5;
    }

    public VideoViewModel getViewModelChild() {
        return this.mViewModelChild;
    }

    public static ActivityVideoBottomBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoBottomBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityVideoBottomBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_video_bottom, viewGroup, z, obj);
    }

    public static ActivityVideoBottomBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoBottomBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityVideoBottomBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_video_bottom, null, false, obj);
    }

    public static ActivityVideoBottomBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoBottomBinding bind(View view, Object obj) {
        return (ActivityVideoBottomBinding) bind(obj, view, C0903R.layout.activity_video_bottom);
    }
}
