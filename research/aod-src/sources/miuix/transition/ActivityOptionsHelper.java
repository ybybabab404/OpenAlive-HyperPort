package miuix.transition;

import android.app.Activity;
import android.app.ActivityOptions;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.Keep;
import miuix.reflect.ReflectionHelper;

/* JADX INFO: loaded from: classes3.dex */
@Keep
public class ActivityOptionsHelper {
    public static final int ANIM_LAUNCH_ACTIVITY_FROM_ROUNDED_VIEW = 102;
    public static final int ANIM_LAUNCH_ACTIVITY_WITH_SCALED_THUMB = 103;
    private static final String TAG = "ActivityOptionsHelper";

    public static class ClipAnimationHolder {
        public static final boolean SUPPORT_MIUI_CLIP_ANIMATION = isSupportMiuiClipAnimation();

        public static boolean isSupportMiuiClipAnimation() {
            try {
                Class cls = Float.TYPE;
                ActivityOptions.class.getMethod("makeMiuiClipAnimation", Rect.class, Rect.class, cls, cls, Integer.TYPE, cls, Boolean.TYPE);
                return true;
            } catch (NoSuchMethodException e) {
                Log.d(ActivityOptionsHelper.TAG, e.toString());
                return false;
            }
        }
    }

    public static class RoundAnimationHolder {
        public static final boolean SUPPORT_MIUI_ROUND_ANIMATION = isSupportMiuiRoundAnimation();

        public static boolean isSupportMiuiRoundAnimation() {
            try {
                Class cls = Float.TYPE;
                ActivityOptions.class.getMethod("makeMiuiRoundAnimation", cls, cls, Integer.TYPE, cls);
                return true;
            } catch (NoSuchMethodException e) {
                Log.d(ActivityOptionsHelper.TAG, e.toString());
                return false;
            }
        }
    }

    public static class ScaleUpAnimationHolder {
        public static final boolean SUPPORT_FROM_ROUND_VIEW = isSupportFromRoundedView();
        public static final boolean SUPPORT_SCALED_THUMB = isSupportScaledThumb();
        public static final boolean SUPPORT_UPDATE_DATA = isSupportUpdateData();
        public static final boolean SUPPORT_HYPER_VIEW_SCALE_ANIMATION = isSupportHyperViewScaleAnimation();

        public static boolean isSupportFromRoundedView() {
            try {
                Class cls = Integer.TYPE;
                ActivityOptions.class.getMethod("makeScaleUpAnimationFromRoundedView", View.class, Bitmap.class, cls, cls, cls, cls, Float.TYPE, Handler.class, Runnable.class, Runnable.class, Runnable.class, Runnable.class).setAccessible(true);
                return true;
            } catch (NoSuchMethodException e) {
                Log.d(ActivityOptionsHelper.TAG, e.toString());
                return false;
            }
        }

        public static boolean isSupportHyperViewScaleAnimation() {
            try {
                ActivityOptions.class.getDeclaredMethod("makeHyperViewScaleAnimation", View.class).setAccessible(true);
                return true;
            } catch (NoSuchMethodException e) {
                Log.d(ActivityOptionsHelper.TAG, e.toString());
                return false;
            }
        }

        public static boolean isSupportScaledThumb() {
            try {
                Class cls = Integer.TYPE;
                ActivityOptions.class.getMethod("makeScaleUpDown", View.class, Bitmap.class, cls, cls, cls, cls, Float.TYPE, Handler.class, Runnable.class, Runnable.class, Runnable.class, Runnable.class, cls).setAccessible(true);
                return true;
            } catch (NoSuchMethodException e) {
                Log.d(ActivityOptionsHelper.TAG, e.toString());
                return false;
            }
        }

        public static boolean isSupportUpdateData() {
            try {
                Activity.class.getDeclaredMethod("updateScaleUpDownData", Bundle.class).setAccessible(true);
                return true;
            } catch (NoSuchMethodException e) {
                Log.d(ActivityOptionsHelper.TAG, e.toString());
                return false;
            }
        }
    }

    private ActivityOptionsHelper() {
    }

    public static Bitmap captureSnapshot(View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        long j = width * height * 4;
        long scaledMaximumDrawingCacheSize = ViewConfiguration.get(view.getContext()).getScaledMaximumDrawingCacheSize();
        Bitmap bitmap = null;
        if (width <= 0 || height <= 0 || j > scaledMaximumDrawingCacheSize) {
            if (width > 0 && height > 0) {
                Log.d(TAG, "too large to create a bitmap!");
            }
            return null;
        }
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getResources().getDisplayMetrics(), width, height, Bitmap.Config.ARGB_8888);
            try {
                bitmapCreateBitmap.setDensity(view.getResources().getDisplayMetrics().densityDpi);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                view.computeScroll();
                int iSave = canvas.save();
                canvas.translate(-view.getScrollX(), -view.getScaleY());
                view.draw(canvas);
                canvas.restoreToCount(iSave);
                canvas.setBitmap(null);
                return bitmapCreateBitmap;
            } catch (OutOfMemoryError unused) {
                bitmap = bitmapCreateBitmap;
                Log.d(TAG, "too large to create a bitmap!");
                return bitmap;
            }
        } catch (OutOfMemoryError unused2) {
        }
    }

    public static boolean isSupportMiuiClipAnimation() {
        return ClipAnimationHolder.SUPPORT_MIUI_CLIP_ANIMATION;
    }

    public static boolean isSupportMiuiRoundAnimation() {
        return RoundAnimationHolder.SUPPORT_MIUI_ROUND_ANIMATION;
    }

    public static boolean isSupportScaleAnimation() {
        return ScaleUpAnimationHolder.SUPPORT_HYPER_VIEW_SCALE_ANIMATION;
    }

    public static boolean isSupportScaleUpDown() {
        return ScaleUpAnimationHolder.SUPPORT_FROM_ROUND_VIEW || ScaleUpAnimationHolder.SUPPORT_SCALED_THUMB;
    }

    public static boolean isSupportScaleUpDown(int i) {
        if (i == 102 && ScaleUpAnimationHolder.SUPPORT_FROM_ROUND_VIEW) {
            return true;
        }
        return i == 103 && ScaleUpAnimationHolder.SUPPORT_SCALED_THUMB;
    }

    public static boolean isSupportUpdateScaleUpDownData() {
        return ScaleUpAnimationHolder.SUPPORT_UPDATE_DATA;
    }

    public static ActivityOptions makeHyperViewScaleAnimation(View view) {
        if (ScaleUpAnimationHolder.SUPPORT_HYPER_VIEW_SCALE_ANIMATION) {
            return ActivityOptionsCompat.makeHyperViewScaleAnimation(view);
        }
        return null;
    }

    public static ActivityOptions makeMiuiClipAnimation(Rect rect, Rect rect2, float f, float f2, int i, float f3, boolean z) {
        if (isSupportMiuiClipAnimation()) {
            return ActivityOptionsCompat.makeMiuiClipAnimation(rect, rect2, f, f2, i, f3, z);
        }
        return null;
    }

    public static ActivityOptions makeMiuiRoundAnimation(float f, float f2, int i, float f3) {
        if (isSupportMiuiRoundAnimation()) {
            return ActivityOptionsCompat.makeMiuiRoundAnimation(f, f2, i, f3);
        }
        return null;
    }

    public static ActivityOptions makeScaleUpAnim(View view, Bitmap bitmap, int i, int i2, int i3, int i4, float f, Handler handler, Runnable runnable, Runnable runnable2, Runnable runnable3, Runnable runnable4, int i5) {
        if (isSupportScaleUpDown()) {
            return ScaleUpAnimationHolder.SUPPORT_SCALED_THUMB ? ActivityOptionsCompat.makeScaleUpDown(view, bitmap, i, i2, i3, i4, f, handler, runnable, runnable2, runnable3, runnable4, i5) : ActivityOptionsCompat.makeScaleUpAnimationFromRoundedView(view, bitmap, i, i2, i3, i4, f, handler, runnable, runnable2, runnable3, runnable4);
        }
        return null;
    }

    public static ActivityOptions makeScaleUpAnim(View view, Rect rect, int i, int i2, int i3) {
        if (isSupportScaleUpDown()) {
            return makeScaleUpAnim(view, rect, i, i2, null, null, null, null, (i3 == 102 || i3 == 103) ? i3 : 102);
        }
        return null;
    }

    private static ActivityOptions makeScaleUpAnim(View view, Rect rect, int i, int i2, Runnable runnable, Runnable runnable2, Runnable runnable3, Runnable runnable4, int i3) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(captureSnapshot(view), rect.left, rect.top, rect.width(), rect.height());
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return makeScaleUpAnim(view, bitmapCreateBitmap, iArr[0] + rect.left, iArr[1] + rect.top, i, i2, view.getScaleX(), new Handler(), runnable, runnable2, runnable3, runnable4, i3);
    }

    @Deprecated
    public static ActivityOptions makeScaleUpAnimationFromRoundedView(View view, Bitmap bitmap, int i, int i2, int i3, int i4, float f, Handler handler, Runnable runnable, Runnable runnable2, Runnable runnable3, Runnable runnable4) {
        return makeScaleUpAnim(view, bitmap, i, i2, i3, i4, f, handler, runnable, runnable2, runnable3, runnable4, 102);
    }

    @Deprecated
    public static ActivityOptions makeScaleUpAnimationFromRoundedView(View view, Rect rect, int i, int i2) {
        if (isSupportScaleUpDown()) {
            return makeScaleUpAnim(view, rect, i, i2, null, null, null, null, 102);
        }
        return null;
    }

    public static boolean updateScaleUpDownData(Activity activity, Bundle bundle) {
        if (!isSupportUpdateScaleUpDownData()) {
            return false;
        }
        try {
            ReflectionHelper.invoke(Activity.class, activity, "updateScaleUpDownData", new Class[]{Bundle.class}, bundle);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}
