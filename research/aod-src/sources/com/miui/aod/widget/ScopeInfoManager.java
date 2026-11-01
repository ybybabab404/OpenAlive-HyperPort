package com.miui.aod.widget;

import androidx.collection.ArrayMap;
import com.miui.aod.R;
import com.miui.aod.components.DrawableData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class ScopeInfoManager {
    public static final Map sFrameInfoMap;

    static {
        ArrayMap arrayMap = new ArrayMap();
        sFrameInfoMap = arrayMap;
        arrayMap.put("scope_a", new ScopeInfo(R.drawable.scope_a, R.drawable.scope_a_thumbnail, R.string.scope_a));
        arrayMap.put("scope_b", new ScopeInfo(R.drawable.scope_b, R.drawable.scope_b_thumbnail, R.string.scope_b));
        arrayMap.put("scope_c", new ScopeInfo(R.drawable.scope_c, R.drawable.scope_c_thumbnail, R.string.scope_c));
        arrayMap.put("scope_d", new ScopeInfo(R.drawable.scope_d, R.drawable.scope_d_thumbnail, R.string.scope_d));
        arrayMap.put("scope_e", new ScopeInfo(R.drawable.scope_e, R.drawable.scope_e_thumbnail, R.string.scope_e));
        arrayMap.put("scope_f", new ScopeInfo(R.drawable.scope_f, R.drawable.scope_f_thumbnail, R.string.scope_f));
        arrayMap.put("scope_g", new ScopeInfo(R.drawable.scope_g, R.drawable.scope_g_thumbnail, R.string.scope_g));
        arrayMap.put("scope_h", new ScopeInfo(R.drawable.scope_h, R.drawable.scope_h_thumbnail, R.string.scope_h));
    }

    public static List getDrawableListByCateName(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new DrawableData(R.drawable.scope_a_thumbnail, "scope_a"));
        arrayList.add(new DrawableData(R.drawable.scope_b_thumbnail, "scope_b"));
        arrayList.add(new DrawableData(R.drawable.scope_c_thumbnail, "scope_c"));
        arrayList.add(new DrawableData(R.drawable.scope_d_thumbnail, "scope_d"));
        arrayList.add(new DrawableData(R.drawable.scope_e_thumbnail, "scope_e"));
        arrayList.add(new DrawableData(R.drawable.scope_f_thumbnail, "scope_f"));
        arrayList.add(new DrawableData(R.drawable.scope_g_thumbnail, "scope_g"));
        arrayList.add(new DrawableData(R.drawable.scope_h_thumbnail, "scope_h"));
        return arrayList;
    }

    public static ScopeInfo getFrameAnimationInfoByStyleName(String str) {
        return (ScopeInfo) sFrameInfoMap.get(str);
    }
}
