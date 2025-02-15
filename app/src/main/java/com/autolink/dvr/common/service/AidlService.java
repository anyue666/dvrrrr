package com.autolink.dvr.common.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import com.autolink.dvr.IDVRAidlCallback;
import com.autolink.dvr.IDVRAidlInterface;
import com.autolink.dvr.common.media.MediaCodecConstant;
import com.autolink.dvr.common.utils.LogUtils2;

/* loaded from: classes.dex */
public class AidlService extends Service {
    private final String TAG = "DVR_AidlService";
    private RemoteCallbackList<IDVRAidlCallback> mCallbackList = new RemoteCallbackList<>();
    private MyStub myStub;

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        LogUtils2.logI("DVR_AidlService", "onCreate");
        this.myStub = new MyStub();
    }

    public class MyStub extends IDVRAidlInterface.Stub {
        public MyStub() {
        }

        @Override // com.autolink.dvr.IDVRAidlInterface
        public boolean getDVRStates() throws RemoteException {
            LogUtils2.logI("DVR_AidlService", "getDVRStates isStartRecord = " + MediaCodecConstant.isStartRecord);
            return MediaCodecConstant.isStartRecord;
        }

        @Override // com.autolink.dvr.IDVRAidlInterface
        public void registerCallback(IDVRAidlCallback iDVRAidlCallback) throws RemoteException {
            LogUtils2.logI("DVR_AidlService", "registerCallback ");
            AidlService.this.mCallbackList.register(iDVRAidlCallback);
        }

        @Override // com.autolink.dvr.IDVRAidlInterface
        public void unregisterCallback(IDVRAidlCallback iDVRAidlCallback) throws RemoteException {
            LogUtils2.logI("DVR_AidlService", "unregisterCallback ");
            AidlService.this.mCallbackList.unregister(iDVRAidlCallback);
        }

        public void notifyReceiver(boolean z) {
            LogUtils2.logI("DVR_AidlService", "notifyReceiver isStartRecord = " + z);
            synchronized (AidlService.this.mCallbackList) {
                int beginBroadcast = AidlService.this.mCallbackList.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    IDVRAidlCallback iDVRAidlCallback = (IDVRAidlCallback) AidlService.this.mCallbackList.getBroadcastItem(i);
                    if (iDVRAidlCallback != null) {
                        try {
                            iDVRAidlCallback.setDVRStates(z);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
                AidlService.this.mCallbackList.finishBroadcast();
            }
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        LogUtils2.logI("DVR_AidlService", "onBind ");
        return this.myStub.asBinder();
    }
}
