package com.miui.aod.widget;

import android.view.View;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.IPreAodView;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public interface IAodClock extends IPreAodView {
    default void bindView(View view) {
    }

    default int getClockGravity() {
        return 17;
    }

    default int getLayoutResource(StyleInfo styleInfo) {
        return -1;
    }

    default boolean handleUpdateNotificationIcons(IAodView iAodView) {
        return false;
    }

    default boolean isUpdatePositionBySelf() {
        return false;
    }

    default void onInflateBatteryComplete() {
    }

    default void onInflateComplete() {
    }

    default void preDestroyForChange() {
    }

    default void releaseDrawable() {
    }

    default void setClockMask(int i, int i2) {
    }

    default void setTimeZone(TimeZone timeZone) {
    }

    default void setTimeZone2(TimeZone timeZone) {
    }

    default void updateLayoutPosition(boolean z, boolean z2) {
    }

    default void updateTime(boolean z) {
    }
}
