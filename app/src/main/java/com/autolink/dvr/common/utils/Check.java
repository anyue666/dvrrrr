package com.autolink.dvr.common.utils;

/* loaded from: classes.dex */
public class Check {
    private static final int MIN_CLICK_DELAY_TIME = 200;
    private static long lastClickTime;

    public static boolean isFastClick() {
        long currentTimeMillis = System.currentTimeMillis();
        boolean z = currentTimeMillis - lastClickTime >= 200;
        lastClickTime = currentTimeMillis;
        return z;
    }
}
