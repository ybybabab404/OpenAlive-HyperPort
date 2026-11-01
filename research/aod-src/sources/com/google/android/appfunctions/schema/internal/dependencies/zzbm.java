package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.Map;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzbm implements Map.Entry {
    public final Map.Entry zza;

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.zza.getKey();
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        zzbo zzboVar = (zzbo) this.zza.getValue();
        if (zzboVar == null) {
            return null;
        }
        zzboVar.zzd(null);
        return zzboVar.zza;
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        if (obj instanceof zzce) {
            return ((zzbo) this.zza.getValue()).zza((zzce) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }

    public final zzbo zza() {
        return (zzbo) this.zza.getValue();
    }
}
