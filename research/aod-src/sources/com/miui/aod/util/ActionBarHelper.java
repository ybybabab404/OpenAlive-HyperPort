package com.miui.aod.util;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AppCompatActivity;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ActionBarHelper.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ActionBarHelper {
    public static final ActionBarHelper INSTANCE = new ActionBarHelper();

    public static final void setupOverlayActionBar(AppCompatActivity activity, View scrollableView) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(scrollableView, "scrollableView");
        ActionBar appCompatActionBar = activity.getAppCompatActionBar();
        if (appCompatActionBar != null) {
            appCompatActionBar.setDisplayHomeAsUpEnabled(true);
            appCompatActionBar.setMaskColor(-16777216);
        }
        activity.registerCoordinateScrollView(scrollableView);
    }

    public static final void setupOverlayActionBarForFragment(AppCompatActivity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        ActionBar appCompatActionBar = activity.getAppCompatActionBar();
        if (appCompatActionBar != null) {
            appCompatActionBar.setMaskColor(-16777216);
        }
    }

    public static final void setupOverlayActionBarForPad(AppCompatActivity activity, View scrollableView) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(scrollableView, "scrollableView");
        ActionBar appCompatActionBar = activity.getAppCompatActionBar();
        if (appCompatActionBar != null) {
            appCompatActionBar.setDisplayHomeAsUpEnabled(true);
        }
        activity.registerCoordinateScrollView(scrollableView);
    }
}
