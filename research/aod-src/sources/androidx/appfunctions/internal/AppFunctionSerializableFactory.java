package androidx.appfunctions.internal;

import android.app.PendingIntent;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.AppFunctionObjectTypeMetadata;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
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
/* JADX INFO: compiled from: AppFunctionSerializableFactory.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@RequiresApi
@RestrictTo
public interface AppFunctionSerializableFactory<T> {

    /* JADX INFO: compiled from: AppFunctionSerializableFactory.kt */
    @Metadata
    public interface TypeParameter<T> {

        /* JADX INFO: compiled from: AppFunctionSerializableFactory.kt */
        @Metadata
        public static final class PrimitiveListTypeParameter<I, T extends List<?>> implements TypeParameter<T> {
            public final Class itemClazz;

            public PrimitiveListTypeParameter(Class itemClazz) {
                Intrinsics.checkNotNullParameter(itemClazz, "itemClazz");
                this.itemClazz = itemClazz;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof PrimitiveListTypeParameter) && Intrinsics.areEqual(this.itemClazz, ((PrimitiveListTypeParameter) obj).itemClazz);
            }

            @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory.TypeParameter
            public List getFromAppFunctionData(AppFunctionData appFunctionData, String key) {
                Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
                Intrinsics.checkNotNullParameter(key, "key");
                Class cls = this.itemClazz;
                if (Intrinsics.areEqual(cls, String.class)) {
                    return appFunctionData.getStringList(key);
                }
                if (Intrinsics.areEqual(cls, PendingIntent.class)) {
                    return appFunctionData.getPendingIntentList(key);
                }
                throw new IllegalStateException("Unsupported item type for primitive list: " + this.itemClazz + '.');
            }

            public int hashCode() {
                return this.itemClazz.hashCode();
            }

            @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory.TypeParameter
            public void setValueInAppFunctionData(AppFunctionData.Builder appFunctionDataBuilder, String key, List list) {
                Intrinsics.checkNotNullParameter(appFunctionDataBuilder, "appFunctionDataBuilder");
                Intrinsics.checkNotNullParameter(key, "key");
                if (list == null) {
                    return;
                }
                Class cls = this.itemClazz;
                if (Intrinsics.areEqual(cls, String.class)) {
                    appFunctionDataBuilder.setStringList(key, list);
                    return;
                }
                if (Intrinsics.areEqual(cls, PendingIntent.class)) {
                    appFunctionDataBuilder.setPendingIntentList(key, list);
                    return;
                }
                throw new IllegalStateException("Unsupported item type for primitive list: " + this.itemClazz + '.');
            }

            public String toString() {
                return "PrimitiveListTypeParameter(itemClazz=" + this.itemClazz + ')';
            }
        }

        /* JADX INFO: compiled from: AppFunctionSerializableFactory.kt */
        @Metadata
        public static final class PrimitiveTypeParameter<T> implements TypeParameter<T> {
            public final Class clazz;

            public PrimitiveTypeParameter(Class clazz) {
                Intrinsics.checkNotNullParameter(clazz, "clazz");
                this.clazz = clazz;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof PrimitiveTypeParameter) && Intrinsics.areEqual(this.clazz, ((PrimitiveTypeParameter) obj).clazz);
            }

            @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory.TypeParameter
            public Object getFromAppFunctionData(AppFunctionData appFunctionData, String key) {
                Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
                Intrinsics.checkNotNullParameter(key, "key");
                Class cls = this.clazz;
                if (Intrinsics.areEqual(cls, Integer.TYPE)) {
                    return Integer.valueOf(appFunctionData.getInt(key));
                }
                if (Intrinsics.areEqual(cls, Long.TYPE)) {
                    return Long.valueOf(appFunctionData.getLong(key));
                }
                if (Intrinsics.areEqual(cls, Float.TYPE)) {
                    return Float.valueOf(appFunctionData.getFloat(key));
                }
                if (Intrinsics.areEqual(cls, Double.TYPE)) {
                    return Double.valueOf(appFunctionData.getDouble(key));
                }
                if (Intrinsics.areEqual(cls, Boolean.TYPE)) {
                    return Boolean.valueOf(appFunctionData.getBoolean(key));
                }
                if (Intrinsics.areEqual(cls, String.class)) {
                    return appFunctionData.getString(key);
                }
                if (Intrinsics.areEqual(cls, PendingIntent.class)) {
                    return appFunctionData.getPendingIntent(key);
                }
                if (Intrinsics.areEqual(cls, int[].class)) {
                    return appFunctionData.getIntArray(key);
                }
                if (Intrinsics.areEqual(cls, long[].class)) {
                    return appFunctionData.getLongArray(key);
                }
                if (Intrinsics.areEqual(cls, float[].class)) {
                    return appFunctionData.getFloatArray(key);
                }
                if (Intrinsics.areEqual(cls, double[].class)) {
                    return appFunctionData.getDoubleArray(key);
                }
                if (Intrinsics.areEqual(cls, boolean[].class)) {
                    return appFunctionData.getBooleanArray(key);
                }
                if (Intrinsics.areEqual(cls, byte[].class)) {
                    return appFunctionData.getByteArray(key);
                }
                throw new IllegalStateException("Unsupported primitive type: " + this.clazz + '.');
            }

            public int hashCode() {
                return this.clazz.hashCode();
            }

            @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory.TypeParameter
            public void setValueInAppFunctionData(AppFunctionData.Builder appFunctionDataBuilder, String key, Object obj) {
                Intrinsics.checkNotNullParameter(appFunctionDataBuilder, "appFunctionDataBuilder");
                Intrinsics.checkNotNullParameter(key, "key");
                if (obj == null) {
                    return;
                }
                Class cls = this.clazz;
                if (Intrinsics.areEqual(cls, Integer.TYPE)) {
                    appFunctionDataBuilder.setInt(key, ((Integer) obj).intValue());
                    return;
                }
                if (Intrinsics.areEqual(cls, Long.TYPE)) {
                    appFunctionDataBuilder.setLong(key, ((Long) obj).longValue());
                    return;
                }
                if (Intrinsics.areEqual(cls, Float.TYPE)) {
                    appFunctionDataBuilder.setFloat(key, ((Float) obj).floatValue());
                    return;
                }
                if (Intrinsics.areEqual(cls, Double.TYPE)) {
                    appFunctionDataBuilder.setDouble(key, ((Double) obj).doubleValue());
                    return;
                }
                if (Intrinsics.areEqual(cls, Boolean.TYPE)) {
                    appFunctionDataBuilder.setBoolean(key, ((Boolean) obj).booleanValue());
                    return;
                }
                if (Intrinsics.areEqual(cls, String.class)) {
                    appFunctionDataBuilder.setString(key, (String) obj);
                    return;
                }
                if (Intrinsics.areEqual(cls, PendingIntent.class)) {
                    appFunctionDataBuilder.setPendingIntent(key, (PendingIntent) obj);
                    return;
                }
                if (Intrinsics.areEqual(cls, int[].class)) {
                    appFunctionDataBuilder.setIntArray(key, (int[]) obj);
                    return;
                }
                if (Intrinsics.areEqual(cls, long[].class)) {
                    appFunctionDataBuilder.setLongArray(key, (long[]) obj);
                    return;
                }
                if (Intrinsics.areEqual(cls, float[].class)) {
                    appFunctionDataBuilder.setFloatArray(key, (float[]) obj);
                    return;
                }
                if (Intrinsics.areEqual(cls, double[].class)) {
                    appFunctionDataBuilder.setDoubleArray(key, (double[]) obj);
                    return;
                }
                if (Intrinsics.areEqual(cls, boolean[].class)) {
                    appFunctionDataBuilder.setBooleanArray(key, (boolean[]) obj);
                    return;
                }
                if (Intrinsics.areEqual(cls, byte[].class)) {
                    appFunctionDataBuilder.setByteArray(key, (byte[]) obj);
                    return;
                }
                throw new IllegalStateException("Unsupported primitive type: " + this.clazz + '.');
            }

            public String toString() {
                return "PrimitiveTypeParameter(clazz=" + this.clazz + ')';
            }
        }

        /* JADX INFO: compiled from: AppFunctionSerializableFactory.kt */
        @Metadata
        @SourceDebugExtension
        public static final class SerializableListTypeParameter<I, T extends List<?>> implements TypeParameter<T> {
            public final Class itemClazz;
            public final AppFunctionSerializableFactory serializableFactory;

            public SerializableListTypeParameter(Class itemClazz, AppFunctionSerializableFactory serializableFactory) {
                Intrinsics.checkNotNullParameter(itemClazz, "itemClazz");
                Intrinsics.checkNotNullParameter(serializableFactory, "serializableFactory");
                this.itemClazz = itemClazz;
                this.serializableFactory = serializableFactory;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof SerializableListTypeParameter)) {
                    return false;
                }
                SerializableListTypeParameter serializableListTypeParameter = (SerializableListTypeParameter) obj;
                return Intrinsics.areEqual(this.itemClazz, serializableListTypeParameter.itemClazz) && Intrinsics.areEqual(this.serializableFactory, serializableListTypeParameter.serializableFactory);
            }

            @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory.TypeParameter
            public List getFromAppFunctionData(AppFunctionData appFunctionData, String key) {
                Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
                Intrinsics.checkNotNullParameter(key, "key");
                List appFunctionDataList = appFunctionData.getAppFunctionDataList(key);
                if (appFunctionDataList == null) {
                    return null;
                }
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(appFunctionDataList, 10));
                Iterator<T> it = appFunctionDataList.iterator();
                while (it.hasNext()) {
                    arrayList.add(this.serializableFactory.fromAppFunctionData((AppFunctionData) it.next()));
                }
                return arrayList;
            }

            public int hashCode() {
                return (this.itemClazz.hashCode() * 31) + this.serializableFactory.hashCode();
            }

            @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory.TypeParameter
            public void setValueInAppFunctionData(AppFunctionData.Builder appFunctionDataBuilder, String key, List list) {
                Intrinsics.checkNotNullParameter(appFunctionDataBuilder, "appFunctionDataBuilder");
                Intrinsics.checkNotNullParameter(key, "key");
                if (list == null) {
                    return;
                }
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (T t : list) {
                    AppFunctionSerializableFactory appFunctionSerializableFactory = this.serializableFactory;
                    Intrinsics.checkNotNull(t, "null cannot be cast to non-null type I of androidx.appfunctions.internal.AppFunctionSerializableFactory.TypeParameter.SerializableListTypeParameter");
                    arrayList.add(appFunctionSerializableFactory.toAppFunctionData(t));
                }
                appFunctionDataBuilder.setAppFunctionDataList(key, arrayList);
            }

            public String toString() {
                return "SerializableListTypeParameter(itemClazz=" + this.itemClazz + ", serializableFactory=" + this.serializableFactory + ')';
            }
        }

        /* JADX INFO: compiled from: AppFunctionSerializableFactory.kt */
        @Metadata
        public static final class SerializableTypeParameter<I, T extends I> implements TypeParameter<T> {
            public final Class clazz;
            public final AppFunctionSerializableFactory serializableFactory;

            public SerializableTypeParameter(Class clazz, AppFunctionSerializableFactory serializableFactory) {
                Intrinsics.checkNotNullParameter(clazz, "clazz");
                Intrinsics.checkNotNullParameter(serializableFactory, "serializableFactory");
                this.clazz = clazz;
                this.serializableFactory = serializableFactory;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof SerializableTypeParameter)) {
                    return false;
                }
                SerializableTypeParameter serializableTypeParameter = (SerializableTypeParameter) obj;
                return Intrinsics.areEqual(this.clazz, serializableTypeParameter.clazz) && Intrinsics.areEqual(this.serializableFactory, serializableTypeParameter.serializableFactory);
            }

            @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory.TypeParameter
            public Object getFromAppFunctionData(AppFunctionData appFunctionData, String key) {
                Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
                Intrinsics.checkNotNullParameter(key, "key");
                AppFunctionData appFunctionData2 = appFunctionData.getAppFunctionData(key);
                if (appFunctionData2 != null) {
                    return this.serializableFactory.fromAppFunctionData(appFunctionData2);
                }
                return null;
            }

            public int hashCode() {
                return (this.clazz.hashCode() * 31) + this.serializableFactory.hashCode();
            }

            @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory.TypeParameter
            public void setValueInAppFunctionData(AppFunctionData.Builder appFunctionDataBuilder, String key, Object obj) {
                Intrinsics.checkNotNullParameter(appFunctionDataBuilder, "appFunctionDataBuilder");
                Intrinsics.checkNotNullParameter(key, "key");
                if (obj == null) {
                    return;
                }
                appFunctionDataBuilder.setAppFunctionData(key, this.serializableFactory.toAppFunctionData(obj));
            }

            public String toString() {
                return "SerializableTypeParameter(clazz=" + this.clazz + ", serializableFactory=" + this.serializableFactory + ')';
            }
        }

        Object getFromAppFunctionData(AppFunctionData appFunctionData, String str);

        void setValueInAppFunctionData(AppFunctionData.Builder builder, String str, Object obj);
    }

    Object fromAppFunctionData(AppFunctionData appFunctionData);

    default AppFunctionComponentsMetadata getAppFunctionComponentsMetadata() {
        AppFunctionInventory appFunctionInventory$appfunctions_release = Dependencies.INSTANCE.getAppFunctionInventory$appfunctions_release();
        if (appFunctionInventory$appfunctions_release != null) {
            return appFunctionInventory$appfunctions_release.getComponentsMetadata();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    default AppFunctionData.Builder getAppFunctionDataBuilder(String qualifiedName) {
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        AppFunctionComponentsMetadata appFunctionComponentsMetadata = getAppFunctionComponentsMetadata();
        int i = 2;
        String str = null;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        Object[] objArr3 = 0;
        if (appFunctionComponentsMetadata == null) {
            return new AppFunctionData.Builder(qualifiedName, str, i, objArr3 == true ? 1 : 0);
        }
        Object obj = appFunctionComponentsMetadata.getDataTypes().get(qualifiedName);
        AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata = obj instanceof AppFunctionObjectTypeMetadata ? (AppFunctionObjectTypeMetadata) obj : null;
        return appFunctionObjectTypeMetadata == null ? new AppFunctionData.Builder(qualifiedName, objArr2 == true ? 1 : 0, i, objArr == true ? 1 : 0) : new AppFunctionData.Builder(appFunctionObjectTypeMetadata, appFunctionComponentsMetadata);
    }

    default AppFunctionData getAppFunctionDataWithSpec(AppFunctionData appFunctionData, String qualifiedName) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        AppFunctionComponentsMetadata appFunctionComponentsMetadata = getAppFunctionComponentsMetadata();
        if (appFunctionComponentsMetadata != null) {
            Object obj = appFunctionComponentsMetadata.getDataTypes().get(qualifiedName);
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata = obj instanceof AppFunctionObjectTypeMetadata ? (AppFunctionObjectTypeMetadata) obj : null;
            if (appFunctionObjectTypeMetadata != null) {
                return appFunctionData.replaceSpecWith$appfunctions_release(appFunctionObjectTypeMetadata, appFunctionComponentsMetadata);
            }
        }
        return appFunctionData;
    }

    AppFunctionData toAppFunctionData(Object obj);
}
