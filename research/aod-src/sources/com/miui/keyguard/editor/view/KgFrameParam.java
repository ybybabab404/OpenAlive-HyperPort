package com.miui.keyguard.editor.view;

import android.graphics.Rect;
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
/* JADX INFO: compiled from: KgFrame.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class KgFrameParam {
    public int contentDescriptionResId;
    public int gravity;
    public int id;
    public int layoutDirection;
    public Rect rect;

    public KgFrameParam(int i, Rect rect, int i2) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        this.id = i;
        this.rect = rect;
        this.contentDescriptionResId = i2;
        this.layoutDirection = -1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KgFrameParam)) {
            return false;
        }
        KgFrameParam kgFrameParam = (KgFrameParam) obj;
        return this.id == kgFrameParam.id && Intrinsics.areEqual(this.rect, kgFrameParam.rect) && this.contentDescriptionResId == kgFrameParam.contentDescriptionResId;
    }

    public final int getContentDescriptionResId() {
        return this.contentDescriptionResId;
    }

    public final int getGravity() {
        return this.gravity;
    }

    public final int getId() {
        return this.id;
    }

    public final int getLayoutDirection() {
        return this.layoutDirection;
    }

    public final Rect getRect() {
        return this.rect;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.id) * 31) + this.rect.hashCode()) * 31) + Integer.hashCode(this.contentDescriptionResId);
    }

    public final void setLayoutDirection(int i) {
        this.layoutDirection = i;
    }

    public String toString() {
        return "KgFrameParam(id=" + this.id + ", rect=" + this.rect + ", contentDescriptionResId=" + this.contentDescriptionResId + ")";
    }
}
