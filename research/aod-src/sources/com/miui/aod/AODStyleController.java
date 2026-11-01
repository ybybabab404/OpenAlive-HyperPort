package com.miui.aod;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.AodContainerView;
import com.miui.aod.widget.FlipILinkageClock;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.IAodView;
import com.miui.aod.widget.ILinkageClock;
import com.miui.aod.widget.OnClockColorChangeListener;
import com.miui.aod.widget.OneLineClock;
import com.miui.aod.widget.SunSelector;

/* JADX INFO: loaded from: classes.dex */
public class AODStyleController {
    public AodContainerView mAodContainerView;
    public final int mSize;
    public boolean mForAodNotification = false;
    public IAodClock mAodClock = null;

    public AODStyleController(int i) {
        this.mSize = i;
    }

    public static StyleInfo getStyleInfo(Context context) {
        StyleInfo styleInfo = getStyleInfo(context, Utils.getAodUsingCategoryName(context));
        return (!Utils.isUsingSuperWallpaperStyle(context) || styleInfo.supportSuperWallpaperMode()) ? styleInfo : getStyleInfo(context, CategoryConstants.CATEGORY_SUPER_WALLPAPER);
    }

    public static StyleInfo getStyleInfo(Context context, String str) {
        return CategoryFactory.createStyleInfo(context, str);
    }

    public static boolean isBigTimeStyle(Context context) {
        return CategoryConstants.CATEGORY_BIG_TIME.equals(Utils.getAodUsingCategoryName(context));
    }

    public int getClockGravity() {
        IAodClock iAodClock = this.mAodClock;
        if (iAodClock != null) {
            return iAodClock.getClockGravity();
        }
        return Integer.MAX_VALUE;
    }

    public boolean handleUpdateNotificationIcons(IAodView iAodView) {
        IAodClock iAodClock = this.mAodClock;
        return iAodClock != null && iAodClock.handleUpdateNotificationIcons(iAodView);
    }

    public void handleUpdateTime(boolean z) {
        AodContainerView aodContainerView = this.mAodContainerView;
        if (aodContainerView != null) {
            aodContainerView.handleUpdateTime(z);
        }
    }

    public IAodClock inflateView(View view) {
        return inflateView(view, Utils.getAodUsingCategoryName(view.getContext()));
    }

    public IAodClock inflateView(View view, StyleInfo styleInfo) {
        if (view instanceof AodContainerView) {
            this.mAodContainerView = (AodContainerView) view;
            styleInfo.setForAodNotification(this.mForAodNotification);
            this.mAodClock = this.mAodContainerView.update(styleInfo, this.mSize);
        }
        return this.mAodClock;
    }

    public IAodClock inflateView(View view, String str) {
        return inflateView(view, CategoryFactory.createStyleInfo(view.getContext(), str));
    }

    public IAodClock inflateView(View view, String str, String str2) {
        return inflateView(view, CategoryFactory.createStyleInfo(view.getContext(), str, str2));
    }

    public void releaseResources() {
        IAodClock iAodClock = this.mAodClock;
        if (iAodClock == null || !(iAodClock instanceof ILinkageClock)) {
            return;
        }
        ((ILinkageClock) iAodClock).releaseResources();
    }

    public void setClockViewVisible(boolean z) {
        this.mAodContainerView.setClockViewVisible(z);
    }

    public void setForAodNotification(boolean z) {
        this.mForAodNotification = z;
    }

    public void setSunImage(int i, View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.aod_bg);
        if (imageView != null) {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = view.getContext().getResources().getDimensionPixelOffset(R.dimen.sun_width);
            imageView.setLayoutParams(layoutParams);
            if (i < 0 || i >= SunSelector.getDrawableLength()) {
                return;
            }
            imageView.setImageResource(SunSelector.getSunImage(i));
        }
    }

    public void updateAodBgGravitiy() {
        View viewFindViewById = this.mAodContainerView.findViewById(R.id.aod_bg);
        IAodClock iAodClock = this.mAodClock;
        if (iAodClock != null && (iAodClock instanceof OneLineClock) && viewFindViewById != null && (viewFindViewById.getLayoutParams() instanceof FrameLayout.LayoutParams) && this.mSize == 2) {
            ((FrameLayout.LayoutParams) viewFindViewById.getLayoutParams()).gravity = 17;
        }
    }

    public void updateClockColor() {
        IAodClock iAodClock = this.mAodClock;
        if (iAodClock == null || !(iAodClock instanceof OnClockColorChangeListener)) {
            return;
        }
        ((OnClockColorChangeListener) iAodClock).updateClockColor(this.mSize);
    }

    public void updateClockOrientation() {
        IAodClock iAodClock = this.mAodClock;
        if (iAodClock == null || !(iAodClock instanceof FlipILinkageClock)) {
            return;
        }
        ((FlipILinkageClock) iAodClock).onRotationChanged();
    }
}
