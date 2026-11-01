package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
public abstract class zzt implements Iterable<Byte>, Serializable {
    public static final zzt zza = new zzr(zzbi.zza);
    public static final zzo zzb;
    public int zzc = 0;

    static {
        zzb = zzf.zza() ? new zzs(null) : new zzm(null);
    }

    public static int zzs(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(String.valueOf(i).length() + 21);
            sb.append("Beginning index: ");
            sb.append(i);
            sb.append(" < 0");
            throw new IndexOutOfBoundsException(sb.toString());
        }
        if (i2 < i) {
            StringBuilder sb2 = new StringBuilder(String.valueOf(i).length() + 44 + String.valueOf(i2).length());
            sb2.append("Beginning index larger than ending index: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            throw new IndexOutOfBoundsException(sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder(String.valueOf(i2).length() + 15 + String.valueOf(i3).length());
        sb3.append("End index: ");
        sb3.append(i2);
        sb3.append(" >= ");
        sb3.append(i3);
        throw new IndexOutOfBoundsException(sb3.toString());
    }

    public static /* synthetic */ boolean zzt(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        int i4 = i + i3;
        zzs(i, i4, bArr.length);
        zzs(i2, i3 + i2, bArr2.length);
        while (i < i4) {
            if (bArr[i] != bArr2[i2]) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzt)) {
            return false;
        }
        zzt zztVar = (zzt) obj;
        int iZzb = zzb();
        if (iZzb != zztVar.zzb()) {
            return false;
        }
        if (iZzb == 0) {
            return true;
        }
        int i = this.zzc;
        int i2 = zztVar.zzc;
        if (i == 0 || i2 == 0 || i == i2) {
            return zzh(zztVar);
        }
        return false;
    }

    public final int hashCode() {
        int iZzi = this.zzc;
        if (iZzi == 0) {
            int iZzb = zzb();
            iZzi = zzi(iZzb, 0, iZzb);
            if (iZzi == 0) {
                iZzi = 1;
            }
            this.zzc = iZzi;
        }
        return iZzi;
    }

    @Override // java.lang.Iterable
    public final /* synthetic */ Iterator<Byte> iterator() {
        return new zzk(this);
    }

    public final String toString() {
        return String.format(Locale.ROOT, "<ByteString@%s size=%d contents=\"%s\">", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(zzb()), zzb() <= 50 ? zzda.zza(zzq()) : zzda.zza(zzc(0, 47).zzq()).concat("..."));
    }

    public abstract byte zza(int i);

    public abstract int zzb();

    public abstract zzt zzc(int i, int i2);

    public abstract void zzd(byte[] bArr, int i, int i2, int i3);

    public abstract void zze(zzj zzjVar);

    public abstract boolean zzh(zzt zztVar);

    public abstract int zzi(int i, int i2, int i3);

    public final byte[] zzq() {
        int iZzb = zzb();
        if (iZzb == 0) {
            return zzbi.zza;
        }
        byte[] bArr = new byte[iZzb];
        zzd(bArr, 0, 0, iZzb);
        return bArr;
    }
}
