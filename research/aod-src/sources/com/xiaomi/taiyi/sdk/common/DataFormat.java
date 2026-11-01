package com.xiaomi.taiyi.sdk.common;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
public enum DataFormat {
    UNKNOWN(-1),
    IGNORE(0),
    IMAGE_RGBA(1),
    IMAGE_RGB(2),
    IMAGE_BGR(3),
    IMAGE_RGB_565(4),
    IMAGE_HARDWARE(5),
    IMAGE_GRAY(6),
    IMAGE_YUV_NV21(7),
    IMAGE_YUV_NV12(8),
    IMAGE_YUV_420_888(9),
    IMAGE_YUV_444(10),
    ONE_BYTE_ARRAY(20),
    ONE_SHORT_ARRAY(21),
    ONE_INT_ARRAY(22),
    ONE_LONG_ARRAY(24),
    ONE_FLOAT_ARRAY(23),
    ONE_DOUBLE_ARRAY(25),
    TWO_BYTE_ARRAY(26),
    TWO_SHORT_ARRAY(27),
    TWO_INT_ARRAY(28),
    TWO_LONG_ARRAY(29),
    TWO_FLOAT_ARRAY(30),
    TWO_DOUBLE_ARRAY(31);

    private final int value;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    public static final Companion Companion = new Companion(null);

    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final DataFormat fromValue(int i) {
            DataFormat next;
            Iterator<DataFormat> it = DataFormat.getEntries().iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (next.getValue() != i);
            DataFormat dataFormat = next;
            return dataFormat == null ? DataFormat.UNKNOWN : dataFormat;
        }
    }

    DataFormat(int i) {
        this.value = i;
    }

    @JvmStatic
    public static final DataFormat fromValue(int i) {
        return Companion.fromValue(i);
    }

    public static EnumEntries<DataFormat> getEntries() {
        return $ENTRIES;
    }

    public final int getValue() {
        return this.value;
    }
}
