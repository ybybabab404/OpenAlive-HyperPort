package com.miui.keyguard.editor.edit.aiwapper;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import androidx.fragment.app.Fragment;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.edit.base.WallpaperEditor;
import com.miui.maml.R;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
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
/* JADX INFO: compiled from: ThemeApiWrapper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ThemeApiWrapper {
    public static final ThemeApiWrapper INSTANCE = new ThemeApiWrapper();

    /* JADX INFO: compiled from: ThemeApiWrapper.kt */
    @Metadata
    public static final class Key {
        public static final Key INSTANCE = new Key();
    }

    /* JADX INFO: compiled from: ThemeApiWrapper.kt */
    @Metadata
    public static final class Method {
        public static final Method INSTANCE = new Method();
    }

    public static final boolean getAiGenerateStatus$lambda$0(Function1 function1, Message message) {
        Bundle data = message.getData();
        Intrinsics.checkNotNullExpressionValue(data, "getData(...)");
        function1.invoke(data);
        return true;
    }

    public final int getAiGenerateStatus(Context context, String str, final Function1 callBack) {
        Intrinsics.checkNotNullParameter(callBack, "callBack");
        Messenger messenger = new Messenger(new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.miui.keyguard.editor.edit.aiwapper.ThemeApiWrapper$$ExternalSyntheticLambda0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return ThemeApiWrapper.getAiGenerateStatus$lambda$0(callBack, message);
            }
        }));
        Bundle bundle = new Bundle();
        bundle.putString("localIdentify", str);
        bundle.putParcelable("replyMessenger", messenger);
        if (context == null) {
            return 0;
        }
        try {
            ContentResolver contentResolver = context.getContentResolver();
            if (contentResolver == null) {
                return 0;
            }
            contentResolver.call("com.android.thememanager.theme_provider", "aiGenerateStatus", (String) null, bundle);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final void notifyEditPageFinish(Context context, String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("taskId", str);
        bundle.putString("localIdentify", str2);
        Log.i("AIWallPaperManager", "notifyEditPageFinish: " + str + "  localIdentify:" + str2);
        if (context != null) {
            try {
                ContentResolver contentResolver = context.getContentResolver();
                if (contentResolver != null) {
                    contentResolver.call("com.android.thememanager.theme_provider", "aiGenerateCancel", (String) null, bundle);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("AIWallPaperManager", "notifyEditPageFinish: error" + e.getMessage());
            }
        }
    }

    public final void restoreWallpaper(Context context, String str, String str2, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent("miui.intent.action.START_AI_RESUME");
        intent.setPackage("com.android.thememanager");
        intent.putExtra("skip_module", "4");
        intent.putExtra("isResumeAi", true);
        intent.putExtra("isFromLockScreen", z);
        intent.putExtra("localIdentify", str);
        intent.putExtra("localPath", str2);
        intent.putExtra("subScene", "wallpaperEditor");
        Log.i("AIWallPaperManager", "restoreWallpaper " + intent + "extras:" + intent.getExtras());
        context.startActivity(intent);
    }

    public final void startAIGenerate(Fragment fragment, String taskId, Uri uri, WallpaperPositionInfo wallpaperPositionInfo, int i, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Intrinsics.checkNotNullParameter(taskId, "taskId");
        Intent intent = new Intent("miui.intent.action.AI_ROUTER");
        intent.setPackage("com.android.thememanager");
        intent.putExtra("skip_module", "4");
        intent.putExtra("aiBizId", taskId);
        intent.putExtra("imageUri", String.valueOf(uri));
        intent.putExtra("isFromLockScreen", z);
        intent.putExtra("left", (wallpaperPositionInfo != null ? (int) wallpaperPositionInfo.getLeft() : 0) * i);
        intent.putExtra("top", (wallpaperPositionInfo != null ? (int) wallpaperPositionInfo.getTop() : 0) * i);
        intent.putExtra("right", (wallpaperPositionInfo != null ? (int) wallpaperPositionInfo.getRight() : 0) * i);
        intent.putExtra("bottom", (wallpaperPositionInfo != null ? (int) wallpaperPositionInfo.getBottom() : 0) * i);
        intent.putExtra("isLivePhoto", z2);
        intent.putExtra("isVideo", z3);
        intent.putExtra("openSource", 6);
        try {
            WallpaperEditor.Companion.setLockPick(true);
            fragment.startActivityForResult(intent, R.styleable.AppCompatTheme_toolbarStyle);
            Log.i("AIWallPaperManager", "startAIGenerate: end");
        } catch (Exception e) {
            Log.e("AIWallPaperManager", "startAIGenerate: error" + e.getMessage());
        }
    }
}
