package com.miui.keyguard.editor.homepage.view.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.view.FontFilterViewStatus;
import com.miui.keyguard.editor.view.WallpaperFilterSelectItemCallback;
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
public final class FilterTitleAdapter extends RecyclerView.Adapter<FilterTitleViewHolder> {
    public final Typeface boldFont;
    public int currentTitle;
    public final FontFilterViewStatus filterViewStatus;
    public final boolean glassDisabled;
    public int lastTitleResId;
    public final WallpaperFilterSelectItemCallback listener;
    public final Typeface normalFont;
    public final boolean overlapDisable;
    public final int selectColor;
    public List titleResData;
    public final int unselectColor;
    public View viewForMeasure;

    public static final void onBindViewHolder$lambda$1(FilterTitleAdapter filterTitleAdapter, int i, View view) {
        FontFilterViewStatus fontFilterViewStatus = filterTitleAdapter.filterViewStatus;
        if ((fontFilterViewStatus == null || !fontFilterViewStatus.getColorViewPagerAnimStatus()) && i != filterTitleAdapter.currentTitle) {
            filterTitleAdapter.currentTitle = i;
            WallpaperFilterSelectItemCallback.DefaultImpls.onSelectItem$default(filterTitleAdapter.listener, i, view, false, 4, null);
            filterTitleAdapter.notifyDataSetChanged();
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
        View itemView = holder.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        int iIntValue = ((Number) this.titleResData.get(i)).intValue();
        TextView textView = (TextView) itemView.findViewById(R.id.filter_title);
        if ((this.overlapDisable && R.string.kg_font_filter_group_label_overlap == iIntValue) || (this.glassDisabled && R.string.kg_font_filter_group_label_glass == iIntValue)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
        }
        textView.setTypeface(this.normalFont);
        textView.setTextColor(this.unselectColor);
        int i2 = this.currentTitle;
        if (i == i2) {
            textView.setTypeface(this.boldFont);
            textView.setTextColor(this.selectColor);
            if (this.lastTitleResId == -1) {
                this.lastTitleResId = iIntValue;
            }
        } else if (iIntValue == this.lastTitleResId) {
            this.lastTitleResId = ((Number) this.titleResData.get(i2)).intValue();
        }
        textView.setText(((Number) this.titleResData.get(i)).intValue());
        itemView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.view.adapter.FilterTitleAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FilterTitleAdapter.onBindViewHolder$lambda$1(this.f$0, i, view);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public FilterTitleViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (this.viewForMeasure == null) {
            this.viewForMeasure = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_filter_title_item, (ViewGroup) null, false);
        }
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_filter_title_item, parent, false);
        Intrinsics.checkNotNull(viewInflate);
        return new FilterTitleViewHolder(viewInflate);
    }
}
