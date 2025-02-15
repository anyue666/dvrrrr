package com.autolink.dvr.common.utils;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import de.mindpipe.android.logging.log4j.LogConfigurator;
import java.io.File;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/* loaded from: classes.dex */
public class LogUtils2 {
    private static boolean DEBUG = true;
    public static final String DEFAULT_TAG = "DVR_";
    private static boolean ERROR = true;
    private static boolean INFO = true;
    private static boolean TRACE = false;
    private static boolean VERBOSE = true;
    private static boolean WARN = true;
    private static Logger sLogger;

    public static void init(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        DEBUG = z;
        VERBOSE = z2;
        WARN = z3;
        INFO = z4;
        ERROR = z5;
        TRACE = z6;
    }

    public static void init(Context context) {
        if (!"1".equals(getProperty("persist.sys.autolink.dvr.debug"))) {
            logI(DEFAULT_TAG, "this is not debug,will not save log to file");
            return;
        }
        init(true, true, true, true, true, true);
        File file = new File(context.getExternalFilesDir("log"), Process.myPid() + "_log.txt");
        LogConfigurator logConfigurator = new LogConfigurator();
        logConfigurator.setFileName(file.getAbsolutePath());
        logConfigurator.setRootLevel(Level.ALL);
        logConfigurator.setLevel("DVR", Level.ALL);
        logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
        logConfigurator.setMaxFileSize(20971520L);
        logConfigurator.setImmediateFlush(true);
        logConfigurator.setMaxBackupSize(20);
        logConfigurator.setUseLogCatAppender(false);
        logConfigurator.configure();
        Logger logger = Logger.getLogger("DVR");
        sLogger = logger;
        logger.debug("Log4J has started!!!");
    }

    public static String getProperty(String str) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class).invoke(cls, str);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void logD(String str, String str2) {
        if (DEBUG) {
            Log.d(str, "[thread:" + Thread.currentThread().getName() + "] - " + str2);
        }
        Logger logger = sLogger;
        if (logger != null) {
            logger.debug(str + ":" + str2);
        }
    }

    public static void logTrace(String str, String str2) {
        if (TRACE) {
            Log.d(str, "[thread:" + Thread.currentThread().getName() + "] - " + str2);
        }
        Logger logger = sLogger;
        if (logger != null) {
            logger.debug(str + ":" + str2);
        }
    }

    public static void logV(String str, String str2) {
        if (VERBOSE) {
            Log.v(str, "[thread:" + Thread.currentThread().getName() + "] - " + str2);
            Logger logger = sLogger;
            if (logger != null) {
                logger.debug(str + ":" + str2);
            }
        }
    }

    public static void logI(String str, String str2) {
        if (INFO) {
            Log.i(str, "[thread:" + Thread.currentThread().getName() + "] - " + str2);
            Logger logger = sLogger;
            if (logger != null) {
                logger.info(str + ":" + str2);
            }
        }
    }

    public static void logW(String str, String str2) {
        if (WARN) {
            Log.w(str, "[thread:" + Thread.currentThread().getName() + "] - " + str2);
            Logger logger = sLogger;
            if (logger != null) {
                logger.warn(str + ":" + str2);
            }
        }
    }

    public static void logE(String str, String str2) {
        if (ERROR) {
            Log.e(str, "[thread:" + Thread.currentThread().getName() + "] - " + str2);
            Logger logger = sLogger;
            if (logger != null) {
                logger.error(str + ":" + str2);
            }
        }
    }
}
