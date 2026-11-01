package miuix.appcompat.app;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import miuix.core.util.HyperMaterialUtils;

/* JADX INFO: loaded from: classes2.dex */
public class SearchModeStubView extends LinearLayout {
    public SearchViewMaterialImpl mMaterialImpl;
    public SearchViewShadowImpl mMiShadowImpl;
    public SearchViewOverlayMaskImpl mOverlayMaskImpl;

    public SearchModeStubView(@NonNull Context context) {
        super(context);
    }

    public SearchModeStubView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SearchModeStubView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public SearchModeStubView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public final void ensureMaterialImpl() {
        if (this.mMaterialImpl == null) {
            this.mMaterialImpl = new SearchViewMaterialImpl(getMaterialTargetParent());
        }
    }

    public final void ensureMiShadowImpl() {
        if (this.mMiShadowImpl == null) {
            this.mMiShadowImpl = new SearchViewShadowImpl(getMiShadowTargetParent());
        }
    }

    public final void ensureOverlayMaskImpl() {
        if (this.mOverlayMaskImpl == null) {
            this.mOverlayMaskImpl = new SearchViewOverlayMaskImpl(this);
        }
    }

    public ViewGroup getMaterialTargetParent() {
        return (ViewGroup) getChildAt(0);
    }

    public ViewGroup getMiShadowTargetParent() {
        return getMaterialTargetParent();
    }

    public boolean isMaterialEnabled() {
        ensureMaterialImpl();
        return this.mMaterialImpl.isMaterialEnabled();
    }

    public boolean isMiShadowEnabled() {
        ensureMiShadowImpl();
        return this.mMiShadowImpl.isMiShadowEnabled();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        SearchViewOverlayMaskImpl searchViewOverlayMaskImpl = this.mOverlayMaskImpl;
        if (searchViewOverlayMaskImpl != null) {
            searchViewOverlayMaskImpl.drawOverlayMask(canvas);
        }
    }

    public void setFloatingMode(boolean z) {
        if (z) {
            if (HyperMaterialUtils.isFeatureEnableNoCache(getContext())) {
                setMaterialEnabled(true);
                return;
            } else {
                setMiShadowEnabled(true);
                return;
            }
        }
        if (isMaterialEnabled()) {
            setMaterialEnabled(false);
        }
        if (isMiShadowEnabled()) {
            setMiShadowEnabled(false);
        }
    }

    public void setMaskColor(int i) {
        ensureOverlayMaskImpl();
        this.mOverlayMaskImpl.setMaskColor(i);
    }

    public void setMaskExtraHeight(float f) {
        ensureOverlayMaskImpl();
        this.mOverlayMaskImpl.setMaskExtraHeight(f);
    }

    public void setMaterialEnabled(boolean z) {
        ensureMaterialImpl();
        this.mMaterialImpl.setMaterialEnabled(z);
    }

    public void setMiShadowEnabled(boolean z) {
        ensureMiShadowImpl();
        this.mMiShadowImpl.setMiShadowEnabled(z);
    }

    public void setOverlayMaskConfig(@Nullable OverlayMaskConfig overlayMaskConfig) {
        ensureOverlayMaskImpl();
        this.mOverlayMaskImpl.setOverlayMaskConfig(overlayMaskConfig);
    }

    public void setOverlayMaskEnabled(boolean z) {
        ensureOverlayMaskImpl();
        this.mOverlayMaskImpl.setOverlayMaskEnabled(z);
    }
}
