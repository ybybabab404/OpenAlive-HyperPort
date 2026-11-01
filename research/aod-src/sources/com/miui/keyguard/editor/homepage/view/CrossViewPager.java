package com.miui.keyguard.editor.homepage.view;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.homepage.bean.FloorItemBean;
import com.miui.keyguard.editor.homepage.bean.RemoveInfo;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.util.BaseVerticalScrollAnimController;
import com.miui.keyguard.editor.homepage.util.LiteVerticalScrollAnimController;
import com.miui.keyguard.editor.homepage.util.OnVerticalScrollAnimationCallback;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.util.SelectItemProvider;
import com.miui.keyguard.editor.homepage.util.VerticalScrollAnimController;
import com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.FloorAdapter;
import com.miui.keyguard.editor.homepage.view.adapter.FloorViewHolder;
import com.miui.keyguard.editor.homepage.view.generator.CrossViewPagerFactoryImpl;
import com.miui.keyguard.editor.homepage.view.generator.DynamicViewFactory;
import com.miui.keyguard.editor.homepage.view.transformer.PagerAlphaTransformer;
import com.miui.keyguard.editor.homepage.view.transformer.PagerMarginTransformer;
import com.miui.keyguard.editor.homepage.view.viewpager.DampPagerSnapHelperImpl;
import com.miui.keyguard.editor.homepage.view.viewpager.DampViewPager2;
import com.miui.keyguard.editor.homepage.view.viewpager.SpringLayout;
import com.miui.keyguard.editor.homepage.view.viewpager.SpringScrollListener;
import com.miui.keyguard.editor.homepage.view.viewpager.TemplateDeleteLayer;
import com.miui.keyguard.editor.homepage.view.viewpager.VerticalSpringTouchManager;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ScenarioRecognitionUtils;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.view.viewpager2.CompositePageTransformer;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
/* JADX INFO: compiled from: CrossViewPager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class CrossViewPager extends FrameLayout implements CrossViewPagerCallback, SelectItemProvider {
    public static final Companion Companion = new Companion(null);
    public final String TAG;
    public FloorAdapter adapter;
    public View deletedButton;
    public int deletedX;
    public int deletedY;
    public CrossViewPagerCallback externalCallback;
    public Map horizontalPositions;
    public float initialTouchX;
    public float initialTouchY;
    public boolean isFirstSelect;
    public boolean isTouchCompletedForDelete;
    public int lastSelectX;
    public int lastSelectY;
    public final Lazy recyclerViewInViewPager$delegate;
    public final SpringLayout springLayout;
    public final VerticalSpringTouchManager springTouchManager;
    public BaseVerticalScrollAnimController verticalAnimController;
    public int verticalPosition;
    public int verticalScrollState;
    public final DampViewPager2 viewPager;

    /* JADX INFO: compiled from: CrossViewPager.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public CrossViewPager(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public CrossViewPager(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.TAG = "Keyguard-Editor-CrossViewPager";
        addView(DynamicViewFactory.create$default(new CrossViewPagerFactoryImpl(context), null, 1, null));
        SpringLayout springLayout = (SpringLayout) findViewById(R.id.kg_spring_layout);
        this.springLayout = springLayout;
        View viewFindViewById = findViewById(R.id.kg_viewpager_vertical);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.viewPager = (DampViewPager2) viewFindViewById;
        FloorAdapter floorAdapter = new FloorAdapter();
        floorAdapter.setCallback(this);
        this.adapter = floorAdapter;
        this.recyclerViewInViewPager$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.view.CrossViewPager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossViewPager.recyclerViewInViewPager_delegate$lambda$1(this.f$0);
            }
        });
        this.springTouchManager = new VerticalSpringTouchManager(context, springLayout);
        this.verticalPosition = -1;
        this.horizontalPositions = new LinkedHashMap();
        this.lastSelectX = -1;
        this.lastSelectY = -1;
        this.deletedX = -1;
        this.deletedY = -1;
        this.initialTouchX = -1.0f;
        this.initialTouchY = -1.0f;
        this.isFirstSelect = true;
    }

    public /* synthetic */ CrossViewPager(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    private final Rect getDeleteButtonRect() {
        View view = this.deletedButton;
        if (view == null) {
            return null;
        }
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        int width = view.getWidth();
        int height = view.getHeight();
        Log.i(this.TAG, "delete button bounds: left = " + i + ", top = " + i2 + ", width = " + width + ",height = " + height);
        return new Rect(i, i2, width + i, height + i2);
    }

    private final RecyclerView getRecyclerViewInViewPager() {
        return (RecyclerView) this.recyclerViewInViewPager$delegate.getValue();
    }

    public static final CharSequence logSelectedItemState$lambda$4(CrossViewPager crossViewPager, int i, int i2) {
        RecyclerView.ViewHolder viewHolder = crossViewPager.getViewHolder(i2, i);
        View view = viewHolder != null ? viewHolder.itemView : null;
        boolean z = viewHolder != null;
        Float fValueOf = view != null ? Float.valueOf(view.getTranslationX()) : null;
        Float fValueOf2 = view != null ? Float.valueOf(view.getScaleX()) : null;
        Float fValueOf3 = view != null ? Float.valueOf(view.getScaleY()) : null;
        Float fValueOf4 = view != null ? Float.valueOf(view.getAlpha()) : null;
        BaseItemViewHolder baseItemViewHolder = viewHolder instanceof BaseItemViewHolder ? (BaseItemViewHolder) viewHolder : null;
        return "x=" + i2 + ", exists=" + z + ", translationX=" + fValueOf + ", scaleX=" + fValueOf2 + ", scaleY=" + fValueOf3 + ", alpha=" + fValueOf4 + ", selected=" + (baseItemViewHolder != null ? Boolean.valueOf(baseItemViewHolder.isSelected()) : null);
    }

    public static /* synthetic */ void offscreenPageLimit$default(CrossViewPager crossViewPager, int i, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 1;
        }
        if ((i2 & 2) != 0) {
            z = false;
        }
        crossViewPager.offscreenPageLimit(i, z);
    }

    public static final Boolean onPageDeleted$lambda$11(CrossViewPager crossViewPager, int i, int i2, TemplateItemBean templateItemBean) {
        boolean zOnPageDeleted = false;
        try {
            CrossViewPagerCallback crossViewPagerCallback = crossViewPager.externalCallback;
            if (crossViewPagerCallback != null) {
                zOnPageDeleted = crossViewPagerCallback.onPageDeleted(i, i2, templateItemBean);
            }
        } catch (Exception e) {
            Log.e(crossViewPager.TAG, "externalCallback@onPageDeleted failed: " + e);
        }
        return Boolean.valueOf(zOnPageDeleted);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void onPageDeleted$lambda$15(final CrossViewPager crossViewPager, final int i, final int i2, final TemplateItemBean templateItemBean, Boolean bool) {
        if (!bool.booleanValue()) {
            Log.e(crossViewPager.TAG, "onPageDeleted failed: data delete failed.");
            return;
        }
        Context context = crossViewPager.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        final TemplateDeleteLayer templateDeleteLayer = new TemplateDeleteLayer(context, null, 2, 0 == true ? 1 : 0);
        templateDeleteLayer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        templateDeleteLayer.initLayout(i, i2, crossViewPager);
        crossViewPager.addView(templateDeleteLayer);
        crossViewPager.post(new Runnable() { // from class: com.miui.keyguard.editor.homepage.view.CrossViewPager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                CrossViewPager.onPageDeleted$lambda$15$lambda$14(templateDeleteLayer, crossViewPager, i, i2, templateItemBean);
            }
        });
    }

    public static final void onPageDeleted$lambda$15$lambda$14(final TemplateDeleteLayer templateDeleteLayer, final CrossViewPager crossViewPager, final int i, final int i2, final TemplateItemBean templateItemBean) {
        templateDeleteLayer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.view.CrossViewPager$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CrossViewPager.onPageDeleted$lambda$15$lambda$14$lambda$12(view);
            }
        });
        templateDeleteLayer.startDeleteTransformAnim(new Function0() { // from class: com.miui.keyguard.editor.homepage.view.CrossViewPager$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossViewPager.onPageDeleted$lambda$15$lambda$14$lambda$13(this.f$0, templateDeleteLayer, i, i2, templateItemBean);
            }
        });
    }

    public static final void onPageDeleted$lambda$15$lambda$14$lambda$12(View view) {
    }

    public static final Unit onPageDeleted$lambda$15$lambda$14$lambda$13(CrossViewPager crossViewPager, TemplateDeleteLayer templateDeleteLayer, int i, int i2, TemplateItemBean templateItemBean) {
        try {
            crossViewPager.removeView(templateDeleteLayer);
            CrossViewPagerCallback crossViewPagerCallback = crossViewPager.externalCallback;
            if (crossViewPagerCallback != null) {
                crossViewPagerCallback.onPageDeleteCompleted(i, i2, templateItemBean);
            }
            crossViewPager.adapter.notifyItemChanged(i2, new RemoveInfo(i));
        } catch (Exception e) {
            Log.e(crossViewPager.TAG, "CrossViewPager@onPageDeleted failed: " + e);
        }
        return Unit.INSTANCE;
    }

    public static final RecyclerView recyclerViewInViewPager_delegate$lambda$1(CrossViewPager crossViewPager) {
        View childAt = crossViewPager.viewPager.getChildAt(0);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView");
        return (RecyclerView) childAt;
    }

    public final void checkOrHideDeleteMenuForPosition(int i, int i2) {
        try {
            RecyclerView.ViewHolder viewHolder = getViewHolder(i, i2);
            if (viewHolder instanceof BaseItemViewHolder) {
                ((BaseItemViewHolder) viewHolder).showDeleteLayer(false);
                return;
            }
            Log.w(this.TAG, "checkOrHideDeleteMenuForPosition(x: " + i + ", y: " + i2 + ") failed: not found view holder.");
        } catch (Exception e) {
            Log.e(this.TAG, "checkOrHideDeleteMenuForPosition(x: " + i + ", y: " + i2 + ") failed: " + e);
        }
    }

    public final Point checkValidPosition(int i, int i2, int i3, int i4) {
        if (i4 != 0) {
            return new Point(i2, i3);
        }
        if (i != 0) {
            if (i == 1) {
                Integer num = (Integer) this.horizontalPositions.get(Integer.valueOf(i3));
                if (num == null) {
                    return null;
                }
                return new Point(num.intValue(), i3);
            }
            Log.w(this.TAG, "checkValidPosition: unknown orientation: " + i);
            return null;
        }
        int i5 = this.verticalPosition;
        if (i5 == -1) {
            Log.w(this.TAG, "checkValidPosition: verticalPosition = -1. x: " + i2 + ", y: " + i3);
            return null;
        }
        if (i3 == i5) {
            return new Point(i2, i3);
        }
        Log.w(this.TAG, "checkValidPosition: y is not selected. x = " + i2 + ", y = " + i3 + ", sy = " + i5);
        return null;
    }

    public final void clearLastSelectPosition() {
        this.lastSelectX = -1;
        this.lastSelectY = -1;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Intrinsics.checkNotNullParameter(ev, "ev");
        if (this.deletedX == -1 || this.deletedY == -1 || this.deletedButton == null) {
            return super.dispatchTouchEvent(ev);
        }
        int action = ev.getAction();
        if (action == 0) {
            this.initialTouchX = ev.getRawX();
            this.initialTouchY = ev.getRawY();
        } else if (action == 1) {
            onClickUp();
        } else if (action == 3) {
            this.initialTouchX = -1.0f;
            this.initialTouchY = -1.0f;
            this.isTouchCompletedForDelete = true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public final boolean getFirstSelect() {
        return this.isFirstSelect;
    }

    public final RecyclerView.ViewHolder getFloorSelectViewHolder(int i) {
        FloorViewHolder floorViewHolder = getFloorViewHolder(i);
        if (floorViewHolder != null) {
            return floorViewHolder.getViewHolder(floorViewHolder.getSelectedPosition());
        }
        Log.i(this.TAG, "getFloorSelectViewHolder : floorViewHolder==null");
        return null;
    }

    public final FloorViewHolder getFloorViewHolder(int i) {
        try {
            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = getRecyclerViewInViewPager().findViewHolderForAdapterPosition(i);
            if (viewHolderFindViewHolderForAdapterPosition instanceof FloorViewHolder) {
                return (FloorViewHolder) viewHolderFindViewHolderForAdapterPosition;
            }
            return null;
        } catch (Exception e) {
            Log.e(this.TAG, "getItem failed: " + e);
            return null;
        }
    }

    public final TemplateItemBean getItem(int i, int i2) {
        try {
            FloorViewHolder floorViewHolder = getFloorViewHolder(i2);
            if (floorViewHolder != null) {
                return floorViewHolder.getItem(i);
            }
            Log.w(this.TAG, "getItem: no floor found for position: " + i2);
            return null;
        } catch (Exception e) {
            Log.e(this.TAG, "getItem(" + i + ", " + i2 + ") failed: " + e);
            return null;
        }
    }

    @Nullable
    public final RecyclerView.LayoutManager getLayoutManager() {
        return getRecyclerViewInViewPager().getLayoutManager();
    }

    public final int getSelectedFloorPosition() {
        return this.viewPager.getCurrentItem();
    }

    @Override // com.miui.keyguard.editor.homepage.util.SelectItemProvider
    @Nullable
    public TemplateItemBean getSelectedItem() {
        try {
            int currentItem = this.viewPager.getCurrentItem();
            FloorViewHolder floorViewHolder = getFloorViewHolder(currentItem);
            if (floorViewHolder != null) {
                return floorViewHolder.getSelectedItem();
            }
            Log.w(this.TAG, "getSelectedItem: no floor found for position: " + currentItem);
            return null;
        } catch (Exception e) {
            Log.e(this.TAG, "getSelectedItem failed: " + e);
            return null;
        }
    }

    public final TemplateItemBean getSelectedItemForFloor(int i) {
        FloorViewHolder floorViewHolder = getFloorViewHolder(i);
        if (floorViewHolder != null) {
            return floorViewHolder.getSelectedItem();
        }
        Log.w(this.TAG, "getSelectedItemForFloor: no floor found for position: " + i);
        return null;
    }

    @Nullable
    public final Point getSelectedPosition() {
        try {
            int currentItem = this.viewPager.getCurrentItem();
            FloorViewHolder floorViewHolder = getFloorViewHolder(currentItem);
            if (floorViewHolder != null) {
                return new Point(floorViewHolder.getSelectedPosition(), currentItem);
            }
            Log.w(this.TAG, "getSelectedPosition: no floor found for position: " + currentItem);
            return null;
        } catch (Exception e) {
            Log.e(this.TAG, "getSelectedPosition failed: " + e);
            return null;
        }
    }

    @Override // com.miui.keyguard.editor.homepage.util.SelectItemProvider
    @Nullable
    public RecyclerView.ViewHolder getSelectedViewHolder() {
        return getSelectedViewHolderForFloor(getSelectedFloorPosition());
    }

    public final RecyclerView.ViewHolder getSelectedViewHolderForFloor(int i) {
        FloorViewHolder floorViewHolder = getFloorViewHolder(i);
        if (floorViewHolder != null) {
            return floorViewHolder.getViewHolder(floorViewHolder.getSelectedPosition());
        }
        Log.w(this.TAG, "getSelectedViewHolderForFloor: no floor found for position: " + i);
        return null;
    }

    public final RecyclerView.ViewHolder getViewHolder(int i, int i2) {
        FloorViewHolder floorViewHolder = getFloorViewHolder(i2);
        if (floorViewHolder != null) {
            return floorViewHolder.getViewHolder(i);
        }
        Log.w(this.TAG, "getViewHolder: no floor found for position: " + i2);
        return null;
    }

    @NotNull
    public final DampViewPager2 getViewPager2() {
        return this.viewPager;
    }

    public final void horizontalPageSelectChanged(int i, int i2) {
        RecyclerView.ViewHolder viewHolder = getViewHolder(this.lastSelectX, this.lastSelectY);
        if (viewHolder instanceof BaseItemViewHolder) {
            ((BaseItemViewHolder) viewHolder).onViewHolderSelectChanged(false);
        } else {
            FloorViewHolder floorViewHolder = getFloorViewHolder(i2);
            if (floorViewHolder != null) {
                floorViewHolder.setFloorSelectItem(this.lastSelectX, false);
            }
        }
        RecyclerView.ViewHolder viewHolder2 = getViewHolder(i, i2);
        if (viewHolder2 instanceof BaseItemViewHolder) {
            if (!this.isFirstSelect) {
                ((BaseItemViewHolder) viewHolder2).onViewHolderSelectChanged(true);
            } else {
                ((BaseItemViewHolder) viewHolder2).setSelectedUI(true);
                this.isFirstSelect = false;
            }
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
    public final void initPagerTransformers() {
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        float fProvideInternalPagerTopMargin = resourcesProvider.provideInternalPagerTopMargin(context);
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        float fProvideInternalPagerBottomMargin = resourcesProvider.provideInternalPagerBottomMargin(context2);
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        float fProvideBottomPagerOffscreenOffset = resourcesProvider.provideBottomPagerOffscreenOffset(context3);
        Context context4 = getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
        float fProvideTemplateContainerPadding = resourcesProvider.provideTemplateContainerPadding(context4);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        Context context5 = getContext();
        Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
        PagerMarginTransformer pagerMarginTransformer = new PagerMarginTransformer(context5, fProvideInternalPagerTopMargin + fProvideBottomPagerOffscreenOffset + fProvideTemplateContainerPadding, fProvideInternalPagerBottomMargin);
        pagerMarginTransformer.attachViewPager2(this.viewPager);
        compositePageTransformer.addTransformer(pagerMarginTransformer);
        PagerAlphaTransformer pagerAlphaTransformer = new PagerAlphaTransformer(1);
        this.viewPager.registerOnPageChangeCallback(pagerAlphaTransformer);
        compositePageTransformer.addTransformer(pagerAlphaTransformer);
        this.viewPager.setPageTransformer(compositePageTransformer);
    }

    public final void logSelectedItemState(String reason, int i, final int i2) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        FloorViewHolder floorViewHolder = getFloorViewHolder(i2);
        Point selectedPosition = getSelectedPosition();
        String strJoinToString$default = CollectionsKt.joinToString$default(CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(i - 1), Integer.valueOf(i), Integer.valueOf(i + 1)}), "; ", null, null, 0, null, new Function1() { // from class: com.miui.keyguard.editor.homepage.view.CrossViewPager$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return CrossViewPager.logSelectedItemState$lambda$4(this.f$0, i2, ((Integer) obj).intValue());
            }
        }, 30, null);
        Log.i(this.TAG, "logSelectedItemState -> reason = " + reason + ", x = " + i + ", y = " + i2 + ", selectedPosition = " + selectedPosition + ", verticalState = " + this.verticalScrollState + ", floorSelectedPosition = " + (floorViewHolder != null ? Integer.valueOf(floorViewHolder.getSelectedPosition()) : null) + ", " + strJoinToString$default);
    }

    public final void offscreenPageLimit(int i, boolean z) {
        DampViewPager2 dampViewPager2 = this.viewPager;
        if (!z) {
            dampViewPager2.setOffscreenPageLimit(i);
            return;
        }
        RecyclerView.Adapter adapter = dampViewPager2.getAdapter();
        if (adapter != null) {
            i = adapter.getItemCount();
        }
        dampViewPager2.setOffscreenPageLimit(i);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onAddTemplate() {
        CrossViewPagerCallback.DefaultImpls.onAddTemplate(this);
    }

    public final void onClickUp() {
        if (!this.isTouchCompletedForDelete) {
            this.isTouchCompletedForDelete = true;
            return;
        }
        if (this.initialTouchX == -1.0f || this.initialTouchY == -1.0f || this.deletedX == -1 || this.deletedY == -1 || this.deletedButton == null) {
            return;
        }
        Rect deleteButtonRect = getDeleteButtonRect();
        if (deleteButtonRect == null) {
            Log.e(this.TAG, "onClickUp failed: deleteArea = null");
            return;
        }
        if (deleteButtonRect.contains((int) this.initialTouchX, (int) this.initialTouchY)) {
            Log.w(this.TAG, "onClickUp skipped: click inside the delete button. clickX = " + this.initialTouchX + ", clickY = " + this.initialTouchY);
            return;
        }
        Log.i(this.TAG, "click outside! cancel delete layer in " + this.deletedX + ", " + this.deletedY + "~");
        RecyclerView.ViewHolder viewHolder = getViewHolder(this.deletedX, this.deletedY);
        if (viewHolder != null) {
            if (viewHolder instanceof BaseItemViewHolder) {
                ((BaseItemViewHolder) viewHolder).showDeleteLayer(false);
                return;
            }
            return;
        }
        Log.w(this.TAG, "click outside! cancel delete layer in " + this.deletedX + ", " + this.deletedY + " failed: holder not found.");
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onCurrentTemplateScreenshot(BaseTemplateView lockTemplateView, BaseTemplateView baseTemplateView, CommonConfig templateConfig) {
        Intrinsics.checkNotNullParameter(lockTemplateView, "lockTemplateView");
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        CrossViewPagerCallback crossViewPagerCallback = this.externalCallback;
        if (crossViewPagerCallback != null) {
            crossViewPagerCallback.onCurrentTemplateScreenshot(lockTemplateView, baseTemplateView, templateConfig);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.viewPager.setOffscreenPageLimit(1);
        this.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.miui.keyguard.editor.homepage.view.CrossViewPager.onFinishInflate.1
            @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int i) {
                CrossViewPager.this.verticalScrollState = i;
                CrossViewPager crossViewPager = CrossViewPager.this;
                crossViewPager.onPageScrollStateChanged(1, requiredSelectedForFloor(crossViewPager.viewPager.getCurrentItem()), CrossViewPager.this.viewPager.getCurrentItem(), i);
            }

            @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i) {
                CrossViewPager.this.onPageSelected(1, requiredSelectedForFloor(i), i, CrossViewPager.this.verticalScrollState);
            }

            public final int requiredSelectedForFloor(int i) {
                FloorViewHolder floorViewHolder = CrossViewPager.this.getFloorViewHolder(i);
                if (floorViewHolder != null) {
                    return floorViewHolder.getSelectedPosition();
                }
                return -1;
            }
        });
        initPagerTransformers();
        this.viewPager.setPagerSnapHelper(new DampPagerSnapHelperImpl(this.viewPager));
        this.verticalAnimController = DeviceUtil.INSTANCE.isLiteDevice() ? new LiteVerticalScrollAnimController(this) : new VerticalScrollAnimController(this);
        this.springLayout.setSpringTouchManager(this.springTouchManager);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onHistoryTemplateReady() {
        CrossViewPagerCallback.DefaultImpls.onHistoryTemplateReady(this);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onHomeWallpaperDarkResultReady() {
        CrossViewPagerCallback.DefaultImpls.onHomeWallpaperDarkResultReady(this);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onMyHomeTemplateCreated(BaseTemplateView baseTemplateView) {
        CrossViewPagerCallback.DefaultImpls.onMyHomeTemplateCreated(this, baseTemplateView);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onMyLockTemplateCreated(BaseTemplateView baseTemplateView, CommonConfig commonConfig) {
        CrossViewPagerCallback crossViewPagerCallback = this.externalCallback;
        if (crossViewPagerCallback != null) {
            CrossViewPagerCallback.DefaultImpls.onMyLockTemplateCreated$default(crossViewPagerCallback, baseTemplateView, null, 2, null);
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageDeleteCompleted(int i, int i2, TemplateItemBean templateItemBean) {
        CrossViewPagerCallback.DefaultImpls.onPageDeleteCompleted(this, i, i2, templateItemBean);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public boolean onPageDeleted(final int i, final int i2, final TemplateItemBean templateItemBean) {
        Log.i(this.TAG, "onPageDeleted -> x = " + i + ", y = " + i2);
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.homepage.view.CrossViewPager$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return CrossViewPager.onPageDeleted$lambda$11(this.f$0, i, i2, templateItemBean);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.homepage.view.CrossViewPager$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CrossViewPager.onPageDeleted$lambda$15(this.f$0, i, i2, templateItemBean, (Boolean) obj);
            }
        });
        return true;
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageEnterDeleteMode(int i, int i2, View deleteView, boolean z) {
        Intrinsics.checkNotNullParameter(deleteView, "deleteView");
        CrossViewPagerCallback.DefaultImpls.onPageEnterDeleteMode(this, i, i2, deleteView, z);
        Log.i(this.TAG, "onPageEnterDeleteMode -> x: " + i + ", y: " + i2 + ", isEnterDeleteMode: " + z);
        if (z) {
            this.deletedX = i;
            this.deletedY = i2;
            this.deletedButton = deleteView;
            this.isTouchCompletedForDelete = false;
            return;
        }
        this.deletedX = -1;
        this.deletedY = -1;
        this.deletedButton = null;
        this.isTouchCompletedForDelete = true;
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageScrollStateChanged(int i, int i2, int i3, int i4) {
        Log.i(this.TAG, "onPageScrollStateChanged -> orientation = " + i + ", x = " + i2 + ", y = " + i3 + ", state = " + i4);
        if (i4 == 2) {
            if (i == 0) {
                ScenarioRecognitionUtils.INSTANCE.setScenarioStateStart(442L);
            } else if (i == 1) {
                ScenarioRecognitionUtils.INSTANCE.setScenarioStateStart(443L);
            }
        }
        if (i4 == 0) {
            if (i == 0) {
                ScenarioRecognitionUtils.INSTANCE.setScenarioStateEnd(442L);
            } else if (i == 1) {
                ScenarioRecognitionUtils.INSTANCE.setScenarioStateEnd(443L);
            }
        }
        if (i4 == 1) {
            checkOrHideDeleteMenuForPosition(i2, i3);
        }
        BaseVerticalScrollAnimController baseVerticalScrollAnimController = this.verticalAnimController;
        if (baseVerticalScrollAnimController != null) {
            baseVerticalScrollAnimController.onPageScrollStateChanged(i, i2, i3, i4);
        }
        CrossViewPagerCallback crossViewPagerCallback = this.externalCallback;
        if (crossViewPagerCallback != null) {
            crossViewPagerCallback.onPageScrollStateChanged(i, i2, i3, i4);
        }
    }

    public final void onPageSelectInternal(int i, int i2, int i3, int i4) {
        recordPositions(i, i2, i3);
        Log.i(this.TAG, "onPageSelectInternal -> orientation = " + i + ", x = " + i2 + ", y = " + i3 + ", scrollState = " + i4 + ", lastSelectX = " + this.lastSelectX + ", lastSelectY = " + this.lastSelectY);
        Point pointCheckValidPosition = checkValidPosition(i, i2, i3, i4);
        if (pointCheckValidPosition != null) {
            int i5 = this.lastSelectX;
            int i6 = pointCheckValidPosition.x;
            if (i5 == i6) {
                int i7 = this.lastSelectY;
                int i8 = pointCheckValidPosition.y;
                if (i7 == i8) {
                    Log.w(this.TAG, "onPageSelectInternal: (x, y) has been selected. x = " + i6 + ", y = " + i8);
                    return;
                }
            }
            onValidPageSelectInternal(i, i6, pointCheckValidPosition.y, i4);
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageSelected(final int i, final int i2, final int i3, final int i4) {
        post(new Runnable() { // from class: com.miui.keyguard.editor.homepage.view.CrossViewPager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onPageSelectInternal(i, i2, i3, i4);
            }
        });
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageTransformed(int i, BaseViewHolder pageHolder, View page, float f, float f2, int i2) {
        Intrinsics.checkNotNullParameter(pageHolder, "pageHolder");
        Intrinsics.checkNotNullParameter(page, "page");
        CrossViewPagerCallback crossViewPagerCallback = this.externalCallback;
        if (crossViewPagerCallback != null) {
            crossViewPagerCallback.onPageTransformed(i, pageHolder, page, f, f2, i2);
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onTemplateCustomized(int i, int i2, TemplateItemBean templateItemBean, int i3) {
        Log.i(this.TAG, "onTemplateCustomized -> x = " + i + ", y = " + i2);
        checkOrHideDeleteMenuForPosition(i, i2);
        CrossViewPagerCallback crossViewPagerCallback = this.externalCallback;
        if (crossViewPagerCallback != null) {
            crossViewPagerCallback.onTemplateCustomized(i, i2, templateItemBean, i3);
        }
    }

    public final void onValidPageSelectInternal(int i, int i2, int i3, int i4) {
        Log.i(this.TAG, "onValidPageSelectInternal -> orientation = " + i + ", x = " + i2 + ", y = " + i3 + ", scrollState = " + i4 + ", lastSelectX = " + this.lastSelectX + ", lastSelectY = " + this.lastSelectY);
        if (i4 == 0) {
            verticalPageSelectChanged(i2, i3, i4);
            horizontalPageSelectChanged(i2, i3);
        } else if (i == 1) {
            verticalPageSelectChanged(i2, i3, i4);
        } else {
            horizontalPageSelectChanged(i2, i3);
        }
        this.lastSelectX = i2;
        this.lastSelectY = i3;
        CrossViewPagerCallback crossViewPagerCallback = this.externalCallback;
        if (crossViewPagerCallback != null) {
            crossViewPagerCallback.onPageSelected(i, i2, i3, i4);
        }
    }

    public final void performFinishInflated() {
        onFinishInflate();
    }

    public final void processScreenConfigurationChanged() {
        RecyclerView.RecycledViewPool recycledViewPool;
        this.verticalScrollState = 0;
        clearLastSelectPosition();
        this.deletedX = -1;
        this.deletedY = -1;
        this.deletedButton = null;
        this.isTouchCompletedForDelete = false;
        this.initialTouchX = -1.0f;
        this.initialTouchY = -1.0f;
        this.verticalPosition = -1;
        this.horizontalPositions.clear();
        setFirstSelect(true);
        initPagerTransformers();
        RecyclerView recyclerViewInViewPager = getRecyclerViewInViewPager();
        if (recyclerViewInViewPager != null && (recycledViewPool = recyclerViewInViewPager.getRecycledViewPool()) != null) {
            recycledViewPool.clear();
        }
        BaseVerticalScrollAnimController baseVerticalScrollAnimController = this.verticalAnimController;
        if (baseVerticalScrollAnimController != null) {
            baseVerticalScrollAnimController.processScreenConfigurationChanged();
        }
    }

    public final void recordPositions(int i, int i2, int i3) {
        if (i != 0) {
            if (i != 1) {
                return;
            }
            this.verticalPosition = i3;
        } else {
            this.horizontalPositions.put(Integer.valueOf(i3), Integer.valueOf(i2));
        }
    }

    @Override // com.miui.keyguard.editor.homepage.util.SelectItemProvider
    public Pair requireScreenshotPreview() {
        int currentItem = this.viewPager.getCurrentItem();
        FloorViewHolder floorViewHolder = getFloorViewHolder(currentItem);
        if (floorViewHolder == null) {
            Log.w(this.TAG, "getCurrentScreenshot failed: floor view holder not found for position: " + currentItem);
            return null;
        }
        int selectedPosition = floorViewHolder.getSelectedPosition();
        RecyclerView.ViewHolder viewHolder = floorViewHolder.getViewHolder(selectedPosition);
        if (!(viewHolder instanceof BaseItemViewHolder)) {
            Log.w(this.TAG, "requireScreenshotPreviewView failed: template view holder not found for x: " + selectedPosition + ", y: " + currentItem);
            return null;
        }
        BaseItemViewHolder baseItemViewHolder = (BaseItemViewHolder) viewHolder;
        Log.i(this.TAG, "apply template at position: x = " + baseItemViewHolder.getX() + ", y = " + baseItemViewHolder.getY());
        return new Pair(baseItemViewHolder.getScreenshotPreviewView(), (TemplateItemBean) baseItemViewHolder.getItemData());
    }

    public final void resetAdapter(List newData) {
        Intrinsics.checkNotNullParameter(newData, "newData");
        clearLastSelectPosition();
        FloorAdapter floorAdapter = new FloorAdapter();
        floorAdapter.setCallback(this);
        this.adapter = floorAdapter;
        floorAdapter.setData(newData);
        this.viewPager.setAdapter(this.adapter);
    }

    public final void resetScrollEventTarget() {
        this.viewPager.resetScrollEventTarget();
    }

    public final void selectTargetFloor(int i) {
        if (i < 0 || i >= this.adapter.getData().size()) {
            return;
        }
        this.viewPager.setCurrentItem(i, false);
    }

    public final void setData(@NotNull List<FloorItemBean> newData) {
        Intrinsics.checkNotNullParameter(newData, "newData");
        clearLastSelectPosition();
        this.adapter.setData(newData);
        this.viewPager.setAdapter(this.adapter);
    }

    public final void setFirstSelect(boolean z) {
        this.isFirstSelect = z;
    }

    public final void setSpringScrollListener(@NotNull SpringScrollListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        VerticalSpringTouchManager verticalSpringTouchManager = this.springTouchManager;
        if (verticalSpringTouchManager != null) {
            verticalSpringTouchManager.setSpringScrollListener(listener);
        }
    }

    public final void setVerticalScrollAnimationCallback(@NotNull OnVerticalScrollAnimationCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        BaseVerticalScrollAnimController baseVerticalScrollAnimController = this.verticalAnimController;
        if (baseVerticalScrollAnimController != null) {
            baseVerticalScrollAnimController.setCallback(callback);
        }
    }

    public final void setViewPagerCallback(@NotNull CrossViewPagerCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.externalCallback = callback;
    }

    public final void verticalPageSelectChanged(int i, int i2, int i3) {
        BaseVerticalScrollAnimController baseVerticalScrollAnimController = this.verticalAnimController;
        if (baseVerticalScrollAnimController != null) {
            baseVerticalScrollAnimController.onPageSelected(1, i, i2, i3);
        }
        if (i3 == 0) {
            RecyclerView.ViewHolder viewHolder = getViewHolder(i, i2);
            if (viewHolder instanceof BaseItemViewHolder) {
                if (this.isFirstSelect) {
                    ((BaseItemViewHolder) viewHolder).setSelectedUI(true);
                    this.isFirstSelect = false;
                } else {
                    ((BaseItemViewHolder) viewHolder).onViewHolderSelectChanged(true);
                }
            }
            int i4 = i2 + 1;
            FloorViewHolder floorViewHolder = getFloorViewHolder(i4);
            RecyclerView.ViewHolder viewHolder2 = getViewHolder(floorViewHolder != null ? floorViewHolder.getSelectedPosition() : -1, i4);
            if (viewHolder2 instanceof BaseItemViewHolder) {
                ((BaseItemViewHolder) viewHolder2).onViewHolderSelectChanged(false);
            }
            int i5 = i2 + 2;
            FloorViewHolder floorViewHolder2 = getFloorViewHolder(i5);
            RecyclerView.ViewHolder viewHolder3 = getViewHolder(floorViewHolder2 != null ? floorViewHolder2.getSelectedPosition() : -1, i5);
            if (viewHolder3 instanceof BaseItemViewHolder) {
                ((BaseItemViewHolder) viewHolder3).onViewHolderSelectChanged(false);
            }
        }
        boolean z = (this.lastSelectX == -1 && this.lastSelectY == -1) ? false : true;
        FloorViewHolder floorViewHolder3 = getFloorViewHolder(i2);
        if (floorViewHolder3 != null) {
            floorViewHolder3.setFloorSelected(true);
            floorViewHolder3.getTitleController().showTitleModule(z);
        }
        Iterator it = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(i2 - 1), Integer.valueOf(i2 + 1)}).iterator();
        while (it.hasNext()) {
            FloorViewHolder floorViewHolder4 = getFloorViewHolder(((Number) it.next()).intValue());
            if (floorViewHolder4 != null) {
                floorViewHolder4.setFloorSelected(false);
                floorViewHolder4.getTitleController().hideTitleModule(z);
            }
        }
    }
}
