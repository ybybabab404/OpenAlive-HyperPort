package com.miui.keyguard.editor.edit.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import com.miui.clock.MiuiClockController;
import com.miui.clock.MiuiClockView;
import com.miui.clock.module.ClockBean;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.ColorValueInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.preset.FilterGroup;
import com.miui.keyguard.editor.data.preset.FilterTypeSelectInfo;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterData;
import com.miui.keyguard.editor.data.preset.WallpaperFilter;
import com.miui.keyguard.editor.data.preset.WallpaperFilterKt;
import com.miui.keyguard.editor.data.util.DataUtilKt;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.handler.ColorPickHandler;
import com.miui.keyguard.editor.edit.color.picker.AutoColorPicker;
import com.miui.keyguard.editor.edit.signature.SignatureEditor;
import com.miui.keyguard.editor.edit.style.FontStyleEditCallback;
import com.miui.keyguard.editor.edit.style.RealtimeStyleEditor;
import com.miui.keyguard.editor.edit.style.ViewStyleEditor;
import com.miui.keyguard.editor.edit.view.EditorDialogTitleView;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.settings.util.ActivityEmbeddingUtils;
import com.miui.keyguard.editor.utils.AnimatorKt;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.DualClockManager;
import com.miui.keyguard.editor.utils.KeyboardUtils;
import com.miui.keyguard.editor.utils.MiuiClockScreenshotHelper;
import com.miui.keyguard.editor.utils.Process;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.utils.segment.SegmentInfo;
import com.miui.keyguard.editor.view.AutoBottomSheet;
import com.miui.keyguard.editor.view.ClockTemplateItem;
import com.miui.keyguard.editor.view.FontFilterView;
import com.miui.keyguard.editor.view.KgFrameKt;
import com.miui.keyguard.editor.view.KgFrameParam;
import com.miui.keyguard.editor.view.MiuiClockViewSafeHolder;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import com.miui.maml.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import miuix.bottomsheet.BottomSheetModal;
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
/* JADX INFO: compiled from: BaseClockView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public abstract class BaseClockView extends FrameLayout implements MiuiClockController.MiuiClockInfoListener, EditCallback, EditorDialogTitleView.OnCloseDialogListener, CombineEditListener {
    public static final Companion Companion = new Companion(null);
    public boolean allFrameShowing;
    public final List avoidEditTypeList;
    public View backContentLayer;
    public BaseTemplateView baseTemplateView;
    public View clock;
    public AutoBottomSheet clockBottomSheetModal;
    public AutoBottomSheet clockCustomFontColorBottomSheetModal;
    public FontFilterView clockFontFilterView;
    public AbstractPopupEditor clockStyleEditor;
    public View colorLayer;
    public final ClockBean currentClockBean;
    public int displayClockEffect;
    public EditFragment editFragment;
    public final Lazy editFrameController$delegate;
    public int enterOrientation;
    public int enterScreenState;
    public Bundle extraParameters;
    public FontStyleEditCallback fontStyleEditCallback;
    public FrameLayout foreContentLayer;
    public boolean isDualClock;
    public boolean mShow;
    public MiuiClockView miuiClockView;
    public final ArrayList miuiClockViewInitListeners;
    public ColorValueInfo primaryColorValueInfo;
    public RealtimeStyleEditor realtimeEditor;
    public TextView scaleTips;
    public ColorValueInfo secondaryColorValueInfo;
    public AutoBottomSheet signatureCustomColorBottomSheetModal;
    public FontFilterView signatureFontFilterView;
    public AutoBottomSheet signatureSheetModal;
    public SignatureEditor signatureStyleEditor;
    public int styleEditorFontColor;
    public boolean successUpdateClockView;

    /* JADX INFO: compiled from: BaseClockView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: BaseClockView.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CustomMarginType.values().length];
            try {
                iArr[CustomMarginType.WIDTH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CustomMarginType.HEIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CustomMarginType.ALL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseClockView$delayShowAllFrame$1, reason: invalid class name */
    /* JADX INFO: compiled from: BaseClockView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseClockView$delayShowAllFrame$1", f = "BaseClockView.kt", l = {321}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseClockView.this.new AnonymousClass1(continuation);
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
                if (DelayKt.delay(600L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            BaseClockView.this.showAllFrame();
            BaseClockView.this.allFrameShowing = false;
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseClockView$onMiuiClockViewCreated$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BaseClockView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseClockView$onMiuiClockViewCreated$1", f = "BaseClockView.kt", l = {516}, m = "invokeSuspend")
    public static final class C00991 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C00991(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseClockView.this.new C00991(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C00991) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            EditorDialogTitleView clockStyleEditorView;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                BaseClockView baseClockView = BaseClockView.this;
                this.label = 1;
                if (baseClockView.showClockEditBorderView(this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            if (BaseClockView.this.editable()) {
                BaseClockView baseClockView2 = BaseClockView.this;
                EditFragment editFragment = baseClockView2.getEditFragment();
                baseClockView2.popFontFilterDialog(editFragment != null ? editFragment.getMPopFontFilterPaperDialog() : -1);
            }
            EditFragment editFragment2 = BaseClockView.this.getEditFragment();
            if (editFragment2 != null && editFragment2.getMPopFontFilterPaperDialog() == 0 && (clockStyleEditorView = BaseClockView.this.getClockStyleEditorView()) != null) {
                clockStyleEditorView.switchTargetWithTitle(1);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseClockView$onScreenSizeChangedWhenEditable$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BaseClockView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseClockView$onScreenSizeChangedWhenEditable$1", f = "BaseClockView.kt", l = {660}, m = "invokeSuspend")
    public static final class C01001 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01001(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseClockView.this.new C01001(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01001) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(100L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            BaseClockView.this.generateEditFrames();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.base.BaseClockView$showClockEditBorderView$2, reason: invalid class name */
    /* JADX INFO: compiled from: BaseClockView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.base.BaseClockView$showClockEditBorderView$2", f = "BaseClockView.kt", l = {542, 547}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public Object L$0;
        public int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BaseClockView.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:27:0x005e  */
        /* JADX WARN: Code duplicated, block: B:29:0x0061  */
        /* JADX WARN: Code duplicated, block: B:39:0x0081  */
        /* JADX WARN: Code duplicated, block: B:41:0x008c  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CombineEditView combineEditView;
            CombineEditView combineEditView2;
            TemplateConfig templateConfig;
            WallpaperInfo wallpaperInfo;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            Bitmap currentWallpaper = null;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(100L, this) != coroutine_suspended) {
                }
                return coroutine_suspended;
            }
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
            } else {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                combineEditView2 = (CombineEditView) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            combineEditView = combineEditView2;
            if (!BaseClockView.this.isAttachedToWindow()) {
                return Unit.INSTANCE;
            }
            BaseClockView baseClockView = BaseClockView.this;
            templateConfig = baseClockView.templateConfig();
            if (templateConfig != null && (wallpaperInfo = templateConfig.getWallpaperInfo()) != null) {
                currentWallpaper = wallpaperInfo.getCurrentWallpaper();
            }
            baseClockView.initFrames(currentWallpaper, BaseClockView.this);
            if (combineEditView != null || combineEditView.getTemplateStatus() != 1) {
                BaseClockView.this.getEditFrameController().showAll();
                if (combineEditView != null) {
                    combineEditView.setFramesLoadCompleted(true);
                }
            }
            BaseClockView.this.getFramesComplete();
            return Unit.INSTANCE;
            EditFragment editFragment = BaseClockView.this.getEditFragment();
            combineEditView = editFragment != null ? editFragment.getCombineEditView() : null;
            if (combineEditView != null && combineEditView.isInAnim()) {
                BaseClockView baseClockView2 = BaseClockView.this;
                this.L$0 = combineEditView;
                this.label = 2;
                if (baseClockView2.awaitLayoutAfterAnim(combineEditView, this) != coroutine_suspended) {
                    combineEditView2 = combineEditView;
                    combineEditView = combineEditView2;
                }
                return coroutine_suspended;
            }
            if (!BaseClockView.this.isAttachedToWindow()) {
                return Unit.INSTANCE;
            }
            BaseClockView baseClockView3 = BaseClockView.this;
            templateConfig = baseClockView3.templateConfig();
            if (templateConfig != null) {
                currentWallpaper = wallpaperInfo.getCurrentWallpaper();
            }
            baseClockView3.initFrames(currentWallpaper, BaseClockView.this);
            if (combineEditView != null) {
                BaseClockView.this.getEditFrameController().showAll();
                if (combineEditView != null) {
                    combineEditView.setFramesLoadCompleted(true);
                }
            } else {
                BaseClockView.this.getEditFrameController().showAll();
                if (combineEditView != null) {
                    combineEditView.setFramesLoadCompleted(true);
                }
            }
            BaseClockView.this.getFramesComplete();
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseClockView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.displayClockEffect = -1;
        this.currentClockBean = new ClockBean(getClockStyleType());
        this.styleEditorFontColor = -16777216;
        this.isDualClock = DualClockManager.isDualClockAvailable(context);
        this.miuiClockViewInitListeners = new ArrayList();
        this.editFrameController$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.edit.base.BaseClockView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return BaseClockView.editFrameController_delegate$lambda$0(this.f$0);
            }
        });
        this.avoidEditTypeList = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.styleable.AppCompatTheme_toolbarStyle), 60});
    }

    public static final void createBottomSheet$lambda$25(BaseClockView baseClockView) {
        BaseTemplateView baseTemplateView = baseClockView.baseTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.onShowBottomSheet();
        }
        baseClockView.controlTopButtonState(false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final TemplateEditFrameController editFrameController_delegate$lambda$0(BaseClockView baseClockView) {
        return new TemplateEditFrameController(baseClockView.editFrameClickListener(), null, 2, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ KgFrameParam getCurrentFrameParam$default(BaseClockView baseClockView, int i, Rect rect, View[] viewArr, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getCurrentFrameParam");
        }
        if ((i3 & 2) != 0) {
            rect = null;
        }
        if ((i3 & 8) != 0) {
            i2 = com.miui.keyguard.editor.R.string.kg_font_filter_object_label_time;
        }
        return baseClockView.getCurrentFrameParam(i, rect, viewArr, i2);
    }

    public static /* synthetic */ KgFrameParam getFixationFrameParam$default(BaseClockView baseClockView, int i, Rect rect, CustomMarginType customMarginType, View[] viewArr, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getFixationFrameParam");
        }
        if ((i3 & 2) != 0) {
            rect = null;
        }
        Rect rect2 = rect;
        if ((i3 & 16) != 0) {
            i2 = com.miui.keyguard.editor.R.string.kg_font_filter_object_label_time;
        }
        return baseClockView.getFixationFrameParam(i, rect2, customMarginType, viewArr, i2);
    }

    public static /* synthetic */ void hideAllFrame$default(BaseClockView baseClockView, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: hideAllFrame");
        }
        if ((i & 1) != 0) {
            z = false;
        }
        baseClockView.hideAllFrame(z);
    }

    public static final void initClock$lambda$14(BaseClockView baseClockView, MiuiClockView miuiClockView, TemplateConfig templateConfig) {
        Log.d("Keyguard-Editor-BaseClockView", "miuiClockView load complete");
        baseClockView.clock = miuiClockView != null ? miuiClockView.getChildAt(0) : null;
        baseClockView.miuiClockView = miuiClockView;
        baseClockView.onMiuiClockViewCreated(templateConfig);
        baseClockView.setClockGradientIndex(templateConfig);
        if (baseClockView.editable()) {
            miuiClockView.setImportantForAccessibility(4);
            MiuiClockView foreClock = baseClockView.getForeClock();
            if (foreClock != null) {
                foreClock.setImportantForAccessibility(4);
            }
        }
        BaseTemplateView baseTemplateView = baseClockView.baseTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.onTemplateReady(3);
        }
    }

    public static final void onClickClockFrame$lambda$38$lambda$37(BaseClockView baseClockView, EditorDialogTitleView editorDialogTitleView) {
        baseClockView.onClockBottomSheetDismiss();
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (!deviceUtil.isPad()) {
            Context context = baseClockView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (!deviceUtil.isFoldLargeScreen(context)) {
                if (editorDialogTitleView != null) {
                    editorDialogTitleView.switchTargetWithTitle(1);
                    return;
                }
                return;
            }
        }
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context2 = baseClockView.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context2), Dispatchers.getMain(), null, new BaseClockView$onClickClockFrame$1$1$1(editorDialogTitleView, null), 2, null);
    }

    public static /* synthetic */ void popClockFontFilterDialog$default(BaseClockView baseClockView, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: popClockFontFilterDialog");
        }
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        baseClockView.popClockFontFilterDialog(i, i2);
    }

    public static final void popClockFontFilterDialog$lambda$36(BaseClockView baseClockView, int i, int i2) {
        baseClockView.popClockFontFilterDialog(i, i2 + 1);
    }

    public static /* synthetic */ void resetSpecialBottomSheet$default(BaseClockView baseClockView, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: resetSpecialBottomSheet");
        }
        if ((i & 1) != 0) {
            z = false;
        }
        baseClockView.resetSpecialBottomSheet(z);
    }

    private final void setClockGradientIndex(TemplateConfig templateConfig) {
        Bundle clockExtras;
        Bundle clockExtras2;
        if (!ClockEffectUtils.isGradualType(this.currentClockBean.getClockEffect()) || templateConfig == null || (clockExtras = templateConfig.getClockExtras()) == null || !clockExtras.containsKey("gradientIndex")) {
            return;
        }
        int i = (templateConfig == null || (clockExtras2 = templateConfig.getClockExtras()) == null) ? 0 : clockExtras2.getInt("gradientIndex");
        MiuiClockView miuiClockView = this.miuiClockView;
        if (miuiClockView != null) {
            miuiClockView.setGradientIndex(i);
        }
        MiuiClockView foreClock = getForeClock();
        if (foreClock != null) {
            foreClock.setGradientIndex(i);
        }
    }

    public static /* synthetic */ Object showClockEditBorderView$suspendImpl(BaseClockView baseClockView, Continuation continuation) {
        if (baseClockView.editable()) {
            EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
            Context context = baseClockView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), Dispatchers.getMain(), null, baseClockView.new AnonymousClass2(null), 2, null);
        }
        return Unit.INSTANCE;
    }

    public static /* synthetic */ void startPickColorFromWallpaper$default(BaseClockView baseClockView, Bitmap bitmap, boolean z, boolean z2, Rect rect, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: startPickColorFromWallpaper");
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        if ((i & 8) != 0) {
            rect = null;
        }
        baseClockView.startPickColorFromWallpaper(bitmap, z, z2, rect);
    }

    public void addDepthVideoForegroundLayer(View depthVideoForegroundLayer) {
        Intrinsics.checkNotNullParameter(depthVideoForegroundLayer, "depthVideoForegroundLayer");
        addView(depthVideoForegroundLayer);
        FrameLayout frameLayout = this.foreContentLayer;
        if (frameLayout != null) {
            frameLayout.bringToFront();
        }
    }

    public void addHierarchyLayer(View hierarchyLayer) {
        Intrinsics.checkNotNullParameter(hierarchyLayer, "hierarchyLayer");
        addView(hierarchyLayer);
        FrameLayout frameLayout = this.foreContentLayer;
        if (frameLayout != null) {
            frameLayout.bringToFront();
        }
    }

    public final void addLayers() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        View viewCreateColorLayer = createColorLayer(context);
        View view = null;
        if (viewCreateColorLayer != null) {
            addView(viewCreateColorLayer);
        } else {
            viewCreateColorLayer = null;
        }
        this.colorLayer = viewCreateColorLayer;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        View viewCreateBackContentLayer = createBackContentLayer(context2);
        if (viewCreateBackContentLayer != null) {
            addView(viewCreateBackContentLayer);
            view = viewCreateBackContentLayer;
        }
        this.backContentLayer = view;
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        MultiTouchFrameLayout multiTouchFrameLayoutCreateForeContentLayer = createForeContentLayer(context3);
        addView(multiTouchFrameLayoutCreateForeContentLayer);
        this.foreContentLayer = multiTouchFrameLayoutCreateForeContentLayer;
    }

    public void addMiuiClockViewInitListener(Runnable listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.miuiClockViewInitListeners.add(listener);
    }

    public void applyPrimaryMode() {
    }

    public void applySecondaryMode() {
    }

    public void applySignatureFrameVisibilityIfNeeded() {
    }

    public void applyTemplateInfo(TemplateConfig templateConfig) {
        ClockInfo clockInfo;
        if (templateConfig == null || (clockInfo = templateConfig.getClockInfo()) == null) {
            return;
        }
        clockInfo.setStyle(this.currentClockBean.getStyle());
        clockInfo.setPrimaryColor(this.currentClockBean.getPrimaryColor());
        clockInfo.setBlendColor(this.currentClockBean.getBlendColor());
        clockInfo.setAutoPrimaryColor(this.currentClockBean.isAutoPrimaryColor());
        ColorValueInfo primaryColorValueInfo = clockInfo.getPrimaryColorValueInfo();
        if (primaryColorValueInfo != null) {
            float[] fArr = new float[3];
            for (int i = 0; i < 3; i++) {
                fArr[i] = 0.0f;
            }
            ColorUtils.colorToHSL(clockInfo.getClockEffect() == 0 ? this.currentClockBean.getPrimaryColor() : this.currentClockBean.getBlendColor(), fArr);
            primaryColorValueInfo.setHueValue((int) ((fArr[0] / 360.0f) * 100.0f));
            primaryColorValueInfo.setSaturationValue((int) (fArr[1] * 100.0f));
            primaryColorValueInfo.setLightnessValue((int) (fArr[2] * 100.0f));
        }
        clockInfo.setSecondaryColor(this.currentClockBean.getSecondaryColor());
        clockInfo.setSecondaryBlendColor(this.currentClockBean.getSecondaryBlendColor());
        clockInfo.setAutoSecondaryColor(this.currentClockBean.isAutoSecondaryColor());
        clockInfo.setDiffHourMinuteColor(this.currentClockBean.isDiffHourMinuteColor());
        ColorValueInfo secondaryColorValueInfo = clockInfo.getSecondaryColorValueInfo();
        if (secondaryColorValueInfo != null) {
            float[] fArr2 = new float[3];
            for (int i2 = 0; i2 < 3; i2++) {
                fArr2[i2] = 0.0f;
            }
            ColorUtils.colorToHSL(clockInfo.getClockEffect() == 0 ? this.currentClockBean.getSecondaryColor() : this.currentClockBean.getSecondaryBlendColor(), fArr2);
            secondaryColorValueInfo.setHueValue((int) ((fArr2[0] / 360.0f) * 100.0f));
            secondaryColorValueInfo.setSaturationValue((int) (fArr2[1] * 100.0f));
            secondaryColorValueInfo.setLightnessValue((int) (fArr2[2] * 100.0f));
        }
        clockInfo.setClockEffect(this.currentClockBean.getClockEffect());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.view.ViewTreeObserver$OnGlobalLayoutListener, com.miui.keyguard.editor.edit.base.BaseClockView$awaitLayoutAfterAnim$2$layoutListener$1] */
    /* JADX WARN: Type inference failed for: r4v1, types: [android.view.View$OnAttachStateChangeListener, com.miui.keyguard.editor.edit.base.BaseClockView$awaitLayoutAfterAnim$2$detachListener$1] */
    public final Object awaitLayoutAfterAnim(final CombineEditView combineEditView, Continuation continuation) {
        if (!isAttachedToWindow()) {
            return Unit.INSTANCE;
        }
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final ?? r1 = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.miui.keyguard.editor.edit.base.BaseClockView$awaitLayoutAfterAnim$2$layoutListener$1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (combineEditView.isInAnim()) {
                    return;
                }
                ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
                if (!viewTreeObserver.isAlive()) {
                    viewTreeObserver = null;
                }
                if (viewTreeObserver != null) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                }
                if (cancellableContinuationImpl.isActive()) {
                    CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                    Result.Companion companion = Result.Companion;
                    cancellableContinuation.resumeWith(Result.m1061constructorimpl(Unit.INSTANCE));
                }
            }
        };
        final ?? r4 = new View.OnAttachStateChangeListener() { // from class: com.miui.keyguard.editor.edit.base.BaseClockView$awaitLayoutAfterAnim$2$detachListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View v) {
                Intrinsics.checkNotNullParameter(v, "v");
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View v) {
                Intrinsics.checkNotNullParameter(v, "v");
                ViewTreeObserver viewTreeObserver = this.this$0.getViewTreeObserver();
                if (!viewTreeObserver.isAlive()) {
                    viewTreeObserver = null;
                }
                if (viewTreeObserver != null) {
                    viewTreeObserver.removeOnGlobalLayoutListener(r1);
                }
                this.this$0.removeOnAttachStateChangeListener(this);
                if (cancellableContinuationImpl.isActive()) {
                    CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                    Result.Companion companion = Result.Companion;
                    cancellableContinuation.resumeWith(Result.m1061constructorimpl(Unit.INSTANCE));
                }
            }
        };
        getViewTreeObserver().addOnGlobalLayoutListener(r1);
        addOnAttachStateChangeListener(r4);
        cancellableContinuationImpl.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: com.miui.keyguard.editor.edit.base.BaseClockView$awaitLayoutAfterAnim$2$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Throwable) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(Throwable th) {
                ViewTreeObserver viewTreeObserver = this.this$0.getViewTreeObserver();
                if (!viewTreeObserver.isAlive()) {
                    viewTreeObserver = null;
                }
                if (viewTreeObserver != null) {
                    viewTreeObserver.removeOnGlobalLayoutListener(r1);
                }
                this.this$0.removeOnAttachStateChangeListener(r4);
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    public int backContentLayerId() {
        return 0;
    }

    public void clearEditeFocus() {
    }

    public void clearEditeStatus() {
    }

    public void clearSelectState() {
        getEditFrameController().clearSelectState();
    }

    @Override // com.miui.keyguard.editor.edit.view.EditorDialogTitleView.OnCloseDialogListener
    public void closeDialog() {
        for (AutoBottomSheet autoBottomSheet : getAutoBottomSheetList()) {
            if (autoBottomSheet != null) {
                autoBottomSheet.dismiss();
            }
        }
        onClockBottomSheetDismiss();
        onSignatureBottomSheetDismiss();
        this.mShow = false;
    }

    public final boolean completeWithCacheColor(boolean z) {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return baseTemplateView.completeWithCacheColor(z);
        }
        return false;
    }

    public final Rect computeRect(View[] viewArr) {
        if (viewArr.length == 0) {
            DataUtilKt.throwIfDebug("view is empty");
        }
        Rect rect = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0);
        for (View view : viewArr) {
            if (view != null && view.getVisibility() != 8) {
                Rect rect2 = new Rect();
                view.getGlobalVisibleRect(rect2);
                if (KgFrameKt.isInValidParam(rect2)) {
                    rect2.left = view.getLeft();
                    rect2.right = view.getRight();
                    rect2.top = view.getTop();
                    rect2.bottom = view.getBottom();
                }
                if (!KgFrameKt.isInValidParam(rect2)) {
                    rect.left = RangesKt.coerceAtMost(rect.left, rect2.left);
                    rect.top = RangesKt.coerceAtMost(rect.top, rect2.top);
                    rect.right = RangesKt.coerceAtLeast(rect.right, rect2.right);
                    rect.bottom = RangesKt.coerceAtLeast(rect.bottom, rect2.bottom);
                }
            }
        }
        return rect;
    }

    public void controlTopButtonState(boolean z) {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.controlTopButtonState(z);
        }
    }

    public AutoColorPicker createAutoColorPicker() {
        return null;
    }

    public View createBackContentLayer(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (backContentLayerId() == 0) {
            return null;
        }
        if (Process.INSTANCE.isThemeManagerProcess() && (context instanceof Activity) && ActivityEmbeddingUtils.isActivityAlreadyEmbedded(context)) {
            context = DeviceUtil.INSTANCE.createConfigurationContext(context);
        }
        View viewInflate = LayoutInflater.from(context).inflate(backContentLayerId(), (ViewGroup) null);
        viewInflate.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type android.widget.FrameLayout");
        return (FrameLayout) viewInflate;
    }

    public AutoBottomSheet createBottomSheet(View anchorView, int i, int i2, int i3, int i4, int i5) {
        Intrinsics.checkNotNullParameter(anchorView, "anchorView");
        Context context = getContext();
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.miui.keyguard.editor.EditorActivity");
        AutoBottomSheet margin = new AutoBottomSheet((EditorActivity) context).setOnBeforeShowListener(new BottomSheetModal.OnShowListener() { // from class: com.miui.keyguard.editor.edit.base.BaseClockView$$ExternalSyntheticLambda2
            @Override // miuix.bottomsheet.BottomSheetModal.OnShowListener
            public final void onShow() {
                BaseClockView.createBottomSheet$lambda$25(this.f$0);
            }
        }).setOffsetParams(this.baseTemplateView).setScaleParam().setAnchorView(anchorView, i).setMargin(i2, i3, i4, i5);
        margin.setSkipCollapsed(true);
        margin.setSkipHalfExpanded(true);
        return margin;
    }

    public View createColorLayer(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        return imageView;
    }

    public ColorPickHandler createColorPickHandler() {
        return null;
    }

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
    public MultiTouchFrameLayout createForeContentLayer(Context context) {
        MultiTouchFrameLayout multiTouchFrameLayout;
        WallpaperInfo wallpaperInfo;
        Intrinsics.checkNotNullParameter(context, "context");
        Context contextCreateConfigurationContext = (Process.INSTANCE.isThemeManagerProcess() && (context instanceof Activity) && ActivityEmbeddingUtils.isActivityAlreadyEmbedded(context)) ? DeviceUtil.INSTANCE.createConfigurationContext(context) : context;
        String resourceType = null;
        if (foreContentLayerId() != 0) {
            View viewInflate = LayoutInflater.from(contextCreateConfigurationContext).inflate(foreContentLayerId(), (ViewGroup) null);
            Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.base.MultiTouchFrameLayout");
            multiTouchFrameLayout = (MultiTouchFrameLayout) viewInflate;
        } else {
            multiTouchFrameLayout = new MultiTouchFrameLayout(contextCreateConfigurationContext);
        }
        multiTouchFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        if (editable()) {
            TextView textView = new TextView(context);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.topMargin = textView.getResources().getDimensionPixelSize(com.miui.keyguard.editor.R.dimen.kg_scale_tips_top_margin);
            layoutParams.gravity = 49;
            Resources resources = textView.getResources();
            int i = com.miui.keyguard.editor.R.dimen.kg_title_pinch_to_scale_margin_start_or_end;
            layoutParams.setMarginStart(resources.getDimensionPixelSize(i));
            layoutParams.setMarginEnd(textView.getResources().getDimensionPixelSize(i));
            textView.setLayoutParams(layoutParams);
            textView.setText(com.miui.keyguard.editor.R.string.kg_function_wallpaper_pinch_to_zoom);
            textView.setTextSize(0, textView.getResources().getDimension(com.miui.keyguard.editor.R.dimen.kg_scale_tips_text_size));
            textView.setTextColor(ViewUtil.INSTANCE.getColor(textView, com.miui.keyguard.editor.R.color.kg_white_60));
            WallpaperEditor.Companion companion = WallpaperEditor.Companion;
            TemplateConfig templateConfig = templateConfig();
            if (templateConfig != null && (wallpaperInfo = templateConfig.getWallpaperInfo()) != null) {
                resourceType = wallpaperInfo.getResourceType();
            }
            textView.setAlpha(companion.canScale(resourceType) ? 1.0f : 0.0f);
            textView.setImportantForAccessibility(2);
            this.scaleTips = textView;
            multiTouchFrameLayout.addView(textView);
        }
        return multiTouchFrameLayout;
    }

    public Rect currentColorPickupRect() {
        return null;
    }

    public final void delayShowAllFrame() {
        this.allFrameShowing = true;
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new AnonymousClass1(null), 3, null);
    }

    public void destroy() {
        this.colorLayer = null;
        this.backContentLayer = null;
        this.foreContentLayer = null;
        this.miuiClockView = null;
        this.fontStyleEditCallback = null;
        this.miuiClockViewInitListeners.clear();
        AutoBottomSheet autoBottomSheet = this.signatureSheetModal;
        if (autoBottomSheet != null) {
            autoBottomSheet.reset(false);
        }
        this.signatureSheetModal = null;
        AutoBottomSheet autoBottomSheet2 = this.signatureCustomColorBottomSheetModal;
        if (autoBottomSheet2 != null) {
            autoBottomSheet2.reset(false);
        }
        this.signatureCustomColorBottomSheetModal = null;
        resetFilterView();
    }

    public void dismissBottomSheet() {
        AutoBottomSheet autoBottomSheet;
        AutoBottomSheet autoBottomSheet2 = this.clockCustomFontColorBottomSheetModal;
        if ((autoBottomSheet2 == null || !autoBottomSheet2.isShowing()) && ((autoBottomSheet = this.signatureCustomColorBottomSheetModal) == null || !autoBottomSheet.isShowing())) {
            closeDialog();
            return;
        }
        AutoBottomSheet autoBottomSheet3 = this.clockCustomFontColorBottomSheetModal;
        if (autoBottomSheet3 != null) {
            autoBottomSheet3.dismiss();
        }
        AutoBottomSheet autoBottomSheet4 = this.signatureCustomColorBottomSheetModal;
        if (autoBottomSheet4 != null) {
            autoBottomSheet4.dismiss();
        }
    }

    public Rect dualClockAvoidRect() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(com.miui.keyguard.editor.R.dimen.kg_template_view_dual_hierarchy_avoid_rect_top);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(com.miui.keyguard.editor.R.dimen.kg_template_view_dual_hierarchy_avoid_rect_width);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(com.miui.keyguard.editor.R.dimen.kg_template_view_dual_hierarchy_avoid_rect_height);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int width = (deviceUtil.currentDisplaySizeByContext(context).getWidth() - dimensionPixelSize2) / 2;
        return new Rect(width, dimensionPixelSize, dimensionPixelSize2 + width, dimensionPixelSize3 + dimensionPixelSize);
    }

    public abstract View.OnClickListener editFrameClickListener();

    public final boolean editable() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return baseTemplateView.editable();
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x002c  */
    public final boolean effectDisable(FontFilterData fontFilterData) {
        boolean z;
        WallpaperInfo wallpaperInfo;
        if (fontFilterData == null || fontFilterData.getFilterId() != 2) {
            z = false;
        } else {
            MagicType magicType = MagicType.INSTANCE;
            TemplateConfig templateConfig = templateConfig();
            if (magicType.isDepthVideo((templateConfig == null || (wallpaperInfo = templateConfig.getWallpaperInfo()) == null) ? null : Integer.valueOf(wallpaperInfo.getMagicType()))) {
                z = true;
            } else {
                z = false;
            }
        }
        EditFragmentViewModel.Companion companion = EditFragmentViewModel.Companion;
        int filterId = fontFilterData != null ? fontFilterData.getFilterId() : 1;
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        return z || companion.shouldDisableGlassEffect(filterId, baseTemplateView != null ? baseTemplateView.templateConfig() : null);
    }

    public void exitEditFrame() {
        getEditFrameController().exit();
        TextView textView = this.scaleTips;
        if (textView != null) {
            AnimatorKt.alphaTo$default(textView, 0.0f, 0.0f, 0.0f, null, 14, null);
        }
    }

    public void exitEdite() {
    }

    public int foreContentLayerId() {
        return 0;
    }

    public void generateEditFrames() {
        EditFragment editFragment;
        CombineEditView combineEditView;
        CombineEditView combineEditView2;
        if (this.isDualClock) {
            return;
        }
        getEditFrameController().updateIfNeeded(getEditFrames(this));
        if (!getEditFrameController().getCanShowAll$keyguardeditor_release() || ((editFragment = this.editFragment) != null && (combineEditView2 = editFragment.getCombineEditView()) != null && combineEditView2.getTemplateStatus() == 1)) {
            TemplateEditFrameController.hideAll$default(getEditFrameController(), false, 1, null);
            return;
        }
        getEditFrameController().showAll();
        applySignatureFrameVisibilityIfNeeded();
        EditFragment editFragment2 = this.editFragment;
        if (editFragment2 == null || (combineEditView = editFragment2.getCombineEditView()) == null) {
            return;
        }
        combineEditView.setFramesLoadCompleted(true);
    }

    @NotNull
    public List<View> getAdditionalExitLayers() {
        return CollectionsKt.emptyList();
    }

    public int getAllInOneSetWay() {
        return 0;
    }

    @NotNull
    public AutoBottomSheet[] getAutoBottomSheetList() {
        return new AutoBottomSheet[]{this.clockBottomSheetModal, this.clockCustomFontColorBottomSheetModal, this.signatureSheetModal, this.signatureCustomColorBottomSheetModal};
    }

    @Nullable
    public final <T extends View> T getBackContentLayer() {
        return (T) this.backContentLayer;
    }

    @Nullable
    public final BaseTemplateView getBaseTemplateView() {
        return this.baseTemplateView;
    }

    @Nullable
    public final View getClock() {
        return this.clock;
    }

    @Nullable
    public final AutoBottomSheet getClockBottomSheetModal() {
        return this.clockBottomSheetModal;
    }

    @Nullable
    public final AutoBottomSheet getClockCustomFontColorBottomSheetModal() {
        return this.clockCustomFontColorBottomSheetModal;
    }

    @Nullable
    public final FontFilterView getClockFontFilterView() {
        return this.clockFontFilterView;
    }

    @Nullable
    public final AbstractPopupEditor getClockStyleEditor() {
        return this.clockStyleEditor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public EditorDialogTitleView getClockStyleEditorView() {
        EditorDialogTitleView editorDialogTitleViewEditorViewContainer;
        getOrCreateClockStyleEditor();
        AbstractPopupEditor abstractPopupEditor = this.clockStyleEditor;
        if (abstractPopupEditor != 0) {
            FontFilterView fontFilterView = abstractPopupEditor.getFontFilterView();
            this.clockFontFilterView = fontFilterView;
            if (fontFilterView != null) {
                fontFilterView.setCustomColorBottomSheet(getCustomFontBottomSheet());
            }
            if (abstractPopupEditor instanceof ViewStyleEditor) {
                this.realtimeEditor = (RealtimeStyleEditor) abstractPopupEditor;
            }
        }
        AbstractPopupEditor abstractPopupEditor2 = this.clockStyleEditor;
        if (abstractPopupEditor2 == null || (editorDialogTitleViewEditorViewContainer = abstractPopupEditor2.editorViewContainer()) == null) {
            return null;
        }
        editorDialogTitleViewEditorViewContainer.setOnCloseDialogListener(this);
        return editorDialogTitleViewEditorViewContainer;
    }

    @NotNull
    public abstract String getClockStyleType();

    @Nullable
    public final <T extends View> T getColorLayer() {
        return (T) this.colorLayer;
    }

    @Nullable
    public ColorPickHandler getColorPickHandler() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return baseTemplateView.getColorPickHandler();
        }
        return null;
    }

    @NotNull
    public final ClockBean getCurrentClockBean() {
        return this.currentClockBean;
    }

    public final KgFrameParam getCurrentFrameParam(int i, Rect rect, View[] views, int i2) {
        Intrinsics.checkNotNullParameter(views, "views");
        Rect rectComputeRect = computeRect(views);
        if (rect != null) {
            rectComputeRect.left += rect.left;
            rectComputeRect.top += rect.top;
            rectComputeRect.right += rect.right;
            rectComputeRect.bottom += rect.bottom;
        }
        return new KgFrameParam(i, rectComputeRect, i2);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public int getCurrentSelect() {
        return CombineEditListener.DefaultImpls.getCurrentSelect(this);
    }

    @Nullable
    public final AutoBottomSheet getCustomFontBottomSheet() {
        return this.clockCustomFontColorBottomSheetModal;
    }

    @Override // com.miui.keyguard.editor.edit.base.EditCallback
    public Object getData(String key, Object... params) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(params, "params");
        return EditCallback.DefaultImpls.getData(this, key, Arrays.copyOf(params, params.length));
    }

    public List getDepthLayerOrder(View view, View view2) {
        return CollectionsKt.listOfNotNull((Object[]) new View[]{getBackContentLayer(), view, view2, getForeContentLayer()});
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public int getDialogOffset() {
        return CombineEditListener.DefaultImpls.getDialogOffset(this);
    }

    public final int getDisplayClockEffect() {
        return this.displayClockEffect;
    }

    @Nullable
    public final EditFragment getEditFragment() {
        return this.editFragment;
    }

    @NotNull
    public final TemplateEditFrameController getEditFrameController() {
        return (TemplateEditFrameController) this.editFrameController$delegate.getValue();
    }

    public List getEditFrames(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return CollectionsKt.emptyList();
    }

    public int getFilterBottomSheetAlignModel() {
        return 1280;
    }

    public final KgFrameParam getFixationFrameParam(int i, Rect rect, CustomMarginType customType, View[] views, int i2) {
        Intrinsics.checkNotNullParameter(customType, "customType");
        Intrinsics.checkNotNullParameter(views, "views");
        Rect rectComputeRect = computeRect(views);
        if (rect != null) {
            int i3 = WhenMappings.$EnumSwitchMapping$0[customType.ordinal()];
            if (i3 == 1) {
                rectComputeRect.left = rect.left;
                rectComputeRect.top += rect.top;
                rectComputeRect.right = rect.right;
                rectComputeRect.bottom += rect.bottom;
            } else if (i3 == 2) {
                rectComputeRect.left = rect.left;
                int i4 = rectComputeRect.top + rect.top;
                rectComputeRect.top = i4;
                rectComputeRect.right = rect.right;
                rectComputeRect.bottom = i4 + rect.bottom;
            } else {
                if (i3 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                int i5 = rect.left;
                rectComputeRect.left = i5;
                rectComputeRect.top = rect.top;
                rectComputeRect.right = i5 + rect.width();
                rectComputeRect.bottom = rect.bottom + rect.top;
            }
        }
        return new KgFrameParam(i, rectComputeRect, i2);
    }

    @NotNull
    public FontFilter getFontFilter() {
        return new FontFilter(new Integer[0], new FilterGroup[0][], false, 4, null);
    }

    @NotNull
    public FilterTypeSelectInfo[] getFontFilterColorSelectInfo() {
        int i = 0;
        int i2 = 2;
        DefaultConstructorMarker defaultConstructorMarker = null;
        return new FilterTypeSelectInfo[]{new FilterTypeSelectInfo(i, i, i2, defaultConstructorMarker), new FilterTypeSelectInfo(i, i, i2, defaultConstructorMarker)};
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public View getFontStyleContentView() {
        View viewCreateFontEditorContent;
        AbstractPopupEditor orCreateClockStyleEditor = getOrCreateClockStyleEditor();
        if (orCreateClockStyleEditor instanceof ViewStyleEditor) {
            this.realtimeEditor = (RealtimeStyleEditor) orCreateClockStyleEditor;
        }
        FontFilterView fontFilterView = orCreateClockStyleEditor != 0 ? orCreateClockStyleEditor.getFontFilterView() : null;
        this.clockFontFilterView = fontFilterView;
        if (fontFilterView != null) {
            fontFilterView.setCustomColorBottomSheet(this.clockCustomFontColorBottomSheetModal);
        }
        return (orCreateClockStyleEditor == 0 || (viewCreateFontEditorContent = orCreateClockStyleEditor.createFontEditorContent()) == null) ? new View(getContext()) : viewCreateFontEditorContent;
    }

    @Nullable
    public final FontStyleEditCallback getFontStyleEditCallback() {
        return this.fontStyleEditCallback;
    }

    @Nullable
    public MiuiClockView getForeClock() {
        return null;
    }

    @Nullable
    public final <T extends View> T getForeContentLayer() {
        return this.foreContentLayer;
    }

    public void getFramesComplete() {
    }

    @Nullable
    public List<View> getHideViewInScreenShot() {
        return null;
    }

    @Nullable
    public <T extends View> T getHierarchyLayer() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return (T) baseTemplateView.getHierarchyLayer();
        }
        return null;
    }

    @Nullable
    public String getLastStyleClassicSignature() {
        return this.currentClockBean.getClassicSignature();
    }

    public int getLastStyleClockWeight() {
        return this.currentClockBean.getClockWeight();
    }

    @Nullable
    public Integer getLastStyleFontStyle() {
        return Integer.valueOf(this.currentClockBean.getFontStyle());
    }

    public float getLastStyleGlassTransparency() {
        Float glassTransparency = this.currentClockBean.getGlassTransparency();
        if (glassTransparency != null) {
            return glassTransparency.floatValue();
        }
        return 0.15f;
    }

    public int getLastStyleHollowStyle() {
        return this.currentClockBean.getHollowStyle();
    }

    public final boolean getMShow() {
        return this.mShow;
    }

    @Nullable
    public final MiuiClockView getMiuiClockView() {
        return this.miuiClockView;
    }

    @NotNull
    public MiuiClockViewSafeHolder getMiuiClockViewSafeHolder() {
        ClockInfo clockInfo;
        TemplateConfig templateConfig = templateConfig();
        return new MiuiClockViewSafeHolder((templateConfig == null || (clockInfo = templateConfig.getClockInfo()) == null) ? null : clockInfo.getTemplateId(), new WeakReference(this.miuiClockView), null, null, 12, null);
    }

    @Nullable
    public AbstractPopupEditor getOrCreateClockStyleEditor() {
        return null;
    }

    @Nullable
    public SignatureEditor getOrCreateSignatureStyleEditor() {
        return null;
    }

    public int getPickLiftClickedViewId() {
        return 0;
    }

    @Nullable
    public final RealtimeStyleEditor getRealtimeEditor() {
        return this.realtimeEditor;
    }

    @Nullable
    public final TextView getScaleTips() {
        return this.scaleTips;
    }

    @Nullable
    public final AutoBottomSheet getSignatureCustomColorBottomSheetModal() {
        return this.signatureCustomColorBottomSheetModal;
    }

    @Nullable
    public final FontFilterView getSignatureFontFilterView() {
        return this.signatureFontFilterView;
    }

    @Nullable
    public final AutoBottomSheet getSignatureSheetModal() {
        return this.signatureSheetModal;
    }

    @Nullable
    public final SignatureEditor getSignatureStyleEditor() {
        return this.signatureStyleEditor;
    }

    public EditorDialogTitleView getSignatureStyleEditorView(View anchorView) {
        EditorDialogTitleView container;
        Intrinsics.checkNotNullParameter(anchorView, "anchorView");
        getOrCreateSignatureStyleEditor();
        SignatureEditor signatureEditor = this.signatureStyleEditor;
        if (signatureEditor != null) {
            FontFilterView fontFilterView = signatureEditor.getFontFilterView();
            this.signatureFontFilterView = fontFilterView;
            if (fontFilterView != null) {
                fontFilterView.setCustomColorBottomSheet(this.signatureCustomColorBottomSheetModal);
            }
            EditorDialogTitleView container2 = signatureEditor.getContainer();
            if (container2 != null) {
                container2.setOnCloseDialogListener(this);
            }
        }
        SignatureEditor signatureEditor2 = this.signatureStyleEditor;
        if (signatureEditor2 != null && (container = signatureEditor2.getContainer()) != null) {
            container.switchTargetWithTitle(0);
        }
        SignatureEditor signatureEditor3 = this.signatureStyleEditor;
        if (signatureEditor3 != null) {
            return signatureEditor3.getContainer();
        }
        return null;
    }

    public final int getStyleEditorFontColor() {
        return this.styleEditorFontColor;
    }

    @Nullable
    public SegmentInfo getSubjectInfo() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return baseTemplateView.getSubjectInfo();
        }
        return null;
    }

    public final boolean getSuccessUpdateClockView() {
        return this.successUpdateClockView;
    }

    public long getTemplateSource() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return baseTemplateView.getTemplateSource();
        }
        return -1L;
    }

    @NotNull
    public List<WallpaperFilter> getWallpaperFilter() {
        return WallpaperFilterKt.getDEFAULT_WALLPAPER_FILTER_DATA();
    }

    @Nullable
    public <T extends IWallpaperLayer> T getWallpaperLayer() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return (T) baseTemplateView.getWallpaperLayer();
        }
        return null;
    }

    public void hideAllFrame(boolean z) {
        getEditFrameController().hideAll(z);
    }

    public void hideSignature(boolean z) {
    }

    public Rect hierarchyAvoidRect() {
        return this.isDualClock ? dualClockAvoidRect() : templateHierarchyAvoidRect();
    }

    public final boolean hierarchyEnable() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return baseTemplateView.hierarchyEnable();
        }
        return false;
    }

    public final Set hierarchyWithNotificationTypes() {
        return SetsKt.setOf((Object[]) new String[]{"hierarchy_check_small_screen_with_notification", "hierarchy_check_large_screen_port_with_notification", "hierarchy_check_large_screen_land_with_notification"});
    }

    public void inTalkBackCloseDialogs() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.inTalkBackCloseDialogs(new AutoBottomSheet[0]);
        }
    }

    public final void init(BaseTemplateView baseTemplateView, EditFragment editFragment) {
        Intrinsics.checkNotNullParameter(baseTemplateView, "baseTemplateView");
        this.baseTemplateView = baseTemplateView;
        this.editFragment = editFragment;
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{com.miui.keyguard.editor.R.attr.editorFontColor});
        this.styleEditorFontColor = typedArrayObtainStyledAttributes.getColor(0, -16777216);
        typedArrayObtainStyledAttributes.recycle();
        addLayers();
    }

    public void initClock(final TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        final MiuiClockView miuiClockView = (MiuiClockView) findViewById(com.miui.keyguard.editor.R.id.clock_view);
        if (miuiClockView != null) {
            miuiClockView.setMiuiClockInfoListener(this);
        }
        if (isCreateForScreenShot()) {
            MiuiClockScreenshotHelper miuiClockScreenshotHelper = MiuiClockScreenshotHelper.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.miui.keyguard.editor.utils.DeviceScreenshotHelper.ScreenshotContext");
            Context contextCreateScreenshotConfiguration = miuiClockScreenshotHelper.createScreenshotConfiguration((DeviceScreenshotHelper.ScreenshotContext) context);
            if (miuiClockView != null) {
                miuiClockView.setContext(contextCreateScreenshotConfiguration);
            }
        }
        if (miuiClockView != null) {
            miuiClockView.ignoreAccessibilityScale(true);
        }
        ClockBean clockBean = this.currentClockBean;
        if (clockBean != null) {
            clockBean.setUnablePresetData(true);
        }
        if (miuiClockView != null) {
            miuiClockView.init(this.currentClockBean, true);
        }
        initForeClock(this);
        if (miuiClockView != null) {
            miuiClockView.setLoadListener(new MiuiClockController.LoadListener() { // from class: com.miui.keyguard.editor.edit.base.BaseClockView$$ExternalSyntheticLambda4
                @Override // com.miui.clock.MiuiClockController.LoadListener
                public final void loadFinish() {
                    BaseClockView.initClock$lambda$14(this.f$0, miuiClockView, templateConfig);
                }
            });
        }
    }

    public final void initForTheme() {
        addLayers();
    }

    public void initForeClock(FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
    }

    public void initFrames(Bitmap bitmap, FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        if (!this.isDualClock) {
            MultiTouchFrameLayout multiTouchFrameLayout = (MultiTouchFrameLayout) getForeContentLayer();
            if (multiTouchFrameLayout != null) {
                getEditFrameController().initForeLayer(multiTouchFrameLayout);
                getEditFrameController().addAll(getEditFrames(templateView));
            }
            BaseTemplateView baseTemplateView = this.baseTemplateView;
            if (baseTemplateView != null) {
                baseTemplateView.resetColorModes();
            }
        }
        requestLayout();
    }

    public void initTemplateBean(TemplateConfig templateConfig) {
        String string;
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        Bundle bundle = this.extraParameters;
        if (bundle != null && bundle.containsKey("localCity")) {
            ClockBean clockBean = this.currentClockBean;
            Bundle bundle2 = this.extraParameters;
            if (bundle2 == null || (string = bundle2.getString("localCity")) == null) {
                string = null;
            } else {
                Log.i("Keyguard-Editor-BaseClockView", "localCity preset: " + string);
            }
            clockBean.setDualClockLocalCity(string);
        }
        ClockInfo clockInfo = templateConfig.getClockInfo();
        if ((clockInfo != null ? clockInfo.getPrimaryColorValueInfo() : null) == null) {
            templateConfig.getClockInfo().setPrimaryColorValueInfo(new ColorValueInfo(0, 0, 0, 7, null));
        }
        ClockInfo clockInfo2 = templateConfig.getClockInfo();
        if ((clockInfo2 != null ? clockInfo2.getSecondaryColorValueInfo() : null) == null) {
            templateConfig.getClockInfo().setSecondaryColorValueInfo(new ColorValueInfo(0, 0, 0, 7, null));
        }
        ClockInfo clockInfo3 = templateConfig.getClockInfo();
        this.primaryColorValueInfo = clockInfo3 != null ? clockInfo3.getPrimaryColorValueInfo() : null;
        ClockInfo clockInfo4 = templateConfig.getClockInfo();
        this.secondaryColorValueInfo = clockInfo4 != null ? clockInfo4.getSecondaryColorValueInfo() : null;
        this.currentClockBean.setEnableDiffusion(templateConfig.getClockInfo().getEnableDiffusion());
        initClock(templateConfig);
    }

    public void initTemplateBean(TemplateConfig templateConfig, String str) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        initTemplateBean(templateConfig);
    }

    public final boolean isClockTemplateChanged() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        return baseTemplateView != null && baseTemplateView.isClockTemplateChanged();
    }

    public final boolean isCreateForScreenShot() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return baseTemplateView.isCreateForScreenShot();
        }
        return false;
    }

    public final boolean isDualClock() {
        return this.isDualClock;
    }

    public final boolean isEnableAlphaTitle() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return baseTemplateView.isEnableAlphaTitle();
        }
        return false;
    }

    public final boolean isGalleryOpened() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return baseTemplateView.isGalleryOpened();
        }
        return false;
    }

    public final boolean isGlassDisabled() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return baseTemplateView.isGlassDisabled();
        }
        return false;
    }

    public boolean isInThirdPartyWallpaperService() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return baseTemplateView.isInThirdPartyWallpaperService();
        }
        return false;
    }

    public boolean isPickPanelLifted() {
        return false;
    }

    public Rect largeScreenWithNotificationAvoidRect(boolean z) {
        return new Rect();
    }

    public void markEdited() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.markEdited();
        }
    }

    public void onApplied(boolean z) {
    }

    public void onClickClockFrame(View anchor) {
        final BaseClockView baseClockView;
        View view;
        Intrinsics.checkNotNullParameter(anchor, "anchor");
        resetPrimaryButtonBar();
        if (this.clockBottomSheetModal == null) {
            baseClockView = this;
            view = anchor;
            baseClockView.clockBottomSheetModal = createBottomSheet(anchor, 1280, 0, 0, 0, sheetMarginBottom());
        } else {
            baseClockView = this;
            view = anchor;
        }
        if (baseClockView.clockCustomFontColorBottomSheetModal == null) {
            baseClockView.clockCustomFontColorBottomSheetModal = baseClockView.createBottomSheet(view, 1280, 0, 0, 0, baseClockView.sheetMarginBottom());
        }
        AutoBottomSheet autoBottomSheet = baseClockView.clockBottomSheetModal;
        if (autoBottomSheet != null) {
            final EditorDialogTitleView clockStyleEditorView = baseClockView.getClockStyleEditorView();
            if (clockStyleEditorView != null) {
                clockStyleEditorView.sendAccessibilityEvent();
            }
            autoBottomSheet.setContentView(clockStyleEditorView);
            autoBottomSheet.setOnDismissListener(new BottomSheetModal.OnDismissListener() { // from class: com.miui.keyguard.editor.edit.base.BaseClockView$$ExternalSyntheticLambda3
                @Override // miuix.bottomsheet.BottomSheetModal.OnDismissListener
                public final void onDismiss() {
                    BaseClockView.onClickClockFrame$lambda$38$lambda$37(this.f$0, clockStyleEditorView);
                }
            });
        }
        baseClockView.mShow = true;
        AutoBottomSheet autoBottomSheet2 = baseClockView.clockBottomSheetModal;
        if (autoBottomSheet2 != null) {
            autoBottomSheet2.show();
        }
        BaseTemplateView baseTemplateView = baseClockView.baseTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.dismissAutoBottomSheetIfNeed();
        }
    }

    public void onClockBeanChange(ClockBean clockBean, boolean z) {
    }

    public void onClockBottomSheetDismiss() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null && baseTemplateView.isTemplateSwitching$keyguardeditor_release()) {
            Log.d("Keyguard-Editor-BaseClockView", "onClockBottomSheetDismiss: deferred, isTemplateSwitching=true");
            BaseTemplateView baseTemplateView2 = this.baseTemplateView;
            if (baseTemplateView2 != null) {
                baseTemplateView2.setPendingDismissAfterSwitch$keyguardeditor_release(true);
            }
            this.mShow = false;
            return;
        }
        clearSelectState();
        FontFilterView fontFilterView = this.clockFontFilterView;
        if (fontFilterView != null) {
            fontFilterView.reset();
        }
        BaseTemplateView baseTemplateView3 = this.baseTemplateView;
        if (baseTemplateView3 != null) {
            baseTemplateView3.onDismissBottomSheet();
        }
        this.mShow = false;
    }

    public void onClockInfoUpdate() {
        markEdited();
        MiuiClockView miuiClockView = this.miuiClockView;
        if (miuiClockView != null) {
            miuiClockView.setClockBean(this.currentClockBean);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissBottomSheet() {
        CombineEditListener.DefaultImpls.onDismissBottomSheet(this);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissBottomSheetForScreenChange(Configuration configuration) {
        CombineEditListener.DefaultImpls.onDismissBottomSheetForScreenChange(this, configuration);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissKeyboard() {
        CombineEditListener.DefaultImpls.onDismissKeyboard(this);
    }

    @Override // com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public void onDualClockChange(boolean z) {
    }

    public void onEdited(int i, Object obj) {
        BaseTemplateView baseTemplateView;
        Log.d("Keyguard-Editor-BaseClockView", "onEdited: " + i);
        if (!this.avoidEditTypeList.contains(Integer.valueOf(i))) {
            markEdited();
        }
        if (i == 200) {
            ClockTemplateItem clockTemplateItem = (ClockTemplateItem) obj;
            AutoBottomSheet autoBottomSheet = this.clockCustomFontColorBottomSheetModal;
            if (autoBottomSheet != null && (baseTemplateView = this.baseTemplateView) != null) {
                baseTemplateView.cacheBottomSheet(autoBottomSheet);
            }
            BaseTemplateView baseTemplateView2 = this.baseTemplateView;
            if (baseTemplateView2 != null) {
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.miui.keyguard.editor.view.ClockTemplateItem");
                baseTemplateView2.switchClockTemplate(clockTemplateItem.getTemplateConfig());
            }
        }
    }

    public void onEditorSubUIDismissed() {
    }

    public void onEditorSubUIShown() {
    }

    public void onExitTransform() {
    }

    public void onHierarchyDisable() {
    }

    public void onHierarchyEnable() {
    }

    @Override // com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public void onMiuiClockInfoChange(ClockBean clockBean, boolean z) {
    }

    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        Iterator it = this.miuiClockViewInitListeners.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            Object next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            ((Runnable) next).run();
        }
        this.miuiClockViewInitListeners.clear();
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), Dispatchers.getMain(), null, new C00991(null), 2, null);
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0075  */
    /* JADX WARN: Code duplicated, block: B:29:0x0079  */
    public void onPreviewChanged(boolean z, boolean z2) {
        TextView textView;
        WallpaperInfo wallpaperInfo;
        WallpaperInfo wallpaperInfo2;
        if (z) {
            TextView textView2 = this.scaleTips;
            if (textView2 != null) {
                AnimatorKt.alphaTo$default(textView2, 0.0f, 0.0f, 0.0f, null, 14, null);
            }
            hideAllFrame(true);
            hideSignature(true);
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            this.enterOrientation = deviceUtil.getScreenOrientation(context);
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            this.enterScreenState = deviceUtil.getScreenStatus(context2);
            return;
        }
        TemplateConfig templateConfig = templateConfig();
        String resourceType = null;
        if (((templateConfig == null || (wallpaperInfo2 = templateConfig.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getResourceType()) != null) {
            WallpaperEditor.Companion companion = WallpaperEditor.Companion;
            TemplateConfig templateConfig2 = templateConfig();
            if (templateConfig2 != null && (wallpaperInfo = templateConfig2.getWallpaperInfo()) != null) {
                resourceType = wallpaperInfo.getResourceType();
            }
            if (companion.canScale(resourceType)) {
                textView = this.scaleTips;
                if (textView != null) {
                    AnimatorKt.alphaTo$default(textView, 1.0f, 0.0f, 0.0f, null, 14, null);
                }
            } else {
                TextView textView3 = this.scaleTips;
                if (textView3 != null) {
                    AnimatorKt.alphaTo$default(textView3, 0.0f, 0.0f, 0.0f, null, 14, null);
                }
            }
        } else {
            textView = this.scaleTips;
            if (textView != null) {
                AnimatorKt.alphaTo$default(textView, 1.0f, 0.0f, 0.0f, null, 14, null);
            }
        }
        hideSignature(false);
        int i = this.enterOrientation;
        DeviceUtil deviceUtil2 = DeviceUtil.INSTANCE;
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        boolean z3 = i != deviceUtil2.getScreenOrientation(context3);
        int i2 = this.enterScreenState;
        Context context4 = getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
        if (i2 != deviceUtil2.getScreenStatus(context4) || z3) {
            delayShowAllFrame();
        } else {
            if (this.allFrameShowing) {
                return;
            }
            showAllFrame();
        }
    }

    public void onScreenSizeChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
    }

    public void onScreenSizeChangedWhenEditable(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        TemplateEditFrameController.hideAll$default(getEditFrameController(), false, 1, null);
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), Dispatchers.getMain(), null, new C01001(null), 2, null);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onScrolledChanged(boolean z, boolean z2) {
        CombineEditListener.DefaultImpls.onScrolledChanged(this, z, z2);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onShowBottomSheet() {
        CombineEditListener.DefaultImpls.onShowBottomSheet(this);
    }

    public void onSignatureBottomSheetDismiss() {
        KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        keyboardUtils.hideSoftInput(context);
        clearSelectState();
        FontFilterView fontFilterView = this.signatureFontFilterView;
        if (fontFilterView != null) {
            fontFilterView.reset();
        }
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.onDismissBottomSheet();
        }
        this.mShow = false;
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onTemplateReady(int i) {
        CombineEditListener.DefaultImpls.onTemplateReady(this, i);
    }

    public final void popClockFontFilterDialog(final int i, final int i2) {
        View viewFindViewById = findViewById(i);
        if (viewFindViewById != null) {
            onClickClockFrame(viewFindViewById);
            EditorDialogTitleView clockStyleEditorView = getClockStyleEditorView();
            if (clockStyleEditorView != null) {
                clockStyleEditorView.switchTargetWithTitle(1);
                return;
            }
            return;
        }
        if (i2 < 5) {
            postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseClockView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BaseClockView.popClockFontFilterDialog$lambda$36(this.f$0, i, i2);
                }
            }, 100L);
            return;
        }
        Log.w("Keyguard-Editor-BaseClockView", "popFontFilterDialog skip, anchor view is null, anchorViewId=" + i);
    }

    public void popFontFilterDialog(int i) {
    }

    public Pair preProcessBitmapForPickColor(Bitmap bitmap, boolean z) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        return new Pair(null, Boolean.FALSE);
    }

    public void refreshColorPickupRect() {
    }

    public void removeClockLoadListener() {
        MiuiClockView miuiClockView = this.miuiClockView;
        if (miuiClockView != null) {
            miuiClockView.setLoadListener(null);
        }
    }

    public void resetAllBottomSheet() {
        AutoBottomSheet autoBottomSheet = this.clockBottomSheetModal;
        if (autoBottomSheet != null) {
            AutoBottomSheet.reset$default(autoBottomSheet, false, 1, null);
        }
        this.clockBottomSheetModal = null;
        AutoBottomSheet autoBottomSheet2 = this.clockCustomFontColorBottomSheetModal;
        if (autoBottomSheet2 != null) {
            AutoBottomSheet.reset$default(autoBottomSheet2, false, 1, null);
        }
        this.clockCustomFontColorBottomSheetModal = null;
        AutoBottomSheet autoBottomSheet3 = this.signatureSheetModal;
        if (autoBottomSheet3 != null) {
            AutoBottomSheet.reset$default(autoBottomSheet3, false, 1, null);
        }
        this.signatureSheetModal = null;
        AutoBottomSheet autoBottomSheet4 = this.signatureCustomColorBottomSheetModal;
        if (autoBottomSheet4 != null) {
            AutoBottomSheet.reset$default(autoBottomSheet4, false, 1, null);
        }
        this.signatureCustomColorBottomSheetModal = null;
        resetFilterView();
    }

    public void resetClockFontColor() {
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
        if (viewModel == null) {
            AutoBottomSheet autoBottomSheet = this.clockCustomFontColorBottomSheetModal;
            if (autoBottomSheet != null) {
                AutoBottomSheet.reset$default(autoBottomSheet, false, 1, null);
            }
            this.clockCustomFontColorBottomSheetModal = null;
            return;
        }
        FontFilterData primaryFontFilter = viewModel.getPrimaryFontFilter();
        FontFilterData fontFilterData = new FontFilterData(1, 0, -1, false, 8, null);
        if (effectDisable(primaryFontFilter)) {
            viewModel.setOldPrimaryFontFilter(new FontFilterData(primaryFontFilter.getFilterId(), primaryFontFilter.getColorId(), primaryFontFilter.getColor(), false, 8, null));
            FontFilterData secondaryFontFilter = viewModel.getSecondaryFontFilter();
            viewModel.setOldSecondaryFontFilter(new FontFilterData(secondaryFontFilter.getFilterId(), secondaryFontFilter.getColorId(), secondaryFontFilter.getColor(), false, 8, null));
            viewModel.updateFontFilter(0, fontFilterData);
            viewModel.updateFontFilter(1, fontFilterData);
        } else {
            FontFilterData oldPrimaryFontFilter = viewModel.getOldPrimaryFontFilter();
            FontFilterData oldSecondaryFontFilter = viewModel.getOldSecondaryFontFilter();
            if (oldPrimaryFontFilter != null && oldSecondaryFontFilter != null && !effectDisable(oldPrimaryFontFilter)) {
                viewModel.updateFontFilter(0, oldPrimaryFontFilter);
                viewModel.updateFontFilter(1, oldSecondaryFontFilter);
                viewModel.setOldPrimaryFontFilter(null);
                viewModel.setOldSecondaryFontFilter(null);
            }
        }
        AutoBottomSheet autoBottomSheet2 = this.clockCustomFontColorBottomSheetModal;
        if (autoBottomSheet2 != null) {
            AutoBottomSheet.reset$default(autoBottomSheet2, false, 1, null);
        }
        this.clockCustomFontColorBottomSheetModal = null;
    }

    public final void resetColorSelectionToAuto() {
        if (this.clockFontFilterView == null) {
            AbstractPopupEditor orCreateClockStyleEditor = getOrCreateClockStyleEditor();
            this.clockFontFilterView = orCreateClockStyleEditor != null ? orCreateClockStyleEditor.getFontFilterView() : null;
        }
        if (this.signatureFontFilterView == null) {
            SignatureEditor orCreateSignatureStyleEditor = getOrCreateSignatureStyleEditor();
            this.signatureFontFilterView = orCreateSignatureStyleEditor != null ? orCreateSignatureStyleEditor.getFontFilterView() : null;
        }
        FontFilterView fontFilterView = this.clockFontFilterView;
        if (fontFilterView != null) {
            fontFilterView.resetColorSelectionToAuto();
        }
        FontFilterView fontFilterView2 = this.signatureFontFilterView;
        if (fontFilterView2 != null) {
            fontFilterView2.resetColorSelectionToAuto();
        }
    }

    public final void resetFilterView() {
        FontFilterView fontFilterView = this.clockFontFilterView;
        if (fontFilterView != null) {
            fontFilterView.reset();
        }
        this.clockFontFilterView = null;
        FontFilterView fontFilterView2 = this.signatureFontFilterView;
        if (fontFilterView2 != null) {
            fontFilterView2.reset();
        }
        this.signatureFontFilterView = null;
    }

    public void resetPrimaryButtonBar() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.resetPrimaryButtonBar();
        }
    }

    public void resetSpecialBottomSheet(boolean z) {
    }

    public void resetStyleEditor() {
        this.clockStyleEditor = null;
        this.signatureStyleEditor = null;
    }

    public void restorePickPanelLift(int i) {
    }

    public void scaleTipsIsVisible(boolean z) {
        TextView textView = this.scaleTips;
        if (textView != null) {
            textView.setVisibility(z ? 0 : 8);
        }
    }

    public final void setBaseTemplateView(@Nullable BaseTemplateView baseTemplateView) {
        this.baseTemplateView = baseTemplateView;
    }

    public final void setClock(@Nullable View view) {
        this.clock = view;
    }

    public final void setClockBottomSheetModal(@Nullable AutoBottomSheet autoBottomSheet) {
        this.clockBottomSheetModal = autoBottomSheet;
    }

    public final void setClockCustomFontColorBottomSheetModal(@Nullable AutoBottomSheet autoBottomSheet) {
        this.clockCustomFontColorBottomSheetModal = autoBottomSheet;
    }

    public final void setClockFontFilterView(@Nullable FontFilterView fontFilterView) {
        this.clockFontFilterView = fontFilterView;
    }

    public final void setClockStyleEditor(@Nullable AbstractPopupEditor abstractPopupEditor) {
        this.clockStyleEditor = abstractPopupEditor;
    }

    public final void setClockTemplateChanged(boolean z) {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.markClockTemplateChanged(z);
        }
    }

    public final void setColorMode(@Nullable Integer num) {
        if (num != null) {
            getEditFrameController().setColorMode(num.intValue());
        }
    }

    public final void setCustomFontBottomSheet(@NotNull AutoBottomSheet bottomSheet) {
        Intrinsics.checkNotNullParameter(bottomSheet, "bottomSheet");
        this.clockCustomFontColorBottomSheetModal = bottomSheet;
    }

    public final void setDisplayClockEffect(int i) {
        this.displayClockEffect = i;
    }

    public final void setDualClock(boolean z) {
        this.isDualClock = z;
    }

    public final void setEditFragment(@Nullable EditFragment editFragment) {
        this.editFragment = editFragment;
    }

    public final void setExtraParameters(@Nullable Bundle bundle) {
        this.extraParameters = bundle;
    }

    public final void setFontStyleEditCallback(@Nullable FontStyleEditCallback fontStyleEditCallback) {
        this.fontStyleEditCallback = fontStyleEditCallback;
    }

    public final void setMShow(boolean z) {
        this.mShow = z;
    }

    public final void setMiuiClockView(@Nullable MiuiClockView miuiClockView) {
        this.miuiClockView = miuiClockView;
    }

    public final void setRealtimeEditor(@Nullable RealtimeStyleEditor realtimeStyleEditor) {
        this.realtimeEditor = realtimeStyleEditor;
    }

    public final void setScaleTips(@Nullable TextView textView) {
        this.scaleTips = textView;
    }

    public final void setScaleTipsColorMode(@Nullable Integer num) {
        if (num != null) {
            updateTextViewWithColorMode(num.intValue());
        }
    }

    public final void setSignatureCustomColorBottomSheetModal(@Nullable AutoBottomSheet autoBottomSheet) {
        this.signatureCustomColorBottomSheetModal = autoBottomSheet;
    }

    public final void setSignatureFontFilterView(@Nullable FontFilterView fontFilterView) {
        this.signatureFontFilterView = fontFilterView;
    }

    public final void setSignatureSheetModal(@Nullable AutoBottomSheet autoBottomSheet) {
        this.signatureSheetModal = autoBottomSheet;
    }

    public final void setSignatureStyleEditor(@Nullable SignatureEditor signatureEditor) {
        this.signatureStyleEditor = signatureEditor;
    }

    public final void setStyleEditorFontColor(int i) {
        this.styleEditorFontColor = i;
    }

    public final void setSuccessUpdateClockView(boolean z) {
        this.successUpdateClockView = z;
    }

    public int sheetMarginBottom() {
        return getResources().getDimensionPixelSize(com.miui.keyguard.editor.R.dimen.kg_clock_bottom_sheet_margin_bottom_common);
    }

    public void showAllFrame() {
        getEditFrameController().showAll();
    }

    public final boolean showBottomSheetModal() {
        return this.mShow;
    }

    public Object showClockEditBorderView(Continuation continuation) {
        return showClockEditBorderView$suspendImpl(this, continuation);
    }

    public Rect smaleScreenWithNotificationAvoidRect() {
        return new Rect();
    }

    public final void startPickColorFromWallpaper(Bitmap bitmap, boolean z, boolean z2, Rect rect) {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            baseTemplateView.startPickColorFromWallpaper(bitmap, z, z2, rect);
        }
    }

    public boolean supportGradientBlur() {
        return false;
    }

    public boolean supportHierarchy() {
        return true;
    }

    public final void switchTips(boolean z) {
        TextView textView = this.scaleTips;
        if (textView != null) {
            AnimatorKt.alphaTo$default(textView, z ? 1.0f : 0.0f, 0.0f, 0.0f, null, 14, null);
        }
    }

    public TemplateConfig templateConfig() {
        CommonConfig commonConfigTemplateConfig;
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView == null || (commonConfigTemplateConfig = baseTemplateView.templateConfig()) == null) {
            return null;
        }
        return commonConfigTemplateConfig.getLockscreenInfo();
    }

    public Rect templateHierarchyAvoidRect() {
        return new Rect(0, 0, 0, 0);
    }

    public Set unSupportHierarchyTypes() {
        return SetsKt.emptySet();
    }

    public final void updateBottomSheet(AutoBottomSheet[] autoBottomSheetArr) {
        if (autoBottomSheetArr != null) {
            if (autoBottomSheetArr.length == 0) {
                autoBottomSheetArr = null;
            }
            if (autoBottomSheetArr != null) {
                AutoBottomSheet autoBottomSheet = autoBottomSheetArr[0];
                this.clockBottomSheetModal = autoBottomSheet;
                this.clockCustomFontColorBottomSheetModal = autoBottomSheetArr[1];
                if (autoBottomSheet != null) {
                    autoBottomSheet.setOnDismissListener(new BottomSheetModal.OnDismissListener() { // from class: com.miui.keyguard.editor.edit.base.BaseClockView$$ExternalSyntheticLambda5
                        @Override // miuix.bottomsheet.BottomSheetModal.OnDismissListener
                        public final void onDismiss() {
                            this.f$0.onClockBottomSheetDismiss();
                        }
                    });
                }
            }
        }
    }

    public abstract void updateClockColor(ColorData colorData);

    public final void updateIsDualClock(boolean z) {
        this.isDualClock = z;
    }

    public final void updateTextViewWithColorMode(int i) {
        TextView textView = this.scaleTips;
        if (textView != null) {
            textView.setTextColor(getContext().getColor(i == 0 ? com.miui.keyguard.editor.R.color.kg_white_60 : com.miui.keyguard.editor.R.color.kg_black_80));
        }
    }

    public boolean wallpaperHasChanged() {
        BaseTemplateView baseTemplateView = this.baseTemplateView;
        if (baseTemplateView != null) {
            return baseTemplateView.isWallpaperHasChanged();
        }
        return false;
    }

    public boolean wantsDeferredVisibilityOnSwitch() {
        return false;
    }
}
