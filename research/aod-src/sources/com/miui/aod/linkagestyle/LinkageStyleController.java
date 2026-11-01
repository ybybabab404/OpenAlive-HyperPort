package com.miui.aod.linkagestyle;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.miui.aod.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
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
/* JADX INFO: compiled from: LinkageStyleController.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class LinkageStyleController {
    public static final LinkageStyleController INSTANCE = new LinkageStyleController();
    public static final EaseManager.EaseStyle aodRhombusTransEase;
    public static final EaseManager.EaseStyle aodTransEase;

    static {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 1.0f, 0.43f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        aodRhombusTransEase = style;
        EaseManager.EaseStyle style2 = EaseManager.getStyle(-2, 0.98f, 0.52f);
        Intrinsics.checkNotNullExpressionValue(style2, "getStyle(...)");
        aodTransEase = style2;
    }

    public static /* synthetic */ void aodLinkageAlphaDelayAnimation$default(LinkageStyleController linkageStyleController, View view, AnimatorListenerAdapter animatorListenerAdapter, long j, int i, Object obj) {
        if ((i & 4) != 0) {
            j = 100;
        }
        linkageStyleController.aodLinkageAlphaDelayAnimation(view, animatorListenerAdapter, j);
    }

    public final void aodLinkageAlphaDelayAnimation(View view, AnimatorListenerAdapter animatorListenerAdapter) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(animatorListenerAdapter, "animatorListenerAdapter");
        aodLinkageAlphaDelayAnimation$default(this, view, animatorListenerAdapter, 0L, 4, null);
    }

    public final void aodLinkageAlphaDelayAnimation(View view, AnimatorListenerAdapter animatorListenerAdapter, long j) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(animatorListenerAdapter, "animatorListenerAdapter");
        view.setAlpha(0.0f);
        view.animate().alpha(1.0f).setStartDelay(j).setDuration(400L).setInterpolator(new DecelerateInterpolator()).setListener(animatorListenerAdapter).start();
    }

    public final void aodLinkageTransAnimation(final View view, float f, float f2, AnimatorListenerAdapter animatorListenerAdapter, boolean z) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(animatorListenerAdapter, "animatorListenerAdapter");
        AnimConfig animConfigAddListeners = new AnimConfig().setEase(aodTransEase).addListeners(new TransitionListener() { // from class: com.miui.aod.linkagestyle.LinkageStyleController$aodLinkageTransAnimation$translateConfig$1
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
        aodLinkageAlphaDelayAnimation$default(this, view, animatorListenerAdapter, 0L, 4, null);
    }

    public final void aodRhombusTransAnimation(final View view, float f, float f2, AnimatorListenerAdapter animatorListenerAdapter, boolean z) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(animatorListenerAdapter, "animatorListenerAdapter");
        AnimConfig delay = new AnimConfig().setEase(aodRhombusTransEase).addListeners(new TransitionListener() { // from class: com.miui.aod.linkagestyle.LinkageStyleController$aodRhombusTransAnimation$translateConfig$1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                view.setTranslationY(0.0f);
            }
        }).setDelay(100L);
        AnimState animState = new AnimState("transition_from");
        ViewProperty viewProperty = ViewProperty.TRANSLATION_Y;
        AnimState animStateAdd = animState.add(viewProperty, f, new long[0]);
        AnimState animStateAdd2 = new AnimState("transition_to").add(viewProperty, f2, new long[0]);
        if (z) {
            Folme.useAt(view).state().setTo(animStateAdd).to(animStateAdd2, delay);
        }
        aodLinkageAlphaDelayAnimation$default(this, view, animatorListenerAdapter, 0L, 4, null);
    }

    public final int getStartY(Context context, String clockTemplateId, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(clockTemplateId, "clockTemplateId");
        if (StringsKt.contains$default((CharSequence) clockTemplateId, (CharSequence) "classic", false, 2, (Object) null)) {
            return context.getResources().getDimensionPixelSize(R.dimen.linkage_start_classic);
        }
        if (Intrinsics.areEqual(clockTemplateId, "magazine_a")) {
            return context.getResources().getDimensionPixelSize(R.dimen.linkage_start_magazineA);
        }
        if (Intrinsics.areEqual(clockTemplateId, "magazine_b")) {
            return context.getResources().getDimensionPixelSize(R.dimen.linkage_start_magazineB);
        }
        if (Intrinsics.areEqual(clockTemplateId, "magazine_c") && i == 5) {
            return context.getResources().getDimensionPixelSize(R.dimen.linkage_start_magazineC1);
        }
        return 0;
    }
}
