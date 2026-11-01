package micloud.compat.v18.finddevice;

/* JADX INFO: loaded from: classes2.dex */
public class FindDeviceInfoCompat {
    public final String displayId;
    public final String email;
    public final String fid;
    public final String findToken;
    public final boolean isLocked;
    public final boolean isOpen;
    public final String phone;
    public final String sessionUserId;

    public FindDeviceInfoCompat(boolean z, boolean z2, String str, String str2, String str3, String str4, String str5, String str6) {
        this.isOpen = z;
        this.isLocked = z2;
        this.sessionUserId = str;
        this.displayId = str2;
        this.fid = str3;
        this.email = str4;
        this.phone = str5;
        this.findToken = str6;
    }
}
