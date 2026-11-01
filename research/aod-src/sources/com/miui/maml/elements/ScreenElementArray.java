package com.miui.maml.elements;

import android.text.TextUtils;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.util.Utils;
import com.xiaomi.onetrack.api.bf;
import com.xiaomi.onetrack.h.b.a;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public class ScreenElementArray extends ElementGroup {
    private static final String DEF_INDEX_VAR_NAME = "__i";
    public static final String TAG_NAME = "Array";

    public ScreenElementArray(Element element, final ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        final int attrAsInt = Utils.getAttrAsInt(element, a.h, 0);
        String attribute = element.getAttribute("indexName");
        final IndexedVariable indexedVariable = new IndexedVariable(TextUtils.isEmpty(attribute) ? DEF_INDEX_VAR_NAME : attribute, getVariables(), true);
        Utils.traverseXmlElementChildren(element, null, new Utils.XmlTraverseListener() { // from class: com.miui.maml.elements.ScreenElementArray.1
            @Override // com.miui.maml.util.Utils.XmlTraverseListener
            public void onChild(Element element2) {
                String attr = ScreenElementArray.this.getAttr(element2, bf.a);
                boolean zStartsWith = element2.getTagName().startsWith(VariableElement.TAG_NAME);
                ElementGroup elementGroupCreateArrayGroup = null;
                for (int i = 0; i < attrAsInt; i++) {
                    if (zStartsWith) {
                        element2.setAttribute("dontAddToMap", com.xiaomi.onetrack.util.a.i);
                    } else {
                        element2.setAttribute("namesSuffix", "[" + i + "]");
                    }
                    ScreenElement screenElementOnCreateChild = ScreenElementArray.super.onCreateChild(element2);
                    if (screenElementOnCreateChild != null) {
                        if (elementGroupCreateArrayGroup == null) {
                            elementGroupCreateArrayGroup = ElementGroup.createArrayGroup(screenElementRoot, indexedVariable);
                            elementGroupCreateArrayGroup.setName(attr);
                            ScreenElementArray.this.addElement(elementGroupCreateArrayGroup);
                        }
                        elementGroupCreateArrayGroup.addElement(screenElementOnCreateChild);
                    }
                }
            }
        });
    }

    @Override // com.miui.maml.elements.ElementGroup
    public ScreenElement onCreateChild(Element element) {
        return null;
    }
}
