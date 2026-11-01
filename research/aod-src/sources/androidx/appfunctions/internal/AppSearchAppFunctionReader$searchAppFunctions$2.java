package androidx.appfunctions.internal;

import androidx.appfunctions.AppFunctionSearchSpec;
import androidx.appfunctions.metadata.AppFunctionPackageMetadata;
import androidx.appsearch.app.GlobalSearchSession;
import com.miui.maml.R;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ProducerScope;

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
@DebugMetadata(c = "androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2", f = "AppSearchAppFunctionReader.kt", l = {R.styleable.AppCompatTheme_panelMenuListTheme, R.styleable.AppCompatTheme_popupWindowStyle, R.styleable.AppCompatTheme_popupWindowStyle, R.styleable.AppCompatTheme_textColorSearchUrl}, m = "invokeSuspend")
final class AppSearchAppFunctionReader$searchAppFunctions$2 extends SuspendLambda implements Function2<ProducerScope<? super List<? extends AppFunctionPackageMetadata>>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ AppFunctionSearchSpec $searchFunctionSpec;
    public /* synthetic */ Object L$0;
    public Object L$1;
    public Object L$2;
    public int label;
    public final /* synthetic */ AppSearchAppFunctionReader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppSearchAppFunctionReader$searchAppFunctions$2(AppSearchAppFunctionReader appSearchAppFunctionReader, AppFunctionSearchSpec appFunctionSearchSpec, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appSearchAppFunctionReader;
        this.$searchFunctionSpec = appFunctionSearchSpec;
    }

    public static final Unit invokeSuspend$lambda$0(Job job, AppSearchAppFunctionReader.AppSearchChannelObserver appSearchChannelObserver, GlobalSearchSession globalSearchSession) {
        Job.DefaultImpls.cancel$default(job, null, 1, null);
        appSearchChannelObserver.close();
        globalSearchSession.unregisterObserverCallback("android", appSearchChannelObserver);
        globalSearchSession.close();
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppSearchAppFunctionReader$searchAppFunctions$2 appSearchAppFunctionReader$searchAppFunctions$2 = new AppSearchAppFunctionReader$searchAppFunctions$2(this.this$0, this.$searchFunctionSpec, continuation);
        appSearchAppFunctionReader$searchAppFunctions$2.L$0 = obj;
        return appSearchAppFunctionReader$searchAppFunctions$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((AppSearchAppFunctionReader$searchAppFunctions$2) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x008e  */
    /* JADX WARN: Code duplicated, block: B:27:0x009f  */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00dd, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r11, r4, r20) == r1) goto L30;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r21) {
        /*
            Method dump skipped, instruction units count: 227
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
