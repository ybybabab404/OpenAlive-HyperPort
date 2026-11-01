package com.miui.keyguard.editor.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.MiuiSettings;
import android.text.TextUtils;
import java.util.Calendar;
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
/* JADX INFO: compiled from: DualClockManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DualClockManager {
    public static final DualClockManager INSTANCE = new DualClockManager();

    public static final boolean closeDualClock(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DualClockManager dualClockManager = INSTANCE;
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        return dualClockManager.setBooleanFromSystem(contentResolver, "auto_dual_clock", false);
    }

    public static final String getDualClockResidentTimeZone(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DualClockManager dualClockManager = INSTANCE;
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        return dualClockManager.getStringFromSystem(contentResolver, "resident_timezone");
    }

    public static final boolean isDualClockAvailable(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (isDualClockEnable(context)) {
            String id = Calendar.getInstance().getTimeZone().getID();
            String dualClockResidentTimeZone = getDualClockResidentTimeZone(context);
            if (dualClockResidentTimeZone != null && dualClockResidentTimeZone.length() != 0 && id != null && id.length() != 0 && !TextUtils.equals(id, dualClockResidentTimeZone)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isDualClockEnable(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DualClockManager dualClockManager = INSTANCE;
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        return dualClockManager.getBooleanFromSystem(contentResolver, "auto_dual_clock", false);
    }

    public final boolean getBooleanFromSystem(ContentResolver contentResolver, String str, boolean z) {
        return MiuiSettings.System.getBoolean(contentResolver, str, z);
    }

    public final String getStringFromSystem(ContentResolver contentResolver, String str) {
        return MiuiSettings.System.getString(contentResolver, str);
    }

    public final boolean setBooleanFromSystem(ContentResolver contentResolver, String str, boolean z) {
        return MiuiSettings.System.putBoolean(contentResolver, str, z);
    }
}
