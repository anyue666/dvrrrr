package com.autolink.dvr.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.viewmodel.VideoViewModel;

/* loaded from: classes.dex */
public class ActivityVideoControlBindingImpl extends ActivityVideoControlBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final LinearLayout mboundView1;
    private final LinearLayout mboundView2;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(6);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(1, new String[]{"activity_video_top"}, new int[]{3}, new int[]{C0903R.layout.activity_video_top});
        includedLayouts.setIncludes(2, new String[]{"activity_video_bottom"}, new int[]{4}, new int[]{C0903R.layout.activity_video_bottom});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0903R.id.activity_video_control_play, 5);
    }

    public ActivityVideoControlBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 6, sIncludes, sViewsWithIds));
    }

    private ActivityVideoControlBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 2, (ActivityVideoBottomBinding) objArr[4], (ImageView) objArr[5], (ActivityVideoTopBinding) objArr[3]);
        this.mDirtyFlags = -1L;
        setContainedBinding(this.activityVideoBottom);
        setContainedBinding(this.activityVideoTop);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        LinearLayout linearLayout = (LinearLayout) objArr[1];
        this.mboundView1 = linearLayout;
        linearLayout.setTag(null);
        LinearLayout linearLayout2 = (LinearLayout) objArr[2];
        this.mboundView2 = linearLayout2;
        linearLayout2.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
        }
        this.activityVideoTop.invalidateAll();
        this.activityVideoBottom.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.activityVideoTop.hasPendingBindings() || this.activityVideoBottom.hasPendingBindings();
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

    @Override // com.autolink.dvr.databinding.ActivityVideoControlBinding
    public void setViewModelChild(VideoViewModel videoViewModel) {
        this.mViewModelChild = videoViewModel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(3);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.activityVideoTop.setLifecycleOwner(lifecycleOwner);
        this.activityVideoBottom.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        if (i == 0) {
            return onChangeActivityVideoBottom((ActivityVideoBottomBinding) obj, i2);
        }
        if (i != 1) {
            return false;
        }
        return onChangeActivityVideoTop((ActivityVideoTopBinding) obj, i2);
    }

    private boolean onChangeActivityVideoBottom(ActivityVideoBottomBinding activityVideoBottomBinding, int i) {
        if (i != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeActivityVideoTop(ActivityVideoTopBinding activityVideoTopBinding, int i) {
        if (i != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        VideoViewModel videoViewModel = this.mViewModelChild;
        if ((j & 12) != 0) {
            this.activityVideoBottom.setViewModelChild(videoViewModel);
            this.activityVideoTop.setViewModelChild(videoViewModel);
        }
        executeBindingsOn(this.activityVideoTop);
        executeBindingsOn(this.activityVideoBottom);
    }
}
