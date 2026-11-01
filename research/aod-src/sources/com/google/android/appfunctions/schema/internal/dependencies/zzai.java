package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.Collections;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
public final class zzai {
    public static final zzai zza = new zzai(true);
    public static volatile zzai zzc;
    public final Map zzd = Collections.EMPTY_MAP;

    public zzai(boolean z) {
    }

    public static zzai zza() {
        zzai zzaiVar;
        Class cls = zzf.zzb;
        zzai zzaiVar2 = zzc;
        if (zzaiVar2 != null) {
            return zzaiVar2;
        }
        synchronized (zzai.class) {
            zzaiVar = zzc;
            if (zzaiVar == null) {
                Class cls2 = zzag.zza;
                zzai zzaiVar3 = null;
                if (cls2 != null) {
                    try {
                        zzaiVar3 = (zzai) cls2.getDeclaredMethod("getEmptyRegistry", new Class[0]).invoke(null, new Object[0]);
                    } catch (Exception unused) {
                    }
                }
                zzaiVar = zzaiVar3 == null ? zza : zzaiVar3;
                zzc = zzaiVar;
            }
        }
        return zzaiVar;
    }
}
