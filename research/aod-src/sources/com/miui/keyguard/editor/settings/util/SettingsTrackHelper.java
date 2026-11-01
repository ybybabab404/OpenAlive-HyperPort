package com.miui.keyguard.editor.settings.util;

import android.content.Context;
import com.miui.keyguard.editor.track.EditorTracker;
import com.miui.keyguard.editor.track.TrackerUtil;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: SettingsTrackHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SettingsTrackHelper {
    public static final SettingsTrackHelper INSTANCE = new SettingsTrackHelper();

    public static /* synthetic */ void trackUsedTemplate$default(SettingsTrackHelper settingsTrackHelper, Context context, String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str = "个性化设置";
        }
        if ((i & 8) != 0) {
            str3 = null;
        }
        settingsTrackHelper.trackUsedTemplate(context, str, str2, str3);
    }

    public final void trackDeleteTemplate(Context context, String templateNames) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(templateNames, "templateNames");
        if (DeviceUtil.INSTANCE.isInternationalBuild()) {
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("delete_template_style", templateNames);
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("delete", "348.0.0.0.43928", linkedHashMap);
        }
    }

    public final void trackNegativeCardClick(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (DeviceUtil.INSTANCE.isInternationalBuild()) {
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("position", "个性化设置");
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("click", "348.7.0.1.43925", linkedHashMap);
        }
    }

    public final void trackNegativeCardExpose(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (DeviceUtil.INSTANCE.isInternationalBuild()) {
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("position", "个性化设置");
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("expose", "348.7.0.1.43924", linkedHashMap);
        }
    }

    public final void trackUsedTemplate(Context context, String position, String buttonName, String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(buttonName, "buttonName");
        if (DeviceUtil.INSTANCE.isInternationalBuild()) {
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("position", position);
        linkedHashMap.put("button_name", buttonName);
        if (str != null) {
            String templateName = TrackerUtil.INSTANCE.getTemplateName(str);
            if (templateName == null) {
                templateName = "";
            }
            linkedHashMap.put("used_template_style", templateName);
        }
        EditorTracker companion = EditorTracker.Companion.getInstance(context);
        if (companion != null) {
            companion.track("click", "348.7.0.1.43926", linkedHashMap);
        }
    }
}
