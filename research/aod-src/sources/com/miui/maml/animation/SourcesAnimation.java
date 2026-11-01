package com.miui.maml.animation;

import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.maml.elements.ScreenElement;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public class SourcesAnimation extends PositionAnimation {
    public static final String TAG_NAME = "SourcesAnimation";
    private String mCurrentSrc;

    public static class Source extends BaseAnimation.AnimationItem {
        public static final String TAG_NAME = "Source";
        public String mSrc;

        public Source(BaseAnimation baseAnimation, Element element) {
            super(baseAnimation, element);
            this.mSrc = element.getAttribute("src");
        }
    }

    public SourcesAnimation(Element element, ScreenElement screenElement) {
        super(element, Source.TAG_NAME, screenElement);
    }

    public final String getSrc() {
        return this.mCurrentSrc;
    }

    @Override // com.miui.maml.animation.BaseAnimation
    public BaseAnimation.AnimationItem onCreateItem(BaseAnimation baseAnimation, Element element) {
        return new Source(baseAnimation, element);
    }

    @Override // com.miui.maml.animation.BaseAnimation
    public void onTick(BaseAnimation.AnimationItem animationItem, BaseAnimation.AnimationItem animationItem2, float f) {
        if (animationItem2 == null) {
            setCurValue(0, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
            setCurValue(1, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        } else {
            setCurValue(0, animationItem2.get(0));
            setCurValue(1, animationItem2.get(1));
            this.mCurrentSrc = ((Source) animationItem2).mSrc;
        }
    }
}
