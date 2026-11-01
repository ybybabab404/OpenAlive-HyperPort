package miuix.graphics.shadow;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import miuix.core.util.MaterialConfig;

/* JADX INFO: loaded from: classes2.dex */
public class DropShadowConfig {
    public float blurRadiusDp;
    public BlurMaskFilter.Blur blurStyle;
    public boolean clipShadowEnable;
    public float dispersion;
    public float offsetXDp;
    public float offsetYDp;
    public int shadowColor;
    public int shadowDarkColor;

    public static class Builder {
        public final DropShadowConfig dropShadowConfig;

        public Builder(float f) {
            this.dropShadowConfig = new DropShadowConfig(f);
        }

        public Builder(MaterialConfig.ShadowConfig shadowConfig) {
            int i = shadowConfig.shadowColor;
            this.dropShadowConfig = new DropShadowConfig(i, i, shadowConfig.shadowOffsetX, shadowConfig.shadowOffsetY, shadowConfig.shadowRadius, shadowConfig.shadowDispersion, BlurMaskFilter.Blur.NORMAL);
        }

        public DropShadowConfig create() {
            return this.dropShadowConfig;
        }

        public Builder setClipShadowEnable(boolean z) {
            this.dropShadowConfig.clipShadowEnable = z;
            return this;
        }

        public Builder setColor(int i, int i2) {
            DropShadowConfig dropShadowConfig = this.dropShadowConfig;
            dropShadowConfig.shadowColor = i;
            dropShadowConfig.shadowDarkColor = i2;
            return this;
        }

        public Builder setDispersion(float f) {
            this.dropShadowConfig.dispersion = f;
            return this;
        }

        public Builder setOffsetXDp(int i) {
            this.dropShadowConfig.offsetXDp = i;
            return this;
        }

        public Builder setOffsetYDp(int i) {
            this.dropShadowConfig.offsetYDp = i;
            return this;
        }
    }

    public DropShadowConfig(float f) {
        this(f, BlurMaskFilter.Blur.NORMAL);
    }

    public DropShadowConfig(float f, BlurMaskFilter.Blur blur) {
        this(Color.parseColor("#0D000000"), Color.parseColor("#0DFFFFFF"), 0.0f, 0.0f, f, 1.0f, blur);
    }

    public DropShadowConfig(int i, int i2, float f, float f2, float f3, float f4, BlurMaskFilter.Blur blur) {
        this.clipShadowEnable = true;
        this.shadowColor = i;
        this.shadowDarkColor = i2;
        this.offsetXDp = f;
        this.offsetYDp = f2;
        this.blurRadiusDp = f3;
        this.dispersion = f4;
        this.blurStyle = blur;
    }
}
