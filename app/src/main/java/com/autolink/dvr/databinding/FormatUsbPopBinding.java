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
public abstract class FormatUsbPopBinding extends ViewDataBinding {
    public final AppCompatButton formatPopBtnConfirm;
    public final AppCompatButton formatPopBtnUnmount;
    public final TextView formatPopTv;

    protected FormatUsbPopBinding(Object obj, View view, int i, AppCompatButton appCompatButton, AppCompatButton appCompatButton2, TextView textView) {
        super(obj, view, i);
        this.formatPopBtnConfirm = appCompatButton;
        this.formatPopBtnUnmount = appCompatButton2;
        this.formatPopTv = textView;
    }

    public static FormatUsbPopBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FormatUsbPopBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FormatUsbPopBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.format_usb_pop, viewGroup, z, obj);
    }

    public static FormatUsbPopBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FormatUsbPopBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FormatUsbPopBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.format_usb_pop, null, false, obj);
    }

    public static FormatUsbPopBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FormatUsbPopBinding bind(View view, Object obj) {
        return (FormatUsbPopBinding) bind(obj, view, C0903R.layout.format_usb_pop);
    }
}
