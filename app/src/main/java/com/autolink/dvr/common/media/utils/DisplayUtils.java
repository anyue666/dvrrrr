package com.autolink.dvr.common.media.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.opengl.EGLContext;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import com.autolink.dvr.common.utils.LogUtils2;

/* loaded from: classes.dex */
public class DisplayUtils {
    private static final String TAG = "DVR_DisplayUtils";
    public static EGLContext eglContext = null;
    public static int fboTextureId = 0;
    public static int surfaceViewHeight = 720;
    public static int surfaceViewWidth = 1280;

    public static int getScreenWidth(Context context) {
        return surfaceViewWidth;
    }

    public static int getScreenHeight(Context context) {
        return surfaceViewHeight;
    }

    public static void adjustBrightness(float f, Context context) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.screenBrightness = f;
        window.setAttributes(attributes);
    }

    public static boolean isHaveCamera(int i) {
        if (Build.VERSION.SDK_INT < 9) {
            LogUtils2.logI(TAG, "isHaveCamera: sdk < 9, return false ");
            return false;
        }
        int numberOfCameras = Camera.getNumberOfCameras();
        LogUtils2.logD(TAG, "isHaveCamera: cameraCount = " + numberOfCameras);
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i2 = 0; i2 < numberOfCameras; i2++) {
            Camera.getCameraInfo(i2, cameraInfo);
            if (cameraInfo.facing == 0) {
                LogUtils2.logI(TAG, "isHaveCamera: yes ");
                return true;
            }
        }
        LogUtils2.logI(TAG, "isHaveCamera: no ");
        return false;
    }

    public static boolean isHaveDVRCamera(Context context) {
        try {
            for (String str : ((CameraManager) context.getSystemService("camera")).getCameraIdList()) {
                LogUtils2.logI(TAG, "isHaveDVRCamera cid = " + str);
                if (str.equals("48")) {
                    LogUtils2.logI(TAG, "isHaveDVRCamera  yes");
                    return true;
                }
            }
        } catch (Exception e) {
            LogUtils2.logI(TAG, "isHaveDVRCamera Exception = " + e.toString());
        }
        LogUtils2.logI(TAG, "isHaveDVRCamera no");
        return false;
    }
}
