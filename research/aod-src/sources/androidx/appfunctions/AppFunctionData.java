package androidx.appfunctions;

import android.app.PendingIntent;
import android.app.appsearch.GenericDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.ext.SdkExtensions;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.AppFunctionObjectTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionResponseMetadata;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;

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
/* JADX INFO: compiled from: AppFunctionData.kt */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@Metadata
@SourceDebugExtension
public final class AppFunctionData {
    public static final Companion Companion = new Companion(null);
    public static final AppFunctionData EMPTY;
    public final Bundle extras;
    public final GenericDocument genericDocument;
    public final String id;
    public final AppFunctionDataSpec spec;

    /* JADX INFO: compiled from: AppFunctionData.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Builder {
        public final Bundle extrasBuilder;
        public GenericDocument.Builder genericDocumentBuilder;
        public final String qualifiedName;
        public final AppFunctionDataSpec spec;

        public Builder(AppFunctionDataSpec appFunctionDataSpec) {
            this.extrasBuilder = new Bundle();
            this.spec = appFunctionDataSpec;
            this.qualifiedName = appFunctionDataSpec.getObjectQualifiedName();
            this.genericDocumentBuilder = new GenericDocument.Builder("", "", appFunctionDataSpec.getObjectQualifiedName());
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Builder(AppFunctionObjectTypeMetadata objectTypeMetadata, AppFunctionComponentsMetadata componentMetadata) {
            this(AppFunctionDataSpec.Companion.create(objectTypeMetadata, componentMetadata));
            Intrinsics.checkNotNullParameter(objectTypeMetadata, "objectTypeMetadata");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Builder(AppFunctionResponseMetadata responseMetadata, AppFunctionComponentsMetadata componentMetadata) {
            this(AppFunctionDataSpec.Companion.create(responseMetadata, componentMetadata));
            Intrinsics.checkNotNullParameter(responseMetadata, "responseMetadata");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
        }

        public Builder(String qualifiedName, String id) {
            Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
            Intrinsics.checkNotNullParameter(id, "id");
            this.extrasBuilder = new Bundle();
            this.qualifiedName = qualifiedName;
            this.spec = null;
            this.genericDocumentBuilder = new GenericDocument.Builder("", id, qualifiedName);
        }

        public /* synthetic */ Builder(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? "" : str2);
        }

        public final AppFunctionData build() {
            Set<String> allPropertyKeys$appfunctions_release;
            GenericDocument genericDocumentBuild = this.genericDocumentBuilder.build();
            Intrinsics.checkNotNullExpressionValue(genericDocumentBuild, "build(...)");
            AppFunctionData appFunctionData = new AppFunctionData(this.spec, genericDocumentBuild, this.extrasBuilder);
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null && (allPropertyKeys$appfunctions_release = appFunctionDataSpec.getAllPropertyKeys$appfunctions_release()) != null) {
                for (String str : allPropertyKeys$appfunctions_release) {
                    if (this.spec.isRequired$appfunctions_release(str) && !appFunctionData.containsKey(str)) {
                        throw new IllegalArgumentException("Missing required property: '" + str + "' for object '" + this.qualifiedName + '\'');
                    }
                }
            }
            return appFunctionData;
        }

        public final Builder setAppFunctionData(String key, AppFunctionData value) {
            AppFunctionDataSpec propertyObjectSpec;
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, AppFunctionData.class, false, value);
            }
            AppFunctionDataSpec appFunctionDataSpec2 = this.spec;
            if (appFunctionDataSpec2 != null && (propertyObjectSpec = appFunctionDataSpec2.getPropertyObjectSpec(key)) != null) {
                propertyObjectSpec.validateDataSpecMatches(value);
            }
            this.genericDocumentBuilder.setPropertyDocument(key, value.getGenericDocument$appfunctions_release());
            if (!value.getExtras$appfunctions_release().isEmpty()) {
                this.extrasBuilder.putBundle(AppFunctionData.Companion.extrasKey(key), value.getExtras$appfunctions_release());
            }
            return this;
        }

        public final Builder setAppFunctionDataList(String key, List value) {
            AppFunctionDataSpec propertyObjectSpec;
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, AppFunctionData.class, true, value);
            }
            GenericDocument.Builder builder = this.genericDocumentBuilder;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(value, 10));
            Iterator it = value.iterator();
            while (it.hasNext()) {
                arrayList.add(((AppFunctionData) it.next()).getGenericDocument$appfunctions_release());
            }
            int i = 0;
            GenericDocument[] genericDocumentArr = (GenericDocument[]) arrayList.toArray(new GenericDocument[0]);
            builder.setPropertyDocument(key, (GenericDocument[]) Arrays.copyOf(genericDocumentArr, genericDocumentArr.length));
            for (Object obj : value) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                AppFunctionData appFunctionData = (AppFunctionData) obj;
                AppFunctionDataSpec appFunctionDataSpec2 = this.spec;
                if (appFunctionDataSpec2 != null && (propertyObjectSpec = appFunctionDataSpec2.getPropertyObjectSpec(key)) != null) {
                    propertyObjectSpec.validateDataSpecMatches(appFunctionData);
                }
                if (!appFunctionData.getExtras$appfunctions_release().isEmpty()) {
                    this.extrasBuilder.putBundle(AppFunctionData.Companion.extrasKey(key, i), appFunctionData.getExtras$appfunctions_release());
                }
                i = i2;
            }
            return this;
        }

        public final Builder setBoolean(String key, boolean z) {
            Intrinsics.checkNotNullParameter(key, "key");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Boolean.TYPE, false, Boolean.valueOf(z));
            }
            this.genericDocumentBuilder.setPropertyBoolean(key, z);
            return this;
        }

        public final Builder setBooleanArray(String key, boolean[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Boolean.TYPE, true, value);
            }
            this.genericDocumentBuilder.setPropertyBoolean(key, Arrays.copyOf(value, value.length));
            return this;
        }

        public final Builder setByteArray(String key, byte[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Byte.TYPE, true, value);
            }
            this.genericDocumentBuilder.setPropertyBytes(key, value);
            return this;
        }

        public final Builder setDouble(String key, double d) {
            Intrinsics.checkNotNullParameter(key, "key");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Double.TYPE, false, Double.valueOf(d));
            }
            this.genericDocumentBuilder.setPropertyDouble(key, d);
            return this;
        }

        public final Builder setDoubleArray(String key, double[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Double.TYPE, true, value);
            }
            this.genericDocumentBuilder.setPropertyDouble(key, Arrays.copyOf(value, value.length));
            return this;
        }

        public final Builder setFloat(String key, float f) {
            Intrinsics.checkNotNullParameter(key, "key");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Float.TYPE, false, Float.valueOf(f));
            }
            this.genericDocumentBuilder.setPropertyDouble(key, f);
            return this;
        }

        public final Builder setFloatArray(String key, float[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Float.TYPE, true, value);
            }
            GenericDocument.Builder builder = this.genericDocumentBuilder;
            List listAsList = ArraysKt.asList(value);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listAsList, 10));
            Iterator it = listAsList.iterator();
            while (it.hasNext()) {
                arrayList.add(Double.valueOf(((Number) it.next()).floatValue()));
            }
            double[] doubleArray = CollectionsKt.toDoubleArray(arrayList);
            builder.setPropertyDouble(key, Arrays.copyOf(doubleArray, doubleArray.length));
            return this;
        }

        public final Builder setInt(String key, int i) {
            Intrinsics.checkNotNullParameter(key, "key");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Integer.TYPE, false, Integer.valueOf(i));
            }
            this.genericDocumentBuilder.setPropertyLong(key, i);
            return this;
        }

        public final Builder setIntArray(String key, int[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Integer.TYPE, true, value);
            }
            GenericDocument.Builder builder = this.genericDocumentBuilder;
            List listAsList = ArraysKt.asList(value);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listAsList, 10));
            Iterator it = listAsList.iterator();
            while (it.hasNext()) {
                arrayList.add(Long.valueOf(((Number) it.next()).intValue()));
            }
            long[] longArray = CollectionsKt.toLongArray(arrayList);
            builder.setPropertyLong(key, Arrays.copyOf(longArray, longArray.length));
            return this;
        }

        public final void setLegacyId(String str) {
            if (SdkExtensions.getExtensionVersion(33) >= 13) {
                this.genericDocumentBuilder.setId(str);
            } else {
                Log.wtf("AppFunctions", "setId method in GenericDocument isn't supported on the current device.");
            }
        }

        public final Builder setLong(String key, long j) {
            Intrinsics.checkNotNullParameter(key, "key");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Long.TYPE, false, Long.valueOf(j));
            }
            this.genericDocumentBuilder.setPropertyLong(key, j);
            return this;
        }

        public final Builder setLongArray(String key, long[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Long.TYPE, true, value);
            }
            this.genericDocumentBuilder.setPropertyLong(key, Arrays.copyOf(value, value.length));
            return this;
        }

        public final Builder setPendingIntent(String key, PendingIntent value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, PendingIntent.class, false, value);
            }
            this.extrasBuilder.putParcelable(AppFunctionData.Companion.extrasKey(key), value);
            return this;
        }

        public final Builder setPendingIntentList(String key, List value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, PendingIntent.class, true, value);
            }
            this.extrasBuilder.putParcelableArrayList(AppFunctionData.Companion.extrasKey(key), new ArrayList<>(value));
            return this;
        }

        public final Builder setString(String key, String value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, String.class, false, value);
            }
            this.genericDocumentBuilder.setPropertyString(key, value);
            if (Intrinsics.areEqual(key, "id")) {
                setLegacyId(value);
            }
            return this;
        }

        public final Builder setStringList(String key, List value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, new String().getClass(), true, value);
            }
            GenericDocument.Builder builder = this.genericDocumentBuilder;
            String[] strArr = (String[]) value.toArray(new String[0]);
            builder.setPropertyString(key, (String[]) Arrays.copyOf(strArr, strArr.length));
            return this;
        }
    }

    /* JADX INFO: compiled from: AppFunctionData.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String extrasKey(String str) {
            return "property/" + str;
        }

        public final String extrasKey(String str, int i) {
            return "property/" + str + '[' + i + ']';
        }

        public final String getPackageName(Class cls) {
            if (SetsKt.setOf((Object[]) new String[]{Reflection.getOrCreateKotlinClass(LocalDateTime.class).getSimpleName(), Reflection.getOrCreateKotlinClass(Uri.class).getSimpleName()}).contains(cls.getSimpleName())) {
                return "androidx.appfunctions.internal.serializableproxies";
            }
            String packageName = cls.getPackageName();
            Intrinsics.checkNotNullExpressionValue(packageName, "getPackageName(...)");
            return packageName;
        }

        public final Class getSerializableClass(String str) {
            try {
                Class<?> cls = Class.forName(str);
                Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<T of androidx.appfunctions.AppFunctionData.Companion.getSerializableClass>");
                return cls;
            } catch (Exception e) {
                Log.d("AppFunctions", "Unable to find serializable class " + str, e);
                throw new IllegalArgumentException("Unable to find serializable class " + str);
            }
        }

        public final AppFunctionSerializableFactory getSerializableFactory(Class cls) {
            String packageName = getPackageName(cls);
            String name = cls.getName();
            Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
            try {
                Object objNewInstance = Class.forName(packageName + '.' + ('$' + StringsKt.substringAfterLast$default(name, '.', null, 2, null) + "Factory")).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type androidx.appfunctions.internal.AppFunctionSerializableFactory<T of androidx.appfunctions.AppFunctionData.Companion.getSerializableFactory>");
                return (AppFunctionSerializableFactory) objNewInstance;
            } catch (Exception e) {
                Log.d("AppFunctions", "Unable to create AppFunctionSerializableFactory for " + cls, e);
                throw new IllegalArgumentException("Unable to create AppFunctionSerializableFactory for " + cls);
            }
        }

        public final AppFunctionData serialize(Object serializable, Class serializableClass) {
            Intrinsics.checkNotNullParameter(serializable, "serializable");
            Intrinsics.checkNotNullParameter(serializableClass, "serializableClass");
            try {
                return getSerializableFactory(serializableClass).toAppFunctionData(serializable);
            } catch (Exception e) {
                Log.d("AppFunctions", "Something went wrong while serialize " + serializable + " of class " + serializableClass, e);
                throw new IllegalArgumentException("Unable to serialize " + serializableClass + ". Is the class annotated with @AppFunctionSerializable?");
            }
        }

        public final AppFunctionData serialize(Object serializable, String qualifiedName) {
            Intrinsics.checkNotNullParameter(serializable, "serializable");
            Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
            return serialize(serializable, getSerializableClass(qualifiedName));
        }
    }

    static {
        GenericDocument genericDocumentBuild = new GenericDocument.Builder("", "", "").build();
        Intrinsics.checkNotNullExpressionValue(genericDocumentBuild, "build(...)");
        Bundle EMPTY2 = Bundle.EMPTY;
        Intrinsics.checkNotNullExpressionValue(EMPTY2, "EMPTY");
        EMPTY = new AppFunctionData(genericDocumentBuild, EMPTY2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AppFunctionData(GenericDocument genericDocument, Bundle extras) {
        this(null, genericDocument, extras);
        Intrinsics.checkNotNullParameter(genericDocument, "genericDocument");
        Intrinsics.checkNotNullParameter(extras, "extras");
    }

    public AppFunctionData(AppFunctionDataSpec appFunctionDataSpec, GenericDocument genericDocument, Bundle extras) {
        Intrinsics.checkNotNullParameter(genericDocument, "genericDocument");
        Intrinsics.checkNotNullParameter(extras, "extras");
        this.spec = appFunctionDataSpec;
        this.genericDocument = genericDocument;
        this.extras = extras;
        String id = genericDocument.getId();
        Intrinsics.checkNotNullExpressionValue(id, "getId(...)");
        this.id = id;
    }

    public final boolean containsKey(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec == null || appFunctionDataSpec.containsMetadata(key)) {
            return Intrinsics.areEqual(key, "id") || this.genericDocument.getProperty(key) != null || this.extras.containsKey(key) || this.extras.containsKey(Companion.extrasKey(key));
        }
        throw new IllegalArgumentException("There is no metadata associated with " + key);
    }

    public final Object deserialize(Class serializableClass) {
        Intrinsics.checkNotNullParameter(serializableClass, "serializableClass");
        try {
            return Companion.getSerializableFactory(serializableClass).fromAppFunctionData(this);
        } catch (Exception e) {
            Log.d("AppFunctions", "Something went wrong while deserialize " + this + " to " + serializableClass, e);
            throw new IllegalArgumentException("Unable to deserialize " + serializableClass + ". Is the class annotated with @AppFunctionSerializable?");
        }
    }

    public final Object deserialize(String qualifiedName) {
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        return deserialize(Companion.getSerializableClass(qualifiedName));
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final AppFunctionData getAppFunctionData(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        GenericDocument[] genericDocumentArr = (GenericDocument[]) unsafeGetProperty(key, GenericDocument[].class);
        AppFunctionData appFunctionData = null;
        appFunctionData = null;
        if (genericDocumentArr != null && genericDocumentArr.length != 0) {
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            AppFunctionDataSpec propertyObjectSpec = appFunctionDataSpec != null ? appFunctionDataSpec.getPropertyObjectSpec(key) : null;
            GenericDocument genericDocument = genericDocumentArr[0];
            Bundle bundle = this.extras.getBundle(Companion.extrasKey(key));
            if (bundle == null) {
                bundle = Bundle.EMPTY;
            }
            Intrinsics.checkNotNull(bundle);
            appFunctionData = new AppFunctionData(propertyObjectSpec, genericDocument, bundle);
        }
        AppFunctionDataSpec appFunctionDataSpec2 = this.spec;
        if (appFunctionDataSpec2 != null) {
            appFunctionDataSpec2.validateReadRequest(key, AppFunctionData.class, false, appFunctionData);
        }
        return appFunctionData;
    }

    public final List getAppFunctionDataList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        ArrayList arrayList = null;
        AppFunctionDataSpec propertyObjectSpec = appFunctionDataSpec != null ? appFunctionDataSpec.getPropertyObjectSpec(key) : null;
        GenericDocument[] genericDocumentArr = (GenericDocument[]) unsafeGetProperty(key, GenericDocument[].class);
        if (genericDocumentArr != null) {
            arrayList = new ArrayList(genericDocumentArr.length);
            int length = genericDocumentArr.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                GenericDocument genericDocument = genericDocumentArr[i];
                int i3 = i2 + 1;
                Bundle bundle = this.extras.getBundle(Companion.extrasKey(key, i2));
                if (bundle == null) {
                    bundle = Bundle.EMPTY;
                }
                Intrinsics.checkNotNull(bundle);
                arrayList.add(new AppFunctionData(propertyObjectSpec, genericDocument, bundle));
                i++;
                i2 = i3;
            }
        }
        AppFunctionDataSpec appFunctionDataSpec2 = this.spec;
        if (appFunctionDataSpec2 != null) {
            appFunctionDataSpec2.validateReadRequest(key, AppFunctionData.class, true, arrayList);
        }
        return arrayList;
    }

    public final boolean getBoolean(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getBoolean(key, false);
    }

    public final boolean getBoolean(String key, boolean z) {
        Intrinsics.checkNotNullParameter(key, "key");
        Boolean booleanOrNull = getBooleanOrNull(key);
        return booleanOrNull != null ? booleanOrNull.booleanValue() : z;
    }

    public final boolean[] getBooleanArray(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        boolean[] zArr = (boolean[]) unsafeGetProperty(key, boolean[].class);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Boolean.TYPE, true, zArr);
        }
        return zArr;
    }

    public final Boolean getBooleanOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        boolean[] zArr = (boolean[]) unsafeGetProperty(key, boolean[].class);
        Boolean boolValueOf = (zArr == null || zArr.length == 0) ? null : Boolean.valueOf(zArr[0]);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Boolean.TYPE, false, boolValueOf);
        }
        return boolValueOf;
    }

    public final byte[] getByteArray(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        byte[][] bArr = (byte[][]) unsafeGetProperty(key, byte[][].class);
        byte[] bArr2 = (bArr == null || bArr.length == 0) ? null : bArr[0];
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Byte.TYPE, true, bArr2);
        }
        return bArr2;
    }

    public final double getDouble(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getDouble(key, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
    }

    public final double getDouble(String key, double d) {
        Intrinsics.checkNotNullParameter(key, "key");
        Double doubleOrNull = getDoubleOrNull(key);
        return doubleOrNull != null ? doubleOrNull.doubleValue() : d;
    }

    public final double[] getDoubleArray(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        double[] dArr = (double[]) unsafeGetProperty(key, double[].class);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Double.TYPE, true, dArr);
        }
        return dArr;
    }

    public final Double getDoubleOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        double[] dArr = (double[]) unsafeGetProperty(key, double[].class);
        Double dValueOf = (dArr == null || dArr.length == 0) ? null : Double.valueOf(dArr[0]);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Double.TYPE, false, dValueOf);
        }
        return dValueOf;
    }

    public final Bundle getExtras$appfunctions_release() {
        return this.extras;
    }

    public final float getFloat(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getFloat(key, 0.0f);
    }

    public final float getFloat(String key, float f) {
        Intrinsics.checkNotNullParameter(key, "key");
        Float floatOrNull = getFloatOrNull(key);
        return floatOrNull != null ? floatOrNull.floatValue() : f;
    }

    public final float[] getFloatArray(String key) {
        float[] floatArray;
        Intrinsics.checkNotNullParameter(key, "key");
        double[] dArr = (double[]) unsafeGetProperty(key, double[].class);
        if (dArr != null) {
            ArrayList arrayList = new ArrayList(dArr.length);
            for (double d : dArr) {
                if (!isDoubleWithinFloatRange(d)) {
                    throw new IllegalStateException("One of the value associated with " + key + " is not within the range of Float");
                }
                arrayList.add(Float.valueOf((float) d));
            }
            floatArray = CollectionsKt.toFloatArray(arrayList);
        } else {
            floatArray = null;
        }
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Float.TYPE, true, floatArray);
        }
        return floatArray;
    }

    public final Float getFloatOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        double[] dArr = (double[]) unsafeGetProperty(key, double[].class);
        Double dValueOf = (dArr == null || dArr.length == 0) ? null : Double.valueOf(dArr[0]);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Float.TYPE, false, dValueOf);
        }
        if (dValueOf == null || isDoubleWithinFloatRange(dValueOf.doubleValue())) {
            if (dValueOf != null) {
                return Float.valueOf((float) dValueOf.doubleValue());
            }
            return null;
        }
        throw new IllegalStateException("The value associated with " + key + " is not within the range of Float");
    }

    public final GenericDocument getGenericDocument$appfunctions_release() {
        return this.genericDocument;
    }

    public final int getInt(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getInt(key, 0);
    }

    public final int getInt(String key, int i) {
        Intrinsics.checkNotNullParameter(key, "key");
        Integer intOrNull = getIntOrNull(key);
        return intOrNull != null ? intOrNull.intValue() : i;
    }

    public final int[] getIntArray(String key) {
        int[] intArray;
        Intrinsics.checkNotNullParameter(key, "key");
        long[] jArr = (long[]) unsafeGetProperty(key, long[].class);
        if (jArr != null) {
            ArrayList arrayList = new ArrayList(jArr.length);
            for (long j : jArr) {
                if (!isLongWithinLongRange(j)) {
                    throw new IllegalStateException("One of the value associated with " + key + " is not within the range of Int");
                }
                arrayList.add(Integer.valueOf((int) j));
            }
            intArray = CollectionsKt.toIntArray(arrayList);
        } else {
            intArray = null;
        }
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Integer.TYPE, true, intArray);
        }
        return intArray;
    }

    public final Integer getIntOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        long[] jArr = (long[]) unsafeGetProperty(key, long[].class);
        Long lValueOf = (jArr == null || jArr.length == 0) ? null : Long.valueOf(jArr[0]);
        if (lValueOf == null || isLongWithinLongRange(lValueOf.longValue())) {
            Integer numValueOf = lValueOf != null ? Integer.valueOf((int) lValueOf.longValue()) : null;
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateReadRequest(key, Integer.TYPE, false, numValueOf);
            }
            return numValueOf;
        }
        throw new IllegalStateException("The value associated with " + key + " is not within the range of Int");
    }

    public final long getLong(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getLong(key, 0L);
    }

    public final long getLong(String key, long j) {
        Intrinsics.checkNotNullParameter(key, "key");
        Long longOrNull = getLongOrNull(key);
        return longOrNull != null ? longOrNull.longValue() : j;
    }

    public final long[] getLongArray(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        long[] jArr = (long[]) unsafeGetProperty(key, long[].class);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Long.TYPE, true, jArr);
        }
        return jArr;
    }

    public final Long getLongOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        long[] jArr = (long[]) unsafeGetProperty(key, long[].class);
        Long lValueOf = (jArr == null || jArr.length == 0) ? null : Long.valueOf(jArr[0]);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Long.TYPE, false, lValueOf);
        }
        return lValueOf;
    }

    public final PendingIntent getPendingIntent(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getPendingIntentOrNull(key);
    }

    public final List getPendingIntentList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        ArrayList parcelableArrayList = this.extras.getParcelableArrayList(Companion.extrasKey(key), PendingIntent.class);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, PendingIntent.class, true, parcelableArrayList);
        }
        return parcelableArrayList;
    }

    public final PendingIntent getPendingIntentOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        PendingIntent pendingIntent = (PendingIntent) this.extras.getParcelable(Companion.extrasKey(key), PendingIntent.class);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, PendingIntent.class, false, pendingIntent);
        }
        return pendingIntent;
    }

    public final String getQualifiedName() {
        String schemaType = this.genericDocument.getSchemaType();
        Intrinsics.checkNotNullExpressionValue(schemaType, "getSchemaType(...)");
        return schemaType;
    }

    public final AppFunctionDataSpec getSpec$appfunctions_release() {
        return this.spec;
    }

    public final String getString(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getStringOrNull(key);
    }

    public final List getStringList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        String[] strArr = (String[]) unsafeGetProperty(key, String[].class);
        List listAsList = strArr != null ? ArraysKt.asList(strArr) : null;
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, String.class, true, listAsList);
        }
        return listAsList;
    }

    public final String getStringOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        String[] strArr = (String[]) unsafeGetProperty(key, String[].class);
        String str = null;
        if (Intrinsics.areEqual(key, "id")) {
            String str2 = this.id;
            if (str2.length() != 0) {
                str = str2;
            } else if (strArr != null) {
                if (strArr.length == 0) {
                    strArr = null;
                }
                if (strArr != null) {
                    str = strArr[0];
                }
            }
        } else if (strArr != null && strArr.length != 0) {
            str = strArr[0];
        }
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, String.class, false, str);
        }
        return str;
    }

    public final boolean isDoubleWithinFloatRange(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            return true;
        }
        return d <= 3.4028234663852886E38d && d >= -3.4028234663852886E38d;
    }

    public final boolean isLongWithinLongRange(long j) {
        return j >= -2147483648L && j <= 2147483647L;
    }

    public final AppFunctionData replaceSpecWith$appfunctions_release(AppFunctionObjectTypeMetadata objectTypeMetadata, AppFunctionComponentsMetadata componentMetadata) {
        Intrinsics.checkNotNullParameter(objectTypeMetadata, "objectTypeMetadata");
        Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
        return new AppFunctionData(AppFunctionDataSpec.Companion.create(objectTypeMetadata, componentMetadata), this.genericDocument, this.extras);
    }

    public final AppFunctionData replaceSpecWith$appfunctions_release(AppFunctionResponseMetadata responseMetadata, AppFunctionComponentsMetadata componentMetadata) {
        Intrinsics.checkNotNullParameter(responseMetadata, "responseMetadata");
        Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
        return new AppFunctionData(AppFunctionDataSpec.Companion.create(responseMetadata, componentMetadata), this.genericDocument, this.extras);
    }

    public final AppFunctionData replaceSpecWith$appfunctions_release(List parameterMetadata, AppFunctionComponentsMetadata componentMetadata) {
        Intrinsics.checkNotNullParameter(parameterMetadata, "parameterMetadata");
        Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
        return new AppFunctionData(AppFunctionDataSpec.Companion.create(parameterMetadata, componentMetadata), this.genericDocument, this.extras);
    }

    public String toString() {
        return "AppFunctionData(genericDocument=" + this.genericDocument + ", extras=" + this.extras + ')';
    }

    public final Object unsafeGetProperty(String str, Class cls) {
        try {
            Object property = this.genericDocument.getProperty(str);
            if (property != null) {
                return cls.cast(property);
            }
            return null;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Found the property under [" + str + "] but data type does not match with the request.", e);
        }
    }

    public final void visitAppFunctionUriGrants(Function1 visitor) {
        AppFunctionUriGrant appFunctionUriGrant;
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        if (Intrinsics.areEqual(getQualifiedName(), AppFunctionUriGrant.class.getCanonicalName())) {
            try {
                appFunctionUriGrant = (AppFunctionUriGrant) deserialize(AppFunctionUriGrant.class);
            } catch (Exception e) {
                Log.d("AppFunctions", "Unexpected error while visiting AppFunctionUriGrant", e);
                appFunctionUriGrant = null;
            }
            if (appFunctionUriGrant != null) {
                visitor.invoke(appFunctionUriGrant);
            }
        }
        for (String str : this.genericDocument.getPropertyNames()) {
            try {
                Intrinsics.checkNotNull(str);
                AppFunctionData appFunctionData = getAppFunctionData(str);
                if (appFunctionData != null) {
                    appFunctionData.visitAppFunctionUriGrants(visitor);
                }
            } catch (Exception unused) {
            }
            try {
                Intrinsics.checkNotNull(str);
                List appFunctionDataList = getAppFunctionDataList(str);
                if (appFunctionDataList == null) {
                    appFunctionDataList = CollectionsKt.emptyList();
                }
                Iterator it = appFunctionDataList.iterator();
                while (it.hasNext()) {
                    ((AppFunctionData) it.next()).visitAppFunctionUriGrants(visitor);
                }
            } catch (Exception unused2) {
            }
        }
    }
}
