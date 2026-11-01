package com.miui.aod.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.miui.aod.R;

/* JADX INFO: loaded from: classes.dex */
public class SunClockView extends FrameLayout {
    public SunClockView(Context context) {
        super(context);
    }

    public SunClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setSize(int i) {
        int i2;
        int i3;
        TextView textView = (TextView) findViewById(R.id.clock_horizontal);
        if (i == 2) {
            textView.setLetterSpacing(1.0f);
            i3 = R.style.Aod_clock_sun_s;
            i2 = R.dimen.sun_paddingTop_s;
        } else {
            i2 = i == 1 ? R.dimen.sun_paddingTop_e : R.dimen.sun_paddingTop;
            i3 = R.style.Aod_clock_sun;
        }
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(i2);
        textView.setTextAppearance(i3);
        textView.setPadding(0, dimensionPixelOffset, 0, 0);
    }
}
