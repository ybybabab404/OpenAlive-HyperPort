package com.miui.clock.tiny.model;

import com.miui.clock.tiny.bubble.BubbleAodClockInfo;
import com.miui.clock.tiny.bubble.BubbleClockInfo;
import com.miui.clock.tiny.bubble.BubbleClockTopInfo;
import com.miui.clock.tiny.classic.ClassicClockInfo;
import com.miui.clock.tiny.doodle.DoodleClockInfo;
import com.miui.clock.tiny.pets.PetsClockInfo;
import com.miui.clock.tiny.pets.PetsClockTopInfo;
import com.miui.clock.tiny.pets.ai.AiPetsClockInfo;
import com.miui.clock.tiny.pets.interactive.InteractivePetsClockInfo;
import com.miui.clock.tiny.splicing.SplicingClockInfo;

/* JADX INFO: loaded from: classes.dex */
public abstract class TinyClockInfo {
    public int mAODColor;
    public int mClockStyle;
    public int mRotation;
    public int mType;
    public String templateId;
    public boolean wallpaperDark0;
    public boolean wallpaperDark180;
    public int mPrimaryColor0 = -1;
    public int mPrimaryColor180 = -1;
    public int mSecondaryColor0 = -1;
    public int mSecondaryColor180 = -1;
    public int mSecondaryBlendColor0 = -1;
    public int mSecondaryBlendColor180 = -1;
    public int blendColor0 = -1;
    public int blendColor180 = -1;
    public float mScale = 1.0f;

    public TinyClockInfo(TinyClockBean tinyClockBean, int i) {
        setTemplateId(tinyClockBean.getTemplateId());
        setType(i);
        parseClockBean(tinyClockBean);
    }

    public static TinyClockInfo convertInfoFromClockBean(TinyClockBean tinyClockBean, int i) {
        String templateId = tinyClockBean.getTemplateId();
        templateId.getClass();
        switch (templateId) {
            case "splicing":
                return new SplicingClockInfo(tinyClockBean, i);
            case "bubble":
                return isAODType(i) ? new BubbleAodClockInfo(tinyClockBean, i) : new BubbleClockInfo(tinyClockBean, i);
            case "doodle":
                return new DoodleClockInfo(tinyClockBean, i);
            case "interactive_pets":
                return new InteractivePetsClockInfo(tinyClockBean, i);
            case "ai_pets":
                return new AiPetsClockInfo(tinyClockBean, i);
            case "pets":
                return new PetsClockInfo(tinyClockBean, i);
            case "bubble_top":
                return new BubbleClockTopInfo(tinyClockBean, i);
            case "pets_top":
                return new PetsClockTopInfo(tinyClockBean, i);
            case "classic":
                return new ClassicClockInfo(tinyClockBean, i);
            default:
                return null;
        }
    }

    public static boolean isAODType(int i) {
        return i == 1;
    }

    private void parseClockBean(TinyClockBean tinyClockBean) {
        setWallpaperDark0(tinyClockBean.isWallpaperDark());
        setWallpaperDark180(tinyClockBean.isWallpaperDark180());
        setScale(tinyClockBean.getScale());
        setPrimaryColor0(tinyClockBean.getPrimaryColor());
        setPrimaryColor180(tinyClockBean.getPrimaryColor180());
        setClockStyle(tinyClockBean.getStyle());
    }

    public int getAODColor() {
        return this.mAODColor;
    }

    public int getBlendColor() {
        if (getRotation() == 2) {
            int i = this.blendColor180;
            if (i == 0) {
                return -1;
            }
            return i;
        }
        int i2 = this.blendColor0;
        if (i2 == 0) {
            return -1;
        }
        return i2;
    }

    public int getClockStyle() {
        return this.mClockStyle;
    }

    public abstract int getLayoutId(int i);

    public int getPrimaryColor() {
        return getRotation() == 2 ? this.mPrimaryColor180 : this.mPrimaryColor0;
    }

    public int getRotation() {
        return this.mRotation;
    }

    public float getScale() {
        return this.mScale;
    }

    public int getSecondaryBlendColor() {
        if (this.mRotation == 2) {
            int i = this.mSecondaryBlendColor180;
            if (i == 0) {
                return -1;
            }
            return i;
        }
        int i2 = this.mSecondaryBlendColor0;
        if (i2 == 0) {
            return -1;
        }
        return i2;
    }

    public int getSecondaryColor() {
        if (this.mRotation == 2) {
            int i = this.mSecondaryColor180;
            if (i == 0) {
                return -1;
            }
            return i;
        }
        int i2 = this.mSecondaryColor0;
        if (i2 == 0) {
            return -1;
        }
        return i2;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public int getType() {
        return this.mType;
    }

    public boolean isAODType() {
        return getType() == 1;
    }

    public boolean isWallpaperDark() {
        return getRotation() == 2 ? this.wallpaperDark180 : this.wallpaperDark0;
    }

    public boolean isWallpaperDark0() {
        return this.wallpaperDark0;
    }

    public boolean isWallpaperDark180() {
        return this.wallpaperDark180;
    }

    public void setAODColor(int i) {
        this.mAODColor = i;
    }

    public void setBlendColor0(int i) {
        if (i != 0) {
            this.blendColor0 = i;
        }
    }

    public void setBlendColor180(int i) {
        if (i != 0) {
            this.blendColor180 = i;
        }
    }

    public void setClockStyle(int i) {
        this.mClockStyle = i;
    }

    public void setPrimaryColor(int i) {
        this.mPrimaryColor180 = i;
        this.mPrimaryColor0 = i;
    }

    public void setPrimaryColor0(int i) {
        this.mPrimaryColor0 = i;
    }

    public void setPrimaryColor180(int i) {
        this.mPrimaryColor180 = i;
    }

    public void setRotation(int i) {
        this.mRotation = i;
    }

    public void setScale(float f) {
        this.mScale = f;
    }

    public void setSecondaryBlendColor0(int i) {
        this.mSecondaryBlendColor0 = i;
    }

    public void setSecondaryBlendColor180(int i) {
        this.mSecondaryBlendColor180 = i;
    }

    public void setSecondaryColor0(int i) {
        this.mSecondaryColor0 = i;
    }

    public void setSecondaryColor180(int i) {
        this.mSecondaryColor180 = i;
    }

    public void setTemplateId(String str) {
        this.templateId = str;
    }

    public void setType(int i) {
        this.mType = i;
    }

    public void setWallpaperDark0(boolean z) {
        this.wallpaperDark0 = z;
    }

    public void setWallpaperDark180(boolean z) {
        this.wallpaperDark180 = z;
    }

    public String toString() {
        return "TinyClockInfo{mType=" + this.mType + ", mPrimaryColor0=" + this.mPrimaryColor0 + ", mPrimaryColor180=" + this.mPrimaryColor180 + ", mSecondaryColor0=" + this.mSecondaryColor0 + ", mSecondaryColor180=" + this.mSecondaryColor180 + ", mSecondaryBlendColor0=" + this.mSecondaryBlendColor0 + ", mSecondaryBlendColor180=" + this.mSecondaryBlendColor180 + ", mRotation=" + this.mRotation + ", blendColor0=" + this.blendColor0 + ", blendColor180=" + this.blendColor180 + ", wallpaperDark0=" + this.wallpaperDark0 + ", wallpaperDark180=" + this.wallpaperDark180 + ", mClockStyle=" + this.mClockStyle + ", mScale=" + this.mScale + ", mAODColor=" + this.mAODColor + ", templateId='" + this.templateId + "'}";
    }
}
