package com.miui.keyguard.editor.edit.magazineclock;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.clock.MiuiClockView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.SignatureInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.CustomMarginType;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.color.picker.MagazineABAutoColorPicker;
import com.miui.keyguard.editor.edit.signature.MagazineABSignatureEditor;
import com.miui.keyguard.editor.edit.signature.SignatureEditor;
import com.miui.keyguard.editor.edit.style.MagazineAStyleEditor;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.KgFrame;
import com.miui.keyguard.editor.view.KgFrameParam;
import com.miui.lockscreeninfo.LockScreenInfoLayout;
import java.util.List;
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
/* JADX INFO: compiled from: MagazineATemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MagazineATemplateView extends MagazineSignatureTemplateView implements View.OnClickListener {
    public TemplateConfig currentConfig;
    public float originalLetterSpace;
    public KgFrame timeAreaFrame;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagazineATemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.originalLetterSpace = -1.0f;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int backContentLayerId() {
        return R.layout.kg_layout_magazine_a_clock_back_content_layer;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public AutoColorPicker createAutoColorPicker() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return new MagazineABAutoColorPicker(context, getClockStyleType(), getCurrentClockBean(), getCurrentSignatureInfo());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public View.OnClickListener editFrameClickListener() {
        return this;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int foreContentLayerId() {
        return R.layout.kg_layout_magazine_a_clock_fore_content_layer;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public String getClockStyleType() {
        return "magazine_a";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        LockScreenInfoLayout signatureView = getSignatureView();
        View signatureContainer = signatureView != null ? signatureView.getSignatureContainer() : null;
        Resources resources = getResources();
        int i = R.dimen.kg_magazine_signature_font_line_margin_left;
        int dimensionPixelSize = resources.getDimensionPixelSize(i);
        int top = signatureContainer != null ? signatureContainer.getTop() - getResources().getDimensionPixelSize(R.dimen.kg_magazine_a_signature_padding_top) : 0;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        Rect rect = new Rect(dimensionPixelSize, top, deviceUtil.currentDisplaySize(context).width() - getResources().getDimensionPixelSize(i), signatureContainer != null ? signatureContainer.getHeight() + getResources().getDimensionPixelSize(R.dimen.kg_magazine_a_signature_padding_bottom) : 0);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(i);
        View clockContainer = getClockContainer();
        int top2 = clockContainer != null ? clockContainer.getTop() : 0;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        int iWidth = deviceUtil.currentDisplaySize(context2).width() - getResources().getDimensionPixelSize(i);
        View clockContainer2 = getClockContainer();
        Rect rect2 = new Rect(dimensionPixelSize2, top2, iWidth, clockContainer2 != null ? clockContainer2.getHeight() : 0);
        int i2 = R.id.kg_signature_area_frame;
        CustomMarginType customMarginType = CustomMarginType.ALL;
        LockScreenInfoLayout signatureView2 = getSignatureView();
        return CollectionsKt.listOf((Object[]) new KgFrameParam[]{getFixationFrameParam(i2, rect, customMarginType, new View[]{signatureView2 != null ? signatureView2.getSignatureView() : null}, R.string.kg_classic_signature_label), BaseClockView.getFixationFrameParam$default(this, R.id.kg_time_area_frame, rect2, customMarginType, new View[0], 0, 16, null)});
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public AbstractPopupEditor getOrCreateClockStyleEditor() {
        AbstractPopupEditor clockStyleEditor = getClockStyleEditor();
        if (clockStyleEditor == null) {
            clockStyleEditor = new MagazineAStyleEditor(this, this, getCurrentClockBean().getStyle(), getFontStyleEditCallback());
        }
        setClockStyleEditor(clockStyleEditor);
        AbstractPopupEditor clockStyleEditor2 = getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor2);
        return clockStyleEditor2;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @Nullable
    public SignatureEditor getOrCreateSignatureStyleEditor() {
        MagazineATemplateView magazineATemplateView;
        SignatureInfo signatureInfo;
        SignatureEditor signatureStyleEditor = getSignatureStyleEditor();
        if (signatureStyleEditor == null) {
            TemplateConfig templateConfig = this.currentConfig;
            if (templateConfig == null || (signatureInfo = templateConfig.getSignatureInfo()) == null) {
                magazineATemplateView = this;
                signatureStyleEditor = null;
            } else {
                int templateFontType = signatureInfo.getTemplateFontType();
                com.miui.lockscreeninfo.model.SignatureInfo currentSignatureInfo = getCurrentSignatureInfo();
                TemplateConfig templateConfig2 = templateConfig();
                Intrinsics.checkNotNull(templateConfig2);
                magazineATemplateView = this;
                signatureStyleEditor = new MagazineABSignatureEditor(magazineATemplateView, currentSignatureInfo, templateFontType, templateConfig2, getSignatureView());
            }
        } else {
            magazineATemplateView = this;
        }
        magazineATemplateView.setSignatureStyleEditor(signatureStyleEditor);
        SignatureEditor signatureStyleEditor2 = magazineATemplateView.getSignatureStyleEditor();
        if (signatureStyleEditor2 != null) {
            signatureStyleEditor2.setEditorViewPagerHeight(magazineATemplateView.getContext().getResources().getDimensionPixelSize(R.dimen.kg_editor_dialog_signature_content_height_magazine_a));
        }
        return magazineATemplateView.getSignatureStyleEditor();
    }

    @Override // com.miui.keyguard.editor.edit.magazineclock.MagazineSignatureTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void initTemplateBean(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initTemplateBean(templateConfig);
        this.currentConfig = templateConfig;
        getCurrentClockBean().setStyle(templateConfig.getClockInfo().getStyle());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        int id = v.getId();
        if (id == R.id.kg_signature_area_frame) {
            onClickSignatureFrame(v);
        } else if (id == R.id.kg_time_area_frame) {
            onClickClockFrame(v);
        }
    }

    @Override // com.miui.keyguard.editor.edit.magazineclock.MagazineSignatureTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        float originalLetterSpace;
        com.miui.lockscreeninfo.model.SignatureInfo currentSignatureInfo;
        WallpaperInfo wallpaperInfo;
        WallpaperInfo wallpaperInfo2;
        super.onEdited(i, obj);
        Bitmap currentWallpaper = null;
        if (i != 61) {
            if (i == 62) {
                if (obj instanceof Integer) {
                    getCurrentSignatureInfo().setTemplateFontType(((Number) obj).intValue());
                    onSignatureInfoUpdate();
                    return;
                }
                return;
            }
            if (i == 130 && (obj instanceof Integer)) {
                getCurrentClockBean().setStyle(((Number) obj).intValue());
                updateTimeAreaFrameGravity();
                onClockInfoUpdate();
                TemplateConfig templateConfig = templateConfig();
                if (templateConfig != null && (wallpaperInfo2 = templateConfig.getWallpaperInfo()) != null) {
                    currentWallpaper = wallpaperInfo2.getCurrentWallpaper();
                }
                BaseClockView.startPickColorFromWallpaper$default(this, currentWallpaper, false, true, null, 8, null);
                return;
            }
            return;
        }
        if (obj instanceof Integer) {
            com.miui.lockscreeninfo.model.SignatureInfo currentSignatureInfo2 = getCurrentSignatureInfo();
            if (currentSignatureInfo2 != null) {
                currentSignatureInfo2.setAlignment(((Number) obj).intValue());
            }
            updateSignatureAreaFrameGravity();
            if (Intrinsics.areEqual(obj, Integer.valueOf(com.miui.maml.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader))) {
                MagazineABSignatureEditor magazineABSignatureEditor = getMagazineABSignatureEditor();
                originalLetterSpace = magazineABSignatureEditor != null ? magazineABSignatureEditor.getOriginalLetterSpace() : 0.0f;
            } else {
                if (this.originalLetterSpace != -1.0f && (currentSignatureInfo = getCurrentSignatureInfo()) != null) {
                    currentSignatureInfo.setLetterSpaceValue(this.originalLetterSpace);
                }
                originalLetterSpace = -1.0f;
            }
            this.originalLetterSpace = originalLetterSpace;
            onSignatureInfoUpdate();
            TemplateConfig templateConfig2 = templateConfig();
            if (templateConfig2 != null && (wallpaperInfo = templateConfig2.getWallpaperInfo()) != null) {
                currentWallpaper = wallpaperInfo.getCurrentWallpaper();
            }
            BaseClockView.startPickColorFromWallpaper$default(this, currentWallpaper, false, true, null, 8, null);
            MagazineABSignatureEditor magazineABSignatureEditor2 = getMagazineABSignatureEditor();
            if (magazineABSignatureEditor2 != null) {
                magazineABSignatureEditor2.limitWordSpacing(Intrinsics.areEqual(obj, Integer.valueOf(com.miui.maml.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader)));
            }
        }
    }

    @Override // com.miui.keyguard.editor.edit.magazineclock.MagazineSignatureTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            LockScreenInfoLayout signatureView = getSignatureView();
            miuiClockView.setBackgroundBlurContainer(signatureView != null ? signatureView.getBackgroundBlurContainer() : null);
        }
        super.onMiuiClockViewCreated(templateConfig);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void popFontFilterDialog(int i) {
        super.popFontFilterDialog(i);
        if (i == 0) {
            BaseClockView.popClockFontFilterDialog$default(this, R.id.kg_time_area_frame, 0, 2, null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.magazineclock.MagazineSignatureTemplateView
    public void setClockTranslateInSecondaryMode() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_clock_view_secondary_mode_translate);
        LockScreenInfoLayout signatureView = getSignatureView();
        if (signatureView != null) {
            ViewUtil.setMarginTop(signatureView, dimensionPixelSize);
        }
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            ViewUtil.setMarginBottom(miuiClockView, -dimensionPixelSize);
        }
    }

    public final int signatureAreaFrameGravity(int i) {
        if (i == 101) {
            return 8388611;
        }
        if (i == 106) {
            return 1;
        }
        if (i != 103) {
            return i != 104 ? 8388613 : 1;
        }
        return 8388611;
    }

    public final int timeAreaFrameGravity(int i) {
        int i2 = 1;
        if (i == 1) {
            i2 = 8388611;
        } else if (i != 2) {
            i2 = 8388613;
        }
        return i2 | 80;
    }

    public final void updateSignatureAreaFrameGravity() {
        KgFrame signatureAreaFrame = getSignatureAreaFrame();
        if (signatureAreaFrame != null) {
            ViewUtil.INSTANCE.setGravity(signatureAreaFrame, signatureAreaFrameGravity(getCurrentSignatureInfo().getAlignment()));
        }
    }

    public final void updateTimeAreaFrameGravity() {
        KgFrame kgFrame = this.timeAreaFrame;
        if (kgFrame != null) {
            ViewUtil.INSTANCE.setGravity(kgFrame, timeAreaFrameGravity(getCurrentClockBean().getStyle()));
        }
    }
}
