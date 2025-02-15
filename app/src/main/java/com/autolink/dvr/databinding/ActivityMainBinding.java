package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.common.media.surface.BackgroundSurfaceView;
import com.autolink.dvr.common.media.surface.CameraSurfaceView;
import com.autolink.dvr.viewmodel.MainViewModel;

/* loaded from: classes.dex */
public abstract class ActivityMainBinding extends ViewDataBinding {
    public final BackgroundSurfaceView activityMainBg;
    public final CameraSurfaceView activityMainCamera;
    public final ImageView activityMainCloseIv;
    public final ImageView activityMainCoverupIv;
    public final TextView activityMainCoverupTv;
    public final ImageView activityMainEmergencyLightIv;
    public final ImageView activityMainFileIv;
    public final ImageView activityMainOverlayInformationIv;
    public final ImageView activityMainSettingIv;
    public final ImageView activityMainTurnLightIv;
    public final ImageView activityMainVideoStateIv;
    public final TextView activityMainVideoStateTv;

    @Bindable
    protected MainViewModel mViewModel;

    public abstract void setViewModel(MainViewModel mainViewModel);

    protected ActivityMainBinding(Object obj, View view, int i, BackgroundSurfaceView backgroundSurfaceView, CameraSurfaceView cameraSurfaceView, ImageView imageView, ImageView imageView2, TextView textView, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, ImageView imageView8, TextView textView2) {
        super(obj, view, i);
        this.activityMainBg = backgroundSurfaceView;
        this.activityMainCamera = cameraSurfaceView;
        this.activityMainCloseIv = imageView;
        this.activityMainCoverupIv = imageView2;
        this.activityMainCoverupTv = textView;
        this.activityMainEmergencyLightIv = imageView3;
        this.activityMainFileIv = imageView4;
        this.activityMainOverlayInformationIv = imageView5;
        this.activityMainSettingIv = imageView6;
        this.activityMainTurnLightIv = imageView7;
        this.activityMainVideoStateIv = imageView8;
        this.activityMainVideoStateTv = textView2;
    }

    public MainViewModel getViewModel() {
        return this.mViewModel;
    }

    public static ActivityMainBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMainBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityMainBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_main, viewGroup, z, obj);
    }

    public static ActivityMainBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMainBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityMainBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_main, null, false, obj);
    }

    public static ActivityMainBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMainBinding bind(View view, Object obj) {
        return (ActivityMainBinding) bind(obj, view, C0903R.layout.activity_main);
    }
}
