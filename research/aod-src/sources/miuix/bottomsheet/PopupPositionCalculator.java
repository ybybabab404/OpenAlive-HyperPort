package miuix.bottomsheet;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import miuix.core.util.MiuixUIUtils;

/* JADX INFO: loaded from: classes2.dex */
public class PopupPositionCalculator {

    public static class PopupPosition {
        public float adjustedHeight;
        public float adjustedWidth;
        public int direction;
        public boolean heightAdjusted;
        public boolean widthAdjusted;
        public float x;
        public float y;

        public PopupPosition(float f, float f2, int i, float f3, float f4, boolean z, boolean z2) {
            this.x = f;
            this.y = f2;
            this.direction = i;
            this.adjustedWidth = f3;
            this.adjustedHeight = f4;
            this.widthAdjusted = z;
            this.heightAdjusted = z2;
        }
    }

    public static class Rect {
        public float centerX;
        public float centerY;
        public float height;
        public float maxX;
        public float maxY;
        public float minX;
        public float minY;
        public float width;
    }

    public PopupPosition calculate(View view, View view2, View view3, int i, int i2, int i3) {
        boolean z;
        float f;
        Context context = view.getContext();
        float fDp2px = MiuixUIUtils.dp2px(context, i);
        float fDp2px2 = MiuixUIUtils.dp2px(context, i2);
        float fDp2px3 = MiuixUIUtils.dp2px(context, i3);
        Rect viewRect = getViewRect(view);
        Rect safeRect = getSafeRect(view3);
        float measuredHeight = view2.getMeasuredHeight();
        float f2 = viewRect.minX;
        float f3 = safeRect.minX;
        float f4 = ((f2 - f3) - fDp2px2) - fDp2px3;
        float f5 = safeRect.maxX;
        float f6 = viewRect.maxX;
        int i4 = ((f5 - f6) - fDp2px2) - fDp2px3 >= f4 ? 8388613 : 8388611;
        float f7 = i4 == 8388613 ? f6 + fDp2px3 : (f2 - fDp2px3) - fDp2px;
        float f8 = f3 + fDp2px2;
        float f9 = (f5 - fDp2px2) - fDp2px;
        if (f7 < f8) {
            Log.w("PopupPositionCalculator", String.format("Popup exceeds left boundary by %.0fpx. Adjusted X position from %.0fpx to %.0fpx", Float.valueOf(f8 - f7), Float.valueOf(f7), Float.valueOf(f8)));
            f9 = f8;
        } else if (f7 > f9) {
            Log.w("PopupPositionCalculator", String.format("Popup exceeds right boundary by %.0fpx. Adjusted X position from %.0fpx to %.0fpx", Float.valueOf(f7 - f9), Float.valueOf(f7), Float.valueOf(f9)));
        } else {
            f9 = f7;
        }
        float fClamp = clamp(viewRect.centerY - (measuredHeight / 2.0f), safeRect.minY + fDp2px2, (safeRect.maxY - fDp2px2) - measuredHeight);
        float f10 = safeRect.height;
        float f11 = 2.0f * fDp2px2;
        if (measuredHeight > f10 - f11) {
            float f12 = f10 - f11;
            fClamp = safeRect.minY + fDp2px2;
            Log.w("PopupPositionCalculator", String.format("Popup height (%.0fpx) exceeds available height (%.0fpx). Adjusted to %.0fpx", Float.valueOf(measuredHeight), Float.valueOf(safeRect.height - f11), Float.valueOf(f12)));
            f = f12;
            z = true;
        } else {
            z = false;
            f = measuredHeight;
        }
        return new PopupPosition(f9, fClamp, i4, fDp2px, f, false, z);
    }

    public PopupPosition calculateTransform(View view, View view2, View view3, int i, int i2, int i3) {
        boolean z;
        float f;
        Context context = view.getContext();
        float fDp2px = MiuixUIUtils.dp2px(context, i);
        float fDp2px2 = MiuixUIUtils.dp2px(context, i2);
        float fDp2px3 = MiuixUIUtils.dp2px(context, i3);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        Rect viewRect = getViewRect(view);
        Rect safeRect = getSafeRect(view3);
        float measuredHeight = view2.getMeasuredHeight();
        float f2 = viewRect.minX;
        float f3 = safeRect.minX;
        float f4 = ((f2 - f3) - fDp2px2) - fDp2px3;
        float f5 = safeRect.maxX;
        float f6 = viewRect.maxX;
        int i4 = ((f5 - f6) - fDp2px2) - fDp2px3 >= f4 ? 8388611 : 8388613;
        if (i4 != 8388611) {
            f2 = f6 - fDp2px;
        }
        float f7 = f3 + fDp2px2;
        float f8 = (f5 - fDp2px2) - fDp2px;
        if (f2 < f7) {
            Log.w("PopupPositionCalculator", String.format("Popup exceeds left boundary by %.0fpx. Adjusted X position from %.0fpx to %.0fpx", Float.valueOf(f7 - f2), Float.valueOf(f2), Float.valueOf(f7)));
            f8 = f7;
        } else if (f2 > f8) {
            Log.w("PopupPositionCalculator", String.format("Popup exceeds right boundary by %.0fpx. Adjusted X position from %.0fpx to %.0fpx", Float.valueOf(f2 - f8), Float.valueOf(f2), Float.valueOf(f8)));
        } else {
            f8 = f2;
        }
        float f9 = viewRect.minY;
        float f10 = safeRect.minY;
        float f11 = ((f9 - f10) - fDp2px2) - fDp2px3;
        float f12 = safeRect.maxY;
        float f13 = viewRect.maxY;
        int i5 = f11 >= ((f12 - f13) - fDp2px2) - fDp2px3 ? 48 : 80;
        if (i5 == 48) {
            f9 = f13 - measuredHeight;
        }
        float fClamp = clamp(f9, f10 + fDp2px2, (f12 - fDp2px2) - measuredHeight);
        float f14 = safeRect.height;
        float f15 = 2.0f * fDp2px2;
        if (measuredHeight > f14 - f15) {
            float f16 = f14 - f15;
            fClamp = safeRect.minY + fDp2px2;
            Log.w("PopupPositionCalculator", String.format("Popup height (%.0fpx) exceeds available height (%.0fpx). Adjusted to %.0fpx", Float.valueOf(measuredHeight), Float.valueOf(safeRect.height - f15), Float.valueOf(f16)));
            f = f16;
            z = true;
        } else {
            z = false;
            f = measuredHeight;
        }
        return new PopupPosition(f8, fClamp, i4 | i5, fDp2px, f, false, z);
    }

    public final float clamp(float f, float f2, float f3) {
        return Math.max(f2, Math.min(f, f3));
    }

    public Rect getSafeRect(View view) {
        Rect rect = new Rect();
        rect.minX = 0.0f;
        rect.minY = 0.0f;
        rect.maxX = view.getWidth();
        rect.maxY = view.getHeight();
        int iDp2px = MiuixUIUtils.dp2px(view.getContext(), 24.0f);
        WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view);
        if (rootWindowInsets != null) {
            Insets insets = rootWindowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            float f = iDp2px;
            rect.minX += f;
            rect.maxX -= f;
            rect.minY = Math.max(insets.top, iDp2px);
            rect.maxY -= insets.bottom + iDp2px;
        } else {
            float f2 = iDp2px;
            rect.minX += f2;
            rect.minY += f2;
            rect.maxX -= f2;
            rect.maxY -= f2;
        }
        float f3 = rect.maxX;
        float f4 = rect.minX;
        float f5 = f3 - f4;
        rect.width = f5;
        float f6 = rect.maxY;
        float f7 = rect.minY;
        float f8 = f6 - f7;
        rect.height = f8;
        rect.centerX = f4 + (f5 / 2.0f);
        rect.centerY = f7 + (f8 / 2.0f);
        return rect;
    }

    public Rect getViewRect(View view) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        Rect rect = new Rect();
        float f = iArr[0];
        rect.minX = f;
        rect.minY = iArr[1];
        rect.maxX = f + view.getWidth();
        rect.maxY = rect.minY + view.getHeight();
        rect.width = view.getWidth();
        float height = view.getHeight();
        rect.height = height;
        rect.centerX = rect.minX + (rect.width / 2.0f);
        rect.centerY = rect.minY + (height / 2.0f);
        return rect;
    }
}
