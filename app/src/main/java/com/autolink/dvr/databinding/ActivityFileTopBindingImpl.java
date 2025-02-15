package com.autolink.dvr.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.viewmodel.FileViewModel;
import com.google.android.material.tabs.TabLayout;

/* loaded from: classes.dex */
public class ActivityFileTopBindingImpl extends ActivityFileTopBinding {
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
        sparseIntArray.put(C0903R.id.activity_file_top_back_iv, 1);
        sparseIntArray.put(C0903R.id.activity_file_top_tb, 2);
        sparseIntArray.put(C0903R.id.activity_file_top_multiple_iv, 3);
        sparseIntArray.put(C0903R.id.activity_file_top_delete_iv, 4);
        sparseIntArray.put(C0903R.id.activity_file_top_more_iv, 5);
    }

    public ActivityFileTopBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 6, sIncludes, sViewsWithIds));
    }

    private ActivityFileTopBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ImageView) objArr[1], (ImageView) objArr[4], (ImageView) objArr[5], (ImageView) objArr[3], (TabLayout) objArr[2]);
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
        if (3 != i) {
            return false;
        }
        setViewModelChild((FileViewModel) obj);
        return true;
    }

    @Override // com.autolink.dvr.databinding.ActivityFileTopBinding
    public void setViewModelChild(FileViewModel fileViewModel) {
        this.mViewModelChild = fileViewModel;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
