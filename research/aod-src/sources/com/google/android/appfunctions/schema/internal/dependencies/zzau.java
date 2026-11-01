package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzau implements zzam {
    public final int zzb;
    public final zzdr zzc;
    public final boolean zzd;
    public final boolean zze;

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(Object obj) {
        return this.zzb - ((zzau) obj).zzb;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzam
    public final int zza() {
        return this.zzb;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzam
    public final zzdr zzb() {
        return this.zzc;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzam
    public final zzds zzc() {
        return this.zzc.zza();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzam
    public final boolean zzd() {
        return this.zzd;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzam
    public final boolean zze() {
        return this.zze;
    }
}
