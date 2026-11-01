package com.miui.keyguard.editor.data.template;

import android.app.WallpaperColors;
import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.miwallpaper.IMiuiWallpaperManagerCallback;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: WallpaperChangedListener.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class HomeWallpaperChangedListener extends IMiuiWallpaperManagerCallback.Stub {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = "HomeWallpaperChangedListener";
    public static final long TIMEOUT = 5;
    public final Context context;
    public final CountDownLatch countDownLatch;

    /* JADX INFO: compiled from: WallpaperChangedListener.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final HomeWallpaperChangedListener registerHome(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            HomeWallpaperChangedListener homeWallpaperChangedListener = new HomeWallpaperChangedListener(context, null);
            WallpaperController.Companion.getInstance(context).registerWallpaperChangedListener(homeWallpaperChangedListener, 1);
            return homeWallpaperChangedListener;
        }
    }

    public HomeWallpaperChangedListener(Context context) {
        this.context = context;
        this.countDownLatch = new CountDownLatch(1);
    }

    public /* synthetic */ HomeWallpaperChangedListener(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onDrawFrameEnd() {
        Log.i(TAG, "onDrawFrameEnd home");
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onPartColorComputeComplete(Map map, Map map2, int i) {
        Log.i(TAG, "onPartColorComputeComplete home");
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onWallpaperChanged(WallpaperColors wallpaperColors, String str, int i) {
        this.countDownLatch.countDown();
        Log.i(TAG, "onWallpaperChanged home");
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onWallpaperFirstFrameRendered(int i) {
        Log.i(TAG, "onWallpaperFirstFrameRendered home");
    }

    public final void waitWallpaperChanged(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (z) {
            try {
                this.countDownLatch.await(TIMEOUT, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Log.e(TAG, "await home wallpaper changed interrupted", e);
            }
        }
        WallpaperController.Companion.getInstance(context).unregisterWallpaperChangedListener(this);
    }
}
