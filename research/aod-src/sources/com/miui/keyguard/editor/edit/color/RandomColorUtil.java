package com.miui.keyguard.editor.edit.color;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
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
/* JADX INFO: compiled from: RandomColorUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class RandomColorUtil {
    public static final RandomColorUtil INSTANCE = new RandomColorUtil();

    public static /* synthetic */ void tryRandomColorType$default(RandomColorUtil randomColorUtil, TemplateConfig templateConfig, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        randomColorUtil.tryRandomColorType(templateConfig, z);
    }

    public final int getCurrentRandomColorType(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return Settings.Secure.getInt(context.getContentResolver(), "color_scheme", 0);
    }

    public final boolean isSupportRandomColor(TemplateConfig templateConfig) {
        String templateId = templateConfig.getClockInfo().getTemplateId();
        return Intrinsics.areEqual(templateId, "doodle") || Intrinsics.areEqual(templateId, "smart_frame");
    }

    public final int randomColorType() {
        int i = 3;
        long j = 3;
        long jCurrentTimeMillis = System.currentTimeMillis() % j;
        int i2 = (int) (jCurrentTimeMillis + (j & (((jCurrentTimeMillis ^ j) & ((-jCurrentTimeMillis) | jCurrentTimeMillis)) >> 63)));
        if (i2 == 0) {
            i = 1;
        } else if (i2 == 1) {
            i = 2;
        }
        Log.i("Keyguard-Editor-RandomColorUtil", "randomColorType: " + i);
        return i;
    }

    public final void tryRandomColorType(TemplateConfig config, boolean z) {
        Intrinsics.checkNotNullParameter(config, "config");
        Log.i("Keyguard-Editor-RandomColorUtil", "tryRandomColorType: " + config.getRandomColorType() + ", wallpaperChanged=" + z);
        if (isSupportRandomColor(config)) {
            config.setRandomColorType((!Intrinsics.areEqual(config.getClockInfo().getTemplateId(), "doodle") || z) ? randomColorType() : 2);
        }
    }

    public final void updateCurrentRandomColorType(Context context, TemplateConfig config) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(config, "config");
        if (isSupportRandomColor(config)) {
            updateCurrentRandomColorTypeInternal(context, config.getRandomColorType());
        }
    }

    public final void updateCurrentRandomColorTypeInternal(Context context, int i) {
        Log.i("Keyguard-Editor-RandomColorUtil", "updateCurrentRandomColorType: " + i);
        Settings.Secure.putInt(context.getContentResolver(), "color_scheme", i);
    }
}
