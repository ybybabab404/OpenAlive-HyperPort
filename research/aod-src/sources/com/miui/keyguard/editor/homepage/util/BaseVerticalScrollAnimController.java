package com.miui.keyguard.editor.homepage.util;

import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.view.CrossViewPager;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder;
import com.miui.keyguard.editor.homepage.view.viewpager.ScrollHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: BaseVerticalScrollAnimController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public class BaseVerticalScrollAnimController implements CrossViewPagerCallback {
    public static final Companion Companion = new Companion(null);
    public final CrossViewPager crossViewPager;
    public final Map floorHolderUnScheduledAnimCount;
    public boolean isFlingAnimationFinished;
    public boolean isLargeScreen;
    public int lastSelectedPosition;
    public int lastSelectedPositionYForReset;
    public final RecyclerView.LayoutManager layoutManager;
    public OnVerticalScrollAnimationCallback mCallback;
    public final ScrollHelper scrollHelper;

    /* JADX INFO: compiled from: BaseVerticalScrollAnimController.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BaseVerticalScrollAnimController(CrossViewPager crossViewPager) {
        Intrinsics.checkNotNullParameter(crossViewPager, "crossViewPager");
        this.crossViewPager = crossViewPager;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        this.isLargeScreen = (deviceUtil.isPhone() || deviceUtil.isFlip()) ? false : true;
        this.lastSelectedPositionYForReset = -1;
        this.isFlingAnimationFinished = true;
        this.layoutManager = crossViewPager.getLayoutManager();
        ScrollHelper scrollHelper = new ScrollHelper();
        scrollHelper.attachToViewPager2(crossViewPager.getViewPager2());
        this.scrollHelper = scrollHelper;
        this.floorHolderUnScheduledAnimCount = new LinkedHashMap();
    }

    public static /* synthetic */ void changeHorizontalHolderUnScheduledAnimCount$default(BaseVerticalScrollAnimController baseVerticalScrollAnimController, int i, boolean z, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: changeHorizontalHolderUnScheduledAnimCount");
        }
        if ((i2 & 2) != 0) {
            z = true;
        }
        baseVerticalScrollAnimController.changeHorizontalHolderUnScheduledAnimCount(i, z);
    }

    public final void changeHorizontalHolderUnScheduledAnimCount(int i, boolean z) {
        Integer num = (Integer) this.floorHolderUnScheduledAnimCount.get(Integer.valueOf(i));
        int iIntValue = num != null ? num.intValue() : 0;
        this.floorHolderUnScheduledAnimCount.put(Integer.valueOf(i), Integer.valueOf(Integer.max(0, z ? iIntValue + 1 : iIntValue - 1)));
        Log.d("Keyguard-Editor-BaseVerticalScrollAnimController", "position = " + i + " animCount = " + this.floorHolderUnScheduledAnimCount.get(Integer.valueOf(i)));
    }

    public final CrossViewPager getCrossViewPager() {
        return this.crossViewPager;
    }

    public final Map getFloorHolderUnScheduledAnimCount() {
        return this.floorHolderUnScheduledAnimCount;
    }

    public final int getLastSelectedPosition() {
        return this.lastSelectedPosition;
    }

    public final int getLastSelectedPositionYForReset() {
        return this.lastSelectedPositionYForReset;
    }

    public final RecyclerView.LayoutManager getLayoutManager() {
        return this.layoutManager;
    }

    public final OnVerticalScrollAnimationCallback getMCallback() {
        return this.mCallback;
    }

    public final ScrollHelper getScrollHelper() {
        return this.scrollHelper;
    }

    public final boolean isFlingAnimationFinished() {
        return this.isFlingAnimationFinished;
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onAddTemplate() {
        CrossViewPagerCallback.DefaultImpls.onAddTemplate(this);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onCurrentTemplateScreenshot(BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, CommonConfig commonConfig) {
        CrossViewPagerCallback.DefaultImpls.onCurrentTemplateScreenshot(this, baseTemplateView, baseTemplateView2, commonConfig);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onHistoryTemplateReady() {
        CrossViewPagerCallback.DefaultImpls.onHistoryTemplateReady(this);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onHomeWallpaperDarkResultReady() {
        CrossViewPagerCallback.DefaultImpls.onHomeWallpaperDarkResultReady(this);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onMyHomeTemplateCreated(BaseTemplateView baseTemplateView) {
        CrossViewPagerCallback.DefaultImpls.onMyHomeTemplateCreated(this, baseTemplateView);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onMyLockTemplateCreated(BaseTemplateView baseTemplateView, CommonConfig commonConfig) {
        CrossViewPagerCallback.DefaultImpls.onMyLockTemplateCreated(this, baseTemplateView, commonConfig);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageDeleteCompleted(int i, int i2, TemplateItemBean templateItemBean) {
        CrossViewPagerCallback.DefaultImpls.onPageDeleteCompleted(this, i, i2, templateItemBean);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public boolean onPageDeleted(int i, int i2, TemplateItemBean templateItemBean) {
        return CrossViewPagerCallback.DefaultImpls.onPageDeleted(this, i, i2, templateItemBean);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageEnterDeleteMode(int i, int i2, View view, boolean z) {
        CrossViewPagerCallback.DefaultImpls.onPageEnterDeleteMode(this, i, i2, view, z);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageScrollStateChanged(int i, int i2, int i3, int i4) {
        CrossViewPagerCallback.DefaultImpls.onPageScrollStateChanged(this, i, i2, i3, i4);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageSelected(int i, int i2, int i3, int i4) {
        CrossViewPagerCallback.DefaultImpls.onPageSelected(this, i, i2, i3, i4);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageTransformed(int i, BaseViewHolder baseViewHolder, View view, float f, float f2, int i2) {
        CrossViewPagerCallback.DefaultImpls.onPageTransformed(this, i, baseViewHolder, view, f, f2, i2);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onTemplateCustomized(int i, int i2, TemplateItemBean templateItemBean, int i3) {
        CrossViewPagerCallback.DefaultImpls.onTemplateCustomized(this, i, i2, templateItemBean, i3);
    }

    public final void onVerticalScrollStateChanged(int i) {
        OnVerticalScrollAnimationCallback onVerticalScrollAnimationCallback = this.mCallback;
        if (onVerticalScrollAnimationCallback != null) {
            onVerticalScrollAnimationCallback.onVerticalScrollStateChanged(i);
        }
    }

    public void processScreenConfigurationChanged() {
    }

    public final void resetCustomButton(BaseItemViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        View customButton = holder.getCustomButton();
        Folme.clean(customButton);
        if (customButton != null) {
            ViewUtil.INSTANCE.clearBackgroundBlurModeWhenBlurEnabled(customButton);
            customButton.setAlpha(0.0f);
        }
    }

    public final void setCallback(OnVerticalScrollAnimationCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mCallback = callback;
    }

    public final void setFlingAnimationFinished(boolean z) {
        this.isFlingAnimationFinished = z;
    }

    public final void setLastSelectedPosition(int i) {
        this.lastSelectedPosition = i;
    }

    public final void setLastSelectedPositionYForReset(int i) {
        this.lastSelectedPositionYForReset = i;
    }

    public final void startCustomButtonAnimationForSnapCurrentPosition(final int i, final BaseItemViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        final View customButton = holder.getCustomButton();
        Folme.clean(customButton);
        ViewUtil.setBackgroundBlurModeWhenBlurEnabled$default(ViewUtil.INSTANCE, customButton, 0.0f, 0, 3, null);
        AnimState animState = new AnimState("alpha_from");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        Folme.useAt(customButton).state().setTo(animState.add(viewProperty, 0.0f, new long[0])).to(new AnimState("alpha_to").add(viewProperty, 1.0f, new long[0]), new AnimConfig().setDelay(200L).setSpecial(viewProperty, EaseManager.getStyle(-2, 0.85f, 0.35f), new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.homepage.util.BaseVerticalScrollAnimController$startCustomButtonAnimationForSnapCurrentPosition$config$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                holder.decorateCustomizeBackground();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                restoreOriginal();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                restoreOriginal();
            }

            public final void restoreOriginal() {
                holder.setSelected(true);
                this.setFlingAnimationFinished(true);
                this.onVerticalScrollStateChanged(3);
                customButton.setAlpha(1.0f);
                this.changeHorizontalHolderUnScheduledAnimCount(i, false);
            }
        }));
        changeHorizontalHolderUnScheduledAnimCount$default(this, i, false, 2, null);
    }
}
