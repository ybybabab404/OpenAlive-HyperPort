package com.miui.keyguard.editor.track;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TrackTimeUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TrackTimeUtils {
    public static final TrackTimeUtils INSTANCE = new TrackTimeUtils();
    public static HashMap sCalTimeMap = new HashMap();
    public static long sColdStartTime;

    public void beginTimeCalculate(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        sCalTimeMap.put(key, Long.valueOf(System.currentTimeMillis()));
    }

    public final void clearStartTimeCalculate() {
        clearTimeCalculate("editor_start");
        sColdStartTime = 0L;
    }

    public final void clearTimeCalculate(String str) {
        sCalTimeMap.remove(str);
    }

    public final long getTimeCalculate(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        long jCurrentTimeMillis = System.currentTimeMillis();
        Long l = (Long) sCalTimeMap.get(key);
        if (l == null) {
            return -1L;
        }
        sCalTimeMap.remove(key);
        return jCurrentTimeMillis - l.longValue();
    }
}
