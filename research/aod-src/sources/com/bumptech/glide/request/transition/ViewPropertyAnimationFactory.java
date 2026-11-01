package com.bumptech.glide.request.transition;

import com.bumptech.glide.load.DataSource;

/* JADX INFO: loaded from: classes.dex */
public class ViewPropertyAnimationFactory<R> implements TransitionFactory<R> {
    public ViewPropertyTransition animation;
    public final ViewPropertyTransition.Animator animator;

    @Override // com.bumptech.glide.request.transition.TransitionFactory
    public Transition build(DataSource dataSource, boolean z) {
        if (dataSource == DataSource.MEMORY_CACHE || !z) {
            return NoTransition.get();
        }
        if (this.animation == null) {
            this.animation = new ViewPropertyTransition(this.animator);
        }
        return this.animation;
    }
}
