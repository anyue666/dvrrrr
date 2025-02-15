package com.autolink.dvr.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.viewmodel.VideoViewModel;

/* loaded from: classes.dex */
public class ActivityVideoBindingImpl extends ActivityVideoBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(5);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(1, new String[]{"activity_video_control"}, new int[]{2}, new int[]{C0903R.layout.activity_video_control});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0903R.id.activity_video_content, 3);
        sparseIntArray.put(C0903R.id.activity_video_coverview, 4);
    }

    public ActivityVideoBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 5, sIncludes, sViewsWithIds));
    }

    private ActivityVideoBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 1, (View) objArr[3], (ActivityVideoControlBinding) objArr[2], (View) objArr[4], (LinearLayout) objArr[1]);
        this.mDirtyFlags = -1L;
        setContainedBinding(this.activityVideoControl);
        this.activityVideoLlControl.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
        }
        this.activityVideoControl.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.activityVideoControl.hasPendingBindings();
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

    @Override // com.autolink.dvr.databinding.ActivityVideoBinding
    public void setViewModel(VideoViewModel videoViewModel) {
        this.mViewModel = videoViewModel;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(2);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.activityVideoControl.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        if (i != 0) {
            return false;
        }
        return onChangeActivityVideoControl((ActivityVideoControlBinding) obj, i2);
    }

    private boolean onChangeActivityVideoControl(ActivityVideoControlBinding activityVideoControlBinding, int i) {
        if (i != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
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
        VideoViewModel videoViewModel = this.mViewModel;
        if ((j & 6) != 0) {
            this.activityVideoControl.setViewModelChild(videoViewModel);
        }
        executeBindingsOn(this.activityVideoControl);
    }
}
