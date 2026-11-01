package com.miui.keyguard.editor.settings;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.SignatureInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.CurrentTemplateApi;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateApiImpl;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import com.miui.keyguard.editor.data.template.WallpaperInfoType;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.GsonSingletonKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: SettingsTemplatesProviderManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SettingsTemplatesProviderManager {
    public static final SettingsTemplatesProviderManager INSTANCE = new SettingsTemplatesProviderManager();
    public static volatile boolean isApplying;

    public final void applyCurrentTemplate(Context context, Bundle bundle, Bundle result) {
        String string;
        TemplateConfig lockscreenInfo;
        SignatureInfo signatureInfo;
        TemplateConfig lockscreenInfo2;
        ClockInfo clockInfo;
        TemplateConfig lockscreenInfo3;
        ClockInfo clockInfo2;
        HomeConfig homeInfo;
        TemplateConfig lockscreenInfo4;
        String string2;
        Intrinsics.checkNotNullParameter(result, "result");
        Log.d("SettingsTemplatesProviderManager", "CALL_APPLY_CURRENT_TEMPLATE start");
        if (context != null) {
            isApplying = true;
            String str = "";
            if (bundle == null || (string = bundle.getString("configUri")) == null) {
                string = "";
            }
            if (Intrinsics.areEqual(string, "@MINE") && (string2 = Settings.Secure.getString(context.getContentResolver(), "constant_template_editor_info")) != null) {
                str = string2;
            }
            if (StringsKt.isBlank(str)) {
                Log.e("SettingsTemplatesProviderManager", "Empty settings template info");
                result.putBoolean("resultApplyCurrentTemplate", false);
                isApplying = false;
                return;
            }
            try {
                Object objFromJson = GsonSingletonKt.getGSON_SINGLETON().fromJson(str, (Class<Object>) CommonConfig.class);
                CommonConfig commonConfig = (CommonConfig) objFromJson;
                WallpaperInfo wallpaperInfo = commonConfig.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo != null) {
                    wallpaperInfo.setInfoType(WallpaperInfoType.LOCK_WALLPAPER);
                }
                WallpaperInfo wallpaperInfo2 = commonConfig.getHomeInfo().getWallpaperInfo();
                if (wallpaperInfo2 != null) {
                    wallpaperInfo2.setInfoType(WallpaperInfoType.HOME_WALLPAPER);
                }
                CommonConfig commonConfig2 = (CommonConfig) objFromJson;
                TemplateApi companion = TemplateApi.Companion.getInstance(context);
                Integer numValueOf = null;
                WallpaperInfo wallpaperInfo3 = (commonConfig2 == null || (lockscreenInfo4 = commonConfig2.getLockscreenInfo()) == null) ? null : lockscreenInfo4.getWallpaperInfo();
                WallpaperInfo wallpaperInfo4 = (commonConfig2 == null || (homeInfo = commonConfig2.getHomeInfo()) == null) ? null : homeInfo.getWallpaperInfo();
                WallpaperController.Companion companion2 = WallpaperController.Companion;
                String templateId = (commonConfig2 == null || (lockscreenInfo3 = commonConfig2.getLockscreenInfo()) == null || (clockInfo2 = lockscreenInfo3.getClockInfo()) == null) ? null : clockInfo2.getTemplateId();
                Integer numValueOf2 = (commonConfig2 == null || (lockscreenInfo2 = commonConfig2.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo2.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo.getStyle());
                if (commonConfig2 != null && (lockscreenInfo = commonConfig2.getLockscreenInfo()) != null && (signatureInfo = lockscreenInfo.getSignatureInfo()) != null) {
                    numValueOf = Integer.valueOf(signatureInfo.getAlignment());
                }
                companion.reapplyCurrentWallpaper(wallpaperInfo3, wallpaperInfo4, companion2.getPickWallpaperColorInfo(templateId, numValueOf2, numValueOf));
                Log.d("SettingsTemplatesProviderManager", "CALL_APPLY_CURRENT_TEMPLATE end");
                result.putBoolean("resultApplyCurrentTemplate", true);
                isApplying = false;
            } catch (Exception e) {
                result.putBoolean("resultApplyCurrentTemplate", false);
                Log.e("SettingsTemplatesProviderManager", "Failed to parse template config", e);
                isApplying = false;
            }
        }
    }

    public final void applyHistoryTemplate(Context context, Bundle bundle, Bundle result) {
        Intrinsics.checkNotNullParameter(result, "result");
        Log.d("SettingsTemplatesProviderManager", "CALL_APPLY_HISTORY_TEMPLATE start, waitFlush=" + TemplateApiImpl.Companion.isWaitingFlush());
        if (context != null) {
            isApplying = true;
        }
    }

    public final void deleteHistory(Context context, Bundle bundle, Bundle result) {
        Intrinsics.checkNotNullParameter(result, "result");
        Log.d("SettingsTemplatesProviderManager", "CALL_DELETE_HISTORY_TEMPLATE start");
        if (context != null) {
            long j = bundle != null ? bundle.getLong("entityId") : 0L;
            TemplateApi.Companion companion = TemplateApi.Companion;
            TemplateHistoryConfig historyTemplate = companion.getInstance(context).getHistoryTemplate(j);
            if (historyTemplate != null) {
                result.putBoolean("resultDeleteTemplate", companion.getInstance(context).deleteHistoryTemplate(historyTemplate, false, true));
            }
        }
    }

    public final CommonConfig getHistoryConfig(Context context, Bundle bundle) {
        String string;
        if (context == null) {
            return null;
        }
        if (bundle == null || (string = bundle.getString("configUri")) == null) {
            string = "";
        }
        return TemplateDataUtil.getTemplateConfig(context, string, false, bundle != null ? bundle.getInt("hideFlag") : 0);
    }

    public final boolean isApplying() {
        return isApplying;
    }

    public final void refreshCurrentTemplateInKeyEditor(Context context) {
        Log.d("SettingsTemplatesProviderManager", "CALL_REFRESH_CURRENT_TEMPLATE start");
        if (context != null) {
            CurrentTemplateApi.requireCurrentTemplate$default(new CurrentTemplateApi(context), null, 1, null);
        }
        Log.d("SettingsTemplatesProviderManager", "CALL_REFRESH_CURRENT_TEMPLATE start");
    }

    public final void setApplying(boolean z) {
        isApplying = z;
    }
}
