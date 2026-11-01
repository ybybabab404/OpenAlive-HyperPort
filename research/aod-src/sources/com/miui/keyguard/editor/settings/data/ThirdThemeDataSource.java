package com.miui.keyguard.editor.settings.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.CommonUtils;
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
/* JADX INFO: compiled from: ThirdThemeDataSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ThirdThemeDataSource {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final TemplateApi dataApi;

    /* JADX INFO: compiled from: ThirdThemeDataSource.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.ThirdThemeDataSource$getThirdThemeTemplateFlow$1, reason: invalid class name */
    /* JADX INFO: compiled from: ThirdThemeDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.ThirdThemeDataSource$getThirdThemeTemplateFlow$1", f = "ThirdThemeDataSource.kt", l = {34}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<FlowCollector<? super TemplateItemBean>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ int $previewHeight;
        public final /* synthetic */ int $previewWidth;
        public /* synthetic */ Object L$0;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int i, int i2, Continuation continuation) {
            super(2, continuation);
            this.$previewWidth = i;
            this.$previewHeight = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = ThirdThemeDataSource.this.new AnonymousClass1(this.$previewWidth, this.$previewHeight, continuation);
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
                TemplateItemBean thirdThemeTemplate = ThirdThemeDataSource.this.getThirdThemeTemplate(this.$previewWidth, this.$previewHeight);
                this.label = 1;
                if (flowCollector.emit(thirdThemeTemplate, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.ThirdThemeDataSource$getThirdThemeTemplateFlow$2, reason: invalid class name */
    /* JADX INFO: compiled from: ThirdThemeDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.ThirdThemeDataSource$getThirdThemeTemplateFlow$2", f = "ThirdThemeDataSource.kt", l = {}, m = "invokeSuspend")
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
            Log.e("ThirdPartyDataSource", "thirdPartyThemeTask error: " + ((Throwable) this.L$0));
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.data.ThirdThemeDataSource$getThirdThemeTemplateFlow$3, reason: invalid class name */
    /* JADX INFO: compiled from: ThirdThemeDataSource.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.data.ThirdThemeDataSource$getThirdThemeTemplateFlow$3", f = "ThirdThemeDataSource.kt", l = {38}, m = "invokeSuspend")
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

    public ThirdThemeDataSource(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.dataApi = TemplateApi.Companion.getInstance(context);
    }

    public final TemplateItemBean getThirdThemeTemplate(int i, int i2) {
        if (this.dataApi.isThirdPartyTheme()) {
            Log.i("ThirdPartyDataSource", "thirdPartyThemeTask start");
            Bundle bundle = new Bundle();
            bundle.putInt("file_type", 4);
            bundle.putString("file_mode", "r");
            Bundle bundleCall = this.context.getContentResolver().call(Uri.parse("content://miui.keyguard.editor.templatefileprovider"), "callGetFileDescriptorByFileName", TemplateFilePathGenerator.TemplateFileType.THIRD_PARTY_THEME_PREVIEW.getFileName(), bundle);
            ParcelFileDescriptor parcelFileDescriptor = bundleCall != null ? (ParcelFileDescriptor) bundleCall.getParcelable("parcelFileDescriptor") : null;
            Bitmap bitmapParcelFileDescriptorToBitmap$default = parcelFileDescriptor != null ? BitmapUtil.parcelFileDescriptorToBitmap$default(BitmapUtil.INSTANCE, parcelFileDescriptor, i, i2, null, null, 24, null) : null;
            Bitmap homeWallpaperPreview = WallpaperController.Companion.getInstance(this.context).getHomeWallpaperPreview(this.context);
            Log.i("ThirdPartyDataSource", "thirdPartyThemeSupplier previewImage=" + bitmapParcelFileDescriptorToBitmap$default + ", homePreviewBitmap=" + homeWallpaperPreview + "}");
            boolean zIsDarkWallpaper = CommonUtils.INSTANCE.isDarkWallpaper(homeWallpaperPreview);
            if (bitmapParcelFileDescriptorToBitmap$default != null) {
                TemplateItemBean templateItemBean = new TemplateItemBean(4, 0, Integer.valueOf(R.string.kg_template_title_mine), null, null, null, null, bitmapParcelFileDescriptorToBitmap$default, homeWallpaperPreview, zIsDarkWallpaper, false, false, false, 7280, null);
                Log.i("ThirdPartyDataSource", "thirdPartyThemeTask completed");
                return templateItemBean;
            }
            Log.i("ThirdPartyDataSource", "thirdPartyThemeTask completed templateItemBean is null");
        }
        return null;
    }

    public final Flow getThirdThemeTemplateFlow(int i, int i2) {
        return FlowKt.flowOn(FlowKt.onEmpty(FlowKt.m1255catch(FlowKt.flow(new AnonymousClass1(i, i2, null)), new AnonymousClass2(null)), new AnonymousClass3(null)), Dispatchers.getIO());
    }
}
