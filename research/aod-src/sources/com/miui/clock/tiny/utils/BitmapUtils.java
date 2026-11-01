package com.miui.clock.tiny.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
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
/* JADX INFO: compiled from: BitmapUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class BitmapUtils {
    public static final BitmapUtils INSTANCE = new BitmapUtils();
    public static final Map clockResourcesCache = new LinkedHashMap();

    public final Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            return bitmap.copy(bitmap.getConfig(), bitmap.isMutable());
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        if (intrinsicWidth <= 0) {
            intrinsicWidth = 1;
        }
        int intrinsicHeight = drawable.getIntrinsicHeight();
        return Bitmap.createBitmap(intrinsicWidth, intrinsicHeight > 0 ? intrinsicHeight : 1, Bitmap.Config.ARGB_8888);
    }

    public final Drawable getDrawable(String path, String key) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(key, "key");
        Map map = clockResourcesCache;
        WeakReference weakReference = (WeakReference) map.get(key);
        Drawable drawableCreateFromPath = weakReference != null ? (Drawable) weakReference.get() : null;
        if (drawableCreateFromPath == null) {
            drawableCreateFromPath = Drawable.createFromPath(path);
            if (drawableCreateFromPath == null) {
                Log.d("BitmapUtils", "drawable is null, path " + path);
                return null;
            }
            map.put(key, new WeakReference(drawableCreateFromPath));
        }
        return drawableCreateFromPath;
    }
}
