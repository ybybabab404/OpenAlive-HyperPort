package miuix.animation.function;

import com.miui.aod.components.view.BaseStyleSelectView;

/* JADX INFO: loaded from: classes2.dex */
public class Scale implements Function {
    private final Function base;
    private final double pivotX;
    private final double pivotY;
    private final double scaleX;
    private final double scaleY;

    public Scale(Function function, double d, double d2, double d3, double d4) {
        this.base = function;
        this.scaleX = d;
        this.scaleY = d2;
        this.pivotX = d3;
        this.pivotY = d4;
    }

    private double convertX(double d) {
        double d2 = this.scaleX;
        if (d2 == 1.0d) {
            return d;
        }
        double d3 = this.pivotX;
        return d3 == BaseStyleSelectView.COLLAPSE_SLIDE_VALUE ? d * d2 : ((d - d3) * d2) + d3;
    }

    private double convertY(double d) {
        double d2 = this.scaleY;
        if (d2 == 1.0d) {
            return d;
        }
        double d3 = this.pivotY;
        return d3 == BaseStyleSelectView.COLLAPSE_SLIDE_VALUE ? d * d2 : ((d - d3) * d2) + d3;
    }

    private double revertX(double d) {
        double d2 = this.scaleX;
        if (d2 == 1.0d) {
            return d;
        }
        double d3 = this.pivotX;
        return d3 == BaseStyleSelectView.COLLAPSE_SLIDE_VALUE ? d / d2 : ((d - d3) / d2) + d3;
    }

    private double revertY(double d) {
        double d2 = this.scaleY;
        if (d2 == 1.0d) {
            return d;
        }
        double d3 = this.pivotY;
        return d3 == BaseStyleSelectView.COLLAPSE_SLIDE_VALUE ? d / d2 : ((d - d3) / d2) + d3;
    }

    @Override // miuix.animation.function.Function
    public double apply(double d) {
        return convertY(this.base.apply(revertX(d)));
    }

    public Function getBase() {
        return this.base;
    }

    public double getPivotX() {
        return this.pivotX;
    }

    public double getPivotY() {
        return this.pivotY;
    }

    public double getScaleX() {
        return this.scaleX;
    }

    public double getScaleY() {
        return this.scaleY;
    }
}
