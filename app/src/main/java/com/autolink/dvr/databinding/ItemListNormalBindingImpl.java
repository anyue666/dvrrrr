package com.autolink.dvr.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.model.FileNormalListResult;
import com.autolink.dvr.p003ui.view.DvrCheckBox;
import com.autolink.dvr.p003ui.view.DvrImageView;
import com.autolink.dvr.p003ui.view.FileItemBgView;
import com.autolink.dvr.p003ui.view.ItemConstraintLayout;
import com.autolink.dvr.viewmodel.FileViewModel;

/* loaded from: classes.dex */
public class ItemListNormalBindingImpl extends ItemListNormalBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0903R.id.title, 2);
        sparseIntArray.put(C0903R.id.rl_pic_layout, 3);
        sparseIntArray.put(C0903R.id.imgCard, 4);
        sparseIntArray.put(C0903R.id.item_list_normal_background_iv, 5);
        sparseIntArray.put(C0903R.id.view_select, 6);
        sparseIntArray.put(C0903R.id.item_list_normal_date_tv, 7);
        sparseIntArray.put(C0903R.id.item_list_normal_checkbox, 8);
    }

    public ItemListNormalBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 9, sIncludes, sViewsWithIds));
    }

    private ItemListNormalBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (CardView) objArr[4], (ItemConstraintLayout) objArr[0], (DvrImageView) objArr[5], (DvrCheckBox) objArr[8], (TextView) objArr[7], (TextView) objArr[1], (RelativeLayout) objArr[3], (TextView) objArr[2], (FileItemBgView) objArr[6]);
        this.mDirtyFlags = -1L;
        this.itemLayout.setTag(null);
        this.itemListNormalNameTv.setTag(null);
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

    @Override // com.autolink.dvr.databinding.ItemListNormalBinding
    public void setResultItem(FileNormalListResult.Item item) {
        this.mResultItem = item;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(1);
        super.requestRebind();
    }

    @Override // com.autolink.dvr.databinding.ItemListNormalBinding
    public void setViewModel(FileViewModel fileViewModel) {
        this.mViewModel = fileViewModel;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        FileNormalListResult.Item item = this.mResultItem;
        String str = null;
        long j2 = j & 5;
        if (j2 != 0 && item != null) {
            str = item.getName();
        }
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.itemListNormalNameTv, str);
        }
    }
}
