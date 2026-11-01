package com.miui.clock.allInOne;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.clock.R;
import com.miui.clock.module.ClockBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import com.miui.clock.utils.avoid.ClockDepthAvoidRuleUtils;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.maml.elements.MusicLyricParser;
import com.xiaomi.onetrack.g.d;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

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
/* JADX INFO: compiled from: AllInOneUtil.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AllInOneUtil {
    public static Rect cachedEditRect;
    public static ClockLayoutInput cachedInput;
    public static RectComputeResult cachedResult;
    public static final AllInOneUtil INSTANCE = new AllInOneUtil();
    public static int cachedOrientation = -1;
    public static int cachedDensityDpi = -1;
    public static final double AREA_MULTIPLIER = 2.6d;

    /* JADX INFO: compiled from: AllInOneUtil.kt */
    @Metadata
    public static final class AreaEnforceResult {
        public final int timeHeight;
        public final int timeWidth;

        public AreaEnforceResult(int i, int i2) {
            this.timeWidth = i;
            this.timeHeight = i2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AreaEnforceResult)) {
                return false;
            }
            AreaEnforceResult areaEnforceResult = (AreaEnforceResult) obj;
            return this.timeWidth == areaEnforceResult.timeWidth && this.timeHeight == areaEnforceResult.timeHeight;
        }

        public final int getTimeHeight() {
            return this.timeHeight;
        }

        public final int getTimeWidth() {
            return this.timeWidth;
        }

        public int hashCode() {
            return (Integer.hashCode(this.timeWidth) * 31) + Integer.hashCode(this.timeHeight);
        }

        public String toString() {
            return "AreaEnforceResult(timeWidth=" + this.timeWidth + ", timeHeight=" + this.timeHeight + ')';
        }
    }

    /* JADX INFO: compiled from: AllInOneUtil.kt */
    @Metadata
    public static final class ClockLayoutInput {
        public static final Companion Companion = new Companion(null);
        public final float bottomRatio;
        public final AllInOneClockStyle.Region depthLayer;
        public final boolean isDoubleRow;
        public final int magazineHeight;
        public final int signatureCount;
        public final boolean skipScreenTransform;
        public final float timeHeight;
        public final float timeWidth;
        public final float topRatio;

        /* JADX INFO: compiled from: AllInOneUtil.kt */
        @Metadata
        public static final class Companion {
            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final boolean shouldSkipTransform(float f, float f2) {
                if (f2 <= 0.0f) {
                    return false;
                }
                float f3 = f / f2;
                if (3.0f > f3 || f3 > 4.2f) {
                    return 0.7f <= f3 && f3 <= 0.76f;
                }
                return true;
            }
        }

        public ClockLayoutInput(float f, float f2, float f3, float f4, AllInOneClockStyle.Region region, boolean z, int i, boolean z2, int i2) {
            this.topRatio = f;
            this.bottomRatio = f2;
            this.timeWidth = f3;
            this.timeHeight = f4;
            this.depthLayer = region;
            this.isDoubleRow = z;
            this.signatureCount = i;
            this.skipScreenTransform = z2;
            this.magazineHeight = i2;
        }

        public /* synthetic */ ClockLayoutInput(float f, float f2, float f3, float f4, AllInOneClockStyle.Region region, boolean z, int i, boolean z2, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(f, f2, f3, f4, region, z, i, (i3 & 128) != 0 ? false : z2, (i3 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? -1 : i2);
        }

        public static /* synthetic */ ClockLayoutInput copy$default(ClockLayoutInput clockLayoutInput, float f, float f2, float f3, float f4, AllInOneClockStyle.Region region, boolean z, int i, boolean z2, int i2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                f = clockLayoutInput.topRatio;
            }
            if ((i3 & 2) != 0) {
                f2 = clockLayoutInput.bottomRatio;
            }
            if ((i3 & 4) != 0) {
                f3 = clockLayoutInput.timeWidth;
            }
            if ((i3 & 8) != 0) {
                f4 = clockLayoutInput.timeHeight;
            }
            if ((i3 & 16) != 0) {
                region = clockLayoutInput.depthLayer;
            }
            if ((i3 & 32) != 0) {
                z = clockLayoutInput.isDoubleRow;
            }
            if ((i3 & 64) != 0) {
                i = clockLayoutInput.signatureCount;
            }
            if ((i3 & 128) != 0) {
                z2 = clockLayoutInput.skipScreenTransform;
            }
            if ((i3 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0) {
                i2 = clockLayoutInput.magazineHeight;
            }
            boolean z3 = z2;
            int i4 = i2;
            boolean z4 = z;
            int i5 = i;
            AllInOneClockStyle.Region region2 = region;
            float f5 = f3;
            return clockLayoutInput.copy(f, f2, f5, f4, region2, z4, i5, z3, i4);
        }

        public final ClockLayoutInput copy(float f, float f2, float f3, float f4, AllInOneClockStyle.Region region, boolean z, int i, boolean z2, int i2) {
            return new ClockLayoutInput(f, f2, f3, f4, region, z, i, z2, i2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ClockLayoutInput)) {
                return false;
            }
            ClockLayoutInput clockLayoutInput = (ClockLayoutInput) obj;
            return Float.compare(this.topRatio, clockLayoutInput.topRatio) == 0 && Float.compare(this.bottomRatio, clockLayoutInput.bottomRatio) == 0 && Float.compare(this.timeWidth, clockLayoutInput.timeWidth) == 0 && Float.compare(this.timeHeight, clockLayoutInput.timeHeight) == 0 && this.depthLayer == clockLayoutInput.depthLayer && this.isDoubleRow == clockLayoutInput.isDoubleRow && this.signatureCount == clockLayoutInput.signatureCount && this.skipScreenTransform == clockLayoutInput.skipScreenTransform && this.magazineHeight == clockLayoutInput.magazineHeight;
        }

        public final float getBottomRatio() {
            return this.bottomRatio;
        }

        public final AllInOneClockStyle.Region getDepthLayer() {
            return this.depthLayer;
        }

        public final int getMagazineHeight() {
            return this.magazineHeight;
        }

        public final int getSignatureCount() {
            return this.signatureCount;
        }

        public final boolean getSkipScreenTransform() {
            return this.skipScreenTransform;
        }

        public final float getTimeHeight() {
            return this.timeHeight;
        }

        public final float getTimeWidth() {
            return this.timeWidth;
        }

        public final float getTopRatio() {
            return this.topRatio;
        }

        public int hashCode() {
            int iHashCode = ((((((Float.hashCode(this.topRatio) * 31) + Float.hashCode(this.bottomRatio)) * 31) + Float.hashCode(this.timeWidth)) * 31) + Float.hashCode(this.timeHeight)) * 31;
            AllInOneClockStyle.Region region = this.depthLayer;
            return ((((((((iHashCode + (region == null ? 0 : region.hashCode())) * 31) + Boolean.hashCode(this.isDoubleRow)) * 31) + Integer.hashCode(this.signatureCount)) * 31) + Boolean.hashCode(this.skipScreenTransform)) * 31) + Integer.hashCode(this.magazineHeight);
        }

        public final boolean isDoubleRow() {
            return this.isDoubleRow;
        }

        public String toString() {
            return "ClockLayoutInput(topRatio=" + this.topRatio + ", bottomRatio=" + this.bottomRatio + ", timeWidth=" + this.timeWidth + ", timeHeight=" + this.timeHeight + ", depthLayer=" + this.depthLayer + ", isDoubleRow=" + this.isDoubleRow + ", signatureCount=" + this.signatureCount + ", skipScreenTransform=" + this.skipScreenTransform + ", magazineHeight=" + this.magazineHeight + ')';
        }
    }

    /* JADX INFO: compiled from: AllInOneUtil.kt */
    @Metadata
    public static final class PresetConfig {
        public final int bottomWeight;
        public final int clockWeight;
        public final int fontStyle;
        public final int heightResId;
        public final boolean isColonShow;
        public final boolean isDoubleRow;
        public final AllInOneClockStyle.Region region;
        public final boolean skipScreenTransform;
        public final int topWeight;
        public final int totalWeight;
        public final int widthResId;

        public PresetConfig(int i, int i2, int i3, int i4, int i5, int i6, AllInOneClockStyle.Region region, boolean z, boolean z2, int i7, boolean z3) {
            Intrinsics.checkNotNullParameter(region, "region");
            this.widthResId = i;
            this.heightResId = i2;
            this.topWeight = i3;
            this.bottomWeight = i4;
            this.totalWeight = i5;
            this.clockWeight = i6;
            this.region = region;
            this.isDoubleRow = z;
            this.isColonShow = z2;
            this.fontStyle = i7;
            this.skipScreenTransform = z3;
        }

        public /* synthetic */ PresetConfig(int i, int i2, int i3, int i4, int i5, int i6, AllInOneClockStyle.Region region, boolean z, boolean z2, int i7, boolean z3, int i8, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, i3, i4, i5, i6, region, z, z2, (i8 & 512) != 0 ? 21 : i7, (i8 & 1024) != 0 ? false : z3);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PresetConfig)) {
                return false;
            }
            PresetConfig presetConfig = (PresetConfig) obj;
            return this.widthResId == presetConfig.widthResId && this.heightResId == presetConfig.heightResId && this.topWeight == presetConfig.topWeight && this.bottomWeight == presetConfig.bottomWeight && this.totalWeight == presetConfig.totalWeight && this.clockWeight == presetConfig.clockWeight && this.region == presetConfig.region && this.isDoubleRow == presetConfig.isDoubleRow && this.isColonShow == presetConfig.isColonShow && this.fontStyle == presetConfig.fontStyle && this.skipScreenTransform == presetConfig.skipScreenTransform;
        }

        public final int getBottomWeight() {
            return this.bottomWeight;
        }

        public final int getClockWeight() {
            return this.clockWeight;
        }

        public final int getFontStyle() {
            return this.fontStyle;
        }

        public final int getHeightResId() {
            return this.heightResId;
        }

        public final AllInOneClockStyle.Region getRegion() {
            return this.region;
        }

        public final boolean getSkipScreenTransform() {
            return this.skipScreenTransform;
        }

        public final int getTopWeight() {
            return this.topWeight;
        }

        public final int getWidthResId() {
            return this.widthResId;
        }

        public int hashCode() {
            return (((((((((((((((((((Integer.hashCode(this.widthResId) * 31) + Integer.hashCode(this.heightResId)) * 31) + Integer.hashCode(this.topWeight)) * 31) + Integer.hashCode(this.bottomWeight)) * 31) + Integer.hashCode(this.totalWeight)) * 31) + Integer.hashCode(this.clockWeight)) * 31) + this.region.hashCode()) * 31) + Boolean.hashCode(this.isDoubleRow)) * 31) + Boolean.hashCode(this.isColonShow)) * 31) + Integer.hashCode(this.fontStyle)) * 31) + Boolean.hashCode(this.skipScreenTransform);
        }

        public final boolean isColonShow() {
            return this.isColonShow;
        }

        public final boolean isDoubleRow() {
            return this.isDoubleRow;
        }

        public String toString() {
            return "PresetConfig(widthResId=" + this.widthResId + ", heightResId=" + this.heightResId + ", topWeight=" + this.topWeight + ", bottomWeight=" + this.bottomWeight + ", totalWeight=" + this.totalWeight + ", clockWeight=" + this.clockWeight + ", region=" + this.region + ", isDoubleRow=" + this.isDoubleRow + ", isColonShow=" + this.isColonShow + ", fontStyle=" + this.fontStyle + ", skipScreenTransform=" + this.skipScreenTransform + ')';
        }
    }

    /* JADX INFO: compiled from: AllInOneUtil.kt */
    @Metadata
    public static final class PresetResult {
        public final float bottomRatio;
        public final int clockWeight;
        public final boolean isDoubleRow;
        public final float timeHeight;
        public final float timeWidth;
        public final float topRatio;

        public PresetResult(float f, float f2, float f3, float f4, int i, boolean z) {
            this.timeWidth = f;
            this.timeHeight = f2;
            this.topRatio = f3;
            this.bottomRatio = f4;
            this.clockWeight = i;
            this.isDoubleRow = z;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PresetResult)) {
                return false;
            }
            PresetResult presetResult = (PresetResult) obj;
            return Float.compare(this.timeWidth, presetResult.timeWidth) == 0 && Float.compare(this.timeHeight, presetResult.timeHeight) == 0 && Float.compare(this.topRatio, presetResult.topRatio) == 0 && Float.compare(this.bottomRatio, presetResult.bottomRatio) == 0 && this.clockWeight == presetResult.clockWeight && this.isDoubleRow == presetResult.isDoubleRow;
        }

        public final float getBottomRatio() {
            return this.bottomRatio;
        }

        public final int getClockWeight() {
            return this.clockWeight;
        }

        public final float getTimeHeight() {
            return this.timeHeight;
        }

        public final float getTimeWidth() {
            return this.timeWidth;
        }

        public final float getTopRatio() {
            return this.topRatio;
        }

        public int hashCode() {
            return (((((((((Float.hashCode(this.timeWidth) * 31) + Float.hashCode(this.timeHeight)) * 31) + Float.hashCode(this.topRatio)) * 31) + Float.hashCode(this.bottomRatio)) * 31) + Integer.hashCode(this.clockWeight)) * 31) + Boolean.hashCode(this.isDoubleRow);
        }

        public final boolean isDoubleRow() {
            return this.isDoubleRow;
        }

        public String toString() {
            return "PresetResult(timeWidth=" + this.timeWidth + ", timeHeight=" + this.timeHeight + ", topRatio=" + this.topRatio + ", bottomRatio=" + this.bottomRatio + ", clockWeight=" + this.clockWeight + ", isDoubleRow=" + this.isDoubleRow + ')';
        }
    }

    /* JADX INFO: compiled from: AllInOneUtil.kt */
    @Metadata
    public static final class RatioEnforceResult {
        public final boolean heightAdjusted;
        public final int timeHeight;
        public final int timeWidth;

        public RatioEnforceResult(int i, int i2, boolean z) {
            this.timeWidth = i;
            this.timeHeight = i2;
            this.heightAdjusted = z;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RatioEnforceResult)) {
                return false;
            }
            RatioEnforceResult ratioEnforceResult = (RatioEnforceResult) obj;
            return this.timeWidth == ratioEnforceResult.timeWidth && this.timeHeight == ratioEnforceResult.timeHeight && this.heightAdjusted == ratioEnforceResult.heightAdjusted;
        }

        public final int getTimeHeight() {
            return this.timeHeight;
        }

        public final int getTimeWidth() {
            return this.timeWidth;
        }

        public int hashCode() {
            return (((Integer.hashCode(this.timeWidth) * 31) + Integer.hashCode(this.timeHeight)) * 31) + Boolean.hashCode(this.heightAdjusted);
        }

        public String toString() {
            return "RatioEnforceResult(timeWidth=" + this.timeWidth + ", timeHeight=" + this.timeHeight + ", heightAdjusted=" + this.heightAdjusted + ')';
        }
    }

    /* JADX INFO: compiled from: AllInOneUtil.kt */
    @Metadata
    public static final class RectComputeResult {
        public Rect rect;
        public AllInOneBaseInfo.AllInOneRectParams rectParams;

        public RectComputeResult(Rect rect, AllInOneBaseInfo.AllInOneRectParams allInOneRectParams) {
            this.rect = rect;
            this.rectParams = allInOneRectParams;
        }

        public final Rect getRect() {
            return this.rect;
        }

        public final AllInOneBaseInfo.AllInOneRectParams getRectParams() {
            return this.rectParams;
        }

        public final void setRectParams(AllInOneBaseInfo.AllInOneRectParams allInOneRectParams) {
            this.rectParams = allInOneRectParams;
        }
    }

    public static final Pair adjustForMagazineOverflow(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8;
        if (i4 > 0 && (i8 = (i3 + i4) - i6) > 0) {
            int i9 = i5 + i7;
            int iCoerceAtLeast = RangesKt.coerceAtLeast(i - i9, 0);
            return i8 <= iCoerceAtLeast ? new Pair(Integer.valueOf(i - i8), Integer.valueOf(i2)) : new Pair(Integer.valueOf(i9), Integer.valueOf(RangesKt.coerceAtLeast(i2 - (i8 - iCoerceAtLeast), 0)));
        }
        return new Pair(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static final Rect buildColorPickupSourceRect(Context context, Rect baseRect, int i, int i2, TextPaint textPaint, int i3, String str, boolean z, TextPaint textPaint2, String str2, String str3, Rect galleryRect, int i4) {
        float fMeasureText;
        int dimen;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(baseRect, "baseRect");
        Intrinsics.checkNotNullParameter(galleryRect, "galleryRect");
        if (baseRect.isEmpty()) {
            return new Rect();
        }
        Rect rect = new Rect();
        rect.union(baseRect);
        int iCenterX = baseRect.centerX();
        String dateTextForMeasurement = getDateTextForMeasurement(context, i3, str);
        Boolean boolHasIcon = ClassicClockTimeUtils.hasIcon(dateTextForMeasurement);
        Intrinsics.checkNotNullExpressionValue(boolHasIcon, "hasIcon(...)");
        int iCoerceAtLeast = RangesKt.coerceAtLeast((int) Math.ceil((textPaint == null || dateTextForMeasurement.length() <= 0) ? 0.0f : textPaint.measureText(dateTextForMeasurement) + (boolHasIcon.booleanValue() ? DeviceConfig.getDimen(R.dimen.miui_date_all_in_one_gap_instead_icon, context) : 0)), 0) / 2;
        int dimen2 = DeviceConfig.getDimen(R.dimen.miui_all_in_one_text_area_height, context);
        int iCoerceAtLeast2 = RangesKt.coerceAtLeast((baseRect.top - dimen2) - DeviceConfig.getDimen(R.dimen.miui_all_in_one_gap_between_text_area_and_time, context), 0);
        rect.union(new Rect(iCenterX - iCoerceAtLeast, iCoerceAtLeast2, iCoerceAtLeast + iCenterX, RangesKt.coerceAtMost(dimen2 + iCoerceAtLeast2, i2)));
        if (z) {
            int signatureLines = str3 != null ? INSTANCE.getSignatureLines(context, str3) : 0;
            if (textPaint2 == null || str2 == null || str2.length() == 0) {
                fMeasureText = 0.0f;
            } else if (signatureLines <= 1) {
                fMeasureText = textPaint2.measureText(str2);
            } else {
                Intrinsics.checkNotNull(str2);
                Iterator it = StringsKt.split$default(str2, new String[]{MusicLyricParser.CRLF, "\n", "\r"}, false, 0, 6, null).iterator();
                fMeasureText = 0.0f;
                while (it.hasNext()) {
                    float fMeasureText2 = textPaint2.measureText((String) it.next());
                    if (fMeasureText2 > fMeasureText) {
                        fMeasureText = fMeasureText2;
                    }
                }
            }
            int iCoerceAtLeast3 = RangesKt.coerceAtLeast((int) Math.ceil(fMeasureText), 0) / 2;
            int i5 = iCenterX - iCoerceAtLeast3;
            int i6 = iCenterX + iCoerceAtLeast3;
            int dimen3 = DeviceConfig.getDimen(R.dimen.miui_all_in_one_gap_between_time_and_sigature, context);
            if (signatureLines != 1) {
                dimen = signatureLines != 2 ? 0 : DeviceConfig.getDimen(R.dimen.miui_all_in_one_signature_height_two_line, context);
            } else {
                dimen = DeviceConfig.getDimen(R.dimen.miui_all_in_one_signature_height_one_line, context);
            }
            if (dimen > 0) {
                int iCoerceAtMost = RangesKt.coerceAtMost(baseRect.bottom + dimen3, i2);
                rect.union(new Rect(i5, iCoerceAtMost, i6, RangesKt.coerceAtMost(dimen + iCoerceAtMost, i2)));
            }
        }
        if (!galleryRect.isEmpty()) {
            rect.union(galleryRect);
        }
        if (i4 != 0) {
            rect.offset(0, i4);
        }
        return rect;
    }

    public static final Rect calculateColorPickupFinalRect(Rect originalRect, int i, int i2) {
        Intrinsics.checkNotNullParameter(originalRect, "originalRect");
        if (originalRect.isEmpty()) {
            return new Rect(0, 0, i, i2);
        }
        int iWidth = originalRect.width();
        int iHeight = originalRect.height();
        if (iWidth <= 0 || iHeight <= 0) {
            return new Rect(0, 0, i, i2);
        }
        double d = AREA_MULTIPLIER;
        int iSqrt = (int) (((double) iWidth) * Math.sqrt(d));
        int iSqrt2 = (int) (((double) iHeight) * Math.sqrt(d));
        int iCoerceAtMost = RangesKt.coerceAtMost(iSqrt, i);
        int iCoerceAtMost2 = RangesKt.coerceAtMost(iSqrt2, i2);
        int iCenterX = originalRect.centerX();
        int iCenterY = originalRect.centerY();
        int iCoerceAtLeast = RangesKt.coerceAtLeast(iCenterX - (iCoerceAtMost / 2), 0);
        int iCoerceAtLeast2 = RangesKt.coerceAtLeast(iCenterY - (iCoerceAtMost2 / 2), 0);
        Rect rect = new Rect(iCoerceAtLeast, iCoerceAtLeast2, RangesKt.coerceAtMost(iCoerceAtMost + iCoerceAtLeast, i), RangesKt.coerceAtMost(iCoerceAtMost2 + iCoerceAtLeast2, i2));
        Log.d("AllInOneUtil", "calculateColorPickupFinalRect, finalRect: " + rect);
        return rect;
    }

    public static final int computeMagazinePositionY(Context context, int i, boolean z, int i2) {
        Intrinsics.checkNotNullParameter(context, "context");
        int dimen = DeviceConfig.getDimen(R.dimen.miui_all_in_one_gap_between_time_and_sigature, context);
        if (z) {
            return i + dimen + (i2 > 1 ? DeviceConfig.getDimen(R.dimen.miui_all_in_one_signature_height_two_line, context) : DeviceConfig.getDimen(R.dimen.miui_all_in_one_signature_height_one_line, context)) + DeviceConfig.getDimen(R.dimen.miui_all_in_one_gap_between_signature_and_magazine, context);
        }
        return i + dimen;
    }

    public static final PresetResult computePresetParams(Context context, int i, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        PresetConfig presetConfig = getPresetConfig(i);
        int iResolveResIdWithSuffix = MutiDeviceDimenAdapter.resolveResIdWithSuffix(context, presetConfig.getWidthResId());
        int iResolveResIdWithSuffix2 = MutiDeviceDimenAdapter.resolveResIdWithSuffix(context, presetConfig.getHeightResId());
        float dimenDpValue = DeviceConfig.getDimenDpValue(iResolveResIdWithSuffix, context);
        float dimenDpValue2 = DeviceConfig.getDimenDpValue(iResolveResIdWithSuffix2, context);
        Log.d("AllInOneConvertDataDebug", "computePresetParams: style=" + i + ", resolvedWidthResId=" + iResolveResIdWithSuffix + ", resolvedHeightResId=" + iResolveResIdWithSuffix2 + ", timeWidthDp=" + dimenDpValue + ", timeHeightDp=" + dimenDpValue2 + ", useDp=" + z);
        if (!z) {
            dimenDpValue = DeviceConfig.dpToPx(dimenDpValue, context);
        }
        float f = dimenDpValue;
        if (!z) {
            dimenDpValue2 = DeviceConfig.dpToPx(dimenDpValue2, context);
        }
        return new PresetResult(f, dimenDpValue2, presetConfig.getTopWeight(), presetConfig.getBottomWeight(), presetConfig.getClockWeight(), presetConfig.isDoubleRow());
    }

    public static /* synthetic */ ClockLayoutInput convertToStorageFormat$default(AllInOneUtil allInOneUtil, AllInOneBaseInfo.AllInOneRectParams allInOneRectParams, Context context, boolean z, boolean z2, int i, Object obj) {
        if ((i & 8) != 0) {
            z2 = false;
        }
        return allInOneUtil.convertToStorageFormat(allInOneRectParams, context, z, z2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:40:0x0090  */
    /* JADX WARN: Code duplicated, block: B:41:0x0095  */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x007a, code lost:
    
        if (r4.equals("oversize_b") == false) goto L44;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final com.miui.clock.module.ClockBean dealWithOTAClockBean(com.miui.clock.module.ClockBean r8, android.content.Context r9) {
        /*
            Method dump skipped, instruction units count: 220
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.clock.allInOne.AllInOneUtil.dealWithOTAClockBean(com.miui.clock.module.ClockBean, android.content.Context):com.miui.clock.module.ClockBean");
    }

    public static final String getDateTextForMeasurement(Context context, int i, String str) {
        int i2;
        Intrinsics.checkNotNullParameter(context, "context");
        if (i == 100) {
            i2 = R.string.miui_allinone_date_format_date_only;
        } else if (i == 101) {
            i2 = R.string.miui_allinone_sample_lunar_only;
        } else if (i == 103) {
            i2 = R.string.miui_allinone_sample_date_and_lunar;
        } else if (i == 500) {
            i2 = R.string.miui_allinone_sample_steps;
        } else if (i == 504) {
            i2 = R.string.miui_allinone_sample_stand;
        } else if (i == 400) {
            i2 = R.string.miui_allinone_sample_weather;
        } else if (i != 401) {
            switch (i) {
                case d.c /* 404 */:
                    i2 = R.string.miui_allinone_sample_sunset;
                    break;
                case 405:
                    i2 = R.string.miui_allinone_sample_uvi;
                    break;
                case 406:
                    i2 = R.string.miui_allinone_sample_feelings;
                    break;
                case 407:
                    i2 = R.string.miui_allinone_sample_wind;
                    break;
                case 408:
                    i2 = R.string.miui_allinone_sample_humidity;
                    break;
                default:
                    i2 = 0;
                    break;
            }
        } else {
            i2 = R.string.miui_allinone_sample_aqi;
        }
        if (i2 == 0) {
            return str == null ? "" : str;
        }
        String string = context.getString(i2);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    public static final PresetConfig getPresetConfig(int i) {
        switch (i) {
            case 1:
                boolean zIsGxzwLowPosition = DeviceConfig.isGxzwLowPosition();
                return new PresetConfig(R.dimen.miui_all_in_one_style1_width, R.dimen.miui_all_in_one_style1_height, zIsGxzwLowPosition ? 1 : 23, zIsGxzwLowPosition ? 1 : 26, zIsGxzwLowPosition ? 2 : 49, 420, AllInOneClockStyle.Region.REGION_3, false, true, 0, true, 512, null);
            case 2:
                return new PresetConfig(R.dimen.miui_all_in_one_style2_width, R.dimen.miui_all_in_one_style2_height, 0, 1, 1, 378, AllInOneClockStyle.Region.REGION_1, false, true, 0, true, 512, null);
            case 3:
                return new PresetConfig(R.dimen.miui_all_in_one_style3_width, R.dimen.miui_all_in_one_style3_height, 0, 1, 1, 420, AllInOneClockStyle.Region.REGION_1, true, false, 0, true, 512, null);
            case 4:
                return new PresetConfig(R.dimen.miui_all_in_one_style4_width, R.dimen.miui_all_in_one_style4_height, 6, 7, 13, TextStyleCategoryInfo.MAX_FONT_WEIGHT, AllInOneClockStyle.Region.REGION_3, true, false, 0, false, 1536, null);
            case 5:
                return new PresetConfig(R.dimen.miui_all_in_one_style5_width, R.dimen.miui_all_in_one_style5_height, 0, 1, 1, 450, AllInOneClockStyle.Region.REGION_2, false, false, 22, false, 1024, null);
            case 6:
                return new PresetConfig(R.dimen.miui_all_in_one_style6_width, R.dimen.miui_all_in_one_style6_height, 0, 1, 1, TextStyleCategoryInfo.MAX_FONT_WEIGHT, AllInOneClockStyle.Region.REGION_1, false, false, 0, false, 1536, null);
            case 7:
                boolean zIsGxzwLowPosition2 = DeviceConfig.isGxzwLowPosition();
                return new PresetConfig(R.dimen.miui_all_in_one_style7_width, R.dimen.miui_all_in_one_style7_height, zIsGxzwLowPosition2 ? 11 : 5, zIsGxzwLowPosition2 ? 12 : 7, zIsGxzwLowPosition2 ? 23 : 12, 450, AllInOneClockStyle.Region.REGION_3, true, false, 22, false, 1024, null);
            case 8:
                return new PresetConfig(R.dimen.miui_all_in_one_style8_width, R.dimen.miui_all_in_one_style8_height, 10, 1, 11, 630, AllInOneClockStyle.Region.REGION_3, false, false, 23, false, 1024, null);
            default:
                return new PresetConfig(R.dimen.miui_all_in_one_style1_width, R.dimen.miui_all_in_one_style1_height, 12, 13, 25, 420, AllInOneClockStyle.Region.REGION_3, false, true, 0, false, 1536, null);
        }
    }

    public static final void inheritClassicPlusSecondaryColors(ClockBean clockBean) {
        Intrinsics.checkNotNullParameter(clockBean, "clockBean");
        clockBean.setSecondaryColor(clockBean.getPrimaryColor() != 0 ? clockBean.getPrimaryColor() : clockBean.getBlendColor());
        clockBean.setAutoSecondaryColor(clockBean.isAutoPrimaryColor());
        clockBean.setSecondaryBlendColor(clockBean.getBlendColor() != 0 ? clockBean.getBlendColor() : clockBean.getPrimaryColor());
    }

    public static final void initClockParams(ClockBean clockBean, Context context) {
        Intrinsics.checkNotNullParameter(clockBean, "clockBean");
        Intrinsics.checkNotNullParameter(context, "context");
        INSTANCE.applyOtaClockParams(clockBean, context, clockBean.getStyle());
    }

    public static final boolean needMergeToAllInOne(String str) {
        return Intrinsics.areEqual("classic_max", str) || Intrinsics.areEqual("classic_plus", str) || Intrinsics.areEqual("oversize_a", str) || Intrinsics.areEqual("oversize_b", str) || Intrinsics.areEqual("magazine_c", str);
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0130  */
    public final void applyOtaClockParams(ClockBean clockBean, Context context, int i) {
        float fPxToDp;
        String str;
        PresetConfig presetConfig = getPresetConfig(i);
        clockBean.setStyle(i);
        clockBean.setClockDepthType(presetConfig.getRegion());
        clockBean.setDoubleRow(presetConfig.isDoubleRow());
        clockBean.setClockTopRatio(presetConfig.getTopWeight());
        clockBean.setClockBottomRatio(presetConfig.getBottomWeight());
        clockBean.setClockWeight(presetConfig.getClockWeight());
        int iResolveResIdWithSuffix = MutiDeviceDimenAdapter.resolveResIdWithSuffix(context, presetConfig.getWidthResId());
        int iResolveResIdWithSuffix2 = MutiDeviceDimenAdapter.resolveResIdWithSuffix(context, presetConfig.getHeightResId());
        clockBean.setTimeWidth(DeviceConfig.getDimenDpValue(iResolveResIdWithSuffix, context));
        clockBean.setTimeHeight(DeviceConfig.getDimenDpValue(iResolveResIdWithSuffix2, context));
        clockBean.setSkipScreenTransform(presetConfig.getSkipScreenTransform());
        if (!clockBean.isSkipScreenTransform() && DeviceConfig.FOLD_DEVICE) {
            float dimenDpValue = DeviceConfig.getDimenDpValue(presetConfig.getHeightResId(), context);
            float dimenDpValue2 = DeviceConfig.getDimenDpValue(R.dimen.miui_all_in_one_base_edit_area_height, context);
            if (dimenDpValue2 > 0.0f) {
                int[] foldOuterScreenSize = DeviceConfig.getFoldOuterScreenSize(context);
                if (foldOuterScreenSize != null) {
                    int i2 = foldOuterScreenSize[1];
                    float f = foldOuterScreenSize[2] / 160.0f;
                    ClockDepthAvoidRuleUtils.SafeBounds safeBoundsForSmallPort = ClockDepthAvoidRuleUtils.getSafeBoundsForSmallPort(context);
                    fPxToDp = ((i2 - ((safeBoundsForSmallPort != null ? safeBoundsForSmallPort.getTop() : 75.0f) * f)) - ((safeBoundsForSmallPort != null ? safeBoundsForSmallPort.getBottom() : 119.0f) * f)) / f;
                } else {
                    int[] screenWH = DeviceConfig.getScreenWH(context);
                    int iMin = Math.min(screenWH[0], screenWH[1]);
                    int iMax = Math.max(screenWH[0], screenWH[1]);
                    boolean zInFoldLargeScreen = DeviceConfig.inFoldLargeScreen(context);
                    if (!DeviceConfig.isQ18() || !zInFoldLargeScreen) {
                        iMin = iMax;
                    }
                    if (iMin == iMax) {
                        iMax = iMin;
                    }
                    fPxToDp = DeviceConfig.pxToDp(ClockDepthAvoidRuleUtils.createEditRect$default(context, iMax, iMin, ClockDepthAvoidRuleUtils.DEFAULT_CONFIG, null, null, 48, null).height(), context);
                }
                clockBean.setTimeHeight((dimenDpValue / dimenDpValue2) * fPxToDp);
                StringBuilder sb = new StringBuilder();
                sb.append("applyOtaClockParams Fold height scaling: phonePreset=");
                sb.append(dimenDpValue);
                sb.append(", base=");
                sb.append(dimenDpValue2);
                sb.append(", foldEditArea=");
                sb.append(fPxToDp);
                sb.append(", outerScreen=");
                if (foldOuterScreenSize != null) {
                    str = foldOuterScreenSize[0] + 'x' + foldOuterScreenSize[1] + '@' + foldOuterScreenSize[2] + "dpi";
                    if (str == null) {
                        str = "null(fallback)";
                    }
                } else {
                    str = "null(fallback)";
                }
                sb.append(str);
                sb.append(", newHeight=");
                sb.append(clockBean.getTimeHeight());
                Log.d("AllInOneConvertDataDebug", sb.toString());
            }
        }
        clockBean.setColonShow(presetConfig.isColonShow());
        clockBean.setFontStyle(presetConfig.getFontStyle());
        if (clockBean.getClassicLine1() < 100) {
            initDefaultDateComponent(clockBean, context);
        }
        applyOtaColorEffect(clockBean, context);
    }

    public final void applyOtaColorEffect(ClockBean clockBean, Context context) {
        if (!DeviceConfig.supportBackgroundBlur(context)) {
            clockBean.setClockEffect(0);
            return;
        }
        int clockEffect = clockBean.getClockEffect();
        if (clockEffect == 0 || clockEffect == 2) {
            return;
        }
        if (clockEffect != 4) {
            clockBean.setClockEffect(1);
            if (clockBean.isAutoPrimaryColor() && clockBean.isAutoSecondaryColor()) {
                return;
            }
            if (clockBean.isAutoPrimaryColor()) {
                clockBean.setSecondaryBlendColor(clockBean.getSecondaryColor());
                return;
            } else {
                if (clockBean.isAutoSecondaryColor()) {
                    clockBean.setBlendColor(clockBean.getPrimaryColor());
                    clockBean.setSecondaryBlendColor(clockBean.getPrimaryColor());
                    return;
                }
                return;
            }
        }
        clockBean.setClockEffect(1);
        if (clockBean.isAutoPrimaryColor() && clockBean.isAutoSecondaryColor()) {
            return;
        }
        if (clockBean.isAutoPrimaryColor()) {
            clockBean.setSecondaryBlendColor(clockBean.getSecondaryColor());
        } else if (clockBean.isAutoSecondaryColor()) {
            clockBean.setBlendColor(clockBean.getPrimaryColor());
            clockBean.setSecondaryBlendColor(clockBean.getPrimaryColor());
        } else {
            clockBean.setBlendColor(clockBean.getPrimaryColor());
            clockBean.setSecondaryBlendColor(clockBean.getSecondaryColor());
        }
    }

    public final ClockLayoutInput applySqrtTransform(ClockLayoutInput clockLayoutInput, int i, int i2, int i3, int i4) {
        float timeWidth;
        double timeHeight;
        double dSqrt;
        double d = ((double) i) / ((double) i2);
        double d2 = ((double) i3) / ((double) i4);
        if (clockLayoutInput.isDoubleRow()) {
            timeWidth = (float) (((double) clockLayoutInput.getTimeWidth()) * Math.sqrt(Math.sqrt(d)));
            timeHeight = clockLayoutInput.getTimeHeight();
            dSqrt = Math.sqrt(Math.sqrt(d2));
        } else {
            timeWidth = (float) (((double) clockLayoutInput.getTimeWidth()) * Math.sqrt(d));
            timeHeight = clockLayoutInput.getTimeHeight();
            dSqrt = Math.sqrt(d2);
        }
        return new ClockLayoutInput(clockLayoutInput.getTopRatio(), clockLayoutInput.getBottomRatio(), timeWidth, (float) (timeHeight * dSqrt), clockLayoutInput.getDepthLayer(), clockLayoutInput.isDoubleRow(), clockLayoutInput.getSignatureCount(), false, 0, 384, null);
    }

    public final RectComputeResult computeClockRect(Context context, Rect rect, ClockLayoutInput clockLayoutInput, String str) {
        float f;
        float f2;
        Configuration configuration;
        Configuration configuration2;
        Resources resources = context.getResources();
        int i = -1;
        int i2 = (resources == null || (configuration2 = resources.getConfiguration()) == null) ? -1 : configuration2.orientation;
        Resources resources2 = context.getResources();
        if (resources2 != null && (configuration = resources2.getConfiguration()) != null) {
            i = configuration.densityDpi;
        }
        int i3 = i;
        RectComputeResult rectComputeResult = cachedResult;
        if (rectComputeResult != null && Intrinsics.areEqual(clockLayoutInput, cachedInput) && Intrinsics.areEqual(rect, cachedEditRect) && i2 == cachedOrientation && i3 == cachedDensityDpi) {
            if (AllInOneDebug.DEBUG_LOG) {
                Log.d("AllInOneConvertDataDebug", "computeClockRect cache HIT, skip computation, cached: " + rectComputeResult);
            }
            return rectComputeResult;
        }
        float topRatio = clockLayoutInput.getTopRatio();
        float bottomRatio = clockLayoutInput.getBottomRatio();
        AllInOneClockStyle.Region depthLayer = clockLayoutInput.getDepthLayer();
        boolean zIsDoubleRow = clockLayoutInput.isDoubleRow();
        int signatureCount = clockLayoutInput.getSignatureCount();
        boolean z = AllInOneDebug.DEBUG_LOG;
        if (z) {
            Log.d("AllInOneConvertDataDebug", "final computeClockRect, clockInfo transform current screen: timeWidth " + clockLayoutInput.getTimeWidth() + ", timeHeight " + clockLayoutInput.getTimeHeight() + ", isDoubleRow=" + zIsDoubleRow + ", topWeight: " + topRatio + ", bottomWeight: " + bottomRatio + ", editRectWidth: " + rect.width() + ", editRectHeight: " + rect.height());
        }
        int iDpToPxRounded = DeviceConfig.dpToPxRounded(clockLayoutInput.getTimeWidth(), context);
        int iDpToPxRounded2 = DeviceConfig.dpToPxRounded(clockLayoutInput.getTimeHeight(), context);
        int dateTopToTime = getDateTopToTime(context);
        int signatureBottomToTime = getSignatureBottomToTime(context, signatureCount);
        if (z) {
            Log.d("AllInOneConvertDataDebug", "convert dp to real show px: timeWidthPx " + iDpToPxRounded + ", timeHeightPx " + iDpToPxRounded2 + ", dateTopToTime=" + dateTopToTime + ", signatureBottomToTime=" + signatureBottomToTime);
        }
        int iCoerceAtLeast = RangesKt.coerceAtLeast((rect.height() - dateTopToTime) - signatureBottomToTime, 0);
        int iMin = Math.min(iDpToPxRounded2, iCoerceAtLeast);
        RatioEnforceResult ratioEnforceResultEnforceTimeRatio = enforceTimeRatio(iDpToPxRounded, iMin, rect.width(), iCoerceAtLeast, zIsDoubleRow, 0.75f);
        if (z) {
            Log.d("AllInOneConvertDataDebug", "Single and double row constraints, before: " + iDpToPxRounded + " x " + iMin + ",  now: " + ratioEnforceResultEnforceTimeRatio.getTimeWidth() + " x " + ratioEnforceResultEnforceTimeRatio.getTimeHeight());
        }
        int timeWidth = ratioEnforceResultEnforceTimeRatio.getTimeWidth();
        int timeHeight = ratioEnforceResultEnforceTimeRatio.getTimeHeight();
        if (DeviceConfig.isPadDevice()) {
            AreaEnforceResult areaEnforceResultEnforceArea = enforceArea(context, timeWidth, timeHeight, zIsDoubleRow);
            timeWidth = areaEnforceResultEnforceArea.getTimeWidth();
            timeHeight = areaEnforceResultEnforceArea.getTimeHeight();
        }
        int i4 = timeWidth;
        int i5 = timeHeight;
        int iCoerceAtLeast2 = RangesKt.coerceAtLeast(rect.height() - ((dateTopToTime + i5) + signatureBottomToTime), 0);
        float f3 = 0.0f;
        if (iCoerceAtLeast2 <= 2) {
            f2 = 0.0f;
        } else {
            float f4 = bottomRatio + topRatio;
            if (f4 <= 0.0f) {
                f = iCoerceAtLeast2;
                f3 = (6.0f * f) / 13.0f;
            } else {
                f = iCoerceAtLeast2;
                f3 = (topRatio * f) / f4;
            }
            f2 = f - f3;
        }
        int i6 = rect.top + ((int) f3) + dateTopToTime;
        float f5 = (rect.left + rect.right) / 2.0f;
        float f6 = i4 / 2.0f;
        AllInOneBaseInfo.AllInOneRectParams allInOneRectParams = new AllInOneBaseInfo.AllInOneRectParams(depthLayer, f3 / rect.height(), f2 / rect.height(), i4, i5);
        Rect rect2 = new Rect((int) (f5 - f6), i6, (int) (f6 + f5), i6 + i5);
        if (z) {
            Log.i("AllInOneConvertDataDebug", str + ": outParams=" + allInOneRectParams + ", newRect=" + rect2);
        }
        RectComputeResult rectComputeResult2 = new RectComputeResult(rect2, allInOneRectParams);
        cachedInput = clockLayoutInput;
        cachedEditRect = new Rect(rect);
        cachedOrientation = i2;
        cachedDensityDpi = i3;
        cachedResult = rectComputeResult2;
        return rectComputeResult2;
    }

    public final RectComputeResult computeForLandscape(Context context, ClockLayoutInput params) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(params, "params");
        Log.d("AllInOneConvertDataDebug", "computeForLandscape: portrait(virtual) → landscape(current)");
        return computeForScreen(context, orientationTransformForDisplay(params, context), "computeForLandscape");
    }

    public final RectComputeResult computeForOuterScreen(Context context, ClockLayoutInput params) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(params, "params");
        Log.i("AllInOneConvertDataDebug", "current is Fold outerScreen, timeWidth: " + params.getTimeWidth() + ", convert outer width: " + (params.getTimeWidth() / 1.25f));
        return computeForScreen(context, ClockLayoutInput.copy$default(params, 0.0f, 0.0f, params.getTimeWidth() / 1.25f, 0.0f, null, false, 0, false, 0, 507, null), "computeForOuterScreen");
    }

    public final RectComputeResult computeForPortrait(Context context, ClockLayoutInput params) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(params, "params");
        return computeForScreen(context, params, "computeForPortrait");
    }

    public final RectComputeResult computeForQ18InnerPortrait(Context context, ClockLayoutInput clockLayoutInput) {
        Log.d("AllInOneConvertDataDebug", "Q18 display: landscape(virtual) → portrait(current)");
        return computeForScreen(context, orientationTransformForDisplay(clockLayoutInput, context), "computeForQ18InnerPortrait");
    }

    public final RectComputeResult computeForScreen(Context context, ClockLayoutInput clockLayoutInput, String str) {
        int[] screenWH = DeviceConfig.getScreenWH(context);
        return computeClockRect(context, ClockDepthAvoidRuleUtils.createEditRect$default(context, screenWH[0], screenWH[1], ClockDepthAvoidRuleUtils.DEFAULT_CONFIG, null, null, 48, null), clockLayoutInput, str);
    }

    public final RectComputeResult computeInnerFoldDevice(Context context, ClockLayoutInput clockLayoutInput, boolean z, boolean z2, String str) {
        if (z == z2) {
            Log.i("AllInOneConvertDataDebug", "current is " + str + " inner need sqrt transform, isPortrait=" + z);
            return z2 ? computeForQ18InnerPortrait(context, clockLayoutInput) : computeForLandscape(context, clockLayoutInput);
        }
        Log.i("AllInOneConvertDataDebug", "current is " + str + " inner use storage directly, isPortrait=" + z);
        return computeForScreen(context, clockLayoutInput, "computeFor" + str + "InnerBaseline");
    }

    public final RectComputeResult computeRectForConfigurationChanged(Context context, ClockLayoutInput clockLayoutInput) {
        int i = 0;
        if (context != null) {
            int[] screenWH = DeviceConfig.getScreenWH(context);
            i = screenWH[0] < screenWH[1] ? 1 : 2;
        }
        return computeRectForConfigurationChanged(context, clockLayoutInput, i);
    }

    /* JADX WARN: Code duplicated, block: B:31:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:32:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:37:0x0152  */
    /* JADX WARN: Code duplicated, block: B:39:0x015e  */
    /* JADX WARN: Code duplicated, block: B:41:0x0172  */
    /* JADX WARN: Code duplicated, block: B:43:0x017a  */
    /* JADX WARN: Code duplicated, block: B:47:0x01c3  */
    /* JADX WARN: Code duplicated, block: B:48:0x01ca  */
    /* JADX WARN: Code duplicated, block: B:51:0x01d9  */
    public final RectComputeResult computeRectForConfigurationChanged(Context context, ClockLayoutInput clockLayoutInput, int i) {
        RectComputeResult rectComputeResultComputeRectForPhoneOrFlip;
        RectComputeResult rectComputeResult;
        Rect rect;
        Rect rectCreateEditRect$default;
        int dateTopToTime;
        boolean z;
        int iComputeMagazinePositionY;
        int iIntValue;
        int iIntValue2;
        AllInOneBaseInfo.AllInOneRectParams rectParams;
        AllInOneBaseInfo.AllInOneRectParams rectParams2;
        Float fValueOf;
        float fHeight;
        float f;
        float f2;
        if (context == null || clockLayoutInput == null) {
            Log.d("AllInOneConvertDataDebug", "computeRectForConfigurationChanged: params invalid");
            return null;
        }
        Log.d("AllInOneConvertDataDebug", "preparing calculate current screen data, timeWidthDp: " + clockLayoutInput.getTimeWidth() + ", timeHeightDp: " + clockLayoutInput.getTimeHeight() + ", virtualTopRatio: " + clockLayoutInput.getTopRatio() + ", virtualBottomRatio: " + clockLayoutInput.getBottomRatio() + ", isDoubleRow= " + clockLayoutInput.isDoubleRow());
        if (DeviceConfig.isFlip() || DeviceConfig.isPhoneDevice()) {
            Log.i("AllInOneConvertDataDebug", "current is phone or flip");
            rectComputeResultComputeRectForPhoneOrFlip = computeRectForPhoneOrFlip(context, clockLayoutInput);
        } else {
            if (!DeviceConfig.isPadDevice()) {
                if (DeviceConfig.isFoldDevice()) {
                    rectComputeResultComputeRectForPhoneOrFlip = computeRectForFoldDevice(context, clockLayoutInput, i);
                } else {
                    rectComputeResult = null;
                }
                if (rectComputeResult != null && clockLayoutInput.getMagazineHeight() > 0 && !DeviceConfig.supportVariableFont() && (rect = rectComputeResult.getRect()) != null && rect.width() > 0) {
                    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                    rectCreateEditRect$default = ClockDepthAvoidRuleUtils.createEditRect$default(context, displayMetrics.widthPixels, displayMetrics.heightPixels, null, null, null, 56, null);
                    dateTopToTime = getDateTopToTime(context);
                    int i2 = rect.bottom;
                    if (clockLayoutInput.getSignatureCount() > 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    iComputeMagazinePositionY = computeMagazinePositionY(context, i2, z, clockLayoutInput.getSignatureCount());
                    Log.d("AllInOneConvertDataDebug", "magazine check: rect=" + rect + ", editRect=" + rectCreateEditRect$default + ", screenW=" + displayMetrics.widthPixels + ", screenH=" + displayMetrics.heightPixels + ", magazineY=" + iComputeMagazinePositionY + ", magazineH=" + clockLayoutInput.getMagazineHeight() + ", overflow=" + ((clockLayoutInput.getMagazineHeight() + iComputeMagazinePositionY) - rectCreateEditRect$default.bottom));
                    Pair pairAdjustForMagazineOverflow = adjustForMagazineOverflow(rect.top, rect.height(), iComputeMagazinePositionY, clockLayoutInput.getMagazineHeight(), rectCreateEditRect$default.top, rectCreateEditRect$default.bottom, dateTopToTime);
                    iIntValue = ((Number) pairAdjustForMagazineOverflow.component1()).intValue();
                    iIntValue2 = ((Number) pairAdjustForMagazineOverflow.component2()).intValue();
                    if (iIntValue == rect.top || iIntValue2 != rect.height()) {
                        rect.top = iIntValue;
                        rect.bottom = iIntValue + iIntValue2;
                        rectParams = rectComputeResult.getRectParams();
                        if (rectParams != null) {
                            fHeight = ((iIntValue - dateTopToTime) - rectCreateEditRect$default.top) / rectCreateEditRect$default.height();
                            f = rectParams.clockTopRatio;
                            if (f > 0.0f) {
                                f2 = (rectParams.clockBottomRatio * fHeight) / f;
                            } else {
                                f2 = rectParams.clockBottomRatio;
                            }
                            rectComputeResult.setRectParams(new AllInOneBaseInfo.AllInOneRectParams(rectParams.clockDepthType, fHeight, f2, rectParams.timeWidth, iIntValue2));
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append("magazine adjusted: magazineH=");
                        sb.append(clockLayoutInput.getMagazineHeight());
                        sb.append(", overflow=");
                        sb.append((iComputeMagazinePositionY + clockLayoutInput.getMagazineHeight()) - rectCreateEditRect$default.bottom);
                        sb.append(", rect=");
                        sb.append(rect);
                        sb.append(", newTopRatio=");
                        rectParams2 = rectComputeResult.getRectParams();
                        if (rectParams2 != null) {
                            fValueOf = Float.valueOf(rectParams2.clockTopRatio);
                        } else {
                            fValueOf = null;
                        }
                        sb.append(fValueOf);
                        sb.append(", newBottomRatio=");
                        AllInOneBaseInfo.AllInOneRectParams rectParams3 = rectComputeResult.getRectParams();
                        sb.append(rectParams3 != null ? Float.valueOf(rectParams3.clockBottomRatio) : null);
                        sb.append(", supportVariableFont=");
                        sb.append(DeviceConfig.supportVariableFont());
                        Log.d("AllInOneConvertDataDebug", sb.toString());
                    }
                }
                return rectComputeResult;
            }
            rectComputeResultComputeRectForPhoneOrFlip = computeRectForPad(context, clockLayoutInput, i);
        }
        rectComputeResult = rectComputeResultComputeRectForPhoneOrFlip;
        if (rectComputeResult != null) {
            DisplayMetrics displayMetrics2 = context.getResources().getDisplayMetrics();
            rectCreateEditRect$default = ClockDepthAvoidRuleUtils.createEditRect$default(context, displayMetrics2.widthPixels, displayMetrics2.heightPixels, null, null, null, 56, null);
            dateTopToTime = getDateTopToTime(context);
            int i3 = rect.bottom;
            if (clockLayoutInput.getSignatureCount() > 0) {
                z = true;
            } else {
                z = false;
            }
            iComputeMagazinePositionY = computeMagazinePositionY(context, i3, z, clockLayoutInput.getSignatureCount());
            Log.d("AllInOneConvertDataDebug", "magazine check: rect=" + rect + ", editRect=" + rectCreateEditRect$default + ", screenW=" + displayMetrics2.widthPixels + ", screenH=" + displayMetrics2.heightPixels + ", magazineY=" + iComputeMagazinePositionY + ", magazineH=" + clockLayoutInput.getMagazineHeight() + ", overflow=" + ((clockLayoutInput.getMagazineHeight() + iComputeMagazinePositionY) - rectCreateEditRect$default.bottom));
            Pair pairAdjustForMagazineOverflow2 = adjustForMagazineOverflow(rect.top, rect.height(), iComputeMagazinePositionY, clockLayoutInput.getMagazineHeight(), rectCreateEditRect$default.top, rectCreateEditRect$default.bottom, dateTopToTime);
            iIntValue = ((Number) pairAdjustForMagazineOverflow2.component1()).intValue();
            iIntValue2 = ((Number) pairAdjustForMagazineOverflow2.component2()).intValue();
            if (iIntValue == rect.top) {
                rect.top = iIntValue;
                rect.bottom = iIntValue + iIntValue2;
                rectParams = rectComputeResult.getRectParams();
                if (rectParams != null) {
                    fHeight = ((iIntValue - dateTopToTime) - rectCreateEditRect$default.top) / rectCreateEditRect$default.height();
                    f = rectParams.clockTopRatio;
                    if (f > 0.0f) {
                        f2 = (rectParams.clockBottomRatio * fHeight) / f;
                    } else {
                        f2 = rectParams.clockBottomRatio;
                    }
                    rectComputeResult.setRectParams(new AllInOneBaseInfo.AllInOneRectParams(rectParams.clockDepthType, fHeight, f2, rectParams.timeWidth, iIntValue2));
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append("magazine adjusted: magazineH=");
                sb2.append(clockLayoutInput.getMagazineHeight());
                sb2.append(", overflow=");
                sb2.append((iComputeMagazinePositionY + clockLayoutInput.getMagazineHeight()) - rectCreateEditRect$default.bottom);
                sb2.append(", rect=");
                sb2.append(rect);
                sb2.append(", newTopRatio=");
                rectParams2 = rectComputeResult.getRectParams();
                if (rectParams2 != null) {
                    fValueOf = Float.valueOf(rectParams2.clockTopRatio);
                } else {
                    fValueOf = null;
                }
                sb2.append(fValueOf);
                sb2.append(", newBottomRatio=");
                AllInOneBaseInfo.AllInOneRectParams rectParams4 = rectComputeResult.getRectParams();
                sb2.append(rectParams4 != null ? Float.valueOf(rectParams4.clockBottomRatio) : null);
                sb2.append(", supportVariableFont=");
                sb2.append(DeviceConfig.supportVariableFont());
                Log.d("AllInOneConvertDataDebug", sb2.toString());
            } else {
                rect.top = iIntValue;
                rect.bottom = iIntValue + iIntValue2;
                rectParams = rectComputeResult.getRectParams();
                if (rectParams != null) {
                    fHeight = ((iIntValue - dateTopToTime) - rectCreateEditRect$default.top) / rectCreateEditRect$default.height();
                    f = rectParams.clockTopRatio;
                    if (f > 0.0f) {
                        f2 = (rectParams.clockBottomRatio * fHeight) / f;
                    } else {
                        f2 = rectParams.clockBottomRatio;
                    }
                    rectComputeResult.setRectParams(new AllInOneBaseInfo.AllInOneRectParams(rectParams.clockDepthType, fHeight, f2, rectParams.timeWidth, iIntValue2));
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append("magazine adjusted: magazineH=");
                sb3.append(clockLayoutInput.getMagazineHeight());
                sb3.append(", overflow=");
                sb3.append((iComputeMagazinePositionY + clockLayoutInput.getMagazineHeight()) - rectCreateEditRect$default.bottom);
                sb3.append(", rect=");
                sb3.append(rect);
                sb3.append(", newTopRatio=");
                rectParams2 = rectComputeResult.getRectParams();
                if (rectParams2 != null) {
                    fValueOf = Float.valueOf(rectParams2.clockTopRatio);
                } else {
                    fValueOf = null;
                }
                sb3.append(fValueOf);
                sb3.append(", newBottomRatio=");
                AllInOneBaseInfo.AllInOneRectParams rectParams5 = rectComputeResult.getRectParams();
                sb3.append(rectParams5 != null ? Float.valueOf(rectParams5.clockBottomRatio) : null);
                sb3.append(", supportVariableFont=");
                sb3.append(DeviceConfig.supportVariableFont());
                Log.d("AllInOneConvertDataDebug", sb3.toString());
            }
        }
        return rectComputeResult;
    }

    public final RectComputeResult computeRectForFoldDevice(Context context, ClockLayoutInput clockLayoutInput, int i) {
        RectComputeResult rectComputeResultComputeForOuterScreen;
        boolean zIsQ18 = DeviceConfig.isQ18();
        String str = zIsQ18 ? "Q18" : "Fold";
        boolean z = i == 1;
        boolean zInFoldLargeScreen = DeviceConfig.inFoldLargeScreen(context);
        if (clockLayoutInput.getSkipScreenTransform()) {
            Log.i("AllInOneConvertDataDebug", str + " skipScreenTransform=true, skip all transform (outer/inner)");
            rectComputeResultComputeForOuterScreen = computeForScreen(context, clockLayoutInput, "computeFor" + str + "_skipTransform");
        } else {
            rectComputeResultComputeForOuterScreen = !zInFoldLargeScreen ? computeForOuterScreen(context, clockLayoutInput) : computeInnerFoldDevice(context, clockLayoutInput, z, zIsQ18, str);
        }
        Log.i("AllInOneUtil", str + ", curLarge=" + zInFoldLargeScreen + ", isPortrait=" + z);
        return rectComputeResultComputeForOuterScreen;
    }

    public final RectComputeResult computeRectForPad(Context context, ClockLayoutInput clockLayoutInput, int i) {
        Log.i("AllInOneConvertDataDebug", "computeRectForPad: skip=" + clockLayoutInput.getSkipScreenTransform() + ", w=" + clockLayoutInput.getTimeWidth() + ", h=" + clockLayoutInput.getTimeHeight() + ", ratio=" + (clockLayoutInput.getTimeHeight() > 0.0f ? clockLayoutInput.getTimeWidth() / clockLayoutInput.getTimeHeight() : 0.0f));
        if (clockLayoutInput.getSkipScreenTransform()) {
            Log.i("AllInOneConvertDataDebug", "PAD skipScreenTransform=true, skip sqrt transform");
            return computeForScreen(context, clockLayoutInput, "computeForPad_skipTransform");
        }
        if (i != 1) {
            Log.i("AllInOneConvertDataDebug", "current is PAD landscape");
            return computeForLandscape(context, clockLayoutInput);
        }
        Log.i("AllInOneConvertDataDebug", "current is PAD portrait");
        return computeForPortrait(context, clockLayoutInput);
    }

    public final RectComputeResult computeRectForPhoneOrFlip(Context context, ClockLayoutInput clockLayoutInput) {
        return computeForPortrait(context, clockLayoutInput);
    }

    public final ClockLayoutInput convertFoldDeviceToStorage(ClockLayoutInput clockLayoutInput, Context context) {
        boolean zIsQ18 = DeviceConfig.isQ18();
        String str = zIsQ18 ? "Q18" : "Fold";
        boolean zInFoldLargeScreen = DeviceConfig.inFoldLargeScreen(context);
        if (clockLayoutInput.getSkipScreenTransform() && zInFoldLargeScreen) {
            Log.d("AllInOneConvertDataDebug", str + " skipScreenTransform=true, skip storage transform");
            return clockLayoutInput;
        }
        if (zInFoldLargeScreen) {
            return convertInnerFoldStorage(clockLayoutInput, context, zIsQ18, str);
        }
        Log.d("AllInOneConvertDataDebug", "current is " + str + " outerScreen, transform width " + clockLayoutInput.getTimeWidth() + " → " + (clockLayoutInput.getTimeWidth() * 1.25f));
        return ClockLayoutInput.copy$default(clockLayoutInput, 0.0f, 0.0f, clockLayoutInput.getTimeWidth() * 1.25f, 0.0f, null, false, 0, false, 0, 507, null);
    }

    public final ClockLayoutInput convertInnerFoldStorage(ClockLayoutInput clockLayoutInput, Context context, boolean z, String str) {
        boolean zIsPortraitDevice = DeviceConfig.isPortraitDevice(context);
        if (zIsPortraitDevice == z) {
            Log.d("AllInOneConvertDataDebug", "current is " + str + " inner need sqrt transform, isPortrait=" + zIsPortraitDevice);
            return z ? convertSpecialPToLStorage(clockLayoutInput, context) : convertLToPStorage(clockLayoutInput, context);
        }
        Log.d("AllInOneConvertDataDebug", "current is " + str + " inner is storage baseline, return params, isPortrait=" + zIsPortraitDevice);
        return clockLayoutInput;
    }

    public final ClockLayoutInput convertLToPStorage(ClockLayoutInput clockLayoutInput, Context context) {
        Log.d("AllInOneConvertDataDebug", "convertLToPStorage: landscape(current) → portrait(virtual)");
        return orientationTransformForStorage(clockLayoutInput, context);
    }

    public final ClockLayoutInput convertPadToPortraitForStorage(ClockLayoutInput clockLayoutInput, Context context) {
        if (clockLayoutInput.getSkipScreenTransform()) {
            Log.d("AllInOneConvertDataDebug", "PAD skipScreenTransform=true, skip storage transform");
            return clockLayoutInput;
        }
        if (DeviceConfig.isPortraitDevice(context)) {
            Log.d("AllInOneConvertDataDebug", "current is PAD portrait, return params");
            return clockLayoutInput;
        }
        Log.d("AllInOneConvertDataDebug", "current is PAD landScape, transform params");
        return convertLToPStorage(clockLayoutInput, context);
    }

    public final ClockLayoutInput convertSpecialPToLStorage(ClockLayoutInput clockLayoutInput, Context context) {
        Log.d("AllInOneConvertDataDebug", "convertSpecialPToLStorage: portrait(current) → landscape(virtual)");
        return orientationTransformForStorage(clockLayoutInput, context);
    }

    public final ClockLayoutInput convertToStorageFormat(AllInOneBaseInfo.AllInOneRectParams allInOneRectParams, Context context, boolean z, boolean z2) {
        if (allInOneRectParams == null || context == null) {
            return null;
        }
        float fPxToDp = DeviceConfig.pxToDp(allInOneRectParams.timeWidth, context);
        float fPxToDp2 = DeviceConfig.pxToDp(allInOneRectParams.timeHeight, context);
        Log.d("AllInOneConvertDataDebug", "StorageFormat timeWidthDp: " + fPxToDp + ", timeHeightDp: " + fPxToDp2 + ", clockTopRatio: " + allInOneRectParams.clockTopRatio + ", clockBottomRatio: " + allInOneRectParams.clockBottomRatio);
        ClockLayoutInput clockLayoutInput = new ClockLayoutInput(allInOneRectParams.clockTopRatio, allInOneRectParams.clockBottomRatio, fPxToDp, fPxToDp2, allInOneRectParams.clockDepthType, z, 0, z2, 0, AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL, null);
        if (DeviceConfig.isFlip() || DeviceConfig.isPhoneDevice()) {
            Log.d("AllInOneConvertDataDebug", "current is phone or flip， return params");
            return clockLayoutInput;
        }
        if (DeviceConfig.isPadDevice()) {
            return convertPadToPortraitForStorage(clockLayoutInput, context);
        }
        return DeviceConfig.isFoldDevice() ? convertFoldDeviceToStorage(clockLayoutInput, context) : clockLayoutInput;
    }

    public final ClockBean copyClockBean(ClockBean bean) {
        Intrinsics.checkNotNullParameter(bean, "bean");
        Object objClone = bean.clone();
        Intrinsics.checkNotNull(objClone, "null cannot be cast to non-null type com.miui.clock.module.ClockBean");
        return (ClockBean) objClone;
    }

    public final AreaEnforceResult enforceArea(Context context, int i, int i2, boolean z) {
        int iCoerceAtLeast;
        int iCoerceAtLeast2;
        Configuration configuration;
        if (DeviceConfig.isPadDevice()) {
            int[] screenWH = DeviceConfig.getScreenWH(context);
            Resources resources = context.getResources();
            boolean z2 = ((resources == null || (configuration = resources.getConfiguration()) == null) ? 0 : configuration.orientation) == 2;
            float f = z2 ? 0.55f : 0.7f;
            float f2 = z2 ? 0.5f : 0.4f;
            int i3 = (int) (screenWH[0] * f);
            int i4 = (int) (screenWH[1] * f2);
            if (i > i3 || i2 > i4) {
                Log.d("AllInOneConvertDataDebug", "Pad area constraints, before=" + i + " x " + i2 + ", maxWidth=" + i3 + ", maxHeight=" + i4 + ", isLandscape=" + z2);
                float f3 = (float) i3;
                float f4 = (float) i;
                float f5 = (float) i2;
                float fMin = Math.min(f3 / f4, ((float) i4) / f5);
                iCoerceAtLeast = (int) (f4 * fMin);
                iCoerceAtLeast2 = (int) (f5 * fMin);
            } else {
                iCoerceAtLeast = i;
                iCoerceAtLeast2 = i2;
            }
            if (iCoerceAtLeast > 0 && iCoerceAtLeast2 > 0) {
                float f6 = iCoerceAtLeast;
                float f7 = iCoerceAtLeast2;
                float f8 = f6 / f7;
                if (z && f8 >= 0.75f) {
                    iCoerceAtLeast = RangesKt.coerceAtLeast((int) Math.floor(f7 * 0.75f), 1);
                } else if (!z && f8 < 0.75f) {
                    iCoerceAtLeast2 = RangesKt.coerceAtLeast((int) Math.floor(f6 / 0.75f), 1);
                }
            }
        } else {
            iCoerceAtLeast = i;
            iCoerceAtLeast2 = i2;
        }
        if (iCoerceAtLeast != i || iCoerceAtLeast2 != i2) {
            Log.d("AllInOneConvertDataDebug", "area constraints result: " + iCoerceAtLeast + " x " + iCoerceAtLeast2);
        }
        return new AreaEnforceResult(iCoerceAtLeast, iCoerceAtLeast2);
    }

    /* JADX WARN: Code duplicated, block: B:20:0x004b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:21:0x004d  */
    /* JADX WARN: Code duplicated, block: B:26:0x005b  */
    public final RatioEnforceResult enforceTimeRatio(int i, int i2, int i3, int i4, boolean z, float f) {
        int iMin;
        if (i <= 0 || i2 <= 0) {
            return new RatioEnforceResult(i, i2, false);
        }
        int iMin2 = Math.min(i, i3);
        int iMin3 = Math.min(i2, i4);
        float f2 = iMin2;
        float f3 = iMin3;
        float f4 = f2 / f3;
        if (!z) {
            if (f4 < f) {
                iMin = Math.min(RangesKt.coerceAtLeast((int) Math.floor(f2 / f), 1), i4);
            }
            float f5 = iMin2 / iMin;
            if (!z && f5 >= f) {
                iMin2 = RangesKt.coerceAtLeast(iMin2 - 1, 1);
            } else if (!z && f5 < f) {
                iMin = RangesKt.coerceAtLeast(iMin - 1, 1);
            }
            return new RatioEnforceResult(iMin2, iMin, iMin != iMin3);
        }
        if (f4 >= f) {
            iMin2 = RangesKt.coerceAtLeast((int) Math.floor(f3 * f), 0);
        }
        iMin = iMin3;
        float f6 = iMin2 / iMin;
        if (!z) {
            if (!z) {
                iMin = RangesKt.coerceAtLeast(iMin - 1, 1);
            }
        } else if (!z) {
            iMin = RangesKt.coerceAtLeast(iMin - 1, 1);
        }
        return new RatioEnforceResult(iMin2, iMin, iMin != iMin3);
    }

    public final int getDateTopToTime(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return DeviceConfig.getDimen(R.dimen.miui_all_in_one_text_area_height, context) + DeviceConfig.getDimen(R.dimen.miui_all_in_one_gap_between_text_area_and_time, context);
    }

    public final int getGlassSmallBlurRadius(float f) {
        return (int) (10 + (40 * f));
    }

    public final int getSignatureBottomToTime(Context context, int i) {
        int dimen;
        Intrinsics.checkNotNullParameter(context, "context");
        int dimen2 = DeviceConfig.getDimen(R.dimen.miui_all_in_one_gap_between_time_and_sigature, context);
        if (i != 1) {
            dimen = i != 2 ? 0 : DeviceConfig.getDimen(R.dimen.miui_all_in_one_signature_height_two_line, context);
        } else {
            dimen = DeviceConfig.getDimen(R.dimen.miui_all_in_one_signature_height_one_line, context);
        }
        if (dimen == 0) {
            return 0;
        }
        return dimen2 + dimen;
    }

    public final int getSignatureLines(Context context, String signatureContent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(signatureContent, "signatureContent");
        if (TextUtils.isEmpty(signatureContent)) {
            return 0;
        }
        int dimen = DeviceConfig.getDimen(R.dimen.classic_text_signature_max_width, context);
        TextPaint textPaint = new TextPaint();
        textPaint.setTypeface(FontUtils.getMiSans(380));
        textPaint.setTextSize(DeviceConfig.getDimen(R.dimen.classic_signature_text_size, context));
        StaticLayout staticLayoutBuild = StaticLayout.Builder.obtain(signatureContent, 0, signatureContent.length(), textPaint, dimen).build();
        Intrinsics.checkNotNullExpressionValue(staticLayoutBuild, "build(...)");
        return RangesKt.coerceIn(staticLayoutBuild.getLineCount(), 1, 2);
    }

    public final int getSignatureLines(TextView textView, String signatureContent) {
        Intrinsics.checkNotNullParameter(textView, "textView");
        Intrinsics.checkNotNullParameter(signatureContent, "signatureContent");
        if (TextUtils.isEmpty(signatureContent)) {
            return 0;
        }
        int dimen = (DeviceConfig.getDimen(R.dimen.classic_text_signature_max_width, textView.getContext()) - textView.getCompoundPaddingLeft()) - textView.getCompoundPaddingRight();
        if (dimen <= 0) {
            Context context = textView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            return getSignatureLines(context, signatureContent);
        }
        StaticLayout staticLayoutBuild = StaticLayout.Builder.obtain(signatureContent, 0, signatureContent.length(), textView.getPaint(), dimen).build();
        Intrinsics.checkNotNullExpressionValue(staticLayoutBuild, "build(...)");
        return RangesKt.coerceIn(staticLayoutBuild.getLineCount(), 1, 2);
    }

    public final void initDefaultDateComponent(ClockBean clockBean, Context context) {
        clockBean.setClassicLine1(ClockBean.getShowLunarCalendar(context, DateFormatUtils.updateCurrentUserId()) == 1 ? 103 : 100);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x001d, code lost:
    
        if (r3.equals("classic_max") != false) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x001f, code lost:
    
        switch(r4) {
            case 21: goto L21;
            case 22: goto L20;
            case 23: goto L18;
            case 24: goto L16;
            case 25: goto L14;
            default: goto L13;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0022, code lost:
    
        return 21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0023, code lost:
    
        return 25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0026, code lost:
    
        return 24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0029, code lost:
    
        return 23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x002c, code lost:
    
        return 22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x002d, code lost:
    
        return 21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0014, code lost:
    
        if (r3.equals("classic_plus") == false) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int mapOtaFontStyle(java.lang.String r3, int r4) {
        /*
            r2 = this;
            r2 = 21
            if (r3 == 0) goto L46
            int r0 = r3.hashCode()
            r1 = 22
            switch(r0) {
                case -1744945801: goto L43;
                case -1744945800: goto L3d;
                case -567074184: goto L2e;
                case -138709961: goto L17;
                case -4941529: goto Le;
                default: goto Ld;
            }
        Ld:
            goto L46
        Le:
            java.lang.String r0 = "classic_plus"
            boolean r3 = r3.equals(r0)
            if (r3 != 0) goto L1f
            goto L46
        L17:
            java.lang.String r0 = "classic_max"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L46
        L1f:
            switch(r4) {
                case 21: goto L2d;
                case 22: goto L2c;
                case 23: goto L29;
                case 24: goto L26;
                case 25: goto L23;
                default: goto L22;
            }
        L22:
            return r2
        L23:
            r2 = 25
            return r2
        L26:
            r2 = 24
            return r2
        L29:
            r2 = 23
            return r2
        L2c:
            return r1
        L2d:
            return r2
        L2e:
            java.lang.String r0 = "magazine_c"
            boolean r3 = r3.equals(r0)
            if (r3 != 0) goto L37
            goto L46
        L37:
            r3 = 9
            if (r4 != r3) goto L3c
            return r1
        L3c:
            return r2
        L3d:
            java.lang.String r4 = "oversize_b"
        L3f:
            r3.equals(r4)
            goto L46
        L43:
            java.lang.String r4 = "oversize_a"
            goto L3f
        L46:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.clock.allInOne.AllInOneUtil.mapOtaFontStyle(java.lang.String, int):int");
    }

    public final int mapOtaHollowStyle(String str, int i) {
        if (Intrinsics.areEqual(str, "oversize_a")) {
            if (i != 2) {
                return i != 3 ? 1 : 3;
            }
            return 4;
        }
        if (!Intrinsics.areEqual(str, "magazine_c")) {
            return 1;
        }
        if (i != 6) {
            return i != 8 ? 1 : 4;
        }
        return 3;
    }

    public final String normalizeClassicSignature(String str, Context context) {
        if (str != null && str.length() != 0 && !StringsKt.contains$default((CharSequence) str, '\n', false, 2, (Object) null) && !StringsKt.contains$default((CharSequence) str, '\r', false, 2, (Object) null)) {
            int dimen = DeviceConfig.getDimen(R.dimen.classic_text_signature_max_width, context);
            float dimen2 = DeviceConfig.getDimen(R.dimen.classic_signature_text_size, context);
            TextPaint textPaint = new TextPaint();
            textPaint.setTextSize(dimen2);
            textPaint.setTypeface(FontUtils.getMiSans(380));
            StaticLayout staticLayoutBuild = StaticLayout.Builder.obtain(str, 0, str.length(), textPaint, dimen).build();
            Intrinsics.checkNotNullExpressionValue(staticLayoutBuild, "build(...)");
            if (staticLayoutBuild.getLineCount() > 1) {
                int lineEnd = staticLayoutBuild.getLineEnd(0);
                String strSubstring = str.substring(0, lineEnd);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                String string = StringsKt.trimEnd(strSubstring).toString();
                String strSubstring2 = str.substring(lineEnd);
                Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                return string + '\n' + strSubstring2;
            }
        }
        return str;
    }

    public final ClockLayoutInput orientationTransformForDisplay(ClockLayoutInput clockLayoutInput, Context context) {
        int[] screenWH = DeviceConfig.getScreenWH(context);
        int i = screenWH[0];
        int i2 = screenWH[1];
        ClockLayoutInput clockLayoutInputApplySqrtTransform = applySqrtTransform(clockLayoutInput, i, i2, i2, i);
        Log.d("AllInOneConvertDataDebug", "orientationTransformForDisplay: isDoubleRow=" + clockLayoutInput.isDoubleRow() + ", in=(" + clockLayoutInput.getTimeWidth() + ", " + clockLayoutInput.getTimeHeight() + "), out=(" + clockLayoutInputApplySqrtTransform.getTimeWidth() + ", " + clockLayoutInputApplySqrtTransform.getTimeHeight() + ')');
        return clockLayoutInputApplySqrtTransform;
    }

    public final ClockLayoutInput orientationTransformForStorage(ClockLayoutInput clockLayoutInput, Context context) {
        int[] screenWH = DeviceConfig.getScreenWH(context);
        int i = screenWH[0];
        int i2 = screenWH[1];
        ClockLayoutInput clockLayoutInputApplySqrtTransform = applySqrtTransform(clockLayoutInput, i2, i, i, i2);
        Log.d("AllInOneConvertDataDebug", "orientationTransformForStorage: isDoubleRow=" + clockLayoutInput.isDoubleRow() + ", in=(" + clockLayoutInput.getTimeWidth() + ", " + clockLayoutInput.getTimeHeight() + "), out=(" + clockLayoutInputApplySqrtTransform.getTimeWidth() + ", " + clockLayoutInputApplySqrtTransform.getTimeHeight() + ')');
        return clockLayoutInputApplySqrtTransform;
    }

    public final void updateFontFeatureSettings(Paint paint, boolean z, boolean z2) {
        String str;
        Intrinsics.checkNotNullParameter(paint, "paint");
        if (z && z2) {
            str = "tnum, ss01";
        } else if (z) {
            str = "ss01";
        } else {
            str = z2 ? "tnum" : null;
        }
        paint.setFontFeatureSettings(str);
    }
}
