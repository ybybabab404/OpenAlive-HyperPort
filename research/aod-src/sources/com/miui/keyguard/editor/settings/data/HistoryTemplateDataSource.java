package com.miui.keyguard.editor.settings.data;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.maml.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: HistoryTemplateDataSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class HistoryTemplateDataSource {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final TemplateApi dataApi;

    /* JADX INFO: compiled from: HistoryTemplateDataSource.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$deleteHistory$1, reason: invalid class name */
    /* JADX INFO: compiled from: HistoryTemplateDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource", f = "HistoryTemplateDataSource.kt", l = {R.styleable.AppCompatTheme_windowActionBar}, m = "deleteHistory")
    public static final class AnonymousClass1 extends ContinuationImpl {
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HistoryTemplateDataSource.this.deleteHistory(null, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplateBitmap$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: HistoryTemplateDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource", f = "HistoryTemplateDataSource.kt", l = {149}, m = "getHistoryTemplateBitmap")
    public static final class C01361 extends ContinuationImpl {
        public int label;
        public /* synthetic */ Object result;

        public C01361(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HistoryTemplateDataSource.this.getHistoryTemplateBitmap(null, null, 0, 0, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplatesChangedEventFlow$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: HistoryTemplateDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplatesChangedEventFlow$1", f = "HistoryTemplateDataSource.kt", l = {R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class C01371 extends SuspendLambda implements Function2<ProducerScope<? super String>, Continuation<? super Unit>, Object> {
        public /* synthetic */ Object L$0;
        public int label;

        public C01371(Continuation continuation) {
            super(2, continuation);
        }

        public static final Unit invokeSuspend$lambda$0(HistoryTemplateDataSource historyTemplateDataSource, HistoryTemplateDataSource$getHistoryTemplatesChangedEventFlow$1$historyTemplatesChangedListener$1 historyTemplateDataSource$getHistoryTemplatesChangedEventFlow$1$historyTemplatesChangedListener$1) {
            historyTemplateDataSource.context.getContentResolver().unregisterContentObserver(historyTemplateDataSource$getHistoryTemplatesChangedEventFlow$1$historyTemplatesChangedListener$1);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C01371 c01371 = HistoryTemplateDataSource.this.new C01371(continuation);
            c01371.L$0 = obj;
            return c01371;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((C01371) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v1, types: [android.database.ContentObserver, com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplatesChangedEventFlow$1$historyTemplatesChangedListener$1] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final Handler handler = new Handler(Looper.getMainLooper());
                final ?? r3 = new ContentObserver(handler) { // from class: com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplatesChangedEventFlow$1$historyTemplatesChangedListener$1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z, Uri uri) {
                        super.onChange(z, uri);
                        Log.i("HistoryTemplateDataSource", "history templates has changed, need to notify to refresh");
                        producerScope.mo1228trySendJP2dKIU("history_templates_changed");
                    }
                };
                HistoryTemplateDataSource.this.context.getContentResolver().registerContentObserver(Uri.parse("content://miui.keyguard.editor.templatefileprovider/notify_delete_histories"), false, r3);
                final HistoryTemplateDataSource historyTemplateDataSource = HistoryTemplateDataSource.this;
                Function0 function0 = new Function0() { // from class: com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplatesChangedEventFlow$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return HistoryTemplateDataSource.C01371.invokeSuspend$lambda$0(historyTemplateDataSource, r3);
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplatesChangedEventFlow$2, reason: invalid class name */
    /* JADX INFO: compiled from: HistoryTemplateDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplatesChangedEventFlow$2", f = "HistoryTemplateDataSource.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function3<FlowCollector<? super String>, Throwable, Continuation<? super Unit>, Object> {
        public /* synthetic */ Object L$0;
        public int label;

        public AnonymousClass2(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(FlowCollector flowCollector, Throwable th, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
            anonymousClass2.L$0 = th;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Log.e("HistoryTemplateDataSource", "getSettingsConfigChangedEventFlow error", (Throwable) this.L$0);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplatesFlow$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: HistoryTemplateDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplatesFlow$1", f = "HistoryTemplateDataSource.kt", l = {54}, m = "invokeSuspend")
    public static final class C01381 extends SuspendLambda implements Function2<FlowCollector<? super List<? extends TemplateItemBean>>, Continuation<? super Unit>, Object> {
        public /* synthetic */ Object L$0;
        public int label;

        public C01381(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C01381 c01381 = HistoryTemplateDataSource.this.new C01381(continuation);
            c01381.L$0 = obj;
            return c01381;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((C01381) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws RemoteException {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                List historyTemplates = HistoryTemplateDataSource.this.getHistoryTemplates();
                this.label = 1;
                if (flowCollector.emit(historyTemplates, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplatesFlow$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: HistoryTemplateDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplatesFlow$2", f = "HistoryTemplateDataSource.kt", l = {57}, m = "invokeSuspend")
    public static final class C01392 extends SuspendLambda implements Function4<FlowCollector<? super List<? extends TemplateItemBean>>, Throwable, Long, Continuation<? super Boolean>, Object> {
        public /* synthetic */ long J$0;
        public /* synthetic */ Object L$0;
        public int label;

        public C01392(Continuation continuation) {
            super(4, continuation);
        }

        @Override // kotlin.jvm.functions.Function4
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            return invoke((FlowCollector) obj, (Throwable) obj2, ((Number) obj3).longValue(), (Continuation) obj4);
        }

        public final Object invoke(FlowCollector flowCollector, Throwable th, long j, Continuation continuation) {
            C01392 c01392 = new C01392(continuation);
            c01392.L$0 = th;
            c01392.J$0 = j;
            return c01392.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Throwable th;
            long j;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Throwable th2 = (Throwable) this.L$0;
                long j2 = this.J$0;
                Log.e("HistoryTemplateDataSource", "allHistoryTemplatesTask cause: " + th2 + ", attempt: " + j2);
                this.L$0 = th2;
                this.J$0 = j2;
                this.label = 1;
                if (DelayKt.delay(1000L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                th = th2;
                j = j2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                j = this.J$0;
                th = (Throwable) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            return Boxing.boxBoolean((th instanceof RemoteException) && j < 3);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplatesFlow$3, reason: invalid class name */
    /* JADX INFO: compiled from: HistoryTemplateDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.HistoryTemplateDataSource$getHistoryTemplatesFlow$3", f = "HistoryTemplateDataSource.kt", l = {61}, m = "invokeSuspend")
    public static final class AnonymousClass3 extends SuspendLambda implements Function3<FlowCollector<? super List<? extends TemplateItemBean>>, Throwable, Continuation<? super Unit>, Object> {
        public /* synthetic */ Object L$0;
        public /* synthetic */ Object L$1;
        public int label;

        public AnonymousClass3(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(FlowCollector flowCollector, Throwable th, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation);
            anonymousClass3.L$0 = flowCollector;
            anonymousClass3.L$1 = th;
            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Log.e("HistoryTemplateDataSource", "allHistoryTemplatesTask error: " + ((Throwable) this.L$1));
                List listEmptyList = CollectionsKt.emptyList();
                this.L$0 = null;
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

    public HistoryTemplateDataSource(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.dataApi = TemplateApi.Companion.getInstance(context);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public final Object deleteHistory(TemplateItemBean templateItemBean, Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object objWithContext = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            CoroutineDispatcher io2 = Dispatchers.getIO();
            HistoryTemplateDataSource$deleteHistory$deleteResult$1 historyTemplateDataSource$deleteHistory$deleteResult$1 = new HistoryTemplateDataSource$deleteHistory$deleteResult$1(templateItemBean, this, null);
            anonymousClass1.label = 1;
            objWithContext = BuildersKt.withContext(io2, historyTemplateDataSource$deleteHistory$deleteResult$1, anonymousClass1);
            if (objWithContext == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objWithContext);
        }
        return Boxing.boxBoolean(((Boolean) objWithContext).booleanValue());
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public final Object getHistoryTemplateBitmap(Context context, TemplateItemBean templateItemBean, int i, int i2, Continuation continuation) {
        C01361 c01361;
        if (continuation instanceof C01361) {
            c01361 = (C01361) continuation;
            int i3 = c01361.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                c01361.label = i3 - Integer.MIN_VALUE;
            } else {
                c01361 = new C01361(continuation);
            }
        } else {
            c01361 = new C01361(continuation);
        }
        Object objWithContext = c01361.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i4 = c01361.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            TemplateHistoryConfig history = templateItemBean.getHistory();
            if (history == null) {
                return null;
            }
            CoroutineDispatcher io2 = Dispatchers.getIO();
            HistoryTemplateDataSource$getHistoryTemplateBitmap$screenshots$1 historyTemplateDataSource$getHistoryTemplateBitmap$screenshots$1 = new HistoryTemplateDataSource$getHistoryTemplateBitmap$screenshots$1(context, history, i, i2, null);
            c01361.label = 1;
            objWithContext = BuildersKt.withContext(io2, historyTemplateDataSource$getHistoryTemplateBitmap$screenshots$1, c01361);
            if (objWithContext == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i4 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objWithContext);
        }
        return (Pair) objWithContext;
    }

    public final List getHistoryTemplates() throws RemoteException {
        ArrayList<TemplateHistoryConfig> parcelableArrayList;
        Log.i("HistoryTemplateDataSource", "allHistoryTemplatesTask start");
        ArrayList arrayList = new ArrayList();
        Bundle bundleCall = this.context.getContentResolver().call(Uri.parse("content://miui.keyguard.editor.templatefileprovider"), "callGetHistoryTemplates", (String) null, (Bundle) null);
        if (bundleCall != null) {
            bundleCall.setClassLoader(TemplateHistoryConfig.class.getClassLoader());
        }
        if (bundleCall == null || (parcelableArrayList = bundleCall.getParcelableArrayList("historyTemplates")) == null) {
            throw new RemoteException("The keyEditor process has not yet been created");
        }
        for (TemplateHistoryConfig templateHistoryConfig : parcelableArrayList) {
            if (templateHistoryConfig.getMagicType() != 100000 || FileUtil.isSupportVideoDepth()) {
                templateHistoryConfig.setLoadingDrawable(this.dataApi.getTemplateLoadingDrawable(templateHistoryConfig.getLoadingColor()));
                arrayList.add(new TemplateItemBean(2, 0, Integer.valueOf(com.miui.keyguard.editor.R.string.kg_template_title_mine_collocation), null, templateHistoryConfig, null, null, null, null, false, false, false, false, 8160, null));
            }
        }
        Log.i("HistoryTemplateDataSource", "allHistoryTemplatesTask completed, allHistoryTemplates.size = " + arrayList.size());
        return arrayList;
    }

    public final Flow getHistoryTemplatesChangedEventFlow() {
        return FlowKt.flowOn(FlowKt.m1255catch(FlowKt.callbackFlow(new C01371(null)), new AnonymousClass2(null)), Dispatchers.getIO());
    }

    public final Flow getHistoryTemplatesFlow() {
        return FlowKt.flowOn(FlowKt.m1255catch(FlowKt.retryWhen(FlowKt.flow(new C01381(null)), new C01392(null)), new AnonymousClass3(null)), Dispatchers.getIO());
    }
}
