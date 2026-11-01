package com.miui.maml.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.miui.maml.R;

/* JADX INFO: loaded from: classes2.dex */
public class PortableUtils {
    public static final boolean ATLEAST_MIUI_15;
    public static final int MIUI_V15_VERSION_CODE = 15;
    public static final int MIUI_VERSION_CODE;

    static {
        int miuiVersionCode = getMiuiVersionCode();
        MIUI_VERSION_CODE = miuiVersionCode;
        ATLEAST_MIUI_15 = miuiVersionCode >= 15;
    }

    public static boolean cutBlankIcon(String str) {
        return ATLEAST_MIUI_15 && "1x1".equalsIgnoreCase(str);
    }

    public static Drawable getBadgedDrawable(Context context, Drawable drawable, Drawable drawable2, Rect rect, boolean z) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        boolean z2 = z && (drawable instanceof BitmapDrawable) && ((BitmapDrawable) drawable).getBitmap().isMutable();
        Bitmap bitmap = z2 ? ((BitmapDrawable) drawable).getBitmap() : Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if (!z2) {
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
            drawable.draw(canvas);
        }
        if (rect == null) {
            drawable2.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
            drawable2.draw(canvas);
        } else {
            if (rect.left < 0 || rect.top < 0 || rect.width() > intrinsicWidth || rect.height() > intrinsicHeight) {
                throw new IllegalArgumentException("Badge location " + rect + " not in badged drawable bounds " + new Rect(0, 0, intrinsicWidth, intrinsicHeight));
            }
            drawable2.setBounds(0, 0, rect.width(), rect.height());
            canvas.save();
            canvas.translate(rect.left, rect.top);
            drawable2.draw(canvas);
            canvas.restore();
        }
        if (z2) {
            return drawable;
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        if (drawable instanceof BitmapDrawable) {
            bitmapDrawable.setTargetDensity(((BitmapDrawable) drawable).getBitmap().getDensity());
        }
        return bitmapDrawable;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getMaskCornerId(String str) {
        int i = R.drawable.icon_mask;
        String lowerCase = str.toLowerCase();
        lowerCase.getClass();
        switch (lowerCase) {
            case "1x1":
                if (cutBlankIcon("1x1")) {
                    return R.drawable.icon_mask_1x1;
                }
                return i;
            case "1x2":
                return R.drawable.icon_mask_1x2;
            case "2x1":
                return R.drawable.icon_mask_2x1;
            case "2x2":
                return R.drawable.icon_mask_2x2;
            default:
                return i;
        }
    }

    public static int getMiuiVersionCode() {
        String str = SystemProperties.get("ro.miui.ui.version.code", "");
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static Drawable getUserBadgedIcon(Context context, Drawable drawable, UserHandle userHandle) {
        return context.getPackageManager().getUserBadgedIcon(drawable, userHandle);
    }

    /* JADX WARN: switch over string: strings are not added: [[1x2], [2x1], [2x2]] */
    public static Drawable getXSpaceBadgeDrawable(Context context, String str) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        int iFindNearestSupportDensity = ThemeDensityFallbackUtils.findNearestSupportDensity(displayMetrics.densityDpi);
        int i = R.drawable.ic_corp_icon_xspace_single;
        String lowerCase = str.toLowerCase();
        lowerCase.getClass();
        switch (lowerCase) {
            case "1x1":
                i = R.drawable.ic_corp_icon_xspace_1x1;
                break;
        }
        return context.getResources().getDrawableForDensity(i, iFindNearestSupportDensity, null);
    }

    public static Drawable getXspaceDrawable(Context context, Drawable drawable, Drawable drawable2) {
        if (drawable == null || drawable2 == null) {
            Log.e("LargeIconsHelper", "getXspaceDrawable exception, drawable or xSpaceDrawable in to null");
            return null;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int intrinsicWidth2 = drawable2.getIntrinsicWidth();
        int intrinsicHeight2 = drawable2.getIntrinsicHeight();
        if (intrinsicWidth < intrinsicWidth2 || intrinsicHeight < intrinsicHeight2) {
            Log.e("LargeIconsHelper", "getXspaceDrawable exception, originDrawable must be larger than xSpaceDrawable!");
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        drawable2.setBounds(0, intrinsicHeight - intrinsicHeight2, intrinsicWidth2, intrinsicHeight);
        drawable2.draw(canvas);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmapCreateBitmap);
        if (drawable instanceof BitmapDrawable) {
            bitmapDrawable.setTargetDensity(((BitmapDrawable) drawable).getBitmap().getDensity());
        }
        return bitmapDrawable;
    }
}
