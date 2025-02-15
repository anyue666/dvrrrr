package com.autolink.dvr.common.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.view.WindowManager;
import android.widget.Toast;
import com.autolink.buryservice.BuryServiceManager;
import com.autolink.buryservice.bury.consts.BuryEventConst;
import com.autolink.buryservice.bury.consts.BuryKeyConst;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.IDVRAidlInterface;
import com.autolink.dvr.common.can.CanOperation;
import com.autolink.dvr.common.media.MediaCodecConstant;
import com.autolink.dvr.common.media.codec.MediaEncodeManager;
import com.autolink.dvr.common.media.surface.CameraManagerHelper;
import com.autolink.dvr.common.media.surface.VideoFBORender;
import com.autolink.dvr.common.media.utils.DisplayUtils;
import com.autolink.dvr.common.media.utils.FileUtils;
import com.autolink.dvr.common.receiver.USBReceiver;
import com.autolink.dvr.common.service.AidlService;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.SaveMsg;
import com.autolink.dvr.common.utils.USBUtil;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpStatus;
import java.io.File;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class DVRService extends Service implements VideoFBORender.OnSurfaceListener {
    private static final String ACTION = "com.autolink.dvr.aidl.service";
    public static final int CAMERA_RESTORE = 10;
    public static final int CHECK_CAMERA = 15;
    public static final int DVR_START_RECORDING = 8;
    public static final int DVR_STOP_RECORDING = 9;
    public static final int GET_USB_INFO = 16;
    public static final int HAVE_CAMERA = 1;
    public static final int MAIN_ACTIVITY_CHECK_REBOOT = 18;
    public static final int MAIN_ACTIVITY_COVERUP = 5;
    public static final int MAIN_ACTIVITY_DISMISS_WINDOW = 6;
    public static final int MAIN_ACTIVITY_FORMAT_USB = 7;
    public static final int MAIN_ACTIVITY_MESSENGER = 4;
    public static final int NEED_AUTO_START_RECORDING = 3;
    public static final int NEED_STOP_RECORDING = 0;
    public static final int NO_CAMERA = 2;
    private static final String PACKAGE = "com.autolink.dvr";
    private static final String SERVICE_PATH = "com.autolink.dvr.common.service.AidlService";
    public static final int TOAST_USB_BAD = 17;
    private static boolean isFirstStart = true;
    private static boolean isStartWithRec0 = true;
    private CameraManagerHelper cameraManager;
    private long lastUsbMountedTime;
    private long lastUsbUnmountedTime;
    private Messenger mActivityMessenger;
    private BuryServiceManager mBuryServiceManager;
    public Handler mHandler;
    private Messenger mServiceMessenger;
    private USBReceiver mUSBReceiver;
    private MediaEncodeManager mediaEncodeManager;
    private MyBroadcastReceiver myBroadcastReceiver;
    AidlService.MyStub myStub;
    private VideoFBORender videoFBORender;
    private final String TAG = "DVR_DVRService";
    private boolean isConnected = false;
    private int cameraId = 48;
    private final int USB_MOUNTED_TIME = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    private final int USB_UNMOUNTED_TIME = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    private int mCurrentWaitStopedTime = 0;
    private ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.autolink.dvr.common.service.DVRService.1
        ServiceConnectionC09211() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            DVRService.this.myStub = (AidlService.MyStub) IDVRAidlInterface.Stub.asInterface(iBinder);
            DVRService.this.isConnected = true;
            LogUtils2.logI("DVR_DVRService", "onServiceConnected isStartRecord = " + MediaCodecConstant.isStartRecord);
            if (DVRService.isStartWithRec0) {
                return;
            }
            LogUtils2.logI("DVR_DVRService", "onServiceConnected notify systemui");
            DVRService.this.myStub.notifyReceiver(MediaCodecConstant.isStartRecord);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils2.logI("DVR_DVRService", "onServiceDisconnected ");
            DVRService.this.isConnected = false;
        }
    };

    /* renamed from: com.autolink.dvr.common.service.DVRService$1 */
    class ServiceConnectionC09211 implements ServiceConnection {
        ServiceConnectionC09211() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            DVRService.this.myStub = (AidlService.MyStub) IDVRAidlInterface.Stub.asInterface(iBinder);
            DVRService.this.isConnected = true;
            LogUtils2.logI("DVR_DVRService", "onServiceConnected isStartRecord = " + MediaCodecConstant.isStartRecord);
            if (DVRService.isStartWithRec0) {
                return;
            }
            LogUtils2.logI("DVR_DVRService", "onServiceConnected notify systemui");
            DVRService.this.myStub.notifyReceiver(MediaCodecConstant.isStartRecord);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils2.logI("DVR_DVRService", "onServiceDisconnected ");
            DVRService.this.isConnected = false;
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        LogUtils2.logI("DVR_DVRService", "onCreate ");
        if (SaveMsg.getProperties().equals("0")) {
            LogUtils2.logI("DVR_DVRService", "onCreate  return");
            return;
        }
        this.mBuryServiceManager = BuryServiceManager.getInstance();
        this.myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CanOperation.ACTION_EMERGENCY);
        intentFilter.addAction(CanOperation.ACTION_POWER_OFF);
        intentFilter.addAction(CanOperation.ACTION_POWER_ACC_ON);
        intentFilter.addAction(CanOperation.ACTION_OVER_SPEED);
        registerReceiver(this.myBroadcastReceiver, intentFilter);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.autolink.dvr", SERVICE_PATH));
        intent.setAction(ACTION);
        bindService(intent, this.serviceConnection, 1);
        HandlerThread handlerThread = new HandlerThread("DVRServiceHandlerThread");
        handlerThread.start();
        this.mHandler = new MyHandler(handlerThread.getLooper(), this);
        this.mServiceMessenger = new Messenger(this.mHandler);
        USBReceiver uSBReceiver = USBReceiver.getInstance();
        this.mUSBReceiver = uSBReceiver;
        uSBReceiver.registerReceiver(getApplicationContext());
        this.mUSBReceiver.setHandler(this.mHandler);
        this.mHandler.sendEmptyMessageDelayed(15, 3000L);
        this.mHandler.sendEmptyMessage(16);
        this.cameraManager = new CameraManagerHelper(this);
        this.mHandler.sendEmptyMessage(8);
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogUtils2.logI("DVR_DVRService", "onDestroy ");
        USBReceiver uSBReceiver = this.mUSBReceiver;
        if (uSBReceiver != null) {
            uSBReceiver.unregisterReceiver();
        }
        unbindService(this.serviceConnection);
        unregisterReceiver(this.myBroadcastReceiver);
        super.onDestroy();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        LogUtils2.logI("DVR_DVRService", "onBind ");
        return this.mServiceMessenger.getBinder();
    }

    @Override // com.autolink.dvr.common.media.surface.VideoFBORender.OnSurfaceListener
    public void onSurfaceCreate(SurfaceTexture surfaceTexture, int i) {
        LogUtils2.logI("DVR_DVRService", "onSurfaceCreate ,surfaceTexture = " + surfaceTexture + "fboTextureId = " + i);
        this.cameraManager.startCamera(surfaceTexture, this.cameraId, this.mHandler);
    }

    private void initMediaCodec(Handler handler) {
        LogUtils2.logI("DVR_DVRService", "initMediaCodec  width=" + DisplayUtils.surfaceViewWidth + " , height=" + DisplayUtils.surfaceViewHeight);
        VideoFBORender videoFBORender = new VideoFBORender(this);
        this.videoFBORender = videoFBORender;
        videoFBORender.setOnSurfaceListener(this);
        previewAngle(this);
        MediaEncodeManager mediaEncodeManager = MediaEncodeManager.getInstance();
        this.mediaEncodeManager = mediaEncodeManager;
        mediaEncodeManager.setEglSurfaceRender(this.videoFBORender);
        this.mediaEncodeManager.setHandler(this.mHandler);
        this.mediaEncodeManager.initMediaCodec(getApplicationContext(), DisplayUtils.surfaceViewWidth, DisplayUtils.surfaceViewHeight);
        this.mediaEncodeManager.initEglThread(1);
        this.mediaEncodeManager.initCodecThread();
    }

    public void previewAngle(Context context) {
        int rotation = ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation();
        LogUtils2.logI("DVR_DVRService", "previewAngle angle = " + rotation);
        this.videoFBORender.resetMatrix();
        if (rotation != 0) {
            return;
        }
        this.videoFBORender.setAngle(180.0f, 1.0f, 0.0f, 0.0f);
    }

    protected void startRecordingAndCheck() {
        LogUtils2.logI("DVR_DVRService", "startRecordingAndCheck isMountedUSB = " + SaveMsg.isMountedUSB + " , isFirstStart = " + isFirstStart + " , isNeedFormatUSB = " + USBUtil.isNeedFormatUSB + " , mediaMuxerState = " + MediaCodecConstant.mediaMuxerState + ", mCurrentWaitStopedTime = " + this.mCurrentWaitStopedTime);
        if (MediaCodecConstant.mediaMuxerState == 3) {
            if (this.mCurrentWaitStopedTime == 10) {
                formatUSBReboot();
                this.mCurrentWaitStopedTime = 0;
                return;
            } else {
                this.mHandler.sendEmptyMessageDelayed(8, 1000L);
                this.mCurrentWaitStopedTime++;
                return;
            }
        }
        if (CameraManagerHelper.getException().booleanValue()) {
            coverUp();
            return;
        }
        int recMsg = SaveMsg.getRecMsg(getApplicationContext());
        if (recMsg > 0) {
            FileUtils.saveTime_n = recMsg;
        }
        if (isFirstStart) {
            if (DisplayUtils.isHaveDVRCamera(this) && SaveMsg.isMountedUSB && !USBUtil.isHaveOtherFile(USBUtil.USB_PATH)) {
                if (SaveMsg.getRecMsg(getApplicationContext()) == 0) {
                    isStartWithRec0 = true;
                    LogUtils2.logI("DVR_DVRService", "startRecordingAndCheck isStartRecord = " + MediaCodecConstant.isStartRecord);
                    this.mHandler.sendEmptyMessageDelayed(0, 3000L);
                } else {
                    isStartWithRec0 = false;
                }
                startRecording();
                return;
            }
            return;
        }
        if (!DisplayUtils.isHaveDVRCamera(this) || !SaveMsg.isMountedUSB || USBUtil.isHaveOtherFile(USBUtil.USB_PATH) || SaveMsg.getRecMsg(getApplicationContext()) == 0) {
            return;
        }
        isStartWithRec0 = false;
        startRecording();
    }

    protected void startRecording() {
        LogUtils2.logE("DVR_DVRService", "startRecording isConnected=" + this.isConnected + " ,myStub=" + this.myStub + " ,isStartRecord= " + MediaCodecConstant.isStartRecord);
        if (MediaCodecConstant.isStartRecord) {
            LogUtils2.logI("DVR_DVRService", "startRecording, do nothing, is started");
            return;
        }
        MediaCodecConstant.isStartRecord = true;
        coverUp();
        if (isFirstStart) {
            initMediaCodec(this.mHandler);
        }
        this.mediaEncodeManager.startEncode(isFirstStart);
        isFirstStart = false;
        if (this.isConnected && this.myStub != null && !isStartWithRec0) {
            LogUtils2.logI("DVR_DVRService", "startRecording notify systemui");
            this.myStub.notifyReceiver(MediaCodecConstant.isStartRecord);
        }
        this.mBuryServiceManager.panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_Videotape, null, 0);
    }

    protected void stopRecording(boolean z) {
        LogUtils2.logI("DVR_DVRService", "stopRecording isConnected=" + this.isConnected + " , isStopEglThread =" + z + " ,myStub=" + this.myStub + " ,isStartRecord=" + MediaCodecConstant.isStartRecord);
        if (!MediaCodecConstant.isStartRecord && !z) {
            LogUtils2.logI("DVR_DVRService", "stopRecording, do nothing, is stopped");
            return;
        }
        MediaCodecConstant.isStartRecord = false;
        coverUp();
        MediaEncodeManager mediaEncodeManager = this.mediaEncodeManager;
        if (mediaEncodeManager != null) {
            mediaEncodeManager.stopEncode(z);
        }
        if (z) {
            isFirstStart = true;
            isStartWithRec0 = true;
            this.mediaEncodeManager = null;
            this.cameraManager.closeCamera();
        }
        if (!this.isConnected || this.myStub == null) {
            return;
        }
        LogUtils2.logI("DVR_DVRService", "stopRecording notify systemui");
        this.myStub.notifyReceiver(MediaCodecConstant.isStartRecord);
    }

    public void coverUp() {
        LogUtils2.logI("DVR_DVRService", "coverUp, " + this.mActivityMessenger);
        if (this.mActivityMessenger != null) {
            Message message = new Message();
            message.what = 5;
            try {
                this.mActivityMessenger.send(message);
            } catch (Exception e) {
                LogUtils2.logI("DVR_DVRService", "coverUp Exception = " + e.toString());
            }
        }
    }

    public void formatUSB() {
        LogUtils2.logI("DVR_DVRService", "formatUSB, " + this.mActivityMessenger);
        if (this.mActivityMessenger != null) {
            Message message = new Message();
            message.what = 7;
            try {
                this.mActivityMessenger.send(message);
            } catch (Exception e) {
                LogUtils2.logI("DVR_DVRService", "coverUp Exception = " + e.toString());
            }
        }
    }

    private void formatUSBReboot() {
        LogUtils2.logI("DVR_DVRService", "formatUSBReboot, " + this.mActivityMessenger);
        if (this.mActivityMessenger != null) {
            Message message = new Message();
            message.what = 18;
            try {
                this.mActivityMessenger.send(message);
            } catch (Exception e) {
                LogUtils2.logI("DVR_DVRService", "coverUp Exception = " + e.toString());
            }
        }
    }

    public void dismissWindow() {
        LogUtils2.logI("DVR_DVRService", "dismissWindow, " + this.mActivityMessenger);
        if (this.mActivityMessenger != null) {
            Message message = new Message();
            message.what = 6;
            try {
                this.mActivityMessenger.send(message);
            } catch (Exception e) {
                LogUtils2.logI("DVR_DVRService", "coverUp Exception = " + e.toString());
            }
        }
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        public MyBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            LogUtils2.logI("DVR_DVRService", "onReceive intent = " + intent.getAction());
            if (intent.getAction().equals(CanOperation.ACTION_EMERGENCY)) {
                CanOperation.isHaveEmergencyEvent = true;
                if (!MediaCodecConstant.isStartRecord) {
                    if (SaveMsg.getRecMsg(DVRService.this.getApplicationContext()) == 0) {
                        SaveMsg.setRecMsg(DVRService.this.getApplicationContext(), 3);
                    }
                    DVRService.this.startRecordingAndCheck();
                }
                if (DisplayUtils.isHaveDVRCamera(DVRService.this.getApplicationContext()) && SaveMsg.isMountedUSB) {
                    DVRService.this.mediaEncodeManager.startEmergency();
                    if ("AIR_BAG".equals(intent.getStringExtra("type"))) {
                        DVRService.this.mBuryServiceManager.panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_Emergencyvideorecord, BuryKeyConst.CH_BI_Key_pushtype, 0);
                        return;
                    } else {
                        if ("AIR_BAG".equals(intent.getStringExtra("type"))) {
                            DVRService.this.mBuryServiceManager.panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_Emergencyvideorecord, BuryKeyConst.CH_BI_Key_pushtype, 1);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            if (intent.getAction().equals(CanOperation.ACTION_POWER_OFF)) {
                DVRService.this.stopRecording(false);
            } else if (intent.getAction().equals(CanOperation.ACTION_POWER_ACC_ON)) {
                DVRService.this.startRecordingAndCheck();
            } else if (intent.getAction().equals(CanOperation.ACTION_OVER_SPEED)) {
                DVRService.this.coverUp();
            }
        }
    }

    class MyHandler extends Handler {
        String firstFileName;
        WeakReference<DVRService> mService;

        /* synthetic */ MyHandler(DVRService dVRService, Looper looper, DVRService dVRService2, ServiceConnectionC09211 serviceConnectionC09211) {
            this(looper, dVRService2);
        }

        private MyHandler(Looper looper, DVRService dVRService) {
            super(looper);
            this.mService = new WeakReference<>(dVRService);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            LogUtils2.logI("DVR_DVRService", "MyHandler handleMessage = " + message.what);
            if (this.mService.get() != null) {
                switch (message.what) {
                    case 0:
                        String str = FileUtils.recordVideoFileName;
                        this.firstFileName = str;
                        if (str != null) {
                            this.firstFileName = str.replace("tmp", "mp4");
                        }
                        DVRService.this.stopRecording(false);
                        boolean unused = DVRService.isStartWithRec0 = false;
                        sendEmptyMessageDelayed(3, 1500L);
                        break;
                    case 1:
                        DVRService.this.coverUp();
                        CameraManagerHelper unused2 = DVRService.this.cameraManager;
                        if (CameraManagerHelper.getException().booleanValue()) {
                            if (DVRService.this.isConnected && DVRService.this.myStub != null) {
                                LogUtils2.logI("DVR_DVRService", "camera error notify systemui");
                                DVRService.this.myStub.notifyReceiver(false);
                            }
                            DVRService.this.cameraManager.startCamera2(DVRService.this.cameraId);
                            break;
                        }
                        break;
                    case 2:
                        DVRService.this.stopRecording(false);
                        DVRService.this.coverUp();
                        break;
                    case 3:
                        int recMsg = SaveMsg.getRecMsg(DVRService.this.getApplicationContext());
                        LogUtils2.logI("DVR_DVRService", "MyHandler isMountedUSB = " + SaveMsg.isMountedUSB + " ,n = " + recMsg + " ,FileName=" + this.firstFileName);
                        if (recMsg != 0 && SaveMsg.isMountedUSB) {
                            DVRService.this.startRecordingAndCheck();
                            DVRService.this.coverUp();
                        }
                        USBUtil.deleteFile(USBUtil.USBPath + File.separator + USBUtil.normal + File.separator + this.firstFileName);
                        StringBuilder sb = new StringBuilder();
                        sb.append(FileUtils.getExternalPath(DVRService.this.getApplicationContext()));
                        sb.append(File.separator);
                        sb.append("event");
                        USBUtil.deleteAllVideo(sb.toString());
                        USBUtil.deleteAllVideo(FileUtils.getExternalPath(DVRService.this.getApplicationContext()) + File.separator + USBUtil.normal);
                        break;
                    case 4:
                        DVRService.this.mActivityMessenger = message.replyTo;
                        break;
                    case 8:
                        DVRService.this.startRecordingAndCheck();
                        break;
                    case 9:
                        DVRService.this.stopRecording(false);
                        break;
                    case 10:
                        if (DVRService.this.isConnected && DVRService.this.myStub != null) {
                            LogUtils2.logI("DVR_DVRService", "camera restore notify systemui");
                            DVRService.this.myStub.notifyReceiver(MediaCodecConstant.isStartRecord);
                            break;
                        }
                        break;
                    case 11:
                        if (!DVRService.this.isFastMountMessage()) {
                            DVRService.this.startRecordingAndCheck();
                            DVRService.this.dismissWindow();
                            DVRService.this.coverUp();
                            break;
                        }
                        break;
                    case 12:
                        if (!DVRService.this.isFastUnmountMessage()) {
                            DVRService.this.stopRecording(true);
                            DVRService.this.dismissWindow();
                            DVRService.this.coverUp();
                            break;
                        }
                        break;
                    case 13:
                        DVRService.this.dismissWindow();
                        DVRService.this.formatUSB();
                        break;
                    case 15:
                        if (!DisplayUtils.isHaveDVRCamera(DVRService.this.getApplicationContext())) {
                            LogUtils2.logD("DVR_DVRService", "Not Have Camera");
                            sendEmptyMessage(2);
                            sendEmptyMessageDelayed(15, 5000L);
                            break;
                        } else {
                            LogUtils2.logD("DVR_DVRService", "Have Camera");
                            sendEmptyMessage(1);
                            sendEmptyMessageDelayed(15, 2000L);
                            break;
                        }
                    case 16:
                        USBUtil.getUSBInfo(DVRService.this);
                        break;
                    case 17:
                        Toast.makeText(DVRService.this, C0903R.string.usb_write_error, 1).show();
                        break;
                }
            }
            LogUtils2.logI("DVR_DVRService", "MyHandler dvrService is null");
        }
    }

    public boolean isFastMountMessage() {
        long currentTimeMillis = System.currentTimeMillis();
        boolean z = currentTimeMillis - this.lastUsbMountedTime < 500;
        this.lastUsbMountedTime = currentTimeMillis;
        LogUtils2.logI("DVR_DVRService", "isFastMountMessage = " + z);
        return z;
    }

    public boolean isFastUnmountMessage() {
        long currentTimeMillis = System.currentTimeMillis();
        boolean z = currentTimeMillis - this.lastUsbUnmountedTime < 500;
        this.lastUsbUnmountedTime = currentTimeMillis;
        LogUtils2.logI("DVR_DVRService", "isFastUnmountMessage = " + z);
        return z;
    }
}
