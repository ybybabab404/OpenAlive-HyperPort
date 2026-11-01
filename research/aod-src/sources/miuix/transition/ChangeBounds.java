package miuix.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.transition.MiuixTransitionUtils;
import java.lang.ref.WeakReference;
import java.util.Map;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.property.ViewProperty;
import miuix.internal.util.ViewUtils;

/* JADX INFO: loaded from: classes3.dex */
public class ChangeBounds extends MiuixTransition {
    public static final String[] sTransitionProperties;
    public static Map sViewPropertyMap;
    public final BottomProperty mBottomProperty;
    public final LeftProperty mLeftProperty;
    public boolean mReparent;
    public final RightProperty mRightProperty;
    public int[] mTempLocation;
    public final TopProperty mTopProperty;

    public static class BottomProperty extends ViewProperty implements IIntValueProperty<ViewBounds> {
        public BottomProperty() {
            super("bottom");
        }

        @Override // miuix.animation.property.IIntValueProperty
        public int getIntValue(ViewBounds viewBounds) {
            return viewBounds.getBottom();
        }

        @Override // miuix.animation.property.FloatProperty
        public float getValue(View view) {
            return 0.0f;
        }

        @Override // miuix.animation.property.IIntValueProperty
        public void setIntValue(ViewBounds viewBounds, int i) {
            viewBounds.setBottom(i);
        }

        @Override // miuix.animation.property.FloatProperty
        public void setValue(View view, float f) {
        }
    }

    public static class LeftProperty extends ViewProperty implements IIntValueProperty<ViewBounds> {
        public LeftProperty() {
            super("left");
        }

        @Override // miuix.animation.property.IIntValueProperty
        public int getIntValue(ViewBounds viewBounds) {
            return viewBounds.getLeft();
        }

        @Override // miuix.animation.property.FloatProperty
        public float getValue(View view) {
            return 0.0f;
        }

        @Override // miuix.animation.property.IIntValueProperty
        public void setIntValue(ViewBounds viewBounds, int i) {
            viewBounds.setLeft(i);
        }

        @Override // miuix.animation.property.FloatProperty
        public void setValue(View view, float f) {
        }
    }

    public static class RightProperty extends ViewProperty implements IIntValueProperty<ViewBounds> {
        public RightProperty() {
            super("right");
        }

        @Override // miuix.animation.property.IIntValueProperty
        public int getIntValue(ViewBounds viewBounds) {
            return viewBounds.getRight();
        }

        @Override // miuix.animation.property.FloatProperty
        public float getValue(View view) {
            return 0.0f;
        }

        @Override // miuix.animation.property.IIntValueProperty
        public void setIntValue(ViewBounds viewBounds, int i) {
            viewBounds.setRight(i);
        }

        @Override // miuix.animation.property.FloatProperty
        public void setValue(View view, float f) {
        }
    }

    public static class TopProperty extends ViewProperty implements IIntValueProperty<ViewBounds> {
        public TopProperty() {
            super("top");
        }

        @Override // miuix.animation.property.IIntValueProperty
        public int getIntValue(ViewBounds viewBounds) {
            return viewBounds.getTop();
        }

        @Override // miuix.animation.property.FloatProperty
        public float getValue(View view) {
            return 0.0f;
        }

        @Override // miuix.animation.property.IIntValueProperty
        public void setIntValue(ViewBounds viewBounds, int i) {
            viewBounds.setTop(i);
        }

        @Override // miuix.animation.property.FloatProperty
        public void setValue(View view, float f) {
        }
    }

    public static class ViewBounds {
        private WeakReference<View> mView;
        int left = -1;
        int top = -1;
        int right = -1;
        int bottom = -1;

        public ViewBounds(View view) {
            this.mView = new WeakReference<>(view);
        }

        private void setLeftTopRightBottom() {
            View view = this.mView.get();
            if (view != null) {
                if (!(view instanceof TextView)) {
                    ViewUtils.setLeftTopRightBottom(view, this.left, this.top, this.right, this.bottom);
                } else {
                    view.measure(View.MeasureSpec.makeMeasureSpec(this.right - this.left, 1073741824), View.MeasureSpec.makeMeasureSpec(this.bottom - this.top, 1073741824));
                    view.layout(this.left, this.top, this.right, this.bottom);
                }
            }
        }

        public int getBottom() {
            return this.bottom;
        }

        public int getLeft() {
            return this.left;
        }

        public int getRight() {
            return this.right;
        }

        public int getTop() {
            return this.top;
        }

        public void setBottom(int i) {
            if (this.bottom != i) {
                this.bottom = i;
                setLeftTopRightBottom();
            }
        }

        public void setLeft(int i) {
            if (this.left != i) {
                this.left = i;
                setLeftTopRightBottom();
            }
        }

        public void setRight(int i) {
            if (this.right != i) {
                this.right = i;
                setLeftTopRightBottom();
            }
        }

        public void setTop(int i) {
            if (this.top != i) {
                this.top = i;
                setLeftTopRightBottom();
            }
        }
    }

    static {
        ArrayMap arrayMap = new ArrayMap();
        sViewPropertyMap = arrayMap;
        sTransitionProperties = new String[]{"android:transition:bounds", "android:transition:parent", "android:transition:windowX", "android:transition:windowY"};
        arrayMap.put("android:transition:x", ViewProperty.X);
        sViewPropertyMap.put("android:transition:y", ViewProperty.Y);
        sViewPropertyMap.put("android:transition:width", ViewProperty.WIDTH);
        sViewPropertyMap.put("android:transition:height", ViewProperty.HEIGHT);
    }

    public ChangeBounds() {
        this.mReparent = false;
        this.mTempLocation = new int[2];
        this.mLeftProperty = new LeftProperty();
        this.mTopProperty = new TopProperty();
        this.mRightProperty = new RightProperty();
        this.mBottomProperty = new BottomProperty();
    }

    public ChangeBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mReparent = false;
        this.mTempLocation = new int[2];
        this.mLeftProperty = new LeftProperty();
        this.mTopProperty = new TopProperty();
        this.mRightProperty = new RightProperty();
        this.mBottomProperty = new BottomProperty();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ChangeBounds);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ChangeBounds_reparent, false);
        typedArrayObtainStyledAttributes.recycle();
        setReparent(z);
    }

    @Override // miuix.transition.MiuixTransition
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // miuix.transition.MiuixTransition
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (!ViewCompat.isLaidOut(view) && view.getWidth() == 0 && view.getHeight() == 0) {
            return;
        }
        transitionValues.values.put("android:transition:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        for (Map.Entry entry : sViewPropertyMap.entrySet()) {
            transitionValues.values.put((String) entry.getKey(), Float.valueOf(((ViewProperty) entry.getValue()).getValue(view)));
        }
        transitionValues.values.put("android:transition:parent", transitionValues.view.getParent());
        if (this.mReparent) {
            transitionValues.view.getLocationInWindow(this.mTempLocation);
            transitionValues.values.put("android:transition:windowX", Integer.valueOf(this.mTempLocation[0]));
            transitionValues.values.put("android:transition:windowY", Integer.valueOf(this.mTempLocation[1]));
        }
    }

    @Override // miuix.transition.MiuixTransition
    public void createAnimator(final ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return;
        }
        Map map = transitionValues.values;
        Map map2 = transitionValues2.values;
        ViewGroup viewGroup2 = (ViewGroup) map.get("android:transition:parent");
        ViewGroup viewGroup3 = (ViewGroup) map2.get("android:transition:parent");
        if (viewGroup2 == null || viewGroup3 == null) {
            return;
        }
        final View view = transitionValues2.view;
        final AnimConfig animConfig = new AnimConfig();
        animConfig.addListeners(new TransitionListener() { // from class: miuix.transition.ChangeBounds.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                if (obj == "changebounds_end") {
                    ChangeBounds changeBounds = ChangeBounds.this;
                    if (changeBounds.mNumInstances == 0) {
                        changeBounds.onTransitionStart();
                    }
                    ChangeBounds.this.mNumInstances++;
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                if (obj == "changebounds_end") {
                    ChangeBounds changeBounds = ChangeBounds.this;
                    int i = changeBounds.mNumInstances - 1;
                    changeBounds.mNumInstances = i;
                    if (i == 0) {
                        animConfig.removeListeners(this);
                        ChangeBounds.this.onTransitionEnd();
                    }
                }
            }
        });
        if (parentMatches(viewGroup2, viewGroup3)) {
            Rect rect = (Rect) transitionValues.values.get("android:transition:bounds");
            Rect rect2 = (Rect) transitionValues2.values.get("android:transition:bounds");
            int i = rect.left;
            int i2 = rect2.left;
            int i3 = rect.top;
            int i4 = rect2.top;
            int i5 = rect.right;
            int i6 = rect2.right;
            int i7 = rect.bottom;
            int i8 = rect2.bottom;
            if (i == i2 && i3 == i4 && i5 == i6 && i7 == i8) {
                return;
            }
            final ViewGroup viewGroup4 = (ViewGroup) view.getParent();
            ((ViewGroup) view.getParent()).suppressLayout(true);
            animConfig.addListeners(new TransitionListener() { // from class: miuix.transition.ChangeBounds.2
                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    if (obj == "changebounds_end") {
                        animConfig.removeListeners(this);
                        viewGroup4.suppressLayout(false);
                    }
                }
            });
            ViewBounds viewBounds = new ViewBounds(view);
            ViewUtils.setLeftTopRightBottom(view, i, i3, i5, i7);
            IStateStyle iStateStyleUseValue = Folme.useValue(viewBounds);
            AnimState animStateAdd = new AnimState("changebounds_start").add((ViewProperty) this.mLeftProperty, i, 4).add((ViewProperty) this.mTopProperty, i3, 4).add((ViewProperty) this.mRightProperty, i5, 4).add((ViewProperty) this.mBottomProperty, i7, 4);
            AnimState animStateAdd2 = new AnimState("changebounds_end").add((ViewProperty) this.mLeftProperty, i2, 4).add((ViewProperty) this.mTopProperty, i4, 4).add((ViewProperty) this.mRightProperty, i6, 4).add((ViewProperty) this.mBottomProperty, i8, 4);
            iStateStyleUseValue.setTo(animStateAdd);
            addTransitionRunner(new MiuixTransition.TransitionRunner(viewBounds, animStateAdd, animStateAdd2, getAnimConfig(), animConfig));
            return;
        }
        viewGroup.getLocationInWindow(this.mTempLocation);
        int iIntValue = ((Integer) transitionValues.values.get("android:transition:windowX")).intValue() - this.mTempLocation[0];
        int iIntValue2 = ((Integer) transitionValues.values.get("android:transition:windowY")).intValue() - this.mTempLocation[1];
        int iIntValue3 = ((Integer) transitionValues2.values.get("android:transition:windowX")).intValue() - this.mTempLocation[0];
        int iIntValue4 = ((Integer) transitionValues2.values.get("android:transition:windowY")).intValue() - this.mTempLocation[1];
        float fFloatValue = ((Float) transitionValues.values.get("android:transition:width")).floatValue() + 0.0f;
        float fFloatValue2 = ((Float) transitionValues.values.get("android:transition:height")).floatValue() + 0.0f;
        float fFloatValue3 = ((Float) transitionValues2.values.get("android:transition:width")).floatValue() + 0.0f;
        float fFloatValue4 = ((Float) transitionValues2.values.get("android:transition:height")).floatValue() + 0.0f;
        Rect rect3 = new Rect();
        transitionValues.view.getLocalVisibleRect(rect3);
        if (rect3.top > 0 || rect3.bottom < fFloatValue2) {
            return;
        }
        if (iIntValue == iIntValue3 && iIntValue2 == iIntValue4) {
            return;
        }
        final View viewCopyViewImage = MiuixTransitionUtils.copyViewImage(viewGroup, view, viewGroup2);
        viewGroup.getOverlay().add(viewCopyViewImage);
        final float alpha = view.getAlpha();
        view.setAlpha(0.0f);
        AnimState animState = new AnimState("changebounds_start");
        ViewProperty viewProperty = ViewProperty.X;
        AnimState animStateAdd3 = animState.add(viewProperty, iIntValue);
        ViewProperty viewProperty2 = ViewProperty.Y;
        AnimState animStateAdd4 = animStateAdd3.add(viewProperty2, iIntValue2);
        ViewProperty viewProperty3 = ViewProperty.WIDTH;
        AnimState animStateAdd5 = animStateAdd4.add(viewProperty3, fFloatValue);
        ViewProperty viewProperty4 = ViewProperty.HEIGHT;
        AnimState animStateAdd6 = animStateAdd5.add(viewProperty4, fFloatValue2);
        AnimState animStateAdd7 = new AnimState("changebounds_end").add(viewProperty, iIntValue3).add(viewProperty2, iIntValue4).add(viewProperty3, fFloatValue3).add(viewProperty4, fFloatValue4);
        animConfig.addListeners(new TransitionListener() { // from class: miuix.transition.ChangeBounds.3
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                if (obj == "changebounds_end") {
                    animConfig.removeListeners(this);
                    viewGroup.getOverlay().remove(viewCopyViewImage);
                    view.setAlpha(alpha);
                }
            }
        });
        Folme.useAt(viewCopyViewImage).state().setTo(animStateAdd6);
        addTransitionRunner(new MiuixTransition.TransitionRunner(viewCopyViewImage, animStateAdd6, animStateAdd7, getAnimConfig(), animConfig));
    }

    @Override // miuix.transition.MiuixTransition
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public boolean parentMatches(View view, View view2) {
        if (!this.mReparent) {
            return true;
        }
        TransitionValues matchedTransitionValues = getMatchedTransitionValues(view, true);
        if (matchedTransitionValues == null) {
            return view == view2;
        }
        return view2 == matchedTransitionValues.view;
    }

    public ChangeBounds setReparent(boolean z) {
        this.mReparent = z;
        return this;
    }
}
