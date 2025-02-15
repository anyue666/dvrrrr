package com.autolink.dvr.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.model.FileNormalListResult;
import com.autolink.dvr.viewmodel.FileViewModel;

/* loaded from: classes.dex */
public abstract class ItemListEventBinding extends ViewDataBinding {
    public final CardView imgCard;
    public final ImageView itemListNormalBackgroundIv;
    public final CheckBox itemListNormalCheckbox;
    public final TextView itemListNormalDateTv;
    public final TextView itemListNormalNameTv;

    @Bindable
    protected FileNormalListResult.Item mResultItem;

    @Bindable
    protected FileViewModel mViewModel;

    public abstract void setResultItem(FileNormalListResult.Item item);

    public abstract void setViewModel(FileViewModel fileViewModel);

    protected ItemListEventBinding(Object obj, View view, int i, CardView cardView, ImageView imageView, CheckBox checkBox, TextView textView, TextView textView2) {
        super(obj, view, i);
        this.imgCard = cardView;
        this.itemListNormalBackgroundIv = imageView;
        this.itemListNormalCheckbox = checkBox;
        this.itemListNormalDateTv = textView;
        this.itemListNormalNameTv = textView2;
    }

    public FileNormalListResult.Item getResultItem() {
        return this.mResultItem;
    }

    public FileViewModel getViewModel() {
        return this.mViewModel;
    }

    public static ItemListEventBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemListEventBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemListEventBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.item_list_event, viewGroup, z, obj);
    }

    public static ItemListEventBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemListEventBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemListEventBinding) ViewDataBinding.inflateInternal(layoutInflater, C0903R.layout.item_list_event, null, false, obj);
    }

    public static ItemListEventBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemListEventBinding bind(View view, Object obj) {
        return (ItemListEventBinding) bind(obj, view, C0903R.layout.item_list_event);
    }
}
