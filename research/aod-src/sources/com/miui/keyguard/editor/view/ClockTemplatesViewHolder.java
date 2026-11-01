package com.miui.keyguard.editor.view;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ClockTemplatePagerAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ClockTemplatesViewHolder extends RecyclerView.ViewHolder {
    public final ClockTemplateAdapter clockTemplateAdapter;
    public final RecyclerView recyclerView;
    public final TextView templatesTitle;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockTemplatesViewHolder(View itemView, Function2 clickCallback) {
        super(itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        Intrinsics.checkNotNullParameter(clickCallback, "clickCallback");
        View viewFindViewById = itemView.findViewById(R.id.clock_template_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.templatesTitle = (TextView) viewFindViewById;
        View viewFindViewById2 = itemView.findViewById(R.id.kg_clock_template_horizontal_recycler);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById2;
        this.recyclerView = recyclerView;
        ClockTemplateAdapter clockTemplateAdapter = new ClockTemplateAdapter(clickCallback);
        this.clockTemplateAdapter = clockTemplateAdapter;
        recyclerView.setAdapter(clockTemplateAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
    }

    public static final void bindClockTemplatesView$lambda$2(View view) {
    }

    public static final void bindClockTemplatesView$lambda$6$lambda$5(ClockTemplatesViewHolder clockTemplatesViewHolder, int i) {
        RecyclerView.LayoutManager layoutManager = clockTemplatesViewHolder.recyclerView.getLayoutManager();
        LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(i, clockTemplatesViewHolder.itemView.getResources().getDimensionPixelSize(R.dimen.kg_clock_template_recycler_view_margin_start));
        }
    }

    public final void bindClockTemplatesView(ClockTemplateRow clockTemplateRow) {
        Intrinsics.checkNotNullParameter(clockTemplateRow, "clockTemplateRow");
        this.templatesTitle.setText(this.itemView.getResources().getString(clockTemplateRow.getTitleId()));
        this.templatesTitle.getRootView().setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.view.ClockTemplatesViewHolder$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClockTemplatesViewHolder.bindClockTemplatesView$lambda$2(view);
            }
        });
        ClockTemplateAdapter clockTemplateAdapter = this.clockTemplateAdapter;
        clockTemplateAdapter.setTitleRes(clockTemplateRow.getTitleId());
        clockTemplateAdapter.submitList(clockTemplateRow.getTemplateList());
        Iterator it = clockTemplateRow.getTemplateList().iterator();
        final int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            } else if (((ClockTemplateItem) it.next()).isSelected()) {
                break;
            } else {
                i++;
            }
        }
        if (i != -1) {
            this.recyclerView.post(new Runnable() { // from class: com.miui.keyguard.editor.view.ClockTemplatesViewHolder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ClockTemplatesViewHolder.bindClockTemplatesView$lambda$6$lambda$5(this.f$0, i);
                }
            });
        }
    }

    public final ClockTemplateAdapter getClockTemplateAdapter() {
        return this.clockTemplateAdapter;
    }
}
