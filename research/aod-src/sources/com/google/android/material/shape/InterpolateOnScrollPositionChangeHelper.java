package com.google.android.material.shape;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

/* JADX INFO: loaded from: classes.dex */
public class InterpolateOnScrollPositionChangeHelper {
    public final int[] containerLocation;
    public ScrollView containingScrollView;
    public MaterialShapeDrawable materialShapeDrawable;
    public final int[] scrollLocation;
    public View shapedView;

    /* JADX INFO: renamed from: com.google.android.material.shape.InterpolateOnScrollPositionChangeHelper$1, reason: invalid class name */
    public class AnonymousClass1 implements ViewTreeObserver.OnScrollChangedListener {
        public final /* synthetic */ InterpolateOnScrollPositionChangeHelper this$0;

        @Override // android.view.ViewTreeObserver.OnScrollChangedListener
        public void onScrollChanged() {
            this.this$0.updateInterpolationForScreenPosition();
        }
    }

    public void updateInterpolationForScreenPosition() {
        ScrollView scrollView = this.containingScrollView;
        if (scrollView == null) {
            return;
        }
        if (scrollView.getChildCount() == 0) {
            throw new IllegalStateException("Scroll bar must contain a child to calculate interpolation.");
        }
        this.containingScrollView.getLocationInWindow(this.scrollLocation);
        this.containingScrollView.getChildAt(0).getLocationInWindow(this.containerLocation);
        int top = (this.shapedView.getTop() - this.scrollLocation[1]) + this.containerLocation[1];
        int height = this.shapedView.getHeight();
        int height2 = this.containingScrollView.getHeight();
        if (top < 0) {
            this.materialShapeDrawable.setInterpolation(Math.max(0.0f, Math.min(1.0f, (top / height) + 1.0f)));
            this.shapedView.invalidate();
            return;
        }
        int i = top + height;
        if (i > height2) {
            this.materialShapeDrawable.setInterpolation(Math.max(0.0f, Math.min(1.0f, 1.0f - ((i - height2) / height))));
            this.shapedView.invalidate();
        } else if (this.materialShapeDrawable.getInterpolation() != 1.0f) {
            this.materialShapeDrawable.setInterpolation(1.0f);
            this.shapedView.invalidate();
        }
    }
}
