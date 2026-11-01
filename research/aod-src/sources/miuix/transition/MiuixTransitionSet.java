package miuix.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class MiuixTransitionSet extends MiuixTransition {
    public int mCurrentListeners;
    public boolean mPlayTogether;
    public boolean mStarted;
    public ArrayList mTransitions;

    public static class TransitionSetListener extends TransitionListenerAdapter {
        public MiuixTransitionSet mTransitionSet;

        public TransitionSetListener(MiuixTransitionSet miuixTransitionSet) {
            this.mTransitionSet = miuixTransitionSet;
        }

        @Override // miuix.transition.TransitionListenerAdapter, miuix.transition.MiuixTransition.MiuixTransitionListener
        public void onTransitionEnd(MiuixTransition miuixTransition) {
            MiuixTransitionSet miuixTransitionSet = this.mTransitionSet;
            int i = miuixTransitionSet.mCurrentListeners - 1;
            miuixTransitionSet.mCurrentListeners = i;
            if (i == 0) {
                miuixTransitionSet.mStarted = false;
                miuixTransitionSet.onTransitionEnd();
            }
            miuixTransition.removeListener(this);
        }

        @Override // miuix.transition.TransitionListenerAdapter, miuix.transition.MiuixTransition.MiuixTransitionListener
        public void onTransitionStart(MiuixTransition miuixTransition) {
            MiuixTransitionSet miuixTransitionSet = this.mTransitionSet;
            if (miuixTransitionSet.mStarted) {
                return;
            }
            miuixTransitionSet.onTransitionStart();
            this.mTransitionSet.mStarted = true;
        }
    }

    public MiuixTransitionSet() {
        this.mTransitions = new ArrayList();
        this.mPlayTogether = true;
        this.mStarted = false;
    }

    public MiuixTransitionSet(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTransitions = new ArrayList();
        this.mPlayTogether = true;
        this.mStarted = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TransitionSet);
        setOrdering(typedArrayObtainStyledAttributes.getInt(R.styleable.TransitionSet_transitionOrdering, 0));
        typedArrayObtainStyledAttributes.recycle();
    }

    public MiuixTransitionSet addTransition(MiuixTransition miuixTransition) {
        if (miuixTransition != null) {
            addTransitionInternal(miuixTransition);
        }
        return this;
    }

    public final void addTransitionInternal(MiuixTransition miuixTransition) {
        this.mTransitions.add(miuixTransition);
        miuixTransition.mParent = this;
    }

    @Override // miuix.transition.MiuixTransition
    public void captureEndValues(TransitionValues transitionValues) {
        if (isValidTarget(transitionValues.view)) {
            for (MiuixTransition miuixTransition : this.mTransitions) {
                if (miuixTransition.isValidTarget(transitionValues.view)) {
                    miuixTransition.captureEndValues(transitionValues);
                    transitionValues.mTargetedTransitions.add(miuixTransition);
                }
            }
        }
    }

    @Override // miuix.transition.MiuixTransition
    public void captureStartValues(TransitionValues transitionValues) {
        if (isValidTarget(transitionValues.view)) {
            for (MiuixTransition miuixTransition : this.mTransitions) {
                if (miuixTransition.isValidTarget(transitionValues.view)) {
                    miuixTransition.captureStartValues(transitionValues);
                    transitionValues.mTargetedTransitions.add(miuixTransition);
                }
            }
        }
    }

    @Override // miuix.transition.MiuixTransition
    public void clear() {
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((MiuixTransition) this.mTransitions.get(i)).clear();
        }
    }

    @Override // miuix.transition.MiuixTransition
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public MiuixTransitionSet mo1355clone() {
        MiuixTransitionSet miuixTransitionSet = (MiuixTransitionSet) super.mo1355clone();
        miuixTransitionSet.mTransitions = new ArrayList();
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            miuixTransitionSet.addTransitionInternal(((MiuixTransition) this.mTransitions.get(i)).mo1355clone());
        }
        return miuixTransitionSet;
    }

    @Override // miuix.transition.MiuixTransition
    public void createAnimators(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2, ArrayList arrayList, ArrayList arrayList2) {
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((MiuixTransition) this.mTransitions.get(i)).createAnimators(viewGroup, transitionValuesMaps, transitionValuesMaps2, arrayList, arrayList2);
        }
    }

    @Override // miuix.transition.MiuixTransition
    public void forceToEnd() {
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((MiuixTransition) this.mTransitions.get(i)).forceToEnd();
        }
    }

    public MiuixTransition getTransitionAt(int i) {
        if (i < 0 || i >= this.mTransitions.size()) {
            return null;
        }
        return (MiuixTransition) this.mTransitions.get(i);
    }

    public int getTransitionCount() {
        return this.mTransitions.size();
    }

    @Override // miuix.transition.MiuixTransition
    public void runAnimators() {
        if (this.mTransitions.isEmpty()) {
            onTransitionStart();
            onTransitionEnd();
            return;
        }
        setupStartEndListeners();
        int size = this.mTransitions.size();
        if (this.mPlayTogether) {
            for (int i = 0; i < size; i++) {
                ((MiuixTransition) this.mTransitions.get(i)).runAnimators();
            }
            return;
        }
        for (int i2 = 1; i2 < size; i2++) {
            final MiuixTransition miuixTransition = (MiuixTransition) this.mTransitions.get(i2 - 1);
            final MiuixTransition miuixTransition2 = (MiuixTransition) this.mTransitions.get(i2);
            miuixTransition.addListener(new TransitionListenerAdapter() { // from class: miuix.transition.MiuixTransitionSet.1
                @Override // miuix.transition.TransitionListenerAdapter, miuix.transition.MiuixTransition.MiuixTransitionListener
                public void onTransitionEnd(MiuixTransition miuixTransition3) {
                    miuixTransition.removeListener(this);
                    miuixTransition2.runAnimators();
                }
            });
        }
        MiuixTransition miuixTransition3 = (MiuixTransition) this.mTransitions.get(0);
        if (miuixTransition3 != null) {
            miuixTransition3.runAnimators();
        }
    }

    public MiuixTransitionSet setOrdering(int i) {
        if (i == 0) {
            this.mPlayTogether = true;
            return this;
        }
        if (i == 1) {
            this.mPlayTogether = false;
            return this;
        }
        throw new AndroidRuntimeException("Invalid parameter for TransitionSet ordering: " + i);
    }

    public final void setupStartEndListeners() {
        TransitionSetListener transitionSetListener = new TransitionSetListener(this);
        Iterator it = this.mTransitions.iterator();
        while (it.hasNext()) {
            ((MiuixTransition) it.next()).addListener(transitionSetListener);
        }
        this.mCurrentListeners = this.mTransitions.size();
    }

    @Override // miuix.transition.MiuixTransition
    public String toString(String str) {
        String string = super.toString(str);
        for (int i = 0; i < this.mTransitions.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append("\n");
            sb.append(((MiuixTransition) this.mTransitions.get(i)).toString(str + "  "));
            string = sb.toString();
        }
        return string;
    }
}
