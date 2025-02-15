package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.viewmodel.FileViewModel;
import com.google.android.material.tabs.TabLayout;

/* loaded from: classes.dex */
public abstract class ActivityFileTopBinding extends ViewDataBinding {
    public final ImageView activityFileTopBackIv;
    public final ImageView activityFileTopDeleteIv;
    public final ImageView activityFileTopMoreIv;
    public final ImageView activityFileTopMultipleIv;
    public final TabLayout activityFileTopTb;

    @Bindable
    protected FileViewModel mViewModelChild;

    public abstract void setViewModelChild(FileViewModel fileViewModel);

    protected ActivityFileTopBinding(Object obj, View view, int i, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, TabLayout tabLayout) {
        super(obj, view, i);
        this.activityFileTopBackIv = imageView;
        this.activityFileTopDeleteIv = imageView2;
        this.activityFileTopMoreIv = imageView3;
        this.activityFileTopMultipleIv = imageView4;
        this.activityFileTopTb = tabLayout;
    }

    public FileViewModel getViewModelChild() {
        return this.mViewModelChild;
    }

    public static ActivityFileTopBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFileTopBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityFileTopBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_file_top, viewGroup, z, obj);
    }

    public static ActivityFileTopBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFileTopBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityFileTopBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_file_top, null, false, obj);
    }

    public static ActivityFileTopBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFileTopBinding bind(View view, Object obj) {
        return (ActivityFileTopBinding) bind(obj, view, C0903R.layout.activity_file_top);
    }
}
