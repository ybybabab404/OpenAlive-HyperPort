package com.miui.keyguard.editor.edit.style;

import android.content.Context;
import android.view.View;
import com.miui.clock.oversize.a.OversizeAPreviewView;
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
/* JADX INFO: compiled from: OversizeAStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class OversizeAStyleEditor extends ViewStyleEditor {
    public final int containerWidth;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.style.OversizeAStyleEditor$createDrawableAdapter$1, reason: invalid class name */
    /* JADX INFO: compiled from: OversizeAStyleEditor.kt */
    @Metadata
    @SourceDebugExtension
    public static final class AnonymousClass1 extends StyleSelectorAdapter<Integer> {
        public AnonymousClass1(List list) {
            super(list);
        }

        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public int itemLayoutId() {
            return R.layout.kg_layout_drawable_selector_item_oversize_a;
        }

        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public void onBind(View view, int i) {
            OversizeAPreviewView oversizeAPreviewView;
            super.onBind(view, i);
            if (view != null && (oversizeAPreviewView = (OversizeAPreviewView) view.findViewById(R.id.oversize_a_style_preview_view)) != null) {
                OversizeAStyleEditor oversizeAStyleEditor = OversizeAStyleEditor.this;
                oversizeAPreviewView.setStyle(((Number) getData().get(i)).intValue());
                oversizeAPreviewView.setCalendar(oversizeAStyleEditor.getMiuiXCalendar());
                oversizeAPreviewView.updateTimeView();
                ViewUtil viewUtil = ViewUtil.INSTANCE;
                Context context = oversizeAPreviewView.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                oversizeAPreviewView.setClockDarkMode(viewUtil.getDarkMode(context));
            }
            if (view != null) {
                view.setContentDescription(OversizeAStyleEditor.this.getContext().getString(((Number) OversizeAStyleEditorKt.OVERSIZE_A_STYLE_NAMES.get(i)).intValue()));
            }
        }

        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public void onItemSelect(View view, int i) {
            super.onItemSelect(view, i);
            OversizeAStyleEditor.this.getEditCallback().onEdited(160, OversizeAStyleEditorKt.OVERSIZE_A_STYLE_LIST.get(i));
            final PopupLayerView popupLayerView = OversizeAStyleEditor.this.popupLayerView();
            popupLayerView.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.style.OversizeAStyleEditor$createDrawableAdapter$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PopupLayerView.updateLayout$default(popupLayerView, null, 1, null);
                }
            });
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OversizeAStyleEditor(BaseClockView templateView, EditCallback editCallback, int i, FontStyleEditCallback fontStyleEditCallback) {
        super(i, templateView, editCallback, false, fontStyleEditCallback, 8, null);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        setSelectorViewVisible(8);
        EditorDialogTitleView container = getContainer();
        String string = getContext().getString(R.string.kg_title_clock_name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        EditorDialogTitleView.setTitle$default(container, string, getContext().getString(R.string.kg_title_font_name), 0, 4, null);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        this.containerWidth = (deviceUtil.isPhone() || deviceUtil.isFoldSmallScreen(getContext())) ? deviceUtil.currentDisplaySize(getContext()).width() : getContext().getResources().getDimensionPixelSize(R.dimen.kg_editor_container_pad_width);
    }

    @Override // com.miui.keyguard.editor.edit.style.ViewStyleEditor
    public StyleSelectorAdapter createDrawableAdapter(int i) {
        super.createDrawableAdapter(i);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(OversizeAStyleEditorKt.OVERSIZE_A_STYLE_LIST);
        anonymousClass1.setSelectedIndex(OversizeAStyleEditorKt.OVERSIZE_A_STYLE_LIST.indexOf(Integer.valueOf(i)));
        return anonymousClass1;
    }
}
