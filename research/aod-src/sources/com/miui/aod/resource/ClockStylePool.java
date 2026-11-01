package com.miui.aod.resource;

import androidx.collection.ArrayMap;
import com.miui.aod.R;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class ClockStylePool {
    public static final Map sMap;

    static {
        ArrayMap arrayMap = new ArrayMap();
        sMap = arrayMap;
        arrayMap.put("CLOCK_STYLE_STATIC_1", Integer.valueOf(R.drawable.aod_clock_panel));
        arrayMap.put("CLOCK_STYLE_STATIC_2", Integer.valueOf(R.drawable.aod_clock_panel_2));
        arrayMap.put("CLOCK_STYLE_STATIC_3", Integer.valueOf(R.drawable.aod_clock_panel_3));
        arrayMap.put("CLOCK_STYLE_STATIC_4", Integer.valueOf(R.drawable.aod_clock_panel_4));
        arrayMap.put("CLOCK_STYLE_STATIC_5", Integer.valueOf(R.drawable.aod_clock_panel_5));
        arrayMap.put("CLOCK_STYLE_STATIC_6", Integer.valueOf(R.drawable.aod_clock_panel_6));
        arrayMap.put("CLOCK_STYLE_STATIC_7", Integer.valueOf(R.drawable.aod_clock_panel_7));
    }

    public static Integer getClockPanelBgByStyleName(String str) {
        return (Integer) sMap.getOrDefault(str, Integer.valueOf(R.drawable.aod_clock_panel));
    }
}
