package com.miui.keyguard.shortcuts.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.ImageView;
import com.miui.keyguard.shortcuts.manager.ShortcutWindowManager;
import com.miui.keyguard.shortcuts.utils.BlurHelper;
import com.miui.keyguard.shortcuts.utils.DeviceHelper;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.keyguard.shortcuts.utils.OccludedAnimationUtils;
import com.miui.keyguard.shortcuts.utils.RemoteAnimationTargetExtKt;
import com.miui.keyguard.shortcuts.utils.TransactionExtKt;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ValueProperty;
import org.jetbrains.annotations.NotNull;

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
/* JADX INFO: compiled from: ShortcutOccludedAnimView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SuppressLint({"ViewConstructor"})
public class ShortcutOccludedAnimView extends BaseShortcutAnimView {
    public static final Companion Companion = new Companion(null);
    public final IStateStyle fullAnimFolme;
    public boolean hasReparent;
    public RemoteAnimationTarget remoteAnimationTarget;

    /* JADX INFO: compiled from: ShortcutOccludedAnimView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutOccludedAnimView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.fullAnimFolme = Folme.useValue(OccludedAnimationUtils.INSTANCE.getTAG_FULL_ANIM());
    }

    public static final void doOccludedAnimationForShortcut$lambda$4(ShortcutOccludedAnimView shortcutOccludedAnimView, float f, float f2) {
        ImageView iconView = shortcutOccludedAnimView.getIconView();
        if (iconView != null) {
            iconView.setAlpha(f2);
        }
        shortcutOccludedAnimView.setIconCircleAlpha(f);
        shortcutOccludedAnimView.invalidate();
    }

    public final AnimState buildBackAnimStateEnd() {
        AnimState animState = new AnimState("buildBackAnimStateEnd");
        OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
        AnimState animStateAdd = animState.add((FloatProperty) occludedAnimationUtils.getTAG_BACK_ANIM_INACTIVE(), 1.0f, new long[0]).add((FloatProperty) occludedAnimationUtils.getTAG_BACK_ANIM_DISTANCE(), 0.0f, new long[0]);
        Intrinsics.checkNotNullExpressionValue(animStateAdd, "add(...)");
        return animStateAdd;
    }

    public final AnimState buildBackAnimStateStart(float f) {
        AnimState animState = new AnimState("buildBackAnimStateStart");
        OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
        AnimState animStateAdd = animState.add((FloatProperty) occludedAnimationUtils.getTAG_BACK_ANIM_INACTIVE(), 0.0f, new long[0]).add(occludedAnimationUtils.getTAG_BACK_ANIM_DISTANCE(), f, new long[0]);
        Intrinsics.checkNotNullExpressionValue(animStateAdd, "add(...)");
        return animStateAdd;
    }

    public final AnimState buildFullAnimStateEnd() {
        AnimState animState = new AnimState("buildFullAnimStateEnd");
        OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
        AnimState animStateAdd = animState.add((FloatProperty) occludedAnimationUtils.getTAG_FULL_ANIM(), 1.0f, new long[0]).add((FloatProperty) occludedAnimationUtils.getTAG_SHORTCUT_ICON_SCALE(), 1.8f, new long[0]);
        Intrinsics.checkNotNullExpressionValue(animStateAdd, "add(...)");
        return animStateAdd;
    }

    public final AnimState buildFullAnimStateStart() {
        AnimState animState = new AnimState("buildFullAnimStateStart");
        OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
        AnimState animStateAdd = animState.add((FloatProperty) occludedAnimationUtils.getTAG_FULL_ANIM(), 0.0f, new long[0]).add((FloatProperty) occludedAnimationUtils.getTAG_SHORTCUT_ICON_SCALE(), 1.0f, new long[0]);
        Intrinsics.checkNotNullExpressionValue(animStateAdd, "add(...)");
        return animStateAdd;
    }

    public final AnimState buildOccludedAnimForDefaultStateEnd() {
        AnimState animState = new AnimState("buildOccludedAnimForDefaultStateEnd");
        OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
        AnimState animStateAdd = animState.add((FloatProperty) occludedAnimationUtils.getTAG_DEFAULT_LEASH_SCALE(), 1.0f, new long[0]).add((FloatProperty) occludedAnimationUtils.getTAG_DEFAULT_LEASH_ALPHA(), 1.0f, new long[0]);
        Intrinsics.checkNotNullExpressionValue(animStateAdd, "add(...)");
        return animStateAdd;
    }

    public final AnimState buildOccludedAnimForDefaultStateStart() {
        AnimState animState = new AnimState("buildOccludedAnimForDefaultStateStart");
        OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
        AnimState animStateAdd = animState.add((FloatProperty) occludedAnimationUtils.getTAG_DEFAULT_LEASH_SCALE(), 0.9f, new long[0]).add((FloatProperty) occludedAnimationUtils.getTAG_DEFAULT_LEASH_ALPHA(), 0.0f, new long[0]);
        Intrinsics.checkNotNullExpressionValue(animStateAdd, "add(...)");
        return animStateAdd;
    }

    public final AnimState buildOccludedAnimForShortcutStateEnd() {
        AnimState animState = new AnimState("buildOccludedAnimForShortcutStateEnd");
        OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
        AnimState animStateAdd = animState.add((FloatProperty) occludedAnimationUtils.getTAG_SHORTCUT_LEASH_ALPHA(), 1.0f, new long[0]).add((FloatProperty) occludedAnimationUtils.getTAG_SHORTCUT_ICON_ALPHA(), 0.0f, new long[0]).add((FloatProperty) occludedAnimationUtils.getTAG_FAKE_ALPHA_ANIM(), 0.0f, new long[0]).add((FloatProperty) occludedAnimationUtils.getTAG_BLUR_RATIO_ANIM(), 0.0f, new long[0]);
        Intrinsics.checkNotNullExpressionValue(animStateAdd, "add(...)");
        return animStateAdd;
    }

    public final AnimState buildOccludedAnimForShortcutStateStart() {
        AnimState animState = new AnimState("buildOccludedAnimForShortcutStateStart");
        OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
        AnimState animStateAdd = animState.add((FloatProperty) occludedAnimationUtils.getTAG_SHORTCUT_LEASH_ALPHA(), 0.0f, new long[0]).add((FloatProperty) occludedAnimationUtils.getTAG_SHORTCUT_ICON_ALPHA(), 1.0f, new long[0]).add((FloatProperty) occludedAnimationUtils.getTAG_FAKE_ALPHA_ANIM(), 0.4f, new long[0]).add((FloatProperty) occludedAnimationUtils.getTAG_BLUR_RATIO_ANIM(), 1.0f, new long[0]);
        Intrinsics.checkNotNullExpressionValue(animStateAdd, "add(...)");
        return animStateAdd;
    }

    public final void cancelFullAnim() {
        this.fullAnimFolme.cancel();
    }

    public final boolean checkOccludedRemoteAnimation(RemoteAnimationTarget[] targetApps, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        Intrinsics.checkNotNullParameter(targetApps, "targetApps");
        if (targetApps.length == 0) {
            return false;
        }
        RemoteAnimationTarget remoteAnimationTarget = targetApps[0];
        this.remoteAnimationTarget = remoteAnimationTarget;
        if (remoteAnimationTarget != null) {
            setFinishedCallback(iRemoteAnimationFinishedCallback);
        }
        this.hasReparent = false;
        LogUtils logUtils = LogUtils.INSTANCE;
        RemoteAnimationTarget remoteAnimationTarget2 = this.remoteAnimationTarget;
        LogUtils.logD$default(logUtils, "ShortcutOccludedAnimView", "checkOccludedRemoteAnimation, target.package=" + (remoteAnimationTarget2 != null ? remoteAnimationTarget2.packageName : null), null, 4, null);
        return true;
    }

    public final void doOccludedAnimationEnd() {
        doRemoteAnimationEnd();
        RemoteAnimationTarget remoteAnimationTarget = this.remoteAnimationTarget;
        if (remoteAnimationTarget != null) {
            remoteAnimationTarget.leash.release();
            this.remoteAnimationTarget = null;
        }
        this.hasReparent = false;
    }

    public final void doOccludedAnimationForDefault(float f, float f2) {
        RemoteAnimationTarget remoteAnimationTarget = this.remoteAnimationTarget;
        if (remoteAnimationTarget == null || remoteAnimationTarget.leash == null) {
            return;
        }
        getRemoteAnimMatrix().setScale(f, f, getScreenWidth() / 2.0f, getScreenHeight() / 2.0f);
        SurfaceControl.Transaction transaction = getTransaction();
        SurfaceControl leash = remoteAnimationTarget.leash;
        Intrinsics.checkNotNullExpressionValue(leash, "leash");
        TransactionExtKt.setWindowCropByReflection(transaction, leash, RemoteAnimationTargetExtKt.getLocalBoundsByReflection(remoteAnimationTarget));
        SurfaceControl leash2 = remoteAnimationTarget.leash;
        Intrinsics.checkNotNullExpressionValue(leash2, "leash");
        TransactionExtKt.setMatrixByReflection(transaction, leash2, getRemoteAnimMatrix(), new float[9]);
        transaction.setAlpha(remoteAnimationTarget.leash, f2);
        SurfaceControl leash3 = remoteAnimationTarget.leash;
        Intrinsics.checkNotNullExpressionValue(leash3, "leash");
        setSurfaceControlCornerRadius(transaction, leash3);
        doSurfaceControlReparentApply(remoteAnimationTarget.leash);
    }

    public final void doOccludedAnimationForShortcut(float f, final float f2, final float f3) {
        RemoteAnimationTarget remoteAnimationTarget = this.remoteAnimationTarget;
        if (remoteAnimationTarget != null) {
            if (remoteAnimationTarget.leash == null) {
                return;
            }
            float f4 = 2;
            float iconCircleCenterX = getIconCircleCenterX() - (getIconCircleWidth() / f4);
            float iconCircleCenterY = getIconCircleCenterY() - (getIconCircleHeight() / f4);
            Matrix remoteAnimMatrix = getRemoteAnimMatrix();
            remoteAnimMatrix.setTranslate(iconCircleCenterX, iconCircleCenterY);
            remoteAnimMatrix.postScale(getIconCircleWidth() / getScreenWidth(), getIconCircleHeight() / getScreenHeight(), iconCircleCenterX, iconCircleCenterY);
            doSurfaceControlReparentApply(remoteAnimationTarget.leash);
            SurfaceControl leash = remoteAnimationTarget.leash;
            Intrinsics.checkNotNullExpressionValue(leash, "leash");
            createSyncRtSurfaceParamsAndApply(leash, RemoteAnimationTargetExtKt.getLocalBoundsByReflection(remoteAnimationTarget), f);
        }
        post(new Runnable() { // from class: com.miui.keyguard.shortcuts.view.ShortcutOccludedAnimView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ShortcutOccludedAnimView.doOccludedAnimationForShortcut$lambda$4(this.f$0, f3, f2);
            }
        });
    }

    @Override // com.miui.keyguard.shortcuts.view.BaseShortcutAnimView
    public void doSurfaceControlReparent(SurfaceControl surfaceControl, SurfaceControl parentSc) {
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        Intrinsics.checkNotNullParameter(parentSc, "parentSc");
        if (!surfaceControl.isValid() || !parentSc.isValid()) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ShortcutOccludedAnimView", "parentSc.isNotValid!!!!!", null, 4, null);
        } else {
            if (this.hasReparent) {
                return;
            }
            this.hasReparent = true;
            getTransaction().show(parentSc);
            getTransaction().reparent(surfaceControl, parentSc);
            LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutOccludedAnimView", "doSurfaceControlReparentApply success!", null, 4, null);
        }
    }

    public final IStateStyle getFullScreenAnim(final float f, final float f2, final float f3, final float f4, final float f5, final float f6, final float f7, final float f8, final float f9, final float f10, final float f11, final float f12, final Function0 function0, final Function0 function1) {
        AnimConfig animConfig = new AnimConfig();
        OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
        ValueProperty tag_full_anim = occludedAnimationUtils.getTAG_FULL_ANIM();
        AnimConfig animConfigFullAnim = occludedAnimationUtils.getAnimConfigFullAnim();
        Intrinsics.checkNotNull(animConfigFullAnim, "null cannot be cast to non-null type miuix.animation.base.AnimSpecialConfig");
        AnimConfig special = animConfig.setSpecial(tag_full_anim, (AnimSpecialConfig) animConfigFullAnim);
        ValueProperty tag_shortcut_icon_scale = occludedAnimationUtils.getTAG_SHORTCUT_ICON_SCALE();
        AnimConfig animConfigShortcutIconScale = occludedAnimationUtils.getAnimConfigShortcutIconScale();
        Intrinsics.checkNotNull(animConfigShortcutIconScale, "null cannot be cast to non-null type miuix.animation.base.AnimSpecialConfig");
        IStateStyle iStateStyle = this.fullAnimFolme.setTo(buildFullAnimStateStart()).to(buildFullAnimStateEnd(), special.setSpecial(tag_shortcut_icon_scale, (AnimSpecialConfig) animConfigShortcutIconScale).addListeners(new TransitionListener() { // from class: com.miui.keyguard.shortcuts.view.ShortcutOccludedAnimView.getFullScreenAnim.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                function0.invoke();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                function1.invoke();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                function1.invoke();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                OccludedAnimationUtils occludedAnimationUtils2 = OccludedAnimationUtils.INSTANCE;
                UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, occludedAnimationUtils2.getTAG_FULL_ANIM());
                UpdateInfo updateInfoFindBy2 = UpdateInfo.findBy(collection, occludedAnimationUtils2.getTAG_SHORTCUT_ICON_SCALE());
                float floatValue = updateInfoFindBy != null ? updateInfoFindBy.getFloatValue() : 0.0f;
                float floatValue2 = updateInfoFindBy2 != null ? updateInfoFindBy2.getFloatValue() : 1.0f;
                ShortcutOccludedAnimView shortcutOccludedAnimView = ShortcutOccludedAnimView.this;
                float f13 = f;
                shortcutOccludedAnimView.setIconCircleWidth(f13 + ((f2 - f13) * floatValue));
                ShortcutOccludedAnimView shortcutOccludedAnimView2 = ShortcutOccludedAnimView.this;
                float f14 = f3;
                shortcutOccludedAnimView2.setIconCircleHeight(f14 + ((f4 - f14) * floatValue));
                ShortcutOccludedAnimView shortcutOccludedAnimView3 = ShortcutOccludedAnimView.this;
                float f15 = f5;
                shortcutOccludedAnimView3.setIconCircleCenterX(f15 + ((f6 - f15) * floatValue));
                ShortcutOccludedAnimView shortcutOccludedAnimView4 = ShortcutOccludedAnimView.this;
                float f16 = f7;
                shortcutOccludedAnimView4.setIconCircleCenterY(f16 + ((f8 - f16) * floatValue));
                ShortcutOccludedAnimView shortcutOccludedAnimView5 = ShortcutOccludedAnimView.this;
                float f17 = f9;
                shortcutOccludedAnimView5.setIconCircleRadiusL(f17 + ((f10 - f17) * floatValue));
                ShortcutOccludedAnimView shortcutOccludedAnimView6 = ShortcutOccludedAnimView.this;
                float f18 = f11;
                shortcutOccludedAnimView6.setIconCircleRadiusR(f18 + ((f12 - f18) * floatValue));
                ShortcutOccludedAnimView.this.updateIconViewPositionAndScale(floatValue2);
                ShortcutOccludedAnimView.this.invalidate();
            }
        }));
        Intrinsics.checkNotNullExpressionValue(iStateStyle, "to(...)");
        return iStateStyle;
    }

    public final IStateStyle getFullScreenAnim(Function0 onAnimationStart, Function0 onAnimationEnd) {
        Intrinsics.checkNotNullParameter(onAnimationStart, "onAnimationStart");
        Intrinsics.checkNotNullParameter(onAnimationEnd, "onAnimationEnd");
        setFolmeAnim(getFullScreenAnim(getIconCircleWidth(), getScreenWidth(), getIconCircleHeight(), getScreenHeight(), getIconCircleCenterX(), getScreenWidth() / 2.0f, getIconCircleCenterY(), getScreenHeight() / 2.0f, getIconCircleRadiusL(), getWindowTopLeftRadius(), getIconCircleRadiusR(), getWindowTopRightRadius(), onAnimationStart, onAnimationEnd));
        IStateStyle folmeAnim = getFolmeAnim();
        Intrinsics.checkNotNull(folmeAnim);
        return folmeAnim;
    }

    @Override // com.miui.keyguard.shortcuts.view.BaseShortcutAnimView
    public void initSynSurfaceTransactionApplier() {
        if (this.remoteAnimationTarget != null) {
            super.initSynSurfaceTransactionApplier();
        }
    }

    public final boolean isNeedHideBlurWhenOccludedAnim(Context context) {
        if (BlurHelper.Companion.supportBackgroundBlur(context) || !DeviceHelper.INSTANCE.isDeviceUseBlur()) {
            return true;
        }
        RemoteAnimationTarget remoteAnimationTarget = this.remoteAnimationTarget;
        return Intrinsics.areEqual(remoteAnimationTarget != null ? remoteAnimationTarget.packageName : null, "miui.systemui.plugin");
    }

    public final void onTouchDown(boolean z) {
        updateCurrentShortcutIcon(z);
        View blurContainerView = ShortcutWindowManager.INSTANCE.getBlurContainerView();
        if (blurContainerView != null) {
            blurContainerView.setAlpha(0.0f);
        }
        setVisibility(0);
    }
}
