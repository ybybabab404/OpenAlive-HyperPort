package com.miui.aod.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.util.Consumer;
import com.miui.aod.R;
import com.miui.aod.util.ShaderUtils;
import com.miui.keyguard.editor.utils.ViewUtil;

/* JADX INFO: loaded from: classes.dex */
public class HorizontalClockView extends FrameLayout implements IAnimatable {
    public TextView mClockHorizontal;
    public TextView mClockHorizontalDual;
    public TextView mDateDualView;
    public TextView mDateView;
    public GradientLinearLayout mGradientLayout;
    public Animator mHueAnimator;

    public HorizontalClockView(Context context) {
        super(context);
    }

    public HorizontalClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HorizontalClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void clearAnimationView() {
        Animator animator = this.mHueAnimator;
        if (animator != null) {
            animator.cancel();
        }
    }

    public void init(int i, boolean z, int i2) {
        final int i3;
        int i4;
        int i5;
        TextView textView;
        boolean z2 = i2 == 3;
        if (i == 2) {
            i4 = z2 ? R.style.Aod_horizontal_left_s : R.style.Aod_horizontal_s;
            i5 = R.style.Aod_widget_horizontal_s;
            i3 = R.style.Aod_date_s;
        } else {
            i3 = z2 ? R.style.Aod_date_horizontal_left : R.style.Aod_date_horizontal;
            if (z2) {
                i4 = R.style.Aod_horizontal_left;
            } else {
                i4 = z ? R.style.Aod_horizontal_dual : R.style.Aod_horizontal;
            }
            i5 = R.style.Aod_widget_horizontal;
        }
        Typeface typefaceCreateFromAsset = Typeface.createFromAsset(getContext().getAssets(), "fonts/MiClock-Extralight.otf");
        if (i == 4) {
            typefaceCreateFromAsset = Typeface.createFromAsset(getContext().getAssets(), "fonts/MiClock-Light.otf");
        }
        TextView textView2 = (TextView) findViewById(R.id.clock_horizontal);
        this.mClockHorizontal = textView2;
        if (textView2 != null) {
            textView2.setTextAppearance(i5);
            this.mClockHorizontal.setTypeface(typefaceCreateFromAsset);
        }
        TextView textView3 = (TextView) findViewById(R.id.date);
        this.mDateView = textView3;
        ViewUtil.doIfNotNull(textView3, new Consumer() { // from class: com.miui.aod.widget.HorizontalClockView$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((TextView) obj).setTextAppearance(i3);
            }
        });
        TextView textView4 = (TextView) findViewById(R.id.date_lunar);
        ViewUtil.doIfNotNull(textView4, new Consumer() { // from class: com.miui.aod.widget.HorizontalClockView$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((TextView) obj).setTextAppearance(i3);
            }
        });
        if (z && (textView = (TextView) findViewById(R.id.date_lunar_dual)) != null) {
            textView.setTextAppearance(i3);
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(i4, R.styleable.horizontalClock);
        int dimension = (int) typedArrayObtainStyledAttributes.getDimension(0, 0.0f);
        int dimension2 = (int) typedArrayObtainStyledAttributes.getDimension(2, 0.0f);
        int dimension3 = (int) typedArrayObtainStyledAttributes.getDimension(3, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
        ViewUtil.setMargins(this.mDateView, 0, dimension2, 0, dimension3);
        if (z) {
            TextView textView5 = (TextView) findViewById(R.id.clock_horizontal_dual);
            this.mClockHorizontalDual = textView5;
            if (textView5 != null) {
                textView5.setTextAppearance(i5);
                this.mClockHorizontalDual.setTypeface(typefaceCreateFromAsset);
                float textSize = this.mClockHorizontal.getTextSize() * 0.8f;
                this.mClockHorizontal.setTextSize(0, textSize);
                this.mClockHorizontalDual.setTextSize(0, textSize);
            }
            TextView textView6 = (TextView) findViewById(R.id.date_dual);
            this.mDateDualView = textView6;
            if (textView6 != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView6.getLayoutParams();
                if (z2) {
                    layoutParams.setMargins(0, 0, 0, dimension3);
                } else {
                    this.mDateDualView.setTextAppearance(i3);
                }
                this.mDateDualView.setLayoutParams(layoutParams);
            }
        }
        ViewUtil.setMargins(textView4, 0, 0, 0, dimension3);
        TextView textView7 = this.mClockHorizontal;
        if (textView7 != null) {
            textView7.setPadding(0, dimension, 0, 0);
        }
        TextView textView8 = this.mClockHorizontalDual;
        if (textView8 != null) {
            textView8.setPadding(0, dimension, 0, 0);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mGradientLayout = (GradientLinearLayout) findViewById(R.id.gradient_layout);
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void startAnimation(AnimatorListenerAdapter animatorListenerAdapter) {
        this.mHueAnimator = ShaderUtils.hueAnimateIn(animatorListenerAdapter, this.mGradientLayout);
    }
}
