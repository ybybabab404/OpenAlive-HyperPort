package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.miui.aod.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;

/* JADX INFO: loaded from: classes.dex */
public class GradientLinearLayout extends LinearLayout implements IGradientView {
    private Drawable mGradientOverlayDrawable;

    public GradientLinearLayout(Context context) {
        super(context);
    }

    public GradientLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GradientLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), null);
        super.dispatchDraw(canvas);
        if (this.mGradientOverlayDrawable != null) {
            if (DeviceUtil.INSTANCE.isHighTextContrastEnabled(getContext())) {
                CommonUtils.setXfermodeForDrawable(this.mGradientOverlayDrawable, new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
            } else {
                CommonUtils.setXfermodeForDrawable(this.mGradientOverlayDrawable, new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            }
            this.mGradientOverlayDrawable.setBounds(0, 0, getWidth(), getHeight());
            this.mGradientOverlayDrawable.draw(canvas);
        }
        canvas.restoreToCount(iSaveLayer);
    }

    @Override // com.miui.aod.widget.IGradientView
    public Drawable getGradientOverlayDrawable() {
        return this.mGradientOverlayDrawable;
    }

    public void setGradientOverlayDrawable(Drawable drawable) {
        if (this.mGradientOverlayDrawable != drawable) {
            this.mGradientOverlayDrawable = drawable;
            invalidate();
        }
    }
}
