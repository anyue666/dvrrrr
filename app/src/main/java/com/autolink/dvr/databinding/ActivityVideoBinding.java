package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.viewmodel.VideoViewModel;

/* loaded from: classes.dex */
public abstract class ActivityVideoBinding extends ViewDataBinding {
    public final View activityVideoContent;
    public final ActivityVideoControlBinding activityVideoControl;
    public final View activityVideoCoverview;
    public final LinearLayout activityVideoLlControl;

    @Bindable
    protected VideoViewModel mViewModel;

    public abstract void setViewModel(VideoViewModel videoViewModel);

    protected ActivityVideoBinding(Object obj, View view, int i, View view2, ActivityVideoControlBinding activityVideoControlBinding, View view3, LinearLayout linearLayout) {
        super(obj, view, i);
        this.activityVideoContent = view2;
        this.activityVideoControl = activityVideoControlBinding;
        this.activityVideoCoverview = view3;
        this.activityVideoLlControl = linearLayout;
    }

    public VideoViewModel getViewModel() {
        return this.mViewModel;
    }

    public static ActivityVideoBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityVideoBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_video, viewGroup, z, obj);
    }

    public static ActivityVideoBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityVideoBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_video, null, false, obj);
    }

    public static ActivityVideoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVideoBinding bind(View view, Object obj) {
        return (ActivityVideoBinding) bind(obj, view, C0903R.layout.activity_video);
    }
}
