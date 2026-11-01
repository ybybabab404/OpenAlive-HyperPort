package com.miui.keyguard.shortcuts.manager;

import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import com.miui.keyguard.shortcuts.utils.DataUtils;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.sync.Mutex;

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
@DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutRepositoryImpl$observeFlow$1$contentObserver$1$onChange$1", f = "ShortcutRepositoryImpl.kt", l = {382}, m = "invokeSuspend")
@SourceDebugExtension
public final class ShortcutRepositoryImpl$observeFlow$1$contentObserver$1$onChange$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ ProducerScope $$this$callbackFlow;
    public final /* synthetic */ boolean $isLeft;
    public Object L$0;
    public Object L$1;
    public Object L$2;
    public Object L$3;
    public boolean Z$0;
    public int label;
    public final /* synthetic */ ShortcutRepositoryImpl$observeFlow$1$contentObserver$1 this$0;
    public final /* synthetic */ ShortcutRepositoryImpl this$1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutRepositoryImpl$observeFlow$1$contentObserver$1$onChange$1(ShortcutRepositoryImpl$observeFlow$1$contentObserver$1 shortcutRepositoryImpl$observeFlow$1$contentObserver$1, ShortcutRepositoryImpl shortcutRepositoryImpl, boolean z, ProducerScope producerScope, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shortcutRepositoryImpl$observeFlow$1$contentObserver$1;
        this.this$1 = shortcutRepositoryImpl;
        this.$isLeft = z;
        this.$$this$callbackFlow = producerScope;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShortcutRepositoryImpl$observeFlow$1$contentObserver$1$onChange$1(this.this$0, this.this$1, this.$isLeft, this.$$this$callbackFlow, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((ShortcutRepositoryImpl$observeFlow$1$contentObserver$1$onChange$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Mutex mutex;
        ShortcutRepositoryImpl$observeFlow$1$contentObserver$1 shortcutRepositoryImpl$observeFlow$1$contentObserver$1;
        ProducerScope producerScope;
        ShortcutRepositoryImpl shortcutRepositoryImpl;
        boolean z;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            mutex = this.this$0.getMutex();
            shortcutRepositoryImpl$observeFlow$1$contentObserver$1 = this.this$0;
            ShortcutRepositoryImpl shortcutRepositoryImpl2 = this.this$1;
            boolean z2 = this.$isLeft;
            producerScope = this.$$this$callbackFlow;
            this.L$0 = mutex;
            this.L$1 = shortcutRepositoryImpl$observeFlow$1$contentObserver$1;
            this.L$2 = shortcutRepositoryImpl2;
            this.L$3 = producerScope;
            this.Z$0 = z2;
            this.label = 1;
            if (mutex.lock(null, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            shortcutRepositoryImpl = shortcutRepositoryImpl2;
            z = z2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            z = this.Z$0;
            ProducerScope producerScope2 = (ProducerScope) this.L$3;
            shortcutRepositoryImpl = (ShortcutRepositoryImpl) this.L$2;
            shortcutRepositoryImpl$observeFlow$1$contentObserver$1 = (ShortcutRepositoryImpl$observeFlow$1$contentObserver$1) this.L$1;
            Mutex mutex2 = (Mutex) this.L$0;
            ResultKt.throwOnFailure(obj);
            producerScope = producerScope2;
            mutex = mutex2;
        }
        try {
            boolean zIsUnknown = shortcutRepositoryImpl$observeFlow$1$contentObserver$1.getCurrentData().isUnknown();
            DataUtils dataUtils = DataUtils.INSTANCE;
            ShortcutEntity shortcutDataFromSettings = dataUtils.getShortcutDataFromSettings(shortcutRepositoryImpl.context, z);
            LogUtils logUtils = LogUtils.INSTANCE;
            LogUtils.logD$default(logUtils, "ShortcutRepositoryImpl", "onChange isLeft:" + z + ", tmpShortcut=" + shortcutDataFromSettings + ", currentData=" + shortcutRepositoryImpl$observeFlow$1$contentObserver$1.getCurrentData(), null, 4, null);
            if (shortcutRepositoryImpl.isSystemUiProcess && zIsUnknown) {
                ShortcutEntity shortcutEntityUpdateShortcutWhenFirstLoad = shortcutRepositoryImpl.updateShortcutWhenFirstLoad(shortcutDataFromSettings, z);
                Object objMo1228trySendJP2dKIU = producerScope.mo1228trySendJP2dKIU(shortcutEntityUpdateShortcutWhenFirstLoad);
                LogUtils.logI$default(logUtils, "ShortcutRepositoryImpl", "updateShortcutWhenFirstLoad isLeft:" + z + ", send success: " + ChannelResult.m1246isSuccessimpl(objMo1228trySendJP2dKIU) + ", updatedShortcut=" + shortcutEntityUpdateShortcutWhenFirstLoad, null, 4, null);
                if (ChannelResult.m1246isSuccessimpl(objMo1228trySendJP2dKIU)) {
                    shortcutRepositoryImpl$observeFlow$1$contentObserver$1.setCurrentData(shortcutEntityUpdateShortcutWhenFirstLoad);
                }
            } else {
                if (shortcutDataFromSettings == null) {
                    shortcutDataFromSettings = dataUtils.getEMPTY_ENTITY();
                }
                if (!shortcutRepositoryImpl$observeFlow$1$contentObserver$1.getCurrentData().isSameShortcut(shortcutDataFromSettings)) {
                    if (!shortcutDataFromSettings.isEmptyOrUnknown()) {
                        if (shortcutRepositoryImpl$observeFlow$1$contentObserver$1.getCurrentData().isSameShortcutFingerprint(shortcutDataFromSettings)) {
                            if (!shortcutRepositoryImpl.isSystemUiProcess || shortcutRepositoryImpl$observeFlow$1$contentObserver$1.getCurrentData().isAvailable()) {
                                shortcutDataFromSettings.setAvailable(shortcutRepositoryImpl$observeFlow$1$contentObserver$1.getCurrentData().isAvailable());
                            } else {
                                Boxing.boxBoolean(shortcutDataFromSettings.updateAvailable(shortcutRepositoryImpl.context));
                            }
                            shortcutDataFromSettings.setShortcutName(shortcutRepositoryImpl$observeFlow$1$contentObserver$1.getCurrentData().getShortcutName());
                            shortcutDataFromSettings.setShortcutTrackName(shortcutRepositoryImpl$observeFlow$1$contentObserver$1.getCurrentData().getShortcutTrackName());
                            shortcutDataFromSettings.setDrawable(shortcutRepositoryImpl$observeFlow$1$contentObserver$1.getCurrentData().getDrawable());
                        } else {
                            if (zIsUnknown || shortcutRepositoryImpl.isSystemUiProcess) {
                                shortcutDataFromSettings.updateAvailable(shortcutRepositoryImpl.context);
                            }
                            shortcutDataFromSettings.buildShortcutNameAndDrawable(shortcutRepositoryImpl.context);
                        }
                    }
                    Object objMo1228trySendJP2dKIU2 = producerScope.mo1228trySendJP2dKIU(shortcutDataFromSettings);
                    LogUtils.logI$default(logUtils, "ShortcutRepositoryImpl", "onChange isLeft:" + z + ", send success: " + ChannelResult.m1246isSuccessimpl(objMo1228trySendJP2dKIU2) + ", newShortcut=" + shortcutDataFromSettings + ", currentData=" + shortcutRepositoryImpl$observeFlow$1$contentObserver$1.getCurrentData(), null, 4, null);
                    if (ChannelResult.m1246isSuccessimpl(objMo1228trySendJP2dKIU2)) {
                        shortcutRepositoryImpl$observeFlow$1$contentObserver$1.setCurrentData(shortcutDataFromSettings);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
            return Unit.INSTANCE;
        } finally {
            mutex.unlock(null);
        }
    }
}
