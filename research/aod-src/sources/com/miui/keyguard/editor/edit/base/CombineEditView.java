package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import androidx.core.view.ViewGroupKt;
import com.miui.keyguard.editor.CommonEditorActivity;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.EditorServiceManager;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.ExitAnimationPerformer;
import com.miui.keyguard.editor.base.TransitionKt;
import com.miui.keyguard.editor.base.TransitionLayer;
import com.miui.keyguard.editor.base.TransitionPerformer;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView;
import com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.track.TrackHelper;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.TemplateRadiusAnimationListener;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;
import miuix.core.util.MiuixUIUtils;
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
/* JADX INFO: compiled from: CombineEditView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class CombineEditView extends ViewGroup implements OnScrollStatusChanged, ExitAnimationPerformer {
    public boolean clickSelect;
    public boolean clickUnselect;
    public CombineEditView$combineEditListener$1 combineEditListener;
    public IStateStyle dialogAnim;
    public volatile boolean dialogInShow;
    public volatile float dialogProcess;
    public boolean dismissDialogWhenScreenChange;
    public CombineEditAdapter editAdapter;
    public WeakReference editFragmentWeak;
    public final EditViewHolder[] editHolders;
    public float editTemplateTop;
    public EditFragmentViewModel editViewModel;
    public boolean framesLoadCompleted;
    public boolean inAnim;
    public final float[] interpolatedRadii;
    public Job loadingJob;
    public long loadingTimeStamp;
    public boolean needDispatchChild;
    public int orientation;
    public boolean pendingExitPreview;
    public volatile int previewOffset;
    public Point screenSize;
    public int screenWidthDp;
    public Set scrollListeners;
    public volatile int selectOffset;
    public final RectF selectTemplateRectF;
    public CombineSpringTouchManager springTouchManager;
    public AtomicInteger templateReadyCount;
    public int templateStatus;
    public final RectF unselectTemplateRectF;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.CombineEditView$init$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CombineEditView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.CombineEditView$init$2", f = "CombineEditView.kt", l = {276}, m = "invokeSuspend")
    public static final class C01112 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01112(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CombineEditView.this.new C01112(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01112) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(400L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            CombineEditView.this.loadingTimeStamp = System.currentTimeMillis();
            BaseTemplateView lockTemplateView = CombineEditView.this.getLockTemplateView();
            if (lockTemplateView != null) {
                lockTemplateView.showLoading();
            }
            HomeTemplateView homeTemplateView = CombineEditView.this.getHomeTemplateView();
            if (homeTemplateView != null) {
                homeTemplateView.showLoading();
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r5v9, types: [com.miui.keyguard.editor.edit.base.CombineEditView$combineEditListener$1] */
    public CombineEditView(@NotNull final Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.orientation = context.getResources().getConfiguration().orientation;
        this.screenWidthDp = context.getResources().getConfiguration().screenWidthDp;
        this.scrollListeners = new LinkedHashSet();
        this.templateReadyCount = new AtomicInteger(0);
        this.combineEditListener = new CombineEditListener() { // from class: com.miui.keyguard.editor.edit.base.CombineEditView$combineEditListener$1
            @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
            public int getCurrentSelect() {
                return this.this$0.getSelectIndexByOffset();
            }

            @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
            public int getDialogOffset() {
                return this.this$0.getDialogOffset();
            }

            @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
            public void onDismissBottomSheet() {
                this.this$0.animToDialog(false);
            }

            @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
            public void onDismissBottomSheetForScreenChange(Configuration newConfig) {
                Intrinsics.checkNotNullParameter(newConfig, "newConfig");
                int i = newConfig.orientation;
                int i2 = newConfig.screenWidthDp;
                Log.d("CombineEditView", "onDismissBottomSheetForScreenChange, orientation: " + this.this$0.orientation + ", newOrientation: " + i + ", screenWidthDp: " + this.this$0.screenWidthDp + ", newScreenWidthDp=" + i2 + ", templateStatus: " + this.this$0.getTemplateStatus());
                if (this.this$0.orientation != i || this.this$0.screenWidthDp != i2) {
                    CombineEditView combineEditView = this.this$0;
                    combineEditView.dismissDialogWhenScreenChange = combineEditView.getTemplateStatus() == 2;
                    this.this$0.cancelDialogAnimIfRunning();
                }
                this.this$0.orientation = i;
                this.this$0.screenWidthDp = i2;
            }

            @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
            public void onDismissKeyboard() {
                if (this.this$0.dismissDialogWhenScreenChange) {
                    Log.d("CombineEditView", "resetAnimationStatus for dismiss Keyboard");
                    this.this$0.resetAnimationStatus(false);
                }
            }

            @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
            public void onPreviewChanged(boolean z, boolean z2) {
                CombineEditListener.DefaultImpls.onPreviewChanged(this, z, z2);
            }

            @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
            public void onScrolledChanged(boolean z, boolean z2) {
                CombineEditListener.DefaultImpls.onScrolledChanged(this, z, z2);
            }

            @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
            public void onShowBottomSheet() {
                this.this$0.animToDialog(true);
            }

            @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
            public void onTemplateReady(int i) {
                Log.d("CombineEditView", "onTemplateReady: templateStatus=" + this.this$0.getTemplateStatus() + " count=" + this.this$0.templateReadyCount.get() + " id=" + i);
                BaseTemplateView lockTemplateView = this.this$0.getLockTemplateView();
                if (lockTemplateView != null) {
                    lockTemplateView.waitTemplateInit();
                }
                HomeTemplateView homeTemplateView = this.this$0.getHomeTemplateView();
                if (homeTemplateView != null) {
                    homeTemplateView.waitTemplateInit();
                }
                if (this.this$0.getTemplateStatus() == -1 && this.this$0.templateReadyCount.incrementAndGet() == 4) {
                    Job job = this.this$0.loadingJob;
                    if (job != null) {
                        Job.DefaultImpls.cancel$default(job, null, 1, null);
                    }
                    BuildersKt__Builders_commonKt.launch$default(EditFragmentViewModel.Companion.getCoroutineScope(context), null, null, new CombineEditView$combineEditListener$1$onTemplateReady$1(this.this$0, context, null), 3, null);
                }
            }
        };
        this.selectTemplateRectF = new RectF();
        this.unselectTemplateRectF = new RectF();
        this.interpolatedRadii = new float[8];
        EditViewHolder[] editViewHolderArr = new EditViewHolder[2];
        for (int i = 0; i < 2; i++) {
            editViewHolderArr[i] = null;
        }
        this.editHolders = editViewHolderArr;
        this.screenSize = new Point();
        setBackgroundColor(0);
    }

    public /* synthetic */ CombineEditView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static final Unit animSwitchPreview$lambda$18(BaseTemplateView baseTemplateView, float f, float f2, int i, BaseTemplateView baseTemplateView2, int i2, int i3, Ref.FloatRef floatRef, View view, CombineEditView combineEditView, int i4, RoundOutlineProvider roundOutlineProvider, float[] fArr, RoundOutlineProvider roundOutlineProvider2) {
        float f3 = (i * f2) + f;
        baseTemplateView.setScaleX(f3);
        baseTemplateView.setScaleY(f3);
        baseTemplateView2.setScaleX(f3);
        baseTemplateView2.setScaleY(f3);
        int i5 = (int) ((i2 * i3) - floatRef.element);
        baseTemplateView2.offsetLeftAndRight(i5);
        if (view != null) {
            view.offsetLeftAndRight(i5);
        }
        combineEditView.updateTemplateRect();
        combineEditView.previewOffset = i4 == 0 ? 0 : i2;
        combineEditView.templateStatus = i4;
        combineEditView.inAnim = false;
        combineEditView.requestLayout();
        roundOutlineProvider.setRadii(fArr);
        roundOutlineProvider2.setRadii(fArr);
        baseTemplateView.invalidateOutline();
        baseTemplateView2.invalidateOutline();
        int i6 = combineEditView.templateStatus;
        CombineSpringTouchManager combineSpringTouchManager = combineEditView.springTouchManager;
        Integer numValueOf = combineSpringTouchManager != null ? Integer.valueOf(combineSpringTouchManager.getSelectViewPosition()) : null;
        Log.d("CombineEditView", "animSwitchPreview: templateStatus=" + i6 + " select=" + numValueOf + " direction=" + i3 + " scaleDiff=" + f2 + " scaleDirection=" + i + " xBase=" + floatRef.element + " xDiff=" + i2 + " scaleBase=" + f + " endStatus=" + i4 + " previewOffset=" + combineEditView.previewOffset + " pendingExitPreview=" + combineEditView.pendingExitPreview);
        if (combineEditView.pendingExitPreview) {
            combineEditView.pendingExitPreview = false;
            combineEditView.animSwitchPreview();
        }
        return Unit.INSTANCE;
    }

    public static final Unit animToDialog$lambda$15(CombineEditView combineEditView, float f, int i, BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, int i2, boolean z) {
        if (!z) {
            combineEditView.dialogProcess = f;
            combineEditView.templateStatus = i;
            baseTemplateView.setAlpha(RangesKt.coerceAtMost(RangesKt.coerceAtLeast(1 - combineEditView.dialogProcess, 0.0f), 1.0f));
            baseTemplateView.setVisibility(baseTemplateView.getAlpha() == 1.0f ? 0 : 8);
            baseTemplateView2.setTranslationY(i2 * combineEditView.dialogProcess);
            combineEditView.dialogAnim = null;
            combineEditView.inAnim = false;
            combineEditView.requestLayout();
        }
        return Unit.INSTANCE;
    }

    private final int getSelectOffsetInit() {
        int selectViewPosition;
        int i = this.screenSize.x;
        CombineEditAdapter combineEditAdapter = this.editAdapter;
        Intrinsics.checkNotNull(combineEditAdapter);
        int templateMarginOffset = i - combineEditAdapter.getTemplateMarginOffset();
        if (ViewUtil.INSTANCE.isRtl(this)) {
            CombineSpringTouchManager combineSpringTouchManager = this.springTouchManager;
            Intrinsics.checkNotNull(combineSpringTouchManager);
            selectViewPosition = combineSpringTouchManager.getSelectViewPosition();
        } else {
            CombineSpringTouchManager combineSpringTouchManager2 = this.springTouchManager;
            Intrinsics.checkNotNull(combineSpringTouchManager2);
            selectViewPosition = -combineSpringTouchManager2.getSelectViewPosition();
        }
        return templateMarginOffset * selectViewPosition;
    }

    public static final void init$lambda$3$lambda$2(CombineEditView combineEditView, boolean z) {
        BaseTemplateView lockTemplateView = combineEditView.getLockTemplateView();
        if (lockTemplateView != null) {
            lockTemplateView.onWallpaperScaleChanged(z);
        }
        HomeTemplateView homeTemplateView = combineEditView.getHomeTemplateView();
        if (homeTemplateView != null) {
            homeTemplateView.onWallpaperScaleChanged(z);
        }
    }

    public static final void loadTemplate$lambda$1$lambda$0(CombineEditView combineEditView) {
        if (combineEditView.templateStatus != -1) {
            combineEditView.performForwardTransition();
        }
        CombineSpringTouchManager combineSpringTouchManager = combineEditView.springTouchManager;
        float f = (combineSpringTouchManager == null || combineSpringTouchManager.getSelectViewPosition() != 0) ? 0.0f : 1.0f;
        BaseTemplateView lockTemplateView = combineEditView.getLockTemplateView();
        if (lockTemplateView != null) {
            lockTemplateView.onShowButtonBar(f);
        }
        HomeTemplateView homeTemplateView = combineEditView.getHomeTemplateView();
        if (homeTemplateView != null) {
            homeTemplateView.onShowButtonBar(1 - f);
        }
        combineEditView.updateTemplateRect();
        combineEditView.updateEditTemplateTop();
    }

    public static final void onConfigurationChanged$lambda$9(CombineEditView combineEditView) {
        BaseTemplateView itemView;
        CombineEditSnapHelper snapHelper;
        combineEditView.updateTemplateRect();
        combineEditView.updateEditTemplateTop();
        combineEditView.updateLoadingView();
        CombineSpringTouchManager combineSpringTouchManager = combineEditView.springTouchManager;
        if (combineSpringTouchManager != null && (snapHelper = combineSpringTouchManager.getSnapHelper()) != null) {
            snapHelper.snapToTargetExistingView();
        }
        if (combineEditView.templateStatus == 1) {
            for (EditViewHolder editViewHolder : combineEditView.editHolders) {
                if (editViewHolder != null && (itemView = editViewHolder.getItemView()) != null) {
                    ViewOutlineProvider outlineProvider = itemView.getOutlineProvider();
                    RoundOutlineProvider roundOutlineProvider = outlineProvider instanceof RoundOutlineProvider ? (RoundOutlineProvider) outlineProvider : null;
                    if (roundOutlineProvider != null) {
                        roundOutlineProvider.setRadii(DeviceUtil.INSTANCE.screenRadii(itemView));
                        itemView.invalidateOutline();
                    }
                }
            }
        }
        Log.d("CombineEditView", "onConfigurationChanged: isRtl=" + ViewUtil.INSTANCE.isRtl(combineEditView) + " screenSize=" + combineEditView.screenSize + " selectOffset=" + combineEditView.selectOffset + " previewOffset=" + combineEditView.previewOffset + " templateStatus=" + combineEditView.templateStatus);
    }

    public final void addOnScrollStatusListener(OnScrollStatusChanged l) {
        Intrinsics.checkNotNullParameter(l, "l");
        this.scrollListeners.add(l);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x006b A[PHI: r0 r5
  0x006b: PHI (r0v33 com.miui.keyguard.editor.edit.base.BaseTemplateView) = 
  (r0v11 com.miui.keyguard.editor.edit.base.BaseTemplateView)
  (r0v37 com.miui.keyguard.editor.edit.base.BaseTemplateView)
 binds: [B:33:0x00a7, B:21:0x0068] A[DONT_GENERATE, DONT_INLINE]
  0x006b: PHI (r5v12 com.miui.keyguard.editor.edit.base.BaseTemplateView) = (r5v2 com.miui.keyguard.editor.edit.base.BaseTemplateView), (r5v15 com.miui.keyguard.editor.edit.base.BaseTemplateView) binds: [B:33:0x00a7, B:21:0x0068] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:27:0x007d A[PHI: r0 r5
  0x007d: PHI (r0v15 com.miui.keyguard.editor.edit.base.BaseTemplateView) = 
  (r0v11 com.miui.keyguard.editor.edit.base.BaseTemplateView)
  (r0v37 com.miui.keyguard.editor.edit.base.BaseTemplateView)
 binds: [B:36:0x00b0, B:26:0x007b] A[DONT_GENERATE, DONT_INLINE]
  0x007d: PHI (r5v6 com.miui.keyguard.editor.edit.base.BaseTemplateView) = (r5v2 com.miui.keyguard.editor.edit.base.BaseTemplateView), (r5v15 com.miui.keyguard.editor.edit.base.BaseTemplateView) binds: [B:36:0x00b0, B:26:0x007b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:28:0x007f A[PHI: r0 r5
  0x007f: PHI (r0v12 com.miui.keyguard.editor.edit.base.BaseTemplateView) = 
  (r0v11 com.miui.keyguard.editor.edit.base.BaseTemplateView)
  (r0v37 com.miui.keyguard.editor.edit.base.BaseTemplateView)
 binds: [B:36:0x00b0, B:26:0x007b] A[DONT_GENERATE, DONT_INLINE]
  0x007f: PHI (r5v3 com.miui.keyguard.editor.edit.base.BaseTemplateView) = (r5v2 com.miui.keyguard.editor.edit.base.BaseTemplateView), (r5v15 com.miui.keyguard.editor.edit.base.BaseTemplateView) binds: [B:36:0x00b0, B:26:0x007b] A[DONT_GENERATE, DONT_INLINE]] */
    public final void animSwitchPreview() {
        BaseTemplateView itemView;
        BaseTemplateView itemView2;
        final int i;
        BaseTemplateView baseTemplateView;
        BaseTemplateView baseTemplateView2;
        final int i2;
        boolean z;
        float dimension;
        RoundOutlineProvider roundOutlineProvider;
        final RoundOutlineProvider roundOutlineProvider2;
        CombineSpringTouchManager combineSpringTouchManager = this.springTouchManager;
        if (combineSpringTouchManager == null || combineSpringTouchManager.getMScrollState() != 0 || !getFramesLoadCompleted() || this.inAnim) {
            return;
        }
        int i3 = this.templateStatus;
        if (i3 == 1 || i3 == 0) {
            this.inAnim = true;
            CombineEditAdapter combineEditAdapter = this.editAdapter;
            Intrinsics.checkNotNull(combineEditAdapter);
            final float templateScale = 1.0f - combineEditAdapter.getTemplateScale();
            final Ref.FloatRef floatRef = new Ref.FloatRef();
            CombineEditAdapter combineEditAdapter2 = this.editAdapter;
            Intrinsics.checkNotNull(combineEditAdapter2);
            final int templateMarginOffset = combineEditAdapter2.getTemplateMarginOffset();
            CombineSpringTouchManager combineSpringTouchManager2 = this.springTouchManager;
            int i4 = -1;
            if (combineSpringTouchManager2 == null || combineSpringTouchManager2.getSelectViewPosition() != 0) {
                EditViewHolder editViewHolder = this.editHolders[1];
                Intrinsics.checkNotNull(editViewHolder);
                itemView = editViewHolder.getItemView();
                EditViewHolder editViewHolder2 = this.editHolders[0];
                Intrinsics.checkNotNull(editViewHolder2);
                itemView2 = editViewHolder2.getItemView();
                if (this.templateStatus == 0) {
                    if (ViewUtil.INSTANCE.isRtl(this)) {
                        i4 = 1;
                    }
                    BaseTemplateView baseTemplateView3 = itemView2;
                    baseTemplateView = itemView;
                    baseTemplateView2 = baseTemplateView3;
                    i = i4;
                    z = false;
                    i4 = 1;
                    i2 = 1;
                } else {
                    if (ViewUtil.INSTANCE.isRtl(this)) {
                        i = -1;
                    } else {
                        i = 1;
                    }
                    BaseTemplateView baseTemplateView4 = itemView2;
                    baseTemplateView = itemView;
                    baseTemplateView2 = baseTemplateView4;
                    i2 = 0;
                    z = true;
                }
            } else {
                EditViewHolder editViewHolder3 = this.editHolders[0];
                Intrinsics.checkNotNull(editViewHolder3);
                itemView = editViewHolder3.getItemView();
                EditViewHolder editViewHolder4 = this.editHolders[1];
                Intrinsics.checkNotNull(editViewHolder4);
                itemView2 = editViewHolder4.getItemView();
                if (this.templateStatus == 0) {
                    if (!ViewUtil.INSTANCE.isRtl(this)) {
                        i4 = 1;
                    }
                    BaseTemplateView baseTemplateView5 = itemView2;
                    baseTemplateView = itemView;
                    baseTemplateView2 = baseTemplateView5;
                    i = i4;
                    z = false;
                    i4 = 1;
                    i2 = 1;
                } else {
                    if (ViewUtil.INSTANCE.isRtl(this)) {
                        i = 1;
                    } else {
                        i = -1;
                    }
                    BaseTemplateView baseTemplateView6 = itemView2;
                    baseTemplateView = itemView;
                    baseTemplateView2 = baseTemplateView6;
                    i2 = 0;
                    z = true;
                }
            }
            final float scaleX = baseTemplateView.getScaleX();
            TemplateButtonBar primaryButtonBar = baseTemplateView2.getPrimaryButtonBar();
            FrameLayout root = primaryButtonBar != null ? primaryButtonBar.getRoot() : null;
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            float[] fArrScreenRadii = deviceUtil.screenRadii(baseTemplateView);
            if (deviceUtil.isQ18()) {
                dimension = getResources().getDimension(R.dimen.kg_template_view_radius);
            } else {
                ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
                Context context = baseTemplateView.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                dimension = resourcesProvider.providePreviewRadius(context);
            }
            float[] fArr = new float[8];
            final int i5 = i4;
            for (int i6 = 0; i6 < 8; i6++) {
                fArr[i6] = dimension;
            }
            Pair pair = this.templateStatus == 0 ? TuplesKt.to(fArr, fArrScreenRadii) : TuplesKt.to(fArrScreenRadii, fArr);
            final float[] fArr2 = (float[]) pair.component1();
            final float[] fArr3 = (float[]) pair.component2();
            if (baseTemplateView.getOutlineProvider() instanceof RoundOutlineProvider) {
                ViewOutlineProvider outlineProvider = baseTemplateView.getOutlineProvider();
                Intrinsics.checkNotNull(outlineProvider, "null cannot be cast to non-null type com.miui.keyguard.editor.view.RoundOutlineProvider");
                roundOutlineProvider = (RoundOutlineProvider) outlineProvider;
            } else {
                roundOutlineProvider = new RoundOutlineProvider(fArr2);
                baseTemplateView.setOutlineProvider(roundOutlineProvider);
            }
            if (baseTemplateView2.getOutlineProvider() instanceof RoundOutlineProvider) {
                ViewOutlineProvider outlineProvider2 = baseTemplateView2.getOutlineProvider();
                Intrinsics.checkNotNull(outlineProvider2, "null cannot be cast to non-null type com.miui.keyguard.editor.view.RoundOutlineProvider");
                roundOutlineProvider2 = (RoundOutlineProvider) outlineProvider2;
            } else {
                roundOutlineProvider2 = new RoundOutlineProvider(fArr2);
                baseTemplateView2.setOutlineProvider(roundOutlineProvider2);
            }
            baseTemplateView.setClipToOutline(true);
            baseTemplateView2.setClipToOutline(true);
            if (i2 == 1) {
                BaseTemplateView lockTemplateView = getLockTemplateView();
                if (lockTemplateView != null) {
                    lockTemplateView.clearEditeStatus();
                }
                TrackHelper trackHelper = TrackHelper.INSTANCE;
                Context context2 = getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                CombineSpringTouchManager combineSpringTouchManager3 = this.springTouchManager;
                int selectViewPosition = combineSpringTouchManager3 != null ? combineSpringTouchManager3.getSelectViewPosition() : 0;
                BaseTemplateView lockTemplateView2 = getLockTemplateView();
                trackHelper.trackEditorEnterPreview(context2, selectViewPosition, lockTemplateView2 != null ? lockTemplateView2.getTemplateId() : null, EditorActivity.Companion.getTackId());
            } else {
                roundOutlineProvider = roundOutlineProvider;
            }
            final BaseTemplateView baseTemplateView7 = baseTemplateView;
            final FrameLayout frameLayout = root;
            final BaseTemplateView baseTemplateView8 = baseTemplateView2;
            final RoundOutlineProvider roundOutlineProvider3 = roundOutlineProvider;
            final Function0 function0 = new Function0() { // from class: com.miui.keyguard.editor.edit.base.CombineEditView$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return CombineEditView.animSwitchPreview$lambda$18(baseTemplateView7, scaleX, templateScale, i5, baseTemplateView8, templateMarginOffset, i, floatRef, frameLayout, this, i2, roundOutlineProvider3, fArr3, roundOutlineProvider2);
                }
            };
            final int i7 = i;
            Object context3 = getContext();
            CombineEditListener combineEditListener = context3 instanceof CombineEditListener ? (CombineEditListener) context3 : null;
            if (combineEditListener != null) {
                combineEditListener.onPreviewChanged(i2 == 1, baseTemplateView7.isDarkWallpaper());
            }
            CombineEditListener.DefaultImpls.onPreviewChanged$default(baseTemplateView7, i2 == 1, false, 2, null);
            TemplateButtonBar primaryButtonBar2 = baseTemplateView7.getPrimaryButtonBar();
            if (primaryButtonBar2 != null) {
                FunctionButtonBar.switchButton$default(primaryButtonBar2, z, false, null, 6, null);
            }
            CombineEditListener.DefaultImpls.onPreviewChanged$default(baseTemplateView8, i2 == 1, false, 2, null);
            baseTemplateView8.onShowButtonBar(0.0f);
            TemplateButtonBar primaryButtonBar3 = baseTemplateView8.getPrimaryButtonBar();
            if (primaryButtonBar3 != null) {
                FunctionButtonBar.switchButton$default(primaryButtonBar3, z, false, null, 4, null);
            }
            Folme.useValue(new Object[0]).setTo("process", Float.valueOf(0.0f)).to("process", Float.valueOf(1.0f), TransitionKt.getAnimConfig(1.0f, 0.28f, new TransitionListener() { // from class: com.miui.keyguard.editor.edit.base.CombineEditView.animSwitchPreview.3
                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    super.onCancel(obj);
                    function0.invoke();
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    super.onComplete(obj);
                    function0.invoke();
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object obj, Collection collection) {
                    float fCoerceAtMost = RangesKt.coerceAtMost(RangesKt.coerceAtLeast(UpdateInfo.findByName(collection, "process").getFloatValue(), 0.0f), 1.0f);
                    float f = templateScale * fCoerceAtMost * i5;
                    float f2 = templateMarginOffset * fCoerceAtMost * i7;
                    baseTemplateView7.setScaleX(scaleX + f);
                    baseTemplateView7.setScaleY(scaleX + f);
                    baseTemplateView8.setScaleX(scaleX + f);
                    baseTemplateView8.setScaleY(scaleX + f);
                    int iRoundToInt = MathKt.roundToInt(f2 - floatRef.element);
                    baseTemplateView8.offsetLeftAndRight(iRoundToInt);
                    View view = frameLayout;
                    if (view != null) {
                        view.offsetLeftAndRight(iRoundToInt);
                    }
                    floatRef.element += iRoundToInt;
                    float[] fArrInterpolateFloatArray = this.interpolateFloatArray(fArr2, fArr3, fCoerceAtMost);
                    roundOutlineProvider3.setRadii(fArrInterpolateFloatArray);
                    roundOutlineProvider2.setRadii(fArrInterpolateFloatArray);
                    baseTemplateView7.invalidateOutline();
                    baseTemplateView8.invalidateOutline();
                }
            }));
        }
    }

    public final void animToDialog(final boolean z) {
        BaseTemplateView homeTemplateView;
        BaseTemplateView lockTemplateView;
        float f;
        final float f2;
        Log.d("CombineEditView", "animToDialog: showDialog=" + z + ", templateStatus=" + this.templateStatus + " (EDIT=0, DIALOG=2)");
        int i = this.templateStatus;
        final int i2 = 2;
        if (!(i == 0 && z) && (i != 2 || z)) {
            Log.w("CombineEditView", "animToDialog: BLOCKED by templateStatus guard, templateStatus=" + i + ", showDialog=" + z);
            return;
        }
        if (!z && this.dismissDialogWhenScreenChange) {
            Log.d("CombineEditView", "animToDialog: cancel for screen changed");
            resetAnimationStatus(z);
            requestLayout();
            return;
        }
        this.inAnim = true;
        this.dialogInShow = z;
        final int dialogOffset = getDialogOffset();
        if (selectLockByOffset()) {
            homeTemplateView = getLockTemplateView();
            Intrinsics.checkNotNull(homeTemplateView);
            lockTemplateView = getHomeTemplateView();
            Intrinsics.checkNotNull(lockTemplateView);
        } else {
            homeTemplateView = getHomeTemplateView();
            Intrinsics.checkNotNull(homeTemplateView);
            lockTemplateView = getLockTemplateView();
            Intrinsics.checkNotNull(lockTemplateView);
        }
        final BaseTemplateView baseTemplateView = lockTemplateView;
        final BaseTemplateView baseTemplateView2 = homeTemplateView;
        IStateStyle iStateStyle = this.dialogAnim;
        if (iStateStyle != null) {
            Folme.clean(iStateStyle);
        }
        if (z) {
            f = this.dialogProcess;
            f2 = 1.0f;
        } else {
            f = this.dialogProcess;
            baseTemplateView.setVisibility(0);
            f2 = 0.0f;
            i2 = 0;
        }
        float f3 = f;
        final Function1 function1 = new Function1() { // from class: com.miui.keyguard.editor.edit.base.CombineEditView$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return CombineEditView.animToDialog$lambda$15(this.f$0, f2, i2, baseTemplateView, baseTemplateView2, dialogOffset, ((Boolean) obj).booleanValue());
            }
        };
        Log.d("CombineEditView", "animToDialog: startProcess=" + f3 + " endProcess=" + f2 + " endStatus=" + i2 + " dialogOffset=" + dialogOffset + " templateStatus=" + this.templateStatus + " dialogOffset=" + dialogOffset);
        IStateStyle to = Folme.useValue(new Object[0]).setTo("process", Float.valueOf(f3));
        Float fValueOf = Float.valueOf(f2);
        final int i3 = i2;
        this.dialogAnim = to.to("process", fValueOf, TransitionKt.getAnimConfig(1.0f, 0.28f, new TransitionListener() { // from class: com.miui.keyguard.editor.edit.base.CombineEditView.animToDialog.2
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                CombineEditView.this.setTemplateStatus(i3);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                function1.invoke(Boolean.valueOf(CombineEditView.this.dialogInShow != z));
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                if (CombineEditView.this.dialogInShow == z) {
                    CombineEditView.this.requestLayout();
                    CombineEditView.this.dialogProcess = RangesKt.coerceAtMost(RangesKt.coerceAtLeast(UpdateInfo.findByName(collection, "process").getFloatValue(), 0.0f), 1.0f);
                    float f4 = CombineEditView.this.dialogProcess * dialogOffset;
                    float fCoerceAtMost = RangesKt.coerceAtMost(RangesKt.coerceAtLeast(1 - CombineEditView.this.dialogProcess, 0.0f), 1.0f);
                    baseTemplateView2.setTranslationY(f4);
                    baseTemplateView.setAlpha(fCoerceAtMost);
                }
            }
        }));
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i) {
        if (i == -1) {
            EditViewHolder editViewHolder = this.editHolders[ViewUtil.INSTANCE.isRtl(this) ? 1 : 0];
            Intrinsics.checkNotNull(editViewHolder);
            return editViewHolder.getItemView().getLeft() < 0;
        }
        if (i != 1) {
            return true;
        }
        EditViewHolder editViewHolder2 = this.editHolders[!ViewUtil.INSTANCE.isRtl(this) ? 1 : 0];
        Intrinsics.checkNotNull(editViewHolder2);
        return editViewHolder2.getItemView().getRight() > this.screenSize.x;
    }

    public final void cancelDialogAnimIfRunning() {
        if (this.dialogAnim == null) {
            return;
        }
        Log.d("CombineEditView", "cancelDialogAnimIfRunning: dialogProcess=" + this.dialogProcess);
        Folme.clean(this.dialogAnim);
        BaseTemplateView lockTemplateView = getLockTemplateView();
        if (lockTemplateView != null) {
            lockTemplateView.setTranslationY(0.0f);
        }
        HomeTemplateView homeTemplateView = getHomeTemplateView();
        if (homeTemplateView != null) {
            homeTemplateView.setTranslationY(0.0f);
        }
        resetAnimationStatus(false);
    }

    /* JADX WARN: Code duplicated, block: B:29:0x006c  */
    /* JADX WARN: Code duplicated, block: B:54:0x00c8  */
    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int i;
        boolean z;
        boolean z2;
        BaseTemplateView itemView;
        TemplateButtonBar primaryButtonBar;
        boolean zOnTouchEvent;
        CombineSpringTouchManager combineSpringTouchManager;
        CombinedWallpaperView combinedWallpaperView;
        CombinedWallpaperView combinedWallpaperView2;
        CombineSpringTouchManager combineSpringTouchManager2;
        CombineSpringTouchManager combineSpringTouchManager3;
        CombineSpringTouchManager combineSpringTouchManager4;
        CombinedWallpaperView combinedWallpaperView3;
        BaseTemplateView itemView2;
        View clockLayer;
        if (this.inAnim || motionEvent == null || (i = this.templateStatus) == -1) {
            return true;
        }
        if (i == 2) {
            this.needDispatchChild = true;
            return super.dispatchTouchEvent(motionEvent);
        }
        if (motionEvent.getAction() == 0) {
            this.clickSelect = this.selectTemplateRectF.contains(motionEvent.getRawX(), motionEvent.getRawY());
            this.clickUnselect = this.unselectTemplateRectF.contains(motionEvent.getRawX(), motionEvent.getRawY());
        }
        EditViewHolder[] editViewHolderArr = this.editHolders;
        CombineSpringTouchManager combineSpringTouchManager5 = this.springTouchManager;
        Intrinsics.checkNotNull(combineSpringTouchManager5);
        EditViewHolder editViewHolder = editViewHolderArr[combineSpringTouchManager5.getSelectViewPosition()];
        if (editViewHolder == null || (itemView2 = editViewHolder.getItemView()) == null || (clockLayer = itemView2.getClockLayer()) == null) {
            z = false;
        } else {
            AllInOneTemplateView allInOneTemplateView = clockLayer instanceof AllInOneTemplateView ? (AllInOneTemplateView) clockLayer : null;
            if (allInOneTemplateView == null || !allInOneTemplateView.isDragClockMode()) {
                z = false;
            } else {
                z = true;
            }
        }
        CombineSpringTouchManager combineSpringTouchManager6 = this.springTouchManager;
        Intrinsics.checkNotNull(combineSpringTouchManager6);
        BaseTemplateView homeTemplateView = combineSpringTouchManager6.getSelectViewPosition() == 0 ? getHomeTemplateView() : getLockTemplateView();
        if (homeTemplateView != null && (combinedWallpaperView3 = (CombinedWallpaperView) homeTemplateView.getWallpaperLayer()) != null) {
            combinedWallpaperView3.setBlockSelfGestures(z);
        }
        CombineSpringTouchManager combineSpringTouchManager7 = this.springTouchManager;
        if (combineSpringTouchManager7 != null) {
            combineSpringTouchManager7.onTouchEventDispatch(motionEvent, z);
        }
        int i2 = this.templateStatus;
        if (i2 == 0 && this.clickSelect) {
            z2 = true;
        } else {
            if (i2 != 1) {
                EditViewHolder[] editViewHolderArr2 = this.editHolders;
                CombineSpringTouchManager combineSpringTouchManager8 = this.springTouchManager;
                Intrinsics.checkNotNull(combineSpringTouchManager8);
                EditViewHolder editViewHolder2 = editViewHolderArr2[combineSpringTouchManager8.getSelectViewPosition()];
                if (editViewHolder2 != null && (itemView = editViewHolder2.getItemView()) != null && (primaryButtonBar = itemView.getPrimaryButtonBar()) != null && primaryButtonBar.inButtonGroup(motionEvent.getX(), motionEvent.getY())) {
                    z2 = true;
                }
            }
            z2 = false;
        }
        this.needDispatchChild = z2;
        if (z2) {
            zOnTouchEvent = super.dispatchTouchEvent(motionEvent);
        } else {
            CombineSpringTouchManager combineSpringTouchManager9 = this.springTouchManager;
            if (combineSpringTouchManager9 == null || !combineSpringTouchManager9.isInterceptTouchEvent()) {
                zOnTouchEvent = true;
            } else {
                CombineSpringTouchManager combineSpringTouchManager10 = this.springTouchManager;
                Intrinsics.checkNotNull(combineSpringTouchManager10);
                zOnTouchEvent = combineSpringTouchManager10.onTouchEvent(motionEvent);
            }
        }
        if (z && this.clickSelect) {
            zOnTouchEvent = true;
        }
        if (!zOnTouchEvent && (((combineSpringTouchManager3 = this.springTouchManager) == null || combineSpringTouchManager3.getMScrollState() != 0) && (combineSpringTouchManager4 = this.springTouchManager) != null)) {
            combineSpringTouchManager4.updateScrollState(0);
        }
        if (1 == motionEvent.getAction() && (combineSpringTouchManager = this.springTouchManager) != null && combineSpringTouchManager.isValidTouch()) {
            CombineSpringTouchManager combineSpringTouchManager11 = this.springTouchManager;
            if (combineSpringTouchManager11 == null || combineSpringTouchManager11.isWallpaperScale() || ((!this.clickSelect || zOnTouchEvent) && (this.templateStatus != 1 || (combineSpringTouchManager2 = this.springTouchManager) == null || combineSpringTouchManager2.isEditTouchEvent()))) {
                CombineSpringTouchManager combineSpringTouchManager12 = this.springTouchManager;
                if (combineSpringTouchManager12 != null && !combineSpringTouchManager12.isEditTouchEvent() && this.clickUnselect && this.unselectTemplateRectF.contains(motionEvent.getRawX(), motionEvent.getRawY())) {
                    BaseTemplateView lockTemplateView = getLockTemplateView();
                    if (lockTemplateView != null && (combinedWallpaperView2 = (CombinedWallpaperView) lockTemplateView.getWallpaperLayer()) != null) {
                        combinedWallpaperView2.setBlockSelfGestures(false);
                    }
                    HomeTemplateView homeTemplateView2 = getHomeTemplateView();
                    if (homeTemplateView2 != null && (combinedWallpaperView = (CombinedWallpaperView) homeTemplateView2.getWallpaperLayer()) != null) {
                        combinedWallpaperView.setBlockSelfGestures(false);
                    }
                    CombineSpringTouchManager combineSpringTouchManager13 = this.springTouchManager;
                    if (combineSpringTouchManager13 != null) {
                        combineSpringTouchManager13.snapToSelectTemplate((combineSpringTouchManager13 == null || combineSpringTouchManager13.getSelectViewPosition() != 0) ? 0 : 1);
                    }
                }
            } else {
                animSwitchPreview();
            }
            this.clickSelect = false;
            this.clickUnselect = false;
            CombineSpringTouchManager combineSpringTouchManager14 = this.springTouchManager;
            if (combineSpringTouchManager14 != null) {
                combineSpringTouchManager14.resetTouchFlow();
            }
        }
        return zOnTouchEvent;
    }

    @NotNull
    public final EditViewHolder[] getAllEditViewHolder() {
        return this.editHolders;
    }

    @NotNull
    public final Point getCenter() {
        Point point = new Point();
        point.x = getLeft() + (getWidth() / 2);
        point.y = getTop() + (getHeight() / 2);
        return point;
    }

    public final float getCombineEditorViewY() {
        RectF combineScreen;
        CombineEditAdapter combineEditAdapter = this.editAdapter;
        if (combineEditAdapter == null || (combineScreen = combineEditAdapter.getCombineScreen()) == null) {
            return 0.0f;
        }
        return combineScreen.top;
    }

    public final int getDialogOffset() {
        return (int) (-(this.editTemplateTop - MiuixUIUtils.getStatusBarHeight(getContext())));
    }

    @Nullable
    public final WeakReference<EditFragment> getEditFragmentWeak() {
        return this.editFragmentWeak;
    }

    public final View getEditView(int i) {
        if (i >= 0 && i < 2) {
            EditViewHolder editViewHolder = this.editHolders[i];
            if (editViewHolder != null) {
                return editViewHolder.getItemView();
            }
            return null;
        }
        Log.e("CombineEditView", "getEditView: error viewType=" + i);
        return null;
    }

    public final boolean getFramesLoadCompleted() {
        return this.framesLoadCompleted;
    }

    @Nullable
    public final HomeTemplateView getHomeTemplateView() {
        View editView = getEditView(1);
        if (editView instanceof HomeTemplateView) {
            return (HomeTemplateView) editView;
        }
        return null;
    }

    public final int getHorizontalRealOffset(int i) {
        EditViewHolder editViewHolder;
        EditViewHolder editViewHolder2;
        byte b = i > 0 ? (byte) -1 : (byte) 1;
        BaseTemplateView itemView = null;
        if (b == -1) {
            boolean zIsRtl = ViewUtil.INSTANCE.isRtl(this);
            EditViewHolder[] editViewHolderArr = this.editHolders;
            if (!zIsRtl ? (editViewHolder = editViewHolderArr[0]) != null : (editViewHolder = editViewHolderArr[1]) != null) {
                itemView = editViewHolder.getItemView();
            }
            Intrinsics.checkNotNull(itemView);
            int left = 0 - itemView.getLeft();
            if (left <= i) {
                return left;
            }
        } else {
            if (b != 1) {
                return 0;
            }
            if (!ViewUtil.INSTANCE.isRtl(this) ? (editViewHolder2 = this.editHolders[1]) != null : (editViewHolder2 = this.editHolders[0]) != null) {
                itemView = editViewHolder2.getItemView();
            }
            int i2 = this.screenSize.x;
            Intrinsics.checkNotNull(itemView);
            int right = i2 - itemView.getRight();
            if (right > i) {
                return right;
            }
        }
        return i;
    }

    @Nullable
    public final BaseTemplateView getLockTemplateView() {
        View editView = getEditView(0);
        if (editView instanceof BaseTemplateView) {
            return (BaseTemplateView) editView;
        }
        return null;
    }

    public final int getOverOffset() {
        CombineEditAdapter combineEditAdapter = this.editAdapter;
        if (combineEditAdapter != null) {
            return combineEditAdapter.getTemplateMarginOffset();
        }
        return 0;
    }

    public final int getPreviewOffset() {
        return this.previewOffset;
    }

    @NotNull
    public final Point getScreenSize() {
        return this.screenSize;
    }

    @Nullable
    public final EditViewHolder getSelectEditViewHolder() {
        EditViewHolder[] editViewHolderArr = this.editHolders;
        if (editViewHolderArr.length != 2) {
            return null;
        }
        CombineSpringTouchManager combineSpringTouchManager = this.springTouchManager;
        return editViewHolderArr[combineSpringTouchManager != null ? combineSpringTouchManager.getSelectViewPosition() : 0];
    }

    public final int getSelectIndexByOffset() {
        return !selectLockByOffset() ? 1 : 0;
    }

    public final int getSelectOffset() {
        return this.selectOffset;
    }

    public final int getTemplateStatus() {
        return this.templateStatus;
    }

    public final void init(EditFragment editFragment, int i) {
        BaseTemplateView itemView;
        Intrinsics.checkNotNullParameter(editFragment, "editFragment");
        this.editFragmentWeak = new WeakReference(editFragment);
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(editFragment);
        this.editViewModel = viewModel;
        if (viewModel != null) {
            viewModel.setSelectTemplate(i);
        }
        this.screenSize = DeviceUtil.INSTANCE.getScreenSize(getContext());
        setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        CombineSpringTouchManager combineSpringTouchManager = new CombineSpringTouchManager(new WeakReference(this));
        combineSpringTouchManager.setSelectViewPosition(i);
        combineSpringTouchManager.setOnWallpaperScaleChangedListener(new CombineSpringTouchManager.WallpaperScaleChangedListener() { // from class: com.miui.keyguard.editor.edit.base.CombineEditView$$ExternalSyntheticLambda0
            @Override // com.miui.keyguard.editor.edit.base.CombineSpringTouchManager.WallpaperScaleChangedListener
            public final void onWallpaperScaleChanged(boolean z) {
                CombineEditView.init$lambda$3$lambda$2(this.f$0, z);
            }
        });
        this.springTouchManager = combineSpringTouchManager;
        WeakReference weakReference = this.editFragmentWeak;
        Intrinsics.checkNotNull(weakReference);
        CombineSpringTouchManager combineSpringTouchManager2 = this.springTouchManager;
        Intrinsics.checkNotNull(combineSpringTouchManager2);
        this.editAdapter = new CombineEditAdapter(new CombineEditParam(weakReference, combineSpringTouchManager2, this.screenSize, null, null, null, 56, null));
        this.selectOffset = getSelectOffsetInit();
        CombineEditAdapter combineEditAdapter = this.editAdapter;
        Intrinsics.checkNotNull(combineEditAdapter);
        for (int itemCount = combineEditAdapter.getItemCount() - 1; -1 < itemCount; itemCount--) {
            EditViewHolder[] editViewHolderArr = this.editHolders;
            CombineEditAdapter combineEditAdapter2 = this.editAdapter;
            Intrinsics.checkNotNull(combineEditAdapter2);
            CombineEditAdapter combineEditAdapter3 = this.editAdapter;
            Intrinsics.checkNotNull(combineEditAdapter3);
            editViewHolderArr[itemCount] = combineEditAdapter2.onCreateViewHolder(this, combineEditAdapter3.getItemViewType(itemCount));
            EditViewHolder editViewHolder = this.editHolders[itemCount];
            if (editViewHolder != null && (itemView = editViewHolder.getItemView()) != null) {
                itemView.setCombineAnimListener(this.combineEditListener);
            }
        }
        if (getContext() instanceof CommonEditorActivity) {
            this.templateStatus = -1;
            BaseTemplateView lockTemplateView = getLockTemplateView();
            if (lockTemplateView != null) {
                lockTemplateView.initLoading();
            }
            HomeTemplateView homeTemplateView = getHomeTemplateView();
            if (homeTemplateView != null) {
                homeTemplateView.initLoading();
            }
            Context context = getContext();
            EditorActivity editorActivity = context instanceof EditorActivity ? (EditorActivity) context : null;
            if (editorActivity != null) {
                editorActivity.setApplyButtonEnabled(false);
            }
            EditFragmentViewModel.Companion companion = EditFragmentViewModel.Companion;
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            this.loadingJob = BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context2), null, null, new C01112(null), 3, null);
        }
        addOnScrollStatusListener(this);
        setFocusableInTouchMode(false);
        setFocusable(false);
        setImportantForAccessibility(2);
    }

    public final float[] interpolateFloatArray(float[] fArr, float[] fArr2, float f) {
        int length = fArr.length;
        for (int i = 0; i < length; i++) {
            float[] fArr3 = this.interpolatedRadii;
            float f2 = fArr[i];
            fArr3[i] = f2 + ((fArr2[i] - f2) * f);
        }
        return this.interpolatedRadii;
    }

    public final boolean isAllInOneDragging() {
        View clockLayer;
        BaseTemplateView lockTemplateView = getLockTemplateView();
        if (lockTemplateView != null && (clockLayer = lockTemplateView.getClockLayer()) != null) {
            AllInOneTemplateView allInOneTemplateView = clockLayer instanceof AllInOneTemplateView ? (AllInOneTemplateView) clockLayer : null;
            if (allInOneTemplateView != null && allInOneTemplateView.isDragClockMode()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isInAnim() {
        return this.inAnim;
    }

    public final void loadTemplate(CommonConfig templateConfig, EditFragment.TemplateData templateData, long j) {
        BaseTemplateView itemView;
        CombineEditParam param;
        CombineEditParam param2;
        CombineEditParam param3;
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        Intrinsics.checkNotNullParameter(templateData, "templateData");
        CombineEditAdapter combineEditAdapter = this.editAdapter;
        if (combineEditAdapter != null && (param3 = combineEditAdapter.getParam()) != null) {
            param3.setTemplateConfig(templateConfig);
        }
        CombineEditAdapter combineEditAdapter2 = this.editAdapter;
        if (combineEditAdapter2 != null && (param2 = combineEditAdapter2.getParam()) != null) {
            param2.setTemplateData(templateData);
        }
        CombineEditAdapter combineEditAdapter3 = this.editAdapter;
        if (combineEditAdapter3 != null && (param = combineEditAdapter3.getParam()) != null) {
            param.setTemplateSource(Long.valueOf(j));
        }
        List listReversed = ArraysKt.reversed(this.editHolders);
        int size = listReversed.size();
        for (int i = 0; i < size; i++) {
            EditViewHolder editViewHolder = (EditViewHolder) listReversed.get(i);
            CombineEditAdapter combineEditAdapter4 = this.editAdapter;
            Intrinsics.checkNotNull(combineEditAdapter4);
            Intrinsics.checkNotNull(editViewHolder);
            combineEditAdapter4.onBindViewHolder(this, editViewHolder.getItemView(), editViewHolder.getViewType());
            if (this.templateStatus != -1) {
                editViewHolder.getItemView().setAlpha(0.0f);
            }
        }
        EditViewHolder[] editViewHolderArr = this.editHolders;
        CombineSpringTouchManager combineSpringTouchManager = this.springTouchManager;
        Intrinsics.checkNotNull(combineSpringTouchManager);
        EditViewHolder editViewHolder2 = editViewHolderArr[combineSpringTouchManager.getSelectViewPosition()];
        if (editViewHolder2 == null || (itemView = editViewHolder2.getItemView()) == null) {
            return;
        }
        itemView.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.CombineEditView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                CombineEditView.loadTemplate$lambda$1$lambda$0(this.f$0);
            }
        });
    }

    @Override // android.view.View
    public void offsetLeftAndRight(int i) {
        this.selectOffset += i;
        if (this.templateStatus == 0) {
            float f = this.screenSize.x;
            CombineEditAdapter combineEditAdapter = this.editAdapter;
            Intrinsics.checkNotNull(combineEditAdapter);
            float templateMarginOffset = f - combineEditAdapter.getTemplateMarginOffset();
            int i2 = this.screenSize.x;
            CombineEditAdapter combineEditAdapter2 = this.editAdapter;
            Intrinsics.checkNotNull(combineEditAdapter2);
            int templateMarginOffset2 = i2 - combineEditAdapter2.getTemplateMarginOffset();
            float f2 = (ViewUtil.INSTANCE.isRtl(this) ? templateMarginOffset2 - this.selectOffset : templateMarginOffset2 + this.selectOffset) / templateMarginOffset;
            BaseTemplateView lockTemplateView = getLockTemplateView();
            if (lockTemplateView != null) {
                lockTemplateView.onShowButtonBar(f2);
            }
            HomeTemplateView homeTemplateView = getHomeTemplateView();
            if (homeTemplateView != null) {
                homeTemplateView.onShowButtonBar(1 - f2);
            }
        }
        Iterator it = ViewGroupKt.getChildren(this).iterator();
        while (it.hasNext()) {
            ((View) it.next()).offsetLeftAndRight(i);
        }
    }

    public final boolean onApply(Consumer consumer, TemplateApplier.ApplyCallback applyCallback) {
        HomeTemplateView homeTemplateView = getHomeTemplateView();
        if (homeTemplateView != null) {
            homeTemplateView.onPrepareApply();
        }
        DeviceScreenshotHelper.INSTANCE.setCurrentHomeTemplateWeak(new WeakReference(getHomeTemplateView()));
        BaseTemplateView lockTemplateView = getLockTemplateView();
        if (lockTemplateView != null) {
            return lockTemplateView.onApply((Consumer<Object>) consumer, applyCallback);
        }
        return false;
    }

    public final void onCancel() {
        CombineEditSnapHelper snapHelper;
        CombineSpringTouchManager combineSpringTouchManager = this.springTouchManager;
        if (combineSpringTouchManager == null || (snapHelper = combineSpringTouchManager.getSnapHelper()) == null) {
            return;
        }
        snapHelper.stopFling();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        BaseTemplateView itemView;
        BaseTemplateView itemView2;
        BaseTemplateView itemView3;
        BaseTemplateView itemView4;
        super.onConfigurationChanged(configuration);
        Point screenSize = DeviceUtil.INSTANCE.getScreenSize(getContext());
        this.screenSize = screenSize;
        CombineEditAdapter combineEditAdapter = this.editAdapter;
        if (combineEditAdapter != null) {
            combineEditAdapter.onScreenSizeChanged(screenSize, this.editHolders, this.templateStatus);
        }
        this.selectOffset = getSelectOffsetInit();
        int templateMarginOffset = 0;
        if (this.templateStatus == 2) {
            CombineSpringTouchManager combineSpringTouchManager = this.springTouchManager;
            if (combineSpringTouchManager == null || combineSpringTouchManager.getSelectViewPosition() != 0) {
                EditViewHolder editViewHolder = this.editHolders[1];
                if (editViewHolder != null && (itemView2 = editViewHolder.getItemView()) != null) {
                    itemView2.setTranslationY(0.0f);
                }
                EditViewHolder editViewHolder2 = this.editHolders[0];
                if (editViewHolder2 != null && (itemView = editViewHolder2.getItemView()) != null) {
                    itemView.setAlpha(1.0f);
                }
                BaseTemplateView lockTemplateView = getLockTemplateView();
                if (lockTemplateView != null && lockTemplateView.getVisibility() != 0) {
                    lockTemplateView.setVisibility(0);
                }
            } else {
                EditViewHolder editViewHolder3 = this.editHolders[0];
                if (editViewHolder3 != null && (itemView4 = editViewHolder3.getItemView()) != null) {
                    itemView4.setTranslationY(0.0f);
                }
                EditViewHolder editViewHolder4 = this.editHolders[1];
                if (editViewHolder4 != null && (itemView3 = editViewHolder4.getItemView()) != null) {
                    itemView3.setAlpha(1.0f);
                }
                HomeTemplateView homeTemplateView = getHomeTemplateView();
                if (homeTemplateView != null && homeTemplateView.getVisibility() != 0) {
                    homeTemplateView.setVisibility(0);
                }
            }
            this.templateStatus = 0;
        }
        if (this.templateStatus == 1) {
            CombineEditAdapter combineEditAdapter2 = this.editAdapter;
            Intrinsics.checkNotNull(combineEditAdapter2);
            templateMarginOffset = combineEditAdapter2.getTemplateMarginOffset();
        }
        this.previewOffset = templateMarginOffset;
        post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.CombineEditView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                CombineEditView.onConfigurationChanged$lambda$9(this.f$0);
            }
        });
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        CombineSpringTouchManager combineSpringTouchManager = this.springTouchManager;
        boolean zIsInterceptTouchEvent = combineSpringTouchManager != null ? combineSpringTouchManager.isInterceptTouchEvent() : false;
        if (this.needDispatchChild) {
            if (!(zIsInterceptTouchEvent ? true : super.onInterceptTouchEvent(motionEvent))) {
                return false;
            }
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        FrameLayout root;
        int i6;
        FrameLayout root2;
        if (this.inAnim) {
            return;
        }
        CombineSpringTouchManager combineSpringTouchManager = this.springTouchManager;
        Intrinsics.checkNotNull(combineSpringTouchManager);
        boolean z2 = combineSpringTouchManager.getSelectViewPosition() == 0;
        BaseTemplateView lockTemplateView = getLockTemplateView();
        if (lockTemplateView != null) {
            if (z2) {
                i6 = 0;
            } else {
                i6 = ViewUtil.INSTANCE.isRtl(this) ? this.previewOffset : -this.previewOffset;
            }
            int i7 = this.selectOffset;
            ViewGroup.LayoutParams layoutParams = lockTemplateView.getLayoutParams();
            int marginStart = i7 + (layoutParams instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams).getMarginStart() : 0) + i6;
            int i8 = i + marginStart;
            int i9 = marginStart + i3;
            lockTemplateView.layout(i8, i2, i9, i4);
            TemplateButtonBar primaryButtonBar = lockTemplateView.getPrimaryButtonBar();
            if (primaryButtonBar != null && (root2 = primaryButtonBar.getRoot()) != null) {
                root2.layout(i8, i2, i9, i4);
            }
        }
        HomeTemplateView homeTemplateView = getHomeTemplateView();
        if (homeTemplateView != null) {
            if (z2) {
                i5 = ViewUtil.INSTANCE.isRtl(this) ? -this.previewOffset : this.previewOffset;
            } else {
                i5 = 0;
            }
            int i10 = this.selectOffset;
            ViewGroup.LayoutParams layoutParams2 = homeTemplateView.getLayoutParams();
            int marginStart2 = i10 + (layoutParams2 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams2).getMarginStart() : 0) + i5;
            boolean zIsRtl = ViewUtil.INSTANCE.isRtl(this);
            int i11 = this.screenSize.x;
            if (zIsRtl) {
                i11 = -i11;
            }
            int i12 = marginStart2 + i11;
            int i13 = i + i12;
            int i14 = i3 + i12;
            homeTemplateView.layout(i13, i2, i14, i4);
            TemplateButtonBar primaryButtonBar2 = homeTemplateView.getPrimaryButtonBar();
            if (primaryButtonBar2 == null || (root = primaryButtonBar2.getRoot()) == null) {
                return;
            }
            root.layout(i13, i2, i14, i4);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        Iterator it = ViewGroupKt.getChildren(this).iterator();
        while (it.hasNext()) {
            ((View) it.next()).measure(i, i2);
        }
    }

    public void onPageSelect(int i) {
        CombineEditListener combineEditListener;
        HomeTemplateView homeTemplateView = getHomeTemplateView();
        BaseTemplateView lockTemplateView = getLockTemplateView();
        if (i == 0) {
            if (homeTemplateView != null) {
                homeTemplateView.pauseFastPlayer();
            }
            if (lockTemplateView != null) {
                lockTemplateView.startFastPlayer();
            }
            if (lockTemplateView != null) {
                Object context = getContext();
                combineEditListener = context instanceof CombineEditListener ? (CombineEditListener) context : null;
                if (combineEditListener != null) {
                    combineEditListener.onScrolledChanged(this.templateStatus == 1, lockTemplateView.isDarkWallpaper());
                    return;
                }
                return;
            }
            return;
        }
        if (i != 1) {
            return;
        }
        if (homeTemplateView != null) {
            homeTemplateView.startFastPlayer();
        }
        if (lockTemplateView != null) {
            lockTemplateView.pauseFastPlayer();
        }
        if (homeTemplateView != null) {
            Object context2 = getContext();
            combineEditListener = context2 instanceof CombineEditListener ? (CombineEditListener) context2 : null;
            if (combineEditListener != null) {
                combineEditListener.onScrolledChanged(this.templateStatus == 1, homeTemplateView.isDarkWallpaper());
            }
        }
        if (lockTemplateView != null) {
            lockTemplateView.clearEditeStatus();
        }
    }

    public final void onReceiveActivityResult(int i, int i2, Intent intent) {
        if (WallpaperEditor.Companion.isLockPick()) {
            BaseTemplateView lockTemplateView = getLockTemplateView();
            if (lockTemplateView != null) {
                lockTemplateView.onReceiveActivityResult(i, i2, intent);
                return;
            }
            return;
        }
        HomeTemplateView homeTemplateView = getHomeTemplateView();
        if (homeTemplateView != null) {
            homeTemplateView.onReceiveActivityResult(i, i2, intent);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.OnScrollStatusChanged
    public void onScroll(int i) {
    }

    public final void onScrollStatusChanged(int i) {
        BaseTemplateView lockTemplateView;
        if (i == 0) {
            EditFragmentViewModel editFragmentViewModel = this.editViewModel;
            boolean z = false;
            if (editFragmentViewModel != null) {
                CombineSpringTouchManager combineSpringTouchManager = this.springTouchManager;
                editFragmentViewModel.setSelectTemplate(combineSpringTouchManager != null ? combineSpringTouchManager.getSelectViewPosition() : 0);
            }
            this.selectOffset = getSelectOffsetInit();
            if (this.templateStatus == 0) {
                CombineSpringTouchManager combineSpringTouchManager2 = this.springTouchManager;
                if (combineSpringTouchManager2 != null && combineSpringTouchManager2.getSelectViewPosition() == 0) {
                    z = true;
                }
                BaseTemplateView lockTemplateView2 = getLockTemplateView();
                if (lockTemplateView2 != null) {
                    lockTemplateView2.onShowButtonBar(z ? 1.0f : 0.0f);
                }
                HomeTemplateView homeTemplateView = getHomeTemplateView();
                if (homeTemplateView != null) {
                    homeTemplateView.onShowButtonBar(z ? 0.0f : 1.0f);
                }
            }
            Iterator it = this.scrollListeners.iterator();
            while (it.hasNext()) {
                ((OnScrollStatusChanged) it.next()).onScroll(i);
                CombineSpringTouchManager combineSpringTouchManager3 = this.springTouchManager;
                if (combineSpringTouchManager3 != null) {
                    onPageSelect(combineSpringTouchManager3.getSelectViewPosition());
                }
            }
        }
        if (i == 1 && (lockTemplateView = getLockTemplateView()) != null) {
            lockTemplateView.onShowTitleText();
        }
        Iterator it2 = this.scrollListeners.iterator();
        while (it2.hasNext()) {
            ((OnScrollStatusChanged) it2.next()).onScroll(i);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        CombineSpringTouchManager combineSpringTouchManager = this.springTouchManager;
        Intrinsics.checkNotNull(combineSpringTouchManager);
        if (!combineSpringTouchManager.isInterceptTouchEvent()) {
            return super.onTouchEvent(motionEvent);
        }
        CombineSpringTouchManager combineSpringTouchManager2 = this.springTouchManager;
        Intrinsics.checkNotNull(combineSpringTouchManager2);
        return combineSpringTouchManager2.onTouchEvent(motionEvent);
    }

    public final void performForwardTransition() {
        if (getContext() instanceof TransitionLayer) {
            Object context = getContext();
            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.miui.keyguard.editor.base.TransitionLayer");
            TransitionPerformer transitionPerformer = ((TransitionLayer) context).getTransitionPerformer();
            BaseTemplateView lockTemplateView = getLockTemplateView();
            Intrinsics.checkNotNull(lockTemplateView);
            HomeTemplateView homeTemplateView = getHomeTemplateView();
            Intrinsics.checkNotNull(homeTemplateView);
            transitionPerformer.performForwardTransition(lockTemplateView, homeTemplateView, getContext() instanceof CommonEditorActivity);
        }
    }

    public final void resetAnimationStatus(boolean z) {
        this.dismissDialogWhenScreenChange = false;
        this.dialogAnim = null;
        this.dialogInShow = z;
        this.dialogProcess = z ? 1.0f : 0.0f;
        this.inAnim = false;
    }

    public final boolean selectLockByOffset() {
        if (ViewUtil.INSTANCE.isRtl(this)) {
            return this.selectOffset < ((this.screenSize.x - getOverOffset()) + Math.abs(this.previewOffset)) / 2;
        }
        return this.selectOffset > (-((this.screenSize.x - getOverOffset()) + Math.abs(this.previewOffset))) / 2;
    }

    public final void setEditFragmentWeak(@Nullable WeakReference<EditFragment> weakReference) {
        this.editFragmentWeak = weakReference;
    }

    public final void setFramesLoadCompleted(boolean z) {
        this.framesLoadCompleted = z;
    }

    public final void setPendingExitPreview(boolean z) {
        this.pendingExitPreview = z;
    }

    public final void setPreviewOffset(int i) {
        this.previewOffset = i;
    }

    public final void setScreenSize(@NotNull Point point) {
        Intrinsics.checkNotNullParameter(point, "<set-?>");
        this.screenSize = point;
    }

    public final void setSelectOffset(int i) {
        this.selectOffset = i;
    }

    public final void setTemplateStatus(int i) {
        this.templateStatus = i;
    }

    @Override // com.miui.keyguard.editor.base.ExitAnimationPerformer
    public void startExitTransformerAnimation(boolean z, final TemplateApplier.ApplyCallback applyCallback, final ExitAnimationPerformer exitAnimationPerformer) {
        this.inAnim = true;
        final BaseTemplateView lockTemplateView = getLockTemplateView();
        Intrinsics.checkNotNull(lockTemplateView);
        final HomeTemplateView homeTemplateView = getHomeTemplateView();
        Intrinsics.checkNotNull(homeTemplateView);
        if (z) {
            ExitAnimationPerformer.DefaultImpls.startExitTransformerAnimation$default(lockTemplateView, true, null, null, 6, null);
            return;
        }
        Rect rect = new Rect();
        ViewUtil.INSTANCE.getShowRect(lockTemplateView, rect);
        final float translationX = lockTemplateView.getTranslationX() + ((this.screenSize.x / 2) - rect.centerX());
        ExitAnimationPerformer.DefaultImpls.startExitTransformerAnimation$default(lockTemplateView, false, null, null, 6, null);
        ExitAnimationPerformer.DefaultImpls.startExitTransformerAnimation$default(homeTemplateView, false, null, null, 6, null);
        float dimension = getResources().getDimension(R.dimen.kg_template_view_radius);
        ViewProperty viewProperty = ViewProperty.SCALE_X;
        String name = viewProperty.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        TemplateRadiusAnimationListener templateRadiusAnimationListener = new TemplateRadiusAnimationListener(lockTemplateView, dimension, false, name);
        IStateStyle iStateStyleAdd = Folme.use((View) lockTemplateView).state().add((FloatProperty) viewProperty, 1.0f);
        ViewProperty viewProperty2 = ViewProperty.SCALE_Y;
        IStateStyle iStateStyleAdd2 = iStateStyleAdd.add((FloatProperty) viewProperty2, 1.0f).add(ViewProperty.TRANSLATION_X, translationX);
        AnimConfig animConfig = TransitionKt.getAnimConfig(0.95f, 0.3f);
        final float f = 0.9f;
        animConfig.addListeners(templateRadiusAnimationListener, new TransitionListener() { // from class: com.miui.keyguard.editor.edit.base.CombineEditView$startExitTransformerAnimation$1$1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                requestExitAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                requestExitAnim();
            }

            public final void requestExitAnim() {
                lockTemplateView.setScaleX(1.0f);
                lockTemplateView.setScaleY(1.0f);
                lockTemplateView.setTranslationX(translationX);
                homeTemplateView.setScaleX(f);
                homeTemplateView.setScaleY(f);
                EditorServiceManager.Companion.getInstance().sendStartAnimationRequest(lockTemplateView, false, applyCallback, exitAnimationPerformer);
            }
        });
        Unit unit = Unit.INSTANCE;
        iStateStyleAdd2.to("lock anim", animConfig);
        Folme.use((View) homeTemplateView).state().add((FloatProperty) viewProperty, 0.9f).add((FloatProperty) viewProperty2, 0.9f).add((FloatProperty) ViewProperty.ALPHA, 0.0f).to("home scale", TransitionKt.getDefaultConfig());
    }

    public final void updateConfiguration(Configuration configuration) {
        onConfigurationChanged(configuration);
    }

    public final void updateEditTemplateTop() {
        Pair pair;
        Point point = this.screenSize;
        float f = point.x;
        float f2 = point.y;
        Float fValueOf = Float.valueOf(0.0f);
        RectF rectF = new RectF(0.0f, 0.0f, f, f2);
        Matrix matrix = new Matrix();
        CombineEditAdapter combineEditAdapter = this.editAdapter;
        float templateScale = combineEditAdapter != null ? combineEditAdapter.getTemplateScale() : 1.0f;
        CombineEditAdapter combineEditAdapter2 = this.editAdapter;
        if (combineEditAdapter2 == null || (pair = combineEditAdapter2.getTemplateScaleCenter()) == null) {
            pair = new Pair(fValueOf, fValueOf);
        }
        matrix.postScale(templateScale, templateScale, ((Number) pair.getFirst()).floatValue(), ((Number) pair.getSecond()).floatValue());
        matrix.mapRect(rectF);
        this.editTemplateTop = rectF.top;
    }

    public final void updateLoadingView() {
        BaseTemplateView lockTemplateView = getLockTemplateView();
        if (lockTemplateView != null) {
            lockTemplateView.updateLoadingView();
        }
        HomeTemplateView homeTemplateView = getHomeTemplateView();
        if (homeTemplateView != null) {
            homeTemplateView.updateLoadingView();
        }
    }

    public final void updateTemplateRect() {
        CombineSpringTouchManager combineSpringTouchManager = this.springTouchManager;
        if (combineSpringTouchManager == null || combineSpringTouchManager.getSelectViewPosition() != 0) {
            HomeTemplateView homeTemplateView = getHomeTemplateView();
            if (homeTemplateView != null) {
                ViewUtil.getShowRect$default(ViewUtil.INSTANCE, homeTemplateView, this.selectTemplateRectF, 0.0f, 0.0f, 6, null);
                TemplateButtonBar primaryButtonBar = homeTemplateView.getPrimaryButtonBar();
                if (primaryButtonBar != null) {
                    primaryButtonBar.updateButtonGroupRec();
                }
            }
            BaseTemplateView lockTemplateView = getLockTemplateView();
            if (lockTemplateView != null) {
                ViewUtil.getShowRect$default(ViewUtil.INSTANCE, lockTemplateView, this.unselectTemplateRectF, 0.0f, 0.0f, 6, null);
                return;
            }
            return;
        }
        BaseTemplateView lockTemplateView2 = getLockTemplateView();
        if (lockTemplateView2 != null) {
            ViewUtil.getShowRect$default(ViewUtil.INSTANCE, lockTemplateView2, this.selectTemplateRectF, 0.0f, 0.0f, 6, null);
            TemplateButtonBar primaryButtonBar2 = lockTemplateView2.getPrimaryButtonBar();
            if (primaryButtonBar2 != null) {
                primaryButtonBar2.updateButtonGroupRec();
            }
        }
        HomeTemplateView homeTemplateView2 = getHomeTemplateView();
        if (homeTemplateView2 != null) {
            ViewUtil.getShowRect$default(ViewUtil.INSTANCE, homeTemplateView2, this.unselectTemplateRectF, 0.0f, 0.0f, 6, null);
        }
    }
}
