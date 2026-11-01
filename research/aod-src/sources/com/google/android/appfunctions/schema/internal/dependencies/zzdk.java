package com.google.android.appfunctions.schema.internal.dependencies;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzdk {
    public static final long zza;
    public static final boolean zzb;
    public static final Unsafe zzc;
    public static final Class zzd;
    public static final boolean zze;
    public static final zzdj zzf;
    public static final boolean zzg;
    public static final boolean zzh;

    static {
        Unsafe unsafeZzr = zzr();
        zzc = unsafeZzr;
        zzd = zzf.zzb();
        boolean zZzs = zzs(Long.TYPE);
        zze = zZzs;
        boolean zZzs2 = zzs(Integer.TYPE);
        zzdj zzdiVar = null;
        if (unsafeZzr != null) {
            if (!zzf.zza()) {
                zzdiVar = new zzdi(unsafeZzr);
            } else if (zZzs) {
                zzdiVar = new zzdh(unsafeZzr);
            } else if (zZzs2) {
                zzdiVar = new zzdg(unsafeZzr);
            }
        }
        zzf = zzdiVar;
        zzg = zzdiVar == null ? false : zzdiVar.zzj();
        zzh = zzdiVar == null ? false : zzdiVar.zzi();
        zza = zzD(byte[].class);
        zzD(boolean[].class);
        zzE(boolean[].class);
        zzD(int[].class);
        zzE(int[].class);
        zzD(long[].class);
        zzE(long[].class);
        zzD(float[].class);
        zzE(float[].class);
        zzD(double[].class);
        zzE(double[].class);
        zzD(Object[].class);
        zzE(Object[].class);
        Field fieldZzF = zzF();
        if (fieldZzF != null && zzdiVar != null) {
            zzdiVar.zza.objectFieldOffset(fieldZzF);
        }
        zzb = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }

    public static int zzD(Class cls) {
        if (zzh) {
            return zzf.zza.arrayBaseOffset(cls);
        }
        return -1;
    }

    public static int zzE(Class cls) {
        if (zzh) {
            return zzf.zza.arrayIndexScale(cls);
        }
        return -1;
    }

    public static Field zzF() {
        Field fieldZzG;
        if (zzf.zza() && (fieldZzG = zzG(Buffer.class, "effectiveDirectAddress")) != null) {
            return fieldZzG;
        }
        Field fieldZzG2 = zzG(Buffer.class, "address");
        if (fieldZzG2 == null || fieldZzG2.getType() != Long.TYPE) {
            return null;
        }
        return fieldZzG2;
    }

    public static Field zzG(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static byte zzH(Object obj, long j) {
        return (byte) ((zzf.zza.getInt(obj, (-4) & j) >>> ((int) (((~j) & 3) << 3))) & 255);
    }

    public static byte zzI(Object obj, long j) {
        return (byte) ((zzf.zza.getInt(obj, (-4) & j) >>> ((int) ((j & 3) << 3))) & 255);
    }

    public static void zzJ(Object obj, long j, byte b) {
        Unsafe unsafe = zzf.zza;
        long j2 = (-4) & j;
        int i = unsafe.getInt(obj, j2);
        int i2 = ((~((int) j)) & 3) << 3;
        unsafe.putInt(obj, j2, ((255 & b) << i2) | (i & (~(255 << i2))));
    }

    public static void zzK(Object obj, long j, byte b) {
        Unsafe unsafe = zzf.zza;
        long j2 = (-4) & j;
        int i = (((int) j) & 3) << 3;
        unsafe.putInt(obj, j2, ((255 & b) << i) | (unsafe.getInt(obj, j2) & (~(255 << i))));
    }

    public static boolean zza() {
        return zzh;
    }

    public static boolean zzb() {
        return zzg;
    }

    public static Object zzc(Class cls) {
        try {
            return zzc.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    public static int zzd(Object obj, long j) {
        return zzf.zza.getInt(obj, j);
    }

    public static long zzf(Object obj, long j) {
        return zzf.zza.getLong(obj, j);
    }

    public static boolean zzh(Object obj, long j) {
        return zzf.zzc(obj, j);
    }

    public static float zzj(Object obj, long j) {
        return zzf.zze(obj, j);
    }

    public static double zzl(Object obj, long j) {
        return zzf.zzg(obj, j);
    }

    public static Object zzn(Object obj, long j) {
        return zzf.zza.getObject(obj, j);
    }

    public static void zzq(byte[] bArr, long j, byte b) {
        zzf.zzb(bArr, zza + j, b);
    }

    public static Unsafe zzr() {
        Unsafe unsafe;
        try {
            unsafe = (Unsafe) AccessController.doPrivileged(new zzdf());
        } catch (Throwable unused) {
            unsafe = null;
        }
        if (unsafe == null) {
            return null;
        }
        try {
            unsafe.arrayBaseOffset(byte[].class);
            return unsafe;
        } catch (Exception unused2) {
            Logger.getLogger(zzdk.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "getUnsafe", "As part of the planned removal, sun.misc.Unsafe is available in the current environment but configured to throw on use. Protobuf will continue without using it, but with slightly reduced performance. --sun-misc-unsafe-memory-access=allow is likely available to opt back in if desired. A later Protobuf version release will stop using sun.misc.Unsafe entirely.");
            return null;
        }
    }

    public static boolean zzs(Class cls) {
        if (!zzf.zza()) {
            return false;
        }
        try {
            Class cls2 = zzd;
            Class cls3 = Boolean.TYPE;
            cls2.getMethod("peekLong", cls, cls3);
            cls2.getMethod("pokeLong", cls, Long.TYPE, cls3);
            Class cls4 = Integer.TYPE;
            cls2.getMethod("pokeInt", cls, cls4, cls3);
            cls2.getMethod("peekInt", cls, cls3);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, cls4, cls4);
            cls2.getMethod("peekByteArray", cls, byte[].class, cls4, cls4);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static /* synthetic */ boolean zzy(Object obj, long j) {
        return zzH(obj, j) != 0;
    }

    public static /* synthetic */ boolean zzz(Object obj, long j) {
        return zzI(obj, j) != 0;
    }
}
