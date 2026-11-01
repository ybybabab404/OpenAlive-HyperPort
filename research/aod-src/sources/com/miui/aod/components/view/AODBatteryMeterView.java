package com.miui.aod.components.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.miui.statusbar.BatteryMeterIconView;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.util.BatteryController;
import com.miui.aod.util.BatteryStatus;
import com.miui.aod.util.wakelock.DelayedWakeLock;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.widget.GradientLinearLayout;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class AODBatteryMeterView extends GradientLinearLayout implements BatteryController.BatteryStateChangeCallback {
    private BatteryController mBatteryController;
    private BatteryMeterIconView mBatteryIconView;
    private TextView mBatteryTextDigitView;
    private WakeLock mDrawWakelock;
    private int mLevel;

    public static class BatteryEventType {
    }

    public static /* synthetic */ void $r8$lambda$ybhHrOTYvOQk0z95C6VOj2_AQmg(AODBatteryMeterView aODBatteryMeterView) {
        aODBatteryMeterView.mBatteryTextDigitView.setText(String.format(aODBatteryMeterView.getResources().getString(Utils.isLayoutRtl() ? R.string.battery_level_rtl : R.string.battery_level), Integer.valueOf(aODBatteryMeterView.mLevel)));
        aODBatteryMeterView.invalidate();
    }

    public AODBatteryMeterView(Context context) {
        this(context, null, 0);
    }

    public AODBatteryMeterView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AODBatteryMeterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setOrientation(0);
        setGravity(8388627);
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mDrawWakelock = new DelayedWakeLock(new Handler(Looper.getMainLooper()), WakeLock.wrap(WakeLock.createDrawInner(getContext(), "AODBatteryMeterView")));
        BatteryController batteryController = BatteryController.getInstance(getContext());
        this.mBatteryController = batteryController;
        batteryController.addCallback(this);
    }

    @Override // com.miui.aod.util.BatteryController.BatteryStateChangeCallback
    public void onBatteryLevelChanged(BatteryStatus batteryStatus) {
        int level = batteryStatus.getLevel();
        boolean zIsCharging = batteryStatus.isCharging();
        this.mBatteryIconView.update(level, false, false, batteryStatus.isQuickCharge() || batteryStatus.isSuperQuickCharge() || batteryStatus.isSuperSuperQuickCharge(), batteryStatus.isCharging(), false);
        EventBus.getDefault().post(new BatteryEventType());
        if (level != this.mLevel) {
            this.mLevel = level;
            this.mBatteryIconView.setContentDescription(getContext().getString(zIsCharging ? R.string.accessibility_battery_level_charging : R.string.accessibility_battery_level, Integer.valueOf(level)));
            update();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BatteryController batteryController = this.mBatteryController;
        if (batteryController == null) {
            return;
        }
        batteryController.removeCallback(this);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBatteryIconView = (BatteryMeterIconView) findViewById(R.id.aod_battery_icon);
        this.mBatteryTextDigitView = (TextView) findViewById(R.id.aod_battery_digital);
        this.mBatteryIconView.setForThirdPart(true);
    }

    public void update() {
        this.mDrawWakelock.wrap(new Runnable() { // from class: com.miui.aod.components.view.AODBatteryMeterView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AODBatteryMeterView.$r8$lambda$ybhHrOTYvOQk0z95C6VOj2_AQmg(this.f$0);
            }
        }).run();
    }
}
