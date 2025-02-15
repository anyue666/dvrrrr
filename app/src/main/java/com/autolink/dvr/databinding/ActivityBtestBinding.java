package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.p003ui.view.DvrRecyclerView;
import com.autolink.dvr.viewmodel.FileViewModel;

/* loaded from: classes.dex */
public abstract class ActivityBtestBinding extends ViewDataBinding {

    @Bindable
    protected FileViewModel mViewModelChild;
    public final DvrRecyclerView testView;

    public abstract void setViewModelChild(FileViewModel fileViewModel);

    protected ActivityBtestBinding(Object obj, View view, int i, DvrRecyclerView dvrRecyclerView) {
        super(obj, view, i);
        this.testView = dvrRecyclerView;
    }

    public FileViewModel getViewModelChild() {
        return this.mViewModelChild;
    }

    public static ActivityBtestBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityBtestBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityBtestBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_btest, viewGroup, z, obj);
    }

    public static ActivityBtestBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityBtestBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityBtestBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_btest, null, false, obj);
    }

    public static ActivityBtestBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityBtestBinding bind(View view, Object obj) {
        return (ActivityBtestBinding) bind(obj, view, C0903R.layout.activity_btest);
    }
}
