package cn.kuaipan.android.kss;

import android.util.Log;
import cn.kuaipan.android.utils.ApiDataHelper;
import cn.kuaipan.android.utils.Encode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class UploadRequestResult implements IKssUploadRequestResult, KssDef {
    public IKssUploadRequestResult.Block[] blocks;
    public final String file_meta;
    public String[] node_urls;
    public final byte[] secure_key;

    public UploadRequestResult(Map map) {
        this.secure_key = Encode.hexStringToByteArray(ApiDataHelper.asString(map, "secure_key"));
        this.file_meta = ApiDataHelper.asString(map, "file_meta");
        Collection<Map> collection = (Collection) map.get("block_metas");
        int i = 0;
        if (collection != null) {
            this.blocks = new IKssUploadRequestResult.Block[collection.size()];
            int i2 = 0;
            for (Map map2 : collection) {
                boolean z = ApiDataHelper.asNumber(map2.get("is_existed"), 0).intValue() != 0;
                this.blocks[i2] = new IKssUploadRequestResult.Block(ApiDataHelper.asString(map2, z ? "commit_meta" : "block_meta"), z);
                i2++;
            }
        }
        Collection collection2 = (Collection) map.get("node_urls");
        if (collection2 != null) {
            this.node_urls = new String[collection2.size()];
            Iterator it = collection2.iterator();
            while (it.hasNext()) {
                this.node_urls[i] = (String) it.next();
                i++;
            }
        }
    }

    public String toString() {
        Object obj;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("secure_key", Encode.byteArrayToHexString(this.secure_key));
            jSONObject.put("file_meta", this.file_meta);
            jSONObject.put("node_urls", this.node_urls != null ? new JSONArray((Collection) Arrays.asList(this.node_urls)) : new JSONArray());
            JSONArray jSONArray = new JSONArray();
            IKssUploadRequestResult.Block[] blockArr = this.blocks;
            if (blockArr != null) {
                for (IKssUploadRequestResult.Block block : blockArr) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("is_existed", block.exist ? 1 : 0);
                    if (block.exist) {
                        jSONObject2.put("commit_meta", block.meta);
                    } else {
                        jSONObject2.put("block_meta", block.meta);
                    }
                    jSONArray.put(jSONObject2);
                }
            }
            jSONObject.put("block_metas", jSONArray);
            obj = jSONObject;
        } catch (JSONException unused) {
            Log.w("UploadRequestResult", "Failed generate Json String for UploadRequestResult");
            obj = null;
        }
        return String.valueOf(obj);
    }
}
