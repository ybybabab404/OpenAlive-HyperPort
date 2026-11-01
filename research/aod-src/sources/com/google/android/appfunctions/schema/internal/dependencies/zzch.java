package com.google.android.appfunctions.schema.internal.dependencies;

import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.maml.R;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzch implements zzcs {
    public static final int[] zza = new int[0];
    public static final Unsafe zzb = zzdk.zzr();
    public final int[] zzc;
    public final Object[] zzd;
    public final int zze;
    public final int zzf;
    public final zzce zzg;
    public final boolean zzh;
    public final boolean zzi;
    public final int[] zzj;
    public final int zzk;
    public final int zzl;
    public final zzdc zzm;
    public final zzaj zzn;

    public zzch(int[] iArr, Object[] objArr, int i, int i2, zzce zzceVar, boolean z, int[] iArr2, int i3, int i4, zzck zzckVar, zzbr zzbrVar, zzdc zzdcVar, zzaj zzajVar, zzbz zzbzVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = zzceVar instanceof zzax;
        boolean z2 = false;
        if (zzajVar != null && zzajVar.zza(zzceVar)) {
            z2 = true;
        }
        this.zzh = z2;
        this.zzj = iArr2;
        this.zzk = i3;
        this.zzl = i4;
        this.zzm = zzdcVar;
        this.zzn = zzajVar;
        this.zzg = zzceVar;
    }

    public static int zzC(int i) {
        return (i >>> 20) & 255;
    }

    public static boolean zzE(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zzax) {
            return ((zzax) obj).isMutable();
        }
        return true;
    }

    public static double zzG(Object obj, long j) {
        return ((Double) zzdk.zzn(obj, j)).doubleValue();
    }

    public static float zzH(Object obj, long j) {
        return ((Float) zzdk.zzn(obj, j)).floatValue();
    }

    public static int zzI(Object obj, long j) {
        return ((Integer) zzdk.zzn(obj, j)).intValue();
    }

    public static long zzJ(Object obj, long j) {
        return ((Long) zzdk.zzn(obj, j)).longValue();
    }

    public static boolean zzK(Object obj, long j) {
        return ((Boolean) zzdk.zzn(obj, j)).booleanValue();
    }

    public static final void zzT(int i, Object obj, zzdt zzdtVar) {
        if (obj instanceof String) {
            zzdtVar.zzm(i, (String) obj);
        } else {
            zzdtVar.zzn(i, (zzt) obj);
        }
    }

    /* JADX WARN: Code duplicated, block: B:126:0x026e  */
    /* JADX WARN: Code duplicated, block: B:128:0x0274  */
    /* JADX WARN: Code duplicated, block: B:131:0x028c  */
    /* JADX WARN: Code duplicated, block: B:132:0x028f  */
    /* JADX WARN: Code duplicated, block: B:171:0x0350  */
    /* JADX WARN: Code duplicated, block: B:187:0x03a6  */
    /* JADX WARN: Code duplicated, block: B:190:0x03b0  */
    public static zzch zzm(Class cls, zzcb zzcbVar, zzck zzckVar, zzbr zzbrVar, zzdc zzdcVar, zzaj zzajVar, zzbz zzbzVar) {
        int i;
        int iCharAt;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int[] iArr;
        int i7;
        int i8;
        char cCharAt;
        int i9;
        char cCharAt2;
        int i10;
        char cCharAt3;
        int i11;
        char cCharAt4;
        int i12;
        char cCharAt5;
        int i13;
        char cCharAt6;
        int i14;
        char cCharAt7;
        int i15;
        char cCharAt8;
        int i16;
        int i17;
        int i18;
        int iObjectFieldOffset;
        char c;
        int iObjectFieldOffset2;
        int i19;
        int i20;
        int i21;
        Field fieldZzn;
        char cCharAt9;
        int i22;
        int i23;
        int i24;
        int i25;
        int i26;
        Object obj;
        Field fieldZzn2;
        int i27;
        Object obj2;
        Field fieldZzn3;
        int i28;
        char cCharAt10;
        int i29;
        char cCharAt11;
        int i30;
        char cCharAt12;
        int i31;
        char cCharAt13;
        if (!(zzcbVar instanceof zzcq)) {
            throw null;
        }
        zzcq zzcqVar = (zzcq) zzcbVar;
        String strZzd = zzcqVar.zzd();
        int length = strZzd.length();
        char c2 = 55296;
        if (strZzd.charAt(0) >= 55296) {
            int i32 = 1;
            while (true) {
                i = i32 + 1;
                if (strZzd.charAt(i32) < 55296) {
                    break;
                }
                i32 = i;
            }
        } else {
            i = 1;
        }
        int i33 = i + 1;
        int iCharAt2 = strZzd.charAt(i);
        if (iCharAt2 >= 55296) {
            int i34 = iCharAt2 & 8191;
            int i35 = 13;
            while (true) {
                i31 = i33 + 1;
                cCharAt13 = strZzd.charAt(i33);
                if (cCharAt13 < 55296) {
                    break;
                }
                i34 |= (cCharAt13 & 8191) << i35;
                i35 += 13;
                i33 = i31;
            }
            iCharAt2 = i34 | (cCharAt13 << i35);
            i33 = i31;
        }
        if (iCharAt2 == 0) {
            i3 = 0;
            i6 = 0;
            iCharAt = 0;
            i2 = 0;
            i4 = 0;
            i5 = 0;
            iArr = zza;
            i7 = 0;
        } else {
            int i36 = i33 + 1;
            int iCharAt3 = strZzd.charAt(i33);
            if (iCharAt3 >= 55296) {
                int i37 = iCharAt3 & 8191;
                int i38 = 13;
                while (true) {
                    i15 = i36 + 1;
                    cCharAt8 = strZzd.charAt(i36);
                    if (cCharAt8 < 55296) {
                        break;
                    }
                    i37 |= (cCharAt8 & 8191) << i38;
                    i38 += 13;
                    i36 = i15;
                }
                iCharAt3 = i37 | (cCharAt8 << i38);
                i36 = i15;
            }
            int i39 = i36 + 1;
            int iCharAt4 = strZzd.charAt(i36);
            if (iCharAt4 >= 55296) {
                int i40 = iCharAt4 & 8191;
                int i41 = 13;
                while (true) {
                    i14 = i39 + 1;
                    cCharAt7 = strZzd.charAt(i39);
                    if (cCharAt7 < 55296) {
                        break;
                    }
                    i40 |= (cCharAt7 & 8191) << i41;
                    i41 += 13;
                    i39 = i14;
                }
                iCharAt4 = i40 | (cCharAt7 << i41);
                i39 = i14;
            }
            int i42 = i39 + 1;
            int iCharAt5 = strZzd.charAt(i39);
            if (iCharAt5 >= 55296) {
                int i43 = iCharAt5 & 8191;
                int i44 = 13;
                while (true) {
                    i13 = i42 + 1;
                    cCharAt6 = strZzd.charAt(i42);
                    if (cCharAt6 < 55296) {
                        break;
                    }
                    i43 |= (cCharAt6 & 8191) << i44;
                    i44 += 13;
                    i42 = i13;
                }
                iCharAt5 = i43 | (cCharAt6 << i44);
                i42 = i13;
            }
            int i45 = i42 + 1;
            int iCharAt6 = strZzd.charAt(i42);
            if (iCharAt6 >= 55296) {
                int i46 = iCharAt6 & 8191;
                int i47 = 13;
                while (true) {
                    i12 = i45 + 1;
                    cCharAt5 = strZzd.charAt(i45);
                    if (cCharAt5 < 55296) {
                        break;
                    }
                    i46 |= (cCharAt5 & 8191) << i47;
                    i47 += 13;
                    i45 = i12;
                }
                iCharAt6 = i46 | (cCharAt5 << i47);
                i45 = i12;
            }
            int i48 = i45 + 1;
            iCharAt = strZzd.charAt(i45);
            if (iCharAt >= 55296) {
                int i49 = iCharAt & 8191;
                int i50 = 13;
                while (true) {
                    i11 = i48 + 1;
                    cCharAt4 = strZzd.charAt(i48);
                    if (cCharAt4 < 55296) {
                        break;
                    }
                    i49 |= (cCharAt4 & 8191) << i50;
                    i50 += 13;
                    i48 = i11;
                }
                iCharAt = i49 | (cCharAt4 << i50);
                i48 = i11;
            }
            int i51 = i48 + 1;
            int iCharAt7 = strZzd.charAt(i48);
            if (iCharAt7 >= 55296) {
                int i52 = iCharAt7 & 8191;
                int i53 = 13;
                while (true) {
                    i10 = i51 + 1;
                    cCharAt3 = strZzd.charAt(i51);
                    if (cCharAt3 < 55296) {
                        break;
                    }
                    i52 |= (cCharAt3 & 8191) << i53;
                    i53 += 13;
                    i51 = i10;
                }
                iCharAt7 = i52 | (cCharAt3 << i53);
                i51 = i10;
            }
            int i54 = i51 + 1;
            int iCharAt8 = strZzd.charAt(i51);
            if (iCharAt8 >= 55296) {
                int i55 = iCharAt8 & 8191;
                int i56 = 13;
                while (true) {
                    i9 = i54 + 1;
                    cCharAt2 = strZzd.charAt(i54);
                    if (cCharAt2 < 55296) {
                        break;
                    }
                    i55 |= (cCharAt2 & 8191) << i56;
                    i56 += 13;
                    i54 = i9;
                }
                iCharAt8 = i55 | (cCharAt2 << i56);
                i54 = i9;
            }
            int i57 = i54 + 1;
            int iCharAt9 = strZzd.charAt(i54);
            if (iCharAt9 >= 55296) {
                int i58 = iCharAt9 & 8191;
                int i59 = 13;
                while (true) {
                    i8 = i57 + 1;
                    cCharAt = strZzd.charAt(i57);
                    if (cCharAt < 55296) {
                        break;
                    }
                    i58 |= (cCharAt & 8191) << i59;
                    i59 += 13;
                    i57 = i8;
                }
                iCharAt9 = i58 | (cCharAt << i59);
                i57 = i8;
            }
            int i60 = iCharAt3 + iCharAt3 + iCharAt4;
            int[] iArr2 = new int[iCharAt9 + iCharAt7 + iCharAt8];
            int i61 = iCharAt7;
            i2 = iCharAt5;
            i3 = i61;
            i4 = iCharAt6;
            i5 = iCharAt9;
            i6 = i60;
            iArr = iArr2;
            i7 = iCharAt3;
            i33 = i57;
        }
        Unsafe unsafe = zzb;
        Object[] objArrZze = zzcqVar.zze();
        Class<?> cls2 = zzcqVar.zzb().getClass();
        int i62 = i5 + i3;
        int i63 = iCharAt + iCharAt;
        int[] iArr3 = new int[iCharAt * 3];
        Object[] objArr = new Object[i63];
        int i64 = i5;
        int i65 = i62;
        int i66 = 0;
        int i67 = 0;
        while (i33 < length) {
            int i68 = i33 + 1;
            int iCharAt10 = strZzd.charAt(i33);
            if (iCharAt10 >= c2) {
                int i69 = iCharAt10 & 8191;
                int i70 = i68;
                int i71 = 13;
                while (true) {
                    i30 = i70 + 1;
                    cCharAt12 = strZzd.charAt(i70);
                    if (cCharAt12 < c2) {
                        break;
                    }
                    i69 |= (cCharAt12 & 8191) << i71;
                    i71 += 13;
                    i70 = i30;
                }
                iCharAt10 = i69 | (cCharAt12 << i71);
                i16 = i30;
            } else {
                i16 = i68;
            }
            int i72 = i16 + 1;
            int iCharAt11 = strZzd.charAt(i16);
            if (iCharAt11 >= c2) {
                int i73 = iCharAt11 & 8191;
                int i74 = i72;
                int i75 = 13;
                while (true) {
                    i29 = i74 + 1;
                    cCharAt11 = strZzd.charAt(i74);
                    if (cCharAt11 < c2) {
                        break;
                    }
                    i73 |= (cCharAt11 & 8191) << i75;
                    i75 += 13;
                    i74 = i29;
                }
                iCharAt11 = i73 | (cCharAt11 << i75);
                i17 = i29;
            } else {
                i17 = i72;
            }
            if ((iCharAt11 & 1024) != 0) {
                iArr[i66] = i67;
                i66++;
            }
            int i76 = iCharAt11 & 255;
            zzcq zzcqVar2 = zzcqVar;
            int i77 = iCharAt11 & AutoBottomSheetKt.ALIGN_SCREEN_END;
            if (i76 >= 51) {
                int i78 = i17 + 1;
                int iCharAt12 = strZzd.charAt(i17);
                char c3 = 55296;
                if (iCharAt12 >= 55296) {
                    int i79 = iCharAt12 & 8191;
                    int i80 = i78;
                    int i81 = 13;
                    while (true) {
                        i28 = i80 + 1;
                        cCharAt10 = strZzd.charAt(i80);
                        if (cCharAt10 < c3) {
                            break;
                        }
                        i79 |= (cCharAt10 & 8191) << i81;
                        i81 += 13;
                        i80 = i28;
                        c3 = 55296;
                    }
                    iCharAt12 = i79 | (cCharAt10 << i81);
                    i23 = i28;
                } else {
                    i23 = i78;
                }
                int i82 = i23;
                int i83 = i76 - 51;
                if (i83 == 9 || i83 == 17) {
                    i24 = i6 + 1;
                    int i84 = i67 / 3;
                    objArr[i84 + i84 + 1] = objArrZze[i6];
                } else {
                    if (i83 != 12) {
                        i25 = i77;
                    } else if (zzcqVar2.zzc() == 1 || i77 != 0) {
                        i24 = i6 + 1;
                        int i85 = i67 / 3;
                        objArr[i85 + i85 + 1] = objArrZze[i6];
                    } else {
                        i25 = 0;
                    }
                    i26 = iCharAt12 + iCharAt12;
                    obj = objArrZze[i26];
                    int i86 = i25;
                    if (obj instanceof Field) {
                        fieldZzn2 = (Field) obj;
                    } else {
                        fieldZzn2 = zzn(cls2, (String) obj);
                        objArrZze[i26] = fieldZzn2;
                    }
                    int i87 = i7;
                    iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzn2);
                    i27 = i26 + 1;
                    obj2 = objArrZze[i27];
                    i18 = i87;
                    if (obj2 instanceof Field) {
                        fieldZzn3 = (Field) obj2;
                    } else {
                        fieldZzn3 = zzn(cls2, (String) obj2);
                        objArrZze[i27] = fieldZzn3;
                    }
                    iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZzn3);
                    strZzd = strZzd;
                    i20 = i86;
                    i17 = i82;
                    i19 = 0;
                    c = 55296;
                }
                i6 = i24;
                i25 = i77;
                i26 = iCharAt12 + iCharAt12;
                obj = objArrZze[i26];
                int i88 = i25;
                if (obj instanceof Field) {
                    fieldZzn2 = (Field) obj;
                } else {
                    fieldZzn2 = zzn(cls2, (String) obj);
                    objArrZze[i26] = fieldZzn2;
                }
                int i89 = i7;
                iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzn2);
                i27 = i26 + 1;
                obj2 = objArrZze[i27];
                i18 = i89;
                if (obj2 instanceof Field) {
                    fieldZzn3 = (Field) obj2;
                } else {
                    fieldZzn3 = zzn(cls2, (String) obj2);
                    objArrZze[i27] = fieldZzn3;
                }
                iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZzn3);
                strZzd = strZzd;
                i20 = i88;
                i17 = i82;
                i19 = 0;
                c = 55296;
            } else {
                i18 = i7;
                int i90 = i6 + 1;
                Field fieldZzn4 = zzn(cls2, (String) objArrZze[i6]);
                if (i76 == 9 || i76 == 17) {
                    int i91 = i67 / 3;
                    objArr[i91 + i91 + 1] = fieldZzn4.getType();
                } else {
                    if (i76 != 27) {
                        if (i76 == 49) {
                            i6 += 2;
                            i22 = 1;
                        } else if (i76 == 12 || i76 == 30 || i76 == 44) {
                            if (zzcqVar2.zzc() == 1 || i77 != 0) {
                                i6 += 2;
                                int i92 = i67 / 3;
                                objArr[i92 + i92 + 1] = objArrZze[i90];
                            } else {
                                i6 = i90;
                                i77 = 0;
                            }
                        } else if (i76 == 50) {
                            int i93 = i6 + 2;
                            int i94 = i64 + 1;
                            iArr[i64] = i67;
                            int i95 = i67 / 3;
                            int i96 = i95 + i95;
                            objArr[i96] = objArrZze[i90];
                            if (i77 != 0) {
                                objArr[i96 + 1] = objArrZze[i93];
                                i6 += 3;
                                i64 = i94;
                            } else {
                                i6 = i93;
                                i64 = i94;
                                i77 = 0;
                            }
                        }
                        iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzn4);
                        if ((iCharAt11 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 || i76 > 17) {
                            c = 55296;
                            iObjectFieldOffset2 = 1048575;
                            i19 = 0;
                        } else {
                            int i97 = i17 + 1;
                            int iCharAt13 = strZzd.charAt(i17);
                            if (iCharAt13 >= 55296) {
                                int i98 = iCharAt13 & 8191;
                                int i99 = 13;
                                while (true) {
                                    i21 = i97 + 1;
                                    cCharAt9 = strZzd.charAt(i97);
                                    if (cCharAt9 < 55296) {
                                        break;
                                    }
                                    i98 |= (cCharAt9 & 8191) << i99;
                                    i99 += 13;
                                    i97 = i21;
                                }
                                iCharAt13 = i98 | (cCharAt9 << i99);
                            } else {
                                i21 = i97;
                            }
                            int i100 = i18 + i18 + (iCharAt13 / 32);
                            Object obj3 = objArrZze[i100];
                            if (obj3 instanceof Field) {
                                fieldZzn = (Field) obj3;
                            } else {
                                fieldZzn = zzn(cls2, (String) obj3);
                                objArrZze[i100] = fieldZzn;
                            }
                            int i101 = iCharAt13;
                            int iObjectFieldOffset3 = (int) unsafe.objectFieldOffset(fieldZzn);
                            i19 = i101 % 32;
                            i17 = i21;
                            c = 55296;
                            iObjectFieldOffset2 = iObjectFieldOffset3;
                        }
                        if (i76 >= 18 && i76 <= 49) {
                            iArr[i65] = iObjectFieldOffset;
                            i65++;
                        }
                        i20 = i77;
                    } else {
                        i22 = 1;
                        i6 += 2;
                    }
                    int i102 = i67 / 3;
                    objArr[i102 + i102 + i22] = objArrZze[i90];
                    iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzn4);
                    if ((iCharAt11 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0) {
                        c = 55296;
                        iObjectFieldOffset2 = 1048575;
                        i19 = 0;
                    } else {
                        c = 55296;
                        iObjectFieldOffset2 = 1048575;
                        i19 = 0;
                    }
                    if (i76 >= 18) {
                        iArr[i65] = iObjectFieldOffset;
                        i65++;
                    }
                    i20 = i77;
                }
                i6 = i90;
                iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzn4);
                if ((iCharAt11 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0) {
                    c = 55296;
                    iObjectFieldOffset2 = 1048575;
                    i19 = 0;
                } else {
                    c = 55296;
                    iObjectFieldOffset2 = 1048575;
                    i19 = 0;
                }
                if (i76 >= 18) {
                    iArr[i65] = iObjectFieldOffset;
                    i65++;
                }
                i20 = i77;
            }
            int i103 = i67 + 1;
            iArr3[i67] = iCharAt10;
            int i104 = i67 + 2;
            iArr3[i103] = ((iCharAt11 & 512) != 0 ? 536870912 : 0) | ((iCharAt11 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? 268435456 : 0) | (i20 != 0 ? Integer.MIN_VALUE : 0) | (i76 << 20) | iObjectFieldOffset;
            i67 += 3;
            iArr3[i104] = (i19 << 20) | iObjectFieldOffset2;
            i33 = i17;
            strZzd = strZzd;
            c2 = c;
            zzcqVar = zzcqVar2;
            length = length;
            i7 = i18;
        }
        return new zzch(iArr3, objArr, i2, i4, zzcqVar.zzb(), false, iArr, i5, i62, zzckVar, zzbrVar, zzdcVar, zzajVar, zzbzVar);
    }

    public static Field zzn(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException e) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String string = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 11 + name.length() + 29 + String.valueOf(string).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(string);
            throw new RuntimeException(sb.toString(), e);
        }
    }

    public final int zzA(int i) {
        return this.zzc[i + 1];
    }

    public final int zzB(int i) {
        return this.zzc[i + 2];
    }

    public final boolean zzL(Object obj, Object obj2, int i) {
        return zzN(obj, i) == zzN(obj2, i);
    }

    public final boolean zzM(Object obj, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zzN(obj, i);
        }
        return (i3 & i4) != 0;
    }

    public final boolean zzN(Object obj, int i) {
        int iZzB = zzB(i);
        long j = iZzB & 1048575;
        if (j != 1048575) {
            return ((1 << (iZzB >>> 20)) & zzdk.zzd(obj, j)) != 0;
        }
        int iZzA = zzA(i);
        long j2 = iZzA & 1048575;
        switch (zzC(iZzA)) {
            case 0:
                return Double.doubleToRawLongBits(zzdk.zzl(obj, j2)) != 0;
            case 1:
                return Float.floatToRawIntBits(zzdk.zzj(obj, j2)) != 0;
            case 2:
                return zzdk.zzf(obj, j2) != 0;
            case 3:
                return zzdk.zzf(obj, j2) != 0;
            case 4:
                return zzdk.zzd(obj, j2) != 0;
            case 5:
                return zzdk.zzf(obj, j2) != 0;
            case 6:
                return zzdk.zzd(obj, j2) != 0;
            case 7:
                return zzdk.zzh(obj, j2);
            case 8:
                Object objZzn = zzdk.zzn(obj, j2);
                if (objZzn instanceof String) {
                    return !((String) objZzn).isEmpty();
                }
                if (objZzn instanceof zzt) {
                    return !zzt.zza.equals(objZzn);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzdk.zzn(obj, j2) != null;
            case 10:
                return !zzt.zza.equals(zzdk.zzn(obj, j2));
            case 11:
                return zzdk.zzd(obj, j2) != 0;
            case 12:
                return zzdk.zzd(obj, j2) != 0;
            case 13:
                return zzdk.zzd(obj, j2) != 0;
            case 14:
                return zzdk.zzf(obj, j2) != 0;
            case 15:
                return zzdk.zzd(obj, j2) != 0;
            case 16:
                return zzdk.zzf(obj, j2) != 0;
            case 17:
                return zzdk.zzn(obj, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    public final boolean zzP(Object obj, int i, int i2) {
        return zzdk.zzd(obj, (long) (zzB(i2) & 1048575)) == i;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final boolean zzb(Object obj, Object obj2) {
        boolean zZzI;
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzA = zzA(i);
            long j = iZzA & 1048575;
            switch (zzC(iZzA)) {
                case 0:
                    if (!zzL(obj, obj2, i) || Double.doubleToLongBits(zzdk.zzl(obj, j)) != Double.doubleToLongBits(zzdk.zzl(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 1:
                    if (!zzL(obj, obj2, i) || Float.floatToIntBits(zzdk.zzj(obj, j)) != Float.floatToIntBits(zzdk.zzj(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 2:
                    if (!zzL(obj, obj2, i) || zzdk.zzf(obj, j) != zzdk.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 3:
                    if (!zzL(obj, obj2, i) || zzdk.zzf(obj, j) != zzdk.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 4:
                    if (!zzL(obj, obj2, i) || zzdk.zzd(obj, j) != zzdk.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 5:
                    if (!zzL(obj, obj2, i) || zzdk.zzf(obj, j) != zzdk.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 6:
                    if (!zzL(obj, obj2, i) || zzdk.zzd(obj, j) != zzdk.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 7:
                    if (!zzL(obj, obj2, i) || zzdk.zzh(obj, j) != zzdk.zzh(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 8:
                    if (!zzL(obj, obj2, i) || !zzct.zzI(zzdk.zzn(obj, j), zzdk.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 9:
                    if (!zzL(obj, obj2, i) || !zzct.zzI(zzdk.zzn(obj, j), zzdk.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 10:
                    if (!zzL(obj, obj2, i) || !zzct.zzI(zzdk.zzn(obj, j), zzdk.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 11:
                    if (!zzL(obj, obj2, i) || zzdk.zzd(obj, j) != zzdk.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 12:
                    if (!zzL(obj, obj2, i) || zzdk.zzd(obj, j) != zzdk.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 13:
                    if (!zzL(obj, obj2, i) || zzdk.zzd(obj, j) != zzdk.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 14:
                    if (!zzL(obj, obj2, i) || zzdk.zzf(obj, j) != zzdk.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 15:
                    if (!zzL(obj, obj2, i) || zzdk.zzd(obj, j) != zzdk.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 16:
                    if (!zzL(obj, obj2, i) || zzdk.zzf(obj, j) != zzdk.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 17:
                    if (!zzL(obj, obj2, i) || !zzct.zzI(zzdk.zzn(obj, j), zzdk.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    zZzI = zzct.zzI(zzdk.zzn(obj, j), zzdk.zzn(obj2, j));
                    break;
                case 50:
                    zZzI = zzct.zzI(zzdk.zzn(obj, j), zzdk.zzn(obj2, j));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case R.styleable.AppCompatTheme_editTextBackground /* 68 */:
                    long jZzB = zzB(i) & 1048575;
                    if (zzdk.zzd(obj, jZzB) != zzdk.zzd(obj2, jZzB) || !zzct.zzI(zzdk.zzn(obj, j), zzdk.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                default:
                    continue;
                    break;
            }
            if (!zZzI) {
                return false;
            }
        }
        zzdc zzdcVar = this.zzm;
        if (!zzdcVar.zzj(obj).equals(zzdcVar.zzj(obj2))) {
            return false;
        }
        if (!this.zzh) {
            return true;
        }
        zzaj zzajVar = this.zzn;
        return zzajVar.zzb(obj).equals(zzajVar.zzb(obj2));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final int zzc(Object obj) {
        int i;
        long jDoubleToLongBits;
        int iFloatToIntBits;
        int i2;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int[] iArr = this.zzc;
            if (i3 >= iArr.length) {
                int iHashCode = (i4 * 53) + this.zzm.zzj(obj).hashCode();
                return this.zzh ? (iHashCode * 53) + this.zzn.zzb(obj).zza.hashCode() : iHashCode;
            }
            int iZzA = zzA(i3);
            int i5 = 1048575 & iZzA;
            int iZzC = zzC(iZzA);
            int i6 = iArr[i3];
            long j = i5;
            int iHashCode2 = 37;
            switch (iZzC) {
                case 0:
                    i = i4 * 53;
                    jDoubleToLongBits = Double.doubleToLongBits(zzdk.zzl(obj, j));
                    byte[] bArr = zzbi.zza;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 1:
                    i = i4 * 53;
                    iFloatToIntBits = Float.floatToIntBits(zzdk.zzj(obj, j));
                    i4 = i + iFloatToIntBits;
                    break;
                case 2:
                    i = i4 * 53;
                    jDoubleToLongBits = zzdk.zzf(obj, j);
                    byte[] bArr2 = zzbi.zza;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 3:
                    i = i4 * 53;
                    jDoubleToLongBits = zzdk.zzf(obj, j);
                    byte[] bArr3 = zzbi.zza;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 4:
                    i = i4 * 53;
                    iFloatToIntBits = zzdk.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 5:
                    i = i4 * 53;
                    jDoubleToLongBits = zzdk.zzf(obj, j);
                    byte[] bArr4 = zzbi.zza;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 6:
                    i = i4 * 53;
                    iFloatToIntBits = zzdk.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 7:
                    i = i4 * 53;
                    iFloatToIntBits = zzbi.zzb(zzdk.zzh(obj, j));
                    i4 = i + iFloatToIntBits;
                    break;
                case 8:
                    i = i4 * 53;
                    iFloatToIntBits = ((String) zzdk.zzn(obj, j)).hashCode();
                    i4 = i + iFloatToIntBits;
                    break;
                case 9:
                    i2 = i4 * 53;
                    Object objZzn = zzdk.zzn(obj, j);
                    if (objZzn != null) {
                        iHashCode2 = objZzn.hashCode();
                    }
                    i4 = i2 + iHashCode2;
                    break;
                case 10:
                    i = i4 * 53;
                    iFloatToIntBits = zzdk.zzn(obj, j).hashCode();
                    i4 = i + iFloatToIntBits;
                    break;
                case 11:
                    i = i4 * 53;
                    iFloatToIntBits = zzdk.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 12:
                    i = i4 * 53;
                    iFloatToIntBits = zzdk.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 13:
                    i = i4 * 53;
                    iFloatToIntBits = zzdk.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 14:
                    i = i4 * 53;
                    jDoubleToLongBits = zzdk.zzf(obj, j);
                    byte[] bArr5 = zzbi.zza;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 15:
                    i = i4 * 53;
                    iFloatToIntBits = zzdk.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 16:
                    i = i4 * 53;
                    jDoubleToLongBits = zzdk.zzf(obj, j);
                    byte[] bArr6 = zzbi.zza;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 17:
                    i2 = i4 * 53;
                    Object objZzn2 = zzdk.zzn(obj, j);
                    if (objZzn2 != null) {
                        iHashCode2 = objZzn2.hashCode();
                    }
                    i4 = i2 + iHashCode2;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i4 * 53;
                    iFloatToIntBits = zzdk.zzn(obj, j).hashCode();
                    i4 = i + iFloatToIntBits;
                    break;
                case 50:
                    i = i4 * 53;
                    iFloatToIntBits = zzdk.zzn(obj, j).hashCode();
                    i4 = i + iFloatToIntBits;
                    break;
                case 51:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = Double.doubleToLongBits(zzG(obj, j));
                        byte[] bArr7 = zzbi.zza;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 52:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = Float.floatToIntBits(zzH(obj, j));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 53:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzJ(obj, j);
                        byte[] bArr8 = zzbi.zza;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 54:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzJ(obj, j);
                        byte[] bArr9 = zzbi.zza;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 55:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzI(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 56:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzJ(obj, j);
                        byte[] bArr10 = zzbi.zza;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 57:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzI(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 58:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzbi.zzb(zzK(obj, j));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 59:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = ((String) zzdk.zzn(obj, j)).hashCode();
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 60:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzdk.zzn(obj, j).hashCode();
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 61:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzdk.zzn(obj, j).hashCode();
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 62:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzI(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 63:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzI(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 64:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzI(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 65:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzJ(obj, j);
                        byte[] bArr11 = zzbi.zza;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 66:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzI(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 67:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzJ(obj, j);
                        byte[] bArr12 = zzbi.zza;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case R.styleable.AppCompatTheme_editTextBackground /* 68 */:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzdk.zzn(obj, j).hashCode();
                        i4 = i + iFloatToIntBits;
                    }
                    break;
            }
            i3 += 3;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:144:0x03af A[PHI: r17
  0x03af: PHI (r17v48 int) = (r17v31 int), (r17v32 int), (r17v35 int), (r17v41 int), (r17v42 int), (r17v45 int), (r17v49 int) binds: [B:214:0x057b, B:210:0x055d, B:204:0x0524, B:159:0x0420, B:155:0x0404, B:149:0x03ce, B:143:0x03ad] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:183:0x04b8 A[PHI: r17
  0x04b8: PHI (r17v37 int) = (r17v30 int), (r17v38 int) binds: [B:218:0x0599, B:182:0x04b6] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final int zze(Object obj) {
        int i;
        int i2;
        int iZzC;
        int iZzC2;
        int iZzC3;
        int iZzD;
        int iZzC4;
        int iZzC5;
        int iZzC6;
        int iZzc;
        int iZzC7;
        int i3;
        int iZzt;
        int size;
        int iZzu;
        int iZzC8;
        int iZzc2;
        int iZzC9;
        int iZzc3;
        int iZzC10;
        int iZzC11;
        int serializedSize;
        int iZzC12;
        int iZzC13;
        int iZzC14;
        int iZzC15;
        int iZzC16;
        int iZzF;
        int iZzC17;
        int iZzC18;
        int iZzC19;
        int iZzD2;
        int iZzC20;
        int iZzC21;
        int iZzc4;
        int iZzC22;
        zzch zzchVar = this;
        Object obj2 = obj;
        int i4 = 1;
        Unsafe unsafe = zzb;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 1048575;
        while (true) {
            int[] iArr = zzchVar.zzc;
            if (i5 >= iArr.length) {
                zzdc zzdcVar = zzchVar.zzm;
                int iZzs = i7 + zzdcVar.zzs(zzdcVar.zzj(obj2));
                if (!zzchVar.zzh) {
                    return iZzs;
                }
                zzcy zzcyVar = zzchVar.zzn.zzb(obj2).zza;
                int iZzc5 = zzcyVar.zzc();
                int iZzl = 0;
                for (int i9 = 0; i9 < iZzc5; i9++) {
                    Map.Entry entryZzd = zzcyVar.zzd(i9);
                    iZzl += zzan.zzl((zzam) ((zzcv) entryZzd).zza(), entryZzd.getValue());
                }
                for (Map.Entry entry : zzcyVar.zze()) {
                    iZzl += zzan.zzl((zzam) entry.getKey(), entry.getValue());
                }
                return iZzs + iZzl;
            }
            int iZzA = zzchVar.zzA(i5);
            int iZzC23 = zzC(iZzA);
            int i10 = iArr[i5];
            int i11 = iArr[i5 + 2];
            int i12 = i11 & 1048575;
            if (iZzC23 <= 17) {
                if (i12 != i8) {
                    i6 = i12 == 1048575 ? 0 : unsafe.getInt(obj2, i12);
                    i8 = i12;
                }
                i = i4 << (i11 >>> 20);
            } else {
                i = 0;
            }
            int i13 = iZzA & 1048575;
            if (iZzC23 >= zzao.zzJ.zza()) {
                zzao.zzW.zza();
            }
            long j = i13;
            switch (iZzC23) {
                case 0:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        iZzC = zzac.zzC(i10 << 3) + 8;
                        i7 += iZzC;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 1:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        iZzC2 = zzac.zzC(i10 << 3);
                        iZzC5 = iZzC2 + 4;
                        i7 += iZzC5;
                    }
                    zzchVar = this;
                    obj2 = obj;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 2:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        long j2 = unsafe.getLong(obj2, j);
                        iZzC3 = zzac.zzC(i10 << 3);
                        iZzD = zzac.zzD(j2);
                        i3 = iZzC3 + iZzD;
                        i7 += i3;
                    }
                    zzchVar = this;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 3:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        long j3 = unsafe.getLong(obj2, j);
                        iZzC3 = zzac.zzC(i10 << 3);
                        iZzD = zzac.zzD(j3);
                        i3 = iZzC3 + iZzD;
                        i7 += i3;
                    }
                    zzchVar = this;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 4:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        long j4 = unsafe.getInt(obj2, j);
                        iZzC3 = zzac.zzC(i10 << 3);
                        iZzD = zzac.zzD(j4);
                        i3 = iZzC3 + iZzD;
                        i7 += i3;
                    }
                    zzchVar = this;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 5:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        iZzC4 = zzac.zzC(i10 << 3);
                        iZzC5 = iZzC4 + 8;
                        i7 += iZzC5;
                    }
                    zzchVar = this;
                    obj2 = obj;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 6:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        iZzC2 = zzac.zzC(i10 << 3);
                        iZzC5 = iZzC2 + 4;
                        i7 += iZzC5;
                    }
                    zzchVar = this;
                    obj2 = obj;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 7:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        iZzC5 = zzac.zzC(i10 << 3) + 1;
                        i7 += iZzC5;
                    }
                    zzchVar = this;
                    obj2 = obj;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 8:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        int i14 = i10 << 3;
                        Object object = unsafe.getObject(obj2, j);
                        if (object instanceof zzt) {
                            iZzC6 = zzac.zzC(i14);
                            iZzc = ((zzt) object).zzb();
                            iZzC7 = zzac.zzC(iZzc);
                        } else {
                            iZzC6 = zzac.zzC(i14);
                            iZzc = zzdq.zzc((String) object);
                            iZzC7 = zzac.zzC(iZzc);
                        }
                        i3 = iZzC6 + iZzC7 + iZzc;
                        i7 += i3;
                    }
                    zzchVar = this;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 9:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        iZzC = zzct.zzE(i10, unsafe.getObject(obj2, j), zzchVar.zzq(i5));
                        i7 += iZzC;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 10:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        zzt zztVar = (zzt) unsafe.getObject(obj2, j);
                        iZzC6 = zzac.zzC(i10 << 3);
                        iZzc = zztVar.zzb();
                        iZzC7 = zzac.zzC(iZzc);
                        i3 = iZzC6 + iZzC7 + iZzc;
                        i7 += i3;
                    }
                    zzchVar = this;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 11:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        int i15 = unsafe.getInt(obj2, j);
                        iZzC3 = zzac.zzC(i10 << 3);
                        iZzD = zzac.zzC(i15);
                        i3 = iZzC3 + iZzD;
                        i7 += i3;
                    }
                    zzchVar = this;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 12:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        long j5 = unsafe.getInt(obj2, j);
                        iZzC3 = zzac.zzC(i10 << 3);
                        iZzD = zzac.zzD(j5);
                        i3 = iZzC3 + iZzD;
                        i7 += i3;
                    }
                    zzchVar = this;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 13:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        iZzC2 = zzac.zzC(i10 << 3);
                        iZzC5 = iZzC2 + 4;
                        i7 += iZzC5;
                    }
                    zzchVar = this;
                    obj2 = obj;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 14:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        iZzC4 = zzac.zzC(i10 << 3);
                        iZzC5 = iZzC4 + 8;
                        i7 += iZzC5;
                    }
                    zzchVar = this;
                    obj2 = obj;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 15:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        int i16 = unsafe.getInt(obj2, j);
                        iZzC3 = zzac.zzC(i10 << 3);
                        iZzD = zzac.zzC((i16 >> 31) ^ (i16 + i16));
                        i3 = iZzC3 + iZzD;
                        i7 += i3;
                    }
                    zzchVar = this;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 16:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        long j6 = unsafe.getLong(obj2, j);
                        iZzC3 = zzac.zzC(i10 << 3);
                        iZzD = zzac.zzD((j6 >> 63) ^ (j6 + j6));
                        i3 = iZzC3 + iZzD;
                        i7 += i3;
                    }
                    zzchVar = this;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 17:
                    i2 = i4;
                    if (zzchVar.zzM(obj2, i5, i8, i6, i)) {
                        iZzC = zzct.zzF(i10, (zzce) unsafe.getObject(obj2, j), zzchVar.zzq(i5));
                        i7 += iZzC;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 18:
                    i2 = i4;
                    iZzC = zzct.zzD(i10, (List) unsafe.getObject(obj2, j), false);
                    i7 += iZzC;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 19:
                    i2 = i4;
                    iZzC = zzct.zzB(i10, (List) unsafe.getObject(obj2, j), false);
                    i7 += iZzC;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 20:
                    i2 = i4;
                    List list = (List) unsafe.getObject(obj2, j);
                    Class cls = zzct.zzb;
                    if (list.size() == 0) {
                        iZzt = 0;
                    } else {
                        iZzt = zzct.zzt(list) + (list.size() * zzac.zzC(i10 << 3));
                    }
                    i7 += iZzt;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 21:
                    i2 = i4;
                    List list2 = (List) unsafe.getObject(obj2, j);
                    Class cls2 = zzct.zzb;
                    size = list2.size();
                    if (size == 0) {
                        iZzC = 0;
                    } else {
                        iZzu = zzct.zzu(list2);
                        iZzC8 = zzac.zzC(i10 << 3);
                        iZzC = iZzu + (size * iZzC8);
                    }
                    i7 += iZzC;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 22:
                    i2 = i4;
                    List list3 = (List) unsafe.getObject(obj2, j);
                    Class cls3 = zzct.zzb;
                    size = list3.size();
                    if (size == 0) {
                        iZzC = 0;
                    } else {
                        iZzu = zzct.zzx(list3);
                        iZzC8 = zzac.zzC(i10 << 3);
                        iZzC = iZzu + (size * iZzC8);
                    }
                    i7 += iZzC;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 23:
                    i2 = i4;
                    iZzC = zzct.zzD(i10, (List) unsafe.getObject(obj2, j), false);
                    i7 += iZzC;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 24:
                    i2 = i4;
                    iZzC = zzct.zzB(i10, (List) unsafe.getObject(obj2, j), false);
                    i7 += iZzC;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 25:
                    i2 = i4;
                    List list4 = (List) unsafe.getObject(obj2, j);
                    Class cls4 = zzct.zzb;
                    int size2 = list4.size();
                    if (size2 == 0) {
                        iZzC = 0;
                    } else {
                        iZzC = size2 * (zzac.zzC(i10 << 3) + 1);
                    }
                    i7 += iZzC;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 26:
                    i2 = i4;
                    List list5 = (List) unsafe.getObject(obj2, j);
                    Class cls5 = zzct.zzb;
                    int size3 = list5.size();
                    if (size3 == 0) {
                        iZzt = 0;
                    } else {
                        iZzt = zzac.zzC(i10 << 3) * size3;
                        if (list5 instanceof zzbq) {
                            zzbq zzbqVar = (zzbq) list5;
                            for (int i17 = 0; i17 < size3; i17++) {
                                Object objZzc = zzbqVar.zzc();
                                if (objZzc instanceof zzt) {
                                    iZzc3 = ((zzt) objZzc).zzb();
                                    iZzC10 = zzac.zzC(iZzc3);
                                } else {
                                    iZzc3 = zzdq.zzc((String) objZzc);
                                    iZzC10 = zzac.zzC(iZzc3);
                                }
                                iZzt += iZzC10 + iZzc3;
                            }
                        } else {
                            for (int i18 = 0; i18 < size3; i18++) {
                                Object obj3 = list5.get(i18);
                                if (obj3 instanceof zzt) {
                                    iZzc2 = ((zzt) obj3).zzb();
                                    iZzC9 = zzac.zzC(iZzc2);
                                } else {
                                    iZzc2 = zzdq.zzc((String) obj3);
                                    iZzC9 = zzac.zzC(iZzc2);
                                }
                                iZzt += iZzC9 + iZzc2;
                            }
                        }
                    }
                    i7 += iZzt;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 27:
                    i2 = i4;
                    List list6 = (List) unsafe.getObject(obj2, j);
                    zzcs zzcsVarZzq = zzchVar.zzq(i5);
                    Class cls6 = zzct.zzb;
                    int size4 = list6.size();
                    if (size4 == 0) {
                        iZzC11 = 0;
                    } else {
                        iZzC11 = zzac.zzC(i10 << 3) * size4;
                        for (int i19 = 0; i19 < size4; i19++) {
                            Object obj4 = list6.get(i19);
                            if (obj4 instanceof zzbp) {
                                serializedSize = ((zzbp) obj4).zzb();
                                iZzC12 = zzac.zzC(serializedSize);
                            } else {
                                serializedSize = ((zzc) obj4).getSerializedSize(zzcsVarZzq);
                                iZzC12 = zzac.zzC(serializedSize);
                            }
                            iZzC11 += iZzC12 + serializedSize;
                        }
                    }
                    i7 += iZzC11;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 28:
                    i2 = i4;
                    List list7 = (List) unsafe.getObject(obj2, j);
                    Class cls7 = zzct.zzb;
                    int size5 = list7.size();
                    if (size5 == 0) {
                        iZzC13 = 0;
                    } else {
                        iZzC13 = size5 * zzac.zzC(i10 << 3);
                        for (int i20 = 0; i20 < list7.size(); i20++) {
                            int iZzb = ((zzt) list7.get(i20)).zzb();
                            iZzC13 += zzac.zzC(iZzb) + iZzb;
                        }
                    }
                    i7 += iZzC13;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 29:
                    i2 = i4;
                    List list8 = (List) unsafe.getObject(obj2, j);
                    Class cls8 = zzct.zzb;
                    size = list8.size();
                    if (size == 0) {
                        iZzC = 0;
                    } else {
                        iZzu = zzct.zzy(list8);
                        iZzC8 = zzac.zzC(i10 << 3);
                        iZzC = iZzu + (size * iZzC8);
                    }
                    i7 += iZzC;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 30:
                    i2 = i4;
                    List list9 = (List) unsafe.getObject(obj2, j);
                    Class cls9 = zzct.zzb;
                    size = list9.size();
                    if (size == 0) {
                        iZzC = 0;
                    } else {
                        iZzu = zzct.zzw(list9);
                        iZzC8 = zzac.zzC(i10 << 3);
                        iZzC = iZzu + (size * iZzC8);
                    }
                    i7 += iZzC;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 31:
                    i2 = i4;
                    iZzC = zzct.zzB(i10, (List) unsafe.getObject(obj2, j), false);
                    i7 += iZzC;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 32:
                    i2 = i4;
                    iZzC = zzct.zzD(i10, (List) unsafe.getObject(obj2, j), false);
                    i7 += iZzC;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 33:
                    i2 = i4;
                    List list10 = (List) unsafe.getObject(obj2, j);
                    Class cls10 = zzct.zzb;
                    size = list10.size();
                    if (size == 0) {
                        iZzC = 0;
                    } else {
                        iZzu = zzct.zzz(list10);
                        iZzC8 = zzac.zzC(i10 << 3);
                        iZzC = iZzu + (size * iZzC8);
                    }
                    i7 += iZzC;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 34:
                    i2 = i4;
                    List list11 = (List) unsafe.getObject(obj2, j);
                    Class cls11 = zzct.zzb;
                    size = list11.size();
                    if (size == 0) {
                        iZzC = 0;
                    } else {
                        iZzu = zzct.zzv(list11);
                        iZzC8 = zzac.zzC(i10 << 3);
                        iZzC = iZzu + (size * iZzC8);
                    }
                    i7 += iZzC;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 35:
                    i2 = i4;
                    iZzC14 = zzct.zzC((List) unsafe.getObject(obj2, j));
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 36:
                    i2 = i4;
                    iZzC14 = zzct.zzA((List) unsafe.getObject(obj2, j));
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 37:
                    i2 = i4;
                    iZzC14 = zzct.zzt((List) unsafe.getObject(obj2, j));
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 38:
                    i2 = i4;
                    iZzC14 = zzct.zzu((List) unsafe.getObject(obj2, j));
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 39:
                    i2 = i4;
                    iZzC14 = zzct.zzx((List) unsafe.getObject(obj2, j));
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 40:
                    i2 = i4;
                    iZzC14 = zzct.zzC((List) unsafe.getObject(obj2, j));
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 41:
                    i2 = i4;
                    iZzC14 = zzct.zzA((List) unsafe.getObject(obj2, j));
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 42:
                    i2 = i4;
                    List list12 = (List) unsafe.getObject(obj2, j);
                    Class cls12 = zzct.zzb;
                    iZzC14 = list12.size();
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 43:
                    i2 = i4;
                    iZzC14 = zzct.zzy((List) unsafe.getObject(obj2, j));
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 44:
                    i2 = i4;
                    iZzC14 = zzct.zzw((List) unsafe.getObject(obj2, j));
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 45:
                    i2 = i4;
                    iZzC14 = zzct.zzA((List) unsafe.getObject(obj2, j));
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 46:
                    i2 = i4;
                    iZzC14 = zzct.zzC((List) unsafe.getObject(obj2, j));
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 47:
                    i2 = i4;
                    iZzC14 = zzct.zzz((List) unsafe.getObject(obj2, j));
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 48:
                    i2 = i4;
                    iZzC14 = zzct.zzv((List) unsafe.getObject(obj2, j));
                    if (iZzC14 > 0) {
                        iZzC15 = zzac.zzC(i10 << 3);
                        iZzC16 = zzac.zzC(iZzC14);
                        iZzC13 = iZzC15 + iZzC16 + iZzC14;
                        i7 += iZzC13;
                    }
                    i5 += 3;
                    i4 = i2;
                    break;
                case 49:
                    List list13 = (List) unsafe.getObject(obj2, j);
                    zzcs zzcsVarZzq2 = zzchVar.zzq(i5);
                    Class cls13 = zzct.zzb;
                    int size6 = list13.size();
                    if (size6 == 0) {
                        iZzF = 0;
                    } else {
                        int i21 = 0;
                        iZzF = 0;
                        while (i21 < size6) {
                            iZzF += zzct.zzF(i10, (zzce) list13.get(i21), zzcsVarZzq2);
                            i21++;
                            i4 = i4;
                        }
                    }
                    i2 = i4;
                    i7 += iZzF;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 50:
                    zzby zzbyVar = (zzby) unsafe.getObject(obj2, j);
                    if (!zzbyVar.isEmpty()) {
                        Iterator it = zzbyVar.entrySet().iterator();
                        if (it.hasNext()) {
                            Map.Entry entry2 = (Map.Entry) it.next();
                            entry2.getKey();
                            entry2.getValue();
                            throw null;
                        }
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 51:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        iZzC17 = zzac.zzC(i10 << 3);
                        iZzC20 = iZzC17 + 8;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 52:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        iZzC18 = zzac.zzC(i10 << 3);
                        iZzC20 = iZzC18 + 4;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 53:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        long jZzJ = zzJ(obj2, j);
                        iZzC19 = zzac.zzC(i10 << 3);
                        iZzD2 = zzac.zzD(jZzJ);
                        iZzC20 = iZzC19 + iZzD2;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 54:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        long jZzJ2 = zzJ(obj2, j);
                        iZzC19 = zzac.zzC(i10 << 3);
                        iZzD2 = zzac.zzD(jZzJ2);
                        iZzC20 = iZzC19 + iZzD2;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 55:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        long jZzI = zzI(obj2, j);
                        iZzC19 = zzac.zzC(i10 << 3);
                        iZzD2 = zzac.zzD(jZzI);
                        iZzC20 = iZzC19 + iZzD2;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 56:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        iZzC17 = zzac.zzC(i10 << 3);
                        iZzC20 = iZzC17 + 8;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 57:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        iZzC18 = zzac.zzC(i10 << 3);
                        iZzC20 = iZzC18 + 4;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 58:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        iZzC20 = zzac.zzC(i10 << 3) + i4;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 59:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        int i22 = i10 << 3;
                        Object object2 = unsafe.getObject(obj2, j);
                        if (object2 instanceof zzt) {
                            iZzC21 = zzac.zzC(i22);
                            iZzc4 = ((zzt) object2).zzb();
                            iZzC22 = zzac.zzC(iZzc4);
                        } else {
                            iZzC21 = zzac.zzC(i22);
                            iZzc4 = zzdq.zzc((String) object2);
                            iZzC22 = zzac.zzC(iZzc4);
                        }
                        iZzC20 = iZzC21 + iZzC22 + iZzc4;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 60:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        iZzC20 = zzct.zzE(i10, unsafe.getObject(obj2, j), zzchVar.zzq(i5));
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 61:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        zzt zztVar2 = (zzt) unsafe.getObject(obj2, j);
                        iZzC21 = zzac.zzC(i10 << 3);
                        iZzc4 = zztVar2.zzb();
                        iZzC22 = zzac.zzC(iZzc4);
                        iZzC20 = iZzC21 + iZzC22 + iZzc4;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 62:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        int iZzI = zzI(obj2, j);
                        iZzC19 = zzac.zzC(i10 << 3);
                        iZzD2 = zzac.zzC(iZzI);
                        iZzC20 = iZzC19 + iZzD2;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 63:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        long jZzI2 = zzI(obj2, j);
                        iZzC19 = zzac.zzC(i10 << 3);
                        iZzD2 = zzac.zzD(jZzI2);
                        iZzC20 = iZzC19 + iZzD2;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 64:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        iZzC18 = zzac.zzC(i10 << 3);
                        iZzC20 = iZzC18 + 4;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 65:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        iZzC17 = zzac.zzC(i10 << 3);
                        iZzC20 = iZzC17 + 8;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 66:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        int iZzI2 = zzI(obj2, j);
                        iZzC19 = zzac.zzC(i10 << 3);
                        iZzD2 = zzac.zzC((iZzI2 >> 31) ^ (iZzI2 + iZzI2));
                        iZzC20 = iZzC19 + iZzD2;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case 67:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        long jZzJ3 = zzJ(obj2, j);
                        iZzC19 = zzac.zzC(i10 << 3);
                        iZzD2 = zzac.zzD((jZzJ3 >> 63) ^ (jZzJ3 + jZzJ3));
                        iZzC20 = iZzC19 + iZzD2;
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                case R.styleable.AppCompatTheme_editTextBackground /* 68 */:
                    if (zzchVar.zzP(obj2, i10, i5)) {
                        iZzC20 = zzct.zzF(i10, (zzce) unsafe.getObject(obj2, j), zzchVar.zzq(i5));
                        i7 += iZzC20;
                    }
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
                default:
                    i2 = i4;
                    i5 += 3;
                    i4 = i2;
                    break;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:7:0x0024  */
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzf(Object obj, zzdt zzdtVar) {
        Map.Entry entry;
        Iterator it;
        boolean z;
        int i;
        int i2;
        int i3;
        int i4;
        zzch zzchVar = this;
        if (zzchVar.zzh) {
            zzan zzanVarZzb = zzchVar.zzn.zzb(obj);
            if (zzanVarZzb.zza.isEmpty()) {
                entry = null;
                it = null;
            } else {
                Iterator itZzc = zzanVarZzb.zzc();
                entry = (Map.Entry) itZzc.next();
                it = itZzc;
            }
        } else {
            entry = null;
            it = null;
        }
        int[] iArr = zzchVar.zzc;
        Unsafe unsafe = zzb;
        int i5 = 1048575;
        int i6 = 1048575;
        int i7 = 0;
        int i8 = 0;
        while (i7 < iArr.length) {
            int iZzA = zzchVar.zzA(i7);
            int iZzC = zzC(iZzA);
            int i9 = iArr[i7];
            if (iZzC <= 17) {
                int i10 = iArr[i7 + 2];
                z = true;
                int i11 = i10 & i5;
                if (i11 != i6) {
                    i8 = i11 == i5 ? 0 : unsafe.getInt(obj, i11);
                    i6 = i11;
                }
                i = i6;
                i2 = i8;
                i3 = 1 << (i10 >>> 20);
            } else {
                z = true;
                i = i6;
                i2 = i8;
                i3 = 0;
            }
            while (true) {
                if (entry != null) {
                    zzaj zzajVar = zzchVar.zzn;
                    i4 = i5;
                    if (zzajVar.zzf(entry) <= i9) {
                        zzajVar.zzg(zzdtVar, entry);
                        entry = it.hasNext() ? (Map.Entry) it.next() : null;
                        i5 = i4;
                    }
                } else {
                    i4 = i5;
                }
            }
            long j = iZzA & i4;
            switch (iZzC) {
                case 0:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzf(i9, zzdk.zzl(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 1:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zze(i9, zzdk.zzj(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 2:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzc(i9, unsafe.getLong(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 3:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzh(i9, unsafe.getLong(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 4:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzi(i9, unsafe.getInt(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 5:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzj(i9, unsafe.getLong(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 6:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzk(i9, unsafe.getInt(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 7:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzl(i9, zzdk.zzh(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 8:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzT(i9, unsafe.getObject(obj, j), zzdtVar);
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 9:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzr(i9, unsafe.getObject(obj, j), zzchVar.zzq(i7));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 10:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzn(i9, (zzt) unsafe.getObject(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 11:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzo(i9, unsafe.getInt(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 12:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzg(i9, unsafe.getInt(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 13:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzb(i9, unsafe.getInt(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 14:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzd(i9, unsafe.getLong(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 15:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzp(i9, unsafe.getInt(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 16:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzq(i9, unsafe.getLong(obj, j));
                    }
                    zzchVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 17:
                    if (zzchVar.zzM(obj, i7, i, i2, i3)) {
                        zzdtVar.zzs(i9, unsafe.getObject(obj, j), zzchVar.zzq(i7));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 18:
                    zzct.zzb(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 19:
                    zzct.zzc(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 20:
                    zzct.zzd(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 21:
                    zzct.zze(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 22:
                    zzct.zzi(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 23:
                    zzct.zzg(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 24:
                    zzct.zzl(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 25:
                    zzct.zzo(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 26:
                    zzct.zzp(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 27:
                    zzct.zzr(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, zzchVar.zzq(i7));
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 28:
                    zzct.zzq(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 29:
                    zzct.zzj(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 30:
                    zzct.zzn(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 31:
                    zzct.zzm(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 32:
                    zzct.zzh(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 33:
                    zzct.zzk(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 34:
                    zzct.zzf(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 35:
                    zzct.zzb(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 36:
                    zzct.zzc(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 37:
                    zzct.zzd(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 38:
                    zzct.zze(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 39:
                    zzct.zzi(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 40:
                    zzct.zzg(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 41:
                    zzct.zzl(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 42:
                    zzct.zzo(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 43:
                    zzct.zzj(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 44:
                    zzct.zzn(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 45:
                    zzct.zzm(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 46:
                    zzct.zzh(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 47:
                    zzct.zzk(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 48:
                    zzct.zzf(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 49:
                    zzct.zzs(iArr[i7], (List) unsafe.getObject(obj, j), zzdtVar, zzchVar.zzq(i7));
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 50:
                    if (unsafe.getObject(obj, j) != null) {
                        throw null;
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 51:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzf(i9, zzG(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 52:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zze(i9, zzH(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 53:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzc(i9, zzJ(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 54:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzh(i9, zzJ(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 55:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzi(i9, zzI(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 56:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzj(i9, zzJ(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 57:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzk(i9, zzI(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 58:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzl(i9, zzK(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 59:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzT(i9, unsafe.getObject(obj, j), zzdtVar);
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 60:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzr(i9, unsafe.getObject(obj, j), zzchVar.zzq(i7));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 61:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzn(i9, (zzt) unsafe.getObject(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 62:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzo(i9, zzI(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 63:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzg(i9, zzI(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 64:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzb(i9, zzI(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 65:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzd(i9, zzJ(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 66:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzp(i9, zzI(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 67:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzq(i9, zzJ(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case R.styleable.AppCompatTheme_editTextBackground /* 68 */:
                    if (zzchVar.zzP(obj, i9, i7)) {
                        zzdtVar.zzs(i9, unsafe.getObject(obj, j), zzchVar.zzq(i7));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                default:
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
            }
        }
        while (entry != null) {
            zzchVar.zzn.zzg(zzdtVar, entry);
            entry = it.hasNext() ? (Map.Entry) it.next() : null;
        }
        zzdc zzdcVar = zzchVar.zzm;
        zzdcVar.zzo(zzdcVar.zzj(obj), zzdtVar);
    }

    /* JADX WARN: Code duplicated, block: B:26:0x006d  */
    /* JADX WARN: Code duplicated, block: B:28:0x0073  */
    /* JADX WARN: Code duplicated, block: B:41:0x0080 A[SYNTHETIC] */
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzk(Object obj) {
        if (zzE(obj)) {
            if (obj instanceof zzax) {
                zzax zzaxVar = (zzax) obj;
                zzaxVar.clearMemoizedSerializedSize();
                zzaxVar.clearMemoizedHashCode();
                zzaxVar.markImmutable();
            }
            int[] iArr = this.zzc;
            for (int i = 0; i < iArr.length; i += 3) {
                int iZzA = zzA(i);
                int i2 = 1048575 & iZzA;
                int iZzC = zzC(iZzA);
                long j = i2;
                if (iZzC != 9) {
                    if (iZzC != 60 && iZzC != 68) {
                        switch (iZzC) {
                            case 17:
                                if (zzN(obj, i)) {
                                    zzq(i).zzk(zzb.getObject(obj, j));
                                }
                                break;
                            case 18:
                            case 19:
                            case 20:
                            case 21:
                            case 22:
                            case 23:
                            case 24:
                            case 25:
                            case 26:
                            case 27:
                            case 28:
                            case 29:
                            case 30:
                            case 31:
                            case 32:
                            case 33:
                            case 34:
                            case 35:
                            case 36:
                            case 37:
                            case 38:
                            case 39:
                            case 40:
                            case 41:
                            case 42:
                            case 43:
                            case 44:
                            case 45:
                            case 46:
                            case 47:
                            case 48:
                            case 49:
                                ((zzbh) zzdk.zzn(obj, j)).zzb();
                                break;
                            case 50:
                                Unsafe unsafe = zzb;
                                Object object = unsafe.getObject(obj, j);
                                if (object != null) {
                                    ((zzby) object).zzd();
                                    unsafe.putObject(obj, j, object);
                                }
                                break;
                        }
                    } else if (zzP(obj, iArr[i], i)) {
                        zzq(i).zzk(zzb.getObject(obj, j));
                    }
                } else if (zzN(obj, i)) {
                    zzq(i).zzk(zzb.getObject(obj, j));
                }
            }
            this.zzm.zzm(obj);
            if (this.zzh) {
                this.zzn.zzd(obj);
            }
        }
    }

    public final zzcs zzq(int i) {
        Object[] objArr = this.zzd;
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzcs zzcsVar = (zzcs) objArr[i3];
        if (zzcsVar != null) {
            return zzcsVar;
        }
        zzcs zzcsVarZzb = zzco.zza().zzb((Class) objArr[i3 + 1]);
        objArr[i3] = zzcsVarZzb;
        return zzcsVarZzb;
    }

    public final Object zzr(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }
}
