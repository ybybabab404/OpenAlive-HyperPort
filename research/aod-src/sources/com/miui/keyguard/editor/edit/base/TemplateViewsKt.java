package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.util.Log;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.utils.BitmapUtil;
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
/* JADX INFO: compiled from: TemplateViews.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TemplateViewsKt {
    public static final Integer areaColorMode(Bitmap bitmap, Rect rect) {
        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
        Bitmap bitmapCropBitmap = bitmapUtil.cropBitmap(bitmap, rect.left, rect.right, rect.top, rect.bottom);
        if (bitmapCropBitmap != null) {
            return Integer.valueOf(bitmapUtil.getColorMode(bitmapCropBitmap));
        }
        Log.w("TemplateViews", "cropped is null");
        return null;
    }

    public static final Rect colorModeRect(Context context, Bitmap bitmap, int i, int i2, int i3, int i4) {
        float f = context.getResources().getFloat(i);
        float f2 = context.getResources().getFloat(i2);
        float f3 = context.getResources().getFloat(i3);
        float f4 = context.getResources().getFloat(i4);
        float width = bitmap.getWidth() * f;
        float height = bitmap.getHeight() * f2;
        return new Rect((int) width, (int) height, (int) (width + (f3 * bitmap.getWidth())), (int) (height + (f4 * bitmap.getHeight())));
    }

    public static final ColorModes colorModes(BaseTemplateView baseTemplateView, Bitmap bitmap, boolean z) {
        Intrinsics.checkNotNullParameter(baseTemplateView, "<this>");
        if (bitmap == null) {
            Log.w("TemplateViews", "get colorModes, bitmap is null");
            return null;
        }
        Log.d("TemplateViews", "cropped=" + z + " bitmap.width=" + Integer.valueOf(bitmap.getWidth()) + " bitmap.height=" + Integer.valueOf(bitmap.getHeight()));
        if (!z) {
            IWallpaperLayer wallpaperLayer = baseTemplateView.getWallpaperLayer();
            WallpaperPositionInfo position = wallpaperLayer != null ? wallpaperLayer.getPosition(bitmap) : null;
            if (position == null) {
                Log.w("TemplateViews", "get colorModes, positionInfo is null, view size rect");
                position = new WallpaperPositionInfo(0.0f, 0.0f, baseTemplateView.getWidth(), baseTemplateView.getHeight(), null, null, null, null, 224, null);
            }
            bitmap = BitmapUtil.INSTANCE.cropBitmap(bitmap, (int) position.getLeft(), (int) position.getRight(), (int) position.getTop(), (int) position.getBottom());
        }
        if (bitmap == null) {
            Log.w("TemplateViews", "get colorModes, croppedBitmap is null");
            return null;
        }
        Integer numValueOf = Integer.valueOf(BitmapUtil.INSTANCE.getColorMode(bitmap));
        Context context = baseTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        Integer numWallpaperScaleHintColorMode = wallpaperScaleHintColorMode(context, bitmap);
        Context context2 = baseTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        return new ColorModes(numValueOf, numWallpaperScaleHintColorMode, functionBarTextColorMode(context2, bitmap));
    }

    public static final Integer functionBarTextColorMode(Context context, Bitmap bitmap) {
        Rect rectColorModeRect = colorModeRect(context, bitmap, R.dimen.kg_func_text_color_mode_rect_left_scale, R.dimen.kg_func_text_color_mode_rect_top_scale, R.dimen.kg_func_text_color_mode_rect_width_scale, R.dimen.kg_func_text_color_mode_rect_height_scale);
        if (rectColorModeRect.right <= rectColorModeRect.left) {
            Log.d("TemplateViews", "bar text reset right rect=" + rectColorModeRect + " bitmap.width=" + bitmap.getWidth());
            rectColorModeRect.right = Math.min(rectColorModeRect.left + 1, bitmap.getWidth());
        }
        if (rectColorModeRect.bottom <= rectColorModeRect.top) {
            Log.d("TemplateViews", "bar text reset bottom rect=" + rectColorModeRect + " bitmap.height=" + bitmap.getHeight());
            rectColorModeRect.bottom = Math.min(rectColorModeRect.top + 1, bitmap.getHeight());
        }
        return areaColorMode(bitmap, rectColorModeRect);
    }

    public static final void setColorMode(ImageView imageView, int i) {
        Intrinsics.checkNotNullParameter(imageView, "<this>");
        if (i != 2) {
            imageView.setColorFilter((ColorFilter) null);
            return;
        }
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setScale(-1.0f, -1.0f, -1.0f, 1.0f);
        imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    public static final Integer wallpaperScaleHintColorMode(Context context, Bitmap bitmap) {
        Rect rectColorModeRect = colorModeRect(context, bitmap, R.dimen.kg_scale_hint_color_mode_rect_left_scale, R.dimen.kg_scale_hint_color_mode_rect_top_scale, R.dimen.kg_scale_hint_color_mode_rect_width_scale, R.dimen.kg_scale_hint_color_mode_rect_height_scale);
        if (rectColorModeRect.right <= rectColorModeRect.left) {
            Log.d("TemplateViews", "scale hint reset right rect=" + rectColorModeRect + " bitmap.width=" + bitmap.getWidth());
            rectColorModeRect.right = Math.min(rectColorModeRect.left + 1, bitmap.getWidth());
        }
        if (rectColorModeRect.bottom <= rectColorModeRect.top) {
            Log.d("TemplateViews", "scale hint reset bottom rect=" + rectColorModeRect + " bitmap.height=" + bitmap.getHeight());
            rectColorModeRect.bottom = Math.min(rectColorModeRect.top + 1, bitmap.getHeight());
        }
        return areaColorMode(bitmap, rectColorModeRect);
    }
}
