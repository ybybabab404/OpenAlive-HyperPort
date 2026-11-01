package com.miui.clock.oversize.b;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import com.miui.clock.MiuiTextGlassView;

/* JADX INFO: loaded from: classes.dex */
public class NoPaddingTextView extends MiuiTextGlassView {
    public final Paint.FontMetricsInt fontMetricsInt;

    public NoPaddingTextView(Context context) {
        super(context);
        this.fontMetricsInt = new Paint.FontMetricsInt();
    }

    public NoPaddingTextView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.fontMetricsInt = new Paint.FontMetricsInt();
    }

    public NoPaddingTextView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.fontMetricsInt = new Paint.FontMetricsInt();
    }

    @Override // com.miui.clock.MiuiTextGlassView, android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        getPaint().getFontMetricsInt(this.fontMetricsInt);
        Paint.FontMetricsInt fontMetricsInt = this.fontMetricsInt;
        setPadding(getPaddingStart(), fontMetricsInt.ascent - fontMetricsInt.top, getPaddingEnd(), fontMetricsInt.bottom - fontMetricsInt.descent);
        super.onMeasure(i, i2);
    }
}
