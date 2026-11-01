package com.airbnb.lottie.animation.content;

import android.graphics.PointF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.model.CubicCurveData;
import com.airbnb.lottie.model.content.RoundedCorners;
import com.airbnb.lottie.model.content.ShapeData;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RoundedCornersContent implements ShapeModifierContent, BaseKeyframeAnimation.AnimationListener {
    public final LottieDrawable lottieDrawable;
    public final String name;
    public final BaseKeyframeAnimation roundedCorners;
    public ShapeData shapeData;

    public RoundedCornersContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, RoundedCorners roundedCorners) {
        this.lottieDrawable = lottieDrawable;
        this.name = roundedCorners.getName();
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation = roundedCorners.getCornerRadius().createAnimation();
        this.roundedCorners = baseKeyframeAnimationCreateAnimation;
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation);
        baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
    }

    public static int floorDiv(int i, int i2) {
        int i3 = i / i2;
        return ((i ^ i2) >= 0 || i2 * i3 == i) ? i3 : i3 - 1;
    }

    public static int floorMod(int i, int i2) {
        return i - (floorDiv(i, i2) * i2);
    }

    public BaseKeyframeAnimation getRoundedCorners() {
        return this.roundedCorners;
    }

    public final ShapeData getShapeData(ShapeData shapeData) {
        List curves = shapeData.getCurves();
        boolean zIsClosed = shapeData.isClosed();
        int size = curves.size() - 1;
        int i = 0;
        while (size >= 0) {
            CubicCurveData cubicCurveData = (CubicCurveData) curves.get(size);
            CubicCurveData cubicCurveData2 = (CubicCurveData) curves.get(floorMod(size - 1, curves.size()));
            PointF vertex = (size != 0 || zIsClosed) ? cubicCurveData2.getVertex() : shapeData.getInitialPoint();
            i = (((size != 0 || zIsClosed) ? cubicCurveData2.getControlPoint2() : vertex).equals(vertex) && cubicCurveData.getControlPoint1().equals(vertex) && !(!shapeData.isClosed() && size == 0 && size == curves.size() - 1)) ? i + 2 : i + 1;
            size--;
        }
        ShapeData shapeData2 = this.shapeData;
        if (shapeData2 == null || shapeData2.getCurves().size() != i) {
            ArrayList arrayList = new ArrayList(i);
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(new CubicCurveData());
            }
            this.shapeData = new ShapeData(new PointF(0.0f, 0.0f), false, arrayList);
        }
        this.shapeData.setClosed(zIsClosed);
        return this.shapeData;
    }

    /* JADX WARN: Code duplicated, block: B:26:0x009f  */
    @Override // com.airbnb.lottie.animation.content.ShapeModifierContent
    public ShapeData modifyShape(ShapeData shapeData) {
        boolean z;
        int i;
        List curves = shapeData.getCurves();
        if (curves.size() > 2) {
            float fFloatValue = ((Float) this.roundedCorners.getValue()).floatValue();
            if (fFloatValue != 0.0f) {
                ShapeData shapeData2 = getShapeData(shapeData);
                shapeData2.setInitialPoint(shapeData.getInitialPoint().x, shapeData.getInitialPoint().y);
                List curves2 = shapeData2.getCurves();
                boolean zIsClosed = shapeData.isClosed();
                int i2 = 0;
                int i3 = 0;
                while (i2 < curves.size()) {
                    CubicCurveData cubicCurveData = (CubicCurveData) curves.get(i2);
                    CubicCurveData cubicCurveData2 = (CubicCurveData) curves.get(floorMod(i2 - 1, curves.size()));
                    CubicCurveData cubicCurveData3 = (CubicCurveData) curves.get(floorMod(i2 - 2, curves.size()));
                    PointF vertex = (i2 != 0 || zIsClosed) ? cubicCurveData2.getVertex() : shapeData.getInitialPoint();
                    PointF controlPoint2 = (i2 != 0 || zIsClosed) ? cubicCurveData2.getControlPoint2() : vertex;
                    PointF controlPoint1 = cubicCurveData.getControlPoint1();
                    PointF vertex2 = cubicCurveData3.getVertex();
                    PointF vertex3 = cubicCurveData.getVertex();
                    if (!shapeData.isClosed() && i2 == 0) {
                        z = i2 == curves.size() + (-1);
                    }
                    if (controlPoint2.equals(vertex) && controlPoint1.equals(vertex) && !z) {
                        float f = vertex.x;
                        float f2 = f - vertex2.x;
                        float f3 = vertex.y;
                        float f4 = f3 - vertex2.y;
                        float f5 = vertex3.x - f;
                        float f6 = vertex3.y - f3;
                        i = i2;
                        float fHypot = (float) Math.hypot(f2, f4);
                        float fHypot2 = (float) Math.hypot(f5, f6);
                        float fMin = Math.min(fFloatValue / fHypot, 0.5f);
                        float fMin2 = Math.min(fFloatValue / fHypot2, 0.5f);
                        float f7 = vertex.x;
                        float f8 = ((vertex2.x - f7) * fMin) + f7;
                        float f9 = vertex.y;
                        float f10 = ((vertex2.y - f9) * fMin) + f9;
                        float f11 = ((vertex3.x - f7) * fMin2) + f7;
                        float f12 = ((vertex3.y - f9) * fMin2) + f9;
                        float f13 = f8 - ((f8 - f7) * 0.5519f);
                        float f14 = f10 - ((f10 - f9) * 0.5519f);
                        float f15 = f11 - ((f11 - f7) * 0.5519f);
                        float f16 = f12 - ((f12 - f9) * 0.5519f);
                        CubicCurveData cubicCurveData4 = (CubicCurveData) curves2.get(floorMod(i3 - 1, curves2.size()));
                        CubicCurveData cubicCurveData5 = (CubicCurveData) curves2.get(i3);
                        cubicCurveData4.setControlPoint2(f8, f10);
                        cubicCurveData4.setVertex(f8, f10);
                        if (i == 0) {
                            shapeData2.setInitialPoint(f8, f10);
                        }
                        cubicCurveData5.setControlPoint1(f13, f14);
                        CubicCurveData cubicCurveData6 = (CubicCurveData) curves2.get(i3 + 1);
                        cubicCurveData5.setControlPoint2(f15, f16);
                        cubicCurveData5.setVertex(f11, f12);
                        cubicCurveData6.setControlPoint1(f11, f12);
                        i3 += 2;
                    } else {
                        i = i2;
                        CubicCurveData cubicCurveData7 = (CubicCurveData) curves2.get(floorMod(i3 - 1, curves2.size()));
                        CubicCurveData cubicCurveData8 = (CubicCurveData) curves2.get(i3);
                        cubicCurveData7.setControlPoint2(cubicCurveData2.getVertex().x, cubicCurveData2.getVertex().y);
                        cubicCurveData7.setVertex(cubicCurveData2.getVertex().x, cubicCurveData2.getVertex().y);
                        cubicCurveData8.setControlPoint1(cubicCurveData.getVertex().x, cubicCurveData.getVertex().y);
                        i3++;
                    }
                    i2 = i + 1;
                }
                return shapeData2;
            }
        }
        return shapeData;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public void setContents(List list, List list2) {
    }
}
