package com.autolink.dvr.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.viewmodel.VideoViewModel;

/* loaded from: classes.dex */
public class ActivityVideoPlayBindingImpl extends ActivityVideoPlayBinding {
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
        sparseIntArray.put(C0903R.id.video_view, 1);
        sparseIntArray.put(C0903R.id.ic_close_icon, 2);
        sparseIntArray.put(C0903R.id.top_time, 3);
        sparseIntArray.put(C0903R.id.time, 4);
        sparseIntArray.put(C0903R.id.seek_bar, 5);
        sparseIntArray.put(C0903R.id.sum_time, 6);
        sparseIntArray.put(C0903R.id.play_icon, 7);
        sparseIntArray.put(C0903R.id.last_one, 8);
        sparseIntArray.put(C0903R.id.next_one, 9);
        sparseIntArray.put(C0903R.id.full_screen, 10);
        sparseIntArray.put(C0903R.id.delete, 11);
        sparseIntArray.put(C0903R.id.speed_over, 12);
        sparseIntArray.put(C0903R.id.close_iv, 13);
        sparseIntArray.put(C0903R.id.speed_over_img, 14);
        sparseIntArray.put(C0903R.id.speed_over_tv, 15);
    }

    public ActivityVideoPlayBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 16, sIncludes, sViewsWithIds));
    }

    private ActivityVideoPlayBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ImageView) objArr[13], (ImageView) objArr[11], (ImageView) objArr[10], (ImageView) objArr[2], (ImageView) objArr[8], (ImageView) objArr[9], (ImageView) objArr[7], (SeekBar) objArr[5], (RelativeLayout) objArr[12], (ImageView) objArr[14], (TextView) objArr[15], (TextView) objArr[6], (TextView) objArr[4], (TextView) objArr[3], (VideoView) objArr[1]);
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
        setViewModel((VideoViewModel) obj);
        return true;
    }

    @Override // com.autolink.dvr.databinding.ActivityVideoPlayBinding
    public void setViewModel(VideoViewModel videoViewModel) {
        this.mViewModel = videoViewModel;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
