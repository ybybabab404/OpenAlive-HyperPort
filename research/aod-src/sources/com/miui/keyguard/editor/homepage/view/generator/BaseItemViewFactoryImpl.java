package com.miui.keyguard.editor.homepage.view.generator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.view.ClickHotAreaView;
import com.miui.keyguard.editor.homepage.view.CustomButtonView;
import com.miui.keyguard.editor.homepage.view.DeleteLayer;
import com.miui.keyguard.editor.utils.FileUtil;
import java.lang.ref.WeakReference;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

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
/* JADX INFO: compiled from: BaseItemViewFactoryImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class BaseItemViewFactoryImpl extends DynamicViewFactory {
    public final boolean myFloor;
    public static final Companion Companion = new Companion(null);
    public static final Stack presetRootLayoutCache = new Stack();
    public static final Stack historyRootLayoutCache = new Stack();

    /* JADX INFO: compiled from: BaseItemViewFactoryImpl.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void clearCache() {
            BaseItemViewFactoryImpl.presetRootLayoutCache.clear();
            BaseItemViewFactoryImpl.historyRootLayoutCache.clear();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseItemViewFactoryImpl(Context context, boolean z) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.myFloor = z;
    }

    public /* synthetic */ BaseItemViewFactoryImpl(Context context, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? false : z);
    }

    public final FrameLayout buildTemplateContainer(FactoryParams factoryParams) {
        FrameLayout frameLayoutGenerateTemplateContainer = generateTemplateContainer(factoryParams);
        RelativeLayout relativeLayoutGenerateContentView = generateContentView(factoryParams);
        relativeLayoutGenerateContentView.addView(generateCustomButton());
        if (FileUtil.isSupportVideoDepth()) {
            relativeLayoutGenerateContentView.addView(generateDepthVideoIcon());
        }
        relativeLayoutGenerateContentView.addView(generateLoopFlogIcon());
        frameLayoutGenerateTemplateContainer.addView(generateClickHotAreaView());
        frameLayoutGenerateTemplateContainer.addView(relativeLayoutGenerateContentView);
        return frameLayoutGenerateTemplateContainer;
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0020  */
    @Override // com.miui.keyguard.editor.homepage.view.generator.DynamicViewFactory
    public View create(FactoryParams factoryParams) {
        Stack stack;
        View view;
        Intrinsics.checkNotNull(factoryParams);
        if (factoryParams.isPreset()) {
            Stack stack2 = presetRootLayoutCache;
            if (stack2.isEmpty()) {
                stack = historyRootLayoutCache;
                if (!stack.isEmpty() && (view = (View) ((WeakReference) stack.pop()).get()) != null) {
                    return view;
                }
            } else {
                View view2 = (View) ((WeakReference) stack2.pop()).get();
                if (view2 != null) {
                    return view2;
                }
            }
        } else {
            stack = historyRootLayoutCache;
            if (!stack.isEmpty()) {
                return view;
            }
        }
        return createBaseItemRoot(factoryParams);
    }

    public final View createBaseItemRoot(FactoryParams factoryParams) {
        FrameLayout frameLayoutGenerateRootLayout = generateRootLayout();
        frameLayoutGenerateRootLayout.addView(buildTemplateContainer(factoryParams));
        return frameLayoutGenerateRootLayout;
    }

    public final FrameLayout.LayoutParams createMatchParentLayoutParams() {
        return generateFrameLayoutParams(matchParent(), matchParent());
    }

    public final FrameLayout.LayoutParams createTemplateContainerLayoutParams(FactoryParams factoryParams) {
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        FrameLayout.LayoutParams layoutParamsGenerateFrameLayoutParams = generateFrameLayoutParams((int) resourcesProvider.provideTemplateContainerWidth(getContext()), (int) resourcesProvider.provideTemplateContainerHeight(getContext(), factoryParams));
        if (factoryParams.isPreset()) {
            layoutParamsGenerateFrameLayoutParams.gravity = 17;
            return layoutParamsGenerateFrameLayoutParams;
        }
        layoutParamsGenerateFrameLayoutParams.gravity = 1;
        return layoutParamsGenerateFrameLayoutParams;
    }

    public final ClickHotAreaView generateClickHotAreaView() {
        ClickHotAreaView clickHotAreaView = new ClickHotAreaView(getContext(), null, 0, 6, null);
        clickHotAreaView.setId(R.id.kg_template_click_area);
        clickHotAreaView.setLayoutParams(createMatchParentLayoutParams());
        return clickHotAreaView;
    }

    public final RelativeLayout generateContentView(FactoryParams factoryParams) {
        int iProvideHomeExtraHeight;
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        FrameLayout.LayoutParams layoutParamsGenerateFrameLayoutParams = generateFrameLayoutParams(matchParent(), wrapContent());
        layoutParamsGenerateFrameLayoutParams.gravity = 80;
        int dimenPixelSize = getDimenPixelSize(R.dimen.kg_main_item_custom_button_margin_bottom);
        if (factoryParams.isHistory() || this.myFloor) {
            ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
            Context context = relativeLayout.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            iProvideHomeExtraHeight = resourcesProvider.provideHomeExtraHeight(context);
        } else {
            iProvideHomeExtraHeight = 0;
        }
        layoutParamsGenerateFrameLayoutParams.bottomMargin = dimenPixelSize + iProvideHomeExtraHeight;
        relativeLayout.setLayoutParams(layoutParamsGenerateFrameLayoutParams);
        relativeLayout.setImportantForAccessibility(2);
        return relativeLayout;
    }

    public final TextView generateCustomButton() {
        CustomButtonView customButtonView = new CustomButtonView(getContext(), null, 0, 6, null);
        customButtonView.setId(R.id.kg_tv_custom);
        RelativeLayout.LayoutParams layoutParamsGenerateRelativeLayoutParams = generateRelativeLayoutParams(wrapContent(), wrapContent());
        layoutParamsGenerateRelativeLayoutParams.addRule(13);
        customButtonView.setMaxLines(1);
        customButtonView.setLayoutParams(layoutParamsGenerateRelativeLayoutParams);
        customButtonView.setTypeface(getFont(R.string.kg_font_mi_sans_medium));
        customButtonView.setIncludeFontPadding(false);
        int i = R.string.kg_customize;
        customButtonView.setText(getString(i));
        customButtonView.setTextColor(getColor(R.color.kg_main_item_template_custom_button_text_color));
        customButtonView.setTextSize(0, getDimenPixelSize(R.dimen.kg_main_item_custom_button_text_size));
        customButtonView.resetTextSize();
        customButtonView.setContentDescription(customButtonView.getContext().getResources().getString(i));
        int dimenPixelSize = getDimenPixelSize(R.dimen.kg_main_item_custom_button_padding_horizontal);
        int dimenPixelSize2 = getDimenPixelSize(R.dimen.kg_main_item_custom_button_padding_vertical);
        customButtonView.setPadding(dimenPixelSize, dimenPixelSize2, dimenPixelSize, dimenPixelSize2);
        customButtonView.setFocusable(true);
        customButtonView.setClickable(true);
        customButtonView.setImportantForAccessibility(1);
        return customButtonView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final DeleteLayer generateDeleteLayer(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.kg_template_container);
        DeleteLayer deleteLayer = null;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        if (viewGroup != null) {
            int i = R.id.kg_delete_layer;
            DeleteLayer deleteLayer2 = (DeleteLayer) viewGroup.findViewById(i);
            if (deleteLayer2 == null) {
                deleteLayer2 = new DeleteLayer(getContext(), objArr2 == true ? 1 : 0, 2, objArr == true ? 1 : 0);
                deleteLayer2.setId(i);
                int iMatchParent = matchParent();
                ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
                Context context = deleteLayer2.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                float fProvideTemplateContainerHeight = resourcesProvider.provideTemplateContainerHeight(context, new FactoryParams(true, false, 2, null));
                Context context2 = deleteLayer2.getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                deleteLayer2.setLayoutParams(new FrameLayout.LayoutParams(iMatchParent, (int) (fProvideTemplateContainerHeight - (2 * resourcesProvider.provideTemplateContainerPadding(context2)))));
                deleteLayer2.setVisibility(8);
                viewGroup.addView(deleteLayer2);
            }
            deleteLayer = deleteLayer2;
        }
        Intrinsics.checkNotNull(deleteLayer);
        return deleteLayer;
    }

    public final ImageView generateDepthVideoIcon() {
        ImageView imageView = new ImageView(getContext());
        imageView.setId(R.id.kg_depth_video_custom);
        RelativeLayout.LayoutParams layoutParamsGenerateRelativeLayoutParams = generateRelativeLayoutParams(wrapContent(), wrapContent());
        layoutParamsGenerateRelativeLayoutParams.addRule(21);
        layoutParamsGenerateRelativeLayoutParams.addRule(15);
        layoutParamsGenerateRelativeLayoutParams.setMarginEnd(getDimenPixelSize(R.dimen.kg_main_item_depth_video_icon_margin_end));
        imageView.setLayoutParams(layoutParamsGenerateRelativeLayoutParams);
        imageView.setImageResource(R.drawable.kg_depth_video_icon);
        imageView.setVisibility(8);
        return imageView;
    }

    public final ImageView generateLoopFlogIcon() {
        ImageView imageView = new ImageView(getContext());
        imageView.setId(R.id.kg_loop_flag_icon);
        RelativeLayout.LayoutParams layoutParamsGenerateRelativeLayoutParams = generateRelativeLayoutParams(wrapContent(), wrapContent());
        layoutParamsGenerateRelativeLayoutParams.addRule(21);
        layoutParamsGenerateRelativeLayoutParams.addRule(15);
        layoutParamsGenerateRelativeLayoutParams.setMarginEnd(getDimenPixelSize(R.dimen.kg_main_item_depth_video_icon_margin_end));
        imageView.setLayoutParams(layoutParamsGenerateRelativeLayoutParams);
        imageView.setImageResource(R.drawable.kg_loop_flag_icon);
        imageView.setVisibility(8);
        return imageView;
    }

    public final FrameLayout generateRootLayout() {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(createMatchParentLayoutParams());
        return frameLayout;
    }

    public final FrameLayout generateTemplateContainer(FactoryParams factoryParams) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setId(R.id.kg_template_container);
        frameLayout.setLayoutParams(createTemplateContainerLayoutParams(factoryParams));
        int dimenPixelSize = getDimenPixelSize(R.dimen.kg_main_item_container_padding);
        frameLayout.setPadding(dimenPixelSize, dimenPixelSize, dimenPixelSize, dimenPixelSize);
        frameLayout.setImportantForAccessibility(2);
        return frameLayout;
    }

    public final void initBaseItemCache() {
        FactoryParams factoryParams = new FactoryParams(true, false, 2, null);
        for (int i = 0; i < 6; i++) {
            presetRootLayoutCache.push(new WeakReference(createBaseItemRoot(factoryParams)));
        }
        FactoryParams factoryParams2 = new FactoryParams(true, true);
        for (int i2 = 0; i2 < 11; i2++) {
            historyRootLayoutCache.push(new WeakReference(createBaseItemRoot(factoryParams2)));
        }
    }
}
