package com.miui.keyguard.editor.settings;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.content.res.ResourcesCompat;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.history.common.ViewExtKt;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.homepage.view.CustomButtonView;
import com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder;
import com.miui.keyguard.editor.settings.util.DrawableUtilsKt;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.TalkBackUtil;
import com.miui.keyguard.editor.utils.ViewStylerKt;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;

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
/* JADX INFO: compiled from: SettingsTemplateAdapterAndHoldImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class BaseSettingsTemplateViewHolder extends BaseViewHolder<TemplateItemBean, CrossViewPagerCallback> implements ISettingsViewHolder {
    public final Context context;
    public final CustomButtonView customizeDesktopBtn;
    public final CustomButtonView customizeKeyguardBtn;
    public SettingsTemplateDataModelProvider dataModelProvider;
    public final FrameLayout desktopContainer;
    public final View desktopContainerStroke;
    public boolean homeIsDarkColor;
    public final FrameLayout keyguardContainer;
    public final View keyguardContainerStroke;
    public boolean lockIsDarkColor;
    public final ImageView loopFlagOnDesktop;
    public final ImageView loopFlagOnKeyguard;
    public int previewRadius;
    public Point realSizeOutValue;
    public final Resources resources;
    public int templateContainerHeight;
    public int templateContainerWidth;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.settings.BaseSettingsTemplateViewHolder$decorateCustomizeBackground$1, reason: invalid class name */
    /* JADX INFO: compiled from: SettingsTemplateAdapterAndHoldImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.settings.BaseSettingsTemplateViewHolder$decorateCustomizeBackground$1", f = "SettingsTemplateAdapterAndHoldImpl.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ boolean $isColorDark;
        public final /* synthetic */ View $view;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(View view, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$view = view;
            this.$isColorDark = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseSettingsTemplateViewHolder.this.new AnonymousClass1(this.$view, this.$isColorDark, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Context context = BaseSettingsTemplateViewHolder.this.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "<get-context>(...)");
            ViewUtil.INSTANCE.decorateCustomButton(this.$view, ViewStylerKt.canUseBlur(context), this.$isColorDark);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseSettingsTemplateViewHolder(SettingsTemplateDataModelProvider settingsTemplateDataModelProvider, View itemView) {
        super(itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        this.dataModelProvider = settingsTemplateDataModelProvider;
        Context context = itemView.getContext();
        this.context = context;
        Resources resources = itemView.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
        this.resources = resources;
        View viewFindViewById = itemView.findViewById(R.id.template_keyguard_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.keyguardContainer = (FrameLayout) viewFindViewById;
        View viewFindViewById2 = itemView.findViewById(R.id.template_desktop_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.desktopContainer = (FrameLayout) viewFindViewById2;
        View viewFindViewById3 = itemView.findViewById(R.id.template_keyguard_container_stroke);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        this.keyguardContainerStroke = viewFindViewById3;
        View viewFindViewById4 = itemView.findViewById(R.id.template_desktop_container_stroke);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
        this.desktopContainerStroke = viewFindViewById4;
        View viewFindViewById5 = itemView.findViewById(R.id.customize_keyguard_btn);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(...)");
        this.customizeKeyguardBtn = (CustomButtonView) viewFindViewById5;
        View viewFindViewById6 = itemView.findViewById(R.id.customize_desktop_btn);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(...)");
        this.customizeDesktopBtn = (CustomButtonView) viewFindViewById6;
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(context, "context");
        this.realSizeOutValue = settingsResourceProvider.requireDisplayRealSize(context);
        View viewFindViewById7 = itemView.findViewById(R.id.iv_loop_flag_keyguard);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(...)");
        this.loopFlagOnKeyguard = (ImageView) viewFindViewById7;
        View viewFindViewById8 = itemView.findViewById(R.id.iv_loop_flag_desktop);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(...)");
        this.loopFlagOnDesktop = (ImageView) viewFindViewById8;
        Intrinsics.checkNotNullExpressionValue(context, "context");
        this.templateContainerWidth = settingsResourceProvider.provideTemplateContainerWidth(context);
        Intrinsics.checkNotNullExpressionValue(context, "context");
        this.templateContainerHeight = settingsResourceProvider.provideTemplateContainerHeight(context);
        Intrinsics.checkNotNullExpressionValue(context, "context");
        this.previewRadius = settingsResourceProvider.provideTemplateRadius(context);
    }

    public static final boolean setViewTouchListener$lambda$4(Ref.BooleanRef booleanRef, BaseSettingsTemplateViewHolder baseSettingsTemplateViewHolder, View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNull(view);
        Intrinsics.checkNotNull(motionEvent);
        booleanRef.element = baseSettingsTemplateViewHolder.shouldDispatchCallback(view, motionEvent);
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x003a  */
    /* JADX WARN: Code duplicated, block: B:12:0x0042  */
    public static final Unit setViewTouchListener$lambda$5(Ref.BooleanRef booleanRef, BaseSettingsTemplateViewHolder baseSettingsTemplateViewHolder, int i, View it) {
        CrossViewPagerCallback crossViewPagerCallback;
        Intrinsics.checkNotNullParameter(it, "it");
        Log.i("BaseSettingsTemplateViewHolder", "OnClickListener clickEnable = " + booleanRef.element);
        if (booleanRef.element) {
            crossViewPagerCallback = (CrossViewPagerCallback) baseSettingsTemplateViewHolder.getCallback();
            if (crossViewPagerCallback != null) {
                crossViewPagerCallback.onTemplateCustomized(0, 0, (TemplateItemBean) baseSettingsTemplateViewHolder.getItemData(), i);
            }
        } else {
            TalkBackUtil talkBackUtil = TalkBackUtil.INSTANCE;
            Object systemService = baseSettingsTemplateViewHolder.context.getSystemService("accessibility");
            if (talkBackUtil.isTalkBackEnabled(systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null)) {
                crossViewPagerCallback = (CrossViewPagerCallback) baseSettingsTemplateViewHolder.getCallback();
                if (crossViewPagerCallback != null) {
                    crossViewPagerCallback.onTemplateCustomized(0, 0, (TemplateItemBean) baseSettingsTemplateViewHolder.getItemData(), i);
                }
            }
        }
        booleanRef.element = false;
        return Unit.INSTANCE;
    }

    public final void addTemplateView(FrameLayout container, View templateView) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        float f = 1;
        templateView.setForeground(DrawableUtilsKt.createDrawableWithStroke(this.previewRadius * RangesKt.coerceAtLeast(f / templateView.getScaleX(), f / templateView.getScaleY()), templateView.getResources().getDimensionPixelSize(R.dimen.kg_settings_template_stroke_width), ResourcesCompat.getColor(templateView.getResources(), R.color.kg_settings_stroke_color, templateView.getContext().getTheme())));
        templateView.setClipToOutline(true);
        RoundOutlineProvider roundOutlineProvider = new RoundOutlineProvider(this.previewRadius);
        roundOutlineProvider.setRadius(roundOutlineProvider.getRadius() * RangesKt.coerceAtLeast(f / templateView.getScaleX(), f / templateView.getScaleY()));
        templateView.setOutlineProvider(roundOutlineProvider);
        ViewUtil.INSTANCE.setSmoothCornerEnabled(templateView, true);
        container.addView(templateView);
    }

    public final void applyPressEffect(View view) {
        Folme.useAt(view).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).handleTouchOf(view, new AnimConfig[0]);
    }

    public final void decorateCustomizeBackground(View view, boolean z) {
        Intrinsics.checkNotNullParameter(view, "view");
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = this.context;
        Intrinsics.checkNotNullExpressionValue(context, "context");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new AnonymousClass1(view, z, null), 3, null);
    }

    public final Context getContext() {
        return this.context;
    }

    public final CustomButtonView getCustomizeDesktopBtn() {
        return this.customizeDesktopBtn;
    }

    public final CustomButtonView getCustomizeKeyguardBtn() {
        return this.customizeKeyguardBtn;
    }

    public final SettingsTemplateDataModelProvider getDataModelProvider() {
        return this.dataModelProvider;
    }

    public final FrameLayout getDesktopContainer() {
        return this.desktopContainer;
    }

    public final View getDesktopContainerStroke() {
        return this.desktopContainerStroke;
    }

    public final boolean getHomeIsDarkColor() {
        return this.homeIsDarkColor;
    }

    public final FrameLayout getKeyguardContainer() {
        return this.keyguardContainer;
    }

    public final View getKeyguardContainerStroke() {
        return this.keyguardContainerStroke;
    }

    public final boolean getLockIsDarkColor() {
        return this.lockIsDarkColor;
    }

    public final Point getRealSizeOutValue() {
        return this.realSizeOutValue;
    }

    public final int getTemplateContainerHeight() {
        return this.templateContainerHeight;
    }

    public final int getTemplateContainerWidth() {
        return this.templateContainerWidth;
    }

    @Override // com.miui.keyguard.editor.settings.ISettingsViewHolder
    public boolean isHomeDarkColor() {
        return ISettingsViewHolder.DefaultImpls.isHomeDarkColor(this);
    }

    public final boolean isInView(View view, int i, int i2) {
        Rect rect = new Rect();
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isInSingleHandMode(this.context)) {
            int i3 = deviceUtil.getScreenSize(this.context).y;
            iArr[1] = Math.min(iArr[1] + ((int) (((double) i3) * 0.4d)), i3 - view.getHeight());
        }
        int i4 = iArr[0];
        rect.set(i4, iArr[1], view.getWidth() + i4, iArr[1] + view.getHeight());
        return rect.contains(i, i2);
    }

    @Override // com.miui.keyguard.editor.settings.ISettingsViewHolder
    public boolean isLockDarkColor() {
        return ISettingsViewHolder.DefaultImpls.isLockDarkColor(this);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0037  */
    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderBound(TemplateItemBean templateItemBean, int i) {
        boolean z;
        super.onViewHolderBound((Object) templateItemBean, i);
        setX(i);
        setViewTouchListener(this.keyguardContainer, 1);
        setViewTouchListener(this.customizeKeyguardBtn, 1);
        setViewTouchListener(this.desktopContainer, 2);
        setViewTouchListener(this.customizeDesktopBtn, 2);
        boolean z2 = templateItemBean != null && templateItemBean.getItemType() == 1;
        if (z2) {
            SettingsTemplateDataModelProvider settingsTemplateDataModelProvider = this.dataModelProvider;
            z = settingsTemplateDataModelProvider != null ? settingsTemplateDataModelProvider.getGalleryOpenState() : false;
        }
        SettingsTemplateDataModelProvider settingsTemplateDataModelProvider2 = this.dataModelProvider;
        Log.d("SettingsLoopFlag", "onViewHolderBound: isCurrentTemplate=" + z2 + ", galleryOpen=" + (settingsTemplateDataModelProvider2 != null ? Boolean.valueOf(settingsTemplateDataModelProvider2.getGalleryOpenState()) : null) + ", showLoopFlag=" + z);
        if (z) {
            this.loopFlagOnKeyguard.setVisibility(0);
            this.loopFlagOnDesktop.setVisibility(0);
        } else {
            this.loopFlagOnKeyguard.setVisibility(8);
            this.loopFlagOnDesktop.setVisibility(8);
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderCreated(View itemView) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        Context context = this.context;
        Intrinsics.checkNotNullExpressionValue(context, "context");
        this.realSizeOutValue = settingsResourceProvider.requireDisplayRealSize(context);
        Context context2 = this.context;
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        this.templateContainerWidth = settingsResourceProvider.provideTemplateContainerWidth(context2);
        Context context3 = this.context;
        Intrinsics.checkNotNullExpressionValue(context3, "context");
        this.templateContainerHeight = settingsResourceProvider.provideTemplateContainerHeight(context3);
        Context context4 = this.context;
        Intrinsics.checkNotNullExpressionValue(context4, "context");
        this.previewRadius = settingsResourceProvider.provideTemplateRadius(context4);
        FrameLayout frameLayout = this.keyguardContainer;
        frameLayout.getLayoutParams().width = this.templateContainerWidth;
        frameLayout.getLayoutParams().height = this.templateContainerHeight;
        FrameLayout frameLayout2 = this.desktopContainer;
        frameLayout2.getLayoutParams().width = this.templateContainerWidth;
        frameLayout2.getLayoutParams().height = this.templateContainerHeight;
        Context context5 = this.context;
        Intrinsics.checkNotNullExpressionValue(context5, "context");
        int iProvideCustomMarginButtonBottom = settingsResourceProvider.provideCustomMarginButtonBottom(context5);
        ViewUtil.setMarginBottom(this.customizeKeyguardBtn, iProvideCustomMarginButtonBottom);
        ViewUtil.setMarginBottom(this.customizeDesktopBtn, iProvideCustomMarginButtonBottom);
        applyPressEffect(this.keyguardContainer);
        applyPressEffect(this.desktopContainer);
        applyPressEffect(this.customizeKeyguardBtn);
        applyPressEffect(this.customizeDesktopBtn);
    }

    public final void setCustomButtonVisible(boolean z) {
        int i = z ? 0 : 8;
        this.customizeKeyguardBtn.setVisibility(i);
        this.customizeDesktopBtn.setVisibility(i);
    }

    public final void setHomeIsDarkColor(boolean z) {
        this.homeIsDarkColor = z;
    }

    public final void setLockIsDarkColor(boolean z) {
        this.lockIsDarkColor = z;
    }

    public final void setViewTouchListener(View view, final int i) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.keyguard.editor.settings.BaseSettingsTemplateViewHolder$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return BaseSettingsTemplateViewHolder.setViewTouchListener$lambda$4(booleanRef, this, view2, motionEvent);
            }
        });
        ViewExtKt.clickNoRepeat$default(view, 0L, new Function1() { // from class: com.miui.keyguard.editor.settings.BaseSettingsTemplateViewHolder$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return BaseSettingsTemplateViewHolder.setViewTouchListener$lambda$5(booleanRef, this, i, (View) obj);
            }
        }, 1, null);
    }

    public final boolean shouldDispatchCallback(View view, MotionEvent motionEvent) {
        return motionEvent.getAction() == 1 && isInView(view, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
    }
}
