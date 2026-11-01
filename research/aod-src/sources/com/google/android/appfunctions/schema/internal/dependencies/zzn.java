package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzn extends zzq {
    public final byte[] zzb;
    public final int zzc;
    public final int zzd;

    public zzn(byte[] bArr, int i, int i2) {
        super(null);
        zzt.zzs(i, i + i2, bArr.length);
        this.zzb = bArr;
        this.zzc = i;
        this.zzd = i2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final byte zza(int i) {
        return this.zzb[this.zzc + i];
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final int zzb() {
        return this.zzd;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final zzt zzc(int i, int i2) {
        int iZzs = zzt.zzs(i, i2, this.zzd);
        return iZzs == 0 ? zzt.zza : new zzn(this.zzb, this.zzc + i, iZzs);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final void zzd(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zzb, this.zzc, bArr, 0, i3);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final void zze(zzj zzjVar) {
        zzjVar.zza(this.zzb, this.zzc, this.zzd);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final boolean zzh(zzt zztVar) {
        boolean z = zztVar instanceof zzr;
        if (!z && !(zztVar instanceof zzn)) {
            return zztVar.zzh(this);
        }
        int i = this.zzd;
        if (i > zztVar.zzb()) {
            StringBuilder sb = new StringBuilder(String.valueOf(i).length() + 18 + String.valueOf(i).length());
            sb.append("Length too large: ");
            sb.append(i);
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
        if (i <= zztVar.zzb()) {
            if (z) {
                return zzt.zzt(this.zzb, this.zzc, ((zzr) zztVar).zzk(), 0, i);
            }
            if (zztVar instanceof zzn) {
                zzn zznVar = (zzn) zztVar;
                return zzt.zzt(this.zzb, this.zzc, zznVar.zzb, zznVar.zzc, i);
            }
            zzt zztVarZzc = zztVar.zzc(0, i);
            int i2 = this.zzc;
            return zztVarZzc.equals(zzc(i2, i + i2));
        }
        int iZzb = zztVar.zzb();
        StringBuilder sb2 = new StringBuilder(String.valueOf(i).length() + 27 + String.valueOf(iZzb).length());
        sb2.append("Ran off end of other: 0, ");
        sb2.append(i);
        sb2.append(", ");
        sb2.append(iZzb);
        throw new IllegalArgumentException(sb2.toString());
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final int zzi(int i, int i2, int i3) {
        return zzbi.zzc(i, this.zzb, this.zzc, i3);
    }

    public final /* synthetic */ byte[] zzk() {
        return this.zzb;
    }

    public final /* synthetic */ int zzl() {
        return this.zzc;
    }
}
