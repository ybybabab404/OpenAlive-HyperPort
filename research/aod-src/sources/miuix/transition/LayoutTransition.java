package miuix.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes3.dex */
public class LayoutTransition extends android.animation.LayoutTransition {
    public final ArrayList currentAppearingViews;
    public final LinkedHashMap currentChangingAnimations;
    public final ArrayList currentDisappearingViews;
    public final HashMap layoutChangeListenerMap;
    public boolean mAnimateParentHierarchy;
    public EaseManager.EaseStyle mAppearingEase;
    public EaseManager.EaseStyle mChangingAppearingEase;
    public EaseManager.EaseStyle mChangingDisappearingEase;
    public EaseManager.EaseStyle mChangingEase;
    public EaseManager.EaseStyle mDisappearingEase;
    public ArrayList mListeners;
    public int mTransitionTypes;

    public static class ChangingAnimator {
        public AnimConfig mAnimConfig;
        public float mBottom;
        public IFolme mFolmeImpl;
        public float mLeft;
        public float mRight;
        public float mScrollX;
        public float mScrollY;
        public View mTarget;
        public float mTop;
        public static final FloatProperty LEFT_PROPERTY = new FloatProperty<ChangingAnimator>("left") { // from class: miuix.transition.LayoutTransition.ChangingAnimator.1
            @Override // miuix.animation.property.FloatProperty
            public float getValue(ChangingAnimator changingAnimator) {
                return changingAnimator.mLeft;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(ChangingAnimator changingAnimator, float f) {
                changingAnimator.mLeft = f;
                View view = changingAnimator.mTarget;
                if (view != null) {
                    view.setLeft((int) f);
                }
            }
        };
        public static final FloatProperty TOP_PROPERTY = new FloatProperty<ChangingAnimator>("top") { // from class: miuix.transition.LayoutTransition.ChangingAnimator.2
            @Override // miuix.animation.property.FloatProperty
            public float getValue(ChangingAnimator changingAnimator) {
                return changingAnimator.mTop;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(ChangingAnimator changingAnimator, float f) {
                changingAnimator.mTop = f;
                View view = changingAnimator.mTarget;
                if (view != null) {
                    view.setTop((int) f);
                }
            }
        };
        public static final FloatProperty RIGHT_PROPERTY = new FloatProperty<ChangingAnimator>("right") { // from class: miuix.transition.LayoutTransition.ChangingAnimator.3
            @Override // miuix.animation.property.FloatProperty
            public float getValue(ChangingAnimator changingAnimator) {
                return changingAnimator.mRight;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(ChangingAnimator changingAnimator, float f) {
                changingAnimator.mRight = f;
                View view = changingAnimator.mTarget;
                if (view != null) {
                    view.setRight((int) f);
                }
            }
        };
        public static final FloatProperty BOTTOM_PROPERTY = new FloatProperty<ChangingAnimator>("bottom") { // from class: miuix.transition.LayoutTransition.ChangingAnimator.4
            @Override // miuix.animation.property.FloatProperty
            public float getValue(ChangingAnimator changingAnimator) {
                return changingAnimator.mBottom;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(ChangingAnimator changingAnimator, float f) {
                changingAnimator.mBottom = f;
                View view = changingAnimator.mTarget;
                if (view != null) {
                    view.setBottom((int) f);
                }
            }
        };
        public static final FloatProperty SCROLLX_PROPERTY = new FloatProperty<ChangingAnimator>("scrollX") { // from class: miuix.transition.LayoutTransition.ChangingAnimator.5
            @Override // miuix.animation.property.FloatProperty
            public float getValue(ChangingAnimator changingAnimator) {
                return changingAnimator.mScrollX;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(ChangingAnimator changingAnimator, float f) {
                changingAnimator.mScrollX = f;
                View view = changingAnimator.mTarget;
                if (view != null) {
                    view.setScrollX((int) f);
                }
            }
        };
        public static final FloatProperty SCROLLY_PROPERTY = new FloatProperty<ChangingAnimator>("scrollY") { // from class: miuix.transition.LayoutTransition.ChangingAnimator.6
            @Override // miuix.animation.property.FloatProperty
            public float getValue(ChangingAnimator changingAnimator) {
                return changingAnimator.mScrollY;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(ChangingAnimator changingAnimator, float f) {
                changingAnimator.mScrollY = f;
                View view = changingAnimator.mTarget;
                if (view != null) {
                    view.setScrollY((int) f);
                }
            }
        };

        public ChangingAnimator() {
        }

        public void cancel() {
            IFolme iFolme = this.mFolmeImpl;
            if (iFolme != null) {
                iFolme.cancel();
            }
        }

        public void initFromView(View view) {
            this.mLeft = view.getLeft();
            this.mTop = view.getTop();
            this.mRight = view.getRight();
            this.mBottom = view.getBottom();
            this.mScrollX = view.getScrollX();
            this.mScrollY = view.getScrollY();
        }

        public void start() {
            IFolme iFolme = this.mFolmeImpl;
            if (iFolme != null) {
                iFolme.to(this.mAnimConfig);
            }
        }
    }

    public static final class CleanupCallback implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
        public final Map layoutChangeListenerMap;
        public final ViewGroup parent;

        public CleanupCallback(Map map, ViewGroup viewGroup) {
            this.layoutChangeListenerMap = map;
            this.parent = viewGroup;
        }

        public final void cleanup() {
            this.parent.getViewTreeObserver().removeOnPreDrawListener(this);
            this.parent.removeOnAttachStateChangeListener(this);
            if (this.layoutChangeListenerMap.size() > 0) {
                for (View view : this.layoutChangeListenerMap.keySet()) {
                    view.removeOnLayoutChangeListener((View.OnLayoutChangeListener) this.layoutChangeListenerMap.get(view));
                }
                this.layoutChangeListenerMap.clear();
            }
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            cleanup();
            return true;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            cleanup();
        }
    }

    public static void setLeftTopRightBottom(View view, int i, int i2, int i3, int i4) {
        view.setLeftTopRightBottom(i, i2, i3, i4);
    }

    @Override // android.animation.LayoutTransition
    public void addChild(ViewGroup viewGroup, View view) {
        addChild(viewGroup, view, true);
    }

    public final void addChild(ViewGroup viewGroup, View view, boolean z) {
        if (viewGroup.getWindowVisibility() != 0) {
            return;
        }
        if ((this.mTransitionTypes & 1) == 1) {
            cancel(3);
        }
        if (z && (this.mTransitionTypes & 4) == 4) {
            cancel(0);
            cancel(4);
        }
        if (hasListeners() && (this.mTransitionTypes & 1) == 1) {
            Iterator it = ((ArrayList) this.mListeners.clone()).iterator();
            while (it.hasNext()) {
                ((android.animation.LayoutTransition.TransitionListener) it.next()).startTransition(this, viewGroup, view, 2);
            }
        }
        if (z && (this.mTransitionTypes & 4) == 4) {
            runChangeTransition(viewGroup, view, 2);
        }
        if ((this.mTransitionTypes & 1) == 1) {
            runAppearingTransition(viewGroup, view);
        }
    }

    @Override // android.animation.LayoutTransition
    public void addTransitionListener(android.animation.LayoutTransition.TransitionListener transitionListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(transitionListener);
    }

    public void cancel() {
        if (this.currentChangingAnimations.size() > 0) {
            Iterator it = ((LinkedHashMap) this.currentChangingAnimations.clone()).values().iterator();
            while (it.hasNext()) {
                ((ChangingAnimator) it.next()).cancel();
            }
            this.currentChangingAnimations.clear();
        }
    }

    public void cancel(int i) {
        if ((i == 0 || i == 1 || i == 4) && this.currentChangingAnimations.size() > 0) {
            Iterator it = ((LinkedHashMap) this.currentChangingAnimations.clone()).values().iterator();
            while (it.hasNext()) {
                ((ChangingAnimator) it.next()).cancel();
            }
            this.currentChangingAnimations.clear();
        }
    }

    @Override // android.animation.LayoutTransition
    public void disableTransitionType(int i) {
        if (i == 0) {
            this.mTransitionTypes &= -5;
            return;
        }
        if (i == 1) {
            this.mTransitionTypes &= -9;
            return;
        }
        if (i == 2) {
            this.mTransitionTypes &= -2;
        } else if (i == 3) {
            this.mTransitionTypes &= -3;
        } else {
            if (i != 4) {
                return;
            }
            this.mTransitionTypes &= -17;
        }
    }

    @Override // android.animation.LayoutTransition
    public void enableTransitionType(int i) {
        if (i == 0) {
            this.mTransitionTypes |= 4;
            return;
        }
        if (i == 1) {
            this.mTransitionTypes |= 8;
            return;
        }
        if (i == 2) {
            this.mTransitionTypes |= 1;
        } else if (i == 3) {
            this.mTransitionTypes |= 2;
        } else {
            if (i != 4) {
                return;
            }
            this.mTransitionTypes |= 16;
        }
    }

    public void endChangingAnimations() {
    }

    public EaseManager.EaseStyle getAnimEase(int i) {
        if (i == 0) {
            return this.mChangingAppearingEase;
        }
        if (i == 1) {
            return this.mChangingDisappearingEase;
        }
        if (i == 2) {
            return this.mAppearingEase;
        }
        if (i == 3) {
            return this.mDisappearingEase;
        }
        if (i != 4) {
            return null;
        }
        return this.mChangingEase;
    }

    @Override // android.animation.LayoutTransition
    public Animator getAnimator(int i) {
        return null;
    }

    @Override // android.animation.LayoutTransition
    public long getDuration(int i) {
        return 0L;
    }

    @Override // android.animation.LayoutTransition
    public TimeInterpolator getInterpolator(int i) {
        return null;
    }

    @Override // android.animation.LayoutTransition
    public long getStagger(int i) {
        return 0L;
    }

    @Override // android.animation.LayoutTransition
    public long getStartDelay(int i) {
        return 0L;
    }

    @Override // android.animation.LayoutTransition
    public List getTransitionListeners() {
        return this.mListeners;
    }

    public final boolean hasListeners() {
        ArrayList arrayList = this.mListeners;
        return arrayList != null && arrayList.size() > 0;
    }

    @Override // android.animation.LayoutTransition
    public void hideChild(ViewGroup viewGroup, View view) {
        removeChild(viewGroup, view, true);
    }

    @Override // android.animation.LayoutTransition
    public void hideChild(ViewGroup viewGroup, View view, int i) {
        removeChild(viewGroup, view, i == 8);
    }

    @Override // android.animation.LayoutTransition
    public boolean isChangingLayout() {
        return this.currentChangingAnimations.size() > 0;
    }

    @Override // android.animation.LayoutTransition
    public boolean isRunning() {
        return this.currentChangingAnimations.size() > 0 || this.currentAppearingViews.size() > 0 || this.currentDisappearingViews.size() > 0;
    }

    @Override // android.animation.LayoutTransition
    public boolean isTransitionTypeEnabled(int i) {
        if (i == 0) {
            return (this.mTransitionTypes & 4) == 4;
        }
        if (i == 1) {
            return (this.mTransitionTypes & 8) == 8;
        }
        if (i == 2) {
            return (this.mTransitionTypes & 1) == 1;
        }
        if (i != 3) {
            return i == 4 && (this.mTransitionTypes & 16) == 16;
        }
        return (this.mTransitionTypes & 2) == 2;
    }

    public void layoutChange(ViewGroup viewGroup) {
        if (viewGroup.getWindowVisibility() == 0 && (this.mTransitionTypes & 16) == 16 && !isRunning()) {
            runChangeTransition(viewGroup, null, 4);
        }
    }

    @Override // android.animation.LayoutTransition
    public void removeChild(ViewGroup viewGroup, View view) {
        removeChild(viewGroup, view, true);
    }

    public final void removeChild(ViewGroup viewGroup, View view, boolean z) {
        if (viewGroup.getWindowVisibility() != 0) {
            return;
        }
        if ((this.mTransitionTypes & 2) == 2) {
            cancel(2);
        }
        if (z && (this.mTransitionTypes & 8) == 8) {
            cancel(1);
            cancel(4);
        }
        if (hasListeners() && (this.mTransitionTypes & 2) == 2) {
            Iterator it = ((ArrayList) this.mListeners.clone()).iterator();
            while (it.hasNext()) {
                ((android.animation.LayoutTransition.TransitionListener) it.next()).startTransition(this, viewGroup, view, 3);
            }
        }
        if (z && (this.mTransitionTypes & 8) == 8) {
            runChangeTransition(viewGroup, view, 3);
        }
        if ((this.mTransitionTypes & 2) == 2) {
            runDisappearingTransition(viewGroup, view);
        }
    }

    @Override // android.animation.LayoutTransition
    public void removeTransitionListener(android.animation.LayoutTransition.TransitionListener transitionListener) {
        ArrayList arrayList = this.mListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(transitionListener);
    }

    public final void runAppearingTransition(final ViewGroup viewGroup, final View view) {
        if (!this.currentDisappearingViews.contains(view)) {
            Folme.use(view).setTo(ViewProperty.ALPHA, Float.valueOf(0.0f));
        }
        final AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(getAnimEase(2));
        animConfig.addListeners(new TransitionListener() { // from class: miuix.transition.LayoutTransition.2
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                animConfig.removeListeners(this);
                LayoutTransition.this.currentAppearingViews.remove(view);
                if (LayoutTransition.this.hasListeners()) {
                    Iterator it = ((ArrayList) LayoutTransition.this.mListeners.clone()).iterator();
                    while (it.hasNext()) {
                        ((android.animation.LayoutTransition.TransitionListener) it.next()).endTransition(LayoutTransition.this, viewGroup, view, 2);
                    }
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                animConfig.removeListeners(this);
                LayoutTransition.this.currentAppearingViews.remove(view);
                if (LayoutTransition.this.hasListeners()) {
                    Iterator it = ((ArrayList) LayoutTransition.this.mListeners.clone()).iterator();
                    while (it.hasNext()) {
                        ((android.animation.LayoutTransition.TransitionListener) it.next()).endTransition(LayoutTransition.this, viewGroup, view, 2);
                    }
                }
            }
        });
        Folme.use(view).to(ViewProperty.ALPHA, Float.valueOf(1.0f), animConfig);
        this.currentAppearingViews.add(view);
    }

    public final void runChangeTransition(ViewGroup viewGroup, View view, int i) {
        ViewGroup viewGroup2;
        ViewTreeObserver viewTreeObserver = viewGroup.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt != view) {
                    setupChangeAnimation(viewGroup, i, null, 0L, childAt);
                }
            }
            if (this.mAnimateParentHierarchy) {
                ViewGroup viewGroup3 = viewGroup;
                while (viewGroup3 != null) {
                    ViewParent parent = viewGroup3.getParent();
                    if (parent instanceof ViewGroup) {
                        viewGroup2 = (ViewGroup) parent;
                        setupChangeAnimation(viewGroup2, i, null, 0L, viewGroup3);
                    } else {
                        viewGroup2 = null;
                    }
                    viewGroup3 = viewGroup2;
                }
            }
            CleanupCallback cleanupCallback = new CleanupCallback(this.layoutChangeListenerMap, viewGroup);
            viewTreeObserver.addOnPreDrawListener(cleanupCallback);
            viewGroup.addOnAttachStateChangeListener(cleanupCallback);
        }
    }

    public final void runDisappearingTransition(final ViewGroup viewGroup, final View view) {
        final AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(getAnimEase(3));
        animConfig.addListeners(new TransitionListener() { // from class: miuix.transition.LayoutTransition.3
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                animConfig.removeListeners(this);
                LayoutTransition.this.currentDisappearingViews.remove(view);
                if (LayoutTransition.this.hasListeners()) {
                    Iterator it = ((ArrayList) LayoutTransition.this.mListeners.clone()).iterator();
                    while (it.hasNext()) {
                        ((android.animation.LayoutTransition.TransitionListener) it.next()).endTransition(LayoutTransition.this, viewGroup, view, 3);
                    }
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                animConfig.removeListeners(this);
                LayoutTransition.this.currentDisappearingViews.remove(view);
                if (LayoutTransition.this.hasListeners()) {
                    Iterator it = ((ArrayList) LayoutTransition.this.mListeners.clone()).iterator();
                    while (it.hasNext()) {
                        ((android.animation.LayoutTransition.TransitionListener) it.next()).endTransition(LayoutTransition.this, viewGroup, view, 3);
                    }
                }
            }
        });
        Folme.use(view).to(ViewProperty.ALPHA, Float.valueOf(0.0f), animConfig);
        this.currentDisappearingViews.add(view);
    }

    @Override // android.animation.LayoutTransition
    public void setAnimateParentHierarchy(boolean z) {
        this.mAnimateParentHierarchy = z;
    }

    @Override // android.animation.LayoutTransition
    public void setAnimator(int i, Animator animator) {
    }

    @Override // android.animation.LayoutTransition
    public void setDuration(int i, long j) {
    }

    @Override // android.animation.LayoutTransition
    public void setDuration(long j) {
    }

    @Override // android.animation.LayoutTransition
    public void setInterpolator(int i, TimeInterpolator timeInterpolator) {
    }

    @Override // android.animation.LayoutTransition
    public void setStagger(int i, long j) {
    }

    @Override // android.animation.LayoutTransition
    public void setStartDelay(int i, long j) {
    }

    public final void setupChangeAnimation(final ViewGroup viewGroup, final int i, Animator animator, long j, final View view) {
        if (this.layoutChangeListenerMap.get(view) != null) {
            return;
        }
        if (view.getWidth() == 0 && view.getHeight() == 0) {
            return;
        }
        final int left = view.getLeft();
        final int top = view.getTop();
        final int right = view.getRight();
        final int bottom = view.getBottom();
        final int scrollX = view.getScrollX();
        final int scrollY = view.getScrollY();
        View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: miuix.transition.LayoutTransition.1
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                final int i10;
                int left2 = view.getLeft();
                int top2 = view.getTop();
                int right2 = view.getRight();
                int bottom2 = view.getBottom();
                int scrollX2 = view.getScrollX();
                int scrollY2 = view.getScrollY();
                if (left == left2 && top == top2 && right == right2 && bottom == bottom2 && scrollX == scrollX2 && scrollY == scrollY2) {
                    return;
                }
                ChangingAnimator changingAnimator = (ChangingAnimator) LayoutTransition.this.currentChangingAnimations.get(view);
                if (changingAnimator == null) {
                    changingAnimator = new ChangingAnimator();
                    LayoutTransition.setLeftTopRightBottom(view, left, top, right, bottom);
                    View view3 = view;
                    changingAnimator.mTarget = view3;
                    changingAnimator.initFromView(view3);
                    int i11 = i;
                    if (i11 == 2) {
                        i10 = 0;
                    } else {
                        i10 = i11 == 3 ? 1 : 4;
                    }
                    final AnimConfig ease = new AnimConfig().setEase(LayoutTransition.this.getAnimEase(i10));
                    ease.addListeners(new TransitionListener() { // from class: miuix.transition.LayoutTransition.1.1
                        @Override // miuix.animation.listener.TransitionListener
                        public void onBegin(Object obj) {
                            if (LayoutTransition.this.hasListeners()) {
                                for (android.animation.LayoutTransition.TransitionListener transitionListener : (ArrayList) LayoutTransition.this.mListeners.clone()) {
                                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                    transitionListener.startTransition(LayoutTransition.this, viewGroup, view, i10);
                                }
                            }
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onCancel(Object obj) {
                            ease.removeListeners(this);
                            LayoutTransition.this.layoutChangeListenerMap.remove(view);
                            if (LayoutTransition.this.hasListeners()) {
                                for (android.animation.LayoutTransition.TransitionListener transitionListener : (ArrayList) LayoutTransition.this.mListeners.clone()) {
                                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                    LayoutTransition layoutTransition = LayoutTransition.this;
                                    ViewGroup viewGroup2 = viewGroup;
                                    View view4 = view;
                                    int i12 = i;
                                    transitionListener.endTransition(layoutTransition, viewGroup2, view4, i12 == 2 ? 0 : i12 == 3 ? 1 : 4);
                                }
                            }
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onComplete(Object obj) {
                            ease.removeListeners(this);
                            LayoutTransition.this.currentChangingAnimations.remove(view);
                            if (LayoutTransition.this.hasListeners()) {
                                for (android.animation.LayoutTransition.TransitionListener transitionListener : (ArrayList) LayoutTransition.this.mListeners.clone()) {
                                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                    LayoutTransition layoutTransition = LayoutTransition.this;
                                    ViewGroup viewGroup2 = viewGroup;
                                    View view4 = view;
                                    int i12 = i;
                                    transitionListener.endTransition(layoutTransition, viewGroup2, view4, i12 == 2 ? 0 : i12 == 3 ? 1 : 4);
                                }
                            }
                        }
                    });
                    changingAnimator.mAnimConfig = ease;
                }
                IFolme iFolmeUse = Folme.use(changingAnimator);
                iFolmeUse.setup(new AnimState().add(ChangingAnimator.LEFT_PROPERTY, left2).add(ChangingAnimator.TOP_PROPERTY, top2).add(ChangingAnimator.RIGHT_PROPERTY, right2).add(ChangingAnimator.BOTTOM_PROPERTY, bottom2).add(ChangingAnimator.SCROLLX_PROPERTY, scrollX2).add(ChangingAnimator.SCROLLY_PROPERTY, scrollY2));
                changingAnimator.mFolmeImpl = iFolmeUse;
                LayoutTransition.this.currentChangingAnimations.put(view, changingAnimator);
                LayoutTransition.this.startChangingAnimations();
                view.removeOnLayoutChangeListener(this);
                LayoutTransition.this.layoutChangeListenerMap.remove(view);
            }
        };
        view.addOnLayoutChangeListener(onLayoutChangeListener);
        this.layoutChangeListenerMap.put(view, onLayoutChangeListener);
    }

    @Override // android.animation.LayoutTransition
    public void showChild(ViewGroup viewGroup, View view) {
        addChild(viewGroup, view, true);
    }

    @Override // android.animation.LayoutTransition
    public void showChild(ViewGroup viewGroup, View view, int i) {
        addChild(viewGroup, view, i == 8);
    }

    public void startChangingAnimations() {
        Iterator it = ((LinkedHashMap) this.currentChangingAnimations.clone()).values().iterator();
        while (it.hasNext()) {
            ((ChangingAnimator) it.next()).start();
        }
    }
}
