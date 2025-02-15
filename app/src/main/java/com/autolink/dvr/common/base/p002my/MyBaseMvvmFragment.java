package com.autolink.dvr.common.base.p002my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.autolink.dvr.common.base.p002my.MyBaseViewModel;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public abstract class MyBaseMvvmFragment<Vm extends MyBaseViewModel, V extends ViewDataBinding> extends MyBaseBindingFragment<V> {
    protected Vm mViewModel;

    protected abstract Object getViewModelOrFactory();

    protected abstract int getViewModelVariable();

    protected abstract void initObservable(Vm vm);

    protected abstract void loadData(Vm vm);

    @Override // com.autolink.dvr.common.base.p002my.MyBaseBindingFragment, com.autolink.dvr.common.base.p002my.MyBaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        initViewModel();
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        initObservable(this.mViewModel);
        if (getViewModelVariable() != 0) {
            this.mBinding.setVariable(getViewModelVariable(), this.mViewModel);
        }
        return onCreateView;
    }

    @Override // com.autolink.dvr.common.base.p002my.MyBaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        loadData(getViewModel());
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
        if (viewModelOrFactory instanceof ViewModel) {
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
