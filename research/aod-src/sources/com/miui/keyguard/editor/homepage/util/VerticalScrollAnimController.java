package com.miui.keyguard.editor.homepage.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.homepage.view.CrossViewPager;
import com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.FloorViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder;
import com.miui.keyguard.editor.homepage.view.viewpager.DampViewPager2;
import com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback;
import com.miui.keyguard.editor.homepage.view.viewpager.ScrollHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import miuix.animation.Folme;
import miuix.animation.FolmeStyle;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;

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
/* JADX INFO: compiled from: VerticalScrollAnimController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class VerticalScrollAnimController extends BaseVerticalScrollAnimController {
    public static final Companion Companion = new Companion(null);
    public final boolean isRtl;
    public float neighboringLeftInitialTranslateX;
    public float neighboringRightInitialTranslateX;

    /* JADX INFO: compiled from: VerticalScrollAnimController.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VerticalScrollAnimController(final CrossViewPager crossViewPager) {
        super(crossViewPager);
        Intrinsics.checkNotNullParameter(crossViewPager, "crossViewPager");
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = crossViewPager.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.neighboringLeftInitialTranslateX = resourcesProvider.provideLeftPageInitialTranslateX(context);
        Context context2 = crossViewPager.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        this.neighboringRightInitialTranslateX = resourcesProvider.provideRightPageInitialTranslateX(context2);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context3 = crossViewPager.getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        this.isRtl = deviceUtil.isRtl(context3);
        getScrollHelper().setPageScrollCallback(new ScrollCallback() { // from class: com.miui.keyguard.editor.homepage.util.VerticalScrollAnimController.1
            public final void checkOrRestoreCurrentSelectedTemplate(int i) {
                try {
                    if (VerticalScrollAnimController.this.getLayoutManager() == null) {
                        Log.w("Keyguard-Editor-CrossListAnimController", "checkOrRestoreCurrentSelectedTemplate failed: layoutManager = null");
                        return;
                    }
                    FloorViewHolder floorViewHolder = crossViewPager.getFloorViewHolder(i);
                    if (floorViewHolder == null) {
                        Log.w("Keyguard-Editor-CrossListAnimController", "checkOrRestoreCurrentSelectedTemplate failed: currentSelectedFloorHolder not found at " + i);
                        return;
                    }
                    int selectedPosition = floorViewHolder.getSelectedPosition();
                    RecyclerView.ViewHolder viewHolder = floorViewHolder.getViewHolder(selectedPosition);
                    if (!(viewHolder instanceof BaseItemViewHolder)) {
                        Log.w("Keyguard-Editor-CrossListAnimController", "checkOrRestoreCurrentSelectedTemplate failed: selectedCurrentHolder at (" + selectedPosition + ", " + i + ") is " + viewHolder);
                        return;
                    }
                    Log.i("Keyguard-Editor-CrossListAnimController", "start restore anim state in " + i + " when idled.");
                    VerticalScrollAnimController.this.completeSelectHolderAnimState((BaseItemViewHolder) viewHolder);
                    RecyclerView.ViewHolder viewHolder2 = floorViewHolder.getViewHolder(VerticalScrollAnimController.this.isRtl ? selectedPosition + 1 : selectedPosition - 1);
                    if (viewHolder2 instanceof BaseItemViewHolder) {
                        ((BaseItemViewHolder) viewHolder2).itemView.setTranslationX(VerticalScrollAnimController.this.neighboringLeftInitialTranslateX);
                    }
                    RecyclerView.ViewHolder viewHolder3 = floorViewHolder.getViewHolder(VerticalScrollAnimController.this.isRtl ? selectedPosition - 1 : selectedPosition + 1);
                    if (viewHolder3 instanceof BaseItemViewHolder) {
                        ((BaseItemViewHolder) viewHolder3).itemView.setTranslationX(VerticalScrollAnimController.this.neighboringRightInitialTranslateX);
                    }
                } catch (Exception e) {
                    Log.e("Keyguard-Editor-CrossListAnimController", "checkOrRestoreCurrentSelectedTemplate", e);
                }
            }

            public final void checkOrRestoreLastSelectedTemplateState(int i) {
                try {
                    if (i == crossViewPager.getSelectedFloorPosition()) {
                        Log.w("Keyguard-Editor-CrossListAnimController", "checkOrRestoreLastSelectedTemplateState skip: position(" + i + ") is current selected floor");
                        return;
                    }
                    FloorViewHolder floorViewHolder = crossViewPager.getFloorViewHolder(i);
                    if (floorViewHolder == null) {
                        Log.w("Keyguard-Editor-CrossListAnimController", "foldNeighboringTemplates failed: floorHolder not found at " + i);
                        return;
                    }
                    RecyclerView.LayoutManager layoutManager = floorViewHolder.getLayoutManager();
                    int selectedPosition = floorViewHolder.getSelectedPosition();
                    RecyclerView.ViewHolder viewHolder = floorViewHolder.getViewHolder(selectedPosition);
                    View view = viewHolder != null ? viewHolder.itemView : null;
                    if (view == null) {
                        Log.w("Keyguard-Editor-CrossListAnimController", "foldNeighboringTemplates failed: centerPageView = null. positionX = " + selectedPosition);
                        return;
                    }
                    view.setZ(1.0f);
                    RecyclerView.ViewHolder viewHolder2 = floorViewHolder.getViewHolder(selectedPosition - 1);
                    if (viewHolder2 instanceof BaseItemViewHolder) {
                        View itemView = ((BaseItemViewHolder) viewHolder2).itemView;
                        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
                        itemView.setTranslationX(VerticalScrollAnimController.this.calculateCenterDistance(itemView, view, layoutManager));
                        itemView.setZ(0.0f);
                    }
                    RecyclerView.ViewHolder viewHolder3 = floorViewHolder.getViewHolder(selectedPosition + 1);
                    if (viewHolder3 instanceof BaseItemViewHolder) {
                        View itemView2 = ((BaseItemViewHolder) viewHolder3).itemView;
                        Intrinsics.checkNotNullExpressionValue(itemView2, "itemView");
                        itemView2.setTranslationX(VerticalScrollAnimController.this.calculateCenterDistance(itemView2, view, layoutManager));
                        itemView2.setZ(0.0f);
                    }
                } catch (Exception e) {
                    Log.e("Keyguard-Editor-CrossListAnimController", "foldNeighboringTemplates", e);
                }
            }

            @Override // com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback
            public void onFling(int i, int i2) {
                ScrollCallback.DefaultImpls.onFling(this, i, i2);
                try {
                    if (VerticalScrollAnimController.this.getLayoutManager() == null) {
                        Log.w("Keyguard-Editor-CrossListAnimController", "onFling failed: layoutManager = null");
                        return;
                    }
                    if (!VerticalScrollAnimController.this.getScrollHelper().isOverlayScrollVertically()) {
                        Log.i("Keyguard-Editor-CrossListAnimController", "onFling failed: isOverlayScrollVertically = false");
                        return;
                    }
                    int iPredictSnapTargetPosition = VerticalScrollAnimController.this.getScrollHelper().predictSnapTargetPosition(VerticalScrollAnimController.this.getLayoutManager());
                    Log.i("Keyguard-Editor-CrossListAnimController", "onFling -> snapTargetPosition = " + iPredictSnapTargetPosition);
                    if (iPredictSnapTargetPosition == VerticalScrollAnimController.this.getLastSelectedPosition()) {
                        Log.w("Keyguard-Editor-CrossListAnimController", "onFling failed: snapTargetPosition = lastSelectedPosition.");
                    } else {
                        VerticalScrollAnimController.this.startTransformerAnimationsForSnapTargetPosition(iPredictSnapTargetPosition);
                    }
                } catch (Exception e) {
                    Log.e("Keyguard-Editor-CrossListAnimController", "onFling", e);
                }
            }

            @Override // com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback
            public void onPageScrollStateChanged(int i) {
                ScrollCallback.DefaultImpls.onPageScrollStateChanged(this, i);
                if (i == 0) {
                    if (VerticalScrollAnimController.this.isFlingAnimationFinished()) {
                        VerticalScrollAnimController.this.onVerticalScrollStateChanged(2);
                    }
                    checkOrRestoreLastSelectedTemplateState(VerticalScrollAnimController.this.getLastSelectedPositionYForReset());
                    VerticalScrollAnimController.this.setLastSelectedPositionYForReset(-1);
                    if (!VerticalScrollAnimController.this.isFlingAnimationFinished()) {
                        Log.i("Keyguard-Editor-CrossListAnimController", "onIdle -> isFlingAnimationFinished: false");
                        return;
                    }
                    int selectedFloorPosition = crossViewPager.getSelectedFloorPosition();
                    Log.i("Keyguard-Editor-CrossListAnimController", "onIdle -> check restore state for position: " + selectedFloorPosition);
                    checkOrRestoreCurrentSelectedTemplate(selectedFloorPosition);
                    return;
                }
                if (i != 1) {
                    return;
                }
                ScrollHelper.Companion.setInScroll(true);
                try {
                    if (VerticalScrollAnimController.this.getLayoutManager() == null) {
                        Log.w("Keyguard-Editor-CrossListAnimController", "onPageScrollStateChanged failed: layoutManager = null");
                        return;
                    }
                    if (VerticalScrollAnimController.this.getLastSelectedPosition() == VerticalScrollAnimController.this.getScrollHelper().getStartDragClosestChildPosition()) {
                        VerticalScrollAnimController.this.postPrepareSnapNextFloorState(crossViewPager.getSelectedFloorPosition() + 1);
                        return;
                    }
                    Log.i("Keyguard-Editor-CrossListAnimController", "start drag position is not selected position start transAnim");
                    VerticalScrollAnimController verticalScrollAnimController = VerticalScrollAnimController.this;
                    RecyclerView.ViewHolder floorCurrentHolder = verticalScrollAnimController.getFloorCurrentHolder(verticalScrollAnimController.getLastSelectedPosition());
                    if (floorCurrentHolder != null) {
                        VerticalScrollAnimController verticalScrollAnimController2 = VerticalScrollAnimController.this;
                        if (floorCurrentHolder instanceof BaseItemViewHolder) {
                            verticalScrollAnimController2.resetCenterPageState(verticalScrollAnimController2.getLastSelectedPosition(), (BaseItemViewHolder) floorCurrentHolder);
                        }
                    }
                    VerticalScrollAnimController verticalScrollAnimController3 = VerticalScrollAnimController.this;
                    verticalScrollAnimController3.startTransformerAnimationsForSnapTargetPosition(verticalScrollAnimController3.getScrollHelper().getStartDragClosestChildPosition());
                    VerticalScrollAnimController verticalScrollAnimController4 = VerticalScrollAnimController.this;
                    verticalScrollAnimController4.setLastSelectedPosition(verticalScrollAnimController4.getScrollHelper().getStartDragClosestChildPosition());
                } catch (Exception e) {
                    Log.e("Keyguard-Editor-CrossListAnimController", "onPageScrollStateChanged", e);
                } finally {
                    VerticalScrollAnimController.this.onVerticalScrollStateChanged(1);
                }
            }

            @Override // com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback
            public void onPageScrolled(int i, int i2, int i3) {
                ScrollCallback.DefaultImpls.onPageScrolled(this, i, i2, i3);
            }

            @Override // com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback
            public void onPageSelectChanged(int i) {
                ScrollCallback.DefaultImpls.onPageSelectChanged(this, i);
                Log.i("Keyguard-Editor-CrossListAnimController", "onPageSelectChanged: " + i);
                if (VerticalScrollAnimController.this.getLastSelectedPosition() == i) {
                    Log.w("Keyguard-Editor-CrossListAnimController", "onPageSelectChanged failed: position: " + i + " not changed.");
                    return;
                }
                int lastSelectedPosition = VerticalScrollAnimController.this.getLastSelectedPosition();
                VerticalScrollAnimController.this.setLastSelectedPosition(i);
                try {
                    if (VerticalScrollAnimController.this.getLayoutManager() == null) {
                        Log.w("Keyguard-Editor-CrossListAnimController", "onPageSelectChanged failed: layoutManager = null");
                        return;
                    }
                    FloorViewHolder floorViewHolder = crossViewPager.getFloorViewHolder(lastSelectedPosition);
                    if (floorViewHolder == null) {
                        Log.w("Keyguard-Editor-CrossListAnimController", "onPageSelectChanged failed: lastSelectFloor not found at " + lastSelectedPosition);
                        return;
                    }
                    int selectedPosition = floorViewHolder.getSelectedPosition();
                    RecyclerView.ViewHolder viewHolder = floorViewHolder.getViewHolder(selectedPosition);
                    if (viewHolder instanceof BaseItemViewHolder) {
                        VerticalScrollAnimController.this.setLastSelectedPositionYForReset(lastSelectedPosition);
                        VerticalScrollAnimController.this.resetCenterPageState(lastSelectedPosition, (BaseItemViewHolder) viewHolder);
                        ((BaseItemViewHolder) viewHolder).setSelected(false);
                    } else {
                        Log.w("Keyguard-Editor-CrossListAnimController", "onPageSelectChanged failed: lastSelectCurrentHolder not found at " + selectedPosition);
                    }
                } catch (Exception e) {
                    Log.e("Keyguard-Editor-CrossListAnimController", "onPageSelectChanged", e);
                }
            }
        });
    }

    public static final void postPrepareSnapNextFloorState$lambda$0(VerticalScrollAnimController verticalScrollAnimController, int i) {
        try {
            FloorViewHolder floorViewHolder = verticalScrollAnimController.getCrossViewPager().getFloorViewHolder(i);
            if (floorViewHolder == null) {
                Log.w("Keyguard-Editor-CrossListAnimController", "postPrepareSnapNextFloorState failed: snapNextFloorHolder not found at " + i);
                return;
            }
            RecyclerView.LayoutManager layoutManager = floorViewHolder.getLayoutManager();
            int selectedPosition = floorViewHolder.getSelectedPosition();
            RecyclerView.ViewHolder viewHolder = floorViewHolder.getViewHolder(selectedPosition);
            if (!(viewHolder instanceof BaseItemViewHolder)) {
                Log.w("Keyguard-Editor-CrossListAnimController", "postPrepareSnapNextFloorState failed: snapNextCurrentHolder not found at " + selectedPosition);
                return;
            }
            View itemView = ((BaseItemViewHolder) viewHolder).itemView;
            Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
            Log.i("Keyguard-Editor-CrossListAnimController", "startDragging -> reset position: " + i);
            verticalScrollAnimController.resetCenterPageState(i, (BaseItemViewHolder) viewHolder);
            RecyclerView.ViewHolder viewHolder2 = floorViewHolder.getViewHolder(selectedPosition + (-1));
            if (viewHolder2 instanceof BaseItemViewHolder) {
                View itemView2 = ((BaseItemViewHolder) viewHolder2).itemView;
                Intrinsics.checkNotNullExpressionValue(itemView2, "itemView");
                itemView2.setTranslationX(verticalScrollAnimController.calculateCenterDistance(itemView2, itemView, layoutManager));
                itemView2.setZ(0.0f);
            }
            RecyclerView.ViewHolder viewHolder3 = floorViewHolder.getViewHolder(selectedPosition + 1);
            if (viewHolder3 instanceof BaseItemViewHolder) {
                View itemView3 = ((BaseItemViewHolder) viewHolder3).itemView;
                Intrinsics.checkNotNullExpressionValue(itemView3, "itemView");
                itemView3.setTranslationX(verticalScrollAnimController.calculateCenterDistance(itemView3, itemView, layoutManager));
                itemView3.setZ(0.0f);
            }
        } catch (Exception e) {
            Log.e("Keyguard-Editor-CrossListAnimController", "postPrepareSnapNextFloorState", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void resetCenterPageState(int i, BaseItemViewHolder baseItemViewHolder) {
        Log.i("Keyguard-Editor-CrossListAnimController", "resetCenterPageState position = " + i);
        View itemView = baseItemViewHolder.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        Folme.clean(itemView);
        itemView.setZ(1.0f);
        updatePageZIndexChanged(1.0f);
        PreviewHolder.DefaultImpls.switchHome$default(baseItemViewHolder, false, false, 2, null);
        resetCustomButton(baseItemViewHolder);
        getFloorHolderUnScheduledAnimCount().put(Integer.valueOf(i), 0);
    }

    public final int calculateCenterDistance(View view, View view2, RecyclerView.LayoutManager layoutManager) {
        return getChildCenter(view2, layoutManager) - getChildCenter(view, layoutManager);
    }

    public final void completeSelectHolderAnimState(BaseItemViewHolder baseItemViewHolder) {
        baseItemViewHolder.decorateCustomizeBackground();
        View customButton = baseItemViewHolder.getCustomButton();
        if (customButton != null) {
            Folme.clean(customButton);
            customButton.setAlpha(1.0f);
        }
    }

    public final int getChildCenter(View view, RecyclerView.LayoutManager layoutManager) {
        return getDecoratedStart(view, layoutManager) + (getDecoratedMeasurement(view, layoutManager) / 2);
    }

    public final int getDecoratedMeasurement(View view, RecyclerView.LayoutManager layoutManager) {
        try {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
            RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
            return (layoutManager != null ? layoutManager.getDecoratedMeasuredWidth(view) : view.getMeasuredWidth()) + ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-CrossListAnimController", "getDecoratedMeasurement", e);
            return 0;
        }
    }

    public final int getDecoratedStart(View view, RecyclerView.LayoutManager layoutManager) {
        try {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
            return (layoutManager != null ? layoutManager.getDecoratedLeft(view) : view.getLeft()) - ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) layoutParams)).leftMargin;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-CrossListAnimController", "getDecoratedStart", e);
            return 0;
        }
    }

    public final RecyclerView.ViewHolder getFloorCurrentHolder(int i) {
        FloorViewHolder floorViewHolder = getCrossViewPager().getFloorViewHolder(i);
        int selectedPosition = floorViewHolder != null ? floorViewHolder.getSelectedPosition() : 0;
        if (floorViewHolder != null) {
            return floorViewHolder.getViewHolder(selectedPosition);
        }
        return null;
    }

    public final void postPrepareSnapNextFloorState(final int i) {
        getCrossViewPager().post(new Runnable() { // from class: com.miui.keyguard.editor.homepage.util.VerticalScrollAnimController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                VerticalScrollAnimController.postPrepareSnapNextFloorState$lambda$0(this.f$0, i);
            }
        });
    }

    @Override // com.miui.keyguard.editor.homepage.util.BaseVerticalScrollAnimController
    public void processScreenConfigurationChanged() {
        Context context = getCrossViewPager().getContext();
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Intrinsics.checkNotNull(context);
        this.neighboringLeftInitialTranslateX = resourcesProvider.provideLeftPageInitialTranslateX(context);
        this.neighboringRightInitialTranslateX = resourcesProvider.provideRightPageInitialTranslateX(context);
        getScrollHelper().reset();
        setLastSelectedPositionYForReset(-1);
        setFlingAnimationFinished(true);
    }

    public final FolmeStyle startAnimationForSnapNeighboringPosition(final int i, BaseItemViewHolder baseItemViewHolder, int i2, final View view) {
        final View itemView = baseItemViewHolder.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        if (baseItemViewHolder.getTargetContentView() == null) {
            Log.w("Keyguard-Editor-CrossListAnimController", "startAnimationForSnapNeighboringPosition failed: targetContent = null");
            return null;
        }
        final float f = i2 > 0 ? this.neighboringLeftInitialTranslateX : this.neighboringRightInitialTranslateX;
        Folme.clean(itemView);
        AnimState animState = new AnimState("translate_from");
        ViewProperty viewProperty = ViewProperty.TRANSLATION_X;
        IStateStyle iStateStyle = Folme.useAt(itemView).state().setTo(animState.add(viewProperty, i2, new long[0])).to(new AnimState("translate_to").add(viewProperty, f, new long[0]), new AnimConfig().setSpecial(viewProperty, EaseManager.getStyle(-2, 0.95f, 0.4f), new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.homepage.util.VerticalScrollAnimController$startAnimationForSnapNeighboringPosition$config$1
            private final void restoreOriginal() {
                ScrollHelper.Companion.setInScroll(false);
                itemView.setTranslationX(f);
                view.setZ(0.0f);
                itemView.setZ(0.0f);
                this.updatePageZIndexChanged(0.0f);
                this.changeHorizontalHolderUnScheduledAnimCount(i, false);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                view.setZ(1.0f);
                itemView.setZ(0.0f);
                this.updatePageZIndexChanged(1.0f);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                restoreOriginal();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                restoreOriginal();
            }
        }));
        BaseVerticalScrollAnimController.changeHorizontalHolderUnScheduledAnimCount$default(this, i, false, 2, null);
        return iStateStyle;
    }

    public final void startTransformerAnimationsForSnapTargetPosition(int i) {
        try {
            FloorViewHolder floorViewHolder = getCrossViewPager().getFloorViewHolder(i);
            if (floorViewHolder == null) {
                Log.w("Keyguard-Editor-CrossListAnimController", "startTransformerAnimationsForSnapTargetPosition failed: snapTargetFloorHolder not found at " + i);
                return;
            }
            RecyclerView.LayoutManager layoutManager = floorViewHolder.getLayoutManager();
            int selectedPosition = floorViewHolder.getSelectedPosition();
            DampViewPager2 viewPager = floorViewHolder.getViewPager();
            if (viewPager == null) {
                viewPager = null;
            }
            Object viewHolder = floorViewHolder.getViewHolder(selectedPosition);
            if (!(viewHolder instanceof BaseItemViewHolder)) {
                Log.w("Keyguard-Editor-CrossListAnimController", "startTransformerAnimationsForSnapTargetPosition failed: snapTargetCurrentHolder at (" + i + ", " + selectedPosition + ") is " + viewHolder);
                return;
            }
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            if (viewPager != null) {
                viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.miui.keyguard.editor.homepage.util.VerticalScrollAnimController.startTransformerAnimationsForSnapTargetPosition.1
                    @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.OnPageChangeCallback
                    public void onPageScrollStateChanged(int i2) {
                        super.onPageScrollStateChanged(i2);
                        if (i2 == 1) {
                            if (objectRef.element == null && objectRef2.element == null) {
                                return;
                            }
                            Log.d("Keyguard-Editor-CrossListAnimController", "state:SCROLL_STATE_DRAGGING,cancel AnimationForSnapNeighboringPosition");
                            FolmeStyle folmeStyle = (FolmeStyle) objectRef.element;
                            if (folmeStyle != null) {
                                folmeStyle.cancel();
                            }
                            FolmeStyle folmeStyle2 = (FolmeStyle) objectRef2.element;
                            if (folmeStyle2 != null) {
                                folmeStyle2.cancel();
                            }
                            objectRef2.element = null;
                            objectRef.element = null;
                        }
                    }
                });
            }
            Integer num = (Integer) getFloorHolderUnScheduledAnimCount().get(Integer.valueOf(i));
            int iIntValue = num != null ? num.intValue() : 0;
            if (iIntValue > 0) {
                Log.w("Keyguard-Editor-CrossListAnimController", "floor " + i + " have " + iIntValue + " unScheduled Anim can not execute transformer anim !");
                return;
            }
            setFlingAnimationFinished(false);
            Log.i("Keyguard-Editor-CrossListAnimController", "start animation for position: " + i);
            View itemView = ((BaseItemViewHolder) viewHolder).itemView;
            Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
            PreviewHolder.DefaultImpls.switchHome$default((PreviewHolder) viewHolder, true, false, 2, null);
            startCustomButtonAnimationForSnapCurrentPosition(i, (BaseItemViewHolder) viewHolder);
            RecyclerView.ViewHolder viewHolder2 = floorViewHolder.getViewHolder(selectedPosition - 1);
            if (viewHolder2 instanceof BaseItemViewHolder) {
                View itemView2 = ((BaseItemViewHolder) viewHolder2).itemView;
                Intrinsics.checkNotNullExpressionValue(itemView2, "itemView");
                objectRef2.element = startAnimationForSnapNeighboringPosition(i, (BaseItemViewHolder) viewHolder2, calculateCenterDistance(itemView2, itemView, layoutManager), itemView);
            }
            RecyclerView.ViewHolder viewHolder3 = floorViewHolder.getViewHolder(selectedPosition + 1);
            if (viewHolder3 instanceof BaseItemViewHolder) {
                View itemView3 = ((BaseItemViewHolder) viewHolder3).itemView;
                Intrinsics.checkNotNullExpressionValue(itemView3, "itemView");
                objectRef.element = startAnimationForSnapNeighboringPosition(i, (BaseItemViewHolder) viewHolder3, calculateCenterDistance(itemView3, itemView, layoutManager), itemView);
            }
        } catch (Exception e) {
            Log.e("Keyguard-Editor-CrossListAnimController", "startTransformerAnimationsForSnapTargetPosition failed", e);
        }
    }

    public final void updatePageZIndexChanged(float f) {
        OnVerticalScrollAnimationCallback mCallback = getMCallback();
        if (mCallback != null) {
            mCallback.onPageZIndexChanged(f);
        }
    }
}
