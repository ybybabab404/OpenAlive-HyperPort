package com.miui.keyguard.editor.edit.allinone;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.style.FontStyleEditCallback;
import com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter;
import com.miui.keyguard.editor.edit.view.StyleSelectorHorizontalView;
import com.miui.keyguard.editor.utils.ToastUtil;
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
/* JADX INFO: compiled from: AllInOneFontEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class AllInOneFontEditor extends AbstractPopupEditor {
    public final View container;
    public int currentFontWeight;
    public final DrawableSelectorAdapter fontAdapter;
    public final DrawableSelectorAdapter hollowAdapter;
    public final StyleSelectorHorizontalView hollowSelectorView;
    public final View hollowStyleContainer;
    public List hollowStyleDrawables;
    public boolean isDarkMode;
    public int restoreHollowIndexAfterFontBack;
    public int savedPreHollowWeight;
    public final SeekBar seekBar;
    public int selectedFontStyle;
    public final View selectorContainer;
    public final StyleSelectorHorizontalView selectorView;
    public final int weightRange;
    public static final Companion Companion = new Companion(null);
    public static final int FONT_WEIGHT_LEAST = 220;
    public static final int FONT_WEIGHT_MAX = TextStyleCategoryInfo.MAX_FONT_WEIGHT;
    public static final List ALL_IN_ONE_FONT_STYLES = CollectionsKt.listOf((Object[]) new Integer[]{21, 22, 23, 24, 25});
    public static final List ALL_IN_ONE_FONT_STYLE_DRAWABLES = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.drawable.kg_font_mi_sans_a), Integer.valueOf(R.drawable.kg_font_mi_sans_b), Integer.valueOf(R.drawable.kg_font_mi_sans_c), Integer.valueOf(R.drawable.kg_font_mi_sans_d), Integer.valueOf(R.drawable.kg_font_mi_sans_e)});
    public static final List ALL_IN_ONE_FONT_STYLE_NAMES = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.string.kg_allinone_font_type_MiSans_RC), Integer.valueOf(R.string.kg_allinone_font_type_MiSans_Rounded), Integer.valueOf(R.string.kg_allinone_font_type_MiSerif), Integer.valueOf(R.string.kg_allinone_font_type_MiSans_Square), Integer.valueOf(R.string.kg_allinone_font_type_MiHaus)});
    public static final List ALL_IN_ONE_FONT_HOLLOW_STYLE_DRAWABLES_LIGHT = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.drawable.kg_allinone_hollow_preview_style_no_light), Integer.valueOf(R.drawable.kg_allinone_hollow_preview_style_hour_light), Integer.valueOf(R.drawable.kg_allinone_hollow_preview_style_minute_light), Integer.valueOf(R.drawable.kg_allinone_hollow_preview_style_all_light)});
    public static final List ALL_IN_ONE_FONT_HOLLOW_STYLE_DRAWABLES_DARK = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.drawable.kg_allinone_hollow_preview_style_no_dark), Integer.valueOf(R.drawable.kg_allinone_hollow_preview_style_hour_dark), Integer.valueOf(R.drawable.kg_allinone_hollow_preview_style_minute_dark), Integer.valueOf(R.drawable.kg_allinone_hollow_preview_style_all_dark)});
    public static final List ALL_IN_ONE_FONT_HOLLOW_STYLE_NAMES = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.string.kg_allinone_hollow_style_none), Integer.valueOf(R.string.kg_allinone_hollow_style_hour), Integer.valueOf(R.string.kg_allinone_hollow_style_minute), Integer.valueOf(R.string.kg_allinone_hollow_style_both)});
    public static final List ALL_IN_ONE_FONT_HOLLOW_STYLE = CollectionsKt.listOf((Object[]) new Integer[]{1, 2, 3, 4});

    /* JADX INFO: compiled from: AllInOneFontEditor.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AllInOneFontEditor(FrameLayout templateView, int i, final EditCallback editCallback, int i2, int i3, FontStyleEditCallback fontStyleEditCallback, View hollowStyleContainer) {
        super(templateView, editCallback, fontStyleEditCallback);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        Intrinsics.checkNotNullParameter(hollowStyleContainer, "hollowStyleContainer");
        this.selectedFontStyle = i;
        this.hollowStyleContainer = hollowStyleContainer;
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
        View viewFindViewById3 = hollowStyleContainer.findViewById(R.id.hollow_style_selector);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        StyleSelectorHorizontalView styleSelectorHorizontalView2 = (StyleSelectorHorizontalView) viewFindViewById3;
        this.hollowSelectorView = styleSelectorHorizontalView2;
        View viewFindViewById4 = viewInflate.findViewById(R.id.font_size_seekbar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
        this.seekBar = (SeekBar) viewFindViewById4;
        this.weightRange = FONT_WEIGHT_MAX - FONT_WEIGHT_LEAST;
        this.currentFontWeight = i2;
        boolean z = (getContext().getApplicationContext().getResources().getConfiguration().uiMode & 48) == 32;
        this.isDarkMode = z;
        this.hollowStyleDrawables = z ? ALL_IN_ONE_FONT_HOLLOW_STYLE_DRAWABLES_DARK : ALL_IN_ONE_FONT_HOLLOW_STYLE_DRAWABLES_LIGHT;
        this.restoreHollowIndexAfterFontBack = -1;
        this.savedPreHollowWeight = i2;
        final List list = ALL_IN_ONE_FONT_STYLE_DRAWABLES;
        DrawableSelectorAdapter drawableSelectorAdapter = new DrawableSelectorAdapter(list) { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneFontEditor$fontAdapter$1
            @Override // com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter, com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public int itemLayoutId() {
                return R.layout.kg_layout_drawable_selector_item_no_padding;
            }

            @Override // com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter, com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onBind(View view, int i4) {
                super.onBind(view, i4);
                ImageView imageView = view != null ? (ImageView) view.findViewById(R.id.drawable_selector_item_icon) : null;
                AllInOneFontEditor allInOneFontEditor = this.this$0;
                if (imageView != null) {
                    imageView.setContentDescription(allInOneFontEditor.getContext().getString(((Number) AllInOneFontEditor.ALL_IN_ONE_FONT_STYLE_NAMES.get(i4)).intValue()));
                }
                if (view != null) {
                    FilterIconLayout filterIconLayout = view instanceof FilterIconLayout ? (FilterIconLayout) view : null;
                    if (filterIconLayout != null) {
                        filterIconLayout.setRadius(view.getContext().getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_font_select_radius));
                    }
                }
            }

            @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onItemSelect(View view, int i4) {
                int i5 = this.this$0.selectedFontStyle;
                super.onItemSelect(view, i4);
                this.this$0.selectedFontStyle = ((Number) AllInOneFontEditor.ALL_IN_ONE_FONT_STYLES.get(i4)).intValue();
                Log.i("AllInOneFontEditor", "[FontSwitch] onItemSelect: oldFont=" + i5 + " -> newFont=" + this.this$0.selectedFontStyle + ", index=" + i4 + ", supportHollow=" + this.this$0.isFontStyleSupportHollow() + ", currentWeight=" + this.this$0.currentFontWeight + ", hollowIndex=" + this.this$0.hollowAdapter.getSelectedIndex());
                editCallback.onEdited(232, Integer.valueOf(this.this$0.selectedFontStyle));
                this.this$0.updateHollowStyleAvailability();
                if (!this.this$0.isFontStyleSupportHollow()) {
                    Log.i("AllInOneFontEditor", "[FontSwitch] font not support hollow, saving hollowIndex=" + this.this$0.hollowAdapter.getSelectedIndex() + ", reset to 0");
                    if (this.this$0.hollowAdapter.getSelectedIndex() > 0) {
                        AllInOneFontEditor allInOneFontEditor = this.this$0;
                        allInOneFontEditor.restoreHollowIndexAfterFontBack = allInOneFontEditor.hollowAdapter.getSelectedIndex();
                    }
                    editCallback.onEdited(231, AllInOneFontEditor.ALL_IN_ONE_FONT_HOLLOW_STYLE.get(0));
                    AllInOneFontEditor allInOneFontEditor2 = this.this$0;
                    allInOneFontEditor2.select(allInOneFontEditor2.hollowAdapter, 0);
                    return;
                }
                if (this.this$0.restoreHollowIndexAfterFontBack > 0) {
                    int i6 = this.this$0.restoreHollowIndexAfterFontBack;
                    this.this$0.restoreHollowIndexAfterFontBack = -1;
                    if (this.this$0.hollowAdapter.getSelectedIndex() != i6) {
                        Log.i("AllInOneFontEditor", "[FontSwitch] restoring hollow: current=" + this.this$0.hollowAdapter.getSelectedIndex() + " -> restore=" + i6);
                        editCallback.onEdited(231, AllInOneFontEditor.ALL_IN_ONE_FONT_HOLLOW_STYLE.get(i6));
                        AllInOneFontEditor allInOneFontEditor3 = this.this$0;
                        allInOneFontEditor3.select(allInOneFontEditor3.hollowAdapter, i6);
                        this.this$0.restoreMaxFontWeight(i6);
                    }
                }
            }
        };
        this.fontAdapter = drawableSelectorAdapter;
        final List list2 = this.hollowStyleDrawables;
        DrawableSelectorAdapter drawableSelectorAdapter2 = new DrawableSelectorAdapter(list2) { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneFontEditor$hollowAdapter$1
            @Override // com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter, com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public int itemLayoutId() {
                return R.layout.kg_layout_drawable_selector_item_hollow;
            }

            @Override // com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter, com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onBind(View view, int i4) {
                ImageView imageView;
                super.onBind(view, i4);
                if (view != null && (imageView = (ImageView) view.findViewById(R.id.drawable_selector_item_icon)) != null) {
                    imageView.setImportantForAccessibility(4);
                }
                if (view != null) {
                    FilterIconLayout filterIconLayout = view instanceof FilterIconLayout ? (FilterIconLayout) view : null;
                    if (filterIconLayout != null) {
                        filterIconLayout.setRadius(view.getContext().getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_font_select_radius));
                    }
                    String string = view.getContext().getString(((Number) AllInOneFontEditor.ALL_IN_ONE_FONT_HOLLOW_STYLE_NAMES.get(i4)).intValue());
                    view.setContentDescription((i4 == getSelectedIndex() ? view.getContext().getString(R.string.kg_talkback_selected) : view.getContext().getString(R.string.kg_talkback_not_selected)) + ", " + string);
                }
            }

            @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onItemSelect(View view, int i4) {
                if (!this.this$0.isFontStyleSupportHollow()) {
                    Log.d("AllInOneFontEditor", "[HollowSwitch] blocked: font=" + this.this$0.selectedFontStyle + " not support hollow");
                    ToastUtil.INSTANCE.showText(this.this$0.getContext(), R.string.kg_allinone_hollow_not_support_current_font);
                    return;
                }
                int selectedIndex = getSelectedIndex();
                if (selectedIndex == 0 && i4 > 0) {
                    AllInOneFontEditor allInOneFontEditor = this.this$0;
                    allInOneFontEditor.savedPreHollowWeight = allInOneFontEditor.currentFontWeight;
                    Log.i("AllInOneFontEditor", "[HollowSwitch] saving pre-hollow weight=" + this.this$0.savedPreHollowWeight);
                }
                super.onItemSelect(view, i4);
                notifyDataSetChanged();
                Log.i("AllInOneFontEditor", "[HollowSwitch] onItemSelect: oldIndex=" + selectedIndex + " -> newIndex=" + i4 + ", hollowStyle=" + AllInOneFontEditor.ALL_IN_ONE_FONT_HOLLOW_STYLE.get(i4) + ", font=" + this.this$0.selectedFontStyle + ", weight=" + this.this$0.currentFontWeight);
                editCallback.onEdited(231, AllInOneFontEditor.ALL_IN_ONE_FONT_HOLLOW_STYLE.get(i4));
                if (selectedIndex <= 0 || i4 != 0) {
                    this.this$0.restoreMaxFontWeight(i4);
                } else {
                    this.this$0.restorePreHollowWeight();
                }
            }
        };
        this.hollowAdapter = drawableSelectorAdapter2;
        int i4 = this.selectedFontStyle;
        Log.i("AllInOneFontEditor", "[Init] AllInOneFontEditor: fontStyle=" + i4 + ", weight=" + i2 + ", hollowStyle=" + i3 + ", supportHollow=" + (i4 != 23));
        styleSelectorHorizontalView.setAdapter(drawableSelectorAdapter);
        select(drawableSelectorAdapter, findIndex(ALL_IN_ONE_FONT_STYLES, this.selectedFontStyle));
        setFontDefaultWeight(i2);
        styleSelectorHorizontalView.scrollToPositionWithOffset(drawableSelectorAdapter.getSelectedIndex(), getContext().getResources().getDimensionPixelOffset(getItemTopAndBottomOffsetResId()));
        styleSelectorHorizontalView2.setAdapter(drawableSelectorAdapter2);
        select(drawableSelectorAdapter2, findIndex(ALL_IN_ONE_FONT_HOLLOW_STYLE, i3));
        styleSelectorHorizontalView2.scrollToPositionWithOffset(drawableSelectorAdapter2.getSelectedIndex(), getContext().getResources().getDimensionPixelOffset(getItemTopAndBottomOffsetResId()));
        hollowStyleContainer.setVisibility(0);
        updateHollowStyleAvailability();
        initListener();
        hollowStyleContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneFontEditor$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AllInOneFontEditor._init_$lambda$0(view);
            }
        });
    }

    public static final void _init_$lambda$0(View view) {
    }

    public final int findIndex(List list, int i) {
        int iIndexOf = list.indexOf(Integer.valueOf(i));
        if (iIndexOf == -1) {
            return 0;
        }
        return iIndexOf;
    }

    public final View getContainer() {
        return this.container;
    }

    public final int getFontWeight(int i) {
        return ((this.weightRange * i) / 100) + FONT_WEIGHT_LEAST;
    }

    public final int getItemTopAndBottomOffsetResId() {
        return R.dimen.kg_classic_text_editor_top_and_bottom_offset;
    }

    public final void initListener() {
        this.seekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneFontEditor.initListener.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean z) {
                int i2 = AllInOneFontEditor.this.currentFontWeight;
                int fontWeight = AllInOneFontEditor.this.getFontWeight(i);
                AllInOneFontEditor.this.getEditCallback().onEdited(com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle, Integer.valueOf(fontWeight));
                AllInOneFontEditor.this.currentFontWeight = fontWeight;
                AllInOneFontEditor.this.updateHollowStyleAvailability();
                if (z && AllInOneFontEditor.this.hollowAdapter.getSelectedIndex() == 0) {
                    AllInOneFontEditor.this.savedPreHollowWeight = fontWeight;
                }
                if (AllInOneFontEditor.this.isFontStyleSupportHollow() && AllInOneFontEditor.this.isMaxFontWeight()) {
                    return;
                }
                if (AllInOneFontEditor.this.hollowAdapter.getSelectedIndex() != 0) {
                    Log.i("AllInOneFontEditor", "[WeightChange] reset hollow: weight " + i2 + "->" + fontWeight + ", supportHollow=" + AllInOneFontEditor.this.isFontStyleSupportHollow() + ", isMax=" + AllInOneFontEditor.this.isMaxFontWeight());
                }
                AllInOneFontEditor.this.getEditCallback().onEdited(231, AllInOneFontEditor.ALL_IN_ONE_FONT_HOLLOW_STYLE.get(0));
                AllInOneFontEditor allInOneFontEditor = AllInOneFontEditor.this;
                allInOneFontEditor.select(allInOneFontEditor.hollowAdapter, 0);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
            }
        });
        SeekBar seekBar = this.seekBar;
        if (seekBar != null) {
            seekBar.setContentDescription(getContext().getString(R.string.kg_allinone_weight_title));
        }
    }

    public final boolean isFontStyleSupportHollow() {
        return this.selectedFontStyle != 23;
    }

    public final boolean isMaxFontWeight() {
        return this.currentFontWeight >= FONT_WEIGHT_MAX;
    }

    public final void restoreMaxFontWeight(int i) {
        SeekBar seekBar;
        if (i <= 0 || isMaxFontWeight() || (seekBar = this.seekBar) == null) {
            return;
        }
        seekBar.setProgress(100);
    }

    public final void restorePreHollowWeight() {
        int i = this.savedPreHollowWeight;
        Log.i("AllInOneFontEditor", "[HollowSwitch] restoring pre-hollow weight=" + i);
        SeekBar seekBar = this.seekBar;
        if (seekBar != null) {
            seekBar.setProgress(((i - FONT_WEIGHT_LEAST) * 100) / this.weightRange);
        }
    }

    public final void select(DrawableSelectorAdapter adapter, int i) {
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        adapter.setSelectedIndex(i);
        adapter.notifyDataSetChanged();
    }

    public final void setFontDefaultWeight(int i) {
        this.seekBar.setMiddleEnabled(true);
        SeekBar seekBar = this.seekBar;
        if (seekBar != null) {
            seekBar.setProgress(((i - FONT_WEIGHT_LEAST) * 100) / this.weightRange);
        }
        this.currentFontWeight = i;
        updateHollowStyleAvailability();
    }

    public final void updateHollowStyleAvailability() {
        this.hollowStyleContainer.setAlpha(isFontStyleSupportHollow() ? 1.0f : 0.4f);
    }
}
