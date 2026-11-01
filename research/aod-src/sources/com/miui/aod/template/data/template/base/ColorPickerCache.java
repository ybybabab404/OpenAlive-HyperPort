package com.miui.aod.template.data.template.base;

import android.util.Log;
import com.miui.keyguard.editor.edit.color.ColorData;
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
/* JADX INFO: compiled from: ColorPickerCache.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ColorPickerCache {
    public static final ColorPickerCache INSTANCE = new ColorPickerCache();
    public static ColorData myColorData;

    public final void cacheMyColorData(ColorData colorData) {
        Log.i("ColorPickerCache", "cache myColorData = " + colorData);
        myColorData = colorData;
    }

    public final ColorData myColorData() {
        Log.i("ColorPickerCache", "get cache, cache is null = " + myColorData);
        return myColorData;
    }
}
