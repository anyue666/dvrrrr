package com.autolink.dvr.common.utils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/* loaded from: classes.dex */
public class LogUtils {
    public static final String DEFAULT_TAG = "DRV_";
    private static final boolean LOGD_ON = true;
    private static final boolean LOGE_ON = true;
    private static final boolean LOGI_ON = true;
    private static final boolean LOGV_ON = true;
    private static final boolean LOGWTF_ON = true;
    private static final boolean LOGW_ON = true;
    private static final boolean LOG_ENABLE = true;
    private String sLogTag;

    private LogUtils() {
        this.sLogTag = "";
    }

    private static class LogUtilsHolder {
        private static final LogUtils INSTANCE = new LogUtils();

        private LogUtilsHolder() {
        }
    }

    public static LogUtils getInstance() {
        return LogUtilsHolder.INSTANCE;
    }

    @Deprecated
    public void init() {
        init(DEFAULT_TAG);
    }

    public void init(String str) {
        init(str, false, 0, 0, true);
    }

    public void init(String str, boolean z, int i, int i2, final boolean z2) {
        this.sLogTag = str;
        PrettyFormatStrategy build = PrettyFormatStrategy.newBuilder().showThreadInfo(z).methodCount(i).methodOffset(i2).tag(str).build();
        Logger.clearLogAdapters();
        Logger.addLogAdapter(new AndroidLogAdapter(build) { // from class: com.autolink.dvr.common.utils.LogUtils.1
            @Override // com.orhanobut.logger.AndroidLogAdapter, com.orhanobut.logger.LogAdapter
            public boolean isLoggable(int i3, String str2) {
                return z2;
            }
        });
    }

    /* renamed from: d */
    public void m224d(String str, Object... objArr) {
        Logger.m499d(str, objArr);
    }

    /* renamed from: d */
    public void m223d(String str, String str2, Object... objArr) {
        Logger.m503t(str).mo507d(str2, objArr);
    }

    /* renamed from: d */
    public void m221d(Object obj) {
        Logger.m498d(obj);
    }

    /* renamed from: d */
    public void m222d(String str, Object obj) {
        Logger.m503t(str).mo506d(obj);
    }

    public void json(String str) {
        Logger.json(str);
    }

    public void xml(String str) {
        Logger.xml(str);
    }

    /* renamed from: w */
    public void m233w(String str, String str2, Object... objArr) {
        Logger.m503t(str).mo513w(str2, objArr);
    }

    /* renamed from: w */
    public void m234w(String str, Object... objArr) {
        Logger.m505w(str, objArr);
    }

    /* renamed from: e */
    public void m226e(String str, Object... objArr) {
        Logger.m500e(str, objArr);
    }

    /* renamed from: e */
    public void m228e(Throwable th, String str, Object... objArr) {
        Logger.m501e(th, str, objArr);
    }

    /* renamed from: e */
    public void m225e(String str, String str2, Object... objArr) {
        Logger.m503t(str).mo508e(str2, objArr);
    }

    /* renamed from: e */
    public void m227e(Throwable th, String str, String str2, Object... objArr) {
        Logger.m503t(str2).mo509e(th, str, objArr);
    }

    /* renamed from: i */
    public void m230i(String str, Object... objArr) {
        Logger.m502i(str, objArr);
    }

    /* renamed from: i */
    public void m229i(String str, String str2, Object... objArr) {
        Logger.m503t(str).mo510i(str2, objArr);
    }

    public void wtf(String str, Object... objArr) {
        Logger.wtf(str, objArr);
    }

    public void wtf(String str, String str2, Object... objArr) {
        Logger.m503t(str).wtf(str2, objArr);
    }

    /* renamed from: v */
    public void m232v(String str, Object... objArr) {
        Logger.m504v(str, objArr);
    }

    /* renamed from: v */
    public void m231v(String str, String str2, Object... objArr) {
        Logger.m503t(str).mo512v(str2, objArr);
    }
}
