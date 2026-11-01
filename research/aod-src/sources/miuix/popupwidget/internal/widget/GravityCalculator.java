package miuix.popupwidget.internal.widget;

import android.graphics.Rect;
import miuix.popupwidget.internal.strategy.PopupWindowSpec;

/* JADX INFO: loaded from: classes2.dex */
public class GravityCalculator {
    public static int compute(PopupWindowSpec popupWindowSpec, Rect rect, Rect rect2) {
        if (popupWindowSpec == null || rect == null || rect2 == null) {
            return 51;
        }
        Rect rect3 = popupWindowSpec.mSafeInsets;
        Rect rect4 = popupWindowSpec.mDecorViewBounds;
        int i = rect3.top;
        int i2 = rect3.bottom;
        int i3 = rect3.left;
        int iWidth = (rect4.width() - i3) - rect3.right;
        int iHeight = (rect4.height() - i) - i2;
        float fCenterX = ((rect.centerX() - rect4.left) - i3) / Math.max(iWidth, 1);
        float fCenterY = ((rect.centerY() - rect4.top) - i) / Math.max(iHeight, 1);
        int iHeight2 = rect2.height();
        int i4 = (rect.top - rect4.top) - i;
        int i5 = (rect4.bottom - i2) - rect.bottom;
        boolean z = fCenterY < 0.4f;
        boolean z2 = fCenterY > 0.6f;
        boolean z3 = fCenterX < 0.35f;
        boolean z4 = fCenterX > 0.65f;
        if (z) {
            return getTopGravity(z3, z4);
        }
        if (z2) {
            return getBottomGravity(z3, z4);
        }
        if (z3) {
            return getCenterLeftGravity(i4, iHeight2, i5);
        }
        return z4 ? getCenterRightGravity(i4, iHeight2, i5) : getCenterGravity(i4, iHeight2, i5);
    }

    public static int getBottomGravity(boolean z, boolean z2) {
        if (z) {
            return 83;
        }
        return z2 ? 85 : 81;
    }

    public static int getCenterGravity(int i, int i2, int i3) {
        if (i >= i2) {
            return 81;
        }
        if (i3 >= i2) {
            return 49;
        }
        return (i >= i3 ? 80 : 48) | 1;
    }

    public static int getCenterLeftGravity(int i, int i2, int i3) {
        if (i >= i2) {
            return 83;
        }
        return i3 >= i2 ? 51 : 19;
    }

    public static int getCenterRightGravity(int i, int i2, int i3) {
        if (i >= i2) {
            return 85;
        }
        return i3 >= i2 ? 53 : 21;
    }

    public static int getTopGravity(boolean z, boolean z2) {
        if (z) {
            return 51;
        }
        return z2 ? 53 : 49;
    }
}
