package com.miui.keyguard.shortcuts.list;

import android.database.ContentObserver;
import android.os.Handler;
import androidx.lifecycle.LifecycleOwnerKt;
import kotlin.Metadata;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Job;
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
/* JADX INFO: compiled from: LockScreenShortcutsSettingsActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1 extends ContentObserver {
    public final /* synthetic */ ProducerScope $$this$callbackFlow;
    public final Mutex mutex;
    public Job prevJob;
    public final /* synthetic */ LockScreenShortcutsSettingsActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1(LockScreenShortcutsSettingsActivity lockScreenShortcutsSettingsActivity, ProducerScope producerScope, Handler handler) {
        super(handler);
        this.this$0 = lockScreenShortcutsSettingsActivity;
        this.$$this$callbackFlow = producerScope;
        this.mutex = MutexKt.Mutex$default(false, 1, null);
    }

    public final Mutex getMutex() {
        return this.mutex;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        super.onChange(z);
        Job job = this.prevJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        this.prevJob = BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this.this$0), null, null, new LockScreenShortcutsSettingsActivity$overlayShortcutsFlow$1$overlayObserver$1$onChange$1(this, this.this$0, this.$$this$callbackFlow, null), 3, null);
    }
}
