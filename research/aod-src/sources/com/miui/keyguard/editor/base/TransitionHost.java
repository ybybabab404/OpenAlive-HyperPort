package com.miui.keyguard.editor.base;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.homepage.MainFragment;
import com.miui.keyguard.editor.homepage.util.TitleDescController;
import com.miui.keyguard.editor.homepage.view.CrossViewPager;
import com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder;
import com.miui.keyguard.editor.utils.ScenarioRecognitionUtils;
import com.miui.keyguard.editor.utils.ViewUtil;
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
/* JADX INFO: compiled from: Transition.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TransitionHost {
    public final MainFragment host;
    public final int x;
    public final int y;

    public TransitionHost(MainFragment host, int i, int i2) {
        Intrinsics.checkNotNullParameter(host, "host");
        this.host = host;
        this.x = i;
        this.y = i2;
    }

    public static final void performForwardTransition$lambda$0(CrossViewPager crossViewPager, TransitionHost transitionHost) {
        View view;
        RecyclerView.ViewHolder viewHolder = crossViewPager.getViewHolder(transitionHost.x, transitionHost.y);
        if (viewHolder == null || (view = viewHolder.itemView) == null) {
            return;
        }
        view.setAlpha(0.0f);
    }

    public final Rect getHomePreviewRect() {
        Rect rect = new Rect();
        Object viewHolder = this.host.getViewPaper().getViewHolder(this.x, this.y);
        PreviewHolder previewHolder = viewHolder instanceof PreviewHolder ? (PreviewHolder) viewHolder : null;
        View homePreview = previewHolder != null ? previewHolder.getHomePreview() : null;
        if (homePreview == null) {
            return null;
        }
        ViewUtil.INSTANCE.getShowRect(homePreview, rect);
        return rect;
    }

    public final Rect getLockPreviewRect() {
        Rect rect = new Rect();
        Object viewHolder = this.host.getViewPaper().getViewHolder(this.x, this.y);
        if (viewHolder instanceof PreviewHolder) {
            ((PreviewHolder) viewHolder).getLockPreview().getGlobalVisibleRect(rect);
        }
        return rect;
    }

    public final void onBackwardTransitionEnd() {
        View view;
        RecyclerView.ViewHolder viewHolder = this.host.getViewPaper().getViewHolder(this.x, this.y);
        if (viewHolder != null && (view = viewHolder.itemView) != null) {
            view.setAlpha(1.0f);
        }
        this.host.getViewPaper().logSelectedItemState("onBackwardTransitionEnd", this.x, this.y);
        ScenarioRecognitionUtils.INSTANCE.setScenarioStateEnd(445L);
    }

    public final void onForwardTransitionEnd() {
        if (!this.host.isAdded() || this.host.isDetached() || this.host.getParentFragmentManager().isStateSaved()) {
            return;
        }
        this.host.getParentFragmentManager().beginTransaction().hide(this.host).commitNow();
        this.host.setInEnterEditFragment(false);
        ScenarioRecognitionUtils.INSTANCE.setScenarioStateEnd(444L);
    }

    public final void performBackwardTransition() {
        ScenarioRecognitionUtils.INSTANCE.setScenarioStateStart(445L);
        TitleDescController currentFloorTitleDescController = this.host.getCurrentFloorTitleDescController();
        if (currentFloorTitleDescController != null) {
            currentFloorTitleDescController.performBackwardTransition();
        }
    }

    public final void performForwardTransition(TransitionPerformer transitionPerformer) {
        Intrinsics.checkNotNullParameter(transitionPerformer, "transitionPerformer");
        ScenarioRecognitionUtils.INSTANCE.setScenarioStateStart(444L);
        TitleDescController currentFloorTitleDescController = this.host.getCurrentFloorTitleDescController();
        if (currentFloorTitleDescController != null) {
            currentFloorTitleDescController.performForwardTransition();
        }
        final CrossViewPager viewPaper = this.host.getViewPaper();
        viewPaper.post(new Runnable() { // from class: com.miui.keyguard.editor.base.TransitionHost$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TransitionHost.performForwardTransition$lambda$0(viewPaper, this);
            }
        });
        RecyclerView.ViewHolder viewHolder = viewPaper.getViewHolder(this.x - 1, this.y);
        View view = viewHolder != null ? viewHolder.itemView : null;
        RecyclerView.ViewHolder viewHolder2 = viewPaper.getViewHolder(this.x + 1, this.y);
        View view2 = viewHolder2 != null ? viewHolder2.itemView : null;
        RecyclerView.ViewHolder selectedViewHolderForFloor = viewPaper.getSelectedViewHolderForFloor(this.y + 1);
        transitionPerformer.performSlideOut(view, view2, selectedViewHolderForFloor != null ? selectedViewHolderForFloor.itemView : null);
    }
}
