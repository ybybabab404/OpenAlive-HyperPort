package miuix.androidbasewidget.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import miuix.androidbasewidget.R;

/* JADX INFO: loaded from: classes2.dex */
public final class IndicatorViewController {
    public FrameLayout mCaptionArea;
    public int mCaptionDisplayed;
    public int mCaptionToShow;
    public final Context mContext;
    public CharSequence mError;
    public boolean mErrorEnabled;
    public TextView mErrorView;
    public int mErrorViewAccessibilityLiveRegion;
    public CharSequence mErrorViewContentDescription;
    public LinearLayout mIndicatorArea;
    public int mIndicatorsAdded;
    public final TextInputLayout mTextInputView;
    public final int INDICATOR_AREA_MARGIN_LEFT = 8;
    public ColorStateList mErrorViewTextColor = initErrorColor(R.attr.miuixTextInputLayoutErrorColor);
    public int mErrorTextAppearance = initErrorAppearance(R.attr.miuixTextInputLayoutErrorStyle);

    @Retention(RetentionPolicy.SOURCE)
    public @interface IndicatorIndex {
    }

    public IndicatorViewController(TextInputLayout textInputLayout) {
        this.mContext = textInputLayout.getContext();
        this.mTextInputView = textInputLayout;
    }

    public void addIndicator(TextView textView, int i) {
        if (this.mIndicatorArea == null && this.mCaptionArea == null) {
            LinearLayout linearLayout = new LinearLayout(this.mContext);
            this.mIndicatorArea = linearLayout;
            linearLayout.setOrientation(0);
            this.mTextInputView.addView(this.mIndicatorArea, -2, -2);
            setIndicatorAreaMarginLeft(8);
            this.mCaptionArea = new FrameLayout(this.mContext);
            this.mIndicatorArea.addView(this.mCaptionArea, new LinearLayout.LayoutParams(0, -2, 1.0f));
        }
        if (isCaptionView(i)) {
            this.mCaptionArea.setVisibility(0);
            this.mCaptionArea.addView(textView);
        } else {
            this.mIndicatorArea.addView(textView, new LinearLayout.LayoutParams(-2, -2));
        }
        this.mIndicatorArea.setVisibility(0);
        this.mIndicatorsAdded++;
    }

    public final TextView getCaptionViewFromDisplayState(int i) {
        if (i != 1) {
            return null;
        }
        return this.mErrorView;
    }

    public int getErrorAccessibilityLiveRegion() {
        return this.mErrorViewAccessibilityLiveRegion;
    }

    public CharSequence getErrorContentDescription() {
        return this.mErrorViewContentDescription;
    }

    public int getErrorTextAppearance() {
        return this.mErrorTextAppearance;
    }

    public TextView getErrorView() {
        return this.mErrorView;
    }

    public int getErrorViewCurrentTextColor() {
        TextView textView = this.mErrorView;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    public void hideError() {
        int i = this.mCaptionDisplayed;
        if (i == 1) {
            this.mCaptionToShow = 0;
        }
        updateCaptionViewsVisibility(i, this.mCaptionToShow);
    }

    public final int initErrorAppearance(int i) {
        TypedValue typedValue = new TypedValue();
        return this.mContext.getTheme().resolveAttribute(i, typedValue, true) ? typedValue.resourceId : R.style.Widget_TextInputLayout_Error_DayNight;
    }

    public final ColorStateList initErrorColor(int i) {
        TypedValue typedValue = new TypedValue();
        return ContextCompat.getColorStateList(this.mContext, this.mContext.getTheme().resolveAttribute(i, typedValue, true) ? typedValue.resourceId : R.color.miuix_color_red_light_level1);
    }

    public boolean isCaptionView(int i) {
        return i == 0;
    }

    public boolean isErrorEnabled() {
        return this.mErrorEnabled;
    }

    public void removeIndicator(TextView textView, int i) {
        FrameLayout frameLayout;
        if (this.mIndicatorArea == null) {
            return;
        }
        if (!isCaptionView(i) || (frameLayout = this.mCaptionArea) == null) {
            this.mIndicatorArea.removeView(textView);
        } else {
            frameLayout.removeView(textView);
        }
        int i2 = this.mIndicatorsAdded - 1;
        this.mIndicatorsAdded = i2;
        setViewGroupGoneIfEmpty(this.mIndicatorArea, i2);
    }

    public final void setCaptionViewVisibilities(int i, int i2) {
        TextView captionViewFromDisplayState;
        TextView captionViewFromDisplayState2;
        if (i == i2) {
            return;
        }
        if (i2 != 0 && (captionViewFromDisplayState2 = getCaptionViewFromDisplayState(i2)) != null) {
            captionViewFromDisplayState2.setVisibility(0);
            captionViewFromDisplayState2.setAlpha(1.0f);
        }
        if (i != 0 && (captionViewFromDisplayState = getCaptionViewFromDisplayState(i)) != null) {
            captionViewFromDisplayState.setVisibility(4);
            if (i == 1) {
                captionViewFromDisplayState.setText((CharSequence) null);
            }
        }
        this.mCaptionDisplayed = i2;
    }

    public void setErrorAccessibilityLiveRegion(int i) {
        this.mErrorViewAccessibilityLiveRegion = i;
        TextView textView = this.mErrorView;
        if (textView != null) {
            textView.setAccessibilityLiveRegion(i);
        }
    }

    public void setErrorContentDescription(CharSequence charSequence) {
        this.mErrorViewContentDescription = charSequence;
        TextView textView = this.mErrorView;
        if (textView != null) {
            textView.setContentDescription(charSequence);
        }
    }

    public void setErrorEnabled(boolean z) {
        if (this.mErrorEnabled == z) {
            return;
        }
        if (z) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(this.mContext);
            this.mErrorView = appCompatTextView;
            appCompatTextView.setId(R.id.miuix_textinput_error);
            this.mErrorView.setTextAlignment(5);
            setErrorTextAppearance(this.mErrorTextAppearance);
            setErrorViewTextColor(this.mErrorViewTextColor);
            setErrorContentDescription(this.mErrorViewContentDescription);
            setErrorAccessibilityLiveRegion(this.mErrorViewAccessibilityLiveRegion);
            this.mErrorView.setVisibility(4);
            addIndicator(this.mErrorView, 0);
        } else {
            hideError();
            removeIndicator(this.mErrorView, 0);
            this.mErrorView = null;
        }
        this.mErrorEnabled = z;
    }

    public void setErrorTextAppearance(int i) {
        this.mErrorTextAppearance = i;
        TextView textView = this.mErrorView;
        if (textView != null) {
            this.mTextInputView.setTextAppearanceCompatWithErrorFallback(textView, i);
        }
    }

    public void setErrorViewTextColor(ColorStateList colorStateList) {
        this.mErrorViewTextColor = colorStateList;
        TextView textView = this.mErrorView;
        if (textView == null || colorStateList == null) {
            return;
        }
        textView.setTextColor(colorStateList);
    }

    public final void setIndicatorAreaMarginLeft(int i) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIndicatorArea.getLayoutParams();
        layoutParams.setMargins((int) TypedValue.applyDimension(1, i, this.mContext.getResources().getDisplayMetrics()), 0, 0, 0);
        this.mIndicatorArea.setLayoutParams(layoutParams);
    }

    public final void setViewGroupGoneIfEmpty(ViewGroup viewGroup, int i) {
        if (i == 0) {
            viewGroup.setVisibility(8);
        }
    }

    public void showError(CharSequence charSequence) {
        this.mError = charSequence;
        this.mErrorView.setText(charSequence);
        int i = this.mCaptionDisplayed;
        if (i != 1) {
            this.mCaptionToShow = 1;
        }
        updateCaptionViewsVisibility(i, this.mCaptionToShow);
        this.mErrorView.announceForAccessibility(charSequence);
    }

    public final void updateCaptionViewsVisibility(int i, int i2) {
        if (i == i2) {
            return;
        }
        setCaptionViewVisibilities(i, i2);
    }
}
