package com.autolink.dvr.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.common.base.hmi.BaseActivity;
import com.autolink.dvr.common.base.hmi.BaseViewModel;

/* loaded from: classes.dex */
public abstract class BaseMvvmActivity<Vm extends BaseViewModel, V extends ViewDataBinding> extends BaseActivity<Vm> {
    protected V mBinding;

    protected abstract int getLayoutId();

    @Override // com.autolink.dvr.common.base.hmi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseActivity
    public View provideContentRootView() {
        if (getLayoutId() == 0) {
            throw new RuntimeException("getLayout() must be not null");
        }
        V v = (V) DataBindingUtil.setContentView(this, getLayoutId());
        this.mBinding = v;
        v.setLifecycleOwner(this);
        this.mBinding.executePendingBindings();
        return this.mBinding.getRoot();
    }

    /* renamed from: $ */
    protected <T extends View> T m219$(int i) {
        return (T) super.findViewById(i);
    }

    public void startActivity(Class cls) {
        startActivity(new Intent(this, (Class<?>) cls));
    }

    public void startActivity(Class cls, int i, int i2) {
        startActivity(new Intent(this, (Class<?>) cls));
        overridePendingTransition(i, i2);
    }

    public void startActivity(Class cls, int i, int i2, Bundle bundle) {
        Intent intent = new Intent(this, (Class<?>) cls);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(i, i2);
    }
}
