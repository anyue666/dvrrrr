package com.autolink.dvr.common.utils;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/* loaded from: classes.dex */
public class CompositeDisposableManager {
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public CompositeDisposable getmCompositeDisposable() {
        return this.mCompositeDisposable;
    }

    public void onCleared() {
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
