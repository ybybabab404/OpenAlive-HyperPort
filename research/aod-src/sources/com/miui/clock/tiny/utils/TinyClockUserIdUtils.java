package com.miui.clock.tiny.utils;

import android.app.ActivityManager;
import android.util.Log;
import com.miui.clock.tiny.pets.interactive.InteractivePetsStyle;
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
/* JADX INFO: compiled from: TinyClockUserIdUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class TinyClockUserIdUtils {
    public static final TinyClockUserIdUtils INSTANCE = new TinyClockUserIdUtils();
    public static Integer mUserId;

    public final void refreshResourceByUserId() {
        InteractivePetsStyle.InteractivePetsResourceUtil.updateDownDir();
    }

    public final Integer updateCurrentUserId() {
        Integer num = mUserId;
        if (num != null) {
            return num;
        }
        try {
            try {
                Object objInvoke = ActivityManager.class.getMethod("getCurrentUser", new Class[0]).invoke(null, new Object[0]);
                Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Int");
                Integer num2 = (Integer) objInvoke;
                Log.d("TinyClockUserIdUtils", "get current user id successful， before" + mUserId + ", new: " + num2.intValue());
                mUserId = num2;
            } catch (Exception e) {
                mUserId = 0;
                Log.e("TinyClockUserIdUtils", "get current user id fail" + e.getMessage());
            }
            refreshResourceByUserId();
            Integer num3 = mUserId;
            if (num3 != null) {
                return num3;
            }
            return 0;
        } catch (Throwable th) {
            refreshResourceByUserId();
            throw th;
        }
    }
}
