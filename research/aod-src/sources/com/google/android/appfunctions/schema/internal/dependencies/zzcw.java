package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzcw implements Iterator {
    public final /* synthetic */ zzcy zza;
    public int zzb;
    public boolean zzc;
    public Iterator zzd;

    public /* synthetic */ zzcw(zzcy zzcyVar, byte[] bArr) {
        Objects.requireNonNull(zzcyVar);
        this.zza = zzcyVar;
        this.zzb = -1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i = this.zzb + 1;
        zzcy zzcyVar = this.zza;
        if (i >= zzcyVar.zzj()) {
            return !zzcyVar.zzk().isEmpty() && zza().hasNext();
        }
        return true;
    }

    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ Object next() {
        this.zzc = true;
        int i = this.zzb + 1;
        this.zzb = i;
        zzcy zzcyVar = this.zza;
        return i < zzcyVar.zzj() ? (zzcv) zzcyVar.zzi()[i] : (Map.Entry) zza().next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.zzc) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzc = false;
        zzcy zzcyVar = this.zza;
        zzcyVar.zzh();
        int i = this.zzb;
        if (i >= zzcyVar.zzj()) {
            zza().remove();
        } else {
            this.zzb = i - 1;
            zzcyVar.zzg(i);
        }
    }

    public final Iterator zza() {
        if (this.zzd == null) {
            this.zzd = this.zza.zzk().entrySet().iterator();
        }
        return this.zzd;
    }
}
