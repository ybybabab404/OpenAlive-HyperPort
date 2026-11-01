package com.miui.keyguard.editor.data.template;

import android.graphics.Bitmap;
import android.util.Log;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

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
/* JADX INFO: compiled from: BitmapTempStore.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class BitmapTempStore {
    public static final Companion Companion = new Companion(null);
    public static BitmapTempStore INSTANCE;
    public ConcurrentHashMap dataMap = new ConcurrentHashMap();

    /* JADX INFO: compiled from: BitmapTempStore.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final BitmapTempStore getInstance() {
            BitmapTempStore bitmapTempStore;
            BitmapTempStore bitmapTempStore2 = BitmapTempStore.INSTANCE;
            if (bitmapTempStore2 != null) {
                return bitmapTempStore2;
            }
            synchronized (this) {
                bitmapTempStore = BitmapTempStore.INSTANCE;
                if (bitmapTempStore == null) {
                    bitmapTempStore = new BitmapTempStore();
                    BitmapTempStore.INSTANCE = bitmapTempStore;
                }
            }
            return bitmapTempStore;
        }
    }

    public final void clear() {
        this.dataMap.clear();
    }

    public final Bitmap getBitmap(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return (Bitmap) this.dataMap.get(key);
    }

    public final void putBitmap(String key, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (bitmap != null) {
            this.dataMap.put(key, bitmap);
            return;
        }
        Log.w("BitmapTempStore", "key:" + key + " bitmap is null");
    }

    public final void removeBitmap(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.dataMap.remove(key);
    }
}
