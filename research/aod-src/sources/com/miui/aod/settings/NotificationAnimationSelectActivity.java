package com.miui.aod.settings;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.aod.AODStyleController;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.DrawableData;
import com.miui.aod.components.widget.NotificationAnimationSelectView;
import com.miui.aod.util.ActionBarHelper;
import com.miui.aod.util.WindowUtils;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.aod.widget.NotificationAnimationView;
import com.miui.aod.widget.notification.NotificationStyleManager;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import java.util.TimeZone;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AppCompatActivity;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class NotificationAnimationSelectActivity extends AppCompatActivity {
    public boolean fromUser;
    public boolean m24HourFormat;
    public long mAnimationDuration;
    public NotificationAnimationSelectView mAnimationSelectView;
    public final Runnable mAnimationTask;
    public NotificationAnimationView mAnimationView;
    public final Animator.AnimatorListener mAnimatorListener;
    public View mAodContent;
    public CustomClockView mClockView;
    public final ContentObserver mContentObserver;
    public final Handler mHandler;
    public View mLayoutAodNotificationScreenOff;
    public View mLayoutAodNotificationScreenOn;
    public LayoutInflater mLayoutInflater;
    public boolean mNeedShowWallpaper = false;
    public final View.OnTouchListener mOnTouchListener;
    public boolean mPreviewMode;
    public AODStyleController mPreviewStyleController;
    public StyleInfo mStyleInfo;
    public final Runnable mUpdateRunnable;

    public NotificationAnimationSelectActivity() {
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.fromUser = false;
        this.mContentObserver = new ContentObserver(handler) { // from class: com.miui.aod.settings.NotificationAnimationSelectActivity.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (NotificationAnimationSelectActivity.this.fromUser) {
                    NotificationAnimationSelectActivity.this.fromUser = false;
                    return;
                }
                String selectedStyleName = NotificationStyleManager.getSelectedStyleName(NotificationAnimationSelectActivity.this.getApplicationContext());
                if ("none".equals(selectedStyleName) || NotificationStyleManager.STYLE_NAME_SCREEN_ON.equals(selectedStyleName)) {
                    NotificationAnimationSelectActivity.this.stopAnimation();
                } else {
                    NotificationAnimationSelectActivity.this.scheduleAnimation(selectedStyleName);
                }
                NotificationAnimationSelectActivity.this.mAnimationSelectView.setItemSelected(selectedStyleName);
            }
        };
        this.mAnimatorListener = new AnimatorListenerAdapter() { // from class: com.miui.aod.settings.NotificationAnimationSelectActivity.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (NotificationAnimationSelectActivity.this.mPreviewMode) {
                    NotificationAnimationSelectActivity.this.setSystemUIVisible(false);
                    NotificationAnimationSelectActivity.this.mAnimationSelectView.setVisibility(8);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                if (NotificationAnimationSelectActivity.this.mPreviewMode) {
                    return;
                }
                NotificationAnimationSelectActivity.this.setSystemUIVisible(true);
                NotificationAnimationSelectActivity.this.mAnimationSelectView.setVisibility(0);
            }
        };
        this.mAnimationTask = new Runnable() { // from class: com.miui.aod.settings.NotificationAnimationSelectActivity.3
            @Override // java.lang.Runnable
            public void run() {
                if (NotificationAnimationSelectActivity.this.startAnimation(NotificationStyleManager.getSelectedStyleName(NotificationAnimationSelectActivity.this.getApplicationContext()), false)) {
                    NotificationAnimationSelectActivity.this.mHandler.postDelayed(this, NotificationAnimationSelectActivity.this.mAnimationDuration + 3000);
                }
            }
        };
        this.mOnTouchListener = new View.OnTouchListener() { // from class: com.miui.aod.settings.NotificationAnimationSelectActivity.4
            public long mCurrentMS;
            public float mDownX;
            public float mDownY;

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    this.mDownX = motionEvent.getX();
                    this.mDownY = motionEvent.getY();
                    this.mCurrentMS = System.currentTimeMillis();
                    return false;
                }
                if (action != 1) {
                    return false;
                }
                long jCurrentTimeMillis = System.currentTimeMillis() - this.mCurrentMS;
                float x = motionEvent.getX() - this.mDownX;
                float y = motionEvent.getY() - this.mDownY;
                if (jCurrentTimeMillis > 100) {
                    return Math.abs(x) > 20.0f || Math.abs(y) > 20.0f;
                }
                return false;
            }
        };
        this.mUpdateRunnable = new Runnable() { // from class: com.miui.aod.settings.NotificationAnimationSelectActivity.5
            @Override // java.lang.Runnable
            public void run() {
                NotificationAnimationSelectActivity notificationAnimationSelectActivity = NotificationAnimationSelectActivity.this;
                notificationAnimationSelectActivity.mPreviewStyleController.handleUpdateTime(notificationAnimationSelectActivity.m24HourFormat);
                NotificationAnimationSelectActivity.this.mHandler.postDelayed(NotificationAnimationSelectActivity.this.mUpdateRunnable, 60000L);
            }
        };
    }

    private void enterOrExitSuperPreview(boolean z) {
        View viewFindViewById = findViewById(R.id.black_bg_view);
        if (z) {
            viewFindViewById.animate().alpha(0.0f).setDuration(1200L).setStartDelay(600L).start();
        } else {
            viewFindViewById.setAlpha(1.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSystemUIVisible(boolean z) {
        if (z) {
            getWindow().getDecorView().setSystemUiVisibility(1280);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(5894);
        }
    }

    public final void changeLayout() {
        StyleInfo styleInfo = AODStyleController.getStyleInfo(getApplicationContext());
        if (styleInfo.getName() == null || !styleInfo.getName().equals(this.mStyleInfo.getName())) {
            startActivity(new Intent(this, (Class<?>) NotificationAnimationSelectActivity.class));
            finish();
            return;
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.aod_notification_custom_clock_margin_top);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.aod_notification_custom_clock_margin_start);
        this.mLayoutAodNotificationScreenOn.setBackgroundResource(R.drawable.aod_notification_on_preview_bg);
        if (Utils.isFoldDevice()) {
            updatePreviewFrameSize();
            if (Utils.inLargeScreen(this)) {
                boolean z = getResources().getConfiguration().orientation == 2;
                int dimensionPixelSize3 = getResources().getDimensionPixelSize(z ? R.dimen.aod_notification_custom_clock_margin_top_full_land : R.dimen.aod_notification_custom_clock_margin_top_full);
                dimensionPixelSize2 = getResources().getDimensionPixelSize(z ? R.dimen.aod_notification_custom_clock_margin_start_full_land : R.dimen.aod_notification_custom_clock_margin_start_full);
                dimensionPixelSize = dimensionPixelSize3;
            }
        } else {
            WindowUtils windowUtils = WindowUtils.INSTANCE;
            int i = windowUtils.getWindowSize(this).x;
            int i2 = windowUtils.getWindowSize(this).y;
            int dimensionPixelSize4 = (i - (getResources().getDimensionPixelSize(R.dimen.content_horizontal_margin) * 2)) - (getResources().getDimensionPixelSize(R.dimen.aod_notification_padding) * 2);
            String str = i + ":" + i2;
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mLayoutAodNotificationScreenOn.getLayoutParams();
            if (layoutParams != null) {
                ((ViewGroup.MarginLayoutParams) layoutParams).width = dimensionPixelSize4;
                layoutParams.dimensionRatio = str;
            }
            this.mLayoutAodNotificationScreenOn.setLayoutParams(layoutParams);
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mLayoutAodNotificationScreenOff.getLayoutParams();
            if (layoutParams2 != null) {
                ((ViewGroup.MarginLayoutParams) layoutParams2).width = dimensionPixelSize4;
                layoutParams2.dimensionRatio = str;
            }
            this.mLayoutAodNotificationScreenOff.setLayoutParams(layoutParams2);
        }
        if (this.mStyleInfo != null && this.mPreviewStyleController != null) {
            if (Utils.isFoldDevice()) {
                ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mLayoutAodNotificationScreenOff.getLayoutParams();
                if (((ViewGroup.MarginLayoutParams) layoutParams3).width > 0 && ((ViewGroup.MarginLayoutParams) layoutParams3).height > 0) {
                    FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) this.mAodContent.getLayoutParams();
                    layoutParams4.width = (((ViewGroup.MarginLayoutParams) layoutParams3).width - this.mLayoutAodNotificationScreenOff.getPaddingLeft()) - this.mLayoutAodNotificationScreenOff.getPaddingRight();
                    this.mAodContent.setLayoutParams(layoutParams4);
                }
            }
            if (CategoryConstants.CATEGORY_LINKAGE.equals(this.mStyleInfo.getName()) || CategoryConstants.CATEGORY_TEXTSTYLE.equals(this.mStyleInfo.getName())) {
                this.mPreviewStyleController.inflateView(this.mAodContent);
            }
        }
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mLayoutAodNotificationScreenOff.getLayoutParams();
        LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) this.mClockView.getLayoutParams();
        layoutParams6.topMargin = dimensionPixelSize;
        layoutParams6.setMarginStart(dimensionPixelSize2);
        layoutParams6.width = ((ViewGroup.MarginLayoutParams) layoutParams5).width - (getResources().getDimensionPixelSize(R.dimen.aod_notification_custom_clock_margin_start) * 2);
        if (Utils.isFoldDevice() && Utils.inLargeScreen(this)) {
            layoutParams6.width = ((ViewGroup.MarginLayoutParams) layoutParams5).width - (getResources().getDimensionPixelSize(R.dimen.aod_notification_custom_clock_margin_start_full) * 2);
        }
        this.mClockView.setLayoutParams(layoutParams6);
        this.mClockView.changeLayout();
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        changeLayout();
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.mNeedShowWallpaper = Utils.isUsingSuperWallpaperStyle(getApplicationContext()) && Utils.isAodEnable(getApplicationContext());
        super.onCreate(bundle);
        Window window = getWindow();
        window.clearFlags(201326592);
        window.getDecorView().setSystemUiVisibility(1280);
        window.addFlags(Integer.MIN_VALUE);
        window.addFlags(134217728);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.layoutInDisplayCutoutMode = 1;
        window.setAttributes(attributes);
        window.setStatusBarColor(0);
        setContentView(R.layout.activity_notification_animation_select);
        ActionBarHelper.setupOverlayActionBar(this, findViewById(R.id.scroll_view));
        this.mLayoutAodNotificationScreenOff = findViewById(R.id.layout_aod_notification_screen_off);
        this.mLayoutAodNotificationScreenOn = findViewById(R.id.layout_aod_notification_screen_on);
        this.mClockView = (CustomClockView) findViewById(R.id.custom_clock);
        this.mAodContent = findViewById(R.id.clock_container);
        NotificationAnimationView notificationAnimationView = (NotificationAnimationView) findViewById(R.id.animation_view);
        this.mAnimationView = notificationAnimationView;
        notificationAnimationView.setOutlineProvider(new RoundOutlineProvider(getResources().getDimensionPixelSize(R.dimen.notification_animation_preview_radius)));
        this.mAodContent.setOnTouchListener(this.mOnTouchListener);
        View view = this.mAodContent;
        if (Utils.isAodEnable(getApplicationContext())) {
            view.setImportantForAccessibility(1);
            this.m24HourFormat = DateFormat.is24HourFormat(getApplicationContext());
            this.mLayoutInflater = LayoutInflater.from(getApplicationContext());
            AODStyleController aODStyleController = new AODStyleController(2);
            this.mPreviewStyleController = aODStyleController;
            aODStyleController.setForAodNotification(true);
            if (this.mNeedShowWallpaper) {
                ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin = 0;
            }
            StyleInfo styleInfo = AODStyleController.getStyleInfo(getApplicationContext());
            if (!CategoryConstants.CATEGORY_TEXTSTYLE.equals(styleInfo.getName()) && !CategoryConstants.CATEGORY_LINKAGE.equals(styleInfo.getName())) {
                this.mPreviewStyleController.inflateView(view);
            }
            this.mPreviewStyleController.handleUpdateTime(this.m24HourFormat);
            Calendar calendar = new Calendar(TimeZone.getDefault());
            this.mHandler.postDelayed(this.mUpdateRunnable, ((60 - calendar.get(21)) * 1000) + (1000 - calendar.get(22)));
        } else {
            view.setImportantForAccessibility(4);
        }
        NotificationAnimationSelectView notificationAnimationSelectView = (NotificationAnimationSelectView) findViewById(R.id.notification_animation_select_view);
        this.mAnimationSelectView = notificationAnimationSelectView;
        notificationAnimationSelectView.setOnItemClickListener(new NotificationAnimationSelectView.OnItemClickListener() { // from class: com.miui.aod.settings.NotificationAnimationSelectActivity$$ExternalSyntheticLambda0
            @Override // com.miui.aod.components.widget.NotificationAnimationSelectView.OnItemClickListener
            public final void onItemClick(int i, DrawableData drawableData) {
                this.f$0.onNotificationAnimationSelect(i, drawableData);
            }
        });
        this.mAnimationSelectView.setDrawableData(NotificationStyleManager.getStyleList());
        getContentResolver().registerContentObserver(Settings.System.getUriFor("wakeup_for_keyguard_notification"), false, this.mContentObserver);
        setStyleScreenOn(NotificationStyleManager.STYLE_NAME_SCREEN_ON.equals(NotificationStyleManager.getSelectedStyleName(getApplicationContext())));
        this.mStyleInfo = AODStyleController.getStyleInfo(getApplicationContext());
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mAodContent.getLayoutParams();
        String name = this.mStyleInfo.getName();
        name.getClass();
        if (name.equals(CategoryConstants.CATEGORY_LINEAR_TIMETEXT)) {
            layoutParams.gravity = this.mStyleInfo.getClockGravity() | 48;
        } else if (name.equals(CategoryConstants.CATEGORY_SMART_COVER)) {
            layoutParams.gravity = 8388661;
        }
        UserHandleUtils.refresh();
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        NotificationAnimationSelectView notificationAnimationSelectView = this.mAnimationSelectView;
        if (notificationAnimationSelectView != null) {
            notificationAnimationSelectView.onDestroyView();
        }
        CustomClockView customClockView = this.mClockView;
        if (customClockView != null) {
            customClockView.stopUpdateTime();
            this.mClockView = null;
        }
        this.mHandler.removeCallbacksAndMessages(null);
        getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    public final void onNotificationAnimationSelect(int i, DrawableData drawableData) {
        if ("none".equals(drawableData.mName)) {
            setStyleScreenOn(false);
            stopAnimation();
            Utils.putKeyguardNotificationStatus(getApplicationContext(), 0);
        } else if (NotificationStyleManager.STYLE_NAME_SCREEN_ON.equals(drawableData.mName)) {
            setStyleScreenOn(true);
            stopAnimation();
            Utils.putKeyguardNotificationStatus(getApplicationContext(), 1);
        } else {
            setStyleScreenOn(false);
            scheduleAnimation(drawableData.mName);
            Utils.putKeyguardNotificationStatus(getApplicationContext(), 2);
        }
        this.fromUser = true;
        NotificationStyleManager.setSelectedStyleName(getApplicationContext(), drawableData.mName);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.mHandler.removeCallbacksAndMessages(null);
        if (this.mNeedShowWallpaper) {
            enterOrExitSuperPreview(false);
        }
        stopAnimation();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (Utils.reStartActivityMultiWindow(this)) {
            return;
        }
        if (this.mNeedShowWallpaper) {
            enterOrExitSuperPreview(true);
        }
        String selectedStyleName = NotificationStyleManager.getSelectedStyleName(getApplicationContext());
        if (!"none".equals(selectedStyleName) && !NotificationStyleManager.STYLE_NAME_SCREEN_ON.equals(selectedStyleName)) {
            scheduleAnimation(selectedStyleName);
        }
        this.mAnimationSelectView.setItemSelected(selectedStyleName);
        View viewFindViewById = findViewById(R.id.action_bar_container);
        if (viewFindViewById.isLaidOut()) {
            changeLayout();
        } else {
            viewFindViewById.post(new Runnable() { // from class: com.miui.aod.settings.NotificationAnimationSelectActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.changeLayout();
                }
            });
        }
    }

    public final void scheduleAnimation(String str) {
        this.mHandler.removeCallbacks(this.mAnimationTask);
        if (NotificationStyleManager.STYLE_NAME_RED_WAVE.equals(str)) {
            startAnimation(str, true);
        } else {
            this.mHandler.post(this.mAnimationTask);
        }
    }

    public final void setStyleScreenOn(boolean z) {
        if (z) {
            this.mLayoutAodNotificationScreenOn.setVisibility(0);
            this.mLayoutAodNotificationScreenOff.setVisibility(8);
        } else {
            this.mLayoutAodNotificationScreenOn.setVisibility(8);
            this.mLayoutAodNotificationScreenOff.setVisibility(0);
        }
    }

    public final boolean startAnimation(String str, boolean z) {
        if (this.mAnimationView == null) {
            return false;
        }
        stopAnimation();
        NotificationAnimationView.AnimationDrawer animationDrawerCreateAnimationDrawer = NotificationStyleManager.createAnimationDrawer(getApplicationContext(), str);
        if (animationDrawerCreateAnimationDrawer == null) {
            return false;
        }
        this.mAnimationDuration = animationDrawerCreateAnimationDrawer.getDuration();
        animationDrawerCreateAnimationDrawer.setRepeatMode(z);
        this.mAnimationView.setAnimationDrawer(animationDrawerCreateAnimationDrawer);
        this.mAnimationView.startAnimation();
        return true;
    }

    public final void stopAnimation() {
        this.mHandler.removeCallbacks(this.mAnimationTask);
        NotificationAnimationView notificationAnimationView = this.mAnimationView;
        if (notificationAnimationView != null) {
            notificationAnimationView.stopAnimation();
        }
    }

    public final void updatePreviewFrameSize() {
        WindowUtils windowUtils = WindowUtils.INSTANCE;
        int i = windowUtils.getWindowSize(this).y;
        int i2 = windowUtils.getWindowSize(this).x;
        ActionBar appCompatActionBar = getAppCompatActionBar();
        int height = (appCompatActionBar == null || appCompatActionBar.getActionBarView() == null || !(appCompatActionBar.getActionBarView().getParent() instanceof View)) ? 0 : ((View) appCompatActionBar.getActionBarView().getParent()).getHeight();
        View view = (View) this.mAnimationSelectView.getParent();
        int height2 = view != null ? view.getHeight() : 0;
        View viewFindViewById = findViewById(R.id.preview_content);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewFindViewById.getLayoutParams();
        int paddingTop = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + viewFindViewById.getPaddingTop() + viewFindViewById.getPaddingBottom();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int dimensionPixelSize = ((((i - height) - height2) - paddingTop) - (marginLayoutParams.topMargin + marginLayoutParams.bottomMargin)) - getResources().getDimensionPixelSize(R.dimen.notification_animation_preview_bottom_margin);
        int i3 = (i2 * dimensionPixelSize) / i;
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mLayoutAodNotificationScreenOff.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = i3;
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = dimensionPixelSize;
        layoutParams2.dimensionRatio = null;
        this.mLayoutAodNotificationScreenOff.setLayoutParams(layoutParams2);
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mLayoutAodNotificationScreenOn.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = i3;
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = dimensionPixelSize;
        layoutParams3.dimensionRatio = null;
        this.mLayoutAodNotificationScreenOn.setLayoutParams(layoutParams3);
    }
}
