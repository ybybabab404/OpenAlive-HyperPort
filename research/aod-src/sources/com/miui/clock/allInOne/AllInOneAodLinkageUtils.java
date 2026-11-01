package com.miui.clock.allInOne;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Log;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.clock.R;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.avoid.ClockDepthAvoidRuleUtils;
import com.miui.clock.utils.avoid.DepthAvoidEvaluatorDebug;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
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
/* JADX INFO: compiled from: AllInOneAodLinkageUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AllInOneAodLinkageUtils {
    public static final AllInOneAodLinkageUtils INSTANCE = new AllInOneAodLinkageUtils();

    /* JADX INFO: compiled from: AllInOneAodLinkageUtils.kt */
    @Metadata
    public static final class AodLinkedResult {
        public final int aodClockTopPx;
        public final int aodHeightPx;
        public final int aodWeight;
        public final int aodWidthPx;

        public AodLinkedResult(int i, int i2, int i3, int i4) {
            this.aodWeight = i;
            this.aodWidthPx = i2;
            this.aodHeightPx = i3;
            this.aodClockTopPx = i4;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AodLinkedResult)) {
                return false;
            }
            AodLinkedResult aodLinkedResult = (AodLinkedResult) obj;
            return this.aodWeight == aodLinkedResult.aodWeight && this.aodWidthPx == aodLinkedResult.aodWidthPx && this.aodHeightPx == aodLinkedResult.aodHeightPx && this.aodClockTopPx == aodLinkedResult.aodClockTopPx;
        }

        public final int getAodClockTopPx() {
            return this.aodClockTopPx;
        }

        public final int getAodHeightPx() {
            return this.aodHeightPx;
        }

        public final int getAodWeight() {
            return this.aodWeight;
        }

        public final int getAodWidthPx() {
            return this.aodWidthPx;
        }

        public int hashCode() {
            return (((((Integer.hashCode(this.aodWeight) * 31) + Integer.hashCode(this.aodWidthPx)) * 31) + Integer.hashCode(this.aodHeightPx)) * 31) + Integer.hashCode(this.aodClockTopPx);
        }

        public String toString() {
            return "AodLinkedResult(aodWeight=" + this.aodWeight + ", aodWidthPx=" + this.aodWidthPx + ", aodHeightPx=" + this.aodHeightPx + ", aodClockTopPx=" + this.aodClockTopPx + ')';
        }
    }

    public static final AodLinkedResult computeAodLinkedResult(int i, int i2, int i3, int i4, int i5, int i6, float f, float f2, float f3, boolean z, float f4, int i7, float f5, float f6, int i8) {
        float f7;
        float f8;
        float f9;
        float f10;
        boolean z2 = DepthAvoidEvaluatorDebug.DEBUG_LOG;
        if (z2) {
            StringBuilder sb = new StringBuilder();
            sb.append("computeAodLinkedResult input: widthPx=");
            sb.append(i);
            sb.append(", heightPx=");
            sb.append(i2);
            sb.append(", weight=");
            sb.append(i3);
            sb.append(", screenH=");
            sb.append(i4);
            sb.append(", widthThresholdPx=");
            sb.append(i6);
            sb.append(", scaleLarge=");
            f7 = f;
            sb.append(f7);
            sb.append(", scaleSmall=");
            f8 = f2;
            sb.append(f8);
            sb.append(", maxHeightPercent=");
            f9 = f3;
            sb.append(f9);
            sb.append(", isDoubleRow=");
            sb.append(z);
            sb.append(", maxHeightPercentDoubleRow=");
            f10 = f4;
            sb.append(f10);
            sb.append(", screenWidthPx=");
            sb.append(i8);
            Log.d("AllInOneAodLinkageUtils", sb.toString());
        } else {
            f7 = f;
            f8 = f2;
            f9 = f3;
            f10 = f4;
        }
        boolean zIsLargeClock = isLargeClock(i, i2, i8, i4);
        int aodClockWeight = getAodClockWeight(i3, i7, zIsLargeClock);
        float f11 = i2 > 0 ? i / i2 : 0.0f;
        if (!z && f5 <= f11 && f11 <= f6) {
            return new AodLinkedResult(aodClockWeight, i, i2, i5);
        }
        if (i < i6) {
            f7 = f8;
        }
        int i9 = (int) (i * f7);
        if (z) {
            f9 = f10;
        }
        int i10 = (int) (i4 * f9);
        AodLinkedResult aodLinkedResult = new AodLinkedResult(aodClockWeight, i9, RangesKt.coerceAtMost((int) (i2 * f7), i10), i5);
        if (z2) {
            Log.d("AllInOneAodLinkageUtils", "computeAodLinkedResult output: " + aodLinkedResult + ", widthScale=" + f7 + ", maxHeightPx=" + i10 + ", isLargeClock=" + zIsLargeClock);
        }
        return aodLinkedResult;
    }

    public static final AodLinkedResult computeAodLinkedResult(Context context, AllInOneBaseInfo baseInfo) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(baseInfo, "baseInfo");
        Log.d("AllInOneAodLinkageUtils", "computeAodLinkedResult: magazineHeight=" + baseInfo.getMagazineHeight() + ", supportVariableFont=" + DeviceConfig.supportVariableFont() + ", displayType=" + baseInfo.getDisplayType());
        int[] screenWH = DeviceConfig.getScreenWH(context);
        int i = screenWH[0];
        int i2 = screenWH[1];
        AllInOneUtil.RectComputeResult rectComputeResultComputeLayoutResult = baseInfo.computeLayoutResult(context);
        AllInOneBaseInfo.AllInOneRectParams rectParams = rectComputeResultComputeLayoutResult != null ? rectComputeResultComputeLayoutResult.getRectParams() : null;
        int i3 = rectParams != null ? rectParams.timeWidth : 0;
        int i4 = rectParams != null ? rectParams.timeHeight : 0;
        Rect rectCreateEditRect$default = ClockDepthAvoidRuleUtils.createEditRect$default(context, i, i2, null, null, null, 56, null);
        int iHeight = (int) (rectCreateEditRect$default.top + (rectCreateEditRect$default.height() * (rectParams != null ? rectParams.clockTopRatio : baseInfo.getClockTopRatio())));
        Resources resources = context.getResources();
        return computeAodLinkedResult$default(i3, i4, baseInfo.getClockWeight(), i2, iHeight, DeviceConfig.getDimen(R.dimen.miui_aod_linkage_width_threshold, context), resources.getInteger(R.integer.miui_aod_linkage_width_scale_large) / 100.0f, resources.getInteger(R.integer.miui_aod_linkage_width_scale_small) / 100.0f, resources.getInteger(R.integer.miui_aod_linkage_max_height_percent) / 100.0f, baseInfo.isDoubleRow(), 0.0f, baseInfo.getHollowStyle(), 0.0f, 0.0f, i, 13312, null);
    }

    public static /* synthetic */ AodLinkedResult computeAodLinkedResult$default(int i, int i2, int i3, int i4, int i5, int i6, float f, float f2, float f3, boolean z, float f4, int i7, float f5, float f6, int i8, int i9, Object obj) {
        return computeAodLinkedResult(i, i2, i3, i4, (i9 & 16) != 0 ? 0 : i5, (i9 & 32) != 0 ? 0 : i6, (i9 & 64) != 0 ? 0.6f : f, (i9 & 128) != 0 ? 0.85f : f2, (i9 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? 0.2f : f3, (i9 & 512) != 0 ? false : z, (i9 & 1024) != 0 ? 0.3f : f4, (i9 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? 1 : i7, (i9 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? 3.15f : f5, (i9 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? 3.6f : f6, (i9 & 16384) != 0 ? 0 : i8);
    }

    public static final int getAodClockWeight(int i, int i2, boolean z) {
        double dCeil;
        if (i2 != 1 || i <= 0) {
            return i;
        }
        int iCoerceAtMost = RangesKt.coerceAtMost(i, TextStyleCategoryInfo.MAX_FONT_WEIGHT);
        if (z) {
            dCeil = iCoerceAtMost >= 500 ? Math.ceil((iCoerceAtMost * 0.5f) + 200) : Math.ceil((iCoerceAtMost * 0.55f) + 175);
        } else {
            if (iCoerceAtMost < 500) {
                if (iCoerceAtMost >= 400) {
                    dCeil = Math.ceil((iCoerceAtMost * 0.2f) + 300);
                }
                return RangesKt.coerceAtMost(iCoerceAtMost, TextStyleCategoryInfo.MAX_FONT_WEIGHT);
            }
            dCeil = Math.ceil((iCoerceAtMost * 0.45f) + 175);
        }
        iCoerceAtMost = (int) dCeil;
        return RangesKt.coerceAtMost(iCoerceAtMost, TextStyleCategoryInfo.MAX_FONT_WEIGHT);
    }

    public static /* synthetic */ int getAodClockWeight$default(int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return getAodClockWeight(i, i2, z);
    }

    public static final boolean isLargeClock(int i, int i2, int i3, int i4) {
        if (i > 0 && i2 > 0 && i3 > 0 && i4 > 0) {
            if (((long) i) * ((long) i2) > ((long) i3) * ((long) i4) * 0.2d) {
                return true;
            }
        }
        return false;
    }
}
