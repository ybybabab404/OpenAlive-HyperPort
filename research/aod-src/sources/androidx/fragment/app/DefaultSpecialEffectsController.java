package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
class DefaultSpecialEffectsController extends SpecialEffectsController {

    /* JADX INFO: renamed from: androidx.fragment.app.DefaultSpecialEffectsController$10, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass10 {
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
            FragmentTransitionImpl fragmentTransitionImpl = FragmentTransition.PLATFORM_IMPL;
            if (fragmentTransitionImpl != null && fragmentTransitionImpl.canHandle(obj)) {
                return fragmentTransitionImpl;
            }
            FragmentTransitionImpl fragmentTransitionImpl2 = FragmentTransition.SUPPORT_IMPL;
            if (fragmentTransitionImpl2 != null && fragmentTransitionImpl2.canHandle(obj)) {
                return fragmentTransitionImpl2;
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

    public DefaultSpecialEffectsController(ViewGroup viewGroup) {
        super(viewGroup);
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

    /* JADX WARN: Code duplicated, block: B:27:0x0082  */
    @Override // androidx.fragment.app.SpecialEffectsController
    public void executeOperations(List list, boolean z) {
        Iterator it = list.iterator();
        SpecialEffectsController.Operation operation = null;
        SpecialEffectsController.Operation operation2 = null;
        while (it.hasNext()) {
            SpecialEffectsController.Operation operation3 = (SpecialEffectsController.Operation) it.next();
            SpecialEffectsController.Operation.State stateFrom = SpecialEffectsController.Operation.State.from(operation3.getFragment().mView);
            int i = AnonymousClass10.$SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[operation3.getFinalState().ordinal()];
            if (i == 1 || i == 2 || i == 3) {
                if (stateFrom == SpecialEffectsController.Operation.State.VISIBLE && operation == null) {
                    operation = operation3;
                }
            } else if (i == 4 && stateFrom != SpecialEffectsController.Operation.State.VISIBLE) {
                operation2 = operation3;
            }
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
            operation4.addCompletionListener(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.1
                @Override // java.lang.Runnable
                public void run() {
                    if (arrayList3.contains(operation4)) {
                        arrayList3.remove(operation4);
                        DefaultSpecialEffectsController.this.applyContainerChanges(operation4);
                    }
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

    public void retainMatchingViews(ArrayMap arrayMap, Collection collection) {
        Iterator it = arrayMap.entrySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(ViewCompat.getTransitionName((View) ((Map.Entry) it.next()).getValue()))) {
                it.remove();
            }
        }
    }

    public final void startAnimations(List list, List list2, boolean z, Map map) {
        final ViewGroup container = getContainer();
        Context context = container.getContext();
        ArrayList<AnimationInfo> arrayList = new ArrayList();
        Iterator it = list.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            final AnimationInfo animationInfo = (AnimationInfo) it.next();
            if (animationInfo.isVisibilityUnchanged()) {
                animationInfo.completeSpecialEffect();
            } else {
                FragmentAnim.AnimationOrAnimator animation = animationInfo.getAnimation(context);
                if (animation == null) {
                    animationInfo.completeSpecialEffect();
                } else {
                    final Animator animator = animation.animator;
                    if (animator == null) {
                        arrayList.add(animationInfo);
                    } else {
                        final SpecialEffectsController.Operation operation = animationInfo.getOperation();
                        Fragment fragment = operation.getFragment();
                        if (Boolean.TRUE.equals(map.get(operation))) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "Ignoring Animator set on " + fragment + " as this Fragment was involved in a Transition.");
                            }
                            animationInfo.completeSpecialEffect();
                        } else {
                            final boolean z3 = operation.getFinalState() == SpecialEffectsController.Operation.State.GONE;
                            if (z3) {
                                list2.remove(operation);
                            }
                            final View view = fragment.mView;
                            container.startViewTransition(view);
                            animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.2
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator animator2) {
                                    container.endViewTransition(view);
                                    if (z3) {
                                        operation.getFinalState().applyState(view);
                                    }
                                    animationInfo.completeSpecialEffect();
                                }
                            });
                            animator.setTarget(view);
                            animator.start();
                            animationInfo.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.3
                                @Override // androidx.core.os.CancellationSignal.OnCancelListener
                                public void onCancel() {
                                    animator.end();
                                }
                            });
                            z2 = true;
                        }
                    }
                }
            }
        }
        for (final AnimationInfo animationInfo2 : arrayList) {
            SpecialEffectsController.Operation operation2 = animationInfo2.getOperation();
            Fragment fragment2 = operation2.getFragment();
            if (z) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Transitions.");
                }
                animationInfo2.completeSpecialEffect();
            } else if (z2) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Animators.");
                }
                animationInfo2.completeSpecialEffect();
            } else {
                final View view2 = fragment2.mView;
                Animation animation2 = (Animation) Preconditions.checkNotNull(((FragmentAnim.AnimationOrAnimator) Preconditions.checkNotNull(animationInfo2.getAnimation(context))).animation);
                if (operation2.getFinalState() != SpecialEffectsController.Operation.State.REMOVED) {
                    view2.startAnimation(animation2);
                    animationInfo2.completeSpecialEffect();
                } else {
                    container.startViewTransition(view2);
                    FragmentAnim.EndViewTransitionAnimation endViewTransitionAnimation = new FragmentAnim.EndViewTransitionAnimation(animation2, container, view2);
                    endViewTransitionAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation3) {
                            container.post(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                                    container.endViewTransition(view2);
                                    animationInfo2.completeSpecialEffect();
                                }
                            });
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation3) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation3) {
                        }
                    });
                    view2.startAnimation(endViewTransitionAnimation);
                }
                animationInfo2.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.5
                    @Override // androidx.core.os.CancellationSignal.OnCancelListener
                    public void onCancel() {
                        view2.clearAnimation();
                        container.endViewTransition(view2);
                        animationInfo2.completeSpecialEffect();
                    }
                });
            }
        }
    }

    public final Map startTransitions(List list, List list2, final boolean z, final SpecialEffectsController.Operation operation, final SpecialEffectsController.Operation operation2) {
        Object obj;
        ArrayList arrayList;
        View view;
        View view2;
        ArrayList arrayList2;
        ArrayList arrayList3;
        Object objMergeTransitionsTogether;
        Object objMergeTransitionsTogether2;
        SpecialEffectsController.Operation operation3;
        View view3;
        Rect rect;
        ArrayList arrayList4;
        View view4;
        SharedElementCallback enterTransitionCallback;
        SharedElementCallback exitTransitionCallback;
        int i;
        final View view5;
        int i2;
        String strFindKeyForValue;
        int i3;
        DefaultSpecialEffectsController defaultSpecialEffectsController = this;
        HashMap map = new HashMap();
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
                map.put(transitionInfo2.getOperation(), Boolean.FALSE);
                transitionInfo2.completeSpecialEffect();
            }
        } else {
            View view6 = new View(defaultSpecialEffectsController.getContainer().getContext());
            final Rect rect2 = new Rect();
            ArrayList arrayList5 = new ArrayList();
            ArrayList arrayList6 = new ArrayList();
            ArrayMap arrayMap = new ArrayMap();
            Iterator it3 = list.iterator();
            Object obj2 = null;
            View view7 = null;
            boolean z2 = false;
            while (it3.hasNext()) {
                TransitionInfo transitionInfo3 = (TransitionInfo) it3.next();
                if (!transitionInfo3.hasSharedElementTransition() || operation == null || operation2 == null) {
                    view3 = view6;
                    rect = rect2;
                    arrayList4 = arrayList5;
                    view4 = view7;
                } else {
                    Object objWrapTransitionInSet = fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(transitionInfo3.getSharedElementTransition()));
                    ArrayList<String> sharedElementSourceNames = operation2.getFragment().getSharedElementSourceNames();
                    ArrayList<String> sharedElementSourceNames2 = operation.getFragment().getSharedElementSourceNames();
                    ArrayList<String> sharedElementTargetNames = operation.getFragment().getSharedElementTargetNames();
                    int i4 = 0;
                    while (i4 < sharedElementTargetNames.size()) {
                        int iIndexOf = sharedElementSourceNames.indexOf(sharedElementTargetNames.get(i4));
                        Object obj3 = objWrapTransitionInSet;
                        if (iIndexOf != -1) {
                            sharedElementSourceNames.set(iIndexOf, sharedElementSourceNames2.get(i4));
                        }
                        i4++;
                        objWrapTransitionInSet = obj3;
                    }
                    Object obj4 = objWrapTransitionInSet;
                    ArrayList<String> sharedElementTargetNames2 = operation2.getFragment().getSharedElementTargetNames();
                    if (z == 0) {
                        enterTransitionCallback = operation.getFragment().getExitTransitionCallback();
                        exitTransitionCallback = operation2.getFragment().getEnterTransitionCallback();
                    } else {
                        enterTransitionCallback = operation.getFragment().getEnterTransitionCallback();
                        exitTransitionCallback = operation2.getFragment().getExitTransitionCallback();
                    }
                    int i5 = 0;
                    for (int size = sharedElementSourceNames.size(); i5 < size; size = size) {
                        arrayMap.put(sharedElementSourceNames.get(i5), sharedElementTargetNames2.get(i5));
                        i5++;
                    }
                    ArrayMap arrayMap2 = new ArrayMap();
                    defaultSpecialEffectsController.findNamedViews(arrayMap2, operation.getFragment().mView);
                    arrayMap2.retainAll(sharedElementSourceNames);
                    if (enterTransitionCallback != null) {
                        enterTransitionCallback.onMapSharedElements(sharedElementSourceNames, arrayMap2);
                        int size2 = sharedElementSourceNames.size() - 1;
                        while (size2 >= 0) {
                            String str = sharedElementSourceNames.get(size2);
                            View view8 = (View) arrayMap2.get(str);
                            if (view8 == null) {
                                arrayMap.remove(str);
                                i3 = size2;
                            } else {
                                i3 = size2;
                                if (!str.equals(ViewCompat.getTransitionName(view8))) {
                                    arrayMap.put(ViewCompat.getTransitionName(view8), (String) arrayMap.remove(str));
                                }
                            }
                            size2 = i3 - 1;
                        }
                    } else {
                        arrayMap.retainAll(arrayMap2.keySet());
                    }
                    final ArrayMap arrayMap3 = new ArrayMap();
                    defaultSpecialEffectsController.findNamedViews(arrayMap3, operation2.getFragment().mView);
                    arrayMap3.retainAll(sharedElementTargetNames2);
                    arrayMap3.retainAll(arrayMap.values());
                    if (exitTransitionCallback != null) {
                        exitTransitionCallback.onMapSharedElements(sharedElementTargetNames2, arrayMap3);
                        int size3 = sharedElementTargetNames2.size() - 1;
                        while (size3 >= 0) {
                            String str2 = sharedElementTargetNames2.get(size3);
                            View view9 = (View) arrayMap3.get(str2);
                            if (view9 == null) {
                                String strFindKeyForValue2 = FragmentTransition.findKeyForValue(arrayMap, str2);
                                if (strFindKeyForValue2 != null) {
                                    arrayMap.remove(strFindKeyForValue2);
                                }
                                i2 = size3;
                            } else {
                                i2 = size3;
                                if (!str2.equals(ViewCompat.getTransitionName(view9)) && (strFindKeyForValue = FragmentTransition.findKeyForValue(arrayMap, str2)) != null) {
                                    arrayMap.put(strFindKeyForValue, ViewCompat.getTransitionName(view9));
                                }
                            }
                            size3 = i2 - 1;
                        }
                    } else {
                        FragmentTransition.retainValues(arrayMap, arrayMap3);
                    }
                    defaultSpecialEffectsController.retainMatchingViews(arrayMap2, arrayMap.keySet());
                    defaultSpecialEffectsController.retainMatchingViews(arrayMap3, arrayMap.values());
                    if (arrayMap.isEmpty()) {
                        arrayList5.clear();
                        arrayList6.clear();
                        view3 = view6;
                        rect = rect2;
                        arrayList4 = arrayList5;
                        arrayMap = arrayMap;
                        arrayList6 = arrayList6;
                        obj2 = null;
                    } else {
                        FragmentTransition.callSharedElementStartEnd(operation2.getFragment(), operation.getFragment(), z, arrayMap2, true);
                        view4 = view7;
                        defaultSpecialEffectsController = this;
                        OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.6
                            @Override // java.lang.Runnable
                            public void run() {
                                FragmentTransition.callSharedElementStartEnd(operation2.getFragment(), operation.getFragment(), z, arrayMap3, false);
                            }
                        });
                        arrayList5.addAll(arrayMap2.values());
                        if (sharedElementSourceNames.isEmpty()) {
                            i = 0;
                        } else {
                            i = 0;
                            View view10 = (View) arrayMap2.get(sharedElementSourceNames.get(0));
                            fragmentTransitionImpl.setEpicenter(obj4, view10);
                            view4 = view10;
                        }
                        arrayList6.addAll(arrayMap3.values());
                        if (!sharedElementTargetNames2.isEmpty() && (view5 = (View) arrayMap3.get(sharedElementTargetNames2.get(i))) != null) {
                            OneShotPreDrawListener.add(defaultSpecialEffectsController.getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.7
                                @Override // java.lang.Runnable
                                public void run() {
                                    fragmentTransitionImpl.getBoundsOnScreen(view5, rect2);
                                }
                            });
                            z2 = true;
                        }
                        fragmentTransitionImpl.setSharedElementTargets(obj4, view6, arrayList5);
                        ArrayList arrayList7 = arrayList5;
                        rect = rect2;
                        view3 = view6;
                        fragmentTransitionImpl.scheduleRemoveTargets(obj4, null, null, null, null, obj4, arrayList6);
                        Boolean bool = Boolean.TRUE;
                        map.put(operation, bool);
                        map.put(operation2, bool);
                        arrayList4 = arrayList7;
                        obj2 = obj4;
                    }
                    arrayList5 = arrayList4;
                    arrayList6 = arrayList6;
                    arrayMap = arrayMap;
                    view6 = view3;
                    rect2 = rect;
                }
                view7 = view4;
                arrayList5 = arrayList4;
                arrayList6 = arrayList6;
                arrayMap = arrayMap;
                view6 = view3;
                rect2 = rect;
            }
            SpecialEffectsController.Operation operation4 = operation;
            SpecialEffectsController.Operation operation5 = operation2;
            View view11 = view6;
            Rect rect3 = rect2;
            ArrayList arrayList8 = arrayList5;
            ArrayMap arrayMap4 = arrayMap;
            View view12 = view7;
            ArrayList arrayList9 = arrayList6;
            boolean z3 = false;
            ArrayList arrayList10 = new ArrayList();
            Iterator it4 = list.iterator();
            Object obj5 = null;
            Object obj6 = null;
            while (it4.hasNext()) {
                TransitionInfo transitionInfo4 = (TransitionInfo) it4.next();
                if (transitionInfo4.isVisibilityUnchanged()) {
                    map.put(transitionInfo4.getOperation(), Boolean.FALSE);
                    transitionInfo4.completeSpecialEffect();
                } else {
                    Object objCloneTransition = fragmentTransitionImpl.cloneTransition(transitionInfo4.getTransition());
                    SpecialEffectsController.Operation operation6 = transitionInfo4.getOperation();
                    boolean z4 = (obj2 == null || !(operation6 == operation4 || operation6 == operation5)) ? z3 : true;
                    if (objCloneTransition == null) {
                        if (!z4) {
                            map.put(operation6, Boolean.FALSE);
                            transitionInfo4.completeSpecialEffect();
                        }
                        view = view11;
                        arrayList2 = arrayList8;
                        arrayList = arrayList9;
                        arrayList3 = arrayList10;
                        view2 = view12;
                    } else {
                        ArrayList arrayList11 = arrayList10;
                        final ArrayList arrayList12 = new ArrayList();
                        defaultSpecialEffectsController.captureTransitioningViews(arrayList12, operation6.getFragment().mView);
                        if (z4) {
                            if (operation6 == operation4) {
                                arrayList12.removeAll(arrayList8);
                            } else {
                                arrayList12.removeAll(arrayList9);
                            }
                        }
                        if (arrayList12.isEmpty()) {
                            fragmentTransitionImpl.addTarget(objCloneTransition, view11);
                            view = view11;
                            arrayList2 = arrayList8;
                            arrayList = arrayList9;
                            objMergeTransitionsTogether = obj5;
                            objMergeTransitionsTogether2 = obj6;
                            obj = objCloneTransition;
                            operation3 = operation6;
                            view2 = view12;
                            arrayList3 = arrayList11;
                        } else {
                            fragmentTransitionImpl.addTargets(objCloneTransition, arrayList12);
                            ArrayList arrayList13 = arrayList8;
                            obj = objCloneTransition;
                            arrayList = arrayList9;
                            view = view11;
                            view2 = view12;
                            arrayList2 = arrayList13;
                            arrayList3 = arrayList11;
                            objMergeTransitionsTogether = obj5;
                            objMergeTransitionsTogether2 = obj6;
                            fragmentTransitionImpl.scheduleRemoveTargets(obj, obj, arrayList12, null, null, null, null);
                            if (operation6.getFinalState() == SpecialEffectsController.Operation.State.GONE) {
                                operation3 = operation6;
                                list2.remove(operation3);
                                ArrayList arrayList14 = new ArrayList(arrayList12);
                                arrayList14.remove(operation3.getFragment().mView);
                                fragmentTransitionImpl.scheduleHideFragmentView(obj, operation3.getFragment().mView, arrayList14);
                                OneShotPreDrawListener.add(defaultSpecialEffectsController.getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.8
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        FragmentTransition.setViewVisibility(arrayList12, 4);
                                    }
                                });
                            } else {
                                operation3 = operation6;
                            }
                        }
                        if (operation3.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                            arrayList3.addAll(arrayList12);
                            if (z2) {
                                fragmentTransitionImpl.setEpicenter(obj, rect3);
                            }
                        } else {
                            fragmentTransitionImpl.setEpicenter(obj, view2);
                        }
                        map.put(operation3, Boolean.TRUE);
                        if (transitionInfo4.isOverlapAllowed()) {
                            objMergeTransitionsTogether = fragmentTransitionImpl.mergeTransitionsTogether(objMergeTransitionsTogether, obj, null);
                        } else {
                            objMergeTransitionsTogether2 = fragmentTransitionImpl.mergeTransitionsTogether(objMergeTransitionsTogether2, obj, null);
                        }
                        obj5 = objMergeTransitionsTogether;
                        obj6 = objMergeTransitionsTogether2;
                    }
                    arrayList8 = arrayList2;
                    operation4 = operation;
                    operation5 = operation2;
                    arrayList10 = arrayList3;
                    view12 = view2;
                    view11 = view;
                    arrayList9 = arrayList;
                    z3 = false;
                }
            }
            ArrayList arrayList15 = arrayList8;
            ArrayList arrayList16 = arrayList9;
            ArrayList arrayList17 = arrayList10;
            Object objMergeTransitionsInSequence = fragmentTransitionImpl.mergeTransitionsInSequence(obj5, obj6, obj2);
            Iterator it5 = list.iterator();
            while (it5.hasNext()) {
                final TransitionInfo transitionInfo5 = (TransitionInfo) it5.next();
                if (!transitionInfo5.isVisibilityUnchanged()) {
                    Object transition = transitionInfo5.getTransition();
                    SpecialEffectsController.Operation operation7 = transitionInfo5.getOperation();
                    boolean z5 = obj2 != null && (operation7 == operation || operation7 == operation2);
                    if (transition != null || z5) {
                        if (ViewCompat.isLaidOut(defaultSpecialEffectsController.getContainer())) {
                            fragmentTransitionImpl.setListenerForTransitionEnd(transitionInfo5.getOperation().getFragment(), objMergeTransitionsInSequence, transitionInfo5.getSignal(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.9
                                @Override // java.lang.Runnable
                                public void run() {
                                    transitionInfo5.completeSpecialEffect();
                                }
                            });
                        } else {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "SpecialEffectsController: Container " + defaultSpecialEffectsController.getContainer() + " has not been laid out. Completing operation " + operation7);
                            }
                            transitionInfo5.completeSpecialEffect();
                        }
                    }
                }
            }
            if (ViewCompat.isLaidOut(defaultSpecialEffectsController.getContainer())) {
                FragmentTransition.setViewVisibility(arrayList17, 4);
                ArrayList arrayListPrepareSetNameOverridesReordered = fragmentTransitionImpl.prepareSetNameOverridesReordered(arrayList16);
                fragmentTransitionImpl.beginDelayedTransition(defaultSpecialEffectsController.getContainer(), objMergeTransitionsInSequence);
                fragmentTransitionImpl.setNameOverridesReordered(defaultSpecialEffectsController.getContainer(), arrayList15, arrayList16, arrayListPrepareSetNameOverridesReordered, arrayMap4);
                FragmentTransition.setViewVisibility(arrayList17, 0);
                fragmentTransitionImpl.swapSharedElementTargets(obj2, arrayList15, arrayList16);
                return map;
            }
        }
        return map;
    }
}
