package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzab extends zzac {
    public final byte[] zzc;
    public final int zzd;
    public int zze;
    public int zzf;
    public final OutputStream zzg;

    public final void zzA(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.zzd;
        int i4 = this.zze;
        int i5 = i3 - i4;
        if (i5 >= i2) {
            System.arraycopy(bArr, i, this.zzc, i4, i2);
            this.zze += i2;
            this.zzf += i2;
            return;
        }
        byte[] bArr2 = this.zzc;
        System.arraycopy(bArr, i, bArr2, i4, i5);
        int i6 = i + i5;
        this.zze = i3;
        this.zzf += i5;
        zzI();
        int i7 = i2 - i5;
        if (i7 <= i3) {
            System.arraycopy(bArr, i6, bArr2, 0, i7);
            this.zze = i7;
        } else {
            this.zzg.write(bArr, i6, i7);
        }
        this.zzf += i7;
    }

    public final void zzH(int i) throws IOException {
        if (this.zzd - this.zze < i) {
            zzI();
        }
    }

    public final void zzI() throws IOException {
        this.zzg.write(this.zzc, 0, this.zze);
        this.zze = 0;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzj
    public final void zza(byte[] bArr, int i, int i2) throws IOException {
        zzA(bArr, i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzb(int i, int i2) throws IOException {
        zzo((i << 3) | i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzc(int i, int i2) throws IOException {
        zzH(20);
        zzt(i << 3);
        if (i2 >= 0) {
            zzt(i2);
        } else {
            zzw(i2);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzd(int i, int i2) throws IOException {
        zzH(20);
        zzt(i << 3);
        zzt(i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zze(int i, int i2) throws IOException {
        zzH(14);
        zzt((i << 3) | 5);
        zzx(i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzf(int i, long j) throws IOException {
        zzH(20);
        zzt(i << 3);
        zzw(j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzg(int i, long j) throws IOException {
        zzH(18);
        zzt((i << 3) | 1);
        zzy(j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzh(int i, boolean z) throws IOException {
        zzH(11);
        zzt(i << 3);
        zzs(z ? (byte) 1 : (byte) 0);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzi(int i, String str) throws IOException {
        zzo((i << 3) | 2);
        zzz(str);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzj(int i, zzt zztVar) throws IOException {
        zzo((i << 3) | 2);
        zzo(zztVar.zzb());
        zztVar.zze(this);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzk(int i, zzce zzceVar) throws IOException {
        zzo(11);
        zzd(2, i);
        zzo(26);
        zzo(zzceVar.getSerializedSize());
        zzceVar.writeTo(this);
        zzo(12);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzl(int i, zzt zztVar) throws IOException {
        zzo(11);
        zzd(2, i);
        zzj(3, zztVar);
        zzo(12);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzm(byte b) throws IOException {
        if (this.zze == this.zzd) {
            zzI();
        }
        zzs(b);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzn(int i) throws IOException {
        if (i >= 0) {
            zzo(i);
        } else {
            zzq(i);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzo(int i) throws IOException {
        zzH(5);
        zzt(i);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzp(int i) throws IOException {
        zzH(4);
        zzx(i);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzq(long j) throws IOException {
        zzH(10);
        zzw(j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final void zzr(long j) throws IOException {
        zzH(8);
        zzy(j);
    }

    public final void zzs(byte b) {
        byte[] bArr = this.zzc;
        int i = this.zze;
        bArr[i] = b;
        this.zze = i + 1;
        this.zzf++;
    }

    public final void zzt(int i) {
        if (!zzac.zzc) {
            while ((i & (-128)) != 0) {
                byte[] bArr = this.zzc;
                int i2 = this.zze;
                this.zze = i2 + 1;
                bArr[i2] = (byte) (i | 128);
                this.zzf++;
                i >>>= 7;
            }
            byte[] bArr2 = this.zzc;
            int i3 = this.zze;
            this.zze = i3 + 1;
            bArr2[i3] = (byte) i;
            this.zzf++;
            return;
        }
        long j = this.zze;
        while ((i & (-128)) != 0) {
            byte[] bArr3 = this.zzc;
            int i4 = this.zze;
            this.zze = i4 + 1;
            zzdk.zzq(bArr3, i4, (byte) (i | 128));
            i >>>= 7;
        }
        byte[] bArr4 = this.zzc;
        int i5 = this.zze;
        this.zze = i5 + 1;
        zzdk.zzq(bArr4, i5, (byte) i);
        this.zzf += (int) (((long) this.zze) - j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzac
    public final int zzv() {
        throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array or ByteBuffer.");
    }

    public final void zzw(long j) {
        if (zzac.zzc) {
            long j2 = this.zze;
            while (true) {
                int i = (int) j;
                if ((j & (-128)) == 0) {
                    byte[] bArr = this.zzc;
                    int i2 = this.zze;
                    this.zze = i2 + 1;
                    zzdk.zzq(bArr, i2, (byte) i);
                    this.zzf += (int) (((long) this.zze) - j2);
                    return;
                }
                byte[] bArr2 = this.zzc;
                int i3 = this.zze;
                this.zze = i3 + 1;
                zzdk.zzq(bArr2, i3, (byte) (i | 128));
                j >>>= 7;
            }
        } else {
            while (true) {
                int i4 = (int) j;
                if ((j & (-128)) == 0) {
                    byte[] bArr3 = this.zzc;
                    int i5 = this.zze;
                    this.zze = i5 + 1;
                    bArr3[i5] = (byte) i4;
                    this.zzf++;
                    return;
                }
                byte[] bArr4 = this.zzc;
                int i6 = this.zze;
                this.zze = i6 + 1;
                bArr4[i6] = (byte) (i4 | 128);
                this.zzf++;
                j >>>= 7;
            }
        }
    }

    public final void zzx(int i) {
        int i2 = this.zze;
        byte[] bArr = this.zzc;
        bArr[i2] = (byte) i;
        bArr[i2 + 1] = (byte) (i >> 8);
        bArr[i2 + 2] = (byte) (i >> 16);
        bArr[i2 + 3] = (byte) (i >> 24);
        this.zze = i2 + 4;
        this.zzf += 4;
    }

    public final void zzy(long j) {
        int i = this.zze;
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
        this.zzf += 8;
    }

    public final void zzz(String str) throws IOException {
        int iZzc;
        int length = str.length() * 3;
        int iZzC = zzac.zzC(length);
        int i = iZzC + length;
        int i2 = this.zzd;
        if (i > i2) {
            byte[] bArr = new byte[length];
            int iZzd = zzdq.zzd(str, bArr, 0, length);
            zzo(iZzd);
            zzA(bArr, 0, iZzd);
            return;
        }
        if (i > i2 - this.zze) {
            zzI();
        }
        int iZzC2 = zzac.zzC(str.length());
        int i3 = this.zze;
        try {
            if (iZzC2 == iZzC) {
                int i4 = i3 + iZzC2;
                this.zze = i4;
                int iZzd2 = zzdq.zzd(str, this.zzc, i4, i2 - i4);
                this.zze = i3;
                iZzc = (iZzd2 - i3) - iZzC2;
                zzt(iZzc);
                this.zze = iZzd2;
            } else {
                iZzc = zzdq.zzc(str);
                zzt(iZzc);
                this.zze = zzdq.zzd(str, this.zzc, this.zze, iZzc);
            }
            this.zzf += iZzc;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new zzaa(e);
        }
    }
}
