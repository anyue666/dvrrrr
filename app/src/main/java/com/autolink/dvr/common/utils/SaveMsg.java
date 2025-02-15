package com.autolink.dvr.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.media.utils.FileUtils;

/* loaded from: classes.dex */
public class SaveMsg {
    private static final String TAG = "DVR_SaveMsg";
    public static boolean isMountedUSB;

    public static void setRecMsg(Context context, int i) {
        LogUtils2.logI(TAG, "setRecMsg n = " + i);
        SharedPreferences.Editor edit = context.getSharedPreferences("RecMsg", 0).edit();
        edit.putInt("rec", i);
        edit.commit();
        if (i > 0) {
            FileUtils.saveTime_n = i;
        }
    }

    public static int getRecMsg(Context context) {
        return context.getSharedPreferences("RecMsg", 0).getInt("rec", 3);
    }

    public static void setSensitivityMsg(Context context, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences("SensitivityMsg", 0).edit();
        edit.putInt("sen", i);
        edit.commit();
    }

    public static int getSensitivityMsg(Context context) {
        return context.getSharedPreferences("SensitivityMsg", 0).getInt("sen", 1);
    }

    public static String getProperties() {
        String str;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            str = (String) cls.getMethod("get", String.class, String.class).invoke(cls, DVRApplication.DVR_PROPERTIES, "1");
        } catch (Exception e) {
            LogUtils2.logI(TAG, "getProperties e = " + e.toString());
            str = "";
        }
        LogUtils2.logI(TAG, "getProperties result = " + str);
        return str;
    }
}
