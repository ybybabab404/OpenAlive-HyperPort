package com.miui.maml.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.maml.folme.AnimatedPropertyType;
import com.miui.maml.folme.AnimatedTarget;
import com.miui.maml.folme.PropertyWrapper;
import com.miui.maml.util.MamlLog;
import com.xiaomi.onetrack.util.ac;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public class RectangleScreenElement extends GeometryScreenElement {
    public static final AnimatedProperty CORNER_RADIUS_X;
    public static final AnimatedProperty CORNER_RADIUS_Y;
    private static final String LOG_TAG = "RectangleScreenElement";
    private static final String PROPERTY_NAME_CORNER_RADIUS_X = "cornerRadiusX";
    private static final String PROPERTY_NAME_CORNER_RADIUS_Y = "cornerRadiusY";
    public static final String TAG_NAME = "Rectangle";
    private float mCornerRadiusX;
    private float mCornerRadiusY;
    private PropertyWrapper mRXProperty;
    private PropertyWrapper mRYProperty;
    private RectF mRectF;
    private Paint mTempPaint;
    private Path mTempPath;

    static {
        AnimatedProperty animatedProperty = new AnimatedProperty(PROPERTY_NAME_CORNER_RADIUS_X) { // from class: com.miui.maml.elements.RectangleScreenElement.1
            @Override // miuix.animation.property.FloatProperty
            public float getValue(AnimatedScreenElement animatedScreenElement) {
                if (animatedScreenElement instanceof RectangleScreenElement) {
                    return (float) ((RectangleScreenElement) animatedScreenElement).mRXProperty.getValue();
                }
                return 0.0f;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(AnimatedScreenElement animatedScreenElement, float f) {
                if (animatedScreenElement instanceof RectangleScreenElement) {
                    ((RectangleScreenElement) animatedScreenElement).mRXProperty.setValue(f);
                }
            }

            @Override // com.miui.maml.folme.IAnimatedProperty
            public void setVelocityValue(AnimatedScreenElement animatedScreenElement, float f) {
                if (animatedScreenElement instanceof RectangleScreenElement) {
                    ((RectangleScreenElement) animatedScreenElement).mRXProperty.setVelocity(f);
                }
            }
        };
        CORNER_RADIUS_X = animatedProperty;
        AnimatedProperty animatedProperty2 = new AnimatedProperty(PROPERTY_NAME_CORNER_RADIUS_Y) { // from class: com.miui.maml.elements.RectangleScreenElement.2
            @Override // miuix.animation.property.FloatProperty
            public float getValue(AnimatedScreenElement animatedScreenElement) {
                if (animatedScreenElement instanceof RectangleScreenElement) {
                    return (float) ((RectangleScreenElement) animatedScreenElement).mRYProperty.getValue();
                }
                return 0.0f;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(AnimatedScreenElement animatedScreenElement, float f) {
                if (animatedScreenElement instanceof RectangleScreenElement) {
                    ((RectangleScreenElement) animatedScreenElement).mRYProperty.setValue(f);
                }
            }

            @Override // com.miui.maml.folme.IAnimatedProperty
            public void setVelocityValue(AnimatedScreenElement animatedScreenElement, float f) {
                if (animatedScreenElement instanceof RectangleScreenElement) {
                    ((RectangleScreenElement) animatedScreenElement).mRYProperty.setVelocity(f);
                }
            }
        };
        CORNER_RADIUS_Y = animatedProperty2;
        AnimatedProperty.sPropertyNameMap.put(PROPERTY_NAME_CORNER_RADIUS_X, animatedProperty);
        AnimatedTarget.sPropertyMap.put(Integer.valueOf(AnimatedPropertyType.FILL_COLOR), animatedProperty);
        AnimatedTarget.sPropertyTypeMap.put(animatedProperty, Integer.valueOf(AnimatedPropertyType.CORNER_RADIUS_X));
        AnimatedProperty.sPropertyNameMap.put(PROPERTY_NAME_CORNER_RADIUS_Y, animatedProperty2);
        AnimatedTarget.sPropertyMap.put(Integer.valueOf(AnimatedPropertyType.STROKE_COLOR), animatedProperty2);
        AnimatedTarget.sPropertyTypeMap.put(animatedProperty2, Integer.valueOf(AnimatedPropertyType.CORNER_RADIUS_Y));
    }

    public RectangleScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mRectF = new RectF();
        this.mTempPath = new Path();
        this.mTempPaint = new Paint();
        resolveCornerRadius(element);
    }

    private void resolveCornerRadius(Element element) {
        Expression[] expressionArrBuildMultiple = Expression.buildMultiple(getVariables(), element.getAttribute("cornerRadiusExp"));
        if (expressionArrBuildMultiple == null) {
            String attr = getAttr(element, "cornerRadius");
            if (!TextUtils.isEmpty(attr)) {
                String[] strArrSplit = attr.split(ac.b);
                try {
                    if (strArrSplit.length < 1) {
                        return;
                    }
                    if (strArrSplit.length == 1) {
                        float fScale = scale(Float.parseFloat(strArrSplit[0]));
                        this.mCornerRadiusY = fScale;
                        this.mCornerRadiusX = fScale;
                    } else {
                        this.mCornerRadiusX = scale(Float.parseFloat(strArrSplit[0]));
                        this.mCornerRadiusY = scale(Float.parseFloat(strArrSplit[1]));
                    }
                } catch (Exception e) {
                    MamlLog.e(LOG_TAG, "illegal number format of cornerRadius.", e);
                }
            }
        }
        Expression expression = (expressionArrBuildMultiple == null || expressionArrBuildMultiple.length <= 0) ? null : expressionArrBuildMultiple[0];
        Expression expression2 = (expressionArrBuildMultiple == null || expressionArrBuildMultiple.length <= 1) ? expression : expressionArrBuildMultiple[1];
        this.mRXProperty = new PropertyWrapper(this.mName + ".cornerRadiusX", getVariables(), expression, isInFolmeMode(), descale(this.mCornerRadiusX));
        this.mRYProperty = new PropertyWrapper(this.mName + ".cornerRadiusY", getVariables(), expression2, isInFolmeMode(), descale(this.mCornerRadiusY));
    }

    @Override // com.miui.maml.elements.GeometryScreenElement, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void doTick(long j) {
        super.doTick(j);
        this.mCornerRadiusX = scale(this.mRXProperty.getValue());
        this.mCornerRadiusY = scale(this.mRYProperty.getValue());
    }

    @Override // com.miui.maml.elements.GeometryScreenElement, com.miui.maml.elements.AnimatedScreenElement
    public void initProperties() {
        super.initProperties();
        this.mRXProperty.init();
        this.mRYProperty.init();
    }

    @Override // com.miui.maml.elements.GeometryScreenElement
    public void onDraw(Canvas canvas, GeometryScreenElement.DrawMode drawMode) {
        float width = getWidth();
        float height = getHeight();
        float left = getLeft(0.0f, width);
        float top = getTop(0.0f, height);
        if (width <= 0.0f) {
            width = 0.0f;
        }
        float f = width + left;
        if (height <= 0.0f) {
            height = 0.0f;
        }
        this.mRectF.set(left, top, f, height + top);
        float f2 = this.mWeight / 2.0f;
        float fMin = Math.min(this.mCornerRadiusX, this.mCornerRadiusY);
        if (drawMode == GeometryScreenElement.DrawMode.STROKE_OUTER) {
            float f3 = -f2;
            this.mRectF.inset(f3, f3);
            if (fMin > 0.0f) {
                fMin += f2;
            }
        } else if (drawMode == GeometryScreenElement.DrawMode.STROKE_INNER) {
            if (fMin > f2) {
                fMin -= f2;
            } else if (fMin > 0.0f) {
                this.mTempPath.reset();
                this.mTempPath.setFillType(Path.FillType.EVEN_ODD);
                Path path = this.mTempPath;
                RectF rectF = this.mRectF;
                Path.Direction direction = Path.Direction.CCW;
                path.addRoundRect(rectF, fMin, fMin, direction);
                RectF rectF2 = this.mRectF;
                float f4 = this.mWeight;
                rectF2.inset(f4, f4);
                this.mTempPath.addRect(this.mRectF, direction);
                this.mTempPath.close();
                this.mTempPaint.set(((GeometryScreenElement) this).mPaint);
                this.mTempPaint.setStyle(Paint.Style.FILL);
                canvas.drawPath(this.mTempPath, this.mTempPaint);
                return;
            }
            this.mRectF.inset(f2, f2);
        }
        if (fMin <= 0.0f) {
            canvas.drawRect(this.mRectF, ((GeometryScreenElement) this).mPaint);
        } else {
            canvas.drawRoundRect(this.mRectF, fMin, fMin, ((GeometryScreenElement) this).mPaint);
        }
    }
}
