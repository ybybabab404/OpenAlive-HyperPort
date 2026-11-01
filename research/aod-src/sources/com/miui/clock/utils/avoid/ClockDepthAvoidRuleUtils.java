package com.miui.clock.utils.avoid;

import android.content.Context;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.Log;
import androidx.annotation.Keep;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.utils.DeviceConfig;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
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
/* JADX INFO: compiled from: ClockDepthAvoidRuleUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ClockDepthAvoidRuleUtils {
    public static SafeBoundsConfig sCachedConfig;
    public static final ClockDepthAvoidRuleUtils INSTANCE = new ClockDepthAvoidRuleUtils();
    public static final Config DEFAULT_CONFIG = new Config(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 32767, null);

    /* JADX INFO: compiled from: ClockDepthAvoidRuleUtils.kt */
    @Metadata
    public static final class ClockParams {
        public final int baseDisplayOrientation;
        public final float clockBottomRatio;
        public final float clockTopRatio;
        public final int depthLayer;
        public final float timeHeight;
        public final float timeWidth;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ClockParams)) {
                return false;
            }
            ClockParams clockParams = (ClockParams) obj;
            return Float.compare(this.clockTopRatio, clockParams.clockTopRatio) == 0 && Float.compare(this.clockBottomRatio, clockParams.clockBottomRatio) == 0 && this.baseDisplayOrientation == clockParams.baseDisplayOrientation && Float.compare(this.timeWidth, clockParams.timeWidth) == 0 && Float.compare(this.timeHeight, clockParams.timeHeight) == 0 && this.depthLayer == clockParams.depthLayer;
        }

        public int hashCode() {
            return (((((((((Float.hashCode(this.clockTopRatio) * 31) + Float.hashCode(this.clockBottomRatio)) * 31) + Integer.hashCode(this.baseDisplayOrientation)) * 31) + Float.hashCode(this.timeWidth)) * 31) + Float.hashCode(this.timeHeight)) * 31) + Integer.hashCode(this.depthLayer);
        }

        public String toString() {
            return "ClockParams(clockTopRatio=" + this.clockTopRatio + ", clockBottomRatio=" + this.clockBottomRatio + ", baseDisplayOrientation=" + this.baseDisplayOrientation + ", timeWidth=" + this.timeWidth + ", timeHeight=" + this.timeHeight + ", depthLayer=" + this.depthLayer + ')';
        }
    }

    /* JADX INFO: compiled from: ClockDepthAvoidRuleUtils.kt */
    @Metadata
    public static final class Config {
        public final float doubleLineAspectRatioThresholdWOverH;
        public final float editWidthRatio;
        public final float narrowEditHeightRatio;
        public final float narrowEditTopOffsetRatio;
        public final float narrowScreenWidthDp;
        public final float region1ClockAreaRatio;
        public final float region2HalfHeightRatio;
        public final float region2WidthRatio;
        public final float region3HalfHeightRatio;
        public final float region3SingleLineHeightRatio;
        public final float region3SingleLineWidthRatio;
        public final float region3WidthRatio;
        public final float wideEditHeightRatio;
        public final float wideEditTopOffsetRatio;
        public final float yellowTopOffsetRatioInScreenH;

        public Config(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15) {
            this.editWidthRatio = f;
            this.narrowScreenWidthDp = f2;
            this.narrowEditHeightRatio = f3;
            this.wideEditHeightRatio = f4;
            this.narrowEditTopOffsetRatio = f5;
            this.wideEditTopOffsetRatio = f6;
            this.yellowTopOffsetRatioInScreenH = f7;
            this.region1ClockAreaRatio = f8;
            this.region2HalfHeightRatio = f9;
            this.region2WidthRatio = f10;
            this.region3HalfHeightRatio = f11;
            this.region3WidthRatio = f12;
            this.region3SingleLineHeightRatio = f13;
            this.region3SingleLineWidthRatio = f14;
            this.doubleLineAspectRatioThresholdWOverH = f15;
        }

        public /* synthetic */ Config(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 0.86f : f, (i & 2) != 0 ? 500.0f : f2, (i & 4) != 0 ? 0.83f : f3, (i & 8) != 0 ? 0.79f : f4, (i & 16) != 0 ? 0.07f : f5, (i & 32) != 0 ? 0.08f : f6, (i & 64) != 0 ? 0.11f : f7, (i & 128) != 0 ? 0.3f : f8, (i & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? 0.35f : f9, (i & 512) != 0 ? 0.7f : f10, (i & 1024) == 0 ? f11 : 0.35f, (i & AutoBottomSheetKt.ALIGN_SCREEN_END) == 0 ? f12 : 0.7f, (i & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? 0.25f : f13, (i & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? 0.45f : f14, (i & 16384) != 0 ? 0.75f : f15);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Config)) {
                return false;
            }
            Config config = (Config) obj;
            return Float.compare(this.editWidthRatio, config.editWidthRatio) == 0 && Float.compare(this.narrowScreenWidthDp, config.narrowScreenWidthDp) == 0 && Float.compare(this.narrowEditHeightRatio, config.narrowEditHeightRatio) == 0 && Float.compare(this.wideEditHeightRatio, config.wideEditHeightRatio) == 0 && Float.compare(this.narrowEditTopOffsetRatio, config.narrowEditTopOffsetRatio) == 0 && Float.compare(this.wideEditTopOffsetRatio, config.wideEditTopOffsetRatio) == 0 && Float.compare(this.yellowTopOffsetRatioInScreenH, config.yellowTopOffsetRatioInScreenH) == 0 && Float.compare(this.region1ClockAreaRatio, config.region1ClockAreaRatio) == 0 && Float.compare(this.region2HalfHeightRatio, config.region2HalfHeightRatio) == 0 && Float.compare(this.region2WidthRatio, config.region2WidthRatio) == 0 && Float.compare(this.region3HalfHeightRatio, config.region3HalfHeightRatio) == 0 && Float.compare(this.region3WidthRatio, config.region3WidthRatio) == 0 && Float.compare(this.region3SingleLineHeightRatio, config.region3SingleLineHeightRatio) == 0 && Float.compare(this.region3SingleLineWidthRatio, config.region3SingleLineWidthRatio) == 0 && Float.compare(this.doubleLineAspectRatioThresholdWOverH, config.doubleLineAspectRatioThresholdWOverH) == 0;
        }

        public final float getDoubleLineAspectRatioThresholdWOverH() {
            return this.doubleLineAspectRatioThresholdWOverH;
        }

        public final float getEditWidthRatio() {
            return this.editWidthRatio;
        }

        public final float getRegion1ClockAreaRatio() {
            return this.region1ClockAreaRatio;
        }

        public final float getRegion2HalfHeightRatio() {
            return this.region2HalfHeightRatio;
        }

        public final float getRegion2WidthRatio() {
            return this.region2WidthRatio;
        }

        public final float getRegion3HalfHeightRatio() {
            return this.region3HalfHeightRatio;
        }

        public final float getRegion3SingleLineHeightRatio() {
            return this.region3SingleLineHeightRatio;
        }

        public final float getRegion3SingleLineWidthRatio() {
            return this.region3SingleLineWidthRatio;
        }

        public final float getRegion3WidthRatio() {
            return this.region3WidthRatio;
        }

        public final float getYellowTopOffsetRatioInScreenH() {
            return this.yellowTopOffsetRatioInScreenH;
        }

        public int hashCode() {
            return (((((((((((((((((((((((((((Float.hashCode(this.editWidthRatio) * 31) + Float.hashCode(this.narrowScreenWidthDp)) * 31) + Float.hashCode(this.narrowEditHeightRatio)) * 31) + Float.hashCode(this.wideEditHeightRatio)) * 31) + Float.hashCode(this.narrowEditTopOffsetRatio)) * 31) + Float.hashCode(this.wideEditTopOffsetRatio)) * 31) + Float.hashCode(this.yellowTopOffsetRatioInScreenH)) * 31) + Float.hashCode(this.region1ClockAreaRatio)) * 31) + Float.hashCode(this.region2HalfHeightRatio)) * 31) + Float.hashCode(this.region2WidthRatio)) * 31) + Float.hashCode(this.region3HalfHeightRatio)) * 31) + Float.hashCode(this.region3WidthRatio)) * 31) + Float.hashCode(this.region3SingleLineHeightRatio)) * 31) + Float.hashCode(this.region3SingleLineWidthRatio)) * 31) + Float.hashCode(this.doubleLineAspectRatioThresholdWOverH);
        }

        public String toString() {
            return "Config(editWidthRatio=" + this.editWidthRatio + ", narrowScreenWidthDp=" + this.narrowScreenWidthDp + ", narrowEditHeightRatio=" + this.narrowEditHeightRatio + ", wideEditHeightRatio=" + this.wideEditHeightRatio + ", narrowEditTopOffsetRatio=" + this.narrowEditTopOffsetRatio + ", wideEditTopOffsetRatio=" + this.wideEditTopOffsetRatio + ", yellowTopOffsetRatioInScreenH=" + this.yellowTopOffsetRatioInScreenH + ", region1ClockAreaRatio=" + this.region1ClockAreaRatio + ", region2HalfHeightRatio=" + this.region2HalfHeightRatio + ", region2WidthRatio=" + this.region2WidthRatio + ", region3HalfHeightRatio=" + this.region3HalfHeightRatio + ", region3WidthRatio=" + this.region3WidthRatio + ", region3SingleLineHeightRatio=" + this.region3SingleLineHeightRatio + ", region3SingleLineWidthRatio=" + this.region3SingleLineWidthRatio + ", doubleLineAspectRatioThresholdWOverH=" + this.doubleLineAspectRatioThresholdWOverH + ')';
        }
    }

    /* JADX INFO: compiled from: ClockDepthAvoidRuleUtils.kt */
    @Metadata
    public enum LayoutMode {
        SINGLE_LINE,
        DOUBLE_LINE;

        public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    }

    /* JADX INFO: compiled from: ClockDepthAvoidRuleUtils.kt */
    @Metadata
    public static final class NormalizedRectMetrics {
        public final float bottomRatio;
        public final float height;
        public final float topRatio;
        public final float width;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NormalizedRectMetrics)) {
                return false;
            }
            NormalizedRectMetrics normalizedRectMetrics = (NormalizedRectMetrics) obj;
            return Float.compare(this.topRatio, normalizedRectMetrics.topRatio) == 0 && Float.compare(this.bottomRatio, normalizedRectMetrics.bottomRatio) == 0 && Float.compare(this.width, normalizedRectMetrics.width) == 0 && Float.compare(this.height, normalizedRectMetrics.height) == 0;
        }

        public int hashCode() {
            return (((((Float.hashCode(this.topRatio) * 31) + Float.hashCode(this.bottomRatio)) * 31) + Float.hashCode(this.width)) * 31) + Float.hashCode(this.height);
        }

        public String toString() {
            return "NormalizedRectMetrics(topRatio=" + this.topRatio + ", bottomRatio=" + this.bottomRatio + ", width=" + this.width + ", height=" + this.height + ')';
        }
    }

    /* JADX INFO: compiled from: ClockDepthAvoidRuleUtils.kt */
    @Metadata
    public static final class RectMetrics {
        public final int bottom;
        public final float height;
        public final int left;
        public final int right;
        public final int top;
        public final float width;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RectMetrics)) {
                return false;
            }
            RectMetrics rectMetrics = (RectMetrics) obj;
            return Float.compare(this.width, rectMetrics.width) == 0 && Float.compare(this.height, rectMetrics.height) == 0 && this.left == rectMetrics.left && this.top == rectMetrics.top && this.right == rectMetrics.right && this.bottom == rectMetrics.bottom;
        }

        public int hashCode() {
            return (((((((((Float.hashCode(this.width) * 31) + Float.hashCode(this.height)) * 31) + Integer.hashCode(this.left)) * 31) + Integer.hashCode(this.top)) * 31) + Integer.hashCode(this.right)) * 31) + Integer.hashCode(this.bottom);
        }

        public String toString() {
            return "RectMetrics(width=" + this.width + ", height=" + this.height + ", left=" + this.left + ", top=" + this.top + ", right=" + this.right + ", bottom=" + this.bottom + ')';
        }
    }

    /* JADX INFO: compiled from: ClockDepthAvoidRuleUtils.kt */
    @Metadata
    public static final class RegionBounds {
        public final float yellowBottom;
        public final float yellowTop;

        public RegionBounds(float f, float f2) {
            this.yellowTop = f;
            this.yellowBottom = f2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RegionBounds)) {
                return false;
            }
            RegionBounds regionBounds = (RegionBounds) obj;
            return Float.compare(this.yellowTop, regionBounds.yellowTop) == 0 && Float.compare(this.yellowBottom, regionBounds.yellowBottom) == 0;
        }

        public final float getYellowBottom() {
            return this.yellowBottom;
        }

        public final float getYellowTop() {
            return this.yellowTop;
        }

        public int hashCode() {
            return (Float.hashCode(this.yellowTop) * 31) + Float.hashCode(this.yellowBottom);
        }

        public String toString() {
            return "RegionBounds(yellowTop=" + this.yellowTop + ", yellowBottom=" + this.yellowBottom + ')';
        }
    }

    /* JADX INFO: compiled from: ClockDepthAvoidRuleUtils.kt */
    @Retention(RetentionPolicy.SOURCE)
    @Metadata
    public @interface RegionInt {
    }

    /* JADX INFO: compiled from: ClockDepthAvoidRuleUtils.kt */
    @Keep
    @Metadata
    public static final class SafeBounds {

        @SerializedName("bottom")
        private final float bottom;

        @SerializedName("top")
        private final float top;

        /* JADX WARN: Illegal instructions before constructor call */
        public SafeBounds() {
            float f = 0.0f;
            this(f, f, 3, null);
        }

        public SafeBounds(float f, float f2) {
            this.top = f;
            this.bottom = f2;
        }

        public /* synthetic */ SafeBounds(float f, float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2);
        }

        public static /* synthetic */ SafeBounds copy$default(SafeBounds safeBounds, float f, float f2, int i, Object obj) {
            if ((i & 1) != 0) {
                f = safeBounds.top;
            }
            if ((i & 2) != 0) {
                f2 = safeBounds.bottom;
            }
            return safeBounds.copy(f, f2);
        }

        public final float component1() {
            return this.top;
        }

        public final float component2() {
            return this.bottom;
        }

        @NotNull
        public final SafeBounds copy(float f, float f2) {
            return new SafeBounds(f, f2);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SafeBounds)) {
                return false;
            }
            SafeBounds safeBounds = (SafeBounds) obj;
            return Float.compare(this.top, safeBounds.top) == 0 && Float.compare(this.bottom, safeBounds.bottom) == 0;
        }

        public final float getBottom() {
            return this.bottom;
        }

        public final float getTop() {
            return this.top;
        }

        public int hashCode() {
            return (Float.hashCode(this.top) * 31) + Float.hashCode(this.bottom);
        }

        @NotNull
        public String toString() {
            return "SafeBounds(top=" + this.top + ", bottom=" + this.bottom + ')';
        }
    }

    /* JADX INFO: compiled from: ClockDepthAvoidRuleUtils.kt */
    @Keep
    @Metadata
    public static final class SafeBoundsConfig {

        @SerializedName("largeLand")
        @Nullable
        private final SafeBounds largeLand;

        @SerializedName("largePort")
        @Nullable
        private final SafeBounds largePort;

        @SerializedName("smallPort")
        @Nullable
        private final SafeBounds smallPort;

        @SerializedName("version")
        private final int version;

        public SafeBoundsConfig() {
            this(0, null, null, null, 15, null);
        }

        public SafeBoundsConfig(int i, @Nullable SafeBounds safeBounds, @Nullable SafeBounds safeBounds2, @Nullable SafeBounds safeBounds3) {
            this.version = i;
            this.smallPort = safeBounds;
            this.largePort = safeBounds2;
            this.largeLand = safeBounds3;
        }

        public /* synthetic */ SafeBoundsConfig(int i, SafeBounds safeBounds, SafeBounds safeBounds2, SafeBounds safeBounds3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? null : safeBounds, (i2 & 4) != 0 ? null : safeBounds2, (i2 & 8) != 0 ? null : safeBounds3);
        }

        public static /* synthetic */ SafeBoundsConfig copy$default(SafeBoundsConfig safeBoundsConfig, int i, SafeBounds safeBounds, SafeBounds safeBounds2, SafeBounds safeBounds3, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = safeBoundsConfig.version;
            }
            if ((i2 & 2) != 0) {
                safeBounds = safeBoundsConfig.smallPort;
            }
            if ((i2 & 4) != 0) {
                safeBounds2 = safeBoundsConfig.largePort;
            }
            if ((i2 & 8) != 0) {
                safeBounds3 = safeBoundsConfig.largeLand;
            }
            return safeBoundsConfig.copy(i, safeBounds, safeBounds2, safeBounds3);
        }

        public final int component1() {
            return this.version;
        }

        @Nullable
        public final SafeBounds component2() {
            return this.smallPort;
        }

        @Nullable
        public final SafeBounds component3() {
            return this.largePort;
        }

        @Nullable
        public final SafeBounds component4() {
            return this.largeLand;
        }

        @NotNull
        public final SafeBoundsConfig copy(int i, @Nullable SafeBounds safeBounds, @Nullable SafeBounds safeBounds2, @Nullable SafeBounds safeBounds3) {
            return new SafeBoundsConfig(i, safeBounds, safeBounds2, safeBounds3);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SafeBoundsConfig)) {
                return false;
            }
            SafeBoundsConfig safeBoundsConfig = (SafeBoundsConfig) obj;
            return this.version == safeBoundsConfig.version && Intrinsics.areEqual(this.smallPort, safeBoundsConfig.smallPort) && Intrinsics.areEqual(this.largePort, safeBoundsConfig.largePort) && Intrinsics.areEqual(this.largeLand, safeBoundsConfig.largeLand);
        }

        @Nullable
        public final SafeBounds getLargeLand() {
            return this.largeLand;
        }

        @Nullable
        public final SafeBounds getLargePort() {
            return this.largePort;
        }

        @Nullable
        public final SafeBounds getSmallPort() {
            return this.smallPort;
        }

        public final int getVersion() {
            return this.version;
        }

        public int hashCode() {
            int iHashCode = Integer.hashCode(this.version) * 31;
            SafeBounds safeBounds = this.smallPort;
            int iHashCode2 = (iHashCode + (safeBounds == null ? 0 : safeBounds.hashCode())) * 31;
            SafeBounds safeBounds2 = this.largePort;
            int iHashCode3 = (iHashCode2 + (safeBounds2 == null ? 0 : safeBounds2.hashCode())) * 31;
            SafeBounds safeBounds3 = this.largeLand;
            return iHashCode3 + (safeBounds3 != null ? safeBounds3.hashCode() : 0);
        }

        @NotNull
        public String toString() {
            return "SafeBoundsConfig(version=" + this.version + ", smallPort=" + this.smallPort + ", largePort=" + this.largePort + ", largeLand=" + this.largeLand + ')';
        }
    }

    public static final Rect computeAvoidRect(Rect clockRect, AllInOneClockStyle.Region region, Config config) {
        Intrinsics.checkNotNullParameter(clockRect, "clockRect");
        Intrinsics.checkNotNullParameter(region, "region");
        Intrinsics.checkNotNullParameter(config, "config");
        return computeAvoidRect(clockRect, region, getLayoutMode(clockRect, config), config);
    }

    public static final Rect computeAvoidRect(Rect clockRect, AllInOneClockStyle.Region region, LayoutMode layoutMode, Config config) {
        Rect rect;
        Rect rect2;
        Intrinsics.checkNotNullParameter(clockRect, "clockRect");
        Intrinsics.checkNotNullParameter(region, "region");
        Intrinsics.checkNotNullParameter(layoutMode, "layoutMode");
        Intrinsics.checkNotNullParameter(config, "config");
        if (clockRect.width() <= 0 || clockRect.height() <= 0) {
            return new Rect();
        }
        float fHeight = clockRect.height();
        float fWidth = clockRect.width();
        boolean z = layoutMode == LayoutMode.DOUBLE_LINE;
        if (z) {
            int i = clockRect.left;
            int i2 = clockRect.top;
            rect = new Rect(i, i2, clockRect.right, (int) (i2 + (fHeight / 2.0f)));
        } else {
            int i3 = clockRect.left;
            rect = new Rect(i3, clockRect.top, (int) (i3 + (fWidth / 2.0f)), clockRect.bottom);
        }
        Rect rect3 = z ? new Rect(clockRect.left, (int) (clockRect.top + (fHeight / 2.0f)), clockRect.right, clockRect.bottom) : new Rect((int) (clockRect.left + (fWidth / 2.0f)), clockRect.top, clockRect.right, clockRect.bottom);
        int regionInt = AllInOneClockStyle.toRegionInt(region);
        if (regionInt == 0) {
            float fSqrt = (float) Math.sqrt(config.getRegion1ClockAreaRatio());
            int iCoerceAtLeast = RangesKt.coerceAtLeast((int) (fWidth * fSqrt), 1);
            int iCoerceAtLeast2 = RangesKt.coerceAtLeast((int) (fHeight * fSqrt), 1);
            int i4 = (int) (((clockRect.left + clockRect.right) / 2.0f) - (iCoerceAtLeast / 2.0f));
            int i5 = clockRect.top;
            rect2 = new Rect(i4, i5, iCoerceAtLeast + i4, iCoerceAtLeast2 + i5);
        } else if (regionInt != 1) {
            float fHeight2 = rect.height();
            float fWidth2 = rect.width();
            float region3HalfHeightRatio = z ? config.getRegion3HalfHeightRatio() : config.getRegion3SingleLineHeightRatio();
            float region3WidthRatio = z ? config.getRegion3WidthRatio() : config.getRegion3SingleLineWidthRatio();
            int iCoerceAtLeast3 = RangesKt.coerceAtLeast((int) (fHeight2 * region3HalfHeightRatio), 1);
            int iCoerceAtLeast4 = RangesKt.coerceAtLeast((int) (fWidth2 * region3WidthRatio), 1);
            int i6 = (int) (((rect.left + rect.right) / 2.0f) - (iCoerceAtLeast4 / 2.0f));
            int i7 = z ? rect.top : (int) (((rect.top + rect.bottom) / 2.0f) - (iCoerceAtLeast3 / 2.0f));
            rect2 = new Rect(i6, i7, iCoerceAtLeast4 + i6, iCoerceAtLeast3 + i7);
        } else {
            float fHeight3 = rect3.height();
            float fWidth3 = rect3.width();
            int iCoerceAtLeast5 = RangesKt.coerceAtLeast((int) (fHeight3 * config.getRegion2HalfHeightRatio()), 1);
            int iCoerceAtLeast6 = RangesKt.coerceAtLeast((int) (fWidth3 * config.getRegion2WidthRatio()), 1);
            int i8 = z ? (int) (((rect3.left + rect3.right) / 2.0f) - (iCoerceAtLeast6 / 2.0f)) : rect3.right - iCoerceAtLeast6;
            int i9 = rect3.top;
            rect2 = new Rect(i8, i9, iCoerceAtLeast6 + i8, iCoerceAtLeast5 + i9);
        }
        if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
            StringBuilder sb = new StringBuilder();
            sb.append("computeAvoidRect: region=REGION_");
            sb.append(regionInt);
            sb.append(", layout=");
            sb.append(z ? "DOUBLE" : "SINGLE");
            sb.append(", clockRect=");
            sb.append(clockRect);
            sb.append(", hourRect=");
            sb.append(rect);
            sb.append(", minuteRect=");
            sb.append(rect3);
            sb.append(", avoidRect=");
            sb.append(rect2);
            Log.d("ClockDepthAvoidRuleUtils", sb.toString());
        }
        return rect2;
    }

    public static final Rect computeClockRectFromEditRect(Rect editRect, float f, float f2, float f3) {
        Intrinsics.checkNotNullParameter(editRect, "editRect");
        if (f2 <= 0.0f || f3 <= 0.0f) {
            return null;
        }
        float fHeight = editRect.height();
        if (fHeight <= 0.0f) {
            return null;
        }
        float fCoerceIn = editRect.top + (fHeight * RangesKt.coerceIn(f, 0.0f, 1.0f));
        float f4 = f3 + fCoerceIn;
        float f5 = ((editRect.left + editRect.right) / 2.0f) - (f2 / 2.0f);
        return new Rect((int) f5, (int) fCoerceIn, (int) (f2 + f5), (int) f4);
    }

    public static final Rect computeTimeRectFromEditRectClipped(Context context, Rect editRect, float f, float f2, float f3) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(editRect, "editRect");
        Rect rectComputeClockRectFromEditRect = computeClockRectFromEditRect(editRect, f, f2, f3);
        if (rectComputeClockRectFromEditRect == null) {
            return null;
        }
        Rect rect = new Rect(rectComputeClockRectFromEditRect);
        int dimen = DeviceConfig.getDimen(R.dimen.classic_plus_text_area_height, context);
        int dimen2 = DeviceConfig.getDimen(R.dimen.miui_time_editor_date_extra_top, context);
        rect.top = rect.top + dimen + dimen2;
        rect.bottom = rect.bottom + dimen + dimen2;
        if (rect.intersect(editRect)) {
            return rect;
        }
        return null;
    }

    public static final Rect createEditRect(Context context, int i, int i2, Config config, Float f, Float f2) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(config, "config");
        float f3 = i;
        float editWidthRatio = config.getEditWidthRatio() * f3;
        float f4 = (f3 - editWidthRatio) / 2.0f;
        ClockDepthAvoidRuleUtils clockDepthAvoidRuleUtils = INSTANCE;
        float editTopPx = clockDepthAvoidRuleUtils.getEditTopPx(context, f);
        float bottomInsetPx = clockDepthAvoidRuleUtils.getBottomInsetPx(context, f2);
        int iCoerceIn = RangesKt.coerceIn((int) editTopPx, 0, i2);
        int iCoerceIn2 = RangesKt.coerceIn((int) (i2 - bottomInsetPx), 0, i2);
        int iCoerceIn3 = RangesKt.coerceIn((int) f4, 0, i);
        int iCoerceIn4 = RangesKt.coerceIn((int) (f4 + editWidthRatio), 0, i);
        if (iCoerceIn4 <= iCoerceIn3 || iCoerceIn2 <= iCoerceIn) {
            if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
                Log.w("ClockDepthAvoidRuleUtils", "createEditRect: invalid rect, screenW=" + i + ", screenH=" + i2 + ", editTopPx=" + editTopPx + ", bottomInsetPx=" + bottomInsetPx + ", returning empty");
            }
            return new Rect(0, 0, 0, 0);
        }
        Rect rect = new Rect(iCoerceIn3, iCoerceIn, iCoerceIn4, iCoerceIn2);
        if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
            Log.d("ClockDepthAvoidRuleUtils", "createEditRect: screenW=" + i + ", screenH=" + i2 + ", editTopPx=" + editTopPx + ", bottomInsetPx=" + bottomInsetPx + ", density=" + context.getResources().getDisplayMetrics().density + ", result=" + rect);
        }
        return rect;
    }

    public static /* synthetic */ Rect createEditRect$default(Context context, int i, int i2, Config config, Float f, Float f2, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            config = DEFAULT_CONFIG;
        }
        return createEditRect(context, i, i2, config, (i3 & 16) != 0 ? null : f, (i3 & 32) != 0 ? null : f2);
    }

    public static final int decideRegion(int i, float f, Config config) {
        int i2;
        Intrinsics.checkNotNullParameter(config, "config");
        RegionBounds regionBounds = getRegionBounds(i, config);
        if (f < regionBounds.getYellowTop()) {
            i2 = 0;
        } else {
            i2 = f < regionBounds.getYellowBottom() ? 1 : 2;
        }
        if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
            Log.d("ClockDepthAvoidRuleUtils", "decideRegion: screenH=" + i + ", clockCenterY=" + f + ", yellowTop=" + regionBounds.getYellowTop() + ", yellowBottom=" + regionBounds.getYellowBottom() + ", region=REGION_" + i2);
        }
        return i2;
    }

    public static final AllInOneClockStyle.Region decideRegion(Context context, int i, int i2, Rect clockRect, Config config) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(clockRect, "clockRect");
        Intrinsics.checkNotNullParameter(config, "config");
        return AllInOneClockStyle.toRegion(decideRegion(i2, INSTANCE.getClockCenterY(clockRect), config));
    }

    public static /* synthetic */ AllInOneClockStyle.Region decideRegion$default(Context context, int i, int i2, Rect rect, Config config, int i3, Object obj) {
        if ((i3 & 16) != 0) {
            config = DEFAULT_CONFIG;
        }
        return decideRegion(context, i, i2, rect, config);
    }

    public static final LayoutMode getLayoutMode(Rect clockRect, Config config) {
        Intrinsics.checkNotNullParameter(clockRect, "clockRect");
        Intrinsics.checkNotNullParameter(config, "config");
        return isDoubleLine(clockRect, config) ? LayoutMode.DOUBLE_LINE : LayoutMode.SINGLE_LINE;
    }

    public static final RegionBounds getRegionBounds(int i, Config config) {
        Intrinsics.checkNotNullParameter(config, "config");
        float f = i;
        float yellowTopOffsetRatioInScreenH = (f / 2.0f) - (config.getYellowTopOffsetRatioInScreenH() * f);
        float yellowTopOffsetRatioInScreenH2 = (config.getYellowTopOffsetRatioInScreenH() * f) / 2.0f;
        return new RegionBounds(yellowTopOffsetRatioInScreenH - yellowTopOffsetRatioInScreenH2, yellowTopOffsetRatioInScreenH + yellowTopOffsetRatioInScreenH2);
    }

    public static final SafeBounds getSafeBoundsForSmallPort(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        SafeBoundsConfig safeBoundsConfig = INSTANCE.getSafeBoundsConfig(context);
        if (safeBoundsConfig != null) {
            return safeBoundsConfig.getSmallPort();
        }
        return null;
    }

    public static final boolean isDoubleLine(Rect clockRect, Config config) {
        Intrinsics.checkNotNullParameter(clockRect, "clockRect");
        Intrinsics.checkNotNullParameter(config, "config");
        float fWidth = clockRect.width();
        float fHeight = clockRect.height();
        return fWidth > 0.0f && fHeight > 0.0f && fWidth / fHeight < config.getDoubleLineAspectRatioThresholdWOverH();
    }

    public final float getBottomInsetPx(Context context, Float f) {
        if (f != null) {
            float fFloatValue = f.floatValue();
            float fSimpleDpToPx = DeviceConfig.simpleDpToPx(fFloatValue, context);
            if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
                Log.d("ClockDepthAvoidRuleUtils", "getBottomInsetPx: overrideDp=" + fFloatValue + ", density=" + context.getResources().getDisplayMetrics().density + ", px=" + fSimpleDpToPx);
            }
            return fSimpleDpToPx;
        }
        SafeBounds currentSafeBounds = getCurrentSafeBounds(context);
        if (currentSafeBounds == null) {
            float dimen = DeviceConfig.getDimen(R.dimen.miui_time_editor_bottom_inset, context);
            if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
                Log.d("ClockDepthAvoidRuleUtils", "getBottomInsetPx: fallback dimen, px=" + dimen);
            }
            return dimen;
        }
        float fSimpleDpToPx2 = DeviceConfig.simpleDpToPx(currentSafeBounds.getBottom(), context);
        if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
            Log.d("ClockDepthAvoidRuleUtils", "getBottomInsetPx: settingsDp=" + currentSafeBounds.getBottom() + ", density=" + context.getResources().getDisplayMetrics().density + ", px=" + fSimpleDpToPx2);
        }
        return fSimpleDpToPx2;
    }

    public final float getClockCenterY(Rect rect) {
        return (rect.top + rect.bottom) / 2.0f;
    }

    public final SafeBounds getCurrentSafeBounds(Context context) {
        Pair pair;
        SafeBoundsConfig safeBoundsConfig = getSafeBoundsConfig(context);
        if (safeBoundsConfig == null) {
            return null;
        }
        boolean z = DeviceConfig.FOLD_DEVICE;
        boolean z2 = DeviceConfig.PAD_DEVICE;
        boolean z3 = false;
        boolean z4 = context.getResources().getConfiguration().orientation == 2;
        if (z && DeviceConfig.inFoldLargeScreen(context)) {
            z3 = true;
        }
        if (!z && !z2) {
            pair = TuplesKt.to(safeBoundsConfig.getSmallPort(), "smallPort(phone)");
        } else if (z2) {
            pair = z4 ? TuplesKt.to(safeBoundsConfig.getLargeLand(), "largeLand(pad)") : TuplesKt.to(safeBoundsConfig.getLargePort(), "largePort(pad)");
        } else if (!z) {
            pair = TuplesKt.to(safeBoundsConfig.getSmallPort(), "smallPort(default)");
        } else if (z3) {
            pair = z4 ? TuplesKt.to(safeBoundsConfig.getLargeLand(), "largeLand(fold-inner)") : TuplesKt.to(safeBoundsConfig.getLargePort(), "largePort(fold-inner)");
        } else {
            pair = TuplesKt.to(safeBoundsConfig.getSmallPort(), "smallPort(fold-outer)");
        }
        SafeBounds safeBounds = (SafeBounds) pair.component1();
        String str = (String) pair.component2();
        if (safeBounds == null) {
            Log.w("ClockDepthAvoidRuleUtils", "getCurrentSafeBounds: " + str + " is null in config, fallback to dimen");
            return safeBounds;
        }
        if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
            Log.d("ClockDepthAvoidRuleUtils", "getCurrentSafeBounds: isFold=" + z + ", isPad=" + z2 + ", isFoldLarge=" + z3 + ", isLand=" + z4 + ", matchedKey=" + str + ", top=" + safeBounds.getTop() + "dp, bottom=" + safeBounds.getBottom() + "dp");
        }
        return safeBounds;
    }

    public final float getEditTopPx(Context context, Float f) {
        if (f != null) {
            float fFloatValue = f.floatValue();
            float fSimpleDpToPx = DeviceConfig.simpleDpToPx(fFloatValue, context);
            if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
                Log.d("ClockDepthAvoidRuleUtils", "getEditTopPx: overrideDp=" + fFloatValue + ", density=" + context.getResources().getDisplayMetrics().density + ", px=" + fSimpleDpToPx);
            }
            return fSimpleDpToPx;
        }
        SafeBounds currentSafeBounds = getCurrentSafeBounds(context);
        if (currentSafeBounds != null) {
            float fSimpleDpToPx2 = DeviceConfig.simpleDpToPx(currentSafeBounds.getTop(), context);
            if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
                Log.d("ClockDepthAvoidRuleUtils", "getEditTopPx: settingsDp=" + currentSafeBounds.getTop() + ", density=" + context.getResources().getDisplayMetrics().density + ", px=" + fSimpleDpToPx2);
            }
            return fSimpleDpToPx2;
        }
        int dimen = DeviceConfig.getDimen(R.dimen.miui_time_editor_face_top, context);
        int dimen2 = DeviceConfig.getDimen(R.dimen.miui_time_editor_face_height, context);
        int dimen3 = DeviceConfig.getDimen(R.dimen.miui_time_editor_icon_to_edit_top, context);
        float f2 = dimen + dimen2 + dimen3;
        if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
            Log.d("ClockDepthAvoidRuleUtils", "getEditTopPx: fallback dimen, faceTop=" + dimen + ", faceH=" + dimen2 + ", iconToEditTop=" + dimen3 + ", px=" + f2);
        }
        return f2;
    }

    public final SafeBoundsConfig getSafeBoundsConfig(Context context) {
        SafeBoundsConfig safeBoundsConfig = sCachedConfig;
        if (safeBoundsConfig != null) {
            if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
                Log.d("ClockDepthAvoidRuleUtils", "getSafeBoundsConfig: hit cache, config=" + safeBoundsConfig);
            }
            return safeBoundsConfig;
        }
        try {
            String string = Settings.Global.getString(context.getContentResolver(), "keyguard_safe_bounds");
            if (string == null) {
                Log.w("ClockDepthAvoidRuleUtils", "getSafeBoundsConfig: Settings.keyguard_safe_bounds is null, fallback to dimen");
                return null;
            }
            boolean z = DepthAvoidEvaluatorDebug.DEBUG_LOG;
            if (z) {
                Log.d("ClockDepthAvoidRuleUtils", "getSafeBoundsConfig: raw json=" + string);
            }
            SafeBoundsConfig safeBoundsConfig2 = (SafeBoundsConfig) new Gson().fromJson(string, SafeBoundsConfig.class);
            sCachedConfig = safeBoundsConfig2;
            if (z) {
                Log.d("ClockDepthAvoidRuleUtils", "getSafeBoundsConfig: parsed config=version=" + safeBoundsConfig2.getVersion() + ", smallPort=" + safeBoundsConfig2.getSmallPort() + ", largePort=" + safeBoundsConfig2.getLargePort() + ", largeLand=" + safeBoundsConfig2.getLargeLand());
            }
            return safeBoundsConfig2;
        } catch (Exception e) {
            Log.e("ClockDepthAvoidRuleUtils", "getSafeBoundsConfig: parse failed, fallback to dimen", e);
            return null;
        }
    }
}
