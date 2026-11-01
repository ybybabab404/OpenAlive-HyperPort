package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public interface IDisplayFoldListener extends IInterface {

    public static class Default implements IDisplayFoldListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDisplayFoldListener {

        public static class Proxy implements IDisplayFoldListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public Stub() {
            attachInterface(this, "android.view.IDisplayFoldListener");
        }

        public static IDisplayFoldListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.view.IDisplayFoldListener");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IDisplayFoldListener)) ? new Proxy(iBinder) : (IDisplayFoldListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.view.IDisplayFoldListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.view.IDisplayFoldListener");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            onDisplayFoldChanged(parcel.readInt(), parcel.readInt() != 0);
            return true;
        }
    }

    void onDisplayFoldChanged(int i, boolean z);
}
