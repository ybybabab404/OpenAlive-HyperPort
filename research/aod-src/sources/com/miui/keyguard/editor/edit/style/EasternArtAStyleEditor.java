package com.miui.keyguard.editor.edit.style;

import android.content.Context;
import android.view.View;
import com.miui.clock.aesthetics.MiuiAestheticsAPreviewView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.edit.view.PopupLayerView;
import com.miui.keyguard.editor.edit.view.StyleSelectorAdapter;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
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
/* JADX INFO: compiled from: EasternArtAStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class EasternArtAStyleEditor extends ViewStyleEditor {
    public final int containerWidth;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.style.EasternArtAStyleEditor$createDrawableAdapter$1, reason: invalid class name */
    /* JADX INFO: compiled from: EasternArtAStyleEditor.kt */
    @Metadata
    @SourceDebugExtension
    public static final class AnonymousClass1 extends StyleSelectorAdapter<Integer> {
        public AnonymousClass1(List list) {
            super(list);
        }

        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public int itemLayoutId() {
            return R.layout.kg_layout_drawable_selector_item_eastern_a;
        }

        /* JADX WARN: Code duplicated, block: B:12:0x0039  */
        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public void onBind(View view, int i) {
            MiuiAestheticsAPreviewView miuiAestheticsAPreviewView;
            int dimensionPixelSize;
            super.onBind(view, i);
            if (view == null || (miuiAestheticsAPreviewView = (MiuiAestheticsAPreviewView) view.findViewById(R.id.eastern_a_style_preview_view)) == null) {
                return;
            }
            EasternArtAStyleEditor easternArtAStyleEditor = EasternArtAStyleEditor.this;
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if (deviceUtil.isPad()) {
                dimensionPixelSize = miuiAestheticsAPreviewView.getResources().getDimensionPixelSize(R.dimen.kg_bottom_sheet_width);
            } else {
                Context context = miuiAestheticsAPreviewView.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                if (deviceUtil.isFoldLargeScreen(context)) {
                    dimensionPixelSize = miuiAestheticsAPreviewView.getResources().getDimensionPixelSize(R.dimen.kg_bottom_sheet_width);
                } else {
                    Context context2 = miuiAestheticsAPreviewView.getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                    dimensionPixelSize = deviceUtil.currentDisplaySize(context2).width();
                }
            }
            miuiAestheticsAPreviewView.getLayoutParams().width = (dimensionPixelSize - (miuiAestheticsAPreviewView.getResources().getDimensionPixelSize(R.dimen.kg_eastern_a_style2_frame_extra_padding) * 2)) / EasternArtAStyleEditorKt.EASTERN_ART_A_STYLE_LIST.size();
            miuiAestheticsAPreviewView.setStyle(((Number) getData().get(i)).intValue());
            miuiAestheticsAPreviewView.setCalendar(easternArtAStyleEditor.getMiuiXCalendar());
            miuiAestheticsAPreviewView.updateTimeView();
            ViewUtil viewUtil = ViewUtil.INSTANCE;
            Context context3 = miuiAestheticsAPreviewView.getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
            miuiAestheticsAPreviewView.setClockDarkMode(viewUtil.getDarkMode(context3));
            miuiAestheticsAPreviewView.setContentDescription(miuiAestheticsAPreviewView.getContext().getString(((Number) EasternArtAStyleEditorKt.EASTERN_ART_A_STYLE_NAME.get(i)).intValue()));
        }

        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public void onItemSelect(View view, int i) {
            super.onItemSelect(view, i);
            EasternArtAStyleEditor.this.getEditCallback().onEdited(170, EasternArtAStyleEditorKt.EASTERN_ART_A_STYLE_LIST.get(i));
            final PopupLayerView popupLayerView = EasternArtAStyleEditor.this.popupLayerView();
            popupLayerView.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.style.EasternArtAStyleEditor$createDrawableAdapter$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PopupLayerView.updateLayout$default(popupLayerView, null, 1, null);
                }
            });
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EasternArtAStyleEditor(BaseClockView templateView, EditCallback editCallback, int i, FontStyleEditCallback fontStyleEditCallback) {
        super(i, templateView, editCallback, false, fontStyleEditCallback, 8, null);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        EditorDialogTitleView container = getContainer();
        String string = getContext().getString(R.string.kg_title_clock_name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        EditorDialogTitleView.setTitle$default(container, string, getContext().getString(R.string.kg_title_font_name), 0, 4, null);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        this.containerWidth = (deviceUtil.isPhone() || deviceUtil.isFoldSmallScreen(getContext())) ? deviceUtil.currentDisplaySize(getContext()).width() : getContext().getResources().getDimensionPixelSize(R.dimen.kg_editor_container_pad_width);
        setBaseTemplateView(templateView);
        setSelectorViewVisible(8);
    }

    @Override // com.miui.keyguard.editor.edit.style.ViewStyleEditor
    public StyleSelectorAdapter createDrawableAdapter(int i) {
        super.createDrawableAdapter(i);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(EasternArtAStyleEditorKt.EASTERN_ART_A_STYLE_LIST);
        anonymousClass1.setSelectedIndex(EasternArtAStyleEditorKt.EASTERN_ART_A_STYLE_LIST.indexOf(Integer.valueOf(i)));
        return anonymousClass1;
    }
}
