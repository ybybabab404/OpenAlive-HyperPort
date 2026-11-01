package com.miui.aod.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import java.io.FileOutputStream;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
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
/* JADX INFO: compiled from: BitmapFactoryUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class BitmapFactoryUtils {
    public static final BitmapFactoryUtils INSTANCE = new BitmapFactoryUtils();

    public final int getGifDuration(GifDrawable gifDrawable) {
        Object fieldFromSuperClass;
        Intrinsics.checkNotNullParameter(gifDrawable, "gifDrawable");
        Drawable.ConstantState constantState = gifDrawable.getConstantState();
        if (constantState != null && (fieldFromSuperClass = ReflectUtil.getFieldFromSuperClass(constantState, "frameLoader")) != null) {
            Object fieldFromSuperClass2 = ReflectUtil.getFieldFromSuperClass(fieldFromSuperClass, "gifDecoder");
            if (fieldFromSuperClass2 instanceof GifDecoder) {
                int frameCount = gifDrawable.getFrameCount();
                int delay = 0;
                for (int i = 0; i < frameCount; i++) {
                    delay += ((GifDecoder) fieldFromSuperClass2).getDelay(i);
                }
                return delay;
            }
        }
        return 0;
    }

    public final boolean saveToFile(Bitmap bitmap, String str, boolean z) throws IOException {
        if (bitmap == null) {
            return false;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        try {
            boolean zCompress = bitmap.compress(z ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            CloseableKt.closeFinally(fileOutputStream, null);
            return zCompress;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(fileOutputStream, th);
                throw th2;
            }
        }
    }
}
