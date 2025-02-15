package com.autolink.dvr.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.autolink.dvr.common.utils.LogUtils2;

/* loaded from: classes.dex */
public class BootCompleteReceiver extends BroadcastReceiver {
    private final String TAG = "DVR_BootCompleteReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || !intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            return;
        }
        LogUtils2.logI("DVR_BootCompleteReceiver", "onReceive : received boot completed broadcast");
    }
}
