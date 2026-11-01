package com.miui.aod.category;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.collection.ArrayMap;
import com.google.gson.annotations.SerializedName;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.PureColorAndImageStyleSelectView;
import com.miui.aod.resource.ImagePool;
import com.miui.aod.theme.beans.ImageStyleBean;
import com.miui.aod.theme.beans.ThemeBean;
import com.miui.aod.util.DeviceInfo;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.PaintVerticalClock;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PaintVerticalCategoryInfo extends CategoryInfo {
    private static final Map<String, String> sNameMap;

    @SerializedName("theme_style_info")
    private ImageStyleBean mThemeStyleBean;

    static {
        ArrayMap arrayMap = new ArrayMap();
        sNameMap = arrayMap;
        arrayMap.put("moonlight", "big_lock1");
        arrayMap.put("paint", "big_lock2");
        arrayMap.put("shadow", "big_lock3");
    }

    public PaintVerticalCategoryInfo() {
        super(CategoryConstants.CATEGORY_PAINT_VERTICAL);
        setBgCluster(new int[]{-1});
    }

    @Override // com.miui.aod.common.StyleInfo
    public IAodClock createAodClock(boolean z, int i) {
        return (i == 2 || i == 4) ? new PaintVerticalClock(i, false) : new PaintVerticalClock(i, z);
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getDefaultClockBg() {
        return ImagePool.getImageRes(getDefaultClockBgName()).intValue();
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getDefaultClockBgName() {
        return "moonlight";
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getFullTitle() {
        int i;
        if (TextUtils.isEmpty(this.mClockBg)) {
            i = R.string.vertical_clock_with_planet;
        } else {
            String str = this.mClockBg;
            str.getClass();
            if (str.equals("shadow")) {
                i = R.string.vertical_clock_with_shadow;
            } else {
                i = !str.equals("paint") ? R.string.vertical_clock : R.string.vertical_clock_with_paint;
            }
        }
        return AODApplication.sInstance.getResources().getString(i);
    }

    @Override // com.miui.aod.common.StyleInfo
    public Map<String, Object> getParameterForAnalytic() {
        ArrayMap arrayMap = new ArrayMap();
        Map<String, String> map = sNameMap;
        arrayMap.put("style_name", map.getOrDefault(getClockBgName(), StyleInfo.DEFAULT_PARAMETER));
        arrayMap.put("style_event", map.getOrDefault(getClockBgName(), "big_lock1"));
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getPreviewTopPaddingResId() {
        return R.dimen.aod_preview_middle_top_padding;
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getStyleSelectViewName() {
        return PureColorAndImageStyleSelectView.class.getName();
    }

    @Override // com.miui.aod.common.StyleInfo
    public ImageStyleBean getThemeBean() {
        return this.mThemeStyleBean;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTitleResId() {
        return R.string.large_time;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTopMargin(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.icons_margin_top_vertical);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isSameHistoryRecord(StyleInfo styleInfo) {
        String clockBgName;
        return (styleInfo instanceof PaintVerticalCategoryInfo) && (clockBgName = getClockBgName()) != null && clockBgName.equals(styleInfo.getClockBgName());
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setBg(View view, int i) {
        super.setBg(view, i);
        Context context = view.getContext();
        ImageView imageView = (ImageView) view.findViewById(R.id.aod_bg);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        if (i == 2) {
            int dimensionPixelSize = (int) (context.getResources().getDimensionPixelSize(R.dimen.category_item_small_time_bgsize) * CategoryConstants.SSMALL_RADIO);
            layoutParams.height = dimensionPixelSize;
            layoutParams.width = dimensionPixelSize;
            layoutParams.bottomMargin = (int) (context.getResources().getDimensionPixelSize(R.dimen.category_item_small_time_bg_bottommargin) * CategoryConstants.SSMALL_RADIO);
            layoutParams.gravity = 81;
            return;
        }
        layoutParams.topMargin = -DeviceInfo.dp2Px(55);
        if (i == 3 || i == 5) {
            layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.aod_bg_height);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setThemeBean(ThemeBean themeBean) {
        this.mThemeStyleBean = (ImageStyleBean) themeBean;
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
