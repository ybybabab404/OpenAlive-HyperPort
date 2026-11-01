package androidx.appsearch.platformstorage.converter;

import android.app.appsearch.GenericDocument;
import android.app.appsearch.Migrator;
import android.app.appsearch.PackageIdentifier;
import android.app.appsearch.SetSchemaRequest;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appsearch.app.SchemaVisibilityConfig;
import androidx.core.util.Preconditions;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
public final class SetSchemaRequestToPlatformConverter {

    /* JADX INFO: renamed from: androidx.appsearch.platformstorage.converter.SetSchemaRequestToPlatformConverter$1, reason: invalid class name */
    class AnonymousClass1 extends Migrator {
        public final /* synthetic */ androidx.appsearch.app.Migrator val$jetpackMigrator;

        @Override // android.app.appsearch.Migrator
        public GenericDocument onDowngrade(int i, int i2, GenericDocument genericDocument) {
            androidx.appsearch.app.GenericDocument jetpackGenericDocument = GenericDocumentToPlatformConverter.toJetpackGenericDocument(genericDocument);
            androidx.appsearch.app.GenericDocument genericDocumentOnDowngrade = this.val$jetpackMigrator.onDowngrade(i, i2, jetpackGenericDocument);
            return jetpackGenericDocument.equals(genericDocumentOnDowngrade) ? genericDocument : GenericDocumentToPlatformConverter.toPlatformGenericDocument(genericDocumentOnDowngrade);
        }

        @Override // android.app.appsearch.Migrator
        public GenericDocument onUpgrade(int i, int i2, GenericDocument genericDocument) {
            androidx.appsearch.app.GenericDocument jetpackGenericDocument = GenericDocumentToPlatformConverter.toJetpackGenericDocument(genericDocument);
            androidx.appsearch.app.GenericDocument genericDocumentOnUpgrade = this.val$jetpackMigrator.onUpgrade(i, i2, jetpackGenericDocument);
            return jetpackGenericDocument.equals(genericDocumentOnUpgrade) ? genericDocument : GenericDocumentToPlatformConverter.toPlatformGenericDocument(genericDocumentOnUpgrade);
        }

        @Override // android.app.appsearch.Migrator
        public boolean shouldMigrate(int i, int i2) {
            return this.val$jetpackMigrator.shouldMigrate(i, i2);
        }
    }

    @RequiresApi
    public static class ApiHelperForT {
        @DoNotInline
        public static void addRequiredPermissionsForSchemaTypeVisibility(SetSchemaRequest.Builder builder, String str, Set<Integer> set) {
            builder.addRequiredPermissionsForSchemaTypeVisibility(str, set);
        }
    }

    @RequiresApi
    public static class ApiHelperForV {
        @DoNotInline
        public static void addSchemaTypeVisibleToConfig(SetSchemaRequest.Builder builder, String str, Set<SchemaVisibilityConfig> set) {
            Iterator<SchemaVisibilityConfig> it = set.iterator();
            while (it.hasNext()) {
                builder.addSchemaTypeVisibleToConfig(str, toPlatformSchemaVisibilityConfig(it.next()));
            }
        }

        @DoNotInline
        public static void setPubliclyVisibleSchema(SetSchemaRequest.Builder builder, String str, PackageIdentifier packageIdentifier) {
            builder.setPubliclyVisibleSchema(str, packageIdentifier);
        }

        public static android.app.appsearch.SchemaVisibilityConfig toPlatformSchemaVisibilityConfig(SchemaVisibilityConfig schemaVisibilityConfig) {
            Preconditions.checkNotNull(schemaVisibilityConfig);
            android.app.appsearch.SchemaVisibilityConfig.Builder builderM = SetSchemaRequestToPlatformConverter$ApiHelperForV$$ExternalSyntheticApiModelOutline4.m();
            List allowedPackages = schemaVisibilityConfig.getAllowedPackages();
            for (int i = 0; i < allowedPackages.size(); i++) {
                builderM.addAllowedPackage(new PackageIdentifier(((androidx.appsearch.app.PackageIdentifier) allowedPackages.get(i)).getPackageName(), ((androidx.appsearch.app.PackageIdentifier) allowedPackages.get(i)).getSha256Certificate()));
            }
            Iterator it = schemaVisibilityConfig.getRequiredPermissions().iterator();
            while (it.hasNext()) {
                builderM.addRequiredPermissions((Set) it.next());
            }
            androidx.appsearch.app.PackageIdentifier publiclyVisibleTargetPackage = schemaVisibilityConfig.getPubliclyVisibleTargetPackage();
            if (publiclyVisibleTargetPackage != null) {
                builderM.setPubliclyVisibleTargetPackage(new PackageIdentifier(publiclyVisibleTargetPackage.getPackageName(), publiclyVisibleTargetPackage.getSha256Certificate()));
            }
            return builderM.build();
        }
    }
}
