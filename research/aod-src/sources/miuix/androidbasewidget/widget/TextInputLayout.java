package miuix.androidbasewidget.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import miuix.androidbasewidget.R;

/* JADX INFO: loaded from: classes2.dex */
public class TextInputLayout extends LinearLayout {
    public final int ACCESSIBILITY_MODE_ASSERTIVE;
    public final int ACCESSIBILITY_MODE_NONE;
    public final int ACCESSIBILITY_MODE_POLITE;
    public final int PARENT_LAYOUT_PADDING_BOTTOM;
    public final int PARENT_LAYOUT_PADDING_LEFT;
    public final int PARENT_LAYOUT_PADDING_RIGHT;
    public final int PARENT_LAYOUT_PADDING_TOP;
    public android.widget.EditText mEditText;
    public CharSequence mError;
    public final IndicatorViewController mIndicatorViewController;

    public TextInputLayout(Context context) {
        this(context, null, R.attr.miuixTextInputStyle);
    }

    public TextInputLayout(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.miuixTextInputStyle);
    }

    public TextInputLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.PARENT_LAYOUT_PADDING_TOP = 8;
        this.PARENT_LAYOUT_PADDING_BOTTOM = 16;
        this.PARENT_LAYOUT_PADDING_LEFT = 12;
        this.PARENT_LAYOUT_PADDING_RIGHT = 12;
        this.ACCESSIBILITY_MODE_NONE = 0;
        this.ACCESSIBILITY_MODE_POLITE = 1;
        this.ACCESSIBILITY_MODE_ASSERTIVE = 2;
        this.mIndicatorViewController = new IndicatorViewController(this);
        setOrientation(1);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TextInputLayout, i, R.style.Widget_TextInputLayout_DayNight);
        int i2 = R.styleable.TextInputLayout_miuixError;
        this.mError = typedArrayObtainStyledAttributes.hasValue(i2) ? typedArrayObtainStyledAttributes.getText(i2) : null;
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.TextInputLayout_miuixErrorTextAppearance, getErrorTextAppearance());
        int i3 = R.styleable.TextInputLayout_miuixErrorContentDescription;
        CharSequence text = typedArrayObtainStyledAttributes.hasValue(i3) ? typedArrayObtainStyledAttributes.getText(i3) : this.mError;
        int i4 = typedArrayObtainStyledAttributes.getInt(R.styleable.TextInputLayout_miuixErrorAccessibilityLiveRegion, 0);
        int i5 = R.styleable.TextInputLayout_miuixErrorTextColor;
        if (typedArrayObtainStyledAttributes.hasValue(i5)) {
            setErrorTextColor(typedArrayObtainStyledAttributes.getColorStateList(i5));
        }
        typedArrayObtainStyledAttributes.recycle();
        setTextInputLayoutPadding(12, 12, 8, 16);
        setErrorTextAppearance(resourceId);
        setErrorContentDescription(text);
        setErrorAccessibilityLiveRegion(i4);
        setErrorEnabled(false);
    }

    private int getErrorTextAppearance() {
        return this.mIndicatorViewController.getErrorTextAppearance();
    }

    @Nullable
    public android.widget.EditText getEditText() {
        this.mEditText = null;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof android.widget.EditText) {
                this.mEditText = (android.widget.EditText) childAt;
                break;
            }
        }
        return this.mEditText;
    }

    public CharSequence getError() {
        return this.mError;
    }

    public int getErrorAccessibilityLiveRegion() {
        return this.mIndicatorViewController.getErrorAccessibilityLiveRegion();
    }

    public CharSequence getErrorContentDescription() {
        return this.mIndicatorViewController.getErrorContentDescription();
    }

    @ColorInt
    public int getErrorCurrentTextColors() {
        return this.mIndicatorViewController.getErrorViewCurrentTextColor();
    }

    public TextView getErrorView() {
        return this.mIndicatorViewController.getErrorView();
    }

    public void setError(@Nullable CharSequence charSequence) {
        if (!this.mIndicatorViewController.isErrorEnabled()) {
            if (TextUtils.isEmpty(charSequence)) {
                return;
            } else {
                setErrorEnabled(true);
            }
        }
        if (TextUtils.isEmpty(charSequence)) {
            this.mError = null;
            this.mIndicatorViewController.hideError();
            return;
        }
        this.mError = charSequence;
        this.mIndicatorViewController.showError(charSequence);
        android.widget.EditText editText = this.mEditText;
        if (editText != null) {
            Editable text = editText.getText();
            if (text != null) {
                charSequence = ((Object) charSequence) + text.toString();
            }
            setContentDescription(charSequence);
        }
    }

    public void setErrorAccessibilityLiveRegion(int i) {
        this.mIndicatorViewController.setErrorAccessibilityLiveRegion(i);
    }

    public void setErrorContentDescription(@Nullable CharSequence charSequence) {
        this.mIndicatorViewController.setErrorContentDescription(charSequence);
    }

    public void setErrorEnabled(boolean z) {
        this.mIndicatorViewController.setErrorEnabled(z);
    }

    public void setErrorTextAppearance(@StyleRes int i) {
        this.mIndicatorViewController.setErrorTextAppearance(i);
    }

    public void setErrorTextColor(@Nullable ColorStateList colorStateList) {
        this.mIndicatorViewController.setErrorViewTextColor(colorStateList);
    }

    public void setTextAppearanceCompatWithErrorFallback(@NonNull TextView textView, @StyleRes int i) {
        try {
            TextViewCompat.setTextAppearance(textView, i);
            if (textView.getTextColors().getDefaultColor() != -65281) {
                return;
            }
        } catch (Exception unused) {
        }
        TextViewCompat.setTextAppearance(textView, R.style.TextAppearance_AppCompat_Caption);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.design_error));
    }

    public final void setTextInputLayoutPadding(int i, int i2, int i3, int i4) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        setPadding((int) TypedValue.applyDimension(1, i, displayMetrics), (int) TypedValue.applyDimension(1, i3, displayMetrics), (int) TypedValue.applyDimension(1, i2, displayMetrics), (int) TypedValue.applyDimension(1, i4, displayMetrics));
    }
}
