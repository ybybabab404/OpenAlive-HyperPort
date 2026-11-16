package org.aliveclean.aicr;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/**
 * 手写的最小 AIDL 代理：com.xiaomi.aicr.IAiCrCoreService。
 * 交易码与 MIUI 的原始 .aidl 严格一致（1=getPluginBinder 2=releaseConnect 3=isSupport），
 * 这里只保留我们需要的代理侧，不引入任何算法实现。
 */
public interface IAiCrCoreService extends IInterface {
    String DESCRIPTOR = "com.xiaomi.aicr.IAiCrCoreService";

    IBinder getPluginBinder(ICoreServiceCallback callback, String address, String desc, String minVersion, Bundle status);

    void releaseConnect(String address);

    boolean isSupport(String desc);

    final class Proxy implements IAiCrCoreService {
        private final IBinder remote;

        private Proxy(IBinder remote) { this.remote = remote; }

        public static IAiCrCoreService asInterface(IBinder binder) {
            if (binder == null) return null;
            IInterface local = binder.queryLocalInterface(DESCRIPTOR);
            if (local instanceof IAiCrCoreService) return (IAiCrCoreService) local;
            return new Proxy(binder);
        }

        @Override public IBinder asBinder() { return remote; }

        @Override public IBinder getPluginBinder(ICoreServiceCallback callback, String address, String desc, String minVersion, Bundle status) {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                data.writeStrongInterface(callback);
                data.writeString(address);
                data.writeString(desc);
                data.writeString(minVersion);
                remote.transact(1, data, reply, 0);
                reply.readException();
                IBinder plugin = reply.readStrongBinder();
                if (reply.readInt() != 0) status.readFromParcel(reply);
                return plugin;
            } catch (android.os.RemoteException error) {
                throw new IllegalStateException("getPluginBinder failed: " + error.getMessage(), error);
            } finally {
                reply.recycle();
                data.recycle();
            }
        }

        @Override public void releaseConnect(String address) {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                data.writeString(address);
                remote.transact(2, data, reply, 0);
                reply.readException();
            } catch (android.os.RemoteException error) {
                throw new IllegalStateException("releaseConnect failed", error);
            } finally {
                reply.recycle();
                data.recycle();
            }
        }

        @Override public boolean isSupport(String desc) {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                data.writeString(desc);
                remote.transact(3, data, reply, 0);
                reply.readException();
                return reply.readInt() != 0;
            } catch (android.os.RemoteException error) {
                throw new IllegalStateException("isSupport failed", error);
            } finally {
                reply.recycle();
                data.recycle();
            }
        }
    }
}
