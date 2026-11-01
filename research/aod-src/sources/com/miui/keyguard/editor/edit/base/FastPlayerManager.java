package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;
import com.miui.fastplayer.FastPlayer;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.CommonUtils;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: FastPlayerManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FastPlayerManager {
    public volatile VideoMetaData cachedMetaData;
    public volatile FastPlayer fastPlayer;
    public volatile boolean isFirstFrameReady;
    public final boolean isLoop;
    public volatile boolean isPaused;
    public final long loopIntervalTime;
    public final Handler mainHandler;
    public Function0 readyCallback;
    public final Runnable restartRunnable;

    /* JADX INFO: compiled from: FastPlayerManager.kt */
    @Metadata
    public static final class VideoMetaData {
        public final int duration;
        public final float frameRate;
        public final int height;
        public final int rotation;
        public final int width;

        public VideoMetaData(int i, int i2, int i3, int i4, float f) {
            this.width = i;
            this.height = i2;
            this.duration = i3;
            this.rotation = i4;
            this.frameRate = f;
        }

        public /* synthetic */ VideoMetaData(int i, int i2, int i3, int i4, float f, int i5, DefaultConstructorMarker defaultConstructorMarker) {
            this((i5 & 1) != 0 ? -1 : i, (i5 & 2) != 0 ? -1 : i2, (i5 & 4) != 0 ? -1 : i3, (i5 & 8) != 0 ? -1 : i4, (i5 & 16) != 0 ? -1.0f : f);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof VideoMetaData)) {
                return false;
            }
            VideoMetaData videoMetaData = (VideoMetaData) obj;
            return this.width == videoMetaData.width && this.height == videoMetaData.height && this.duration == videoMetaData.duration && this.rotation == videoMetaData.rotation && Float.compare(this.frameRate, videoMetaData.frameRate) == 0;
        }

        public final int getDuration() {
            return this.duration;
        }

        public final float getFrameRate() {
            return this.frameRate;
        }

        public final int getHeight() {
            return this.height;
        }

        public final int getRotation() {
            return this.rotation;
        }

        public final int getWidth() {
            return this.width;
        }

        public int hashCode() {
            return (((((((Integer.hashCode(this.width) * 31) + Integer.hashCode(this.height)) * 31) + Integer.hashCode(this.duration)) * 31) + Integer.hashCode(this.rotation)) * 31) + Float.hashCode(this.frameRate);
        }

        public String toString() {
            return "VideoMetaData(width=" + this.width + ", height=" + this.height + ", duration=" + this.duration + ", rotation=" + this.rotation + ", frameRate=" + this.frameRate + ")";
        }
    }

    public FastPlayerManager(boolean z, long j) {
        this.isLoop = z;
        this.loopIntervalTime = j;
        this.cachedMetaData = new VideoMetaData(0, 0, 0, 0, 0.0f, 31, null);
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.restartRunnable = new Runnable() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                FastPlayerManager.restartRunnable$lambda$0(this.f$0);
            }
        };
    }

    public /* synthetic */ FastPlayerManager(boolean z, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z, (i & 2) != 0 ? 2000L : j);
    }

    public static final Unit pauseFastPlayer$lambda$13(FastPlayerManager fastPlayerManager) {
        FastPlayer fastPlayer = fastPlayerManager.fastPlayer;
        if (fastPlayer != null) {
            fastPlayer.pause();
        }
        return Unit.INSTANCE;
    }

    public static final Unit releaseFastPlayer$lambda$14(FastPlayer fastPlayer) {
        if (fastPlayer != null) {
            try {
                fastPlayer.release();
            } catch (Throwable th) {
                Log.e("Keyguard-Editor-FastPlayerManager", "release failed", th);
            }
        }
        return Unit.INSTANCE;
    }

    public static final void restartRunnable$lambda$0(FastPlayerManager fastPlayerManager) {
        Log.i("Keyguard-Editor-FastPlayerManager", "video loop is running,isPaused is " + fastPlayerManager.isPaused);
        if (fastPlayerManager.isPaused) {
            fastPlayerManager.seekTo(0L);
        } else {
            fastPlayerManager.restartFastPlayer();
        }
    }

    public static final Unit setOpenGLSurfaceVisible$lambda$11(FastPlayerManager fastPlayerManager, boolean z) {
        FastPlayer fastPlayer = fastPlayerManager.fastPlayer;
        if (fastPlayer != null) {
            fastPlayer.setOpenGLSurfaceVisible(z, true);
        }
        return Unit.INSTANCE;
    }

    public static final void startDepthVideo$lambda$10(Context context, final FastPlayerManager fastPlayerManager, String str, Surface surface, Surface surface2, boolean z, final DepthFirstFrameCallBack depthFirstFrameCallBack) throws IOException {
        FastPlayer fastPlayer = new FastPlayer(context);
        fastPlayerManager.fastPlayer = fastPlayer;
        FastPlayer.FastPlayerListener fastPlayerListener = new FastPlayer.FastPlayerListener() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManager$$ExternalSyntheticLambda7
            @Override // com.miui.fastplayer.FastPlayer.FastPlayerListener
            public final int playerModeChange(int i, int i2, long j) {
                return FastPlayerManager.startDepthVideo$lambda$10$lambda$9(this.f$0, depthFirstFrameCallBack, i, i2, j);
            }
        };
        fastPlayer.addDataSource(context, Uri.parse(str), 0);
        fastPlayer.addShaderSource(context, 0, R.raw.alpha_video_vertex);
        fastPlayer.addShaderSource(context, 1, R.raw.alpha_video_fragment);
        fastPlayer.setVideoScalingMode(2);
        fastPlayer.setSurface(surface, surface2, 12, null);
        fastPlayer.setCallback(fastPlayerListener);
        fastPlayerManager.cachedMetaData = fastPlayerManager.extractMetaData(fastPlayer);
        if (fastPlayerManager.fastPlayer != fastPlayer) {
            Log.i("Keyguard-Editor-FastPlayerManager", "startDepthVideo: player replaced during setup, release stale one");
            try {
                fastPlayer.release();
            } catch (Throwable unused) {
            }
        } else {
            fastPlayerManager.isPaused = !z;
            if (z) {
                fastPlayer.start();
            } else {
                fastPlayer.pause();
            }
        }
    }

    public static final int startDepthVideo$lambda$10$lambda$9(final FastPlayerManager fastPlayerManager, final DepthFirstFrameCallBack depthFirstFrameCallBack, int i, int i2, long j) {
        Log.i("Keyguard-Editor-FastPlayerManager", "depth video callback mode is " + i);
        if (i == 2) {
            fastPlayerManager.mainHandler.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManager$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    FastPlayerManager.startDepthVideo$lambda$10$lambda$9$lambda$7(this.f$0);
                }
            });
            fastPlayerManager.mainHandler.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManager$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    depthFirstFrameCallBack.onFirstFrame();
                }
            }, 100L);
            return 0;
        }
        if (!fastPlayerManager.isLoop || i != 6) {
            return 0;
        }
        FastPlayerManagerKt.getPlayerHandler().postDelayed(fastPlayerManager.restartRunnable, fastPlayerManager.loopIntervalTime);
        return 0;
    }

    public static final void startDepthVideo$lambda$10$lambda$9$lambda$7(FastPlayerManager fastPlayerManager) {
        Function0 function0 = fastPlayerManager.readyCallback;
        if (function0 != null) {
            function0.invoke();
            fastPlayerManager.readyCallback = null;
        }
    }

    public static final Unit startFastPlayer$lambda$12(FastPlayerManager fastPlayerManager) {
        FastPlayer fastPlayer = fastPlayerManager.fastPlayer;
        if (fastPlayer != null) {
            fastPlayer.start();
        }
        return Unit.INSTANCE;
    }

    public static final void startNormalVideo$lambda$5(Context context, final FastPlayerManager fastPlayerManager, final String str, Surface surface, boolean z, final FirstFrameCallBack firstFrameCallBack) throws IOException {
        FastPlayer fastPlayer = new FastPlayer(context);
        fastPlayerManager.fastPlayer = fastPlayer;
        FastPlayer.FastPlayerListener fastPlayerListener = new FastPlayer.FastPlayerListener() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManager$$ExternalSyntheticLambda8
            @Override // com.miui.fastplayer.FastPlayer.FastPlayerListener
            public final int playerModeChange(int i, int i2, long j) {
                return FastPlayerManager.startNormalVideo$lambda$5$lambda$4(this.f$0, firstFrameCallBack, str, i, i2, j);
            }
        };
        fastPlayer.addDataSource(context, Uri.parse(str), 0);
        fastPlayer.addShaderSource(context, 0, R.raw.normal_vertex);
        fastPlayer.addShaderSource(context, 1, R.raw.normal_fragment);
        fastPlayer.setVideoScalingMode(2);
        fastPlayer.setSurface(surface, 13);
        fastPlayer.setCallback(fastPlayerListener);
        fastPlayerManager.cachedMetaData = fastPlayerManager.extractMetaData(fastPlayer);
        if (fastPlayerManager.fastPlayer != fastPlayer) {
            Log.i("Keyguard-Editor-FastPlayerManager", "startNormalVideo: player replaced during setup, release stale one");
            try {
                fastPlayer.release();
            } catch (Throwable unused) {
            }
        } else {
            fastPlayerManager.isPaused = !z;
            if (z) {
                fastPlayer.start();
            } else {
                fastPlayer.pause();
            }
        }
    }

    public static final int startNormalVideo$lambda$5$lambda$4(final FastPlayerManager fastPlayerManager, final FirstFrameCallBack firstFrameCallBack, final String str, int i, int i2, long j) {
        Log.i("Keyguard-Editor-FastPlayerManager", "normal video callback mode is " + i);
        if (i == 2) {
            fastPlayerManager.isFirstFrameReady = true;
            fastPlayerManager.mainHandler.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManager$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    FastPlayerManager.startNormalVideo$lambda$5$lambda$4$lambda$2(this.f$0);
                }
            });
            fastPlayerManager.mainHandler.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManager$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    firstFrameCallBack.onFirstFrame(str);
                }
            }, 100L);
            return 0;
        }
        if (!fastPlayerManager.isLoop || i != 6) {
            return 0;
        }
        FastPlayerManagerKt.getPlayerHandler().postDelayed(fastPlayerManager.restartRunnable, fastPlayerManager.loopIntervalTime);
        return 0;
    }

    public static final void startNormalVideo$lambda$5$lambda$4$lambda$2(FastPlayerManager fastPlayerManager) {
        Function0 function0 = fastPlayerManager.readyCallback;
        if (function0 != null) {
            function0.invoke();
            fastPlayerManager.readyCallback = null;
        }
    }

    public final VideoMetaData extractMetaData(FastPlayer fastPlayer) {
        try {
            CommonUtils commonUtils = CommonUtils.INSTANCE;
            String strExtractMetadata = fastPlayer.extractMetadata(18, 0);
            Intrinsics.checkNotNullExpressionValue(strExtractMetadata, "extractMetadata(...)");
            int i = commonUtils.parseInt(strExtractMetadata);
            String strExtractMetadata2 = fastPlayer.extractMetadata(19, 0);
            Intrinsics.checkNotNullExpressionValue(strExtractMetadata2, "extractMetadata(...)");
            int i2 = commonUtils.parseInt(strExtractMetadata2);
            String strExtractMetadata3 = fastPlayer.extractMetadata(60, 0);
            Intrinsics.checkNotNullExpressionValue(strExtractMetadata3, "extractMetadata(...)");
            int i3 = commonUtils.parseInt(strExtractMetadata3);
            String strExtractMetadata4 = fastPlayer.extractMetadata(24, 0);
            Intrinsics.checkNotNullExpressionValue(strExtractMetadata4, "extractMetadata(...)");
            int i4 = commonUtils.parseInt(strExtractMetadata4);
            String strExtractMetadata5 = fastPlayer.extractMetadata(50, 0);
            Intrinsics.checkNotNullExpressionValue(strExtractMetadata5, "extractMetadata(...)");
            float f = commonUtils.parseFloat(strExtractMetadata5);
            Log.d("Keyguard-Editor-FastPlayerManager", "extractMetaData: w=" + i + " h=" + i2 + " dur=" + i3 + " rot=" + i4 + " fps=" + f);
            return new VideoMetaData(i, i2, i3, i4, f);
        } catch (Throwable th) {
            Log.e("Keyguard-Editor-FastPlayerManager", "extractMetaData failed", th);
            return new VideoMetaData(0, 0, 0, 0, 0.0f, 31, null);
        }
    }

    public final VideoMetaData getVideoMetaData() {
        return this.cachedMetaData;
    }

    public final synchronized boolean isFirstFrameReady(Function0 function0) {
        try {
            if (!this.isFirstFrameReady) {
                this.readyCallback = function0;
            } else if (function0 != null) {
                function0.invoke();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.isFirstFrameReady;
    }

    public final void pauseFastPlayer() {
        Log.i("Keyguard-Editor-FastPlayerManager", "fast player is paused");
        this.isPaused = true;
        FastPlayerManagerKt.runOnPlayerThread(new Function0() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FastPlayerManager.pauseFastPlayer$lambda$13(this.f$0);
            }
        });
    }

    public final void releaseFastPlayer() {
        Log.i("Keyguard-Editor-FastPlayerManager", "fast player is released");
        final FastPlayer fastPlayer = this.fastPlayer;
        this.fastPlayer = null;
        this.isFirstFrameReady = false;
        this.cachedMetaData = new VideoMetaData(0, 0, 0, 0, 0.0f, 31, null);
        FastPlayerManagerKt.getPlayerHandler().removeCallbacks(this.restartRunnable);
        FastPlayerManagerKt.runOnPlayerThread(new Function0() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManager$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FastPlayerManager.releaseFastPlayer$lambda$14(fastPlayer);
            }
        });
    }

    public final void restartFastPlayer() {
        Log.i("Keyguard-Editor-FastPlayerManager", "fast player is restarted");
        seekTo(0L);
        FastPlayer fastPlayer = this.fastPlayer;
        if (fastPlayer != null) {
            fastPlayer.start();
        }
    }

    public final void seekTo(long j) {
        Log.i("Keyguard-Editor-FastPlayerManager", "fast player is seek to " + j);
        FastPlayer fastPlayer = this.fastPlayer;
        if (fastPlayer != null) {
            fastPlayer.seekto(0.0f, j, 0);
        }
    }

    public final void setOpenGLSurfaceVisible(final boolean z) {
        FastPlayerManagerKt.runOnPlayerThread(new Function0() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManager$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FastPlayerManager.setOpenGLSurfaceVisible$lambda$11(this.f$0, z);
            }
        });
    }

    public final void startDepthVideo(final Context context, final Surface foregroundSurface, final Surface backgroundSurface, final String path, final DepthFirstFrameCallBack firstFrame, final boolean z, String tag) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(foregroundSurface, "foregroundSurface");
        Intrinsics.checkNotNullParameter(backgroundSurface, "backgroundSurface");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(firstFrame, "firstFrame");
        Intrinsics.checkNotNullParameter(tag, "tag");
        Log.i("Keyguard-Editor-FastPlayerManager", "startDepthVideo: foregroundSurface=" + foregroundSurface + " backgroundSurface=" + backgroundSurface + " tag=" + tag);
        releaseFastPlayer();
        FastPlayerManagerKt.getPlayerHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                FastPlayerManager.startDepthVideo$lambda$10(context, this, path, foregroundSurface, backgroundSurface, z, firstFrame);
            }
        });
    }

    public final void startFastPlayer() {
        Log.i("Keyguard-Editor-FastPlayerManager", "fast player is started");
        this.isPaused = false;
        FastPlayerManagerKt.runOnPlayerThread(new Function0() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManager$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FastPlayerManager.startFastPlayer$lambda$12(this.f$0);
            }
        });
    }

    public final void startNormalVideo(final Context context, final Surface surface, final String path, final FirstFrameCallBack firstFrame, final boolean z, String tag) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(surface, "surface");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(firstFrame, "firstFrame");
        Intrinsics.checkNotNullParameter(tag, "tag");
        Log.i("Keyguard-Editor-FastPlayerManager", "startNormalVideo: surface=" + surface + " tag=" + tag);
        releaseFastPlayer();
        FastPlayerManagerKt.getPlayerHandler().post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.FastPlayerManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                FastPlayerManager.startNormalVideo$lambda$5(context, this, path, surface, z, firstFrame);
            }
        });
    }
}
