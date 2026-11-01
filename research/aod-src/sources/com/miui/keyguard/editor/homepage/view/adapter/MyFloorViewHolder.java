package com.miui.keyguard.editor.homepage.view.adapter;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.homepage.bean.FloorItemBean;
import com.miui.keyguard.editor.homepage.bean.RemoveInfo;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.view.viewpager.DampViewPager2;
import com.miui.keyguard.editor.homepage.view.viewpager.HorizontalSpringTouchManager;
import com.miui.keyguard.editor.homepage.view.viewpager.SpringLayout;
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
    */
/* JADX INFO: compiled from: AdapterAndHolderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MyFloorViewHolder extends FloorViewHolder<MyTemplateAdapter> {
    public final String TAG;
    public final SpringLayout floorContain;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MyFloorViewHolder(ViewGroup container, DampViewPager2 pager2) {
        super(container, pager2);
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(pager2, "pager2");
        this.TAG = "KeyGuardEditor.MyFloorViewHolder";
        this.floorContain = (SpringLayout) container;
    }

    public static final boolean onViewHolderCreated$lambda$1(MyFloorViewHolder myFloorViewHolder, View view, MotionEvent motionEvent) {
        RecyclerView.ViewHolder viewHolder = myFloorViewHolder.getViewHolder(myFloorViewHolder.getSelectedPosition());
        if (viewHolder instanceof BaseItemViewHolder) {
            ((BaseItemViewHolder) viewHolder).setOutsideTouchEvent(motionEvent.getAction() == 0 || motionEvent.getAction() == 2);
        }
        return false;
    }

    public final int guessNextSelectedPosition(int i) {
        MyTemplateAdapter myTemplateAdapter = (MyTemplateAdapter) getPagerAdapter();
        int itemCount = myTemplateAdapter != null ? myTemplateAdapter.getItemCount() : 0;
        if (itemCount <= 1) {
            Log.i(this.TAG, "reSelectNextSelectedPosition finished: itemCount = " + itemCount);
            return -1;
        }
        int i2 = itemCount - 1;
        int i3 = i == i2 ? i - 1 : i + 1;
        if (i3 >= 0 && i3 <= i2) {
            return i3;
        }
        Log.i(this.TAG, "reSelectNextSelectedPosition failed: invalid guessPosition. guessPos = " + i3 + ", itemCount = " + itemCount);
        return -1;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderBound(FloorItemBean floorItemBean, int i, List payloads) {
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        super.onViewHolderBound((Object) floorItemBean, i, payloads);
        removeItem(payloads);
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.FloorViewHolder, com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderCreated(View itemView) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        super.onViewHolderCreated(itemView);
        getViewPager().setOffscreenPageLimit(2);
        MyTemplateAdapter myTemplateAdapter = new MyTemplateAdapter(false, 1, null);
        myTemplateAdapter.setCallback(getCallback());
        setPagerAdapter(myTemplateAdapter);
        getViewPager().setAdapter(getPagerAdapter());
        SpringLayout springLayout = this.floorContain;
        springLayout.setSpringTouchManager(new HorizontalSpringTouchManager(springLayout.getContext(), this.floorContain));
        this.floorContain.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.keyguard.editor.homepage.view.adapter.MyFloorViewHolder$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return MyFloorViewHolder.onViewHolderCreated$lambda$1(this.f$0, view, motionEvent);
            }
        });
    }

    public final void removeItem(List list) {
        TemplateItemBean templateItemBean;
        Object obj = list.get(0);
        if (obj instanceof RemoveInfo) {
            int position = ((RemoveInfo) obj).getPosition();
            int iGuessNextSelectedPosition = guessNextSelectedPosition(position);
            MyTemplateAdapter myTemplateAdapter = (MyTemplateAdapter) getPagerAdapter();
            if (myTemplateAdapter != null && (templateItemBean = (TemplateItemBean) myTemplateAdapter.getItem(iGuessNextSelectedPosition)) != null) {
                templateItemBean.setSelected(true);
            }
            MyTemplateAdapter myTemplateAdapter2 = (MyTemplateAdapter) getPagerAdapter();
            int itemCount = myTemplateAdapter2 != null ? myTemplateAdapter2.getItemCount() : 0;
            MyTemplateAdapter myTemplateAdapter3 = (MyTemplateAdapter) getPagerAdapter();
            if (myTemplateAdapter3 != null) {
                myTemplateAdapter3.removeAt(position);
            }
            if (itemCount > 0) {
                if (position == itemCount - 1) {
                    MyTemplateAdapter myTemplateAdapter4 = (MyTemplateAdapter) getPagerAdapter();
                    if (myTemplateAdapter4 != null) {
                        myTemplateAdapter4.notifyItemRangeChanged(position - 1, 1);
                        return;
                    }
                    return;
                }
                MyTemplateAdapter myTemplateAdapter5 = (MyTemplateAdapter) getPagerAdapter();
                if (myTemplateAdapter5 != null) {
                    myTemplateAdapter5.notifyItemRangeChanged(position, itemCount - position);
                }
            }
        }
    }
}
