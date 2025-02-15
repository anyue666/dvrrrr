package com.autolink.dvr;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.autolink.dvr.IDVRAidlCallback;

/* loaded from: classes.dex */
public interface IDVRAidlInterface extends IInterface {

    public static class Default implements IDVRAidlInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.autolink.dvr.IDVRAidlInterface
        public boolean getDVRStates() throws RemoteException {
            return false;
        }

        @Override // com.autolink.dvr.IDVRAidlInterface
        public void registerCallback(IDVRAidlCallback iDVRAidlCallback) throws RemoteException {
        }

        @Override // com.autolink.dvr.IDVRAidlInterface
        public void unregisterCallback(IDVRAidlCallback iDVRAidlCallback) throws RemoteException {
        }
    }

    boolean getDVRStates() throws RemoteException;

    void registerCallback(IDVRAidlCallback iDVRAidlCallback) throws RemoteException;

    void unregisterCallback(IDVRAidlCallback iDVRAidlCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IDVRAidlInterface {
        private static final String DESCRIPTOR = "com.autolink.dvr.IDVRAidlInterface";
        static final int TRANSACTION_getDVRStates = 1;
        static final int TRANSACTION_registerCallback = 2;
        static final int TRANSACTION_unregisterCallback = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDVRAidlInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDVRAidlInterface)) {
                return (IDVRAidlInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                boolean dVRStates = getDVRStates();
                parcel2.writeNoException();
                parcel2.writeInt(dVRStates ? 1 : 0);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                registerCallback(IDVRAidlCallback.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            }
            if (i != 3) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            unregisterCallback(IDVRAidlCallback.Stub.asInterface(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy implements IDVRAidlInterface {
            public static IDVRAidlInterface sDefaultImpl;
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.autolink.dvr.IDVRAidlInterface
            public boolean getDVRStates() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDVRStates();
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.autolink.dvr.IDVRAidlInterface
            public void registerCallback(IDVRAidlCallback iDVRAidlCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iDVRAidlCallback != null ? iDVRAidlCallback.asBinder() : null);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerCallback(iDVRAidlCallback);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.autolink.dvr.IDVRAidlInterface
            public void unregisterCallback(IDVRAidlCallback iDVRAidlCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iDVRAidlCallback != null ? iDVRAidlCallback.asBinder() : null);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterCallback(iDVRAidlCallback);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IDVRAidlInterface iDVRAidlInterface) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iDVRAidlInterface == null) {
                return false;
            }
            Proxy.sDefaultImpl = iDVRAidlInterface;
            return true;
        }

        public static IDVRAidlInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
