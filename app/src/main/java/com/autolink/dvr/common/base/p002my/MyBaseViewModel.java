package com.autolink.dvr.common.base.p002my;

import androidx.lifecycle.ViewModel;
import com.autolink.dvr.common.base.p002my.MyBaseRepository;
import com.autolink.dvr.common.utils.LogUtils2;

/* loaded from: classes.dex */
public abstract class MyBaseViewModel<M extends MyBaseRepository> extends ViewModel {
    private final String TAG = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();
    protected M mRepository;

    public MyBaseViewModel(M m) {
        this.mRepository = m;
    }

    public M getRepository() {
        return this.mRepository;
    }

    @Override // androidx.lifecycle.ViewModel
    protected void onCleared() {
        super.onCleared();
        LogUtils2.logV(this.TAG, "[onCleared]");
    }
}
