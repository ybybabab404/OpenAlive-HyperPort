package miuix.recyclerview.tool;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.RequiresApi;
import com.xiaomi.onetrack.util.ac;
import miuix.mimotion.MiMotionHelper;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes2.dex */
@RequiresApi
public class GetSpeedForDynamicRefreshRate {
    public static int sControlViewHashCode = 0;
    public static boolean sHasGetProperty = false;
    public static int[] sRefreshRateList;
    public static int[] sRefreshRateSpeedLimits;
    public int mCurrentRefreshRate;
    public final Display mDisplay;
    public final boolean mIsEnable;
    public MiMotionRecyclerViewHelper mMiMotionRecyclerViewHelper;
    public RecyclerView mRecyclerView;
    public final Window mWindow;
    public volatile boolean mIsTouch = false;
    public boolean mHasFocus = false;
    public int mCountIndex = 0;
    public long mStartTime = -1;
    public long mTotalDistance = 0;
    public boolean mNeedAbandon = false;
    public int mOldScrollState = 0;
    public int mRefreshRate = -1;

    public GetSpeedForDynamicRefreshRate(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        Activity activity = getActivity(recyclerView.getContext());
        Display display = activity != null ? activity.getDisplay() : null;
        this.mDisplay = display;
        Window window = activity != null ? activity.getWindow() : null;
        this.mWindow = window;
        boolean z = (!getParam() || display == null || window == null) ? false : true;
        this.mIsEnable = z;
        if (!z) {
            Log.e("DynamicRefreshRate recy", "dynamic is not enable");
        }
        if (display == null || window == null) {
            return;
        }
        if (MiMotionHelper.isSupportMiMotion()) {
            MiMotionRecyclerViewHelper miMotionRecyclerViewHelper = new MiMotionRecyclerViewHelper();
            this.mMiMotionRecyclerViewHelper = miMotionRecyclerViewHelper;
            if (!miMotionRecyclerViewHelper.initMiMotion(recyclerView)) {
                this.mMiMotionRecyclerViewHelper = null;
            }
        }
        int[] iArr = sRefreshRateList;
        if (iArr == null || iArr.length <= 0) {
            return;
        }
        this.mCurrentRefreshRate = iArr[0];
    }

    public static Activity getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public static boolean getParam() {
        boolean z = false;
        if (sHasGetProperty) {
            return (sRefreshRateList == null || sRefreshRateSpeedLimits == null) ? false : true;
        }
        try {
            try {
                String str = (String) Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class).invoke(null, "ro.vendor.display.dynamic_refresh_rate");
                if (str == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("dynamic params is ");
                    sb.append((sRefreshRateList == null || sRefreshRateSpeedLimits == null) ? false : true);
                    Log.i("DynamicRefreshRate recy", sb.toString());
                    sHasGetProperty = true;
                    return false;
                }
                String[] strArrSplit = str.split(":");
                if (strArrSplit.length != 2) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("dynamic params is ");
                    sb2.append((sRefreshRateList == null || sRefreshRateSpeedLimits == null) ? false : true);
                    Log.i("DynamicRefreshRate recy", sb2.toString());
                    sHasGetProperty = true;
                    return false;
                }
                String[] strArrSplit2 = strArrSplit[0].split(ac.b);
                String[] strArrSplit3 = strArrSplit[1].split(ac.b);
                if (strArrSplit3.length != strArrSplit2.length - 1) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("dynamic params is ");
                    sb3.append((sRefreshRateList == null || sRefreshRateSpeedLimits == null) ? false : true);
                    Log.i("DynamicRefreshRate recy", sb3.toString());
                    sHasGetProperty = true;
                    return false;
                }
                sRefreshRateList = new int[strArrSplit2.length];
                for (int i = 0; i < strArrSplit2.length; i++) {
                    sRefreshRateList[i] = Integer.parseInt(strArrSplit2[i]);
                }
                sRefreshRateSpeedLimits = new int[strArrSplit3.length];
                for (int i2 = 0; i2 < strArrSplit3.length; i2++) {
                    sRefreshRateSpeedLimits[i2] = Integer.parseInt(strArrSplit3[i2]);
                }
                StringBuilder sb4 = new StringBuilder();
                sb4.append("dynamic params is ");
                if (sRefreshRateList != null && sRefreshRateSpeedLimits != null) {
                    z = true;
                }
                sb4.append(z);
                Log.i("DynamicRefreshRate recy", sb4.toString());
                sHasGetProperty = true;
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                StringBuilder sb5 = new StringBuilder();
                sb5.append("dynamic params is ");
                sb5.append((sRefreshRateList == null || sRefreshRateSpeedLimits == null) ? false : true);
                Log.i("DynamicRefreshRate recy", sb5.toString());
                sHasGetProperty = true;
                sRefreshRateList = null;
                sRefreshRateSpeedLimits = null;
                return false;
            }
        } catch (Throwable th) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append("dynamic params is ");
            if (sRefreshRateList != null) {
                z = true;
            }
            sb6.append(z);
            Log.i("DynamicRefreshRate recy", sb6.toString());
            sHasGetProperty = true;
            throw th;
        }
        StringBuilder sb7 = new StringBuilder();
        sb7.append("dynamic params is ");
        if (sRefreshRateList != null && sRefreshRateSpeedLimits != null) {
            z = true;
        }
        sb7.append(z);
        Log.i("DynamicRefreshRate recy", sb7.toString());
        sHasGetProperty = true;
        throw th;
    }

    public final int calculateRefreshRate(int i) {
        int[] iArr = sRefreshRateList;
        int i2 = iArr[iArr.length - 1];
        if (!this.mHasFocus || this.mNeedAbandon) {
            return -1;
        }
        if (i == 0) {
            return i2;
        }
        if (this.mCountIndex == 0) {
            this.mTotalDistance = 0L;
            this.mStartTime = System.currentTimeMillis();
        }
        int i3 = this.mCountIndex + 1;
        this.mCountIndex = i3;
        this.mTotalDistance += (long) i;
        if (i3 < 3) {
            return -1;
        }
        int iAbs = Math.abs(Math.round(this.mTotalDistance / ((System.currentTimeMillis() - this.mStartTime) / 1000.0f)));
        this.mCountIndex = 0;
        int i4 = 0;
        while (true) {
            int[] iArr2 = sRefreshRateSpeedLimits;
            if (i4 >= iArr2.length) {
                break;
            }
            if (iAbs > iArr2[i4]) {
                i2 = sRefreshRateList[i4];
                break;
            }
            i4++;
        }
        int i5 = this.mCurrentRefreshRate;
        if (i2 >= i5) {
            int[] iArr3 = sRefreshRateList;
            if (i5 != iArr3[iArr3.length - 1] || i2 != iArr3[0]) {
                return -1;
            }
        }
        this.mCurrentRefreshRate = i2;
        return i2;
    }

    public void calculateSpeed(int i, int i2, int i3, int i4) {
        int iCalculateRefreshRate;
        MiMotionRecyclerViewHelper miMotionRecyclerViewHelper = this.mMiMotionRecyclerViewHelper;
        if (miMotionRecyclerViewHelper != null) {
            miMotionRecyclerViewHelper.calculateSpeed(i3, i4, i, i2);
            return;
        }
        if (this.mIsEnable) {
            if ((i == 0 && i2 == 0) || this.mIsTouch || (iCalculateRefreshRate = calculateRefreshRate(Math.max(Math.abs(i), Math.abs(i2)))) == -1) {
                return;
            }
            setRefreshRate(iCalculateRefreshRate, false);
        }
    }

    public final void checkMiMotionRecyclerViewHelper() {
        if (!MiMotionHelper.isSupportMiMotion() || !MiMotionHelper.getInstance().isEnabled()) {
            this.mMiMotionRecyclerViewHelper = null;
            return;
        }
        if (this.mMiMotionRecyclerViewHelper == null) {
            MiMotionRecyclerViewHelper miMotionRecyclerViewHelper = new MiMotionRecyclerViewHelper();
            this.mMiMotionRecyclerViewHelper = miMotionRecyclerViewHelper;
            if (miMotionRecyclerViewHelper.initMiMotion(this.mRecyclerView)) {
                return;
            }
            this.mMiMotionRecyclerViewHelper = null;
        }
    }

    public void onFocusChange(boolean z) {
        MiMotionRecyclerViewHelper miMotionRecyclerViewHelper = this.mMiMotionRecyclerViewHelper;
        if (miMotionRecyclerViewHelper != null) {
            miMotionRecyclerViewHelper.onFocusChange(z);
        } else if (this.mIsEnable) {
            this.mHasFocus = z;
            this.mNeedAbandon = true;
            setRefreshRate(sRefreshRateList[0], false);
        }
    }

    public void scrollState(RecyclerView recyclerView, int i) {
        MiMotionRecyclerViewHelper miMotionRecyclerViewHelper = this.mMiMotionRecyclerViewHelper;
        if (miMotionRecyclerViewHelper != null) {
            miMotionRecyclerViewHelper.scrollState(recyclerView, i);
            return;
        }
        if (this.mIsEnable) {
            if (this.mNeedAbandon || this.mIsTouch || this.mOldScrollState != 2) {
                this.mOldScrollState = i;
                return;
            }
            this.mOldScrollState = i;
            if ((recyclerView.canScrollVertically(-1) && recyclerView.canScrollVertically(1)) || (recyclerView.canScrollHorizontally(-1) && recyclerView.canScrollVertically(1))) {
                int[] iArr = sRefreshRateList;
                setRefreshRate(iArr[iArr.length - 1], false);
            }
        }
    }

    public final void setRefreshRate(int i, boolean z) {
        Display.Mode[] supportedModes = this.mDisplay.getSupportedModes();
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        if (i == this.mRefreshRate || supportedModes == null) {
            return;
        }
        this.mRefreshRate = i;
        for (Display.Mode mode : supportedModes) {
            if (Math.abs(mode.getRefreshRate() - i) <= 1.0f) {
                if (z || hashCode() == sControlViewHashCode || mode.getRefreshRate() > this.mRefreshRate) {
                    sControlViewHashCode = hashCode();
                    Log.i("DynamicRefreshRate recy", sControlViewHashCode + " set Refresh rate to: " + i + ", mode is: " + mode.getModeId());
                    attributes.preferredDisplayModeId = mode.getModeId();
                    this.mWindow.setAttributes(attributes);
                    return;
                }
                return;
            }
        }
    }

    public void touchEvent(MotionEvent motionEvent) {
        checkMiMotionRecyclerViewHelper();
        MiMotionRecyclerViewHelper miMotionRecyclerViewHelper = this.mMiMotionRecyclerViewHelper;
        if (miMotionRecyclerViewHelper != null) {
            miMotionRecyclerViewHelper.touchEvent(motionEvent);
            return;
        }
        if (this.mIsEnable) {
            if (motionEvent.getActionMasked() != 0) {
                if (motionEvent.getActionMasked() == 1) {
                    this.mIsTouch = false;
                    return;
                }
                return;
            }
            this.mIsTouch = true;
            int i = sRefreshRateList[0];
            this.mCurrentRefreshRate = i;
            this.mCountIndex = 0;
            setRefreshRate(i, true);
            this.mHasFocus = true;
            this.mNeedAbandon = false;
        }
    }
}
