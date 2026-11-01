package com.miui.keyguard.editor.edit.color.picker;

import android.util.Log;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
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
/* JADX INFO: compiled from: ColorPickerCache.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ColorPickerCache implements DefaultLifecycleObserver {
    public static final ColorPickerCache INSTANCE = new ColorPickerCache();
    public static ColorData aodColorData;
    public static ColorData editableColorData;
    public static ColorData myColorData;

    private ColorPickerCache() {
    }

    public final ColorData aodColorData() {
        return aodColorData;
    }

    public final void cacheAodColorData(ColorData colorData) {
        Log.i("ColorPickerCache", "cache aodColorData");
        aodColorData = colorData;
    }

    public final void cacheEditableColorData(ColorData colorData) {
        Log.i("ColorPickerCache", "cache editableColorData");
        editableColorData = colorData;
    }

    public final void cacheMyColorData(ColorData colorData) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        Log.i("ColorPickerCache", "cache myColorData");
        myColorData = colorData;
    }

    public final void clearMyColorData() {
        Log.i("ColorPickerCache", "clear cache");
        myColorData = null;
    }

    public final ColorData editableColorData() {
        return editableColorData;
    }

    public final ColorData myColorData() {
        Log.i("ColorPickerCache", "get cache, cache is null = " + (myColorData == null));
        return myColorData;
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        super.onDestroy(owner);
        Log.i("ColorPickerCache", "onDestroy");
        myColorData = null;
        aodColorData = null;
    }
}
