package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.state.WidgetFrame;

/* JADX INFO: loaded from: classes.dex */
public class MotionWidget implements TypedValues {
    public WidgetFrame widgetFrame;

    public static class Motion {
    }

    public static class PropertySet {
    }

    public String toString() {
        return this.widgetFrame.left + ", " + this.widgetFrame.top + ", " + this.widgetFrame.right + ", " + this.widgetFrame.bottom;
    }
}
