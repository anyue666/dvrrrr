package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.viewmodel.FileViewModel;

/* loaded from: classes.dex */
public abstract class ActivityTestItemBinding extends ViewDataBinding {
    public final ImageView img;

    @Bindable
    protected FileViewModel mViewModelChild;

    public abstract void setViewModelChild(FileViewModel fileViewModel);

    protected ActivityTestItemBinding(Object obj, View view, int i, ImageView imageView) {
        super(obj, view, i);
        this.img = imageView;
    }

    public FileViewModel getViewModelChild() {
        return this.mViewModelChild;
    }

    public static ActivityTestItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTestItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityTestItemBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_test_item, viewGroup, z, obj);
    }

    public static ActivityTestItemBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTestItemBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityTestItemBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_test_item, null, false, obj);
    }

    public static ActivityTestItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTestItemBinding bind(View view, Object obj) {
        return (ActivityTestItemBinding) bind(obj, view, C0903R.layout.activity_test_item);
    }
}
