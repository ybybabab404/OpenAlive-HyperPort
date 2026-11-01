package com.miui.keyguard.editor.settings;

import android.util.Log;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Merge.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.settings.SettingsTemplateDataModelProvider$getRefreshTemplatesFlow$$inlined$flatMapLatest$1", f = "SettingsTemplateDataProvider.kt", l = {193}, m = "invokeSuspend")
@SourceDebugExtension
public final class SettingsTemplateDataModelProvider$getRefreshTemplatesFlow$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super List<? extends TemplateItemBean>>, Integer, Continuation<? super Unit>, Object> {
    public /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ SettingsTemplateDataModelProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsTemplateDataModelProvider$getRefreshTemplatesFlow$$inlined$flatMapLatest$1(Continuation continuation, SettingsTemplateDataModelProvider settingsTemplateDataModelProvider) {
        super(3, continuation);
        this.this$0 = settingsTemplateDataModelProvider;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(FlowCollector flowCollector, Object obj, Continuation continuation) {
        SettingsTemplateDataModelProvider$getRefreshTemplatesFlow$$inlined$flatMapLatest$1 settingsTemplateDataModelProvider$getRefreshTemplatesFlow$$inlined$flatMapLatest$1 = new SettingsTemplateDataModelProvider$getRefreshTemplatesFlow$$inlined$flatMapLatest$1(continuation, this.this$0);
        settingsTemplateDataModelProvider$getRefreshTemplatesFlow$$inlined$flatMapLatest$1.L$0 = flowCollector;
        settingsTemplateDataModelProvider$getRefreshTemplatesFlow$$inlined$flatMapLatest$1.L$1 = obj;
        return settingsTemplateDataModelProvider$getRefreshTemplatesFlow$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int iIntValue = ((Number) this.L$1).intValue();
            Log.i("SettingsTemplateDataModel", "start load combine data");
            Flow resultTemplatesFlow = this.this$0.getResultTemplatesFlow(iIntValue);
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, resultTemplatesFlow, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
