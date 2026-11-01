package com.miui.keyguard.shortcuts.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.FolmeEase;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.property.ValueProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: OccludedAnimationUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class OccludedAnimationUtils {
    public static final ValueProperty TAG_BLUR_RATIO_ANIM;
    public static final ValueProperty TAG_DEFAULT_LEASH_ALPHA;
    public static final ValueProperty TAG_DEFAULT_LEASH_SCALE;
    public static final ValueProperty TAG_FAKE_ALPHA_ANIM;
    public static final ValueProperty TAG_FULL_ANIM;
    public static final ValueProperty TAG_SHORTCUT_ICON_ALPHA;
    public static final ValueProperty TAG_SHORTCUT_ICON_SCALE;
    public static final ValueProperty TAG_SHORTCUT_LEASH_ALPHA;
    public static final AnimConfig animConfigBackAnimDistance;
    public static final AnimConfig animConfigBackAnimInactive;
    public static final AnimConfig animConfigBlurRatio;
    public static final AnimConfig animConfigDefaultLeashAlpha;
    public static final AnimConfig animConfigDefaultLeashScale;
    public static final AnimConfig animConfigFakeAlpha;
    public static final AnimConfig animConfigFullAnim;
    public static final AnimConfig animConfigShortcutIconAlpha;
    public static final AnimConfig animConfigShortcutIconScale;
    public static final AnimConfig animConfigShortcutLeashAlpha;
    public static final AnimState animStateScaleByGoToSleep;
    public static final EaseManager.EaseStyle easeBackAnimDistance;
    public static final EaseManager.EaseStyle easeBackAnimInactive;
    public static final EaseManager.EaseStyle easeBlurRatio;
    public static final EaseManager.EaseStyle easeDefaultLeashAlpha;
    public static final EaseManager.EaseStyle easeDefaultLeashScale;
    public static final EaseManager.EaseStyle easeFakeAlpha;
    public static final EaseManager.EaseStyle easeFullAnim;
    public static final EaseManager.EaseStyle easeShortcutIconAlpha;
    public static final EaseManager.EaseStyle easeShortcutIconScale;
    public static final EaseManager.EaseStyle easeShortcutLeashAlpha;
    public static final OccludedAnimationUtils INSTANCE = new OccludedAnimationUtils();
    public static final ValueProperty TAG_BACK_ANIM_INACTIVE = new ValueProperty("TAG_BACK_ANIM_INACTIVE", 0.004f);
    public static final ValueProperty TAG_BACK_ANIM_DISTANCE = new ValueProperty("TAG_BACK_ANIM_DISTANCE", 1.0f);

    static {
        EaseManager.EaseStyle easeStyleSpring = FolmeEase.spring(0.95f, 0.35f);
        easeBackAnimInactive = easeStyleSpring;
        EaseManager.EaseStyle easeStyleSpring2 = FolmeEase.spring(0.95f, 0.35f);
        easeBackAnimDistance = easeStyleSpring2;
        AnimConfig ease = new AnimSpecialConfig().setEase(easeStyleSpring);
        Intrinsics.checkNotNullExpressionValue(ease, "setEase(...)");
        animConfigBackAnimInactive = ease;
        AnimConfig ease2 = new AnimSpecialConfig().setEase(easeStyleSpring2);
        Intrinsics.checkNotNullExpressionValue(ease2, "setEase(...)");
        animConfigBackAnimDistance = ease2;
        TAG_FULL_ANIM = new ValueProperty("TAG_FULL_ANIM", 0.00390625f);
        TAG_SHORTCUT_ICON_SCALE = new ValueProperty("TAG_SHORTCUT_ICON_SCALE", 0.004f);
        EaseManager.EaseStyle easeStyleSpring3 = FolmeEase.spring(0.95f, 0.35f);
        easeFullAnim = easeStyleSpring3;
        EaseManager.EaseStyle easeStyleSpring4 = FolmeEase.spring(1.0f, 0.18f);
        easeShortcutIconScale = easeStyleSpring4;
        AnimConfig ease3 = new AnimSpecialConfig().setEase(easeStyleSpring3);
        Intrinsics.checkNotNullExpressionValue(ease3, "setEase(...)");
        animConfigFullAnim = ease3;
        AnimConfig ease4 = new AnimSpecialConfig().setEase(easeStyleSpring4);
        Intrinsics.checkNotNullExpressionValue(ease4, "setEase(...)");
        animConfigShortcutIconScale = ease4;
        TAG_SHORTCUT_LEASH_ALPHA = new ValueProperty("TAG_OCCLUDED_ANIM_FOR_SHORTCUT-LEASH_ALPHA", 0.00390625f);
        TAG_SHORTCUT_ICON_ALPHA = new ValueProperty("TAG_OCCLUDED_ANIM_FOR_SHORTCUT-ICON_ALPHA", 0.00390625f);
        EaseManager.EaseStyle easeStyleSpring5 = FolmeEase.spring(0.95f, 0.35f);
        easeShortcutLeashAlpha = easeStyleSpring5;
        EaseManager.EaseStyle easeStyleSpring6 = FolmeEase.spring(0.95f, 0.35f);
        easeShortcutIconAlpha = easeStyleSpring6;
        AnimConfig ease5 = new AnimSpecialConfig().setEase(easeStyleSpring5);
        Intrinsics.checkNotNullExpressionValue(ease5, "setEase(...)");
        animConfigShortcutLeashAlpha = ease5;
        AnimConfig ease6 = new AnimSpecialConfig().setEase(easeStyleSpring6);
        Intrinsics.checkNotNullExpressionValue(ease6, "setEase(...)");
        animConfigShortcutIconAlpha = ease6;
        TAG_FAKE_ALPHA_ANIM = new ValueProperty("TAG_FAKE_ALPHA_ANIM", 0.00390625f);
        TAG_BLUR_RATIO_ANIM = new ValueProperty("TAG_BLUR_RATIO_ANIM", 0.00390625f);
        EaseManager.EaseStyle easeStyleSpring7 = FolmeEase.spring(1.0f, 0.3f);
        easeFakeAlpha = easeStyleSpring7;
        EaseManager.EaseStyle easeStyleCubicOut = FolmeEase.cubicOut(450L);
        easeBlurRatio = easeStyleCubicOut;
        AnimConfig ease7 = new AnimSpecialConfig().setEase(easeStyleSpring7);
        Intrinsics.checkNotNullExpressionValue(ease7, "setEase(...)");
        animConfigFakeAlpha = ease7;
        AnimConfig ease8 = new AnimSpecialConfig().setEase(easeStyleCubicOut);
        Intrinsics.checkNotNullExpressionValue(ease8, "setEase(...)");
        animConfigBlurRatio = ease8;
        TAG_DEFAULT_LEASH_SCALE = new ValueProperty("TAG_OCCLUDED_ANIM_FOR_DEFAULT-LEASH_SCALE", 0.004f);
        TAG_DEFAULT_LEASH_ALPHA = new ValueProperty("TAG_OCCLUDED_ANIM_FOR_DEFAULT-LEASH_ALPHA", 0.00390625f);
        EaseManager.EaseStyle easeStyleSpring8 = FolmeEase.spring(0.95f, 0.35f);
        easeDefaultLeashScale = easeStyleSpring8;
        EaseManager.EaseStyle easeStyleSpring9 = FolmeEase.spring(1.0f, 0.25f);
        easeDefaultLeashAlpha = easeStyleSpring9;
        AnimConfig ease9 = new AnimSpecialConfig().setEase(easeStyleSpring8);
        Intrinsics.checkNotNullExpressionValue(ease9, "setEase(...)");
        animConfigDefaultLeashScale = ease9;
        AnimConfig ease10 = new AnimSpecialConfig().setEase(easeStyleSpring9);
        Intrinsics.checkNotNullExpressionValue(ease10, "setEase(...)");
        animConfigDefaultLeashAlpha = ease10;
        animStateScaleByGoToSleep = new AnimState("TAG_ANIM_VIEW_SCALE").add(ViewProperty.SCALE_X, 0.95f, new long[0]).add(ViewProperty.SCALE_Y, 0.95f, new long[0]);
    }

    public final AnimConfig getAnimConfigBackAnimDistance() {
        return animConfigBackAnimDistance;
    }

    public final AnimConfig getAnimConfigBackAnimInactive() {
        return animConfigBackAnimInactive;
    }

    public final AnimConfig getAnimConfigBlurRatio() {
        return animConfigBlurRatio;
    }

    public final AnimConfig getAnimConfigDefaultLeashAlpha() {
        return animConfigDefaultLeashAlpha;
    }

    public final AnimConfig getAnimConfigDefaultLeashScale() {
        return animConfigDefaultLeashScale;
    }

    public final AnimConfig getAnimConfigFakeAlpha() {
        return animConfigFakeAlpha;
    }

    public final AnimConfig getAnimConfigFullAnim() {
        return animConfigFullAnim;
    }

    public final AnimConfig getAnimConfigShortcutIconAlpha() {
        return animConfigShortcutIconAlpha;
    }

    public final AnimConfig getAnimConfigShortcutIconScale() {
        return animConfigShortcutIconScale;
    }

    public final AnimConfig getAnimConfigShortcutLeashAlpha() {
        return animConfigShortcutLeashAlpha;
    }

    public final AnimState getAnimStateScaleByGoToSleep() {
        return animStateScaleByGoToSleep;
    }

    public final ValueProperty getTAG_BACK_ANIM_DISTANCE() {
        return TAG_BACK_ANIM_DISTANCE;
    }

    public final ValueProperty getTAG_BACK_ANIM_INACTIVE() {
        return TAG_BACK_ANIM_INACTIVE;
    }

    public final ValueProperty getTAG_BLUR_RATIO_ANIM() {
        return TAG_BLUR_RATIO_ANIM;
    }

    public final ValueProperty getTAG_DEFAULT_LEASH_ALPHA() {
        return TAG_DEFAULT_LEASH_ALPHA;
    }

    public final ValueProperty getTAG_DEFAULT_LEASH_SCALE() {
        return TAG_DEFAULT_LEASH_SCALE;
    }

    public final ValueProperty getTAG_FAKE_ALPHA_ANIM() {
        return TAG_FAKE_ALPHA_ANIM;
    }

    public final ValueProperty getTAG_FULL_ANIM() {
        return TAG_FULL_ANIM;
    }

    public final ValueProperty getTAG_SHORTCUT_ICON_ALPHA() {
        return TAG_SHORTCUT_ICON_ALPHA;
    }

    public final ValueProperty getTAG_SHORTCUT_ICON_SCALE() {
        return TAG_SHORTCUT_ICON_SCALE;
    }

    public final ValueProperty getTAG_SHORTCUT_LEASH_ALPHA() {
        return TAG_SHORTCUT_LEASH_ALPHA;
    }
}
