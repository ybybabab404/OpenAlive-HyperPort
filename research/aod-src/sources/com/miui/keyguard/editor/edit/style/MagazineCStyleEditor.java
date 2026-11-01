package com.miui.keyguard.editor.edit.style;

import android.content.Context;
import android.view.View;
import com.miui.clock.magazine.MagazineCClockPreviewView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.magazineclock.MagazineCTemplateView;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.edit.view.StyleSelectorAdapter;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.FilterIconLayout;
import kotlin.Metadata;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: MagazineCStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class MagazineCStyleEditor extends ViewStyleEditor {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagazineCStyleEditor(MagazineCTemplateView templateView, EditCallback editCallback, int i, FontStyleEditCallback fontStyleEditCallback) {
        super(i, templateView, editCallback, false, fontStyleEditCallback, 8, null);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        EditorDialogTitleView container = getContainer();
        String string = getContext().getString(R.string.kg_title_clock_name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        EditorDialogTitleView.setTitle$default(container, string, getContext().getString(R.string.kg_title_font_name), 0, 4, null);
        setSelectorViewVisible(8);
    }

    @Override // com.miui.keyguard.editor.edit.style.ViewStyleEditor
    public StyleSelectorAdapter createDrawableAdapter(int i) {
        super.createDrawableAdapter(i);
        StyleSelectorAdapter<Integer> styleSelectorAdapter = new StyleSelectorAdapter<Integer>(MagazineCStyleEditorKt.MAGAZINE_C_STYLE_LIST) { // from class: com.miui.keyguard.editor.edit.style.MagazineCStyleEditor.createDrawableAdapter.1
            @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public int itemLayoutId() {
                return R.layout.kg_layout_drawable_selector_item_magazine_c;
            }

            @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onBind(View view, int i2) {
                MagazineCClockPreviewView magazineCClockPreviewView;
                super.onBind(view, i2);
                if (view == null || (magazineCClockPreviewView = (MagazineCClockPreviewView) view.findViewById(R.id.magazine_c_style_preview_view)) == null) {
                    return;
                }
                MagazineCStyleEditor magazineCStyleEditor = MagazineCStyleEditor.this;
                magazineCClockPreviewView.setStyle(((Number) getData().get(i2)).intValue());
                magazineCClockPreviewView.setCalendar(magazineCStyleEditor.getMiuiXCalendar());
                magazineCClockPreviewView.updateTimeView();
                ViewUtil viewUtil = ViewUtil.INSTANCE;
                Context context = magazineCClockPreviewView.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                magazineCClockPreviewView.setClockDarkMode(viewUtil.getDarkMode(context));
                magazineCClockPreviewView.setContentDescription(magazineCClockPreviewView.getContext().getString(((Number) MagazineCStyleEditorKt.MAGAZINE_C_STYLE_NAMES.get(i2)).intValue()));
                FilterIconLayout filterIconLayout = view instanceof FilterIconLayout ? (FilterIconLayout) view : null;
                if (filterIconLayout != null) {
                    filterIconLayout.setRadius(view.getContext().getResources().getDimensionPixelSize(R.dimen.kg_magazine_c_clock_style_frame_radius));
                }
            }

            @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onItemSelect(View view, int i2) {
                super.onItemSelect(view, i2);
                MagazineCStyleEditor.this.getEditCallback().onEdited(131, MagazineCStyleEditorKt.MAGAZINE_C_STYLE_LIST.get(i2));
            }
        };
        styleSelectorAdapter.setSelectedIndex(MagazineCStyleEditorKt.MAGAZINE_C_STYLE_LIST.indexOf(Integer.valueOf(i)));
        return styleSelectorAdapter;
    }
}
