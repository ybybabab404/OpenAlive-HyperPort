package com.google.android.appfunctions.schema.internal.dependencies;

import com.miui.aod.components.view.BaseStyleSelectView;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzb' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:422)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:351)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:153)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
public final class zzbl {
    public static final zzbl zza;
    public static final zzbl zzb;
    public static final zzbl zzc;
    public static final zzbl zzd;
    public static final zzbl zze;
    public static final zzbl zzf;
    public static final zzbl zzg;
    public static final zzbl zzh;
    public static final zzbl zzi;
    public static final zzbl zzj;
    public static final /* synthetic */ zzbl[] zzl;
    public final Class zzk;

    static {
        zzbl zzblVar = new zzbl("VOID", 0, Void.class, Void.class, null);
        zza = zzblVar;
        Class cls = Integer.TYPE;
        zzbl zzblVar2 = new zzbl("INT", 1, cls, Integer.class, 0);
        zzb = zzblVar2;
        zzbl zzblVar3 = new zzbl("LONG", 2, Long.TYPE, Long.class, 0L);
        zzc = zzblVar3;
        zzbl zzblVar4 = new zzbl("FLOAT", 3, Float.TYPE, Float.class, Float.valueOf(0.0f));
        zzd = zzblVar4;
        zzbl zzblVar5 = new zzbl("DOUBLE", 4, Double.TYPE, Double.class, Double.valueOf(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE));
        zze = zzblVar5;
        zzbl zzblVar6 = new zzbl("BOOLEAN", 5, Boolean.TYPE, Boolean.class, Boolean.FALSE);
        zzf = zzblVar6;
        zzbl zzblVar7 = new zzbl("STRING", 6, String.class, String.class, "");
        zzg = zzblVar7;
        zzbl zzblVar8 = new zzbl("BYTE_STRING", 7, zzt.class, zzt.class, zzt.zza);
        zzh = zzblVar8;
        zzbl zzblVar9 = new zzbl("ENUM", 8, cls, Integer.class, null);
        zzi = zzblVar9;
        zzbl zzblVar10 = new zzbl("MESSAGE", 9, Object.class, Object.class, null);
        zzj = zzblVar10;
        zzl = new zzbl[]{zzblVar, zzblVar2, zzblVar3, zzblVar4, zzblVar5, zzblVar6, zzblVar7, zzblVar8, zzblVar9, zzblVar10};
    }

    public zzbl(String str, int i, Class cls, Class cls2, Object obj) {
        super(str, i);
        this.zzk = cls2;
    }

    public static zzbl[] values() {
        return (zzbl[]) zzl.clone();
    }

    public final Class zza() {
        return this.zzk;
    }
}
