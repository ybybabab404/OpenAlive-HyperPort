package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzbw {
    public static final zzcc zzb = new zzbu();
    public final zzcc zza;

    public zzbw() {
        zzcc zzccVar;
        zzaq zzaqVarZza = zzaq.zza();
        Class cls = zzf.zzb;
        try {
            zzccVar = (zzcc) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            zzccVar = zzb;
        }
        zzbv zzbvVar = new zzbv(zzaqVarZza, zzccVar);
        byte[] bArr = zzbi.zza;
        this.zza = zzbvVar;
    }

    public static boolean zzb(zzcb zzcbVar) {
        return zzcbVar.zzc() + (-1) != 1;
    }

    public static boolean zzc(Class cls) {
        Class cls2 = zzf.zzb;
        return zzax.class.isAssignableFrom(cls);
    }

    public final zzcs zza(Class cls) {
        zzct.zza(cls);
        zzcb zzcbVarZzc = this.zza.zzc(cls);
        if (zzcbVarZzc.zza()) {
            return zzc(cls) ? zzci.zzh(zzct.zzH(), zzal.zza(), zzcbVarZzc.zzb()) : zzci.zzh(zzct.zzG(), zzal.zzb(), zzcbVarZzc.zzb());
        }
        if (zzc(cls)) {
            return zzch.zzm(cls, zzcbVarZzc, zzcl.zzb(), zzbs.zzb(), zzct.zzH(), zzb(zzcbVarZzc) ? zzal.zza() : null, zzca.zzb());
        }
        zzaj zzajVarZzb = null;
        zzck zzckVarZza = zzcl.zza();
        zzbr zzbrVarZza = zzbs.zza();
        zzdc zzdcVarZzG = zzct.zzG();
        if (zzb(zzcbVarZzc)) {
            zzajVarZzb = zzal.zzb();
        }
        return zzch.zzm(cls, zzcbVarZzc, zzckVarZza, zzbrVarZza, zzdcVarZzG, zzajVarZzb, zzca.zza());
    }
}
