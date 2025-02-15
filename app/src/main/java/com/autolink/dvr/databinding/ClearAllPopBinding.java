package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;

/* loaded from: classes.dex */
public abstract class ClearAllPopBinding extends ViewDataBinding {
    public final AppCompatButton clearAllPopBtnCancel;
    public final AppCompatButton clearAllPopBtnConfirm;
    public final TextView clearAllPopTv;

    protected ClearAllPopBinding(Object obj, View view, int i, AppCompatButton appCompatButton, AppCompatButton appCompatButton2, TextView textView) {
        super(obj, view, i);
        this.clearAllPopBtnCancel = appCompatButton;
        this.clearAllPopBtnConfirm = appCompatButton2;
        this.clearAllPopTv = textView;
    }

    public static ClearAllPopBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ClearAllPopBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ClearAllPopBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.clear_all_pop, viewGroup, z, obj);
    }

    public static ClearAllPopBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ClearAllPopBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ClearAllPopBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.clear_all_pop, null, false, obj);
    }

    public static ClearAllPopBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ClearAllPopBinding bind(View view, Object obj) {
        return (ClearAllPopBinding) bind(obj, view, C0903R.layout.clear_all_pop);
    }
}
