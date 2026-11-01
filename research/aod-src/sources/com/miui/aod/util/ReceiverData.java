package com.miui.aod.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: BroadcastDispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ReceiverData {
    public final Context context;
    public final IntentFilter filter;
    public final String permission;
    public final BroadcastReceiver receiver;

    public ReceiverData(Context context, BroadcastReceiver receiver, IntentFilter intentFilter, String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        this.context = context;
        this.receiver = receiver;
        this.filter = intentFilter;
        this.permission = str;
    }

    public /* synthetic */ ReceiverData(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, broadcastReceiver, (i & 4) != 0 ? null : intentFilter, (i & 8) != 0 ? null : str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ReceiverData)) {
            return false;
        }
        ReceiverData receiverData = (ReceiverData) obj;
        return Intrinsics.areEqual(this.context, receiverData.context) && Intrinsics.areEqual(this.receiver, receiverData.receiver) && Intrinsics.areEqual(this.filter, receiverData.filter) && Intrinsics.areEqual(this.permission, receiverData.permission);
    }

    public final Context getContext() {
        return this.context;
    }

    public final IntentFilter getFilter() {
        return this.filter;
    }

    public final BroadcastReceiver getReceiver() {
        return this.receiver;
    }

    public int hashCode() {
        int iHashCode = ((this.context.hashCode() * 31) + this.receiver.hashCode()) * 31;
        IntentFilter intentFilter = this.filter;
        int iHashCode2 = (iHashCode + (intentFilter == null ? 0 : intentFilter.hashCode())) * 31;
        String str = this.permission;
        return iHashCode2 + (str != null ? str.hashCode() : 0);
    }

    public String toString() {
        return "ReceiverData(context=" + this.context + ", receiver=" + this.receiver + ", filter=" + this.filter + ", permission=" + this.permission + ")";
    }
}
