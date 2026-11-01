package com.miui.keyguard.editor.utils;

import android.view.View;
import android.view.ViewOutlineProvider;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.internal.AnimInfo;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Animator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TemplateRadiusAnimationListener extends TransitionListener {
    public final float[] radii;
    public final float radius;
    public final boolean revert;
    public final View view;
    public final String viewProperty;

    public TemplateRadiusAnimationListener(View view, float f, boolean z, String viewProperty) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(viewProperty, "viewProperty");
        this.view = view;
        this.radius = f;
        this.revert = z;
        this.viewProperty = viewProperty;
        this.radii = DeviceUtil.INSTANCE.screenRadii(view);
    }

    public final float[] cornerRadius(double d, double d2, double d3) {
        if (d2 == d3) {
            return this.radii;
        }
        int length = this.radii.length;
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            boolean z = this.revert;
            float f = z ? this.radii[i] : this.radius;
            fArr[i] = f + ((float) ((Math.abs(d - d2) / Math.abs(d2 - d3)) * ((double) ((z ? this.radius : this.radii[i]) - f))));
        }
        return fArr;
    }

    @Override // miuix.animation.listener.TransitionListener
    public void onUpdate(Object obj, Collection collection) {
        Object next;
        AnimInfo animInfo = null;
        if (collection != null) {
            Iterator it = collection.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((UpdateInfo) next).property.getName(), this.viewProperty));
            UpdateInfo updateInfo = (UpdateInfo) next;
            if (updateInfo != null) {
                animInfo = updateInfo.animInfo;
            }
        }
        if (animInfo != null) {
            updateViewCornerRadius(animInfo.value, animInfo.startValue, animInfo.targetValue);
        }
    }

    public final void updateViewCornerRadius(double d, double d2, double d3) {
        boolean z = true;
        if (!this.view.getClipToOutline()) {
            this.view.setClipToOutline(true);
        }
        ViewOutlineProvider outlineProvider = this.view.getOutlineProvider();
        if (!(outlineProvider instanceof RoundOutlineProvider)) {
            outlineProvider = new RoundOutlineProvider(cornerRadius(d, d2, d3));
            z = false;
        }
        ((RoundOutlineProvider) outlineProvider).setRadii(cornerRadius(d, d2, d3));
        if (z) {
            this.view.invalidateOutline();
        } else {
            this.view.setOutlineProvider(outlineProvider);
        }
    }
}
