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
import com.miui.aod.R;
import com.miui.aod.util.ShaderUtils;

/* JADX INFO: loaded from: classes.dex */
public class VerticalClockView extends FrameLayout implements IAnimatable {
    public GradientLinearLayout mGradientLayout;
    public Animator mHueAnimator;

    public VerticalClockView(Context context) {
        super(context);
    }

    public VerticalClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VerticalClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void clearAnimationView() {
        Animator animator = this.mHueAnimator;
        if (animator != null) {
            animator.cancel();
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mGradientLayout = (GradientLinearLayout) findViewById(R.id.gradient_layout);
    }

    public void setSize(int i, boolean z) {
        int i2;
        int i3;
        TextView textView;
        int i4 = R.style.Aod_date_s;
        if (i == 2) {
            i2 = R.style.Aod_widget_s;
            i3 = R.style.Aod_vertical_s;
        } else if (i == 4) {
            i2 = R.style.Aod_widget_t;
            i3 = R.style.Aod_vertical_t;
        } else {
            i2 = R.style.Aod_widget_vertical;
            i4 = R.style.Aod_date_vertical;
            i3 = R.style.Aod_vertical;
        }
        Typeface typefaceCreateFromAsset = Typeface.createFromAsset(getContext().getAssets(), "fonts/MiClockMono-Extralight.otf");
        if (i == 4) {
            typefaceCreateFromAsset = Typeface.createFromAsset(getContext().getAssets(), "fonts/MiClockMono-Light.otf");
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(i3, R.styleable.verticalClock);
        int dimension = (int) typedArrayObtainStyledAttributes.getDimension(0, 0.0f);
        int dimension2 = (int) typedArrayObtainStyledAttributes.getDimension(2, 0.0f);
        int dimension3 = (int) typedArrayObtainStyledAttributes.getDimension(4, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
        TextView textView2 = (TextView) findViewById(R.id.clock_hour);
        TextView textView3 = (TextView) findViewById(R.id.clock_minute);
        if (textView2 != null) {
            textView2.setTextAppearance(i2);
            textView2.setTypeface(typefaceCreateFromAsset);
            textView2.setPadding(0, dimension, 0, 0);
        }
        if (textView3 != null) {
            textView3.setTextAppearance(i2);
            textView3.setTypeface(typefaceCreateFromAsset);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView3.getLayoutParams();
            layoutParams.setMargins(0, dimension2, 0, 0);
            textView3.setLayoutParams(layoutParams);
        }
        TextView textView4 = (TextView) findViewById(R.id.date);
        if (textView4 != null) {
            textView4.setTypeface(typefaceCreateFromAsset);
            textView4.setTextAppearance(i4);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) textView4.getLayoutParams();
            layoutParams2.setMargins(0, 0, 0, dimension3);
            textView4.setLayoutParams(layoutParams2);
        }
        if (z && (textView = (TextView) findViewById(R.id.date_dual)) != null) {
            textView.setTypeface(typefaceCreateFromAsset);
            textView.setTextAppearance(i4);
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams3.setMargins(0, 0, 0, dimension3);
            textView4.setLayoutParams(layoutParams3);
        }
        TextView textView5 = (TextView) findViewById(R.id.date_lunar);
        if (textView5 != null) {
            textView5.setTypeface(typefaceCreateFromAsset);
            textView5.setTextAppearance(i4);
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) textView5.getLayoutParams();
            layoutParams4.setMargins(0, 0, 0, dimension3);
            textView5.setLayoutParams(layoutParams4);
        }
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void startAnimation(AnimatorListenerAdapter animatorListenerAdapter) {
        this.mHueAnimator = ShaderUtils.hueAnimateIn(animatorListenerAdapter, this.mGradientLayout);
    }
}
