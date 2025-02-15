package com.autolink.dvr.common.base;

import com.autolink.dvr.common.base.hmi.BaseViewModel;
import com.autolink.dvr.common.base.p002my.MyBaseRepository;
import com.autolink.dvr.common.utils.LogUtils;

/* loaded from: classes.dex */
public abstract class BaseMvvmViewModel<M extends MyBaseRepository> extends BaseViewModel {
    private final String TAG = LogUtils.DEFAULT_TAG + getClass().getSimpleName();
    protected M mRepository;

    public BaseMvvmViewModel(M m) {
        this.mRepository = m;
    }

    public M getRepository() {
        return this.mRepository;
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseViewModel, androidx.lifecycle.ViewModel
    protected void onCleared() {
        super.onCleared();
        LogUtils.getInstance().m222d(this.TAG, "[onCleared]");
    }
}
