package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzaq implements zzcc {
    public static final zzaq zza = new zzaq();

    public static zzaq zza() {
        return zza;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcc
    public final boolean zzb(Class cls) {
        return zzax.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcc
    public final zzcb zzc(Class cls) {
        if (!zzax.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Unsupported message type: ".concat(cls.getName()));
        }
        try {
            return (zzcb) zzax.getDefaultInstance(cls.asSubclass(zzax.class)).buildMessageInfo();
        } catch (Exception e) {
            throw new RuntimeException("Unable to get message info for ".concat(cls.getName()), e);
        }
    }
}
