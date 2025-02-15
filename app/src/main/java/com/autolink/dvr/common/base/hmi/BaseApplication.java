package com.autolink.dvr.common.base.hmi;

import android.app.Application;
import android.content.Context;
import com.autolink.dvr.common.init.SchedulerSuppress;
import com.autolink.dvr.common.utils.LogUtils;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public abstract class BaseApplication extends Application {
    private static final String DIRECTIVE_STRUCTURE_NAMESPACE = "ai.dueros.device_interface.extensions.custom_user_interaction";
    private static final String MESSAGEIDHEADER_NAMESPACE = "local.ai.dueros.device_interface.directive_register";
    private static final String REGISTER_DIRECTIVE = "Register";
    private static final String UNREGISTER_DIRECTIVE = "Unregister";
    private static String mAppVersion = null;
    private static String mAppid = null;
    private static boolean mIsConnected = false;
    private static String mSid = null;
    private static String mSvid = null;
    private static boolean mVtsCapabilityState = false;
    private ExecutorService mSingleThreadPool = Executors.newSingleThreadExecutor();

    public boolean setVtsCapabilityState() {
        return false;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        initRxjava();
        initLogUtils();
    }

    private void initRxjava() {
        SchedulerSuppress.SuppressIo();
        SchedulerSuppress.SuppressCompute();
        SchedulerSuppress.SuppressBackground();
        RxJavaPlugins.lockdown();
    }

    public static boolean ismVtsCapabilityState() {
        return mVtsCapabilityState;
    }

    public static boolean isVRConnected() {
        return mIsConnected;
    }

    private void initLogUtils() {
        LogUtils.getInstance().init(getPackageName(), false, 1, 1, false);
    }

    private boolean isApkInDebug(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public static String getAppid() {
        return mAppid;
    }

    public static String getSid() {
        return mSid;
    }

    public static String getSvid() {
        return mSvid;
    }

    public static String getAppVersion() {
        return mAppVersion;
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
    }
}
