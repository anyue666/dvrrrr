package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;

/* loaded from: classes.dex */
public abstract class DialogDeleteLayoutBinding extends ViewDataBinding {
    public final TextView cancel;
    public final TextView confirm;
    public final TextView info;
    public final TextView title;

    protected DialogDeleteLayoutBinding(Object obj, View view, int i, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        super(obj, view, i);
        this.cancel = textView;
        this.confirm = textView2;
        this.info = textView3;
        this.title = textView4;
    }

    public static DialogDeleteLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDeleteLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (DialogDeleteLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.dialog_delete_layout, viewGroup, z, obj);
    }

    public static DialogDeleteLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDeleteLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (DialogDeleteLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.dialog_delete_layout, null, false, obj);
    }

    public static DialogDeleteLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogDeleteLayoutBinding bind(View view, Object obj) {
        return (DialogDeleteLayoutBinding) bind(obj, view, C0903R.layout.dialog_delete_layout);
    }
}
