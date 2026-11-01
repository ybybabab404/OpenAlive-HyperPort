package com.miui.keyguard.editor.edit.frameclock;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.clock.MiuiClockView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.SmartFrameInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.handler.ColorPickHandler;
import com.miui.keyguard.editor.edit.color.handler.SmartFrameColorPickHandler;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.color.picker.SmartFrameAutoColorPicker;
import com.miui.keyguardtemplate.smartframe.SmartFrameView;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
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
/* JADX INFO: compiled from: FrameTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FrameTemplateView extends BaseClockView implements View.OnClickListener {
    public SmartFrameView smartFrameView;
    public View styleAnchor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FrameTemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int backContentLayerId() {
        return R.layout.kg_layout_smart_frame_clock_back_content_layer;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public AutoColorPicker createAutoColorPicker() {
        TemplateConfig templateConfig = templateConfig();
        if (templateConfig == null) {
            return null;
        }
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return new SmartFrameAutoColorPicker(context, getClockStyleType(), templateConfig);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public ColorPickHandler createColorPickHandler() {
        SmartFrameInfo smartFrame;
        TemplateConfig templateConfig = templateConfig();
        if (templateConfig == null || (smartFrame = templateConfig.getSmartFrame()) == null) {
            return null;
        }
        return new SmartFrameColorPickHandler(this, getCurrentClockBean(), smartFrame);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public View.OnClickListener editFrameClickListener() {
        return this;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public String getClockStyleType() {
        return "smart_frame";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return CollectionsKt.listOf(BaseClockView.getCurrentFrameParam$default(this, R.id.shape_frame, null, new View[]{this.styleAnchor}, 0, 8, null));
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void initFrames(Bitmap bitmap, FrameLayout templateView) {
        SmartFrameInfo smartFrame;
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        super.initFrames(bitmap, templateView);
        TemplateConfig templateConfig = templateConfig();
        if (templateConfig == null || (smartFrame = templateConfig.getSmartFrame()) == null) {
            return;
        }
        onBackgroundColorChanged(smartFrame.getSolidColor());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void initTemplateBean(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initTemplateBean(templateConfig);
        getCurrentClockBean().setStyle(templateConfig.getClockInfo().getStyle());
        getCurrentClockBean().setPrimaryColor(templateConfig.getClockInfo().getPrimaryColor());
        getCurrentClockBean().setAutoPrimaryColor(templateConfig.getClockInfo().isAutoPrimaryColor());
        getCurrentClockBean().setSecondaryColor(templateConfig.getClockInfo().getSecondaryColor());
        getCurrentClockBean().setAutoSecondaryColor(templateConfig.getClockInfo().isAutoSecondaryColor());
        getCurrentClockBean().setBlendColor(templateConfig.getClockInfo().getBlendColor());
        getCurrentClockBean().setSecondaryBlendColor(templateConfig.getClockInfo().getSecondaryBlendColor());
        getCurrentClockBean().setClockEffect(templateConfig.getClockInfo().getClockEffect());
    }

    public final void onBackgroundColorChanged(int i) {
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        v.getId();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        SmartFrameInfo smartFrame;
        super.onEdited(i, obj);
        if (i == 150 && (obj instanceof Integer)) {
            Number number = (Number) obj;
            getCurrentClockBean().setStyle(number.intValue());
            TemplateConfig templateConfig = templateConfig();
            if (templateConfig != null && (smartFrame = templateConfig.getSmartFrame()) != null) {
                smartFrame.setShape(number.intValue());
            }
            SmartFrameView smartFrameView = this.smartFrameView;
            if (smartFrameView != null) {
                smartFrameView.setShape(number.intValue());
            }
        }
    }

    public final void onFrameInfoUpdate() {
        SmartFrameInfo smartFrame;
        TemplateConfig templateConfig = templateConfig();
        if (templateConfig == null || (smartFrame = templateConfig.getSmartFrame()) == null) {
            return;
        }
        SmartFrameView smartFrameView = this.smartFrameView;
        if (smartFrameView != null) {
            smartFrameView.setShape(smartFrame.getShape());
        }
        SmartFrameView smartFrameView2 = this.smartFrameView;
        if (smartFrameView2 != null) {
            smartFrameView2.setFillColor(smartFrame.getSolidColor());
        }
        onBackgroundColorChanged(smartFrame.getSolidColor());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        this.smartFrameView = (SmartFrameView) findViewById(R.id.smart_frame);
        this.styleAnchor = findViewById(R.id.style_anchor);
        super.onMiuiClockViewCreated(templateConfig);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public boolean supportHierarchy() {
        return false;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void updateClockColor(ColorData colorData) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            miuiClockView.setClockPalette(colorData.getRandomColorType(), false, colorData.getClockPalette());
        }
        setSuccessUpdateClockView(getMiuiClockView() != null);
    }
}
