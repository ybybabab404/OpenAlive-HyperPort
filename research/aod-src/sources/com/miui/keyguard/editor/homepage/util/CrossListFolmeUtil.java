package com.miui.keyguard.editor.homepage.util;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
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
/* JADX INFO: compiled from: CrossListFolmeUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CrossListFolmeUtil {
    public static final CrossListFolmeUtil INSTANCE = new CrossListFolmeUtil();

    public static /* synthetic */ void startFadeOutAlphaAnimation$default(CrossListFolmeUtil crossListFolmeUtil, View view, TransitionListener transitionListener, int i, Object obj) {
        if ((i & 2) != 0) {
            transitionListener = null;
        }
        crossListFolmeUtil.startFadeOutAlphaAnimation(view, transitionListener);
    }

    public static /* synthetic */ void startHierarchyAlphaAnimation$default(CrossListFolmeUtil crossListFolmeUtil, View view, boolean z, TransitionListener transitionListener, int i, Object obj) {
        if ((i & 4) != 0) {
            transitionListener = null;
        }
        crossListFolmeUtil.startHierarchyAlphaAnimation(view, z, transitionListener);
    }

    public final void resetViewAlpha(View view) {
        if (view == null) {
            return;
        }
        Folme.useAt(view).state().setTo(ViewProperty.ALPHA, Float.valueOf(0.0f));
    }

    public final void startFadeInAlphaAnimation(View view) {
        if (view == null) {
            return;
        }
        Folme.use(view).cancel();
        Folme.useAt(view).state().to(new AnimState("fade_in_alpha").add(ViewProperty.ALPHA, 1.0f, new long[0]), new AnimConfig().setEase(FolmeEase.cubicOut(350L)).setDelay(120L));
    }

    public final void startFadeOutAlphaAnimation(View view, TransitionListener transitionListener) {
        if (view == null) {
            return;
        }
        Folme.use(view).cancel();
        AnimState animStateAdd = new AnimState("fade_out_alpha").add(ViewProperty.ALPHA, 0.0f, new long[0]);
        AnimConfig ease = new AnimConfig().setEase(FolmeEase.spring(1.0f, 0.15f));
        if (transitionListener != null) {
            ease.addListeners(transitionListener);
        }
        Folme.useAt(view).state().to(animStateAdd, ease);
    }

    public final void startHierarchyAlphaAnimation(View target, boolean z, TransitionListener transitionListener) {
        Intrinsics.checkNotNullParameter(target, "target");
        Folme.use(target).cancel();
        float f = z ? 1.0f : 0.0f;
        EaseManager.EaseStyle easeStyleSpring = z ? FolmeEase.spring(1.0f, 0.38f) : FolmeEase.spring(1.0f, 0.2f);
        AnimState animState = new AnimState("hierarchy_alpha");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, f, new long[0]);
        AnimConfig special = new AnimConfig().setSpecial(viewProperty, easeStyleSpring, new float[0]);
        if (transitionListener != null) {
            special.addListeners(transitionListener);
        }
        Folme.useAt(target).state().to(animStateAdd, special);
    }
}
