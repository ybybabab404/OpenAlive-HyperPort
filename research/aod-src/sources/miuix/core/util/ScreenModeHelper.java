package miuix.core.util;

import android.content.Context;
import android.graphics.Point;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import miuix.core.util.screenutils.FreeFormModeHelper;
import miuix.core.util.screenutils.SplitScreenModeHelper;

/* JADX INFO: loaded from: classes2.dex */
public class ScreenModeHelper {
    public static void detectWindowMode(Context context, WindowBaseInfo windowBaseInfo, Point point) {
        FreeFormModeHelper.detectFreeFormInfo(windowBaseInfo, context, point);
        if (isInFreeFormMode(windowBaseInfo.windowMode)) {
            return;
        }
        SplitScreenModeHelper.detectSplitScreenInfo(windowBaseInfo, point);
    }

    public static boolean isInFreeFormMode(int i) {
        return (i & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0;
    }

    public static boolean isInSplitScreenMode(int i) {
        return (i & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0;
    }
}
