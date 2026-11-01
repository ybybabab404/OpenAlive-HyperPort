package com.miui.maml.elements;

import android.graphics.Canvas;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public class LineScreenElement extends GeometryScreenElement {
    public static final String TAG_NAME = "Line";
    private Expression mEndXExp;
    private Expression mEndYExp;

    public LineScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mEndXExp = Expression.build(screenElementRoot.getVariables(), element.getAttribute("x1"));
        this.mEndYExp = Expression.build(screenElementRoot.getVariables(), element.getAttribute("y1"));
    }

    private final float getEndX() {
        Expression expression = this.mEndXExp;
        return scale(expression != null ? expression.evaluate() : BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
    }

    private final float getEndY() {
        Expression expression = this.mEndYExp;
        return scale(expression != null ? expression.evaluate() : BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
    }

    @Override // com.miui.maml.elements.GeometryScreenElement
    public void onDraw(Canvas canvas, GeometryScreenElement.DrawMode drawMode) {
        canvas.drawLine(0.0f, 0.0f, getEndX() - getX(), getEndY() - getY(), ((GeometryScreenElement) this).mPaint);
    }
}
