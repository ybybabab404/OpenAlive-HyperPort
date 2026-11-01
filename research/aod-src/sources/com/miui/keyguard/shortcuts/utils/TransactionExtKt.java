package com.miui.keyguard.shortcuts.utils;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.SurfaceControl;
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
/* JADX INFO: compiled from: TransactionExt.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TransactionExtKt {
    public static final Lazy setMiCornerRadiiMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.utils.TransactionExtKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return TransactionExtKt.setMiCornerRadiiMethod_delegate$lambda$3();
        }
    });

    public static final Method getSetMiCornerRadiiMethod() {
        return (Method) setMiCornerRadiiMethod$delegate.getValue();
    }

    public static final void setCornerRadiusByReflection(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, float f) {
        Intrinsics.checkNotNullParameter(transaction, "<this>");
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        try {
            Method declaredMethod = SurfaceControl.Transaction.class.getDeclaredMethod("setCornerRadius", SurfaceControl.class, Float.TYPE);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(transaction, surfaceControl, Float.valueOf(f));
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "AnimationUtils", "reflect setCornerRadius error: " + e, null, 4, null);
        }
    }

    public static final void setMatrixByReflection(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Matrix matrix, float[] floatArray) {
        Intrinsics.checkNotNullParameter(transaction, "<this>");
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        Intrinsics.checkNotNullParameter(matrix, "matrix");
        Intrinsics.checkNotNullParameter(floatArray, "floatArray");
        try {
            Method declaredMethod = SurfaceControl.Transaction.class.getDeclaredMethod("setMatrix", SurfaceControl.class, Matrix.class, float[].class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(transaction, surfaceControl, matrix, floatArray);
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "AnimationUtils", "reflect setMatrix error: " + e, null, 4, null);
        }
    }

    public static final Method setMiCornerRadiiMethod_delegate$lambda$3() {
        Object objM1061constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            Method declaredMethod = SurfaceControl.Transaction.class.getDeclaredMethod("setMiCornerRadii", SurfaceControl.class, float[].class);
            declaredMethod.setAccessible(true);
            objM1061constructorimpl = Result.m1061constructorimpl(declaredMethod);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logE$default(LogUtils.INSTANCE, "AnimationUtils", "setMiCornerRadiiMethod failed: " + thM1063exceptionOrNullimpl.getMessage(), null, 4, null);
        }
        if (Result.m1065isFailureimpl(objM1061constructorimpl)) {
            objM1061constructorimpl = null;
        }
        return (Method) objM1061constructorimpl;
    }

    public static final void setMiCornerRadiusByReflection(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, float[] radii) {
        Intrinsics.checkNotNullParameter(transaction, "<this>");
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        Intrinsics.checkNotNullParameter(radii, "radii");
        try {
            Method setMiCornerRadiiMethod = getSetMiCornerRadiiMethod();
            if (setMiCornerRadiiMethod != null) {
                setMiCornerRadiiMethod.invoke(transaction, surfaceControl, radii);
            }
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "AnimationUtils", "reflect setMiCornerRadii error: " + e, null, 4, null);
        }
    }

    public static final void setWindowCropByReflection(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect) {
        Intrinsics.checkNotNullParameter(transaction, "<this>");
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        Intrinsics.checkNotNullParameter(rect, "rect");
        try {
            Method declaredMethod = SurfaceControl.Transaction.class.getDeclaredMethod("setWindowCrop", SurfaceControl.class, Rect.class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(transaction, surfaceControl, rect);
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "AnimationUtils", "reflect setWindowCrop error: " + e, null, 4, null);
        }
    }

    public static final boolean supportSetMiCornerRadii() {
        return getSetMiCornerRadiiMethod() != null;
    }
}
