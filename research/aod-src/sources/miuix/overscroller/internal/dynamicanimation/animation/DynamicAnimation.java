package miuix.overscroller.internal.dynamicanimation.animation;

import android.util.AndroidRuntimeException;
import android.view.View;
import com.miui.maml.folme.AnimatedProperty;
import java.util.ArrayList;
import miuix.animation.internal.FolmeEngine;
import miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation;

/* JADX INFO: loaded from: classes2.dex */
public abstract class DynamicAnimation<T extends DynamicAnimation<T>> implements AnimationHandler.AnimationFrameCallback {
    public final FloatPropertyCompat mProperty;
    public static final ViewProperty TRANSLATION_X = new ViewProperty("translationX") { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.1
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getTranslationX();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setTranslationX(f);
        }
    };
    public static final ViewProperty TRANSLATION_Y = new ViewProperty("translationY") { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.2
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getTranslationY();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setTranslationY(f);
        }
    };
    public static final ViewProperty TRANSLATION_Z = new ViewProperty("translationZ") { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.3
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getTranslationZ();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setTranslationZ(f);
        }
    };
    public static final ViewProperty SCALE_X = new ViewProperty(AnimatedProperty.PROPERTY_NAME_SCALE_X) { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.4
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getScaleX();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setScaleX(f);
        }
    };
    public static final ViewProperty SCALE_Y = new ViewProperty(AnimatedProperty.PROPERTY_NAME_SCALE_Y) { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.5
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getScaleY();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setScaleY(f);
        }
    };
    public static final ViewProperty ROTATION = new ViewProperty(AnimatedProperty.PROPERTY_NAME_ROTATION) { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.6
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getRotation();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setRotation(f);
        }
    };
    public static final ViewProperty ROTATION_X = new ViewProperty(AnimatedProperty.PROPERTY_NAME_ROTATION_X) { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.7
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getRotationX();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setRotationX(f);
        }
    };
    public static final ViewProperty ROTATION_Y = new ViewProperty(AnimatedProperty.PROPERTY_NAME_ROTATION_Y) { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.8
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getRotationY();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setRotationY(f);
        }
    };
    public static final ViewProperty X = new ViewProperty(AnimatedProperty.PROPERTY_NAME_X) { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.9
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getX();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setX(f);
        }
    };
    public static final ViewProperty Y = new ViewProperty(AnimatedProperty.PROPERTY_NAME_Y) { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.10
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getY();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setY(f);
        }
    };
    public static final ViewProperty Z = new ViewProperty("z") { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.11
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getZ();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setZ(f);
        }
    };
    public static final ViewProperty ALPHA = new ViewProperty(AnimatedProperty.PROPERTY_NAME_ALPHA) { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.12
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getAlpha();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setAlpha(f);
        }
    };
    public static final ViewProperty SCROLL_X = new ViewProperty("scrollX") { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.13
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getScrollX();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setScrollX((int) f);
        }
    };
    public static final ViewProperty SCROLL_Y = new ViewProperty("scrollY") { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.14
        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getScrollY();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setScrollY((int) f);
        }
    };
    public float mVelocity = 0.0f;
    public float mValue = Float.MAX_VALUE;
    public boolean mStartValueIsSet = false;
    public boolean mRunning = false;
    public float mMaxValue = Float.MAX_VALUE;
    public float mMinValue = -Float.MAX_VALUE;
    public long mLastFrameTimeNanos = 0;
    public long mStartDelay = 0;
    public final ArrayList mEndListeners = new ArrayList();
    public final ArrayList mUpdateListeners = new ArrayList();
    public final Object mTarget = null;
    public float mMinVisibleChange = 1.0f;

    public static class MassState {
        public float mValue;
        public float mVelocity;
    }

    public interface OnAnimationEndListener {
        void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2);
    }

    public interface OnAnimationUpdateListener {
        void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2);
    }

    public static abstract class ViewProperty extends FloatPropertyCompat<View> {
        public ViewProperty(String str) {
            super(str);
        }
    }

    public DynamicAnimation(final FloatValueHolder floatValueHolder) {
        this.mProperty = new FloatPropertyCompat("FloatValueHolder") { // from class: miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation.15
            @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
            public float getValue(Object obj) {
                return floatValueHolder.getValue();
            }

            @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
            public void setValue(Object obj, float f) {
                floatValueHolder.setValue(f);
            }
        };
    }

    public static void removeEntry(ArrayList arrayList, Object obj) {
        int iIndexOf = arrayList.indexOf(obj);
        if (iIndexOf >= 0) {
            arrayList.set(iIndexOf, null);
        }
    }

    public static void removeNullEntries(ArrayList arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size) == null) {
                arrayList.remove(size);
            }
        }
    }

    public DynamicAnimation addUpdateListener(OnAnimationUpdateListener onAnimationUpdateListener) {
        if (isRunning()) {
            throw new UnsupportedOperationException("Error: Update listeners must be added beforethe miuix.animation.");
        }
        if (!this.mUpdateListeners.contains(onAnimationUpdateListener)) {
            this.mUpdateListeners.add(onAnimationUpdateListener);
        }
        return this;
    }

    public void cancel() {
        if (!getAnimationHandler().isCurrentThread()) {
            throw new AndroidRuntimeException("Animations may only be canceled from the same thread as the animation handler");
        }
        if (this.mRunning) {
            endAnimationInternal(true);
        }
    }

    @Override // miuix.overscroller.internal.dynamicanimation.animation.AnimationHandler.AnimationFrameCallback
    public boolean doAnimationFrame(long j) {
        long frameDeltaNanos = AnimationHandler.getInstance().getFrameDeltaNanos();
        boolean fromFramePeriodNsecs = AnimationHandler.getInstance().getFromFramePeriodNsecs();
        long j2 = this.mLastFrameTimeNanos;
        if (j2 == 0) {
            this.mLastFrameTimeNanos = j;
            setPropertyValue(this.mValue);
            return false;
        }
        if (frameDeltaNanos == 0) {
            frameDeltaNanos = j - j2;
        }
        long jMin = (!fromFramePeriodNsecs || frameDeltaNanos == 0) ? Math.min(frameDeltaNanos, FolmeEngine.MAX_DELTA) : Math.min(frameDeltaNanos, 1000000000L);
        this.mLastFrameTimeNanos = j;
        boolean zUpdateValueAndVelocity = updateValueAndVelocity(jMin);
        float fMin = Math.min(this.mValue, this.mMaxValue);
        this.mValue = fMin;
        float fMax = Math.max(fMin, this.mMinValue);
        this.mValue = fMax;
        setPropertyValue(fMax);
        if (zUpdateValueAndVelocity) {
            endAnimationInternal(false);
        }
        return zUpdateValueAndVelocity;
    }

    public final void endAnimationInternal(boolean z) {
        this.mRunning = false;
        AnimationHandler.getInstance().removeCallback(this);
        this.mLastFrameTimeNanos = 0L;
        this.mStartValueIsSet = false;
        for (int i = 0; i < this.mEndListeners.size(); i++) {
            if (this.mEndListeners.get(i) != null) {
                ((OnAnimationEndListener) this.mEndListeners.get(i)).onAnimationEnd(this, z, this.mValue, this.mVelocity);
            }
        }
        removeNullEntries(this.mEndListeners);
    }

    public AnimationHandler getAnimationHandler() {
        return AnimationHandler.getInstance();
    }

    public final float getPropertyValue() {
        return this.mProperty.getValue(this.mTarget);
    }

    public float getValueThreshold() {
        return this.mMinVisibleChange * 0.75f;
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    public void removeUpdateListener(OnAnimationUpdateListener onAnimationUpdateListener) {
        removeEntry(this.mUpdateListeners, onAnimationUpdateListener);
    }

    public DynamicAnimation setMaxValue(float f) {
        this.mMaxValue = f;
        return this;
    }

    public DynamicAnimation setMinValue(float f) {
        this.mMinValue = f;
        return this;
    }

    public DynamicAnimation setMinimumVisibleChange(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Minimum visible change must be positive.");
        }
        this.mMinVisibleChange = f;
        setValueThreshold(f * 0.75f);
        return this;
    }

    public void setPropertyValue(float f) {
        this.mProperty.setValue(this.mTarget, f);
        for (int i = 0; i < this.mUpdateListeners.size(); i++) {
            if (this.mUpdateListeners.get(i) != null) {
                ((OnAnimationUpdateListener) this.mUpdateListeners.get(i)).onAnimationUpdate(this, this.mValue, this.mVelocity);
            }
        }
        removeNullEntries(this.mUpdateListeners);
    }

    public DynamicAnimation setStartValue(float f) {
        this.mValue = f;
        this.mStartValueIsSet = true;
        return this;
    }

    public DynamicAnimation setStartVelocity(float f) {
        this.mVelocity = f;
        return this;
    }

    public abstract void setValueThreshold(float f);

    public void start(boolean z) {
        if (!getAnimationHandler().isCurrentThread()) {
            throw new AndroidRuntimeException("Animations may only be started on the main thread");
        }
        if (this.mRunning) {
            return;
        }
        startAnimationInternal(z);
    }

    public final void startAnimationInternal(boolean z) {
        if (this.mRunning) {
            return;
        }
        this.mRunning = true;
        if (!this.mStartValueIsSet) {
            this.mValue = getPropertyValue();
        }
        float f = this.mValue;
        if (f > this.mMaxValue || f < this.mMinValue) {
            throw new IllegalArgumentException("Starting value need to be in between min value and max value");
        }
        if (z) {
            return;
        }
        AnimationHandler.getInstance().addAnimationFrameCallback(this, this.mStartDelay);
    }

    public abstract boolean updateValueAndVelocity(long j);
}
