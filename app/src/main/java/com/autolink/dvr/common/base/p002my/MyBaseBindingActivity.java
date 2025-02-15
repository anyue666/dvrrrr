package com.autolink.dvr.common.base.p002my;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/* loaded from: classes.dex */
public abstract class MyBaseBindingActivity<V extends ViewDataBinding> extends MyBaseActivity {
    protected V mBinding;

    protected abstract int getLayoutId();

    @Override // com.autolink.dvr.common.base.p002my.MyBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getLayoutId() == 0) {
            throw new RuntimeException("getLayout() must be not null");
        }
        V v = (V) DataBindingUtil.setContentView(this, getLayoutId());
        this.mBinding = v;
        v.setLifecycleOwner(this);
        this.mBinding.executePendingBindings();
    }

    public V getBinding() {
        return this.mBinding;
    }
}
