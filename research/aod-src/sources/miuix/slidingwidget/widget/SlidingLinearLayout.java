package miuix.slidingwidget.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.util.HashMap;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes2.dex */
public class SlidingLinearLayout extends LinearLayout {
    public final HashMap preAddViewPairs;
    public final int[] preLayout;
    public final HashMap preRemoveViewPairs;

    /* JADX INFO: renamed from: miuix.slidingwidget.widget.SlidingLinearLayout$1, reason: invalid class name */
    class AnonymousClass1 extends TransitionListener {
        public final /* synthetic */ SlidingLinearLayout this$0;
        public final /* synthetic */ View val$view;

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            this.this$0.removeView(this.val$view);
        }
    }

    /* JADX INFO: renamed from: miuix.slidingwidget.widget.SlidingLinearLayout$2, reason: invalid class name */
    class AnonymousClass2 extends TransitionListener {
        public final /* synthetic */ SlidingLinearLayout this$0;
        public final /* synthetic */ int val$index;

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            if (this.val$index < this.this$0.getChildCount()) {
                try {
                    this.this$0.removeViewAt(this.val$index);
                } catch (Exception e) {
                    Log.e("SlidingLinear", "error in removeViewSlidingAt, index " + this.val$index + " " + e);
                }
            }
        }
    }

    /* JADX INFO: renamed from: miuix.slidingwidget.widget.SlidingLinearLayout$3, reason: invalid class name */
    class AnonymousClass3 extends TransitionListener {
        public final /* synthetic */ SlidingLinearLayout this$0;
        public final /* synthetic */ int val$count;
        public final /* synthetic */ int val$end;
        public final /* synthetic */ int val$start;
        public final /* synthetic */ int val$transientCount;

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            if (this.val$transientCount != this.val$end - 1 || this.val$start + this.val$count > this.this$0.getChildCount()) {
                return;
            }
            try {
                this.this$0.removeViews(this.val$start, this.val$count);
            } catch (Exception e) {
                Log.e("SlidingLinear", "error in removeViewsSliding,start " + this.val$start + " count " + this.val$count + " " + e);
            }
        }
    }

    public SlidingLinearLayout(Context context) {
        this(context, null);
    }

    public SlidingLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.preAddViewPairs = new HashMap();
        this.preRemoveViewPairs = new HashMap();
        this.preLayout = new int[4];
        setLayoutTransition(null);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float fFloatValue;
        float fFloatValue2;
        float fFloatValue3;
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            int childCount = getChildCount();
            char c = 2;
            char c2 = 3;
            char c3 = 1;
            char c4 = 0;
            boolean z2 = getOrientation() != 1 ? Math.abs(this.preLayout[0] - i) > Math.abs(this.preLayout[2] - i3) : Math.abs(this.preLayout[1] - i2) > Math.abs(this.preLayout[3] - i4);
            int i5 = 0;
            while (i5 < childCount) {
                View childAt = getChildAt(i5);
                HashMap map = this.preAddViewPairs;
                float fFloatValue4 = 0.0f;
                if (map == null || map.size() <= 0) {
                    c2 = c2;
                    c3 = c3;
                    i5 = i5;
                } else {
                    Pair pair = (Pair) this.preAddViewPairs.get(childAt);
                    if (pair != null && childAt.getVisibility() != 8) {
                        if ((childAt.getX() != ((Float) pair.first).floatValue() || childAt.getY() != ((Float) pair.second).floatValue()) && !z2) {
                            fFloatValue2 = ((Float) pair.first).floatValue() - childAt.getX();
                            fFloatValue3 = ((Float) pair.second).floatValue() - childAt.getY();
                        } else if (childAt.getX() == ((Float) pair.first).floatValue() && childAt.getY() == ((Float) pair.second).floatValue() && z2) {
                            int[] iArr = this.preLayout;
                            float f = iArr[c4] - i;
                            fFloatValue3 = iArr[c3] - i2;
                            fFloatValue2 = f;
                        } else {
                            fFloatValue2 = 0.0f;
                            fFloatValue3 = 0.0f;
                        }
                        AnimState animState = new AnimState("start");
                        ViewProperty viewProperty = ViewProperty.TRANSLATION_X;
                        AnimState animStateAdd = animState.add(viewProperty, fFloatValue2);
                        ViewProperty viewProperty2 = ViewProperty.TRANSLATION_Y;
                        AnimState animStateAdd2 = animStateAdd.add(viewProperty2, fFloatValue3);
                        Folme.useAt(childAt).state().setTo(animStateAdd2).fromTo(animStateAdd2, new AnimState("end").add(viewProperty, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE).add(viewProperty2, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE), new AnimConfig[0]);
                    }
                    this.preAddViewPairs.remove(childAt);
                }
                HashMap map2 = this.preRemoveViewPairs;
                if (map2 != null && map2.size() > 0) {
                    Pair pair2 = (Pair) this.preRemoveViewPairs.get(childAt);
                    if (pair2 != null && childAt.getVisibility() != 8) {
                        if ((childAt.getX() != ((Float) pair2.first).floatValue() || childAt.getY() != ((Float) pair2.second).floatValue()) && !z2) {
                            fFloatValue4 = ((Float) pair2.first).floatValue() - childAt.getX();
                            fFloatValue = ((Float) pair2.second).floatValue() - childAt.getY();
                        } else if (childAt.getX() == ((Float) pair2.first).floatValue() && childAt.getY() == ((Float) pair2.second).floatValue() && z2) {
                            int[] iArr2 = this.preLayout;
                            fFloatValue4 = iArr2[0] - i;
                            fFloatValue = iArr2[c3] - i2;
                        } else {
                            fFloatValue = 0.0f;
                        }
                        AnimState animState2 = new AnimState("start");
                        ViewProperty viewProperty3 = ViewProperty.TRANSLATION_X;
                        AnimState animStateAdd3 = animState2.add(viewProperty3, fFloatValue4);
                        ViewProperty viewProperty4 = ViewProperty.TRANSLATION_Y;
                        AnimState animStateAdd4 = animStateAdd3.add(viewProperty4, fFloatValue);
                        Folme.useAt(childAt).state().setTo(animStateAdd4).fromTo(animStateAdd4, new AnimState("end").add(viewProperty3, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE).add(viewProperty4, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE), new AnimConfig[0]);
                    }
                    this.preRemoveViewPairs.remove(childAt);
                }
                i5++;
                c = c;
                c2 = c2;
                c3 = c3;
                c4 = 0;
            }
            char c5 = c;
            char c6 = c2;
            char c7 = c3;
            HashMap map3 = this.preAddViewPairs;
            if (map3 != null) {
                map3.clear();
            }
            HashMap map4 = this.preRemoveViewPairs;
            if (map4 != null) {
                map4.clear();
            }
            int[] iArr3 = this.preLayout;
            iArr3[0] = i;
            iArr3[c7] = i2;
            iArr3[c5] = i3;
            iArr3[c6] = i4;
        }
    }
}
