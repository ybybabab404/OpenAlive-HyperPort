package miuix.miuixbasewidget.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import miuix.internal.util.AttributeResolver;
import miuix.miuixbasewidget.R;
import miuix.theme.token.ShadowToken;

/* JADX INFO: loaded from: classes2.dex */
class FabShadowHelper {
    public boolean mEnabled = true;
    public boolean mFirstDrawDone = false;
    public BaseWidgetDropShadowHelper mShadowHelper;

    public static /* synthetic */ void $r8$lambda$ZEQzUAa60wF1Ide3QLlceJeB9C8(FabShadowHelper fabShadowHelper, View view) {
        BaseWidgetDropShadowHelper baseWidgetDropShadowHelper = fabShadowHelper.mShadowHelper;
        if (baseWidgetDropShadowHelper != null) {
            baseWidgetDropShadowHelper.refreshViewShadow(view);
        }
    }

    public final void disableAllParentClip(View view) {
        ViewParent parent = view.getParent();
        while (parent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) parent;
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
            parent = viewGroup.getParent();
        }
    }

    public void draw(Canvas canvas, int i) {
        BaseWidgetDropShadowHelper baseWidgetDropShadowHelper;
        if (!this.mEnabled || (baseWidgetDropShadowHelper = this.mShadowHelper) == null) {
            return;
        }
        baseWidgetDropShadowHelper.drawShadow(canvas, i);
    }

    public final void initHelper(Context context) {
        boolean zResolveBoolean = AttributeResolver.resolveBoolean(context, R.attr.isLightTheme, true);
        ShadowToken shadowToken = ShadowToken.Regular;
        int i = shadowToken.color;
        this.mShadowHelper = new BaseWidgetDropShadowHelper(context, new DropShadowConfig(i, i, shadowToken.offsetX, shadowToken.offsetY, shadowToken.radius, shadowToken.dispersion, true, BlurMaskFilter.Blur.NORMAL), zResolveBoolean);
    }

    public void invalidateShadow(View view, float f) {
        BaseWidgetDropShadowHelper baseWidgetDropShadowHelper = this.mShadowHelper;
        if (baseWidgetDropShadowHelper != null) {
            baseWidgetDropShadowHelper.invalidateShadow(view, f);
        }
    }

    public boolean isMiShadowEnabled() {
        BaseWidgetDropShadowHelper baseWidgetDropShadowHelper = this.mShadowHelper;
        return baseWidgetDropShadowHelper != null && baseWidgetDropShadowHelper.mEnableMiShadow;
    }

    public void onConfigChanged(View view, Configuration configuration) {
        if (view == null || this.mShadowHelper == null) {
            return;
        }
        this.mShadowHelper.onConfigChanged(view, configuration, AttributeResolver.resolveBoolean(view.getContext(), R.attr.isLightTheme, true));
    }

    public void onLayout(View view, int i, int i2, int i3, int i4) {
        if (this.mEnabled) {
            if (this.mShadowHelper == null) {
                initHelper(view.getContext());
                view.setOutlineProvider(new ViewOutlineProvider() { // from class: miuix.miuixbasewidget.widget.FabShadowHelper.1
                    @Override // android.view.ViewOutlineProvider
                    public void getOutline(View view2, Outline outline) {
                        outline.setOval(0, 0, view2.getWidth(), view2.getHeight());
                    }
                });
                view.setClipToOutline(false);
                disableAllParentClip(view);
            }
            this.mShadowHelper.updateShadowRect(i, i2, i3, i4);
            this.mShadowHelper.enableViewShadow(view, true, 2);
        }
    }

    public void postFirstDrawRefresh(final View view) {
        BaseWidgetDropShadowHelper baseWidgetDropShadowHelper;
        if (this.mFirstDrawDone || !this.mEnabled || (baseWidgetDropShadowHelper = this.mShadowHelper) == null || !baseWidgetDropShadowHelper.mEnableMiShadow) {
            return;
        }
        this.mFirstDrawDone = true;
        view.post(new Runnable() { // from class: miuix.miuixbasewidget.widget.FabShadowHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FabShadowHelper.$r8$lambda$ZEQzUAa60wF1Ide3QLlceJeB9C8(this.f$0, view);
            }
        });
    }

    public void setAlpha(int i) {
        BaseWidgetDropShadowHelper baseWidgetDropShadowHelper = this.mShadowHelper;
        if (baseWidgetDropShadowHelper != null) {
            baseWidgetDropShadowHelper.setAlpha(i / 255.0f);
        }
    }
}
