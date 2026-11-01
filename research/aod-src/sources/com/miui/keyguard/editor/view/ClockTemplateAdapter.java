package com.miui.keyguard.editor.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.miui.keyguard.editor.R;
import kotlin.Metadata;
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
public final class ClockTemplateAdapter extends ListAdapter<ClockTemplateItem, ClockTemplateItemViewHolder> {
    public static final Companion Companion = new Companion(null);
    public static final DiffUtil.ItemCallback diffCallback = new DiffUtil.ItemCallback<ClockTemplateItem>() { // from class: com.miui.keyguard.editor.view.ClockTemplateAdapter$Companion$diffCallback$1
        @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
        public boolean areContentsTheSame(ClockTemplateItem oldItem, ClockTemplateItem newItem) {
            Intrinsics.checkNotNullParameter(oldItem, "oldItem");
            Intrinsics.checkNotNullParameter(newItem, "newItem");
            return Intrinsics.areEqual(oldItem, newItem);
        }

        @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
        public boolean areItemsTheSame(ClockTemplateItem oldItem, ClockTemplateItem newItem) {
            Intrinsics.checkNotNullParameter(oldItem, "oldItem");
            Intrinsics.checkNotNullParameter(newItem, "newItem");
            return Intrinsics.areEqual(oldItem.getTemplateRes(), newItem.getTemplateRes());
        }
    };
    public final Function2 clickCallback;
    public int templatesTitleRes;

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
    public ClockTemplateAdapter(Function2 clickCallback) {
        super(diffCallback);
        Intrinsics.checkNotNullParameter(clickCallback, "clickCallback");
        this.clickCallback = clickCallback;
        this.templatesTitleRes = -1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ClockTemplateItemViewHolder holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Object item = getItem(i);
        Intrinsics.checkNotNullExpressionValue(item, "getItem(...)");
        holder.bindTemplateItemView((ClockTemplateItem) item, this.clickCallback, this.templatesTitleRes);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ClockTemplateItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_clock_template_item_view, parent, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        return new ClockTemplateItemViewHolder(viewInflate);
    }

    public final void setTitleRes(int i) {
        this.templatesTitleRes = i;
    }
}
