package miuix.view;

import android.os.Looper;
import android.util.Log;
import android.view.View;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import com.xiaomi.onetrack.BuildConfig;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import miuix.HapticLog;
import miuix.core.util.SystemProperties;

/* JADX INFO: loaded from: classes3.dex */
public class HapticCompat {
    public static String CURRENT_HAPTIC_VERSION = SystemProperties.get("sys.haptic.version", BuildConfig.VERSION_NAME);
    public static List sProviders = new ArrayList();
    public static final Executor sSingleThread = Executors.newSingleThreadExecutor();

    @Retention(RetentionPolicy.SOURCE)
    public @interface HapticVersion {
    }

    public static class WeakReferenceHandler implements Runnable {
        public final int mFeedbackConstant;
        public final WeakReference mViewReference;

        public WeakReferenceHandler(View view, int i) {
            this.mViewReference = new WeakReference(view);
            this.mFeedbackConstant = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            View view = (View) this.mViewReference.get();
            if (view == null || !view.isAttachedToWindow()) {
                return;
            }
            try {
                HapticCompat.performHapticFeedback(view, this.mFeedbackConstant);
            } catch (Exception unused) {
            }
        }
    }

    static {
        loadProviders("miuix.view.LinearVibrator", "miuix.view.ExtendedVibrator");
    }

    public static boolean checkHapticVersion1FeedBackConstant(int i) {
        int i2 = HapticFeedbackConstants.MIUI_HAPTIC_VERSION_1_START;
        if (i >= i2 && i <= HapticFeedbackConstants.MIUI_HAPTIC_VERSION_1_END) {
            return true;
        }
        Log.e("HapticCompat", String.format("Illegal haptic version 1 feedback constant, should be in range [0x%08x..0x%08x]", Integer.valueOf(i2), Integer.valueOf(HapticFeedbackConstants.MIUI_HAPTIC_VERSION_1_END)));
        return false;
    }

    public static boolean checkHapticVersion2FeedBackConstant(int i) {
        int i2 = HapticFeedbackConstants.MIUI_HAPTIC_VERSION_2_START;
        if (i >= i2 && i <= HapticFeedbackConstants.MIUI_HAPTIC_VERSION_2_END) {
            return true;
        }
        Log.e("HapticCompat", String.format("Illegal haptic version 2 feedback constant, should be in range [0x%08x..0x%08x]", Integer.valueOf(i2), Integer.valueOf(HapticFeedbackConstants.MIUI_HAPTIC_VERSION_2_END)));
        return false;
    }

    public static boolean doesSupportHaptic(String str) {
        return CURRENT_HAPTIC_VERSION.equals(str);
    }

    public static void loadProviders(String... strArr) {
        for (String str : strArr) {
            Log.i("HapticCompat", "loading provider: " + str);
            try {
                Class.forName(str, true, HapticCompat.class.getClassLoader());
            } catch (ClassNotFoundException e) {
                Log.w("HapticCompat", String.format("load provider %s failed.", str), e);
            }
        }
    }

    public static int obtainFeedBack(int i) {
        for (HapticFeedbackProvider hapticFeedbackProvider : sProviders) {
            if (hapticFeedbackProvider instanceof LinearVibrator) {
                return ((LinearVibrator) hapticFeedbackProvider).obtainFeedBack(i);
            }
        }
        return -1;
    }

    @Keep
    public static boolean performHapticFeedback(@NonNull View view, int i) {
        if (view == null) {
            Log.e("HapticCompat", "performHapticFeedback: view is null!");
            return false;
        }
        if (i < 268435456) {
            Log.i("HapticCompat", String.format("perform haptic: 0x%08x", Integer.valueOf(i)));
            HapticLog.printTrace("performHapticFeedback view: " + view + ", feedbackConstant: " + i);
            return view.performHapticFeedback(i);
        }
        int i2 = HapticFeedbackConstants.MIUI_HAPTIC_END;
        if (i > i2) {
            Log.w("HapticCompat", String.format("illegal feedback constant, should be in range [0x%08x..0x%08x]", 268435456, Integer.valueOf(i2)));
            return false;
        }
        Iterator it = sProviders.iterator();
        while (it.hasNext()) {
            if (((HapticFeedbackProvider) it.next()).performHapticFeedback(view, i)) {
                return true;
            }
        }
        return false;
    }

    public static boolean performHapticFeedback(View view, int i, int i2) {
        if (doesSupportHaptic("2.0")) {
            if (checkHapticVersion2FeedBackConstant(i)) {
                return performHapticFeedback(view, i);
            }
            return false;
        }
        if (doesSupportHaptic(BuildConfig.VERSION_NAME)) {
            if (checkHapticVersion1FeedBackConstant(i2)) {
                return performHapticFeedback(view, i2);
            }
            return false;
        }
        Log.e("HapticCompat", "Unexpected haptic version: " + CURRENT_HAPTIC_VERSION);
        return false;
    }

    @Keep
    public static void performHapticFeedbackAsync(@NonNull View view, int i) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            sSingleThread.execute(new WeakReferenceHandler(view, i));
        } else {
            performHapticFeedback(view, i);
        }
    }

    @Keep
    public static void performHapticFeedbackAsync(@NonNull View view, int i, int i2) {
        if (doesSupportHaptic("2.0")) {
            if (checkHapticVersion2FeedBackConstant(i)) {
                performHapticFeedbackAsync(view, i);
            }
        } else if (doesSupportHaptic(BuildConfig.VERSION_NAME)) {
            if (checkHapticVersion1FeedBackConstant(i2)) {
                performHapticFeedbackAsync(view, i2);
            }
        } else {
            Log.e("HapticCompat", "Unexpected haptic version: " + CURRENT_HAPTIC_VERSION);
        }
    }

    @Keep
    public static void registerProvider(HapticFeedbackProvider hapticFeedbackProvider) {
        sProviders.add(hapticFeedbackProvider);
    }
}
