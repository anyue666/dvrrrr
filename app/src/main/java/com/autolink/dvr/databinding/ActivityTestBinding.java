package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.viewmodel.FileViewModel;

/* loaded from: classes.dex */
public abstract class ActivityTestBinding extends ViewDataBinding {

    @Bindable
    protected FileViewModel mViewModelChild;
    public final Button start;

    public abstract void setViewModelChild(FileViewModel fileViewModel);

    protected ActivityTestBinding(Object obj, View view, int i, Button button) {
        super(obj, view, i);
        this.start = button;
    }

    public FileViewModel getViewModelChild() {
        return this.mViewModelChild;
    }

    public static ActivityTestBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTestBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityTestBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_test, viewGroup, z, obj);
    }

    public static ActivityTestBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTestBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityTestBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_test, null, false, obj);
    }

    public static ActivityTestBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTestBinding bind(View view, Object obj) {
        return (ActivityTestBinding) bind(obj, view, C0903R.layout.activity_test);
    }
}
