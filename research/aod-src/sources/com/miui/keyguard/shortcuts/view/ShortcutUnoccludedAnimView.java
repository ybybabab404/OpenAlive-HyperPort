package com.miui.keyguard.shortcuts.view;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.widget.ImageView;
import com.miui.keyguard.shortcuts.manager.MiuiShortcutManager;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.keyguard.shortcuts.utils.RemoteAnimationTargetExtKt;
import com.miui.keyguard.shortcuts.utils.SyncRtSurfaceTransactionApplierExtKt;
import com.miui.keyguard.shortcuts.utils.UnoccludedAnimationUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.controller.AnimState;
import miuix.animation.property.FloatProperty;
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
/* JADX INFO: compiled from: ShortcutUnoccludedAnimView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SuppressLint({"ViewConstructor"})
@SourceDebugExtension
public class ShortcutUnoccludedAnimView extends BaseShortcutAnimView {
    public static final Companion Companion = new Companion(null);
    public Map hasReparentMap;
    public RemoteAnimationTarget[] remoteAnimationTargetArray;
    public final Lazy unoccludedAnimDefaultEndState$delegate;
    public final Lazy unoccludedAnimDefaultEndStateAsync$delegate;
    public final Lazy unoccludedAnimDefaultStartState$delegate;
    public final Lazy unoccludedAnimDefaultStartStateAsync$delegate;

    /* JADX INFO: compiled from: ShortcutUnoccludedAnimView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutUnoccludedAnimView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.hasReparentMap = new LinkedHashMap();
        this.unoccludedAnimDefaultStartState$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.view.ShortcutUnoccludedAnimView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ShortcutUnoccludedAnimView.unoccludedAnimDefaultStartState_delegate$lambda$2();
            }
        });
        this.unoccludedAnimDefaultEndState$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.view.ShortcutUnoccludedAnimView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ShortcutUnoccludedAnimView.unoccludedAnimDefaultEndState_delegate$lambda$3();
            }
        });
        this.unoccludedAnimDefaultEndStateAsync$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.view.ShortcutUnoccludedAnimView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ShortcutUnoccludedAnimView.unoccludedAnimDefaultEndStateAsync_delegate$lambda$4();
            }
        });
        this.unoccludedAnimDefaultStartStateAsync$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.view.ShortcutUnoccludedAnimView$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ShortcutUnoccludedAnimView.unoccludedAnimDefaultStartStateAsync_delegate$lambda$5();
            }
        });
    }

    public static final AnimState unoccludedAnimDefaultEndStateAsync_delegate$lambda$4() {
        return new AnimState("UnoccludedAnimDefaultEndStateAsync").add((FloatProperty) UnoccludedAnimationUtils.INSTANCE.getTAG_DEFAULT_LEASH_ALPHA(), 0.0f, new long[0]);
    }

    public static final AnimState unoccludedAnimDefaultEndState_delegate$lambda$3() {
        AnimState animState = new AnimState("buildUnoccludedAnimDefaultEndState");
        UnoccludedAnimationUtils unoccludedAnimationUtils = UnoccludedAnimationUtils.INSTANCE;
        return animState.add((FloatProperty) unoccludedAnimationUtils.getTAG_DEFAULT_LEASH_ALPHA(), 0.0f, new long[0]).add((FloatProperty) unoccludedAnimationUtils.getTAG_DEFAULT_LEASH_SCALE(), 0.95f, new long[0]);
    }

    public static final AnimState unoccludedAnimDefaultStartStateAsync_delegate$lambda$5() {
        return new AnimState("UnoccludedAnimDefaultStartStateAsync").add((FloatProperty) UnoccludedAnimationUtils.INSTANCE.getTAG_DEFAULT_LEASH_ALPHA(), 1.0f, new long[0]);
    }

    public static final AnimState unoccludedAnimDefaultStartState_delegate$lambda$2() {
        AnimState animState = new AnimState("UnoccludedAnimDefaultStartState");
        UnoccludedAnimationUtils unoccludedAnimationUtils = UnoccludedAnimationUtils.INSTANCE;
        return animState.add((FloatProperty) unoccludedAnimationUtils.getTAG_DEFAULT_LEASH_ALPHA(), 1.0f, new long[0]).add((FloatProperty) unoccludedAnimationUtils.getTAG_DEFAULT_LEASH_SCALE(), 1.0f, new long[0]);
    }

    public final boolean checkUnoccludedRemoteAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0) {
            return false;
        }
        this.remoteAnimationTargetArray = remoteAnimationTargetArr;
        if (remoteAnimationTargetArr != null) {
            setFinishedCallback(iRemoteAnimationFinishedCallback);
            ArrayList arrayList = new ArrayList(remoteAnimationTargetArr.length);
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                String str = remoteAnimationTarget.packageName;
                ActivityManager.RunningTaskInfo runningTaskInfoByReflection = RemoteAnimationTargetExtKt.getRunningTaskInfoByReflection(remoteAnimationTarget);
                arrayList.add("[target.package=" + str + ", target.realActivity=" + (runningTaskInfoByReflection != null ? RemoteAnimationTargetExtKt.getRealActivityByReflection(runningTaskInfoByReflection) : null) + "]");
            }
            LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutUnoccludedAnimView", "checkUnoccludedRemoteAnimation, targetApps.size=" + remoteAnimationTargetArr.length + ", " + arrayList, null, 4, null);
        }
        Iterator it = this.hasReparentMap.entrySet().iterator();
        while (it.hasNext()) {
            ((SurfaceControl) ((Map.Entry) it.next()).getKey()).release();
        }
        this.hasReparentMap.clear();
        return true;
    }

    @Override // com.miui.keyguard.shortcuts.view.BaseShortcutAnimView
    public void doSurfaceControlReparent(SurfaceControl surfaceControl, SurfaceControl parentSc) {
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        Intrinsics.checkNotNullParameter(parentSc, "parentSc");
        if (!surfaceControl.isValid() || !parentSc.isValid()) {
            LogUtils.logE$default(LogUtils.INSTANCE, "ShortcutUnoccludedAnimView", "parentSc.isNotValid!!!!!", null, 4, null);
            return;
        }
        Object obj = this.hasReparentMap.get(surfaceControl);
        Boolean bool = Boolean.TRUE;
        if (Intrinsics.areEqual(obj, bool)) {
            return;
        }
        this.hasReparentMap.put(surfaceControl, bool);
        getTransaction().show(parentSc);
        getTransaction().reparent(surfaceControl, parentSc);
        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutUnoccludedAnimView", "doSurfaceControlReparentApply success!", null, 4, null);
    }

    public final void doUnoccludedAnimationEnd() {
        doRemoteAnimationEnd();
        this.hasReparentMap.clear();
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.remoteAnimationTargetArray;
        if (remoteAnimationTargetArr != null) {
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                remoteAnimationTarget.leash.release();
            }
        }
        this.remoteAnimationTargetArray = null;
    }

    public final void doUnoccludedAnimationUpdateForDefault(float f, float f2) {
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.remoteAnimationTargetArray;
        if (remoteAnimationTargetArr != null) {
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                SurfaceControl surfaceControl = remoteAnimationTarget.leash;
                if (surfaceControl != null && surfaceControl.isValid()) {
                    getRemoteAnimMatrix().setScale(f2, f2, getScreenWidth() / 2.0f, getScreenHeight() / 2.0f);
                    doSurfaceControlReparentApply(remoteAnimationTarget.leash);
                    SurfaceControl leash = remoteAnimationTarget.leash;
                    Intrinsics.checkNotNullExpressionValue(leash, "leash");
                    createSyncRtSurfaceParamsAndApply(leash, RemoteAnimationTargetExtKt.getLocalBoundsByReflection(remoteAnimationTarget), f);
                }
            }
        }
    }

    public final void doUnoccludedAnimationUpdateForDefaultAsync(float f) {
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.remoteAnimationTargetArray;
        if (remoteAnimationTargetArr != null) {
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                SurfaceControl surfaceControl = remoteAnimationTarget.leash;
                if (surfaceControl != null && surfaceControl.isValid()) {
                    getTransaction().setAlpha(remoteAnimationTarget.leash, f);
                    doSurfaceControlReparentApply(remoteAnimationTarget.leash);
                }
            }
        }
    }

    public final void doUnoccludedAnimationUpdateForShortcut(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.remoteAnimationTargetArray;
        if (remoteAnimationTargetArr != null) {
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                doUnoccludedSurfaceControlForShortcutSync(remoteAnimationTarget, f, f2, f3, f4, f5);
            }
            ImageView iconView = getIconView();
            if (iconView != null) {
                iconView.setAlpha(f7);
                MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
                iconView.setVisibility(miuiShortcutManager.isKeyguardShowing() && !miuiShortcutManager.isBouncerShowing() ? 0 : 8);
                updateIconViewPositionAndScale(f6);
            }
            setIconCircleAlpha(f8);
            invalidate();
        }
    }

    public final void doUnoccludedLeashAnimationForBouncerShowing(float f) {
        SyncRtSurfaceTransactionApplier.SurfaceParams surfaceParamsBuildByReflection;
        SyncRtSurfaceTransactionApplier synSurfaceTransactionApplier;
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.remoteAnimationTargetArray;
        if (remoteAnimationTargetArr != null) {
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                SurfaceControl surfaceControl = remoteAnimationTarget.leash;
                if (surfaceControl != null && surfaceControl.isValid()) {
                    SurfaceControl leash = remoteAnimationTarget.leash;
                    Intrinsics.checkNotNullExpressionValue(leash, "leash");
                    SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builderNewSyncRtSurfaceTransactionApplierSurfaceParamsBuilderByReflection = SyncRtSurfaceTransactionApplierExtKt.newSyncRtSurfaceTransactionApplierSurfaceParamsBuilderByReflection(leash);
                    SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builderWithAlphaByReflection = builderNewSyncRtSurfaceTransactionApplierSurfaceParamsBuilderByReflection != null ? SyncRtSurfaceTransactionApplierExtKt.withAlphaByReflection(builderNewSyncRtSurfaceTransactionApplierSurfaceParamsBuilderByReflection, f) : null;
                    if (builderWithAlphaByReflection != null && (surfaceParamsBuildByReflection = SyncRtSurfaceTransactionApplierExtKt.buildByReflection(builderWithAlphaByReflection)) != null && (synSurfaceTransactionApplier = getSynSurfaceTransactionApplier()) != null) {
                        SyncRtSurfaceTransactionApplierExtKt.scheduleApplyByReflection(synSurfaceTransactionApplier, surfaceParamsBuildByReflection);
                    }
                }
            }
        }
    }

    public final void doUnoccludedLeashAnimationForBouncerShowingAsync(float f) {
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.remoteAnimationTargetArray;
        if (remoteAnimationTargetArr != null) {
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                SurfaceControl surfaceControl = remoteAnimationTarget.leash;
                if (surfaceControl != null && surfaceControl.isValid()) {
                    getTransaction().setAlpha(remoteAnimationTarget.leash, f).apply();
                }
            }
        }
    }

    public final void doUnoccludedSurfaceControlForShortcutSync(RemoteAnimationTarget remoteAnimationTarget, float f, float f2, float f3, float f4, float f5) {
        if (remoteAnimationTarget.leash == null) {
            return;
        }
        float screenWidth = f / getScreenWidth();
        boolean z = f >= f2;
        if (z) {
            f2 = f;
        }
        float screenHeight = getScreenHeight() * screenWidth;
        setIconCircleWidth(f);
        setIconCircleHeight(f2);
        Rect localBoundsByReflection = RemoteAnimationTargetExtKt.getLocalBoundsByReflection(remoteAnimationTarget);
        if (z) {
            setIconCircleWidth(f);
            setIconCircleHeight(f);
            setIconCircleRadiusL(getScreenWidth());
            setIconCircleRadiusR(getIconCircleRadiusL());
            if (localBoundsByReflection.width() != localBoundsByReflection.height()) {
                int iHeight = (localBoundsByReflection.height() - localBoundsByReflection.width()) / 2;
                localBoundsByReflection.top += iHeight;
                localBoundsByReflection.bottom -= iHeight;
            }
        } else {
            int iHeight2 = (int) ((localBoundsByReflection.height() - (f2 / screenWidth)) / 2.0f);
            localBoundsByReflection.top += iHeight2;
            localBoundsByReflection.bottom -= iHeight2;
            if (localBoundsByReflection.width() > localBoundsByReflection.height()) {
                int iWidth = (localBoundsByReflection.width() - localBoundsByReflection.height()) / 2;
                localBoundsByReflection.top -= iWidth;
                localBoundsByReflection.bottom += iWidth;
            }
            float f6 = 1 - screenWidth;
            setIconCircleRadiusL(getWindowTopLeftRadius() + (getIconCircleWidth() * f6));
            setIconCircleRadiusR(getWindowTopRightRadius() + (getIconCircleWidth() * f6));
        }
        if (Float.isNaN(f3)) {
            f3 = getScreenWidth() / 2.0f;
        }
        setIconCircleCenterX(f3);
        if (Float.isNaN(f4)) {
            f4 = getScreenHeight() / 2.0f;
        }
        setIconCircleCenterY(f4);
        float iconCircleCenterX = getIconCircleCenterX() - (f / 2.0f);
        float iconCircleCenterY = getIconCircleCenterY() - (screenHeight / 2.0f);
        Matrix remoteAnimMatrix = getRemoteAnimMatrix();
        remoteAnimMatrix.setTranslate(iconCircleCenterX, iconCircleCenterY);
        remoteAnimMatrix.postScale(screenWidth, screenWidth, iconCircleCenterX, iconCircleCenterY);
        doSurfaceControlReparentApply(remoteAnimationTarget.leash);
        SurfaceControl leash = remoteAnimationTarget.leash;
        Intrinsics.checkNotNullExpressionValue(leash, "leash");
        createSyncRtSurfaceParamsAndApply(leash, localBoundsByReflection, f5);
    }

    @Override // com.miui.keyguard.shortcuts.view.BaseShortcutAnimView
    @Nullable
    public String getAnimAppPackageName() {
        RemoteAnimationTarget remoteAnimationTarget;
        ActivityManager.RunningTaskInfo runningTaskInfoByReflection;
        ComponentName realActivityByReflection;
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.remoteAnimationTargetArray;
        String packageName = (remoteAnimationTargetArr == null || (remoteAnimationTarget = (RemoteAnimationTarget) ArraysKt.first(remoteAnimationTargetArr)) == null || (runningTaskInfoByReflection = RemoteAnimationTargetExtKt.getRunningTaskInfoByReflection(remoteAnimationTarget)) == null || (realActivityByReflection = RemoteAnimationTargetExtKt.getRealActivityByReflection(runningTaskInfoByReflection)) == null) ? null : realActivityByReflection.getPackageName();
        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutUnoccludedAnimView", "getAnimAppPackageName, packageName: " + packageName, null, 4, null);
        return packageName;
    }

    @NotNull
    public final AnimState getUnoccludedAnimDefaultEndState() {
        Object value = this.unoccludedAnimDefaultEndState$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (AnimState) value;
    }

    @NotNull
    public final AnimState getUnoccludedAnimDefaultEndStateAsync() {
        Object value = this.unoccludedAnimDefaultEndStateAsync$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (AnimState) value;
    }

    @NotNull
    public final AnimState getUnoccludedAnimDefaultStartState() {
        Object value = this.unoccludedAnimDefaultStartState$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (AnimState) value;
    }

    @NotNull
    public final AnimState getUnoccludedAnimDefaultStartStateAsync() {
        Object value = this.unoccludedAnimDefaultStartStateAsync$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (AnimState) value;
    }

    @Override // com.miui.keyguard.shortcuts.view.BaseShortcutAnimView
    public void initSynSurfaceTransactionApplier() {
        if (this.remoteAnimationTargetArray != null) {
            super.initSynSurfaceTransactionApplier();
        }
    }

    public final void reparentUnoccludedSurfaceControl() {
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.remoteAnimationTargetArray;
        if (remoteAnimationTargetArr != null) {
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                if (!Intrinsics.areEqual(this.hasReparentMap.get(remoteAnimationTarget.leash), Boolean.TRUE)) {
                    doSurfaceControlReparentApply(remoteAnimationTarget.leash);
                }
            }
        }
    }
}
