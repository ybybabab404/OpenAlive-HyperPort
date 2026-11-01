package com.miui.keyguard.editor.edit.style;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.allinone.AllInOneFontEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.FontFilterView;
import com.miui.keyguard.editor.view.SelfContainedSpringBackLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.androidbasewidget.widget.SeekBar;
import miuix.core.widget.NestedScrollView;

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
/* JADX INFO: compiled from: AllInOneStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class AllInOneStyleEditor extends ViewStyleEditor {
    public final SelfContainedSpringBackLayout clockFontContainer;
    public final int clockWeight;
    public final int containerWidth;
    public final View hollowStyleContainer;
    public final NestedScrollView nestedScrollView;
    public final int selectedFontStyle;
    public final int selectedHollowStyle;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AllInOneStyleEditor(BaseClockView templateView, EditCallback editCallback, int i, int i2, int i3, FontStyleEditCallback fontStyleEditCallback) {
        super(i, templateView, editCallback, false, fontStyleEditCallback);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        this.selectedFontStyle = i;
        this.clockWeight = i2;
        this.selectedHollowStyle = i3;
        NestedScrollView nestedScrollView = new NestedScrollView(getContext());
        nestedScrollView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.keyguard.editor.edit.style.AllInOneStyleEditor$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AllInOneStyleEditor.nestedScrollView$lambda$1$lambda$0(view, motionEvent);
            }
        });
        this.nestedScrollView = nestedScrollView;
        SelfContainedSpringBackLayout selfContainedSpringBackLayout = new SelfContainedSpringBackLayout(getContext(), null, 2, null);
        selfContainedSpringBackLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        selfContainedSpringBackLayout.setScrollOrientation(2);
        selfContainedSpringBackLayout.setTarget(nestedScrollView);
        this.clockFontContainer = selfContainedSpringBackLayout;
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.kg_allinone_hollow_style_selector, (ViewGroup) null, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        this.hollowStyleContainer = viewInflate;
        EditorDialogTitleView container = getContainer();
        String string = getContext().getString(R.string.kg_title_clock_name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        container.setTitle(string, getContext().getString(R.string.kg_title_font_name), 1);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        this.containerWidth = (deviceUtil.isPhone() || deviceUtil.isFoldSmallScreen(getContext())) ? deviceUtil.currentDisplaySize(getContext()).width() : getContext().getResources().getDimensionPixelSize(R.dimen.kg_editor_container_pad_width);
        setBaseTemplateView(templateView);
        setSelectorViewVisible(8);
    }

    public static final Unit createDialogView$lambda$11$lambda$10$lambda$9$lambda$8(AllInOneStyleEditor allInOneStyleEditor, float f) {
        allInOneStyleEditor.getEditCallback().onEdited(230, Float.valueOf(f));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean nestedScrollView$lambda$1$lambda$0(View view, MotionEvent motionEvent) {
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

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final View createDialogView() {
        if (this.clockFontContainer.getChildCount() != 0) {
            return this.clockFontContainer;
        }
        SelfContainedSpringBackLayout selfContainedSpringBackLayout = this.clockFontContainer;
        NestedScrollView nestedScrollView = this.nestedScrollView;
        nestedScrollView.setId(R.id.kg_editor_dialog_classic_scroll_view);
        nestedScrollView.setVerticalScrollBarEnabled(false);
        nestedScrollView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        LinearLayout linearLayout = new LinearLayout(nestedScrollView.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setPadding(0, 0, 0, linearLayout.getResources().getDimensionPixelSize(R.dimen.kg_allinone_dialog_content_padding_bottom));
        TextView textView = new TextView(linearLayout.getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.topMargin = textView.getResources().getDimensionPixelSize(R.dimen.kg_classic_dialog_font_title_margin_top);
        layoutParams.setMarginStart(textView.getResources().getDimensionPixelSize(R.dimen.kg_editor_margin_right));
        textView.setLayoutParams(layoutParams);
        textView.setText(textView.getContext().getString(R.string.kg_title_font_name));
        textView.setTextColor(textView.getContext().getColor(R.color.kg_filter_font_effect_title_color));
        textView.setTextSize(14.0f);
        textView.setTypeface(Typeface.create(textView.getContext().getString(R.string.miuix_font_weight_subtitle), 0));
        linearLayout.addView(textView);
        AllInOneFontEditor allInOneFontEditor = new AllInOneFontEditor(getTemplateView(), this.selectedFontStyle, getEditCallback(), this.clockWeight, this.selectedHollowStyle, getFontStyleEditCallback(), this.hollowStyleContainer);
        allInOneFontEditor.setFontFilterView(getFontFilterView());
        View container = allInOneFontEditor.getContainer();
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams2.topMargin = container.getResources().getDimensionPixelSize(R.dimen.kg_font_editor_content_margin_top);
        container.setLayoutParams(layoutParams2);
        linearLayout.addView(container);
        FontFilterView fontFilterView = getFontFilterView();
        if (fontFilterView != null) {
            SeekBar seekBar = fontFilterView.getSeekBar();
            if (seekBar != null) {
                initListener(seekBar);
            }
            fontFilterView.setOnGlassTransparencyInit(new Function1() { // from class: com.miui.keyguard.editor.edit.style.AllInOneStyleEditor$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AllInOneStyleEditor.createDialogView$lambda$11$lambda$10$lambda$9$lambda$8(this.f$0, ((Float) obj).floatValue());
                }
            });
            ViewUtil.INSTANCE.setViewMarginBottom(fontFilterView, fontFilterView.getResources().getDimensionPixelSize(R.dimen.kg_editor_margin_bottom));
            fontFilterView.setCustomColorBottomSheet(getBaseTemplateView().getCustomFontBottomSheet());
        } else {
            fontFilterView = null;
        }
        linearLayout.addView(fontFilterView);
        linearLayout.addView(this.hollowStyleContainer);
        nestedScrollView.addView(linearLayout);
        selfContainedSpringBackLayout.addView(nestedScrollView);
        return this.clockFontContainer;
    }

    public final void initListener(SeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() { // from class: com.miui.keyguard.editor.edit.style.AllInOneStyleEditor.initListener.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(android.widget.SeekBar seekBar2, int i, boolean z) {
                if (z) {
                    float f = i / 100.0f;
                    FontFilterView fontFilterView = AllInOneStyleEditor.this.getFontFilterView();
                    if (fontFilterView != null) {
                        fontFilterView.updateGlassTransparency(f);
                    }
                    AllInOneStyleEditor.this.getEditCallback().onEdited(230, Float.valueOf(f));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(android.widget.SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(android.widget.SeekBar seekBar2) {
            }
        });
    }
}
