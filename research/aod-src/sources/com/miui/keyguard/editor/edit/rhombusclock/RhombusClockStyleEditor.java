package com.miui.keyguard.editor.edit.rhombusclock;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.preset.FilterTypeSelectInfo;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.style.FontStyleEditCallback;
import com.miui.keyguard.editor.edit.style.PagerContentController;
import com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.edit.view.decoration.SingleLineEditorItemDecoration;
import com.miui.keyguard.editor.homepage.view.adapter.EditorDialogViewPagerAdapter;
import com.miui.keyguard.editor.view.ClockTemplatePagerView;
import com.miui.keyguard.editor.view.ColorModificationTarget;
import com.miui.keyguard.editor.view.FilterIconLayout;
import com.miui.keyguard.editor.view.FontFilterView;
import com.miui.keyguard.editor.view.MiuiClockViewSafeHolder;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
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
/* JADX INFO: compiled from: RhombusClockStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class RhombusClockStyleEditor extends AbstractPopupEditor {
    public static final Companion Companion = new Companion(null);
    public static final List RHOMBUS_STYLE_DRAWABLES = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.drawable.kg_rhombus_clock_style0), Integer.valueOf(R.drawable.kg_rhombus_clock_style1), Integer.valueOf(R.drawable.kg_rhombus_clock_style2), Integer.valueOf(R.drawable.kg_rhombus_clock_style3), Integer.valueOf(R.drawable.kg_rhombus_clock_style4), Integer.valueOf(R.drawable.kg_rhombus_clock_style5), Integer.valueOf(R.drawable.kg_rhombus_clock_style6), Integer.valueOf(R.drawable.kg_rhombus_clock_style7), Integer.valueOf(R.drawable.kg_rhombus_clock_style8), Integer.valueOf(R.drawable.kg_rhombus_clock_style9)});
    public static final List RHOMBUS_STYLE_INDEX = CollectionsKt.listOf((Object[]) new Integer[]{1, 3, 10, 4, 2, 5, 9, 7, 8, 6});
    public static final List RHOMBUS_STYLE_NAMES = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.string.kg_oversize_clock_type_serif_font), Integer.valueOf(R.string.kg_classic_font_type_misans_round), Integer.valueOf(R.string.kg_rhombus_clock_type_flower_font), Integer.valueOf(R.string.kg_rhombus_clock_type_bold_serif_font), Integer.valueOf(R.string.kg_rhombus_clock_type_bubble_font), Integer.valueOf(R.string.kg_rhombus_clock_type_square_font), Integer.valueOf(R.string.kg_rhombus_clock_type_brush_font), Integer.valueOf(R.string.kg_rhombus_clock_type_modern_style_serif_font), Integer.valueOf(R.string.kg_rhombus_clock_type_narrow_sans_serif_font), Integer.valueOf(R.string.kg_rhombus_clock_type_mechanical_font)});
    public final RhombusClockStyleEditor$adapter$1 adapter;
    public BaseClockView baseTemplateView;
    public final EditorDialogTitleView container;
    public final ViewPager2 editorDialogViewPager;
    public EditorDialogViewPagerAdapter editorDialogViewPagerAdapter;
    public final LinearLayout editorFontContainer;
    public final View fontEditorContent;
    public PagerContentController pagerContentController;
    public RecyclerView selectorView;

    /* JADX INFO: compiled from: RhombusClockStyleEditor.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final List getRHOMBUS_STYLE_DRAWABLES() {
            return RhombusClockStyleEditor.RHOMBUS_STYLE_DRAWABLES;
        }

        public final List getRHOMBUS_STYLE_INDEX() {
            return RhombusClockStyleEditor.RHOMBUS_STYLE_INDEX;
        }

        public final List getRHOMBUS_STYLE_NAMES() {
            return RhombusClockStyleEditor.RHOMBUS_STYLE_NAMES;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.miui.keyguard.editor.edit.rhombusclock.RhombusClockStyleEditor$adapter$1] */
    public RhombusClockStyleEditor(BaseClockView templateView, final EditCallback editCallback, FontStyleEditCallback fontStyleEditCallback) {
        super(templateView, editCallback, fontStyleEditCallback);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.kg_layout_drawable_editor, (ViewGroup) templateView, false);
        Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.view.EditorDialogTitleView");
        EditorDialogTitleView editorDialogTitleView = (EditorDialogTitleView) viewInflate;
        this.container = editorDialogTitleView;
        View viewInflate2 = LayoutInflater.from(templateView.getContext()).inflate(R.layout.kg_editor_dialog_font_scroll_view, (ViewGroup) templateView, false);
        Intrinsics.checkNotNull(viewInflate2, "null cannot be cast to non-null type android.view.View");
        this.fontEditorContent = viewInflate2;
        this.baseTemplateView = templateView;
        this.pagerContentController = new PagerContentController() { // from class: com.miui.keyguard.editor.edit.rhombusclock.RhombusClockStyleEditor$pagerContentController$1
            @Override // com.miui.keyguard.editor.edit.style.PagerContentController
            public View loadClockTemplateEditorContent() {
                ClockInfo clockInfo;
                ClockInfo clockInfo2;
                Context context = this.this$0.getContext();
                EditCallback editCallback2 = editCallback;
                TemplateConfig templateConfig = this.this$0.baseTemplateView.templateConfig();
                Integer numValueOf = null;
                String templateId = (templateConfig == null || (clockInfo2 = templateConfig.getClockInfo()) == null) ? null : clockInfo2.getTemplateId();
                TemplateConfig templateConfig2 = this.this$0.baseTemplateView.templateConfig();
                if (templateConfig2 != null && (clockInfo = templateConfig2.getClockInfo()) != null) {
                    numValueOf = Integer.valueOf(clockInfo.getStyle());
                }
                return new ClockTemplatePagerView(context, editCallback2, null, 0, templateId, numValueOf, null, 76, null);
            }

            @Override // com.miui.keyguard.editor.edit.style.PagerContentController
            public View loadFontEditorContent() {
                return this.this$0.loadFontEditorContentView();
            }

            @Override // com.miui.keyguard.editor.edit.style.PagerContentController
            public View loadSignatureEditorContent() {
                return PagerContentController.DefaultImpls.loadSignatureEditorContent(this);
            }
        };
        final List rhombus_style_drawables = Companion.getRHOMBUS_STYLE_DRAWABLES();
        this.adapter = new DrawableSelectorAdapter(rhombus_style_drawables) { // from class: com.miui.keyguard.editor.edit.rhombusclock.RhombusClockStyleEditor$adapter$1
            @Override // com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter, com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onBind(View view, int i) {
                super.onBind(view, i);
                if (view != null) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.drawable_selector_item_icon);
                    if (imageView != null) {
                        imageView.setImportantForAccessibility(4);
                    }
                    view.getLayoutParams().width = view.getContext().getResources().getDimensionPixelSize(R.dimen.kg_rhombus_clock_font_style_with);
                    view.getLayoutParams().height = view.getContext().getResources().getDimensionPixelSize(R.dimen.kg_rhombus_clock_font_style_height);
                    ((FilterIconLayout) view).setRadius(view.getContext().getResources().getDimensionPixelSize(R.dimen.kg_rhombus_clock_style_frame_radius));
                    String string = view.getContext().getString(((Number) RhombusClockStyleEditor.Companion.getRHOMBUS_STYLE_NAMES().get(i)).intValue());
                    view.setContentDescription((i == getSelectedIndex() ? view.getContext().getString(R.string.kg_talkback_selected) : view.getContext().getString(R.string.kg_talkback_not_selected)) + ", " + string);
                }
            }

            @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onItemSelect(View view, int i) {
                super.onItemSelect(view, i);
                notifyDataSetChanged();
                editCallback.onEdited(com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, RhombusClockStyleEditor.Companion.getRHOMBUS_STYLE_INDEX().get(i));
                Log.i("RhombusClockStyleEditor", "onItemSelect " + i);
            }
        };
        this.baseTemplateView = templateView;
        this.selectorView = (RecyclerView) viewInflate2.findViewById(R.id.drawable_selector_view);
        this.editorFontContainer = (LinearLayout) viewInflate2.findViewById(R.id.drawable_font_container);
        View viewFindViewById = viewInflate2.findViewById(R.id.drawable_scroll_container);
        if (viewFindViewById != null) {
            viewFindViewById.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.keyguard.editor.edit.rhombusclock.RhombusClockStyleEditor$$ExternalSyntheticLambda0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return RhombusClockStyleEditor._init_$lambda$0(view, motionEvent);
                }
            });
        }
        ViewPager2 viewPager2 = (ViewPager2) editorDialogTitleView.findViewById(R.id.editor_dialog_viewpager);
        this.editorDialogViewPager = viewPager2;
        EditorDialogViewPagerAdapter editorDialogViewPagerAdapterCreateEditorDialogViewPagerAdapter = createEditorDialogViewPagerAdapter();
        this.editorDialogViewPagerAdapter = editorDialogViewPagerAdapterCreateEditorDialogViewPagerAdapter;
        viewPager2.setAdapter(editorDialogViewPagerAdapterCreateEditorDialogViewPagerAdapter);
        viewPager2.setUserInputEnabled(false);
        editorDialogTitleView.setViewPager(viewPager2, 1);
        setSelectorViewVisible(0);
        initViews();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.miui.keyguard.editor.edit.rhombusclock.RhombusClockStyleEditor.2
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i) {
                super.onPageSelected(i);
                if (i == 1 && RhombusClockStyleEditor.this.baseTemplateView.isClockTemplateChanged()) {
                    RhombusClockStyleEditor.this.baseTemplateView.setClockTemplateChanged(false);
                    EditorDialogViewPagerAdapter editorDialogViewPagerAdapter = RhombusClockStyleEditor.this.editorDialogViewPagerAdapter;
                    if (editorDialogViewPagerAdapter != null) {
                        editorDialogViewPagerAdapter.notifyItemChanged(i);
                    }
                }
            }
        });
    }

    public static final boolean _init_$lambda$0(View view, MotionEvent motionEvent) {
        ViewParent parent;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            ViewParent parent2 = view.getParent();
            if (parent2 != null) {
                parent2.requestDisallowInterceptTouchEvent(true);
            }
        } else if ((actionMasked == 1 || actionMasked == 3) && (parent = view.getParent()) != null) {
            parent.requestDisallowInterceptTouchEvent(false);
        }
        return false;
    }

    private final void setSelectorViewVisible(int i) {
        this.selectorView.setVisibility(i);
    }

    public final EditorDialogViewPagerAdapter createEditorDialogViewPagerAdapter() {
        return new EditorDialogViewPagerAdapter(true, this.pagerContentController);
    }

    @Override // com.miui.keyguard.editor.edit.base.AbstractPopupEditor
    public View createFontEditorContent() {
        if (getFontFilterView() == null) {
            FontFilterView fontFilterView = new FontFilterView(getContext());
            FontFilter fontFilter = this.baseTemplateView.getFontFilter();
            FilterTypeSelectInfo[] fontFilterColorSelectInfo = this.baseTemplateView.getFontFilterColorSelectInfo();
            MiuiClockViewSafeHolder miuiClockViewSafeHolder = this.baseTemplateView.getMiuiClockViewSafeHolder();
            boolean zIsEnableAlphaTitle = this.baseTemplateView.isEnableAlphaTitle();
            boolean zIsGlassDisabled = this.baseTemplateView.isGlassDisabled();
            float lastStyleGlassTransparency = this.baseTemplateView.getLastStyleGlassTransparency();
            EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.baseTemplateView.getEditFragment());
            Intrinsics.checkNotNull(viewModel);
            fontFilterView.setFontFilterData(fontFilter, fontFilterColorSelectInfo, miuiClockViewSafeHolder, zIsEnableAlphaTitle, zIsGlassDisabled, lastStyleGlassTransparency, viewModel, ColorModificationTarget.BOTH_PARTS);
            fontFilterView.setCustomColorBottomSheet(this.baseTemplateView.getCustomFontBottomSheet());
            setFontFilterView(fontFilterView);
            this.editorFontContainer.addView(getFontFilterView());
        }
        return this.fontEditorContent;
    }

    @Override // com.miui.keyguard.editor.edit.base.AbstractPopupEditor
    public EditorDialogTitleView editorViewContainer() {
        return this.container;
    }

    public final void initViews() {
        EditorDialogTitleView editorDialogTitleView = this.container;
        String string = getContext().getString(R.string.kg_title_clock_name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        EditorDialogTitleView.setTitle$default(editorDialogTitleView, string, getContext().getString(R.string.kg_title_font_name), 0, 4, null);
        RecyclerView recyclerView = this.selectorView;
        recyclerView.addItemDecoration(new SingleLineEditorItemDecoration(0, null, 3, null));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(this.adapter);
    }

    @Override // com.miui.keyguard.editor.edit.base.AbstractPopupEditor
    public void onTemplateRemoved() {
        super.onTemplateRemoved();
        RecyclerView recyclerView = this.selectorView;
        if (recyclerView != null) {
            recyclerView.setAdapter(null);
        }
    }

    public final void scrollToPositionWithOffset(int i, int i2) {
        RecyclerView recyclerView = this.selectorView;
        RecyclerView.LayoutManager layoutManager = recyclerView != null ? recyclerView.getLayoutManager() : null;
        LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(i, i2);
        }
    }

    public final void select(int i) {
        setSelectedIndex(RHOMBUS_STYLE_INDEX.indexOf(Integer.valueOf(i)));
        scrollToPositionWithOffset(getSelectedIndex(), this.container.getResources().getDimensionPixelSize(R.dimen.kg_editor_first_and_last_item_offset));
    }
}
