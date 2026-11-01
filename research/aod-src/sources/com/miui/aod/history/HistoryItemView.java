package com.miui.aod.history;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.util.DeviceInfo;

/* JADX INFO: loaded from: classes.dex */
public class HistoryItemView extends FrameLayout {
    public static final float sCornerRadius = DeviceInfo.dp2Px(17);
    public boolean mCanBeChecked;
    public CheckBox mCheckBox;
    public boolean mEditMode;
    public Paint mMaskPaint;
    public Paint mPaint;
    public final RectF mRectF;

    public HistoryItemView(Context context) {
        this(context, null);
    }

    public HistoryItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HistoryItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRectF = new RectF();
        this.mCanBeChecked = true;
        this.mEditMode = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        Canvas canvas2;
        super.dispatchDraw(canvas);
        if (this.mPaint == null) {
            Paint paint = new Paint(1);
            this.mPaint = paint;
            paint.setColor(Color.parseColor("#262626"));
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth(Utils.getImageShowStrokeWidth());
        }
        float strokeWidth = this.mPaint.getStrokeWidth();
        Rect clipBounds = canvas.getClipBounds();
        float f = strokeWidth / 2.0f;
        this.mRectF.set(clipBounds.left + f, clipBounds.top + f, clipBounds.right - f, clipBounds.bottom - f);
        if (this.mCheckBox.isChecked()) {
            if (this.mMaskPaint == null) {
                Paint paint2 = new Paint(1);
                this.mMaskPaint = paint2;
                paint2.setColor(Color.parseColor("#4D000000"));
                this.mMaskPaint.setStyle(Paint.Style.FILL);
            }
            float f2 = clipBounds.left;
            float f3 = clipBounds.top;
            float f4 = clipBounds.right;
            float f5 = clipBounds.bottom;
            float f6 = sCornerRadius;
            canvas2 = canvas;
            canvas2.drawRoundRect(f2, f3, f4, f5, f6, f6, this.mMaskPaint);
        } else {
            canvas2 = canvas;
        }
        RectF rectF = this.mRectF;
        float f7 = sCornerRadius;
        canvas2.drawRoundRect(rectF, f7, f7, this.mPaint);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mCheckBox = (CheckBox) findViewById(R.id.checkbox);
        setEditMode(this.mEditMode, this.mCanBeChecked, true);
    }

    public void setCheckBoxState(boolean z) {
        this.mCheckBox.setChecked(z);
    }

    public void setEditMode(boolean z, boolean z2) {
        setEditMode(z, z2, false);
    }

    public void setEditMode(boolean z, boolean z2, boolean z3) {
        if (this.mEditMode == z && this.mCanBeChecked == z2 && !z3) {
            return;
        }
        this.mEditMode = z;
        this.mCanBeChecked = z2;
        int i = 0;
        this.mCheckBox.setVisibility((z && z2) ? 0 : 8);
        boolean z4 = this.mEditMode && !this.mCanBeChecked;
        while (true) {
            float f = 1.0f;
            if (i >= getChildCount()) {
                break;
            }
            View childAt = getChildAt(i);
            if (z4) {
                f = 0.4f;
            }
            childAt.setAlpha(f);
            i++;
        }
        Drawable foreground = getForeground();
        if (foreground != null) {
            foreground.setAlpha((int) ((z4 ? 0.4f : 1.0f) * 255.0f));
        }
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.mCheckBox.setOnCheckedChangeListener(onCheckedChangeListener);
    }
}
