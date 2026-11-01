package com.google.android.appfunctions.schema.internal.dependencies;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
abstract class zzdj {
    public final Unsafe zza;

    public zzdj(Unsafe unsafe) {
        this.zza = unsafe;
    }

    public abstract void zzb(Object obj, long j, byte b);

    public abstract boolean zzc(Object obj, long j);

    public abstract float zze(Object obj, long j);

    public abstract double zzg(Object obj, long j);

    public boolean zzi() {
        try {
            Class<?> cls = this.zza.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("arrayBaseOffset", Class.class);
            cls.getMethod("arrayIndexScale", Class.class);
            Class cls2 = Long.TYPE;
            cls.getMethod("getInt", Object.class, cls2);
            cls.getMethod("putInt", Object.class, cls2, Integer.TYPE);
            cls.getMethod("getLong", Object.class, cls2);
            cls.getMethod("putLong", Object.class, cls2, cls2);
            cls.getMethod("getObject", Object.class, cls2);
            cls.getMethod("putObject", Object.class, cls2, Object.class);
            return true;
        } catch (Throwable th) {
            Logger.getLogger(zzdk.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: ".concat(th.toString()));
            return false;
        }
    }

    public boolean zzj() {
        try {
            Class<?> cls = this.zza.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            return zzdk.zzF() != null;
        } catch (Throwable th) {
            Logger.getLogger(zzdk.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: ".concat(th.toString()));
            return false;
        }
    }
}
