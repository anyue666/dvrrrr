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
public abstract class PopVideoWindowBinding extends ViewDataBinding {
    public final ImageView icCloseIcon;

    @Bindable
    protected VideoViewModel mViewModel;

    public abstract void setViewModel(VideoViewModel videoViewModel);

    protected PopVideoWindowBinding(Object obj, View view, int i, ImageView imageView) {
        super(obj, view, i);
        this.icCloseIcon = imageView;
    }

    public VideoViewModel getViewModel() {
        return this.mViewModel;
    }

    public static PopVideoWindowBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static PopVideoWindowBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (PopVideoWindowBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.pop_video_window, viewGroup, z, obj);
    }

    public static PopVideoWindowBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static PopVideoWindowBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (PopVideoWindowBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.pop_video_window, null, false, obj);
    }

    public static PopVideoWindowBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static PopVideoWindowBinding bind(View view, Object obj) {
        return (PopVideoWindowBinding) bind(obj, view, C0903R.layout.pop_video_window);
    }
}
