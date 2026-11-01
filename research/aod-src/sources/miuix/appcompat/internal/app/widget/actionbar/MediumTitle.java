package miuix.appcompat.internal.app.widget.actionbar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.LinearLayout;
import miuix.appcompat.R;
import miuix.appcompat.internal.view.ColorTransitionTextView;
import miuix.internal.util.AttributeResolver;

/* JADX INFO: loaded from: classes2.dex */
public class MediumTitle {
    public Context mContext;
    public ColorTransitionTextView mMediumSubtitleView;
    public LinearLayout mMediumTitleLayout;
    public ColorTransitionTextView mMediumTitleView;
    public boolean mVisible = true;
    public boolean mSubtitleSizeable = true;
    public boolean mTextColorTransitEnable = false;

    /* JADX INFO: renamed from: $r8$lambda$WM1Huo_i_t-ObhGlRL9JbUhESzo, reason: not valid java name */
    public static /* synthetic */ void m1322$r8$lambda$WM1Huo_i_tObhGlRL9JbUhESzo(MediumTitle mediumTitle) {
        mediumTitle.getClass();
        mediumTitle.mMediumTitleLayout.setTouchDelegate(new TouchDelegate(new Rect(0, 0, mediumTitle.mMediumTitleLayout.getWidth(), mediumTitle.mMediumTitleLayout.getHeight()), mediumTitle.mMediumTitleView));
    }

    public MediumTitle(Context context, int i, int i2) {
        this.mContext = context;
    }

    public final void applyTitleSizeForSubtitlePresence() {
        ColorTransitionTextView colorTransitionTextView;
        if (this.mMediumTitleView == null || (colorTransitionTextView = this.mMediumSubtitleView) == null) {
            return;
        }
        this.mMediumTitleView.setTextSize(0, this.mContext.getResources().getDimension(!TextUtils.isEmpty(colorTransitionTextView.getText()) ? R.dimen.miuix_appcompat_medium_title_text_size_with_subtitle : R.dimen.miuix_appcompat_medium_title_text_size));
    }

    public final LinearLayout.LayoutParams getChildLayoutParams() {
        return new LinearLayout.LayoutParams(-2, -2);
    }

    public View getLayout() {
        return this.mMediumTitleLayout;
    }

    public int getVisibility() {
        return this.mMediumTitleLayout.getVisibility();
    }

    public void init() {
        Resources resources = this.mContext.getResources();
        LinearLayout linearLayout = new LinearLayout(this.mContext);
        this.mMediumTitleLayout = linearLayout;
        linearLayout.setImportantForAccessibility(2);
        this.mMediumTitleLayout.setOrientation(1);
        this.mMediumTitleLayout.setGravity(8388627);
        ColorTransitionTextView colorTransitionTextView = new ColorTransitionTextView(this.mContext, null, R.attr.mediumTitleTheme);
        this.mMediumTitleView = colorTransitionTextView;
        colorTransitionTextView.setVerticalScrollBarEnabled(false);
        this.mMediumTitleView.setHorizontalScrollBarEnabled(false);
        this.mMediumTitleView.setFocusableInTouchMode(false);
        this.mMediumTitleView.setGravity(8388627);
        this.mMediumTitleView.setSingleLine(true);
        this.mMediumTitleView.setMaxLines(1);
        ColorTransitionTextView colorTransitionTextView2 = new ColorTransitionTextView(this.mContext, null, R.attr.mediumSubtitleTheme);
        this.mMediumSubtitleView = colorTransitionTextView2;
        colorTransitionTextView2.setVerticalScrollBarEnabled(false);
        this.mMediumSubtitleView.setHorizontalScrollBarEnabled(false);
        this.mMediumSubtitleView.setGravity(8388627);
        this.mMediumTitleLayout.post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.actionbar.MediumTitle$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MediumTitle mediumTitle = this.f$0;
                mediumTitle.mMediumTitleLayout.setBackground(AttributeResolver.resolveDrawable(mediumTitle.mContext, android.R.attr.actionBarItemBackground));
            }
        });
        this.mMediumTitleView.setId(R.id.action_bar_title_medium);
        this.mMediumTitleLayout.addView(this.mMediumTitleView, getChildLayoutParams());
        this.mMediumSubtitleView.setId(R.id.action_bar_subtitle_medium);
        this.mMediumSubtitleView.setVisibility(8);
        this.mMediumTitleLayout.addView(this.mMediumSubtitleView, getChildLayoutParams());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mMediumSubtitleView.getLayoutParams();
        layoutParams.topMargin = resources.getDimensionPixelOffset(R.dimen.action_bar_subtitle_top_margin);
        layoutParams.bottomMargin = resources.getDimensionPixelOffset(R.dimen.action_bar_subtitle_bottom_margin);
        applyTitleSizeForSubtitlePresence();
    }

    public void onConfigurationChanged(Configuration configuration) {
        applyTitleSizeForSubtitlePresence();
    }

    public void setEnabled(boolean z) {
        this.mMediumTitleLayout.setEnabled(z);
    }

    public void setOnClickListener(View.OnClickListener onClickListener, boolean z) {
        this.mMediumTitleView.setOnClickListener(onClickListener);
        this.mMediumTitleView.post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.actionbar.MediumTitle$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MediumTitle.m1322$r8$lambda$WM1Huo_i_tObhGlRL9JbUhESzo(this.f$0);
            }
        });
        this.mMediumTitleView.setClickable(z);
    }

    public void setSubTitle(CharSequence charSequence) {
        this.mMediumSubtitleView.setText(charSequence);
        setSubTitleVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        applyTitleSizeForSubtitlePresence();
    }

    public void setSubTitleClickable(boolean z) {
        ColorTransitionTextView colorTransitionTextView = this.mMediumSubtitleView;
        if (colorTransitionTextView != null) {
            colorTransitionTextView.setClickable(z);
        }
    }

    public void setSubTitleOnClickListener(View.OnClickListener onClickListener, boolean z) {
        ColorTransitionTextView colorTransitionTextView = this.mMediumSubtitleView;
        if (colorTransitionTextView != null) {
            colorTransitionTextView.setOnClickListener(onClickListener);
            this.mMediumSubtitleView.setClickable(z);
        }
    }

    public void setSubTitleVisibility(int i) {
        this.mMediumSubtitleView.setVisibility(i);
    }

    public void setTextColorTransitEnable(boolean z, int i) {
        if (this.mTextColorTransitEnable != z) {
            this.mTextColorTransitEnable = z;
            ColorTransitionTextView colorTransitionTextView = this.mMediumTitleView;
            if (colorTransitionTextView != null) {
                colorTransitionTextView.startColorTransition(z, false);
            }
            ColorTransitionTextView colorTransitionTextView2 = this.mMediumSubtitleView;
            if (colorTransitionTextView2 != null) {
                colorTransitionTextView2.startColorTransition(z, false);
            }
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (!TextUtils.equals(charSequence, this.mMediumTitleView.getText())) {
            this.mMediumTitleView.setText(charSequence);
            setEnabled(!TextUtils.isEmpty(charSequence));
            if (!TextUtils.isEmpty(charSequence)) {
                this.mMediumTitleView.setVisibility(0);
                this.mMediumTitleLayout.setVisibility(0);
            }
        }
        applyTitleSizeForSubtitlePresence();
    }

    public void setTitleClickable(boolean z) {
        ColorTransitionTextView colorTransitionTextView = this.mMediumTitleView;
        if (colorTransitionTextView != null) {
            colorTransitionTextView.setClickable(z);
        }
    }

    public void setTitleVisibility(int i) {
        this.mMediumTitleView.setVisibility(i);
    }

    public void setVisibility(int i) {
        if (this.mVisible || i != 0) {
            this.mMediumTitleLayout.setVisibility(i);
        } else {
            this.mMediumTitleLayout.setVisibility(4);
        }
    }

    public void setVisible(boolean z) {
        if (this.mVisible != z) {
            this.mVisible = z;
            this.mMediumTitleLayout.setVisibility(z ? 0 : 4);
        }
    }
}
