package com.miui.keyguard.shortcuts.utils;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: SyncRtSurfaceTransactionApplierExt.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class SyncRtSurfaceTransactionApplierExtKt {
    public static final Lazy withMiCornerRadiiMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.utils.SyncRtSurfaceTransactionApplierExtKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return SyncRtSurfaceTransactionApplierExtKt.withMiCornerRadiiMethod_delegate$lambda$3();
        }
    });

    public static final SyncRtSurfaceTransactionApplier.SurfaceParams buildByReflection(SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builder) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        try {
            Method declaredMethod = SyncRtSurfaceTransactionApplier.SurfaceParams.Builder.class.getDeclaredMethod("build", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(builder, new Object[0]);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type android.view.SyncRtSurfaceTransactionApplier.SurfaceParams");
            return (SyncRtSurfaceTransactionApplier.SurfaceParams) objInvoke;
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "SyncRtSurfaceTransactionApplierExt", "buildByReflection failed: " + e.getMessage(), null, 4, null);
            return null;
        }
    }

    public static final Method getWithMiCornerRadiiMethod() {
        return (Method) withMiCornerRadiiMethod$delegate.getValue();
    }

    public static final SyncRtSurfaceTransactionApplier newSyncRtSurfaceTransactionApplierByReflection(View targetView) {
        Intrinsics.checkNotNullParameter(targetView, "targetView");
        try {
            Constructor declaredConstructor = SyncRtSurfaceTransactionApplier.class.getDeclaredConstructor(View.class);
            declaredConstructor.setAccessible(true);
            Object objNewInstance = declaredConstructor.newInstance(targetView);
            Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type android.view.SyncRtSurfaceTransactionApplier");
            return (SyncRtSurfaceTransactionApplier) objNewInstance;
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "SyncRtSurfaceTransactionApplierExt", "newSyncRtSurfaceTransactionApplierByReflection failed: " + e.getMessage(), null, 4, null);
            return null;
        }
    }

    public static final SyncRtSurfaceTransactionApplier.SurfaceParams.Builder newSyncRtSurfaceTransactionApplierSurfaceParamsBuilderByReflection(SurfaceControl surfaceControl) {
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        try {
            Constructor declaredConstructor = SyncRtSurfaceTransactionApplier.SurfaceParams.Builder.class.getDeclaredConstructor(SurfaceControl.class);
            declaredConstructor.setAccessible(true);
            Object objNewInstance = declaredConstructor.newInstance(surfaceControl);
            Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder");
            return (SyncRtSurfaceTransactionApplier.SurfaceParams.Builder) objNewInstance;
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "SyncRtSurfaceTransactionApplierExt", "newSyncRtSurfaceTransactionApplierSurfaceParamsBuilderByReflection failed: " + e.getMessage(), null, 4, null);
            return null;
        }
    }

    public static final void scheduleApplyByReflection(SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, SyncRtSurfaceTransactionApplier.SurfaceParams... params) {
        Intrinsics.checkNotNullParameter(syncRtSurfaceTransactionApplier, "<this>");
        Intrinsics.checkNotNullParameter(params, "params");
        try {
            Method declaredMethod = SyncRtSurfaceTransactionApplier.class.getDeclaredMethod("scheduleApply", SyncRtSurfaceTransactionApplier.SurfaceParams[].class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(syncRtSurfaceTransactionApplier, params);
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "SyncRtSurfaceTransactionApplierExt", "scheduleApplyByReflection failed: " + e.getMessage(), null, 4, null);
        }
    }

    public static final boolean supportWithMiCornerRadii() {
        return getWithMiCornerRadiiMethod() != null;
    }

    public static final SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withAlphaByReflection(SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builder, float f) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        try {
            Method declaredMethod = SyncRtSurfaceTransactionApplier.SurfaceParams.Builder.class.getDeclaredMethod("withAlpha", Float.TYPE);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(builder, Float.valueOf(f));
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder");
            return (SyncRtSurfaceTransactionApplier.SurfaceParams.Builder) objInvoke;
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "SyncRtSurfaceTransactionApplierExt", "withAlphaByReflection failed: " + e.getMessage(), null, 4, null);
            return null;
        }
    }

    public static final SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withCornerRadiusByReflection(SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builder, float f) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        try {
            Method declaredMethod = SyncRtSurfaceTransactionApplier.SurfaceParams.Builder.class.getDeclaredMethod("withCornerRadius", Float.TYPE);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(builder, Float.valueOf(f));
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder");
            return (SyncRtSurfaceTransactionApplier.SurfaceParams.Builder) objInvoke;
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "SyncRtSurfaceTransactionApplierExt", "withCornerRadiusByReflection failed: " + e.getMessage(), null, 4, null);
            return null;
        }
    }

    public static final SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withMatrixByReflection(SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builder, Matrix matrix) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(matrix, "matrix");
        try {
            Method declaredMethod = SyncRtSurfaceTransactionApplier.SurfaceParams.Builder.class.getDeclaredMethod("withMatrix", Matrix.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(builder, matrix);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder");
            return (SyncRtSurfaceTransactionApplier.SurfaceParams.Builder) objInvoke;
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "SyncRtSurfaceTransactionApplierExt", "withMatrixByReflection failed: " + e.getMessage(), null, 4, null);
            return null;
        }
    }

    public static final Method withMiCornerRadiiMethod_delegate$lambda$3() {
        Object objM1061constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            Method declaredMethod = SyncRtSurfaceTransactionApplier.SurfaceParams.Builder.class.getDeclaredMethod("withMiCornerRadii", float[].class);
            declaredMethod.setAccessible(true);
            objM1061constructorimpl = Result.m1061constructorimpl(declaredMethod);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logW$default(LogUtils.INSTANCE, "SyncRtSurfaceTransactionApplierExt", "withMiCornerRadiiMethod failed: " + thM1063exceptionOrNullimpl.getMessage(), null, 4, null);
        }
        if (Result.m1065isFailureimpl(objM1061constructorimpl)) {
            objM1061constructorimpl = null;
        }
        return (Method) objM1061constructorimpl;
    }

    public static final SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withMiCornerRadiusByReflection(SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builder, float[] radii) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(radii, "radii");
        try {
            Method withMiCornerRadiiMethod = getWithMiCornerRadiiMethod();
            Object objInvoke = withMiCornerRadiiMethod != null ? withMiCornerRadiiMethod.invoke(builder, radii) : null;
            if (objInvoke instanceof SyncRtSurfaceTransactionApplier.SurfaceParams.Builder) {
                return (SyncRtSurfaceTransactionApplier.SurfaceParams.Builder) objInvoke;
            }
            return null;
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "SyncRtSurfaceTransactionApplierExt", "withMiCornerRadiusByReflection failed: " + e.getMessage(), null, 4, null);
            return null;
        }
    }

    public static final SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withWindowCropByReflection(SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builder, Rect rect) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(rect, "rect");
        try {
            Method declaredMethod = SyncRtSurfaceTransactionApplier.SurfaceParams.Builder.class.getDeclaredMethod("withWindowCrop", Rect.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(builder, rect);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder");
            return (SyncRtSurfaceTransactionApplier.SurfaceParams.Builder) objInvoke;
        } catch (Exception e) {
            LogUtils.logW$default(LogUtils.INSTANCE, "SyncRtSurfaceTransactionApplierExt", "withWindowCropByReflection failed: " + e.getMessage(), null, 4, null);
            return null;
        }
    }
}
