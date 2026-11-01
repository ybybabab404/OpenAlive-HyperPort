package com.miui.keyguard.editor.homepage.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import com.miui.keyguard.editor.homepage.bean.FloorItemBean;
import com.miui.keyguard.editor.homepage.view.viewpager.DampViewPager2;
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
/* JADX INFO: compiled from: AdapterAndHolderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class PreviewFloorViewHolder extends FloorViewHolder<PreviewTemplateAdapter> {
    public final ViewGroup touchLayout;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PreviewFloorViewHolder(ViewGroup container, DampViewPager2 pager2) {
        super(container, pager2);
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(pager2, "pager2");
        this.touchLayout = container;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.FloorViewHolder, com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderCreated(View itemView) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        super.onViewHolderCreated(itemView);
        PreviewTemplateAdapter previewTemplateAdapter = new PreviewTemplateAdapter();
        previewTemplateAdapter.setCallback(getCallback());
        setPagerAdapter(previewTemplateAdapter);
        getViewPager().setAdapter(getPagerAdapter());
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.FloorViewHolder
    public void selectTargetPosition(FloorItemBean floorItemBean) {
        if (floorItemBean == null) {
            return;
        }
        int selectPosition = -1;
        if (floorItemBean.getSelectPosition() == -1) {
            PreviewTemplateAdapter previewTemplateAdapter = (PreviewTemplateAdapter) getPagerAdapter();
            if (previewTemplateAdapter != null) {
                selectPosition = previewTemplateAdapter.getInitialPosition();
            }
        } else {
            selectPosition = floorItemBean.getSelectPosition();
        }
        PreviewTemplateAdapter previewTemplateAdapter2 = (PreviewTemplateAdapter) getPagerAdapter();
        int itemCount = previewTemplateAdapter2 != null ? previewTemplateAdapter2.getItemCount() : 0;
        if (selectPosition < 0 || selectPosition > itemCount - 1) {
            return;
        }
        getViewPager().setCurrentItem(selectPosition, false);
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.FloorViewHolder
    public void setFloorSelected(boolean z) {
        super.setFloorSelected(z);
        this.touchLayout.setSelected(z);
    }
}
