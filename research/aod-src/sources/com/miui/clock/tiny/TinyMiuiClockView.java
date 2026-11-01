package com.miui.clock.tiny;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.tiny.utils.ContextUtils;
import com.miui.clock.tiny.utils.DeviceConfig;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class TinyMiuiClockView extends FrameLayout {
    public Configuration currentConfiguration;
    public Context mContext;
    public TinyMiuiClockController mController;
    public boolean mIsAttachedToWindow;

    public @interface TinyClockRotation {
    }

    public TinyMiuiClockView(@NonNull Context context) {
        this(context, null);
    }

    public TinyMiuiClockView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        Configuration configuration = new Configuration();
        this.currentConfiguration = configuration;
        configuration.setTo(getResources().getConfiguration());
    }

    public void changeRotation(int i) {
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController == null || tinyMiuiClockController.getRotation() == i || i % 2 != 0) {
            return;
        }
        Log.d("TinyMiuiClockView", "change rotation from " + this.mController.getRotation() + " to " + i);
        this.mController.changeRotation(i);
    }

    public final TinyMiuiClockView createMiuiClockController(TinyClockBean tinyClockBean, int i, int i2) {
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController != null && this.mIsAttachedToWindow) {
            tinyMiuiClockController.onDetachedFromWindow();
            this.mController = null;
        }
        TinyMiuiClockController tinyMiuiClockController2 = new TinyMiuiClockController(this.mContext, this, tinyClockBean, i, i2);
        this.mController = tinyMiuiClockController2;
        tinyMiuiClockController2.build();
        if (this.mIsAttachedToWindow) {
            this.mController.onAttachedToWindow(i2);
        }
        return this;
    }

    public int getBlendColor() {
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController == null) {
            return -1;
        }
        tinyMiuiClockController.getClockStyleInfo().getBlendColor();
        return -1;
    }

    public TinyClockBean getClockBean() {
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController != null) {
            return tinyMiuiClockController.getClockBean();
        }
        return null;
    }

    public Calendar getClockCalendar() {
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController != null) {
            return tinyMiuiClockController.getClockCalendar();
        }
        return null;
    }

    public View getIClockView(TinyClockViewType tinyClockViewType) {
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController != null) {
            return tinyMiuiClockController.getIClockView(tinyClockViewType);
        }
        return null;
    }

    public int getMarginAOD() {
        Log.d("TinyMiuiClockViewDEBUG", "TinyMiuiClockView.getMarginAOD()");
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController != null) {
            return tinyMiuiClockController.getMarginAOD();
        }
        return 0;
    }

    public int getPrimaryColor() {
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController != null) {
            return tinyMiuiClockController.getClockStyleInfo().getPrimaryColor();
        }
        return -1;
    }

    public TinyMiuiClockView init(TinyClockBean tinyClockBean, int i) {
        return createMiuiClockController(tinyClockBean, i, i != 2 ? DeviceConfig.getRotationInCutout(this.mContext) : 0);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsAttachedToWindow = true;
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController == null || tinyMiuiClockController.getType() == 2) {
            return;
        }
        TinyMiuiClockController tinyMiuiClockController2 = this.mController;
        tinyMiuiClockController2.onAttachedToWindow(tinyMiuiClockController2.getRotation());
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean z = (this.currentConfiguration.updateFrom(configuration) & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0;
        if (this.mController == null) {
            Log.e("TinyMiuiClockView", "not init mController" + getClockBean());
            return;
        }
        Log.d("TinyMiuiClockView", "onConfigurationChanged PackageName: " + this.mContext.getPackageName() + " clock current DisplayMetrics --> " + getResources().getDisplayMetrics().toString() + " rotation: " + this.mController.getRotation() + " densityChanged " + z);
        if (z) {
            this.mController.refreshClockView();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIsAttachedToWindow = false;
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController == null || tinyMiuiClockController.getType() == 2) {
            return;
        }
        this.mController.onDetachedFromWindow();
    }

    public void setBatteryColor(int i) {
        Log.d("TinyMiuiClockViewDEBUG", "TinyMiuiClockView.setBatteryColor()");
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController != null) {
            tinyMiuiClockController.setBatteryColor(i);
        } else {
            Log.d("TinyMiuiClockView", "TinyMiuiClockController is null, set battery color failed.");
        }
    }

    public void setBatteryLevel(int i) {
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController == null || i < 0 || i > 100) {
            Log.d("TinyMiuiClockView", "set battery level failed.");
        } else {
            tinyMiuiClockController.setBatteryLevel(i);
        }
    }

    public void setGlobalContext(Context context) {
        if (context != null) {
            ContextUtils.sGlobalContext = context;
        }
    }

    public void setTinyMiuiClockViewListener(ITinyMiuiClockViewListener iTinyMiuiClockViewListener) {
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController == null || tinyMiuiClockController.getType() == 2) {
            return;
        }
        this.mController.setTinyMiuiClockViewListener(iTinyMiuiClockViewListener);
    }

    public void updateTime() {
        Log.d("TinyMiuiClockViewDEBUG", "TinyMiuiClockView.updateTime()");
        TinyMiuiClockController tinyMiuiClockController = this.mController;
        if (tinyMiuiClockController != null) {
            tinyMiuiClockController.updateTime();
        }
    }
}
