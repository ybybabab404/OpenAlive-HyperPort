package com.miui.keyguard.editor.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.graphics.Rect;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.miui.clock.utils.DeviceConfig;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.ActivityUIAdapter;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.utils.AnimatorKt;
import com.miui.keyguard.editor.utils.BlendColorInfo;
import com.miui.keyguard.editor.utils.BlendMode;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.androidbasewidget.widget.ClearableEditText;
import miuix.animation.Folme;
import miuix.animation.listener.TransitionListener;
import miuix.bottomsheet.BottomSheetBehavior;
import miuix.bottomsheet.BottomSheetModal;

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
/* JADX INFO: compiled from: AutoBottomSheet.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class AutoBottomSheet {
    public final Activity activity;
    public int alignMode;
    public View anchorView;
    public int baseTemplateViewTopMargin;
    public Integer bottomMargin;
    public EditorBottomSheetModal bottomSheetModal;
    public final FrameLayout container;
    public int containerBackgroundLiteColor;
    public int containerBackgroundPrimaryColor;
    public int containerBackgroundSecondaryColor;
    public View contentView;
    public final ViewGroup decorView;
    public int keyboardHeight;
    public List mEditList;
    public int marginBottom;
    public int marginEnd;
    public int marginStart;
    public int marginTop;
    public boolean needDelayRelease;
    public int offset;
    public BottomSheetModal.OnShowStartListener onAnimationStartListener;
    public BottomSheetModal.OnDismissListener onBeforeDismissListener;
    public BottomSheetModal.OnShowListener onBeforeShowListener;
    public BottomSheetModal.OnShowListener onShowListener;
    public final View rootView;
    public float scale;
    public final int screenHeight;
    public final int screenWidth;
    public boolean showing;

    public static WindowInsets $r8$lambda$0mv2XTIXt1D6Tia1TVWggPw0M1c(AutoBottomSheet autoBottomSheet, View view, WindowInsets windowInsets) {
        Insets insets = windowInsets.getInsets(WindowInsets.Type.ime());
        int i = autoBottomSheet.keyboardHeight;
        int i2 = insets.bottom;
        if (i != i2) {
            autoBottomSheet.keyboardHeight = i2;
            Log.i("AutoBottomSheet", "keyboardHeight=" + i2);
            autoBottomSheet.updateContainerForKeyboard();
        }
        return windowInsets;
    }

    public static void $r8$lambda$WGeEOdEdgJl4mnFtQ0cioEWu_s4(View view) {
    }

    public AutoBottomSheet(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.activity = activity;
        View decorView = activity.getWindow().getDecorView();
        Intrinsics.checkNotNull(decorView, "null cannot be cast to non-null type android.view.ViewGroup");
        this.decorView = (ViewGroup) decorView;
        this.scale = 1.0f;
        boolean z = false;
        View viewInflate = LayoutInflater.from(activity).inflate(R.layout.kg_auto_bottom_sheet, (ViewGroup) null, false);
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.view.AutoBottomSheet$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.dismiss();
            }
        });
        Intrinsics.checkNotNullExpressionValue(viewInflate, "apply(...)");
        this.rootView = viewInflate;
        EditorBottomSheetModal editorBottomSheetModal = new EditorBottomSheetModal(activity, false);
        editorBottomSheetModal.setAnimationInterruptible(true);
        BottomSheetBehavior behavior = editorBottomSheetModal.getBehavior();
        Intrinsics.checkNotNullExpressionValue(behavior, "getBehavior(...)");
        behavior.setState(3);
        behavior.setForceFullHeight(false);
        behavior.setFixedHeightRatioEnabled(false);
        editorBottomSheetModal.setOnDismissListener(new BottomSheetModal.OnDismissListener() { // from class: com.miui.keyguard.editor.view.AutoBottomSheet$$ExternalSyntheticLambda2
            @Override // miuix.bottomsheet.BottomSheetModal.OnDismissListener
            public final void onDismiss() {
                AutoBottomSheet.bottomSheetModal$lambda$7$lambda$2(this.f$0);
            }
        });
        editorBottomSheetModal.setBeforeDismissListener(new BottomSheetModal.OnDismissListener() { // from class: com.miui.keyguard.editor.view.AutoBottomSheet$$ExternalSyntheticLambda3
            @Override // miuix.bottomsheet.BottomSheetModal.OnDismissListener
            public final void onDismiss() {
                AutoBottomSheet.bottomSheetModal$lambda$7$lambda$3(this.f$0);
            }
        });
        editorBottomSheetModal.setBeforeShowListener(new BottomSheetModal.OnShowListener() { // from class: com.miui.keyguard.editor.view.AutoBottomSheet$$ExternalSyntheticLambda4
            @Override // miuix.bottomsheet.BottomSheetModal.OnShowListener
            public final void onShow() {
                AutoBottomSheet.bottomSheetModal$lambda$7$lambda$4(this.f$0);
            }
        });
        editorBottomSheetModal.setOnOnShowListener(new BottomSheetModal.OnShowListener() { // from class: com.miui.keyguard.editor.view.AutoBottomSheet$$ExternalSyntheticLambda5
            @Override // miuix.bottomsheet.BottomSheetModal.OnShowListener
            public final void onShow() {
                AutoBottomSheet.bottomSheetModal$lambda$7$lambda$5(this.f$0);
            }
        });
        editorBottomSheetModal.setOnShowStartListener(new BottomSheetModal.OnShowStartListener() { // from class: com.miui.keyguard.editor.view.AutoBottomSheet$$ExternalSyntheticLambda6
            @Override // miuix.bottomsheet.BottomSheetModal.OnShowStartListener
            public final void onShowStart() {
                AutoBottomSheet.bottomSheetModal$lambda$7$lambda$6(this.f$0);
            }
        });
        this.bottomSheetModal = editorBottomSheetModal;
        this.containerBackgroundPrimaryColor = -1;
        this.containerBackgroundSecondaryColor = -1;
        this.containerBackgroundLiteColor = -1;
        TypedArray typedArrayObtainStyledAttributes = activity.obtainStyledAttributes(null, R.styleable.AutoBottomSheet, R.attr.autoBottomSheetStyle, 0);
        this.containerBackgroundPrimaryColor = typedArrayObtainStyledAttributes.getColor(R.styleable.AutoBottomSheet_primaryColor, activity.getResources().getColor(R.color.kg_auto_bottom_sheet_blend_color1));
        this.containerBackgroundSecondaryColor = typedArrayObtainStyledAttributes.getColor(R.styleable.AutoBottomSheet_secondaryColor, activity.getResources().getColor(R.color.kg_miui_home_desktop_white_blend_color1));
        this.containerBackgroundLiteColor = typedArrayObtainStyledAttributes.getColor(R.styleable.AutoBottomSheet_liteColor, activity.getResources().getColor(R.color.kg_auto_bottom_sheet_lite_color_light));
        typedArrayObtainStyledAttributes.recycle();
        boolean zIsLightTheme = ActivityUIAdapter.Companion.isLightTheme(activity);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isQ18() && deviceUtil.isFoldLargeScreen(activity)) {
            z = true;
        }
        float dimensionPixelSize = activity.getResources().getDimensionPixelSize(R.dimen.kg_auto_bottom_sheet_radius);
        float dimensionPixelSize2 = activity.getResources().getDimensionPixelSize(R.dimen.kg_auto_bottom_sheet_blur_radius);
        BlendColorInfo[] blendColorInfoArr = {new BlendColorInfo(this.containerBackgroundPrimaryColor, zIsLightTheme ? BlendMode.COLOR_DODGE : BlendMode.COLOR_BURN), new BlendColorInfo(this.containerBackgroundSecondaryColor, BlendMode.SRC_OVER)};
        FrameLayout frameLayout = (FrameLayout) viewInflate.findViewById(R.id.container);
        frameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.view.AutoBottomSheet$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AutoBottomSheet.$r8$lambda$WGeEOdEdgJl4mnFtQ0cioEWu_s4(view);
            }
        });
        if (z) {
            ViewUtil viewUtil = ViewUtil.INSTANCE;
            Intrinsics.checkNotNull(frameLayout);
            ViewUtil.decorateAutoBottomPadTopRoundBackground$default(viewUtil, frameLayout, frameLayout, dimensionPixelSize, blendColorInfoArr, this.containerBackgroundLiteColor, zIsLightTheme, false, dimensionPixelSize2, 32, null);
        } else {
            ViewUtil viewUtil2 = ViewUtil.INSTANCE;
            Intrinsics.checkNotNull(frameLayout);
            ViewUtil.decorateAutoBottomPadBackground$default(viewUtil2, frameLayout, frameLayout, dimensionPixelSize, blendColorInfoArr, this.containerBackgroundLiteColor, zIsLightTheme, false, dimensionPixelSize2, 32, null);
        }
        frameLayout.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.miui.keyguard.editor.view.AutoBottomSheet$$ExternalSyntheticLambda8
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return AutoBottomSheet.$r8$lambda$0mv2XTIXt1D6Tia1TVWggPw0M1c(this.f$0, view, windowInsets);
            }
        });
        this.container = frameLayout;
        Rect rectCurrentDisplaySize = deviceUtil.currentDisplaySize(activity);
        this.screenWidth = rectCurrentDisplaySize.width();
        this.screenHeight = rectCurrentDisplaySize.height();
    }

    public static final void bottomSheetModal$lambda$7$lambda$2(AutoBottomSheet autoBottomSheet) {
        if (autoBottomSheet.needDelayRelease) {
            Log.d("AutoBottomSheet", "delay release bottom sheet modal");
            autoBottomSheet.releaseBottomSheetModal();
            autoBottomSheet.needDelayRelease = false;
        }
        BottomSheetModal.OnDismissListener onDismissListener = autoBottomSheet.onBeforeDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
        View view = autoBottomSheet.contentView;
        ViewParent parent = view != null ? view.getParent() : null;
        ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (viewGroup != null) {
            viewGroup.removeView(autoBottomSheet.contentView);
        }
    }

    public static final void bottomSheetModal$lambda$7$lambda$3(AutoBottomSheet autoBottomSheet) {
        BottomSheetModal.OnDismissListener onDismissListener = autoBottomSheet.onBeforeDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public static final void bottomSheetModal$lambda$7$lambda$4(AutoBottomSheet autoBottomSheet) {
        Log.d("AutoBottomSheet", "bottomSheetModal.beforeShow: onBeforeShowListener=" + (autoBottomSheet.onBeforeShowListener != null));
        BottomSheetModal.OnShowListener onShowListener = autoBottomSheet.onBeforeShowListener;
        if (onShowListener != null) {
            onShowListener.onShow();
        }
    }

    public static final void bottomSheetModal$lambda$7$lambda$5(AutoBottomSheet autoBottomSheet) {
        Log.d("AutoBottomSheet", "bottomSheetModal.onShow: onShowListener=" + (autoBottomSheet.onShowListener != null));
        BottomSheetModal.OnShowListener onShowListener = autoBottomSheet.onShowListener;
        if (onShowListener != null) {
            onShowListener.onShow();
        }
    }

    public static final void bottomSheetModal$lambda$7$lambda$6(AutoBottomSheet autoBottomSheet) {
        Log.d("AutoBottomSheet", "bottomSheetModal.onShowStart: onAnimationStartListener=" + (autoBottomSheet.onAnimationStartListener != null));
        BottomSheetModal.OnShowStartListener onShowStartListener = autoBottomSheet.onAnimationStartListener;
        if (onShowStartListener != null) {
            onShowStartListener.onShowStart();
        }
    }

    public static /* synthetic */ void reset$default(AutoBottomSheet autoBottomSheet, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        autoBottomSheet.reset(z);
    }

    public static final void show$lambda$19(final AutoBottomSheet autoBottomSheet, boolean z) {
        Log.d("AutoBottomSheet", "show PAD post: onBeforeShowListener=" + (autoBottomSheet.onBeforeShowListener != null));
        BottomSheetModal.OnShowListener onShowListener = autoBottomSheet.onBeforeShowListener;
        if (onShowListener != null) {
            onShowListener.onShow();
        }
        Log.d("AutoBottomSheet", "show PAD post: onAnimationStartListener=" + (autoBottomSheet.onAnimationStartListener != null));
        BottomSheetModal.OnShowStartListener onShowStartListener = autoBottomSheet.onAnimationStartListener;
        if (onShowStartListener != null) {
            onShowStartListener.onShowStart();
        }
        int dimensionPixelSize = z ? 0 : autoBottomSheet.activity.getResources().getDimensionPixelSize(R.dimen.kg_clock_bottom_sheet_margin_bottom_common);
        autoBottomSheet.bottomMargin = Integer.valueOf(dimensionPixelSize);
        AnimatorKt.showPopup(autoBottomSheet.container, dimensionPixelSize, new TransitionListener() { // from class: com.miui.keyguard.editor.view.AutoBottomSheet$show$4$1
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                Log.d("AutoBottomSheet", "show PAD showPopup onComplete: onShowListener=" + (this.this$0.onShowListener != null));
                BottomSheetModal.OnShowListener onShowListener2 = this.this$0.onShowListener;
                if (onShowListener2 != null) {
                    onShowListener2.onShow();
                }
            }
        });
    }

    public final void dismiss() {
        int dimensionPixelSize;
        this.keyboardHeight = 0;
        List<ClearableEditText> list = this.mEditList;
        if (list != null) {
            for (ClearableEditText clearableEditText : list) {
                if (clearableEditText != null) {
                    clearableEditText.clearFocus();
                }
            }
        }
        View view = this.contentView;
        if (Intrinsics.areEqual(view != null ? view.getParent() : null, this.container)) {
            BottomSheetModal.OnDismissListener onDismissListener = this.onBeforeDismissListener;
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
            FrameLayout frameLayout = this.container;
            Integer num = this.bottomMargin;
            if (num != null) {
                dimensionPixelSize = num.intValue();
            } else {
                DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
                dimensionPixelSize = (deviceUtil.isQ18() && deviceUtil.isFoldLargeScreen(this.activity)) ? 0 : this.activity.getResources().getDimensionPixelSize(R.dimen.kg_clock_bottom_sheet_margin_bottom_common);
            }
            AnimatorKt.dismissPopup(frameLayout, dimensionPixelSize, new TransitionListener() { // from class: com.miui.keyguard.editor.view.AutoBottomSheet.dismiss.2
                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    super.onCancel(obj);
                    AutoBottomSheet.this.largeScreenDismissFinish();
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    super.onComplete(obj);
                    AutoBottomSheet.this.largeScreenDismissFinish();
                }
            });
        } else {
            this.bottomSheetModal.dismiss();
        }
        this.showing = false;
    }

    public final int getAlignMode() {
        return this.alignMode;
    }

    public final View getAnchorView() {
        return this.anchorView;
    }

    public final FrameLayout getContainer() {
        return this.container;
    }

    public final boolean isShowing() {
        return this.showing;
    }

    public final void largeScreenDismissFinish() {
        View view = this.contentView;
        ViewParent parent = view != null ? view.getParent() : null;
        ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (viewGroup != null) {
            viewGroup.removeView(this.contentView);
        }
        ViewParent parent2 = this.rootView.getParent();
        ViewGroup viewGroup2 = parent2 instanceof ViewGroup ? (ViewGroup) parent2 : null;
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.rootView);
        }
    }

    public final void onImeKeyboardHeightChanged(int i) {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            return;
        }
        if (deviceUtil.isFold() && deviceUtil.isFoldLargeScreen(this.activity)) {
            return;
        }
        this.keyboardHeight = i;
        if (this.bottomSheetModal.isShowing()) {
            updateContentViewForKeyboard();
        }
    }

    public final void releaseBottomSheetModal() {
        this.bottomSheetModal.release();
        this.bottomSheetModal.setBeforeShowListener(null);
        this.bottomSheetModal.setBeforeDismissListener(null);
        this.bottomSheetModal.setOnDismissListener(null);
        this.bottomSheetModal.setOnOnShowListener(null);
        this.bottomSheetModal.setOnShowStartListener(null);
    }

    public final void reset(boolean z) {
        boolean zIsShowing = isShowing();
        this.needDelayRelease = zIsShowing;
        Log.d("AutoBottomSheet", "reset needDismiss=" + z + " needDelayRelease =" + zIsShowing);
        if (z) {
            dismiss();
        }
        if (!this.needDelayRelease) {
            releaseBottomSheetModal();
        }
        this.onBeforeShowListener = null;
        this.onBeforeDismissListener = null;
        this.onShowListener = null;
        this.onAnimationStartListener = null;
        this.contentView = null;
        this.anchorView = null;
    }

    public final AutoBottomSheet setAnchorView(View view, int i) {
        if (view == null) {
            Log.d("AutoBottomSheet", "anchor view is null");
            return this;
        }
        this.anchorView = view;
        this.alignMode = i;
        Log.d("AutoBottomSheet", "alignMode=" + i);
        updateContainer();
        return this;
    }

    public final AutoBottomSheet setContentView(View view) {
        this.contentView = view;
        return this;
    }

    public final void setEdits(List editList) {
        Intrinsics.checkNotNullParameter(editList, "editList");
        this.mEditList = editList;
    }

    public final AutoBottomSheet setMargin(int i, int i2, int i3, int i4) {
        this.marginStart = i;
        this.marginTop = i2;
        this.marginEnd = i3;
        this.marginBottom = i4;
        updateContainer();
        return this;
    }

    public final AutoBottomSheet setOffsetParams(BaseTemplateView baseTemplateView) {
        int dimensionPixelSize = this.activity.getResources().getDimensionPixelSize(R.dimen.kg_template_dialog_top_margin);
        Rect rect = new Rect();
        if (baseTemplateView != null) {
            ViewUtil.INSTANCE.getShowRect(baseTemplateView, rect);
        }
        int i = rect.top;
        this.baseTemplateViewTopMargin = i;
        this.offset = dimensionPixelSize - i;
        return this;
    }

    public final AutoBottomSheet setOnAnimationStartListener(BottomSheetModal.OnShowStartListener onShowStartListener) {
        this.onAnimationStartListener = onShowStartListener;
        return this;
    }

    public final AutoBottomSheet setOnBeforeShowListener(BottomSheetModal.OnShowListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Log.d("AutoBottomSheet", "setOnBeforeShowListener: called, showing=" + this.showing);
        this.onBeforeShowListener = listener;
        return this;
    }

    public final AutoBottomSheet setOnDismissListener(BottomSheetModal.OnDismissListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onBeforeDismissListener = listener;
        return this;
    }

    public final AutoBottomSheet setOnShowListener(BottomSheetModal.OnShowListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Log.d("AutoBottomSheet", "setOnShowListener: called, showing=" + this.showing);
        this.onShowListener = listener;
        return this;
    }

    public final AutoBottomSheet setScaleParam() {
        this.scale = ResourcesProvider.INSTANCE.provideTemplateEditScale(this.activity);
        return this;
    }

    public final void setShowing(boolean z) {
        this.showing = z;
    }

    public final void setSkipCollapsed(boolean z) {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            return;
        }
        if (deviceUtil.isFold() && deviceUtil.isFoldLargeScreen(this.activity)) {
            return;
        }
        this.bottomSheetModal.getBehavior().setSkipCollapsed(z);
    }

    public final void setSkipHalfExpanded(boolean z) {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            return;
        }
        if (deviceUtil.isFold() && deviceUtil.isFoldLargeScreen(this.activity)) {
            return;
        }
        this.bottomSheetModal.getBehavior().setSkipHalfExpanded(z);
    }

    public final AutoBottomSheet show() {
        if (this.contentView == null) {
            Log.d("AutoBottomSheet", "show: content view is null ");
            return this;
        }
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        View view = null;
        if (deviceUtil.isPad() || (deviceUtil.isFold() && deviceUtil.isFoldLargeScreen(this.activity))) {
            final boolean z = deviceUtil.isQ18() && deviceUtil.isFoldLargeScreen(this.activity);
            Folme.clean(this.container);
            this.container.removeAllViews();
            View view2 = this.contentView;
            ViewParent parent = view2 != null ? view2.getParent() : null;
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup != null) {
                viewGroup.removeView(this.contentView);
            }
            View view3 = this.rootView;
            ViewParent parent2 = view3 != null ? view3.getParent() : null;
            ViewGroup viewGroup2 = parent2 instanceof ViewGroup ? (ViewGroup) parent2 : null;
            if (viewGroup2 != null) {
                viewGroup2.removeView(this.rootView);
            }
            if (z) {
                try {
                    int dimensionPixelSize = this.activity.getResources().getDimensionPixelSize(R.dimen.kg_editor_dialog_content_height_q18);
                    View view4 = this.contentView;
                    if (view4 != null) {
                        view4.setLayoutParams(new FrameLayout.LayoutParams(-1, dimensionPixelSize));
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
            FrameLayout frameLayout = this.container;
            View view5 = this.contentView;
            if (view5 != null) {
                if (!DeviceConfig.supportBackgroundBlur(view5.getContext())) {
                    ActivityUIAdapter.Companion companion = ActivityUIAdapter.Companion;
                    Context context = view5.getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                    if (companion.isLightTheme(context)) {
                        view5.setBackgroundResource(R.drawable.kg_auto_bottom_sheet_bg);
                    }
                }
                view = view5;
            }
            frameLayout.addView(view);
            if (z) {
                this.alignMode = 1280;
                this.marginStart = 0;
                this.marginEnd = 0;
                this.marginTop = 0;
                this.marginBottom = 0;
                updateContainer();
            }
            this.container.setAlpha(0.0f);
            this.decorView.addView(this.rootView, -1, -1);
            this.container.post(new Runnable() { // from class: com.miui.keyguard.editor.view.AutoBottomSheet$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AutoBottomSheet.show$lambda$19(this.f$0, z);
                }
            });
        } else {
            View view6 = this.contentView;
            ViewParent parent3 = view6 != null ? view6.getParent() : null;
            ViewGroup viewGroup3 = parent3 instanceof ViewGroup ? (ViewGroup) parent3 : null;
            if (viewGroup3 != null) {
                viewGroup3.removeView(this.contentView);
            }
            BottomSheetBehavior behavior = this.bottomSheetModal.getBehavior();
            Intrinsics.checkNotNullExpressionValue(behavior, "getBehavior(...)");
            if (deviceUtil.isQ18()) {
                int dimensionPixelSize2 = this.activity.getResources().getDimensionPixelSize(R.dimen.kg_editor_dialog_content_height_q18);
                View view7 = this.contentView;
                if (view7 != null) {
                    view7.setLayoutParams(new FrameLayout.LayoutParams(-1, dimensionPixelSize2));
                }
            }
            behavior.setState(3);
            this.bottomSheetModal.setContentView(this.contentView);
            Log.d("AutoBottomSheet", "show small");
            this.bottomSheetModal.show();
        }
        this.showing = true;
        return this;
    }

    public final void updateContainer() {
        int i;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this.activity.getResources().getDimensionPixelSize(!(deviceUtil.isQ18() && deviceUtil.isFoldLargeScreen(this.activity)) ? R.dimen.kg_bottom_sheet_width : R.dimen.kg_bottom_sheet_width_q18), -2);
        int[] iArr = new int[2];
        View view = this.anchorView;
        if (view != null) {
            view.getLocationOnScreen(iArr);
        }
        int i2 = this.marginStart;
        int i3 = this.marginTop;
        int i4 = this.marginEnd;
        int i5 = this.marginBottom + this.keyboardHeight;
        Rect rect = new Rect();
        View view2 = this.anchorView;
        if (view2 != null) {
            ViewUtil.INSTANCE.getShowRect(view2, rect);
        }
        int i6 = rect.left;
        int i7 = rect.top;
        int i8 = this.offset;
        int i9 = i7 + i8;
        int i10 = rect.right;
        int i11 = (int) (i9 + (this.scale * (rect.bottom - i7)));
        Log.d("AutoBottomSheet", "anchorLeft=" + i6 + " anchorTop=" + i9 + " anchorRight=" + i10 + " anchorBottom=" + i11 + ", offset=" + i8);
        int i12 = this.alignMode;
        if ((i12 & 1) != 0) {
            i3 += i11;
        }
        if ((i12 & 2) != 0) {
            i3 += i9;
        }
        int i13 = 80;
        if ((i12 & 4) != 0) {
            i5 += this.screenHeight - i11;
            i = 80;
        } else {
            i = 0;
        }
        if ((i12 & 8) != 0) {
            i5 += this.screenHeight - i9;
        } else {
            i13 = i;
        }
        if ((i12 & 16) != 0) {
            i13 |= 8388611;
            i2 += deviceUtil.isRtl(this.activity) ? this.screenWidth - i6 : i10;
        }
        if ((this.alignMode & 32) != 0) {
            i13 |= 8388611;
            i2 += deviceUtil.isRtl(this.activity) ? this.screenWidth - i10 : i6;
        }
        if ((this.alignMode & 64) != 0) {
            i4 += deviceUtil.isRtl(this.activity) ? i10 : this.screenWidth - i6;
            i13 |= 8388613;
        }
        if ((this.alignMode & 128) != 0) {
            if (!deviceUtil.isRtl(this.activity)) {
                i6 = this.screenWidth - i10;
            }
            i4 += i6;
            i13 |= 8388613;
        }
        int i14 = this.alignMode;
        if ((i14 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0) {
            i13 |= 1;
        }
        if ((i14 & 512) != 0) {
            i13 |= 16;
        }
        if ((i14 & 1024) != 0) {
            i13 |= 80;
        }
        if ((i14 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0) {
            i13 |= 8388613;
        }
        if ((i14 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0) {
            i13 |= 48;
        }
        if ((i14 & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0) {
            i13 |= 8388611;
        }
        Log.d("AutoBottomSheet", "sw=" + this.screenWidth + " sh=" + this.screenHeight + " layoutMarginStart=" + i2 + " layoutMarginTop=" + i3 + " layoutMarginEnd=" + i4 + " layoutMarginBottom=" + i5);
        layoutParams.setMargins(0, i3, 0, i5);
        layoutParams.setMarginStart(i2);
        layoutParams.setMarginEnd(i4);
        layoutParams.gravity = i13;
        this.container.setLayoutParams(layoutParams);
    }

    public final void updateContainerForKeyboard() {
        int dimensionPixelOffset;
        float bottom = (this.keyboardHeight <= 0 || (dimensionPixelOffset = (this.screenHeight - this.keyboardHeight) - this.activity.getResources().getDimensionPixelOffset(R.dimen.kg_signature_editor_margin_bottom_soft_keyboard)) >= this.container.getBottom()) ? 0 : dimensionPixelOffset - this.container.getBottom();
        if (this.container.getTranslationY() == bottom) {
            return;
        }
        FrameLayout frameLayout = this.container;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(frameLayout, (Property<FrameLayout, Float>) View.TRANSLATION_Y, frameLayout.getTranslationY(), bottom);
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator(1.5f));
        objectAnimatorOfFloat.setDuration(300L);
        objectAnimatorOfFloat.start();
    }

    /* JADX WARN: Code duplicated, block: B:46:0x009f  */
    public final void updateContentViewForKeyboard() {
        Object obj;
        View view;
        float f;
        if (!this.bottomSheetModal.isShowing()) {
            Log.d("AutoBottomSheet", "skip updateContentViewForKeyboard: showing=" + this.bottomSheetModal.isShowing());
            return;
        }
        View view2 = this.contentView;
        if (view2 == null) {
            return;
        }
        View view3 = view2;
        while (true) {
            obj = null;
            if (view3 == null) {
                view = view2;
                break;
            }
            Object parent = view3.getParent();
            if (parent instanceof CoordinatorLayout) {
                view = view3;
                break;
            }
            view3 = parent instanceof View ? (View) parent : null;
        }
        if (view3 == null) {
            Object parent2 = view2.getParent();
            View view4 = parent2 instanceof View ? (View) parent2 : null;
            if (view4 != null) {
                view2 = view4;
            }
            view = view2;
        }
        if (this.keyboardHeight <= 0) {
            f = 0.0f;
        } else {
            List list = this.mEditList;
            if (list == null) {
                return;
            }
            for (Object obj2 : list) {
                if (((ClearableEditText) obj2) != null) {
                    obj = obj2;
                    break;
                }
            }
            ClearableEditText clearableEditText = (ClearableEditText) obj;
            if (clearableEditText == null) {
                return;
            }
            int dimensionPixelOffset = this.activity.getResources().getDimensionPixelOffset(R.dimen.kg_signature_editor_margin_bottom_soft_keyboard);
            int i = this.screenHeight - this.keyboardHeight;
            int[] iArr = new int[2];
            clearableEditText.getLocationOnScreen(iArr);
            int height = iArr[1] + clearableEditText.getHeight();
            int i2 = i - dimensionPixelOffset;
            if (height > i2) {
                f = i2 - height;
            } else {
                f = 0.0f;
            }
        }
        if (view.getTranslationY() == f) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, view.getTranslationY(), f);
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator(1.5f));
        objectAnimatorOfFloat.setDuration(300L);
        objectAnimatorOfFloat.start();
    }
}
