package com.miui.keyguard.editor.guidance;

import android.util.Log;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.miui.keyguard.editor.R;
import kotlin.Metadata;
import miuix.appcompat.app.AlertDialog;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: GuidancePagerListener.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class GuidancePagerListener extends ViewPager2.OnPageChangeCallback {
    public final AlertDialog guidanceDialog;
    public final LinearLayoutCompat indicator;
    public final int pagerLastIndex;
    public final RecyclerView recyclerView;
    public int selectedPosition;
    public final String tag;

    public final GuidanceViewHolder findViewHolder(int i) {
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.recyclerView.findViewHolderForAdapterPosition(i);
        if (viewHolderFindViewHolderForAdapterPosition instanceof GuidanceViewHolder) {
            return (GuidanceViewHolder) viewHolderFindViewHolderForAdapterPosition;
        }
        return null;
    }

    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
    public void onPageScrollStateChanged(int i) {
        GuidanceViewHolder guidanceViewHolderFindViewHolder;
        super.onPageScrollStateChanged(i);
        int i2 = this.selectedPosition;
        if (i2 != -1) {
            if (i != 0) {
                if (i == 1 && (guidanceViewHolderFindViewHolder = findViewHolder(i2)) != null) {
                    guidanceViewHolderFindViewHolder.stop();
                    return;
                }
                return;
            }
            Log.i(this.tag, "onPageScrollStateChanged selectedPosition " + i2);
            GuidanceViewHolder guidanceViewHolderFindViewHolder2 = findViewHolder(this.selectedPosition);
            if (guidanceViewHolderFindViewHolder2 != null) {
                guidanceViewHolderFindViewHolder2.play();
            }
        }
    }

    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
    public void onPageSelected(int i) {
        int i2 = this.selectedPosition;
        if (i2 != -1) {
            this.indicator.getChildAt(i2).setSelected(false);
            GuidanceViewHolder guidanceViewHolderFindViewHolder = findViewHolder(this.selectedPosition);
            if (guidanceViewHolderFindViewHolder != null) {
                guidanceViewHolderFindViewHolder.stop();
            }
        } else {
            GuidanceViewHolder guidanceViewHolderFindViewHolder2 = findViewHolder(i);
            if (guidanceViewHolderFindViewHolder2 != null) {
                guidanceViewHolderFindViewHolder2.play();
            }
        }
        this.indicator.getChildAt(i).setSelected(true);
        this.guidanceDialog.getButton(-1).setText(i < this.pagerLastIndex ? R.string.kg_guidance_dialog_positive_button_text_next_step : R.string.kg_guidance_dialog_positive_button_text_start);
        this.selectedPosition = i;
    }
}
