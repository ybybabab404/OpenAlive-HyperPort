package com.miui.aod;

import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.doze.SettingsHolder;
import com.miui.aod.flip.FlipLinkageStyleClockView;
import com.miui.aod.flip.FlipLinkageStyleController;
import com.miui.aod.linkagestyle.LinkageStyleClockView;
import com.miui.aod.superwallpaper.SuperWallpaperReceiver;
import com.miui.aod.util.AlarmTimeout;
import com.miui.aod.util.DeviceInfo;
import com.miui.aod.util.LooperExecutor;
import com.miui.aod.util.WindowUtils;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.SuperWallpaperClockView;
import com.xiaomi.onetrack.util.ac;
import java.lang.ref.WeakReference;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes.dex */
public class AODUpdatePositionController {
    public static boolean DEBUG = true;
    public static int SMART_COVOR_INITINAL_VALUE = 0;
    public static int SMART_COVOR_PERIOD = 72;
    public static int SUPER_WALLPAPER_PERIOD = 18;
    public static final String TAG = "AODUpdatePositionController";
    public static long UPDATE_POSITION_DELAY = 10000;
    public static final SparseIntArray sInitPositionMap;
    public static final SparseIntArray sTranslationMapForFingerprintIcon;
    public AlarmTimeout mAlarmTimeout;
    public IAodClock mAodClock;
    public int mAodMoveCurrent;
    public int mAodMovePeriod;
    public boolean mAscSort;
    public int mBottomExtraMargin;
    public boolean mCanMove;
    public final Context mContext;
    public final int mCutoutHeight;
    public final WakeLock mDrawWakeLock;
    public int mExtraTranslationY;
    public ValueAnimator mFreshAnimator;
    public int mGxzwIconX;
    public int mGxzwIconY;
    public final Handler mHandler;
    public final DozeHost mHost;
    public boolean mInitAscSort;
    public int mInitPositionValue;
    public final boolean mIsDisplayUpdateModeOn;
    public boolean mIsGxzwIconShow;
    public int mMode;
    public Point mScreenSize;
    public final boolean mSupportChangeInitPosition;
    public View mTargetView;
    public int mTopExtraMargin;
    public final int mTranslationX;
    public int mTranslationY;
    public float mTranslationYStep;
    public int mViewHeight;
    public int mViewTop;
    public WallpaperManager mWallpaperManager;
    public final LooperExecutor mainExecutor;
    public final EaseManager.EaseStyle moveEaseStyle = EaseManager.getStyle(0, 750.0f, 0.9f, 0.8f);
    public int mTargetY = -1;

    public static class H extends Handler {
        public final WeakReference mRef;

        public H(AODUpdatePositionController aODUpdatePositionController) {
            super(Looper.getMainLooper());
            this.mRef = new WeakReference(aODUpdatePositionController);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            AODUpdatePositionController aODUpdatePositionController = (AODUpdatePositionController) this.mRef.get();
            if (aODUpdatePositionController != null && message.what == 713) {
                View view = (View) message.obj;
                aODUpdatePositionController.setWallpaperPosition(view, message.arg1, message.arg2);
                aODUpdatePositionController.animateFresh(view);
            }
        }
    }

    public @interface IPositionMode {
    }

    public static /* synthetic */ void $r8$lambda$RJF1G8UUrzwXQVoJCQINtkIgY8Q(View view, int i, int i2) {
        view.setTranslationX(i);
        view.setTranslationY(i2);
    }

    /* JADX INFO: renamed from: $r8$lambda$fx0CCHbIVf_4qV3fNRjZfAk-QN8, reason: not valid java name */
    public static /* synthetic */ void m153$r8$lambda$fx0CCHbIVf_4qV3fNRjZfAkQN8(AODUpdatePositionController aODUpdatePositionController, ViewGroup.MarginLayoutParams marginLayoutParams) {
        View view = aODUpdatePositionController.mTargetView;
        if (view == null) {
            return;
        }
        view.setLayoutParams(marginLayoutParams);
    }

    public static /* synthetic */ void $r8$lambda$gdU2fYN11dyltvV8WhPriR36mJY(AODUpdatePositionController aODUpdatePositionController, float f) {
        Folme.clean(aODUpdatePositionController.mTargetView);
        aODUpdatePositionController.animateTranslation(aODUpdatePositionController.mTargetView, 0, f);
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(5);
        sInitPositionMap = sparseIntArray;
        SparseIntArray sparseIntArray2 = new SparseIntArray(5);
        sTranslationMapForFingerprintIcon = sparseIntArray2;
        sparseIntArray.put(-1, 19);
        sparseIntArray.put(13, 31);
        sparseIntArray.put(31, 19);
        sparseIntArray.put(19, 37);
        sparseIntArray.put(37, 13);
        sparseIntArray2.put(13, 13);
        sparseIntArray2.put(19, 25);
        sparseIntArray2.put(31, 37);
        sparseIntArray2.put(37, 49);
    }

    public AODUpdatePositionController(Context context, DozeHost dozeHost) {
        int i = 3;
        this.mContext = context;
        this.mHost = dozeHost;
        this.mainExecutor = dozeHost.mainExecutor;
        String str = TAG;
        this.mDrawWakeLock = WakeLock.wrapForAnimation(context, str, 800L);
        init();
        H h = new H(this);
        this.mHandler = h;
        this.mIsDisplayUpdateModeOn = SettingsHolder.mAodDisplayUpdate == 1;
        this.mSupportChangeInitPosition = supportWallpaperChangeInitPosition();
        boolean zIsUsingSuperWallpaperStyle = Utils.isUsingSuperWallpaperStyle(context);
        boolean zIsUsingLinkageStyle = Utils.isUsingLinkageStyle(context);
        this.mTranslationX = context.getResources().getDimensionPixelSize(R.dimen.clock_container_translation_x);
        this.mTranslationYStep = context.getResources().getDimensionPixelSize(R.dimen.clock_container_translation_y);
        this.mCutoutHeight = DeviceInfo.getStatusBarHeight(context);
        if (zIsUsingSuperWallpaperStyle) {
            i = 2;
        } else if (zIsUsingLinkageStyle) {
            FlipLinkageStyleController flipLinkageStyleController = FlipLinkageStyleController.INSTANCE;
            if (flipLinkageStyleController.isUsingFlip(context) && flipLinkageStyleController.isFlipped()) {
                i = 4;
            }
        } else {
            i = 0;
        }
        initParams(context, i);
        notifyGxzwIconState(Utils.isShowFingerprintIconQuick(dozeHost));
        this.mAscSort = this.mInitAscSort;
        this.mAlarmTimeout = new AlarmTimeout((AlarmManager) AODApplication.sysuiContext.getSystemService(AlarmManager.class), new AlarmManager.OnAlarmListener() { // from class: com.miui.aod.AODUpdatePositionController$$ExternalSyntheticLambda1
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                this.f$0.mCanMove = true;
            }
        }, str, h);
        this.mCanMove = true;
        updateCanMove();
        Log.i(str, "AODUpdatePositionController: mAscSort " + this.mAscSort + "  " + this.mMode);
    }

    public static int getInitValue() {
        int i = AODSettings.getInt(AODApplication.sInstance, "LAST_INIT_POSITION", -1);
        int i2 = sInitPositionMap.get(i, 19);
        AODSettings.putInt(AODApplication.sInstance, "LAST_INIT_POSITION", i2);
        Log.i(TAG, "getInitValue: lastInitValue " + i + " initValue " + i2);
        return i2;
    }

    public static boolean supportWallpaperChangeInitPosition() {
        boolean zIsUsingSuperWallpaperStyle = Utils.isUsingSuperWallpaperStyle(AODApplication.sInstance);
        Log.i(TAG, "supportWallpaperChangeInitPosition: " + zIsUsingSuperWallpaperStyle);
        return zIsUsingSuperWallpaperStyle;
    }

    public final void animateFresh(final View view) {
        ValueAnimator valueAnimator = this.mFreshAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(300L);
        this.mFreshAnimator = duration;
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.aod.AODUpdatePositionController$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                view.invalidate();
            }
        });
        this.mFreshAnimator.start();
    }

    public final void animateTranslation(View view, int i, float f) {
        this.mDrawWakeLock.acquire("updatePosition");
        if (this.mMode == 2) {
            scheduleUpdateWallpaperPosition(view, i, (int) f);
        } else {
            Folme.useAt(view).state().to(new AnimState("target").add(ViewProperty.TRANSLATION_X, i).add(ViewProperty.TRANSLATION_Y, f), new AnimConfig().setEase(this.moveEaseStyle).addListeners(new TransitionListener() { // from class: com.miui.aod.AODUpdatePositionController.1
                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    AODUpdatePositionController.this.mHost.updatePosition();
                }
            }));
        }
    }

    public int calculateGap(View view, float f) {
        View viewFindViewById;
        int bottom = (this.mMode == 2 && (viewFindViewById = view.findViewById(R.id.aod_icons_container)) != null) ? viewFindViewById.getBottom() : view.getBottom();
        return (bottom + ((int) f)) - this.mGxzwIconY;
    }

    public void cancelAlarm() {
        AlarmTimeout alarmTimeout = this.mAlarmTimeout;
        if (alarmTimeout != null) {
            alarmTimeout.cancel();
        }
    }

    public Point getContainerTopAndHeight() {
        return new Point((int) this.mTargetView.getTranslationY(), (int) (this.mTargetView.getTranslationY() + this.mViewHeight));
    }

    public final int getTranslationY(Context context) {
        return AODSettings.getClockStyleInfo(context).getTranslationY(context);
    }

    public final void init() {
        boolean zIsShowFingerprintIconQuick = Utils.isShowFingerprintIconQuick(this.mHost);
        int initValue = getInitValue();
        this.mInitPositionValue = initValue;
        this.mInitAscSort = initValue > 19;
        if (zIsShowFingerprintIconQuick) {
            int i = sTranslationMapForFingerprintIcon.get(initValue);
            Log.i(TAG, "instance mInitPositionValue: " + this.mInitPositionValue + " newValue " + i);
            this.mInitPositionValue = i;
        }
        if (this.mInitAscSort) {
            return;
        }
        this.mInitPositionValue += 3;
    }

    public void initParams(Context context, int i) {
        this.mViewHeight = 0;
        this.mMode = i;
        this.mExtraTranslationY = getTranslationY(context);
        this.mScreenSize = new Point();
        this.mTopExtraMargin = Utils.dp2Px(40);
        if (this.mMode == 1) {
            this.mScreenSize.set(0, context.getResources().getDimensionPixelSize(R.dimen.smart_cover_y_region));
            this.mAodMovePeriod = SMART_COVOR_PERIOD;
            this.mAodMoveCurrent = SMART_COVOR_INITINAL_VALUE;
            this.mBottomExtraMargin = 0;
        } else {
            this.mScreenSize = DeviceInfo.getDisplaySize();
            int i2 = this.mMode;
            if (i2 == 0) {
                this.mAodMovePeriod = Settings.Secure.getInt(context.getContentResolver(), "aod_move_period", 54);
                this.mAodMoveCurrent = this.mInitPositionValue;
                this.mBottomExtraMargin = Utils.dp2Px(20);
            } else if (i2 == 3) {
                this.mAodMovePeriod = 16;
                this.mAodMoveCurrent = 8;
                this.mBottomExtraMargin = 0;
            } else {
                this.mAodMovePeriod = SUPER_WALLPAPER_PERIOD;
                this.mAodMoveCurrent = 8;
                this.mBottomExtraMargin = 0;
            }
        }
        int i3 = this.mMode;
        if (i3 != 2 && i3 != 3 && i3 != 4) {
            this.mCanMove = true;
            AlarmTimeout alarmTimeout = this.mAlarmTimeout;
            if (alarmTimeout != null) {
                alarmTimeout.cancel();
            }
        }
        if (this.mMode == 4) {
            this.mAodMoveCurrent = 0;
            this.mAscSort = true;
            this.mAodMovePeriod = 12;
        }
    }

    public final void notifyGxzwIconState(boolean z) {
        Log.i(TAG, "notifyGxzwIconState: visible " + z);
        if (this.mIsGxzwIconShow != z) {
            this.mIsGxzwIconShow = z;
            if (!z || this.mGxzwIconY > 0) {
                updateTranslateStep();
            } else {
                updateGxzwIconSize();
            }
        }
    }

    public void onDetachFromWindow() {
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public void onGxzwIconChanged(View view, boolean z) {
        boolean zIsShowFingerprintIconQuick = Utils.isShowFingerprintIconQuick(this.mHost);
        if (zIsShowFingerprintIconQuick != this.mIsGxzwIconShow) {
            String str = TAG;
            Log.i(str, "onGxzwIconChanged: visible " + zIsShowFingerprintIconQuick);
            notifyGxzwIconState(z ^ true);
            if (zIsShowFingerprintIconQuick) {
                int iCalculateGap = calculateGap(view, view.getTranslationY());
                Log.d(str, "onGxzwIconChanged: gap = " + iCalculateGap + ", mMode = " + this.mMode + ", isFingerprintPressed = " + this.mHost.isFingerprintPressed());
                if (iCalculateGap > 0) {
                    if (this.mMode == 2 && this.mHost.isFingerprintPressed()) {
                        return;
                    }
                    updatePosition(view, false, false, false);
                }
            }
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN_ORDERED)
    public void onSuperWallpaperPositionEvent(SuperWallpaperReceiver.PositionEvent positionEvent) {
        int i;
        int top;
        DozeHost dozeHost = this.mHost;
        if (dozeHost == null) {
            return;
        }
        if (!dozeHost.isDozing()) {
            Log.d(TAG, "it's not dozing, so don't consume the event");
            return;
        }
        if (AODSettings.getClockStyleInfo(this.mContext).usingNormalPositionStrategy()) {
            i = positionEvent.position;
            top = DeviceInfo.getRealSize().y / 2;
        } else {
            i = positionEvent.position;
            top = this.mTargetView.getTop();
        }
        int i2 = i - top;
        Log.i(TAG, "onSuperWallpaperPositionEvent: " + positionEvent.position + "  " + i2 + " " + DeviceInfo.getRealSize().y);
        if (this.mSupportChangeInitPosition && this.mMode == 2) {
            this.mAscSort = i2 >= 0;
            if (i2 > 0) {
                this.mAodMoveCurrent = 12;
            } else if (i2 < 0) {
                this.mAodMoveCurrent = 5;
            }
            if (this.mHost.isAodVisible()) {
                this.mTargetView.setVisibility(0);
            }
            this.mTargetView.setTranslationX(0.0f);
            this.mTargetView.setTranslationY(i2);
        }
        this.mHost.resetPendingVisible();
        EventBus.getDefault().removeStickyEvent(positionEvent);
    }

    public final void printDeviceInfo() {
        if (DEBUG) {
            Log.i(TAG, "printDeviceInfo:  mGxzwIconX " + this.mGxzwIconX + " mGxzwIconY " + this.mGxzwIconY + " mCutoutHeight " + this.mCutoutHeight);
        }
    }

    public void release() {
        AlarmTimeout alarmTimeout = this.mAlarmTimeout;
        if (alarmTimeout != null) {
            alarmTimeout.cancel();
            this.mAlarmTimeout.release();
        }
        if (this.mTargetView != null) {
            this.mTargetView = null;
        }
        ValueAnimator valueAnimator = this.mFreshAnimator;
        if (valueAnimator != null) {
            valueAnimator.removeAllUpdateListeners();
            this.mFreshAnimator.cancel();
        }
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public final void scheduleUpdateWallpaperPosition(final View view, final int i, final int i2) {
        Log.i(TAG, "scheduleUpdateWallpaperPosition: " + i + " " + i2);
        this.mHandler.removeMessages(713);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(713, i, i2, view));
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.aod.AODUpdatePositionController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                AODUpdatePositionController.$r8$lambda$RJF1G8UUrzwXQVoJCQINtkIgY8Q(view, i, i2);
            }
        }, 120L);
    }

    public final void sendWallpaperCommand(int i, int i2, int i3) {
        Intent intent = new Intent();
        if (this.mWallpaperManager.getWallpaperInfo() != null) {
            intent.setPackage(this.mWallpaperManager.getWallpaperInfo().getPackageName());
            intent.setAction("miui.miwallpaper.action.ACTION_AOD_OFFSET");
            intent.putExtra("action_aod_offset", new int[]{i, i2, i3});
            this.mContext.sendBroadcastAsUser(intent, UserHandleUtils.getUserHandle(UserHandleUtils.USER_CURRENT));
        }
    }

    public void setAodClock(IAodClock iAodClock) {
        this.mAodClock = iAodClock;
    }

    public void setCanMove(boolean z) {
        this.mCanMove = z;
    }

    public void setViewPosition(int i, int i2) {
        int i3 = i2 - i;
        if (this.mViewHeight == i3 && this.mViewTop == i) {
            return;
        }
        this.mViewTop = i;
        this.mViewHeight = i3;
        updateTranslateStep();
    }

    public final void setWallpaperPosition(View view, int i, int i2) {
        if (this.mMode != 2) {
            return;
        }
        int i3 = this.mTargetY;
        if (i3 == -1 || i3 != i2) {
            this.mTargetY = i2;
            try {
                if (this.mWallpaperManager == null) {
                    this.mWallpaperManager = (WallpaperManager) this.mContext.getSystemService("wallpaper");
                }
                if (this.mWallpaperManager != null) {
                    int i4 = (!AODSettings.getClockStyleInfo(this.mContext).usingNormalPositionStrategy() ? this.mViewTop : DeviceInfo.getRealSize().y / 2) + i2;
                    Log.i(TAG, "setWallpaperPosition:offsetY " + i4 + " targetY:" + i2);
                    sendWallpaperCommand(0, i4, 0);
                }
            } catch (Exception e) {
                Log.e(TAG, "setWallpaperPosition: " + e.getMessage());
            }
        }
    }

    public void updateCanMove() {
        if (Utils.aodGone(AODApplication.sInstance)) {
            return;
        }
        int i = this.mMode;
        if (i == 2 || i == 3 || i == 4) {
            this.mCanMove = false;
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.AODUpdatePositionController$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.mAlarmTimeout.schedule(AODUpdatePositionController.UPDATE_POSITION_DELAY, 1);
                }
            });
        }
    }

    public final void updateGxzwIconSize() {
        this.mGxzwIconX = 0;
        this.mGxzwIconY = 0;
        Pair gxzwIconPosition = Utils.getGxzwIconPosition();
        if (((Integer) gxzwIconPosition.second).intValue() < 0) {
            updateTranslateStep();
            return;
        }
        this.mGxzwIconX = ((Integer) gxzwIconPosition.first).intValue();
        this.mGxzwIconY = ((Integer) gxzwIconPosition.second).intValue();
        updateTranslateStep();
    }

    public void updatePosition(View view, boolean z, boolean z2, boolean z3) {
        int i;
        int i2;
        int i3;
        LinkageStyleClockView linkageStyleClockView;
        IAodClock iAodClock = this.mAodClock;
        if (iAodClock == null || !iAodClock.isUpdatePositionBySelf()) {
            this.mTargetView = view;
            if (this.mMode == 4) {
                updatePositionForFlip(z3);
                return;
            }
            if (!this.mIsDisplayUpdateModeOn) {
                Log.e(TAG, "updatePosition() blocking on setting value");
                return;
            }
            printDeviceInfo();
            if (this.mTargetView.getHeight() != this.mViewHeight) {
                View viewFindViewById = this.mTargetView.findViewById(R.id.clock);
                if (viewFindViewById instanceof SuperWallpaperClockView) {
                    Point topAndBottom = ((SuperWallpaperClockView) viewFindViewById).getTopAndBottom();
                    int i4 = topAndBottom.x;
                    this.mViewTop = i4;
                    this.mViewHeight = topAndBottom.y - i4;
                } else {
                    this.mViewTop = this.mTargetView.getTop();
                    this.mViewHeight = this.mTargetView.getHeight();
                }
                updateTranslateStep();
            }
            if (this.mMode == 3 && (linkageStyleClockView = (LinkageStyleClockView) this.mTargetView.findViewById(R.id.linkage_clock_container)) != null) {
                final ViewGroup.MarginLayoutParams viewMargin = linkageStyleClockView.getViewMargin((ViewGroup.MarginLayoutParams) this.mTargetView.getLayoutParams());
                this.mTargetView.post(new Runnable() { // from class: com.miui.aod.AODUpdatePositionController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AODUpdatePositionController.m153$r8$lambda$fx0CCHbIVf_4qV3fNRjZfAkQN8(this.f$0, viewMargin);
                    }
                });
                Point viewTopAndMargin = linkageStyleClockView.getViewTopAndMargin(this.mViewTop, this.mViewHeight, this.mTargetView.getTop(), this.mTargetView.getHeight());
                this.mViewHeight = viewTopAndMargin.y;
                this.mViewTop = viewTopAndMargin.x;
            }
            if (this.mMode == 1 && this.mViewHeight > 1300 && Utils.isUsingSuperWallpaperStyle(this.mContext)) {
                this.mViewTop = 0;
                this.mViewHeight = 563;
                updateTranslateStep();
            }
            if (this.mCanMove) {
                if (z) {
                    int i5 = this.mMode;
                    if (i5 == 0) {
                        if (this.mAscSort) {
                            int i6 = this.mAodMoveCurrent;
                            int i7 = i6 + 1;
                            this.mAodMoveCurrent = i7;
                            if (i7 >= this.mAodMovePeriod) {
                                this.mAodMoveCurrent = i6 - 2;
                                this.mAscSort = false;
                            }
                        } else {
                            int i8 = this.mAodMoveCurrent - 1;
                            this.mAodMoveCurrent = i8;
                            if (i8 <= -1) {
                                this.mAodMoveCurrent = 2;
                                this.mAscSort = true;
                            }
                        }
                    } else if (i5 != 2 && i5 != 3) {
                        int i9 = this.mAodMoveCurrent + 6;
                        this.mAodMoveCurrent = i9;
                        this.mAodMoveCurrent = i9 % this.mAodMovePeriod;
                    } else if (this.mAscSort) {
                        int i10 = this.mAodMoveCurrent + 1;
                        this.mAodMoveCurrent = i10;
                        int i11 = this.mAodMovePeriod;
                        if (i10 >= i11) {
                            this.mAodMoveCurrent = i11 - 3;
                            this.mAscSort = false;
                        }
                    } else {
                        int i12 = this.mAodMoveCurrent - 1;
                        this.mAodMoveCurrent = i12;
                        if (i12 <= -1) {
                            this.mAodMoveCurrent = 2;
                            this.mAscSort = true;
                        }
                    }
                }
                int i13 = this.mAodMoveCurrent / 2;
                int i14 = this.mMode;
                if (i14 == 2 || i14 == 3) {
                    i = i13;
                    i2 = 1;
                } else {
                    i2 = i13 % 3;
                    i = i13 / (i14 != 1 ? 3 : 6);
                }
                String str = TAG;
                Log.d(str, "(" + i2 + ", " + i + ") position: " + (i13 + 1) + " aodCurrent " + this.mAodMoveCurrent + " mViewTop:" + this.mViewTop + " " + ((FrameLayout.LayoutParams) this.mTargetView.getLayoutParams()).gravity);
                int i15 = this.mTranslationX * (i2 - 1);
                float f = ((this.mTranslationYStep * ((float) i)) - ((float) this.mViewTop)) + ((float) this.mTranslationY);
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    Folme.clean(this.mTargetView);
                }
                boolean z4 = z2 || (i3 = this.mMode) == 2 || i3 == 3;
                float translationY = this.mTargetView.getTranslationY();
                int iCalculateGap = calculateGap(this.mTargetView, translationY);
                int iCalculateGap2 = calculateGap(this.mTargetView, f);
                Log.d(str, "mGxzwIconY: " + this.mGxzwIconY + ", mIsGxzwIconShow: " + this.mIsGxzwIconShow + ", targetGap: " + iCalculateGap2 + ", realGap: " + iCalculateGap + ", trans: " + i15 + ac.b + f);
                if (!this.mIsGxzwIconShow || this.mGxzwIconY <= 0) {
                    updateTranslation(z4, i15, f);
                    return;
                }
                if (iCalculateGap2 < 0) {
                    updateTranslation(z4, i15, f);
                } else if (iCalculateGap > 0) {
                    this.mAscSort = false;
                    updateTranslation(z4, i15, translationY - iCalculateGap);
                } else {
                    this.mAodMoveCurrent--;
                    this.mAscSort = false;
                }
            }
        }
    }

    public void updatePositionForFlip(boolean z) {
        if (this.mCanMove || z) {
            int i = WindowUtils.INSTANCE.getWindowSize(this.mContext).y;
            int realViewHeight = ((FlipLinkageStyleClockView) this.mTargetView.findViewById(R.id.flip_linkage_clock_container)).getRealViewHeight();
            this.mViewHeight = realViewHeight;
            if (realViewHeight != 0 || z) {
                float f = (i - realViewHeight) / 5.0f;
                if (this.mAscSort) {
                    this.mAodMoveCurrent++;
                } else {
                    this.mAodMoveCurrent--;
                }
                int i2 = this.mAodMoveCurrent;
                if (i2 >= this.mAodMovePeriod) {
                    this.mAscSort = false;
                    this.mAodMoveCurrent = 9;
                } else if (i2 <= -1) {
                    this.mAscSort = true;
                    this.mAodMoveCurrent = 2;
                }
                int i3 = this.mAodMoveCurrent / 2;
                final float f2 = i3 * f;
                Log.d(TAG, "updatePositionForFlip [mAodMoveCurrent: " + this.mAodMoveCurrent + ",part: " + i3 + ",finalTransitionY: " + f2 + "]");
                if (f2 == this.mTargetView.getTranslationY()) {
                    return;
                }
                this.mainExecutor.execute(new Runnable() { // from class: com.miui.aod.AODUpdatePositionController$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        AODUpdatePositionController.$r8$lambda$gdU2fYN11dyltvV8WhPriR36mJY(this.f$0, f2);
                    }
                });
            }
        }
    }

    public final void updateTranslateStep() {
        boolean z = SettingsHolder.mNotchHide == 1;
        int i = this.mGxzwIconY;
        if (i <= 0 || !this.mIsGxzwIconShow || this.mMode == 1) {
            i = this.mScreenSize.y;
        }
        int iMax = (z && this.mIsGxzwIconShow) ? Math.max(0, (i - this.mBottomExtraMargin) - this.mCutoutHeight) : Math.max(0, i - this.mBottomExtraMargin);
        if (this.mMode == 0) {
            iMax = Math.min(iMax, (int) (this.mScreenSize.y * 0.85f));
        }
        int i2 = this.mCutoutHeight;
        int i3 = this.mExtraTranslationY + i2 + this.mTopExtraMargin;
        this.mTranslationY = i3;
        if (z || this.mMode == 2) {
            this.mTranslationY = i3 - i2;
        }
        int i4 = this.mTranslationY + this.mViewHeight;
        if (i4 < iMax) {
            this.mTranslationYStep = (iMax - i4) / (this.mMode != 1 ? 8.0f : 6.0f);
        }
        Log.i(TAG, "updateTranslateStep: mScreenSize.y " + this.mScreenSize.y + " bottom " + iMax + " mTranslationY " + this.mTranslationY + "  mTranslationYStep: " + this.mTranslationYStep);
    }

    public void updateTranslation(boolean z, final int i, final float f) {
        View view = this.mTargetView;
        if (view == null) {
            return;
        }
        if (z) {
            this.mainExecutor.execute(new Runnable() { // from class: com.miui.aod.AODUpdatePositionController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    AODUpdatePositionController aODUpdatePositionController = this.f$0;
                    aODUpdatePositionController.animateTranslation(aODUpdatePositionController.mTargetView, i, f);
                }
            });
        } else {
            view.setTranslationX(i);
            this.mTargetView.setTranslationY(f);
        }
    }
}
