package com.xiaomi.taiyi.sdk.common;

import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AIKeys {
    public static final AIKeys INSTANCE = new AIKeys();
    public static final String KEY_AI_ABILITY = "ai_ability";
    public static final String KEY_AI_ABILITY_API = "ai_api";
    public static final String KEY_AI_ABILITY_API_ARRAY = "ai_api_array";
    public static final String KEY_AI_ABILITY_API_LEVEL = "ai_api_level";
    public static final String KEY_AI_ABILITY_NAME = "ai_name";
    public static final String KEY_AI_ABILITY_VERSION = "ai_version";
    public static final String KEY_AI_ALLOW_INTERNAL = "allow_internal";
    public static final String KEY_AI_CALLBACK = "ai_callback";
    public static final String KEY_AI_CLIENT_ID = "ai_client_id";
    public static final String KEY_AI_CONFIG_JSON = "ai_config_json";
    public static final String KEY_AI_CONFIG_JSON_LIST = "ai_config_json_list";
    public static final String KEY_AI_DOWNLOAD_SIZE = "download_size";
    public static final String KEY_AI_DOWNLOAD_TYPE = "download_type";
    public static final String KEY_AI_LOCAL_SIZE = "local_size";
    public static final String KEY_AI_METHOD = "ai_method";
    public static final String KEY_AI_RESOURCE_JSON = "ai_resource_json";
    public static final String KEY_AI_RESOURCE_JSON_LIST = "ai_resource_json_list";
    public static final String KEY_AI_SERVICE_ACTION = "service_action";
    public static final String KEY_AI_SERVICE_ID = "service_id";
    public static final String KEY_AI_SERVICE_NAME = "service_name";
    public static final String KEY_AI_SERVICE_PACKAGE = "service_package";
    public static final String KEY_AI_TOTAL_SIZE = "total_size";
    public static final String KEY_APP_CODE = "ai_app_code";
    public static final String KEY_APP_SIGNATURE = "ai_app_signature";
    public static final String KEY_APP_VERSION_CODE = "app_version_code";
    public static final String KEY_APP_VERSION_NAME = "app_version_name";
    public static final String KEY_ERROR_CODE = "error_code";
    public static final String KEY_ERROR_MSG = "error_msg";
    public static final String KEY_FORMAT_TIME = "format_time";
    public static final String KEY_JSON_RESULT = "json_result";
    public static final String KEY_JSON_SOURCE = "json_source";
    public static final String KEY_PACKAGE_NAME = "package_name";
    public static final String KEY_PID = "pid";
    public static final String KEY_PROCESS_NAME = "process_name";
    public static final String KEY_PRODUCT_CODE = "ai_product_code";
    public static final String KEY_PROVIDER_SUB_METHOD = "sub_method";
    public static final String KEY_REQUEST = "request";
    public static final String KEY_REQUEST_ID = "request_id";
    public static final String KEY_RESOURCE_ID = "ai_resource_id";
    public static final String KEY_RESOURCE_UNWRAPPED = "resource_unwrapped";
    public static final String KEY_RESOURCE_VERSION = "ai_resource_version";
    public static final String KEY_RESPONSE = "response";
    public static final String KEY_RUN_AFFINITY = "run_affinity";
    public static final String KEY_SDK_CODE = "ai_sdk_code";
    public static final String KEY_SDK_NAME = "ai_sdk_name";
    public static final String KEY_SHARE_MEMORY_ENABLE = "share_memory_enable";
    public static final String KEY_SHARE_MEMORY_POOL = "share_memory_pool";
    public static final String KEY_SPEC_CODE = "spec_error_code";
    public static final String KEY_SPEC_INFO = "spec_info";
    public static final String KEY_SYS_BOOT_MILLS = "sys_boot_mills";
    public static final String KEY_SYS_BOOT_NANOS = "sys_boot_nanos";
    public static final String KEY_TID = "tid";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_TRACK_NUM = "track_num";
    public static final String KEY_TRACK_PERIOD = "track_period";
    public static final String KEY_TRACK_TYPE = "track_type";
    public static final String KEY_UID = "uid";
    public static final String PARAM_BLACK_OPERATORS = "blackOperators";
    public static final String PARAM_MAX_TEMPERATURE = "maxTemperature";
    public static final String PARAM_MIN_ANDROID_VERSION = "minAndroidVersion";
    public static final String PARAM_MIN_AVAIL_MEMORY = "minAvailMemory";
    public static final String PARAM_MIN_BATTERY = "minBattery";
    public static final String PARAM_MIN_OS_VERSION = "minOsVersion";
    public static final String PARAM_MIN_RAM = "minRam";
    public static final String PARAM_MIN_ROM = "minRom";
    public static final String PARAM_WHITE_PHONE_MODELS = "whiteModels";
    public static final String PARAM_WHITE_REGIONS = "whiteRegions";
    public static final String PARAM_WHITE_SOCS = "whiteSocs";

    private AIKeys() {
    }
}
