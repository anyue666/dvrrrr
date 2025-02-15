package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.viewmodel.VideoViewModel;

/* loaded from: classes.dex */
public abstract class ActivityVideoControlBinding extends ViewDataBinding {
    public final ActivityVideoBottomBinding activityVideoBottom;
    public final ImageView activityVideoControlPlay;
    public final ActivityVideoTopBinding activityVideoTop;

    @Bindable
    protected VideoViewModel mViewModelChild;

    public abstract void setViewModelChild(VideoViewModel videoViewModel);

    protected ActivityVideoControlBinding(Object obj, View view, int i, ActivityVideoBottomBinding activityVideoBottomBinding, ImageView imageView, ActivityVideoTopBinding activityVideoTopBinding) {
        super(obj, view, i);
        this.activityVideoBottom = activityVideoBottomBinding;
        this.activityVideoControlPlay = imageView;
        this.activityVideoTop = activityVideoTopBinding;
    }

    public VideoViewModel getViewModelChild() {
        return this.mViewModelChild;
    }

    public static ActivityVideoControlBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoControlBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityVideoControlBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_video_control, viewGroup, z, obj);
    }

    public static ActivityVideoControlBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoControlBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityVideoControlBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_video_control, null, false, obj);
    }

    public static ActivityVideoControlBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoControlBinding bind(View view, Object obj) {
        return (ActivityVideoControlBinding) bind(obj, view, C0903R.layout.activity_video_control);
    }
}
