package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.Arrays;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzr extends zzq {
    public final byte[] zzb;

    public zzr(byte[] bArr) {
        super(null);
        bArr.getClass();
        this.zzb = bArr;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final byte zza(int i) {
        return this.zzb[i];
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final int zzb() {
        return this.zzb.length;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final zzt zzc(int i, int i2) {
        byte[] bArr = this.zzb;
        int iZzs = zzt.zzs(0, i2, bArr.length);
        return iZzs == 0 ? zzt.zza : new zzn(bArr, 0, iZzs);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final void zzd(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zzb, 0, bArr, 0, i3);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final void zze(zzj zzjVar) {
        byte[] bArr = this.zzb;
        zzjVar.zza(bArr, 0, bArr.length);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final boolean zzh(zzt zztVar) {
        boolean z = zztVar instanceof zzr;
        if (z) {
            return Arrays.equals(this.zzb, ((zzr) zztVar).zzb);
        }
        boolean z2 = zztVar instanceof zzn;
        if (!z2) {
            return zztVar.zzh(this);
        }
        byte[] bArr = this.zzb;
        int iZzb = zztVar.zzb();
        int length = bArr.length;
        if (length > iZzb) {
            StringBuilder sb = new StringBuilder(String.valueOf(length).length() + 18 + String.valueOf(length).length());
            sb.append("Length too large: ");
            sb.append(length);
            sb.append(length);
            throw new IllegalArgumentException(sb.toString());
        }
        if (length <= zztVar.zzb()) {
            if (z) {
                return zzt.zzt(bArr, 0, ((zzr) zztVar).zzb, 0, length);
            }
            if (!z2) {
                return zztVar.zzc(0, length).equals(zzc(0, length));
            }
            zzn zznVar = (zzn) zztVar;
            return zzt.zzt(bArr, 0, zznVar.zzk(), zznVar.zzl(), length);
        }
        int iZzb2 = zztVar.zzb();
        StringBuilder sb2 = new StringBuilder(String.valueOf(length).length() + 27 + String.valueOf(iZzb2).length());
        sb2.append("Ran off end of other: 0, ");
        sb2.append(length);
        sb2.append(", ");
        sb2.append(iZzb2);
        throw new IllegalArgumentException(sb2.toString());
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final int zzi(int i, int i2, int i3) {
        return zzbi.zzc(i, this.zzb, 0, i3);
    }

    public final /* synthetic */ byte[] zzk() {
        return this.zzb;
    }
}
