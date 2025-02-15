package com.autolink.dvr.common.base.hmi;

import android.util.Log;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.List;

/* loaded from: classes.dex */
public abstract class UsecaseSingleBase<T, V> {
    Disposable mDisposable;
    public V mResult;
    private SingleCallback<V> mSingleCallback;
    SingleObserver<V> mVSingleObserver = new SingleObserver<V>() { // from class: com.autolink.dvr.common.base.hmi.UsecaseSingleBase.1
        C09091() {
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            Log.d("dowork", "SingleObserver Disposable onSubscribe");
            UsecaseSingleBase.this.mDisposable = disposable;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(V v) {
            if (UsecaseSingleBase.this.mSingleCallback != null) {
                UsecaseSingleBase.this.mSingleCallback.SingleResult(1, v);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            if (UsecaseSingleBase.this.mSingleCallback != null) {
                UsecaseSingleBase.this.mSingleCallback.SingleResult(-1, null);
            }
        }
    };

    protected abstract void deleteFile(List<String> list) throws Exception;

    protected abstract boolean dowork(T t, V v);

    protected abstract V getResult(boolean z);

    public void unregisterObservable(SingleCallback<V> singleCallback) {
        if (singleCallback != null) {
            this.mSingleCallback = singleCallback;
        }
        Disposable disposable = this.mDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void registerObservable(SingleCallback<V> singleCallback) {
        if (singleCallback == null || this.mSingleCallback != null) {
            return;
        }
        this.mSingleCallback = singleCallback;
    }

    /* renamed from: com.autolink.dvr.common.base.hmi.UsecaseSingleBase$1 */
    class C09091 implements SingleObserver<V> {
        C09091() {
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            Log.d("dowork", "SingleObserver Disposable onSubscribe");
            UsecaseSingleBase.this.mDisposable = disposable;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(V v) {
            if (UsecaseSingleBase.this.mSingleCallback != null) {
                UsecaseSingleBase.this.mSingleCallback.SingleResult(1, v);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            if (UsecaseSingleBase.this.mSingleCallback != null) {
                UsecaseSingleBase.this.mSingleCallback.SingleResult(-1, null);
            }
        }
    }
}
