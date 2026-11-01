package com.xiaomi.taiyi.sdk.base.data;

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
public final class ErrorCode {
    public static final int ERROR_ABILITY_BIND_FAILED = 10505;
    public static final int ERROR_ABILITY_NOT_FOUND = 10507;
    public static final int ERROR_ABILITY_NOT_INIT = 10500;
    public static final int ERROR_ABILITY_UNBIND_FAILED = 10506;
    public static final int ERROR_AI_INFO_EMPTY = 10401;
    public static final int ERROR_ALREADY_UPDATED = 10723;
    public static final int ERROR_AUTHORIZE_ERROR = 10110;
    public static final int ERROR_AUTHORIZE_FAILED = 10109;
    public static final int ERROR_AWAIT_EXCEPTION = 10301;
    public static final int ERROR_AWAIT_TIMEOUT = 10302;
    public static final int ERROR_CERT_PATH_VALIDATOR = 11108;
    public static final int ERROR_CONFIG_NOT_UPDATE = 10602;
    public static final int ERROR_CONNECT_FAILED = 11102;
    public static final int ERROR_CONNECT_TIMEOUT = 11103;
    public static final int ERROR_CONTEXT_INVALID = 10108;
    public static final int ERROR_DETAIL_EMPTY = 10425;
    public static final int ERROR_DOWNLOAD_BLOCKED = 10714;
    public static final int ERROR_DOWNLOAD_CANCELED_BY_USER = 10716;
    public static final int ERROR_DOWNLOAD_CANNOT_RESUME = 10712;
    public static final int ERROR_DOWNLOAD_DEVICE_NOT_FOUND = 10711;
    public static final int ERROR_DOWNLOAD_FILE_ALREADY_EXISTS = 10713;
    public static final int ERROR_DOWNLOAD_FILE_ERROR = 10706;
    public static final int ERROR_DOWNLOAD_FILE_PARAM_NOT_MATCH = 10722;
    public static final int ERROR_DOWNLOAD_FILE_REMOVED = 10719;
    public static final int ERROR_DOWNLOAD_HASH_NOT_MATCH = 10718;
    public static final int ERROR_DOWNLOAD_HTTP_DATA_ERROR = 10708;
    public static final int ERROR_DOWNLOAD_INSUFFICIENT_SPACE = 10710;
    public static final int ERROR_DOWNLOAD_PAUSED_BY_USER = 10715;
    public static final int ERROR_DOWNLOAD_PAUSED_QUEUED_FOR_WIFI = 10702;
    public static final int ERROR_DOWNLOAD_PAUSED_UNKNOWN = 10703;
    public static final int ERROR_DOWNLOAD_PAUSED_WAITING_FOR_NETWORK = 10701;
    public static final int ERROR_DOWNLOAD_PAUSED_WAITING_TO_RETRY = 10700;
    public static final int ERROR_DOWNLOAD_RESUME_FAILED = 10721;
    public static final int ERROR_DOWNLOAD_SIZE_NOT_MATCH = 10717;
    public static final int ERROR_DOWNLOAD_TOO_MANY_REDIRECTS = 10709;
    public static final int ERROR_DOWNLOAD_UNHANDLED_HTTP_CODE = 10707;
    public static final int ERROR_DOWNLOAD_UNKNOWN = 10705;
    public static final int ERROR_DOWNLOAD_URI_INVALID = 10720;
    public static final int ERROR_DOWNLOAD_URL_REQUEST = 10704;
    public static final int ERROR_EXECUTE_EXCEPTION = 10502;
    public static final int ERROR_EXECUTE_REMOTE_EXCEPTION = 10501;
    public static final int ERROR_FILE_COPY_FAILED_BY_CHANNEL = 11002;
    public static final int ERROR_FILE_COPY_FAILED_BY_IO = 11003;
    public static final int ERROR_FILE_COPY_FAILED_UNKNOWN = 11001;
    public static final int ERROR_FILE_COPY_HASH_NOT_MATCH = 11019;
    public static final int ERROR_FILE_COPY_NOT_FOUND = 11000;
    public static final int ERROR_FILE_COPY_SPACE_NOT_ENOUGH = 11004;
    public static final int ERROR_FILE_UNZIP_FAILED_BY_IO = 11008;
    public static final int ERROR_FILE_UNZIP_FAILED_BY_ZIP = 11007;
    public static final int ERROR_FILE_UNZIP_FAILED_UNKNOWN = 11005;
    public static final int ERROR_FILE_UNZIP_HASH_NOT_MATCH = 11017;
    public static final int ERROR_FILE_UNZIP_SIZE_NOT_MATCH = 11016;
    public static final int ERROR_FILE_UNZIP_SPACE_NOT_ENOUGH = 11006;
    public static final int ERROR_FILTER_EMPTY = 10423;
    public static final int ERROR_FILTER_INVALID = 10424;
    public static final int ERROR_FRAME_CAST_FAILED = 10416;
    public static final int ERROR_FRAME_DATA_EMPTY = 10414;
    public static final int ERROR_FRAME_READ_BUFFER_FAILED = 10422;
    public static final int ERROR_FRAME_READ_BUFFER_INDEX_OUT = 10421;
    public static final int ERROR_FRAME_READ_BUFFER_UNDER_FLOW = 10420;
    public static final int ERROR_FRAME_REPEAT = 10413;
    public static final int ERROR_FRAME_SIZE_EMPTY = 10406;
    public static final int ERROR_FRAME_WRITE_BUFFER_FAILED = 10417;
    public static final int ERROR_FRAME_WRITE_BUFFER_OVER_FLOW = 10418;
    public static final int ERROR_FRAME_WRITE_FAILED = 10410;
    public static final int ERROR_FRAME_WRITE_READ_ONLY_BUFFER = 10419;
    public static final int ERROR_INTERNET_NOT_ALLOW_BY_USER = 10900;
    public static final int ERROR_INTERNET_NOT_AVAILABLE = 11100;
    public static final int ERROR_INTERNET_PROTOCOL = 11107;
    public static final int ERROR_NO_ADDRESS = 11110;
    public static final int ERROR_NO_LOCAL_LOADER = 10303;
    public static final int ERROR_PARAM_API_EMPTY = 10400;
    public static final int ERROR_PARAM_CONFIG_NOT_FOUND = 10402;
    public static final int ERROR_PLUGIN_APK_CANNOT_READ = 10001;
    public static final int ERROR_PLUGIN_APK_CAN_WRITE = 10002;
    public static final int ERROR_PLUGIN_APK_NOT_EXIST = 10000;
    public static final int ERROR_PLUGIN_APK_NOT_FOUND = 10010;
    public static final int ERROR_PLUGIN_ATTACH_FAILED = 10007;
    public static final int ERROR_PLUGIN_CLASS_CAST_FAILED = 10006;
    public static final int ERROR_PLUGIN_CLASS_NOT_FOUND = 10005;
    public static final int ERROR_PLUGIN_CREATE_CONTEXT_FAILED = 10004;
    public static final int ERROR_PLUGIN_CREATE_FAILED_UNKNOWN = 10009;
    public static final int ERROR_PLUGIN_CREATE_LOADER_FAILED = 10003;
    public static final int ERROR_PLUGIN_INFO_EMPTY = 10008;
    public static final int ERROR_PLUGIN_ON_CREATE_FAILED = 10011;
    public static final int ERROR_POST_EXCEPTION = 10504;
    public static final int ERROR_POST_REMOTE_EXCEPTION = 10503;
    public static final int ERROR_PROCESS_BIND_CRASH = 10801;
    public static final int ERROR_PROCESS_CRASH = 10800;
    public static final int ERROR_PROCESS_FAILED = 10803;
    public static final int ERROR_PROCESS_INIT_FAILED = 10804;
    public static final int ERROR_PROCESS_PARAM_INVALID = 10805;
    public static final int ERROR_PROCESS_RESOURCE_LOST = 10806;
    public static final int ERROR_PROCESS_RESULT_INVALID = 10807;
    public static final int ERROR_PROCESS_UNBIND_CRASH = 10802;
    public static final int ERROR_PROVIDER_ACQUIRE_ERROR = 10203;
    public static final int ERROR_PROVIDER_AUTHORITY_INVALID = 10204;
    public static final int ERROR_PROVIDER_CALL_DEAD = 10207;
    public static final int ERROR_PROVIDER_CALL_EMPTY = 10205;
    public static final int ERROR_PROVIDER_CALL_ERROR = 10208;
    public static final int ERROR_PROVIDER_CALL_NO_METHOD = 10209;
    public static final int ERROR_PROVIDER_CALL_NO_REQUEST = 10211;
    public static final int ERROR_PROVIDER_CALL_NO_RESPONSE = 10206;
    public static final int ERROR_PROVIDER_CALL_NO_SUB_METHOD = 10210;
    public static final int ERROR_PROVIDER_DEAD = 10201;
    public static final int ERROR_PROVIDER_EMPTY = 10202;
    public static final int ERROR_PROVIDER_NOT_FOUND = 10200;
    public static final int ERROR_PROVIDER_PERMISSION_DENIED = 10213;
    public static final int ERROR_REQUEST_CTA_FAILED = 10212;
    public static final int ERROR_REQUEST_IO_EXCEPTION = 11111;
    public static final int ERROR_RESOURCE_CONFIG_LIBRARY_NOT_FOUND = 10404;
    public static final int ERROR_RESOURCE_CONFIG_MODEL_NOT_FOUND = 10405;
    public static final int ERROR_RESOURCE_CONFIG_PLUGIN_NOT_FOUND = 10403;
    public static final int ERROR_RESOURCE_PREPARE_FAILED = 11018;
    public static final int ERROR_RESPONSE_INVALID = 11112;
    public static final int ERROR_RESPONSE_PARSE_ERROR = 11113;
    public static final int ERROR_RESPONSE_SPECIAL_CODE = 20000000;
    public static final int ERROR_RESPONSE_STANDARD_CODE = 10000000;
    public static final int ERROR_SERVICE_ACCESS_DENIED = 10105;
    public static final int ERROR_SERVICE_APP_NOT_BIND = 10106;
    public static final int ERROR_SERVICE_BINDER_DIED = 10104;
    public static final int ERROR_SERVICE_BIND_FAILED = 10107;
    public static final int ERROR_SERVICE_DISCONNECT = 10103;
    public static final int ERROR_SERVICE_INCOMPATIBLE = 10102;
    public static final int ERROR_SERVICE_INVALID = 10101;
    public static final int ERROR_SERVICE_NOT_SUPPORT = 10100;
    public static final int ERROR_SHARE_FILE_FAILED_BY_IO = 11013;
    public static final int ERROR_SHARE_FILE_FAILED_BY_RUNTIME = 11014;
    public static final int ERROR_SHARE_FILE_FAILED_UNKNOWN = 11015;
    public static final int ERROR_SHARE_FILE_NOT_FOUND = 11011;
    public static final int ERROR_SHARE_FILE_OPERATION_NOT_SUPPORT = 11012;
    public static final int ERROR_SHARE_MEMORY_CLOSED = 10411;
    public static final int ERROR_SHARE_MEMORY_CREATE_FAILED = 10412;
    public static final int ERROR_SHARE_MEMORY_FD_INVALID = 10408;
    public static final int ERROR_SHARE_MEMORY_MAP_FAILED = 10415;
    public static final int ERROR_SHARE_MEMORY_NOT_FOUND = 10407;
    public static final int ERROR_SHARE_MEMORY_REQUEST_ALLOCATION_FAIL = 10409;
    public static final int ERROR_SOCKET_TIMEOUT = 11104;
    public static final int ERROR_SSL_HANDSHAKE_FAIL = 11106;
    public static final int ERROR_UNAVAILABLE_BY_NO_DOWNLOAD = 10601;
    public static final int ERROR_UNEXPECTED_EOF = 11109;
    public static final int ERROR_UNINSTALL_DELETE_FAILED = 11010;
    public static final int ERROR_UNINSTALL_DELETE_NOT_SUPPORT = 11009;
    public static final int ERROR_UNKNOWN_HOST = 11105;
    public static final int ERROR_UNSUPPORTED_BY_DISABLED = 10600;
    public static final int ERROR_WIFI_NOT_AVAILABLE = 11101;
    public static final int ERROR_WLAN_AUTO_DOWNLOAD_NOT_ALLOW_BY_USER = 10901;
    public static final int ERROR_WORK_ON_MAIN_THREAD = 10300;
    public static final ErrorCode INSTANCE = new ErrorCode();
    public static final int NONE = 0;
    public static final int UNKNOWN = -1;

    private ErrorCode() {
    }
}
