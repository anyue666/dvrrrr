package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;

/* loaded from: classes.dex */
public abstract class SettingLayoutBinding extends ViewDataBinding {
    public final RelativeLayout activitySettingBackground;
    public final ImageView activitySettingCloseIv;
    public final LinearLayout activitySettingLl2;
    public final LinearLayout activitySettingLl4;
    public final AppCompatButton activitySettingRecBtn1Close;
    public final AppCompatButton activitySettingRecBtn2Min1;
    public final AppCompatButton activitySettingRecBtn3Min2;
    public final AppCompatButton activitySettingRecBtn4Min3;
    public final TextView activitySettingRecSubtitleTv;
    public final RelativeLayout activitySettingRl1;
    public final LinearLayout activitySettingRl5;
    public final AppCompatButton activitySettingSetBtn1ClearAll;
    public final AppCompatButton activitySettingSetBtn2EjectUsb;
    public final AppCompatButton activitySettingSetBtn3RestoreDefault;
    public final ProgressBar activitySettingStoreProgressBar;
    public final TextView activitySettingStoreSpaceTv;
    public final TextView activitySettingStoreSubtitleTv;
    public final TextView activitySettingTitleTv;

    protected SettingLayoutBinding(Object obj, View view, int i, RelativeLayout relativeLayout, ImageView imageView, LinearLayout linearLayout, LinearLayout linearLayout2, AppCompatButton appCompatButton, AppCompatButton appCompatButton2, AppCompatButton appCompatButton3, AppCompatButton appCompatButton4, TextView textView, RelativeLayout relativeLayout2, LinearLayout linearLayout3, AppCompatButton appCompatButton5, AppCompatButton appCompatButton6, AppCompatButton appCompatButton7, ProgressBar progressBar, TextView textView2, TextView textView3, TextView textView4) {
        super(obj, view, i);
        this.activitySettingBackground = relativeLayout;
        this.activitySettingCloseIv = imageView;
        this.activitySettingLl2 = linearLayout;
        this.activitySettingLl4 = linearLayout2;
        this.activitySettingRecBtn1Close = appCompatButton;
        this.activitySettingRecBtn2Min1 = appCompatButton2;
        this.activitySettingRecBtn3Min2 = appCompatButton3;
        this.activitySettingRecBtn4Min3 = appCompatButton4;
        this.activitySettingRecSubtitleTv = textView;
        this.activitySettingRl1 = relativeLayout2;
        this.activitySettingRl5 = linearLayout3;
        this.activitySettingSetBtn1ClearAll = appCompatButton5;
        this.activitySettingSetBtn2EjectUsb = appCompatButton6;
        this.activitySettingSetBtn3RestoreDefault = appCompatButton7;
        this.activitySettingStoreProgressBar = progressBar;
        this.activitySettingStoreSpaceTv = textView2;
        this.activitySettingStoreSubtitleTv = textView3;
        this.activitySettingTitleTv = textView4;
    }

    public static SettingLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SettingLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (SettingLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.setting_layout, viewGroup, z, obj);
    }

    public static SettingLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SettingLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (SettingLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.setting_layout, null, false, obj);
    }

    public static SettingLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SettingLayoutBinding bind(View view, Object obj) {
        return (SettingLayoutBinding) bind(obj, view, C0903R.layout.setting_layout);
    }
}
