package miuix.androidbasewidget.widget;

import android.view.View;
import android.view.ViewParent;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.OriginalViewPager2;

/* JADX INFO: loaded from: classes2.dex */
public final class MarginPageTransformer implements OriginalViewPager2.PageTransformer {
    public final int mMarginPx;

    public final OriginalViewPager2 requireViewPager(View view) {
        ViewParent parent = view.getParent();
        ViewParent parent2 = parent.getParent();
        if ((parent instanceof RecyclerView) && (parent2 instanceof OriginalViewPager2)) {
            return (OriginalViewPager2) parent2;
        }
        throw new IllegalStateException("Expected the page view to be managed by a ViewPager2 instance.");
    }

    @Override // androidx.viewpager2.widget.OriginalViewPager2.PageTransformer
    public void transformPage(View view, float f) {
        OriginalViewPager2 originalViewPager2RequireViewPager = requireViewPager(view);
        float f2 = this.mMarginPx * f;
        if (originalViewPager2RequireViewPager.getOrientation() != 0) {
            view.setTranslationY(f2);
            return;
        }
        if (originalViewPager2RequireViewPager.isRtl()) {
            f2 = -f2;
        }
        view.setTranslationX(f2);
    }
}
