package com.miui.keyguard.editor.edit.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Process;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.WallpaperInfoType;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.videodownload.IVideoDownloadCallback;
import com.miui.videodownload.IVideoDownloadService;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.Unit;
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
/* JADX INFO: compiled from: DepthPresetDownloadManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DepthPresetDownloadManager {
    public static final Companion Companion = new Companion(null);
    public static String THEME_VIDEO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.miui.aod";
    public final String TAG;
    public boolean bindStatus;
    public final WeakReference buttonCallbackWeak;
    public boolean connectStatus;
    public final WeakReference contextWeak;
    public boolean isDownloadSuccessCallbackSuppressed;
    public final VideoDownloadCallback mCallback;
    public final ServiceConnection mConnection;
    public IVideoDownloadService mService;
    public CommonConfig templateConfig;

    /* JADX INFO: compiled from: DepthPresetDownloadManager.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getPresetDepthVideoPath(WallpaperInfo wallpaperInfo) {
            if (TextUtils.isEmpty(wallpaperInfo != null ? wallpaperInfo.getVideoName() : null)) {
                return "";
            }
            if (TextUtils.isEmpty(wallpaperInfo != null ? wallpaperInfo.getSha1() : null)) {
                return "";
            }
            String theme_video_path = getTHEME_VIDEO_PATH();
            String str = File.separator;
            return theme_video_path + str + ".videodownload" + str + (wallpaperInfo != null ? wallpaperInfo.getVideoName() : null) + (wallpaperInfo != null ? wallpaperInfo.getSha1() : null);
        }

        public final String getTHEME_VIDEO_PATH() {
            return DepthPresetDownloadManager.THEME_VIDEO_PATH;
        }

        public final boolean isPresetDepthVideo(WallpaperInfo wallpaperInfo, boolean z) {
            if (!z || wallpaperInfo == null) {
                return false;
            }
            String presetDepthVideoPath = getPresetDepthVideoPath(wallpaperInfo);
            if (TextUtils.isEmpty(presetDepthVideoPath)) {
                return false;
            }
            return FileUtil.isFileExist(presetDepthVideoPath);
        }
    }

    /* JADX INFO: compiled from: DepthPresetDownloadManager.kt */
    @Metadata
    public final class VideoDownloadCallback extends IVideoDownloadCallback.Stub {
        public VideoDownloadCallback() {
        }

        @Override // com.miui.videodownload.IVideoDownloadCallback
        public void onDownloadstatechanged(int i, Bundle bundle) throws Exception {
            DownloadButtonCallback downloadButtonCallback;
            Context context = (Context) DepthPresetDownloadManager.this.getContextWeak().get();
            if (context == null) {
                Log.e(DepthPresetDownloadManager.this.TAG, "onDownloadstatechanged: context is null");
                return;
            }
            Log.i(DepthPresetDownloadManager.this.TAG, "onDownloadStateChanged state:" + i + ", bundle:" + bundle);
            if (i == 1005) {
                Uri uri = bundle != null ? (Uri) bundle.getParcelable("finalSavePath") : null;
                FileUtil.copyPresetVideo(context, FileUtil.createDrawableDir(context, DepthPresetDownloadManager.Companion.getTHEME_VIDEO_PATH()), uri != null ? uri.toString() : null);
            }
            if (i == 1002) {
                Integer numValueOf = bundle != null ? Integer.valueOf(bundle.getInt("downloadProgress", 0)) : null;
                DownloadButtonCallback downloadButtonCallback2 = (DownloadButtonCallback) DepthPresetDownloadManager.this.buttonCallbackWeak.get();
                if (downloadButtonCallback2 != null) {
                    downloadButtonCallback2.onChanged(i, numValueOf != null ? numValueOf.intValue() : 0);
                    return;
                }
                return;
            }
            if ((DepthPresetDownloadManager.this.isDownloadSuccessCallbackSuppressed() && i == 1005) || (downloadButtonCallback = (DownloadButtonCallback) DepthPresetDownloadManager.this.buttonCallbackWeak.get()) == null) {
                return;
            }
            downloadButtonCallback.onChanged(i, 0);
        }
    }

    public DepthPresetDownloadManager(WeakReference contextWeak, WeakReference buttonCallbackWeak) {
        Intrinsics.checkNotNullParameter(contextWeak, "contextWeak");
        Intrinsics.checkNotNullParameter(buttonCallbackWeak, "buttonCallbackWeak");
        this.contextWeak = contextWeak;
        this.buttonCallbackWeak = buttonCallbackWeak;
        this.TAG = "Keyguard-Editor-DepthPresetDownloadManager";
        this.mCallback = new VideoDownloadCallback();
        this.mConnection = new ServiceConnection() { // from class: com.miui.keyguard.editor.edit.base.DepthPresetDownloadManager$mConnection$1
            @Override // android.content.ServiceConnection
            public void onBindingDied(ComponentName name) {
                Intrinsics.checkNotNullParameter(name, "name");
                onFinish();
            }

            public final void onFinish() {
                Log.w(this.this$0.TAG, "onFinish: ", new Throwable());
                try {
                    IVideoDownloadService mService = this.this$0.getMService();
                    if (mService != null) {
                        mService.unregisterCallback();
                    }
                } catch (Exception e) {
                    Log.e(this.this$0.TAG, "mService unregisterCallback failed " + e.getMessage());
                }
                this.this$0.setMService(null);
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(ComponentName name) {
                Intrinsics.checkNotNullParameter(name, "name");
                onFinish();
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName className, IBinder service) {
                Intrinsics.checkNotNullParameter(className, "className");
                Intrinsics.checkNotNullParameter(service, "service");
                Log.i(this.this$0.TAG, "onServiceConnected: ");
                try {
                    this.this$0.setMService(IVideoDownloadService.Stub.asInterface(service));
                    if (this.this$0.getMService() == null) {
                        Log.d(this.this$0.TAG, "onServiceConnected: null");
                        return;
                    }
                    IVideoDownloadService mService = this.this$0.getMService();
                    if (mService != null) {
                        mService.registerCallback(this.this$0.getMCallback());
                    }
                    this.this$0.setConnectStatus(true);
                    this.this$0.startDownload();
                } catch (IllegalAccessException e) {
                    Log.w(this.this$0.TAG, "onServiceConnected error: ", e);
                } catch (NoSuchMethodException e2) {
                    Log.w(this.this$0.TAG, "onServiceConnected error: ", e2);
                } catch (InvocationTargetException e3) {
                    Log.w(this.this$0.TAG, "onServiceConnected error: ", e3);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName className) {
                Intrinsics.checkNotNullParameter(className, "className");
                onFinish();
            }
        };
    }

    public final boolean bind() {
        Context context = (Context) this.contextWeak.get();
        if (context == null) {
            Log.e(this.TAG, "bind: context is null");
            return false;
        }
        Log.i(this.TAG, "start bind");
        Intent intent = new Intent("miui.intent.action.DEPTH_VIDEO_DOWNLOAD");
        intent.setPackage("com.android.thememanager");
        boolean zBindService = context.bindService(intent, this.mConnection, 1);
        this.bindStatus = zBindService;
        if (!zBindService) {
            UserHandle userHandleMyUserHandle = Process.myUserHandle();
            Log.w(this.TAG, "bindService failed, try userHandle " + userHandleMyUserHandle);
            try {
                this.bindStatus = context.bindServiceAsUser(intent, this.mConnection, 1, userHandleMyUserHandle);
                Unit unit = Unit.INSTANCE;
            } catch (Exception e) {
                Log.e(this.TAG, "bindServiceAsUser failed with exception: " + e.getMessage(), e);
            }
        }
        return this.bindStatus;
    }

    public final boolean checkThemeCTA() throws Throwable {
        try {
            Context context = (Context) this.contextWeak.get();
            if (context == null) {
                throw new Throwable("context is null");
            }
            Bundle bundleCall = context.getContentResolver().call(Uri.parse("content://com.android.thememanager.theme_provider"), "isThemeCtaAllowed", (String) null, (Bundle) null);
            Boolean boolValueOf = bundleCall != null ? Boolean.valueOf(bundleCall.getBoolean("ctaAllowed")) : null;
            Log.d(this.TAG, "checkThemeCTA: " + boolValueOf);
            if (boolValueOf != null) {
                return boolValueOf.booleanValue();
            }
            return false;
        } catch (Exception e) {
            Log.e(this.TAG, "checkThemeCTA failed " + e);
            return false;
        }
    }

    public final boolean checkVideoFinish() {
        TemplateConfig lockscreenInfo;
        Companion companion = Companion;
        CommonConfig commonConfig = this.templateConfig;
        String presetDepthVideoPath = companion.getPresetDepthVideoPath((commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo.getWallpaperInfo());
        Log.d(this.TAG, "checkVideoFinish path: " + presetDepthVideoPath);
        if (!FileUtil.isFileExist(presetDepthVideoPath)) {
            return false;
        }
        createWallpaperInfo(presetDepthVideoPath);
        return true;
    }

    public final void createWallpaperInfo(String path) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfoForVideo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfoForVideo2;
        TemplateConfig lockscreenInfo3;
        Intrinsics.checkNotNullParameter(path, "path");
        Log.i(this.TAG, "createWallpaperInfo path: " + path);
        CommonConfig commonConfig = this.templateConfig;
        if (commonConfig == null) {
            Log.w(this.TAG, "createWallpaperInfo: templateConfig is null");
            return;
        }
        if (commonConfig != null && (lockscreenInfo3 = commonConfig.getLockscreenInfo()) != null) {
            lockscreenInfo3.setWallpaperInfoForVideo(new WallpaperInfo(path, null, null, null, null, MagicType.TYPE_DEPTH_VIDEO, null, null, "video", path, false, null, false, null, false, null, false, true, path, null, null, null, null, 0, false, 1, 0, null, false, null, null, 2088281088, null));
        }
        CommonConfig commonConfig2 = this.templateConfig;
        if (commonConfig2 != null && (lockscreenInfo2 = commonConfig2.getLockscreenInfo()) != null && (wallpaperInfoForVideo2 = lockscreenInfo2.getWallpaperInfoForVideo()) != null) {
            wallpaperInfoForVideo2.setInfoType(WallpaperInfoType.LOCK_WALLPAPER);
        }
        String str = this.TAG;
        CommonConfig commonConfig3 = this.templateConfig;
        Log.i(str, "wallpaperInfoForVideo: " + ((commonConfig3 == null || (lockscreenInfo = commonConfig3.getLockscreenInfo()) == null || (wallpaperInfoForVideo = lockscreenInfo.getWallpaperInfoForVideo()) == null) ? null : wallpaperInfoForVideo.toString()));
    }

    public final boolean getConnectStatus() {
        return this.connectStatus;
    }

    public final WeakReference getContextWeak() {
        return this.contextWeak;
    }

    public final VideoDownloadCallback getMCallback() {
        return this.mCallback;
    }

    public final IVideoDownloadService getMService() {
        return this.mService;
    }

    public final CommonConfig getTemplateConfig() {
        return this.templateConfig;
    }

    public final boolean isDownloadSuccessCallbackSuppressed() {
        return this.isDownloadSuccessCallbackSuppressed;
    }

    public final void jumpToThemeCta() {
        Context context = (Context) this.contextWeak.get();
        if (context == null) {
            Log.e(this.TAG, "jumpToThemeCta: context is null");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("miui.thememanager.ALL_CTA_AND_NETWORK");
        intent.putExtra("editEntrance", true);
        intent.putExtra("openSource", 0);
        Intent intent2 = new Intent();
        intent2.setAction("miui.thememanager.SHOW_CTA");
        intent2.putExtra("editEntrance", true);
        intent2.putExtra("openSource", 0);
        Intent intent3 = new Intent();
        intent3.setClassName("com.android.thememanager", "com.android.thememanager.ThemeResourceTabActivity");
        try {
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return;
            }
            if (intent2.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent2);
            } else if (intent3.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent3);
            } else {
                Log.e(this.TAG, "startThemeAppCta fail");
            }
        } catch (Exception e) {
            String str = this.TAG;
            String message = e.getMessage();
            if (message == null) {
                message = "";
            }
            Log.e(str, "startThemeAppCta exception " + message);
        }
    }

    public final void pauseDownload() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        Bundle bundle = new Bundle();
        CommonConfig commonConfig = this.templateConfig;
        String sha1 = null;
        bundle.putString("downloadUrl", (commonConfig == null || (lockscreenInfo3 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getDownloadUrl());
        CommonConfig commonConfig2 = this.templateConfig;
        bundle.putString("videoName", (commonConfig2 == null || (lockscreenInfo2 = commonConfig2.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getVideoName());
        CommonConfig commonConfig3 = this.templateConfig;
        if (commonConfig3 != null && (lockscreenInfo = commonConfig3.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
            sha1 = wallpaperInfo.getSha1();
        }
        bundle.putString("sha1", sha1);
        Log.i(this.TAG, "pauseDownload: " + bundle);
        IVideoDownloadService iVideoDownloadService = this.mService;
        if (iVideoDownloadService != null) {
            iVideoDownloadService.pauseDownload(bundle);
        }
    }

    public final void setConnectStatus(boolean z) {
        this.connectStatus = z;
    }

    public final void setDownloadSuccessCallbackSuppressed(boolean z) {
        this.isDownloadSuccessCallbackSuppressed = z;
    }

    public final void setMService(IVideoDownloadService iVideoDownloadService) {
        this.mService = iVideoDownloadService;
    }

    public final void setTemplateConfig(CommonConfig commonConfig) {
        this.templateConfig = commonConfig;
    }

    public final void startDownload() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        Long fileSize;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo4;
        WallpaperInfo wallpaperInfo4;
        this.isDownloadSuccessCallbackSuppressed = false;
        Bundle bundle = new Bundle();
        CommonConfig commonConfig = this.templateConfig;
        String sha1 = null;
        bundle.putString("downloadUrl", (commonConfig == null || (lockscreenInfo4 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo4 = lockscreenInfo4.getWallpaperInfo()) == null) ? null : wallpaperInfo4.getDownloadUrl());
        CommonConfig commonConfig2 = this.templateConfig;
        bundle.putString("videoName", (commonConfig2 == null || (lockscreenInfo3 = commonConfig2.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getVideoName());
        CommonConfig commonConfig3 = this.templateConfig;
        if (commonConfig3 != null && (lockscreenInfo2 = commonConfig3.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) != null) {
            sha1 = wallpaperInfo2.getSha1();
        }
        bundle.putString("sha1", sha1);
        CommonConfig commonConfig4 = this.templateConfig;
        bundle.putLong("fileSize", (commonConfig4 == null || (lockscreenInfo = commonConfig4.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null || (fileSize = wallpaperInfo.getFileSize()) == null) ? -1L : fileSize.longValue());
        Log.i(this.TAG, "startDownload: " + bundle);
        IVideoDownloadService iVideoDownloadService = this.mService;
        if (iVideoDownloadService != null) {
            iVideoDownloadService.startDownload(bundle);
        }
    }

    public final void unbind() {
        Context context = (Context) this.contextWeak.get();
        if (context == null) {
            Log.e(this.TAG, "unbind: context is null");
            return;
        }
        Log.i(this.TAG, "start unbind");
        try {
            if (this.bindStatus) {
                pauseDownload();
                context.unbindService(this.mConnection);
            }
        } catch (Exception e) {
            Log.w(this.TAG, "unbind error: ", e);
        }
    }
}
