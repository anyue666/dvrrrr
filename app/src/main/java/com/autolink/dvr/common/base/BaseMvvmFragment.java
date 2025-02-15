package com.autolink.dvr.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import com.autolink.dvr.common.base.hmi.BaseFragment;
import com.autolink.dvr.common.base.hmi.BaseViewModel;
import com.autolink.dvr.common.utils.LogUtils;
import com.autolink.dvr.common.utils.LogUtils2;

/* loaded from: classes.dex */
public abstract class BaseMvvmFragment<VM extends BaseViewModel, V extends ViewDataBinding> extends BaseFragment<VM> {
    protected FragmentActivity mActivity;
    protected V mBinding;
    protected View rootView;
    private final String TAG = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();
    public boolean isEditState = false;
    public boolean isDisplayMode = true;
    public boolean isLongPress = false;

    protected abstract int getLayoutId();

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LogUtils2.logI(this.TAG, "onCreate");
        this.mActivity = getActivity();
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.isEditState = false;
        this.isDisplayMode = true;
        this.isLongPress = false;
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        LogUtils2.logI(this.TAG, "onCreateView");
        if (getLayoutId() == 0) {
            throw new RuntimeException("getLayout() must be not null");
        }
        V v = (V) DataBindingUtil.inflate(layoutInflater, getLayoutId(), viewGroup, false);
        this.mBinding = v;
        v.setLifecycleOwner(this);
        this.mBinding.executePendingBindings();
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.rootView = onCreateView;
        return onCreateView;
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseFragment
    public int provideContentView() {
        return getLayoutId();
    }

    protected <T extends View> T findViewById(int i) {
        View view = this.rootView;
        if (view == null) {
            return null;
        }
        return (T) view.findViewById(i);
    }

    public void startActivity(Class cls) {
        LogUtils.getInstance().m222d(this.TAG, "startActivity Class=" + cls.getSimpleName());
        startActivity(new Intent(this.mActivity, (Class<?>) cls));
    }

    public void startActivity(Class cls, Bundle bundle) {
        LogUtils.getInstance().m222d(this.TAG, "startActivity Class=" + cls.getSimpleName());
        Intent intent = new Intent(this.mActivity, (Class<?>) cls);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    public boolean isIsEditState() {
        return this.isEditState;
    }

    public boolean isIsDisplayMode() {
        return this.isDisplayMode;
    }

    public boolean isIsLongPress() {
        return this.isLongPress;
    }

    public void startActivityForResult(Class cls, Bundle bundle, int i) {
        LogUtils.getInstance().m222d(this.TAG, "startActivityForResult Class=" + cls.getSimpleName());
        Intent intent = new Intent(this.mActivity, (Class<?>) cls);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, i);
    }
}
