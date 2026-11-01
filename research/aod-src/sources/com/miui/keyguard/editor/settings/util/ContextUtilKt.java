package com.miui.keyguard.editor.settings.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.miui.keyguard.editor.CommonEditorActivity;
import com.miui.keyguard.editor.edit.base.WallpaperEditor;
import com.miui.keyguard.editor.utils.DeviceUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miui.os.Build;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ContextUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ContextUtilKt {
    public static final boolean isActivityInvalid(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (!(context instanceof Activity)) {
            return false;
        }
        Activity activity = (Activity) context;
        return activity.isFinishing() || activity.isDestroyed();
    }

    public static final void navigatorToCommonEditorActivity(Context context, int i, String configPath, long j) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(configPath, "configPath");
        String str = i == 1 ? "lock" : "desktop";
        Intent intent = new Intent(context, (Class<?>) CommonEditorActivity.class);
        intent.setAction("miui.keyguard.editor.common");
        intent.setComponent(new ComponentName("com.miui.aod", "com.miui.keyguard.editor.CommonEditorActivity"));
        intent.putExtra("caller", str);
        intent.putExtra("argConfigPath", configPath);
        intent.putExtra("argTemplateSource", j);
        intent.putExtra("whereFrom", "homepage");
        intent.setFlags(268468224);
        Activity activity = context instanceof Activity ? (Activity) context : null;
        if (activity != null) {
            activity.startActivity(intent);
        }
    }

    public static final void navigatorToFloorActivity(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intent intent = new Intent();
        intent.setAction("miui.keyguard.editor.settings");
        if (i >= 49) {
            intent.putExtra("cross_position", 1);
        }
        intent.setFlags(268468224);
        Activity activity = context instanceof Activity ? (Activity) context : null;
        if (activity != null) {
            activity.startActivity(intent);
        }
    }

    public static final void navigatorToThemePicker(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intent intent = new Intent();
        intent.setAction(WallpaperEditor.Companion.getACTION_THEME_IMAGE_LIST_PICKER_AOD());
        if (Build.IS_INTERNATIONAL_BUILD) {
            intent.setComponent(new ComponentName("com.android.thememanager", "com.android.thememanager.settings.WallpaperSettingsPickerActivity"));
        } else {
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if (deviceUtil.isFold()) {
                intent.setComponent(new ComponentName("com.android.thememanager", "com.android.thememanager.settings.ThemeAndWallpaperPickerSettingActivity"));
            } else if (deviceUtil.isPad()) {
                intent.setComponent(new ComponentName("com.android.thememanager", "com.android.thememanager.settings.ThemeAndWallpaperSettingDialogActivity"));
            }
        }
        intent.putExtra("openSource", 2);
        Activity activity = context instanceof Activity ? (Activity) context : null;
        if (activity != null) {
            activity.startActivity(intent);
        }
        SettingsTrackHelper settingsTrackHelper = SettingsTrackHelper.INSTANCE;
        settingsTrackHelper.trackNegativeCardClick(context);
        SettingsTrackHelper.trackUsedTemplate$default(settingsTrackHelper, context, null, "新建搭配", null, 10, null);
    }
}
