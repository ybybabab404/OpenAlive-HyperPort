package com.miui.keyguard.editor.utils;

import android.app.StatusBarManager;
import android.content.Context;
import android.provider.MiuiSettings;
import android.util.Log;
import kotlin.Metadata;
import kotlin.Unit;
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
/* JADX INFO: compiled from: SystemBarController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SystemBarController {
    public static final SystemBarController INSTANCE = new SystemBarController();
    public static StatusBarManager systemBarManager;

    public static final void disable(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            INSTANCE.getSystemBarManager(context).disable(i);
        } catch (Exception e) {
            Log.e("SystemBarController", "disable status bar error", e);
        }
    }

    public static final boolean isFullScreenGestureEnabled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return MiuiSettings.Global.getBoolean(context.getContentResolver(), "force_fsg_nav_bar");
    }

    public final synchronized StatusBarManager getSystemBarManager(Context context) {
        StatusBarManager statusBarManager;
        synchronized (SystemBarController.class) {
            try {
                if (systemBarManager == null) {
                    Object systemService = context.getApplicationContext().getSystemService("statusbar");
                    Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.StatusBarManager");
                    systemBarManager = (StatusBarManager) systemService;
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        statusBarManager = systemBarManager;
        Intrinsics.checkNotNull(statusBarManager);
        return statusBarManager;
    }
}
