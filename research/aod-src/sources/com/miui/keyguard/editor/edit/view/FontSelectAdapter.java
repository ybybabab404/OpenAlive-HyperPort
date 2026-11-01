package com.miui.keyguard.editor.edit.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
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
/* JADX INFO: compiled from: FontSelectAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FontSelectAdapter extends StyleSelectorAdapter<Integer> {
    public final int containerWidth;
    public Context context;
    public final EditCallback editCallback;
    public List fontTypeList;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FontSelectAdapter(EditCallback editCallback, int i, String templateId, Context context) {
        super(Intrinsics.areEqual("magazine_a", templateId) ? FontSelectAdapterKt.FONT_DRAWABLE_LIST : CollectionsKt.reversed(FontSelectAdapterKt.FONT_DRAWABLE_LIST));
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        Intrinsics.checkNotNullParameter(context, "context");
        this.editCallback = editCallback;
        this.context = context;
        this.fontTypeList = CollectionsKt.listOf((Object[]) new Integer[]{0, 1});
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        this.containerWidth = (deviceUtil.isPhone() || deviceUtil.isFoldSmallScreen(this.context)) ? deviceUtil.currentDisplaySize(this.context).width() : this.context.getResources().getDimensionPixelSize(R.dimen.kg_editor_container_pad_width);
        setSelectedIndex(getSelectedIndex(i, templateId));
    }

    private final void setItemWidth(View view) {
        ViewGroup.LayoutParams layoutParams;
        if (view == null || (layoutParams = view.getLayoutParams()) == null) {
            return;
        }
        layoutParams.width = (this.containerWidth - (this.context.getResources().getDimensionPixelSize(R.dimen.kg_magazine_b_signature_font_style_width) * 2)) / FontSelectAdapterKt.FONT_DRAWABLE_LIST.size();
    }

    public final List getFontTypeList(String str) {
        return Intrinsics.areEqual(str, "magazine_a") ? this.fontTypeList : CollectionsKt.reversed(this.fontTypeList);
    }

    public final int getSelectedIndex(int i, String str) {
        List fontTypeList = getFontTypeList(str);
        this.fontTypeList = fontTypeList;
        return fontTypeList.indexOf(Integer.valueOf(i));
    }

    @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
    public int itemLayoutId() {
        return R.layout.kg_layout_font_style_selector_item;
    }

    @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
    public void onBind(View view, int i) {
        super.onBind(view, i);
        if (view == null) {
            return;
        }
        setItemWidth(view);
        ((ImageView) view.findViewById(R.id.drawable_selector_item_icon)).setImageResource(((Number) getData().get(i)).intValue());
        Context context = view.getContext();
        view.setContentDescription(context != null ? context.getString(((Number) FontSelectAdapterKt.FONT_DRAWABLE_LIST_NAMES.get(i)).intValue()) : null);
    }

    @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
    public void onItemSelect(View view, int i) {
        super.onItemSelect(view, i);
        this.editCallback.onEdited(62, this.fontTypeList.get(i));
    }
}
