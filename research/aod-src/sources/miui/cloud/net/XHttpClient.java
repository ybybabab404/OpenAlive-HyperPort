package miui.cloud.net;

import android.text.TextUtils;
import com.xiaomi.micloudsdk.stat.MiCloudNetEventStatInjector;
import com.xiaomi.micloudsdk.stat.NetFailedStatParam;
import com.xiaomi.micloudsdk.stat.NetSuccessStatParam;
import com.xiaomi.onetrack.g.b;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import miui.cloud.common.XCallback;
import miui.cloud.common.XLogger;
import miui.cloud.common.XWrapper;

/* JADX INFO: loaded from: classes2.dex */
public final class XHttpClient {
    public volatile DataProcessorFactor mDataProcessorFactor;
    public int mMaxRuningTaskCount;
    public LinkedList mPendingTasks;
    public int mRunningTaskCount;
    public volatile IUserAgentNameProvider mUserAgentNameProvider;

    /* JADX INFO: renamed from: miui.cloud.net.XHttpClient$1, reason: invalid class name */
    class AnonymousClass1 implements IResponseHandler {
        public final /* synthetic */ XWrapper val$response;

        @Override // miui.cloud.net.XHttpClient.IResponseHandler
        public void handleHttpResponse(HttpResponse httpResponse) {
            this.val$response.set(httpResponse);
        }
    }

    public static class DataConversionException extends Exception {
        public DataConversionException(String str) {
            super(str);
        }

        public DataConversionException(String str, Throwable th) {
            super(str, th);
        }

        public DataConversionException(Throwable th) {
            super(th);
        }
    }

    public static class DataProcessorFactor {
        public IReceiveDataProcessor getReceiveDataProcessor(Map map, InputStream inputStream) {
            return new XReceiveDataAutoAdaptProcessor();
        }
    }

    public class HttpRequest implements Runnable {
        public XCallback mCallback;
        public Object mCtx;
        public Object mData;
        public Map mHeader;
        public String mMethod;
        public IReceiveDataProcessor mReceiveDataProcessor;
        public HttpResponse mResponse;
        public ISendDataProcessor mSendDataProcessor;
        public String mUrl;
        public final /* synthetic */ XHttpClient this$0;

        /* JADX WARN: Code duplicated, block: B:80:0x0186  */
        /* JADX WARN: Code duplicated, block: B:81:0x019c  */
        public void doHttpRequest() throws Throwable {
            XCallback xCallback;
            long jCurrentTimeMillis;
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            HttpURLConnection httpURLConnection = null;
            httpURLConnection = null;
            HttpURLConnection httpURLConnection2 = null;
            Date date = null;
            HttpURLConnection httpURLConnection3 = null;
            HttpURLConnection httpURLConnection4 = null;
            HttpURLConnection httpURLConnection5 = null;
            try {
                try {
                    if (this.mUrl.equals("[TEST]")) {
                        HttpResponse httpResponse = this.mResponse;
                        httpResponse.stateCode = 1024;
                        httpResponse.stateMessage = "TEST OK";
                        httpResponse.headers = this.mHeader;
                        httpResponse.content = this.mData;
                        try {
                            Thread.sleep(200L);
                        } catch (InterruptedException unused) {
                        }
                    } else {
                        HttpURLConnection httpURLConnection6 = (HttpURLConnection) new URL(this.mUrl).openConnection();
                        try {
                            prepareConn(httpURLConnection6);
                            httpURLConnection6.setRequestMethod(this.mMethod);
                            setRequestHeader(httpURLConnection6, this.mHeader);
                            httpURLConnection6.addRequestProperty(b.h, "close");
                            if (this.mData != null) {
                                httpURLConnection6.setDoOutput(true);
                                int outDataLength = this.mSendDataProcessor.getOutDataLength(this.mData);
                                if (outDataLength < 0) {
                                    httpURLConnection6.setChunkedStreamingMode(0);
                                } else {
                                    httpURLConnection6.setFixedLengthStreamingMode(outDataLength);
                                }
                                httpURLConnection6.setRequestProperty("Content-Type", this.mSendDataProcessor.getOutDataContentType(this.mData));
                                OutputStream outputStream = httpURLConnection6.getOutputStream();
                                try {
                                    this.mSendDataProcessor.processOutData(this.mData, outputStream);
                                    outputStream.close();
                                } catch (Throwable th) {
                                    outputStream.close();
                                    throw th;
                                }
                            }
                            this.mResponse.stateCode = httpURLConnection6.getResponseCode();
                            this.mResponse.stateMessage = httpURLConnection6.getResponseMessage();
                            this.mResponse.contentLength = httpURLConnection6.getContentLength();
                            this.mResponse.headers = httpURLConnection6.getHeaderFields();
                            long date2 = httpURLConnection6.getDate();
                            HttpResponse httpResponse2 = this.mResponse;
                            if (date2 != 0) {
                                date = new Date(date2);
                            }
                            httpResponse2.date = date;
                            if (this.mResponse.stateCode == 200) {
                                InputStream inputStream = httpURLConnection6.getInputStream();
                                try {
                                    if (this.mReceiveDataProcessor == null) {
                                        this.mReceiveDataProcessor = this.this$0.mDataProcessorFactor.getReceiveDataProcessor(this.mResponse.headers, inputStream);
                                    }
                                    HttpResponse httpResponse3 = this.mResponse;
                                    httpResponse3.content = this.mReceiveDataProcessor.processInData(httpResponse3.headers, inputStream);
                                    inputStream.close();
                                } catch (Throwable th2) {
                                    inputStream.close();
                                    throw th2;
                                }
                            }
                            httpURLConnection2 = httpURLConnection6;
                        } catch (MalformedURLException e) {
                            e = e;
                            httpURLConnection4 = httpURLConnection6;
                            this.mResponse.error = e;
                            XLogger.loge(e);
                            if (httpURLConnection4 != null) {
                                httpURLConnection4.disconnect();
                            }
                            this.mResponse.ctx = this.mCtx;
                            xCallback = this.mCallback;
                            if (xCallback != null) {
                            }
                            jCurrentTimeMillis = System.currentTimeMillis() - jCurrentTimeMillis2;
                            if (this.mResponse.error == null) {
                                MiCloudNetEventStatInjector.getInstance().addNetFailedEvent(new NetFailedStatParam(this.mUrl, jCurrentTimeMillis2, jCurrentTimeMillis, this.mResponse.error, 0));
                                return;
                            }
                            MiCloudNetEventStatInjector miCloudNetEventStatInjector = MiCloudNetEventStatInjector.getInstance();
                            String str = this.mUrl;
                            HttpResponse httpResponse4 = this.mResponse;
                            miCloudNetEventStatInjector.addNetSuccessEvent(new NetSuccessStatParam(str, jCurrentTimeMillis2, jCurrentTimeMillis, httpResponse4.contentLength, httpResponse4.stateCode, 0));
                        } catch (IOException e2) {
                            e = e2;
                            httpURLConnection5 = httpURLConnection6;
                            this.mResponse.error = e;
                            XLogger.loge(e);
                            if (httpURLConnection5 != null) {
                                httpURLConnection5.disconnect();
                            }
                            this.mResponse.ctx = this.mCtx;
                            xCallback = this.mCallback;
                            if (xCallback != null) {
                            }
                            jCurrentTimeMillis = System.currentTimeMillis() - jCurrentTimeMillis2;
                            if (this.mResponse.error == null) {
                                MiCloudNetEventStatInjector.getInstance().addNetFailedEvent(new NetFailedStatParam(this.mUrl, jCurrentTimeMillis2, jCurrentTimeMillis, this.mResponse.error, 0));
                                return;
                            }
                            MiCloudNetEventStatInjector miCloudNetEventStatInjector2 = MiCloudNetEventStatInjector.getInstance();
                            String str2 = this.mUrl;
                            HttpResponse httpResponse5 = this.mResponse;
                            miCloudNetEventStatInjector2.addNetSuccessEvent(new NetSuccessStatParam(str2, jCurrentTimeMillis2, jCurrentTimeMillis, httpResponse5.contentLength, httpResponse5.stateCode, 0));
                        } catch (DataConversionException e3) {
                            e = e3;
                            httpURLConnection = httpURLConnection6;
                            this.mResponse.error = e;
                            XLogger.loge(e);
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            this.mResponse.ctx = this.mCtx;
                            xCallback = this.mCallback;
                            if (xCallback != null) {
                            }
                            jCurrentTimeMillis = System.currentTimeMillis() - jCurrentTimeMillis2;
                            if (this.mResponse.error == null) {
                                MiCloudNetEventStatInjector.getInstance().addNetFailedEvent(new NetFailedStatParam(this.mUrl, jCurrentTimeMillis2, jCurrentTimeMillis, this.mResponse.error, 0));
                                return;
                            }
                            MiCloudNetEventStatInjector miCloudNetEventStatInjector3 = MiCloudNetEventStatInjector.getInstance();
                            String str3 = this.mUrl;
                            HttpResponse httpResponse6 = this.mResponse;
                            miCloudNetEventStatInjector3.addNetSuccessEvent(new NetSuccessStatParam(str3, jCurrentTimeMillis2, jCurrentTimeMillis, httpResponse6.contentLength, httpResponse6.stateCode, 0));
                        } catch (Throwable th3) {
                            th = th3;
                            httpURLConnection3 = httpURLConnection6;
                            if (httpURLConnection3 != null) {
                                httpURLConnection3.disconnect();
                            }
                            this.mResponse.ctx = this.mCtx;
                            XCallback xCallback2 = this.mCallback;
                            if (xCallback2 != null) {
                                ((IResponseHandler) xCallback2.asInterface()).handleHttpResponse(this.mResponse);
                            }
                            throw th;
                        }
                    }
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    this.mResponse.ctx = this.mCtx;
                    xCallback = this.mCallback;
                    if (xCallback != null) {
                        ((IResponseHandler) xCallback.asInterface()).handleHttpResponse(this.mResponse);
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (MalformedURLException e4) {
                e = e4;
            } catch (IOException e5) {
                e = e5;
            } catch (DataConversionException e6) {
                e = e6;
            }
            jCurrentTimeMillis = System.currentTimeMillis() - jCurrentTimeMillis2;
            if (this.mResponse.error == null) {
                MiCloudNetEventStatInjector.getInstance().addNetFailedEvent(new NetFailedStatParam(this.mUrl, jCurrentTimeMillis2, jCurrentTimeMillis, this.mResponse.error, 0));
                return;
            }
            MiCloudNetEventStatInjector miCloudNetEventStatInjector4 = MiCloudNetEventStatInjector.getInstance();
            String str4 = this.mUrl;
            HttpResponse httpResponse7 = this.mResponse;
            miCloudNetEventStatInjector4.addNetSuccessEvent(new NetSuccessStatParam(str4, jCurrentTimeMillis2, jCurrentTimeMillis, httpResponse7.contentLength, httpResponse7.stateCode, 0));
        }

        public final void prepareConn(URLConnection uRLConnection) {
            uRLConnection.setConnectTimeout(30000);
            uRLConnection.setReadTimeout(30000);
            if (this.this$0.mUserAgentNameProvider != null) {
                String userAgent = this.this$0.mUserAgentNameProvider.getUserAgent();
                if (TextUtils.isEmpty(userAgent)) {
                    return;
                }
                uRLConnection.setRequestProperty("User-Agent", userAgent);
            }
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            doHttpRequest();
            this.this$0.finishTask(this);
        }

        public final void setRequestHeader(HttpURLConnection httpURLConnection, Map map) {
            if (map == null) {
                return;
            }
            for (String str : map.keySet()) {
                httpURLConnection.setRequestProperty(str, TextUtils.join(", ", ((List) map.get(str)).toArray(new String[0])));
            }
        }
    }

    public static class HttpResponse {
        public Object content;
        public long contentLength;
        public Object ctx;
        public Date date;
        public Exception error;
        public Map headers;
        public int stateCode;
        public String stateMessage;

        public String toString() {
            if (this.error != null) {
                return "Error: \n" + this.error.toString() + "\n";
            }
            Integer numValueOf = Integer.valueOf(this.stateCode);
            String str = this.stateMessage;
            Map map = this.headers;
            Object obj = this.content;
            return String.format("%s %s \n%s \n%s:%s", numValueOf, str, map, obj == null ? null : obj.getClass(), this.content);
        }
    }

    public interface IReceiveDataProcessor {
        Object processInData(Map map, InputStream inputStream);
    }

    public interface IResponseHandler {
        void handleHttpResponse(HttpResponse httpResponse);
    }

    public interface ISendDataProcessor {
        String getOutDataContentType(Object obj);

        int getOutDataLength(Object obj);

        void processOutData(Object obj, OutputStream outputStream);
    }

    public interface IUserAgentNameProvider {
        String getUserAgent();
    }

    public final synchronized void finishTask(HttpRequest httpRequest) {
        int i = this.mRunningTaskCount - 1;
        this.mRunningTaskCount = i;
        XLogger.log("Task--", Integer.valueOf(i));
        scheduleTasksLocked();
    }

    public final void scheduleTasksLocked() {
        if (this.mRunningTaskCount >= this.mMaxRuningTaskCount || this.mPendingTasks.isEmpty()) {
            return;
        }
        while (this.mRunningTaskCount < this.mMaxRuningTaskCount && !this.mPendingTasks.isEmpty()) {
            new Thread((Runnable) this.mPendingTasks.getFirst()).start();
            this.mPendingTasks.removeFirst();
            this.mRunningTaskCount++;
        }
        XLogger.log("task++", Integer.valueOf(this.mRunningTaskCount));
    }
}
