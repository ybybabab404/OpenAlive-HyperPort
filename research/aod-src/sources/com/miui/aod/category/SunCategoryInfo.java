package com.miui.aod.category;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.collection.ArrayMap;
import com.miui.aod.R;
import com.miui.aod.components.view.DescriptionStyleSelectView;
import com.miui.aod.util.ImageUtils;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.SunClock;
import com.miui.aod.widget.SunSelector;
import java.util.Calendar;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SunCategoryInfo extends CategoryInfo {
    public SunCategoryInfo() {
        super(CategoryConstants.CATEGORY_SUN);
    }

    @Override // com.miui.aod.common.StyleInfo
    public IAodClock createAodClock(boolean z, int i) {
        return new SunClock(i, z);
    }

    @Override // com.miui.aod.common.StyleInfo
    public Map<String, Object> getParameterForAnalytic() {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("style_name", "outer_space");
        arrayMap.put("style_event", "outer_space");
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getPreviewTopPaddingResId() {
        return R.dimen.aod_preview_middle_top_padding;
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getStyleSelectViewName() {
        return DescriptionStyleSelectView.class.getName();
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTitleResId() {
        return R.string.deep_space_planet;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTopMargin(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.icons_margin_top_sun);
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTopMarginPreview(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.icons_margin_top_sun_p);
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTranslationY(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.sun_translation_y);
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setBg(View view, int i) {
        super.setBg(view, i);
        Context context = view.getContext();
        ImageView imageView = (ImageView) view.findViewById(R.id.aod_bg);
        if (imageView != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
            Calendar calendar = Calendar.getInstance();
            int i2 = calendar.get(11);
            int i3 = calendar.get(12);
            SunSelector.updateSunRiseTime(context);
            if (i == 1) {
                marginLayoutParams.width = context.getResources().getDimensionPixelOffset(R.dimen.aod_sun_bg_width);
                marginLayoutParams.height = context.getResources().getDimensionPixelOffset(R.dimen.aod_sun_bg_height);
                marginLayoutParams.topMargin = context.getResources().getDimensionPixelOffset(R.dimen.aod_sun_bg_margin_top);
            } else if (i == 2) {
                marginLayoutParams.width = -1;
                marginLayoutParams.height = context.getResources().getDimensionPixelOffset(R.dimen.aod_sun_bg_height_middle);
            } else if (i == 3 || i == 5) {
                marginLayoutParams.width = context.getResources().getDimensionPixelOffset(R.dimen.aod_sun_bg_width);
                marginLayoutParams.height = context.getResources().getDimensionPixelOffset(R.dimen.aod_sun_bg_height);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            imageView.setLayoutParams(marginLayoutParams);
            if (i == 1) {
                ((FrameLayout) view.findViewById(R.id.clock_container)).setPadding(imageView.getPaddingLeft(), context.getResources().getDimensionPixelOffset(R.dimen.aod_bg_sun_padding_top_l), imageView.getPaddingRight(), imageView.getPaddingBottom());
            }
            ImageUtils.loadIntoImage(imageView, SunSelector.getSunImage(SunSelector.getDrawableIndex((i2 * 60) + i3)), (i == 3 || i == 1) ? 0 : 1);
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportLunarTime(Context context) {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportStepComponent() {
        return true;
    }
}
