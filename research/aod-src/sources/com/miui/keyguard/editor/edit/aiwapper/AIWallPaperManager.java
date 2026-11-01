package com.miui.keyguard.editor.edit.aiwapper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ExtraInfo;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.video.VideoCheckUtils;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.MainCoroutineDispatcher;
import miuix.device.DeviceUtils;
import miuix.os.Build;
import miuix.os.DeviceHelper;

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
@SourceDebugExtension
public final class AIWallPaperManager {
    public static final Companion Companion = new Companion(null);
    public static volatile AIWallPaperManager instance;
    public volatile String mAiStyle;
    public boolean mCanSwitchDynamicStatic;
    public WeakReference mContextRef;
    public boolean mCurrentIsOrigin;
    public volatile String mCurrentTaskId;
    public AIGenerateFinishCallback mFinishCallBack;
    public final AtomicBoolean mIsRunning;
    public boolean mLivePhoto;
    public volatile String mLocalIdentify;
    public volatile String mLocalPath;
    public Boolean mOriginIsVideo;
    public String mOriginResourcePath;
    public WallpaperTypeInfo mOriginWallpaperInfo;
    public Job mStatusCheckJob;
    public int mStatusCode;
    public WeakReference mTemplateViewRef;

    /* JADX INFO: compiled from: AIWallPaperManager.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final AIWallPaperManager getInstance() {
            AIWallPaperManager aIWallPaperManager;
            AIWallPaperManager aIWallPaperManager2 = AIWallPaperManager.instance;
            if (aIWallPaperManager2 != null) {
                return aIWallPaperManager2;
            }
            synchronized (this) {
                aIWallPaperManager = AIWallPaperManager.instance;
                if (aIWallPaperManager == null) {
                    aIWallPaperManager = new AIWallPaperManager(null);
                    AIWallPaperManager.instance = aIWallPaperManager;
                }
            }
            return aIWallPaperManager;
        }
    }

    /* JADX INFO: compiled from: AIWallPaperManager.kt */
    @Metadata
    public static final class Constant {
        public static final Constant INSTANCE = new Constant();

        public final HashSet getCNDeviceMap() {
            return SetsKt.hashSetOf("haotian", "dada", "muyu", "uke", "rothko", "duchamp", "ruyi", "goku", "aurora", "shennong", "shennong_t", "houji", "manet", "vermeer", "sheng", "onyx", "rodin", "xuanyuan", "zorn", "miro", "dijun", "jinghu", "chenfeng", "peridot", "corot", "ishtar", "nuwa", "fuxi", "socrates", "babylon", "violin", "bixi", "luming", "klimt", "dali", "goya", "turner", "nezha", "popsicle", "pudding", "piano", "pandora", "yupei", "annibale", "myron", "dash", "klee", "pecan", "pixiu", "prague", "yili", "warhol", "chagall", "byron", "lhasa", "warsaw", "athens", "songyuan", "madrid", "hongkong", "shuntian", "yingtian", "venice");
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$checkVideoFormat$2, reason: invalid class name */
    /* JADX INFO: compiled from: AIWallPaperManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$checkVideoFormat$2", f = "AIWallPaperManager.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
        public final /* synthetic */ String $videoPath;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(String str, Continuation continuation) {
            super(2, continuation);
            this.$videoPath = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$videoPath, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:34:0x00ad  */
        /* JADX WARN: Not initialized variable reg: 2, insn: 0x0079: MOVE (r1 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:20:0x0079 */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            MediaExtractor mediaExtractor;
            Exception e;
            MediaExtractor mediaExtractor2;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            MediaExtractor mediaExtractor3 = null;
            try {
                try {
                    mediaExtractor = new MediaExtractor();
                    try {
                        mediaExtractor.setDataSource(this.$videoPath);
                        VideoCheckUtils.Companion companion = VideoCheckUtils.Companion;
                        MediaFormat videoFormat = companion.getVideoFormat(mediaExtractor);
                        if (videoFormat == null) {
                            throw new IllegalStateException("Not Found videoTrack");
                        }
                        boolean zIsHorizontalVideo = companion.isHorizontalVideo(videoFormat);
                        int videoFrameRate = companion.getVideoFrameRate(videoFormat);
                        long videoDuration = companion.getVideoDuration(videoFormat);
                        boolean z = !zIsHorizontalVideo && videoDuration <= 6000000 && videoFrameRate <= 30;
                        Log.i("AIWallPaperManager", "Video check result: valid=" + z + ", portrait=" + (true ^ zIsHorizontalVideo) + ", duration=" + videoDuration + "ms, FrameRate=" + videoFrameRate + "}");
                        mediaExtractor.release();
                        return Boxing.boxBoolean(z);
                    } catch (Exception e2) {
                        e = e2;
                        Log.e("AIWallPaperManager", "Error checking video format: " + this.$videoPath, e);
                        Boolean boolBoxBoolean = Boxing.boxBoolean(false);
                        if (mediaExtractor != null) {
                            mediaExtractor.release();
                        }
                        return boolBoxBoolean;
                    }
                } catch (Throwable th) {
                    th = th;
                    mediaExtractor3 = mediaExtractor2;
                    if (mediaExtractor3 != null) {
                        mediaExtractor3.release();
                    }
                    throw th;
                }
            } catch (Exception e3) {
                mediaExtractor = null;
                e = e3;
            } catch (Throwable th2) {
                th = th2;
                if (mediaExtractor3 != null) {
                    mediaExtractor3.release();
                }
                throw th;
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$getAiGenerateStatus$1, reason: invalid class name */
    /* JADX INFO: compiled from: AIWallPaperManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager", f = "AIWallPaperManager.kt", l = {512}, m = "getAiGenerateStatus")
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
            return AIWallPaperManager.this.getAiGenerateStatus(null, null, false, false, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$startAIGenerate$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIWallPaperManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$startAIGenerate$2", f = "AIWallPaperManager.kt", l = {269, 287}, m = "invokeSuspend")
    public static final class C00942 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public final /* synthetic */ AIGenerateFinishCallback $finishCallBack;
        public final /* synthetic */ Fragment $fragment;
        public final /* synthetic */ boolean $isFromLock;
        public final /* synthetic */ WallpaperPositionInfo $positionInfo;
        public final /* synthetic */ WallpaperInfo $wallpaperInfo;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$startAIGenerate$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: AIWallPaperManager.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$startAIGenerate$2$1", f = "AIWallPaperManager.kt", l = {}, m = "invokeSuspend")
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ Context $context;
            public int label;
            public final /* synthetic */ AIWallPaperManager this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(AIWallPaperManager aIWallPaperManager, Context context, Continuation continuation) {
                super(2, continuation);
                this.this$0 = aIWallPaperManager;
                this.$context = context;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, this.$context, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                WallpaperTypeInfo wallpaperTypeInfo;
                Bitmap bitmap;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                WallpaperTypeInfo wallpaperTypeInfo2 = this.this$0.mOriginWallpaperInfo;
                if (wallpaperTypeInfo2 != null && (bitmap = wallpaperTypeInfo2.getBitmap()) != null) {
                    Boxing.boxBoolean(BitmapUtil.INSTANCE.toPNG(bitmap, this.$context.getCacheDir().getAbsolutePath() + "ai_origin"));
                }
                if (!Intrinsics.areEqual(this.this$0.getMOriginIsVideo(), Boxing.boxBoolean(true)) && (wallpaperTypeInfo = this.this$0.mOriginWallpaperInfo) != null) {
                    wallpaperTypeInfo.setBitmap(null);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00942(WallpaperInfo wallpaperInfo, AIGenerateFinishCallback aIGenerateFinishCallback, Fragment fragment, WallpaperPositionInfo wallpaperPositionInfo, boolean z, Context context, Continuation continuation) {
            super(2, continuation);
            this.$wallpaperInfo = wallpaperInfo;
            this.$finishCallBack = aIGenerateFinishCallback;
            this.$fragment = fragment;
            this.$positionInfo = wallpaperPositionInfo;
            this.$isFromLock = z;
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AIWallPaperManager.this.new C00942(this.$wallpaperInfo, this.$finishCallBack, this.$fragment, this.$positionInfo, this.$isFromLock, this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C00942) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:47:0x00ec, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(r0, r1, r15) == r11) goto L48;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r16) throws java.io.IOException {
            /*
                Method dump skipped, instruction units count: 242
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager.C00942.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$startAIGenerateReal$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIWallPaperManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager", f = "AIWallPaperManager.kt", l = {775}, m = "startAIGenerateReal")
    public static final class C00951 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public int label;
        public /* synthetic */ Object result;

        public C00951(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AIWallPaperManager.this.startAIGenerateReal(null, null, null, null, 0, false, false, null, false, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$startAIGenerateReal$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIWallPaperManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$startAIGenerateReal$2", f = "AIWallPaperManager.kt", l = {}, m = "invokeSuspend")
    public static final class C00962 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Fragment $fragment;
        public final /* synthetic */ int $inSampleSize;
        public final /* synthetic */ boolean $isFromLock;
        public final /* synthetic */ boolean $isLivePhoto;
        public final /* synthetic */ boolean $isVideo;
        public final /* synthetic */ WallpaperPositionInfo $positionInfo;
        public final /* synthetic */ String $taskId;
        public final /* synthetic */ Uri $uri;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00962(Fragment fragment, String str, Uri uri, WallpaperPositionInfo wallpaperPositionInfo, int i, boolean z, boolean z2, boolean z3, Continuation continuation) {
            super(2, continuation);
            this.$fragment = fragment;
            this.$taskId = str;
            this.$uri = uri;
            this.$positionInfo = wallpaperPositionInfo;
            this.$inSampleSize = i;
            this.$isFromLock = z;
            this.$isLivePhoto = z2;
            this.$isVideo = z3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C00962(this.$fragment, this.$taskId, this.$uri, this.$positionInfo, this.$inSampleSize, this.$isFromLock, this.$isLivePhoto, this.$isVideo, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C00962) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ThemeApiWrapper.INSTANCE.startAIGenerate(this.$fragment, this.$taskId, this.$uri, this.$positionInfo, this.$inSampleSize, this.$isFromLock, this.$isLivePhoto, this.$isVideo);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$startAIGenerateReal$3, reason: invalid class name */
    /* JADX INFO: compiled from: AIWallPaperManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$startAIGenerateReal$3", f = "AIWallPaperManager.kt", l = {789}, m = "invokeSuspend")
    public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ FragmentActivity $activity;
        public final /* synthetic */ AIGenerateFinishCallback $finishCallBack;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(FragmentActivity fragmentActivity, AIGenerateFinishCallback aIGenerateFinishCallback, Continuation continuation) {
            super(2, continuation);
            this.$activity = fragmentActivity;
            this.$finishCallBack = aIGenerateFinishCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AIWallPaperManager.this.new AnonymousClass3(this.$activity, this.$finishCallBack, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                AIWallPaperManager aIWallPaperManager = AIWallPaperManager.this;
                FragmentActivity fragmentActivity = this.$activity;
                AIGenerateFinishCallback aIGenerateFinishCallback = this.$finishCallBack;
                this.label = 1;
                if (aIWallPaperManager.startStatusCheck(fragmentActivity, aIGenerateFinishCallback, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$startStatusCheck$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIWallPaperManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager", f = "AIWallPaperManager.kt", l = {821}, m = "startStatusCheck")
    public static final class C00971 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public int label;
        public /* synthetic */ Object result;

        public C00971(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AIWallPaperManager.this.startStatusCheck(null, null, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$updateAiGenerateStatusCode$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIWallPaperManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager", f = "AIWallPaperManager.kt", l = {374}, m = "updateAiGenerateStatusCode")
    public static final class C00981 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public int label;
        public /* synthetic */ Object result;

        public C00981(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AIWallPaperManager.this.updateAiGenerateStatusCode(null, null, false, false, this);
        }
    }

    public AIWallPaperManager() {
        this.mIsRunning = new AtomicBoolean(false);
        this.mCurrentIsOrigin = true;
        this.mStatusCode = 50010;
    }

    public /* synthetic */ AIWallPaperManager(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public static final CharSequence generateTaskId$lambda$9$lambda$8$lambda$7(byte b) {
        String str = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(b)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    public static final Unit startStatusCheck$lambda$12(AIWallPaperManager aIWallPaperManager, AIGenerateFinishCallback aIGenerateFinishCallback, Bundle data) {
        Intrinsics.checkNotNullParameter(data, "data");
        String string = data.getString("aiTaskStatus");
        Log.d("AIWallPaperManager", "轮询检查AI生成任务是否完成  status： " + string + " localIdentify: " + aIWallPaperManager.mLocalIdentify + " localPath: " + aIWallPaperManager.mLocalPath);
        if (Intrinsics.areEqual(string, "Complete")) {
            aIWallPaperManager.stopStatusCheck();
            aIGenerateFinishCallback.generateFinish(true);
        } else if (Intrinsics.areEqual(string, "Error")) {
            aIWallPaperManager.stopStatusCheck();
            aIGenerateFinishCallback.generateFinish(false);
        }
        return Unit.INSTANCE;
    }

    public final Object checkVideoFormat(String str, Continuation continuation) {
        if (str != null && str.length() != 0 && new File(str).exists()) {
            return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(str, null), continuation);
        }
        Log.w("AIWallPaperManager", "Video file not found: " + str);
        return Boxing.boxBoolean(false);
    }

    public final String generateTaskId(boolean z) throws IOException {
        String strEncodeMD5;
        String strJoinToString$default;
        WallpaperPositionInfo positionInfo;
        WallpaperPositionInfo positionInfo2;
        Bitmap bitmap;
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        if (z) {
            MediaExtractor mediaExtractor = new MediaExtractor();
            try {
                try {
                    String str = this.mOriginResourcePath;
                    if (str != null) {
                        mediaExtractor.setDataSource(str);
                        VideoCheckUtils.Companion companion = VideoCheckUtils.Companion;
                        MediaFormat videoFormat = companion.getVideoFormat(mediaExtractor);
                        objectRef.element = videoFormat != null ? Long.valueOf(companion.getVideoDuration(videoFormat)) : null;
                    }
                    mediaExtractor.release();
                    WallpaperTypeInfo wallpaperTypeInfo = this.mOriginWallpaperInfo;
                    if (wallpaperTypeInfo == null || (bitmap = wallpaperTypeInfo.getBitmap()) == null) {
                        strJoinToString$default = null;
                    } else {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        try {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                            try {
                                byte[] bArrDigest = MessageDigest.getInstance("MD5").digest(byteArrayOutputStream.toByteArray());
                                Intrinsics.checkNotNullExpressionValue(bArrDigest, "digest(...)");
                                strJoinToString$default = ArraysKt.joinToString$default(bArrDigest, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1() { // from class: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        return AIWallPaperManager.generateTaskId$lambda$9$lambda$8$lambda$7(((Byte) obj).byteValue());
                                    }
                                }, 30, (Object) null);
                            } catch (Exception e) {
                                Log.e("AIWallPaperManager", "MD5 algorithm not available", e);
                                strJoinToString$default = "";
                            }
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(byteArrayOutputStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(byteArrayOutputStream, th);
                                throw th2;
                            }
                        }
                    }
                    StringBuilder sb = new StringBuilder();
                    WallpaperTypeInfo wallpaperTypeInfo2 = this.mOriginWallpaperInfo;
                    String resourcePath = wallpaperTypeInfo2 != null ? wallpaperTypeInfo2.getResourcePath() : null;
                    if (resourcePath == null) {
                        resourcePath = "";
                    }
                    sb.append(resourcePath);
                    WallpaperTypeInfo wallpaperTypeInfo3 = this.mOriginWallpaperInfo;
                    sb.append((wallpaperTypeInfo3 == null || (positionInfo2 = wallpaperTypeInfo3.getPositionInfo()) == null) ? null : Integer.valueOf(positionInfo2.hashCodeWithPrecisionCorrection()));
                    sb.append(strJoinToString$default != null ? strJoinToString$default : "");
                    sb.append(String.valueOf(objectRef.element));
                    strEncodeMD5 = FileUtil.encodeMD5(sb.toString());
                } catch (Exception e2) {
                    Log.e("AIWallPaperManager", "Error getting video duration", e2);
                    mediaExtractor.release();
                    return "";
                }
            } catch (Throwable th3) {
                mediaExtractor.release();
                throw th3;
            }
        } else {
            WallpaperTypeInfo wallpaperTypeInfo4 = this.mOriginWallpaperInfo;
            String resourcePath2 = wallpaperTypeInfo4 != null ? wallpaperTypeInfo4.getResourcePath() : null;
            WallpaperTypeInfo wallpaperTypeInfo5 = this.mOriginWallpaperInfo;
            strEncodeMD5 = FileUtil.encodeMD5(resourcePath2 + ((wallpaperTypeInfo5 == null || (positionInfo = wallpaperTypeInfo5.getPositionInfo()) == null) ? null : Integer.valueOf(positionInfo.hashCodeWithPrecisionCorrection())));
            strJoinToString$default = null;
        }
        WallpaperTypeInfo wallpaperTypeInfo6 = this.mOriginWallpaperInfo;
        String resourcePath3 = wallpaperTypeInfo6 != null ? wallpaperTypeInfo6.getResourcePath() : null;
        WallpaperTypeInfo wallpaperTypeInfo7 = this.mOriginWallpaperInfo;
        WallpaperPositionInfo positionInfo3 = wallpaperTypeInfo7 != null ? wallpaperTypeInfo7.getPositionInfo() : null;
        Log.i("AIWallPaperManager", "taskId=" + strEncodeMD5 + ", resourcePath=" + resourcePath3 + ",positionInfo=" + positionInfo3 + ", imageMd5=" + ((Object) strJoinToString$default) + ", duration=" + objectRef.element + " ");
        this.mCurrentTaskId = strEncodeMD5;
        return strEncodeMD5;
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0014  */
    public final Object getAiGenerateStatus(Context context, WallpaperInfo wallpaperInfo, boolean z, boolean z2, Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        Context context2;
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
        AnonymousClass1 anonymousClass2 = anonymousClass1;
        Object objUpdateAiGenerateStatusCode = anonymousClass2.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass2.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objUpdateAiGenerateStatusCode);
            if (isGenerateRunning()) {
                return Boxing.boxInt(1);
            }
            WeakReference weakReference = this.mContextRef;
            if (weakReference != null && (context2 = (Context) weakReference.get()) != null) {
                this.mOriginResourcePath = getGeneratingFilePath(context2, wallpaperInfo);
            }
            anonymousClass2.label = 1;
            objUpdateAiGenerateStatusCode = updateAiGenerateStatusCode(context, wallpaperInfo, z, z2, anonymousClass2);
            if (objUpdateAiGenerateStatusCode == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objUpdateAiGenerateStatusCode);
        }
        int iIntValue = ((Number) objUpdateAiGenerateStatusCode).intValue();
        if (iIntValue == 40010 || iIntValue == -1 || iIntValue == 40011) {
            return Boxing.boxInt(3);
        }
        return ArraysKt.contains(AiWallPaperStatus.INSTANCE.getDISABLE_ERROR_CODE_ARRAY(), iIntValue) ? Boxing.boxInt(2) : Boxing.boxInt(1);
    }

    public final Triple getDisableTips(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int i = this.mStatusCode;
        if (i == 1008) {
            return new Triple(context.getString(R.string.kg_dialog_ai_blocking_for_image_not_clear_title), context.getString(R.string.kg_dialog_ai_blocking_for_image_not_clear), Boolean.FALSE);
        }
        if (i == 1011) {
            return new Triple(context.getString(R.string.kg_dialog_ai_blocking_for_content_violation_title), context.getString(R.string.kg_dialog_ai_blocking_for_content_violation), Boolean.TRUE);
        }
        if (i == 1016) {
            return new Triple(context.getString(R.string.kg_dialog_ai_blocking_for_account_abnormal_title), context.getString(R.string.kg_dialog_ai_blocking_for_account_abnormal), Boolean.FALSE);
        }
        if (i == 1019) {
            return new Triple(context.getString(R.string.kg_dialog_ai_blocking_for_service_unavailable_title), context.getString(R.string.kg_dialog_ai_blocking_for_service_unavailable), Boolean.FALSE);
        }
        if (i == 1024) {
            return new Triple(null, context.getString(R.string.kg_dialog_ai_blocking_for_resolution_too_small, 300, 667), Boolean.FALSE);
        }
        if (i == 20030) {
            return new Triple(null, context.getString(R.string.kg_dialog_ai_blocking_for_too_large, 50), Boolean.FALSE);
        }
        if (i == 20040) {
            return new Triple(null, context.getString(R.string.kg_dialog_ai_blocking_for_ai_generated), Boolean.FALSE);
        }
        switch (i) {
            case 40012:
                return new Triple(null, context.getString(R.string.kg_dialog_ai_blocking_for_not_support_video), Boolean.FALSE);
            case 40013:
                return new Triple(null, context.getString(R.string.kg_dialog_ai_blocking_for_not_support_wallpaper), Boolean.FALSE);
            default:
                return null;
        }
    }

    public final String getGeneratingFilePath(Context context, WallpaperInfo wallpaperInfo) {
        WallpaperTypeInfo wallpaperTypeInfo = this.mOriginWallpaperInfo;
        String resourcePath = wallpaperTypeInfo != null ? wallpaperTypeInfo.getResourcePath() : null;
        if (Intrinsics.areEqual(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, "image") && wallpaperInfo.getVideoType() == 2) {
            ExtraInfo extraInfo = wallpaperInfo.getExtraInfo();
            resourcePath = extraInfo != null ? extraInfo.getOriginLivePhotoImagePath() : null;
        } else {
            if (Intrinsics.areEqual(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, "video") && wallpaperInfo.getVideoType() == 0 && !wallpaperInfo.isAiGenerated()) {
                resourcePath = wallpaperInfo.getOriginResourcePath();
            } else if (!FileUtil.isFileExist(resourcePath)) {
                resourcePath = TemplateApi.Companion.getInstance(context).currentWallPaperPath(wallpaperInfo);
            }
        }
        return resourcePath == null ? "" : resourcePath;
    }

    public final Boolean getMOriginIsVideo() {
        return this.mOriginIsVideo;
    }

    public final String getMOriginResourcePath() {
        return this.mOriginResourcePath;
    }

    public final IAIWallPaperChangeListener getTemplateView() {
        WeakReference weakReference = this.mTemplateViewRef;
        if (weakReference != null) {
            return (IAIWallPaperChangeListener) weakReference.get();
        }
        return null;
    }

    public final void handleGenerateFailed(Bundle bundle) {
        String string;
        Triple triple = null;
        String string2 = bundle != null ? bundle.getString("taskId") : null;
        int i = (bundle == null || (string = bundle.getString(AIKeys.KEY_ERROR_CODE)) == null) ? 50010 : Integer.parseInt(string);
        if (isOnceGenerate(string2) || 4800 == i) {
            this.mStatusCode = i;
            if (ArraysKt.contains(AiWallPaperStatus.INSTANCE.getNEED_ALTER_ERROR_CODE_ARRAY(), this.mStatusCode)) {
                triple = new Triple(bundle != null ? bundle.getString("error_title") : null, bundle != null ? bundle.getString("error_content") : null, Boolean.valueOf(this.mStatusCode == 1011));
            }
            stopStatusCheck();
            IAIWallPaperChangeListener templateView = getTemplateView();
            if (templateView != null) {
                templateView.handleGenerateFailed(triple, this.mStatusCode);
            }
            Log.i("AIWallPaperManager", "handleGenerateFailed " + this.mStatusCode);
        }
    }

    public final void handleSelectedStyle(Bundle bundle) {
        this.mAiStyle = bundle != null ? bundle.getString("aiStyle") : null;
        Log.i("AIWallPaperManager", "handleSelectedStyle aiStyle：" + this.mAiStyle);
    }

    public final void handleUpdateTaskInfo(Bundle bundle) {
        this.mLocalIdentify = bundle != null ? bundle.getString("localIdentify") : null;
        this.mLocalPath = bundle != null ? bundle.getString("localPath") : null;
        Log.i("AIWallPaperManager", "handleUpdateTaskInfo mLocalIdentify：" + this.mLocalIdentify + " mLocalPath:" + this.mLocalPath);
    }

    public final void handleWallpaperChange(Bundle bundle) {
        Intent intent = bundle != null ? (Intent) bundle.getParcelable("data") : null;
        if (intent == null) {
            intent = null;
        }
        if (isOnceGenerate(intent != null ? intent.getStringExtra("taskId") : null)) {
            stopStatusCheck();
            Log.i("AIWallPaperManager", "handleWallpaperChange");
            Intent intent2 = bundle != null ? (Intent) bundle.getParcelable("data") : null;
            Intent intent3 = intent2 != null ? intent2 : null;
            if (intent3 != null) {
                intent3.putExtra("wallpaper_from", "from_ai_generate");
            }
            IAIWallPaperChangeListener templateView = getTemplateView();
            if (templateView != null) {
                templateView.onWallPaperChange(bundle);
            }
            this.mCurrentIsOrigin = false;
        }
    }

    public final void handleWallpaperReset() {
        Context context;
        stopStatusCheck();
        if (this.mCurrentIsOrigin) {
            AIGenerateFinishCallback aIGenerateFinishCallback = this.mFinishCallBack;
            if (aIGenerateFinishCallback != null) {
                aIGenerateFinishCallback.generateFinish(true);
                return;
            }
            return;
        }
        WeakReference weakReference = this.mContextRef;
        if (weakReference == null || (context = (Context) weakReference.get()) == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), null, null, new AIWallPaperManager$handleWallpaperReset$1$1(context, this, null), 3, null);
    }

    public final void init(Context context, IAIWallPaperChangeListener templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.mTemplateViewRef = new WeakReference(templateView);
        this.mContextRef = new WeakReference(context);
        stopStatusCheck();
    }

    public final boolean isAiGeneratedImage(WallpaperInfo wallpaperInfo) {
        return wallpaperInfo.isAiGenerated() && !wallpaperInfo.isDirectGenerate();
    }

    public final boolean isGenerateRunning() {
        return this.mIsRunning.get();
    }

    public final boolean isInAiGenerationProcess(WallpaperInfo wallpaperInfo) {
        return wallpaperInfo.isDirectGenerate();
    }

    public final boolean isLivePhoto(WallpaperInfo wallpaperInfo) {
        return Intrinsics.areEqual(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, "video") && wallpaperInfo.getVideoType() == 2;
    }

    public final boolean isOnceGenerate(String str) {
        if (!isGenerateRunning()) {
            Log.i("AIWallPaperManager", "reject isGenerateRunning + " + isGenerateRunning());
            return false;
        }
        if (Intrinsics.areEqual(str, this.mCurrentTaskId)) {
            return true;
        }
        Log.i("AIWallPaperManager", "taskId: " + str + " mCurrentTaskId: " + this.mCurrentTaskId);
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0021  */
    public final Object isSupportType(WallpaperInfo wallpaperInfo, Continuation continuation) {
        String resourceType = wallpaperInfo.getResourceType();
        boolean z = true;
        if (!Intrinsics.areEqual(resourceType, "image")) {
            if (Intrinsics.areEqual(resourceType, "video")) {
                int videoType = wallpaperInfo.getVideoType();
                if (videoType == 0) {
                    return checkVideoFormat(this.mOriginResourcePath, continuation);
                }
                if (videoType != 2) {
                    z = false;
                }
            } else {
                z = false;
            }
        }
        return Boxing.boxBoolean(z);
    }

    public final boolean isSupportedDevice() {
        if (Build.IS_INTERNATIONAL_BUILD) {
            return false;
        }
        return Constant.INSTANCE.getCNDeviceMap().contains(DeviceUtils.getProductDevice());
    }

    public final boolean isTooLarge() {
        String str = this.mOriginResourcePath;
        if (str == null) {
            return false;
        }
        File file = new File(str);
        long j = 1024;
        Log.i("AIWallPaperManager", "size " + ((file.length() / j) / j) + " MB");
        if (!file.exists() || file.length() < 52428800) {
            return false;
        }
        Log.i("AIWallPaperManager", "updateAiGenerateStatusCode result: " + this.mStatusCode + " (file too large)");
        return true;
    }

    public final void notifyEditPageFinish(Context context) {
        if (context == null || this.mCurrentTaskId == null) {
            return;
        }
        ThemeApiWrapper.INSTANCE.notifyEditPageFinish(context.getApplicationContext(), this.mCurrentTaskId, this.mLocalIdentify);
    }

    public final void onWallPaperSelected(WallpaperTypeInfo wallpaperTypeInfo) {
        if (wallpaperTypeInfo == null || !wallpaperTypeInfo.isDirectGenerate()) {
            this.mOriginWallpaperInfo = null;
            this.mCurrentTaskId = null;
        }
        this.mLocalIdentify = null;
        this.mLocalPath = null;
        stopStatusCheck();
    }

    public final void openGeneratingPage(Context context, boolean z) {
        String str;
        Intrinsics.checkNotNullParameter(context, "context");
        String str2 = this.mLocalIdentify;
        if (str2 == null || str2.length() == 0 || (str = this.mLocalPath) == null || str.length() == 0) {
            return;
        }
        ThemeApiWrapper.INSTANCE.restoreWallpaper(context, this.mLocalIdentify, this.mLocalPath, z);
    }

    public final void release(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Log.i("AIWallPaperManager", "release");
        notifyEditPageFinish(context);
        stopStatusCheck();
        this.mFinishCallBack = null;
        this.mContextRef = null;
        this.mTemplateViewRef = null;
        this.mOriginWallpaperInfo = null;
        this.mStatusCode = 50010;
        this.mLocalIdentify = null;
        this.mLocalPath = null;
        this.mCurrentTaskId = null;
        this.mAiStyle = null;
        this.mOriginIsVideo = null;
        this.mOriginResourcePath = null;
    }

    public final void startAIGenerate(Fragment fragment, WallpaperInfo wallpaperInfo, boolean z, Function1 convertToWallpaperType, AIGenerateFinishCallback finishCallBack) {
        Context context;
        WallpaperPositionInfo positionInfo;
        Intrinsics.checkNotNullParameter(convertToWallpaperType, "convertToWallpaperType");
        Intrinsics.checkNotNullParameter(finishCallBack, "finishCallBack");
        if (fragment == null || (context = fragment.getContext()) == null) {
            Log.e("AIWallPaperManager", "fragment context error");
            return;
        }
        WallpaperPositionInfo wallpaperPositionInfo = null;
        this.mAiStyle = null;
        this.mIsRunning.set(true);
        this.mFinishCallBack = finishCallBack;
        WallpaperTypeInfo wallpaperTypeInfo = (WallpaperTypeInfo) convertToWallpaperType.invoke(wallpaperInfo);
        if (wallpaperTypeInfo != null && !wallpaperTypeInfo.isAiGenerated()) {
            this.mLivePhoto = isLivePhoto(wallpaperInfo);
            this.mOriginWallpaperInfo = wallpaperTypeInfo;
            this.mOriginResourcePath = getGeneratingFilePath(context, wallpaperInfo);
            this.mCurrentIsOrigin = true;
            this.mOriginIsVideo = Boolean.valueOf(Intrinsics.areEqual(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, "video") && wallpaperInfo.getVideoType() == 0);
        }
        WallpaperTypeInfo wallpaperTypeInfo2 = this.mOriginWallpaperInfo;
        if (wallpaperTypeInfo2 != null && (positionInfo = wallpaperTypeInfo2.getPositionInfo()) != null) {
            wallpaperPositionInfo = new WallpaperPositionInfo(positionInfo.getLeft(), positionInfo.getTop(), positionInfo.getRight(), positionInfo.getBottom(), positionInfo.getMatrixArr(), null, null, null, 224, null);
        }
        Log.i("AIWallPaperManager", "startAIGenerate OriginWallpaperInfo " + this.mOriginWallpaperInfo + "  mOriginPath: " + this.mOriginResourcePath + " mLivePhoto:" + this.mLivePhoto);
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), null, null, new C00942(wallpaperInfo, finishCallBack, fragment, wallpaperPositionInfo, z, context, null), 3, null);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0017  */
    public final Object startAIGenerateReal(Fragment fragment, Uri uri, String str, WallpaperPositionInfo wallpaperPositionInfo, int i, boolean z, boolean z2, AIGenerateFinishCallback aIGenerateFinishCallback, boolean z3, Continuation continuation) {
        C00951 c00951;
        FragmentActivity activity;
        AIGenerateFinishCallback aIGenerateFinishCallback2;
        AIWallPaperManager aIWallPaperManager = this;
        if (continuation instanceof C00951) {
            c00951 = (C00951) continuation;
            int i2 = c00951.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c00951.label = i2 - Integer.MIN_VALUE;
            } else {
                c00951 = aIWallPaperManager.new C00951(continuation);
            }
        } else {
            c00951 = aIWallPaperManager.new C00951(continuation);
        }
        Object obj = c00951.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i3 = c00951.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            activity = fragment.getActivity();
            if (activity == null) {
                Log.e("AIWallPaperManager", "fragment activity error");
                return Unit.INSTANCE;
            }
            MainCoroutineDispatcher main = Dispatchers.getMain();
            C00962 c00962 = new C00962(fragment, str, uri, wallpaperPositionInfo, i, z, z2, z3, null);
            c00951.L$0 = aIWallPaperManager;
            aIGenerateFinishCallback2 = aIGenerateFinishCallback;
            c00951.L$1 = aIGenerateFinishCallback2;
            c00951.L$2 = activity;
            c00951.label = 1;
            if (BuildersKt.withContext(main, c00962, c00951) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            FragmentActivity fragmentActivity = (FragmentActivity) c00951.L$2;
            AIGenerateFinishCallback aIGenerateFinishCallback3 = (AIGenerateFinishCallback) c00951.L$1;
            AIWallPaperManager aIWallPaperManager2 = (AIWallPaperManager) c00951.L$0;
            ResultKt.throwOnFailure(obj);
            activity = fragmentActivity;
            aIWallPaperManager = aIWallPaperManager2;
            aIGenerateFinishCallback2 = aIGenerateFinishCallback3;
        }
        aIWallPaperManager.mStatusCheckJob = BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(activity), Dispatchers.getIO(), null, aIWallPaperManager.new AnonymousClass3(activity, aIGenerateFinishCallback2, null), 2, null);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public final Object startStatusCheck(Context context, final AIGenerateFinishCallback aIGenerateFinishCallback, Continuation continuation) {
        C00971 c00971;
        if (continuation instanceof C00971) {
            c00971 = (C00971) continuation;
            int i = c00971.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c00971.label = i - Integer.MIN_VALUE;
            } else {
                c00971 = new C00971(continuation);
            }
        } else {
            c00971 = new C00971(continuation);
        }
        Object obj = c00971.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c00971.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            AIGenerateFinishCallback aIGenerateFinishCallback2 = (AIGenerateFinishCallback) c00971.L$2;
            context = (Context) c00971.L$1;
            AIWallPaperManager aIWallPaperManager = (AIWallPaperManager) c00971.L$0;
            ResultKt.throwOnFailure(obj);
            aIGenerateFinishCallback = aIGenerateFinishCallback2;
            this = aIWallPaperManager;
        }
        while (this.isGenerateRunning()) {
            if (context != null) {
                Log.d("AIWallPaperManager", "轮询检查AI生成任务是否完成 mLocalIdentify：- " + this.mLocalIdentify);
                Boxing.boxInt(ThemeApiWrapper.INSTANCE.getAiGenerateStatus(context, this.mLocalIdentify, new Function1() { // from class: com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        return AIWallPaperManager.startStatusCheck$lambda$12(this.f$0, aIGenerateFinishCallback, (Bundle) obj2);
                    }
                }));
            } else {
                this.stopStatusCheck();
            }
            c00971.L$0 = this;
            c00971.L$1 = context;
            c00971.L$2 = aIGenerateFinishCallback;
            c00971.label = 1;
            if (DelayKt.delay(5000L, c00971) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        return Unit.INSTANCE;
    }

    public final void stopStatusCheck() {
        if (this.mIsRunning.getAndSet(false)) {
            Job job = this.mStatusCheckJob;
            if (job != null) {
                Job.DefaultImpls.cancel$default(job, null, 1, null);
            }
            this.mStatusCheckJob = null;
        }
        this.mLocalIdentify = null;
        this.mLocalPath = null;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0017  */
    public final Object updateAiGenerateStatusCode(Context context, WallpaperInfo wallpaperInfo, boolean z, boolean z2, Continuation continuation) {
        C00981 c00981;
        String str;
        WallpaperInfo wallpaperInfo2 = wallpaperInfo;
        if (continuation instanceof C00981) {
            c00981 = (C00981) continuation;
            int i = c00981.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c00981.label = i - Integer.MIN_VALUE;
            } else {
                c00981 = new C00981(continuation);
            }
        } else {
            c00981 = new C00981(continuation);
        }
        Object objIsSupportType = c00981.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c00981.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objIsSupportType);
            if (z) {
                this.mCanSwitchDynamicStatic = z2;
                this.mStatusCode = 50010;
            }
            Log.i("AIWallPaperManager", "updateAiGenerateStatusCode wallpaperInfo: " + wallpaperInfo2);
            if (wallpaperInfo2 == null || (str = this.mOriginResourcePath) == null || str.length() == 0) {
                this.mStatusCode = -1;
                Log.i("AIWallPaperManager", "updateAiGenerateStatusCode result: -1 (null or empty resource path)");
                return Boxing.boxInt(this.mStatusCode);
            }
            if (!isSupportedDevice()) {
                this.mStatusCode = 40011;
                Log.i("AIWallPaperManager", "updateAiGenerateStatusCode result: 40011 (unsupported device)");
                return Boxing.boxInt(this.mStatusCode);
            }
            if (isInAiGenerationProcess(wallpaperInfo2)) {
                this.mStatusCode = 50010;
                Log.i("AIWallPaperManager", "updateAiGenerateStatusCode result: 50010 (isInAiGenerationProcess - allowed)");
                return Boxing.boxInt(this.mStatusCode);
            }
            if (isAiGeneratedImage(wallpaperInfo2)) {
                this.mStatusCode = 20040;
                Log.i("AIWallPaperManager", "updateAiGenerateStatusCode result: 20040 (AI generated image - not allowed)");
                return Boxing.boxInt(this.mStatusCode);
            }
            if (Intrinsics.areEqual(wallpaperInfo2.getResourceType(), "video") && wallpaperInfo2.getVideoType() == 0 && (DeviceHelper.detectType(context) == 3 || DeviceHelper.detectType(context) == 5 || DeviceHelper.detectType(context) == 2)) {
                this.mStatusCode = 40010;
                Log.i("AIWallPaperManager", "updateAiGenerateStatusCode result: 40010 (unsupported device(FOLD or TABLET))");
                return Boxing.boxInt(this.mStatusCode);
            }
            if (ArraysKt.contains(AiWallPaperStatus.INSTANCE.getDISABLE_ERROR_CODE_ARRAY_REMOTE(), this.mStatusCode)) {
                Log.i("AIWallPaperManager", "updateAiGenerateStatusCode result: " + this.mStatusCode + " (disable error code)");
                return Boxing.boxInt(this.mStatusCode);
            }
            c00981.L$0 = this;
            c00981.L$1 = wallpaperInfo2;
            c00981.label = 1;
            objIsSupportType = isSupportType(wallpaperInfo2, c00981);
            if (objIsSupportType == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            WallpaperInfo wallpaperInfo3 = (WallpaperInfo) c00981.L$1;
            AIWallPaperManager aIWallPaperManager = (AIWallPaperManager) c00981.L$0;
            ResultKt.throwOnFailure(objIsSupportType);
            wallpaperInfo2 = wallpaperInfo3;
            this = aIWallPaperManager;
        }
        if (((Boolean) objIsSupportType).booleanValue()) {
            if (this.isTooLarge()) {
                this.mStatusCode = 20030;
                return Boxing.boxInt(20030);
            }
            this.mStatusCode = 50010;
            Log.i("AIWallPaperManager", "updateAiGenerateStatusCode result: 50010 (all checks passed)");
            return Boxing.boxInt(this.mStatusCode);
        }
        if (Intrinsics.areEqual(wallpaperInfo2.getResourceType(), "video") && (wallpaperInfo2.getVideoType() == 0 || wallpaperInfo2.getVideoType() == 1)) {
            this.mStatusCode = 40013;
            Log.i("AIWallPaperManager", "updateAiGenerateStatusCode result: 40013 (unsupported video format)");
            return Boxing.boxInt(this.mStatusCode);
        }
        if (!this.mCanSwitchDynamicStatic || wallpaperInfo2.getVideoType() == 2 || wallpaperInfo2.getVideoType() == 3) {
            this.mStatusCode = 40010;
            Log.i("AIWallPaperManager", "updateAiGenerateStatusCode result: 40010 (unsupported type)");
            return Boxing.boxInt(this.mStatusCode);
        }
        this.mStatusCode = 40012;
        Log.i("AIWallPaperManager", "updateAiGenerateStatusCode result: 40012 (unsupported video)");
        return Boxing.boxInt(this.mStatusCode);
    }
}
