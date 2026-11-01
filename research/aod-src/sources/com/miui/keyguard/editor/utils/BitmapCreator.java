package com.miui.keyguard.editor.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.graphics.Matrix;
import android.util.Log;
import com.miui.maml.folme.AnimatedProperty;
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
/* JADX INFO: compiled from: BitmapCreator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class BitmapCreator {
    public static final BitmapCreator INSTANCE = new BitmapCreator();

    public static /* synthetic */ Bitmap createBitmap$default(BitmapCreator bitmapCreator, int i, int i2, Bitmap.Config config, boolean z, boolean z2, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            z = WideColorGamutManager.INSTANCE.supportWideColor();
        }
        boolean z3 = z;
        if ((i3 & 16) != 0) {
            z2 = false;
        }
        return bitmapCreator.createBitmap(i, i2, config, z3, z2);
    }

    public static /* synthetic */ Bitmap decodeFile$default(BitmapCreator bitmapCreator, String str, BitmapFactory.Options options, int i, Object obj) {
        if ((i & 2) != 0) {
            options = null;
        }
        return bitmapCreator.decodeFile(str, options);
    }

    public final Bitmap createBitmap(int i, int i2, Bitmap.Config config, boolean z, boolean z2) {
        Bitmap bitmapCreateBitmap;
        Intrinsics.checkNotNullParameter(config, "config");
        Log.d("BitmapCreator", "createBitmap: useBt2020=" + z2 + ", setWideGamut=" + z);
        if (z2) {
            bitmapCreateBitmap = Bitmap.createBitmap(i, i2, config, true, ColorSpace.get(ColorSpace.Named.BT2020));
        } else {
            bitmapCreateBitmap = z ? Bitmap.createBitmap(i, i2, config, true, ColorSpace.get(ColorSpace.Named.DISPLAY_P3)) : Bitmap.createBitmap(i, i2, config);
        }
        Log.d("BitmapCreator", "createBitmap: colorSpace=" + (bitmapCreateBitmap != null ? bitmapCreateBitmap.getColorSpace() : null));
        Intrinsics.checkNotNull(bitmapCreateBitmap);
        return bitmapCreateBitmap;
    }

    public final Bitmap createBitmap(Bitmap source, int i, int i2, int i3, int i4) {
        Intrinsics.checkNotNullParameter(source, "source");
        return Bitmap.createBitmap(source, i, i2, i3, i4);
    }

    public final Bitmap createBitmap(Bitmap source, int i, int i2, int i3, int i4, Matrix matrix, boolean z) {
        Intrinsics.checkNotNullParameter(source, "source");
        return Bitmap.createBitmap(source, i, i2, i3, i4, matrix, z);
    }

    public final Bitmap decodeFile(String str, BitmapFactory.Options options) {
        if (options != null) {
            WideColorGamutManager.INSTANCE.trySetPreferredColorSpace(str, options);
        }
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
        Log.d("BitmapCreator", "decodeFile: config=" + (bitmapDecodeFile != null ? bitmapDecodeFile.getConfig() : null) + ", colorSpace=" + (bitmapDecodeFile != null ? bitmapDecodeFile.getColorSpace() : null) + ", filePath=" + str);
        return ensureSoftwareBitmap(bitmapDecodeFile);
    }

    public final Bitmap ensureSoftwareBitmap(Bitmap bitmap) {
        Bitmap.Config config;
        if (bitmap == null) {
            return null;
        }
        Bitmap.Config config2 = bitmap.getConfig();
        if (config2 != null && config2 != (config = Bitmap.Config.ARGB_8888) && config2 != Bitmap.Config.RGB_565) {
            Log.d("BitmapCreator", "ensureSoftwareBitmap: converting " + config2 + " bitmap to ARGB_8888, size=" + bitmap.getWidth() + AnimatedProperty.PROPERTY_NAME_X + bitmap.getHeight());
            Bitmap bitmapCopy = bitmap.copy(config, true);
            if (bitmapCopy != null) {
                bitmap.recycle();
                return bitmapCopy;
            }
            Log.e("BitmapCreator", "ensureSoftwareBitmap: copy failed, fallback to " + config2 + " bitmap");
        }
        return bitmap;
    }
}
