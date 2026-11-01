package miuix.graphics.shadow;

import android.graphics.BlurMaskFilter;

/* JADX INFO: loaded from: classes2.dex */
public class DropShadowMaskHelper extends DropShadowHelper {
    public BlurMaskFilter mBlurMaskFilter;

    @Override // miuix.graphics.shadow.DropShadowHelper
    public void onDensityChanged(float f, DropShadowConfig dropShadowConfig) {
        super.onDensityChanged(f, dropShadowConfig);
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(this.mBlurRadiusPx, dropShadowConfig.blurStyle);
        this.mBlurMaskFilter = blurMaskFilter;
        this.mShadowPaint.setMaskFilter(blurMaskFilter);
    }

    @Override // miuix.graphics.shadow.DropShadowHelper
    public void updateShadowRect(int i, int i2, int i3, int i4) {
        super.updateShadowRect(i, i2, i3, i4);
        this.mShadowRect.offset(this.mOffsetXPx, this.mOffsetYPx);
    }
}
