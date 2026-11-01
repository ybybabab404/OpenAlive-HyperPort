package com.miui.miwallpaper.util;

import android.content.Context;
import android.graphics.Rect;
import android.view.WindowManager;

/* JADX INFO: loaded from: classes2.dex */
public class ScreenUtils {
    public static int getRefreshRate(Context context) {
        return (int) ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRefreshRate();
    }

    public static Rect getScreenRect(Context context) {
        Rect bounds = ((WindowManager) context.getSystemService(WindowManager.class)).getMaximumWindowMetrics().getBounds();
        if (EffectSystemSettingUtils.NEED_ROTATE_WALLPAPER) {
            return (!EffectSystemSettingUtils.IS_FOLD_DEVICE || EffectSystemSettingUtils.isLargeScreen(context) || bounds.width() <= bounds.height()) ? bounds : new Rect(0, 0, bounds.height(), bounds.width());
        }
        int iWidth = bounds.width();
        int iHeight = bounds.height();
        return iWidth < iHeight ? new Rect(0, 0, iWidth, iHeight) : new Rect(0, 0, iHeight, iWidth);
    }
}
