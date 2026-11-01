package cn.kuaipan.android.http.client;

import android.os.SystemClock;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;

/* JADX INFO: loaded from: classes.dex */
public class KscHttpClient extends DefaultHttpClient {
    public volatile LoggingConfiguration curlConfiguration;
    public final boolean mForKssTransmission;
    public final boolean mKeepAlive;
    public final boolean mRequestSentRetryEnabled;

    /* JADX INFO: renamed from: cn.kuaipan.android.http.client.KscHttpClient$1, reason: invalid class name */
    class AnonymousClass1 implements ConnPerRoute {
        @Override // org.apache.http.conn.params.ConnPerRoute
        public int getMaxForRoute(HttpRoute httpRoute) {
            return 32;
        }
    }

    public class CurlLogger implements HttpRequestInterceptor {
        public CurlLogger() {
        }

        public /* synthetic */ CurlLogger(KscHttpClient kscHttpClient, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // org.apache.http.HttpRequestInterceptor
        public void process(HttpRequest httpRequest, HttpContext httpContext) {
            LoggingConfiguration loggingConfiguration = KscHttpClient.this.curlConfiguration;
            boolean z = httpRequest instanceof HttpUriRequest;
            if (z) {
                Log.i("CurlLogger", KscHttpClient.toCurl((HttpUriRequest) httpRequest, false));
            }
            if (loggingConfiguration != null && loggingConfiguration.isLoggable() && z) {
                loggingConfiguration.println(KscHttpClient.toCurl((HttpUriRequest) httpRequest, false));
            }
        }
    }

    public static class LoggingConfiguration {
        public final int level;
        public final String tag;

        public final boolean isLoggable() {
            return Log.isLoggable(this.tag, this.level);
        }

        public final void println(String str) {
            Log.println(this.level, this.tag, str);
        }
    }

    public class TimeMarker implements HttpRequestInterceptor {
        public TimeMarker() {
        }

        public /* synthetic */ TimeMarker(KscHttpClient kscHttpClient, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // org.apache.http.HttpRequestInterceptor
        public void process(HttpRequest httpRequest, HttpContext httpContext) {
            httpContext.setAttribute("ksc.connect_start", Long.valueOf(SystemClock.elapsedRealtime()));
            List linkedList = (List) httpContext.getAttribute("ksc.message_list");
            if (linkedList == null) {
                linkedList = new LinkedList();
                httpContext.setAttribute("ksc.message_list", linkedList);
            }
            linkedList.add(httpRequest);
        }
    }

    public static String toCurl(HttpUriRequest httpUriRequest, boolean z) throws IOException {
        HttpEntity entity;
        StringBuilder sb = new StringBuilder();
        sb.append("curl ");
        for (Header header : httpUriRequest.getAllHeaders()) {
            if (z || (!header.getName().equals("Authorization") && !header.getName().equals("Cookie"))) {
                sb.append("--header \"");
                sb.append(header.toString().trim());
                sb.append("\" ");
            }
        }
        URI uri = httpUriRequest.getURI();
        if (httpUriRequest instanceof RequestWrapper) {
            HttpRequest original = ((RequestWrapper) httpUriRequest).getOriginal();
            if (original instanceof HttpUriRequest) {
                uri = ((HttpUriRequest) original).getURI();
            }
        }
        sb.append("\"");
        sb.append(uri);
        sb.append("\"");
        if ((httpUriRequest instanceof HttpEntityEnclosingRequest) && (entity = ((HttpEntityEnclosingRequest) httpUriRequest).getEntity()) != null && entity.isRepeatable()) {
            if (entity.getContentLength() < 1024) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                entity.writeTo(byteArrayOutputStream);
                String string = byteArrayOutputStream.toString();
                sb.append(" --data-ascii \"");
                sb.append(string);
                sb.append("\"");
            } else {
                sb.append(" [TOO MUCH DATA TO INCLUDE]");
            }
        }
        return sb.toString();
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    public ConnectionReuseStrategy createConnectionReuseStrategy() {
        return this.mKeepAlive ? new DefaultConnectionReuseStrategy() : new NoConnectionReuseStrategy();
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    public BasicHttpProcessor createHttpProcessor() {
        BasicHttpProcessor basicHttpProcessorCreateHttpProcessor;
        if (this.mForKssTransmission) {
            basicHttpProcessorCreateHttpProcessor = new BasicHttpProcessor();
            basicHttpProcessorCreateHttpProcessor.addInterceptor(new RequestDefaultHeaders());
            basicHttpProcessorCreateHttpProcessor.addInterceptor(new RequestContent());
            basicHttpProcessorCreateHttpProcessor.addInterceptor(new RequestTargetHost());
            basicHttpProcessorCreateHttpProcessor.addInterceptor(new RequestConnControl());
            basicHttpProcessorCreateHttpProcessor.addInterceptor(new RequestUserAgent());
        } else {
            basicHttpProcessorCreateHttpProcessor = super.createHttpProcessor();
        }
        AnonymousClass1 anonymousClass1 = null;
        basicHttpProcessorCreateHttpProcessor.addRequestInterceptor(new TimeMarker(this, anonymousClass1));
        basicHttpProcessorCreateHttpProcessor.addRequestInterceptor(new CurlLogger(this, anonymousClass1));
        return basicHttpProcessorCreateHttpProcessor;
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    public HttpRequestRetryHandler createHttpRequestRetryHandler() {
        return new KscHttpRequestRetryHandler(3, this.mRequestSentRetryEnabled, 10000);
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    public HttpRoutePlanner createHttpRoutePlanner() {
        return new KscHttpRoutePlanner(getConnectionManager().getSchemeRegistry());
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    public RedirectHandler createRedirectHandler() {
        return new KscRedirectHandler();
    }

    public void finalize() throws Throwable {
        super.finalize();
    }
}
