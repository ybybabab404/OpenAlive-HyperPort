package miuix.internal.view;

import android.content.res.Resources;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.util.Log;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes2.dex */
@RequiresApi
public class CheckWidgetAnimatedStateListDrawable extends AnimatedStateListDrawable {
    public static final String TAG = "miuix.internal.view.CheckWidgetAnimatedStateListDrawable";
    public CheckWidgetConstantState mCheckWidgetConstantState;

    public static class CheckWidgetConstantState extends Drawable.ConstantState {
        public int backGroundColor;
        public int backgroundDisableAlpha;
        public int backgroundNormalAlpha;
        public int blackColor;
        public int grayColor;
        public Drawable.ConstantState mParent;
        public int strokeColor;
        public int strokeDisableAlpha;
        public int strokeNormalAlpha;
        public int strokeStyle;
        public float strokeWidth;
        public boolean touchAnimEnable;

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            Drawable.ConstantState constantState = this.mParent;
            if (constantState == null) {
                return false;
            }
            return constantState.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            Drawable.ConstantState constantState = this.mParent;
            if (constantState == null) {
                return -1;
            }
            return constantState.getChangingConfigurations();
        }

        public Drawable newAnimatedStateListDrawable(Resources resources, Resources.Theme theme, CheckWidgetConstantState checkWidgetConstantState) {
            return new CheckWidgetAnimatedStateListDrawable(resources, theme, checkWidgetConstantState);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            if (this.mParent == null) {
                return null;
            }
            return newAnimatedStateListDrawable(null, null, this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            if (this.mParent == null) {
                return null;
            }
            return newAnimatedStateListDrawable(resources, null, this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            if (this.mParent == null) {
                return null;
            }
            return newAnimatedStateListDrawable(resources, theme, this);
        }
    }

    public CheckWidgetAnimatedStateListDrawable() {
        this.mCheckWidgetConstantState = newCheckWidgetConstantState();
    }

    public CheckWidgetAnimatedStateListDrawable(Resources resources, Resources.Theme theme, CheckWidgetConstantState checkWidgetConstantState) {
        if (checkWidgetConstantState == null) {
            Log.e(TAG, "checkWidgetConstantState is null ,but it can't be null", null);
            return;
        }
        Drawable drawableNewDrawable = resources == null ? checkWidgetConstantState.mParent.newDrawable() : theme == null ? checkWidgetConstantState.mParent.newDrawable(resources) : checkWidgetConstantState.mParent.newDrawable(resources, theme);
        if (drawableNewDrawable != null) {
            checkWidgetConstantState.mParent = drawableNewDrawable.getConstantState();
        }
        setConstantState((DrawableContainer.DrawableContainerState) checkWidgetConstantState.mParent);
        onStateChange(getState());
        jumpToCurrentState();
        CheckWidgetConstantState checkWidgetConstantState2 = this.mCheckWidgetConstantState;
        checkWidgetConstantState2.grayColor = checkWidgetConstantState.grayColor;
        checkWidgetConstantState2.blackColor = checkWidgetConstantState.blackColor;
        checkWidgetConstantState2.backGroundColor = checkWidgetConstantState.backGroundColor;
        checkWidgetConstantState2.touchAnimEnable = checkWidgetConstantState.touchAnimEnable;
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return true;
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mCheckWidgetConstantState;
    }

    public CheckWidgetConstantState newCheckWidgetConstantState() {
        return new CheckWidgetConstantState();
    }

    @Override // android.graphics.drawable.AnimatedStateListDrawable, android.graphics.drawable.StateListDrawable, android.graphics.drawable.DrawableContainer
    public void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (this.mCheckWidgetConstantState == null) {
            this.mCheckWidgetConstantState = newCheckWidgetConstantState();
        }
        this.mCheckWidgetConstantState.mParent = drawableContainerState;
    }
}
