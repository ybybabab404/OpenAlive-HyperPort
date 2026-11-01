package com.google.android.appfunctions.schema.internal.dependencies;

import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzdg extends zzdj {
    public zzdg(Unsafe unsafe) {
        super(unsafe);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdj
    public final void zzb(Object obj, long j, byte b) {
        if (zzdk.zzb) {
            zzdk.zzJ(obj, j, b);
        } else {
            zzdk.zzK(obj, j, b);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdj
    public final boolean zzc(Object obj, long j) {
        return zzdk.zzb ? zzdk.zzy(obj, j) : zzdk.zzz(obj, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdj
    public final float zze(Object obj, long j) {
        return Float.intBitsToFloat(this.zza.getInt(obj, j));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdj
    public final double zzg(Object obj, long j) {
        return Double.longBitsToDouble(this.zza.getLong(obj, j));
    }
}
