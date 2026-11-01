package com.xiaomi.keychainsdk.request;

/* JADX INFO: loaded from: classes2.dex */
public class RequestAPI {
    public final String requestUrl;
    public final String resultUrl;
    public static RequestAPI GET_VERSION = new RequestAPI("https://keybag.id.mi.com/api/getVersion", "");
    public static RequestAPI CREATE = new RequestAPI("https://keybag.id.mi.com/api/createMasterKey", "https://keybag.id.mi.com/api/createMasterKeyResult");
    public static RequestAPI RESTORE = new RequestAPI("https://keybag.id.mi.com/api/restoreMasterKey", "https://keybag.id.mi.com/api/restoreMasterKeyResult");
    public static RequestAPI RESET = new RequestAPI("https://keybag.id.mi.com/api/resetMasterKey", "https://keybag.id.mi.com/api/resetMasterKeyResult");
    public static RequestAPI UPDATE = new RequestAPI("https://keybag.id.mi.com/api/changePasscode", "https://keybag.id.mi.com/api/changePasscodeResult");

    public RequestAPI(String str, String str2) {
        this.requestUrl = str;
        this.resultUrl = str2;
    }
}
