package androidx.appfunctions.internal;

import androidx.appfunctions.metadata.AppFunctionPackageMetadata;
import com.miui.maml.R;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@DebugMetadata(c = "androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$1", f = "AppSearchAppFunctionReader.kt", l = {R.styleable.AppCompatTheme_listPreferredItemPaddingLeft}, m = "invokeSuspend")
final class AppSearchAppFunctionReader$searchAppFunctions$1 extends SuspendLambda implements Function2<FlowCollector<? super List<? extends AppFunctionPackageMetadata>>, Continuation<? super Unit>, Object> {
    public /* synthetic */ Object L$0;
    public int label;

    public AppSearchAppFunctionReader$searchAppFunctions$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppSearchAppFunctionReader$searchAppFunctions$1 appSearchAppFunctionReader$searchAppFunctions$1 = new AppSearchAppFunctionReader$searchAppFunctions$1(continuation);
        appSearchAppFunctionReader$searchAppFunctions$1.L$0 = obj;
        return appSearchAppFunctionReader$searchAppFunctions$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
        return ((AppSearchAppFunctionReader$searchAppFunctions$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            List listEmptyList = CollectionsKt.emptyList();
            this.label = 1;
            if (flowCollector.emit(listEmptyList, this) == coroutine_suspended) {
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
