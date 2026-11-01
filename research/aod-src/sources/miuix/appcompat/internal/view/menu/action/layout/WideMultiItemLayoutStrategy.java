package miuix.appcompat.internal.view.menu.action.layout;

import android.content.Context;

/* JADX INFO: loaded from: classes2.dex */
public class WideMultiItemLayoutStrategy extends MultiItemLayoutStrategy {
    public WideMultiItemLayoutStrategy(Context context) {
        super(context);
    }

    @Override // miuix.appcompat.internal.view.menu.action.layout.MultiItemLayoutStrategy, miuix.appcompat.internal.view.menu.action.layout.LayoutStrategy
    public int calculateMenuWidth(LayoutTier layoutTier, int i, int i2) {
        if (layoutTier == null) {
            throw new IllegalArgumentException("LayoutTier cannot be null");
        }
        if (i < 4 || i > 5) {
            throw new IllegalArgumentException("itemCount must be 4 or 5 for WideMultiItemLayoutStrategy");
        }
        return dp2px(344.0f);
    }
}
