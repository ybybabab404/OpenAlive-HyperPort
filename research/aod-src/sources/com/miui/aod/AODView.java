package com.miui.aod;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.gson.Gson;
import com.miui.aod.category.FlipLinkageClockCategoryInfo;
import com.miui.aod.category.LinkageClockCategoryInfo;
import com.miui.aod.category.SmartCoverStyleInfo;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.AodContainerView;
import com.miui.aod.flip.FlipLinkageStyleController;
import com.miui.aod.monitor.AodViewMonitor;
import com.miui.aod.notification.DiffDispatch;
import com.miui.aod.notification.NotificationController;
import com.miui.aod.notification.NotificationMetaData;
import com.miui.aod.notification.NotificationViewModel;
import com.miui.aod.other.step.GoalLiveData;
import com.miui.aod.other.step.StepBean;
import com.miui.aod.other.step.StepLiveData;
import com.miui.aod.other.step.StepUtils;
import com.miui.aod.theme.MamlViewContainer;
import com.miui.aod.util.BitmapUtils;
import com.miui.aod.util.DeviceInfo;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.CommonUtils;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.IAnimatable;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.IAodView;
import com.miui.aod.widget.NotificationAnimationView;
import com.miui.aod.widget.SuperWallpaperClockView;
import com.miui.aod.widget.notification.NotificationStyleManager;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.maml.FancyDrawable;
import com.miui.maml.MiAdvancedView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Consumer;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes.dex */
public class AODView extends FrameLayout implements Observer<StepBean>, IAodView {
    public static final String TAG = "AODView";
    public static boolean isMaxSizeCounted = false;
    public List iconsTranslationXList;
    public List iconsTranslationXListFlip;
    public boolean m24HourFormat;
    public final Runnable mAnimateInvisible;
    public final ArrayList mAnimatedViews;
    public final NotificationAnimationView.AnimationDrawer.AnimationStateListener mAnimationListener;
    public NotificationAnimationView mAnimationView;
    public IAodClock mAodClock;
    public boolean mAodClockVisible;
    public long mAodStartTime;
    public AODStyleController mAodStyleController;
    public int mCachedOrientation;
    public final Context mContext;
    public boolean mFirstAddNotificationWithFocusExist;
    public ImageView mFirstIcon;
    public boolean mFocusNotificationChanged;
    public ImageView mForthIcon;
    public DozeHost mHost;
    public boolean mInNotificationAnimProgress;
    public boolean mIsUpdateForOldNotification;
    public List mLastFocusNotifications;
    public long mLastNotificationAnimTime;
    public List mLastNotificationArray;
    public int mLastNotificationPosition;
    public int mLastNotificationSize;
    public List mLastNotifications;
    public Animator mMaskAnimator;
    public View mMaskForSuperWallpaper;
    public int mMode;
    public ImageView mMoreNotiIcon;
    public boolean mNotificationAnimFirstShow;
    public final List mNotificationArray;
    public final NotificationController.NotificationChangeListener mNotificationChangeListener;
    public float mNotificationIconAlpha;
    public final List mNotificationIconViews;
    public ViewGroup mNotificationIcons;
    public List mNotificationList;
    public NotificationMetaData mNotificationMetaData;
    public int mNotificationSize;
    public NotificationViewModel mNotificationViewModel;
    public final View.OnLayoutChangeListener mOnLayoutChangeListener;
    public boolean mOneIconMode;
    public AODUpdatePositionController mPositionController;
    public ImageView mSecondIcon;
    public StyleInfo mStyleInfo;
    public View mTableModeContainer;
    public ImageView mThirdIcon;
    public final Runnable mTransferTask;
    public WakeLock mWakeLock;
    public int maxSize;
    public Runnable monitorNotificationsRunnable;
    public final EaseManager.EaseStyle moveEaseStyle;
    public EaseManager.EaseStyle scaleEase;
    public EaseManager.EaseStyle showEase;

    /* JADX INFO: renamed from: com.miui.aod.AODView$3, reason: invalid class name */
    public class AnonymousClass3 implements NotificationController.NotificationChangeListener {
        public int notificationSize = 0;
        public int focusNotificationSize = 0;
        public final Set focusNotificationSet = new HashSet();

        /* JADX INFO: renamed from: $r8$lambda$cV6VAW9rON5d-nIat66qev7PmPI, reason: not valid java name */
        public static /* synthetic */ void m187$r8$lambda$cV6VAW9rON5dnIat66qev7PmPI(AnonymousClass3 anonymousClass3, WeakReference weakReference) {
            anonymousClass3.getClass();
            AodContainerView aodContainerView = (AodContainerView) weakReference.get();
            if (aodContainerView == null || !aodContainerView.isAttachedToWindow() || AODView.this.mHost == null || !AODView.this.mHost.isAodVisible() || AODView.this.mHost.isFullAod()) {
                return;
            }
            AodViewMonitor.sampleOnNotificationsChanged(aodContainerView);
        }

        public static /* synthetic */ void $r8$lambda$nJmkuicPjvYWnOSK6Vk065NTwnQ(AnonymousClass3 anonymousClass3, boolean z) {
            if (!z) {
                anonymousClass3.getClass();
            } else {
                if (AODView.this.mNotificationArray.isEmpty() && AODView.this.mLastFocusNotifications.isEmpty()) {
                    return;
                }
                AODView.this.scheduleNotificationAnimation();
            }
        }

        public AnonymousClass3() {
        }

        @Override // com.miui.aod.notification.NotificationController.NotificationChangeListener
        public void onUpdate(List list, final boolean z) {
            if (list == null) {
                list = new ArrayList();
                AODView.this.mLastNotifications = new ArrayList();
            } else {
                AODView.this.mLastNotifications = new ArrayList(list);
            }
            Log.v(AODView.TAG, "onUpdate: " + list.size() + ", needAnimate: " + z);
            if (this.focusNotificationSize > AODView.this.maxSize) {
                ArrayList arrayList = new ArrayList();
                if (AODView.this.mLastFocusNotifications != null) {
                    for (int i = AODView.this.maxSize; i < AODView.this.mLastFocusNotifications.size(); i++) {
                        NotificationController.NotificationData notificationData = (NotificationController.NotificationData) AODView.this.mLastFocusNotifications.get(i);
                        arrayList.add(new NotificationController.NotificationData(notificationData.mPackageName, notificationData.mTitle, notificationData.mIconDrawable, notificationData.mPostTime));
                    }
                }
                list.addAll(0, arrayList);
                Collections.sort(list);
            }
            this.notificationSize = list.size();
            AODView.this.mNotificationArray.clear();
            if (!list.isEmpty()) {
                HashSet hashSet = new HashSet();
                for (NotificationController.NotificationData notificationData2 : list) {
                    if (!notificationData2.isForegroundService() && !hashSet.contains(notificationData2.mPackageName) && hashSet.size() < 5) {
                        AODView.this.mNotificationArray.add(notificationData2);
                        hashSet.add(notificationData2.mPackageName);
                    }
                }
            }
            AODView.this.post(new Runnable() { // from class: com.miui.aod.AODView$3$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AODView.AnonymousClass3.$r8$lambda$nJmkuicPjvYWnOSK6Vk065NTwnQ(this.f$0, z);
                }
            });
            if (AODView.this.mStyleInfo.needSendNotificationCommand()) {
                try {
                    WallpaperManager wallpaperManager = (WallpaperManager) AODView.this.mContext.getSystemService("wallpaper");
                    Bundle bundle = new Bundle();
                    for (int i2 = 0; i2 < Math.min(AODView.this.mNotificationArray.size(), 4); i2++) {
                        bundle.putParcelable("icon_" + i2, BitmapUtils.getBitmapFromDrawable(((NotificationController.NotificationData) AODView.this.mNotificationArray.get(i2)).mIconDrawable));
                    }
                    wallpaperManager.sendWallpaperCommand(AODView.this.getWindowToken(), "action_notification", AODView.this.mNotificationArray.size(), 0, 0, bundle);
                } catch (Exception e) {
                    Log.e(AODView.TAG, "action_notification error :" + e.getMessage());
                }
            }
            AODView.this.updateNotificationList();
            try {
                if (AODView.this.mTableModeContainer instanceof AodContainerView) {
                    final WeakReference weakReference = new WeakReference((AodContainerView) AODView.this.mTableModeContainer);
                    if (AODView.this.monitorNotificationsRunnable != null) {
                        AODView aODView = AODView.this;
                        aODView.removeCallbacks(aODView.monitorNotificationsRunnable);
                    }
                    AODView aODView2 = AODView.this;
                    Runnable runnable = new Runnable() { // from class: com.miui.aod.AODView$3$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            AODView.AnonymousClass3.m187$r8$lambda$cV6VAW9rON5dnIat66qev7PmPI(this.f$0, weakReference);
                        }
                    };
                    aODView2.monitorNotificationsRunnable = runnable;
                    aODView2.postDelayed(runnable, 150L);
                }
            } catch (Exception unused) {
            }
            Log.v(AODView.TAG, "onUpdate: finished");
        }

        @Override // com.miui.aod.notification.NotificationController.NotificationChangeListener
        public void onUpdateFocusNotification(List list) {
            if (!AODView.isMaxSizeCounted) {
                AODView.this.mNotificationList = new ArrayList(list);
                return;
            }
            ArrayList arrayList = new ArrayList(list);
            this.focusNotificationSize = arrayList.size();
            this.focusNotificationSet.clear();
            Log.d(AODView.TAG, "onUpdateFocusNotification: maxSize = " + AODView.this.maxSize + "; size = " + arrayList.size());
            if (AODView.this.maxSize < this.focusNotificationSize) {
                for (int i = AODView.this.maxSize; i < this.focusNotificationSize; i++) {
                    this.focusNotificationSet.add(((NotificationController.NotificationData) arrayList.get(i)).mPackageName);
                }
            } else {
                arrayList.forEach(new Consumer() { // from class: com.miui.aod.AODView$3$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.focusNotificationSet.add(((NotificationController.NotificationData) obj).mPackageName);
                    }
                });
            }
            int clockGravity = AODView.this.mStyleInfo.getClockGravity();
            if ((AODView.this.mStyleInfo instanceof LinkageClockCategoryInfo) && AODView.this.mAodStyleController != null) {
                clockGravity = AODView.this.mAodStyleController.getClockGravity();
            }
            NotificationMetaData notificationMetaData = new NotificationMetaData(this.notificationSize, this.focusNotificationSize, AODView.this.maxSize);
            AODView.this.mNotificationMetaData = notificationMetaData;
            if (AODView.this.mStyleInfo.isNotificationSwitchOn() && AODView.this.mHost != null) {
                AODView.this.mWakeLock.setMaxAcquireTime(1000L);
                AODView.this.mWakeLock.acquire("UpdateFocusNotification");
                DiffDispatch.diff(arrayList, AODView.this.mHost, clockGravity, notificationMetaData);
                AODView.this.mFocusNotificationChanged = true;
            }
            AODView.this.mLastFocusNotifications = arrayList;
            if (this.focusNotificationSize > AODView.this.maxSize) {
                Log.d(AODView.TAG, "onUpdateFocusNotification: re-update the notification ...");
                AODView aODView = AODView.this;
                aODView.removeCallbacks(aODView.mTransferTask);
                AODView aODView2 = AODView.this;
                aODView2.postDelayed(aODView2.mTransferTask, 300L);
            }
            Log.v(AODView.TAG, "onUpdateFocusNotification: finished");
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$FAMxCFD5-L2epFoCEnxNKGtCxc8, reason: not valid java name */
    public static /* synthetic */ void m157$r8$lambda$FAMxCFD5L2epFoCEnxNKGtCxc8(AODView aODView) {
        aODView.getClass();
        Log.d(TAG, "makeNormalPanel mStyleInfo:" + new Gson().toJson(aODView.mStyleInfo) + "; UserHandleUtils.USER_CURRENT=" + UserHandleUtils.USER_CURRENT + "; Utils.isAodProcess()=" + Utils.isAodProcess());
    }

    public static /* synthetic */ void $r8$lambda$FHT64E9J4UVnFLxIQRP92yDcGw0(AODView aODView, ValueAnimator valueAnimator) {
        View view = aODView.mMaskForSuperWallpaper;
        if (view != null) {
            view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }
    }

    public static /* synthetic */ void $r8$lambda$JXDXor_XjdwYosYI6PzSUATon5g(AODView aODView) {
        DozeHost dozeHost = aODView.mHost;
        if (dozeHost != null) {
            dozeHost.setNotificationAnimate(false);
            aODView.mHost.fireAnimateState();
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$T6Uc3TYjCDrYnd7qjnl-HAMicNM, reason: not valid java name */
    public static /* synthetic */ void m158$r8$lambda$T6Uc3TYjCDrYnd7qjnlHAMicNM(AODView aODView) {
        if (aODView.mNotificationArray.isEmpty() && aODView.mLastFocusNotifications.isEmpty()) {
            return;
        }
        aODView.showAnimate(((NotificationController.NotificationData) (aODView.mNotificationArray.isEmpty() ? aODView.mLastFocusNotifications : aODView.mNotificationArray).get(0)).mPackageName);
    }

    /* JADX INFO: renamed from: $r8$lambda$UxABbKBA6w-ePAWs9qlUp_sQsSA, reason: not valid java name */
    public static /* synthetic */ void m159$r8$lambda$UxABbKBA6wePAWs9qlUp_sQsSA(AODView aODView, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        aODView.getClass();
        aODView.onAodContentLayoutChange(i2, i4, (i2 == i6 && i4 == i8 && i == i5 && i3 == i7) ? false : true);
    }

    public AODView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNotificationIconViews = new ArrayList(4);
        this.mInNotificationAnimProgress = false;
        this.mNotificationAnimFirstShow = true;
        this.mNotificationIconAlpha = 1.0f;
        this.mNotificationArray = new ArrayList();
        this.mLastNotificationArray = new ArrayList();
        this.mNotificationSize = 0;
        this.mLastNotificationSize = 0;
        this.mAnimatedViews = new ArrayList();
        this.mOneIconMode = false;
        this.iconsTranslationXList = new ArrayList();
        this.iconsTranslationXListFlip = new ArrayList();
        this.maxSize = 3;
        this.mFocusNotificationChanged = false;
        this.mFirstAddNotificationWithFocusExist = false;
        this.mLastNotificationPosition = -1;
        this.mIsUpdateForOldNotification = false;
        this.mNotificationMetaData = null;
        this.mCachedOrientation = 0;
        this.mTransferTask = new Runnable() { // from class: com.miui.aod.AODView.1
            @Override // java.lang.Runnable
            public void run() {
                AODView.this.mNotificationChangeListener.onUpdate(AODView.this.mLastNotifications, false);
            }
        };
        this.mMode = 0;
        this.mLastFocusNotifications = new ArrayList();
        this.mNotificationChangeListener = new AnonymousClass3();
        this.showEase = EaseManager.getStyle(-2, 0.9f, 0.3f);
        this.scaleEase = EaseManager.getStyle(-2, 0.75f, 0.3f);
        this.mAnimateInvisible = new Runnable() { // from class: com.miui.aod.AODView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AODView.$r8$lambda$JXDXor_XjdwYosYI6PzSUATon5g(this.f$0);
            }
        };
        this.mAnimationListener = new NotificationAnimationView.AnimationDrawer.AnimationStateListener() { // from class: com.miui.aod.AODView.6
            @Override // com.miui.aod.widget.NotificationAnimationView.AnimationDrawer.AnimationStateListener
            public void onAnimationEnd() {
                AODView.this.mAnimateInvisible.run();
            }
        };
        this.moveEaseStyle = EaseManager.getStyle(0, 750.0f, 1.0f, 0.35f);
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.miui.aod.AODView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                AODView.m159$r8$lambda$UxABbKBA6wePAWs9qlUp_sQsSA(this.f$0, view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        };
        this.mAodClockVisible = true;
        this.mContext = context;
        setForceDarkAllowed(false);
        this.mNotificationSize = 0;
        this.mAodStartTime = SystemClock.elapsedRealtime();
        this.mCachedOrientation = context.getResources().getConfiguration().orientation;
        this.mWakeLock = WakeLock.wrap(WakeLock.createDrawInner(context, TAG));
        setIconsTranslationXList();
    }

    @Override // com.miui.aod.widget.IAodView
    public void bindNotificationIconView(ImageView imageView, int i) {
        bindView(imageView, i);
    }

    public final void bindView(ImageView imageView, int i) {
        if (imageView == null) {
            return;
        }
        if (i < 0 || i >= this.mNotificationArray.size()) {
            imageView.setVisibility(4);
            return;
        }
        NotificationController.NotificationData notificationData = (NotificationController.NotificationData) this.mNotificationArray.get(i);
        Drawable drawable = notificationData.mIconDrawable;
        if (drawable == null) {
            imageView.setVisibility(4);
            return;
        }
        this.mWakeLock.setMaxAcquireTime(1000L);
        this.mWakeLock.acquire("NotificationIcon");
        imageView.setVisibility(0);
        if (this.mOneIconMode) {
            return;
        }
        onMamlResume(drawable);
        try {
            imageView.setImageDrawable(drawable);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "bindView: " + e);
            try {
                imageView.setImageDrawable(null);
                Bitmap bitmapFromDrawable = BitmapUtils.getBitmapFromDrawable(notificationData.mIconDrawable);
                if (bitmapFromDrawable != null) {
                    imageView.setImageBitmap(bitmapFromDrawable);
                }
            } catch (Exception e2) {
                Log.e(TAG, "bindView: " + e2);
                e2.printStackTrace();
            }
        } catch (Exception e3) {
            Log.e(TAG, "bindView: ", e3);
        }
    }

    public float calculateInitIconTranslationX(int i, float f, List list) {
        float dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.icon_margin_start) + f;
        if (isLeftGravity(this.mAodStyleController)) {
            dimensionPixelOffset = -dimensionPixelOffset;
        } else if (!isRightGravity(this.mAodStyleController)) {
            int i2 = this.mLastNotificationSize;
            if (i2 == 0) {
                int i3 = this.mNotificationSize;
                if (i3 > 0) {
                    dimensionPixelOffset = ((Float) (i3 > 4 ? list.get(4) : list.get(i3 - 1))).floatValue();
                } else {
                    dimensionPixelOffset = 0.0f;
                }
            } else {
                dimensionPixelOffset = (i2 < 5 ? ((Float) list.get(i2 - 1)).floatValue() : ((Float) list.get(4)).floatValue()) - dimensionPixelOffset;
            }
        }
        return dimensionPixelOffset * i;
    }

    public float calculateTargetIconTranslationX(int i, List list) {
        int i2;
        float fFloatValue = 0.0f;
        if (!isLeftGravity(this.mAodStyleController) && !isRightGravity(this.mAodStyleController) && (i2 = this.mNotificationSize) > 0) {
            fFloatValue = ((Float) (i2 > 4 ? list.get(4) : list.get(i2 - 1))).floatValue();
        }
        return fFloatValue * i;
    }

    public final void cancelIconTranslationAnimations() {
        for (ImageView imageView : this.mNotificationIconViews) {
            if (imageView != null) {
                Folme.useAt(imageView).state().cancel(ViewProperty.TRANSLATION_X);
            }
        }
    }

    public void clearClockPanelAnimation(boolean z) {
        if (this.mAnimatedViews.size() == 0) {
            collectAnimatedView(this, this.mAnimatedViews);
        }
        Iterator it = this.mAnimatedViews.iterator();
        while (it.hasNext()) {
            ((IAnimatable) it.next()).clearAnimationView();
        }
        if (z) {
            this.mAnimatedViews.clear();
        }
    }

    public final void collectAnimatedView(ViewGroup viewGroup, ArrayList arrayList) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof IAnimatable) {
                arrayList.add(childAt);
            }
            if (childAt instanceof ViewGroup) {
                collectAnimatedView((ViewGroup) childAt, arrayList);
            }
        }
    }

    public void doIconsUpdate(float f) {
        if (this.mAodStyleController.getClockGravity() == 17) {
            doIconsUpdateCenterGravity(f);
        } else if (isLeftGravity(this.mAodStyleController)) {
            doIconsUpdateLeftGravity();
        } else if (isRightGravity(this.mAodStyleController)) {
            doIconsUpdateRightGravity();
        }
    }

    public void doIconsUpdateAnimation(float f, float f2) {
        boolean z = false;
        if (this.mAodStyleController.getClockGravity() == 17 || isLeftGravity(this.mAodStyleController)) {
            bindView(this.mFirstIcon, 0);
            bindView(this.mSecondIcon, 1);
            bindView(this.mThirdIcon, 2);
            bindView(this.mForthIcon, 3);
        } else {
            bindView(this.mMoreNotiIcon, 0);
            bindView(this.mForthIcon, 1);
            bindView(this.mThirdIcon, 2);
            bindView(this.mSecondIcon, 3);
            z = true;
        }
        ImageView imageView = z ? this.mMoreNotiIcon : this.mFirstIcon;
        ImageView imageView2 = z ? this.mForthIcon : this.mSecondIcon;
        ImageView imageView3 = z ? this.mSecondIcon : this.mForthIcon;
        ImageView imageView4 = z ? this.mFirstIcon : this.mMoreNotiIcon;
        int i = this.mNotificationSize;
        if (i == 1) {
            doIconsUpdateAnimationForFirstIcon(f2, imageView);
            return;
        }
        if (i == 2) {
            doIconsUpdateAnimationForFirstIcon(f2, imageView);
            linkageIconTranslationAnimation(imageView2, f2, f);
            return;
        }
        if (i != 3) {
            updatePartIcons(f2, f, imageView, imageView2, imageView3, imageView4);
            return;
        }
        doIconsUpdateAnimationForFirstIcon(f2, imageView);
        linkageIconTranslationAnimation(imageView2, f2, f);
        if (!this.mIsUpdateForOldNotification) {
            linkageIconTranslationAnimation(this.mThirdIcon, f2, f);
        } else if (this.mLastNotificationPosition == 2) {
            linkageIconTranslationAnimation(this.mThirdIcon, f2, f);
        }
    }

    public void doIconsUpdateAnimationForFirstIcon(float f, View view) {
        view.setTranslationX(f);
        linkageIconAlphaAnimation(view);
    }

    public final void doIconsUpdateCenterGravity(float f) {
        for (int i = 0; i < this.mNotificationIconViews.size(); i++) {
            bindView((ImageView) this.mNotificationIconViews.get(i), i);
        }
        int iMin = Math.min(this.mNotificationSize, this.mNotificationIconViews.size());
        for (int i2 = 0; i2 < iMin; i2++) {
            safelySetIconTranslationX((ImageView) this.mNotificationIconViews.get(i2), f);
        }
        ImageView imageView = this.mMoreNotiIcon;
        if (imageView != null) {
            imageView.setVisibility(this.mNotificationArray.size() <= 4 ? 4 : 0);
        }
    }

    public final void doIconsUpdateLeftGravity() {
        bindView(this.mFirstIcon, 0);
        bindView(this.mSecondIcon, 1);
        bindView(this.mThirdIcon, 2);
        bindView(this.mForthIcon, 3);
        this.mMoreNotiIcon.setVisibility(this.mNotificationArray.size() <= 4 ? 4 : 0);
    }

    public final void doIconsUpdateRightGravity() {
        bindView(this.mMoreNotiIcon, 0);
        bindView(this.mForthIcon, 1);
        bindView(this.mThirdIcon, 2);
        bindView(this.mSecondIcon, 3);
        this.mFirstIcon.setVisibility(this.mNotificationArray.size() <= 4 ? 4 : 0);
        this.mFirstIcon.setImageResource(R.drawable.ic_moreicon);
    }

    public final void fallbackDefaultIcon() {
        ImageView imageView = this.mFirstIcon;
        if (imageView != null) {
            imageView.setVisibility(4);
        }
        ImageView imageView2 = this.mSecondIcon;
        if (imageView2 != null) {
            imageView2.setVisibility(4);
        }
        ImageView imageView3 = this.mThirdIcon;
        if (imageView3 != null) {
            imageView3.setVisibility(4);
        }
        ImageView imageView4 = this.mForthIcon;
        if (imageView4 != null) {
            imageView4.setVisibility(4);
        }
        ImageView imageView5 = this.mMoreNotiIcon;
        if (imageView5 != null) {
            imageView5.setVisibility(4);
        }
    }

    public final int getBottomWithFocusNotificationShowed(boolean z) {
        if (this.mNotificationMetaData == null) {
            this.mNotificationMetaData = new NotificationMetaData(0, 0, 0);
        }
        int notificationSize = this.mNotificationMetaData.getNotificationSize();
        int focusNotificationSize = this.mNotificationMetaData.getFocusNotificationSize();
        int maxSize = this.mNotificationMetaData.getMaxSize();
        String str = TAG;
        Log.d(str, "getBottomWithFocusNotificationShowed: notificationSize=" + notificationSize + ", focusNotificationSize=" + focusNotificationSize + ", maxSize=" + maxSize + ", firstAddNotificationWithFocusExist=" + z);
        int dimensionPixelSize = (notificationSize > 0 || focusNotificationSize > maxSize || z) ? (this.mContext.getResources().getDimensionPixelSize(R.dimen.focus_notification_height) + this.mContext.getResources().getDimensionPixelSize(R.dimen.focus_notification_gap)) * Math.min(focusNotificationSize, maxSize) : 0;
        View viewFindLowestVisibleLeafView = Utils.findLowestVisibleLeafView((ViewGroup) this.mTableModeContainer);
        if (viewFindLowestVisibleLeafView == null) {
            Log.w(str, "getBottomWithFocusNotificationShowed: lowestVisibleView is null, return 0");
            return 0;
        }
        int[] iArr = new int[2];
        viewFindLowestVisibleLeafView.getLocationInWindow(iArr);
        int height = dimensionPixelSize + iArr[1] + viewFindLowestVisibleLeafView.getHeight();
        Log.d(str, "getBottomWithFocusNotificationShowed: realBottom=" + height);
        return height;
    }

    public Map<String, Object> getContainerTopAndHeight() {
        HashMap map = new HashMap();
        if (this.mTableModeContainer != null && this.mPositionController != null && this.mHost != null) {
            FlipLinkageStyleController flipLinkageStyleController = FlipLinkageStyleController.INSTANCE;
            if (flipLinkageStyleController.isUsingFlip(this.mContext) && flipLinkageStyleController.isFlipped()) {
                Point containerTopAndHeight = this.mPositionController.getContainerTopAndHeight();
                map.put("top", Integer.valueOf(containerTopAndHeight.x));
                map.put("bottom", Integer.valueOf(containerTopAndHeight.y));
                return map;
            }
            map.put("top", Integer.valueOf(((int) this.mTableModeContainer.getTranslationY()) - ((int) this.mHost.getClockTranslation())));
        }
        return map;
    }

    public DozeHost getHost() {
        return this.mHost;
    }

    @Override // com.miui.aod.widget.IAodView
    @NonNull
    public List<NotificationController.NotificationData> getNotificationDatas() {
        return this.mNotificationArray;
    }

    public AODUpdatePositionController getPositionController() {
        return this.mPositionController;
    }

    public StyleInfo getStyleInfo() {
        return this.mStyleInfo;
    }

    public final void handleGeneralUpdateIcons() {
        List list;
        float dimensionPixelOffset;
        FlipLinkageStyleController flipLinkageStyleController = FlipLinkageStyleController.INSTANCE;
        int i = 1;
        boolean z = flipLinkageStyleController.isUsingFlip(this.mContext) && flipLinkageStyleController.isFlipped();
        int i2 = DeviceUtil.INSTANCE.isRtl(getContext()) ? -1 : 1;
        if (z) {
            dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.icon_size_flip);
            list = this.iconsTranslationXListFlip;
        } else {
            int i3 = i2;
            list = this.iconsTranslationXList;
            dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.icon_size);
            i = i3;
        }
        float fCalculateTargetIconTranslationX = calculateTargetIconTranslationX(i, list);
        float fCalculateInitIconTranslationX = calculateInitIconTranslationX(i, dimensionPixelOffset, list);
        cancelIconTranslationAnimations();
        if (isNeedLinkageIconAnimation()) {
            doIconsUpdateAnimation(fCalculateInitIconTranslationX, fCalculateTargetIconTranslationX);
        } else {
            doIconsUpdate(fCalculateTargetIconTranslationX);
        }
    }

    public void handleUpdateIcons(boolean z) {
        if (Looper.getMainLooper().isCurrentThread()) {
            if (this.mStyleInfo == null || !needShowNotification()) {
                Log.d(TAG, "no need show icons");
            } else {
                updateIcons(z);
            }
        }
    }

    public final void handleUpdateTime() {
        if (this.mStyleInfo.needSendUpdateTimeCommand()) {
            try {
                ((WallpaperManager) this.mContext.getSystemService("wallpaper")).sendWallpaperCommand(getWindowToken(), "action_minute_update", 0, 0, 0, null);
            } catch (Exception e) {
                Log.e(TAG, "action_minute_update error :" + e.getMessage());
            }
        }
        this.mAodStyleController.handleUpdateTime(this.m24HourFormat);
        Log.v(TAG, "AOD Time Updated ...");
    }

    public void handleUpdateView(boolean z, boolean z2, boolean z3) {
        if (z) {
            this.mPositionController.updatePosition(this.mTableModeContainer, true, z2, false);
        }
        handleUpdateTime();
        if (z3) {
            handleUpdateIcons(false);
        }
    }

    public void initMaskView() {
        ViewStub viewStub;
        if (this.mMaskForSuperWallpaper == null && (viewStub = (ViewStub) findViewById(R.id.mask_super_wallpaper)) != null) {
            this.mMaskForSuperWallpaper = viewStub.inflate();
        }
        View view = this.mMaskForSuperWallpaper;
        if (view != null) {
            view.setAlpha(1.0f);
        }
    }

    public boolean isAodShown() {
        View view = this.mTableModeContainer;
        return view != null && view.getVisibility() == 0 && this.mTableModeContainer.getAlpha() == 1.0f;
    }

    public boolean isLeftGravity(AODStyleController aODStyleController) {
        if (aODStyleController == null) {
            return false;
        }
        int clockGravity = aODStyleController.getClockGravity() & 7;
        return clockGravity == 8388611 || clockGravity == 3;
    }

    public boolean isNeedLinkageIconAnimation() {
        if (isAodShown()) {
            return true;
        }
        Log.d(TAG, "AODView is not shown, no animation");
        return false;
    }

    public boolean isNotificationChanged(boolean z) {
        NotificationMetaData notificationMetaData;
        int i = 0;
        this.mIsUpdateForOldNotification = false;
        this.mLastNotificationPosition = -1;
        if (this.mNotificationArray.isEmpty() && this.mLastNotificationArray.isEmpty()) {
            return false;
        }
        if (z) {
            return true;
        }
        if (this.mNotificationArray.isEmpty() || this.mLastNotificationArray.isEmpty()) {
            if (this.mLastNotificationArray.isEmpty() && this.mNotificationArray.size() == 1 && (notificationMetaData = this.mNotificationMetaData) != null) {
                int focusNotificationSize = notificationMetaData.getFocusNotificationSize();
                int maxSize = this.mNotificationMetaData.getMaxSize();
                int i2 = this.mNotificationSize;
                if (this.mNotificationMetaData.getFocusNotificationSize() > 0 && isAodShown()) {
                    boolean z2 = focusNotificationSize <= maxSize && i2 > 0;
                    if (focusNotificationSize > maxSize && i2 <= 0) {
                        i = 1;
                    }
                    if (z2 || i != 0) {
                        Log.d(TAG, "updateIcons: set mFirstAddNotificationWithFocusExist true");
                        this.mFirstAddNotificationWithFocusExist = true;
                    }
                }
            }
            return true;
        }
        if (this.mNotificationArray.size() != this.mLastNotificationArray.size()) {
            return true;
        }
        String str = ((NotificationController.NotificationData) this.mNotificationArray.get(0)).mPackageName;
        if (str.equals(((NotificationController.NotificationData) this.mLastNotificationArray.get(0)).mPackageName)) {
            for (int i3 = 0; i3 < this.mNotificationArray.size(); i3++) {
                NotificationController.NotificationData notificationData = (NotificationController.NotificationData) this.mNotificationArray.get(i3);
                NotificationController.NotificationData notificationData2 = (NotificationController.NotificationData) this.mLastNotificationArray.get(i3);
                if (!TextUtils.equals(notificationData.mPackageName, notificationData2.mPackageName) || notificationData.mIsXSpace != notificationData2.mIsXSpace) {
                    return true;
                }
            }
            return false;
        }
        while (i < this.mLastNotificationArray.size()) {
            if (str.equals(((NotificationController.NotificationData) this.mLastNotificationArray.get(i)).mPackageName) && i < 5) {
                this.mLastNotificationPosition = i;
                this.mIsUpdateForOldNotification = true;
                Log.d(TAG, "isNotificationChanged, mLastNotificationPosition: " + this.mLastNotificationPosition);
                break;
            }
            i++;
        }
        return true;
    }

    public boolean isRightGravity(AODStyleController aODStyleController) {
        if (aODStyleController == null) {
            return false;
        }
        int clockGravity = aODStyleController.getClockGravity() & 7;
        return clockGravity == 8388613 || clockGravity == 5;
    }

    public final void linkageIconAlphaAnimation(final View view) {
        if (view == null) {
            return;
        }
        AnimConfig delay = new AnimConfig().setEase(this.showEase).addListeners(new TransitionListener() { // from class: com.miui.aod.AODView.4
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                AODView.this.linkageIconScaleAnimation(view);
            }
        }).setDelay(50L);
        IStateStyle iStateStyleState = Folme.useAt(view).state();
        ViewProperty viewProperty = ViewProperty.ALPHA;
        iStateStyleState.setTo(viewProperty, Float.valueOf(0.0f)).to(viewProperty, Float.valueOf(1.0f), delay);
    }

    public final void linkageIconScaleAnimation(View view) {
        if (view == null) {
            return;
        }
        AnimConfig ease = new AnimConfig().setEase(this.scaleEase);
        IStateStyle iStateStyleState = Folme.useAt(view).state();
        ViewProperty viewProperty = ViewProperty.SCALE_X;
        Float fValueOf = Float.valueOf(0.8f);
        ViewProperty viewProperty2 = ViewProperty.SCALE_Y;
        iStateStyleState.setTo(viewProperty, fValueOf, viewProperty2, Float.valueOf(0.8f)).to(viewProperty, Float.valueOf(1.0f), viewProperty2, Float.valueOf(1.0f), ease);
    }

    public final void linkageIconTranslationAnimation(final View view, final float f, float f2) {
        if (view == null) {
            return;
        }
        AnimConfig animConfigAddListeners = new AnimConfig().setEase(this.showEase).addListeners(new TransitionListener() { // from class: com.miui.aod.AODView.5
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                view.setTranslationX(f);
            }
        });
        IStateStyle iStateStyleState = Folme.useAt(view).state();
        ViewProperty viewProperty = ViewProperty.TRANSLATION_X;
        iStateStyleState.setTo(viewProperty, Float.valueOf(f2)).to(viewProperty, Float.valueOf(f), animConfigAddListeners);
        Log.d(TAG, "linkageIconTranslationAnimation, initial: " + f2 + ", target: " + f + ", view: " + view);
    }

    public final void makeNormalPanel() {
        this.mAodStyleController = new AODStyleController(3);
        this.mStyleInfo = AODStyleController.getStyleInfo(getContext());
        BackgroundThread.getHandler().post(new Runnable() { // from class: com.miui.aod.AODView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AODView.m157$r8$lambda$FAMxCFD5L2epFoCEnxNKGtCxc8(this.f$0);
            }
        });
        this.mOneIconMode = AODSettings.notificationOneIconMode(this.mStyleInfo);
        if (this.mStyleInfo instanceof SmartCoverStyleInfo) {
            this.mMode = 1;
        } else if (Utils.isUsingSuperWallpaperStyle(this.mContext)) {
            this.mMode = 2;
        } else if (Utils.isUsingLinkageStyle(this.mContext)) {
            this.mMode = 3;
        }
        if (this.mStyleInfo instanceof FlipLinkageClockCategoryInfo) {
            this.mMode = 4;
        }
        this.mPositionController.initParams(getContext(), this.mMode);
        this.m24HourFormat = CommonUtils.is24HourFormat(this.mContext, UserHandleUtils.USER_CURRENT);
        this.mTableModeContainer = findViewById(R.id.clock_container);
        if (!Utils.isUsingSuperWallpaperStyle(getContext()) || this.mMode == 1) {
            if (this.mMode != 4) {
                this.mTableModeContainer.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
            }
            DozeHost dozeHost = this.mHost;
            if (dozeHost != null) {
                dozeHost.disableShowWallpaper();
            }
            setBackgroundColor(-16777216);
        }
        IAodClock iAodClockInflateView = this.mAodStyleController.inflateView(this.mTableModeContainer, this.mStyleInfo);
        this.mAodClock = iAodClockInflateView;
        this.mPositionController.setAodClock(iAodClockInflateView);
        if (needShowNotification()) {
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.icons);
            this.mNotificationIcons = viewGroup;
            if (viewGroup != null) {
                this.mNotificationIconAlpha = viewGroup.getAlpha();
            }
            this.mFirstIcon = (ImageView) findViewById(R.id.first);
            this.mSecondIcon = (ImageView) findViewById(R.id.second);
            this.mThirdIcon = (ImageView) findViewById(R.id.third);
            this.mForthIcon = (ImageView) findViewById(R.id.forth);
            this.mMoreNotiIcon = (ImageView) findViewById(R.id.more_notification_icon);
            setupNotificationIconCollection();
        }
        this.mAnimationView = (NotificationAnimationView) findViewById(R.id.notification_animation_view);
        DozeHost dozeHost2 = this.mHost;
        if (dozeHost2 != null) {
            dozeHost2.printMemInfo();
        }
        updateClockViewVisible();
        Log.w(TAG, "isLowMemDevice: " + DeviceInfo.isLowMemDevice());
    }

    public final boolean needShowNotification() {
        return this.mStyleInfo.isNotificationSwitchOn();
    }

    public final void onAodContentLayoutChange(int i, int i2, boolean z) {
        DozeHost dozeHost;
        String str = TAG;
        Log.d(str, "onAodContentLayoutChange: top=" + i + ", bottom=" + i2 + ", changed=" + z + ", mFocusNotificationChanged=" + this.mFocusNotificationChanged + ", mFirstAddNotificationWithFocusExist=" + this.mFirstAddNotificationWithFocusExist);
        if (AODSettings.needFullAod() || !(this.mFocusNotificationChanged || this.mFirstAddNotificationWithFocusExist)) {
            if (z) {
                if (i2 - i > 0) {
                    this.mPositionController.setViewPosition(i, i2);
                }
                this.mPositionController.updatePosition(this.mTableModeContainer, false, false, false);
                return;
            }
            return;
        }
        if (Utils.isGxzwSensor() && (dozeHost = this.mHost) != null && Utils.isUnlockWithFingerprintPossible(this.mContext, dozeHost)) {
            float translationY = this.mTableModeContainer.getTranslationY();
            int iIntValue = ((Integer) Utils.getGxzwIconPosition().second).intValue();
            int i3 = (((int) translationY) + i2) - iIntValue;
            if (!z) {
                i2 = getBottomWithFocusNotificationShowed(this.mFirstAddNotificationWithFocusExist);
                i3 = i2 - iIntValue;
            }
            Log.d(str, "hasUpdatePositionForFocusNotification, gap: " + i3 + ", translationY: " + translationY + ", bottom: " + i2 + ", gxzwIconY=" + iIntValue);
            if (i3 > 0) {
                AnimConfig animConfigAddListeners = new AnimConfig().setEase(this.moveEaseStyle).addListeners(new TransitionListener() { // from class: com.miui.aod.AODView.7
                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object obj) {
                        if (AODView.this.mHost != null) {
                            AODView.this.mHost.updatePosition();
                        }
                    }
                });
                this.mHost.getService().getRequestDoze().onRequestClockHide();
                this.mHost.setClockViewVisible(true);
                Folme.useAt(this.mTableModeContainer).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(translationY - (this.mContext.getResources().getDimensionPixelSize(R.dimen.focus_notification_gap) + i3)), animConfigAddListeners);
            }
        }
        this.mFocusNotificationChanged = false;
        this.mFirstAddNotificationWithFocusExist = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
        EventBus.getDefault().register(this.mPositionController);
        post(new Runnable() { // from class: com.miui.aod.AODView.2
            @Override // java.lang.Runnable
            public void run() {
                AODView aODView = AODView.this;
                aODView.maxSize = DiffDispatch.getMaxCount(aODView, aODView.mStyleInfo);
                AODView.isMaxSizeCounted = true;
                if (AODView.this.mNotificationList != null) {
                    AODView.this.mNotificationChangeListener.onUpdateFocusNotification(AODView.this.mNotificationList);
                    AODView.this.mNotificationList = null;
                }
            }
        });
        NotificationController.getInstance(AODApplication.sysuiContext).initUpdate();
        NotificationViewModel notificationViewModel = (NotificationViewModel) new ViewModelProvider(DozeLifecycleOwner.getInstance()).get(NotificationViewModel.class);
        this.mNotificationViewModel = notificationViewModel;
        notificationViewModel.addCallback(this.mNotificationChangeListener);
        if (StepUtils.supportStepComponent() && this.mStyleInfo.isStepComponentSwitchOn()) {
            GoalLiveData.getInstance().registerObserver();
            StepLiveData.getInstance().observe(DozeLifecycleOwner.getInstance(), this);
            StepLiveData.getInstance().registerObserver();
            StepLiveData.getInstance().startPolling("StepLiveData", 600000L);
        }
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(StepBean stepBean) {
        View view = this.mTableModeContainer;
        if (view instanceof AodContainerView) {
            ((AodContainerView) view).updateStep(stepBean);
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = this.mCachedOrientation;
        int i2 = configuration.orientation;
        boolean z = i != i2;
        this.mCachedOrientation = i2;
        if (z) {
            updateClockContainerMarginTop();
            updatePositionParams(true);
        }
        DozeHost dozeHost = this.mHost;
        if (dozeHost != null) {
            dozeHost.onConfigurationChanged(configuration);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mNotificationArray.clear();
        this.mLastFocusNotifications.clear();
        this.mLastNotificationArray.clear();
        this.mLastNotificationSize = 0;
        this.mNotificationMetaData = null;
        this.mFocusNotificationChanged = false;
        this.mFirstAddNotificationWithFocusExist = false;
        this.mLastNotificationAnimTime = 0L;
        this.mAodClockVisible = true;
        fallbackDefaultIcon();
        clearClockPanelAnimation(true);
        resetMaskView();
        View viewFindViewById = findViewById(R.id.aod_icons_container);
        if (viewFindViewById != null) {
            viewFindViewById.setAlpha(1.0f);
        }
        isMaxSizeCounted = false;
        EventBus.getDefault().unregister(this);
        DozeHost dozeHost = this.mHost;
        if (dozeHost != null) {
            dozeHost.setNotificationAnimate(false);
        }
        this.mNotificationViewModel.removeCallback(this.mNotificationChangeListener);
        Runnable runnable = this.monitorNotificationsRunnable;
        if (runnable != null) {
            removeCallbacks(runnable);
            this.monitorNotificationsRunnable = null;
        }
        removeCallbacks(this.mTransferTask);
        NotificationAnimationView notificationAnimationView = this.mAnimationView;
        if (notificationAnimationView != null) {
            notificationAnimationView.stopAnimation();
        }
        if (this.mPositionController != null) {
            EventBus.getDefault().unregister(this.mPositionController);
            this.mPositionController.onDetachFromWindow();
        }
        StepLiveData.getInstance().removeObserver(this);
        StepLiveData.getInstance().unRegisterObserver();
        GoalLiveData.getInstance().unRegisterObserver();
    }

    public void onGxzwIconChanged(boolean z) {
        AODUpdatePositionController aODUpdatePositionController = this.mPositionController;
        if (aODUpdatePositionController != null) {
            aODUpdatePositionController.onGxzwIconChanged(this.mTableModeContainer, z);
        }
    }

    public void onInflateComplete() {
        makeNormalPanel();
        handleUpdateView(false, false, true);
        setClockViewVisible(this.mAodClockVisible);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLayoutChangeEvent(SuperWallpaperClockView.LayoutChangeEvent layoutChangeEvent) {
        Log.i(TAG, "onLayoutChangeEvent: " + layoutChangeEvent.mTop + "  " + layoutChangeEvent.mBottom);
        onAodContentLayoutChange(layoutChangeEvent.mTop, layoutChangeEvent.mBottom, true);
    }

    public void onMamlResume(Object obj) {
        if (obj instanceof MiAdvancedView) {
            ((MiAdvancedView) obj).onResume();
        } else if (obj instanceof FancyDrawable) {
            ((FancyDrawable) obj).onResume();
        }
    }

    public void onReuseAodView() {
        String string;
        Log.i(TAG, "reuse aod view");
        if (getResources().getConfiguration().orientation != this.mCachedOrientation) {
            post(new Runnable() { // from class: com.miui.aod.AODView$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    AODView aODView = this.f$0;
                    aODView.dispatchConfigurationChanged(aODView.getResources().getConfiguration());
                }
            });
        }
        updatePositionParams(false);
        this.m24HourFormat = CommonUtils.is24HourFormat(this.mContext, UserHandleUtils.USER_CURRENT);
        this.mAodStartTime = SystemClock.elapsedRealtime();
        this.mAodClock.setTimeZone(TimeZone.getDefault());
        if (AODSettings.isDualClock(getContext()) && (string = MiuiSettingsUtils.System.getString(getContext().getContentResolver(), "resident_timezone")) != null) {
            this.mAodClock.setTimeZone2(TimeZone.getTimeZone(string));
        }
        this.mAodClock.update(this.mStyleInfo, 3);
    }

    public void onUpdatePositionTimer() {
        IAodClock iAodClock = this.mAodClock;
        if (iAodClock != null) {
            iAodClock.updateLayoutPosition(true, true);
        }
    }

    public void preDestroyForChange() {
        this.mTableModeContainer.removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        stopNotificationAnim();
        IAodClock iAodClock = this.mAodClock;
        if (iAodClock != null) {
            iAodClock.preDestroyForChange();
        }
    }

    public void releaseResourcesIfNeeded() {
        Animator animator = this.mMaskAnimator;
        if (animator != null) {
            animator.cancel();
            this.mMaskAnimator = null;
        }
        MamlViewContainer mamlViewContainer = (MamlViewContainer) findViewById(R.id.maml_view_container);
        if (mamlViewContainer != null) {
            mamlViewContainer.release();
        }
        AODUpdatePositionController aODUpdatePositionController = this.mPositionController;
        if (aODUpdatePositionController != null) {
            aODUpdatePositionController.release();
            EventBus.getDefault().unregister(this.mPositionController);
            this.mPositionController.setCanMove(false);
        }
        AODStyleController aODStyleController = this.mAodStyleController;
        if (aODStyleController != null) {
            aODStyleController.releaseResources();
        }
    }

    public final void resetMaskView() {
        Animator animator = this.mMaskAnimator;
        if (animator != null) {
            animator.cancel();
            this.mMaskAnimator = null;
        }
        View view = this.mMaskForSuperWallpaper;
        if (view != null) {
            view.setAlpha(0.0f);
        }
    }

    public final void safelySetIconTranslationX(ImageView imageView, float f) {
        if (imageView != null) {
            imageView.setTranslationX(f);
        } else {
            Log.w(TAG, "icon is null");
        }
    }

    public final void scheduleNotificationAnimation() {
        if (SystemClock.elapsedRealtime() - this.mAodStartTime < 800) {
            Log.i(TAG, "scheduleNotificationAnimation: notification update when AOD start");
            return;
        }
        if ((this.mNotificationArray.isEmpty() && this.mLastFocusNotifications.isEmpty()) || !Utils.isAodAnimateEnable(this.mContext) || Utils.isPowerSaveMode(this.mContext)) {
            return;
        }
        if (SystemClock.elapsedRealtime() - this.mLastNotificationAnimTime < 9000) {
            Log.i(TAG, "scheduleNotificationAnimation: notification with same package name in short time");
            return;
        }
        this.mLastNotificationAnimTime = SystemClock.elapsedRealtime();
        setVisibility(0);
        post(new Runnable() { // from class: com.miui.aod.AODView$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                AODView.m158$r8$lambda$T6Uc3TYjCDrYnd7qjnlHAMicNM(this.f$0);
            }
        });
    }

    public void setClockViewVisible(boolean z) {
        if (this.mAodClockVisible != z) {
            this.mAodStyleController.setClockViewVisible(z);
            this.mAodClockVisible = z;
        }
    }

    public void setDozeHost(DozeHost dozeHost) {
        DozeHost dozeHost2 = this.mHost;
        if (dozeHost2 != null && dozeHost2 != dozeHost) {
            dozeHost2.cancelAndRunPendingRemoveAodView();
        }
        this.mHost = dozeHost;
        this.mPositionController = new AODUpdatePositionController(this.mContext, this.mHost);
    }

    public final void setIconsTranslationXList() {
        float dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.icon_margin_start);
        float dimensionPixelOffset2 = getResources().getDimensionPixelOffset(R.dimen.icon_size);
        float dimensionPixelOffset3 = getResources().getDimensionPixelOffset(R.dimen.icon_size_flip);
        updateIconsTranslationXList(dimensionPixelOffset, dimensionPixelOffset2);
        updateIconsTranslationXListFlip(dimensionPixelOffset, dimensionPixelOffset3);
    }

    public void setNotificationAnimFirstShow(boolean z) {
        this.mNotificationAnimFirstShow = z;
    }

    public void setSunImage(int i) {
        this.mAodStyleController.setSunImage(i, this);
    }

    public final void setupNotificationIconCollection() {
        this.mNotificationIconViews.clear();
        this.mNotificationIconViews.add(this.mFirstIcon);
        this.mNotificationIconViews.add(this.mSecondIcon);
        this.mNotificationIconViews.add(this.mThirdIcon);
        this.mNotificationIconViews.add(this.mForthIcon);
    }

    public final void showAnimate(String str) {
        NotificationAnimationView notificationAnimationView;
        DozeHost dozeHost;
        Log.v(TAG, "showAnimate pkg:" + str);
        if ((this.mStyleInfo instanceof FlipLinkageClockCategoryInfo) || (notificationAnimationView = this.mAnimationView) == null) {
            return;
        }
        notificationAnimationView.stopAnimation();
        NotificationAnimationView.AnimationDrawer animationDrawerCreateAnimationDrawer = NotificationStyleManager.createAnimationDrawer(getContext(), NotificationStyleManager.getSelectedStyleName(getContext()));
        if (animationDrawerCreateAnimationDrawer == null || (dozeHost = this.mHost) == null) {
            return;
        }
        dozeHost.setNotificationAnimate(true);
        this.mHost.fireAnimateState();
        animationDrawerCreateAnimationDrawer.setAnimationListener(this.mAnimationListener);
        this.mAnimationView.setAnimationDrawer(animationDrawerCreateAnimationDrawer);
        this.mAnimationView.startAnimation();
    }

    public void startClockPanelAnimation(AnimatorListenerAdapter animatorListenerAdapter) {
        if (this.mAnimatedViews.size() == 0) {
            collectAnimatedView(this, this.mAnimatedViews);
        }
        Iterator it = this.mAnimatedViews.iterator();
        while (it.hasNext()) {
            ((IAnimatable) it.next()).startAnimation(animatorListenerAdapter);
        }
    }

    public void startMaskAnimation() {
        Log.w(TAG, "startMaskAnimation: ");
        if (this.mMaskForSuperWallpaper != null) {
            Animator animator = this.mMaskAnimator;
            if (animator != null) {
                animator.cancel();
            }
            ValueAnimator duration = ValueAnimator.ofFloat(1.0f, 0.0f).setDuration(600L);
            duration.setInterpolator(EaseManager.getInterpolator(new EaseManager.InterpolateEaseStyle(15)));
            duration.setStartDelay(300L);
            duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.aod.AODView$$ExternalSyntheticLambda4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    AODView.$r8$lambda$FHT64E9J4UVnFLxIQRP92yDcGw0(this.f$0, valueAnimator);
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(duration);
            animatorSet.start();
            this.mMaskAnimator = animatorSet;
        }
    }

    public void stopNotificationAnim() {
        NotificationAnimationView notificationAnimationView = this.mAnimationView;
        if (notificationAnimationView != null) {
            notificationAnimationView.stopAnimation();
        }
    }

    public void updateClockColor() {
        AODStyleController aODStyleController = this.mAodStyleController;
        if (aODStyleController != null) {
            aODStyleController.updateClockColor();
        }
    }

    public final void updateClockContainerMarginTop() {
        View view;
        FrameLayout.LayoutParams layoutParams;
        if (!Utils.isFoldDevice() || (view = this.mTableModeContainer) == null || this.mStyleInfo == null || (layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams()) == null) {
            return;
        }
        layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.clock_container_margin_top);
        this.mStyleInfo.dealAodContainerLayoutParam(layoutParams, 3, getContext(), AODSettings.isDualClock(getContext()));
        this.mTableModeContainer.setLayoutParams(layoutParams);
    }

    public void updateClockOrientation() {
        AODStyleController aODStyleController = this.mAodStyleController;
        if (aODStyleController != null) {
            aODStyleController.updateClockOrientation();
        }
    }

    public void updateClockType() {
        makeNormalPanel();
        this.mPositionController.updatePosition(this.mTableModeContainer, false, false, true);
        handleUpdateView(false, false, true);
        invalidate();
    }

    public void updateClockViewVisible() {
        this.mAodStyleController.setClockViewVisible(this.mAodClockVisible);
    }

    public void updateFocusNotificationMaxSize() {
        this.maxSize = DiffDispatch.getMaxCount(this, this.mStyleInfo);
    }

    public final void updateIcons(boolean z) {
        this.mNotificationSize = this.mNotificationArray.size();
        String str = TAG;
        Log.d(str, "updateIcons, mNotificationSize: " + this.mNotificationSize + ", mLastNotificationSize: " + this.mLastNotificationSize);
        if (!isNotificationChanged(z)) {
            Log.d(str, "no need update icons");
            return;
        }
        fallbackDefaultIcon();
        if (this.mOneIconMode) {
            bindView(this.mFirstIcon, 0);
            return;
        }
        MamlViewContainer mamlViewContainer = (MamlViewContainer) findViewById(R.id.maml_view_container);
        if (mamlViewContainer != null) {
            mamlViewContainer.fresh();
            return;
        }
        if (this.mAodStyleController.handleUpdateNotificationIcons(this)) {
            Log.d(str, "BigTime update Icons");
            return;
        }
        if (this.mFirstIcon == null) {
            Log.d(str, "icons is null");
            return;
        }
        handleGeneralUpdateIcons();
        this.mLastNotificationArray.clear();
        this.mLastNotificationArray.addAll(this.mNotificationArray);
        this.mLastNotificationSize = this.mLastNotificationArray.size();
    }

    public void updateIconsTranslationXList() {
        float dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.icon_margin_start);
        float dimensionPixelOffset2 = getResources().getDimensionPixelOffset(R.dimen.icon_size);
        float dimensionPixelOffset3 = getResources().getDimensionPixelOffset(R.dimen.icon_size_flip);
        updateIconsTranslationXList(dimensionPixelOffset, dimensionPixelOffset2);
        updateIconsTranslationXListFlip(dimensionPixelOffset, dimensionPixelOffset3);
    }

    public void updateIconsTranslationXList(float f, float f2) {
        this.iconsTranslationXList.clear();
        this.iconsTranslationXList.add(Float.valueOf((f * 2.0f) + (2.0f * f2)));
        this.iconsTranslationXList.add(Float.valueOf((2.3f * f) + f2));
        this.iconsTranslationXList.add(Float.valueOf(f + f2));
        this.iconsTranslationXList.add(Float.valueOf(f2 - (f * 0.5f)));
        this.iconsTranslationXList.add(Float.valueOf(0.0f));
    }

    public void updateIconsTranslationXListFlip(float f, float f2) {
        this.iconsTranslationXListFlip.clear();
        this.iconsTranslationXListFlip.add(Float.valueOf((f * 2.0f) + (2.0f * f2)));
        this.iconsTranslationXListFlip.add(Float.valueOf((2.3f * f) + f2));
        this.iconsTranslationXListFlip.add(Float.valueOf(f + f2));
        this.iconsTranslationXListFlip.add(Float.valueOf(f2 - (f * 0.5f)));
        this.iconsTranslationXListFlip.add(Float.valueOf(0.0f));
    }

    public final void updateNotificationList() {
        post(new Runnable() { // from class: com.miui.aod.AODView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleUpdateIcons(false);
            }
        });
    }

    public void updatePartIcons(float f, float f2, View view, View view2, View view3, View view4) {
        doIconsUpdateAnimationForFirstIcon(f, view);
        linkageIconTranslationAnimation(view2, f, f2);
        if (this.mIsUpdateForOldNotification) {
            int i = this.mLastNotificationPosition;
            if (i == 2) {
                linkageIconTranslationAnimation(this.mThirdIcon, f, f2);
            } else if (i >= 3) {
                linkageIconTranslationAnimation(this.mThirdIcon, f, f2);
                linkageIconTranslationAnimation(view3, f, f2);
            }
        } else {
            linkageIconTranslationAnimation(this.mThirdIcon, f, f2);
            linkageIconTranslationAnimation(view3, f, f2);
        }
        if (this.mNotificationSize <= 4 || view4 == null) {
            return;
        }
        view4.setVisibility(0);
        if (this.mIsUpdateForOldNotification) {
            return;
        }
        linkageIconAlphaAnimation(view4);
    }

    public final void updatePositionParams(boolean z) {
        AODUpdatePositionController aODUpdatePositionController = this.mPositionController;
        if (aODUpdatePositionController == null || this.mStyleInfo == null) {
            return;
        }
        aODUpdatePositionController.initParams(getContext(), this.mMode);
        this.mPositionController.setAodClock(this.mAodClock);
        handleUpdateView(z, false, true);
    }
}
