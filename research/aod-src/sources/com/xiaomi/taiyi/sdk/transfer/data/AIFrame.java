package com.xiaomi.taiyi.sdk.transfer.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.common.DataFormat;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

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
public final class AIFrame implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    private static final String FLAG_R = "r";
    private static final String FLAG_W = "w";
    private static final String TAG = "AIFrame";
    private int byteLength;
    private String flag;
    private DataFormat format;
    private boolean readAsBuffer;
    private String shareMemId;
    private String tag;
    private Object value;
    private boolean writeInBuffer;
    private int x;
    private int y;

    @Metadata
    public static final class CREATOR implements Parcelable.Creator<AIFrame> {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final AIFrame createBigDataFrame(String key, byte[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            return new AIFrame(value, key, value.length, 1, DataFormat.ONE_BYTE_ARRAY, value.length);
        }

        @JvmStatic
        public final AIFrame createFrame(Object value, String tag, int i, int i2, DataFormat format) {
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(tag, "tag");
            Intrinsics.checkNotNullParameter(format, "format");
            return new AIFrame(value, tag, i, i2, format);
        }

        @JvmStatic
        public final AIFrame createFrame(Object value, String tag, int i, int i2, DataFormat format, int i3) {
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(tag, "tag");
            Intrinsics.checkNotNullParameter(format, "format");
            return new AIFrame(value, tag, i, i2, format, i3);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIFrame createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new AIFrame(parcel);
        }

        @JvmStatic
        public final AIFrame createHighReadFrame(Object value, String tag, int i, int i2, DataFormat format) {
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(tag, "tag");
            Intrinsics.checkNotNullParameter(format, "format");
            AIFrame aIFrame = new AIFrame(value, tag, i, i2, format);
            aIFrame.setReadAsBuffer$base_release(true);
            return aIFrame;
        }

        @JvmStatic
        public final AIFrame createHighReadFrame(Object value, String tag, int i, int i2, DataFormat format, int i3) {
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(tag, "tag");
            Intrinsics.checkNotNullParameter(format, "format");
            AIFrame aIFrame = new AIFrame(value, tag, i, i2, format, i3);
            aIFrame.setReadAsBuffer$base_release(true);
            return aIFrame;
        }

        @JvmStatic
        public final AIFrame createHighWriteFrame(String tag, int i, int i2, DataFormat format) {
            Intrinsics.checkNotNullParameter(tag, "tag");
            Intrinsics.checkNotNullParameter(format, "format");
            AIFrame aIFrame = new AIFrame(null, tag, i, i2, format);
            aIFrame.setWriteInBuffer$base_release(true);
            return aIFrame;
        }

        @JvmStatic
        public final AIFrame createHighWriteFrame(String tag, int i, int i2, DataFormat format, int i3) {
            Intrinsics.checkNotNullParameter(tag, "tag");
            Intrinsics.checkNotNullParameter(format, "format");
            AIFrame aIFrame = new AIFrame(null, tag, i, i2, format, i3);
            aIFrame.setWriteInBuffer$base_release(true);
            return aIFrame;
        }

        @JvmStatic
        public final AIFrame createLongTextFrame(String key, String text) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(text, "text");
            byte[] bytes = text.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            return new AIFrame(bytes, key, bytes.length, 1, DataFormat.ONE_BYTE_ARRAY, bytes.length);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIFrame[] newArray(int i) {
            return new AIFrame[i];
        }
    }

    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DataFormat.values().length];
            try {
                iArr[DataFormat.ONE_BYTE_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DataFormat.ONE_SHORT_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DataFormat.ONE_INT_ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DataFormat.ONE_LONG_ARRAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[DataFormat.ONE_FLOAT_ARRAY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[DataFormat.ONE_DOUBLE_ARRAY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[DataFormat.TWO_BYTE_ARRAY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[DataFormat.TWO_SHORT_ARRAY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[DataFormat.TWO_INT_ARRAY.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[DataFormat.TWO_LONG_ARRAY.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[DataFormat.TWO_FLOAT_ARRAY.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[DataFormat.TWO_DOUBLE_ARRAY.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private AIFrame() {
        this.tag = "";
        this.format = DataFormat.UNKNOWN;
        this.shareMemId = "";
        this.flag = "";
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AIFrame(Parcel parcel) {
        this();
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        readFromParcel(parcel);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:13:0x0037  */
    public AIFrame(Object obj, String tag, int i, int i2, DataFormat format) {
        this();
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(format, "format");
        this.value = obj;
        this.tag = tag;
        this.x = i;
        this.y = i2;
        this.format = format;
        switch (WhenMappings.$EnumSwitchMapping$0[format.ordinal()]) {
            case 1:
                break;
            case 2:
                i *= 2;
                break;
            case 3:
            case 5:
                i *= 4;
                break;
            case 4:
            case 6:
                i *= 8;
                break;
            case 7:
                i *= i2;
                break;
            case 8:
                i *= i2;
                i *= 2;
                break;
            case 9:
            case 11:
                i *= i2;
                i *= 4;
                break;
            case 10:
            case 12:
                i *= i2;
                i *= 8;
                break;
            default:
                if (!(obj instanceof byte[])) {
                    i *= i2;
                } else {
                    i = ((byte[]) obj).length;
                }
                break;
        }
        this.byteLength = i;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AIFrame(Object obj, String tag, int i, int i2, DataFormat format, int i3) {
        this(obj, tag, i, i2, format);
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(format, "format");
        this.byteLength = i3;
    }

    @JvmStatic
    public static final AIFrame createBigDataFrame(String str, byte[] bArr) {
        return CREATOR.createBigDataFrame(str, bArr);
    }

    @JvmStatic
    public static final AIFrame createFrame(Object obj, String str, int i, int i2, DataFormat dataFormat) {
        return CREATOR.createFrame(obj, str, i, i2, dataFormat);
    }

    @JvmStatic
    public static final AIFrame createFrame(Object obj, String str, int i, int i2, DataFormat dataFormat, int i3) {
        return CREATOR.createFrame(obj, str, i, i2, dataFormat, i3);
    }

    @JvmStatic
    public static final AIFrame createHighReadFrame(Object obj, String str, int i, int i2, DataFormat dataFormat) {
        return CREATOR.createHighReadFrame(obj, str, i, i2, dataFormat);
    }

    @JvmStatic
    public static final AIFrame createHighReadFrame(Object obj, String str, int i, int i2, DataFormat dataFormat, int i3) {
        return CREATOR.createHighReadFrame(obj, str, i, i2, dataFormat, i3);
    }

    @JvmStatic
    public static final AIFrame createHighWriteFrame(String str, int i, int i2, DataFormat dataFormat) {
        return CREATOR.createHighWriteFrame(str, i, i2, dataFormat);
    }

    @JvmStatic
    public static final AIFrame createHighWriteFrame(String str, int i, int i2, DataFormat dataFormat, int i3) {
        return CREATOR.createHighWriteFrame(str, i, i2, dataFormat, i3);
    }

    @JvmStatic
    public static final AIFrame createLongTextFrame(String str, String str2) {
        return CREATOR.createLongTextFrame(str, str2);
    }

    public static /* synthetic */ int read$default(AIFrame aIFrame, ShareMemoryHolder shareMemoryHolder, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return aIFrame.read(shareMemoryHolder, z);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final int getByteLength() {
        return this.byteLength;
    }

    public final DataFormat getFormat() {
        return this.format;
    }

    public final boolean getReadAsBuffer() {
        return this.readAsBuffer;
    }

    public final String getShareMemId() {
        return this.shareMemId;
    }

    public final String getTag() {
        return this.tag;
    }

    public final Object getValue() {
        return this.value;
    }

    public final boolean getWriteInBuffer() {
        return this.writeInBuffer;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    public final boolean hasRead() {
        return this.value != null || Intrinsics.areEqual(this.flag, FLAG_R);
    }

    public final boolean hasWrite() {
        return this.shareMemId.length() > 0 && Intrinsics.areEqual(this.flag, "w");
    }

    public final boolean isHighRead() {
        return this.readAsBuffer && this.shareMemId.length() > 0;
    }

    public final boolean isHighWrite() {
        return this.writeInBuffer && this.shareMemId.length() > 0;
    }

    public final Pair<ByteBuffer, Integer> map(ShareMemoryHolder sm) {
        Intrinsics.checkNotNullParameter(sm, "sm");
        Pair<ByteBuffer, Integer> map = sm.map();
        if (((Number) map.getSecond()).intValue() == 0) {
            this.shareMemId = sm.getId();
        }
        return map;
    }

    public final ByteBuffer openArray$base_release() {
        ByteBuffer byteBufferOrder = ByteBuffer.allocateDirect(this.byteLength).order(ByteOrder.nativeOrder());
        Object obj = this.value;
        if (obj == null) {
            return byteBufferOrder;
        }
        int i = 0;
        switch (WhenMappings.$EnumSwitchMapping$0[this.format.ordinal()]) {
            case 1:
                byteBufferOrder.put((byte[]) obj);
                break;
            case 2:
                byteBufferOrder.asShortBuffer().put((short[]) obj);
                break;
            case 3:
                byteBufferOrder.asIntBuffer().put((int[]) obj);
                break;
            case 4:
                byteBufferOrder.asLongBuffer().put((long[]) obj);
                break;
            case 5:
                byteBufferOrder.asFloatBuffer().put((float[]) obj);
                break;
            case 6:
                byteBufferOrder.asDoubleBuffer().put((double[]) obj);
                break;
            case 7:
                byte[][] bArr = (byte[][]) obj;
                int length = bArr.length;
                while (i < length) {
                    byteBufferOrder.put(bArr[i]);
                    i++;
                }
                Unit unit = Unit.INSTANCE;
                break;
            case 8:
                short[][] sArr = (short[][]) obj;
                ShortBuffer shortBufferAsShortBuffer = byteBufferOrder.asShortBuffer();
                int length2 = sArr.length;
                while (i < length2) {
                    shortBufferAsShortBuffer.put(sArr[i]);
                    i++;
                }
                Unit unit2 = Unit.INSTANCE;
                break;
            case 9:
                int[][] iArr = (int[][]) obj;
                IntBuffer intBufferAsIntBuffer = byteBufferOrder.asIntBuffer();
                int length3 = iArr.length;
                while (i < length3) {
                    intBufferAsIntBuffer.put(iArr[i]);
                    i++;
                }
                Unit unit3 = Unit.INSTANCE;
                break;
            case 10:
                long[][] jArr = (long[][]) obj;
                LongBuffer longBufferAsLongBuffer = byteBufferOrder.asLongBuffer();
                int length4 = jArr.length;
                while (i < length4) {
                    longBufferAsLongBuffer.put(jArr[i]);
                    i++;
                }
                Unit unit4 = Unit.INSTANCE;
                break;
            case 11:
                float[][] fArr = (float[][]) obj;
                FloatBuffer floatBufferAsFloatBuffer = byteBufferOrder.asFloatBuffer();
                int length5 = fArr.length;
                while (i < length5) {
                    floatBufferAsFloatBuffer.put(fArr[i]);
                    i++;
                }
                Unit unit5 = Unit.INSTANCE;
                break;
            case 12:
                double[][] dArr = (double[][]) obj;
                DoubleBuffer doubleBufferAsDoubleBuffer = byteBufferOrder.asDoubleBuffer();
                int length6 = dArr.length;
                while (i < length6) {
                    doubleBufferAsDoubleBuffer.put(dArr[i]);
                    i++;
                }
                Unit unit6 = Unit.INSTANCE;
                break;
            default:
                byteBufferOrder.put((byte[]) obj);
                break;
        }
        return byteBufferOrder;
    }

    public final int read(ShareMemoryHolder sm, boolean z) {
        Intrinsics.checkNotNullParameter(sm, "sm");
        Pair<Object, Integer> pair = sm.read(this, z);
        Object objComponent1 = pair.component1();
        int iIntValue = ((Number) pair.component2()).intValue();
        if (iIntValue == 0) {
            this.value = objComponent1;
            this.flag = FLAG_R;
        }
        SdkLog.i(TAG, "read " + sm + " -> " + this + ". code = " + iIntValue);
        return iIntValue;
    }

    public final void readFromParcel(Parcel parcel) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        String string = parcel.readString();
        if (string == null) {
            string = "";
        }
        this.tag = string;
        this.x = parcel.readInt();
        this.y = parcel.readInt();
        this.byteLength = parcel.readInt();
        this.format = DataFormat.Companion.fromValue(parcel.readInt());
        String string2 = parcel.readString();
        this.shareMemId = string2 != null ? string2 : "";
        this.readAsBuffer = parcel.readBoolean();
    }

    public final void setReadAsBuffer$base_release(boolean z) {
        this.readAsBuffer = z;
    }

    public final void setWriteInBuffer$base_release(boolean z) {
        this.writeInBuffer = z;
    }

    public String toString() {
        return "AIFrame(" + this.tag + ")[" + this.x + ", " + this.y + ", " + this.format + ", " + this.shareMemId + ", " + this.flag + "][rb: " + this.readAsBuffer + ", wb: " + this.writeInBuffer + "]";
    }

    public final void unmap(ShareMemoryHolder sm) {
        Intrinsics.checkNotNullParameter(sm, "sm");
        sm.unmap();
    }

    public final int write(ShareMemoryHolder sm) {
        Intrinsics.checkNotNullParameter(sm, "sm");
        int iWrite = sm.write(this);
        if (iWrite == 0) {
            this.shareMemId = sm.getId();
            this.flag = "w";
        }
        SdkLog.i(TAG, "write " + sm + " -> " + this + ". code = " + iWrite);
        return iWrite;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int i) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeString(this.tag);
        dest.writeInt(this.x);
        dest.writeInt(this.y);
        dest.writeInt(this.byteLength);
        dest.writeInt(this.format.getValue());
        dest.writeString(this.shareMemId);
        dest.writeBoolean(this.readAsBuffer);
    }
}
