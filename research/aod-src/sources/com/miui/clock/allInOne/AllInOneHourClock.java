package com.miui.clock.allInOne;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.clock.classic.ClassicTextAreaView;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.MiuiBlurUtils;
import java.util.TimeZone;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.androidbasewidget.widget.ClearableEditText;
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
/* JADX INFO: compiled from: AllInOneHourClock.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AllInOneHourClock extends AllInOneBase {
    public final String clockInstanceName;

    /* JADX INFO: compiled from: AllInOneHourClock.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AllInOneClockStyle.Region.values().length];
            try {
                iArr[AllInOneClockStyle.Region.REGION_1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AllInOneClockStyle.Region.REGION_2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public AllInOneHourClock(@Nullable Context context) {
        super(context);
        this.clockInstanceName = "HOUR_CLOCK";
    }

    public AllInOneHourClock(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.clockInstanceName = "HOUR_CLOCK";
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Nullable
    public View getBackgroundBlurContainer() {
        AllInOneBaseInfo mClockStyleInfo = getMClockStyleInfo();
        if ((mClockStyleInfo != null ? mClockStyleInfo.getClockDepthType() : null) == AllInOneClockStyle.Region.REGION_2) {
            return null;
        }
        return resolveBlurBlendContainer();
    }

    @NotNull
    public final String getClockInstanceName() {
        return this.clockInstanceName;
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType viewType) {
        Intrinsics.checkNotNullParameter(viewType, "viewType");
        View mTimeGroup = getMTimeGroup();
        if (mTimeGroup != null) {
            if (viewType != ClockViewType.FULL_HOUR || !DateFormatUtils.isSystemUI(getContext())) {
                mTimeGroup = null;
            }
            if (mTimeGroup != null) {
                return mTimeGroup;
            }
        }
        return super.getIClockView(viewType);
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onTimeZoneUpdate(TimeZone timeZone) {
        this.mCalendar.setTimeZone(timeZone);
        updateTime();
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(@Nullable ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        boolean currentTextDark;
        super.updateClockBlendColor(i, i2, i3, i4);
        if (!DeviceConfig.supportBackgroundBlur(this.mContext)) {
            updateClockColor(i, i2);
            return;
        }
        AllInOneBaseInfo mClockStyleInfo = getMClockStyleInfo();
        Intrinsics.checkNotNull(mClockStyleInfo);
        boolean zIsAODType = ClockStyleInfo.isAODType(mClockStyleInfo.getDisplayType());
        AllInOneBaseInfo mClockStyleInfo2 = getMClockStyleInfo();
        Intrinsics.checkNotNull(mClockStyleInfo2);
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(mClockStyleInfo2.getDisplayType());
        if (zIsFullAODType) {
            currentTextDark = false;
        } else {
            AllInOneBaseInfo mClockStyleInfo3 = getMClockStyleInfo();
            Intrinsics.checkNotNull(mClockStyleInfo3);
            currentTextDark = mClockStyleInfo3.getCurrentTextDark();
        }
        boolean z = currentTextDark;
        AllInOneBaseInfo mClockStyleInfo4 = getMClockStyleInfo();
        Intrinsics.checkNotNull(mClockStyleInfo4);
        int currentLabAlpha = mClockStyleInfo4.getCurrentLabAlpha();
        AllInOneBaseInfo mClockStyleInfo5 = getMClockStyleInfo();
        Intrinsics.checkNotNull(mClockStyleInfo5);
        boolean zIsGlassType = ClockEffectUtils.isGlassType(mClockStyleInfo5.getClockEffect());
        if (!zIsGlassType) {
            TimeView mHourView = getMHourView();
            AllInOneBaseInfo mClockStyleInfo6 = getMClockStyleInfo();
            AllInOneBaseInfo mClockStyleInfo7 = getMClockStyleInfo();
            Intrinsics.checkNotNull(mClockStyleInfo7);
            ClockEffectUtils.setClockEffectsView(mHourView, mClockStyleInfo6, z, i, mClockStyleInfo7.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, currentLabAlpha);
            TimeView mMinuteView = getMMinuteView();
            AllInOneBaseInfo mClockStyleInfo8 = getMClockStyleInfo();
            AllInOneBaseInfo mClockStyleInfo9 = getMClockStyleInfo();
            Intrinsics.checkNotNull(mClockStyleInfo9);
            ClockEffectUtils.setClockEffectsView(mMinuteView, mClockStyleInfo8, z, i2, mClockStyleInfo9.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, currentLabAlpha);
            TimeView mColonView = getMColonView();
            AllInOneBaseInfo mClockStyleInfo10 = getMClockStyleInfo();
            AllInOneBaseInfo mClockStyleInfo11 = getMClockStyleInfo();
            Intrinsics.checkNotNull(mClockStyleInfo11);
            ClockEffectUtils.setClockEffectsView(mColonView, mClockStyleInfo10, z, i2, mClockStyleInfo11.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, currentLabAlpha);
        }
        ClockEffectUtils.setClockEffectsView(getMTextArea(), getMClockStyleInfo(), z, i, 0, i3, zIsAODType, zIsFullAODType, currentLabAlpha);
        AllInOneBaseInfo mClockStyleInfo12 = getMClockStyleInfo();
        Intrinsics.checkNotNull(mClockStyleInfo12);
        if ((ClockEffectUtils.isBlurMixType(mClockStyleInfo12.getClockEffect()) || zIsGlassType) && !isToFullAod()) {
            MiuiBlurUtils.addMiBackgroundBlendColor(getMTextArea(), i3, 200);
        }
        ClearableEditText activeSignatureView = getActiveSignatureView();
        AllInOneBaseInfo mClockStyleInfo13 = getMClockStyleInfo();
        AllInOneBaseInfo mClockStyleInfo14 = getMClockStyleInfo();
        Intrinsics.checkNotNull(mClockStyleInfo14);
        ClockEffectUtils.setClockEffectsView(activeSignatureView, mClockStyleInfo13, z, i, mClockStyleInfo14.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, currentLabAlpha);
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        super.updateClockColor(i, i2);
        updatePrimaryColor(i);
        TimeView mMinuteView = getMMinuteView();
        if (mMinuteView != null) {
            mMinuteView.setTextColor(i2);
        }
        TimeView mColonView = getMColonView();
        if (mColonView != null) {
            mColonView.setTextColor(i2);
        }
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        if (getMClockStyleInfo() == null) {
            return;
        }
        AllInOneBaseInfo mClockStyleInfo = getMClockStyleInfo();
        Intrinsics.checkNotNull(mClockStyleInfo);
        if (ClockStyleInfo.isFullAODType(mClockStyleInfo.getDisplayType())) {
            TimeView mHourView = getMHourView();
            if (mHourView != null) {
                AllInOneBaseInfo mClockStyleInfo2 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo2);
                mHourView.setTextColor(mClockStyleInfo2.getFullAodPrimaryColor());
            }
            TimeView mMinuteView = getMMinuteView();
            if (mMinuteView != null) {
                AllInOneBaseInfo mClockStyleInfo3 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo3);
                mMinuteView.setTextColor(mClockStyleInfo3.getFullAodSecondaryColor());
            }
            TimeView mColonView = getMColonView();
            if (mColonView != null) {
                AllInOneBaseInfo mClockStyleInfo4 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo4);
                mColonView.setTextColor(mClockStyleInfo4.getFullAodSecondaryColor());
            }
            ClassicTextAreaView mTextArea = getMTextArea();
            if (mTextArea != null) {
                AllInOneBaseInfo mClockStyleInfo5 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo5);
                mTextArea.setTextColor(mClockStyleInfo5.getDateColor());
            }
        } else {
            TimeView mHourView2 = getMHourView();
            if (mHourView2 != null) {
                AllInOneBaseInfo mClockStyleInfo6 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo6);
                mHourView2.setTextColor(mClockStyleInfo6.getPrimaryColor());
            }
            TimeView mMinuteView2 = getMMinuteView();
            if (mMinuteView2 != null) {
                AllInOneBaseInfo mClockStyleInfo7 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo7);
                mMinuteView2.setTextColor(mClockStyleInfo7.getSecondaryColor());
            }
            TimeView mColonView2 = getMColonView();
            if (mColonView2 != null) {
                AllInOneBaseInfo mClockStyleInfo8 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo8);
                mColonView2.setTextColor(mClockStyleInfo8.getSecondaryColor());
            }
            ClassicTextAreaView mTextArea2 = getMTextArea();
            if (mTextArea2 != null) {
                AllInOneBaseInfo mClockStyleInfo9 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo9);
                mTextArea2.setTextColor(mClockStyleInfo9.getDateColor());
            }
        }
        updateTime();
    }

    @Override // com.miui.clock.allInOne.AllInOneBase
    public void updateDepthContentVisibility() {
        if (getMClockStyleInfo() == null) {
            return;
        }
        AllInOneBaseInfo mClockStyleInfo = getMClockStyleInfo();
        AllInOneClockStyle.Region clockDepthType = mClockStyleInfo != null ? mClockStyleInfo.getClockDepthType() : null;
        int i = clockDepthType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[clockDepthType.ordinal()];
        if (i == 1) {
            TimeView mMinuteView = getMMinuteView();
            if (mMinuteView != null) {
                mMinuteView.setVisibility(0);
            }
            FrameLayout mSignatureTextContainer = getMSignatureTextContainer();
            if (mSignatureTextContainer != null) {
                mSignatureTextContainer.setVisibility(8);
            }
            ClassicTextAreaView mTextArea = getMTextArea();
            if (mTextArea != null) {
                mTextArea.setVisibility(8);
            }
            View mTimeAccessibilityOverlay = getMTimeAccessibilityOverlay();
            if (mTimeAccessibilityOverlay == null) {
                return;
            }
            mTimeAccessibilityOverlay.setVisibility(8);
            return;
        }
        if (i != 2) {
            TimeView mMinuteView2 = getMMinuteView();
            if (mMinuteView2 != null) {
                mMinuteView2.setVisibility(8);
            }
            FrameLayout mSignatureTextContainer2 = getMSignatureTextContainer();
            if (mSignatureTextContainer2 != null) {
                mSignatureTextContainer2.setVisibility(8);
            }
            ClassicTextAreaView mTextArea2 = getMTextArea();
            if (mTextArea2 != null) {
                mTextArea2.setVisibility(8);
            }
            View mTimeAccessibilityOverlay2 = getMTimeAccessibilityOverlay();
            if (mTimeAccessibilityOverlay2 == null) {
                return;
            }
            mTimeAccessibilityOverlay2.setVisibility(8);
            return;
        }
        TimeView mMinuteView3 = getMMinuteView();
        if (mMinuteView3 != null) {
            mMinuteView3.setVisibility(8);
        }
        FrameLayout mSignatureTextContainer3 = getMSignatureTextContainer();
        if (mSignatureTextContainer3 != null) {
            mSignatureTextContainer3.setVisibility(0);
        }
        ClassicTextAreaView mTextArea3 = getMTextArea();
        if (mTextArea3 != null) {
            mTextArea3.setVisibility(0);
        }
        View mTimeAccessibilityOverlay3 = getMTimeAccessibilityOverlay();
        if (mTimeAccessibilityOverlay3 == null) {
            return;
        }
        mTimeAccessibilityOverlay3.setVisibility(0);
    }

    public void updatePrimaryColor(int i) {
        TimeView mHourView = getMHourView();
        if (mHourView != null) {
            mHourView.setTextColor(i);
        }
    }
}
