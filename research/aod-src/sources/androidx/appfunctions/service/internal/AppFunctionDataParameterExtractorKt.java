package androidx.appfunctions.service.internal;

import android.util.Log;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.AppFunctionInvalidArgumentException;
import androidx.appfunctions.metadata.AppFunctionArrayTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionBooleanTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionBytesTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionDataTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionDoubleTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionFloatTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionIntTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionLongTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionObjectTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionParameterMetadata;
import androidx.appfunctions.metadata.AppFunctionPendingIntentTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionReferenceTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionStringTypeMetadata;
import com.miui.aod.components.view.BaseStyleSelectView;
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
/* JADX INFO: compiled from: AppFunctionDataParameterExtractor.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AppFunctionDataParameterExtractorKt {
    public static final Object getArrayTypeParameterValue(AppFunctionData appFunctionData, String str, AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata, boolean z, boolean z2) {
        AppFunctionDataTypeMetadata itemType = appFunctionArrayTypeMetadata.getItemType();
        if (itemType instanceof AppFunctionIntTypeMetadata) {
            if (z2 || z) {
                return appFunctionData.getIntArray(str);
            }
            int[] intArray = appFunctionData.getIntArray(str);
            return intArray == null ? new int[0] : intArray;
        }
        if (itemType instanceof AppFunctionLongTypeMetadata) {
            if (z2 || z) {
                return appFunctionData.getLongArray(str);
            }
            long[] longArray = appFunctionData.getLongArray(str);
            return longArray == null ? new long[0] : longArray;
        }
        if (itemType instanceof AppFunctionFloatTypeMetadata) {
            if (z2 || z) {
                return appFunctionData.getFloatArray(str);
            }
            float[] floatArray = appFunctionData.getFloatArray(str);
            return floatArray == null ? new float[0] : floatArray;
        }
        if (itemType instanceof AppFunctionDoubleTypeMetadata) {
            if (z2 || z) {
                return appFunctionData.getDoubleArray(str);
            }
            double[] doubleArray = appFunctionData.getDoubleArray(str);
            return doubleArray == null ? new double[0] : doubleArray;
        }
        if (itemType instanceof AppFunctionBooleanTypeMetadata) {
            if (z2 || z) {
                return appFunctionData.getBooleanArray(str);
            }
            boolean[] booleanArray = appFunctionData.getBooleanArray(str);
            return booleanArray == null ? new boolean[0] : booleanArray;
        }
        if (itemType instanceof AppFunctionBytesTypeMetadata) {
            if (z2 || z) {
                return appFunctionData.getByteArray(str);
            }
            byte[] byteArray = appFunctionData.getByteArray(str);
            return byteArray == null ? new byte[0] : byteArray;
        }
        if (itemType instanceof AppFunctionStringTypeMetadata) {
            if (z2 || z) {
                return appFunctionData.getStringList(str);
            }
            List stringList = appFunctionData.getStringList(str);
            return stringList == null ? CollectionsKt.emptyList() : stringList;
        }
        if (itemType instanceof AppFunctionPendingIntentTypeMetadata) {
            if (z2 || z) {
                return appFunctionData.getPendingIntentList(str);
            }
            List pendingIntentList = appFunctionData.getPendingIntentList(str);
            return pendingIntentList == null ? CollectionsKt.emptyList() : pendingIntentList;
        }
        if (itemType instanceof AppFunctionObjectTypeMetadata) {
            if (z2 || z) {
                List<AppFunctionData> appFunctionDataList = appFunctionData.getAppFunctionDataList(str);
                if (appFunctionDataList == null) {
                    return null;
                }
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(appFunctionDataList, 10));
                for (AppFunctionData appFunctionData2 : appFunctionDataList) {
                    String qualifiedName = ((AppFunctionObjectTypeMetadata) itemType).getQualifiedName();
                    if (qualifiedName == null) {
                        throw new IllegalStateException("Required value was null.");
                    }
                    arrayList.add(appFunctionData2.deserialize(qualifiedName));
                }
                return arrayList;
            }
            List<AppFunctionData> appFunctionDataList2 = appFunctionData.getAppFunctionDataList(str);
            if (appFunctionDataList2 == null) {
                return CollectionsKt.emptyList();
            }
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(appFunctionDataList2, 10));
            for (AppFunctionData appFunctionData3 : appFunctionDataList2) {
                String qualifiedName2 = ((AppFunctionObjectTypeMetadata) itemType).getQualifiedName();
                if (qualifiedName2 == null) {
                    throw new IllegalStateException("Required value was null.");
                }
                arrayList2.add(appFunctionData3.deserialize(qualifiedName2));
            }
            return arrayList2;
        }
        if (!(itemType instanceof AppFunctionReferenceTypeMetadata)) {
            throw new IllegalStateException("Unknown item DataTypeMetadata: " + itemType.getClass());
        }
        if (z2 || z) {
            List<AppFunctionData> appFunctionDataList3 = appFunctionData.getAppFunctionDataList(str);
            if (appFunctionDataList3 == null) {
                return null;
            }
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(appFunctionDataList3, 10));
            for (AppFunctionData appFunctionData4 : appFunctionDataList3) {
                String referenceDataType = ((AppFunctionReferenceTypeMetadata) itemType).getReferenceDataType();
                if (referenceDataType == null) {
                    throw new IllegalStateException("Required value was null.");
                }
                arrayList3.add(appFunctionData4.deserialize(referenceDataType));
            }
            return arrayList3;
        }
        List<AppFunctionData> appFunctionDataList4 = appFunctionData.getAppFunctionDataList(str);
        if (appFunctionDataList4 == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(appFunctionDataList4, 10));
        for (AppFunctionData appFunctionData5 : appFunctionDataList4) {
            String referenceDataType2 = ((AppFunctionReferenceTypeMetadata) itemType).getReferenceDataType();
            if (referenceDataType2 == null) {
                throw new IllegalStateException("Required value was null.");
            }
            arrayList4.add(appFunctionData5.deserialize(referenceDataType2));
        }
        return arrayList4;
    }

    /* JADX WARN: Code duplicated, block: B:71:0x00fd  */
    public static final Object unsafeGetParameterValue(AppFunctionData appFunctionData, AppFunctionParameterMetadata parameterMetadata) throws AppFunctionInvalidArgumentException {
        Object objDeserialize;
        Intrinsics.checkNotNullParameter(appFunctionData, "<this>");
        Intrinsics.checkNotNullParameter(parameterMetadata, "parameterMetadata");
        try {
            String name = parameterMetadata.getName();
            boolean zIsRequired = parameterMetadata.isRequired();
            boolean zIsNullable = parameterMetadata.getDataType().isNullable();
            AppFunctionDataTypeMetadata dataType = parameterMetadata.getDataType();
            if (dataType instanceof AppFunctionIntTypeMetadata) {
                if (zIsRequired || zIsNullable) {
                    objDeserialize = appFunctionData.getIntOrNull(name);
                } else {
                    Integer intOrNull = appFunctionData.getIntOrNull(name);
                    objDeserialize = Integer.valueOf(intOrNull != null ? intOrNull.intValue() : 0);
                }
            } else if (dataType instanceof AppFunctionLongTypeMetadata) {
                if (zIsRequired || zIsNullable) {
                    objDeserialize = appFunctionData.getLongOrNull(name);
                } else {
                    Long longOrNull = appFunctionData.getLongOrNull(name);
                    objDeserialize = Long.valueOf(longOrNull != null ? longOrNull.longValue() : 0L);
                }
            } else if (dataType instanceof AppFunctionFloatTypeMetadata) {
                if (zIsRequired || zIsNullable) {
                    objDeserialize = appFunctionData.getFloatOrNull(name);
                } else {
                    Float floatOrNull = appFunctionData.getFloatOrNull(name);
                    objDeserialize = Float.valueOf(floatOrNull != null ? floatOrNull.floatValue() : 0.0f);
                }
            } else if (dataType instanceof AppFunctionDoubleTypeMetadata) {
                if (zIsRequired || zIsNullable) {
                    objDeserialize = appFunctionData.getDoubleOrNull(name);
                } else {
                    Double doubleOrNull = appFunctionData.getDoubleOrNull(name);
                    objDeserialize = Double.valueOf(doubleOrNull != null ? doubleOrNull.doubleValue() : BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                }
            } else if (dataType instanceof AppFunctionBooleanTypeMetadata) {
                if (zIsRequired || zIsNullable) {
                    objDeserialize = appFunctionData.getBooleanOrNull(name);
                } else {
                    Boolean booleanOrNull = appFunctionData.getBooleanOrNull(name);
                    objDeserialize = Boolean.valueOf(booleanOrNull != null ? booleanOrNull.booleanValue() : false);
                }
            } else {
                if (dataType instanceof AppFunctionBytesTypeMetadata) {
                    throw new IllegalStateException("Type of a single byte is not supported");
                }
                if (dataType instanceof AppFunctionStringTypeMetadata) {
                    objDeserialize = appFunctionData.getString(name);
                } else if (dataType instanceof AppFunctionPendingIntentTypeMetadata) {
                    objDeserialize = appFunctionData.getPendingIntent(name);
                } else if (dataType instanceof AppFunctionObjectTypeMetadata) {
                    AppFunctionData appFunctionData2 = appFunctionData.getAppFunctionData(name);
                    if (appFunctionData2 != null) {
                        String qualifiedName = ((AppFunctionObjectTypeMetadata) dataType).getQualifiedName();
                        if (qualifiedName == null) {
                            throw new IllegalStateException("Required value was null.");
                        }
                        objDeserialize = appFunctionData2.deserialize(qualifiedName);
                    } else {
                        objDeserialize = null;
                    }
                } else if (dataType instanceof AppFunctionArrayTypeMetadata) {
                    objDeserialize = getArrayTypeParameterValue(appFunctionData, parameterMetadata.getName(), (AppFunctionArrayTypeMetadata) dataType, dataType.isNullable(), parameterMetadata.isRequired());
                } else {
                    if (!(dataType instanceof AppFunctionReferenceTypeMetadata)) {
                        throw new IllegalStateException("Unknown DataTypeMetadata: " + dataType.getClass());
                    }
                    AppFunctionData appFunctionData3 = appFunctionData.getAppFunctionData(name);
                    if (appFunctionData3 != null) {
                        String referenceDataType = ((AppFunctionReferenceTypeMetadata) dataType).getReferenceDataType();
                        if (referenceDataType == null) {
                            throw new IllegalStateException("Required value was null.");
                        }
                        objDeserialize = appFunctionData3.deserialize(referenceDataType);
                    } else {
                        objDeserialize = null;
                    }
                }
            }
            if (objDeserialize == null && parameterMetadata.isRequired()) {
                Log.d("AppFunctions", "Parameter " + parameterMetadata.getName() + " is required");
                throw new IllegalArgumentException(("Parameter " + parameterMetadata.getName() + " is required").toString());
            }
            return objDeserialize;
        } catch (IllegalArgumentException e) {
            Log.d("AppFunctions", "Parameter " + parameterMetadata.getName() + " should be the type of " + parameterMetadata.getDataType(), e);
            throw new AppFunctionInvalidArgumentException("Parameter " + parameterMetadata.getName() + " should be the type of " + parameterMetadata.getDataType());
        }
    }
}
