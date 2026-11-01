package com.miui.keyguard.editor.guidance;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.smooth.SmoothFrameLayout2;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: GuidanceAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class GuidanceAdapter extends RecyclerView.Adapter<GuidanceViewHolder> {
    public final List data;
    public final float videoLandscapeScale;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.data.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(GuidanceViewHolder holder, int i) throws IOException {
        Intrinsics.checkNotNullParameter(holder, "holder");
        holder.onBind((GuidanceItem) this.data.get(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public GuidanceViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_layout_guidance_item, parent, false);
        View viewFindViewById = viewInflate.findViewById(R.id.video_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        View viewFindViewById2 = viewInflate.findViewById(R.id.video_mask);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        setupVideoContainerSize((FrameLayout) viewFindViewById, (SmoothFrameLayout2) viewFindViewById2);
        Intrinsics.checkNotNull(viewInflate);
        return new GuidanceViewHolder(viewInflate);
    }

    public final void setupVideoContainerSize(FrameLayout frameLayout, SmoothFrameLayout2 smoothFrameLayout2) {
        Resources resources = frameLayout.getResources();
        float dimension = resources.getDimension(R.dimen.kg_guidance_video_container_width);
        float dimension2 = resources.getDimension(R.dimen.kg_guidance_video_container_height);
        float dimension3 = resources.getDimension(R.dimen.kg_guidance_video_mask_width);
        float dimension4 = resources.getDimension(R.dimen.kg_guidance_video_mask_height);
        float dimension5 = resources.getDimension(R.dimen.kg_guidance_video_radius);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (!deviceUtil.isJ18() || !deviceUtil.isPhone()) {
            Context context = frameLayout.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (deviceUtil.isLandScape(context)) {
                float f = this.videoLandscapeScale;
                dimension *= f;
                dimension2 *= f;
                dimension3 *= f;
                dimension4 *= f;
                dimension5 *= f;
            }
        }
        ViewGroup.LayoutParams layoutParams = smoothFrameLayout2.getLayoutParams();
        layoutParams.width = (int) dimension3;
        layoutParams.height = (int) dimension4;
        smoothFrameLayout2.setLayoutParams(layoutParams);
        smoothFrameLayout2.setCornerRadius(dimension5);
        ViewGroup.LayoutParams layoutParams2 = frameLayout.getLayoutParams();
        layoutParams2.width = (int) dimension;
        layoutParams2.height = (int) dimension2;
        frameLayout.setLayoutParams(layoutParams2);
    }
}
