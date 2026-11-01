package com.miui.keyguard.editor.utils;

import android.os.Looper;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
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
/* JADX INFO: compiled from: Constants.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ConstantsKt {
    public static final Set CALL_PACKAGE_ALLOW = SetsKt.mutableSetOf("com.android.thememanager", "com.miui.aod", FashionGalleryHelper.INSTANCE.getFashionGalleryPackage(), "com.miui.gallery", "com.android.fileexplorer", "com.mi.android.globalFileexplorer", "com.google.android.apps.bard");
    public static final List NOTIFY_PACKAGES_ON_APPLY = CollectionsKt.listOf((Object[]) new String[]{"com.android.systemui", "com.miui.aod", "com.miui.home", "com.miui.personalassistant", "com.miui.globalminusscreen"});

    public static final void checkOnMainThread() {
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            return;
        }
        throw new IllegalStateException("error Thread " + Thread.currentThread());
    }

    public static final Set getCALL_PACKAGE_ALLOW() {
        return CALL_PACKAGE_ALLOW;
    }

    public static final List getNOTIFY_PACKAGES_ON_APPLY() {
        return NOTIFY_PACKAGES_ON_APPLY;
    }
}
