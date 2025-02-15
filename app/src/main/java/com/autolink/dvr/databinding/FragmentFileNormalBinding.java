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
public abstract class FragmentFileNormalBinding extends ViewDataBinding {
    public final DvrRecyclerView fragmentFileNormalRecyclerview;

    @Bindable
    protected FileViewModel mViewModel;

    public abstract void setViewModel(FileViewModel fileViewModel);

    protected FragmentFileNormalBinding(Object obj, View view, int i, DvrRecyclerView dvrRecyclerView) {
        super(obj, view, i);
        this.fragmentFileNormalRecyclerview = dvrRecyclerView;
    }

    public FileViewModel getViewModel() {
        return this.mViewModel;
    }

    public static FragmentFileNormalBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentFileNormalBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentFileNormalBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.fragment_file_normal, viewGroup, z, obj);
    }

    public static FragmentFileNormalBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentFileNormalBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentFileNormalBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.fragment_file_normal, null, false, obj);
    }

    public static FragmentFileNormalBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentFileNormalBinding bind(View view, Object obj) {
        return (FragmentFileNormalBinding) bind(obj, view, C0903R.layout.fragment_file_normal);
    }
}
