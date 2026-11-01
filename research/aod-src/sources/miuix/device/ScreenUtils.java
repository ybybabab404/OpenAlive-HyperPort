package miuix.device;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.WindowManager;

/* JADX INFO: loaded from: classes2.dex */
class ScreenUtils {
    public static void getScreenSize(Context context, Point point) {
        Rect bounds = getWindowManager(context).getMaximumWindowMetrics().getBounds();
        point.x = bounds.width();
        point.y = bounds.height();
    }

    public static WindowManager getWindowManager(Context context) {
        return (WindowManager) context.getSystemService("window");
    }
}
