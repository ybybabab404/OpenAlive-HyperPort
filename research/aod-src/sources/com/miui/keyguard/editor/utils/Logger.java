package com.miui.keyguard.editor.utils;

import android.content.Context;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.util.Log;
import org.jetbrains.annotations.NotNull;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Logger.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class Logger {

    @NotNull
    public static final Logger INSTANCE = new Logger();

    private Logger() {
    }

    public static /* synthetic */ void d$default(Logger logger, Context context, String str, String str2, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        logger.d(context, str, str2, z);
    }

    public static /* synthetic */ void e$default(Logger logger, Context context, String str, String str2, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        logger.e(context, str, str2, z);
    }

    public static /* synthetic */ void i$default(Logger logger, Context context, String str, String str2, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        logger.i(context, str, str2, z);
    }

    public static /* synthetic */ void v$default(Logger logger, Context context, String str, String str2, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        logger.v(context, str, str2, z);
    }

    public static /* synthetic */ void w$default(Logger logger, Context context, String str, String str2, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        logger.w(context, str, str2, z);
    }

    public final void d(@NotNull Context context, @NotNull String tag, @NotNull String msg, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (z) {
            Log.getLogcatLogger().debug(tag, msg);
        }
        Log.getAsyncFileLogger(context).debug(tag, msg);
    }

    public final void e(@NotNull Context context, @NotNull String tag, @NotNull String msg, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (z) {
            Log.getLogcatLogger().error(tag, msg);
        }
        Log.getAsyncFileLogger(context).error(tag, msg);
    }

    public final void i(@NotNull Context context, @NotNull String tag, @NotNull String msg, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (z) {
            Log.getLogcatLogger().info(tag, msg);
        }
        Log.getAsyncFileLogger(context).info(tag, msg);
    }

    public final void v(@NotNull Context context, @NotNull String tag, @NotNull String msg, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (z) {
            Log.getLogcatLogger().verbose(tag, msg);
        }
        Log.getAsyncFileLogger(context).verbose(tag, msg);
    }

    public final void w(@NotNull Context context, @NotNull String tag, @NotNull String msg, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (z) {
            Log.getLogcatLogger().warn(tag, msg);
        }
        Log.getAsyncFileLogger(context).warn(tag, msg);
    }
}
