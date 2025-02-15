package com.autolink.dvr.common.media.surface;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.media.MediaCodecConstant;
import com.autolink.dvr.common.media.utils.DisplayUtils;
import com.autolink.dvr.common.utils.LogUtils2;
import java.util.Arrays;
import java.util.List;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes.dex */
public class CameraManagerHelper {
    private static final String TAG = "DVR_CameraManagerHelper";
    private static Boolean isException = false;
    private Camera camera;
    private CameraDevice cameraDevice;
    private CameraManager cameraManager;
    private Context context;
    private int defaultCameraId = 0;
    private Handler handler;
    private int previewHeight;
    private int previewWidth;
    private int screenHeight;
    private int screenWidth;
    private SurfaceTexture surfaceTexture;

    public CameraManagerHelper(Context context) {
        this.context = context;
        this.screenWidth = DisplayUtils.getScreenWidth(context);
        this.screenHeight = DisplayUtils.getScreenHeight(context);
        LogUtils2.logI(TAG, "CameraManagerHelper  screenWidth = " + this.screenWidth + " ,screenHeight=" + this.screenHeight);
    }

    public void startCamera(SurfaceTexture surfaceTexture, int i, Handler handler) {
        LogUtils2.logI(TAG, "startCamera ");
        this.surfaceTexture = surfaceTexture;
        this.handler = handler;
        startCamera2(i);
    }

    private void startCamera(int i) {
        LogUtils2.logI(TAG, "startCamera cameraId = " + i);
        try {
            Camera open = Camera.open(i);
            this.camera = open;
            open.setPreviewTexture(this.surfaceTexture);
            Camera.Parameters parameters = this.camera.getParameters();
            parameters.setFlashMode(DebugKt.DEBUG_PROPERTY_VALUE_OFF);
            parameters.setPreviewFormat(17);
            Camera.Size cameraSize = getCameraSize(parameters.getSupportedPreviewSizes(), this.screenWidth, this.screenHeight, 0.10000000149011612d);
            parameters.setPreviewSize(cameraSize.width, cameraSize.height);
            LogUtils2.logI(TAG, "startCamera: 预览宽:" + cameraSize.width + " -- 预览高:" + cameraSize.height);
            this.previewWidth = cameraSize.width;
            this.previewHeight = cameraSize.height;
            Camera.Size cameraSize2 = getCameraSize(parameters.getSupportedPictureSizes(), this.screenWidth, this.screenHeight, 0.10000000149011612d);
            parameters.setPictureSize(cameraSize2.width, cameraSize2.height);
            LogUtils2.logI(TAG, "startCamera: 图片宽:" + cameraSize2.width + " -- 图片高:" + cameraSize2.height);
            this.camera.setParameters(parameters);
            this.camera.startPreview();
            isException = false;
        } catch (Exception e) {
            LogUtils2.logE(TAG, "startCamera Exception = " + e.toString());
            isException = true;
        }
    }

    public void startCamera2(int i) {
        LogUtils2.logI(TAG, "startCamera2 cameraId = " + i);
        this.cameraManager = (CameraManager) this.context.getSystemService("camera");
        try {
            if (ActivityCompat.checkSelfPermission(this.context, "android.permission.CAMERA") != 0) {
                LogUtils2.logI(TAG, "startCamera2 no Permission ");
            } else {
                this.cameraManager.openCamera(String.valueOf(i), new CameraDevice.StateCallback() { // from class: com.autolink.dvr.common.media.surface.CameraManagerHelper.1
                    C09141() {
                    }

                    @Override // android.hardware.camera2.CameraDevice.StateCallback
                    public void onOpened(CameraDevice cameraDevice) {
                        LogUtils2.logI(CameraManagerHelper.TAG, "startCamera2 onOpened ");
                        if (CameraManagerHelper.isException.booleanValue() && MediaCodecConstant.isStartRecord) {
                            CameraManagerHelper.this.handler.sendEmptyMessage(10);
                        }
                        Boolean unused = CameraManagerHelper.isException = false;
                        CameraManagerHelper.this.cameraDevice = cameraDevice;
                        CameraManagerHelper.this.createPreviewSession();
                    }

                    @Override // android.hardware.camera2.CameraDevice.StateCallback
                    public void onDisconnected(CameraDevice cameraDevice) {
                        LogUtils2.logI(CameraManagerHelper.TAG, "startCamera2 onDisconnected ");
                        Boolean unused = CameraManagerHelper.isException = true;
                        cameraDevice.close();
                        CameraManagerHelper.this.cameraDevice = null;
                        Toast.makeText(DVRApplication.getInstance(), C0903R.string.main_cover_camera, 0).show();
                    }

                    @Override // android.hardware.camera2.CameraDevice.StateCallback
                    public void onError(CameraDevice cameraDevice, int i2) {
                        LogUtils2.logI(CameraManagerHelper.TAG, "startCamera2 onError ");
                        Boolean unused = CameraManagerHelper.isException = true;
                        cameraDevice.close();
                        CameraManagerHelper.this.cameraDevice = null;
                        Toast.makeText(DVRApplication.getInstance(), C0903R.string.main_cover_camera, 0).show();
                    }
                }, this.handler);
            }
        } catch (Exception e) {
            LogUtils2.logI(TAG, "startCamera2 Exception = " + e.toString());
            isException = true;
        }
    }

    /* renamed from: com.autolink.dvr.common.media.surface.CameraManagerHelper$1 */
    class C09141 extends CameraDevice.StateCallback {
        C09141() {
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpened(CameraDevice cameraDevice) {
            LogUtils2.logI(CameraManagerHelper.TAG, "startCamera2 onOpened ");
            if (CameraManagerHelper.isException.booleanValue() && MediaCodecConstant.isStartRecord) {
                CameraManagerHelper.this.handler.sendEmptyMessage(10);
            }
            Boolean unused = CameraManagerHelper.isException = false;
            CameraManagerHelper.this.cameraDevice = cameraDevice;
            CameraManagerHelper.this.createPreviewSession();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onDisconnected(CameraDevice cameraDevice) {
            LogUtils2.logI(CameraManagerHelper.TAG, "startCamera2 onDisconnected ");
            Boolean unused = CameraManagerHelper.isException = true;
            cameraDevice.close();
            CameraManagerHelper.this.cameraDevice = null;
            Toast.makeText(DVRApplication.getInstance(), C0903R.string.main_cover_camera, 0).show();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onError(CameraDevice cameraDevice, int i2) {
            LogUtils2.logI(CameraManagerHelper.TAG, "startCamera2 onError ");
            Boolean unused = CameraManagerHelper.isException = true;
            cameraDevice.close();
            CameraManagerHelper.this.cameraDevice = null;
            Toast.makeText(DVRApplication.getInstance(), C0903R.string.main_cover_camera, 0).show();
        }
    }

    public void closeCamera() {
        LogUtils2.logI(TAG, "close camera");
        CameraDevice cameraDevice = this.cameraDevice;
        if (cameraDevice != null) {
            cameraDevice.close();
            this.cameraDevice = null;
        }
    }

    public void createPreviewSession() {
        LogUtils2.logI(TAG, "createPreviewSession");
        Surface surface = new Surface(this.surfaceTexture);
        try {
            this.cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() { // from class: com.autolink.dvr.common.media.surface.CameraManagerHelper.2
                final /* synthetic */ Surface val$previewSurface;

                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                }

                C09152(Surface surface2) {
                    r2 = surface2;
                }

                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                    try {
                        CaptureRequest.Builder createCaptureRequest = CameraManagerHelper.this.cameraDevice.createCaptureRequest(1);
                        createCaptureRequest.addTarget(r2);
                        cameraCaptureSession.setRepeatingRequest(createCaptureRequest.build(), new CameraCaptureSession.CaptureCallback() { // from class: com.autolink.dvr.common.media.surface.CameraManagerHelper.2.1
                            AnonymousClass1() {
                            }

                            @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
                            public void onCaptureProgressed(CameraCaptureSession cameraCaptureSession2, CaptureRequest captureRequest, CaptureResult captureResult) {
                                super.onCaptureProgressed(cameraCaptureSession2, captureRequest, captureResult);
                            }

                            @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
                            public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession2, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
                                super.onCaptureCompleted(cameraCaptureSession2, captureRequest, totalCaptureResult);
                            }
                        }, null);
                    } catch (CameraAccessException e) {
                        LogUtils2.logE(CameraManagerHelper.TAG, "createPreviewSession CameraAccessException = " + e.toString());
                    }
                }

                /* renamed from: com.autolink.dvr.common.media.surface.CameraManagerHelper$2$1 */
                class AnonymousClass1 extends CameraCaptureSession.CaptureCallback {
                    AnonymousClass1() {
                    }

                    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
                    public void onCaptureProgressed(CameraCaptureSession cameraCaptureSession2, CaptureRequest captureRequest, CaptureResult captureResult) {
                        super.onCaptureProgressed(cameraCaptureSession2, captureRequest, captureResult);
                    }

                    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
                    public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession2, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
                        super.onCaptureCompleted(cameraCaptureSession2, captureRequest, totalCaptureResult);
                    }
                }
            }, null);
        } catch (Exception e) {
            LogUtils2.logE(TAG, "createPreviewSession Exception = " + e.toString());
        }
    }

    /* renamed from: com.autolink.dvr.common.media.surface.CameraManagerHelper$2 */
    class C09152 extends CameraCaptureSession.StateCallback {
        final /* synthetic */ Surface val$previewSurface;

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
        }

        C09152(Surface surface2) {
            r2 = surface2;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
            try {
                CaptureRequest.Builder createCaptureRequest = CameraManagerHelper.this.cameraDevice.createCaptureRequest(1);
                createCaptureRequest.addTarget(r2);
                cameraCaptureSession.setRepeatingRequest(createCaptureRequest.build(), new CameraCaptureSession.CaptureCallback() { // from class: com.autolink.dvr.common.media.surface.CameraManagerHelper.2.1
                    AnonymousClass1() {
                    }

                    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
                    public void onCaptureProgressed(CameraCaptureSession cameraCaptureSession2, CaptureRequest captureRequest, CaptureResult captureResult) {
                        super.onCaptureProgressed(cameraCaptureSession2, captureRequest, captureResult);
                    }

                    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
                    public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession2, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
                        super.onCaptureCompleted(cameraCaptureSession2, captureRequest, totalCaptureResult);
                    }
                }, null);
            } catch (CameraAccessException e) {
                LogUtils2.logE(CameraManagerHelper.TAG, "createPreviewSession CameraAccessException = " + e.toString());
            }
        }

        /* renamed from: com.autolink.dvr.common.media.surface.CameraManagerHelper$2$1 */
        class AnonymousClass1 extends CameraCaptureSession.CaptureCallback {
            AnonymousClass1() {
            }

            @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
            public void onCaptureProgressed(CameraCaptureSession cameraCaptureSession2, CaptureRequest captureRequest, CaptureResult captureResult) {
                super.onCaptureProgressed(cameraCaptureSession2, captureRequest, captureResult);
            }

            @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
            public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession2, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
                super.onCaptureCompleted(cameraCaptureSession2, captureRequest, totalCaptureResult);
            }
        }
    }

    private void stopPreview() {
        LogUtils2.logI(TAG, "stopPreview ");
        Camera camera = this.camera;
        if (camera != null) {
            camera.stopPreview();
            this.camera.release();
            this.camera = null;
        }
    }

    public void autoFocus() {
        Camera camera = this.camera;
        if (camera != null) {
            camera.autoFocus(null);
        }
    }

    public int getPreviewWidth() {
        return this.previewWidth;
    }

    public int getPreviewHeight() {
        return this.previewHeight;
    }

    private Camera.Size getCameraSize(List<Camera.Size> list, int i, int i2, double d) {
        int i3;
        int i4;
        if (i < i2) {
            i4 = i;
            i3 = i2;
        } else {
            i3 = i;
            i4 = i2;
        }
        double d2 = i3 / i4;
        Camera.Size size = null;
        if (list == null || list.isEmpty()) {
            LogUtils2.logI(TAG, "getCameraSize: 获取相机预览数据失败 ");
            return null;
        }
        for (Camera.Size size2 : list) {
            double abs = Math.abs((size2.width / size2.height) - d2);
            if (abs <= d) {
                if (size == null || size.width * size.height < size2.width * size2.height) {
                    size = size2;
                }
                d = abs;
            }
        }
        if (size != null) {
            return size;
        }
        double d3 = d + 0.10000000149011612d;
        if (d3 > 1.0d) {
            return list.get(0);
        }
        return getCameraSize(list, i3, i4, d3);
    }

    public void setFlashMode(String str) {
        if (this.context.getPackageManager().hasSystemFeature("android.hardware.camera.flash")) {
            Camera.Parameters parameters = this.camera.getParameters();
            parameters.setFlashMode(str);
            this.camera.setParameters(parameters);
        }
    }

    public void switchCamera() {
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (this.defaultCameraId == 0) {
                if (cameraInfo.facing == 1) {
                    restartCameraPreview(i);
                    this.defaultCameraId = i;
                    return;
                }
            } else if (cameraInfo.facing == 0) {
                restartCameraPreview(i);
                this.defaultCameraId = i;
                return;
            }
        }
    }

    private void restartCameraPreview(int i) {
        LogUtils2.logI(TAG, "restartCameraPreview: cameraId = " + i);
        stopPreview();
        isException = false;
        startCamera(i);
    }

    public static Boolean getException() {
        LogUtils2.logI(TAG, "getException: isException = " + isException);
        return isException;
    }

    public int getZoomValue(int i) {
        int i2;
        Camera.Parameters parameters = this.camera.getParameters();
        if (!parameters.isZoomSupported() || i == 0) {
            return -1;
        }
        int zoom = parameters.getZoom();
        if (i == 1) {
            i2 = zoom + 1;
            if (i2 < parameters.getMaxZoom()) {
                parameters.setZoom(i2);
                this.camera.setParameters(parameters);
            }
        } else {
            i2 = zoom - 1;
            if (i2 >= 0) {
                parameters.setZoom(i2);
                this.camera.setParameters(parameters);
            }
        }
        return i2;
    }

    public void setZoomValue(int i) {
        Camera.Parameters parameters = this.camera.getParameters();
        if (parameters.isZoomSupported()) {
            Log.d(TAG, "setZoomValue: " + i);
            Log.d(TAG, "setZoomValue: Camera最大缩放值" + getMaxZoomValue());
            if (i > getMaxZoomValue()) {
                i = getMaxZoomValue();
            }
            parameters.setZoom(i);
            this.camera.setParameters(parameters);
        }
    }

    public int getMaxZoomValue() {
        Camera.Parameters parameters = this.camera.getParameters();
        if (parameters.isZoomSupported()) {
            return parameters.getMaxZoom();
        }
        return -1;
    }
}
