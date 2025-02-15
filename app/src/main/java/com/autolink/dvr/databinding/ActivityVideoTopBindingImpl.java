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
public class ActivityVideoTopBindingImpl extends ActivityVideoTopBinding {
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
        sparseIntArray.put(C0903R.id.activity_video_top_back, 1);
        sparseIntArray.put(C0903R.id.activity_video_top_back_iv, 2);
        sparseIntArray.put(C0903R.id.activity_video_top_filename, 3);
        sparseIntArray.put(C0903R.id.activity_video_top_filename_tv, 4);
        sparseIntArray.put(C0903R.id.activity_main_top_separation, 5);
        sparseIntArray.put(C0903R.id.activity_video_top_delete, 6);
        sparseIntArray.put(C0903R.id.activity_video_top_delete_iv, 7);
    }

    public ActivityVideoTopBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 8, sIncludes, sViewsWithIds));
    }

    private ActivityVideoTopBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (RelativeLayout) objArr[5], (RelativeLayout) objArr[1], (ImageView) objArr[2], (RelativeLayout) objArr[6], (ImageView) objArr[7], (RelativeLayout) objArr[3], (TextView) objArr[4]);
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

    @Override // com.autolink.dvr.databinding.ActivityVideoTopBinding
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
