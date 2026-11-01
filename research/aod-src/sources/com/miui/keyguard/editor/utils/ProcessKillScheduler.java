package com.miui.keyguard.editor.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
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
    */
/* JADX INFO: compiled from: ProcessKillScheduler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ProcessKillScheduler {
    public final Function0 beforeKill;
    public final long delayMs;
    public final Lazy handler$delegate;
    public volatile boolean isBackupOrRestoreInProgress;
    public final Runnable killRunnable;
    public final Function0 shouldKill;
    public final String tag;

    public ProcessKillScheduler(String tag, long j, Function0 shouldKill, Function0 beforeKill) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(shouldKill, "shouldKill");
        Intrinsics.checkNotNullParameter(beforeKill, "beforeKill");
        this.tag = tag;
        this.delayMs = j;
        this.shouldKill = shouldKill;
        this.beforeKill = beforeKill;
        this.handler$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.utils.ProcessKillScheduler$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ProcessKillScheduler.handler_delegate$lambda$2(this.f$0);
            }
        });
        this.killRunnable = new Runnable() { // from class: com.miui.keyguard.editor.utils.ProcessKillScheduler$killRunnable$1
            @Override // java.lang.Runnable
            public void run() {
                if (this.this$0.isBackupOrRestoreInProgress()) {
                    Log.i(this.this$0.tag, "backup or restore in progress, delay killing");
                    this.this$0.getHandler().postDelayed(this, this.this$0.delayMs);
                    return;
                }
                if (!((Boolean) this.this$0.shouldKill.invoke()).booleanValue()) {
                    Log.i(this.this$0.tag, "should not kill now, retry later");
                    this.this$0.getHandler().postDelayed(this, this.this$0.delayMs);
                    return;
                }
                this.this$0.beforeKill.invoke();
                Log.i(this.this$0.tag, "killing process pid=" + android.os.Process.myPid());
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        };
    }

    public /* synthetic */ ProcessKillScheduler(String str, long j, Function0 function0, Function0 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, j, (i & 4) != 0 ? new Function0() { // from class: com.miui.keyguard.editor.utils.ProcessKillScheduler$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(ProcessKillScheduler._init_$lambda$0());
            }
        } : function0, (i & 8) != 0 ? new Function0() { // from class: com.miui.keyguard.editor.utils.ProcessKillScheduler$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Unit.INSTANCE;
            }
        } : function1);
    }

    public static final boolean _init_$lambda$0() {
        return true;
    }

    public static final Handler handler_delegate$lambda$2(ProcessKillScheduler processKillScheduler) {
        HandlerThread handlerThread = new HandlerThread("KillScheduler-" + processKillScheduler.tag);
        handlerThread.start();
        return new Handler(handlerThread.getLooper());
    }

    public final void cancel() {
        Log.i(this.tag, "cancel scheduled kill");
        getHandler().removeCallbacks(this.killRunnable);
    }

    public final Handler getHandler() {
        return (Handler) this.handler$delegate.getValue();
    }

    public final boolean isBackupOrRestoreInProgress() {
        return this.isBackupOrRestoreInProgress;
    }

    public final void schedule() {
        Log.i(this.tag, "schedule kill after " + this.delayMs + "ms");
        getHandler().removeCallbacks(this.killRunnable);
        getHandler().postDelayed(this.killRunnable, this.delayMs);
    }

    public final void setBackupOrRestoreInProgress(boolean z) {
        Log.i(this.tag, "setBackupOrRestoreInProgress: " + z);
        this.isBackupOrRestoreInProgress = z;
    }
}
