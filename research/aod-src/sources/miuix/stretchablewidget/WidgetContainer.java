package miuix.stretchablewidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class WidgetContainer extends LinearLayout {
    public WidgetContainer(Context context) {
        this(context, null);
    }

    public WidgetContainer(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WidgetContainer(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public int getWidgetHeight() {
        return getHeight();
    }

    public void setWidgetHeight(int i) {
        if (i < 0) {
            return;
        }
        getLayoutParams().height = i;
        requestLayout();
    }
}
