package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.Cache;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.Metrics;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.state.WidgetFrame;
import androidx.constraintlayout.core.widgets.analyzer.ChainRun;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.onetrack.api.an;
import com.xiaomi.onetrack.util.ac;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class ConstraintWidget {
    public static float DEFAULT_BIAS = 0.5f;
    public ChainRun horizontalChainRun;
    public int horizontalGroup;
    public boolean inPlaceholder;
    public ArrayList mAnchors;
    public boolean mAnimated;
    public int mBaselineDistance;
    public boolean mBottomHasCentered;
    public ConstraintAnchor mCenter;
    public Object mCompanionWidget;
    public int mContainerItemSkip;
    public String mDebugName;
    public float mDimensionRatio;
    public int mDimensionRatioSide;
    public int mDistToBottom;
    public int mDistToLeft;
    public int mDistToRight;
    public int mDistToTop;
    public boolean mGroupsToSolver;
    public int mHeight;
    public float mHorizontalBiasPercent;
    public boolean mHorizontalChainFixedPosition;
    public int mHorizontalChainStyle;
    public ConstraintWidget mHorizontalNextWidget;
    public boolean mHorizontalWrapVisited;
    public boolean mIsHeightWrapContent;
    public boolean[] mIsInBarrier;
    public boolean mIsWidthWrapContent;
    public boolean mLeftHasCentered;
    public ConstraintAnchor[] mListAnchors;
    public DimensionBehaviour[] mListDimensionBehaviors;
    public ConstraintWidget[] mListNextMatchConstraintsWidget;
    public int mMinHeight;
    public int mMinWidth;
    public ConstraintWidget[] mNextChainWidget;
    public int mOffsetX;
    public int mOffsetY;
    public ConstraintWidget mParent;
    public int mRelX;
    public int mRelY;
    public boolean mRightHasCentered;
    public boolean mTopHasCentered;
    public String mType;
    public float mVerticalBiasPercent;
    public boolean mVerticalChainFixedPosition;
    public int mVerticalChainStyle;
    public ConstraintWidget mVerticalNextWidget;
    public boolean mVerticalWrapVisited;
    public int mVisibility;
    public float[] mWeight;
    public int mWidth;
    public int mX;
    public int mY;
    public String stringId;
    public ChainRun verticalChainRun;
    public int verticalGroup;
    public boolean measured = false;
    public WidgetRun[] run = new WidgetRun[2];
    public HorizontalWidgetRun horizontalRun = null;
    public VerticalWidgetRun verticalRun = null;
    public boolean[] isTerminalWidget = {true, true};
    public boolean mResolvedHasRatio = false;
    public boolean mMeasureRequested = true;
    public boolean OPTIMIZE_WRAP = false;
    public boolean OPTIMIZE_WRAP_ON_RESOLVED = true;
    public int mWidthOverride = -1;
    public int mHeightOverride = -1;
    public WidgetFrame frame = new WidgetFrame(this);
    public boolean resolvedHorizontal = false;
    public boolean resolvedVertical = false;
    public boolean horizontalSolvingPass = false;
    public boolean verticalSolvingPass = false;
    public int mHorizontalResolution = -1;
    public int mVerticalResolution = -1;
    public int mWrapBehaviorInParent = 0;
    public int mMatchConstraintDefaultWidth = 0;
    public int mMatchConstraintDefaultHeight = 0;
    public int[] mResolvedMatchConstraintDefault = new int[2];
    public int mMatchConstraintMinWidth = 0;
    public int mMatchConstraintMaxWidth = 0;
    public float mMatchConstraintPercentWidth = 1.0f;
    public int mMatchConstraintMinHeight = 0;
    public int mMatchConstraintMaxHeight = 0;
    public float mMatchConstraintPercentHeight = 1.0f;
    public int mResolvedDimensionRatioSide = -1;
    public float mResolvedDimensionRatio = 1.0f;
    public int[] mMaxDimension = {Integer.MAX_VALUE, Integer.MAX_VALUE};
    public float mCircleConstraintAngle = 0.0f;
    public boolean hasBaseline = false;
    public boolean mInVirtualLayout = false;
    public int mLastHorizontalMeasureSpec = 0;
    public int mLastVerticalMeasureSpec = 0;
    public ConstraintAnchor mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
    public ConstraintAnchor mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
    public ConstraintAnchor mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
    public ConstraintAnchor mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
    public ConstraintAnchor mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
    public ConstraintAnchor mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
    public ConstraintAnchor mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);

    /* JADX INFO: renamed from: androidx.constraintlayout.core.widgets.ConstraintWidget$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type;
        public static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour;

        static {
            int[] iArr = new int[DimensionBehaviour.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour = iArr;
            try {
                iArr[DimensionBehaviour.FIXED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[DimensionBehaviour.WRAP_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[DimensionBehaviour.MATCH_PARENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[DimensionBehaviour.MATCH_CONSTRAINT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[ConstraintAnchor.Type.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type = iArr2;
            try {
                iArr2[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BASELINE.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER_X.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER_Y.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.NONE.ordinal()] = 9;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public ConstraintWidget() {
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor;
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mType = null;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        addAnchors();
    }

    public final void addAnchors() {
        this.mAnchors.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mCenter);
        this.mAnchors.add(this.mBaseline);
    }

    public void addChildrenToSolverByDependency(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, HashSet hashSet, int i, boolean z) {
        if (z) {
            if (!hashSet.contains(this)) {
                return;
            }
            Optimizer.checkMatchParent(constraintWidgetContainer, linearSystem, this);
            hashSet.remove(this);
            addToSolver(linearSystem, constraintWidgetContainer.optimizeFor(64));
        }
        if (i == 0) {
            HashSet dependents = this.mLeft.getDependents();
            if (dependents != null) {
                Iterator it = dependents.iterator();
                while (it.hasNext()) {
                    ((ConstraintAnchor) it.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
                }
            }
            HashSet dependents2 = this.mRight.getDependents();
            if (dependents2 != null) {
                Iterator it2 = dependents2.iterator();
                while (it2.hasNext()) {
                    ((ConstraintAnchor) it2.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
                }
                return;
            }
            return;
        }
        HashSet dependents3 = this.mTop.getDependents();
        if (dependents3 != null) {
            Iterator it3 = dependents3.iterator();
            while (it3.hasNext()) {
                ((ConstraintAnchor) it3.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
        HashSet dependents4 = this.mBottom.getDependents();
        if (dependents4 != null) {
            Iterator it4 = dependents4.iterator();
            while (it4.hasNext()) {
                ((ConstraintAnchor) it4.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
        HashSet dependents5 = this.mBaseline.getDependents();
        if (dependents5 != null) {
            Iterator it5 = dependents5.iterator();
            while (it5.hasNext()) {
                ((ConstraintAnchor) it5.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
    }

    public boolean addFirst() {
        return (this instanceof VirtualLayout) || (this instanceof Guideline);
    }

    /* JADX WARN: Code duplicated, block: B:192:0x02f4  */
    /* JADX WARN: Code duplicated, block: B:194:0x02f9 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:196:0x02fd  */
    /* JADX WARN: Code duplicated, block: B:199:0x0301  */
    /* JADX WARN: Code duplicated, block: B:19:0x004d  */
    /* JADX WARN: Code duplicated, block: B:203:0x030b  */
    /* JADX WARN: Code duplicated, block: B:206:0x0317  */
    /* JADX WARN: Code duplicated, block: B:209:0x031e  */
    /* JADX WARN: Code duplicated, block: B:211:0x0322  */
    /* JADX WARN: Code duplicated, block: B:214:0x033d  */
    /* JADX WARN: Code duplicated, block: B:235:0x03a4  */
    /* JADX WARN: Code duplicated, block: B:250:0x0437  */
    /* JADX WARN: Code duplicated, block: B:267:0x0489  */
    /* JADX WARN: Code duplicated, block: B:270:0x0499  */
    /* JADX WARN: Code duplicated, block: B:271:0x049b  */
    /* JADX WARN: Code duplicated, block: B:273:0x049e  */
    /* JADX WARN: Code duplicated, block: B:310:0x0576  */
    /* JADX WARN: Code duplicated, block: B:312:0x057d  */
    /* JADX WARN: Code duplicated, block: B:314:0x0584  */
    /* JADX WARN: Code duplicated, block: B:315:0x0593  */
    /* JADX WARN: Code duplicated, block: B:316:0x0596  */
    /* JADX WARN: Code duplicated, block: B:319:0x05ae  */
    /* JADX WARN: Multi-variable type inference failed */
    public void addToSolver(LinearSystem linearSystem, boolean z) {
        boolean z2;
        boolean z3;
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        boolean zIsInHorizontalChain;
        boolean zIsInVerticalChain;
        int i;
        SolverVariable solverVariable;
        int i2;
        int i3;
        int i4;
        boolean z4;
        int i5;
        boolean z5;
        DimensionBehaviour dimensionBehaviour;
        DimensionBehaviour dimensionBehaviour2;
        boolean z6;
        SolverVariable solverVariable2;
        int i6;
        boolean z7;
        boolean z8;
        boolean z9;
        SolverVariable solverVariable3;
        SolverVariable solverVariable4;
        SolverVariable solverVariable5;
        int i7;
        char c;
        int i8;
        int i9;
        int i10;
        LinearSystem linearSystem2;
        boolean z10;
        VerticalWidgetRun verticalWidgetRun;
        HorizontalWidgetRun horizontalWidgetRun;
        int i11;
        int i12;
        int i13;
        HorizontalWidgetRun horizontalWidgetRun2;
        VerticalWidgetRun verticalWidgetRun2;
        LinearSystem linearSystem3 = linearSystem;
        SolverVariable solverVariableCreateObjectVariable = linearSystem3.createObjectVariable(this.mLeft);
        SolverVariable solverVariableCreateObjectVariable2 = linearSystem3.createObjectVariable(this.mRight);
        SolverVariable solverVariableCreateObjectVariable3 = linearSystem3.createObjectVariable(this.mTop);
        SolverVariable solverVariableCreateObjectVariable4 = linearSystem3.createObjectVariable(this.mBottom);
        SolverVariable solverVariableCreateObjectVariable5 = linearSystem3.createObjectVariable(this.mBaseline);
        ConstraintWidget constraintWidget3 = this.mParent;
        if (constraintWidget3 == null) {
            z2 = false;
            z3 = false;
        } else {
            z3 = constraintWidget3 != null && constraintWidget3.mListDimensionBehaviors[0] == DimensionBehaviour.WRAP_CONTENT;
            z2 = constraintWidget3 != null && constraintWidget3.mListDimensionBehaviors[1] == DimensionBehaviour.WRAP_CONTENT;
            int i14 = this.mWrapBehaviorInParent;
            if (i14 == 1) {
                z2 = false;
            } else if (i14 == 2) {
                z3 = false;
            } else if (i14 == 3) {
                z2 = false;
                z3 = false;
            }
        }
        if (this.mVisibility == 8 && !this.mAnimated && !hasDependencies()) {
            boolean[] zArr = this.mIsInBarrier;
            if (!zArr[0] && !zArr[1]) {
                return;
            }
        }
        boolean z11 = this.resolvedHorizontal;
        if (z11 || this.resolvedVertical) {
            if (z11) {
                linearSystem3.addEquality(solverVariableCreateObjectVariable, this.mX);
                linearSystem3.addEquality(solverVariableCreateObjectVariable2, this.mX + this.mWidth);
                if (z3 && (constraintWidget2 = this.mParent) != null) {
                    if (this.OPTIMIZE_WRAP_ON_RESOLVED) {
                        ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) constraintWidget2;
                        constraintWidgetContainer.addHorizontalWrapMinVariable(this.mLeft);
                        constraintWidgetContainer.addHorizontalWrapMaxVariable(this.mRight);
                    } else {
                        linearSystem3.addGreaterThan(linearSystem3.createObjectVariable(constraintWidget2.mRight), solverVariableCreateObjectVariable2, 0, 5);
                    }
                }
            }
            if (this.resolvedVertical) {
                linearSystem3.addEquality(solverVariableCreateObjectVariable3, this.mY);
                linearSystem3.addEquality(solverVariableCreateObjectVariable4, this.mY + this.mHeight);
                if (this.mBaseline.hasDependents()) {
                    linearSystem3.addEquality(solverVariableCreateObjectVariable5, this.mY + this.mBaselineDistance);
                }
                if (z2 && (constraintWidget = this.mParent) != null) {
                    if (this.OPTIMIZE_WRAP_ON_RESOLVED) {
                        ConstraintWidgetContainer constraintWidgetContainer2 = (ConstraintWidgetContainer) constraintWidget;
                        constraintWidgetContainer2.addVerticalWrapMinVariable(this.mTop);
                        constraintWidgetContainer2.addVerticalWrapMaxVariable(this.mBottom);
                    } else {
                        linearSystem3.addGreaterThan(linearSystem3.createObjectVariable(constraintWidget.mBottom), solverVariableCreateObjectVariable4, 0, 5);
                    }
                }
            }
            if (this.resolvedHorizontal && this.resolvedVertical) {
                this.resolvedHorizontal = false;
                this.resolvedVertical = false;
                return;
            }
        }
        Metrics metrics = LinearSystem.sMetrics;
        if (metrics != null) {
            metrics.widgets++;
        }
        if (z && (horizontalWidgetRun2 = this.horizontalRun) != null && (verticalWidgetRun2 = this.verticalRun) != null) {
            DependencyNode dependencyNode = horizontalWidgetRun2.start;
            if (dependencyNode.resolved && horizontalWidgetRun2.end.resolved && verticalWidgetRun2.start.resolved && verticalWidgetRun2.end.resolved) {
                if (metrics != null) {
                    metrics.graphSolved++;
                }
                linearSystem3.addEquality(solverVariableCreateObjectVariable, dependencyNode.value);
                linearSystem3.addEquality(solverVariableCreateObjectVariable2, this.horizontalRun.end.value);
                linearSystem3.addEquality(solverVariableCreateObjectVariable3, this.verticalRun.start.value);
                linearSystem3.addEquality(solverVariableCreateObjectVariable4, this.verticalRun.end.value);
                linearSystem3.addEquality(solverVariableCreateObjectVariable5, this.verticalRun.baseline.value);
                if (this.mParent != null) {
                    if (z3 && this.isTerminalWidget[0] && !isInHorizontalChain()) {
                        linearSystem3.addGreaterThan(linearSystem3.createObjectVariable(this.mParent.mRight), solverVariableCreateObjectVariable2, 0, 8);
                    }
                    if (z2 && this.isTerminalWidget[1] && !isInVerticalChain()) {
                        linearSystem3.addGreaterThan(linearSystem3.createObjectVariable(this.mParent.mBottom), solverVariableCreateObjectVariable4, 0, 8);
                    }
                }
                this.resolvedHorizontal = false;
                this.resolvedVertical = false;
                return;
            }
        }
        if (metrics != null) {
            metrics.linearSolved++;
        }
        if (this.mParent != null) {
            if (isChainHead(0)) {
                ((ConstraintWidgetContainer) this.mParent).addChain(this, 0);
                zIsInHorizontalChain = true;
                i13 = 1;
            } else {
                zIsInHorizontalChain = isInHorizontalChain();
                i13 = 1;
            }
            if (isChainHead(i13)) {
                ((ConstraintWidgetContainer) this.mParent).addChain(this, i13);
                zIsInVerticalChain = true;
            } else {
                zIsInVerticalChain = isInVerticalChain();
            }
            if (!zIsInHorizontalChain && z3 && this.mVisibility != 8 && this.mLeft.mTarget == null && this.mRight.mTarget == null) {
                linearSystem3.addGreaterThan(linearSystem3.createObjectVariable(this.mParent.mRight), solverVariableCreateObjectVariable2, 0, 1);
            }
            if (!zIsInVerticalChain && z2 && this.mVisibility != 8 && this.mTop.mTarget == null && this.mBottom.mTarget == null && this.mBaseline == null) {
                linearSystem3.addGreaterThan(linearSystem3.createObjectVariable(this.mParent.mBottom), solverVariableCreateObjectVariable4, 0, 1);
            }
        } else {
            zIsInHorizontalChain = false;
            zIsInVerticalChain = false;
        }
        int i15 = this.mWidth;
        int i16 = this.mMinWidth;
        if (i15 >= i16) {
            i16 = i15;
        }
        int i17 = this.mHeight;
        int i18 = this.mMinHeight;
        if (i17 >= i18) {
            i18 = i17;
        }
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour3 = dimensionBehaviourArr[0];
        SolverVariable solverVariable6 = solverVariableCreateObjectVariable4;
        DimensionBehaviour dimensionBehaviour4 = DimensionBehaviour.MATCH_CONSTRAINT;
        boolean z12 = dimensionBehaviour3 != dimensionBehaviour4;
        DimensionBehaviour dimensionBehaviour5 = dimensionBehaviourArr[1];
        boolean z13 = zIsInHorizontalChain;
        boolean z14 = dimensionBehaviour5 != dimensionBehaviour4;
        boolean z15 = zIsInVerticalChain;
        int i19 = this.mDimensionRatioSide;
        this.mResolvedDimensionRatioSide = i19;
        int i20 = i18;
        float f = this.mDimensionRatio;
        this.mResolvedDimensionRatio = f;
        int i21 = this.mMatchConstraintDefaultWidth;
        int i22 = this.mMatchConstraintDefaultHeight;
        if (f > 0.0f) {
            i = i16;
            if (this.mVisibility != 8) {
                i2 = (dimensionBehaviour3 == dimensionBehaviour4 && i21 == 0) ? 3 : i21;
                int i23 = (dimensionBehaviour5 == dimensionBehaviour4 && i22 == 0) ? 3 : i22;
                if (dimensionBehaviour3 == dimensionBehaviour4 && dimensionBehaviour5 == dimensionBehaviour4) {
                    solverVariable = solverVariableCreateObjectVariable2;
                    i12 = 3;
                    if (i2 == 3 && i23 == 3) {
                        setupDimensionRatio(z3, z2, z12, z14);
                    }
                    i20 = i20;
                    z4 = true;
                    i3 = i23;
                    i4 = i;
                    int[] iArr = this.mResolvedMatchConstraintDefault;
                    iArr[0] = i2;
                    iArr[1] = i3;
                    this.mResolvedHasRatio = z4;
                    if (z4) {
                        int i24 = this.mResolvedDimensionRatioSide;
                        i5 = -1;
                        boolean z16 = i24 != 0 || i24 == -1;
                        if (z4 || !((i11 = this.mResolvedDimensionRatioSide) == 1 || i11 == i5)) {
                            z5 = false;
                        } else {
                            z5 = true;
                        }
                        dimensionBehaviour = this.mListDimensionBehaviors[0];
                        dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
                        if (dimensionBehaviour == dimensionBehaviour2 || !(this instanceof ConstraintWidgetContainer)) {
                            z6 = false;
                        } else {
                            z6 = true;
                        }
                        if (z6) {
                            i4 = 0;
                        }
                        boolean z17 = !this.mCenter.isConnected();
                        boolean[] zArr2 = this.mIsInBarrier;
                        boolean z18 = zArr2[0];
                        boolean z19 = zArr2[1];
                        if (this.mHorizontalResolution != 2 || this.resolvedHorizontal) {
                            z9 = z3;
                            solverVariable2 = solverVariableCreateObjectVariable3;
                            z8 = z13;
                            z7 = z15;
                            i6 = i2;
                        } else {
                            if (z && (horizontalWidgetRun = this.horizontalRun) != null) {
                                DependencyNode dependencyNode2 = horizontalWidgetRun.start;
                                if (dependencyNode2.resolved && horizontalWidgetRun.end.resolved) {
                                    if (z) {
                                        linearSystem3.addEquality(solverVariableCreateObjectVariable, dependencyNode2.value);
                                        SolverVariable solverVariable7 = solverVariable;
                                        linearSystem3.addEquality(solverVariable7, this.horizontalRun.end.value);
                                        if (this.mParent != null && z3 && this.isTerminalWidget[0] && !isInHorizontalChain()) {
                                            linearSystem3.addGreaterThan(linearSystem3.createObjectVariable(this.mParent.mRight), solverVariable7, 0, 8);
                                        }
                                        solverVariable = solverVariable7;
                                    }
                                    z9 = z3;
                                    solverVariable2 = solverVariableCreateObjectVariable3;
                                    z8 = z13;
                                    z7 = z15;
                                    i6 = i2;
                                }
                            }
                            SolverVariable solverVariable8 = solverVariable;
                            ConstraintWidget constraintWidget4 = this.mParent;
                            SolverVariable solverVariableCreateObjectVariable6 = constraintWidget4 != null ? linearSystem3.createObjectVariable(constraintWidget4.mRight) : null;
                            ConstraintWidget constraintWidget5 = this.mParent;
                            SolverVariable solverVariableCreateObjectVariable7 = constraintWidget5 != null ? linearSystem3.createObjectVariable(constraintWidget5.mLeft) : null;
                            boolean z20 = this.isTerminalWidget[0];
                            DimensionBehaviour[] dimensionBehaviourArr2 = this.mListDimensionBehaviors;
                            DimensionBehaviour dimensionBehaviour6 = dimensionBehaviourArr2[0];
                            ConstraintAnchor constraintAnchor = this.mLeft;
                            i6 = i2;
                            ConstraintAnchor constraintAnchor2 = this.mRight;
                            z4 = z4;
                            z9 = z3;
                            int i25 = this.mX;
                            boolean z21 = z16;
                            SolverVariable solverVariable9 = solverVariableCreateObjectVariable7;
                            int i26 = this.mMinWidth;
                            int i27 = this.mMaxDimension[0];
                            float f2 = this.mHorizontalBiasPercent;
                            boolean z22 = dimensionBehaviourArr2[1] == dimensionBehaviour4;
                            solverVariableCreateObjectVariable = solverVariableCreateObjectVariable;
                            solverVariable6 = solverVariable6;
                            dimensionBehaviour4 = dimensionBehaviour4;
                            z2 = z2;
                            z8 = z13;
                            z7 = z15;
                            dimensionBehaviour2 = dimensionBehaviour2;
                            solverVariable = solverVariable8;
                            solverVariable2 = solverVariableCreateObjectVariable3;
                            linearSystem3 = linearSystem;
                            applyConstraints(linearSystem3, true, z9, z2, z20, solverVariable9, solverVariableCreateObjectVariable6, dimensionBehaviour6, z6, constraintAnchor, constraintAnchor2, i25, i4, i26, i27, f2, z21, z22, z8, z7, z18, i6, i3, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth, this.mMatchConstraintPercentWidth, z17);
                        }
                        if (z || (verticalWidgetRun = this.verticalRun) == null) {
                            solverVariable3 = solverVariable2;
                            solverVariable4 = solverVariable6;
                            solverVariable5 = r24;
                            i7 = 0;
                            c = 1;
                            i8 = 8;
                            i9 = 1;
                        } else {
                            DependencyNode dependencyNode3 = verticalWidgetRun.start;
                            if (dependencyNode3.resolved && verticalWidgetRun.end.resolved) {
                                int i28 = dependencyNode3.value;
                                solverVariable3 = solverVariable2;
                                linearSystem3.addEquality(solverVariable3, i28);
                                solverVariable4 = solverVariable6;
                                linearSystem3.addEquality(solverVariable4, this.verticalRun.end.value);
                                solverVariable5 = solverVariableCreateObjectVariable5;
                                linearSystem3.addEquality(solverVariable5, this.verticalRun.baseline.value);
                                ConstraintWidget constraintWidget6 = this.mParent;
                                if (constraintWidget6 == null || z7 || !z2) {
                                    i7 = 0;
                                    c = 1;
                                } else {
                                    c = 1;
                                    if (this.isTerminalWidget[1]) {
                                        i7 = 0;
                                        i8 = 8;
                                        linearSystem3.addGreaterThan(linearSystem3.createObjectVariable(constraintWidget6.mBottom), solverVariable4, 0, 8);
                                    } else {
                                        i7 = 0;
                                    }
                                    i9 = i7;
                                }
                                i8 = 8;
                                i9 = i7;
                            } else {
                                solverVariable3 = solverVariable2;
                                solverVariable4 = solverVariable6;
                                solverVariable5 = r24;
                                i7 = 0;
                                c = 1;
                                i8 = 8;
                                i9 = 1;
                            }
                        }
                        if (this.mVerticalResolution == 2) {
                            i10 = i7;
                        } else {
                            i10 = i9;
                        }
                        if (i10 == 0 && !this.resolvedVertical) {
                            boolean z23 = (this.mListDimensionBehaviors[c] == dimensionBehaviour2 && (this instanceof ConstraintWidgetContainer)) ? c : i7;
                            int i29 = z23 != 0 ? i7 : i20;
                            ConstraintWidget constraintWidget7 = this.mParent;
                            SolverVariable solverVariableCreateObjectVariable8 = constraintWidget7 != null ? linearSystem3.createObjectVariable(constraintWidget7.mBottom) : null;
                            ConstraintWidget constraintWidget8 = this.mParent;
                            SolverVariable solverVariableCreateObjectVariable9 = constraintWidget8 != null ? linearSystem3.createObjectVariable(constraintWidget8.mTop) : null;
                            if (this.mBaselineDistance > 0 || this.mVisibility == i8) {
                                z10 = z17;
                                ConstraintAnchor constraintAnchor3 = this.mBaseline;
                                if (constraintAnchor3.mTarget != null) {
                                    linearSystem3.addEquality(solverVariable5, solverVariable3, getBaselineDistance(), i8);
                                    linearSystem3.addEquality(solverVariable5, linearSystem3.createObjectVariable(this.mBaseline.mTarget), this.mBaseline.getMargin(), i8);
                                    if (z2) {
                                        linearSystem3.addGreaterThan(solverVariableCreateObjectVariable8, linearSystem3.createObjectVariable(this.mBottom), i7, 5);
                                    }
                                    z10 = i7;
                                } else if (this.mVisibility == i8) {
                                    linearSystem3.addEquality(solverVariable5, solverVariable3, constraintAnchor3.getMargin(), i8);
                                    z10 = z17;
                                } else {
                                    linearSystem3.addEquality(solverVariable5, solverVariable3, getBaselineDistance(), i8);
                                    z10 = z17;
                                }
                            }
                            z10 = z17;
                            boolean z24 = this.isTerminalWidget[c];
                            DimensionBehaviour[] dimensionBehaviourArr3 = this.mListDimensionBehaviors;
                            int i30 = i7;
                            char c2 = c;
                            applyConstraints(linearSystem, false, z2, z9, z24, solverVariableCreateObjectVariable9, solverVariableCreateObjectVariable8, dimensionBehaviourArr3[c], z23, this.mTop, this.mBottom, this.mY, i29, this.mMinHeight, this.mMaxDimension[c2], this.mVerticalBiasPercent, z5, dimensionBehaviourArr3[i30] == dimensionBehaviour4 ? c2 : i30, z7, z8, z19, i3, i6, this.mMatchConstraintMinHeight, this.mMatchConstraintMaxHeight, this.mMatchConstraintPercentHeight, z10);
                        }
                        if (!z4) {
                            linearSystem2 = linearSystem;
                        } else if (this.mResolvedDimensionRatioSide == 1) {
                            linearSystem.addRatio(solverVariable4, solverVariable3, solverVariable, solverVariableCreateObjectVariable, this.mResolvedDimensionRatio, 8);
                            linearSystem2 = linearSystem;
                        } else {
                            linearSystem.addRatio(solverVariable, solverVariableCreateObjectVariable, solverVariable4, solverVariable3, this.mResolvedDimensionRatio, 8);
                            linearSystem2 = linearSystem;
                        }
                        if (this.mCenter.isConnected()) {
                            linearSystem2.addCenterPoint(this, this.mCenter.getTarget().getOwner(), (float) Math.toRadians(this.mCircleConstraintAngle + 90.0f), this.mCenter.getMargin());
                        }
                        this.resolvedHorizontal = false;
                        this.resolvedVertical = false;
                    }
                    i5 = -1;
                    if (z4) {
                        z5 = false;
                    } else {
                        z5 = false;
                    }
                    dimensionBehaviour = this.mListDimensionBehaviors[0];
                    dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
                    if (dimensionBehaviour == dimensionBehaviour2) {
                        z6 = false;
                    } else {
                        z6 = false;
                    }
                    if (z6) {
                        i4 = 0;
                    }
                    boolean z110 = !this.mCenter.isConnected();
                    boolean[] zArr3 = this.mIsInBarrier;
                    boolean z111 = zArr3[0];
                    boolean z112 = zArr3[1];
                    if (this.mHorizontalResolution != 2) {
                        z9 = z3;
                        solverVariable2 = solverVariableCreateObjectVariable3;
                        z8 = z13;
                        z7 = z15;
                        i6 = i2;
                    } else {
                        z9 = z3;
                        solverVariable2 = solverVariableCreateObjectVariable3;
                        z8 = z13;
                        z7 = z15;
                        i6 = i2;
                    }
                    if (z) {
                        solverVariable3 = solverVariable2;
                        solverVariable4 = solverVariable6;
                        solverVariable5 = r24;
                        i7 = 0;
                        c = 1;
                        i8 = 8;
                        i9 = 1;
                    } else {
                        solverVariable3 = solverVariable2;
                        solverVariable4 = solverVariable6;
                        solverVariable5 = r24;
                        i7 = 0;
                        c = 1;
                        i8 = 8;
                        i9 = 1;
                    }
                    if (this.mVerticalResolution == 2) {
                        i10 = i7;
                    } else {
                        i10 = i9;
                    }
                    if (i10 == 0) {
                    }
                    if (!z4) {
                        linearSystem2 = linearSystem;
                    } else if (this.mResolvedDimensionRatioSide == 1) {
                        linearSystem.addRatio(solverVariable4, solverVariable3, solverVariable, solverVariableCreateObjectVariable, this.mResolvedDimensionRatio, 8);
                        linearSystem2 = linearSystem;
                    } else {
                        linearSystem.addRatio(solverVariable, solverVariableCreateObjectVariable, solverVariable4, solverVariable3, this.mResolvedDimensionRatio, 8);
                        linearSystem2 = linearSystem;
                    }
                    if (this.mCenter.isConnected()) {
                        linearSystem2.addCenterPoint(this, this.mCenter.getTarget().getOwner(), (float) Math.toRadians(this.mCircleConstraintAngle + 90.0f), this.mCenter.getMargin());
                    }
                    this.resolvedHorizontal = false;
                    this.resolvedVertical = false;
                }
                solverVariable = solverVariableCreateObjectVariable2;
                i12 = 3;
                if (dimensionBehaviour3 == dimensionBehaviour4 && i2 == i12) {
                    this.mResolvedDimensionRatioSide = 0;
                    int i31 = (int) (i17 * f);
                    if (dimensionBehaviour5 != dimensionBehaviour4) {
                        i2 = 4;
                        i3 = i23;
                        i4 = i31;
                    } else {
                        solverVariable6 = solverVariable6;
                        i20 = i20;
                        i3 = i23;
                        i4 = i31;
                        z4 = true;
                    }
                } else {
                    if (dimensionBehaviour5 == dimensionBehaviour4 && i23 == i12) {
                        this.mResolvedDimensionRatioSide = 1;
                        if (i19 == -1) {
                            this.mResolvedDimensionRatio = 1.0f / f;
                        }
                        i20 = (int) (this.mResolvedDimensionRatio * i15);
                        if (dimensionBehaviour3 != dimensionBehaviour4) {
                            solverVariable6 = solverVariable6;
                            i4 = i;
                            z4 = false;
                            i3 = 4;
                        }
                    } else {
                        i20 = i20;
                    }
                    z4 = true;
                    i3 = i23;
                    i4 = i;
                }
                int[] iArr2 = this.mResolvedMatchConstraintDefault;
                iArr2[0] = i2;
                iArr2[1] = i3;
                this.mResolvedHasRatio = z4;
                if (z4) {
                    int i210 = this.mResolvedDimensionRatioSide;
                    i5 = -1;
                    if (i210 != 0) {
                    }
                    if (z4) {
                        z5 = false;
                    } else {
                        z5 = false;
                    }
                    dimensionBehaviour = this.mListDimensionBehaviors[0];
                    dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
                    if (dimensionBehaviour == dimensionBehaviour2) {
                        z6 = false;
                    } else {
                        z6 = false;
                    }
                    if (z6) {
                        i4 = 0;
                    }
                    boolean z113 = !this.mCenter.isConnected();
                    boolean[] zArr4 = this.mIsInBarrier;
                    boolean z114 = zArr4[0];
                    boolean z115 = zArr4[1];
                    if (this.mHorizontalResolution != 2) {
                        z9 = z3;
                        solverVariable2 = solverVariableCreateObjectVariable3;
                        z8 = z13;
                        z7 = z15;
                        i6 = i2;
                    } else {
                        z9 = z3;
                        solverVariable2 = solverVariableCreateObjectVariable3;
                        z8 = z13;
                        z7 = z15;
                        i6 = i2;
                    }
                    if (z) {
                        solverVariable3 = solverVariable2;
                        solverVariable4 = solverVariable6;
                        solverVariable5 = r24;
                        i7 = 0;
                        c = 1;
                        i8 = 8;
                        i9 = 1;
                    } else {
                        solverVariable3 = solverVariable2;
                        solverVariable4 = solverVariable6;
                        solverVariable5 = r24;
                        i7 = 0;
                        c = 1;
                        i8 = 8;
                        i9 = 1;
                    }
                    if (this.mVerticalResolution == 2) {
                        i10 = i7;
                    } else {
                        i10 = i9;
                    }
                    if (i10 == 0) {
                    }
                    if (!z4) {
                        linearSystem2 = linearSystem;
                    } else if (this.mResolvedDimensionRatioSide == 1) {
                        linearSystem.addRatio(solverVariable4, solverVariable3, solverVariable, solverVariableCreateObjectVariable, this.mResolvedDimensionRatio, 8);
                        linearSystem2 = linearSystem;
                    } else {
                        linearSystem.addRatio(solverVariable, solverVariableCreateObjectVariable, solverVariable4, solverVariable3, this.mResolvedDimensionRatio, 8);
                        linearSystem2 = linearSystem;
                    }
                    if (this.mCenter.isConnected()) {
                        linearSystem2.addCenterPoint(this, this.mCenter.getTarget().getOwner(), (float) Math.toRadians(this.mCircleConstraintAngle + 90.0f), this.mCenter.getMargin());
                    }
                    this.resolvedHorizontal = false;
                    this.resolvedVertical = false;
                }
                i5 = -1;
                if (z4) {
                    z5 = false;
                } else {
                    z5 = false;
                }
                dimensionBehaviour = this.mListDimensionBehaviors[0];
                dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
                if (dimensionBehaviour == dimensionBehaviour2) {
                    z6 = false;
                } else {
                    z6 = false;
                }
                if (z6) {
                    i4 = 0;
                }
                boolean z116 = !this.mCenter.isConnected();
                boolean[] zArr5 = this.mIsInBarrier;
                boolean z117 = zArr5[0];
                boolean z118 = zArr5[1];
                if (this.mHorizontalResolution != 2) {
                    z9 = z3;
                    solverVariable2 = solverVariableCreateObjectVariable3;
                    z8 = z13;
                    z7 = z15;
                    i6 = i2;
                } else {
                    z9 = z3;
                    solverVariable2 = solverVariableCreateObjectVariable3;
                    z8 = z13;
                    z7 = z15;
                    i6 = i2;
                }
                if (z) {
                    solverVariable3 = solverVariable2;
                    solverVariable4 = solverVariable6;
                    solverVariable5 = r24;
                    i7 = 0;
                    c = 1;
                    i8 = 8;
                    i9 = 1;
                } else {
                    solverVariable3 = solverVariable2;
                    solverVariable4 = solverVariable6;
                    solverVariable5 = r24;
                    i7 = 0;
                    c = 1;
                    i8 = 8;
                    i9 = 1;
                }
                if (this.mVerticalResolution == 2) {
                    i10 = i7;
                } else {
                    i10 = i9;
                }
                if (i10 == 0) {
                }
                if (!z4) {
                    linearSystem2 = linearSystem;
                } else if (this.mResolvedDimensionRatioSide == 1) {
                    linearSystem.addRatio(solverVariable4, solverVariable3, solverVariable, solverVariableCreateObjectVariable, this.mResolvedDimensionRatio, 8);
                    linearSystem2 = linearSystem;
                } else {
                    linearSystem.addRatio(solverVariable, solverVariableCreateObjectVariable, solverVariable4, solverVariable3, this.mResolvedDimensionRatio, 8);
                    linearSystem2 = linearSystem;
                }
                if (this.mCenter.isConnected()) {
                    linearSystem2.addCenterPoint(this, this.mCenter.getTarget().getOwner(), (float) Math.toRadians(this.mCircleConstraintAngle + 90.0f), this.mCenter.getMargin());
                }
                this.resolvedHorizontal = false;
                this.resolvedVertical = false;
            }
            z4 = false;
            int[] iArr3 = this.mResolvedMatchConstraintDefault;
            iArr3[0] = i2;
            iArr3[1] = i3;
            this.mResolvedHasRatio = z4;
            if (z4) {
                int i211 = this.mResolvedDimensionRatioSide;
                i5 = -1;
                if (i211 != 0) {
                }
                if (z4) {
                    z5 = false;
                } else {
                    z5 = false;
                }
                dimensionBehaviour = this.mListDimensionBehaviors[0];
                dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
                if (dimensionBehaviour == dimensionBehaviour2) {
                    z6 = false;
                } else {
                    z6 = false;
                }
                if (z6) {
                    i4 = 0;
                }
                boolean z119 = !this.mCenter.isConnected();
                boolean[] zArr6 = this.mIsInBarrier;
                boolean z1110 = zArr6[0];
                boolean z1111 = zArr6[1];
                if (this.mHorizontalResolution != 2) {
                    z9 = z3;
                    solverVariable2 = solverVariableCreateObjectVariable3;
                    z8 = z13;
                    z7 = z15;
                    i6 = i2;
                } else {
                    z9 = z3;
                    solverVariable2 = solverVariableCreateObjectVariable3;
                    z8 = z13;
                    z7 = z15;
                    i6 = i2;
                }
                if (z) {
                    solverVariable3 = solverVariable2;
                    solverVariable4 = solverVariable6;
                    solverVariable5 = r24;
                    i7 = 0;
                    c = 1;
                    i8 = 8;
                    i9 = 1;
                } else {
                    solverVariable3 = solverVariable2;
                    solverVariable4 = solverVariable6;
                    solverVariable5 = r24;
                    i7 = 0;
                    c = 1;
                    i8 = 8;
                    i9 = 1;
                }
                if (this.mVerticalResolution == 2) {
                    i10 = i7;
                } else {
                    i10 = i9;
                }
                if (i10 == 0) {
                }
                if (!z4) {
                    linearSystem2 = linearSystem;
                } else if (this.mResolvedDimensionRatioSide == 1) {
                    linearSystem.addRatio(solverVariable4, solverVariable3, solverVariable, solverVariableCreateObjectVariable, this.mResolvedDimensionRatio, 8);
                    linearSystem2 = linearSystem;
                } else {
                    linearSystem.addRatio(solverVariable, solverVariableCreateObjectVariable, solverVariable4, solverVariable3, this.mResolvedDimensionRatio, 8);
                    linearSystem2 = linearSystem;
                }
                if (this.mCenter.isConnected()) {
                    linearSystem2.addCenterPoint(this, this.mCenter.getTarget().getOwner(), (float) Math.toRadians(this.mCircleConstraintAngle + 90.0f), this.mCenter.getMargin());
                }
                this.resolvedHorizontal = false;
                this.resolvedVertical = false;
            }
            i5 = -1;
            if (z4) {
                z5 = false;
            } else {
                z5 = false;
            }
            dimensionBehaviour = this.mListDimensionBehaviors[0];
            dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
            if (dimensionBehaviour == dimensionBehaviour2) {
                z6 = false;
            } else {
                z6 = false;
            }
            if (z6) {
                i4 = 0;
            }
            boolean z1112 = !this.mCenter.isConnected();
            boolean[] zArr7 = this.mIsInBarrier;
            boolean z1113 = zArr7[0];
            boolean z1114 = zArr7[1];
            if (this.mHorizontalResolution != 2) {
                z9 = z3;
                solverVariable2 = solverVariableCreateObjectVariable3;
                z8 = z13;
                z7 = z15;
                i6 = i2;
            } else {
                z9 = z3;
                solverVariable2 = solverVariableCreateObjectVariable3;
                z8 = z13;
                z7 = z15;
                i6 = i2;
            }
            if (z) {
                solverVariable3 = solverVariable2;
                solverVariable4 = solverVariable6;
                solverVariable5 = r24;
                i7 = 0;
                c = 1;
                i8 = 8;
                i9 = 1;
            } else {
                solverVariable3 = solverVariable2;
                solverVariable4 = solverVariable6;
                solverVariable5 = r24;
                i7 = 0;
                c = 1;
                i8 = 8;
                i9 = 1;
            }
            if (this.mVerticalResolution == 2) {
                i10 = i7;
            } else {
                i10 = i9;
            }
            if (i10 == 0) {
            }
            if (!z4) {
                linearSystem2 = linearSystem;
            } else if (this.mResolvedDimensionRatioSide == 1) {
                linearSystem.addRatio(solverVariable4, solverVariable3, solverVariable, solverVariableCreateObjectVariable, this.mResolvedDimensionRatio, 8);
                linearSystem2 = linearSystem;
            } else {
                linearSystem.addRatio(solverVariable, solverVariableCreateObjectVariable, solverVariable4, solverVariable3, this.mResolvedDimensionRatio, 8);
                linearSystem2 = linearSystem;
            }
            if (this.mCenter.isConnected()) {
                linearSystem2.addCenterPoint(this, this.mCenter.getTarget().getOwner(), (float) Math.toRadians(this.mCircleConstraintAngle + 90.0f), this.mCenter.getMargin());
            }
            this.resolvedHorizontal = false;
            this.resolvedVertical = false;
        }
        i = i16;
        solverVariable = solverVariableCreateObjectVariable2;
        i2 = i21;
        i3 = i22;
        i4 = i;
        z4 = false;
        int[] iArr4 = this.mResolvedMatchConstraintDefault;
        iArr4[0] = i2;
        iArr4[1] = i3;
        this.mResolvedHasRatio = z4;
        if (z4) {
            int i212 = this.mResolvedDimensionRatioSide;
            i5 = -1;
            if (i212 != 0) {
            }
            if (z4) {
                z5 = false;
            } else {
                z5 = false;
            }
            dimensionBehaviour = this.mListDimensionBehaviors[0];
            dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
            if (dimensionBehaviour == dimensionBehaviour2) {
                z6 = false;
            } else {
                z6 = false;
            }
            if (z6) {
                i4 = 0;
            }
            boolean z1115 = !this.mCenter.isConnected();
            boolean[] zArr8 = this.mIsInBarrier;
            boolean z1116 = zArr8[0];
            boolean z1117 = zArr8[1];
            if (this.mHorizontalResolution != 2) {
                z9 = z3;
                solverVariable2 = solverVariableCreateObjectVariable3;
                z8 = z13;
                z7 = z15;
                i6 = i2;
            } else {
                z9 = z3;
                solverVariable2 = solverVariableCreateObjectVariable3;
                z8 = z13;
                z7 = z15;
                i6 = i2;
            }
            if (z) {
                solverVariable3 = solverVariable2;
                solverVariable4 = solverVariable6;
                solverVariable5 = r24;
                i7 = 0;
                c = 1;
                i8 = 8;
                i9 = 1;
            } else {
                solverVariable3 = solverVariable2;
                solverVariable4 = solverVariable6;
                solverVariable5 = r24;
                i7 = 0;
                c = 1;
                i8 = 8;
                i9 = 1;
            }
            if (this.mVerticalResolution == 2) {
                i10 = i7;
            } else {
                i10 = i9;
            }
            if (i10 == 0) {
            }
            if (!z4) {
                linearSystem2 = linearSystem;
            } else if (this.mResolvedDimensionRatioSide == 1) {
                linearSystem.addRatio(solverVariable4, solverVariable3, solverVariable, solverVariableCreateObjectVariable, this.mResolvedDimensionRatio, 8);
                linearSystem2 = linearSystem;
            } else {
                linearSystem.addRatio(solverVariable, solverVariableCreateObjectVariable, solverVariable4, solverVariable3, this.mResolvedDimensionRatio, 8);
                linearSystem2 = linearSystem;
            }
            if (this.mCenter.isConnected()) {
                linearSystem2.addCenterPoint(this, this.mCenter.getTarget().getOwner(), (float) Math.toRadians(this.mCircleConstraintAngle + 90.0f), this.mCenter.getMargin());
            }
            this.resolvedHorizontal = false;
            this.resolvedVertical = false;
        }
        i5 = -1;
        if (z4) {
            z5 = false;
        } else {
            z5 = false;
        }
        dimensionBehaviour = this.mListDimensionBehaviors[0];
        dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
        if (dimensionBehaviour == dimensionBehaviour2) {
            z6 = false;
        } else {
            z6 = false;
        }
        if (z6) {
            i4 = 0;
        }
        boolean z1118 = !this.mCenter.isConnected();
        boolean[] zArr9 = this.mIsInBarrier;
        boolean z1119 = zArr9[0];
        boolean z11110 = zArr9[1];
        if (this.mHorizontalResolution != 2) {
            z9 = z3;
            solverVariable2 = solverVariableCreateObjectVariable3;
            z8 = z13;
            z7 = z15;
            i6 = i2;
        } else {
            z9 = z3;
            solverVariable2 = solverVariableCreateObjectVariable3;
            z8 = z13;
            z7 = z15;
            i6 = i2;
        }
        if (z) {
            solverVariable3 = solverVariable2;
            solverVariable4 = solverVariable6;
            solverVariable5 = r24;
            i7 = 0;
            c = 1;
            i8 = 8;
            i9 = 1;
        } else {
            solverVariable3 = solverVariable2;
            solverVariable4 = solverVariable6;
            solverVariable5 = r24;
            i7 = 0;
            c = 1;
            i8 = 8;
            i9 = 1;
        }
        if (this.mVerticalResolution == 2) {
            i10 = i7;
        } else {
            i10 = i9;
        }
        if (i10 == 0) {
        }
        if (!z4) {
            linearSystem2 = linearSystem;
        } else if (this.mResolvedDimensionRatioSide == 1) {
            linearSystem.addRatio(solverVariable4, solverVariable3, solverVariable, solverVariableCreateObjectVariable, this.mResolvedDimensionRatio, 8);
            linearSystem2 = linearSystem;
        } else {
            linearSystem.addRatio(solverVariable, solverVariableCreateObjectVariable, solverVariable4, solverVariable3, this.mResolvedDimensionRatio, 8);
            linearSystem2 = linearSystem;
        }
        if (this.mCenter.isConnected()) {
            linearSystem2.addCenterPoint(this, this.mCenter.getTarget().getOwner(), (float) Math.toRadians(this.mCircleConstraintAngle + 90.0f), this.mCenter.getMargin());
        }
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
    }

    public boolean allowedInBarrier() {
        return this.mVisibility != 8;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x015b  */
    /* JADX WARN: Code duplicated, block: B:108:0x0186  */
    /* JADX WARN: Code duplicated, block: B:111:0x01b6  */
    /* JADX WARN: Code duplicated, block: B:114:0x01c1  */
    /* JADX WARN: Code duplicated, block: B:116:0x01d0 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:286:0x047d  */
    /* JADX WARN: Code duplicated, block: B:290:0x0484  */
    /* JADX WARN: Code duplicated, block: B:310:0x04b0  */
    /* JADX WARN: Code duplicated, block: B:317:0x04cd  */
    /* JADX WARN: Code duplicated, block: B:319:0x04d1 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:320:0x04d3  */
    /* JADX WARN: Code duplicated, block: B:321:0x04d8  */
    /* JADX WARN: Code duplicated, block: B:323:0x04db  */
    /* JADX WARN: Code duplicated, block: B:332:0x04f2  */
    /* JADX WARN: Code duplicated, block: B:33:0x008a  */
    /* JADX WARN: Code duplicated, block: B:357:0x0529 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:376:0x0560  */
    /* JADX WARN: Code duplicated, block: B:387:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:38:0x0098  */
    /* JADX WARN: Code duplicated, block: B:41:0x009f  */
    /* JADX WARN: Code duplicated, block: B:43:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:47:0x00b0 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:48:0x00b2 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:51:0x00bd A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:52:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:54:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:57:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:58:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:61:0x00e4  */
    /* JADX WARN: Code duplicated, block: B:73:0x010a  */
    /* JADX WARN: Code duplicated, block: B:74:0x010c  */
    /* JADX WARN: Code duplicated, block: B:76:0x010f  */
    /* JADX WARN: Code duplicated, block: B:77:0x0111  */
    /* JADX WARN: Code duplicated, block: B:84:0x011c  */
    /* JADX WARN: Code duplicated, block: B:87:0x0126  */
    /* JADX WARN: Code duplicated, block: B:90:0x012b  */
    /* JADX WARN: Code duplicated, block: B:93:0x0134 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:94:0x0136  */
    /* JADX WARN: Code duplicated, block: B:95:0x013a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:96:0x013c  */
    /* JADX WARN: Code duplicated, block: B:97:0x0144  */
    /* JADX WARN: Code duplicated, block: B:99:0x0158  */
    public final void applyConstraints(LinearSystem linearSystem, boolean z, boolean z2, boolean z3, boolean z4, SolverVariable solverVariable, SolverVariable solverVariable2, DimensionBehaviour dimensionBehaviour, boolean z5, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i, int i2, int i3, int i4, float f, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, int i5, int i6, int i7, int i8, float f2, boolean z11) {
        int i9;
        boolean z12;
        int i10;
        SolverVariable solverVariable3;
        int iMin;
        int i11;
        int i12;
        boolean z13;
        ConstraintAnchor.Type type;
        ConstraintAnchor.Type type2;
        SolverVariable solverVariableCreateObjectVariable;
        SolverVariable solverVariableCreateObjectVariable2;
        boolean z14;
        ConstraintAnchor constraintAnchor3;
        int i13;
        int i14;
        int i15;
        boolean z15;
        boolean z16;
        boolean z17;
        boolean z18;
        SolverVariable solverVariable4;
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        ConstraintWidget constraintWidget3;
        ConstraintWidget constraintWidget4;
        int iMin2;
        int i16;
        boolean z19;
        int i17;
        int margin;
        int i18;
        int i19;
        int i20;
        int i21;
        int i22;
        int i23;
        boolean z20;
        ConstraintWidget constraintWidget5;
        boolean z21;
        ConstraintWidget constraintWidget6;
        linearSystem = linearSystem;
        int i24 = i8;
        SolverVariable solverVariableCreateObjectVariable3 = linearSystem.createObjectVariable(constraintAnchor);
        SolverVariable solverVariableCreateObjectVariable4 = linearSystem.createObjectVariable(constraintAnchor2);
        SolverVariable solverVariableCreateObjectVariable5 = linearSystem.createObjectVariable(constraintAnchor.getTarget());
        SolverVariable solverVariableCreateObjectVariable6 = linearSystem.createObjectVariable(constraintAnchor2.getTarget());
        if (LinearSystem.getMetrics() != null) {
            LinearSystem.getMetrics().nonresolvedWidgets++;
        }
        boolean zIsConnected = constraintAnchor.isConnected();
        boolean zIsConnected2 = constraintAnchor2.isConnected();
        boolean zIsConnected3 = this.mCenter.isConnected();
        int i25 = zIsConnected2 ? (zIsConnected ? 1 : 0) + 1 : zIsConnected ? 1 : 0;
        if (zIsConnected3) {
            i25++;
        }
        int i26 = z6 ? 3 : i5;
        int i27 = AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[dimensionBehaviour.ordinal()];
        if (i27 != 1 && i27 != 2 && i27 != 3 && i27 == 4) {
            i9 = i26;
            z12 = i9 != 4;
            i10 = this.mWidthOverride;
            if (i10 == -1 && z) {
                this.mWidthOverride = -1;
                z12 = false;
            } else {
                i10 = i2;
            }
            solverVariable3 = solverVariableCreateObjectVariable6;
            iMin = this.mHeightOverride;
            if (iMin != -1 || z) {
                iMin = i10;
            } else {
                this.mHeightOverride = -1;
                z12 = false;
            }
            if (this.mVisibility == 8) {
                iMin = 0;
                z12 = false;
            }
            if (z11) {
                if (zIsConnected && !zIsConnected2 && !zIsConnected3) {
                    linearSystem.addEquality(solverVariableCreateObjectVariable3, i);
                } else if (zIsConnected && !zIsConnected2) {
                    linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), 8);
                }
            }
            if (!z12) {
                if (i25 == 2 && !z6 && (i9 == 1 || i9 == 0)) {
                    int iMax = Math.max(i7, iMin);
                    if (i24 > 0) {
                        iMax = Math.min(i24, iMax);
                    }
                    linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMax, 8);
                    z13 = false;
                } else {
                    if (i7 == -2) {
                        i11 = iMin;
                    } else {
                        i11 = i7;
                    }
                    if (i24 == -2) {
                        i12 = iMin;
                    } else {
                        i12 = i24;
                    }
                    if (iMin > 0 && i9 != 1) {
                        iMin = 0;
                    }
                    if (i11 > 0) {
                        linearSystem.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i11, 8);
                        iMin = Math.max(iMin, i11);
                    }
                    if (i12 > 0) {
                        if (z2 || i9 != 1) {
                            linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i12, 8);
                        }
                        iMin = Math.min(iMin, i12);
                    }
                    if (i9 == 1) {
                        if (z2) {
                            linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 8);
                        } else if (z8) {
                            linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 5);
                            linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 8);
                        } else {
                            linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 5);
                            linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 8);
                        }
                        solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                        z13 = z12;
                        solverVariable3 = solverVariable3;
                        z4 = z4;
                        i11 = i11;
                        i24 = i12;
                        i25 = i25 == true ? 1 : 0;
                    } else {
                        if (i9 == 2) {
                            type = constraintAnchor.getType();
                            type2 = ConstraintAnchor.Type.TOP;
                            if (type != type2 || constraintAnchor.getType() == ConstraintAnchor.Type.BOTTOM) {
                                solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(type2));
                                solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                            } else {
                                solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
                                solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.RIGHT));
                            }
                            SolverVariable solverVariable5 = solverVariableCreateObjectVariable2;
                            solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                            linearSystem.addConstraint(linearSystem.createRow().createRowDimensionRatio(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, solverVariable5, solverVariableCreateObjectVariable, f2));
                            if (z2) {
                                z12 = false;
                            }
                            z13 = z12;
                            z4 = z4;
                        } else {
                            solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                            z13 = z12;
                            z4 = true;
                        }
                        i24 = i12;
                    }
                }
                if (z11 || z8) {
                    if (i25 >= 2 && z2 && z4) {
                        linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariable, 0, 8);
                        boolean z22 = z || this.mBaseline.mTarget == null;
                        if (z || (constraintAnchor3 = this.mBaseline.mTarget) == null) {
                            z14 = z22;
                        } else {
                            ConstraintWidget constraintWidget7 = constraintAnchor3.mOwner;
                            if (constraintWidget7.mDimensionRatio != 0.0f) {
                                DimensionBehaviour[] dimensionBehaviourArr = constraintWidget7.mListDimensionBehaviors;
                                DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[0];
                                DimensionBehaviour dimensionBehaviour3 = DimensionBehaviour.MATCH_CONSTRAINT;
                                if (dimensionBehaviour2 == dimensionBehaviour3) {
                                    z14 = true;
                                    if (dimensionBehaviourArr[1] != dimensionBehaviour3) {
                                        z14 = false;
                                    }
                                } else {
                                    z14 = false;
                                }
                            } else {
                                z14 = false;
                            }
                        }
                        if (z14) {
                            linearSystem.addGreaterThan(solverVariable2, solverVariableCreateObjectVariable4, 0, 8);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (!zIsConnected && !zIsConnected2 && !zIsConnected3) {
                    i17 = 5;
                    solverVariable2 = solverVariable2;
                    z21 = z2;
                } else if (!zIsConnected || zIsConnected2) {
                    if (zIsConnected || !zIsConnected2) {
                        if (zIsConnected && zIsConnected2) {
                            ConstraintWidget constraintWidget8 = constraintAnchor.mTarget.mOwner;
                            ConstraintWidget constraintWidget9 = constraintAnchor2.mTarget.mOwner;
                            ConstraintWidget parent = getParent();
                            int i28 = 6;
                            if (!z13) {
                                i13 = 8;
                                if (solverVariableCreateObjectVariable5.isFinalValue && solverVariable3.isFinalValue) {
                                    SolverVariable solverVariable6 = solverVariable3;
                                    linearSystem.addCentering(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), f, solverVariable6, solverVariableCreateObjectVariable4, constraintAnchor2.getMargin(), 8);
                                    if (z2 && z4) {
                                        int margin2 = constraintAnchor2.mTarget != null ? constraintAnchor2.getMargin() : 0;
                                        if (solverVariable6 != solverVariable2) {
                                            linearSystem.addGreaterThan(solverVariable2, solverVariableCreateObjectVariable4, margin2, 5);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                }
                                linearSystem = linearSystem;
                                solverVariableCreateObjectVariable3 = solverVariableCreateObjectVariable3;
                                solverVariable3 = solverVariable3;
                                solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                                i28 = 6;
                                i14 = 4;
                                i15 = 5;
                                z15 = true;
                                z16 = true;
                                z17 = false;
                            } else if (i9 == 0) {
                                if (i24 != 0 || i11 != 0) {
                                    i22 = 8;
                                    z17 = false;
                                    i23 = 5;
                                    i14 = 5;
                                    z20 = true;
                                    z16 = true;
                                } else {
                                    if (solverVariableCreateObjectVariable5.isFinalValue && solverVariable3.isFinalValue) {
                                        linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), 8);
                                        linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariable3, -constraintAnchor2.getMargin(), 8);
                                        return;
                                    }
                                    i22 = 8;
                                    z20 = false;
                                    z16 = false;
                                    i23 = 8;
                                    i14 = 8;
                                    z17 = true;
                                }
                                if ((constraintWidget8 instanceof Barrier) || (constraintWidget9 instanceof Barrier)) {
                                    i14 = 4;
                                }
                                z15 = z20;
                                i15 = i23;
                                i13 = i22;
                                solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                                i28 = 6;
                            } else {
                                i13 = 8;
                                if (i9 == 2) {
                                    i14 = ((constraintWidget8 instanceof Barrier) || (constraintWidget9 instanceof Barrier)) ? 4 : 5;
                                    i15 = 5;
                                } else if (i9 == 1) {
                                    solverVariableCreateObjectVariable3 = solverVariableCreateObjectVariable3;
                                    solverVariable3 = solverVariable3;
                                    solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                                    i28 = 6;
                                    i15 = 8;
                                    i14 = 4;
                                } else if (i9 == 3) {
                                    if (this.mResolvedDimensionRatioSide == -1) {
                                        if (z9) {
                                            linearSystem = linearSystem;
                                            solverVariableCreateObjectVariable3 = solverVariableCreateObjectVariable3;
                                            solverVariable3 = solverVariable3;
                                            solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                                            i15 = 8;
                                            i28 = z2 ? 5 : 4;
                                        } else {
                                            linearSystem = linearSystem;
                                            solverVariableCreateObjectVariable3 = solverVariableCreateObjectVariable3;
                                            solverVariable3 = solverVariable3;
                                            solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                                            i28 = 8;
                                            i15 = 8;
                                        }
                                        i14 = 5;
                                    } else {
                                        if (z6) {
                                            if (i6 == 2 || i6 == 1) {
                                                i20 = 5;
                                                i21 = 4;
                                            } else {
                                                i20 = 8;
                                                i21 = 5;
                                            }
                                            i15 = i20;
                                            i14 = i21;
                                        } else {
                                            if (i24 > 0) {
                                                i14 = 5;
                                            } else if (i24 != 0 || i11 != 0) {
                                                i14 = 4;
                                            } else if (z9) {
                                                i15 = (constraintWidget8 == parent || constraintWidget9 == parent) ? 5 : 4;
                                                i14 = 4;
                                            } else {
                                                i14 = 8;
                                            }
                                            i15 = 5;
                                        }
                                        z15 = true;
                                        z16 = true;
                                        z17 = true;
                                        linearSystem = linearSystem;
                                    }
                                    z15 = true;
                                    z16 = true;
                                    z17 = true;
                                } else {
                                    linearSystem = linearSystem;
                                    solverVariableCreateObjectVariable3 = solverVariableCreateObjectVariable3;
                                    solverVariable3 = solverVariable3;
                                    solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                                    i28 = 6;
                                    i14 = 4;
                                    i15 = 5;
                                    z15 = false;
                                    z16 = false;
                                    z17 = false;
                                }
                                z15 = true;
                                z16 = true;
                                z17 = false;
                            }
                            if (z16 && solverVariableCreateObjectVariable5 == solverVariable3 && constraintWidget8 != parent) {
                                z16 = false;
                                z18 = false;
                            } else {
                                z18 = true;
                            }
                            if (z15) {
                                if (z13 || z7 || z9 || solverVariableCreateObjectVariable5 != solverVariable || solverVariable3 != solverVariable2) {
                                    i19 = i28;
                                    z2 = z2;
                                } else {
                                    i19 = i13;
                                    i15 = i19;
                                    z2 = false;
                                    z18 = false;
                                }
                                solverVariable4 = solverVariable;
                                constraintWidget = constraintWidget9;
                                SolverVariable solverVariable7 = solverVariableCreateObjectVariable4;
                                linearSystem.addCentering(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), f, solverVariable3, solverVariable7, constraintAnchor2.getMargin(), i19);
                                solverVariable3 = solverVariable3;
                                solverVariableCreateObjectVariable4 = solverVariable7;
                            } else {
                                solverVariable3 = solverVariable3;
                                solverVariable4 = solverVariable;
                                constraintWidget = constraintWidget9;
                                z2 = z2;
                            }
                            if (this.mVisibility == i13 && !constraintAnchor2.hasDependents()) {
                                return;
                            }
                            if (z16) {
                                if (!z2 || solverVariableCreateObjectVariable5 == solverVariable3 || z13) {
                                    constraintWidget2 = constraintWidget8;
                                    constraintWidget3 = constraintWidget;
                                } else {
                                    constraintWidget2 = constraintWidget8;
                                    if (constraintWidget2 instanceof Barrier) {
                                        constraintWidget3 = constraintWidget;
                                    } else {
                                        constraintWidget3 = constraintWidget;
                                        if (constraintWidget3 instanceof Barrier) {
                                        }
                                        linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), i18);
                                        linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariable3, -constraintAnchor2.getMargin(), i18);
                                        i15 = i18;
                                    }
                                    i18 = 6;
                                    linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), i18);
                                    linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariable3, -constraintAnchor2.getMargin(), i18);
                                    i15 = i18;
                                }
                                i18 = i15;
                                linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), i18);
                                linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariable3, -constraintAnchor2.getMargin(), i18);
                                i15 = i18;
                            } else {
                                constraintWidget2 = constraintWidget8;
                                constraintWidget3 = constraintWidget;
                            }
                            if (!z2 || !z10 || (constraintWidget2 instanceof Barrier) || (constraintWidget3 instanceof Barrier)) {
                                constraintWidget4 = parent;
                            } else {
                                constraintWidget4 = parent;
                                if (constraintWidget3 != constraintWidget4) {
                                    iMin2 = 6;
                                    i16 = 6;
                                    z19 = true;
                                }
                                if (z19) {
                                    if (!z17 && (!z9 || z3)) {
                                        if (constraintWidget2 != constraintWidget4 && constraintWidget3 != constraintWidget4) {
                                            i28 = iMin2;
                                        }
                                        if ((constraintWidget2 instanceof Guideline) || (constraintWidget3 instanceof Guideline)) {
                                            i28 = 5;
                                        }
                                        if ((constraintWidget2 instanceof Barrier) || (constraintWidget3 instanceof Barrier)) {
                                            i28 = 5;
                                        }
                                        iMin2 = Math.max(z9 ? 5 : i28, iMin2);
                                    }
                                    if (z2 != 0) {
                                        iMin2 = Math.min(i16, iMin2);
                                        if (z6 && !z9 && (constraintWidget2 == constraintWidget4 || constraintWidget3 == constraintWidget4)) {
                                            iMin2 = 4;
                                        }
                                    }
                                    linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), iMin2);
                                    linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariable3, -constraintAnchor2.getMargin(), iMin2);
                                } else {
                                    z2 = z2;
                                }
                                if (z2) {
                                    if (solverVariable4 == solverVariableCreateObjectVariable5) {
                                        margin = constraintAnchor.getMargin();
                                    } else {
                                        margin = 0;
                                    }
                                    if (solverVariableCreateObjectVariable5 != solverVariable4) {
                                        linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariable4, margin, 5);
                                    }
                                }
                                if (z2 || !z13 || i3 != 0 || i11 != 0) {
                                    i17 = 5;
                                } else if (z13 && i9 == 3) {
                                    linearSystem.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, 0, 8);
                                    i17 = 5;
                                } else {
                                    i17 = 5;
                                    linearSystem.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, 0, 5);
                                }
                            }
                            iMin2 = i14;
                            i16 = i15;
                            z19 = z18;
                            if (z19) {
                                if (!z17) {
                                }
                                if (z2 != 0) {
                                    iMin2 = Math.min(i16, iMin2);
                                    if (z6) {
                                        iMin2 = 4;
                                    }
                                }
                                linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), iMin2);
                                linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariable3, -constraintAnchor2.getMargin(), iMin2);
                            } else {
                                z2 = z2;
                            }
                            if (z2) {
                                if (solverVariable4 == solverVariableCreateObjectVariable5) {
                                    margin = constraintAnchor.getMargin();
                                } else {
                                    margin = 0;
                                }
                                if (solverVariableCreateObjectVariable5 != solverVariable4) {
                                    linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariable4, margin, 5);
                                }
                            }
                            if (z2) {
                                i17 = 5;
                            } else {
                                i17 = 5;
                            }
                        }
                        z21 = z2;
                    } else {
                        linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariable3, -constraintAnchor2.getMargin(), 8);
                        if (z2) {
                            if (this.OPTIMIZE_WRAP && solverVariableCreateObjectVariable3.isFinalValue && (constraintWidget5 = this.mParent) != null) {
                                ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) constraintWidget5;
                                if (z) {
                                    constraintWidgetContainer.addHorizontalWrapMinVariable(constraintAnchor);
                                } else {
                                    constraintWidgetContainer.addVerticalWrapMinVariable(constraintAnchor);
                                }
                            } else {
                                linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariable, 0, 5);
                                i17 = 5;
                            }
                        }
                        solverVariable2 = solverVariable2;
                        z21 = z2;
                    }
                    i17 = 5;
                    solverVariable2 = solverVariable2;
                    z21 = z2;
                } else {
                    i17 = (z2 && (constraintAnchor.mTarget.mOwner instanceof Barrier)) ? 8 : 5;
                    solverVariable3 = solverVariable3;
                    z21 = z2;
                    solverVariable2 = solverVariable2;
                }
                if (z21 && z4) {
                    int margin3 = constraintAnchor2.mTarget != null ? constraintAnchor2.getMargin() : 0;
                    if (solverVariable3 != solverVariable2) {
                        if (!this.OPTIMIZE_WRAP || !solverVariableCreateObjectVariable4.isFinalValue || (constraintWidget6 = this.mParent) == null) {
                            linearSystem.addGreaterThan(solverVariable2, solverVariableCreateObjectVariable4, margin3, i17);
                            return;
                        }
                        ConstraintWidgetContainer constraintWidgetContainer2 = (ConstraintWidgetContainer) constraintWidget6;
                        if (z) {
                            constraintWidgetContainer2.addHorizontalWrapMaxVariable(constraintAnchor2);
                            return;
                        } else {
                            constraintWidgetContainer2.addVerticalWrapMaxVariable(constraintAnchor2);
                            return;
                        }
                    }
                    return;
                }
                return;
            }
            if (z5) {
                linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, 0, 3);
                if (i3 > 0) {
                    linearSystem.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i3, 8);
                }
                if (i4 < Integer.MAX_VALUE) {
                    linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i4, 8);
                }
            } else {
                linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 8);
            }
            z13 = z12;
            i11 = i7;
            if (z11) {
            }
            if (i25 >= 2) {
            }
        }
        i9 = i26;
        i10 = this.mWidthOverride;
        if (i10 == -1) {
            i10 = i2;
        } else {
            i10 = i2;
        }
        solverVariable3 = solverVariableCreateObjectVariable6;
        iMin = this.mHeightOverride;
        if (iMin != -1) {
            iMin = i10;
        } else {
            iMin = i10;
        }
        if (this.mVisibility == 8) {
            iMin = 0;
            z12 = false;
        }
        if (z11) {
            if (zIsConnected) {
                if (zIsConnected) {
                    linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), 8);
                }
            } else if (zIsConnected) {
                linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), 8);
            }
        }
        if (!z12) {
            if (i25 == 2) {
            }
            if (i7 == -2) {
                i11 = iMin;
            } else {
                i11 = i7;
            }
            if (i24 == -2) {
                i12 = iMin;
            } else {
                i12 = i24;
            }
            if (iMin > 0) {
                iMin = 0;
            }
            if (i11 > 0) {
                linearSystem.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i11, 8);
                iMin = Math.max(iMin, i11);
            }
            if (i12 > 0) {
                if (z2) {
                    linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i12, 8);
                } else {
                    linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i12, 8);
                }
                iMin = Math.min(iMin, i12);
            }
            if (i9 == 1) {
                if (z2) {
                    linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 8);
                } else if (z8) {
                    linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 5);
                    linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 8);
                } else {
                    linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 5);
                    linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 8);
                }
                solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                z13 = z12;
                solverVariable3 = solverVariable3;
                z4 = z4;
                i11 = i11;
                i24 = i12;
                i25 = i25 == true ? 1 : 0;
            } else {
                if (i9 == 2) {
                    type = constraintAnchor.getType();
                    type2 = ConstraintAnchor.Type.TOP;
                    if (type != type2) {
                        solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(type2));
                        solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                    } else {
                        solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(type2));
                        solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                    }
                    SolverVariable solverVariable8 = solverVariableCreateObjectVariable2;
                    solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                    linearSystem.addConstraint(linearSystem.createRow().createRowDimensionRatio(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, solverVariable8, solverVariableCreateObjectVariable, f2));
                    if (z2) {
                        z12 = false;
                    }
                    z13 = z12;
                    z4 = z4;
                } else {
                    solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                    z13 = z12;
                    z4 = true;
                }
                i24 = i12;
            }
            if (z11) {
            }
            if (i25 >= 2) {
            }
        }
        if (z5) {
            linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, 0, 3);
            if (i3 > 0) {
                linearSystem.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i3, 8);
            }
            if (i4 < Integer.MAX_VALUE) {
                linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i4, 8);
            }
        } else {
            linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 8);
        }
        z13 = z12;
        i11 = i7;
        if (z11) {
        }
        if (i25 >= 2) {
        }
    }

    public void connect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2) {
        connect(type, constraintWidget, type2, 0);
    }

    public void connect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i) {
        ConstraintAnchor.Type type3;
        ConstraintAnchor.Type type4;
        boolean z;
        ConstraintAnchor.Type type5 = ConstraintAnchor.Type.CENTER;
        if (type == type5) {
            if (type2 != type5) {
                ConstraintAnchor.Type type6 = ConstraintAnchor.Type.LEFT;
                if (type2 == type6 || type2 == ConstraintAnchor.Type.RIGHT) {
                    connect(type6, constraintWidget, type2, 0);
                    connect(ConstraintAnchor.Type.RIGHT, constraintWidget, type2, 0);
                    getAnchor(type5).connect(constraintWidget.getAnchor(type2), 0);
                    return;
                }
                ConstraintAnchor.Type type7 = ConstraintAnchor.Type.TOP;
                if (type2 == type7 || type2 == ConstraintAnchor.Type.BOTTOM) {
                    connect(type7, constraintWidget, type2, 0);
                    connect(ConstraintAnchor.Type.BOTTOM, constraintWidget, type2, 0);
                    getAnchor(type5).connect(constraintWidget.getAnchor(type2), 0);
                    return;
                }
                return;
            }
            ConstraintAnchor.Type type8 = ConstraintAnchor.Type.LEFT;
            ConstraintAnchor anchor = getAnchor(type8);
            ConstraintAnchor.Type type9 = ConstraintAnchor.Type.RIGHT;
            ConstraintAnchor anchor2 = getAnchor(type9);
            ConstraintAnchor.Type type10 = ConstraintAnchor.Type.TOP;
            ConstraintAnchor anchor3 = getAnchor(type10);
            ConstraintAnchor.Type type11 = ConstraintAnchor.Type.BOTTOM;
            ConstraintAnchor anchor4 = getAnchor(type11);
            boolean z2 = true;
            if ((anchor == null || !anchor.isConnected()) && (anchor2 == null || !anchor2.isConnected())) {
                connect(type8, constraintWidget, type8, 0);
                connect(type9, constraintWidget, type9, 0);
                z = true;
            } else {
                z = false;
            }
            if ((anchor3 == null || !anchor3.isConnected()) && (anchor4 == null || !anchor4.isConnected())) {
                connect(type10, constraintWidget, type10, 0);
                connect(type11, constraintWidget, type11, 0);
            } else {
                z2 = false;
            }
            if (z && z2) {
                getAnchor(type5).connect(constraintWidget.getAnchor(type5), 0);
                return;
            }
            if (z) {
                ConstraintAnchor.Type type12 = ConstraintAnchor.Type.CENTER_X;
                getAnchor(type12).connect(constraintWidget.getAnchor(type12), 0);
                return;
            } else {
                if (z2) {
                    ConstraintAnchor.Type type13 = ConstraintAnchor.Type.CENTER_Y;
                    getAnchor(type13).connect(constraintWidget.getAnchor(type13), 0);
                    return;
                }
                return;
            }
        }
        ConstraintAnchor.Type type14 = ConstraintAnchor.Type.CENTER_X;
        if (type == type14 && (type2 == (type4 = ConstraintAnchor.Type.LEFT) || type2 == ConstraintAnchor.Type.RIGHT)) {
            ConstraintAnchor anchor5 = getAnchor(type4);
            ConstraintAnchor anchor6 = constraintWidget.getAnchor(type2);
            ConstraintAnchor anchor7 = getAnchor(ConstraintAnchor.Type.RIGHT);
            anchor5.connect(anchor6, 0);
            anchor7.connect(anchor6, 0);
            getAnchor(type14).connect(anchor6, 0);
            return;
        }
        ConstraintAnchor.Type type15 = ConstraintAnchor.Type.CENTER_Y;
        if (type == type15 && (type2 == (type3 = ConstraintAnchor.Type.TOP) || type2 == ConstraintAnchor.Type.BOTTOM)) {
            ConstraintAnchor anchor8 = constraintWidget.getAnchor(type2);
            getAnchor(type3).connect(anchor8, 0);
            getAnchor(ConstraintAnchor.Type.BOTTOM).connect(anchor8, 0);
            getAnchor(type15).connect(anchor8, 0);
            return;
        }
        if (type == type14 && type2 == type14) {
            ConstraintAnchor.Type type16 = ConstraintAnchor.Type.LEFT;
            getAnchor(type16).connect(constraintWidget.getAnchor(type16), 0);
            ConstraintAnchor.Type type17 = ConstraintAnchor.Type.RIGHT;
            getAnchor(type17).connect(constraintWidget.getAnchor(type17), 0);
            getAnchor(type14).connect(constraintWidget.getAnchor(type2), 0);
            return;
        }
        if (type == type15 && type2 == type15) {
            ConstraintAnchor.Type type18 = ConstraintAnchor.Type.TOP;
            getAnchor(type18).connect(constraintWidget.getAnchor(type18), 0);
            ConstraintAnchor.Type type19 = ConstraintAnchor.Type.BOTTOM;
            getAnchor(type19).connect(constraintWidget.getAnchor(type19), 0);
            getAnchor(type15).connect(constraintWidget.getAnchor(type2), 0);
            return;
        }
        ConstraintAnchor anchor9 = getAnchor(type);
        ConstraintAnchor anchor10 = constraintWidget.getAnchor(type2);
        if (anchor9.isValidConnection(anchor10)) {
            ConstraintAnchor.Type type20 = ConstraintAnchor.Type.BASELINE;
            if (type == type20) {
                ConstraintAnchor anchor11 = getAnchor(ConstraintAnchor.Type.TOP);
                ConstraintAnchor anchor12 = getAnchor(ConstraintAnchor.Type.BOTTOM);
                if (anchor11 != null) {
                    anchor11.reset();
                }
                if (anchor12 != null) {
                    anchor12.reset();
                }
            } else if (type == ConstraintAnchor.Type.TOP || type == ConstraintAnchor.Type.BOTTOM) {
                ConstraintAnchor anchor13 = getAnchor(type20);
                if (anchor13 != null) {
                    anchor13.reset();
                }
                ConstraintAnchor anchor14 = getAnchor(type5);
                if (anchor14.getTarget() != anchor10) {
                    anchor14.reset();
                }
                ConstraintAnchor opposite = getAnchor(type).getOpposite();
                ConstraintAnchor anchor15 = getAnchor(type15);
                if (anchor15.isConnected()) {
                    opposite.reset();
                    anchor15.reset();
                }
            } else if (type == ConstraintAnchor.Type.LEFT || type == ConstraintAnchor.Type.RIGHT) {
                ConstraintAnchor anchor16 = getAnchor(type5);
                if (anchor16.getTarget() != anchor10) {
                    anchor16.reset();
                }
                ConstraintAnchor opposite2 = getAnchor(type).getOpposite();
                ConstraintAnchor anchor17 = getAnchor(type14);
                if (anchor17.isConnected()) {
                    opposite2.reset();
                    anchor17.reset();
                }
            }
            anchor9.connect(anchor10, i);
        }
    }

    public void connect(ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i) {
        if (constraintAnchor.getOwner() == this) {
            connect(constraintAnchor.getType(), constraintAnchor2.getOwner(), constraintAnchor2.getType(), i);
        }
    }

    public void connectCircularConstraint(ConstraintWidget constraintWidget, float f, int i) {
        ConstraintAnchor.Type type = ConstraintAnchor.Type.CENTER;
        immediateConnect(type, constraintWidget, type, i, 0);
        this.mCircleConstraintAngle = f;
    }

    public void copy(ConstraintWidget constraintWidget, HashMap map) {
        this.mHorizontalResolution = constraintWidget.mHorizontalResolution;
        this.mVerticalResolution = constraintWidget.mVerticalResolution;
        this.mMatchConstraintDefaultWidth = constraintWidget.mMatchConstraintDefaultWidth;
        this.mMatchConstraintDefaultHeight = constraintWidget.mMatchConstraintDefaultHeight;
        int[] iArr = this.mResolvedMatchConstraintDefault;
        int[] iArr2 = constraintWidget.mResolvedMatchConstraintDefault;
        iArr[0] = iArr2[0];
        iArr[1] = iArr2[1];
        this.mMatchConstraintMinWidth = constraintWidget.mMatchConstraintMinWidth;
        this.mMatchConstraintMaxWidth = constraintWidget.mMatchConstraintMaxWidth;
        this.mMatchConstraintMinHeight = constraintWidget.mMatchConstraintMinHeight;
        this.mMatchConstraintMaxHeight = constraintWidget.mMatchConstraintMaxHeight;
        this.mMatchConstraintPercentHeight = constraintWidget.mMatchConstraintPercentHeight;
        this.mIsWidthWrapContent = constraintWidget.mIsWidthWrapContent;
        this.mIsHeightWrapContent = constraintWidget.mIsHeightWrapContent;
        this.mResolvedDimensionRatioSide = constraintWidget.mResolvedDimensionRatioSide;
        this.mResolvedDimensionRatio = constraintWidget.mResolvedDimensionRatio;
        int[] iArr3 = constraintWidget.mMaxDimension;
        this.mMaxDimension = Arrays.copyOf(iArr3, iArr3.length);
        this.mCircleConstraintAngle = constraintWidget.mCircleConstraintAngle;
        this.hasBaseline = constraintWidget.hasBaseline;
        this.inPlaceholder = constraintWidget.inPlaceholder;
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mListDimensionBehaviors = (DimensionBehaviour[]) Arrays.copyOf(this.mListDimensionBehaviors, 2);
        this.mParent = this.mParent == null ? null : (ConstraintWidget) map.get(constraintWidget.mParent);
        this.mWidth = constraintWidget.mWidth;
        this.mHeight = constraintWidget.mHeight;
        this.mDimensionRatio = constraintWidget.mDimensionRatio;
        this.mDimensionRatioSide = constraintWidget.mDimensionRatioSide;
        this.mX = constraintWidget.mX;
        this.mY = constraintWidget.mY;
        this.mRelX = constraintWidget.mRelX;
        this.mRelY = constraintWidget.mRelY;
        this.mOffsetX = constraintWidget.mOffsetX;
        this.mOffsetY = constraintWidget.mOffsetY;
        this.mBaselineDistance = constraintWidget.mBaselineDistance;
        this.mMinWidth = constraintWidget.mMinWidth;
        this.mMinHeight = constraintWidget.mMinHeight;
        this.mHorizontalBiasPercent = constraintWidget.mHorizontalBiasPercent;
        this.mVerticalBiasPercent = constraintWidget.mVerticalBiasPercent;
        this.mCompanionWidget = constraintWidget.mCompanionWidget;
        this.mContainerItemSkip = constraintWidget.mContainerItemSkip;
        this.mVisibility = constraintWidget.mVisibility;
        this.mAnimated = constraintWidget.mAnimated;
        this.mDebugName = constraintWidget.mDebugName;
        this.mType = constraintWidget.mType;
        this.mDistToTop = constraintWidget.mDistToTop;
        this.mDistToLeft = constraintWidget.mDistToLeft;
        this.mDistToRight = constraintWidget.mDistToRight;
        this.mDistToBottom = constraintWidget.mDistToBottom;
        this.mLeftHasCentered = constraintWidget.mLeftHasCentered;
        this.mRightHasCentered = constraintWidget.mRightHasCentered;
        this.mTopHasCentered = constraintWidget.mTopHasCentered;
        this.mBottomHasCentered = constraintWidget.mBottomHasCentered;
        this.mHorizontalWrapVisited = constraintWidget.mHorizontalWrapVisited;
        this.mVerticalWrapVisited = constraintWidget.mVerticalWrapVisited;
        this.mHorizontalChainStyle = constraintWidget.mHorizontalChainStyle;
        this.mVerticalChainStyle = constraintWidget.mVerticalChainStyle;
        this.mHorizontalChainFixedPosition = constraintWidget.mHorizontalChainFixedPosition;
        this.mVerticalChainFixedPosition = constraintWidget.mVerticalChainFixedPosition;
        float[] fArr = this.mWeight;
        float[] fArr2 = constraintWidget.mWeight;
        fArr[0] = fArr2[0];
        fArr[1] = fArr2[1];
        ConstraintWidget[] constraintWidgetArr = this.mListNextMatchConstraintsWidget;
        ConstraintWidget[] constraintWidgetArr2 = constraintWidget.mListNextMatchConstraintsWidget;
        constraintWidgetArr[0] = constraintWidgetArr2[0];
        constraintWidgetArr[1] = constraintWidgetArr2[1];
        ConstraintWidget[] constraintWidgetArr3 = this.mNextChainWidget;
        ConstraintWidget[] constraintWidgetArr4 = constraintWidget.mNextChainWidget;
        constraintWidgetArr3[0] = constraintWidgetArr4[0];
        constraintWidgetArr3[1] = constraintWidgetArr4[1];
        ConstraintWidget constraintWidget2 = constraintWidget.mHorizontalNextWidget;
        this.mHorizontalNextWidget = constraintWidget2 == null ? null : (ConstraintWidget) map.get(constraintWidget2);
        ConstraintWidget constraintWidget3 = constraintWidget.mVerticalNextWidget;
        this.mVerticalNextWidget = constraintWidget3 != null ? (ConstraintWidget) map.get(constraintWidget3) : null;
    }

    public void createObjectVariables(LinearSystem linearSystem) {
        linearSystem.createObjectVariable(this.mLeft);
        linearSystem.createObjectVariable(this.mTop);
        linearSystem.createObjectVariable(this.mRight);
        linearSystem.createObjectVariable(this.mBottom);
        if (this.mBaselineDistance > 0) {
            linearSystem.createObjectVariable(this.mBaseline);
        }
    }

    public void ensureWidgetRuns() {
        if (this.horizontalRun == null) {
            this.horizontalRun = new HorizontalWidgetRun(this);
        }
        if (this.verticalRun == null) {
            this.verticalRun = new VerticalWidgetRun(this);
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type type) {
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[type.ordinal()]) {
            case 1:
                return this.mLeft;
            case 2:
                return this.mTop;
            case 3:
                return this.mRight;
            case 4:
                return this.mBottom;
            case 5:
                return this.mBaseline;
            case 6:
                return this.mCenter;
            case 7:
                return this.mCenterX;
            case 8:
                return this.mCenterY;
            case 9:
                return null;
            default:
                throw new AssertionError(type.name());
        }
    }

    public int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public float getBiasPercent(int i) {
        if (i == 0) {
            return this.mHorizontalBiasPercent;
        }
        if (i == 1) {
            return this.mVerticalBiasPercent;
        }
        return -1.0f;
    }

    public int getBottom() {
        return getY() + this.mHeight;
    }

    public Object getCompanionWidget() {
        return this.mCompanionWidget;
    }

    public String getDebugName() {
        return this.mDebugName;
    }

    public DimensionBehaviour getDimensionBehaviour(int i) {
        if (i == 0) {
            return getHorizontalDimensionBehaviour();
        }
        if (i == 1) {
            return getVerticalDimensionBehaviour();
        }
        return null;
    }

    public float getDimensionRatio() {
        return this.mDimensionRatio;
    }

    public int getDimensionRatioSide() {
        return this.mDimensionRatioSide;
    }

    public int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    public float getHorizontalBiasPercent() {
        return this.mHorizontalBiasPercent;
    }

    public int getHorizontalChainStyle() {
        return this.mHorizontalChainStyle;
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mListDimensionBehaviors[0];
    }

    public int getHorizontalMargin() {
        ConstraintAnchor constraintAnchor = this.mLeft;
        int i = constraintAnchor != null ? constraintAnchor.mMargin : 0;
        ConstraintAnchor constraintAnchor2 = this.mRight;
        return constraintAnchor2 != null ? i + constraintAnchor2.mMargin : i;
    }

    public int getLastHorizontalMeasureSpec() {
        return this.mLastHorizontalMeasureSpec;
    }

    public int getLastVerticalMeasureSpec() {
        return this.mLastVerticalMeasureSpec;
    }

    public int getLength(int i) {
        if (i == 0) {
            return getWidth();
        }
        if (i == 1) {
            return getHeight();
        }
        return 0;
    }

    public int getMaxHeight() {
        return this.mMaxDimension[1];
    }

    public int getMaxWidth() {
        return this.mMaxDimension[0];
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public ConstraintWidget getNextChainMember(int i) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (i != 0) {
            if (i == 1 && (constraintAnchor2 = (constraintAnchor = this.mBottom).mTarget) != null && constraintAnchor2.mTarget == constraintAnchor) {
                return constraintAnchor2.mOwner;
            }
            return null;
        }
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return null;
        }
        return constraintAnchor4.mOwner;
    }

    public ConstraintWidget getParent() {
        return this.mParent;
    }

    public ConstraintWidget getPreviousChainMember(int i) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (i != 0) {
            if (i == 1 && (constraintAnchor2 = (constraintAnchor = this.mTop).mTarget) != null && constraintAnchor2.mTarget == constraintAnchor) {
                return constraintAnchor2.mOwner;
            }
            return null;
        }
        ConstraintAnchor constraintAnchor3 = this.mLeft;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return null;
        }
        return constraintAnchor4.mOwner;
    }

    public int getRight() {
        return getX() + this.mWidth;
    }

    public WidgetRun getRun(int i) {
        if (i == 0) {
            return this.horizontalRun;
        }
        if (i == 1) {
            return this.verticalRun;
        }
        return null;
    }

    public void getSceneString(StringBuilder sb) {
        sb.append("  " + this.stringId + ":{\n");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("    actualWidth:");
        sb2.append(this.mWidth);
        sb.append(sb2.toString());
        sb.append("\n");
        sb.append("    actualHeight:" + this.mHeight);
        sb.append("\n");
        sb.append("    actualLeft:" + this.mX);
        sb.append("\n");
        sb.append("    actualTop:" + this.mY);
        sb.append("\n");
        getSceneString(sb, "left", this.mLeft);
        getSceneString(sb, "top", this.mTop);
        getSceneString(sb, "right", this.mRight);
        getSceneString(sb, "bottom", this.mBottom);
        getSceneString(sb, "baseline", this.mBaseline);
        getSceneString(sb, "centerX", this.mCenterX);
        getSceneString(sb, "centerY", this.mCenterY);
        getSceneString(sb, "    width", this.mWidth, this.mMinWidth, this.mMaxDimension[0], this.mWidthOverride, this.mMatchConstraintMinWidth, this.mMatchConstraintDefaultWidth, this.mMatchConstraintPercentWidth, this.mWeight[0]);
        getSceneString(sb, "    height", this.mHeight, this.mMinHeight, this.mMaxDimension[1], this.mHeightOverride, this.mMatchConstraintMinHeight, this.mMatchConstraintDefaultHeight, this.mMatchConstraintPercentHeight, this.mWeight[1]);
        serializeDimensionRatio(sb, "    dimensionRatio", this.mDimensionRatio, this.mDimensionRatioSide);
        serializeAttribute(sb, "    horizontalBias", this.mHorizontalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(sb, "    verticalBias", this.mVerticalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(sb, "    horizontalChainStyle", this.mHorizontalChainStyle, 0);
        serializeAttribute(sb, "    verticalChainStyle", this.mVerticalChainStyle, 0);
        sb.append("  }");
    }

    public final void getSceneString(StringBuilder sb, String str, int i, int i2, int i3, int i4, int i5, int i6, float f, float f2) {
        sb.append(str);
        sb.append(" :  {\n");
        serializeAttribute(sb, "      size", i, 0);
        serializeAttribute(sb, "      min", i2, 0);
        serializeAttribute(sb, "      max", i3, Integer.MAX_VALUE);
        serializeAttribute(sb, "      matchMin", i5, 0);
        serializeAttribute(sb, "      matchDef", i6, 0);
        serializeAttribute(sb, "      matchPercent", f, 1.0f);
        sb.append("    },\n");
    }

    public final void getSceneString(StringBuilder sb, String str, ConstraintAnchor constraintAnchor) {
        if (constraintAnchor.mTarget == null) {
            return;
        }
        sb.append("    ");
        sb.append(str);
        sb.append(" : [ '");
        sb.append(constraintAnchor.mTarget);
        sb.append("'");
        if (constraintAnchor.mGoneMargin != Integer.MIN_VALUE || constraintAnchor.mMargin != 0) {
            sb.append(ac.b);
            sb.append(constraintAnchor.mMargin);
            if (constraintAnchor.mGoneMargin != Integer.MIN_VALUE) {
                sb.append(ac.b);
                sb.append(constraintAnchor.mGoneMargin);
                sb.append(ac.b);
            }
        }
        sb.append(" ] ,\n");
    }

    public float getVerticalBiasPercent() {
        return this.mVerticalBiasPercent;
    }

    public int getVerticalChainStyle() {
        return this.mVerticalChainStyle;
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mListDimensionBehaviors[1];
    }

    public int getVerticalMargin() {
        int i = this.mLeft != null ? this.mTop.mMargin : 0;
        return this.mRight != null ? i + this.mBottom.mMargin : i;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public int getX() {
        ConstraintWidget constraintWidget = this.mParent;
        return (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) ? this.mX : ((ConstraintWidgetContainer) constraintWidget).mPaddingLeft + this.mX;
    }

    public int getY() {
        ConstraintWidget constraintWidget = this.mParent;
        return (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) ? this.mY : ((ConstraintWidgetContainer) constraintWidget).mPaddingTop + this.mY;
    }

    public boolean hasBaseline() {
        return this.hasBaseline;
    }

    public boolean hasDanglingDimension(int i) {
        if (i == 0) {
            return (this.mLeft.mTarget != null ? 1 : 0) + (this.mRight.mTarget != null ? 1 : 0) < 2;
        }
        return ((this.mTop.mTarget != null ? 1 : 0) + (this.mBottom.mTarget != null ? 1 : 0)) + (this.mBaseline.mTarget != null ? 1 : 0) < 2;
    }

    public boolean hasDependencies() {
        int size = this.mAnchors.size();
        for (int i = 0; i < size; i++) {
            if (((ConstraintAnchor) this.mAnchors.get(i)).hasDependents()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDimensionOverride() {
        return (this.mWidthOverride == -1 && this.mHeightOverride == -1) ? false : true;
    }

    public boolean hasResolvedTargets(int i, int i2) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (i == 0) {
            ConstraintAnchor constraintAnchor3 = this.mLeft.mTarget;
            return constraintAnchor3 != null && constraintAnchor3.hasFinalValue() && (constraintAnchor2 = this.mRight.mTarget) != null && constraintAnchor2.hasFinalValue() && (this.mRight.mTarget.getFinalValue() - this.mRight.getMargin()) - (this.mLeft.mTarget.getFinalValue() + this.mLeft.getMargin()) >= i2;
        }
        ConstraintAnchor constraintAnchor4 = this.mTop.mTarget;
        if (constraintAnchor4 != null && constraintAnchor4.hasFinalValue() && (constraintAnchor = this.mBottom.mTarget) != null && constraintAnchor.hasFinalValue() && (this.mBottom.mTarget.getFinalValue() - this.mBottom.getMargin()) - (this.mTop.mTarget.getFinalValue() + this.mTop.getMargin()) >= i2) {
            return true;
        }
        return false;
    }

    public void immediateConnect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i, int i2) {
        getAnchor(type).connect(constraintWidget.getAnchor(type2), i, i2, true);
    }

    public final boolean isChainHead(int i) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        int i2 = i * 2;
        ConstraintAnchor[] constraintAnchorArr = this.mListAnchors;
        ConstraintAnchor constraintAnchor3 = constraintAnchorArr[i2];
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return (constraintAnchor4 == null || constraintAnchor4.mTarget == constraintAnchor3 || (constraintAnchor2 = (constraintAnchor = constraintAnchorArr[i2 + 1]).mTarget) == null || constraintAnchor2.mTarget != constraintAnchor) ? false : true;
    }

    public boolean isHorizontalSolvingPassDone() {
        return this.horizontalSolvingPass;
    }

    public boolean isInBarrier(int i) {
        return this.mIsInBarrier[i];
    }

    public boolean isInHorizontalChain() {
        ConstraintAnchor constraintAnchor = this.mLeft;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return constraintAnchor4 != null && constraintAnchor4.mTarget == constraintAnchor3;
    }

    public boolean isInPlaceholder() {
        return this.inPlaceholder;
    }

    public boolean isInVerticalChain() {
        ConstraintAnchor constraintAnchor = this.mTop;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mBottom;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return constraintAnchor4 != null && constraintAnchor4.mTarget == constraintAnchor3;
    }

    public boolean isInVirtualLayout() {
        return this.mInVirtualLayout;
    }

    public boolean isMeasureRequested() {
        return this.mMeasureRequested && this.mVisibility != 8;
    }

    public boolean isResolvedHorizontally() {
        if (this.resolvedHorizontal) {
            return true;
        }
        return this.mLeft.hasFinalValue() && this.mRight.hasFinalValue();
    }

    public boolean isResolvedVertically() {
        if (this.resolvedVertical) {
            return true;
        }
        return this.mTop.hasFinalValue() && this.mBottom.hasFinalValue();
    }

    public boolean isVerticalSolvingPassDone() {
        return this.verticalSolvingPass;
    }

    public void markHorizontalSolvingPassDone() {
        this.horizontalSolvingPass = true;
    }

    public void markVerticalSolvingPassDone() {
        this.verticalSolvingPass = true;
    }

    public boolean oppositeDimensionsTied() {
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.MATCH_CONSTRAINT;
        return dimensionBehaviour == dimensionBehaviour2 && dimensionBehaviourArr[1] == dimensionBehaviour2;
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mCircleConstraintAngle = 0.0f;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        dimensionBehaviourArr[0] = dimensionBehaviour;
        dimensionBehaviourArr[1] = dimensionBehaviour;
        this.mCompanionWidget = null;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mType = null;
        this.mHorizontalWrapVisited = false;
        this.mVerticalWrapVisited = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mHorizontalChainFixedPosition = false;
        this.mVerticalChainFixedPosition = false;
        float[] fArr = this.mWeight;
        fArr[0] = -1.0f;
        fArr[1] = -1.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        int[] iArr = this.mMaxDimension;
        iArr[0] = Integer.MAX_VALUE;
        iArr[1] = Integer.MAX_VALUE;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mMatchConstraintMaxWidth = Integer.MAX_VALUE;
        this.mMatchConstraintMaxHeight = Integer.MAX_VALUE;
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMinHeight = 0;
        this.mResolvedHasRatio = false;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mGroupsToSolver = false;
        boolean[] zArr = this.isTerminalWidget;
        zArr[0] = true;
        zArr[1] = true;
        this.mInVirtualLayout = false;
        boolean[] zArr2 = this.mIsInBarrier;
        zArr2[0] = false;
        zArr2[1] = false;
        this.mMeasureRequested = true;
        int[] iArr2 = this.mResolvedMatchConstraintDefault;
        iArr2[0] = 0;
        iArr2[1] = 0;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
    }

    public void resetAllConstraints() {
        resetAnchors();
        setVerticalBiasPercent(DEFAULT_BIAS);
        setHorizontalBiasPercent(DEFAULT_BIAS);
    }

    public void resetAnchors() {
        ConstraintWidget parent = getParent();
        if (parent != null && (parent instanceof ConstraintWidgetContainer) && ((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            return;
        }
        int size = this.mAnchors.size();
        for (int i = 0; i < size; i++) {
            ((ConstraintAnchor) this.mAnchors.get(i)).reset();
        }
    }

    public void resetFinalResolution() {
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        int size = this.mAnchors.size();
        for (int i = 0; i < size; i++) {
            ((ConstraintAnchor) this.mAnchors.get(i)).resetFinalResolution();
        }
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable(cache);
        this.mTop.resetSolverVariable(cache);
        this.mRight.resetSolverVariable(cache);
        this.mBottom.resetSolverVariable(cache);
        this.mBaseline.resetSolverVariable(cache);
        this.mCenter.resetSolverVariable(cache);
        this.mCenterX.resetSolverVariable(cache);
        this.mCenterY.resetSolverVariable(cache);
    }

    public final void serializeAttribute(StringBuilder sb, String str, float f, float f2) {
        if (f == f2) {
            return;
        }
        sb.append(str);
        sb.append(" :   ");
        sb.append(f);
        sb.append(",\n");
    }

    public final void serializeAttribute(StringBuilder sb, String str, int i, int i2) {
        if (i == i2) {
            return;
        }
        sb.append(str);
        sb.append(" :   ");
        sb.append(i);
        sb.append(",\n");
    }

    public final void serializeDimensionRatio(StringBuilder sb, String str, float f, int i) {
        if (f == 0.0f) {
            return;
        }
        sb.append(str);
        sb.append(" :  [");
        sb.append(f);
        sb.append(ac.b);
        sb.append(i);
        sb.append("");
        sb.append("],\n");
    }

    public void setAnimated(boolean z) {
        this.mAnimated = z;
    }

    public void setBaselineDistance(int i) {
        this.mBaselineDistance = i;
        this.hasBaseline = i > 0;
    }

    public void setCompanionWidget(Object obj) {
        this.mCompanionWidget = obj;
    }

    public void setDebugName(String str) {
        this.mDebugName = str;
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0086 A[PHI: r0
  0x0086: PHI (r0v2 int) = (r0v1 int), (r0v0 int), (r0v0 int), (r0v0 int), (r0v0 int), (r0v0 int) binds: [B:46:0x0086, B:36:0x007f, B:24:0x0051, B:26:0x0057, B:28:0x0063, B:30:0x0067] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:39:0x0086 -> B:40:0x0087). Please report as a decompilation issue!!! */
    public void setDimensionRatio(String str) {
        float fAbs;
        int i = 0;
        if (str == null || str.length() == 0) {
            this.mDimensionRatio = 0.0f;
            return;
        }
        int length = str.length();
        int iIndexOf = str.indexOf(44);
        int i2 = 0;
        int i3 = -1;
        if (iIndexOf > 0 && iIndexOf < length - 1) {
            String strSubstring = str.substring(0, iIndexOf);
            if (!strSubstring.equalsIgnoreCase(ExifInterface.LONGITUDE_WEST)) {
                i2 = strSubstring.equalsIgnoreCase(an.b) ? 1 : -1;
            }
            i3 = i2;
            i2 = iIndexOf + 1;
        }
        int iIndexOf2 = str.indexOf(58);
        try {
            if (iIndexOf2 < 0 || iIndexOf2 >= length - 1) {
                String strSubstring2 = str.substring(i2);
                if (strSubstring2.length() > 0) {
                    fAbs = Float.parseFloat(strSubstring2);
                } else {
                    fAbs = i;
                }
            } else {
                String strSubstring3 = str.substring(i2, iIndexOf2);
                String strSubstring4 = str.substring(iIndexOf2 + 1);
                if (strSubstring3.length() <= 0 || strSubstring4.length() <= 0) {
                    fAbs = i;
                } else {
                    float f = Float.parseFloat(strSubstring3);
                    float f2 = Float.parseFloat(strSubstring4);
                    if (f <= 0.0f || f2 <= 0.0f) {
                        fAbs = i;
                    } else {
                        fAbs = i3 == 1 ? Math.abs(f2 / f) : Math.abs(f / f2);
                    }
                }
            }
        } catch (NumberFormatException unused) {
        }
        i = (fAbs > i ? 1 : (fAbs == i ? 0 : -1));
        if (i > 0) {
            this.mDimensionRatio = fAbs;
            this.mDimensionRatioSide = i3;
        }
    }

    public void setFinalBaseline(int i) {
        if (this.hasBaseline) {
            int i2 = i - this.mBaselineDistance;
            int i3 = this.mHeight + i2;
            this.mY = i2;
            this.mTop.setFinalValue(i2);
            this.mBottom.setFinalValue(i3);
            this.mBaseline.setFinalValue(i);
            this.resolvedVertical = true;
        }
    }

    public void setFinalHorizontal(int i, int i2) {
        if (this.resolvedHorizontal) {
            return;
        }
        this.mLeft.setFinalValue(i);
        this.mRight.setFinalValue(i2);
        this.mX = i;
        this.mWidth = i2 - i;
        this.resolvedHorizontal = true;
    }

    public void setFinalLeft(int i) {
        this.mLeft.setFinalValue(i);
        this.mX = i;
    }

    public void setFinalTop(int i) {
        this.mTop.setFinalValue(i);
        this.mY = i;
    }

    public void setFinalVertical(int i, int i2) {
        if (this.resolvedVertical) {
            return;
        }
        this.mTop.setFinalValue(i);
        this.mBottom.setFinalValue(i2);
        this.mY = i;
        this.mHeight = i2 - i;
        if (this.hasBaseline) {
            this.mBaseline.setFinalValue(i + this.mBaselineDistance);
        }
        this.resolvedVertical = true;
    }

    public void setFrame(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = i3 - i;
        int i8 = i4 - i2;
        this.mX = i;
        this.mY = i2;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.FIXED;
        if (dimensionBehaviour == dimensionBehaviour2 && i7 < (i6 = this.mWidth)) {
            i7 = i6;
        }
        if (dimensionBehaviourArr[1] == dimensionBehaviour2 && i8 < (i5 = this.mHeight)) {
            i8 = i5;
        }
        this.mWidth = i7;
        this.mHeight = i8;
        int i9 = this.mMinHeight;
        if (i8 < i9) {
            this.mHeight = i9;
        }
        int i10 = this.mMinWidth;
        if (i7 < i10) {
            this.mWidth = i10;
        }
        int i11 = this.mMatchConstraintMaxWidth;
        if (i11 > 0 && dimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
            this.mWidth = Math.min(this.mWidth, i11);
        }
        int i12 = this.mMatchConstraintMaxHeight;
        if (i12 > 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT) {
            this.mHeight = Math.min(this.mHeight, i12);
        }
        int i13 = this.mWidth;
        if (i7 != i13) {
            this.mWidthOverride = i13;
        }
        int i14 = this.mHeight;
        if (i8 != i14) {
            this.mHeightOverride = i14;
        }
    }

    public void setHasBaseline(boolean z) {
        this.hasBaseline = z;
    }

    public void setHeight(int i) {
        this.mHeight = i;
        int i2 = this.mMinHeight;
        if (i < i2) {
            this.mHeight = i2;
        }
    }

    public void setHorizontalBiasPercent(float f) {
        this.mHorizontalBiasPercent = f;
    }

    public void setHorizontalChainStyle(int i) {
        this.mHorizontalChainStyle = i;
    }

    public void setHorizontalDimension(int i, int i2) {
        this.mX = i;
        int i3 = i2 - i;
        this.mWidth = i3;
        int i4 = this.mMinWidth;
        if (i3 < i4) {
            this.mWidth = i4;
        }
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[0] = dimensionBehaviour;
    }

    public void setHorizontalMatchStyle(int i, int i2, int i3, float f) {
        this.mMatchConstraintDefaultWidth = i;
        this.mMatchConstraintMinWidth = i2;
        if (i3 == Integer.MAX_VALUE) {
            i3 = 0;
        }
        this.mMatchConstraintMaxWidth = i3;
        this.mMatchConstraintPercentWidth = f;
        if (f <= 0.0f || f >= 1.0f || i != 0) {
            return;
        }
        this.mMatchConstraintDefaultWidth = 2;
    }

    public void setHorizontalWeight(float f) {
        this.mWeight[0] = f;
    }

    public void setInBarrier(int i, boolean z) {
        this.mIsInBarrier[i] = z;
    }

    public void setInPlaceholder(boolean z) {
        this.inPlaceholder = z;
    }

    public void setInVirtualLayout(boolean z) {
        this.mInVirtualLayout = z;
    }

    public void setLastMeasureSpec(int i, int i2) {
        this.mLastHorizontalMeasureSpec = i;
        this.mLastVerticalMeasureSpec = i2;
        setMeasureRequested(false);
    }

    public void setMaxHeight(int i) {
        this.mMaxDimension[1] = i;
    }

    public void setMaxWidth(int i) {
        this.mMaxDimension[0] = i;
    }

    public void setMeasureRequested(boolean z) {
        this.mMeasureRequested = z;
    }

    public void setMinHeight(int i) {
        if (i < 0) {
            this.mMinHeight = 0;
        } else {
            this.mMinHeight = i;
        }
    }

    public void setMinWidth(int i) {
        if (i < 0) {
            this.mMinWidth = 0;
        } else {
            this.mMinWidth = i;
        }
    }

    public void setOrigin(int i, int i2) {
        this.mX = i;
        this.mY = i2;
    }

    public void setParent(ConstraintWidget constraintWidget) {
        this.mParent = constraintWidget;
    }

    public void setVerticalBiasPercent(float f) {
        this.mVerticalBiasPercent = f;
    }

    public void setVerticalChainStyle(int i) {
        this.mVerticalChainStyle = i;
    }

    public void setVerticalDimension(int i, int i2) {
        this.mY = i;
        int i3 = i2 - i;
        this.mHeight = i3;
        int i4 = this.mMinHeight;
        if (i3 < i4) {
            this.mHeight = i4;
        }
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[1] = dimensionBehaviour;
    }

    public void setVerticalMatchStyle(int i, int i2, int i3, float f) {
        this.mMatchConstraintDefaultHeight = i;
        this.mMatchConstraintMinHeight = i2;
        if (i3 == Integer.MAX_VALUE) {
            i3 = 0;
        }
        this.mMatchConstraintMaxHeight = i3;
        this.mMatchConstraintPercentHeight = f;
        if (f <= 0.0f || f >= 1.0f || i != 0) {
            return;
        }
        this.mMatchConstraintDefaultHeight = 2;
    }

    public void setVerticalWeight(float f) {
        this.mWeight[1] = f;
    }

    public void setVisibility(int i) {
        this.mVisibility = i;
    }

    public void setWidth(int i) {
        this.mWidth = i;
        int i2 = this.mMinWidth;
        if (i < i2) {
            this.mWidth = i2;
        }
    }

    public void setWrapBehaviorInParent(int i) {
        if (i < 0 || i > 3) {
            return;
        }
        this.mWrapBehaviorInParent = i;
    }

    public void setX(int i) {
        this.mX = i;
    }

    public void setY(int i) {
        this.mY = i;
    }

    public void setupDimensionRatio(boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.mResolvedDimensionRatioSide == -1) {
            if (z3 && !z4) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (!z3 && z4) {
                this.mResolvedDimensionRatioSide = 1;
                if (this.mDimensionRatioSide == -1) {
                    this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                }
            }
        }
        if (this.mResolvedDimensionRatioSide == 0 && (!this.mTop.isConnected() || !this.mBottom.isConnected())) {
            this.mResolvedDimensionRatioSide = 1;
        } else if (this.mResolvedDimensionRatioSide == 1 && (!this.mLeft.isConnected() || !this.mRight.isConnected())) {
            this.mResolvedDimensionRatioSide = 0;
        }
        if (this.mResolvedDimensionRatioSide == -1 && (!this.mTop.isConnected() || !this.mBottom.isConnected() || !this.mLeft.isConnected() || !this.mRight.isConnected())) {
            if (this.mTop.isConnected() && this.mBottom.isConnected()) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (this.mLeft.isConnected() && this.mRight.isConnected()) {
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
        if (this.mResolvedDimensionRatioSide == -1) {
            int i = this.mMatchConstraintMinWidth;
            if (i > 0 && this.mMatchConstraintMinHeight == 0) {
                this.mResolvedDimensionRatioSide = 0;
            } else {
                if (i != 0 || this.mMatchConstraintMinHeight <= 0) {
                    return;
                }
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (this.mType != null) {
            str = "type: " + this.mType + " ";
        } else {
            str = "";
        }
        sb.append(str);
        if (this.mDebugName != null) {
            str2 = "id: " + this.mDebugName + " ";
        }
        sb.append(str2);
        sb.append("(");
        sb.append(this.mX);
        sb.append(", ");
        sb.append(this.mY);
        sb.append(") - (");
        sb.append(this.mWidth);
        sb.append(" x ");
        sb.append(this.mHeight);
        sb.append(")");
        return sb.toString();
    }

    public void updateFromRuns(boolean z, boolean z2) {
        int i;
        int i2;
        boolean zIsResolved = z & this.horizontalRun.isResolved();
        boolean zIsResolved2 = z2 & this.verticalRun.isResolved();
        HorizontalWidgetRun horizontalWidgetRun = this.horizontalRun;
        int i3 = horizontalWidgetRun.start.value;
        VerticalWidgetRun verticalWidgetRun = this.verticalRun;
        int i4 = verticalWidgetRun.start.value;
        int i5 = horizontalWidgetRun.end.value;
        int i6 = verticalWidgetRun.end.value;
        int i7 = i6 - i4;
        if (i5 - i3 < 0 || i7 < 0 || i3 == Integer.MIN_VALUE || i3 == Integer.MAX_VALUE || i4 == Integer.MIN_VALUE || i4 == Integer.MAX_VALUE || i5 == Integer.MIN_VALUE || i5 == Integer.MAX_VALUE || i6 == Integer.MIN_VALUE || i6 == Integer.MAX_VALUE) {
            i5 = 0;
            i3 = 0;
            i6 = 0;
            i4 = 0;
        }
        int i8 = i5 - i3;
        int i9 = i6 - i4;
        if (zIsResolved) {
            this.mX = i3;
        }
        if (zIsResolved2) {
            this.mY = i4;
        }
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (zIsResolved) {
            if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED && i8 < (i2 = this.mWidth)) {
                i8 = i2;
            }
            this.mWidth = i8;
            int i10 = this.mMinWidth;
            if (i8 < i10) {
                this.mWidth = i10;
            }
        }
        if (zIsResolved2) {
            if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED && i9 < (i = this.mHeight)) {
                i9 = i;
            }
            this.mHeight = i9;
            int i11 = this.mMinHeight;
            if (i9 < i11) {
                this.mHeight = i11;
            }
        }
    }

    public void updateFromSolver(LinearSystem linearSystem, boolean z) {
        VerticalWidgetRun verticalWidgetRun;
        HorizontalWidgetRun horizontalWidgetRun;
        int objectVariableValue = linearSystem.getObjectVariableValue(this.mLeft);
        int objectVariableValue2 = linearSystem.getObjectVariableValue(this.mTop);
        int objectVariableValue3 = linearSystem.getObjectVariableValue(this.mRight);
        int objectVariableValue4 = linearSystem.getObjectVariableValue(this.mBottom);
        if (z && (horizontalWidgetRun = this.horizontalRun) != null) {
            DependencyNode dependencyNode = horizontalWidgetRun.start;
            if (dependencyNode.resolved) {
                DependencyNode dependencyNode2 = horizontalWidgetRun.end;
                if (dependencyNode2.resolved) {
                    objectVariableValue = dependencyNode.value;
                    objectVariableValue3 = dependencyNode2.value;
                }
            }
        }
        if (z && (verticalWidgetRun = this.verticalRun) != null) {
            DependencyNode dependencyNode3 = verticalWidgetRun.start;
            if (dependencyNode3.resolved) {
                DependencyNode dependencyNode4 = verticalWidgetRun.end;
                if (dependencyNode4.resolved) {
                    objectVariableValue2 = dependencyNode3.value;
                    objectVariableValue4 = dependencyNode4.value;
                }
            }
        }
        int i = objectVariableValue4 - objectVariableValue2;
        if (objectVariableValue3 - objectVariableValue < 0 || i < 0 || objectVariableValue == Integer.MIN_VALUE || objectVariableValue == Integer.MAX_VALUE || objectVariableValue2 == Integer.MIN_VALUE || objectVariableValue2 == Integer.MAX_VALUE || objectVariableValue3 == Integer.MIN_VALUE || objectVariableValue3 == Integer.MAX_VALUE || objectVariableValue4 == Integer.MIN_VALUE || objectVariableValue4 == Integer.MAX_VALUE) {
            objectVariableValue = 0;
            objectVariableValue4 = 0;
            objectVariableValue2 = 0;
            objectVariableValue3 = 0;
        }
        setFrame(objectVariableValue, objectVariableValue2, objectVariableValue3, objectVariableValue4);
    }
}
