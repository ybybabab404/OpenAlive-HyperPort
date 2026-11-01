package com.miui.keyguard.editor.homepage.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.ClickUtil;
import com.miui.keyguard.editor.view.FilterIconLayout;
import com.miui.keyguard.editor.view.FontFilterSelectItemCallback;
import com.miui.keyguard.editor.view.FontFilterViewStatus;
import java.util.List;
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
public final class FontFilterTitleAdapter extends RecyclerView.Adapter<FilterTitleViewHolder> {
    public int currentTitleIndex;
    public final FontFilterViewStatus filterViewStatus;
    public boolean glassDisabled;
    public int lastTitleResId;
    public final FontFilterSelectItemCallback listener;
    public final boolean overlapDisable;
    public FilterIconLayout selectedView;
    public List titleResData;
    public View viewForMeasure;

    public FontFilterTitleAdapter(List titleResData, int i, FontFilterSelectItemCallback listener, FontFilterViewStatus fontFilterViewStatus, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(titleResData, "titleResData");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.titleResData = titleResData;
        this.currentTitleIndex = i;
        this.listener = listener;
        this.filterViewStatus = fontFilterViewStatus;
        this.overlapDisable = z;
        this.glassDisabled = z2;
        this.lastTitleResId = -1;
    }

    public static final void onBindViewHolder$lambda$1(FontFilterTitleAdapter fontFilterTitleAdapter, int i, FilterIconLayout filterIconLayout, View view) {
        FontFilterViewStatus fontFilterViewStatus = fontFilterTitleAdapter.filterViewStatus;
        if ((fontFilterViewStatus == null || !fontFilterViewStatus.getColorViewPagerAnimStatus()) && fontFilterTitleAdapter.currentTitleIndex != i && ClickUtil.INSTANCE.isClickProcessing().compareAndSet(false, true)) {
            filterIconLayout.changeSelect(true, true);
            FilterIconLayout filterIconLayout2 = fontFilterTitleAdapter.selectedView;
            if (filterIconLayout2 != null) {
                filterIconLayout2.changeSelect(false, true);
            }
            fontFilterTitleAdapter.selectedView = filterIconLayout;
            fontFilterTitleAdapter.currentTitleIndex = i;
            fontFilterTitleAdapter.listener.onSelectItem(i, view);
            fontFilterTitleAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.titleResData.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(FilterTitleViewHolder holder, final int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        View view = holder.itemView;
        Intrinsics.checkNotNull(view, "null cannot be cast to non-null type com.miui.keyguard.editor.view.FilterIconLayout");
        final FilterIconLayout filterIconLayout = (FilterIconLayout) view;
        int iIntValue = ((Number) this.titleResData.get(i)).intValue();
        Button button = (Button) filterIconLayout.findViewById(R.id.filter_title);
        if ((this.overlapDisable && R.string.kg_font_filter_group_label_overlap == iIntValue) || (this.glassDisabled && R.string.kg_font_filter_group_label_glass == iIntValue)) {
            button.setVisibility(8);
        } else {
            button.setVisibility(0);
        }
        if (i != this.currentTitleIndex) {
            filterIconLayout.changeSelect(false, false);
        } else if (this.lastTitleResId == -1) {
            this.selectedView = filterIconLayout;
            this.lastTitleResId = iIntValue;
            filterIconLayout.changeSelect(true, false);
        }
        button.setText(((Number) this.titleResData.get(i)).intValue());
        button.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.view.adapter.FontFilterTitleAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                FontFilterTitleAdapter.onBindViewHolder$lambda$1(this.f$0, i, filterIconLayout, view2);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public FilterTitleViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (this.viewForMeasure == null) {
            this.viewForMeasure = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_filter_font_effect_title_item, (ViewGroup) null, false);
        }
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_filter_font_effect_title_item, parent, false);
        Intrinsics.checkNotNull(viewInflate);
        return new FilterTitleViewHolder(viewInflate);
    }

    public final void updateTitleIndex(int i) {
        this.currentTitleIndex = i;
        this.lastTitleResId = -1;
        notifyDataSetChanged();
    }
}
