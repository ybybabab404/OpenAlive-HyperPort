package com.miui.keyguard.editor.history.common;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.history.bean.HistoryItemBean;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.utils.CommonUtils;
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
/* JADX INFO: compiled from: BindAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class BindAdapter {
    public static final BindAdapter INSTANCE = new BindAdapter();

    public static final void updateMask(AppCompatImageView view, HistoryItemBean historyItemBean) {
        TemplateItemBean templateItemBean;
        TemplateHistoryConfig history;
        Intrinsics.checkNotNullParameter(view, "view");
        CommonUtils commonUtils = CommonUtils.INSTANCE;
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        boolean z = false;
        if (historyItemBean != null && (templateItemBean = historyItemBean.getTemplateItemBean()) != null && (history = templateItemBean.getHistory()) != null && history.isDarkWallpaper() == 1) {
            z = true;
        }
        view.setImageResource(commonUtils.obtainHomeLayoutDrawable(context, z));
    }

    public static final void updateStatus(AppCompatTextView view, HistoryItemBean historyItemBean, boolean z, boolean z2) {
        int i;
        int i2;
        TemplateItemBean templateItemBean;
        Intrinsics.checkNotNullParameter(view, "view");
        if (historyItemBean != null && historyItemBean.getStatus() == 1) {
            int dimensionPixelSize = view.getResources().getDimensionPixelSize(R.dimen.kg_editor_history_item_state_padding_left);
            int dimensionPixelSize2 = view.getResources().getDimensionPixelSize(R.dimen.kg_editor_history_item_state_padding_right);
            view.setGravity(21);
            view.setPadding(dimensionPixelSize, 0, dimensionPixelSize2, 0);
            view.setText(view.getResources().getText(R.string.kg_settings_apply_loading));
            view.setTextColor(ContextCompat.getColor(view.getContext(), z ? R.color.kg_white_50 : R.color.kg_black_60));
            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            view.setBackgroundResource(z ? R.drawable.kg_editor_history_item_btn_bg_dark : R.drawable.kg_editor_history_item_btn_bg_light);
            view.setImportantForAccessibility(2);
            return;
        }
        if (historyItemBean != null && historyItemBean.getStatus() == 2) {
            view.setGravity(17);
            view.setPadding(0, 0, 0, 0);
            view.setText(view.getResources().getText(R.string.kg_settings_template_using));
            view.setTextColor(ContextCompat.getColor(view.getContext(), z ? R.color.kg_white_50 : R.color.kg_black_60));
            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            view.setBackgroundResource(0);
            view.setImportantForAccessibility(2);
            return;
        }
        view.setGravity(17);
        view.setPadding(0, 0, 0, 0);
        view.setText(!z2 ? view.getResources().getText(R.string.kg_apply) : "");
        view.setContentDescription(view.getResources().getText(R.string.kg_apply));
        view.setTextColor(ContextCompat.getColor(view.getContext(), z ? R.color.kg_white_80 : R.color.kg_black_80));
        if (z2 || !z) {
            i = (z2 || z) ? 0 : R.drawable.kg_editor_history_item_btn_bg_light;
        } else {
            i = R.drawable.kg_editor_history_item_btn_bg_dark;
        }
        view.setBackgroundResource(i);
        view.setImportantForAccessibility(0);
        if (!z2 || historyItemBean == null || (templateItemBean = historyItemBean.getTemplateItemBean()) == null || templateItemBean.getItemType() != 2) {
            i2 = 0;
        } else if ((historyItemBean == null || !historyItemBean.getSelected()) && !z) {
            view.setContentDescription(view.getResources().getText(R.string.kg_talkback_not_selected));
            i2 = R.drawable.kg_icon_history_unchecked_light;
        } else if ((historyItemBean == null || !historyItemBean.getSelected()) && z) {
            view.setContentDescription(view.getResources().getText(R.string.kg_talkback_not_selected));
            i2 = R.drawable.kg_icon_history_unchecked_dark;
        } else {
            view.setContentDescription(view.getResources().getText(R.string.kg_talkback_selected));
            i2 = R.drawable.kg_icon_history_checked_light;
        }
        view.setCompoundDrawablesWithIntrinsicBounds(0, i2, 0, 0);
    }
}
