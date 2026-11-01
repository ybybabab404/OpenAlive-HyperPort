package com.miui.keyguard.editor.data.template;

import android.app.WallpaperColors;
import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.MagicType;
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
public final class WallpaperChangedListener extends IMiuiWallpaperManagerCallback.Stub {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final CountDownLatch countDownLatch;
    public final boolean isNewWallpaper;
    public final int magicType;
    public final String tag;
    public final String wallpaperType;

    /* JADX INFO: compiled from: WallpaperChangedListener.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final WallpaperChangedListener register(Context context, int i, String str) {
            Intrinsics.checkNotNullParameter(context, "context");
            WallpaperChangedListener wallpaperChangedListener = new WallpaperChangedListener(context, i, str, null);
            WallpaperController.Companion.getInstance(context).registerWallpaperChangedListener(wallpaperChangedListener, 2);
            return wallpaperChangedListener;
        }
    }

    public WallpaperChangedListener(Context context, int i, String str) {
        this.context = context;
        this.magicType = i;
        this.wallpaperType = str;
        this.tag = "WallpaperChangedCallback";
        WallpaperController companion = WallpaperController.Companion.getInstance();
        boolean z = false;
        if (companion != null && companion.isWallpaperSupportWaitImage()) {
            z = true;
        }
        this.isNewWallpaper = z;
        Log.i("WallpaperChangedCallback", "magicType =" + i + ", wallpaperType =" + str + ", isNewWallpaper=" + z);
        this.countDownLatch = new CountDownLatch((MagicType.INSTANCE.isDepthVideo(Integer.valueOf(i)) || Intrinsics.areEqual(str, "video") || (z && Intrinsics.areEqual(str, "image"))) ? 3 : 2);
    }

    public /* synthetic */ WallpaperChangedListener(Context context, int i, String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, i, str);
    }

    public static /* synthetic */ void waitWallpaperChanged$default(WallpaperChangedListener wallpaperChangedListener, Context context, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        wallpaperChangedListener.waitWallpaperChanged(context, z);
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onDrawFrameEnd() {
        Log.i(this.tag, "onDrawFrameEnd");
        this.countDownLatch.countDown();
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onPartColorComputeComplete(Map map, Map map2, int i) {
        Log.i(this.tag, "onPartColorComputeComplete");
        this.countDownLatch.countDown();
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onWallpaperChanged(WallpaperColors wallpaperColors, String str, int i) {
        Log.i(this.tag, "onWallpaperChanged");
        this.countDownLatch.countDown();
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onWallpaperFirstFrameRendered(int i) {
        if (MagicType.INSTANCE.isDepthVideo(Integer.valueOf(this.magicType)) || Intrinsics.areEqual(this.wallpaperType, "video")) {
            Log.i(this.tag, "onWallpaperFirstFrameRendered");
            this.countDownLatch.countDown();
        }
    }

    public final void waitWallpaperChanged(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (z) {
            try {
                this.countDownLatch.await(5L, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Log.e(this.tag, "await lock wallpaper changed interrupted", e);
            }
        }
        WallpaperController.Companion.getInstance(context).unregisterWallpaperChangedListener(this);
    }
}
