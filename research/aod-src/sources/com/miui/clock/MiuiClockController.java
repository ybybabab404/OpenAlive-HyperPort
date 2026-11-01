package com.miui.clock;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.google.gson.Gson;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.allInOne.AllInOneUtil;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.Effect;
import com.miui.clock.task.Task;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DataUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.OtaUtils;
import com.miui.clock.utils.avoid.DepthAvoidRectProvider;
import com.miui.clock.utils.avoid.DepthBitmapAvoidConcurrent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class MiuiClockController implements DepthAvoidRectProvider, IAllInOneClockView {
    public final String TAG;
    public ContentObserver mAccessibilityHighTextContrastObserver;
    public boolean mAsync;
    public ContentObserver mBackgroundBlurEnableObserver;
    public ClockBean mClockBean;
    public ContentObserver mClockInfoListener;
    public ClockPaletteChangeListener mClockPaletteChangeListener;
    public ClockStyleInfo mClockStyleInfo;
    public IClockView mClockView;
    public ViewGroup mContainer;
    public Context mContext;
    public String mCurrentJson;
    public int mCurrentUserId;
    public final DepthBitmapAvoidConcurrent mDepthBitmapAvoidConcurrent;
    public final int mDisplayType;
    public ContentObserver mDualClockOpenObserver;
    public Gson mGson;
    public boolean mHasPackageListener;
    public boolean mHasRegisterClockInfoListener;
    public boolean mHasRegisterTimeListener;
    public ClockBean mInClockBean;
    public InitViewTask mInitTask;
    public ContentObserver mIs24HourFormatObserver;
    public LoadListener mLoadListener;
    public MiuiClockInfoListener mMiuiClockInfoListener;
    public final boolean mNeedRegisterClockInfoListener;
    public BroadcastReceiver mPackageChangeReceiver;
    public Map mPalette;
    public String mResidentTimezone;
    public ContentObserver mResidentTimezoneObserver;
    public int mSetWallpaperSupportDepth;
    public ContentObserver mSuperSaveOpenObserver;
    public BroadcastReceiver mTimezoneChangeReceiver;
    public TimeUpdateListener mUpdateTimeListener;
    public BroadcastReceiver mUpdateTimeReceiver;
    public UserHandle mUserAllHandle;
    public BroadcastReceiver mUserSwitchBroadcastReceiver;
    public ContentObserver mWallpaperSupportDepthObserver;
    public View outBackgroundBlurContainer;
    public boolean mAsyncFinish = false;
    public int mEditorVersion = -1;
    public volatile boolean isAttach = false;
    public boolean controllerDestroyed = false;
    public String mCurrentTimezone = TimeZone.getDefault().getID();
    public boolean shouldUpdateTimeZoomOnTimeUpdate = false;
    public boolean mAutoDualClock = true;
    public boolean mDualClockOpen = false;
    public boolean mShowDualClock = false;
    public boolean mHasTopMargin = true;
    public String mOwnerString = null;
    public int mClockStyle = 0;
    public int mSelectedClockPosition = 0;
    public int mLastClockPosition = 0;
    public int mShowLunarCalendar = -1;
    public float mScaleRatio = 1.0f;
    public boolean mTextDark = false;
    public int mChoosePaletteType = 0;
    public boolean mInfoTextDark = false;
    public final Handler mHandler = new Handler();
    public boolean mAutoUpdateTime = true;
    public boolean isSuperSaveOpen = false;
    public boolean wallpaperSupportDepth = false;
    public boolean mAccessibilityHighTextEnabled = false;
    public int mBackgroundBlurEnabled = -1;
    public boolean mIgnoreAccessibilityScale = false;
    public final Runnable mUpdateTimeRunnable = new Runnable() { // from class: com.miui.clock.MiuiClockController.1
        @Override // java.lang.Runnable
        public void run() {
            if (MiuiClockController.this.mClockView != null) {
                Log.d(MiuiClockController.this.TAG, "Listener updateTime");
                String timeZone = MiuiClockController.this.mClockView.getTimeZone();
                if (MiuiClockController.this.shouldUpdateTimeZoomOnTimeUpdate || !TextUtils.equals(timeZone, MiuiClockController.this.mCurrentTimezone)) {
                    String str = MiuiClockController.this.mCurrentTimezone;
                    MiuiClockController.this.mCurrentTimezone = TimeZone.getDefault().getID();
                    Log.d(MiuiClockController.this.TAG, "fixed clock timezone, oldCurrentTimezone is " + str + ", clockTimezone is " + timeZone + " mCurrentTimezone is " + MiuiClockController.this.mCurrentTimezone + " shouldUpdateTimeZoomOnTimeUpdate: " + MiuiClockController.this.shouldUpdateTimeZoomOnTimeUpdate);
                    MiuiClockController.this.mClockView.updateTimeZone(MiuiClockController.this.mCurrentTimezone);
                    MiuiClockController.this.shouldUpdateTimeZoomOnTimeUpdate = false;
                } else {
                    MiuiClockController.this.mClockView.updateTime();
                }
                if (MiuiClockController.this.mUpdateTimeListener != null) {
                    MiuiClockController.this.mUpdateTimeListener.update();
                }
            }
        }
    };
    public final ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.miui.clock.MiuiClockController$$ExternalSyntheticLambda7
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
            MiuiClockController.m641$r8$lambda$0fQvnI02SByRtVaIWieKtVDzhs(this.f$0);
        }
    };
    public final Runnable mDoodleTextListener = new DoodleTextListener(this);
    public final Runnable mAddListenerTask = new Runnable() { // from class: com.miui.clock.MiuiClockController.14
        @Override // java.lang.Runnable
        public void run() {
            if (!MiuiClockController.this.isAttach) {
                Log.e(MiuiClockController.this.TAG, "not attach" + MiuiClockController.this.mAsyncFinish);
                return;
            }
            Log.w(MiuiClockController.this.TAG, "execute: mAddListenerTask");
            MiuiClockController.this.registerDoodleTextObserver();
            MiuiClockController.this.registerTimeListener();
            MiuiClockController.this.registerPackageBroadcast();
            MiuiClockController.this.registerSuperSaveOpenListener();
            MiuiClockController.this.registerWallpaperSupportDepthListener();
            MiuiClockController.this.registerAccessibilityHighTextContrastListener();
            MiuiClockController.this.registerIs24HourFormatListener();
            if (DeviceConfig.BACKGROUND_BLUR_SUPPORTED_2) {
                MiuiClockController.this.registerBackgroundBlurEnableListener();
            }
            if (MiuiClockController.this.mNeedRegisterClockInfoListener) {
                MiuiClockController miuiClockController = MiuiClockController.this;
                miuiClockController.registerClockInfoListener(miuiClockController.isCrossUser());
            }
        }
    };
    public final Runnable mRemoveListenerTask = new Runnable() { // from class: com.miui.clock.MiuiClockController.15
        @Override // java.lang.Runnable
        public void run() {
            Log.w(MiuiClockController.this.TAG, "execute: mRemoveListenerTask");
            MiuiClockController.this.removeDoodleTextObserver();
            MiuiClockController.this.removeTimeListener();
            MiuiClockController.this.removePackageBroadcast();
            MiuiClockController.this.removeSuperSaveOpenListener();
            MiuiClockController.this.removeWallpaperSupportDepthListener();
            MiuiClockController.this.removeAccessibilityHighTextContrastListener();
            MiuiClockController.this.removeIs24HourFormatListener();
            if (DeviceConfig.BACKGROUND_BLUR_SUPPORTED_2) {
                MiuiClockController.this.removeBackgroundBlurEnableListener();
            }
            if (MiuiClockController.this.mNeedRegisterClockInfoListener) {
                MiuiClockController.this.removeClockInfoListener();
            }
        }
    };

    /* JADX INFO: renamed from: com.miui.clock.MiuiClockController$11, reason: invalid class name */
    public class AnonymousClass11 extends ContentObserver {
        /* JADX INFO: renamed from: $r8$lambda$-L-0NVBRNF0HD0ZJQicA98_w-NQ, reason: not valid java name */
        public static /* synthetic */ void m643$r8$lambda$L0NVBRNF0HD0ZJQicA98_wNQ(AnonymousClass11 anonymousClass11) {
            Log.i(MiuiClockController.this.TAG, "mClockInfoListener on change");
            MiuiClockController miuiClockController = MiuiClockController.this;
            miuiClockController.updateClockBeanFromJson(miuiClockController.isCrossUser(), false, null);
        }

        public AnonymousClass11(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            MiuiClockController.this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$11$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MiuiClockController.AnonymousClass11.m643$r8$lambda$L0NVBRNF0HD0ZJQicA98_wNQ(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.miui.clock.MiuiClockController$12, reason: invalid class name */
    public class AnonymousClass12 extends BroadcastReceiver {
        public static /* synthetic */ void $r8$lambda$PfJTPfJX6uzNxv6wdQXpDNP8gwY(AnonymousClass12 anonymousClass12, String str) {
            anonymousClass12.getClass();
            if ("android.intent.action.USER_SWITCHED".equals(str)) {
                MiuiClockController.this.updateClockBeanFromJson(true, true, null);
            }
        }

        public AnonymousClass12() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            Log.i(MiuiClockController.this.TAG, "onReceive User Switch Broadcast");
            final String action = intent.getAction();
            if ("android.intent.action.USER_SWITCHED".equals(action)) {
                DateFormatUtils.clearUserIdCache();
                DateFormatUtils.updateCurrentUserId();
                MiuiClockController.this.setCurrentUserId(intent.getIntExtra("android.intent.extra.user_handle", 0));
                MiuiClockController miuiClockController = MiuiClockController.this;
                miuiClockController.mResidentTimezone = miuiClockController.getResidentTimezone(miuiClockController.isCrossUser());
                Log.i(MiuiClockController.this.TAG, "onReceive User Switch Broadcast, update mResidentTimezone" + MiuiClockController.this.mCurrentTimezone + MiuiClockController.this.mResidentTimezone);
                MiuiClockController miuiClockController2 = MiuiClockController.this;
                miuiClockController2.mDualClockOpen = miuiClockController2.getAutoDualClock(miuiClockController2.isCrossUser()) != 0;
                MiuiClockController.this.updateDualClock();
            }
            MiuiClockController.this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$12$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MiuiClockController.AnonymousClass12.$r8$lambda$PfJTPfJX6uzNxv6wdQXpDNP8gwY(this.f$0, action);
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.miui.clock.MiuiClockController$13, reason: invalid class name */
    public class AnonymousClass13 extends ContentObserver {
        public static /* synthetic */ void $r8$lambda$RFXJTx9T5cvJJQ3Ati8oV4rFA98(AnonymousClass13 anonymousClass13) {
            Log.d(MiuiClockController.this.TAG, "Background Blur Enable Listener change");
            int iIsBackgroundBlurEnabled = DeviceConfig.isBackgroundBlurEnabled(MiuiClockController.this.mContext);
            DeviceConfig.setBackgroundBlurEnabled(iIsBackgroundBlurEnabled);
            if (MiuiClockController.this.mBackgroundBlurEnabled != iIsBackgroundBlurEnabled) {
                MiuiClockController.this.mBackgroundBlurEnabled = iIsBackgroundBlurEnabled;
                if (MiuiClockController.this.mMiuiClockInfoListener != null) {
                    MiuiClockController.this.mMiuiClockInfoListener.onBackgroundBlurEnableChange(MiuiClockController.this.mBackgroundBlurEnabled == 1);
                }
                if (MiuiClockController.this.mClockView != null) {
                    if (MiuiClockController.this.mBackgroundBlurEnabled != 1) {
                        MiuiClockController.this.mClockView.clearPassBlur();
                    }
                    MiuiClockController miuiClockController = MiuiClockController.this;
                    miuiClockController.setClockPalette(miuiClockController.mChoosePaletteType, MiuiClockController.this.mTextDark, MiuiClockController.this.mPalette);
                }
            }
        }

        public AnonymousClass13(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            MiuiClockController.this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$13$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MiuiClockController.AnonymousClass13.$r8$lambda$RFXJTx9T5cvJJQ3Ati8oV4rFA98(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.miui.clock.MiuiClockController$2, reason: invalid class name */
    public class AnonymousClass2 extends ContentObserver {
        /* JADX INFO: renamed from: $r8$lambda$grxj0Zci_TwwH4THX-0-Y0KLdAw, reason: not valid java name */
        public static /* synthetic */ void m644$r8$lambda$grxj0Zci_TwwH4THX0Y0KLdAw(AnonymousClass2 anonymousClass2) {
            boolean z = Settings.System.getInt(MiuiClockController.this.mContext.getContentResolver(), "power_supersave_mode_open", 0) != 0;
            Log.i(MiuiClockController.this.TAG, "isSuperSaveOpen on change " + z + ", previous=" + MiuiClockController.this.isSuperSaveOpen);
            boolean z2 = z != MiuiClockController.this.isSuperSaveOpen;
            MiuiClockController.this.isSuperSaveOpen = z;
            if (MiuiClockController.this.mClockView != null) {
                MiuiClockController.this.mClockView.setSuperSaveOpen(MiuiClockController.this.isSuperSaveOpen);
                if (MiuiClockController.this.isSuperSaveOpen) {
                    MiuiClockController.this.mClockView.clearColorEffect();
                }
            }
            if (MiuiClockController.this.mMiuiClockInfoListener != null) {
                MiuiClockController.this.mMiuiClockInfoListener.onSuperSaveChange(MiuiClockController.this.isSuperSaveOpen);
            }
            if (z2) {
                MiuiClockController miuiClockController = MiuiClockController.this;
                miuiClockController.resetColorOnSuperSaveChange(miuiClockController.isSuperSaveOpen);
            }
        }

        public AnonymousClass2(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            MiuiClockController.this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MiuiClockController.AnonymousClass2.m644$r8$lambda$grxj0Zci_TwwH4THX0Y0KLdAw(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.miui.clock.MiuiClockController$3, reason: invalid class name */
    public class AnonymousClass3 extends ContentObserver {
        /* JADX INFO: renamed from: $r8$lambda$hLXm4KOODzvMh3E-cApQYdoLR7w, reason: not valid java name */
        public static /* synthetic */ void m645$r8$lambda$hLXm4KOODzvMh3EcApQYdoLR7w(AnonymousClass3 anonymousClass3) {
            boolean zIsWallpaperSupportDepth = MiuiClockController.this.isWallpaperSupportDepth();
            if (MiuiClockController.this.wallpaperSupportDepth != zIsWallpaperSupportDepth) {
                MiuiClockController.this.wallpaperSupportDepth = zIsWallpaperSupportDepth;
                if (MiuiClockController.this.mClockView != null) {
                    MiuiClockController.this.mClockView.setWallpaperSupportDepth(MiuiClockController.this.wallpaperSupportDepth);
                }
            }
        }

        public AnonymousClass3(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            MiuiClockController.this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MiuiClockController.AnonymousClass3.m645$r8$lambda$hLXm4KOODzvMh3EcApQYdoLR7w(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.miui.clock.MiuiClockController$4, reason: invalid class name */
    public class AnonymousClass4 extends BroadcastReceiver {
        public AnonymousClass4() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            final String stringExtra = intent.getStringExtra("time-zone");
            final String str = MiuiClockController.this.mCurrentTimezone;
            MiuiClockController.this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MiuiClockController.AnonymousClass4 anonymousClass4 = this.f$0;
                    MiuiClockController.this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$4$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MiuiClockController.this.handleTimezoneChange(str, str);
                        }
                    });
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.miui.clock.MiuiClockController$8, reason: invalid class name */
    public class AnonymousClass8 extends ContentObserver {
        /* JADX INFO: renamed from: $r8$lambda$l-nrAgWtMv_gvyh_3u1OTTZMrqI, reason: not valid java name */
        public static /* synthetic */ void m646$r8$lambda$lnrAgWtMv_gvyh_3u1OTTZMrqI(AnonymousClass8 anonymousClass8) {
            boolean zIsHighTextContrastEnabled = DeviceConfig.isHighTextContrastEnabled(MiuiClockController.this.mContext);
            if (MiuiClockController.this.mAccessibilityHighTextEnabled != zIsHighTextContrastEnabled) {
                MiuiClockController.this.mAccessibilityHighTextEnabled = zIsHighTextContrastEnabled;
                if (MiuiClockController.this.mMiuiClockInfoListener != null) {
                    MiuiClockController.this.mMiuiClockInfoListener.onAccessibilityHighTextEnableChange(MiuiClockController.this.mAccessibilityHighTextEnabled);
                }
                if (MiuiClockController.this.mClockView != null) {
                    MiuiClockController.this.mClockView.onHighTextContrastEnabledChanged(MiuiClockController.this.mAccessibilityHighTextEnabled);
                }
            }
        }

        public AnonymousClass8(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            MiuiClockController.this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MiuiClockController.AnonymousClass8.m646$r8$lambda$lnrAgWtMv_gvyh_3u1OTTZMrqI(this.f$0);
                }
            });
        }
    }

    public interface ClockPaletteChangeListener {
        void onInfoColorChange(boolean z);

        void onPaletteChange(boolean z, boolean z2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ClockStyle {
    }

    public static class DoodleTextListener implements Runnable {
        public final WeakReference mRef;

        public DoodleTextListener(MiuiClockController miuiClockController) {
            this.mRef = new WeakReference(miuiClockController);
        }

        @Override // java.lang.Runnable
        public void run() {
            ClockStyleInfo clockStyleInfo;
            MiuiClockController miuiClockController = (MiuiClockController) this.mRef.get();
            if (miuiClockController == null || miuiClockController.mClockView == null || (clockStyleInfo = miuiClockController.mClockView.getClockStyleInfo()) == null || !"doodle".equals(clockStyleInfo.getType())) {
                return;
            }
            Log.d("DoodleText", "onChange triggered, refresh clock");
            miuiClockController.mClockView.updateTime();
        }
    }

    public interface IClockView {
        default void asyncInitializeClockStyleAfterInflation(ClockStyleInfo clockStyleInfo) {
        }

        default Rect calculateColorPickupRect(Boolean bool, Rect rect) {
            return new Rect();
        }

        default void checkNeedRelayoutAfterShown() {
        }

        default void clearColorEffect() {
            clearColorEffect(true);
        }

        default void clearColorEffect(boolean z) {
        }

        default void clearPassBlur() {
            clearColorEffect(true);
        }

        default View getBackgroundBlurContainer() {
            return null;
        }

        int getClockHeight();

        default ClockStyleInfo getClockStyleInfo() {
            return null;
        }

        float getClockVisibleHeight();

        default Rect getDepthAvoidRect() {
            return new Rect();
        }

        default int getGalleryGravity() {
            return 8388611;
        }

        default float[] getGradientAnimationParams() {
            return Effect.hiddenGradientParams(getGradientParams());
        }

        default float[] getGradientParams() {
            return Effect.EMPTY_GRADIENT;
        }

        default String getHealthJson() {
            return "";
        }

        View getIClockView(ClockViewType clockViewType);

        default String getLocalCity() {
            return null;
        }

        default int getMagazineColor() {
            return 0;
        }

        int getNotificationClockBottom();

        default int getNotificationClockTop() {
            return 0;
        }

        default int getNotificationRelativePosition() {
            return 1;
        }

        default int getTextViewSize() {
            return 0;
        }

        default TextView getTimeView() {
            return null;
        }

        default String getTimeZone() {
            return null;
        }

        float getTopMargin();

        default String getWeatherJson() {
            return "";
        }

        default void ignoreAccessibilityScale(boolean z) {
        }

        default void onHighTextContrastEnabledChanged(boolean z) {
        }

        default void resetClock() {
        }

        default void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        }

        default void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
            setAllInOneClockRectParams(region, f, f2, i, i2, i3);
        }

        default void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
            setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
        }

        default void setBackgroundBlurContainer(View view) {
        }

        void setClockAlpha(float f);

        default void setClockPalette(int i, boolean z, Map map, boolean z2) {
        }

        void setClockStyleInfo(ClockStyleInfo clockStyleInfo);

        default void setClockViewRect(Rect rect) {
        }

        default void setCurrentUserId(int i) {
        }

        default void setEditMode(boolean z) {
        }

        default void setEditorColonShow(boolean z) {
        }

        default void setEditorColonViewVisible(boolean z) {
        }

        default void setGpuOptimize(boolean z) {
        }

        default void setGradientIndex(int i) {
            if (getClockStyleInfo() != null) {
                getClockStyleInfo().setGradientIndex(i);
            }
        }

        default void setInfoTextColorDark(boolean z) {
        }

        default void setLocalCity(String str) {
        }

        void setMagazineInfoVisible(boolean z);

        default void setMinuteColonAlpha(float f) {
        }

        default void setMinuteColor(int i, int i2) {
        }

        void setOwnerInfo(String str);

        void setScaleRatio(float f);

        void setShowLunarCalendar(boolean z);

        default void setSuperSaveOpen(boolean z) {
            if (getClockStyleInfo() != null) {
                getClockStyleInfo().setSuperSaveOpen(z);
            }
        }

        void setTextColorDark(boolean z);

        default void setWallpaperSupportDepth(boolean z) {
        }

        default void updateClockBlendColor(int i, int i2) {
            updateClockBlendColor(i, i2, 0, 0);
        }

        default void updateClockBlendColor(int i, int i2, int i3, int i4) {
        }

        default void updateClockGlassBrightness(boolean z) {
        }

        default void updateColor() {
        }

        default void updateGradientEffectColor(float[] fArr) {
        }

        void updateResidentTimeZone(String str);

        void updateTime();

        void updateTimeZone(String str);

        void updateViewTopMargin(boolean z);
    }

    public static class InitViewTask extends AsyncTask<Void, Void, View> {
        public ClockBean mClockBean;
        public final WeakReference weakReferenceContext;
        public WeakReference weakReferenceController;
        public final WeakReference weakReferenceInflateContext;

        public InitViewTask(Context context, Context context2, MiuiClockController miuiClockController, ClockBean clockBean) {
            this.weakReferenceController = new WeakReference(miuiClockController);
            this.weakReferenceContext = new WeakReference(context);
            this.weakReferenceInflateContext = new WeakReference(context2);
            this.mClockBean = clockBean;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.AsyncTask
        public View doInBackground(Void... voidArr) {
            DateFormatUtils.updateCurrentUserId();
            DeviceConfig.getIntForUserMethod();
            DeviceConfig.isFlip();
            FontUtils.preInitFont();
            Context context = (Context) this.weakReferenceContext.get();
            if (context == null) {
                Log.e("MiuiClockController", "doInBackground weakReferenceContext is null");
                return null;
            }
            Context context2 = (Context) this.weakReferenceInflateContext.get();
            if (context2 == null) {
                Log.e("MiuiClockController", "doInBackground weakReferenceInflateContext is null");
                return null;
            }
            DeviceConfig.supportBackgroundBlur(context);
            DateFormatUtils.initDataFormat(context);
            DataUtils.initAppInstalledInfo(context);
            MiuiClockController miuiClockController = (MiuiClockController) this.weakReferenceController.get();
            if (miuiClockController == null) {
                Log.e("MiuiClockController", "doInBackground weakReferenceController is null");
                return null;
            }
            miuiClockController.initDataAsync();
            if ("old".equals(this.mClockBean.getTemplateId())) {
                return miuiClockController.inflateClockView();
            }
            miuiClockController.mClockStyleInfo = ClockStyleInfo.convertInfoFromClockBean(context, this.mClockBean, miuiClockController.mDisplayType);
            View viewInflate = LayoutInflater.from(context2).inflate(miuiClockController.mClockStyleInfo.getLayoutId(), miuiClockController.mContainer, false);
            if (viewInflate instanceof IClockView) {
                ((IClockView) viewInflate).asyncInitializeClockStyleAfterInflation(miuiClockController.mClockStyleInfo);
            }
            return viewInflate;
        }

        @Override // android.os.AsyncTask
        public void onCancelled() {
            super.onCancelled();
            Log.d("MiuiClockController", "InitViewTask is onCancelled");
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(View view) {
            super.onPostExecute(view);
            MiuiClockController miuiClockController = (MiuiClockController) this.weakReferenceController.get();
            Log.d("MiuiClockController", "InitViewTask onPostExecute view: " + view);
            if (miuiClockController != null && view != null) {
                miuiClockController.addClockView(view);
                return;
            }
            Log.e("MiuiClockController", "onPostExecute weakReferenceController is " + miuiClockController + ", view is " + view);
        }
    }

    public interface LoadListener {
        void loadFinish();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LunarCalendarStyle {
    }

    public interface MiuiClockInfoListener {
        default void onAccessibilityHighTextEnableChange(boolean z) {
        }

        default void onBackgroundBlurEnableChange(boolean z) {
        }

        void onClockBeanChange(ClockBean clockBean, boolean z);

        void onDualClockChange(boolean z);

        void onMiuiClockInfoChange(ClockBean clockBean, boolean z);

        default void onSuperSaveChange(boolean z) {
        }
    }

    public interface TimeUpdateListener {
        void update();
    }

    /* JADX INFO: renamed from: $r8$lambda$-8PjrWcVHYu_YSd1Z9mTysYgQG4, reason: not valid java name */
    public static /* synthetic */ void m640$r8$lambda$8PjrWcVHYu_YSd1Z9mTysYgQG4(MiuiClockController miuiClockController, boolean z) {
        Log.i(miuiClockController.TAG, "registerClockInfoListener re-read after observer registered");
        miuiClockController.updateClockBeanFromJson(z, false, null);
    }

    /* JADX INFO: renamed from: $r8$lambda$0fQvnI02SByRtVaIWie-KtVDzhs, reason: not valid java name */
    public static /* synthetic */ void m641$r8$lambda$0fQvnI02SByRtVaIWieKtVDzhs(MiuiClockController miuiClockController) {
        if (miuiClockController.mClockView == null || !miuiClockController.mContainer.isShown()) {
            return;
        }
        miuiClockController.mClockView.checkNeedRelayoutAfterShown();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void $r8$lambda$HPTw_UDy71zjRMxLYFfQTkK8QhA(MiuiClockController miuiClockController, View view) {
        miuiClockController.getClass();
        IClockView iClockView = (IClockView) view;
        miuiClockController.mClockView = iClockView;
        if (iClockView != null) {
            iClockView.setCurrentUserId(miuiClockController.mCurrentUserId);
            miuiClockController.mClockView.ignoreAccessibilityScale(miuiClockController.mIgnoreAccessibilityScale);
            ClockStyleInfo clockStyleInfo = miuiClockController.mClockStyleInfo;
            if (clockStyleInfo != null) {
                clockStyleInfo.setSuperSaveOpen(miuiClockController.isSuperSaveOpen);
                miuiClockController.mClockStyleInfo.setShouldObserveSettings();
            }
            miuiClockController.mClockView.setClockStyleInfo(miuiClockController.mClockStyleInfo);
            boolean zIsWallpaperSupportDepth = miuiClockController.isWallpaperSupportDepth();
            miuiClockController.mClockView.setBackgroundBlurContainer(miuiClockController.outBackgroundBlurContainer);
            miuiClockController.mClockView.setClockPalette(miuiClockController.mChoosePaletteType, miuiClockController.mTextDark, miuiClockController.mPalette, zIsWallpaperSupportDepth);
            miuiClockController.mClockView.setInfoTextColorDark(miuiClockController.mInfoTextDark);
            miuiClockController.mClockView.updateResidentTimeZone(miuiClockController.mResidentTimezone);
            miuiClockController.mClockView.updateTimeZone(miuiClockController.mCurrentTimezone);
            miuiClockController.mClockView.setShowLunarCalendar(miuiClockController.getShowLunarCalendar());
            miuiClockController.mClockView.setScaleRatio(miuiClockController.mScaleRatio);
            miuiClockController.mClockView.setTextColorDark(miuiClockController.mTextDark);
            miuiClockController.mClockView.updateViewTopMargin(miuiClockController.mHasTopMargin);
            miuiClockController.mClockView.setOwnerInfo(miuiClockController.mOwnerString);
        }
        miuiClockController.mContainer.addView(view);
        if (view != 0) {
            view.dispatchConfigurationChanged(view.getResources().getConfiguration());
        }
        MiuiClockInfoListener miuiClockInfoListener = miuiClockController.mMiuiClockInfoListener;
        if (miuiClockInfoListener != null) {
            miuiClockInfoListener.onClockBeanChange(miuiClockController.mClockBean, false);
        }
        miuiClockController.mAsyncFinish = true;
        if (miuiClockController.isAttach) {
            miuiClockController.onAttachedToWindow();
        }
        LoadListener loadListener = miuiClockController.mLoadListener;
        if (loadListener != null) {
            loadListener.loadFinish();
        }
    }

    public static /* synthetic */ void $r8$lambda$l8iLjr0BcbP7kcP6kbSYH7Ycrq8(MiuiClockController miuiClockController) {
        miuiClockController.getClass();
        miuiClockController.handleTimezoneChange(TimeZone.getDefault().getID(), miuiClockController.mCurrentTimezone);
        TimeUpdateListener timeUpdateListener = miuiClockController.mUpdateTimeListener;
        if (timeUpdateListener != null) {
            timeUpdateListener.update();
        }
    }

    public MiuiClockController(Context context, ViewGroup viewGroup, ClockBean clockBean, int i, boolean z) {
        this.mAsync = false;
        this.mContext = context;
        this.mContainer = viewGroup;
        String str = isAOD() ? "MiuiClockController Aod" : "MiuiClockController";
        this.TAG = str;
        this.mDepthBitmapAvoidConcurrent = new DepthBitmapAvoidConcurrent(context, this);
        boolean zIsCrossUser = isCrossUser();
        if (zIsCrossUser) {
            DateFormatUtils.clearUserIdCache();
            updateCurrentUserId();
        }
        this.mDisplayType = i;
        this.mAsync = z;
        Log.i(str, "new MiuiClockController setClockBean = " + clockBean + ", type = " + i);
        initData();
        StringBuilder sb = new StringBuilder();
        sb.append("pkg name = ");
        sb.append(context.getPackageName());
        Log.i(str, sb.toString());
        if (clockBean == null) {
            this.mNeedRegisterClockInfoListener = true;
            String clockInfoJson = getClockInfoJson(zIsCrossUser);
            if (zIsCrossUser) {
                this.mCurrentJson = clockInfoJson;
            }
            clockBean = getClockBeanFromSetting(clockInfoJson);
        } else {
            this.mNeedRegisterClockInfoListener = false;
        }
        this.mInClockBean = clockBean;
    }

    public void addClockView(final View view) {
        this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MiuiClockController.$r8$lambda$HPTw_UDy71zjRMxLYFfQTkK8QhA(this.f$0, view);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void addClockView(ClockBean clockBean, boolean z) {
        ClockStyleInfo clockStyleInfoConvertInfoFromClockBean;
        View viewInflate;
        if (this.controllerDestroyed) {
            Log.e(this.TAG, "Systemui controllerDestroyed, not to addClockView clockBean" + clockBean + " mClockBean" + this.mClockBean + this);
            return;
        }
        Log.i(this.TAG, "addClockView clockBean= [" + clockBean + "], mClockBean = [" + this.mClockBean + "]" + this);
        if (this.mClockBean != clockBean) {
            this.mClockBean = clockBean;
        }
        if (this.mShowDualClock && !unSupportDualClockTemplate()) {
            String dualClockLocalCity = clockBean.getDualClockLocalCity();
            ClockBean clockBean2 = new ClockBean("dual");
            clockBean2.setDualClockLocalCity(dualClockLocalCity);
            clockBean = clockBean2;
        }
        this.mContainer.removeView((View) this.mClockView);
        if ("old".equals(clockBean.getTemplateId())) {
            clockStyleInfoConvertInfoFromClockBean = null;
            viewInflate = inflateClockView();
        } else {
            clockStyleInfoConvertInfoFromClockBean = ClockStyleInfo.convertInfoFromClockBean(this.mContext, clockBean, this.mDisplayType);
            viewInflate = LayoutInflater.from(getInflateContext()).inflate(clockStyleInfoConvertInfoFromClockBean.getLayoutId(), this.mContainer, false);
        }
        this.mContainer.addView(viewInflate);
        IClockView iClockView = (IClockView) viewInflate;
        this.mClockView = iClockView;
        if (iClockView != null) {
            iClockView.setCurrentUserId(this.mCurrentUserId);
            this.mClockView.ignoreAccessibilityScale(this.mIgnoreAccessibilityScale);
            if (clockStyleInfoConvertInfoFromClockBean != null) {
                clockStyleInfoConvertInfoFromClockBean.setSuperSaveOpen(this.isSuperSaveOpen);
                clockStyleInfoConvertInfoFromClockBean.setShouldObserveSettings();
            }
            this.mClockView.setClockStyleInfo(clockStyleInfoConvertInfoFromClockBean);
            boolean zIsWallpaperSupportDepth = isWallpaperSupportDepth();
            this.mClockView.setBackgroundBlurContainer(this.outBackgroundBlurContainer);
            this.mClockView.setClockPalette(this.mChoosePaletteType, this.mTextDark, this.mPalette, zIsWallpaperSupportDepth);
            this.mClockView.setInfoTextColorDark(this.mInfoTextDark);
            this.mClockView.updateResidentTimeZone(this.mResidentTimezone);
            this.mClockView.updateTimeZone(this.mCurrentTimezone);
            this.mClockView.setShowLunarCalendar(getShowLunarCalendar());
            this.mClockView.setScaleRatio(this.mScaleRatio);
            this.mClockView.setTextColorDark(this.mTextDark);
            this.mClockView.updateViewTopMargin(this.mHasTopMargin);
            this.mClockView.setOwnerInfo(this.mOwnerString);
        }
        MiuiClockInfoListener miuiClockInfoListener = this.mMiuiClockInfoListener;
        if (miuiClockInfoListener != null) {
            miuiClockInfoListener.onClockBeanChange(this.mClockBean, z);
        }
        Log.d(this.TAG, "devices_info:  show_values_flag:" + this.mContext.getString(R.string.show_values_flag));
        Log.d(this.TAG, "devices_info:  smallestScreenWidthDp:" + this.mContext.getResources().getConfiguration().smallestScreenWidthDp);
        Log.d(this.TAG, "devices_info:  densityDpi:" + this.mContext.getResources().getConfiguration().densityDpi);
        Log.d(this.TAG, "devices_info:  screenHeightDp:" + this.mContext.getResources().getConfiguration().screenHeightDp);
        Log.d(this.TAG, "devices_info:  screenWidthDp:" + this.mContext.getResources().getConfiguration().screenWidthDp);
    }

    public final void addClockViewAsync(ClockBean clockBean) {
        if (this.mClockBean != clockBean) {
            this.mClockBean = clockBean;
        }
        if (this.mShowDualClock && !unSupportDualClockTemplate()) {
            String dualClockLocalCity = clockBean.getDualClockLocalCity();
            ClockBean clockBean2 = new ClockBean("dual");
            clockBean2.setDualClockLocalCity(dualClockLocalCity);
            clockBean = clockBean2;
        }
        this.mContainer.removeView((View) this.mClockView);
        InitViewTask initViewTask = this.mInitTask;
        if (initViewTask != null) {
            initViewTask.cancel(true);
            this.mInitTask = null;
        }
        InitViewTask initViewTask2 = new InitViewTask(this.mContext, getInflateContext(), this, clockBean);
        this.mInitTask = initViewTask2;
        initViewTask2.execute(new Void[0]);
    }

    public void build() {
        ClockBean clockBean = this.mInClockBean;
        if (clockBean != null) {
            addClockView(clockBean, false);
        }
    }

    public void buildAsync() {
        ClockBean clockBean = this.mInClockBean;
        if (clockBean != null) {
            this.mAsync = true;
            addClockViewAsync(clockBean);
        }
    }

    public Rect calculateColorPickupRect(Boolean bool, Rect rect) {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            return iClockView.calculateColorPickupRect(bool, rect);
        }
        Log.d(this.TAG, "calculateColorPickupRect: mClockView is null, return empty");
        return new Rect();
    }

    public void clearClockBlur() {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.clearPassBlur();
        }
    }

    public final ClockBean dealWithUpgradeToOS4(ClockBean clockBean) {
        if (clockBean == null) {
            return ClockBean.getClassicDefaultBean(this.mContext);
        }
        try {
            return AllInOneUtil.needMergeToAllInOne(clockBean.getTemplateId()) ? AllInOneUtil.dealWithOTAClockBean(clockBean, this.mContext) : clockBean;
        } catch (Exception e) {
            Log.w(this.TAG, "dealWithUpgradeToOS4 failed", e);
            return clockBean;
        }
    }

    public final void dealWithVersionUpgrade(ClockBean clockBean) {
        if (clockBean == null) {
            Log.d(this.TAG, "dealWithVersionUpgrade: bean is null");
            return;
        }
        if (clockBean.getTemplateId().equals("classic")) {
            OtaUtils.dealWithClassicOTATextArea(clockBean);
        } else if (clockBean.getTemplateId().equals("classic_plus")) {
            OtaUtils.dealWithClassicPlusOTAFont(clockBean);
            OtaUtils.dealWithClassicPlusOTATextArea(clockBean);
        }
        OtaUtils.dealWithClassicOTAColorEffect(this.mContext, clockBean);
    }

    public final int getAutoDualClock(boolean z) {
        if (!z) {
            return Settings.System.getInt(this.mContext.getContentResolver(), "auto_dual_clock", 0);
        }
        try {
            Class cls = Integer.TYPE;
            return ((Integer) Settings.System.class.getMethod("getIntForUser", ContentResolver.class, String.class, cls, cls).invoke(null, this.mContext.getContentResolver(), "auto_dual_clock", 0, Integer.valueOf(this.mCurrentUserId))).intValue();
        } catch (Exception e) {
            Log.e(this.TAG, "getIntForUser fail", e);
            return 0;
        }
    }

    public View getBackgroundBlurContainer() {
        IClockView iClockView = this.mClockView;
        if (iClockView == null) {
            Log.d(this.TAG, "getBackgroundBlurContainer is null " + this.mClockView);
            return null;
        }
        View backgroundBlurContainer = iClockView.getBackgroundBlurContainer();
        Log.d(this.TAG, "getBackgroundBlurContainer  " + backgroundBlurContainer);
        return backgroundBlurContainer;
    }

    public ClockBean getClockBean() {
        return this.mClockBean;
    }

    public final ClockBean getClockBeanFromSetting(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("clockInfo");
            if (this.mGson == null) {
                this.mGson = new Gson();
            }
            ClockBean clockBean = (ClockBean) this.mGson.fromJson(string, ClockBean.class);
            int i = jSONObject.has("version") ? jSONObject.getInt("version") : -1;
            Log.d(this.TAG, "getClockBeanFromSetting: version is= " + i);
            if (i < 3) {
                Log.d(this.TAG, "deal with OTA from os1");
                dealWithVersionUpgrade(clockBean);
            }
            if (i >= 4) {
                return clockBean;
            }
            Log.d(this.TAG, "deal with OTA to os4");
            return dealWithUpgradeToOS4(clockBean);
        } catch (Exception e) {
            Log.e(this.TAG, "getClockBeanFromSetting fail, msg = " + e);
            return ClockBean.getClassicDefaultBean(this.mContext);
        }
    }

    public int getClockHeight() {
        return this.mClockView.getClockHeight();
    }

    public final String getClockInfoJson(boolean z) {
        String str;
        if (!z) {
            return Settings.Secure.getString(this.mContext.getContentResolver(), "constant_lockscreen_info");
        }
        String str2 = "";
        try {
            String constantTemplateEditorInfo = getConstantTemplateEditorInfo();
            if (constantTemplateEditorInfo == null || constantTemplateEditorInfo.isEmpty()) {
                str = (String) Settings.Secure.class.getMethod("getStringForUser", ContentResolver.class, String.class, Integer.TYPE).invoke(null, this.mContext.getContentResolver(), "constant_lockscreen_info", Integer.valueOf(this.mCurrentUserId));
            } else {
                Pair lockscreenInfo = getLockscreenInfo(constantTemplateEditorInfo);
                this.mEditorVersion = ((Integer) lockscreenInfo.first).intValue();
                str = (String) lockscreenInfo.second;
            }
            str2 = str;
            Log.i(this.TAG, "getStringForUser, mCurrentUserId = " + this.mCurrentUserId + ", keyguardVersion=" + this.mEditorVersion + ", json = " + str2);
            return str2;
        } catch (Exception e) {
            Log.e(this.TAG, "getStringForUser fail", e);
            return str2;
        }
    }

    public Map getClockPalette() {
        return this.mPalette;
    }

    public int getClockPaletteType() {
        return this.mChoosePaletteType;
    }

    public int getClockPosition() {
        int i;
        if (this.mShowDualClock && !unSupportDualClockTemplate()) {
            return 101;
        }
        int i2 = this.mClockStyle;
        if (i2 != 0) {
            return i2;
        }
        ClockBean clockBean = this.mClockBean;
        return (clockBean == null || !Objects.equals(clockBean.getTemplateId(), "old") || (i = this.mSelectedClockPosition) == 0) ? getDefaultClockPosition() : i;
    }

    public ClockStyleInfo getClockStyleInfo() {
        IClockView iClockView = this.mClockView;
        if (iClockView == null) {
            return null;
        }
        return iClockView.getClockStyleInfo();
    }

    public float getClockVisibleHeight() {
        return this.mClockView.getClockVisibleHeight();
    }

    public final String getConstantTemplateEditorInfo() {
        String str;
        try {
            str = (String) Settings.Secure.class.getMethod("getStringForUser", ContentResolver.class, String.class, Integer.TYPE).invoke(null, this.mContext.getContentResolver(), "constant_template_editor_info", Integer.valueOf(this.mCurrentUserId));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            str = "";
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            str = "";
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
            str = "";
        }
        Log.d(this.TAG, "templateEditorInfo=" + str);
        return str;
    }

    public int getDefaultClockPosition() {
        String str = Build.DEVICE;
        return ("davinci".equals(str) || "davinciin".equals(str) || "raphael".equals(str) || "raphaelin".equals(str) || "chiron".equals(str) || "polaris".equals(str)) ? 3 : 0;
    }

    public Rect getDepthAvoidRect() {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            return iClockView.getDepthAvoidRect();
        }
        Log.d(this.TAG, "getDepthAvoidRect: mClockView is null, return empty");
        return new Rect();
    }

    public int getGalleryGravity() {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            return iClockView.getGalleryGravity();
        }
        return 8388611;
    }

    public String getHealthJson() {
        IClockView iClockView = this.mClockView;
        return iClockView != null ? iClockView.getHealthJson() : "";
    }

    public View getIClockView(ClockViewType clockViewType) {
        return this.mClockView.getIClockView(clockViewType);
    }

    public IClockView getIClockView() {
        return this.mClockView;
    }

    public final Context getInflateContext() {
        Context context = this.mContext;
        if (context != null && context.getApplicationContext() == null) {
            Log.i(this.TAG, "getInflateContext: mContext");
            return this.mContext;
        }
        ViewGroup viewGroup = this.mContainer;
        if (viewGroup == null || viewGroup.getContext() == null) {
            Log.i(this.TAG, "getInflateContext: fallback");
            return this.mContext;
        }
        Log.i(this.TAG, "getInflateContext: container");
        return this.mContainer.getContext();
    }

    public String getLocalCity() {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            return iClockView.getLocalCity();
        }
        return null;
    }

    public final Pair getLockscreenInfo(String str) {
        int i;
        String string = "";
        try {
            JSONObject jSONObject = new JSONObject(str);
            string = jSONObject.getString("lockscreenInfo");
            i = jSONObject.getInt("version");
        } catch (Exception unused) {
            Log.d(this.TAG, "getClockInfo error, return!");
            i = -1;
        }
        return Pair.create(Integer.valueOf(i), string);
    }

    public int getMagazineColor() {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            return iClockView.getMagazineColor();
        }
        return 0;
    }

    public int getNotificationClockBottom() {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            return iClockView.getNotificationClockBottom();
        }
        return 0;
    }

    public int getNotificationRelativePosition() {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            return iClockView.getNotificationRelativePosition();
        }
        return 1;
    }

    public final String getResidentTimezone(boolean z) {
        if (!z) {
            return Settings.System.getString(this.mContext.getContentResolver(), "resident_timezone");
        }
        try {
            return (String) Settings.System.class.getMethod("getStringForUser", ContentResolver.class, String.class, Integer.TYPE).invoke(null, this.mContext.getContentResolver(), "resident_timezone", Integer.valueOf(this.mCurrentUserId));
        } catch (Exception e) {
            Log.e(this.TAG, "getIntForUser fail", e);
            return "";
        }
    }

    public final boolean getShowLunarCalendar() {
        int i = this.mShowLunarCalendar;
        if (i != -1) {
            return i == 1;
        }
        return Settings.System.getInt(this.mContext.getContentResolver(), "show_lunar_calendar", 0) == 1;
    }

    public float getTopMargin() {
        return this.mClockView.getTopMargin();
    }

    public String getWeatherJson() {
        IClockView iClockView = this.mClockView;
        return iClockView != null ? iClockView.getWeatherJson() : "";
    }

    public final void handleTimezoneChange(String str, String str2) {
        if (this.mHasRegisterTimeListener) {
            String id = TimeZone.getDefault().getID();
            Log.d(this.TAG, "TimezoneChangeReceiver time zone changed， onReceive oldTimezone " + str2 + " receiveTimezone:" + str + ", mCurrentTimezone : " + this.mCurrentTimezone + "systemTimeZone : " + id);
            if (!TextUtils.equals(str2, this.mCurrentTimezone) || !TextUtils.equals(str, id)) {
                this.shouldUpdateTimeZoomOnTimeUpdate = true;
            }
            if (TextUtils.isEmpty(str)) {
                str = id;
            }
            this.mCurrentTimezone = str;
            updateDualClock();
            IClockView iClockView = this.mClockView;
            if (iClockView != null) {
                iClockView.updateTimeZone(this.mCurrentTimezone);
            }
        }
    }

    public void ignoreAccessibilityScale(boolean z) {
        this.mIgnoreAccessibilityScale = z;
    }

    public View inflateClockView() {
        int i;
        int i2;
        ApplicationInfo applicationInfo;
        Bundle bundle;
        int clockPosition = getClockPosition();
        if (clockPosition == 1) {
            i = R.layout.miui_center_horizontal_clock;
        } else if (clockPosition == 2) {
            i = R.layout.miui_left_top_clock;
        } else if (clockPosition == 3) {
            i = R.layout.miui_vertical_clock;
        } else if (clockPosition == 4) {
            i = R.layout.miui_left_top_large_clock;
        } else if (clockPosition != 101) {
            try {
                PackageInfo packageInfo = this.mContext.getPackageManager().getPackageInfo("com.android.systemui", 128);
                i2 = (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null || (bundle = applicationInfo.metaData) == null) ? 0 : bundle.getInt("miui.systemui_base_version", 0);
            } catch (Exception e) {
                Log.e(this.TAG, "Context.getPackageManager().getPackageInfo not found", e);
            }
            i = i2 < 11 ? R.layout.miui_center_horizontal_clock : R.layout.miui_left_top_large_clock;
        } else {
            i = R.layout.miui_dual_clock;
        }
        return LayoutInflater.from(getInflateContext()).inflate(i, this.mContainer, false);
    }

    public final void initData() {
        this.mSelectedClockPosition = Settings.System.getInt(this.mContext.getContentResolver(), "selected_keyguard_clock_position", 0);
        this.mDualClockOpen = getAutoDualClock(isCrossUser()) != 0;
        String residentTimezone = getResidentTimezone(isCrossUser());
        this.mResidentTimezone = residentTimezone;
        this.mShowDualClock = (!this.mDualClockOpen || residentTimezone == null || residentTimezone.equals(this.mCurrentTimezone)) ? false : true;
        this.isSuperSaveOpen = Settings.System.getInt(this.mContext.getContentResolver(), "power_supersave_mode_open", 0) != 0;
        if (!this.mAsync) {
            this.wallpaperSupportDepth = isWallpaperSupportDepth();
            this.mAccessibilityHighTextEnabled = DeviceConfig.isHighTextContrastEnabled(this.mContext);
        }
        int iIsBackgroundBlurEnabled = DeviceConfig.isBackgroundBlurEnabled(this.mContext);
        this.mBackgroundBlurEnabled = iIsBackgroundBlurEnabled;
        DeviceConfig.setBackgroundBlurEnabled(iIsBackgroundBlurEnabled);
    }

    public final void initDataAsync() {
        this.wallpaperSupportDepth = isWallpaperSupportDepth();
        this.mAccessibilityHighTextEnabled = DeviceConfig.isHighTextContrastEnabled(this.mContext);
        this.mContext.getDrawable(R.drawable.classic_placeholder_icon);
        this.mContext.getDrawable(R.drawable.weather_icon_sun_up);
        this.mContext.getDrawable(R.drawable.weather_icon_sun_down);
        this.mContext.getString(R.string.no_data_placeholder_single_line_string);
        this.mContext.getString(R.string.classic_calendar_day_of_week_format);
        this.mContext.getString(R.string.format_full_month_of_year);
        this.mContext.getString(R.string.format_full_day_of_week);
        this.mContext.getResources().getDimension(R.dimen.classic_text_area_text_size);
        this.mContext.getResources().getDimensionPixelSize(R.dimen.health_icon_18);
        this.mContext.getResources().getDimensionPixelSize(R.dimen.text_placeholder_width_small);
        this.mContext.getResources().getDimensionPixelSize(R.dimen.text_placeholder_height_small);
        this.mContext.getResources().getDimensionPixelSize(R.dimen.text_placeholder_padding);
        this.mContext.getResources().getDimensionPixelSize(R.dimen.text_placeholder_radius);
    }

    public final boolean isAOD() {
        return "com.miui.aod".equals(this.mContext.getPackageName());
    }

    public final boolean isClockInfoEquals(String str, String str2) {
        if (TextUtils.equals(str, str2)) {
            return true;
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = new JSONObject(str2);
            int iOptInt = jSONObject.optInt("version", -1);
            int iOptInt2 = jSONObject2.optInt("version", -1);
            if (iOptInt == iOptInt2) {
                boolean zEquals = TextUtils.equals(jSONObject.optString("clockInfo", null), jSONObject2.optString("clockInfo", null));
                if (!zEquals) {
                    Log.i(this.TAG, "isClockInfoEquals clockInfo diff");
                }
                return zEquals;
            }
            Log.i(this.TAG, "isClockInfoEquals version diff: " + iOptInt + " vs " + iOptInt2);
            return false;
        } catch (Exception e) {
            Log.w(this.TAG, "isClockInfoEquals parse error", e);
            return TextUtils.equals(str, str2);
        }
    }

    public final boolean isCrossUser() {
        return isSystemUI() || isAOD();
    }

    public boolean isDualClock() {
        return this.mShowDualClock;
    }

    public final boolean isSystemUI() {
        return "com.android.systemui".equals(this.mContext.getPackageName());
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0038  */
    public final boolean isWallpaperSupportDepth() {
        int i = this.mSetWallpaperSupportDepth;
        boolean z = false;
        if (i != 0) {
            return i == 1;
        }
        if (isCrossUser()) {
            try {
                if (((Integer) DeviceConfig.getIntForUserMethod().invoke(null, this.mContext.getContentResolver(), "wallpaper_matting_support_2", 0, Integer.valueOf(this.mCurrentUserId))).intValue() == 1) {
                    z = true;
                }
            } catch (Exception e) {
                Log.e(this.TAG, "getIntForUser fail", e);
            }
        } else if (Settings.Secure.getInt(this.mContext.getContentResolver(), "wallpaper_matting_support_2", 0) == 1) {
            z = true;
        }
        Log.i(this.TAG, "isWallpaperSupportDepth on change " + z);
        return z;
    }

    public void onAttachedToWindow() {
        setIsAttach(true);
        if (!this.mAsync || this.mAsyncFinish) {
            registerListener();
        }
        if (isSystemUI()) {
            this.mContainer.getViewTreeObserver().addOnGlobalLayoutListener(this.globalLayoutListener);
        }
    }

    public void onDetachedFromWindow() {
        setIsAttach(false);
        if (isSystemUI()) {
            this.mContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this.globalLayoutListener);
        }
        if (!ClockStyleInfo.isPreviewType(this.mDisplayType)) {
            DataUtils.clearInstallInfoCache();
            DateFormatUtils.clearUserIdCache();
            Task.removeBgTask(this.mAddListenerTask);
            Task.postBgTask(this.mRemoveListenerTask);
        }
        InitViewTask initViewTask = this.mInitTask;
        if (initViewTask != null) {
            initViewTask.cancel(true);
            this.mInitTask = null;
        }
    }

    public final void registerAccessibilityHighTextContrastListener() {
        if (this.mAccessibilityHighTextContrastObserver == null) {
            this.mAccessibilityHighTextContrastObserver = new AnonymousClass8(this.mHandler);
        }
        updateCurrentUserId();
        UserHandle userHandle = null;
        try {
            userHandle = (UserHandle) UserHandle.class.getConstructor(Integer.TYPE).newInstance(UserHandle.class.getField("USER_ALL").get(null));
        } catch (Exception e) {
            Log.e(this.TAG, "constructor user handler fail", e);
        }
        if (!isCrossUser() || userHandle == null) {
            this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("high_text_contrast_enabled"), false, this.mAccessibilityHighTextContrastObserver);
        } else {
            try {
                ContentResolver.class.getMethod("registerContentObserverAsUser", Uri.class, Boolean.TYPE, ContentObserver.class, UserHandle.class).invoke(this.mContext.getContentResolver(), Settings.Secure.getUriFor("high_text_contrast_enabled"), Boolean.FALSE, this.mAccessibilityHighTextContrastObserver, userHandle);
            } catch (Exception e2) {
                Log.e(this.TAG, "register content observer as user fail", e2);
            }
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mAccessibilityHighTextContrastObserver.onChange(false);
            }
        });
    }

    public final void registerBackgroundBlurEnableListener() {
        if (this.mBackgroundBlurEnableObserver == null) {
            this.mBackgroundBlurEnableObserver = new AnonymousClass13(this.mHandler);
        }
        updateCurrentUserId();
        UserHandle userHandle = null;
        try {
            userHandle = (UserHandle) UserHandle.class.getConstructor(Integer.TYPE).newInstance(UserHandle.class.getField("USER_ALL").get(null));
        } catch (Exception e) {
            Log.e(this.TAG, "constructor user handler fail", e);
        }
        if (!isCrossUser() || userHandle == null) {
            this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("background_blur_enable"), false, this.mBackgroundBlurEnableObserver);
        } else {
            try {
                ContentResolver.class.getMethod("registerContentObserverAsUser", Uri.class, Boolean.TYPE, ContentObserver.class, UserHandle.class).invoke(this.mContext.getContentResolver(), Settings.Secure.getUriFor("background_blur_enable"), Boolean.FALSE, this.mBackgroundBlurEnableObserver, userHandle);
            } catch (Exception e2) {
                Log.e(this.TAG, "register content observer as user fail", e2);
            }
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mBackgroundBlurEnableObserver.onChange(false);
            }
        });
    }

    public final void registerClockInfoListener(final boolean z) {
        if (this.mHasRegisterClockInfoListener) {
            Log.i(this.TAG, "registerClockInfoListener has register, return isCrossUser = " + z);
            return;
        }
        this.mHasRegisterClockInfoListener = true;
        Log.i(this.TAG, "registerClockInfoListener isCrossUser = " + z + ", this = " + this);
        if (this.mClockInfoListener == null) {
            this.mClockInfoListener = new AnonymousClass11(this.mHandler);
        }
        if (z) {
            this.mUserSwitchBroadcastReceiver = new AnonymousClass12();
            updateCurrentUserId();
            try {
                this.mUserAllHandle = (UserHandle) UserHandle.class.getConstructor(Integer.TYPE).newInstance(UserHandle.class.getField("USER_ALL").get(null));
            } catch (Exception e) {
                Log.e(this.TAG, "constructor user handler fail", e);
            }
            try {
                Context.class.getMethod("registerReceiverAsUser", BroadcastReceiver.class, UserHandle.class, IntentFilter.class, String.class, Handler.class).invoke(this.mContext, this.mUserSwitchBroadcastReceiver, this.mUserAllHandle, new IntentFilter("android.intent.action.USER_SWITCHED"), null, null);
            } catch (Exception e2) {
                Log.e(this.TAG, "register receiver as user fail", e2);
            }
            try {
                ContentResolver.class.getMethod("registerContentObserverAsUser", Uri.class, Boolean.TYPE, ContentObserver.class, UserHandle.class).invoke(this.mContext.getContentResolver(), Settings.Secure.getUriFor("constant_template_editor_info"), Boolean.FALSE, this.mClockInfoListener, this.mUserAllHandle);
            } catch (Exception e3) {
                Log.e(this.TAG, "register content observer as user fail", e3);
            }
        } else {
            this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("constant_template_editor_info"), false, this.mClockInfoListener);
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                MiuiClockController.m640$r8$lambda$8PjrWcVHYu_YSd1Z9mTysYgQG4(this.f$0, z);
            }
        });
    }

    public final void registerDoodleTextObserver() {
        if (this.mContext == null) {
            return;
        }
        if (isSystemUI() || this.mNeedRegisterClockInfoListener) {
            SettingsObserver.init(this.mContext.getApplicationContext() != null ? this.mContext.getApplicationContext() : this.mContext, "lockscreen_template_doodle_text_support");
            SettingsObserver.addListener("lockscreen_template_doodle_text_support", this.mDoodleTextListener);
            IClockView iClockView = this.mClockView;
            if (iClockView == null || iClockView == null || iClockView.getClockStyleInfo() == null) {
                return;
            }
            this.mClockView.getClockStyleInfo().setShouldObserveSettings();
        }
    }

    public final void registerIs24HourFormatListener() {
        if (this.mIs24HourFormatObserver == null) {
            this.mIs24HourFormatObserver = new ContentObserver(this.mHandler) { // from class: com.miui.clock.MiuiClockController.10
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    if (MiuiClockController.this.mClockView == null || MiuiClockController.this.mUpdateTimeRunnable == null) {
                        return;
                    }
                    MiuiClockController.this.mHandler.post(MiuiClockController.this.mUpdateTimeRunnable);
                }
            };
        }
        UserHandle userHandle = null;
        try {
            userHandle = (UserHandle) UserHandle.class.getConstructor(Integer.TYPE).newInstance(UserHandle.class.getField("USER_ALL").get(null));
        } catch (Exception e) {
            Log.e(this.TAG, "constructor user handler fail", e);
        }
        if (!isCrossUser() || userHandle == null) {
            this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("time_12_24"), false, this.mIs24HourFormatObserver);
            return;
        }
        try {
            ContentResolver.class.getMethod("registerContentObserverAsUser", Uri.class, Boolean.TYPE, ContentObserver.class, UserHandle.class).invoke(this.mContext.getContentResolver(), Settings.System.getUriFor("time_12_24"), Boolean.FALSE, this.mIs24HourFormatObserver, userHandle);
        } catch (Exception e2) {
            Log.e(this.TAG, "register content observer as user fail", e2);
        }
    }

    public final void registerListener() {
        if (ClockStyleInfo.isPreviewType(this.mDisplayType)) {
            return;
        }
        Task.removeBgTask(this.mRemoveListenerTask);
        Task.postBgTask(this.mAddListenerTask);
    }

    public void registerPackageBroadcast() {
        if (this.mHasPackageListener) {
            Log.d(this.TAG, "has register package broadcast");
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        if (this.mPackageChangeReceiver == null) {
            this.mPackageChangeReceiver = new BroadcastReceiver() { // from class: com.miui.clock.MiuiClockController.9
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (intent.getData() != null) {
                        String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                        Log.d(MiuiClockController.this.TAG, "receive package changed " + schemeSpecificPart);
                        if (TextUtils.equals(schemeSpecificPart, "com.mi.health") || TextUtils.equals(schemeSpecificPart, "com.miui.weather2")) {
                            DataUtils.clearInstallInfoCache();
                        }
                    }
                }
            };
        }
        this.mHasPackageListener = true;
        this.mContext.registerReceiver(this.mPackageChangeReceiver, intentFilter);
    }

    public final void registerSuperSaveOpenListener() {
        if (this.mSuperSaveOpenObserver == null) {
            this.mSuperSaveOpenObserver = new AnonymousClass2(this.mHandler);
        }
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("power_supersave_mode_open"), false, this.mSuperSaveOpenObserver);
        this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mSuperSaveOpenObserver.onChange(false);
            }
        });
    }

    public final void registerTimeListener() {
        if (this.mHasRegisterTimeListener) {
            Log.i(this.TAG, "registerTimeListener, has register return");
            return;
        }
        Log.i(this.TAG, "registerTimeListener, this = " + this);
        this.mHasRegisterTimeListener = true;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        if (this.mTimezoneChangeReceiver == null) {
            this.mTimezoneChangeReceiver = new AnonymousClass4();
        }
        this.mContext.registerReceiver(this.mTimezoneChangeReceiver, intentFilter);
        if (this.mAutoUpdateTime) {
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.TIME_TICK");
            intentFilter2.addAction("android.intent.action.TIME_SET");
            Log.d(this.TAG, "---registerReceiver---");
            if (this.mUpdateTimeReceiver == null) {
                this.mUpdateTimeReceiver = new BroadcastReceiver() { // from class: com.miui.clock.MiuiClockController.5
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        MiuiClockController.this.mHandler.post(MiuiClockController.this.mUpdateTimeRunnable);
                    }
                };
            }
            this.mContext.registerReceiver(this.mUpdateTimeReceiver, intentFilter2);
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MiuiClockController.$r8$lambda$l8iLjr0BcbP7kcP6kbSYH7Ycrq8(this.f$0);
            }
        });
        updateCurrentUserId();
        UserHandle userHandle = null;
        try {
            userHandle = (UserHandle) UserHandle.class.getConstructor(Integer.TYPE).newInstance(UserHandle.class.getField("USER_ALL").get(null));
        } catch (Exception e) {
            Log.e(this.TAG, "constructor user handler fail", e);
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (this.mDualClockOpenObserver == null) {
            this.mDualClockOpenObserver = new ContentObserver(this.mHandler) { // from class: com.miui.clock.MiuiClockController.6
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    super.onChange(z);
                    if (MiuiClockController.this.mHasRegisterTimeListener) {
                        MiuiClockController miuiClockController = MiuiClockController.this;
                        miuiClockController.mDualClockOpen = miuiClockController.getAutoDualClock(miuiClockController.isCrossUser()) != 0;
                        MiuiClockController.this.updateDualClock();
                    }
                }
            };
        }
        boolean zIsCrossUser = isCrossUser();
        Class cls = Boolean.TYPE;
        if (!zIsCrossUser || userHandle == null) {
            contentResolver.registerContentObserver(Settings.System.getUriFor("auto_dual_clock"), false, this.mDualClockOpenObserver);
        } else {
            try {
                ContentResolver.class.getMethod("registerContentObserverAsUser", Uri.class, cls, ContentObserver.class, UserHandle.class).invoke(this.mContext.getContentResolver(), Settings.System.getUriFor("auto_dual_clock"), Boolean.FALSE, this.mDualClockOpenObserver, userHandle);
            } catch (Exception e2) {
                Log.e(this.TAG, "register content observer as user fail", e2);
            }
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mDualClockOpenObserver.onChange(false);
            }
        });
        if (this.mResidentTimezoneObserver == null) {
            this.mResidentTimezoneObserver = new ContentObserver(this.mHandler) { // from class: com.miui.clock.MiuiClockController.7
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    Log.d(MiuiClockController.this.TAG, "---registerReceiver mResidentTimezoneObserver---");
                    super.onChange(z);
                    if (MiuiClockController.this.mHasRegisterTimeListener) {
                        DateFormatUtils.clearCache();
                        MiuiClockController miuiClockController = MiuiClockController.this;
                        miuiClockController.mResidentTimezone = miuiClockController.getResidentTimezone(miuiClockController.isCrossUser());
                        MiuiClockController.this.updateDualClock();
                        if (MiuiClockController.this.mClockView != null) {
                            MiuiClockController.this.mClockView.updateResidentTimeZone(MiuiClockController.this.mResidentTimezone);
                        }
                    }
                }
            };
        }
        if (!isCrossUser() || userHandle == null) {
            contentResolver.registerContentObserver(Settings.System.getUriFor("resident_timezone"), false, this.mResidentTimezoneObserver);
        } else {
            try {
                ContentResolver.class.getMethod("registerContentObserverAsUser", Uri.class, cls, ContentObserver.class, UserHandle.class).invoke(this.mContext.getContentResolver(), Settings.System.getUriFor("resident_timezone"), Boolean.FALSE, this.mResidentTimezoneObserver, userHandle);
            } catch (Exception e3) {
                Log.e(this.TAG, "register content observer as user fail", e3);
            }
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mResidentTimezoneObserver.onChange(false);
            }
        });
    }

    public final void registerWallpaperSupportDepthListener() {
        if (this.mWallpaperSupportDepthObserver == null) {
            this.mWallpaperSupportDepthObserver = new AnonymousClass3(this.mHandler);
        }
        updateCurrentUserId();
        UserHandle userHandle = null;
        try {
            userHandle = (UserHandle) UserHandle.class.getConstructor(Integer.TYPE).newInstance(UserHandle.class.getField("USER_ALL").get(null));
        } catch (Exception e) {
            Log.e(this.TAG, "constructor user handler fail", e);
        }
        if (!isCrossUser() || userHandle == null) {
            this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("wallpaper_matting_support_2"), false, this.mWallpaperSupportDepthObserver);
        } else {
            try {
                ContentResolver.class.getMethod("registerContentObserverAsUser", Uri.class, Boolean.TYPE, ContentObserver.class, UserHandle.class).invoke(this.mContext.getContentResolver(), Settings.Secure.getUriFor("wallpaper_matting_support_2"), Boolean.FALSE, this.mWallpaperSupportDepthObserver, userHandle);
            } catch (Exception e2) {
                Log.e(this.TAG, "register content observer as user fail", e2);
            }
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.clock.MiuiClockController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mWallpaperSupportDepthObserver.onChange(false);
            }
        });
    }

    public final void removeAccessibilityHighTextContrastListener() {
        if (this.mAccessibilityHighTextContrastObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mAccessibilityHighTextContrastObserver);
        }
    }

    public final void removeBackgroundBlurEnableListener() {
        if (this.mBackgroundBlurEnableObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mBackgroundBlurEnableObserver);
        }
    }

    public final void removeClockInfoListener() {
        if (!this.mHasRegisterClockInfoListener) {
            Log.i(this.TAG, "removeClockInfoListener un register, return, this = " + this);
            return;
        }
        Log.i(this.TAG, "removeClockInfoListener, this = " + this);
        this.mHasRegisterClockInfoListener = false;
        if (this.mClockInfoListener != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mClockInfoListener);
        }
        BroadcastReceiver broadcastReceiver = this.mUserSwitchBroadcastReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }
    }

    public final void removeDoodleTextObserver() {
        SettingsObserver.removeListener("lockscreen_template_doodle_text_support", this.mDoodleTextListener);
    }

    public final void removeIs24HourFormatListener() {
        if (this.mIs24HourFormatObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mIs24HourFormatObserver);
        }
    }

    public void removePackageBroadcast() {
        if (this.mHasPackageListener) {
            this.mHasPackageListener = false;
            Log.d(this.TAG, "unRegister package broadcast");
            this.mContext.unregisterReceiver(this.mPackageChangeReceiver);
        }
    }

    public final void removeSuperSaveOpenListener() {
        if (this.mSuperSaveOpenObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mSuperSaveOpenObserver);
        }
    }

    public final void removeTimeListener() {
        BroadcastReceiver broadcastReceiver;
        if (!this.mHasRegisterTimeListener) {
            Log.i(this.TAG, "removeTimeListener, un register return");
            return;
        }
        Log.i(this.TAG, "removeTimeListener, this = " + this);
        this.mHasRegisterTimeListener = false;
        BroadcastReceiver broadcastReceiver2 = this.mTimezoneChangeReceiver;
        if (broadcastReceiver2 != null) {
            this.mContext.unregisterReceiver(broadcastReceiver2);
        }
        if (this.mAutoUpdateTime && (broadcastReceiver = this.mUpdateTimeReceiver) != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        ContentObserver contentObserver = this.mDualClockOpenObserver;
        if (contentObserver != null) {
            contentResolver.unregisterContentObserver(contentObserver);
        }
        ContentObserver contentObserver2 = this.mResidentTimezoneObserver;
        if (contentObserver2 != null) {
            contentResolver.unregisterContentObserver(contentObserver2);
        }
    }

    public final void removeWallpaperSupportDepthListener() {
        if (this.mWallpaperSupportDepthObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mWallpaperSupportDepthObserver);
        }
    }

    public void resetColorOnSuperSaveChange(boolean z) {
        IClockView iClockView;
        if (getClockStyleInfo() == null || (iClockView = this.mClockView) == null) {
            return;
        }
        this.isSuperSaveOpen = z;
        iClockView.setSuperSaveOpen(z);
        int clockEffect = getClockStyleInfo().getClockEffect();
        if (ClockEffectUtils.isSolidType(clockEffect)) {
            updateColor();
            return;
        }
        if (ClockEffectUtils.isGlassType(clockEffect) || ClockEffectUtils.isBlurMixType(clockEffect) || ClockEffectUtils.isDifferenceType(clockEffect) || ClockEffectUtils.isGradualType(clockEffect)) {
            if (!z) {
                updateClockBlendColor(getClockStyleInfo().getBlendColor(), getClockStyleInfo().getSecondaryBlendColor());
            } else {
                clearClockBlur();
                updateColor();
            }
        }
    }

    public void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        IClockView iClockView = getIClockView();
        if (iClockView != null) {
            iClockView.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
        } else {
            Log.d(this.TAG, "setAllInOneClockRectParams clockView is null ");
        }
    }

    public void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        IClockView iClockView = getIClockView();
        if (iClockView != null) {
            iClockView.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
        } else {
            Log.d(this.TAG, "setAllInOneClockRectParams clockView is null ");
        }
    }

    public void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        IClockView iClockView = getIClockView();
        if (iClockView != null) {
            iClockView.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
        } else {
            Log.d(this.TAG, "setAllInOneClockRectParams clockView is null ");
        }
    }

    public void setAutoDualClock(boolean z) {
        this.mAutoDualClock = z;
        updateDualClock();
    }

    public void setAutoUpdateTime(boolean z) {
        this.mAutoUpdateTime = z;
    }

    public void setClockAlpha(float f) {
        this.mClockView.setClockAlpha(f);
    }

    public void setClockBean(ClockBean clockBean) {
        setClockBean(clockBean, false, false);
    }

    public final void setClockBean(ClockBean clockBean, boolean z, boolean z2) {
        if (clockBean == null) {
            Log.i(this.TAG, "bean is null");
            return;
        }
        if (z && clockBean.equals(this.mClockBean) && !z2) {
            Log.i(this.TAG, "bean is same with now value");
            return;
        }
        if (z2) {
            addClockView(clockBean, true);
            return;
        }
        Log.i(this.TAG, "setClockBean = " + clockBean + ", mClockBean = " + this.mClockBean);
        if (!TextUtils.equals(clockBean.getTemplateId(), this.mClockBean.getTemplateId())) {
            addClockView(clockBean, false);
            return;
        }
        this.mClockBean = clockBean;
        if (this.mShowDualClock && !unSupportDualClockTemplate()) {
            String dualClockLocalCity = clockBean.getDualClockLocalCity();
            ClockBean clockBean2 = new ClockBean("dual");
            clockBean2.setDualClockLocalCity(dualClockLocalCity);
            clockBean = clockBean2;
        }
        if (!(this.mClockView instanceof MiuiDualClock) || "dual".equals(clockBean.getTemplateId())) {
            Log.d(this.TAG, "clock change bean, reset clock");
            this.mClockView.resetClock();
        } else {
            addClockView(this.mClockBean, false);
        }
        ClockStyleInfo clockStyleInfoConvertInfoFromClockBean = ClockStyleInfo.convertInfoFromClockBean(this.mContext, clockBean, this.mDisplayType);
        if (clockStyleInfoConvertInfoFromClockBean != null) {
            clockStyleInfoConvertInfoFromClockBean.setSuperSaveOpen(this.isSuperSaveOpen);
            clockStyleInfoConvertInfoFromClockBean.setShouldObserveSettings();
        }
        this.mClockView.setClockStyleInfo(clockStyleInfoConvertInfoFromClockBean);
        this.mClockView.setClockPalette(this.mChoosePaletteType, this.mTextDark, this.mPalette, isWallpaperSupportDepth());
        this.mClockView.setInfoTextColorDark(this.mInfoTextDark);
        MiuiClockInfoListener miuiClockInfoListener = this.mMiuiClockInfoListener;
        if (miuiClockInfoListener != null) {
            miuiClockInfoListener.onClockBeanChange(this.mClockBean, false);
        }
    }

    public void setClockPalette(int i, boolean z, Map map) {
        this.mChoosePaletteType = i;
        this.mTextDark = z;
        this.mPalette = map;
        if (this.mClockView != null) {
            boolean zIsWallpaperSupportDepth = isWallpaperSupportDepth();
            updateSuperSaveOpen();
            this.mClockView.setClockPalette(i, z, map, zIsWallpaperSupportDepth);
        }
        ClockPaletteChangeListener clockPaletteChangeListener = this.mClockPaletteChangeListener;
        if (clockPaletteChangeListener != null) {
            ClockBean clockBean = this.mClockBean;
            if (clockBean != null) {
                clockPaletteChangeListener.onPaletteChange(clockBean.isAutoPrimaryColor(), this.mClockBean.isAutoSecondaryColor());
            } else {
                clockPaletteChangeListener.onPaletteChange(false, false);
            }
        }
    }

    public void setClockPaletteChangeListener(ClockPaletteChangeListener clockPaletteChangeListener) {
        this.mClockPaletteChangeListener = clockPaletteChangeListener;
    }

    public void setClockStyle(int i) {
        if (this.mClockStyle != i) {
            this.mClockStyle = i;
            updateKeyguardClock();
        }
    }

    public final void setCurrentUserId(int i) {
        Log.i(this.TAG, "set current user id = " + i);
        this.mCurrentUserId = i;
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setCurrentUserId(i);
        }
    }

    public void setEditMode(boolean z) {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setEditMode(z);
        }
    }

    public void setEditorColonShow(boolean z) {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setEditorColonShow(z);
        }
    }

    public void setEditorColonViewVisible(boolean z) {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setEditorColonViewVisible(z);
        }
    }

    public void setGradientIndex(int i) {
        if (this.mClockView != null) {
            Log.d(this.TAG, "setGradientIndex" + i);
            this.mClockView.setGradientIndex(i);
        }
    }

    public void setHasTopMargin(boolean z) {
        if (this.mHasTopMargin != z) {
            this.mHasTopMargin = z;
            IClockView iClockView = this.mClockView;
            if (iClockView != null) {
                iClockView.updateViewTopMargin(z);
            }
        }
    }

    public void setInfoTextColorDark(boolean z) {
        this.mInfoTextDark = z;
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setInfoTextColorDark(z);
        }
        ClockPaletteChangeListener clockPaletteChangeListener = this.mClockPaletteChangeListener;
        if (clockPaletteChangeListener != null) {
            clockPaletteChangeListener.onInfoColorChange(z);
        }
    }

    public void setIsAttach(boolean z) {
        this.isAttach = z;
    }

    public void setLoadListener(LoadListener loadListener) {
        this.mLoadListener = loadListener;
    }

    public void setLocalCity(String str) {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setLocalCity(str);
        }
    }

    public void setMagazineInfoVisible(boolean z) {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setMagazineInfoVisible(z);
        }
    }

    public void setMinuteColonAlpha(float f) {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setMinuteColonAlpha(f);
        }
    }

    public void setMinuteColor(int i, int i2) {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setMinuteColor(i, i2);
        }
    }

    public void setMiuiClockInfoListener(MiuiClockInfoListener miuiClockInfoListener) {
        this.mMiuiClockInfoListener = miuiClockInfoListener;
    }

    public void setOutBackgroundBlurContainer(View view) {
        this.outBackgroundBlurContainer = view;
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setBackgroundBlurContainer(view);
        }
    }

    public void setOwnerInfo(String str) {
        if (TextUtils.equals(this.mOwnerString, str)) {
            return;
        }
        this.mOwnerString = str;
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setOwnerInfo(str);
        }
    }

    public void setScaleRatio(float f) {
        if (this.mScaleRatio != f) {
            this.mScaleRatio = f;
            IClockView iClockView = this.mClockView;
            if (iClockView != null) {
                iClockView.setScaleRatio(f);
            }
        }
    }

    public void setShowLunarCalendar(int i) {
        if (this.mShowLunarCalendar != i) {
            this.mShowLunarCalendar = i;
            IClockView iClockView = this.mClockView;
            if (iClockView != null) {
                iClockView.setShowLunarCalendar(getShowLunarCalendar());
            }
        }
    }

    public void setTextColorDark(boolean z) {
        if (this.mTextDark != z) {
            this.mTextDark = z;
            IClockView iClockView = this.mClockView;
            if (iClockView != null) {
                iClockView.setTextColorDark(z);
            }
        }
    }

    public void setTimeUpdateListener(TimeUpdateListener timeUpdateListener) {
        this.mUpdateTimeListener = timeUpdateListener;
    }

    public void setWallpaperSupportDepth(boolean z) {
        this.mSetWallpaperSupportDepth = z ? 1 : 2;
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.setWallpaperSupportDepth(z);
        }
    }

    public final boolean unSupportDualClockTemplate() {
        if (ClockStyleInfo.isSecondaryType(this.mDisplayType)) {
            return true;
        }
        ClockBean clockBean = this.mClockBean;
        if (clockBean != null) {
            return "magazine_c_date_only".equals(clockBean.getTemplateId());
        }
        return false;
    }

    public final void updateClockBeanFromJson(boolean z, boolean z2, String str) {
        ClockBean clockBean;
        initData();
        if (str == null || str.isEmpty()) {
            str = getClockInfoJson(z);
        }
        if (z && isClockInfoEquals(str, this.mCurrentJson)) {
            if (z2) {
                updateResidentTimeZone(this.mResidentTimezone);
            }
            Log.i(this.TAG, "last json clockInfo equals current json clockInfo");
            return;
        }
        if (z) {
            this.mCurrentJson = str;
        }
        ClockBean clockBeanFromSetting = getClockBeanFromSetting(str);
        if (!z2 && isSystemUI() && (clockBean = this.mClockBean) != null) {
            String templateId = clockBean.getTemplateId();
            String templateId2 = clockBeanFromSetting.getTemplateId();
            if ((TextUtils.equals(templateId, "classic_plus_small") && TextUtils.equals(templateId2, "classic_plus")) || ((TextUtils.equals(templateId, "rhombus_notification") && TextUtils.equals(templateId2, "rhombus")) || ((TextUtils.equals(templateId, "eastern_c_notification") && TextUtils.equals(templateId2, "eastern_c")) || ((TextUtils.equals(templateId, "eastern_b_vertical_notification") && TextUtils.equals(templateId2, "eastern_b")) || (TextUtils.equals(templateId, "eastern_a_notification") && TextUtils.equals(templateId2, "eastern_a")))))) {
                ClockBean clockBean2 = (ClockBean) clockBeanFromSetting.clone();
                clockBean2.setTemplateId(templateId);
                if (clockBean2.equals(this.mClockBean)) {
                    Log.d(this.TAG, "updateClockBean: is same bean not update, currTemplateId " + templateId + " updateTemplateId " + templateId2);
                    return;
                }
            }
        }
        setClockBean(clockBeanFromSetting, true, z2);
        MiuiClockInfoListener miuiClockInfoListener = this.mMiuiClockInfoListener;
        if (miuiClockInfoListener != null) {
            miuiClockInfoListener.onMiuiClockInfoChange(clockBeanFromSetting, z2);
        }
    }

    public void updateClockBlendColor(int i, int i2) {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.updateClockBlendColor(i, i2);
        }
    }

    public void updateColor() {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.updateColor();
        }
    }

    public final void updateCurrentUserId() {
        try {
            setCurrentUserId(DateFormatUtils.updateCurrentUserId());
        } catch (Exception e) {
            Log.e(this.TAG, "get current user id fail", e);
        }
    }

    public final void updateDualClock() {
        String str;
        Log.d(this.TAG, "updateDualClock mAutoDualClock = " + this.mAutoDualClock + ", mDualClockOpen = " + this.mDualClockOpen + ", mResidentTimezone = " + this.mResidentTimezone + ", unSupportDualClockTemplate = " + unSupportDualClockTemplate() + ", mCurrentTimezone = " + this.mCurrentTimezone);
        this.mShowDualClock = this.mAutoDualClock && this.mDualClockOpen && (str = this.mResidentTimezone) != null && !str.equals(this.mCurrentTimezone);
        updateKeyguardClock();
        MiuiClockInfoListener miuiClockInfoListener = this.mMiuiClockInfoListener;
        if (miuiClockInfoListener != null) {
            miuiClockInfoListener.onDualClockChange(this.mShowDualClock);
        }
    }

    public void updateKeyguardClock() {
        Log.d(this.TAG, "updateKeyguardClock :" + getClockPosition() + "--> " + this.mLastClockPosition);
        if (getClockPosition() != this.mLastClockPosition) {
            this.mLastClockPosition = getClockPosition();
            Log.d(this.TAG, "new  mClockBean:" + this.mClockBean);
            ClockBean clockBean = this.mClockBean;
            if (clockBean != null) {
                addClockView(clockBean, false);
            }
        }
    }

    public void updateResidentTimeZone(String str) {
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.updateResidentTimeZone(str);
            this.mResidentTimezone = str;
        }
    }

    public final void updateSuperSaveOpen() {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        boolean z = Settings.System.getInt(context.getContentResolver(), "power_supersave_mode_open", 0) != 0;
        if (z != this.isSuperSaveOpen) {
            Log.i(this.TAG, "updateSuperSaveOpen to change " + z);
            this.isSuperSaveOpen = z;
            IClockView iClockView = this.mClockView;
            if (iClockView != null) {
                iClockView.setSuperSaveOpen(z);
            }
            MiuiClockInfoListener miuiClockInfoListener = this.mMiuiClockInfoListener;
            if (miuiClockInfoListener != null) {
                miuiClockInfoListener.onSuperSaveChange(z);
            }
        }
    }

    public void updateTime() {
        Log.d(this.TAG, "updateTime " + this.mClockView);
        IClockView iClockView = this.mClockView;
        if (iClockView != null) {
            iClockView.updateTime();
            TimeUpdateListener timeUpdateListener = this.mUpdateTimeListener;
            if (timeUpdateListener != null) {
                timeUpdateListener.update();
            }
        }
    }
}
