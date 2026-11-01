package com.miui.keyguard.editor.base;

import android.view.View;
import java.util.ArrayList;
import java.util.List;
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
/* JADX INFO: compiled from: AodViewFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AodClockViews {
    public final List clockViews;
    public View rootView;

    public AodClockViews(View view, List clockViews) {
        Intrinsics.checkNotNullParameter(clockViews, "clockViews");
        this.rootView = view;
        this.clockViews = clockViews;
    }

    public /* synthetic */ AodClockViews(View view, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : view, (i & 2) != 0 ? new ArrayList() : list);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AodClockViews)) {
            return false;
        }
        AodClockViews aodClockViews = (AodClockViews) obj;
        return Intrinsics.areEqual(this.rootView, aodClockViews.rootView) && Intrinsics.areEqual(this.clockViews, aodClockViews.clockViews);
    }

    public final List getClockViews() {
        return this.clockViews;
    }

    public final View getRootView() {
        return this.rootView;
    }

    public int hashCode() {
        View view = this.rootView;
        return ((view == null ? 0 : view.hashCode()) * 31) + this.clockViews.hashCode();
    }

    public final void setRootView(View view) {
        this.rootView = view;
    }

    public String toString() {
        return "AodClockViews(rootView=" + this.rootView + ", clockViews=" + this.clockViews + ")";
    }
}
