package com.xiaomi.micloudsdk.request;

import java.util.Locale;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: classes2.dex */
public class CloudHttpClient implements HttpClient {
    public HttpClient mProxy;

    public final void addHeader(HttpRequest httpRequest) {
        httpRequest.addHeader("Accept-Language", Locale.getDefault().toString());
    }

    @Override // org.apache.http.client.HttpClient
    public Object execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler responseHandler) {
        addHeader(httpRequest);
        return this.mProxy.execute(httpHost, httpRequest, responseHandler);
    }

    @Override // org.apache.http.client.HttpClient
    public Object execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler responseHandler, HttpContext httpContext) {
        addHeader(httpRequest);
        return this.mProxy.execute(httpHost, httpRequest, responseHandler, httpContext);
    }

    @Override // org.apache.http.client.HttpClient
    public Object execute(HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
        addHeader(httpUriRequest);
        return this.mProxy.execute(httpUriRequest, responseHandler);
    }

    @Override // org.apache.http.client.HttpClient
    public Object execute(HttpUriRequest httpUriRequest, ResponseHandler responseHandler, HttpContext httpContext) {
        addHeader(httpUriRequest);
        return this.mProxy.execute(httpUriRequest, responseHandler, httpContext);
    }

    @Override // org.apache.http.client.HttpClient
    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) {
        addHeader(httpRequest);
        return this.mProxy.execute(httpHost, httpRequest);
    }

    @Override // org.apache.http.client.HttpClient
    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {
        addHeader(httpRequest);
        return this.mProxy.execute(httpHost, httpRequest);
    }

    @Override // org.apache.http.client.HttpClient
    public HttpResponse execute(HttpUriRequest httpUriRequest) {
        addHeader(httpUriRequest);
        return this.mProxy.execute(httpUriRequest);
    }

    @Override // org.apache.http.client.HttpClient
    public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) {
        addHeader(httpUriRequest);
        return this.mProxy.execute(httpUriRequest, httpContext);
    }

    @Override // org.apache.http.client.HttpClient
    public ClientConnectionManager getConnectionManager() {
        return this.mProxy.getConnectionManager();
    }

    @Override // org.apache.http.client.HttpClient
    public HttpParams getParams() {
        return this.mProxy.getParams();
    }
}
