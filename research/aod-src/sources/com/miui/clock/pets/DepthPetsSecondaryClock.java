package com.miui.clock.pets;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.palette.ColorConfig;
import com.miui.clock.utils.ClockEffectUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
/* JADX INFO: compiled from: DepthPetsSecondaryClock.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class DepthPetsSecondaryClock extends DepthPetsClockBase {
    public DepthPetsTimeView mTimeView;

    /* JADX INFO: compiled from: DepthPetsSecondaryClock.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            try {
                iArr[ClockViewType.FULL_DATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClockViewType.FULL_TIME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClockViewType.FULL_WEEK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DepthPetsSecondaryClock(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        DepthPetsTimeView depthPetsTimeView = this.mTimeView;
        Intrinsics.checkNotNull(depthPetsTimeView);
        DepthPetsClockBaseInfo mInfo = getMInfo();
        if (mInfo != null) {
            ClockEffectUtils.clearClockEffectsContainer(depthPetsTimeView, mInfo, z);
            ClockEffectUtils.clearClockEffectsView(depthPetsTimeView.getMMonth(), mInfo, z);
            ClockEffectUtils.clearClockEffectsView(depthPetsTimeView.getWeekToday(), mInfo, z);
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Nullable
    public View getBackgroundBlurContainer() {
        return this.mTimeView;
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = clockViewType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[clockViewType.ordinal()];
        if (i == 1) {
            DepthPetsTimeView depthPetsTimeView = this.mTimeView;
            if (depthPetsTimeView != null) {
                return depthPetsTimeView.getMMonth();
            }
            return null;
        }
        if (i == 2) {
            DepthPetsTimeView depthPetsTimeView2 = this.mTimeView;
            Intrinsics.checkNotNull(depthPetsTimeView2);
            return depthPetsTimeView2;
        }
        if (i != 3) {
            return super.getIClockView(clockViewType);
        }
        DepthPetsTimeView depthPetsTimeView3 = this.mTimeView;
        if (depthPetsTimeView3 != null) {
            return depthPetsTimeView3.getWeekToday();
        }
        return null;
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        View viewFindViewById = findViewById(R.id.clock_time);
        Intrinsics.checkNotNull(viewFindViewById, "null cannot be cast to non-null type com.miui.clock.pets.DepthPetsTimeView");
        this.mTimeView = (DepthPetsTimeView) viewFindViewById;
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(@Nullable ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        DepthPetsTimeView depthPetsTimeView = this.mTimeView;
        if (depthPetsTimeView != null) {
            depthPetsTimeView.setClockInfo(clockStyleInfo);
        }
        updateTime();
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        super.updateClockBlendColor(i, i2, i3, i4);
        DepthPetsClockBaseInfo mInfo = getMInfo();
        Intrinsics.checkNotNull(mInfo);
        boolean zIsAODType = ClockStyleInfo.isAODType(mInfo.getDisplayType());
        DepthPetsClockBaseInfo mInfo2 = getMInfo();
        Intrinsics.checkNotNull(mInfo2);
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(mInfo2.getDisplayType());
        ColorConfig colorConfig = getPaletteManager().getColorConfig();
        boolean zUpdateTextDark = updateTextDark(colorConfig != null ? colorConfig.getTextDark() : false);
        ColorConfig colorConfig2 = getPaletteManager().getColorConfig();
        int textDarkAlpha = getTextDarkAlpha(colorConfig2 != null ? colorConfig2.getTextDark() : false);
        int dimen = getDimen(R.dimen.miui_magazine_c_clock_blur_radius);
        DepthPetsTimeView depthPetsTimeView = this.mTimeView;
        View weekToday = depthPetsTimeView != null ? depthPetsTimeView.getWeekToday() : null;
        DepthPetsTimeView depthPetsTimeView2 = this.mTimeView;
        updateViewBackgroundBlurContainer(depthPetsTimeView, dimen, weekToday, depthPetsTimeView2 != null ? depthPetsTimeView2.getMMonth() : null);
        DepthPetsTimeView depthPetsTimeView3 = this.mTimeView;
        TextView mMonth = depthPetsTimeView3 != null ? depthPetsTimeView3.getMMonth() : null;
        DepthPetsClockBaseInfo mInfo3 = getMInfo();
        boolean z = !zIsFullAODType && zUpdateTextDark;
        DepthPetsClockBaseInfo mInfo4 = getMInfo();
        Intrinsics.checkNotNull(mInfo4);
        ClockEffectUtils.setClockEffectsView(mMonth, mInfo3, z, i2, mInfo4.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
        DepthPetsTimeView depthPetsTimeView4 = this.mTimeView;
        TextView weekToday2 = depthPetsTimeView4 != null ? depthPetsTimeView4.getWeekToday() : null;
        DepthPetsClockBaseInfo mInfo5 = getMInfo();
        boolean z2 = !zIsFullAODType && zUpdateTextDark;
        DepthPetsClockBaseInfo mInfo6 = getMInfo();
        Intrinsics.checkNotNull(mInfo6);
        ClockEffectUtils.setClockEffectsView(weekToday2, mInfo5, z2, i2, mInfo6.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        DepthPetsTimeView depthPetsTimeView;
        if (getMInfo() == null || (depthPetsTimeView = this.mTimeView) == null) {
            return;
        }
        depthPetsTimeView.updateColor(i, i2);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        DepthPetsTimeView depthPetsTimeView = this.mTimeView;
        if (depthPetsTimeView != null) {
            depthPetsTimeView.updateTime(this.m24HourFormat, this.mCalendar);
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        DepthPetsTimeView depthPetsTimeView = this.mTimeView;
        if (depthPetsTimeView != null) {
            depthPetsTimeView.updateViewsLayoutParams();
            ViewGroup.LayoutParams layoutParams = depthPetsTimeView.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
            ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(R.dimen.miui_depth_pets_height);
            ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(R.dimen.miui_depth_pets_width);
            layoutParams2.topToTop = 0;
            layoutParams2.leftToLeft = 0;
            layoutParams2.rightToRight = 0;
            layoutParams2.setMargins(0, getDimen(R.dimen.miui_depth_pets_top), 0, 0);
            depthPetsTimeView.setLayoutParams(layoutParams2);
        }
    }
}
