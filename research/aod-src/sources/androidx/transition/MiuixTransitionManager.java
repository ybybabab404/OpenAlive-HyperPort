package androidx.transition;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.collection.ArrayMap;
import androidx.core.view.ViewCompat;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import miuix.transition.MiuixTransition;

/* JADX INFO: loaded from: classes.dex */
public class MiuixTransitionManager {
    public static MiuixTransition sDefaultTransition = new miuix.transition.AutoTransition();
    public static ThreadLocal sRunningTransitions = new ThreadLocal();
    public static ArrayList sPendingTransitions = new ArrayList();

    public static class MultiListener implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
        public ViewGroup mSceneRoot;
        public MiuixTransition mTransition;

        public MultiListener(MiuixTransition miuixTransition, ViewGroup viewGroup) {
            this.mTransition = miuixTransition;
            this.mSceneRoot = viewGroup;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            removeListeners();
            if (!MiuixTransitionManager.sPendingTransitions.remove(this.mSceneRoot)) {
                return true;
            }
            final ArrayMap runningTransitions = MiuixTransitionManager.getRunningTransitions();
            ArrayList arrayList = (ArrayList) runningTransitions.get(this.mSceneRoot);
            if (arrayList == null) {
                arrayList = new ArrayList();
                runningTransitions.put(this.mSceneRoot, arrayList);
            }
            arrayList.add(this.mTransition);
            this.mTransition.addListener(new miuix.transition.TransitionListenerAdapter() { // from class: androidx.transition.MiuixTransitionManager.MultiListener.1
                @Override // miuix.transition.TransitionListenerAdapter, miuix.transition.MiuixTransition.MiuixTransitionListener
                public void onTransitionEnd(MiuixTransition miuixTransition) {
                    ((ArrayList) runningTransitions.get(MultiListener.this.mSceneRoot)).remove(miuixTransition);
                    miuixTransition.removeListener(this);
                }
            });
            this.mTransition.captureValues(this.mSceneRoot, false);
            this.mTransition.playTransition(this.mSceneRoot);
            return true;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            removeListeners();
            MiuixTransitionManager.sPendingTransitions.remove(this.mSceneRoot);
            ArrayList arrayList = (ArrayList) MiuixTransitionManager.getRunningTransitions().get(this.mSceneRoot);
            if (arrayList != null && arrayList.size() > 0) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((MiuixTransition) it.next()).forceToEnd();
                }
                arrayList.clear();
            }
            this.mTransition.clearValues(true);
            this.mTransition.clearValues(false);
            this.mTransition = null;
        }

        public final void removeListeners() {
            this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            this.mSceneRoot.removeOnAttachStateChangeListener(this);
        }
    }

    public static void beginDelayedTransition(ViewGroup viewGroup, MiuixTransition miuixTransition) {
        if (sPendingTransitions.contains(viewGroup) || !ViewCompat.isLaidOut(viewGroup)) {
            return;
        }
        sPendingTransitions.add(viewGroup);
        if (miuixTransition == null) {
            miuixTransition = sDefaultTransition;
        }
        MiuixTransition miuixTransitionMo1355clone = miuixTransition.mo1355clone();
        sceneChangeSetup(viewGroup, miuixTransitionMo1355clone);
        Scene.setCurrentScene(viewGroup, null);
        sceneChangeRunTransition(viewGroup, miuixTransitionMo1355clone);
    }

    public static ArrayMap getRunningTransitions() {
        ArrayMap arrayMap;
        WeakReference weakReference = (WeakReference) sRunningTransitions.get();
        if (weakReference != null && (arrayMap = (ArrayMap) weakReference.get()) != null) {
            return arrayMap;
        }
        ArrayMap arrayMap2 = new ArrayMap();
        sRunningTransitions.set(new WeakReference(arrayMap2));
        return arrayMap2;
    }

    public static void sceneChangeRunTransition(ViewGroup viewGroup, MiuixTransition miuixTransition) {
        if (miuixTransition == null || viewGroup == null) {
            return;
        }
        MultiListener multiListener = new MultiListener(miuixTransition, viewGroup);
        viewGroup.addOnAttachStateChangeListener(multiListener);
        viewGroup.getViewTreeObserver().addOnPreDrawListener(multiListener);
    }

    public static void sceneChangeSetup(ViewGroup viewGroup, MiuixTransition miuixTransition) {
        ArrayList<MiuixTransition> arrayList = (ArrayList) getRunningTransitions().get(viewGroup);
        if (arrayList != null && arrayList.size() > 0) {
            for (MiuixTransition miuixTransition2 : arrayList) {
                miuixTransition2.forceToEnd();
                miuixTransition2.clear();
            }
            arrayList.clear();
        }
        if (miuixTransition != null) {
            miuixTransition.captureValues(viewGroup, true);
        }
        Scene currentScene = Scene.getCurrentScene(viewGroup);
        if (currentScene != null) {
            currentScene.exit();
        }
    }
}
