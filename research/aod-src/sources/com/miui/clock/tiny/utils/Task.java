package com.miui.clock.tiny.utils;

import android.os.Handler;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: Task.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class Task {
    public static final Companion Companion = new Companion(null);
    public static Handler mBgHandler;

    /* JADX INFO: compiled from: Task.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void postBgTask(Runnable runnable) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            if (Task.mBgHandler == null) {
                Task.mBgHandler = new Handler(TaskManager.Companion.getInstance().getBgHandlerLooper());
            }
            Handler handler = Task.mBgHandler;
            Intrinsics.checkNotNull(handler);
            handler.post(runnable);
        }

        public final void removeBgTask(Runnable runnable) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            if (Task.mBgHandler == null) {
                Task.mBgHandler = new Handler(TaskManager.Companion.getInstance().getBgHandlerLooper());
            }
            Handler handler = Task.mBgHandler;
            Intrinsics.checkNotNull(handler);
            handler.removeCallbacks(runnable);
        }

        public final void run(Runnable runnable) {
            TaskManager.Companion.getInstance().execute(runnable);
        }
    }
}
