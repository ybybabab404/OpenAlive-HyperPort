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
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.OneLineClock;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class OnelineCategoryInfo extends CategoryInfo {
    private static final Map<String, String> sNameMap;

    @SerializedName("theme_style_info")
    private ImageStyleBean mThemeStyleBean;

    static {
        ArrayMap arrayMap = new ArrayMap();
        sNameMap = arrayMap;
        arrayMap.put("tree", "select_background1");
        arrayMap.put("spaceman", "select_background2");
        arrayMap.put("spirit", "select_background3");
        arrayMap.put("succulent", "select_background4");
        arrayMap.put("cactus", "select_background5");
        arrayMap.put("ghost", "select_background6");
    }

    public OnelineCategoryInfo() {
        super(CategoryConstants.CATEGORY_ONELINE);
        setBgCluster(new int[]{-1});
    }

    public static OnelineCategoryInfo newPlusCategoryInfo() {
        OnelineCategoryInfo onelineCategoryInfo = new OnelineCategoryInfo();
        onelineCategoryInfo.setChangeColorAlongTime(false);
        onelineCategoryInfo.setClockBgName("imageselector");
        onelineCategoryInfo.setBgCluster(new int[]{-1});
        ImageSelectorCategoryInfo imageSelectorCategoryInfo = new ImageSelectorCategoryInfo();
        imageSelectorCategoryInfo.setChangeColorAlongTime(false);
        imageSelectorCategoryInfo.setBgCluster(new int[]{-1});
        imageSelectorCategoryInfo.setExtraInfoShowStyle(1);
        onelineCategoryInfo.setRealStyleInfo(imageSelectorCategoryInfo);
        return onelineCategoryInfo;
    }

    @Override // com.miui.aod.common.StyleInfo
    public IAodClock createAodClock(boolean z, int i) {
        return (i == 2 || i == 4) ? new OneLineClock(i, false) : new OneLineClock(i, z);
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getDefaultClockBg() {
        return ImagePool.getImageRes(getDefaultClockBgName()).intValue();
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getDefaultClockBgName() {
        return "tree";
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getFullTitle() {
        int i;
        if (!TextUtils.isEmpty(this.mClockBg)) {
            String str = this.mClockBg;
            str.getClass();
            switch (str) {
                case "cactus":
                    i = R.string.oneline_clock_with_cactus;
                    break;
                case "spirit":
                    i = R.string.oneline_clock_with_spirit;
                    break;
                case "ghost":
                    i = R.string.oneline_clock_with_ghost;
                    break;
                case "succulent":
                    i = R.string.oneline_clock_with_succulent;
                    break;
                case "spaceman":
                    i = R.string.oneline_clock_with_spaceman;
                    break;
                default:
                    i = R.string.chosen_picture;
                    break;
            }
        } else {
            i = R.string.oneline_clock_with_forest;
        }
        return AODApplication.sInstance.getResources().getString(i);
    }

    @Override // com.miui.aod.common.StyleInfo
    public Map<String, Object> getParameterForAnalytic() {
        ArrayMap arrayMap = new ArrayMap();
        Map<String, String> map = sNameMap;
        arrayMap.put("style_name", map.getOrDefault(getClockBgName(), StyleInfo.DEFAULT_PARAMETER));
        arrayMap.put("style_event", map.getOrDefault(getClockBgName(), "select_background1"));
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getPreviewTopPaddingResId() {
        return R.dimen.aod_preview_default_top_padding;
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
        return R.string.chosen_picture;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTopMargin(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.icons_margin_top_oneline);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isSameHistoryRecord(StyleInfo styleInfo) {
        String clockBgName;
        return (styleInfo instanceof OnelineCategoryInfo) && (clockBgName = getClockBgName()) != null && clockBgName.equals(styleInfo.getClockBgName());
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setBg(View view, int i) {
        super.setBg(view, i);
        Context context = view.getContext();
        ImageView imageView = (ImageView) view.findViewById(R.id.aod_bg);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        if (i != 2) {
            if (i == 3 || i == 5) {
                layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.aod_bg_height);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return;
            }
            return;
        }
        int dimensionPixelSize = (int) (context.getResources().getDimensionPixelSize(R.dimen.category_item_small_time_bgsize) * CategoryConstants.SSMALL_RADIO);
        layoutParams.height = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        if (isForAodNotification()) {
            layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.aod_bg_height);
        }
        layoutParams.bottomMargin = (int) (context.getResources().getDimensionPixelSize(R.dimen.category_item_small_time_bg_bottommargin) * CategoryConstants.SSMALL_RADIO);
        layoutParams.gravity = 81;
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
