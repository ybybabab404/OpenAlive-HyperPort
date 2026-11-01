package cn.kuaipan.android.kss;

import android.util.Log;
import cn.kuaipan.android.utils.Encode;
import java.util.Arrays;
import java.util.Collection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class DownloadRequestResult implements IKssDownloadRequestResult, KssDef {
    public IKssDownloadRequestResult.Block[] blocks;
    public byte[] secure_key;
    public final String stat;

    public String toString() {
        Object obj;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("stat", this.stat);
            jSONObject.put("secure_key", Encode.byteArrayToHexString(this.secure_key));
            JSONArray jSONArray = new JSONArray();
            IKssDownloadRequestResult.Block[] blockArr = this.blocks;
            if (blockArr != null) {
                for (IKssDownloadRequestResult.Block block : blockArr) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("sha1", block.sha1);
                    jSONObject2.put("size", block.size);
                    jSONObject2.put("urls", block.urls != null ? new JSONArray((Collection) Arrays.asList(block.urls)) : new JSONArray());
                    jSONArray.put(jSONObject2);
                }
            }
            jSONObject.put("blocks", jSONArray);
            obj = jSONObject;
        } catch (JSONException unused) {
            Log.w("DownloadRequestResult", "Failed generate Json String for UploadRequestResult");
            obj = null;
        }
        return String.valueOf(obj);
    }
}
