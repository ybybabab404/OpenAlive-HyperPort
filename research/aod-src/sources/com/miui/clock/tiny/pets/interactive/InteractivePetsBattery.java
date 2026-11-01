package com.miui.clock.tiny.pets.interactive;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.widget.BatteryView;

/* JADX INFO: loaded from: classes.dex */
public class InteractivePetsBattery extends ConstraintLayout {
    public String TAG;
    public float designScale;
    public BatteryView mBatteryView;
    public ConstraintLayout mBatteryViewLayout;
    public Context mContext;
    public int mRotation;
    public int mType;

    public InteractivePetsBattery(Context context) {
        this(context, null);
    }

    public InteractivePetsBattery(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public InteractivePetsBattery(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "InteractivePetsBattery";
        this.mType = 0;
        this.designScale = 1.0f;
        this.mRotation = 0;
        this.mContext = context;
    }

    private int getDimen(int i) {
        return (int) (((double) (getResources().getDimensionPixelSize(i) * getDesignScale())) + 0.5d);
    }

    public View getBatteryView() {
        return this.mBatteryViewLayout;
    }

    public float getDesignScale() {
        return this.designScale;
    }

    public void initBatteryView(int i) {
        boolean z = i == 0;
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mBatteryViewLayout.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = (int) (getDimen(R.dimen.tiny_interactive_pets_battery_width) / this.designScale);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = (int) (getDimen(R.dimen.tiny_interactive_pets_battery_height) / this.designScale);
        layoutParams.leftToLeft = 0;
        layoutParams.topToTop = 0;
        if (z) {
            layoutParams.setMargins(getDimen(R.dimen.tiny_interactive_pets_battery_margin_left), getDimen(R.dimen.tiny_interactive_pets_battery_margin_top_0), 0, 0);
        } else {
            layoutParams.setMargins(getDimen(R.dimen.tiny_interactive_pets_battery_margin_left), getDimen(R.dimen.tiny_interactive_pets_battery_margin_top), 0, 0);
        }
        this.mBatteryViewLayout.setLayoutParams(layoutParams);
    }

    public void setBatteryColor(int i) {
        this.mBatteryView.setBatteryColor(i);
    }

    public void setDesignScale(float f) {
        this.designScale = f;
        initBatteryView(this.mRotation);
        this.mBatteryViewLayout.setPivotX(0.0f);
        this.mBatteryViewLayout.setPivotY(0.0f);
        this.mBatteryViewLayout.setScaleX(f);
        this.mBatteryViewLayout.setScaleY(f);
    }

    public void setNeedShowNormal(boolean z) {
        this.mBatteryView.setNeedShowNormal(z);
    }
}
