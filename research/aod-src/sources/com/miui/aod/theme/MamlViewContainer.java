package com.miui.aod.theme;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.aod.Utils;
import com.miui.aod.components.widget.ITimeUpdate;
import com.miui.aod.util.BatteryController;
import com.miui.aod.util.BatteryStatus;
import com.miui.aod.util.CancelableWakeLockTask;
import com.miui.aod.util.DeviceInfo;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.widget.MamlClock;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.maml.component.MamlView;
import com.miui.maml.data.VariableNames;

/* JADX INFO: loaded from: classes.dex */
public class MamlViewContainer extends FrameLayout implements MamlView.OnExternCommandListener, ITimeUpdate, BatteryController.BatteryStateChangeCallback {
    public MamlClock mAodClockCallback;
    public final Configuration mConfiguration;
    public boolean mForceDisableTouchDispatch;
    public boolean mHasInitialed;
    public boolean mIsLoaded;
    public MamlView mMamlView;
    public final CancelableWakeLockTask mPauseTask;
    public boolean mPendingAnimation;
    public ImageView mThumbnail;

    public static /* synthetic */ void $r8$lambda$GKNfJ5CoBDuc43x4XLDQmGyCnFY(MamlViewContainer mamlViewContainer) {
        mamlViewContainer.executeCommand("pause");
        mamlViewContainer.onPause();
    }

    public MamlViewContainer(Context context) {
        this(context, null);
    }

    public MamlViewContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MamlViewContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Configuration configuration = new Configuration();
        this.mConfiguration = configuration;
        setWillNotDraw(true);
        this.mPauseTask = new CancelableWakeLockTask(new Runnable() { // from class: com.miui.aod.theme.MamlViewContainer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MamlViewContainer.$r8$lambda$GKNfJ5CoBDuc43x4XLDQmGyCnFY(this.f$0);
            }
        }, WakeLock.wrap(WakeLock.createDrawInner(context, "MamlViewContainer")), new Handler(Looper.getMainLooper()), "MamlViewContainer");
        this.mForceDisableTouchDispatch = false;
        this.mIsLoaded = false;
        this.mHasInitialed = false;
        configuration.updateFrom(getContext().getResources().getConfiguration());
    }

    public void executeCommand(String str) {
        Log.i("MamlViewContainer", "executeCommand: " + str);
        if (!this.mIsLoaded || TextUtils.isEmpty(str)) {
            return;
        }
        this.mMamlView.onCommand(str);
    }

    public void fresh() {
        if (this.mIsLoaded) {
            onResume();
            if (this.mMamlView.isLoaded()) {
                this.mMamlView.requestUpdate();
            }
            if (this.mPauseTask.hasScheduled()) {
                return;
            }
            this.mPauseTask.execute(1000L);
        }
    }

    @Override // com.miui.aod.components.widget.ITimeUpdate
    public void handleUpdateTime(boolean z) {
        fresh();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        BatteryController.getInstance(getContext().getApplicationContext()).addCallback(this);
    }

    @Override // com.miui.aod.util.BatteryController.BatteryStateChangeCallback
    public void onBatteryLevelChanged(BatteryStatus batteryStatus) {
        if (!batteryStatus.isCharging()) {
            setVariableState(VariableNames.BATTERY_STATE, 0);
        } else if (batteryStatus.getLevel() >= 100) {
            setVariableState(VariableNames.BATTERY_STATE, 3);
        } else {
            setVariableState(VariableNames.BATTERY_STATE, 1);
        }
        setVariableState(VariableNames.BATTERY_LEVEL, batteryStatus.getLevel());
    }

    @Override // com.miui.maml.component.MamlView.OnExternCommandListener
    public void onCommand(String str, Double d, String str2) {
        Log.i("MamlViewContainer", "onCommand: " + str + " " + d + " " + str2);
        if ("animationState".equals(str) && "init".equals(str2)) {
            this.mHasInitialed = true;
            if (this.mPendingAnimation) {
                this.mMamlView.onCommand("play");
                this.mPendingAnimation = false;
            }
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if ((this.mConfiguration.updateFrom(configuration) & AutoBottomSheetKt.ALIGN_SCREEN_END) == 0 || this.mAodClockCallback == null) {
            return;
        }
        release();
        this.mAodClockCallback.reload();
        BatteryController.getInstance(getContext().getApplicationContext()).notifyBatteryStatusChanged();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        release();
        BatteryController.getInstance(getContext().getApplicationContext()).removeCallback(this);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mForceDisableTouchDispatch || super.onInterceptTouchEvent(motionEvent);
    }

    public void onPause() {
        if (!this.mIsLoaded || this.mMamlView == null) {
            return;
        }
        Log.i("MamlViewContainer", "onPause: ");
        this.mMamlView.onPause();
    }

    public void onResume() {
        if (!this.mIsLoaded || this.mMamlView == null) {
            return;
        }
        Log.i("MamlViewContainer", "onResume: ");
        this.mMamlView.onResume();
    }

    public void release() {
        Log.i("MamlViewContainer", "release: ");
        this.mIsLoaded = false;
        this.mHasInitialed = false;
        this.mPauseTask.remove();
        MamlView mamlView = this.mMamlView;
        if (mamlView != null) {
            mamlView.setOnExternCommandListener(null);
            this.mMamlView.clearBitmapCache();
            this.mMamlView.onDestroy();
            this.mMamlView = null;
        }
    }

    public void setBatteryEnable(boolean z) {
        setVariableState("battery_enable", z ? 1 : 0);
    }

    public void setBg(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (this.mThumbnail == null) {
            ImageView imageView = new ImageView(getContext());
            this.mThumbnail = imageView;
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            addView(this.mThumbnail, new FrameLayout.LayoutParams(-1, -1));
        }
        this.mThumbnail.setImageBitmap(bitmap);
    }

    public void setCallback(MamlClock mamlClock) {
        this.mAodClockCallback = mamlClock;
    }

    public void setForceDisableTouchDispatch(boolean z) {
        this.mForceDisableTouchDispatch = z;
    }

    public boolean setLocalResourcePath(Context context, String str, int i) {
        MamlView mamlView = this.mMamlView;
        if (mamlView != null) {
            mamlView.onDestory();
        }
        removeView(this.mMamlView);
        this.mIsLoaded = false;
        this.mHasInitialed = false;
        try {
            MamlView.Config config = new MamlView.Config();
            config.path = str;
            config.mode = 3;
            config.resizeScale = (Utils.isFoldDevice() && Utils.inLargeScreen(getContext())) ? getLayoutParams().width / DeviceInfo.getRealSize().x : 1.0f;
            MamlView mamlView2 = new MamlView(context, config);
            this.mMamlView = mamlView2;
            mamlView2.setFocusable(false);
            this.mMamlView.setFocusableInTouchMode(false);
            this.mMamlView.setImportantForAccessibility(2);
            if (this.mMamlView.isLoaded()) {
                this.mIsLoaded = true;
                this.mMamlView.setCacheSize(5242880);
                this.mMamlView.setAutoFinishRoot(true);
                addView(this.mMamlView, -1, -1);
                this.mMamlView.setOnExternCommandListener(this);
                this.mPauseTask.execute(Math.min(Utils.getTemporaryStyleDuration(), Math.max(5000, i)));
            }
            if (isAttachedToWindow()) {
                BatteryController.getInstance(getContext().getApplicationContext()).notifyBatteryStatusChanged();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("setLocalResourcePath: load ");
            sb.append(this.mIsLoaded ? "success" : "failed");
            Log.i("MamlViewContainer", sb.toString());
        } catch (Exception e) {
            Log.e("MamlViewContainer", "setLocalResourcePath exception: ", e);
        }
        return this.mIsLoaded;
    }

    public void setLunarCalenderEnable(boolean z) {
        setVariableState("lunar_calendar_enable", z ? 1 : 0);
    }

    public void setNotificationEnable(boolean z) {
        setVariableState("notification_enable", z ? 1 : 0);
    }

    public void setPreviewMode(boolean z) {
        setVariableState("preview_mode", z ? 1 : 0);
    }

    public final void setVariableState(String str, int i) {
        if (this.mIsLoaded) {
            this.mMamlView.putVariableNumber(str, i);
            fresh();
        }
    }

    public void startAnimation() {
        if (!this.mHasInitialed) {
            this.mPendingAnimation = true;
        } else {
            this.mMamlView.onCommand("play");
            this.mPendingAnimation = false;
        }
    }
}
