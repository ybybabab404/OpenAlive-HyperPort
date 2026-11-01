package androidx.appfunctions.internal;

import androidx.appfunctions.metadata.AppFunctionArrayTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionBooleanTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.AppFunctionDataTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionLongTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionObjectTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionParameterMetadata;
import androidx.appfunctions.metadata.AppFunctionReferenceTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionResponseMetadata;
import androidx.appfunctions.metadata.AppFunctionSchemaMetadata;
import androidx.appfunctions.metadata.AppFunctionStringTypeMetadata;
import androidx.appfunctions.metadata.CompileTimeAppFunctionMetadata;
import com.xiaomi.onetrack.api.am;
import com.xiaomi.onetrack.api.bf;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: renamed from: androidx.appfunctions.internal.$AggregatedAppFunctionInventory_Impl, reason: invalid class name */
/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: $AggregatedAppFunctionInventory_Impl.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class C$AggregatedAppFunctionInventory_Impl extends AggregatedAppFunctionInventory {
    public final List inventories = CollectionsKt.listOf((Object[]) new AppFunctionInventory[]{new AppFunctionInventory() { // from class: com.miui.aod.gemini.$AodDeviceStateSetter_AppFunctionInventory

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionArrayTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_ITEMIZATIONKEYS;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_ITEMIZATIONKEYS_PRIMITIVE_ITEM_TYPE;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_KEY;

        @NotNull
        private final AppFunctionBooleanTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_REQUESTINITIATEDWHILEUNLOCKED;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_VALUE;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMRESPONSE_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMRESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMRESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_CURRENTVALUE;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMRESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_FAILUREREASON;

        @NotNull
        private final AppFunctionBooleanTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMRESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_ISSUCCESSFUL;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMRESPONSE_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionComponentsMetadata componentsMetadata;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> componentsMetadataDataTypesMap;

        @NotNull
        private final Map<String, CompileTimeAppFunctionMetadata> functionIdToMetadataMap = MapsKt.mapOf(TuplesKt.to(AodDeviceStateSetterIds.SET_DEVICE_STATE_ITEM_ID, ComMiuiAodGeminiAodDeviceStateSetterSetDeviceStateItemMetadataObject.INSTANCE.getAPP_FUNCTION_METADATA()));

        /* JADX INFO: renamed from: com.miui.aod.gemini.$AodDeviceStateSetter_AppFunctionInventory$ComMiuiAodGeminiAodDeviceStateSetterSetDeviceStateItemMetadataObject */
        /* JADX INFO: compiled from: $AodDeviceStateSetter_AppFunctionInventory.kt */
        @Metadata
        public static final class ComMiuiAodGeminiAodDeviceStateSetterSetDeviceStateItemMetadataObject {

            @NotNull
            private static final CompileTimeAppFunctionMetadata APP_FUNCTION_METADATA;

            @NotNull
            public static final ComMiuiAodGeminiAodDeviceStateSetterSetDeviceStateItemMetadataObject INSTANCE = new ComMiuiAodGeminiAodDeviceStateSetterSetDeviceStateItemMetadataObject();

            @NotNull
            private static final List<AppFunctionParameterMetadata> PARAMETER_METADATA_LIST;

            @NotNull
            private static final AppFunctionReferenceTypeMetadata PARAMETER_METADATA_SETDEVICESTATEITEMPARAMS_REFERENCE_DATA_TYPE;

            @NotNull
            private static final AppFunctionReferenceTypeMetadata REFERENCE_RESPONSE_VALUE_TYPE;

            @NotNull
            private static final AppFunctionResponseMetadata RESPONSE_METADATA;

            @Nullable
            private static final AppFunctionSchemaMetadata SCHEMA_METADATA;

            @NotNull
            private static final AppFunctionParameterMetadata SETDEVICESTATEITEMPARAMS_PARAMETER_METADATA;

            static {
                AppFunctionSchemaMetadata appFunctionSchemaMetadata = new AppFunctionSchemaMetadata("device_state", "setDeviceStateItem", 1L);
                SCHEMA_METADATA = appFunctionSchemaMetadata;
                AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemParams", false, "");
                PARAMETER_METADATA_SETDEVICESTATEITEMPARAMS_REFERENCE_DATA_TYPE = appFunctionReferenceTypeMetadata;
                AppFunctionParameterMetadata appFunctionParameterMetadata = new AppFunctionParameterMetadata("setDeviceStateItemParams", true, appFunctionReferenceTypeMetadata, "");
                SETDEVICESTATEITEMPARAMS_PARAMETER_METADATA = appFunctionParameterMetadata;
                List<AppFunctionParameterMetadata> listListOf = CollectionsKt.listOf(appFunctionParameterMetadata);
                PARAMETER_METADATA_LIST = listListOf;
                AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata2 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemResponse", false, "");
                REFERENCE_RESPONSE_VALUE_TYPE = appFunctionReferenceTypeMetadata2;
                AppFunctionResponseMetadata appFunctionResponseMetadata = new AppFunctionResponseMetadata(appFunctionReferenceTypeMetadata2, "");
                RESPONSE_METADATA = appFunctionResponseMetadata;
                APP_FUNCTION_METADATA = new CompileTimeAppFunctionMetadata(AodDeviceStateSetterIds.SET_DEVICE_STATE_ITEM_ID, true, appFunctionSchemaMetadata, listListOf, appFunctionResponseMetadata, null, null, 96, null);
            }

            private ComMiuiAodGeminiAodDeviceStateSetterSetDeviceStateItemMetadataObject() {
            }

            @NotNull
            public final CompileTimeAppFunctionMetadata getAPP_FUNCTION_METADATA() {
                return APP_FUNCTION_METADATA;
            }
        }

        {
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_KEY = appFunctionStringTypeMetadata;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata2 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_ITEMIZATIONKEYS_PRIMITIVE_ITEM_TYPE = appFunctionStringTypeMetadata2;
            AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata = new AppFunctionArrayTypeMetadata(appFunctionStringTypeMetadata2, false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_ITEMIZATIONKEYS = appFunctionArrayTypeMetadata;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata3 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_VALUE = appFunctionStringTypeMetadata3;
            AppFunctionBooleanTypeMetadata appFunctionBooleanTypeMetadata = new AppFunctionBooleanTypeMetadata(true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_REQUESTINITIATEDWHILEUNLOCKED = appFunctionBooleanTypeMetadata;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf = MapsKt.mapOf(TuplesKt.to("key", appFunctionStringTypeMetadata), TuplesKt.to("itemizationKeys", appFunctionArrayTypeMetadata), TuplesKt.to(am.t, appFunctionStringTypeMetadata3), TuplesKt.to("requestInitiatedWhileUnlocked", appFunctionBooleanTypeMetadata));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf;
            List<String> listListOf = CollectionsKt.listOf((Object[]) new String[]{"key", am.t});
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata = new AppFunctionObjectTypeMetadata(mapMapOf, listListOf, "com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemParams", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMPARAMS_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata;
            AppFunctionBooleanTypeMetadata appFunctionBooleanTypeMetadata2 = new AppFunctionBooleanTypeMetadata(false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMRESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_ISSUCCESSFUL = appFunctionBooleanTypeMetadata2;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata4 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMRESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_CURRENTVALUE = appFunctionStringTypeMetadata4;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata5 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMRESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_FAILUREREASON = appFunctionStringTypeMetadata5;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf2 = MapsKt.mapOf(TuplesKt.to("isSuccessful", appFunctionBooleanTypeMetadata2), TuplesKt.to("currentValue", appFunctionStringTypeMetadata4), TuplesKt.to("failureReason", appFunctionStringTypeMetadata5));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMRESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf2;
            List<String> listListOf2 = CollectionsKt.listOf((Object[]) new String[]{"isSuccessful", "currentValue"});
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMRESPONSE_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf2;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata2 = new AppFunctionObjectTypeMetadata(mapMapOf2, listListOf2, "com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemResponse", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_SETDEVICESTATEITEMRESPONSE_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata2;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf3 = MapsKt.mapOf(TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemParams", appFunctionObjectTypeMetadata), TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemResponse", appFunctionObjectTypeMetadata2));
            this.componentsMetadataDataTypesMap = mapMapOf3;
            this.componentsMetadata = new AppFunctionComponentsMetadata(mapMapOf3);
        }

        @Override // androidx.appfunctions.internal.AppFunctionInventory
        @NotNull
        public AppFunctionComponentsMetadata getComponentsMetadata() {
            return this.componentsMetadata;
        }

        @Override // androidx.appfunctions.internal.AppFunctionInventory
        @NotNull
        public Map<String, CompileTimeAppFunctionMetadata> getFunctionIdToMetadataMap() {
            return this.functionIdToMetadataMap;
        }
    }, new AppFunctionInventory() { // from class: com.miui.aod.gemini.$AodMetadataManager_AppFunctionInventory

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_HINTTEXT;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_KEY;

        @NotNull
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_NAME;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_POSSIBLEVALUES;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_PURPOSE;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_SENSITIVITY;

        @NotNull
        private final AppFunctionBooleanTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_WRITABLE;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEFAULTVALUE;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEVICELOCALE;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_GLOBALHINTTEXT;

        @NotNull
        private final AppFunctionArrayTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_ITEMIZATIONTYPES;

        @NotNull
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_ITEMIZATIONTYPES_REFERENCE_ITEM_TYPE;

        @NotNull
        private final AppFunctionArrayTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_PERSCREENMETADATA;

        @NotNull
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_PERSCREENMETADATA_REFERENCE_ITEM_TYPE;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETDEVICESTATEMETADATAPARAMS_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETDEVICESTATEMETADATAPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionBooleanTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETDEVICESTATEMETADATAPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_REQUESTINITIATEDWHILEUNLOCKED;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETDEVICESTATEMETADATAPARAMS_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONDETAIL_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONDETAIL_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONDETAIL_OBJECT_DATA_TYPE_PROPERTIES_MAP_KEY;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONDETAIL_OBJECT_DATA_TYPE_PROPERTIES_MAP_VALUE;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONDETAIL_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE_PROPERTIES_MAP_HINTTEXT;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE_PROPERTIES_MAP_KEY;

        @NotNull
        private final AppFunctionArrayTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE_PROPERTIES_MAP_VALUES;

        @NotNull
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE_PROPERTIES_MAP_VALUES_REFERENCE_ITEM_TYPE;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_PROPERTIES_MAP_ENGLISH;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_PROPERTIES_MAP_LOCALIZED;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_DESCRIPTION;

        @NotNull
        private final AppFunctionArrayTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEVICESTATEITEMSMETADATA;

        @NotNull
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEVICESTATEITEMSMETADATA_REFERENCE_ITEM_TYPE;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_INTENTURI;

        @NotNull
        private final AppFunctionArrayTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_ITEMIZATIONTYPES;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_ITEMIZATIONTYPES_PRIMITIVE_ITEM_TYPE;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionComponentsMetadata componentsMetadata;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> componentsMetadataDataTypesMap;

        @NotNull
        private final Map<String, CompileTimeAppFunctionMetadata> functionIdToMetadataMap = MapsKt.mapOf(TuplesKt.to(AodMetadataManagerIds.GET_DEVICE_STATE_METADATA_ID, ComMiuiAodGeminiAodMetadataManagerGetDeviceStateMetadataMetadataObject.INSTANCE.getAPP_FUNCTION_METADATA()));

        /* JADX INFO: renamed from: com.miui.aod.gemini.$AodMetadataManager_AppFunctionInventory$ComMiuiAodGeminiAodMetadataManagerGetDeviceStateMetadataMetadataObject */
        /* JADX INFO: compiled from: $AodMetadataManager_AppFunctionInventory.kt */
        @Metadata
        public static final class ComMiuiAodGeminiAodMetadataManagerGetDeviceStateMetadataMetadataObject {

            @NotNull
            private static final CompileTimeAppFunctionMetadata APP_FUNCTION_METADATA;

            @NotNull
            private static final AppFunctionParameterMetadata GETDEVICESTATEMETADATAPARAMS_PARAMETER_METADATA;

            @NotNull
            public static final ComMiuiAodGeminiAodMetadataManagerGetDeviceStateMetadataMetadataObject INSTANCE = new ComMiuiAodGeminiAodMetadataManagerGetDeviceStateMetadataMetadataObject();

            @NotNull
            private static final AppFunctionReferenceTypeMetadata PARAMETER_METADATA_GETDEVICESTATEMETADATAPARAMS_REFERENCE_DATA_TYPE;

            @NotNull
            private static final List<AppFunctionParameterMetadata> PARAMETER_METADATA_LIST;

            @NotNull
            private static final AppFunctionReferenceTypeMetadata REFERENCE_RESPONSE_VALUE_TYPE;

            @NotNull
            private static final AppFunctionResponseMetadata RESPONSE_METADATA;

            @Nullable
            private static final AppFunctionSchemaMetadata SCHEMA_METADATA;

            static {
                AppFunctionSchemaMetadata appFunctionSchemaMetadata = new AppFunctionSchemaMetadata("device_state", "getDeviceStateMetadata", 1L);
                SCHEMA_METADATA = appFunctionSchemaMetadata;
                AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.GetDeviceStateMetadataParams", false, "");
                PARAMETER_METADATA_GETDEVICESTATEMETADATAPARAMS_REFERENCE_DATA_TYPE = appFunctionReferenceTypeMetadata;
                AppFunctionParameterMetadata appFunctionParameterMetadata = new AppFunctionParameterMetadata("getDeviceStateMetadataParams", true, appFunctionReferenceTypeMetadata, "");
                GETDEVICESTATEMETADATAPARAMS_PARAMETER_METADATA = appFunctionParameterMetadata;
                List<AppFunctionParameterMetadata> listListOf = CollectionsKt.listOf(appFunctionParameterMetadata);
                PARAMETER_METADATA_LIST = listListOf;
                AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata2 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.DeviceStateMetadataResponse", false, "");
                REFERENCE_RESPONSE_VALUE_TYPE = appFunctionReferenceTypeMetadata2;
                AppFunctionResponseMetadata appFunctionResponseMetadata = new AppFunctionResponseMetadata(appFunctionReferenceTypeMetadata2, "");
                RESPONSE_METADATA = appFunctionResponseMetadata;
                APP_FUNCTION_METADATA = new CompileTimeAppFunctionMetadata(AodMetadataManagerIds.GET_DEVICE_STATE_METADATA_ID, true, appFunctionSchemaMetadata, listListOf, appFunctionResponseMetadata, null, null, 96, null);
            }

            private ComMiuiAodGeminiAodMetadataManagerGetDeviceStateMetadataMetadataObject() {
            }

            @NotNull
            public final CompileTimeAppFunctionMetadata getAPP_FUNCTION_METADATA() {
                return APP_FUNCTION_METADATA;
            }
        }

        {
            AppFunctionBooleanTypeMetadata appFunctionBooleanTypeMetadata = new AppFunctionBooleanTypeMetadata(true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETDEVICESTATEMETADATAPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_REQUESTINITIATEDWHILEUNLOCKED = appFunctionBooleanTypeMetadata;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf = MapsKt.mapOf(TuplesKt.to("requestInitiatedWhileUnlocked", appFunctionBooleanTypeMetadata));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETDEVICESTATEMETADATAPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf;
            List<String> listEmptyList = CollectionsKt.emptyList();
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETDEVICESTATEMETADATAPARAMS_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listEmptyList;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata = new AppFunctionObjectTypeMetadata(mapMapOf, listEmptyList, "com.google.android.appfunctions.schema.devicestate.v1.GetDeviceStateMetadataParams", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETDEVICESTATEMETADATAPARAMS_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_PROPERTIES_MAP_ENGLISH = appFunctionStringTypeMetadata;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata2 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_PROPERTIES_MAP_LOCALIZED = appFunctionStringTypeMetadata2;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf2 = MapsKt.mapOf(TuplesKt.to("english", appFunctionStringTypeMetadata), TuplesKt.to("localized", appFunctionStringTypeMetadata2));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf2;
            List<String> listListOf = CollectionsKt.listOf("english");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata2 = new AppFunctionObjectTypeMetadata(mapMapOf2, listListOf, "com.google.android.appfunctions.schema.devicestate.v1.LocalizedString", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata2;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata3 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_KEY = appFunctionStringTypeMetadata3;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata4 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_PURPOSE = appFunctionStringTypeMetadata4;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.LocalizedString", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_NAME = appFunctionReferenceTypeMetadata;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata5 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_SENSITIVITY = appFunctionStringTypeMetadata5;
            AppFunctionBooleanTypeMetadata appFunctionBooleanTypeMetadata2 = new AppFunctionBooleanTypeMetadata(true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_WRITABLE = appFunctionBooleanTypeMetadata2;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata6 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_POSSIBLEVALUES = appFunctionStringTypeMetadata6;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata7 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_HINTTEXT = appFunctionStringTypeMetadata7;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf3 = MapsKt.mapOf(TuplesKt.to("key", appFunctionStringTypeMetadata3), TuplesKt.to("purpose", appFunctionStringTypeMetadata4), TuplesKt.to(bf.a, appFunctionReferenceTypeMetadata), TuplesKt.to("sensitivity", appFunctionStringTypeMetadata5), TuplesKt.to("writable", appFunctionBooleanTypeMetadata2), TuplesKt.to("possibleValues", appFunctionStringTypeMetadata6), TuplesKt.to("hintText", appFunctionStringTypeMetadata7));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf3;
            List<String> listListOf2 = CollectionsKt.listOf((Object[]) new String[]{"key", "purpose"});
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf2;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata3 = new AppFunctionObjectTypeMetadata(mapMapOf3, listListOf2, "com.google.android.appfunctions.schema.devicestate.v1.DeviceStateItemMetadata", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEMMETADATA_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata3;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata8 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_DESCRIPTION = appFunctionStringTypeMetadata8;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata9 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_INTENTURI = appFunctionStringTypeMetadata9;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata10 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_ITEMIZATIONTYPES_PRIMITIVE_ITEM_TYPE = appFunctionStringTypeMetadata10;
            AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata = new AppFunctionArrayTypeMetadata(appFunctionStringTypeMetadata10, false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_ITEMIZATIONTYPES = appFunctionArrayTypeMetadata;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata2 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.DeviceStateItemMetadata", false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEVICESTATEITEMSMETADATA_REFERENCE_ITEM_TYPE = appFunctionReferenceTypeMetadata2;
            AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata2 = new AppFunctionArrayTypeMetadata(appFunctionReferenceTypeMetadata2, false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEVICESTATEITEMSMETADATA = appFunctionArrayTypeMetadata2;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf4 = MapsKt.mapOf(TuplesKt.to("description", appFunctionStringTypeMetadata8), TuplesKt.to("intentUri", appFunctionStringTypeMetadata9), TuplesKt.to("itemizationTypes", appFunctionArrayTypeMetadata), TuplesKt.to("deviceStateItemsMetadata", appFunctionArrayTypeMetadata2));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf4;
            List<String> listListOf3 = CollectionsKt.listOf("description");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf3;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata4 = new AppFunctionObjectTypeMetadata(mapMapOf4, listListOf3, "com.google.android.appfunctions.schema.devicestate.v1.PerScreenMetadata", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENMETADATA_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata4;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata11 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONDETAIL_OBJECT_DATA_TYPE_PROPERTIES_MAP_KEY = appFunctionStringTypeMetadata11;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata12 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONDETAIL_OBJECT_DATA_TYPE_PROPERTIES_MAP_VALUE = appFunctionStringTypeMetadata12;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf5 = MapsKt.mapOf(TuplesKt.to("key", appFunctionStringTypeMetadata11), TuplesKt.to(am.t, appFunctionStringTypeMetadata12));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONDETAIL_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf5;
            List<String> listListOf4 = CollectionsKt.listOf((Object[]) new String[]{"key", am.t});
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONDETAIL_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf4;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata5 = new AppFunctionObjectTypeMetadata(mapMapOf5, listListOf4, "com.google.android.appfunctions.schema.devicestate.v1.ItemizationDetail", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONDETAIL_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata5;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata13 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE_PROPERTIES_MAP_KEY = appFunctionStringTypeMetadata13;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata3 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.ItemizationDetail", false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE_PROPERTIES_MAP_VALUES_REFERENCE_ITEM_TYPE = appFunctionReferenceTypeMetadata3;
            AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata3 = new AppFunctionArrayTypeMetadata(appFunctionReferenceTypeMetadata3, false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE_PROPERTIES_MAP_VALUES = appFunctionArrayTypeMetadata3;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata14 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE_PROPERTIES_MAP_HINTTEXT = appFunctionStringTypeMetadata14;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf6 = MapsKt.mapOf(TuplesKt.to("key", appFunctionStringTypeMetadata13), TuplesKt.to("values", appFunctionArrayTypeMetadata3), TuplesKt.to("hintText", appFunctionStringTypeMetadata14));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf6;
            List<String> listListOf5 = CollectionsKt.listOf("key");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf5;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata6 = new AppFunctionObjectTypeMetadata(mapMapOf6, listListOf5, "com.google.android.appfunctions.schema.devicestate.v1.ItemizationType", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_ITEMIZATIONTYPE_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata6;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata4 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.PerScreenMetadata", false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_PERSCREENMETADATA_REFERENCE_ITEM_TYPE = appFunctionReferenceTypeMetadata4;
            AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata4 = new AppFunctionArrayTypeMetadata(appFunctionReferenceTypeMetadata4, false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_PERSCREENMETADATA = appFunctionArrayTypeMetadata4;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata15 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEVICELOCALE = appFunctionStringTypeMetadata15;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata16 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_GLOBALHINTTEXT = appFunctionStringTypeMetadata16;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata5 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.ItemizationType", false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_ITEMIZATIONTYPES_REFERENCE_ITEM_TYPE = appFunctionReferenceTypeMetadata5;
            AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata5 = new AppFunctionArrayTypeMetadata(appFunctionReferenceTypeMetadata5, false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_ITEMIZATIONTYPES = appFunctionArrayTypeMetadata5;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata17 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEFAULTVALUE = appFunctionStringTypeMetadata17;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf7 = MapsKt.mapOf(TuplesKt.to("perScreenMetadata", appFunctionArrayTypeMetadata4), TuplesKt.to("deviceLocale", appFunctionStringTypeMetadata15), TuplesKt.to("globalHintText", appFunctionStringTypeMetadata16), TuplesKt.to("itemizationTypes", appFunctionArrayTypeMetadata5), TuplesKt.to("defaultValue", appFunctionStringTypeMetadata17));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf7;
            List<String> listListOf6 = CollectionsKt.listOf("deviceLocale");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf6;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata7 = new AppFunctionObjectTypeMetadata(mapMapOf7, listListOf6, "com.google.android.appfunctions.schema.devicestate.v1.DeviceStateMetadataResponse", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEMETADATARESPONSE_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata7;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf8 = MapsKt.mapOf(TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.GetDeviceStateMetadataParams", appFunctionObjectTypeMetadata), TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.LocalizedString", appFunctionObjectTypeMetadata2), TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.DeviceStateItemMetadata", appFunctionObjectTypeMetadata3), TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.PerScreenMetadata", appFunctionObjectTypeMetadata4), TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.ItemizationDetail", appFunctionObjectTypeMetadata5), TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.ItemizationType", appFunctionObjectTypeMetadata6), TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.DeviceStateMetadataResponse", appFunctionObjectTypeMetadata7));
            this.componentsMetadataDataTypesMap = mapMapOf8;
            this.componentsMetadata = new AppFunctionComponentsMetadata(mapMapOf8);
        }

        @Override // androidx.appfunctions.internal.AppFunctionInventory
        @NotNull
        public AppFunctionComponentsMetadata getComponentsMetadata() {
            return this.componentsMetadata;
        }

        @Override // androidx.appfunctions.internal.AppFunctionInventory
        @NotNull
        public Map<String, CompileTimeAppFunctionMetadata> getFunctionIdToMetadataMap() {
            return this.functionIdToMetadataMap;
        }
    }, new AppFunctionInventory() { // from class: com.miui.aod.gemini.$AodStateManager_AppFunctionInventory

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP_HINTTEXT;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP_JSONVALUE;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP_KEY;

        @NotNull
        private final AppFunctionLongTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP_LASTUPDATEDEPOCHMILLIS;

        @NotNull
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP_NAME;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP_PURPOSE;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEVICELOCALE;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_GLOBALHINTTEXT;

        @NotNull
        private final AppFunctionArrayTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_PERSCREENDEVICESTATES;

        @NotNull
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_PERSCREENDEVICESTATES_REFERENCE_ITEM_TYPE;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETUNCATEGORIZEDDEVICESTATEPARAMS_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETUNCATEGORIZEDDEVICESTATEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionBooleanTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETUNCATEGORIZEDDEVICESTATEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_REQUESTINITIATEDWHILEUNLOCKED;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETUNCATEGORIZEDDEVICESTATEPARAMS_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_PROPERTIES_MAP_ENGLISH;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_PROPERTIES_MAP_LOCALIZED;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionObjectTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE_PROPERTIES_MAP;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE_PROPERTIES_MAP_DESCRIPTION;

        @NotNull
        private final AppFunctionArrayTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEVICESTATEITEMS;

        @NotNull
        private final AppFunctionReferenceTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEVICESTATEITEMS_REFERENCE_ITEM_TYPE;

        @NotNull
        private final AppFunctionStringTypeMetadata COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE_PROPERTIES_MAP_INTENTURI;

        @NotNull
        private final List<String> COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST;

        @NotNull
        private final AppFunctionComponentsMetadata componentsMetadata;

        @NotNull
        private final Map<String, AppFunctionDataTypeMetadata> componentsMetadataDataTypesMap;

        @NotNull
        private final Map<String, CompileTimeAppFunctionMetadata> functionIdToMetadataMap = MapsKt.mapOf(TuplesKt.to(AodStateManagerIds.GET_UNCATEGORIZED_DEVICE_STATE_ID, ComMiuiAodGeminiAodStateManagerGetUncategorizedDeviceStateMetadataObject.INSTANCE.getAPP_FUNCTION_METADATA()));

        /* JADX INFO: renamed from: com.miui.aod.gemini.$AodStateManager_AppFunctionInventory$ComMiuiAodGeminiAodStateManagerGetUncategorizedDeviceStateMetadataObject */
        /* JADX INFO: compiled from: $AodStateManager_AppFunctionInventory.kt */
        @Metadata
        public static final class ComMiuiAodGeminiAodStateManagerGetUncategorizedDeviceStateMetadataObject {

            @NotNull
            private static final CompileTimeAppFunctionMetadata APP_FUNCTION_METADATA;

            @NotNull
            private static final AppFunctionParameterMetadata GETUNCATEGORIZEDDEVICESTATEPARAMS_PARAMETER_METADATA;

            @NotNull
            public static final ComMiuiAodGeminiAodStateManagerGetUncategorizedDeviceStateMetadataObject INSTANCE = new ComMiuiAodGeminiAodStateManagerGetUncategorizedDeviceStateMetadataObject();

            @NotNull
            private static final AppFunctionReferenceTypeMetadata PARAMETER_METADATA_GETUNCATEGORIZEDDEVICESTATEPARAMS_REFERENCE_DATA_TYPE;

            @NotNull
            private static final List<AppFunctionParameterMetadata> PARAMETER_METADATA_LIST;

            @NotNull
            private static final AppFunctionReferenceTypeMetadata REFERENCE_RESPONSE_VALUE_TYPE;

            @NotNull
            private static final AppFunctionResponseMetadata RESPONSE_METADATA;

            @Nullable
            private static final AppFunctionSchemaMetadata SCHEMA_METADATA;

            static {
                AppFunctionSchemaMetadata appFunctionSchemaMetadata = new AppFunctionSchemaMetadata("device_state", "getUncategorizedDeviceState", 1L);
                SCHEMA_METADATA = appFunctionSchemaMetadata;
                AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.GetUncategorizedDeviceStateParams", false, "");
                PARAMETER_METADATA_GETUNCATEGORIZEDDEVICESTATEPARAMS_REFERENCE_DATA_TYPE = appFunctionReferenceTypeMetadata;
                AppFunctionParameterMetadata appFunctionParameterMetadata = new AppFunctionParameterMetadata("getUncategorizedDeviceStateParams", true, appFunctionReferenceTypeMetadata, "");
                GETUNCATEGORIZEDDEVICESTATEPARAMS_PARAMETER_METADATA = appFunctionParameterMetadata;
                List<AppFunctionParameterMetadata> listListOf = CollectionsKt.listOf(appFunctionParameterMetadata);
                PARAMETER_METADATA_LIST = listListOf;
                AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata2 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.DeviceStateResponse", false, "");
                REFERENCE_RESPONSE_VALUE_TYPE = appFunctionReferenceTypeMetadata2;
                AppFunctionResponseMetadata appFunctionResponseMetadata = new AppFunctionResponseMetadata(appFunctionReferenceTypeMetadata2, "");
                RESPONSE_METADATA = appFunctionResponseMetadata;
                APP_FUNCTION_METADATA = new CompileTimeAppFunctionMetadata(AodStateManagerIds.GET_UNCATEGORIZED_DEVICE_STATE_ID, true, appFunctionSchemaMetadata, listListOf, appFunctionResponseMetadata, null, null, 96, null);
            }

            private ComMiuiAodGeminiAodStateManagerGetUncategorizedDeviceStateMetadataObject() {
            }

            @NotNull
            public final CompileTimeAppFunctionMetadata getAPP_FUNCTION_METADATA() {
                return APP_FUNCTION_METADATA;
            }
        }

        {
            AppFunctionBooleanTypeMetadata appFunctionBooleanTypeMetadata = new AppFunctionBooleanTypeMetadata(true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETUNCATEGORIZEDDEVICESTATEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP_REQUESTINITIATEDWHILEUNLOCKED = appFunctionBooleanTypeMetadata;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf = MapsKt.mapOf(TuplesKt.to("requestInitiatedWhileUnlocked", appFunctionBooleanTypeMetadata));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETUNCATEGORIZEDDEVICESTATEPARAMS_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf;
            List<String> listEmptyList = CollectionsKt.emptyList();
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETUNCATEGORIZEDDEVICESTATEPARAMS_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listEmptyList;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata = new AppFunctionObjectTypeMetadata(mapMapOf, listEmptyList, "com.google.android.appfunctions.schema.devicestate.v1.GetUncategorizedDeviceStateParams", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_GETUNCATEGORIZEDDEVICESTATEPARAMS_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_PROPERTIES_MAP_ENGLISH = appFunctionStringTypeMetadata;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata2 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_PROPERTIES_MAP_LOCALIZED = appFunctionStringTypeMetadata2;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf2 = MapsKt.mapOf(TuplesKt.to("english", appFunctionStringTypeMetadata), TuplesKt.to("localized", appFunctionStringTypeMetadata2));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf2;
            List<String> listListOf = CollectionsKt.listOf("english");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata2 = new AppFunctionObjectTypeMetadata(mapMapOf2, listListOf, "com.google.android.appfunctions.schema.devicestate.v1.LocalizedString", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_LOCALIZEDSTRING_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata2;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata3 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP_KEY = appFunctionStringTypeMetadata3;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata4 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP_PURPOSE = appFunctionStringTypeMetadata4;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.LocalizedString", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP_NAME = appFunctionReferenceTypeMetadata;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata5 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP_JSONVALUE = appFunctionStringTypeMetadata5;
            AppFunctionLongTypeMetadata appFunctionLongTypeMetadata = new AppFunctionLongTypeMetadata(true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP_LASTUPDATEDEPOCHMILLIS = appFunctionLongTypeMetadata;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata6 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP_HINTTEXT = appFunctionStringTypeMetadata6;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf3 = MapsKt.mapOf(TuplesKt.to("key", appFunctionStringTypeMetadata3), TuplesKt.to("purpose", appFunctionStringTypeMetadata4), TuplesKt.to(bf.a, appFunctionReferenceTypeMetadata), TuplesKt.to("jsonValue", appFunctionStringTypeMetadata5), TuplesKt.to("lastUpdatedEpochMillis", appFunctionLongTypeMetadata), TuplesKt.to("hintText", appFunctionStringTypeMetadata6));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf3;
            List<String> listListOf2 = CollectionsKt.listOf("purpose");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf2;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata3 = new AppFunctionObjectTypeMetadata(mapMapOf3, listListOf2, "com.google.android.appfunctions.schema.devicestate.v1.DeviceStateItem", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATEITEM_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata3;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata7 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE_PROPERTIES_MAP_DESCRIPTION = appFunctionStringTypeMetadata7;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata8 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE_PROPERTIES_MAP_INTENTURI = appFunctionStringTypeMetadata8;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata2 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.DeviceStateItem", false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEVICESTATEITEMS_REFERENCE_ITEM_TYPE = appFunctionReferenceTypeMetadata2;
            AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata = new AppFunctionArrayTypeMetadata(appFunctionReferenceTypeMetadata2, false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEVICESTATEITEMS = appFunctionArrayTypeMetadata;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf4 = MapsKt.mapOf(TuplesKt.to("description", appFunctionStringTypeMetadata7), TuplesKt.to("intentUri", appFunctionStringTypeMetadata8), TuplesKt.to("deviceStateItems", appFunctionArrayTypeMetadata));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf4;
            List<String> listListOf3 = CollectionsKt.listOf("description");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf3;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata4 = new AppFunctionObjectTypeMetadata(mapMapOf4, listListOf3, "com.google.android.appfunctions.schema.devicestate.v1.PerScreenDeviceStates", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_PERSCREENDEVICESTATES_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata4;
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata3 = new AppFunctionReferenceTypeMetadata("com.google.android.appfunctions.schema.devicestate.v1.PerScreenDeviceStates", false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_PERSCREENDEVICESTATES_REFERENCE_ITEM_TYPE = appFunctionReferenceTypeMetadata3;
            AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata2 = new AppFunctionArrayTypeMetadata(appFunctionReferenceTypeMetadata3, false, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_PERSCREENDEVICESTATES = appFunctionArrayTypeMetadata2;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata9 = new AppFunctionStringTypeMetadata(false, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_DEVICELOCALE = appFunctionStringTypeMetadata9;
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata10 = new AppFunctionStringTypeMetadata(true, "", null);
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP_GLOBALHINTTEXT = appFunctionStringTypeMetadata10;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf5 = MapsKt.mapOf(TuplesKt.to("perScreenDeviceStates", appFunctionArrayTypeMetadata2), TuplesKt.to("deviceLocale", appFunctionStringTypeMetadata9), TuplesKt.to("globalHintText", appFunctionStringTypeMetadata10));
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE_PROPERTIES_MAP = mapMapOf5;
            List<String> listListOf4 = CollectionsKt.listOf("deviceLocale");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE_REQUIRED_PROPERTIES_LIST = listListOf4;
            AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata5 = new AppFunctionObjectTypeMetadata(mapMapOf5, listListOf4, "com.google.android.appfunctions.schema.devicestate.v1.DeviceStateResponse", true, "");
            this.COM_GOOGLE_ANDROID_APPFUNCTIONS_SCHEMA_DEVICESTATE_V1_DEVICESTATERESPONSE_OBJECT_DATA_TYPE = appFunctionObjectTypeMetadata5;
            Map<String, AppFunctionDataTypeMetadata> mapMapOf6 = MapsKt.mapOf(TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.GetUncategorizedDeviceStateParams", appFunctionObjectTypeMetadata), TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.LocalizedString", appFunctionObjectTypeMetadata2), TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.DeviceStateItem", appFunctionObjectTypeMetadata3), TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.PerScreenDeviceStates", appFunctionObjectTypeMetadata4), TuplesKt.to("com.google.android.appfunctions.schema.devicestate.v1.DeviceStateResponse", appFunctionObjectTypeMetadata5));
            this.componentsMetadataDataTypesMap = mapMapOf6;
            this.componentsMetadata = new AppFunctionComponentsMetadata(mapMapOf6);
        }

        @Override // androidx.appfunctions.internal.AppFunctionInventory
        @NotNull
        public AppFunctionComponentsMetadata getComponentsMetadata() {
            return this.componentsMetadata;
        }

        @Override // androidx.appfunctions.internal.AppFunctionInventory
        @NotNull
        public Map<String, CompileTimeAppFunctionMetadata> getFunctionIdToMetadataMap() {
            return this.functionIdToMetadataMap;
        }
    }});

    @Override // androidx.appfunctions.internal.AggregatedAppFunctionInventory
    @NotNull
    public List<AppFunctionInventory> getInventories() {
        return this.inventories;
    }
}
