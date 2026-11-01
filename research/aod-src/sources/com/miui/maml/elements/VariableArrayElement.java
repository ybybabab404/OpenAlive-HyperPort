package com.miui.maml.elements;

import android.graphics.Canvas;
import android.text.TextUtils;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import com.miui.maml.util.Utils;
import com.xiaomi.onetrack.api.am;
import com.xiaomi.onetrack.api.bf;
import java.util.ArrayList;
import java.util.HashSet;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public class VariableArrayElement extends ScreenElement {
    public static final String TAG_NAME = "VarArray";
    private ArrayList<Item> mArray;
    Object[] mData;
    private int mItemCount;
    private IndexedVariable mItemCountVar;
    Type mType;
    HashSet<VarObserver> mVarObserver;
    private ArrayList<Var> mVars;

    public class Item {
        public Expression mExpression;
        public Object mValue;

        public Item(Variables variables, Element element) {
            if (element != null) {
                this.mExpression = Expression.build(variables, element.getAttribute("expression"));
                String attribute = element.getAttribute(am.t);
                if (VariableArrayElement.this.mType != Type.DOUBLE || TextUtils.isEmpty(attribute)) {
                    this.mValue = attribute;
                } else {
                    try {
                        this.mValue = Double.valueOf(Double.parseDouble(attribute));
                    } catch (NumberFormatException unused) {
                    }
                }
            }
        }

        public Item(Object obj) {
            this.mValue = obj;
            this.mExpression = null;
        }

        public Double evaluate() {
            Expression expression = this.mExpression;
            if (expression != null) {
                if (expression.isNull()) {
                    return null;
                }
                return Double.valueOf(this.mExpression.evaluate());
            }
            Object obj = this.mValue;
            if (obj instanceof Number) {
                return Double.valueOf(((Number) obj).doubleValue());
            }
            return null;
        }

        public String evaluateStr() {
            Expression expression = this.mExpression;
            if (expression != null) {
                return expression.evaluateStr();
            }
            Object obj = this.mValue;
            if (obj instanceof String) {
                return (String) obj;
            }
            return null;
        }

        public boolean isExpression() {
            return this.mExpression != null;
        }
    }

    public enum Type {
        DOUBLE,
        STRING
    }

    public class Var {
        private boolean mConst;
        private boolean mCurrentItemIsExpression;
        private int mIndex = -1;
        private Expression mIndexExpression;
        private String mName;
        private IndexedVariable mVar;

        public Var(Variables variables, Element element) {
            if (element != null) {
                this.mName = element.getAttribute(bf.a);
                this.mIndexExpression = Expression.build(variables, element.getAttribute("index"));
                this.mConst = Boolean.parseBoolean(element.getAttribute("const"));
                this.mVar = new IndexedVariable(this.mName, VariableArrayElement.this.getVariables(), VariableArrayElement.this.mType != Type.STRING);
            }
        }

        private void update() {
            Expression expression = this.mIndexExpression;
            if (expression == null) {
                return;
            }
            int iEvaluate = (int) expression.evaluate();
            if (iEvaluate < 0 || iEvaluate >= VariableArrayElement.this.mArray.size()) {
                Type type = VariableArrayElement.this.mType;
                if (type == Type.STRING) {
                    this.mVar.set((Object) null);
                    return;
                } else {
                    if (type == Type.DOUBLE) {
                        this.mVar.set(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                        return;
                    }
                    return;
                }
            }
            if (this.mIndex != iEvaluate || this.mCurrentItemIsExpression) {
                Item item = (Item) VariableArrayElement.this.mArray.get(iEvaluate);
                if (this.mIndex != iEvaluate) {
                    this.mIndex = iEvaluate;
                    this.mCurrentItemIsExpression = item.isExpression();
                }
                Type type2 = VariableArrayElement.this.mType;
                if (type2 == Type.STRING) {
                    this.mVar.set(item.evaluateStr());
                } else if (type2 == Type.DOUBLE) {
                    this.mVar.set(item.evaluate());
                }
            }
        }

        public void init() {
            this.mIndex = -1;
            update();
        }

        public void tick() {
            if (this.mConst) {
                return;
            }
            update();
        }
    }

    public interface VarObserver {
        void onDataChange(Object[] objArr);
    }

    public VariableArrayElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mArray = new ArrayList<>();
        this.mVars = new ArrayList<>();
        Type type = Type.DOUBLE;
        this.mType = type;
        this.mVarObserver = new HashSet<>();
        if (element != null) {
            if ("string".equalsIgnoreCase(element.getAttribute("type"))) {
                this.mType = Type.STRING;
            } else {
                this.mType = type;
            }
            final Variables variables = getVariables();
            Utils.traverseXmlElementChildren(Utils.getChild(element, "Vars"), VariableElement.TAG_NAME, new Utils.XmlTraverseListener() { // from class: com.miui.maml.elements.VariableArrayElement.1
                @Override // com.miui.maml.util.Utils.XmlTraverseListener
                public void onChild(Element element2) {
                    VariableArrayElement.this.mVars.add(VariableArrayElement.this.new Var(variables, element2));
                }
            });
            Utils.traverseXmlElementChildren(Utils.getChild(element, "Items"), ListScreenElement.ListItemElement.TAG_NAME, new Utils.XmlTraverseListener() { // from class: com.miui.maml.elements.VariableArrayElement.2
                @Override // com.miui.maml.util.Utils.XmlTraverseListener
                public void onChild(Element element2) {
                    VariableArrayElement.this.mArray.add(VariableArrayElement.this.new Item(variables, element2));
                }
            });
            if (this.mHasName) {
                this.mItemCountVar = new IndexedVariable(this.mName + ".count", variables, true);
            }
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void doRender(Canvas canvas) {
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void doTick(long j) {
        int size = this.mVars.size();
        for (int i = 0; i < size; i++) {
            this.mVars.get(i).tick();
        }
    }

    public int getItemSize() {
        return this.mItemCount;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        int size = this.mVars.size();
        for (int i = 0; i < size; i++) {
            this.mVars.get(i).init();
        }
        int size2 = this.mArray.size();
        this.mItemCount = size2;
        IndexedVariable indexedVariable = this.mItemCountVar;
        if (indexedVariable != null) {
            indexedVariable.set(size2);
        }
        if (this.mData == null) {
            this.mData = new Object[this.mItemCount];
            for (int i2 = 0; i2 < this.mItemCount; i2++) {
                this.mData[i2] = this.mArray.get(i2).mValue;
            }
        }
    }

    public void registerVarObserver(VarObserver varObserver, boolean z) {
        if (varObserver == null) {
            return;
        }
        if (!z) {
            this.mVarObserver.remove(varObserver);
        } else {
            this.mVarObserver.add(varObserver);
            varObserver.onDataChange(this.mData);
        }
    }
}
