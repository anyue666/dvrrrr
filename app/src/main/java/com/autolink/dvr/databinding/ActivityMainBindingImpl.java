package com.autolink.dvr.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.common.media.surface.BackgroundSurfaceView;
import com.autolink.dvr.common.media.surface.CameraSurfaceView;
import com.autolink.dvr.viewmodel.MainViewModel;

/* loaded from: classes.dex */
public class ActivityMainBindingImpl extends ActivityMainBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0903R.id.activity_main_bg, 1);
        sparseIntArray.put(C0903R.id.activity_main_camera, 2);
        sparseIntArray.put(C0903R.id.activity_main_close_iv, 3);
        sparseIntArray.put(C0903R.id.activity_main_video_state_iv, 4);
        sparseIntArray.put(C0903R.id.activity_main_video_state_tv, 5);
        sparseIntArray.put(C0903R.id.activity_main_turn_light_iv, 6);
        sparseIntArray.put(C0903R.id.activity_main_emergency_light_iv, 7);
        sparseIntArray.put(C0903R.id.activity_main_overlay_information_iv, 8);
        sparseIntArray.put(C0903R.id.activity_main_coverup_iv, 9);
        sparseIntArray.put(C0903R.id.activity_main_coverup_tv, 10);
        sparseIntArray.put(C0903R.id.activity_main_setting_iv, 11);
        sparseIntArray.put(C0903R.id.activity_main_file_iv, 12);
    }

    public ActivityMainBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 13, sIncludes, sViewsWithIds));
    }

    private ActivityMainBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (BackgroundSurfaceView) objArr[1], (CameraSurfaceView) objArr[2], (ImageView) objArr[3], (ImageView) objArr[9], (TextView) objArr[10], (ImageView) objArr[7], (ImageView) objArr[12], (ImageView) objArr[8], (ImageView) objArr[11], (ImageView) objArr[6], (ImageView) objArr[4], (TextView) objArr[5]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        if (2 != i) {
            return false;
        }
        setViewModel((MainViewModel) obj);
        return true;
    }

    @Override // com.autolink.dvr.databinding.ActivityMainBinding
    public void setViewModel(MainViewModel mainViewModel) {
        this.mViewModel = mainViewModel;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
