package com.autolink.dvr.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.model.FileNormalListResult;
import com.autolink.dvr.viewmodel.FileViewModel;

/* loaded from: classes.dex */
public class ItemListEventBindingImpl extends ItemListEventBinding {
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
        sparseIntArray.put(C0903R.id.imgCard, 3);
        sparseIntArray.put(C0903R.id.item_list_normal_background_iv, 4);
        sparseIntArray.put(C0903R.id.item_list_normal_checkbox, 5);
    }

    public ItemListEventBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 6, sIncludes, sViewsWithIds));
    }

    private ItemListEventBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (CardView) objArr[3], (ImageView) objArr[4], (CheckBox) objArr[5], (TextView) objArr[1], (TextView) objArr[2]);
        this.mDirtyFlags = -1L;
        this.itemListNormalDateTv.setTag(null);
        this.itemListNormalNameTv.setTag(null);
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
        if (1 == i) {
            setResultItem((FileNormalListResult.Item) obj);
            return true;
        }
        if (2 != i) {
            return false;
        }
        setViewModel((FileViewModel) obj);
        return true;
    }

    @Override // com.autolink.dvr.databinding.ItemListEventBinding
    public void setResultItem(FileNormalListResult.Item item) {
        this.mResultItem = item;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(1);
        super.requestRebind();
    }

    @Override // com.autolink.dvr.databinding.ItemListEventBinding
    public void setViewModel(FileViewModel fileViewModel) {
        this.mViewModel = fileViewModel;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        FileNormalListResult.Item item = this.mResultItem;
        long j2 = j & 5;
        String str2 = null;
        if (j2 == 0 || item == null) {
            str = null;
        } else {
            str2 = item.getDate();
            str = item.getName();
        }
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.itemListNormalDateTv, str2);
            TextViewBindingAdapter.setText(this.itemListNormalNameTv, str);
        }
    }
}
