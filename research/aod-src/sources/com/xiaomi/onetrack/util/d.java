package com.xiaomi.onetrack.util;

import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class d {
    private static final String a = "DataConvertUtil";

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v7 */
    public static Object a(byte[] bArr) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        Throwable th;
        ObjectInputStream objectInputStream;
        try {
            try {
                byteArrayInputStream = new ByteArrayInputStream(bArr);
                try {
                    objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    try {
                        Object object = objectInputStream.readObject();
                        p.a((InputStream) byteArrayInputStream);
                        p.a((InputStream) objectInputStream);
                        return object;
                    } catch (Exception e) {
                        e = e;
                        s.b(a, "deserialize error:" + e.getMessage());
                        p.a((InputStream) byteArrayInputStream);
                        p.a((InputStream) objectInputStream);
                        return null;
                    }
                } catch (Exception e2) {
                    e = e2;
                    objectInputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    bArr = 0;
                    p.a((InputStream) byteArrayInputStream);
                    p.a((InputStream) bArr);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e3) {
            e = e3;
            objectInputStream = null;
            byteArrayInputStream = null;
        } catch (Throwable th4) {
            byteArrayInputStream = null;
            th = th4;
            bArr = 0;
        }
    }

    public static String a(Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            try {
                return new JSONObject(map).toString();
            } catch (Exception e) {
                s.b(a, "map to json error:" + e.getMessage());
            }
        }
        return "";
    }

    public static Map<String, String> a(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap();
        }
        HashMap map = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, jSONObject.optString(next));
            }
        } catch (Exception e) {
            s.b(a, "json to map error:" + e.getMessage());
        }
        return map;
    }

    public static byte[] a(Object obj) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        ObjectOutputStream objectOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    try {
                        objectOutputStream.writeObject(obj);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        p.a((OutputStream) byteArrayOutputStream);
                        p.a((OutputStream) objectOutputStream);
                        return byteArray;
                    } catch (IOException e) {
                        e = e;
                        s.b(a, "serialize error:" + e.getMessage());
                        p.a((OutputStream) byteArrayOutputStream);
                        p.a((OutputStream) objectOutputStream);
                        return null;
                    }
                } catch (IOException e2) {
                    e = e2;
                    objectOutputStream = null;
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream2 = byteArrayOutputStream;
                    p.a((OutputStream) byteArrayOutputStream2);
                    p.a((OutputStream) 0);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e3) {
            e = e3;
            byteArrayOutputStream = null;
            objectOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            p.a((OutputStream) byteArrayOutputStream2);
            p.a((OutputStream) 0);
            throw th;
        }
    }
}
