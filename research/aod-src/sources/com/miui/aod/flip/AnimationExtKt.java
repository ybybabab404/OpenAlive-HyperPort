package com.miui.aod.flip;

import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.miui.aod.category.FlipLinkageClockCategoryInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
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
/* JADX INFO: compiled from: AnimationExt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AnimationExtKt {
    public static final EaseManager.EaseStyle aodTransEase;

    static {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 0.98f, 0.52f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        aodTransEase = style;
    }

    public static final void alphaDelayAnimation(View view, AnimatorListenerAdapter animatorListenerAdapter, boolean z) {
        view.setAlpha(0.0f);
        view.animate().alpha(1.0f).setStartDelay(z ? 100L : 0L).setDuration(400L).setInterpolator(new DecelerateInterpolator()).setListener(animatorListenerAdapter).start();
    }

    public static /* synthetic */ void alphaDelayAnimation$default(View view, AnimatorListenerAdapter animatorListenerAdapter, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        alphaDelayAnimation(view, animatorListenerAdapter, z);
    }

    public static final void alphaDelayAnimationForPets(View view, AnimatorListenerAdapter animatorListenerAdapter) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(animatorListenerAdapter, "animatorListenerAdapter");
        view.setAlpha(0.0f);
        view.animate().alpha(1.0f).setStartDelay(150L).setDuration(450L).setInterpolator(new DecelerateInterpolator()).setListener(animatorListenerAdapter).start();
    }

    public static final void animate(View container, FlipLinkageClockCategoryInfo info, AnimatorListenerAdapter animatorListenerAdapter) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(animatorListenerAdapter, "animatorListenerAdapter");
        transAnimation(container, 300.0f, 0.0f, animatorListenerAdapter, true);
    }

    public static final void transAnimation(final View view, float f, float f2, AnimatorListenerAdapter animatorListenerAdapter, boolean z) {
        AnimConfig animConfigAddListeners = new AnimConfig().setEase(aodTransEase).addListeners(new TransitionListener() { // from class: com.miui.aod.flip.AnimationExtKt$transAnimation$translateConfig$1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                view.setTranslationY(0.0f);
            }
        });
        Intrinsics.checkNotNullExpressionValue(animConfigAddListeners, "addListeners(...)");
        AnimState animState = new AnimState("transition_from");
        ViewProperty viewProperty = ViewProperty.TRANSLATION_Y;
        AnimState animStateAdd = animState.add(viewProperty, f, new long[0]);
        AnimState animStateAdd2 = new AnimState("transition_to").add(viewProperty, f2, new long[0]);
        if (z) {
            Folme.useAt(view).state().setTo(animStateAdd).to(animStateAdd2, animConfigAddListeners);
        }
        alphaDelayAnimation$default(view, animatorListenerAdapter, false, 4, null);
    }
}
