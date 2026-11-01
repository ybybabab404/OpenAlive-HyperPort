package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzco {
    public static final zzco zza = new zzco();
    public final ConcurrentHashMap zzc = new ConcurrentHashMap();
    public final zzbw zzb = new zzbw();

    public static zzco zza() {
        return zza;
    }

    public final zzcs zzb(Class cls) {
        zzbi.zza(cls, "messageType");
        ConcurrentHashMap concurrentHashMap = this.zzc;
        zzcs zzcsVar = (zzcs) concurrentHashMap.get(cls);
        if (zzcsVar != null) {
            return zzcsVar;
        }
        zzcs zzcsVarZza = this.zzb.zza(cls);
        zzcs zzcsVar2 = (zzcs) concurrentHashMap.putIfAbsent(cls, zzcsVarZza);
        return zzcsVar2 != null ? zzcsVar2 : zzcsVarZza;
    }
}
