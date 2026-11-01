package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
abstract class zzu extends zzx {
    public final byte[] zzf;
    public int zzg;
    public int zzh;
    public int zzi;
    public final int zzj;
    public int zzl;

    public /* synthetic */ zzu(byte[] bArr, int i, int i2, boolean z, byte[] bArr2) {
        super(null);
        this.zzl = Integer.MAX_VALUE;
        this.zzf = bArr;
        this.zzg = i2 + i;
        this.zzi = i;
        this.zzj = i;
    }

    public final int zzB(int i) {
        if (i < 0) {
            throw new zzbk("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        int i2 = i + (this.zzi - this.zzj);
        if (i2 < 0) {
            throw new zzbk("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit. If reading multiple messages, consider resetting the counter between each message using CodedInputStream.resetSizeCounter().");
        }
        int i3 = this.zzl;
        if (i2 > i3) {
            throw new zzbk("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        this.zzl = i2;
        zzP();
        return i3;
    }

    public final void zzP() {
        int i = this.zzg + this.zzh;
        this.zzg = i;
        int i2 = i - this.zzj;
        int i3 = this.zzl;
        if (i2 <= i3) {
            this.zzh = 0;
            return;
        }
        int i4 = i2 - i3;
        this.zzh = i4;
        this.zzg = i - i4;
    }
}
