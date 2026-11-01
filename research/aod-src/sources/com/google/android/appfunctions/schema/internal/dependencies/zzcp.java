package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
final class zzcp extends zze implements RandomAccess {
    public static final Object[] zza;
    public static final zzcp zzb;
    public Object[] zzc;
    public int zzd;

    static {
        Object[] objArr = new Object[0];
        zza = objArr;
        zzb = new zzcp(objArr, 0, false);
    }

    public zzcp(Object[] objArr, int i, boolean z) {
        super(z);
        this.zzc = objArr;
        this.zzd = i;
    }

    public static zzcp zzd() {
        return zzb;
    }

    private static int zzf(int i) {
        return Math.max(((i * 3) / 2) + 1, 10);
    }

    private final void zzg(int i) {
        if (i < 0 || i >= this.zzd) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        int i2;
        zzu();
        if (i < 0 || i > (i2 = this.zzd)) {
            throw new IndexOutOfBoundsException(zzi(i));
        }
        int i3 = i + 1;
        Object[] objArr = this.zzc;
        int length = objArr.length;
        if (i2 < length) {
            System.arraycopy(objArr, i, objArr, i3, i2 - i);
        } else {
            Object[] objArr2 = new Object[zzf(length)];
            System.arraycopy(this.zzc, 0, objArr2, 0, i);
            System.arraycopy(this.zzc, i, objArr2, i3, this.zzd - i);
            this.zzc = objArr2;
        }
        this.zzc[i] = obj;
        this.zzd++;
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        zzu();
        int i = this.zzd;
        int length = this.zzc.length;
        if (i == length) {
            this.zzc = Arrays.copyOf(this.zzc, zzf(length));
        }
        Object[] objArr = this.zzc;
        int i2 = this.zzd;
        this.zzd = i2 + 1;
        objArr[i2] = obj;
        ((AbstractList) this).modCount++;
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        zzg(i);
        return this.zzc[i];
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.List
    public final Object remove(int i) {
        zzu();
        zzg(i);
        Object[] objArr = this.zzc;
        Object obj = objArr[i];
        int i2 = this.zzd;
        if (i < i2 - 1) {
            System.arraycopy(objArr, i + 1, objArr, i, (i2 - i) - 1);
        }
        this.zzd--;
        ((AbstractList) this).modCount++;
        return obj;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        zzu();
        zzg(i);
        Object[] objArr = this.zzc;
        Object obj2 = objArr[i];
        objArr[i] = obj;
        ((AbstractList) this).modCount++;
        return obj2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzd;
    }

    public final String zzi(int i) {
        return zzg.zza(this.zzd, i, (byte) 13, "Index:", ", Size:");
    }
}
