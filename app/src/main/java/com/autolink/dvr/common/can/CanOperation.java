package com.autolink.dvr.common.can;

import android.car.Car;
import android.car.hardware.power.CarPowerManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.UserHandle;
import com.autolink.adapterinterface.car.ALCarPropertyEvent;
import com.autolink.adaptermanager.ALManagerFactory;
import com.autolink.adaptermanager.IALManager;
import com.autolink.adaptermanager.car.ALCarManager;
import com.autolink.adaptermanager.car.ALVehicleControlProperty;
import com.autolink.adaptermanager.clusterinteraction.ALClusterInteractionManager;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.SaveMsg;
import java.util.concurrent.CompletableFuture;

/* loaded from: classes.dex */
public class CanOperation implements ALCarManager.IALCmdListener, ALCarManager.IALCarPropListener, IOperation, CarPowerManager.CarPowerStateListenerWithCompletion, IALManager.ServiceConnectionListener, ALCarManager.IALExtCarPropListener {
    public static final String ACTION_EMERGENCY = "android.intent.action.ACTION_EMERGENCY";
    public static final String ACTION_OVER_SPEED = "android.intent.action.ACTION_OVER_SPEED";
    public static final String ACTION_POWER_ACC_ON = "android.intent.action.ACTION_POWER_ACC_ON";
    public static final String ACTION_POWER_OFF = "android.intent.action.ACTION_POWER_OFF";
    public static boolean OverlayAEBInformation = false;
    public static boolean OverlayAIRBAGInformation = false;
    private static CanOperation carOperation = null;
    public static int doubleFlashLight = 0;
    public static boolean isHaveEmergencyEvent = false;
    public static boolean isNeedToast = true;
    public static boolean isSpeedOver15 = false;
    public static int turnLeftLight = 0;
    public static int turnRightLight = 0;
    public static int vehiclePowerMode = -1;
    private final String TAG = "DVR_CanOperation";
    private final int TIME_DELAY_GETSPEED = 1000;
    private ALCarManager alCarManager;
    private ALClusterInteractionManager alClusterInteractionManager;
    private Context mContext;
    private Handler mHandler;
    private HandlerThread mHandlerThread;

    public static CanOperation getInstance() {
        if (carOperation == null) {
            carOperation = new CanOperation(DVRApplication.getInstance());
        }
        return carOperation;
    }

    public CanOperation(Context context) {
        this.mContext = null;
        LogUtils2.logI("DVR_CanOperation", "CanOperation ");
        this.mContext = context;
        ALCarManager createCar = ALCarManager.createCar(context);
        this.alCarManager = createCar;
        if (createCar != null) {
            createCar.registALCMDListener(24, this);
            this.alCarManager.setALCarExtPropListener(this);
        } else {
            LogUtils2.logE("DVR_CanOperation", "ALCarManager.createCar return null");
        }
        this.alClusterInteractionManager = (ALClusterInteractionManager) ALManagerFactory.getInstance(context).getManager(ALManagerFactory.CI_SERVICE, this);
        ((CarPowerManager) Car.createCar(context).getCarManager("power")).setListenerWithCompletion(this);
    }

    @Override // com.autolink.dvr.common.can.IOperation
    public void setCanSignal(int i, int i2) {
        LogUtils2.logI("DVR_CanOperation", "setCanSignal key = " + i + " ,value = " + i2);
        ALCarManager aLCarManager = this.alCarManager;
        if (aLCarManager != null) {
            aLCarManager.canSetVehicleParam(i, i2);
        }
    }

    @Override // com.autolink.dvr.common.can.IOperation
    public int getCanSignal(int i) {
        LogUtils2.logI("DVR_CanOperation", "setCanSignal key = " + i);
        ALCarManager aLCarManager = this.alCarManager;
        if (aLCarManager != null) {
            return aLCarManager.canGetVehicleParam(i);
        }
        return -999;
    }

    @Override // com.autolink.adaptermanager.car.ALCarManager.IALCarPropListener
    public void onALCarPropChanged(ALCarPropertyEvent aLCarPropertyEvent) {
        if (803 == aLCarPropertyEvent.getId()) {
            LogUtils2.logI("DVR_CanOperation", "onALCarPropChanged SPEED value = " + aLCarPropertyEvent.getValue());
            if (Float.compare(((Float) aLCarPropertyEvent.getValue()).floatValue(), 15.0f) >= 0) {
                if (isSpeedOver15) {
                    return;
                }
                LogUtils2.logI("DVR_CanOperation", "onALCarPropChanged SPEED over");
                isSpeedOver15 = true;
                this.mContext.sendBroadcastAsUser(new Intent(ACTION_OVER_SPEED), UserHandle.getUserHandleForUid(-1));
                return;
            }
            if (isSpeedOver15) {
                LogUtils2.logI("DVR_CanOperation", "onALCarPropChanged SPEED not over");
                isSpeedOver15 = false;
                this.mContext.sendBroadcastAsUser(new Intent(ACTION_OVER_SPEED), UserHandle.getUserHandleForUid(-1));
            }
        }
    }

    @Override // com.autolink.adaptermanager.car.ALCarManager.IALExtCarPropListener
    public void onALCarEventChanged(ALCarPropertyEvent aLCarPropertyEvent) {
        int id = aLCarPropertyEvent.getId();
        if (2010 == id) {
            LogUtils2.logI("DVR_CanOperation", "onALCarEventChanged VEHICLE_PROPERTY_EXT_VENDOR_AIRBAG_FAIL = " + aLCarPropertyEvent.getValue());
            if (((Integer) aLCarPropertyEvent.getValue()).intValue() == 2 || ((Integer) aLCarPropertyEvent.getValue()).intValue() == 3) {
                OverlayAIRBAGInformation = true;
            } else if (((Integer) aLCarPropertyEvent.getValue()).intValue() == 0) {
                OverlayAIRBAGInformation = false;
            }
        }
        if (2011 == id) {
            LogUtils2.logI("DVR_CanOperation", "onALCarEventChanged VEHICLE_PROPERTY_EXT_VENDOR_AEB_MODE = " + aLCarPropertyEvent.getValue());
            if (((Integer) aLCarPropertyEvent.getValue()).intValue() == 2) {
                OverlayAEBInformation = true;
            } else {
                OverlayAEBInformation = false;
            }
        }
    }

    @Override // com.autolink.adaptermanager.car.ALCarManager.IALCmdListener
    public void onCmdChanged(byte b, byte b2, short s, int i) {
        if (s == 649) {
            LogUtils2.logI("DVR_CanOperation", "onCmdChanged VEHICLE_PROPERTY_AEB_DEC = " + i);
            if (i == 1) {
                Intent intent = new Intent(ACTION_EMERGENCY);
                intent.putExtra("type", "AEB_DEC");
                this.mContext.sendBroadcast(intent);
            }
            return;
        }
        if (s == 650) {
            LogUtils2.logI("DVR_CanOperation", "onCmdChanged VEHICLE_PROPERTY_KEY_STS  = " + i);
            if (i == 0 || i == 3) {
                vehiclePowerMode = i;
                return;
            }
            vehiclePowerMode = i;
            isNeedToast = true;
            if (SaveMsg.getRecMsg(this.mContext) != 0) {
                this.mContext.sendBroadcast(new Intent(ACTION_POWER_ACC_ON));
                return;
            }
            return;
        }
        switch (s) {
            case ALVehicleControlProperty.VEHICLE_PROPERTY_AIR_BAG /* 631 */:
                LogUtils2.logI("DVR_CanOperation", "onCmdChanged VEHICLE_PROPERTY_AIR_BAG = " + i);
                if (i >= 1 && i <= 10) {
                    Intent intent2 = new Intent(ACTION_EMERGENCY);
                    intent2.putExtra("type", "AIR_BAG");
                    this.mContext.sendBroadcast(intent2);
                    break;
                }
                break;
            case ALVehicleControlProperty.VEHICLE_PROPERTY_LEFT_TURN_LIGHT /* 632 */:
                LogUtils2.logI("DVR_CanOperation", "onCmdChanged VEHICLE_PROPERTY_LEFT_TURN_LIGHT = " + i);
                if (i == 0) {
                    turnLeftLight = 0;
                    break;
                } else {
                    turnLeftLight = 1;
                    break;
                }
            case ALVehicleControlProperty.VEHICLE_PROPERTY_RIGHT_TURN_LIGHT /* 633 */:
                LogUtils2.logI("DVR_CanOperation", "onCmdChanged VEHICLE_PROPERTY_RIGHT_TURN_LIGHT = " + i);
                if (i == 0) {
                    turnRightLight = 0;
                    break;
                } else {
                    turnRightLight = 2;
                    break;
                }
            case ALVehicleControlProperty.VEHICLE_PROPERTY_RIGHT_HAZARD_LIGHT /* 634 */:
                LogUtils2.logI("DVR_CanOperation", "onCmdChanged VEHICLE_PROPERTY_RIGHT_HAZARD_LIGHT = " + i);
                doubleFlashLight = i;
                break;
        }
    }

    public void onDestroy() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
        }
        this.alClusterInteractionManager.unbindService(this.mContext);
    }

    @Override // android.car.hardware.power.CarPowerManager.CarPowerStateListenerWithCompletion
    public void onStateChanged(int i, CompletableFuture<Void> completableFuture) {
        if (i == 2) {
            this.mContext.sendBroadcast(new Intent(ACTION_POWER_OFF));
            LogUtils2.logI("DVR_CanOperation", "Enter str mode");
        } else if (i == 3) {
            this.mContext.sendBroadcast(new Intent(ACTION_POWER_ACC_ON));
            LogUtils2.logI("DVR_CanOperation", "Exit str mode");
        }
    }

    @Override // com.autolink.adaptermanager.IALManager.ServiceConnectionListener
    public void onServiceConnected() {
        HandlerThread handlerThread = new HandlerThread("getspeed_thread");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        Handler handler = new Handler(this.mHandlerThread.getLooper());
        this.mHandler = handler;
        handler.postDelayed(new Runnable() { // from class: com.autolink.dvr.common.can.CanOperation.1
            RunnableC09101() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (Float.compare(CanOperation.this.alClusterInteractionManager != null ? CanOperation.this.alClusterInteractionManager.getCurrentSpeed() : 0.0f, 15.0f) >= 0) {
                    if (!CanOperation.isSpeedOver15) {
                        LogUtils2.logI("DVR_CanOperation", "onALCarPropChanged SPEED over");
                        CanOperation.isSpeedOver15 = true;
                        CanOperation.this.mContext.sendBroadcastAsUser(new Intent(CanOperation.ACTION_OVER_SPEED), UserHandle.getUserHandleForUid(-1));
                    }
                } else if (CanOperation.isSpeedOver15) {
                    LogUtils2.logI("DVR_CanOperation", "onALCarPropChanged SPEED not over");
                    CanOperation.isSpeedOver15 = false;
                    CanOperation.this.mContext.sendBroadcastAsUser(new Intent(CanOperation.ACTION_OVER_SPEED), UserHandle.getUserHandleForUid(-1));
                }
                CanOperation.this.mHandler.postDelayed(this, 1000L);
            }
        }, 1000L);
    }

    /* renamed from: com.autolink.dvr.common.can.CanOperation$1 */
    class RunnableC09101 implements Runnable {
        RunnableC09101() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (Float.compare(CanOperation.this.alClusterInteractionManager != null ? CanOperation.this.alClusterInteractionManager.getCurrentSpeed() : 0.0f, 15.0f) >= 0) {
                if (!CanOperation.isSpeedOver15) {
                    LogUtils2.logI("DVR_CanOperation", "onALCarPropChanged SPEED over");
                    CanOperation.isSpeedOver15 = true;
                    CanOperation.this.mContext.sendBroadcastAsUser(new Intent(CanOperation.ACTION_OVER_SPEED), UserHandle.getUserHandleForUid(-1));
                }
            } else if (CanOperation.isSpeedOver15) {
                LogUtils2.logI("DVR_CanOperation", "onALCarPropChanged SPEED not over");
                CanOperation.isSpeedOver15 = false;
                CanOperation.this.mContext.sendBroadcastAsUser(new Intent(CanOperation.ACTION_OVER_SPEED), UserHandle.getUserHandleForUid(-1));
            }
            CanOperation.this.mHandler.postDelayed(this, 1000L);
        }
    }

    @Override // com.autolink.adaptermanager.IALManager.ServiceConnectionListener
    public void onServiceDisconnected() {
        LogUtils2.logW("DVR_CanOperation", "service connect fail");
    }
}
