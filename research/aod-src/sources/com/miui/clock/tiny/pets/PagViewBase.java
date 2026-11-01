package com.miui.clock.tiny.pets;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.miui.clock.tiny.utils.DateFormatUtils;
import com.miui.clock.tiny.utils.DeviceConfig;
import org.libpag.PAGComposition;
import org.libpag.PAGFile;
import org.libpag.PAGImageView;

/* JADX INFO: loaded from: classes.dex */
public abstract class PagViewBase extends PAGImageView {
    public static final Handler mHandler = new Handler(Looper.getMainLooper());
    public float endStopProgress;
    public final Runnable flushRunnable;
    public volatile int flushWidth;
    public final boolean isSystemUI;
    public PAGFile.LoadListener listener;
    public volatile PAGComposition mComposition;
    public Context mContext;
    public boolean mHasAttachedToWindow;
    public volatile boolean mLoading;
    public int mModeType;
    public volatile int mRotation;
    public final WindowManager mWindowManager;
    public volatile int viewHeight;
    public volatile int viewWidth;

    public interface ILoadCallback {
    }

    public interface SizeChangedCallback {
    }

    public PagViewBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mModeType = 0;
        this.mLoading = false;
        this.endStopProgress = 1.0f;
        this.mHasAttachedToWindow = false;
        this.listener = new PAGFile.LoadListener() { // from class: com.miui.clock.tiny.pets.PagViewBase$$ExternalSyntheticLambda0
        };
        this.flushRunnable = new Runnable() { // from class: com.miui.clock.tiny.pets.PagViewBase$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$3();
            }
        };
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mComposition = new PAGComposition(0L);
        this.isSystemUI = DateFormatUtils.isSystemUI(context);
        this.mRotation = DeviceConfig.getRotationInCutout(this.mContext);
        Log.d("PetsPagViewBase", "init rotation: " + this.mRotation + " --> " + this);
    }

    public boolean flush() {
        if (this.isSystemUI) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            float f = displayMetrics.density;
            int iMax = (int) (Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels) / f);
            int iMax2 = (int) (Math.max(this.viewWidth, this.viewHeight) / f);
            boolean z = (iMax2 <= 0 ? Math.max(iMax, iMax2) : iMax2) <= 670;
            if (!z) {
                Log.d("PetsPagViewBase", "flush error in phone，screenLongEdgeDp " + iMax + ", viewScreenLongEdgeDp " + iMax2);
                return z;
            }
        }
        boolean zFlush = super.flush();
        if (this.isSystemUI && zFlush) {
            int i = this.flushWidth;
            int i2 = this.viewWidth;
            if (i2 != 0 && i2 != i) {
                this.flushWidth = i2;
                Log.d("PetsPagViewBase", "successful flush width change : " + i + " to " + i2);
            }
        }
        return zFlush;
    }

    public final /* synthetic */ void lambda$new$3() {
        Log.d("PetsPagViewBase", "asyncFlush: " + this.mComposition + ", width: " + this.viewWidth);
        if (getComposition() == null || this.mComposition == null) {
            return;
        }
        Log.d("PetsPagViewBase", "asyncFlush: " + flush() + ", width: " + this.viewWidth);
    }
}
