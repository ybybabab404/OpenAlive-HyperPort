package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.miui.aod.utils.CommonUtils;

/* JADX INFO: loaded from: classes.dex */
public class GradientTextView extends AppCompatTextView implements IGradientView {
    public final PorterDuffXfermode duffXferMode;
    public Drawable mGradientOverlayDrawable;

    public GradientTextView(Context context) {
        super(context);
        this.duffXferMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    public GradientTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.duffXferMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    public GradientTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.duffXferMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override // com.miui.aod.widget.IGradientView
    public Drawable getGradientOverlayDrawable() {
        return this.mGradientOverlayDrawable;
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), null);
        super.onDraw(canvas);
        Drawable drawable = this.mGradientOverlayDrawable;
        if (drawable != null) {
            CommonUtils.setXfermodeForDrawable(drawable, this.duffXferMode);
            this.mGradientOverlayDrawable.setBounds(0, 0, getWidth(), getHeight());
            this.mGradientOverlayDrawable.draw(canvas);
        }
        canvas.restoreToCount(iSaveLayer);
    }

    public void setGradientOverlayDrawable(Drawable drawable) {
        this.mGradientOverlayDrawable = drawable;
    }
}
