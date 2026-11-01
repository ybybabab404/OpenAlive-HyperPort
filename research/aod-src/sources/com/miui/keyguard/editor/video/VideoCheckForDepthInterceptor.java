package com.miui.keyguard.editor.video;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.miui.maml.R;
import com.miui.maml.folme.AnimatedPropertyType;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import miuix.appcompat.app.AlertDialog;
import miuix.core.util.IOUtils;

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
/* JADX INFO: compiled from: VideoCheckForDepthInterceptor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class VideoCheckForDepthInterceptor {
    public final Context context;
    public AlertDialog invalidDialog;
    public final boolean isFromGallery;
    public final boolean isOnlyCheckVideo;
    public static final Companion Companion = new Companion(null);
    public static final byte[] FTYP_HEADER = {102, 116, 121, 112};
    public static final byte[] MP4_BRANDS = {109, 112, 52, 50, 105, 115, 111, 109, 97, 118, 99, 49, 105, 115, 111, 53};
    public static final byte[] MOV_BRAND = {113, 116, 32, 32};

    /* JADX INFO: compiled from: VideoCheckForDepthInterceptor.kt */
    @Metadata
    public static final class Companion {

        /* JADX INFO: compiled from: VideoCheckForDepthInterceptor.kt */
        @Metadata
        public interface CheckVideoCallback {

            /* JADX INFO: compiled from: VideoCheckForDepthInterceptor.kt */
            @Metadata
            public static final class DefaultImpls {
                public static void onCheckVideoFail(CheckVideoCallback checkVideoCallback, Integer num) {
                }
            }

            void onCheckVideoFail(Integer num);

            void onCheckVideoSuccess();
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.video.VideoCheckForDepthInterceptor$accessCheck$1, reason: invalid class name */
    /* JADX INFO: compiled from: VideoCheckForDepthInterceptor.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.video.VideoCheckForDepthInterceptor$accessCheck$1", f = "VideoCheckForDepthInterceptor.kt", l = {R.styleable.AppCompatTheme_listPreferredItemHeight}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Companion.CheckVideoCallback $callback;
        public final /* synthetic */ Uri $data;
        public final /* synthetic */ Ref.BooleanRef $result;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Uri uri, Ref.BooleanRef booleanRef, Companion.CheckVideoCallback checkVideoCallback, Continuation continuation) {
            super(2, continuation);
            this.$data = uri;
            this.$result = booleanRef;
            this.$callback = checkVideoCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return VideoCheckForDepthInterceptor.this.new AnonymousClass1(this.$data, this.$result, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                VideoCheckForDepthInterceptor videoCheckForDepthInterceptor = VideoCheckForDepthInterceptor.this;
                Uri uri = this.$data;
                this.label = 1;
                obj = videoCheckForDepthInterceptor.checkVideo(uri, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            VideoCheckUtils.CheckVideoResult checkVideoResult = (VideoCheckUtils.CheckVideoResult) obj;
            if (VideoCheckForDepthInterceptor.this.isOnlyCheckVideo) {
                this.$result.element = VideoCheckForDepthInterceptor.this.parseCheckResult(checkVideoResult);
            } else {
                this.$result.element = VideoCheckForDepthInterceptor.this.dispatchCheckResult(checkVideoResult);
            }
            if (this.$result.element) {
                Companion.CheckVideoCallback checkVideoCallback = this.$callback;
                if (checkVideoCallback != null) {
                    checkVideoCallback.onCheckVideoSuccess();
                }
            } else {
                Companion.CheckVideoCallback checkVideoCallback2 = this.$callback;
                if (checkVideoCallback2 != null) {
                    VideoCheckUtils.CheckVideoResult.Invalid invalid = checkVideoResult instanceof VideoCheckUtils.CheckVideoResult.Invalid ? (VideoCheckUtils.CheckVideoResult.Invalid) checkVideoResult : null;
                    checkVideoCallback2.onCheckVideoFail(invalid != null ? Boxing.boxInt(invalid.getCode()) : null);
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.video.VideoCheckForDepthInterceptor$checkVideo$2, reason: invalid class name */
    /* JADX INFO: compiled from: VideoCheckForDepthInterceptor.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.video.VideoCheckForDepthInterceptor$checkVideo$2", f = "VideoCheckForDepthInterceptor.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super VideoCheckUtils.CheckVideoResult>, Object> {
        public final /* synthetic */ Uri $data;
        public int label;
        public final /* synthetic */ VideoCheckForDepthInterceptor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Uri uri, VideoCheckForDepthInterceptor videoCheckForDepthInterceptor, Continuation continuation) {
            super(2, continuation);
            this.$data = uri;
            this.this$0 = videoCheckForDepthInterceptor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$data, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:46:0x00c4  */
        /* JADX WARN: Not initialized variable reg: 3, insn: 0x0047: MOVE (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:16:0x0047 */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            MediaExtractor mediaExtractor;
            MediaExtractor mediaExtractor2;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (this.$data == null) {
                Log.e("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideo: data is null，passed");
                return VideoCheckUtils.CheckVideoResult.Success.INSTANCE;
            }
            MediaExtractor mediaExtractor3 = null;
            try {
                try {
                    mediaExtractor = new MediaExtractor();
                    try {
                        mediaExtractor.setDataSource(this.this$0.context, this.$data, (Map<String, String>) null);
                        VideoCheckUtils.Companion companion = VideoCheckUtils.Companion;
                        MediaFormat videoFormat = companion.getVideoFormat(mediaExtractor);
                        if (companion.getVideoFormat(mediaExtractor) == null) {
                            Log.e("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideo: videoTrackIndex < 0");
                            VideoCheckUtils.CheckVideoResult.Invalid invalid = new VideoCheckUtils.CheckVideoResult.Invalid(1000, null, 2, null);
                            mediaExtractor.release();
                            return invalid;
                        }
                        Intrinsics.checkNotNull(videoFormat);
                        Pair videoSize = companion.getVideoSize(videoFormat);
                        if (!this.this$0.checkVideoFormatValid(this.$data, videoFormat)) {
                            VideoCheckUtils.CheckVideoResult.Invalid invalid2 = new VideoCheckUtils.CheckVideoResult.Invalid(1001, null, 2, null);
                            mediaExtractor.release();
                            return invalid2;
                        }
                        if (this.this$0.isDolbyVisionVideo(videoFormat)) {
                            Log.i("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideo: is dolby video");
                            VideoCheckUtils.CheckVideoResult.Invalid invalid3 = new VideoCheckUtils.CheckVideoResult.Invalid(AnimatedPropertyType.STROKE_COLOR, null, 2, null);
                            mediaExtractor.release();
                            return invalid3;
                        }
                        if (!companion.isAtLeast4KVideo(((Number) videoSize.getFirst()).intValue(), ((Number) videoSize.getSecond()).intValue())) {
                            VideoCheckUtils.CheckVideoResult.Success success = VideoCheckUtils.CheckVideoResult.Success.INSTANCE;
                            mediaExtractor.release();
                            return success;
                        }
                        Log.i("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideo: is >= 4k video");
                        VideoCheckUtils.CheckVideoResult.Invalid invalid4 = new VideoCheckUtils.CheckVideoResult.Invalid(AnimatedPropertyType.STROKE_COLOR, null, 2, null);
                        mediaExtractor.release();
                        return invalid4;
                    } catch (Exception e) {
                        e = e;
                        Log.e("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideo: occur exception", e);
                        VideoCheckUtils.CheckVideoResult.Invalid invalid5 = new VideoCheckUtils.CheckVideoResult.Invalid(1000, null, 2, null);
                        if (mediaExtractor != null) {
                            mediaExtractor.release();
                        }
                        return invalid5;
                    }
                } catch (Throwable th) {
                    th = th;
                    mediaExtractor3 = mediaExtractor2;
                    if (mediaExtractor3 != null) {
                        mediaExtractor3.release();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                mediaExtractor = null;
            } catch (Throwable th2) {
                th = th2;
                if (mediaExtractor3 != null) {
                    mediaExtractor3.release();
                }
                throw th;
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.video.VideoCheckForDepthInterceptor$checkVideo$4, reason: invalid class name */
    /* JADX INFO: compiled from: VideoCheckForDepthInterceptor.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.video.VideoCheckForDepthInterceptor$checkVideo$4", f = "VideoCheckForDepthInterceptor.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super VideoCheckUtils.CheckVideoResult>, Object> {
        public final /* synthetic */ Uri $data;
        public final /* synthetic */ int $duration;
        public final /* synthetic */ float $frameRate;
        public final /* synthetic */ int $height;
        public final /* synthetic */ int $width;
        public int label;
        public final /* synthetic */ VideoCheckForDepthInterceptor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(Uri uri, VideoCheckForDepthInterceptor videoCheckForDepthInterceptor, int i, int i2, int i3, float f, Continuation continuation) {
            super(2, continuation);
            this.$data = uri;
            this.this$0 = videoCheckForDepthInterceptor;
            this.$width = i;
            this.$height = i2;
            this.$duration = i3;
            this.$frameRate = f;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass4(this.$data, this.this$0, this.$width, this.$height, this.$duration, this.$frameRate, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            MediaExtractor mediaExtractor;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Uri uri = this.$data;
            MediaExtractor mediaExtractor2 = 0;
            try {
                if (uri == null) {
                    Log.e("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideo: data is null");
                    return new VideoCheckUtils.CheckVideoResult.Invalid(1000, null, 2, null);
                }
                try {
                    mediaExtractor = new MediaExtractor();
                    try {
                        mediaExtractor.setDataSource(this.this$0.context, this.$data, (Map<String, String>) null);
                        VideoCheckUtils.Companion companion = VideoCheckUtils.Companion;
                        MediaFormat videoFormat = companion.getVideoFormat(mediaExtractor);
                        if (companion.getVideoFormat(mediaExtractor) == null) {
                            Log.e("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideo: videoTrackIndex < 0");
                            VideoCheckUtils.CheckVideoResult.Invalid invalid = new VideoCheckUtils.CheckVideoResult.Invalid(1000, null, 2, null);
                            mediaExtractor.release();
                            return invalid;
                        }
                        if (!this.this$0.checkVideoFormatValid(this.$data, videoFormat)) {
                            VideoCheckUtils.CheckVideoResult.Invalid invalid2 = new VideoCheckUtils.CheckVideoResult.Invalid(1001, null, 2, null);
                            mediaExtractor.release();
                            return invalid2;
                        }
                        if (this.$width > 0 && this.$height > 0) {
                            Intrinsics.checkNotNull(videoFormat);
                            if (!companion.isHorizontalVideo(videoFormat)) {
                                if (this.$duration > 6000) {
                                    VideoCheckUtils.CheckVideoResult.DurationCrop durationCrop = VideoCheckUtils.CheckVideoResult.DurationCrop.INSTANCE;
                                    mediaExtractor.release();
                                    return durationCrop;
                                }
                                if (!companion.isLessThan2KVideo(this.$width, this.$height)) {
                                    VideoCheckUtils.CheckVideoResult.Compression compression = VideoCheckUtils.CheckVideoResult.Compression.INSTANCE;
                                    mediaExtractor.release();
                                    return compression;
                                }
                                float f = this.$frameRate;
                                if (f >= 0.0f && ((int) f) <= 30) {
                                    VideoCheckUtils.CheckVideoResult.Success success = VideoCheckUtils.CheckVideoResult.Success.INSTANCE;
                                    mediaExtractor.release();
                                    return success;
                                }
                                VideoCheckUtils.CheckVideoResult.Compression compression2 = VideoCheckUtils.CheckVideoResult.Compression.INSTANCE;
                                mediaExtractor.release();
                                return compression2;
                            }
                        }
                        Log.i("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideo: is horizontal video");
                        VideoCheckUtils.CheckVideoResult.Invalid invalid3 = new VideoCheckUtils.CheckVideoResult.Invalid(AnimatedPropertyType.TEXT_SIZE, null, 2, null);
                        mediaExtractor.release();
                        return invalid3;
                    } catch (Exception e) {
                        e = e;
                        Log.e("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideo: occur exception", e);
                        VideoCheckUtils.CheckVideoResult.Invalid invalid4 = new VideoCheckUtils.CheckVideoResult.Invalid(1000, null, 2, null);
                        if (mediaExtractor != null) {
                            mediaExtractor.release();
                        }
                        return invalid4;
                    }
                } catch (Exception e2) {
                    e = e2;
                    mediaExtractor = null;
                } catch (Throwable th) {
                    th = th;
                    if (mediaExtractor2 != 0) {
                        mediaExtractor2.release();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                mediaExtractor2 = uri;
            }
        }
    }

    public VideoCheckForDepthInterceptor(Context context, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.isOnlyCheckVideo = z;
        this.isFromGallery = z2;
    }

    public /* synthetic */ VideoCheckForDepthInterceptor(Context context, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2);
    }

    public static final void createInvalidDialog$lambda$2(VideoCheckForDepthInterceptor videoCheckForDepthInterceptor, DialogInterface dialogInterface, int i) {
        Context context = videoCheckForDepthInterceptor.context;
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
        dialogInterface.dismiss();
    }

    public final void accessCheck(Uri uri, Companion.CheckVideoCallback checkVideoCallback) {
        Log.d("Keyguard-Editor-VideoWallpaperInterceptor", "accessCheck: isOnlyCheckVideo = " + this.isOnlyCheckVideo);
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        Object obj = this.context;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type androidx.lifecycle.LifecycleOwner");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) obj), null, null, new AnonymousClass1(uri, booleanRef, checkVideoCallback, null), 3, null);
    }

    public final Object checkVideo(Uri uri, int i, int i2, int i3, int i4, float f, Continuation continuation) {
        Log.d("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideo: START CHECK VIDEO");
        return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass4(uri, this, i, i2, i3, f, null), continuation);
    }

    public final Object checkVideo(Uri uri, Continuation continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(uri, this, null), continuation);
    }

    /* JADX WARN: Code duplicated, block: B:106:0x01ac A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public final boolean checkVideoFormatValid(Uri uri, MediaFormat mediaFormat) throws Throwable {
        ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor;
        ParcelFileDescriptor parcelFileDescriptorOpen;
        boolean z;
        if (mediaFormat == null) {
            Log.d("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideoFormatValid: get video mimetype error");
            return false;
        }
        FileInputStream fileInputStream = null;
        try {
            parcelFileDescriptorOpenFileDescriptor = this.context.getContentResolver().openFileDescriptor(uri, "r");
        } catch (Exception e) {
            Log.e("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideoFormatValid: uri failed:" + e);
            parcelFileDescriptorOpenFileDescriptor = null;
        }
        try {
            if (parcelFileDescriptorOpenFileDescriptor == null) {
                try {
                    String path = uri.getPath();
                    if (path != null && (parcelFileDescriptorOpen = ParcelFileDescriptor.open(new File(path), 268435456)) != null) {
                        parcelFileDescriptorOpenFileDescriptor = parcelFileDescriptorOpen;
                    }
                    Log.e("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideoFormatValid: open uri error:" + uri);
                    IOUtils.closeQuietly((InputStream) null);
                    return false;
                } catch (Exception e2) {
                    e = e2;
                    Log.e("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideoFormatValid: error:" + Log.getStackTraceString(e));
                    IOUtils.closeQuietly((InputStream) fileInputStream);
                    if (parcelFileDescriptorOpenFileDescriptor != null) {
                        try {
                            parcelFileDescriptorOpenFileDescriptor.close();
                        } catch (Exception unused) {
                        }
                    }
                    return false;
                }
            }
            FileInputStream fileInputStream2 = new FileInputStream(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor());
            try {
                byte[] bArr = new byte[12];
                int i = fileInputStream2.read(bArr);
                if (i < 12) {
                    Log.e("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideoFormatValid: head error: " + i + " " + uri);
                    IOUtils.closeQuietly((InputStream) fileInputStream2);
                    try {
                        parcelFileDescriptorOpenFileDescriptor.close();
                    } catch (Exception unused2) {
                    }
                    return false;
                }
                for (int i2 = 0; i2 < 4; i2++) {
                    if (bArr[i2 + 4] != FTYP_HEADER[i2]) {
                        Log.d("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideoFormatValid: ftyp error: header=" + bArr);
                        IOUtils.closeQuietly((InputStream) fileInputStream2);
                        try {
                            parcelFileDescriptorOpenFileDescriptor.close();
                        } catch (Exception unused3) {
                        }
                        return false;
                    }
                }
                int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(0, MP4_BRANDS.length - 1, 4);
                if (progressionLastElement < 0) {
                    z = false;
                    break;
                }
                int i3 = 0;
                loop1: while (true) {
                    int i4 = 0;
                    while (true) {
                        if (i4 >= 4) {
                            z = true;
                            break loop1;
                        }
                        if (bArr[i4 + 8] != MP4_BRANDS[i3 + i4]) {
                            break;
                        }
                        i4++;
                    }
                    if (i3 == progressionLastElement) {
                        z = false;
                        break;
                    }
                    i3 += 4;
                }
                int i5 = 0;
                boolean z2 = false;
                while (i5 < 4) {
                    if (bArr[i5 + 8] != MOV_BRAND[i5]) {
                        z2 = false;
                        break;
                    }
                    i5++;
                    z2 = true;
                }
                IOUtils.closeQuietly((InputStream) fileInputStream2);
                try {
                    parcelFileDescriptorOpenFileDescriptor.close();
                } catch (Exception unused4) {
                }
                int integer = mediaFormat.getInteger("color-standard", -1);
                int integer2 = mediaFormat.getInteger("color-transfer", -1);
                boolean z3 = integer == 6 && (integer2 == 6 || integer2 == 7);
                boolean zIsDolbyVisionVideo = isDolbyVisionVideo(mediaFormat);
                String string = mediaFormat.getString("mime");
                boolean z4 = Intrinsics.areEqual(string, "video/avc") || Intrinsics.areEqual(string, "video/hevc");
                Log.d("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideoFormatValid: isMP4 = " + z + " isMOV = " + z2 + "  isHDR = " + z3 + "  isDolby = " + zIsDolbyVisionVideo + "  videoMime = " + string + "  isSupportedCodec = " + z4);
                return (z || z2) && !zIsDolbyVisionVideo && !z3 && z4;
            } catch (Exception e3) {
                e = e3;
                fileInputStream = fileInputStream2;
                Log.e("Keyguard-Editor-VideoWallpaperInterceptor", "checkVideoFormatValid: error:" + Log.getStackTraceString(e));
                IOUtils.closeQuietly((InputStream) fileInputStream);
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    parcelFileDescriptorOpenFileDescriptor.close();
                }
                return false;
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                IOUtils.closeQuietly((InputStream) fileInputStream);
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    try {
                        parcelFileDescriptorOpenFileDescriptor.close();
                    } catch (Exception unused5) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final AlertDialog createInvalidDialog(int i) {
        AlertDialog.Builder positiveButton = new AlertDialog.Builder(this.context).setTitle(getInvalidDialogTitle(i)).setCancelable(false).setPositiveButton(this.isFromGallery ? com.miui.keyguard.editor.R.string.kg_resource_re_choose : com.miui.keyguard.editor.R.string.kg_confirm, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.video.VideoCheckForDepthInterceptor$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                VideoCheckForDepthInterceptor.createInvalidDialog$lambda$2(this.f$0, dialogInterface, i2);
            }
        });
        if (this.isFromGallery) {
            positiveButton.setNegativeButton(com.miui.keyguard.editor.R.string.kg_editor_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.video.VideoCheckForDepthInterceptor$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    dialogInterface.dismiss();
                }
            });
        }
        if (i == 1001) {
            positiveButton.setMessage(com.miui.keyguard.editor.R.string.kg_choose_video_dialog_invalid_format_message);
        } else if (i == 1005) {
            positiveButton.setMessage(com.miui.keyguard.editor.R.string.kg_choose_video_dialog_invalid_unsupport_video_message);
        }
        AlertDialog alertDialogCreate = positiveButton.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        return alertDialogCreate;
    }

    public final boolean dispatchCheckResult(VideoCheckUtils.CheckVideoResult checkVideoResult) {
        Log.d("Keyguard-Editor-VideoWallpaperInterceptor", "dispatchCheckResult: checkVideoResult = " + checkVideoResult);
        if (checkVideoResult instanceof VideoCheckUtils.CheckVideoResult.Invalid) {
            AlertDialog alertDialogCreateInvalidDialog = createInvalidDialog(((VideoCheckUtils.CheckVideoResult.Invalid) checkVideoResult).getCode());
            this.invalidDialog = alertDialogCreateInvalidDialog;
            if (alertDialogCreateInvalidDialog != null) {
                alertDialogCreateInvalidDialog.show();
            }
            return false;
        }
        VideoCheckUtils.CheckVideoResult.DurationCrop durationCrop = VideoCheckUtils.CheckVideoResult.DurationCrop.INSTANCE;
        if (!Intrinsics.areEqual(checkVideoResult, durationCrop) && !Intrinsics.areEqual(checkVideoResult, VideoCheckUtils.CheckVideoResult.Compression.INSTANCE)) {
            if (Intrinsics.areEqual(checkVideoResult, VideoCheckUtils.CheckVideoResult.Success.INSTANCE)) {
                return true;
            }
            throw new NoWhenBranchMatchedException();
        }
        AlertDialog alertDialogCreateInvalidDialog2 = createInvalidDialog(Intrinsics.areEqual(checkVideoResult, durationCrop) ? 1000 : AnimatedPropertyType.STROKE_COLOR);
        this.invalidDialog = alertDialogCreateInvalidDialog2;
        if (alertDialogCreateInvalidDialog2 != null) {
            alertDialogCreateInvalidDialog2.show();
        }
        return false;
    }

    public final int getInvalidDialogTitle(int i) {
        if (i == 1001) {
            return com.miui.keyguard.editor.R.string.kg_choose_video_dialog_invalid_format_title;
        }
        if (i != 1002) {
            return i != 1005 ? com.miui.keyguard.editor.R.string.kg_choose_video_dialog_invalid_default_message : com.miui.keyguard.editor.R.string.kg_choose_video_dialog_invalid_format_title;
        }
        return com.miui.keyguard.editor.R.string.kg_choose_video_dialog_invalid_direction_title;
    }

    public final boolean isDolbyVisionVideo(MediaFormat mediaFormat) {
        return VideoWallpaperUtils.isDolbyVisionVideo(mediaFormat.getString("mime"));
    }

    public final boolean parseCheckResult(VideoCheckUtils.CheckVideoResult checkVideoResult) {
        return checkVideoResult instanceof VideoCheckUtils.CheckVideoResult.Success;
    }
}
