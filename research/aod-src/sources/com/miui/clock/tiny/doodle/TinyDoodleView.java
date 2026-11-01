package com.miui.clock.tiny.doodle;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class TinyDoodleView extends View {
    public int bgColor;
    public int curLanguage;
    public int curOrientation;
    public int filamentColor;
    public int mDensityDpi;
    public Paint paint;
    public int textColor;
    public TinyDoodleViewCache tinyDoodleViewCache;

    public TinyDoodleView(Context context) {
        this(context, null);
    }

    public TinyDoodleView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TinyDoodleView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.bgColor = -16711936;
        this.filamentColor = -16776961;
        this.textColor = -65536;
        this.paint = new Paint();
        this.curLanguage = 1;
        this.curOrientation = 0;
        this.mDensityDpi = context.getResources().getDisplayMetrics().densityDpi;
    }

    private Drawable getBgDrawable() {
        TinyDoodleViewCache tinyDoodleViewCache = this.tinyDoodleViewCache;
        if (tinyDoodleViewCache == null || !tinyDoodleViewCache.isInit()) {
            return null;
        }
        return this.tinyDoodleViewCache.getBgDrawable(this.curOrientation);
    }

    private Drawable getFilament() {
        TinyDoodleViewCache tinyDoodleViewCache = this.tinyDoodleViewCache;
        if (tinyDoodleViewCache == null || !tinyDoodleViewCache.isInit()) {
            return null;
        }
        return this.tinyDoodleViewCache.getFilament(this.curOrientation);
    }

    private Drawable getTextDrawable() {
        TinyDoodleViewCache tinyDoodleViewCache = this.tinyDoodleViewCache;
        if (tinyDoodleViewCache == null || !tinyDoodleViewCache.isInit()) {
            return null;
        }
        return this.tinyDoodleViewCache.getTextBg(this.curOrientation, getCurLanguage());
    }

    public void drawToCanvas(Canvas canvas, int i, int i2) {
        TinyDoodleViewCache tinyDoodleViewCache = this.tinyDoodleViewCache;
        if (tinyDoodleViewCache == null || !tinyDoodleViewCache.isInit()) {
            StringBuilder sb = new StringBuilder();
            sb.append("onDraw: tinyDoodleViewCache == null is");
            sb.append(this.tinyDoodleViewCache == null);
            Log.i("TinyMiuiClockView.TinyDoodleView", sb.toString());
            return;
        }
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, i, i2, this.paint, 31);
        Drawable bgDrawable = getBgDrawable();
        if (bgDrawable != null) {
            bgDrawable.setBounds(0, 0, i, i2);
            bgDrawable.setColorFilter(this.bgColor, PorterDuff.Mode.SRC_IN);
            bgDrawable.draw(canvas);
        }
        Drawable textDrawable = getTextDrawable();
        if (textDrawable != null) {
            textDrawable.setBounds(0, 0, i, i2);
            textDrawable.setColorFilter(this.textColor, PorterDuff.Mode.SRC_IN);
            textDrawable.draw(canvas);
        }
        Drawable filament = getFilament();
        if (filament != null) {
            filament.setBounds(0, 0, i, i2);
            filament.setColorFilter(this.filamentColor, PorterDuff.Mode.SRC_IN);
            filament.draw(canvas);
        }
        canvas.restoreToCount(iSaveLayer);
    }

    public int getBgColor() {
        return this.bgColor;
    }

    public int getCurLanguage() {
        return this.curLanguage;
    }

    public int getCurOrientation() {
        return this.curOrientation;
    }

    public int getFilamentColor() {
        return this.filamentColor;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public TinyDoodleViewCache getTinyDoodleViewCache() {
        return this.tinyDoodleViewCache;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        Log.d("TinyMiuiClockView.TinyDoodleView", "onConfigurationChanged: ");
        super.onConfigurationChanged(configuration);
        int i = configuration.densityDpi;
        if (this.mDensityDpi != i) {
            this.mDensityDpi = i;
            invalidate();
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawToCanvas(canvas, getWidth(), getHeight());
    }

    public void setBgColor(int i) {
        this.bgColor = i;
        invalidate();
    }

    public void setCurLanguage(@IntRange int i) {
        this.curLanguage = i;
        invalidate();
    }

    public void setFilamentColor(int i) {
        this.filamentColor = i;
        invalidate();
    }

    public void setStyle(int i) {
        DoodleStyle.TinyDoodleViewStyle[] tinyDoodleViewStyleArr = DoodleStyle.doodleViewColorStyles;
        int length = i % tinyDoodleViewStyleArr.length;
        setBgColor(tinyDoodleViewStyleArr[length].getBgColor());
        setFilamentColor(tinyDoodleViewStyleArr[length].getFilamentColor());
        setTextColor(tinyDoodleViewStyleArr[length].getTextColor());
        setCurLanguage(tinyDoodleViewStyleArr[length].getLanguage());
    }

    public void setTextColor(int i) {
        this.textColor = i;
        invalidate();
    }

    public void setTinyDoodleViewCache(TinyDoodleViewCache tinyDoodleViewCache) {
        this.tinyDoodleViewCache = tinyDoodleViewCache;
        if (tinyDoodleViewCache != null) {
            invalidate();
        }
    }
}
