package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzbv implements zzcc {
    public final zzcc[] zza;

    public zzbv(zzcc... zzccVarArr) {
        this.zza = zzccVarArr;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcc
    public final boolean zzb(Class cls) {
        for (int i = 0; i < 2; i++) {
            if (this.zza[i].zzb(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcc
    public final zzcb zzc(Class cls) {
        for (int i = 0; i < 2; i++) {
            zzcc zzccVar = this.zza[i];
            if (zzccVar.zzb(cls)) {
                return zzccVar.zzc(cls);
            }
        }
        throw new UnsupportedOperationException("No factory is available for message type: ".concat(cls.getName()));
    }
}
