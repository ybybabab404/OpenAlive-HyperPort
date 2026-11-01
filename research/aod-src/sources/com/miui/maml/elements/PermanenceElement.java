package com.miui.maml.elements;

import android.graphics.Canvas;
import android.text.TextUtils;
import com.miui.maml.CommandTrigger;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.VariableType;
import com.miui.maml.data.Variables;
import com.miui.maml.util.SharedPreferenceHelper;
import com.miui.maml.util.Utils;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public class PermanenceElement extends ScreenElement {
    private static final String OLD_VALUE = "old_value";
    public static final String TAG_NAME = "Permanence";
    private Expression mExpression;
    private boolean mInited;
    private double mOldValue;
    private IndexedVariable mOldVar;
    private double mThreshold;
    private CommandTrigger mTrigger;
    private VariableType mType;
    private IndexedVariable mVar;

    /* JADX INFO: renamed from: com.miui.maml.elements.PermanenceElement$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$data$VariableType;

        static {
            int[] iArr = new int[VariableType.values().length];
            $SwitchMap$com$miui$maml$data$VariableType = iArr;
            try {
                iArr[VariableType.STR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$data$VariableType[VariableType.NUM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public PermanenceElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        if (element != null) {
            this.mExpression = Expression.build(getVariables(), element.getAttribute("expression"));
            this.mThreshold = Math.abs(Utils.getAttrAsFloat(element, "threshold", 1.0f));
            this.mType = VariableType.parseType(element.getAttribute("type"));
            Variables variables = getVariables();
            this.mVar = new IndexedVariable(this.mName, variables, this.mType.isNumber());
            this.mOldVar = new IndexedVariable(this.mName + "." + OLD_VALUE, variables, this.mType.isNumber());
            this.mTrigger = CommandTrigger.fromParentElement(element, screenElementRoot);
        }
    }

    private double getDouble(boolean z, int i) {
        Expression expression = this.mExpression;
        if (expression != null) {
            return expression.evaluate();
        }
        IndexedVariable indexedVariable = this.mVar;
        return z ? indexedVariable.getArrDouble(i) : indexedVariable.getDouble();
    }

    private SharedPreferenceHelper getSharePreferenceHelper() {
        String str = (String) getVariables().get("customEditLocalId");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return SharedPreferenceHelper.getInstance(getContext().mContext, "sp_" + str);
    }

    private void onValueChange(double d) {
        if (!this.mInited) {
            this.mOldValue = d;
        }
        if (this.mTrigger == null || Math.abs(d - this.mOldValue) < this.mThreshold) {
            return;
        }
        this.mOldVar.set(this.mOldValue);
        this.mOldValue = d;
        this.mTrigger.perform();
    }

    private void update() {
        SharedPreferenceHelper sharePreferenceHelper = getSharePreferenceHelper();
        if (sharePreferenceHelper == null) {
            return;
        }
        int i = AnonymousClass1.$SwitchMap$com$miui$maml$data$VariableType[this.mType.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return;
            }
            if (sharePreferenceHelper.containsKey(this.mName)) {
                double dLoad = sharePreferenceHelper.load(this.mName, -1.0f);
                this.mVar.set(dLoad);
                onValueChange(dLoad);
                return;
            }
            double d = getDouble(false, 0);
            this.mVar.set(d);
            onValueChange(d);
            sharePreferenceHelper.save(this.mName, (float) d);
            return;
        }
        if (!sharePreferenceHelper.containsKey(this.mName)) {
            sharePreferenceHelper.save(this.mName, this.mExpression.evaluateStr());
        }
        String strLoad = sharePreferenceHelper.load(this.mName, "");
        String string = this.mVar.getString();
        if (Utils.equals(strLoad, string)) {
            return;
        }
        this.mOldVar.set(string);
        this.mVar.set(strLoad);
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.perform();
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void doRender(Canvas canvas) {
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void doTick(long j) {
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.finish();
        }
        this.mInited = false;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.init();
        }
        update();
        this.mInited = true;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void pause() {
        super.pause();
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.pause();
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void reset(long j) {
        super.reset(j);
        update();
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void resume() {
        super.resume();
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.resume();
        }
    }
}
