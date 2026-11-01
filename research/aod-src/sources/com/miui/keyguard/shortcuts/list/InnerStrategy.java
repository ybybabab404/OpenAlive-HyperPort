package com.miui.keyguard.shortcuts.list;

import android.content.Context;
import com.miui.keyguard.shortcuts.utils.ViewUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.strategy.ActionBarConfig;
import miuix.appcompat.app.strategy.ActionBarSpec;
import miuix.appcompat.app.strategy.CommonActionBarStrategy;
import org.jetbrains.annotations.NotNull;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ShortcutListAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class InnerStrategy extends CommonActionBarStrategy {

    @NotNull
    private final Context context;

    public InnerStrategy(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // miuix.appcompat.app.strategy.CommonActionBarStrategy, miuix.appcompat.app.strategy.IActionBarStrategy
    @NotNull
    public ActionBarConfig config(@NotNull ActionBar actionBar, @NotNull ActionBarSpec actionBarSpec) {
        Intrinsics.checkNotNullParameter(actionBar, "actionBar");
        Intrinsics.checkNotNullParameter(actionBarSpec, "actionBarSpec");
        ActionBarConfig actionBarConfigConfig = super.config(actionBar, actionBarSpec);
        if (ViewUtils.INSTANCE.isLargeScreen()) {
            actionBarConfigConfig.expandState = 1;
            actionBarConfigConfig.resizable = false;
        }
        Intrinsics.checkNotNullExpressionValue(actionBarConfigConfig, "apply(...)");
        return actionBarConfigConfig;
    }

    @NotNull
    public final Context getContext() {
        return this.context;
    }
}
