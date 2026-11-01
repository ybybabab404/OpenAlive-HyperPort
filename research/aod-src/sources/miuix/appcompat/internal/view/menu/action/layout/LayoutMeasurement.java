package miuix.appcompat.internal.view.menu.action.layout;

import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public class LayoutMeasurement {
    public final int[] itemWidths;
    public final int menuHeight;
    public final int menuWidth;
    public final float[] textWidths;
    public final LayoutTier tier;

    public String toString() {
        return "LayoutMeasurement{tier=" + this.tier + ", menuWidth=" + this.menuWidth + ", menuHeight=" + this.menuHeight + ", itemWidths=" + Arrays.toString(this.itemWidths) + ", textWidths=" + Arrays.toString(this.textWidths) + '}';
    }
}
