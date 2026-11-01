package miuix.view;

import android.R;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MiuiBlurUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.AttributeResolver;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiBlurUiHelper implements BlurableWidget {
    public boolean mActualApply;
    public boolean mActualEnable;
    public int[] mBlurBlendColorModes;
    public int[] mBlurBlendColors;
    public int mBlurRadius;
    public final BlurStateCallback mCallback;
    public final Context mContext;
    public boolean mEnableBlurSelf;
    public boolean mExpectedApply;
    public boolean mExpectedEnable;
    public final boolean mIsBlurParamsByCallback;
    public boolean mIsSupportBlur;
    public boolean mMaterialFullyTakenOver;
    public final View mTargetView;
    public int mViewBlurMode;

    public interface BlurStateCallback {
        default Drawable getBackground() {
            return null;
        }

        default int getBackgroundColor() {
            return 0;
        }

        default BlurApplier getBlurApplier() {
            return null;
        }

        default MaterialConfig.BlurConfig getBlurConfig(boolean z) {
            return null;
        }

        default boolean isLightTheme() {
            return true;
        }

        void onBlurApplyStateChanged(boolean z);

        void onBlurEnableStateChanged(boolean z);

        default void onCreateBlurParams(MiuiBlurUiHelper miuiBlurUiHelper) {
        }
    }

    public MiuiBlurUiHelper(Context context, View view, boolean z, boolean z2, BlurStateCallback blurStateCallback) {
        this(context, view, z, z2, true, blurStateCallback);
    }

    public MiuiBlurUiHelper(Context context, View view, boolean z, boolean z2, boolean z3, BlurStateCallback blurStateCallback) {
        this.mIsSupportBlur = false;
        this.mExpectedEnable = false;
        this.mActualEnable = false;
        this.mExpectedApply = false;
        this.mActualApply = false;
        this.mBlurBlendColors = null;
        this.mBlurBlendColorModes = null;
        this.mBlurRadius = 0;
        this.mMaterialFullyTakenOver = false;
        this.mContext = context;
        this.mTargetView = view;
        this.mCallback = blurStateCallback;
        this.mIsBlurParamsByCallback = z2;
        this.mEnableBlurSelf = z3;
        this.mViewBlurMode = z ? 2 : 1;
    }

    public static int[] getFinalBlendColorForViewByBackgroundColor(Context context, int i, int[] iArr, int[] iArr2) {
        int length = iArr.length;
        int[] iArr3 = new int[length];
        System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
        if (i == 0) {
            Drawable drawableResolveDrawable = AttributeResolver.resolveDrawable(context, R.attr.windowBackground);
            if (drawableResolveDrawable instanceof ColorDrawable) {
                i = ((ColorDrawable) drawableResolveDrawable).getColor();
            }
        }
        if (i == 0 || i == iArr[iArr.length - 1]) {
            return iArr3;
        }
        int i2 = (i >> 16) & 255;
        int i3 = i & 255;
        if (i2 == ((i >> 8) & 255) && i2 == i3) {
            int i4 = length - 1;
            iArr3[i4] = (i & 16777215) | ((-16777216) & iArr[i4]);
            return iArr3;
        }
        int length2 = iArr.length;
        int[] iArr4 = new int[length2 + 1];
        System.arraycopy(iArr, 0, iArr4, 0, iArr.length);
        iArr4[length2] = (i & 16777215) | 805306368;
        return iArr4;
    }

    public static int[] getFinalBlendColorForViewByBackgroundColor(Context context, Drawable drawable, int[] iArr, int[] iArr2) {
        Integer colorFromDrawable;
        return (drawable == null || (colorFromDrawable = MiuixUIUtils.getColorFromDrawable(drawable)) == null) ? iArr : getFinalBlendColorForViewByBackgroundColor(context, colorFromDrawable.intValue(), iArr, iArr2);
    }

    public final void applyBlur(float f) {
        this.mMaterialFullyTakenOver = false;
        if (this.mEnableBlurSelf) {
            applyBlurSelf(f);
        } else {
            applyBlurParent(f);
        }
    }

    @Override // miuix.view.BlurableWidget
    public void applyBlur(boolean z) {
        this.mExpectedApply = z;
        applyBlurInner(z);
    }

    public final void applyBlurInner(boolean z) {
        if (this.mIsSupportBlur && this.mActualEnable && this.mActualApply != z) {
            this.mActualApply = z;
            if (z) {
                refreshBlur();
            } else {
                handleCancelBlur();
            }
        }
    }

    public final void applyBlurParent(float f) {
        MiuiBlurUtils.setViewBlurMode(this.mTargetView, this.mViewBlurMode);
        int i = this.mBlurRadius;
        if (i >= 0) {
            MiuiBlurUtils.setBackgroundBlurRadius(this.mTargetView, dpToPx(i, f));
        }
    }

    public final void applyBlurSelf(float f) {
        BlurApplier blurApplier = this.mCallback.getBlurApplier();
        if (blurApplier == null) {
            blurApplier = BlurApplier.UNIFORM;
        }
        int i = this.mBlurRadius;
        if (i <= 0) {
            blurApplier.clearBlur(this.mTargetView);
            return;
        }
        int iDpToPx = dpToPx(i, f);
        BlurStateCallback blurStateCallback = this.mCallback;
        this.mMaterialFullyTakenOver = blurApplier.applyMaterial(this.mTargetView, iDpToPx, this.mViewBlurMode, blurStateCallback.getBlurConfig(blurStateCallback.isLightTheme()));
    }

    public final void applyColorBlend() {
        int[] iArr;
        int[] iArr2 = this.mBlurBlendColors;
        if (iArr2 == null || (iArr = this.mBlurBlendColorModes) == null) {
            return;
        }
        MiuiBlurUtils.setBackgroundBlendConfig(this.mTargetView, iArr2, iArr);
    }

    public final void createBlurParamsIfNeeded() {
        if (this.mEnableBlurSelf) {
            MiuiBlurUtils.clearBackgroundBlur(this.mTargetView);
        } else {
            MiuiBlurUtils.setViewBlurMode(this.mTargetView, 0);
        }
        MiuiBlurUtils.clearBackgroundBlendConfig(this.mTargetView);
        if (this.mIsBlurParamsByCallback) {
            this.mCallback.onCreateBlurParams(this);
        } else {
            createBlurParamsInternal();
        }
    }

    public final void createBlurParamsInternal() {
        BlurStateCallback blurStateCallback = this.mCallback;
        MaterialConfig.BlurConfig blurConfig = blurStateCallback.getBlurConfig(blurStateCallback.isLightTheme());
        if (blurConfig == null) {
            return;
        }
        Drawable background = this.mCallback.getBackground();
        int backgroundColor = this.mCallback.getBackgroundColor();
        int i = blurConfig.blurRadius;
        MaterialConfig.ColorBlendConfig colorBlendConfig = blurConfig.colorBlendConfig;
        if (colorBlendConfig == null) {
            this.mBlurRadius = i;
            return;
        }
        int[] iArr = colorBlendConfig.blendColors;
        int[] finalBlendColorForViewByBackgroundColor = backgroundColor != 0 ? getFinalBlendColorForViewByBackgroundColor(this.mContext, backgroundColor, iArr, colorBlendConfig.blendModes) : getFinalBlendColorForViewByBackgroundColor(this.mContext, background, iArr, colorBlendConfig.blendModes);
        int length = finalBlendColorForViewByBackgroundColor.length;
        int[] iArr2 = colorBlendConfig.blendModes;
        if (length > iArr2.length) {
            int length2 = finalBlendColorForViewByBackgroundColor.length;
            int[] iArr3 = new int[length2];
            System.arraycopy(iArr2, 0, iArr3, 0, iArr2.length);
            iArr3[length2 - 1] = 3;
            iArr2 = iArr3;
        }
        setBlurParams(finalBlendColorForViewByBackgroundColor, iArr2, i);
    }

    public final int dpToPx(float f, float f2) {
        return (int) ((f * f2) + 0.5f);
    }

    public final float getDensity() {
        try {
            return this.mTargetView.getContext().getResources().getDisplayMetrics().density;
        } catch (Exception unused) {
            return 2.75f;
        }
    }

    public final void handleCancelBlur() {
        MiuiBlurUtils.clearBackgroundBlur(this.mTargetView);
        MiuiBlurUtils.clearBackgroundBlendConfig(this.mTargetView);
        this.mCallback.onBlurApplyStateChanged(false);
    }

    public boolean isApplyBlur() {
        return this.mExpectedApply;
    }

    public boolean isEnableBlur() {
        return this.mExpectedEnable;
    }

    public boolean isSupportBlur() {
        return this.mIsSupportBlur;
    }

    public void onConfigChanged() {
        resetBlurParams();
        if (!HyperMaterialUtils.isFeatureEnable(this.mContext)) {
            setEnableInner(false);
        } else if (HyperMaterialUtils.isSupport() && isEnableBlur()) {
            setEnableInner(true);
        }
    }

    public void refreshBlur() {
        if (this.mActualApply) {
            if (this.mBlurBlendColors == null) {
                createBlurParamsIfNeeded();
            }
            float density = getDensity();
            this.mCallback.onBlurApplyStateChanged(true);
            MiuiBlurUtils.setMiViewMaterialType(this.mTargetView, 0);
            applyBlur(density);
            if (this.mMaterialFullyTakenOver) {
                return;
            }
            applyColorBlend();
        }
    }

    public void resetBlurParams() {
        this.mBlurBlendColors = null;
        this.mBlurBlendColorModes = null;
        this.mBlurRadius = 0;
    }

    public void setBlurParams(int[] iArr, int[] iArr2, int i) {
        this.mBlurBlendColors = iArr;
        this.mBlurBlendColorModes = iArr2;
        this.mBlurRadius = i;
    }

    public void setEnableBlur(boolean z) {
        if (this.mIsSupportBlur) {
            this.mExpectedEnable = z;
            if (HyperMaterialUtils.isFeatureEnable(this.mContext)) {
                setEnableInner(this.mExpectedEnable);
            }
        }
    }

    public final void setEnableInner(boolean z) {
        if (this.mActualEnable == z) {
            return;
        }
        if (z) {
            this.mActualEnable = true;
            applyBlurInner(this.mExpectedApply);
        } else {
            applyBlurInner(false);
            this.mActualEnable = false;
        }
        this.mCallback.onBlurEnableStateChanged(this.mActualEnable);
    }

    public void setSupportBlur(boolean z) {
        this.mIsSupportBlur = z;
    }
}
