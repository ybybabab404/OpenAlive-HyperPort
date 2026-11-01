package com.miui.keyguard.editor.settings;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TemplateViewFactory;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.util.DataUtil;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.settings.util.DrawableUtilsKt;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;

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
/* JADX INFO: compiled from: SettingsTemplateAdapterAndHoldImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class SettingsMyTemplateViewHolder extends BaseSettingsTemplateViewHolder implements DefaultLifecycleObserver {
    public static final Companion Companion = new Companion(null);
    public BaseTemplateView desktopTemplateView;
    public BaseTemplateView keyguardTemplateView;
    public String lastTemplateId;
    public FrameLayout.LayoutParams layoutPramsForTemplate;
    public float templateScaleX;
    public float templateScaleY;

    /* JADX INFO: compiled from: SettingsTemplateAdapterAndHoldImpl.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsMyTemplateViewHolder(SettingsTemplateDataModelProvider settingsTemplateDataModelProvider, View itemView) {
        super(settingsTemplateDataModelProvider, itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        this.templateScaleX = 1.0f;
        this.templateScaleY = 1.0f;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00f6  */
    private final void createOrLoadTemplateView(String str, CommonConfig commonConfig) {
        CommonConfig commonConfigDeepCopy;
        WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
        Log.i("SettingsMyTemplateViewHolder", "createOrLoadTemplateView templateId = " + str + ", wallpaper type = " + (wallpaperInfo != null ? wallpaperInfo.getResourceType() : null) + ", config = " + commonConfig);
        setCustomButtonVisible(shouldShowCustomButton());
        if (!Intrinsics.areEqual(str, this.lastTemplateId)) {
            TemplateViewFactory templateViewFactory = TemplateViewFactory.INSTANCE;
            Context context = this.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            FrameLayout.LayoutParams layoutParams = this.layoutPramsForTemplate;
            if (layoutParams == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutPramsForTemplate");
                layoutParams = null;
            }
            BaseTemplateView baseTemplateViewCreateScaledPreviewTemplateView$default = TemplateViewFactory.createScaledPreviewTemplateView$default(templateViewFactory, context, str, layoutParams, this.templateScaleX, this.templateScaleY, false, 32, null);
            if (baseTemplateViewCreateScaledPreviewTemplateView$default != null) {
                baseTemplateViewCreateScaledPreviewTemplateView$default.setTemplateSource(-1L);
                baseTemplateViewCreateScaledPreviewTemplateView$default.setEnableOriginWallpaperPlaceholder(false);
                getKeyguardContainer().removeAllViews();
                addTemplateView(getKeyguardContainer(), baseTemplateViewCreateScaledPreviewTemplateView$default);
            } else {
                baseTemplateViewCreateScaledPreviewTemplateView$default = null;
            }
            this.keyguardTemplateView = baseTemplateViewCreateScaledPreviewTemplateView$default;
            Context context2 = this.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            FrameLayout.LayoutParams layoutParams2 = this.layoutPramsForTemplate;
            if (layoutParams2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutPramsForTemplate");
                layoutParams2 = null;
            }
            BaseTemplateView baseTemplateViewCreateScaledPreviewHomeTemplateView$default = TemplateViewFactory.createScaledPreviewHomeTemplateView$default(templateViewFactory, context2, layoutParams2, this.templateScaleX, this.templateScaleY, false, 16, null);
            baseTemplateViewCreateScaledPreviewHomeTemplateView$default.setTemplateSource(-1L);
            baseTemplateViewCreateScaledPreviewHomeTemplateView$default.setEnableOriginWallpaperPlaceholder(false);
            baseTemplateViewCreateScaledPreviewHomeTemplateView$default.disableTouch();
            getDesktopContainer().removeAllViews();
            addTemplateView(getDesktopContainer(), baseTemplateViewCreateScaledPreviewHomeTemplateView$default);
            this.desktopTemplateView = baseTemplateViewCreateScaledPreviewHomeTemplateView$default;
            this.lastTemplateId = str;
        }
        if (commonConfig.getLockscreenInfo().isThirdPartyTheme()) {
            MagicType magicType = MagicType.INSTANCE;
            WallpaperInfo wallpaperInfo2 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            if (magicType.isDepthVideo(wallpaperInfo2 != null ? Integer.valueOf(wallpaperInfo2.getMagicType()) : null)) {
                commonConfigDeepCopy = commonConfig.deepCopy();
            } else {
                commonConfigDeepCopy = null;
            }
        } else {
            commonConfigDeepCopy = commonConfig.deepCopy();
        }
        WallpaperInfo wallpaperInfo3 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        if (wallpaperInfo3 != null) {
            wallpaperInfo3.setPositionInfo(null);
        }
        WallpaperInfo wallpaperInfo4 = commonConfig.getHomeInfo().getWallpaperInfo();
        if (wallpaperInfo4 != null) {
            wallpaperInfo4.setPositionInfo(null);
        }
        CrossViewPagerCallback crossViewPagerCallback = (CrossViewPagerCallback) getCallback();
        if (crossViewPagerCallback != null) {
            BaseTemplateView baseTemplateView = this.keyguardTemplateView;
            if (commonConfigDeepCopy == null) {
                commonConfigDeepCopy = commonConfig;
            }
            crossViewPagerCallback.onMyLockTemplateCreated(baseTemplateView, commonConfigDeepCopy);
        }
        CrossViewPagerCallback crossViewPagerCallback2 = (CrossViewPagerCallback) getCallback();
        if (crossViewPagerCallback2 != null) {
            crossViewPagerCallback2.onMyHomeTemplateCreated(this.desktopTemplateView);
        }
        BaseTemplateView baseTemplateView2 = this.desktopTemplateView;
        if (baseTemplateView2 != null) {
            baseTemplateView2.loadTemplate(commonConfig);
        }
        BaseTemplateView baseTemplateView3 = this.keyguardTemplateView;
        if (baseTemplateView3 != null) {
            baseTemplateView3.setExtraParameters(commonConfig.getLockscreenInfo().getClockExtras());
            baseTemplateView3.loadTemplate(commonConfig);
            for (View view : baseTemplateView3.aboveWallpaperLayers()) {
                view.setVisibility(0);
                view.setAlpha(1.0f);
            }
        }
    }

    public static final Unit onViewHolderBound$lambda$2(SettingsMyTemplateViewHolder settingsMyTemplateViewHolder, boolean z) {
        settingsMyTemplateViewHolder.setLockIsDarkColor(z);
        settingsMyTemplateViewHolder.decorateCustomizeBackground(settingsMyTemplateViewHolder.getCustomizeKeyguardBtn(), settingsMyTemplateViewHolder.getLockIsDarkColor());
        return Unit.INSTANCE;
    }

    public static final Unit onViewHolderBound$lambda$3(SettingsMyTemplateViewHolder settingsMyTemplateViewHolder, boolean z) {
        settingsMyTemplateViewHolder.setHomeIsDarkColor(z);
        settingsMyTemplateViewHolder.decorateCustomizeBackground(settingsMyTemplateViewHolder.getCustomizeDesktopBtn(), settingsMyTemplateViewHolder.getHomeIsDarkColor());
        CrossViewPagerCallback crossViewPagerCallback = (CrossViewPagerCallback) settingsMyTemplateViewHolder.getCallback();
        if (crossViewPagerCallback != null) {
            crossViewPagerCallback.onHomeWallpaperDarkResultReady();
        }
        return Unit.INSTANCE;
    }

    @Override // com.miui.keyguard.editor.settings.ISettingsViewHolder
    public BaseTemplateView getHomeTemplateView() {
        return this.desktopTemplateView;
    }

    @Override // com.miui.keyguard.editor.settings.ISettingsViewHolder
    public BaseTemplateView getLockTemplateView() {
        return this.keyguardTemplateView;
    }

    @Override // com.miui.keyguard.editor.settings.ISettingsViewHolder
    public View getSelfItemView() {
        View itemView = this.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        return itemView;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public View getTargetContentView() {
        View itemView = this.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        return itemView;
    }

    @Override // com.miui.keyguard.editor.settings.BaseSettingsTemplateViewHolder, com.miui.keyguard.editor.settings.ISettingsViewHolder
    public boolean isHomeDarkColor() {
        return getHomeIsDarkColor();
    }

    @Override // com.miui.keyguard.editor.settings.BaseSettingsTemplateViewHolder, com.miui.keyguard.editor.settings.ISettingsViewHolder
    public boolean isLockDarkColor() {
        return getLockIsDarkColor();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        super.onDestroy(owner);
        BaseTemplateView baseTemplateView = this.keyguardTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.removeClockLoadListener();
        }
    }

    @Override // com.miui.keyguard.editor.settings.BaseSettingsTemplateViewHolder, com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderBound(TemplateItemBean templateItemBean, int i) {
        CommonConfig current;
        super.onViewHolderBound(templateItemBean, i);
        Log.w("SettingsMyTemplateViewHolder", "onViewHolderBound: data == " + templateItemBean + ", position = " + i);
        CommonConfig commonConfigDeepCopy = (templateItemBean == null || (current = templateItemBean.getCurrent()) == null) ? null : current.deepCopy();
        if (commonConfigDeepCopy == null) {
            Log.w("SettingsMyTemplateViewHolder", "onViewHolderBound failed: current == null, position = " + i);
            return;
        }
        String templateId = commonConfigDeepCopy.getLockscreenInfo().getClockInfo().getTemplateId();
        DataUtil dataUtil = DataUtil.INSTANCE;
        WallpaperInfo wallpaperInfo = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
        WallpaperInfo wallpaperInfo2 = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
        Context context = this.itemView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        dataUtil.resetWallpaperInfoMagicType(wallpaperInfo, wallpaperInfo2, context);
        createOrLoadTemplateView(templateId, commonConfigDeepCopy);
        BaseTemplateView baseTemplateView = this.keyguardTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.setOnWallpaperDarkResultReadyListener(new Function1() { // from class: com.miui.keyguard.editor.settings.SettingsMyTemplateViewHolder$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return SettingsMyTemplateViewHolder.onViewHolderBound$lambda$2(this.f$0, ((Boolean) obj).booleanValue());
                }
            });
        }
        BaseTemplateView baseTemplateView2 = this.desktopTemplateView;
        if (baseTemplateView2 != null) {
            baseTemplateView2.setOnWallpaperDarkResultReadyListener(new Function1() { // from class: com.miui.keyguard.editor.settings.SettingsMyTemplateViewHolder$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return SettingsMyTemplateViewHolder.onViewHolderBound$lambda$3(this.f$0, ((Boolean) obj).booleanValue());
                }
            });
        }
    }

    @Override // com.miui.keyguard.editor.settings.BaseSettingsTemplateViewHolder, com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderCreated(View itemView) {
        Lifecycle lifecycle;
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        super.onViewHolderCreated(itemView);
        Log.w("SettingsMyTemplateViewHolder", "onViewHolderCreated");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "<get-context>(...)");
        boolean zIsDisplayLandScape = deviceUtil.isDisplayLandScape(context);
        int i = getRealSizeOutValue().x;
        int i2 = getRealSizeOutValue().y;
        int iMax = zIsDisplayLandScape ? Math.max(i, i2) : Math.min(i, i2);
        int iMin = zIsDisplayLandScape ? Math.min(getRealSizeOutValue().x, getRealSizeOutValue().y) : Math.max(getRealSizeOutValue().x, getRealSizeOutValue().y);
        this.templateScaleX = (getTemplateContainerWidth() * 1.0f) / iMax;
        float templateContainerHeight = (getTemplateContainerHeight() * 1.0f) / iMin;
        this.templateScaleY = templateContainerHeight;
        Log.i("SettingsMyTemplateViewHolder", "templateScaleX=" + this.templateScaleX + ", templateScaleY=" + templateContainerHeight + ",realSizeOutValue.x=" + getRealSizeOutValue().x + ",realSizeOutValue.y=" + getRealSizeOutValue().y + ",realWidth=" + iMax + ",realHeight=" + iMin);
        this.layoutPramsForTemplate = new FrameLayout.LayoutParams(iMax, iMin);
        Context context2 = itemView.getContext();
        FragmentActivity fragmentActivity = context2 instanceof FragmentActivity ? (FragmentActivity) context2 : null;
        if (fragmentActivity != null && (lifecycle = fragmentActivity.getLifecycle()) != null) {
            lifecycle.addObserver(this);
        }
        setupTemplateStroke(getKeyguardContainerStroke());
        setupTemplateStroke(getDesktopContainerStroke());
    }

    public final void setupTemplateStroke(View view) {
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "<get-context>(...)");
        float fProvideTemplateRadius = settingsResourceProvider.provideTemplateRadius(context);
        float f = 1;
        view.setForeground(DrawableUtilsKt.createDrawableWithStroke(RangesKt.coerceAtLeast(f / view.getScaleX(), f / view.getScaleY()) * fProvideTemplateRadius, view.getResources().getDimensionPixelSize(R.dimen.kg_settings_template_stroke_width), ResourcesCompat.getColor(view.getResources(), R.color.kg_settings_stroke_color, view.getContext().getTheme())));
        view.setClipToOutline(true);
        RoundOutlineProvider roundOutlineProvider = new RoundOutlineProvider(fProvideTemplateRadius);
        roundOutlineProvider.setRadius(roundOutlineProvider.getRadius() * RangesKt.coerceAtLeast(f / view.getScaleX(), f / view.getScaleY()));
        view.setOutlineProvider(roundOutlineProvider);
        ViewUtil.INSTANCE.setSmoothCornerEnabled(view, true);
    }

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
}
