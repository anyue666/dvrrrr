package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.model.FileNormalListResult;
import com.autolink.dvr.p003ui.view.DvrCheckBox;
import com.autolink.dvr.p003ui.view.DvrImageView;
import com.autolink.dvr.p003ui.view.FileItemBgView;
import com.autolink.dvr.p003ui.view.ItemConstraintLayout;
import com.autolink.dvr.viewmodel.FileViewModel;

/* loaded from: classes.dex */
public abstract class ItemListNormalBinding extends ViewDataBinding {
    public final CardView imgCard;
    public final ItemConstraintLayout itemLayout;
    public final DvrImageView itemListNormalBackgroundIv;
    public final DvrCheckBox itemListNormalCheckbox;
    public final TextView itemListNormalDateTv;
    public final TextView itemListNormalNameTv;

    @Bindable
    protected FileNormalListResult.Item mResultItem;

    @Bindable
    protected FileViewModel mViewModel;
    public final RelativeLayout rlPicLayout;
    public final TextView title;
    public final FileItemBgView viewSelect;

    public abstract void setResultItem(FileNormalListResult.Item item);

    public abstract void setViewModel(FileViewModel fileViewModel);

    protected ItemListNormalBinding(Object obj, View view, int i, CardView cardView, ItemConstraintLayout itemConstraintLayout, DvrImageView dvrImageView, DvrCheckBox dvrCheckBox, TextView textView, TextView textView2, RelativeLayout relativeLayout, TextView textView3, FileItemBgView fileItemBgView) {
        super(obj, view, i);
        this.imgCard = cardView;
        this.itemLayout = itemConstraintLayout;
        this.itemListNormalBackgroundIv = dvrImageView;
        this.itemListNormalCheckbox = dvrCheckBox;
        this.itemListNormalDateTv = textView;
        this.itemListNormalNameTv = textView2;
        this.rlPicLayout = relativeLayout;
        this.title = textView3;
        this.viewSelect = fileItemBgView;
    }

    public FileNormalListResult.Item getResultItem() {
        return this.mResultItem;
    }

    public FileViewModel getViewModel() {
        return this.mViewModel;
    }

    public static ItemListNormalBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemListNormalBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemListNormalBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.item_list_normal, viewGroup, z, obj);
    }

    public static ItemListNormalBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemListNormalBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemListNormalBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.item_list_normal, null, false, obj);
    }

    public static ItemListNormalBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemListNormalBinding bind(View view, Object obj) {
        return (ItemListNormalBinding) bind(obj, view, C0903R.layout.item_list_normal);
    }
}
