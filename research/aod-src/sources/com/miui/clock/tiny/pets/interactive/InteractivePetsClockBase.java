package com.miui.clock.tiny.pets.interactive;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockInfo;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.tiny.model.TinyMiuiBaseClock;
import com.miui.clock.tiny.widget.BatteryView;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class InteractivePetsClockBase extends TinyMiuiBaseClock {
    public InteractivePetsClockTimeBase clockView;
    public BatteryView mBatteryView;
    public ConstraintLayout mBatteryViewLayout;
    public InteractivePetsClockInfo mClockInfo;

    /* JADX INFO: renamed from: com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType;

        static {
            int[] iArr = new int[TinyClockViewType.values().length];
            $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType = iArr;
            try {
                iArr[TinyClockViewType.DATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.TIME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.WEEK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.BATTERY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.THIS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public InteractivePetsClockBase(Context context) {
        this(context, null);
    }

    public InteractivePetsClockBase(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public InteractivePetsClockBase(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public Calendar getClockCalendar() {
        return this.clockView.getClockCalendar();
    }

    public float getDesignScale() {
        return this.mClockInfo.getDesignWidthDp() / 372.0f;
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock
    public int getDimen(int i) {
        return (int) (((double) (getResources().getDimensionPixelSize(i) * getDesignScale())) + 0.5d);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public View getIClockView(TinyClockViewType tinyClockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[tinyClockViewType.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            return this.clockView.getIClockView(tinyClockViewType).findViewById(R.id.time_hour);
        }
        return i != 4 ? this : this.mBatteryView;
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public int getMarginAOD() {
        return 0;
    }

    public void initBatteryView() {
    }

    public void initClockView(int i) {
    }

    public void initColor() {
        BatteryView batteryView = this.mBatteryView;
        if (batteryView != null) {
            batteryView.setBatteryColor(-1);
        }
    }

    public void initSelf(int i) {
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.clockView = (InteractivePetsClockTimeBase) findViewById(R.id.clock_time);
        this.mBatteryView = (BatteryView) findViewById(R.id.battery);
        this.mBatteryViewLayout = (ConstraintLayout) findViewById(R.id.pets_battery);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock
    public void onLanguageChanged() {
        super.onLanguageChanged();
        InteractivePetsClockTimeBase interactivePetsClockTimeBase = this.clockView;
        if (interactivePetsClockTimeBase != null) {
            interactivePetsClockTimeBase.setClockStyle();
        }
        InteractivePetsClockInfo interactivePetsClockInfo = this.mClockInfo;
        if (interactivePetsClockInfo != null) {
            initClockView(interactivePetsClockInfo.getRotation());
        }
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setBatteryColor(int i) {
        BatteryView batteryView = this.mBatteryView;
        if (batteryView != null) {
            batteryView.setBatteryColor(i);
        }
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setBatteryLevel(int i) {
        BatteryView batteryView = this.mBatteryView;
        if (batteryView != null) {
            batteryView.setBatteryLevel(i);
        }
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setClockStyleInfo(TinyClockInfo tinyClockInfo) {
        super.setClockStyleInfo(tinyClockInfo);
        this.mClockInfo = (InteractivePetsClockInfo) tinyClockInfo;
        Log.d("PetsClockBase", "doodle clock change info " + this.mClockInfo.toString());
        this.clockView.setClockStyleInfo(this.mClockInfo);
        initColor();
        initClockView(this.mClockInfo.getRotation());
        initBatteryView();
        initSelf(this.mClockInfo.getRotation());
    }

    public void setDesignWidthDp(float f) {
        this.mClockInfo.setDesignWidthDp(f);
        setClockStyleInfo(this.mClockInfo);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTime() {
        this.clockView.updateTime();
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTimeZone(String str) {
        this.clockView.updateTimeZone(str);
    }
}
