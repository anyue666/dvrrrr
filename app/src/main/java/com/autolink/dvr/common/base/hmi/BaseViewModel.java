package com.autolink.dvr.common.base.hmi;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import com.autolink.dvr.common.utils.CompositeDisposableManager;

/* loaded from: classes.dex */
public class BaseViewModel extends ViewModel implements IBaseViewModel {
    private CompositeDisposableManager mCompositeDisposableManager = new CompositeDisposableManager();

    @Override // com.autolink.dvr.common.base.hmi.IBaseViewModel
    public void onAny(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
    }

    @Override // com.autolink.dvr.common.base.hmi.IBaseViewModel
    public void onCreate() {
    }

    @Override // com.autolink.dvr.common.base.hmi.IBaseViewModel
    public void onDestroy() {
    }

    @Override // com.autolink.dvr.common.base.hmi.IBaseViewModel
    public void onPause() {
    }

    @Override // com.autolink.dvr.common.base.hmi.IBaseViewModel
    public void onResume() {
    }

    @Override // com.autolink.dvr.common.base.hmi.IBaseViewModel
    public void onStart() {
    }

    @Override // com.autolink.dvr.common.base.hmi.IBaseViewModel
    public void onStop() {
    }

    @Override // androidx.lifecycle.ViewModel
    protected void onCleared() {
        super.onCleared();
        this.mCompositeDisposableManager.onCleared();
    }
}
