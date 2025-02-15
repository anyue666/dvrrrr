package com.autolink.dvr.common.base.hmi;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes.dex */
public abstract class UsecaseCompliteBase<T, V> {
    private CompletableCallback<V> mCompletableCallback;
    Disposable mDisposable;
    private V mResult;

    abstract boolean dowork(T t, V v);

    public void registerObservable(CompletableCallback<V> completableCallback) {
        if (completableCallback == null || this.mCompletableCallback != null) {
            return;
        }
        this.mCompletableCallback = completableCallback;
    }

    public void unregisterObservable(CompletableCallback<V> completableCallback) {
        if (completableCallback == null || this.mCompletableCallback != completableCallback) {
            return;
        }
        this.mCompletableCallback = null;
        Disposable disposable = this.mDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void executeCompletable(final T t, Scheduler scheduler, Scheduler scheduler2, Observer observer) {
        Completable.create(new CompletableOnSubscribe() { // from class: com.autolink.dvr.common.base.hmi.UsecaseCompliteBase.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // io.reactivex.rxjava3.core.CompletableOnSubscribe
            public void subscribe(CompletableEmitter completableEmitter) throws Throwable {
                UsecaseCompliteBase usecaseCompliteBase = UsecaseCompliteBase.this;
                if (usecaseCompliteBase.dowork(t, usecaseCompliteBase.mResult)) {
                    completableEmitter.onComplete();
                } else {
                    completableEmitter.onError(new Throwable(""));
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() { // from class: com.autolink.dvr.common.base.hmi.UsecaseCompliteBase.1
            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                UsecaseCompliteBase.this.mDisposable = disposable;
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                if (UsecaseCompliteBase.this.mCompletableCallback != null) {
                    UsecaseCompliteBase.this.mCompletableCallback.CompletableResult(0, null);
                }
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable th) {
                if (UsecaseCompliteBase.this.mCompletableCallback != null) {
                    UsecaseCompliteBase.this.mCompletableCallback.CompletableResult(-1, null);
                }
            }
        });
    }
}
