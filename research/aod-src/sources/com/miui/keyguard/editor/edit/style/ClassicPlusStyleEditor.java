package com.miui.keyguard.editor.edit.style;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.clock.classic.ClassicPlusClockPreviewView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.view.StyleSelectorAdapter;
import com.miui.keyguard.editor.utils.ViewUtil;
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
/* JADX INFO: compiled from: ClassicPlusStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ClassicPlusStyleEditor extends ViewStyleEditor {
    public int currentFontStyle;

    @Override // com.miui.keyguard.editor.edit.style.ViewStyleEditor
    public StyleSelectorAdapter createDrawableAdapter(int i) {
        super.createDrawableAdapter(i);
        StyleSelectorAdapter<Integer> styleSelectorAdapter = new StyleSelectorAdapter<Integer>(ClassicPlusStyleEditorKt.CLOCK_STYLES) { // from class: com.miui.keyguard.editor.edit.style.ClassicPlusStyleEditor.createDrawableAdapter.1
            @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public int itemLayoutId() {
                return R.layout.kg_layout_drawable_selector_item_classic_plus;
            }

            @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onBind(View view, int i2) {
                ClassicPlusClockPreviewView classicPlusClockPreviewView;
                super.onBind(view, i2);
                if (view == null || (classicPlusClockPreviewView = (ClassicPlusClockPreviewView) view.findViewById(R.id.classic_plus_style_preview_view)) == null) {
                    return;
                }
                ClassicPlusStyleEditor classicPlusStyleEditor = ClassicPlusStyleEditor.this;
                classicPlusClockPreviewView.setFontStyle(classicPlusStyleEditor.currentFontStyle);
                classicPlusClockPreviewView.setStyle(((Number) getData().get(i2)).intValue());
                classicPlusClockPreviewView.setCalendar(classicPlusStyleEditor.getMiuiXCalendar());
                classicPlusClockPreviewView.updateTimeView();
                ViewUtil viewUtil = ViewUtil.INSTANCE;
                Context context = classicPlusClockPreviewView.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                classicPlusClockPreviewView.setClockDarkMode(viewUtil.getDarkMode(context));
                classicPlusClockPreviewView.setContentDescription(classicPlusClockPreviewView.getContext().getString(((Number) ClassicPlusStyleEditorKt.CLOCK_STYLES_NAME.get(i2)).intValue()));
            }

            @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onItemSelect(View view, int i2) {
                super.onItemSelect(view, i2);
                ClassicPlusStyleEditor.this.getEditCallback().onEdited(com.miui.maml.R.styleable.AppCompatTheme_textColorAlertDialogListItem, ClassicPlusStyleEditorKt.CLOCK_STYLES.get(i2));
            }
        };
        styleSelectorAdapter.setSelectedIndex(ClassicPlusStyleEditorKt.CLOCK_STYLES.indexOf(Integer.valueOf(i)));
        return styleSelectorAdapter;
    }

    @Override // com.miui.keyguard.editor.edit.style.ViewStyleEditor
    public RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), ClassicPlusStyleEditorKt.CLOCK_STYLES.size());
    }

    @Override // com.miui.keyguard.editor.edit.style.ViewStyleEditor, com.miui.keyguard.editor.edit.style.RealtimeStyleEditor
    public void onTimeUpdate() {
        super.onTimeUpdate();
        StyleSelectorAdapter drawableAdapter = getDrawableAdapter();
        if (drawableAdapter != null) {
            drawableAdapter.notifyDataSetChanged();
        }
    }

    public void updateClockStyle(int i) {
        this.currentFontStyle = i;
        StyleSelectorAdapter drawableAdapter = getDrawableAdapter();
        if (drawableAdapter != null) {
            drawableAdapter.notifyDataSetChanged();
        }
    }
}
