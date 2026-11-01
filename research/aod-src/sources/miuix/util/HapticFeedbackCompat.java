package miuix.util;

import android.content.Context;
import android.os.Looper;
import android.os.VibrationAttributes;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import miui.util.HapticFeedbackUtil;
import miuix.HapticLog;
import miuix.view.HapticCompat;
import miuix.view.PlatformConstants;

/* JADX INFO: loaded from: classes3.dex */
public class HapticFeedbackCompat {
    public static boolean mAvailable;
    public static boolean mCanCheckExtHaptic;
    public static boolean mCanStop;
    public static boolean mExtHapticAlways;
    public static boolean mIsSupportExtHapticWithReason;
    public static boolean mIsSupportHapticWithReason;
    public static boolean mPerformExtHapticFeedbackThreeParamsMethodExist;
    public static boolean mPerformExtHapticFeedbackTwoParamsMethodExist;
    public static boolean mPerformHapticFeedbackFourParamsMethod1Exist;
    public static boolean mPerformHapticFeedbackFourParamsMethod2Exist;
    public static final Executor sSingleThread = Executors.newSingleThreadExecutor();
    public HapticFeedbackUtil mHapticFeedbackUtil;

    /* JADX INFO: renamed from: miuix.util.HapticFeedbackCompat$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        public final /* synthetic */ HapticFeedbackCompat this$0;
        public final /* synthetic */ int val$effectId;

        @Override // java.lang.Runnable
        public void run() {
            this.this$0.performHapticFeedback(this.val$effectId);
        }
    }

    static {
        int i = PlatformConstants.VERSION;
        Class cls = Boolean.TYPE;
        Class cls2 = Double.TYPE;
        Class cls3 = Integer.TYPE;
        if (i >= 1) {
            try {
                mAvailable = HapticFeedbackUtil.isSupportLinearMotorVibrate();
            } catch (Throwable th) {
                android.util.Log.w("HapticFeedbackCompat", "MIUI Haptic Implementation is not available", th);
                mAvailable = false;
            }
            if (mAvailable) {
                try {
                    HapticFeedbackUtil.class.getMethod("performHapticFeedback", cls3, cls2, String.class);
                    mIsSupportHapticWithReason = true;
                } catch (Throwable th2) {
                    android.util.Log.w("HapticFeedbackCompat", "Not support haptic with reason", th2);
                    mIsSupportHapticWithReason = false;
                }
                try {
                    HapticFeedbackUtil.class.getMethod("isSupportExtHapticFeedback", cls3);
                    mCanCheckExtHaptic = true;
                } catch (Throwable unused) {
                    mCanCheckExtHaptic = false;
                }
                try {
                    HapticFeedbackUtil.class.getMethod("performExtHapticFeedback", cls3, cls);
                    mExtHapticAlways = true;
                } catch (Throwable unused2) {
                    mExtHapticAlways = false;
                }
                try {
                    HapticFeedbackUtil.class.getMethod("stop", new Class[0]);
                    mCanStop = true;
                } catch (Throwable unused3) {
                    mCanStop = false;
                }
                try {
                    HapticFeedbackUtil.class.getMethod("performExtHapticFeedback", cls3, cls2, String.class);
                    mIsSupportExtHapticWithReason = true;
                } catch (Throwable th3) {
                    android.util.Log.w("HapticFeedbackCompat", "Not support ext haptic with reason", th3);
                    mIsSupportExtHapticWithReason = false;
                }
            }
        }
        if (PlatformConstants.romHapticVersion >= 1.2d) {
            try {
                HapticFeedbackUtil.class.getMethod("performExtHapticFeedback", VibrationAttributes.class, cls3);
                mPerformExtHapticFeedbackTwoParamsMethodExist = true;
            } catch (Exception unused4) {
            }
            try {
                HapticFeedbackUtil.class.getMethod("performExtHapticFeedback", VibrationAttributes.class, cls3, cls);
                mPerformExtHapticFeedbackThreeParamsMethodExist = true;
            } catch (Exception unused5) {
            }
            try {
                HapticFeedbackUtil.class.getMethod("performHapticFeedback", VibrationAttributes.class, cls3, cls, cls3);
                mPerformHapticFeedbackFourParamsMethod1Exist = true;
            } catch (Exception unused6) {
            }
            try {
                HapticFeedbackUtil.class.getMethod("performHapticFeedback", VibrationAttributes.class, cls3, cls2, String.class);
                mPerformHapticFeedbackFourParamsMethod2Exist = true;
            } catch (Exception unused7) {
            }
        }
    }

    public HapticFeedbackCompat(Context context) {
        this(context, true);
    }

    public HapticFeedbackCompat(Context context, boolean z) {
        if (PlatformConstants.VERSION < 1) {
            android.util.Log.w("HapticFeedbackCompat", "MiuiHapticFeedbackConstants not found or not compatible for LinearVibrator.");
        } else if (mAvailable) {
            this.mHapticFeedbackUtil = new HapticFeedbackUtil(context, z);
        } else {
            android.util.Log.w("HapticFeedbackCompat", "linear motor is not supported in this platform.");
        }
    }

    public boolean performExtHapticFeedback(int i) {
        if (this.mHapticFeedbackUtil == null) {
            return false;
        }
        HapticLog.printTrace("performExtHapticFeedback: " + i);
        return this.mHapticFeedbackUtil.performExtHapticFeedback(i);
    }

    public void performExtHapticFeedbackAsync(final int i) {
        if (this.mHapticFeedbackUtil == null) {
            return;
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            sSingleThread.execute(new Runnable() { // from class: miuix.util.HapticFeedbackCompat$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.performExtHapticFeedback(i);
                }
            });
        } else {
            performExtHapticFeedback(i);
        }
    }

    public boolean performHapticFeedback(int i) {
        return performHapticFeedback(null, i);
    }

    public boolean performHapticFeedback(VibrationAttributes vibrationAttributes, int i) {
        return performHapticFeedback(vibrationAttributes, i, false);
    }

    public boolean performHapticFeedback(VibrationAttributes vibrationAttributes, int i, boolean z) {
        int iObtainFeedBack;
        if (this.mHapticFeedbackUtil == null || (iObtainFeedBack = HapticCompat.obtainFeedBack(i)) == -1) {
            return false;
        }
        try {
            HapticLog.printTrace("performHapticFeedback: attributes: " + vibrationAttributes + ", effectId: " + i + ", always: " + z);
            return (PlatformConstants.romHapticVersion < 1.2d || !mPerformExtHapticFeedbackThreeParamsMethodExist) ? this.mHapticFeedbackUtil.performHapticFeedback(iObtainFeedBack, z) : this.mHapticFeedbackUtil.performHapticFeedback(vibrationAttributes, iObtainFeedBack, z);
        } catch (Exception e) {
            android.util.Log.e("HapticFeedbackCompat", "Failed to perform haptic!", e);
            return false;
        }
    }
}
