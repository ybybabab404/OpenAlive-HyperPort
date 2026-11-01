package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzdi extends zzdj {
    public zzdi(Unsafe unsafe) {
        super(unsafe);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdj
    public final void zzb(Object obj, long j, byte b) {
        this.zza.putByte(obj, j, b);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdj
    public final boolean zzc(Object obj, long j) {
        return this.zza.getBoolean(obj, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdj
    public final float zze(Object obj, long j) {
        return this.zza.getFloat(obj, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdj
    public final double zzg(Object obj, long j) {
        return this.zza.getDouble(obj, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdj
    public final boolean zzi() {
        if (!super.zzi()) {
            return false;
        }
        try {
            Class<?> cls = this.zza.getClass();
            Class cls2 = Long.TYPE;
            cls.getMethod("getByte", Object.class, cls2);
            cls.getMethod("putByte", Object.class, cls2, Byte.TYPE);
            cls.getMethod("getBoolean", Object.class, cls2);
            cls.getMethod("putBoolean", Object.class, cls2, Boolean.TYPE);
            cls.getMethod("getFloat", Object.class, cls2);
            cls.getMethod("putFloat", Object.class, cls2, Float.TYPE);
            cls.getMethod("getDouble", Object.class, cls2);
            cls.getMethod("putDouble", Object.class, cls2, Double.TYPE);
            return true;
        } catch (Throwable th) {
            Logger.getLogger(zzdk.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: ".concat(th.toString()));
            return false;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdj
    public final boolean zzj() {
        if (!super.zzj()) {
            return false;
        }
        try {
            Class<?> cls = this.zza.getClass();
            Class cls2 = Long.TYPE;
            cls.getMethod("getByte", cls2);
            cls.getMethod("putByte", cls2, Byte.TYPE);
            cls.getMethod("getInt", cls2);
            cls.getMethod("putInt", cls2, Integer.TYPE);
            cls.getMethod("getLong", cls2);
            cls.getMethod("putLong", cls2, cls2);
            cls.getMethod("copyMemory", cls2, cls2, cls2);
            cls.getMethod("copyMemory", Object.class, cls2, Object.class, cls2, cls2);
            return true;
        } catch (Throwable th) {
            Logger.getLogger(zzdk.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: ".concat(th.toString()));
            return false;
        }
    }
}
