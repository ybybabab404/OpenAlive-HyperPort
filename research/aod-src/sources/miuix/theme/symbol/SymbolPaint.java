package miuix.theme.symbol;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class SymbolPaint<T extends Paint> {
    public final Paint paint;
    public int[] state = null;
    public ColorStateList colorsList = null;

    public SymbolPaint(Paint paint) {
        this.paint = paint;
        paint.setAlpha(255);
    }

    public boolean applyState(int[] iArr) {
        this.state = iArr;
        int colorForCurrentState = getColorForCurrentState();
        int color = this.paint.getColor();
        this.paint.setColor(colorForCurrentState);
        return colorForCurrentState != color;
    }

    public int getColorForCurrentState() {
        ColorStateList colorStateList = this.colorsList;
        return getColorForCurrentState(colorStateList != null ? colorStateList.getDefaultColor() : 0);
    }

    public final int getColorForCurrentState(int i) {
        ColorStateList colorStateList = this.colorsList;
        return colorStateList != null ? colorStateList.getColorForState(this.state, i) : i;
    }

    public Paint getPaint() {
        return this.paint;
    }

    public void setAlpha(int i) {
        if (this.paint.getAlpha() != i) {
            this.paint.setAlpha(i);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("color=#");
        sb.append(Integer.toHexString(this.paint.getColor()));
        sb.append(", state=");
        int[] iArr = this.state;
        sb.append(iArr != null ? Arrays.toString(iArr) : "null");
        sb.append(", colorList=");
        sb.append(this.colorsList);
        return sb.toString();
    }
}
