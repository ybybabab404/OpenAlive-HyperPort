package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
public abstract class zzx {
    public static volatile int zzf = 100;
    public final int zzc = zzf;

    public /* synthetic */ zzx(byte[] bArr) {
    }

    public static zzx zzI(byte[] bArr, int i, int i2, boolean z) {
        zzv zzvVar = new zzv(bArr, i, i2, z, null);
        try {
            zzvVar.zzB(i2);
            return zzvVar;
        } catch (zzbk e) {
            throw new IllegalArgumentException(e);
        }
    }
}
