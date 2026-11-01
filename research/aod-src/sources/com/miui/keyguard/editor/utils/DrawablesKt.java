package com.miui.keyguard.editor.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Drawables.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DrawablesKt {
    public static final Drawable createRoundDrawable(float f, int i) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(i);
        gradientDrawable.setCornerRadius(f);
        if (f > 0.0f) {
            try {
                gradientDrawable.getClass().getMethod("setSmoothCornerEnabled", Boolean.TYPE).invoke(gradientDrawable, Boolean.TRUE);
                return gradientDrawable;
            } catch (Exception e) {
                Log.e("Keyguard-Editor-", "createRoundDrawable : " + e.getMessage());
            }
        }
        return gradientDrawable;
    }

    public static final Drawable createTopRoundDrawable(float f, int i) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(i);
        gradientDrawable.setCornerRadii(new float[]{f, f, f, f, 0.0f, 0.0f, 0.0f, 0.0f});
        if (f > 0.0f) {
            try {
                gradientDrawable.getClass().getMethod("setSmoothCornerEnabled", Boolean.TYPE).invoke(gradientDrawable, Boolean.TRUE);
                return gradientDrawable;
            } catch (Exception e) {
                Log.e("Keyguard-Editor-", "createTopRoundDrawable : " + e.getMessage());
            }
        }
        return gradientDrawable;
    }
}
