package miuix.androidbasewidget.widget;

import android.view.View;
import androidx.viewpager2.widget.OriginalViewPager2;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public final class CompositePageTransformer implements OriginalViewPager2.PageTransformer {
    public final List mTransformers;

    @Override // androidx.viewpager2.widget.OriginalViewPager2.PageTransformer
    public void transformPage(View view, float f) {
        Iterator it = this.mTransformers.iterator();
        while (it.hasNext()) {
            ((OriginalViewPager2.PageTransformer) it.next()).transformPage(view, f);
        }
    }
}
