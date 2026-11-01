package com.miui.keyguard.editor.edit.style;

import android.content.Context;
import android.view.View;
import com.miui.clock.eastern.c.MiuiEasternArtCPreviewView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.edit.view.PopupLayerView;
import com.miui.keyguard.editor.edit.view.StyleSelectorAdapter;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.FilterIconLayout;
import java.util.List;
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
/* JADX INFO: compiled from: EasternArtCStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class EasternArtCStyleEditor extends ViewStyleEditor {

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.style.EasternArtCStyleEditor$createDrawableAdapter$1, reason: invalid class name */
    /* JADX INFO: compiled from: EasternArtCStyleEditor.kt */
    @Metadata
    @SourceDebugExtension
    public static final class AnonymousClass1 extends StyleSelectorAdapter<Integer> {
        public AnonymousClass1(List list) {
            super(list);
        }

        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public int itemLayoutId() {
            return R.layout.kg_layout_drawable_selector_item_eastern_c;
        }

        /* JADX WARN: Code duplicated, block: B:12:0x0039  */
        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public void onBind(View view, int i) {
            MiuiEasternArtCPreviewView miuiEasternArtCPreviewView;
            int dimensionPixelSize;
            super.onBind(view, i);
            if (view != null && (miuiEasternArtCPreviewView = (MiuiEasternArtCPreviewView) view.findViewById(R.id.eastern_c_style_preview_view)) != null) {
                EasternArtCStyleEditor easternArtCStyleEditor = EasternArtCStyleEditor.this;
                DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
                if (deviceUtil.isPad()) {
                    dimensionPixelSize = miuiEasternArtCPreviewView.getResources().getDimensionPixelSize(R.dimen.kg_bottom_sheet_width);
                } else {
                    Context context = miuiEasternArtCPreviewView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                    if (deviceUtil.isFoldLargeScreen(context)) {
                        dimensionPixelSize = miuiEasternArtCPreviewView.getResources().getDimensionPixelSize(R.dimen.kg_bottom_sheet_width);
                    } else {
                        Context context2 = miuiEasternArtCPreviewView.getContext();
                        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                        dimensionPixelSize = deviceUtil.currentDisplaySize(context2).width();
                    }
                }
                miuiEasternArtCPreviewView.getLayoutParams().width = (dimensionPixelSize - (miuiEasternArtCPreviewView.getResources().getDimensionPixelSize(R.dimen.kg_eastern_c_style1_frame_extra_padding) * 3)) / EasternArtCStyleEditorKt.EASTERN_ART_C_STYLE_LIST.size();
                miuiEasternArtCPreviewView.setStyle(((Number) getData().get(i)).intValue());
                miuiEasternArtCPreviewView.setCalendar(easternArtCStyleEditor.getMiuiXCalendar());
                miuiEasternArtCPreviewView.updateTimeView();
                ViewUtil viewUtil = ViewUtil.INSTANCE;
                Context context3 = miuiEasternArtCPreviewView.getContext();
                Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                miuiEasternArtCPreviewView.setClockDarkMode(viewUtil.getDarkMode(context3));
                miuiEasternArtCPreviewView.setImportantForAccessibility(4);
            }
            if (view != null) {
                FilterIconLayout filterIconLayout = view instanceof FilterIconLayout ? (FilterIconLayout) view : null;
                if (filterIconLayout != null) {
                    filterIconLayout.setRadius(view.getContext().getResources().getDimensionPixelSize(R.dimen.kg_eastern_c_style1_clock_font_select_radius));
                }
                String string = view.getContext().getString(((Number) EasternArtCStyleEditorKt.EASTERN_ART_C_STYLE_NAME.get(i)).intValue());
                view.setContentDescription((i == getSelectedIndex() ? view.getContext().getString(R.string.kg_talkback_selected) : view.getContext().getString(R.string.kg_talkback_not_selected)) + ", " + string);
            }
        }

        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public void onItemSelect(View view, int i) {
            super.onItemSelect(view, i);
            notifyDataSetChanged();
            EasternArtCStyleEditor.this.getEditCallback().onEdited(190, EasternArtCStyleEditorKt.EASTERN_ART_C_STYLE_LIST.get(i));
            final PopupLayerView popupLayerView = EasternArtCStyleEditor.this.popupLayerView();
            popupLayerView.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.style.EasternArtCStyleEditor$createDrawableAdapter$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PopupLayerView.updateLayout$default(popupLayerView, null, 1, null);
                }
            });
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EasternArtCStyleEditor(BaseClockView templateView, EditCallback editCallback, int i, FontStyleEditCallback fontStyleEditCallback) {
        super(i, templateView, editCallback, false, fontStyleEditCallback, 8, null);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        EditorDialogTitleView container = getContainer();
        String string = getContext().getString(R.string.kg_title_clock_name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        EditorDialogTitleView.setTitle$default(container, string, getContext().getString(R.string.kg_title_font_name), 0, 4, null);
        setBaseTemplateView(templateView);
    }

    @Override // com.miui.keyguard.editor.edit.style.ViewStyleEditor
    public StyleSelectorAdapter createDrawableAdapter(int i) {
        super.createDrawableAdapter(i);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(EasternArtCStyleEditorKt.EASTERN_ART_C_STYLE_LIST);
        anonymousClass1.setSelectedIndex(EasternArtCStyleEditorKt.EASTERN_ART_C_STYLE_LIST.indexOf(Integer.valueOf(i)));
        return anonymousClass1;
    }
}
