package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
public abstract class zzac extends zzj {
    public static final boolean zzc = zzdk.zza();
    public Object zza;

    public /* synthetic */ zzac(byte[] bArr) {
    }

    public static int zzC(int i) {
        return (352 - (Integer.numberOfLeadingZeros(i) * 9)) >>> 6;
    }

    public static int zzD(long j) {
        return (640 - (Long.numberOfLeadingZeros(j) * 9)) >>> 6;
    }

    public static int zzE(zzce zzceVar) {
        int serializedSize = zzceVar.getSerializedSize();
        return zzC(serializedSize) + serializedSize;
    }

    public final void zzF() {
        if (zzv() > 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
        if (zzv() < 0) {
            throw new IllegalStateException("Wrote more data than expected.");
        }
    }

    public abstract void zzb(int i, int i2);

    public abstract void zzc(int i, int i2);

    public abstract void zzd(int i, int i2);

    public abstract void zze(int i, int i2);

    public abstract void zzf(int i, long j);

    public abstract void zzg(int i, long j);

    public abstract void zzh(int i, boolean z);

    public abstract void zzi(int i, String str);

    public abstract void zzj(int i, zzt zztVar);

    public abstract void zzk(int i, zzce zzceVar);

    public abstract void zzl(int i, zzt zztVar);

    public abstract void zzm(byte b);

    public abstract void zzn(int i);

    public abstract void zzo(int i);

    public abstract void zzp(int i);

    public abstract void zzq(long j);

    public abstract void zzr(long j);

    public abstract int zzv();
}
