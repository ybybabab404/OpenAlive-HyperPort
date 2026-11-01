package androidx.appsearch.app;

import androidx.annotation.RestrictTo;
import androidx.core.util.ObjectsCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class SetSchemaRequest {
    public final boolean mForceOverride;
    public final Map mMigrators;
    public final Map mPubliclyVisibleSchemas;
    public final Set mSchemas;
    public final Set mSchemasNotDisplayedBySystem;
    public final Map mSchemasVisibleToConfigs;
    public final Map mSchemasVisibleToPackages;
    public final Map mSchemasVisibleToPermissions;
    public final int mVersion;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface AppSearchSupportedPermission {
    }

    public static final class Builder {
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SetSchemaRequest)) {
            return false;
        }
        SetSchemaRequest setSchemaRequest = (SetSchemaRequest) obj;
        return this.mSchemas.equals(setSchemaRequest.mSchemas) && this.mSchemasNotDisplayedBySystem.equals(setSchemaRequest.mSchemasNotDisplayedBySystem) && this.mSchemasVisibleToPackages.equals(setSchemaRequest.mSchemasVisibleToPackages) && this.mSchemasVisibleToPermissions.equals(setSchemaRequest.mSchemasVisibleToPermissions) && this.mPubliclyVisibleSchemas.equals(setSchemaRequest.mPubliclyVisibleSchemas) && this.mSchemasVisibleToConfigs.equals(setSchemaRequest.mSchemasVisibleToConfigs) && this.mMigrators.equals(setSchemaRequest.mMigrators) && this.mForceOverride == setSchemaRequest.mForceOverride && this.mVersion == setSchemaRequest.mVersion;
    }

    public int hashCode() {
        return ObjectsCompat.hash(this.mSchemas, this.mSchemasNotDisplayedBySystem, this.mSchemasVisibleToPackages, this.mSchemasVisibleToPermissions, this.mPubliclyVisibleSchemas, this.mSchemasVisibleToConfigs, this.mMigrators, Boolean.valueOf(this.mForceOverride), Integer.valueOf(this.mVersion));
    }
}
