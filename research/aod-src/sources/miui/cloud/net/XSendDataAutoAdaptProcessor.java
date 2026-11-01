package miui.cloud.net;

import java.io.OutputStream;

/* JADX INFO: loaded from: classes2.dex */
public class XSendDataAutoAdaptProcessor extends XAutoAdaptProcessor implements XHttpClient.ISendDataProcessor {
    public Object mBufferedData;
    public XHttpClient.ISendDataProcessor mBufferedProcessor;
    public String mEncode;

    private void bufferData(Object obj) {
        if (this.mBufferedData == obj) {
            return;
        }
        this.mBufferedData = obj;
        for (XAutoAdaptProcessor.DataProcessorCreator dataProcessorCreator : XAutoAdaptProcessor.AVALIABLE_PROCESSOR_CREATORS) {
            XHttpClient.ISendDataProcessor instanceIfAbleToProcessOutData = dataProcessorCreator.getInstanceIfAbleToProcessOutData(obj, this.mEncode);
            if (instanceIfAbleToProcessOutData != null) {
                this.mBufferedProcessor = instanceIfAbleToProcessOutData;
                return;
            }
        }
        this.mBufferedProcessor = new XPlainTextProcessor(this.mEncode);
    }

    @Override // miui.cloud.net.XHttpClient.ISendDataProcessor
    public String getOutDataContentType(Object obj) {
        bufferData(obj);
        return this.mBufferedProcessor.getOutDataContentType(this.mBufferedData);
    }

    @Override // miui.cloud.net.XHttpClient.ISendDataProcessor
    public int getOutDataLength(Object obj) {
        bufferData(obj);
        return this.mBufferedProcessor.getOutDataLength(this.mBufferedData);
    }

    @Override // miui.cloud.net.XHttpClient.ISendDataProcessor
    public void processOutData(Object obj, OutputStream outputStream) {
        bufferData(obj);
        this.mBufferedProcessor.processOutData(this.mBufferedData, outputStream);
    }
}
