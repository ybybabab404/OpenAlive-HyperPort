package com.miui.keyguard.editor.view;

import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: RoundOutlineProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class RoundOutlineProvider extends ViewOutlineProvider {
    public boolean isRect;
    public final Path path;
    public float[] radii;
    public final Rect selfRect;

    public RoundOutlineProvider(float f) {
        this.radii = new float[8];
        this.path = new Path();
        this.selfRect = new Rect();
        this.isRect = true;
        setRadius(f);
    }

    public RoundOutlineProvider(float[] floatArray) {
        Intrinsics.checkNotNullParameter(floatArray, "floatArray");
        this.radii = new float[8];
        this.path = new Path();
        this.selfRect = new Rect();
        this.radii = floatArray;
    }

    @Override // android.view.ViewOutlineProvider
    public void getOutline(View view, Outline outline) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(outline, "outline");
        if (this.isRect) {
            this.selfRect.set(0, 0, view.getWidth(), view.getHeight());
            outline.setRoundRect(this.selfRect, getRadius());
        } else {
            this.path.reset();
            this.path.addRoundRect(0.0f, 0.0f, view.getWidth(), view.getHeight(), this.radii, Path.Direction.CW);
            outline.setPath(this.path);
        }
        view.setClipToOutline(true);
    }

    public final float getRadius() {
        return this.radii[0];
    }

    public final void setRadii(float[] radii) {
        Intrinsics.checkNotNullParameter(radii, "radii");
        float[] fArrCopyOf = Arrays.copyOf(radii, radii.length);
        Intrinsics.checkNotNullExpressionValue(fArrCopyOf, "copyOf(...)");
        this.radii = fArrCopyOf;
        this.isRect = false;
    }

    public final void setRadius(float f) {
        int length = this.radii.length;
        for (int i = 0; i < length; i++) {
            this.radii[i] = f;
        }
        this.isRect = true;
    }
}
