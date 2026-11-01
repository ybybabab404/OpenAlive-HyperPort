package com.miui.keyguard.editor.edit.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.utils.ClickUtil;
import com.miui.keyguard.editor.view.FilterIconLayout;
import java.util.ArrayList;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: StyleSelectorAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class StyleSelectorAdapter<T> extends RecyclerView.Adapter<ItemViewHolder> {
    public final List data;
    public int selectedIndex;
    public View selectedItemView;

    /* JADX INFO: compiled from: StyleSelectorAdapter.kt */
    @Metadata
    public static final class ItemViewHolder extends RecyclerView.ViewHolder {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ItemViewHolder(View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
        }
    }

    public StyleSelectorAdapter(List data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.selectedIndex = -1;
        this.data = new ArrayList(data);
    }

    public static final void onBindViewHolder$lambda$0(int i, StyleSelectorAdapter styleSelectorAdapter, View view) {
        ClickUtil clickUtil = ClickUtil.INSTANCE;
        clickUtil.getEditButtonLock().compareAndSet(clickUtil.getBUTTON_FREE(), i);
        styleSelectorAdapter.onItemSelect(view, i);
        clickUtil.getEditButtonLock().compareAndSet(i, clickUtil.getBUTTON_FREE());
    }

    public final List getData() {
        return this.data;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.data.size();
    }

    public final int getSelectedIndex() {
        return this.selectedIndex;
    }

    public abstract int itemLayoutId();

    public void onBind(View view, int i) {
        if (view == null) {
            return;
        }
        boolean z = i == this.selectedIndex;
        if (view instanceof FilterIconLayout) {
            ((FilterIconLayout) view).changeSelect(z, false);
        } else {
            view.setSelected(z);
        }
        if (z) {
            this.selectedItemView = view;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ItemViewHolder viewHolder, final int i) {
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        onBind(viewHolder.itemView, i);
        viewHolder.setIsRecyclable(false);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.view.StyleSelectorAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StyleSelectorAdapter.onBindViewHolder$lambda$0(i, this, view);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId(), parent, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        return new ItemViewHolder(viewInflate);
    }

    public void onItemSelect(View view, int i) {
        if (i == this.selectedIndex) {
            return;
        }
        if (!(view instanceof FilterIconLayout)) {
            View view2 = this.selectedItemView;
            if (view2 != null) {
                view2.setSelected(false);
            }
            this.selectedItemView = view;
            if (view != null) {
                view.setSelected(true);
            }
            this.selectedIndex = i;
            return;
        }
        ((FilterIconLayout) view).changeSelect(true, true);
        View view3 = this.selectedItemView;
        if (view3 != null) {
            if (view3 instanceof FilterIconLayout) {
                ((FilterIconLayout) view3).changeSelect(false, true);
            } else {
                view3.setSelected(false);
            }
        }
        this.selectedItemView = view;
        this.selectedIndex = i;
    }

    public final void setSelectedIndex(int i) {
        this.selectedIndex = i;
    }
}
