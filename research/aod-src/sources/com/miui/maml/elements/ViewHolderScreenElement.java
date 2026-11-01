package com.miui.maml.elements;

import android.graphics.Canvas;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.miui.maml.RendererController;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.animation.BaseAnimation;
import com.miui.maml.util.MamlLog;
import com.miui.maml.util.MamlViewManager;
import java.util.ArrayList;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ViewHolderScreenElement extends ElementGroupRC {
    protected static final int LAYER_BOTTOM = 2;
    protected static final int LAYER_TOP = 1;
    private static final String LOG_TAG = "MAML ViewHolderScreenElement";
    private boolean mHardware;
    protected int mLayer;
    private ViewGroup.LayoutParams mLayoutParams;
    protected boolean mUpdatePosition;
    protected boolean mUpdateSize;
    protected boolean mUpdateTranslation;
    protected boolean mViewAdded;

    public class ProxyListener extends RendererController.EmptyListener {
        private ProxyListener() {
        }

        @Override // com.miui.maml.RendererController.EmptyListener, com.miui.maml.RendererController.IRenderable
        public void doRender() {
            ViewHolderScreenElement.this.getView().postInvalidate();
        }

        @Override // com.miui.maml.RendererController.ISelfUpdateRenderable
        public void forceUpdate() {
            ViewHolderScreenElement.this.mRoot.getRendererController().forceUpdate();
        }

        @Override // com.miui.maml.RendererController.EmptyListener, com.miui.maml.RendererController.Listener
        public void tick(long j) {
            ViewHolderScreenElement.this.doTickChildren(j);
        }

        @Override // com.miui.maml.RendererController.ISelfUpdateRenderable
        public void triggerUpdate() {
            ViewHolderScreenElement.this.mRoot.getRendererController().triggerUpdate();
        }
    }

    public ViewHolderScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mLayer = 2;
        String attr = getAttr(element, "layerType");
        this.mHardware = Boolean.parseBoolean(getAttr(element, "hardware"));
        this.mUpdatePosition = getAttrAsBoolean(getAttr(element, "updatePosition"), true);
        this.mUpdateSize = getAttrAsBoolean(getAttr(element, "updateSize"), true);
        this.mUpdateTranslation = getAttrAsBoolean(getAttr(element, "updateTranslation"), true);
        if (TextUtils.isEmpty(attr) || "top".equals(attr)) {
            this.mLayer = 1;
        } else if ("bottom".equals(attr)) {
            this.mLayer = 2;
        }
        this.mLayoutParams = getLayoutParam();
    }

    private final void finishView() {
        View view;
        ViewParent parent;
        if (!this.mViewAdded || (view = getView()) == null || (parent = view.getParent()) == null || !(parent instanceof ViewGroup)) {
            return;
        }
        ((ViewGroup) parent).removeView(view);
        this.mViewAdded = false;
        onViewRemoved(view);
    }

    private static boolean getAttrAsBoolean(String str, boolean z) {
        return TextUtils.isEmpty(str) ? z : Boolean.parseBoolean(str);
    }

    private final void initView() {
        MamlViewManager viewManager;
        if (this.mViewAdded || (viewManager = this.mRoot.getViewManager()) == null) {
            return;
        }
        View view = getView();
        onUpdateView(view);
        if (this.mLayer == 2) {
            viewManager.addView(view, 0, this.mLayoutParams);
        } else {
            viewManager.addView(view, this.mLayoutParams);
        }
        if (this.mHardware) {
            view.setLayerType(2, null);
        }
        this.mViewAdded = true;
        onViewAdded(view);
    }

    private boolean updateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        boolean z;
        int width = (int) getWidth();
        if (layoutParams.width == width || width == 0) {
            z = false;
        } else {
            layoutParams.width = width;
            z = true;
        }
        int height = (int) getHeight();
        if (layoutParams.height == height || height == 0) {
            return z;
        }
        layoutParams.height = height;
        return true;
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void doTick(long j) {
        if (this.mController == null) {
            super.doTick(j);
            getView().postInvalidate();
        } else {
            doTickSelf(j);
        }
        updateView();
    }

    public void doTickSelf(long j) {
        ArrayList<BaseAnimation> arrayList = this.mAnimations;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mAnimations.get(i).tick(j);
            }
        }
        int iEvaluateAlpha = evaluateAlpha();
        this.mAlpha = iEvaluateAlpha;
        this.mAlpha = iEvaluateAlpha >= 0 ? iEvaluateAlpha : 0;
        FunctionElement functionElement = this.mTickListener;
        if (functionElement != null) {
            functionElement.perform();
        }
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        finishView();
    }

    public ViewGroup.LayoutParams getLayoutParam() {
        return new FrameLayout.LayoutParams(-1, -1);
    }

    public abstract View getView();

    @Override // com.miui.maml.elements.ElementGroupRC, com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        if (this.mRoot.getViewManager() != null) {
            initView();
        } else {
            MamlLog.e(LOG_TAG, "ViewManager must be set before init");
        }
    }

    public boolean isViewAdded() {
        return this.mViewAdded;
    }

    @Override // com.miui.maml.elements.ElementGroupRC
    public void onControllerCreated(RendererController rendererController) {
        rendererController.setListener(new ProxyListener());
    }

    public void onUpdateView(View view) {
        if (this.mUpdatePosition) {
            view.setX(getAbsoluteLeft());
            view.setY(getAbsoluteTop());
        }
        if (this.mUpdateTranslation) {
            view.setPivotX(getPivotX());
            view.setPivotY(getPivotY());
            view.setRotation(getRotation());
            view.setRotationX(getRotationX());
            view.setRotationY(getRotationY());
            view.setAlpha(getAlpha() / 255.0f);
            view.setScaleX(getScaleX());
            view.setScaleY(getScaleY());
        }
        if (this.mUpdateSize && updateLayoutParams(this.mLayoutParams)) {
            view.setLayoutParams(this.mLayoutParams);
        }
    }

    public void onViewAdded(View view) {
    }

    public void onViewRemoved(View view) {
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void onVisibilityChange(final boolean z) {
        postInMainThread(new Runnable() { // from class: com.miui.maml.elements.ViewHolderScreenElement.2
            @Override // java.lang.Runnable
            public void run() {
                ViewHolderScreenElement.this.getView().setVisibility(z ? 0 : 4);
            }
        });
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void render(Canvas canvas) {
    }

    public void setHardwareLayer(final boolean z) {
        postInMainThread(new Runnable() { // from class: com.miui.maml.elements.ViewHolderScreenElement.1
            @Override // java.lang.Runnable
            public void run() {
                ViewHolderScreenElement.this.getView().setLayerType(z ? 2 : 0, null);
            }
        });
    }

    public void updateView() {
        if ((this.mUpdatePosition || this.mUpdateTranslation || this.mUpdateSize) && this.mViewAdded) {
            onUpdateView(getView());
        }
    }
}
