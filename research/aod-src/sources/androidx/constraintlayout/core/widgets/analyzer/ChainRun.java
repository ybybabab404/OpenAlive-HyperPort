package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class ChainRun extends WidgetRun {
    public int chainStyle;
    public ArrayList widgets;

    public ChainRun(ConstraintWidget constraintWidget, int i) {
        super(constraintWidget);
        this.widgets = new ArrayList();
        this.orientation = i;
        build();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void apply() {
        Iterator it = this.widgets.iterator();
        while (it.hasNext()) {
            ((WidgetRun) it.next()).apply();
        }
        int size = this.widgets.size();
        if (size < 1) {
            return;
        }
        ConstraintWidget constraintWidget = ((WidgetRun) this.widgets.get(0)).widget;
        ConstraintWidget constraintWidget2 = ((WidgetRun) this.widgets.get(size - 1)).widget;
        if (this.orientation == 0) {
            ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
            ConstraintAnchor constraintAnchor2 = constraintWidget2.mRight;
            DependencyNode target = getTarget(constraintAnchor, 0);
            int margin = constraintAnchor.getMargin();
            ConstraintWidget firstVisibleWidget = getFirstVisibleWidget();
            if (firstVisibleWidget != null) {
                margin = firstVisibleWidget.mLeft.getMargin();
            }
            if (target != null) {
                addTarget(this.start, target, margin);
            }
            DependencyNode target2 = getTarget(constraintAnchor2, 0);
            int margin2 = constraintAnchor2.getMargin();
            ConstraintWidget lastVisibleWidget = getLastVisibleWidget();
            if (lastVisibleWidget != null) {
                margin2 = lastVisibleWidget.mRight.getMargin();
            }
            if (target2 != null) {
                addTarget(this.end, target2, -margin2);
            }
        } else {
            ConstraintAnchor constraintAnchor3 = constraintWidget.mTop;
            ConstraintAnchor constraintAnchor4 = constraintWidget2.mBottom;
            DependencyNode target3 = getTarget(constraintAnchor3, 1);
            int margin3 = constraintAnchor3.getMargin();
            ConstraintWidget firstVisibleWidget2 = getFirstVisibleWidget();
            if (firstVisibleWidget2 != null) {
                margin3 = firstVisibleWidget2.mTop.getMargin();
            }
            if (target3 != null) {
                addTarget(this.start, target3, margin3);
            }
            DependencyNode target4 = getTarget(constraintAnchor4, 1);
            int margin4 = constraintAnchor4.getMargin();
            ConstraintWidget lastVisibleWidget2 = getLastVisibleWidget();
            if (lastVisibleWidget2 != null) {
                margin4 = lastVisibleWidget2.mBottom.getMargin();
            }
            if (target4 != null) {
                addTarget(this.end, target4, -margin4);
            }
        }
        this.start.updateDelegate = this;
        this.end.updateDelegate = this;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        for (int i = 0; i < this.widgets.size(); i++) {
            ((WidgetRun) this.widgets.get(i)).applyToWidget();
        }
    }

    public final void build() {
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2 = this.widget;
        ConstraintWidget previousChainMember = constraintWidget2.getPreviousChainMember(this.orientation);
        while (true) {
            ConstraintWidget constraintWidget3 = previousChainMember;
            constraintWidget = constraintWidget2;
            constraintWidget2 = constraintWidget3;
            if (constraintWidget2 == null) {
                break;
            } else {
                previousChainMember = constraintWidget2.getPreviousChainMember(this.orientation);
            }
        }
        this.widget = constraintWidget;
        this.widgets.add(constraintWidget.getRun(this.orientation));
        ConstraintWidget nextChainMember = constraintWidget.getNextChainMember(this.orientation);
        while (nextChainMember != null) {
            this.widgets.add(nextChainMember.getRun(this.orientation));
            nextChainMember = nextChainMember.getNextChainMember(this.orientation);
        }
        for (WidgetRun widgetRun : this.widgets) {
            int i = this.orientation;
            if (i == 0) {
                widgetRun.widget.horizontalChainRun = this;
            } else if (i == 1) {
                widgetRun.widget.verticalChainRun = this;
            }
        }
        if (this.orientation == 0 && ((ConstraintWidgetContainer) this.widget.getParent()).isRtl() && this.widgets.size() > 1) {
            ArrayList arrayList = this.widgets;
            this.widget = ((WidgetRun) arrayList.get(arrayList.size() - 1)).widget;
        }
        this.chainStyle = this.orientation == 0 ? this.widget.getHorizontalChainStyle() : this.widget.getVerticalChainStyle();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void clear() {
        this.runGroup = null;
        Iterator it = this.widgets.iterator();
        while (it.hasNext()) {
            ((WidgetRun) it.next()).clear();
        }
    }

    public final ConstraintWidget getFirstVisibleWidget() {
        for (int i = 0; i < this.widgets.size(); i++) {
            WidgetRun widgetRun = (WidgetRun) this.widgets.get(i);
            if (widgetRun.widget.getVisibility() != 8) {
                return widgetRun.widget;
            }
        }
        return null;
    }

    public final ConstraintWidget getLastVisibleWidget() {
        for (int size = this.widgets.size() - 1; size >= 0; size--) {
            WidgetRun widgetRun = (WidgetRun) this.widgets.get(size);
            if (widgetRun.widget.getVisibility() != 8) {
                return widgetRun.widget;
            }
        }
        return null;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public long getWrapDimension() {
        int size = this.widgets.size();
        long wrapDimension = 0;
        for (int i = 0; i < size; i++) {
            WidgetRun widgetRun = (WidgetRun) this.widgets.get(i);
            wrapDimension = wrapDimension + ((long) widgetRun.start.margin) + widgetRun.getWrapDimension() + ((long) widgetRun.end.margin);
        }
        return wrapDimension;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public boolean supportsWrapComputation() {
        int size = this.widgets.size();
        for (int i = 0; i < size; i++) {
            if (!((WidgetRun) this.widgets.get(i)).supportsWrapComputation()) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ChainRun ");
        sb.append(this.orientation == 0 ? "horizontal : " : "vertical : ");
        for (WidgetRun widgetRun : this.widgets) {
            sb.append("<");
            sb.append(widgetRun);
            sb.append("> ");
        }
        return sb.toString();
    }

    /* JADX WARN: Code duplicated, block: B:90:0x0160  */
    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        int i;
        int i2;
        boolean z;
        float f;
        float f2;
        int i3;
        int i4;
        int i5;
        int i6;
        float f3;
        int i7;
        int i8;
        int i9;
        int i10;
        boolean z2;
        if (this.start.resolved && this.end.resolved) {
            ConstraintWidget parent = this.widget.getParent();
            boolean zIsRtl = parent instanceof ConstraintWidgetContainer ? ((ConstraintWidgetContainer) parent).isRtl() : false;
            int i11 = this.end.value - this.start.value;
            int size = this.widgets.size();
            int i12 = 0;
            while (true) {
                i = -1;
                i2 = 8;
                if (i12 >= size) {
                    i12 = -1;
                    break;
                } else if (((WidgetRun) this.widgets.get(i12)).widget.getVisibility() != 8) {
                    break;
                } else {
                    i12++;
                }
            }
            int i13 = size - 1;
            for (int i14 = i13; i14 >= 0; i14--) {
                if (((WidgetRun) this.widgets.get(i14)).widget.getVisibility() != 8) {
                    i = i14;
                    break;
                }
            }
            int i15 = 0;
            while (true) {
                if (i15 >= 2) {
                    z = zIsRtl;
                    f = 0.0f;
                    f2 = 0.0f;
                    i3 = 0;
                    i4 = 0;
                    i5 = 0;
                    break;
                }
                int i16 = 0;
                i4 = 0;
                i5 = 0;
                int i17 = 0;
                f2 = 0.0f;
                while (i16 < size) {
                    WidgetRun widgetRun = (WidgetRun) this.widgets.get(i16);
                    if (widgetRun.widget.getVisibility() == i2) {
                        z2 = zIsRtl;
                    } else {
                        i17++;
                        if (i16 > 0 && i16 >= i12) {
                            i4 += widgetRun.start.margin;
                        }
                        DimensionDependency dimensionDependency = widgetRun.dimension;
                        int i18 = dimensionDependency.value;
                        boolean z3 = widgetRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                        if (z3) {
                            int i19 = this.orientation;
                            if (i19 == 0 && !widgetRun.widget.horizontalRun.dimension.resolved) {
                                return;
                            }
                            if (i19 == 1 && !widgetRun.widget.verticalRun.dimension.resolved) {
                                return;
                            } else {
                                z2 = zIsRtl;
                            }
                        } else {
                            z2 = zIsRtl;
                            if (widgetRun.matchConstraintsType == 1 && i15 == 0) {
                                i18 = dimensionDependency.wrapValue;
                                i5++;
                            } else if (dimensionDependency.resolved) {
                            }
                            z3 = true;
                        }
                        if (z3) {
                            i4 += i18;
                        } else {
                            i5++;
                            float f4 = widgetRun.widget.mWeight[this.orientation];
                            if (f4 >= 0.0f) {
                                f2 += f4;
                            }
                        }
                        if (i16 < i13 && i16 < i) {
                            i4 += -widgetRun.end.margin;
                        }
                    }
                    i16++;
                    zIsRtl = z2;
                    i2 = 8;
                }
                z = zIsRtl;
                f = 0.0f;
                if (i4 < i11 || i5 == 0) {
                    i3 = i17;
                    break;
                } else {
                    i15++;
                    zIsRtl = z;
                    i2 = 8;
                }
            }
            int i20 = this.start.value;
            if (z) {
                i20 = this.end.value;
            }
            float f5 = 0.5f;
            if (i4 > i11) {
                i20 = z ? i20 + ((int) (((i4 - i11) / 2.0f) + 0.5f)) : i20 - ((int) (((i4 - i11) / 2.0f) + 0.5f));
            }
            if (i5 > 0) {
                float f6 = i11 - i4;
                int i21 = (int) ((f6 / i5) + 0.5f);
                int i22 = 0;
                int i23 = 0;
                while (i22 < size) {
                    WidgetRun widgetRun2 = (WidgetRun) this.widgets.get(i22);
                    float f7 = f5;
                    int i24 = i20;
                    if (widgetRun2.widget.getVisibility() != 8 && widgetRun2.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        DimensionDependency dimensionDependency2 = widgetRun2.dimension;
                        if (dimensionDependency2.resolved) {
                            i21 = i21;
                            i23 = i23;
                        } else {
                            int i25 = f2 > f ? (int) (((widgetRun2.widget.mWeight[this.orientation] * f6) / f2) + f7) : i21;
                            if (this.orientation == 0) {
                                ConstraintWidget constraintWidget = widgetRun2.widget;
                                i9 = constraintWidget.mMatchConstraintMaxWidth;
                                i10 = constraintWidget.mMatchConstraintMinWidth;
                            } else {
                                ConstraintWidget constraintWidget2 = widgetRun2.widget;
                                i9 = constraintWidget2.mMatchConstraintMaxHeight;
                                i10 = constraintWidget2.mMatchConstraintMinHeight;
                            }
                            int i26 = i23;
                            int iMax = Math.max(i10, widgetRun2.matchConstraintsType == 1 ? Math.min(i25, dimensionDependency2.wrapValue) : i25);
                            if (i9 > 0) {
                                iMax = Math.min(i9, iMax);
                            }
                            if (iMax != i25) {
                                i23 = i26 + 1;
                                i25 = iMax;
                            } else {
                                i23 = i26;
                            }
                            widgetRun2.dimension.resolve(i25);
                        }
                    } else {
                        i21 = i21;
                        i23 = i23;
                    }
                    i22++;
                    f5 = f7;
                    i20 = i24;
                    f6 = f6;
                    i21 = i21;
                }
                i6 = i20;
                f3 = f5;
                int i27 = i23;
                if (i27 > 0) {
                    i5 -= i27;
                    i4 = 0;
                    for (int i28 = 0; i28 < size; i28++) {
                        WidgetRun widgetRun3 = (WidgetRun) this.widgets.get(i28);
                        if (widgetRun3.widget.getVisibility() != 8) {
                            if (i28 > 0 && i28 >= i12) {
                                i4 += widgetRun3.start.margin;
                            }
                            i4 += widgetRun3.dimension.value;
                            if (i28 < i13 && i28 < i) {
                                i4 += -widgetRun3.end.margin;
                            }
                        }
                    }
                }
                i8 = 2;
                if (this.chainStyle == 2 && i27 == 0) {
                    i7 = 0;
                    this.chainStyle = 0;
                } else {
                    i7 = 0;
                }
            } else {
                i6 = i20;
                f3 = 0.5f;
                i7 = 0;
                i8 = 2;
            }
            if (i4 > i11) {
                this.chainStyle = i8;
            }
            if (i3 > 0 && i5 == 0 && i12 == i) {
                this.chainStyle = i8;
            }
            int i29 = this.chainStyle;
            if (i29 == 1) {
                int i30 = i3 > 1 ? (i11 - i4) / (i3 - 1) : i3 == 1 ? (i11 - i4) / 2 : i7;
                if (i5 > 0) {
                    i30 = i7;
                }
                int i31 = i6;
                while (i7 < size) {
                    WidgetRun widgetRun4 = (WidgetRun) this.widgets.get(z ? size - (i7 + 1) : i7);
                    if (widgetRun4.widget.getVisibility() == 8) {
                        widgetRun4.start.resolve(i31);
                        widgetRun4.end.resolve(i31);
                    } else {
                        if (i7 > 0) {
                            i31 = z ? i31 - i30 : i31 + i30;
                        }
                        if (i7 > 0 && i7 >= i12) {
                            i31 = z ? i31 - widgetRun4.start.margin : i31 + widgetRun4.start.margin;
                        }
                        if (z) {
                            widgetRun4.end.resolve(i31);
                        } else {
                            widgetRun4.start.resolve(i31);
                        }
                        DimensionDependency dimensionDependency3 = widgetRun4.dimension;
                        int i32 = dimensionDependency3.value;
                        if (widgetRun4.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun4.matchConstraintsType == 1) {
                            i32 = dimensionDependency3.wrapValue;
                        }
                        i31 = z ? i31 - i32 : i31 + i32;
                        if (z) {
                            widgetRun4.start.resolve(i31);
                        } else {
                            widgetRun4.end.resolve(i31);
                        }
                        widgetRun4.resolved = true;
                        if (i7 < i13 && i7 < i) {
                            i31 = z ? i31 - (-widgetRun4.end.margin) : i31 + (-widgetRun4.end.margin);
                        }
                    }
                    i7++;
                }
                return;
            }
            if (i29 == 0) {
                int i33 = (i11 - i4) / (i3 + 1);
                if (i5 > 0) {
                    i33 = i7;
                }
                int i34 = i6;
                while (i7 < size) {
                    WidgetRun widgetRun5 = (WidgetRun) this.widgets.get(z ? size - (i7 + 1) : i7);
                    if (widgetRun5.widget.getVisibility() == 8) {
                        widgetRun5.start.resolve(i34);
                        widgetRun5.end.resolve(i34);
                    } else {
                        int i35 = z ? i34 - i33 : i34 + i33;
                        if (i7 > 0 && i7 >= i12) {
                            i35 = z ? i35 - widgetRun5.start.margin : i35 + widgetRun5.start.margin;
                        }
                        if (z) {
                            widgetRun5.end.resolve(i35);
                        } else {
                            widgetRun5.start.resolve(i35);
                        }
                        DimensionDependency dimensionDependency4 = widgetRun5.dimension;
                        int iMin = dimensionDependency4.value;
                        if (widgetRun5.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun5.matchConstraintsType == 1) {
                            iMin = Math.min(iMin, dimensionDependency4.wrapValue);
                        }
                        i34 = z ? i35 - iMin : i35 + iMin;
                        if (z) {
                            widgetRun5.start.resolve(i34);
                        } else {
                            widgetRun5.end.resolve(i34);
                        }
                        if (i7 < i13 && i7 < i) {
                            i34 = z ? i34 - (-widgetRun5.end.margin) : i34 + (-widgetRun5.end.margin);
                        }
                    }
                    i7++;
                }
                return;
            }
            if (i29 == 2) {
                float horizontalBiasPercent = this.orientation == 0 ? this.widget.getHorizontalBiasPercent() : this.widget.getVerticalBiasPercent();
                if (z) {
                    horizontalBiasPercent = 1.0f - horizontalBiasPercent;
                }
                int i36 = (int) (((i11 - i4) * horizontalBiasPercent) + f3);
                if (i36 < 0 || i5 > 0) {
                    i36 = i7;
                }
                int i37 = z ? i6 - i36 : i6 + i36;
                while (i7 < size) {
                    WidgetRun widgetRun6 = (WidgetRun) this.widgets.get(z ? size - (i7 + 1) : i7);
                    if (widgetRun6.widget.getVisibility() == 8) {
                        widgetRun6.start.resolve(i37);
                        widgetRun6.end.resolve(i37);
                    } else {
                        if (i7 > 0 && i7 >= i12) {
                            i37 = z ? i37 - widgetRun6.start.margin : i37 + widgetRun6.start.margin;
                        }
                        if (z) {
                            widgetRun6.end.resolve(i37);
                        } else {
                            widgetRun6.start.resolve(i37);
                        }
                        DimensionDependency dimensionDependency5 = widgetRun6.dimension;
                        int i38 = dimensionDependency5.value;
                        if (widgetRun6.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun6.matchConstraintsType == 1) {
                            i38 = dimensionDependency5.wrapValue;
                        }
                        i37 = z ? i37 - i38 : i37 + i38;
                        if (z) {
                            widgetRun6.start.resolve(i37);
                        } else {
                            widgetRun6.end.resolve(i37);
                        }
                        if (i7 < i13 && i7 < i) {
                            i37 = z ? i37 - (-widgetRun6.end.margin) : i37 + (-widgetRun6.end.margin);
                        }
                    }
                    i7++;
                }
            }
        }
    }
}
