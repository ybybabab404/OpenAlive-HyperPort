package com.miui.aod.notification;

import android.view.View;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class FocusNotificationData extends NotificationController.NotificationData {
    public final View mView;

    public FocusNotificationData(String str, View view) {
        super(str, "");
        this.mView = view;
    }

    @Override // com.miui.aod.notification.NotificationController.NotificationData
    public boolean equals(Object obj) {
        if (!(obj instanceof NotificationController.NotificationData)) {
            return false;
        }
        NotificationController.NotificationData notificationData = (NotificationController.NotificationData) obj;
        String str = this.mPackageName;
        if (str == null && notificationData.mPackageName != null) {
            return false;
        }
        if (str != null && !str.equals(notificationData.mPackageName)) {
            return false;
        }
        String str2 = this.mKey;
        if (str2 != null || notificationData.mKey == null) {
            return str2 == null || str2.equals(notificationData.mKey);
        }
        return false;
    }

    @Override // com.miui.aod.notification.NotificationController.NotificationData
    public int hashCode() {
        return Objects.hash(this.mPackageName, this.mKey);
    }

    @Override // com.miui.aod.notification.NotificationController.NotificationData
    public String toString() {
        return super.toString() + "@FocusNotificationData";
    }
}
