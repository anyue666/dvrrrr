package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.viewmodel.FileViewModel;

/* loaded from: classes.dex */
public abstract class FragmentFileEventBinding extends ViewDataBinding {
    public final RecyclerView fragmentFileEventRecyclerview;

    @Bindable
    protected FileViewModel mViewModel;

    public abstract void setViewModel(FileViewModel fileViewModel);

    protected FragmentFileEventBinding(Object obj, View view, int i, RecyclerView recyclerView) {
        super(obj, view, i);
        this.fragmentFileEventRecyclerview = recyclerView;
    }

    public FileViewModel getViewModel() {
        return this.mViewModel;
    }

    public static FragmentFileEventBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentFileEventBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentFileEventBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.fragment_file_event, viewGroup, z, obj);
    }

    public static FragmentFileEventBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentFileEventBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentFileEventBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.fragment_file_event, null, false, obj);
    }

    public static FragmentFileEventBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentFileEventBinding bind(View view, Object obj) {
        return (FragmentFileEventBinding) bind(obj, view, C0903R.layout.fragment_file_event);
    }
}
