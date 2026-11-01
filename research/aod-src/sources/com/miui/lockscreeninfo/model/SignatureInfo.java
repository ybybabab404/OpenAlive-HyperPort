package com.miui.lockscreeninfo.model;

/* JADX INFO: loaded from: classes2.dex */
public class SignatureInfo {
    public int aodBlendColor;
    public int aodPrimaryColor;
    public int blendColor;
    public int clockEffect;
    public int displayType;
    public String fontFamily;
    public int fullAodBlendColor;
    public int fullAodPrimaryColor;
    public int height;
    public float letterSpaceUnit;
    public float letterSpaceValue;
    public float lineSpaceUnit;
    public float lineSpaceValue;
    public int lines;
    public boolean mMultiWindowBlur;
    public int templateFontType;
    public String templateId;
    public boolean textDark;
    public int textSizeState;
    public float textsize;
    public boolean verticalMode;
    public int width;
    public String content = "";
    public int primaryColor = -1;
    public int alignment = 103;
    public boolean isAutoPrimaryColor = false;

    public SignatureInfo(String str) {
        boolean z = this.verticalMode;
        this.letterSpaceValue = z ? 0.8f : 0.0f;
        this.lineSpaceValue = z ? 28.0f : -21.0f;
        this.lines = Integer.MAX_VALUE;
        this.blendColor = -1;
        this.aodBlendColor = -1;
        this.textDark = false;
        this.templateId = str;
        initData();
    }

    public static boolean isFullAODType(int i) {
        return (i & 32) == 32;
    }

    public int getAlignment() {
        return this.alignment;
    }

    public int getAodBlendColor() {
        return this.aodBlendColor;
    }

    public int getBlendColor() {
        return this.blendColor;
    }

    public int getClockEffect() {
        return this.clockEffect;
    }

    public String getContent() {
        return this.content;
    }

    public int getDisplayType() {
        return this.displayType;
    }

    public int getFullAodBlendColor() {
        return this.fullAodBlendColor;
    }

    public int getFullAodPrimaryColor() {
        return this.fullAodPrimaryColor;
    }

    public float getLetterSpaceUnit() {
        return this.letterSpaceUnit;
    }

    public float getLetterSpaceValue() {
        return this.letterSpaceValue;
    }

    public float getLineSpaceUnit() {
        return this.lineSpaceUnit;
    }

    public float getLineSpaceValue() {
        return this.lineSpaceValue;
    }

    public int getLines() {
        return this.lines;
    }

    public int getPrimaryColor() {
        return this.primaryColor;
    }

    public int getTemplateFontType() {
        return this.templateFontType;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public int getTextSizeState() {
        return this.textSizeState;
    }

    public float getTextsize() {
        return this.textsize;
    }

    public void initData() {
    }

    public boolean isAutoPrimaryColor() {
        return this.isAutoPrimaryColor;
    }

    public boolean isMultiWindowBlur() {
        return this.mMultiWindowBlur;
    }

    public boolean isTextDark() {
        return this.textDark;
    }

    public boolean isVerticalMode() {
        return this.verticalMode;
    }

    public void setAlignment(int i) {
        this.alignment = i;
        this.verticalMode = i <= 102;
        initData();
    }

    public void setAodBlendColor(int i) {
        this.aodBlendColor = i;
    }

    public void setAodPrimaryColor(int i) {
        this.aodPrimaryColor = i;
    }

    public void setAutoPrimaryColor(boolean z) {
        this.isAutoPrimaryColor = z;
    }

    public void setBlendColor(int i) {
        this.blendColor = i;
    }

    public void setClockEffect(int i) {
        this.clockEffect = i;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setDisplayType(int i) {
        this.displayType = i;
    }

    public void setFullAodBlendColor(int i) {
        this.fullAodBlendColor = i;
    }

    public void setFullAodPrimaryColor(int i) {
        this.fullAodPrimaryColor = i;
    }

    public void setLetterSpaceUnit(float f) {
        this.letterSpaceUnit = f;
    }

    public void setLetterSpaceValue(float f) {
        this.letterSpaceValue = f;
    }

    public void setLineSpaceUnit(float f) {
        this.lineSpaceUnit = f;
    }

    public void setLineSpaceValue(float f) {
        this.lineSpaceValue = f;
    }

    public void setPrimaryColor(int i) {
        this.primaryColor = i;
    }

    public void setTemplateFontType(int i) {
        this.templateFontType = i;
    }

    public void setTextDark(boolean z) {
        this.textDark = z;
    }

    public void setTextSizeState(int i) {
        this.textSizeState = i;
    }

    public void setTextsize(float f) {
        this.textsize = f;
    }

    public String toString() {
        return "SignatureInfo{templateId='" + this.templateId + "', content='" + this.content + "', primaryColor=" + this.primaryColor + ", alignment=" + this.alignment + ", isAutoPrimaryColor=" + this.isAutoPrimaryColor + ", width=" + this.width + ", height=" + this.height + ", textsize=" + this.textsize + ", verticalMode=" + this.verticalMode + ", letterSpaceValue=" + this.letterSpaceValue + ", lineSpaceValue=" + this.lineSpaceValue + ", fontFamily='" + this.fontFamily + "', lines=" + this.lines + ", blendColor=" + this.blendColor + ", aodBlendColor=" + this.aodBlendColor + ", textDark=" + this.textDark + ", clockEffect=" + this.clockEffect + ", aodPrimaryColor=" + this.aodPrimaryColor + ", fullAodBlendColor=" + this.fullAodBlendColor + ", letterSpaceUnit=" + this.letterSpaceUnit + ", lineSpaceUnit=" + this.lineSpaceUnit + ", textSizeState=" + this.textSizeState + ", templateFontType=" + this.templateFontType + '}';
    }
}
