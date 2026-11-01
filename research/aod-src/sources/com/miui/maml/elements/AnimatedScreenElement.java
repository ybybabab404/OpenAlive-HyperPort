package com.miui.maml.elements;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import androidx.collection.ArraySet;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.animation.AlphaAnimation;
import com.miui.maml.animation.BaseAnimation;
import com.miui.maml.animation.PositionAnimation;
import com.miui.maml.animation.RotationAnimation;
import com.miui.maml.animation.ScaleAnimation;
import com.miui.maml.animation.SizeAnimation;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.maml.folme.AnimatedTarget;
import com.miui.maml.folme.MamlTransitionListener;
import com.miui.maml.folme.PropertyWrapper;
import com.miui.maml.folme.TransitionListenerWrapper;
import com.miui.maml.util.ColorParser;
import com.miui.maml.util.MamlLog;
import com.miui.maml.util.Utils;
import com.xiaomi.onetrack.h.d.a;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.property.FloatProperty;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AnimatedScreenElement extends ScreenElement {
    public static final String LOG_TAG = "AnimatedScreenElement";
    private static final int S_PAINT_COLOR = -4982518;
    private static final ThreadLocal<Camera> sLocalCamera = new ThreadLocal<>();
    private IndexedVariable mActualXVar;
    private IndexedVariable mActualYVar;
    protected int mAlpha;
    public PropertyWrapper mAlphaProperty;
    private AlphaAnimation mAlphas;
    private AnimatedTarget mAnimTarget;
    protected String mContentDescription;
    protected Expression mContentDescriptionExp;
    private boolean mFolmeMode;
    protected boolean mHasContentDescription;
    public PropertyWrapper mHeightProperty;
    protected boolean mInterceptTouch;
    private boolean mIsHaptic;
    private MamlTransitionListener mListener;
    private TransitionListenerWrapper mListenerWrapper;
    private float mMarginBottom;
    private float mMarginLeft;
    private float mMarginRight;
    private float mMarginTop;
    private Matrix mMatrix;
    private Paint mPaint;
    public PropertyWrapper mPivotXProperty;
    public PropertyWrapper mPivotYProperty;
    public PropertyWrapper mPivotZProperty;
    private PositionAnimation mPositions;
    protected boolean mPressed;
    public PropertyWrapper mRotationProperty;
    public PropertyWrapper mRotationXProperty;
    public PropertyWrapper mRotationYProperty;
    public PropertyWrapper mRotationZProperty;
    private RotationAnimation mRotations;
    private Expression mScaleExpression;
    public PropertyWrapper mScaleXProperty;
    public PropertyWrapper mScaleYProperty;
    private ScaleAnimation mScales;
    private SizeAnimation mSizes;
    protected FunctionElement mTickListener;
    protected boolean mTintChanged;
    protected int mTintColor;
    protected ColorParser mTintColorParser;
    public PropertyWrapper mTintColorProperty;
    protected PorterDuffColorFilter mTintFilter;
    protected PorterDuff.Mode mTintMode;
    protected Expression mTintModeExp;
    public CopyOnWriteArraySet<FloatProperty> mToProperties;
    protected boolean mTouchable;
    private int mVirtualViewId;
    public PropertyWrapper mWidthProperty;
    public PropertyWrapper mXProperty;
    public PropertyWrapper mYProperty;

    public AnimatedScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mToProperties = new CopyOnWriteArraySet<>();
        this.mTintMode = PorterDuff.Mode.SRC_IN;
        this.mTintChanged = true;
        this.mMatrix = new Matrix();
        this.mPaint = new Paint();
        this.mVirtualViewId = Integer.MIN_VALUE;
        this.mListener = new MamlTransitionListener(this);
        load(element);
        if (this.mHasContentDescription) {
            this.mRoot.addAccessibleElements(this);
        }
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(1.0f);
        this.mPaint.setColor(S_PAINT_COLOR);
        this.mListenerWrapper = new TransitionListenerWrapper(this.mListener);
    }

    private Expression createExp(Variables variables, Element element, String str, String str2) {
        Expression expressionBuild = Expression.build(variables, getAttr(element, str));
        return (expressionBuild != null || TextUtils.isEmpty(str2)) ? expressionBuild : Expression.build(variables, getAttr(element, str2));
    }

    private void folmeFromToImpl(final AnimState animState, final AnimState animState2, final AnimConfig animConfig) {
        getContext().getHandler().post(new Runnable() { // from class: com.miui.maml.elements.AnimatedScreenElement.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Folme.useAt(AnimatedScreenElement.this.getAnimTarget()).state().fromTo(animState, animState2, animConfig);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void folmeSetToImpl(final AnimState animState) {
        getContext().getHandler().post(new Runnable() { // from class: com.miui.maml.elements.AnimatedScreenElement.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Folme.useAt(AnimatedScreenElement.this.getAnimTarget()).state().setTo(animState);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void folmeToImpl(final AnimState animState, final AnimConfig animConfig) {
        getContext().getHandler().post(new Runnable() { // from class: com.miui.maml.elements.AnimatedScreenElement.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Folme.useAt(AnimatedScreenElement.this.getAnimTarget()).state().to(animState, animConfig);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleCancel() {
        if (this.mTouchable && this.mPressed) {
            this.mPressed = false;
            performAction("cancel");
            onActionCancel();
        }
    }

    private boolean isInMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private void load(Element element) {
        Expression expression;
        Expression expression2;
        Expression expression3;
        Expression expression4;
        Expression expressionCreateExp;
        Expression expressionCreateExp2;
        Expression expressionCreateExp3;
        Expression expressionCreateExp4;
        Expression expression5;
        Expression expression6;
        Expression expressionCreateExp5;
        Expression expression7;
        Expression expression8;
        Expression expressionCreateExp6;
        boolean z;
        Variables variables = getVariables();
        if (element != null) {
            this.mScaleExpression = createExp(variables, element, "scale", null);
            Expression expressionCreateExp7 = createExp(variables, element, AnimatedProperty.PROPERTY_NAME_X, "left");
            expressionCreateExp6 = createExp(variables, element, AnimatedProperty.PROPERTY_NAME_Y, "top");
            Expression expressionCreateExp8 = createExp(variables, element, AnimatedProperty.PROPERTY_NAME_W, "width");
            Expression expressionCreateExp9 = createExp(variables, element, AnimatedProperty.PROPERTY_NAME_H, "height");
            Expression expressionCreateExp10 = createExp(variables, element, "angle", AnimatedProperty.PROPERTY_NAME_ROTATION);
            Expression expressionCreateExp11 = createExp(variables, element, "centerX", AnimatedProperty.PROPERTY_NAME_PIVOT_X);
            Expression expressionCreateExp12 = createExp(variables, element, "centerY", AnimatedProperty.PROPERTY_NAME_PIVOT_Y);
            expressionCreateExp = createExp(variables, element, AnimatedProperty.PROPERTY_NAME_ALPHA, null);
            expressionCreateExp2 = createExp(variables, element, AnimatedProperty.PROPERTY_NAME_SCALE_X, null);
            Expression expressionCreateExp13 = createExp(variables, element, AnimatedProperty.PROPERTY_NAME_SCALE_Y, null);
            expressionCreateExp3 = createExp(variables, element, "angleX", AnimatedProperty.PROPERTY_NAME_ROTATION_X);
            expressionCreateExp4 = createExp(variables, element, "angleY", AnimatedProperty.PROPERTY_NAME_ROTATION_Y);
            expression6 = expressionCreateExp13;
            expressionCreateExp5 = createExp(variables, element, "angleZ", AnimatedProperty.PROPERTY_NAME_ROTATION_Z);
            Expression expressionCreateExp14 = createExp(variables, element, "centerZ", AnimatedProperty.PROPERTY_NAME_PIVOT_Z);
            if (this.mHasName) {
                this.mActualXVar = new IndexedVariable(this.mName + "." + ScreenElement.ACTUAL_X, variables, true);
                z = true;
                this.mActualYVar = new IndexedVariable(this.mName + "." + ScreenElement.ACTUAL_Y, variables, true);
            } else {
                z = true;
            }
            this.mTouchable = Boolean.parseBoolean(getAttr(element, "touchable"));
            this.mInterceptTouch = Boolean.parseBoolean(getAttr(element, "interceptTouch"));
            this.mIsHaptic = Boolean.parseBoolean(getAttr(element, "haptic"));
            this.mContentDescription = getAttr(element, "contentDescription");
            this.mContentDescriptionExp = Expression.build(variables, getAttr(element, "contentDescriptionExp"));
            this.mHasContentDescription = (TextUtils.isEmpty(this.mContentDescription) && this.mContentDescriptionExp == null) ? false : z;
            this.mMarginLeft = Utils.getAttrAsFloat(element, "marginLeft", 0.0f);
            this.mMarginRight = Utils.getAttrAsFloat(element, "marginRight", 0.0f);
            this.mMarginTop = Utils.getAttrAsFloat(element, "marginTop", 0.0f);
            this.mMarginBottom = Utils.getAttrAsFloat(element, "marginBottom", 0.0f);
            String attr = getAttr(element, "tint");
            if (!TextUtils.isEmpty(attr)) {
                this.mTintColorParser = new ColorParser(variables, attr);
            }
            this.mTintModeExp = Expression.build(variables, getAttr(element, "tintmode"));
            ColorParser colorParser = this.mTintColorParser;
            this.mTintColor = colorParser != null ? colorParser.getColor() : 0;
            this.mFolmeMode = Boolean.parseBoolean(getAttr(element, "folmeMode"));
            expression5 = expressionCreateExp11;
            expression8 = expressionCreateExp14;
            expression = expressionCreateExp7;
            expression3 = expressionCreateExp9;
            expression7 = expressionCreateExp12;
            expression4 = expressionCreateExp10;
            expression2 = expressionCreateExp8;
        } else {
            expression = null;
            expression2 = null;
            expression3 = null;
            expression4 = null;
            expressionCreateExp = null;
            expressionCreateExp2 = null;
            expressionCreateExp3 = null;
            expressionCreateExp4 = null;
            expression5 = null;
            expression6 = null;
            expressionCreateExp5 = null;
            expression7 = null;
            expression8 = null;
            expressionCreateExp6 = null;
        }
        this.mXProperty = new PropertyWrapper(this.mName + ".x", variables, expression, isInFolmeMode(), BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        this.mYProperty = new PropertyWrapper(this.mName + ".y", variables, expressionCreateExp6, isInFolmeMode(), BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        this.mWidthProperty = new PropertyWrapper(this.mName + ".w", variables, expression2, isInFolmeMode(), -1.0d);
        this.mHeightProperty = new PropertyWrapper(this.mName + ".h", variables, expression3, isInFolmeMode(), -1.0d);
        this.mRotationProperty = new PropertyWrapper(this.mName + ".rotation", variables, expression4, isInFolmeMode(), BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        this.mAlphaProperty = new PropertyWrapper(this.mName + ".alpha", variables, expressionCreateExp, isInFolmeMode(), 255.0d);
        this.mRotationXProperty = new PropertyWrapper(this.mName + ".rotationX", variables, expressionCreateExp3, isInFolmeMode(), BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        this.mRotationYProperty = new PropertyWrapper(this.mName + ".rotationY", variables, expressionCreateExp4, isInFolmeMode(), BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        this.mRotationZProperty = new PropertyWrapper(this.mName + ".rotationZ", variables, expressionCreateExp5, isInFolmeMode(), BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        this.mScaleXProperty = new PropertyWrapper(this.mName + ".scaleX", variables, expressionCreateExp2, isInFolmeMode(), 1.0d);
        this.mScaleYProperty = new PropertyWrapper(this.mName + ".scaleY", variables, expression6, isInFolmeMode(), 1.0d);
        this.mTintColorProperty = new PropertyWrapper(this.mName + ".tintColor", variables, null, isInFolmeMode(), this.mTintColor);
        this.mPivotXProperty = new PropertyWrapper(this.mName + ".pivotX", variables, expression5, isInFolmeMode(), BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        this.mPivotYProperty = new PropertyWrapper(this.mName + ".pivotY", variables, expression7, isInFolmeMode(), BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        this.mPivotZProperty = new PropertyWrapper(this.mName + ".pivotZ", variables, expression8, isInFolmeMode(), BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
    }

    private void setupToProperties(StateElement stateElement) {
        Iterator<String> it = stateElement.getProperties().iterator();
        while (it.hasNext()) {
            FloatProperty propertyByName = AnimatedProperty.getPropertyByName(it.next());
            if (propertyByName != null) {
                this.mToProperties.add(propertyByName);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00d2  */
    public void doRenderWithTranslation(Canvas canvas) {
        Canvas canvas2;
        int iSave = canvas.save();
        this.mMatrix.reset();
        float rotationX = getRotationX();
        float rotationY = getRotationY();
        float rotationZ = getRotationZ();
        if (rotationX != 0.0f || rotationY != 0.0f || rotationZ != 0.0f) {
            ThreadLocal<Camera> threadLocal = sLocalCamera;
            if (threadLocal.get() == null) {
                threadLocal.set(new Camera());
            }
            Camera camera = threadLocal.get();
            camera.save();
            camera.rotate(rotationX, rotationY, rotationZ);
            float pivotZ = getPivotZ();
            if (pivotZ != 0.0f) {
                camera.translate(0.0f, 0.0f, pivotZ);
            }
            camera.getMatrix(this.mMatrix);
            camera.restore();
        }
        float rotation = getRotation();
        if (rotation != 0.0f) {
            this.mMatrix.preRotate(rotation);
        }
        float scaleX = getScaleX();
        float scaleY = getScaleY();
        if (scaleX != 1.0f || scaleY != 1.0f) {
            this.mMatrix.preScale(scaleX, scaleY);
        }
        float x = getX();
        float y = getY();
        float pivotX = getPivotX() - (x - getLeft());
        float pivotY = getPivotY() - (y - getTop());
        this.mMatrix.preTranslate(-pivotX, -pivotY);
        this.mMatrix.postTranslate(pivotX + x, pivotY + y);
        canvas.concat(this.mMatrix);
        doRender(canvas);
        if (this.mRoot.mShowDebugLayout) {
            float width = getWidth();
            float height = getHeight();
            if (width <= 0.0f || height <= 0.0f) {
                canvas2 = canvas;
            } else {
                float left = getLeft(0.0f, width);
                float top = getTop(0.0f, height);
                canvas2 = canvas;
                canvas2.drawRect(left, top, left + width, top + height, this.mPaint);
            }
        } else {
            canvas2 = canvas;
        }
        canvas2.restoreToCount(iSave);
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void doTick(long j) {
        super.doTick(j);
        if (this.mHasName) {
            this.mActualXVar.set(descale(getX()));
            this.mActualYVar.set(descale(getY()));
        }
        int iEvaluateAlpha = evaluateAlpha();
        this.mAlpha = iEvaluateAlpha;
        if (iEvaluateAlpha < 0) {
            iEvaluateAlpha = 0;
        }
        this.mAlpha = iEvaluateAlpha;
        this.mTintChanged = false;
        int tintColor = getTintColor();
        if (tintColor != this.mTintColor) {
            this.mTintChanged = true;
            this.mTintColor = tintColor;
        }
        if (this.mTintColor != 0) {
            PorterDuff.Mode porterDuffMode = this.mTintMode;
            Expression expression = this.mTintModeExp;
            if (expression != null) {
                porterDuffMode = Utils.getPorterDuffMode((int) expression.evaluate(), this.mTintMode);
            }
            if (this.mTintMode != porterDuffMode) {
                this.mTintMode = porterDuffMode;
                this.mTintChanged = true;
            }
            if (this.mTintFilter == null) {
                this.mTintChanged = true;
            }
            if (this.mTintChanged) {
                this.mTintFilter = new PorterDuffColorFilter(this.mTintColor, porterDuffMode);
            }
        } else {
            this.mTintFilter = null;
        }
        FunctionElement functionElement = this.mTickListener;
        if (functionElement != null) {
            functionElement.perform();
        }
    }

    public int evaluateAlpha() {
        int value = (int) this.mAlphaProperty.getValue();
        if (!isInFolmeMode()) {
            AlphaAnimation alphaAnimation = this.mAlphas;
            value = Utils.mixAlpha(value, alphaAnimation != null ? alphaAnimation.getAlpha() : 255);
        }
        ElementGroup elementGroup = this.mParent;
        return (elementGroup == null || (elementGroup instanceof LayerScreenElement) || (elementGroup != null && this.mParent.isLayered())) ? value : Utils.mixAlpha(value, this.mParent.getAlpha());
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        getContext().getHandler().removeCallbacksAndMessages(this);
        try {
            if (this.mAnimTarget == null || !getRoot().needFolmeClean()) {
                return;
            }
            Folme.clean(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void folmeCancel(Expression[] expressionArr) {
        try {
            if (expressionArr == null) {
                Folme.useAt(getAnimTarget()).state().cancel();
                this.mToProperties.clear();
                return;
            }
            ArraySet arraySet = new ArraySet();
            for (Expression expression : expressionArr) {
                FloatProperty propertyByName = AnimatedProperty.getPropertyByName(expression.evaluateStr());
                if (propertyByName != null) {
                    arraySet.add(propertyByName);
                    this.mToProperties.remove(propertyByName);
                }
            }
            Folme.useAt(getAnimTarget()).state().cancel((FloatProperty[]) arraySet.toArray(new FloatProperty[arraySet.size()]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void folmeFromTo(String str, String str2, String str3) {
        ScreenElement screenElementFindElement = getRoot().findElement(str);
        ScreenElement screenElementFindElement2 = getRoot().findElement(str2);
        ScreenElement screenElementFindElement3 = getRoot().findElement(str3);
        if (!(screenElementFindElement instanceof StateElement) || !(screenElementFindElement2 instanceof StateElement)) {
            MamlLog.w(LOG_TAG, "folmeFromTo: wrong state name " + str + " " + str2);
            return;
        }
        StateElement stateElement = (StateElement) screenElementFindElement;
        StateElement stateElement2 = (StateElement) screenElementFindElement2;
        ConfigElement configElement = screenElementFindElement3 instanceof ConfigElement ? (ConfigElement) screenElementFindElement3 : null;
        AnimConfig animConfig = configElement != null ? configElement.getAnimConfig(this.mListenerWrapper) : new AnimConfig();
        AnimState animState = stateElement.getAnimState(AnimatedTarget.STATE_TAG_FROM);
        AnimState animState2 = stateElement2.getAnimState(AnimatedTarget.STATE_TAG_TO);
        setupToProperties(stateElement2);
        folmeFromToImpl(animState, animState2, animConfig);
    }

    public void folmeSetTo(String str) {
        ScreenElement screenElementFindElement = getRoot().findElement(str);
        if (screenElementFindElement instanceof StateElement) {
            folmeSetToImpl(((StateElement) screenElementFindElement).getAnimState(AnimatedTarget.STATE_TAG_SET_TO));
            return;
        }
        MamlLog.w(LOG_TAG, "folmeSetTo: wrong state name " + str);
    }

    public void folmeTo(String str, String str2) {
        ScreenElement screenElementFindElement = getRoot().findElement(str);
        ScreenElement screenElementFindElement2 = getRoot().findElement(str2);
        if (!(screenElementFindElement instanceof StateElement)) {
            MamlLog.w(LOG_TAG, "folmeTo: wrong state name " + str);
            return;
        }
        StateElement stateElement = (StateElement) screenElementFindElement;
        ConfigElement configElement = screenElementFindElement2 instanceof ConfigElement ? (ConfigElement) screenElementFindElement2 : null;
        AnimConfig animConfig = configElement != null ? configElement.getAnimConfig(this.mListenerWrapper) : new AnimConfig();
        AnimState animState = stateElement.getAnimState(AnimatedTarget.STATE_TAG_TO);
        setupToProperties(stateElement);
        folmeToImpl(animState, animConfig);
    }

    public float getAbsoluteLeft() {
        float left = getLeft();
        ElementGroup elementGroup = this.mParent;
        return left + (elementGroup == null ? 0.0f : elementGroup.getParentLeft());
    }

    public float getAbsoluteTop() {
        float top = getTop();
        ElementGroup elementGroup = this.mParent;
        return top + (elementGroup == null ? 0.0f : elementGroup.getParentTop());
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public AnimatedTarget getAnimTarget() {
        if (this.mAnimTarget == null) {
            this.mAnimTarget = (AnimatedTarget) Folme.getTarget(this, AnimatedTarget.sCreator);
        }
        return this.mAnimTarget;
    }

    public String getContentDescription() {
        Expression expression = this.mContentDescriptionExp;
        if (expression == null) {
            return this.mContentDescription;
        }
        String strEvaluateStr = expression.evaluateStr();
        if (strEvaluateStr != null) {
            return strEvaluateStr;
        }
        MamlLog.e(LOG_TAG, "element.getContentDescription() == null " + this.mName);
        return "";
    }

    public float getHeight() {
        return scale(getHeightRaw());
    }

    public float getHeightRaw() {
        SizeAnimation sizeAnimation;
        return (isInFolmeMode() || (sizeAnimation = this.mSizes) == null) ? (float) this.mHeightProperty.getValue() : (float) sizeAnimation.getHeight();
    }

    public float getLeft() {
        return getLeft(getX(), getWidth());
    }

    public final float getMarginBottom() {
        return scale(this.mMarginBottom);
    }

    public final float getMarginLeft() {
        return scale(this.mMarginLeft);
    }

    public final float getMarginRight() {
        return scale(this.mMarginRight);
    }

    public final float getMarginTop() {
        return scale(this.mMarginTop);
    }

    public Matrix getMatrix() {
        return this.mMatrix;
    }

    public float getPivotX() {
        return scale(this.mPivotXProperty.getValue());
    }

    public float getPivotY() {
        return scale(this.mPivotYProperty.getValue());
    }

    public float getPivotZ() {
        return scale(this.mPivotZProperty.getValue());
    }

    public float getRotation() {
        RotationAnimation rotationAnimation;
        float value = (float) this.mRotationProperty.getValue();
        return (isInFolmeMode() || (rotationAnimation = this.mRotations) == null) ? value : value + rotationAnimation.getAngle();
    }

    public float getRotationX() {
        return (float) this.mRotationXProperty.getValue();
    }

    public float getRotationY() {
        return (float) this.mRotationYProperty.getValue();
    }

    public float getRotationZ() {
        return (float) this.mRotationZProperty.getValue();
    }

    public float getScaleX() {
        float value = (float) this.mScaleXProperty.getValue();
        if (!isInFolmeMode()) {
            Expression expression = this.mScaleExpression;
            if (expression != null) {
                value = (float) expression.evaluate();
            }
            ScaleAnimation scaleAnimation = this.mScales;
            if (scaleAnimation != null) {
                return (float) (((double) value) * scaleAnimation.getScaleX());
            }
        }
        return value;
    }

    public float getScaleY() {
        float value = (float) this.mScaleYProperty.getValue();
        if (!isInFolmeMode()) {
            Expression expression = this.mScaleExpression;
            if (expression != null) {
                value = (float) expression.evaluate();
            }
            ScaleAnimation scaleAnimation = this.mScales;
            if (scaleAnimation != null) {
                return (float) (((double) value) * scaleAnimation.getScaleY());
            }
        }
        return value;
    }

    public int getTintColor() {
        ColorParser colorParser;
        return (isInFolmeMode() || (colorParser = this.mTintColorParser) == null) ? (int) this.mTintColorProperty.getValue() : colorParser.getColor();
    }

    public float getTop() {
        return getTop(getY(), getHeight());
    }

    public float getWidth() {
        return scale(getWidthRaw());
    }

    public float getWidthRaw() {
        SizeAnimation sizeAnimation;
        return (isInFolmeMode() || (sizeAnimation = this.mSizes) == null) ? (float) this.mWidthProperty.getValue() : (float) sizeAnimation.getWidth();
    }

    public float getX() {
        PositionAnimation positionAnimation;
        float value = (float) this.mXProperty.getValue();
        if (!isInFolmeMode() && (positionAnimation = this.mPositions) != null) {
            value = (float) (((double) value) + positionAnimation.getX());
        }
        return scale(value);
    }

    public float getY() {
        PositionAnimation positionAnimation;
        float value = (float) this.mYProperty.getValue();
        if (!isInFolmeMode() && (positionAnimation = this.mPositions) != null) {
            value = (float) (((double) value) + positionAnimation.getY());
        }
        return scale(value);
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        if (isInFolmeMode()) {
            initProperties();
        }
    }

    public void initProperties() {
        this.mXProperty.init();
        this.mYProperty.init();
        this.mWidthProperty.init();
        this.mHeightProperty.init();
        this.mRotationProperty.init();
        this.mAlphaProperty.init();
        this.mRotationXProperty.init();
        this.mRotationYProperty.init();
        this.mRotationZProperty.init();
        this.mScaleXProperty.init();
        this.mScaleYProperty.init();
        this.mTintColorProperty.init();
        this.mPivotXProperty.init();
        this.mPivotYProperty.init();
        this.mPivotZProperty.init();
    }

    public boolean isInFolmeMode() {
        return this.mFolmeMode && this.mHasName;
    }

    public void onActionCancel() {
    }

    public void onActionDown(float f, float f2) {
        this.mRoot.onUIInteractive(this, "down");
    }

    public void onActionMove(float f, float f2) {
        this.mRoot.onUIInteractive(this, "move");
    }

    public void onActionUp() {
        this.mRoot.onUIInteractive(this, a.j);
    }

    @Override // com.miui.maml.elements.ScreenElement
    public BaseAnimation onCreateAnimation(String str, Element element) {
        if (AlphaAnimation.TAG_NAME.equals(str)) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(element, this);
            this.mAlphas = alphaAnimation;
            return alphaAnimation;
        }
        if (PositionAnimation.TAG_NAME.equals(str)) {
            PositionAnimation positionAnimation = new PositionAnimation(element, this);
            this.mPositions = positionAnimation;
            return positionAnimation;
        }
        if (RotationAnimation.TAG_NAME.equals(str)) {
            RotationAnimation rotationAnimation = new RotationAnimation(element, this);
            this.mRotations = rotationAnimation;
            return rotationAnimation;
        }
        if (SizeAnimation.TAG_NAME.equals(str)) {
            SizeAnimation sizeAnimation = new SizeAnimation(element, this);
            this.mSizes = sizeAnimation;
            return sizeAnimation;
        }
        if (!ScaleAnimation.TAG_NAME.equals(str)) {
            return super.onCreateAnimation(str, element);
        }
        ScaleAnimation scaleAnimation = new ScaleAnimation(element, this);
        this.mScales = scaleAnimation;
        return scaleAnimation;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public boolean onHover(MotionEvent motionEvent) {
        if (isVisible() && this.mHasContentDescription) {
            String contentDescription = getContentDescription();
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            boolean zOnHover = super.onHover(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 7) {
                if (actionMasked == 9 && touched(x, y)) {
                    this.mRoot.onHoverChange(this, contentDescription);
                    zOnHover = true;
                }
            } else if (touched(x, y)) {
                if (this.mRoot.getHoverElement() != this) {
                    this.mRoot.onHoverChange(this, contentDescription);
                }
                zOnHover = true;
            }
            if (zOnHover) {
                requestUpdate();
            }
            if (zOnHover && this.mInterceptTouch) {
                return true;
            }
        }
        return false;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void onSetAnimBefore() {
        this.mAlphas = null;
        this.mPositions = null;
        this.mRotations = null;
        this.mSizes = null;
        this.mScales = null;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void onSetAnimEnable(BaseAnimation baseAnimation) {
        if (baseAnimation instanceof AlphaAnimation) {
            this.mAlphas = (AlphaAnimation) baseAnimation;
            return;
        }
        if (baseAnimation instanceof PositionAnimation) {
            this.mPositions = (PositionAnimation) baseAnimation;
            return;
        }
        if (baseAnimation instanceof RotationAnimation) {
            this.mRotations = (RotationAnimation) baseAnimation;
        } else if (baseAnimation instanceof SizeAnimation) {
            this.mSizes = (SizeAnimation) baseAnimation;
        } else if (baseAnimation instanceof ScaleAnimation) {
            this.mScales = (ScaleAnimation) baseAnimation;
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public boolean onTouch(MotionEvent motionEvent) {
        if (isVisible() && this.mTouchable) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            boolean zOnTouch = super.onTouch(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked != 2) {
                        if (actionMasked == 3) {
                            handleCancel();
                        }
                    } else if (this.mPressed) {
                        zOnTouch = touched(x, y);
                        performAction("move");
                        onActionMove(x, y);
                    }
                } else if (this.mPressed) {
                    this.mPressed = false;
                    if (touched(x, y)) {
                        if (this.mIsHaptic) {
                            this.mRoot.haptic(1);
                        }
                        performAction(a.j);
                        onActionUp();
                    } else {
                        performAction("cancel");
                        onActionCancel();
                    }
                    zOnTouch = true;
                }
            } else if (touched(x, y)) {
                this.mPressed = true;
                if (this.mIsHaptic) {
                    this.mRoot.haptic(1);
                }
                performAction("down");
                onActionDown(x, y);
                zOnTouch = true;
            }
            if (zOnTouch) {
                requestUpdate();
            }
            if (zOnTouch && this.mInterceptTouch) {
                return true;
            }
        }
        return false;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void onVisibilityChange(boolean z) {
        super.onVisibilityChange(z);
        if (z) {
            return;
        }
        handleCancel();
        if (this.mVirtualViewId == Integer.MIN_VALUE || getRoot().getMamlAccessHelper() == null || getRoot().getMamlAccessHelper().getFocusedVirtualView() != this.mVirtualViewId) {
            return;
        }
        getRoot().getMamlAccessHelper().performAccessibilityAction(this.mVirtualViewId, 128);
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void pause() {
        super.pause();
        handleCancel();
        try {
            if (this.mAnimTarget == null || this.mToProperties.size() <= 0) {
                return;
            }
            Folme.useAt(this.mAnimTarget).state().end(this.mToProperties.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void render(Canvas canvas) {
        updateVisibility();
        if (isVisible()) {
            doRenderWithTranslation(canvas);
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void setColorFilter(ColorFilter colorFilter) {
        Paint paint = this.mPaint;
        if (paint != null) {
            paint.setColorFilter(colorFilter);
        }
    }

    public void setHeight(double d) {
        this.mHeightProperty.setValue(descale(d));
    }

    public void setOnTickListener(FunctionElement functionElement) {
        this.mTickListener = functionElement;
    }

    public void setVirtualViewId(int i) {
        this.mVirtualViewId = i;
    }

    public void setWidth(double d) {
        this.mWidthProperty.setValue(descale(d));
    }

    public void setX(double d) {
        this.mXProperty.setValue(descale(d));
    }

    public void setY(double d) {
        this.mYProperty.setValue(descale(d));
    }

    public boolean touched(float f, float f2) {
        return touched(f, f2, true);
    }

    public boolean touched(float f, float f2, boolean z) {
        if (z) {
            ElementGroup elementGroup = this.mParent;
            float parentLeft = elementGroup == null ? 0.0f : elementGroup.getParentLeft();
            ElementGroup elementGroup2 = this.mParent;
            f -= parentLeft;
            f2 -= elementGroup2 != null ? elementGroup2.getParentTop() : 0.0f;
        }
        float left = getLeft();
        float top = getTop();
        return f >= left && f <= left + getWidth() && f2 >= top && f2 <= top + getHeight();
    }

    public void unsetOnTickListener() {
        this.mTickListener = null;
    }
}
