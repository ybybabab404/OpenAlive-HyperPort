package com.miui.keyguard.editor.settings.data;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.utils.GsonSingletonKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
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
/* JADX INFO: compiled from: SettingsConfigChangeDataSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SettingsConfigChangeDataSource {
    public static final Companion Companion = new Companion(null);
    public final Context context;

    /* JADX INFO: compiled from: SettingsConfigChangeDataSource.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.SettingsConfigChangeDataSource$getSettingsConfigChangedEventFlow$1, reason: invalid class name */
    /* JADX INFO: compiled from: SettingsConfigChangeDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.SettingsConfigChangeDataSource$getSettingsConfigChangedEventFlow$1", f = "SettingsConfigChangeDataSource.kt", l = {57}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<ProducerScope<? super String>, Continuation<? super Unit>, Object> {
        public /* synthetic */ Object L$0;
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        public static final Unit invokeSuspend$lambda$1(SettingsConfigChangeDataSource settingsConfigChangeDataSource, SettingsConfigChangeDataSource$getSettingsConfigChangedEventFlow$1$settingsConfigChangedListener$1 settingsConfigChangeDataSource$getSettingsConfigChangedEventFlow$1$settingsConfigChangedListener$1) {
            settingsConfigChangeDataSource.getContext().getContentResolver().unregisterContentObserver(settingsConfigChangeDataSource$getSettingsConfigChangedEventFlow$1$settingsConfigChangedListener$1);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = SettingsConfigChangeDataSource.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v3, types: [android.database.ContentObserver, com.miui.keyguard.editor.settings.data.SettingsConfigChangeDataSource$getSettingsConfigChangedEventFlow$1$settingsConfigChangedListener$1] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                CommonConfig commonConfig = null;
                try {
                    String string = Settings.Secure.getString(SettingsConfigChangeDataSource.this.getContext().getContentResolver(), "constant_template_editor_info");
                    if (string != null) {
                        commonConfig = (CommonConfig) GsonSingletonKt.getGSON_SINGLETON().fromJson(string, CommonConfig.class);
                    }
                } catch (Exception e) {
                    Log.w("SettingsConfigChangeDataSource", "init lastTemplateConfig failed: " + e);
                }
                objectRef.element = commonConfig;
                final Handler handler = new Handler(Looper.getMainLooper());
                final SettingsConfigChangeDataSource settingsConfigChangeDataSource = SettingsConfigChangeDataSource.this;
                final ?? r5 = new ContentObserver(handler) { // from class: com.miui.keyguard.editor.settings.data.SettingsConfigChangeDataSource$getSettingsConfigChangedEventFlow$1$settingsConfigChangedListener$1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z, Uri uri) {
                        super.onChange(z, uri);
                        String string2 = Settings.Secure.getString(settingsConfigChangeDataSource.getContext().getContentResolver(), "constant_template_editor_info");
                        if (string2 == null) {
                            return;
                        }
                        CommonConfig commonConfig2 = (CommonConfig) GsonSingletonKt.getGSON_SINGLETON().fromJson(string2, CommonConfig.class);
                        Log.i("SettingsConfigChangeDataSource", "Settings template info changed uri=" + uri + ",templateConfig=" + commonConfig2);
                        CurrentTemplateDataSource.Companion companion = CurrentTemplateDataSource.Companion;
                        boolean zIsConfigDiff = companion.isConfigDiff((CommonConfig) objectRef.element, commonConfig2);
                        boolean zIsWallpaperChangedByThirdApp = companion.isWallpaperChangedByThirdApp(settingsConfigChangeDataSource.getContext(), TemplateApi.Companion.getInstance(settingsConfigChangeDataSource.getContext()));
                        if (!zIsConfigDiff || zIsWallpaperChangedByThirdApp) {
                            return;
                        }
                        Log.i("SettingsConfigChangeDataSource", "config is changed, need to notify to refresh");
                        objectRef.element = commonConfig2;
                        producerScope.mo1228trySendJP2dKIU("settings_template_info_changed");
                    }
                };
                SettingsConfigChangeDataSource.this.getContext().getContentResolver().registerContentObserver(Settings.Secure.getUriFor("constant_template_editor_info"), false, r5);
                final SettingsConfigChangeDataSource settingsConfigChangeDataSource2 = SettingsConfigChangeDataSource.this;
                Function0 function0 = new Function0() { // from class: com.miui.keyguard.editor.settings.data.SettingsConfigChangeDataSource$getSettingsConfigChangedEventFlow$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return SettingsConfigChangeDataSource.AnonymousClass1.invokeSuspend$lambda$1(settingsConfigChangeDataSource2, r5);
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

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.SettingsConfigChangeDataSource$getSettingsConfigChangedEventFlow$2, reason: invalid class name */
    /* JADX INFO: compiled from: SettingsConfigChangeDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.SettingsConfigChangeDataSource$getSettingsConfigChangedEventFlow$2", f = "SettingsConfigChangeDataSource.kt", l = {}, m = "invokeSuspend")
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
            Log.e("SettingsConfigChangeDataSource", "getSettingsConfigChangedEventFlow error", (Throwable) this.L$0);
            return Unit.INSTANCE;
        }
    }

    public SettingsConfigChangeDataSource(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final Context getContext() {
        return this.context;
    }

    public final Flow getSettingsConfigChangedEventFlow() {
        return FlowKt.flowOn(FlowKt.m1255catch(FlowKt.callbackFlow(new AnonymousClass1(null)), new AnonymousClass2(null)), Dispatchers.getIO());
    }
}
