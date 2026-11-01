package com.miui.keyguard.editor.edit.base;

import android.os.Trace;
import android.view.View;
import android.view.ViewGroup;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.KgVisualCheckBox;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TemplateButtonBar.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TemplateButtonBar extends FunctionButtonBar {
    public final int editMode;
    public final BaseTemplateView templateView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateButtonBar(ViewGroup parent, BaseTemplateView templateView, int i) {
        super(parent, templateView);
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.templateView = templateView;
        this.editMode = i;
    }

    public /* synthetic */ TemplateButtonBar(ViewGroup viewGroup, BaseTemplateView baseTemplateView, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(viewGroup, baseTemplateView, (i2 & 4) != 0 ? 2 : i);
    }

    public static /* synthetic */ KgVisualCheckBox addAiGenerateButton$default(TemplateButtonBar templateButtonBar, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -1;
        }
        return templateButtonBar.addAiGenerateButton(i);
    }

    public static /* synthetic */ KgVisualCheckBox addBorderPreviewButtonWithBlurBackground$default(TemplateButtonBar templateButtonBar, int i, int i2, int i3, int i4, int i5, Integer num, int i6, Object obj) {
        if ((i6 & 16) != 0) {
            i5 = -1;
        }
        int i7 = i5;
        if ((i6 & 32) != 0) {
            num = null;
        }
        return templateButtonBar.addBorderPreviewButtonWithBlurBackground(i, i2, i3, i4, i7, num);
    }

    public static /* synthetic */ KgVisualCheckBox addFilterButton$default(TemplateButtonBar templateButtonBar, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -1;
        }
        return templateButtonBar.addFilterButton(i);
    }

    public static /* synthetic */ KgVisualCheckBox addFollowButton$default(TemplateButtonBar templateButtonBar, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -1;
        }
        return templateButtonBar.addFollowButton(i);
    }

    public static /* synthetic */ KgVisualCheckBox addHierarchyButton$default(TemplateButtonBar templateButtonBar, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -1;
        }
        return templateButtonBar.addHierarchyButton(i);
    }

    public static /* synthetic */ KgVisualCheckBox addHomeBlur$default(TemplateButtonBar templateButtonBar, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -1;
        }
        return templateButtonBar.addHomeBlur(i);
    }

    public static /* synthetic */ KgVisualCheckBox addHomeWallpaperPickerButton$default(TemplateButtonBar templateButtonBar, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = -1;
        }
        if ((i3 & 2) != 0) {
            i2 = R.string.kg_function_button_text_wallpaper;
        }
        return templateButtonBar.addHomeWallpaperPickerButton(i, i2);
    }

    public static /* synthetic */ KgVisualCheckBox addWallpaperPickerButton$default(TemplateButtonBar templateButtonBar, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = -1;
        }
        if ((i3 & 2) != 0) {
            i2 = R.string.kg_function_button_text_wallpaper;
        }
        return templateButtonBar.addWallpaperPickerButton(i, i2);
    }

    public final KgVisualCheckBox addAiGenerateButton(int i) {
        return addNormalButtonWithBlurBackground(R.id.kg_function_button_ai_generate, R.drawable.kg_function_button_icon_generate, R.string.kg_title_ai_generate, i);
    }

    public final KgVisualCheckBox addBorderButtonWithBlurBackground(int i, int i2, int i3, int i4) {
        KgVisualCheckBox kgVisualCheckBoxAddBorderButton = addBorderButton(i, i2, i3, i4);
        decorateFrostedPuredBackground(kgVisualCheckBoxAddBorderButton);
        return kgVisualCheckBoxAddBorderButton;
    }

    public final KgVisualCheckBox addBorderPreviewButtonWithBlurBackground(int i, int i2, int i3, int i4, int i5, Integer num) {
        KgVisualCheckBox kgVisualCheckBoxAddBorderPreviewButton = addBorderPreviewButton(i, i2, i3, i4, i5, num);
        decorateFrostedPuredBackground(kgVisualCheckBoxAddBorderPreviewButton);
        return kgVisualCheckBoxAddBorderPreviewButton;
    }

    public final KgVisualCheckBox addFillButtonWithBlurBackground(int i, int i2, int i3, int i4, int i5) {
        KgVisualCheckBox kgVisualCheckBoxAddFillButton$default = FunctionButtonBar.addFillButton$default(this, i, i2, i3, i5, i4, null, 32, null);
        decorateFrostedPuredBackground(kgVisualCheckBoxAddFillButton$default);
        return kgVisualCheckBoxAddFillButton$default;
    }

    public final KgVisualCheckBox addFilterButton(int i) {
        return addBorderButtonWithBlurBackground(R.id.kg_function_button_filter, R.drawable.kg_function_button_icon_filter, R.string.kg_title_effect_name, i);
    }

    public final KgVisualCheckBox addFollowButton(int i) {
        return addBorderPreviewButtonWithBlurBackground(R.id.kg_function_button_follow, R.drawable.kg_function_button_icon_wallpaper, R.string.kg_function_button_text_follow, R.drawable.kg_home_reset_follow, i, Integer.valueOf(R.string.kg_talkback_follow_the_keyguard));
    }

    public final KgVisualCheckBox addHierarchyButton(int i) {
        return addFillButtonWithBlurBackground(R.id.kg_function_button_hierarchy, R.drawable.kg_function_button_icon_hierarchy_selector, R.string.kg_function_button_text_hierarchy, getRoot().getContext().getColor(R.color.kg_blue), i);
    }

    public final KgVisualCheckBox addHomeBlur(int i) {
        return addFillButtonWithBlurBackground(R.id.kg_function_button_home_blur, R.drawable.kg_function_button_icon_blur, R.string.kg_wallpaper_filter_type_obscure, getRoot().getContext().getColor(R.color.kg_blue), i);
    }

    public final KgVisualCheckBox addHomeWallpaperPickerButton(int i, int i2) {
        return addBorderPreviewButtonWithBlurBackground$default(this, R.id.kg_function_home_button_wallpaper, R.drawable.kg_function_button_icon_wallpaper, i2, R.drawable.kg_home_new_wallpaper, i, null, 32, null);
    }

    public final KgVisualCheckBox addNormalButtonWithBlurBackground(int i, int i2, int i3, int i4) {
        KgVisualCheckBox kgVisualCheckBoxAddNormalButton$default = FunctionButtonBar.addNormalButton$default(this, i, i2, i3, i4, 0, 16, null);
        decorateFrostedPuredBackground(kgVisualCheckBoxAddNormalButton$default);
        return kgVisualCheckBoxAddNormalButton$default;
    }

    public final KgVisualCheckBox addWallpaperPickerButton(int i, int i2) {
        return addNormalButtonWithBlurBackground(R.id.kg_function_button_wallpaper, R.drawable.kg_function_button_icon_wallpaper, i2, i);
    }

    public final void decorateFrostedPuredBackground(KgVisualCheckBox kgVisualCheckBox) {
        EditorApplicationProxy.Companion companion = EditorApplicationProxy.Companion;
        if (companion.getEDITOR_DEBUG()) {
            Trace.beginSection("decorateFrostedPuredBackground");
        }
        View iconBox = kgVisualCheckBox.getIconBox();
        if (iconBox != null) {
            ViewUtil viewUtil = ViewUtil.INSTANCE;
            ViewUtil.decorateFrostedPured$default(viewUtil, iconBox, kgVisualCheckBox.getResources().getDimension(R.dimen.kg_function_button_icon_radius), false, viewUtil.getColor(this.templateView, R.color.kg_white_14), 2, null);
        }
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
        }
    }

    public final void disableAllExceptHierarchy() {
        int childCount = getButtonGroup().getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getButtonGroup().getChildAt(i);
            if (childAt instanceof KgVisualCheckBox) {
                KgVisualCheckBox kgVisualCheckBox = (KgVisualCheckBox) childAt;
                if (kgVisualCheckBox.getId() != R.id.kg_function_button_hierarchy && kgVisualCheckBox.getId() != R.id.kg_function_button_ai_generate) {
                    kgVisualCheckBox.disable();
                }
            }
        }
    }

    public final void enableAllExceptHierarchy() {
        int childCount = getButtonGroup().getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getButtonGroup().getChildAt(i);
            if (childAt instanceof KgVisualCheckBox) {
                KgVisualCheckBox kgVisualCheckBox = (KgVisualCheckBox) childAt;
                if (kgVisualCheckBox.getId() != R.id.kg_function_button_hierarchy && kgVisualCheckBox.getId() != R.id.kg_function_button_ai_generate) {
                    if (kgVisualCheckBox.getId() == R.id.kg_function_button_font) {
                        if (this.templateView.isDualClock()) {
                            kgVisualCheckBox.disable();
                        } else {
                            kgVisualCheckBox.enable();
                        }
                    } else if (kgVisualCheckBox.getId() == R.id.kg_function_button_filter) {
                        IWallpaperLayer wallpaperLayer = this.templateView.getWallpaperLayer();
                        if (wallpaperLayer != null ? Intrinsics.areEqual(wallpaperLayer.getScaleable(), Boolean.FALSE) : false) {
                            kgVisualCheckBox.disable();
                        } else {
                            kgVisualCheckBox.enable();
                        }
                    } else {
                        kgVisualCheckBox.enable();
                    }
                }
            }
        }
    }

    public final KgVisualCheckBox getAiGenerateButton() {
        View viewFindViewById = getRoot().findViewById(R.id.kg_function_button_ai_generate);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        return (KgVisualCheckBox) viewFindViewById;
    }

    public final KgVisualCheckBox getFilterButton() {
        return (KgVisualCheckBox) getRoot().findViewById(R.id.kg_function_button_filter);
    }

    public final BaseTemplateView getTemplateView() {
        return this.templateView;
    }

    public final KgVisualCheckBox getWallpaperPickerButton() {
        return (KgVisualCheckBox) getRoot().findViewById(R.id.kg_function_button_wallpaper);
    }
}
