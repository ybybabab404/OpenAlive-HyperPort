package com.xiaomi.taiyi.sdk.transfer.data;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public class AIPackage implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    private Bundle bundle;
    private final HashMap<String, Object> customMap;

    @Metadata
    public static final class CREATOR implements Parcelable.Creator<AIPackage> {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIPackage createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new AIPackage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIPackage[] newArray(int i) {
            return new AIPackage[i];
        }
    }

    public AIPackage() {
        this.bundle = new Bundle();
        this.customMap = new HashMap<>();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AIPackage(Parcel parcel) {
        this();
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        readFromParcel(parcel);
    }

    public static /* synthetic */ Bitmap getBitmap$default(AIPackage aIPackage, String str, Bitmap bitmap, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getBitmap");
        }
        if ((i & 4) != 0) {
            z = true;
        }
        return aIPackage.getBitmap(str, bitmap, z);
    }

    public static /* synthetic */ void putBitmap$default(AIPackage aIPackage, String str, Bitmap bitmap, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: putBitmap");
        }
        if ((i & 4) != 0) {
            z = true;
        }
        aIPackage.putBitmap(str, bitmap, z);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final IBinder getBinder(String key, IBinder iBinder) {
        Intrinsics.checkNotNullParameter(key, "key");
        IBinder binder = this.bundle.getBinder(key);
        return binder == null ? iBinder : binder;
    }

    public final Bitmap getBitmap(String key, Bitmap bitmap, boolean z) {
        IBitmap iBitmapAsInterface;
        Bitmap bitmap2;
        Intrinsics.checkNotNullParameter(key, "key");
        if (!z) {
            return (Bitmap) getParcelable(key, Bitmap.class, bitmap);
        }
        IBinder binder = getBinder(key, null);
        return (binder == null || (iBitmapAsInterface = IBitmap.Stub.asInterface(binder)) == null || (bitmap2 = iBitmapAsInterface.get()) == null) ? bitmap : bitmap2;
    }

    public final boolean getBoolean(String key, boolean z) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.bundle.getBoolean(key, z);
    }

    public final boolean[] getBooleanArray(String key, boolean[] zArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        boolean[] booleanArray = this.bundle.getBooleanArray(key);
        return booleanArray == null ? zArr : booleanArray;
    }

    public final Bundle getBundle(String key, Bundle bundle) {
        Intrinsics.checkNotNullParameter(key, "key");
        Bundle bundle2 = this.bundle.getBundle(key);
        return bundle2 == null ? bundle : bundle2;
    }

    public final byte getByte(String key, byte b) {
        Intrinsics.checkNotNullParameter(key, "key");
        Byte b2 = this.bundle.getByte(key, b);
        Intrinsics.checkNotNullExpressionValue(b2, "getByte(...)");
        return b2.byteValue();
    }

    public final byte[] getByteArray(String key, byte[] bArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        byte[] byteArray = this.bundle.getByteArray(key);
        return byteArray == null ? bArr : byteArray;
    }

    public final char getChar(String key, char c) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.bundle.getChar(key, c);
    }

    public final char[] getCharArray(String key, char[] cArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        char[] charArray = this.bundle.getCharArray(key);
        return charArray == null ? cArr : charArray;
    }

    public final Object getCustomData(String key, Object obj) {
        Intrinsics.checkNotNullParameter(key, "key");
        Object obj2 = this.customMap.get(key);
        return obj2 == null ? obj : obj2;
    }

    public final double getDouble(String key, double d) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.bundle.getDouble(key, d);
    }

    public final double[] getDoubleArray(String key, double[] dArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        double[] doubleArray = this.bundle.getDoubleArray(key);
        return doubleArray == null ? dArr : doubleArray;
    }

    public final float getFloat(String key, float f) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.bundle.getFloat(key, f);
    }

    public final float[] getFloatArray(String key, float[] fArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        float[] floatArray = this.bundle.getFloatArray(key);
        return floatArray == null ? fArr : floatArray;
    }

    public final int getInt(String key, int i) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.bundle.getInt(key, i);
    }

    public final int[] getIntArray(String key, int[] iArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        int[] intArray = this.bundle.getIntArray(key);
        return intArray == null ? iArr : intArray;
    }

    public final ArrayList<Integer> getIntArrayList(String key, ArrayList<Integer> arrayList) {
        Intrinsics.checkNotNullParameter(key, "key");
        ArrayList<Integer> integerArrayList = this.bundle.getIntegerArrayList(key);
        return integerArrayList == null ? arrayList : integerArrayList;
    }

    public final long getLong(String key, long j) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.bundle.getLong(key, j);
    }

    public final long[] getLongArray(String key, long[] jArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        long[] longArray = this.bundle.getLongArray(key);
        return longArray == null ? jArr : longArray;
    }

    public final <T extends Parcelable> T getParcelable(String key, T t) {
        Intrinsics.checkNotNullParameter(key, "key");
        T t2 = (T) this.bundle.getParcelable(key);
        return t2 == null ? t : t2;
    }

    public final <T> T getParcelable(String key, Class<T> clazz, T t) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        T t2 = (T) this.bundle.getParcelable(key, clazz);
        return t2 == null ? t : t2;
    }

    public final Parcelable[] getParcelableArray(String key, Parcelable[] parcelableArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.bundle.getParcelableArray(key);
    }

    public final <T> T[] getParcelableArray(String key, Class<T> clazz, T[] tArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return (T[]) this.bundle.getParcelableArray(key, clazz);
    }

    public final Serializable getSerializable(String key, Serializable serializable) {
        Intrinsics.checkNotNullParameter(key, "key");
        Serializable serializable2 = this.bundle.getSerializable(key);
        return serializable2 == null ? serializable : serializable2;
    }

    public final <T extends Serializable> T getSerializable(String key, Class<T> clazz, T t) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        T t2 = (T) this.bundle.getSerializable(key, clazz);
        return t2 == null ? t : t2;
    }

    public final short getShort(String key, short s) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.bundle.getShort(key, s);
    }

    public final short[] getShortArray(String key, short[] sArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        short[] shortArray = this.bundle.getShortArray(key);
        return shortArray == null ? sArr : shortArray;
    }

    public final Size getSize(String key, Size size) {
        Intrinsics.checkNotNullParameter(key, "key");
        Size size2 = this.bundle.getSize(key);
        return size2 == null ? size : size2;
    }

    public final SizeF getSizeF(String key, SizeF sizeF) {
        Intrinsics.checkNotNullParameter(key, "key");
        SizeF sizeF2 = this.bundle.getSizeF(key);
        return sizeF2 == null ? sizeF : sizeF2;
    }

    public final String getString(String key, String str) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.bundle.getString(key, str);
    }

    public final String[] getStringArray(String key, String[] strArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        String[] stringArray = this.bundle.getStringArray(key);
        return stringArray == null ? strArr : stringArray;
    }

    public final ArrayList<String> getStringArrayList(String key, ArrayList<String> arrayList) {
        Intrinsics.checkNotNullParameter(key, "key");
        ArrayList<String> stringArrayList = this.bundle.getStringArrayList(key);
        return stringArrayList == null ? arrayList : stringArrayList;
    }

    public final boolean hasCustomKey(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.customMap.containsKey(key);
    }

    public final boolean hasKey(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.bundle.containsKey(key);
    }

    public final void putAll(Bundle bundle) {
        Intrinsics.checkNotNullParameter(bundle, "bundle");
        bundle.putAll(bundle);
    }

    public final void putBinder(String key, IBinder iBinder) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putBinder(key, iBinder);
    }

    public final void putBitmap(String key, final Bitmap bitmap, boolean z) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (z) {
            putBinder(key, new IBitmap.Stub() { // from class: com.xiaomi.taiyi.sdk.transfer.data.AIPackage.putBitmap.1
                @Override // com.xiaomi.taiyi.sdk.transfer.data.IBitmap
                public Bitmap get() {
                    return bitmap;
                }
            });
        } else {
            putParcelable(key, bitmap);
        }
    }

    public final void putBoolean(String key, boolean z) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putBoolean(key, z);
    }

    public final void putBooleanArray(String key, boolean[] zArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putBooleanArray(key, zArr);
    }

    public final void putBundle(String key, Bundle bundle) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putBundle(key, bundle);
    }

    public final void putByte(String key, byte b) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putByte(key, b);
    }

    public final void putByteArray(String key, byte[] bArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putByteArray(key, bArr);
    }

    public final void putChar(String key, char c) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putChar(key, c);
    }

    public final void putCharArray(String key, char[] cArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putCharArray(key, cArr);
    }

    public final void putCustomData(String key, Object obj) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.customMap.put(key, obj);
    }

    public final void putDouble(String key, double d) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putDouble(key, d);
    }

    public final void putDoubleArray(String key, double[] dArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putDoubleArray(key, dArr);
    }

    public final void putFloat(String key, float f) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putFloat(key, f);
    }

    public final void putFloatArray(String key, float[] fArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putFloatArray(key, fArr);
    }

    public final void putInt(String key, int i) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putInt(key, i);
    }

    public final void putIntArray(String key, int[] iArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putIntArray(key, iArr);
    }

    public final void putIntArrayList(String key, ArrayList<Integer> arrayList) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putIntegerArrayList(key, arrayList);
    }

    public final void putLong(String key, long j) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putLong(key, j);
    }

    public final void putLongArray(String key, long[] jArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putLongArray(key, jArr);
    }

    public final void putParcelable(String key, Parcelable parcelable) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putParcelable(key, parcelable);
    }

    public final void putParcelableArray(String key, Parcelable[] parcelableArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putParcelableArray(key, parcelableArr);
    }

    public final void putSerializable(String key, Serializable serializable) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putSerializable(key, serializable);
    }

    public final void putShort(String key, short s) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putShort(key, s);
    }

    public final void putShortArray(String key, short[] value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        this.bundle.putShortArray(key, value);
    }

    public final void putSize(String key, Size size) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putSize(key, size);
    }

    public final void putSizeF(String key, SizeF sizeF) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putSizeF(key, sizeF);
    }

    public final void putString(String key, String str) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putString(key, str);
    }

    public final void putStringArray(String key, String[] strArr) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putStringArray(key, strArr);
    }

    public final void putStringArrayList(String key, ArrayList<String> arrayList) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.putStringArrayList(key, arrayList);
    }

    public void readFromParcel(Parcel parcel) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        Bundle bundle = parcel.readBundle(AIPackage.class.getClassLoader());
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.bundle = bundle;
        bundle.setClassLoader(AIPackage.class.getClassLoader());
    }

    public final void removeCustomKey(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.customMap.remove(key);
    }

    public final void removeKey(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.bundle.remove(key);
    }

    public final void setClassLoader(ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        this.bundle.setClassLoader(classLoader);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("[\n");
        int i = 0;
        for (String str : this.bundle.keySet()) {
            Intrinsics.checkNotNull(str);
            if (!StringsKt.contains$default((CharSequence) str, (CharSequence) AIKeys.KEY_APP_SIGNATURE, false, 2, (Object) null) && !StringsKt.contains$default((CharSequence) str, (CharSequence) AIKeys.KEY_APP_CODE, false, 2, (Object) null)) {
                sb.append("\t");
                sb.append(str);
                sb.append(" : ");
                String strValueOf = String.valueOf(this.bundle.get(str));
                if (strValueOf.length() > 45) {
                    String strSubstring = strValueOf.substring(0, 20);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    sb.append(strSubstring);
                    sb.append("***");
                    String strSubstring2 = strValueOf.substring(strValueOf.length() - 20, strValueOf.length() - 10);
                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                    sb.append(strSubstring2);
                    sb.append("...");
                } else {
                    sb.append(strValueOf);
                }
                sb.append("\n");
                i++;
                if (i >= 25) {
                    sb.append("...\n");
                    break;
                }
            }
        }
        sb.append("]");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int i) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeBundle(this.bundle);
    }
}
