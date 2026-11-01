package com.miui.keyguard.editor.edit.color.picker;

import android.content.Context;
import android.graphics.Bitmap;
import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.edit.color.ColorData;
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
/* JADX INFO: compiled from: RhombusAutoColorPicker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class RhombusAutoColorPicker extends AutoColorPicker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RhombusAutoColorPicker(Context context, String clockType, ClockBean clockBean) {
        super(context, clockType, clockBean);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(clockType, "clockType");
    }

    @Override // com.miui.keyguard.editor.edit.color.picker.AutoColorPicker
    public ColorData pickColor(Bitmap wallpaper) {
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        RectWhich rectWhich = RectWhich.CLOCK;
        return new ColorData(0, 0, 0, 0, !AutoColorPicker.isDarkImage$default(this, wallpaper, getRectWhichString(rectWhich), 0, 4, null), !AutoColorPicker.isDarkImage$default(this, wallpaper, getRectWhichString(RectWhich.STATUS_BAR), 0, 4, null), AutoColorPicker.getColorPalette$default(this, wallpaper, getRectWhichString(rectWhich), 0, 4, null), false, null, 0, 911, null);
    }
}
