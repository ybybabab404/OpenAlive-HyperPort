package androidx.appsearch.app;

import android.os.Parcelable;
import androidx.appsearch.flags.FlaggedApi;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.PackageIdentifierParcel;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.collection.ArraySet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@FlaggedApi
@SafeParcelable.Class
public final class SchemaVisibilityConfig extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.VisibilityConfigCreator();
    public final List mAllowedPackages;
    public List mAllowedPackagesCached;
    public Integer mHashCode;
    public final PackageIdentifierParcel mPubliclyVisibleTargetPackage;
    public final List mRequiredPermissions;
    public Set mRequiredPermissionsCached;

    @FlaggedApi
    public static final class Builder {
        public boolean mBuilt;
        public PackageIdentifierParcel mPubliclyVisibleTargetPackage;
        public List mAllowedPackages = new ArrayList();
        public List mRequiredPermissions = new ArrayList();

        public Builder addAllowedPackage(PackageIdentifier packageIdentifier) {
            Objects.requireNonNull(packageIdentifier);
            resetIfBuilt();
            this.mAllowedPackages.add(packageIdentifier.getPackageIdentifierParcel());
            return this;
        }

        public Builder addRequiredPermissions(Set set) {
            Objects.requireNonNull(set);
            resetIfBuilt();
            this.mRequiredPermissions.add(new VisibilityPermissionConfig(set));
            return this;
        }

        public SchemaVisibilityConfig build() {
            this.mBuilt = true;
            return new SchemaVisibilityConfig(this.mAllowedPackages, this.mRequiredPermissions, this.mPubliclyVisibleTargetPackage);
        }

        public final void resetIfBuilt() {
            if (this.mBuilt) {
                this.mAllowedPackages = new ArrayList(this.mAllowedPackages);
                this.mRequiredPermissions = new ArrayList(this.mRequiredPermissions);
                this.mBuilt = false;
            }
        }

        public Builder setPubliclyVisibleTargetPackage(PackageIdentifier packageIdentifier) {
            resetIfBuilt();
            if (packageIdentifier == null) {
                this.mPubliclyVisibleTargetPackage = null;
                return this;
            }
            this.mPubliclyVisibleTargetPackage = packageIdentifier.getPackageIdentifierParcel();
            return this;
        }
    }

    public SchemaVisibilityConfig(List list, List list2, PackageIdentifierParcel packageIdentifierParcel) {
        Objects.requireNonNull(list);
        this.mAllowedPackages = list;
        Objects.requireNonNull(list2);
        this.mRequiredPermissions = list2;
        this.mPubliclyVisibleTargetPackage = packageIdentifierParcel;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SchemaVisibilityConfig)) {
            return false;
        }
        SchemaVisibilityConfig schemaVisibilityConfig = (SchemaVisibilityConfig) obj;
        return Objects.equals(this.mAllowedPackages, schemaVisibilityConfig.mAllowedPackages) && Objects.equals(this.mRequiredPermissions, schemaVisibilityConfig.mRequiredPermissions) && Objects.equals(this.mPubliclyVisibleTargetPackage, schemaVisibilityConfig.mPubliclyVisibleTargetPackage);
    }

    public List getAllowedPackages() {
        if (this.mAllowedPackagesCached == null) {
            this.mAllowedPackagesCached = new ArrayList(this.mAllowedPackages.size());
            for (int i = 0; i < this.mAllowedPackages.size(); i++) {
                this.mAllowedPackagesCached.add(new PackageIdentifier((PackageIdentifierParcel) this.mAllowedPackages.get(i)));
            }
        }
        return this.mAllowedPackagesCached;
    }

    public PackageIdentifier getPubliclyVisibleTargetPackage() {
        PackageIdentifierParcel packageIdentifierParcel = this.mPubliclyVisibleTargetPackage;
        if (packageIdentifierParcel == null) {
            return null;
        }
        return new PackageIdentifier(packageIdentifierParcel);
    }

    public Set getRequiredPermissions() {
        if (this.mRequiredPermissionsCached == null) {
            this.mRequiredPermissionsCached = new ArraySet(this.mRequiredPermissions.size());
            for (int i = 0; i < this.mRequiredPermissions.size(); i++) {
                Set allRequiredPermissions = ((VisibilityPermissionConfig) this.mRequiredPermissions.get(i)).getAllRequiredPermissions();
                Set set = this.mRequiredPermissionsCached;
                if (set != null && allRequiredPermissions != null) {
                    set.add(allRequiredPermissions);
                }
            }
        }
        Set set2 = this.mRequiredPermissionsCached;
        Objects.requireNonNull(set2);
        return set2;
    }

    public int hashCode() {
        if (this.mHashCode == null) {
            this.mHashCode = Integer.valueOf(Objects.hash(this.mAllowedPackages, this.mRequiredPermissions, this.mPubliclyVisibleTargetPackage));
        }
        return this.mHashCode.intValue();
    }
}
