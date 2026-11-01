package androidx.appsearch.app;

import androidx.appsearch.safeparcel.PackageIdentifierParcel;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public class PackageIdentifier {
    public final PackageIdentifierParcel mPackageIdentifierParcel;

    public PackageIdentifier(PackageIdentifierParcel packageIdentifierParcel) {
        this.mPackageIdentifierParcel = (PackageIdentifierParcel) Preconditions.checkNotNull(packageIdentifierParcel);
    }

    public PackageIdentifier(String str, byte[] bArr) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(bArr);
        this.mPackageIdentifierParcel = new PackageIdentifierParcel(str, bArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PackageIdentifier) {
            return this.mPackageIdentifierParcel.equals(((PackageIdentifier) obj).getPackageIdentifierParcel());
        }
        return false;
    }

    public PackageIdentifierParcel getPackageIdentifierParcel() {
        return this.mPackageIdentifierParcel;
    }

    public String getPackageName() {
        return this.mPackageIdentifierParcel.getPackageName();
    }

    public byte[] getSha256Certificate() {
        return this.mPackageIdentifierParcel.getSha256Certificate();
    }

    public int hashCode() {
        return this.mPackageIdentifierParcel.hashCode();
    }
}
