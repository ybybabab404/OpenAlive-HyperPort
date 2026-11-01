package com.miui.keyguard.editor.edit.style;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.edit.view.StyleSelectorAdapter;
import com.miui.keyguard.editor.edit.view.decoration.SingleLineEditorItemDecoration;
import com.miui.keyguard.editor.homepage.view.adapter.EditorDialogViewPagerAdapter;
import com.miui.keyguard.editor.view.ClockTemplatePagerView;
import com.miui.keyguard.editor.view.ColorModificationTarget;
import com.miui.keyguard.editor.view.FontFilterView;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import java.util.TimeZone;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.pickerwidget.date.Calendar;
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
/* JADX INFO: compiled from: ViewStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class ViewStyleEditor extends AbstractPopupEditor implements RealtimeStyleEditor {
    public final boolean addFontFilterViewToWrapper;
    public BaseClockView baseTemplateView;
    public ColorModificationTarget colorModificationTarget;
    public final EditorDialogTitleView container;
    public final StyleSelectorAdapter drawableAdapter;
    public final LinearLayout editorFontContainer;
    public final View editorPagerContentWrapper;
    public final ViewPager2 editorViewPager;
    public final EditorDialogViewPagerAdapter editorViewPagerAdapter;
    public final Calendar miuiXCalendar;
    public PagerContentController pagerContentController;
    public final RecyclerView selectorView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewStyleEditor(int i, BaseClockView templateView, EditCallback editCallback, boolean z, FontStyleEditCallback fontStyleEditCallback) {
        super(templateView, editCallback, fontStyleEditCallback);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        this.addFontFilterViewToWrapper = z;
        this.baseTemplateView = templateView;
        this.miuiXCalendar = new Calendar(TimeZone.getDefault());
        View viewInflate = LayoutInflater.from(templateView.getContext()).inflate(R.layout.kg_editor_dialog_font_scroll_view, (ViewGroup) templateView, false);
        Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type android.view.View");
        this.editorPagerContentWrapper = viewInflate;
        this.pagerContentController = new PagerContentController() { // from class: com.miui.keyguard.editor.edit.style.ViewStyleEditor$pagerContentController$1
            @Override // com.miui.keyguard.editor.edit.style.PagerContentController
            public View loadClockTemplateEditorContent() {
                return this.this$0.loadClockTemplateEditorContentView();
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
        EditorDialogTitleView editorDialogTitleViewCreateViewByLayoutId = createViewByLayoutId();
        this.container = editorDialogTitleViewCreateViewByLayoutId;
        ViewPager2 viewPager2 = (ViewPager2) editorDialogTitleViewCreateViewByLayoutId.findViewById(R.id.editor_dialog_viewpager);
        this.editorViewPager = viewPager2;
        RecyclerView recyclerView = (RecyclerView) viewInflate.findViewById(R.id.drawable_selector_view);
        this.selectorView = recyclerView;
        this.editorFontContainer = (LinearLayout) viewInflate.findViewById(R.id.drawable_font_container);
        View viewFindViewById = viewInflate.findViewById(R.id.drawable_scroll_container);
        if (viewFindViewById != null) {
            viewFindViewById.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.keyguard.editor.edit.style.ViewStyleEditor$$ExternalSyntheticLambda0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return ViewStyleEditor._init_$lambda$0(view, motionEvent);
                }
            });
        }
        recyclerView.addItemDecoration(getItemDecoration());
        recyclerView.setLayoutManager(getLayoutManager());
        StyleSelectorAdapter styleSelectorAdapterCreateDrawableAdapter = createDrawableAdapter(i);
        this.drawableAdapter = styleSelectorAdapterCreateDrawableAdapter;
        if (styleSelectorAdapterCreateDrawableAdapter != null) {
            recyclerView.setAdapter(styleSelectorAdapterCreateDrawableAdapter);
            recyclerView.scrollToPosition(styleSelectorAdapterCreateDrawableAdapter.getSelectedIndex());
        }
        EditorDialogViewPagerAdapter editorDialogViewPagerAdapterCreateEditorDialogViewPagerAdapter = createEditorDialogViewPagerAdapter();
        this.editorViewPagerAdapter = editorDialogViewPagerAdapterCreateEditorDialogViewPagerAdapter;
        viewPager2.setAdapter(editorDialogViewPagerAdapterCreateEditorDialogViewPagerAdapter);
        viewPager2.setUserInputEnabled(false);
        viewPager2.setImportantForAccessibility(2);
        editorDialogTitleViewCreateViewByLayoutId.setViewPager(viewPager2, 1);
        this.colorModificationTarget = getColorModificationTarget();
        initFontFilterView();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.miui.keyguard.editor.edit.style.ViewStyleEditor.3
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i2) {
                super.onPageSelected(i2);
                if (i2 == 1 && ViewStyleEditor.this.getBaseTemplateView().isClockTemplateChanged()) {
                    ViewStyleEditor.this.getBaseTemplateView().setClockTemplateChanged(false);
                    ViewStyleEditor.this.editorViewPagerAdapter.notifyItemChanged(i2);
                }
            }
        });
    }

    public /* synthetic */ ViewStyleEditor(int i, BaseClockView baseClockView, EditCallback editCallback, boolean z, FontStyleEditCallback fontStyleEditCallback, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, baseClockView, editCallback, (i2 & 8) != 0 ? true : z, (i2 & 16) != 0 ? null : fontStyleEditCallback);
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
        return new EditorDialogViewPagerAdapter(true, getPagerContentController());
    }

    private final void initFontFilterView() {
        createFontEditorContent();
    }

    public StyleSelectorAdapter createDrawableAdapter(int i) {
        setSelectorViewVisible(0);
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.base.AbstractPopupEditor
    public View createFontEditorContent() {
        if (getFontFilterView() == null) {
            FontFilterView fontFilterView = new FontFilterView(getContext());
            BaseClockView baseClockView = this.baseTemplateView;
            if (baseClockView != null) {
                EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(baseClockView.getEditFragment());
                if (viewModel != null) {
                    fontFilterView.setFontFilterData(baseClockView.getFontFilter(), baseClockView.getFontFilterColorSelectInfo(), baseClockView.getMiuiClockViewSafeHolder(), baseClockView.isEnableAlphaTitle(), baseClockView.isGlassDisabled(), baseClockView.getLastStyleGlassTransparency(), viewModel, this.colorModificationTarget);
                }
                fontFilterView.setCustomColorBottomSheet(baseClockView.getCustomFontBottomSheet());
            }
            setFontFilterView(fontFilterView);
            if (this.addFontFilterViewToWrapper) {
                this.editorFontContainer.addView(getFontFilterView());
            }
        }
        return this.editorPagerContentWrapper;
    }

    public EditorDialogTitleView createViewByLayoutId() {
        View viewInflate = LayoutInflater.from(getTemplateView().getContext()).inflate(R.layout.kg_layout_drawable_editor, (ViewGroup) getTemplateView(), false);
        Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.view.EditorDialogTitleView");
        return (EditorDialogTitleView) viewInflate;
    }

    @Override // com.miui.keyguard.editor.edit.base.AbstractPopupEditor
    public EditorDialogTitleView editorViewContainer() {
        return this.container;
    }

    public final BaseClockView getBaseTemplateView() {
        return this.baseTemplateView;
    }

    public ColorModificationTarget getColorModificationTarget() {
        return ColorModificationTarget.BOTH_PARTS;
    }

    public final EditorDialogTitleView getContainer() {
        return this.container;
    }

    public final StyleSelectorAdapter getDrawableAdapter() {
        return this.drawableAdapter;
    }

    public final LinearLayout getEditorFontContainer() {
        return this.editorFontContainer;
    }

    public final androidx.recyclerview.widget.RecyclerView.ItemDecoration getItemDecoration() {
        return new SingleLineEditorItemDecoration(0, null, 3, null);
    }

    public androidx.recyclerview.widget.RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext(), 0, false);
    }

    public final Calendar getMiuiXCalendar() {
        return this.miuiXCalendar;
    }

    public PagerContentController getPagerContentController() {
        return this.pagerContentController;
    }

    public ClockTemplatePagerView loadClockTemplateEditorContentView() {
        ClockInfo clockInfo;
        ClockInfo clockInfo2;
        ClockInfo clockInfo3;
        Context context = getContext();
        EditCallback editCallback = getEditCallback();
        TemplateConfig templateConfig = this.baseTemplateView.templateConfig();
        Integer numValueOf = null;
        String templateId = (templateConfig == null || (clockInfo3 = templateConfig.getClockInfo()) == null) ? null : clockInfo3.getTemplateId();
        TemplateConfig templateConfig2 = this.baseTemplateView.templateConfig();
        Integer numValueOf2 = (templateConfig2 == null || (clockInfo2 = templateConfig2.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo2.getStyle());
        TemplateConfig templateConfig3 = this.baseTemplateView.templateConfig();
        if (templateConfig3 != null && (clockInfo = templateConfig3.getClockInfo()) != null) {
            numValueOf = Integer.valueOf(clockInfo.getClassicLine2());
        }
        return new ClockTemplatePagerView(context, editCallback, null, 0, templateId, numValueOf2, numValueOf, 12, null);
    }

    @Override // com.miui.keyguard.editor.edit.base.AbstractPopupEditor
    public void onTemplateRemoved() {
        super.onTemplateRemoved();
        this.selectorView.setAdapter(null);
    }

    public void onTimeUpdate() {
        StyleSelectorAdapter styleSelectorAdapter = this.drawableAdapter;
        if (styleSelectorAdapter != null) {
            styleSelectorAdapter.notifyDataSetChanged();
        }
    }

    public final void setBaseTemplateView(BaseClockView baseClockView) {
        Intrinsics.checkNotNullParameter(baseClockView, "<set-?>");
        this.baseTemplateView = baseClockView;
    }

    public final void setEditorViewPagerHeight(int i, boolean z) {
        FontFilterView fontFilterView;
        ViewGroup.LayoutParams layoutParams = this.container.getLayoutParams();
        layoutParams.height = i;
        this.container.setLayoutParams(layoutParams);
        if (!z || (fontFilterView = getFontFilterView()) == null) {
            return;
        }
        fontFilterView.setHslPagerHeight(i);
    }

    public final void setSelectorViewVisible(int i) {
        this.selectorView.setVisibility(i);
    }
}
