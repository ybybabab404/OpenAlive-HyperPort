package com.miui.keyguard.editor.settings.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.DoodleInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.CurrentTemplateApi;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.utils.task.Task;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Dispatchers;
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
/* JADX INFO: compiled from: CurrentTemplateDataSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CurrentTemplateDataSource {
    public static final Companion Companion = new Companion(null);
    public static boolean applyingMaml;
    public final Context context;
    public CurrentTemplateApi currentTemplateApi;
    public boolean isCurrentConfigChanged;
    public CommonConfig lastConfig;

    /* JADX INFO: compiled from: CurrentTemplateDataSource.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean getApplyingMaml() {
            return CurrentTemplateDataSource.applyingMaml;
        }

        /* JADX WARN: Code duplicated, block: B:37:0x0064  */
        public final boolean isConfigDiff(CommonConfig commonConfig, CommonConfig commonConfig2) {
            boolean z;
            TemplateConfig lockscreenInfo;
            TemplateConfig lockscreenInfo2;
            TemplateConfig lockscreenInfo3;
            if (commonConfig2 != null) {
                TemplateConfig lockscreenInfo4 = commonConfig2.getLockscreenInfo();
                DoodleInfo doodle = null;
                if (Intrinsics.areEqual(lockscreenInfo4 != null ? lockscreenInfo4.getClockInfo() : null, (commonConfig == null || (lockscreenInfo3 = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo3.getClockInfo())) {
                    TemplateConfig lockscreenInfo5 = commonConfig2.getLockscreenInfo();
                    if (Intrinsics.areEqual(lockscreenInfo5 != null ? lockscreenInfo5.getSignatureInfo() : null, (commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo2.getSignatureInfo())) {
                        TemplateConfig lockscreenInfo6 = commonConfig2.getLockscreenInfo();
                        DoodleInfo doodle2 = lockscreenInfo6 != null ? lockscreenInfo6.getDoodle() : null;
                        if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null) {
                            doodle = lockscreenInfo.getDoodle();
                        }
                        if (Intrinsics.areEqual(doodle2, doodle)) {
                            z = false;
                        } else {
                            z = true;
                        }
                    } else {
                        z = true;
                    }
                } else {
                    z = true;
                }
            } else {
                z = true;
            }
            Log.d("CurrentTemplateDataSource", "isConfigDiff: changed=" + z);
            return z;
        }

        public final boolean isWallpaperChangedByThirdApp(Context context, TemplateApi templateApi) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(templateApi, "templateApi");
            boolean zIsLockSetPkgSupportDepth = templateApi.isLockSetPkgSupportDepth();
            boolean zIsHomeSetPkgSupportDepth = templateApi.isHomeSetPkgSupportDepth();
            if (zIsLockSetPkgSupportDepth && zIsHomeSetPkgSupportDepth) {
                return false;
            }
            WallpaperController companion = WallpaperController.Companion.getInstance();
            if (companion != null && companion.isMiuiDefaultWallpaper()) {
                Log.d("CurrentTemplateDataSource", "isWallpaperChangedByThirdApp: setPkg null but is miui default wallpaper, skip");
                return false;
            }
            Log.d("CurrentTemplateDataSource", "isWallpaperChangedByThirdApp: lockSetByEditor=" + zIsLockSetPkgSupportDepth + ", homeSetByEditor=" + zIsHomeSetPkgSupportDepth + ", controllerReady=" + (companion != null));
            return true;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.CurrentTemplateDataSource$getCurrentTemplateFlow$1, reason: invalid class name */
    /* JADX INFO: compiled from: CurrentTemplateDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.CurrentTemplateDataSource$getCurrentTemplateFlow$1", f = "CurrentTemplateDataSource.kt", l = {32}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<FlowCollector<? super TemplateItemBean>, Continuation<? super Unit>, Object> {
        public /* synthetic */ Object L$0;
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = CurrentTemplateDataSource.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((AnonymousClass1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                TemplateItemBean currentTemplate = CurrentTemplateDataSource.this.getCurrentTemplate();
                this.label = 1;
                if (flowCollector.emit(currentTemplate, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.CurrentTemplateDataSource$getCurrentTemplateFlow$2, reason: invalid class name */
    /* JADX INFO: compiled from: CurrentTemplateDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.CurrentTemplateDataSource$getCurrentTemplateFlow$2", f = "CurrentTemplateDataSource.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function3<FlowCollector<? super TemplateItemBean>, Throwable, Continuation<? super Unit>, Object> {
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
            Log.e("CurrentTemplateDataSource", "currentTemplateTask error: " + ((Throwable) this.L$0));
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.CurrentTemplateDataSource$getCurrentTemplateFlow$3, reason: invalid class name */
    /* JADX INFO: compiled from: CurrentTemplateDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.CurrentTemplateDataSource$getCurrentTemplateFlow$3", f = "CurrentTemplateDataSource.kt", l = {36}, m = "invokeSuspend")
    public static final class AnonymousClass3 extends SuspendLambda implements Function2<FlowCollector<? super TemplateItemBean>, Continuation<? super Unit>, Object> {
        public /* synthetic */ Object L$0;
        public int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((AnonymousClass3) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                this.label = 1;
                if (flowCollector.emit(null, this) == coroutine_suspended) {
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

    public CurrentTemplateDataSource(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.currentTemplateApi = new CurrentTemplateApi(context);
        this.isCurrentConfigChanged = true;
    }

    public static final void refreshCurrentTemplateByProvider$lambda$1(CurrentTemplateDataSource currentTemplateDataSource) {
        try {
            long jUptimeMillis = SystemClock.uptimeMillis();
            currentTemplateDataSource.context.getContentResolver().call(Uri.parse("content://miui.keyguard.editor.templatefileprovider"), "callRefreshCurrentTemplate", (String) null, (Bundle) null);
            Log.i("CurrentTemplateDataSource", "refreshCurrentTemplateByProvider done, cost=" + (SystemClock.uptimeMillis() - jUptimeMillis) + "ms");
        } catch (Exception e) {
            Log.e("CurrentTemplateDataSource", "refreshCurrentTemplateByProvider failed: " + e);
        }
    }

    public final TemplateItemBean getCurrentTemplate() {
        WallpaperInfo wallpaperInfo;
        Log.i("CurrentTemplateDataSource", "currentTemplateTask start applyingMaml=" + applyingMaml);
        long jUptimeMillis = SystemClock.uptimeMillis();
        TemplateApi companion = TemplateApi.Companion.getInstance(this.context);
        Companion companion2 = Companion;
        if (companion2.isWallpaperChangedByThirdApp(this.context, companion) && !applyingMaml) {
            refreshCurrentTemplateByProvider();
        }
        CommonConfig commonConfigRequireCurrentTemplate$default = CurrentTemplateApi.requireCurrentTemplate$default(this.currentTemplateApi, null, 1, null);
        if (commonConfigRequireCurrentTemplate$default == null) {
            return null;
        }
        Log.i("CurrentTemplateDataSource", "currentTemplateTask completed, duration =" + (SystemClock.uptimeMillis() - jUptimeMillis) + ", currentTemplate = " + commonConfigRequireCurrentTemplate$default);
        boolean zIsConfigDiff = companion2.isConfigDiff(commonConfigRequireCurrentTemplate$default, this.lastConfig);
        this.isCurrentConfigChanged = zIsConfigDiff;
        Log.d("CurrentTemplateDataSource", "isConfigDiff: changed=" + zIsConfigDiff);
        this.lastConfig = commonConfigRequireCurrentTemplate$default;
        WallpaperInfo wallpaperInfo2 = commonConfigRequireCurrentTemplate$default.getLockscreenInfo().getWallpaperInfo();
        Bitmap currentWallpaper = wallpaperInfo2 != null ? wallpaperInfo2.getCurrentWallpaper() : null;
        WallpaperInfo wallpaperInfo3 = commonConfigRequireCurrentTemplate$default.getHomeInfo().getWallpaperInfo();
        boolean zSameAs = currentWallpaper != null ? currentWallpaper.sameAs(wallpaperInfo3 != null ? wallpaperInfo3.getCurrentWallpaper() : null) : false;
        Log.i("CurrentTemplateDataSource", "currentTemplateTask completed, lockBitmap and homeBitmap is same: " + zSameAs);
        if (zSameAs && (wallpaperInfo = commonConfigRequireCurrentTemplate$default.getHomeInfo().getWallpaperInfo()) != null) {
            wallpaperInfo.setCurrentWallpaper(currentWallpaper);
        }
        return new TemplateItemBean(1, 0, Integer.valueOf(R.string.kg_template_title_mine_collocation), null, null, null, commonConfigRequireCurrentTemplate$default, null, null, false, false, false, false, 8112, null);
    }

    public final Flow getCurrentTemplateFlow() {
        return FlowKt.flowOn(FlowKt.onEmpty(FlowKt.m1255catch(FlowKt.flow(new AnonymousClass1(null)), new AnonymousClass2(null)), new AnonymousClass3(null)), Dispatchers.getIO());
    }

    public final boolean isCurrentConfigChanged() {
        return this.isCurrentConfigChanged;
    }

    public final void refreshCurrentTemplateByProvider() {
        Log.i("CurrentTemplateDataSource", "refreshCurrentTemplateByProvider: dispatch async");
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.settings.data.CurrentTemplateDataSource$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CurrentTemplateDataSource.refreshCurrentTemplateByProvider$lambda$1(this.f$0);
            }
        });
    }
}
