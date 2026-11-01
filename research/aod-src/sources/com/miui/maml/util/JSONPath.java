package com.miui.maml.util;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class JSONPath {
    private static final String LOG_TAG = "JSONPath";
    private JSONObject mRoot;
    private JSONArray mRootArray;

    public JSONPath(JSONArray jSONArray) {
        this.mRootArray = jSONArray;
    }

    public JSONPath(JSONObject jSONObject) {
        this.mRoot = jSONObject;
    }

    public Object get(String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split("/");
        Object obj = this.mRoot;
        if (obj == null) {
            obj = this.mRootArray;
        }
        if (obj == null) {
            return null;
        }
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            try {
                String strSubstring = strArrSplit[i2];
                if (!TextUtils.isEmpty(strSubstring)) {
                    int iIndexOf = strSubstring.indexOf("[");
                    if (iIndexOf != -1) {
                        i = Integer.parseInt(strSubstring.substring(iIndexOf + 1, strSubstring.length() - 1));
                        strSubstring = strSubstring.substring(0, iIndexOf);
                    } else {
                        i = -1;
                    }
                    if ((obj instanceof JSONObject) && !TextUtils.isEmpty(strSubstring)) {
                        obj = ((JSONObject) obj).get(strSubstring);
                    }
                    if (obj instanceof JSONArray) {
                        JSONArray jSONArray = (JSONArray) obj;
                        if (i == -1) {
                            return obj;
                        }
                        obj = jSONArray.get(i);
                    }
                    if (obj == null || obj == JSONObject.NULL) {
                        return null;
                    }
                }
            } catch (Exception e) {
                MamlLog.w(LOG_TAG, e.toString());
                return null;
            }
        }
        return obj;
    }
}
