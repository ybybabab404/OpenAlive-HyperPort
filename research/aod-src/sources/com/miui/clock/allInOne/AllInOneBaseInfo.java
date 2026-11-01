package com.miui.clock.allInOne;

import android.content.Context;
import android.util.Log;
import com.miui.clock.R;
import com.miui.clock.hct.Hct;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.MiuiClassicClockInfoBase;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.clock.utils.avoid.ClockDepthAvoidRuleUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: AllInOneBaseInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public abstract class AllInOneBaseInfo extends MiuiClassicClockInfoBase {
    public static final Companion Companion = new Companion(null);
    public final String CONVERT_TAG;
    public int clockStyle;
    public boolean isDoubleRow;
    public int magazineHeight;
    public AllInOneRectParams rectParamsOrNull;
    public int signatureLineCount;

    /* JADX INFO: compiled from: AllInOneBaseInfo.kt */
    @Metadata
    public static final class AllInOneRectParams {
        public final float clockBottomRatio;
        public final AllInOneClockStyle.Region clockDepthType;
        public final float clockTopRatio;
        public final int timeHeight;
        public final int timeWidth;
        public static final Companion Companion = new Companion(null);
        public static final int NO_OP_INT = -1;
        public static final float NO_OP_FLOAT = -1.0f;

        /* JADX INFO: compiled from: AllInOneBaseInfo.kt */
        @Metadata
        public static final class Companion {
            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public AllInOneRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2) {
            this.clockDepthType = region;
            this.clockTopRatio = f;
            this.clockBottomRatio = f2;
            this.timeWidth = i;
            this.timeHeight = i2;
        }
    }

    /* JADX INFO: compiled from: AllInOneBaseInfo.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int covertWeekTextColor(int i) {
            if (i == 0) {
                i = -1;
            }
            return GlobalColorUtils.transformWithTone(i, Hct.fromInt(i).getTone() >= 75.0d ? 20 : 98);
        }
    }

    public AllInOneBaseInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
        this.CONVERT_TAG = "AllInOneConvertDataDebug";
        this.magazineHeight = -1;
        this.clockStyle = 1;
        parseClockBean(clockBean);
    }

    private final void parseClockBean(ClockBean clockBean) {
        if (clockBean == null) {
            return;
        }
        this.clockStyle = clockBean.getStyle();
        this.isDoubleRow = clockBean.isDoubleRow();
    }

    public final AllInOneRectParams computeInitParamsRect(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        AllInOneUtil.RectComputeResult rectComputeResultComputeLayoutResult = computeLayoutResult(context);
        AllInOneRectParams rectParams = rectComputeResultComputeLayoutResult != null ? rectComputeResultComputeLayoutResult.getRectParams() : null;
        if (rectParams != null) {
            return rectParams;
        }
        Log.e("ClockStyleInfo", "computeInitRectParams is null ");
        return convertDpToPx(context, exportDbRectParamsWithPresetFallback(context));
    }

    public final AllInOneUtil.RectComputeResult computeLayoutResult(Context context) {
        int signatureLines;
        float f;
        AllInOneClockStyle.Region clockDepthType;
        float clockTopRatio;
        float clockBottomRatio;
        float f2;
        Intrinsics.checkNotNullParameter(context, "context");
        if (hasSignature()) {
            AllInOneUtil allInOneUtil = AllInOneUtil.INSTANCE;
            String signature = getSignature();
            Intrinsics.checkNotNullExpressionValue(signature, "getSignature(...)");
            signatureLines = allInOneUtil.getSignatureLines(context, signature);
        } else {
            signatureLines = 0;
        }
        int i = signatureLines;
        if (getTimeWidth() <= 0.0f || getTimeHeight() <= 0.0f) {
            AllInOneRectParams allInOneRectParamsComputePresetRectParams = computePresetRectParams(context);
            float f3 = allInOneRectParamsComputePresetRectParams.timeWidth;
            float f4 = allInOneRectParamsComputePresetRectParams.timeHeight;
            f = f4;
            clockDepthType = allInOneRectParamsComputePresetRectParams.clockDepthType;
            clockTopRatio = allInOneRectParamsComputePresetRectParams.clockTopRatio;
            clockBottomRatio = allInOneRectParamsComputePresetRectParams.clockBottomRatio;
            f2 = f3;
        } else {
            float timeWidth = getTimeWidth();
            float timeHeight = getTimeHeight();
            f = timeHeight;
            clockDepthType = getClockDepthType();
            clockTopRatio = getClockTopRatio();
            clockBottomRatio = getClockBottomRatio();
            f2 = timeWidth;
        }
        return AllInOneUtil.INSTANCE.computeRectForConfigurationChanged(context, new AllInOneUtil.ClockLayoutInput(clockTopRatio, clockBottomRatio, f2, f, clockDepthType, this.isDoubleRow, i, isSkipScreenTransform(), this.magazineHeight));
    }

    public final AllInOneRectParams computePresetRectParams(Context context) {
        AllInOneUtil.PresetResult presetResultComputePresetParams = AllInOneUtil.computePresetParams(context, this.clockStyle, true);
        AllInOneUtil.PresetConfig presetConfig = AllInOneUtil.getPresetConfig(this.clockStyle);
        setClockWeight(presetResultComputePresetParams.getClockWeight());
        this.isDoubleRow = presetResultComputePresetParams.isDoubleRow();
        boolean skipScreenTransform = presetConfig.getSkipScreenTransform();
        float timeHeight = presetResultComputePresetParams.getTimeHeight();
        if (!skipScreenTransform && DeviceConfig.FOLD_DEVICE) {
            float dimenDpValue = DeviceConfig.getDimenDpValue(presetConfig.getHeightResId(), context);
            float dimenDpValue2 = DeviceConfig.getDimenDpValue(R.dimen.miui_all_in_one_base_edit_area_height, context);
            if (dimenDpValue2 > 0.0f) {
                int[] foldOuterScreenSize = DeviceConfig.getFoldOuterScreenSize(context);
                if (foldOuterScreenSize != null) {
                    int i = foldOuterScreenSize[1];
                    float f = foldOuterScreenSize[2] / 160.0f;
                    ClockDepthAvoidRuleUtils.SafeBounds safeBoundsForSmallPort = ClockDepthAvoidRuleUtils.getSafeBoundsForSmallPort(context);
                    timeHeight = (dimenDpValue / dimenDpValue2) * (((i - ((safeBoundsForSmallPort != null ? safeBoundsForSmallPort.getTop() : 75.0f) * f)) - ((safeBoundsForSmallPort != null ? safeBoundsForSmallPort.getBottom() : 119.0f) * f)) / f);
                } else {
                    int[] screenWH = DeviceConfig.getScreenWH(context);
                    int iMin = Math.min(screenWH[0], screenWH[1]);
                    int iMax = Math.max(screenWH[0], screenWH[1]);
                    int i2 = (DeviceConfig.isQ18() && DeviceConfig.inFoldLargeScreen(context)) ? iMin : iMax;
                    if (i2 == iMax) {
                        iMax = iMin;
                    }
                    timeHeight = (dimenDpValue / dimenDpValue2) * DeviceConfig.pxToDp(ClockDepthAvoidRuleUtils.createEditRect$default(context, iMax, i2, ClockDepthAvoidRuleUtils.DEFAULT_CONFIG, null, null, 48, null).height(), context);
                }
            }
        }
        setSkipScreenTransform(skipScreenTransform);
        return new AllInOneRectParams(presetConfig.getRegion(), presetResultComputePresetParams.getTopRatio(), presetResultComputePresetParams.getBottomRatio(), (int) presetResultComputePresetParams.getTimeWidth(), (int) timeHeight);
    }

    public final AllInOneRectParams convertDpToPx(Context context, AllInOneRectParams allInOneRectParams) {
        return new AllInOneRectParams(allInOneRectParams.clockDepthType, allInOneRectParams.clockTopRatio, allInOneRectParams.clockBottomRatio, DeviceConfig.dpToPxRounded(allInOneRectParams.timeWidth, context), DeviceConfig.dpToPxRounded(allInOneRectParams.timeHeight, context));
    }

    public final AllInOneRectParams exportDbRectParamsWithPresetFallback(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (getTimeWidth() <= 0.0f || getTimeHeight() <= 0.0f) ? computePresetRectParams(context) : exportRectParamsFromClockStyleInfo(context);
    }

    public final AllInOneRectParams exportRectParamsFromClockStyleInfo(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return new AllInOneRectParams(getClockDepthType(), getClockTopRatio(), getClockBottomRatio(), (int) getTimeWidth(), (int) getTimeHeight());
    }

    public final AllInOneAodLinkageUtils.AodLinkedResult getAllInOneAodLinkedResult(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return AllInOneAodLinkageUtils.computeAodLinkedResult(context, this);
    }

    @Override // com.miui.clock.module.MiuiClassicClockInfoBase
    public int getDateColor() {
        if (ClockStyleInfo.isFullAODType(((MiuiClassicClockInfoBase) this).displayType)) {
            return getFullAodPrimaryColor();
        }
        return ClockStyleInfo.isAODType(((MiuiClassicClockInfoBase) this).displayType) ? getAodPrimaryColor() : getPrimaryColor();
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getInfoAreaColor() {
        return this.isSuperSaveOpen ? Companion.covertWeekTextColor(-1) : super.getInfoAreaColor();
    }

    public final int getMagazineHeight() {
        return this.magazineHeight;
    }

    public final AllInOneRectParams getRectParamsOrNull() {
        return this.rectParamsOrNull;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getSecondaryColor() {
        if (this.secondaryColor == 0) {
            return -1;
        }
        return super.getSecondaryColor();
    }

    public final int getSignatureLineCount() {
        return this.signatureLineCount;
    }

    public final boolean isDoubleRow() {
        return this.isDoubleRow;
    }

    public final void setDoubleRow(boolean z) {
        this.isDoubleRow = z;
    }

    public final void setMagazineHeight(int i) {
        this.magazineHeight = i;
    }

    public final void setRectParams(Context context, AllInOneRectParams allInOneRectParams) {
        setRectParams(context, allInOneRectParams, false);
    }

    public final void setRectParams(Context context, AllInOneRectParams allInOneRectParams, boolean z) {
        this.rectParamsOrNull = allInOneRectParams;
        if (!z || allInOneRectParams == null || context == null) {
            return;
        }
        Log.d(this.CONVERT_TAG, "setRectParams, Prepare for storage: width: " + allInOneRectParams.timeWidth + ", height: " + allInOneRectParams.timeHeight + ", topRatio: " + allInOneRectParams.clockTopRatio + ", bottomRatio: " + allInOneRectParams.clockBottomRatio + ", isDoubleRow: " + this.isDoubleRow);
        AllInOneUtil.ClockLayoutInput clockLayoutInputConvertToStorageFormat = AllInOneUtil.INSTANCE.convertToStorageFormat(allInOneRectParams, context, this.isDoubleRow, isSkipScreenTransform());
        if (clockLayoutInputConvertToStorageFormat == null) {
            return;
        }
        Log.d(this.CONVERT_TAG, "storage end; convertParams: " + clockLayoutInputConvertToStorageFormat);
        setClockDepthType(clockLayoutInputConvertToStorageFormat.getDepthLayer());
        setClockTopRatio(clockLayoutInputConvertToStorageFormat.getTopRatio());
        setClockBottomRatio(clockLayoutInputConvertToStorageFormat.getBottomRatio());
        setTimeWidth(clockLayoutInputConvertToStorageFormat.getTimeWidth());
        setTimeHeight(clockLayoutInputConvertToStorageFormat.getTimeHeight());
    }

    public final void setSignatureLineCount(int i) {
        this.signatureLineCount = i;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public void updateAodHighTextColors() {
        updateInfoColors();
        if (this.aodPrimaryColor == 0 || this.aodSecondaryColor == 0) {
            this.aodPrimaryColor = -1;
            this.aodSecondaryColor = -1;
            int iCovertWeekTextColor = Companion.covertWeekTextColor(-1);
            this.infoAreaColor = iCovertWeekTextColor;
            this.aodInfoColor = iCovertWeekTextColor;
            this.fullAodInfoColor = iCovertWeekTextColor;
        }
    }

    public final void updateInfoColors() {
        int i = this.primaryColor;
        if (ClockEffectUtils.isBlurMixType(getClockEffect()) || ClockEffectUtils.isDifferenceType(getClockEffect())) {
            i = this.blendColor;
        }
        int iCovertWeekTextColor = Companion.covertWeekTextColor(i);
        this.infoAreaColor = iCovertWeekTextColor;
        this.aodInfoColor = iCovertWeekTextColor;
        this.fullAodInfoColor = iCovertWeekTextColor;
    }
}
