package miuix.miuixbasewidget.widget;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public class SplashScreenViewGroup extends ViewGroup {
    public int mIconSize;
    public RoundCornerImageView mIconView;

    public static class SplashScreenOutlineProvider extends ViewOutlineProvider {
        public float radius;

        public SplashScreenOutlineProvider(float f) {
            this.radius = f;
        }

        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            Path path = new Path();
            RectF rectF = new RectF(0.0f, 0.0f, view.getWidth(), view.getHeight());
            float f = this.radius;
            path.addRoundRect(rectF, new float[]{f, f, f, f, f, f, f, f}, Path.Direction.CW);
            outline.setAlpha(0.0f);
            outline.setConvexPath(path);
        }
    }

    public SplashScreenViewGroup(Context context) {
        this(context, null);
    }

    public SplashScreenViewGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SplashScreenViewGroup(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SplashScreenViewGroup(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIconSize = 96;
        init(context);
    }

    public static int dp2px(Context context, float f) {
        return (int) ((f * (context.getResources().getConfiguration().densityDpi / 160.0f)) + 0.5f);
    }

    public final void clipCornerRadius(Context context) {
        float fDp2px = dp2px(context, 24.0f);
        this.mIconView.setCornerRadius(fDp2px);
        this.mIconView.setCornerRadiusEnabled(false);
        this.mIconView.setOutlineProvider(new SplashScreenOutlineProvider(fDp2px));
        this.mIconView.setClipToOutline(true);
    }

    public final void init(Context context) {
        this.mIconView = new RoundCornerImageView(context);
        int iDp2px = dp2px(context, this.mIconSize);
        this.mIconSize = iDp2px;
        addView(this.mIconView, iDp2px, iDp2px);
        clipCornerRadius(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (getChildCount() != 1) {
            return;
        }
        View childAt = getChildAt(0);
        int measuredWidth = childAt.getMeasuredWidth();
        int measuredHeight = childAt.getMeasuredHeight();
        int measuredWidth2 = (getMeasuredWidth() - measuredWidth) / 2;
        int measuredHeight2 = (int) ((getMeasuredHeight() - measuredHeight) * 0.48f);
        childAt.layout(measuredWidth2, measuredHeight2, measuredWidth + measuredWidth2, measuredHeight + measuredHeight2);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mIconSize, 1073741824);
        this.mIconView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
    }

    public void setIcon(@DrawableRes int i) {
        setIcon(i, false);
    }

    public void setIcon(@DrawableRes int i, boolean z) {
        RoundCornerImageView roundCornerImageView = this.mIconView;
        if (roundCornerImageView != null) {
            roundCornerImageView.setNeedStroke(z);
            this.mIconView.setImageDrawable(i);
        }
    }

    public void setIcon(@Nullable Drawable drawable) {
        setIcon(drawable, false);
    }

    public void setIcon(@Nullable Drawable drawable, boolean z) {
        RoundCornerImageView roundCornerImageView = this.mIconView;
        if (roundCornerImageView != null) {
            roundCornerImageView.setNeedStroke(z);
            this.mIconView.setImageDrawable(drawable);
        }
    }
}
