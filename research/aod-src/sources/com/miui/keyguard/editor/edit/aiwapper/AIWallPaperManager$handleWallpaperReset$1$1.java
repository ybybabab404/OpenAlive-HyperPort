package com.miui.keyguard.editor.edit.aiwapper;

import android.content.Context;
import android.graphics.Bitmap;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.utils.BitmapUtil;
import java.io.File;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CoroutineScope;

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
/* JADX INFO: compiled from: AIWallPaperManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$handleWallpaperReset$1$1", f = "AIWallPaperManager.kt", l = {624, 630}, m = "invokeSuspend")
public final class AIWallPaperManager$handleWallpaperReset$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Context $it;
    public Object L$0;
    public int label;
    public final /* synthetic */ AIWallPaperManager this$0;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$handleWallpaperReset$1$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: AIWallPaperManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$handleWallpaperReset$1$1$1", f = "AIWallPaperManager.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $it;
        public final /* synthetic */ Ref.ObjectRef $originBitmap;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Context context, Ref.ObjectRef objectRef, Continuation continuation) {
            super(2, continuation);
            this.$it = context;
            this.$originBitmap = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$it, this.$originBitmap, continuation);
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
            String str = this.$it.getCacheDir().getAbsolutePath() + "ai_origin";
            if (new File(str).exists()) {
                this.$originBitmap.element = BitmapUtil.INSTANCE.resizeImageWallpaper(str);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$handleWallpaperReset$1$1$2, reason: invalid class name */
    /* JADX INFO: compiled from: AIWallPaperManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$handleWallpaperReset$1$1$2", f = "AIWallPaperManager.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Ref.ObjectRef $originBitmap;
        public int label;
        public final /* synthetic */ AIWallPaperManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(AIWallPaperManager aIWallPaperManager, Ref.ObjectRef objectRef, Continuation continuation) {
            super(2, continuation);
            this.this$0 = aIWallPaperManager;
            this.$originBitmap = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, this.$originBitmap, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Integer videoType;
            WallpaperTypeInfo wallpaperTypeInfo;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            WallpaperTypeInfo wallpaperTypeInfo2 = this.this$0.mOriginWallpaperInfo;
            if (wallpaperTypeInfo2 != null) {
                wallpaperTypeInfo2.setBitmap((Bitmap) this.$originBitmap.element);
            }
            WallpaperTypeInfo wallpaperTypeInfo3 = this.this$0.mOriginWallpaperInfo;
            if (wallpaperTypeInfo3 != null && (videoType = wallpaperTypeInfo3.getVideoType()) != null && 2 == videoType.intValue() && (wallpaperTypeInfo = this.this$0.mOriginWallpaperInfo) != null) {
                wallpaperTypeInfo.setType("video");
            }
            IAIWallPaperChangeListener templateView = this.this$0.getTemplateView();
            if (templateView != null) {
                templateView.onWallPaperReset(this.this$0.mOriginWallpaperInfo);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AIWallPaperManager$handleWallpaperReset$1$1(Context context, AIWallPaperManager aIWallPaperManager, Continuation continuation) {
        super(2, continuation);
        this.$it = context;
        this.this$0 = aIWallPaperManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AIWallPaperManager$handleWallpaperReset$1$1(this.$it, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AIWallPaperManager$handleWallpaperReset$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0054, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r8, r3, r7) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            r2 = 2
            r3 = 1
            r4 = 0
            if (r1 == 0) goto L23
            if (r1 == r3) goto L1b
            if (r1 != r2) goto L13
            kotlin.ResultKt.throwOnFailure(r8)
            goto L57
        L13:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1b:
            java.lang.Object r1 = r7.L$0
            kotlin.jvm.internal.Ref$ObjectRef r1 = (kotlin.jvm.internal.Ref.ObjectRef) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L41
        L23:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlin.jvm.internal.Ref$ObjectRef r1 = new kotlin.jvm.internal.Ref$ObjectRef
            r1.<init>()
            kotlinx.coroutines.CoroutineDispatcher r8 = kotlinx.coroutines.Dispatchers.getIO()
            com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$handleWallpaperReset$1$1$1 r5 = new com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$handleWallpaperReset$1$1$1
            android.content.Context r6 = r7.$it
            r5.<init>(r6, r1, r4)
            r7.L$0 = r1
            r7.label = r3
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r8, r5, r7)
            if (r8 != r0) goto L41
            goto L56
        L41:
            kotlinx.coroutines.MainCoroutineDispatcher r8 = kotlinx.coroutines.Dispatchers.getMain()
            com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$handleWallpaperReset$1$1$2 r3 = new com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$handleWallpaperReset$1$1$2
            com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager r5 = r7.this$0
            r3.<init>(r5, r1, r4)
            r7.L$0 = r4
            r7.label = r2
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r8, r3, r7)
            if (r7 != r0) goto L57
        L56:
            return r0
        L57:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$handleWallpaperReset$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
