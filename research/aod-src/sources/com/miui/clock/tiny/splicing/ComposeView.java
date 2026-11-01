package com.miui.clock.tiny.splicing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.miui.clock.tiny.R;

/* JADX INFO: loaded from: classes.dex */
public class ComposeView extends View {
    public Drawable cameraAreaDrawable;
    public int cameraHeadColor;
    public int cameraHeadMarginLeft;
    public int cameraHeadMarginTop;
    public int camera_head_area_height;
    public int camera_head_area_width;
    public Drawable clockAreaBgDrawable;
    public int clockBgHeight;
    public int clockBgHeightRotation1;
    public int clockBgMarginLeftRotation1;
    public int clockBgMarginLeftRotation2;
    public Drawable darkPlaceDrawable;
    public int darkPlaceHeight;
    public int darkPlaceMarginLeft;
    public Context mContext;
    public int mRotation;
    public float mScale;
    public Paint paint;
    public int type;

    public ComposeView(Context context) {
        this(context, null);
    }

    public ComposeView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ComposeView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.paint = new Paint();
        this.cameraHeadColor = -65536;
        this.mRotation = 0;
        this.mContext = context;
    }

    @ColorInt
    public int getCameraHeadColor() {
        return this.cameraHeadColor;
    }

    public final float getDimen(int i) {
        return this.mContext.getResources().getDimensionPixelSize(i) * this.mScale;
    }

    public int getType() {
        return this.type;
    }

    public void init() {
        init(0);
    }

    public final void init(int i) {
        if (this.mScale <= 0.0f) {
            this.mScale = 1.0f;
        }
        if (this.mRotation == 0) {
            this.cameraAreaDrawable = ContextCompat.getDrawable(this.mContext, R.drawable.composeview_camera_area0);
            this.darkPlaceDrawable = ContextCompat.getDrawable(this.mContext, R.drawable.composeview_dark_place0);
            this.clockAreaBgDrawable = ContextCompat.getDrawable(this.mContext, R.drawable.composeview_clock_area_bg0);
            this.darkPlaceHeight = (int) getDimen(R.dimen.composeview_dark_place_height);
            this.camera_head_area_width = (int) getDimen(R.dimen.camera_head_area_width);
            this.camera_head_area_height = (int) getDimen(R.dimen.camera_head_area_height);
            this.clockBgMarginLeftRotation1 = (int) getDimen(R.dimen.composeview_clock_margin_left);
            this.clockBgHeightRotation1 = (int) getDimen(R.dimen.composeview_clock_bg_height);
            return;
        }
        this.cameraAreaDrawable = ContextCompat.getDrawable(this.mContext, R.drawable.composeview_camera_area2);
        this.darkPlaceDrawable = ContextCompat.getDrawable(this.mContext, R.drawable.composeview_dark_place2);
        this.clockAreaBgDrawable = ContextCompat.getDrawable(this.mContext, R.drawable.composeview_clock_area_bg2);
        int i2 = R.dimen.camera_head_margin_left;
        this.cameraHeadMarginLeft = (int) getDimen(i2);
        this.cameraHeadMarginTop = (int) getDimen(R.dimen.camera_head_margin_top);
        this.darkPlaceMarginLeft = (int) getDimen(R.dimen.composeview_dark_place_margin_left);
        this.clockBgMarginLeftRotation2 = (int) getDimen(i2);
        this.clockBgHeight = (int) getDimen(R.dimen.composeview_clockBgHeight);
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        init();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), this.paint, 31);
        if (this.mRotation == 0) {
            this.darkPlaceDrawable.setBounds(0, 0, getWidth(), this.darkPlaceHeight);
            this.darkPlaceDrawable.draw(canvas);
            this.cameraAreaDrawable.setBounds(0, 0, this.camera_head_area_width, this.camera_head_area_height);
            this.clockAreaBgDrawable.setBounds(this.clockBgMarginLeftRotation1, 0, getWidth(), this.clockBgHeightRotation1);
        } else {
            this.darkPlaceDrawable.setBounds(this.darkPlaceMarginLeft, 0, getWidth(), getHeight());
            this.darkPlaceDrawable.draw(canvas);
            this.cameraAreaDrawable.setBounds(this.cameraHeadMarginLeft, this.cameraHeadMarginTop, getWidth(), getHeight());
            this.clockAreaBgDrawable.setBounds(this.clockBgMarginLeftRotation2, 0, getWidth(), this.clockBgHeight);
        }
        this.cameraAreaDrawable.setColorFilter(this.cameraHeadColor, PorterDuff.Mode.SRC_IN);
        this.cameraAreaDrawable.draw(canvas);
        this.clockAreaBgDrawable.draw(canvas);
        canvas.restoreToCount(iSaveLayer);
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    public void setCameraHeadColor(@ColorInt int i) {
        this.cameraHeadColor = i;
        invalidate();
    }

    public void setCurOrientation(int i) {
        Log.d("ComposeView", "setCurOrientation");
    }

    public void setScale(float f) {
        this.mScale = f;
    }

    public void setType(int i) {
        this.type = i;
    }
}
