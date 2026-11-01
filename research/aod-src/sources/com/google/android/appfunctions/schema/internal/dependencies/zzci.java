package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzci implements zzcs {
    public final zzce zza;
    public final zzdc zzb;
    public final boolean zzc;
    public final zzaj zzd;

    public zzci(zzdc zzdcVar, zzaj zzajVar, zzce zzceVar) {
        this.zzb = zzdcVar;
        this.zzc = zzajVar.zza(zzceVar);
        this.zzd = zzajVar;
        this.zza = zzceVar;
    }

    public static zzci zzh(zzdc zzdcVar, zzaj zzajVar, zzce zzceVar) {
        return new zzci(zzdcVar, zzajVar, zzceVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final boolean zzb(Object obj, Object obj2) {
        zzdc zzdcVar = this.zzb;
        if (!zzdcVar.zzj(obj).equals(zzdcVar.zzj(obj2))) {
            return false;
        }
        if (!this.zzc) {
            return true;
        }
        zzaj zzajVar = this.zzd;
        return zzajVar.zzb(obj).equals(zzajVar.zzb(obj2));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final int zzc(Object obj) {
        int iHashCode = this.zzb.zzj(obj).hashCode();
        return this.zzc ? (iHashCode * 53) + this.zzd.zzb(obj).zza.hashCode() : iHashCode;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final int zze(Object obj) {
        zzdc zzdcVar = this.zzb;
        int iZzr = zzdcVar.zzr(zzdcVar.zzj(obj));
        return this.zzc ? iZzr + this.zzd.zzb(obj).zzi() : iZzr;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzf(Object obj, zzdt zzdtVar) {
        Iterator itZzc = this.zzd.zzb(obj).zzc();
        while (itZzc.hasNext()) {
            Map.Entry entry = (Map.Entry) itZzc.next();
            zzam zzamVar = (zzam) entry.getKey();
            if (zzamVar.zzc() != zzds.MESSAGE || zzamVar.zzd() || zzamVar.zze()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (entry instanceof zzbm) {
                zzdtVar.zzv(zzamVar.zza(), ((zzbm) entry).zza().zzc());
            } else {
                zzdtVar.zzv(zzamVar.zza(), entry.getValue());
            }
        }
        zzdc zzdcVar = this.zzb;
        zzdcVar.zzp(zzdcVar.zzj(obj), zzdtVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzk(Object obj) {
        this.zzb.zzm(obj);
        this.zzd.zzd(obj);
    }
}
