package com.miui.keyguard.shortcuts.manager;

import android.database.ContentObserver;
import android.os.Handler;
import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import com.miui.keyguard.shortcuts.utils.DataUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ShortcutRepositoryImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ShortcutRepositoryImpl$observeFlow$1$contentObserver$1 extends ContentObserver {
    public final /* synthetic */ ProducerScope $$this$callbackFlow;
    public final /* synthetic */ boolean $isLeft;
    public volatile ShortcutEntity currentData;
    public final Mutex mutex;
    public final /* synthetic */ ShortcutRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutRepositoryImpl$observeFlow$1$contentObserver$1(ShortcutRepositoryImpl shortcutRepositoryImpl, boolean z, ProducerScope producerScope, Handler handler) {
        super(handler);
        this.this$0 = shortcutRepositoryImpl;
        this.$isLeft = z;
        this.$$this$callbackFlow = producerScope;
        this.currentData = DataUtils.INSTANCE.getUNKNOWN_ENTITY();
        this.mutex = MutexKt.Mutex$default(false, 1, null);
    }

    public final ShortcutEntity getCurrentData() {
        return this.currentData;
    }

    public final Mutex getMutex() {
        return this.mutex;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        super.onChange(z);
        BuildersKt__Builders_commonKt.launch$default(this.this$0.scope, Dispatchers.getIO(), null, new ShortcutRepositoryImpl$observeFlow$1$contentObserver$1$onChange$1(this, this.this$0, this.$isLeft, this.$$this$callbackFlow, null), 2, null);
    }

    public final void setCurrentData(ShortcutEntity shortcutEntity) {
        Intrinsics.checkNotNullParameter(shortcutEntity, "<set-?>");
        this.currentData = shortcutEntity;
    }
}
