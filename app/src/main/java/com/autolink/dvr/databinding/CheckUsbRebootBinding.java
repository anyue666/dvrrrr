package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;

/* loaded from: classes.dex */
public abstract class CheckUsbRebootBinding extends ViewDataBinding {
    public final AppCompatButton checkUsbRebootBtnConfirm;

    protected CheckUsbRebootBinding(Object obj, View view, int i, AppCompatButton appCompatButton) {
        super(obj, view, i);
        this.checkUsbRebootBtnConfirm = appCompatButton;
    }

    public static CheckUsbRebootBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static CheckUsbRebootBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (CheckUsbRebootBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.check_usb_reboot, viewGroup, z, obj);
    }

    public static CheckUsbRebootBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static CheckUsbRebootBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (CheckUsbRebootBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.check_usb_reboot, null, false, obj);
    }

    public static CheckUsbRebootBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static CheckUsbRebootBinding bind(View view, Object obj) {
        return (CheckUsbRebootBinding) bind(obj, view, C0903R.layout.check_usb_reboot);
    }
}
