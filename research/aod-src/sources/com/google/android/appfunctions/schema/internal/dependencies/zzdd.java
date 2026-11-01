package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
public final class zzdd {
    public static final zzdd zza = new zzdd(0, new int[0], new Object[0], false);
    public int zzb;
    public int[] zzc;
    public Object[] zzd;
    public int zze = -1;
    public boolean zzf;

    public zzdd(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public static zzdd zza() {
        return zza;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzdd)) {
            return false;
        }
        zzdd zzddVar = (zzdd) obj;
        int i = this.zzb;
        if (i == zzddVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzddVar.zzc;
            for (int i2 = 0; i2 < i; i2++) {
                if (iArr[i2] == iArr2[i2]) {
                }
            }
            Object[] objArr = this.zzd;
            Object[] objArr2 = zzddVar.zzd;
            int i3 = this.zzb;
            for (int i4 = 0; i4 < i3; i4++) {
                if (objArr[i4].equals(objArr2[i4])) {
                }
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i = this.zzb;
        int i2 = i + 527;
        int[] iArr = this.zzc;
        int iHashCode = 17;
        int i3 = 17;
        for (int i4 = 0; i4 < i; i4++) {
            i3 = (i3 * 31) + iArr[i4];
        }
        int i5 = ((i2 * 31) + i3) * 31;
        Object[] objArr = this.zzd;
        int i6 = this.zzb;
        for (int i7 = 0; i7 < i6; i7++) {
            iHashCode = (iHashCode * 31) + objArr[i7].hashCode();
        }
        return i5 + iHashCode;
    }

    public final void zzd() {
        if (this.zzf) {
            this.zzf = false;
        }
    }

    public final void zzf(zzdt zzdtVar) {
        for (int i = 0; i < this.zzb; i++) {
            zzdtVar.zzv(this.zzc[i] >>> 3, this.zzd[i]);
        }
    }

    public final void zzg(zzdt zzdtVar) {
        if (this.zzb != 0) {
            for (int i = 0; i < this.zzb; i++) {
                int i2 = this.zzc[i];
                Object obj = this.zzd[i];
                int i3 = i2 & 7;
                int i4 = i2 >>> 3;
                if (i3 == 0) {
                    zzdtVar.zzc(i4, ((Long) obj).longValue());
                } else if (i3 == 1) {
                    zzdtVar.zzj(i4, ((Long) obj).longValue());
                } else if (i3 == 2) {
                    zzdtVar.zzn(i4, (zzt) obj);
                } else if (i3 == 3) {
                    zzdtVar.zzt(i4);
                    ((zzdd) obj).zzg(zzdtVar);
                    zzdtVar.zzu(i4);
                } else {
                    if (i3 != 5) {
                        throw new RuntimeException(new zzbj("Protocol message tag had invalid wire type."));
                    }
                    zzdtVar.zzk(i4, ((Integer) obj).intValue());
                }
            }
        }
    }

    public final int zzh() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzC = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            int i3 = this.zzc[i2] >>> 3;
            zzt zztVar = (zzt) this.zzd[i2];
            int iZzC2 = zzac.zzC(8);
            int iZzC3 = zzac.zzC(16) + zzac.zzC(i3);
            int iZzC4 = zzac.zzC(24);
            int iZzb = zztVar.zzb();
            iZzC += iZzC2 + iZzC2 + iZzC3 + iZzC4 + zzac.zzC(iZzb) + iZzb;
        }
        this.zze = iZzC;
        return iZzC;
    }

    public final int zzi() {
        int iZzC;
        int iZzD;
        int iZzC2;
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzb; i3++) {
            int i4 = this.zzc[i3];
            int i5 = i4 >>> 3;
            int i6 = i4 & 7;
            if (i6 != 0) {
                if (i6 == 1) {
                    ((Long) this.zzd[i3]).getClass();
                    iZzC2 = zzac.zzC(i5 << 3) + 8;
                } else if (i6 == 2) {
                    int i7 = i5 << 3;
                    zzt zztVar = (zzt) this.zzd[i3];
                    int iZzC3 = zzac.zzC(i7);
                    int iZzb = zztVar.zzb();
                    iZzC2 = iZzC3 + zzac.zzC(iZzb) + iZzb;
                } else if (i6 == 3) {
                    int iZzC4 = zzac.zzC(i5 << 3);
                    iZzC = iZzC4 + iZzC4;
                    iZzD = ((zzdd) this.zzd[i3]).zzi();
                } else {
                    if (i6 != 5) {
                        throw new IllegalStateException(new zzbj("Protocol message tag had invalid wire type."));
                    }
                    ((Integer) this.zzd[i3]).getClass();
                    iZzC2 = zzac.zzC(i5 << 3) + 4;
                }
                i2 += iZzC2;
            } else {
                int i8 = i5 << 3;
                long jLongValue = ((Long) this.zzd[i3]).longValue();
                iZzC = zzac.zzC(i8);
                iZzD = zzac.zzD(jLongValue);
            }
            iZzC2 = iZzC + iZzD;
            i2 += iZzC2;
        }
        this.zze = i2;
        return i2;
    }

    public final void zzj(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzcg.zzb(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }
}
