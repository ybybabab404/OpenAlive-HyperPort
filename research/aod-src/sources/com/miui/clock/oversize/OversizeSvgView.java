package com.miui.clock.oversize;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.FontNeumaticCompressedB;

/* JADX INFO: loaded from: classes.dex */
public class OversizeSvgView extends AppCompatImageView {
    public BaseFontStyle mFontStyle;
    public int mIndex;
    public int mTextColor;
    public OnDrawableChangeListener onDrawableChangeListener;

    public interface OnDrawableChangeListener {
        void onChange(Drawable drawable);
    }

    public OversizeSvgView(Context context) {
        super(context);
        this.mFontStyle = new FontNeumaticCompressedB();
    }

    public OversizeSvgView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFontStyle = new FontNeumaticCompressedB();
    }

    public OversizeSvgView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFontStyle = new FontNeumaticCompressedB();
    }

    public void build() {
        int[] userDefinedResources = this.mFontStyle.getUserDefinedResources();
        if (userDefinedResources == null || userDefinedResources.length == 0) {
            userDefinedResources = this.mFontStyle.getSvgResource();
        }
        Drawable drawable = ContextCompat.getDrawable(getContext(), userDefinedResources[this.mIndex]);
        setColor(drawable, this.mTextColor);
        OnDrawableChangeListener onDrawableChangeListener = this.onDrawableChangeListener;
        if (onDrawableChangeListener != null) {
            onDrawableChangeListener.onChange(drawable);
        }
        setImageDrawable(drawable);
    }

    public final void setColor(Drawable drawable, int i) {
        drawable.setTint(i);
    }

    public OversizeSvgView setFontStyle(BaseFontStyle baseFontStyle) {
        this.mFontStyle = baseFontStyle;
        return this;
    }

    public OversizeSvgView setIndex(int i) {
        this.mIndex = i;
        return this;
    }

    public void setOnDrawableChangeListener(OnDrawableChangeListener onDrawableChangeListener) {
        this.onDrawableChangeListener = onDrawableChangeListener;
    }

    public OversizeSvgView setTextColor(int i) {
        this.mTextColor = i;
        return this;
    }

    public void updateColor(int i) {
        this.mTextColor = i;
        if (getDrawable() != null) {
            setColor(getDrawable(), i);
        }
    }
}
