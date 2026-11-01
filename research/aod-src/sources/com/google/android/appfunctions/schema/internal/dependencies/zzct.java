package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.List;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzct {
    public static final Class zzb;
    public static final zzdc zzc;
    public static final zzdc zzd;

    static {
        Class<?> cls;
        Class<?> cls2;
        Class cls3 = zzf.zzb;
        zzdc zzdcVar = null;
        try {
            cls = Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            cls = null;
        }
        zzb = cls;
        try {
            cls2 = Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused2) {
            cls2 = null;
        }
        if (cls2 != null) {
            try {
                zzdcVar = (zzdc) cls2.getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Throwable unused3) {
            }
        }
        zzc = zzdcVar;
        zzd = new zzde();
    }

    public static int zzA(List list) {
        return list.size() * 4;
    }

    public static int zzB(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzac.zzC(i << 3) + 4);
    }

    public static int zzC(List list) {
        return list.size() * 8;
    }

    public static int zzD(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzac.zzC(i << 3) + 8);
    }

    public static int zzE(int i, Object obj, zzcs zzcsVar) {
        int iZzC;
        int serializedSize;
        int iZzC2;
        int i2 = i << 3;
        if (obj instanceof zzbp) {
            iZzC = zzac.zzC(i2);
            serializedSize = ((zzbp) obj).zzb();
            iZzC2 = zzac.zzC(serializedSize);
        } else {
            iZzC = zzac.zzC(i2);
            serializedSize = ((zzc) obj).getSerializedSize(zzcsVar);
            iZzC2 = zzac.zzC(serializedSize);
        }
        return iZzC + iZzC2 + serializedSize;
    }

    public static int zzF(int i, zzce zzceVar, zzcs zzcsVar) {
        int iZzC = zzac.zzC(i << 3);
        return iZzC + iZzC + ((zzc) zzceVar).getSerializedSize(zzcsVar);
    }

    public static zzdc zzG() {
        return zzc;
    }

    public static zzdc zzH() {
        return zzd;
    }

    public static boolean zzI(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public static void zza(Class cls) {
        if (zzax.class.isAssignableFrom(cls)) {
            return;
        }
        Class cls2 = zzf.zzb;
        Class cls3 = zzb;
        if (cls3 != null && !cls3.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zzb(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzC(i, list, z);
    }

    public static void zzc(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzB(i, list, z);
    }

    public static void zzd(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzy(i, list, z);
    }

    public static void zze(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzz(i, list, z);
    }

    public static void zzf(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzL(i, list, z);
    }

    public static void zzg(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzA(i, list, z);
    }

    public static void zzh(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzJ(i, list, z);
    }

    public static void zzi(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzw(i, list, z);
    }

    public static void zzj(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzH(i, list, z);
    }

    public static void zzk(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzK(i, list, z);
    }

    public static void zzl(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzx(i, list, z);
    }

    public static void zzm(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzI(i, list, z);
    }

    public static void zzn(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzD(i, list, z);
    }

    public static void zzo(int i, List list, zzdt zzdtVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzE(i, list, z);
    }

    public static void zzp(int i, List list, zzdt zzdtVar) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzF(i, list);
    }

    public static void zzq(int i, List list, zzdt zzdtVar) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdtVar.zzG(i, list);
    }

    public static void zzr(int i, List list, zzdt zzdtVar, zzcs zzcsVar) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ((zzad) zzdtVar).zzr(i, list.get(i2), zzcsVar);
        }
    }

    public static void zzs(int i, List list, zzdt zzdtVar, zzcs zzcsVar) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ((zzad) zzdtVar).zzs(i, list.get(i2), zzcsVar);
        }
    }

    public static int zzt(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzbt)) {
            int iZzD = 0;
            while (i < size) {
                iZzD += zzac.zzD(((Long) list.get(i)).longValue());
                i++;
            }
            return iZzD;
        }
        zzbt zzbtVar = (zzbt) list;
        int iZzD2 = 0;
        while (i < size) {
            iZzD2 += zzac.zzD(zzbtVar.zze(i));
            i++;
        }
        return iZzD2;
    }

    public static int zzu(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzbt)) {
            int iZzD = 0;
            while (i < size) {
                iZzD += zzac.zzD(((Long) list.get(i)).longValue());
                i++;
            }
            return iZzD;
        }
        zzbt zzbtVar = (zzbt) list;
        int iZzD2 = 0;
        while (i < size) {
            iZzD2 += zzac.zzD(zzbtVar.zze(i));
            i++;
        }
        return iZzD2;
    }

    public static int zzv(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzbt)) {
            int iZzD = 0;
            while (i < size) {
                long jLongValue = ((Long) list.get(i)).longValue();
                iZzD += zzac.zzD((jLongValue >> 63) ^ (jLongValue + jLongValue));
                i++;
            }
            return iZzD;
        }
        zzbt zzbtVar = (zzbt) list;
        int iZzD2 = 0;
        while (i < size) {
            long jZze = zzbtVar.zze(i);
            iZzD2 += zzac.zzD((jZze >> 63) ^ (jZze + jZze));
            i++;
        }
        return iZzD2;
    }

    public static int zzw(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzay)) {
            int iZzD = 0;
            while (i < size) {
                iZzD += zzac.zzD(((Integer) list.get(i)).intValue());
                i++;
            }
            return iZzD;
        }
        zzay zzayVar = (zzay) list;
        int iZzD2 = 0;
        while (i < size) {
            iZzD2 += zzac.zzD(zzayVar.zzf(i));
            i++;
        }
        return iZzD2;
    }

    public static int zzx(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzay)) {
            int iZzD = 0;
            while (i < size) {
                iZzD += zzac.zzD(((Integer) list.get(i)).intValue());
                i++;
            }
            return iZzD;
        }
        zzay zzayVar = (zzay) list;
        int iZzD2 = 0;
        while (i < size) {
            iZzD2 += zzac.zzD(zzayVar.zzf(i));
            i++;
        }
        return iZzD2;
    }

    public static int zzy(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzay)) {
            int iZzC = 0;
            while (i < size) {
                iZzC += zzac.zzC(((Integer) list.get(i)).intValue());
                i++;
            }
            return iZzC;
        }
        zzay zzayVar = (zzay) list;
        int iZzC2 = 0;
        while (i < size) {
            iZzC2 += zzac.zzC(zzayVar.zzf(i));
            i++;
        }
        return iZzC2;
    }

    public static int zzz(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzay)) {
            int iZzC = 0;
            while (i < size) {
                int iIntValue = ((Integer) list.get(i)).intValue();
                iZzC += zzac.zzC((iIntValue >> 31) ^ (iIntValue + iIntValue));
                i++;
            }
            return iZzC;
        }
        zzay zzayVar = (zzay) list;
        int iZzC2 = 0;
        while (i < size) {
            int iZzf = zzayVar.zzf(i);
            iZzC2 += zzac.zzC((iZzf >> 31) ^ (iZzf + iZzf));
            i++;
        }
        return iZzC2;
    }
}
