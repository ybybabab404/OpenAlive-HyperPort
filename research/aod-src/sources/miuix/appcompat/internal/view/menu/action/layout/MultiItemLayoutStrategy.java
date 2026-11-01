package miuix.appcompat.internal.view.menu.action.layout;

import android.content.Context;

/* JADX INFO: loaded from: classes2.dex */
public class MultiItemLayoutStrategy implements LayoutStrategy {
    public final Context mContext;

    public MultiItemLayoutStrategy(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.mContext = context;
    }

    @Override // miuix.appcompat.internal.view.menu.action.layout.LayoutStrategy
    public int[] calculateItemWidths(LayoutTier layoutTier, int i, int i2) {
        if (layoutTier == null) {
            throw new IllegalArgumentException("LayoutTier cannot be null");
        }
        if (i2 < 4 || i2 > 5) {
            throw new IllegalArgumentException("itemCount must be 4 or 5 for MultiItemLayoutStrategy");
        }
        int[] iArr = new int[i2];
        int iDp2px = (i - ((dp2px(3.0f) * 2) + ((i2 + 1) * dp2px(10.0f)))) / i2;
        for (int i3 = 0; i3 < i2; i3++) {
            iArr[i3] = iDp2px;
        }
        return iArr;
    }

    @Override // miuix.appcompat.internal.view.menu.action.layout.LayoutStrategy
    public int calculateMenuHeight(LayoutTier layoutTier, int i) {
        if (layoutTier != null) {
            return i;
        }
        throw new IllegalArgumentException("LayoutTier cannot be null");
    }

    @Override // miuix.appcompat.internal.view.menu.action.layout.LayoutStrategy
    public int calculateMenuWidth(LayoutTier layoutTier, int i, int i2) {
        if (layoutTier == null) {
            throw new IllegalArgumentException("LayoutTier cannot be null");
        }
        if (i < 4 || i > 5) {
            throw new IllegalArgumentException("itemCount must be 4 or 5 for MultiItemLayoutStrategy");
        }
        return i2 - (dp2px(24.0f) * 2);
    }

    @Override // miuix.appcompat.internal.view.menu.action.layout.LayoutStrategy
    public LayoutTier determineLayoutTier(float[] fArr) {
        if (fArr == null) {
            throw new IllegalArgumentException("textWidths cannot be null");
        }
        if (fArr.length < 4 || fArr.length > 5) {
            throw new IllegalArgumentException("textWidths must have 4 or 5 elements for MultiItemLayoutStrategy");
        }
        return LayoutTier.NORMAL;
    }

    public int dp2px(float f) {
        return (int) ((f * this.mContext.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public boolean needHeightIncrease(float[] fArr, int[] iArr) {
        if (fArr == null || iArr == null || fArr.length != iArr.length) {
            return false;
        }
        for (int i = 0; i < fArr.length; i++) {
            if (dp2px(fArr[i]) > iArr[i]) {
                return true;
            }
        }
        return false;
    }
}
