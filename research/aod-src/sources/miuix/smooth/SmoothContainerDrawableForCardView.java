package miuix.smooth;

import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

/* JADX INFO: loaded from: classes2.dex */
public class SmoothContainerDrawableForCardView extends SmoothContainerDrawable2 {
    public RectF rectF = new RectF();
    public Path path = new Path();

    @Override // miuix.smooth.SmoothContainerDrawable2, android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        this.path.reset();
        Rect boundsInner = getBoundsInner();
        RectF rectF = this.rectF;
        rectF.left = boundsInner.left;
        rectF.top = boundsInner.top;
        rectF.right = boundsInner.right;
        rectF.bottom = boundsInner.bottom;
        this.path.addRoundRect(rectF, getCornerRadius(), getCornerRadius(), Path.Direction.CW);
        outline.setPath(this.path);
    }
}
