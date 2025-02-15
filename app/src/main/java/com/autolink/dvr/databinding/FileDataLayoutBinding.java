package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.autolink.dvr.C0903R;

/* loaded from: classes.dex */
public abstract class FileDataLayoutBinding extends ViewDataBinding {
    public final RecyclerView nestingRecyclerView;

    protected FileDataLayoutBinding(Object obj, View view, int i, RecyclerView recyclerView) {
        super(obj, view, i);
        this.nestingRecyclerView = recyclerView;
    }

    public static FileDataLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FileDataLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FileDataLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.file_data_layout, viewGroup, z, obj);
    }

    public static FileDataLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FileDataLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FileDataLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.file_data_layout, null, false, obj);
    }

    public static FileDataLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FileDataLayoutBinding bind(View view, Object obj) {
        return (FileDataLayoutBinding) bind(obj, view, C0903R.layout.file_data_layout);
    }
}
