package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzcl {
    public static final zzck zza;
    public static final zzck zzb;

    static {
        zzck zzckVar;
        Class cls = zzf.zzb;
        try {
            zzckVar = (zzck) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzckVar = null;
        }
        zza = zzckVar;
        zzb = new zzck();
    }

    public static zzck zza() {
        return zza;
    }

    public static zzck zzb() {
        return zzb;
    }
}
