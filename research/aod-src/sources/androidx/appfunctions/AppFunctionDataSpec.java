package androidx.appfunctions;

import android.app.PendingIntent;
import androidx.appfunctions.metadata.AppFunctionAllOfTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionArrayTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionBooleanTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionBytesTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.AppFunctionDataTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionDoubleTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionFloatTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionIntTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionLongTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionObjectTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionParameterMetadata;
import androidx.appfunctions.metadata.AppFunctionPendingIntentTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionReferenceTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionResponseMetadata;
import androidx.appfunctions.metadata.AppFunctionStringTypeMetadata;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: AppFunctionDataSpec.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public abstract class AppFunctionDataSpec {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: AppFunctionDataSpec.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final AppFunctionDataSpec create(AppFunctionObjectTypeMetadata objectType, AppFunctionComponentsMetadata componentMetadata) {
            Intrinsics.checkNotNullParameter(objectType, "objectType");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
            return new ObjectSpec(objectType, componentMetadata);
        }

        public final AppFunctionDataSpec create(AppFunctionResponseMetadata responseMetadata, AppFunctionComponentsMetadata componentMetadata) {
            Intrinsics.checkNotNullParameter(responseMetadata, "responseMetadata");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
            return new ObjectSpec(new AppFunctionObjectTypeMetadata(MapsKt.mapOf(TuplesKt.to("androidAppfunctionsReturnValue", responseMetadata.getValueType())), CollectionsKt.listOf("androidAppfunctionsReturnValue"), null, false, null, 16, null), componentMetadata);
        }

        public final AppFunctionDataSpec create(List parameterMetadataList, AppFunctionComponentsMetadata componentMetadata) {
            Intrinsics.checkNotNullParameter(parameterMetadataList, "parameterMetadataList");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
            return new ParametersSpec(parameterMetadataList, componentMetadata);
        }
    }

    /* JADX INFO: compiled from: AppFunctionDataSpec.kt */
    @Metadata
    public static final class ObjectSpec extends AppFunctionDataSpec {
        public final AppFunctionComponentsMetadata componentMetadata;
        public final AppFunctionObjectTypeMetadata objectTypeMetadata;

        public ObjectSpec(AppFunctionObjectTypeMetadata objectTypeMetadata, AppFunctionComponentsMetadata componentMetadata) {
            Intrinsics.checkNotNullParameter(objectTypeMetadata, "objectTypeMetadata");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
            this.objectTypeMetadata = objectTypeMetadata;
            this.componentMetadata = componentMetadata;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ObjectSpec) {
                return Intrinsics.areEqual(this.objectTypeMetadata, ((ObjectSpec) obj).objectTypeMetadata);
            }
            return false;
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public Set getAllPropertyKeys$appfunctions_release() {
            return this.objectTypeMetadata.getProperties().keySet();
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public AppFunctionComponentsMetadata getComponentMetadata() {
            return this.componentMetadata;
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public AppFunctionDataTypeMetadata getDataType$appfunctions_release(String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            return (AppFunctionDataTypeMetadata) this.objectTypeMetadata.getProperties().get(key);
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public String getObjectQualifiedName() {
            String qualifiedName = this.objectTypeMetadata.getQualifiedName();
            return qualifiedName == null ? "" : qualifiedName;
        }

        public int hashCode() {
            return Objects.hash(this.objectTypeMetadata);
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public boolean isRequired$appfunctions_release(String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            return this.objectTypeMetadata.getRequired().contains(key);
        }

        public String toString() {
            return "ObjectSpec(objectTypeMetadata=" + this.objectTypeMetadata + ", componentMetadata=" + this.componentMetadata + ')';
        }
    }

    /* JADX INFO: compiled from: AppFunctionDataSpec.kt */
    @Metadata
    @SourceDebugExtension
    public static final class ParametersSpec extends AppFunctionDataSpec {
        public final AppFunctionComponentsMetadata componentMetadata;
        public final List parameterMetadataList;

        public ParametersSpec(List parameterMetadataList, AppFunctionComponentsMetadata componentMetadata) {
            Intrinsics.checkNotNullParameter(parameterMetadataList, "parameterMetadataList");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
            this.parameterMetadataList = parameterMetadataList;
            this.componentMetadata = componentMetadata;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ParametersSpec) {
                return Intrinsics.areEqual(this.parameterMetadataList, ((ParametersSpec) obj).parameterMetadataList);
            }
            return false;
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public Set getAllPropertyKeys$appfunctions_release() {
            List list = this.parameterMetadataList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((AppFunctionParameterMetadata) it.next()).getName());
            }
            return CollectionsKt.toSet(arrayList);
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public AppFunctionComponentsMetadata getComponentMetadata() {
            return this.componentMetadata;
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public AppFunctionDataTypeMetadata getDataType$appfunctions_release(String key) {
            Object next;
            Intrinsics.checkNotNullParameter(key, "key");
            Iterator it = this.parameterMetadataList.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((AppFunctionParameterMetadata) next).getName(), key));
            AppFunctionParameterMetadata appFunctionParameterMetadata = (AppFunctionParameterMetadata) next;
            if (appFunctionParameterMetadata != null) {
                return appFunctionParameterMetadata.getDataType();
            }
            return null;
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public String getObjectQualifiedName() {
            return "";
        }

        public int hashCode() {
            return Objects.hash(this.parameterMetadataList);
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public boolean isRequired$appfunctions_release(String key) {
            Object next;
            Intrinsics.checkNotNullParameter(key, "key");
            Iterator it = this.parameterMetadataList.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((AppFunctionParameterMetadata) next).getName(), key));
            AppFunctionParameterMetadata appFunctionParameterMetadata = (AppFunctionParameterMetadata) next;
            if (appFunctionParameterMetadata != null) {
                return appFunctionParameterMetadata.isRequired();
            }
            return false;
        }

        public String toString() {
            return "ParametersSpec(parameterMetadataList=" + this.parameterMetadataList + ", componentMetadata=" + this.componentMetadata + ')';
        }
    }

    public final boolean conform(AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata, Class cls) {
        return conform(appFunctionArrayTypeMetadata.getItemType(), cls, false);
    }

    public final boolean conform(AppFunctionDataTypeMetadata appFunctionDataTypeMetadata, Class typeClazz, boolean z) {
        Intrinsics.checkNotNullParameter(appFunctionDataTypeMetadata, "<this>");
        Intrinsics.checkNotNullParameter(typeClazz, "typeClazz");
        if (appFunctionDataTypeMetadata instanceof AppFunctionIntTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, Integer.TYPE);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionLongTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, Long.TYPE);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionFloatTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, Float.TYPE);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionDoubleTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, Double.TYPE);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionBooleanTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, Boolean.TYPE);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionStringTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, String.class);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionBytesTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, Byte.TYPE);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionPendingIntentTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, PendingIntent.class);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionArrayTypeMetadata) {
            return z && conform((AppFunctionArrayTypeMetadata) appFunctionDataTypeMetadata, typeClazz);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionObjectTypeMetadata) {
            return !z && conform((AppFunctionObjectTypeMetadata) appFunctionDataTypeMetadata, typeClazz);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionReferenceTypeMetadata) {
            return !z && conform((AppFunctionReferenceTypeMetadata) appFunctionDataTypeMetadata, typeClazz);
        }
        throw new IllegalStateException("Unexpected data type " + appFunctionDataTypeMetadata.getClass());
    }

    public final boolean conform(AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata, Class cls) {
        return Intrinsics.areEqual(cls, AppFunctionData.class);
    }

    public final boolean conform(AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata, Class cls) {
        return Intrinsics.areEqual(cls, AppFunctionData.class);
    }

    public final boolean containsMetadata(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getDataType$appfunctions_release(key) != null;
    }

    public abstract Set getAllPropertyKeys$appfunctions_release();

    public abstract AppFunctionComponentsMetadata getComponentMetadata();

    public abstract AppFunctionDataTypeMetadata getDataType$appfunctions_release(String str);

    public abstract String getObjectQualifiedName();

    public final AppFunctionDataSpec getPropertyObjectSpec(AppFunctionDataTypeMetadata appFunctionDataTypeMetadata) {
        if (appFunctionDataTypeMetadata instanceof AppFunctionArrayTypeMetadata) {
            return getPropertyObjectSpec(((AppFunctionArrayTypeMetadata) appFunctionDataTypeMetadata).getItemType());
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionObjectTypeMetadata) {
            return new ObjectSpec((AppFunctionObjectTypeMetadata) appFunctionDataTypeMetadata, getComponentMetadata());
        }
        if (!(appFunctionDataTypeMetadata instanceof AppFunctionReferenceTypeMetadata)) {
            if (appFunctionDataTypeMetadata instanceof AppFunctionAllOfTypeMetadata) {
                return new ObjectSpec(((AppFunctionAllOfTypeMetadata) appFunctionDataTypeMetadata).getPseudoObjectTypeMetadata$appfunctions_release(getComponentMetadata()), getComponentMetadata());
            }
            throw new IllegalStateException("Unexpected data type " + appFunctionDataTypeMetadata);
        }
        AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata = (AppFunctionReferenceTypeMetadata) appFunctionDataTypeMetadata;
        AppFunctionDataTypeMetadata appFunctionDataTypeMetadata2 = (AppFunctionDataTypeMetadata) getComponentMetadata().getDataTypes().get(appFunctionReferenceTypeMetadata.getReferenceDataType());
        if (appFunctionDataTypeMetadata2 != null) {
            return getPropertyObjectSpec(appFunctionDataTypeMetadata2);
        }
        throw new IllegalStateException("Unable to resolve data type for " + appFunctionReferenceTypeMetadata.getReferenceDataType());
    }

    public final AppFunctionDataSpec getPropertyObjectSpec(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        AppFunctionDataTypeMetadata dataType$appfunctions_release = getDataType$appfunctions_release(key);
        if (dataType$appfunctions_release != null) {
            return getPropertyObjectSpec(dataType$appfunctions_release);
        }
        throw new IllegalArgumentException("Value associated with " + key + " is not an object");
    }

    public abstract boolean isRequired$appfunctions_release(String str);

    public final void requireConstraintsConformance(AppFunctionDataTypeMetadata appFunctionDataTypeMetadata, String str, Object obj) {
        if (obj == null && !appFunctionDataTypeMetadata.isNullable() && isRequired$appfunctions_release(str)) {
            throw new IllegalArgumentException(('\"' + str + "\" cannot be set to a null value.").toString());
        }
        if (obj == null) {
            return;
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionIntTypeMetadata) {
            AppFunctionIntTypeMetadata appFunctionIntTypeMetadata = (AppFunctionIntTypeMetadata) appFunctionDataTypeMetadata;
            if (appFunctionIntTypeMetadata.getEnumValues() == null || CollectionsKt.contains(appFunctionIntTypeMetadata.getEnumValues(), obj)) {
                return;
            }
            throw new IllegalArgumentException(("Invalid value for \"" + str + "\" got \"" + obj + "\", expecting one of " + appFunctionIntTypeMetadata.getEnumValues()).toString());
        }
        if (!(appFunctionDataTypeMetadata instanceof AppFunctionStringTypeMetadata)) {
            if (appFunctionDataTypeMetadata instanceof AppFunctionArrayTypeMetadata) {
                requireItemTypeConstraintsConformance((AppFunctionArrayTypeMetadata) appFunctionDataTypeMetadata, str, obj);
                return;
            }
            return;
        }
        AppFunctionStringTypeMetadata appFunctionStringTypeMetadata = (AppFunctionStringTypeMetadata) appFunctionDataTypeMetadata;
        if (appFunctionStringTypeMetadata.getEnumValues() == null || CollectionsKt.contains(appFunctionStringTypeMetadata.getEnumValues(), obj)) {
            return;
        }
        throw new IllegalArgumentException(("Invalid value for \"" + str + "\" got \"" + obj + "\", expecting one of " + appFunctionStringTypeMetadata.getEnumValues()).toString());
    }

    public final void requireItemTypeConstraintsConformance(AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata, String str, Object obj) {
        AppFunctionDataTypeMetadata itemType = appFunctionArrayTypeMetadata.getItemType();
        if (itemType instanceof AppFunctionIntTypeMetadata) {
            int[] iArr = obj instanceof int[] ? (int[]) obj : null;
            if (iArr == null) {
                iArr = new int[0];
            }
            for (int i : iArr) {
                requireConstraintsConformance(appFunctionArrayTypeMetadata.getItemType(), str, Integer.valueOf(i));
            }
            return;
        }
        if (itemType instanceof AppFunctionStringTypeMetadata) {
            List listEmptyList = obj instanceof List ? (List) obj : null;
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            Iterator it = listEmptyList.iterator();
            while (it.hasNext()) {
                requireConstraintsConformance(appFunctionArrayTypeMetadata.getItemType(), str, (String) it.next());
            }
        }
    }

    public final void validateDataSpecMatches(AppFunctionData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        AppFunctionDataSpec spec$appfunctions_release = data.getSpec$appfunctions_release();
        if (spec$appfunctions_release == null || Intrinsics.areEqual(this, spec$appfunctions_release)) {
            return;
        }
        throw new IllegalArgumentException((data + " does not match the metadata specification of " + this).toString());
    }

    public final void validateReadRequest(String targetKey, Class targetClass, boolean z, Object obj) {
        String str;
        Intrinsics.checkNotNullParameter(targetKey, "targetKey");
        Intrinsics.checkNotNullParameter(targetClass, "targetClass");
        AppFunctionDataTypeMetadata dataType$appfunctions_release = getDataType$appfunctions_release(targetKey);
        if (dataType$appfunctions_release == null) {
            throw new IllegalArgumentException("No value should be set at " + targetKey);
        }
        if (conform(dataType$appfunctions_release, targetClass, z)) {
            requireConstraintsConformance(dataType$appfunctions_release, targetKey, obj);
            return;
        }
        if (z) {
            str = "Unexpected read for " + targetKey + ": expecting collection of " + targetClass + ", the actual value should be " + dataType$appfunctions_release;
        } else {
            str = "Unexpected read for " + targetKey + ": expecting " + targetClass + ", the actual value should be " + dataType$appfunctions_release;
        }
        throw new IllegalArgumentException(str.toString());
    }

    public final void validateWriteRequest(String targetKey, Class targetClass, boolean z, Object targetValue) {
        String str;
        Intrinsics.checkNotNullParameter(targetKey, "targetKey");
        Intrinsics.checkNotNullParameter(targetClass, "targetClass");
        Intrinsics.checkNotNullParameter(targetValue, "targetValue");
        AppFunctionDataTypeMetadata dataType$appfunctions_release = getDataType$appfunctions_release(targetKey);
        if (dataType$appfunctions_release == null) {
            throw new IllegalArgumentException("No value should be set at " + targetKey);
        }
        if (conform(dataType$appfunctions_release, targetClass, z)) {
            requireConstraintsConformance(dataType$appfunctions_release, targetKey, targetValue);
            return;
        }
        if (z) {
            str = "Invalid value for " + targetKey + ": got collection of " + targetClass + ", expecting a value matching " + dataType$appfunctions_release;
        } else {
            str = "Invalid value for " + targetKey + ": got " + targetClass + ", expecting a value matching " + dataType$appfunctions_release;
        }
        throw new IllegalArgumentException(str.toString());
    }
}
