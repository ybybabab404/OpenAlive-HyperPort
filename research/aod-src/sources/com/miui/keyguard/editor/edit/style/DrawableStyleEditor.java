package com.miui.keyguard.editor.edit.style;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.preset.FilterTypeSelectInfo;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.edit.view.decoration.SingleLineEditorItemDecoration;
import com.miui.keyguard.editor.homepage.view.adapter.EditorDialogViewPagerAdapter;
import com.miui.keyguard.editor.view.ClockTemplatePagerView;
import com.miui.keyguard.editor.view.ColorModificationTarget;
import com.miui.keyguard.editor.view.FontFilterView;
import com.miui.keyguard.editor.view.MiuiClockViewSafeHolder;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.recyclerview.widget.RecyclerView;

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
/* JADX INFO: compiled from: DrawableStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class DrawableStyleEditor extends AbstractPopupEditor {
    public BaseClockView baseTemplateView;
    public final ColorModificationTarget colorModificationTarget;
    public final EditorDialogTitleView container;
    public final LinearLayout editorFontContainer;
    public final View editorPagerContentWrapper;
    public final ViewPager2 editorViewPager;
    public final EditorDialogViewPagerAdapter editorViewPagerAdapter;
    public PagerContentController pagerContentController;
    public final RecyclerView selectorView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DrawableStyleEditor(BaseClockView templateView, final EditCallback editCallback, List drawableList, List styleList, int i, FontStyleEditCallback fontStyleEditCallback) {
        super(templateView, editCallback, fontStyleEditCallback);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        Intrinsics.checkNotNullParameter(drawableList, "drawableList");
        Intrinsics.checkNotNullParameter(styleList, "styleList");
        View viewInflate = LayoutInflater.from(templateView.getContext()).inflate(R.layout.kg_layout_drawable_editor, (ViewGroup) templateView, false);
        Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.view.EditorDialogTitleView");
        EditorDialogTitleView editorDialogTitleView = (EditorDialogTitleView) viewInflate;
        this.container = editorDialogTitleView;
        this.baseTemplateView = templateView;
        this.colorModificationTarget = ColorModificationTarget.BOTH_PARTS;
        View viewInflate2 = LayoutInflater.from(templateView.getContext()).inflate(R.layout.kg_editor_dialog_font_scroll_view, (ViewGroup) templateView, false);
        Intrinsics.checkNotNull(viewInflate2, "null cannot be cast to non-null type android.view.View");
        this.editorPagerContentWrapper = viewInflate2;
        this.pagerContentController = new PagerContentController() { // from class: com.miui.keyguard.editor.edit.style.DrawableStyleEditor$pagerContentController$1
            @Override // com.miui.keyguard.editor.edit.style.PagerContentController
            public View loadClockTemplateEditorContent() {
                ClockInfo clockInfo;
                ClockInfo clockInfo2;
                Context context = this.this$0.getContext();
                EditCallback editCallback2 = editCallback;
                TemplateConfig templateConfig = this.this$0.getBaseTemplateView().templateConfig();
                Integer numValueOf = null;
                String templateId = (templateConfig == null || (clockInfo2 = templateConfig.getClockInfo()) == null) ? null : clockInfo2.getTemplateId();
                TemplateConfig templateConfig2 = this.this$0.getBaseTemplateView().templateConfig();
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
        int iIndexOf = styleList.indexOf(Integer.valueOf(i));
        ViewPager2 viewPager2 = (ViewPager2) editorDialogTitleView.findViewById(R.id.editor_dialog_viewpager);
        this.editorViewPager = viewPager2;
        this.editorFontContainer = (LinearLayout) viewInflate2.findViewById(R.id.drawable_font_container);
        RecyclerView recyclerView = (RecyclerView) viewInflate2.findViewById(R.id.drawable_selector_view);
        this.selectorView = recyclerView;
        View viewFindViewById = viewInflate2.findViewById(R.id.drawable_scroll_container);
        if (viewFindViewById != null) {
            viewFindViewById.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.keyguard.editor.edit.style.DrawableStyleEditor$$ExternalSyntheticLambda0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return DrawableStyleEditor._init_$lambda$0(view, motionEvent);
                }
            });
        }
        recyclerView.addItemDecoration(new SingleLineEditorItemDecoration(0, null, 3, null));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new DrawableSelectorAdapter(iIndexOf, drawableList, this) { // from class: com.miui.keyguard.editor.edit.style.DrawableStyleEditor.3
            public final /* synthetic */ DrawableStyleEditor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(drawableList);
                this.this$0 = this;
                setSelectedIndex(iIndexOf);
            }

            @Override // com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter, com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public int itemLayoutId() {
                return R.layout.kg_layout_drawable_selector_item;
            }

            @Override // com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter, com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onBind(View view, int i2) {
                super.onBind(view, i2);
                if (view != null) {
                    this.this$0.onItemBind(view, i2);
                }
            }

            @Override // com.miui.keyguard.editor.edit.view.DrawableSelectorAdapter
            public void onBindDrawable(ImageView imageView) {
                Intrinsics.checkNotNullParameter(imageView, "imageView");
                super.onBindDrawable(imageView);
                this.this$0.onDrawableBind(imageView);
            }

            @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
            public void onItemSelect(View view, int i2) {
                super.onItemSelect(view, i2);
                this.this$0.onItemSelected(i2);
            }
        });
        recyclerView.scrollToPosition(Math.max(iIndexOf - 1, 0));
        String string = getContext().getString(R.string.kg_title_clock_name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        EditorDialogTitleView.setTitle$default(editorDialogTitleView, string, null, 0, 6, null);
        EditorDialogViewPagerAdapter editorDialogViewPagerAdapterCreateEditorDialogViewPagerAdapter = createEditorDialogViewPagerAdapter();
        this.editorViewPagerAdapter = editorDialogViewPagerAdapterCreateEditorDialogViewPagerAdapter;
        viewPager2.setAdapter(editorDialogViewPagerAdapterCreateEditorDialogViewPagerAdapter);
        viewPager2.setUserInputEnabled(false);
        editorDialogTitleView.setViewPager(viewPager2, 1);
        initFontFilterView();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.miui.keyguard.editor.edit.style.DrawableStyleEditor.4
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i2) {
                super.onPageSelected(i2);
                if (i2 == 1 && DrawableStyleEditor.this.getBaseTemplateView().isClockTemplateChanged()) {
                    DrawableStyleEditor.this.getBaseTemplateView().setClockTemplateChanged(false);
                    DrawableStyleEditor.this.editorViewPagerAdapter.notifyItemChanged(i2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
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

    private final EditorDialogViewPagerAdapter createEditorDialogViewPagerAdapter() {
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
            fontFilterView.setFontFilterData(fontFilter, fontFilterColorSelectInfo, miuiClockViewSafeHolder, zIsEnableAlphaTitle, zIsGlassDisabled, lastStyleGlassTransparency, viewModel, this.colorModificationTarget);
            fontFilterView.setCustomColorBottomSheet(this.baseTemplateView.getCustomFontBottomSheet());
            setFontFilterView(fontFilterView);
            this.editorFontContainer.addView(getFontFilterView());
        }
        return this.editorPagerContentWrapper;
    }

    @Override // com.miui.keyguard.editor.edit.base.AbstractPopupEditor
    public EditorDialogTitleView editorViewContainer() {
        return this.container;
    }

    public final BaseClockView getBaseTemplateView() {
        return this.baseTemplateView;
    }

    public final View getEditorPagerContentWrapper() {
        return this.editorPagerContentWrapper;
    }

    public final void initFontFilterView() {
        createFontEditorContent();
    }

    public void onDrawableBind(ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "imageView");
    }

    public void onItemBind(View itemView, int i) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
    }

    public abstract void onItemSelected(int i);

    @Override // com.miui.keyguard.editor.edit.base.AbstractPopupEditor
    public void onTemplateRemoved() {
        super.onTemplateRemoved();
        this.selectorView.setAdapter(null);
    }

    public final void setDrawableSelectorViewGone() {
        this.selectorView.setVisibility(8);
    }
}
