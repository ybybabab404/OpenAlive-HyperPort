package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes.dex */
public final class PreFillType {
    public static final Bitmap.Config DEFAULT_CONFIG = Bitmap.Config.RGB_565;
    public final Bitmap.Config config;
    public final int height;
    public final int weight;
    public final int width;

    public static class Builder {
    }

    public boolean equals(Object obj) {
        if (obj instanceof PreFillType) {
            PreFillType preFillType = (PreFillType) obj;
            if (this.height == preFillType.height && this.width == preFillType.width && this.weight == preFillType.weight && this.config == preFillType.config) {
                return true;
            }
        }
        return false;
    }

    public Bitmap.Config getConfig() {
        return this.config;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        return (((((this.width * 31) + this.height) * 31) + this.config.hashCode()) * 31) + this.weight;
    }

    public String toString() {
        return "PreFillSize{width=" + this.width + ", height=" + this.height + ", config=" + this.config + ", weight=" + this.weight + '}';
    }
}
