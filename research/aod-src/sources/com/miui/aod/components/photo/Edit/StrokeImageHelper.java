package com.miui.aod.components.photo.Edit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.miui.aod.R;

/* JADX INFO: loaded from: classes.dex */
public class StrokeImageHelper {
    private static final float BOARD_OFFSET = 0.5f;
    private final RectF mContentBounds = new RectF();
    private final Paint mStrokePaint;

    public StrokeImageHelper(Context context) {
        Paint paint = new Paint(1);
        this.mStrokePaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(context.getResources().getDimensionPixelSize(R.dimen.photo_editor_preview_stroke_width));
        paint.setColor(context.getResources().getColor(R.color.photo_editor_preview_stroke_color, null));
    }

    private void draw(Canvas canvas, Matrix matrix) {
        matrix.mapRect(this.mContentBounds);
        fixContentBounds();
        canvas.drawRect(this.mContentBounds, this.mStrokePaint);
    }

    private void fixContentBounds() {
        RectF rectF = this.mContentBounds;
        rectF.set(Math.round(rectF.left) + 0.5f, Math.round(this.mContentBounds.top) + 0.5f, Math.round(this.mContentBounds.right) - 0.5f, Math.round(this.mContentBounds.bottom) - 0.5f);
    }

    public void draw(Canvas canvas, Rect rect, Matrix matrix) {
        this.mContentBounds.set(rect);
        draw(canvas, matrix);
    }

    public void draw(Canvas canvas, RectF rectF) {
        this.mContentBounds.set(rectF);
        fixContentBounds();
        canvas.drawRect(this.mContentBounds, this.mStrokePaint);
    }

    public void draw(Canvas canvas, RectF rectF, Matrix matrix) {
        this.mContentBounds.set(rectF);
        draw(canvas, matrix);
    }
}
