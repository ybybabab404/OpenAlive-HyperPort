package miui.cloud.net;

import android.text.TextUtils;
import android.util.Log;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class XUrlencodedProcessor implements XHttpClient.ISendDataProcessor, XHttpClient.IReceiveDataProcessor {
    public static final XAutoAdaptProcessor.DataProcessorCreator CREATOR = new XAutoAdaptProcessor.DataProcessorCreator() { // from class: miui.cloud.net.XUrlencodedProcessor.1
        @Override // miui.cloud.net.XAutoAdaptProcessor.DataProcessorCreator
        public XHttpClient.IReceiveDataProcessor getInstanceIfAbleToProcessInData(String str, String str2) {
            if (str.equals(XUrlencodedProcessor.MIME_TYPE)) {
                return new XUrlencodedProcessor(str2);
            }
            return null;
        }

        @Override // miui.cloud.net.XAutoAdaptProcessor.DataProcessorCreator
        public XHttpClient.ISendDataProcessor getInstanceIfAbleToProcessOutData(Object obj, String str) {
            if (obj instanceof Map) {
                return new XUrlencodedProcessor(str);
            }
            return null;
        }
    };
    public static String MIME_TYPE = "application/x-www-form-urlencoded";
    public Map mBufferedData;
    public String mBufferedDataString;
    public String mEncode;
    public XPlainTextProcessor mPlainTextProcessor;

    public XUrlencodedProcessor(String str) {
        this.mPlainTextProcessor = new XPlainTextProcessor(str);
        this.mEncode = str;
    }

    public final void bufferData(Map map) {
        if (this.mBufferedData == map) {
            return;
        }
        this.mBufferedData = map;
        this.mBufferedDataString = encode(map);
    }

    public final Map decode(String str) throws XHttpClient.DataConversionException {
        HashMap map = new HashMap();
        String[] strArrSplit = TextUtils.split(str, "&");
        for (int i = 0; i < strArrSplit.length; i++) {
            String[] strArrSplit2 = TextUtils.split(strArrSplit[i], "=");
            if (strArrSplit2.length < 2) {
                throw new XHttpClient.DataConversionException("Bad input data: " + str + ", wrong format near: " + strArrSplit[i]);
            }
            try {
                map.put(URLDecoder.decode(strArrSplit2[0], this.mEncode), URLDecoder.decode(strArrSplit2[1], this.mEncode));
            } catch (UnsupportedEncodingException unused) {
                Log.e("XUrlencodedProcessor", "Encode not supported: " + this.mEncode);
            }
        }
        return map;
    }

    public final String encode(Map map) throws XHttpClient.DataConversionException {
        ArrayList arrayList = new ArrayList();
        for (String str : map.keySet()) {
            try {
                String str2 = (String) map.get(str);
                if (str2 == null) {
                    str2 = "null";
                }
                arrayList.add(URLEncoder.encode(str, this.mEncode) + "=" + URLEncoder.encode(str2, this.mEncode));
            } catch (UnsupportedEncodingException e) {
                throw new XHttpClient.DataConversionException(e);
            }
        }
        return TextUtils.join("&", arrayList.toArray(new String[0]));
    }

    @Override // miui.cloud.net.XHttpClient.ISendDataProcessor
    public String getOutDataContentType(Object obj) {
        return MIME_TYPE;
    }

    @Override // miui.cloud.net.XHttpClient.ISendDataProcessor
    public int getOutDataLength(Object obj) {
        bufferData((Map) obj);
        return this.mPlainTextProcessor.getOutDataLength(this.mBufferedDataString);
    }

    @Override // miui.cloud.net.XHttpClient.IReceiveDataProcessor
    public Object processInData(Map map, InputStream inputStream) {
        return decode((String) this.mPlainTextProcessor.processInData(map, inputStream));
    }

    @Override // miui.cloud.net.XHttpClient.ISendDataProcessor
    public void processOutData(Object obj, OutputStream outputStream) {
        bufferData((Map) obj);
        this.mPlainTextProcessor.processOutData(this.mBufferedDataString, outputStream);
    }
}
