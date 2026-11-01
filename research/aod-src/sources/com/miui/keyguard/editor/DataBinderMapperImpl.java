package com.miui.keyguard.editor;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.miui.keyguard.editor.databinding.KgEditHistoryFragmentBindingImpl;
import com.miui.keyguard.editor.databinding.KgLayoutHistoryTemplateItemBindingImpl;
import com.miui.keyguard.editor.databinding.KgLayoutMyTemplateItemBindingImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    public static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;

    public static class InnerBrLookup {
        public static final SparseArray sKeys;

        static {
            SparseArray sparseArray = new SparseArray(3);
            sKeys = sparseArray;
            sparseArray.put(0, "_all");
            sparseArray.put(1, "dataBean");
            sparseArray.put(2, "vm");
        }
    }

    public static class InnerLayoutIdLookup {
        public static final HashMap sKeys;

        static {
            HashMap map = new HashMap(3);
            sKeys = map;
            map.put("layout/kg_edit_history_fragment_0", Integer.valueOf(R.layout.kg_edit_history_fragment));
            map.put("layout/kg_layout_history_template_item_0", Integer.valueOf(R.layout.kg_layout_history_template_item));
            map.put("layout/kg_layout_my_template_item_0", Integer.valueOf(R.layout.kg_layout_my_template_item));
        }
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(3);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(R.layout.kg_edit_history_fragment, 1);
        sparseIntArray.put(R.layout.kg_layout_history_template_item, 2);
        sparseIntArray.put(R.layout.kg_layout_my_template_item, 3);
    }

    @Override // androidx.databinding.DataBinderMapper
    public List collectDependencies() {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        return arrayList;
    }

    @Override // androidx.databinding.DataBinderMapper
    public String convertBrIdToString(int i) {
        return (String) InnerBrLookup.sKeys.get(i);
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View view, int i) {
        int i2 = INTERNAL_LAYOUT_ID_LOOKUP.get(i);
        if (i2 <= 0) {
            return null;
        }
        Object tag = view.getTag();
        if (tag == null) {
            throw new RuntimeException("view must have a tag");
        }
        if (i2 == 1) {
            if ("layout/kg_edit_history_fragment_0".equals(tag)) {
                return new KgEditHistoryFragmentBindingImpl(dataBindingComponent, view);
            }
            throw new IllegalArgumentException("The tag for kg_edit_history_fragment is invalid. Received: " + tag);
        }
        if (i2 == 2) {
            if ("layout/kg_layout_history_template_item_0".equals(tag)) {
                return new KgLayoutHistoryTemplateItemBindingImpl(dataBindingComponent, view);
            }
            throw new IllegalArgumentException("The tag for kg_layout_history_template_item is invalid. Received: " + tag);
        }
        if (i2 != 3) {
            return null;
        }
        if ("layout/kg_layout_my_template_item_0".equals(tag)) {
            return new KgLayoutMyTemplateItemBindingImpl(dataBindingComponent, view);
        }
        throw new IllegalArgumentException("The tag for kg_layout_my_template_item is invalid. Received: " + tag);
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View[] viewArr, int i) {
        if (viewArr == null || viewArr.length == 0 || INTERNAL_LAYOUT_ID_LOOKUP.get(i) <= 0 || viewArr[0].getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    @Override // androidx.databinding.DataBinderMapper
    public int getLayoutId(String str) {
        Integer num;
        if (str == null || (num = (Integer) InnerLayoutIdLookup.sKeys.get(str)) == null) {
            return 0;
        }
        return num.intValue();
    }
}
