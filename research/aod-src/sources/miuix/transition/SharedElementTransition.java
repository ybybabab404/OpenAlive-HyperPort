package miuix.transition;

import android.content.Context;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import java.util.Map;
import miuix.animation.Folme;
import miuix.animation.controller.AnimState;
import miuix.animation.property.ViewProperty;
import miuix.animation.property.ViewPropertyExt;

/* JADX INFO: loaded from: classes3.dex */
public class SharedElementTransition extends ChangeBounds {
    public static final String[] sTransitionProperties = {"android:transition:bounds", "android:transition:parent", "android:transition:windowX", "android:transition:windowY", "android:transition:z", "android:transition:scaleX", "android:transition:scaleY", "android:transition:rotation", "android:transition:rotationX", "android:transition:rotationY", "android:transition:scrollX", "android:transition:scrollY", "android:transition:alpha", "android:transition:background", "android:transition:foreground"};
    public static Map sViewPropertyMap;

    static {
        ArrayMap arrayMap = new ArrayMap();
        sViewPropertyMap = arrayMap;
        arrayMap.put("android:transition:z", ViewProperty.Z);
        sViewPropertyMap.put("android:transition:translationX", ViewProperty.TRANSLATION_X);
        sViewPropertyMap.put("android:transition:translationY", ViewProperty.TRANSLATION_Y);
        sViewPropertyMap.put("android:transition:translationZ", ViewProperty.TRANSLATION_Z);
        sViewPropertyMap.put("android:transition:scaleX", ViewProperty.SCALE_X);
        sViewPropertyMap.put("android:transition:scaleY", ViewProperty.SCALE_Y);
        sViewPropertyMap.put("android:transition:rotation", ViewProperty.ROTATION);
        sViewPropertyMap.put("android:transition:rotationX", ViewProperty.ROTATION_X);
        sViewPropertyMap.put("android:transition:rotationY", ViewProperty.ROTATION_Y);
        sViewPropertyMap.put("android:transition:scrollX", ViewProperty.SCROLL_X);
        sViewPropertyMap.put("android:transition:scrollY", ViewProperty.SCROLL_Y);
        sViewPropertyMap.put("android:transition:alpha", ViewProperty.ALPHA);
        sViewPropertyMap.put("android:transition:background", ViewPropertyExt.BACKGROUND);
        sViewPropertyMap.put("android:transition:foreground", ViewPropertyExt.FOREGROUND);
    }

    public SharedElementTransition(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // miuix.transition.ChangeBounds, miuix.transition.MiuixTransition
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // miuix.transition.ChangeBounds, miuix.transition.MiuixTransition
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // miuix.transition.ChangeBounds
    public void captureValues(TransitionValues transitionValues) {
        super.captureValues(transitionValues);
        View view = transitionValues.view;
        if (!ViewCompat.isLaidOut(view) && view.getWidth() == 0 && view.getHeight() == 0) {
            return;
        }
        for (Map.Entry entry : sViewPropertyMap.entrySet()) {
            if (((String) entry.getKey()).equals("android:transition:background")) {
                transitionValues.values.put((String) entry.getKey(), Integer.valueOf(ViewPropertyExt.BACKGROUND.getIntValue(view)));
            } else if (((String) entry.getKey()).equals("android:transition:foreground")) {
                transitionValues.values.put((String) entry.getKey(), Integer.valueOf(ViewPropertyExt.FOREGROUND.getIntValue(view)));
            } else {
                transitionValues.values.put((String) entry.getKey(), Float.valueOf(((ViewProperty) entry.getValue()).getValue(view)));
            }
        }
    }

    @Override // miuix.transition.ChangeBounds, miuix.transition.MiuixTransition
    public void createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return;
        }
        View view = transitionValues2.view;
        AnimState diffAnimState = getDiffAnimState(transitionValues, transitionValues2);
        diffAnimState.setTag("sharedelement_end");
        if (!diffAnimState.isEmpty()) {
            addTransitionRunner(new MiuixTransition.TransitionRunner(Folme.useAt(view).state(), (Object) null, (Object) "sharedelement_end", getAnimConfig()));
        }
        super.createAnimator(viewGroup, transitionValues, transitionValues2);
    }

    public AnimState getDiffAnimState(TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        AnimState animState = new AnimState();
        for (String str : sViewPropertyMap.keySet()) {
            if (str.equals("android:transition:background") || str.equals("android:transition:foreground")) {
                int iIntValue = ((Integer) transitionValues.values.get(str)).intValue();
                int iIntValue2 = ((Integer) transitionValues2.values.get(str)).intValue();
                if (iIntValue != iIntValue2) {
                    animState.add((ViewProperty) sViewPropertyMap.get(str), iIntValue2);
                }
            } else {
                float fFloatValue = ((Float) transitionValues.values.get(str)).floatValue();
                float fFloatValue2 = ((Float) transitionValues2.values.get(str)).floatValue();
                if (MiuixTransition.diff(fFloatValue, fFloatValue2)) {
                    animState.add((ViewProperty) sViewPropertyMap.get(str), fFloatValue2);
                }
            }
        }
        return animState;
    }

    @Override // miuix.transition.ChangeBounds, miuix.transition.MiuixTransition
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }
}
