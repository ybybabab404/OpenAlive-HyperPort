package com.miui.keyguard.editor.edit.style;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.doodleclock.DoodleTemplateView;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.DualClockManager;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.view.FontFilterView;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.keyguard.editor.viewmodel.IntWrapper;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: DoodleStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DoodleStyleEditor extends DrawableStyleEditor {
    public final int containerWidth;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoodleStyleEditor(DoodleTemplateView templateView, int i, FontStyleEditCallback fontStyleEditCallback) {
        super(templateView, templateView, DoodleStyleEditorKt.DOODLE_DRAWABLE_LIST, DoodleStyleEditorKt.DOODLE_STYLE_LIST, i, fontStyleEditCallback);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        setDrawableSelectorViewGone();
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        this.containerWidth = (deviceUtil.isPhone() || deviceUtil.isFoldSmallScreen(getContext())) ? deviceUtil.currentDisplaySize(getContext()).width() : getContext().getResources().getDimensionPixelSize(R.dimen.kg_editor_container_pad_width);
    }

    public static final void createFontEditorContent$lambda$0(EditFragmentViewModel editFragmentViewModel, DoodleStyleEditor doodleStyleEditor, CompoundButton compoundButton, boolean z) {
        IntWrapper doodleStatus;
        if (editFragmentViewModel == null || (doodleStatus = editFragmentViewModel.getDoodleStatus()) == null || doodleStatus.getIntValue() != 0) {
            if (editFragmentViewModel != null) {
                EditFragmentViewModel.updateDoodleStatus$default(editFragmentViewModel, z ? 1 : 2, false, 2, null);
                return;
            }
            return;
        }
        compoundButton.setChecked(false);
        if (doodleStyleEditor.getBaseTemplateView().isInThirdPartyWallpaperService()) {
            ToastUtil.INSTANCE.showText(doodleStyleEditor.getContext(), R.string.kg_doodle_text_not_support_third_party_wallpaper_service_tip);
            return;
        }
        if (doodleStyleEditor.getBaseTemplateView().isGalleryOpened()) {
            ToastUtil.INSTANCE.showText(doodleStyleEditor.getContext(), R.string.kg_doodle_text_not_support_gallery_tip);
        } else if (DualClockManager.isDualClockAvailable(doodleStyleEditor.getContext())) {
            ToastUtil.INSTANCE.showText(doodleStyleEditor.getContext(), R.string.kg_doodle_text_not_support_clock_tip);
        } else {
            ToastUtil.INSTANCE.showText(doodleStyleEditor.getContext(), R.string.kg_doodle_text_not_support_wallpaper_tip);
        }
    }

    @Override // com.miui.keyguard.editor.edit.style.DrawableStyleEditor, com.miui.keyguard.editor.edit.base.AbstractPopupEditor
    public View createFontEditorContent() {
        if (getFontFilterView() == null) {
            super.createFontEditorContent();
            final EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(getBaseTemplateView().getEditFragment());
            CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.keyguard.editor.edit.style.DoodleStyleEditor$$ExternalSyntheticLambda0
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    DoodleStyleEditor.createFontEditorContent$lambda$0(viewModel, this, compoundButton, z);
                }
            };
            FontFilterView fontFilterView = getFontFilterView();
            if (fontFilterView != null) {
                fontFilterView.setDoodleSwitchContainer(onCheckedChangeListener, 0);
            }
        }
        return getEditorPagerContentWrapper();
    }

    @Override // com.miui.keyguard.editor.edit.style.DrawableStyleEditor
    public void onDrawableBind(ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "imageView");
        setItemWidth(imageView);
        imageView.getLayoutParams().height = getContext().getResources().getDimensionPixelOffset(R.dimen.kg_doodle_style_editor_item_height);
    }

    @Override // com.miui.keyguard.editor.edit.style.DrawableStyleEditor
    public void onItemBind(View itemView, int i) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        super.onItemBind(itemView, i);
        itemView.setContentDescription(getContext().getString(((Number) DoodleStyleEditorKt.DOODLE_STYLE_NAME.get(i)).intValue()));
    }

    @Override // com.miui.keyguard.editor.edit.style.DrawableStyleEditor
    public void onItemSelected(int i) {
        getEditCallback().onEdited(140, DoodleStyleEditorKt.DOODLE_STYLE_LIST.get(i));
    }

    public final void setItemWidth(View view) {
        ViewGroup.LayoutParams layoutParams;
        if (view == null || (layoutParams = view.getLayoutParams()) == null) {
            return;
        }
        layoutParams.width = (this.containerWidth - (getContext().getResources().getDimensionPixelSize(R.dimen.kg_doodle_clock_editor_container_padding) * 2)) / DoodleStyleEditorKt.DOODLE_DRAWABLE_LIST.size();
    }
}
