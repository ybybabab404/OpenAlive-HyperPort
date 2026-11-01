package com.miui.keyguard.shortcuts.controller;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.MotionEvent;
import android.view.RemoteAnimationTarget;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.miui.keyguard.shortcuts.R;
import com.miui.keyguard.shortcuts.manager.MiuiShortcutManager;
import com.miui.keyguard.shortcuts.manager.OccludedAnimTrace;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.keyguard.shortcuts.utils.ViewUtils;
import java.io.IOException;
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
/* JADX INFO: compiled from: ShortcutMoveController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ShortcutMoveController {
    public static final Companion Companion = new Companion(null);
    public float activeMoveDistance;
    public Callback callback;
    public final Context context;
    public float initialTouchX;
    public float initialTouchY;
    public boolean isLeftShortcutMoved;
    public boolean isLeftShortcutTouched;
    public boolean isRightShortcutMoved;
    public boolean isRightShortcutTouched;
    public final ImageView leftIcon;
    public boolean motionCancelled;
    public float moveDistance;
    public final ImageView rightIcon;
    public final Point screenPoint;
    public final ShortcutMoveController$shortcutOccludedAnimCallback$1 shortcutOccludedAnimCallback;
    public final ShortcutOccludedAnimController shortcutOccludedAnimController;
    public float touchSlop;
    public float touchX;
    public float touchY;
    public final ViewGroup viewGroup;

    /* JADX INFO: compiled from: ShortcutMoveController.kt */
    @Metadata
    public interface Callback {

        /* JADX INFO: compiled from: ShortcutMoveController.kt */
        @Metadata
        public static final class DefaultImpls {
            public static /* synthetic */ void updateCanShowBiometricAuthentication$default(Callback callback, boolean z, boolean z2, int i, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateCanShowBiometricAuthentication");
                }
                if ((i & 2) != 0) {
                    z2 = false;
                }
                callback.updateCanShowBiometricAuthentication(z, z2);
            }

            public static void updateSwipingInProgress(Callback callback, boolean z) {
            }
        }

        void onBackAnimationEnd();

        void onDismissAnimationEnd();

        void onFullscreenAnimationEnd();

        void onFullscreenAnimationStart();

        void onMoveDistanceUpdate(float f);

        void onOccludedAnimationEnd();

        void onOccludedAnimationStart();

        void updateCanShowBiometricAuthentication(boolean z, boolean z2);

        void updateSwipingInProgress(boolean z);
    }

    /* JADX INFO: compiled from: ShortcutMoveController.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.miui.keyguard.shortcuts.controller.ShortcutMoveController$shortcutOccludedAnimCallback$1, com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$Callback] */
    public ShortcutMoveController(Context context, ViewGroup viewGroup, Callback callback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(viewGroup, "viewGroup");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.context = context;
        this.viewGroup = viewGroup;
        this.callback = callback;
        Point point = new Point();
        this.screenPoint = point;
        ?? r0 = new ShortcutOccludedAnimController.Callback() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutMoveController$shortcutOccludedAnimCallback$1
            @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
            public void onBackAnimationEnd() throws IOException {
                reset("shortcutOccludedAnimCallback#onBackAnimationEnd");
                this.this$0.getCallback().onBackAnimationEnd();
                this.this$0.getCallback().updateCanShowBiometricAuthentication(true, true);
            }

            @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
            public void onBackAnimationUpdate(float f) {
                this.this$0.getCallback().onMoveDistanceUpdate(f);
            }

            @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
            public void onDismissAnimationEnd() {
                this.this$0.getCallback().onDismissAnimationEnd();
            }

            @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
            public void onFullscreenAnimationEnd() {
                this.this$0.getCallback().onFullscreenAnimationEnd();
            }

            @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
            public void onFullscreenAnimationStart() {
                this.this$0.getCallback().onFullscreenAnimationStart();
            }

            @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
            public void onOccludedAnimationEnd() throws IOException {
                this.this$0.getCallback().onOccludedAnimationEnd();
                ShortcutMoveController.Callback.DefaultImpls.updateCanShowBiometricAuthentication$default(this.this$0.getCallback(), MiuiShortcutManager.INSTANCE.shouldShowFingerprintOrFaceIcon(), false, 2, null);
                reset("shortcutOccludedAnimCallback#onOccludedAnimationEnd");
            }

            @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
            public void onOccludedAnimationStart() {
                this.this$0.getCallback().onOccludedAnimationStart();
                ShortcutMoveController.Callback.DefaultImpls.updateCanShowBiometricAuthentication$default(this.this$0.getCallback(), false, false, 2, null);
            }

            @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
            public void reset(String reason) throws IOException {
                Intrinsics.checkNotNullParameter(reason, "reason");
                ShortcutOccludedAnimController.Callback.DefaultImpls.reset(this, reason);
                this.this$0.reset(reason);
            }
        };
        this.shortcutOccludedAnimCallback = r0;
        Display display = context.getDisplay();
        if (display != null) {
            display.getRealSize(point);
        }
        initDimens();
        this.leftIcon = (ImageView) viewGroup.findViewById(R.id.shortcut_view_left);
        this.rightIcon = (ImageView) viewGroup.findViewById(R.id.shortcut_view_right);
        this.shortcutOccludedAnimController = new ShortcutOccludedAnimController(context, viewGroup, r0);
    }

    public final boolean checkOccludedRemoteAnimation(RemoteAnimationTarget[] target, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        Intrinsics.checkNotNullParameter(target, "target");
        return this.shortcutOccludedAnimController.checkOccludedRemoteAnimation(target, iRemoteAnimationFinishedCallback);
    }

    public final float dampeDistance(float f) {
        return (float) (((double) ((float) Math.atan(f / 200.0f))) / 1.5707963267948966d);
    }

    public final void destroy() {
        this.shortcutOccludedAnimController.destroy();
    }

    public final Callback getCallback() {
        return this.callback;
    }

    public final void initCommonTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        this.touchY = motionEvent.getY();
        float x = motionEvent.getX();
        this.touchX = x;
        if (actionMasked == 0) {
            this.initialTouchX = x;
            float f = this.touchY;
            this.initialTouchY = f;
            boolean zIsTouchOnIcon = isTouchOnIcon(this.rightIcon, x, f);
            this.isRightShortcutTouched = zIsTouchOnIcon;
            this.isLeftShortcutTouched = !zIsTouchOnIcon && isTouchOnIcon(this.leftIcon, this.touchX, this.touchY);
        }
    }

    public final void initDimens() {
        this.touchSlop = ViewConfiguration.get(this.context).getScaledPagingTouchSlop();
    }

    public final boolean interceptTouchEvent() {
        return !(this.isLeftShortcutTouched || this.isRightShortcutTouched || !this.shortcutOccludedAnimController.isAnyAnimPlaying()) || MiuiShortcutManager.INSTANCE.isInAppExiting();
    }

    public final boolean isOccludedAnimStartOrPlaying() {
        return this.shortcutOccludedAnimController.isOccludedAnimStartOrPlaying();
    }

    public final boolean isTouchOnIcon(View view, float f, float f2) {
        if (view != null && view.isShown() && view.getAlpha() == 1.0f) {
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            float width = view.getWidth() / 2.0f;
            if (Math.hypot(f - (iArr[0] + width), f2 - (iArr[1] + width)) <= width) {
                return true;
            }
        }
        return false;
    }

    public final boolean isValidHorizontalTouchDown(float f, float f2) {
        if (Math.abs(f) > Math.abs(f2)) {
            return true;
        }
        if (!this.isLeftShortcutTouched || f <= 0.0f) {
            return this.isRightShortcutTouched && f < 0.0f;
        }
        return true;
    }

    public final void keyguardGoingAway() {
        this.shortcutOccludedAnimController.keyguardGoingAway();
    }

    public final void onBottomIconDarkModeChanged(boolean z) {
        this.shortcutOccludedAnimController.onBottomIconDarkModeChanged(z);
    }

    public final void onBouncerShownChanged(boolean z) {
        boolean zIsInAppOpening = MiuiShortcutManager.INSTANCE.isInAppOpening();
        this.shortcutOccludedAnimController.onBouncerShownChanged(z);
        this.callback.updateCanShowBiometricAuthentication(true, zIsInAppOpening);
    }

    public final void onChargeAnimationStart() {
        this.shortcutOccludedAnimController.onChargeAnimationStart();
    }

    public final void onConfigurationChanged(Configuration newConfiguration) throws IOException {
        Intrinsics.checkNotNullParameter(newConfiguration, "newConfiguration");
        this.shortcutOccludedAnimController.onConfigurationChanged(newConfiguration);
    }

    public final void onFinishedGoingToSleep() {
        this.shortcutOccludedAnimController.onFinishedGoingToSleep();
    }

    public final void onFoldStateChanged(boolean z) {
        this.shortcutOccludedAnimController.onFoldStateChanged(z);
    }

    public final void onKeyguardOccludedChanged(boolean z) {
        if (!z) {
            Callback.DefaultImpls.updateCanShowBiometricAuthentication$default(this.callback, true, false, 2, null);
        }
        this.shortcutOccludedAnimController.onKeyguardOccludedChanged(z);
    }

    public final void onKeyguardShowingChanged(boolean z) throws IOException {
        if (z) {
            Callback.DefaultImpls.updateCanShowBiometricAuthentication$default(this.callback, true, false, 2, null);
        }
        this.shortcutOccludedAnimController.onKeyguardShowingChanged(z);
    }

    public final void onStartedWakingUp() {
        this.shortcutOccludedAnimController.onStartedWakingUp();
    }

    public final void onTouchDown() {
        this.motionCancelled = false;
        this.isLeftShortcutMoved = false;
        this.isRightShortcutMoved = false;
        this.activeMoveDistance = 0.0f;
        this.touchSlop = this.isLeftShortcutTouched ? this.touchSlop : this.touchSlop;
    }

    public final boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        int actionMasked = event.getActionMasked();
        boolean z = false;
        if (this.motionCancelled && actionMasked != 0) {
            LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutMoveController", "onTouchEvent is discarded Cancelled：true", null, 4, null);
            if (actionMasked == 1 || actionMasked == 3) {
                this.motionCancelled = false;
            }
            return false;
        }
        if (interceptTouchEvent()) {
            return true;
        }
        initCommonTouchEvent(event);
        if (!this.isRightShortcutTouched && !this.isLeftShortcutTouched) {
            return false;
        }
        if (actionMasked == 0) {
            onTouchDown();
        } else {
            if (actionMasked == 1) {
                LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutMoveController", "ACTION_UP", null, 4, null);
                onTouchUp();
                return true;
            }
            if (actionMasked == 2) {
                float f = this.touchX - this.initialTouchX;
                float f2 = this.touchY - this.initialTouchY;
                if (!this.isRightShortcutMoved && !this.isLeftShortcutMoved && !isValidHorizontalTouchDown(f, f2)) {
                    return false;
                }
                this.isRightShortcutMoved = this.isRightShortcutTouched && onTouchMove(this.touchX, this.touchY);
                if (this.isLeftShortcutTouched && onTouchMove(this.touchX, this.touchY)) {
                    z = true;
                }
                this.isLeftShortcutMoved = z;
            } else {
                if (actionMasked == 3) {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutMoveController", "ACTION_CANCEL", null, 4, null);
                    onTouchUp();
                    return true;
                }
                if (actionMasked == 5) {
                    this.motionCancelled = true;
                }
            }
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0032  */
    public final boolean onTouchMove(float f, float f2) throws IOException {
        float f3;
        float f4;
        float f5;
        boolean z = this.isLeftShortcutTouched;
        if (!z && !this.isRightShortcutTouched) {
            return false;
        }
        if (this.isLeftShortcutMoved || this.isRightShortcutMoved) {
            OccludedAnimTrace occludedAnimTrace = OccludedAnimTrace.INSTANCE;
            occludedAnimTrace.startForMove();
            occludedAnimTrace.startForShadeHideWithMove();
            if (this.isLeftShortcutTouched) {
                float f6 = this.touchSlop;
                float f7 = f - f6;
                f3 = this.initialTouchX;
                if (f7 < f3) {
                    f5 = 0.0f;
                } else {
                    f4 = f - f6;
                    f5 = f4 - f3;
                }
            } else {
                float f8 = this.touchSlop;
                float f9 = f + f8;
                f3 = this.initialTouchX;
                if (f9 > f3) {
                    f5 = 0.0f;
                } else {
                    f4 = f + f8;
                    f5 = f4 - f3;
                }
            }
            float f10 = this.touchSlop;
            float f11 = f2 + f10;
            float f12 = this.initialTouchY;
            float f13 = f11 > f12 ? 0.0f : (f2 + f10) - f12;
            double d = 2.0f;
            this.moveDistance = (float) Math.sqrt(((float) Math.pow(f5, d)) + ((float) Math.pow(f13, d)));
            if (this.shortcutOccludedAnimController.isActive()) {
                if (this.activeMoveDistance == 0.0f) {
                    this.activeMoveDistance = this.moveDistance;
                }
                float f14 = this.moveDistance;
                float f15 = this.activeMoveDistance;
                if (f14 > f15) {
                    this.moveDistance = f15 + ((f14 - f15) * (1 - dampeDistance(f14)));
                }
            } else {
                this.activeMoveDistance = 0.0f;
            }
            updateShortcutLayoutVisibility(false);
            this.shortcutOccludedAnimController.onMoveDistanceUpdate(this.moveDistance, f13);
            this.callback.onMoveDistanceUpdate(this.moveDistance);
            Callback.DefaultImpls.updateCanShowBiometricAuthentication$default(this.callback, false, false, 2, null);
        } else {
            this.shortcutOccludedAnimController.onTouchDown(f, f2, z);
        }
        return true;
    }

    public final void onTouchUp() {
        if ((this.isLeftShortcutTouched || this.isRightShortcutTouched) && MiuiShortcutManager.INSTANCE.getInteractive()) {
            this.shortcutOccludedAnimController.onTouchUp(this.touchX, this.touchY);
        }
        this.callback.updateSwipingInProgress(false);
        this.isLeftShortcutTouched = false;
        this.isRightShortcutTouched = false;
        this.isLeftShortcutMoved = false;
        this.isRightShortcutMoved = false;
        OccludedAnimTrace occludedAnimTrace = OccludedAnimTrace.INSTANCE;
        occludedAnimTrace.endTraceForMove();
        occludedAnimTrace.endForShadeHideWithMove();
    }

    public final void reset(String reason) throws IOException {
        Intrinsics.checkNotNullParameter(reason, "reason");
        this.shortcutOccludedAnimController.reset(reason);
        this.callback.onMoveDistanceUpdate(0.0f);
        this.shortcutOccludedAnimController.removeAnimShortcutView();
        updateShortcutLayoutVisibility(true);
        this.activeMoveDistance = 0.0f;
        this.isLeftShortcutTouched = false;
        this.isLeftShortcutMoved = false;
        this.isRightShortcutTouched = false;
        this.isRightShortcutMoved = false;
    }

    public final void updateShortcutLayoutVisibility(boolean z) {
        ImageView imageView;
        if (z) {
            ImageView imageView2 = this.leftIcon;
            if (imageView2 != null) {
                ViewUtils.INSTANCE.showIfDrawablePresent(imageView2);
            }
            ImageView imageView3 = this.rightIcon;
            if (imageView3 != null) {
                ViewUtils.INSTANCE.showIfDrawablePresent(imageView3);
                return;
            }
            return;
        }
        MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
        if (miuiShortcutManager.isOccludedMovedShortcutLeft()) {
            ImageView imageView4 = this.leftIcon;
            if (imageView4 != null) {
                imageView4.setVisibility(4);
                return;
            }
            return;
        }
        if (!miuiShortcutManager.isOccludedMovedShortcutRight() || (imageView = this.rightIcon) == null) {
            return;
        }
        imageView.setVisibility(4);
    }
}
