package com.autolink.dvr.common.base.hmi;

import android.app.Service;

/* loaded from: classes.dex */
public abstract class BaseService extends Service {
    private boolean mIsDialogShow = false;
    private String mServiceName = getClass().getSimpleName() + getClass().hashCode();

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }
}
