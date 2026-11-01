package com.miui.keyguard.editor.homepage.model;

import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.db.EditorDatabase;
import com.miui.keyguard.editor.data.migration.AllInOneClockMigration;
import com.miui.keyguard.editor.data.template.TemplateApi;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result;
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
/* JADX INFO: compiled from: CrossListDataModel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.homepage.model.CrossListDataModel$Companion$updatePresetDatabaseAsync$1", f = "CrossListDataModel.kt", l = {}, m = "invokeSuspend")
@SourceDebugExtension
public final class CrossListDataModel$Companion$updatePresetDatabaseAsync$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ List $allSupport;
    public final /* synthetic */ Context $context;
    public /* synthetic */ Object L$0;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CrossListDataModel$Companion$updatePresetDatabaseAsync$1(Context context, List list, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$allSupport = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CrossListDataModel$Companion$updatePresetDatabaseAsync$1 crossListDataModel$Companion$updatePresetDatabaseAsync$1 = new CrossListDataModel$Companion$updatePresetDatabaseAsync$1(this.$context, this.$allSupport, continuation);
        crossListDataModel$Companion$updatePresetDatabaseAsync$1.L$0 = obj;
        return crossListDataModel$Companion$updatePresetDatabaseAsync$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((CrossListDataModel$Companion$updatePresetDatabaseAsync$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CrossListDataModel.Companion.realUpdate(this.$context, this.$allSupport);
        Context context = this.$context;
        try {
            Result.Companion companion = Result.Companion;
            List invalidList = EditorDatabase.Companion.getInstance(context).getTemplateHistoryDao().getInvalidList();
            ArrayList<TemplateHistoryConfig> arrayList = new ArrayList();
            for (Object obj2 : invalidList) {
                if (!AllInOneClockMigration.INSTANCE.getUPGRADABLE_TEMPLATES().contains(((TemplateHistoryConfig) obj2).getTemplateId())) {
                    arrayList.add(obj2);
                }
            }
            Log.i("PresetDB", "updatePresetDatabaseAsync: invalid templates found: " + invalidList.size() + ", safe to delete: " + arrayList.size());
            if (!arrayList.isEmpty()) {
                EditorDatabase.Companion.getInstance(context).getTemplateHistoryDao();
                for (TemplateHistoryConfig templateHistoryConfig : arrayList) {
                    Log.i("PresetDB", "updatePresetDatabaseAsync: deleting invalid template: templateId=" + templateHistoryConfig.getTemplateId() + ", configUri=" + templateHistoryConfig.getConfigUri());
                    TemplateApi.DefaultImpls.deleteHistoryTemplate$default(TemplateApi.Companion.getInstance(context), templateHistoryConfig, false, false, 6, null);
                }
            }
            Result.m1061constructorimpl(Boxing.boxInt(Log.i("PresetDB", "updatePresetDatabaseAsync delete completed")));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        return Unit.INSTANCE;
    }
}
