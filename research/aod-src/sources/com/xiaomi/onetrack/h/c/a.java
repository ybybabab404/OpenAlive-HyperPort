package com.xiaomi.onetrack.h.c;

import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class a {
    HashMap<String, HashMap<String, Integer>> a;
    private String b;
    private int c;
    private HashMap<String, Integer> d;

    /* JADX INFO: renamed from: com.xiaomi.onetrack.h.c.a$a, reason: collision with other inner class name */
    public static class C0048a {
        private String a;
        private int b;
        private HashMap<String, HashMap<String, Integer>> c;
        private HashMap<String, Integer> d;

        public C0048a a(int i) {
            this.b = i;
            return this;
        }

        public C0048a a(String str) {
            this.a = str;
            return this;
        }

        public C0048a a(HashMap<String, HashMap<String, Integer>> map) {
            this.c = map;
            return this;
        }

        public a a() {
            return new a(this);
        }

        public C0048a b(HashMap<String, Integer> map) {
            this.d = map;
            return this;
        }
    }

    private a(C0048a c0048a) {
        this.b = c0048a.a;
        this.c = c0048a.b;
        this.a = c0048a.c;
        this.d = c0048a.d;
    }

    public String a() {
        return this.b;
    }

    public void a(int i) {
        this.c = i;
    }

    public void a(String str) {
        this.b = str;
    }

    public void a(HashMap<String, HashMap<String, Integer>> map) {
        this.a = map;
    }

    public int b() {
        return this.c;
    }

    public void b(HashMap<String, Integer> map) {
        this.d = map;
    }

    public HashMap<String, HashMap<String, Integer>> c() {
        return this.a;
    }

    public HashMap<String, Integer> d() {
        return this.d;
    }
}
