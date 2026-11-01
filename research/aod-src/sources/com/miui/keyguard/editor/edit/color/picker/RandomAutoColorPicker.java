package com.miui.keyguard.editor.edit.color.picker;

import android.content.Context;
import android.graphics.Bitmap;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.RandomColorUtil;
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
/* JADX INFO: compiled from: RandomAutoColorPicker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class RandomAutoColorPicker extends AutoColorPicker {
    public final TemplateConfig config;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RandomAutoColorPicker(Context context, String clockType, TemplateConfig templateConfig) {
        super(context, clockType, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(clockType, "clockType");
        this.config = templateConfig;
    }

    @Override // com.miui.keyguard.editor.edit.color.picker.AutoColorPicker
    public final ColorData pickColor(Bitmap wallpaper) {
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        TemplateConfig templateConfig = this.config;
        return pickColorRandomType(wallpaper, templateConfig != null ? templateConfig.getRandomColorType() : RandomColorUtil.INSTANCE.getCurrentRandomColorType(getContext()));
    }

    public abstract ColorData pickColorRandomType(Bitmap bitmap, int i);
}
