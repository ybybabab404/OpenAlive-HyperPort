package com.miui.keyguard.editor.settings.data;

import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.utils.segment.UtilsKt;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: HomeColumnDataSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class HomeColumnDataSource {
    public static final HomeColumnDataSource INSTANCE = new HomeColumnDataSource();
    public static int homeColumns;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.HomeColumnDataSource$getHomeColumns$1, reason: invalid class name */
    /* JADX INFO: compiled from: HomeColumnDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.HomeColumnDataSource$getHomeColumns$1", f = "HomeColumnDataSource.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Context context, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Log.d("HomeColumnDataSource", "getHomeColumns start");
            Object objRunSyncWithTimeOut = UtilsKt.runSyncWithTimeOut(Dispatchers.getIO(), 100L, Boxing.boxBoolean(true), new HomeColumnDataSource$getHomeColumns$1$columnNums$1(this.$context, null));
            if (objRunSyncWithTimeOut == null) {
                Log.d("HomeColumnDataSource", "getHomeColumns end");
                return Unit.INSTANCE;
            }
            Log.d("HomeColumnDataSource", "getHomeColumns column number is " + objRunSyncWithTimeOut);
            HomeColumnDataSource homeColumnDataSource = HomeColumnDataSource.INSTANCE;
            Integer num = objRunSyncWithTimeOut instanceof Integer ? (Integer) objRunSyncWithTimeOut : null;
            HomeColumnDataSource.homeColumns = num != null ? num.intValue() : 0;
            return Unit.INSTANCE;
        }
    }

    public final void clearHomeColumns() {
        homeColumns = 0;
    }

    public final int getHomeColumns(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int i = homeColumns;
        if (i != 0) {
            return i;
        }
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), null, null, new AnonymousClass1(context, null), 3, null);
        int i2 = homeColumns;
        if (i2 == 0) {
            return 4;
        }
        return i2;
    }
}
