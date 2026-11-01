package com.miui.aod;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Trace;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.miui.aod.analy.AnalyticManager;
import com.miui.aod.category.FlipLinkageClockCategoryInfo;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.doze.DozeLog;
import com.miui.aod.doze.DozeMachine;
import com.miui.aod.doze.SettingsHolder;
import com.miui.aod.flip.AODTinyMiuiClockView;
import com.miui.aod.flip.AnimationExtKt;
import com.miui.aod.flip.FlipLinkageStyleClockView;
import com.miui.aod.flip.FlipLinkageStyleController;
import com.miui.aod.linkagestyle.LinkageStyleClockView;
import com.miui.aod.linkagestyle.LinkageStyleController;
import com.miui.aod.monitor.AodViewMonitor;
import com.miui.aod.theme.ThemeDataManager;
import com.miui.aod.util.AnimTracer;
import com.miui.aod.util.DisplayUtils;
import com.miui.aod.util.DoubleTapHelper;
import com.miui.aod.util.LooperExecutor;
import com.miui.aod.util.ScreenResolutionUtils;
import com.miui.aod.util.WallPaperUtils;
import com.miui.aod.util.WrappedAnimatorListenerAdapter;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.CommonUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.clock.MiuiClockView;
import com.miui.clock.module.ClockBean;
import com.miui.clock.tiny.TinyMiuiClockController;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.xiaomi.onetrack.api.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import miuix.animation.Folme;
import miuix.core.util.EnvStateManager;
import miuix.os.DeviceHelper;

/* JADX INFO: loaded from: classes.dex */
public class DozeHost {
    public boolean isFlipping;
    public WindowManager.LayoutParams lp;
    public AODView mAODView;
    public String mAodStateOnReason;
    public View mBlackBg;
    public final ArrayList mCallbacks;
    public float mClockTranslation;
    public final Configuration mConfiguration;
    public View mContainer;
    public final Context mContext;
    public DoubleTapHelper mDoubleTapHelper;
    public boolean mDozing;
    public boolean mDozingRequested;
    public PowerManager.WakeLock mDrawWakeLock;
    public Runnable mEnterAnimDelayRunnable;
    public final Runnable mFingerUpRunnable;
    public boolean mFingerprintPressed;
    public boolean mFodTurnedOnScreen;
    public final Handler mHandler;
    public volatile boolean mIsAodVisible;
    public volatile boolean mKeyguardOccluded;
    public int mLinkageMagazineHeight;
    public Runnable mMonitorSampleRunnable;
    public boolean mNeedLinkageWallpaperAnim;
    public boolean mNeedShowWallpaper;
    public boolean mPendingContainerVisible;
    public PowerManager mPowerManager;
    public boolean mPreDisplayedAod;
    public final Runnable mPrintMemInfoTask;
    public ViewPropertyAnimator mSetAodVisibilityAnimator;
    public boolean mShowAodAnimate;
    public final Runnable mStopAodRunnable;
    public final boolean mSupportGestureWakeup;
    public volatile boolean mSuppressAod;
    public final WakeLock mWakeLock;
    public final LooperExecutor mainExecutor;
    public final Runnable removeAODViewRunnable;
    public DozeMachine.Service service;
    public boolean startMaskAnimationIfNeeded;
    public boolean mWindowAdded = false;
    public boolean mSimPinSecure = false;
    public boolean mGxzwIconTransparent = true;
    public boolean mNeedTurnOnDelay = false;
    public boolean mFingerprintUnlockAvailable = false;
    public boolean isAnimationDisappearing = false;
    public Map mAodPosition = null;

    /* JADX INFO: renamed from: com.miui.aod.DozeHost$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public final /* synthetic */ Callback val$callback;
        public final /* synthetic */ boolean val$firstEnter;
        public final /* synthetic */ boolean val$visible;

        public static /* synthetic */ void $r8$lambda$40LVPUQl19oVk5Gh5XPgk24Ap4w(AnonymousClass1 anonymousClass1, ValueAnimator valueAnimator) {
            if (!DozeHost.this.mNeedShowWallpaper || DozeHost.this.mBlackBg == null) {
                return;
            }
            DozeHost.this.mBlackBg.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }

        public AnonymousClass1(boolean z, Callback callback, boolean z2) {
            this.val$visible = z;
            this.val$callback = callback;
            this.val$firstEnter = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            Callback callback;
            if (DozeHost.this.mContainer == null || DozeHost.this.isFullAod()) {
                Log.i("DozeHost", "Hide AOD mContainer == null");
                if (!DozeHost.this.isFullAod() || this.val$visible || (callback = this.val$callback) == null) {
                    return;
                }
                callback.onDozingRequested(true);
                return;
            }
            if (!this.val$visible) {
                WrappedAnimatorListenerAdapter wrappedAnimatorListenerAdapter = new WrappedAnimatorListenerAdapter() { // from class: com.miui.aod.DozeHost.1.1
                    public boolean isCancel = false;
                    public final AnimTracer.Manual tracer = AnimTracer.manual("KeyguardAnim-hideAod");

                    @Override // com.miui.aod.util.WrappedAnimatorListenerAdapter, android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        super.onAnimationCancel(animator);
                        this.isCancel = true;
                    }

                    @Override // com.miui.aod.util.WrappedAnimatorListenerAdapter, android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (!this.isCancel) {
                            DozeHost.this.mIsAodVisible = false;
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            Callback callback2 = anonymousClass1.val$callback;
                            if (callback2 != null) {
                                DozeHost.this.isAnimationDisappearing = true;
                                callback2.onDozingRequested(true);
                            }
                            if (DozeHost.this.mContainer != null) {
                                DozeHost.this.mContainer.setVisibility(4);
                            }
                            if (DozeHost.this.mNeedShowWallpaper && DozeHost.this.mBlackBg != null) {
                                DozeHost.this.mBlackBg.setVisibility(0);
                                DozeHost.this.mBlackBg.setAlpha(1.0f);
                            }
                        }
                        DozeHost.this.releaseDrawWakelock();
                        DozeHost.this.mWakeLock.release("#visible");
                        this.tracer.end();
                    }

                    @Override // com.miui.aod.util.WrappedAnimatorListenerAdapter, android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        super.onAnimationStart(animator);
                        this.tracer.begin();
                        DozeHost.this.requestDrawWakelock(2000L, "DozeHost#setAodVisibility(2");
                        DozeHost.this.mWakeLock.acquire("#visible");
                        if (!DozeHost.this.mNeedShowWallpaper || DozeHost.this.mBlackBg == null) {
                            return;
                        }
                        DozeHost.this.mBlackBg.setVisibility(0);
                        DozeHost.this.mBlackBg.setAlpha(0.0f);
                    }
                };
                DozeHost.this.mContainer.setAlpha(1.0f);
                DozeHost dozeHost = DozeHost.this;
                dozeHost.mSetAodVisibilityAnimator = dozeHost.mContainer.animate().alpha(0.0f).setDuration(1800L).setListener(wrappedAnimatorListenerAdapter).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.aod.DozeHost$1$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        DozeHost.AnonymousClass1.$r8$lambda$40LVPUQl19oVk5Gh5XPgk24Ap4w(this.f$0, valueAnimator);
                    }
                });
                DozeHost.this.mSetAodVisibilityAnimator.start();
                return;
            }
            if (DozeHost.this.mAODView != null) {
                if ((DozeHost.this.mContainer.getVisibility() == 0 && DozeHost.this.mContainer.getAlpha() == 1.0f) || DozeHost.this.mPendingContainerVisible) {
                    return;
                }
                DozeHost.this.cancelSetVisibilityAnimation();
                DozeHost.this.mContainer.setVisibility(0);
                DozeHost.this.mAODView.updateClockViewVisible();
                DozeHost.this.startEnterAnim(new WrappedAnimatorListenerAdapter() { // from class: com.miui.aod.DozeHost.1.2
                    @Override // com.miui.aod.util.WrappedAnimatorListenerAdapter, android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        DozeHost.this.mWakeLock.release("#invisible");
                    }

                    @Override // com.miui.aod.util.WrappedAnimatorListenerAdapter, android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        super.onAnimationStart(animator);
                        DozeHost dozeHost2 = DozeHost.this;
                        dozeHost2.isAnimationDisappearing = false;
                        if (dozeHost2.mNeedShowWallpaper && DozeHost.this.mBlackBg != null) {
                            final View view = DozeHost.this.mBlackBg;
                            view.setVisibility(0);
                            view.setAlpha(1.0f);
                            view.animate().alpha(0.0f).setDuration(1800L).setInterpolator(new DecelerateInterpolator()).setListener(new WrappedAnimatorListenerAdapter() { // from class: com.miui.aod.DozeHost.1.2.1
                                @Override // com.miui.aod.util.WrappedAnimatorListenerAdapter, android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator animator2) {
                                    super.onAnimationEnd(animator2);
                                    view.setVisibility(8);
                                }
                            }).start();
                        }
                        DozeHost.this.mWakeLock.acquire("#invisible");
                    }
                }, this.val$firstEnter);
            }
        }
    }

    public interface Callback extends PowerSaveChangeCallback {
        default void fireAodState(boolean z, String str) {
        }

        default void notifyKeycodeGoto() {
        }

        default void onAodAnimate(boolean z) {
        }

        default void onDozingRequested(boolean z) {
        }

        default void onFingerprintPressed(boolean z, boolean z2) {
        }
    }

    public interface PowerSaveChangeCallback {
        default void onPowerSaveChanged(boolean z) {
        }
    }

    public static /* synthetic */ void $r8$lambda$1pg4j8w4ZUlPLt4fno03TN4OxOI() {
        long nativeHeapAllocatedSize;
        try {
            nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize();
        } catch (Exception e) {
            Log.e("DozeHost", "error...", e);
            nativeHeapAllocatedSize = 0;
        }
        Log.e("DozeHost", "NativeHeapAllocatedSize: " + nativeHeapAllocatedSize);
    }

    public static /* synthetic */ void $r8$lambda$5W_x7GOG87Sx4n79ikcOTKgY42s(List list, boolean z, String str) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Callback callback = (Callback) it.next();
            if (callback != null) {
                callback.fireAodState(z, str);
            } else {
                Log.w("DozeHost", "fireAodState: callback == null");
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$FURgNVtqV6ZosGhdnzpTiQlUQZY(DozeHost dozeHost, boolean z) {
        View view = dozeHost.mContainer;
        if (view == null) {
            return;
        }
        view.setAlpha(1.0f);
        int i = 4;
        if (!dozeHost.mIsAodVisible) {
            dozeHost.mContainer.setVisibility(4);
        } else if (!dozeHost.mPendingContainerVisible) {
            dozeHost.mContainer.setVisibility(0);
        }
        View view2 = dozeHost.mBlackBg;
        if (view2 != null) {
            if (dozeHost.mNeedShowWallpaper && !z) {
                i = 0;
            }
            view2.setVisibility(i);
        }
    }

    public static /* synthetic */ void $r8$lambda$R2q3pXW6ijlPyMtu6L63AXKrkM0(DozeHost dozeHost) {
        AODView aODView = dozeHost.mAODView;
        if (aODView != null) {
            aODView.invalidate();
        }
    }

    public static /* synthetic */ void $r8$lambda$RK5VK4XHijxPfsnIHn9sYSJIHH4(DozeHost dozeHost, AnimatorListenerAdapter animatorListenerAdapter, StyleInfo styleInfo) {
        AODView aODView;
        if (dozeHost.mContainer == null || (aODView = dozeHost.mAODView) == null) {
            return;
        }
        aODView.startClockPanelAnimation(animatorListenerAdapter);
        dozeHost.mHandler.postDelayed(new Runnable() { // from class: com.miui.aod.DozeHost.6
            @Override // java.lang.Runnable
            public void run() {
                if (DozeHost.this.mFingerprintPressed && Utils.isGxzwSensor()) {
                    return;
                }
                DozeHost.this.mContainer.setAlpha(1.0f);
            }
        }, styleInfo.getInnerStartDelay());
    }

    public static /* synthetic */ void $r8$lambda$S2RE2d_J7xaX1RvhyL1X_gsRhqE(DozeHost dozeHost) {
        AODView aODView = dozeHost.mAODView;
        if (aODView != null) {
            aODView.setBackgroundColor(dozeHost.mKeyguardOccluded ? -16777216 : 0);
        }
    }

    public static /* synthetic */ void $r8$lambda$TgvE_iVEvo7DljMh58qimTnHJvw(DozeHost dozeHost, StyleInfo styleInfo) {
        AODView aODView;
        if (dozeHost.mFingerprintPressed || dozeHost.isFullAod() || !dozeHost.isAodVisible() || (aODView = dozeHost.mAODView) == null || !aODView.isAttachedToWindow()) {
            return;
        }
        AodViewMonitor.sampleOnAodEnter(dozeHost.mAODView, styleInfo);
    }

    /* JADX INFO: renamed from: $r8$lambda$WCbo8F-mmUAk7tzMu1rixfbSc4U, reason: not valid java name */
    public static /* synthetic */ boolean m188$r8$lambda$WCbo8FmmUAk7tzMu1rixfbSc4U(DozeHost dozeHost) {
        dozeHost.getClass();
        Log.d("DozeHost", "onDoubleTap");
        CommonUtils.requestWakeUp(dozeHost.mContext);
        return true;
    }

    public static /* synthetic */ boolean $r8$lambda$Xr1QEGs8MtvpTwMILueS3xblWCA(DozeHost dozeHost, View view, MotionEvent motionEvent) {
        dozeHost.getClass();
        Log.d("DozeHost", "onTouch:" + motionEvent.getAction());
        if (dozeHost.mSupportGestureWakeup && SettingsHolder.mOpenDoubleTapGoToSleep && AODSettings.needKeepScreenOnAtFirst()) {
            dozeHost.mDoubleTapHelper.onTouchEvent(motionEvent);
        }
        if (motionEvent.getAction() != 0) {
            return true;
        }
        dozeHost.notifyKeycodeGoto();
        return true;
    }

    /* JADX INFO: renamed from: $r8$lambda$ZTwEF5fApWAF47F2d_bUhWAY7-s, reason: not valid java name */
    public static /* synthetic */ void m189$r8$lambda$ZTwEF5fApWAF47F2d_bUhWAY7s(boolean z) {
    }

    /* JADX INFO: renamed from: $r8$lambda$dn5GnbmHWUOqS1JC6N2uG3-vDSw, reason: not valid java name */
    public static /* synthetic */ void m190$r8$lambda$dn5GnbmHWUOqS1JC6N2uG3vDSw(DozeHost dozeHost) {
        DozeMachine.Service service = dozeHost.service;
        DozeMachine dozeMachine = service != null ? service.getDozeMachine() : null;
        if (dozeMachine != null) {
            dozeMachine.requestState(DozeMachine.State.DOZE);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$f-WR6fwqKu-jkYya9XiTabRQdnA, reason: not valid java name */
    public static /* synthetic */ void m191$r8$lambda$fWR6fwqKujkYya9XiTabRQdnA(DozeHost dozeHost, boolean z) {
        AODView aODView;
        if (dozeHost.mDozing && (aODView = dozeHost.mAODView) != null && aODView.isAttachedToWindow()) {
            dozeHost.mAODView.onGxzwIconChanged(z);
        }
    }

    public static /* synthetic */ void $r8$lambda$oCvCBwvHKiJSiyNI8YkzIOxw_4Q(DozeHost dozeHost) {
        AODView aODView = dozeHost.mAODView;
        if (aODView != null) {
            aODView.startMaskAnimation();
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$yCezaC1QHA3L-92j9eRx31dSZTU, reason: not valid java name */
    public static /* synthetic */ void m193$r8$lambda$yCezaC1QHA3L92j9eRx31dSZTU(DozeMachine dozeMachine) {
        if (dozeMachine == null || dozeMachine.getState() == DozeMachine.State.DOZE_PULSING) {
            return;
        }
        dozeMachine.requestAod(9);
    }

    public DozeHost(Context context) {
        Configuration configuration = new Configuration();
        this.mConfiguration = configuration;
        this.mNeedLinkageWallpaperAnim = false;
        this.isFlipping = false;
        this.mPreDisplayedAod = false;
        this.mFingerUpRunnable = new Runnable() { // from class: com.miui.aod.DozeHost.2
            @Override // java.lang.Runnable
            public void run() {
                if (DozeHost.this.mContainer == null || DozeHost.this.mContainer.getVisibility() != 0) {
                    return;
                }
                DozeHost.this.mContainer.setAlpha(1.0f);
            }
        };
        this.startMaskAnimationIfNeeded = false;
        this.removeAODViewRunnable = new Runnable() { // from class: com.miui.aod.DozeHost.7
            @Override // java.lang.Runnable
            public void run() {
                DozeHost.this.removeAODView();
                DozeHost.this.cancelSetVisibilityAnimation();
            }
        };
        this.mStopAodRunnable = new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onStopDoze();
            }
        };
        this.mCallbacks = new ArrayList();
        this.mPrintMemInfoTask = new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                DozeHost.$r8$lambda$1pg4j8w4ZUlPLt4fno03TN4OxOI();
            }
        };
        LooperExecutor looperExecutor = new LooperExecutor(Looper.getMainLooper());
        this.mainExecutor = looperExecutor;
        this.mHandler = looperExecutor.getHandler();
        this.mContext = context;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mPowerManager = powerManager;
        this.mWakeLock = WakeLock.wrap(WakeLock.createPartialInner(powerManager, "DozeHost"));
        this.mSupportGestureWakeup = Utils.isSupportGestureWakeup();
        configuration.updateFrom(context.getResources().getConfiguration());
    }

    public static boolean preDisplayedAod(DozeHost dozeHost) {
        return dozeHost != null && dozeHost.preDisplayedAod();
    }

    public void addCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    public void applyLinkageState() {
        LinkageStyleClockView linkageClockView = getLinkageClockView();
        if (linkageClockView == null || !linkageClockView.isAllInOneTemplateFromSettings(this.mContext)) {
            return;
        }
        Log.d("DozeHost", "applyLinkageState: mLinkageMagazineHeight = " + this.mLinkageMagazineHeight);
        linkageClockView.applyLinkageState(this.mLinkageMagazineHeight);
    }

    public void cancelAndRunPendingRemoveAodView() {
        if (this.mHandler.hasCallbacks(this.removeAODViewRunnable)) {
            this.mHandler.removeCallbacks(this.removeAODViewRunnable);
            this.removeAODViewRunnable.run();
        }
    }

    public final void cancelEnterAnimation() {
        Runnable runnable = this.mEnterAnimDelayRunnable;
        if (runnable != null) {
            this.mHandler.removeCallbacks(runnable);
            this.mEnterAnimDelayRunnable = null;
        }
        View view = this.mContainer;
        if (view != null) {
            view.animate().cancel();
            this.mContainer.clearAnimation();
            Folme.useAt(this.mContainer).cancel();
        }
        AODView aODView = this.mAODView;
        if (aODView != null) {
            aODView.clearClockPanelAnimation(false);
        }
    }

    public void cancelSetVisibilityAnimation() {
        ViewPropertyAnimator viewPropertyAnimator = this.mSetAodVisibilityAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            this.mSetAodVisibilityAnimator = null;
        }
    }

    public void checkFullAodVisibility(boolean z) {
        AODView aODView;
        if (!isFullAod() || (aODView = this.mAODView) == null) {
            return;
        }
        aODView.setAlpha(z ? 0.0f : 1.0f);
    }

    public void create() {
        ScreenResolutionUtils.cleanRealSize();
        dealWithFlipChange(this.mContext.getResources().getConfiguration(), true);
    }

    public void dealWithChange() {
        requestDrawWakelock(2000L, "DozeHost");
        setAodVisibility(false);
        DozeMachine.Service service = this.service;
        if (service == null || service.getDozeMachine() == null) {
            updateClockTypeForChange();
            setAodVisibility(true);
        } else {
            final DozeMachine dozeMachine = this.service.getDozeMachine();
            dozeMachine.preDestroyForChange();
            updateClockTypeForChange();
            dozeMachine.requestState(DozeMachine.State.DOZE_AOD_PAUSED);
            if (!Utils.aodGone(this.mContext)) {
                this.mHandler.postDelayed(this.mWakeLock.wrap(new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        dozeMachine.requestAod(10);
                    }
                }), 600L);
            }
            if (dozeMachine.getState() != DozeMachine.State.FINISH) {
                dozeMachine.rebindForChange();
            }
        }
        AODView aODView = this.mAODView;
        if (aODView != null) {
            aODView.handleUpdateIcons(true);
        }
    }

    public final void dealWithFlipChange(Configuration configuration, boolean z) {
        Context context = AODApplication.sysuiContext;
        EnvStateManager.markEnvStateDirty(context);
        Log.d("DozeHost", "dealWithFlipChange: create= " + z + ", isTinyScreen = " + DeviceHelper.isTinyScreen(context) + ", newConfig = " + configuration);
        if (Utils.isFlipDevice()) {
            if (z) {
                FlipLinkageStyleController.INSTANCE.reset();
            }
            requestDrawWakelock(2000L, "DozeHost");
            if (!DeviceHelper.isTinyScreen(context)) {
                if (DeviceHelper.isWideScreen(context) || !FlipLinkageStyleController.INSTANCE.isFlipped() || z) {
                    return;
                }
                this.removeAODViewRunnable.run();
                return;
            }
            this.isFlipping = false;
            FlipLinkageStyleController flipLinkageStyleController = FlipLinkageStyleController.INSTANCE;
            if (!flipLinkageStyleController.isFlipped() && !z) {
                this.isFlipping = true;
            }
            flipLinkageStyleController.setFlipped(true);
            flipLinkageStyleController.updateClockOrientation(DisplayUtils.getCutoutPosition(this.mContext));
            if (z || this.service == null) {
                return;
            }
            if (this.isFlipping) {
                setAodVisibility(false);
                final DozeMachine dozeMachine = this.service.getDozeMachine();
                if (dozeMachine != null) {
                    dozeMachine.preDestroyForChange();
                    updateClockTypeForChange();
                    if (!flipLinkageStyleController.isUsingFlip(this.mContext) || AODSettings.isSuperSaveModeOpen(this.mContext)) {
                        dozeMachine.requestState(DozeMachine.State.DOZE);
                        this.mHandler.postDelayed(this.mWakeLock.wrap(new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                dozeMachine.requestState(DozeMachine.State.FINISH);
                            }
                        }), 800L);
                    } else {
                        dozeMachine.requestState(DozeMachine.State.DOZE_AOD_PAUSING);
                        if (Utils.aodDisabledForScheduleMode(this.mContext)) {
                            dozeMachine.requestState(DozeMachine.State.DOZE);
                        } else {
                            this.mHandler.postDelayed(this.mWakeLock.wrap(new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    DozeHost.m193$r8$lambda$yCezaC1QHA3L92j9eRx31dSZTU(dozeMachine);
                                }
                            }), 600L);
                        }
                    }
                    if (dozeMachine.getState() != DozeMachine.State.FINISH) {
                        dozeMachine.rebindForChange();
                    }
                } else {
                    updateClockTypeForChange();
                    setAodVisibility(true);
                }
                if (this.mAODView != null) {
                    AnalyticManager.getInstance(this.mContext).trackAodShowEvent(this.mContainer);
                }
            }
            updateClockOrientation();
        }
    }

    public void destroy() {
        Runnable runnable;
        View view = this.mContainer;
        if (view != null && (runnable = this.mMonitorSampleRunnable) != null) {
            view.removeCallbacks(runnable);
            this.mMonitorSampleRunnable = null;
        }
        EnvStateManager.removeInfoOfContext(AODApplication.sysuiContext);
    }

    public void disableShowWallpaper() {
        if (this.mNeedShowWallpaper) {
            this.mNeedShowWallpaper = false;
            AODView aODView = this.mAODView;
            if (aODView != null) {
                aODView.setBackgroundColor(-16777216);
            }
        }
    }

    public void dozeTimeTick(boolean z) {
        AODView aODView = this.mAODView;
        if (aODView != null) {
            aODView.handleUpdateView(z, true, false);
        }
    }

    public ViewGroup findFocusNotificationContainer() {
        AODView aODView = this.mAODView;
        if (aODView != null) {
            return (ViewGroup) aODView.findViewById(R.id.fn_container);
        }
        return null;
    }

    public ViewGroup findNotificationContainer() {
        AODView aODView = this.mAODView;
        if (aODView != null) {
            return (ViewGroup) aODView.findViewById(R.id.icons);
        }
        return null;
    }

    public void fireAnimateState() {
        Iterator it = new ArrayList(this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onAodAnimate(this.mShowAodAnimate);
        }
    }

    public void fireAodState(final boolean z, final String str) {
        if ("reason_fod".equals(str)) {
            this.mFodTurnedOnScreen = z;
        }
        setAodStateReason(z ? str : null);
        final ArrayList arrayList = new ArrayList(this.mCallbacks);
        this.mainExecutor.execute(this.mWakeLock.wrapWithLog(new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DozeHost.$r8$lambda$5W_x7GOG87Sx4n79ikcOTKgY42s(arrayList, z, str);
            }
        }, "fireAodState"));
    }

    public void fireFingerprintPressed(final boolean z, final boolean z2) {
        this.mFingerprintPressed = z;
        this.mainExecutor.execute(this.mWakeLock.wrapWithLog(new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onFingerprintPressed(z, z2);
            }
        }, "fireFingerprintPressed"));
    }

    public void firePowerSaveChanged() {
        boolean zIsPowerSaveMode = Utils.isPowerSaveMode(this.mContext);
        Iterator it = new ArrayList(this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onPowerSaveChanged(zIsPowerSaveMode);
        }
    }

    public AODView getAODView() {
        return this.mAODView;
    }

    public boolean getAnimationState() {
        return this.isAnimationDisappearing;
    }

    public String getAodStateReason() {
        return this.mAodStateOnReason;
    }

    public Map getAodViewTopAndBottom() {
        return this.mAodPosition;
    }

    public float getClockTranslation() {
        return this.mClockTranslation;
    }

    public View getContainer() {
        return this.mContainer;
    }

    public PowerManager.WakeLock getDrawWakeLock(String str) {
        Log.d("DozeHost", str + "#getDrawWakeLock");
        if (this.mDrawWakeLock == null) {
            PowerManager.WakeLock wakeLockCreateDrawInner = WakeLock.createDrawInner(this.mPowerManager, "aod:dozeHostDrawWakeLock");
            this.mDrawWakeLock = wakeLockCreateDrawInner;
            wakeLockCreateDrawInner.setReferenceCounted(false);
        }
        return this.mDrawWakeLock;
    }

    public final LinkageStyleClockView getLinkageClockView() {
        View view = this.mContainer;
        if (view != null) {
            return (LinkageStyleClockView) view.findViewById(R.id.linkage_clock_container);
        }
        return null;
    }

    public DozeMachine.Service getService() {
        return this.service;
    }

    public boolean hasFodTurnedOnScreen() {
        return this.mFodTurnedOnScreen;
    }

    public void initMaskViewIfNeeded() {
        AODView aODView;
        if (!this.startMaskAnimationIfNeeded || (aODView = this.mAODView) == null) {
            return;
        }
        aODView.initMaskView();
    }

    public void invalidateAODView() {
        this.mainExecutor.execute(new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                DozeHost.$r8$lambda$R2q3pXW6ijlPyMtu6L63AXKrkM0(this.f$0);
            }
        });
    }

    public boolean isAnimateShowing() {
        return this.mShowAodAnimate;
    }

    public boolean isAodVisible() {
        return this.mIsAodVisible;
    }

    public boolean isDozing() {
        return this.mDozingRequested;
    }

    public boolean isFingerprintPressed() {
        return this.mFingerprintPressed;
    }

    public boolean isFingerprintUnlockAvailable() {
        return this.mFingerprintUnlockAvailable;
    }

    public boolean isFlipping() {
        return this.isFlipping;
    }

    public boolean isFullAod() {
        return AODSettings.needFullAod();
    }

    public boolean isGxzwIconShown() {
        return !this.mGxzwIconTransparent;
    }

    public boolean isPulsingBlocked() {
        return false;
    }

    public boolean isSimPinSecure() {
        return this.mSimPinSecure;
    }

    public boolean isSuppressAod() {
        return this.mSuppressAod;
    }

    public void notifyKeycodeGoto() {
        Log.i("DozeHost", "notifyKeycodeGoto: ");
        Iterator it = new ArrayList(this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).notifyKeycodeGoto();
        }
    }

    public void onCommand(String str, int i) {
        if ("keyguard_occluded".equals(str)) {
            setKeyguardOccluded(i == 1);
            return;
        }
        if ("suppressAmbientDisplay".equals(str)) {
            setSuppressAmbientDisplay(i == 1);
            return;
        }
        if ("clock_show".equals(str)) {
            setClockViewVisible(i == 1);
            return;
        }
        if (!"full_aod".equals(str)) {
            if ("trackAodEvent".equals(str)) {
                AnalyticManager.getInstance(this.mContext).trackAodEvent();
                return;
            }
            return;
        }
        boolean zNeedFullAod = AODSettings.needFullAod();
        AODSettings.setNeedFullAod(i == 1);
        if (zNeedFullAod != AODSettings.needFullAod()) {
            AodViewManager.INSTANCE.clearAodViewCache();
            prepareAodViewAndShow();
            if (zNeedFullAod) {
                this.mAODView.setBackgroundColor(-16777216);
            }
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        dealWithFlipChange(configuration, false);
        boolean z = (this.mConfiguration.updateFrom(configuration) & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0;
        if (z) {
            ScreenResolutionUtils.cleanRealSize();
        }
        if (Utils.isFoldDevice() && z) {
            dealWithChange();
        }
    }

    public void onDoAodAnimation(ViewGroup viewGroup, boolean z) {
        if (viewGroup == null || !z) {
            this.mPreDisplayedAod = false;
            return;
        }
        AODSettings.setNeedLinkageWallpaperAnim(true);
        this.mIsAodVisible = !Utils.aodGone(this.mContext);
        if (this.mIsAodVisible) {
            this.mFingerprintUnlockAvailable = Utils.isUnlockWithFingerprintPossible(this.mContext, this);
            this.removeAODViewRunnable.run();
            viewGroup.setVisibility(0);
            viewGroup.removeAllViews();
            prepareAodViewAndShow(viewGroup);
            this.mPreDisplayedAod = true;
        }
    }

    public final void onFingerprintPressed(boolean z, boolean z2) {
        if (this.mDozing) {
            Iterator it = new ArrayList(this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onFingerprintPressed(z, z2);
            }
        }
        if (Utils.isGxzwSensor()) {
            this.mHandler.removeCallbacks(this.mFingerUpRunnable);
            if (!z) {
                this.mHandler.postDelayed(this.mFingerUpRunnable, 30L);
                return;
            }
            View view = this.mContainer;
            if (view != null) {
                view.animate().cancel();
                this.mContainer.setAlpha(0.0f);
            }
        }
    }

    public void onGxzwIconChanged(final boolean z) {
        this.mGxzwIconTransparent = z;
        this.mainExecutor.execute(this.mWakeLock.wrapWithLog(new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                DozeHost.m191$r8$lambda$fWR6fwqKujkYya9XiTabRQdnA(this.f$0, z);
            }
        }, "onGxzwIconChanged"));
    }

    public void onKeyguardTransparent() {
        this.mainExecutor.execute(this.mWakeLock.wrapWithLog(this.removeAODViewRunnable, "onKeyguardTransparent"));
    }

    public void onScreenTurnOnDelayed(boolean z) {
        this.mNeedTurnOnDelay = z;
    }

    public final void onStopDoze() {
        Runnable runnable;
        cancelEnterAnimation();
        View view = this.mContainer;
        if (view != null && (runnable = this.mMonitorSampleRunnable) != null) {
            view.removeCallbacks(runnable);
            this.mMonitorSampleRunnable = null;
        }
        if (this.mDozingRequested) {
            this.mDozingRequested = false;
            ThemeDataManager.getInstance(this.mContext).setThemeLoadListener(null);
            Iterator it = new ArrayList(this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onDozingRequested(this.mDozingRequested);
            }
            DozeLog.traceDozing(this.mContext, this.mDozing);
            this.mDozing = this.mDozingRequested;
            Log.i("DozeHost", "onStopDoze: needTurnOnDelay " + this.mNeedTurnOnDelay);
            AODView aODView = this.mAODView;
            if (aODView != null) {
                aODView.setVisibility(0);
            }
            this.mHandler.postDelayed(this.removeAODViewRunnable, 550L);
        }
    }

    public void onUpdatePositionTimer() {
        AODView aODView = this.mAODView;
        if (aODView != null) {
            aODView.onUpdatePositionTimer();
        }
    }

    public final boolean preDisplayedAod() {
        return this.mPreDisplayedAod;
    }

    public final void prepareAodViewAndShow() {
        prepareAodViewAndShow(null);
    }

    public final void prepareAodViewAndShow(ViewGroup viewGroup) {
        if (!preDisplayedAod() || this.mAODView == null) {
            if (viewGroup == null) {
                viewGroup = this.service.getSysuiContainer();
            }
            if (viewGroup == null) {
                return;
            }
            Trace.beginSection("DozeHost_prepareAodView");
            AODView aODViewShowSecurityIdentityViewAt = showSecurityIdentityViewAt(viewGroup);
            this.mAODView = aODViewShowSecurityIdentityViewAt;
            aODViewShowSecurityIdentityViewAt.setAlpha(1.0f);
            this.mAODView.setVisibility(0);
            if (Utils.isLinkageState(AODApplication.sInstance) && this.mNeedLinkageWallpaperAnim && !Utils.isFsAodSameToLock(AODApplication.sInstance)) {
                this.mAODView.setClockViewVisible(false);
            }
            this.mDoubleTapHelper = new DoubleTapHelper(this.mAODView, 200L, new DoubleTapHelper.ActivationListener() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda5
                @Override // com.miui.aod.util.DoubleTapHelper.ActivationListener
                public final void onActiveChanged(boolean z) {
                    DozeHost.m189$r8$lambda$ZTwEF5fApWAF47F2d_bUhWAY7s(z);
                }
            }, new DoubleTapHelper.DoubleTapListener() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda6
                @Override // com.miui.aod.util.DoubleTapHelper.DoubleTapListener
                public final boolean onDoubleTap() {
                    return DozeHost.m188$r8$lambda$WCbo8FmmUAk7tzMu1rixfbSc4U(this.f$0);
                }
            }, null, null);
            if (this.mNeedShowWallpaper) {
                this.mAODView.setBackgroundColor(this.mKeyguardOccluded ? -16777216 : 0);
            }
            this.mAODView.handleUpdateView(true, false, false);
            if (this.mIsAodVisible) {
                this.mBlackBg.setVisibility(8);
                if (this.mNeedShowWallpaper && AODUpdatePositionController.supportWallpaperChangeInitPosition() && !AODStyleController.getStyleInfo(this.mContext).supportSuperWallpaperMode()) {
                    this.mContainer.setVisibility(4);
                    this.mPendingContainerVisible = true;
                } else {
                    this.mContainer.setVisibility(0);
                    this.mAODView.updateClockViewVisible();
                }
                this.mContainer.setAlpha(0.0f);
                final boolean z = this.mNeedLinkageWallpaperAnim;
                startEnterAnim(new WrappedAnimatorListenerAdapter() { // from class: com.miui.aod.DozeHost.5
                    public final AnimTracer.Manual mAnimTracer = AnimTracer.manual("KeyguardAnim-enterAod");

                    @Override // com.miui.aod.util.WrappedAnimatorListenerAdapter, android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        if (z) {
                            return;
                        }
                        this.mAnimTracer.end();
                    }

                    @Override // com.miui.aod.util.WrappedAnimatorListenerAdapter, android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        super.onAnimationStart(animator);
                        if (z) {
                            return;
                        }
                        this.mAnimTracer.begin();
                    }
                }, true);
            } else {
                if (rootViewCanBeHidden()) {
                    Log.d("DozeHost", "make rootView GONE");
                    this.mAODView.setVisibility(8);
                    this.mAODView.setBackgroundColor(-16777216);
                }
                this.mBlackBg.setVisibility(this.mNeedShowWallpaper ? 0 : 8);
                this.mContainer.setVisibility(4);
            }
            if (isFullAod() && Utils.isCurrentScreenSupportFsAod(this.mContext)) {
                setupFullAodViewState();
            } else {
                this.mContainer.setTransitionAlpha(1.0f);
            }
            Trace.endSection();
            FlipLinkageStyleController flipLinkageStyleController = FlipLinkageStyleController.INSTANCE;
            if (flipLinkageStyleController.isUsingFlip(AODApplication.sInstance) && flipLinkageStyleController.isFlipped()) {
                AnalyticManager.getInstance(this.mContext).trackAodShowEvent(this.mContainer);
            }
        }
    }

    public void printMemInfo() {
        BackgroundThread.removeCallbacks(this.mPrintMemInfoTask);
        BackgroundThread.postDelayed(this.mPrintMemInfoTask, 2000L);
    }

    public void releaseDrawWakelock() {
        PowerManager.WakeLock wakeLock = this.mDrawWakeLock;
        if (wakeLock == null || !wakeLock.isHeld()) {
            return;
        }
        this.mDrawWakeLock.release();
    }

    public void removeAODView() {
        AODView aODView = this.mAODView;
        if (aODView != null && this.mWindowAdded) {
            aODView.setVisibility(8);
            DozeMachine.Service service = this.service;
            if (service != null && service.getSysuiContainer() != null) {
                this.service.getSysuiContainer().removeView(this.mAODView);
            }
            this.mWindowAdded = false;
            Log.d("DozeHost", "mWindowAdded = false");
        }
        AODView aODView2 = this.mAODView;
        if (aODView2 != null) {
            aODView2.releaseResourcesIfNeeded();
            ViewGroup viewGroupFindFocusNotificationContainer = findFocusNotificationContainer();
            if (viewGroupFindFocusNotificationContainer != null) {
                viewGroupFindFocusNotificationContainer.removeAllViews();
            }
            this.mAODView = null;
        }
        this.mDoubleTapHelper = null;
        this.mBlackBg = null;
        this.mNeedTurnOnDelay = false;
        WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            wakeLock.release("DozeHost#removeAodView");
        }
    }

    public void removeCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }

    public void requestDrawWakelock(long j, String str) {
        getDrawWakeLock(str);
        Log.d("DozeHost", str + "#requestDrawWakelock: " + j);
        this.mDrawWakeLock.acquire(Math.min((long) Utils.getTemporaryStyleDuration(), Math.max(0L, j)));
    }

    public void resetPendingVisible() {
        this.mPendingContainerVisible = false;
    }

    public boolean rootViewCanBeHidden() {
        return (isFingerprintUnlockAvailable() || Utils.isAodEnable(this.mContext) || DisplayUtils.hasVirtualDisplay(this.mContext)) ? false : true;
    }

    public void screenTurnOn() {
        if (this.mNeedTurnOnDelay && this.mWindowAdded && !this.mDozingRequested && this.mHandler.hasCallbacks(this.removeAODViewRunnable)) {
            Log.w("DozeHost", "turn on screen base on previous mNeedTurnOnDelay = true");
            this.mHandler.removeCallbacks(this.removeAODViewRunnable);
            this.mainExecutor.execute(this.removeAODViewRunnable);
        }
    }

    public void setAnimationState(boolean z) {
        this.isAnimationDisappearing = z;
    }

    public void setAodStateReason(String str) {
        this.mAodStateOnReason = str;
    }

    public void setAodVisibility(final boolean z) {
        this.mIsAodVisible = z;
        AnalyticManager.getInstance(this.mContext).trackAodVisibility(this.mIsAodVisible);
        this.mainExecutor.execute(this.mWakeLock.wrapWithLog(new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                DozeHost.$r8$lambda$FURgNVtqV6ZosGhdnzpTiQlUQZY(this.f$0, z);
            }
        }, "setAodClockVisibility(1"));
    }

    public void setAodVisibility(boolean z, Callback callback) {
        setAodVisibility(z, callback, false);
    }

    public void setAodVisibility(boolean z, Callback callback, boolean z2) {
        this.mIsAodVisible = z;
        AnalyticManager.getInstance(this.mContext).trackAodVisibility(this.mIsAodVisible);
        this.mainExecutor.execute(this.mWakeLock.wrapWithLog(new AnonymousClass1(z, callback, z2), "setAodClockVisibility(2"));
    }

    public void setAodWindow() {
        WindowManager.LayoutParams layoutParams = this.lp;
        if (layoutParams != null) {
            try {
                layoutParams.screenOrientation = 1;
                this.service.getSysuiContainer().updateViewLayout(this.mAODView, this.lp);
            } catch (Exception unused) {
                Log.d("DozeHost", "updateView lp orientation");
            }
        }
    }

    public void setClockViewVisible(boolean z) {
        AODView aODView = this.mAODView;
        if (aODView != null) {
            aODView.setClockViewVisible(z);
        }
    }

    public final void setKeyguardOccluded(boolean z) {
        if (this.mKeyguardOccluded != z) {
            this.mKeyguardOccluded = z;
            if (this.mNeedShowWallpaper && this.mAODView != null && Utils.isUsingSuperWallpaperStyle(AODApplication.sInstance)) {
                this.mainExecutor.execute(new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        DozeHost.$r8$lambda$S2RE2d_J7xaX1RvhyL1X_gsRhqE(this.f$0);
                    }
                });
            }
        }
    }

    public void setNotificationAnimate(boolean z) {
        this.mShowAodAnimate = z;
    }

    public void setService(DozeMachine.Service service) {
        this.service = service;
    }

    public void setShouldUseMaskAnimation(boolean z) {
        if (isFullAod()) {
            z = false;
        }
        this.startMaskAnimationIfNeeded = z;
    }

    public void setSimPinSecure(boolean z) {
        this.mSimPinSecure = z;
    }

    public void setSunImage(int i) {
        AODView aODView = this.mAODView;
        if (aODView != null) {
            aODView.setSunImage(i);
        }
    }

    public final void setSuppressAmbientDisplay(boolean z) {
        if (this.mSuppressAod != z) {
            this.mSuppressAod = z;
            if (this.mSuppressAod) {
                this.mainExecutor.execute(new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        DozeHost.m190$r8$lambda$dn5GnbmHWUOqS1JC6N2uG3vDSw(this.f$0);
                    }
                });
            }
        }
    }

    public final void setupFullAodViewState() {
        AODView aODView = this.mAODView;
        if (aODView == null || this.mContainer == null) {
            return;
        }
        aODView.setAlpha(0.0f);
        this.mAODView.setBackgroundColor(-16777216);
        this.mContainer.setTransitionAlpha(0.0f);
        Runnable runnable = this.mMonitorSampleRunnable;
        if (runnable != null) {
            this.mContainer.removeCallbacks(runnable);
            this.mMonitorSampleRunnable = null;
        }
        this.mContainer = null;
    }

    public final AODView showSecurityIdentityViewAt(ViewGroup viewGroup) {
        this.mNeedShowWallpaper = false;
        this.mNeedLinkageWallpaperAnim = AODSettings.needLinkageWallpaperAnim();
        if (Utils.isUsingSuperWallpaperStyle(AODApplication.sInstance) && AODSettings.isSupportAodWallpaper(this.mContext) && Utils.isAodEnable(this.mContext)) {
            WallPaperUtils.updateWallpaperSupportsAmbientModeState();
            this.mNeedShowWallpaper = WallPaperUtils.isWallpaperSupportsAmbientMode();
        }
        removeAODView();
        AODView aODViewFindAodView = AodViewManager.INSTANCE.findAodView(this.mContext, this);
        aODViewFindAodView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda13
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return DozeHost.$r8$lambda$Xr1QEGs8MtvpTwMILueS3xblWCA(this.f$0, view, motionEvent);
            }
        });
        boolean z = Utils.isLinkageState(AODApplication.sInstance) && this.mNeedLinkageWallpaperAnim;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2026, 8455424, (Utils.isGxzwSensor() || this.mNeedShowWallpaper || z) ? -2 : -1);
        this.lp = layoutParams;
        int i = layoutParams.flags & (-131081);
        layoutParams.flags = i | 32;
        layoutParams.softInputMode = 3;
        if (this.mNeedShowWallpaper) {
            layoutParams.flags = i | 1048608;
        }
        CommonUtils.setLayoutInDisplayCutoutMode(layoutParams, 1);
        this.lp.setTitle("AOD");
        WindowInsetsController windowInsetsController = aODViewFindAodView.getWindowInsetsController();
        if (windowInsetsController != null) {
            windowInsetsController.hide(WindowInsets.Type.statusBars());
            windowInsetsController.hide(WindowInsets.Type.navigationBars());
        }
        int rotation = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
        if (!Utils.inLargeScreen(this.mContext) || rotation == 0) {
            this.lp.screenOrientation = 1;
        } else if (rotation == 1) {
            this.lp.screenOrientation = 0;
        } else if (rotation != 3) {
            this.lp.screenOrientation = 1;
        } else {
            this.lp.screenOrientation = 8;
        }
        View view = this.mAODView;
        if (view != null) {
            viewGroup.removeView(view);
        }
        if (aODViewFindAodView.getParent() instanceof ViewGroup) {
            ViewGroup viewGroup2 = (ViewGroup) aODViewFindAodView.getParent();
            Log.w("DozeHost", "view already has parent: " + viewGroup2);
            viewGroup2.removeView(aODViewFindAodView);
        }
        viewGroup.addView(aODViewFindAodView, this.lp);
        this.mWindowAdded = true;
        Log.d("DozeHost", "mWindowAdded = true");
        this.mContainer = aODViewFindAodView.findViewById(R.id.clock_container);
        applyLinkageState();
        View viewFindViewById = aODViewFindAodView.findViewById(R.id.black_bg_view);
        this.mBlackBg = viewFindViewById;
        viewFindViewById.setVisibility(8);
        if (!this.mDozingRequested) {
            aODViewFindAodView.setVisibility(8);
            this.mContainer.setAlpha(0.0f);
        }
        if (!this.mNeedShowWallpaper && !z) {
            return aODViewFindAodView;
        }
        aODViewFindAodView.setBackgroundColor(0);
        return aODViewFindAodView;
    }

    public void startDozing() {
        if (this.mDozingRequested) {
            return;
        }
        dealWithFlipChange(this.mContext.getResources().getConfiguration(), true);
        this.mDozingRequested = true;
        resetPendingVisible();
        Iterator it = new ArrayList(this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onDozingRequested(this.mDozingRequested);
        }
        DozeLog.traceDozing(this.mContext, this.mDozing);
        this.mDozing = this.mDozingRequested;
        this.mFingerprintUnlockAvailable = Utils.isUnlockWithFingerprintPossible(this.mContext, this);
        this.mIsAodVisible = !Utils.aodGone(this.mContext);
        AnalyticManager.getInstance(this.mContext).trackAodVisibility(this.mIsAodVisible);
        if (!ThemeDataManager.getInstance(this.mContext).isLoadingTheme()) {
            prepareAodViewAndShow();
        } else {
            Log.i("DozeHost", "startDozing: isLoadingTheme");
            ThemeDataManager.getInstance(this.mContext).setThemeLoadListener(new ThemeDataManager.ThemeLoadListener() { // from class: com.miui.aod.DozeHost.3
                @Override // com.miui.aod.theme.ThemeDataManager.ThemeLoadListener
                public void onComplete(StyleInfo styleInfo) {
                    Log.i("DozeHost", "onComplete: ");
                    DozeHost.this.prepareAodViewAndShow();
                    ThemeDataManager.getInstance(DozeHost.this.mContext).setThemeLoadListener(null);
                }

                @Override // com.miui.aod.theme.ThemeDataManager.ThemeLoadListener
                public void onError(Throwable th) {
                    Log.i("DozeHost", "onError: ");
                    DozeHost.this.prepareAodViewAndShow();
                    ThemeDataManager.getInstance(DozeHost.this.mContext).setThemeLoadListener(null);
                }
            });
        }
    }

    public void startEnterAnim(final AnimatorListenerAdapter animatorListenerAdapter, boolean z) {
        View view;
        String templateId;
        this.isAnimationDisappearing = false;
        final StyleInfo clockStyleInfo = AODSettings.getClockStyleInfo(this.mContext);
        long startDelay = clockStyleInfo.getStartDelay();
        if (Utils.needDelayRegisterSensor()) {
            startDelay = Math.max(400L, startDelay);
        }
        if (AODSettings.needKeepScreenOnAtFirst() && z) {
            setShouldUseMaskAnimation(true);
        } else {
            requestDrawWakelock(5000L, "DozeHost#startEnterAnim");
        }
        if (this.mContainer == null) {
            return;
        }
        if (clockStyleInfo.hasAnimation()) {
            this.mAODView.clearClockPanelAnimation(z);
            this.mContainer.setAlpha(0.0f);
            Runnable runnable = new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    DozeHost.$r8$lambda$RK5VK4XHijxPfsnIHn9sYSJIHH4(this.f$0, animatorListenerAdapter, clockStyleInfo);
                }
            };
            this.mEnterAnimDelayRunnable = runnable;
            this.mHandler.postDelayed(runnable, startDelay);
        } else {
            if (clockStyleInfo instanceof FlipLinkageClockCategoryInfo) {
                if (AODSettings.needKeepScreenOnAtFirst() && this.isFlipping) {
                    this.isFlipping = false;
                    BackgroundThread.post(new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda15
                        @Override // java.lang.Runnable
                        public final void run() {
                            Utils.updateTouchModeForKeepScreenOnIfNeeded(0, true);
                        }
                    });
                }
                this.mContainer.clearAnimation();
                Folme.clean(this.mContainer);
                FlipLinkageStyleClockView flipLinkageStyleClockView = (FlipLinkageStyleClockView) this.mContainer.findViewById(R.id.flip_linkage_clock_container);
                AODTinyMiuiClockView miuiClockView = flipLinkageStyleClockView != null ? flipLinkageStyleClockView.getMiuiClockView() : null;
                TinyClockBean clockBean = miuiClockView != null ? miuiClockView.getClockBean() : null;
                templateId = clockBean != null ? clockBean.getTemplateId() : null;
                if (z && templateId != null && miuiClockView != null && templateId.equals("interactive_pets")) {
                    if (z) {
                        this.mContainer.setTranslationY(0.0f);
                    }
                    AnimationExtKt.alphaDelayAnimationForPets(this.mContainer, animatorListenerAdapter);
                    ((TinyMiuiClockController.IClockView) miuiClockView.getIClockView(TinyClockViewType.THIS)).toLinkageAodState();
                    if (this.mNeedLinkageWallpaperAnim) {
                        this.mNeedLinkageWallpaperAnim = false;
                        return;
                    }
                    return;
                }
                if (this.mNeedLinkageWallpaperAnim) {
                    AnimationExtKt.animate(this.mContainer, (FlipLinkageClockCategoryInfo) clockStyleInfo, animatorListenerAdapter);
                    this.mNeedLinkageWallpaperAnim = false;
                    return;
                } else {
                    if (z) {
                        this.mContainer.setTranslationY(0.0f);
                    }
                    this.mContainer.setAlpha(0.0f);
                    this.mContainer.animate().alpha(1.0f).setDuration(1000L).setInterpolator(new AccelerateInterpolator()).setStartDelay(startDelay).setListener(animatorListenerAdapter).start();
                    return;
                }
            }
            if (!Utils.isLinkageState(this.mContext)) {
                this.mContainer.setAlpha(0.0f);
                this.mContainer.animate().alpha(1.0f).setDuration(1000L).setInterpolator(new AccelerateInterpolator()).setStartDelay(startDelay).setListener(animatorListenerAdapter).start();
            } else if (!this.mNeedLinkageWallpaperAnim || (view = this.mContainer) == null) {
                if (z) {
                    this.mContainer.setTranslationY(this.mClockTranslation);
                    this.mContainer.setAlpha(0.0f);
                }
                this.mContainer.animate().alpha(1.0f).setDuration(1000L).setInterpolator(new AccelerateInterpolator()).setStartDelay(startDelay).setListener(animatorListenerAdapter).start();
            } else {
                LinkageStyleClockView linkageStyleClockView = (LinkageStyleClockView) view.findViewById(R.id.linkage_clock_container);
                MiuiClockView miuiClockView2 = linkageStyleClockView != null ? linkageStyleClockView.getMiuiClockView() : null;
                ClockBean clockBean2 = miuiClockView2 != null ? miuiClockView2.getClockBean() : null;
                templateId = clockBean2 != null ? clockBean2.getTemplateId() : null;
                int style = clockBean2 != null ? clockBean2.getStyle() : 0;
                if (templateId != null) {
                    if (templateId.equals("rhombus")) {
                        LinkageStyleController linkageStyleController = LinkageStyleController.INSTANCE;
                        View view2 = this.mContainer;
                        float f = this.mClockTranslation;
                        linkageStyleController.aodRhombusTransAnimation(view2, 200.0f + f, f, animatorListenerAdapter, z);
                    } else if ("doodle".equals(templateId) || style == 6) {
                        if (z) {
                            this.mContainer.setTranslationY(this.mClockTranslation);
                        }
                        LinkageStyleController.INSTANCE.aodLinkageAlphaDelayAnimation(this.mContainer, animatorListenerAdapter);
                    } else {
                        LinkageStyleController linkageStyleController2 = LinkageStyleController.INSTANCE;
                        linkageStyleController2.aodLinkageTransAnimation(this.mContainer, linkageStyleController2.getStartY(this.mContext, templateId, style), this.mClockTranslation, animatorListenerAdapter, z);
                    }
                }
                this.mNeedLinkageWallpaperAnim = false;
            }
        }
        try {
            View view3 = this.mContainer;
            if (view3 != null) {
                Runnable runnable2 = this.mMonitorSampleRunnable;
                if (runnable2 != null) {
                    view3.removeCallbacks(runnable2);
                }
                Runnable runnable3 = new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        DozeHost.$r8$lambda$TgvE_iVEvo7DljMh58qimTnHJvw(this.f$0, clockStyleInfo);
                    }
                };
                this.mMonitorSampleRunnable = runnable3;
                this.mContainer.postDelayed(runnable3, 150L);
            }
        } catch (Exception e) {
            Log.w("DozeHost", "monitor sampleOnAodEnter schedule error", e);
        }
    }

    public void startMaskAnimationIfNeeded() {
        if (this.startMaskAnimationIfNeeded) {
            this.startMaskAnimationIfNeeded = false;
            this.mainExecutor.execute(new Runnable() { // from class: com.miui.aod.DozeHost$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    DozeHost.$r8$lambda$oCvCBwvHKiJSiyNI8YkzIOxw_4Q(this.f$0);
                }
            });
        }
    }

    public void stopDozing() {
        releaseDrawWakelock();
        this.mFodTurnedOnScreen = false;
        if (Looper.myLooper() == this.mHandler.getLooper()) {
            onStopDoze();
        } else {
            this.mHandler.postAtFrontOfQueue(this.mStopAodRunnable);
        }
        this.mFingerprintPressed = false;
        resetPendingVisible();
        AnalyticManager.getInstance(this.mContext).trackAodVisibility(false);
    }

    public void updateClockColor(boolean z, HashMap map, Map map2) {
        AODApplicationDelegate.mRealClockStyleIsDeep = Boolean.valueOf(z);
        AODApplicationDelegate.mRealClockColorMap = map;
        AODApplicationDelegate.mRealFullImageMap = map2;
        AODView aODView = this.mAODView;
        if (aODView != null) {
            aODView.updateClockColor();
        }
    }

    public void updateClockOrientation() {
        AODView aODView = this.mAODView;
        if (aODView != null) {
            aODView.updateClockOrientation();
            this.mAODView.updateClockViewVisible();
            this.mAODView.handleUpdateIcons(true);
        }
    }

    public void updateClockTranslation(Bundle bundle) {
        View view;
        float f = bundle.getFloat("clockTranslation");
        String string = bundle.getString(a.a);
        Log.d("DozeHost", "updateClockTranslation: " + f + ", action: " + string);
        if (this.mClockTranslation != f) {
            this.mClockTranslation = f;
            if (!"clockChanged".equals(string) || (view = this.mContainer) == null) {
                return;
            }
            Folme.useAt(view).cancel();
            this.mContainer.setTranslationY(this.mClockTranslation);
        }
    }

    public void updateClockType() {
        View view = this.mContainer;
        if (view != null && view.getVisibility() != 0) {
            this.mContainer.setVisibility(0);
        }
        AODView aODView = this.mAODView;
        if (aODView != null) {
            aODView.updateClockType();
        }
    }

    public void updateClockTypeForChange() {
        Log.d("DozeHost", "updateClockTypeForChange: ");
        AODView aODView = this.mAODView;
        if (aODView == null || this.mContainer == null) {
            Log.e("DozeHost", "updateClockTypeForChange: mAODView == null");
            return;
        }
        aODView.preDestroyForChange();
        this.mAODView.setAlpha(1.0f);
        this.mAODView.setVisibility(0);
        this.mAODView.setBackgroundColor(-16777216);
        this.mAODView.setClockViewVisible(false);
        AODUpdatePositionController positionController = this.mAODView.getPositionController();
        if (positionController != null) {
            positionController.updateCanMove();
        }
        this.mAODView.updateClockType();
        this.mAODView.setClockViewVisible(true);
        this.mAODView.updateFocusNotificationMaxSize();
        this.mAODView.updateIconsTranslationXList();
        this.mNeedLinkageWallpaperAnim = false;
        if (Utils.isFlipDevice()) {
            this.mClockTranslation = 0.0f;
        }
        this.mAodPosition = null;
        if (isFullAod() && Utils.isCurrentScreenSupportFsAod(this.mContext)) {
            setupFullAodViewState();
        } else if (this.mIsAodVisible) {
            this.mBlackBg.setVisibility(8);
            this.mContainer.setVisibility(0);
            this.mContainer.setAlpha(0.0f);
            this.mContainer.clearAnimation();
            startEnterAnim(new WrappedAnimatorListenerAdapter() { // from class: com.miui.aod.DozeHost.4
                @Override // com.miui.aod.util.WrappedAnimatorListenerAdapter, android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                }
            }, true);
        } else {
            this.mBlackBg.setVisibility(8);
            this.mContainer.setVisibility(4);
        }
        AodViewManager.INSTANCE.clearAodViewCache();
    }

    public void updateLinkagePosition(Bundle bundle) {
        if (bundle != null) {
            this.mLinkageMagazineHeight = bundle.getInt("magazineHeight", 0);
        }
    }

    public void updatePosition() {
        AODView aODView = this.mAODView;
        if (aODView != null) {
            this.mAodPosition = aODView.getContainerTopAndHeight();
        }
    }

    public void updateSuppressAmbientDisplay() {
        setSuppressAmbientDisplay(Utils.isAmbientDisplaySuppressed(this.mContext));
    }
}
