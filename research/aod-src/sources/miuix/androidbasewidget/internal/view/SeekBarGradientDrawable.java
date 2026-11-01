package miuix.androidbasewidget.internal.view;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes2.dex */
@RequiresApi
public class SeekBarGradientDrawable extends GradientDrawable {
    public int mHeight;
    public SeekBarGradientState mSeekBarGradientState;
    public int mWidth;

    public static class SeekBarGradientState extends Drawable.ConstantState {
        public Drawable.ConstantState mParent;

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.mParent.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mParent.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            if (this.mParent == null) {
                return null;
            }
            return newSeekBarGradientDrawable(null, null, this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            if (this.mParent == null) {
                return null;
            }
            return newSeekBarGradientDrawable(resources, null, this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            if (this.mParent == null) {
                return null;
            }
            return newSeekBarGradientDrawable(resources, theme, this);
        }

        public Drawable newSeekBarGradientDrawable(Resources resources, Resources.Theme theme, SeekBarGradientState seekBarGradientState) {
            return new SeekBarGradientDrawable(resources, theme, seekBarGradientState);
        }

        public void setConstantState(Drawable.ConstantState constantState) {
            this.mParent = constantState;
        }
    }

    public SeekBarGradientDrawable(Resources resources, Resources.Theme theme, SeekBarGradientState seekBarGradientState) {
        this.mWidth = -1;
        this.mHeight = -1;
        Drawable drawableNewDrawable = resources == null ? seekBarGradientState.mParent.newDrawable() : theme == null ? seekBarGradientState.mParent.newDrawable(resources) : seekBarGradientState.mParent.newDrawable(resources, theme);
        seekBarGradientState.mParent = drawableNewDrawable.getConstantState();
        SeekBarGradientState seekBarGradientStateNewSeekBarGradientState = newSeekBarGradientState();
        this.mSeekBarGradientState = seekBarGradientStateNewSeekBarGradientState;
        seekBarGradientStateNewSeekBarGradientState.setConstantState(seekBarGradientState.mParent);
        this.mWidth = drawableNewDrawable.getIntrinsicWidth();
        this.mHeight = drawableNewDrawable.getIntrinsicHeight();
        if (drawableNewDrawable instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) drawableNewDrawable;
            setCornerRadius(gradientDrawable.getCornerRadius());
            setShape(gradientDrawable.getShape());
            setColor(gradientDrawable.getColor());
        }
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mSeekBarGradientState;
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mHeight;
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mWidth;
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    public SeekBarGradientState newSeekBarGradientState() {
        return new SeekBarGradientState();
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        boolean zOnStateChange = super.onStateChange(iArr);
        boolean z = false;
        for (int i : iArr) {
            if (i == 16842919) {
                z = true;
            }
        }
        if (z) {
            startPressedAnim();
        }
        if (!z) {
            startUnPressedAnim();
        }
        return zOnStateChange;
    }

    public void startPressedAnim() {
    }

    public void startUnPressedAnim() {
    }
}
