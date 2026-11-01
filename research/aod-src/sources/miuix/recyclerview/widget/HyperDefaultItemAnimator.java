package miuix.recyclerview.widget;

import android.view.View;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.maml.folme.AnimatedProperty;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.IFolme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes2.dex */
public class HyperDefaultItemAnimator extends MiuiBaseDefaultItemAnimator {
    public static final ViewProperty SCALE_X;
    public static final ViewProperty SCALE_Y;
    public static View.OnAttachStateChangeListener sAttachedListener = new View.OnAttachStateChangeListener() { // from class: miuix.recyclerview.widget.HyperDefaultItemAnimator.1
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
            MiuiBaseDefaultItemAnimator.resetAnimation(view);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
        }
    };

    static {
        float f = 0.001f;
        SCALE_X = new ViewProperty(AnimatedProperty.PROPERTY_NAME_SCALE_X, f) { // from class: miuix.recyclerview.widget.HyperDefaultItemAnimator.7
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getScaleX();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f2) {
                view.setScaleX(f2);
            }
        };
        SCALE_Y = new ViewProperty(AnimatedProperty.PROPERTY_NAME_SCALE_Y, f) { // from class: miuix.recyclerview.widget.HyperDefaultItemAnimator.8
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getScaleY();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f2) {
                view.setScaleY(f2);
            }
        };
    }

    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void animateAddImpl(final androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        AnimConfig animConfigAddListeners = new AnimConfig().setEase(FolmeEase.spring(0.95f, 0.38f)).addListeners(new TransitionListener() { // from class: miuix.recyclerview.widget.HyperDefaultItemAnimator.4
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                HyperDefaultItemAnimator.this.notifyAddStarting(viewHolder);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                HyperDefaultItemAnimator.this.notifyAddFinished(viewHolder);
            }
        });
        IStateStyle iStateStyleState = Folme.use(viewHolder.itemView).state();
        ViewProperty viewProperty = ViewProperty.ALPHA;
        Float fValueOf = Float.valueOf(1.0f);
        iStateStyleState.to(viewProperty, fValueOf, SCALE_X, fValueOf, SCALE_Y, fValueOf, animConfigAddListeners);
    }

    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void animateChangeImpl(MiuiBaseDefaultItemAnimator.ChangeInfo changeInfo) {
        final androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder = changeInfo.oldHolder;
        View view = viewHolder == null ? null : viewHolder.itemView;
        final androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder2 = changeInfo.newHolder;
        View view2 = viewHolder2 != null ? viewHolder2.itemView : null;
        if (view != null) {
            view.addOnAttachStateChangeListener(sAttachedListener);
            Folme.use(view).state().to(ViewProperty.TRANSLATION_X, Integer.valueOf(changeInfo.toX - changeInfo.fromX), ViewProperty.TRANSLATION_Y, Integer.valueOf(changeInfo.toY - changeInfo.fromY), new AnimConfig().setEase(FolmeEase.spring(0.95f, 0.38f)).addListeners(new TransitionListener() { // from class: miuix.recyclerview.widget.HyperDefaultItemAnimator.5
                @Override // miuix.animation.listener.TransitionListener
                public void onBegin(Object obj) {
                    HyperDefaultItemAnimator.this.notifyChangeStarting(viewHolder, true);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    HyperDefaultItemAnimator.this.notifyChangeFinished(viewHolder, true);
                }
            }));
        }
        if (view2 != null) {
            Folme.use(view2).state().to(ViewProperty.TRANSLATION_X, 0, ViewProperty.TRANSLATION_Y, 0, new AnimConfig().setEase(FolmeEase.spring(0.95f, 0.38f)).addListeners(new TransitionListener() { // from class: miuix.recyclerview.widget.HyperDefaultItemAnimator.6
                @Override // miuix.animation.listener.TransitionListener
                public void onBegin(Object obj) {
                    HyperDefaultItemAnimator.this.notifyChangeStarting(viewHolder2, false);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    HyperDefaultItemAnimator.this.notifyChangeFinished(viewHolder2, false);
                }
            }));
        }
    }

    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void animateMoveImpl(final MiuiBaseDefaultItemAnimator.MoveInfo moveInfo) {
        Folme.use(moveInfo.holder.itemView).to(new AnimState().add(ViewProperty.TRANSLATION_X, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE).add(ViewProperty.TRANSLATION_Y, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE), new AnimConfig().setEase(FolmeEase.spring(0.95f, 0.38f)).addListeners(new TransitionListener() { // from class: miuix.recyclerview.widget.HyperDefaultItemAnimator.3
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                HyperDefaultItemAnimator.this.notifyMoveStarting(moveInfo.holder);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                HyperDefaultItemAnimator.this.notifyMoveFinished(moveInfo.holder);
            }
        }));
    }

    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void animateRemoveImpl(final androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.addOnAttachStateChangeListener(sAttachedListener);
        AnimConfig animConfigAddListeners = new AnimConfig().setEase(FolmeEase.spring(0.95f, 0.38f)).addListeners(new TransitionListener() { // from class: miuix.recyclerview.widget.HyperDefaultItemAnimator.2
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                HyperDefaultItemAnimator.this.notifyRemoveStarting(viewHolder);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                HyperDefaultItemAnimator.this.notifyRemoveFinished(viewHolder);
            }
        });
        viewHolder.itemView.setTranslationZ(-1.0f);
        IStateStyle iStateStyleState = Folme.use(viewHolder.itemView).state();
        ViewProperty viewProperty = ViewProperty.ALPHA;
        Float fValueOf = Float.valueOf(0.0f);
        ViewProperty viewProperty2 = SCALE_X;
        Float fValueOf2 = Float.valueOf(0.4f);
        iStateStyleState.to(viewProperty, fValueOf, viewProperty2, fValueOf2, SCALE_Y, fValueOf2, animConfigAddListeners);
    }

    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void prepareAdd(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        resetAnimation(viewHolder);
        IFolme iFolmeUse = Folme.use(viewHolder.itemView);
        ViewProperty viewProperty = ViewProperty.ALPHA;
        Float fValueOf = Float.valueOf(0.0f);
        ViewProperty viewProperty2 = SCALE_X;
        Float fValueOf2 = Float.valueOf(0.4f);
        iFolmeUse.setTo(viewProperty, fValueOf, viewProperty2, fValueOf2, SCALE_Y, fValueOf2);
    }

    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void prepareChange(MiuiBaseDefaultItemAnimator.ChangeInfo changeInfo) {
        float translationX = changeInfo.oldHolder.itemView.getTranslationX();
        float translationY = changeInfo.oldHolder.itemView.getTranslationY();
        resetAnimation(changeInfo.oldHolder);
        int i = (int) ((changeInfo.toX - changeInfo.fromX) - translationX);
        int i2 = (int) ((changeInfo.toY - changeInfo.fromY) - translationY);
        IFolme iFolmeUse = Folme.use(changeInfo.oldHolder.itemView);
        ViewProperty viewProperty = ViewProperty.TRANSLATION_X;
        Float fValueOf = Float.valueOf(translationX);
        ViewProperty viewProperty2 = ViewProperty.TRANSLATION_Y;
        iFolmeUse.resetTo(viewProperty, fValueOf, viewProperty2, Float.valueOf(translationY));
        androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder = changeInfo.newHolder;
        if (viewHolder != null) {
            resetAnimation(viewHolder);
            Folme.use(changeInfo.newHolder.itemView).resetTo(viewProperty, Integer.valueOf(-i), viewProperty2, Integer.valueOf(-i2));
        }
    }

    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void prepareMove(MiuiBaseDefaultItemAnimator.MoveInfo moveInfo) {
        Folme.use(moveInfo.holder.itemView).setTo(ViewProperty.TRANSLATION_X, Integer.valueOf(moveInfo.fromX - moveInfo.toX), ViewProperty.TRANSLATION_Y, Integer.valueOf(moveInfo.fromY - moveInfo.toY));
    }

    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void resetAnimation(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        if (viewHolder != null) {
            View view = viewHolder.itemView;
            Folme.use(view).setTo(new AnimState().add(ViewProperty.TRANSLATION_X, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE).add(ViewProperty.TRANSLATION_Y, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE).add(ViewProperty.ALPHA, 1.0d).add(SCALE_X, 1.0d).add(SCALE_Y, 1.0d));
        }
    }
}
