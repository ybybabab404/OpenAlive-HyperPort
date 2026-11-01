package miuix.appcompat.app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Keep;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.base.AnimConfig;
import miuix.appcompat.R;
import miuix.core.util.MiShadowUtils;
import miuix.internal.util.ViewUtils;
import miuix.theme.token.ShadowToken;

/* JADX INFO: loaded from: classes2.dex */
public class SearchViewShadowImpl implements ISearchViewShadowWidget {
    public final Map mBlendTargets = new HashMap();
    public boolean mMiShadowEnabled;
    public ViewGroup mShadowTargetParent;
    public static final int SEARCH_VIEW_FLOATING_BG_COLOR_LIGHT = R.color.miuix_color_white_solid_100;
    public static final int SEARCH_VIEW_FLOATING_BG_COLOR_DARK = R.color.miuix_color_white_solid_40;

    @Keep
    public static class BackgroundBlendTarget {
        private float mBlend = 0.0f;
        private int mBlueComponent;
        private final Drawable mDrawable;
        private int mGreenComponent;
        private int mRedComponent;

        public BackgroundBlendTarget(Drawable drawable, int i, int i2, int i3) {
            this.mDrawable = drawable;
            this.mRedComponent = i;
            this.mGreenComponent = i2;
            this.mBlueComponent = i3;
        }

        @Keep
        public float getBlend() {
            return this.mBlend;
        }

        @Keep
        public void setBlend(float f) {
            this.mBlend = f;
            if (f <= 0.0f) {
                this.mDrawable.setColorFilter(null);
            } else {
                this.mDrawable.setColorFilter(new PorterDuffColorFilter(Color.argb((int) ((Math.min(f, 1.0f) * 255.0f) + 0.5f), this.mRedComponent, this.mGreenComponent, this.mBlueComponent), PorterDuff.Mode.SRC_OVER));
            }
        }
    }

    public SearchViewShadowImpl(ViewGroup viewGroup) {
        this.mShadowTargetParent = viewGroup;
        viewGroup.setClipToPadding(false);
        this.mShadowTargetParent.setClipChildren(false);
    }

    public final void animateWhiteBlend(List list, boolean z) {
        if (list.isEmpty()) {
            return;
        }
        AnimConfig ease = new AnimConfig().setEase(FolmeEase.linear(350L));
        float f = z ? 1.0f : 0.0f;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Folme.useValue((BackgroundBlendTarget) it.next()).setFlags(1L).to("blend", Float.valueOf(f), ease);
        }
    }

    public final BackgroundBlendTarget ensureBlendTarget(View view) {
        Drawable background = view.getBackground();
        if (background == null) {
            this.mBlendTargets.remove(view);
            return null;
        }
        BackgroundBlendTarget backgroundBlendTarget = (BackgroundBlendTarget) this.mBlendTargets.get(view);
        if (backgroundBlendTarget != null && backgroundBlendTarget.mDrawable == background) {
            return backgroundBlendTarget;
        }
        Drawable drawableMutate = background.mutate();
        if (drawableMutate != background) {
            view.setBackground(drawableMutate);
        }
        Context context = this.mShadowTargetParent.getContext();
        int color = context.getResources().getColor(ViewUtils.isNightMode(context) ? SEARCH_VIEW_FLOATING_BG_COLOR_DARK : SEARCH_VIEW_FLOATING_BG_COLOR_LIGHT);
        BackgroundBlendTarget backgroundBlendTarget2 = new BackgroundBlendTarget(drawableMutate, Color.red(color), Color.green(color), Color.blue(color));
        this.mBlendTargets.put(view, backgroundBlendTarget2);
        return backgroundBlendTarget2;
    }

    public boolean isMiShadowEnabled() {
        return this.mMiShadowEnabled;
    }

    public void setMiShadowEnabled(boolean z) {
        if (!MiShadowUtils.SUPPORT_MI_SHADOW || this.mMiShadowEnabled == z) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mShadowTargetParent.getChildCount(); i++) {
            View childAt = this.mShadowTargetParent.getChildAt(i);
            if (childAt != null && childAt.getVisibility() == 0) {
                updateMiShadowToChild(childAt, z);
                BackgroundBlendTarget backgroundBlendTargetEnsureBlendTarget = ensureBlendTarget(childAt);
                if (backgroundBlendTargetEnsureBlendTarget != null) {
                    arrayList.add(backgroundBlendTargetEnsureBlendTarget);
                }
            }
        }
        animateWhiteBlend(arrayList, z);
        this.mMiShadowEnabled = z;
    }

    public final void updateMiShadowToChild(View view, boolean z) {
        if (!z) {
            MiShadowUtils.clearMiShadow(view);
        } else {
            ShadowToken shadowToken = ShadowToken.Regular;
            MiShadowUtils.setMiShadow(view, shadowToken.color, shadowToken.offsetX, shadowToken.offsetY, shadowToken.radius);
        }
    }
}
