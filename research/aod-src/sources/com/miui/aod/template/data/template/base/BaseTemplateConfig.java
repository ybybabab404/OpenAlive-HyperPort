package com.miui.aod.template.data.template.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.template.FullAodTemplateView;
import com.miui.aod.template.MiuiClockViewKt;
import com.miui.aod.util.SVGUtils;
import com.miui.clock.MiuiClockController;
import com.miui.clock.MiuiClockView;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.rhombus.MiuiRhombusBase;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.ColorValueInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.color.picker.RhombusAutoColorPicker;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperCallback;
import com.miui.keyguard.editor.utils.DualClockManager;
import com.miui.keyguard.editor.utils.task.Task;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: BaseTemplateConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public abstract class BaseTemplateConfig implements WallpaperCallback {
    public AutoColorPicker autoColorPicker;
    public View backGroundView;
    public View clock;
    public ColorData colorData;
    public final Context context;
    public final ClockBean currentClockBean;
    public Bundle extraParameters;
    public boolean isDualClock;
    public MiuiClockView miuiClockView;
    public ColorValueInfo primaryColorValueInfo;
    public ColorValueInfo secondaryColorValueInfo;
    public boolean successUpdateClockView;
    public final TemplateConfig templateConfig;
    public long templateSource;
    public final FullAodTemplateView templateView;

    public BaseTemplateConfig(TemplateConfig templateConfig, Context context, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.templateConfig = templateConfig;
        this.context = context;
        this.templateView = templateView;
        this.currentClockBean = new ClockBean(getClockStyleType());
        this.templateSource = -2L;
        this.isDualClock = DualClockManager.isDualClockAvailable(context);
    }

    public static final void initClock$lambda$2(BaseTemplateConfig baseTemplateConfig, MiuiClockView miuiClockView) {
        baseTemplateConfig.clock = miuiClockView != null ? miuiClockView.getChildAt(0) : null;
        baseTemplateConfig.updateSvgResource(miuiClockView);
        baseTemplateConfig.onMiuiClockViewCreated();
        MiuiClockViewKt.updateSvgColor(miuiClockView);
    }

    public static /* synthetic */ void startPickColorFromWallpaper$default(BaseTemplateConfig baseTemplateConfig, Bitmap bitmap, boolean z, boolean z2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: startPickColorFromWallpaper");
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        baseTemplateConfig.startPickColorFromWallpaper(bitmap, z, z2);
    }

    public static final ColorData startPickColorFromWallpaper$lambda$4(BaseTemplateConfig baseTemplateConfig, Bitmap bitmap, boolean z) {
        try {
            AutoColorPicker autoColorPicker = baseTemplateConfig.autoColorPicker;
            if (autoColorPicker != null) {
                return AutoColorPicker.pickColor$default(autoColorPicker, bitmap, z, null, 4, null);
            }
            return null;
        } catch (Exception e) {
            Log.w("BaseTemplateConfig", "startPickColorFromWallpaper: " + e);
            return null;
        }
    }

    public static final void startPickColorFromWallpaper$lambda$5(BaseTemplateConfig baseTemplateConfig, boolean z, ColorData colorData) {
        if (colorData == null) {
            Log.w("BaseTemplateConfig", "startPickColorFromWallpaper: data is null");
        } else {
            ColorPickerCache.INSTANCE.cacheMyColorData(colorData);
            baseTemplateConfig.onColorPickComplete(colorData, z);
        }
    }

    public int backContentLayerId() {
        return 0;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.WallpaperCallback
    public void checkNeedRemoveBackground() {
        WallpaperCallback.DefaultImpls.checkNeedRemoveBackground(this);
    }

    public final boolean completeWithCacheColor(boolean z) {
        ColorData colorDataMyColorData = ColorPickerCache.INSTANCE.myColorData();
        if (colorDataMyColorData == null) {
            return false;
        }
        Log.i("BaseTemplateConfig", "startPickColorFromWallpaper: use cache");
        onColorPickComplete(colorDataMyColorData, z);
        return true;
    }

    public AutoColorPicker createAutoColorPicker() {
        return null;
    }

    public int foreContentLayerId() {
        return 0;
    }

    public View getBackContentLayer() {
        return this.templateView.getBackContentLayer();
    }

    public final View getBackGroundView() {
        return this.backGroundView;
    }

    public final View getClock() {
        return this.clock;
    }

    public abstract String getClockStyleType();

    public final Context getContext() {
        return this.context;
    }

    public final ClockBean getCurrentClockBean() {
        return this.currentClockBean;
    }

    public final MiuiClockView getMiuiClockView() {
        return this.miuiClockView;
    }

    public final boolean getSuccessUpdateClockView() {
        return this.successUpdateClockView;
    }

    public final TemplateConfig getTemplateConfig() {
        return this.templateConfig;
    }

    public final long getTemplateSource() {
        return this.templateSource;
    }

    public final FullAodTemplateView getTemplateView() {
        return this.templateView;
    }

    public final void initClock() {
        final MiuiClockView miuiClockView = (MiuiClockView) this.templateView.findViewById(R.id.clock_view);
        this.miuiClockView = miuiClockView;
        if (miuiClockView == null) {
            return;
        }
        miuiClockView.ignoreAccessibilityScale(true);
        miuiClockView.init(this.currentClockBean, Utils.isFsAodSameToLock(miuiClockView.getContext()) ? 32 : 0, true);
        miuiClockView.setBackgroundBlurContainer(this.backGroundView);
        if (MiuiClockViewKt.isRhombusTemplateB(this.currentClockBean)) {
            initColorData();
            miuiClockView.setTimeUpdateListener(new MiuiClockController.TimeUpdateListener() { // from class: com.miui.aod.template.data.template.base.BaseTemplateConfig$$ExternalSyntheticLambda0
                @Override // com.miui.clock.MiuiClockController.TimeUpdateListener
                public final void update() {
                    MiuiClockViewKt.updateSvgColor(miuiClockView);
                }
            });
        }
        miuiClockView.setLoadListener(new MiuiClockController.LoadListener() { // from class: com.miui.aod.template.data.template.base.BaseTemplateConfig$$ExternalSyntheticLambda1
            @Override // com.miui.clock.MiuiClockController.LoadListener
            public final void loadFinish() {
                BaseTemplateConfig.initClock$lambda$2(this.f$0, miuiClockView);
            }
        });
    }

    public final void initColorData() {
        Bitmap currentWallpaper;
        RhombusAutoColorPicker rhombusAutoColorPicker = new RhombusAutoColorPicker(this.context, "rhombus", this.currentClockBean);
        WallpaperInfo wallpaperInfo = this.templateConfig.getWallpaperInfo();
        this.colorData = (wallpaperInfo == null || (currentWallpaper = wallpaperInfo.getCurrentWallpaper()) == null) ? null : rhombusAutoColorPicker.pickColor(currentWallpaper);
    }

    public void initForeClock(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
    }

    public void initTemplateBean(TemplateConfig templateConfig) {
        String string;
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        Bundle bundle = this.extraParameters;
        if (bundle != null && bundle.containsKey("localCity")) {
            ClockBean clockBean = this.currentClockBean;
            Bundle bundle2 = this.extraParameters;
            if (bundle2 == null || (string = bundle2.getString("localCity")) == null) {
                string = null;
            } else {
                Log.i("BaseTemplateConfig", "localCity preset: " + string);
            }
            clockBean.setDualClockLocalCity(string);
        }
        ClockInfo clockInfo = templateConfig.getClockInfo();
        if ((clockInfo != null ? clockInfo.getPrimaryColorValueInfo() : null) == null) {
            templateConfig.getClockInfo().setPrimaryColorValueInfo(new ColorValueInfo(0, 0, 0, 7, null));
        }
        ClockInfo clockInfo2 = templateConfig.getClockInfo();
        if ((clockInfo2 != null ? clockInfo2.getSecondaryColorValueInfo() : null) == null) {
            templateConfig.getClockInfo().setSecondaryColorValueInfo(new ColorValueInfo(0, 0, 0, 7, null));
        }
        ClockInfo clockInfo3 = templateConfig.getClockInfo();
        this.primaryColorValueInfo = clockInfo3 != null ? clockInfo3.getPrimaryColorValueInfo() : null;
        ClockInfo clockInfo4 = templateConfig.getClockInfo();
        this.secondaryColorValueInfo = clockInfo4 != null ? clockInfo4.getSecondaryColorValueInfo() : null;
    }

    public final boolean isDualClock() {
        return this.isDualClock;
    }

    public final boolean isFullAodChangePrimaryColor(TemplateConfig templateConfig) {
        String templateId = templateConfig.getClockInfo().getTemplateId();
        switch (templateId.hashCode()) {
            case -567074186:
                return templateId.equals("magazine_a");
            case -567074185:
                return templateId.equals("magazine_b");
            case -567074184:
                return templateId.equals("magazine_c");
            case 853620882:
                return templateId.equals("classic");
            case 1179492204:
                return templateId.equals("rhombus");
            case 1786073894:
                return templateId.equals("eastern_a");
            case 1786073895:
                return templateId.equals("eastern_b");
            case 1786073896:
                return templateId.equals("eastern_c");
            default:
                return false;
        }
    }

    public final boolean isFullAodChangeSecondaryColor(TemplateConfig templateConfig) {
        String templateId = templateConfig.getClockInfo().getTemplateId();
        switch (templateId.hashCode()) {
            case -567074184:
                return templateId.equals("magazine_c");
            case 853620882:
                return templateId.equals("classic");
            case 1179492204:
                return templateId.equals("rhombus");
            case 1786073894:
                return templateId.equals("eastern_a");
            case 1786073895:
                return templateId.equals("eastern_b");
            default:
                return false;
        }
    }

    public final boolean needDarkClockArea() {
        ClockStyleInfo clockStyleInfo;
        MiuiClockView miuiClockView = this.miuiClockView;
        if (ClockEffectUtils.isBlurMixType((miuiClockView == null || (clockStyleInfo = miuiClockView.getClockStyleInfo()) == null) ? 0 : clockStyleInfo.getClockEffect()) && DeviceConfig.supportBackgroundBlur(this.context)) {
            return true;
        }
        MiuiClockView miuiClockView2 = this.miuiClockView;
        return miuiClockView2 != null ? miuiClockView2.isDualClock() : false;
    }

    public void onColorPickComplete(ColorData colorData, boolean z) {
        ClockInfo clockInfo;
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        boolean darkClockArea = needDarkClockArea() ? false : colorData.getDarkClockArea();
        TemplateConfig templateConfig = this.templateConfig;
        if (templateConfig != null && (clockInfo = templateConfig.getClockInfo()) != null) {
            clockInfo.setDarkClockArea(darkClockArea);
        }
        updateClockColor(new ColorData(0, 0, 0, 0, darkClockArea, darkClockArea, colorData.getClockPalette(), darkClockArea, colorData.getSignaturePalette(), colorData.getRandomColorType(), 15, null));
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.WallpaperCallback
    public void onFirstBindWallpaper(Bitmap bitmap) {
        WallpaperInfo wallpaperInfo = this.templateConfig.getWallpaperInfo();
        Bitmap currentWallpaper = wallpaperInfo != null ? wallpaperInfo.getCurrentWallpaper() : null;
        Log.i("BaseTemplateConfig", "bitmap=" + bitmap + ", wallpaperForPickColor=" + currentWallpaper);
        startPickColorFromWallpaper$default(this, currentWallpaper == null ? bitmap : currentWallpaper, true, false, 4, null);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.WallpaperCallback
    public void onGestureWallpaperEnd(Bitmap bitmap) {
    }

    public void onMiuiClockViewCreated() {
    }

    public final boolean onWindowFocusChanged(boolean z) {
        if (!z) {
            return false;
        }
        boolean zIsDualClockAvailable = DualClockManager.isDualClockAvailable(this.context);
        boolean z2 = this.isDualClock;
        if (zIsDualClockAvailable == z2) {
            return false;
        }
        this.isDualClock = !z2;
        return true;
    }

    public final void setAutoColorPicker(AutoColorPicker autoColorPicker) {
        this.autoColorPicker = autoColorPicker;
    }

    public final void setBackgroundView(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        this.backGroundView = view;
    }

    public final void setSuccessUpdateClockView(boolean z) {
        this.successUpdateClockView = z;
    }

    public final void startPickColorFromWallpaper(final Bitmap bitmap, final boolean z, final boolean z2) {
        if (bitmap == null) {
            Log.w("BaseTemplateConfig", "startPickColorFromWallpaper: bitmap is null");
        } else {
            if (completeWithCacheColor(z)) {
                return;
            }
            Task.create(new Supplier() { // from class: com.miui.aod.template.data.template.base.BaseTemplateConfig$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return BaseTemplateConfig.startPickColorFromWallpaper$lambda$4(this.f$0, bitmap, z2);
                }
            }).post(new Consumer() { // from class: com.miui.aod.template.data.template.base.BaseTemplateConfig$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BaseTemplateConfig.startPickColorFromWallpaper$lambda$5(this.f$0, z, (ColorData) obj);
                }
            });
        }
    }

    public void udpateFullAodTextColor(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        if (isFullAodChangePrimaryColor(templateConfig) && !MiuiClockViewKt.isRhombusTemplateB(this.currentClockBean)) {
            templateConfig.getClockInfo().setPrimaryColor(GlobalColorUtils.transformFullAodColor(templateConfig.getClockInfo().getPrimaryColor()));
        }
        if (!isFullAodChangeSecondaryColor(templateConfig) || MiuiClockViewKt.isRhombusTemplateB(this.currentClockBean)) {
            return;
        }
        templateConfig.getClockInfo().setSecondaryColor(GlobalColorUtils.transformFullAodColor(templateConfig.getClockInfo().getSecondaryColor()));
    }

    public abstract void updateClockColor(ColorData colorData);

    public final void updateSvgResource(MiuiClockView miuiClockView) {
        View iClockView = miuiClockView.getIClockView(ClockViewType.ALL_VIEW);
        MiuiRhombusBase miuiRhombusBase = iClockView instanceof MiuiRhombusBase ? (MiuiRhombusBase) iClockView : null;
        if (miuiRhombusBase == null || miuiRhombusBase.getStyle() == null || miuiClockView.getClockBean() == null) {
            return;
        }
        BaseFontStyle style = miuiRhombusBase.getStyle();
        int style2 = miuiClockView.getClockBean().getStyle();
        if (style2 == 1) {
            style.setUserDefinedResource(SVGUtils.SVG_A_RESOURCE);
            return;
        }
        if (style2 == 2) {
            style.setUserDefinedResource(SVGUtils.SVG_B_RESOURCE);
            style.setUserDefinedColonResource(R.drawable.colon_28);
        } else if (style2 == 3) {
            style.setUserDefinedResource(SVGUtils.SVG_C_RESOURCE);
        } else if (style2 == 4) {
            style.setUserDefinedResource(SVGUtils.SVG_D_RESOURCE);
        } else {
            if (style2 != 7) {
                return;
            }
            style.setUserDefinedResource(SVGUtils.SVG_G_RESOURCE);
        }
    }
}
