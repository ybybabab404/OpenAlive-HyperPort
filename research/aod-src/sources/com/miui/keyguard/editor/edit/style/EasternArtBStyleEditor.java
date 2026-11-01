package com.miui.keyguard.editor.edit.style;

import android.content.Context;
import android.view.View;
import com.miui.clock.eastern.b.EasternArtBClockPreviewView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.edit.view.PopupLayerView;
import com.miui.keyguard.editor.edit.view.StyleSelectorAdapter;
import com.miui.keyguard.editor.utils.DeviceUtil;
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
/* JADX INFO: compiled from: EasternArtBStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class EasternArtBStyleEditor extends ViewStyleEditor {

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.style.EasternArtBStyleEditor$createDrawableAdapter$1, reason: invalid class name */
    /* JADX INFO: compiled from: EasternArtBStyleEditor.kt */
    @Metadata
    @SourceDebugExtension
    public static final class AnonymousClass1 extends StyleSelectorAdapter<Integer> {
        public AnonymousClass1(List list) {
            super(list);
        }

        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public int itemLayoutId() {
            return R.layout.kg_layout_drawable_selector_item_eastern_b;
        }

        /* JADX WARN: Code duplicated, block: B:12:0x0039  */
        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public void onBind(View view, int i) {
            EasternArtBClockPreviewView easternArtBClockPreviewView;
            int dimensionPixelSize;
            super.onBind(view, i);
            if (view == null || (easternArtBClockPreviewView = (EasternArtBClockPreviewView) view.findViewById(R.id.eastern_b_style_preview_view)) == null) {
                return;
            }
            EasternArtBStyleEditor easternArtBStyleEditor = EasternArtBStyleEditor.this;
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if (deviceUtil.isPad()) {
                dimensionPixelSize = easternArtBClockPreviewView.getResources().getDimensionPixelSize(R.dimen.kg_bottom_sheet_width);
            } else {
                Context context = easternArtBClockPreviewView.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                if (deviceUtil.isFoldLargeScreen(context)) {
                    dimensionPixelSize = easternArtBClockPreviewView.getResources().getDimensionPixelSize(R.dimen.kg_bottom_sheet_width);
                } else {
                    Context context2 = easternArtBClockPreviewView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                    dimensionPixelSize = deviceUtil.currentDisplaySize(context2).width();
                }
            }
            easternArtBClockPreviewView.getLayoutParams().width = (dimensionPixelSize - (easternArtBClockPreviewView.getResources().getDimensionPixelSize(R.dimen.kg_eastern_a_style2_frame_extra_padding) * 2)) / EasternArtBStyleEditorKt.EASTERN_ART_B_STYLE_LIST.size();
            easternArtBClockPreviewView.setStyle(((Number) getData().get(i)).intValue());
            easternArtBClockPreviewView.setCalendar(easternArtBStyleEditor.getMiuiXCalendar());
            easternArtBClockPreviewView.updateTimeView();
        }

        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public void onItemSelect(View view, int i) {
            super.onItemSelect(view, i);
            EasternArtBStyleEditor.this.getEditCallback().onEdited(180, EasternArtBStyleEditorKt.EASTERN_ART_B_STYLE_LIST.get(i));
            final PopupLayerView popupLayerView = EasternArtBStyleEditor.this.popupLayerView();
            popupLayerView.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.style.EasternArtBStyleEditor$createDrawableAdapter$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PopupLayerView.updateLayout$default(popupLayerView, null, 1, null);
                }
            });
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EasternArtBStyleEditor(BaseClockView templateView, EditCallback editCallback, int i, FontStyleEditCallback fontStyleEditCallback) {
        super(i, templateView, editCallback, false, fontStyleEditCallback, 8, null);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        EditorDialogTitleView container = getContainer();
        String string = getContext().getString(R.string.kg_title_clock_name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        EditorDialogTitleView.setTitle$default(container, string, getContext().getString(R.string.kg_title_font_name), 0, 4, null);
        setBaseTemplateView(templateView);
        setSelectorViewVisible(8);
    }

    @Override // com.miui.keyguard.editor.edit.style.ViewStyleEditor
    public StyleSelectorAdapter createDrawableAdapter(int i) {
        super.createDrawableAdapter(i);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(EasternArtBStyleEditorKt.EASTERN_ART_B_STYLE_LIST);
        anonymousClass1.setSelectedIndex(EasternArtBStyleEditorKt.EASTERN_ART_B_STYLE_LIST.indexOf(Integer.valueOf(i)));
        return anonymousClass1;
    }
}
