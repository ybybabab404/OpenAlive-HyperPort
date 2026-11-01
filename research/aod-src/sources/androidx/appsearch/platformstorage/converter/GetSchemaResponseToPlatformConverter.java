package androidx.appsearch.platformstorage.converter;

import android.app.appsearch.GetSchemaResponse;
import android.app.appsearch.PackageIdentifier;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appsearch.app.SchemaVisibilityConfig;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
public final class GetSchemaResponseToPlatformConverter {

    @RequiresApi
    public static class ApiHelperForT {
        @DoNotInline
        public static Map<String, Set<Set<Integer>>> getRequiredPermissionsForSchemaTypeVisibility(GetSchemaResponse getSchemaResponse) {
            return getSchemaResponse.getRequiredPermissionsForSchemaTypeVisibility();
        }

        @DoNotInline
        public static Set<String> getSchemaTypesNotDisplayedBySystem(GetSchemaResponse getSchemaResponse) {
            return getSchemaResponse.getSchemaTypesNotDisplayedBySystem();
        }

        @DoNotInline
        public static Map<String, Set<PackageIdentifier>> getSchemaTypesVisibleToPackage(GetSchemaResponse getSchemaResponse) {
            return getSchemaResponse.getSchemaTypesVisibleToPackages();
        }
    }

    @RequiresApi
    public static class ApiHelperForV {
        @DoNotInline
        public static Map<String, androidx.appsearch.app.PackageIdentifier> getPubliclyVisibleSchemas(GetSchemaResponse getSchemaResponse) {
            Map publiclyVisibleSchemas = getSchemaResponse.getPubliclyVisibleSchemas();
            if (publiclyVisibleSchemas.isEmpty()) {
                return Collections.EMPTY_MAP;
            }
            ArrayMap arrayMap = new ArrayMap(publiclyVisibleSchemas.size());
            for (Map.Entry entry : publiclyVisibleSchemas.entrySet()) {
                arrayMap.put((String) entry.getKey(), new androidx.appsearch.app.PackageIdentifier(((PackageIdentifier) entry.getValue()).getPackageName(), ((PackageIdentifier) entry.getValue()).getSha256Certificate()));
            }
            return arrayMap;
        }

        @DoNotInline
        public static Map<String, Set<SchemaVisibilityConfig>> getSchemasVisibleToConfigs(GetSchemaResponse getSchemaResponse) {
            Map schemaTypesVisibleToConfigs = getSchemaResponse.getSchemaTypesVisibleToConfigs();
            if (schemaTypesVisibleToConfigs.isEmpty()) {
                return Collections.EMPTY_MAP;
            }
            ArrayMap arrayMap = new ArrayMap(schemaTypesVisibleToConfigs.size());
            for (Map.Entry entry : schemaTypesVisibleToConfigs.entrySet()) {
                ArraySet arraySet = new ArraySet(((Set) entry.getValue()).size());
                Iterator it = ((Set) entry.getValue()).iterator();
                while (it.hasNext()) {
                    arraySet.add(toJetpackSchemaVisibilityConfig(GetSchemaResponseToPlatformConverter$ApiHelperForV$$ExternalSyntheticApiModelOutline0.m(it.next())));
                }
                arrayMap.put((String) entry.getKey(), arraySet);
            }
            return arrayMap;
        }

        public static SchemaVisibilityConfig toJetpackSchemaVisibilityConfig(android.app.appsearch.SchemaVisibilityConfig schemaVisibilityConfig) {
            Preconditions.checkNotNull(schemaVisibilityConfig);
            SchemaVisibilityConfig.Builder builder = new SchemaVisibilityConfig.Builder();
            List allowedPackages = schemaVisibilityConfig.getAllowedPackages();
            for (int i = 0; i < allowedPackages.size(); i++) {
                builder.addAllowedPackage(new androidx.appsearch.app.PackageIdentifier(((PackageIdentifier) allowedPackages.get(i)).getPackageName(), ((PackageIdentifier) allowedPackages.get(i)).getSha256Certificate()));
            }
            Iterator it = schemaVisibilityConfig.getRequiredPermissions().iterator();
            while (it.hasNext()) {
                builder.addRequiredPermissions((Set) it.next());
            }
            PackageIdentifier publiclyVisibleTargetPackage = schemaVisibilityConfig.getPubliclyVisibleTargetPackage();
            if (publiclyVisibleTargetPackage != null) {
                builder.setPubliclyVisibleTargetPackage(new androidx.appsearch.app.PackageIdentifier(publiclyVisibleTargetPackage.getPackageName(), publiclyVisibleTargetPackage.getSha256Certificate()));
            }
            return builder.build();
        }
    }
}
