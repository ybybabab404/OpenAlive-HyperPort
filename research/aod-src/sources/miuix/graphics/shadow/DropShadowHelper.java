package miuix.graphics.shadow;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MiShadowUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.core.util.RomUtils;

/* JADX INFO: loaded from: classes2.dex */
public class DropShadowHelper {
    public Context mContext;
    public DropShadowConfig mDropShadowConfig;
    public boolean mEnableMiShadow;
    public boolean mIsLightTheme;
    public boolean[] mOriginViewParentClipState;
    public int mShadowColor;
    public int mShadowColorAlpha;
    public float mOffsetXPx = 0.0f;
    public float mOffsetYPx = 0.0f;
    public float mBlurRadiusPx = 0.0f;
    public float mAlpha = 1.0f;
    public float mLastDensity = 0.0f;
    public RectF mShadowRect = new RectF();
    public Paint mShadowPaint = new Paint();
    public boolean mEnableShadow = false;

    public DropShadowHelper(Context context, DropShadowConfig dropShadowConfig, boolean z) {
        boolean z2 = false;
        this.mContext = context;
        this.mDropShadowConfig = dropShadowConfig;
        this.mIsLightTheme = z;
        if (RomUtils.getHyperOsVersion() >= 2 && MiShadowUtils.SUPPORT_MI_SHADOW) {
            z2 = true;
        }
        this.mEnableMiShadow = z2;
        updateShadowValues(z, context.getResources().getDisplayMetrics().density, dropShadowConfig);
    }

    public void drawShadow(Canvas canvas, float f) {
        if (this.mEnableMiShadow) {
            return;
        }
        canvas.drawRoundRect(this.mShadowRect, f, f, this.mShadowPaint);
    }

    public void enableViewShadow(View view, boolean z, int i) {
        if (this.mEnableShadow == z) {
            return;
        }
        enableViewShadowInternal(view, z, i);
    }

    public final void enableViewShadowInternal(View view, boolean z, int i) {
        this.mEnableShadow = z;
        refreshViewShadow(view);
        if (this.mEnableShadow) {
            this.mOriginViewParentClipState = new boolean[i];
            for (int i2 = 0; i2 < i; i2++) {
                Object parent = view.getParent();
                if (parent == null) {
                    return;
                }
                ViewGroup viewGroup = (ViewGroup) parent;
                this.mOriginViewParentClipState[i2] = viewGroup.getClipChildren();
                viewGroup.setClipChildren(false);
                view = (View) parent;
            }
            return;
        }
        boolean[] zArr = this.mOriginViewParentClipState;
        if (zArr != null && zArr.length >= i) {
            for (int i3 = 0; i3 < i; i3++) {
                Object parent2 = view.getParent();
                if (parent2 == null) {
                    break;
                }
                ((ViewGroup) parent2).setClipChildren(this.mOriginViewParentClipState[i3]);
                view = (View) parent2;
            }
        }
        this.mOriginViewParentClipState = null;
    }

    public RectF getShadowRect() {
        return this.mShadowRect;
    }

    public void invalidateShadow(View view) {
        if (this.mEnableMiShadow) {
            int i = this.mShadowColor;
            float f = this.mOffsetXPx;
            float f2 = this.mOffsetYPx;
            float f3 = this.mBlurRadiusPx;
            DropShadowConfig dropShadowConfig = this.mDropShadowConfig;
            MiShadowUtils.setMiShadow(view, i, f, f2, f3, dropShadowConfig.dispersion, dropShadowConfig.clipShadowEnable);
        }
    }

    public boolean isEnableMiShadow() {
        return this.mEnableMiShadow;
    }

    public void onConfigChanged(View view, Configuration configuration, boolean z) {
        this.mIsLightTheme = z;
        updateShadowValues(z, (configuration.densityDpi * 1.0f) / 160.0f, this.mDropShadowConfig);
        refreshViewShadow(view);
    }

    public void onDensityChanged(float f, DropShadowConfig dropShadowConfig) {
        this.mOffsetXPx = MiuixUIUtils.dp2px(f, dropShadowConfig.offsetXDp);
        this.mOffsetYPx = MiuixUIUtils.dp2px(f, dropShadowConfig.offsetYDp);
        this.mBlurRadiusPx = MiuixUIUtils.dp2px(f, dropShadowConfig.blurRadiusDp);
    }

    public void refreshViewShadow(View view) {
        if (!this.mEnableMiShadow) {
            view.invalidate();
            return;
        }
        if (!this.mEnableShadow) {
            MiShadowUtils.clearMiShadow(view);
            return;
        }
        int i = this.mShadowColor;
        float f = this.mOffsetXPx;
        float f2 = this.mOffsetYPx;
        float f3 = this.mBlurRadiusPx;
        DropShadowConfig dropShadowConfig = this.mDropShadowConfig;
        MiShadowUtils.setMiShadow(view, i, f, f2, f3, dropShadowConfig.dispersion, dropShadowConfig.clipShadowEnable);
    }

    public void setClipShadow(boolean z) {
        DropShadowConfig dropShadowConfig = this.mDropShadowConfig;
        if (dropShadowConfig == null || dropShadowConfig.clipShadowEnable == z) {
            return;
        }
        dropShadowConfig.clipShadowEnable = z;
    }

    public void setEnableMiShadow(boolean z) {
        this.mEnableMiShadow = z;
    }

    public void updateDropShadowConfig(MaterialConfig.ShadowConfig shadowConfig) {
        this.mDropShadowConfig = new DropShadowConfig.Builder(shadowConfig).create();
        updateShadowValues(this.mIsLightTheme, this.mContext.getResources().getDisplayMetrics().density, this.mDropShadowConfig);
        this.mEnableShadow = this.mBlurRadiusPx > 0.0f;
    }

    public void updateDropShadowConfig(DropShadowConfig dropShadowConfig) {
        this.mDropShadowConfig = dropShadowConfig;
        updateShadowValues(this.mIsLightTheme, this.mContext.getResources().getDisplayMetrics().density, dropShadowConfig);
        this.mEnableShadow = this.mBlurRadiusPx > 0.0f;
    }

    public void updateShadowRect(int i, int i2, int i3, int i4) {
        this.mShadowRect.set(0.0f, 0.0f, i3 - i, i4 - i2);
    }

    public void updateShadowValues(boolean z, float f, DropShadowConfig dropShadowConfig) {
        int i = z ? dropShadowConfig.shadowColor : dropShadowConfig.shadowDarkColor;
        this.mShadowColor = i;
        this.mShadowColorAlpha = (i >> 24) & 255;
        this.mShadowPaint.setColor(i);
        if (this.mLastDensity != f) {
            this.mLastDensity = f;
        }
        onDensityChanged(f, dropShadowConfig);
        this.mShadowPaint.setShadowLayer(this.mBlurRadiusPx, this.mOffsetXPx, this.mOffsetYPx, this.mShadowColor);
    }

    public void updateViewShadow(View view, int i) {
        enableViewShadowInternal(view, this.mEnableShadow, i);
    }
}
