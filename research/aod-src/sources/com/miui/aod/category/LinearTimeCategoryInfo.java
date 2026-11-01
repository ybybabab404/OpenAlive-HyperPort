package com.miui.aod.category;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import androidx.collection.ArrayMap;
import com.google.gson.annotations.SerializedName;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.ClockStyleSelectView;
import com.miui.aod.resource.ColorPool;
import com.miui.aod.widget.HorizontalClock;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.VerticalClock;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LinearTimeCategoryInfo extends CategoryInfo {

    @SerializedName("is_left_gravity")
    private boolean mIsLeftGravity;

    @SerializedName("is_vertical")
    private boolean mIsVertical;

    public LinearTimeCategoryInfo() {
        super(CategoryConstants.CATEGORY_LINEAR_TIMETEXT);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean compareToForPresetStyle(StyleInfo styleInfo) {
        if (styleInfo != null && (styleInfo instanceof LinearTimeCategoryInfo)) {
            LinearTimeCategoryInfo linearTimeCategoryInfo = (LinearTimeCategoryInfo) styleInfo;
            if (linearTimeCategoryInfo.isLeftGravity() == isLeftGravity() && linearTimeCategoryInfo.isVertical() == isVertical()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public IAodClock createAodClock(boolean z, int i) {
        if (i == 2 || i == 4) {
            z = false;
        }
        if (isLeftGravity()) {
            return new HorizontalClock(i, z, 3);
        }
        return this.mIsVertical ? new VerticalClock(i, z) : new HorizontalClock(i, z, 17);
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getClockGravity() {
        if (this.mIsLeftGravity) {
            return 3;
        }
        return super.getClockGravity();
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getClockMask() {
        return isVertical() ? ColorPool.getClockMaskY(getColorName()) : super.getClockMask();
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getColorName() {
        String colorName = super.getColorName();
        if (!isLeftGravity() && TextUtils.isEmpty(colorName)) {
            return this.mIsVertical ? "aod_gradient_2" : "aod_gradient_1";
        }
        return colorName;
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getFullTitle() {
        return this.mIsVertical ? AODApplication.sInstance.getResources().getString(R.string.vertical_clock) : AODApplication.sInstance.getResources().getString(R.string.horizontal_clock);
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getNotificationAnimTime() {
        return 1500;
    }

    @Override // com.miui.aod.common.StyleInfo
    public Map<String, Object> getParameterForAnalytic() {
        String str;
        ArrayMap arrayMap = new ArrayMap();
        if (isVertical()) {
            str = "style2";
        } else {
            str = isLeftGravity() ? "style3" : "style1";
        }
        arrayMap.put("style_name", "standard");
        arrayMap.put("style_event", "standard");
        arrayMap.put("style_type", str);
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    public StyleInfo.PresetStyle getPresetStyle() {
        return new StyleInfo.PresetStyle(R.string.time_style, new String[]{"{\"panel\":\"CENTER_HORIZON\"}", "{\"is_vertical\":true}", "{\"is_left_gravity\":true}"});
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getPreviewTopPaddingResId() {
        return R.dimen.aod_preview_large_top_padding;
    }

    @Override // com.miui.aod.common.StyleInfo
    public long getStartDelay() {
        return 300L;
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getStyleSelectViewName() {
        return ClockStyleSelectView.class.getName();
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTopMargin(Context context) {
        return isVertical() ? context.getResources().getDimensionPixelOffset(R.dimen.icons_margin_top_vertical) : super.getTopMargin(context);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean hasAnimation() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isLeftGravity() {
        return this.mIsLeftGravity;
    }

    public boolean isVertical() {
        return this.mIsVertical;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setBg(View view, int i) {
        View viewFindViewById = view.findViewById(R.id.aod_bg);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(8);
        }
    }

    public void setLeftGravity(boolean z) {
        this.mIsLeftGravity = z;
    }

    public void setVertical(boolean z) {
        this.mIsVertical = z;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportChangeColorAlongTime() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportStepComponent() {
        return true;
    }
}
