package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import com.airbnb.lottie.animation.content.ShapeModifierContent;
import com.airbnb.lottie.model.content.ShapeData;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ShapeKeyframeAnimation extends BaseKeyframeAnimation<ShapeData, Path> {
    public List shapeModifiers;
    public final Path tempPath;
    public final ShapeData tempShapeData;

    public ShapeKeyframeAnimation(List list) {
        super(list);
        this.tempShapeData = new ShapeData();
        this.tempPath = new Path();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public Path getValue(Keyframe keyframe, float f) {
        this.tempShapeData.interpolateBetween((ShapeData) keyframe.startValue, (ShapeData) keyframe.endValue, f);
        ShapeData shapeDataModifyShape = this.tempShapeData;
        List list = this.shapeModifiers;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                shapeDataModifyShape = ((ShapeModifierContent) this.shapeModifiers.get(size)).modifyShape(shapeDataModifyShape);
            }
        }
        MiscUtils.getPathFromData(shapeDataModifyShape, this.tempPath);
        return this.tempPath;
    }

    public void setShapeModifiers(List list) {
        this.shapeModifiers = list;
    }
}
