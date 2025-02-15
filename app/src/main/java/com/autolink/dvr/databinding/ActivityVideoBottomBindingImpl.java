package com.autolink.dvr.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.viewmodel.VideoViewModel;

/* loaded from: classes.dex */
public class ActivityVideoBottomBindingImpl extends ActivityVideoBottomBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0903R.id.activity_video_bottom_previous, 1);
        sparseIntArray.put(C0903R.id.activity_video_bottom_previous_iv, 2);
        sparseIntArray.put(C0903R.id.activity_video_bottom_play, 3);
        sparseIntArray.put(C0903R.id.activity_video_bottom_play_iv, 4);
        sparseIntArray.put(C0903R.id.activity_video_bottom_next, 5);
        sparseIntArray.put(C0903R.id.activity_video_bottom_next_iv, 6);
        sparseIntArray.put(C0903R.id.activity_video_bottom_time, 7);
        sparseIntArray.put(C0903R.id.activity_video_bottom_time_iv, 8);
        sparseIntArray.put(C0903R.id.activity_video_bottom_separation, 9);
        sparseIntArray.put(C0903R.id.activity_video_bottom_volume, 10);
        sparseIntArray.put(C0903R.id.activity_video_bottom_volume_iv, 11);
        sparseIntArray.put(C0903R.id.activity_video_bottom_shrink, 12);
        sparseIntArray.put(C0903R.id.activity_video_bottom_shrink_iv, 13);
    }

    public ActivityVideoBottomBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 14, sIncludes, sViewsWithIds));
    }

    private ActivityVideoBottomBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (RelativeLayout) objArr[5], (ImageView) objArr[6], (RelativeLayout) objArr[3], (ImageView) objArr[4], (RelativeLayout) objArr[1], (ImageView) objArr[2], (RelativeLayout) objArr[9], (RelativeLayout) objArr[12], (ImageView) objArr[13], (RelativeLayout) objArr[7], (TextView) objArr[8], (RelativeLayout) objArr[10], (ImageView) objArr[11]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) objArr[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
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
        if (3 != i) {
            return false;
        }
        setViewModelChild((VideoViewModel) obj);
        return true;
    }

    @Override // com.autolink.dvr.databinding.ActivityVideoBottomBinding
    public void setViewModelChild(VideoViewModel videoViewModel) {
        this.mViewModelChild = videoViewModel;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
