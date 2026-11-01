package com.miui.keyguard.editor.edit.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: AlignSelectAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AlignSelectAdapter extends StyleSelectorAdapter<Integer> {
    public static final List ALIGN_STYLE_LIST;
    public static final List ALIGN_STYLE_LIST_RTL;
    public static final List ALIGN_TEXT_LIST_NAMES;
    public final int alignStyle;
    public final int containerWidth;
    public final Context context;
    public final EditCallback editCallback;
    public final boolean isRtl;
    public static final Companion Companion = new Companion(null);
    public static final List ALIGN_DRAWABLE_LIST = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.drawable.kg_ic_align_top_left), Integer.valueOf(R.drawable.kg_ic_align_top_right), Integer.valueOf(R.drawable.kg_ic_align_left), Integer.valueOf(R.drawable.kg_ic_align_center), Integer.valueOf(R.drawable.kg_ic_align_right), Integer.valueOf(R.drawable.kg_ic_align_edge)});

    /* JADX INFO: compiled from: AlignSelectAdapter.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        Integer numValueOf = Integer.valueOf(com.miui.maml.R.styleable.AppCompatTheme_textAppearanceListItemSmall);
        Integer numValueOf2 = Integer.valueOf(com.miui.maml.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader);
        ALIGN_STYLE_LIST = CollectionsKt.listOf((Object[]) new Integer[]{101, 102, 103, 104, numValueOf, numValueOf2});
        ALIGN_STYLE_LIST_RTL = CollectionsKt.listOf((Object[]) new Integer[]{102, 101, numValueOf, 104, 103, numValueOf2});
        ALIGN_TEXT_LIST_NAMES = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.string.kg_magazine_b_signature_type_vertical_row_to_left), Integer.valueOf(R.string.kg_magazine_b_signature_type_vertical_row_to_right), Integer.valueOf(R.string.kg_magazine_b_signature_type_horizontal_row_left), Integer.valueOf(R.string.kg_magazine_b_signature_type_horizontal_row_centered), Integer.valueOf(R.string.kg_magazine_b_signature_type_horizontal_row_right), Integer.valueOf(R.string.kg_magazine_b_signature_type_ends_horizontal_row_aligned)});
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlignSelectAdapter(Context context, EditCallback editCallback, int i) {
        super(ALIGN_DRAWABLE_LIST);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        this.context = context;
        this.editCallback = editCallback;
        this.alignStyle = i;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        boolean zIsRtl = deviceUtil.isRtl(context);
        this.isRtl = zIsRtl;
        setSelectedIndex((zIsRtl ? ALIGN_STYLE_LIST_RTL : ALIGN_STYLE_LIST).indexOf(Integer.valueOf(i)));
        this.containerWidth = (deviceUtil.isPhone() || deviceUtil.isFoldSmallScreen(context)) ? deviceUtil.currentDisplaySize(context).width() : context.getResources().getDimensionPixelSize(R.dimen.kg_editor_container_pad_width);
    }

    @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
    public int itemLayoutId() {
        return 0;
    }

    @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
    public void onBind(View view, int i) {
        if (view == null) {
            return;
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.align_image);
        imageView.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.kg_background_align_select_view));
        Drawable drawable = ContextCompat.getDrawable(imageView.getContext(), ((Number) getData().get(i)).intValue());
        if (drawable != null) {
            drawable.setLayoutDirection(1);
            drawable.setAutoMirrored(true);
        } else {
            drawable = null;
        }
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(view.getContext().getString(((Number) ALIGN_TEXT_LIST_NAMES.get(i)).intValue()));
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        if (this.alignStyle == 106) {
            this.editCallback.onEdited(61, Integer.valueOf(selectedData()));
        }
        setItemWidth(view);
        super.onBind(view, i);
    }

    @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public StyleSelectorAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_layout_drawable_selector_item_align, parent, false);
        Intrinsics.checkNotNull(viewInflate);
        return new StyleSelectorAdapter.ItemViewHolder(viewInflate);
    }

    @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
    public void onItemSelect(View view, int i) {
        super.onItemSelect(view, i);
        this.editCallback.onEdited(61, Integer.valueOf(selectedData()));
    }

    public final int selectedData() {
        return ((Number) (this.isRtl ? ALIGN_STYLE_LIST_RTL : ALIGN_STYLE_LIST).get(getSelectedIndex())).intValue();
    }

    public final void setItemWidth(View view) {
        ViewGroup.LayoutParams layoutParams;
        if (view == null || (layoutParams = view.getLayoutParams()) == null) {
            return;
        }
        layoutParams.width = (this.containerWidth - (this.context.getResources().getDimensionPixelSize(R.dimen.kg_editor_first_and_last_item_offset) * 2)) / ALIGN_STYLE_LIST.size();
    }
}
