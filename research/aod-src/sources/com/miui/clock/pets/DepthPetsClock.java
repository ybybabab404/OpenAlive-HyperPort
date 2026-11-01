package com.miui.clock.pets;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
/* JADX INFO: compiled from: DepthPetsClock.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class DepthPetsClock extends DepthPetsClockBase {
    public DepthPetsTimeView mTimeView;

    /* JADX INFO: compiled from: DepthPetsClock.kt */
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
                iArr[ClockViewType.FULL_WEEK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClockViewType.HOUR1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ClockViewType.HOUR2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ClockViewType.FULL_TIME.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DepthPetsClock(@NotNull Context context, @Nullable AttributeSet attributeSet) {
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
            ClockEffectUtils.clearClockEffectsView(depthPetsTimeView.getTimeHourLeft(), mInfo, z);
            ClockEffectUtils.clearClockEffectsView(depthPetsTimeView.getTimeHourRight(), mInfo, z);
            ClockEffectUtils.clearClockEffectsView(depthPetsTimeView.getTimeMinuteRight(), mInfo, z);
            ClockEffectUtils.clearClockEffectsView(depthPetsTimeView.getTimeMinuteLeft(), mInfo, z);
            ClockEffectUtils.clearClockEffectsView(depthPetsTimeView.getWeekToday(), mInfo, z);
            ClockEffectUtils.clearClockEffectsView(depthPetsTimeView.getWeekBottom(), mInfo, z);
            ClockEffectUtils.clearClockEffectsView(depthPetsTimeView.getTimeDot(), mInfo, z);
            ClockEffectUtils.clearClockEffectsView(depthPetsTimeView.getMMonth(), mInfo, z);
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
            if (depthPetsTimeView2 != null) {
                return depthPetsTimeView2.getWeekToday();
            }
            return null;
        }
        if (i == 3) {
            DepthPetsTimeView depthPetsTimeView3 = this.mTimeView;
            if (depthPetsTimeView3 != null) {
                return depthPetsTimeView3.getTimeHourLeft();
            }
            return null;
        }
        if (i == 4) {
            DepthPetsTimeView depthPetsTimeView4 = this.mTimeView;
            if (depthPetsTimeView4 != null) {
                return depthPetsTimeView4.getTimeHourRight();
            }
            return null;
        }
        if (i != 5) {
            return super.getIClockView(clockViewType);
        }
        DepthPetsTimeView depthPetsTimeView5 = this.mTimeView;
        Intrinsics.checkNotNull(depthPetsTimeView5);
        return depthPetsTimeView5;
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.notification_top_depth_pets);
    }

    @Override // com.miui.clock.pets.DepthPetsClockBase, com.miui.clock.MiuiBaseClock2
    @Nullable
    public View getNotificationClockTopView() {
        return this.mTimeView;
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
        updateViewBackgroundBlurContainer(depthPetsTimeView, dimen, depthPetsTimeView);
        DepthPetsTimeView depthPetsTimeView2 = this.mTimeView;
        ImageView timeHourLeft = depthPetsTimeView2 != null ? depthPetsTimeView2.getTimeHourLeft() : null;
        DepthPetsClockBaseInfo mInfo3 = getMInfo();
        boolean z = !zIsFullAODType && zUpdateTextDark;
        DepthPetsClockBaseInfo mInfo4 = getMInfo();
        Intrinsics.checkNotNull(mInfo4);
        ClockEffectUtils.setClockEffectsView(timeHourLeft, mInfo3, z, i, mInfo4.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        DepthPetsTimeView depthPetsTimeView3 = this.mTimeView;
        ImageView timeHourRight = depthPetsTimeView3 != null ? depthPetsTimeView3.getTimeHourRight() : null;
        DepthPetsClockBaseInfo mInfo5 = getMInfo();
        boolean z2 = !zIsFullAODType && zUpdateTextDark;
        DepthPetsClockBaseInfo mInfo6 = getMInfo();
        Intrinsics.checkNotNull(mInfo6);
        ClockEffectUtils.setClockEffectsView(timeHourRight, mInfo5, z2, i, mInfo6.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        DepthPetsTimeView depthPetsTimeView4 = this.mTimeView;
        ImageView timeMinuteLeft = depthPetsTimeView4 != null ? depthPetsTimeView4.getTimeMinuteLeft() : null;
        DepthPetsClockBaseInfo mInfo7 = getMInfo();
        boolean z3 = !zIsFullAODType && zUpdateTextDark;
        DepthPetsClockBaseInfo mInfo8 = getMInfo();
        Intrinsics.checkNotNull(mInfo8);
        ClockEffectUtils.setClockEffectsView(timeMinuteLeft, mInfo7, z3, i, mInfo8.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        DepthPetsTimeView depthPetsTimeView5 = this.mTimeView;
        ImageView timeMinuteRight = depthPetsTimeView5 != null ? depthPetsTimeView5.getTimeMinuteRight() : null;
        DepthPetsClockBaseInfo mInfo9 = getMInfo();
        boolean z4 = !zIsFullAODType && zUpdateTextDark;
        DepthPetsClockBaseInfo mInfo10 = getMInfo();
        Intrinsics.checkNotNull(mInfo10);
        ClockEffectUtils.setClockEffectsView(timeMinuteRight, mInfo9, z4, i, mInfo10.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        DepthPetsTimeView depthPetsTimeView6 = this.mTimeView;
        TextView weekToday = depthPetsTimeView6 != null ? depthPetsTimeView6.getWeekToday() : null;
        DepthPetsClockBaseInfo mInfo11 = getMInfo();
        boolean z5 = !zIsFullAODType && zUpdateTextDark;
        DepthPetsClockBaseInfo mInfo12 = getMInfo();
        Intrinsics.checkNotNull(mInfo12);
        ClockEffectUtils.setClockEffectsView(weekToday, mInfo11, z5, i, mInfo12.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        DepthPetsTimeView depthPetsTimeView7 = this.mTimeView;
        ImageView weekBottom = depthPetsTimeView7 != null ? depthPetsTimeView7.getWeekBottom() : null;
        DepthPetsClockBaseInfo mInfo13 = getMInfo();
        boolean z6 = !zIsFullAODType && zUpdateTextDark;
        DepthPetsClockBaseInfo mInfo14 = getMInfo();
        Intrinsics.checkNotNull(mInfo14);
        ClockEffectUtils.setClockEffectsView(weekBottom, mInfo13, z6, i, mInfo14.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        DepthPetsTimeView depthPetsTimeView8 = this.mTimeView;
        ImageView timeDot = depthPetsTimeView8 != null ? depthPetsTimeView8.getTimeDot() : null;
        DepthPetsClockBaseInfo mInfo15 = getMInfo();
        boolean z7 = !zIsFullAODType && zUpdateTextDark;
        DepthPetsClockBaseInfo mInfo16 = getMInfo();
        Intrinsics.checkNotNull(mInfo16);
        ClockEffectUtils.setClockEffectsView(timeDot, mInfo15, z7, i2, mInfo16.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
        DepthPetsTimeView depthPetsTimeView9 = this.mTimeView;
        TextView mMonth = depthPetsTimeView9 != null ? depthPetsTimeView9.getMMonth() : null;
        DepthPetsClockBaseInfo mInfo17 = getMInfo();
        boolean z8 = !zIsFullAODType && zUpdateTextDark;
        DepthPetsClockBaseInfo mInfo18 = getMInfo();
        Intrinsics.checkNotNull(mInfo18);
        ClockEffectUtils.setClockEffectsView(mMonth, mInfo17, z8, i, mInfo18.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
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
            layoutParams2.topToTop = 0;
            layoutParams2.leftToLeft = 0;
            layoutParams2.rightToRight = 0;
            layoutParams2.setMargins(0, getDimen(R.dimen.miui_depth_pets_top), 0, 0);
            depthPetsTimeView.setLayoutParams(layoutParams2);
        }
    }
}
