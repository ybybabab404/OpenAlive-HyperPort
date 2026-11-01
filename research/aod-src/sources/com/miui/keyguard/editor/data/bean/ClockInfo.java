package com.miui.keyguard.editor.data.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
    */
/* JADX INFO: compiled from: TemplateConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClockInfo {
    private int blendColor;
    private int classicLine1;
    private int classicLine2;
    private int classicLine3;
    private int classicLine4;
    private int classicLine5;

    @Nullable
    private String classicSignature;
    private float clockBottomRatio;
    private int clockDepthType;
    private int clockEffect;
    private float clockTopRatio;
    private int clockWeight;
    private boolean enableDiffusion;
    private int extraFlag;
    private int fontStyle;
    private float glassTransparency;
    private int hollowStyle;
    private boolean isAutoPrimaryColor;
    private boolean isAutoSecondaryColor;
    private boolean isColonShow;
    private boolean isDarkClockArea;
    private boolean isDiffHourMinuteColor;
    private boolean isDoubleRow;
    private boolean isSkipScreenTransform;
    private int primaryColor;

    @Nullable
    private ColorValueInfo primaryColorValueInfo;
    private int primaryFontColorPanelId;
    private int secondaryBlendColor;
    private int secondaryColor;

    @Nullable
    private ColorValueInfo secondaryColorValueInfo;
    private int secondaryFontColorPanelId;

    @Nullable
    private String signatureLine1;

    @Nullable
    private String signatureLine2;

    @Nullable
    private String signatureLine3;
    private int style;

    @NotNull
    private String templateId;
    private float timeHeight;
    private float timeWidth;
    private boolean userModifiedPrimaryColor;
    private boolean userModifiedSecondaryColor;

    public ClockInfo(@NotNull String templateId, int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7, int i8, int i9, int i10, int i11, int i12, int i13, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, boolean z2, boolean z3, boolean z4, @Nullable ColorValueInfo colorValueInfo, @Nullable ColorValueInfo colorValueInfo2, boolean z5, int i14, int i15, float f, float f2, float f3, float f4, int i16, float f5, int i17, int i18, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10) {
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        this.templateId = templateId;
        this.primaryColor = i;
        this.secondaryColor = i2;
        this.blendColor = i3;
        this.secondaryBlendColor = i4;
        this.clockEffect = i5;
        this.style = i6;
        this.enableDiffusion = z;
        this.classicLine1 = i7;
        this.classicLine2 = i8;
        this.classicLine3 = i9;
        this.classicLine4 = i10;
        this.classicLine5 = i11;
        this.primaryFontColorPanelId = i12;
        this.secondaryFontColorPanelId = i13;
        this.classicSignature = str;
        this.signatureLine1 = str2;
        this.signatureLine2 = str3;
        this.signatureLine3 = str4;
        this.isAutoPrimaryColor = z2;
        this.isAutoSecondaryColor = z3;
        this.isDiffHourMinuteColor = z4;
        this.primaryColorValueInfo = colorValueInfo;
        this.secondaryColorValueInfo = colorValueInfo2;
        this.isDarkClockArea = z5;
        this.clockWeight = i14;
        this.extraFlag = i15;
        this.clockTopRatio = f;
        this.clockBottomRatio = f2;
        this.timeWidth = f3;
        this.timeHeight = f4;
        this.clockDepthType = i16;
        this.glassTransparency = f5;
        this.fontStyle = i17;
        this.hollowStyle = i18;
        this.isDoubleRow = z6;
        this.isColonShow = z7;
        this.isSkipScreenTransform = z8;
        this.userModifiedPrimaryColor = z9;
        this.userModifiedSecondaryColor = z10;
    }

    public /* synthetic */ ClockInfo(String str, int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7, int i8, int i9, int i10, int i11, int i12, int i13, String str2, String str3, String str4, String str5, boolean z2, boolean z3, boolean z4, ColorValueInfo colorValueInfo, ColorValueInfo colorValueInfo2, boolean z5, int i14, int i15, float f, float f2, float f3, float f4, int i16, float f5, int i17, int i18, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, int i19, int i20, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, i2, i3, i4, i5, i6, z, i7, i8, i9, i10, i11, i12, i13, (i19 & 32768) != 0 ? "" : str2, (i19 & 65536) != 0 ? "" : str3, (i19 & 131072) != 0 ? "" : str4, (i19 & 262144) != 0 ? "" : str5, z2, z3, z4, (i19 & 4194304) != 0 ? null : colorValueInfo, (i19 & 8388608) != 0 ? null : colorValueInfo2, z5, i14, i15, f, f2, f3, f4, i16, f5, i17, i18, z6, (i20 & 16) != 0 ? false : z7, (i20 & 32) != 0 ? false : z8, (i20 & 64) != 0 ? false : z9, (i20 & 128) != 0 ? false : z10);
    }

    @NotNull
    public final String component1() {
        return this.templateId;
    }

    public final int component10() {
        return this.classicLine2;
    }

    public final int component11() {
        return this.classicLine3;
    }

    public final int component12() {
        return this.classicLine4;
    }

    public final int component13() {
        return this.classicLine5;
    }

    public final int component14() {
        return this.primaryFontColorPanelId;
    }

    public final int component15() {
        return this.secondaryFontColorPanelId;
    }

    @Nullable
    public final String component16() {
        return this.classicSignature;
    }

    @Nullable
    public final String component17() {
        return this.signatureLine1;
    }

    @Nullable
    public final String component18() {
        return this.signatureLine2;
    }

    @Nullable
    public final String component19() {
        return this.signatureLine3;
    }

    public final int component2() {
        return this.primaryColor;
    }

    public final boolean component20() {
        return this.isAutoPrimaryColor;
    }

    public final boolean component21() {
        return this.isAutoSecondaryColor;
    }

    public final boolean component22() {
        return this.isDiffHourMinuteColor;
    }

    @Nullable
    public final ColorValueInfo component23() {
        return this.primaryColorValueInfo;
    }

    @Nullable
    public final ColorValueInfo component24() {
        return this.secondaryColorValueInfo;
    }

    public final boolean component25() {
        return this.isDarkClockArea;
    }

    public final int component26() {
        return this.clockWeight;
    }

    public final int component27() {
        return this.extraFlag;
    }

    public final float component28() {
        return this.clockTopRatio;
    }

    public final float component29() {
        return this.clockBottomRatio;
    }

    public final int component3() {
        return this.secondaryColor;
    }

    public final float component30() {
        return this.timeWidth;
    }

    public final float component31() {
        return this.timeHeight;
    }

    public final int component32() {
        return this.clockDepthType;
    }

    public final float component33() {
        return this.glassTransparency;
    }

    public final int component34() {
        return this.fontStyle;
    }

    public final int component35() {
        return this.hollowStyle;
    }

    public final boolean component36() {
        return this.isDoubleRow;
    }

    public final boolean component37() {
        return this.isColonShow;
    }

    public final boolean component38() {
        return this.isSkipScreenTransform;
    }

    public final boolean component39() {
        return this.userModifiedPrimaryColor;
    }

    public final int component4() {
        return this.blendColor;
    }

    public final boolean component40() {
        return this.userModifiedSecondaryColor;
    }

    public final int component5() {
        return this.secondaryBlendColor;
    }

    public final int component6() {
        return this.clockEffect;
    }

    public final int component7() {
        return this.style;
    }

    public final boolean component8() {
        return this.enableDiffusion;
    }

    public final int component9() {
        return this.classicLine1;
    }

    @NotNull
    public final ClockInfo copy(@NotNull String templateId, int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7, int i8, int i9, int i10, int i11, int i12, int i13, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, boolean z2, boolean z3, boolean z4, @Nullable ColorValueInfo colorValueInfo, @Nullable ColorValueInfo colorValueInfo2, boolean z5, int i14, int i15, float f, float f2, float f3, float f4, int i16, float f5, int i17, int i18, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10) {
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        return new ClockInfo(templateId, i, i2, i3, i4, i5, i6, z, i7, i8, i9, i10, i11, i12, i13, str, str2, str3, str4, z2, z3, z4, colorValueInfo, colorValueInfo2, z5, i14, i15, f, f2, f3, f4, i16, f5, i17, i18, z6, z7, z8, z9, z10);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockInfo)) {
            return false;
        }
        ClockInfo clockInfo = (ClockInfo) obj;
        return Intrinsics.areEqual(this.templateId, clockInfo.templateId) && this.primaryColor == clockInfo.primaryColor && this.secondaryColor == clockInfo.secondaryColor && this.blendColor == clockInfo.blendColor && this.secondaryBlendColor == clockInfo.secondaryBlendColor && this.clockEffect == clockInfo.clockEffect && this.style == clockInfo.style && this.enableDiffusion == clockInfo.enableDiffusion && this.classicLine1 == clockInfo.classicLine1 && this.classicLine2 == clockInfo.classicLine2 && this.classicLine3 == clockInfo.classicLine3 && this.classicLine4 == clockInfo.classicLine4 && this.classicLine5 == clockInfo.classicLine5 && this.primaryFontColorPanelId == clockInfo.primaryFontColorPanelId && this.secondaryFontColorPanelId == clockInfo.secondaryFontColorPanelId && Intrinsics.areEqual(this.classicSignature, clockInfo.classicSignature) && Intrinsics.areEqual(this.signatureLine1, clockInfo.signatureLine1) && Intrinsics.areEqual(this.signatureLine2, clockInfo.signatureLine2) && Intrinsics.areEqual(this.signatureLine3, clockInfo.signatureLine3) && this.isAutoPrimaryColor == clockInfo.isAutoPrimaryColor && this.isAutoSecondaryColor == clockInfo.isAutoSecondaryColor && this.isDiffHourMinuteColor == clockInfo.isDiffHourMinuteColor && Intrinsics.areEqual(this.primaryColorValueInfo, clockInfo.primaryColorValueInfo) && Intrinsics.areEqual(this.secondaryColorValueInfo, clockInfo.secondaryColorValueInfo) && this.isDarkClockArea == clockInfo.isDarkClockArea && this.clockWeight == clockInfo.clockWeight && this.extraFlag == clockInfo.extraFlag && Float.compare(this.clockTopRatio, clockInfo.clockTopRatio) == 0 && Float.compare(this.clockBottomRatio, clockInfo.clockBottomRatio) == 0 && Float.compare(this.timeWidth, clockInfo.timeWidth) == 0 && Float.compare(this.timeHeight, clockInfo.timeHeight) == 0 && this.clockDepthType == clockInfo.clockDepthType && Float.compare(this.glassTransparency, clockInfo.glassTransparency) == 0 && this.fontStyle == clockInfo.fontStyle && this.hollowStyle == clockInfo.hollowStyle && this.isDoubleRow == clockInfo.isDoubleRow && this.isColonShow == clockInfo.isColonShow && this.isSkipScreenTransform == clockInfo.isSkipScreenTransform && this.userModifiedPrimaryColor == clockInfo.userModifiedPrimaryColor && this.userModifiedSecondaryColor == clockInfo.userModifiedSecondaryColor;
    }

    public final int getBlendColor() {
        return this.blendColor;
    }

    public final int getClassicLine1() {
        return this.classicLine1;
    }

    public final int getClassicLine2() {
        return this.classicLine2;
    }

    public final int getClassicLine3() {
        return this.classicLine3;
    }

    public final int getClassicLine4() {
        return this.classicLine4;
    }

    public final int getClassicLine5() {
        return this.classicLine5;
    }

    @Nullable
    public final String getClassicSignature() {
        return this.classicSignature;
    }

    public final float getClockBottomRatio() {
        return this.clockBottomRatio;
    }

    public final int getClockDepthType() {
        return this.clockDepthType;
    }

    public final int getClockEffect() {
        return this.clockEffect;
    }

    public final float getClockTopRatio() {
        return this.clockTopRatio;
    }

    public final int getClockWeight() {
        return this.clockWeight;
    }

    public final boolean getEnableDiffusion() {
        return this.enableDiffusion;
    }

    public final int getExtraFlag() {
        return this.extraFlag;
    }

    public final int getFontStyle() {
        return this.fontStyle;
    }

    public final float getGlassTransparency() {
        return this.glassTransparency;
    }

    public final int getHollowStyle() {
        return this.hollowStyle;
    }

    public final int getPrimaryColor() {
        return this.primaryColor;
    }

    @Nullable
    public final ColorValueInfo getPrimaryColorValueInfo() {
        return this.primaryColorValueInfo;
    }

    public final int getPrimaryFontColorPanelId() {
        return this.primaryFontColorPanelId;
    }

    public final int getSecondaryBlendColor() {
        return this.secondaryBlendColor;
    }

    public final int getSecondaryColor() {
        return this.secondaryColor;
    }

    @Nullable
    public final ColorValueInfo getSecondaryColorValueInfo() {
        return this.secondaryColorValueInfo;
    }

    public final int getSecondaryFontColorPanelId() {
        return this.secondaryFontColorPanelId;
    }

    @Nullable
    public final String getSignatureLine1() {
        return this.signatureLine1;
    }

    @Nullable
    public final String getSignatureLine2() {
        return this.signatureLine2;
    }

    @Nullable
    public final String getSignatureLine3() {
        return this.signatureLine3;
    }

    public final int getStyle() {
        return this.style;
    }

    @NotNull
    public final String getTemplateId() {
        return this.templateId;
    }

    public final float getTimeHeight() {
        return this.timeHeight;
    }

    public final float getTimeWidth() {
        return this.timeWidth;
    }

    public final boolean getUserModifiedPrimaryColor() {
        return this.userModifiedPrimaryColor;
    }

    public final boolean getUserModifiedSecondaryColor() {
        return this.userModifiedSecondaryColor;
    }

    public int hashCode() {
        int iHashCode = ((((((((((((((((((((((((((((this.templateId.hashCode() * 31) + Integer.hashCode(this.primaryColor)) * 31) + Integer.hashCode(this.secondaryColor)) * 31) + Integer.hashCode(this.blendColor)) * 31) + Integer.hashCode(this.secondaryBlendColor)) * 31) + Integer.hashCode(this.clockEffect)) * 31) + Integer.hashCode(this.style)) * 31) + Boolean.hashCode(this.enableDiffusion)) * 31) + Integer.hashCode(this.classicLine1)) * 31) + Integer.hashCode(this.classicLine2)) * 31) + Integer.hashCode(this.classicLine3)) * 31) + Integer.hashCode(this.classicLine4)) * 31) + Integer.hashCode(this.classicLine5)) * 31) + Integer.hashCode(this.primaryFontColorPanelId)) * 31) + Integer.hashCode(this.secondaryFontColorPanelId)) * 31;
        String str = this.classicSignature;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.signatureLine1;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.signatureLine2;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.signatureLine3;
        int iHashCode5 = (((((((iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31) + Boolean.hashCode(this.isAutoPrimaryColor)) * 31) + Boolean.hashCode(this.isAutoSecondaryColor)) * 31) + Boolean.hashCode(this.isDiffHourMinuteColor)) * 31;
        ColorValueInfo colorValueInfo = this.primaryColorValueInfo;
        int iHashCode6 = (iHashCode5 + (colorValueInfo == null ? 0 : colorValueInfo.hashCode())) * 31;
        ColorValueInfo colorValueInfo2 = this.secondaryColorValueInfo;
        return ((((((((((((((((((((((((((((((((iHashCode6 + (colorValueInfo2 != null ? colorValueInfo2.hashCode() : 0)) * 31) + Boolean.hashCode(this.isDarkClockArea)) * 31) + Integer.hashCode(this.clockWeight)) * 31) + Integer.hashCode(this.extraFlag)) * 31) + Float.hashCode(this.clockTopRatio)) * 31) + Float.hashCode(this.clockBottomRatio)) * 31) + Float.hashCode(this.timeWidth)) * 31) + Float.hashCode(this.timeHeight)) * 31) + Integer.hashCode(this.clockDepthType)) * 31) + Float.hashCode(this.glassTransparency)) * 31) + Integer.hashCode(this.fontStyle)) * 31) + Integer.hashCode(this.hollowStyle)) * 31) + Boolean.hashCode(this.isDoubleRow)) * 31) + Boolean.hashCode(this.isColonShow)) * 31) + Boolean.hashCode(this.isSkipScreenTransform)) * 31) + Boolean.hashCode(this.userModifiedPrimaryColor)) * 31) + Boolean.hashCode(this.userModifiedSecondaryColor);
    }

    public final boolean isAutoPrimaryColor() {
        return this.isAutoPrimaryColor;
    }

    public final boolean isAutoSecondaryColor() {
        return this.isAutoSecondaryColor;
    }

    public final boolean isColonShow() {
        return this.isColonShow;
    }

    public final boolean isDarkClockArea() {
        return this.isDarkClockArea;
    }

    public final boolean isDiffHourMinuteColor() {
        return this.isDiffHourMinuteColor;
    }

    public final boolean isDoubleRow() {
        return this.isDoubleRow;
    }

    public final boolean isSkipScreenTransform() {
        return this.isSkipScreenTransform;
    }

    public final void setAutoPrimaryColor(boolean z) {
        this.isAutoPrimaryColor = z;
    }

    public final void setAutoSecondaryColor(boolean z) {
        this.isAutoSecondaryColor = z;
    }

    public final void setBlendColor(int i) {
        this.blendColor = i;
    }

    public final void setClassicLine1(int i) {
        this.classicLine1 = i;
    }

    public final void setClassicLine2(int i) {
        this.classicLine2 = i;
    }

    public final void setClassicLine3(int i) {
        this.classicLine3 = i;
    }

    public final void setClassicLine4(int i) {
        this.classicLine4 = i;
    }

    public final void setClassicLine5(int i) {
        this.classicLine5 = i;
    }

    public final void setClassicSignature(@Nullable String str) {
        this.classicSignature = str;
    }

    public final void setClockBottomRatio(float f) {
        this.clockBottomRatio = f;
    }

    public final void setClockDepthType(int i) {
        this.clockDepthType = i;
    }

    public final void setClockEffect(int i) {
        this.clockEffect = i;
    }

    public final void setClockTopRatio(float f) {
        this.clockTopRatio = f;
    }

    public final void setClockWeight(int i) {
        this.clockWeight = i;
    }

    public final void setColonShow(boolean z) {
        this.isColonShow = z;
    }

    public final void setDarkClockArea(boolean z) {
        this.isDarkClockArea = z;
    }

    public final void setDiffHourMinuteColor(boolean z) {
        this.isDiffHourMinuteColor = z;
    }

    public final void setDoubleRow(boolean z) {
        this.isDoubleRow = z;
    }

    public final void setEnableDiffusion(boolean z) {
        this.enableDiffusion = z;
    }

    public final void setExtraFlag(int i) {
        this.extraFlag = i;
    }

    public final void setFontStyle(int i) {
        this.fontStyle = i;
    }

    public final void setGlassTransparency(float f) {
        this.glassTransparency = f;
    }

    public final void setHollowStyle(int i) {
        this.hollowStyle = i;
    }

    public final void setPrimaryColor(int i) {
        this.primaryColor = i;
    }

    public final void setPrimaryColorValueInfo(@Nullable ColorValueInfo colorValueInfo) {
        this.primaryColorValueInfo = colorValueInfo;
    }

    public final void setPrimaryFontColorPanelId(int i) {
        this.primaryFontColorPanelId = i;
    }

    public final void setSecondaryBlendColor(int i) {
        this.secondaryBlendColor = i;
    }

    public final void setSecondaryColor(int i) {
        this.secondaryColor = i;
    }

    public final void setSecondaryColorValueInfo(@Nullable ColorValueInfo colorValueInfo) {
        this.secondaryColorValueInfo = colorValueInfo;
    }

    public final void setSecondaryFontColorPanelId(int i) {
        this.secondaryFontColorPanelId = i;
    }

    public final void setSignatureLine1(@Nullable String str) {
        this.signatureLine1 = str;
    }

    public final void setSignatureLine2(@Nullable String str) {
        this.signatureLine2 = str;
    }

    public final void setSignatureLine3(@Nullable String str) {
        this.signatureLine3 = str;
    }

    public final void setSkipScreenTransform(boolean z) {
        this.isSkipScreenTransform = z;
    }

    public final void setStyle(int i) {
        this.style = i;
    }

    public final void setTemplateId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.templateId = str;
    }

    public final void setTimeHeight(float f) {
        this.timeHeight = f;
    }

    public final void setTimeWidth(float f) {
        this.timeWidth = f;
    }

    public final void setUserModifiedPrimaryColor(boolean z) {
        this.userModifiedPrimaryColor = z;
    }

    public final void setUserModifiedSecondaryColor(boolean z) {
        this.userModifiedSecondaryColor = z;
    }

    @NotNull
    public String toString() {
        return "ClockInfo(templateId=" + this.templateId + ", primaryColor=" + this.primaryColor + ", secondaryColor=" + this.secondaryColor + ", blendColor=" + this.blendColor + ", secondaryBlendColor=" + this.secondaryBlendColor + ", clockEffect=" + this.clockEffect + ", style=" + this.style + ", enableDiffusion=" + this.enableDiffusion + ", classicLine1=" + this.classicLine1 + ", classicLine2=" + this.classicLine2 + ", classicLine3=" + this.classicLine3 + ", classicLine4=" + this.classicLine4 + ", classicLine5=" + this.classicLine5 + ", primaryFontColorPanelId=" + this.primaryFontColorPanelId + ", secondaryFontColorPanelId=" + this.secondaryFontColorPanelId + ", classicSignature=" + this.classicSignature + ", signatureLine1=" + this.signatureLine1 + ", signatureLine2=" + this.signatureLine2 + ", signatureLine3=" + this.signatureLine3 + ", isAutoPrimaryColor=" + this.isAutoPrimaryColor + ", isAutoSecondaryColor=" + this.isAutoSecondaryColor + ", isDiffHourMinuteColor=" + this.isDiffHourMinuteColor + ", primaryColorValueInfo=" + this.primaryColorValueInfo + ", secondaryColorValueInfo=" + this.secondaryColorValueInfo + ", isDarkClockArea=" + this.isDarkClockArea + ", clockWeight=" + this.clockWeight + ", extraFlag=" + this.extraFlag + ", clockTopRatio=" + this.clockTopRatio + ", clockBottomRatio=" + this.clockBottomRatio + ", timeWidth=" + this.timeWidth + ", timeHeight=" + this.timeHeight + ", clockDepthType=" + this.clockDepthType + ", glassTransparency=" + this.glassTransparency + ", fontStyle=" + this.fontStyle + ", hollowStyle=" + this.hollowStyle + ", isDoubleRow=" + this.isDoubleRow + ", isColonShow=" + this.isColonShow + ", isSkipScreenTransform=" + this.isSkipScreenTransform + ", userModifiedPrimaryColor=" + this.userModifiedPrimaryColor + ", userModifiedSecondaryColor=" + this.userModifiedSecondaryColor + ")";
    }
}
