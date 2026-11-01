package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzc' uses external variables
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
public final class zzdr {
    public static final zzdr zza;
    public static final zzdr zzb;
    public static final zzdr zzc;
    public static final zzdr zzd;
    public static final zzdr zze;
    public static final zzdr zzf;
    public static final zzdr zzg;
    public static final zzdr zzh;
    public static final zzdr zzi;
    public static final zzdr zzj;
    public static final zzdr zzk;
    public static final zzdr zzl;
    public static final zzdr zzm;
    public static final zzdr zzn;
    public static final zzdr zzo;
    public static final zzdr zzp;
    public static final zzdr zzq;
    public static final zzdr zzr;
    public static final /* synthetic */ zzdr[] zzt;
    public final zzds zzs;

    static {
        zzdr zzdrVar = new zzdr("DOUBLE", 0, zzds.DOUBLE, 1);
        zza = zzdrVar;
        zzdr zzdrVar2 = new zzdr("FLOAT", 1, zzds.FLOAT, 5);
        zzb = zzdrVar2;
        zzds zzdsVar = zzds.LONG;
        zzdr zzdrVar3 = new zzdr("INT64", 2, zzdsVar, 0);
        zzc = zzdrVar3;
        zzdr zzdrVar4 = new zzdr("UINT64", 3, zzdsVar, 0);
        zzd = zzdrVar4;
        zzds zzdsVar2 = zzds.INT;
        zzdr zzdrVar5 = new zzdr("INT32", 4, zzdsVar2, 0);
        zze = zzdrVar5;
        zzdr zzdrVar6 = new zzdr("FIXED64", 5, zzdsVar, 1);
        zzf = zzdrVar6;
        zzdr zzdrVar7 = new zzdr("FIXED32", 6, zzdsVar2, 5);
        zzg = zzdrVar7;
        zzdr zzdrVar8 = new zzdr("BOOL", 7, zzds.BOOLEAN, 0);
        zzh = zzdrVar8;
        zzdr zzdrVar9 = new zzdr("STRING", 8, zzds.STRING, 2);
        zzi = zzdrVar9;
        zzds zzdsVar3 = zzds.MESSAGE;
        zzdr zzdrVar10 = new zzdr("GROUP", 9, zzdsVar3, 3);
        zzj = zzdrVar10;
        zzdr zzdrVar11 = new zzdr("MESSAGE", 10, zzdsVar3, 2);
        zzk = zzdrVar11;
        zzdr zzdrVar12 = new zzdr("BYTES", 11, zzds.BYTE_STRING, 2);
        zzl = zzdrVar12;
        zzdr zzdrVar13 = new zzdr("UINT32", 12, zzdsVar2, 0);
        zzm = zzdrVar13;
        zzdr zzdrVar14 = new zzdr("ENUM", 13, zzds.ENUM, 0);
        zzn = zzdrVar14;
        zzdr zzdrVar15 = new zzdr("SFIXED32", 14, zzdsVar2, 5);
        zzo = zzdrVar15;
        zzdr zzdrVar16 = new zzdr("SFIXED64", 15, zzdsVar, 1);
        zzp = zzdrVar16;
        zzdr zzdrVar17 = new zzdr("SINT32", 16, zzdsVar2, 0);
        zzq = zzdrVar17;
        zzdr zzdrVar18 = new zzdr("SINT64", 17, zzdsVar, 0);
        zzr = zzdrVar18;
        zzt = new zzdr[]{zzdrVar, zzdrVar2, zzdrVar3, zzdrVar4, zzdrVar5, zzdrVar6, zzdrVar7, zzdrVar8, zzdrVar9, zzdrVar10, zzdrVar11, zzdrVar12, zzdrVar13, zzdrVar14, zzdrVar15, zzdrVar16, zzdrVar17, zzdrVar18};
    }

    public zzdr(String str, int i, zzds zzdsVar, int i2) {
        super(str, i);
        this.zzs = zzdsVar;
    }

    public static zzdr[] values() {
        return (zzdr[]) zzt.clone();
    }

    public final zzds zza() {
        return this.zzs;
    }
}
