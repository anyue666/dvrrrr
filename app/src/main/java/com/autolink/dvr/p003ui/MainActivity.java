package com.autolink.dvr.p003ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import com.autolink.buryservice.BuryServiceManager;
import com.autolink.buryservice.bury.consts.BuryEventConst;
import com.autolink.buryservice.bury.consts.BuryKeyConst;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.base.BaseMvvmActivity;
import com.autolink.dvr.common.can.CanOperation;
import com.autolink.dvr.common.media.MediaCodecConstant;
import com.autolink.dvr.common.media.surface.CameraManagerHelper;
import com.autolink.dvr.common.media.utils.DisplayUtils;
import com.autolink.dvr.common.media.utils.FileUtils;
import com.autolink.dvr.common.service.DVRService;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.SaveMsg;
import com.autolink.dvr.common.utils.USBUtil;
import com.autolink.dvr.databinding.ActivityMainBinding;
import com.autolink.dvr.p003ui.file.FileActivity;
import com.autolink.dvr.p003ui.setting.CheckUSBRebootDialog;
import com.autolink.dvr.p003ui.setting.ClearAllDialog;
import com.autolink.dvr.p003ui.setting.FormatUSBDialog;
import com.autolink.dvr.p003ui.setting.SettingPopupWindow;
import com.autolink.dvr.viewmodel.MainViewModel;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpStatus;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Map;

/* loaded from: classes.dex */
public class MainActivity extends BaseMvvmActivity<MainViewModel, ActivityMainBinding> implements SettingPopupWindow.OnWindowItemClickListener, FormatUSBDialog.USBFormatListener {
    private ClearAllDialog clearAllDialog;
    private FormatUSBDialog formatUSBDialog;
    private long lastClickTime;
    private Messenger mActivityMessenger;
    private BuryServiceManager mBuryServiceManager;
    private CheckUSBRebootDialog mCheckUSBRebootDialog;
    public Handler mHandler;
    private HandlerThread mHandlerThread;
    private Messenger mServiceMessenger;
    private WorkHandler mWorkHandler;
    private SettingPopupWindow popupWindow;
    private final String TAG = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();
    private final int CLICK_CHECK_TIME = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    private final int FORMAT_USB = 1;
    private final int CLEAR_ALL = 2;
    private final int CHECK_USB = 3;
    private final int GET_USB_INFO = 4;
    private final int MSG_EJECT_USB = 10;
    private ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.autolink.dvr.ui.MainActivity.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtils2.logI(MainActivity.this.TAG, "onServiceConnected ");
            MainActivity.this.mServiceMessenger = new Messenger(iBinder);
            if (MainActivity.this.mActivityMessenger == null) {
                MainActivity.this.mActivityMessenger = new Messenger(MainActivity.this.mHandler);
            }
            Message message = new Message();
            message.what = 4;
            message.replyTo = MainActivity.this.mActivityMessenger;
            try {
                MainActivity.this.mServiceMessenger.send(message);
            } catch (Exception e) {
                LogUtils2.logI(MainActivity.this.TAG, "onServiceConnected Messenger Exception = " + e.toString());
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils2.logI(MainActivity.this.TAG, "onServiceDisconnected ");
        }
    };

    @Override // com.autolink.dvr.common.base.BaseMvvmActivity
    protected int getLayoutId() {
        return C0903R.layout.activity_main;
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseActivity, com.autolink.dvr.common.base.hmi.IBaseView
    public void initViewObservable() {
    }

    @Override // com.autolink.dvr.ui.setting.SettingPopupWindow.OnWindowItemClickListener
    public void onClickRestore() {
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseActivity, com.autolink.dvr.common.base.hmi.IBaseView
    public void initView() {
        if (SaveMsg.getProperties().equals("0")) {
            LogUtils2.logI(this.TAG, "initView  return");
            return;
        }
        initPermissions();
        bindService(new Intent(this, (Class<?>) DVRService.class), this.serviceConnection, 1);
        todo();
        this.mHandler = new MyHandler(Looper.getMainLooper(), this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        LogUtils2.logI(this.TAG, "onStart ");
        coverUp();
        this.mWorkHandler.sendEmptyMessage(3);
    }

    @Override // com.autolink.dvr.common.base.BaseMvvmActivity, com.autolink.dvr.common.base.hmi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        this.mBuryServiceManager = BuryServiceManager.getInstance();
        if (getIntent().hasCategory("android.intent.category.LAUNCHER")) {
            this.mBuryServiceManager.panoramicImageDVRBury("CH_BI_Event_APPList_IQY_Open", BuryKeyConst.CH_BI_Key_pushtype, 0);
        } else {
            this.mBuryServiceManager.panoramicImageDVRBury("CH_BI_Event_APPList_IQY_Open", BuryKeyConst.CH_BI_Key_pushtype, 1);
        }
        HandlerThread handlerThread = new HandlerThread("work_thread");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mWorkHandler = new WorkHandler(this.mHandlerThread.getLooper());
        super.onCreate(bundle);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        FormatUSBDialog formatUSBDialog = this.formatUSBDialog;
        if (formatUSBDialog != null) {
            formatUSBDialog.finish();
            this.formatUSBDialog = null;
        }
        CheckUSBRebootDialog checkUSBRebootDialog = this.mCheckUSBRebootDialog;
        if (checkUSBRebootDialog != null) {
            checkUSBRebootDialog.finish();
            this.mCheckUSBRebootDialog = null;
        }
        ClearAllDialog clearAllDialog = this.clearAllDialog;
        if (clearAllDialog != null) {
            clearAllDialog.finish();
            this.clearAllDialog = null;
        }
        SettingPopupWindow settingPopupWindow = this.popupWindow;
        if (settingPopupWindow != null) {
            settingPopupWindow.finish();
            this.popupWindow = null;
        }
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        LogUtils2.logI(this.TAG, "onDestroy isStartRecord = " + MediaCodecConstant.isStartRecord);
        unbindService(this.serviceConnection);
        Message message = new Message();
        message.what = 4;
        message.replyTo = null;
        try {
            this.mServiceMessenger.send(message);
        } catch (Exception e) {
            LogUtils2.logI(this.TAG, "onDestroy Messenger Exception = " + e.toString());
        }
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void coverUp() {
        LogUtils2.logI(this.TAG, "coverUp isMountedUSB = " + SaveMsg.isMountedUSB + " ,isSpeedOver15 = " + CanOperation.isSpeedOver15 + " , isStartRecord = " + MediaCodecConstant.isStartRecord + ", isNeedFormatUSB = " + USBUtil.isNeedFormatUSB);
        if (DisplayUtils.isHaveDVRCamera(this) && !CameraManagerHelper.getException().booleanValue()) {
            if (SaveMsg.isMountedUSB && !USBUtil.isNeedFormatUSB) {
                if (CanOperation.isSpeedOver15) {
                    ((ActivityMainBinding) this.mBinding).activityMainCloseIv.setBackgroundResource(C0903R.drawable.ic_close);
                    ((ActivityMainBinding) this.mBinding).activityMainCoverupIv.setBackgroundResource(C0903R.drawable.ic_img_speed);
                    ((ActivityMainBinding) this.mBinding).activityMainCoverupIv.setVisibility(0);
                    ((ActivityMainBinding) this.mBinding).activityMainCoverupTv.setText(C0903R.string.main_cover_speed);
                    ((ActivityMainBinding) this.mBinding).activityMainCoverupTv.setVisibility(0);
                    ((ActivityMainBinding) this.mBinding).activityMainCamera.setVisibility(8);
                    ((ActivityMainBinding) this.mBinding).activityMainBg.setVisibility(8);
                    ((ActivityMainBinding) this.mBinding).activityMainVideoStateIv.setVisibility(8);
                    ((ActivityMainBinding) this.mBinding).activityMainVideoStateTv.setVisibility(8);
                    return;
                }
                ((ActivityMainBinding) this.mBinding).activityMainCloseIv.setBackgroundResource(C0903R.drawable.ic_main_close);
                ((ActivityMainBinding) this.mBinding).activityMainCoverupIv.setVisibility(8);
                ((ActivityMainBinding) this.mBinding).activityMainCoverupTv.setVisibility(8);
                ((ActivityMainBinding) this.mBinding).activityMainCamera.setVisibility(0);
                ((ActivityMainBinding) this.mBinding).activityMainBg.setVisibility(0);
                ((ActivityMainBinding) this.mBinding).activityMainVideoStateIv.setVisibility(0);
                ((ActivityMainBinding) this.mBinding).activityMainVideoStateTv.setVisibility(0);
                if (!MediaCodecConstant.isStartRecord || SaveMsg.getRecMsg(getApplicationContext()) == 0) {
                    ((ActivityMainBinding) this.mBinding).activityMainVideoStateTv.setText(C0903R.string.main_video_state_off);
                    return;
                } else {
                    ((ActivityMainBinding) this.mBinding).activityMainVideoStateTv.setText(C0903R.string.main_video_state_on);
                    return;
                }
            }
            ((ActivityMainBinding) this.mBinding).activityMainCloseIv.setBackgroundResource(C0903R.drawable.ic_close);
            ((ActivityMainBinding) this.mBinding).activityMainCoverupIv.setBackgroundResource(C0903R.drawable.ic_img_usb_nor);
            ((ActivityMainBinding) this.mBinding).activityMainCoverupIv.setVisibility(0);
            ((ActivityMainBinding) this.mBinding).activityMainCoverupTv.setText(C0903R.string.main_cover_usb);
            ((ActivityMainBinding) this.mBinding).activityMainCoverupTv.setVisibility(0);
            ((ActivityMainBinding) this.mBinding).activityMainCamera.setVisibility(8);
            ((ActivityMainBinding) this.mBinding).activityMainBg.setVisibility(8);
            ((ActivityMainBinding) this.mBinding).activityMainVideoStateIv.setVisibility(8);
            ((ActivityMainBinding) this.mBinding).activityMainVideoStateTv.setVisibility(8);
            return;
        }
        ((ActivityMainBinding) this.mBinding).activityMainCloseIv.setBackgroundResource(C0903R.drawable.ic_close);
        ((ActivityMainBinding) this.mBinding).activityMainCoverupIv.setBackgroundResource(C0903R.drawable.ic_img_camera_error);
        ((ActivityMainBinding) this.mBinding).activityMainCoverupIv.setVisibility(0);
        ((ActivityMainBinding) this.mBinding).activityMainCoverupTv.setText(C0903R.string.main_cover_camera);
        ((ActivityMainBinding) this.mBinding).activityMainCoverupTv.setVisibility(8);
        ((ActivityMainBinding) this.mBinding).activityMainCamera.setVisibility(8);
        ((ActivityMainBinding) this.mBinding).activityMainBg.setVisibility(8);
        ((ActivityMainBinding) this.mBinding).activityMainVideoStateIv.setVisibility(8);
        ((ActivityMainBinding) this.mBinding).activityMainVideoStateTv.setVisibility(8);
    }

    protected void startRecording() {
        LogUtils2.logI(this.TAG, "startRecording, " + this.mServiceMessenger);
        if (this.mServiceMessenger != null) {
            Message message = new Message();
            message.what = 8;
            try {
                this.mServiceMessenger.send(message);
            } catch (Exception e) {
                LogUtils2.logI(this.TAG, "startRecording Exception = " + e.toString());
            }
        }
    }

    protected void stopRecording() {
        LogUtils2.logI(this.TAG, "stopRecording, " + this.mServiceMessenger);
        if (this.mServiceMessenger != null) {
            Message message = new Message();
            message.what = 9;
            try {
                this.mServiceMessenger.send(message);
            } catch (Exception e) {
                LogUtils2.logI(this.TAG, "stopRecording Exception = " + e.toString());
            }
        }
    }

    @Override // com.autolink.dvr.ui.setting.SettingPopupWindow.OnWindowItemClickListener
    public void onClickCloseRec() {
        LogUtils2.logI(this.TAG, "onClickCloseRec , isStartRecord = " + MediaCodecConstant.isStartRecord);
        stopRecording();
        this.mBuryServiceManager.panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_Option, BuryKeyConst.CH_BI_Key_autorecord, 0);
    }

    @Override // com.autolink.dvr.ui.setting.SettingPopupWindow.OnWindowItemClickListener
    public void onClickOpenRec() {
        LogUtils2.logI(this.TAG, "onClickOpenRec , isStartRecord = " + MediaCodecConstant.isStartRecord);
        startRecording();
        this.mBuryServiceManager.panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_Option, BuryKeyConst.CH_BI_Key_autorecord, 1);
    }

    @Override // com.autolink.dvr.ui.setting.SettingPopupWindow.OnWindowItemClickListener
    public void onClickClearAll(ClearAllDialog clearAllDialog) {
        this.clearAllDialog = clearAllDialog;
    }

    @Override // com.autolink.dvr.ui.setting.SettingPopupWindow.OnWindowItemClickListener
    public void onClearAll() {
        this.mWorkHandler.sendEmptyMessage(2);
    }

    @Override // com.autolink.dvr.ui.setting.SettingPopupWindow.OnWindowItemClickListener
    public void onClickEjectUSB() {
        LogUtils2.logI(this.TAG, "onClickEjectUSB , isStartRecord = " + MediaCodecConstant.isStartRecord);
        stopRecording();
        ((ActivityMainBinding) this.mBinding).activityMainFileIv.setVisibility(8);
        this.mHandler.sendEmptyMessageDelayed(10, 1000L);
    }

    private void initPermissions() {
        registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.autolink.dvr.ui.MainActivity$$ExternalSyntheticLambda0
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                MainActivity.this.m634lambda$initPermissions$0$comautolinkdvruiMainActivity((Map) obj);
            }
        }).launch(new String[]{"android.permission.RECORD_AUDIO", "android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"});
    }

    /* renamed from: lambda$initPermissions$0$com-autolink-dvr-ui-MainActivity, reason: not valid java name */
    /* synthetic */ void m634lambda$initPermissions$0$comautolinkdvruiMainActivity(Map map) {
        Object obj = map.get("android.permission.CAMERA");
        if (obj == null || !obj.equals(true)) {
            LogUtils2.logI(this.TAG, "initPermissions camera Permission request denied");
            Toast.makeText(this, "Camera Permission request denied", 0).show();
            finish();
        }
        Object obj2 = map.get("android.permission.RECORD_AUDIO");
        if (obj2 == null || !obj2.equals(true)) {
            LogUtils2.logI(this.TAG, "initPermissions audio Permission request denied");
            Toast.makeText(this, "Audio Permission request denied", 0).show();
            finish();
        }
        Object obj3 = map.get("android.permission.WRITE_EXTERNAL_STORAGE");
        if (obj3 == null || !obj3.equals(true)) {
            LogUtils2.logI(this.TAG, "initPermissions storage Permission request denied");
            Toast.makeText(this, "Storage Permission request denied", 0).show();
            finish();
        }
    }

    private void todo() {
        ((ActivityMainBinding) this.mBinding).activityMainCloseIv.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.MainActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LogUtils2.logI(MainActivity.this.TAG, "onClick activityMainTopBackIv");
                MainActivity.this.finish();
                MainActivity.this.mBuryServiceManager.panoramicImageDVRBury("CH_BI_Event_APPList_IQY_Open", BuryKeyConst.CH_BI_Key_pushtype, 0);
            }
        });
        ((ActivityMainBinding) this.mBinding).activityMainSettingIv.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.MainActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LogUtils2.logI(MainActivity.this.TAG, "onClick activityMainBottomSettingIv");
                if (MainActivity.this.isFastClick()) {
                    return;
                }
                MainActivity.this.popupWindow = new SettingPopupWindow();
                MainActivity.this.popupWindow.setOnWindowItemClickListener(MainActivity.this);
                if (MainActivity.this.mWorkHandler.hasMessages(4)) {
                    MainActivity.this.mWorkHandler.removeMessages(4);
                }
                MainActivity.this.mWorkHandler.sendEmptyMessage(4);
            }
        });
        ((ActivityMainBinding) this.mBinding).activityMainFileIv.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.MainActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LogUtils2.logI(MainActivity.this.TAG, "onClick activityMainBottomFileIv");
                if (MainActivity.this.isFastClick()) {
                    return;
                }
                MainActivity.this.startActivity(FileActivity.class);
                MainActivity.this.mBuryServiceManager.panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_FileManager_Open, null, 0);
            }
        });
        ((ActivityMainBinding) this.mBinding).activityMainVideoStateTv.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.MainActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void formatUSB() {
        LogUtils2.logI(this.TAG, "formatUSB");
        if (this.formatUSBDialog != null) {
            return;
        }
        FormatUSBDialog formatUSBDialog = new FormatUSBDialog();
        this.formatUSBDialog = formatUSBDialog;
        formatUSBDialog.setUSBFormatListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissWindow() {
        LogUtils2.logI(this.TAG, "dismissWindow");
        SettingPopupWindow settingPopupWindow = this.popupWindow;
        if (settingPopupWindow != null) {
            settingPopupWindow.finish();
            this.popupWindow = null;
        }
        ClearAllDialog clearAllDialog = this.clearAllDialog;
        if (clearAllDialog != null) {
            clearAllDialog.finish();
            this.clearAllDialog = null;
        }
        FormatUSBDialog formatUSBDialog = this.formatUSBDialog;
        if (formatUSBDialog != null) {
            formatUSBDialog.finish();
            this.formatUSBDialog = null;
        }
        CheckUSBRebootDialog checkUSBRebootDialog = this.mCheckUSBRebootDialog;
        if (checkUSBRebootDialog != null) {
            checkUSBRebootDialog.finish();
            this.mCheckUSBRebootDialog = null;
        }
    }

    @Override // com.autolink.dvr.ui.setting.FormatUSBDialog.USBFormatListener
    public void onUSBFromat() {
        this.mWorkHandler.sendEmptyMessage(1);
    }

    @Override // com.autolink.dvr.ui.setting.FormatUSBDialog.USBFormatListener
    public void onUSBEject() {
        this.mHandler.sendEmptyMessage(10);
    }

    class WorkHandler extends Handler {
        private WorkHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 1) {
                LogUtils2.logI(MainActivity.this.TAG, "format usb");
                USBUtil.formatUSB(MainActivity.this);
                MainActivity.this.mHandler.post(new Runnable() { // from class: com.autolink.dvr.ui.MainActivity.WorkHandler.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(DVRApplication.getInstance(), C0903R.string.usb_format_yes, 0).show();
                    }
                });
                return;
            }
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        return;
                    }
                    USBUtil.getUSBInfo(MainActivity.this);
                    if (MainActivity.this.popupWindow == null || MainActivity.this.popupWindow.getIsRemoved()) {
                        return;
                    }
                    MainActivity.this.mHandler.post(new Runnable() { // from class: com.autolink.dvr.ui.MainActivity.WorkHandler.4
                        @Override // java.lang.Runnable
                        public void run() {
                            MainActivity.this.popupWindow.setStoreSpace();
                        }
                    });
                    return;
                }
                USBUtil.getUSBInfo(MainActivity.this);
                if (SaveMsg.isMountedUSB && USBUtil.isHaveOtherFile(USBUtil.USB_PATH)) {
                    if (MediaCodecConstant.isStartRecord) {
                        MainActivity.this.stopRecording();
                    }
                    MainActivity.this.mHandler.sendEmptyMessage(7);
                }
                MainActivity.this.mHandler.post(new Runnable() { // from class: com.autolink.dvr.ui.MainActivity.WorkHandler.3
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.coverUp();
                        if (!DisplayUtils.isHaveDVRCamera(MainActivity.this) || CameraManagerHelper.getException().booleanValue()) {
                            Toast.makeText(DVRApplication.getInstance(), C0903R.string.main_cover_camera, 0).show();
                        }
                    }
                });
                return;
            }
            LogUtils2.logI(MainActivity.this.TAG, "clearAll");
            USBUtil.deleteAllVideo(USBUtil.USBPath + File.separator + "event");
            USBUtil.deleteAllVideo(USBUtil.USBPath + File.separator + USBUtil.normal);
            USBUtil.deleteAllVideo(FileUtils.getExternalPath(MainActivity.this) + File.separator + "event");
            USBUtil.deleteAllVideo(FileUtils.getExternalPath(MainActivity.this) + File.separator + USBUtil.normal);
            MainActivity.this.mBuryServiceManager.panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_Option, BuryKeyConst.CH_BI_Key_OneClickClear, 0);
            MainActivity.this.mHandler.post(new Runnable() { // from class: com.autolink.dvr.ui.MainActivity.WorkHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(DVRApplication.getInstance(), C0903R.string.settings_clear_all_text, 0).show();
                }
            });
        }
    }

    class MyHandler extends Handler {
        WeakReference<MainActivity> mActivity;

        private MyHandler(Looper looper, MainActivity mainActivity) {
            super(looper);
            this.mActivity = new WeakReference<>(mainActivity);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            LogUtils2.logI(MainActivity.this.TAG, "MyHandler handleMessage = " + message.what);
            if (this.mActivity.get() == null) {
                LogUtils2.logI(MainActivity.this.TAG, "MyHandler mainActivity is null");
                return;
            }
            int i = message.what;
            if (i == 5) {
                MainActivity.this.coverUp();
                return;
            }
            if (i == 6) {
                if (MainActivity.this.isFastClick()) {
                    return;
                }
                MainActivity.this.dismissWindow();
                return;
            }
            if (i == 7) {
                MainActivity.this.formatUSB();
                return;
            }
            if (i != 10) {
                if (i != 18) {
                    return;
                }
                MainActivity.this.mHandler.post(new Runnable() { // from class: com.autolink.dvr.ui.MainActivity.MyHandler.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (MainActivity.this.mCheckUSBRebootDialog == null) {
                            MainActivity.this.mCheckUSBRebootDialog = new CheckUSBRebootDialog();
                        }
                        MainActivity.this.mCheckUSBRebootDialog.setUSBFormatListener(new CheckUSBRebootDialog.USBRebootListener() { // from class: com.autolink.dvr.ui.MainActivity.MyHandler.1.1
                            @Override // com.autolink.dvr.ui.setting.CheckUSBRebootDialog.USBRebootListener
                            public void onUSBReboot() {
                                Intent launchIntentForPackage = MainActivity.this.getPackageManager().getLaunchIntentForPackage(MainActivity.this.getPackageName());
                                launchIntentForPackage.addFlags(335577088);
                                MainActivity.this.startActivity(launchIntentForPackage);
                                Process.killProcess(Process.myPid());
                                System.exit(0);
                            }
                        });
                    }
                });
            } else if (MediaCodecConstant.mediaMuxerState == 3) {
                LogUtils2.logI(MainActivity.this.TAG, "Waiting MediaMuxer release finish");
                sendEmptyMessageDelayed(10, 200L);
            } else {
                USBUtil.unmountUSB(MainActivity.this);
                ((ActivityMainBinding) MainActivity.this.mBinding).activityMainFileIv.setVisibility(0);
                Toast.makeText(DVRApplication.getInstance(), C0903R.string.settings_eject_usb_text, 0).show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFastClick() {
        long currentTimeMillis = System.currentTimeMillis();
        boolean z = currentTimeMillis - this.lastClickTime < 500;
        this.lastClickTime = currentTimeMillis;
        LogUtils2.logI(this.TAG, "isFastClick = " + z);
        return z;
    }
}
