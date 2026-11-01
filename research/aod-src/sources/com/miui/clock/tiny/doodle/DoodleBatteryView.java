package com.miui.clock.tiny.doodle;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.clock.tiny.widget.BatteryView;

/* JADX INFO: loaded from: classes.dex */
public class DoodleBatteryView extends ConstraintLayout {
    public String TAG;
    public TextView batteryBorderView;
    public GradientDrawable batteryBorderViewDrawable;
    public boolean dark;
    public boolean dark180;
    public float designScale;
    public BatteryView mBatteryView;
    public TinyClockBean mBean;
    public Context mContext;
    public int mDensityDpi;
    public int mRotation;
    public int mType;
    public boolean needInverseColor;

    public DoodleBatteryView(Context context) {
        super(context, null);
        this.TAG = "DoodleBatteryView";
        this.mType = 0;
        this.mRotation = 0;
        this.designScale = 1.0f;
        this.needInverseColor = false;
        this.mDensityDpi = context.getResources().getDisplayMetrics().densityDpi;
        this.mContext = context;
    }

    public DoodleBatteryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "DoodleBatteryView";
        this.mType = 0;
        this.mRotation = 0;
        this.designScale = 1.0f;
        this.needInverseColor = false;
        this.mContext = context;
    }

    private int getDimen(int i) {
        return (int) (((double) (getResources().getDimensionPixelSize(i) * getDesignScale())) + 0.5d);
    }

    private void initBatteryView(int i) {
        if (this.mBatteryView == null) {
            Log.w(this.TAG, "please init doodle BatteryView");
            return;
        }
        boolean z = i != 2;
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.batteryBorderView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_doodle_battery_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_doodle_battery_height);
        layoutParams.leftToLeft = 0;
        layoutParams.topToTop = 0;
        layoutParams.setMargins(getDimen(z ? R.dimen.tiny_doodle_rotation_0_battery_margin_left : R.dimen.tiny_doodle_rotation_180_battery_margin_left), getDimen(z ? R.dimen.tiny_doodle_rotation_0_battery_margin_top : R.dimen.tiny_doodle_rotation_180_battery_margin_top), 0, 0);
        this.batteryBorderView.setLayoutParams(layoutParams);
        this.mBatteryView.setScale(1.0f);
        this.mBatteryView.setScaleX(getDesignScale());
        this.mBatteryView.setScaleY(getDesignScale());
    }

    public float getDesignScale() {
        return this.designScale;
    }

    public final void initColor(int i) {
        int color = Color.parseColor("#FFEBEAE8");
        int color2 = Color.parseColor("#FF1E1E1E");
        boolean z = this.mRotation == 0 ? this.dark : this.dark180;
        if (!this.needInverseColor ? i == 1 || i == 4 || i == 7 : !z) {
            color = color2;
        }
        this.batteryBorderViewDrawable.setStroke(Math.max(1, getDimen(R.dimen.tiny_doodle_battery_border_stroke_width)), color);
        this.mBatteryView.setBatteryColor(color);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = configuration.densityDpi;
        if (this.mDensityDpi != i) {
            this.mDensityDpi = i;
            updateLayout();
        }
    }

    public void setDark(boolean z) {
        this.dark = z;
    }

    public void setDark180(boolean z) {
        this.dark180 = z;
    }

    public void setNeedInverseColor(boolean z) {
        this.needInverseColor = z;
    }

    public void setNeedShowNormal(boolean z) {
        this.mBatteryView.setNeedShowNormal(z);
    }

    public void setStyle(int i) {
        initColor(i);
    }

    public void setType(int i) {
        this.mBatteryView.setVisibility(1 == i ? 8 : 0);
        this.batteryBorderView.setVisibility(1 == i ? 8 : 0);
    }

    public void updateLayout() {
        TinyClockBean tinyClockBean;
        if (this.mBatteryView == null || (tinyClockBean = this.mBean) == null) {
            Log.w(this.TAG, "please init  BatteryView");
            return;
        }
        initColor(tinyClockBean.getStyle());
        if (this.mType != 2) {
            initBatteryView(this.mRotation);
        } else {
            initBatteryView(this.mRotation);
            this.mBatteryView.setNeedShowNormal(true);
        }
    }
}
