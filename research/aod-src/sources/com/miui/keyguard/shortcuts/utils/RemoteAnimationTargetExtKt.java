package com.miui.keyguard.shortcuts.utils;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.graphics.Rect;
import android.view.RemoteAnimationTarget;
import java.lang.reflect.Field;
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
/* JADX INFO: compiled from: RemoteAnimationTargetExt.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class RemoteAnimationTargetExtKt {
    public static final Rect getLocalBoundsByReflection(RemoteAnimationTarget remoteAnimationTarget) {
        Intrinsics.checkNotNullParameter(remoteAnimationTarget, "<this>");
        try {
            Field declaredField = RemoteAnimationTarget.class.getDeclaredField("localBounds");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(remoteAnimationTarget);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type android.graphics.Rect");
            return (Rect) obj;
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "RemoteAnimationTargetExt", "getLocalBoundsByReflection failed: " + e.getMessage(), null, 4, null);
            return new Rect();
        }
    }

    public static final ComponentName getRealActivityByReflection(TaskInfo taskInfo) {
        Intrinsics.checkNotNullParameter(taskInfo, "<this>");
        try {
            Field declaredField = TaskInfo.class.getDeclaredField("realActivity");
            declaredField.setAccessible(true);
            return (ComponentName) declaredField.get(taskInfo);
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "RemoteAnimationTargetExt", "getRealActivityByReflection failed: " + e.getMessage(), null, 4, null);
            return null;
        }
    }

    public static final ActivityManager.RunningTaskInfo getRunningTaskInfoByReflection(RemoteAnimationTarget remoteAnimationTarget) {
        Intrinsics.checkNotNullParameter(remoteAnimationTarget, "<this>");
        try {
            Field declaredField = RemoteAnimationTarget.class.getDeclaredField("taskInfo");
            declaredField.setAccessible(true);
            return (ActivityManager.RunningTaskInfo) declaredField.get(remoteAnimationTarget);
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "RemoteAnimationTargetExt", "getRunningTaskInfoByReflection failed: " + e.getMessage(), null, 4, null);
            return null;
        }
    }
}
