package com.miui.keyguard.editor.edit.color.handler;

import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.DoodleInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.color.ColorSelectBean;
import com.miui.keyguard.editor.edit.doodleclock.DoodleTemplateView;
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
/* JADX INFO: compiled from: DoodleColorPickHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DoodleColorPickHandler extends ColorPickHandler {
    public final DoodleInfo doodleInfo;
    public final DoodleTemplateView doodleTemplateView;
    public boolean hierarchyChecked;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoodleColorPickHandler(DoodleTemplateView doodleTemplateView, ClockBean clockBean, DoodleInfo doodleInfo, boolean z) {
        super(doodleTemplateView, clockBean, null, 4, null);
        Intrinsics.checkNotNullParameter(doodleTemplateView, "doodleTemplateView");
        Intrinsics.checkNotNullParameter(clockBean, "clockBean");
        Intrinsics.checkNotNullParameter(doodleInfo, "doodleInfo");
        this.doodleTemplateView = doodleTemplateView;
        this.doodleInfo = doodleInfo;
        this.hierarchyChecked = z;
    }

    @Override // com.miui.keyguard.editor.edit.color.handler.ColorPickHandler
    public void onColorSelected(ColorSelectBean data) {
        Intrinsics.checkNotNullParameter(data, "data");
        updateColorId(data);
        if (data.isPrimaryColor()) {
            getClockBean().setPrimaryColor(data.getSelectedColor());
            getClockBean().setAutoPrimaryColor(data.isAutoPickColor());
            this.doodleTemplateView.onClockInfoUpdate();
        } else {
            this.doodleInfo.setSolidColor(data.getSelectedColor());
            this.doodleInfo.setAutoSolidColor(data.isAutoPickColor());
            this.doodleTemplateView.onDoodleInfoUpdate();
        }
    }

    @Override // com.miui.keyguard.editor.edit.color.handler.ColorPickHandler, com.miui.keyguard.editor.utils.OnHierarchyEnableChangeListener
    public void onHierarchyEnableChange(boolean z) {
        this.hierarchyChecked = z;
    }

    @Override // com.miui.keyguard.editor.edit.color.handler.ColorPickHandler
    public void updateColorId(ColorSelectBean data) {
        DoodleInfo doodle;
        ClockInfo clockInfo;
        Intrinsics.checkNotNullParameter(data, "data");
        if (data.isPrimaryColor()) {
            TemplateConfig templateConfig = getTemplateView().templateConfig();
            if (templateConfig == null || (clockInfo = templateConfig.getClockInfo()) == null) {
                return;
            }
            clockInfo.setPrimaryFontColorPanelId(data.getColorId());
            return;
        }
        TemplateConfig templateConfig2 = getTemplateView().templateConfig();
        if (templateConfig2 == null || (doodle = templateConfig2.getDoodle()) == null) {
            return;
        }
        doodle.setPrimaryFontColorPanelId(data.getColorId());
    }
}
