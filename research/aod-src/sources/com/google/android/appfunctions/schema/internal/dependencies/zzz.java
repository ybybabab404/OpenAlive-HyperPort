package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.Locale;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzz extends zzac {
    public final byte[] zzc;
    public final int zzd;
    public int zze;

    public zzz(byte[] bArr, int i, int i2) {
        super(null);
        int length = bArr.length;
        if (((length - i2) | i2) < 0) {
            throw new IllegalArgumentException(String.format(Locale.US, "Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(length), 0, Integer.valueOf(i2)));
        }
        this.zzc = bArr;
        this.zze = 0;
        this.zzd = i2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzj
    public final void zza(byte[] bArr, int i, int i2) throws zzaa {
        zzs(bArr, i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzb(int i, int i2) throws zzaa {
        zzo((i << 3) | i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzc(int i, int i2) throws zzaa {
        zzo(i << 3);
        zzn(i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzd(int i, int i2) throws zzaa {
        zzo(i << 3);
        zzo(i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zze(int i, int i2) throws zzaa {
        zzo((i << 3) | 5);
        zzp(i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzf(int i, long j) throws zzaa {
        zzo(i << 3);
        zzq(j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzg(int i, long j) throws zzaa {
        zzo((i << 3) | 1);
        zzr(j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzh(int i, boolean z) throws zzaa {
        zzo(i << 3);
        zzm(z ? (byte) 1 : (byte) 0);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzi(int i, String str) throws zzaa {
        zzo((i << 3) | 2);
        zzt(str);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzj(int i, zzt zztVar) throws zzaa {
        zzo((i << 3) | 2);
        zzo(zztVar.zzb());
        zztVar.zze(this);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzk(int i, zzce zzceVar) throws zzaa {
        zzo(11);
        zzd(2, i);
        zzo(26);
        zzo(zzceVar.getSerializedSize());
        zzceVar.writeTo(this);
        zzo(12);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzl(int i, zzt zztVar) throws zzaa {
        zzo(11);
        zzd(2, i);
        zzj(3, zztVar);
        zzo(12);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzm(byte b) throws zzaa {
        IndexOutOfBoundsException indexOutOfBoundsException;
        int i = this.zze;
        try {
            int i2 = i + 1;
            try {
                this.zzc[i] = b;
                this.zze = i2;
            } catch (IndexOutOfBoundsException e) {
                indexOutOfBoundsException = e;
                i = i2;
                throw new zzaa(i, this.zzd, 1, indexOutOfBoundsException);
            }
        } catch (IndexOutOfBoundsException e2) {
            indexOutOfBoundsException = e2;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzn(int i) throws zzaa {
        if (i >= 0) {
            zzo(i);
        } else {
            zzq(i);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzo(int i) throws zzaa {
        int i2;
        IndexOutOfBoundsException indexOutOfBoundsException;
        int i3 = this.zze;
        while ((i & (-128)) != 0) {
            try {
                i2 = i3 + 1;
                try {
                    this.zzc[i3] = (byte) (i | 128);
                    i >>>= 7;
                    i3 = i2;
                } catch (IndexOutOfBoundsException e) {
                    indexOutOfBoundsException = e;
                    i3 = i2;
                    throw new zzaa(i3, this.zzd, 1, indexOutOfBoundsException);
                }
            } catch (IndexOutOfBoundsException e2) {
                indexOutOfBoundsException = e2;
                throw new zzaa(i3, this.zzd, 1, indexOutOfBoundsException);
            }
        }
        i2 = i3 + 1;
        this.zzc[i3] = (byte) i;
        this.zze = i2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzp(int i) throws zzaa {
        int i2 = this.zze;
        try {
            byte[] bArr = this.zzc;
            bArr[i2] = (byte) i;
            bArr[i2 + 1] = (byte) (i >> 8);
            bArr[i2 + 2] = (byte) (i >> 16);
            bArr[i2 + 3] = (byte) (i >> 24);
            this.zze = i2 + 4;
        } catch (IndexOutOfBoundsException e) {
            throw new zzaa(i2, this.zzd, 4, e);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzq(long j) throws zzaa {
        int i;
        IndexOutOfBoundsException indexOutOfBoundsException;
        boolean z = zzac.zzc;
        int i2 = this.zze;
        if (!z || this.zzd - i2 < 10) {
            while ((j & (-128)) != 0) {
                try {
                    int i3 = i2 + 1;
                    try {
                        this.zzc[i2] = (byte) (((int) j) | 128);
                        j >>>= 7;
                        i2 = i3;
                    } catch (IndexOutOfBoundsException e) {
                        indexOutOfBoundsException = e;
                        i2 = i3;
                        throw new zzaa(i2, this.zzd, 1, indexOutOfBoundsException);
                    }
                } catch (IndexOutOfBoundsException e2) {
                    indexOutOfBoundsException = e2;
                }
            }
            i = i2 + 1;
            try {
                this.zzc[i2] = (byte) j;
            } catch (IndexOutOfBoundsException e3) {
                indexOutOfBoundsException = e3;
                i2 = i;
                throw new zzaa(i2, this.zzd, 1, indexOutOfBoundsException);
            }
        } else {
            while ((j & (-128)) != 0) {
                zzdk.zzq(this.zzc, i2, (byte) (((int) j) | 128));
                j >>>= 7;
                i2++;
            }
            i = i2 + 1;
            zzdk.zzq(this.zzc, i2, (byte) j);
        }
        this.zze = i;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzr(long j) throws zzaa {
        int i = this.zze;
        try {
            byte[] bArr = this.zzc;
            bArr[i] = (byte) j;
            bArr[i + 1] = (byte) (j >> 8);
            bArr[i + 2] = (byte) (j >> 16);
            bArr[i + 3] = (byte) (j >> 24);
            bArr[i + 4] = (byte) (j >> 32);
            bArr[i + 5] = (byte) (j >> 40);
            bArr[i + 6] = (byte) (j >> 48);
            bArr[i + 7] = (byte) (j >> 56);
            this.zze = i + 8;
        } catch (IndexOutOfBoundsException e) {
            throw new zzaa(i, this.zzd, 8, e);
        }
    }

    public final void zzs(byte[] bArr, int i, int i2) throws zzaa {
        try {
            System.arraycopy(bArr, i, this.zzc, this.zze, i2);
            this.zze += i2;
        } catch (IndexOutOfBoundsException e) {
            throw new zzaa(this.zze, this.zzd, i2, e);
        }
    }

    public final void zzt(String str) throws zzaa {
        int i = this.zze;
        try {
            int iZzC = zzac.zzC(str.length() * 3);
            int iZzC2 = zzac.zzC(str.length());
            if (iZzC2 != iZzC) {
                zzo(zzdq.zzc(str));
                byte[] bArr = this.zzc;
                int i2 = this.zze;
                this.zze = zzdq.zzd(str, bArr, i2, bArr.length - i2);
                return;
            }
            int i3 = i + iZzC2;
            this.zze = i3;
            byte[] bArr2 = this.zzc;
            int iZzd = zzdq.zzd(str, bArr2, i3, bArr2.length - i3);
            this.zze = i;
            zzo((iZzd - i) - iZzC2);
            this.zze = iZzd;
        } catch (IndexOutOfBoundsException e) {
            throw new zzaa(e);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final int zzv() {
        return this.zzd - this.zze;
    }
}
