package com.miui.aod.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.miui.aod.R;
import com.miui.aod.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;

/* JADX INFO: loaded from: classes.dex */
public class RotateHorizontalLinearLayout extends LinearLayout {
    public Drawable mGradientOverlayDrawable;
    public boolean mIsV;

    public RotateHorizontalLinearLayout(Context context) {
        this(context, null);
    }

    public RotateHorizontalLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public RotateHorizontalLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.rotated_horizontal_view, 0, 0);
        this.mIsV = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        Canvas canvas2;
        int iSaveLayer;
        int iSaveLayer2 = -1;
        if (this.mGradientOverlayDrawable != null) {
            canvas2 = canvas;
            iSaveLayer = canvas2.saveLayer(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), null);
        } else {
            canvas2 = canvas;
            iSaveLayer = -1;
        }
        if (this.mIsV) {
            iSaveLayer2 = canvas2.saveLayer(new RectF(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight()), null);
            canvas2.rotate(90.0f, getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f);
        }
        super.dispatchDraw(canvas2);
        if (this.mIsV) {
            canvas2.restoreToCount(iSaveLayer2);
        }
        if (iSaveLayer > 0) {
            if (DeviceUtil.INSTANCE.isHighTextContrastEnabled(getContext())) {
                CommonUtils.setXfermodeForDrawable(this.mGradientOverlayDrawable, new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
            } else {
                CommonUtils.setXfermodeForDrawable(this.mGradientOverlayDrawable, new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            }
            this.mGradientOverlayDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            this.mGradientOverlayDrawable.draw(canvas2);
            canvas2.restoreToCount(iSaveLayer);
        }
    }

    public Drawable getGradientOverlayDrawable() {
        return this.mGradientOverlayDrawable;
    }

    public void init() {
        setOrientation(0);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mIsV) {
            setMeasuredDimension(View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824));
        }
    }

    public void setGradientOverlayDrawable(Drawable drawable) {
        this.mGradientOverlayDrawable = drawable;
    }

    public void setV(boolean z) {
        this.mIsV = z;
        invalidate();
    }
}
