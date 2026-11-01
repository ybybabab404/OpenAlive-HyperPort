package com.miui.keyguard.editor.edit.base;

import android.view.View;
import com.miui.keyguard.editor.utils.DefaultTransitionListener;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.FloatProperty;
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
/* JADX INFO: compiled from: TemplateApplierAnimator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TemplateApplierAnimatorKt {
    public static final AnimConfig createEaseConfig(float f, float f2, Function0 function0) {
        AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, f, f2));
        if (function0 != null) {
            ease.addListeners(new DefaultTransitionListener(function0));
        }
        Intrinsics.checkNotNull(ease);
        return ease;
    }

    public static final void startAlphaAnimation(View view, Function0 function0, TransitionListener transitionListener) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        AnimConfig animConfigCreateEaseConfig = createEaseConfig(0.95f, 0.18f, function0);
        if (transitionListener != null) {
            animConfigCreateEaseConfig.addListeners(transitionListener);
        }
        Folme.useAt(view).state().setup("startAlphaAnimation").add((FloatProperty) ViewProperty.ALPHA, 0.0f).to("startAlphaAnimation", animConfigCreateEaseConfig);
    }

    public static /* synthetic */ void startAlphaAnimation$default(View view, Function0 function0, TransitionListener transitionListener, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        if ((i & 2) != 0) {
            transitionListener = null;
        }
        startAlphaAnimation(view, function0, transitionListener);
    }

    public static final void startTranslateAnimation(View view, Function0 function0) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Folme.useAt(view).state().setup("startTranslateAnimation").add((FloatProperty) ViewProperty.TRANSLATION_Y, view.getHeight()).to("startTranslateAnimation", createEaseConfig(0.95f, 0.3f, function0));
    }

    public static /* synthetic */ void startTranslateAnimation$default(View view, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        startTranslateAnimation(view, function0);
    }
}
