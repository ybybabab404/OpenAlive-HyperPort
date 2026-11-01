package com.miui.keyguard.shortcuts.controller;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.keyguard.shortcuts.manager.ShortcutWindowManager;
import com.miui.keyguard.shortcuts.utils.BlurHelper;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.keyguard.shortcuts.view.BaseShortcutAnimView;
import java.io.IOException;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: BaseShortcutAnimController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class BaseShortcutAnimController<T extends BaseShortcutAnimView> {
    public static final Companion Companion = new Companion(null);
    public float activeAnimPer;
    public final BlurHelper blurHelper;
    public ImageView bottomShortcutView;
    public final Configuration configuration;
    public final Context context;
    public Handler mainHandler;
    public float moveDistance;
    public float moveDistanceY;
    public float movePer;
    public float moveYPer;
    public BaseShortcutAnimView shortcutAnimView;
    public final ViewGroup viewGroup;

    /* JADX INFO: compiled from: BaseShortcutAnimController.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BaseShortcutAnimController(Context context, ViewGroup viewGroup) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(viewGroup, "viewGroup");
        this.context = context;
        this.viewGroup = viewGroup;
        this.blurHelper = new BlurHelper();
        this.configuration = new Configuration();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public static /* synthetic */ void addAnimShortcutView$default(BaseShortcutAnimController baseShortcutAnimController, Function0 function0, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addAnimShortcutView");
        }
        if ((i & 1) != 0) {
            function0 = null;
        }
        baseShortcutAnimController.addAnimShortcutView(function0);
    }

    public final void addAnimShortcutView(Function0 function0) {
        ShortcutWindowManager.INSTANCE.addView(getShortcutAnimView(), function0);
    }

    public void destroy() {
    }

    public final float getActiveAnimPer() {
        return this.activeAnimPer;
    }

    public final BlurHelper getBlurHelper() {
        return this.blurHelper;
    }

    public final Context getContext() {
        return this.context;
    }

    public final Handler getMainHandler() {
        return this.mainHandler;
    }

    public final float getMoveDistance() {
        return this.moveDistance;
    }

    public final float getMoveDistanceY() {
        return this.moveDistanceY;
    }

    public final float getMovePer() {
        return this.movePer;
    }

    public final float getMoveYPer() {
        return this.moveYPer;
    }

    public BaseShortcutAnimView getShortcutAnimView() {
        BaseShortcutAnimView baseShortcutAnimView = this.shortcutAnimView;
        if (baseShortcutAnimView != null) {
            return baseShortcutAnimView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("shortcutAnimView");
        return null;
    }

    public final void initBottomShortcutView(int i) {
        this.bottomShortcutView = (ImageView) this.viewGroup.findViewById(i);
    }

    public void keyguardGoingAway() {
    }

    public final void onBottomIconDarkModeChanged(boolean z) {
        getShortcutAnimView().setIconDarkMode(z);
    }

    public void onChargeAnimationStart() {
    }

    public void onConfigurationChanged(Configuration newConfiguration) throws IOException {
        Intrinsics.checkNotNullParameter(newConfiguration, "newConfiguration");
        getShortcutAnimView().onConfigurationChanged(newConfiguration);
        int iUpdateFrom = this.configuration.updateFrom(newConfiguration);
        boolean z = (iUpdateFrom & 128) != 0;
        if ((iUpdateFrom & AutoBottomSheetKt.ALIGN_SCREEN_END) == 0 && !z) {
            return;
        }
        reset("smallWidthChanged|orientationChanged");
    }

    public void onFinishedGoingToSleep() {
    }

    public void onKeyguardOccludedChanged(boolean z) {
    }

    public void onKeyguardShowingChanged(boolean z) {
    }

    public void onStartedWakingUp() {
    }

    public final void removeAnimShortcutView() {
        LogUtils.logD$default(LogUtils.INSTANCE, "BaseShortcutAnimController", "removeShortcutView=" + getShortcutAnimView(), null, 4, null);
        ShortcutWindowManager.INSTANCE.removeView(getShortcutAnimView());
    }

    public void reset(String reason) throws IOException {
        Intrinsics.checkNotNullParameter(reason, "reason");
        this.activeAnimPer = 0.0f;
        this.moveDistance = 0.0f;
        getShortcutAnimView().reset(reason);
        updateBlurRatio(0.0f);
    }

    public final void setActiveAnimPer(float f) {
        this.activeAnimPer = f;
    }

    public final void setBlurContainerScale(float f, float f2) {
        View blurContainerView = ShortcutWindowManager.INSTANCE.getBlurContainerView();
        if (blurContainerView != null) {
            blurContainerView.setScaleX(f);
            blurContainerView.setScaleY(f2);
        }
    }

    public final void setMoveDistance(float f) {
        this.moveDistance = f;
    }

    public final void setMoveDistanceY(float f) {
        this.moveDistanceY = f;
    }

    public final void setMovePer(float f) {
        this.movePer = f;
    }

    public final void setMoveYPer(float f) {
        this.moveYPer = f;
    }

    public final void updateBlurRatio(float f) throws IOException {
        this.blurHelper.applyBlur(ShortcutWindowManager.INSTANCE.getBlurContainerView(), f);
    }

    public final void updateShortcutIconDrawable(Drawable drawable) {
        getShortcutAnimView().updateShortcutIconDrawable(drawable);
    }

    public final void updateShortcutIconSize(int i, int i2) {
        getShortcutAnimView().updateShortcutIconSize(i, i2);
    }

    public final void updateShortcutView(int i) {
        initBottomShortcutView(i);
        ImageView imageView = this.bottomShortcutView;
        if (imageView != null) {
            updateShortcutIconSize(imageView.getWidth(), imageView.getHeight());
            updateShortcutIconDrawable(imageView.getDrawable());
        }
    }
}
