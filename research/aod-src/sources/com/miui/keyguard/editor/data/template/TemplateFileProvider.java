package com.miui.keyguard.editor.data.template;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Log;
import com.miui.keyguard.editor.SettingsEditorServiceManager;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.aiwapper.AIWallPaperManager;
import com.miui.keyguard.editor.settings.SettingsTemplatesProviderManager;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.ProcessManager;
import com.miui.keyguard.editor.viewmodel.ApplyStatusManager;
import com.miui.maml.folme.AnimatedProperty;
import java.io.File;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TemplateFileProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TemplateFileProvider extends ContentProvider {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.content.ContentProvider
    public Bundle call(String method, String str, Bundle bundle) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        String source;
        Context context;
        Context context2;
        String string;
        Intrinsics.checkNotNullParameter(method, "method");
        ProcessManager.INSTANCE.interrupt();
        Log.d("Keyguard-Editor-TemplateFileProvider", "call: method = " + method + ", arg = " + str + ", extras = " + bundle);
        Bundle bundle2 = new Bundle();
        ParcelFileDescriptor parcelFileDescriptorWriteOrReadFile = null;
        switch (method.hashCode()) {
            case -1911420754:
                if (method.equals("select_style")) {
                    AIWallPaperManager.Companion.getInstance().handleSelectedStyle(bundle);
                    return bundle2;
                }
                return bundle2;
            case -1755161594:
                if (method.equals("callGetHistoryWallpaper")) {
                    Log.i("Keyguard-Editor-TemplateFileProvider", "CALL_GET_HISTORY_WALLPAPER start");
                    CommonConfig historyConfig = SettingsTemplatesProviderManager.INSTANCE.getHistoryConfig(getContext(), bundle);
                    if (getContext() != null && historyConfig != null && (lockscreenInfo = historyConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null && (source = wallpaperInfo.getSource()) != null) {
                        ParcelFileDescriptor parcelFileDescriptorOpenReadOnlyFile = openReadOnlyFile(source);
                        bundle2.putParcelable("parcelFileDescriptor", parcelFileDescriptorOpenReadOnlyFile);
                        Log.i("Keyguard-Editor-TemplateFileProvider", "CALL_GET_HISTORY_WALLPAPER parseFileDescriptor=" + parcelFileDescriptorOpenReadOnlyFile);
                        return bundle2;
                    }
                }
                return bundle2;
            case -1683885273:
                if (method.equals("is_generating")) {
                    bundle2.putBoolean("isGenerating", AIWallPaperManager.Companion.getInstance().isGenerateRunning());
                    return bundle2;
                }
                return bundle2;
            case -1547878349:
                if (method.equals("change_wallpaper")) {
                    AIWallPaperManager.Companion.getInstance().handleWallpaperChange(bundle);
                    return bundle2;
                }
                return bundle2;
            case -1248128707:
                if (method.equals("callGetHistoryTemplates") && (context = getContext()) != null) {
                    ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
                    int i = 0;
                    for (Object obj : TemplateApi.DefaultImpls.getHistoryTemplates$default(TemplateApi.Companion.getInstance(context), false, 1, null)) {
                        int i2 = i + 1;
                        if (i < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        TemplateHistoryConfig templateHistoryConfig = (TemplateHistoryConfig) obj;
                        boolean z = templateHistoryConfig.getMagicType() != 100000 || FileUtil.isSupportVideoDepth();
                        boolean z2 = Intrinsics.areEqual(templateHistoryConfig.getResourceType(), "sensor") && !CommonUtils.INSTANCE.isFlipOrFoldDevice();
                        if (z && !z2) {
                            arrayList.add(templateHistoryConfig);
                        }
                        i = i2;
                    }
                    Log.d("Keyguard-Editor-TemplateFileProvider", "CALL_GET_HISTORY_TEMPLATES historyTemplates.size: " + arrayList.size());
                    bundle2.putParcelableArrayList("historyTemplates", arrayList);
                    return bundle2;
                }
                return bundle2;
            case -1094959266:
                if (method.equals("callApplyHistoryTemplate")) {
                    SettingsTemplatesProviderManager.INSTANCE.applyHistoryTemplate(getContext(), bundle, bundle2);
                    return bundle2;
                }
                return bundle2;
            case -1011546954:
                if (method.equals("callRefreshCurrentTemplate")) {
                    if (ApplyStatusManager.INSTANCE.isApplying()) {
                        Log.d("Keyguard-Editor-TemplateFileProvider", "CALL_REFRESH_CURRENT_TEMPLATE: applying in progress, skip refreshing");
                        return bundle2;
                    }
                    Log.d("Keyguard-Editor-TemplateFileProvider", "CALL_REFRESH_CURRENT_TEMPLATE: not applying, refreshing current template");
                    SettingsTemplatesProviderManager.INSTANCE.refreshCurrentTemplateInKeyEditor(getContext());
                    return bundle2;
                }
                return bundle2;
            case -990900462:
                if (method.equals("reset_wallpaper")) {
                    AIWallPaperManager.Companion.getInstance().handleWallpaperReset();
                    return bundle2;
                }
                return bundle2;
            case -622172095:
                if (method.equals("callGetFileDescriptorByFileName") && (context2 = getContext()) != null) {
                    int i3 = bundle != null ? bundle.getInt("file_type") : 1;
                    if (bundle == null || (string = bundle.getString("file_mode")) == null) {
                        string = "r";
                    }
                    if (str != null) {
                        if (i3 == 1) {
                            parcelFileDescriptorWriteOrReadFile = writeOrReadFile(string, new TemplateFilePathGenerator(context2).generateCurrentTemplateByFileName(str));
                        } else if (i3 == 4) {
                            parcelFileDescriptorWriteOrReadFile = writeOrReadFile(string, new TemplateFilePathGenerator(context2).generateFilePathByName(str));
                        }
                    }
                    bundle2.putParcelable("parcelFileDescriptor", parcelFileDescriptorWriteOrReadFile);
                    return bundle2;
                }
                return bundle2;
            case -312499131:
                if (method.equals("callDeleteHistoryTemplate")) {
                    SettingsTemplatesProviderManager.INSTANCE.deleteHistory(getContext(), bundle, bundle2);
                    return bundle2;
                }
                return bundle2;
            case -290513102:
                if (method.equals("update_task_info")) {
                    AIWallPaperManager.Companion.getInstance().handleUpdateTaskInfo(bundle);
                    return bundle2;
                }
                return bundle2;
            case -145594484:
                if (method.equals("update_task_cross_view_pager")) {
                    SettingsEditorServiceManager.INSTANCE.updateCrossPager();
                    return bundle2;
                }
                return bundle2;
            case 243753795:
                if (method.equals("callApplyCurrentTemplate")) {
                    SettingsTemplatesProviderManager.INSTANCE.applyCurrentTemplate(getContext(), bundle, bundle2);
                    return bundle2;
                }
                return bundle2;
            case 649485895:
                if (method.equals("generate_failed")) {
                    AIWallPaperManager.Companion.getInstance().handleGenerateFailed(bundle);
                    return bundle2;
                }
                return bundle2;
            case 1203819587:
                if (method.equals("callGetFileDescriptor") && getContext() != null) {
                    bundle2.putParcelable("parcelFileDescriptor", str != null ? openReadOnlyFile(str) : null);
                    return bundle2;
                }
                return bundle2;
            default:
                return bundle2;
        }
    }

    public final boolean checkMode(String str, String str2) {
        if (Intrinsics.areEqual(str, str2)) {
            return true;
        }
        Log.w("Keyguard-Editor-TemplateFileProvider", "openFile: mode " + str + " is unsupported !");
        return false;
    }

    public final ParcelFileDescriptor createParcelFileDes(String str, int i) {
        try {
            File file = new File(str);
            FileUtil.makeFileExists(file);
            return ParcelFileDescriptor.open(file, i);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateFileProvider", "openFile: error ! " + e);
            return null;
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        if (isValidUri(uri)) {
            return "image/png";
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    public final boolean isValidUri(Uri uri) {
        String path = uri.getPath();
        return Intrinsics.areEqual(path, "/lastImageLockScreenWallpaper") || Intrinsics.areEqual(path, "/thirdPartyPreviewImage") || Intrinsics.areEqual(path, "/template_current/wallpaper") || Intrinsics.areEqual(path, "/template_current/wallpaper_cropped") || Intrinsics.areEqual(path, "/editor_wallpaper") || Intrinsics.areEqual(path, "/template_current/wallpaper_home");
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Log.i("Keyguard-Editor-TemplateFileProvider", "onCreate: ");
        return true;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.content.ContentProvider
    public ParcelFileDescriptor openFile(Uri uri, String mode) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Context context = getContext();
        if (context == null) {
            return null;
        }
        TemplateFilePathGenerator templateFilePathGenerator = new TemplateFilePathGenerator(context);
        Log.d("Keyguard-Editor-TemplateFileProvider", "openFile: uri = " + uri);
        String path = uri.getPath();
        if (path != null) {
            switch (path.hashCode()) {
                case -2147432170:
                    if (path.equals("/template_current/wallpaper_cropped")) {
                        if (!checkMode(mode, "r")) {
                            return null;
                        }
                        Log.i("Keyguard-Editor-TemplateFileProvider", "openFile: open wallpaper_cropped");
                        return openReadOnlyFile(templateFilePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_CROPPED));
                    }
                    break;
                case -2085865131:
                    if (path.equals("/lastImageLockScreenWallpaper")) {
                        if (!checkMode(mode, AnimatedProperty.PROPERTY_NAME_W)) {
                            return null;
                        }
                        Log.i("Keyguard-Editor-TemplateFileProvider", "openFile: open lastImageLockScreenWallpaper");
                        return openLocalFile(templateFilePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER));
                    }
                    break;
                case -1897221951:
                    if (path.equals("/thirdPartyPreviewImage")) {
                        if (!checkMode(mode, AnimatedProperty.PROPERTY_NAME_W)) {
                            return null;
                        }
                        Log.i("Keyguard-Editor-TemplateFileProvider", "openFile: open thirdPartyPreviewImage");
                        return openLocalFile(templateFilePathGenerator.generateFilePath(TemplateFilePathGenerator.TemplateFileType.THIRD_PARTY_THEME_PREVIEW));
                    }
                    break;
                case -1457493171:
                    if (path.equals("/template_current/wallpaper_copied")) {
                        Log.i("Keyguard-Editor-TemplateFileProvider", "openFile: open wallpaper_copied");
                        if (!checkMode(mode, "r")) {
                            return null;
                        }
                        Log.i("Keyguard-Editor-TemplateFileProvider", "openFile: open wallpaper_copied");
                        return openReadOnlyFile(templateFilePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_COPIED));
                    }
                    break;
                case -894096426:
                    if (path.equals("/template_current/wallpaper")) {
                        if (!checkMode(mode, "r")) {
                            return null;
                        }
                        Log.i("Keyguard-Editor-TemplateFileProvider", "openFile: open origin_wallpaper");
                        return openReadOnlyFile(templateFilePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER));
                    }
                    break;
                case -178547111:
                    if (path.equals("/currentDarkenWallpaper")) {
                        Log.i("Keyguard-Editor-TemplateFileProvider", "openFile: open currentDarkenWallpaper");
                        return createParcelFileDes(templateFilePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.CURRENT_DARKEN_WALLPAPER), 939524096);
                    }
                    break;
                case 1104005087:
                    if (path.equals("/editor_wallpaper")) {
                        Log.i("Keyguard-Editor-TemplateFileProvider", "openFile: open new template wallpaper");
                        if (!checkMode(mode, "r")) {
                            return null;
                        }
                        String mOriginResourcePath = AIWallPaperManager.Companion.getInstance().getMOriginResourcePath();
                        if (mOriginResourcePath == null) {
                            mOriginResourcePath = "";
                        }
                        return openReadOnlyFile(mOriginResourcePath);
                    }
                    break;
                case 1902540712:
                    if (path.equals("/template_current/wallpaper_home")) {
                        if (!checkMode(mode, "r")) {
                            return null;
                        }
                        Log.i("Keyguard-Editor-TemplateFileProvider", "openFile: open wallpaper_home");
                        return openReadOnlyFile(templateFilePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME));
                    }
                    break;
            }
        }
        Log.w("Keyguard-Editor-TemplateFileProvider", "openFile: path " + path + " is unsupported !");
        return null;
    }

    public final ParcelFileDescriptor openLocalFile(String str) {
        try {
            File file = new File(str);
            FileUtil.makeFileExists(file);
            return ParcelFileDescriptor.open(file, 671088640);
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateFileProvider", "openFile: error ! " + e);
            return null;
        }
    }

    public final ParcelFileDescriptor openReadOnlyFile(String str) {
        try {
            File file = new File(str);
            if (file.exists()) {
                return ParcelFileDescriptor.open(file, 268435456);
            }
            Log.w("Keyguard-Editor-TemplateFileProvider", "openReadOnlyFile failed: file not exists. path = " + str);
            return null;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-TemplateFileProvider", "openFile: error ! " + e);
            return null;
        }
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }

    public final ParcelFileDescriptor writeOrReadFile(String str, String str2) {
        if (str2 != null && str2.length() != 0) {
            return checkMode(str, AnimatedProperty.PROPERTY_NAME_W) ? openLocalFile(str2) : openReadOnlyFile(str2);
        }
        Log.w("Keyguard-Editor-TemplateFileProvider", "Invalid file path: " + str2);
        return null;
    }
}
