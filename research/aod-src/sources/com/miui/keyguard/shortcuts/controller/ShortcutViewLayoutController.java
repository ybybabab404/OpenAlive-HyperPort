package com.miui.keyguard.shortcuts.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.keyguard.shortcuts.R;
import com.miui.keyguard.shortcuts.analy.ShortcutAnalyticManager;
import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import com.miui.keyguard.shortcuts.manager.MiuiShortcutManager;
import com.miui.keyguard.shortcuts.utils.DeviceHelper;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.keyguard.shortcuts.utils.ViewUtils;
import com.miui.keyguard.shortcuts.view.SineEaseInOutInterpolator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
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
/* JADX INFO: compiled from: ShortcutViewLayoutController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ShortcutViewLayoutController implements MiuiShortcutManager.ShortcutDataCallback {
    public static final Companion Companion = new Companion(null);
    public ShortcutTipsAnimCallback animCallback;
    public final Context context;
    public boolean isBottomIconRectIsDeep;
    public boolean isGxzwLowPosAndFingerApplyForKeyguard;
    public final boolean isGxzwLowPosition;
    public AnimatorSet shortViewLayoutAnimatorSet;
    public int shortcutFixedHeight;
    public final ViewGroup shortcutViewLayout;
    public final ImageView shortcutViewLeft;
    public final FrameLayout shortcutViewLeftLayout;
    public final ImageView shortcutViewRight;
    public final FrameLayout shortcutViewRightLayout;
    public final Context sysuiContext;

    /* JADX INFO: compiled from: ShortcutViewLayoutController.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: ShortcutViewLayoutController.kt */
    @Metadata
    public interface ShortcutTipsAnimCallback {
        void onAnimationEnd();

        void onAnimationStart();
    }

    public ShortcutViewLayoutController(Context context, Context sysuiContext, ViewGroup shortcutViewLayout, boolean z, boolean z2, ShortcutTipsAnimCallback shortcutTipsAnimCallback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(sysuiContext, "sysuiContext");
        Intrinsics.checkNotNullParameter(shortcutViewLayout, "shortcutViewLayout");
        this.context = context;
        this.sysuiContext = sysuiContext;
        this.shortcutViewLayout = shortcutViewLayout;
        this.isBottomIconRectIsDeep = z;
        this.isGxzwLowPosition = z2;
        this.animCallback = shortcutTipsAnimCallback;
        this.shortcutViewLeftLayout = (FrameLayout) shortcutViewLayout.findViewById(R.id.shortcut_view_left_layout);
        View viewFindViewById = shortcutViewLayout.findViewById(R.id.shortcut_view_left);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        ImageView imageView = (ImageView) viewFindViewById;
        this.shortcutViewLeft = imageView;
        this.shortcutViewRightLayout = (FrameLayout) shortcutViewLayout.findViewById(R.id.shortcut_view_right_layout);
        View viewFindViewById2 = shortcutViewLayout.findViewById(R.id.shortcut_view_right);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        ImageView imageView2 = (ImageView) viewFindViewById2;
        this.shortcutViewRight = imageView2;
        MiuiShortcutManager.INSTANCE.addShortDataCallback(this);
        ITouchStyle iTouchStyle = Folme.useAt(imageView).touch();
        iTouchStyle.clean();
        iTouchStyle.setScale(1.0f, new ITouchStyle.TouchType[0]);
        iTouchStyle.handleTouchOf(imageView, new AnimConfig[0]);
        ITouchStyle iTouchStyle2 = Folme.useAt(imageView2).touch();
        iTouchStyle2.clean();
        iTouchStyle2.setScale(1.0f, new ITouchStyle.TouchType[0]);
        iTouchStyle2.handleTouchOf(imageView2, new AnimConfig[0]);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutViewLayoutController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleOnIconClick(true);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutViewLayoutController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleOnIconClick(false);
            }
        });
        updateShortcutViewImageTintList();
        updateShortcutFixedSize();
    }

    public final void cancelTipsAnimation() {
        AnimatorSet animatorSet = this.shortViewLayoutAnimatorSet;
        if (animatorSet != null) {
            if (animatorSet.isRunning()) {
                animatorSet.cancel();
            }
            this.shortViewLayoutAnimatorSet = null;
        }
    }

    public final ShortcutTipsAnimCallback getAnimCallback() {
        return this.animCallback;
    }

    public final AnimatorSet getButtonLayoutAnimate(final View view, boolean z) {
        AnimatorSet animatorSet = new AnimatorSet();
        float f = z ? -1.0f : 1.0f;
        Property property = View.TRANSLATION_X;
        float f2 = (-50) * f;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, 0.0f, f2);
        objectAnimatorOfFloat.setDuration(150L);
        float f3 = 10 * f;
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, f2, f3);
        objectAnimatorOfFloat2.setDuration(150L);
        float f4 = (-8) * f;
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, f3, f4);
        objectAnimatorOfFloat3.setDuration(100L);
        float f5 = 5 * f;
        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, f4, f5);
        objectAnimatorOfFloat4.setDuration(100L);
        ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, f5, 0.0f);
        objectAnimatorOfFloat5.setDuration(100L);
        animatorSet.play(objectAnimatorOfFloat).before(objectAnimatorOfFloat2);
        animatorSet.play(objectAnimatorOfFloat2).before(objectAnimatorOfFloat3);
        animatorSet.play(objectAnimatorOfFloat3).before(objectAnimatorOfFloat4);
        animatorSet.play(objectAnimatorOfFloat4).before(objectAnimatorOfFloat5);
        animatorSet.setInterpolator(new SineEaseInOutInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutViewLayoutController$getButtonLayoutAnimate$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                this.this$0.onTipsAnimationEnd(view);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                this.this$0.onTipsAnimationEnd(view);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                ShortcutViewLayoutController.ShortcutTipsAnimCallback animCallback = this.this$0.getAnimCallback();
                if (animCallback != null) {
                    animCallback.onAnimationStart();
                }
            }
        });
        return animatorSet;
    }

    public final ViewGroup getShortcutViewLayout() {
        return this.shortcutViewLayout;
    }

    public final ImageView getShortcutViewLeft() {
        return this.shortcutViewLeft;
    }

    public final ImageView getShortcutViewRight() {
        return this.shortcutViewRight;
    }

    public final void handleOnIconClick(boolean z) {
        MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
        if (!miuiShortcutManager.getTalkbackEnabled()) {
            showShortcutTipsAnim(z);
            return;
        }
        miuiShortcutManager.setOccludedMovedShortcut(z);
        miuiShortcutManager.startShortcutActivity();
        miuiShortcutManager.resetOccludedMovedShortcut();
    }

    public final void onBottomIconDarkModeChanged(boolean z) {
        this.isBottomIconRectIsDeep = z;
        updateShortcutViewImageTintList();
    }

    @Override // com.miui.keyguard.shortcuts.manager.MiuiShortcutManager.ShortcutDataCallback
    public void onShortcutDataChanged(ShortcutEntity shortcutData, boolean z) {
        Intrinsics.checkNotNullParameter(shortcutData, "shortcutData");
        ImageView imageView = z ? this.shortcutViewLeft : this.shortcutViewRight;
        FrameLayout frameLayout = z ? this.shortcutViewLeftLayout : this.shortcutViewRightLayout;
        Intrinsics.checkNotNull(frameLayout);
        updateShortcutView(shortcutData, imageView, frameLayout);
    }

    public final void onTipsAnimationEnd(View view) {
        view.setTranslationX(0.0f);
        this.shortViewLayoutAnimatorSet = null;
        ShortcutTipsAnimCallback shortcutTipsAnimCallback = this.animCallback;
        if (shortcutTipsAnimCallback != null) {
            shortcutTipsAnimCallback.onAnimationEnd();
        }
    }

    public final void release() {
        MiuiShortcutManager.INSTANCE.removeShortDataCallback(this);
        this.animCallback = null;
    }

    public final void showShortcutTipsAnim(boolean z) {
        AnimatorSet animatorSet = this.shortViewLayoutAnimatorSet;
        if (animatorSet == null || !animatorSet.isRunning()) {
            if (this.shortViewLayoutAnimatorSet == null) {
                FrameLayout frameLayout = z ? this.shortcutViewLeftLayout : this.shortcutViewRightLayout;
                Intrinsics.checkNotNull(frameLayout);
                this.shortViewLayoutAnimatorSet = getButtonLayoutAnimate(frameLayout, z);
            }
            AnimatorSet animatorSet2 = this.shortViewLayoutAnimatorSet;
            if (animatorSet2 != null) {
                if (z) {
                    if (this.shortcutViewLeft.isShown()) {
                        animatorSet2.start();
                        ShortcutAnalyticManager shortcutAnalyticManager = ShortcutAnalyticManager.INSTANCE;
                        MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
                        shortcutAnalyticManager.handleKeyguardShortcutExposeEvent(miuiShortcutManager.getSysuiContext(), "点击icon", miuiShortcutManager.getShortcutDataLeft().getShortcutTrackName());
                        return;
                    }
                    return;
                }
                if (this.shortcutViewRight.isShown()) {
                    animatorSet2.start();
                    ShortcutAnalyticManager shortcutAnalyticManager2 = ShortcutAnalyticManager.INSTANCE;
                    MiuiShortcutManager miuiShortcutManager2 = MiuiShortcutManager.INSTANCE;
                    shortcutAnalyticManager2.handleKeyguardShortcutExposeEvent(miuiShortcutManager2.getSysuiContext(), "点击icon", miuiShortcutManager2.getShortcutDataRight().getShortcutTrackName());
                }
            }
        }
    }

    public final void updateHorizontalMargin() {
        int shortcutHorizontalMargin = DeviceHelper.INSTANCE.getShortcutHorizontalMargin(this.context);
        ViewGroup.LayoutParams layoutParams = this.shortcutViewLeftLayout.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
        if (layoutParams2 != null) {
            layoutParams2.setMarginStart(shortcutHorizontalMargin);
            this.shortcutViewLeftLayout.setLayoutParams(layoutParams2);
        }
        ViewGroup.LayoutParams layoutParams3 = this.shortcutViewRightLayout.getLayoutParams();
        FrameLayout.LayoutParams layoutParams4 = layoutParams3 instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams3 : null;
        if (layoutParams4 != null) {
            layoutParams4.setMarginEnd(shortcutHorizontalMargin);
            this.shortcutViewRightLayout.setLayoutParams(layoutParams4);
        }
    }

    public final void updateMiuiShortcutTipsForGxzwLowPosition(boolean z) {
        this.isGxzwLowPosAndFingerApplyForKeyguard = z;
    }

    public final void updateShortcutFixedSize() {
        int dimension;
        int dimensionPixelSize;
        try {
            int identifier = this.sysuiContext.getResources().getIdentifier("keyguard_affordance_fixed_height", "dimen", "com.android.systemui");
            int identifier2 = this.sysuiContext.getResources().getIdentifier("keyguard_affordance_fixed_width", "dimen", "com.android.systemui");
            if (identifier == 0 || identifier2 == 0) {
                dimension = (int) this.context.getResources().getDimension(R.dimen.keyguard_shortcut_fixed_size);
                LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutViewLayoutController", "updateShortcutFixedSize: fallback size=" + dimension, null, 4, null);
                dimensionPixelSize = dimension;
            } else {
                dimension = this.sysuiContext.getResources().getDimensionPixelSize(identifier);
                dimensionPixelSize = this.sysuiContext.getResources().getDimensionPixelSize(identifier2);
                LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutViewLayoutController", "updateShortcutFixedSize: fixedWidth=" + dimensionPixelSize + ", fixedHeight=" + dimension, null, 4, null);
            }
            this.shortcutFixedHeight = dimension;
            ImageView imageView = this.shortcutViewLeft;
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = dimensionPixelSize;
            layoutParams.height = dimension;
            imageView.setLayoutParams(layoutParams);
            ImageView imageView2 = this.shortcutViewRight;
            ViewGroup.LayoutParams layoutParams2 = imageView2.getLayoutParams();
            layoutParams2.width = dimensionPixelSize;
            layoutParams2.height = dimension;
            imageView2.setLayoutParams(layoutParams2);
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ShortcutViewLayoutController", "updateShortcutFixedSize failed: " + e, null, 4, null);
        }
    }

    public final void updateShortcutView(ShortcutEntity shortcutEntity, ImageView imageView, View view) {
        imageView.setImageDrawable(shortcutEntity.getDrawableWithAvailable());
        imageView.setVisibility(shortcutEntity.getDrawableWithAvailable() != null ? 0 : 8);
        view.setContentDescription(shortcutEntity.getShortcutName());
        view.setVisibility(shortcutEntity.getDrawableWithAvailable() != null ? 0 : 8);
    }

    public final void updateShortcutViewImageTintList() {
        ColorStateList iconTintList = ViewUtils.INSTANCE.getIconTintList(this.context, this.isBottomIconRectIsDeep);
        if (iconTintList != null) {
            this.shortcutViewLeft.setImageTintList(iconTintList);
            this.shortcutViewRight.setImageTintList(iconTintList);
        }
        this.shortcutViewLeft.postInvalidate();
        this.shortcutViewRight.postInvalidate();
        this.shortcutViewLayout.postInvalidate();
    }
}
