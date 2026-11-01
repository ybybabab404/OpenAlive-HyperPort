package com.miui.keyguard.editor.homepage.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwnerKt;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.ActivityUIAdapter;
import com.miui.keyguard.editor.base.TransitionKt;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import com.miui.keyguard.editor.data.util.DataUtilKt;
import com.miui.keyguard.editor.edit.base.TemplateApplierAnimatorKt;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.view.ClickHotAreaView;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.homepage.view.DeleteLayer;
import com.miui.keyguard.editor.homepage.view.generator.BaseItemViewFactoryImpl;
import com.miui.keyguard.editor.homepage.view.generator.FactoryParams;
import com.miui.keyguard.editor.homepage.view.viewpager.DampViewPager2;
import com.miui.keyguard.editor.utils.ClickUtil;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.ViewStylerKt;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.AlertDialogBuilder;
import com.miui.keyguard.editor.view.AlertDialogsKtKt;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.core.util.MiuiBlurUtils;

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
/* JADX INFO: compiled from: BaseAdapterAndHolder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public abstract class BaseItemViewHolder extends BaseViewHolder<TemplateItemBean, CrossViewPagerCallback> implements View.OnClickListener, View.OnLongClickListener, PreviewHolder {
    public static final Companion Companion = new Companion(null);
    public final String _tag;
    public boolean canLongClick;
    public final Context context;
    public int currentButtonStatus;
    public final TextView customButton;
    public ImageView deleteButton;
    public boolean deleteDialogShow;
    public DeleteLayer deleteLayer;
    public final ImageView depthVideoIcon;
    public boolean dismissingDeleteLayer;
    public final float homeScale;
    public RoundOutlineProvider homeTemplateOutline;
    public final LayoutInflater inflater;
    public boolean isCustomButtonDecorated;
    public boolean isDeleteComponentsInitialized;
    public boolean isOutsideTouchEvent;
    public boolean isShowHome;
    public final BaseItemViewFactoryImpl itemViewFactoryImpl;
    public final ImageView loopFlagIcon;
    public final FactoryParams params;
    public final float previewRadius;
    public final Point realSizeOutValue;
    public final Resources resources;
    public final FrameLayout templateContainer;
    public final float templateContainerHeight;
    public final float templateContainerPadding;
    public final float templateContainerWidth;
    public final ClickHotAreaView templateHotAreaView;
    public RoundOutlineProvider templateOutline;
    public View templateView;

    /* JADX INFO: compiled from: BaseAdapterAndHolder.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder$requestFocus$1, reason: invalid class name */
    /* JADX INFO: compiled from: BaseAdapterAndHolder.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder$requestFocus$1", f = "BaseAdapterAndHolder.kt", l = {380}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseItemViewHolder.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(700L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            BaseItemViewHolder.this.templateHotAreaView.requestFocus();
            ClickHotAreaView clickHotAreaView = BaseItemViewHolder.this.templateHotAreaView;
            if (clickHotAreaView != null) {
                clickHotAreaView.sendAccessibilityEvent(8);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseItemViewHolder(LayoutInflater inflater, ViewGroup parent, BaseItemViewFactoryImpl itemViewFactoryImpl, FactoryParams params) {
        super(itemViewFactoryImpl.create(params));
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(itemViewFactoryImpl, "itemViewFactoryImpl");
        Intrinsics.checkNotNullParameter(params, "params");
        this.inflater = inflater;
        this.itemViewFactoryImpl = itemViewFactoryImpl;
        this.params = params;
        this._tag = "Keyguard-Editor-BaseItemViewHolder";
        Context context = this.itemView.getContext();
        this.context = context;
        Resources resources = this.itemView.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
        this.resources = resources;
        View viewFindViewById = this.itemView.findViewById(R.id.kg_template_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.templateContainer = (FrameLayout) viewFindViewById;
        View viewFindViewById2 = this.itemView.findViewById(R.id.kg_template_click_area);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.templateHotAreaView = (ClickHotAreaView) viewFindViewById2;
        View viewFindViewById3 = this.itemView.findViewById(R.id.kg_tv_custom);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        this.customButton = (TextView) viewFindViewById3;
        this.depthVideoIcon = (ImageView) this.itemView.findViewById(R.id.kg_depth_video_custom);
        this.loopFlagIcon = (ImageView) this.itemView.findViewById(R.id.kg_loop_flag_icon);
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(context, "context");
        this.homeScale = resourcesProvider.provideHomeScale(context);
        Intrinsics.checkNotNullExpressionValue(context, "context");
        this.templateContainerWidth = resourcesProvider.provideTemplateContainerWidth(context);
        Intrinsics.checkNotNullExpressionValue(context, "context");
        this.templateContainerPadding = resourcesProvider.provideTemplateContainerPadding(context);
        Intrinsics.checkNotNullExpressionValue(context, "context");
        this.templateContainerHeight = resourcesProvider.provideTemplateContainerHeight(context, new FactoryParams(params.isPreset(), false, 2, null));
        Intrinsics.checkNotNullExpressionValue(context, "context");
        this.realSizeOutValue = resourcesProvider.provideRealSize(context);
        Intrinsics.checkNotNullExpressionValue(context, "context");
        this.previewRadius = resourcesProvider.providePreviewRadius(context);
        this.currentButtonStatus = -1;
        this.canLongClick = true;
    }

    public static final void showDeletePageDialog$lambda$3(Ref.BooleanRef booleanRef, DialogInterface dialogInterface, int i) {
        if (ClickUtil.INSTANCE.isFastDoubleClick()) {
            return;
        }
        booleanRef.element = true;
    }

    public static final void showDeletePageDialog$lambda$5(BaseItemViewHolder baseItemViewHolder, Ref.BooleanRef booleanRef, DialogInterface dialogInterface) {
        baseItemViewHolder.deleteDialogShow = false;
        baseItemViewHolder.showDeleteLayer(false);
        if (booleanRef.element) {
            baseItemViewHolder.deletePage();
        }
    }

    public static final void showDeletePageDialog$lambda$6(DialogInterface dialogInterface, int i) {
    }

    public final void addHomeTemplateView(View templateView, ViewGroup.LayoutParams layoutParams) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        templateView.setClipToOutline(true);
        RoundOutlineProvider roundOutlineProvider = this.homeTemplateOutline;
        if (roundOutlineProvider != null) {
            float f = 1;
            roundOutlineProvider.setRadius(roundOutlineProvider.getRadius() * RangesKt.coerceAtLeast(f / templateView.getScaleX(), f / templateView.getScaleY()));
        } else {
            roundOutlineProvider = null;
        }
        templateView.setOutlineProvider(roundOutlineProvider);
        ViewUtil.INSTANCE.setSmoothCornerEnabled(templateView, true);
        if (layoutParams == null) {
            this.templateContainer.addView(templateView, 0);
        } else {
            this.templateContainer.addView(templateView, 0, layoutParams);
        }
    }

    public final void addTemplateView(View templateView, ViewGroup.LayoutParams layoutParams) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        templateView.setClipToOutline(true);
        RoundOutlineProvider roundOutlineProvider = this.templateOutline;
        if (roundOutlineProvider != null) {
            float f = 1;
            roundOutlineProvider.setRadius(roundOutlineProvider.getRadius() * RangesKt.coerceAtLeast(f / templateView.getScaleX(), f / templateView.getScaleY()));
        } else {
            roundOutlineProvider = null;
        }
        templateView.setOutlineProvider(roundOutlineProvider);
        ViewUtil.INSTANCE.setSmoothCornerEnabled(templateView, true);
        this.templateView = templateView;
        if (layoutParams == null) {
            this.templateContainer.addView(templateView, 0);
        } else {
            this.templateContainer.addView(templateView, 0, layoutParams);
        }
        templateView.setImportantForAccessibility(2);
        this.templateContainer.setImportantForAccessibility(2);
    }

    public final void decorateCustomizeBackground() {
        if (this.isCustomButtonDecorated) {
            return;
        }
        this.isCustomButtonDecorated = ViewUtil.INSTANCE.decorateCustomButton(this.customButton, ViewStylerKt.canUseBlur(), isDarkWallpaper());
    }

    public final void deletePage() {
        CrossViewPagerCallback crossViewPagerCallback;
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        if (templateItemBean == null || templateItemBean.getItemType() != 2 || (crossViewPagerCallback = (CrossViewPagerCallback) getCallback()) == null) {
            return;
        }
        crossViewPagerCallback.onPageDeleted(getX(), getY(), templateItemBean);
    }

    public final void dismissDeleteLayerWithAnim() {
        final List listFilterNotNull = ArraysKt.filterNotNull(new View[]{this.deleteLayer, this.deleteButton});
        if (listFilterNotNull.isEmpty()) {
            return;
        }
        AnimState animState = new AnimState("dismiss_start");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, 1.0f, new long[0]);
        AnimState animStateAdd2 = new AnimState("dismiss_end").add(viewProperty, 0.0f, new long[0]);
        AnimConfig animConfig = TransitionKt.getAnimConfig(0.95f, 0.3f, new TransitionListener() { // from class: com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder$dismissDeleteLayerWithAnim$config$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                this.this$0.dismissingDeleteLayer = true;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                Iterator it = listFilterNotNull.iterator();
                while (it.hasNext()) {
                    ((View) it.next()).setVisibility(8);
                }
                this.this$0.dismissingDeleteLayer = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                Iterator it = listFilterNotNull.iterator();
                while (it.hasNext()) {
                    ((View) it.next()).setVisibility(8);
                }
                this.this$0.dismissingDeleteLayer = false;
            }
        });
        View[] viewArr = (View[]) listFilterNotNull.toArray(new View[0]);
        Folme.useAt((View[]) Arrays.copyOf(viewArr, viewArr.length)).state().setTo(animStateAdd).to(animStateAdd2, animConfig);
    }

    public final boolean getCanLongClick() {
        return this.canLongClick;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder
    public View getCustomButton() {
        return this.customButton;
    }

    public final List getDeleteLayerViews() {
        return CollectionsKt.listOfNotNull((Object[]) new View[]{this.deleteLayer, this.deleteButton});
    }

    public final float getHomeScale() {
        return this.homeScale;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder
    public View getItemView() {
        View itemView = this.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        return itemView;
    }

    public final FactoryParams getParams() {
        return this.params;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder
    public TemplateItemBean getPreviewData() {
        return (TemplateItemBean) getItemData();
    }

    public final Point getRealSizeOutValue() {
        return this.realSizeOutValue;
    }

    public final Resources getResources() {
        return this.resources;
    }

    public final View getScreenshotPreviewView() {
        return this.templateContainer.getChildAt(0);
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public View getTargetContentView() {
        return this.templateContainer;
    }

    public final float getTemplateContainerHeight() {
        return this.templateContainerHeight;
    }

    public final float getTemplateContainerPadding() {
        return this.templateContainerPadding;
    }

    public final float getTemplateContainerWidth() {
        return this.templateContainerWidth;
    }

    public final void initDeleteComponents() {
        if (this.isDeleteComponentsInitialized) {
            Log.w(this._tag, "initDeleteComponents returned: isDeleteComponentsInitialized");
            return;
        }
        if (this.deleteLayer == null) {
            DataUtilKt.throwIfDebug("have you create deleteLayer");
        }
        DeleteLayer deleteLayer = this.deleteLayer;
        if (deleteLayer != null) {
            deleteLayer.initialize();
        }
        this.isDeleteComponentsInitialized = true;
    }

    public final void interceptLongClick(boolean z) {
        Log.d(this._tag, "interceptLongClick isIntercept:" + z);
        this.canLongClick = z ^ true;
    }

    public final boolean isDarkWallpaper() {
        CommonConfig current;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        Bitmap currentWallpaper = null;
        if ((templateItemBean != null ? templateItemBean.getHistory() : null) != null) {
            TemplateHistoryConfig history = templateItemBean.getHistory();
            Intrinsics.checkNotNull(history);
            return history.isDarkWallpaper() != 0;
        }
        if ((templateItemBean != null ? templateItemBean.getPreset() : null) != null) {
            CommonUtils commonUtils = CommonUtils.INSTANCE;
            PresetTemplateConfig preset = templateItemBean.getPreset();
            Intrinsics.checkNotNull(preset);
            return commonUtils.isDarkWallpaper(preset.getPreviewImage());
        }
        if ((templateItemBean != null ? templateItemBean.getCurrent() : null) == null) {
            Log.e("BaseItemViewHolder", "bitmap is not found");
            return false;
        }
        if (this.currentButtonStatus == -1) {
            CommonUtils commonUtils2 = CommonUtils.INSTANCE;
            if (templateItemBean != null && (current = templateItemBean.getCurrent()) != null && (lockscreenInfo = current.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                currentWallpaper = wallpaperInfo.getCurrentWallpaper();
            }
            this.currentButtonStatus = commonUtils2.isDarkWallpaper(currentWallpaper) ? 1 : 0;
        }
        return this.currentButtonStatus == 1;
    }

    public final boolean isOutsideTouchEvent() {
        return this.isOutsideTouchEvent;
    }

    public final boolean isSelected() {
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        if (templateItemBean != null) {
            return templateItemBean.isSelected();
        }
        return false;
    }

    public final boolean isShowHome() {
        return this.isShowHome;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Integer numValueOf = view != null ? Integer.valueOf(view.getId()) : null;
        int i = R.id.kg_template_click_area;
        if (numValueOf == null || numValueOf.intValue() != i) {
            int i2 = R.id.kg_tv_custom;
            if (numValueOf == null || numValueOf.intValue() != i2) {
                int i3 = R.id.button_delete;
                if (numValueOf != null && numValueOf.intValue() == i3) {
                    showDeletePageDialog();
                    return;
                }
                int i4 = R.id.kg_delete_layer;
                if (numValueOf != null && numValueOf.intValue() == i4) {
                    showDeleteLayer(false);
                    return;
                }
                return;
            }
        }
        CrossViewPagerCallback crossViewPagerCallback = (CrossViewPagerCallback) getCallback();
        int x = getX();
        int y = getY();
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        Log.i(this._tag, "onClick@custom -> callback: " + crossViewPagerCallback + ", x: " + x + ", y: " + y);
        if (crossViewPagerCallback != null) {
            CrossViewPagerCallback.DefaultImpls.onTemplateCustomized$default(crossViewPagerCallback, x, y, templateItemBean, 0, 8, null);
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onPageTransformed(int i, float f, float f2, int i2) {
        super.onPageTransformed(i, f, f2, i2);
    }

    public final void onPreShowDeleteLayer() {
        initDeleteComponents();
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderBound(TemplateItemBean templateItemBean, int i) {
        super.onViewHolderBound((Object) templateItemBean, i);
        if (DampViewPager2.isSliding) {
            Log.d(this._tag, "View Pager is sliding");
        } else {
            setSelectedUI(templateItemBean != null ? templateItemBean.isSelected() : false);
        }
        setTemplateItemDescription(templateItemBean);
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderCreated(View itemView) {
        int iProvideHomeExtraHeight;
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        FrameLayout frameLayout = this.templateContainer;
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        layoutParams.height = (int) this.templateContainerHeight;
        frameLayout.setLayoutParams(layoutParams);
        Object parent = this.customButton.getParent();
        View view = parent instanceof View ? (View) parent : null;
        if (view != null) {
            ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
            FrameLayout.LayoutParams layoutParams3 = layoutParams2 instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams2 : null;
            if (layoutParams3 != null) {
                int dimensionPixelSize = this.resources.getDimensionPixelSize(R.dimen.kg_main_item_custom_button_margin_bottom);
                if (!this.params.isPreset() || this.params.isHistory()) {
                    ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
                    Context context = this.context;
                    Intrinsics.checkNotNullExpressionValue(context, "context");
                    iProvideHomeExtraHeight = resourcesProvider.provideHomeExtraHeight(context);
                } else {
                    iProvideHomeExtraHeight = 0;
                }
                layoutParams3.bottomMargin = dimensionPixelSize + iProvideHomeExtraHeight;
            }
        }
        this.templateOutline = new RoundOutlineProvider(this.previewRadius);
        this.homeTemplateOutline = new RoundOutlineProvider(this.previewRadius);
        this.customButton.setAlpha(0.0f);
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        ViewUtil.setThrottledClickListener$default(viewUtil, this.templateHotAreaView, this, 0L, 2, null);
        this.templateHotAreaView.setOnLongClickListener(this);
        this.templateContainer.setOnLongClickListener(this);
        ClickHotAreaView clickHotAreaView = this.templateHotAreaView;
        if (clickHotAreaView != null) {
            clickHotAreaView.setAccessibilityTraversalAfter(R.id.kg_tv_description);
        }
        ViewUtil.setThrottledClickListener$default(viewUtil, this.customButton, this, 0L, 2, null);
        ClickHotAreaView clickHotAreaView2 = this.templateHotAreaView;
        if (clickHotAreaView2 != null) {
            clickHotAreaView2.setImportantForAccessibility(2);
        }
    }

    public void onViewHolderSelectChanged(boolean z) {
        Log.i(this._tag, "onViewHolderSelectChanged X:" + getX() + " Y:" + getY() + " selected:" + z);
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        if (templateItemBean == null || templateItemBean.isSelected() != z) {
            if (z) {
                decorateCustomizeBackground();
                startCustomBtnShowAnimation();
                setCustomButtonVisible(shouldShowCustomButton());
            } else {
                MiuiBlurUtils.clearBackgroundBlur(this.customButton);
                TemplateApplierAnimatorKt.startAlphaAnimation$default(this.customButton, null, null, 3, null);
                this.isCustomButtonDecorated = false;
            }
            TemplateItemBean templateItemBean2 = (TemplateItemBean) getItemData();
            if (templateItemBean2 != null) {
                templateItemBean2.setSelected(z);
            }
        }
    }

    public final void requestFocus() {
        LifecycleCoroutineScope lifecycleScope;
        Context context = this.context;
        EditorActivity editorActivity = context instanceof EditorActivity ? (EditorActivity) context : null;
        if (editorActivity == null || (lifecycleScope = LifecycleOwnerKt.getLifecycleScope(editorActivity)) == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(lifecycleScope, Dispatchers.getMain(), null, new AnonymousClass1(null), 2, null);
    }

    public final void setBaseViewHolderAccess(boolean z) {
        View view = this.templateView;
        if (view != null) {
            view.setImportantForAccessibility(z ? 1 : 2);
        }
        ClickHotAreaView clickHotAreaView = this.templateHotAreaView;
        if (clickHotAreaView != null) {
            clickHotAreaView.setImportantForAccessibility(z ? 1 : 2);
        }
    }

    public final void setContentDescription(int i) {
        Resources resources;
        Resources resources2;
        ClickHotAreaView clickHotAreaView = this.templateHotAreaView;
        String string = null;
        if (clickHotAreaView != null) {
            Context context = this.context;
            clickHotAreaView.setContentDescription((context == null || (resources2 = context.getResources()) == null) ? null : resources2.getString(i));
        }
        ClickHotAreaView clickHotAreaView2 = this.templateHotAreaView;
        Context context2 = this.context;
        if (context2 != null && (resources = context2.getResources()) != null) {
            string = resources.getString(i);
        }
        clickHotAreaView2.setContentDescription(string);
    }

    public final void setCustomButtonVisible(boolean z) {
        int i = z ? 0 : 8;
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        Integer numValueOf = templateItemBean != null ? Integer.valueOf(templateItemBean.getItemType()) : null;
        this.customButton.setVisibility(i);
        this.templateHotAreaView.setVisibility((numValueOf == null || numValueOf.intValue() != -1) ? i : 0);
    }

    public final void setDepthVideoIcon(boolean z) {
        int i = z ? 0 : 8;
        ImageView imageView = this.depthVideoIcon;
        if (imageView != null) {
            imageView.setVisibility(i);
        }
    }

    public final void setLoopFlagIcon(boolean z) {
        int i = z ? 0 : 8;
        ImageView imageView = this.loopFlagIcon;
        if (imageView != null) {
            imageView.setVisibility(i);
        }
    }

    public final void setOutsideTouchEvent(boolean z) {
        this.isOutsideTouchEvent = z;
    }

    public final void setSelected(boolean z) {
        TemplateItemBean templateItemBean = (TemplateItemBean) getItemData();
        if (templateItemBean != null) {
            templateItemBean.setSelected(z);
        }
    }

    public final void setSelectedUI(boolean z) {
        Log.i(this._tag, "setSelectedUI x:" + getX() + " y:" + getY() + " is selected:" + z);
        if (z) {
            decorateCustomizeBackground();
            setCustomButtonVisible(shouldShowCustomButton());
        } else {
            MiuiBlurUtils.clearBackgroundBlur(this.customButton);
        }
        this.customButton.setAlpha(z ? 1.0f : 0.0f);
    }

    public final void setShowHome(boolean z) {
        this.isShowHome = z;
    }

    /* JADX WARN: Code duplicated, block: B:69:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:74:0x00d6  */
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
    public final void setTemplateItemDescription(TemplateItemBean templateItemBean) {
        String str;
        ClockInfo clockInfo;
        PresetTemplateConfig preset;
        CommonConfig accessConfig;
        TemplateConfig lockscreenInfo;
        PresetTemplateConfig preset2;
        CommonConfig accessConfig2;
        TemplateConfig lockscreenInfo2;
        TemplateHistoryConfig history;
        CommonConfig accessConfig3;
        TemplateConfig lockscreenInfo3;
        TemplateHistoryConfig history2;
        CommonConfig accessConfig4;
        TemplateConfig lockscreenInfo4;
        Integer templateNameForAccessibility;
        Integer numValueOf;
        CommonConfig current;
        TemplateConfig lockscreenInfo5;
        CommonConfig current2;
        TemplateConfig lockscreenInfo6;
        ClockInfo clockInfo2 = null;
        templateName = null;
        templateName = null;
        templateName = null;
        templateName = null;
        templateName = null;
        templateName = null;
        templateName = null;
        templateName = null;
        templateName = null;
        templateName = null;
        String templateName = null;
        Integer numValueOf2 = templateItemBean != null ? Integer.valueOf(templateItemBean.getItemType()) : null;
        if (numValueOf2 != null && numValueOf2.intValue() == 1) {
            clockInfo = (templateItemBean == null || (current2 = templateItemBean.getCurrent()) == null || (lockscreenInfo6 = current2.getLockscreenInfo()) == null) ? null : lockscreenInfo6.getClockInfo();
            if (templateItemBean != null && (current = templateItemBean.getCurrent()) != null && (lockscreenInfo5 = current.getLockscreenInfo()) != null) {
                templateName = lockscreenInfo5.getTemplateName();
            }
        } else {
            if (numValueOf2 == null || numValueOf2.intValue() != 2) {
                if (numValueOf2 != null && numValueOf2.intValue() == 3) {
                    clockInfo = (templateItemBean == null || (preset2 = templateItemBean.getPreset()) == null || (accessConfig2 = preset2.getAccessConfig()) == null || (lockscreenInfo2 = accessConfig2.getLockscreenInfo()) == null) ? null : lockscreenInfo2.getClockInfo();
                    if (templateItemBean != null && (preset = templateItemBean.getPreset()) != null && (accessConfig = preset.getAccessConfig()) != null && (lockscreenInfo = accessConfig.getLockscreenInfo()) != null) {
                        templateName = lockscreenInfo.getTemplateName();
                    }
                } else {
                    str = null;
                }
                templateNameForAccessibility = TemplateDataUtil.INSTANCE.getTemplateNameForAccessibility(clockInfo2, str);
                if (templateNameForAccessibility == null) {
                    if (templateItemBean == null && templateItemBean.getItemType() == -1) {
                        numValueOf = Integer.valueOf(R.string.kg_settings_template_new_match);
                    } else {
                        numValueOf = Integer.valueOf(R.string.kg_template_preview_content_description);
                    }
                    templateNameForAccessibility = numValueOf;
                }
                setContentDescription(templateNameForAccessibility.intValue());
            }
            clockInfo = (templateItemBean == null || (history2 = templateItemBean.getHistory()) == null || (accessConfig4 = history2.getAccessConfig()) == null || (lockscreenInfo4 = accessConfig4.getLockscreenInfo()) == null) ? null : lockscreenInfo4.getClockInfo();
            if (templateItemBean != null && (history = templateItemBean.getHistory()) != null && (accessConfig3 = history.getAccessConfig()) != null && (lockscreenInfo3 = accessConfig3.getLockscreenInfo()) != null) {
                templateName = lockscreenInfo3.getTemplateName();
            }
        }
        ClockInfo clockInfo3 = clockInfo;
        str = templateName;
        clockInfo2 = clockInfo3;
        templateNameForAccessibility = TemplateDataUtil.INSTANCE.getTemplateNameForAccessibility(clockInfo2, str);
        if (templateNameForAccessibility == null) {
            if (templateItemBean == null) {
                numValueOf = Integer.valueOf(R.string.kg_template_preview_content_description);
            } else {
                numValueOf = Integer.valueOf(R.string.kg_template_preview_content_description);
            }
            templateNameForAccessibility = numValueOf;
        }
        setContentDescription(templateNameForAccessibility.intValue());
    }

    public boolean shouldShowCustomButton() {
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004d  */
    public final void showDeleteLayer(boolean z) {
        BaseItemViewHolder baseItemViewHolder;
        CrossViewPagerCallback crossViewPagerCallback;
        if (z || this.deleteLayer != null) {
            if (this.deleteLayer == null) {
                BaseItemViewFactoryImpl baseItemViewFactoryImpl = this.itemViewFactoryImpl;
                View itemView = this.itemView;
                Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
                DeleteLayer deleteLayerGenerateDeleteLayer = baseItemViewFactoryImpl.generateDeleteLayer(itemView);
                this.deleteLayer = deleteLayerGenerateDeleteLayer;
                DeleteLayer deleteLayer = deleteLayerGenerateDeleteLayer != null ? (DeleteLayer) deleteLayerGenerateDeleteLayer.findViewById(R.id.kg_delete_layer) : null;
                this.deleteLayer = deleteLayer;
                this.deleteButton = deleteLayer != null ? (ImageView) deleteLayer.findViewById(R.id.button_delete) : null;
                DeleteLayer deleteLayer2 = this.deleteLayer;
                if (deleteLayer2 != null) {
                    deleteLayer2.setOnClickListener(this);
                }
                ImageView imageView = this.deleteButton;
                if (imageView != null) {
                    baseItemViewHolder = this;
                    ViewUtil.setThrottledClickListener$default(ViewUtil.INSTANCE, imageView, baseItemViewHolder, 0L, 2, null);
                } else {
                    baseItemViewHolder = this;
                }
            } else {
                baseItemViewHolder = this;
            }
            DeleteLayer deleteLayer3 = baseItemViewHolder.deleteLayer;
            boolean z2 = false;
            if (deleteLayer3 != null && deleteLayer3.getVisibility() == 0) {
                z2 = true;
            }
            if (z2 != z) {
                if (z) {
                    baseItemViewHolder.onPreShowDeleteLayer();
                    baseItemViewHolder.showDeleteLayerWithAnim();
                } else {
                    baseItemViewHolder.dismissDeleteLayerWithAnim();
                }
                ImageView imageView2 = baseItemViewHolder.deleteButton;
                if (imageView2 == null || (crossViewPagerCallback = (CrossViewPagerCallback) baseItemViewHolder.getCallback()) == null) {
                    return;
                }
                crossViewPagerCallback.onPageEnterDeleteMode(baseItemViewHolder.getX(), baseItemViewHolder.getY(), imageView2, z);
            }
        }
    }

    public final void showDeleteLayerWithAnim() {
        final List listFilterNotNull = ArraysKt.filterNotNull(new View[]{this.deleteLayer, this.deleteButton});
        if (listFilterNotNull.isEmpty()) {
            return;
        }
        AnimState animState = new AnimState("show_start");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, 0.0f, new long[0]);
        AnimState animStateAdd2 = new AnimState("show_end").add(viewProperty, 1.0f, new long[0]);
        AnimConfig animConfig = TransitionKt.getAnimConfig(0.95f, 0.3f, new TransitionListener() { // from class: com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder$showDeleteLayerWithAnim$config$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                Iterator it = listFilterNotNull.iterator();
                while (it.hasNext()) {
                    ((View) it.next()).setVisibility(0);
                }
            }
        });
        View[] viewArr = (View[]) listFilterNotNull.toArray(new View[0]);
        Folme.useAt((View[]) Arrays.copyOf(viewArr, viewArr.length)).state().setTo(animStateAdd).to(animStateAdd2, animConfig);
    }

    public final void showDeletePageDialog() {
        DeleteLayer deleteLayer;
        if (this.dismissingDeleteLayer || this.deleteDialogShow || (deleteLayer = this.deleteLayer) == null || deleteLayer.getVisibility() != 0) {
            return;
        }
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        Context context = this.context;
        Intrinsics.checkNotNullExpressionValue(context, "context");
        ActivityUIAdapter.Companion companion = ActivityUIAdapter.Companion;
        Context context2 = this.context;
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        AlertDialogsKtKt.adaptN8AlertDialog$default(new AlertDialogBuilder(context, companion.isLightTheme(context2) ? miuix.appcompat.R.style.AlertDialog_Theme_Light_Danger : miuix.appcompat.R.style.AlertDialog_Theme_Dark_Danger).setTitle(R.string.kg_delete_this_collocation).setMessage(R.string.kg_delete_this_collocation_desc).setPositiveButton(R.string.kg_main_item_delete_button_text, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BaseItemViewHolder.showDeletePageDialog$lambda$3(booleanRef, dialogInterface, i);
            }
        }).setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f$0.deleteDialogShow = true;
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                BaseItemViewHolder.showDeletePageDialog$lambda$5(this.f$0, booleanRef, dialogInterface);
            }
        }).setNegativeButton(R.string.kg_dialog_fashion_gallery_button_positive, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BaseItemViewHolder.showDeletePageDialog$lambda$6(dialogInterface, i);
            }
        }).show(), true, false, false, 12, null);
    }

    public final void startCustomBtnShowAnimation() {
        decorateCustomizeBackground();
        Folme.useAt(this.customButton).state().setup("startAlphaAnimation").add((FloatProperty) ViewProperty.ALPHA, 1.0f).to("startAlphaAnimation", new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.4f)));
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder
    public void switchHome(boolean z, boolean z2) {
    }
}
