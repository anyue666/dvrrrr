package com.autolink.dvr.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.p003ui.view.CrossConstraintLayout;
import com.autolink.dvr.p003ui.view.DvrViewPager;
import com.autolink.dvr.viewmodel.FileViewModel;

/* loaded from: classes.dex */
public class ActivityFileBindingImpl extends ActivityFileBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final CrossConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(12);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"activity_file_top"}, new int[]{1}, new int[]{C0903R.layout.activity_file_top});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0903R.id.activity_file_content_tv, 2);
        sparseIntArray.put(C0903R.id.activity_file_content_vp, 3);
        sparseIntArray.put(C0903R.id.tips_layout_bg, 4);
        sparseIntArray.put(C0903R.id.top_tips_window, 5);
        sparseIntArray.put(C0903R.id.view_switch_layout, 6);
        sparseIntArray.put(C0903R.id.tips_point_1, 7);
        sparseIntArray.put(C0903R.id.tips_view, 8);
        sparseIntArray.put(C0903R.id.list_switch_layout, 9);
        sparseIntArray.put(C0903R.id.tips_point_2, 10);
        sparseIntArray.put(C0903R.id.tips_list, 11);
    }

    public ActivityFileBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 12, sIncludes, sViewsWithIds));
    }

    private ActivityFileBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 1, (TextView) objArr[2], (DvrViewPager) objArr[3], (ActivityFileTopBinding) objArr[1], (ConstraintLayout) objArr[9], (ConstraintLayout) objArr[4], (TextView) objArr[11], (View) objArr[7], (View) objArr[10], (TextView) objArr[8], (ConstraintLayout) objArr[5], (ConstraintLayout) objArr[6]);
        this.mDirtyFlags = -1L;
        setContainedBinding(this.activityFileLlTop);
        CrossConstraintLayout crossConstraintLayout = (CrossConstraintLayout) objArr[0];
        this.mboundView0 = crossConstraintLayout;
        crossConstraintLayout.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
        }
        this.activityFileLlTop.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.activityFileLlTop.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        if (2 != i) {
            return false;
        }
        setViewModel((FileViewModel) obj);
        return true;
    }

    @Override // com.autolink.dvr.databinding.ActivityFileBinding
    public void setViewModel(FileViewModel fileViewModel) {
        this.mViewModel = fileViewModel;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(2);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.activityFileLlTop.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        if (i != 0) {
            return false;
        }
        return onChangeActivityFileLlTop((ActivityFileTopBinding) obj, i2);
    }

    private boolean onChangeActivityFileLlTop(ActivityFileTopBinding activityFileTopBinding, int i) {
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
        FileViewModel fileViewModel = this.mViewModel;
        if ((j & 6) != 0) {
            this.activityFileLlTop.setViewModelChild(fileViewModel);
        }
        executeBindingsOn(this.activityFileLlTop);
    }
}
