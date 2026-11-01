package com.miui.keyguard.editor.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
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
/* JADX INFO: compiled from: AccelerometerRotationObserver.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AccelerometerRotationObserver extends ContentObserver {
    public ContentResolver contentResolver;
    public final WeakReference contextWeak;
    public final Function1 onAccelerometerRotationChanged;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AccelerometerRotationObserver(WeakReference contextWeak, Function1 onAccelerometerRotationChanged) {
        super(new Handler(Looper.getMainLooper()));
        Intrinsics.checkNotNullParameter(contextWeak, "contextWeak");
        Intrinsics.checkNotNullParameter(onAccelerometerRotationChanged, "onAccelerometerRotationChanged");
        this.contextWeak = contextWeak;
        this.onAccelerometerRotationChanged = onAccelerometerRotationChanged;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        super.onChange(z);
        Context context = (Context) this.contextWeak.get();
        if (context != null) {
            this.onAccelerometerRotationChanged.invoke(Boolean.valueOf(DeviceUtil.INSTANCE.isScreenOrientationLocked(context)));
        }
    }

    public final void register() {
        if (this.contentResolver != null) {
            return;
        }
        Context context = (Context) this.contextWeak.get();
        ContentResolver contentResolver = context != null ? context.getContentResolver() : null;
        this.contentResolver = contentResolver;
        if (contentResolver != null) {
            contentResolver.registerContentObserver(Settings.System.getUriFor("accelerometer_rotation"), false, this);
        }
    }

    public final void unregister() {
        ContentResolver contentResolver = this.contentResolver;
        if (contentResolver == null) {
            return;
        }
        if (contentResolver != null) {
            contentResolver.unregisterContentObserver(this);
        }
        this.contentResolver = null;
    }
}
