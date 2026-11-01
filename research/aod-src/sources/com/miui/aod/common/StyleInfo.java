package com.miui.aod.common;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.gson.annotations.SerializedName;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.components.ApplyInstanceStyleSelectPresenter;
import com.miui.aod.components.BaseStyleSelectPresenter;
import com.miui.aod.components.view.PureColorAndImageStyleSelectView;
import com.miui.aod.components.view.ThemeStyleView;
import com.miui.aod.other.step.StepUtils;
import com.miui.aod.resource.ColorPool;
import com.miui.aod.resource.ImagePool;
import com.miui.aod.theme.beans.ImageStyleBean;
import com.miui.aod.theme.beans.ThemeBean;
import com.miui.aod.theme.utils.ThemeUtils;
import com.miui.aod.util.ImageUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.IAodClock;
import com.xiaomi.onetrack.api.bf;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class StyleInfo {
    private static final int BATTERY_MASK = 1;
    public static final int CLUSTER_SHOW_ALL = -2;
    public static final int CLUSTER_SHOW_NULL = -1;
    private static final int DATE_AND_TIME_MASK = 4;
    public static final String DEFAULT_PARAMETER = "default";
    private static final int LUNAR_CONFIG_MASK = 8;
    private static final int NOTIFICATION_MASK = 2;
    private static final int STEP_COMPONENT_MASK = 16;

    @SerializedName("attribute_flag")
    private int mAttributeFlag = 3;

    @SerializedName("bg_cluster")
    private int[] mBgCluster;

    @SerializedName("bg")
    protected String mClockBg;

    @SerializedName("color")
    private String mColorName;

    @SerializedName("covermode_styleinfo")
    private StyleInfo mCoverModeStyleInfo;
    private boolean mForAodNotification;

    @SerializedName("is_change_color_alongtime")
    private boolean mIsChangeColorAlongTime;

    @SerializedName("label")
    private String mLabel;

    @SerializedName(bf.a)
    private String mName;

    @SerializedName("real_styleinfo")
    private StyleInfo mRealStyleInfo;

    public static class PresetStyle {
        public String[] mStyleParameters;
        public int mTitleResId;

        public PresetStyle(int i, String[] strArr) {
            this.mTitleResId = i;
            this.mStyleParameters = strArr;
        }
    }

    public StyleInfo(String str) {
        this.mName = str;
    }

    public boolean compareToForPresetStyle(StyleInfo styleInfo) {
        return styleInfo != null && getClass() == styleInfo.getClass();
    }

    public IAodClock createAodClock(boolean z, int i) {
        return null;
    }

    public void dealAodContainerLayoutParam(ViewGroup.LayoutParams layoutParams, int i, Context context, boolean z) {
    }

    public void dealAodContentContainerLayoutParams(ViewGroup.LayoutParams layoutParams, int i) {
        layoutParams.height = -2;
        layoutParams.width = -2;
    }

    public void dealClockContentContainerLayoutParams(ViewGroup.LayoutParams layoutParams, int i) {
        layoutParams.height = -2;
        layoutParams.width = -2;
    }

    public void dealContentLayoutParams(ViewGroup.LayoutParams layoutParams, int i) {
        if (i != 4) {
            layoutParams.height = -2;
            layoutParams.width = -2;
        }
    }

    public boolean dealNotificationWidgetLayoutParams() {
        return false;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof StyleInfo)) {
            return false;
        }
        StyleInfo styleInfo = (StyleInfo) obj;
        if (TextUtils.isEmpty(styleInfo.getColorName()) || styleInfo.getColorName().equals(getColorName())) {
            return (TextUtils.isEmpty(getColorName()) || getColorName().equals(styleInfo.getColorName())) && styleInfo.getClockBgResID() == getClockBgResID();
        }
        return false;
    }

    public int getBatteryMask() {
        return getClockMask();
    }

    public int[] getBgCluster() {
        return this.mBgCluster;
    }

    public String getClockBgName() {
        return TextUtils.isEmpty(this.mClockBg) ? getDefaultClockBgName() : this.mClockBg;
    }

    public int getClockBgResID() {
        int iIntValue = ImagePool.getImageRes(getClockBgName()).intValue();
        return iIntValue != 0 ? iIntValue : getDefaultClockBg();
    }

    public int getClockColor() {
        return ColorPool.getClockColor(getColorName());
    }

    public int getClockGravity() {
        return 17;
    }

    public int getClockMask() {
        return this.mIsChangeColorAlongTime ? ColorPool.getGradientDrawableMask() : ColorPool.getClockMaskX(getColorName());
    }

    public String getColorName() {
        return this.mColorName;
    }

    public StyleInfo getCoverModeStyleInfo() {
        return this.mCoverModeStyleInfo;
    }

    public int getDefaultClockBg() {
        return 0;
    }

    public String getDefaultClockBgName() {
        return "";
    }

    public String getFullTitle() {
        return AODApplication.sInstance.getResources().getString(getTitleResId());
    }

    public int getIconMask() {
        return this.mIsChangeColorAlongTime ? ColorPool.getGradientDrawableMask() : ColorPool.getIconMask(getColorName());
    }

    public long getInnerStartDelay() {
        return 0L;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public String getName() {
        return this.mName;
    }

    public int getNotificationAnimTime() {
        return 2400;
    }

    public Map<String, Object> getParameterForAnalytic() {
        return null;
    }

    public PresetStyle getPresetStyle() {
        return null;
    }

    public int getPreviewTopPaddingResId() {
        return R.dimen.aod_preview_default_top_padding;
    }

    public StyleInfo getRealStyleInfo() {
        return this.mRealStyleInfo;
    }

    public long getStartDelay() {
        return 0L;
    }

    public final String getStyleSelectOrThemeViewName() {
        return getThemeBean() == null ? getStyleSelectViewName() : ThemeStyleView.class.getName();
    }

    public String getStyleSelectPresenterName(int i) {
        return i == 0 ? ApplyInstanceStyleSelectPresenter.class.getName() : BaseStyleSelectPresenter.class.getName();
    }

    public String getStyleSelectViewName() {
        return PureColorAndImageStyleSelectView.class.getName();
    }

    public ThemeBean getThemeBean() {
        return null;
    }

    public int getTitleResId() {
        return R.string.standard_style;
    }

    public int getTopMargin(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.icons_margin_top);
    }

    public int getTopMarginPreview(Context context) {
        return getTopMargin(context);
    }

    public int getTranslationY(Context context) {
        return 0;
    }

    public boolean hasAnimation() {
        return false;
    }

    public void initConfig(Context context) {
    }

    public boolean isBatterySwitchOn() {
        return supportBatteryIcon() && (this.mAttributeFlag & 1) != 0;
    }

    public boolean isChangeColorAlongTime() {
        return this.mIsChangeColorAlongTime;
    }

    public boolean isDateAndTimeSwitchOn() {
        return supportDateAndTime() && (this.mAttributeFlag & 4) != 0;
    }

    public boolean isForAodNotification() {
        return this.mForAodNotification;
    }

    public boolean isLeftGravity() {
        return false;
    }

    public boolean isLunarSwitchOn(Context context) {
        return supportLunarTime(context) && (this.mAttributeFlag & 8) != 0;
    }

    public boolean isNotificationSwitchOn() {
        return supportNotificationIcon() && (this.mAttributeFlag & 2) != 0;
    }

    public boolean isRightGravity() {
        return false;
    }

    public boolean isSameHistoryRecord(StyleInfo styleInfo) {
        if (getThemeBean() != null) {
            return getThemeBean().isSameHistoryRecord(styleInfo.getThemeBean());
        }
        String str = this.mName;
        return str != null && str.equals(styleInfo.getName());
    }

    public boolean isStepComponentSwitchOn() {
        return StepUtils.supportStepComponent() && supportStepComponent() && (this.mAttributeFlag & 16) != 0;
    }

    public boolean isValidForShow(Context context) {
        return true;
    }

    public boolean needInflateBattery(Context context) {
        return false;
    }

    public boolean needInflateNotification() {
        return true;
    }

    public boolean needSendNotificationCommand() {
        return false;
    }

    public boolean needSendUpdateTimeCommand() {
        return false;
    }

    public void onDeleteHistoryRecord(Context context) {
    }

    public void setBg(View view, int i) {
        Context context = AODApplication.sInstance;
        ImageView imageView = (ImageView) view.findViewById(R.id.aod_bg);
        if (imageView != null) {
            imageView.setVisibility(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
            if (i == 1) {
                layoutParams.width = context.getResources().getDimensionPixelOffset(R.dimen.aod_bg_width);
                layoutParams.height = context.getResources().getDimensionPixelOffset(R.dimen.aod_bg_height);
                layoutParams.topMargin = context.getResources().getDimensionPixelOffset(R.dimen.clock_container_margin_top_large);
            } else if (i == 2 || i == 6) {
                layoutParams.gravity = 17;
            }
            imageView.setLayoutParams(layoutParams);
            if (i == 1) {
                ((FrameLayout) view.findViewById(R.id.clock_container)).setPadding(imageView.getPaddingLeft(), 0, imageView.getPaddingRight(), imageView.getPaddingBottom());
            }
            if (getThemeBean() instanceof ImageStyleBean) {
                ImageStyleBean imageStyleBean = (ImageStyleBean) getThemeBean();
                imageView.setImageBitmap(ThemeUtils.getBitmap(context, imageStyleBean, imageStyleBean.mImg));
                return;
            }
            int clockBgResID = getClockBgResID();
            int i2 = i == 2 ? 1 : 0;
            if (clockBgResID != 0) {
                ImageUtils.loadIntoImage(imageView, clockBgResID, i2);
            }
        }
    }

    public void setBgCluster(int[] iArr) {
        this.mBgCluster = iArr;
    }

    public void setChangeColorAlongTime(boolean z) {
        this.mIsChangeColorAlongTime = z;
    }

    public void setClockBgName(String str) {
        this.mClockBg = str;
    }

    public void setColorName(String str) {
        this.mColorName = str;
    }

    public void setCoverModeStyleInfo(StyleInfo styleInfo) {
        this.mCoverModeStyleInfo = styleInfo;
    }

    public void setForAodNotification(boolean z) {
        this.mForAodNotification = z;
    }

    public void setLabel(String str) {
        this.mLabel = str;
    }

    public void setLunarSwitchOn(boolean z) {
        if (z) {
            this.mAttributeFlag |= 8;
        } else {
            this.mAttributeFlag &= -9;
        }
    }

    public void setRealStyleInfo(StyleInfo styleInfo) {
        this.mRealStyleInfo = styleInfo;
    }

    public void setThemeBean(ThemeBean themeBean) {
    }

    public boolean supportBatteryIcon() {
        return true;
    }

    public boolean supportChangeColorAlongTime() {
        return false;
    }

    public boolean supportDateAndTime() {
        return false;
    }

    public boolean supportHideClock() {
        return false;
    }

    public boolean supportLunarTime(Context context) {
        return AODSettings.supportShowLunarDate(context);
    }

    public boolean supportNotificationIcon() {
        return true;
    }

    public boolean supportStepComponent() {
        return false;
    }

    public boolean supportSuperWallpaperMode() {
        return false;
    }

    public void switchOnBatteryIcon(boolean z) {
        if (z) {
            this.mAttributeFlag |= 1;
        } else {
            this.mAttributeFlag &= -2;
        }
    }

    public void switchOnDateAndTime(boolean z) {
        if (z) {
            this.mAttributeFlag |= 4;
        } else {
            this.mAttributeFlag &= -5;
        }
    }

    public void switchOnNotificationIcon(boolean z) {
        if (z) {
            this.mAttributeFlag |= 2;
        } else {
            this.mAttributeFlag &= -3;
        }
    }

    public void switchOnStepComponent(boolean z) {
        if (z) {
            this.mAttributeFlag |= 16;
        } else {
            this.mAttributeFlag &= -17;
        }
    }

    public boolean usingNormalPositionStrategy() {
        return false;
    }
}
