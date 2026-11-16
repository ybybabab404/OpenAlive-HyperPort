package org.aliveclean.aicr;

/** 与 com.xiaomi.aicr.constant.Constants.STATUS_DEFINE 顺序一致的枚举（取 ordinal 比较）。 */
public final class AicrStatus {
    public static final int OK = 0;
    public static final int DOWNLOADING_FIRST_USE = 1;
    public static final int NEED_UPGRADE = 2;
    public static final int SERVICE_NOT_CONNECTED = 3;
    public static final int SERVICE_ERROR = 4;
    public static final int NOT_FOUND = 5;
    public static final int SET_IMAGE_FAIL = 6;
    public static final int UNSUPPORTED_CAPABILITY = 7;
    public static final int PERMISSION_DENIED = 8;

    public static String name(int status) {
        switch (status) {
            case OK: return "OK";
            case DOWNLOADING_FIRST_USE: return "DOWNLOADING_FIRST_USE";
            case NEED_UPGRADE: return "NEED_UPGRADE";
            case SERVICE_NOT_CONNECTED: return "SERVICE_NOT_CONNECTED";
            case SERVICE_ERROR: return "SERVICE_ERROR";
            case NOT_FOUND: return "NOT_FOUND";
            case SET_IMAGE_FAIL: return "SET_IMAGE_FAIL";
            case UNSUPPORTED_CAPABILITY: return "UNSUPPORTED_CAPABILITY";
            case PERMISSION_DENIED: return "PERMISSION_DENIED";
            default: return "UNKNOWN(" + status + ")";
        }
    }

    private AicrStatus() { }
}
