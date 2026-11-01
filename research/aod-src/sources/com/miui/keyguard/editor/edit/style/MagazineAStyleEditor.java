package com.miui.keyguard.editor.edit.style;

import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.magazineclock.MagazineATemplateView;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.edit.view.PopupLayerView;
import com.miui.keyguard.editor.edit.view.StyleSelectorAdapter;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.view.ColorModificationTarget;
import com.miui.keyguard.editor.view.FilterIconLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.core.util.WindowUtils;

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
/* JADX INFO: compiled from: MagazineAStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class MagazineAStyleEditor extends ViewStyleEditor {

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.style.MagazineAStyleEditor$createDrawableAdapter$1, reason: invalid class name */
    /* JADX INFO: compiled from: MagazineAStyleEditor.kt */
    @Metadata
    @SourceDebugExtension
    public static final class AnonymousClass1 extends StyleSelectorAdapter<Integer> {
        public AnonymousClass1(List list) {
            super(list);
        }

        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public int itemLayoutId() {
            return R.layout.kg_layout_drawable_selector_item_magazine_a;
        }

        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public void onBind(View view, int i) {
            super.onBind(view, i);
            ImageView imageView = view != null ? (ImageView) view.findViewById(R.id.magazine_a_style_preview_view) : null;
            if (imageView != null) {
                Intrinsics.checkNotNull(view);
                imageView.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.kg_background_align_select_view));
            }
            if (imageView != null) {
                Drawable drawable = ContextCompat.getDrawable(imageView.getContext(), ((Number) MagazineAStyleEditorKt.MAGAZINE_A_STYLE_ALIGN_DRAWABLE_LIST.get(i)).intValue());
                if (drawable != null) {
                    drawable.setAutoMirrored(true);
                } else {
                    drawable = null;
                }
                imageView.setImageDrawable(drawable);
            }
            if (imageView != null) {
                imageView.setImportantForAccessibility(4);
            }
            int iIntValue = ((Number) getData().get(i)).intValue();
            if (iIntValue == 1) {
                MagazineAStyleEditor magazineAStyleEditor = MagazineAStyleEditor.this;
                magazineAStyleEditor.updateImageAlignment(imageView, 3, true ^ DeviceUtil.INSTANCE.isRtl(magazineAStyleEditor.getContext()));
            } else if (iIntValue == 3) {
                MagazineAStyleEditor magazineAStyleEditor2 = MagazineAStyleEditor.this;
                magazineAStyleEditor2.updateImageAlignment(imageView, 5, DeviceUtil.INSTANCE.isRtl(magazineAStyleEditor2.getContext()));
            }
            MagazineAStyleEditor.this.setItemWidth(view);
            if (view != null) {
                FilterIconLayout filterIconLayout = view instanceof FilterIconLayout ? (FilterIconLayout) view : null;
                if (filterIconLayout != null) {
                    filterIconLayout.setRadius(((FilterIconLayout) view).getContext().getResources().getDimensionPixelSize(R.dimen.kg_filter_drawable_selector_item_radius));
                }
                String string = view.getContext().getString(((Number) MagazineAStyleEditorKt.MAGAZINE_A_STYLE_NAMES.get(i)).intValue());
                view.setContentDescription((i == getSelectedIndex() ? view.getContext().getString(R.string.kg_talkback_selected) : view.getContext().getString(R.string.kg_talkback_not_selected)) + ", " + string);
            }
        }

        @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
        public void onItemSelect(View view, int i) {
            super.onItemSelect(view, i);
            notifyDataSetChanged();
            MagazineAStyleEditor.this.getEditCallback().onEdited(130, MagazineAStyleEditorKt.MAGAZINE_A_STYLE_LIST.get(i));
            final PopupLayerView popupLayerView = MagazineAStyleEditor.this.popupLayerView();
            popupLayerView.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.style.MagazineAStyleEditor$createDrawableAdapter$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PopupLayerView.updateLayout$default(popupLayerView, null, 1, null);
                }
            });
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagazineAStyleEditor(MagazineATemplateView templateView, EditCallback editCallback, int i, FontStyleEditCallback fontStyleEditCallback) {
        super(i, templateView, editCallback, false, fontStyleEditCallback, 8, null);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        EditorDialogTitleView container = getContainer();
        String string = getContext().getString(R.string.kg_title_clock_name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        EditorDialogTitleView.setTitle$default(container, string, getContext().getString(R.string.kg_title_font_name), 0, 4, null);
        setBaseTemplateView(templateView);
        View viewFindViewById = getEditorFontContainer().findViewById(R.id.drawable_selector_view);
        if (viewFindViewById != null) {
            ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
            if (marginLayoutParams != null) {
                marginLayoutParams.topMargin = 0;
                viewFindViewById.setLayoutParams(marginLayoutParams);
            }
        }
        TextView textView = (TextView) getEditorFontContainer().findViewById(R.id.drawable_selector_view_title);
        if (textView != null) {
            textView.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setItemWidth(View view) {
        ViewGroup.LayoutParams layoutParams;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowUtils.getWindowManager(getContext()).getDefaultDisplay().getMetrics(displayMetrics);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        int dimensionPixelSize = (deviceUtil.isPhone() || deviceUtil.isFoldSmallScreen(getContext())) ? displayMetrics.widthPixels : getContext().getResources().getDimensionPixelSize(R.dimen.kg_editor_container_pad_width);
        if (view == null || (layoutParams = view.getLayoutParams()) == null) {
            return;
        }
        layoutParams.width = (dimensionPixelSize - (getContext().getResources().getDimensionPixelSize(R.dimen.kg_editor_first_and_last_item_offset) * 2)) / MagazineAStyleEditorKt.MAGAZINE_A_STYLE_LIST.size();
    }

    @Override // com.miui.keyguard.editor.edit.style.ViewStyleEditor
    public StyleSelectorAdapter createDrawableAdapter(int i) {
        super.createDrawableAdapter(i);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(MagazineAStyleEditorKt.MAGAZINE_A_STYLE_LIST);
        anonymousClass1.setSelectedIndex(MagazineAStyleEditorKt.MAGAZINE_A_STYLE_LIST.indexOf(Integer.valueOf(i)));
        return anonymousClass1;
    }

    @Override // com.miui.keyguard.editor.edit.style.ViewStyleEditor
    public ColorModificationTarget getColorModificationTarget() {
        return ColorModificationTarget.PRIMARY_PART;
    }

    public final void updateImageAlignment(ImageView imageView, int i, boolean z) {
        ViewGroup.LayoutParams layoutParams = imageView != null ? imageView.getLayoutParams() : null;
        FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
        if (layoutParams2 != null) {
            layoutParams2.gravity = i;
        }
        if (imageView != null) {
            imageView.setLayoutParams(layoutParams2);
        }
        if (imageView != null) {
            imageView.setScaleType(z ? ImageView.ScaleType.FIT_START : ImageView.ScaleType.FIT_END);
        }
    }
}
