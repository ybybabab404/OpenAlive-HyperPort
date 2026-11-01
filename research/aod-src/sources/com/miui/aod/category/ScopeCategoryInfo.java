package com.miui.aod.category;

import android.content.Context;
import android.view.View;
import androidx.collection.ArrayMap;
import com.google.gson.annotations.SerializedName;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.DescriptionAndImageStyleSelectView;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.ScopeClock;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ScopeCategoryInfo extends CategoryInfo {
    private static final Map<String, String> sNameMap;

    @SerializedName("num")
    protected int num;

    static {
        ArrayMap arrayMap = new ArrayMap();
        sNameMap = arrayMap;
        arrayMap.put("scope_a", "kaleidoscope1");
        arrayMap.put("scope_b", "kaleidoscope2");
        arrayMap.put("scope_c", "kaleidoscope3");
        arrayMap.put("scope_d", "kaleidoscope4");
        arrayMap.put("scope_e", "kaleidoscope5");
        arrayMap.put("scope_f", "kaleidoscope6");
        arrayMap.put("scope_g", "kaleidoscope7");
        arrayMap.put("scope_h", "kaleidoscope8");
    }

    public ScopeCategoryInfo() {
        super(CategoryConstants.CATEGORY_SCOPE);
        setBgCluster(new int[]{-1});
    }

    @Override // com.miui.aod.common.StyleInfo
    public IAodClock createAodClock(boolean z, int i) {
        return (i == 2 || i == 4) ? new ScopeClock(i, false, getClockBgName()) : new ScopeClock(i, z, getClockBgName());
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getDefaultClockBgName() {
        return "scope_a";
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getFullTitle() {
        String string = AODApplication.sInstance.getResources().getString(getTitleResId());
        if (this.num <= 0) {
            return string;
        }
        return string + this.num;
    }

    @Override // com.miui.aod.common.StyleInfo
    public long getInnerStartDelay() {
        return 100L;
    }

    public int getNum() {
        return this.num;
    }

    @Override // com.miui.aod.common.StyleInfo
    public Map<String, Object> getParameterForAnalytic() {
        ArrayMap arrayMap = new ArrayMap();
        Map<String, String> map = sNameMap;
        arrayMap.put("style_name", map.getOrDefault(getClockBgName(), StyleInfo.DEFAULT_PARAMETER));
        arrayMap.put("style_event", map.getOrDefault(getClockBgName(), "kaleidoscope1"));
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getPreviewTopPaddingResId() {
        return R.dimen.aod_preview_middle_top_padding;
    }

    @Override // com.miui.aod.common.StyleInfo
    public long getStartDelay() {
        return 300L;
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getStyleSelectViewName() {
        return DescriptionAndImageStyleSelectView.class.getName();
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTitleResId() {
        return R.string.scope_time;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTopMargin(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.icons_margin_top_oneline);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean hasAnimation() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isSameHistoryRecord(StyleInfo styleInfo) {
        String clockBgName;
        return (styleInfo instanceof ScopeCategoryInfo) && (clockBgName = getClockBgName()) != null && clockBgName.equals(styleInfo.getClockBgName());
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setBg(View view, int i) {
        View viewFindViewById = view.findViewById(R.id.aod_bg);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(8);
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportHideClock() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportStepComponent() {
        return true;
    }
}
