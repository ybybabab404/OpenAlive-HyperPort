package com.miui.keyguard.editor.homepage.view.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.util.LongSparseArray;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.bean.FloorItemBean;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.homepage.view.generator.DynamicViewFactory;
import com.miui.keyguard.editor.homepage.view.generator.FactoryParams;
import com.miui.keyguard.editor.homepage.view.generator.TitleViewFactory;
import com.miui.keyguard.editor.homepage.view.viewpager.DampViewPager2;
import com.miui.keyguard.editor.homepage.view.viewpager.SpringLayout;
import com.miui.keyguard.editor.homepage.view.viewpager.TouchLayout;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;
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
/* JADX INFO: compiled from: AdapterAndHolderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FloorAdapter extends BaseAdapter<FloorItemBean, CrossViewPagerCallback> {
    public final LongSparseArray floorRVStates;

    public FloorAdapter() {
        super(1);
        this.floorRVStates = new LongSparseArray();
    }

    public static final TitleViewFactory createMyViewHolder$lambda$1(ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        Intrinsics.checkNotNull(context);
        return new TitleViewFactory(context);
    }

    public static final TitleViewFactory createMyViewHolder$lambda$2(Lazy lazy) {
        return (TitleViewFactory) lazy.getValue();
    }

    public final DampViewPager2 createDampViewPager(Context context) {
        DampViewPager2 dampViewPager2 = new DampViewPager2(context);
        dampViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.miui.keyguard.editor.homepage.view.adapter.FloorAdapter$createDampViewPager$1$1
            @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int i) {
                boolean z = true;
                if (i != 1 && i != 2) {
                    z = false;
                }
                DampViewPager2.isSliding = z;
            }
        });
        return dampViewPager2;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseAdapter
    public BaseViewHolder createMyViewHolder(final ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Lazy lazy = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.view.adapter.FloorAdapter$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FloorAdapter.createMyViewHolder$lambda$1(parent);
            }
        });
        if (i != 1) {
            Context context = parent.getContext();
            Intrinsics.checkNotNull(context);
            DampViewPager2 dampViewPager2CreateDampViewPager = createDampViewPager(context);
            ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
            Context context2 = parent.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resourcesProvider.provideTemplateContainerWidth(context2), -2);
            layoutParams.gravity = 1;
            layoutParams.topMargin = parent.getContext().getResources().getDimensionPixelSize(R.dimen.kg_main_internal_pager_margin_top);
            dampViewPager2CreateDampViewPager.setOrientation(0);
            dampViewPager2CreateDampViewPager.setClipChildren(false);
            dampViewPager2CreateDampViewPager.setLayoutParams(layoutParams);
            TouchLayout touchLayout = new TouchLayout(parent.getContext());
            touchLayout.setClipChildren(false);
            touchLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, -1));
            touchLayout.addView(dampViewPager2CreateDampViewPager);
            touchLayout.addView(DynamicViewFactory.create$default(createMyViewHolder$lambda$2(lazy), null, 1, null));
            touchLayout.init();
            return new PreviewFloorViewHolder(touchLayout, dampViewPager2CreateDampViewPager);
        }
        Context context3 = parent.getContext();
        Intrinsics.checkNotNull(context3);
        DampViewPager2 dampViewPager2CreateDampViewPager2 = createDampViewPager(context3);
        ResourcesProvider resourcesProvider2 = ResourcesProvider.INSTANCE;
        Context context4 = parent.getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
        int iProvideTemplateContainerWidth = (int) resourcesProvider2.provideTemplateContainerWidth(context4);
        Context context5 = parent.getContext();
        Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(iProvideTemplateContainerWidth, (int) resourcesProvider2.provideTemplateContainerHeight(context5, new FactoryParams(false, false, 2, null)));
        layoutParams2.gravity = 1;
        layoutParams2.topMargin = parent.getContext().getResources().getDimensionPixelSize(R.dimen.kg_main_internal_pager_margin_top);
        dampViewPager2CreateDampViewPager2.setOrientation(0);
        dampViewPager2CreateDampViewPager2.setClipChildren(false);
        dampViewPager2CreateDampViewPager2.setLayoutParams(layoutParams2);
        SpringLayout springLayout = new SpringLayout(parent.getContext());
        springLayout.setClipChildren(false);
        springLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, -1));
        springLayout.addView(dampViewPager2CreateDampViewPager2);
        springLayout.addView(DynamicViewFactory.create$default(createMyViewHolder$lambda$2(lazy), null, 1, null));
        springLayout.setImportantForAccessibility(2);
        return new MyFloorViewHolder(springLayout, dampViewPager2CreateDampViewPager2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return ((FloorItemBean) getMData().get(i)).getFloor();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        FloorItemBean floorItemBean = (FloorItemBean) getItem(i);
        if (floorItemBean != null) {
            return floorItemBean.getItemType();
        }
        return -1;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseViewHolder holder, int i) {
        RecyclerView.LayoutManager layoutManager;
        Intrinsics.checkNotNullParameter(holder, "holder");
        super.onBindViewHolder(holder, i);
        if (holder instanceof FloorViewHolder) {
            FloorViewHolder floorViewHolder = (FloorViewHolder) holder;
            Parcelable parcelable = (Parcelable) this.floorRVStates.get(floorViewHolder.getItemId());
            if (parcelable == null || (layoutManager = floorViewHolder.getViewPager().getRecyclerViewImpl().getLayoutManager()) == null) {
                return;
            }
            layoutManager.onRestoreInstanceState(parcelable);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseViewHolder holder, int i, List payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, i);
        } else {
            holder.onViewHolderBound(getItem(i), i, payloads);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        super.onViewRecycled(holder);
        if (holder instanceof FloorViewHolder) {
            FloorViewHolder floorViewHolder = (FloorViewHolder) holder;
            RecyclerView.LayoutManager layoutManager = floorViewHolder.getViewPager().getRecyclerViewImpl().getLayoutManager();
            Parcelable parcelableOnSaveInstanceState = layoutManager != null ? layoutManager.onSaveInstanceState() : null;
            if (parcelableOnSaveInstanceState != null) {
                this.floorRVStates.put(floorViewHolder.getItemId(), parcelableOnSaveInstanceState);
            }
        }
    }
}
