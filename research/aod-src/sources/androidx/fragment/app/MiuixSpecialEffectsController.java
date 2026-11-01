package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.util.Preconditions;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"RestrictedApi"})
public class MiuixSpecialEffectsController extends SpecialEffectsController {

    /* JADX INFO: renamed from: androidx.fragment.app.MiuixSpecialEffectsController$9, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass9 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State;

        static {
            int[] iArr = new int[SpecialEffectsController.Operation.State.values().length];
            $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State = iArr;
            try {
                iArr[SpecialEffectsController.Operation.State.GONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.INVISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.REMOVED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.VISIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static class AnimationInfo extends SpecialEffectsInfo {
        public FragmentAnim.AnimationOrAnimator mAnimation;
        public boolean mIsPop;
        public boolean mLoadedAnim;

        public AnimationInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal, boolean z) {
            super(operation, cancellationSignal);
            this.mLoadedAnim = false;
            this.mIsPop = z;
        }

        public FragmentAnim.AnimationOrAnimator getAnimation(Context context) {
            if (this.mLoadedAnim) {
                return this.mAnimation;
            }
            FragmentAnim.AnimationOrAnimator animationOrAnimatorLoadAnimation = FragmentAnim.loadAnimation(context, getOperation().getFragment(), getOperation().getFinalState() == SpecialEffectsController.Operation.State.VISIBLE, this.mIsPop);
            this.mAnimation = animationOrAnimatorLoadAnimation;
            this.mLoadedAnim = true;
            return animationOrAnimatorLoadAnimation;
        }
    }

    public static class SpecialEffectsInfo {
        public final SpecialEffectsController.Operation mOperation;
        public final CancellationSignal mSignal;

        public SpecialEffectsInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal) {
            this.mOperation = operation;
            this.mSignal = cancellationSignal;
        }

        public void completeSpecialEffect() {
            this.mOperation.completeSpecialEffect(this.mSignal);
        }

        public SpecialEffectsController.Operation getOperation() {
            return this.mOperation;
        }

        public CancellationSignal getSignal() {
            return this.mSignal;
        }

        public boolean isVisibilityUnchanged() {
            SpecialEffectsController.Operation.State stateFrom = SpecialEffectsController.Operation.State.from(this.mOperation.getFragment().mView);
            SpecialEffectsController.Operation.State finalState = this.mOperation.getFinalState();
            if (stateFrom == finalState) {
                return true;
            }
            SpecialEffectsController.Operation.State state = SpecialEffectsController.Operation.State.VISIBLE;
            return (stateFrom == state || finalState == state) ? false : true;
        }
    }

    public static class TransitionInfo extends SpecialEffectsInfo {
        public final boolean mOverlapAllowed;
        public final Object mSharedElementTransition;
        public final Object mTransition;

        public TransitionInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal, boolean z, boolean z2) {
            super(operation, cancellationSignal);
            if (operation.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                this.mTransition = z ? operation.getFragment().getReenterTransition() : operation.getFragment().getEnterTransition();
                this.mOverlapAllowed = z ? operation.getFragment().getAllowReturnTransitionOverlap() : operation.getFragment().getAllowEnterTransitionOverlap();
            } else {
                this.mTransition = z ? operation.getFragment().getReturnTransition() : operation.getFragment().getExitTransition();
                this.mOverlapAllowed = true;
            }
            if (!z2) {
                this.mSharedElementTransition = null;
            } else if (z) {
                this.mSharedElementTransition = operation.getFragment().getSharedElementReturnTransition();
            } else {
                this.mSharedElementTransition = operation.getFragment().getSharedElementEnterTransition();
            }
        }

        public FragmentTransitionImpl getHandlingImpl() {
            FragmentTransitionImpl handlingImpl = getHandlingImpl(this.mTransition);
            FragmentTransitionImpl handlingImpl2 = getHandlingImpl(this.mSharedElementTransition);
            if (handlingImpl == null || handlingImpl2 == null || handlingImpl == handlingImpl2) {
                return handlingImpl != null ? handlingImpl : handlingImpl2;
            }
            throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + getOperation().getFragment() + " returned Transition " + this.mTransition + " which uses a different Transition  type than its shared element transition " + this.mSharedElementTransition);
        }

        public final FragmentTransitionImpl getHandlingImpl(Object obj) {
            if (obj == null) {
                return null;
            }
            FragmentTransitionImpl fragmentTransitionImpl = MiuixFragmentTransition.PLATFORM_IMPL;
            if (fragmentTransitionImpl != null && fragmentTransitionImpl.canHandle(obj)) {
                return fragmentTransitionImpl;
            }
            FragmentTransitionImpl fragmentTransitionImpl2 = MiuixFragmentTransition.SUPPORT_IMPL;
            if (fragmentTransitionImpl2 != null && fragmentTransitionImpl2.canHandle(obj)) {
                return fragmentTransitionImpl2;
            }
            FragmentTransitionImpl fragmentTransitionImpl3 = MiuixFragmentTransition.MIUIX_IMPL;
            if (fragmentTransitionImpl3 != null && fragmentTransitionImpl3.canHandle(obj)) {
                return fragmentTransitionImpl3;
            }
            throw new IllegalArgumentException("Transition " + obj + " for fragment " + getOperation().getFragment() + " is not a valid framework Transition or AndroidX Transition");
        }

        public Object getSharedElementTransition() {
            return this.mSharedElementTransition;
        }

        public Object getTransition() {
            return this.mTransition;
        }

        public boolean hasSharedElementTransition() {
            return this.mSharedElementTransition != null;
        }

        public boolean isOverlapAllowed() {
            return this.mOverlapAllowed;
        }
    }

    public static /* synthetic */ void $r8$lambda$RvmUOLOvaf3fOEmpGsApmP2J5hQ(MiuixSpecialEffectsController miuixSpecialEffectsController, List list, SpecialEffectsController.Operation operation) {
        miuixSpecialEffectsController.getClass();
        if (list.contains(operation)) {
            list.remove(operation);
            miuixSpecialEffectsController.applyContainerChanges(operation);
        }
    }

    private void startAnimations(List list, List list2, boolean z, Map map) {
        final SpecialEffectsController.Operation operation;
        final AnimationInfo animationInfo;
        final View view;
        final ViewGroup container = getContainer();
        Context context = container.getContext();
        ArrayList<AnimationInfo> arrayList = new ArrayList();
        Iterator it = list.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            final AnimationInfo animationInfo2 = (AnimationInfo) it.next();
            if (animationInfo2.isVisibilityUnchanged()) {
                animationInfo2.completeSpecialEffect();
            } else {
                FragmentAnim.AnimationOrAnimator animation = animationInfo2.getAnimation(context);
                if (animation == null) {
                    animationInfo2.completeSpecialEffect();
                } else {
                    final Animator animator = animation.animator;
                    if (animator == null) {
                        arrayList.add(animationInfo2);
                    } else {
                        final SpecialEffectsController.Operation operation2 = animationInfo2.getOperation();
                        Fragment fragment = operation2.getFragment();
                        if (Boolean.TRUE.equals(map.get(operation2))) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "Ignoring Animator set on " + fragment + " as this Fragment was involved in a Transition.");
                            }
                            animationInfo2.completeSpecialEffect();
                        } else {
                            final boolean z3 = operation2.getFinalState() == SpecialEffectsController.Operation.State.GONE;
                            if (z3) {
                                list2.remove(operation2);
                            }
                            final View view2 = fragment.mView;
                            container.startViewTransition(view2);
                            final ViewGroup viewGroup = container;
                            container = viewGroup;
                            animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.1
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator animator2) {
                                    viewGroup.endViewTransition(view2);
                                    if (z3) {
                                        operation2.getFinalState().applyState(view2);
                                    }
                                    animationInfo2.completeSpecialEffect();
                                    if (FragmentManager.isLoggingEnabled(2)) {
                                        Log.v("FragmentManager", "Animator from operation " + operation2 + " has ended.");
                                    }
                                }
                            });
                            animator.setTarget(view2);
                            animator.start();
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "Animator from operation " + operation2 + " has started.");
                            }
                            animationInfo2.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.2
                                @Override // androidx.core.os.CancellationSignal.OnCancelListener
                                public void onCancel() {
                                    animator.end();
                                    if (FragmentManager.isLoggingEnabled(2)) {
                                        Log.v("FragmentManager", "Animator from operation " + operation2 + " has been canceled.");
                                    }
                                }
                            });
                            z2 = true;
                        }
                    }
                }
            }
        }
        for (AnimationInfo animationInfo3 : arrayList) {
            SpecialEffectsController.Operation operation3 = animationInfo3.getOperation();
            Fragment fragment2 = operation3.getFragment();
            if (z) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Transitions.");
                }
                animationInfo3.completeSpecialEffect();
            } else if (z2) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Animators.");
                }
                animationInfo3.completeSpecialEffect();
            } else {
                View view3 = fragment2.mView;
                Animation animation2 = (Animation) Preconditions.checkNotNull(((FragmentAnim.AnimationOrAnimator) Preconditions.checkNotNull(animationInfo3.getAnimation(context))).animation);
                if (operation3.getFinalState() != SpecialEffectsController.Operation.State.REMOVED) {
                    view3.startAnimation(animation2);
                    animationInfo3.completeSpecialEffect();
                    operation = operation3;
                    animationInfo = animationInfo3;
                    view = view3;
                } else {
                    container.startViewTransition(view3);
                    FragmentAnim.EndViewTransitionAnimation endViewTransitionAnimation = new FragmentAnim.EndViewTransitionAnimation(animation2, container, view3);
                    operation = operation3;
                    animationInfo = animationInfo3;
                    view = view3;
                    endViewTransitionAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.3
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation3) {
                            container.post(new Runnable() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.3.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                                    container.endViewTransition(view);
                                    animationInfo.completeSpecialEffect();
                                }
                            });
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "Animation from operation " + operation + " has ended.");
                            }
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation3) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation3) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "Animation from operation " + operation + " has reached onAnimationStart.");
                            }
                        }
                    });
                    view.startAnimation(endViewTransitionAnimation);
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "Animation from operation " + operation + " has started.");
                    }
                }
                CancellationSignal signal = animationInfo.getSignal();
                final AnimationInfo animationInfo4 = animationInfo;
                final SpecialEffectsController.Operation operation4 = operation;
                final View view4 = view;
                signal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.4
                    @Override // androidx.core.os.CancellationSignal.OnCancelListener
                    public void onCancel() {
                        view4.clearAnimation();
                        container.endViewTransition(view4);
                        animationInfo4.completeSpecialEffect();
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "Animation from operation " + operation4 + " has been cancelled.");
                        }
                    }
                });
            }
        }
    }

    private Map startTransitions(List list, List list2, final boolean z, SpecialEffectsController.Operation operation, SpecialEffectsController.Operation operation2) {
        View view;
        String str;
        String str2;
        String str3;
        ArrayList arrayList;
        View view2;
        Object objMergeTransitionsTogether;
        Object objMergeTransitionsTogether2;
        boolean z2;
        View view3;
        ArrayList arrayList2;
        ArrayList arrayList3;
        Object obj;
        HashMap map;
        SpecialEffectsController.Operation operation3;
        HashMap map2;
        ArrayList arrayList4;
        View view4;
        final Rect rect;
        ArrayMap arrayMap;
        SharedElementCallback enterTransitionCallback;
        SharedElementCallback exitTransitionCallback;
        Rect rect2;
        int i;
        final View view5;
        String strFindKeyForValue;
        int i2;
        final SpecialEffectsController.Operation operation4 = operation;
        final SpecialEffectsController.Operation operation5 = operation2;
        HashMap map3 = new HashMap();
        Iterator it = list.iterator();
        final FragmentTransitionImpl fragmentTransitionImpl = null;
        while (it.hasNext()) {
            TransitionInfo transitionInfo = (TransitionInfo) it.next();
            if (!transitionInfo.isVisibilityUnchanged()) {
                FragmentTransitionImpl handlingImpl = transitionInfo.getHandlingImpl();
                if (fragmentTransitionImpl == null) {
                    fragmentTransitionImpl = handlingImpl;
                } else if (handlingImpl != null && fragmentTransitionImpl != handlingImpl) {
                    throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + transitionInfo.getOperation().getFragment() + " returned Transition " + transitionInfo.getTransition() + " which uses a different Transition  type than other Fragments.");
                }
            }
        }
        if (fragmentTransitionImpl == null) {
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                TransitionInfo transitionInfo2 = (TransitionInfo) it2.next();
                map3.put(transitionInfo2.getOperation(), Boolean.FALSE);
                transitionInfo2.completeSpecialEffect();
            }
            return map3;
        }
        View view6 = new View(getContainer().getContext());
        Rect rect3 = new Rect();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        ArrayMap arrayMap2 = new ArrayMap();
        Iterator it3 = list.iterator();
        Object obj2 = null;
        View view7 = null;
        boolean z3 = false;
        while (true) {
            view = view7;
            str = "FragmentManager";
            if (!it3.hasNext()) {
                break;
            }
            TransitionInfo transitionInfo3 = (TransitionInfo) it3.next();
            if (!transitionInfo3.hasSharedElementTransition() || operation4 == null || operation5 == null) {
                ArrayList arrayList7 = arrayList5;
                map2 = map3;
                arrayList4 = arrayList7;
                view4 = view6;
                rect = rect3;
                arrayMap = arrayMap2;
            } else {
                Object objWrapTransitionInSet = fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(transitionInfo3.getSharedElementTransition()));
                ArrayList<String> sharedElementSourceNames = operation5.getFragment().getSharedElementSourceNames();
                ArrayList<String> sharedElementSourceNames2 = operation4.getFragment().getSharedElementSourceNames();
                ArrayList<String> sharedElementTargetNames = operation4.getFragment().getSharedElementTargetNames();
                HashMap map4 = map3;
                int i3 = 0;
                while (i3 < sharedElementTargetNames.size()) {
                    int iIndexOf = sharedElementSourceNames.indexOf(sharedElementTargetNames.get(i3));
                    ArrayList<String> arrayList8 = sharedElementTargetNames;
                    if (iIndexOf != -1) {
                        sharedElementSourceNames.set(iIndexOf, sharedElementSourceNames2.get(i3));
                    }
                    i3++;
                    sharedElementTargetNames = arrayList8;
                }
                ArrayList<String> sharedElementTargetNames2 = operation5.getFragment().getSharedElementTargetNames();
                if (z == 0) {
                    enterTransitionCallback = operation4.getFragment().getExitTransitionCallback();
                    exitTransitionCallback = operation5.getFragment().getEnterTransitionCallback();
                } else {
                    enterTransitionCallback = operation4.getFragment().getEnterTransitionCallback();
                    exitTransitionCallback = operation5.getFragment().getExitTransitionCallback();
                }
                View view8 = view6;
                int i4 = 0;
                for (int size = sharedElementSourceNames.size(); i4 < size; size = size) {
                    arrayMap2.put(sharedElementSourceNames.get(i4), sharedElementTargetNames2.get(i4));
                    i4++;
                }
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", ">>> entering view names <<<");
                    Iterator<String> it4 = sharedElementTargetNames2.iterator();
                    while (true) {
                        Iterator<String> it5 = it4;
                        if (!it4.hasNext()) {
                            break;
                        }
                        Log.v("FragmentManager", "Name: " + it5.next());
                        it4 = it5;
                        rect3 = rect3;
                    }
                    rect2 = rect3;
                    Log.v("FragmentManager", ">>> exiting view names <<<");
                    for (Iterator<String> it6 = sharedElementSourceNames.iterator(); it6.hasNext(); it6 = it6) {
                        Log.v("FragmentManager", "Name: " + it6.next());
                    }
                } else {
                    rect2 = rect3;
                }
                ArrayMap arrayMap3 = new ArrayMap();
                findNamedViews(arrayMap3, operation4.getFragment().mView);
                arrayMap3.retainAll(sharedElementSourceNames);
                if (enterTransitionCallback != null) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "Executing exit callback for operation " + operation4);
                    }
                    enterTransitionCallback.onMapSharedElements(sharedElementSourceNames, arrayMap3);
                    int size2 = sharedElementSourceNames.size() - 1;
                    while (size2 >= 0) {
                        String str4 = sharedElementSourceNames.get(size2);
                        View view9 = (View) arrayMap3.get(str4);
                        if (view9 == null) {
                            arrayMap2.remove(str4);
                            i2 = size2;
                        } else {
                            i2 = size2;
                            if (!str4.equals(ViewCompat.getTransitionName(view9))) {
                                arrayMap2.put(ViewCompat.getTransitionName(view9), (String) arrayMap2.remove(str4));
                            }
                        }
                        size2 = i2 - 1;
                    }
                } else {
                    arrayMap2.retainAll(arrayMap3.keySet());
                }
                final ArrayMap arrayMap4 = new ArrayMap();
                findNamedViews(arrayMap4, operation5.getFragment().mView);
                arrayMap4.retainAll(sharedElementTargetNames2);
                arrayMap4.retainAll(arrayMap2.values());
                if (exitTransitionCallback != null) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "Executing enter callback for operation " + operation5);
                    }
                    exitTransitionCallback.onMapSharedElements(sharedElementTargetNames2, arrayMap4);
                    for (int size3 = sharedElementTargetNames2.size() - 1; size3 >= 0; size3--) {
                        String str5 = sharedElementTargetNames2.get(size3);
                        View view10 = (View) arrayMap4.get(str5);
                        if (view10 == null) {
                            String strFindKeyForValue2 = MiuixFragmentTransition.findKeyForValue(arrayMap2, str5);
                            if (strFindKeyForValue2 != null) {
                                arrayMap2.remove(strFindKeyForValue2);
                            }
                        } else if (!str5.equals(ViewCompat.getTransitionName(view10)) && (strFindKeyForValue = MiuixFragmentTransition.findKeyForValue(arrayMap2, str5)) != null) {
                            arrayMap2.put(strFindKeyForValue, ViewCompat.getTransitionName(view10));
                        }
                    }
                } else {
                    MiuixFragmentTransition.retainValues(arrayMap2, arrayMap4);
                }
                retainMatchingViews(arrayMap3, arrayMap2.keySet());
                retainMatchingViews(arrayMap4, arrayMap2.values());
                if (arrayMap2.isEmpty()) {
                    arrayList5.clear();
                    arrayList6.clear();
                    arrayList4 = arrayList5;
                    arrayMap = arrayMap2;
                    arrayList6 = arrayList6;
                    view7 = view;
                    map2 = map4;
                    view4 = view8;
                    rect = rect2;
                    obj2 = null;
                } else {
                    MiuixFragmentTransition.callSharedElementStartEnd(operation5.getFragment(), operation4.getFragment(), z, arrayMap3, true);
                    OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.5
                        @Override // java.lang.Runnable
                        public void run() {
                            MiuixFragmentTransition.callSharedElementStartEnd(operation5.getFragment(), operation4.getFragment(), z, arrayMap4, false);
                        }
                    });
                    arrayList5.addAll(arrayMap3.values());
                    if (sharedElementSourceNames.isEmpty()) {
                        i = 0;
                    } else {
                        i = 0;
                        View view11 = (View) arrayMap3.get((String) sharedElementSourceNames.get(0));
                        fragmentTransitionImpl.setEpicenter(objWrapTransitionInSet, view11);
                        view = view11;
                    }
                    arrayList6.addAll(arrayMap4.values());
                    if (sharedElementTargetNames2.isEmpty() || (view5 = (View) arrayMap4.get((String) sharedElementTargetNames2.get(i))) == null) {
                        rect = rect2;
                    } else {
                        rect = rect2;
                        OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.6
                            @Override // java.lang.Runnable
                            public void run() {
                                fragmentTransitionImpl.getBoundsOnScreen(view5, rect);
                            }
                        });
                        z3 = true;
                    }
                    fragmentTransitionImpl.setSharedElementTargets(objWrapTransitionInSet, view8, arrayList5);
                    arrayList4 = arrayList5;
                    arrayMap = arrayMap2;
                    view4 = view8;
                    fragmentTransitionImpl.scheduleRemoveTargets(objWrapTransitionInSet, null, null, null, null, objWrapTransitionInSet, arrayList6);
                    Boolean bool = Boolean.TRUE;
                    map2 = map4;
                    map2.put(operation4, bool);
                    map2.put(operation5, bool);
                    obj2 = objWrapTransitionInSet;
                }
                HashMap map5 = map2;
                arrayList5 = arrayList4;
                map3 = map5;
                rect3 = rect;
                arrayList6 = arrayList6;
                arrayMap2 = arrayMap;
                view6 = view4;
            }
            view7 = view;
            HashMap map6 = map2;
            arrayList5 = arrayList4;
            map3 = map6;
            rect3 = rect;
            arrayList6 = arrayList6;
            arrayMap2 = arrayMap;
            view6 = view4;
        }
        ArrayList arrayList9 = arrayList5;
        HashMap map7 = map3;
        ArrayList arrayList10 = arrayList9;
        View view12 = view6;
        Rect rect4 = rect3;
        ArrayMap arrayMap5 = arrayMap2;
        ArrayList arrayList11 = arrayList6;
        boolean z4 = true;
        ArrayList arrayList12 = new ArrayList();
        Iterator it7 = list.iterator();
        Object obj3 = null;
        Object obj4 = null;
        while (it7.hasNext()) {
            TransitionInfo transitionInfo4 = (TransitionInfo) it7.next();
            if (transitionInfo4.isVisibilityUnchanged()) {
                map7.put(transitionInfo4.getOperation(), Boolean.FALSE);
                transitionInfo4.completeSpecialEffect();
                z4 = true;
            } else {
                Object objCloneTransition = fragmentTransitionImpl.cloneTransition(transitionInfo4.getTransition());
                SpecialEffectsController.Operation operation6 = transitionInfo4.getOperation();
                boolean z5 = obj2 != null && (operation6 == operation4 || operation6 == operation5);
                if (objCloneTransition == null) {
                    if (!z5) {
                        map7.put(operation6, Boolean.FALSE);
                        transitionInfo4.completeSpecialEffect();
                    }
                    View view13 = view;
                    arrayList = arrayList10;
                    view2 = view13;
                    view3 = view12;
                    arrayList3 = arrayList11;
                    arrayList2 = arrayList12;
                    map = map7;
                    str3 = str;
                    z2 = true;
                } else {
                    HashMap map8 = map7;
                    final ArrayList arrayList13 = new ArrayList();
                    ArrayList arrayList14 = arrayList12;
                    captureTransitioningViews(arrayList13, operation6.getFragment().mView);
                    if (z5) {
                        if (operation6 == operation4) {
                            arrayList13.removeAll(arrayList10);
                        } else {
                            arrayList13.removeAll(arrayList11);
                        }
                    }
                    if (arrayList13.isEmpty()) {
                        fragmentTransitionImpl.addTarget(objCloneTransition, view12);
                        View view14 = view;
                        arrayList = arrayList10;
                        view2 = view14;
                        view3 = view12;
                        objMergeTransitionsTogether2 = obj3;
                        operation3 = operation6;
                        str3 = str;
                        objMergeTransitionsTogether = obj4;
                        z2 = true;
                        arrayList2 = arrayList14;
                        arrayList3 = arrayList11;
                        obj = objCloneTransition;
                        map = map8;
                    } else {
                        fragmentTransitionImpl.addTargets(objCloneTransition, arrayList13);
                        str3 = str;
                        View view15 = view;
                        arrayList = arrayList10;
                        view2 = view15;
                        objMergeTransitionsTogether = obj4;
                        objMergeTransitionsTogether2 = obj3;
                        z2 = true;
                        view3 = view12;
                        arrayList2 = arrayList14;
                        arrayList3 = arrayList11;
                        obj = objCloneTransition;
                        map = map8;
                        fragmentTransitionImpl.scheduleRemoveTargets(obj, objCloneTransition, arrayList13, null, null, null, null);
                        if (operation6.getFinalState() == SpecialEffectsController.Operation.State.GONE) {
                            operation3 = operation6;
                            list2.remove(operation3);
                            ArrayList arrayList15 = new ArrayList(arrayList13);
                            arrayList15.remove(operation3.getFragment().mView);
                            fragmentTransitionImpl.scheduleHideFragmentView(obj, operation3.getFragment().mView, arrayList15);
                            OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.7
                                @Override // java.lang.Runnable
                                public void run() {
                                    MiuixFragmentTransition.setViewVisibility(arrayList13, 4);
                                }
                            });
                        } else {
                            operation3 = operation6;
                        }
                    }
                    if (operation3.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                        arrayList2.addAll(arrayList13);
                        if (z3) {
                            fragmentTransitionImpl.setEpicenter(obj, rect4);
                        }
                    } else {
                        fragmentTransitionImpl.setEpicenter(obj, view2);
                    }
                    map.put(operation3, Boolean.TRUE);
                    if (transitionInfo4.isOverlapAllowed()) {
                        objMergeTransitionsTogether2 = fragmentTransitionImpl.mergeTransitionsTogether(objMergeTransitionsTogether2, obj, null);
                    } else {
                        objMergeTransitionsTogether = fragmentTransitionImpl.mergeTransitionsTogether(objMergeTransitionsTogether, obj, null);
                    }
                    obj3 = objMergeTransitionsTogether2;
                    obj4 = objMergeTransitionsTogether;
                }
                ArrayList arrayList16 = arrayList;
                view = view2;
                arrayList10 = arrayList16;
                arrayList11 = arrayList3;
                operation4 = operation;
                operation5 = operation2;
                map7 = map;
                arrayList12 = arrayList2;
                z4 = z2;
                view12 = view3;
                str = str3;
            }
        }
        ArrayList<View> arrayList17 = arrayList10;
        ArrayList<View> arrayList18 = arrayList11;
        ArrayList arrayList19 = arrayList12;
        HashMap map9 = map7;
        boolean z6 = z4;
        String str6 = str;
        Object objMergeTransitionsInSequence = fragmentTransitionImpl.mergeTransitionsInSequence(obj3, obj4, obj2);
        if (objMergeTransitionsInSequence == null) {
            return map9;
        }
        Iterator it8 = list.iterator();
        while (it8.hasNext()) {
            final TransitionInfo transitionInfo5 = (TransitionInfo) it8.next();
            if (!transitionInfo5.isVisibilityUnchanged()) {
                Object transition = transitionInfo5.getTransition();
                final SpecialEffectsController.Operation operation7 = transitionInfo5.getOperation();
                boolean z7 = (obj2 == null || !(operation7 == operation || operation7 == operation2)) ? false : z6;
                if (transition == null && !z7) {
                    str2 = str6;
                } else if (ViewCompat.isLaidOut(getContainer())) {
                    str2 = str6;
                    fragmentTransitionImpl.setListenerForTransitionEnd(transitionInfo5.getOperation().getFragment(), objMergeTransitionsInSequence, transitionInfo5.getSignal(), new Runnable() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.8
                        @Override // java.lang.Runnable
                        public void run() {
                            transitionInfo5.completeSpecialEffect();
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "Transition for operation " + operation7 + "has completed");
                            }
                        }
                    });
                } else {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        str2 = str6;
                        Log.v(str2, "SpecialEffectsController: Container " + getContainer() + " has not been laid out. Completing operation " + operation7);
                    } else {
                        str2 = str6;
                    }
                    transitionInfo5.completeSpecialEffect();
                }
                str6 = str2;
            }
        }
        String str7 = str6;
        if (!ViewCompat.isLaidOut(getContainer())) {
            return map9;
        }
        MiuixFragmentTransition.setViewVisibility(arrayList19, 4);
        ArrayList arrayListPrepareSetNameOverridesReordered = fragmentTransitionImpl.prepareSetNameOverridesReordered(arrayList18);
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(str7, ">>>>> Beginning transition <<<<<");
            Log.v(str7, ">>>>> SharedElementFirstOutViews <<<<<");
            for (View view16 : arrayList17) {
                Log.v(str7, "View: " + view16 + " Name: " + ViewCompat.getTransitionName(view16));
            }
            Log.v(str7, ">>>>> SharedElementLastInViews <<<<<");
            for (View view17 : arrayList18) {
                Log.v(str7, "View: " + view17 + " Name: " + ViewCompat.getTransitionName(view17));
            }
        }
        fragmentTransitionImpl.beginDelayedTransition(getContainer(), objMergeTransitionsInSequence);
        fragmentTransitionImpl.setNameOverridesReordered(getContainer(), arrayList17, arrayList18, arrayListPrepareSetNameOverridesReordered, arrayMap5);
        MiuixFragmentTransition.setViewVisibility(arrayList19, 0);
        fragmentTransitionImpl.swapSharedElementTargets(obj2, arrayList17, arrayList18);
        return map9;
    }

    public void applyContainerChanges(SpecialEffectsController.Operation operation) {
        operation.getFinalState().applyState(operation.getFragment().mView);
    }

    public void captureTransitioningViews(ArrayList arrayList, View view) {
        if (!(view instanceof ViewGroup)) {
            if (arrayList.contains(view)) {
                return;
            }
            arrayList.add(view);
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        if (ViewGroupCompat.isTransitionGroup(viewGroup)) {
            if (arrayList.contains(view)) {
                return;
            }
            arrayList.add(viewGroup);
            return;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.getVisibility() == 0) {
                captureTransitioningViews(arrayList, childAt);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00a6  */
    @Override // androidx.fragment.app.SpecialEffectsController
    public void executeOperations(List list, boolean z) {
        Iterator it = list.iterator();
        SpecialEffectsController.Operation operation = null;
        SpecialEffectsController.Operation operation2 = null;
        while (it.hasNext()) {
            SpecialEffectsController.Operation operation3 = (SpecialEffectsController.Operation) it.next();
            SpecialEffectsController.Operation.State stateFrom = SpecialEffectsController.Operation.State.from(operation3.getFragment().mView);
            int i = AnonymousClass9.$SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[operation3.getFinalState().ordinal()];
            if (i == 1 || i == 2 || i == 3) {
                if (stateFrom == SpecialEffectsController.Operation.State.VISIBLE && operation == null) {
                    operation = operation3;
                }
            } else if (i == 4 && stateFrom != SpecialEffectsController.Operation.State.VISIBLE) {
                operation2 = operation3;
            }
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "Executing operations from " + operation + " to " + operation2);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList(list);
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            final SpecialEffectsController.Operation operation4 = (SpecialEffectsController.Operation) it2.next();
            CancellationSignal cancellationSignal = new CancellationSignal();
            operation4.markStartedSpecialEffect(cancellationSignal);
            arrayList.add(new AnimationInfo(operation4, cancellationSignal, z));
            CancellationSignal cancellationSignal2 = new CancellationSignal();
            operation4.markStartedSpecialEffect(cancellationSignal2);
            boolean z2 = false;
            if (z) {
                if (operation4 == operation) {
                    z2 = true;
                }
            } else if (operation4 == operation2) {
                z2 = true;
            }
            arrayList2.add(new TransitionInfo(operation4, cancellationSignal2, z, z2));
            operation4.addCompletionListener(new Runnable() { // from class: androidx.fragment.app.MiuixSpecialEffectsController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MiuixSpecialEffectsController.$r8$lambda$RvmUOLOvaf3fOEmpGsApmP2J5hQ(this.f$0, arrayList3, operation4);
                }
            });
        }
        Map mapStartTransitions = startTransitions(arrayList2, arrayList3, z, operation, operation2);
        startAnimations(arrayList, arrayList3, mapStartTransitions.containsValue(Boolean.TRUE), mapStartTransitions);
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            applyContainerChanges((SpecialEffectsController.Operation) it3.next());
        }
        arrayList3.clear();
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "Completed executing operations from " + operation + " to " + operation2);
        }
    }

    public void findNamedViews(Map map, View view) {
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            map.put(transitionName, view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    findNamedViews(map, childAt);
                }
            }
        }
    }

    @Override // androidx.fragment.app.SpecialEffectsController
    public /* bridge */ /* synthetic */ ViewGroup getContainer() {
        return super.getContainer();
    }

    public void retainMatchingViews(ArrayMap arrayMap, Collection collection) {
        Iterator it = arrayMap.entrySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(ViewCompat.getTransitionName((View) ((Map.Entry) it.next()).getValue()))) {
                it.remove();
            }
        }
    }
}
