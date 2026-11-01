package com.miui.keyguard.editor.edit.base;

import android.graphics.Point;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.edit.EditFragment;
import java.lang.ref.WeakReference;
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
/* JADX INFO: compiled from: CombineEditView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CombineEditParam {
    public final WeakReference editFragmentWeak;
    public Point screenSize;
    public CommonConfig templateConfig;
    public EditFragment.TemplateData templateData;
    public Long templateSource;
    public final CombineSpringTouchManager touchManager;

    public CombineEditParam(WeakReference editFragmentWeak, CombineSpringTouchManager touchManager, Point screenSize, CommonConfig commonConfig, EditFragment.TemplateData templateData, Long l) {
        Intrinsics.checkNotNullParameter(editFragmentWeak, "editFragmentWeak");
        Intrinsics.checkNotNullParameter(touchManager, "touchManager");
        Intrinsics.checkNotNullParameter(screenSize, "screenSize");
        this.editFragmentWeak = editFragmentWeak;
        this.touchManager = touchManager;
        this.screenSize = screenSize;
        this.templateConfig = commonConfig;
        this.templateData = templateData;
        this.templateSource = l;
    }

    public /* synthetic */ CombineEditParam(WeakReference weakReference, CombineSpringTouchManager combineSpringTouchManager, Point point, CommonConfig commonConfig, EditFragment.TemplateData templateData, Long l, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(weakReference, combineSpringTouchManager, point, (i & 8) != 0 ? null : commonConfig, (i & 16) != 0 ? null : templateData, (i & 32) != 0 ? null : l);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CombineEditParam)) {
            return false;
        }
        CombineEditParam combineEditParam = (CombineEditParam) obj;
        return Intrinsics.areEqual(this.editFragmentWeak, combineEditParam.editFragmentWeak) && Intrinsics.areEqual(this.touchManager, combineEditParam.touchManager) && Intrinsics.areEqual(this.screenSize, combineEditParam.screenSize) && Intrinsics.areEqual(this.templateConfig, combineEditParam.templateConfig) && Intrinsics.areEqual(this.templateData, combineEditParam.templateData) && Intrinsics.areEqual(this.templateSource, combineEditParam.templateSource);
    }

    public final WeakReference getEditFragmentWeak() {
        return this.editFragmentWeak;
    }

    public final Point getScreenSize() {
        return this.screenSize;
    }

    public final CommonConfig getTemplateConfig() {
        return this.templateConfig;
    }

    public final EditFragment.TemplateData getTemplateData() {
        return this.templateData;
    }

    public final Long getTemplateSource() {
        return this.templateSource;
    }

    public int hashCode() {
        int iHashCode = ((((this.editFragmentWeak.hashCode() * 31) + this.touchManager.hashCode()) * 31) + this.screenSize.hashCode()) * 31;
        CommonConfig commonConfig = this.templateConfig;
        int iHashCode2 = (iHashCode + (commonConfig == null ? 0 : commonConfig.hashCode())) * 31;
        EditFragment.TemplateData templateData = this.templateData;
        int iHashCode3 = (iHashCode2 + (templateData == null ? 0 : templateData.hashCode())) * 31;
        Long l = this.templateSource;
        return iHashCode3 + (l != null ? l.hashCode() : 0);
    }

    public final void setScreenSize(Point point) {
        Intrinsics.checkNotNullParameter(point, "<set-?>");
        this.screenSize = point;
    }

    public final void setTemplateConfig(CommonConfig commonConfig) {
        this.templateConfig = commonConfig;
    }

    public final void setTemplateData(EditFragment.TemplateData templateData) {
        this.templateData = templateData;
    }

    public final void setTemplateSource(Long l) {
        this.templateSource = l;
    }

    public String toString() {
        return "CombineEditParam(editFragmentWeak=" + this.editFragmentWeak + ", touchManager=" + this.touchManager + ", screenSize=" + this.screenSize + ", templateConfig=" + this.templateConfig + ", templateData=" + this.templateData + ", templateSource=" + this.templateSource + ")";
    }
}
