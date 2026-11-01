package com.miui.aod.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.miui.aod.Utils;
import com.miui.aod.utils.UserHandleUtils;
import kotlin.Metadata;
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
public class BroadcastDispatcher {
    public final Looper broadcastLooper;
    public final BroadcastDispatcher$handler$1 handler;
    public final ArraySet receivers = new ArraySet();
    public final Handler workerHandler;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.miui.aod.util.BroadcastDispatcher$handler$1] */
    public BroadcastDispatcher() {
        final Looper looperProvideBroadcastRunningLooper = Utils.provideBroadcastRunningLooper();
        this.broadcastLooper = looperProvideBroadcastRunningLooper;
        this.workerHandler = new Handler(looperProvideBroadcastRunningLooper);
        this.handler = new Handler(looperProvideBroadcastRunningLooper) { // from class: com.miui.aod.util.BroadcastDispatcher$handler$1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                int i = msg.what;
                if (i == 0) {
                    Object obj = msg.obj;
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.miui.aod.util.ReceiverData");
                    ReceiverData receiverData = (ReceiverData) obj;
                    BroadcastReceiver receiver = receiverData.getReceiver();
                    int i2 = msg.arg1;
                    Log.d("BroadcastDispatcher", "MSG_ADD_RECEIVER: " + receiver);
                    if (this.this$0.receivers.contains(receiver)) {
                        return;
                    }
                    this.this$0.receivers.add(receiver);
                    com.miui.aod.utils.ContextUtils.registerReceiverAsUser(receiverData.getContext(), receiver, UserHandleUtils.getUserHandle(UserHandleUtils.USER_CURRENT), receiverData.getFilter(), null, this.this$0.workerHandler, i2);
                    return;
                }
                if (i != 1) {
                    super.handleMessage(msg);
                    return;
                }
                Object obj2 = msg.obj;
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type com.miui.aod.util.ReceiverData");
                ReceiverData receiverData2 = (ReceiverData) obj2;
                BroadcastReceiver receiver2 = receiverData2.getReceiver();
                Log.d("BroadcastDispatcher", "MSG_REMOVE_RECEIVER: " + receiver2);
                if (this.this$0.receivers.contains(receiver2)) {
                    try {
                        receiverData2.getContext().unregisterReceiver(receiver2);
                    } catch (IllegalArgumentException e) {
                        Log.e("BroadcastDispatcher", "Trying to unregister unregistered receiver", new IllegalStateException(e));
                    }
                    this.this$0.receivers.remove(receiver2);
                }
            }
        };
    }

    public static /* synthetic */ void registerReceiver$default(BroadcastDispatcher broadcastDispatcher, Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, int i, String str, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: registerReceiver");
        }
        if ((i2 & 8) != 0) {
            i = 2;
        }
        int i3 = i;
        if ((i2 & 16) != 0) {
            str = null;
        }
        broadcastDispatcher.registerReceiver(context, broadcastReceiver, intentFilter, i3, str);
    }

    public final void checkFilter(IntentFilter intentFilter) {
        StringBuilder sb = new StringBuilder();
        if (intentFilter.countActions() == 0) {
            sb.append("Filter must contain at least one action. ");
        }
        if (intentFilter.countDataAuthorities() != 0) {
            sb.append("Filter cannot contain DataAuthorities. ");
        }
        if (intentFilter.countDataPaths() != 0) {
            sb.append("Filter cannot contain DataPaths. ");
        }
        if (intentFilter.countDataSchemes() != 0) {
            sb.append("Filter cannot contain DataSchemes. ");
        }
        if (intentFilter.countDataTypes() != 0) {
            sb.append("Filter cannot contain DataTypes. ");
        }
        if (intentFilter.getPriority() != 0) {
            sb.append("Filter cannot modify priority. ");
        }
        if (!TextUtils.isEmpty(sb)) {
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public final void registerReceiver(Context context, BroadcastReceiver receiver, IntentFilter filter) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(filter, "filter");
        registerReceiver$default(this, context, receiver, filter, 0, null, 24, null);
    }

    public void registerReceiver(Context context, BroadcastReceiver receiver, IntentFilter filter, int i, String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(filter, "filter");
        checkFilter(filter);
        obtainMessage(0, i, 0, new ReceiverData(context, receiver, filter, str)).sendToTarget();
    }

    public void unregisterReceiver(Context context, BroadcastReceiver receiver) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        obtainMessage(1, new ReceiverData(context, receiver, null, null, 12, null)).sendToTarget();
    }
}
