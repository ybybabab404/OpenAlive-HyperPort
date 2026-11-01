package com.xiaomi.opensdk.file.sdk;

import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.kss.UploadRequestResult;
import cn.kuaipan.android.utils.ApiDataHelper;
import cn.kuaipan.android.utils.IObtainable;
import cn.kuaipan.android.utils.JsonUtils;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class FileUploadRequestResult extends UploadRequestResult implements ApiDataHelper.IKscData {
    public static final ApiDataHelper.IKscData.Parser PARSER = new ApiDataHelper.IKscData.Parser<FileUploadRequestResult>() { // from class: com.xiaomi.opensdk.file.sdk.FileUploadRequestResult.1
    };
    public final String requestId;

    public FileUploadRequestResult(Map map) {
        super(map);
        this.requestId = ApiDataHelper.asString(map, "requestId");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v7 */
    public static FileUploadRequestResult create(String str) throws Throwable {
        Throwable th;
        JSONException e;
        IOException e2;
        try {
            try {
                Map map = (Map) JsonUtils.parser(new StringReader(str));
                try {
                    FileUploadRequestResult fileUploadRequestResult = new FileUploadRequestResult(map);
                    if (map != null && (map instanceof IObtainable)) {
                        ((IObtainable) map).recycle();
                    }
                    return fileUploadRequestResult;
                } catch (IOException e3) {
                    e2 = e3;
                    throw new KscException(501004, "kss is null", e2);
                } catch (JSONException e4) {
                    e = e4;
                    throw new KscException(501001, "kss is not json", e);
                }
            } catch (Throwable th2) {
                th = th2;
                if (str != 0 && (str instanceof IObtainable)) {
                    ((IObtainable) str).recycle();
                }
                throw th;
            }
        } catch (IOException e5) {
            e2 = e5;
        } catch (JSONException e6) {
            e = e6;
        } catch (Throwable th3) {
            th = th3;
            str = 0;
            if (str != 0) {
                ((IObtainable) str).recycle();
            }
            throw th;
        }
    }

    @Override // cn.kuaipan.android.kss.UploadRequestResult
    public String toString() {
        String string = super.toString();
        try {
            return new JSONObject(string).put("requestId", this.requestId).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return string;
        }
    }
}
