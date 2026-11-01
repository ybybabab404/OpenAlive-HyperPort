package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PolystarContent implements PathContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent {
    public final boolean hidden;
    public final BaseKeyframeAnimation innerRadiusAnimation;
    public final BaseKeyframeAnimation innerRoundednessAnimation;
    public boolean isPathValid;
    public final boolean isReversed;
    public final LottieDrawable lottieDrawable;
    public final String name;
    public final BaseKeyframeAnimation outerRadiusAnimation;
    public final BaseKeyframeAnimation outerRoundednessAnimation;
    public final BaseKeyframeAnimation pointsAnimation;
    public final BaseKeyframeAnimation positionAnimation;
    public final BaseKeyframeAnimation rotationAnimation;
    public final PolystarShape.Type type;
    public final Path path = new Path();
    public final CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();

    /* JADX INFO: renamed from: com.airbnb.lottie.animation.content.PolystarContent$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type;

        static {
            int[] iArr = new int[PolystarShape.Type.values().length];
            $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type = iArr;
            try {
                iArr[PolystarShape.Type.STAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type[PolystarShape.Type.POLYGON.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public PolystarContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, PolystarShape polystarShape) {
        this.lottieDrawable = lottieDrawable;
        this.name = polystarShape.getName();
        PolystarShape.Type type = polystarShape.getType();
        this.type = type;
        this.hidden = polystarShape.isHidden();
        this.isReversed = polystarShape.isReversed();
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation = polystarShape.getPoints().createAnimation();
        this.pointsAnimation = baseKeyframeAnimationCreateAnimation;
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation2 = polystarShape.getPosition().createAnimation();
        this.positionAnimation = baseKeyframeAnimationCreateAnimation2;
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation3 = polystarShape.getRotation().createAnimation();
        this.rotationAnimation = baseKeyframeAnimationCreateAnimation3;
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation4 = polystarShape.getOuterRadius().createAnimation();
        this.outerRadiusAnimation = baseKeyframeAnimationCreateAnimation4;
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation5 = polystarShape.getOuterRoundedness().createAnimation();
        this.outerRoundednessAnimation = baseKeyframeAnimationCreateAnimation5;
        PolystarShape.Type type2 = PolystarShape.Type.STAR;
        if (type == type2) {
            this.innerRadiusAnimation = polystarShape.getInnerRadius().createAnimation();
            this.innerRoundednessAnimation = polystarShape.getInnerRoundedness().createAnimation();
        } else {
            this.innerRadiusAnimation = null;
            this.innerRoundednessAnimation = null;
        }
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation2);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation3);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation4);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation5);
        if (type == type2) {
            baseLayer.addAnimation(this.innerRadiusAnimation);
            baseLayer.addAnimation(this.innerRoundednessAnimation);
        }
        baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation2.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation3.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation4.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation5.addUpdateListener(this);
        if (type == type2) {
            this.innerRadiusAnimation.addUpdateListener(this);
            this.innerRoundednessAnimation.addUpdateListener(this);
        }
    }

    private void invalidate() {
        this.isPathValid = false;
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public void addValueCallback(Object obj, LottieValueCallback lottieValueCallback) {
        BaseKeyframeAnimation baseKeyframeAnimation;
        BaseKeyframeAnimation baseKeyframeAnimation2;
        if (obj == LottieProperty.POLYSTAR_POINTS) {
            this.pointsAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.POLYSTAR_ROTATION) {
            this.rotationAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.POSITION) {
            this.positionAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.POLYSTAR_INNER_RADIUS && (baseKeyframeAnimation2 = this.innerRadiusAnimation) != null) {
            baseKeyframeAnimation2.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.POLYSTAR_OUTER_RADIUS) {
            this.outerRadiusAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.POLYSTAR_INNER_ROUNDEDNESS && (baseKeyframeAnimation = this.innerRoundednessAnimation) != null) {
            baseKeyframeAnimation.setValueCallback(lottieValueCallback);
        } else if (obj == LottieProperty.POLYSTAR_OUTER_ROUNDEDNESS) {
            this.outerRoundednessAnimation.setValueCallback(lottieValueCallback);
        }
    }

    public final void createPolygonPath() {
        int iFloor = (int) Math.floor(((Float) this.pointsAnimation.getValue()).floatValue());
        BaseKeyframeAnimation baseKeyframeAnimation = this.rotationAnimation;
        double radians = Math.toRadians((baseKeyframeAnimation == null ? BaseStyleSelectView.COLLAPSE_SLIDE_VALUE : ((Float) baseKeyframeAnimation.getValue()).floatValue()) - 90.0d);
        double d = iFloor;
        float fFloatValue = ((Float) this.outerRoundednessAnimation.getValue()).floatValue() / 100.0f;
        float fFloatValue2 = ((Float) this.outerRadiusAnimation.getValue()).floatValue();
        double d2 = fFloatValue2;
        float fCos = (float) (Math.cos(radians) * d2);
        float fSin = (float) (Math.sin(radians) * d2);
        this.path.moveTo(fCos, fSin);
        double d3 = (float) (6.283185307179586d / d);
        double d4 = radians + d3;
        int i = 0;
        for (double dCeil = Math.ceil(d); i < dCeil; dCeil = dCeil) {
            float fCos2 = (float) (Math.cos(d4) * d2);
            int i2 = i;
            double d5 = d4;
            float fSin2 = (float) (d2 * Math.sin(d4));
            if (fFloatValue != 0.0f) {
                double dAtan2 = (float) (Math.atan2(fSin, fCos) - 1.5707963267948966d);
                float fCos3 = (float) Math.cos(dAtan2);
                float fSin3 = (float) Math.sin(dAtan2);
                double dAtan3 = (float) (Math.atan2(fSin2, fCos2) - 1.5707963267948966d);
                float f = fFloatValue2 * fFloatValue * 0.25f;
                this.path.cubicTo(fCos - (f * fCos3), fSin - (f * fSin3), fCos2 + (((float) Math.cos(dAtan3)) * f), fSin2 + (f * ((float) Math.sin(dAtan3))), fCos2, fSin2);
                fCos = fCos2;
                fSin = fSin2;
            } else {
                fSin = fSin2;
                fCos = fCos2;
                this.path.lineTo(fCos, fSin);
            }
            d4 = d5 + d3;
            i = i2 + 1;
        }
        PointF pointF = (PointF) this.positionAnimation.getValue();
        this.path.offset(pointF.x, pointF.y);
        this.path.close();
    }

    public final void createStarPath() {
        float f;
        float f2;
        float fCos;
        float fSin;
        float f3;
        double d;
        float f4;
        float f5;
        float f6;
        float fFloatValue = ((Float) this.pointsAnimation.getValue()).floatValue();
        BaseKeyframeAnimation baseKeyframeAnimation = this.rotationAnimation;
        double radians = Math.toRadians((baseKeyframeAnimation == null ? BaseStyleSelectView.COLLAPSE_SLIDE_VALUE : ((Float) baseKeyframeAnimation.getValue()).floatValue()) - 90.0d);
        double d2 = fFloatValue;
        float f7 = (float) (6.283185307179586d / d2);
        if (this.isReversed) {
            f7 *= -1.0f;
        }
        float f8 = f7 / 2.0f;
        float f9 = fFloatValue - ((int) fFloatValue);
        if (f9 != 0.0f) {
            radians += (double) ((1.0f - f9) * f8);
        }
        float fFloatValue2 = ((Float) this.outerRadiusAnimation.getValue()).floatValue();
        float fFloatValue3 = ((Float) this.innerRadiusAnimation.getValue()).floatValue();
        BaseKeyframeAnimation baseKeyframeAnimation2 = this.innerRoundednessAnimation;
        float fFloatValue4 = baseKeyframeAnimation2 != null ? ((Float) baseKeyframeAnimation2.getValue()).floatValue() / 100.0f : 0.0f;
        BaseKeyframeAnimation baseKeyframeAnimation3 = this.outerRoundednessAnimation;
        float fFloatValue5 = baseKeyframeAnimation3 != null ? ((Float) baseKeyframeAnimation3.getValue()).floatValue() / 100.0f : 0.0f;
        if (f9 != 0.0f) {
            f4 = ((fFloatValue2 - fFloatValue3) * f9) + fFloatValue3;
            f2 = 0.0f;
            double d3 = f4;
            f = 2.0f;
            float fCos2 = (float) (d3 * Math.cos(radians));
            fSin = (float) (d3 * Math.sin(radians));
            this.path.moveTo(fCos2, fSin);
            d = radians + ((double) ((f7 * f9) / 2.0f));
            fCos = fCos2;
            f3 = f8;
        } else {
            f = 2.0f;
            f2 = 0.0f;
            double d4 = fFloatValue2;
            fCos = (float) (Math.cos(radians) * d4);
            fSin = (float) (d4 * Math.sin(radians));
            this.path.moveTo(fCos, fSin);
            f3 = f8;
            d = radians + ((double) f3);
            f4 = 0.0f;
        }
        double dCeil = Math.ceil(d2) * 2.0d;
        int i = 0;
        boolean z = false;
        double d5 = d;
        float f10 = fSin;
        float f11 = fCos;
        double d6 = d5;
        while (true) {
            double d7 = i;
            if (d7 >= dCeil) {
                PointF pointF = (PointF) this.positionAnimation.getValue();
                this.path.offset(pointF.x, pointF.y);
                this.path.close();
                return;
            }
            float f12 = z ? fFloatValue2 : fFloatValue3;
            float f13 = (f4 == f2 || d7 != dCeil - 2.0d) ? f3 : (f7 * f9) / f;
            double d8 = (f4 == f2 || d7 != dCeil - 1.0d) ? f12 : f4;
            float fCos3 = (float) (d8 * Math.cos(d6));
            float f14 = f7;
            float fSin2 = (float) (d8 * Math.sin(d6));
            if (fFloatValue4 == f2 && fFloatValue5 == f2) {
                this.path.lineTo(fCos3, fSin2);
                f6 = fCos3;
                f5 = fSin2;
            } else {
                double dAtan2 = (float) (Math.atan2(f10, f11) - 1.5707963267948966d);
                float fCos4 = (float) Math.cos(dAtan2);
                float fSin3 = (float) Math.sin(dAtan2);
                float f15 = f11;
                float f16 = f10;
                f5 = fSin2;
                double dAtan3 = (float) (Math.atan2(fSin2, fCos3) - 1.5707963267948966d);
                float fCos5 = (float) Math.cos(dAtan3);
                float fSin4 = (float) Math.sin(dAtan3);
                float f17 = z ? fFloatValue4 : fFloatValue5;
                float f18 = z ? fFloatValue5 : fFloatValue4;
                float f19 = (z ? fFloatValue3 : fFloatValue2) * f17 * 0.47829f;
                float f20 = fCos4 * f19;
                float f21 = f19 * fSin3;
                float f22 = (z ? fFloatValue2 : fFloatValue3) * f18 * 0.47829f;
                float f23 = fCos5 * f22;
                float f24 = f22 * fSin4;
                if (f9 != 0.0f) {
                    if (i == 0) {
                        f20 *= f9;
                        f21 *= f9;
                    } else if (d7 == dCeil - 1.0d) {
                        f23 *= f9;
                        f24 *= f9;
                    }
                }
                f6 = fCos3;
                this.path.cubicTo(f15 - f20, f16 - f21, fCos3 + f23, f5 + f24, f6, f5);
            }
            d6 += (double) f13;
            z = !z;
            i++;
            f3 = f3;
            f11 = f6;
            f10 = f5;
            f7 = f14;
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public Path getPath() {
        if (this.isPathValid) {
            return this.path;
        }
        this.path.reset();
        if (this.hidden) {
            this.isPathValid = true;
            return this.path;
        }
        int i = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type[this.type.ordinal()];
        if (i == 1) {
            createStarPath();
        } else if (i == 2) {
            createPolygonPath();
        }
        this.path.close();
        this.trimPaths.apply(this.path);
        this.isPathValid = true;
        return this.path;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public void onValueChanged() {
        invalidate();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public void resolveKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, list, keyPath2, this);
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public void setContents(List list, List list2) {
        for (int i = 0; i < list.size(); i++) {
            Content content = (Content) list.get(i);
            if (content instanceof TrimPathContent) {
                TrimPathContent trimPathContent = (TrimPathContent) content;
                if (trimPathContent.getType() == ShapeTrimPath.Type.SIMULTANEOUSLY) {
                    this.trimPaths.addTrimPath(trimPathContent);
                    trimPathContent.addListener(this);
                }
            }
        }
    }
}
