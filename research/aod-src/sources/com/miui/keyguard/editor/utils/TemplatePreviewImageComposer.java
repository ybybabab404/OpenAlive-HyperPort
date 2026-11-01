package com.miui.keyguard.editor.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import com.miui.maml.folme.AnimatedProperty;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TemplatePreviewImageComposer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TemplatePreviewImageComposer {
    public static final TemplatePreviewImageComposer INSTANCE = new TemplatePreviewImageComposer();
    public static final float[] CONVERT_BLACK_TO_TRANSPARENT_PIXEL_MATRIX_VALUE = {1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f};
    public static final float[] CONVERT_TO_BLACK_AND_WHITE_PIXEL_MATRIX_VALUE = {0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    public static /* synthetic */ Bitmap composeSubjectImage$default(TemplatePreviewImageComposer templatePreviewImageComposer, Bitmap bitmap, Bitmap bitmap2, int i, int i2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            i = bitmap.getWidth();
        }
        if ((i3 & 8) != 0) {
            i2 = bitmap.getHeight();
        }
        return templatePreviewImageComposer.composeSubjectImage(bitmap, bitmap2, i, i2);
    }

    public static /* synthetic */ Bitmap composeSubjectImage$default(TemplatePreviewImageComposer templatePreviewImageComposer, WallpaperInfo wallpaperInfo, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return templatePreviewImageComposer.composeSubjectImage(wallpaperInfo, z);
    }

    public static /* synthetic */ Bitmap convertSubjectMask$default(TemplatePreviewImageComposer templatePreviewImageComposer, Bitmap bitmap, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return templatePreviewImageComposer.convertSubjectMask(bitmap, z);
    }

    public final Bitmap composeSubjectImage(Bitmap wallpaper, Bitmap bitmap, int i, int i2) {
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        Log.i("Keyguard-Editor-SubjectHelper", "composeSubjectImage begin wallpaper=" + wallpaper.getWidth() + AnimatedProperty.PROPERTY_NAME_X + wallpaper.getHeight() + " mask=" + (bitmap != null ? Integer.valueOf(bitmap.getWidth()) : null) + AnimatedProperty.PROPERTY_NAME_X + (bitmap != null ? Integer.valueOf(bitmap.getHeight()) : null) + " target=" + i + AnimatedProperty.PROPERTY_NAME_X + i2);
        if (bitmap == null) {
            Log.i("Keyguard-Editor-SubjectHelper", "composeSubjectImage: mask invalid !");
            return null;
        }
        EditorApplicationProxy.Companion companion = EditorApplicationProxy.Companion;
        if (companion.getEDITOR_DEBUG()) {
            Trace.beginSection("composeSubjectImage");
        }
        Bitmap bitmapCreateBitmap$default = BitmapCreator.createBitmap$default(BitmapCreator.INSTANCE, i, i2, Bitmap.Config.ARGB_8888, WideColorGamutManager.INSTANCE.isWideColorBitmap(wallpaper), false, 16, null);
        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
        Matrix bitmapMatrix$default = BitmapUtil.getBitmapMatrix$default(bitmapUtil, wallpaper.getWidth(), wallpaper.getHeight(), i, i2, null, 16, null);
        if (bitmapMatrix$default == null) {
            bitmapMatrix$default = new Matrix();
        }
        Matrix bitmapMatrix = bitmapUtil.getBitmapMatrix(bitmap.getWidth(), bitmap.getHeight(), wallpaper.getWidth(), wallpaper.getHeight(), ImageView.ScaleType.FIT_XY);
        if (bitmapMatrix == null) {
            bitmapMatrix = new Matrix();
        }
        bitmapMatrix.postConcat(bitmapMatrix$default);
        Canvas canvas = new Canvas(bitmapCreateBitmap$default);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(CONVERT_BLACK_TO_TRANSPARENT_PIXEL_MATRIX_VALUE)));
        Paint paint2 = new Paint();
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, bitmapMatrix, paint);
        canvas.drawBitmap(wallpaper, bitmapMatrix$default, paint2);
        Log.i("Keyguard-Editor-SubjectHelper", "composeSubjectImage end");
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
        }
        return bitmapCreateBitmap$default;
    }

    public final Bitmap composeSubjectImage(WallpaperInfo wallpaperInfo, boolean z) {
        Intrinsics.checkNotNullParameter(wallpaperInfo, "wallpaperInfo");
        String source = wallpaperInfo.getSource();
        Intrinsics.checkNotNull(source);
        return composeSubjectImage(source, wallpaperInfo.getMask(), z);
    }

    public final Bitmap composeSubjectImage(String sourcePath, String str, boolean z) {
        Intrinsics.checkNotNullParameter(sourcePath, "sourcePath");
        if (TextUtils.isEmpty(str)) {
            Log.d("Keyguard-Editor-SubjectHelper", "composeSubjectImage: maskPath is null");
            return null;
        }
        Bitmap templateBitmap$default = TemplateDataUtil.getTemplateBitmap$default(sourcePath, z, 0, 0, true, 12, null);
        Bitmap templateBitmap$default2 = TemplateDataUtil.getTemplateBitmap$default(str, z, 0, 0, true, 12, null);
        if (templateBitmap$default == null) {
            return null;
        }
        Bitmap bitmapResizeToPreviewLength = resizeToPreviewLength(templateBitmap$default);
        if (bitmapResizeToPreviewLength != templateBitmap$default) {
            templateBitmap$default.recycle();
        }
        return composeSubjectImage(bitmapResizeToPreviewLength, templateBitmap$default2, bitmapResizeToPreviewLength.getWidth(), bitmapResizeToPreviewLength.getHeight());
    }

    public final Bitmap composeTemplatePreviewImage(Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, int i, int i2) {
        Bitmap bitmapComposeSubjectImage;
        EditorApplicationProxy.Companion companion = EditorApplicationProxy.Companion;
        if (companion.getEDITOR_DEBUG()) {
            Trace.beginSection("composeTemplatePreviewImage");
        }
        Rect rect = new Rect(0, 0, i, i2);
        Bitmap bitmapCreateBitmap$default = BitmapCreator.createBitmap$default(BitmapCreator.INSTANCE, i, i2, Bitmap.Config.ARGB_8888, false, false, 24, null);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmapCreateBitmap$default);
        Matrix bitmapMatrix$default = BitmapUtil.getBitmapMatrix$default(BitmapUtil.INSTANCE, bitmap2 != null ? bitmap2.getWidth() : 0, bitmap2 != null ? bitmap2.getHeight() : 0, i, i2, null, 16, null);
        if (bitmap2 == null || bitmapMatrix$default == null) {
            bitmapComposeSubjectImage = null;
        } else {
            bitmapComposeSubjectImage = composeSubjectImage(bitmap2, bitmap4, i, i2);
            canvas.drawBitmap(bitmap2, bitmapMatrix$default, paint);
        }
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, (Rect) null, rect, paint);
        }
        if (bitmapComposeSubjectImage != null) {
            canvas.drawBitmap(bitmapComposeSubjectImage, (Rect) null, rect, paint);
        }
        if (bitmap3 != null) {
            canvas.drawBitmap(bitmap3, (Rect) null, rect, paint);
        }
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
        }
        return bitmapCreateBitmap$default;
    }

    public final Bitmap convertSubjectMask(Bitmap subject, boolean z) {
        Intrinsics.checkNotNullParameter(subject, "subject");
        Bitmap bitmapCreateBitmap$default = z ? subject : BitmapCreator.createBitmap$default(BitmapCreator.INSTANCE, subject.getWidth(), subject.getHeight(), Bitmap.Config.ARGB_8888, WideColorGamutManager.INSTANCE.isWideColorBitmap(subject), false, 16, null);
        Canvas canvas = new Canvas(bitmapCreateBitmap$default);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(CONVERT_TO_BLACK_AND_WHITE_PIXEL_MATRIX_VALUE)));
        canvas.drawBitmap(subject, 0.0f, 0.0f, paint);
        return bitmapCreateBitmap$default;
    }

    public final Bitmap resizeToPreviewLength(Bitmap bitmap) {
        int iCoerceAtLeast = RangesKt.coerceAtLeast(bitmap.getWidth(), bitmap.getHeight());
        if (iCoerceAtLeast < 3333) {
            return bitmap;
        }
        int i = (int) (((double) ((iCoerceAtLeast * 1.0f) / 3333)) + 0.5d);
        int width = bitmap.getWidth() / i;
        int height = bitmap.getHeight() / i;
        Log.d("Keyguard-Editor-SubjectHelper", "resizeToPreviewLength: " + bitmap.getWidth() + AnimatedProperty.PROPERTY_NAME_X + bitmap.getHeight() + " -> " + width + AnimatedProperty.PROPERTY_NAME_X + height + ", inSampleSize=" + i);
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(...)");
        return bitmapCreateScaledBitmap;
    }
}
