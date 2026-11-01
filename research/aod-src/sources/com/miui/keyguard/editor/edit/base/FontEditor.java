package com.miui.keyguard.editor.edit.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.style.FontStyleEditCallback;
import com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter;
import com.miui.keyguard.editor.edit.view.StyleSelectorHorizontalView;
import com.miui.keyguard.editor.view.FilterIconLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.androidbasewidget.widget.SeekBar;

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
/* JADX INFO: compiled from: FontEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class FontEditor extends AbstractPopupEditor {
    public static final List FONT_STYLE_NAMES;
    public static final List PLUS_MAX_FONT_STYLE_NAMES;
    public final DrawableSelectorAdapter adapter;
    public final View container;
    public final SeekBar seekBar;
    public final View seekBarContainer;
    public final View selectorContainer;
    public final StyleSelectorHorizontalView selectorView;
    public final int weightRange;
    public static final Companion Companion = new Companion(null);
    public static final List FONT_WEIGHT_DEFAULT_LIST = CollectionsKt.listOf((Object[]) new Integer[]{420, 420, 520, 330, 420});
    public static final List FONT_STYLES = CollectionsKt.listOf((Object[]) new Integer[]{21, 22, 23, 24, 25, 26});
    public static final List PLUS_MAX_FONT_STYLES = CollectionsKt.listOf((Object[]) new Integer[]{21, 22, 23, 24, 25});
    public static final List FONT_STYLE_DRAWABLES = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.drawable.kg_font_mi_sans_400), Integer.valueOf(R.drawable.kg_font_mi_sans_200), Integer.valueOf(R.drawable.kg_font_mi_sans_600), Integer.valueOf(R.drawable.kg_font_mi_sans_round_lock), Integer.valueOf(R.drawable.kg_font_mi_camera), Integer.valueOf(R.drawable.kg_font_bodoni_moda)});
    public static final List PLUS_MAX_FONT_STYLE_DRAWABLES = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.drawable.kg_font_mi_sans_a), Integer.valueOf(R.drawable.kg_font_mi_sans_b), Integer.valueOf(R.drawable.kg_font_mi_sans_c), Integer.valueOf(R.drawable.kg_font_mi_sans_d), Integer.valueOf(R.drawable.kg_font_mi_sans_e)});

    /* JADX INFO: compiled from: FontEditor.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        int i = R.string.kg_classic_font_type_misans_bold;
        Integer numValueOf = Integer.valueOf(i);
        Integer numValueOf2 = Integer.valueOf(R.string.kg_classic_font_type_misans_slim);
        Integer numValueOf3 = Integer.valueOf(R.string.kg_classic_font_type_misans_extra_bold);
        int i2 = R.string.kg_classic_font_type_misans_round;
        Integer numValueOf4 = Integer.valueOf(i2);
        int i3 = R.string.kg_classic_font_type_misans_square;
        Integer numValueOf5 = Integer.valueOf(i3);
        int i4 = R.string.kg_classic_font_type_misans_serif;
        FONT_STYLE_NAMES = CollectionsKt.listOf((Object[]) new Integer[]{numValueOf, numValueOf2, numValueOf3, numValueOf4, numValueOf5, Integer.valueOf(i4)});
        PLUS_MAX_FONT_STYLE_NAMES = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i4), Integer.valueOf(i3), Integer.valueOf(i2)});
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FontEditor(FrameLayout templateView, int i, final EditCallback editCallback, final boolean z, int i2, FontStyleEditCallback fontStyleEditCallback) {
        super(templateView, editCallback, fontStyleEditCallback);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.kg_layout_font_editor, (ViewGroup) templateView, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        this.container = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.selectorContainer = viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.selector);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        StyleSelectorHorizontalView styleSelectorHorizontalView = (StyleSelectorHorizontalView) viewFindViewById2;
        this.selectorView = styleSelectorHorizontalView;
        View viewFindViewById3 = viewInflate.findViewById(R.id.font_size_seekbar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        this.seekBar = (SeekBar) viewFindViewById3;
        View viewFindViewById4 = viewInflate.findViewById(R.id.font_weight_slider);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
        this.seekBarContainer = viewFindViewById4;
        this.weightRange = 430;
        final List list = z ? PLUS_MAX_FONT_STYLE_DRAWABLES : FONT_STYLE_DRAWABLES;
        DrawableSelectorAdapter drawableSelectorAdapter = new DrawableSelectorAdapter(list) { // from class: com.miui.keyguard.editor.edit.base.FontEditor$adapter$1
            @Override // com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter, com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public int itemLayoutId() {
                return R.layout.kg_layout_drawable_selector_item_no_padding;
            }

            @Override // com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter, com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onBind(View view, int i3) {
                super.onBind(view, i3);
                ImageView imageView = view != null ? (ImageView) view.findViewById(R.id.drawable_selector_item_icon) : null;
                FontEditor fontEditor = this;
                boolean z2 = z;
                if (imageView != null) {
                    imageView.setContentDescription(fontEditor.getContext().getString(((Number) (z2 ? FontEditor.PLUS_MAX_FONT_STYLE_NAMES : FontEditor.FONT_STYLE_NAMES).get(i3)).intValue()));
                }
                if (view != null) {
                    FilterIconLayout filterIconLayout = view instanceof FilterIconLayout ? (FilterIconLayout) view : null;
                    if (filterIconLayout != null) {
                        filterIconLayout.setRadius(view.getContext().getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_font_select_radius));
                    }
                }
            }

            @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onItemSelect(View view, int i3) {
                super.onItemSelect(view, i3);
                editCallback.onEdited(20, Integer.valueOf(((Number) (z ? FontEditor.PLUS_MAX_FONT_STYLES : FontEditor.FONT_STYLES).get(i3)).intValue()));
                if (z) {
                    this.setFontDefaultWeight(((Number) FontEditor.FONT_WEIGHT_DEFAULT_LIST.get(i3)).intValue());
                }
            }
        };
        this.adapter = drawableSelectorAdapter;
        styleSelectorHorizontalView.setAdapter(drawableSelectorAdapter);
        int iFindIndex = z ? findIndex(PLUS_MAX_FONT_STYLES, i) : findIndex(FONT_STYLES, i);
        select(iFindIndex);
        if (z) {
            List list2 = FONT_WEIGHT_DEFAULT_LIST;
            if (iFindIndex < list2.size()) {
                Integer numValueOf = Integer.valueOf(i2);
                numValueOf = numValueOf.intValue() == 0 ? null : numValueOf;
                setFontDefaultWeight(numValueOf != null ? numValueOf.intValue() : ((Number) list2.get(iFindIndex)).intValue());
            }
        }
        styleSelectorHorizontalView.scrollToPositionWithOffset(drawableSelectorAdapter.getSelectedIndex(), getContext().getResources().getDimensionPixelOffset(getItemTopAndBottomOffsetResId()));
        viewFindViewById4.setVisibility(z ? 0 : 8);
        initListener();
    }

    private final int findIndex(List list, int i) {
        int iIndexOf = list.indexOf(Integer.valueOf(i));
        if (iIndexOf == -1) {
            return 0;
        }
        return iIndexOf;
    }

    private final int getItemTopAndBottomOffsetResId() {
        return R.dimen.kg_classic_text_editor_top_and_bottom_offset;
    }

    private final void initListener() {
        this.seekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() { // from class: com.miui.keyguard.editor.edit.base.FontEditor.initListener.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean z) {
                FontEditor.this.getEditCallback().onEdited(com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle, Integer.valueOf(FontEditor.this.getFontWeight(i)));
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
            }
        });
    }

    public final View getContainer() {
        return this.container;
    }

    public final int getFontWeight(int i) {
        return ((this.weightRange * i) / 100) + 200;
    }

    @Override // com.miui.keyguard.editor.edit.base.AbstractPopupEditor
    public void onTemplateRemoved() {
        super.onTemplateRemoved();
        this.selectorView.setAdapter(null);
    }

    public final void select(int i) {
        this.adapter.setSelectedIndex(i);
        this.adapter.notifyItemChanged(i);
    }

    public final void setFontDefaultWeight(int i) {
        this.seekBar.setMiddleEnabled(true);
        SeekBar seekBar = this.seekBar;
        if (seekBar != null) {
            seekBar.setProgress(((i - 200) * 100) / this.weightRange);
        }
    }
}
