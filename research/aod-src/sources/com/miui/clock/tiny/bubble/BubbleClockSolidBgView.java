package com.miui.clock.tiny.bubble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public class BubbleClockSolidBgView extends View {
    public int endColor;
    public Paint paint;
    public RectF rectF;
    public int startColor;

    public BubbleClockSolidBgView(Context context) {
        super(context);
        init();
    }

    public BubbleClockSolidBgView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public BubbleClockSolidBgView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public final void init() {
        Paint paint = new Paint();
        this.paint = paint;
        paint.setAntiAlias(true);
        this.rectF = new RectF();
        setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.rectF.set(0.0f, 0.0f, getWidth(), getHeight());
        this.paint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, getHeight(), this.startColor, this.endColor, Shader.TileMode.CLAMP));
        canvas.drawRect(this.rectF, this.paint);
    }

    public void setGradientColors(int i, int i2) {
        this.startColor = i;
        this.endColor = i2;
        invalidate();
    }

    public void setStyle(int i) {
        if (i >= 6) {
            Log.w("BubbleClockSolidBgView", "style is out of range, style=" + i);
            return;
        }
        if (i == 2) {
            this.startColor = -1446913;
            this.endColor = -2894086;
        } else if (i == 3) {
            this.startColor = -3863;
            this.endColor = -8768;
        } else if (i == 4) {
            this.startColor = -2162692;
            this.endColor = -5380650;
        } else if (i == 5) {
            this.startColor = -2492673;
            this.endColor = -4528386;
        }
        invalidate();
    }
}
