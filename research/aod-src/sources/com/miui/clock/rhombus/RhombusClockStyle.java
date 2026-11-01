package com.miui.clock.rhombus;

import com.miui.clock.utils.DeviceConfig;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class RhombusClockStyle {

    @Retention(RetentionPolicy.SOURCE)
    public @interface ClockStyle {
    }

    public static float getTranslationScaleY() {
        if (DeviceConfig.isFlip() || DeviceConfig.FOLD_DEVICE) {
            return 0.016f;
        }
        return DeviceConfig.isPadDevice() ? 0.01f : 0.028f;
    }
}
