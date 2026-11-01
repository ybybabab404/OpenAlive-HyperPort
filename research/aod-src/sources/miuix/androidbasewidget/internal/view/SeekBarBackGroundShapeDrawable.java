package miuix.androidbasewidget.internal.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.RequiresApi;
import miuix.animation.physics.DynamicAnimation;
import miuix.animation.physics.SpringAnimation;
import miuix.animation.property.FloatProperty;

/* JADX INFO: loaded from: classes2.dex */
@RequiresApi
public class SeekBarBackGroundShapeDrawable extends SeekBarGradientDrawable {
    public float mBlackAlpha;
    public FloatProperty mBlackAlphaFloatProperty;
    public GradientDrawable mMaskDrawable;
    public SpringAnimation mPressedBlackAnim;
    public SpringAnimation mUnPressedBlackAnim;

    public static class SeekBarBackGroundShapeDrawableState extends SeekBarGradientDrawable.SeekBarGradientState {
        @Override // miuix.androidbasewidget.internal.view.SeekBarGradientDrawable.SeekBarGradientState
        public Drawable newSeekBarGradientDrawable(Resources resources, Resources.Theme theme, SeekBarGradientDrawable.SeekBarGradientState seekBarGradientState) {
            return new SeekBarBackGroundShapeDrawable(resources, theme, seekBarGradientState);
        }
    }

    public SeekBarBackGroundShapeDrawable(Resources resources, Resources.Theme theme, SeekBarGradientDrawable.SeekBarGradientState seekBarGradientState) {
        super(resources, theme, seekBarGradientState);
        this.mBlackAlpha = 0.0f;
        this.mBlackAlphaFloatProperty = new FloatProperty<SeekBarBackGroundShapeDrawable>("BlackAlpha") { // from class: miuix.androidbasewidget.internal.view.SeekBarBackGroundShapeDrawable.1
            @Override // miuix.animation.property.FloatProperty
            public float getValue(SeekBarBackGroundShapeDrawable seekBarBackGroundShapeDrawable) {
                return seekBarBackGroundShapeDrawable.getBlackAlpha();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(SeekBarBackGroundShapeDrawable seekBarBackGroundShapeDrawable, float f) {
                seekBarBackGroundShapeDrawable.setBlackAlpha(f);
            }
        };
        initAnim();
        initMaskDrawable();
    }

    private void initAnim() {
        SpringAnimation springAnimation = new SpringAnimation(this, this.mBlackAlphaFloatProperty, 0.05f);
        this.mPressedBlackAnim = springAnimation;
        springAnimation.getSpring().setStiffness(986.96f);
        this.mPressedBlackAnim.getSpring().setDampingRatio(0.99f);
        this.mPressedBlackAnim.setMinimumVisibleChange(0.00390625f);
        this.mPressedBlackAnim.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: miuix.androidbasewidget.internal.view.SeekBarBackGroundShapeDrawable$$ExternalSyntheticLambda0
            @Override // miuix.animation.physics.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                this.f$0.invalidateSelf();
            }
        });
        SpringAnimation springAnimation2 = new SpringAnimation(this, this.mBlackAlphaFloatProperty, 0.0f);
        this.mUnPressedBlackAnim = springAnimation2;
        springAnimation2.getSpring().setStiffness(986.96f);
        this.mUnPressedBlackAnim.getSpring().setDampingRatio(0.99f);
        this.mUnPressedBlackAnim.setMinimumVisibleChange(0.00390625f);
        this.mUnPressedBlackAnim.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: miuix.androidbasewidget.internal.view.SeekBarBackGroundShapeDrawable.2
            @Override // miuix.animation.physics.DynamicAnimation.OnAnimationUpdateListener
            public void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                SeekBarBackGroundShapeDrawable.this.invalidateSelf();
            }
        });
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawMask(canvas);
    }

    public final void drawMask(Canvas canvas) {
        this.mMaskDrawable.setBounds(getBounds());
        this.mMaskDrawable.setAlpha((int) (this.mBlackAlpha * 255.0f));
        this.mMaskDrawable.setCornerRadius(getCornerRadius());
        this.mMaskDrawable.draw(canvas);
    }

    public float getBlackAlpha() {
        return this.mBlackAlpha;
    }

    public final void initMaskDrawable() {
        GradientDrawable gradientDrawable = new GradientDrawable(getOrientation(), getColors());
        this.mMaskDrawable = gradientDrawable;
        gradientDrawable.setCornerRadius(getCornerRadius());
        this.mMaskDrawable.setShape(getShape());
        this.mMaskDrawable.setColor(-16777216);
    }

    @Override // miuix.androidbasewidget.internal.view.SeekBarGradientDrawable
    public SeekBarGradientDrawable.SeekBarGradientState newSeekBarGradientState() {
        return new SeekBarBackGroundShapeDrawableState();
    }

    public void setBlackAlpha(float f) {
        this.mBlackAlpha = f;
    }

    @Override // miuix.androidbasewidget.internal.view.SeekBarGradientDrawable
    public void startPressedAnim() {
        this.mUnPressedBlackAnim.cancel();
        this.mPressedBlackAnim.start();
    }

    @Override // miuix.androidbasewidget.internal.view.SeekBarGradientDrawable
    public void startUnPressedAnim() {
        this.mPressedBlackAnim.cancel();
        this.mUnPressedBlackAnim.start();
    }
}
