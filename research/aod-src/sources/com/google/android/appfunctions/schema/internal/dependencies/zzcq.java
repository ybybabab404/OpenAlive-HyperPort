package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzcq implements zzcb {
    public final zzce zza;
    public final String zzb;
    public final Object[] zzc;
    public final int zzd;

    public zzcq(zzce zzceVar, String str, Object[] objArr) {
        this.zza = zzceVar;
        this.zzb = str;
        this.zzc = objArr;
        char cCharAt = str.charAt(0);
        if (cCharAt < 55296) {
            this.zzd = cCharAt;
            return;
        }
        int i = cCharAt & 8191;
        int i2 = 1;
        int i3 = 13;
        while (true) {
            int i4 = i2 + 1;
            char cCharAt2 = str.charAt(i2);
            if (cCharAt2 < 55296) {
                this.zzd = i | (cCharAt2 << i3);
                return;
            } else {
                i |= (cCharAt2 & 8191) << i3;
                i3 += 13;
                i2 = i4;
            }
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcb
    public final boolean zza() {
        return (this.zzd & 2) == 2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcb
    public final zzce zzb() {
        return this.zza;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcb
    public final int zzc() {
        int i = this.zzd;
        if ((i & 1) != 0) {
            return 1;
        }
        return (i & 4) == 4 ? 3 : 2;
    }

    public final String zzd() {
        return this.zzb;
    }

    public final Object[] zze() {
        return this.zzc;
    }
}
