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
public abstract class ActivityVideoTopBinding extends ViewDataBinding {
    public final RelativeLayout activityMainTopSeparation;
    public final RelativeLayout activityVideoTopBack;
    public final ImageView activityVideoTopBackIv;
    public final RelativeLayout activityVideoTopDelete;
    public final ImageView activityVideoTopDeleteIv;
    public final RelativeLayout activityVideoTopFilename;
    public final TextView activityVideoTopFilenameTv;

    @Bindable
    protected VideoViewModel mViewModelChild;

    public abstract void setViewModelChild(VideoViewModel videoViewModel);

    protected ActivityVideoTopBinding(Object obj, View view, int i, RelativeLayout relativeLayout, RelativeLayout relativeLayout2, ImageView imageView, RelativeLayout relativeLayout3, ImageView imageView2, RelativeLayout relativeLayout4, TextView textView) {
        super(obj, view, i);
        this.activityMainTopSeparation = relativeLayout;
        this.activityVideoTopBack = relativeLayout2;
        this.activityVideoTopBackIv = imageView;
        this.activityVideoTopDelete = relativeLayout3;
        this.activityVideoTopDeleteIv = imageView2;
        this.activityVideoTopFilename = relativeLayout4;
        this.activityVideoTopFilenameTv = textView;
    }

    public VideoViewModel getViewModelChild() {
        return this.mViewModelChild;
    }

    public static ActivityVideoTopBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoTopBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityVideoTopBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_video_top, viewGroup, z, obj);
    }

    public static ActivityVideoTopBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoTopBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityVideoTopBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_video_top, null, false, obj);
    }

    public static ActivityVideoTopBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoTopBinding bind(View view, Object obj) {
        return (ActivityVideoTopBinding) bind(obj, view, C0903R.layout.activity_video_top);
    }
}
