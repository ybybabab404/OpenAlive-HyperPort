package com.miui.keyguard.editor.edit.magazineclock;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
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
import com.miui.keyguard.editor.edit.style.MagazineBStyleEditor;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.AutoBottomSheet;
import com.miui.keyguard.editor.view.KgFrame;
import com.miui.keyguard.editor.view.KgFrameParam;
import com.miui.lockscreeninfo.LockScreenInfoLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
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
/* JADX INFO: compiled from: MagazineBTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class MagazineBTemplateView extends MagazineSignatureTemplateView implements View.OnClickListener {
    public TemplateConfig currentConfig;
    public RelativeLayout mClockContainer;
    public KgFrame mSignatureAreaFrame;
    public View mSignatureFrame;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagazineBTemplateView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final int signatureAreaFrameGravity(int i) {
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

    private final void updateSignatureAreaFrameGravity() {
        KgFrame signatureAreaFrame = getSignatureAreaFrame();
        if (signatureAreaFrame != null) {
            ViewUtil.INSTANCE.setGravity(signatureAreaFrame, signatureAreaFrameGravity(getCurrentSignatureInfo().getAlignment()));
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int backContentLayerId() {
        return R.layout.kg_layout_magazine_b_clock_back_content_layer;
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
        return R.layout.kg_layout_magazine_b_clock_fore_content_layer;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public String getClockStyleType() {
        return "magazine_b";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        View childAt;
        View childAt2;
        View childAt3;
        View childAt4;
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        View clockContainer = getClockContainer();
        int left = clockContainer != null ? clockContainer.getLeft() - getResources().getDimensionPixelSize(R.dimen.kg_magazine_signature_font_line_margin_left) : 0;
        View clockContainer2 = getClockContainer();
        int top = clockContainer2 != null ? clockContainer2.getTop() : 0;
        View clockContainer3 = getClockContainer();
        int right = clockContainer3 != null ? clockContainer3.getRight() + getResources().getDimensionPixelSize(R.dimen.kg_magazine_signature_font_line_margin_left) : 0;
        View clockContainer4 = getClockContainer();
        Rect rect = new Rect(left, top, right, clockContainer4 != null ? clockContainer4.getHeight() : 0);
        LockScreenInfoLayout signatureView = getSignatureView();
        int left2 = (signatureView == null || (childAt4 = signatureView.getChildAt(0)) == null) ? 0 : childAt4.getLeft() - getResources().getDimensionPixelSize(R.dimen.kg_magazine_signature_font_line_margin_left);
        LockScreenInfoLayout signatureView2 = getSignatureView();
        int top2 = (signatureView2 == null || (childAt3 = signatureView2.getChildAt(0)) == null) ? 0 : childAt3.getTop();
        LockScreenInfoLayout signatureView3 = getSignatureView();
        int right2 = (signatureView3 == null || (childAt2 = signatureView3.getChildAt(0)) == null) ? 0 : childAt2.getRight() + getResources().getDimensionPixelSize(R.dimen.kg_magazine_signature_font_line_margin_left);
        LockScreenInfoLayout signatureView4 = getSignatureView();
        Rect rect2 = new Rect(left2, top2, right2, ((signatureView4 == null || (childAt = signatureView4.getChildAt(0)) == null) ? 0 : childAt.getHeight()) + getResources().getDimensionPixelSize(R.dimen.kg_magazine_signature_font_line_margin_bottom));
        int i = R.id.kg_signature_area_frame;
        CustomMarginType customMarginType = CustomMarginType.ALL;
        return CollectionsKt.listOf((Object[]) new KgFrameParam[]{getFixationFrameParam(i, rect2, customMarginType, new View[0], R.string.kg_classic_signature_label), BaseClockView.getFixationFrameParam$default(this, R.id.kg_time_area_frame, rect, customMarginType, new View[0], 0, 16, null)});
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void getFramesComplete() {
        super.getFramesComplete();
        this.mSignatureAreaFrame = (KgFrame) findViewById(R.id.kg_signature_area_frame);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public AbstractPopupEditor getOrCreateClockStyleEditor() {
        AbstractPopupEditor clockStyleEditor = getClockStyleEditor();
        if (clockStyleEditor == null) {
            clockStyleEditor = new MagazineBStyleEditor(this, this, getCurrentClockBean().getStyle(), getFontStyleEditCallback());
        }
        setClockStyleEditor(clockStyleEditor);
        AbstractPopupEditor clockStyleEditor2 = getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor2, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.style.MagazineBStyleEditor");
        MagazineBStyleEditor magazineBStyleEditor = (MagazineBStyleEditor) clockStyleEditor2;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        if (deviceUtil.isFoldLargeScreen(context)) {
            magazineBStyleEditor.setEditorViewPagerHeight(getContext().getResources().getDimensionPixelSize(R.dimen.kg_editor_dialog_signature_content_height_magazine_b), true);
        }
        AbstractPopupEditor clockStyleEditor3 = getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor3);
        return clockStyleEditor3;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @Nullable
    public SignatureEditor getOrCreateSignatureStyleEditor() {
        MagazineBTemplateView magazineBTemplateView;
        SignatureInfo signatureInfo;
        SignatureEditor signatureStyleEditor = getSignatureStyleEditor();
        if (signatureStyleEditor == null) {
            TemplateConfig templateConfig = this.currentConfig;
            if (templateConfig == null || (signatureInfo = templateConfig.getSignatureInfo()) == null) {
                magazineBTemplateView = this;
                signatureStyleEditor = null;
            } else {
                int templateFontType = signatureInfo.getTemplateFontType();
                com.miui.lockscreeninfo.model.SignatureInfo currentSignatureInfo = getCurrentSignatureInfo();
                TemplateConfig templateConfig2 = templateConfig();
                Intrinsics.checkNotNull(templateConfig2);
                magazineBTemplateView = this;
                signatureStyleEditor = new MagazineABSignatureEditor(magazineBTemplateView, currentSignatureInfo, templateFontType, templateConfig2, getSignatureView());
            }
        } else {
            magazineBTemplateView = this;
        }
        magazineBTemplateView.setSignatureStyleEditor(signatureStyleEditor);
        SignatureEditor signatureStyleEditor2 = magazineBTemplateView.getSignatureStyleEditor();
        if (signatureStyleEditor2 != null) {
            signatureStyleEditor2.setEditorViewPagerHeight(magazineBTemplateView.getContext().getResources().getDimensionPixelSize(R.dimen.kg_editor_dialog_signature_content_height_magazine_b));
        }
        return magazineBTemplateView.getSignatureStyleEditor();
    }

    @Override // com.miui.keyguard.editor.edit.magazineclock.MagazineSignatureTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void initTemplateBean(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        super.initTemplateBean(templateConfig);
        this.currentConfig = templateConfig;
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

    @Override // com.miui.keyguard.editor.edit.magazineclock.MagazineSignatureTemplateView
    public void onClickSignatureFrame(View anchor) {
        Intrinsics.checkNotNullParameter(anchor, "anchor");
        super.onClickSignatureFrame(anchor);
        signatureAnimation(true);
        AutoBottomSheet signatureSheetModal = getSignatureSheetModal();
        if (signatureSheetModal != null) {
            MagazineABSignatureEditor magazineABSignatureEditor = getMagazineABSignatureEditor();
            signatureSheetModal.setEdits(CollectionsKt.listOf(magazineABSignatureEditor != null ? magazineABSignatureEditor.getMSignatureEditeTextView() : null));
        }
    }

    @Override // com.miui.keyguard.editor.edit.magazineclock.MagazineSignatureTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        WallpaperInfo wallpaperInfo;
        super.onEdited(i, obj);
        switch (i) {
            case 60:
                if (obj instanceof String) {
                    if (!Intrinsics.areEqual(getCurrentSignatureInfo().getContent(), obj)) {
                        markEdited();
                    }
                    getCurrentSignatureInfo().setContent((String) obj);
                    onSignatureInfoUpdate();
                }
                break;
            case 61:
                if (obj instanceof Integer) {
                    getCurrentSignatureInfo().setAlignment(((Number) obj).intValue());
                    updateSignatureAreaFrameGravity();
                    onSignatureInfoUpdate();
                    TemplateConfig templateConfig = templateConfig();
                    BaseClockView.startPickColorFromWallpaper$default(this, (templateConfig == null || (wallpaperInfo = templateConfig.getWallpaperInfo()) == null) ? null : wallpaperInfo.getCurrentWallpaper(), false, true, null, 8, null);
                }
                break;
            case 62:
                if (obj instanceof Integer) {
                    getCurrentSignatureInfo().setTemplateFontType(((Number) obj).intValue());
                    onSignatureInfoUpdate();
                }
                break;
        }
    }

    @Override // com.miui.keyguard.editor.edit.magazineclock.MagazineSignatureTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        LockScreenInfoLayout signatureView = getSignatureView();
        if (signatureView != null) {
            MiuiClockView miuiClockView = getMiuiClockView();
            signatureView.setBackgroundBlurContainer(miuiClockView != null ? miuiClockView.getBackgroundBlurContainer() : null);
        }
        super.onMiuiClockViewCreated(templateConfig);
        this.mSignatureFrame = findViewById(R.id.signature_magazine_b_frame);
        this.mClockContainer = (RelativeLayout) findViewById(com.miui.clock.R.id.clock_container);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onScreenSizeChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onScreenSizeChanged(newConfig);
        RelativeLayout relativeLayout = this.mClockContainer;
        if (relativeLayout == null || relativeLayout.getVisibility() == 0) {
            getEditFrameController().canShowAll(true);
            return;
        }
        getEditFrameController().canShowAll(false);
        IStateStyle iStateStyleState = Folme.useAt(this.mSignatureFrame).state();
        ViewProperty viewProperty = ViewProperty.Y;
        AnimConfig animConfig = new AnimConfig();
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.edit.magazineclock.MagazineBTemplateView$onScreenSizeChanged$1$1
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                this.this$0.generateEditFrames();
                KgFrame kgFrame = this.this$0.mSignatureAreaFrame;
                if (kgFrame != null) {
                    kgFrame.setVisibility(0);
                }
                RelativeLayout relativeLayout2 = this.this$0.mClockContainer;
                if (relativeLayout2 != null) {
                    relativeLayout2.setVisibility(0);
                }
            }
        });
        Unit unit = Unit.INSTANCE;
        iStateStyleState.to(viewProperty, 0, animConfig);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onSignatureBottomSheetDismiss() {
        signatureAnimation(false);
        super.onSignatureBottomSheetDismiss();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void popFontFilterDialog(int i) {
        super.popFontFilterDialog(i);
        if (i == 0) {
            BaseClockView.popClockFontFilterDialog$default(this, R.id.kg_time_area_frame, 0, 2, null);
        }
    }

    public final void signatureAnimation(boolean z) {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (!deviceUtil.isPhone()) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (!deviceUtil.isSmallDisplay(context)) {
                return;
            }
        }
        RelativeLayout relativeLayout = this.mClockContainer;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(!z ? 0 : 8);
        }
        Folme.useAt(this.mSignatureFrame).state().to(ViewProperty.Y, Integer.valueOf(z ? -getContext().getResources().getDimensionPixelOffset(R.dimen.kg_magazine_b_clock_signature_area_frame_trans) : 0), new AnimConfig());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect templateHierarchyAvoidRect() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_magazine_b_hierarchy_avoid_rect_left);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_magazine_b_hierarchy_avoid_rect_top);
        return new Rect(dimensionPixelSize, dimensionPixelSize2, getResources().getDimensionPixelSize(R.dimen.kg_magazine_b_hierarchy_avoid_rect_width) + dimensionPixelSize, getResources().getDimensionPixelSize(R.dimen.kg_magazine_b_hierarchy_avoid_rect_height) + dimensionPixelSize2);
    }
}
