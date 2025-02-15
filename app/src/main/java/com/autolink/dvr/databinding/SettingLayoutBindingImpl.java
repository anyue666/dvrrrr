package com.autolink.dvr.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;

/* loaded from: classes.dex */
public class SettingLayoutBindingImpl extends SettingLayoutBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        return true;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0903R.id.activity_setting_rl1, 1);
        sparseIntArray.put(C0903R.id.activity_setting_close_iv, 2);
        sparseIntArray.put(C0903R.id.activity_setting_title_tv, 3);
        sparseIntArray.put(C0903R.id.activity_setting_ll2, 4);
        sparseIntArray.put(C0903R.id.activity_setting_rec_subtitle_tv, 5);
        sparseIntArray.put(C0903R.id.activity_setting_rec_btn1_close, 6);
        sparseIntArray.put(C0903R.id.activity_setting_rec_btn2_min1, 7);
        sparseIntArray.put(C0903R.id.activity_setting_rec_btn3_min2, 8);
        sparseIntArray.put(C0903R.id.activity_setting_rec_btn4_min3, 9);
        sparseIntArray.put(C0903R.id.activity_setting_ll4, 10);
        sparseIntArray.put(C0903R.id.activity_setting_store_subtitle_tv, 11);
        sparseIntArray.put(C0903R.id.activity_setting_store_space_tv, 12);
        sparseIntArray.put(C0903R.id.activity_setting_store_progressBar, 13);
        sparseIntArray.put(C0903R.id.activity_setting_rl5, 14);
        sparseIntArray.put(C0903R.id.activity_setting_set_btn1_clear_all, 15);
        sparseIntArray.put(C0903R.id.activity_setting_set_btn2_eject_usb, 16);
        sparseIntArray.put(C0903R.id.activity_setting_set_btn3_restore_default, 17);
    }

    public SettingLayoutBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 18, sIncludes, sViewsWithIds));
    }

    private SettingLayoutBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (RelativeLayout) objArr[0], (ImageView) objArr[2], (LinearLayout) objArr[4], (LinearLayout) objArr[10], (AppCompatButton) objArr[6], (AppCompatButton) objArr[7], (AppCompatButton) objArr[8], (AppCompatButton) objArr[9], (TextView) objArr[5], (RelativeLayout) objArr[1], (LinearLayout) objArr[14], (AppCompatButton) objArr[15], (AppCompatButton) objArr[16], (AppCompatButton) objArr[17], (ProgressBar) objArr[13], (TextView) objArr[12], (TextView) objArr[11], (TextView) objArr[3]);
        this.mDirtyFlags = -1L;
        this.activitySettingBackground.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1L;
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
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
