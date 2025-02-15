package com.autolink.dvr.common.base.hmi;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class UsecaseObservableBase<T, V> {
    Disposable mDisposable;
    public ObservableEmitter<V> mEmitter;
    public ObserverCallback mObserverCallback;
    private V mResult;
    private ArrayList mObserverCallbacks = new ArrayList();
    Observer<V> mObserver = new Observer<V>() { // from class: com.autolink.dvr.common.base.hmi.UsecaseObservableBase.1
        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            UsecaseObservableBase.this.mDisposable = disposable;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(V v) {
            if (UsecaseObservableBase.this.mObserverCallback != null) {
                UsecaseObservableBase.this.mObserverCallback.ObserverResult(1, v);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (UsecaseObservableBase.this.mObserverCallback != null) {
                UsecaseObservableBase.this.mObserverCallback.ObserverResult(-1, null);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (UsecaseObservableBase.this.mObserverCallback != null) {
                UsecaseObservableBase.this.mObserverCallback.ObserverResult(0, null);
            }
        }
    };

    protected abstract boolean dowork(T t, V v);

    public void executeObservable(T t) {
    }

    public UsecaseObservableBase() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.autolink.dvr.common.base.hmi.UsecaseObservableBase$$ExternalSyntheticLambda0
            @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                UsecaseObservableBase.this.m220x88ea3338(observableEmitter);
            }
        }).subscribeOn(Schedulers.m528io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this.mObserver);
    }

    /* renamed from: lambda$new$0$com-autolink-dvr-common-base-hmi-UsecaseObservableBase */
    /* synthetic */ void m220x88ea3338(ObservableEmitter observableEmitter) throws Throwable {
        this.mEmitter = observableEmitter;
    }

    public void unregisterObservable(ObserverCallback<V> observerCallback) {
        if (observerCallback != null) {
            this.mObserverCallbacks.remove(observerCallback);
        }
        if (this.mObserverCallbacks.size() == 0) {
            ObservableEmitter<V> observableEmitter = this.mEmitter;
            if (observableEmitter != null) {
                observableEmitter.onComplete();
            }
            Disposable disposable = this.mDisposable;
            if (disposable != null) {
                disposable.dispose();
            }
        }
    }

    public void registerObservable(ObserverCallback<V> observerCallback) {
        if (observerCallback != null) {
            if (!this.mObserverCallbacks.contains(observerCallback)) {
                this.mObserverCallbacks.add(observerCallback);
            }
            this.mObserverCallback = observerCallback;
        }
    }
}
