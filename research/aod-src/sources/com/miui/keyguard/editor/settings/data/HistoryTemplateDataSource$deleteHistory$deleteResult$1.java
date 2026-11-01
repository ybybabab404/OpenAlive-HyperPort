package com.miui.keyguard.editor.settings.data;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.utils.DeviceUtil;
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

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: HistoryTemplateDataSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$deleteHistory$deleteResult$1", f = "HistoryTemplateDataSource.kt", l = {}, m = "invokeSuspend")
@SourceDebugExtension
public final class HistoryTemplateDataSource$deleteHistory$deleteResult$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    public final /* synthetic */ TemplateItemBean $itemBean;
    public int label;
    public final /* synthetic */ HistoryTemplateDataSource this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HistoryTemplateDataSource$deleteHistory$deleteResult$1(TemplateItemBean templateItemBean, HistoryTemplateDataSource historyTemplateDataSource, Continuation continuation) {
        super(2, continuation);
        this.$itemBean = templateItemBean;
        this.this$0 = historyTemplateDataSource;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HistoryTemplateDataSource$deleteHistory$deleteResult$1(this.$itemBean, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((HistoryTemplateDataSource$deleteHistory$deleteResult$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        TemplateHistoryConfig history;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        TemplateItemBean templateItemBean = this.$itemBean;
        long id = (templateItemBean == null || (history = templateItemBean.getHistory()) == null) ? 0L : history.getId();
        Bundle bundle = new Bundle();
        bundle.putLong("entityId", id);
        boolean z = false;
        for (int i = 0; i < 4; i++) {
            try {
                Bundle bundleCall = this.this$0.context.getContentResolver().call(Uri.parse("content://miui.keyguard.editor.templatefileprovider"), "callDeleteHistoryTemplate", (String) null, bundle);
                Log.i("HistoryTemplateDataSource", "deleteTemplateByProvider i=" + i + ", result:" + bundleCall);
                if (bundleCall != null) {
                    boolean z2 = true;
                    if (DeviceUtil.INSTANCE.getEditorVersion(this.this$0.context) > 50) {
                        z2 = bundleCall.getBoolean("resultDeleteTemplate");
                    }
                    Log.i("HistoryTemplateDataSource", "deleteTemplateByProvider deleteResult:" + z2);
                    z = z2;
                    break;
                }
            } catch (Exception e) {
                Log.e("HistoryTemplateDataSource", "externalCallback@onPageDeleted failed: " + e);
            }
        }
        return Boxing.boxBoolean(z);
    }
}
