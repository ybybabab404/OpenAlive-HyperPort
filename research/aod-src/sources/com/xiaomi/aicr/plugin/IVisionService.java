package com.xiaomi.aicr.plugin;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.xiaomi.aicr.vision.VisionAttribute;
import com.xiaomi.aicr.vision.ocr.OCRRes;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface IVisionService extends IInterface {

    public static class Default implements IVisionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVisionService {

        public static class Proxy implements IVisionService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public static IVisionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.aicr.plugin.IVisionService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IVisionService)) ? new Proxy(iBinder) : (IVisionService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.xiaomi.aicr.plugin.IVisionService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.xiaomi.aicr.plugin.IVisionService");
                return true;
            }
            switch (i) {
                case 1:
                    int image = setImage((Bitmap) _Parcel.readTypedObject(parcel, Bitmap.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(image);
                    return true;
                case 2:
                    int imageParcelFileDesciptor = setImageParcelFileDesciptor((ParcelFileDescriptor) _Parcel.readTypedObject(parcel, ParcelFileDescriptor.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(imageParcelFileDesciptor);
                    return true;
                case 3:
                    OCRRes.Location[] locationArrDoOCRDetect = doOCRDetect(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(locationArrDoOCRDetect, 1);
                    return true;
                case 4:
                    OCRRes.OCRResult oCRResultDoOCRRecognize = doOCRRecognize(parcel.readInt());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, oCRResultDoOCRRecognize, 1);
                    return true;
                case 5:
                    int i3 = parcel.readInt();
                    ArrayList arrayList = new ArrayList();
                    int iDoDetect = doDetect(i3, arrayList, (VisionAttribute) _Parcel.readTypedObject(parcel, VisionAttribute.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(iDoDetect);
                    parcel2.writeTypedList(arrayList);
                    return true;
                case 6:
                    String oCRVersion = getOCRVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(oCRVersion);
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

    int doDetect(int i, List list, VisionAttribute visionAttribute);

    OCRRes.Location[] doOCRDetect(int i);

    OCRRes.OCRResult doOCRRecognize(int i);

    String getOCRVersion();

    int setImage(Bitmap bitmap);

    int setImageParcelFileDesciptor(ParcelFileDescriptor parcelFileDescriptor);
}
