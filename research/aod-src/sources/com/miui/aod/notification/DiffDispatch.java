package com.miui.aod.notification;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.miui.aod.AODView;
import com.miui.aod.DozeHost;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.BigTimeCategoryInfo;
import com.miui.aod.category.FlipLinkageClockCategoryInfo;
import com.miui.aod.category.LinkageClockCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.util.WindowUtils;
import java.util.List;
import java.util.function.Consumer;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes.dex */
public final class DiffDispatch {
    public static final String TAG = "DiffDispatch";
    public static EaseManager.EaseStyle moveEaseStyle = EaseManager.getStyle(0, 750.0f, 1.0f, 0.35f);
    public static EaseManager.EaseStyle showEase = EaseManager.getStyle(-2, 0.9f, 0.3f);
    public static EaseManager.EaseStyle scaleEase = EaseManager.getStyle(-2, 0.75f, 0.3f);

    public static /* synthetic */ void $r8$lambda$ldtBn1kTeTe8tgyO_DYet2k8n7w(LinearLayout linearLayout, Context context, int i, NotificationController.NotificationData notificationData) {
        FocusNotificationData focusNotificationData = (FocusNotificationData) notificationData;
        linearLayout.addView(focusNotificationData.mView);
        initLayoutParams(focusNotificationData, context, i);
    }

    public static void diff(List list, DozeHost dozeHost, final int i, NotificationMetaData notificationMetaData) {
        Log.d(TAG, "metaData = " + notificationMetaData);
        final LinearLayout linearLayout = (LinearLayout) dozeHost.findFocusNotificationContainer();
        if (linearLayout == null) {
            return;
        }
        linearLayout.setAlpha(0.0f);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout.getParent();
        linearLayout2.removeView(linearLayout);
        int i2 = i & 7;
        if (i2 == 3) {
            i2 = 8388611;
        } else if (i2 == 5) {
            i2 = 8388613;
        }
        updateGravity(linearLayout, linearLayout2, i2);
        linearLayout.removeAllViews();
        final Context context = linearLayout.getContext();
        notificationMetaData.getNotificationSize();
        int focusNotificationSize = notificationMetaData.getFocusNotificationSize();
        int maxSize = notificationMetaData.getMaxSize();
        if (focusNotificationSize > maxSize) {
            for (int i3 = 0; i3 < maxSize; i3++) {
                FocusNotificationData focusNotificationData = (FocusNotificationData) list.get(i3);
                linearLayout.addView(focusNotificationData.mView);
                initLayoutParams(focusNotificationData, context, i);
            }
        } else {
            list.forEach(new Consumer() { // from class: com.miui.aod.notification.DiffDispatch$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DiffDispatch.$r8$lambda$ldtBn1kTeTe8tgyO_DYet2k8n7w(linearLayout, context, i, (NotificationController.NotificationData) obj);
                }
            });
        }
        linearLayout2.addView(linearLayout, 0);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.focus_notification_height);
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.focus_notification_gap);
        ViewGroup viewGroupFindNotificationContainer = dozeHost.findNotificationContainer();
        if (viewGroupFindNotificationContainer != null) {
            LinearLayout linearLayout3 = (LinearLayout) viewGroupFindNotificationContainer;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout3.getLayoutParams();
            if (linearLayout.getChildCount() > 0) {
                layoutParams.topMargin = 0;
            }
            linearLayout3.setLayoutParams(layoutParams);
            iconsTranslationAnimation(viewGroupFindNotificationContainer, 0.0f, (-(dimensionPixelSize + dimensionPixelSize2)) * Math.min(focusNotificationSize, maxSize));
        }
        showFocusNotificationAlphaAnimation(linearLayout);
        Log.d(TAG, "diff finished ...");
    }

    public static int getMaxCount(AODView aODView, StyleInfo styleInfo) {
        int iIntValue;
        View viewFindViewById = aODView.findViewById(R.id.clock_container);
        if (viewFindViewById == null) {
            return 0;
        }
        if (styleInfo instanceof FlipLinkageClockCategoryInfo) {
            return 1;
        }
        if (styleInfo instanceof BigTimeCategoryInfo) {
            return 0;
        }
        if (!(styleInfo instanceof LinkageClockCategoryInfo)) {
            return 1;
        }
        int i = WindowUtils.INSTANCE.getWindowSize(aODView.getContext()).y;
        Context context = viewFindViewById.getContext();
        ViewGroup viewGroup = (ViewGroup) viewFindViewById.findViewById(R.id.aod_content_container);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.focus_notification_height);
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.focus_notification_gap);
        float clockTranslation = aODView.getHost().getClockTranslation();
        if (!Utils.isGxzwSensor() || !Utils.isUnlockWithFingerprintPossible(context, aODView.getHost()) || (iIntValue = ((Integer) Utils.getGxzwIconPosition().second).intValue()) < 0) {
            iIntValue = i;
        }
        return Math.min(3, Math.max((Math.min(i - viewFindViewById.getTop(), (int) ((iIntValue - viewFindViewById.getTop()) - clockTranslation)) - viewGroup.getBottom()) / (dimensionPixelSize + dimensionPixelSize2), 1));
    }

    public static void iconsTranslationAnimation(View view, float f, float f2) {
        AnimConfig ease = new AnimConfig().setEase(moveEaseStyle);
        IStateStyle iStateStyleState = Folme.useAt(view).state();
        ViewProperty viewProperty = ViewProperty.TRANSLATION_Y;
        iStateStyleState.setTo(viewProperty, Float.valueOf(f2)).to(viewProperty, Float.valueOf(f), ease);
    }

    public static void initAnimation(LinearLayout linearLayout) {
        Log.v(TAG, "initAnimation: ");
        LayoutTransition layoutTransition = new LayoutTransition();
        LinearLayout linearLayout2 = (LinearLayout) linearLayout.getParent();
        LayoutTransition layoutTransition2 = new LayoutTransition();
        layoutTransition2.setAnimator(4, ObjectAnimator.ofFloat((Object) null, "translationY", 300.0f, 0.0f));
        layoutTransition2.setStagger(0, 30L);
        layoutTransition2.setStagger(1, 30L);
        linearLayout2.setLayoutTransition(layoutTransition2);
        layoutTransition.setAnimator(2, ObjectAnimator.ofFloat((Object) null, "translationY", -300.0f, 0.0f));
        layoutTransition.setAnimator(3, ObjectAnimator.ofFloat((Object) null, "translationY", 300.0f, 0.0f));
        layoutTransition.setStagger(0, 30L);
        layoutTransition.setStagger(1, 30L);
        linearLayout.setLayoutTransition(layoutTransition);
    }

    public static void initLayoutParams(FocusNotificationData focusNotificationData, Context context, int i) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) focusNotificationData.mView.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.bottomMargin = context.getResources().getDimensionPixelSize(R.dimen.focus_notification_gap);
    }

    public static void showFocusNotificationAlphaAnimation(final View view) {
        AnimConfig delay = new AnimConfig().setEase(showEase).addListeners(new TransitionListener() { // from class: com.miui.aod.notification.DiffDispatch.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                View view2 = view;
                if (view2 != null) {
                    DiffDispatch.showFocusNotificationScaleAnimation(view2);
                }
            }
        }).setDelay(150L);
        IStateStyle iStateStyleState = Folme.useAt(view).state();
        ViewProperty viewProperty = ViewProperty.ALPHA;
        iStateStyleState.setTo(viewProperty, Float.valueOf(0.0f)).to(viewProperty, Float.valueOf(1.0f), delay);
    }

    public static void showFocusNotificationScaleAnimation(View view) {
        AnimConfig ease = new AnimConfig().setEase(scaleEase);
        IStateStyle iStateStyleState = Folme.useAt(view).state();
        ViewProperty viewProperty = ViewProperty.SCALE_X;
        Float fValueOf = Float.valueOf(0.8f);
        ViewProperty viewProperty2 = ViewProperty.SCALE_Y;
        IStateStyle to = iStateStyleState.setTo(viewProperty, fValueOf, viewProperty2, fValueOf);
        Float fValueOf2 = Float.valueOf(1.0f);
        to.to(viewProperty, fValueOf2, viewProperty2, fValueOf2, ease);
    }

    public static void updateGravity(LinearLayout linearLayout, LinearLayout linearLayout2, int i) {
        linearLayout.setGravity(i);
        linearLayout2.setGravity(i);
    }
}
