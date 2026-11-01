package com.miui.aod.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.util.Consumer;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.keyguard.editor.utils.ViewUtil;

/* JADX INFO: loaded from: classes.dex */
public class OnelineClockView extends FrameLayout {
    public StyleInfo mStyleInfo;

    public static /* synthetic */ void $r8$lambda$XJZSyhLqB7H9iUYQ9lNPuDVYr2c(int i, TextView textView) {
        textView.setTextAppearance(i);
        textView.setTypeface(null);
    }

    /* JADX INFO: renamed from: $r8$lambda$zS0WUW7Om4SHz-mw0cDyrlzwgC8, reason: not valid java name */
    public static /* synthetic */ void m596$r8$lambda$zS0WUW7Om4SHzmw0cDyrlzwgC8(int i, TextView textView) {
        textView.setTextAppearance(i);
        textView.setTypeface(null);
    }

    public OnelineClockView(Context context) {
        super(context);
    }

    public OnelineClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setSize(int i) {
        final int i2;
        int i3;
        final int i4;
        if (i == 2 || i == 4) {
            i2 = R.style.Aod_date_s;
            i3 = R.style.Aod_oneline_s;
            i4 = R.style.Aod_oneline_time_s;
        } else {
            if (i != 1) {
                return;
            }
            i2 = R.style.Aod_date_oneline;
            i3 = R.style.Aod_oneline;
            i4 = R.style.Aod_oneline_time;
        }
        TextView textView = (TextView) findViewById(R.id.clock_horizontal);
        TextView textView2 = (TextView) findViewById(R.id.date);
        ViewUtil.doIfNotNull(textView, new Consumer() { // from class: com.miui.aod.widget.OnelineClockView$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((TextView) obj).setTextAppearance(i4);
            }
        });
        ViewUtil.doIfNotNull(textView2, new Consumer() { // from class: com.miui.aod.widget.OnelineClockView$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                OnelineClockView.m596$r8$lambda$zS0WUW7Om4SHzmw0cDyrlzwgC8(i2, (TextView) obj);
            }
        });
        ViewUtil.doIfNotNull((TextView) findViewById(R.id.date_lunar), new Consumer() { // from class: com.miui.aod.widget.OnelineClockView$$ExternalSyntheticLambda2
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                OnelineClockView.$r8$lambda$XJZSyhLqB7H9iUYQ9lNPuDVYr2c(i2, (TextView) obj);
            }
        });
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(i3, R.styleable.oneline);
        int dimension = (int) typedArrayObtainStyledAttributes.getDimension(3, 0.0f);
        final int dimension2 = (int) typedArrayObtainStyledAttributes.getDimension(4, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
        StyleInfo styleInfo = this.mStyleInfo;
        if (styleInfo != null && styleInfo.isForAodNotification()) {
            dimension = getContext().getResources().getDimensionPixelSize(R.dimen.aod_oneline_padding_top_special);
        }
        setPadding(0, dimension, 0, 0);
        ViewUtil.doIfNotNull(textView, new Consumer() { // from class: com.miui.aod.widget.OnelineClockView$$ExternalSyntheticLambda3
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((TextView) obj).setPadding(0, 0, 0, dimension2);
            }
        });
    }

    public void setStyleInfo(StyleInfo styleInfo) {
        this.mStyleInfo = styleInfo;
    }
}
