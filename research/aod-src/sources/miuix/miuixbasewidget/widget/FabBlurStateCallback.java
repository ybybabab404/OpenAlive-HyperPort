package miuix.miuixbasewidget.widget;

import android.graphics.drawable.Drawable;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.view.MiuiBlurUiHelper;

/* JADX INFO: loaded from: classes2.dex */
class FabBlurStateCallback implements MiuiBlurUiHelper.BlurStateCallback {
    public final FloatingActionButton mFab;
    public int mOriginalBackgroundAlpha = 255;

    public FabBlurStateCallback(FloatingActionButton floatingActionButton) {
        if (floatingActionButton == null) {
            throw new IllegalArgumentException("FloatingActionButton must not be null");
        }
        this.mFab = floatingActionButton;
    }

    @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
    public Drawable getBackground() {
        return null;
    }

    @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
    public int getBackgroundColor() {
        return this.mFab.getFabColor();
    }

    @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
    public MaterialConfig.BlurConfig getBlurConfig(boolean z) {
        MaterialDayNightConfig material = this.mFab.getMaterial();
        if (material == null) {
            return null;
        }
        MaterialConfig.BlurConfig blurConfig = material.getBlurConfig(z);
        MaterialConfig.ColorBlendConfig colorBlendConfig = material.getColorBlendConfig(z);
        return (blurConfig != null || colorBlendConfig == null) ? blurConfig : new MaterialConfig.BlurConfig(colorBlendConfig);
    }

    @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
    public boolean isLightTheme() {
        return this.mFab.resolveIsLightTheme();
    }

    @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
    public void onBlurApplyStateChanged(boolean z) {
        Drawable background = this.mFab.getBackground();
        if (background != null) {
            if (!z) {
                background.setAlpha(this.mOriginalBackgroundAlpha);
            } else {
                this.mOriginalBackgroundAlpha = background.getAlpha();
                background.setAlpha(1);
            }
        }
    }

    @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
    public void onBlurEnableStateChanged(boolean z) {
    }
}
