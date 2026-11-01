package com.miui.maml.elements;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.miui.aod.components.BaseStyleSelectPresenter;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.maml.CommandTriggers;
import com.miui.maml.FramerateTokenList;
import com.miui.maml.NotifierManager;
import com.miui.maml.RendererController;
import com.miui.maml.ScreenContext;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.StylesManager;
import com.miui.maml.animation.BaseAnimation;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import com.miui.maml.util.StyleHelper;
import com.miui.maml.util.Utils;
import com.xiaomi.onetrack.api.bf;
import com.xiaomi.onetrack.util.a;
import com.xiaomi.onetrack.util.ac;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ScreenElement {
    public static final String ACTUAL_H = "actual_h";
    public static final String ACTUAL_W = "actual_w";
    public static final String ACTUAL_X = "actual_x";
    public static final String ACTUAL_Y = "actual_y";
    private static final String LOG_TAG = "MAML ScreenElement";
    public static final String VISIBILITY = "visibility";
    public static final int VISIBILITY_FALSE = 0;
    public static final int VISIBILITY_TRUE = 1;
    private IndexedVariable mActualHeightVar;
    private IndexedVariable mActualWidthVar;
    protected Align mAlign;
    protected AlignV mAlignV;
    protected ArrayList<BaseAnimation> mAnimations;
    protected RendererController mAvailableController;
    protected String mCategory;
    private float mCurFramerate;
    private FramerateTokenList.FramerateToken mFramerateToken;
    protected boolean mHasName;
    protected String mName;
    protected ElementGroup mParent;
    protected boolean mResumed;
    protected ScreenElementRoot mRoot;
    protected StylesManager.Style mStyle;
    protected CommandTriggers mTriggers;
    private Expression mVisibilityExpression;
    private IndexedVariable mVisibilityVar;
    private boolean mInitShow = true;
    private boolean mShow = true;
    private boolean mIsVisible = true;

    /* JADX INFO: renamed from: com.miui.maml.elements.ScreenElement$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$elements$ScreenElement$Align;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$elements$ScreenElement$AlignV;

        static {
            int[] iArr = new int[Align.values().length];
            $SwitchMap$com$miui$maml$elements$ScreenElement$Align = iArr;
            try {
                iArr[Align.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ScreenElement$Align[Align.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[AlignV.values().length];
            $SwitchMap$com$miui$maml$elements$ScreenElement$AlignV = iArr2;
            try {
                iArr2[AlignV.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ScreenElement$AlignV[AlignV.BOTTOM.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum Align {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum AlignV {
        TOP,
        CENTER,
        BOTTOM
    }

    public ScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        this.mRoot = screenElementRoot;
        if (element != null && screenElementRoot != null) {
            this.mStyle = screenElementRoot.getStyle(element.getAttribute("style"));
        }
        load(element);
    }

    public static boolean isTagEnable(String[] strArr, String str) {
        if (strArr == null) {
            return TextUtils.isEmpty(str);
        }
        return Utils.arrayContains(strArr, str);
    }

    private void load(Element element) {
        if (element == null) {
            return;
        }
        this.mCategory = getAttr(element, BaseStyleSelectPresenter.KEY_CATE);
        String attr = getAttr(element, bf.a);
        this.mName = attr;
        boolean zIsEmpty = TextUtils.isEmpty(attr);
        this.mHasName = !zIsEmpty;
        if (!zIsEmpty) {
            String attr2 = getAttr(element, "namesSuffix");
            if (!TextUtils.isEmpty(attr2)) {
                this.mName += attr2;
            }
            if (!Boolean.parseBoolean(getAttr(element, "dontAddToMap"))) {
                getRoot().addElement(this.mName, new WeakReference(this));
            }
        }
        String attr3 = getAttr(element, "visibility");
        if (!TextUtils.isEmpty(attr3)) {
            if (attr3.equalsIgnoreCase("false")) {
                this.mInitShow = false;
            } else if (attr3.equalsIgnoreCase(a.i)) {
                this.mInitShow = true;
            } else {
                this.mVisibilityExpression = Expression.build(getVariables(), attr3);
            }
        }
        Align align = Align.LEFT;
        this.mAlign = align;
        String attr4 = getAttr(element, "align");
        if (TextUtils.isEmpty(attr4)) {
            attr4 = getAttr(element, "alignH");
        }
        if (attr4.equalsIgnoreCase("right")) {
            this.mAlign = Align.RIGHT;
        } else if (attr4.equalsIgnoreCase("left")) {
            this.mAlign = align;
        } else if (attr4.equalsIgnoreCase("center")) {
            this.mAlign = Align.CENTER;
        }
        AlignV alignV = AlignV.TOP;
        this.mAlignV = alignV;
        String attr5 = getAttr(element, "alignV");
        if (attr5.equalsIgnoreCase("bottom")) {
            this.mAlignV = AlignV.BOTTOM;
        } else if (attr5.equalsIgnoreCase("top")) {
            this.mAlignV = alignV;
        } else if (attr5.equalsIgnoreCase("center")) {
            this.mAlignV = AlignV.CENTER;
        }
        loadTriggers(element);
        loadAnimations(element);
    }

    private void loadAnimations(Element element) {
        Utils.traverseXmlElementChildren(element, null, new Utils.XmlTraverseListener() { // from class: com.miui.maml.elements.ScreenElement.1
            @Override // com.miui.maml.util.Utils.XmlTraverseListener
            public void onChild(Element element2) {
                BaseAnimation baseAnimationOnCreateAnimation;
                String nodeName = element2.getNodeName();
                if (!nodeName.endsWith("Animation") || (baseAnimationOnCreateAnimation = ScreenElement.this.onCreateAnimation(nodeName, element2)) == null) {
                    return;
                }
                ScreenElement screenElement = ScreenElement.this;
                if (screenElement.mAnimations == null) {
                    screenElement.mAnimations = new ArrayList<>();
                }
                ScreenElement.this.mAnimations.add(baseAnimationOnCreateAnimation);
            }
        });
    }

    private void setVisibilityVar(boolean z) {
        if (this.mHasName) {
            if (this.mVisibilityVar == null) {
                this.mVisibilityVar = new IndexedVariable(this.mName + ".visibility", getContext().mVariables, true);
            }
            this.mVisibilityVar.set(z ? 1.0d : BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        }
    }

    public void acceptVisitor(ScreenElementVisitor screenElementVisitor) {
        screenElementVisitor.visit(this);
    }

    public FramerateTokenList.FramerateToken createToken(String str) {
        RendererController rendererController = getRendererController();
        if (rendererController == null) {
            return null;
        }
        return rendererController.createToken(str);
    }

    public final double descale(double d) {
        return d / ((double) this.mRoot.getScale());
    }

    public abstract void doRender(Canvas canvas);

    public void doTick(long j) {
        ArrayList<BaseAnimation> arrayList = this.mAnimations;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mAnimations.get(i).tick(j);
            }
        }
    }

    public final double evaluate(Expression expression) {
        return expression == null ? BaseStyleSelectView.COLLAPSE_SLIDE_VALUE : expression.evaluate();
    }

    public final String evaluateStr(Expression expression) {
        if (expression == null) {
            return null;
        }
        return expression.evaluateStr();
    }

    public ScreenElement findElement(String str) {
        String str2 = this.mName;
        if (str2 == null || !str2.equals(str)) {
            return null;
        }
        return this;
    }

    public void finish() {
        CommandTriggers commandTriggers = this.mTriggers;
        if (commandTriggers != null) {
            commandTriggers.finish();
        }
        ArrayList<BaseAnimation> arrayList = this.mAnimations;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mAnimations.get(i).finish();
            }
        }
    }

    public String getAttr(Element element, String str) {
        return StyleHelper.getAttr(element, str, this.mStyle);
    }

    public float getAttrAsFloat(Element element, String str, float f) {
        String attr = getAttr(element, str);
        if (!TextUtils.isEmpty(attr)) {
            try {
                return Float.parseFloat(attr);
            } catch (NumberFormatException unused) {
            }
        }
        return f;
    }

    public int getAttrAsInt(Element element, String str, int i) {
        String attr = getAttr(element, str);
        if (!TextUtils.isEmpty(attr)) {
            try {
                return Integer.parseInt(attr);
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    public long getAttrAsLong(Element element, String str, long j) {
        String attr = getAttr(element, str);
        if (!TextUtils.isEmpty(attr)) {
            try {
                return Long.parseLong(attr);
            } catch (NumberFormatException unused) {
            }
        }
        return j;
    }

    public ScreenContext getContext() {
        return this.mRoot.getContext();
    }

    public final float getFramerate() {
        FramerateTokenList.FramerateToken framerateToken = this.mFramerateToken;
        if (framerateToken == null) {
            return 0.0f;
        }
        return framerateToken.getFramerate();
    }

    public float getLeft(float f, float f2) {
        if (f2 > 0.0f) {
            int i = AnonymousClass2.$SwitchMap$com$miui$maml$elements$ScreenElement$Align[this.mAlign.ordinal()];
            if (i == 1) {
                return f - (f2 / 2.0f);
            }
            if (i == 2) {
                return f - f2;
            }
        }
        return f;
    }

    public String getName() {
        return this.mName;
    }

    public final NotifierManager getNotifierManager() {
        return NotifierManager.getInstance(getContext().mContext);
    }

    public ElementGroup getParent() {
        return this.mParent;
    }

    public RendererController getRendererController() {
        ElementGroup elementGroup = this.mParent;
        if (elementGroup != null) {
            return elementGroup.getRendererController();
        }
        return null;
    }

    public ScreenElementRoot getRoot() {
        return this.mRoot;
    }

    public float getTop(float f, float f2) {
        if (f2 > 0.0f) {
            int i = AnonymousClass2.$SwitchMap$com$miui$maml$elements$ScreenElement$AlignV[this.mAlignV.ordinal()];
            if (i == 1) {
                return f - (f2 / 2.0f);
            }
            if (i == 2) {
                return f - f2;
            }
        }
        return f;
    }

    public CommandTriggers getTriggers() {
        return this.mTriggers;
    }

    public final Variables getVariables() {
        return getContext().mVariables;
    }

    public void init() {
        this.mShow = this.mInitShow;
        FramerateTokenList.FramerateToken framerateToken = this.mFramerateToken;
        if (framerateToken != null) {
            removeToken(framerateToken);
        }
        this.mFramerateToken = null;
        CommandTriggers commandTriggers = this.mTriggers;
        if (commandTriggers != null) {
            commandTriggers.init();
        }
        setAnim(null);
        ArrayList<BaseAnimation> arrayList = this.mAnimations;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mAnimations.get(i).init();
            }
        }
        updateVisibility();
        setVisibilityVar(isVisible());
        performAction("init");
    }

    public boolean isVisible() {
        return this.mIsVisible;
    }

    public boolean isVisibleInner() {
        if (!this.mShow) {
            return false;
        }
        Expression expression = this.mVisibilityExpression;
        if (expression != null && expression.evaluate() <= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            return false;
        }
        ElementGroup elementGroup = this.mParent;
        return elementGroup == null || elementGroup.isVisible();
    }

    public void loadTriggers(Element element) {
        Element child = Utils.getChild(element, CommandTriggers.TAG_NAME);
        if (child != null) {
            this.mTriggers = new CommandTriggers(child, this);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public BaseAnimation onCreateAnimation(String str, Element element) {
        return null;
    }

    public boolean onHover(MotionEvent motionEvent) {
        return false;
    }

    public void onSetAnimBefore() {
    }

    public void onSetAnimEnable(BaseAnimation baseAnimation) {
    }

    public boolean onTouch(MotionEvent motionEvent) {
        return false;
    }

    public void onVisibilityChange(boolean z) {
        setVisibilityVar(z);
        if (z) {
            requestFramerate(this.mCurFramerate);
        } else {
            this.mCurFramerate = getFramerate();
            requestFramerate(0.0f);
        }
    }

    public void pause() {
        this.mResumed = false;
        CommandTriggers commandTriggers = this.mTriggers;
        if (commandTriggers != null) {
            commandTriggers.pause();
        }
        ArrayList<BaseAnimation> arrayList = this.mAnimations;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mAnimations.get(i).pause();
            }
        }
    }

    public final void pauseAnim() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        pauseAnim(jElapsedRealtime);
        doTick(jElapsedRealtime);
    }

    public void pauseAnim(long j) {
        ArrayList<BaseAnimation> arrayList = this.mAnimations;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mAnimations.get(i).pauseAnim(j);
            }
        }
    }

    public void performAction(String str) {
        CommandTriggers commandTriggers = this.mTriggers;
        if (commandTriggers == null || str == null) {
            return;
        }
        commandTriggers.onAction(str);
        requestUpdate();
    }

    public final void playAnim() {
        playAnim(0L, -1L, true, true);
    }

    public void playAnim(long j, long j2, long j3, boolean z, boolean z2) {
        ArrayList<BaseAnimation> arrayList = this.mAnimations;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mAnimations.get(i).playAnim(j, j2, j3, z, z2);
            }
        }
    }

    public final void playAnim(long j, long j2, boolean z, boolean z2) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        playAnim(jElapsedRealtime, j, j2, z, z2);
        doTick(jElapsedRealtime);
    }

    public final void postInMainThread(Runnable runnable) {
        getContext().getHandler().post(runnable);
    }

    public void postRunnable(Runnable runnable) {
        RendererController rendererController = this.mRoot.getRendererController();
        if (rendererController != null) {
            rendererController.postRunnable(runnable);
        }
    }

    public void postRunnableAtFrontOfQueue(Runnable runnable) {
        RendererController rendererController = this.mRoot.getRendererController();
        if (rendererController != null) {
            rendererController.postRunnableAtFrontOfQueue(runnable);
        }
    }

    public void removeToken(FramerateTokenList.FramerateToken framerateToken) {
        RendererController rendererController = getRendererController();
        if (rendererController != null) {
            rendererController.removeToken(framerateToken);
        }
    }

    public void render(Canvas canvas) {
        updateVisibility();
        if (isVisible()) {
            doRender(canvas);
        }
    }

    public final void requestFramerate(float f) {
        if (f < 0.0f) {
            return;
        }
        if (this.mFramerateToken == null) {
            if (f == 0.0f) {
                return;
            } else {
                this.mFramerateToken = createToken(toString());
            }
        }
        if (this.mFramerateToken != null) {
            float systemFrameRate = this.mRoot.getSystemFrameRate();
            FramerateTokenList.FramerateToken framerateToken = this.mFramerateToken;
            if (f > systemFrameRate) {
                f = systemFrameRate;
            }
            framerateToken.requestFramerate(f);
        }
    }

    public void requestUpdate() {
        RendererController rendererController = getRendererController();
        if (rendererController != null) {
            rendererController.forceUpdate();
        }
    }

    public final void reset() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        reset(jElapsedRealtime);
        doTick(jElapsedRealtime);
    }

    public void reset(long j) {
        ArrayList<BaseAnimation> arrayList = this.mAnimations;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mAnimations.get(i).reset(j);
            }
        }
    }

    public void resume() {
        this.mResumed = true;
        CommandTriggers commandTriggers = this.mTriggers;
        if (commandTriggers != null) {
            commandTriggers.resume();
        }
        ArrayList<BaseAnimation> arrayList = this.mAnimations;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mAnimations.get(i).resume();
            }
        }
    }

    public final void resumeAnim() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        resumeAnim(jElapsedRealtime);
        doTick(jElapsedRealtime);
    }

    public void resumeAnim(long j) {
        ArrayList<BaseAnimation> arrayList = this.mAnimations;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mAnimations.get(i).resumeAnim(j);
            }
        }
    }

    public final float scale(double d) {
        return (float) (d * ((double) this.mRoot.getScale()));
    }

    public void setActualHeight(double d) {
        if (this.mHasName) {
            if (this.mActualHeightVar == null) {
                this.mActualHeightVar = new IndexedVariable(this.mName + "." + ACTUAL_H, getVariables(), true);
            }
            this.mActualHeightVar.set(d);
        }
    }

    public void setActualWidth(double d) {
        if (this.mHasName) {
            if (this.mActualWidthVar == null) {
                this.mActualWidthVar = new IndexedVariable(this.mName + "." + ACTUAL_W, getVariables(), true);
            }
            this.mActualWidthVar.set(d);
        }
    }

    public void setAnim(String[] strArr) {
        if (this.mAnimations != null) {
            onSetAnimBefore();
            int size = this.mAnimations.size();
            for (int i = 0; i < size; i++) {
                BaseAnimation baseAnimation = this.mAnimations.get(i);
                boolean zIsTagEnable = isTagEnable(strArr, baseAnimation.getTag());
                baseAnimation.setDisable(!zIsTagEnable);
                if (zIsTagEnable) {
                    onSetAnimEnable(baseAnimation);
                }
            }
        }
    }

    public final void setAnimations(String str) {
        setAnim((TextUtils.isEmpty(str) || ".".equals(str)) ? null : str.split(ac.b));
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setName(String str) {
        getRoot().removeElement(this.mName);
        this.mName = str;
        getRoot().addElement(str, new WeakReference(this));
    }

    public void setParent(ElementGroup elementGroup) {
        this.mParent = elementGroup;
    }

    public void show(boolean z) {
        this.mShow = z;
        updateVisibility();
        requestUpdate();
    }

    public void showCategory(String str, boolean z) {
        String str2 = this.mCategory;
        if (str2 == null || !str2.equals(str)) {
            return;
        }
        show(z);
    }

    public void tick(long j) {
        updateVisibility();
        if (isVisible()) {
            onConfigurationChanged(getContext().mContext.getResources().getConfiguration());
            doTick(j);
        }
    }

    public void updateVisibility() {
        boolean zIsVisibleInner = isVisibleInner();
        if (this.mIsVisible != zIsVisibleInner) {
            this.mIsVisible = zIsVisibleInner;
            onVisibilityChange(zIsVisibleInner);
            if (zIsVisibleInner) {
                doTick(SystemClock.elapsedRealtime());
            }
        }
    }
}
