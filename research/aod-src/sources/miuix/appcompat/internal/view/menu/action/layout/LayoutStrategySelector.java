package miuix.appcompat.internal.view.menu.action.layout;

import android.content.Context;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.util.HashMap;
import java.util.Map;
import miuix.core.util.MiuixUIUtils;

/* JADX INFO: loaded from: classes2.dex */
public class LayoutStrategySelector {
    public final Context context;
    public final Map strategyCache = new HashMap();
    public final int wideThresholdPx;

    public LayoutStrategySelector(Context context) {
        this.context = context;
        this.wideThresholdPx = MiuixUIUtils.dp2px(context, 400.0f);
    }

    public final LayoutStrategy createStrategy(int i, boolean z) {
        if (i == 1) {
            return new SingleItemLayoutStrategy(this.context);
        }
        if (i == 2) {
            return new TwoItemLayoutStrategy(this.context);
        }
        if (i == 3) {
            return new ThreeItemLayoutStrategy(this.context);
        }
        if ((i == 4 || i == 5) && z) {
            return new WideMultiItemLayoutStrategy(this.context);
        }
        return new MultiItemLayoutStrategy(this.context);
    }

    public LayoutStrategy selectStrategy(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("Item count cannot be negative: " + i);
        }
        boolean z = (i == 4 || i == 5) && i2 > this.wideThresholdPx;
        int i3 = z ? i | AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL : i;
        LayoutStrategy layoutStrategy = (LayoutStrategy) this.strategyCache.get(Integer.valueOf(i3));
        if (layoutStrategy != null) {
            return layoutStrategy;
        }
        LayoutStrategy layoutStrategyCreateStrategy = createStrategy(i, z);
        this.strategyCache.put(Integer.valueOf(i3), layoutStrategyCreateStrategy);
        return layoutStrategyCreateStrategy;
    }
}
