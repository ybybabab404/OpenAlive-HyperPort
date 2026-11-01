package androidx.fragment.app;

import android.content.Context;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.transition.FragmentTransitionSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class FragmentTransition {
    public static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8, 10};
    public static final FragmentTransitionImpl PLATFORM_IMPL = new FragmentTransitionCompat21();
    public static final FragmentTransitionImpl SUPPORT_IMPL = resolveSupportImpl();

    public interface Callback {
        void onComplete(Fragment fragment, CancellationSignal cancellationSignal);

        void onStart(Fragment fragment, CancellationSignal cancellationSignal);
    }

    public static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void addSharedElementsWithMatchingNames(ArrayList arrayList, ArrayMap arrayMap, Collection collection) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            View view = (View) arrayMap.valueAt(size);
            if (collection.contains(ViewCompat.getTransitionName(view))) {
                arrayList.add(view);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:31:0x003d  */
    /* JADX WARN: Code duplicated, block: B:32:0x003f  */
    /* JADX WARN: Code duplicated, block: B:42:0x0055  */
    /* JADX WARN: Code duplicated, block: B:43:0x0057  */
    /* JADX WARN: Code duplicated, block: B:50:0x0066  */
    /* JADX WARN: Code duplicated, block: B:65:0x0087 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:66:0x0089  */
    /* JADX WARN: Code duplicated, block: B:67:0x008c  */
    public static void addToFirstInLastOut(BackStackRecord backStackRecord, FragmentTransaction.Op op, SparseArray sparseArray, boolean z, boolean z2) {
        int i;
        boolean z3;
        boolean z4;
        boolean z5;
        View view;
        boolean z6;
        Fragment fragment = op.mFragment;
        if (fragment == null || (i = fragment.mContainerId) == 0) {
            return;
        }
        int i2 = z ? INVERSE_OPS[op.mCmd] : op.mCmd;
        boolean z7 = false;
        boolean z8 = true;
        if (i2 == 1) {
            if (z2) {
                z3 = fragment.mIsNewlyAdded;
            } else if (!fragment.mAdded || fragment.mHidden) {
                z3 = false;
            } else {
                z3 = true;
            }
            z4 = false;
            z7 = z3;
            z5 = false;
        } else if (i2 == 3) {
            if (z2 ? !fragment.mAdded || fragment.mHidden : fragment.mAdded || (view = fragment.mView) == null || view.getVisibility() != 0 || fragment.mPostponedAlpha < 0.0f) {
                z6 = false;
            } else {
                z6 = true;
            }
            z4 = z6;
            z5 = true;
            z8 = false;
        } else if (i2 == 4) {
            if (!z2 ? !(!fragment.mAdded || fragment.mHidden) : fragment.mHiddenChanged && fragment.mAdded && fragment.mHidden) {
                z6 = false;
            } else {
                z6 = true;
            }
            z4 = z6;
            z5 = true;
            z8 = false;
        } else if (i2 == 5) {
            if (!z2) {
                z3 = fragment.mHidden;
            } else if (fragment.mHiddenChanged && !fragment.mHidden && fragment.mAdded) {
                z3 = true;
            } else {
                z3 = false;
            }
            z4 = false;
            z7 = z3;
            z5 = false;
        } else if (i2 == 6) {
            if (z2) {
                z6 = false;
            } else {
                z6 = false;
            }
            z4 = z6;
            z5 = true;
            z8 = false;
        } else if (i2 != 7) {
            z5 = false;
            z8 = false;
            z4 = false;
        } else {
            if (z2) {
                z3 = fragment.mIsNewlyAdded;
            } else {
                if (fragment.mAdded) {
                }
                z3 = false;
            }
            z4 = false;
            z7 = z3;
            z5 = false;
        }
        FragmentContainerTransition fragmentContainerTransitionEnsureContainer = (FragmentContainerTransition) sparseArray.get(i);
        if (z7) {
            fragmentContainerTransitionEnsureContainer = ensureContainer(fragmentContainerTransitionEnsureContainer, sparseArray, i);
            fragmentContainerTransitionEnsureContainer.lastIn = fragment;
            fragmentContainerTransitionEnsureContainer.lastInIsPop = z;
            fragmentContainerTransitionEnsureContainer.lastInTransaction = backStackRecord;
        }
        if (!z2 && z8) {
            if (fragmentContainerTransitionEnsureContainer != null && fragmentContainerTransitionEnsureContainer.firstOut == fragment) {
                fragmentContainerTransitionEnsureContainer.firstOut = null;
            }
            if (!backStackRecord.mReorderingAllowed) {
                FragmentManager fragmentManager = backStackRecord.mManager;
                fragmentManager.getFragmentStore().makeActive(fragmentManager.createOrGetFragmentStateManager(fragment));
                fragmentManager.moveToState(fragment);
            }
        }
        if (z4 && (fragmentContainerTransitionEnsureContainer == null || fragmentContainerTransitionEnsureContainer.firstOut == null)) {
            fragmentContainerTransitionEnsureContainer = ensureContainer(fragmentContainerTransitionEnsureContainer, sparseArray, i);
            fragmentContainerTransitionEnsureContainer.firstOut = fragment;
            fragmentContainerTransitionEnsureContainer.firstOutIsPop = z;
            fragmentContainerTransitionEnsureContainer.firstOutTransaction = backStackRecord;
        }
        if (z2 || !z5 || fragmentContainerTransitionEnsureContainer == null || fragmentContainerTransitionEnsureContainer.lastIn != fragment) {
            return;
        }
        fragmentContainerTransitionEnsureContainer.lastIn = null;
    }

    public static void calculateFragments(BackStackRecord backStackRecord, SparseArray sparseArray, boolean z) {
        int size = backStackRecord.mOps.size();
        for (int i = 0; i < size; i++) {
            addToFirstInLastOut(backStackRecord, (FragmentTransaction.Op) backStackRecord.mOps.get(i), sparseArray, false, z);
        }
    }

    public static ArrayMap calculateNameOverrides(int i, ArrayList arrayList, ArrayList arrayList2, int i2, int i3) {
        ArrayList arrayList3;
        ArrayList arrayList4;
        ArrayMap arrayMap = new ArrayMap();
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i4);
            if (backStackRecord.interactsWith(i)) {
                boolean zBooleanValue = ((Boolean) arrayList2.get(i4)).booleanValue();
                ArrayList arrayList5 = backStackRecord.mSharedElementSourceNames;
                if (arrayList5 != null) {
                    int size = arrayList5.size();
                    if (zBooleanValue) {
                        arrayList3 = backStackRecord.mSharedElementSourceNames;
                        arrayList4 = backStackRecord.mSharedElementTargetNames;
                    } else {
                        ArrayList arrayList6 = backStackRecord.mSharedElementSourceNames;
                        arrayList3 = backStackRecord.mSharedElementTargetNames;
                        arrayList4 = arrayList6;
                    }
                    for (int i5 = 0; i5 < size; i5++) {
                        String str = (String) arrayList4.get(i5);
                        String str2 = (String) arrayList3.get(i5);
                        String str3 = (String) arrayMap.remove(str2);
                        if (str3 != null) {
                            arrayMap.put(str, str3);
                        } else {
                            arrayMap.put(str, str2);
                        }
                    }
                }
            }
        }
        return arrayMap;
    }

    public static void calculatePopFragments(BackStackRecord backStackRecord, SparseArray sparseArray, boolean z) {
        if (backStackRecord.mManager.getContainer().onHasView()) {
            for (int size = backStackRecord.mOps.size() - 1; size >= 0; size--) {
                addToFirstInLastOut(backStackRecord, (FragmentTransaction.Op) backStackRecord.mOps.get(size), sparseArray, true, z);
            }
        }
    }

    public static void callSharedElementStartEnd(Fragment fragment, Fragment fragment2, boolean z, ArrayMap arrayMap, boolean z2) {
        SharedElementCallback enterTransitionCallback = z ? fragment2.getEnterTransitionCallback() : fragment.getEnterTransitionCallback();
        if (enterTransitionCallback != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int size = arrayMap == null ? 0 : arrayMap.size();
            for (int i = 0; i < size; i++) {
                arrayList2.add(arrayMap.keyAt(i));
                arrayList.add(arrayMap.valueAt(i));
            }
            if (z2) {
                enterTransitionCallback.onSharedElementStart(arrayList2, arrayList, null);
            } else {
                enterTransitionCallback.onSharedElementEnd(arrayList2, arrayList, null);
            }
        }
    }

    public static boolean canHandleAll(FragmentTransitionImpl fragmentTransitionImpl, List list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!fragmentTransitionImpl.canHandle(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static ArrayMap captureInSharedElements(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback enterTransitionCallback;
        ArrayList arrayList;
        String strFindKeyForValue;
        Fragment fragment = fragmentContainerTransition.lastIn;
        View view = fragment.getView();
        if (arrayMap.isEmpty() || obj == null || view == null) {
            arrayMap.clear();
            return null;
        }
        ArrayMap arrayMap2 = new ArrayMap();
        fragmentTransitionImpl.findNamedViews(arrayMap2, view);
        BackStackRecord backStackRecord = fragmentContainerTransition.lastInTransaction;
        if (fragmentContainerTransition.lastInIsPop) {
            enterTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = backStackRecord.mSharedElementSourceNames;
        } else {
            enterTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = backStackRecord.mSharedElementTargetNames;
        }
        if (arrayList != null) {
            arrayMap2.retainAll(arrayList);
            arrayMap2.retainAll(arrayMap.values());
        }
        if (enterTransitionCallback == null) {
            retainValues(arrayMap, arrayMap2);
            return arrayMap2;
        }
        enterTransitionCallback.onMapSharedElements(arrayList, arrayMap2);
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            String str = (String) arrayList.get(size);
            View view2 = (View) arrayMap2.get(str);
            if (view2 == null) {
                String strFindKeyForValue2 = findKeyForValue(arrayMap, str);
                if (strFindKeyForValue2 != null) {
                    arrayMap.remove(strFindKeyForValue2);
                }
            } else if (!str.equals(ViewCompat.getTransitionName(view2)) && (strFindKeyForValue = findKeyForValue(arrayMap, str)) != null) {
                arrayMap.put(strFindKeyForValue, ViewCompat.getTransitionName(view2));
            }
        }
        return arrayMap2;
    }

    public static ArrayMap captureOutSharedElements(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback exitTransitionCallback;
        ArrayList arrayList;
        if (arrayMap.isEmpty() || obj == null) {
            arrayMap.clear();
            return null;
        }
        Fragment fragment = fragmentContainerTransition.firstOut;
        ArrayMap arrayMap2 = new ArrayMap();
        fragmentTransitionImpl.findNamedViews(arrayMap2, fragment.requireView());
        BackStackRecord backStackRecord = fragmentContainerTransition.firstOutTransaction;
        if (fragmentContainerTransition.firstOutIsPop) {
            exitTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = backStackRecord.mSharedElementTargetNames;
        } else {
            exitTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = backStackRecord.mSharedElementSourceNames;
        }
        if (arrayList != null) {
            arrayMap2.retainAll(arrayList);
        }
        if (exitTransitionCallback == null) {
            arrayMap.retainAll(arrayMap2.keySet());
            return arrayMap2;
        }
        exitTransitionCallback.onMapSharedElements(arrayList, arrayMap2);
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            String str = (String) arrayList.get(size);
            View view = (View) arrayMap2.get(str);
            if (view == null) {
                arrayMap.remove(str);
            } else if (!str.equals(ViewCompat.getTransitionName(view))) {
                arrayMap.put(ViewCompat.getTransitionName(view), (String) arrayMap.remove(str));
            }
        }
        return arrayMap2;
    }

    public static FragmentTransitionImpl chooseImpl(Fragment fragment, Fragment fragment2) {
        ArrayList arrayList = new ArrayList();
        if (fragment != null) {
            Object exitTransition = fragment.getExitTransition();
            if (exitTransition != null) {
                arrayList.add(exitTransition);
            }
            Object returnTransition = fragment.getReturnTransition();
            if (returnTransition != null) {
                arrayList.add(returnTransition);
            }
            Object sharedElementReturnTransition = fragment.getSharedElementReturnTransition();
            if (sharedElementReturnTransition != null) {
                arrayList.add(sharedElementReturnTransition);
            }
        }
        if (fragment2 != null) {
            Object enterTransition = fragment2.getEnterTransition();
            if (enterTransition != null) {
                arrayList.add(enterTransition);
            }
            Object reenterTransition = fragment2.getReenterTransition();
            if (reenterTransition != null) {
                arrayList.add(reenterTransition);
            }
            Object sharedElementEnterTransition = fragment2.getSharedElementEnterTransition();
            if (sharedElementEnterTransition != null) {
                arrayList.add(sharedElementEnterTransition);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        FragmentTransitionImpl fragmentTransitionImpl = PLATFORM_IMPL;
        if (fragmentTransitionImpl != null && canHandleAll(fragmentTransitionImpl, arrayList)) {
            return fragmentTransitionImpl;
        }
        FragmentTransitionImpl fragmentTransitionImpl2 = SUPPORT_IMPL;
        if (fragmentTransitionImpl2 != null && canHandleAll(fragmentTransitionImpl2, arrayList)) {
            return fragmentTransitionImpl2;
        }
        if (fragmentTransitionImpl == null && fragmentTransitionImpl2 == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    public static ArrayList configureEnteringExitingViews(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Fragment fragment, ArrayList arrayList, View view) {
        if (obj == null) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        View view2 = fragment.getView();
        if (view2 != null) {
            fragmentTransitionImpl.captureTransitioningViews(arrayList2, view2);
        }
        if (arrayList != null) {
            arrayList2.removeAll(arrayList);
        }
        if (!arrayList2.isEmpty()) {
            arrayList2.add(view);
            fragmentTransitionImpl.addTargets(obj, arrayList2);
        }
        return arrayList2;
    }

    public static Object configureSharedElementsOrdered(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, View view, final ArrayMap arrayMap, final FragmentContainerTransition fragmentContainerTransition, final ArrayList arrayList, final ArrayList arrayList2, final Object obj, Object obj2) {
        View view2;
        final Object obj3;
        final Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        Rect rect = null;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        final boolean z = fragmentContainerTransition.lastInIsPop;
        Object sharedElementTransition = arrayMap.isEmpty() ? null : getSharedElementTransition(fragmentTransitionImpl, fragment, fragment2, z);
        ArrayMap arrayMapCaptureOutSharedElements = captureOutSharedElements(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition);
        if (arrayMap.isEmpty()) {
            sharedElementTransition = null;
        } else {
            arrayList.addAll(arrayMapCaptureOutSharedElements.values());
        }
        if (obj == null && obj2 == null && sharedElementTransition == null) {
            return null;
        }
        callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureOutSharedElements, true);
        if (sharedElementTransition != null) {
            Rect rect2 = new Rect();
            view2 = view;
            fragmentTransitionImpl.setSharedElementTargets(sharedElementTransition, view2, arrayList);
            Object obj4 = sharedElementTransition;
            setOutEpicenter(fragmentTransitionImpl, obj4, obj2, arrayMapCaptureOutSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
            obj3 = obj4;
            if (obj != null) {
                fragmentTransitionImpl.setEpicenter(obj, rect2);
            }
            rect = rect2;
        } else {
            view2 = view;
            obj3 = sharedElementTransition;
        }
        final View view3 = view2;
        final Rect rect3 = rect;
        OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.6
            @Override // java.lang.Runnable
            public void run() {
                ArrayMap arrayMapCaptureInSharedElements = FragmentTransition.captureInSharedElements(fragmentTransitionImpl, arrayMap, obj3, fragmentContainerTransition);
                if (arrayMapCaptureInSharedElements != null) {
                    arrayList2.addAll(arrayMapCaptureInSharedElements.values());
                    arrayList2.add(view3);
                }
                FragmentTransition.callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureInSharedElements, false);
                Object obj5 = obj3;
                if (obj5 != null) {
                    fragmentTransitionImpl.swapSharedElementTargets(obj5, arrayList, arrayList2);
                    View inEpicenterView = FragmentTransition.getInEpicenterView(arrayMapCaptureInSharedElements, fragmentContainerTransition, obj, z);
                    if (inEpicenterView != null) {
                        fragmentTransitionImpl.getBoundsOnScreen(inEpicenterView, rect3);
                    }
                }
            }
        });
        return obj3;
    }

    public static Object configureSharedElementsReordered(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, View view, ArrayMap arrayMap, FragmentContainerTransition fragmentContainerTransition, ArrayList arrayList, ArrayList arrayList2, Object obj, Object obj2) {
        final Rect rect;
        final Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        if (fragment != null) {
            fragment.requireView().setVisibility(0);
        }
        final View view2 = null;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        final boolean z = fragmentContainerTransition.lastInIsPop;
        Object sharedElementTransition = arrayMap.isEmpty() ? null : getSharedElementTransition(fragmentTransitionImpl, fragment, fragment2, z);
        ArrayMap arrayMapCaptureOutSharedElements = captureOutSharedElements(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition);
        final ArrayMap arrayMapCaptureInSharedElements = captureInSharedElements(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition);
        if (arrayMap.isEmpty()) {
            if (arrayMapCaptureOutSharedElements != null) {
                arrayMapCaptureOutSharedElements.clear();
            }
            if (arrayMapCaptureInSharedElements != null) {
                arrayMapCaptureInSharedElements.clear();
            }
            sharedElementTransition = null;
        } else {
            addSharedElementsWithMatchingNames(arrayList, arrayMapCaptureOutSharedElements, arrayMap.keySet());
            addSharedElementsWithMatchingNames(arrayList2, arrayMapCaptureInSharedElements, arrayMap.values());
        }
        if (obj == null && obj2 == null && sharedElementTransition == null) {
            return null;
        }
        callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureOutSharedElements, true);
        if (sharedElementTransition != null) {
            arrayList2.add(view);
            fragmentTransitionImpl.setSharedElementTargets(sharedElementTransition, view, arrayList);
            setOutEpicenter(fragmentTransitionImpl, sharedElementTransition, obj2, arrayMapCaptureOutSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
            Rect rect2 = new Rect();
            View inEpicenterView = getInEpicenterView(arrayMapCaptureInSharedElements, fragmentContainerTransition, obj, z);
            if (inEpicenterView != null) {
                fragmentTransitionImpl.setEpicenter(obj, rect2);
            }
            rect = rect2;
            view2 = inEpicenterView;
        } else {
            rect = null;
        }
        OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.5
            @Override // java.lang.Runnable
            public void run() {
                FragmentTransition.callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureInSharedElements, false);
                View view3 = view2;
                if (view3 != null) {
                    fragmentTransitionImpl.getBoundsOnScreen(view3, rect);
                }
            }
        });
        return sharedElementTransition;
    }

    public static void configureTransitionsOrdered(ViewGroup viewGroup, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap arrayMap, final Callback callback) {
        Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        FragmentTransitionImpl fragmentTransitionImplChooseImpl = chooseImpl(fragment2, fragment);
        if (fragmentTransitionImplChooseImpl == null) {
            return;
        }
        boolean z = fragmentContainerTransition.lastInIsPop;
        boolean z2 = fragmentContainerTransition.firstOutIsPop;
        Object enterTransition = getEnterTransition(fragmentTransitionImplChooseImpl, fragment, z);
        Object exitTransition = getExitTransition(fragmentTransitionImplChooseImpl, fragment2, z2);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Object objConfigureSharedElementsOrdered = configureSharedElementsOrdered(fragmentTransitionImplChooseImpl, viewGroup, view, arrayMap, fragmentContainerTransition, arrayList, arrayList2, enterTransition, exitTransition);
        if (enterTransition == null && objConfigureSharedElementsOrdered == null && exitTransition == null) {
            return;
        }
        ArrayList arrayListConfigureEnteringExitingViews = configureEnteringExitingViews(fragmentTransitionImplChooseImpl, exitTransition, fragment2, arrayList, view);
        if (arrayListConfigureEnteringExitingViews == null || arrayListConfigureEnteringExitingViews.isEmpty()) {
            exitTransition = null;
        }
        Object obj = exitTransition;
        fragmentTransitionImplChooseImpl.addTarget(enterTransition, view);
        Object objMergeTransitions = mergeTransitions(fragmentTransitionImplChooseImpl, enterTransition, obj, objConfigureSharedElementsOrdered, fragment, fragmentContainerTransition.lastInIsPop);
        if (fragment2 != null && arrayListConfigureEnteringExitingViews != null && (arrayListConfigureEnteringExitingViews.size() > 0 || arrayList.size() > 0)) {
            final CancellationSignal cancellationSignal = new CancellationSignal();
            callback.onStart(fragment2, cancellationSignal);
            fragmentTransitionImplChooseImpl.setListenerForTransitionEnd(fragment2, objMergeTransitions, cancellationSignal, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.3
                @Override // java.lang.Runnable
                public void run() {
                    callback.onComplete(fragment2, cancellationSignal);
                }
            });
        }
        if (objMergeTransitions != null) {
            ArrayList arrayList3 = new ArrayList();
            fragmentTransitionImplChooseImpl.scheduleRemoveTargets(objMergeTransitions, enterTransition, arrayList3, obj, arrayListConfigureEnteringExitingViews, objConfigureSharedElementsOrdered, arrayList2);
            scheduleTargetChange(fragmentTransitionImplChooseImpl, viewGroup, fragment, view, arrayList2, enterTransition, arrayList3, obj, arrayListConfigureEnteringExitingViews);
            fragmentTransitionImplChooseImpl.setNameOverridesOrdered(viewGroup, arrayList2, arrayMap);
            fragmentTransitionImplChooseImpl.beginDelayedTransition(viewGroup, objMergeTransitions);
            fragmentTransitionImplChooseImpl.scheduleNameReset(viewGroup, arrayList2, arrayMap);
        }
    }

    public static void configureTransitionsReordered(ViewGroup viewGroup, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap arrayMap, final Callback callback) {
        Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        FragmentTransitionImpl fragmentTransitionImplChooseImpl = chooseImpl(fragment2, fragment);
        if (fragmentTransitionImplChooseImpl == null) {
            return;
        }
        boolean z = fragmentContainerTransition.lastInIsPop;
        boolean z2 = fragmentContainerTransition.firstOutIsPop;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Object enterTransition = getEnterTransition(fragmentTransitionImplChooseImpl, fragment, z);
        Object exitTransition = getExitTransition(fragmentTransitionImplChooseImpl, fragment2, z2);
        Object objConfigureSharedElementsReordered = configureSharedElementsReordered(fragmentTransitionImplChooseImpl, viewGroup, view, arrayMap, fragmentContainerTransition, arrayList2, arrayList, enterTransition, exitTransition);
        if (enterTransition == null && objConfigureSharedElementsReordered == null && exitTransition == null) {
            return;
        }
        ArrayList arrayListConfigureEnteringExitingViews = configureEnteringExitingViews(fragmentTransitionImplChooseImpl, exitTransition, fragment2, arrayList2, view);
        ArrayList arrayListConfigureEnteringExitingViews2 = configureEnteringExitingViews(fragmentTransitionImplChooseImpl, enterTransition, fragment, arrayList, view);
        setViewVisibility(arrayListConfigureEnteringExitingViews2, 4);
        Object objMergeTransitions = mergeTransitions(fragmentTransitionImplChooseImpl, enterTransition, exitTransition, objConfigureSharedElementsReordered, fragment, z);
        if (fragment2 != null && arrayListConfigureEnteringExitingViews != null && (arrayListConfigureEnteringExitingViews.size() > 0 || arrayList2.size() > 0)) {
            final CancellationSignal cancellationSignal = new CancellationSignal();
            callback.onStart(fragment2, cancellationSignal);
            fragmentTransitionImplChooseImpl.setListenerForTransitionEnd(fragment2, objMergeTransitions, cancellationSignal, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.1
                @Override // java.lang.Runnable
                public void run() {
                    callback.onComplete(fragment2, cancellationSignal);
                }
            });
        }
        if (objMergeTransitions != null) {
            replaceHide(fragmentTransitionImplChooseImpl, exitTransition, fragment2, arrayListConfigureEnteringExitingViews);
            ArrayList arrayListPrepareSetNameOverridesReordered = fragmentTransitionImplChooseImpl.prepareSetNameOverridesReordered(arrayList);
            fragmentTransitionImplChooseImpl.scheduleRemoveTargets(objMergeTransitions, enterTransition, arrayListConfigureEnteringExitingViews2, exitTransition, arrayListConfigureEnteringExitingViews, objConfigureSharedElementsReordered, arrayList);
            fragmentTransitionImplChooseImpl.beginDelayedTransition(viewGroup, objMergeTransitions);
            fragmentTransitionImplChooseImpl.setNameOverridesReordered(viewGroup, arrayList2, arrayList, arrayListPrepareSetNameOverridesReordered, arrayMap);
            setViewVisibility(arrayListConfigureEnteringExitingViews2, 0);
            fragmentTransitionImplChooseImpl.swapSharedElementTargets(objConfigureSharedElementsReordered, arrayList2, arrayList);
        }
    }

    public static FragmentContainerTransition ensureContainer(FragmentContainerTransition fragmentContainerTransition, SparseArray sparseArray, int i) {
        if (fragmentContainerTransition != null) {
            return fragmentContainerTransition;
        }
        FragmentContainerTransition fragmentContainerTransition2 = new FragmentContainerTransition();
        sparseArray.put(i, fragmentContainerTransition2);
        return fragmentContainerTransition2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static String findKeyForValue(ArrayMap arrayMap, String str) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(arrayMap.valueAt(i))) {
                return (String) arrayMap.keyAt(i);
            }
        }
        return null;
    }

    public static Object getEnterTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return fragmentTransitionImpl.cloneTransition(z ? fragment.getReenterTransition() : fragment.getEnterTransition());
    }

    public static Object getExitTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return fragmentTransitionImpl.cloneTransition(z ? fragment.getReturnTransition() : fragment.getExitTransition());
    }

    public static View getInEpicenterView(ArrayMap arrayMap, FragmentContainerTransition fragmentContainerTransition, Object obj, boolean z) {
        ArrayList arrayList;
        BackStackRecord backStackRecord = fragmentContainerTransition.lastInTransaction;
        if (obj == null || arrayMap == null || (arrayList = backStackRecord.mSharedElementSourceNames) == null || arrayList.isEmpty()) {
            return null;
        }
        return (View) arrayMap.get(z ? (String) backStackRecord.mSharedElementSourceNames.get(0) : (String) backStackRecord.mSharedElementTargetNames.get(0));
    }

    public static Object getSharedElementTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, Fragment fragment2, boolean z) {
        if (fragment == null || fragment2 == null) {
            return null;
        }
        return fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(z ? fragment2.getSharedElementReturnTransition() : fragment.getSharedElementEnterTransition()));
    }

    public static Object mergeTransitions(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Object obj2, Object obj3, Fragment fragment, boolean z) {
        boolean allowReturnTransitionOverlap;
        if (obj == null || obj2 == null || fragment == null) {
            allowReturnTransitionOverlap = true;
        } else {
            allowReturnTransitionOverlap = z ? fragment.getAllowReturnTransitionOverlap() : fragment.getAllowEnterTransitionOverlap();
        }
        return allowReturnTransitionOverlap ? fragmentTransitionImpl.mergeTransitionsTogether(obj2, obj, obj3) : fragmentTransitionImpl.mergeTransitionsInSequence(obj2, obj, obj3);
    }

    public static void replaceHide(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Fragment fragment, final ArrayList arrayList) {
        if (fragment != null && obj != null && fragment.mAdded && fragment.mHidden && fragment.mHiddenChanged) {
            fragment.setHideReplaced(true);
            fragmentTransitionImpl.scheduleHideFragmentView(obj, fragment.getView(), arrayList);
            OneShotPreDrawListener.add(fragment.mContainer, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.2
                @Override // java.lang.Runnable
                public void run() {
                    FragmentTransition.setViewVisibility(arrayList, 4);
                }
            });
        }
    }

    public static FragmentTransitionImpl resolveSupportImpl() {
        try {
            return (FragmentTransitionImpl) FragmentTransitionSupport.class.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void retainValues(ArrayMap arrayMap, ArrayMap arrayMap2) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            if (!arrayMap2.containsKey((String) arrayMap.valueAt(size))) {
                arrayMap.removeAt(size);
            }
        }
    }

    public static void scheduleTargetChange(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, final Fragment fragment, final View view, final ArrayList arrayList, final Object obj, final ArrayList arrayList2, final Object obj2, final ArrayList arrayList3) {
        OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.4
            @Override // java.lang.Runnable
            public void run() {
                Object obj3 = obj;
                if (obj3 != null) {
                    fragmentTransitionImpl.removeTarget(obj3, view);
                    arrayList2.addAll(FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, obj, fragment, arrayList, view));
                }
                if (arrayList3 != null) {
                    if (obj2 != null) {
                        ArrayList arrayList4 = new ArrayList();
                        arrayList4.add(view);
                        fragmentTransitionImpl.replaceTargets(obj2, arrayList3, arrayList4);
                    }
                    arrayList3.clear();
                    arrayList3.add(view);
                }
            }
        });
    }

    public static void setOutEpicenter(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Object obj2, ArrayMap arrayMap, boolean z, BackStackRecord backStackRecord) {
        ArrayList arrayList = backStackRecord.mSharedElementSourceNames;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        View view = (View) arrayMap.get(z ? (String) backStackRecord.mSharedElementTargetNames.get(0) : (String) backStackRecord.mSharedElementSourceNames.get(0));
        fragmentTransitionImpl.setEpicenter(obj, view);
        if (obj2 != null) {
            fragmentTransitionImpl.setEpicenter(obj2, view);
        }
    }

    public static void setViewVisibility(ArrayList arrayList, int i) {
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((View) arrayList.get(size)).setVisibility(i);
        }
    }

    public static void startTransitions(Context context, FragmentContainer fragmentContainer, ArrayList arrayList, ArrayList arrayList2, int i, int i2, boolean z, Callback callback) {
        ViewGroup viewGroup;
        SparseArray sparseArray = new SparseArray();
        for (int i3 = i; i3 < i2; i3++) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i3);
            if (((Boolean) arrayList2.get(i3)).booleanValue()) {
                calculatePopFragments(backStackRecord, sparseArray, z);
            } else {
                calculateFragments(backStackRecord, sparseArray, z);
            }
        }
        if (sparseArray.size() != 0) {
            View view = new View(context);
            int size = sparseArray.size();
            for (int i4 = 0; i4 < size; i4++) {
                int iKeyAt = sparseArray.keyAt(i4);
                ArrayMap arrayMapCalculateNameOverrides = calculateNameOverrides(iKeyAt, arrayList, arrayList2, i, i2);
                FragmentContainerTransition fragmentContainerTransition = (FragmentContainerTransition) sparseArray.valueAt(i4);
                if (fragmentContainer.onHasView() && (viewGroup = (ViewGroup) fragmentContainer.onFindViewById(iKeyAt)) != null) {
                    if (z) {
                        configureTransitionsReordered(viewGroup, fragmentContainerTransition, view, arrayMapCalculateNameOverrides, callback);
                    } else {
                        configureTransitionsOrdered(viewGroup, fragmentContainerTransition, view, arrayMapCalculateNameOverrides, callback);
                    }
                }
            }
        }
    }
}
