package com.miui.maml.elements;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import com.miui.maml.folme.ConfigValue;
import com.xiaomi.onetrack.api.bf;
import java.util.concurrent.ConcurrentHashMap;
import miuix.animation.utils.EaseManager;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes2.dex */
public class AnimConfigElement extends ConfigElement {
    public static final String TAG_NAME = "AnimConfig";
    private ConcurrentHashMap<String, ConfigData> mConfigs;

    public class ConfigData {
        public IndexedVariable mDelay;
        public IndexedVariable mEase;
        public IndexedVariable mEaseLen;
        public IndexedVariable mFromSpeed;
        public IndexedVariable mOnBeginCallback;
        public IndexedVariable mOnBeginCallbackLen;
        public IndexedVariable mOnCompleteCallback;
        public IndexedVariable mOnCompleteCallbackLen;
        public IndexedVariable mOnUpdateCallback;
        public IndexedVariable mOnUpdateCallbackLen;
        public IndexedVariable mProperty;
        public IndexedVariable mPropertyLen;

        private ConfigData() {
        }
    }

    public AnimConfigElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        ConcurrentHashMap<String, ConfigData> concurrentHashMap = new ConcurrentHashMap<>();
        this.mConfigs = concurrentHashMap;
        if (this.mHasName) {
            String str = this.mName;
            concurrentHashMap.put(str, getConfigData(element, str));
            NodeList childNodes = element.getChildNodes();
            int length = childNodes.getLength();
            for (int i = 0; i < length; i++) {
                if (childNodes.item(i).getNodeType() == 1) {
                    Element element2 = (Element) childNodes.item(i);
                    if ("Special".equals(element2.getTagName())) {
                        String attribute = element2.getAttribute(bf.a);
                        if (!TextUtils.isEmpty(attribute)) {
                            String str2 = this.mName + "." + attribute;
                            this.mConfigs.put(str2, getConfigData(element2, str2));
                        }
                    }
                }
            }
        }
    }

    private ConfigData getConfigData(Element element, String str) {
        Variables variables = getVariables();
        ConfigData configData = new ConfigData();
        NamedNodeMap attributes = element.getAttributes();
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            String nodeName = attributes.item(i).getNodeName();
            Expression[] expressionArrBuildMultiple = Expression.buildMultiple(variables, element.getAttribute(nodeName));
            if (expressionArrBuildMultiple != null) {
                updateConfigIndexVariable(configData, str, nodeName, expressionArrBuildMultiple);
            }
        }
        return configData;
    }

    private void getConfigValueFromVarToSet(IndexedVariable indexedVariable, ArraySet<String> arraySet) {
        Object obj = indexedVariable != null ? indexedVariable.get() : null;
        if (obj == null || !(obj instanceof String[])) {
            return;
        }
        for (String str : (String[]) obj) {
            arraySet.add(str);
        }
    }

    private EaseManager.EaseStyle getEase(IndexedVariable indexedVariable) {
        Object obj = indexedVariable != null ? indexedVariable.get() : null;
        if (obj != null && (obj instanceof double[])) {
            double[] dArr = (double[]) obj;
            if (dArr.length > 0) {
                int i = (int) dArr[0];
                float[] fArr = new float[dArr.length - 1];
                int length = dArr.length;
                for (int i2 = 1; i2 < length; i2++) {
                    fArr[i2 - 1] = (float) dArr[i2];
                }
                try {
                    return EaseManager.getStyle(i, fArr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void removeConfigIndexVariable(ConfigData configData, String str) {
        str.getClass();
        switch (str) {
            case "onComplete":
                IndexedVariable indexedVariable = configData.mOnCompleteCallback;
                if (indexedVariable != null) {
                    indexedVariable.set((Object) null);
                    configData.mOnCompleteCallback = null;
                }
                IndexedVariable indexedVariable2 = configData.mOnCompleteCallbackLen;
                if (indexedVariable2 != null) {
                    indexedVariable2.set(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                    configData.mOnCompleteCallbackLen = null;
                    break;
                }
                break;
            case "onBegin":
                IndexedVariable indexedVariable3 = configData.mOnBeginCallback;
                if (indexedVariable3 != null) {
                    indexedVariable3.set((Object) null);
                    configData.mOnBeginCallback = null;
                }
                IndexedVariable indexedVariable4 = configData.mOnBeginCallbackLen;
                if (indexedVariable4 != null) {
                    indexedVariable4.set(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                    configData.mOnBeginCallbackLen = null;
                    break;
                }
                break;
            case "property":
                IndexedVariable indexedVariable5 = configData.mProperty;
                if (indexedVariable5 != null) {
                    indexedVariable5.set((Object) null);
                    configData.mProperty = null;
                }
                IndexedVariable indexedVariable6 = configData.mPropertyLen;
                if (indexedVariable6 != null) {
                    indexedVariable6.set(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                    configData.mPropertyLen = null;
                    break;
                }
                break;
            case "ease":
                IndexedVariable indexedVariable7 = configData.mEase;
                if (indexedVariable7 != null) {
                    indexedVariable7.set((Object) null);
                    configData.mEase = null;
                }
                IndexedVariable indexedVariable8 = configData.mEaseLen;
                if (indexedVariable8 != null) {
                    indexedVariable8.set(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                    configData.mEaseLen = null;
                    break;
                }
                break;
            case "fromSpeed":
                IndexedVariable indexedVariable9 = configData.mFromSpeed;
                if (indexedVariable9 != null) {
                    indexedVariable9.set(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                    configData.mFromSpeed = null;
                    break;
                }
                break;
            case "delay":
                IndexedVariable indexedVariable10 = configData.mDelay;
                if (indexedVariable10 != null) {
                    indexedVariable10.set(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                    configData.mDelay = null;
                    break;
                }
                break;
            case "onUpdate":
                IndexedVariable indexedVariable11 = configData.mOnUpdateCallback;
                if (indexedVariable11 != null) {
                    indexedVariable11.set((Object) null);
                    configData.mOnUpdateCallback = null;
                }
                IndexedVariable indexedVariable12 = configData.mOnUpdateCallbackLen;
                if (indexedVariable12 != null) {
                    indexedVariable12.set(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                    configData.mOnUpdateCallbackLen = null;
                    break;
                }
                break;
        }
    }

    /* JADX WARN: Code duplicated, block: B:103:0x0193 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:106:0x01a3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:82:0x0176 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:83:0x0178  */
    /* JADX WARN: Code duplicated, block: B:84:0x0183 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:85:0x0185  */
    /* JADX WARN: Code duplicated, block: B:87:0x0189  */
    /* JADX WARN: Code duplicated, block: B:89:0x018d  */
    /* JADX WARN: Code duplicated, block: B:91:0x0195  */
    /* JADX WARN: Code duplicated, block: B:93:0x0199  */
    /* JADX WARN: Code duplicated, block: B:95:0x019d  */
    /* JADX WARN: Code duplicated, block: B:99:0x01aa  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r7v5, types: [double[]] */
    /* JADX WARN: Type inference failed for: r7v9, types: [java.lang.Double] */
    private void updateConfigIndexVariable(ConfigData configData, String str, String str2, @NonNull Expression[] expressionArr) {
        boolean z;
        boolean z2;
        IndexedVariable indexedVariable;
        IndexedVariable indexedVariable2;
        int length;
        Object objValueOf;
        Expression expression;
        Expression expression2;
        IndexedVariable indexedVariable3;
        String str3 = str + "." + str2;
        Variables variables = getVariables();
        str2.getClass();
        int i = 0;
        IndexedVariable indexedVariable4 = null;
        switch (str2) {
            case "onComplete":
                if (configData.mOnCompleteCallback == null) {
                    configData.mOnCompleteCallback = new IndexedVariable(str3, variables, false);
                }
                if (configData.mOnCompleteCallbackLen == null) {
                    configData.mOnCompleteCallbackLen = new IndexedVariable(str3 + VariableElement.LENGTH_SUFFIX, variables, true);
                }
                IndexedVariable indexedVariable5 = configData.mOnCompleteCallback;
                indexedVariable4 = configData.mOnCompleteCallbackLen;
                indexedVariable3 = indexedVariable5;
                z2 = false;
                z = false;
                indexedVariable2 = indexedVariable3;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                    break;
                }
                break;
            case "onBegin":
                if (configData.mOnBeginCallback == null) {
                    configData.mOnBeginCallback = new IndexedVariable(str3, variables, false);
                }
                if (configData.mOnBeginCallbackLen == null) {
                    configData.mOnBeginCallbackLen = new IndexedVariable(str3 + VariableElement.LENGTH_SUFFIX, variables, true);
                }
                IndexedVariable indexedVariable6 = configData.mOnBeginCallback;
                indexedVariable4 = configData.mOnBeginCallbackLen;
                indexedVariable3 = indexedVariable6;
                z2 = false;
                z = false;
                indexedVariable2 = indexedVariable3;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                    break;
                }
                break;
            case "property":
                if (configData.mProperty == null) {
                    configData.mProperty = new IndexedVariable(str3, variables, false);
                }
                if (configData.mPropertyLen == null) {
                    configData.mPropertyLen = new IndexedVariable(str3 + VariableElement.LENGTH_SUFFIX, variables, true);
                }
                IndexedVariable indexedVariable7 = configData.mProperty;
                indexedVariable4 = configData.mPropertyLen;
                indexedVariable3 = indexedVariable7;
                z2 = false;
                z = false;
                indexedVariable2 = indexedVariable3;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                    break;
                }
                break;
            case "ease":
                if (configData.mEase == null) {
                    configData.mEase = new IndexedVariable(str3, variables, false);
                }
                if (configData.mEaseLen == null) {
                    configData.mEaseLen = new IndexedVariable(str3 + VariableElement.LENGTH_SUFFIX, variables, true);
                }
                IndexedVariable indexedVariable8 = configData.mEase;
                indexedVariable4 = configData.mEaseLen;
                z = true;
                z2 = false;
                indexedVariable2 = indexedVariable8;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                    break;
                }
                break;
            case "fromSpeed":
                if (configData.mFromSpeed == null) {
                    configData.mFromSpeed = new IndexedVariable(str3, variables, true);
                }
                indexedVariable = configData.mFromSpeed;
                z2 = true;
                z = false;
                indexedVariable2 = indexedVariable;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                    break;
                }
                break;
            case "delay":
                if (configData.mDelay == null) {
                    configData.mDelay = new IndexedVariable(str3, variables, true);
                }
                indexedVariable = configData.mDelay;
                z2 = true;
                z = false;
                indexedVariable2 = indexedVariable;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                    break;
                }
                break;
            case "onUpdate":
                if (configData.mOnUpdateCallback == null) {
                    configData.mOnUpdateCallback = new IndexedVariable(str3, variables, false);
                }
                if (configData.mOnUpdateCallbackLen == null) {
                    configData.mOnUpdateCallbackLen = new IndexedVariable(str3 + VariableElement.LENGTH_SUFFIX, variables, true);
                }
                IndexedVariable indexedVariable9 = configData.mOnUpdateCallback;
                indexedVariable4 = configData.mOnUpdateCallbackLen;
                indexedVariable3 = indexedVariable9;
                z2 = false;
                z = false;
                indexedVariable2 = indexedVariable3;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                    break;
                }
                break;
        }
    }

    public void clearConfigData() {
        for (ConfigData configData : this.mConfigs.values()) {
            removeConfigIndexVariable(configData, "fromSpeed");
            removeConfigIndexVariable(configData, "delay");
            removeConfigIndexVariable(configData, "ease");
            removeConfigIndexVariable(configData, "property");
            removeConfigIndexVariable(configData, "onUpdate");
            removeConfigIndexVariable(configData, "onBegin");
            removeConfigIndexVariable(configData, "onComplete");
        }
        this.mConfigs.clear();
    }

    @Override // com.miui.maml.elements.ConfigElement
    public void evaluateConfigValue() {
        this.mTempValueList.clear();
        for (ConfigData configData : this.mConfigs.values()) {
            ConfigValue configValue = new ConfigValue();
            IndexedVariable indexedVariable = configData.mDelay;
            if (indexedVariable != null) {
                configValue.mDelay = (long) indexedVariable.getDouble();
            }
            IndexedVariable indexedVariable2 = configData.mFromSpeed;
            if (indexedVariable2 != null) {
                configValue.mFromSpeed = (long) indexedVariable2.getDouble();
                configValue.mHasFromSpeed = true;
            }
            IndexedVariable indexedVariable3 = configData.mEase;
            if (indexedVariable3 != null) {
                configValue.mEase = getEase(indexedVariable3);
            }
            IndexedVariable indexedVariable4 = configData.mProperty;
            if (indexedVariable4 != null) {
                getConfigValueFromVarToSet(indexedVariable4, configValue.mRelatedProperty);
            }
            IndexedVariable indexedVariable5 = configData.mOnBeginCallback;
            if (indexedVariable5 != null) {
                getConfigValueFromVarToSet(indexedVariable5, configValue.mOnBeginCallback);
            }
            IndexedVariable indexedVariable6 = configData.mOnCompleteCallback;
            if (indexedVariable6 != null) {
                getConfigValueFromVarToSet(indexedVariable6, configValue.mOnCompleteCallback);
            }
            IndexedVariable indexedVariable7 = configData.mOnUpdateCallback;
            if (indexedVariable7 != null) {
                getConfigValueFromVarToSet(indexedVariable7, configValue.mOnUpdateCallback);
            }
            this.mTempValueList.add(configValue);
        }
    }

    public void removeConfigData(String str, String str2) {
        ConfigData configData = this.mConfigs.get(str);
        if (configData != null) {
            removeConfigIndexVariable(configData, str2);
        }
    }

    public void updateConfigData(String str, String str2, Expression[] expressionArr) {
        ConfigData configData = this.mConfigs.get(str);
        if (configData == null) {
            configData = new ConfigData();
            this.mConfigs.put(str, configData);
        }
        updateConfigIndexVariable(configData, str, str2, expressionArr);
    }
}
