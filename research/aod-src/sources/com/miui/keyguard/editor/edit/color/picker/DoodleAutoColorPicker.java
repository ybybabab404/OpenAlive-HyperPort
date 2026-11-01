package com.miui.keyguard.editor.edit.color.picker;

import android.content.Context;
import android.graphics.Bitmap;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.color.ColorBeanKt;
import com.miui.keyguard.editor.edit.color.ColorData;
import java.util.Map;
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
/* JADX INFO: compiled from: DoodleAutoColorPicker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DoodleAutoColorPicker extends RandomAutoColorPicker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoodleAutoColorPicker(Context context, String clockType, TemplateConfig templateConfig) {
        super(context, clockType, templateConfig);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(clockType, "clockType");
    }

    @Override // com.miui.keyguard.editor.edit.color.picker.RandomAutoColorPicker
    public ColorData pickColorRandomType(Bitmap wallpaper, int i) {
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        boolean z = !AutoColorPicker.isDarkImage$default(this, wallpaper, getRectWhichString(RectWhich.CLOCK), 0, 4, null);
        Map colorPalette$default = AutoColorPicker.getColorPalette$default(this, wallpaper, getRectWhichString(RectWhich.DEFAULT), 0, 4, null);
        if (i != 1) {
            return i != 2 ? new ColorData(ColorBeanKt.getOrDefaultColor(colorPalette$default, "secondary60"), ColorBeanKt.getOrDefaultColor(colorPalette$default, "secondary10"), ColorBeanKt.getOrDefaultColor(colorPalette$default, "primary70"), ColorBeanKt.getOrDefaultColor(colorPalette$default, "primary40"), z, false, colorPalette$default, false, null, i, 416, null) : new ColorData(ColorBeanKt.getOrDefaultColor(colorPalette$default, "primary18"), ColorBeanKt.getOrDefaultColor(colorPalette$default, "secondary60"), ColorBeanKt.getOrDefaultColor(colorPalette$default, "primary85"), ColorBeanKt.getOrDefaultColor(colorPalette$default, "primary60"), z, false, colorPalette$default, false, null, i, 416, null);
        }
        return new ColorData(ColorBeanKt.getOrDefaultColor(colorPalette$default, "primary7"), ColorBeanKt.getOrDefaultColor(colorPalette$default, "primary97"), ColorBeanKt.getOrDefaultColor(colorPalette$default, "primary70"), ColorBeanKt.getOrDefaultColor(colorPalette$default, "primary30"), z, false, colorPalette$default, false, null, i, 416, null);
    }
}
