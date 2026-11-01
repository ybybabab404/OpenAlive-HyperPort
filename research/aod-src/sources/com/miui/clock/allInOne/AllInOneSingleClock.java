package com.miui.clock.allInOne;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.miui.clock.R;
import com.miui.clock.classic.ClassicTextAreaView;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.DeviceConfig;
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
    */
/* JADX INFO: compiled from: AllInOneSingleClock.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AllInOneSingleClock extends AllInOneBase {
    public ViewGroup mClockContainer;
    public ViewGroup mMagazineInfoContainer;
    public boolean updateAllInOneClockRectParams;

    public AllInOneSingleClock(@Nullable Context context) {
        super(context);
        setTAG("AllInOneSingleClock");
    }

    public AllInOneSingleClock(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        setTAG("AllInOneSingleClock");
    }

    public static final void applyAodScaleMode$lambda$3(Rect sourceRect, AllInOneSingleClock this$0, float f) {
        Intrinsics.checkNotNullParameter(sourceRect, "$sourceRect");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        float fCenterX = sourceRect.centerX();
        float f2 = sourceRect.top;
        TimeView[] timeViewArr = {this$0.getMHourView(), this$0.getMMinuteView(), this$0.getMColonView()};
        for (int i = 0; i < 3; i++) {
            TimeView timeView = timeViewArr[i];
            if (timeView != null) {
                timeView.setPivotX(fCenterX);
                timeView.setPivotY(f2);
                timeView.setScaleX(f);
                timeView.setScaleY(f);
            }
        }
        Log.d(this$0.getTAG(), "applyAodScaleMode: scale=" + f + ", top=" + sourceRect.top);
    }

    public final void applyAodScaleMode(final Rect rect) {
        AllInOneBaseInfo mClockStyleInfo = getMClockStyleInfo();
        Intrinsics.checkNotNull(mClockStyleInfo);
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        AllInOneAodLinkageUtils.AodLinkedResult allInOneAodLinkedResult = mClockStyleInfo.getAllInOneAodLinkedResult(context);
        Log.d(getTAG(), "applyAodScaleMode: aodResult=" + allInOneAodLinkedResult + ", sourceRect=" + rect);
        float fWidth = (float) rect.width();
        float fHeight = (float) rect.height();
        if (fWidth == 0.0f || fHeight == 0.0f) {
            super.setClockViewRect(rect);
            return;
        }
        int iHeight = rect.height();
        int aodClockTopPx = allInOneAodLinkedResult.getAodClockTopPx() + getDimen(R.dimen.miui_all_in_one_gap_between_text_area_and_time) + getDimen(R.dimen.miui_all_in_one_text_area_height);
        rect.top = aodClockTopPx;
        rect.bottom = aodClockTopPx + iHeight;
        final float fMin = Math.min(allInOneAodLinkedResult.getAodWidthPx() / fWidth, allInOneAodLinkedResult.getAodHeightPx() / fHeight);
        getLayoutParams().height = aodClockTopPx + ((int) (iHeight * fMin)) + getDimen(R.dimen.miui_time_all_in_one_charge_bottom);
        setLayoutParams(getLayoutParams());
        super.setClockViewRect(rect);
        post(new Runnable() { // from class: com.miui.clock.allInOne.AllInOneSingleClock$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AllInOneSingleClock.applyAodScaleMode$lambda$3(rect, this, fMin);
            }
        });
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
        if (viewType != ClockViewType.FULL_TIME && viewType != ClockViewType.CLOCK_CONTAINER) {
            return super.getIClockView(viewType);
        }
        return this.mClockContainer;
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

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return getDimen(R.dimen.miui_oversize_a_single_margin_top);
    }

    public final boolean getUpdateAllInOneClockRectParams() {
        return this.updateAllInOneClockRectParams;
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mClockContainer = (ViewGroup) findViewById(R.id.clock_container);
        this.mMagazineInfoContainer = (ViewGroup) findViewById(R.id.magazine_info_container);
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setAllInOneClockRectParams(@Nullable AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        this.updateAllInOneClockRectParams = true;
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setAllInOneClockRectParams(@Nullable AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        this.updateAllInOneClockRectParams = true;
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(@Nullable ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        ClearableEditText mSignatureText = getMSignatureText();
        if (mSignatureText != null) {
            mSignatureText.setVisibility(8);
        }
        ViewGroup viewGroup = this.mMagazineInfoContainer;
        Intrinsics.checkNotNull(viewGroup);
        viewGroup.setVisibility(8);
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockViewRect(@NotNull Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        if (getMClockStyleInfo() == null) {
            return;
        }
        Log.d(getTAG(), "setClockViewRect , source rect = " + rect + ", updateAllInOneClockRectParams=" + this.updateAllInOneClockRectParams);
        if (!this.updateAllInOneClockRectParams) {
            if (!DeviceConfig.supportVariableFont()) {
                applyAodScaleMode(rect);
                return;
            }
            AllInOneBaseInfo mClockStyleInfo = getMClockStyleInfo();
            Intrinsics.checkNotNull(mClockStyleInfo);
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            AllInOneAodLinkageUtils.AodLinkedResult allInOneAodLinkedResult = mClockStyleInfo.getAllInOneAodLinkedResult(context);
            Log.d(getTAG(), "aodLinkedResult ,  = " + allInOneAodLinkedResult + ' ');
            int aodClockTopPx = allInOneAodLinkedResult.getAodClockTopPx() + getDimen(R.dimen.miui_all_in_one_gap_between_text_area_and_time) + getDimen(R.dimen.miui_all_in_one_text_area_height);
            rect.top = aodClockTopPx;
            rect.bottom = aodClockTopPx + allInOneAodLinkedResult.getAodHeightPx();
            int iWidth = (rect.width() - allInOneAodLinkedResult.getAodWidthPx()) / 2;
            rect.left += iWidth;
            rect.right -= iWidth;
            getLayoutParams().height = rect.bottom + getDimen(R.dimen.miui_time_all_in_one_charge_bottom);
            setLayoutParams(getLayoutParams());
            setMAllInOneWeight(allInOneAodLinkedResult.getAodWeight());
            Log.d(getTAG(), "setClockViewRect after aodLinked: rect=" + rect + ", offset=" + iWidth + ", aodW=" + allInOneAodLinkedResult.getAodWidthPx() + ", aodH=" + allInOneAodLinkedResult.getAodHeightPx() + ", layoutH=" + getLayoutParams().height);
        }
        super.setClockViewRect(rect);
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

    public final void setUpdateAllInOneClockRectParams(boolean z) {
        this.updateAllInOneClockRectParams = z;
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        if (getMClockStyleInfo() == null) {
            return;
        }
        AllInOneBaseInfo mClockStyleInfo = getMClockStyleInfo();
        if (mClockStyleInfo != null) {
            TimeView mHourView = getMHourView();
            if (mHourView != null) {
                mHourView.setTextColor(mClockStyleInfo.getAodPrimaryColor());
            }
            TimeView mMinuteView = getMMinuteView();
            if (mMinuteView != null) {
                mMinuteView.setTextColor(mClockStyleInfo.getAodSecondaryColor());
            }
            TimeView mColonView = getMColonView();
            if (mColonView != null) {
                mColonView.setTextColor(mClockStyleInfo.getAodSecondaryColor());
            }
            ClassicTextAreaView mTextArea = getMTextArea();
            if (mTextArea != null) {
                mTextArea.setTextColor(mClockStyleInfo.getDateColor());
            }
        }
        updateTime();
    }

    @Override // com.miui.clock.allInOne.AllInOneBase, com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
    }
}
