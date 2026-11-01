package com.miui.keyguard.editor.edit.color.picker;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.lockscreeninfo.model.SignatureInfo;
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
/* JADX INFO: compiled from: MagazineABAutoColorPicker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public class MagazineABAutoColorPicker extends AutoColorPicker {
    public final SignatureInfo signatureInfo;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagazineABAutoColorPicker(Context context, String clockType, ClockBean clockBean, SignatureInfo signatureInfo) {
        super(context, clockType, clockBean);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(clockType, "clockType");
        this.signatureInfo = signatureInfo;
    }

    @Override // com.miui.keyguard.editor.edit.color.picker.AutoColorPicker
    public int getClockStyle() {
        ClockBean clockBean = getClockBean();
        if (clockBean != null) {
            return clockBean.getStyle();
        }
        return -1;
    }

    public int getSignatureAlign() {
        SignatureInfo signatureInfo = this.signatureInfo;
        if (signatureInfo != null) {
            return signatureInfo.getAlignment();
        }
        return -1;
    }

    @Override // com.miui.keyguard.editor.edit.color.picker.AutoColorPicker
    public ColorData pickColor(Bitmap wallpaper) {
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        int clockStyle = getClockStyle();
        int signatureAlign = getSignatureAlign();
        String rectWhichString = getRectWhichString(RectWhich.CLOCK);
        String rectWhichString2 = getRectWhichString(RectWhich.MAGAZINE);
        boolean zIsDarkImage = isDarkImage(wallpaper, rectWhichString, signatureAlign);
        Map colorPalette = getColorPalette(wallpaper, rectWhichString, signatureAlign);
        boolean zIsDarkImage2 = isDarkImage(wallpaper, rectWhichString2, signatureAlign);
        Map colorPalette2 = getColorPalette(wallpaper, rectWhichString2, signatureAlign);
        Log.i("MagazineAAutoColorPicker", "pickColor: clockStyle=" + clockStyle + ",signatureAlign=" + signatureAlign);
        return new ColorData(0, 0, 0, 0, !zIsDarkImage, false, colorPalette, !zIsDarkImage2, colorPalette2, 0, 559, null);
    }
}
