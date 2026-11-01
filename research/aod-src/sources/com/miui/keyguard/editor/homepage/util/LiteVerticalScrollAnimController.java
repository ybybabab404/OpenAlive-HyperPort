package com.miui.keyguard.editor.homepage.util;

import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.homepage.view.CrossViewPager;
import com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.FloorViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder;
import com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;

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
/* JADX INFO: compiled from: LiteVerticalScrollAnimController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class LiteVerticalScrollAnimController extends BaseVerticalScrollAnimController {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: LiteVerticalScrollAnimController.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LiteVerticalScrollAnimController(final CrossViewPager crossViewPager) {
        super(crossViewPager);
        Intrinsics.checkNotNullParameter(crossViewPager, "crossViewPager");
        getScrollHelper().setPageScrollCallback(new ScrollCallback() { // from class: com.miui.keyguard.editor.homepage.util.LiteVerticalScrollAnimController.1
            @Override // com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback
            public void onFling(int i, int i2) {
                ScrollCallback.DefaultImpls.onFling(this, i, i2);
                int iSwitchFloor = LiteVerticalScrollAnimController.this.switchFloor();
                if (iSwitchFloor < 0) {
                    return;
                }
                Object floorSelectViewHolder = crossViewPager.getFloorSelectViewHolder(iSwitchFloor);
                if (!(floorSelectViewHolder instanceof BaseItemViewHolder)) {
                    Log.i("Keyguard-Editor-LiteVerticalScrollAnimController", "onFling floorSelectViewHolder !is BaseItemViewHolder ,switchFloorPosition:" + iSwitchFloor);
                    return;
                }
                Log.i("Keyguard-Editor-LiteVerticalScrollAnimController", "onFling switchFloorPosition:" + iSwitchFloor);
                Integer num = (Integer) LiteVerticalScrollAnimController.this.getFloorHolderUnScheduledAnimCount().get(Integer.valueOf(iSwitchFloor));
                int iIntValue = num != null ? num.intValue() : 0;
                if (iIntValue <= 0) {
                    PreviewHolder.DefaultImpls.switchHome$default((PreviewHolder) floorSelectViewHolder, true, false, 2, null);
                    LiteVerticalScrollAnimController.this.startCustomButtonAnimationForSnapCurrentPosition(iSwitchFloor, (BaseItemViewHolder) floorSelectViewHolder);
                    return;
                }
                Log.w("Keyguard-Editor-LiteVerticalScrollAnimController", "floor " + iSwitchFloor + " have " + iIntValue + " unScheduled Anim can not execute transformer anim !");
            }

            @Override // com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback
            public void onPageScrollStateChanged(int i) {
                ScrollCallback.DefaultImpls.onPageScrollStateChanged(this, i);
                if (i == 0) {
                    LiteVerticalScrollAnimController.this.onVerticalScrollStateChanged(2);
                } else {
                    if (i != 1) {
                        return;
                    }
                    LiteVerticalScrollAnimController.this.onVerticalScrollStateChanged(1);
                }
            }

            @Override // com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback
            public void onPageScrolled(int i, int i2, int i3) {
                ScrollCallback.DefaultImpls.onPageScrolled(this, i, i2, i3);
            }

            @Override // com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback
            public void onPageSelectChanged(int i) {
                ScrollCallback.DefaultImpls.onPageSelectChanged(this, i);
                Log.i("Keyguard-Editor-LiteVerticalScrollAnimController", "position:" + i + " lastSelectedPosition:" + LiteVerticalScrollAnimController.this.getLastSelectedPosition());
                if (i == LiteVerticalScrollAnimController.this.getLastSelectedPosition()) {
                    return;
                }
                int lastSelectedPosition = LiteVerticalScrollAnimController.this.getLastSelectedPosition();
                LiteVerticalScrollAnimController.this.setLastSelectedPosition(i);
                FloorViewHolder floorViewHolder = crossViewPager.getFloorViewHolder(lastSelectedPosition);
                if (floorViewHolder == null) {
                    Log.w("Keyguard-Editor-LiteVerticalScrollAnimController", "lastFloorViewHolder floorViewHolder == null");
                    return;
                }
                RecyclerView.ViewHolder viewHolder = floorViewHolder.getViewHolder(floorViewHolder.getSelectedPosition());
                if (!(viewHolder instanceof BaseItemViewHolder)) {
                    Log.w("Keyguard-Editor-LiteVerticalScrollAnimController", "floorSelectViewHolder  !is BaseItemViewHolder");
                    return;
                }
                LiteVerticalScrollAnimController.this.setLastSelectedPositionYForReset(lastSelectedPosition);
                LiteVerticalScrollAnimController.this.resetFloor(lastSelectedPosition, floorViewHolder);
                ((BaseItemViewHolder) viewHolder).setSelected(false);
            }
        });
    }

    public final void resetCenterPageState(int i, BaseItemViewHolder baseItemViewHolder) {
        View itemView = baseItemViewHolder.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        Folme.clean(itemView);
        PreviewHolder.DefaultImpls.switchHome$default(baseItemViewHolder, false, false, 2, null);
        resetCustomButton(baseItemViewHolder);
        getFloorHolderUnScheduledAnimCount().put(Integer.valueOf(i), 0);
    }

    public final void resetFloor(int i, FloorViewHolder floorViewHolder) {
        RecyclerView.ViewHolder viewHolder = floorViewHolder.getViewHolder(floorViewHolder.getSelectedPosition());
        if (viewHolder instanceof BaseItemViewHolder) {
            resetCenterPageState(i, (BaseItemViewHolder) viewHolder);
        }
    }

    public final int switchFloor() {
        if (getLayoutManager() == null) {
            Log.w("Keyguard-Editor-LiteVerticalScrollAnimController", " switchFloor : layoutManager = null");
            return -1;
        }
        if (!getScrollHelper().isOverlayScrollVertically()) {
            Log.i("Keyguard-Editor-LiteVerticalScrollAnimController", "switchFloor failed: isOverlayScrollVertically = false");
            return -1;
        }
        int iPredictSnapTargetPosition = getScrollHelper().predictSnapTargetPosition(getLayoutManager());
        Log.i("Keyguard-Editor-LiteVerticalScrollAnimController", "switchFloor -> snapTargetPosition = " + iPredictSnapTargetPosition);
        if (iPredictSnapTargetPosition == getLastSelectedPosition()) {
            return -1;
        }
        Log.w("Keyguard-Editor-LiteVerticalScrollAnimController", "switchFloor failed: snapTargetPosition = lastSelectedPosition.");
        return iPredictSnapTargetPosition;
    }
}
