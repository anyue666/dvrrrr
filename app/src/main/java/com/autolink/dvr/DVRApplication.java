package com.autolink.dvr;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.autolink.dvr.common.base.hmi.BaseApplication;
import com.autolink.dvr.common.can.CanOperation;
import com.autolink.dvr.common.service.DVRService;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.SaveMsg;

/* loaded from: classes.dex */
public class DVRApplication extends BaseApplication {
    public static final String DVR_PROPERTIES = "persist.vendor.oem.cfg.cc.ihu.dvr";
    private static DVRApplication instance;
    private final String TAG = "DVR_DVRApplication";
    private CanOperation mCanOperation;

    public static DVRApplication getInstance() {
        return instance;
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseApplication, android.app.Application
    public void onCreate() {
        super.onCreate();
        LogUtils2.init(this);
        LogUtils2.logI("DVR_DVRApplication", "onCreate ");
        getVersion();
        instance = this;
        if (SaveMsg.getProperties().equals("0")) {
            LogUtils2.logI("DVR_DVRApplication", "onCreate  return");
        } else {
            startService(new Intent(this, (Class<?>) DVRService.class));
            this.mCanOperation = CanOperation.getInstance();
        }
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseApplication, android.app.Application
    public void onTerminate() {
        super.onTerminate();
        this.mCanOperation.onDestroy();
    }

    private void getVersion() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            LogUtils2.logI("DVR_DVRApplication", "versionCode = " + packageInfo.versionCode + " , versionName = " + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
