package com.miui.aod.category;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.collection.ArrayMap;
import com.google.gson.annotations.SerializedName;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.ClockStyleSelectView;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.SmartCoverClock;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SmartCoverStyleInfo extends CategoryInfo {
    public static final int style_a = 0;
    public static final int style_b = 1;

    @SerializedName("smart_cover_showonly")
    private boolean mSmartCoverShowOnly;

    @SerializedName("style")
    private int mStyle;

    public @interface IStyle {
    }

    public SmartCoverStyleInfo() {
        super(CategoryConstants.CATEGORY_SMART_COVER);
        this.mStyle = 0;
        this.mSmartCoverShowOnly = false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean compareToForPresetStyle(StyleInfo styleInfo) {
        return super.compareToForPresetStyle(styleInfo) && ((SmartCoverStyleInfo) styleInfo).getStyle() == getStyle();
    }

    @Override // com.miui.aod.common.StyleInfo
    public IAodClock createAodClock(boolean z, int i) {
        return new SmartCoverClock(i);
    }

    @Override // com.miui.aod.common.StyleInfo
    public void dealAodContainerLayoutParam(ViewGroup.LayoutParams layoutParams, int i, Context context, boolean z) {
        if ((i == 1 || i == 3 || i == 5) && (layoutParams instanceof FrameLayout.LayoutParams)) {
            ((FrameLayout.LayoutParams) layoutParams).gravity = getClockGravity();
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getClockGravity() {
        return 5;
    }

    @Override // com.miui.aod.common.StyleInfo
    public Map<String, Object> getParameterForAnalytic() {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("style_name", "simple_clock");
        arrayMap.put("style_event", "simple_clock");
        arrayMap.put("style_type", getStyle() == 0 ? "style1" : "style2");
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    public StyleInfo.PresetStyle getPresetStyle() {
        return new StyleInfo.PresetStyle(R.string.time_style, new String[]{"{\"style\":\"0\"}", "{\"style\":\"1\"}"});
    }

    @Override // com.miui.aod.common.StyleInfo
    public long getStartDelay() {
        return 300L;
    }

    public int getStyle() {
        return this.mStyle;
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getStyleSelectViewName() {
        return ClockStyleSelectView.class.getName();
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTitleResId() {
        return R.string.minimalist_clock;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean hasAnimation() {
        return true;
    }

    public boolean isSmartCoverShowOnly() {
        return this.mSmartCoverShowOnly;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needInflateNotification() {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setBg(View view, int i) {
        if (view.findViewById(R.id.aod_bg) != null) {
            view.findViewById(R.id.aod_bg).setVisibility(8);
        }
    }

    public void setSmartCoverShowOnly(boolean z) {
        this.mSmartCoverShowOnly = z;
    }

    public void setStyle(int i) {
        this.mStyle = i;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportChangeColorAlongTime() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportLunarTime(Context context) {
        return false;
    }
}
