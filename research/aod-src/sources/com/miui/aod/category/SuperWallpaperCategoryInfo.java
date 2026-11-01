package com.miui.aod.category;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.collection.ArrayMap;
import com.google.gson.annotations.SerializedName;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.components.ApplyInstanceStyleSelectPresenter;
import com.miui.aod.components.view.SuperWallpaperStyleSelectView;
import com.miui.aod.util.DeviceInfo;
import com.miui.aod.util.WallPaperUtils;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.SuperWallpaperClock;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SuperWallpaperCategoryInfo extends CategoryInfo {

    @SerializedName("clock_position_x")
    private float mClockPositionX;

    @SerializedName("clock_position_y")
    private float mClockPositionY;

    @SerializedName("dual_clock_position_x_anchor_right")
    private float mDualClockPositionX;

    @SerializedName("dual_clock_position_y")
    private float mDualClockPositionY;

    @SerializedName("notification_position_y")
    private float mNotificationPositionY;

    @SerializedName("support_change_with_time")
    private int mSupportChangeWithTime;
    public final transient float sRangeBottomRate;
    public final transient float sRangeTopRate;

    public SuperWallpaperCategoryInfo() {
        super(CategoryConstants.CATEGORY_SUPER_WALLPAPER);
        float dimensionPixelOffset = (1.0f - (AODApplication.sInstance.getResources().getDimensionPixelOffset(R.dimen.rangeRate) / DeviceInfo.getRealSize().y)) / 2.0f;
        this.sRangeTopRate = dimensionPixelOffset;
        this.sRangeBottomRate = 1.0f - dimensionPixelOffset;
    }

    private float translateYPosition(float f) {
        float f2 = this.sRangeBottomRate;
        float f3 = this.sRangeTopRate;
        return (f * (f2 - f3)) + f3;
    }

    @Override // com.miui.aod.common.StyleInfo
    public IAodClock createAodClock(boolean z, int i) {
        return new SuperWallpaperClock(i);
    }

    @Override // com.miui.aod.common.StyleInfo
    public void dealAodContainerLayoutParam(ViewGroup.LayoutParams layoutParams, int i, Context context, boolean z) {
        if (i == 2 || !(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return;
        }
        ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(0, 0, 0, 0);
    }

    @Override // com.miui.aod.common.StyleInfo
    public void dealAodContentContainerLayoutParams(ViewGroup.LayoutParams layoutParams, int i) {
        layoutParams.height = -1;
        layoutParams.width = -1;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void dealClockContentContainerLayoutParams(ViewGroup.LayoutParams layoutParams, int i) {
        layoutParams.height = -1;
        layoutParams.width = -1;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void dealContentLayoutParams(ViewGroup.LayoutParams layoutParams, int i) {
        layoutParams.height = -1;
        layoutParams.width = -1;
    }

    public float getClockPositionX() {
        return this.mClockPositionX;
    }

    public float getClockPositionY() {
        return this.mClockPositionY;
    }

    public float getDualClockPositionX() {
        return this.mDualClockPositionX;
    }

    public float getDualClockPositionY() {
        return this.mDualClockPositionY;
    }

    public float getNotificationPositionY() {
        return this.mNotificationPositionY;
    }

    @Override // com.miui.aod.common.StyleInfo
    public Map<String, Object> getParameterForAnalytic() {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("style_name", CategoryConstants.CATEGORY_SUPER_WALLPAPER);
        arrayMap.put("style_event", CategoryConstants.CATEGORY_SUPER_WALLPAPER);
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getPreviewTopPaddingResId() {
        return R.dimen.aod_preview_super_wallpaper_top_padding;
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getStyleSelectPresenterName(int i) {
        return ApplyInstanceStyleSelectPresenter.class.getName();
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getStyleSelectViewName() {
        return SuperWallpaperStyleSelectView.class.getName();
    }

    public int getSupportChangeWithTime() {
        return this.mSupportChangeWithTime;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTitleResId() {
        return R.string.deep_space_planet;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTopMargin(Context context) {
        return 0;
    }

    public boolean isInValid() {
        return this.mClockPositionX <= 0.0f && this.mClockPositionY <= 0.0f && this.mDualClockPositionX <= 0.0f && this.mDualClockPositionY <= 0.0f;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needInflateBattery(Context context) {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needInflateNotification() {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setBg(View view, int i) {
        float fTranslateYPosition;
        int i2;
        super.setBg(view, i);
        Context context = view.getContext();
        ImageView imageView = (ImageView) view.findViewById(R.id.aod_bg);
        if (imageView != null) {
            Point realSize = DeviceInfo.getRealSize();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
            imageView.setVisibility(8);
            if (i == 2) {
                marginLayoutParams.width = Utils.getSmallViewWidth(context);
                marginLayoutParams.height = context.getResources().getDimensionPixelOffset(R.dimen.aod_sun_bg_height_middle);
            } else {
                marginLayoutParams.width = realSize.x;
                marginLayoutParams.height = realSize.y;
            }
            imageView.setLayoutParams(marginLayoutParams);
            View view2 = (View) imageView.getParent();
            ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
            View viewFindViewById = view2.findViewById(R.id.single_clock_container);
            View viewFindViewById2 = view2.findViewById(R.id.clock2_container);
            LinearLayout linearLayout = (LinearLayout) view2.findViewById(R.id.icons).getParent();
            linearLayout.getLayoutParams().width = -1;
            if (i == 2) {
                imageView.setVisibility(0);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageIcon(WallPaperUtils.getWallpaperPreview());
                int i3 = (int) (marginLayoutParams.width * ((realSize.y * 1.0f) / realSize.x));
                int smallViewHeight = (i3 - Utils.getSmallViewHeight(view.getContext())) / 2;
                float f = i3;
                ((ViewGroup.MarginLayoutParams) viewFindViewById.getLayoutParams()).setMargins(0, ((int) (translateYPosition(getClockPositionY()) * f)) - smallViewHeight, (int) ((1.0f - getClockPositionX()) * marginLayoutParams.width), 0);
                ((ViewGroup.MarginLayoutParams) viewFindViewById2.getLayoutParams()).setMargins((int) (getDualClockPositionX() * marginLayoutParams.width), ((int) (translateYPosition(getDualClockPositionY()) * f)) - smallViewHeight, 0, 0);
                return;
            }
            view2.setPadding(0, 0, 0, 0);
            if (layoutParams != null) {
                layoutParams.width = marginLayoutParams.width;
                layoutParams.height = marginLayoutParams.height;
            }
            ((ViewGroup.MarginLayoutParams) viewFindViewById.getLayoutParams()).setMargins(0, (int) (translateYPosition(getClockPositionY()) * marginLayoutParams.height), (int) ((1.0f - getClockPositionX()) * marginLayoutParams.width), 0);
            ((ViewGroup.MarginLayoutParams) viewFindViewById2.getLayoutParams()).setMargins((int) (getDualClockPositionX() * marginLayoutParams.width), (int) (translateYPosition(getDualClockPositionY()) * marginLayoutParams.height), 0, 0);
            if (getNotificationPositionY() != 0.0f) {
                fTranslateYPosition = translateYPosition(getNotificationPositionY());
                i2 = marginLayoutParams.height;
            } else {
                fTranslateYPosition = translateYPosition(1.0f);
                i2 = marginLayoutParams.height;
            }
            ((ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams()).setMargins(0, (int) (fTranslateYPosition * i2), 0, 0);
        }
    }

    public void setData(float f, float f2, float f3, float f4, int i) {
        this.mClockPositionX = f;
        this.mClockPositionY = f2;
        this.mDualClockPositionX = f3;
        this.mDualClockPositionY = f4;
        this.mSupportChangeWithTime = i;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean usingNormalPositionStrategy() {
        return true;
    }
}
