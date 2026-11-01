package com.miui.keyguard.editor.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;
import androidx.core.content.ContextCompat;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: GestureSlideUpHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class GestureSlideUpHelper extends BroadcastReceiver {
    public static final Companion Companion = new Companion(null);
    public static ArrayDeque gestureSlideUpCallbackStack;
    public static boolean isReceiverReady;

    /* JADX INFO: compiled from: GestureSlideUpHelper.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final void addCallback(GestureSlideUpCallback gestureSlideUpCallback) {
        ArrayDeque arrayDeque = gestureSlideUpCallbackStack;
        Log.d("GestureSlideUpHelper", "addCallback: callback=" + gestureSlideUpCallback + " size=" + (arrayDeque != null ? Integer.valueOf(arrayDeque.size()) : null));
        ArrayDeque arrayDeque2 = gestureSlideUpCallbackStack;
        if (arrayDeque2 != null) {
            arrayDeque2.addFirst(new WeakReference(gestureSlideUpCallback));
        }
    }

    public final boolean hasAvailableCallback() {
        WeakReference weakReference;
        while (true) {
            ArrayDeque arrayDeque = gestureSlideUpCallbackStack;
            if (arrayDeque == null || !(!arrayDeque.isEmpty())) {
                break;
            }
            ArrayDeque arrayDeque2 = gestureSlideUpCallbackStack;
            GestureSlideUpCallback gestureSlideUpCallback = (arrayDeque2 == null || (weakReference = (WeakReference) arrayDeque2.firstOrNull()) == null) ? null : (GestureSlideUpCallback) weakReference.get();
            if (gestureSlideUpCallback != null && gestureSlideUpCallback.isAvailable()) {
                return true;
            }
            ArrayDeque arrayDeque3 = gestureSlideUpCallbackStack;
            if (arrayDeque3 != null) {
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x002e, code lost:
    
        r1 = null;
     */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onReceive(android.content.Context r2, android.content.Intent r3) {
        /*
            r1 = this;
            if (r3 != 0) goto L4
            goto L82
        L4:
            java.lang.String r1 = r3.getAction()
            java.lang.String r2 = "com.miui.home.fullScreenGesture.actionUp"
            boolean r1 = android.text.TextUtils.equals(r2, r1)
            if (r1 != 0) goto L12
            goto L82
        L12:
            java.lang.String r1 = "actionUpSpeedAndDirection"
            r2 = -1
            int r1 = r3.getIntExtra(r1, r2)
            r2 = 5
            if (r1 == r2) goto L21
            r2 = 10
            if (r1 == r2) goto L21
            goto L82
        L21:
            kotlin.collections.ArrayDeque r1 = com.miui.keyguard.editor.utils.GestureSlideUpHelper.gestureSlideUpCallbackStack
            r2 = 0
            if (r1 == 0) goto L2d
            java.lang.Object r1 = r1.firstOrNull()
            java.lang.ref.WeakReference r1 = (java.lang.ref.WeakReference) r1
            goto L2e
        L2d:
            r1 = r2
        L2e:
            kotlin.collections.ArrayDeque r3 = com.miui.keyguard.editor.utils.GestureSlideUpHelper.gestureSlideUpCallbackStack
            if (r3 == 0) goto L5f
            boolean r3 = r3.isEmpty()
            r0 = 1
            r3 = r3 ^ r0
            if (r3 != r0) goto L5f
            if (r1 == 0) goto L5f
            java.lang.Object r3 = r1.get()
            com.miui.keyguard.editor.utils.GestureSlideUpCallback r3 = (com.miui.keyguard.editor.utils.GestureSlideUpCallback) r3
            if (r3 == 0) goto L5f
            boolean r3 = r3.isAvailable()
            if (r3 != 0) goto L5f
            kotlin.collections.ArrayDeque r1 = com.miui.keyguard.editor.utils.GestureSlideUpHelper.gestureSlideUpCallbackStack
            if (r1 == 0) goto L54
            java.lang.Object r1 = r1.removeFirstOrNull()
            java.lang.ref.WeakReference r1 = (java.lang.ref.WeakReference) r1
        L54:
            kotlin.collections.ArrayDeque r1 = com.miui.keyguard.editor.utils.GestureSlideUpHelper.gestureSlideUpCallbackStack
            if (r1 == 0) goto L2d
            java.lang.Object r1 = r1.firstOrNull()
            java.lang.ref.WeakReference r1 = (java.lang.ref.WeakReference) r1
            goto L2e
        L5f:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "peekCallback="
            r2.append(r3)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "GestureSlideUpHelper"
            android.util.Log.d(r3, r2)
            if (r1 == 0) goto L82
            java.lang.Object r1 = r1.get()
            com.miui.keyguard.editor.utils.GestureSlideUpCallback r1 = (com.miui.keyguard.editor.utils.GestureSlideUpCallback) r1
            if (r1 == 0) goto L82
            r1.onGestureSlideUp()
        L82:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.utils.GestureSlideUpHelper.onReceive(android.content.Context, android.content.Intent):void");
    }

    public final void register(Context context, GestureSlideUpCallback callback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (!isReceiverReady) {
            ArrayDeque arrayDeque = gestureSlideUpCallbackStack;
            if (arrayDeque != null) {
                arrayDeque.clear();
            }
            gestureSlideUpCallbackStack = new ArrayDeque();
            try {
                ContextCompat.registerReceiver(context, this, new IntentFilter("com.miui.home.fullScreenGesture.actionUp"), 2);
                isReceiverReady = true;
                Unit unit = Unit.INSTANCE;
            } catch (Exception e) {
                Log.e("GestureSlideUpHelper", "register: " + e);
            }
        }
        addCallback(callback);
    }

    public final void unregister(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (!isReceiverReady || hasAvailableCallback()) {
            return;
        }
        try {
            context.unregisterReceiver(this);
        } catch (Exception e) {
            Log.e("GestureSlideUpHelper", "unregister: " + e);
        } finally {
            isReceiverReady = false;
            ArrayDeque arrayDeque = gestureSlideUpCallbackStack;
            if (arrayDeque != null) {
                arrayDeque.clear();
            }
            gestureSlideUpCallbackStack = null;
        }
    }
}
