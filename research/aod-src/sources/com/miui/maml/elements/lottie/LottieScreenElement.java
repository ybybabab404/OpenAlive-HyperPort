package com.miui.maml.elements.lottie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.text.TextUtils;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieListener;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.elements.AnimatedScreenElement;
import com.miui.maml.util.Utils;
import com.xiaomi.onetrack.util.a;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public class LottieScreenElement extends AnimatedScreenElement {
    public static final String LOG_TAG = "LottieScreenElement";
    public static final String TAG_NAME = "Lottie";
    private boolean mAutoPlay;
    private LottieDrawable mDrawable;
    private IndexedVariable mDurationProperty;
    private int mLoop;
    private String mLottiePath;
    private IndexedVariable mProgressProperty;

    /* JADX INFO: renamed from: $r8$lambda$GQUuprjFGYVNPvz-UY8fFiqLSxM, reason: not valid java name */
    public static /* synthetic */ void m955$r8$lambda$GQUuprjFGYVNPvzUY8fFiqLSxM(LottieScreenElement lottieScreenElement, ValueAnimator valueAnimator) {
        IndexedVariable indexedVariable = lottieScreenElement.mProgressProperty;
        if (indexedVariable != null) {
            indexedVariable.set(lottieScreenElement.mDrawable.getProgress());
        }
        lottieScreenElement.performAction("update");
    }

    public static /* synthetic */ void $r8$lambda$lwdOaF0g7bQlwztz2fzivR3FtYo(LottieScreenElement lottieScreenElement, LottieComposition lottieComposition) {
        lottieScreenElement.mDrawable.setComposition(lottieComposition);
        int i = lottieScreenElement.mLoop;
        if (i < 0) {
            lottieScreenElement.mDrawable.setRepeatCount(-1);
        } else {
            lottieScreenElement.mDrawable.setRepeatCount(i);
        }
        IndexedVariable indexedVariable = lottieScreenElement.mDurationProperty;
        if (indexedVariable != null) {
            indexedVariable.set((int) (lottieScreenElement.mDrawable.getMaxFrame() - lottieScreenElement.mDrawable.getMinFrame()));
        }
        if (lottieScreenElement.mAutoPlay) {
            lottieScreenElement.mDrawable.playAnimation();
        }
    }

    public LottieScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mLottiePath = element.getAttribute("src");
        this.mLoop = Utils.getAttrAsInt(element, "loop", -1);
        this.mAutoPlay = a.i.equals(element.getAttribute("autoplay"));
        load();
    }

    private void load() {
        String str;
        if (!TextUtils.isEmpty(this.mName)) {
            this.mProgressProperty = new IndexedVariable(this.mName + ".progress", getVariables(), true);
            this.mDurationProperty = new IndexedVariable(this.mName + ".duration", getVariables(), true);
        }
        if (this.mLottiePath != null) {
            this.mDrawable = new LottieDrawable();
            if (this.mRoot.getContext().mResourceManager == null || this.mRoot.getContext().mResourceManager.getResourceLoader() == null) {
                str = null;
            } else {
                str = this.mRoot.getContext().mResourceManager.getResourceLoader().getID() + this.mLottiePath;
            }
            LottieCompositionFactory.fromJsonInputStream(getRoot().getContext().mResourceManager.getInputStream(this.mLottiePath), str).addListener(new LottieListener() { // from class: com.miui.maml.elements.lottie.LottieScreenElement$$ExternalSyntheticLambda0
                @Override // com.airbnb.lottie.LottieListener
                public final void onResult(Object obj) {
                    LottieScreenElement.$r8$lambda$lwdOaF0g7bQlwztz2fzivR3FtYo(this.f$0, (LottieComposition) obj);
                }
            });
            this.mDrawable.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.maml.elements.lottie.LottieScreenElement$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LottieScreenElement.m955$r8$lambda$GQUuprjFGYVNPvzUY8fFiqLSxM(this.f$0, valueAnimator);
                }
            });
            this.mDrawable.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.miui.maml.elements.lottie.LottieScreenElement.1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (LottieScreenElement.this.mProgressProperty != null) {
                        LottieScreenElement.this.mProgressProperty.set(1.0d);
                    }
                    LottieScreenElement.this.performAction("complete");
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                    if (LottieScreenElement.this.mProgressProperty != null) {
                        LottieScreenElement.this.mProgressProperty.set(1.0d);
                    }
                    LottieScreenElement.this.performAction("loopComplete");
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }
            });
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void doRender(Canvas canvas) {
        if (this.mDrawable != null) {
            float x = getX();
            float y = getY();
            float width = getWidth();
            float height = getHeight();
            float left = getLeft(0.0f, width);
            float top = getTop(0.0f, height);
            float f = left <= 0.0f ? x + left : left;
            float f2 = top <= 0.0f ? y + top : top;
            if (width <= 0.0f) {
                width = 0.0f;
            }
            float f3 = width + f;
            if (height <= 0.0f) {
                height = 0.0f;
            }
            canvas.translate(left, top);
            this.mDrawable.setBounds((int) f, (int) f2, (int) f3, (int) (height + f2));
            this.mDrawable.draw(canvas);
        }
    }

    public void goToAndPlay(int i) {
        LottieDrawable lottieDrawable = this.mDrawable;
        if (lottieDrawable != null) {
            lottieDrawable.setFrame(i);
            if (this.mDrawable.isAnimating()) {
                return;
            }
            playAnimation();
        }
    }

    public void goToAndStop(int i) {
        LottieDrawable lottieDrawable = this.mDrawable;
        if (lottieDrawable != null) {
            lottieDrawable.setFrame(i);
            if (this.mDrawable.isAnimating()) {
                stopAnimation();
            }
        }
    }

    public void pauseAnimation() {
        LottieDrawable lottieDrawable = this.mDrawable;
        if (lottieDrawable != null) {
            lottieDrawable.pauseAnimation();
        }
    }

    public void playAnimation() {
        LottieDrawable lottieDrawable = this.mDrawable;
        if (lottieDrawable != null) {
            lottieDrawable.playAnimation();
        }
    }

    public void playSegments(int i, int i2) {
        LottieDrawable lottieDrawable = this.mDrawable;
        if (lottieDrawable != null) {
            lottieDrawable.setMinAndMaxFrame(i, i2);
            IndexedVariable indexedVariable = this.mDurationProperty;
            if (indexedVariable != null) {
                indexedVariable.set((int) (this.mDrawable.getMaxFrame() - this.mDrawable.getMinFrame()));
            }
        }
    }

    public void resumeAnimation() {
        LottieDrawable lottieDrawable = this.mDrawable;
        if (lottieDrawable != null) {
            lottieDrawable.resumeAnimation();
        }
    }

    public void setLoopCount(int i) {
        LottieDrawable lottieDrawable = this.mDrawable;
        if (lottieDrawable != null) {
            lottieDrawable.setRepeatCount(i);
        }
    }

    public void setSpeed(float f) {
        LottieDrawable lottieDrawable = this.mDrawable;
        if (lottieDrawable != null) {
            lottieDrawable.setSpeed(f);
        }
    }

    public void stopAnimation() {
        LottieDrawable lottieDrawable = this.mDrawable;
        if (lottieDrawable != null) {
            lottieDrawable.stop();
        }
    }
}
