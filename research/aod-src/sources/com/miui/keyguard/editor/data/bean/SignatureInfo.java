package com.miui.keyguard.editor.data.bean;

import com.miui.keyguard.editor.view.AutoBottomSheetKt;
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
public final class SignatureInfo {
    private int alignment;
    private int blendColor;
    private int clockEffect;

    @NotNull
    private String content;
    private boolean isAutoPrimaryColor;
    private float letterSpaceValue;
    private float lineSpaceValue;
    private int primaryColor;
    private int primaryFontColorPanelId;
    private int templateFontType;

    @NotNull
    private String templateId;
    private float textSize;

    public SignatureInfo(@NotNull String templateId, @NotNull String content, int i, int i2, int i3, int i4, int i5, boolean z, int i6, float f, float f2, float f3) {
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        Intrinsics.checkNotNullParameter(content, "content");
        this.templateId = templateId;
        this.content = content;
        this.primaryColor = i;
        this.blendColor = i2;
        this.clockEffect = i3;
        this.alignment = i4;
        this.primaryFontColorPanelId = i5;
        this.isAutoPrimaryColor = z;
        this.templateFontType = i6;
        this.textSize = f;
        this.letterSpaceValue = f2;
        this.lineSpaceValue = f3;
    }

    public /* synthetic */ SignatureInfo(String str, String str2, int i, int i2, int i3, int i4, int i5, boolean z, int i6, float f, float f2, float f3, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i7 & 2) != 0 ? "" : str2, i, i2, i3, i4, i5, (i7 & 128) != 0 ? false : z, i6, f, f2, f3);
    }

    public static /* synthetic */ SignatureInfo copy$default(SignatureInfo signatureInfo, String str, String str2, int i, int i2, int i3, int i4, int i5, boolean z, int i6, float f, float f2, float f3, int i7, Object obj) {
        if ((i7 & 1) != 0) {
            str = signatureInfo.templateId;
        }
        if ((i7 & 2) != 0) {
            str2 = signatureInfo.content;
        }
        if ((i7 & 4) != 0) {
            i = signatureInfo.primaryColor;
        }
        if ((i7 & 8) != 0) {
            i2 = signatureInfo.blendColor;
        }
        if ((i7 & 16) != 0) {
            i3 = signatureInfo.clockEffect;
        }
        if ((i7 & 32) != 0) {
            i4 = signatureInfo.alignment;
        }
        if ((i7 & 64) != 0) {
            i5 = signatureInfo.primaryFontColorPanelId;
        }
        if ((i7 & 128) != 0) {
            z = signatureInfo.isAutoPrimaryColor;
        }
        if ((i7 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0) {
            i6 = signatureInfo.templateFontType;
        }
        if ((i7 & 512) != 0) {
            f = signatureInfo.textSize;
        }
        if ((i7 & 1024) != 0) {
            f2 = signatureInfo.letterSpaceValue;
        }
        if ((i7 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0) {
            f3 = signatureInfo.lineSpaceValue;
        }
        float f4 = f2;
        float f5 = f3;
        int i8 = i6;
        float f6 = f;
        int i9 = i5;
        boolean z2 = z;
        int i10 = i3;
        int i11 = i4;
        return signatureInfo.copy(str, str2, i, i2, i10, i11, i9, z2, i8, f6, f4, f5);
    }

    @NotNull
    public final String component1() {
        return this.templateId;
    }

    public final float component10() {
        return this.textSize;
    }

    public final float component11() {
        return this.letterSpaceValue;
    }

    public final float component12() {
        return this.lineSpaceValue;
    }

    @NotNull
    public final String component2() {
        return this.content;
    }

    public final int component3() {
        return this.primaryColor;
    }

    public final int component4() {
        return this.blendColor;
    }

    public final int component5() {
        return this.clockEffect;
    }

    public final int component6() {
        return this.alignment;
    }

    public final int component7() {
        return this.primaryFontColorPanelId;
    }

    public final boolean component8() {
        return this.isAutoPrimaryColor;
    }

    public final int component9() {
        return this.templateFontType;
    }

    @NotNull
    public final SignatureInfo copy(@NotNull String templateId, @NotNull String content, int i, int i2, int i3, int i4, int i5, boolean z, int i6, float f, float f2, float f3) {
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        Intrinsics.checkNotNullParameter(content, "content");
        return new SignatureInfo(templateId, content, i, i2, i3, i4, i5, z, i6, f, f2, f3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SignatureInfo)) {
            return false;
        }
        SignatureInfo signatureInfo = (SignatureInfo) obj;
        return Intrinsics.areEqual(this.templateId, signatureInfo.templateId) && Intrinsics.areEqual(this.content, signatureInfo.content) && this.primaryColor == signatureInfo.primaryColor && this.blendColor == signatureInfo.blendColor && this.clockEffect == signatureInfo.clockEffect && this.alignment == signatureInfo.alignment && this.primaryFontColorPanelId == signatureInfo.primaryFontColorPanelId && this.isAutoPrimaryColor == signatureInfo.isAutoPrimaryColor && this.templateFontType == signatureInfo.templateFontType && Float.compare(this.textSize, signatureInfo.textSize) == 0 && Float.compare(this.letterSpaceValue, signatureInfo.letterSpaceValue) == 0 && Float.compare(this.lineSpaceValue, signatureInfo.lineSpaceValue) == 0;
    }

    public final int getAlignment() {
        return this.alignment;
    }

    public final int getBlendColor() {
        return this.blendColor;
    }

    public final int getClockEffect() {
        return this.clockEffect;
    }

    @NotNull
    public final String getContent() {
        return this.content;
    }

    public final float getLetterSpaceValue() {
        return this.letterSpaceValue;
    }

    public final float getLineSpaceValue() {
        return this.lineSpaceValue;
    }

    public final int getPrimaryColor() {
        return this.primaryColor;
    }

    public final int getPrimaryFontColorPanelId() {
        return this.primaryFontColorPanelId;
    }

    public final int getTemplateFontType() {
        return this.templateFontType;
    }

    @NotNull
    public final String getTemplateId() {
        return this.templateId;
    }

    public final float getTextSize() {
        return this.textSize;
    }

    public int hashCode() {
        return (((((((((((((((((((((this.templateId.hashCode() * 31) + this.content.hashCode()) * 31) + Integer.hashCode(this.primaryColor)) * 31) + Integer.hashCode(this.blendColor)) * 31) + Integer.hashCode(this.clockEffect)) * 31) + Integer.hashCode(this.alignment)) * 31) + Integer.hashCode(this.primaryFontColorPanelId)) * 31) + Boolean.hashCode(this.isAutoPrimaryColor)) * 31) + Integer.hashCode(this.templateFontType)) * 31) + Float.hashCode(this.textSize)) * 31) + Float.hashCode(this.letterSpaceValue)) * 31) + Float.hashCode(this.lineSpaceValue);
    }

    public final boolean isAutoPrimaryColor() {
        return this.isAutoPrimaryColor;
    }

    public final void setAlignment(int i) {
        this.alignment = i;
    }

    public final void setAutoPrimaryColor(boolean z) {
        this.isAutoPrimaryColor = z;
    }

    public final void setBlendColor(int i) {
        this.blendColor = i;
    }

    public final void setClockEffect(int i) {
        this.clockEffect = i;
    }

    public final void setContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.content = str;
    }

    public final void setLetterSpaceValue(float f) {
        this.letterSpaceValue = f;
    }

    public final void setLineSpaceValue(float f) {
        this.lineSpaceValue = f;
    }

    public final void setPrimaryColor(int i) {
        this.primaryColor = i;
    }

    public final void setPrimaryFontColorPanelId(int i) {
        this.primaryFontColorPanelId = i;
    }

    public final void setTemplateFontType(int i) {
        this.templateFontType = i;
    }

    public final void setTemplateId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.templateId = str;
    }

    public final void setTextSize(float f) {
        this.textSize = f;
    }

    @NotNull
    public String toString() {
        return "SignatureInfo(templateId=" + this.templateId + ", content=" + this.content + ", primaryColor=" + this.primaryColor + ", blendColor=" + this.blendColor + ", clockEffect=" + this.clockEffect + ", alignment=" + this.alignment + ", primaryFontColorPanelId=" + this.primaryFontColorPanelId + ", isAutoPrimaryColor=" + this.isAutoPrimaryColor + ", templateFontType=" + this.templateFontType + ", textSize=" + this.textSize + ", letterSpaceValue=" + this.letterSpaceValue + ", lineSpaceValue=" + this.lineSpaceValue + ")";
    }
}
