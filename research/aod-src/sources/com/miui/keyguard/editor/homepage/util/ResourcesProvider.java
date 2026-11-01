package com.miui.keyguard.editor.homepage.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.view.generator.FactoryParams;
import com.miui.keyguard.editor.utils.DeviceUtil;
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
/* JADX INFO: compiled from: ResourcesProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ResourcesProvider {
    public static final ResourcesProvider INSTANCE = new ResourcesProvider();
    public static Float blurRadius;
    public static Float bottomPagerOffscreenOffset;
    public static Float crossTitleTopMargin;
    public static Integer customBackgroundColor;
    public static Integer customBlendColor1;
    public static Integer customBlendColor2;
    public static Float customButtonRadius;
    public static Integer deleteButtonBlendColor1;
    public static Integer deleteButtonBlendColor2;
    public static Float deleteButtonRadius;
    public static Integer deleteLayerBackgroundColor;
    public static Integer homeExtraHeight;
    public static Float homeScale;
    public static Float horizontalPagerMargin;
    public static Float horizontalPagerOffscreenOffset;
    public static Float internalPagerBottomMargin;
    public static Float internalPagerTopMargin;
    public static Float leftPageInitialTranslateX;
    public static Float maxScrollDownOffset;
    public static Float maxScrollUpOffset;
    public static Float neighboringItemMinScale;
    public static Float previewRadius;
    public static Point realSizeOutValue;
    public static Float rightPageInitialTranslateX;
    public static Drawable selectedFrameBackground;
    public static Float templateContainerHeight;
    public static Float templateContainerPadding;
    public static Float templateContainerWidth;
    public static Float templateEditScale;
    public static Float templateOutlineRadius;
    public static Float templatePreviewHeight;
    public static Float templatePreviewWidth;
    public static Float templateTitleTopMargin;

    public static /* synthetic */ float provideTemplateContainerHeight$default(ResourcesProvider resourcesProvider, Context context, FactoryParams factoryParams, int i, Object obj) {
        if ((i & 2) != 0) {
            factoryParams = null;
        }
        return resourcesProvider.provideTemplateContainerHeight(context, factoryParams);
    }

    public final void clearDimenResources() {
        templateContainerWidth = null;
        templateContainerPadding = null;
        templateContainerHeight = null;
        templatePreviewWidth = null;
        templatePreviewHeight = null;
        realSizeOutValue = null;
        previewRadius = null;
        templateOutlineRadius = null;
        customButtonRadius = null;
        deleteButtonRadius = null;
        blurRadius = null;
        internalPagerTopMargin = null;
        internalPagerBottomMargin = null;
        bottomPagerOffscreenOffset = null;
        maxScrollUpOffset = null;
        maxScrollDownOffset = null;
        horizontalPagerOffscreenOffset = null;
        horizontalPagerMargin = null;
        leftPageInitialTranslateX = null;
        rightPageInitialTranslateX = null;
        selectedFrameBackground = null;
        neighboringItemMinScale = null;
        templateEditScale = null;
        templateTitleTopMargin = null;
    }

    public final int getColor(Context context, int i) {
        return ContextCompat.getColor(context, i);
    }

    public final Float getDimen(Context context, int i) {
        Resources resources;
        if (context == null || (resources = context.getResources()) == null) {
            return null;
        }
        return Float.valueOf(resources.getDimension(i));
    }

    public final Integer getDimensionPixelSize(Context context, int i) {
        Resources resources;
        if (context == null || (resources = context.getResources()) == null) {
            return null;
        }
        return Integer.valueOf(resources.getDimensionPixelSize(i));
    }

    public final Float getFloat(Context context, int i) {
        Resources resources;
        if (context == null || (resources = context.getResources()) == null) {
            return null;
        }
        return Float.valueOf(resources.getFloat(i));
    }

    public final Float getProvideTemplateTitleTopMargin() {
        return templateTitleTopMargin;
    }

    public final void loadResources(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        provideTemplateContainerWidth(context);
        provideTemplateContainerPadding(context);
        provideTemplateContainerHeight$default(this, context, null, 2, null);
        provideRealSize(context);
        providePreviewRadius(context);
        provideCustomButtonRadius(context);
        provideDeleteButtonRadius(context);
        provideCustomButtonBlendColor1(context);
        provideCustomButtonBlendColor2(context);
        provideCustomButtonBackgroundColor(context);
        provideDeleteButtonBlendColor1(context);
        provideDeleteButtonBlendColor2(context);
        provideBlurRadius(context);
        provideDeleteLayerBackgroundColor(context);
        provideSelectedFrameBackground(context);
        provideInternalPagerTopMargin(context);
        provideInternalPagerBottomMargin(context);
        provideBottomPagerOffscreenOffset(context);
        provideMaxScrollUpOffset(context);
        provideMaxScrollDownOffset(context);
        provideHorizontalPagerOffscreenOffset(context);
        provideMinScaleForNeighboringItem(context);
        provideHomeExtraHeight(context);
        provideHomeScale(context);
    }

    public final float provideBlurRadius(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (blurRadius == null) {
            blurRadius = Float.valueOf(context.getResources().getFloat(R.dimen.kg_blur_radius_60));
        }
        Float f = blurRadius;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideBottomPagerOffscreenOffset(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (bottomPagerOffscreenOffset == null) {
            bottomPagerOffscreenOffset = getDimen(context, R.dimen.kg_main_vertical_pager_offscreen_offset);
        }
        Float f = bottomPagerOffscreenOffset;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideCrossTitleTopMargin(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (crossTitleTopMargin == null) {
            crossTitleTopMargin = Float.valueOf(context.getResources().getDimensionPixelSize(R.dimen.kg_main_pager_margin_top));
        }
        Float f = crossTitleTopMargin;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final int provideCustomButtonBackgroundColor(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (customBackgroundColor == null) {
            customBackgroundColor = Integer.valueOf(getColor(context, R.color.kg_main_item_template_custom_button_background_color));
        }
        Integer num = customBackgroundColor;
        Intrinsics.checkNotNull(num);
        return num.intValue();
    }

    public final int provideCustomButtonBlendColor1(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (customBlendColor1 == null) {
            customBlendColor1 = Integer.valueOf(getColor(context, R.color.kg_function_button_blend_color1));
        }
        Integer num = customBlendColor1;
        Intrinsics.checkNotNull(num);
        return num.intValue();
    }

    public final int provideCustomButtonBlendColor2(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (customBlendColor2 == null) {
            customBlendColor2 = Integer.valueOf(getColor(context, R.color.kg_function_button_blend_color2));
        }
        Integer num = customBlendColor2;
        Intrinsics.checkNotNull(num);
        return num.intValue();
    }

    public final float provideCustomButtonRadius(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (customButtonRadius == null) {
            customButtonRadius = getDimen(context, R.dimen.kg_main_item_custom_button_radius);
        }
        Float f = customButtonRadius;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final int provideDeleteButtonBlendColor1(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (deleteButtonBlendColor1 == null) {
            deleteButtonBlendColor1 = Integer.valueOf(getColor(context, R.color.kg_main_item_delete_button_blend_color1));
        }
        Integer num = deleteButtonBlendColor1;
        Intrinsics.checkNotNull(num);
        return num.intValue();
    }

    public final int provideDeleteButtonBlendColor2(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (deleteButtonBlendColor2 == null) {
            deleteButtonBlendColor2 = Integer.valueOf(getColor(context, R.color.kg_main_item_delete_button_blend_color2));
        }
        Integer num = deleteButtonBlendColor2;
        Intrinsics.checkNotNull(num);
        return num.intValue();
    }

    public final float provideDeleteButtonRadius(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (deleteButtonRadius == null) {
            deleteButtonRadius = getDimen(context, R.dimen.kg_main_item_delete_button_radius);
        }
        Float f = deleteButtonRadius;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final int provideDeleteLayerBackgroundColor(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (deleteLayerBackgroundColor == null) {
            deleteLayerBackgroundColor = Integer.valueOf(context.getColor(R.color.kg_main_item_delete_layer_background_color));
        }
        Integer num = deleteLayerBackgroundColor;
        Intrinsics.checkNotNull(num);
        return num.intValue();
    }

    public final int provideHomeExtraHeight(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (homeExtraHeight == null) {
            homeExtraHeight = Integer.valueOf(context.getResources().getDimensionPixelSize(R.dimen.kg_home_extra_height));
        }
        Integer num = homeExtraHeight;
        Intrinsics.checkNotNull(num);
        return num.intValue();
    }

    public final float provideHomeScale(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (homeScale == null) {
            homeScale = Float.valueOf(context.getResources().getFloat(R.dimen.kg_home_scale));
        }
        Float f = homeScale;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideHorizontalPagerMargin(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (horizontalPagerMargin == null) {
            horizontalPagerMargin = getDimen(context, R.dimen.kg_main_item_floor_pager_margin_horizontal);
        }
        Float f = horizontalPagerMargin;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideHorizontalPagerOffscreenOffset(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (horizontalPagerOffscreenOffset == null) {
            horizontalPagerOffscreenOffset = Float.valueOf(((provideRealSize(context).x - provideTemplateContainerWidth(context)) / 2) - provideHorizontalPagerMargin(context));
        }
        Float f = horizontalPagerOffscreenOffset;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideInternalPagerBottomMargin(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (internalPagerBottomMargin == null) {
            internalPagerBottomMargin = Float.valueOf((provideRealSize(context).y - provideInternalPagerTopMargin(context)) - provideTemplateContainerHeight$default(this, context, null, 2, null));
        }
        Float f = internalPagerBottomMargin;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideInternalPagerTopMargin(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (internalPagerTopMargin == null) {
            Integer dimensionPixelSize = getDimensionPixelSize(context, R.dimen.kg_main_internal_pager_margin_top);
            internalPagerTopMargin = dimensionPixelSize != null ? Float.valueOf(dimensionPixelSize.intValue()) : null;
        }
        Float f = internalPagerTopMargin;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideLeftPageInitialTranslateX(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (leftPageInitialTranslateX == null) {
            float fProvideTemplateContainerWidth = provideTemplateContainerWidth(context);
            float fProvideTemplateContainerPadding = provideTemplateContainerPadding(context);
            float fProvideHorizontalPagerMargin = provideHorizontalPagerMargin(context);
            float fProvideMinScaleForNeighboringItem = provideMinScaleForNeighboringItem(context);
            leftPageInitialTranslateX = Float.valueOf((((fProvideTemplateContainerWidth - (fProvideTemplateContainerWidth * fProvideMinScaleForNeighboringItem)) / 2) + (fProvideTemplateContainerPadding * fProvideMinScaleForNeighboringItem)) - fProvideHorizontalPagerMargin);
        }
        Float f = leftPageInitialTranslateX;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideMaxScrollDownOffset(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (maxScrollDownOffset == null) {
            maxScrollDownOffset = getDimen(context, R.dimen.kg_main_max_scroll_down_offset);
        }
        Float f = maxScrollDownOffset;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideMaxScrollUpOffset(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (maxScrollUpOffset == null) {
            maxScrollUpOffset = getDimen(context, R.dimen.kg_main_max_scroll_up_offset);
        }
        Float f = maxScrollUpOffset;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideMinScaleForNeighboringItem(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (neighboringItemMinScale == null) {
            neighboringItemMinScale = getFloat(context, R.dimen.kg_main_neighboring_item_min_scale);
        }
        Log.i("Keyguard-Editor-ResourcesProvider", "provideMinScaleForNeighboringItem -> minScale = " + neighboringItemMinScale);
        Float f = neighboringItemMinScale;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float providePreviewRadius(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (previewRadius == null) {
            previewRadius = getDimen(context, R.dimen.kg_main_item_preview_radius);
        }
        Float f = previewRadius;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final Point provideRealSize(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (realSizeOutValue == null) {
            realSizeOutValue = CrossListViewUtil.INSTANCE.requireDisplayRealSize(context);
        }
        Point point = realSizeOutValue;
        Intrinsics.checkNotNull(point);
        return point;
    }

    public final float provideRightPageInitialTranslateX(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (rightPageInitialTranslateX == null) {
            float fProvideTemplateContainerWidth = provideTemplateContainerWidth(context);
            float fProvideTemplateContainerPadding = provideTemplateContainerPadding(context);
            float fProvideHorizontalPagerMargin = provideHorizontalPagerMargin(context);
            float fProvideMinScaleForNeighboringItem = provideMinScaleForNeighboringItem(context);
            rightPageInitialTranslateX = Float.valueOf(((-((fProvideTemplateContainerWidth - (fProvideTemplateContainerWidth * fProvideMinScaleForNeighboringItem)) / 2)) + fProvideHorizontalPagerMargin) - (fProvideTemplateContainerPadding * fProvideMinScaleForNeighboringItem));
        }
        Float f = rightPageInitialTranslateX;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final Drawable provideSelectedFrameBackground(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (selectedFrameBackground == null) {
            selectedFrameBackground = context.getDrawable(R.drawable.kg_background_main_item_selected);
        }
        Drawable drawable = selectedFrameBackground;
        Intrinsics.checkNotNull(drawable);
        return drawable;
    }

    public final float provideTemplateContainerHeight(Context context, FactoryParams factoryParams) {
        Context context2;
        Intrinsics.checkNotNullParameter(context, "context");
        if (templateContainerHeight == null) {
            context2 = context;
            templateContainerHeight = Float.valueOf(CrossListViewUtil.calculateTemplateContainerHeight$default(CrossListViewUtil.INSTANCE, context2, provideTemplateContainerWidth(context), null, provideTemplateContainerPadding(context), 4, null));
        } else {
            context2 = context;
        }
        Float f = templateContainerHeight;
        Intrinsics.checkNotNull(f);
        return f.floatValue() + ((factoryParams == null || factoryParams.isPreset()) ? 0 : provideHomeExtraHeight(context2));
    }

    public final float provideTemplateContainerPadding(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (templateContainerPadding == null) {
            Integer dimensionPixelSize = getDimensionPixelSize(context, R.dimen.kg_main_item_container_padding);
            templateContainerPadding = dimensionPixelSize != null ? Float.valueOf(dimensionPixelSize.intValue()) : null;
        }
        Float f = templateContainerPadding;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideTemplateContainerWidth(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (templateContainerWidth == null) {
            Integer dimensionPixelSize = getDimensionPixelSize(context, R.dimen.kg_main_item_container_width);
            templateContainerWidth = dimensionPixelSize != null ? Float.valueOf(dimensionPixelSize.intValue()) : null;
        }
        Float f = templateContainerWidth;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideTemplateEditScale(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (templateEditScale == null) {
            templateEditScale = Float.valueOf(context.getResources().getFloat(R.dimen.kg_combine_edit_template_scale));
        }
        Float f = templateEditScale;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideTemplateOutlineRadius(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (templateOutlineRadius == null) {
            templateOutlineRadius = getDimen(context, R.dimen.kg_main_item_preview_radius);
        }
        Float f = templateOutlineRadius;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideTemplatePreviewHeight(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (templatePreviewHeight == null) {
            float fProvideTemplatePreviewWidth = provideTemplatePreviewWidth(context);
            CrossListViewUtil crossListViewUtil = CrossListViewUtil.INSTANCE;
            Point pointRequireDisplayRealSize = crossListViewUtil.requireDisplayRealSize(context);
            int iMax = Math.max(pointRequireDisplayRealSize.x, pointRequireDisplayRealSize.y);
            int iMin = Math.min(pointRequireDisplayRealSize.x, pointRequireDisplayRealSize.y);
            if (DeviceUtil.INSTANCE.isLargeDisplay(context) && context.getResources().getConfiguration().orientation == 2) {
                pointRequireDisplayRealSize.x = iMax;
                pointRequireDisplayRealSize.y = iMin;
            } else {
                pointRequireDisplayRealSize.x = iMin;
                pointRequireDisplayRealSize.y = iMax;
            }
            templatePreviewHeight = Float.valueOf(crossListViewUtil.calculateTemplateViewHeightByRealRatio(fProvideTemplatePreviewWidth, pointRequireDisplayRealSize));
        }
        Float f = templatePreviewHeight;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideTemplatePreviewWidth(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (templatePreviewWidth == null) {
            templatePreviewWidth = Float.valueOf(provideTemplateContainerWidth(context) - (2 * provideTemplateContainerPadding(context)));
        }
        Float f = templatePreviewWidth;
        Intrinsics.checkNotNull(f);
        return f.floatValue();
    }

    public final float provideTemplateTitleTopMargin(Context context, float f) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (templateTitleTopMargin == null) {
            templateTitleTopMargin = Float.valueOf(f - context.getResources().getDimensionPixelSize(R.dimen.kg_editor_title_margin_top));
        }
        Float f2 = templateTitleTopMargin;
        Intrinsics.checkNotNull(f2);
        return f2.floatValue();
    }
}
