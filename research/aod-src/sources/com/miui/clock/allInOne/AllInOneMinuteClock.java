package com.miui.clock.allInOne;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.classic.ClassicTextAreaView;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.MiuiBlurUtils;
import java.util.TimeZone;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
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
/* JADX INFO: compiled from: AllInOneMinuteClock.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AllInOneMinuteClock extends AllInOneBase {
    public final String clockInstanceName;
    public View mMagazineInfoContainer;

    /* JADX INFO: compiled from: AllInOneMinuteClock.kt */
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

    public AllInOneMinuteClock(@Nullable Context context) {
        super(context);
        this.clockInstanceName = "MINUTE_CLOCK";
    }

    public AllInOneMinuteClock(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.clockInstanceName = "MINUTE_CLOCK";
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Nullable
    public View getBackgroundBlurContainer() {
        AllInOneBaseInfo mClockStyleInfo = getMClockStyleInfo();
        if ((mClockStyleInfo != null ? mClockStyleInfo.getClockDepthType() : null) == AllInOneClockStyle.Region.REGION_2) {
            return resolveBlurBlendContainer();
        }
        return null;
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

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mMagazineInfoContainer = findViewById(R.id.magazine_info_container);
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onTimeZoneUpdate(TimeZone timeZone) {
        this.mCalendar.setTimeZone(timeZone);
        updateTime();
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
            TimeView mMinuteView = getMMinuteView();
            AllInOneBaseInfo mClockStyleInfo6 = getMClockStyleInfo();
            AllInOneBaseInfo mClockStyleInfo7 = getMClockStyleInfo();
            Intrinsics.checkNotNull(mClockStyleInfo7);
            ClockEffectUtils.setClockEffectsView(mMinuteView, mClockStyleInfo6, z, i2, mClockStyleInfo7.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, currentLabAlpha);
            TimeView mColonView = getMColonView();
            AllInOneBaseInfo mClockStyleInfo8 = getMClockStyleInfo();
            AllInOneBaseInfo mClockStyleInfo9 = getMClockStyleInfo();
            Intrinsics.checkNotNull(mClockStyleInfo9);
            ClockEffectUtils.setClockEffectsView(mColonView, mClockStyleInfo8, z, i2, mClockStyleInfo9.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, currentLabAlpha);
        }
        ClockEffectUtils.setClockEffectsView(getMTextArea(), getMClockStyleInfo(), z, i, 0, i3, zIsAODType, zIsFullAODType, currentLabAlpha);
        AllInOneBaseInfo mClockStyleInfo10 = getMClockStyleInfo();
        Intrinsics.checkNotNull(mClockStyleInfo10);
        if ((ClockEffectUtils.isBlurMixType(mClockStyleInfo10.getClockEffect()) || zIsGlassType) && !isToFullAod()) {
            MiuiBlurUtils.addMiBackgroundBlendColor(getMTextArea(), i3, 200);
        }
        ClearableEditText activeSignatureView = getActiveSignatureView();
        AllInOneBaseInfo mClockStyleInfo11 = getMClockStyleInfo();
        AllInOneBaseInfo mClockStyleInfo12 = getMClockStyleInfo();
        Intrinsics.checkNotNull(mClockStyleInfo12);
        ClockEffectUtils.setClockEffectsView(activeSignatureView, mClockStyleInfo11, z, i, mClockStyleInfo12.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, currentLabAlpha);
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
            TimeView mMinuteView = getMMinuteView();
            if (mMinuteView != null) {
                AllInOneBaseInfo mClockStyleInfo2 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo2);
                mMinuteView.setTextColor(mClockStyleInfo2.getFullAodSecondaryColor());
            }
            TimeView mColonView = getMColonView();
            if (mColonView != null) {
                AllInOneBaseInfo mClockStyleInfo3 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo3);
                mColonView.setTextColor(mClockStyleInfo3.getFullAodSecondaryColor());
            }
            ClassicTextAreaView mTextArea = getMTextArea();
            if (mTextArea != null) {
                AllInOneBaseInfo mClockStyleInfo4 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo4);
                mTextArea.setTextColor(mClockStyleInfo4.getDateColor());
            }
        } else {
            TimeView mMinuteView2 = getMMinuteView();
            if (mMinuteView2 != null) {
                AllInOneBaseInfo mClockStyleInfo5 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo5);
                mMinuteView2.setTextColor(mClockStyleInfo5.getSecondaryColor());
            }
            TimeView mColonView2 = getMColonView();
            if (mColonView2 != null) {
                AllInOneBaseInfo mClockStyleInfo6 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo6);
                mColonView2.setTextColor(mClockStyleInfo6.getSecondaryColor());
            }
            ClassicTextAreaView mTextArea2 = getMTextArea();
            if (mTextArea2 != null) {
                AllInOneBaseInfo mClockStyleInfo7 = getMClockStyleInfo();
                Intrinsics.checkNotNull(mClockStyleInfo7);
                mTextArea2.setTextColor(mClockStyleInfo7.getDateColor());
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
                mMinuteView.setVisibility(8);
            }
            FrameLayout mSignatureTextContainer = getMSignatureTextContainer();
            if (mSignatureTextContainer != null) {
                mSignatureTextContainer.setVisibility(0);
            }
            ClassicTextAreaView mTextArea = getMTextArea();
            if (mTextArea != null) {
                mTextArea.setVisibility(0);
            }
            View mTimeAccessibilityOverlay = getMTimeAccessibilityOverlay();
            if (mTimeAccessibilityOverlay == null) {
                return;
            }
            mTimeAccessibilityOverlay.setVisibility(0);
            return;
        }
        if (i != 2) {
            TimeView mMinuteView2 = getMMinuteView();
            if (mMinuteView2 != null) {
                mMinuteView2.setVisibility(0);
            }
            FrameLayout mSignatureTextContainer2 = getMSignatureTextContainer();
            if (mSignatureTextContainer2 != null) {
                mSignatureTextContainer2.setVisibility(0);
            }
            ClassicTextAreaView mTextArea2 = getMTextArea();
            if (mTextArea2 != null) {
                mTextArea2.setVisibility(0);
            }
            View mTimeAccessibilityOverlay2 = getMTimeAccessibilityOverlay();
            if (mTimeAccessibilityOverlay2 == null) {
                return;
            }
            mTimeAccessibilityOverlay2.setVisibility(0);
            return;
        }
        TimeView mMinuteView3 = getMMinuteView();
        if (mMinuteView3 != null) {
            mMinuteView3.setVisibility(0);
        }
        FrameLayout mSignatureTextContainer3 = getMSignatureTextContainer();
        if (mSignatureTextContainer3 != null) {
            mSignatureTextContainer3.setVisibility(8);
        }
        ClassicTextAreaView mTextArea3 = getMTextArea();
        if (mTextArea3 != null) {
            mTextArea3.setVisibility(8);
        }
        View mTimeAccessibilityOverlay3 = getMTimeAccessibilityOverlay();
        if (mTimeAccessibilityOverlay3 == null) {
            return;
        }
        mTimeAccessibilityOverlay3.setVisibility(8);
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        View view = this.mMagazineInfoContainer;
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            ConstraintLayout.LayoutParams layoutParams2 = layoutParams instanceof ConstraintLayout.LayoutParams ? (ConstraintLayout.LayoutParams) layoutParams : null;
            if (layoutParams2 != null) {
                ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_all_in_one_hour_margin_top);
                layoutParams2.setMarginStart(getDimen(R.dimen.miui_oversize_a_date_margin_right));
                view.setLayoutParams(layoutParams2);
            }
        }
    }
}
