package androidx.appfunctions.service.internal;

import android.app.PendingIntent;
import android.util.Log;
import androidx.appfunctions.AppFunctionAppUnknownException;
import androidx.appfunctions.AppFunctionData;
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
import androidx.appfunctions.metadata.AppFunctionPendingIntentTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionReferenceTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionResponseMetadata;
import androidx.appfunctions.metadata.AppFunctionStringTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionUnitTypeMetadata;
import java.util.ArrayList;
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
    */
/* JADX INFO: compiled from: AppFunctionResponseMetadataReturnValueBuilder.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AppFunctionResponseMetadataReturnValueBuilderKt {
    public static final AppFunctionData unsafeBuildReturnValue(AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata, Object obj) {
        AppFunctionData.Builder builder = new AppFunctionData.Builder("", null, 2, null);
        AppFunctionDataTypeMetadata itemType = appFunctionArrayTypeMetadata.getItemType();
        if (itemType instanceof AppFunctionLongTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.LongArray");
            return builder.setLongArray("androidAppfunctionsReturnValue", (long[]) obj).build();
        }
        if (itemType instanceof AppFunctionIntTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.IntArray");
            return builder.setIntArray("androidAppfunctionsReturnValue", (int[]) obj).build();
        }
        if (itemType instanceof AppFunctionDoubleTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.DoubleArray");
            return builder.setDoubleArray("androidAppfunctionsReturnValue", (double[]) obj).build();
        }
        if (itemType instanceof AppFunctionFloatTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.FloatArray");
            return builder.setFloatArray("androidAppfunctionsReturnValue", (float[]) obj).build();
        }
        if (itemType instanceof AppFunctionBooleanTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.BooleanArray");
            return builder.setBooleanArray("androidAppfunctionsReturnValue", (boolean[]) obj).build();
        }
        if (itemType instanceof AppFunctionStringTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.String>");
            return builder.setStringList("androidAppfunctionsReturnValue", (List) obj).build();
        }
        if (itemType instanceof AppFunctionBytesTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.ByteArray");
            return builder.setByteArray("androidAppfunctionsReturnValue", (byte[]) obj).build();
        }
        if (itemType instanceof AppFunctionPendingIntentTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<android.app.PendingIntent>");
            return builder.setPendingIntentList("androidAppfunctionsReturnValue", (List) obj).build();
        }
        if (itemType instanceof AppFunctionObjectTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any>");
            List list = (List) obj;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (Object obj2 : list) {
                AppFunctionData.Companion companion = AppFunctionData.Companion;
                String qualifiedName = ((AppFunctionObjectTypeMetadata) itemType).getQualifiedName();
                if (qualifiedName == null) {
                    throw new IllegalStateException("Required value was null.");
                }
                arrayList.add(companion.serialize(obj2, qualifiedName));
            }
            return builder.setAppFunctionDataList("androidAppfunctionsReturnValue", arrayList).build();
        }
        if (!(itemType instanceof AppFunctionReferenceTypeMetadata)) {
            throw new IllegalStateException("Unknown item DataTypeMetadata: " + itemType.getClass());
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any>");
        List list2 = (List) obj;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (Object obj3 : list2) {
            AppFunctionData.Companion companion2 = AppFunctionData.Companion;
            String referenceDataType = ((AppFunctionReferenceTypeMetadata) itemType).getReferenceDataType();
            if (referenceDataType == null) {
                throw new IllegalStateException("Required value was null.");
            }
            arrayList2.add(companion2.serialize(obj3, referenceDataType));
        }
        return builder.setAppFunctionDataList("androidAppfunctionsReturnValue", arrayList2).build();
    }

    public static final AppFunctionData unsafeBuildReturnValue(AppFunctionDataTypeMetadata appFunctionDataTypeMetadata, Object obj, AppFunctionResponseMetadata appFunctionResponseMetadata, AppFunctionComponentsMetadata appFunctionComponentsMetadata) {
        AppFunctionData.Builder builder = new AppFunctionData.Builder(appFunctionResponseMetadata, appFunctionComponentsMetadata);
        if (appFunctionDataTypeMetadata instanceof AppFunctionUnitTypeMetadata) {
            return AppFunctionData.EMPTY;
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionLongTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
            return builder.setLong("androidAppfunctionsReturnValue", ((Long) obj).longValue()).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionIntTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
            return builder.setInt("androidAppfunctionsReturnValue", ((Integer) obj).intValue()).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionDoubleTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
            return builder.setDouble("androidAppfunctionsReturnValue", ((Double) obj).doubleValue()).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionFloatTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
            return builder.setFloat("androidAppfunctionsReturnValue", ((Float) obj).floatValue()).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionBooleanTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
            return builder.setBoolean("androidAppfunctionsReturnValue", ((Boolean) obj).booleanValue()).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionStringTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
            return builder.setString("androidAppfunctionsReturnValue", (String) obj).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionBytesTypeMetadata) {
            throw new IllegalStateException("Type of a single byte is not supported");
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionPendingIntentTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type android.app.PendingIntent");
            return builder.setPendingIntent("androidAppfunctionsReturnValue", (PendingIntent) obj).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionObjectTypeMetadata) {
            AppFunctionData.Companion companion = AppFunctionData.Companion;
            String qualifiedName = ((AppFunctionObjectTypeMetadata) appFunctionDataTypeMetadata).getQualifiedName();
            if (qualifiedName != null) {
                return builder.setAppFunctionData("androidAppfunctionsReturnValue", companion.serialize(obj, qualifiedName)).build();
            }
            throw new IllegalStateException("Required value was null.");
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionReferenceTypeMetadata) {
            AppFunctionData.Companion companion2 = AppFunctionData.Companion;
            String referenceDataType = ((AppFunctionReferenceTypeMetadata) appFunctionDataTypeMetadata).getReferenceDataType();
            if (referenceDataType != null) {
                return builder.setAppFunctionData("androidAppfunctionsReturnValue", companion2.serialize(obj, referenceDataType)).build();
            }
            throw new IllegalStateException("Required value was null.");
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionArrayTypeMetadata) {
            return unsafeBuildReturnValue((AppFunctionArrayTypeMetadata) appFunctionDataTypeMetadata, obj);
        }
        throw new IllegalStateException("Unknown DataTypeMetadata: " + appFunctionDataTypeMetadata.getClass());
    }

    public static final AppFunctionData unsafeBuildReturnValue(AppFunctionResponseMetadata appFunctionResponseMetadata, Object obj, AppFunctionComponentsMetadata componentsMetadata) throws AppFunctionAppUnknownException {
        Intrinsics.checkNotNullParameter(appFunctionResponseMetadata, "<this>");
        Intrinsics.checkNotNullParameter(componentsMetadata, "componentsMetadata");
        try {
            if (obj != null) {
                return unsafeBuildReturnValue(appFunctionResponseMetadata.getValueType(), obj, appFunctionResponseMetadata, componentsMetadata);
            }
            if (appFunctionResponseMetadata.getValueType().isNullable()) {
                return AppFunctionData.EMPTY;
            }
            throw new IllegalStateException("Unexpected null for non-null return type");
        } catch (Exception e) {
            Log.d("AppFunctions", "Something went wrong when building the return value", e);
            throw new AppFunctionAppUnknownException("Something went wrong when executing an app function");
        }
    }
}
