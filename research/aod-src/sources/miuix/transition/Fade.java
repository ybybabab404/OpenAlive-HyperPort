package miuix.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.miui.aod.components.view.BaseStyleSelectView;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes3.dex */
public class Fade extends Visibility {
    public AnimState invisibleState;
    public AnimState visibleState;

    public Fade(int i) {
        AnimState animState = new AnimState("fade_in_end");
        ViewProperty viewProperty = ViewProperty.AUTO_ALPHA;
        this.visibleState = animState.add(viewProperty, 1.0d);
        this.invisibleState = new AnimState("fade_out_end").add(viewProperty, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        setMode(i);
    }

    public Fade(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        AnimState animState = new AnimState("fade_in_end");
        ViewProperty viewProperty = ViewProperty.AUTO_ALPHA;
        this.visibleState = animState.add(viewProperty, 1.0d);
        this.invisibleState = new AnimState("fade_out_end").add(viewProperty, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Fade);
        setMode(typedArrayObtainStyledAttributes.getInt(R.styleable.Fade_fadingMode, getMode()));
        typedArrayObtainStyledAttributes.recycle();
    }

    public static float getStartAlpha(TransitionValues transitionValues, float f) {
        Float f2;
        return (transitionValues == null || (f2 = (Float) transitionValues.values.get("android:fade:transitionAlpha")) == null) ? f : f2.floatValue();
    }

    @Override // miuix.transition.Visibility, miuix.transition.MiuixTransition
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        transitionValues.values.put("android:fade:transitionAlpha", Float.valueOf(transitionValues.view.getAlpha()));
    }

    @Override // miuix.transition.Visibility, miuix.transition.MiuixTransition
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        transitionValues.values.put("android:fade:transitionAlpha", Float.valueOf(transitionValues.view.getAlpha()));
    }

    @Override // miuix.transition.Visibility
    public void onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        float startAlpha = getStartAlpha(transitionValues, 0.0f);
        AnimState animStateAdd = new AnimState().add(ViewProperty.ALPHA, startAlpha != 1.0f ? startAlpha : 0.0f);
        final AnimConfig animConfig = new AnimConfig();
        animConfig.addListeners(new TransitionListener() { // from class: miuix.transition.Fade.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                if (obj == "fade_in_end") {
                    Fade fade = Fade.this;
                    if (fade.mNumInstances == 0) {
                        fade.onTransitionStart();
                    }
                    Fade.this.mNumInstances++;
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                if (obj == "fade_in_end") {
                    Fade fade = Fade.this;
                    int i = fade.mNumInstances - 1;
                    fade.mNumInstances = i;
                    if (i == 0) {
                        animConfig.removeListeners(this);
                        Fade.this.onTransitionEnd();
                    }
                }
            }
        });
        IStateStyle iStateStyleState = Folme.useAt(view).state();
        iStateStyleState.setTo(animStateAdd);
        addTransitionRunner(new MiuixTransition.TransitionRunner(iStateStyleState, (Object) animStateAdd, (Object) this.visibleState, getAnimConfig(), animConfig));
    }

    @Override // miuix.transition.Visibility
    public void onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2, TransitionListener transitionListener) {
        AnimState animStateAdd = new AnimState().add(ViewProperty.AUTO_ALPHA, getStartAlpha(transitionValues, 1.0f));
        final AnimConfig animConfig = new AnimConfig();
        animConfig.addListeners(new TransitionListener() { // from class: miuix.transition.Fade.2
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                if (obj == "fade_out_end") {
                    Fade fade = Fade.this;
                    if (fade.mNumInstances == 0) {
                        fade.onTransitionStart();
                    }
                    Fade.this.mNumInstances++;
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                if (obj == "fade_out_end") {
                    Fade fade = Fade.this;
                    int i = fade.mNumInstances - 1;
                    fade.mNumInstances = i;
                    if (i == 0) {
                        animConfig.removeListeners(this);
                        Fade.this.onTransitionEnd();
                    }
                }
            }
        });
        IStateStyle iStateStyleState = Folme.useAt(view).state();
        iStateStyleState.setTo(animStateAdd);
        addTransitionRunner(new MiuixTransition.TransitionRunner(iStateStyleState, (Object) animStateAdd, (Object) this.invisibleState, getAnimConfig(), animConfig));
    }
}
