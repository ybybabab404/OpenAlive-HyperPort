package com.miui.keyguard.editor.homepage.view.viewpager;

import android.content.Context;
import android.view.View;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.keyguard.editor.homepage.util.CrossListViewUtil;
import java.util.Collection;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes2.dex */
public class SpringLayoutHelper {
    public final Context context;
    public int screenHeight;
    public SpringScrollListener springScrollListener;
    public final View targetView;

    public SpringLayoutHelper(Context context, View view) {
        this.context = context;
        this.targetView = view;
    }

    public final float calculateFriction(int i, int i2) {
        if (i2 <= 0) {
            return 0.0f;
        }
        int i3 = i >= 0 ? 1 : -1;
        float f = i2;
        float fMin = Math.min((Math.abs(i) * 1.0f) / f, 1.0f);
        float f2 = fMin * fMin;
        return i3 * ((((f2 * fMin) / 3.0f) - f2) + fMin) * f;
    }

    public final float calculateScrollableOffset(int i) {
        return calculateFriction(i, getScreenHeight()) * 0.4f;
    }

    public final void callbackSpringBackComplete(int i) {
        SpringScrollListener springScrollListener = this.springScrollListener;
        if (springScrollListener != null) {
            springScrollListener.onSpringBackComplete(i);
        }
    }

    public final void callbackSpringScrolled(int i, float f) {
        SpringScrollListener springScrollListener = this.springScrollListener;
        if (springScrollListener != null) {
            springScrollListener.onSpringScrolled(i, f);
        }
    }

    public final int getScreenHeight() {
        if (this.screenHeight == 0) {
            this.screenHeight = CrossListViewUtil.INSTANCE.requireDisplayRealSize(this.context).y;
        }
        return this.screenHeight;
    }

    public final ViewProperty getTranslateProperty(int i) {
        return (i == 1 || i == 3) ? ViewProperty.TRANSLATION_X : ViewProperty.TRANSLATION_Y;
    }

    public final void resetTargetViewTranslate(int i) {
        if (i == 1 || i == 3) {
            this.targetView.setTranslationX(0.0f);
        } else {
            this.targetView.setTranslationY(0.0f);
        }
    }

    public void setSpringScrollListener(SpringScrollListener springScrollListener) {
        this.springScrollListener = springScrollListener;
    }

    public void springScrollX(int i, int i2) {
        float fCalculateScrollableOffset = calculateScrollableOffset(i2);
        if (fCalculateScrollableOffset != 0.0f) {
            this.targetView.setTranslationX(fCalculateScrollableOffset);
            callbackSpringScrolled(i, fCalculateScrollableOffset);
        }
    }

    public void springScrollY(int i, int i2) {
        float fCalculateScrollableOffset = calculateScrollableOffset(i2);
        if (fCalculateScrollableOffset != 0.0f) {
            this.targetView.setTranslationY(fCalculateScrollableOffset);
            callbackSpringScrolled(i, fCalculateScrollableOffset);
        }
    }

    public void startSpringBackAnimation(final int i) {
        Folme.clean(this.targetView);
        Folme.useAt(this.targetView).state().to(new AnimState().add(getTranslateProperty(i), BaseStyleSelectView.COLLAPSE_SLIDE_VALUE), new AnimConfig().setSpecial(getTranslateProperty(i), EaseManager.getStyle(-2, 0.95f, 0.45f), new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.homepage.view.viewpager.SpringLayoutHelper.1
            public final void completeSpringBack() {
                Folme.clean(SpringLayoutHelper.this.targetView);
                SpringLayoutHelper.this.resetTargetViewTranslate(i);
                SpringLayoutHelper.this.callbackSpringBackComplete(i);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                completeSpringBack();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                completeSpringBack();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, SpringLayoutHelper.this.getTranslateProperty(i).getName());
                if (updateInfoFindByName != null) {
                    SpringLayoutHelper.this.callbackSpringScrolled(i, updateInfoFindByName.getFloatValue());
                }
            }
        }));
    }
}
