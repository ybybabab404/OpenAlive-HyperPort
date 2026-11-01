package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzan {
    public static final zzan zzd = new zzan(true);
    public final zzcy zza = new zzcu();
    public boolean zzb;
    public boolean zzc;

    public zzan() {
    }

    public zzan(boolean z) {
        zzb();
        zzb();
    }

    public static int zzj(zzdr zzdrVar, int i, Object obj) {
        int iZzC = zzac.zzC(i << 3);
        if (zzdrVar == zzdr.zzj) {
            iZzC += iZzC;
        }
        return iZzC + zzk(zzdrVar, obj);
    }

    public static int zzk(zzdr zzdrVar, Object obj) {
        int iZzc;
        int iZzC;
        zzdr zzdrVar2 = zzdr.zza;
        zzds zzdsVar = zzds.INT;
        switch (zzdrVar.ordinal()) {
            case 0:
                ((Double) obj).getClass();
                boolean z = zzac.zzc;
                return 8;
            case 1:
                ((Float) obj).getClass();
                boolean z2 = zzac.zzc;
                return 4;
            case 2:
                return zzac.zzD(((Long) obj).longValue());
            case 3:
                return zzac.zzD(((Long) obj).longValue());
            case 4:
                return zzac.zzD(((Integer) obj).intValue());
            case 5:
                ((Long) obj).getClass();
                boolean z3 = zzac.zzc;
                return 8;
            case 6:
                ((Integer) obj).getClass();
                boolean z4 = zzac.zzc;
                return 4;
            case 7:
                ((Boolean) obj).getClass();
                boolean z5 = zzac.zzc;
                return 1;
            case 8:
                if (!(obj instanceof zzt)) {
                    boolean z6 = zzac.zzc;
                    iZzc = zzdq.zzc((String) obj);
                    iZzC = zzac.zzC(iZzc);
                } else {
                    boolean z7 = zzac.zzc;
                    iZzc = ((zzt) obj).zzb();
                    iZzC = zzac.zzC(iZzc);
                }
                break;
            case 9:
                return ((zzce) obj).getSerializedSize();
            case 10:
                if (!(obj instanceof zzbo)) {
                    return zzac.zzE((zzce) obj);
                }
                iZzc = ((zzbo) obj).zzb();
                iZzC = zzac.zzC(iZzc);
                break;
                break;
            case 11:
                if (!(obj instanceof zzt)) {
                    boolean z8 = zzac.zzc;
                    iZzc = ((byte[]) obj).length;
                    iZzC = zzac.zzC(iZzc);
                } else {
                    boolean z9 = zzac.zzc;
                    iZzc = ((zzt) obj).zzb();
                    iZzC = zzac.zzC(iZzc);
                }
                break;
            case 12:
                return zzac.zzC(((Integer) obj).intValue());
            case 13:
                return obj instanceof zzbb ? zzac.zzD(((zzbb) obj).getNumber()) : zzac.zzD(((Integer) obj).intValue());
            case 14:
                ((Integer) obj).getClass();
                boolean z10 = zzac.zzc;
                return 4;
            case 15:
                ((Long) obj).getClass();
                boolean z11 = zzac.zzc;
                return 8;
            case 16:
                int iIntValue = ((Integer) obj).intValue();
                return zzac.zzC((iIntValue >> 31) ^ (iIntValue + iIntValue));
            case 17:
                long jLongValue = ((Long) obj).longValue();
                return zzac.zzD((jLongValue >> 63) ^ (jLongValue + jLongValue));
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
        return iZzC + iZzc;
    }

    public static int zzl(zzam zzamVar, Object obj) {
        zzdr zzdrVarZzb = zzamVar.zzb();
        int iZza = zzamVar.zza();
        if (!zzamVar.zzd()) {
            return zzj(zzdrVarZzb, iZza, obj);
        }
        List list = (List) obj;
        int size = list.size();
        int i = 0;
        if (!zzamVar.zze()) {
            int iZzj = 0;
            while (i < size) {
                iZzj += zzj(zzdrVarZzb, iZza, list.get(i));
                i++;
            }
            return iZzj;
        }
        if (list.isEmpty()) {
            return 0;
        }
        int iZzk = 0;
        while (i < size) {
            iZzk += zzk(zzdrVarZzb, list.get(i));
            i++;
        }
        return zzac.zzC(iZza << 3) + iZzk + zzac.zzC(iZzk);
    }

    public static final int zzq(Map.Entry entry) {
        int i;
        int iZzC;
        int iZzC2;
        zzam zzamVar = (zzam) entry.getKey();
        Object value = entry.getValue();
        if (zzamVar.zzc() != zzds.MESSAGE || zzamVar.zzd() || zzamVar.zze()) {
            return zzl(zzamVar, value);
        }
        if (value instanceof zzbo) {
            int iZza = ((zzam) entry.getKey()).zza();
            int iZzC3 = zzac.zzC(8);
            i = iZzC3 + iZzC3;
            iZzC = zzac.zzC(16) + zzac.zzC(iZza);
            int iZzC4 = zzac.zzC(24);
            int iZzb = ((zzbo) value).zzb();
            iZzC2 = iZzC4 + zzac.zzC(iZzb) + iZzb;
        } else {
            int iZza2 = ((zzam) entry.getKey()).zza();
            int iZzC5 = zzac.zzC(8);
            i = iZzC5 + iZzC5;
            iZzC = zzac.zzC(16) + zzac.zzC(iZza2);
            iZzC2 = zzac.zzC(24) + zzac.zzE((zzce) value);
        }
        return i + iZzC + iZzC2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:27:0x0047 A[RETURN] */
    public static final void zzr(zzam zzamVar, Object obj) {
        boolean z;
        zzamVar.zzb();
        byte[] bArr = zzbi.zza;
        obj.getClass();
        zzdr zzdrVar = zzdr.zza;
        zzds zzdsVar = zzds.INT;
        switch (r0.zza()) {
            case INT:
                z = obj instanceof Integer;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzamVar.zza()), zzamVar.zzb().zza(), obj.getClass().getName()));
            case LONG:
                z = obj instanceof Long;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzamVar.zza()), zzamVar.zzb().zza(), obj.getClass().getName()));
            case FLOAT:
                z = obj instanceof Float;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzamVar.zza()), zzamVar.zzb().zza(), obj.getClass().getName()));
            case DOUBLE:
                z = obj instanceof Double;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzamVar.zza()), zzamVar.zzb().zza(), obj.getClass().getName()));
            case BOOLEAN:
                z = obj instanceof Boolean;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzamVar.zza()), zzamVar.zzb().zza(), obj.getClass().getName()));
            case STRING:
                z = obj instanceof String;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzamVar.zza()), zzamVar.zzb().zza(), obj.getClass().getName()));
            case BYTE_STRING:
                if ((obj instanceof zzt) || (obj instanceof byte[])) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzamVar.zza()), zzamVar.zzb().zza(), obj.getClass().getName()));
            case ENUM:
                if ((obj instanceof Integer) || (obj instanceof zzbb)) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzamVar.zza()), zzamVar.zzb().zza(), obj.getClass().getName()));
            case MESSAGE:
                if ((obj instanceof zzce) || (obj instanceof zzbo)) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzamVar.zza()), zzamVar.zzb().zza(), obj.getClass().getName()));
            default:
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzamVar.zza()), zzamVar.zzb().zza(), obj.getClass().getName()));
        }
    }

    public final /* bridge */ /* synthetic */ Object clone() {
        zzan zzanVar = new zzan();
        zzcy zzcyVar = this.zza;
        int iZzc = zzcyVar.zzc();
        for (int i = 0; i < iZzc; i++) {
            Map.Entry entryZzd = zzcyVar.zzd(i);
            zzanVar.zze((zzam) ((zzcv) entryZzd).zza(), entryZzd.getValue());
        }
        for (Map.Entry entry : zzcyVar.zze()) {
            zzanVar.zze((zzam) entry.getKey(), entry.getValue());
        }
        zzanVar.zzc = this.zzc;
        return zzanVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzan) {
            return this.zza.equals(((zzan) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final void zzb() {
        if (this.zzb) {
            return;
        }
        zzcy zzcyVar = this.zza;
        int iZzc = zzcyVar.zzc();
        for (int i = 0; i < iZzc; i++) {
            Object value = zzcyVar.zzd(i).getValue();
            if (value instanceof zzax) {
                ((zzax) value).makeImmutable();
            }
        }
        Iterator it = zzcyVar.zze().iterator();
        while (it.hasNext()) {
            Object value2 = ((Map.Entry) it.next()).getValue();
            if (value2 instanceof zzax) {
                ((zzax) value2).makeImmutable();
            }
        }
        zzcyVar.zza();
        this.zzb = true;
    }

    public final Iterator zzc() {
        zzcy zzcyVar = this.zza;
        if (zzcyVar.isEmpty()) {
            return Collections.emptyIterator();
        }
        return this.zzc ? new zzbn(zzcyVar.entrySet().iterator()) : zzcyVar.entrySet().iterator();
    }

    public final void zze(zzam zzamVar, Object obj) {
        if (!zzamVar.zzd()) {
            zzr(zzamVar, obj);
        } else {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            List list = (List) obj;
            int size = list.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                Object obj2 = list.get(i);
                zzr(zzamVar, obj2);
                arrayList.add(obj2);
            }
            obj = arrayList;
        }
        if (obj instanceof zzbo) {
            this.zzc = true;
        }
        this.zza.put(zzamVar, obj);
    }

    public final int zzi() {
        zzcy zzcyVar = this.zza;
        int iZzc = zzcyVar.zzc();
        int iZzq = 0;
        for (int i = 0; i < iZzc; i++) {
            iZzq += zzq(zzcyVar.zzd(i));
        }
        Iterator it = zzcyVar.zze().iterator();
        while (it.hasNext()) {
            iZzq += zzq((Map.Entry) it.next());
        }
        return iZzq;
    }
}
