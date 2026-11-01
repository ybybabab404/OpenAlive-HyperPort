package com.miui.keyguard.shortcuts.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.shortcuts.R;
import com.miui.keyguard.shortcuts.utils.HapticFeedbackUtils;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.view.HapticFeedbackConstants;

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
/* JADX INFO: compiled from: ShortcutListAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ShortcutListRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    public final OnItemClickListener clickListener;
    public final List items;

    /* JADX INFO: compiled from: ShortcutListAdapter.kt */
    @Metadata
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView shortcutImage;
        public TextView shortcutText;
        public final /* synthetic */ ShortcutListRecyclerAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(ShortcutListRecyclerAdapter shortcutListRecyclerAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = shortcutListRecyclerAdapter;
            View viewFindViewById = itemView.findViewById(R.id.shortcutImage);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
            this.shortcutImage = (ImageView) viewFindViewById;
            View viewFindViewById2 = itemView.findViewById(R.id.shortcutText);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
            this.shortcutText = (TextView) viewFindViewById2;
            Folme.useAt(this.shortcutImage).touch().handleTouchOf(itemView, new AnimConfig[0]);
        }

        public final ImageView getShortcutImage() {
            return this.shortcutImage;
        }

        public final TextView getShortcutText() {
            return this.shortcutText;
        }
    }

    public ShortcutListRecyclerAdapter(List items, OnItemClickListener clickListener) {
        Intrinsics.checkNotNullParameter(items, "items");
        Intrinsics.checkNotNullParameter(clickListener, "clickListener");
        this.items = items;
        this.clickListener = clickListener;
    }

    public static final void onBindViewHolder$lambda$3$lambda$2$lambda$1(View view, ViewHolder viewHolder, ShortcutListRecyclerAdapter shortcutListRecyclerAdapter, ShortcutItem shortcutItem, View view2) {
        HapticFeedbackUtils hapticFeedbackUtils = HapticFeedbackUtils.INSTANCE;
        Intrinsics.checkNotNull(view);
        hapticFeedbackUtils.performHapticFeedback(view, HapticFeedbackConstants.MIUI_BUTTON_LARGE, HapticFeedbackConstants.MIUI_MESH_NORMAL, 150L);
        int absoluteAdapterPosition = viewHolder.getAbsoluteAdapterPosition();
        LogUtils.logI$default(LogUtils.INSTANCE, "ShortcutListRecyclerAdapter", "clickedPosition = " + absoluteAdapterPosition, null, 4, null);
        int i = -1;
        if (absoluteAdapterPosition != -1) {
            Iterator it = shortcutListRecyclerAdapter.items.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                if (((ShortcutItem) it.next()).isSelected()) {
                    i = i2;
                    break;
                }
                i2++;
            }
            if (i != absoluteAdapterPosition) {
                shortcutListRecyclerAdapter.notifyItemChanged(i);
                shortcutListRecyclerAdapter.notifyItemChanged(absoluteAdapterPosition);
            }
        }
        shortcutListRecyclerAdapter.clickListener.onItemClick(shortcutItem.getShortcutEntity().getIndex());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        final ShortcutItem shortcutItem = (ShortcutItem) this.items.get(i);
        holder.getShortcutImage().setImageDrawable(shortcutItem.getShortcutEntity().getDrawable());
        holder.getShortcutText().setText(shortcutItem.getShortcutEntity().getShortcutName());
        final View view = holder.itemView;
        view.setContentDescription(shortcutItem.getShortcutEntity().getShortcutName());
        view.setSelected(shortcutItem.isSelected());
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.shortcuts.list.ShortcutListRecyclerAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ShortcutListRecyclerAdapter.onBindViewHolder$lambda$3$lambda$2$lambda$1(view, holder, this, shortcutItem, view2);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.keyguard_item_shortcut, parent, false);
        Intrinsics.checkNotNull(viewInflate);
        return new ViewHolder(this, viewInflate);
    }
}
