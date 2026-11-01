package com.miui.keyguard.editor.edit.clock;

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
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.base.FontEditor;
import com.miui.keyguard.editor.edit.style.FontStyleEditCallback;
import com.miui.keyguard.editor.edit.style.ViewStyleEditor;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.FontFilterView;
import com.miui.keyguard.editor.view.SelfContainedSpringBackLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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
/* JADX INFO: compiled from: ClassicTimeStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ClassicTimeStyleEditor extends ViewStyleEditor {
    public final SelfContainedSpringBackLayout clockFontContainer;
    public final int clockWeight;
    public final boolean isPlusMaxTemplate;
    public final NestedScrollView nestedScrollView;
    public final int selectedStyle;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassicTimeStyleEditor(BaseClockView templateView, EditCallback editCallback, int i, boolean z, int i2, FontStyleEditCallback fontStyleEditCallback) {
        super(i, templateView, editCallback, false, fontStyleEditCallback);
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        this.selectedStyle = i;
        this.isPlusMaxTemplate = z;
        this.clockWeight = i2;
        NestedScrollView nestedScrollView = new NestedScrollView(getContext());
        nestedScrollView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.keyguard.editor.edit.clock.ClassicTimeStyleEditor$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return ClassicTimeStyleEditor.nestedScrollView$lambda$1$lambda$0(view, motionEvent);
            }
        });
        this.nestedScrollView = nestedScrollView;
        SelfContainedSpringBackLayout selfContainedSpringBackLayout = new SelfContainedSpringBackLayout(getContext(), null, 2, null);
        selfContainedSpringBackLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        selfContainedSpringBackLayout.setScrollOrientation(2);
        selfContainedSpringBackLayout.setTarget(nestedScrollView);
        this.clockFontContainer = selfContainedSpringBackLayout;
        EditorDialogTitleView container = getContainer();
        String string = getContext().getString(R.string.kg_title_clock_name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        EditorDialogTitleView.setTitle$default(container, string, getContext().getString(R.string.kg_title_font_name), 0, 4, null);
    }

    public static final void createDialogView$lambda$11$lambda$10$lambda$4$lambda$3(ClassicTimeStyleEditor classicTimeStyleEditor, View view) {
        classicTimeStyleEditor.getEditCallback().onEdited(com.miui.maml.R.styleable.AppCompatTheme_toolbarStyle, 0);
    }

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
        View componentView = getComponentView();
        if (componentView != null) {
            linearLayout.addView(componentView);
            linearLayout.findViewById(R.id.dialog_component_content_container).setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.clock.ClassicTimeStyleEditor$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ClassicTimeStyleEditor.createDialogView$lambda$11$lambda$10$lambda$4$lambda$3(this.f$0, view);
                }
            });
        }
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
        View container = new FontEditor(getTemplateView(), this.selectedStyle, getEditCallback(), this.isPlusMaxTemplate, this.clockWeight, getFontStyleEditCallback()).getContainer();
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams2.topMargin = container.getResources().getDimensionPixelSize(R.dimen.kg_font_editor_content_margin_top);
        container.setLayoutParams(layoutParams2);
        linearLayout.addView(container);
        FontFilterView fontFilterView = getFontFilterView();
        if (fontFilterView != null) {
            ViewUtil.INSTANCE.setViewMarginBottom(fontFilterView, fontFilterView.getResources().getDimensionPixelSize(R.dimen.kg_editor_margin_bottom));
            fontFilterView.setCustomColorBottomSheet(getBaseTemplateView().getCustomFontBottomSheet());
        } else {
            fontFilterView = null;
        }
        linearLayout.addView(fontFilterView);
        nestedScrollView.addView(linearLayout);
        selfContainedSpringBackLayout.addView(nestedScrollView);
        return this.clockFontContainer;
    }

    public final View getComponentView() {
        if (this.isPlusMaxTemplate) {
            return null;
        }
        return LayoutInflater.from(getContext()).inflate(R.layout.kg_class_dialog_component_content_title, (ViewGroup) null);
    }
}
