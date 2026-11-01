package com.miui.keyguard.editor.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: MiuiSettingsUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MiuiSettingsUtils {
    public static final MiuiSettingsUtils INSTANCE = new MiuiSettingsUtils();

    /* JADX INFO: compiled from: MiuiSettingsUtils.kt */
    @Metadata
    public static final class Secure {
        public static final Secure INSTANCE = new Secure();

        public final int getInt(ContentResolver contentResolver, String str, int i) {
            return Settings.Secure.getInt(contentResolver, str, i);
        }
    }

    public final boolean aodEnable(Context context) {
        int i = Secure.INSTANCE.getInt(context.getContentResolver(), "doze_always_on", 0);
        Log.i("Keyguard-Editor-MiuiSettingsUtils", "AOD_MODE " + i);
        return i == 1;
    }

    public final boolean fullAodEnable(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        boolean zAodEnable = aodEnable(context);
        boolean z = Secure.INSTANCE.getInt(context.getContentResolver(), "full_screen_aod_on", 0) == 1;
        Log.i("Keyguard-Editor-MiuiSettingsUtils", "fullAodEnable=" + (zAodEnable && z));
        return zAodEnable && z;
    }
}
