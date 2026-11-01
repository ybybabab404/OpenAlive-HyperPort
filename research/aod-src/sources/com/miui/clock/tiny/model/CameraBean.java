package com.miui.clock.tiny.model;

/* JADX INFO: loaded from: classes.dex */
public class CameraBean {
    public int cameraIconBgColor;
    public int cameraIconColor;
    public boolean isCameraBlockAreaWallpaperDark;
    public boolean isCameraIconBgBlur;
    public boolean isDeepCameraWallpaper;
    public float mScale;
    public int mType;
    public String templateId;

    public int getCameraIconBgColor() {
        return this.cameraIconBgColor;
    }

    public int getCameraIconColor() {
        return this.cameraIconColor;
    }

    public float getScale() {
        return this.mScale;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public int getType() {
        return this.mType;
    }

    public boolean isCameraBlockAreaWallpaperDark() {
        return this.isCameraBlockAreaWallpaperDark;
    }

    public boolean isCameraIconBgBlur() {
        return this.isCameraIconBgBlur;
    }

    public boolean isDeepCameraWallpaper() {
        return this.isDeepCameraWallpaper;
    }

    public String toString() {
        return "CameraBean{cameraIconColor=" + this.cameraIconColor + ", cameraIconBgColor=" + this.cameraIconBgColor + ", isDeepCameraWallpaper=" + this.isDeepCameraWallpaper + ", isCameraIconBgBlur=" + this.isCameraIconBgBlur + ", isCameraBlockAreaWallpaperDark=" + this.isCameraBlockAreaWallpaperDark + ", mScale=" + this.mScale + ", mType=" + this.mType + '}';
    }
}
