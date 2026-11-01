package com.miui.keyguard.editor.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.ActivityUIAdapter;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ClockTemplatePagerAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClockTemplateItemViewHolder extends RecyclerView.ViewHolder {
    public final View itemView;
    public final ImageView templateImageView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockTemplateItemViewHolder(View itemView) {
        super(itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        this.itemView = itemView;
        View viewFindViewById = itemView.findViewById(R.id.kg_clock_template_image);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.templateImageView = (ImageView) viewFindViewById;
    }

    public static final void bindTemplateItemView$lambda$0(Function2 function2, ClockTemplateItem clockTemplateItem, int i, View view) {
        function2.invoke(clockTemplateItem, Integer.valueOf(i));
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00cf  */
    public final void bindTemplateItemView(final ClockTemplateItem clockTemplateItem, final Function2 clickCallback, final int i) {
        int iFindDrawableResIdByName;
        Resources resources;
        ClockInfo clockInfo;
        Intrinsics.checkNotNullParameter(clockTemplateItem, "clockTemplateItem");
        Intrinsics.checkNotNullParameter(clickCallback, "clickCallback");
        this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.view.ClockTemplateItemViewHolder$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClockTemplateItemViewHolder.bindTemplateItemView$lambda$0(clickCallback, clockTemplateItem, i, view);
            }
        });
        this.itemView.setSelected(clockTemplateItem.isSelected());
        if (clockTemplateItem.getContentDescription() != null) {
            TemplateDataUtil templateDataUtil = TemplateDataUtil.INSTANCE;
            TemplateConfig templateConfig = clockTemplateItem.getTemplateConfig();
            ClockInfo clockInfo2 = templateConfig != null ? templateConfig.getClockInfo() : null;
            TemplateConfig templateConfig2 = clockTemplateItem.getTemplateConfig();
            Integer templateNameForAccessibility = templateDataUtil.getTemplateNameForAccessibility(clockInfo2, templateConfig2 != null ? templateConfig2.getTemplateName() : null);
            if (templateNameForAccessibility == null) {
                templateNameForAccessibility = Integer.valueOf(R.string.kg_template_preview_content_description);
            }
            TemplateConfig templateConfig3 = clockTemplateItem.getTemplateConfig();
            String templateId = (templateConfig3 == null || (clockInfo = templateConfig3.getClockInfo()) == null) ? null : clockInfo.getTemplateId();
            if (templateId != null && Intrinsics.areEqual(templateId, "depth_pets")) {
                templateNameForAccessibility = Integer.valueOf(R.string.kg_doodle_clock_type_digital);
            }
            View view = this.itemView;
            if (view != null) {
                Context context = view.getContext();
                view.setContentDescription((context == null || (resources = context.getResources()) == null) ? null : resources.getString(templateNameForAccessibility.intValue()));
            }
        }
        if (StringsKt.contains$default((CharSequence) clockTemplateItem.getTemplateRes(), (CharSequence) "doodle", false, 2, (Object) null)) {
            ActivityUIAdapter.Companion companion = ActivityUIAdapter.Companion;
            Context context2 = this.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            if (companion.isLightTheme(context2)) {
                TemplateDataUtil templateDataUtil2 = TemplateDataUtil.INSTANCE;
                Context context3 = this.itemView.getContext();
                Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                iFindDrawableResIdByName = templateDataUtil2.findDrawableResIdByName(context3, clockTemplateItem.getTemplateRes());
            } else {
                TemplateDataUtil templateDataUtil3 = TemplateDataUtil.INSTANCE;
                Context context4 = this.itemView.getContext();
                Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
                iFindDrawableResIdByName = templateDataUtil3.findDrawableResIdByName(context4, clockTemplateItem.getTemplateRes() + "_night");
            }
        } else {
            TemplateDataUtil templateDataUtil4 = TemplateDataUtil.INSTANCE;
            Context context5 = this.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
            iFindDrawableResIdByName = templateDataUtil4.findDrawableResIdByName(context5, clockTemplateItem.getTemplateRes());
        }
        if (iFindDrawableResIdByName > 0) {
            this.templateImageView.setImageDrawable(ResourcesCompat.getDrawable(this.itemView.getResources(), iFindDrawableResIdByName, this.itemView.getContext().getTheme()));
            this.templateImageView.setImportantForAccessibility(2);
        }
    }
}
