package com.miui.aod.template.data.template.classicclock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.widget.FrameLayout;
import com.miui.aod.R;
import com.miui.aod.template.FullAodTemplateView;
import com.miui.aod.template.data.template.base.ColorPickerCache;
import com.miui.clock.MiuiClockView;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.picker.AllInOneAutoColorPicker;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.utils.task.Task;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: AllInOneTemplateConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AllInOneTemplateConfig extends AbstractClassicTemplateConfig {
    public static final Companion Companion = new Companion(null);
    public AllInOneAutoColorPicker allInOneColorPicker;
    public Rect colorPickupRect;
    public final ClockBean foreClockBean;
    public MiuiClockView foreMiuiClockView;

    /* JADX INFO: compiled from: AllInOneTemplateConfig.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AllInOneTemplateConfig(TemplateConfig config, Context context, FullAodTemplateView templateView) {
        super(config, context, templateView);
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.foreClockBean = new ClockBean("all_in_one_minute");
    }

    public static final ColorData onFirstBindWallpaper$lambda$2(AllInOneTemplateConfig allInOneTemplateConfig, Bitmap bitmap, Rect rect) {
        AllInOneAutoColorPicker allInOneAutoColorPicker = allInOneTemplateConfig.allInOneColorPicker;
        if (allInOneAutoColorPicker != null) {
            return allInOneAutoColorPicker.pickColor(bitmap, rect);
        }
        return null;
    }

    public static final void onFirstBindWallpaper$lambda$3(AllInOneTemplateConfig allInOneTemplateConfig, ColorData colorData) {
        if (colorData != null) {
            ColorPickerCache.INSTANCE.cacheMyColorData(colorData);
            allInOneTemplateConfig.onColorPickComplete(colorData, true);
        }
    }

    public static final ColorData onMiuiClockViewCreated$lambda$6(AllInOneTemplateConfig allInOneTemplateConfig, Bitmap bitmap, Rect rect) {
        AllInOneAutoColorPicker allInOneAutoColorPicker = allInOneTemplateConfig.allInOneColorPicker;
        if (allInOneAutoColorPicker != null) {
            return allInOneAutoColorPicker.pickColor(bitmap, rect);
        }
        return null;
    }

    public static final void onMiuiClockViewCreated$lambda$7(AllInOneTemplateConfig allInOneTemplateConfig, ColorData colorData) {
        if (colorData != null) {
            ColorPickerCache.INSTANCE.cacheMyColorData(colorData);
            allInOneTemplateConfig.onColorPickComplete(colorData, false);
        }
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public int backContentLayerId() {
        return R.layout.full_aod_pre_template_view;
    }

    public final void copyExtendedInfoToClockBean(ClockBean clockBean, ClockInfo clockInfo) {
        clockBean.setClockTopRatio(clockInfo.getClockTopRatio());
        clockBean.setClockBottomRatio(clockInfo.getClockBottomRatio());
        clockBean.setTimeWidth(clockInfo.getTimeWidth());
        clockBean.setTimeHeight(clockInfo.getTimeHeight());
        clockBean.setClockDepthType(AllInOneClockStyle.toRegion(clockInfo.getClockDepthType()));
        clockBean.setGlassTransparency(Float.valueOf(clockInfo.getGlassTransparency()));
        clockBean.setFontStyle(clockInfo.getFontStyle());
        clockBean.setHollowStyle(clockInfo.getHollowStyle());
        clockBean.setDoubleRow(clockInfo.isDoubleRow());
        clockBean.setColonShow(clockInfo.isColonShow());
        clockBean.setSkipScreenTransform(clockInfo.isSkipScreenTransform());
        Log.d("AllInOneTemplateConfig", "[CopyExtInfo] target=" + clockBean.getTemplateId() + ": topRatio=" + clockInfo.getClockTopRatio() + ", bottomRatio=" + clockInfo.getClockBottomRatio() + ", timeW=" + clockInfo.getTimeWidth() + ", timeH=" + clockInfo.getTimeHeight() + ", depthType=" + clockInfo.getClockDepthType() + ", glassTrans=" + clockInfo.getGlassTransparency() + ", font=" + clockInfo.getFontStyle() + ", hollow=" + clockInfo.getHollowStyle() + ", doubleRow=" + clockInfo.isDoubleRow() + ", colonShow=" + clockInfo.isColonShow());
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public AutoColorPicker createAutoColorPicker() {
        AllInOneAutoColorPicker allInOneAutoColorPicker = new AllInOneAutoColorPicker(getContext(), getClockStyleType(), getCurrentClockBean());
        this.allInOneColorPicker = allInOneAutoColorPicker;
        return allInOneAutoColorPicker;
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public int foreContentLayerId() {
        return R.layout.full_aod_pre_force_clock;
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public String getClockStyleType() {
        return "all_in_one";
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void initForeClock(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.foreMiuiClockView = (MiuiClockView) templateView.findViewById(R.id.fore_clock);
    }

    @Override // com.miui.aod.template.data.template.classicclock.AbstractClassicTemplateConfig, com.miui.aod.template.data.template.base.EffectsTemplateConfig, com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void initTemplateBean(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initTemplateBean(templateConfig);
        ClockInfo clockInfo = templateConfig.getClockInfo();
        copyExtendedInfoToClockBean(getCurrentClockBean(), clockInfo);
        this.foreClockBean.setClockWeight(clockInfo.getClockWeight());
        this.foreClockBean.setStyle(clockInfo.getStyle());
        this.foreClockBean.setClassicLine1(clockInfo.getClassicLine1());
        this.foreClockBean.setClassicLine5(clockInfo.getClassicLine5());
        this.foreClockBean.setClassicSignature(clockInfo.getClassicSignature());
        this.foreClockBean.setPrimaryColor(clockInfo.getPrimaryColor());
        this.foreClockBean.setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
        this.foreClockBean.setSecondaryColor(clockInfo.getSecondaryColor());
        this.foreClockBean.setAutoSecondaryColor(clockInfo.isAutoSecondaryColor());
        this.foreClockBean.setBlendColor(clockInfo.getBlendColor());
        this.foreClockBean.setSecondaryBlendColor(clockInfo.getSecondaryBlendColor());
        this.foreClockBean.setClockEffect(clockInfo.getClockEffect());
        copyExtendedInfoToClockBean(this.foreClockBean, clockInfo);
    }

    @Override // com.miui.aod.template.data.template.base.BaseTemplateConfig, com.miui.keyguard.editor.edit.wallpaper.WallpaperCallback
    public void onFirstBindWallpaper(final Bitmap bitmap) {
        Bitmap currentWallpaper;
        if (isDualClock()) {
            super.onFirstBindWallpaper(bitmap);
            return;
        }
        WallpaperInfo wallpaperInfo = getTemplateConfig().getWallpaperInfo();
        if (wallpaperInfo != null && (currentWallpaper = wallpaperInfo.getCurrentWallpaper()) != null) {
            bitmap = currentWallpaper;
        }
        final Rect rect = this.colorPickupRect;
        if (bitmap == null || rect == null || completeWithCacheColor(true)) {
            return;
        }
        Task.create(new Supplier() { // from class: com.miui.aod.template.data.template.classicclock.AllInOneTemplateConfig$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return AllInOneTemplateConfig.onFirstBindWallpaper$lambda$2(this.f$0, bitmap, rect);
            }
        }).post(new Consumer() { // from class: com.miui.aod.template.data.template.classicclock.AllInOneTemplateConfig$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AllInOneTemplateConfig.onFirstBindWallpaper$lambda$3(this.f$0, (ColorData) obj);
            }
        });
    }

    @Override // com.miui.aod.template.data.template.classicclock.AbstractClassicTemplateConfig, com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void onMiuiClockViewCreated() {
        super.onMiuiClockViewCreated();
        MiuiClockView miuiClockView = this.foreMiuiClockView;
        if (miuiClockView != null) {
            miuiClockView.setVisibility(!miuiClockView.isDualClock() ? 0 : 8);
        }
        if (!isDualClock()) {
            getTemplateConfig();
            MiuiClockView miuiClockView2 = this.foreMiuiClockView;
            if (miuiClockView2 != null) {
                miuiClockView2.init(this.foreClockBean, 32, false);
            }
            MiuiClockView miuiClockView3 = this.foreMiuiClockView;
            if (miuiClockView3 != null) {
                miuiClockView3.setBackgroundBlurContainer(getBackGroundView());
            }
            MiuiClockView miuiClockView4 = getMiuiClockView();
            final Rect rectCalculateColorPickupRect = miuiClockView4 != null ? miuiClockView4.calculateColorPickupRect(Boolean.TRUE) : null;
            this.colorPickupRect = rectCalculateColorPickupRect;
            WallpaperInfo wallpaperInfo = getTemplateConfig().getWallpaperInfo();
            final Bitmap currentWallpaper = wallpaperInfo != null ? wallpaperInfo.getCurrentWallpaper() : null;
            if (rectCalculateColorPickupRect != null && currentWallpaper != null && this.allInOneColorPicker != null) {
                Task.create(new Supplier() { // from class: com.miui.aod.template.data.template.classicclock.AllInOneTemplateConfig$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return AllInOneTemplateConfig.onMiuiClockViewCreated$lambda$6(this.f$0, currentWallpaper, rectCalculateColorPickupRect);
                    }
                }).post(new Consumer() { // from class: com.miui.aod.template.data.template.classicclock.AllInOneTemplateConfig$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AllInOneTemplateConfig.onMiuiClockViewCreated$lambda$7(this.f$0, (ColorData) obj);
                    }
                });
            } else if (rectCalculateColorPickupRect == null && currentWallpaper != null) {
                super.onFirstBindWallpaper(currentWallpaper);
            }
        }
        if (getSuccessUpdateClockView()) {
            return;
        }
        completeWithCacheColor(false);
    }

    @Override // com.miui.aod.template.data.template.classicclock.AbstractClassicTemplateConfig, com.miui.aod.template.data.template.base.BaseTemplateConfig
    public void updateClockColor(ColorData colorData) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        MiuiClockView miuiClockView = getMiuiClockView();
        boolean z = false;
        if (miuiClockView != null) {
            miuiClockView.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        MiuiClockView miuiClockView2 = this.foreMiuiClockView;
        if (miuiClockView2 != null) {
            miuiClockView2.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        if (getMiuiClockView() != null && this.foreMiuiClockView != null) {
            z = true;
        }
        setSuccessUpdateClockView(z);
    }
}
