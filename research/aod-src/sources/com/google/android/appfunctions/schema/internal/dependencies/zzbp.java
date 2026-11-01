package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
public class zzbp {
    public volatile zzce zza;
    public volatile zzt zzb;
    public volatile boolean zzc;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbp)) {
            return false;
        }
        zzbp zzbpVar = (zzbp) obj;
        zzce zzceVar = this.zza;
        zzce zzceVar2 = zzbpVar.zza;
        if (zzceVar == null && zzceVar2 == null) {
            return zzc().equals(zzbpVar.zzc());
        }
        if (zzceVar != null && zzceVar2 != null) {
            return zzceVar.equals(zzceVar2);
        }
        if (zzceVar != null) {
            zzbpVar.zzd(zzceVar.getDefaultInstanceForType());
            return zzceVar.equals(zzbpVar.zza);
        }
        zzd(zzceVar2.getDefaultInstanceForType());
        return this.zza.equals(zzceVar2);
    }

    public int hashCode() {
        return 1;
    }

    public final zzce zza(zzce zzceVar) {
        zzce zzceVar2 = this.zza;
        this.zzb = null;
        this.zza = zzceVar;
        return zzceVar2;
    }

    public final int zzb() {
        if (this.zzb != null) {
            return this.zzb.zzb();
        }
        if (this.zza != null) {
            return this.zza.getSerializedSize();
        }
        return 0;
    }

    public final zzt zzc() {
        if (this.zzb != null) {
            return this.zzb;
        }
        synchronized (this) {
            try {
                if (this.zzb != null) {
                    return this.zzb;
                }
                if (this.zza == null) {
                    this.zzb = zzt.zza;
                } else {
                    this.zzb = this.zza.toByteString();
                }
                return this.zzb;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void zzd(zzce zzceVar) {
        if (this.zza != null) {
            return;
        }
        synchronized (this) {
            if (this.zza != null) {
                return;
            }
            try {
                this.zza = zzceVar;
                this.zzb = zzt.zza;
            } catch (zzbk unused) {
                this.zzc = true;
                this.zza = zzceVar;
                this.zzb = zzt.zza;
            }
        }
    }
}
