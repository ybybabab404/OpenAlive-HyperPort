package com.miui.aod.gemini;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AodConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AodConfigKt {
    @NotNull
    public static final Context createEnglishContext(@NotNull Context baseContext) {
        Intrinsics.checkNotNullParameter(baseContext, "baseContext");
        Configuration configuration = new Configuration(baseContext.getResources().getConfiguration());
        configuration.setLocale(Locale.US);
        Context contextCreateConfigurationContext = baseContext.createConfigurationContext(configuration);
        Intrinsics.checkNotNullExpressionValue(contextCreateConfigurationContext, "createConfigurationContext(...)");
        return contextCreateConfigurationContext;
    }

    @NotNull
    public static final List<GeminiScreenConfig> createScreens(@NotNull Context context, @NotNull Context englishContext) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(englishContext, "englishContext");
        return CollectionsKt.listOf((Object[]) new GeminiScreenConfig[]{new AodNotificationAnimationScreen(context, englishContext), new AodStateScreen(context, englishContext), new AodDisplayModeScreen(context, englishContext)});
    }

    public static final boolean isSupportAppfunction() {
        return true;
    }

    @Nullable
    public static final String makeUpIntentUri(@Nullable Intent intent) {
        if (intent != null) {
            return intent.toUri(1);
        }
        return null;
    }
}
