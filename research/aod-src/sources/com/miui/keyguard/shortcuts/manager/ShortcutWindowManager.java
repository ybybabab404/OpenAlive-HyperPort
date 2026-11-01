package com.miui.keyguard.shortcuts.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.keyguard.shortcuts.utils.ViewUtils;
import java.lang.reflect.Method;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: ShortcutWindowManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SuppressLint({"StaticFieldLeak"})
@SourceDebugExtension
public final class ShortcutWindowManager {
    public static final ShortcutWindowManager INSTANCE = new ShortcutWindowManager();
    public static View blurContainerView;
    public static boolean isSurfaceHidden;
    public static FrameLayout rootView;
    public static WindowManager.LayoutParams windowLayoutParams;
    public static WindowManager windowManager;

    public static final void removeView$lambda$4$lambda$3(FrameLayout frameLayout, View view) {
        frameLayout.removeView(view);
        if (frameLayout.getChildCount() == 1) {
            INSTANCE.updateKeepScreenOnFlag(false);
        }
    }

    public final void addRootViewToWindow(final Function0 function0) {
        WindowManager.LayoutParams layoutParams;
        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutWindowManager", "addRootViewToWindow", null, 4, null);
        final FrameLayout frameLayout = rootView;
        if (frameLayout == null || frameLayout.isAttachedToWindow() || frameLayout.getParent() != null || (layoutParams = windowLayoutParams) == null) {
            return;
        }
        WindowManager windowManager2 = windowManager;
        if (windowManager2 != null) {
            windowManager2.addView(frameLayout, layoutParams);
        }
        frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.miui.keyguard.shortcuts.manager.ShortcutWindowManager$addRootViewToWindow$1$1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                frameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ShortcutWindowManager.isSurfaceHidden = false;
                Function0 function1 = function0;
                if (function1 != null) {
                    function1.invoke();
                }
            }
        });
    }

    public final void addView(View view, Function0 function0) {
        Intrinsics.checkNotNullParameter(view, "view");
        ensureWindowSurfaceVisible();
        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutWindowManager", "addViewToRootView, view: " + view, null, 4, null);
        FrameLayout frameLayout = rootView;
        if (frameLayout != null) {
            if (!frameLayout.isAttachedToWindow()) {
                INSTANCE.addRootViewToWindow(function0);
            } else if (function0 != null) {
                function0.invoke();
            }
            if (frameLayout.indexOfChild(view) == -1) {
                frameLayout.addView(view);
            }
            INSTANCE.updateKeepScreenOnFlag(true);
        }
    }

    public final void applyParams() {
        WindowManager windowManager2;
        FrameLayout frameLayout = rootView;
        if (frameLayout == null || !frameLayout.isAttachedToWindow() || (windowManager2 = windowManager) == null) {
            return;
        }
        windowManager2.updateViewLayout(frameLayout, windowLayoutParams);
    }

    public final void ensureWindowSurfaceVisible() {
        if (isSurfaceHidden) {
            FrameLayout frameLayout = rootView;
            SurfaceControl viewRootImplSurfaceControl = frameLayout != null ? ViewUtils.INSTANCE.getViewRootImplSurfaceControl(frameLayout) : null;
            if (viewRootImplSurfaceControl == null || !viewRootImplSurfaceControl.isValid()) {
                return;
            }
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.show(viewRootImplSurfaceControl);
            transaction.apply();
            isSurfaceHidden = false;
            LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutWindowManager", "ensureWindowSurfaceVisible", null, 4, null);
        }
    }

    public final View getBlurContainerView() {
        return blurContainerView;
    }

    public final SurfaceControl getViewRootImplSurfaceControl() {
        FrameLayout frameLayout = rootView;
        if (frameLayout != null) {
            return ViewUtils.INSTANCE.getViewRootImplSurfaceControl(frameLayout);
        }
        return null;
    }

    public final void hideWindowSurface() {
        FrameLayout frameLayout = rootView;
        SurfaceControl viewRootImplSurfaceControl = frameLayout != null ? ViewUtils.INSTANCE.getViewRootImplSurfaceControl(frameLayout) : null;
        if (viewRootImplSurfaceControl == null || !viewRootImplSurfaceControl.isValid()) {
            return;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.hide(viewRootImplSurfaceControl);
        transaction.apply();
        isSurfaceHidden = true;
        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutWindowManager", "hideWindowSurface", null, 4, null);
    }

    public final void init(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        windowManager = (WindowManager) systemService;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2017, 218171160, -2);
        INSTANCE.setTrustedOverlayByReflection(layoutParams);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.setTitle("miui_keyguard_shortcut");
        windowLayoutParams = layoutParams;
        FrameLayout frameLayout = new FrameLayout(context);
        rootView = frameLayout;
        frameLayout.setClipChildren(false);
        View view = new View(context);
        blurContainerView = view;
        view.setAlpha(0.0f);
        FrameLayout frameLayout2 = rootView;
        if (frameLayout2 != null) {
            frameLayout2.addView(blurContainerView, -1, -1);
        }
    }

    public final void release() {
        FrameLayout frameLayout = rootView;
        if (frameLayout != null && frameLayout.isAttachedToWindow()) {
            FrameLayout frameLayout2 = rootView;
            if (frameLayout2 != null) {
                frameLayout2.removeAllViews();
            }
            WindowManager windowManager2 = windowManager;
            if (windowManager2 != null) {
                windowManager2.removeViewImmediate(rootView);
            }
        }
        blurContainerView = null;
        rootView = null;
        windowLayoutParams = null;
        windowManager = null;
        isSurfaceHidden = false;
    }

    public final void removeRootViewFromWindow() {
        WindowManager windowManager2;
        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutWindowManager", "removeRootViewFromWindow", null, 4, null);
        FrameLayout frameLayout = rootView;
        if (frameLayout != null && frameLayout.isAttachedToWindow() && (windowManager2 = windowManager) != null) {
            windowManager2.removeView(rootView);
        }
        isSurfaceHidden = false;
    }

    public final void removeView(final View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutWindowManager", "removeViewFromRootView, view: " + view, null, 4, null);
        final FrameLayout frameLayout = rootView;
        if (frameLayout == null || !frameLayout.isAttachedToWindow()) {
            return;
        }
        view.setVisibility(4);
        frameLayout.post(new Runnable() { // from class: com.miui.keyguard.shortcuts.manager.ShortcutWindowManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ShortcutWindowManager.removeView$lambda$4$lambda$3(frameLayout, view);
            }
        });
    }

    public final void setAlpha(float f) {
        FrameLayout frameLayout = rootView;
        if (frameLayout != null) {
            frameLayout.setAlpha(f);
        }
    }

    public final void setTrustedOverlayByReflection(WindowManager.LayoutParams layoutParams) {
        try {
            Method declaredMethod = WindowManager.LayoutParams.class.getDeclaredMethod("setTrustedOverlay", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(layoutParams, new Object[0]);
        } catch (Exception e) {
            LogUtils.INSTANCE.logE("ShortcutWindowManager", "reflect setTrustedOverlay err", e);
        }
    }

    public final void updateKeepScreenOnFlag(boolean z) {
        WindowManager.LayoutParams layoutParams = windowLayoutParams;
        if (layoutParams != null) {
            layoutParams.flags = z ? layoutParams.flags | 128 : layoutParams.flags & (-129);
            INSTANCE.applyParams();
        }
    }
}
