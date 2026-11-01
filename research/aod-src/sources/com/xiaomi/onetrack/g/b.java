package com.xiaomi.onetrack.g;

import android.text.TextUtils;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.d.f;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.s;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class b {
    public static final int a = 30000;
    public static final int b = 30000;
    public static final String c = "OT_SID";
    public static final String d = "OT_ts";
    public static final String e = "OT_net";
    public static final String f = "OT_sender";
    public static final String g = "OT_protocol";
    public static final String h = "Connection";
    public static final int i = 60000;
    public static final String j = a();
    private static String k = "HttpUtil";
    private static final String l = "GET";
    private static final String m = "POST";
    private static final String n = "&";
    private static final String o = "=";
    private static final String p = "UTF-8";
    private static final int q = 3;

    private b() {
    }

    public static d a(String str, String str2, String str3) throws Throwable {
        OutputStream outputStream;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        OutputStream outputStream2;
        InputStream inputStream2 = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setConnectTimeout(60000);
                httpURLConnection.setReadTimeout(60000);
                httpURLConnection.setRequestMethod(m);
                httpURLConnection.setRequestProperty("accept", "*/*");
                httpURLConnection.setRequestProperty("connection", "Keep-Alive");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Authorization", str3);
                httpURLConnection.setDoOutput(true);
                byte[] bytes = str2.getBytes(p);
                outputStream = httpURLConnection.getOutputStream();
                try {
                    outputStream.write(bytes, 0, bytes.length);
                    outputStream.flush();
                    int responseCode = httpURLConnection.getResponseCode();
                    d dVar = new d();
                    dVar.d = responseCode;
                    InputStream errorStream = responseCode != 200 ? httpURLConnection.getErrorStream() : httpURLConnection.getInputStream();
                    try {
                        dVar.a(new String(p.b(errorStream), p));
                        if (dVar.d == 200) {
                            s.a(k, "POST 成功 publishResponse:" + dVar.toString());
                        } else {
                            s.b(k, "POST 失败 publishResponse:" + dVar.toString());
                        }
                        p.a(errorStream);
                        p.a(outputStream);
                        p.a(httpURLConnection);
                        return dVar;
                    } catch (Exception e2) {
                        inputStream = errorStream;
                        e = e2;
                        outputStream2 = outputStream;
                        try {
                            s.b(k, "HttpUtils post url:" + str + " ," + e.getMessage());
                            p.a(inputStream);
                            p.a(outputStream2);
                            p.a(httpURLConnection);
                            return null;
                        } catch (Throwable th) {
                            th = th;
                            inputStream2 = inputStream;
                            outputStream = outputStream2;
                            p.a(inputStream2);
                            p.a(outputStream);
                            p.a(httpURLConnection);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        inputStream2 = errorStream;
                        p.a(inputStream2);
                        p.a(outputStream);
                        p.a(httpURLConnection);
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                    outputStream2 = outputStream;
                    inputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Exception e4) {
                e = e4;
                inputStream = null;
                outputStream2 = null;
            } catch (Throwable th4) {
                th = th4;
                outputStream = null;
            }
        } catch (Exception e5) {
            e = e5;
            inputStream = null;
            outputStream2 = null;
            httpURLConnection = null;
        } catch (Throwable th5) {
            th = th5;
            outputStream = null;
            httpURLConnection = null;
        }
    }

    private static String a() {
        try {
            return new String("miui_sdkconfig_jafej!@#)(*e@!#".getBytes(), p);
        } catch (Exception e2) {
            s.b(k, e2.getMessage());
            return "";
        }
    }

    public static String a(String str) throws IOException {
        return a(str, (Map<String, String>) null, false);
    }

    public static String a(String str, String str2, Map<String, String> map) throws Throwable {
        String strA;
        OutputStream outputStream;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        InputStream inputStream2 = null;
        if (map == null) {
            strA = null;
        } else {
            try {
                strA = a(map, true);
            } catch (Exception e2) {
                e = e2;
                inputStream = null;
                outputStream = null;
                httpURLConnection = null;
                s.b(k, "HttpUtils post token:" + str2 + " ," + e.getMessage());
                p.a(inputStream);
                p.a(outputStream);
                p.a(httpURLConnection);
                return null;
            } catch (Throwable th) {
                th = th;
                outputStream = null;
                httpURLConnection = null;
                p.a(inputStream2);
                p.a(outputStream);
                p.a(httpURLConnection);
                throw th;
            }
        }
        httpURLConnection = (HttpURLConnection) new URL(str2).openConnection();
        try {
            httpURLConnection.setConnectTimeout(60000);
            httpURLConnection.setReadTimeout(60000);
            httpURLConnection.setRequestMethod(m);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + str);
            httpURLConnection.setDoOutput(true);
            byte[] bytes = strA.getBytes(p);
            outputStream = httpURLConnection.getOutputStream();
            try {
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                int responseCode = httpURLConnection.getResponseCode();
                s.a(k, "postGetToken responseData statusCode:" + responseCode);
                inputStream = httpURLConnection.getInputStream();
                try {
                    try {
                        String str3 = new String(p.b(inputStream), p);
                        p.a(inputStream);
                        p.a(outputStream);
                        p.a(httpURLConnection);
                        return str3;
                    } catch (Exception e3) {
                        e = e3;
                        s.b(k, "HttpUtils post token:" + str2 + " ," + e.getMessage());
                        p.a(inputStream);
                        p.a(outputStream);
                        p.a(httpURLConnection);
                        return null;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    inputStream2 = inputStream;
                    p.a(inputStream2);
                    p.a(outputStream);
                    p.a(httpURLConnection);
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                inputStream = null;
            } catch (Throwable th3) {
                th = th3;
                p.a(inputStream2);
                p.a(outputStream);
                p.a(httpURLConnection);
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            inputStream = null;
            outputStream = null;
        } catch (Throwable th4) {
            th = th4;
            outputStream = null;
        }
    }

    private static String a(String str, String str2, Map<String, String> map, boolean z) throws Throwable {
        String strA;
        OutputStream outputStream;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        String str3;
        InputStream inputStream2 = null;
        if (map == null) {
            strA = null;
        } else {
            try {
                strA = a(map, z);
            } catch (Exception e2) {
                e = e2;
                outputStream = null;
                httpURLConnection = null;
                inputStream = null;
                s.b(k, "HttpUtils callRequest failed, url: " + str2 + ", e: ", e);
                p.a(inputStream);
                p.a(outputStream);
                p.a(httpURLConnection);
                return null;
            } catch (Throwable th) {
                th = th;
                outputStream = null;
                httpURLConnection = null;
                p.a(inputStream2);
                p.a(outputStream);
                p.a(httpURLConnection);
                throw th;
            }
        }
        if (!l.equals(str) || strA == null) {
            str3 = str2;
        } else {
            str3 = str2 + "?" + strA;
        }
        httpURLConnection = (HttpURLConnection) new URL(str3).openConnection();
        try {
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(30000);
            a(httpURLConnection);
            try {
                try {
                    if (!l.equals(str)) {
                        if (m.equals(str) && strA != null) {
                            httpURLConnection.setRequestMethod(m);
                            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            httpURLConnection.setDoOutput(true);
                            byte[] bytes = strA.getBytes(p);
                            outputStream = httpURLConnection.getOutputStream();
                            try {
                                outputStream.write(bytes, 0, bytes.length);
                                outputStream.flush();
                            } catch (Exception e3) {
                                e = e3;
                                inputStream = null;
                                s.b(k, "HttpUtils callRequest failed, url: " + str2 + ", e: ", e);
                                p.a(inputStream);
                                p.a(outputStream);
                                p.a(httpURLConnection);
                                return null;
                            } catch (Throwable th2) {
                                th = th2;
                                p.a(inputStream2);
                                p.a(outputStream);
                                p.a(httpURLConnection);
                                throw th;
                            }
                        }
                        int responseCode = httpURLConnection.getResponseCode();
                        inputStream = httpURLConnection.getInputStream();
                        byte[] bArrB = p.b(inputStream);
                        s.a(k, String.format("HttpUtils POST 上传成功 url: %s, code: %s", str2, Integer.valueOf(responseCode)));
                        String str4 = new String(bArrB, p);
                        p.a(inputStream);
                        p.a(outputStream);
                        p.a(httpURLConnection);
                        return str4;
                    }
                    httpURLConnection.setRequestMethod(l);
                    byte[] bArrB2 = p.b(inputStream);
                    s.a(k, String.format("HttpUtils POST 上传成功 url: %s, code: %s", str2, Integer.valueOf(responseCode)));
                    String str5 = new String(bArrB2, p);
                    p.a(inputStream);
                    p.a(outputStream);
                    p.a(httpURLConnection);
                    return str5;
                } catch (Exception e4) {
                    e = e4;
                    s.b(k, "HttpUtils callRequest failed, url: " + str2 + ", e: ", e);
                    p.a(inputStream);
                    p.a(outputStream);
                    p.a(httpURLConnection);
                    return null;
                }
            } catch (Throwable th3) {
                th = th3;
                inputStream2 = inputStream;
                p.a(inputStream2);
                p.a(outputStream);
                p.a(httpURLConnection);
                throw th;
            }
            outputStream = null;
            int responseCode2 = httpURLConnection.getResponseCode();
            inputStream = httpURLConnection.getInputStream();
        } catch (Exception e5) {
            e = e5;
            outputStream = null;
            inputStream = null;
        } catch (Throwable th4) {
            th = th4;
            outputStream = null;
        }
    }

    public static String a(String str, Map<String, String> map) throws IOException {
        return a(str, map, true);
    }

    public static String a(String str, Map<String, String> map, boolean z) throws IOException {
        return a(l, str, map, z);
    }

    public static String a(String str, byte[] bArr) throws IOException {
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        InputStream inputStream;
        s.a(k, "doPost url=" + str + ", len=" + bArr.length);
        InputStream inputStream2 = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setConnectTimeout(30000);
                httpURLConnection.setReadTimeout(30000);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod(m);
                a(httpURLConnection);
                httpURLConnection.setRequestProperty("Content-Type", "application/octet-stream");
                httpURLConnection.setRequestProperty(c, f.a().b()[1]);
                httpURLConnection.setRequestProperty(d, Long.toString(System.currentTimeMillis()));
                httpURLConnection.setRequestProperty(e, c.a(com.xiaomi.onetrack.f.a.b()).toString());
                httpURLConnection.setRequestProperty(f, com.xiaomi.onetrack.f.a.e());
                httpURLConnection.setRequestProperty(g, "3.0");
                outputStream = httpURLConnection.getOutputStream();
                try {
                    outputStream.write(bArr, 0, bArr.length);
                    outputStream.flush();
                    int responseCode = httpURLConnection.getResponseCode();
                    inputStream = httpURLConnection.getInputStream();
                    try {
                        try {
                            byte[] bArrB = p.b(inputStream);
                            s.a(k, String.format("HttpUtils POST 上传成功 url: %s, code: %s", str, Integer.valueOf(responseCode)));
                            String str2 = new String(bArrB, p);
                            p.a(inputStream);
                            p.a(outputStream);
                            p.a(httpURLConnection);
                            return str2;
                        } catch (IOException e2) {
                            e = e2;
                            s.b(k, String.format("HttpUtils POST 上传失败, url: %s, error: %s", str, e.getMessage()));
                            p.a(inputStream);
                            p.a(outputStream);
                            p.a(httpURLConnection);
                            return null;
                        }
                    } catch (Throwable th) {
                        th = th;
                        inputStream2 = inputStream;
                        p.a(inputStream2);
                        p.a(outputStream);
                        p.a(httpURLConnection);
                        throw th;
                    }
                } catch (IOException e3) {
                    e = e3;
                    inputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    p.a(inputStream2);
                    p.a(outputStream);
                    p.a(httpURLConnection);
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
                outputStream = null;
                inputStream = outputStream;
                s.b(k, String.format("HttpUtils POST 上传失败, url: %s, error: %s", str, e.getMessage()));
                p.a(inputStream);
                p.a(outputStream);
                p.a(httpURLConnection);
                return null;
            } catch (Throwable th3) {
                th = th3;
                outputStream = null;
            }
        } catch (IOException e5) {
            e = e5;
            httpURLConnection = null;
            outputStream = null;
        } catch (Throwable th4) {
            th = th4;
            httpURLConnection = null;
            outputStream = null;
        }
    }

    public static String a(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null) {
            ArrayList<String> arrayList = new ArrayList(map.keySet());
            Collections.sort(arrayList);
            for (String str : arrayList) {
                if (!TextUtils.isEmpty(str)) {
                    sb.append(str);
                    sb.append(map.get(str));
                }
            }
        }
        sb.append(j);
        return com.xiaomi.onetrack.d.d.c(sb.toString());
    }

    private static String a(Map<String, String> map, boolean z) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            try {
                if (!TextUtils.isEmpty(entry.getKey())) {
                    if (sb.length() > 0) {
                        sb.append(n);
                    }
                    sb.append(URLEncoder.encode(entry.getKey(), p));
                    sb.append(o);
                    sb.append(URLEncoder.encode(entry.getValue() == null ? "null" : entry.getValue(), p));
                }
            } catch (UnsupportedEncodingException unused) {
                s.b(k, "format params failed");
            }
        }
        if (z) {
            String strA = a(map);
            if (sb.length() > 0) {
                sb.append(n);
            }
            sb.append(URLEncoder.encode("sign", p));
            sb.append(o);
            sb.append(URLEncoder.encode(strA, p));
        }
        return sb.toString();
    }

    private static void a(HttpURLConnection httpURLConnection) {
        OneTrack.HttpRequestProperty httpReqPropConnection;
        if (httpURLConnection == null || (httpReqPropConnection = OneTrack.getHttpReqPropConnection()) == null) {
            return;
        }
        try {
            s.a(k, String.format("setCustomRequestProperty->connection: %s", httpReqPropConnection.getType()));
            httpURLConnection.setRequestProperty(h, httpReqPropConnection.getType());
        } catch (Exception e2) {
            s.b(k, "setCustomRequestProperty error: " + e2.getMessage());
        }
    }

    public static boolean a(com.xiaomi.onetrack.a.b.a aVar) throws Throwable {
        InputStream inputStream;
        if (aVar == null || TextUtils.isEmpty(aVar.c())) {
            s.a(k, "doGetAdMonitor response true ,monitor or url is null");
            return true;
        }
        String strC = aVar.c();
        String str = "";
        int i2 = 0;
        int responseCode = 0;
        HttpURLConnection httpURLConnection = null;
        while (i2 < 3) {
            try {
                try {
                    if (responseCode / 100 == 3) {
                        i2++;
                        strC = httpURLConnection.getHeaderField("Location");
                        s.a(k, "redirect url is:" + strC);
                    }
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(strC).openConnection();
                    try {
                        httpURLConnection2.setInstanceFollowRedirects(false);
                        httpURLConnection2.setRequestMethod(l);
                        httpURLConnection2.setConnectTimeout(30000);
                        httpURLConnection2.setReadTimeout(30000);
                        responseCode = httpURLConnection2.getResponseCode();
                        s.a(k, "AdMonitor get 请求url:" + strC + "_ResponseCode：" + responseCode);
                        if (responseCode / 100 != 5 && responseCode / 100 != 4 && responseCode / 100 != 3) {
                            p.a(httpURLConnection2);
                            return true;
                        }
                        try {
                            InputStream errorStream = httpURLConnection2.getErrorStream();
                            if (errorStream != null) {
                                str = new String(p.b(errorStream), p);
                            }
                        } catch (Throwable th) {
                            s.a(k, "doGetAdMonitor getErrorStream Throwable：" + th.getMessage());
                        }
                        try {
                            if (!ac.b(str) && (inputStream = httpURLConnection2.getInputStream()) != null) {
                                str = new String(p.b(inputStream), p);
                            }
                        } catch (Throwable th2) {
                            s.a(k, "doGetAdMonitor error getInputStream Throwable：" + th2.getMessage());
                        }
                        s.a(k, "doGetAdMonitor get 请求 errBody：" + str);
                        com.xiaomi.onetrack.a.a.a().a(aVar, responseCode, str, (Throwable) null);
                        if (responseCode / 100 != 3) {
                            p.a(httpURLConnection2);
                            return false;
                        }
                        httpURLConnection = httpURLConnection2;
                    } catch (ProtocolException e2) {
                        e = e2;
                        httpURLConnection = httpURLConnection2;
                        com.xiaomi.onetrack.a.a.a().a(aVar, responseCode, str, e);
                        if (TextUtils.isEmpty(e.getMessage()) && e.getMessage().contains("200 OK")) {
                            s.a(k, "response code is 200, bug status line is invalid.");
                            p.a(httpURLConnection);
                            return true;
                        }
                        p.a(httpURLConnection);
                    } catch (Exception e3) {
                        e = e3;
                        httpURLConnection = httpURLConnection2;
                        s.b(k, "HttpUtils doGetAdMonitor 上传异常:" + e.getMessage());
                        com.xiaomi.onetrack.a.a.a().a(aVar, responseCode, str, e);
                        p.a(httpURLConnection);
                    } catch (Throwable th3) {
                        th = th3;
                        httpURLConnection = httpURLConnection2;
                        p.a(httpURLConnection);
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (ProtocolException e4) {
                e = e4;
            } catch (Exception e5) {
                e = e5;
            }
        }
        s.a(k, "redirectCount >= 3, return true");
        p.a(httpURLConnection);
        return true;
    }

    public static String b(String str, Map<String, String> map) throws IOException {
        return b(str, map, true);
    }

    public static String b(String str, Map<String, String> map, boolean z) throws IOException {
        return a(m, str, map, z);
    }
}
