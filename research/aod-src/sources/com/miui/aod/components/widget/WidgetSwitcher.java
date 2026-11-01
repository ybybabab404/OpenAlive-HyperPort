package com.miui.aod.components.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.miui.aod.R;
import com.miui.aod.util.ViewUtils;
import java.util.Objects;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes.dex */
public class WidgetSwitcher extends FrameLayout {
    private SlidingButton mBtn;
    private View mContainer;
    private TextView mTitle;

    /* JADX INFO: renamed from: $r8$lambda$CtL7qMHuRumW7-RvW9xoesx0rzI, reason: not valid java name */
    public static /* synthetic */ void m303$r8$lambda$CtL7qMHuRumW7RvW9xoesx0rzI(WidgetSwitcher widgetSwitcher, View view) {
        SlidingButton slidingButton = widgetSwitcher.mBtn;
        slidingButton.setChecked(!slidingButton.isChecked());
    }

    public WidgetSwitcher(Context context) {
        this(context, null);
    }

    public WidgetSwitcher(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WidgetSwitcher(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.widget_switcher_layout, this);
    }

    public void initContentDescrition() {
        String string = getContext().getResources().getString(R.string.settings_view_btn_check_on);
        String string2 = getContext().getResources().getString(R.string.settings_view_btn_check_off);
        if (!this.mBtn.isChecked()) {
            string = string2;
        }
        ViewUtils.setAccessibilityForView(this.mContainer, this.mTitle.getText().toString(), string);
    }

    public boolean isChecked() {
        return this.mBtn.isChecked();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mContainer = findViewById(R.id.slide_btn_container);
        this.mTitle = (TextView) findViewById(R.id.slide_btn_title);
        this.mBtn = (SlidingButton) findViewById(R.id.slide_btn);
        View view = this.mContainer;
        String string = getContext().getResources().getString(R.string.settings_view_switch_type);
        SlidingButton slidingButton = this.mBtn;
        Objects.requireNonNull(slidingButton);
        ViewUtils.createAccessibilityDelegateForView(view, string, new WidgetSwitcher$$ExternalSyntheticLambda0(slidingButton));
        initContentDescrition();
        this.mContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.components.widget.WidgetSwitcher$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                WidgetSwitcher.m303$r8$lambda$CtL7qMHuRumW7RvW9xoesx0rzI(this.f$0, view2);
            }
        });
    }

    public void setChecked(boolean z) {
        this.mBtn.setChecked(z);
        initContentDescrition();
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mBtn.setEnabled(z);
        this.mTitle.setEnabled(z);
        this.mTitle.setAlpha(z ? 1.0f : 0.6f);
    }

    public void setOnCheckedChangeListener(@Nullable CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.mBtn.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public void setTitle(String str, Drawable drawable) {
        TextView textView = this.mTitle;
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        textView.setText(str);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            this.mTitle.setCompoundDrawables(drawable, null, null, null);
        }
        initContentDescrition();
    }
}
