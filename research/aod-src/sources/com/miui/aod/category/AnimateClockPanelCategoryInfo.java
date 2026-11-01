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
import com.miui.aod.theme.beans.ThemeBean;
import com.miui.aod.theme.beans.WaveClockStyleBean;
import com.miui.aod.widget.ClockPanel;
import com.miui.aod.widget.IAodClock;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AnimateClockPanelCategoryInfo extends CategoryInfo {
    private static final Map<String, Integer> mNameMap;
    private static final Map<String, String> sNameMap;

    @SerializedName("panel")
    protected String mPanel;

    @SerializedName("theme_style_info")
    private WaveClockStyleBean mThemeStyleBean;

    static {
        ArrayMap arrayMap = new ArrayMap();
        sNameMap = arrayMap;
        arrayMap.put("clock_panel_first", "wave_dial1");
        arrayMap.put("clock_panel_second", "wave_dial2");
        arrayMap.put("clock_panel_third", "wave_dial3");
        ArrayMap arrayMap2 = new ArrayMap();
        mNameMap = arrayMap2;
        arrayMap2.put("clock_panel_first", Integer.valueOf(R.string.animation_clock_panel1));
        arrayMap2.put("clock_panel_second", Integer.valueOf(R.string.animation_clock_panel2));
        arrayMap2.put("clock_panel_third", Integer.valueOf(R.string.animation_clock_panel3));
        arrayMap2.put("clock_panel_forth", Integer.valueOf(R.string.animation_clock_panel4));
    }

    public AnimateClockPanelCategoryInfo() {
        super(CategoryConstants.CATEGORY_ANIMATE_CLOCK_PANEL);
        setBgCluster(new int[]{-1});
    }

    @Override // com.miui.aod.common.StyleInfo
    public IAodClock createAodClock(boolean z, int i) {
        return (i == 2 || i == 4) ? new ClockPanel(i, false) : new ClockPanel(i, z);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean equals(Object obj) {
        if (!(obj instanceof AnimateClockPanelCategoryInfo)) {
            return false;
        }
        AnimateClockPanelCategoryInfo animateClockPanelCategoryInfo = (AnimateClockPanelCategoryInfo) obj;
        if (!TextUtils.isEmpty(animateClockPanelCategoryInfo.getColorName()) && !animateClockPanelCategoryInfo.getColorName().equals(getColorName())) {
            return false;
        }
        if ((!TextUtils.isEmpty(getColorName()) && !getColorName().equals(animateClockPanelCategoryInfo.getColorName())) || animateClockPanelCategoryInfo.getClockBgResID() != getClockBgResID()) {
            return false;
        }
        if (TextUtils.isEmpty(getPanel()) || getPanel().equals(animateClockPanelCategoryInfo.getPanel())) {
            return TextUtils.isEmpty(animateClockPanelCategoryInfo.getPanel()) || animateClockPanelCategoryInfo.getPanel().equals(getPanel());
        }
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getColorName() {
        String colorName = super.getColorName();
        return TextUtils.isEmpty(colorName) ? "light_purple" : colorName;
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getFullTitle() {
        if (TextUtils.isEmpty(this.mPanel)) {
            this.mPanel = "clock_panel_first";
        }
        Map<String, Integer> map = mNameMap;
        return AODApplication.sInstance.getResources().getString(map.get(this.mPanel) != null ? map.get(this.mPanel).intValue() : R.string.animation_clock_panel);
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getIconMask() {
        return ColorPool.getClockColor(getColorName());
    }

    public String getPanel() {
        return this.mPanel;
    }

    public String getPanelORDefault() {
        return TextUtils.isEmpty(this.mPanel) ? "clock_panel_first" : this.mPanel;
    }

    @Override // com.miui.aod.common.StyleInfo
    public Map<String, Object> getParameterForAnalytic() {
        ArrayMap arrayMap = new ArrayMap();
        Map<String, String> map = sNameMap;
        arrayMap.put("style_name", map.getOrDefault(getPanel(), StyleInfo.DEFAULT_PARAMETER));
        arrayMap.put("style_event", map.getOrDefault(getPanel(), "wave_dial1"));
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    public StyleInfo.PresetStyle getPresetStyle() {
        return new StyleInfo.PresetStyle(R.string.clock_panel_icon, new String[]{CategoryConstants.DEFAULT_PARAM, "{\"panel\":\"clock_panel_second\",\"color\":\"light_blue\"}", "{\"panel\":\"clock_panel_third\",\"color\":\"light_orange\"}", "{\"panel\":\"clock_panel_forth\",\"color\":\"light_orange\"}"});
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
    public WaveClockStyleBean getThemeBean() {
        return this.mThemeStyleBean;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTitleResId() {
        return R.string.animation_clock_panel;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTranslationY(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.animation_clock_panel_translation_y);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean hasAnimation() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isSameHistoryRecord(StyleInfo styleInfo) {
        String panelORDefault;
        return (styleInfo instanceof AnimateClockPanelCategoryInfo) && (panelORDefault = getPanelORDefault()) != null && panelORDefault.equals(((AnimateClockPanelCategoryInfo) styleInfo).getPanelORDefault());
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setBg(View view, int i) {
        View viewFindViewById = view.findViewById(R.id.aod_bg);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(8);
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setThemeBean(ThemeBean themeBean) {
        this.mThemeStyleBean = (WaveClockStyleBean) themeBean;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportStepComponent() {
        return true;
    }
}
