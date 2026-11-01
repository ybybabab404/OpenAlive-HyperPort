package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import com.miui.keyguard.editor.data.bean.ScreenshotSourceKt;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import com.miui.keyguard.editor.utils.task.Task;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.functions.Function1;
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
/* JADX INFO: compiled from: HierarchyImageAvoidController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class HierarchyImageAvoidController implements View.OnAttachStateChangeListener {
    public final HierarchyImageView bindHierarchyImageView;
    public final CalculateWorker calculateWorker;
    public final Map calculateWorkers;
    public final Runnable checkRectRunnable;
    public final List hierarchyEnableChangeListeners;
    public final Rect imageViewRect;
    public boolean isHierarchyEnable;
    public final boolean isScreenshotTemplateViewCheck;
    public boolean isUserOpenDepthVideo;
    public boolean isUserOpenHierarchy;
    public OnScreenshotTemplateCheckListener onScreenshotTemplateCheckListener;
    public final Map screenshotCheckResult;
    public final Function1 screenshotTemplateCheck;
    public Set unSupportHierarchyTypes;

    public HierarchyImageAvoidController(HierarchyImageView bindHierarchyImageView, boolean z) {
        Intrinsics.checkNotNullParameter(bindHierarchyImageView, "bindHierarchyImageView");
        this.bindHierarchyImageView = bindHierarchyImageView;
        this.isScreenshotTemplateViewCheck = z;
        this.hierarchyEnableChangeListeners = new ArrayList();
        this.isHierarchyEnable = true;
        this.calculateWorker = new CalculateWorker(new WeakReference(bindHierarchyImageView));
        this.checkRectRunnable = new Runnable() { // from class: com.miui.keyguard.editor.utils.HierarchyImageAvoidController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                HierarchyImageAvoidController.checkRectRunnable$lambda$1(this.f$0);
            }
        };
        this.calculateWorkers = new LinkedHashMap();
        this.imageViewRect = new Rect();
        this.screenshotCheckResult = new LinkedHashMap();
        this.unSupportHierarchyTypes = CollectionsKt.toMutableSet(ScreenshotSourceKt.getDefaultNotSupportTypes());
        this.screenshotTemplateCheck = new HierarchyImageAvoidController$screenshotTemplateCheck$1(this, null);
        bindHierarchyImageView.addOnAttachStateChangeListener(this);
    }

    public static final void checkRectRunnable$lambda$1(final HierarchyImageAvoidController hierarchyImageAvoidController) {
        if (hierarchyImageAvoidController.calculateWorker.isWorking()) {
            return;
        }
        hierarchyImageAvoidController.calculateWorker.setWorking(true);
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
        keyguardEditorMiSightUtil.hierarchyComputeStart(hierarchyImageAvoidController.currentCheckType());
        Context context = hierarchyImageAvoidController.bindHierarchyImageView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        if (!DualClockManager.isDualClockAvailable(context)) {
            Task.create(hierarchyImageAvoidController.calculateWorker).post(new Consumer() { // from class: com.miui.keyguard.editor.utils.HierarchyImageAvoidController$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    HierarchyImageAvoidController.checkRectRunnable$lambda$1$lambda$0(this.f$0, (Boolean) obj);
                }
            });
            return;
        }
        hierarchyImageAvoidController.calculateWorker.setWorking(false);
        hierarchyImageAvoidController.onHierarchyEnableChange(false, hierarchyImageAvoidController.calculateWorker.isDepthVideo());
        KeyguardEditorMiSightUtil.hierarchyComputeEnd$default(keyguardEditorMiSightUtil, hierarchyImageAvoidController.currentCheckType(), null, 2, null);
        if (hierarchyImageAvoidController.calculateWorker.isDepthVideo()) {
            hierarchyImageAvoidController.calculateWorker.reset();
        }
    }

    public static final void checkRectRunnable$lambda$1$lambda$0(HierarchyImageAvoidController hierarchyImageAvoidController, Boolean bool) {
        hierarchyImageAvoidController.calculateWorker.setWorking(false);
        Log.i("HierarchyImageAvoidCutController", "check result " + bool + " isUserOpenHierarchy " + hierarchyImageAvoidController.isUserOpenHierarchy + " isHierarchyEnable " + hierarchyImageAvoidController.isHierarchyEnable() + " isDepthVideo=" + hierarchyImageAvoidController.calculateWorker.isDepthVideo());
        if ((hierarchyImageAvoidController.isUserOpenHierarchy || (hierarchyImageAvoidController.isUserOpenDepthVideo && hierarchyImageAvoidController.calculateWorker.isDepthVideo())) && bool != null) {
            hierarchyImageAvoidController.onHierarchyEnableChange(bool.booleanValue(), hierarchyImageAvoidController.calculateWorker.isDepthVideo());
            if (hierarchyImageAvoidController.calculateWorker.isDepthVideo()) {
                hierarchyImageAvoidController.calculateWorker.reset();
            }
        }
        KeyguardEditorMiSightUtil.hierarchyComputeEnd$default(KeyguardEditorMiSightUtil.INSTANCE, hierarchyImageAvoidController.currentCheckType(), null, 2, null);
    }

    public static /* synthetic */ void onImageViewLayout$default(HierarchyImageAvoidController hierarchyImageAvoidController, int i, int i2, int i3, int i4, boolean z, int i5, Object obj) {
        if ((i5 & 16) != 0) {
            z = false;
        }
        hierarchyImageAvoidController.onImageViewLayout(i, i2, i3, i4, z);
    }

    public final void addHierarchyEnableChangeListener(OnHierarchyEnableChangeListener hierarchyEnableChangeListener) {
        Intrinsics.checkNotNullParameter(hierarchyEnableChangeListener, "hierarchyEnableChangeListener");
        if (this.hierarchyEnableChangeListeners.contains(hierarchyEnableChangeListener)) {
            return;
        }
        this.hierarchyEnableChangeListeners.add(hierarchyEnableChangeListener);
    }

    public final void checkRemoveAlreadyPostRunnable() {
        if (HierarchyImageAvoidControllerKt.checkRectHandler.hasCallbacks(this.checkRectRunnable)) {
            HierarchyImageAvoidControllerKt.checkRectHandler.removeCallbacks(this.checkRectRunnable);
        }
    }

    public final void clearOldAndSendNewRunnable() {
        checkRemoveAlreadyPostRunnable();
        HierarchyImageAvoidControllerKt.checkRectHandler.postDelayed(this.checkRectRunnable, 100L);
    }

    public final String currentCheckType() {
        if (this.isScreenshotTemplateViewCheck) {
            return null;
        }
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad() || !this.calculateWorker.getShouldCheck()) {
            return null;
        }
        Context context = this.bindHierarchyImageView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        boolean zIsDualClockEnable = DualClockManager.isDualClockEnable(context);
        boolean z = true;
        if (!deviceUtil.isPhone() && this.bindHierarchyImageView.getResources().getConfiguration().orientation != 1) {
            z = false;
        }
        Context context2 = this.bindHierarchyImageView.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        if (!deviceUtil.isLargeDisplay(context2)) {
            return zIsDualClockEnable ? "hierarchy_check_small_screen_dual_clock" : "hierarchy_check_small_screen";
        }
        if (z) {
            return zIsDualClockEnable ? "hierarchy_check_large_screen_port_dual_clock" : "hierarchy_check_large_screen_port";
        }
        return zIsDualClockEnable ? "hierarchy_check_large_screen_land_dual_clock" : "hierarchy_check_large_screen_land";
    }

    public final boolean isHierarchyEnable() {
        return this.isHierarchyEnable && this.isUserOpenHierarchy;
    }

    public final boolean isUserOpenDepthVideo() {
        return this.isUserOpenDepthVideo;
    }

    public final Object notifyScreenshotCheckListener(Continuation continuation) {
        Object objOnHierarchyCheckEnd;
        String strCurrentCheckType = currentCheckType();
        Log.i("HierarchyImageAvoidCutController", "currentCheckType " + strCurrentCheckType);
        if (strCurrentCheckType != null && strCurrentCheckType.length() != 0) {
            this.screenshotCheckResult.put(strCurrentCheckType, Boxing.boxBoolean(isHierarchyEnable()));
        }
        Iterator it = this.unSupportHierarchyTypes.iterator();
        while (it.hasNext()) {
            this.screenshotCheckResult.put((String) it.next(), Boxing.boxBoolean(false));
        }
        OnScreenshotTemplateCheckListener onScreenshotTemplateCheckListener = this.onScreenshotTemplateCheckListener;
        return (onScreenshotTemplateCheckListener == null || (objOnHierarchyCheckEnd = onScreenshotTemplateCheckListener.onHierarchyCheckEnd(this.screenshotCheckResult, continuation)) != IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? Unit.INSTANCE : objOnHierarchyCheckEnd;
    }

    public final void onDepthVideoChange(DepthVideoParam param) {
        Intrinsics.checkNotNullParameter(param, "param");
        if (this.calculateWorker.getShouldCheck()) {
            this.calculateWorker.setDepthVideoCheckParam(param);
            clearOldAndSendNewRunnable();
        }
    }

    public final void onHierarchyEnableChange(boolean z, boolean z2) {
        if (z != isHierarchyEnable() || z2) {
            Log.i("HierarchyImageAvoidCutController", "[DepthAvoid] hierarchyEnable changed: " + isHierarchyEnable() + " -> " + z + ", isDepthVideo=" + z2 + ", isUserOpen=" + this.isUserOpenHierarchy + ", checkType=" + currentCheckType());
            this.isHierarchyEnable = z;
            Iterator it = this.hierarchyEnableChangeListeners.iterator();
            while (it.hasNext()) {
                ((OnHierarchyEnableChangeListener) it.next()).onHierarchyEnableChange(z);
            }
        }
    }

    public final void onImageBitmapChange(Bitmap bitmap) {
        Log.d("HierarchyImageAvoidCutController", "isScreenshotTemplateViewCheck=" + this.isScreenshotTemplateViewCheck + " shouldCheck=" + this.calculateWorker.getShouldCheck() + " " + (bitmap == null));
        if (!this.isScreenshotTemplateViewCheck && this.calculateWorker.getShouldCheck() && bitmap == null) {
            checkRemoveAlreadyPostRunnable();
        }
    }

    public final void onImageViewDraw() {
        if (this.calculateWorker.getShouldCheck() && this.isUserOpenHierarchy) {
            clearOldAndSendNewRunnable();
        }
    }

    public final void onImageViewLayout(int i, int i2, int i3, int i4, boolean z) {
        this.imageViewRect.set(i, i2, i3, i4);
        if (!this.isScreenshotTemplateViewCheck && !z) {
            this.calculateWorker.onImageLocationChange(i, i2, i3, i4);
            return;
        }
        Iterator it = this.calculateWorkers.entrySet().iterator();
        while (it.hasNext()) {
            ((CalculateWorker) ((Map.Entry) it.next()).getValue()).onImageLocationChange(i, i2, i3, i4);
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        checkRemoveAlreadyPostRunnable();
    }

    public final void requestCheckOnce() {
        if (!this.calculateWorker.getShouldCheck() || this.calculateWorker.isWorking()) {
            return;
        }
        clearOldAndSendNewRunnable();
    }

    public final void resetAvoidRect(Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        setUpAvoidRect(rect);
        CalculateWorker calculateWorker = this.calculateWorker;
        Rect rect2 = this.imageViewRect;
        calculateWorker.onImageLocationChange(rect2.left, rect2.top, rect2.right, rect2.bottom);
        this.bindHierarchyImageView.invalidate();
    }

    public final void setHierarchyEnable(boolean z) {
        this.isHierarchyEnable = z;
    }

    public final void setUpAvoidRect(Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        this.calculateWorker.setUpAvoidRect(rect);
    }

    public final void setUpAvoidRect(Map rectMap, Set unSupportHierarchyTypes) {
        Intrinsics.checkNotNullParameter(rectMap, "rectMap");
        Intrinsics.checkNotNullParameter(unSupportHierarchyTypes, "unSupportHierarchyTypes");
        Log.i("HierarchyImageAvoidCutController", "setUpAvoidRect " + rectMap + ", unSupportHierarchyMap=" + unSupportHierarchyTypes);
        this.unSupportHierarchyTypes.addAll(unSupportHierarchyTypes);
        for (Map.Entry entry : rectMap.entrySet()) {
            String str = (String) entry.getKey();
            Rect rect = (Rect) entry.getValue();
            Map map = this.calculateWorkers;
            CalculateWorker calculateWorker = new CalculateWorker(new WeakReference(this.bindHierarchyImageView));
            calculateWorker.setUpAvoidRect(rect);
            map.put(str, calculateWorker);
        }
    }

    public final void setUserOpenDepthVideo(boolean z) {
        this.isUserOpenDepthVideo = z;
    }

    public final void setUserOpenHierarchy(boolean z) {
        this.isUserOpenHierarchy = z;
    }

    public final Object startScreenshotCheck(OnScreenshotTemplateCheckListener onScreenshotTemplateCheckListener, Continuation continuation) {
        this.onScreenshotTemplateCheckListener = onScreenshotTemplateCheckListener;
        Object objInvoke = this.screenshotTemplateCheck.invoke(continuation);
        return objInvoke == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objInvoke : Unit.INSTANCE;
    }
}
