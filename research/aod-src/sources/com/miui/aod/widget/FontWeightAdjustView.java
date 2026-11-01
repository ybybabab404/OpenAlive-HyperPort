package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.miui.aod.R;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.xiaomi.onetrack.util.ac;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class FontWeightAdjustView extends View {
    public int mBigPointCenterColor;
    public int mBigPointColor;
    public float mBigPointsRadius;
    public int mCurrentPointIndex;
    public FontWeightChangeListener mListener;
    public int mPointCount;
    public Paint mPointPaint;
    public float mPointsRadius;
    public final List mPointsXList;
    public float mPointsY;
    public int mScaledTouchSlop;
    public int mSmallPointColor;
    public float mTouchDownX;
    public String mViewText;

    public interface FontWeightChangeListener {
        void onWeightChange(int i);
    }

    public FontWeightAdjustView(Context context) {
        super(context);
        this.mPointCount = 6;
        this.mCurrentPointIndex = 1;
        this.mPointsXList = new ArrayList();
        init(null, 0);
    }

    public FontWeightAdjustView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPointCount = 6;
        this.mCurrentPointIndex = 1;
        this.mPointsXList = new ArrayList();
        init(attributeSet, 0);
    }

    public FontWeightAdjustView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPointCount = 6;
        this.mCurrentPointIndex = 1;
        this.mPointsXList = new ArrayList();
        init(attributeSet, i);
    }

    public final void findNearestPoint(MotionEvent motionEvent) {
        float f = 2.1474836E9f;
        int i = 0;
        for (int i2 = 0; i2 < this.mPointCount; i2++) {
            float fAbs = Math.abs(motionEvent.getX() - ((Float) this.mPointsXList.get(i2)).floatValue());
            if (fAbs < f) {
                i = i2;
                f = fAbs;
            }
        }
        if (i != this.mCurrentPointIndex) {
            this.mCurrentPointIndex = i;
            FontWeightChangeListener fontWeightChangeListener = this.mListener;
            if (fontWeightChangeListener != null) {
                fontWeightChangeListener.onWeightChange(isRtl() ? (this.mPointCount - 1) - this.mCurrentPointIndex : this.mCurrentPointIndex);
            }
            invalidate();
        }
    }

    public String getViewText() {
        return this.mViewText;
    }

    public final void init(AttributeSet attributeSet, int i) {
        this.mBigPointColor = getResources().getColor(R.color.font_size_view_big_color, null);
        this.mSmallPointColor = getResources().getColor(R.color.font_size_view_small_color, null);
        this.mBigPointCenterColor = getResources().getColor(R.color.font_size_view_big_center_color, null);
        this.mPointsRadius = getResources().getDimension(R.dimen.font_size_view_small_radius);
        this.mBigPointsRadius = getResources().getDimension(R.dimen.font_size_view_big_radius);
        Paint paint = new Paint();
        this.mPointPaint = paint;
        paint.setAntiAlias(true);
        this.mPointPaint.setStyle(Paint.Style.FILL);
        this.mPointPaint.setStrokeWidth(0.0f);
        this.mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() { // from class: com.miui.aod.widget.FontWeightAdjustView.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setClassName("android.widget.SeekBar");
                accessibilityNodeInfoCompat.setContentDescription(((Object) FontWeightAdjustView.this.getContentDescription()) + ac.b + FontWeightAdjustView.this.getViewText());
                accessibilityNodeInfoCompat.addAction(AutoBottomSheetKt.ALIGN_SCREEN_TOP);
                accessibilityNodeInfoCompat.addAction(AutoBottomSheetKt.ALIGN_SCREEN_START);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                if (i2 == 4096 && FontWeightAdjustView.this.mCurrentPointIndex < FontWeightAdjustView.this.mPointCount - 1) {
                    FontWeightAdjustView.this.mCurrentPointIndex++;
                } else if (i2 == 8192 && FontWeightAdjustView.this.mCurrentPointIndex > 0) {
                    FontWeightAdjustView.this.mCurrentPointIndex--;
                }
                if (FontWeightAdjustView.this.mListener != null) {
                    FontWeightAdjustView.this.mListener.onWeightChange(FontWeightAdjustView.this.isRtl() ? (FontWeightAdjustView.this.mPointCount - 1) - FontWeightAdjustView.this.mCurrentPointIndex : FontWeightAdjustView.this.mCurrentPointIndex);
                }
                view.sendAccessibilityEvent(4);
                FontWeightAdjustView.this.invalidate();
                return super.performAccessibilityAction(view, i2, bundle);
            }
        });
    }

    public final boolean isRtl() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < this.mPointCount; i++) {
            if (i == this.mCurrentPointIndex) {
                this.mPointPaint.setColor(this.mBigPointColor);
                canvas.drawCircle(((Float) this.mPointsXList.get(i)).floatValue(), this.mPointsY, this.mBigPointsRadius, this.mPointPaint);
                this.mPointPaint.setColor(this.mBigPointCenterColor);
                canvas.drawCircle(((Float) this.mPointsXList.get(i)).floatValue(), this.mPointsY, this.mPointsRadius, this.mPointPaint);
            } else {
                this.mPointPaint.setColor(this.mSmallPointColor);
                canvas.drawCircle(((Float) this.mPointsXList.get(i)).floatValue(), this.mPointsY, this.mPointsRadius, this.mPointPaint);
                setContentDescription(String.format(Locale.getDefault(), getContext().getString(R.string.battery_level), Integer.valueOf((int) (((this.mCurrentPointIndex * com.miui.maml.R.styleable.AppCompatTheme_textColorAlertDialogListItem) / 550) * 100.0f))));
            }
        }
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mPointsY = getHeight() / 2;
        float width = (getWidth() - getHeight()) / (this.mPointCount - 1);
        this.mPointsXList.clear();
        for (int i5 = 0; i5 < this.mPointCount; i5++) {
            this.mPointsXList.add(Float.valueOf((getHeight() / 2) + (i5 * width)));
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mTouchDownX = motionEvent.getX();
            findNearestPoint(motionEvent);
        } else if (action != 1) {
            if (action != 2) {
                return false;
            }
            if (Math.abs(motionEvent.getX() - this.mTouchDownX) > this.mScaledTouchSlop && getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            findNearestPoint(motionEvent);
        }
        return true;
    }

    public void setCurrentPointIndex(int i) {
        this.mCurrentPointIndex = i;
        if (isRtl()) {
            this.mCurrentPointIndex = (this.mPointCount - 1) - i;
        }
        invalidate();
    }

    public void setFontWeightChangeListener(FontWeightChangeListener fontWeightChangeListener) {
        this.mListener = fontWeightChangeListener;
    }

    public void setPointCount(int i) {
        if (i > 0) {
            this.mPointCount = i;
        }
    }

    public void setViewText(String str) {
        this.mViewText = str;
    }
}
