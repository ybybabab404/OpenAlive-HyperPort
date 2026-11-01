package com.miui.keyguard.editor.utils.segment;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.utils.BitmapUtil;
import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ImageSegmentCache.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ImageSegmentCache {
    public static final Companion Companion = new Companion(null);
    public File cacheDir;
    public final Context context;

    /* JADX INFO: compiled from: ImageSegmentCache.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void clearCache(Context context) {
            if (context != null) {
                FilesKt.deleteRecursively(ImageSegmentCache.Companion.getCacheDir(context));
            }
        }

        public final File getCacheDir(Context context) {
            return new File(context.getFilesDir(), "segment");
        }
    }

    public ImageSegmentCache(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final CacheInfo get(String fileId) {
        Intrinsics.checkNotNullParameter(fileId, "fileId");
        if (this.cacheDir == null) {
            this.cacheDir = new File(this.context.getFilesDir(), "segment");
        }
        File file = this.cacheDir;
        Log.i("ImageSegmentCache", "cacheDir " + (file != null ? file.getAbsolutePath() : null));
        File file2 = this.cacheDir;
        Boolean boolValueOf = file2 != null ? Boolean.valueOf(file2.exists()) : null;
        Intrinsics.checkNotNull(boolValueOf);
        if (!boolValueOf.booleanValue()) {
            return null;
        }
        File file3 = new File(this.cacheDir, fileId);
        Log.i("ImageSegmentCache", "fileDir " + file3.getAbsolutePath());
        if (!file3.exists()) {
            return null;
        }
        return new CacheInfo(BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, new File(file3, fileId + ".webp").getAbsolutePath(), 0, 0, null, 14, null));
    }

    public final void store(Bitmap bitmap, String fileId) {
        Intrinsics.checkNotNullParameter(fileId, "fileId");
        Log.i("ImageSegmentCache", "store bitmap=" + bitmap + " ,fileId=" + fileId);
        if (this.cacheDir == null) {
            this.cacheDir = new File(this.context.getFilesDir(), "segment");
        }
        if (bitmap != null) {
            File file = new File(this.cacheDir, fileId);
            file.mkdir();
            BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
            String absolutePath = new File(file, fileId + ".webp").getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
            BitmapUtil.toWebp$default(bitmapUtil, bitmap, absolutePath, false, 4, null);
        }
    }
}
