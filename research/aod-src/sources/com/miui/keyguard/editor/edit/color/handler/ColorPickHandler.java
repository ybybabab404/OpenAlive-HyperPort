package com.miui.keyguard.editor.edit.color.handler;

import android.content.Context;
import android.util.Log;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.Effect;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.color.ColorBeanKt;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.ColorSelectBean;
import com.miui.keyguard.editor.utils.OnHierarchyEnableChangeListener;
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
/* JADX INFO: compiled from: ColorPickHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public class ColorPickHandler implements EditCallback, OnHierarchyEnableChangeListener {
    public ColorData autoPickColorData;
    public final ClockBean clockBean;
    public final Context context;
    public final ClockBean foreClockBean;
    public boolean selectDualPrimaryColor;
    public final BaseClockView templateView;

    public ColorPickHandler(BaseClockView templateView, ClockBean clockBean, ClockBean clockBean2) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(clockBean, "clockBean");
        this.templateView = templateView;
        this.clockBean = clockBean;
        this.foreClockBean = clockBean2;
        Context context = templateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.context = context;
        this.autoPickColorData = ColorBeanKt.getEMPTY_COLOR_PICK_DATA();
        this.selectDualPrimaryColor = true;
    }

    public /* synthetic */ ColorPickHandler(BaseClockView baseClockView, ClockBean clockBean, ClockBean clockBean2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(baseClockView, clockBean, (i & 4) != 0 ? null : clockBean2);
    }

    public final ColorData autoPickColorData() {
        return this.autoPickColorData;
    }

    public final ClockBean getClockBean() {
        return this.clockBean;
    }

    @Override // com.miui.keyguard.editor.edit.base.EditCallback
    public Object getData(String str, Object... objArr) {
        return EditCallback.DefaultImpls.getData(this, str, objArr);
    }

    public final ClockBean getForeClockBean() {
        return this.foreClockBean;
    }

    public final BaseClockView getTemplateView() {
        return this.templateView;
    }

    public void handlePrimaryColor(ColorSelectBean data) {
        Intrinsics.checkNotNullParameter(data, "data");
        int fontFilterId = data.getFontFilterId();
        if (fontFilterId == 0) {
            this.clockBean.setPrimaryColor(data.getSelectedColor());
            this.clockBean.setAutoPrimaryColor(data.isAutoPickColor());
            ClockBean clockBean = this.foreClockBean;
            if (clockBean != null) {
                clockBean.setPrimaryColor(data.getSelectedColor());
            }
            ClockBean clockBean2 = this.foreClockBean;
            if (clockBean2 != null) {
                clockBean2.setAutoPrimaryColor(data.isAutoPickColor());
                return;
            }
            return;
        }
        if (fontFilterId != 3) {
            this.clockBean.setPrimaryColor(data.getSelectedColor());
            this.clockBean.setBlendColor(data.getSelectedColor());
            this.clockBean.setAutoPrimaryColor(data.isAutoPickColor());
            ClockBean clockBean3 = this.foreClockBean;
            if (clockBean3 != null) {
                clockBean3.setPrimaryColor(data.getSelectedColor());
            }
            ClockBean clockBean4 = this.foreClockBean;
            if (clockBean4 != null) {
                clockBean4.setBlendColor(data.getSelectedColor());
            }
            ClockBean clockBean5 = this.foreClockBean;
            if (clockBean5 != null) {
                clockBean5.setAutoPrimaryColor(data.isAutoPickColor());
                return;
            }
            return;
        }
        Effect.Glow glowParam = Effect.GlowEffect.getGlowParam(data.getSelectedColor());
        Intrinsics.checkNotNullExpressionValue(glowParam, "getGlowParam(...)");
        this.clockBean.setPrimaryColor(glowParam.srcColor);
        this.clockBean.setBlendColor(data.getSelectedColor());
        this.clockBean.setAutoPrimaryColor(false);
        ClockBean clockBean6 = this.foreClockBean;
        if (clockBean6 != null) {
            clockBean6.setPrimaryColor(glowParam.srcColor);
        }
        ClockBean clockBean7 = this.foreClockBean;
        if (clockBean7 != null) {
            clockBean7.setBlendColor(data.getSelectedColor());
        }
        ClockBean clockBean8 = this.foreClockBean;
        if (clockBean8 != null) {
            clockBean8.setAutoPrimaryColor(false);
        }
    }

    public void handleSecondaryColor(ColorSelectBean data) {
        Intrinsics.checkNotNullParameter(data, "data");
        int fontFilterId = data.getFontFilterId();
        if (fontFilterId == 0) {
            this.clockBean.setSecondaryColor(data.getSelectedColor());
            this.clockBean.setAutoSecondaryColor(data.isAutoPickColor());
            ClockBean clockBean = this.foreClockBean;
            if (clockBean != null) {
                clockBean.setSecondaryColor(data.getSelectedColor());
            }
            ClockBean clockBean2 = this.foreClockBean;
            if (clockBean2 != null) {
                clockBean2.setAutoSecondaryColor(data.isAutoPickColor());
                return;
            }
            return;
        }
        if (fontFilterId != 3) {
            this.clockBean.setSecondaryColor(data.getSelectedColor());
            this.clockBean.setSecondaryBlendColor(data.getSelectedColor());
            this.clockBean.setAutoSecondaryColor(data.isAutoPickColor());
            ClockBean clockBean3 = this.foreClockBean;
            if (clockBean3 != null) {
                clockBean3.setSecondaryColor(data.getSelectedColor());
            }
            ClockBean clockBean4 = this.foreClockBean;
            if (clockBean4 != null) {
                clockBean4.setSecondaryBlendColor(data.getSelectedColor());
            }
            ClockBean clockBean5 = this.foreClockBean;
            if (clockBean5 != null) {
                clockBean5.setAutoSecondaryColor(data.isAutoPickColor());
                return;
            }
            return;
        }
        Effect.Glow glowParam = Effect.GlowEffect.getGlowParam(data.getSelectedColor());
        Intrinsics.checkNotNullExpressionValue(glowParam, "getGlowParam(...)");
        this.clockBean.setSecondaryColor(glowParam.srcColor);
        this.clockBean.setSecondaryBlendColor(data.getSelectedColor());
        this.clockBean.setAutoSecondaryColor(false);
        ClockBean clockBean6 = this.foreClockBean;
        if (clockBean6 != null) {
            clockBean6.setSecondaryColor(glowParam.srcColor);
        }
        ClockBean clockBean7 = this.foreClockBean;
        if (clockBean7 != null) {
            clockBean7.setSecondaryBlendColor(data.getSelectedColor());
        }
        ClockBean clockBean8 = this.foreClockBean;
        if (clockBean8 != null) {
            clockBean8.setAutoSecondaryColor(false);
        }
    }

    public final void onAutoPickColorComplete(ColorData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        Log.d("Keyguard-Editor-ColorPickHandler", "onAutoPickColorComplete: " + data);
        this.autoPickColorData = data;
    }

    public void onColorSelected(ColorSelectBean data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.clockBean.setClockEffect(data.getFontFilterId());
        ClockBean clockBean = this.foreClockBean;
        if (clockBean != null) {
            clockBean.setClockEffect(data.getFontFilterId());
        }
        updateColorId(data);
        if (data.isPrimaryColor()) {
            handlePrimaryColor(data);
        } else {
            handleSecondaryColor(data);
        }
        this.templateView.onClockInfoUpdate();
    }

    public final void onConfigurationChanged() {
    }

    public void onDifferentColorSelected(boolean z) {
    }

    @Override // com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        if (i != 50) {
            if (i == 51) {
                if (obj instanceof Boolean) {
                    onDifferentColorSelected(((Boolean) obj).booleanValue());
                    return;
                }
                return;
            }
            if (i != 70) {
                if (i != 71) {
                    if (i == 80 && (obj instanceof ColorSelectBean)) {
                        onColorSelected((ColorSelectBean) obj);
                        return;
                    }
                    return;
                }
                if (obj instanceof ColorSelectBean) {
                    Log.i("Keyguard-Editor-ColorPickHandler", "onEdited: HSV isPrimary = " + obj);
                    this.selectDualPrimaryColor = ((ColorSelectBean) obj).isPrimaryColor();
                    this.templateView.onEdited(71, obj);
                    return;
                }
                return;
            }
        }
        if (obj instanceof ColorSelectBean) {
            onColorSelected((ColorSelectBean) obj);
        }
    }

    @Override // com.miui.keyguard.editor.utils.OnHierarchyEnableChangeListener
    public void onHierarchyEnableChange(boolean z) {
    }

    public void updateColorId(ColorSelectBean data) {
        ClockInfo clockInfo;
        ClockInfo clockInfo2;
        Intrinsics.checkNotNullParameter(data, "data");
        if (data.isPrimaryColor()) {
            TemplateConfig templateConfig = this.templateView.templateConfig();
            if (templateConfig == null || (clockInfo2 = templateConfig.getClockInfo()) == null) {
                return;
            }
            clockInfo2.setPrimaryFontColorPanelId(data.getColorId());
            return;
        }
        TemplateConfig templateConfig2 = this.templateView.templateConfig();
        if (templateConfig2 == null || (clockInfo = templateConfig2.getClockInfo()) == null) {
            return;
        }
        clockInfo.setSecondaryFontColorPanelId(data.getColorId());
    }
}
