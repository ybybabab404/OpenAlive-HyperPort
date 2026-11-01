package com.miui.keyguard.shortcuts.list;

import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: ShortcutListAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ShortcutItem {
    public boolean isSelected;
    public final ShortcutEntity shortcutEntity;

    public ShortcutItem(ShortcutEntity shortcutEntity, boolean z) {
        Intrinsics.checkNotNullParameter(shortcutEntity, "shortcutEntity");
        this.shortcutEntity = shortcutEntity;
        this.isSelected = z;
    }

    public /* synthetic */ ShortcutItem(ShortcutEntity shortcutEntity, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(shortcutEntity, (i & 2) != 0 ? false : z);
    }

    public final ShortcutEntity getShortcutEntity() {
        return this.shortcutEntity;
    }

    public final boolean isSelected() {
        return this.isSelected;
    }

    public final void setSelected(boolean z) {
        this.isSelected = z;
    }
}
