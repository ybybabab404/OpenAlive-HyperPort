package com.miui.clock.tiny.model;

/* JADX INFO: loaded from: classes.dex */
public class TinyClockBean {
    public int aodColor;
    public int blendColor;
    public int blendColor180;
    public float mScale = 1.0f;
    public boolean needInverseColor = false;
    public int primaryColor;
    public int primaryColor180;
    public int secondaryBlendColor0;
    public int secondaryBlendColor180;
    public int secondaryColor0;
    public int secondaryColor180;
    public int style;
    public String templateId;
    public boolean wallpaperDark;
    public boolean wallpaperDark180;

    public int getAodColor() {
        return this.aodColor;
    }

    public int getBlendColor() {
        return this.blendColor;
    }

    public int getBlendColor180() {
        return this.blendColor180;
    }

    public int getPrimaryColor() {
        return this.primaryColor;
    }

    public int getPrimaryColor180() {
        return this.primaryColor180;
    }

    public float getScale() {
        return this.mScale;
    }

    public int getSecondaryBlendColor0() {
        return this.secondaryBlendColor0;
    }

    public int getSecondaryBlendColor180() {
        return this.secondaryBlendColor180;
    }

    public int getSecondaryColor0() {
        return this.secondaryColor0;
    }

    public int getSecondaryColor180() {
        return this.secondaryColor180;
    }

    public int getStyle() {
        return this.style;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public boolean isNeedInverseColor() {
        return this.needInverseColor;
    }

    public boolean isWallpaperDark() {
        return this.wallpaperDark;
    }

    public boolean isWallpaperDark180() {
        return this.wallpaperDark180;
    }

    public void setBlendColor(int i) {
        this.blendColor = i;
    }

    public void setBlendColor180(int i) {
        this.blendColor180 = i;
    }

    public void setNeedInverseColor(boolean z) {
        this.needInverseColor = z;
    }

    public void setPrimaryColor(int i) {
        this.primaryColor = i;
    }

    public void setPrimaryColor180(int i) {
        this.primaryColor180 = i;
    }

    public void setStyle(int i) {
        this.style = i;
    }

    public void setTemplateId(String str) {
        this.templateId = str;
    }

    public void setWallpaperDark(boolean z) {
        this.wallpaperDark = z;
    }

    public void setWallpaperDark180(boolean z) {
        this.wallpaperDark180 = z;
    }

    public String toString() {
        return "TinyClockBean{, templateId='" + this.templateId + "', style=" + this.style + ", primaryColor=" + this.primaryColor + ", primaryColor180=" + this.primaryColor180 + ", secondaryColor0=" + this.secondaryColor0 + ", secondaryColor180=" + this.secondaryColor180 + ", secondaryBlendColor0=" + this.secondaryBlendColor0 + ", secondaryBlendColor180=" + this.secondaryBlendColor180 + ", wallpaperDark=" + this.wallpaperDark + ", wallpaperDark180=" + this.wallpaperDark180 + ", blendColor=" + this.blendColor + ", blendColor180=" + this.blendColor180 + ", mScale=" + this.mScale + ", needInverseColor=" + this.needInverseColor + ", aodColor=" + this.aodColor + '}';
    }
}
