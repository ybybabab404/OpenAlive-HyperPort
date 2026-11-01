package cn.kuaipan.android.kss.upload;

import android.text.TextUtils;
import android.util.Log;
import cn.kuaipan.android.kss.KssDef;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class UploadFileInfo implements KssDef {
    public final ArrayList mBlockInfos = new ArrayList();
    public String mSha1;

    public static class BlockInfo {
        public final String md5;
        public final String sha1;
        public final int size;

        public BlockInfo(String str, String str2, int i) {
            this.sha1 = str;
            this.md5 = str2;
            this.size = i;
        }
    }

    public UploadFileInfo(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.mSha1 = jSONObject.optString("sha1");
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("block_infos");
            if (jSONArrayOptJSONArray == null) {
                return;
            }
            int length = jSONArrayOptJSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                String strOptString = null;
                String strOptString2 = jSONObjectOptJSONObject == null ? null : jSONObjectOptJSONObject.optString("sha1");
                if (jSONObjectOptJSONObject != null) {
                    strOptString = jSONObjectOptJSONObject.optString("md5");
                }
                int iOptInt = -1;
                if (jSONObjectOptJSONObject != null) {
                    iOptInt = jSONObjectOptJSONObject.optInt("size", -1);
                }
                if (!TextUtils.isEmpty(strOptString2) && !TextUtils.isEmpty(strOptString) && iOptInt >= 0) {
                    appendBlock(strOptString2, strOptString, iOptInt);
                }
            }
        } catch (JSONException e) {
            Log.w("UploadFileInfo", "Failed parser UploadFileInfo from a String. The String:" + str, e);
        }
    }

    public void appendBlock(String str, String str2, long j) {
        this.mBlockInfos.add(new BlockInfo(str, str2, (int) j));
    }

    public final JSONArray getBlockInfos() {
        try {
            JSONArray jSONArray = new JSONArray();
            for (BlockInfo blockInfo : this.mBlockInfos) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("sha1", blockInfo.sha1);
                jSONObject.put("md5", blockInfo.md5);
                jSONObject.put("size", blockInfo.size);
                jSONArray.put(jSONObject);
            }
            return jSONArray;
        } catch (Throwable unused) {
            Log.w("UploadFileInfo", "Failed generate Json String for UploadRequestInfo");
            return null;
        }
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block_infos", getBlockInfos());
            jSONObject.put("sha1", this.mSha1);
        } catch (Throwable unused) {
        }
        return String.valueOf(jSONObject);
    }
}
