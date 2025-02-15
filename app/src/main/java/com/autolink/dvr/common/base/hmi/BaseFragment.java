package com.autolink.dvr.common.base.hmi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.autolink.dvr.common.base.hmi.BaseViewModel;
import com.autolink.dvr.common.utils.LogUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment implements IBaseView, ViewTreeObserver.OnWindowFocusChangeListener {
    private static final String TAG = "BaseFragment";
    protected Unbinder mUnbinder;
    protected VM mViewModel;
    private String mPageName = getClass().getSimpleName() + getClass().hashCode();
    private View mView = null;
    protected boolean mCurrentPageVtsCapabilityState = false;
    private boolean mCurrentPageFocus = false;
    protected ViewTreeObserver mViewTreeObserver = null;

    @Override // com.autolink.dvr.common.base.hmi.IBaseView
    public abstract void initView();

    public VM initViewModel() {
        return null;
    }

    @Override // com.autolink.dvr.common.base.hmi.IBaseView
    public abstract void initViewObservable();

    public abstract int provideContentView();

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Class<BaseViewModel> cls;
        View inflate = layoutInflater.inflate(provideContentView(), viewGroup, false);
        this.mUnbinder = ButterKnife.bind(this, inflate);
        if (this.mViewModel == null) {
            VM initViewModel = initViewModel();
            this.mViewModel = initViewModel;
            if (initViewModel == null) {
                Type genericSuperclass = getClass().getGenericSuperclass();
                if (genericSuperclass instanceof ParameterizedType) {
                    cls = (Class) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
                } else {
                    cls = BaseViewModel.class;
                }
                this.mViewModel = (VM) createViewModel(this, cls);
            }
            getLifecycle().addObserver(this.mViewModel);
        }
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView();
        initViewObservable();
        this.mView = view;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        LogUtils.getInstance().m229i(TAG, "onResume:" + this.mPageName, new Object[0]);
        super.onResume();
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        LogUtils.getInstance().m229i(TAG, "onHiddenChanged:" + z, new Object[0]);
        super.onHiddenChanged(z);
    }

    @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
    public void onWindowFocusChanged(boolean z) {
        LogUtils.getInstance().m229i(TAG, "onWindowFocusChanged:" + z, new Object[0]);
        this.mCurrentPageFocus = z;
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        LogUtils.getInstance().m229i(TAG, "onPause:" + this.mPageName, new Object[0]);
        super.onPause();
    }

    public VM getViewModel() {
        return this.mViewModel;
    }

    private <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return (T) new ViewModelProvider(fragment).get(cls);
    }

    public void setmPageName(String str) {
        this.mPageName = str;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        ViewTreeObserver viewTreeObserver = this.mViewTreeObserver;
        if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
            this.mViewTreeObserver.removeOnWindowFocusChangeListener(this);
            this.mViewTreeObserver = null;
        }
        if (this.mView != null) {
            this.mView = null;
        }
        super.onDestroyView();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        LogUtils.getInstance().m229i(TAG, "onDestroy:", new Object[0]);
        super.onDestroy();
        Unbinder unbinder = this.mUnbinder;
        if (unbinder != null) {
            unbinder.unbind();
            this.mUnbinder = null;
        }
        if (this.mViewModel != null) {
            this.mViewModel = null;
        }
    }
}
