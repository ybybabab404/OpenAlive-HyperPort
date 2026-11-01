package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.os.Trace;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.utils.AnimatorKt;
import com.miui.keyguard.editor.utils.BlendMode;
import com.miui.keyguard.editor.utils.DensityUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.CircleImageView;
import com.miui.keyguard.editor.view.KgFillVisualCheckBox;
import com.miui.keyguard.editor.view.KgPreviewVisualCheckBox;
import com.miui.keyguard.editor.view.KgVisualCheckBox;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.IHoverStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.visual.check.BorderLayout;
import miuix.visual.check.VisualCheckedTextView;

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
/* JADX INFO: compiled from: FunctionButtonBar.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public class FunctionButtonBar {
    public final ViewGroup buttonGroup;
    public final RectF buttonGroupRectF;
    public final Lazy depthPresetButton$delegate;
    public RelativeLayout dynamicButtonGroup;
    public final Lazy dynamicSwitchButton$delegate;
    public final ViewGroup groupContainer;
    public final ViewGroup parent;
    public final FrameLayout root;
    public final BaseTemplateView template;
    public TextView titleView;

    /* JADX INFO: compiled from: FunctionButtonBar.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CheckBoxStyle.values().length];
            try {
                iArr[CheckBoxStyle.FILL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CheckBoxStyle.PREVIEW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
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
    public FunctionButtonBar(ViewGroup parent, BaseTemplateView template) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(template, "template");
        this.parent = parent;
        this.template = template;
        this.buttonGroupRectF = new RectF();
        final Context context = parent.getContext();
        FrameLayout frameLayout = new FrameLayout(context) { // from class: com.miui.keyguard.editor.edit.base.FunctionButtonBar$root$1
            @Override // android.view.View
            public boolean hasOverlappingRendering() {
                return false;
            }
        };
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        ViewGroup.LayoutParams layoutParams2 = template.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams2, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) layoutParams2;
        layoutParams.width = layoutParams3.width;
        layoutParams.height = layoutParams3.height;
        layoutParams.setMarginStart(layoutParams3.getMarginStart());
        frameLayout.setLayoutParams(layoutParams);
        this.root = frameLayout;
        RelativeLayout relativeLayout = new RelativeLayout(parent.getContext());
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams4.bottomMargin = relativeLayout.getResources().getDimensionPixelSize(R.dimen.kg_function_depth_preset_margin_bottom);
        relativeLayout.setLayoutParams(layoutParams4);
        this.dynamicButtonGroup = relativeLayout;
        this.depthPresetButton$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.edit.base.FunctionButtonBar$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FunctionButtonBar.depthPresetButton_delegate$lambda$6(this.f$0);
            }
        });
        this.dynamicSwitchButton$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.edit.base.FunctionButtonBar$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FunctionButtonBar.dynamicSwitchButton_delegate$lambda$8(this.f$0);
            }
        });
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-2, linearLayout.getContext().getResources().getDimensionPixelSize(R.dimen.kg_function_button_container_height));
        layoutParams5.gravity = 49;
        layoutParams5.setMarginStart(linearLayout.getResources().getDimensionPixelSize(R.dimen.kg_function_button_margin_start));
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            Context context2 = linearLayout.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            if (!deviceUtil.isDisplayLandScape(context2)) {
                layoutParams5.bottomMargin = linearLayout.getResources().getDimensionPixelSize(R.dimen.kg_function_button_group_margin_bottom);
            }
        }
        linearLayout.setLayoutParams(layoutParams5);
        linearLayout.setClipChildren(false);
        this.buttonGroup = linearLayout;
        LinearLayout linearLayout2 = new LinearLayout(parent.getContext());
        linearLayout2.setClipChildren(false);
        linearLayout2.setOrientation(1);
        FrameLayout.LayoutParams layoutParams6 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams6.gravity = 80;
        linearLayout2.setLayoutParams(layoutParams6);
        this.groupContainer = linearLayout2;
        EditorApplicationProxy.Companion companion = EditorApplicationProxy.Companion;
        if (companion.getEDITOR_DEBUG()) {
            Trace.beginSection("FunctionButtonBar:init");
        }
        linearLayout2.addView(linearLayout);
        frameLayout.addView(linearLayout2);
        ViewUtil.INSTANCE.decorateBackground(linearLayout2);
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
        }
    }

    public static /* synthetic */ void addDivideLine$default(FunctionButtonBar functionButtonBar, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addDivideLine");
        }
        if ((i3 & 1) != 0) {
            i = R.color.kg_white_30;
        }
        if ((i3 & 2) != 0) {
            i2 = -1;
        }
        functionButtonBar.addDivideLine(i, i2);
    }

    public static /* synthetic */ KgVisualCheckBox addFillButton$default(FunctionButtonBar functionButtonBar, int i, int i2, int i3, int i4, int i5, FillButtonBlendStyle fillButtonBlendStyle, int i6, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addFillButton");
        }
        if ((i6 & 8) != 0) {
            i4 = -1;
        }
        int i7 = i4;
        if ((i6 & 32) != 0) {
            fillButtonBlendStyle = FillButtonBlendStyle.A;
        }
        return functionButtonBar.addFillButton(i, i2, i3, i7, i5, fillButtonBlendStyle);
    }

    public static /* synthetic */ KgVisualCheckBox addNormalButton$default(FunctionButtonBar functionButtonBar, int i, int i2, int i3, int i4, int i5, int i6, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addNormalButton");
        }
        if ((i6 & 16) != 0) {
            i5 = -1;
        }
        return functionButtonBar.addNormalButton(i, i2, i3, i4, i5);
    }

    public static /* synthetic */ KgVisualCheckBox createVisualCheckBox$default(FunctionButtonBar functionButtonBar, CheckBoxStyle checkBoxStyle, int i, int i2, int i3, Integer num, Integer num2, int i4, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createVisualCheckBox");
        }
        if ((i4 & 16) != 0) {
            num = null;
        }
        if ((i4 & 32) != 0) {
            num2 = null;
        }
        return functionButtonBar.createVisualCheckBox(checkBoxStyle, i, i2, i3, num, num2);
    }

    public static final void createVisualCheckBox$lambda$37(VisualCheckedTextView visualCheckedTextView, Resources resources) {
        if (visualCheckedTextView.getHeight() > resources.getDimensionPixelSize(R.dimen.kg_function_text_size_height)) {
            visualCheckedTextView.setTextSize(0, resources.getDimension(R.dimen.kg_function_text_small_size));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final DepthPresetDownloadButton depthPresetButton_delegate$lambda$6(FunctionButtonBar functionButtonBar) {
        Context context = functionButtonBar.parent.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        DepthPresetDownloadButton depthPresetDownloadButton = new DepthPresetDownloadButton(context, null, 2, 0 == true ? 1 : 0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 1;
        depthPresetDownloadButton.setLayoutParams(layoutParams);
        return depthPresetDownloadButton;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final DynamicSwitchButton dynamicSwitchButton_delegate$lambda$8(FunctionButtonBar functionButtonBar) {
        Context context = functionButtonBar.parent.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        DynamicSwitchButton dynamicSwitchButton = new DynamicSwitchButton(context, null, 2, 0 == true ? 1 : 0);
        dynamicSwitchButton.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        return dynamicSwitchButton;
    }

    public static final Unit exitForApply$lambda$39(FunctionButtonBar functionButtonBar) {
        functionButtonBar.root.setVisibility(4);
        return Unit.INSTANCE;
    }

    public static final Unit hide$lambda$44(FunctionButtonBar functionButtonBar) {
        functionButtonBar.root.setVisibility(4);
        return Unit.INSTANCE;
    }

    public static final void show$lambda$46(final FunctionButtonBar functionButtonBar) {
        functionButtonBar.initFroAnim(functionButtonBar.root.getHeight() / 5);
        functionButtonBar.root.setAlpha(1.0f);
        switchButton$default(functionButtonBar, true, false, new Function0() { // from class: com.miui.keyguard.editor.edit.base.FunctionButtonBar$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FunctionButtonBar.show$lambda$46$lambda$45(this.f$0);
            }
        }, 2, null);
        switchTitle$default(functionButtonBar, true, null, 2, null);
    }

    public static final Unit show$lambda$46$lambda$45(FunctionButtonBar functionButtonBar) {
        functionButtonBar.groupContainer.setTranslationY(0.0f);
        functionButtonBar.updateButtonGroupRec();
        return Unit.INSTANCE;
    }

    public static /* synthetic */ void switchButton$default(FunctionButtonBar functionButtonBar, boolean z, boolean z2, Function0 function0, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: switchButton");
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        if ((i & 4) != 0) {
            function0 = null;
        }
        functionButtonBar.switchButton(z, z2, function0);
    }

    public static /* synthetic */ void switchTitle$default(FunctionButtonBar functionButtonBar, boolean z, Function0 function0, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: switchTitle");
        }
        if ((i & 2) != 0) {
            function0 = null;
        }
        functionButtonBar.switchTitle(z, function0);
    }

    public final KgVisualCheckBox addBorderButton(int i, int i2, int i3, int i4) {
        KgVisualCheckBox kgVisualCheckBoxCreateVisualCheckBox$default = createVisualCheckBox$default(this, CheckBoxStyle.BORDER, i, i2, i3, null, null, 48, null);
        addButton(i4, kgVisualCheckBoxCreateVisualCheckBox$default);
        return kgVisualCheckBoxCreateVisualCheckBox$default;
    }

    public final KgVisualCheckBox addBorderPreviewButton(int i, int i2, int i3, int i4, int i5, Integer num) {
        KgVisualCheckBox kgVisualCheckBoxCreateVisualCheckBox = createVisualCheckBox(CheckBoxStyle.PREVIEW, i, i2, i3, Integer.valueOf(i4), num);
        addButton(i5, kgVisualCheckBoxCreateVisualCheckBox);
        return kgVisualCheckBoxCreateVisualCheckBox;
    }

    public final void addBottomTip(int i) {
        Context context = this.parent.getContext();
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.leftMargin = DensityUtils.dp2Px(28);
        layoutParams.rightMargin = DensityUtils.dp2Px(28);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(17);
        textView.setText(i);
        textView.setTextSize(0, DensityUtils.dp2Px(13));
        textView.setTextColor(context.getColor(R.color.kg_white_40));
        textView.setMaxLines(3);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        this.buttonGroup.addView(textView, 0);
    }

    public final void addButton(int i, View view) {
        ViewGroup viewGroup = this.buttonGroup;
        if (i == -1) {
            i = viewGroup.getChildCount();
        }
        viewGroup.addView(view, i);
    }

    public final DepthPresetDownloadButton addDepthPresetButton(CommonConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        getDepthPresetButton().onAddToBar(config);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        this.dynamicButtonGroup.addView(getDepthPresetButton(), layoutParams);
        return getDepthPresetButton();
    }

    public final void addDivideLine(int i, int i2) {
        View view = new View(this.parent.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(view.getResources().getDimensionPixelSize(R.dimen.kg_divide_line_width), view.getResources().getDimensionPixelSize(R.dimen.kg_divide_line_height));
        layoutParams.gravity = 48;
        layoutParams.setMarginStart(view.getResources().getDimensionPixelSize(R.dimen.kg_divide_line_margin_start));
        layoutParams.setMarginEnd(view.getResources().getDimensionPixelSize(R.dimen.kg_divide_line_margin_end));
        layoutParams.topMargin = view.getResources().getDimensionPixelSize(R.dimen.kg_divide_line_margin_top);
        view.setLayoutParams(layoutParams);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(view.getContext().getResources().getDimensionPixelSize(R.dimen.kg_button_vertical_line_radius));
        gradientDrawable.setColor(ViewUtil.INSTANCE.getColor(view, i));
        view.setBackground(gradientDrawable);
        addButton(i2, view);
    }

    public final View addDynamicButtonGroup() {
        this.groupContainer.addView(this.dynamicButtonGroup, 0);
        return this.dynamicButtonGroup;
    }

    public final DynamicSwitchButton addDynamicSwitchButton() {
        getDynamicSwitchButton().onAddToBar();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(21);
        layoutParams.setMarginEnd(this.parent.getContext().getResources().getDimensionPixelSize(R.dimen.kg_function_depth_preset_margin_right));
        this.dynamicButtonGroup.addView(getDynamicSwitchButton(), layoutParams);
        return getDynamicSwitchButton();
    }

    public final KgVisualCheckBox addFillButton(int i, int i2, int i3, int i4, int i5, FillButtonBlendStyle blendStyle) {
        Intrinsics.checkNotNullParameter(blendStyle, "blendStyle");
        KgVisualCheckBox kgVisualCheckBoxCreateVisualCheckBox$default = createVisualCheckBox$default(this, CheckBoxStyle.FILL, i, i2, i3, null, null, 48, null);
        Intrinsics.checkNotNull(kgVisualCheckBoxCreateVisualCheckBox$default, "null cannot be cast to non-null type com.miui.keyguard.editor.view.KgFillVisualCheckBox");
        KgFillVisualCheckBox kgFillVisualCheckBox = (KgFillVisualCheckBox) kgVisualCheckBoxCreateVisualCheckBox$default;
        kgFillVisualCheckBox.setSelectedBackgroundColor(i5);
        Context context = kgFillVisualCheckBox.getContext();
        kgFillVisualCheckBox.setUnselectedBlendStyle(context.getColor(R.color.kg_function_button_blend_color1), BlendMode.COLOR_BURN, context.getColor(R.color.kg_function_button_blend_color2), BlendMode.SRC_OVER);
        addButton(i4, kgFillVisualCheckBox);
        return kgFillVisualCheckBox;
    }

    public final KgVisualCheckBox addNormalButton(int i, int i2, int i3, int i4, int i5) {
        KgVisualCheckBox kgVisualCheckBoxCreateVisualCheckBox$default = createVisualCheckBox$default(this, CheckBoxStyle.FILL, i, i2, i3, Integer.valueOf(i4), null, 32, null);
        if (kgVisualCheckBoxCreateVisualCheckBox$default instanceof KgFillVisualCheckBox) {
            ((KgFillVisualCheckBox) kgVisualCheckBoxCreateVisualCheckBox$default).setNormalButton(true);
        }
        addButton(i5, kgVisualCheckBoxCreateVisualCheckBox$default);
        return kgVisualCheckBoxCreateVisualCheckBox$default;
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
    public final TextView addTemplateTitle(int i, float f) {
        TextView textView;
        TextView textView2 = new TextView(this.parent.getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 49;
        textView2.setLayoutParams(layoutParams);
        textView2.setText(i);
        textView2.setTextColor(ViewUtil.INSTANCE.getColor(textView2, R.color.kg_white_95));
        textView2.setAccessibilityTraversalAfter(R.id.kg_btn_editor_apply);
        this.titleView = textView2;
        updateTemplateTitle(f);
        if (i == R.string.kg_template_title_lock_screen && (textView = this.titleView) != null) {
            textView.setVisibility(4);
        }
        this.root.addView(this.titleView, 0);
        TextView textView3 = this.titleView;
        Intrinsics.checkNotNull(textView3);
        return textView3;
    }

    public final void attach() {
        this.root.setAlpha(0.0f);
        this.parent.addView(this.root);
    }

    public final void changeAlpha(float f) {
        changeVisible(f > 0.0f);
        this.root.setAlpha(f);
    }

    public final void changeVisible(boolean z) {
        int i = z ? 0 : 8;
        if (this.root.getVisibility() != i) {
            this.root.setVisibility(i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v2, types: [android.view.View, android.widget.FrameLayout] */
    /* JADX WARN: Type inference failed for: r11v0, types: [android.widget.LinearLayout, com.miui.keyguard.editor.view.KgVisualCheckBox] */
    /* JADX WARN: Type inference failed for: r14v0, types: [android.view.View, android.view.ViewGroup, android.widget.FrameLayout] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [miuix.visual.check.BorderLayout] */
    /* JADX WARN: Type inference failed for: r1v24, types: [com.miui.keyguard.editor.view.KgFillVisualCheckBox] */
    /* JADX WARN: Type inference failed for: r1v25, types: [android.view.View[]] */
    /* JADX WARN: Type inference failed for: r1v26 */
    /* JADX WARN: Type inference failed for: r3v12, types: [android.view.View[]] */
    /* JADX WARN: Type inference failed for: r3v6, types: [android.view.View, android.widget.LinearLayout, miuix.visual.check.BorderLayout] */
    /* JADX WARN: Type inference failed for: r6v5, types: [android.view.LayoutInflater] */
    /* JADX WARN: Type inference failed for: r7v12, types: [android.view.View[]] */
    /* JADX WARN: Type inference failed for: r7v16, types: [miuix.animation.IHoverStyle] */
    /* JADX WARN: Type inference failed for: r7v2, types: [com.miui.keyguard.editor.utils.ViewUtil] */
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
    public final KgVisualCheckBox createVisualCheckBox(CheckBoxStyle checkBoxStyle, int i, int i2, int i3, Integer num, Integer num2) {
        LinearLayout kgFillVisualCheckBox;
        ?? r1;
        ImageView imageView;
        ImageView imageView2;
        Context context = this.root.getContext();
        EditorApplicationProxy.Companion companion = EditorApplicationProxy.Companion;
        if (companion.getEDITOR_DEBUG()) {
            Trace.beginSection("VisualCheckBox");
        }
        int[] iArr = WhenMappings.$EnumSwitchMapping$0;
        int i4 = iArr[checkBoxStyle.ordinal()];
        if (i4 != 1) {
            kgFillVisualCheckBox = i4 != 2 ? new KgVisualCheckBox(context, null) : new KgPreviewVisualCheckBox(context, null);
        } else {
            kgFillVisualCheckBox = new KgFillVisualCheckBox(context, null);
        }
        ?? r11 = kgFillVisualCheckBox;
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
        }
        r11.setId(i);
        r11.setOrientation(1);
        final Resources resources = context.getResources();
        r11.setLayoutParams(new LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.kg_function_button_container_width), -2));
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_function_button_size);
        int i5 = iArr[checkBoxStyle.ordinal()] == 1 ? R.layout.kg_layout_border_layout_fill : R.layout.kg_layout_border_layout_border;
        ?? frameLayout = new FrameLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, resources.getDimensionPixelSize(R.dimen.kg_function_bord_layout_size));
        ViewUtil.INSTANCE.setGravity(frameLayout, 17);
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setId(R.id.kg_func_button_container);
        View viewInflate = LayoutInflater.from(context).inflate(i5, frameLayout, false);
        Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type miuix.visual.check.BorderLayout");
        ?? r3 = (BorderLayout) viewInflate;
        ImageView imageView3 = new ImageView(context);
        imageView3.setId(R.id.kg_func_button_mask);
        int i6 = R.dimen.kg_function_mask_size;
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(resources.getDimensionPixelSize(i6), resources.getDimensionPixelSize(i6));
        layoutParams2.gravity = 17;
        imageView3.setLayoutParams(layoutParams2);
        ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;
        imageView3.setScaleType(scaleType);
        imageView3.setVisibility(8);
        frameLayout.addView(r3);
        frameLayout.addView(imageView3);
        ?? frameLayout2 = new FrameLayout(context);
        frameLayout2.setId(R.id.kg_func_icon_box);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
        layoutParams3.gravity = 17;
        frameLayout2.setLayoutParams(layoutParams3);
        r3.addView(frameLayout2);
        r11.setIconBox(frameLayout2);
        ImageView imageView4 = new ImageView(context);
        imageView4.setId(R.id.kg_func_button_icon);
        FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams4.gravity = 17;
        imageView4.setLayoutParams(layoutParams4);
        imageView4.setImageResource(i2);
        frameLayout2.addView(imageView4);
        r11.setIcon(imageView4);
        if (checkBoxStyle == CheckBoxStyle.PREVIEW) {
            r11.setIFolme(Folme.useAt((View[]) new View[]{r3}));
            IFolme iFolme = r11.getIFolme();
            Intrinsics.checkNotNull(iFolme);
            iFolme.hover().setEffect(IHoverStyle.HoverEffect.FLOATED).handleHoverOf(r3, new AnimConfig[0]);
            Intrinsics.checkNotNull(context);
            r1 = r3;
            imageView = imageView4;
            CircleImageView circleImageView = new CircleImageView(context, null, 0, 6, null);
            circleImageView.setId(R.id.kg_func_button_preview);
            circleImageView.setScaleType(scaleType);
            FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams(-1, -1);
            layoutParams5.gravity = 17;
            circleImageView.setLayoutParams(layoutParams5);
            frameLayout2.addView(circleImageView);
            r11.setPreview(circleImageView);
            ImageView imageView5 = new ImageView(context);
            imageView5.setId(R.id.kg_func_button_extra_icon);
            FrameLayout.LayoutParams layoutParams6 = new FrameLayout.LayoutParams(-2, -2);
            layoutParams6.gravity = 17;
            imageView5.setLayoutParams(layoutParams6);
            Intrinsics.checkNotNull(num);
            imageView5.setImageResource(num.intValue());
            frameLayout2.addView(imageView5);
            r11.setExtraIcon(imageView5);
            imageView2 = imageView3;
        } else {
            r1 = r3;
            imageView = imageView4;
            imageView2 = imageView3;
        }
        r11.setMaskView(imageView2);
        r11.addView(frameLayout);
        r11.setBorderLayout(r1);
        if (r11 instanceof KgFillVisualCheckBox) {
            Folme.useAt((View[]) new View[]{r1}).touch().setTint(0).setNoScale(true);
            ((KgFillVisualCheckBox) r11).setBackground(frameLayout2);
            r11.setIFolme(Folme.useAt((View[]) new View[]{frameLayout2}));
            r11.setIcon(imageView);
        }
        if (companion.getEDITOR_DEBUG()) {
            Trace.beginSection("VisualCheckedTextView");
        }
        final VisualCheckedTextView visualCheckedTextView = new VisualCheckedTextView(context, null);
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
        }
        visualCheckedTextView.setId(R.id.kg_func_button_label);
        LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams7.gravity = 1;
        layoutParams7.topMargin = context.getResources().getDimensionPixelSize(R.dimen.kg_function_button_text_margin_top);
        visualCheckedTextView.setLayoutParams(layoutParams7);
        visualCheckedTextView.setTextColor(context.getColor(R.color.kg_white_60));
        visualCheckedTextView.setTextSize(0, resources.getDimension(R.dimen.kg_function_text_size));
        visualCheckedTextView.setTextAlignment(4);
        if (companion.getEDITOR_DEBUG()) {
            Trace.beginSection("createVisualCheckBox: setText");
        }
        visualCheckedTextView.setText(i3);
        visualCheckedTextView.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.FunctionButtonBar$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                FunctionButtonBar.createVisualCheckBox$lambda$37(visualCheckedTextView, resources);
            }
        });
        if (num2 != null) {
            visualCheckedTextView.setContentDescription(context.getResources().getString(num2.intValue()));
        }
        visualCheckedTextView.setImportantForAccessibility(2);
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
        }
        r11.addView(visualCheckedTextView);
        r11.setLabel(visualCheckedTextView);
        return r11;
    }

    public final void exitForApply(boolean z) {
        if (z) {
            TextView textView = this.titleView;
            if (textView != null) {
                AnimatorKt.alphaTo$default(textView, 0.0f, 0.0f, 0.0f, null, 14, null);
            }
            TemplateApplierAnimatorKt.startTranslateAnimation$default(this.groupContainer, null, 1, null);
            return;
        }
        switchButton$default(this, false, false, new Function0() { // from class: com.miui.keyguard.editor.edit.base.FunctionButtonBar$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FunctionButtonBar.exitForApply$lambda$39(this.f$0);
            }
        }, 2, null);
        TextView textView2 = this.titleView;
        if (textView2 != null) {
            Folme.use((View) textView2).state().setup("FunctionButtonBar.titleView.hide").add(ViewProperty.TRANSLATION_Y, -(this.root.getHeight() / 5.0f)).add((FloatProperty) ViewProperty.ALPHA, 0.0f).to("FunctionButtonBar.titleView.hide", new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.3f)));
        }
    }

    public final ViewGroup getButtonGroup() {
        return this.buttonGroup;
    }

    public final DepthPresetDownloadButton getDepthPresetButton() {
        return (DepthPresetDownloadButton) this.depthPresetButton$delegate.getValue();
    }

    public final RelativeLayout getDynamicButtonGroup() {
        return this.dynamicButtonGroup;
    }

    public final DynamicSwitchButton getDynamicSwitchButton() {
        return (DynamicSwitchButton) this.dynamicSwitchButton$delegate.getValue();
    }

    public final ViewGroup getGroupContainer() {
        return this.groupContainer;
    }

    public final FrameLayout getRoot() {
        return this.root;
    }

    public final void hide() {
        switchButton$default(this, false, false, new Function0() { // from class: com.miui.keyguard.editor.edit.base.FunctionButtonBar$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FunctionButtonBar.hide$lambda$44(this.f$0);
            }
        }, 2, null);
        switchTitle$default(this, false, null, 2, null);
    }

    public final boolean inButtonGroup(float f, float f2) {
        return this.buttonGroupRectF.contains(f, f2);
    }

    public final void initFroAnim(float f) {
        if (this.buttonGroup.getTranslationY() != f) {
            this.buttonGroup.setTranslationY(f);
        }
        this.dynamicButtonGroup.setAlpha(0.0f);
        TextView textView = this.titleView;
        if (textView != null) {
            textView.setAlpha(0.0f);
        }
    }

    public final void resetMarginTop(float f) {
        TextView textView = this.titleView;
        if (textView != null) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
            Context context = textView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            layoutParams.topMargin = (int) resourcesProvider.provideTemplateTitleTopMargin(context, f);
            layoutParams.gravity = 49;
            textView.setLayoutParams(layoutParams);
        }
    }

    public final void show(boolean z) {
        this.root.setVisibility(0);
        if (z) {
            initFroAnim(this.root.getHeight() / 5);
            this.parent.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.FunctionButtonBar$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    FunctionButtonBar.show$lambda$46(this.f$0);
                }
            });
        }
    }

    public final void switchButton(boolean z, boolean z2, final Function0 function0) {
        int height = z ? 0 : this.root.getHeight() / 5;
        float f = z ? 1.0f : 0.0f;
        if (z2) {
            Folme.use((View) this.buttonGroup).state().setup("FunctionButtonBar.groupContainer").add((FloatProperty) ViewProperty.TRANSLATION_Y, height).to("FunctionButtonBar.groupContainer", new AnimConfig().setEase(EaseManager.getStyle(-2, 0.85f, 0.35f)).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.edit.base.FunctionButtonBar.switchButton.1
                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    Function0 function1 = function0;
                    if (function1 != null) {
                        function1.invoke();
                    }
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    Function0 function1 = function0;
                    if (function1 != null) {
                        function1.invoke();
                    }
                }
            }));
            Folme.use((View) this.dynamicButtonGroup).state().setup("FunctionButtonBar.dynamicButtonGroup").add(ViewProperty.ALPHA, f).to("FunctionButtonBar.dynamicButtonGroup", new AnimConfig().setEase(EaseManager.getStyle(-2, 0.85f, 0.35f)));
        } else {
            this.buttonGroup.setTranslationY(height);
            this.dynamicButtonGroup.setAlpha(f);
        }
    }

    public final void switchTitle(boolean z, final Function0 function0) {
        TextView textView = this.titleView;
        if (textView != null) {
            if (z) {
                textView.setVisibility(0);
            }
            final float f = z ? 1.0f : 0.0f;
            Folme.use((View) textView).state().setup("FunctionButtonBar.titleView.hide").add(ViewProperty.ALPHA, f).to("FunctionButtonBar.titleView.hide", new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.3f)).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.edit.base.FunctionButtonBar$switchTitle$1$1
                public final void finishAnim() {
                    TextView textView2 = this.this$0.titleView;
                    if (textView2 != null) {
                        textView2.setAlpha(f);
                    }
                    Function0 function1 = function0;
                    if (function1 != null) {
                        function1.invoke();
                    }
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    super.onCancel(obj);
                    finishAnim();
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    super.onComplete(obj);
                    finishAnim();
                }
            }));
        }
    }

    public final void updateButtonGroupRec() {
        this.buttonGroupRectF.set(this.buttonGroup.getLeft(), this.groupContainer.getTop(), this.buttonGroup.getRight(), this.groupContainer.getBottom());
    }

    public final void updateTemplateTitle(float f) {
        TextView textView = this.titleView;
        if (textView != null) {
            textView.setTextSize(0, textView.getContext().getResources().getDimensionPixelSize(R.dimen.kg_common_editor_title_text_size));
            ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
            FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
            if (layoutParams2 != null) {
                ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
                Context context = textView.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                layoutParams2.topMargin = (int) resourcesProvider.provideTemplateTitleTopMargin(context, f);
            }
        }
    }
}
