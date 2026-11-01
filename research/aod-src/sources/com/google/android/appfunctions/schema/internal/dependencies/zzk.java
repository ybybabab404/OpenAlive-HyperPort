package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.NoSuchElementException;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzk extends zzl {
    public final /* synthetic */ zzt zza;
    public int zzb;
    public final int zzc;

    public zzk(zzt zztVar) {
        Objects.requireNonNull(zztVar);
        this.zza = zztVar;
        this.zzb = 0;
        this.zzc = zztVar.zzb();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzb < this.zzc;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzp
    public final byte zza() {
        int i = this.zzb;
        if (i >= this.zzc) {
            throw new NoSuchElementException();
        }
        this.zzb = i + 1;
        return this.zza.zza(i);
    }
}
