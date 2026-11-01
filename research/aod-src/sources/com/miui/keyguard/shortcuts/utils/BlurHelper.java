package com.miui.keyguard.shortcuts.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.SystemProperties;
import android.util.MathUtils;
import android.view.CrossWindowBlurListeners;
import android.view.SurfaceControl;
import android.view.View;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.io.CloseableKt;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: BlurHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class BlurHelper {
    public int belowTenBlurCount;
    public boolean earlyWakeupEnabled;
    public boolean isBouncerShowing;
    public int lastAppliedBlur;
    public static final Companion Companion = new Companion(null);
    public static final CrossWindowBlurListeners crossWindowBlurListeners = CrossWindowBlurListenersExtKt.getCrossWindowBlurListenersInstanceByReflection();
    public static final Lazy BACKGROUND_BLUR_SUPPORTED$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.utils.BlurHelper$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(BlurHelper.BACKGROUND_BLUR_SUPPORTED_delegate$lambda$1());
        }
    });
    public static final Lazy blurField$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.utils.BlurHelper$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return BlurHelper.blurField_delegate$lambda$4();
        }
    });
    public final int minBlurRadius = 1;
    public final int maxBlurRadius = 100;
    public final int blurBgColor = 1714302510;
    public AtomicBoolean isUseNonHandleBlur = new AtomicBoolean(false);

    /* JADX INFO: compiled from: BlurHelper.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean getBACKGROUND_BLUR_SUPPORTED() {
            return ((Boolean) BlurHelper.BACKGROUND_BLUR_SUPPORTED$delegate.getValue()).booleanValue();
        }

        public final Field getBlurField() {
            return (Field) BlurHelper.blurField$delegate.getValue();
        }

        public final boolean isHighEndGfxByReflection() {
            try {
                Method declaredMethod = ActivityManager.class.getDeclaredMethod("isHighEndGfx", new Class[0]);
                declaredMethod.setAccessible(true);
                Object objInvoke = declaredMethod.invoke(null, new Object[0]);
                Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Boolean");
                return ((Boolean) objInvoke).booleanValue();
            } catch (Exception e) {
                LogUtils.logE$default(LogUtils.INSTANCE, "BlurHelper", "reflect setTrustedOverlay error: " + e, null, 4, null);
                return false;
            }
        }

        public final boolean supportBackgroundBlur(Context context) {
            if (context != null) {
                Companion companion = BlurHelper.Companion;
                if (companion.getBACKGROUND_BLUR_SUPPORTED()) {
                    Field blurField = companion.getBlurField();
                    Object obj = blurField != null ? blurField.get(context.getResources().getConfiguration()) : null;
                    Integer num = obj instanceof Integer ? (Integer) obj : null;
                    if ((num != null ? num.intValue() : -1) == 1) {
                        return true;
                    }
                }
            }
            return false;
        }

        public final boolean supportsBlursOnWindows() {
            if (SystemProperties.getBoolean("ro.surface_flinger.supports_background_blur", false) && isHighEndGfxByReflection()) {
                CrossWindowBlurListeners crossWindowBlurListeners = BlurHelper.crossWindowBlurListeners;
                if ((crossWindowBlurListeners != null ? CrossWindowBlurListenersExtKt.isCrossWindowBlurEnabledByRefection(crossWindowBlurListeners) : false) && !SystemProperties.getBoolean("persist.sysui.disableBlur", false)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static final boolean BACKGROUND_BLUR_SUPPORTED_delegate$lambda$1() {
        return SystemProperties.getBoolean("persist.sys.background_blur_supported", false);
    }

    public static final Field blurField_delegate$lambda$4() {
        Object objM1061constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(Configuration.class.getDeclaredField("blur"));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logE$default(LogUtils.INSTANCE, "BlurHelper", "get blur field error: " + thM1063exceptionOrNullimpl, null, 4, null);
            objM1061constructorimpl = null;
        }
        return (Field) objM1061constructorimpl;
    }

    public final void applyAdvancedMaterialsBlur(View view, float f) {
        if (view == null || view.getContext() == null) {
            return;
        }
        int iBlurRadiusOfRatio = (int) blurRadiusOfRatio(f);
        ViewUtils viewUtils = ViewUtils.INSTANCE;
        if (!viewUtils.getPassWindowBlurEnabledCompat(view) && iBlurRadiusOfRatio > 0) {
            viewUtils.setPassWindowBlurEnabledCompat(view, true);
            viewUtils.setMiBackgroundBlurModeCompat(view, 1);
        }
        if (viewUtils.getPassWindowBlurEnabledCompat(view) && iBlurRadiusOfRatio <= 0) {
            viewUtils.setPassWindowBlurEnabledCompat(view, false);
            viewUtils.setMiBackgroundBlurModeCompat(view, 0);
        }
        viewUtils.setMiViewBlurModeCompat(view, iBlurRadiusOfRatio <= 0 ? 0 : 1);
        if (this.isBouncerShowing && iBlurRadiusOfRatio > 0 && iBlurRadiusOfRatio < 10) {
            int i = this.belowTenBlurCount + 1;
            this.belowTenBlurCount = i;
            iBlurRadiusOfRatio = i == 1 ? 6 : 0;
        }
        viewUtils.setMiBackgroundBlurRadiusCompat(view, iBlurRadiusOfRatio);
    }

    public final void applyBackgroundBlur(View view, float f) {
        if (view == null || view.getContext() == null) {
            return;
        }
        if (f <= 0.0f) {
            view.setBackgroundColor(0);
            this.isUseNonHandleBlur.set(false);
        } else if (this.isUseNonHandleBlur.compareAndSet(false, true)) {
            view.setBackgroundColor(this.blurBgColor);
        }
    }

    public final void applyBlur(SurfaceControl surfaceControl, int i, boolean z) throws IOException {
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        try {
            if (Companion.supportsBlursOnWindows()) {
                setBackgroundBlurRadiusByReflection(transaction, surfaceControl, i);
                if (!this.earlyWakeupEnabled && this.lastAppliedBlur == 0 && i != 0) {
                    setEarlyWakeupStartByReflection(transaction);
                    this.earlyWakeupEnabled = true;
                }
                if (this.earlyWakeupEnabled && this.lastAppliedBlur != 0 && i == 0) {
                    setEarlyWakeupEndByReflection(transaction);
                    this.earlyWakeupEnabled = false;
                }
                this.lastAppliedBlur = i;
            }
            transaction.setOpaque(surfaceControl, z);
            transaction.apply();
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(transaction, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(transaction, th);
                throw th2;
            }
        }
    }

    public final void applyBlur(View view, float f) throws IOException {
        if (view == null || view.getContext() == null) {
            return;
        }
        if (Companion.supportBackgroundBlur(view.getContext())) {
            applyBlurViewAlpha(view, f, false);
            applyAdvancedMaterialsBlur(view, f);
        } else if (DeviceHelper.INSTANCE.isDeviceUseBlur()) {
            applyBlurViewAlpha(view, f, false);
            applyBlur(ViewUtils.INSTANCE.getViewRootImplSurfaceControl(view), (int) blurRadiusOfRatio(f), false);
        } else {
            applyBackgroundBlur(view, f);
            applyBlurViewAlpha(view, f, true);
        }
    }

    public final void applyBlurViewAlpha(View view, float f, boolean z) {
        if (z) {
            view.setAlpha(f);
            return;
        }
        if (view.getAlpha() == 0.0f && f != 0.0f) {
            view.setAlpha(1.0f);
        } else if (view.getAlpha() != 0.0f && f == 0.0f) {
            view.setAlpha(0.0f);
        }
    }

    public final float blurRadiusOfRatio(float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        return MathUtils.lerp(this.minBlurRadius, this.maxBlurRadius, f);
    }

    public final void isBouncerShowing(boolean z) {
        this.isBouncerShowing = z;
        this.belowTenBlurCount = 0;
    }

    public final void setBackgroundBlurRadiusByReflection(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i) {
        try {
            Method declaredMethod = SurfaceControl.Transaction.class.getDeclaredMethod("setBackgroundBlurRadius", SurfaceControl.class, Integer.TYPE);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(transaction, surfaceControl, Integer.valueOf(i));
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "BlurHelper", "reflect setBackgroundBlurRadius error: " + e, null, 4, null);
        }
    }

    public final void setEarlyWakeupEndByReflection(SurfaceControl.Transaction transaction) {
        try {
            Method declaredMethod = SurfaceControl.Transaction.class.getDeclaredMethod("setEarlyWakeupEnd", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(transaction, new Object[0]);
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "BlurHelper", "reflect setEarlyWakeupEnd error: " + e, null, 4, null);
        }
    }

    public final void setEarlyWakeupStartByReflection(SurfaceControl.Transaction transaction) {
        try {
            Method declaredMethod = SurfaceControl.Transaction.class.getDeclaredMethod("setEarlyWakeupStart", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(transaction, new Object[0]);
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "BlurHelper", "reflect setEarlyWakeupStart error: " + e, null, 4, null);
        }
    }
}
