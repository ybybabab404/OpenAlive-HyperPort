package com.miui.aod;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.util.Log;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.miui.keyguard.editor.utils.ProcessKillScheduler;
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
/* JADX INFO: compiled from: LifeCycleObserver.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class LifeCycleObserver implements DefaultLifecycleObserver {
    public static final LifeCycleObserver INSTANCE = new LifeCycleObserver();
    public static String TAG = "AODLifeCycleObserver";
    public static final ProcessKillScheduler killScheduler = new ProcessKillScheduler("AODLifeCycleObserver", 60000, null, null, 12, null);
    public static int sActivityVisibilityCount;
    public static int sReceiverCount;

    private LifeCycleObserver() {
    }

    public final ProcessKillScheduler getKillScheduler() {
        return killScheduler;
    }

    public final boolean isActivity(LifecycleOwner lifecycleOwner) {
        return lifecycleOwner instanceof Activity;
    }

    public final boolean isFrontState() {
        return sActivityVisibilityCount > 0 || sReceiverCount > 0;
    }

    public final boolean isReceiver(LifecycleOwner lifecycleOwner) {
        return lifecycleOwner instanceof BroadcastReceiver;
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onResume(LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        if (isActivity(owner) || isFrontState()) {
            killScheduler.cancel();
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        if (isActivity(owner)) {
            sActivityVisibilityCount++;
        } else if (isReceiver(owner)) {
            sReceiverCount++;
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        if (isActivity(owner)) {
            sActivityVisibilityCount--;
        } else if (isReceiver(owner)) {
            sReceiverCount--;
        }
        if (isFrontState()) {
            return;
        }
        Log.i(TAG, "onStopped: killing settings process after 1 minute");
        killScheduler.schedule();
    }
}
