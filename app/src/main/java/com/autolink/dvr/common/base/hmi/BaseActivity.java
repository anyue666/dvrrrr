package com.autolink.dvr.common.base.hmi;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import butterknife.Unbinder;
import com.autolink.dvr.common.base.hmi.BaseViewModel;
import com.autolink.dvr.common.utils.LogUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity implements IBaseView {
    private static final String TAG = "BaseActivity";
    protected Unbinder mUnbinder;
    protected VM mViewModel;
    protected String mScreenName = null;
    private boolean mCurrentPageFocus = false;
    private String mPageName = getClass().getSimpleName() + getClass().hashCode();

    @Override // com.autolink.dvr.common.base.hmi.IBaseView
    public abstract void initView();

    public VM initViewModel() {
        return null;
    }

    @Override // com.autolink.dvr.common.base.hmi.IBaseView
    public abstract void initViewObservable();

    public abstract View provideContentRootView();

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        Class<BaseViewModel> cls;
        super.onCreate(bundle);
        setContentView(provideContentRootView());
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
            initView();
            initViewObservable();
        }
    }

    public VM getViewModel() {
        return this.mViewModel;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        LogUtils.getInstance().m229i(TAG, "onDestroy", new Object[0]);
        super.onDestroy();
        if (this.mViewModel != null) {
            this.mViewModel = null;
        }
    }

    private <T extends ViewModel> T createViewModel(FragmentActivity fragmentActivity, Class<T> cls) {
        return (T) new ViewModelProvider(fragmentActivity).get(cls);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        LogUtils.getInstance().m229i(TAG, "onResume:" + this.mPageName, new Object[0]);
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        LogUtils.getInstance().m229i(TAG, "onPause:" + this.mPageName, new Object[0]);
        super.onPause();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        LogUtils.getInstance().m229i(TAG, "onWindowFocusChanged:" + z, new Object[0]);
        super.onWindowFocusChanged(z);
        this.mCurrentPageFocus = z;
    }

    private void transparentStatusBar(Window window) {
        window.addFlags(Integer.MIN_VALUE);
        window.getDecorView().setSystemUiVisibility(1024);
        window.setStatusBarColor(0);
    }
}
