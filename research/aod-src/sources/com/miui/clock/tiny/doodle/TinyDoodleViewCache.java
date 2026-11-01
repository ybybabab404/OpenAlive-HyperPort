package com.miui.clock.tiny.doodle;

import android.graphics.drawable.Drawable;
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
/* JADX INFO: compiled from: TinyDoodleViewCache.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class TinyDoodleViewCache {
    public Drawable bgDrawable0;
    public Drawable bgDrawable2;
    public Drawable filament0;
    public Drawable filament2;
    public boolean isInit;
    public Drawable textBgCn0;
    public Drawable textBgCn2;
    public Drawable textBgEn0;
    public Drawable textBgEn2;

    public final Drawable getBgDrawable(int i) {
        return i != 2 ? this.bgDrawable0 : this.bgDrawable2;
    }

    public final Drawable getFilament(int i) {
        return i != 2 ? this.filament0 : this.filament2;
    }

    public final Drawable getTextBg(int i, int i2) {
        if (i2 == 1) {
            return i != 2 ? this.textBgCn0 : this.textBgCn2;
        }
        return i != 2 ? this.textBgEn0 : this.textBgEn2;
    }

    public final boolean isInit() {
        return this.isInit;
    }
}
