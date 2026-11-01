package miuix.appcompat.internal.view.menu.action;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.appcompat.R;
import miuix.core.util.MiuixUIUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ActionMenuItemViewChildren {
    public int mAntiVerticalTruncatedPadding;
    public int mDensityDpi;
    public ImageView mImageView;
    public LinearLayout mParent;
    public TextView mTextView;
    public boolean mLargerFontEnabled = false;
    public float mPressedAlpha = 0.6f;
    public float mPressedBgAlpha = 1.0f;
    public boolean mUseBackgroundPressEffect = false;

    public ActionMenuItemViewChildren(LinearLayout linearLayout) {
        this.mAntiVerticalTruncatedPadding = 0;
        this.mParent = linearLayout;
        Context context = linearLayout.getContext();
        linearLayout.setOrientation(1);
        linearLayout.setGravity(1);
        LinearLayout.inflate(context, R.layout.miuix_appcompat_action_menu_item_child_layout, linearLayout);
        this.mImageView = (ImageView) linearLayout.findViewById(R.id.action_menu_item_child_icon);
        this.mTextView = (TextView) linearLayout.findViewById(R.id.action_menu_item_child_text);
        this.mImageView.setForceDarkAllowed(false);
        this.mAntiVerticalTruncatedPadding = linearLayout.getContext().getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_small_text_anti_truncated_vertical_padding);
        this.mDensityDpi = context.getResources().getDisplayMetrics().densityDpi;
        updatePressedAlpha();
        linearLayout.post(new Runnable() { // from class: miuix.appcompat.internal.view.menu.action.ActionMenuItemViewChildren.1
            @Override // java.lang.Runnable
            public void run() {
                ActionMenuItemViewChildren.this.setupTouchEffect();
            }
        });
    }

    public boolean adjustTextVerticalTruncated() {
        return this.mTextView != null && isTextVerticallyTruncated();
    }

    public void handleVerticalTextTruncated(Rect rect) {
        if (this.mLargerFontEnabled) {
            this.mTextView.setTextSize(1, 14.0f);
        } else {
            this.mTextView.setTextSize(1, 10.0f);
        }
        TextView textView = this.mTextView;
        int i = rect.left;
        int i2 = rect.top;
        int i3 = this.mAntiVerticalTruncatedPadding;
        textView.setPadding(i, i2 + i3, rect.right, rect.bottom + i3);
    }

    public final boolean isDarkMode() {
        Resources resources = this.mParent.getContext().getResources();
        return (resources == null || resources.getConfiguration() == null || (resources.getConfiguration().uiMode & 48) != 32) ? false : true;
    }

    public final boolean isTextVerticallyTruncated() {
        Layout layout;
        TextView textView = this.mTextView;
        if (textView == null || (layout = textView.getLayout()) == null) {
            return false;
        }
        return layout.getHeight() > (this.mTextView.getMeasuredHeight() - this.mTextView.getPaddingTop()) - this.mTextView.getPaddingBottom();
    }

    public void onConfigurationChanged(Configuration configuration) {
        int i = configuration.densityDpi;
        if (i != this.mDensityDpi) {
            this.mDensityDpi = i;
            int iDp2px = MiuixUIUtils.dp2px(this.mImageView.getContext(), 28.0f);
            this.mImageView.setLayoutParams(new LinearLayout.LayoutParams(iDp2px, iDp2px));
            setLargeFontEnabled(this.mLargerFontEnabled);
        }
        if ((configuration.uiMode & 48) != (this.mParent.getResources().getConfiguration().uiMode & 48)) {
            updatePressedAlpha();
            setupTouchEffect();
        }
    }

    public void resetTextSizeAndPaddings(Rect rect) {
        if (this.mLargerFontEnabled) {
            this.mTextView.setTextSize(1, 16.0f);
        } else {
            this.mTextView.setTextSize(1, 11.0f);
        }
        if (rect != null) {
            this.mTextView.setPadding(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    public void setContentDescription(CharSequence charSequence) {
        if (charSequence == null || TextUtils.isEmpty(charSequence)) {
            this.mParent.setContentDescription(this.mTextView.getText());
        } else {
            this.mParent.setContentDescription(charSequence);
        }
    }

    public void setEnabled(boolean z) {
        this.mImageView.setEnabled(z);
        this.mTextView.setEnabled(z);
    }

    public void setIcon(Drawable drawable) {
        if (this.mImageView.getDrawable() != drawable) {
            this.mImageView.setImageDrawable(drawable);
        }
    }

    public void setLargeFontEnabled(boolean z) {
        this.mLargerFontEnabled = z;
        if (z) {
            this.mTextView.setTextSize(1, 16.0f);
        } else {
            this.mTextView.setTextSize(1, 11.0f);
        }
    }

    public void setText(CharSequence charSequence) {
        this.mTextView.setText(charSequence);
    }

    public void setUseBackgroundPressEffect(boolean z) {
        if (this.mUseBackgroundPressEffect != z) {
            this.mUseBackgroundPressEffect = z;
            this.mParent.post(new Runnable() { // from class: miuix.appcompat.internal.view.menu.action.ActionMenuItemViewChildren.2
                @Override // java.lang.Runnable
                public void run() {
                    ActionMenuItemViewChildren.this.setupTouchEffect();
                }
            });
        }
    }

    public final void setupTouchEffect() {
        if (this.mUseBackgroundPressEffect) {
            Folme.use((View) this.mParent).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).clearTintColor().setAlpha(isDarkMode() ? 0.5f : 0.6f, ITouchStyle.TouchType.DOWN).setAlpha(1.0f, ITouchStyle.TouchType.UP).handleTouchOf(this.mParent, new AnimConfig[0]);
            Folme.use((View) this.mParent).hover().setAlpha(1.0f, new IHoverStyle.HoverType[0]).setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(this.mParent, new AnimConfig[0]);
        } else {
            Folme.use((View) this.mParent).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setAlpha(this.mPressedAlpha, ITouchStyle.TouchType.DOWN).setAlpha(1.0f, ITouchStyle.TouchType.UP).clearTintColor().handleTouchOf(this.mParent, new AnimConfig[0]);
            Folme.use((View) this.mParent).hover().setAlpha(1.0f, new IHoverStyle.HoverType[0]).setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(this.mParent, new AnimConfig[0]);
        }
    }

    public final void updatePressedAlpha() {
        boolean zIsDarkMode = isDarkMode();
        this.mPressedAlpha = zIsDarkMode ? 0.53f : 0.6f;
        this.mPressedBgAlpha = zIsDarkMode ? 0.12f : 1.0f;
    }

    public void updateTouchRadius() {
    }
}
