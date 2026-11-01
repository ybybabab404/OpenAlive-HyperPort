package com.miui.keyguard.shortcuts.controller;

import android.content.Context;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import android.view.ViewGroup;
import com.miui.keyguard.shortcuts.manager.MiuiShortcutManager;
import com.miui.keyguard.shortcuts.manager.UnoccludedAnimTrace;
import com.miui.keyguard.shortcuts.utils.DeviceHelper;
import com.miui.keyguard.shortcuts.utils.UnoccludedAnimationUtils;
import com.miui.keyguard.shortcuts.view.ShortcutUnoccludedAnimView;
import java.io.IOException;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;

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
/* JADX INFO: compiled from: ShortcutUnoccludedAnimController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ShortcutUnoccludedAnimController extends BaseShortcutAnimController<ShortcutUnoccludedAnimView> {
    public static final Companion Companion = new Companion(null);
    public final Callback callback;
    public boolean isLeashAnimPlaying;
    public boolean isUnoccludedAnimStarted;
    public boolean isUnoccludedAnimStarting;
    public ShortcutUnoccludedAnimView shortcutAnimView;
    public final IStateStyle unoccludedAlphaHideFolme;
    public final IStateStyle unoccludedAnimForDefaultFolme;
    public final IStateStyle unoccludedAnimForShortcutFolme;
    public final IStateStyle unoccludedPanelBlurFolme;

    /* JADX INFO: compiled from: ShortcutUnoccludedAnimController.kt */
    @Metadata
    public interface Callback {

        /* JADX INFO: compiled from: ShortcutUnoccludedAnimController.kt */
        @Metadata
        public static final class DefaultImpls {
        }

        float getBouncerWallpaperPreviewAlpha();

        void onUnoccludedAnimationEnd();

        void onUnoccludedAnimationStart();
    }

    /* JADX INFO: compiled from: ShortcutUnoccludedAnimController.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutUnoccludedAnimController(Context context, ViewGroup viewGroup, Callback callback) {
        super(context, viewGroup);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(viewGroup, "viewGroup");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
        this.shortcutAnimView = new ShortcutUnoccludedAnimView(context);
        this.unoccludedAlphaHideFolme = Folme.useValue("TAG_UNOCCLUDED_ALPHA_HIDE");
        this.unoccludedAnimForShortcutFolme = Folme.useValue("TAG_UNOCCLUDED_ANIM_SHORTCUT");
        this.unoccludedAnimForDefaultFolme = Folme.useValue("TAG_UNOCCLUDED_ANIM_DEFAULT");
        this.unoccludedPanelBlurFolme = Folme.useValue("TAG_UNOCCLUDED_PANEL_BLUR_ANIM");
    }

    public static final void _set_isUnoccludedAnimStarted_$lambda$0(ShortcutUnoccludedAnimController shortcutUnoccludedAnimController) {
        shortcutUnoccludedAnimController.callback.onUnoccludedAnimationEnd();
    }

    public static final void checkUnoccludedRemoteAnimation$lambda$2(final ShortcutUnoccludedAnimController shortcutUnoccludedAnimController) {
        if (!shortcutUnoccludedAnimController.isUnoccludedAnimStarting || shortcutUnoccludedAnimController.isUnoccludedAnimStarted) {
            return;
        }
        shortcutUnoccludedAnimController.setUnoccludedAnimStarted(true);
        shortcutUnoccludedAnimController.addAnimShortcutView(new Function0() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutUnoccludedAnimController$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ShortcutUnoccludedAnimController.checkUnoccludedRemoteAnimation$lambda$2$lambda$1(this.f$0);
            }
        });
    }

    public static final Unit checkUnoccludedRemoteAnimation$lambda$2$lambda$1(ShortcutUnoccludedAnimController shortcutUnoccludedAnimController) throws IOException {
        shortcutUnoccludedAnimController.doUnoccludedAnimationAsync();
        return Unit.INSTANCE;
    }

    public final void cancelAnimations() {
        this.unoccludedAlphaHideFolme.cancel();
        this.unoccludedAnimForShortcutFolme.cancel();
        this.unoccludedAnimForDefaultFolme.cancel();
    }

    public final boolean checkUnoccludedRemoteAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        boolean zCheckUnoccludedRemoteAnimation = getShortcutAnimView().checkUnoccludedRemoteAnimation(remoteAnimationTargetArr, iRemoteAnimationFinishedCallback);
        this.isUnoccludedAnimStarting = zCheckUnoccludedRemoteAnimation;
        MiuiShortcutManager.INSTANCE.setInAppExiting(zCheckUnoccludedRemoteAnimation);
        getMainHandler().post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutUnoccludedAnimController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ShortcutUnoccludedAnimController.checkUnoccludedRemoteAnimation$lambda$2(this.f$0);
            }
        });
        return this.isUnoccludedAnimStarting;
    }

    @Override // com.miui.keyguard.shortcuts.controller.BaseShortcutAnimController
    public void destroy() {
        super.destroy();
        cancelAnimations();
    }

    public final void doPanelViewBlurAnimation() {
        this.unoccludedPanelBlurFolme.setTo("ANIM_STEP_VALUE", Float.valueOf(1.0f)).to("ANIM_STEP_VALUE", Float.valueOf(0.0f), new AnimConfig().addListeners(new TransitionListener() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutUnoccludedAnimController.doPanelViewBlurAnimation.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj, Collection collection) throws IOException {
                ShortcutUnoccludedAnimController.this.doPanelViewBlurAnimationInner(1.0f);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) throws IOException {
                UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "ANIM_STEP_VALUE");
                if (updateInfoFindByName == null) {
                    return;
                }
                ShortcutUnoccludedAnimController.this.doPanelViewBlurAnimationInner(updateInfoFindByName.getFloatValue());
            }
        }));
    }

    public final void doPanelViewBlurAnimationInner(float f) throws IOException {
        updateBlurRatio(f);
    }

    public final void doUnoccludeAnimationForBouncerShowing(final boolean z) throws IOException {
        this.isLeashAnimPlaying = true;
        float bouncerWallpaperPreviewAlpha = this.callback.getBouncerWallpaperPreviewAlpha();
        if (bouncerWallpaperPreviewAlpha == 1.0f) {
            handleAnimEnd("doUnoccludeAnimationForBouncerShowing wallpaper preview occluded");
            return;
        }
        float fCoerceAtLeast = RangesKt.coerceAtLeast(1 - bouncerWallpaperPreviewAlpha, 0.0f);
        IStateStyle iStateStyle = this.unoccludedAlphaHideFolme;
        UnoccludedAnimationUtils unoccludedAnimationUtils = UnoccludedAnimationUtils.INSTANCE;
        iStateStyle.setTo(unoccludedAnimationUtils.getTAG_LEASH_ALPHA(), Float.valueOf(fCoerceAtLeast)).to(unoccludedAnimationUtils.getTAG_LEASH_ALPHA(), Float.valueOf(0.0f), new AnimConfig().setEase(unoccludedAnimationUtils.getLeashAlphaHideEase()).addListeners(new TransitionListener() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutUnoccludedAnimController.doUnoccludeAnimationForBouncerShowing.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                if (z) {
                    return;
                }
                this.getShortcutAnimView().initSynSurfaceTransactionApplier();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) throws IOException {
                this.handleAnimEnd("doUnoccludeAnimationForBouncerShowing#onCancel");
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) throws IOException {
                this.handleAnimEnd("doUnoccludeAnimationForBouncerShowing#onComplete");
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, UnoccludedAnimationUtils.INSTANCE.getTAG_LEASH_ALPHA());
                float floatValue = updateInfoFindBy != null ? updateInfoFindBy.getFloatValue() : 1.0f;
                if (z) {
                    this.getShortcutAnimView().doUnoccludedLeashAnimationForBouncerShowingAsync(floatValue);
                } else {
                    this.getShortcutAnimView().doUnoccludedLeashAnimationForBouncerShowing(floatValue);
                }
            }
        }));
    }

    public final void doUnoccludedAnimationAsync() throws IOException {
        if (MiuiShortcutManager.INSTANCE.isBouncerShowing()) {
            doUnoccludeAnimationForBouncerShowing(true);
            return;
        }
        doUnoccludedAnimationForDefaultAsync();
        if (needBgBlurHideAnim()) {
            doPanelViewBlurAnimation();
        }
    }

    public final void doUnoccludedAnimationForDefaultAsync() {
        this.isLeashAnimPlaying = true;
        this.unoccludedAnimForDefaultFolme.setTo(getShortcutAnimView().getUnoccludedAnimDefaultStartStateAsync()).to(getShortcutAnimView().getUnoccludedAnimDefaultEndStateAsync(), new AnimConfig().setEase(UnoccludedAnimationUtils.INSTANCE.getLeashAlphaAsyncEase()).addListeners(new TransitionListener() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutUnoccludedAnimController.doUnoccludedAnimationForDefaultAsync.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                UnoccludedAnimTrace.INSTANCE.startForLeashHide();
                ShortcutUnoccludedAnimController.this.getShortcutAnimView().reparentUnoccludedSurfaceControl();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) throws IOException {
                ShortcutUnoccludedAnimController.this.handleAnimEnd("doUnoccludedAnimationForDefault#onCancel");
                UnoccludedAnimTrace.INSTANCE.endForLeashHide();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) throws IOException {
                ShortcutUnoccludedAnimController.this.handleAnimEnd("doUnoccludedAnimationForDefault#onComplete");
                UnoccludedAnimTrace.INSTANCE.endForLeashHide();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, UnoccludedAnimationUtils.INSTANCE.getTAG_DEFAULT_LEASH_ALPHA());
                ShortcutUnoccludedAnimController.this.getShortcutAnimView().doUnoccludedAnimationUpdateForDefaultAsync(updateInfoFindBy != null ? updateInfoFindBy.getFloatValue() : 1.0f);
            }
        }));
    }

    @Override // com.miui.keyguard.shortcuts.controller.BaseShortcutAnimController
    public ShortcutUnoccludedAnimView getShortcutAnimView() {
        return this.shortcutAnimView;
    }

    public final void handleAnimEnd(String str) throws IOException {
        this.isLeashAnimPlaying = false;
        getShortcutAnimView().doUnoccludedAnimationEnd();
        reset(str);
        removeAnimShortcutView();
    }

    public final boolean isUnoccludedAnimStartOrPlaying() {
        return this.isUnoccludedAnimStarting;
    }

    public final boolean needBgBlurHideAnim() {
        if (DeviceHelper.INSTANCE.isCpuAndGpuHighLevel()) {
            return getShortcutAnimView().isRightShortcutAppInAnim() || getShortcutAnimView().isLeftShortcutAppInAnim();
        }
        return false;
    }

    public void onBouncerShownChanged(boolean z) {
    }

    public void onFoldStateChanged(boolean z) throws IOException {
        if (z) {
            cancelAnimations();
            reset("onFoldStateChanged->True");
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.BaseShortcutAnimController
    public void reset(String reason) throws IOException {
        Intrinsics.checkNotNullParameter(reason, "reason");
        super.reset(reason);
        this.isUnoccludedAnimStarting = false;
        setUnoccludedAnimStarted(false);
        MiuiShortcutManager.INSTANCE.setInAppExiting(false);
    }

    public final void setUnoccludedAnimStarted(boolean z) {
        if (this.isUnoccludedAnimStarted != z) {
            this.isUnoccludedAnimStarted = z;
            if (z) {
                this.callback.onUnoccludedAnimationStart();
            } else {
                getMainHandler().post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutUnoccludedAnimController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ShortcutUnoccludedAnimController._set_isUnoccludedAnimStarted_$lambda$0(this.f$0);
                    }
                });
            }
        }
    }
}
