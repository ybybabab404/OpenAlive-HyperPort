package com.miui.aod.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.components.widget.WidgetSwitcher$$ExternalSyntheticLambda0;
import com.miui.aod.util.ViewUtils;
import com.xiaomi.onetrack.util.ac;
import java.util.Objects;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes.dex */
public class SlidingButtonWithMainSubTitle extends LinearLayout {
    public CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;
    public View mRootView;
    public SlidingButton mSlidingButton;
    public TextView mSubTitle;
    public TextView mTitle;

    /* JADX INFO: renamed from: $r8$lambda$uZcoF5ao-96x7_6LAd9aUPF9A34, reason: not valid java name */
    public static /* synthetic */ void m608$r8$lambda$uZcoF5ao96x7_6LAd9aUPF9A34(SlidingButtonWithMainSubTitle slidingButtonWithMainSubTitle, View view) {
        SlidingButton slidingButton = slidingButtonWithMainSubTitle.mSlidingButton;
        slidingButton.setChecked(!slidingButton.isChecked());
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = slidingButtonWithMainSubTitle.mOnCheckedChangeListener;
        if (onCheckedChangeListener != null) {
            SlidingButton slidingButton2 = slidingButtonWithMainSubTitle.mSlidingButton;
            onCheckedChangeListener.onCheckedChanged(slidingButton2, slidingButton2.isChecked());
        }
    }

    public SlidingButtonWithMainSubTitle(Context context) {
        this(context, null);
    }

    public SlidingButtonWithMainSubTitle(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public SlidingButtonWithMainSubTitle(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public boolean getChecked() {
        return this.mSlidingButton.isChecked();
    }

    public final void init() {
        setOrientation(0);
    }

    public void init(String str, String str2, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.mTitle.setText(str);
        if (TextUtils.isEmpty(str2)) {
            this.mSubTitle.setVisibility(8);
        } else {
            this.mSubTitle.setText(str2);
        }
        this.mSlidingButton.setOnPerformCheckedChangeListener(onCheckedChangeListener);
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public void initContentdescription() {
        String string = getContext().getResources().getString(R.string.settings_view_btn_check_on);
        String string2 = getContext().getResources().getString(R.string.settings_view_btn_check_off);
        if (!this.mSlidingButton.isChecked()) {
            string = string2;
        }
        ViewUtils.setAccessibilityForView(this.mRootView, ((Object) this.mTitle.getText()) + ac.b + ((Object) this.mSubTitle.getText()), string);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mRootView = findViewById(R.id.slide_btn_title_root);
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mSubTitle = (TextView) findViewById(R.id.sub_title);
        this.mSlidingButton = (SlidingButton) findViewById(R.id.switch_button);
        View view = this.mRootView;
        String string = getContext().getResources().getString(R.string.settings_view_switch_type);
        SlidingButton slidingButton = this.mSlidingButton;
        Objects.requireNonNull(slidingButton);
        ViewUtils.createAccessibilityDelegateForView(view, string, new WidgetSwitcher$$ExternalSyntheticLambda0(slidingButton));
        this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.widget.SlidingButtonWithMainSubTitle$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SlidingButtonWithMainSubTitle.m608$r8$lambda$uZcoF5ao96x7_6LAd9aUPF9A34(this.f$0, view2);
            }
        });
    }

    public void setChecked(boolean z) {
        this.mSlidingButton.setChecked(z);
        initContentdescription();
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mSlidingButton.setEnabled(z);
    }

    public void setOnCheckChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.mSlidingButton.setOnPerformCheckedChangeListener(onCheckedChangeListener);
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }
}
