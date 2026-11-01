package miuix.appcompat.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Keep;
import com.miui.maml.folme.AnimatedProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.core.util.HyperBloomStrokeUtils;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiShadowUtils;
import miuix.core.util.MiuiBlurUtils;
import miuix.internal.util.ViewUtils;
import miuix.theme.token.BloomStrokeToken;
import miuix.theme.token.ColorBlendToken;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;
import miuix.theme.token.ShadowToken;

/* JADX INFO: loaded from: classes2.dex */
public class SearchViewMaterialImpl implements ISearchViewMaterialWidget {
    public static final MaterialToken Search_View_Glass_Dark;
    public static final MaterialToken Search_View_Glass_Light;
    public static final MaterialDayNightToken Search_View_Glass_Material;
    public final Map mBgAlphaTargets = new HashMap();
    public int mMaterialChangeVersion;
    public boolean mMaterialEnabled;
    public ViewGroup mMaterialTargetParent;

    @Keep
    public static class BackgroundAlphaTarget {
        private float mAlpha;
        private final Drawable mDrawable;

        public BackgroundAlphaTarget(Drawable drawable) {
            this.mDrawable = drawable;
            this.mAlpha = drawable.getAlpha() / 255.0f;
        }

        @Keep
        public float getAlpha() {
            return this.mAlpha;
        }

        @Keep
        public void setAlpha(float f) {
            float fMax = Math.max(0.0f, Math.min(1.0f, f));
            this.mAlpha = fMax;
            this.mDrawable.setAlpha((int) ((fMax * 255.0f) + 0.5f));
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$KbgzfZrQ4ZJLDeGmKxbl9xh-KMg, reason: not valid java name */
    public static /* synthetic */ void m1311$r8$lambda$KbgzfZrQ4ZJLDeGmKxbl9xhKMg(SearchViewMaterialImpl searchViewMaterialImpl, int i, List list) {
        if (searchViewMaterialImpl.mMaterialChangeVersion != i || searchViewMaterialImpl.mMaterialEnabled) {
            return;
        }
        searchViewMaterialImpl.setTargetsAlpha(list, 1.0f);
    }

    static {
        MaterialToken materialTokenBuild = new MaterialToken.Builder(30, "search-view-glass", "light").setColorBlend(ColorBlendToken.Pured_Thin_Glass_Light).setMaskBlur(20).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Light).setShadow(ShadowToken.Regular).build();
        Search_View_Glass_Light = materialTokenBuild;
        MaterialToken materialTokenBuild2 = new MaterialToken.Builder(30, "search-view-glass", "dark").setColorBlend(ColorBlendToken.Pured_Thin_Glass_Dark).setMaskBlur(20).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Dark).setShadow(ShadowToken.Regular).build();
        Search_View_Glass_Dark = materialTokenBuild2;
        Search_View_Glass_Material = new MaterialDayNightToken(materialTokenBuild, materialTokenBuild2);
    }

    public SearchViewMaterialImpl(ViewGroup viewGroup) {
        this.mMaterialTargetParent = viewGroup;
        viewGroup.setClipToPadding(false);
        this.mMaterialTargetParent.setClipChildren(false);
    }

    public final void applyMaterialEffects() {
        for (int i = 0; i < this.mMaterialTargetParent.getChildCount(); i++) {
            View childAt = this.mMaterialTargetParent.getChildAt(i);
            if (childAt != null && childAt.getVisibility() == 0) {
                applyMaterialEffects(childAt);
            }
        }
    }

    public final void applyMaterialEffects(View view) {
        int i;
        Context context = this.mMaterialTargetParent.getContext();
        boolean z = !ViewUtils.isNightMode(context);
        MaterialDayNightConfig materialDayNightConfigCreate = MaterialDayNightConfig.create(Search_View_Glass_Material);
        MaterialConfig materialConfig = materialDayNightConfigCreate != null ? materialDayNightConfigCreate.get(z) : null;
        if (materialConfig != null) {
            float f = context.getResources().getDisplayMetrics().density;
            if (materialConfig.getBlurConfig() != null && (i = materialConfig.getBlurConfig().blurRadius) > 0) {
                MiuiBlurUtils.setBackgroundBlur(view, (int) ((i * f) + 0.5f));
            }
            MiuiBlurUtils.setViewBlurMode(view, 1);
            if (materialConfig.getColorBlendConfig() != null) {
                MiuiBlurUtils.setBackgroundBlendConfig(view, materialConfig.getColorBlendConfig().blendColors, materialConfig.getColorBlendConfig().blendModes);
            }
            if (materialConfig.getBloomStrokeConfig() != null) {
                HyperBloomStrokeUtils.setBloomStrokeConfig(view, materialConfig.getBloomStrokeConfig());
            }
            if (materialConfig.getShadowConfig() != null) {
                MiShadowUtils.setShadowConfig(view, materialConfig.getShadowConfig());
            }
        }
    }

    public final void cancelAlphaAnimations(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Folme.useValue((BackgroundAlphaTarget) it.next()).cancel();
        }
    }

    public final void clearMaterialEffects() {
        for (int i = 0; i < this.mMaterialTargetParent.getChildCount(); i++) {
            View childAt = this.mMaterialTargetParent.getChildAt(i);
            if (childAt != null && childAt.getVisibility() == 0) {
                clearMaterialEffects(childAt);
            }
        }
        this.mBgAlphaTargets.clear();
    }

    public final void clearMaterialEffects(View view) {
        MiuiBlurUtils.clearBackgroundBlur(view);
        MiuiBlurUtils.setViewBlurMode(view, 0);
        MiuiBlurUtils.clearBackgroundBlendConfig(view);
        HyperBloomStrokeUtils.clearBloomStroke(view);
        MiShadowUtils.clearMiShadow(view);
    }

    public final List collectBgAlphaTargets() {
        Drawable background;
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        for (int i = 0; i < this.mMaterialTargetParent.getChildCount(); i++) {
            View childAt = this.mMaterialTargetParent.getChildAt(i);
            if (childAt != null && childAt.getVisibility() == 0 && (background = childAt.getBackground()) != null) {
                hashSet.add(background);
                BackgroundAlphaTarget backgroundAlphaTarget = (BackgroundAlphaTarget) this.mBgAlphaTargets.get(background);
                if (backgroundAlphaTarget == null) {
                    backgroundAlphaTarget = new BackgroundAlphaTarget(background);
                    this.mBgAlphaTargets.put(background, backgroundAlphaTarget);
                }
                arrayList.add(backgroundAlphaTarget);
            }
        }
        this.mBgAlphaTargets.keySet().retainAll(hashSet);
        return arrayList;
    }

    public boolean isMaterialEnabled() {
        return this.mMaterialEnabled;
    }

    public final void postRestoreBackgroundAlpha(final List list, final int i) {
        this.mMaterialTargetParent.postOnAnimation(new Runnable() { // from class: miuix.appcompat.app.SearchViewMaterialImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SearchViewMaterialImpl searchViewMaterialImpl = this.f$0;
                searchViewMaterialImpl.mMaterialTargetParent.postOnAnimation(new Runnable() { // from class: miuix.appcompat.app.SearchViewMaterialImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SearchViewMaterialImpl.m1311$r8$lambda$KbgzfZrQ4ZJLDeGmKxbl9xhKMg(searchViewMaterialImpl, i, list);
                    }
                });
            }
        });
    }

    public void setMaterialEnabled(boolean z) {
        if (this.mMaterialEnabled == z) {
            return;
        }
        if (!z || HyperMaterialUtils.isFeatureEnable(this.mMaterialTargetParent.getContext())) {
            this.mMaterialEnabled = z;
            this.mMaterialChangeVersion++;
            List listCollectBgAlphaTargets = collectBgAlphaTargets();
            if (ViewUtils.isNightMode(this.mMaterialTargetParent.getContext())) {
                setMaterialEnabledInDarkMode(z, listCollectBgAlphaTargets, this.mMaterialChangeVersion);
                return;
            }
            AnimConfig ease = new AnimConfig().setEase(FolmeEase.linear(350L));
            if (z) {
                applyMaterialEffects();
                if (listCollectBgAlphaTargets.isEmpty()) {
                    return;
                }
                Iterator it = listCollectBgAlphaTargets.iterator();
                while (it.hasNext()) {
                    Folme.useValue((BackgroundAlphaTarget) it.next()).setFlags(1L).to(AnimatedProperty.PROPERTY_NAME_ALPHA, Float.valueOf(0.0f), ease);
                }
                return;
            }
            if (listCollectBgAlphaTargets.isEmpty()) {
                clearMaterialEffects();
                return;
            }
            final boolean[] zArr = {false};
            ease.addListeners(new TransitionListener() { // from class: miuix.appcompat.app.SearchViewMaterialImpl.1
                public final void clearMaterialEffectsOnce() {
                    boolean[] zArr2 = zArr;
                    if (zArr2[0]) {
                        return;
                    }
                    zArr2[0] = true;
                    SearchViewMaterialImpl.this.clearMaterialEffects();
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    super.onComplete(obj);
                    clearMaterialEffectsOnce();
                }
            });
            Iterator it2 = listCollectBgAlphaTargets.iterator();
            while (it2.hasNext()) {
                Folme.useValue((BackgroundAlphaTarget) it2.next()).setFlags(1L).to(AnimatedProperty.PROPERTY_NAME_ALPHA, Float.valueOf(1.0f), ease);
            }
        }
    }

    public final void setMaterialEnabledInDarkMode(boolean z, List list, int i) {
        cancelAlphaAnimations(list);
        if (z) {
            setTargetsAlpha(list, 0.0f);
            applyMaterialEffects();
        } else {
            clearMaterialEffects();
            if (list.isEmpty()) {
                return;
            }
            postRestoreBackgroundAlpha(list, i);
        }
    }

    public final void setTargetsAlpha(List list, float f) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((BackgroundAlphaTarget) it.next()).setAlpha(f);
        }
    }
}
