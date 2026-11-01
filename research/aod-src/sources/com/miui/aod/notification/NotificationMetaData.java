package com.miui.aod.notification;

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
/* JADX INFO: compiled from: NotificationMetaData.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class NotificationMetaData {
    public final int focusNotificationSize;
    public final int maxSize;
    public final int notificationSize;

    public NotificationMetaData(int i, int i2, int i3) {
        this.notificationSize = i;
        this.focusNotificationSize = i2;
        this.maxSize = i3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationMetaData)) {
            return false;
        }
        NotificationMetaData notificationMetaData = (NotificationMetaData) obj;
        return this.notificationSize == notificationMetaData.notificationSize && this.focusNotificationSize == notificationMetaData.focusNotificationSize && this.maxSize == notificationMetaData.maxSize;
    }

    public final int getFocusNotificationSize() {
        return this.focusNotificationSize;
    }

    public final int getMaxSize() {
        return this.maxSize;
    }

    public final int getNotificationSize() {
        return this.notificationSize;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.notificationSize) * 31) + Integer.hashCode(this.focusNotificationSize)) * 31) + Integer.hashCode(this.maxSize);
    }

    public String toString() {
        return "NotificationMetaData(notificationSize=" + this.notificationSize + ", focusNotificationSize=" + this.focusNotificationSize + ", maxSize=" + this.maxSize + ")";
    }
}
