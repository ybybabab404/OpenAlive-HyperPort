package com.xiaomi.onetrack.c;

import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class x {
    private long a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private byte[] g;
    private int h;
    private Map<String, String> i;
    private long j;

    public x(long j, String str, String str2, String str3, String str4, String str5, byte[] bArr, Map<String, String> map, long j2, int i) {
        this.a = j;
        this.e = str;
        this.f = str2;
        this.b = str3;
        this.c = str4;
        this.d = str5;
        this.g = bArr;
        this.i = map;
        this.j = j2;
        this.h = i;
    }

    public long a() {
        return this.a;
    }

    public void a(int i) {
        this.h = i;
    }

    public void a(long j) {
        this.a = j;
    }

    public void a(String str) {
        this.b = str;
    }

    public void a(Map<String, String> map) {
        this.i = map;
    }

    public void a(byte[] bArr) {
        this.g = bArr;
    }

    public String b() {
        return this.b;
    }

    public void b(long j) {
        this.j = j;
    }

    public void b(String str) {
        this.c = str;
    }

    public String c() {
        return this.c;
    }

    public void c(String str) {
        this.e = str;
    }

    public String d() {
        return this.e;
    }

    public void d(String str) {
        this.f = str;
    }

    public String e() {
        return this.f;
    }

    public void e(String str) {
        this.d = str;
    }

    public byte[] f() {
        return this.g;
    }

    public int g() {
        return this.h;
    }

    public Map<String, String> h() {
        return this.i;
    }

    public long i() {
        return this.j;
    }

    public String j() {
        return this.d;
    }

    public String toString() {
        return "TransformDBData{mId=" + this.a + ", mAppId='" + this.b + "', mPackageName='" + this.c + "', mEventName='" + this.d + "', mProjectID='" + this.e + "', mTopic='" + this.f + "', mData='" + this.g + "', mPriority='" + this.h + "', mAttributes=" + this.i + ", mTimestamp=" + this.j + '}';
    }
}
