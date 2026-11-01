package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.util.DeviceInfo;
import com.miui.aod.widget.notification.NotificationContainer;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class SuperWallpaperClockView extends FrameLayout {
    public final float BOTTOM_THRESHOLD_RATE;
    public final float TOP_THRESHOLD_RATE;
    public boolean debug;
    public View mBattery;
    public int mBottomMax;
    public View mClock2Container;
    public TextView mClockCity;
    public TextView mClockCity2;
    public View mClockContainer;
    public TextView mClockDate;
    public TextView mClockTime;
    public TextView mClockTime2;
    public TextView mDateLunar;
    public View mIcons;
    public final Paint mPaint;
    public Point mTopAndBottom;
    public int mTopMin;
    public final float sRangeBottomRate;
    public final float sRangeTopRate;

    public static class LayoutChangeEvent {
        public final int mBottom;
        public final int mTop;

        public LayoutChangeEvent(int i, int i2) {
            this.mTop = i;
            this.mBottom = i2;
        }
    }

    public SuperWallpaperClockView(Context context) {
        super(context);
        this.mTopMin = -1;
        this.mBottomMax = -1;
        this.TOP_THRESHOLD_RATE = 0.21367522f;
        this.BOTTOM_THRESHOLD_RATE = 0.7863248f;
        this.debug = false;
        this.mPaint = new Paint(1);
        float dimensionPixelOffset = (1.0f - (AODApplication.sInstance.getResources().getDimensionPixelOffset(R.dimen.rangeRate) / DeviceInfo.getRealSize().y)) / 2.0f;
        this.sRangeTopRate = dimensionPixelOffset;
        this.sRangeBottomRate = 1.0f - dimensionPixelOffset;
    }

    public SuperWallpaperClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTopMin = -1;
        this.mBottomMax = -1;
        this.TOP_THRESHOLD_RATE = 0.21367522f;
        this.BOTTOM_THRESHOLD_RATE = 0.7863248f;
        this.debug = false;
        this.mPaint = new Paint(1);
        float dimensionPixelOffset = (1.0f - (AODApplication.sInstance.getResources().getDimensionPixelOffset(R.dimen.rangeRate) / DeviceInfo.getRealSize().y)) / 2.0f;
        this.sRangeTopRate = dimensionPixelOffset;
        this.sRangeBottomRate = 1.0f - dimensionPixelOffset;
    }

    public static void changeAnchor(View view, int i) {
        ((LinearLayout.LayoutParams) view.getLayoutParams()).gravity = i;
        if (view instanceof TextView) {
            ((TextView) view).setGravity(i);
        }
    }

    public void bindView() {
        this.mClockContainer = findViewById(R.id.single_clock_container);
        this.mClock2Container = findViewById(R.id.clock2_container);
        this.mClockCity = (TextView) this.mClockContainer.findViewById(R.id.city);
        this.mClockCity2 = (TextView) this.mClock2Container.findViewById(R.id.city2);
        this.mClockTime = (TextView) this.mClockContainer.findViewById(R.id.time);
        this.mClockTime2 = (TextView) this.mClock2Container.findViewById(R.id.time2);
        this.mClockDate = (TextView) this.mClockContainer.findViewById(R.id.date);
        this.mDateLunar = (TextView) this.mClockContainer.findViewById(R.id.date_lunar);
        this.mBattery = findViewById(R.id.battery_container);
        View viewFindViewById = findViewById(R.id.aod_icons_container);
        this.mIcons = viewFindViewById;
        if (viewFindViewById == null || !(viewFindViewById instanceof NotificationContainer)) {
            return;
        }
        ((NotificationContainer) viewFindViewById).setGravity(1);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        Canvas canvas2;
        if (this.debug) {
            this.mPaint.setColor(Color.parseColor("#34ff0000"));
            canvas2 = canvas;
            canvas2.drawRect(0.0f, this.mTopAndBottom.x, canvas.getWidth(), this.mTopAndBottom.y, this.mPaint);
            this.mPaint.setColor(Color.parseColor("#3400ff00"));
            canvas2.drawRect(0.0f, this.sRangeTopRate * canvas2.getHeight(), canvas2.getWidth(), this.sRangeBottomRate * canvas2.getHeight(), this.mPaint);
        } else {
            canvas2 = canvas;
        }
        super.dispatchDraw(canvas2);
    }

    public Point getTopAndBottom() {
        if (this.mTopAndBottom == null) {
            int i = DeviceInfo.getRealSize().y;
            this.mTopMin = (int) (i * 0.21367522f);
            this.mBottomMax = (int) (i * 0.7863248f);
            this.mTopAndBottom = new Point(this.mTopMin, this.mBottomMax);
        }
        return this.mTopAndBottom;
    }

    public final void notifyChildLayoutChangeIfNeeded() {
        int i = DeviceInfo.getRealSize().y;
        int i2 = (int) (i * 0.21367522f);
        int i3 = (int) (i * 0.7863248f);
        if (i2 == this.mTopMin && i3 == this.mBottomMax) {
            return;
        }
        this.mTopMin = i2;
        this.mBottomMax = i3;
        this.mTopAndBottom = new Point(this.mTopMin, this.mBottomMax);
        EventBus.getDefault().post(new LayoutChangeEvent(this.mTopMin, this.mBottomMax));
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mTopMin = -1;
        this.mBottomMax = -1;
        this.mTopAndBottom = null;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        notifyChildLayoutChangeIfNeeded();
    }

    public void setSize(int i) {
        float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.super_wallpaper_time_text_size);
        float dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.super_wallpaper_date_text_size);
        if (i == 2) {
            float smallViewWidth = Utils.getSmallViewWidth(getContext()) / 1080.0f;
            dimensionPixelSize *= smallViewWidth;
            dimensionPixelSize2 *= smallViewWidth;
        }
        this.mClockTime.setTextSize(0, dimensionPixelSize);
        this.mClockTime2.setTextSize(0, dimensionPixelSize);
        this.mClockDate.setTextSize(0, dimensionPixelSize2);
        this.mDateLunar.setTextSize(0, dimensionPixelSize2);
        this.mClockCity.setTextSize(0, dimensionPixelSize2);
        this.mClockCity2.setTextSize(0, dimensionPixelSize2);
    }

    public void updateClockPositionByTime(boolean z) {
        if (z) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mClockContainer.getLayoutParams();
            int i = layoutParams.rightMargin;
            if (i == 0) {
                i = layoutParams.leftMargin;
            }
            layoutParams.rightMargin = i;
            layoutParams.leftMargin = 0;
            layoutParams.gravity = 5;
            changeAnchor(this.mClockCity, 5);
            changeAnchor(this.mClockTime, 5);
            changeAnchor(this.mClockDate, 5);
            changeAnchor(this.mDateLunar, 5);
            changeAnchor(this.mBattery, 5);
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mClock2Container.getLayoutParams();
            int i2 = layoutParams2.leftMargin;
            if (i2 == 0) {
                i2 = layoutParams2.rightMargin;
            }
            layoutParams2.leftMargin = i2;
            layoutParams2.rightMargin = 0;
            layoutParams2.gravity = 3;
            changeAnchor(this.mClockCity2, 3);
            changeAnchor(this.mClockTime2, 3);
            return;
        }
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.mClockContainer.getLayoutParams();
        int i3 = layoutParams3.leftMargin;
        if (i3 == 0) {
            i3 = layoutParams3.rightMargin;
        }
        layoutParams3.leftMargin = i3;
        layoutParams3.rightMargin = 0;
        layoutParams3.gravity = 3;
        changeAnchor(this.mClockCity, 3);
        changeAnchor(this.mClockTime, 3);
        changeAnchor(this.mClockDate, 3);
        changeAnchor(this.mDateLunar, 3);
        changeAnchor(this.mBattery, 3);
        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) this.mClock2Container.getLayoutParams();
        int i4 = layoutParams4.rightMargin;
        if (i4 == 0) {
            i4 = layoutParams4.leftMargin;
        }
        layoutParams4.rightMargin = i4;
        layoutParams4.leftMargin = 0;
        layoutParams4.gravity = 5;
        changeAnchor(this.mClockCity2, 5);
        changeAnchor(this.mClockTime2, 5);
    }
}
