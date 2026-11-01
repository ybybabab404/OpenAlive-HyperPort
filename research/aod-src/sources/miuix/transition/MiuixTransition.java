package miuix.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;
import androidx.core.view.ViewCompat;
import com.xiaomi.onetrack.util.ac;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MiuixTransition implements Cloneable {
    public static final int[] DEFAULT_MATCH_ORDER = {2, 1, 3, 4};
    public ArrayList mEndValuesList;
    public ArrayMap mNameOverrides;
    public ArrayList mStartValuesList;
    public final String mName = getClass().getName();
    public AnimConfig mAnimConfig = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.35f));
    public ArrayList mTargetIds = new ArrayList();
    public ArrayList mTargets = new ArrayList();
    public ArrayList mTargetNames = null;
    public ArrayList mTargetTypes = null;
    public ArrayList mTargetIdExcludes = null;
    public ArrayList mTargetExcludes = null;
    public ArrayList mTargetTypeExcludes = null;
    public ArrayList mTargetNameExcludes = null;
    public ArrayList mTargetIdChildExcludes = null;
    public ArrayList mTargetChildExcludes = null;
    public ArrayList mTargetTypeChildExcludes = null;
    public MiuixTransitionSet mParent = null;
    public int[] mMatchOrder = DEFAULT_MATCH_ORDER;
    public TransitionValuesMaps mStartValues = new TransitionValuesMaps();
    public TransitionValuesMaps mEndValues = new TransitionValuesMaps();
    public ViewGroup mSceneRoot = null;
    public boolean mCanRemoveViews = false;
    public int mNumInstances = 0;
    public CopyOnWriteArrayList mListeners = null;
    public ArrayList mTransitionRunners = new ArrayList();
    public int mTransitionMode = 0;

    public static class ArrayListManager {
    }

    public interface MiuixTransitionListener {
        void onTransitionEnd(MiuixTransition miuixTransition);

        void onTransitionStart(MiuixTransition miuixTransition);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TRANSITION_MODE {
    }

    public static class TransitionRunner {
        public AnimConfig[] configs;
        public Object fromTag;
        public IStateStyle stateStyle;
        public Object target;
        public Object toTag;

        public TransitionRunner(Object obj, Object obj2, Object obj3, AnimConfig... animConfigArr) {
            this.target = obj;
            this.fromTag = obj2;
            this.toTag = obj3;
            this.configs = animConfigArr;
        }

        public TransitionRunner(IStateStyle iStateStyle, Object obj, Object obj2, AnimConfig... animConfigArr) {
            this.stateStyle = iStateStyle;
            this.fromTag = obj;
            this.toTag = obj2;
            this.configs = animConfigArr;
        }

        public void run() {
            Log.d("Transition", "fromTag:" + this.fromTag + "\ntoTag:" + this.toTag);
            IStateStyle iStateStyle = this.stateStyle;
            if (iStateStyle != null) {
                iStateStyle.fromTo(this.fromTag, this.toTag, this.configs);
                return;
            }
            Object obj = this.target;
            if (obj instanceof View) {
                Folme.useAt((View) obj).state().fromTo(this.fromTag, this.toTag, this.configs);
            } else {
                Folme.useValue(obj).fromTo(this.fromTag, this.toTag, this.configs);
            }
        }

        public void setTo() {
            Log.d("Transition", "setTo:" + this.toTag);
            IStateStyle iStateStyle = this.stateStyle;
            if (iStateStyle != null) {
                iStateStyle.setTo(this.toTag, this.configs);
                return;
            }
            Object obj = this.target;
            if (obj instanceof View) {
                Folme.useAt((View) obj).state().setTo(this.toTag, this.configs);
            } else {
                Folme.useValue(obj).setTo(this.toTag, this.configs);
            }
        }
    }

    public MiuixTransition() {
    }

    public MiuixTransition(Context context, AttributeSet attributeSet) {
        float[] fArr = null;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Transition);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        boolean z = false;
        int i = 1;
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == R.styleable.Transition_delay) {
                this.mAnimConfig.setDelay(typedArrayObtainStyledAttributes.getInt(index, 0));
            } else if (index == R.styleable.Transition_fromSpeed) {
                this.mAnimConfig.setFromSpeed(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
            } else {
                if (index == R.styleable.Transition_ease) {
                    i = typedArrayObtainStyledAttributes.getInt(index, 1);
                } else if (index == R.styleable.Transition_params) {
                    String[] strArrSplit = typedArrayObtainStyledAttributes.getString(index).split(ac.b);
                    float[] fArr2 = new float[strArrSplit.length];
                    for (int i3 = 0; i3 < strArrSplit.length; i3++) {
                        fArr2[i3] = Float.valueOf(strArrSplit[i3]).floatValue();
                    }
                    fArr = fArr2;
                }
                z = true;
            }
        }
        if (z && fArr != null) {
            this.mAnimConfig.setEase(i, fArr);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public static void addViewValues(TransitionValuesMaps transitionValuesMaps, View view, TransitionValues transitionValues) {
        transitionValuesMaps.mViewValues.put(view, transitionValues);
        int id = view.getId();
        if (id >= 0) {
            if (transitionValuesMaps.mIdValues.indexOfKey(id) >= 0) {
                transitionValuesMaps.mIdValues.put(id, null);
            } else {
                transitionValuesMaps.mIdValues.put(id, view);
            }
        }
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            if (transitionValuesMaps.mNameValues.containsKey(transitionName)) {
                transitionValuesMaps.mNameValues.put(transitionName, null);
            } else {
                transitionValuesMaps.mNameValues.put(transitionName, view);
            }
        }
        if (view.getParent() instanceof ListView) {
            ListView listView = (ListView) view.getParent();
            if (listView.getAdapter().hasStableIds()) {
                long itemIdAtPosition = listView.getItemIdAtPosition(listView.getPositionForView(view));
                if (transitionValuesMaps.mItemIdValues.indexOfKey(itemIdAtPosition) < 0) {
                    ViewCompat.setHasTransientState(view, true);
                    transitionValuesMaps.mItemIdValues.put(itemIdAtPosition, view);
                    return;
                }
                View view2 = (View) transitionValuesMaps.mItemIdValues.get(itemIdAtPosition);
                if (view2 != null) {
                    ViewCompat.setHasTransientState(view2, false);
                    transitionValuesMaps.mItemIdValues.put(itemIdAtPosition, null);
                }
            }
        }
    }

    public static boolean diff(float f, float f2) {
        if (Float.isNaN(f) || Float.isNaN(f2)) {
            return Float.isNaN(f) != Float.isNaN(f2);
        }
        return Math.abs(f - f2) > 1.0E-6f;
    }

    public static boolean isValueChanged(TransitionValues transitionValues, TransitionValues transitionValues2, String str) {
        Object obj = transitionValues.values.get(str);
        Object obj2 = transitionValues2.values.get(str);
        if (obj == null && obj2 == null) {
            return false;
        }
        if (obj == null || obj2 == null) {
            return true;
        }
        return !obj.equals(obj2);
    }

    public MiuixTransition addListener(MiuixTransitionListener miuixTransitionListener) {
        if (this.mListeners == null) {
            this.mListeners = new CopyOnWriteArrayList();
        }
        this.mListeners.add(miuixTransitionListener);
        return this;
    }

    public MiuixTransition addTarget(View view) {
        this.mTargets.add(view);
        return this;
    }

    public void addTransitionRunner(TransitionRunner transitionRunner) {
        if (this.mTransitionRunners == null) {
            this.mTransitionRunners = new ArrayList();
        }
        this.mTransitionRunners.add(transitionRunner);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void addUnmatched(ArrayMap arrayMap, ArrayMap arrayMap2) {
        for (int i = 0; i < arrayMap.size(); i++) {
            TransitionValues transitionValues = (TransitionValues) arrayMap.valueAt(i);
            if (isValidTarget(transitionValues.view)) {
                this.mStartValuesList.add(transitionValues);
                this.mEndValuesList.add(null);
            }
        }
        for (int i2 = 0; i2 < arrayMap2.size(); i2++) {
            TransitionValues transitionValues2 = (TransitionValues) arrayMap2.valueAt(i2);
            if (isValidTarget(transitionValues2.view)) {
                this.mEndValuesList.add(transitionValues2);
                this.mStartValuesList.add(null);
            }
        }
    }

    public abstract void captureEndValues(TransitionValues transitionValues);

    public final void captureHierarchy(View view, boolean z) {
        if (view == null) {
            return;
        }
        if (this.mTransitionMode == 1 && view.getVisibility() == 8) {
            return;
        }
        int id = view.getId();
        ArrayList arrayList = this.mTargetIdExcludes;
        if (arrayList == null || !arrayList.contains(Integer.valueOf(id))) {
            ArrayList arrayList2 = this.mTargetExcludes;
            if (arrayList2 == null || !arrayList2.contains(view)) {
                ArrayList arrayList3 = this.mTargetTypeExcludes;
                if (arrayList3 != null) {
                    int size = arrayList3.size();
                    for (int i = 0; i < size; i++) {
                        if (((Class) this.mTargetTypeExcludes.get(i)).isInstance(view)) {
                            return;
                        }
                    }
                }
                if (view.getParent() instanceof ViewGroup) {
                    TransitionValues transitionValues = new TransitionValues(view);
                    if (z) {
                        captureStartValues(transitionValues);
                    } else {
                        captureEndValues(transitionValues);
                    }
                    transitionValues.mTargetedTransitions.add(this);
                    if (z) {
                        addViewValues(this.mStartValues, view, transitionValues);
                    } else {
                        addViewValues(this.mEndValues, view, transitionValues);
                    }
                }
                if (view instanceof ViewGroup) {
                    ArrayList arrayList4 = this.mTargetIdChildExcludes;
                    if (arrayList4 == null || !arrayList4.contains(Integer.valueOf(id))) {
                        ArrayList arrayList5 = this.mTargetChildExcludes;
                        if (arrayList5 == null || !arrayList5.contains(view)) {
                            ArrayList arrayList6 = this.mTargetTypeChildExcludes;
                            if (arrayList6 != null) {
                                int size2 = arrayList6.size();
                                for (int i2 = 0; i2 < size2; i2++) {
                                    if (((Class) this.mTargetTypeChildExcludes.get(i2)).isInstance(view)) {
                                        return;
                                    }
                                }
                            }
                            ViewGroup viewGroup = (ViewGroup) view;
                            for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                                captureHierarchy(viewGroup.getChildAt(i3), z);
                            }
                        }
                    }
                }
            }
        }
    }

    public abstract void captureStartValues(TransitionValues transitionValues);

    /* JADX WARN: Multi-variable type inference failed */
    public void captureValues(ViewGroup viewGroup, boolean z) {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayMap arrayMap;
        clearValues(z);
        if (!(this.mTargetIds.isEmpty() && this.mTargets.isEmpty()) && (((arrayList = this.mTargetNames) == null || arrayList.isEmpty()) && ((arrayList2 = this.mTargetTypes) == null || arrayList2.isEmpty()))) {
            for (int i = 0; i < this.mTargetIds.size(); i++) {
                View viewFindViewById = viewGroup.findViewById(((Integer) this.mTargetIds.get(i)).intValue());
                if (viewFindViewById != null && (this.mTransitionMode != 1 || viewFindViewById.getVisibility() != 8)) {
                    TransitionValues transitionValues = new TransitionValues(viewFindViewById);
                    if (z) {
                        captureStartValues(transitionValues);
                    } else {
                        captureEndValues(transitionValues);
                    }
                    transitionValues.mTargetedTransitions.add(this);
                    if (z) {
                        addViewValues(this.mStartValues, viewFindViewById, transitionValues);
                    } else {
                        addViewValues(this.mEndValues, viewFindViewById, transitionValues);
                    }
                }
            }
            for (int i2 = 0; i2 < this.mTargets.size(); i2++) {
                View view = (View) this.mTargets.get(i2);
                if (this.mTransitionMode != 1 || view.getVisibility() != 8) {
                    TransitionValues transitionValues2 = new TransitionValues(view);
                    if (z) {
                        captureStartValues(transitionValues2);
                    } else {
                        captureEndValues(transitionValues2);
                    }
                    transitionValues2.mTargetedTransitions.add(this);
                    if (z) {
                        addViewValues(this.mStartValues, view, transitionValues2);
                    } else {
                        addViewValues(this.mEndValues, view, transitionValues2);
                    }
                }
            }
        } else {
            captureHierarchy(viewGroup, z);
        }
        if (z || (arrayMap = this.mNameOverrides) == null) {
            return;
        }
        int size = arrayMap.size();
        ArrayList arrayList3 = new ArrayList(size);
        for (int i3 = 0; i3 < size; i3++) {
            arrayList3.add((View) this.mStartValues.mNameValues.remove((String) this.mNameOverrides.keyAt(i3)));
        }
        for (int i4 = 0; i4 < size; i4++) {
            View view2 = (View) arrayList3.get(i4);
            if (view2 != null) {
                this.mStartValues.mNameValues.put((String) this.mNameOverrides.valueAt(i4), view2);
            }
        }
    }

    public void clear() {
        clearValues(true);
        clearValues(false);
        clearListener();
        this.mAnimConfig.clear();
        this.mTransitionRunners.clear();
    }

    public void clearListener() {
        this.mListeners = null;
    }

    public void clearValues(boolean z) {
        if (z) {
            this.mStartValues.mViewValues.clear();
            this.mStartValues.mIdValues.clear();
            this.mStartValues.mItemIdValues.clear();
        } else {
            this.mEndValues.mViewValues.clear();
            this.mEndValues.mIdValues.clear();
            this.mEndValues.mItemIdValues.clear();
        }
    }

    @Override // 
    /* JADX INFO: renamed from: clone, reason: collision with other method in class */
    public MiuixTransition mo1355clone() {
        try {
            MiuixTransition miuixTransition = (MiuixTransition) super.clone();
            try {
                miuixTransition.mStartValues = new TransitionValuesMaps();
                miuixTransition.mEndValues = new TransitionValuesMaps();
                miuixTransition.mStartValuesList = null;
                miuixTransition.mEndValuesList = null;
                AnimConfig animConfig = new AnimConfig();
                this.mAnimConfig = animConfig;
                animConfig.copy(miuixTransition.mAnimConfig);
                return miuixTransition;
            } catch (CloneNotSupportedException unused) {
                return miuixTransition;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public void createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
    }

    public void createAnimators(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2, ArrayList arrayList, ArrayList arrayList2) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            TransitionValues transitionValues = (TransitionValues) arrayList.get(i);
            TransitionValues transitionValues2 = (TransitionValues) arrayList2.get(i);
            if (transitionValues != null && !transitionValues.mTargetedTransitions.contains(this)) {
                transitionValues = null;
            }
            if (transitionValues2 != null && !transitionValues2.mTargetedTransitions.contains(this)) {
                transitionValues2 = null;
            }
            if ((transitionValues != null || transitionValues2 != null) && (transitionValues == null || transitionValues2 == null || isTransitionRequired(transitionValues, transitionValues2))) {
                createAnimator(viewGroup, transitionValues, transitionValues2);
            }
        }
    }

    public void forceToEnd() {
        Iterator it = new ArrayList(this.mTransitionRunners).iterator();
        while (it.hasNext()) {
            ((TransitionRunner) it.next()).setTo();
        }
    }

    public AnimConfig getAnimConfig() {
        return this.mAnimConfig;
    }

    public TransitionValues getMatchedTransitionValues(View view, boolean z) {
        MiuixTransitionSet miuixTransitionSet = this.mParent;
        if (miuixTransitionSet != null) {
            return miuixTransitionSet.getMatchedTransitionValues(view, z);
        }
        ArrayList arrayList = z ? this.mStartValuesList : this.mEndValuesList;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            }
            TransitionValues transitionValues = (TransitionValues) arrayList.get(i);
            if (transitionValues == null) {
                return null;
            }
            if (transitionValues.view == view) {
                break;
            }
            i++;
        }
        if (i >= 0) {
            return (TransitionValues) (z ? this.mEndValuesList : this.mStartValuesList).get(i);
        }
        return null;
    }

    public List getTargetIds() {
        return this.mTargetIds;
    }

    public List getTargetNames() {
        return this.mTargetNames;
    }

    public List getTargetTypes() {
        return this.mTargetTypes;
    }

    public List getTargets() {
        return this.mTargets;
    }

    public String[] getTransitionProperties() {
        return null;
    }

    public TransitionValues getTransitionValues(View view, boolean z) {
        MiuixTransitionSet miuixTransitionSet = this.mParent;
        if (miuixTransitionSet != null) {
            return miuixTransitionSet.getTransitionValues(view, z);
        }
        return (TransitionValues) (z ? this.mStartValues : this.mEndValues).mViewValues.get(view);
    }

    public boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues != null && transitionValues2 != null) {
            String[] transitionProperties = getTransitionProperties();
            if (transitionProperties != null) {
                for (String str : transitionProperties) {
                    if (isValueChanged(transitionValues, transitionValues2, str)) {
                        return true;
                    }
                }
            } else {
                Iterator it = transitionValues.values.keySet().iterator();
                while (it.hasNext()) {
                    if (isValueChanged(transitionValues, transitionValues2, (String) it.next())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isValidTarget(View view) {
        ArrayList arrayList;
        ArrayList arrayList2;
        int id = view.getId();
        ArrayList arrayList3 = this.mTargetIdExcludes;
        if (arrayList3 != null && arrayList3.contains(Integer.valueOf(id))) {
            return false;
        }
        ArrayList arrayList4 = this.mTargetExcludes;
        if (arrayList4 != null && arrayList4.contains(view)) {
            return false;
        }
        ArrayList arrayList5 = this.mTargetTypeExcludes;
        if (arrayList5 != null) {
            int size = arrayList5.size();
            for (int i = 0; i < size; i++) {
                if (((Class) this.mTargetTypeExcludes.get(i)).isInstance(view)) {
                    return false;
                }
            }
        }
        if (this.mTargetNameExcludes != null && ViewCompat.getTransitionName(view) != null && this.mTargetNameExcludes.contains(ViewCompat.getTransitionName(view))) {
            return false;
        }
        if ((this.mTargetIds.isEmpty() && this.mTargets.isEmpty() && (((arrayList = this.mTargetTypes) == null || arrayList.isEmpty()) && ((arrayList2 = this.mTargetNames) == null || arrayList2.isEmpty()))) || this.mTargetIds.contains(Integer.valueOf(id)) || this.mTargets.contains(view)) {
            return true;
        }
        ArrayList arrayList6 = this.mTargetNames;
        if (arrayList6 != null && arrayList6.contains(ViewCompat.getTransitionName(view))) {
            return true;
        }
        if (this.mTargetTypes != null) {
            for (int i2 = 0; i2 < this.mTargetTypes.size(); i2++) {
                if (((Class) this.mTargetTypes.get(i2)).isInstance(view)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void matchIds(ArrayMap arrayMap, ArrayMap arrayMap2, SparseArray sparseArray, SparseArray sparseArray2) {
        View view;
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            View view2 = (View) sparseArray.valueAt(i);
            if (view2 != null && isValidTarget(view2) && (view = (View) sparseArray2.get(sparseArray.keyAt(i))) != null && isValidTarget(view)) {
                TransitionValues transitionValues = (TransitionValues) arrayMap.get(view2);
                TransitionValues transitionValues2 = (TransitionValues) arrayMap2.get(view);
                if (transitionValues != null && transitionValues2 != null) {
                    this.mStartValuesList.add(transitionValues);
                    this.mEndValuesList.add(transitionValues2);
                    arrayMap.remove(view2);
                    arrayMap2.remove(view);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void matchInstances(ArrayMap arrayMap, ArrayMap arrayMap2) {
        TransitionValues transitionValues;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            View view = (View) arrayMap.keyAt(size);
            if (view != null && isValidTarget(view) && (transitionValues = (TransitionValues) arrayMap2.remove(view)) != null && isValidTarget(transitionValues.view)) {
                this.mStartValuesList.add((TransitionValues) arrayMap.removeAt(size));
                this.mEndValuesList.add(transitionValues);
            }
        }
    }

    public final void matchItemIds(ArrayMap arrayMap, ArrayMap arrayMap2, LongSparseArray longSparseArray, LongSparseArray longSparseArray2) {
        View view;
        int size = longSparseArray.size();
        for (int i = 0; i < size; i++) {
            View view2 = (View) longSparseArray.valueAt(i);
            if (view2 != null && isValidTarget(view2) && (view = (View) longSparseArray2.get(longSparseArray.keyAt(i))) != null && isValidTarget(view)) {
                TransitionValues transitionValues = (TransitionValues) arrayMap.get(view2);
                TransitionValues transitionValues2 = (TransitionValues) arrayMap2.get(view);
                if (transitionValues != null && transitionValues2 != null) {
                    this.mStartValuesList.add(transitionValues);
                    this.mEndValuesList.add(transitionValues2);
                    arrayMap.remove(view2);
                    arrayMap2.remove(view);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void matchNames(ArrayMap arrayMap, ArrayMap arrayMap2, ArrayMap arrayMap3, ArrayMap arrayMap4) {
        View view;
        int size = arrayMap3.size();
        for (int i = 0; i < size; i++) {
            View view2 = (View) arrayMap3.valueAt(i);
            if (view2 != null && isValidTarget(view2) && (view = (View) arrayMap4.get(arrayMap3.keyAt(i))) != null && isValidTarget(view)) {
                TransitionValues transitionValues = (TransitionValues) arrayMap.get(view2);
                TransitionValues transitionValues2 = (TransitionValues) arrayMap2.get(view);
                if (transitionValues != null && transitionValues2 != null) {
                    this.mStartValuesList.add(transitionValues);
                    this.mEndValuesList.add(transitionValues2);
                    arrayMap.remove(view2);
                    arrayMap2.remove(view);
                }
            }
        }
    }

    public final void matchStartAndEnd(TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2) {
        ArrayMap arrayMap = new ArrayMap(transitionValuesMaps.mViewValues);
        ArrayMap arrayMap2 = new ArrayMap(transitionValuesMaps2.mViewValues);
        int i = 0;
        while (true) {
            int[] iArr = this.mMatchOrder;
            if (i >= iArr.length) {
                addUnmatched(arrayMap, arrayMap2);
                return;
            }
            int i2 = iArr[i];
            if (i2 == 1) {
                matchInstances(arrayMap, arrayMap2);
            } else if (i2 == 2) {
                matchNames(arrayMap, arrayMap2, transitionValuesMaps.mNameValues, transitionValuesMaps2.mNameValues);
            } else if (i2 == 3) {
                matchIds(arrayMap, arrayMap2, transitionValuesMaps.mIdValues, transitionValuesMaps2.mIdValues);
            } else if (i2 == 4) {
                matchItemIds(arrayMap, arrayMap2, transitionValuesMaps.mItemIdValues, transitionValuesMaps2.mItemIdValues);
            }
            i++;
        }
    }

    public void onTransitionEnd() {
        CopyOnWriteArrayList copyOnWriteArrayList = this.mListeners;
        if (copyOnWriteArrayList != null && !copyOnWriteArrayList.isEmpty()) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((MiuixTransitionListener) it.next()).onTransitionEnd(this);
            }
        }
        this.mTransitionRunners.clear();
        this.mAnimConfig.clear();
    }

    public void onTransitionStart() {
        CopyOnWriteArrayList copyOnWriteArrayList = this.mListeners;
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.isEmpty()) {
            return;
        }
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((MiuixTransitionListener) it.next()).onTransitionStart(this);
        }
    }

    public void playTransition(ViewGroup viewGroup) {
        this.mStartValuesList = new ArrayList();
        this.mEndValuesList = new ArrayList();
        matchStartAndEnd(this.mStartValues, this.mEndValues);
        createAnimators(viewGroup, this.mStartValues, this.mEndValues, this.mStartValuesList, this.mEndValuesList);
        runAnimators();
    }

    public MiuixTransition removeListener(MiuixTransitionListener miuixTransitionListener) {
        CopyOnWriteArrayList copyOnWriteArrayList = this.mListeners;
        if (copyOnWriteArrayList != null) {
            copyOnWriteArrayList.remove(miuixTransitionListener);
            if (this.mListeners.isEmpty()) {
                this.mListeners = null;
            }
        }
        return this;
    }

    public MiuixTransition removeTarget(View view) {
        this.mTargets.remove(view);
        return this;
    }

    public void runAnimators() {
        ArrayList arrayList = this.mTransitionRunners;
        if (arrayList == null || arrayList.isEmpty()) {
            onTransitionEnd();
            return;
        }
        int size = this.mTransitionRunners.size();
        for (int i = 0; i < size; i++) {
            ((TransitionRunner) this.mTransitionRunners.get(i)).run();
        }
    }

    public String toString() {
        return toString("");
    }

    public String toString(String str) {
        String str2 = str + getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + ": ";
        if (this.mTargetIds.size() <= 0 && this.mTargets.size() <= 0) {
            return str2;
        }
        String str3 = str2 + "tgts(";
        if (this.mTargetIds.size() > 0) {
            for (int i = 0; i < this.mTargetIds.size(); i++) {
                if (i > 0) {
                    str3 = str3 + ", ";
                }
                str3 = str3 + this.mTargetIds.get(i);
            }
        }
        if (this.mTargets.size() > 0) {
            for (int i2 = 0; i2 < this.mTargets.size(); i2++) {
                if (i2 > 0) {
                    str3 = str3 + ", ";
                }
                str3 = str3 + this.mTargets.get(i2);
            }
        }
        return str3 + ")";
    }
}
