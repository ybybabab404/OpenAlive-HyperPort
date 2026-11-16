package org.aliveclean.aicr;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/** 手写的最小 AIDL 桩：com.xiaomi.aicr.ICoreServiceCallback（1=onDownload 2=speed）。 */
public abstract class ICoreServiceCallback extends Binder implements IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.aicr.ICoreServiceCallback";

    public ICoreServiceCallback() { attachInterface(null, DESCRIPTOR); }

    /** 插件首次使用需要下载时回调，参数是下载通知用到的 Binder（可忽略）。 */
    protected void onDownload(IBinder binder) { }

    /** 下载进度回调。 */
    protected void speed(String path, long bytes) { }

    @Override public IBinder asBinder() { return this; }

    @Override protected boolean onTransact(int code, Parcel data, Parcel reply, int flags)
            throws android.os.RemoteException {
        if (code >= 1 && code <= 0xFFFFFF) data.enforceInterface(DESCRIPTOR);
        if (code == 1) { onDownload(data.readStrongBinder()); return true; }
        if (code == 2) { speed(data.readString(), data.readLong()); return true; }
        return super.onTransact(code, data, reply, flags);
    }
}
