package miui.cloud.net;

/* JADX INFO: loaded from: classes2.dex */
public abstract class XAutoAdaptProcessor {
    public static final DataProcessorCreator[] AVALIABLE_PROCESSOR_CREATORS = {XByteArrayProcessor.CREATOR, XPlainTextProcessor.CREATOR, XUrlencodedProcessor.CREATOR, XJSONProcessor.CREATOR};

    public interface DataProcessorCreator {
        XHttpClient.IReceiveDataProcessor getInstanceIfAbleToProcessInData(String str, String str2);

        XHttpClient.ISendDataProcessor getInstanceIfAbleToProcessOutData(Object obj, String str);
    }
}
