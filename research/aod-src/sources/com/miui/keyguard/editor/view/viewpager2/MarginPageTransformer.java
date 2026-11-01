package com.miui.keyguard.editor.view.viewpager2;

import android.view.View;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes2.dex */
public final class MarginPageTransformer implements ViewPager2.PageTransformer {
    public final int mMarginPx;

    public MarginPageTransformer(@Px int i) {
        checkArgumentNonnegative(i, "Margin must be non-negative");
        this.mMarginPx = i;
    }

    private ViewPager2 requireViewPager(View view) {
        ViewParent parent = view.getParent();
        ViewParent parent2 = parent.getParent();
        if ((parent instanceof RecyclerView) && (parent2 instanceof ViewPager2)) {
            return (ViewPager2) parent2;
        }
        throw new IllegalStateException("Expected the page view to be managed by a ViewPager2 instance.");
    }

    public final int checkArgumentNonnegative(int i, String str) {
        if (i >= 0) {
            return i;
        }
        throw new IllegalArgumentException(str);
    }

    @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.PageTransformer
    public void transformPage(@NonNull View view, float f) {
        ViewPager2 viewPager2RequireViewPager = requireViewPager(view);
        float f2 = this.mMarginPx * f;
        if (viewPager2RequireViewPager.getOrientation() != 0) {
            view.setTranslationY(f2);
            return;
        }
        if (viewPager2RequireViewPager.isRtl()) {
            f2 = -f2;
        }
        view.setTranslationX(f2);
    }
}
