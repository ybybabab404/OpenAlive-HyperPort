package com.miui.keyguard.editor.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.homepage.view.adapter.BaseAdapter;
import com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder;
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
/* JADX INFO: compiled from: SettingsTemplateAdapterAndHoldImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SettingsTemplateAdapter extends BaseAdapter<TemplateItemBean, CrossViewPagerCallback> {
    public SettingsTemplateDataModelProvider dataModelProvider;

    public SettingsTemplateAdapter(SettingsTemplateDataModelProvider settingsTemplateDataModelProvider) {
        super(0);
        this.dataModelProvider = settingsTemplateDataModelProvider;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseAdapter
    public BaseViewHolder createMyViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (i == -1) {
            View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_settings_item_template_negative_card_pager, parent, false);
            Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
            return new SettingsNegativeViewHolder(viewInflate);
        }
        if (i != 4) {
            if (i == 1) {
                SettingsTemplateDataModelProvider settingsTemplateDataModelProvider = this.dataModelProvider;
                View viewInflate2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_settings_item_template_pager, parent, false);
                Intrinsics.checkNotNullExpressionValue(viewInflate2, "inflate(...)");
                return new SettingsMyTemplateViewHolder(settingsTemplateDataModelProvider, viewInflate2);
            }
            if (i != 2) {
                throw new IllegalArgumentException("Invalid view type");
            }
        }
        SettingsTemplateDataModelProvider settingsTemplateDataModelProvider2 = this.dataModelProvider;
        View viewInflate3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_settings_item_template_pager, parent, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate3, "inflate(...)");
        return new TemplateHistoryViewHolder(settingsTemplateDataModelProvider2, viewInflate3);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        TemplateItemBean templateItemBean = (TemplateItemBean) getItem(i);
        if (templateItemBean != null) {
            return templateItemBean.hashCode();
        }
        return 0L;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        TemplateItemBean templateItemBean = (TemplateItemBean) getItem(i);
        if (templateItemBean != null) {
            return templateItemBean.getItemType();
        }
        return -1;
    }
}
