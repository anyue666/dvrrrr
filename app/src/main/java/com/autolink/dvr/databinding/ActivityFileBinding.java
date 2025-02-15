package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.p003ui.view.DvrViewPager;
import com.autolink.dvr.viewmodel.FileViewModel;

/* loaded from: classes.dex */
public abstract class ActivityFileBinding extends ViewDataBinding {
    public final TextView activityFileContentTv;
    public final DvrViewPager activityFileContentVp;
    public final ActivityFileTopBinding activityFileLlTop;
    public final ConstraintLayout listSwitchLayout;

    @Bindable
    protected FileViewModel mViewModel;
    public final ConstraintLayout tipsLayoutBg;
    public final TextView tipsList;
    public final View tipsPoint1;
    public final View tipsPoint2;
    public final TextView tipsView;
    public final ConstraintLayout topTipsWindow;
    public final ConstraintLayout viewSwitchLayout;

    public abstract void setViewModel(FileViewModel fileViewModel);

    protected ActivityFileBinding(Object obj, View view, int i, TextView textView, DvrViewPager dvrViewPager, ActivityFileTopBinding activityFileTopBinding, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, TextView textView2, View view2, View view3, TextView textView3, ConstraintLayout constraintLayout3, ConstraintLayout constraintLayout4) {
        super(obj, view, i);
        this.activityFileContentTv = textView;
        this.activityFileContentVp = dvrViewPager;
        this.activityFileLlTop = activityFileTopBinding;
        this.listSwitchLayout = constraintLayout;
        this.tipsLayoutBg = constraintLayout2;
        this.tipsList = textView2;
        this.tipsPoint1 = view2;
        this.tipsPoint2 = view3;
        this.tipsView = textView3;
        this.topTipsWindow = constraintLayout3;
        this.viewSwitchLayout = constraintLayout4;
    }

    public FileViewModel getViewModel() {
        return this.mViewModel;
    }

    public static ActivityFileBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFileBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityFileBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_file, viewGroup, z, obj);
    }

    public static ActivityFileBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFileBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityFileBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.activity_file, null, false, obj);
    }

    public static ActivityFileBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFileBinding bind(View view, Object obj) {
        return (ActivityFileBinding) bind(obj, view, C0903R.layout.activity_file);
    }
}
