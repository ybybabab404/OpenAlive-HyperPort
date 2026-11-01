package com.miui.keyguard.editor.homepage.view.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent3;
import com.miui.keyguard.editor.homepage.util.CrossListViewUtil;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;

/* JADX INFO: loaded from: classes2.dex */
public class DampViewPager2 extends ViewPager2 implements NestedScrollingParent3 {
    public static boolean isSliding = false;
    public int screenHeight;
    public int totalDx;
    public int totalDy;

    public DampViewPager2(@NonNull Context context) {
        this(context, null);
    }

    public DampViewPager2(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.totalDy = 0;
        this.totalDx = 0;
        setNestedScrollingEnabled(true);
    }

    public final float calculateAfterFrictionValueVertically(int i, int i2) {
        int i3 = i > 0 ? 1 : -1;
        float f = i2;
        float fMin = Math.min((Math.abs(i) * 1.0f) / f, 1.0f);
        float f2 = fMin * fMin;
        return i3 * ((((f2 * fMin) / 3.0f) - f2) + fMin) * f;
    }

    public final float calculateTargetOffsetVertically(int i, int i2) {
        return ((calculateAfterFrictionValueVertically(i2, requireScreenHeight()) * 0.4f) / i2) * i;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        super.onNestedPreScroll(view, i, i2, iArr);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3) {
        if (getOrientation() != 1) {
            return;
        }
        onNestedPreScrollVertically(view, i, i2, iArr, i3);
    }

    public final void onNestedPreScrollVertically(View view, int i, int i2, int[] iArr, int i3) {
        if (i2 == 0) {
            return;
        }
        int i4 = this.totalDy + i2;
        this.totalDy = i4;
        iArr[0] = 0;
        if (Math.abs(i4) >= requireScreenHeight()) {
            iArr[1] = i2;
        } else {
            iArr[1] = (int) (i2 - calculateTargetOffsetVertically(i2, this.totalDy));
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
    }

    @Override // androidx.core.view.NestedScrollingParent3
    public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScrollAccepted(View view, View view2, int i, int i2) {
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        return true;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onStopNestedScroll(View view, int i) {
        this.totalDy = 0;
    }

    public final int requireScreenHeight() {
        if (this.screenHeight == 0) {
            this.screenHeight = CrossListViewUtil.INSTANCE.requireDisplayRealSize(getContext()).y;
        }
        return this.screenHeight;
    }
}
