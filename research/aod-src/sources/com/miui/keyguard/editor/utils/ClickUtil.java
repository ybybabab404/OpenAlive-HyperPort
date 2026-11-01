package com.miui.keyguard.editor.utils;

import android.os.SystemClock;
import android.util.Log;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ClickUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClickUtil {
    public static final int BUTTON_FREE = 0;
    public static final ClickUtil INSTANCE = new ClickUtil();
    public static final AtomicInteger editButtonLock = new AtomicInteger(0);
    public static final AtomicBoolean isClickProcessing = new AtomicBoolean(false);
    public static long lastClickTime;

    public final int getBUTTON_FREE() {
        return BUTTON_FREE;
    }

    public final AtomicInteger getEditButtonLock() {
        return editButtonLock;
    }

    public final AtomicBoolean isClickProcessing() {
        return isClickProcessing;
    }

    public final boolean isFastDoubleClick() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        long j = lastClickTime;
        if (jUptimeMillis - j >= 500) {
            lastClickTime = jUptimeMillis;
            return false;
        }
        Log.d("ClickUtil", "isFastDoubleClick true, lastClickTime=" + j);
        return true;
    }
}
