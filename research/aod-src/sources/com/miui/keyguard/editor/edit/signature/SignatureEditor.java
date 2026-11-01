package com.miui.keyguard.editor.edit.signature;

import android.animation.ObjectAnimator;
import android.graphics.Insets;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.preset.FilterTypeSelectInfo;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.style.PagerContentController;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.edit.view.PopupLayerView;
import com.miui.keyguard.editor.homepage.util.CrossListViewUtil;
import com.miui.keyguard.editor.homepage.view.adapter.EditorDialogViewPagerAdapter;
import com.miui.keyguard.editor.view.AutoBottomSheet;
import com.miui.keyguard.editor.view.ColorModificationTarget;
import com.miui.keyguard.editor.view.FontFilterView;
import com.miui.keyguard.editor.view.MiuiClockViewSafeHolder;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.core.widget.NestedScrollView;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: SignatureEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class SignatureEditor extends AbstractPopupEditor implements View.OnApplyWindowInsetsListener, TextWatcher {
    public BaseClockView baseTemplateView;
    public ColorModificationTarget colorModificationTarget;
    public EditorDialogTitleView container;
    public final LinearLayout editorFontContent;
    public final View editorPagerContentWrapper;
    public final ViewPager2 editorViewPager;
    public final EditorDialogViewPagerAdapter editorViewPagerAdapter;
    public AutoBottomSheet hostBottomSheet;
    public int keyboardHeight;
    public PagerContentController pagerContentController;
    public final View signatureEditorScrollContainer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SignatureEditor(BaseClockView templateView, EditCallback editCallback) {
        super(templateView, editCallback, null, 4, null);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        this.baseTemplateView = templateView;
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.kg_layout_drawable_editor, (ViewGroup) templateView, false);
        Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.view.EditorDialogTitleView");
        this.container = (EditorDialogTitleView) viewInflate;
        View viewInflate2 = LayoutInflater.from(getContext()).inflate(getLayoutById(), (ViewGroup) templateView, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate2, "inflate(...)");
        this.signatureEditorScrollContainer = viewInflate2;
        View viewInflate3 = LayoutInflater.from(getContext()).inflate(R.layout.kg_editor_dialog_font_scroll_view, (ViewGroup) templateView, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate3, "inflate(...)");
        this.editorPagerContentWrapper = viewInflate3;
        View viewFindViewById = this.container.findViewById(R.id.editor_dialog_viewpager);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        ViewPager2 viewPager2 = (ViewPager2) viewFindViewById;
        this.editorViewPager = viewPager2;
        this.pagerContentController = new PagerContentController() { // from class: com.miui.keyguard.editor.edit.signature.SignatureEditor$pagerContentController$1
            @Override // com.miui.keyguard.editor.edit.style.PagerContentController
            public View loadClockTemplateEditorContent() {
                return PagerContentController.DefaultImpls.loadClockTemplateEditorContent(this);
            }

            @Override // com.miui.keyguard.editor.edit.style.PagerContentController
            public View loadFontEditorContent() {
                return this.this$0.getFontFilterView() == null ? this.this$0.createFontEditorContent() : this.this$0.getEditorPagerContentWrapper();
            }

            @Override // com.miui.keyguard.editor.edit.style.PagerContentController
            public View loadSignatureEditorContent() {
                return this.this$0.getSignatureEditorScrollContainer();
            }
        };
        this.colorModificationTarget = getColorModificationTarget();
        EditorDialogViewPagerAdapter editorDialogViewPagerAdapterCreateEditorDialogViewPagerAdapter = createEditorDialogViewPagerAdapter();
        this.editorViewPagerAdapter = editorDialogViewPagerAdapterCreateEditorDialogViewPagerAdapter;
        viewPager2.setAdapter(editorDialogViewPagerAdapterCreateEditorDialogViewPagerAdapter);
        viewPager2.setUserInputEnabled(false);
        this.editorFontContent = (LinearLayout) viewInflate3.findViewById(R.id.drawable_font_container);
        this.container.setViewPager(viewPager2, 0);
        templateView.setOnApplyWindowInsetsListener(this);
        View viewFindViewById2 = viewInflate2.findViewById(R.id.signature_editor_container);
        NestedScrollView nestedScrollView = viewFindViewById2 instanceof NestedScrollView ? (NestedScrollView) viewFindViewById2 : null;
        if (nestedScrollView != null) {
            keepTouchInsideContent(nestedScrollView);
        }
        View viewFindViewById3 = viewInflate3.findViewById(R.id.drawable_scroll_container);
        if (viewFindViewById3 != null) {
            keepTouchInsideContent(viewFindViewById3);
        }
        createFontEditorContent();
    }

    private final EditorDialogViewPagerAdapter createEditorDialogViewPagerAdapter() {
        return new EditorDialogViewPagerAdapter(false, this.pagerContentController);
    }

    public static final boolean keepTouchInsideContent$lambda$0(View view, MotionEvent motionEvent) {
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

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
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
            this.editorFontContent.addView(getFontFilterView());
        }
        return this.editorPagerContentWrapper;
    }

    public ColorModificationTarget getColorModificationTarget() {
        return ColorModificationTarget.SECONDARY_PART;
    }

    public final EditorDialogTitleView getContainer() {
        return this.container;
    }

    public final View getEditorPagerContentWrapper() {
        return this.editorPagerContentWrapper;
    }

    public abstract int getLayoutById();

    public final View getSignatureEditorScrollContainer() {
        return this.signatureEditorScrollContainer;
    }

    public final void keepTouchInsideContent(View view) {
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.keyguard.editor.edit.signature.SignatureEditor$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return SignatureEditor.keepTouchInsideContent$lambda$0(view2, motionEvent);
            }
        });
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(insets, "insets");
        Insets insets2 = insets.getInsets(WindowInsets.Type.ime());
        int i = this.keyboardHeight;
        int i2 = insets2.bottom;
        if (i != i2) {
            this.keyboardHeight = i2;
            Log.i("SignatureEditor", "keyboardHeight=" + i2);
            onSoftKeyboardVisibleChanged(this.keyboardHeight > 0);
            AutoBottomSheet autoBottomSheet = this.hostBottomSheet;
            if (autoBottomSheet != null) {
                autoBottomSheet.onImeKeyboardHeightChanged(this.keyboardHeight);
            }
        }
        return insets;
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0030  */
    public final void onSoftKeyboardVisibleChanged(boolean z) {
        int bottom;
        if (z) {
            int dimensionPixelOffset = (CrossListViewUtil.INSTANCE.requireDisplayRealSize(getContext()).y - this.keyboardHeight) - getContext().getResources().getDimensionPixelOffset(R.dimen.kg_signature_editor_margin_bottom_soft_keyboard);
            if (dimensionPixelOffset < this.signatureEditorScrollContainer.getBottom()) {
                bottom = dimensionPixelOffset - this.signatureEditorScrollContainer.getBottom();
            } else {
                bottom = 0;
            }
        } else {
            bottom = 0;
        }
        Log.i("SignatureEditor", "[Keyboard] visible=" + z + ", keyboardHeight=" + this.keyboardHeight + ", offsetVertical=" + bottom + ", containerBottom=" + this.signatureEditorScrollContainer.getBottom());
        updateContainerPosition(bottom);
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String string;
        Log.i("SignatureEditor", "onTextChanged: " + ((Object) charSequence));
        EditCallback editCallback = getEditCallback();
        if (charSequence == null || (string = charSequence.toString()) == null) {
            string = "";
        }
        editCallback.onEdited(60, string);
    }

    public final void setBaseTemplateView(BaseClockView baseClockView) {
        Intrinsics.checkNotNullParameter(baseClockView, "<set-?>");
        this.baseTemplateView = baseClockView;
    }

    public final void setEditorViewPagerHeight(int i) {
        ViewGroup.LayoutParams layoutParams = this.container.getLayoutParams();
        layoutParams.height = i;
        this.container.setLayoutParams(layoutParams);
        FontFilterView fontFilterView = getFontFilterView();
        if (fontFilterView != null) {
            fontFilterView.setHslPagerHeight(i);
        }
    }

    public final void setHostBottomSheet(AutoBottomSheet autoBottomSheet) {
        this.hostBottomSheet = autoBottomSheet;
    }

    public final void updateContainerPosition(int i) {
        PopupLayerView popupLayerView = popupLayerView();
        float f = i;
        if (popupLayerView.getTranslationY() == f) {
            return;
        }
        Log.i("SignatureEditor", "[ContainerPos] animating translationY: " + popupLayerView.getTranslationY() + " -> " + f);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(popupLayerView, (Property<PopupLayerView, Float>) View.TRANSLATION_Y, popupLayerView.getTranslationY(), f);
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator(1.5f));
        objectAnimatorOfFloat.setDuration(300L);
        objectAnimatorOfFloat.start();
    }
}
