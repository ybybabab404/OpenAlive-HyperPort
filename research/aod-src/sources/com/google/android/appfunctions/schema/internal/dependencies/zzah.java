package com.google.android.appfunctions.schema.internal.dependencies;

import androidx.exifinterface.media.ExifInterface;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzah {
    public final Object zza;
    public final int zzb;

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzah)) {
            return false;
        }
        zzah zzahVar = (zzah) obj;
        return this.zza == zzahVar.zza && this.zzb == zzahVar.zzb;
    }

    public final int hashCode() {
        return (System.identityHashCode(this.zza) * ExifInterface.COLOR_SPACE_UNCALIBRATED) + this.zzb;
    }
}
