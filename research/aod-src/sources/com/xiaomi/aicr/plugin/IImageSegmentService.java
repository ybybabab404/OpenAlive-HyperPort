package com.xiaomi.aicr.plugin;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.xiaomi.aicr.vision.VisionAttribute;
import com.xiaomi.aicr.vision.imagesegment.SegmentResult;

/* JADX INFO: loaded from: classes2.dex */
public interface IImageSegmentService extends IInterface {

    public static class Default implements IImageSegmentService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public Bitmap depthSegmentMainFunc(Bitmap bitmap) {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public String getDepthSegmentMainFuncVersion() {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public int initAlgorithmCachePath() {
            return 0;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public int initPersonModel() {
            return 0;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public int initSkyModel() {
            return 0;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public SegmentResult segment(Bitmap bitmap, VisionAttribute visionAttribute) {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImageSegmentService {

        public static class Proxy implements IImageSegmentService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public Bitmap depthSegmentMainFunc(Bitmap bitmap) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.aicr.plugin.IImageSegmentService");
                    _Parcel.writeTypedObject(parcelObtain, bitmap, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bitmap) _Parcel.readTypedObject(parcelObtain2, Bitmap.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public String getDepthSegmentMainFuncVersion() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.aicr.plugin.IImageSegmentService");
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public int initAlgorithmCachePath() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.aicr.plugin.IImageSegmentService");
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public int initPersonModel() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.aicr.plugin.IImageSegmentService");
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public int initSkyModel() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.aicr.plugin.IImageSegmentService");
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public SegmentResult segment(Bitmap bitmap, VisionAttribute visionAttribute) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.aicr.plugin.IImageSegmentService");
                    _Parcel.writeTypedObject(parcelObtain, bitmap, 0);
                    _Parcel.writeTypedObject(parcelObtain, visionAttribute, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SegmentResult) _Parcel.readTypedObject(parcelObtain2, SegmentResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static IImageSegmentService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.aicr.plugin.IImageSegmentService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IImageSegmentService)) ? new Proxy(iBinder) : (IImageSegmentService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.xiaomi.aicr.plugin.IImageSegmentService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.xiaomi.aicr.plugin.IImageSegmentService");
                return true;
            }
            switch (i) {
                case 1:
                    int iInitAlgorithmCachePath = initAlgorithmCachePath();
                    parcel2.writeNoException();
                    parcel2.writeInt(iInitAlgorithmCachePath);
                    return true;
                case 2:
                    int iInitPersonModel = initPersonModel();
                    parcel2.writeNoException();
                    parcel2.writeInt(iInitPersonModel);
                    return true;
                case 3:
                    int iInitSkyModel = initSkyModel();
                    parcel2.writeNoException();
                    parcel2.writeInt(iInitSkyModel);
                    return true;
                case 4:
                    SegmentResult segmentResultSegment = segment((Bitmap) _Parcel.readTypedObject(parcel, Bitmap.CREATOR), (VisionAttribute) _Parcel.readTypedObject(parcel, VisionAttribute.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, segmentResultSegment, 1);
                    return true;
                case 5:
                    SegmentResult segmentResultSegmentByFd = segmentByFd((ParcelFileDescriptor) _Parcel.readTypedObject(parcel, ParcelFileDescriptor.CREATOR), (VisionAttribute) _Parcel.readTypedObject(parcel, VisionAttribute.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, segmentResultSegmentByFd, 1);
                    return true;
                case 6:
                    int iReleaseModel = releaseModel(parcel.readInt() != 0, parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeInt(iReleaseModel);
                    return true;
                case 7:
                    int iReleaseAll = releaseAll();
                    parcel2.writeNoException();
                    parcel2.writeInt(iReleaseAll);
                    return true;
                case 8:
                    String algorithmVersion = getAlgorithmVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(algorithmVersion);
                    return true;
                case 9:
                    SegmentResult segmentResultSegmentMainFunc = segmentMainFunc((Bitmap) _Parcel.readTypedObject(parcel, Bitmap.CREATOR), (VisionAttribute) _Parcel.readTypedObject(parcel, VisionAttribute.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, segmentResultSegmentMainFunc, 1);
                    return true;
                case 10:
                    SegmentResult segmentResultSegmentMainFuncByFd = segmentMainFuncByFd((ParcelFileDescriptor) _Parcel.readTypedObject(parcel, ParcelFileDescriptor.CREATOR), (VisionAttribute) _Parcel.readTypedObject(parcel, VisionAttribute.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, segmentResultSegmentMainFuncByFd, 1);
                    return true;
                case 11:
                    String segmentMainFuncVersion = getSegmentMainFuncVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(segmentMainFuncVersion);
                    return true;
                case 12:
                    Bitmap bitmapDepthSegmentMainFunc = depthSegmentMainFunc((Bitmap) _Parcel.readTypedObject(parcel, Bitmap.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, bitmapDepthSegmentMainFunc, 1);
                    return true;
                case 13:
                    Bitmap bitmapDepthSegmentMainFuncByFd = depthSegmentMainFuncByFd((ParcelFileDescriptor) _Parcel.readTypedObject(parcel, ParcelFileDescriptor.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, bitmapDepthSegmentMainFuncByFd, 1);
                    return true;
                case 14:
                    String depthSegmentMainFuncVersion = getDepthSegmentMainFuncVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(depthSegmentMainFuncVersion);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    public static class _Parcel {
        public static Object readTypedObject(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        public static void writeTypedObject(Parcel parcel, Parcelable parcelable, int i) {
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, i);
            }
        }
    }

    Bitmap depthSegmentMainFunc(Bitmap bitmap);

    Bitmap depthSegmentMainFuncByFd(ParcelFileDescriptor parcelFileDescriptor);

    String getAlgorithmVersion();

    String getDepthSegmentMainFuncVersion();

    String getSegmentMainFuncVersion();

    int initAlgorithmCachePath();

    int initPersonModel();

    int initSkyModel();

    int releaseAll();

    int releaseModel(boolean z, boolean z2);

    SegmentResult segment(Bitmap bitmap, VisionAttribute visionAttribute);

    SegmentResult segmentByFd(ParcelFileDescriptor parcelFileDescriptor, VisionAttribute visionAttribute);

    SegmentResult segmentMainFunc(Bitmap bitmap, VisionAttribute visionAttribute);

    SegmentResult segmentMainFuncByFd(ParcelFileDescriptor parcelFileDescriptor, VisionAttribute visionAttribute);
}
