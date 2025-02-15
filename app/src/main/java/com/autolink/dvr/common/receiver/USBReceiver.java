package com.autolink.dvr.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.media.MediaCodecConstant;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.SaveMsg;
import com.autolink.dvr.common.utils.USBUtil;

/* loaded from: classes.dex */
public class USBReceiver {
    public static final int USB_FORMAT = 13;
    public static final int USB_MOUNTED = 11;
    public static final int USB_MOUNTED_NOT_FAT32 = 14;
    public static final String USB_STATUS = "autolink.dvr.usb.status";
    public static final int USB_UNMOUNTED = 12;
    private static volatile USBReceiver instance;
    private Handler handler;
    private Context mContext;
    private final String TAG = "DVR_USBReceiver";
    private boolean isRecording = false;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.autolink.dvr.common.receiver.USBReceiver.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String path = intent.getData().getPath();
            LogUtils2.logI("DVR_USBReceiver", "broadcastReceiver :" + action + " , path = " + path);
            if (TextUtils.isEmpty(path)) {
                return;
            }
            if ("android.intent.action.MEDIA_MOUNTED".equals(action)) {
                LogUtils2.logI("DVR_USBReceiver", "ACTION_MEDIA_MOUNTED, handler = " + USBReceiver.this.handler);
                USBReceiver.this.usbMounted(path);
                return;
            }
            if ("android.intent.action.MEDIA_UNMOUNTED".equals(action)) {
                LogUtils2.logI("DVR_USBReceiver", "ACTION_MEDIA_UNMOUNTED, handler = " + USBReceiver.this.handler);
                USBReceiver.this.usbEjectOrUnmount(path);
                return;
            }
            if ("android.intent.action.MEDIA_EJECT".equals(action)) {
                LogUtils2.logI("DVR_USBReceiver", "onReceive ACTION_MEDIA_EJECT");
                USBReceiver.this.isRecording = MediaCodecConstant.isStartRecord;
                USBReceiver.this.usbEjectOrUnmount(path);
                return;
            }
            if ("android.intent.action.MEDIA_REMOVED".equals(action)) {
                LogUtils2.logD("DVR_USBReceiver", "onReceive ACTION_MEDIA_REMOVED");
                return;
            }
            if ("android.intent.action.MEDIA_CHECKING".equals(action)) {
                LogUtils2.logD("DVR_USBReceiver", "onReceive ACTION_MEDIA_CHECKING");
                return;
            }
            if ("android.intent.action.MEDIA_BAD_REMOVAL".equals(action)) {
                if (path.equals(USBUtil.USB_PATH) && USBReceiver.this.isRecording) {
                    USBReceiver.this.isRecording = false;
                    Toast.makeText(context, C0903R.string.usb_bad_remove, 0).show();
                }
                LogUtils2.logD("DVR_USBReceiver", "onReceive ACTION_MEDIA_BAD_REMOVAL");
                return;
            }
            if ("android.intent.action.MEDIA_SCANNER_STARTED".equals(action)) {
                LogUtils2.logD("DVR_USBReceiver", "onReceive ACTION_MEDIA_SCANNER_STARTED");
                return;
            }
            if ("android.intent.action.MEDIA_SCANNER_FINISHED".equals(action)) {
                LogUtils2.logD("DVR_USBReceiver", "onReceive ACTION_MEDIA_SCANNER_FINISHED");
            } else if ("android.intent.action.MEDIA_SCANNER_SCAN_FILE".equals(action)) {
                LogUtils2.logD("DVR_USBReceiver", "onReceive ACTION_MEDIA_SCANNER_SCAN_FILE");
            } else if ("android.intent.action.MEDIA_NOFS".equals(action)) {
                LogUtils2.logD("DVR_USBReceiver", "onReceive ACTION_MEDIA_NOFS");
            }
        }
    };

    public static synchronized USBReceiver getInstance() {
        USBReceiver uSBReceiver;
        synchronized (USBReceiver.class) {
            if (instance == null) {
                synchronized (USBReceiver.class) {
                    instance = new USBReceiver();
                }
            }
            uSBReceiver = instance;
        }
        return uSBReceiver;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void registerReceiver(Context context) {
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        intentFilter.addAction("android.intent.action.MEDIA_EJECT");
        intentFilter.addAction("android.intent.action.MEDIA_REMOVED");
        intentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
        intentFilter.addAction("android.intent.action.MEDIA_CHECKING");
        intentFilter.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
        intentFilter.addAction("android.intent.action.MEDIA_SCANNER_STARTED");
        intentFilter.addAction("android.intent.action.MEDIA_SCANNER_FINISHED");
        intentFilter.addAction("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intentFilter.addAction("android.intent.action.MEDIA_NOFS");
        intentFilter.addDataScheme("file");
        context.registerReceiver(this.broadcastReceiver, intentFilter);
    }

    public void unregisterReceiver() {
        this.mContext.unregisterReceiver(this.broadcastReceiver);
    }

    private void sendUsbStatusBroadcast() {
        Intent intent = new Intent(USB_STATUS);
        intent.setPackage(this.mContext.getPackageName());
        this.mContext.sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void usbMounted(String str) {
        USBUtil.getUSBInfo(this.mContext);
        if (this.handler == null || !str.contains(USBUtil.USB_J8)) {
            return;
        }
        if (USBUtil.totalSize < USBUtil.USB_SPACE) {
            LogUtils2.logI("DVR_USBReceiver", "usbMounted, totalSize < 7.5G ");
            Toast.makeText(DVRApplication.getInstance(), C0903R.string.usb_space_not_enough, 0).show();
        } else {
            if (USBUtil.isHaveOtherFile(USBUtil.USBPath)) {
                LogUtils2.logI("DVR_USBReceiver", "usbMounted, HaveOtherFile ");
                Message message = new Message();
                message.what = 13;
                this.handler.sendMessage(message);
                return;
            }
            LogUtils2.logI("DVR_USBReceiver", "usbMounted, not HaveOtherFile ");
            Message message2 = new Message();
            message2.what = 11;
            this.handler.sendMessage(message2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void usbEjectOrUnmount(String str) {
        if (str.contains(USBUtil.USB_J8)) {
            SaveMsg.isMountedUSB = false;
            sendUsbStatusBroadcast();
        }
        if (this.handler == null || !str.contains(USBUtil.USB_J8) || USBUtil.totalSize < USBUtil.USB_SPACE) {
            return;
        }
        Message message = new Message();
        message.what = 12;
        this.handler.sendMessage(message);
    }
}
