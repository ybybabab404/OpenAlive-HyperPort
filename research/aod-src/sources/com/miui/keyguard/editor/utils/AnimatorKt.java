package com.miui.keyguard.editor.utils;

import android.util.Log;
import android.view.View;
import com.miui.keyguard.editor.R;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: Animator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class AnimatorKt {
    public static final void alphaTo(View view, float f, float f2, float f3, final Function0 function0) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Folme.useAt(view).state().setup("alphaToTargetValue").add(ViewProperty.ALPHA, f).to("alphaToTargetValue", new AnimConfig().setEase(EaseManager.getStyle(-2, f2, f3)).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.utils.AnimatorKt.alphaTo.1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                Function0 function1 = function0;
                if (function1 != null) {
                    function1.invoke();
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                Function0 function1 = function0;
                if (function1 != null) {
                    function1.invoke();
                }
            }
        }));
    }

    public static /* synthetic */ void alphaTo$default(View view, float f, float f2, float f3, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            f2 = 0.95f;
        }
        if ((i & 4) != 0) {
            f3 = 0.35f;
        }
        if ((i & 8) != 0) {
            function0 = null;
        }
        alphaTo(view, f, f2, f3, function0);
    }

    public static final void animateOutFullScreen(View templateView, float f) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        float f2 = templateView.getResources().getFloat(R.dimen.kg_template_view_scale);
        IStateStyle upVar = Folme.useAt(templateView).state().setup("exitFullScreen");
        ViewProperty viewProperty = ViewProperty.SCALE_X;
        IStateStyle iStateStyleAdd = upVar.add(viewProperty, f2).add(ViewProperty.SCALE_Y, f2);
        AnimConfig animConfig = new AnimConfig();
        String name = viewProperty.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        iStateStyleAdd.to("exitFullScreen", animConfig.addListeners(new TemplateRadiusAnimationListener(templateView, f, true, name)).setEase(EaseManager.getStyle(-2, 1.0f, 0.35f)));
    }

    public static final void dismissPopup(View target, int i, TransitionListener transitionListener) {
        Intrinsics.checkNotNullParameter(target, "target");
        Log.i("dismissPopup", " showPopup target=" + target + " bottomMargin=" + i);
        AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.38f));
        if (transitionListener != null) {
            ease.addListeners(transitionListener);
        }
        AnimState animState = new AnimState("popupWithAnimation_open_height_from");
        ViewProperty viewProperty = ViewProperty.TRANSLATION_Y;
        Folme.useAt(target).state().setTo(animState.add(viewProperty, 0, new long[0])).to(new AnimState("popupWithAnimation_open_height_to").add(viewProperty, target.getHeight() + i, new long[0]), ease);
    }

    public static final void showPopup(View target, int i, TransitionListener transitionListener) {
        Intrinsics.checkNotNullParameter(target, "target");
        Log.i("showPopup", " showPopup target=" + target + " bottomMargin=" + i);
        target.setAlpha(1.0f);
        AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.38f));
        if (transitionListener != null) {
            ease.addListeners(transitionListener);
        }
        AnimState animState = new AnimState("popupWithAnimation_open_height_from");
        ViewProperty viewProperty = ViewProperty.TRANSLATION_Y;
        Folme.useAt(target).state().setTo(animState.add(viewProperty, target.getHeight() + i, new long[0])).to(new AnimState("popupWithAnimation_open_height_to").add(viewProperty, 0.0f, new long[0]), ease);
    }
}
