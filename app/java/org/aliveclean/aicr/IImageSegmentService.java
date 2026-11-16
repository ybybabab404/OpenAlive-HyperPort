package org.aliveclean.aicr;

import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 手写的最小 AIDL 代理：com.xiaomi.aicr.plugin.IImageSegmentService。
 * 交易码与 MIUI 原始 .aidl 一致；只实现我们实际要用的几个：
 * 1=initAlgorithmCachePath 2=initPersonModel 3=initSkyModel 4=segment 7=releaseAll
 * 9=segmentMainFunc 11=getSegmentMainFuncVersion 12=depthSegmentMainFunc 14=getDepthSegmentMainFuncVersion
 */
public interface IImageSegmentService extends IInterface {
    String DESCRIPTOR = "com.xiaomi.aicr.plugin.IImageSegmentService";

    int initAlgorithmCachePath();
    int initPersonModel();
    int initSkyModel();
    SegmentResult segment(Bitmap bitmap, VisionAttribute attribute);
    SegmentResult segmentMainFunc(Bitmap bitmap, VisionAttribute attribute);
    Bitmap depthSegmentMainFunc(Bitmap bitmap);
    String getSegmentMainFuncVersion();
    String getDepthSegmentMainFuncVersion();
    int releaseAll();

    final class Proxy implements IImageSegmentService {
        private final IBinder remote;

        private Proxy(IBinder remote) { this.remote = remote; }

        public static IImageSegmentService asInterface(IBinder binder) {
            if (binder == null) return null;
            IInterface local = binder.queryLocalInterface(DESCRIPTOR);
            if (local instanceof IImageSegmentService) return (IImageSegmentService) local;
            return new Proxy(binder);
        }

        @Override public IBinder asBinder() { return remote; }

        private Parcel start(int code) {
            Parcel data = Parcel.obtain();
            data.writeInterfaceToken(DESCRIPTOR);
            return data;
        }

        private Parcel call(int code, Parcel data) {
            Parcel reply = Parcel.obtain();
            try {
                remote.transact(code, data, reply, 0);
                reply.readException();
                return reply;
            } catch (android.os.RemoteException error) {
                throw new IllegalStateException("IImageSegmentService transact " + code + " failed", error);
            } finally {
                data.recycle();
            }
        }

        private int intCall(int code) {
            Parcel reply = call(code, start(code));
            try { return reply.readInt(); } finally { reply.recycle(); }
        }

        @Override public int initAlgorithmCachePath() { return intCall(1); }
        @Override public int initPersonModel() { return intCall(2); }
        @Override public int initSkyModel() { return intCall(3); }
        @Override public int releaseAll() { return intCall(7); }

        @Override public SegmentResult segment(Bitmap bitmap, VisionAttribute attribute) {
            Parcel data = start(4);
            writeObject(data, bitmap, 1);
            writeObject(data, attribute, 1);
            Parcel reply = call(4, data);
            try { return readObject(reply, SegmentResult.CREATOR); } finally { reply.recycle(); }
        }

        @Override public SegmentResult segmentMainFunc(Bitmap bitmap, VisionAttribute attribute) {
            Parcel data = start(9);
            writeObject(data, bitmap, 1);
            writeObject(data, attribute, 1);
            Parcel reply = call(9, data);
            try { return readObject(reply, SegmentResult.CREATOR); } finally { reply.recycle(); }
        }

        @Override public Bitmap depthSegmentMainFunc(Bitmap bitmap) {
            Parcel data = start(12);
            writeObject(data, bitmap, 1);
            Parcel reply = call(12, data);
            try { return readObject(reply, Bitmap.CREATOR); } finally { reply.recycle(); }
        }

        @Override public String getSegmentMainFuncVersion() {
            Parcel reply = call(11, start(11));
            try { return reply.readString(); } finally { reply.recycle(); }
        }

        @Override public String getDepthSegmentMainFuncVersion() {
            Parcel reply = call(14, start(14));
            try { return reply.readString(); } finally { reply.recycle(); }
        }
    }

    static void writeObject(Parcel parcel, Parcelable value, int flags) {
        if (value == null) { parcel.writeInt(0); return; }
        parcel.writeInt(1);
        value.writeToParcel(parcel, flags);
    }

    static <T> T readObject(Parcel parcel, Parcelable.Creator<T> creator) {
        return parcel.readInt() == 0 ? null : creator.createFromParcel(parcel);
    }
}
