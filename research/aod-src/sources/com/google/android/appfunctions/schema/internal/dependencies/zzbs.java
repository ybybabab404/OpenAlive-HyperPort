package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzbs {
    public static final zzbr zza;
    public static final zzbr zzb;

    static {
        zzbr zzbrVar;
        Class cls = zzf.zzb;
        try {
            zzbrVar = (zzbr) Class.forName("com.google.protobuf.ListFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzbrVar = null;
        }
        zza = zzbrVar;
        zzb = new zzbr();
    }

    public static zzbr zza() {
        return zza;
    }

    public static zzbr zzb() {
        return zzb;
    }
}
