package miuix.miuixbasewidget.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes2.dex */
class FabOvalShapeWithPadding extends OvalShape {
    public final WeakReference mViewRef;

    public FabOvalShapeWithPadding(View view) {
        this.mViewRef = new WeakReference(view);
    }

    @Override // android.graphics.drawable.shapes.OvalShape, android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public void draw(Canvas canvas, Paint paint) {
        View view = (View) this.mViewRef.get();
        if (view != null) {
            int width = view.getWidth();
            int height = view.getHeight();
            int paddingLeft = view.getPaddingLeft();
            int paddingTop = view.getPaddingTop();
            int paddingRight = (width - paddingLeft) - view.getPaddingRight();
            int paddingBottom = (height - paddingTop) - view.getPaddingBottom();
            canvas.drawCircle(paddingLeft + (paddingRight / 2.0f), paddingTop + (paddingBottom / 2.0f), Math.min(paddingRight, paddingBottom) / 2.0f, paint);
        }
    }
}
