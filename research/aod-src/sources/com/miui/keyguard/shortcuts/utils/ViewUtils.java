package com.miui.keyguard.shortcuts.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.miui.keyguard.shortcuts.R;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.miwallpaper.MiuiWallpaperManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import miui.os.UserHandleEx;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ViewUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ViewUtils {
    public static ColorStateList bottomIconDarkColorList;
    public static ColorStateList bottomIconLightColorList;
    public static final ViewUtils INSTANCE = new ViewUtils();
    public static final Uri SYSTEMUI_WALLPAPER_URI = Uri.parse("content://com.miui.systemui.keyguard.wallpaper");
    public static final Lazy getPassWindowBlurEnabledMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.utils.ViewUtils$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ViewUtils.getPassWindowBlurEnabledMethod_delegate$lambda$2();
        }
    });
    public static final Lazy setPassWindowBlurEnabledMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.utils.ViewUtils$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ViewUtils.setPassWindowBlurEnabledMethod_delegate$lambda$5();
        }
    });
    public static final Lazy setMiBackgroundBlurModeMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.utils.ViewUtils$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ViewUtils.setMiBackgroundBlurModeMethod_delegate$lambda$8();
        }
    });
    public static final Lazy setMiViewBlurModeMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.utils.ViewUtils$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ViewUtils.setMiViewBlurModeMethod_delegate$lambda$11();
        }
    });
    public static final Lazy setMiBackgroundBlurRadiusMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.utils.ViewUtils$$ExternalSyntheticLambda4
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ViewUtils.setMiBackgroundBlurRadiusMethod_delegate$lambda$14();
        }
    });

    public static final Method getPassWindowBlurEnabledMethod_delegate$lambda$2() {
        Object objM1061constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(Class.forName("android.view.View").getMethod("getPassWindowBlurEnabled", new Class[0]));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "get getPassWindowBlurEnabled method error: " + thM1063exceptionOrNullimpl, null, 4, null);
            objM1061constructorimpl = null;
        }
        return (Method) objM1061constructorimpl;
    }

    public static final Method setMiBackgroundBlurModeMethod_delegate$lambda$8() {
        Object objM1061constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(Class.forName("android.view.View").getMethod("setMiBackgroundBlurMode", Integer.TYPE));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "get setMiBackgroundBlurMode method error: " + thM1063exceptionOrNullimpl, null, 4, null);
            objM1061constructorimpl = null;
        }
        return (Method) objM1061constructorimpl;
    }

    public static final Method setMiBackgroundBlurRadiusMethod_delegate$lambda$14() {
        Object objM1061constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(Class.forName("android.view.View").getMethod("setMiBackgroundBlurRadius", Integer.TYPE));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "get setMiBackgroundBlurRadius method error: " + thM1063exceptionOrNullimpl, null, 4, null);
            objM1061constructorimpl = null;
        }
        return (Method) objM1061constructorimpl;
    }

    public static final Method setMiViewBlurModeMethod_delegate$lambda$11() {
        Object objM1061constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(Class.forName("android.view.View").getMethod("setMiViewBlurMode", Integer.TYPE));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "get setMiViewBlurMode method error: " + thM1063exceptionOrNullimpl, null, 4, null);
            objM1061constructorimpl = null;
        }
        return (Method) objM1061constructorimpl;
    }

    public static final Method setPassWindowBlurEnabledMethod_delegate$lambda$5() {
        Object objM1061constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(Class.forName("android.view.View").getMethod("setPassWindowBlurEnabled", Boolean.TYPE));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "get setPassWindowBlurEnabled method error: " + thM1063exceptionOrNullimpl, null, 4, null);
            objM1061constructorimpl = null;
        }
        return (Method) objM1061constructorimpl;
    }

    public final void createAndUpdateWallpaper(Context context, MiuiWallpaperManager miuiWallpaperManager, Function1 action) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(action, "action");
        Bitmap fitScreenWallpaperPreview = miuiWallpaperManager != null ? miuiWallpaperManager.getFitScreenWallpaperPreview(context, 2) : null;
        LogUtils logUtils = LogUtils.INSTANCE;
        LogUtils.logD$default(logUtils, "ViewUtils", "createAndUpdateWallpaper: " + fitScreenWallpaperPreview, null, 4, null);
        if (fitScreenWallpaperPreview != null) {
            ViewUtils viewUtils = INSTANCE;
            Rect screenSize = viewUtils.getScreenSize(context);
            Rect croppedWallpaperPreviewBitmapBounds = viewUtils.getCroppedWallpaperPreviewBitmapBounds(fitScreenWallpaperPreview.getWidth(), fitScreenWallpaperPreview.getHeight(), screenSize.width(), screenSize.height());
            if (croppedWallpaperPreviewBitmapBounds.width() <= 0 || croppedWallpaperPreviewBitmapBounds.height() <= 0) {
                LogUtils.logE$default(logUtils, "ViewUtils", "createAndUpdateWallpaper: invalid croppedBitmapBounds: " + croppedWallpaperPreviewBitmapBounds + ", bitmapSize: " + fitScreenWallpaperPreview.getWidth() + AnimatedProperty.PROPERTY_NAME_X + fitScreenWallpaperPreview.getHeight() + ", screenSize: " + screenSize.width() + AnimatedProperty.PROPERTY_NAME_X + screenSize.height(), null, 4, null);
            } else {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(fitScreenWallpaperPreview, croppedWallpaperPreviewBitmapBounds.left, croppedWallpaperPreviewBitmapBounds.top, croppedWallpaperPreviewBitmapBounds.width(), croppedWallpaperPreviewBitmapBounds.height());
                Intrinsics.checkNotNull(bitmapCreateBitmap);
                action.invoke(bitmapCreateBitmap);
            }
            fitScreenWallpaperPreview.recycle();
        }
    }

    public final boolean getBottomIconDeep(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            Bundle bundleCall = context.createContextAsUser(UserHandleEx.OWNER, 0).getContentResolver().call(SYSTEMUI_WALLPAPER_URI, "isWallpaperBottomIconDeep", (String) null, new Bundle());
            LogUtils.logD$default(LogUtils.INSTANCE, "ViewUtils", "getBottomIconDeep: " + (bundleCall != null ? Boolean.valueOf(bundleCall.getBoolean("result_boolean")) : null), null, 4, null);
            if (bundleCall != null) {
                return bundleCall.getBoolean("result_boolean");
            }
            return true;
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "getBottomIconDeep failed: " + e, null, 4, null);
            return true;
        }
    }

    public final Rect getCroppedWallpaperPreviewBitmapBounds(int i, int i2, int i3, int i4) {
        Pair pair = (i2 > i) == (i4 > i3) ? TuplesKt.to(Integer.valueOf(RangesKt.coerceAtMost(i, i3)), Integer.valueOf(RangesKt.coerceAtMost(i2, i4))) : TuplesKt.to(Integer.valueOf(RangesKt.coerceAtMost(i, i4)), Integer.valueOf(RangesKt.coerceAtMost(i2, i3)));
        int iIntValue = ((Number) pair.component1()).intValue();
        int iIntValue2 = ((Number) pair.component2()).intValue();
        return new Rect(0, iIntValue2 - RangesKt.coerceAtMost(iIntValue2, 368), iIntValue, iIntValue2);
    }

    public final Method getGetPassWindowBlurEnabledMethod() {
        return (Method) getPassWindowBlurEnabledMethod$delegate.getValue();
    }

    public final ColorStateList getIconTintList(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            if (bottomIconLightColorList == null) {
                bottomIconLightColorList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.miuix_default_color_on_surface_secondary_light));
            }
            if (bottomIconDarkColorList == null) {
                bottomIconDarkColorList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.miuix_default_color_on_surface_secondary_dark));
            }
            return z ? bottomIconDarkColorList : bottomIconLightColorList;
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "getIconTintList failed: " + e, null, 4, null);
            return null;
        }
    }

    public final boolean getPassWindowBlurEnabledCompat(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        try {
            Method getPassWindowBlurEnabledMethod = getGetPassWindowBlurEnabledMethod();
            Object objInvoke = getPassWindowBlurEnabledMethod != null ? getPassWindowBlurEnabledMethod.invoke(view, new Object[0]) : null;
            Boolean bool = objInvoke instanceof Boolean ? (Boolean) objInvoke : null;
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "reflect setPassWindowBlurEnabled error: " + e, null, 4, null);
            return false;
        }
    }

    public final Rect getScreenSize(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        Rect bounds = ((WindowManager) systemService).getCurrentWindowMetrics().getBounds();
        Intrinsics.checkNotNullExpressionValue(bounds, "getBounds(...)");
        return bounds;
    }

    public final Method getSetMiBackgroundBlurModeMethod() {
        return (Method) setMiBackgroundBlurModeMethod$delegate.getValue();
    }

    public final Method getSetMiBackgroundBlurRadiusMethod() {
        return (Method) setMiBackgroundBlurRadiusMethod$delegate.getValue();
    }

    public final Method getSetMiViewBlurModeMethod() {
        return (Method) setMiViewBlurModeMethod$delegate.getValue();
    }

    public final Method getSetPassWindowBlurEnabledMethod() {
        return (Method) setPassWindowBlurEnabledMethod$delegate.getValue();
    }

    public final ViewRootImpl getViewRootImplByReflection(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        try {
            Method method = View.class.getMethod("getViewRootImpl", new Class[0]);
            method.setAccessible(true);
            return (ViewRootImpl) method.invoke(view, new Object[0]);
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "reflect get surface control error: " + e, null, 4, null);
            return null;
        }
    }

    public final SurfaceControl getViewRootImplSurfaceControl(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        try {
            ViewRootImpl viewRootImplByReflection = getViewRootImplByReflection(view);
            Field declaredField = ViewRootImpl.class.getDeclaredField("mSurfaceControl");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(viewRootImplByReflection);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type android.view.SurfaceControl");
            return (SurfaceControl) obj;
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "reflect getViewRootImplSurfaceControl error: " + e, null, 4, null);
            return null;
        }
    }

    public final View getViewRootViewByReflection(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        try {
            ViewRootImpl viewRootImplByReflection = getViewRootImplByReflection(view);
            Method declaredMethod = ViewRootImpl.class.getDeclaredMethod("getView", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(viewRootImplByReflection, new Object[0]);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type android.view.View");
            return (View) objInvoke;
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "reflect getViewRootView error: " + e, null, 4, null);
            return null;
        }
    }

    public final boolean isLargeScreen() {
        if (!miuix.os.DeviceHelper.isFoldable()) {
            return false;
        }
        int i = Resources.getSystem().getConfiguration().screenLayout & 15;
        return i == 3 || i == 4;
    }

    public final boolean setMiBackgroundBlurModeCompat(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        try {
            Method setMiBackgroundBlurModeMethod = getSetMiBackgroundBlurModeMethod();
            Object objInvoke = setMiBackgroundBlurModeMethod != null ? setMiBackgroundBlurModeMethod.invoke(view, Integer.valueOf(i)) : null;
            Boolean bool = objInvoke instanceof Boolean ? (Boolean) objInvoke : null;
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "reflect setMiBackgroundBlurModeCompat error: " + e, null, 4, null);
            return false;
        }
    }

    public final void setMiBackgroundBlurRadiusCompat(View view, int i) {
        Object objM1061constructorimpl;
        Intrinsics.checkNotNullParameter(view, "<this>");
        try {
            Result.Companion companion = Result.Companion;
            Method setMiBackgroundBlurRadiusMethod = INSTANCE.getSetMiBackgroundBlurRadiusMethod();
            objM1061constructorimpl = Result.m1061constructorimpl(setMiBackgroundBlurRadiusMethod != null ? setMiBackgroundBlurRadiusMethod.invoke(view, Integer.valueOf(i)) : null);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl == null) {
            return;
        }
        LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "reflect setMiBackgroundBlurRadiusCompat error: " + thM1063exceptionOrNullimpl, null, 4, null);
    }

    public final void setMiViewBlurModeCompat(View view, int i) {
        Object objM1061constructorimpl;
        Intrinsics.checkNotNullParameter(view, "<this>");
        try {
            Result.Companion companion = Result.Companion;
            Method setMiViewBlurModeMethod = INSTANCE.getSetMiViewBlurModeMethod();
            objM1061constructorimpl = Result.m1061constructorimpl(setMiViewBlurModeMethod != null ? setMiViewBlurModeMethod.invoke(view, Integer.valueOf(i)) : null);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl == null) {
            return;
        }
        LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "reflect setMiViewBlurModeCompat error: " + thM1063exceptionOrNullimpl, null, 4, null);
    }

    public final boolean setPassWindowBlurEnabledCompat(View view, boolean z) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        try {
            Method setPassWindowBlurEnabledMethod = getSetPassWindowBlurEnabledMethod();
            Object objInvoke = setPassWindowBlurEnabledMethod != null ? setPassWindowBlurEnabledMethod.invoke(view, Boolean.valueOf(z)) : null;
            Boolean bool = objInvoke instanceof Boolean ? (Boolean) objInvoke : null;
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ViewUtils", "reflect setPassWindowBlurEnabled error: " + e, null, 4, null);
            return false;
        }
    }

    public final void showIfDrawablePresent(ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "<this>");
        if (imageView.getDrawable() == null) {
            imageView = null;
        }
        if (imageView != null) {
            imageView.setVisibility(0);
        }
    }
}
