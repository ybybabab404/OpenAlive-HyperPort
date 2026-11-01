package com.miui.aod.widget;

import android.view.View;
import com.miui.aod.common.StyleInfo;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class ClockViewProxy implements IAodClock {
    public IAodClock mView;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        if (view instanceof IAodClock) {
            this.mView = (IAodClock) view;
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        return 0;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void onInflateBatteryComplete() {
        IAodClock iAodClock = this.mView;
        if (iAodClock != null) {
            iAodClock.onInflateBatteryComplete();
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setClockMask(int i, int i2) {
        IAodClock iAodClock = this.mView;
        if (iAodClock != null) {
            iAodClock.setClockMask(i, i2);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone(TimeZone timeZone) {
        IAodClock iAodClock = this.mView;
        if (iAodClock != null) {
            iAodClock.setTimeZone(timeZone);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone2(TimeZone timeZone) {
        IAodClock iAodClock = this.mView;
        if (iAodClock != null) {
            iAodClock.setTimeZone2(timeZone);
        }
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        IAodClock iAodClock = this.mView;
        if (iAodClock != null) {
            iAodClock.update(styleInfo, i);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        IAodClock iAodClock = this.mView;
        if (iAodClock != null) {
            iAodClock.updateTime(z);
        }
    }
}
