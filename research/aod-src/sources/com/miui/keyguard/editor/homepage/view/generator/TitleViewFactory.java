package com.miui.keyguard.editor.homepage.view.generator;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.LargeFontLanguageHelper;
import com.miui.keyguard.editor.utils.ViewUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TitleViewFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TitleViewFactory extends DynamicViewFactory {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TitleViewFactory(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public final RelativeLayout buildTitleLayout() {
        RelativeLayout relativeLayoutGenerateTitleLayout = generateTitleLayout();
        TextView textViewGenerateTitleView = generateTitleView();
        relativeLayoutGenerateTitleLayout.addView(textViewGenerateTitleView);
        TextView textViewGenerateDescriptionView = generateDescriptionView();
        relativeLayoutGenerateTitleLayout.addView(textViewGenerateDescriptionView);
        LargeFontLanguageHelper.INSTANCE.adaptTitleAndDescriptionForLargeFontLanguage(textViewGenerateTitleView, textViewGenerateDescriptionView, false);
        return relativeLayoutGenerateTitleLayout;
    }

    @Override // com.miui.keyguard.editor.homepage.view.generator.DynamicViewFactory
    public View create(FactoryParams factoryParams) {
        return buildTitleLayout();
    }

    public final TextView generateDescriptionView() {
        TextView textView = new TextView(getContext());
        textView.setId(R.id.kg_tv_description);
        RelativeLayout.LayoutParams layoutParamsGenerateRelativeLayoutParams = generateRelativeLayoutParams(matchParent(), wrapContent());
        layoutParamsGenerateRelativeLayoutParams.addRule(12);
        textView.setLayoutParams(layoutParamsGenerateRelativeLayoutParams);
        textView.setGravity(17);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setIncludeFontPadding(false);
        textView.setMaxLines(2);
        textView.setTextSize(0, getDimenPixelSize(R.dimen.kg_main_item_floor_desc_text_size));
        textView.setTextColor(ViewUtil.INSTANCE.getColor(textView, R.color.kg_main_item_floor_desc_text_color));
        textView.setTypeface(getFont(R.string.kg_font_mi_sans_normal));
        textView.setVisibility(8);
        textView.setAccessibilityTraversalAfter(R.id.kg_tv_title);
        return textView;
    }

    public final RelativeLayout generateTitleLayout() {
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setId(R.id.kg_title_module);
        FrameLayout.LayoutParams layoutParamsGenerateFrameLayoutParams = generateFrameLayoutParams(getDimenPixelSize(R.dimen.kg_main_item_floor_title_bar_width), getDimenPixelSize(R.dimen.kg_main_item_floor_title_bar_height));
        layoutParamsGenerateFrameLayoutParams.gravity = 1;
        layoutParamsGenerateFrameLayoutParams.topMargin = getDimenPixelSize(R.dimen.kg_main_pager_margin_top);
        relativeLayout.setVisibility(4);
        relativeLayout.setLayoutParams(layoutParamsGenerateFrameLayoutParams);
        return relativeLayout;
    }

    public final TextView generateTitleView() {
        TextView textView = new TextView(getContext());
        textView.setId(R.id.kg_tv_title);
        RelativeLayout.LayoutParams layoutParamsGenerateRelativeLayoutParams = generateRelativeLayoutParams(wrapContent(), matchParent());
        layoutParamsGenerateRelativeLayoutParams.addRule(14);
        layoutParamsGenerateRelativeLayoutParams.addRule(2, R.id.kg_tv_description);
        textView.setAccessibilityTraversalAfter(R.id.kg_btn_editor_apply);
        textView.setLayoutParams(layoutParamsGenerateRelativeLayoutParams);
        textView.setGravity(81);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setIncludeFontPadding(false);
        textView.setMaxLines(2);
        textView.setTextSize(0, getDimenPixelSize(R.dimen.kg_main_item_floor_title_text_size));
        textView.setTextColor(ViewUtil.INSTANCE.getColor(textView, R.color.kg_main_item_floor_title_text_color));
        textView.setTypeface(getFont(R.string.kg_font_mi_sans_normal));
        return textView;
    }
}
