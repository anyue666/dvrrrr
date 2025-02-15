package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.p003ui.view.ProgressView;

/* loaded from: classes.dex */
public abstract class DeletingLayoutBinding extends ViewDataBinding {
    public final ProgressView ivLoading;
    public final TextView tvLoading;

    protected DeletingLayoutBinding(Object obj, View view, int i, ProgressView progressView, TextView textView) {
        super(obj, view, i);
        this.ivLoading = progressView;
        this.tvLoading = textView;
    }

    public static DeletingLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DeletingLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (DeletingLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.deleting_layout, viewGroup, z, obj);
    }

    public static DeletingLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DeletingLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (DeletingLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.deleting_layout, null, false, obj);
    }

    public static DeletingLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DeletingLayoutBinding bind(View view, Object obj) {
        return (DeletingLayoutBinding) bind(obj, view, C0903R.layout.deleting_layout);
    }
}
