package com.miui.keyguard.editor.homepage.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.homepage.bean.FloorItemBean;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.util.TitleDescController;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.homepage.view.adapter.BaseAdapter;
import com.miui.keyguard.editor.homepage.view.transformer.HorizontalScaleTransformer;
import com.miui.keyguard.editor.homepage.view.transformer.PagerAlphaTransformer;
import com.miui.keyguard.editor.homepage.view.transformer.PagerMarginTransformer;
import com.miui.keyguard.editor.homepage.view.viewpager.DampViewPager2;
import com.miui.keyguard.editor.homepage.view.viewpager.ScrollHelper;
import com.miui.keyguard.editor.view.viewpager2.CompositePageTransformer;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
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
/* JADX INFO: compiled from: AdapterAndHolderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public class FloorViewHolder<AdapterType extends BaseAdapter<TemplateItemBean, CrossViewPagerCallback>> extends BaseViewHolder<FloorItemBean, CrossViewPagerCallback> {
    public final String TAG;
    public boolean isFloorSelected;
    public BaseAdapter pagerAdapter;
    public final Lazy recyclerViewInViewPager$delegate;
    public int scrollState;
    public final TitleDescController titleController;
    public final DampViewPager2 viewPager;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FloorViewHolder(ViewGroup container, DampViewPager2 pager2) {
        super(container);
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(pager2, "pager2");
        this.TAG = "Keyguard-Editor-FloorViewHolder";
        this.viewPager = pager2;
        this.titleController = new TitleDescController(container);
        this.recyclerViewInViewPager$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.view.adapter.FloorViewHolder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FloorViewHolder.recyclerViewInViewPager_delegate$lambda$0(this.f$0);
            }
        });
    }

    public static final RecyclerView recyclerViewInViewPager_delegate$lambda$0(FloorViewHolder floorViewHolder) {
        View childAt = floorViewHolder.viewPager.getChildAt(0);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView");
        return (RecyclerView) childAt;
    }

    public final TemplateItemBean getItem(int i) {
        BaseAdapter baseAdapter = this.pagerAdapter;
        if (baseAdapter != null) {
            return (TemplateItemBean) baseAdapter.getItem(i);
        }
        return null;
    }

    public final RecyclerView.LayoutManager getLayoutManager() {
        return getRecyclerViewInViewPager().getLayoutManager();
    }

    public final BaseAdapter getPagerAdapter() {
        return this.pagerAdapter;
    }

    public final RecyclerView getRecyclerViewInViewPager() {
        return (RecyclerView) this.recyclerViewInViewPager$delegate.getValue();
    }

    public final TemplateItemBean getSelectedItem() {
        return getItem(getSelectedPosition());
    }

    public final int getSelectedPosition() {
        return this.viewPager.getCurrentItem();
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public View getTargetContentView() {
        return this.viewPager;
    }

    public final TitleDescController getTitleController() {
        return this.titleController;
    }

    public final RecyclerView.ViewHolder getViewHolder(int i) {
        try {
            return getRecyclerViewInViewPager().findViewHolderForAdapterPosition(i);
        } catch (Exception e) {
            Log.e(this.TAG, "getViewHolder error: " + e);
            return null;
        }
    }

    public final DampViewPager2 getViewPager() {
        return this.viewPager;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onPageTransformed(int i, float f, float f2, int i2) {
        try {
            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = getRecyclerViewInViewPager().findViewHolderForAdapterPosition(getSelectedPosition());
            if (viewHolderFindViewHolderForAdapterPosition instanceof BaseItemViewHolder) {
                ((BaseItemViewHolder) viewHolderFindViewHolderForAdapterPosition).onPageTransformed(i, f, f2, i2);
            }
        } catch (Exception e) {
            Log.e(this.TAG, "onPositionOffsetChanged error: " + e);
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderBound(FloorItemBean floorItemBean, int i) {
        super.onViewHolderBound((Object) floorItemBean, i);
        List items = floorItemBean != null ? floorItemBean.getItems() : null;
        if (items == null || items.isEmpty()) {
            Log.w(this.TAG, "onViewHolderBound failed: items is empty");
            return;
        }
        BaseAdapter baseAdapter = this.pagerAdapter;
        if (baseAdapter != null) {
            baseAdapter.setY(getY());
            baseAdapter.setData(items);
            baseAdapter.notifyDataSetChanged();
            selectTargetPosition(floorItemBean);
        }
        Integer title = floorItemBean.getTitle();
        if (title != null) {
            this.titleController.setTitle(title.intValue());
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderCreated(View itemView) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        DampViewPager2 dampViewPager2 = this.viewPager;
        dampViewPager2.setOffscreenPageLimit(1);
        dampViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.miui.keyguard.editor.homepage.view.adapter.FloorViewHolder$onViewHolderCreated$1$1
            @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int i) {
                this.this$0.scrollState = i;
                ScrollHelper.Companion.setInScroll(i != 0);
                CrossViewPagerCallback crossViewPagerCallback = (CrossViewPagerCallback) this.this$0.getCallback();
                if (crossViewPagerCallback != null) {
                    crossViewPagerCallback.onPageScrollStateChanged(0, this.this$0.getViewPager().getCurrentItem(), this.this$0.getY(), i);
                }
            }

            @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i) {
                FloorItemBean floorItemBean = (FloorItemBean) this.this$0.getItemData();
                if (floorItemBean != null) {
                    floorItemBean.setSelectPosition(i);
                }
                CrossViewPagerCallback crossViewPagerCallback = (CrossViewPagerCallback) this.this$0.getCallback();
                if (crossViewPagerCallback != null) {
                    crossViewPagerCallback.onPageSelected(0, i, this.this$0.getY(), this.this$0.scrollState);
                }
            }
        });
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = dampViewPager2.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        PagerMarginTransformer pagerMarginTransformer = new PagerMarginTransformer(this.viewPager, this, (int) resourcesProvider.provideHorizontalPagerOffscreenOffset(context));
        pagerMarginTransformer.attachViewPager2(this.viewPager);
        compositePageTransformer.addTransformer(pagerMarginTransformer);
        Context context2 = dampViewPager2.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        compositePageTransformer.addTransformer(new HorizontalScaleTransformer(context2));
        PagerAlphaTransformer pagerAlphaTransformer = new PagerAlphaTransformer(0);
        dampViewPager2.registerOnPageChangeCallback(pagerAlphaTransformer);
        compositePageTransformer.addTransformer(pagerAlphaTransformer);
        dampViewPager2.setPageTransformer(compositePageTransformer);
    }

    public void selectTargetPosition(FloorItemBean floorItemBean) {
        List items;
        int selectPosition = floorItemBean != null ? floorItemBean.getSelectPosition() : -1;
        int size = (floorItemBean == null || (items = floorItemBean.getItems()) == null) ? 0 : items.size();
        if (selectPosition < 0 || selectPosition > size - 1) {
            return;
        }
        this.viewPager.setCurrentItem(selectPosition, false);
    }

    public final void setFloorSelectItem(int i, boolean z) {
        TemplateItemBean templateItemBean;
        Log.i(this.TAG, "position:" + i + " isSelected:" + z);
        BaseAdapter baseAdapter = this.pagerAdapter;
        if (baseAdapter == null || (templateItemBean = (TemplateItemBean) baseAdapter.getItem(i)) == null) {
            return;
        }
        templateItemBean.setSelected(z);
        BaseAdapter baseAdapter2 = this.pagerAdapter;
        if (baseAdapter2 != null) {
            baseAdapter2.notifyItemChanged(i);
        }
    }

    public void setFloorSelected(boolean z) {
        this.isFloorSelected = z;
        this.viewPager.setCanScroll(z);
    }

    public final void setPagerAdapter(BaseAdapter baseAdapter) {
        this.pagerAdapter = baseAdapter;
    }
}
