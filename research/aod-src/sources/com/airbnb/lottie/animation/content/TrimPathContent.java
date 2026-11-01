package com.airbnb.lottie.animation.content;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TrimPathContent implements Content, BaseKeyframeAnimation.AnimationListener {
    public final BaseKeyframeAnimation endAnimation;
    public final boolean hidden;
    public final List listeners = new ArrayList();
    public final String name;
    public final BaseKeyframeAnimation offsetAnimation;
    public final BaseKeyframeAnimation startAnimation;
    public final ShapeTrimPath.Type type;

    public TrimPathContent(BaseLayer baseLayer, ShapeTrimPath shapeTrimPath) {
        this.name = shapeTrimPath.getName();
        this.hidden = shapeTrimPath.isHidden();
        this.type = shapeTrimPath.getType();
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation = shapeTrimPath.getStart().createAnimation();
        this.startAnimation = baseKeyframeAnimationCreateAnimation;
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation2 = shapeTrimPath.getEnd().createAnimation();
        this.endAnimation = baseKeyframeAnimationCreateAnimation2;
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation3 = shapeTrimPath.getOffset().createAnimation();
        this.offsetAnimation = baseKeyframeAnimationCreateAnimation3;
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation2);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation3);
        baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation2.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation3.addUpdateListener(this);
    }

    public void addListener(BaseKeyframeAnimation.AnimationListener animationListener) {
        this.listeners.add(animationListener);
    }

    public BaseKeyframeAnimation getEnd() {
        return this.endAnimation;
    }

    public BaseKeyframeAnimation getOffset() {
        return this.offsetAnimation;
    }

    public BaseKeyframeAnimation getStart() {
        return this.startAnimation;
    }

    public ShapeTrimPath.Type getType() {
        return this.type;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public void onValueChanged() {
        for (int i = 0; i < this.listeners.size(); i++) {
            ((BaseKeyframeAnimation.AnimationListener) this.listeners.get(i)).onValueChanged();
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public void setContents(List list, List list2) {
    }
}
