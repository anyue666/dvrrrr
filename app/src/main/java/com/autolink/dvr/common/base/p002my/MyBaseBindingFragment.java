package com.autolink.dvr.common.base.p002my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.common.utils.LogUtils2;

/* loaded from: classes.dex */
public abstract class MyBaseBindingFragment<V extends ViewDataBinding> extends MyBaseFragment {
    private static final String TAG = LogUtils2.DEFAULT_TAG + "MyBaseBindingFragment";
    protected V mBinding;

    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override // com.autolink.dvr.common.base.p002my.MyBaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        LogUtils2.logV(TAG, "[onCreateView]");
        if (getLayoutId() == 0) {
            throw new RuntimeException("getLayout() must be not null");
        }
        V v = (V) DataBindingUtil.inflate(layoutInflater, getLayoutId(), viewGroup, false);
        this.mBinding = v;
        v.setLifecycleOwner(this);
        this.mBinding.executePendingBindings();
        initView();
        return this.mBinding.getRoot();
    }

    public V getBinding() {
        return this.mBinding;
    }
}
