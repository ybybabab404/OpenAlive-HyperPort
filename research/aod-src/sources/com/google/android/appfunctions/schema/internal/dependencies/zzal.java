package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzal {
    public static final zzaj zza = new zzak();
    public static final zzaj zzb;

    static {
        zzaj zzajVar;
        Class cls = zzf.zzb;
        try {
            zzajVar = (zzaj) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzajVar = null;
        }
        zzb = zzajVar;
    }

    public static zzaj zza() {
        return zza;
    }

    public static zzaj zzb() {
        zzaj zzajVar = zzb;
        if (zzajVar != null) {
            return zzajVar;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
