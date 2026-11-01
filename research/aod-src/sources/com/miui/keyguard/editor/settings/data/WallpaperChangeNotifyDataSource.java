package com.miui.keyguard.editor.settings.data;

import android.app.WallpaperColors;
import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.WallpaperWhich;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.miwallpaper.IMiuiWallpaperManagerCallback;
import com.miui.miwallpaper.MiuiWallpaperManager;
import java.util.Map;
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
/* JADX INFO: compiled from: WallpaperChangeNotifyDataSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class WallpaperChangeNotifyDataSource {
    public static final Companion Companion = new Companion(null);
    public final Context context;

    /* JADX INFO: compiled from: WallpaperChangeNotifyDataSource.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.WallpaperChangeNotifyDataSource$getWallPaperChangedEventFlow$1, reason: invalid class name */
    /* JADX INFO: compiled from: WallpaperChangeNotifyDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.WallpaperChangeNotifyDataSource$getWallPaperChangedEventFlow$1", f = "WallpaperChangeNotifyDataSource.kt", l = {50}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<ProducerScope<? super String>, Continuation<? super Unit>, Object> {
        public /* synthetic */ Object L$0;
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        public static final Unit invokeSuspend$lambda$0(WallpaperChangeNotifyDataSource wallpaperChangeNotifyDataSource, WallpaperChangeNotifyDataSource$getWallPaperChangedEventFlow$1$wallpaperChangedListener$1 wallpaperChangeNotifyDataSource$getWallPaperChangedEventFlow$1$wallpaperChangedListener$1) {
            WallpaperController.Companion.getInstance(wallpaperChangeNotifyDataSource.context).unregisterWallpaperChangedListener(wallpaperChangeNotifyDataSource$getWallPaperChangedEventFlow$1$wallpaperChangedListener$1);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = WallpaperChangeNotifyDataSource.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v1, types: [com.miui.keyguard.editor.settings.data.WallpaperChangeNotifyDataSource$getWallPaperChangedEventFlow$1$wallpaperChangedListener$1, com.miui.miwallpaper.IMiuiWallpaperManagerCallback] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final ?? r1 = new IMiuiWallpaperManagerCallback.Stub() { // from class: com.miui.keyguard.editor.settings.data.WallpaperChangeNotifyDataSource$getWallPaperChangedEventFlow$1$wallpaperChangedListener$1
                    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
                    public void onDrawFrameEnd() {
                        Log.i("WallpaperChangeNotifyDataSource", "onDrawFrameEnd");
                    }

                    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
                    public void onPartColorComputeComplete(Map map, Map map2, int i2) {
                        Log.i("WallpaperChangeNotifyDataSource", "onPartColorComputeComplete");
                    }

                    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
                    public void onWallpaperChanged(WallpaperColors wallpaperColors, String str, int i2) {
                        CurrentTemplateDataSource.Companion companion = CurrentTemplateDataSource.Companion;
                        Log.i("WallpaperChangeNotifyDataSource", "onWallpaperChanged which=" + i2 + " applyingMaml=" + companion.getApplyingMaml());
                        if (i2 == WallpaperWhich.HomeWhich.INSTANCE.getWhich() && companion.getApplyingMaml()) {
                            return;
                        }
                        producerScope.mo1228trySendJP2dKIU("wallpaperChanged");
                    }

                    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
                    public void onWallpaperFirstFrameRendered(int i2) {
                        Log.i("WallpaperChangeNotifyDataSource", "onWallpaperFirstFrameRendered");
                    }
                };
                WallpaperController.Companion.getInstance(WallpaperChangeNotifyDataSource.this.context).registerWallpaperChangedListener(r1, MiuiWallpaperManager.MI_WALLPAPER_WHICH_ALL);
                final WallpaperChangeNotifyDataSource wallpaperChangeNotifyDataSource = WallpaperChangeNotifyDataSource.this;
                Function0 function0 = new Function0() { // from class: com.miui.keyguard.editor.settings.data.WallpaperChangeNotifyDataSource$getWallPaperChangedEventFlow$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return WallpaperChangeNotifyDataSource.AnonymousClass1.invokeSuspend$lambda$0(wallpaperChangeNotifyDataSource, r1);
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

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.WallpaperChangeNotifyDataSource$getWallPaperChangedEventFlow$2, reason: invalid class name */
    /* JADX INFO: compiled from: WallpaperChangeNotifyDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.WallpaperChangeNotifyDataSource$getWallPaperChangedEventFlow$2", f = "WallpaperChangeNotifyDataSource.kt", l = {}, m = "invokeSuspend")
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
            Log.e("WallpaperChangeNotifyDataSource", "getWallPaperChangedEventFlow: ", (Throwable) this.L$0);
            return Unit.INSTANCE;
        }
    }

    public WallpaperChangeNotifyDataSource(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final Flow getWallPaperChangedEventFlow() {
        return FlowKt.flowOn(FlowKt.m1255catch(FlowKt.callbackFlow(new AnonymousClass1(null)), new AnonymousClass2(null)), Dispatchers.getIO());
    }
}
