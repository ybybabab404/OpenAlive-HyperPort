package com.miui.keyguard.editor.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.EditCallback;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: ClockTemplatePagerAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClockTemplatePagerAdapter extends ListAdapter<ClockTemplateRow, ClockTemplatesViewHolder> {
    public static final Companion Companion = new Companion(null);
    public static final DiffUtil.ItemCallback diffCallback = new DiffUtil.ItemCallback<ClockTemplateRow>() { // from class: com.miui.keyguard.editor.view.ClockTemplatePagerAdapter$Companion$diffCallback$1
        @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
        public boolean areContentsTheSame(ClockTemplateRow oldItem, ClockTemplateRow newItem) {
            Intrinsics.checkNotNullParameter(oldItem, "oldItem");
            Intrinsics.checkNotNullParameter(newItem, "newItem");
            return Intrinsics.areEqual(oldItem, newItem);
        }

        @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
        public boolean areItemsTheSame(ClockTemplateRow oldItem, ClockTemplateRow newItem) {
            Intrinsics.checkNotNullParameter(oldItem, "oldItem");
            Intrinsics.checkNotNullParameter(newItem, "newItem");
            return oldItem.getTitleId() == newItem.getTitleId();
        }
    };
    public final Function2 clickCallback;
    public final Map clockTemplateAdapterMap;
    public int lastSelectedColumn;
    public ClockTemplateItem lastSelectedItem;
    public int lastSelectedRow;

    /* JADX INFO: compiled from: ClockTemplatePagerAdapter.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockTemplatePagerAdapter(final EditCallback editCallback, ClockTemplateItem clockTemplateItem) {
        super(diffCallback);
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        this.lastSelectedRow = clockTemplateItem != null ? clockTemplateItem.getRowIndex() : -1;
        this.lastSelectedColumn = clockTemplateItem != null ? clockTemplateItem.getColumnIndex() : -1;
        this.lastSelectedItem = clockTemplateItem;
        this.clockTemplateAdapterMap = new LinkedHashMap();
        this.clickCallback = new Function2() { // from class: com.miui.keyguard.editor.view.ClockTemplatePagerAdapter$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ClockTemplatePagerAdapter.clickCallback$lambda$0(this.f$0, editCallback, (ClockTemplateItem) obj, ((Integer) obj2).intValue());
            }
        };
    }

    public static final Unit clickCallback$lambda$0(ClockTemplatePagerAdapter clockTemplatePagerAdapter, EditCallback editCallback, ClockTemplateItem clockTemplateItem, int i) {
        Intrinsics.checkNotNullParameter(clockTemplateItem, "clockTemplateItem");
        clockTemplatePagerAdapter.updateSelectedItem(clockTemplateItem);
        editCallback.onEdited(200, clockTemplateItem);
        return Unit.INSTANCE;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ClockTemplatesViewHolder holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        this.clockTemplateAdapterMap.put(Integer.valueOf(i), holder.getClockTemplateAdapter());
        Object item = getItem(i);
        Intrinsics.checkNotNullExpressionValue(item, "getItem(...)");
        holder.bindClockTemplatesView((ClockTemplateRow) item);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ClockTemplatesViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_clock_template_view, parent, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        return new ClockTemplatesViewHolder(viewInflate, this.clickCallback);
    }

    public final void updateSelectedItem(ClockTemplateItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        ClockTemplateItem clockTemplateItem = this.lastSelectedItem;
        Integer numValueOf = clockTemplateItem != null ? Integer.valueOf(clockTemplateItem.getRowIndex()) : null;
        ClockTemplateItem clockTemplateItem2 = this.lastSelectedItem;
        Integer numValueOf2 = clockTemplateItem2 != null ? Integer.valueOf(clockTemplateItem2.getColumnIndex()) : null;
        Log.d("ClockTemplatePagerAdapter", "updateSelectedItem: lastSelected: " + numValueOf + " " + numValueOf2 + ", newSelected: " + item.getRowIndex() + " " + item.getColumnIndex());
        ClockTemplateItem clockTemplateItem3 = this.lastSelectedItem;
        if (clockTemplateItem3 == null || Intrinsics.areEqual(clockTemplateItem3, item)) {
            return;
        }
        ClockTemplateItem clockTemplateItem4 = this.lastSelectedItem;
        if (clockTemplateItem4 != null) {
            clockTemplateItem4.setSelected(false);
        }
        ClockTemplateAdapter clockTemplateAdapter = (ClockTemplateAdapter) this.clockTemplateAdapterMap.get(Integer.valueOf(this.lastSelectedRow));
        if (clockTemplateAdapter != null) {
            clockTemplateAdapter.notifyItemChanged(this.lastSelectedColumn);
        }
        item.setSelected(true);
        this.lastSelectedItem = item;
        this.lastSelectedRow = item.getRowIndex();
        this.lastSelectedColumn = item.getColumnIndex();
        ClockTemplateAdapter clockTemplateAdapter2 = (ClockTemplateAdapter) this.clockTemplateAdapterMap.get(Integer.valueOf(this.lastSelectedRow));
        if (clockTemplateAdapter2 != null) {
            clockTemplateAdapter2.notifyItemChanged(this.lastSelectedColumn);
        }
    }
}
