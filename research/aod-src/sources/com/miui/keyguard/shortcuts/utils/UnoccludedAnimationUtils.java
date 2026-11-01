package com.miui.keyguard.shortcuts.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.FolmeEase;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.property.ValueProperty;
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
/* JADX INFO: compiled from: UnoccludedAnimationUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class UnoccludedAnimationUtils {
    public static final ValueProperty TAG_DEFAULT_LEASH_ALPHA;
    public static final ValueProperty TAG_DEFAULT_LEASH_SCALE;
    public static final ValueProperty TAG_LEASH_ALPHA;
    public static final AnimConfig animConfigDefaultLeashAlpha;
    public static final AnimConfig animConfigDefaultLeashScale;
    public static final AnimConfig animConfigShortcutIconAlpha;
    public static final AnimConfig animConfigShortcutIconCircleAlpha;
    public static final AnimConfig animConfigShortcutIconScale;
    public static final AnimConfig animConfigShortcutLeashAlpha;
    public static final AnimConfig animConfigShortcutLeashHeight;
    public static final AnimConfig animConfigShortcutLeashWidth;
    public static final AnimConfig animConfigShortcutLeashX;
    public static final AnimConfig animConfigShortcutLeashY;
    public static final EaseManager.EaseStyle easeDefaultLeashAlpha;
    public static final EaseManager.EaseStyle easeDefaultLeashScale;
    public static final EaseManager.EaseStyle easeShortcutIconAlpha;
    public static final EaseManager.EaseStyle easeShortcutIconCircleAlpha;
    public static final EaseManager.EaseStyle easeShortcutIconScale;
    public static final EaseManager.EaseStyle easeShortcutLeashAlpha;
    public static final EaseManager.EaseStyle easeShortcutLeashHeight;
    public static final EaseManager.EaseStyle easeShortcutLeashWidth;
    public static final EaseManager.EaseStyle easeShortcutLeashX;
    public static final EaseManager.EaseStyle easeShortcutLeashY;
    public static final EaseManager.EaseStyle leashAlphaAsyncEase;
    public static final EaseManager.EaseStyle leashAlphaAsyncEaseNoHigh;
    public static final EaseManager.EaseStyle leashAlphaHideEase;
    public static final UnoccludedAnimationUtils INSTANCE = new UnoccludedAnimationUtils();
    public static final ValueProperty TAG_SHORTCUT_LEASH_WIDTH = new ValueProperty("TAG_UNOCCLUDED_ANIM_SHORTCUT-LEASH_WIDTH", 1.0f);
    public static final ValueProperty TAG_SHORTCUT_LEASH_HEIGHT = new ValueProperty("TAG_UNOCCLUDED_ANIM_SHORTCUT-LEASH_HEIGHT", 1.0f);
    public static final ValueProperty TAG_SHORTCUT_LEASH_X = new ValueProperty("TAG_UNOCCLUDED_ANIM_SHORTCUT-LEASH_X", 1.0f);
    public static final ValueProperty TAG_SHORTCUT_LEASH_Y = new ValueProperty("TAG_UNOCCLUDED_ANIM_SHORTCUT-LEASH_Y", 1.0f);
    public static final ValueProperty TAG_SHORTCUT_LEASH_ALPHA = new ValueProperty("TAG_UNOCCLUDED_ANIM_SHORTCUT-LEASH_ALPHA", 0.00390625f);
    public static final ValueProperty TAG_SHORTCUT_ICON_ALPHA = new ValueProperty("TAG_UNOCCLUDED_ANIM_SHORTCUT-ICON_ALPHA", 0.00390625f);
    public static final ValueProperty TAG_SHORTCUT_ICON_SCALE = new ValueProperty("TAG_UNOCCLUDED_ANIM_SHORTCUT-ICON_SCALE", 0.004f);
    public static final ValueProperty TAG_SHORTCUT_ICON_CIRCLE_ALPHA = new ValueProperty("TAG_UNOCCLUDED_ANIM_SHORTCUT-ICON_CIRCLE_ALPHA", 0.00390625f);

    static {
        EaseManager.EaseStyle easeStyleSpring = FolmeEase.spring(0.9f, 0.4f);
        easeShortcutLeashWidth = easeStyleSpring;
        EaseManager.EaseStyle easeStyleSpring2 = FolmeEase.spring(0.9f, 0.2f);
        easeShortcutLeashHeight = easeStyleSpring2;
        EaseManager.EaseStyle easeStyleSpring3 = FolmeEase.spring(1.0f, 0.45f);
        easeShortcutLeashX = easeStyleSpring3;
        EaseManager.EaseStyle easeStyleSpring4 = FolmeEase.spring(1.0f, 0.35f);
        easeShortcutLeashY = easeStyleSpring4;
        EaseManager.EaseStyle easeStyleSpring5 = FolmeEase.spring(1.0f, 0.2f);
        easeShortcutLeashAlpha = easeStyleSpring5;
        EaseManager.EaseStyle easeStyleSpring6 = FolmeEase.spring(1.0f, 0.2f);
        easeShortcutIconScale = easeStyleSpring6;
        EaseManager.EaseStyle easeStyleSpring7 = FolmeEase.spring(1.0f, 0.2f);
        easeShortcutIconAlpha = easeStyleSpring7;
        EaseManager.EaseStyle easeStyleSpring8 = FolmeEase.spring(1.0f, 0.2f);
        easeShortcutIconCircleAlpha = easeStyleSpring8;
        AnimConfig ease = new AnimSpecialConfig().setEase(easeStyleSpring);
        Intrinsics.checkNotNullExpressionValue(ease, "setEase(...)");
        animConfigShortcutLeashWidth = ease;
        AnimConfig ease2 = new AnimSpecialConfig().setEase(easeStyleSpring2);
        Intrinsics.checkNotNullExpressionValue(ease2, "setEase(...)");
        animConfigShortcutLeashHeight = ease2;
        AnimConfig delay = new AnimSpecialConfig().setEase(easeStyleSpring3).setDelay(80L);
        Intrinsics.checkNotNullExpressionValue(delay, "setDelay(...)");
        animConfigShortcutLeashX = delay;
        AnimConfig ease3 = new AnimSpecialConfig().setEase(easeStyleSpring4);
        Intrinsics.checkNotNullExpressionValue(ease3, "setEase(...)");
        animConfigShortcutLeashY = ease3;
        AnimConfig delay2 = new AnimSpecialConfig().setEase(easeStyleSpring5).setDelay(50L);
        Intrinsics.checkNotNullExpressionValue(delay2, "setDelay(...)");
        animConfigShortcutLeashAlpha = delay2;
        AnimConfig ease4 = new AnimSpecialConfig().setEase(easeStyleSpring6);
        Intrinsics.checkNotNullExpressionValue(ease4, "setEase(...)");
        animConfigShortcutIconScale = ease4;
        AnimConfig delay3 = new AnimSpecialConfig().setEase(easeStyleSpring7).setDelay(100L);
        Intrinsics.checkNotNullExpressionValue(delay3, "setDelay(...)");
        animConfigShortcutIconAlpha = delay3;
        AnimConfig delay4 = new AnimSpecialConfig().setEase(easeStyleSpring8).setDelay(100L);
        Intrinsics.checkNotNullExpressionValue(delay4, "setDelay(...)");
        animConfigShortcutIconCircleAlpha = delay4;
        TAG_DEFAULT_LEASH_ALPHA = new ValueProperty("TAG_UNOCCLUDED_ANIM_DEFAULT-LEASH_ALPHA", 0.00390625f);
        TAG_DEFAULT_LEASH_SCALE = new ValueProperty("TAG_UNOCCLUDED_ANIM_DEFAULT-LEASH_SCALE", 0.004f);
        EaseManager.EaseStyle easeStyleSpring9 = FolmeEase.spring(0.95f, 0.2f);
        easeDefaultLeashAlpha = easeStyleSpring9;
        EaseManager.EaseStyle easeStyleSpring10 = FolmeEase.spring(0.95f, 0.35f);
        easeDefaultLeashScale = easeStyleSpring10;
        AnimConfig ease5 = new AnimSpecialConfig().setEase(easeStyleSpring9);
        Intrinsics.checkNotNullExpressionValue(ease5, "setEase(...)");
        animConfigDefaultLeashAlpha = ease5;
        AnimConfig ease6 = new AnimSpecialConfig().setEase(easeStyleSpring10);
        Intrinsics.checkNotNullExpressionValue(ease6, "setEase(...)");
        animConfigDefaultLeashScale = ease6;
        EaseManager.EaseStyle easeStyleSpring11 = FolmeEase.spring(1.0f, 0.33f);
        Intrinsics.checkNotNullExpressionValue(easeStyleSpring11, "spring(...)");
        leashAlphaAsyncEase = easeStyleSpring11;
        EaseManager.EaseStyle easeStyleSpring12 = FolmeEase.spring(1.0f, 0.25f);
        Intrinsics.checkNotNullExpressionValue(easeStyleSpring12, "spring(...)");
        leashAlphaAsyncEaseNoHigh = easeStyleSpring12;
        TAG_LEASH_ALPHA = new ValueProperty("TAG_UNOCCLUDED_ALPHA_HIDE-LEASH_ALPHA", 0.00390625f);
        leashAlphaHideEase = FolmeEase.spring(0.95f, 0.2f);
    }

    public final EaseManager.EaseStyle getLeashAlphaAsyncEase() {
        return DeviceHelper.INSTANCE.isCpuAndGpuHighLevel() ? leashAlphaAsyncEase : leashAlphaAsyncEaseNoHigh;
    }

    public final EaseManager.EaseStyle getLeashAlphaHideEase() {
        return leashAlphaHideEase;
    }

    public final ValueProperty getTAG_DEFAULT_LEASH_ALPHA() {
        return TAG_DEFAULT_LEASH_ALPHA;
    }

    public final ValueProperty getTAG_DEFAULT_LEASH_SCALE() {
        return TAG_DEFAULT_LEASH_SCALE;
    }

    public final ValueProperty getTAG_LEASH_ALPHA() {
        return TAG_LEASH_ALPHA;
    }

    public final ValueProperty getTAG_SHORTCUT_ICON_ALPHA() {
        return TAG_SHORTCUT_ICON_ALPHA;
    }

    public final ValueProperty getTAG_SHORTCUT_ICON_CIRCLE_ALPHA() {
        return TAG_SHORTCUT_ICON_CIRCLE_ALPHA;
    }

    public final ValueProperty getTAG_SHORTCUT_ICON_SCALE() {
        return TAG_SHORTCUT_ICON_SCALE;
    }

    public final ValueProperty getTAG_SHORTCUT_LEASH_ALPHA() {
        return TAG_SHORTCUT_LEASH_ALPHA;
    }

    public final ValueProperty getTAG_SHORTCUT_LEASH_HEIGHT() {
        return TAG_SHORTCUT_LEASH_HEIGHT;
    }

    public final ValueProperty getTAG_SHORTCUT_LEASH_WIDTH() {
        return TAG_SHORTCUT_LEASH_WIDTH;
    }

    public final ValueProperty getTAG_SHORTCUT_LEASH_X() {
        return TAG_SHORTCUT_LEASH_X;
    }

    public final ValueProperty getTAG_SHORTCUT_LEASH_Y() {
        return TAG_SHORTCUT_LEASH_Y;
    }
}
