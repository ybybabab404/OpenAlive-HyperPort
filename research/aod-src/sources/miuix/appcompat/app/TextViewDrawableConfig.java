package miuix.appcompat.app;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

/* JADX INFO: loaded from: classes2.dex */
public class TextViewDrawableConfig {
    public Drawable mDrawableBottom;
    public int mDrawableBottomRes;
    public Drawable mDrawableEnd;
    public int mDrawableEndRes;
    public Drawable mDrawableStart;
    public int mDrawableStartRes;
    public Drawable mDrawableTop;
    public int mDrawableTopRes;

    public static class Builder {
    }

    public boolean isDrawableResAlreadySet() {
        return this.mDrawableStartRes > 0 || this.mDrawableTopRes > 0 || this.mDrawableEndRes > 0 || this.mDrawableBottomRes > 0;
    }

    public void setTextViewDrawable(TextView textView) {
        if (isDrawableResAlreadySet()) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(this.mDrawableStartRes, this.mDrawableTopRes, this.mDrawableEndRes, this.mDrawableBottomRes);
        } else {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(this.mDrawableStart, this.mDrawableTop, this.mDrawableEnd, this.mDrawableBottom);
        }
    }
}
