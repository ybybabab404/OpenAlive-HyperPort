package com.miui.keyguard.editor.homepage.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TemplateViewFactory;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.ExtraInfo;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.LockWallpaperSource;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.util.DataUtil;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.base.CombineEditListener;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.homepage.MainFragment;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.homepage.view.generator.BaseItemViewFactoryImpl;
import com.miui.keyguard.editor.homepage.view.generator.FactoryParams;
import com.miui.keyguard.editor.track.TrackHelper;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.view.LockScreenTransformerAnimationListener;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: AdapterAndHolderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MyTemplateViewHolder extends BaseItemViewHolder implements DefaultLifecycleObserver, CombineEditListener {
    public final String TAG;
    public float baseHomeTranslationY;
    public CommonConfig configForScreenshot;
    public int homeExtraHeight;
    public float homeTemplateScale;
    public BaseTemplateView homeTemplateView;
    public boolean isTakeScreenshot;
    public String lastTemplateId;
    public FrameLayout.LayoutParams layoutPramsForTemplate;
    public float lockTemplateScale;
    public BaseTemplateView lockTemplateView;
    public CommonConfig myTemplateBindData;
    public AtomicInteger templateReadyCount;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MyTemplateViewHolder(LayoutInflater inflater, ViewGroup parent, BaseItemViewFactoryImpl itemViewFactoryImpl, FactoryParams params) {
        super(inflater, parent, itemViewFactoryImpl, params);
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(itemViewFactoryImpl, "itemViewFactoryImpl");
        Intrinsics.checkNotNullParameter(params, "params");
        this.TAG = "Keyguard-Editor-MyTemplateViewHolder";
        this.lockTemplateScale = 1.0f;
        this.homeTemplateScale = 1.0f;
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = this.itemView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.homeExtraHeight = resourcesProvider.provideHomeExtraHeight(context);
        this.templateReadyCount = new AtomicInteger(0);
    }

    public static final void createOrLoadTemplateView$lambda$6$lambda$4(MyTemplateViewHolder myTemplateViewHolder, BaseTemplateView baseTemplateView) {
        CrossViewPagerCallback crossViewPagerCallback = (CrossViewPagerCallback) myTemplateViewHolder.getCallback();
        if (crossViewPagerCallback != null) {
            CrossViewPagerCallback.DefaultImpls.onMyLockTemplateCreated$default(crossViewPagerCallback, baseTemplateView, null, 2, null);
        }
    }

    public static final void createOrLoadTemplateView$lambda$6$lambda$5(BaseTemplateView baseTemplateView, CommonConfig commonConfig) {
        TrackHelper trackHelper = TrackHelper.INSTANCE;
        Context context = baseTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        trackHelper.trackCurrentTemplate(context, commonConfig);
    }

    public static final Integer onLongClick$lambda$10(View view) {
        TemplateApi.Companion companion = TemplateApi.Companion;
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return Integer.valueOf(companion.getInstance(context).isThirdPartyTheme() ? R.string.kg_main_toast_current_template_delete_third_party_theme : R.string.kg_main_toast_current_template_delete);
    }

    public static final void onLongClick$lambda$11(View view, Integer num) {
        ToastUtil toastUtil = ToastUtil.INSTANCE;
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        Intrinsics.checkNotNull(num);
        toastUtil.showText(context, num.intValue());
    }

    public static final void onTemplateReady$lambda$8$lambda$7(MyTemplateViewHolder myTemplateViewHolder, BaseTemplateView baseTemplateView) {
        DeviceScreenshotHelper.INSTANCE.setCurrentHomeTemplateWeak(new WeakReference(myTemplateViewHolder.homeTemplateView));
        CommonConfig commonConfig = myTemplateViewHolder.configForScreenshot;
        if (commonConfig == null) {
            commonConfig = myTemplateViewHolder.myTemplateBindData;
            Intrinsics.checkNotNull(commonConfig);
        }
        myTemplateViewHolder.takeScreenshotForCurrentTemplate(baseTemplateView, commonConfig);
    }

    public static /* synthetic */ void switchHomeLayer$default(MyTemplateViewHolder myTemplateViewHolder, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z2 = true;
        }
        myTemplateViewHolder.switchHomeLayer(z, z2);
    }

    public static final void takeScreenshotForCurrentTemplate$lambda$9(MyTemplateViewHolder myTemplateViewHolder, BaseTemplateView baseTemplateView, CommonConfig commonConfig) {
        CrossViewPagerCallback crossViewPagerCallback = (CrossViewPagerCallback) myTemplateViewHolder.getCallback();
        if (crossViewPagerCallback != null) {
            crossViewPagerCallback.onCurrentTemplateScreenshot(baseTemplateView, null, commonConfig);
        }
        myTemplateViewHolder.isTakeScreenshot = true;
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0128  */
    /* JADX WARN: Code duplicated, block: B:41:0x0138  */
    /* JADX WARN: Code duplicated, block: B:44:0x0145  */
    public final void createOrLoadTemplateView(String str, final CommonConfig commonConfig) {
        WallpaperInfo wallpaperInfo;
        WallpaperInfo wallpaperInfo2;
        setCustomButtonVisible(shouldShowCustomButton());
        String str2 = this.TAG;
        WallpaperInfo wallpaperInfo3 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        Log.i(str2, "createOrLoadTemplateView: wallpaper type = " + (wallpaperInfo3 != null ? wallpaperInfo3.getResourceType() : null));
        if (!Intrinsics.areEqual(str, this.lastTemplateId)) {
            TemplateViewFactory templateViewFactory = TemplateViewFactory.INSTANCE;
            Context context = this.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            FrameLayout.LayoutParams layoutParams = this.layoutPramsForTemplate;
            if (layoutParams == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutPramsForTemplate");
                layoutParams = null;
            }
            float f = this.lockTemplateScale;
            BaseTemplateView baseTemplateViewCreateScaledPreviewTemplateView$default = TemplateViewFactory.createScaledPreviewTemplateView$default(templateViewFactory, context, str, layoutParams, f, f, false, 32, null);
            if (baseTemplateViewCreateScaledPreviewTemplateView$default != null) {
                baseTemplateViewCreateScaledPreviewTemplateView$default.setTemplateSource(-1L);
                ViewGroup.LayoutParams layoutParams2 = this.layoutPramsForTemplate;
                if (layoutParams2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("layoutPramsForTemplate");
                    layoutParams2 = null;
                }
                addTemplateView(baseTemplateViewCreateScaledPreviewTemplateView$default, layoutParams2);
                baseTemplateViewCreateScaledPreviewTemplateView$default.setCombineAnimListener(this);
            } else {
                baseTemplateViewCreateScaledPreviewTemplateView$default = null;
            }
            this.lockTemplateView = baseTemplateViewCreateScaledPreviewTemplateView$default;
            Context context2 = this.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            FrameLayout.LayoutParams layoutParams3 = this.layoutPramsForTemplate;
            if (layoutParams3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutPramsForTemplate");
                layoutParams3 = null;
            }
            float f2 = this.homeTemplateScale;
            BaseTemplateView baseTemplateViewCreateScaledPreviewHomeTemplateView$default = TemplateViewFactory.createScaledPreviewHomeTemplateView$default(templateViewFactory, context2, layoutParams3, f2, f2, false, 16, null);
            baseTemplateViewCreateScaledPreviewHomeTemplateView$default.setAlpha(baseTemplateViewCreateScaledPreviewHomeTemplateView$default.getResources().getFloat(R.dimen.kg_corss_home_alpha));
            float f3 = 2;
            float templateContainerHeight = getTemplateContainerHeight() - (getTemplateContainerPadding() * f3);
            FrameLayout.LayoutParams layoutParams4 = this.layoutPramsForTemplate;
            if (layoutParams4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutPramsForTemplate");
                layoutParams4 = null;
            }
            float f4 = (templateContainerHeight - (layoutParams4.height * this.homeTemplateScale)) - this.homeExtraHeight;
            this.baseHomeTranslationY = f4;
            baseTemplateViewCreateScaledPreviewHomeTemplateView$default.setTranslationY(f4);
            float templateContainerWidth = getTemplateContainerWidth() - (getTemplateContainerPadding() * f3);
            FrameLayout.LayoutParams layoutParams5 = this.layoutPramsForTemplate;
            if (layoutParams5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutPramsForTemplate");
                layoutParams5 = null;
            }
            baseTemplateViewCreateScaledPreviewHomeTemplateView$default.setTranslationX((templateContainerWidth - (layoutParams5.width * this.homeTemplateScale)) / 2.0f);
            baseTemplateViewCreateScaledPreviewHomeTemplateView$default.setTemplateSource(-1L);
            ViewGroup.LayoutParams layoutParams6 = this.layoutPramsForTemplate;
            if (layoutParams6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutPramsForTemplate");
                layoutParams6 = null;
            }
            addHomeTemplateView(baseTemplateViewCreateScaledPreviewHomeTemplateView$default, layoutParams6);
            baseTemplateViewCreateScaledPreviewHomeTemplateView$default.setCombineAnimListener(this);
            this.homeTemplateView = baseTemplateViewCreateScaledPreviewHomeTemplateView$default;
            this.lastTemplateId = str;
        }
        if (commonConfig.getLockscreenInfo().isThirdPartyTheme()) {
            MagicType magicType = MagicType.INSTANCE;
            WallpaperInfo wallpaperInfo4 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            if (magicType.isDepthVideo(wallpaperInfo4 != null ? Integer.valueOf(wallpaperInfo4.getMagicType()) : null)) {
                this.configForScreenshot = commonConfig.deepCopy();
                wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo != null) {
                    wallpaperInfo.setPositionInfo(null);
                }
                wallpaperInfo2 = commonConfig.getHomeInfo().getWallpaperInfo();
                if (wallpaperInfo2 != null) {
                    wallpaperInfo2.setPositionInfo(null);
                }
            }
        } else {
            this.configForScreenshot = commonConfig.deepCopy();
            wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
            if (wallpaperInfo != null) {
                wallpaperInfo.setPositionInfo(null);
            }
            wallpaperInfo2 = commonConfig.getHomeInfo().getWallpaperInfo();
            if (wallpaperInfo2 != null) {
                wallpaperInfo2.setPositionInfo(null);
            }
        }
        BaseTemplateView baseTemplateView = this.homeTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.loadTemplate(commonConfig);
        }
        final BaseTemplateView baseTemplateView2 = this.lockTemplateView;
        if (baseTemplateView2 != null) {
            baseTemplateView2.setExtraParameters(commonConfig.getLockscreenInfo().getClockExtras());
            baseTemplateView2.loadTemplate(commonConfig);
            baseTemplateView2.addMiuiClockViewInitListener(new Runnable() { // from class: com.miui.keyguard.editor.homepage.view.adapter.MyTemplateViewHolder$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    MyTemplateViewHolder.createOrLoadTemplateView$lambda$6$lambda$4(this.f$0, baseTemplateView2);
                }
            });
            Task.run(new Runnable() { // from class: com.miui.keyguard.editor.homepage.view.adapter.MyTemplateViewHolder$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    MyTemplateViewHolder.createOrLoadTemplateView$lambda$6$lambda$5(baseTemplateView2, commonConfig);
                }
            });
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public int getCurrentSelect() {
        return CombineEditListener.DefaultImpls.getCurrentSelect(this);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public int getDialogOffset() {
        return CombineEditListener.DefaultImpls.getDialogOffset(this);
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder
    public View getHomePreview() {
        BaseTemplateView baseTemplateView = this.homeTemplateView;
        Intrinsics.checkNotNull(baseTemplateView);
        return baseTemplateView;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder
    public View getLockPreview() {
        BaseTemplateView baseTemplateView = this.lockTemplateView;
        Intrinsics.checkNotNull(baseTemplateView);
        return baseTemplateView;
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        super.onDestroy(owner);
        BaseTemplateView baseTemplateView = this.lockTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.removeClockLoadListener();
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissBottomSheet() {
        CombineEditListener.DefaultImpls.onDismissBottomSheet(this);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissBottomSheetForScreenChange(Configuration configuration) {
        CombineEditListener.DefaultImpls.onDismissBottomSheetForScreenChange(this, configuration);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissKeyboard() {
        CombineEditListener.DefaultImpls.onDismissKeyboard(this);
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(final View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        Boolean boolValueOf = templateItemBean != null ? Boolean.valueOf(templateItemBean.isSelected()) : null;
        Log.i(this.TAG, "onLongClick isOutsideTouchEvent:" + isOutsideTouchEvent() + " selected:" + boolValueOf + " canLongClick:" + getCanLongClick());
        if (!getCanLongClick() || isOutsideTouchEvent() || Intrinsics.areEqual(boolValueOf, Boolean.FALSE)) {
            return false;
        }
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.homepage.view.adapter.MyTemplateViewHolder$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return MyTemplateViewHolder.onLongClick$lambda$10(v);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.homepage.view.adapter.MyTemplateViewHolder$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MyTemplateViewHolder.onLongClick$lambda$11(v, (Integer) obj);
            }
        });
        return true;
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onPreviewChanged(boolean z, boolean z2) {
        CombineEditListener.DefaultImpls.onPreviewChanged(this, z, z2);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onScrolledChanged(boolean z, boolean z2) {
        CombineEditListener.DefaultImpls.onScrolledChanged(this, z, z2);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onShowBottomSheet() {
        CombineEditListener.DefaultImpls.onShowBottomSheet(this);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onTemplateReady(int i) {
        Log.d(this.TAG, "screenshot ready count=" + this.templateReadyCount.get() + " id=" + i);
        if (this.templateReadyCount.incrementAndGet() == 4) {
            final BaseTemplateView baseTemplateView = this.lockTemplateView;
            if (baseTemplateView != null) {
                baseTemplateView.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.homepage.view.adapter.MyTemplateViewHolder$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        MyTemplateViewHolder.onTemplateReady$lambda$8$lambda$7(this.f$0, baseTemplateView);
                    }
                }, MainFragment.Companion.getTAKE_SCREENSHUT_DELY_TIME());
            }
            BaseTemplateView baseTemplateView2 = this.lockTemplateView;
            if (baseTemplateView2 != null) {
                baseTemplateView2.setCombineAnimListener(null);
            }
            BaseTemplateView baseTemplateView3 = this.homeTemplateView;
            if (baseTemplateView3 != null) {
                baseTemplateView3.setCombineAnimListener(null);
            }
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder, com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderBound(TemplateItemBean templateItemBean, int i) {
        ExtraInfo extraInfo;
        TemplateConfig lockscreenInfo;
        CommonConfig current;
        TemplateConfig lockscreenInfo2;
        CommonConfig current2;
        super.onViewHolderBound(templateItemBean, i);
        Integer loopType = null;
        if (this.myTemplateBindData == null) {
            this.myTemplateBindData = (templateItemBean == null || (current2 = templateItemBean.getCurrent()) == null) ? null : current2.deepCopy();
        }
        CommonConfig commonConfig = this.myTemplateBindData;
        if (commonConfig == null) {
            Log.w(this.TAG, "onViewHolderBound failed: current == null, position = " + i);
            return;
        }
        String templateId = commonConfig.getLockscreenInfo().getClockInfo().getTemplateId();
        if (templateId == null) {
            Log.w(this.TAG, "onViewHolderBound failed: templateId == null");
            return;
        }
        DataUtil dataUtil = DataUtil.INSTANCE;
        WallpaperInfo wallpaperInfo = (templateItemBean == null || (current = templateItemBean.getCurrent()) == null || (lockscreenInfo2 = current.getLockscreenInfo()) == null) ? null : lockscreenInfo2.getWallpaperInfo();
        CommonConfig commonConfig2 = this.myTemplateBindData;
        WallpaperInfo wallpaperInfo2 = (commonConfig2 == null || (lockscreenInfo = commonConfig2.getLockscreenInfo()) == null) ? null : lockscreenInfo.getWallpaperInfo();
        Context context = this.itemView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        dataUtil.resetWallpaperInfoMagicType(wallpaperInfo, wallpaperInfo2, context);
        createOrLoadTemplateView(templateId, commonConfig);
        if (commonConfig.getLockscreenInfo().isThirdPartyWallpaper()) {
            WallpaperInfo wallpaperInfo3 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            if ((wallpaperInfo3 != null ? wallpaperInfo3.getCurrentLockWallpaperSource() : null) != LockWallpaperSource.WALLPAPER_MANAGER) {
                ToastUtil toastUtil = ToastUtil.INSTANCE;
                Context context2 = this.itemView.getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                toastUtil.showText(context2, R.string.kg_tip_cant_get_third_party_wallpaper);
            }
        }
        WallpaperInfo wallpaperInfo4 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        if (wallpaperInfo4 != null && (extraInfo = wallpaperInfo4.getExtraInfo()) != null) {
            loopType = extraInfo.getLoopType();
        }
        setLoopFlagIcon(shouldShowLoopFlagIcon(loopType));
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder, com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderCreated(View itemView) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        super.onViewHolderCreated(itemView);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int i = getRealSizeOutValue().x > 0 ? getRealSizeOutValue().x : displayMetrics.widthPixels;
        int i2 = getRealSizeOutValue().y > 0 ? getRealSizeOutValue().y : displayMetrics.heightPixels;
        Log.i(this.TAG, "realWidth = " + i + ", realHeight = " + i2 + ", heightPixels = " + displayMetrics.heightPixels);
        float templateContainerWidth = (getTemplateContainerWidth() - (((float) 2) * getTemplateContainerPadding())) / ((float) i);
        this.lockTemplateScale = templateContainerWidth;
        this.homeTemplateScale = templateContainerWidth * getHomeScale();
        this.layoutPramsForTemplate = new FrameLayout.LayoutParams(i, i2);
        if (itemView.getContext() instanceof FragmentActivity) {
            Context context = itemView.getContext();
            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type androidx.fragment.app.FragmentActivity");
            ((FragmentActivity) context).getLifecycle().addObserver(this);
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder
    public void onViewHolderSelectChanged(final boolean z) {
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = this.itemView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        EditorActivityViewModel viewModel = companion.getViewModel(context);
        if (viewModel == null || viewModel.isTransformFinished()) {
            switchHomeLayer$default(this, z, false, 2, null);
        } else {
            viewModel.setOnTransformerCallback(new LockScreenTransformerAnimationListener() { // from class: com.miui.keyguard.editor.homepage.view.adapter.MyTemplateViewHolder.onViewHolderSelectChanged.1
                @Override // com.miui.keyguard.editor.view.LockScreenTransformerAnimationListener
                public void onEnterLockScreenTransformerAnimationEnd() {
                    MyTemplateViewHolder.switchHomeLayer$default(MyTemplateViewHolder.this, z, false, 2, null);
                }
            });
        }
        super.onViewHolderSelectChanged(z);
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder
    public boolean shouldShowCustomButton() {
        CommonConfig current;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        String resourceType;
        TemplateItemBean templateItemBean;
        CommonConfig current2;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo2;
        String resourceType2;
        TemplateItemBean templateItemBean2 = (TemplateItemBean) getItemData();
        if (templateItemBean2 == null || (current = templateItemBean2.getCurrent()) == null || (lockscreenInfo = current.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || (resourceType = wallpaperInfo.getResourceType()) == null || (templateItemBean = (TemplateItemBean) getItemData()) == null || (current2 = templateItemBean.getCurrent()) == null || (homeInfo = current2.getHomeInfo()) == null || (wallpaperInfo2 = homeInfo.getWallpaperInfo()) == null || (resourceType2 = wallpaperInfo2.getResourceType()) == null) {
            return true;
        }
        return !Wallpaper.Companion.unSupportToEdit(resourceType, resourceType2);
    }

    public final boolean shouldShowLoopFlagIcon(Integer num) {
        FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
        Context context = this.itemView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        boolean zIsLoop = fashionGalleryHelper.isLoop(context, true, num);
        Log.d(this.TAG, "shouldShowLoopFlagIcon: loopType=" + num + ", isLoop=" + zIsLoop);
        return zIsLoop;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder, com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder
    public void switchHome(boolean z, boolean z2) {
        super.switchHome(z, z2);
        switchHomeLayer(z, z2);
    }

    public final void switchHomeLayer(boolean z, boolean z2) {
        if (this.homeTemplateView == null || z == isShowHome()) {
            return;
        }
        setShowHome(z);
        final float f = z ? this.baseHomeTranslationY + this.homeExtraHeight : this.baseHomeTranslationY - this.homeExtraHeight;
        Folme.clean(this.homeTemplateView);
        if (!z2) {
            BaseTemplateView baseTemplateView = this.homeTemplateView;
            if (baseTemplateView != null) {
                baseTemplateView.setTranslationY(f);
                return;
            }
            return;
        }
        AnimState animStateAdd = new AnimState("anim_to").add(ViewProperty.TRANSLATION_Y, f, new long[0]);
        AnimConfig animConfigAddListeners = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.65d, 0.5d)).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.homepage.view.adapter.MyTemplateViewHolder$switchHomeLayer$animConfig$1
            private final void finishAnim() {
                BaseTemplateView baseTemplateView2 = this.this$0.homeTemplateView;
                if (baseTemplateView2 != null) {
                    baseTemplateView2.setTranslationY(f);
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                finishAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                finishAnim();
            }
        });
        BaseTemplateView baseTemplateView2 = this.homeTemplateView;
        Intrinsics.checkNotNull(baseTemplateView2);
        Folme.use((View) baseTemplateView2).state().to(animStateAdd, animConfigAddListeners);
    }

    public final void takeScreenshotForCurrentTemplate(final BaseTemplateView baseTemplateView, final CommonConfig commonConfig) {
        BaseTemplateView baseTemplateView2;
        Context context = baseTemplateView.getContext();
        if (((context instanceof Activity) && ((Activity) context).isFinishing()) || this.isTakeScreenshot || (baseTemplateView2 = this.lockTemplateView) == null) {
            return;
        }
        baseTemplateView2.post(new Runnable() { // from class: com.miui.keyguard.editor.homepage.view.adapter.MyTemplateViewHolder$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                MyTemplateViewHolder.takeScreenshotForCurrentTemplate$lambda$9(this.f$0, baseTemplateView, commonConfig);
            }
        });
    }
}
