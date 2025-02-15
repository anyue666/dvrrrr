package com.autolink.dvr.common.base.p002my;

import android.os.Bundle;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import com.autolink.dvr.common.base.p002my.MyBaseViewModel;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public abstract class MyBaseMvvmActivity<Vm extends MyBaseViewModel, V extends ViewDataBinding> extends MyBaseBindingActivity<V> {
    protected Vm mViewModel;

    protected abstract Object getViewModelOrFactory();

    protected abstract int getViewModelVariable();

    protected abstract void initObservable(Vm vm);

    protected abstract void loadData(Vm vm);

    @Override // com.autolink.dvr.common.base.p002my.MyBaseBindingActivity, com.autolink.dvr.common.base.p002my.MyBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        initViewModel();
        super.onCreate(bundle);
        if (getViewModelVariable() != 0) {
            this.mBinding.setVariable(getViewModelVariable(), this.mViewModel);
        }
        this.mBinding.executePendingBindings();
        initObservable(this.mViewModel);
    }

    @Override // com.autolink.dvr.common.base.p002my.MyBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        loadData(this.mViewModel);
    }

    private void initViewModel() {
        Class<MyBaseViewModel> cls;
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            cls = (Class) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            cls = MyBaseViewModel.class;
        }
        Object viewModelOrFactory = getViewModelOrFactory();
        if (viewModelOrFactory instanceof MyBaseViewModel) {
            this.mViewModel = (Vm) viewModelOrFactory;
        } else if (viewModelOrFactory instanceof ViewModelProvider.Factory) {
            this.mViewModel = (Vm) new ViewModelProvider(this, (ViewModelProvider.Factory) viewModelOrFactory).get(cls);
        } else {
            this.mViewModel = (Vm) new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(cls);
        }
    }

    protected Vm getViewModel() {
        return this.mViewModel;
    }
}
