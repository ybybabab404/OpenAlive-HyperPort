package miui.sharesdk;

/* JADX INFO: loaded from: classes2.dex */
public enum ShareChannel {
    CHANNEL_FAMILY("FamilyShare"),
    CHANNEL_SUGGESTION("Suggestion"),
    CHANNEL_CONTACT("Contact"),
    CHANNEL_WECHAT("WeChat"),
    CHANNEL_QR_CODE("QrCode"),
    CHANNEL_SEARCH("Mid");

    public final String serverTag;

    ShareChannel(String str) {
        this.serverTag = str;
    }
}
