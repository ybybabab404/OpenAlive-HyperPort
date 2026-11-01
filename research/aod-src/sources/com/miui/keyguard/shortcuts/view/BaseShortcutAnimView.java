package com.miui.keyguard.shortcuts.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.keyguard.shortcuts.manager.MiuiShortcutManager;
import com.miui.keyguard.shortcuts.manager.ShortcutWindowManager;
import com.miui.keyguard.shortcuts.utils.AnimationUtils;
import com.miui.keyguard.shortcuts.utils.DeviceHelper;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.keyguard.shortcuts.utils.SyncRtSurfaceTransactionApplierExtKt;
import com.miui.keyguard.shortcuts.utils.TransactionExtKt;
import com.miui.keyguard.shortcuts.utils.ViewUtils;
import com.miui.keyguard.shortcuts.utils.WindowUtils;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import miuix.animation.IStateStyle;
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
/* JADX INFO: compiled from: BaseShortcutAnimView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SuppressLint({"ViewConstructor"})
@SourceDebugExtension
public abstract class BaseShortcutAnimView extends FrameLayout {
    public static final Companion Companion = new Companion(null);
    public final Lazy backgroundPath$delegate;
    public final Lazy backgroundRadii$delegate;
    public final Lazy backgroundRectF$delegate;
    public final Configuration configuration;
    public CurrentShortcutIcon currentShortcutIcon;
    public IRemoteAnimationFinishedCallback finishedCallback;
    public IStateStyle folmeAnim;
    public float iconCircleActiveCenterX;
    public float iconCircleActiveCenterY;
    public float iconCircleActiveWidth;
    public float iconCircleAlpha;
    public float iconCircleCenterX;
    public float iconCircleCenterY;
    public float iconCircleHeight;
    public float iconCircleInitCenterX;
    public float iconCircleInitCenterY;
    public Paint iconCirclePaint;
    public float iconCircleRadiusL;
    public float iconCircleRadiusR;
    public float iconCircleRectHeightWidthRatio;
    public Paint iconCircleStrokePaint;
    public float iconCircleWidth;
    public int iconHeight;
    public ImageView iconView;
    public int iconWidth;
    public boolean isIconDarkMode;
    public SurfaceControl parentSc;
    public final Matrix remoteAnimMatrix;
    public SurfaceControl rootViewSurfaceControl;
    public int screenHeight;
    public Point screenSizePoint;
    public int screenWidth;
    public final Lazy setMiCornerRadii$delegate;
    public final Lazy strokePath$delegate;
    public final Lazy strokeRadii$delegate;
    public final Lazy strokeRectF$delegate;
    public SyncRtSurfaceTransactionApplier synSurfaceTransactionApplier;
    public SurfaceControl.Transaction transaction;
    public float virHeight;
    public float virWidth;
    public float virX;
    public float virY;
    public float windowTopLeftRadius;
    public float windowTopRightRadius;
    public final Lazy withMiCornerRadii$delegate;

    /* JADX INFO: compiled from: BaseShortcutAnimView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: BaseShortcutAnimView.kt */
    @Metadata
    public enum CurrentShortcutIcon {
        NONE,
        LEFT,
        RIGHT;

        public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseShortcutAnimView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.screenSizePoint = new Point();
        WindowUtils windowUtils = WindowUtils.INSTANCE;
        this.windowTopLeftRadius = windowUtils.getPhoneRadius(context, 0);
        this.windowTopRightRadius = windowUtils.getPhoneRadius(context, 1);
        this.iconCirclePaint = new Paint();
        this.iconCircleStrokePaint = new Paint();
        this.strokePath$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.view.BaseShortcutAnimView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return BaseShortcutAnimView.strokePath_delegate$lambda$0();
            }
        });
        this.backgroundPath$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.view.BaseShortcutAnimView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return BaseShortcutAnimView.backgroundPath_delegate$lambda$1();
            }
        });
        this.strokeRectF$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.view.BaseShortcutAnimView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return BaseShortcutAnimView.strokeRectF_delegate$lambda$2();
            }
        });
        this.backgroundRectF$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.view.BaseShortcutAnimView$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return BaseShortcutAnimView.backgroundRectF_delegate$lambda$3();
            }
        });
        this.strokeRadii$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.view.BaseShortcutAnimView$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return BaseShortcutAnimView.strokeRadii_delegate$lambda$4();
            }
        });
        this.backgroundRadii$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.view.BaseShortcutAnimView$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return BaseShortcutAnimView.backgroundRadii_delegate$lambda$5();
            }
        });
        this.withMiCornerRadii$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.view.BaseShortcutAnimView$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return BaseShortcutAnimView.withMiCornerRadii_delegate$lambda$6();
            }
        });
        this.setMiCornerRadii$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.shortcuts.view.BaseShortcutAnimView$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return BaseShortcutAnimView.setMiCornerRadii_delegate$lambda$7();
            }
        });
        this.currentShortcutIcon = CurrentShortcutIcon.NONE;
        this.configuration = new Configuration();
        this.remoteAnimMatrix = new Matrix();
        this.transaction = new SurfaceControl.Transaction();
        initViews();
        Paint paint = this.iconCirclePaint;
        paint.setColor(0);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Paint paint2 = this.iconCircleStrokePaint;
        paint2.setColor(16777215);
        paint2.setAlpha(51);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(2.0f);
        paint2.setAntiAlias(true);
        setWillNotDraw(false);
    }

    public static final Path backgroundPath_delegate$lambda$1() {
        return new Path();
    }

    public static final float[] backgroundRadii_delegate$lambda$5() {
        return new float[8];
    }

    public static final RectF backgroundRectF_delegate$lambda$3() {
        return new RectF();
    }

    private final Path getBackgroundPath() {
        return (Path) this.backgroundPath$delegate.getValue();
    }

    private final float[] getBackgroundRadii() {
        return (float[]) this.backgroundRadii$delegate.getValue();
    }

    private final RectF getBackgroundRectF() {
        return (RectF) this.backgroundRectF$delegate.getValue();
    }

    private final float[] getSetMiCornerRadii() {
        return (float[]) this.setMiCornerRadii$delegate.getValue();
    }

    private final Path getStrokePath() {
        return (Path) this.strokePath$delegate.getValue();
    }

    private final float[] getStrokeRadii() {
        return (float[]) this.strokeRadii$delegate.getValue();
    }

    private final RectF getStrokeRectF() {
        return (RectF) this.strokeRectF$delegate.getValue();
    }

    private final float[] getWithMiCornerRadii() {
        return (float[]) this.withMiCornerRadii$delegate.getValue();
    }

    public static final float[] setMiCornerRadii_delegate$lambda$7() {
        return new float[8];
    }

    public static final Path strokePath_delegate$lambda$0() {
        return new Path();
    }

    public static final float[] strokeRadii_delegate$lambda$4() {
        return new float[8];
    }

    public static final RectF strokeRectF_delegate$lambda$2() {
        return new RectF();
    }

    public static /* synthetic */ void updateIconViewPositionAndScale$default(BaseShortcutAnimView baseShortcutAnimView, float f, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateIconViewPositionAndScale");
        }
        if ((i & 1) != 0) {
            f = 1.0f;
        }
        baseShortcutAnimView.updateIconViewPositionAndScale(f);
    }

    public static final float[] withMiCornerRadii_delegate$lambda$6() {
        return new float[8];
    }

    public final SurfaceControl buildParentSurfaceControl() {
        SurfaceControl surfaceControlBuild = new SurfaceControl.Builder().setName("miui_shortcut_leash").setParent(this.rootViewSurfaceControl).build();
        Intrinsics.checkNotNullExpressionValue(surfaceControlBuild, "build(...)");
        return surfaceControlBuild;
    }

    public final void checkSize() {
        Point point = new Point();
        Display display = getContext().getDisplay();
        if (display != null) {
            display.getRealSize(point);
        }
        if (this.screenSizePoint.equals(point.x, point.y)) {
            return;
        }
        this.screenSizePoint.set(point.x, point.y);
        updateSizeForScreenSizeChange();
        refreshView();
    }

    /* JADX WARN: Code duplicated, block: B:19:0x004b A[PHI: r0 r6
  0x004b: PHI (r0v4 com.miui.keyguard.shortcuts.view.BaseShortcutAnimView) = 
  (r0v2 com.miui.keyguard.shortcuts.view.BaseShortcutAnimView)
  (r0v5 com.miui.keyguard.shortcuts.view.BaseShortcutAnimView)
 binds: [B:23:0x005c, B:17:0x0048] A[DONT_GENERATE, DONT_INLINE]
  0x004b: PHI (r6v6 android.view.SyncRtSurfaceTransactionApplier$SurfaceParams$Builder) = 
  (r6v3 android.view.SyncRtSurfaceTransactionApplier$SurfaceParams$Builder)
  (r6v8 android.view.SyncRtSurfaceTransactionApplier$SurfaceParams$Builder)
 binds: [B:23:0x005c, B:17:0x0048] A[DONT_GENERATE, DONT_INLINE]] */
    public final void createSyncRtSurfaceParamsAndApply(SurfaceControl leash, Rect boundsRect, float f) {
        BaseShortcutAnimView baseShortcutAnimView;
        SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builderWithCornerRadiusByReflection;
        SyncRtSurfaceTransactionApplier.SurfaceParams surfaceParamsBuildByReflection;
        SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier;
        SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builderWithWindowCropByReflection;
        SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builderWithMatrixByReflection;
        Intrinsics.checkNotNullParameter(leash, "leash");
        Intrinsics.checkNotNullParameter(boundsRect, "boundsRect");
        SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builderNewSyncRtSurfaceTransactionApplierSurfaceParamsBuilderByReflection = SyncRtSurfaceTransactionApplierExtKt.newSyncRtSurfaceTransactionApplierSurfaceParamsBuilderByReflection(leash);
        SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builderWithAlphaByReflection = (builderNewSyncRtSurfaceTransactionApplierSurfaceParamsBuilderByReflection == null || (builderWithWindowCropByReflection = SyncRtSurfaceTransactionApplierExtKt.withWindowCropByReflection(builderNewSyncRtSurfaceTransactionApplierSurfaceParamsBuilderByReflection, boundsRect)) == null || (builderWithMatrixByReflection = SyncRtSurfaceTransactionApplierExtKt.withMatrixByReflection(builderWithWindowCropByReflection, this.remoteAnimMatrix)) == null) ? null : SyncRtSurfaceTransactionApplierExtKt.withAlphaByReflection(builderWithMatrixByReflection, f);
        if (isSameWindowRadius() || !SyncRtSurfaceTransactionApplierExtKt.supportWithMiCornerRadii()) {
            baseShortcutAnimView = this;
            float fMin = Math.min(baseShortcutAnimView.windowTopLeftRadius, baseShortcutAnimView.windowTopRightRadius);
            if (builderWithAlphaByReflection != null && (builderWithCornerRadiusByReflection = SyncRtSurfaceTransactionApplierExtKt.withCornerRadiusByReflection(builderWithAlphaByReflection, fMin)) != null) {
                builderWithAlphaByReflection = builderWithCornerRadiusByReflection;
            }
        } else {
            float[] withMiCornerRadii = getWithMiCornerRadii();
            float f2 = this.windowTopLeftRadius;
            float f3 = this.windowTopRightRadius;
            baseShortcutAnimView = this;
            baseShortcutAnimView.fillRadii(withMiCornerRadii, f2, f3, f3, f2);
            if (builderWithAlphaByReflection != null && (builderWithCornerRadiusByReflection = SyncRtSurfaceTransactionApplierExtKt.withMiCornerRadiusByReflection(builderWithAlphaByReflection, baseShortcutAnimView.getWithMiCornerRadii())) != null) {
                builderWithAlphaByReflection = builderWithCornerRadiusByReflection;
            }
        }
        if (builderWithAlphaByReflection == null || (surfaceParamsBuildByReflection = SyncRtSurfaceTransactionApplierExtKt.buildByReflection(builderWithAlphaByReflection)) == null || (syncRtSurfaceTransactionApplier = baseShortcutAnimView.synSurfaceTransactionApplier) == null) {
            return;
        }
        SyncRtSurfaceTransactionApplierExtKt.scheduleApplyByReflection(syncRtSurfaceTransactionApplier, surfaceParamsBuildByReflection);
    }

    public final void doRemoteAnimationEnd() {
        try {
            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = this.finishedCallback;
            if (iRemoteAnimationFinishedCallback != null) {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            }
        } catch (Exception e) {
            LogUtils.INSTANCE.logE("BaseShortcutAnimView", "doOccludedAnimationEnd, finish animation fail", e);
        }
        final SurfaceControl surfaceControl = this.parentSc;
        if (surfaceControl != null) {
            postDelayed(new Runnable() { // from class: com.miui.keyguard.shortcuts.view.BaseShortcutAnimView$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.reparentNullApply(surfaceControl);
                }
            }, 100L);
        }
        this.synSurfaceTransactionApplier = null;
        this.parentSc = null;
    }

    public void doSurfaceControlReparent(SurfaceControl surfaceControl, SurfaceControl parentSc) {
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        Intrinsics.checkNotNullParameter(parentSc, "parentSc");
    }

    public final void doSurfaceControlReparentApply(SurfaceControl surfaceControl) {
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        SurfaceControl surfaceControl2 = this.rootViewSurfaceControl;
        if (surfaceControl2 == null || !surfaceControl2.isValid()) {
            this.rootViewSurfaceControl = ShortcutWindowManager.INSTANCE.getViewRootImplSurfaceControl();
        }
        if (this.parentSc == null) {
            SurfaceControl surfaceControlBuildParentSurfaceControl = buildParentSurfaceControl();
            this.parentSc = surfaceControlBuildParentSurfaceControl;
            if (surfaceControlBuildParentSurfaceControl != null) {
                initParentSurfaceControl(surfaceControlBuildParentSurfaceControl);
            }
        }
        SurfaceControl surfaceControl3 = this.parentSc;
        if (surfaceControl3 != null) {
            doSurfaceControlReparent(surfaceControl, surfaceControl3);
        }
        this.transaction.apply();
    }

    public final void drawWithPath(float f, float f2, float f3, float f4, Canvas canvas) {
        getStrokePath().reset();
        getStrokeRectF().set(f - 2.0f, f2 - 2.0f, f3 + 2.0f, 2.0f + f4);
        float[] strokeRadii = getStrokeRadii();
        float f5 = this.iconCircleRadiusL;
        float f6 = this.iconCircleRadiusR;
        fillRadii(strokeRadii, f5, f6, f6, f5);
        Path strokePath = getStrokePath();
        RectF strokeRectF = getStrokeRectF();
        float[] strokeRadii2 = getStrokeRadii();
        Path.Direction direction = Path.Direction.CW;
        strokePath.addRoundRect(strokeRectF, strokeRadii2, direction);
        canvas.drawPath(getStrokePath(), this.iconCircleStrokePaint);
        getBackgroundPath().reset();
        getBackgroundRectF().set(f, f2, f3, f4);
        float[] backgroundRadii = getBackgroundRadii();
        float f7 = this.iconCircleRadiusL;
        float f8 = this.iconCircleRadiusR;
        fillRadii(backgroundRadii, f7, f8, f8, f7);
        getBackgroundPath().addRoundRect(getBackgroundRectF(), getBackgroundRadii(), direction);
        canvas.drawPath(getBackgroundPath(), this.iconCirclePaint);
    }

    public final void drawWithRoundRect(float f, float f2, float f3, float f4, Canvas canvas) {
        canvas.drawRoundRect(f - 2.0f, f2 - 2.0f, f3 + 2.0f, f4 + 2.0f, this.iconCircleRadiusL, this.iconCircleRadiusR, this.iconCircleStrokePaint);
        canvas.drawRoundRect(f, f2, f3, f4, this.iconCircleRadiusL, this.iconCircleRadiusR, this.iconCirclePaint);
    }

    public final void fillRadii(float[] fArr, float f, float f2, float f3, float f4) {
        if (fArr.length != 8) {
            throw new IllegalArgumentException("radii.length must be 8");
        }
        fArr[0] = f;
        fArr[1] = f;
        fArr[2] = f2;
        fArr[3] = f2;
        fArr[4] = f3;
        fArr[5] = f3;
        fArr[6] = f4;
        fArr[7] = f4;
    }

    @Nullable
    public String getAnimAppPackageName() {
        return "";
    }

    @NotNull
    public final Configuration getConfiguration() {
        return this.configuration;
    }

    public final int getCurrentScreenHeight() {
        return this.screenHeight;
    }

    public final int getCurrentScreenWidth() {
        return this.screenWidth;
    }

    @NotNull
    public final CurrentShortcutIcon getCurrentShortcutIcon() {
        return this.currentShortcutIcon;
    }

    @Nullable
    public final IRemoteAnimationFinishedCallback getFinishedCallback() {
        return this.finishedCallback;
    }

    @Nullable
    public final IStateStyle getFolmeAnim() {
        return this.folmeAnim;
    }

    public final float getIconCircleActiveCenterX() {
        return this.iconCircleActiveCenterX;
    }

    public final float getIconCircleActiveCenterY() {
        return this.iconCircleActiveCenterY;
    }

    public final float getIconCircleActiveWidth() {
        return this.iconCircleActiveWidth;
    }

    public final float getIconCircleAlpha() {
        return this.iconCircleAlpha;
    }

    public final float getIconCircleCenterX() {
        return this.iconCircleCenterX;
    }

    public final float getIconCircleCenterY() {
        return this.iconCircleCenterY;
    }

    public final float getIconCircleHeight() {
        return this.iconCircleHeight;
    }

    public final float getIconCircleInitCenterX() {
        return this.iconCircleInitCenterX;
    }

    public final float getIconCircleInitCenterY() {
        return this.iconCircleInitCenterY;
    }

    @NotNull
    public final Paint getIconCirclePaint() {
        return this.iconCirclePaint;
    }

    public final float getIconCircleRadiusL() {
        return this.iconCircleRadiusL;
    }

    public final float getIconCircleRadiusR() {
        return this.iconCircleRadiusR;
    }

    public final float getIconCircleRectHeightWidthRatio() {
        return this.iconCircleRectHeightWidthRatio;
    }

    @NotNull
    public final Paint getIconCircleStrokePaint() {
        return this.iconCircleStrokePaint;
    }

    public final float getIconCircleWidth() {
        return this.iconCircleWidth;
    }

    public final int getIconHeight() {
        return this.iconHeight;
    }

    @Nullable
    public final ImageView getIconView() {
        return this.iconView;
    }

    public final int getIconWidth() {
        return this.iconWidth;
    }

    @NotNull
    public final Matrix getRemoteAnimMatrix() {
        return this.remoteAnimMatrix;
    }

    public final int getScreenHeight() {
        return this.screenHeight;
    }

    @NotNull
    public final Point getScreenSizePoint() {
        return this.screenSizePoint;
    }

    public final int getScreenWidth() {
        return this.screenWidth;
    }

    @Nullable
    public final SyncRtSurfaceTransactionApplier getSynSurfaceTransactionApplier() {
        return this.synSurfaceTransactionApplier;
    }

    @NotNull
    public final SurfaceControl.Transaction getTransaction() {
        return this.transaction;
    }

    public final float getVirHeight() {
        return this.virHeight;
    }

    public final float getVirWidth() {
        return this.virWidth;
    }

    public final float getVirX() {
        return this.virX;
    }

    public final float getVirY() {
        return this.virY;
    }

    public final float getWindowTopLeftRadius() {
        return this.windowTopLeftRadius;
    }

    public final float getWindowTopRightRadius() {
        return this.windowTopRightRadius;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public final void initParentSurfaceControl(SurfaceControl surfaceControl) {
        this.transaction.show(surfaceControl);
        this.transaction.setPosition(surfaceControl, 0.0f, 0.0f);
    }

    public void initSynSurfaceTransactionApplier() {
        if (this.synSurfaceTransactionApplier != null || ViewUtils.INSTANCE.getViewRootViewByReflection(this) == null) {
            return;
        }
        this.synSurfaceTransactionApplier = SyncRtSurfaceTransactionApplierExtKt.newSyncRtSurfaceTransactionApplierByReflection(this);
    }

    public void initViews() {
        Object systemService = getContext().getSystemService("display");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.hardware.display.DisplayManager");
        ((DisplayManager) systemService).getDisplay(0).getRealSize(this.screenSizePoint);
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        ScaleShortcutImageView scaleShortcutImageView = new ScaleShortcutImageView(context, null, 0, 6, null);
        scaleShortcutImageView.setScaleType(ImageView.ScaleType.CENTER);
        this.iconView = scaleShortcutImageView;
        addView(scaleShortcutImageView);
        updateSizeForScreenSizeChange();
    }

    public final boolean isLeftShortcutAppInAnim() {
        String animAppPackageName = getAnimAppPackageName();
        if (animAppPackageName != null) {
            return MiuiShortcutManager.INSTANCE.isLeftShortcutApp(animAppPackageName);
        }
        return false;
    }

    public final boolean isRightShortcutAppInAnim() {
        String animAppPackageName = getAnimAppPackageName();
        if (animAppPackageName != null) {
            return MiuiShortcutManager.INSTANCE.isRightShortcutApp(animAppPackageName);
        }
        return false;
    }

    public final boolean isSameWindowRadius() {
        return this.windowTopRightRadius == this.windowTopLeftRadius;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        int iUpdateFrom = this.configuration.updateFrom(newConfig);
        boolean z = (iUpdateFrom & 128) != 0;
        if ((iUpdateFrom & AutoBottomSheetKt.ALIGN_SCREEN_END) == 0 && !z) {
            return;
        }
        checkSize();
        reset("smallWidthChanged|orientationChanged");
        MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
        if (miuiShortcutManager.isOccludedMovedShortcutLeft() || miuiShortcutManager.isOccludedMovedShortcutRight()) {
            updateCurrentShortcutIcon(miuiShortcutManager.isOccludedMovedShortcutLeft());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset("onDetachedFromWindow");
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        float f = this.iconCircleCenterX;
        float f2 = this.iconCircleWidth;
        float f3 = f - (f2 / 2.0f);
        float f4 = f + (f2 / 2.0f);
        float f5 = this.iconCircleCenterY;
        float f6 = this.iconCircleHeight;
        float f7 = f5 - (f6 / 2.0f);
        float f8 = f5 + (f6 / 2.0f);
        float f9 = 255;
        this.iconCircleStrokePaint.setAlpha((int) (this.iconCircleAlpha * 0.5f * f9));
        this.iconCirclePaint.setAlpha((int) (this.iconCircleAlpha * f9));
        if (isSameWindowRadius()) {
            drawWithRoundRect(f3, f7, f4, f8, canvas);
        } else {
            drawWithPath(f3, f7, f4, f8, canvas);
        }
    }

    public final void refreshView() {
        requestLayout();
        invalidate();
    }

    public final void reparentNullApply(SurfaceControl surfaceControl) {
        try {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            try {
                transaction.reparent(surfaceControl, null);
                transaction.apply();
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(transaction, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(transaction, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            LogUtils.INSTANCE.logE("BaseShortcutAnimView", "reparent null fail", e);
        }
    }

    public final void reset(String reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        LogUtils.logD$default(LogUtils.INSTANCE, "BaseShortcutAnimView", "reset, from=" + reason, null, 4, null);
        View blurContainerView = ShortcutWindowManager.INSTANCE.getBlurContainerView();
        if (blurContainerView != null) {
            blurContainerView.setScaleX(1.0f);
            blurContainerView.setScaleY(1.0f);
            blurContainerView.setAlpha(0.0f);
        }
        ImageView imageView = this.iconView;
        if (imageView != null) {
            imageView.setScaleX(1.0f);
            imageView.setScaleY(1.0f);
            imageView.setAlpha(1.0f);
        }
        setCurrentShortcutIcon(CurrentShortcutIcon.NONE);
        if (StringsKt.contains$default((CharSequence) reason, (CharSequence) "smallWidthChanged", false, 2, (Object) null)) {
            updateWindowRadius();
        }
    }

    public final void setCurrentShortcutIcon(@NotNull CurrentShortcutIcon value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.currentShortcutIcon = value;
        updateLayoutParams();
        updateIconInitCenterX();
    }

    public final void setFinishedCallback(@Nullable IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        this.finishedCallback = iRemoteAnimationFinishedCallback;
    }

    public final void setFolmeAnim(@Nullable IStateStyle iStateStyle) {
        this.folmeAnim = iStateStyle;
    }

    public final void setIconAlpha(float f) {
        ImageView imageView = this.iconView;
        if (imageView != null) {
            imageView.setAlpha(f);
        }
    }

    public final void setIconCircleActiveCenterX(float f) {
        this.iconCircleActiveCenterX = f;
    }

    public final void setIconCircleActiveCenterY(float f) {
        this.iconCircleActiveCenterY = f;
    }

    public final void setIconCircleActiveWidth(float f) {
        this.iconCircleActiveWidth = f;
    }

    public final void setIconCircleAlpha(float f) {
        this.iconCircleAlpha = f;
    }

    public final void setIconCircleCenterX(float f) {
        this.iconCircleCenterX = f;
    }

    public final void setIconCircleCenterY(float f) {
        this.iconCircleCenterY = f;
    }

    public final void setIconCircleHeight(float f) {
        this.iconCircleHeight = f;
    }

    public final void setIconCircleInitCenterX(float f) {
        this.iconCircleInitCenterX = f;
    }

    public final void setIconCircleInitCenterY(float f) {
        this.iconCircleInitCenterY = f;
    }

    public final void setIconCirclePaint(@NotNull Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "<set-?>");
        this.iconCirclePaint = paint;
    }

    public final void setIconCircleRadiusL(float f) {
        this.iconCircleRadiusL = f;
    }

    public final void setIconCircleRadiusR(float f) {
        this.iconCircleRadiusR = f;
    }

    public final void setIconCircleRectHeightWidthRatio(float f) {
        this.iconCircleRectHeightWidthRatio = f;
    }

    public final void setIconCircleStrokePaint(@NotNull Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "<set-?>");
        this.iconCircleStrokePaint = paint;
    }

    public final void setIconCircleWidth(float f) {
        this.iconCircleWidth = f;
    }

    public final void setIconDarkMode(boolean z) {
        ImageView imageView;
        this.isIconDarkMode = z;
        ViewUtils viewUtils = ViewUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        ColorStateList iconTintList = viewUtils.getIconTintList(context, z);
        if (iconTintList == null || (imageView = this.iconView) == null) {
            return;
        }
        imageView.setImageTintList(iconTintList);
    }

    public final void setIconHeight(int i) {
        this.iconHeight = i;
    }

    public final void setIconView(@Nullable ImageView imageView) {
        this.iconView = imageView;
    }

    public final void setIconWidth(int i) {
        this.iconWidth = i;
    }

    public final void setScreenHeight(int i) {
        this.screenHeight = i;
    }

    public final void setScreenSizePoint(@NotNull Point point) {
        Intrinsics.checkNotNullParameter(point, "<set-?>");
        this.screenSizePoint = point;
    }

    public final void setScreenWidth(int i) {
        this.screenWidth = i;
    }

    public final void setSurfaceControlCornerRadius(SurfaceControl.Transaction transaction, SurfaceControl leash) {
        Intrinsics.checkNotNullParameter(transaction, "transaction");
        Intrinsics.checkNotNullParameter(leash, "leash");
        if (isSameWindowRadius() || !TransactionExtKt.supportSetMiCornerRadii()) {
            TransactionExtKt.setCornerRadiusByReflection(transaction, leash, Math.min(this.windowTopLeftRadius, this.windowTopRightRadius));
            return;
        }
        float[] setMiCornerRadii = getSetMiCornerRadii();
        float f = this.windowTopLeftRadius;
        float f2 = this.windowTopRightRadius;
        fillRadii(setMiCornerRadii, f, f2, f2, f);
        TransactionExtKt.setMiCornerRadiusByReflection(transaction, leash, getSetMiCornerRadii());
    }

    public final void setSynSurfaceTransactionApplier(@Nullable SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier) {
        this.synSurfaceTransactionApplier = syncRtSurfaceTransactionApplier;
    }

    public final void setTransaction(@NotNull SurfaceControl.Transaction transaction) {
        Intrinsics.checkNotNullParameter(transaction, "<set-?>");
        this.transaction = transaction;
    }

    public final void setVirHeight(float f) {
        this.virHeight = f;
    }

    public final void setVirWidth(float f) {
        this.virWidth = f;
    }

    public final void setVirX(float f) {
        this.virX = f;
    }

    public final void setVirY(float f) {
        this.virY = f;
    }

    public final void setWindowTopLeftRadius(float f) {
        this.windowTopLeftRadius = f;
    }

    public final void setWindowTopRightRadius(float f) {
        this.windowTopRightRadius = f;
    }

    public final void updateCurrentShortcutIcon(boolean z) {
        setCurrentShortcutIcon(z ? CurrentShortcutIcon.LEFT : CurrentShortcutIcon.RIGHT);
    }

    public final void updateIconInitCenterX() {
        DeviceHelper deviceHelper = DeviceHelper.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        float shortcutHorizontalMargin = deviceHelper.getShortcutHorizontalMargin(context);
        this.iconCircleInitCenterX = this.currentShortcutIcon == CurrentShortcutIcon.LEFT ? shortcutHorizontalMargin + (this.iconWidth / 2.0f) : (this.screenWidth - shortcutHorizontalMargin) - (this.iconWidth / 2.0f);
    }

    public final void updateIconViewPositionAndScale(float f) {
        ImageView imageView = this.iconView;
        if (imageView != null) {
            imageView.setX(this.iconCircleCenterX - (this.iconWidth / 2.0f));
            imageView.setY(this.iconCircleCenterY - (this.iconHeight / 2.0f));
            imageView.setScaleX(f);
            imageView.setScaleY(f);
        }
    }

    public final void updateLayoutParams() {
        ImageView imageView = this.iconView;
        if (imageView != null) {
            CurrentShortcutIcon currentShortcutIcon = this.currentShortcutIcon;
            CurrentShortcutIcon currentShortcutIcon2 = CurrentShortcutIcon.LEFT;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this.iconWidth, this.iconHeight, (currentShortcutIcon == currentShortcutIcon2 ? 8388611 : 8388613) | 80);
            DeviceHelper deviceHelper = DeviceHelper.INSTANCE;
            Context context = imageView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            int shortcutHorizontalMargin = deviceHelper.getShortcutHorizontalMargin(context);
            if (this.currentShortcutIcon == currentShortcutIcon2) {
                layoutParams.setMarginStart(shortcutHorizontalMargin);
            } else {
                layoutParams.setMarginEnd(shortcutHorizontalMargin);
            }
            imageView.setLayoutParams(layoutParams);
            imageView.setVisibility(this.currentShortcutIcon == CurrentShortcutIcon.NONE ? 4 : 0);
        }
    }

    public final void updateShortcutIconDrawable(Drawable drawable) {
        ImageView imageView = this.iconView;
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
    }

    public final void updateShortcutIconSize(int i, int i2) {
        this.iconWidth = i;
        this.iconHeight = i2;
        updateSizeForScreenSizeChange();
    }

    public void updateSizeForScreenSizeChange() {
        Point point = this.screenSizePoint;
        int i = point.y;
        this.screenHeight = i;
        int i2 = point.x;
        this.screenWidth = i2;
        LogUtils.logI$default(LogUtils.INSTANCE, "BaseShortcutAnimView", "updateSizeForScreenSizeChange, screenWidth=" + i2 + ", screenHeight=" + i, null, 4, null);
        this.iconCircleRectHeightWidthRatio = ((float) this.screenHeight) / ((float) this.screenWidth);
        updateIconInitCenterX();
        int i3 = this.screenHeight;
        this.iconCircleInitCenterY = ((float) i3) - (((float) this.iconHeight) / 2.0f);
        int i4 = this.screenWidth;
        this.iconCircleActiveCenterX = i4 * 0.5f;
        this.iconCircleActiveCenterY = i3 * 0.5f;
        this.iconCircleActiveWidth = i4 * 0.8f;
        updateLayoutParams();
    }

    public void updateViews(float f, float f2, float f3) {
        float f4;
        float f5;
        if (f > 1.0f) {
            float f6 = 1;
            f4 = f6 + ((f - f6) / 20);
        } else {
            f4 = f;
        }
        if (f > 1.0f) {
            float f7 = 1;
            f5 = f7 + ((f - f7) / 80);
        } else {
            f5 = f;
        }
        this.iconCircleActiveCenterY = this.screenHeight * Math.max(0.8f, (0.1f * f2) + 0.82f);
        AnimationUtils animationUtils = AnimationUtils.INSTANCE;
        this.virX = animationUtils.valFromPer(f5, this.iconCircleInitCenterX, this.iconCircleActiveCenterX);
        this.virY = animationUtils.valFromPer((float) Math.pow(f5, 3.0f), this.iconCircleInitCenterY, this.iconCircleActiveCenterY) + (f2 * 100 * f3);
        float fValFromPer = animationUtils.valFromPer(f4, 0.0f, this.iconCircleActiveWidth);
        this.virWidth = fValFromPer;
        float fValFromPer2 = fValFromPer * animationUtils.valFromPer(f3, 1.0f, this.iconCircleRectHeightWidthRatio);
        this.virHeight = fValFromPer2;
        this.iconCircleCenterX = this.virX;
        this.iconCircleCenterY = this.virY - (0.35f * fValFromPer2);
        this.iconCircleWidth = this.virWidth;
        this.iconCircleHeight = fValFromPer2;
        this.iconCircleAlpha = animationUtils.valFromPer(Math.min(f, 1.0f), 0.0f, 0.4f);
        float f8 = 2;
        float fValFromPer3 = animationUtils.valFromPer(f3, this.virWidth / f8, this.windowTopLeftRadius);
        this.iconCircleRadiusL = fValFromPer3;
        float f9 = this.windowTopLeftRadius;
        float f10 = this.windowTopRightRadius;
        if (f9 != f10) {
            fValFromPer3 = animationUtils.valFromPer(f3, this.virWidth / f8, f10);
        }
        this.iconCircleRadiusR = fValFromPer3;
        updateIconViewPositionAndScale$default(this, 0.0f, 1, null);
        invalidate();
    }

    public final void updateWindowRadius() {
        WindowUtils windowUtils = WindowUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.windowTopLeftRadius = windowUtils.getPhoneRadius(context, 0);
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        this.windowTopRightRadius = windowUtils.getPhoneRadius(context2, 1);
    }
}
