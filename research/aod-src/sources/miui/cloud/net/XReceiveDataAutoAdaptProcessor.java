package miui.cloud.net;

import android.text.TextUtils;
import android.util.Pair;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class XReceiveDataAutoAdaptProcessor extends XAutoAdaptProcessor implements XHttpClient.IReceiveDataProcessor {
    public final Pair getContentTypeAndEncodeFromHeader(Map map) {
        String strTrim;
        List list = (List) map.get("Content-Type");
        String strTrim2 = "utf-8";
        if (list == null || list.isEmpty()) {
            strTrim = "text/plain";
        } else {
            String[] strArrSplit = TextUtils.split((String) list.get(0), ";");
            strTrim = strArrSplit[0].trim();
            if (strArrSplit.length > 1) {
                String[] strArrSplit2 = TextUtils.split(strArrSplit[1], "=");
                if (strArrSplit2.length > 1) {
                    strTrim2 = strArrSplit2[1].trim();
                }
            }
        }
        return new Pair(strTrim, strTrim2);
    }

    @Override // miui.cloud.net.XHttpClient.IReceiveDataProcessor
    public Object processInData(Map map, InputStream inputStream) {
        Pair contentTypeAndEncodeFromHeader = getContentTypeAndEncodeFromHeader(map);
        for (XAutoAdaptProcessor.DataProcessorCreator dataProcessorCreator : XAutoAdaptProcessor.AVALIABLE_PROCESSOR_CREATORS) {
            XHttpClient.IReceiveDataProcessor instanceIfAbleToProcessInData = dataProcessorCreator.getInstanceIfAbleToProcessInData((String) contentTypeAndEncodeFromHeader.first, (String) contentTypeAndEncodeFromHeader.second);
            if (instanceIfAbleToProcessInData != null) {
                return instanceIfAbleToProcessInData.processInData(map, inputStream);
            }
        }
        return new XPlainTextProcessor((String) contentTypeAndEncodeFromHeader.second).processInData(map, inputStream);
    }
}
