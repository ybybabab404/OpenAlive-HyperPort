package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzca {
    public static final zzbz zza;
    public static final zzbz zzb;

    static {
        zzbz zzbzVar;
        Class cls = zzf.zzb;
        try {
            zzbzVar = (zzbz) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzbzVar = null;
        }
        zza = zzbzVar;
        zzb = new zzbz();
    }

    public static zzbz zza() {
        return zza;
    }

    public static zzbz zzb() {
        return zzb;
    }
}
