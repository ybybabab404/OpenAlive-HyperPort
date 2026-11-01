package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.List;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzad implements zzdt {
    public final zzac zza;

    public zzad(zzac zzacVar) {
        zzbi.zza(zzacVar, "output");
        this.zza = zzacVar;
        zzacVar.zza = this;
    }

    public static zzad zza(zzac zzacVar) {
        Object obj = zzacVar.zza;
        return obj != null ? (zzad) obj : new zzad(zzacVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzA(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzbt)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzg(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            zzac zzacVar = this.zza;
            zzacVar.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Long) list.get(i4)).getClass();
                i3 += 8;
            }
            zzacVar.zzo(i3);
            while (i2 < list.size()) {
                zzacVar.zzr(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        zzbt zzbtVar = (zzbt) list;
        if (!z) {
            while (i2 < zzbtVar.size()) {
                this.zza.zzg(i, zzbtVar.zze(i2));
                i2++;
            }
            return;
        }
        zzac zzacVar2 = this.zza;
        zzacVar2.zzb(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzbtVar.size(); i6++) {
            zzbtVar.zze(i6);
            i5 += 8;
        }
        zzacVar2.zzo(i5);
        while (i2 < zzbtVar.size()) {
            zzacVar2.zzr(zzbtVar.zze(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzB(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzap)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zze(i, Float.floatToRawIntBits(((Float) list.get(i2)).floatValue()));
                    i2++;
                }
                return;
            }
            zzac zzacVar = this.zza;
            zzacVar.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Float) list.get(i4)).getClass();
                i3 += 4;
            }
            zzacVar.zzo(i3);
            while (i2 < list.size()) {
                zzacVar.zzp(Float.floatToRawIntBits(((Float) list.get(i2)).floatValue()));
                i2++;
            }
            return;
        }
        zzap zzapVar = (zzap) list;
        if (!z) {
            while (i2 < zzapVar.size()) {
                this.zza.zze(i, Float.floatToRawIntBits(zzapVar.zzf(i2)));
                i2++;
            }
            return;
        }
        zzac zzacVar2 = this.zza;
        zzacVar2.zzb(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzapVar.size(); i6++) {
            zzapVar.zzf(i6);
            i5 += 4;
        }
        zzacVar2.zzo(i5);
        while (i2 < zzapVar.size()) {
            zzacVar2.zzp(Float.floatToRawIntBits(zzapVar.zzf(i2)));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzC(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzae)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzg(i, Double.doubleToRawLongBits(((Double) list.get(i2)).doubleValue()));
                    i2++;
                }
                return;
            }
            zzac zzacVar = this.zza;
            zzacVar.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Double) list.get(i4)).getClass();
                i3 += 8;
            }
            zzacVar.zzo(i3);
            while (i2 < list.size()) {
                zzacVar.zzr(Double.doubleToRawLongBits(((Double) list.get(i2)).doubleValue()));
                i2++;
            }
            return;
        }
        zzae zzaeVar = (zzae) list;
        if (!z) {
            while (i2 < zzaeVar.size()) {
                this.zza.zzg(i, Double.doubleToRawLongBits(zzaeVar.zzf(i2)));
                i2++;
            }
            return;
        }
        zzac zzacVar2 = this.zza;
        zzacVar2.zzb(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzaeVar.size(); i6++) {
            zzaeVar.zzf(i6);
            i5 += 8;
        }
        zzacVar2.zzo(i5);
        while (i2 < zzaeVar.size()) {
            zzacVar2.zzr(Double.doubleToRawLongBits(zzaeVar.zzf(i2)));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzD(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzay)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzc(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzac zzacVar = this.zza;
            zzacVar.zzb(i, 2);
            int iZzD = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzD += zzac.zzD(((Integer) list.get(i3)).intValue());
            }
            zzacVar.zzo(iZzD);
            while (i2 < list.size()) {
                zzacVar.zzn(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzay zzayVar = (zzay) list;
        if (!z) {
            while (i2 < zzayVar.size()) {
                this.zza.zzc(i, zzayVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzac zzacVar2 = this.zza;
        zzacVar2.zzb(i, 2);
        int iZzD2 = 0;
        for (int i4 = 0; i4 < zzayVar.size(); i4++) {
            iZzD2 += zzac.zzD(zzayVar.zzf(i4));
        }
        zzacVar2.zzo(iZzD2);
        while (i2 < zzayVar.size()) {
            zzacVar2.zzn(zzayVar.zzf(i2));
            i2++;
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzE(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzi)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzh(i, ((Boolean) list.get(i2)).booleanValue());
                    i2++;
                }
                return;
            }
            zzac zzacVar = this.zza;
            zzacVar.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Boolean) list.get(i4)).getClass();
                i3++;
            }
            zzacVar.zzo(i3);
            while (i2 < list.size()) {
                zzacVar.zzm(((Boolean) list.get(i2)).booleanValue() ? (byte) 1 : (byte) 0);
                i2++;
            }
            return;
        }
        zzi zziVar = (zzi) list;
        if (!z) {
            while (i2 < zziVar.size()) {
                this.zza.zzh(i, zziVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzac zzacVar2 = this.zza;
        zzacVar2.zzb(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zziVar.size(); i6++) {
            zziVar.zzf(i6);
            i5++;
        }
        zzacVar2.zzo(i5);
        while (i2 < zziVar.size()) {
            zzacVar2.zzm(zziVar.zzf(i2) ? (byte) 1 : (byte) 0);
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzF(int i, List list) {
        int i2 = 0;
        if (!(list instanceof zzbq)) {
            while (i2 < list.size()) {
                this.zza.zzi(i, (String) list.get(i2));
                i2++;
            }
            return;
        }
        zzbq zzbqVar = (zzbq) list;
        while (i2 < list.size()) {
            Object objZzc = zzbqVar.zzc();
            if (objZzc instanceof String) {
                this.zza.zzi(i, (String) objZzc);
            } else {
                this.zza.zzj(i, (zzt) objZzc);
            }
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzG(int i, List list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zza.zzj(i, (zzt) list.get(i2));
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzH(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzay)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzd(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzac zzacVar = this.zza;
            zzacVar.zzb(i, 2);
            int iZzC = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzC += zzac.zzC(((Integer) list.get(i3)).intValue());
            }
            zzacVar.zzo(iZzC);
            while (i2 < list.size()) {
                zzacVar.zzo(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzay zzayVar = (zzay) list;
        if (!z) {
            while (i2 < zzayVar.size()) {
                this.zza.zzd(i, zzayVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzac zzacVar2 = this.zza;
        zzacVar2.zzb(i, 2);
        int iZzC2 = 0;
        for (int i4 = 0; i4 < zzayVar.size(); i4++) {
            iZzC2 += zzac.zzC(zzayVar.zzf(i4));
        }
        zzacVar2.zzo(iZzC2);
        while (i2 < zzayVar.size()) {
            zzacVar2.zzo(zzayVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzI(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzay)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zze(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzac zzacVar = this.zza;
            zzacVar.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Integer) list.get(i4)).getClass();
                i3 += 4;
            }
            zzacVar.zzo(i3);
            while (i2 < list.size()) {
                zzacVar.zzp(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzay zzayVar = (zzay) list;
        if (!z) {
            while (i2 < zzayVar.size()) {
                this.zza.zze(i, zzayVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzac zzacVar2 = this.zza;
        zzacVar2.zzb(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzayVar.size(); i6++) {
            zzayVar.zzf(i6);
            i5 += 4;
        }
        zzacVar2.zzo(i5);
        while (i2 < zzayVar.size()) {
            zzacVar2.zzp(zzayVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzJ(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzbt)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzg(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            zzac zzacVar = this.zza;
            zzacVar.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Long) list.get(i4)).getClass();
                i3 += 8;
            }
            zzacVar.zzo(i3);
            while (i2 < list.size()) {
                zzacVar.zzr(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        zzbt zzbtVar = (zzbt) list;
        if (!z) {
            while (i2 < zzbtVar.size()) {
                this.zza.zzg(i, zzbtVar.zze(i2));
                i2++;
            }
            return;
        }
        zzac zzacVar2 = this.zza;
        zzacVar2.zzb(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzbtVar.size(); i6++) {
            zzbtVar.zze(i6);
            i5 += 8;
        }
        zzacVar2.zzo(i5);
        while (i2 < zzbtVar.size()) {
            zzacVar2.zzr(zzbtVar.zze(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzK(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzay)) {
            if (!z) {
                while (i2 < list.size()) {
                    zzac zzacVar = this.zza;
                    int iIntValue = ((Integer) list.get(i2)).intValue();
                    zzacVar.zzd(i, (iIntValue >> 31) ^ (iIntValue + iIntValue));
                    i2++;
                }
                return;
            }
            zzac zzacVar2 = this.zza;
            zzacVar2.zzb(i, 2);
            int iZzC = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                int iIntValue2 = ((Integer) list.get(i3)).intValue();
                iZzC += zzac.zzC((iIntValue2 >> 31) ^ (iIntValue2 + iIntValue2));
            }
            zzacVar2.zzo(iZzC);
            while (i2 < list.size()) {
                int iIntValue3 = ((Integer) list.get(i2)).intValue();
                zzacVar2.zzo((iIntValue3 >> 31) ^ (iIntValue3 + iIntValue3));
                i2++;
            }
            return;
        }
        zzay zzayVar = (zzay) list;
        if (!z) {
            while (i2 < zzayVar.size()) {
                zzac zzacVar3 = this.zza;
                int iZzf = zzayVar.zzf(i2);
                zzacVar3.zzd(i, (iZzf >> 31) ^ (iZzf + iZzf));
                i2++;
            }
            return;
        }
        zzac zzacVar4 = this.zza;
        zzacVar4.zzb(i, 2);
        int iZzC2 = 0;
        for (int i4 = 0; i4 < zzayVar.size(); i4++) {
            int iZzf2 = zzayVar.zzf(i4);
            iZzC2 += zzac.zzC((iZzf2 >> 31) ^ (iZzf2 + iZzf2));
        }
        zzacVar4.zzo(iZzC2);
        while (i2 < zzayVar.size()) {
            int iZzf3 = zzayVar.zzf(i2);
            zzacVar4.zzo((iZzf3 >> 31) ^ (iZzf3 + iZzf3));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzL(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzbt)) {
            if (!z) {
                while (i2 < list.size()) {
                    zzac zzacVar = this.zza;
                    long jLongValue = ((Long) list.get(i2)).longValue();
                    zzacVar.zzf(i, (jLongValue >> 63) ^ (jLongValue + jLongValue));
                    i2++;
                }
                return;
            }
            zzac zzacVar2 = this.zza;
            zzacVar2.zzb(i, 2);
            int iZzD = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                long jLongValue2 = ((Long) list.get(i3)).longValue();
                iZzD += zzac.zzD((jLongValue2 >> 63) ^ (jLongValue2 + jLongValue2));
            }
            zzacVar2.zzo(iZzD);
            while (i2 < list.size()) {
                long jLongValue3 = ((Long) list.get(i2)).longValue();
                zzacVar2.zzq((jLongValue3 >> 63) ^ (jLongValue3 + jLongValue3));
                i2++;
            }
            return;
        }
        zzbt zzbtVar = (zzbt) list;
        if (!z) {
            while (i2 < zzbtVar.size()) {
                zzac zzacVar3 = this.zza;
                long jZze = zzbtVar.zze(i2);
                zzacVar3.zzf(i, (jZze >> 63) ^ (jZze + jZze));
                i2++;
            }
            return;
        }
        zzac zzacVar4 = this.zza;
        zzacVar4.zzb(i, 2);
        int iZzD2 = 0;
        for (int i4 = 0; i4 < zzbtVar.size(); i4++) {
            long jZze2 = zzbtVar.zze(i4);
            iZzD2 += zzac.zzD((jZze2 >> 63) ^ (jZze2 + jZze2));
        }
        zzacVar4.zzo(iZzD2);
        while (i2 < zzbtVar.size()) {
            long jZze3 = zzbtVar.zze(i2);
            zzacVar4.zzq((jZze3 >> 63) ^ (jZze3 + jZze3));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzb(int i, int i2) {
        this.zza.zze(i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzc(int i, long j) {
        this.zza.zzf(i, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzd(int i, long j) {
        this.zza.zzg(i, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zze(int i, float f) {
        this.zza.zze(i, Float.floatToRawIntBits(f));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzf(int i, double d) {
        this.zza.zzg(i, Double.doubleToRawLongBits(d));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzg(int i, int i2) {
        this.zza.zzc(i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzh(int i, long j) {
        this.zza.zzf(i, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzi(int i, int i2) {
        this.zza.zzc(i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzj(int i, long j) {
        this.zza.zzg(i, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzk(int i, int i2) {
        this.zza.zze(i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzl(int i, boolean z) {
        this.zza.zzh(i, z);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzm(int i, String str) {
        this.zza.zzi(i, str);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzn(int i, zzt zztVar) {
        this.zza.zzj(i, zztVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzo(int i, int i2) {
        this.zza.zzd(i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzp(int i, int i2) {
        zzac zzacVar = this.zza;
        zzacVar.zzd(i, (i2 >> 31) ^ (i2 + i2));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzq(int i, long j) {
        zzac zzacVar = this.zza;
        zzacVar.zzf(i, (j >> 63) ^ (j + j));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzr(int i, Object obj, zzcs zzcsVar) {
        zzac zzacVar = this.zza;
        zzc zzcVar = (zzc) obj;
        zzacVar.zzb(i, 2);
        zzacVar.zzo(zzcVar.getSerializedSize(zzcsVar));
        zzcsVar.zzf(zzcVar, this);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzs(int i, Object obj, zzcs zzcsVar) {
        zzac zzacVar = this.zza;
        zzacVar.zzb(i, 3);
        zzcsVar.zzf((zzc) obj, this);
        zzacVar.zzb(i, 4);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzt(int i) {
        this.zza.zzb(i, 3);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzu(int i) {
        this.zza.zzb(i, 4);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzv(int i, Object obj) {
        boolean z = obj instanceof zzt;
        zzac zzacVar = this.zza;
        if (z) {
            zzacVar.zzl(i, (zzt) obj);
        } else {
            zzacVar.zzk(i, (zzce) obj);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzw(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzay)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzc(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzac zzacVar = this.zza;
            zzacVar.zzb(i, 2);
            int iZzD = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzD += zzac.zzD(((Integer) list.get(i3)).intValue());
            }
            zzacVar.zzo(iZzD);
            while (i2 < list.size()) {
                zzacVar.zzn(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzay zzayVar = (zzay) list;
        if (!z) {
            while (i2 < zzayVar.size()) {
                this.zza.zzc(i, zzayVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzac zzacVar2 = this.zza;
        zzacVar2.zzb(i, 2);
        int iZzD2 = 0;
        for (int i4 = 0; i4 < zzayVar.size(); i4++) {
            iZzD2 += zzac.zzD(zzayVar.zzf(i4));
        }
        zzacVar2.zzo(iZzD2);
        while (i2 < zzayVar.size()) {
            zzacVar2.zzn(zzayVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzx(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzay)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zze(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzac zzacVar = this.zza;
            zzacVar.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Integer) list.get(i4)).getClass();
                i3 += 4;
            }
            zzacVar.zzo(i3);
            while (i2 < list.size()) {
                zzacVar.zzp(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzay zzayVar = (zzay) list;
        if (!z) {
            while (i2 < zzayVar.size()) {
                this.zza.zze(i, zzayVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzac zzacVar2 = this.zza;
        zzacVar2.zzb(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzayVar.size(); i6++) {
            zzayVar.zzf(i6);
            i5 += 4;
        }
        zzacVar2.zzo(i5);
        while (i2 < zzayVar.size()) {
            zzacVar2.zzp(zzayVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzy(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzbt)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzf(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            zzac zzacVar = this.zza;
            zzacVar.zzb(i, 2);
            int iZzD = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzD += zzac.zzD(((Long) list.get(i3)).longValue());
            }
            zzacVar.zzo(iZzD);
            while (i2 < list.size()) {
                zzacVar.zzq(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        zzbt zzbtVar = (zzbt) list;
        if (!z) {
            while (i2 < zzbtVar.size()) {
                this.zza.zzf(i, zzbtVar.zze(i2));
                i2++;
            }
            return;
        }
        zzac zzacVar2 = this.zza;
        zzacVar2.zzb(i, 2);
        int iZzD2 = 0;
        for (int i4 = 0; i4 < zzbtVar.size(); i4++) {
            iZzD2 += zzac.zzD(zzbtVar.zze(i4));
        }
        zzacVar2.zzo(iZzD2);
        while (i2 < zzbtVar.size()) {
            zzacVar2.zzq(zzbtVar.zze(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdt
    public final void zzz(int i, List list, boolean z) {
        int i2 = 0;
        if (!(list instanceof zzbt)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzf(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            zzac zzacVar = this.zza;
            zzacVar.zzb(i, 2);
            int iZzD = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzD += zzac.zzD(((Long) list.get(i3)).longValue());
            }
            zzacVar.zzo(iZzD);
            while (i2 < list.size()) {
                zzacVar.zzq(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        zzbt zzbtVar = (zzbt) list;
        if (!z) {
            while (i2 < zzbtVar.size()) {
                this.zza.zzf(i, zzbtVar.zze(i2));
                i2++;
            }
            return;
        }
        zzac zzacVar2 = this.zza;
        zzacVar2.zzb(i, 2);
        int iZzD2 = 0;
        for (int i4 = 0; i4 < zzbtVar.size(); i4++) {
            iZzD2 += zzac.zzD(zzbtVar.zze(i4));
        }
        zzacVar2.zzo(iZzD2);
        while (i2 < zzbtVar.size()) {
            zzacVar2.zzq(zzbtVar.zze(i2));
            i2++;
        }
    }
}
