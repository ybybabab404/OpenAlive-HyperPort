package com.miui.keyguard.shortcuts.manager;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
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
/* JADX INFO: compiled from: PackageChangedManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.PackageChangedManager$handlePackageAddedOrUpdated$1$1$1", f = "PackageChangedManager.kt", l = {}, m = "invokeSuspend")
@SourceDebugExtension
public final class PackageChangedManager$handlePackageAddedOrUpdated$1$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ boolean $isUpdate;
    public final /* synthetic */ String $packageName;
    public final /* synthetic */ List $shortcuts;
    public int label;
    public final /* synthetic */ PackageChangedManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackageChangedManager$handlePackageAddedOrUpdated$1$1$1(boolean z, PackageChangedManager packageChangedManager, String str, List list, Continuation continuation) {
        super(2, continuation);
        this.$isUpdate = z;
        this.this$0 = packageChangedManager;
        this.$packageName = str;
        this.$shortcuts = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PackageChangedManager$handlePackageAddedOrUpdated$1$1$1(this.$isUpdate, this.this$0, this.$packageName, this.$shortcuts, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((PackageChangedManager$handlePackageAddedOrUpdated$1$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.$isUpdate) {
            List list = this.this$0.packageChangedCallbacks;
            String str = this.$packageName;
            List list2 = this.$shortcuts;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((PackageChangedManager.PackageChangedCallback) it.next()).onPackageUpdate(str, list2);
            }
        } else {
            List list3 = this.this$0.packageChangedCallbacks;
            String str2 = this.$packageName;
            List list4 = this.$shortcuts;
            Iterator it2 = list3.iterator();
            while (it2.hasNext()) {
                ((PackageChangedManager.PackageChangedCallback) it2.next()).onPackageAdded(str2, list4);
            }
        }
        return Unit.INSTANCE;
    }
}
