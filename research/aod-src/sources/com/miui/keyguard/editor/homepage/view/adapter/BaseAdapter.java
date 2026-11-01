package com.miui.keyguard.editor.homepage.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
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
/* JADX INFO: compiled from: BaseAdapterAndHolder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class BaseAdapter<DataType, Callback> extends RecyclerView.Adapter<BaseViewHolder<DataType, Callback>> {
    public Object mCallback;
    public LayoutInflater mInflater;
    public final int orientation;
    public final Lazy mData$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.view.adapter.BaseAdapter$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return BaseAdapter.mData_delegate$lambda$0();
        }
    });
    public int mY = -1;

    public BaseAdapter(int i) {
        this.orientation = i;
    }

    public static final List mData_delegate$lambda$0() {
        return new ArrayList();
    }

    public abstract BaseViewHolder createMyViewHolder(ViewGroup viewGroup, int i);

    public final List getData() {
        return getMData();
    }

    public Object getItem(int i) {
        if (i < 0 || i > getItemCount() - 1) {
            return null;
        }
        return getMData().get(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return getMData().size();
    }

    public final int getItemRealCount() {
        return getMData().size();
    }

    public final LayoutInflater getLayoutInflater(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(context);
        }
        LayoutInflater layoutInflater = this.mInflater;
        Intrinsics.checkNotNull(layoutInflater);
        return layoutInflater;
    }

    public final List getMData() {
        return (List) this.mData$delegate.getValue();
    }

    public final int getY() {
        return this.mY;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseViewHolder holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        int i2 = this.orientation;
        if (i2 == 0) {
            holder.setX(i);
            holder.setY(getY());
        } else if (i2 == 1) {
            holder.setY(i);
        }
        holder.itemView.setTag(R.id.kg_template_holder, holder);
        holder.onViewHolderBound(getItem(i), i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        BaseViewHolder baseViewHolderCreateMyViewHolder = createMyViewHolder(parent, i);
        baseViewHolderCreateMyViewHolder.setCallback(this.mCallback);
        View itemView = baseViewHolderCreateMyViewHolder.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        baseViewHolderCreateMyViewHolder.onViewHolderCreated(itemView);
        return baseViewHolderCreateMyViewHolder;
    }

    public final void setCallback(Object obj) {
        this.mCallback = obj;
    }

    public final boolean setData(List list) {
        getMData().clear();
        if (list == null || list.isEmpty()) {
            return false;
        }
        return getMData().addAll(list);
    }

    public final void setY(int i) {
        this.mY = i;
    }
}
