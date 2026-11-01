package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzak extends zzaj {
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzaj
    public final boolean zza(zzce zzceVar) {
        return zzceVar instanceof zzat;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzaj
    public final zzan zzb(Object obj) {
        return ((zzat) obj).zza;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzaj
    public final void zzd(Object obj) {
        ((zzat) obj).zza.zzb();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzaj
    public final int zzf(Map.Entry entry) {
        return ((zzau) entry.getKey()).zzb;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzaj
    public final void zzg(zzdt zzdtVar, Map.Entry entry) {
        zzau zzauVar = (zzau) entry.getKey();
        if (!zzauVar.zzd) {
            zzdr zzdrVar = zzdr.zza;
            switch (zzauVar.zzc.ordinal()) {
                case 0:
                    zzdtVar.zzf(zzauVar.zzb, ((Double) entry.getValue()).doubleValue());
                    break;
                case 1:
                    zzdtVar.zze(zzauVar.zzb, ((Float) entry.getValue()).floatValue());
                    break;
                case 2:
                    zzdtVar.zzc(zzauVar.zzb, ((Long) entry.getValue()).longValue());
                    break;
                case 3:
                    zzdtVar.zzh(zzauVar.zzb, ((Long) entry.getValue()).longValue());
                    break;
                case 4:
                    zzdtVar.zzi(zzauVar.zzb, ((Integer) entry.getValue()).intValue());
                    break;
                case 5:
                    zzdtVar.zzj(zzauVar.zzb, ((Long) entry.getValue()).longValue());
                    break;
                case 6:
                    zzdtVar.zzk(zzauVar.zzb, ((Integer) entry.getValue()).intValue());
                    break;
                case 7:
                    zzdtVar.zzl(zzauVar.zzb, ((Boolean) entry.getValue()).booleanValue());
                    break;
                case 8:
                    zzdtVar.zzm(zzauVar.zzb, (String) entry.getValue());
                    break;
                case 9:
                    zzdtVar.zzs(zzauVar.zzb, entry.getValue(), zzco.zza().zzb(entry.getValue().getClass()));
                    break;
                case 10:
                    zzdtVar.zzr(zzauVar.zzb, entry.getValue(), zzco.zza().zzb(entry.getValue().getClass()));
                    break;
                case 11:
                    zzdtVar.zzn(zzauVar.zzb, (zzt) entry.getValue());
                    break;
                case 12:
                    zzdtVar.zzo(zzauVar.zzb, ((Integer) entry.getValue()).intValue());
                    break;
                case 13:
                    zzdtVar.zzi(zzauVar.zzb, ((Integer) entry.getValue()).intValue());
                    break;
                case 14:
                    zzdtVar.zzb(zzauVar.zzb, ((Integer) entry.getValue()).intValue());
                    break;
                case 15:
                    zzdtVar.zzd(zzauVar.zzb, ((Long) entry.getValue()).longValue());
                    break;
                case 16:
                    zzdtVar.zzp(zzauVar.zzb, ((Integer) entry.getValue()).intValue());
                    break;
                case 17:
                    zzdtVar.zzq(zzauVar.zzb, ((Long) entry.getValue()).longValue());
                    break;
            }
        }
        zzdr zzdrVar2 = zzdr.zza;
        switch (zzauVar.zzc.ordinal()) {
            case 0:
                zzct.zzb(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
            case 1:
                zzct.zzc(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
            case 2:
                zzct.zzd(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
            case 3:
                zzct.zze(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
            case 4:
                zzct.zzi(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
            case 5:
                zzct.zzg(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
            case 6:
                zzct.zzl(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
            case 7:
                zzct.zzo(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
            case 8:
                zzct.zzp(zzauVar.zzb, (List) entry.getValue(), zzdtVar);
                break;
            case 9:
                List list = (List) entry.getValue();
                if (list != null && !list.isEmpty()) {
                    zzct.zzs(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzco.zza().zzb(list.get(0).getClass()));
                    break;
                }
                break;
            case 10:
                List list2 = (List) entry.getValue();
                if (list2 != null && !list2.isEmpty()) {
                    zzct.zzr(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzco.zza().zzb(list2.get(0).getClass()));
                    break;
                }
                break;
            case 11:
                zzct.zzq(zzauVar.zzb, (List) entry.getValue(), zzdtVar);
                break;
            case 12:
                zzct.zzj(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
            case 13:
                zzct.zzi(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
            case 14:
                zzct.zzm(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
            case 15:
                zzct.zzh(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
            case 16:
                zzct.zzk(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
            case 17:
                zzct.zzf(zzauVar.zzb, (List) entry.getValue(), zzdtVar, zzauVar.zze);
                break;
        }
    }
}
