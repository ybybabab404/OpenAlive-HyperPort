package com.miui.aod.category;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import androidx.collection.ArrayMap;
import com.google.gson.annotations.SerializedName;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.DrawableData;
import com.miui.aod.components.view.ClockStyleSelectView;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.theme.beans.ClockStyleBean;
import com.miui.aod.theme.beans.ThemeBean;
import com.miui.aod.widget.ClockPanel;
import com.miui.aod.widget.IAodClock;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ClockPanelCategoryInfo extends CategoryInfo {
    public static final Map<String, Integer> ACCESSIBILITY_NAME_MAP;
    private static final Map<String, String> sNameMap;

    @SerializedName("panel")
    private String mPanel;

    @SerializedName("secondary_color")
    private String mPanelSecondaryColor;

    @SerializedName("theme_style_info")
    private ClockStyleBean mThemeStyleBean;

    static {
        ArrayMap arrayMap = new ArrayMap();
        ACCESSIBILITY_NAME_MAP = arrayMap;
        arrayMap.put("CLOCK_STYLE_STATIC_1", Integer.valueOf(R.string.clock_style_static_1));
        arrayMap.put("CLOCK_STYLE_STATIC_2", Integer.valueOf(R.string.clock_style_static_2));
        arrayMap.put("CLOCK_STYLE_STATIC_3", Integer.valueOf(R.string.clock_style_static_3));
        arrayMap.put("CLOCK_STYLE_STATIC_4", Integer.valueOf(R.string.clock_style_static_4));
        arrayMap.put("CLOCK_STYLE_STATIC_5", Integer.valueOf(R.string.clock_style_static_5));
        arrayMap.put("CLOCK_STYLE_STATIC_6", Integer.valueOf(R.string.clock_style_static_6));
        arrayMap.put("CLOCK_STYLE_STATIC_7", Integer.valueOf(R.string.clock_style_static_7));
        arrayMap.put("CENTER_HORIZON", Integer.valueOf(R.string.center_horizon));
        arrayMap.put("is_vertical", Integer.valueOf(R.string.center_vertical));
        arrayMap.put("is_left_gravity", Integer.valueOf(R.string.left_horizon));
        arrayMap.put("style_0", Integer.valueOf(R.string.right_vertical));
        arrayMap.put("style_1", Integer.valueOf(R.string.right_horizon));
        ArrayMap arrayMap2 = new ArrayMap();
        sNameMap = arrayMap2;
        arrayMap2.put("CLOCK_STYLE_STATIC_1", "clock_face1");
        arrayMap2.put("CLOCK_STYLE_STATIC_2", "clock_face2");
        arrayMap2.put("CLOCK_STYLE_STATIC_3", "clock_face3");
        arrayMap2.put("CLOCK_STYLE_STATIC_4", "clock_face4");
        arrayMap2.put("CLOCK_STYLE_STATIC_5", "clock_face5");
        arrayMap2.put("CLOCK_STYLE_STATIC_6", "clock_face6");
        arrayMap2.put("CLOCK_STYLE_STATIC_7", "clock_face7");
    }

    public ClockPanelCategoryInfo() {
        super(CategoryConstants.CATEGORY_CLOCK_PANEL);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean compareToForPresetStyle(StyleInfo styleInfo) {
        return super.compareToForPresetStyle(styleInfo) && TextUtils.equals(this.mPanel, ((ClockPanelCategoryInfo) styleInfo).getPanel());
    }

    @Override // com.miui.aod.common.StyleInfo
    public IAodClock createAodClock(boolean z, int i) {
        return (i == 2 || i == 4) ? new ClockPanel(i, false) : new ClockPanel(i, z);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean equals(Object obj) {
        if (!(obj instanceof ClockPanelCategoryInfo)) {
            return false;
        }
        ClockPanelCategoryInfo clockPanelCategoryInfo = (ClockPanelCategoryInfo) obj;
        if (!TextUtils.isEmpty(clockPanelCategoryInfo.getColorName()) && !clockPanelCategoryInfo.getColorName().equals(getColorName())) {
            return false;
        }
        if ((!TextUtils.isEmpty(getColorName()) && !getColorName().equals(clockPanelCategoryInfo.getColorName())) || clockPanelCategoryInfo.getClockBgResID() != getClockBgResID()) {
            return false;
        }
        if (TextUtils.isEmpty(getPanel()) || getPanel().equals(clockPanelCategoryInfo.getPanel())) {
            return TextUtils.isEmpty(clockPanelCategoryInfo.getPanel()) || clockPanelCategoryInfo.getPanel().equals(getPanel());
        }
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getBatteryMask() {
        DrawableData drawableDataByName = AodDrawables.getDrawableDataByName(getSecondaryColor());
        if (drawableDataByName == null) {
            drawableDataByName = (DrawableData) AodDrawables.sPureDrawableList.get(0);
        }
        return drawableDataByName.mResId;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getNotificationAnimTime() {
        return 2000;
    }

    public String getPanel() {
        return this.mPanel;
    }

    public String getPanelName(Context context) {
        String panelORDefault = getPanelORDefault();
        Integer num = ACCESSIBILITY_NAME_MAP.get(panelORDefault);
        if (panelORDefault != null && num != null) {
            panelORDefault = context.getString(num.intValue());
        }
        return TextUtils.isEmpty(panelORDefault) ? getPanelORDefault() : panelORDefault;
    }

    public String getPanelORDefault() {
        return TextUtils.isEmpty(this.mPanel) ? "CLOCK_STYLE_STATIC_1" : this.mPanel;
    }

    @Override // com.miui.aod.common.StyleInfo
    public Map<String, Object> getParameterForAnalytic() {
        ArrayMap arrayMap = new ArrayMap();
        Map<String, String> map = sNameMap;
        arrayMap.put("style_name", map.getOrDefault(getPanel(), StyleInfo.DEFAULT_PARAMETER));
        arrayMap.put("style_event", map.getOrDefault(getPanel(), "clock_face1"));
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    public StyleInfo.PresetStyle getPresetStyle() {
        return new StyleInfo.PresetStyle(R.string.clock_panel_icon, new String[]{"{\"panel\":\"CLOCK_STYLE_STATIC_1\"}", "{\"panel\":\"CLOCK_STYLE_STATIC_2\"}", "{\"panel\":\"CLOCK_STYLE_STATIC_3\"}", "{\"panel\":\"CLOCK_STYLE_STATIC_4\"}", "{\"panel\":\"CLOCK_STYLE_STATIC_5\"}", "{\"panel\":\"CLOCK_STYLE_STATIC_6\"}", "{\"panel\":\"CLOCK_STYLE_STATIC_7\"}"});
    }

    public String getSecondaryColor() {
        return this.mPanelSecondaryColor;
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
    public ClockStyleBean getThemeBean() {
        return this.mThemeStyleBean;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTitleResId() {
        return R.string.standard_clock_panel;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean hasAnimation() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isBatterySwitchOn() {
        ClockStyleBean clockStyleBean = this.mThemeStyleBean;
        if (clockStyleBean == null) {
            return super.isBatterySwitchOn();
        }
        return clockStyleBean.mBatteryEnable == 1;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isNotificationSwitchOn() {
        ClockStyleBean clockStyleBean = this.mThemeStyleBean;
        if (clockStyleBean == null) {
            return super.isNotificationSwitchOn();
        }
        return clockStyleBean.mNotificationEnable == 1;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needInflateBattery(Context context) {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setBg(View view, int i) {
        View viewFindViewById = view.findViewById(R.id.aod_bg);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(8);
        }
    }

    public void setPanel(String str) {
        this.mPanel = str;
    }

    public void setSecondaryColor(String str) {
        this.mPanelSecondaryColor = str;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setThemeBean(ThemeBean themeBean) {
        this.mThemeStyleBean = (ClockStyleBean) themeBean;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportStepComponent() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void switchOnBatteryIcon(boolean z) {
        super.switchOnBatteryIcon(z);
        ClockStyleBean clockStyleBean = this.mThemeStyleBean;
        if (clockStyleBean != null) {
            clockStyleBean.mBatteryEnable = z ? 1 : 0;
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public void switchOnNotificationIcon(boolean z) {
        super.switchOnNotificationIcon(z);
        ClockStyleBean clockStyleBean = this.mThemeStyleBean;
        if (clockStyleBean != null) {
            clockStyleBean.mNotificationEnable = z ? 1 : 0;
        }
    }
}
