package com.miui.keyguard.editor.data.template;

import android.util.Log;
import com.miui.keyguard.editor.utils.FileUtil;
import java.io.File;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TemplateApiImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.data.template.TemplateApiImpl$copyFile$copyResult$1", f = "TemplateApiImpl.kt", l = {}, m = "invokeSuspend")
public final class TemplateApiImpl$copyFile$copyResult$1 extends SuspendLambda implements Function1<Continuation<? super Boolean>, Object> {
    public final /* synthetic */ boolean $forceSync;
    public final /* synthetic */ String $srcFileString;
    public final /* synthetic */ String $target;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateApiImpl$copyFile$copyResult$1(String str, String str2, boolean z, Continuation continuation) {
        super(1, continuation);
        this.$target = str;
        this.$srcFileString = str2;
        this.$forceSync = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new TemplateApiImpl$copyFile$copyResult$1(this.$target, this.$srcFileString, this.$forceSync, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Continuation continuation) {
        return ((TemplateApiImpl$copyFile$copyResult$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        FileUtil.makeFileExists(new File(this.$target));
        boolean zFastCopy = FileUtil.fastCopy(this.$srcFileString, this.$target, this.$forceSync);
        if (!zFastCopy) {
            Log.e("Keyguard-Editor-TemplateApiImpl", "copyFile: fastCopy failed, cleaning partial file. target=" + this.$target);
            FileUtil.delete(this.$target);
            Log.w("Keyguard-Editor-TemplateApiImpl", "copyFile: delete partial file result=" + Unit.INSTANCE + ", target=" + this.$target);
        }
        return Boxing.boxBoolean(zFastCopy);
    }
}
