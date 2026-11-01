package com.miui.keyguard.editor.edit.allinone;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.view.WindowInsetsCompat;
import com.miui.clock.MiuiClockControllerExtKt;
import com.miui.clock.MiuiClockView;
import com.miui.clock.allInOne.AllInOneBaseInfo;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.allInOne.AllInOneUtil;
import com.miui.clock.allInOne.TimeView;
import com.miui.clock.classic.ClassicTextAreaView;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.MiuiClassicClockInfoBase;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.avoid.ClockDepthAvoidRuleUtils;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TransitionKt;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.ScreenshotSourceKt;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.migration.AllInOneClockMigration;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.base.CombineEditView;
import com.miui.keyguard.editor.edit.base.DynamicSwitchButton;
import com.miui.keyguard.editor.edit.base.HomeTemplateView;
import com.miui.keyguard.editor.edit.base.MultiTouchFrameLayout;
import com.miui.keyguard.editor.edit.base.TemplateButtonBar;
import com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView;
import com.miui.keyguard.editor.edit.clock.ClassicSelectorBean;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.handler.ColorPickHandler;
import com.miui.keyguard.editor.edit.color.picker.AllInOneAutoColorPicker;
import com.miui.keyguard.editor.edit.style.AllInOneStyleEditor;
import com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.edit.wallpaper.ObserverClickListener;
import com.miui.keyguard.editor.edit.wallpaper.ObserverGestureListener;
import com.miui.keyguard.editor.homepage.util.CrossListFolmeUtil;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.utils.ClockUtil;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.HierarchyImageAvoidController;
import com.miui.keyguard.editor.utils.KeyboardUtils;
import com.miui.keyguard.editor.utils.TalkBackUtil;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.view.AutoBottomSheet;
import com.miui.keyguard.editor.view.KgFrame;
import com.miui.keyguard.editor.view.KgFrameParam;
import com.miui.maml.data.VariableNames;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.onetrack.util.ac;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ValueProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.bottomsheet.BottomSheetModal;
import org.jetbrains.annotations.NotNull;

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
/* JADX INFO: compiled from: AllInOneTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class AllInOneTemplateView extends AbstractClassicTemplateView {
    public static final Companion Companion = new Companion(null);
    public final String COLON_TRANSITION;
    public final float LONG_PRESS_ZOOM_SPRING_DAMPING;
    public final float LONG_PRESS_ZOOM_SPRING_STIFFNESS;
    public final float LONG_PRESS_ZOOM_TARGET;
    public int appliedImeBottom;
    public final Rect bottomHandleRect;
    public AllInOneClockStyle.Region cachedRegion;
    public float clockBottomRatio;
    public float clockTopRatio;
    public ColonAarState colonAarState;
    public long colonAnimGeneration;
    public final ValueProperty colonTransitionAnimation;
    public final float colonTransitionDownState;
    public final float colonTransitionUpState;
    public Rect colorPickupRect;
    public final Paint cornerHandlePaint;
    public final Rect cornerHandleRect;
    public ImageView cornerHandleView;
    public int currentAllVisibleFramesBottom;
    public int currentAllVisibleFramesTop;
    public int currentClockFrameBottom;
    public int currentClockFrameLeft;
    public int currentClockFrameRight;
    public int currentClockFrameTop;
    public int currentDateFrameTop;
    public int currentSignatureFrameBottom;
    public Runnable deferredColonRunnable;
    public final AnimState downTransState;
    public final float dragDampingFactor;
    public int dragInteractionToken;
    public int dragMode;
    public final boolean dragResizeEnabled;
    public MultiTouchFrameLayout editorOverlayLayer;
    public boolean forwardingToWallpaper;
    public final AllInOneTemplateView$handleBottomProperty$1 handleBottomProperty;
    public final AllInOneTemplateView$handleLeftProperty$1 handleLeftProperty;
    public final AllInOneTemplateView$handleRightProperty$1 handleRightProperty;
    public final AllInOneTemplateView$handleTopProperty$1 handleTopProperty;
    public final EaseManager.EaseStyle hideTransEase;
    public WindowInsetsAnimationCompat.Callback imeAnimCallback;
    public boolean imeHideAnimStarted;
    public Integer imeLastBottom;
    public float initialCenterX;
    public boolean isButtonMovedByUser;
    public boolean isClockLayoutSwitchAnimRunning;
    public boolean isColonTransitionAnimRunning;
    public boolean isDoubleRow;
    public boolean isFadeOutCancelled;
    public boolean isSpringBounceAnimRunning;
    public View keyboardHideRoot;
    public boolean keyboardLiftApplied;
    public Rect lastCalculatedEditFrameRect;
    public final Rect lastRegionFrame;
    public float lastTouchX;
    public float lastTouchY;
    public Integer longPressBottomExceedPairTopTarget;
    public float longPressOverScrollY;
    public final Runnable longPressRunnable;
    public final long longPressTimeout;
    public Integer longPressTopExceedPairBottomTarget;
    public float longPressZoomScale;
    public KgFrame mKgTextAreaFrame;
    public KgFrame mTimeAreaFrame;
    public View mTimeAreaView;
    public Rect maxDragAllowedBounds;
    public Rect maxSafeFrameRect;
    public Rect maxSafeRect;
    public final AllInOneTemplateView$overScrollYProperty$1 overScrollYProperty;
    public boolean pendingForceUpdateParams;
    public int pendingImeBottom;
    public boolean pendingLongPress;
    public Integer pendingPickLiftRestore;
    public int pickLiftClickedViewId;
    public boolean pickPanelLiftApplied;
    public boolean prevHeightLessThanMin;
    public boolean prevIsDoubleRow;
    public int setWay;
    public boolean shouldShowColon;
    public final EaseManager.EaseStyle showTransEase;
    public int signatureGapAndHeight;
    public boolean signatureHasContent;
    public int signatureLines;
    public boolean signatureVisible;
    public int timeHeight;
    public int timeWidth;
    public final Rect tmpClockRect;
    public final Lazy touchSlop$delegate;
    public final AnimState upTransState;
    public final float[] zoomPivotScreen;

    /* JADX INFO: compiled from: AllInOneTemplateView.kt */
    @Metadata
    public enum ColonAarState {
        PENDING,
        NOTIFIED;

        public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    }

    /* JADX INFO: compiled from: AllInOneTemplateView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r7v14, types: [com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$handleLeftProperty$1] */
    /* JADX WARN: Type inference failed for: r7v15, types: [com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$handleTopProperty$1] */
    /* JADX WARN: Type inference failed for: r7v16, types: [com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$handleRightProperty$1] */
    /* JADX WARN: Type inference failed for: r7v17, types: [com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$handleBottomProperty$1] */
    /* JADX WARN: Type inference failed for: r7v18, types: [com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$overScrollYProperty$1] */
    public AllInOneTemplateView(@NotNull final Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.colonAarState = ColonAarState.PENDING;
        this.dragResizeEnabled = FontFilterKt.getSUPPORT_EDITOR_VARIABLE_FONT();
        this.cornerHandleRect = new Rect();
        this.bottomHandleRect = new Rect();
        Paint paint = new Paint();
        paint.setColor(-1);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(255);
        this.cornerHandlePaint = paint;
        this.longPressTimeout = ViewConfiguration.getLongPressTimeout();
        this.touchSlop$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Integer.valueOf(AllInOneTemplateView.touchSlop_delegate$lambda$3(context));
            }
        });
        this.longPressRunnable = new Runnable() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                AllInOneTemplateView.longPressRunnable$lambda$4(this.f$0);
            }
        };
        this.LONG_PRESS_ZOOM_TARGET = 1.03f;
        this.LONG_PRESS_ZOOM_SPRING_STIFFNESS = 1.0f;
        this.LONG_PRESS_ZOOM_SPRING_DAMPING = 0.35f;
        this.longPressZoomScale = 1.0f;
        this.zoomPivotScreen = new float[2];
        this.signatureVisible = true;
        this.pendingForceUpdateParams = true;
        this.lastRegionFrame = new Rect();
        this.tmpClockRect = new Rect();
        this.COLON_TRANSITION = "colon_transition";
        ValueProperty valueProperty = new ValueProperty("colon_transition", 0.01f);
        this.colonTransitionAnimation = valueProperty;
        this.colonTransitionUpState = 1.0f;
        AnimState animState = new AnimState();
        animState.add(valueProperty, this.colonTransitionDownState, new long[0]);
        this.downTransState = animState;
        AnimState animState2 = new AnimState();
        animState2.add((FloatProperty) valueProperty, 1.0f, new long[0]);
        this.upTransState = animState2;
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 0.95f, 0.28f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        this.showTransEase = style;
        EaseManager.EaseStyle style2 = EaseManager.getStyle(-2, 0.95f, 0.35f);
        Intrinsics.checkNotNullExpressionValue(style2, "getStyle(...)");
        this.hideTransEase = style2;
        this.maxSafeRect = new Rect(0, 0, 0, 0);
        this.maxSafeFrameRect = new Rect(0, 0, 0, 0);
        this.maxDragAllowedBounds = new Rect(0, 0, 0, 0);
        this.dragDampingFactor = 0.3f;
        this.colorPickupRect = new Rect(0, 0, 0, 0);
        this.handleLeftProperty = new FloatProperty<AllInOneTemplateView>() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$handleLeftProperty$1
            @Override // miuix.animation.property.FloatProperty
            public float getValue(AllInOneTemplateView obj) {
                Intrinsics.checkNotNullParameter(obj, "obj");
                return obj.currentClockFrameLeft;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(AllInOneTemplateView obj, float f) {
                Intrinsics.checkNotNullParameter(obj, "obj");
                obj.currentClockFrameLeft = (int) f;
            }
        };
        this.handleTopProperty = new FloatProperty<AllInOneTemplateView>() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$handleTopProperty$1
            @Override // miuix.animation.property.FloatProperty
            public float getValue(AllInOneTemplateView obj) {
                Intrinsics.checkNotNullParameter(obj, "obj");
                return obj.currentClockFrameTop;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(AllInOneTemplateView obj, float f) {
                Intrinsics.checkNotNullParameter(obj, "obj");
                obj.currentClockFrameTop = (int) f;
            }
        };
        this.handleRightProperty = new FloatProperty<AllInOneTemplateView>() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$handleRightProperty$1
            @Override // miuix.animation.property.FloatProperty
            public float getValue(AllInOneTemplateView obj) {
                Intrinsics.checkNotNullParameter(obj, "obj");
                return obj.currentClockFrameRight;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(AllInOneTemplateView obj, float f) {
                Intrinsics.checkNotNullParameter(obj, "obj");
                obj.currentClockFrameRight = (int) f;
            }
        };
        this.handleBottomProperty = new FloatProperty<AllInOneTemplateView>() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$handleBottomProperty$1
            @Override // miuix.animation.property.FloatProperty
            public float getValue(AllInOneTemplateView obj) {
                Intrinsics.checkNotNullParameter(obj, "obj");
                return obj.currentClockFrameBottom;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(AllInOneTemplateView obj, float f) {
                Intrinsics.checkNotNullParameter(obj, "obj");
                obj.currentClockFrameBottom = (int) f;
            }
        };
        this.overScrollYProperty = new FloatProperty<AllInOneTemplateView>() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$overScrollYProperty$1
            @Override // miuix.animation.property.FloatProperty
            public float getValue(AllInOneTemplateView obj) {
                Intrinsics.checkNotNullParameter(obj, "obj");
                return obj.longPressOverScrollY;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(AllInOneTemplateView obj, float f) {
                Intrinsics.checkNotNullParameter(obj, "obj");
                if (obj.dragMode == 3 || obj.dragMode == 0) {
                    obj.longPressOverScrollY = f;
                    obj.applyLongPressOverScrollTranslation(f);
                }
            }
        };
        this.pickLiftClickedViewId = R.id.kg_time_area_frame;
    }

    public static final void animateHandlesVisibility$fadeIn(AnimState animState, AnimState animState2, AnimConfig animConfig, AllInOneTemplateView allInOneTemplateView, View view) {
        if (view == null || view.getVisibility() == 0) {
            return;
        }
        view.setVisibility(0);
        view.setAlpha(0.0f);
        Folme.useAt(view).state().setTo(animState).to(animState2, animConfig);
        if (allInOneTemplateView.dragMode == 0) {
            Object systemService = allInOneTemplateView.getContext().getSystemService("accessibility");
            if (TalkBackUtil.INSTANCE.isTalkBackEnabled(systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null)) {
                view.requestFocus();
                view.sendAccessibilityEvent(8);
            }
        }
    }

    public static final void animateHandlesVisibility$fadeOut(AnimConfig animConfig, View view) {
        if (view != null && view.getVisibility() == 0) {
            Folme.useAt(view).state().to(new AnimState("hide_end").add(ViewProperty.ALPHA, 0.0f, new long[0]), animConfig);
            view.setVisibility(4);
            view.setAlpha(1.0f);
        }
    }

    public static final void applyKeyboardLift$animateLift(View view, float f) {
        if (view == null) {
            return;
        }
        IStateStyle iStateStyleState = Folme.useAt(view).state();
        AnimState animState = new AnimState("kb_lift_start");
        ViewProperty viewProperty = ViewProperty.TRANSLATION_Y;
        iStateStyleState.setTo(animState.add(viewProperty, view.getTranslationY(), new long[0])).to(new AnimState("kb_lift_end").add(viewProperty, f, new long[0]), new AnimConfig().setEase(EaseManager.getStyle(-2, 1.0f, 0.28f)));
    }

    public static /* synthetic */ void applyKeyboardLift$default(AllInOneTemplateView allInOneTemplateView, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        allInOneTemplateView.applyKeyboardLift(z, i);
    }

    public static final void applyPickPanelLift$animateLift$32(View view, Ref.FloatRef floatRef) {
        if (view == null) {
            return;
        }
        IStateStyle iStateStyleState = Folme.useAt(view).state();
        AnimState animState = new AnimState("pick_lift_start");
        ViewProperty viewProperty = ViewProperty.TRANSLATION_Y;
        iStateStyleState.setTo(animState.add(viewProperty, view.getTranslationY(), new long[0])).to(new AnimState("pick_lift_end").add(viewProperty, floatRef.element, new long[0]), new AnimConfig().setEase(EaseManager.getStyle(-2, 1.0f, 0.28f)));
    }

    public static /* synthetic */ void applyPickPanelLift$default(AllInOneTemplateView allInOneTemplateView, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = R.id.kg_time_area_frame;
        }
        allInOneTemplateView.applyPickPanelLift(z, i);
    }

    public static final boolean decideHierarchyLayerPosition$lambda$49(AllInOneTemplateView allInOneTemplateView, View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 1 || view.hasFocus()) {
            return false;
        }
        KgFrame mKgSignatureAreaFrame = allInOneTemplateView.getMKgSignatureAreaFrame();
        if (mKgSignatureAreaFrame != null) {
            mKgSignatureAreaFrame.performClick();
        }
        return true;
    }

    public static final void decideHierarchyLayerPosition$lambda$50(AllInOneClockStyle.Region region, AllInOneTemplateView allInOneTemplateView, View view, boolean z) {
        String string;
        String str = region == AllInOneClockStyle.Region.REGION_2 ? "HOUR" : "MINUTE";
        boolean z2 = allInOneTemplateView.keyboardLiftApplied;
        boolean z3 = view instanceof EditText;
        EditText editText = z3 ? (EditText) view : null;
        Editable text = editText != null ? editText.getText() : null;
        boolean z4 = text == null || text.length() == 0;
        EditText editText2 = z3 ? (EditText) view : null;
        Log.i("AllInOneTemplateView", "Signature FocusChange(target=" + str + "): hasFocus=" + z + ", , keyboardLiftApplied=" + z2 + ", textEmpty=" + z4 + ", hint='" + ((Object) (editText2 != null ? editText2.getHint() : null)) + "'");
        MultiTouchFrameLayout multiTouchFrameLayout = allInOneTemplateView.editorOverlayLayer;
        if (multiTouchFrameLayout != null) {
            multiTouchFrameLayout.updateNeedDispatch(z);
        }
        MultiTouchFrameLayout multiTouchFrameLayout2 = (MultiTouchFrameLayout) allInOneTemplateView.getForeContentLayer();
        if (multiTouchFrameLayout2 != null) {
            multiTouchFrameLayout2.updateNeedDispatch(z);
        }
        String str2 = "";
        if (z) {
            Log.i("AllInOneTemplateView", "Signature EditText gained focus: hide other frames, register IME listener");
            EditText signatureAreaView = allInOneTemplateView.getSignatureAreaView();
            if (signatureAreaView != null) {
                signatureAreaView.setHint("");
            }
            BaseClockView.hideAllFrame$default(allInOneTemplateView, false, 1, null);
            KgFrame mKgSignatureAreaFrame = allInOneTemplateView.getMKgSignatureAreaFrame();
            if (mKgSignatureAreaFrame != null) {
                mKgSignatureAreaFrame.setSelected(true);
            }
            KgFrame mKgSignatureAreaFrame2 = allInOneTemplateView.getMKgSignatureAreaFrame();
            if (mKgSignatureAreaFrame2 != null) {
                mKgSignatureAreaFrame2.setVisibility(0);
            }
            allInOneTemplateView.registerOneShotKeyboardHideListener();
            return;
        }
        EditText editText3 = z3 ? (EditText) view : null;
        Editable text2 = editText3 != null ? editText3.getText() : null;
        Log.i("AllInOneTemplateView", "Signature EditText lost focus: show all frames; textEmpty=" + (text2 == null || text2.length() == 0));
        KgFrame mKgSignatureAreaFrame3 = allInOneTemplateView.getMKgSignatureAreaFrame();
        if (mKgSignatureAreaFrame3 != null) {
            mKgSignatureAreaFrame3.setSelected(false);
        }
        EditText editText4 = z3 ? (EditText) view : null;
        Editable text3 = editText4 != null ? editText4.getText() : null;
        if (text3 == null || text3.length() == 0) {
            EditText editText5 = z3 ? (EditText) view : null;
            if (editText5 != null) {
                Context context = ((EditText) view).getContext();
                if (context != null && (string = context.getString(com.miui.clock.R.string.miui_clock_classic_signature_area_hint)) != null) {
                    str2 = string;
                }
                editText5.setHint(str2);
            }
        }
        allInOneTemplateView.showAllFrame();
        View clickView = allInOneTemplateView.getClickView();
        if (clickView != null) {
            clickView.setVisibility(8);
        }
        allInOneTemplateView.controlTopButtonState(true);
    }

    public static /* synthetic */ void doColonTransitionAnim$default(AllInOneTemplateView allInOneTemplateView, boolean z, boolean z2, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            z2 = false;
        }
        if ((i & 4) != 0) {
            j = 0;
        }
        allInOneTemplateView.doColonTransitionAnim(z, z2, j);
    }

    private final TimeView getBackMinView() {
        MiuiClockView miuiClockView = getMiuiClockView();
        View iClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.FULL_MINUTE) : null;
        if (iClockView instanceof TimeView) {
            return (TimeView) iClockView;
        }
        return null;
    }

    private final View getBackTimeGroup() {
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            return miuiClockView.getIClockView(ClockViewType.TIME_GROUP);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float getColonHalfWidth() {
        TimeView colonView = getColonView();
        if (colonView != null) {
            return colonView.getTextWidth() / 2.0f;
        }
        return 0.0f;
    }

    private final TimeView getColonView() {
        MiuiClockView miuiClockViewCurrentMinuteOwner = currentMinuteOwner();
        View iClockView = miuiClockViewCurrentMinuteOwner != null ? miuiClockViewCurrentMinuteOwner.getIClockView(ClockViewType.FULL_COLON) : null;
        if (iClockView instanceof TimeView) {
            return (TimeView) iClockView;
        }
        return null;
    }

    public static final CharSequence getDepthLayerOrder$lambda$52(View view, View view2, View view3, View view4, View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        if (v == view) {
            return "back(hour)";
        }
        if (v == view2) {
            return "fore(minute)";
        }
        if (v == view3) {
            return "hierarchy";
        }
        if (v == view4) {
            return "depthVideo";
        }
        String simpleName = v.getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        return simpleName;
    }

    public static final void getEditFrames$lambda$29$lambda$27(ImageView imageView, View view) {
        Object systemService = imageView.getContext().getSystemService("accessibility");
        if (TalkBackUtil.INSTANCE.isTalkBackEnabled(systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null)) {
            imageView.announceForAccessibility(imageView.getResources().getString(R.string.kg_talkback_slide_up_or_down));
        }
    }

    private final View getForeTimeGroup() {
        MiuiClockView foreMiuiClockView = getForeMiuiClockView();
        if (foreMiuiClockView != null) {
            return foreMiuiClockView.getIClockView(ClockViewType.TIME_GROUP);
        }
        return null;
    }

    private final TimeView getHourView() {
        MiuiClockView miuiClockView = getMiuiClockView();
        View iClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.FULL_HOUR) : null;
        if (iClockView instanceof TimeView) {
            return (TimeView) iClockView;
        }
        return null;
    }

    private final Rect getMaxDragAllowedRect() {
        int iCoerceAtLeast;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        Rect rectCurrentDisplaySize = deviceUtil.currentDisplaySize(context);
        int iWidth = rectCurrentDisplaySize.width();
        int iHeight = rectCurrentDisplaySize.height();
        int dimen = DeviceConfig.getDimen(R.dimen.kg_all_in_one_drag_top_margin, getContext());
        int dimen2 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_drag_bottom_margin, getContext());
        int dimen3 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_drag_icon_gap, getContext());
        BaseTemplateView baseTemplateView = getBaseTemplateView();
        TemplateButtonBar primaryButtonBar = baseTemplateView != null ? baseTemplateView.getPrimaryButtonBar() : null;
        DynamicSwitchButton dynamicSwitchButton = primaryButtonBar != null ? primaryButtonBar.getDynamicSwitchButton() : null;
        if (dynamicSwitchButton != null && dynamicSwitchButton.isShown() && dynamicSwitchButton.getParent() == primaryButtonBar.getDynamicButtonGroup()) {
            int[] iArr = new int[2];
            dynamicSwitchButton.getLocationOnScreen(iArr);
            int i = iArr[1] - dimen3;
            iCoerceAtLeast = (i <= dimen3 + dimen || i > iHeight) ? RangesKt.coerceAtLeast(iHeight - dimen2, dimen + 1) : RangesKt.coerceAtLeast(i, dimen + 1);
        } else {
            iCoerceAtLeast = RangesKt.coerceAtLeast(iHeight - dimen2, dimen + 1);
        }
        return new Rect(0, dimen, iWidth, iCoerceAtLeast);
    }

    private final TimeView getMinView() {
        MiuiClockView miuiClockViewCurrentMinuteOwner = currentMinuteOwner();
        View iClockView = miuiClockViewCurrentMinuteOwner != null ? miuiClockViewCurrentMinuteOwner.getIClockView(ClockViewType.FULL_MINUTE) : null;
        if (iClockView instanceof TimeView) {
            return (TimeView) iClockView;
        }
        return null;
    }

    private final int getTouchSlop() {
        return ((Number) this.touchSlop$delegate.getValue()).intValue();
    }

    public static final void longPressRunnable$lambda$4(AllInOneTemplateView allInOneTemplateView) {
        int i;
        if (!allInOneTemplateView.pendingLongPress || allInOneTemplateView.forwardingToWallpaper || (i = allInOneTemplateView.dragMode) == 1 || i == 2) {
            return;
        }
        allInOneTemplateView.dragMode = 3;
        allInOneTemplateView.dragInteractionToken++;
        allInOneTemplateView.setDragBlocking(true);
        allInOneTemplateView.pendingLongPress = false;
        allInOneTemplateView.startLongPressZoom(true);
        Log.i("AllInOneTemplateView", "longPressRunnable: long press");
        allInOneTemplateView.performHapticFeedback(0);
    }

    public static final void onClick$lambda$33(int i, AllInOneTemplateView allInOneTemplateView) {
        Log.d("AllInOneTemplateView", "onClick: onAnimationStart -> applyPickPanelLift, clickedViewId=" + i);
        allInOneTemplateView.pickLiftClickedViewId = i;
        allInOneTemplateView.applyPickPanelLift(true, i);
    }

    public static final void onMiuiClockViewCreated$lambda$17$lambda$16(AllInOneTemplateView allInOneTemplateView) {
        Editable text;
        String string;
        int signatureLines;
        KgFrame mKgSignatureAreaFrame;
        EditText signatureAreaView = allInOneTemplateView.getSignatureAreaView();
        if (signatureAreaView == null || !signatureAreaView.isAttachedToWindow() || (text = signatureAreaView.getText()) == null || (string = text.toString()) == null || string.length() == 0 || (signatureLines = AllInOneUtil.INSTANCE.getSignatureLines(signatureAreaView, string)) == allInOneTemplateView.signatureLines) {
            return;
        }
        allInOneTemplateView.signatureLines = signatureLines;
        KgFrame mKgSignatureAreaFrame2 = allInOneTemplateView.getMKgSignatureAreaFrame();
        if (mKgSignatureAreaFrame2 != null) {
            mKgSignatureAreaFrame2.setFullyRounded(allInOneTemplateView.signatureLines != 2);
        }
        if (allInOneTemplateView.signatureLines == 2 && (mKgSignatureAreaFrame = allInOneTemplateView.getMKgSignatureAreaFrame()) != null) {
            mKgSignatureAreaFrame.setBorderRadius(DeviceConfig.getDimen(R.dimen.kg_all_in_one_signature_frame_border_radius_two_line, allInOneTemplateView.getContext()));
        }
        allInOneTemplateView.signatureGapAndHeight = DeviceConfig.getDimen(com.miui.clock.R.dimen.miui_all_in_one_gap_between_time_and_sigature, allInOneTemplateView.getContext()) + (signatureLines == 2 ? DeviceConfig.getDimen(com.miui.clock.R.dimen.miui_all_in_one_signature_height_two_line, allInOneTemplateView.getContext()) - DeviceConfig.getDimen(R.dimen.kg_all_in_one_signature_height_two_line_offset, allInOneTemplateView.getContext()) : DeviceConfig.getDimen(com.miui.clock.R.dimen.miui_all_in_one_signature_height_one_line, allInOneTemplateView.getContext()));
        allInOneTemplateView.generateEditFrames();
    }

    public static final WindowInsetsCompat registerOneShotKeyboardHideListener$lambda$47(AllInOneTemplateView allInOneTemplateView, View view, WindowInsetsCompat insets) {
        int i;
        Intrinsics.checkNotNullParameter(view, "<unused var>");
        Intrinsics.checkNotNullParameter(insets, "insets");
        int i2 = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;
        Log.d("AllInOneTemplateView", "OnApplyWindowInsets: imeBottom=" + i2 + ", appliedImeBottom=" + allInOneTemplateView.appliedImeBottom + ", keyboardLiftApplied=" + allInOneTemplateView.keyboardLiftApplied);
        if (i2 > 0) {
            allInOneTemplateView.pendingImeBottom = i2;
            boolean z = allInOneTemplateView.keyboardLiftApplied;
            if (z && i2 != (i = allInOneTemplateView.appliedImeBottom)) {
                Log.d("AllInOneTemplateView", "OnApplyWindowInsets: imeBottom changed " + i + "->" + i2 + ", reapplying");
                allInOneTemplateView.keyboardLiftApplied = false;
                allInOneTemplateView.applyKeyboardLift(true, i2);
                return insets;
            }
            if (!z && allInOneTemplateView.isValidImeBottom(i2)) {
                allInOneTemplateView.applyKeyboardLift(true, i2);
            }
        }
        return insets;
    }

    public static final void resetLiftTranslations$reset(View view) {
        if (view == null) {
            return;
        }
        view.setTranslationY(0.0f);
    }

    private final void setAllEditorColonShow(boolean z) {
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            miuiClockView.setEditorColonShow(z);
        }
        MiuiClockView foreMiuiClockView = getForeMiuiClockView();
        if (foreMiuiClockView != null) {
            foreMiuiClockView.setEditorColonShow(z);
        }
    }

    private final void setAllEditorColonViewVisible(boolean z) {
        if (z) {
            MiuiClockView miuiClockViewCurrentMinuteOwner = currentMinuteOwner();
            if (miuiClockViewCurrentMinuteOwner != null) {
                miuiClockViewCurrentMinuteOwner.setEditorColonViewVisible(true);
                return;
            }
            return;
        }
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            miuiClockView.setEditorColonViewVisible(false);
        }
        MiuiClockView foreMiuiClockView = getForeMiuiClockView();
        if (foreMiuiClockView != null) {
            foreMiuiClockView.setEditorColonViewVisible(false);
        }
    }

    private final void setAllMinuteColonAlpha(float f) {
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView != null) {
            miuiClockView.setMinuteColonAlpha(f);
        }
        MiuiClockView foreMiuiClockView = getForeMiuiClockView();
        if (foreMiuiClockView != null) {
            foreMiuiClockView.setMinuteColonAlpha(f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setDragBlocking(boolean z) {
        CombinedWallpaperView combinedWallpaperView;
        Log.d("AllInOneTemplateView", "setDragBlocking: blocking=" + z + ", dragMode=" + this.dragMode);
        BaseTemplateView baseTemplateView = getBaseTemplateView();
        if (baseTemplateView == null || (combinedWallpaperView = (CombinedWallpaperView) baseTemplateView.getWallpaperLayer()) == null) {
            return;
        }
        combinedWallpaperView.setBlockSelfGestures(z);
    }

    private final void setFontStyle(Integer num) {
        if (num != null) {
            getCurrentClockBean().setFontStyle(num.intValue());
            getForeClockBean().setFontStyle(num.intValue());
            onClockInfoUpdate();
        }
    }

    private final void setGlassTransparency(float f) {
        getCurrentClockBean().setGlassTransparency(Float.valueOf(f));
        getForeClockBean().setGlassTransparency(Float.valueOf(f));
        onClockInfoUpdate();
    }

    private final void setHollowStyle(int i) {
        getCurrentClockBean().setHollowStyle(i);
        getForeClockBean().setHollowStyle(i);
        onClockInfoUpdate();
    }

    public static /* synthetic */ void startColonShowAnimWithFallback$default(AllInOneTemplateView allInOneTemplateView, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = 0;
        }
        allInOneTemplateView.startColonShowAnimWithFallback(j);
    }

    public static final void startColonShowAnimWithFallback$lambda$68(AllInOneTemplateView allInOneTemplateView, long j) {
        allInOneTemplateView.deferredColonRunnable = null;
        float colonHalfWidth = allInOneTemplateView.getColonHalfWidth();
        if (colonHalfWidth > 0.0f) {
            allInOneTemplateView.setAllClockTranslationX(colonHalfWidth, -colonHalfWidth);
        }
        allInOneTemplateView.doColonTransitionAnim(false, true, Math.max(0L, j - 16));
    }

    public static final Bitmap startPickColor$lambda$56(AllInOneTemplateView allInOneTemplateView) {
        Object objM1061constructorimpl;
        WallpaperInfo wallpaperInfo;
        try {
            Result.Companion companion = Result.Companion;
            BaseTemplateView baseTemplateView = allInOneTemplateView.getBaseTemplateView();
            objM1061constructorimpl = Result.m1061constructorimpl(baseTemplateView != null ? baseTemplateView.getPickColorBitmap() : null);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            Log.w("AllInOneTemplateView", "startPickColor: getPickColorBitmap failed", thM1063exceptionOrNullimpl);
        }
        if (Result.m1065isFailureimpl(objM1061constructorimpl)) {
            objM1061constructorimpl = null;
        }
        Bitmap bitmap = (Bitmap) objM1061constructorimpl;
        if (bitmap != null) {
            return bitmap;
        }
        TemplateConfig templateConfig = allInOneTemplateView.templateConfig();
        if (templateConfig == null || (wallpaperInfo = templateConfig.getWallpaperInfo()) == null) {
            return null;
        }
        return wallpaperInfo.getCurrentWallpaper();
    }

    public static final void startPickColor$lambda$57(AllInOneTemplateView allInOneTemplateView, Rect rect, Bitmap bitmap) {
        try {
            allInOneTemplateView.startPickColorFromWallpaper(bitmap, true, false, rect);
        } catch (Exception e) {
            Log.e("AllInOneTemplateView", "Failed to pick color after animation", e);
        }
    }

    public static final int touchSlop_delegate$lambda$3(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public static /* synthetic */ void unregisterImeCallback$default(AllInOneTemplateView allInOneTemplateView, View view, int i, Object obj) {
        if ((i & 1) != 0) {
            view = allInOneTemplateView.keyboardHideRoot;
        }
        allInOneTemplateView.unregisterImeCallback(view);
    }

    public static /* synthetic */ void updateClockParams$default(AllInOneTemplateView allInOneTemplateView, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = false;
        }
        allInOneTemplateView.updateClockParams(z, z2);
    }

    public static /* synthetic */ void updateClockScale$default(AllInOneTemplateView allInOneTemplateView, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            z2 = false;
        }
        allInOneTemplateView.updateClockScale(z, z2);
    }

    public final void afterDragClock() {
        startPickColor();
        updateAvoidRectAfterDrag();
    }

    public final void animateHandlesVisibility(boolean z) {
        if (this.dragResizeEnabled) {
            AnimState animState = new AnimState("show_start");
            ViewProperty viewProperty = ViewProperty.ALPHA;
            AnimState animStateAdd = animState.add(viewProperty, 0.0f, new long[0]);
            AnimState animStateAdd2 = new AnimState("show_end").add(viewProperty, 1.0f, new long[0]);
            AnimConfig animConfig = TransitionKt.getAnimConfig(0.95f, 0.35f);
            if (z) {
                animateHandlesVisibility$fadeIn(animStateAdd, animStateAdd2, animConfig, this, this.cornerHandleView);
            } else {
                animateHandlesVisibility$fadeOut(animConfig, this.cornerHandleView);
            }
        }
    }

    public final void applyColonAnimEndState(boolean z) {
        setAllEditorColonViewVisible(false);
        TimeView colonView = getColonView();
        if (colonView != null) {
            colonView.setAlpha(0.0f);
        }
        if (z) {
            this.shouldShowColon = true;
            getCurrentClockBean().setColonShow(true);
            getForeClockBean().setColonShow(true);
            setAllMinuteColonAlpha(1.0f);
            setAllClockTranslationX(0.0f, 0.0f);
            return;
        }
        this.shouldShowColon = false;
        getCurrentClockBean().setColonShow(false);
        getForeClockBean().setColonShow(false);
        setAllMinuteColonAlpha(0.0f);
        float colonHalfWidth = getColonHalfWidth();
        if (colonHalfWidth > 0.0f) {
            setAllClockTranslationX(colonHalfWidth, -colonHalfWidth);
        }
    }

    public final void applyDepthLayerOrder(AllInOneClockStyle.Region region) {
        IWallpaperLayer iWallpaperLayerM733getDepthVideoForegroundLayer;
        Log.i("AllInOneTemplateView", "applyDepthLayerOrder: region=" + region);
        HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
        BaseTemplateView baseTemplateView = getBaseTemplateView();
        Iterator it = getDepthLayerOrder(hierarchyImageView, (baseTemplateView == null || (iWallpaperLayerM733getDepthVideoForegroundLayer = baseTemplateView.m733getDepthVideoForegroundLayer()) == null) ? null : iWallpaperLayerM733getDepthVideoForegroundLayer.wallpaperLayerView()).iterator();
        while (it.hasNext()) {
            ((View) it.next()).bringToFront();
        }
        MultiTouchFrameLayout multiTouchFrameLayout = this.editorOverlayLayer;
        if (multiTouchFrameLayout != null) {
            multiTouchFrameLayout.bringToFront();
        }
        updateBlurContainer();
    }

    public final void applyKeyboardLift(boolean z, int i) {
        if (z && !isValidImeBottom(i)) {
            Log.e("AllInOneTemplateView", "applyKeyboardLift: SUSPICIOUS imeBottom=" + i + ", skipping");
            return;
        }
        dumpEditState("applyKeyboardLift(liftUp=" + z + ", imeBottom=" + i + ")");
        boolean z2 = this.keyboardLiftApplied;
        if (z2 == z) {
            Log.w("AllInOneTemplateView", "applyKeyboardLift: BLOCKED by guard, keyboardLiftApplied(" + z2 + ") == liftUp(" + z + ")");
            return;
        }
        float f = 0.0f;
        if (z) {
            KgFrame mKgSignatureAreaFrame = getMKgSignatureAreaFrame();
            boolean z3 = Math.abs(mKgSignatureAreaFrame != null ? mKgSignatureAreaFrame.getTranslationY() : 0.0f) > 0.5f;
            resetLiftTranslations();
            ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            float fProvideTemplateEditScale = resourcesProvider.provideTemplateEditScale(context);
            int[] iArr = new int[2];
            KgFrame mKgSignatureAreaFrame2 = getMKgSignatureAreaFrame();
            if (mKgSignatureAreaFrame2 != null) {
                mKgSignatureAreaFrame2.getLocationOnScreen(iArr);
            }
            float f2 = iArr[1];
            KgFrame mKgSignatureAreaFrame3 = getMKgSignatureAreaFrame();
            float height = f2 + ((mKgSignatureAreaFrame3 != null ? mKgSignatureAreaFrame3.getHeight() : 0) * fProvideTemplateEditScale);
            BaseTemplateView baseTemplateView = getBaseTemplateView();
            int templateDialogOffset = baseTemplateView != null ? baseTemplateView.getTemplateDialogOffset() : 0;
            BaseTemplateView baseTemplateView2 = getBaseTemplateView();
            float translationY = (templateDialogOffset - (baseTemplateView2 != null ? baseTemplateView2.getTranslationY() : 0.0f)) + height;
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            float fHeight = deviceUtil.currentDisplaySize(context2).height() - i;
            float dimen = translationY - (fHeight - DeviceConfig.getDimen(R.dimen.kg_all_in_one_signature_keyboard_gap_distance, getContext()));
            Log.d("AllInOneTemplateView", "applyKeyboardLift: signatureBottom=" + height + ", finalSignatureBottom=" + translationY + ", keyboardTop=" + fHeight + ", overlap=" + dimen);
            this.appliedImeBottom = i;
            if (dimen > 0.0f) {
                f = (-dimen) / fProvideTemplateEditScale;
                Log.d("AllInOneTemplateView", "applyKeyboardLift: will animate, target=" + f);
            } else {
                if (!z3) {
                    Log.d("AllInOneTemplateView", "applyKeyboardLift: overlap<=0, no animation needed, mark applied=" + z + " and skip");
                    this.keyboardLiftApplied = z;
                    return;
                }
                Log.d("AllInOneTemplateView", "applyKeyboardLift: overlap<=0, but views had residual tY, animating to 0");
            }
        } else {
            this.appliedImeBottom = 0;
        }
        KgFrame kgFrame = this.mKgTextAreaFrame;
        if (kgFrame != null) {
            applyKeyboardLift$animateLift(kgFrame, f);
        }
        KgFrame kgFrame2 = this.mTimeAreaFrame;
        if (kgFrame2 != null) {
            applyKeyboardLift$animateLift(kgFrame2, f);
        }
        KgFrame mKgSignatureAreaFrame4 = getMKgSignatureAreaFrame();
        if (mKgSignatureAreaFrame4 != null) {
            applyKeyboardLift$animateLift(mKgSignatureAreaFrame4, f);
        }
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView == null) {
            miuiClockView = null;
        }
        if (miuiClockView != null) {
            applyKeyboardLift$animateLift(miuiClockView, f);
        }
        MiuiClockView foreClock = getForeClock();
        MiuiClockView miuiClockView2 = foreClock != null ? foreClock : null;
        if (miuiClockView2 != null) {
            applyKeyboardLift$animateLift(miuiClockView2, f);
        }
        ImageView imageView = this.cornerHandleView;
        if (imageView != null) {
            applyKeyboardLift$animateLift(imageView, f);
        }
        TextView scaleTips = getScaleTips();
        if (scaleTips != null) {
            applyKeyboardLift$animateLift(scaleTips, f);
        }
        this.keyboardLiftApplied = z;
    }

    public final void applyLongPressOverScrollTranslation(float f) {
        KgFrame kgFrame = this.mKgTextAreaFrame;
        KgFrame kgFrame2 = this.mTimeAreaFrame;
        KgFrame mKgSignatureAreaFrame = getMKgSignatureAreaFrame();
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView == null) {
            miuiClockView = null;
        }
        MiuiClockView foreClock = getForeClock();
        Iterator it = CollectionsKt.listOfNotNull((Object[]) new View[]{kgFrame, kgFrame2, mKgSignatureAreaFrame, miuiClockView, foreClock != null ? foreClock : null, this.cornerHandleView}).iterator();
        while (it.hasNext()) {
            ((View) it.next()).setTranslationY(f);
        }
    }

    public final void applyPickPanelLift(boolean z, int i) {
        Window window;
        View decorView;
        Log.d("AllInOneTemplateView", "applyPickPanelLift: liftUp=" + z + ", pickPanelLiftApplied=" + this.pickPanelLiftApplied + ", clickedViewId=" + i);
        if (this.pickPanelLiftApplied == z) {
            return;
        }
        Ref.FloatRef floatRef = new Ref.FloatRef();
        if (z) {
            int i2 = R.id.kg_text_area_frame;
            AutoBottomSheet mTextAreaBottomSheetModal = i == i2 ? getMTextAreaBottomSheetModal() : getClockBottomSheetModal();
            FrameLayout container = mTextAreaBottomSheetModal != null ? mTextAreaBottomSheetModal.getContainer() : null;
            Context context = getContext();
            Activity activity = context instanceof Activity ? (Activity) context : null;
            View viewFindViewById = (activity == null || (window = activity.getWindow()) == null || (decorView = window.getDecorView()) == null) ? null : decorView.findViewById(R.id.bottom_sheet_view);
            if (container != null) {
                if (!container.isAttachedToWindow() || container.getHeight() <= 0) {
                    container = null;
                }
                if (container != null) {
                    viewFindViewById = container;
                }
            }
            if (viewFindViewById == null || viewFindViewById.getHeight() == 0) {
                Log.d("AllInOneTemplateView", "applyPickPanelLift: BottomSheet not ready, found=" + (viewFindViewById != null) + ", height=" + (viewFindViewById != null ? Integer.valueOf(viewFindViewById.getHeight()) : null));
                return;
            }
            ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            float fProvideTemplateEditScale = resourcesProvider.provideTemplateEditScale(context2);
            KgFrame kgFrame = i == i2 ? this.mKgTextAreaFrame : this.mTimeAreaFrame;
            if (kgFrame == null) {
                Log.d("AllInOneTemplateView", "applyPickPanelLift: bottomView is null for clickedViewId=" + i);
                return;
            }
            int[] iArr = new int[2];
            kgFrame.getLocationOnScreen(iArr);
            float height = iArr[1] + (kgFrame.getHeight() * fProvideTemplateEditScale);
            BaseTemplateView baseTemplateView = getBaseTemplateView();
            int templateDialogOffset = baseTemplateView != null ? baseTemplateView.getTemplateDialogOffset() : 0;
            BaseTemplateView baseTemplateView2 = getBaseTemplateView();
            float translationY = baseTemplateView2 != null ? baseTemplateView2.getTranslationY() : 0.0f;
            float f = templateDialogOffset - translationY;
            float f2 = height + f;
            int[] iArr2 = new int[2];
            viewFindViewById.getLocationOnScreen(iArr2);
            float translationY2 = iArr2[1] - viewFindViewById.getTranslationY();
            Log.d("AllInOneTemplateView", "applyPickPanelLift: editFrameBottom=" + height + ", dialogOffset=" + templateDialogOffset + ", templateTranslationY=" + translationY + ", finalEditFrameBottom=" + f2 + ", bottomSheetTop=" + translationY2 + ", templateScale=" + fProvideTemplateEditScale);
            float dimen = (float) DeviceConfig.getDimen(R.dimen.kg_all_in_one_clock_view_picker_view_gap_distance, getContext());
            float f3 = f2 - (translationY2 - dimen);
            StringBuilder sb = new StringBuilder();
            sb.append("applyPickPanelLift: gapPx=");
            sb.append(dimen);
            sb.append(", overlap=");
            sb.append(f3);
            Log.d("AllInOneTemplateView", sb.toString());
            if (f3 <= 0.0f) {
                this.pickPanelLiftApplied = true;
                return;
            }
            float f4 = (-f3) / fProvideTemplateEditScale;
            floatRef.element = f4;
            float dimen2 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_clock_view_picker_lift_top_margin, getContext());
            int[] iArr3 = new int[2];
            BaseTemplateView baseTemplateView3 = getBaseTemplateView();
            if (baseTemplateView3 != null) {
                baseTemplateView3.getLocationOnScreen(iArr3);
            }
            float f5 = iArr3[1];
            float f6 = f5 + f + dimen2;
            int[] iArr4 = new int[2];
            KgFrame kgFrame2 = this.mKgTextAreaFrame;
            if (kgFrame2 != null || (kgFrame2 = this.mTimeAreaFrame) != null) {
                kgFrame = kgFrame2;
            }
            kgFrame.getLocationOnScreen(iArr4);
            float f7 = iArr4[1] + f;
            float f8 = (-(f7 - f6)) / fProvideTemplateEditScale;
            if (floatRef.element < f8) {
                floatRef.element = RangesKt.coerceAtMost(f8, 0.0f);
                Log.d("AllInOneTemplateView", "applyPickPanelLift: clamped by top margin, original=" + f4 + ", maxLift=" + f8 + ", finalClockTop=" + f7 + ", containerTop=" + f5);
            }
            Log.d("AllInOneTemplateView", "applyPickPanelLift: target=" + floatRef.element);
        } else {
            floatRef.element = 0.0f;
        }
        KgFrame kgFrame3 = this.mKgTextAreaFrame;
        if (kgFrame3 != null) {
            applyPickPanelLift$animateLift$32(kgFrame3, floatRef);
        }
        KgFrame kgFrame4 = this.mTimeAreaFrame;
        if (kgFrame4 != null) {
            applyPickPanelLift$animateLift$32(kgFrame4, floatRef);
        }
        KgFrame mKgSignatureAreaFrame = getMKgSignatureAreaFrame();
        if (mKgSignatureAreaFrame != null) {
            applyPickPanelLift$animateLift$32(mKgSignatureAreaFrame, floatRef);
        }
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView == null) {
            miuiClockView = null;
        }
        if (miuiClockView != null) {
            applyPickPanelLift$animateLift$32(miuiClockView, floatRef);
        }
        MiuiClockView foreClock = getForeClock();
        MiuiClockView miuiClockView2 = foreClock != null ? foreClock : null;
        if (miuiClockView2 != null) {
            applyPickPanelLift$animateLift$32(miuiClockView2, floatRef);
        }
        ImageView imageView = this.cornerHandleView;
        if (imageView != null) {
            applyPickPanelLift$animateLift$32(imageView, floatRef);
        }
        TextView scaleTips = getScaleTips();
        if (scaleTips != null) {
            applyPickPanelLift$animateLift$32(scaleTips, floatRef);
        }
        this.pickPanelLiftApplied = z;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void applySignatureFrameVisibilityIfNeeded() {
        Boolean boolValueOf;
        Boolean boolValueOf2;
        Boolean boolValueOf3;
        Boolean boolValueOf4;
        Boolean boolValueOf5;
        Boolean boolValueOf6;
        if (!getEditFrameController().getCanShowAll$keyguardeditor_release()) {
            Log.d("AllInOneTemplateView", "applySignatureVisibility: skip, in full-screen preview (canShowAll=false)");
            return;
        }
        EditText signatureAreaView = getSignatureAreaView();
        boolean z = signatureAreaView != null && signatureAreaView.hasFocus();
        MiuiClockView miuiClockView = getMiuiClockView();
        View iClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.SIGNATURE_EDIT_AREA) : null;
        if (iClockView == null) {
            iClockView = null;
        }
        MiuiClockView foreMiuiClockView = getForeMiuiClockView();
        View iClockView2 = foreMiuiClockView != null ? foreMiuiClockView.getIClockView(ClockViewType.SIGNATURE_EDIT_AREA) : null;
        if (iClockView2 == null) {
            iClockView2 = null;
        }
        boolean z2 = this.signatureVisible;
        boolean z3 = this.signatureHasContent;
        String hexString = Integer.toHexString(System.identityHashCode(getSignatureAreaView()));
        KgFrame mKgSignatureAreaFrame = getMKgSignatureAreaFrame();
        if (mKgSignatureAreaFrame != null) {
            boolValueOf = Boolean.valueOf(mKgSignatureAreaFrame.getVisibility() == 0);
        } else {
            boolValueOf = null;
        }
        EditText signatureAreaView2 = getSignatureAreaView();
        if (signatureAreaView2 == null) {
            signatureAreaView2 = null;
        }
        if (signatureAreaView2 != null) {
            boolValueOf2 = Boolean.valueOf(signatureAreaView2.getVisibility() == 0);
        } else {
            boolValueOf2 = null;
        }
        String hexString2 = Integer.toHexString(System.identityHashCode(iClockView));
        if (iClockView != null) {
            boolValueOf3 = Boolean.valueOf(iClockView.getVisibility() == 0);
        } else {
            boolValueOf3 = null;
        }
        String hexString3 = Integer.toHexString(System.identityHashCode(iClockView2));
        if (iClockView2 != null) {
            boolValueOf4 = Boolean.valueOf(iClockView2.getVisibility() == 0);
        } else {
            boolValueOf4 = null;
        }
        Log.d("AllInOneTemplateView", "applySignatureVisibility: editing=" + z + ", signatureVisible=" + z2 + ", hasContent=" + z3 + ", areaView@=" + hexString + ", frameVisible=" + boolValueOf + ", areaViewVisible=" + boolValueOf2 + ", hourEdit@=" + hexString2 + "(visible=" + boolValueOf3 + "), minEdit@=" + hexString3 + "(visible=" + boolValueOf4 + ")");
        if (z) {
            KgFrame mKgSignatureAreaFrame2 = getMKgSignatureAreaFrame();
            if (mKgSignatureAreaFrame2 != null) {
                mKgSignatureAreaFrame2.setVisibility(0);
            }
            KgFrame kgFrame = this.mKgTextAreaFrame;
            if (kgFrame != null) {
                kgFrame.setVisibility(8);
            }
            KgFrame kgFrame2 = this.mTimeAreaFrame;
            if (kgFrame2 != null) {
                kgFrame2.setVisibility(8);
            }
            animateHandlesVisibility(false);
            if (iClockView != null) {
                iClockView.setVisibility(getSignatureAreaView() == iClockView ? 0 : 8);
            }
            if (iClockView2 != null) {
                iClockView2.setVisibility(getSignatureAreaView() == iClockView2 ? 0 : 8);
            }
            if (iClockView != null) {
                boolValueOf5 = Boolean.valueOf(iClockView.getVisibility() == 0);
            } else {
                boolValueOf5 = null;
            }
            if (iClockView2 != null) {
                boolValueOf6 = Boolean.valueOf(iClockView2.getVisibility() == 0);
            } else {
                boolValueOf6 = null;
            }
            Log.d("AllInOneTemplateView", "applySignatureVisibility[editing]: hourEditVisible=" + boolValueOf5 + ", minEditVisible=" + boolValueOf6);
            return;
        }
        KgFrame mKgSignatureAreaFrame3 = getMKgSignatureAreaFrame();
        if (mKgSignatureAreaFrame3 != null) {
            mKgSignatureAreaFrame3.setVisibility(this.signatureVisible ? 0 : 8);
        }
        if (getSignatureAreaView() == null) {
            Log.d("AllInOneTemplateView", "applySignatureVisibility[non-editable]: signatureAreaView is null, skip hourEdit/minEdit visibility control");
            return;
        }
        EditText signatureAreaView3 = getSignatureAreaView();
        if (signatureAreaView3 == null) {
            signatureAreaView3 = null;
        }
        if (signatureAreaView3 != null) {
            signatureAreaView3.setVisibility(this.signatureVisible ? 0 : 8);
        }
        if (getSignatureAreaView() == iClockView) {
            if (iClockView2 != null) {
                iClockView2.setVisibility(8);
            }
        } else if (iClockView != null) {
            iClockView.setVisibility(8);
        }
        boolean z4 = this.signatureVisible;
        String str = getSignatureAreaView() == iClockView ? "hour" : VariableNames.VAR_MINUTE;
        Log.d("AllInOneTemplateView", "applySignatureVisibility[normal]: frameVisible=" + z4 + ", activeView=" + str + "(visible=" + this.signatureVisible + "), otherSideForceHidden=true");
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void applyTemplateInfo(TemplateConfig templateConfig) {
        ClockInfo clockInfo;
        float timeWidth;
        float timeHeight;
        ClockStyleInfo clockStyleInfo;
        ClockStyleInfo clockStyleInfo2;
        ClockStyleInfo clockStyleInfo3;
        ClockStyleInfo clockStyleInfo4;
        ClockStyleInfo clockStyleInfo5;
        super.applyTemplateInfo(templateConfig);
        if (templateConfig == null || (clockInfo = templateConfig.getClockInfo()) == null) {
            return;
        }
        MiuiClockView miuiClockView = getMiuiClockView();
        clockInfo.setClockTopRatio((miuiClockView == null || (clockStyleInfo5 = miuiClockView.getClockStyleInfo()) == null) ? this.clockTopRatio : clockStyleInfo5.getClockTopRatio());
        MiuiClockView miuiClockView2 = getMiuiClockView();
        clockInfo.setClockBottomRatio((miuiClockView2 == null || (clockStyleInfo4 = miuiClockView2.getClockStyleInfo()) == null) ? this.clockBottomRatio : clockStyleInfo4.getClockBottomRatio());
        AllInOneUtil.ClockLayoutInput clockLayoutInputConvertToStorageFormat$default = AllInOneUtil.convertToStorageFormat$default(AllInOneUtil.INSTANCE, new AllInOneBaseInfo.AllInOneRectParams(getCurrentClockBean().getClockDepthType(), this.clockTopRatio, this.clockBottomRatio, this.timeWidth, this.timeHeight), getContext(), getCurrentClockBean().isDoubleRow(), false, 8, null);
        MiuiClockView miuiClockView3 = getMiuiClockView();
        if (miuiClockView3 == null || (clockStyleInfo3 = miuiClockView3.getClockStyleInfo()) == null) {
            timeWidth = clockLayoutInputConvertToStorageFormat$default != null ? clockLayoutInputConvertToStorageFormat$default.getTimeWidth() : DeviceConfig.pxToDp(this.timeWidth, getContext());
        } else {
            timeWidth = clockStyleInfo3.getTimeWidth();
        }
        MiuiClockView miuiClockView4 = getMiuiClockView();
        if (miuiClockView4 == null || (clockStyleInfo2 = miuiClockView4.getClockStyleInfo()) == null) {
            timeHeight = clockLayoutInputConvertToStorageFormat$default != null ? clockLayoutInputConvertToStorageFormat$default.getTimeHeight() : DeviceConfig.pxToDp(this.timeHeight, getContext());
        } else {
            timeHeight = clockStyleInfo2.getTimeHeight();
        }
        boolean zIsDoubleRow = getCurrentClockBean().isDoubleRow();
        float dimenDpValue = DeviceConfig.getDimenDpValue(zIsDoubleRow ? com.miui.clock.R.dimen.miui_time_all_in_one_double_line_min_width : com.miui.clock.R.dimen.miui_time_all_in_one_single_line_min_width, getContext());
        float dimenDpValue2 = DeviceConfig.getDimenDpValue(zIsDoubleRow ? com.miui.clock.R.dimen.miui_time_all_in_one_double_line_min_height : com.miui.clock.R.dimen.miui_time_all_in_one_single_line_min_height, getContext());
        clockInfo.setTimeWidth(Math.max(timeWidth, dimenDpValue));
        clockInfo.setTimeHeight(Math.max(timeHeight, dimenDpValue2));
        MiuiClockView miuiClockView5 = getMiuiClockView();
        AllInOneClockStyle.Region clockDepthType = (miuiClockView5 == null || (clockStyleInfo = miuiClockView5.getClockStyleInfo()) == null) ? null : clockStyleInfo.getClockDepthType();
        AllInOneClockStyle.Region clockDepthType2 = getCurrentClockBean().getClockDepthType();
        clockInfo.setClockDepthType(AllInOneClockStyle.toRegionInt(clockDepthType2));
        Log.w("AllInOneTemplateView", "saveClockInfo: aarDepthType=" + clockDepthType + ", beanDepthType=" + clockDepthType2 + " -> saved=" + clockInfo.getClockDepthType());
        Float glassTransparency = getCurrentClockBean().getGlassTransparency();
        clockInfo.setGlassTransparency(glassTransparency != null ? glassTransparency.floatValue() : 0.15f);
        clockInfo.setFontStyle(getCurrentClockBean().getFontStyle());
        clockInfo.setHollowStyle(getCurrentClockBean().getHollowStyle());
        clockInfo.setDoubleRow(getCurrentClockBean().isDoubleRow());
        clockInfo.setColonShow(getCurrentClockBean().isColonShow());
        clockInfo.setSkipScreenTransform(getCurrentClockBean().isSkipScreenTransform());
        Log.d("AllInOneTemplateView", "applyTemplateInfo: skipScreenTransform=" + clockInfo.isSkipScreenTransform());
    }

    public final void applyZoomPivot() {
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        float fProvideTemplateEditScale = resourcesProvider.provideTemplateEditScale(context);
        float f = fProvideTemplateEditScale > 0.0f ? 1.0f / fProvideTemplateEditScale : 1.0f;
        int[] iArr = new int[2];
        for (View view : longPressZoomTargets()) {
            view.getLocationOnScreen(iArr);
            view.setPivotX((this.zoomPivotScreen[0] - iArr[0]) * f);
            view.setPivotY((this.zoomPivotScreen[1] - iArr[1]) * f);
        }
    }

    public final void cancelColonTransitionAnim() {
        Folme.useValue(this.COLON_TRANSITION).clean();
        this.isColonTransitionAnimRunning = false;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0015  */
    /* JADX WARN: Code duplicated, block: B:19:0x0028  */
    /* JADX WARN: Code duplicated, block: B:22:? A[RETURN, SYNTHETIC] */
    public final void captureZoomPivot() {
        KgFrame kgFrame;
        View view = this.mTimeAreaView;
        KgFrame kgFrame2 = null;
        if (view == null) {
            kgFrame = this.mTimeAreaFrame;
            if (kgFrame != null && kgFrame.getWidth() > 0 && kgFrame.getHeight() > 0) {
                kgFrame2 = kgFrame;
            }
            if (kgFrame2 != null) {
                return;
            } else {
                view = kgFrame2;
            }
        } else {
            if (view.getWidth() <= 0 || view.getHeight() <= 0) {
                view = null;
            }
            if (view == null) {
                kgFrame = this.mTimeAreaFrame;
                if (kgFrame != null) {
                    kgFrame2 = kgFrame;
                }
                if (kgFrame2 != null) {
                    return;
                } else {
                    view = kgFrame2;
                }
            }
        }
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        float fProvideTemplateEditScale = resourcesProvider.provideTemplateEditScale(context);
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        this.zoomPivotScreen[0] = iArr[0] + ((view.getWidth() * fProvideTemplateEditScale) / 2.0f);
        this.zoomPivotScreen[1] = iArr[1] + ((view.getHeight() * fProvideTemplateEditScale) / 2.0f);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0059  */
    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void clearEditeFocus() {
        Boolean boolValueOf;
        String str;
        View clickView = getClickView();
        if (clickView != null) {
            boolValueOf = Boolean.valueOf(clickView.getVisibility() == 0);
        } else {
            boolValueOf = null;
        }
        EditText signatureAreaView = getSignatureAreaView();
        Boolean boolValueOf2 = signatureAreaView != null ? Boolean.valueOf(signatureAreaView.hasFocus()) : null;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Intrinsics.checkNotNullExpressionValue(stackTrace, "getStackTrace(...)");
        StackTraceElement stackTraceElement = (StackTraceElement) ArraysKt.getOrNull(stackTrace, 3);
        if (stackTraceElement != null) {
            str = stackTraceElement.getMethodName() + ":" + stackTraceElement.getLineNumber();
            if (str == null) {
                str = "?";
            }
        } else {
            str = "?";
        }
        Log.w("AllInOneTemplateView", "clearEditeFocus: clickView=" + boolValueOf + ", hasFocus=" + boolValueOf2 + ", caller=" + str);
        super.clearEditeFocus();
        EditText signatureAreaView2 = getSignatureAreaView();
        if (signatureAreaView2 != null) {
            signatureAreaView2.clearFocus();
            signatureAreaView2.setFocusableInTouchMode(false);
        }
        View clickView2 = getClickView();
        if (clickView2 != null) {
            clickView2.setVisibility(8);
        }
    }

    public final AllInOneClockStyle.Region computeRealtimeDepthRegion() {
        int i;
        int i2;
        int i3 = this.currentClockFrameRight;
        int i4 = this.currentClockFrameLeft;
        if (i3 <= i4 || (i = this.currentClockFrameBottom) <= (i2 = this.currentClockFrameTop)) {
            return null;
        }
        AllInOneClockStyle.Region region = this.cachedRegion;
        if (region != null) {
            Rect rect = this.lastRegionFrame;
            if (rect.left == i4 && rect.top == i2 && rect.right == i3 && rect.bottom == i) {
                return region;
            }
        }
        int dimen = DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding_horizontal, getContext());
        int dimen2 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding, getContext());
        this.tmpClockRect.set(this.currentClockFrameLeft + dimen, this.currentClockFrameTop + dimen2, this.currentClockFrameRight - dimen, this.currentClockFrameBottom - dimen2);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int width = deviceUtil.currentDisplaySizeByContext(context).getWidth();
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        int height = deviceUtil.currentDisplaySizeByContext(context2).getHeight();
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        this.cachedRegion = ClockDepthAvoidRuleUtils.decideRegion$default(context3, width, height, this.tmpClockRect, null, 16, null);
        this.lastRegionFrame.set(this.currentClockFrameLeft, this.currentClockFrameTop, this.currentClockFrameRight, this.currentClockFrameBottom);
        return this.cachedRegion;
    }

    public final void correctLongPressFrameTopIfExceedSafe() {
        int i = this.maxSafeFrameRect.top;
        int i2 = this.currentAllVisibleFramesTop;
        int i3 = i - i2;
        if (i3 > 0) {
            this.currentClockFrameTop += i3;
            this.currentClockFrameBottom += i3;
            this.currentAllVisibleFramesTop = i2 + i3;
            this.currentAllVisibleFramesBottom += i3;
            updateRatio();
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public AllInOneAutoColorPicker createAutoColorPicker() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return new AllInOneAutoColorPicker(context, "all_in_one", getCurrentClockBean());
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public ColorPickHandler createColorPickHandler() {
        return new ColorPickHandler(this, getCurrentClockBean(), getForeClockBean());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect currentColorPickupRect() {
        return this.colorPickupRect;
    }

    public final MiuiClockView currentMinuteOwner() {
        AllInOneClockStyle.Region regionComputeRealtimeDepthRegion = computeRealtimeDepthRegion();
        if (regionComputeRealtimeDepthRegion == null) {
            regionComputeRealtimeDepthRegion = getCurrentClockBean().getClockDepthType();
        }
        return AllInOneClockStyle.toRegionInt(regionComputeRealtimeDepthRegion) == 0 ? getMiuiClockView() : getForeClock();
    }

    public final void decideHierarchyLayerPosition() {
        ClockStyleInfo clockStyleInfo;
        ClockStyleInfo clockStyleInfo2;
        String string;
        Boolean boolValueOf;
        Boolean boolValueOf2;
        Boolean boolValueOf3;
        Boolean boolValueOf4;
        Editable text;
        String string2;
        Editable text2;
        AllInOneClockStyle.Region clockDepthType = getCurrentClockBean().getClockDepthType();
        AllInOneClockStyle.Region regionComputeRealtimeDepthRegion = computeRealtimeDepthRegion();
        if (regionComputeRealtimeDepthRegion == null) {
            regionComputeRealtimeDepthRegion = AllInOneClockStyle.Region.REGION_1;
        }
        final AllInOneClockStyle.Region region = regionComputeRealtimeDepthRegion;
        getCurrentClockBean().setClockDepthType(region);
        getForeClockBean().setClockDepthType(region);
        boolean z = clockDepthType != region;
        if (z) {
            Log.w("AllInOneTemplateView", "decideHierarchyLayerPosition: decideRegion=" + region);
        }
        AllInOneClockStyle.Region region2 = AllInOneClockStyle.Region.REGION_2;
        MiuiClockView miuiClockView = region == region2 ? getMiuiClockView() : getForeMiuiClockView();
        View iClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.SIGNATURE_EDIT_AREA) : null;
        EditText editText = iClockView instanceof EditText ? (EditText) iClockView : null;
        if (editText != null && editText != getSignatureAreaView()) {
            EditText signatureAreaView = getSignatureAreaView();
            String str = "";
            if (signatureAreaView == null || (text2 = signatureAreaView.getText()) == null || (string = text2.toString()) == null) {
                string = "";
            }
            Log.i("AllInOneTemplateView", "Signature switch: old=" + Integer.toHexString(System.identityHashCode(signatureAreaView)) + ", new=" + Integer.toHexString(System.identityHashCode(editText)) + ", oldText='" + string + "' newText='" + ((Object) editText.getText()) + "'");
            if (signatureAreaView != null) {
                signatureAreaView.removeTextChangedListener(this);
            }
            setSignatureAreaView(editText);
            Editable text3 = editText.getText();
            if (!Intrinsics.areEqual(text3 != null ? text3.toString() : null, string)) {
                editText.setText(string);
            }
            editText.setFocusableInTouchMode(true);
            editText.setFocusable(true);
            editText.addTextChangedListener(this);
            editText.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda9
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return AllInOneTemplateView.decideHierarchyLayerPosition$lambda$49(this.f$0, view, motionEvent);
                }
            });
            MiuiClockView miuiClockView2 = getMiuiClockView();
            View iClockView2 = miuiClockView2 != null ? miuiClockView2.getIClockView(ClockViewType.SIGNATURE_EDIT_AREA) : null;
            if (iClockView2 == null) {
                iClockView2 = null;
            }
            MiuiClockView foreMiuiClockView = getForeMiuiClockView();
            View iClockView3 = foreMiuiClockView != null ? foreMiuiClockView.getIClockView(ClockViewType.SIGNATURE_EDIT_AREA) : null;
            if (iClockView3 == null) {
                iClockView3 = null;
            }
            Log.i("AllInOneTemplateView", "SignatureVisibility[switch]: old@=" + Integer.toHexString(System.identityHashCode(signatureAreaView)) + ", new@=" + Integer.toHexString(System.identityHashCode(editText)) + ", signatureVisible=" + this.signatureVisible);
            applySignatureFrameVisibilityIfNeeded();
            if (!editText.hasFocus() && ((text = editText.getText()) == null || text.length() == 0)) {
                Context context = editText.getContext();
                if (context != null && (string2 = context.getString(com.miui.clock.R.string.miui_clock_classic_signature_area_hint)) != null) {
                    str = string2;
                }
                Log.i("AllInOneTemplateView", "Signature switch: side=" + (region == region2 ? "HOUR" : "MINUTE") + ", set hint immediately: '" + str + "'");
                editText.setHint(str);
            }
            boolean z2 = this.signatureVisible && getEditFrameController().getCanShowAll$keyguardeditor_release();
            if (iClockView2 != null) {
                iClockView2.setVisibility(getSignatureAreaView() == iClockView2 && z2 ? 0 : 8);
            }
            if (iClockView3 != null) {
                iClockView3.setVisibility(getSignatureAreaView() == iClockView3 && z2 ? 0 : 8);
            }
            Object parent = iClockView2 != null ? iClockView2.getParent() : null;
            View view = parent instanceof View ? (View) parent : null;
            Object parent2 = iClockView3 != null ? iClockView3.getParent() : null;
            View view2 = parent2 instanceof View ? (View) parent2 : null;
            if (view != null) {
                view.setVisibility(getSignatureAreaView() == iClockView2 && z2 ? 0 : 8);
            }
            if (view2 != null) {
                view2.setVisibility(getSignatureAreaView() == iClockView3 && z2 ? 0 : 8);
            }
            if (view != null) {
                boolValueOf = Boolean.valueOf(view.getVisibility() == 0);
            } else {
                boolValueOf = null;
            }
            if (view2 != null) {
                boolValueOf2 = Boolean.valueOf(view2.getVisibility() == 0);
            } else {
                boolValueOf2 = null;
            }
            Log.i("AllInOneTemplateView", "SignatureContainerSync: hourContainer=" + boolValueOf + ", minContainer=" + boolValueOf2 + ", canShow=" + z2);
            rebindWallpaperObserverToOverlay();
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda10
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view3, boolean z3) {
                    AllInOneTemplateView.decideHierarchyLayerPosition$lambda$50(region, this, view3, z3);
                }
            });
            String str2 = region == region2 ? "HOUR" : "MINUTE";
            if (iClockView2 != null) {
                boolValueOf3 = Boolean.valueOf(iClockView2.getVisibility() == 0);
            } else {
                boolValueOf3 = null;
            }
            if (iClockView3 != null) {
                boolValueOf4 = Boolean.valueOf(iClockView3.getVisibility() == 0);
            } else {
                boolValueOf4 = null;
            }
            Log.i("AllInOneTemplateView", "decideHierarchyLayerPosition: switched signatureAreaView to " + str2 + ", hourEditVisible=" + boolValueOf3 + ", minEditVisible=" + boolValueOf4);
        }
        EditText signatureAreaView2 = getSignatureAreaView();
        if (signatureAreaView2 == null || !signatureAreaView2.hasFocus()) {
            Log.i("AllInOneTemplateView", "decideHierarchyLayerPosition: signature has no focus, ensure overlay needDispatch=false");
            MultiTouchFrameLayout multiTouchFrameLayout = this.editorOverlayLayer;
            if (multiTouchFrameLayout != null) {
                multiTouchFrameLayout.updateNeedDispatch(false);
            }
            MultiTouchFrameLayout multiTouchFrameLayout2 = (MultiTouchFrameLayout) getForeContentLayer();
            if (multiTouchFrameLayout2 != null) {
                multiTouchFrameLayout2.updateNeedDispatch(false);
            }
        }
        if (!z) {
            Log.d("AllInOneTemplateView", "decideHierarchyLayerPosition: regionUnchanged=" + region + ", skip AAR push");
            return;
        }
        MiuiClockView miuiClockView3 = getMiuiClockView();
        AllInOneClockStyle.Region clockDepthType2 = (miuiClockView3 == null || (clockStyleInfo2 = miuiClockView3.getClockStyleInfo()) == null) ? null : clockStyleInfo2.getClockDepthType();
        MiuiClockView foreClock = getForeClock();
        Log.d("AllInOneTemplateView", "decideHierarchyLayerPosition: pushDepthToAAR region=" + region + ", aarHourDepth=" + clockDepthType2 + ", aarMinuteDepth=" + ((foreClock == null || (clockStyleInfo = foreClock.getClockStyleInfo()) == null) ? null : clockStyleInfo.getClockDepthType()));
        MiuiClockView miuiClockView4 = getMiuiClockView();
        if (miuiClockView4 != null) {
            MiuiClockControllerExtKt.setAllInOneClockRectParamsOptional$default(miuiClockView4, region, 0.0f, 0.0f, 0, 0, 0, null, null, 254, null);
        }
        MiuiClockView foreClock2 = getForeClock();
        if (foreClock2 != null) {
            MiuiClockControllerExtKt.setAllInOneClockRectParamsOptional$default(foreClock2, region, 0.0f, 0.0f, 0, 0, 0, null, null, 254, null);
        }
        applyDepthLayerOrder(region);
        if (this.shouldShowColon) {
            Log.d("AllInOneTemplateView", "decideHierarchyLayerPosition: region changed → re-toggle colonView owner");
            setAllEditorColonViewVisible(false);
            setAllEditorColonViewVisible(true);
        }
        Log.d("AllInOneTemplateView", "decideHierarchyLayerPosition: done, region=" + region + ", oldRegion=" + clockDepthType);
    }

    public final void doColonTransitionAnim(final boolean z, final boolean z2, long j) {
        final TimeView minView;
        final TimeView hourView = getHourView();
        if (hourView == null || (minView = getMinView()) == null) {
            return;
        }
        final TimeView colonView = getColonView();
        float colonHalfWidth = getColonHalfWidth();
        if (colonHalfWidth > 0.0f || z2) {
            if (colonHalfWidth <= 0.0f && z2) {
                applyColonAnimEndState(!z);
                return;
            }
            cancelColonTransitionAnim();
            this.isColonTransitionAnimRunning = true;
            final long j2 = 1 + this.colonAnimGeneration;
            this.colonAnimGeneration = j2;
            AnimConfig animConfigAddListeners = new AnimConfig().addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$doColonTransitionAnim$animConfig$1
                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    this.this$0.isColonTransitionAnimRunning = false;
                    if (z2 && j2 == this.this$0.colonAnimGeneration) {
                        this.this$0.applyColonAnimEndState(!z);
                    }
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    this.this$0.isColonTransitionAnimRunning = false;
                    if (z2 && j2 == this.this$0.colonAnimGeneration) {
                        this.this$0.applyColonAnimEndState(!z);
                    }
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object obj, Collection collection) {
                    float f;
                    float f2;
                    double d;
                    double dCos;
                    UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, this.this$0.COLON_TRANSITION);
                    if (updateInfoFindByName != null) {
                        float floatValue = updateInfoFindByName.getFloatValue();
                        if (z2) {
                            float fCoerceIn = RangesKt.coerceIn(floatValue, 0.0f, 1.0f);
                            TimeView timeView = colonView;
                            if (timeView != null) {
                                if (z) {
                                    d = 1.0f;
                                    dCos = Math.sin(((double) (1.0f - fCoerceIn)) * 1.5707963267948966d);
                                } else {
                                    d = 1.0f;
                                    dCos = Math.cos(((double) fCoerceIn) * 1.5707963267948966d);
                                }
                                timeView.setAlpha((float) (d - dCos));
                            }
                        }
                        if (this.this$0.isDoubleRow || this.this$0.isClockLayoutSwitchAnimRunning) {
                            return;
                        }
                        float colonHalfWidth2 = this.this$0.getColonHalfWidth();
                        if (colonHalfWidth2 <= 0.0f) {
                            return;
                        }
                        boolean z3 = z;
                        if (z3) {
                            floatValue = 1 - floatValue;
                        }
                        if (z3) {
                            f = floatValue * colonHalfWidth2;
                            f2 = (-floatValue) * colonHalfWidth2;
                        } else {
                            float f3 = 1 - floatValue;
                            float f4 = f3 * colonHalfWidth2;
                            float f5 = colonHalfWidth2 * (-f3);
                            f = f4;
                            f2 = f5;
                        }
                        hourView.setTranslationX(f);
                        minView.setTranslationX(f2);
                    }
                }
            });
            animConfigAddListeners.setEase(z ? this.hideTransEase : this.showTransEase);
            if (j > 0) {
                animConfigAddListeners.setDelay(j);
            }
            Folme.useValue(this.COLON_TRANSITION).setTo(z ? this.upTransState : this.downTransState).to(z ? this.downTransState : this.upTransState, animConfigAddListeners);
        }
    }

    public final void doPostFadeAnimation() {
        View backTimeGroup = getBackTimeGroup();
        View foreTimeGroup = getForeTimeGroup();
        ClockUtil clockUtil = ClockUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        if (clockUtil.is24HourFormat(context)) {
            if (!this.isDoubleRow && isLessMinHeight()) {
                setAllMinuteColonAlpha(0.0f);
                setAllEditorColonShow(true);
                setAllEditorColonViewVisible(true);
                ensureColonAarReady();
                TimeView colonView = getColonView();
                if (colonView != null) {
                    colonView.setAlpha(0.0f);
                }
                startColonShowAnimWithFallback(120L);
            } else if (this.isDoubleRow) {
                cancelColonTransitionAnim();
                setAllClockTranslationX(0.0f, 0.0f);
            } else {
                cancelColonTransitionAnim();
                TimeView colonView2 = getColonView();
                if (colonView2 != null) {
                    colonView2.setAlpha(0.0f);
                }
                setAllEditorColonShow(false);
                setAllClockTranslationX(0.0f, 0.0f);
            }
        }
        this.isClockLayoutSwitchAnimRunning = false;
        if (this.isFadeOutCancelled) {
            this.isFadeOutCancelled = false;
            CrossListFolmeUtil crossListFolmeUtil = CrossListFolmeUtil.INSTANCE;
            crossListFolmeUtil.resetViewAlpha(backTimeGroup);
            crossListFolmeUtil.resetViewAlpha(foreTimeGroup);
        }
        if (backTimeGroup != null) {
            backTimeGroup.setVisibility(0);
        }
        CrossListFolmeUtil crossListFolmeUtil2 = CrossListFolmeUtil.INSTANCE;
        crossListFolmeUtil2.startFadeInAlphaAnimation(backTimeGroup);
        if (foreTimeGroup != null) {
            foreTimeGroup.setVisibility(0);
        }
        crossListFolmeUtil2.startFadeInAlphaAnimation(foreTimeGroup);
    }

    public final void doPreFadeAnimation() {
        Log.i("AllInOneTemplateView", "[LayoutSwitch] doPreFadeAnimation: isDoubleRow=" + this.isDoubleRow + ", prevDoubleRow=" + this.prevIsDoubleRow + ", frame=[" + this.currentClockFrameLeft + ac.b + this.currentClockFrameTop + ac.b + this.currentClockFrameRight + ac.b + this.currentClockFrameBottom + "], colonShow=" + this.shouldShowColon);
        View backTimeGroup = getBackTimeGroup();
        View foreTimeGroup = getForeTimeGroup();
        this.isClockLayoutSwitchAnimRunning = true;
        this.isFadeOutCancelled = false;
        if (backTimeGroup != null) {
            backTimeGroup.setVisibility(0);
        }
        CrossListFolmeUtil crossListFolmeUtil = CrossListFolmeUtil.INSTANCE;
        CrossListFolmeUtil.startFadeOutAlphaAnimation$default(crossListFolmeUtil, backTimeGroup, null, 2, null);
        if (foreTimeGroup != null) {
            foreTimeGroup.setVisibility(0);
        }
        crossListFolmeUtil.startFadeOutAlphaAnimation(foreTimeGroup, new TransitionListener() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView.doPreFadeAnimation.1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                AllInOneTemplateView.this.setAllClockTranslationX(0.0f, 0.0f);
                AllInOneTemplateView.updateClockParams$default(AllInOneTemplateView.this, true, false, 2, null);
                AllInOneTemplateView.this.isFadeOutCancelled = true;
                AllInOneTemplateView.this.doPostFadeAnimation();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                AllInOneTemplateView.this.setAllClockTranslationX(0.0f, 0.0f);
                AllInOneTemplateView.updateClockParams$default(AllInOneTemplateView.this, true, false, 2, null);
                AllInOneTemplateView.this.doPostFadeAnimation();
            }
        });
    }

    public final void dumpEditState(String str) {
        Boolean boolValueOf;
        Insets insets;
        View view = this.keyboardHideRoot;
        if (view == null) {
            return;
        }
        WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view);
        int i = (rootWindowInsets == null || (insets = rootWindowInsets.getInsets(WindowInsetsCompat.Type.ime())) == null) ? -1 : insets.bottom;
        View view2 = this.keyboardHideRoot;
        if (view2 == null) {
            return;
        }
        WindowInsetsCompat rootWindowInsets2 = ViewCompat.getRootWindowInsets(view2);
        boolean z = rootWindowInsets2 != null && rootWindowInsets2.isVisible(WindowInsetsCompat.Type.ime());
        View clickView = getClickView();
        if (clickView != null) {
            boolValueOf = Boolean.valueOf(clickView.getVisibility() == 0);
        } else {
            boolValueOf = null;
        }
        EditText signatureAreaView = getSignatureAreaView();
        Boolean boolValueOf2 = signatureAreaView != null ? Boolean.valueOf(signatureAreaView.hasFocus()) : null;
        EditText signatureAreaView2 = getSignatureAreaView();
        if (signatureAreaView2 == null) {
            signatureAreaView2 = null;
        }
        Boolean boolValueOf3 = signatureAreaView2 != null ? Boolean.valueOf(signatureAreaView2.isCursorVisible()) : null;
        Log.w("AllInOneTemplateView", "[" + str + "] clickView=" + boolValueOf + ", hasFocus=" + boolValueOf2 + ", isCursorVisible=" + boolValueOf3 + ", imeVisible=" + z + ", imeHeight=" + i + ", keyboardLiftApplied=" + this.keyboardLiftApplied);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public View.OnClickListener editFrameClickListener() {
        return this;
    }

    public final void ensureColonAarReady() {
        float colonHalfWidth = getColonHalfWidth();
        if (this.colonAarState == ColonAarState.PENDING || colonHalfWidth <= 0.0f) {
            this.colonAarState = ColonAarState.NOTIFIED;
            updateClockParams$default(this, true, false, 2, null);
        }
    }

    public final MultiTouchFrameLayout ensureEditorOverlayLayer() {
        CombinedWallpaperView combinedWallpaperView;
        final MultiTouchFrameLayout multiTouchFrameLayout = this.editorOverlayLayer;
        if (multiTouchFrameLayout == null) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            multiTouchFrameLayout = new MultiTouchFrameLayout(context);
            multiTouchFrameLayout.setId(R.id.kg_editor_overlay_layer);
            multiTouchFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            multiTouchFrameLayout.setImportantForAccessibility(2);
            this.editorOverlayLayer = multiTouchFrameLayout;
            addView(multiTouchFrameLayout);
            ObserverGestureListener observerGestureListener = new ObserverGestureListener(multiTouchFrameLayout, this) { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$ensureEditorOverlayLayer$2$composite$1
                public final ObserverClickListener click;
                public final /* synthetic */ AllInOneTemplateView this$0;

                {
                    this.this$0 = this;
                    Intrinsics.checkNotNull(multiTouchFrameLayout, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.ObserverClickListener");
                    this.click = multiTouchFrameLayout;
                }

                @Override // com.miui.keyguard.editor.edit.wallpaper.ObserverClickListener
                public boolean onObserveTouchClick(MotionEvent ev) {
                    Intrinsics.checkNotNullParameter(ev, "ev");
                    return this.click.onObserveTouchClick(ev);
                }

                @Override // com.miui.keyguard.editor.edit.wallpaper.ObserverGestureListener
                public void onObserveTouchEvent(MotionEvent ev) {
                    Intrinsics.checkNotNullParameter(ev, "ev");
                    this.this$0.onObservedTouchEvent(ev);
                }
            };
            BaseTemplateView baseTemplateView = getBaseTemplateView();
            if (baseTemplateView != null && (combinedWallpaperView = (CombinedWallpaperView) baseTemplateView.getWallpaperLayer()) != null) {
                combinedWallpaperView.setObserveTouchClickListener(observerGestureListener);
            }
            multiTouchFrameLayout.bringToFront();
        }
        return multiTouchFrameLayout;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void exitEditFrame() {
        super.exitEditFrame();
        animateHandlesVisibility(false);
        if (this.signatureHasContent) {
            return;
        }
        EditText signatureAreaView = getSignatureAreaView();
        if (signatureAreaView == null) {
            signatureAreaView = null;
        }
        if (signatureAreaView != null) {
            signatureAreaView.setVisibility(8);
        }
    }

    public final void finishLongPressSpringBack() {
        afterDragClock();
        this.setWay = this.dragMode;
        this.dragMode = 0;
        setDragBlocking(false);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public List<View> getAdditionalExitLayers() {
        return CollectionsKt.listOfNotNull(this.editorOverlayLayer);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int getAllInOneSetWay() {
        return this.setWay;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public String getClockStyleType() {
        return "all_in_one";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getDepthLayerOrder(final View view, final View view2) {
        List listListOfNotNull;
        Object objPrevious;
        View view3;
        int regionInt = AllInOneClockStyle.toRegionInt(getCurrentClockBean().getClockDepthType());
        if (regionInt != 0) {
            listListOfNotNull = regionInt != 1 ? CollectionsKt.listOfNotNull((Object[]) new View[]{getBackContentLayer(), view, view2, getForeContentLayer()}) : CollectionsKt.listOfNotNull((Object[]) new View[]{getForeContentLayer(), view, view2, getBackContentLayer()});
        } else {
            listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new View[]{getBackContentLayer(), view, view2, getForeContentLayer()});
        }
        List list = listListOfNotNull;
        final View backContentLayer = getBackContentLayer();
        final View foreContentLayer = getForeContentLayer();
        AllInOneClockStyle.Region clockDepthType = getCurrentClockBean().getClockDepthType();
        String strJoinToString$default = CollectionsKt.joinToString$default(list, " -> ", null, null, 0, null, new Function1() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AllInOneTemplateView.getDepthLayerOrder$lambda$52(backContentLayer, foreContentLayer, view, view2, (View) obj);
            }
        }, 30, null);
        ListIterator listIterator = list.listIterator(list.size());
        do {
            if (!listIterator.hasPrevious()) {
                objPrevious = null;
                break;
            }
            objPrevious = listIterator.previous();
            view3 = (View) objPrevious;
            if (view3 == backContentLayer) {
                break;
            }
        } while (view3 != foreContentLayer);
        Log.d("AllInOneTemplateView", "getDepthLayerOrder: regionInt=" + regionInt + ", depthType=" + clockDepthType + ", order(bottom->top)=[" + strJoinToString$default + "], topClock=" + (objPrevious == backContentLayer ? "hour" : VariableNames.VAR_MINUTE));
        return list;
    }

    /* JADX WARN: Code duplicated, block: B:105:0x03cb  */
    /* JADX WARN: Code duplicated, block: B:15:0x0078  */
    /* JADX WARN: Code duplicated, block: B:8:0x002e  */
    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public List getEditFrames(FrameLayout templateView) {
        int iWidth;
        int dimen;
        AllInOneUtil.RectComputeResult rectComputeResultComputeLayoutResult;
        Rect rect;
        boolean z;
        ImageView imageView;
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            iWidth = (deviceUtil.currentDisplaySize(context).width() / 2) - DeviceConfig.getDimen(R.dimen.kg_classic_max_clock_frame_margin_time_pad_left, getContext());
        } else {
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            if (deviceUtil.isFoldLargeScreen(context2)) {
                Context context3 = getContext();
                Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                iWidth = (deviceUtil.currentDisplaySize(context3).width() / 2) - DeviceConfig.getDimen(R.dimen.kg_classic_max_clock_frame_margin_time_pad_left, getContext());
            } else {
                iWidth = DeviceConfig.getDimen(R.dimen.kg_all_in_one_date_signature_frame_margin_horizontal, getContext());
            }
        }
        if (deviceUtil.isPad()) {
            dimen = DeviceConfig.getDimen(R.dimen.kg_classic_max_clock_frame_pad_extra_width, getContext()) + iWidth;
        } else {
            Context context4 = getContext();
            Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
            if (deviceUtil.isFoldLargeScreen(context4)) {
                dimen = DeviceConfig.getDimen(R.dimen.kg_classic_max_clock_frame_pad_extra_width, getContext()) + iWidth;
            } else {
                Context context5 = getContext();
                Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
                dimen = deviceUtil.currentDisplaySize(context5).width() - DeviceConfig.getDimen(R.dimen.kg_all_in_one_date_signature_frame_margin_horizontal, getContext());
            }
        }
        if (getHourView() == null || getMinView() == null) {
            Log.w("AllInOneTemplateView", "getEditFrames: hour/min view not ready -> empty frames");
            return CollectionsKt.emptyList();
        }
        if (this.lastCalculatedEditFrameRect != null) {
            rect = new Rect(this.currentClockFrameLeft, this.currentClockFrameTop, this.currentClockFrameRight, this.currentClockFrameBottom);
        } else {
            Context context6 = getContext();
            Intrinsics.checkNotNullExpressionValue(context6, "getContext(...)");
            this.initialCenterX = deviceUtil.currentDisplaySize(context6).width() / 2.0f;
            MiuiClockView miuiClockView = getMiuiClockView();
            ClockStyleInfo clockStyleInfo = miuiClockView != null ? miuiClockView.getClockStyleInfo() : null;
            AllInOneBaseInfo allInOneBaseInfo = clockStyleInfo instanceof AllInOneBaseInfo ? (AllInOneBaseInfo) clockStyleInfo : null;
            if (allInOneBaseInfo != null) {
                Context context7 = getContext();
                Intrinsics.checkNotNullExpressionValue(context7, "getContext(...)");
                rectComputeResultComputeLayoutResult = allInOneBaseInfo.computeLayoutResult(context7);
            } else {
                rectComputeResultComputeLayoutResult = null;
            }
            Rect rect2 = rectComputeResultComputeLayoutResult != null ? rectComputeResultComputeLayoutResult.getRect() : null;
            if (rect2 != null) {
                rect = new Rect(rect2);
            } else {
                Log.w("AllInOneTemplateView", "computeLayoutResult returned null, fallback to maxSafeRect");
                rect = new Rect(this.maxSafeRect);
            }
            rect.inset(-DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding_horizontal, getContext()), -DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding, getContext()));
            this.lastCalculatedEditFrameRect = new Rect(rect.left, rect.top, rect.right, rect.bottom);
            this.currentClockFrameLeft = rect.left;
            this.currentClockFrameTop = rect.top;
            this.currentClockFrameRight = rect.right;
            this.currentClockFrameBottom = rect.bottom;
            this.prevHeightLessThanMin = isLessMinHeight();
            this.prevIsDoubleRow = this.isDoubleRow;
        }
        decideHierarchyLayerPosition();
        int dimen2 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_clock_view_bottom_touch_area_horizontal_inset, getContext());
        int dimen3 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_clock_view_bottom_touch_area_height, getContext());
        int dimen4 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_clock_view_corner_touch_area_width, getContext());
        int dimen5 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_clock_view_corner_touch_area_height, getContext());
        int dimen6 = DeviceConfig.getDimen(R.dimen.kg_clock_frame_border_width, getContext()) / 2;
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context8 = getContext();
        Intrinsics.checkNotNullExpressionValue(context8, "getContext(...)");
        float fProvideTemplateEditScale = resourcesProvider.provideTemplateEditScale(context8);
        float f = fProvideTemplateEditScale > 0.0f ? 1.0f / fProvideTemplateEditScale : 1.0f;
        int dimen7 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_corner_handle_anchor_x, getContext());
        int dimen8 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_corner_handle_anchor_y, getContext());
        int i = rect.right - dimen6;
        int i2 = rect.bottom - dimen6;
        float f2 = f - 1.0f;
        int i3 = (int) ((dimen7 - (dimen4 / 2.0f)) * f2);
        int i4 = (int) ((dimen8 - (dimen5 / 2.0f)) * f2);
        Context context9 = getContext();
        Intrinsics.checkNotNullExpressionValue(context9, "getContext(...)");
        if (deviceUtil.isRtl(context9)) {
            int i5 = ((rect.left + dimen6) - (dimen4 - dimen7)) + i3;
            int i6 = (i2 - dimen8) - i4;
            this.cornerHandleRect.set(i5, i6, dimen4 + i5, dimen5 + i6);
        } else {
            int i7 = (i - dimen7) - i3;
            int i8 = (i2 - dimen8) - i4;
            this.cornerHandleRect.set(i7, i8, dimen4 + i7, dimen5 + i8);
        }
        ImageView imageView2 = this.cornerHandleView;
        if (imageView2 == null) {
            final ImageView imageView3 = new ImageView(getContext());
            imageView3.setId(R.id.kg_corner_handle);
            imageView3.setImageDrawable(ContextCompat.getDrawable(imageView3.getContext(), R.drawable.all_in_one_right_corner_handle_normal));
            Context context10 = imageView3.getContext();
            Intrinsics.checkNotNullExpressionValue(context10, "getContext(...)");
            imageView3.setScaleX((deviceUtil.isRtl(context10) ? -1.0f : 1.0f) * f);
            imageView3.setScaleY(f);
            imageView3.setContentDescription(imageView3.getResources().getString(R.string.kg_allinone_drag_corner));
            imageView3.setClickable(true);
            imageView3.setFocusable(true);
            imageView3.setFocusableInTouchMode(true);
            imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AllInOneTemplateView.getEditFrames$lambda$29$lambda$27(imageView3, view);
                }
            });
            imageView3.setImportantForAccessibility(1);
            imageView3.setScaleType(ImageView.ScaleType.FIT_CENTER);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this.cornerHandleRect.width(), this.cornerHandleRect.height());
            layoutParams.gravity = 51;
            Rect rect3 = this.cornerHandleRect;
            layoutParams.leftMargin = rect3.left;
            layoutParams.topMargin = rect3.top;
            imageView3.setLayoutParams(layoutParams);
            this.cornerHandleView = imageView3;
            ensureEditorOverlayLayer().addView(this.cornerHandleView);
            if (!this.dragResizeEnabled && (imageView = this.cornerHandleView) != null) {
                imageView.setVisibility(8);
            }
        } else {
            ViewGroup.LayoutParams layoutParams2 = imageView2 != null ? imageView2.getLayoutParams() : null;
            FrameLayout.LayoutParams layoutParams3 = layoutParams2 instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams2 : null;
            if (layoutParams3 != null) {
                int i9 = layoutParams3.leftMargin;
                Rect rect4 = this.cornerHandleRect;
                if (i9 != rect4.left || layoutParams3.topMargin != rect4.top || layoutParams3.width != rect4.width() || layoutParams3.height != this.cornerHandleRect.height()) {
                    Rect rect5 = this.cornerHandleRect;
                    layoutParams3.leftMargin = rect5.left;
                    layoutParams3.topMargin = rect5.top;
                    layoutParams3.width = rect5.width();
                    layoutParams3.height = this.cornerHandleRect.height();
                    ImageView imageView4 = this.cornerHandleView;
                    if (imageView4 != null) {
                        imageView4.setLayoutParams(layoutParams3);
                    }
                }
            }
        }
        int iMax = Math.max(1, dimen3 / 2);
        int i10 = rect.left + dimen2;
        int i11 = rect.right - dimen2;
        if (i11 > i10) {
            Rect rect6 = this.bottomHandleRect;
            int i12 = rect.bottom;
            rect6.set(i10, (i12 - iMax) - dimen6, i11, (i12 + iMax) - dimen6);
        } else {
            this.bottomHandleRect.setEmpty();
        }
        int dimen9 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_date_frame_padding, getContext());
        int i13 = rect.top;
        int i14 = R.dimen.kg_all_in_one_gap_between_text_frame_and_time_frame;
        Rect rect7 = new Rect(iWidth, i13 - ((DeviceConfig.getDimen(i14, getContext()) + DeviceConfig.getDimen(com.miui.clock.R.dimen.miui_all_in_one_text_area_height, getContext())) + (dimen9 * 2)), dimen, rect.top - DeviceConfig.getDimen(i14, getContext()));
        int dimen10 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_signature_frame_padding, getContext());
        int dimen11 = this.signatureLines == 2 ? DeviceConfig.getDimen(com.miui.clock.R.dimen.miui_all_in_one_signature_height_two_line, getContext()) - DeviceConfig.getDimen(R.dimen.kg_all_in_one_signature_height_two_line_offset, getContext()) : DeviceConfig.getDimen(com.miui.clock.R.dimen.miui_all_in_one_signature_height_one_line, getContext());
        int i15 = rect.bottom;
        int i16 = R.dimen.kg_all_in_one_gap_between_time_frame_and_signature_frame;
        Rect rect8 = new Rect(iWidth, i15 + DeviceConfig.getDimen(i16, getContext()), dimen, rect.bottom + DeviceConfig.getDimen(i16, getContext()) + dimen11 + (dimen10 * 2));
        int i17 = rect7.top;
        this.currentAllVisibleFramesTop = i17;
        this.currentAllVisibleFramesBottom = this.signatureVisible ? rect8.bottom : rect.bottom;
        this.currentDateFrameTop = i17;
        this.currentSignatureFrameBottom = rect8.bottom;
        if (this.isDoubleRow != shouldDoubleRow() && this.dragMode != 0 && !this.isSpringBounceAnimRunning) {
            cancelColonTransitionAnim();
            doPreFadeAnimation();
        } else if (this.isClockLayoutSwitchAnimRunning) {
            updateRatio();
            updateClockScale$default(this, false, false, 2, null);
        } else {
            boolean z2 = this.pendingForceUpdateParams;
            if (z2) {
                Context context11 = getContext();
                Intrinsics.checkNotNullExpressionValue(context11, "getContext(...)");
                z = deviceUtil.isDisplayLandScape(context11) ? false : true;
            }
            this.pendingForceUpdateParams = false;
            updateClockParams(z, z2);
        }
        int i18 = R.id.kg_text_area_frame;
        int i19 = R.string.kg_font_filter_object_label_time;
        return CollectionsKt.mutableListOf(new KgFrameParam(i18, rect7, i19), new KgFrameParam(R.id.kg_time_area_frame, rect, i19), new KgFrameParam(R.id.kg_signature_area_frame, rect8, R.string.kg_classic_signature_label));
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public FontFilter getFontFilter() {
        return FontFilterKt.getALL_IN_ONE_FONT_FILTER();
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public View getFontStyleContentView() {
        getOrCreateClockStyleEditor();
        AbstractPopupEditor clockStyleEditor = getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.style.AllInOneStyleEditor");
        return ((AllInOneStyleEditor) clockStyleEditor).createDialogView();
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView
    @NotNull
    public String getForeClockStyleType() {
        return "all_in_one_minute";
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void getFramesComplete() {
        KgFrame mKgSignatureAreaFrame;
        CharSequence string;
        super.getFramesComplete();
        ensureEditorOverlayLayer().bringToFront();
        KgFrame kgFrame = (KgFrame) findViewById(R.id.kg_text_area_frame);
        this.mKgTextAreaFrame = kgFrame;
        if (kgFrame != null) {
            kgFrame.setFullyRounded(true);
        }
        KgFrame kgFrame2 = this.mKgTextAreaFrame;
        if (kgFrame2 != null) {
            ClassicTextAreaView textAreaView = getTextAreaView();
            CharSequence text = textAreaView != null ? textAreaView.getText() : null;
            if (text == null || StringsKt.isBlank(text)) {
                string = getContext().getString(R.string.kg_dialog_no_content_available_the_moment);
            } else {
                ClassicTextAreaView textAreaView2 = getTextAreaView();
                string = textAreaView2 != null ? textAreaView2.getText() : null;
            }
            kgFrame2.setContentDescription(string);
        }
        setMKgSignatureAreaFrame((KgFrame) findViewById(R.id.kg_signature_area_frame));
        KgFrame mKgSignatureAreaFrame2 = getMKgSignatureAreaFrame();
        if (mKgSignatureAreaFrame2 != null) {
            mKgSignatureAreaFrame2.setFullyRounded(this.signatureLines != 2);
        }
        if (this.signatureLines == 2 && (mKgSignatureAreaFrame = getMKgSignatureAreaFrame()) != null) {
            mKgSignatureAreaFrame.setBorderRadius(DeviceConfig.getDimen(R.dimen.kg_all_in_one_signature_frame_border_radius_two_line, getContext()));
        }
        this.mTimeAreaFrame = (KgFrame) findViewById(R.id.kg_time_area_frame);
        if (getVisibility() != 0) {
            setVisibility(0);
        }
        Integer num = this.pendingPickLiftRestore;
        if (num != null) {
            final int iIntValue = num.intValue();
            this.pendingPickLiftRestore = null;
            Log.d("AllInOneTemplateView", "getFramesComplete: restoring pick panel lift, clickedViewId=" + iIntValue);
            post(new Runnable() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.applyPickPanelLift(true, iIntValue);
                }
            });
        }
    }

    public final Rect getMinDragAllowedRect(Rect rect) {
        int i = rect.left;
        int i2 = R.dimen.kg_all_in_one_clock_view_drag_out_safe_allowed_max_distance;
        return new Rect(i + DeviceConfig.getDimen(i2, getContext()), rect.top + DeviceConfig.getDimen(i2, getContext()), rect.right - DeviceConfig.getDimen(i2, getContext()), rect.bottom - DeviceConfig.getDimen(i2, getContext()));
    }

    public final Rect getMinSafeRect(int i) {
        Rect rect;
        int dimen = DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding_horizontal, getContext());
        int dimen2 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding, getContext());
        int i2 = i + dimen2;
        if (this.isDoubleRow) {
            float f = this.initialCenterX;
            int i3 = com.miui.clock.R.dimen.miui_time_all_in_one_double_line_min_width;
            rect = new Rect((int) (f - (DeviceConfig.getDimen(i3, getContext()) / 2)), i2, (int) (this.initialCenterX + (DeviceConfig.getDimen(i3, getContext()) / 2)), DeviceConfig.getDimen(com.miui.clock.R.dimen.miui_time_all_in_one_double_line_min_height, getContext()) + i2);
        } else {
            float f2 = this.initialCenterX;
            int i4 = com.miui.clock.R.dimen.miui_time_all_in_one_single_line_min_width;
            rect = new Rect((int) (f2 - (DeviceConfig.getDimen(i4, getContext()) / 2)), i2, (int) (this.initialCenterX + (DeviceConfig.getDimen(i4, getContext()) / 2)), DeviceConfig.getDimen(com.miui.clock.R.dimen.miui_time_all_in_one_single_line_min_height, getContext()) + i2);
        }
        return new Rect(rect.left - dimen, rect.top - dimen2, rect.right + dimen, rect.bottom + dimen2);
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    @NotNull
    public AbstractPopupEditor getOrCreateClockStyleEditor() {
        AllInOneTemplateView allInOneTemplateView;
        AbstractPopupEditor clockStyleEditor = getClockStyleEditor();
        if (clockStyleEditor == null) {
            allInOneTemplateView = this;
            clockStyleEditor = new AllInOneStyleEditor(allInOneTemplateView, this, getCurrentClockBean().getFontStyle(), getCurrentClockBean().getClockWeight(), getCurrentClockBean().getHollowStyle(), getFontStyleEditCallback());
        } else {
            allInOneTemplateView = this;
        }
        allInOneTemplateView.setClockStyleEditor(clockStyleEditor);
        AbstractPopupEditor clockStyleEditor2 = allInOneTemplateView.getClockStyleEditor();
        Intrinsics.checkNotNull(clockStyleEditor2);
        return clockStyleEditor2;
    }

    public final Rect getPadDragAllowedRectByTop(Rect rect) {
        int dimen = DeviceConfig.getDimen(R.dimen.kg_all_in_one_clock_view_pad_drag_out_safe_allowed_max_distance, getContext());
        return new Rect(rect.left - dimen, rect.top - dimen, rect.right + dimen, rect.bottom + dimen);
    }

    public final Rect getPadSafeRectByTop(int i) {
        float f;
        float f2;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        Rect rectCurrentDisplaySize = deviceUtil.currentDisplaySize(context);
        int iWidth = rectCurrentDisplaySize.width();
        int iHeight = rectCurrentDisplaySize.height();
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        boolean zIsDisplayLandScape = deviceUtil.isDisplayLandScape(context2);
        int i2 = (int) (iWidth * (zIsDisplayLandScape ? 0.55f : 0.7f));
        if (zIsDisplayLandScape) {
            f = iHeight;
            f2 = 0.5f;
        } else {
            f = iHeight;
            f2 = 0.4f;
        }
        int i3 = (int) (f * f2);
        int dimen = DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding_horizontal, getContext());
        int dimen2 = i3 + (DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding, getContext()) * 2);
        float f3 = this.initialCenterX;
        float f4 = (i2 + (dimen * 2)) / 2.0f;
        return new Rect((int) (f3 - f4), i, (int) (f3 + f4), dimen2 + i);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public int getPickLiftClickedViewId() {
        Integer num = this.pendingPickLiftRestore;
        return num != null ? num.intValue() : this.pickLiftClickedViewId;
    }

    /* JADX WARN: Code duplicated, block: B:24:0x005d  */
    public final void handleDragColonAndLetterSpacingAnimation() {
        AllInOneTemplateView allInOneTemplateView;
        boolean zIsLessMinHeight = isLessMinHeight();
        if (this.isClockLayoutSwitchAnimRunning) {
            this.prevHeightLessThanMin = zIsLessMinHeight;
            this.prevIsDoubleRow = this.isDoubleRow;
            return;
        }
        if (this.prevIsDoubleRow && !this.isDoubleRow && !this.shouldShowColon) {
            setAllEditorColonShow(false);
            setAllClockTranslationX(0.0f, 0.0f);
        }
        if (this.isDoubleRow) {
            allInOneTemplateView = this;
        } else {
            ClockUtil clockUtil = ClockUtil.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (clockUtil.is24HourFormat(context)) {
                boolean z = this.prevHeightLessThanMin;
                if (!z && zIsLessMinHeight) {
                    cancelColonTransitionAnim();
                    setAllMinuteColonAlpha(0.0f);
                    setAllEditorColonShow(true);
                    setAllEditorColonViewVisible(true);
                    ensureColonAarReady();
                    TimeView colonView = getColonView();
                    if (colonView != null) {
                        colonView.setAlpha(0.0f);
                    }
                    startColonShowAnimWithFallback$default(this, 0L, 1, null);
                } else if (z && !zIsLessMinHeight) {
                    cancelColonTransitionAnim();
                    setAllEditorColonShow(true);
                    setAllEditorColonViewVisible(true);
                    ensureColonAarReady();
                    setAllMinuteColonAlpha(0.0f);
                    TimeView colonView2 = getColonView();
                    if (colonView2 != null) {
                        colonView2.setAlpha(1.0f);
                    }
                    allInOneTemplateView = this;
                    doColonTransitionAnim$default(allInOneTemplateView, true, true, 0L, 4, null);
                }
                allInOneTemplateView = this;
            } else {
                allInOneTemplateView = this;
            }
        }
        if (!allInOneTemplateView.isDoubleRow && !allInOneTemplateView.shouldShowColon && !zIsLessMinHeight && !allInOneTemplateView.isColonTransitionAnimRunning) {
            TimeView hourView = allInOneTemplateView.getHourView();
            float translationX = hourView != null ? hourView.getTranslationX() : 0.0f;
            TimeView minView = allInOneTemplateView.getMinView();
            float translationX2 = minView != null ? minView.getTranslationX() : 0.0f;
            if (Math.abs(translationX) > 0.5f || Math.abs(translationX2) > 0.5f) {
                float colonHalfWidth = allInOneTemplateView.getColonHalfWidth();
                if (colonHalfWidth > 0.0f) {
                    allInOneTemplateView.setAllClockTranslationX(colonHalfWidth, -colonHalfWidth);
                }
            }
        }
        if (!allInOneTemplateView.isDoubleRow && allInOneTemplateView.shouldShowColon && !allInOneTemplateView.isColonTransitionAnimRunning && zIsLessMinHeight && allInOneTemplateView.prevHeightLessThanMin) {
            TimeView hourView2 = allInOneTemplateView.getHourView();
            float translationX3 = hourView2 != null ? hourView2.getTranslationX() : 0.0f;
            TimeView minView2 = allInOneTemplateView.getMinView();
            float translationX4 = minView2 != null ? minView2.getTranslationX() : 0.0f;
            if (Math.abs(translationX3) > 0.5f || Math.abs(translationX4) > 0.5f) {
                Log.w("AllInOneTemplateView", "[ColonDebug] GUARD_RESET: translationX residual detected! hourTx=" + translationX3 + ", minTx=" + translationX4 + ", forcing to 0");
                allInOneTemplateView.setAllClockTranslationX(0.0f, 0.0f);
            }
        }
        allInOneTemplateView.prevHeightLessThanMin = zIsLessMinHeight;
        allInOneTemplateView.prevIsDoubleRow = allInOneTemplateView.isDoubleRow;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void hideAllFrame(boolean z) {
        super.hideAllFrame(z);
        BaseTemplateView baseTemplateView = getBaseTemplateView();
        if (baseTemplateView == null || !baseTemplateView.getApplying()) {
            animateHandlesVisibility(false);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void initFrames(Bitmap bitmap, FrameLayout templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        if (!isDualClock()) {
            getEditFrameController().initForeLayer(ensureEditorOverlayLayer());
            getEditFrameController().addAll(getEditFrames(templateView));
            ImageView imageView = this.cornerHandleView;
            if (imageView != null) {
                imageView.bringToFront();
            }
            int dimen = DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_border_radius, getContext()) - (DeviceConfig.getDimen(R.dimen.kg_clock_frame_border_width, getContext()) / 2);
            ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            float fProvideTemplateEditScale = resourcesProvider.provideTemplateEditScale(context);
            if (fProvideTemplateEditScale > 0.0f) {
                int i = (int) (dimen / fProvideTemplateEditScale);
                KgFrame item = getEditFrameController().getItem(R.id.kg_time_area_frame);
                if (item != null) {
                    item.setBorderRadius(i);
                }
            }
            if (this.signatureLines == 2) {
                int dimen2 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_signature_frame_border_radius_two_line, getContext());
                KgFrame item2 = getEditFrameController().getItem(R.id.kg_signature_area_frame);
                if (item2 != null) {
                    item2.setBorderRadius(dimen2);
                }
            }
        }
        BaseTemplateView baseTemplateView = getBaseTemplateView();
        if (baseTemplateView != null) {
            baseTemplateView.resetColorModes();
        }
        requestLayout();
    }

    /* JADX WARN: Code duplicated, block: B:64:0x0334  */
    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void initTemplateBean(TemplateConfig templateConfig) {
        ClockBean defaultAllInOneParams;
        float timeWidth;
        float timeHeight;
        float clockTopRatio;
        float clockBottomRatio;
        boolean zIsDoubleRow;
        AllInOneClockStyle.Region region;
        boolean zIsColonShow;
        int clockWeight;
        ColonAarState colonAarState;
        float timeWidth2;
        float timeHeight2;
        float clockTopRatio2;
        float clockBottomRatio2;
        boolean zIsDoubleRow2;
        AllInOneClockStyle.Region region2;
        int clockWeight2;
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        ClockInfo clockInfo = templateConfig.getClockInfo();
        boolean zIsSkipScreenTransform = false;
        boolean z = clockInfo.getTimeWidth() == 0.0f && clockInfo.getTimeHeight() == 0.0f;
        if (z) {
            AllInOneClockMigration allInOneClockMigration = AllInOneClockMigration.INSTANCE;
            int style = clockInfo.getStyle();
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            defaultAllInOneParams = allInOneClockMigration.getDefaultAllInOneParams(style, context);
        } else {
            defaultAllInOneParams = null;
        }
        getCurrentClockBean().setStyle(clockInfo.getStyle());
        getCurrentClockBean().setPrimaryColor(clockInfo.getPrimaryColor());
        getCurrentClockBean().setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
        getCurrentClockBean().setSecondaryColor(clockInfo.getSecondaryColor());
        getCurrentClockBean().setAutoSecondaryColor(clockInfo.isAutoSecondaryColor());
        getCurrentClockBean().setBlendColor(clockInfo.getBlendColor());
        getCurrentClockBean().setSecondaryBlendColor(clockInfo.getSecondaryBlendColor());
        getCurrentClockBean().setClockEffect(getDisplayClockEffect() >= 0 ? getDisplayClockEffect() : clockInfo.getClockEffect());
        getCurrentClockBean().setGlassTransparency(Float.valueOf(clockInfo.getGlassTransparency()));
        getCurrentClockBean().setFontStyle(clockInfo.getFontStyle());
        getCurrentClockBean().setHollowStyle(clockInfo.getHollowStyle());
        ClockBean currentClockBean = getCurrentClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            timeWidth = defaultAllInOneParams.getTimeWidth();
        } else {
            timeWidth = clockInfo.getTimeWidth();
        }
        currentClockBean.setTimeWidth(timeWidth);
        ClockBean currentClockBean2 = getCurrentClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            timeHeight = defaultAllInOneParams.getTimeHeight();
        } else {
            timeHeight = clockInfo.getTimeHeight();
        }
        currentClockBean2.setTimeHeight(timeHeight);
        ClockBean currentClockBean3 = getCurrentClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            clockTopRatio = defaultAllInOneParams.getClockTopRatio();
        } else {
            clockTopRatio = clockInfo.getClockTopRatio();
        }
        currentClockBean3.setClockTopRatio(clockTopRatio);
        ClockBean currentClockBean4 = getCurrentClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            clockBottomRatio = defaultAllInOneParams.getClockBottomRatio();
        } else {
            clockBottomRatio = clockInfo.getClockBottomRatio();
        }
        currentClockBean4.setClockBottomRatio(clockBottomRatio);
        ClockBean currentClockBean5 = getCurrentClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            zIsDoubleRow = defaultAllInOneParams.isDoubleRow();
        } else {
            zIsDoubleRow = clockInfo.isDoubleRow();
        }
        currentClockBean5.setDoubleRow(zIsDoubleRow);
        ClockBean currentClockBean6 = getCurrentClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            region = defaultAllInOneParams.getClockDepthType();
        } else {
            region = AllInOneClockStyle.toRegion(clockInfo.getClockDepthType());
        }
        currentClockBean6.setClockDepthType(region);
        Log.d("AllInOneTemplateView", "initTemplateBean depth: needInitParams=" + z + ", clockDepthType=" + getCurrentClockBean().getClockDepthType() + ", rawClockDepthType=" + clockInfo.getClockDepthType() + ", clockTopRatio=" + getCurrentClockBean().getClockTopRatio() + ", clockBottomRatio=" + getCurrentClockBean().getClockBottomRatio());
        ClockBean currentClockBean7 = getCurrentClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            zIsColonShow = defaultAllInOneParams.isColonShow();
        } else {
            zIsColonShow = clockInfo.isColonShow();
        }
        currentClockBean7.setColonShow(zIsColonShow);
        ClockBean currentClockBean8 = getCurrentClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            clockWeight = defaultAllInOneParams.getClockWeight();
        } else {
            clockWeight = clockInfo.getClockWeight();
        }
        currentClockBean8.setClockWeight(clockWeight);
        ClockBean currentClockBean9 = getCurrentClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            zIsSkipScreenTransform = defaultAllInOneParams.isSkipScreenTransform();
        } else if (clockInfo.isSkipScreenTransform() || AllInOneUtil.ClockLayoutInput.Companion.shouldSkipTransform(getCurrentClockBean().getTimeWidth(), getCurrentClockBean().getTimeHeight())) {
            zIsSkipScreenTransform = true;
        }
        currentClockBean9.setSkipScreenTransform(zIsSkipScreenTransform);
        Log.d("AllInOneTemplateView", "initTemplateBean skipScreenTransform: needInit=" + z + ", dbSkip=" + clockInfo.isSkipScreenTransform() + ", beanSkip=" + getCurrentClockBean().isSkipScreenTransform() + ", w=" + getCurrentClockBean().getTimeWidth() + ", h=" + getCurrentClockBean().getTimeHeight() + ", ratio=" + (getCurrentClockBean().getTimeHeight() > 0.0f ? getCurrentClockBean().getTimeWidth() / getCurrentClockBean().getTimeHeight() : 0.0f));
        getCurrentClockBean().setClassicSignature(clockInfo.getClassicSignature());
        String classicSignature = clockInfo.getClassicSignature();
        String classicSignature2 = getCurrentClockBean().getClassicSignature();
        String classicSignature3 = getForeClockBean().getClassicSignature();
        StringBuilder sb = new StringBuilder();
        sb.append("initTemplateBean: clockInfo.classicSignature=");
        sb.append(classicSignature);
        sb.append(", hourBean.sig=");
        sb.append(classicSignature2);
        sb.append(", minuteBean.sig=");
        sb.append(classicSignature3);
        Log.d("AllInOneTemplateView", sb.toString());
        getCurrentClockBean().setClassicLine1(clockInfo.getClassicLine1());
        getCurrentClockBean().setClassicLine2(clockInfo.getClassicLine2());
        getCurrentClockBean().setClassicLine3(clockInfo.getClassicLine3());
        getCurrentClockBean().setClassicLine4(clockInfo.getClassicLine4());
        getCurrentClockBean().setClassicLine5(clockInfo.getClassicLine5());
        Log.d("AllInOneTemplateView", "initTemplateBean classicLine: config.classicLine1=" + clockInfo.getClassicLine1() + ", hourBean.classicLine1=" + getCurrentClockBean().getClassicLine1() + ", needInitParams=" + z + ", style=" + clockInfo.getStyle());
        if (getCurrentClockBean().isColonShow()) {
            ClockUtil clockUtil = ClockUtil.INSTANCE;
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            if (clockUtil.is24HourFormat(context2)) {
                colonAarState = ColonAarState.NOTIFIED;
            } else {
                colonAarState = ColonAarState.PENDING;
            }
        } else {
            colonAarState = ColonAarState.PENDING;
        }
        this.colonAarState = colonAarState;
        getForeClockBean().setStyle(clockInfo.getStyle());
        getForeClockBean().setPrimaryColor(clockInfo.getPrimaryColor());
        getForeClockBean().setAutoPrimaryColor(clockInfo.isAutoPrimaryColor());
        getForeClockBean().setSecondaryColor(clockInfo.getSecondaryColor());
        getForeClockBean().setAutoSecondaryColor(clockInfo.isAutoSecondaryColor());
        getForeClockBean().setBlendColor(clockInfo.getBlendColor());
        getForeClockBean().setSecondaryBlendColor(clockInfo.getSecondaryBlendColor());
        getForeClockBean().setClockEffect(getDisplayClockEffect() >= 0 ? getDisplayClockEffect() : clockInfo.getClockEffect());
        getForeClockBean().setGlassTransparency(Float.valueOf(clockInfo.getGlassTransparency()));
        getForeClockBean().setFontStyle(clockInfo.getFontStyle());
        getForeClockBean().setHollowStyle(clockInfo.getHollowStyle());
        ClockBean foreClockBean = getForeClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            timeWidth2 = defaultAllInOneParams.getTimeWidth();
        } else {
            timeWidth2 = clockInfo.getTimeWidth();
        }
        foreClockBean.setTimeWidth(timeWidth2);
        ClockBean foreClockBean2 = getForeClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            timeHeight2 = defaultAllInOneParams.getTimeHeight();
        } else {
            timeHeight2 = clockInfo.getTimeHeight();
        }
        foreClockBean2.setTimeHeight(timeHeight2);
        ClockBean foreClockBean3 = getForeClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            clockTopRatio2 = defaultAllInOneParams.getClockTopRatio();
        } else {
            clockTopRatio2 = clockInfo.getClockTopRatio();
        }
        foreClockBean3.setClockTopRatio(clockTopRatio2);
        ClockBean foreClockBean4 = getForeClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            clockBottomRatio2 = defaultAllInOneParams.getClockBottomRatio();
        } else {
            clockBottomRatio2 = clockInfo.getClockBottomRatio();
        }
        foreClockBean4.setClockBottomRatio(clockBottomRatio2);
        ClockBean foreClockBean5 = getForeClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            zIsDoubleRow2 = defaultAllInOneParams.isDoubleRow();
        } else {
            zIsDoubleRow2 = clockInfo.isDoubleRow();
        }
        foreClockBean5.setDoubleRow(zIsDoubleRow2);
        ClockBean foreClockBean6 = getForeClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            region2 = defaultAllInOneParams.getClockDepthType();
        } else {
            region2 = AllInOneClockStyle.toRegion(clockInfo.getClockDepthType());
        }
        foreClockBean6.setClockDepthType(region2);
        getForeClockBean().setColonShow(getCurrentClockBean().isColonShow());
        ClockBean foreClockBean7 = getForeClockBean();
        if (z) {
            Intrinsics.checkNotNull(defaultAllInOneParams);
            clockWeight2 = defaultAllInOneParams.getClockWeight();
        } else {
            clockWeight2 = clockInfo.getClockWeight();
        }
        foreClockBean7.setClockWeight(clockWeight2);
        getForeClockBean().setSkipScreenTransform(getCurrentClockBean().isSkipScreenTransform());
        getForeClockBean().setClassicSignature(clockInfo.getClassicSignature());
        getForeClockBean().setClassicLine1(clockInfo.getClassicLine1());
        getForeClockBean().setClassicLine2(clockInfo.getClassicLine2());
        getForeClockBean().setClassicLine3(clockInfo.getClassicLine3());
        getForeClockBean().setClassicLine4(clockInfo.getClassicLine4());
        getForeClockBean().setClassicLine5(clockInfo.getClassicLine5());
        super.initTemplateBean(templateConfig);
    }

    public final boolean isDragClockMode() {
        return this.dragMode != 0;
    }

    public final boolean isInLongPressArea(int i, int i2) {
        KgFrameParam frameParam;
        Rect rect;
        KgFrameParam frameParam2;
        KgFrame kgFrame = this.mTimeAreaFrame;
        Rect rect2 = (kgFrame == null || (frameParam2 = kgFrame.getFrameParam()) == null) ? null : frameParam2.getRect();
        Rect rect3 = new Rect(rect2 != null ? rect2.left : 0, rect2 != null ? rect2.top + DeviceConfig.getDimen(com.miui.clock.R.dimen.miui_all_in_one_gap_between_text_area_and_time, getContext()) : 0, rect2 != null ? rect2.right : 0, rect2 != null ? rect2.bottom : 0);
        KgFrame kgFrame2 = this.mKgTextAreaFrame;
        return ((kgFrame2 == null || (frameParam = kgFrame2.getFrameParam()) == null || (rect = frameParam.getRect()) == null) ? false : rect.contains(i, i2)) || rect3.contains(i, i2);
    }

    public final boolean isLessMinHeight() {
        return RangesKt.coerceAtLeast((this.currentClockFrameBottom - this.currentClockFrameTop) - (DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding, getContext()) * 2), 0) <= DeviceConfig.getDimen(R.dimen.kg_all_in_one_min_clock_height, getContext());
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public boolean isPickPanelLifted() {
        return this.pickPanelLiftApplied || this.pendingPickLiftRestore != null;
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView
    public boolean isPlusMaxTemplate() {
        return true;
    }

    public final boolean isSignatureImeVisible() {
        WindowInsetsCompat rootWindowInsets;
        Window window;
        View decorView;
        Context context = getContext();
        View viewFindViewById = null;
        Activity activity = context instanceof Activity ? (Activity) context : null;
        if (activity != null && (window = activity.getWindow()) != null && (decorView = window.getDecorView()) != null) {
            viewFindViewById = decorView.findViewById(android.R.id.content);
        }
        boolean z = (viewFindViewById == null || (rootWindowInsets = ViewCompat.getRootWindowInsets(viewFindViewById)) == null || !rootWindowInsets.isVisible(WindowInsetsCompat.Type.ime())) ? false : true;
        EditText signatureAreaView = getSignatureAreaView();
        return signatureAreaView != null && signatureAreaView.hasFocus() && (z || this.keyboardLiftApplied);
    }

    public final boolean isValidImeBottom(int i) {
        float f = i;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return f < ((float) deviceUtil.currentDisplaySize(context).height()) * 0.8f;
    }

    public final List longPressZoomTargets() {
        KgFrame kgFrame = this.mTimeAreaFrame;
        KgFrame kgFrame2 = this.mKgTextAreaFrame;
        KgFrame mKgSignatureAreaFrame = getMKgSignatureAreaFrame();
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView == null) {
            miuiClockView = null;
        }
        MiuiClockView foreClock = getForeClock();
        return CollectionsKt.listOfNotNull((Object[]) new View[]{kgFrame, kgFrame2, mKgSignatureAreaFrame, miuiClockView, foreClock != null ? foreClock : null});
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onApplied(boolean z) {
        MultiTouchFrameLayout multiTouchFrameLayout;
        if (z && (multiTouchFrameLayout = this.editorOverlayLayer) != null) {
            multiTouchFrameLayout.setVisibility(8);
        }
        super.onApplied(z);
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, android.view.View.OnClickListener
    public void onClick(View v) {
        Boolean boolValueOf;
        EditText signatureAreaView;
        Intrinsics.checkNotNullParameter(v, "v");
        int id = v.getId();
        int i = R.id.kg_signature_area_frame;
        if (id == i) {
            boolean z = this.signatureVisible;
            EditText signatureAreaView2 = getSignatureAreaView();
            Boolean boolValueOf2 = null;
            if (signatureAreaView2 != null) {
                boolValueOf = Boolean.valueOf(signatureAreaView2.getVisibility() == 0);
            } else {
                boolValueOf = null;
            }
            EditText signatureAreaView3 = getSignatureAreaView();
            Boolean boolValueOf3 = signatureAreaView3 != null ? Boolean.valueOf(signatureAreaView3.hasFocus()) : null;
            Log.d("AllInOneTemplateView", "onClick signature: signatureVisible=" + z + ", signatureAreaView.isVisible=" + boolValueOf + ", hasFocus=" + boolValueOf3 + ", keyboardLiftApplied=" + this.keyboardLiftApplied);
            if (!this.signatureVisible || (signatureAreaView = getSignatureAreaView()) == null || signatureAreaView.getVisibility() != 0) {
                boolean z2 = this.signatureVisible;
                EditText signatureAreaView4 = getSignatureAreaView();
                if (signatureAreaView4 != null) {
                    boolValueOf2 = Boolean.valueOf(signatureAreaView4.getVisibility() == 0);
                }
                Log.w("AllInOneTemplateView", "onClick signature: BLOCKED by visibility guard, signatureVisible=" + z2 + ", areaViewVisible=" + boolValueOf2);
                KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                keyboardUtils.hideSoftInput(context);
                clearSelectState();
                showAllFrame();
                applySignatureFrameVisibilityIfNeeded();
                return;
            }
            EditText signatureAreaView5 = getSignatureAreaView();
            if (signatureAreaView5 != null && signatureAreaView5.hasFocus()) {
                Log.w("AllInOneTemplateView", "onClick signature: BLOCKED by hasFocus guard, skipping registerOneShotKeyboardHideListener + super.onClick");
                return;
            }
            registerOneShotKeyboardHideListener();
            EditText signatureAreaView6 = getSignatureAreaView();
            if (signatureAreaView6 != null) {
                signatureAreaView6.setHint("");
            }
            KgFrame mKgSignatureAreaFrame = getMKgSignatureAreaFrame();
            if (mKgSignatureAreaFrame != null) {
                mKgSignatureAreaFrame.setSelected(true);
            }
            BaseTemplateView baseTemplateView = getBaseTemplateView();
            if (baseTemplateView != null) {
                baseTemplateView.onShowBottomSheet();
            }
        }
        super.onClick(v);
        int id2 = v.getId();
        int i2 = R.id.kg_time_area_frame;
        if (id2 == i2 || v.getId() == i || v.getId() == R.id.kg_text_area_frame) {
            animateHandlesVisibility(false);
        }
        if (v.getId() == i2 || v.getId() == R.id.kg_text_area_frame) {
            final int id3 = v.getId();
            AutoBottomSheet mTextAreaBottomSheetModal = id3 == R.id.kg_text_area_frame ? getMTextAreaBottomSheetModal() : getClockBottomSheetModal();
            if (mTextAreaBottomSheetModal != null) {
                mTextAreaBottomSheetModal.setOnAnimationStartListener(new BottomSheetModal.OnShowStartListener() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda0
                    @Override // miuix.bottomsheet.BottomSheetModal.OnShowStartListener
                    public final void onShowStart() {
                        AllInOneTemplateView.onClick$lambda$33(id3, this);
                    }
                });
            }
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public void onClockBeanChange(ClockBean clockBean, boolean z) {
        super.onClockBeanChange(clockBean, z);
        updateBlurContainer();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onClockBottomSheetDismiss() {
        this.pendingPickLiftRestore = null;
        super.onClockBottomSheetDismiss();
        onEditorSubUIDismissed();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onClockInfoUpdate() {
        super.onClockInfoUpdate();
        updateBlurContainer();
        MiuiClockView foreMiuiClockView = getForeMiuiClockView();
        if (foreMiuiClockView != null) {
            foreMiuiClockView.setClockBean(getForeClockBean());
        }
        boolean zIsColonShow = getCurrentClockBean().isColonShow();
        this.shouldShowColon = zIsColonShow;
        setAllEditorColonShow(zIsColonShow);
        cancelColonTransitionAnim();
        Runnable runnable = this.deferredColonRunnable;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
        this.deferredColonRunnable = null;
        setAllClockTranslationX(0.0f, 0.0f);
        TimeView colonView = getColonView();
        if (colonView != null) {
            colonView.setAlpha(0.0f);
        }
        setAllEditorColonViewVisible(false);
        updateClockParams$default(this, true, false, 2, null);
        MiuiClockView miuiClockView = getMiuiClockView();
        View iClockView = miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.SIGNATURE_EDIT_AREA) : null;
        if (iClockView == null) {
            iClockView = null;
        }
        MiuiClockView foreMiuiClockView2 = getForeMiuiClockView();
        View iClockView2 = foreMiuiClockView2 != null ? foreMiuiClockView2.getIClockView(ClockViewType.SIGNATURE_EDIT_AREA) : null;
        if (iClockView2 == null) {
            iClockView2 = null;
        }
        EditText signatureAreaView = getSignatureAreaView();
        EditText editText = signatureAreaView != null ? signatureAreaView : null;
        if (editText != null) {
            editText.setVisibility(this.signatureVisible ? 0 : 8);
        }
        if (getSignatureAreaView() == iClockView) {
            if (iClockView2 != null) {
                iClockView2.setVisibility(8);
            }
        } else if (iClockView != null) {
            iClockView.setVisibility(8);
        }
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        WindowInsetsCompat rootWindowInsets;
        Window window;
        View decorView;
        View viewFindViewById = null;
        Integer numValueOf = configuration != null ? Integer.valueOf(configuration.orientation) : null;
        Integer numValueOf2 = configuration != null ? Integer.valueOf(configuration.screenWidthDp) : null;
        Integer numValueOf3 = configuration != null ? Integer.valueOf(configuration.screenHeightDp) : null;
        Log.i("AllInOneTemplateView", "[ConfigChanged] orientation=" + numValueOf + ", screen=" + numValueOf2 + AnimatedProperty.PROPERTY_NAME_X + numValueOf3 + ", dragMode=" + this.dragMode + ", springRunning=" + this.isSpringBounceAnimRunning + ", overScrollY=" + this.longPressOverScrollY);
        this.cachedRegion = null;
        if (this.dragMode != 0) {
            Folme.useValue(this).clean();
            if (this.dragMode != 0) {
                this.dragMode = 0;
                setDragBlocking(false);
            }
            this.isSpringBounceAnimRunning = false;
        }
        if (this.longPressOverScrollY != 0.0f) {
            this.longPressOverScrollY = 0.0f;
            applyLongPressOverScrollTranslation(0.0f);
        }
        resetLongPressZoomImmediate();
        cancelColonTransitionAnim();
        Runnable runnable = this.deferredColonRunnable;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
        this.deferredColonRunnable = null;
        setAllClockTranslationX(0.0f, 0.0f);
        TimeView colonView = getColonView();
        if (colonView != null) {
            colonView.setAlpha(0.0f);
        }
        setAllEditorColonViewVisible(false);
        if (!this.shouldShowColon) {
            setAllEditorColonShow(false);
        }
        super.onConfigurationChanged(configuration);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.initialCenterX = deviceUtil.currentDisplaySize(context).width() / 2.0f;
        int[] screenWH = DeviceConfig.getScreenWH(getContext());
        int i = screenWH[0];
        int i2 = screenWH[1];
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        Rect rectCreateEditRect$default = ClockDepthAvoidRuleUtils.createEditRect$default(context2, i, i2, ClockDepthAvoidRuleUtils.DEFAULT_CONFIG, null, null, 48, null);
        this.maxSafeRect = rectCreateEditRect$default;
        Log.d("AllInOneTemplateView", "onConfigurationChanged maxSafeRect=" + rectCreateEditRect$default + ", aodScreen=" + i + AnimatedProperty.PROPERTY_NAME_X + i2);
        updateMaxSafeFrameRect();
        this.maxDragAllowedBounds = getMaxDragAllowedRect();
        this.lastCalculatedEditFrameRect = null;
        if (this.keyboardLiftApplied) {
            Log.i("AllInOneTemplateView", "onConfigurationChanged: keyboardLiftApplied=true, force reset translationY to 0");
            KgFrame kgFrame = this.mKgTextAreaFrame;
            KgFrame kgFrame2 = this.mTimeAreaFrame;
            KgFrame mKgSignatureAreaFrame = getMKgSignatureAreaFrame();
            MiuiClockView miuiClockView = getMiuiClockView();
            if (miuiClockView == null) {
                miuiClockView = null;
            }
            MiuiClockView foreClock = getForeClock();
            if (foreClock == null) {
                foreClock = null;
            }
            Iterator it = CollectionsKt.listOfNotNull((Object[]) new View[]{kgFrame, kgFrame2, mKgSignatureAreaFrame, miuiClockView, foreClock, this.cornerHandleView}).iterator();
            while (it.hasNext()) {
                ((View) it.next()).setTranslationY(0.0f);
            }
            this.keyboardLiftApplied = false;
            this.appliedImeBottom = 0;
        }
        if (this.pickPanelLiftApplied) {
            Log.i("AllInOneTemplateView", "onConfigurationChanged: pickPanelLiftApplied=true, force reset translationY to 0");
            KgFrame kgFrame3 = this.mKgTextAreaFrame;
            KgFrame kgFrame4 = this.mTimeAreaFrame;
            KgFrame mKgSignatureAreaFrame2 = getMKgSignatureAreaFrame();
            MiuiClockView miuiClockView2 = getMiuiClockView();
            if (miuiClockView2 == null) {
                miuiClockView2 = null;
            }
            MiuiClockView foreClock2 = getForeClock();
            if (foreClock2 == null) {
                foreClock2 = null;
            }
            Iterator it2 = CollectionsKt.listOfNotNull((Object[]) new View[]{kgFrame3, kgFrame4, mKgSignatureAreaFrame2, miuiClockView2, foreClock2, this.cornerHandleView}).iterator();
            while (it2.hasNext()) {
                ((View) it2.next()).setTranslationY(0.0f);
            }
            this.pickPanelLiftApplied = false;
        }
        Context context3 = getContext();
        Activity activity = context3 instanceof Activity ? (Activity) context3 : null;
        if (activity != null && (window = activity.getWindow()) != null && (decorView = window.getDecorView()) != null) {
            viewFindViewById = decorView.findViewById(android.R.id.content);
        }
        boolean z = (viewFindViewById == null || (rootWindowInsets = ViewCompat.getRootWindowInsets(viewFindViewById)) == null || !rootWindowInsets.isVisible(WindowInsetsCompat.Type.ime())) ? false : true;
        StringBuilder sb = new StringBuilder();
        sb.append("onConfigurationChanged: imeVisible=");
        sb.append(z);
        sb.append(", will reset overlay needDispatch=");
        sb.append(!z);
        Log.i("AllInOneTemplateView", sb.toString());
        if (!z) {
            MultiTouchFrameLayout multiTouchFrameLayout = this.editorOverlayLayer;
            if (multiTouchFrameLayout != null) {
                multiTouchFrameLayout.updateNeedDispatch(false);
            }
            MultiTouchFrameLayout multiTouchFrameLayout2 = (MultiTouchFrameLayout) getForeContentLayer();
            if (multiTouchFrameLayout2 != null) {
                multiTouchFrameLayout2.updateNeedDispatch(false);
            }
            View clickView = getClickView();
            if (clickView != null) {
                clickView.setVisibility(8);
            }
            controlTopButtonState(true);
        }
        rebindWallpaperObserverToOverlay();
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        cancelColonTransitionAnim();
        Runnable runnable = this.deferredColonRunnable;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
        this.deferredColonRunnable = null;
        removeCallbacks(this.longPressRunnable);
        unregisterImeCallback$default(this, null, 1, null);
        resetLongPressZoomImmediate();
        super.onDetachedFromWindow();
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.keyguard.editor.edit.base.EditCallback
    public void onEdited(int i, Object obj) {
        super.onEdited(i, obj);
        if (i != 30 && i != 37 && i != 38 && i != 220 && i != 221) {
            switch (i) {
                case 230:
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                    setGlassTransparency(((Float) obj).floatValue());
                    break;
                case 231:
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                    setHollowStyle(((Integer) obj).intValue());
                    break;
                case 232:
                    setFontStyle(obj instanceof Integer ? (Integer) obj : null);
                    break;
            }
        } else {
            this.maxDragAllowedBounds = getMaxDragAllowedRect();
        }
        if ((obj instanceof ClassicSelectorBean) && i == 100) {
            int classicLine1 = getCurrentClockBean().getClassicLine1();
            ClassicSelectorBean classicSelectorBean = (ClassicSelectorBean) obj;
            getForeClockBean().setClassicLine1(classicSelectorBean.getStyle());
            getCurrentClockBean().setClassicLine1(classicSelectorBean.getStyle());
            Log.d("AllInOneTemplateView", "onEdited TEXT_AREA_STYLE: classicLine1 " + classicLine1 + " → " + classicSelectorBean.getStyle());
            onClockInfoUpdate();
            ClassicTextAreaView textAreaView = getTextAreaView();
            CharSequence text = textAreaView != null ? textAreaView.getText() : null;
            if (text == null || text.length() == 0) {
                KgFrame kgFrame = this.mKgTextAreaFrame;
                if (kgFrame != null) {
                    kgFrame.setImportantForAccessibility(2);
                }
            } else {
                KgFrame kgFrame2 = this.mKgTextAreaFrame;
                if (kgFrame2 != null) {
                    kgFrame2.setImportantForAccessibility(1);
                }
                KgFrame kgFrame3 = this.mKgTextAreaFrame;
                if (kgFrame3 != null) {
                    ClassicTextAreaView textAreaView2 = getTextAreaView();
                    kgFrame3.setContentDescription(textAreaView2 != null ? textAreaView2.getText() : null);
                }
            }
            if (classicLine1 != classicSelectorBean.getStyle()) {
                startPickColor();
            }
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onEditorSubUIDismissed() {
        WindowInsetsCompat rootWindowInsets;
        Window window;
        View decorView;
        WindowInsetsCompat rootWindowInsets2;
        Window window2;
        View decorView2;
        if (!getEditFrameController().getCanShowAll$keyguardeditor_release()) {
            Log.i("AllInOneTemplateView", "onEditorSubUIDismissed: skip, in full-screen preview (canShowAll=false)");
            return;
        }
        if (isSignatureImeVisible()) {
            Log.i("AllInOneTemplateView", "onEditorSubUIDismissed: signature ime visible, keep handle hidden");
            animateHandlesVisibility(false);
            return;
        }
        Log.i("AllInOneTemplateView", "onEditorSubUIDismissed: restore frames and handles; reset overlay dispatch if IME not visible");
        clearSelectState();
        applySignatureFrameVisibilityIfNeeded();
        View viewFindViewById = null;
        if (this.keyboardLiftApplied) {
            Context context = getContext();
            Activity activity = context instanceof Activity ? (Activity) context : null;
            View viewFindViewById2 = (activity == null || (window2 = activity.getWindow()) == null || (decorView2 = window2.getDecorView()) == null) ? null : decorView2.findViewById(android.R.id.content);
            if (viewFindViewById2 == null || (rootWindowInsets2 = ViewCompat.getRootWindowInsets(viewFindViewById2)) == null || !rootWindowInsets2.isVisible(WindowInsetsCompat.Type.ime())) {
                applyKeyboardLift$default(this, false, 0, 2, null);
            }
        }
        if (this.pickPanelLiftApplied) {
            applyPickPanelLift$default(this, false, 0, 2, null);
        }
        Context context2 = getContext();
        Activity activity2 = context2 instanceof Activity ? (Activity) context2 : null;
        if (activity2 != null && (window = activity2.getWindow()) != null && (decorView = window.getDecorView()) != null) {
            viewFindViewById = decorView.findViewById(android.R.id.content);
        }
        boolean z = (viewFindViewById == null || (rootWindowInsets = ViewCompat.getRootWindowInsets(viewFindViewById)) == null || !rootWindowInsets.isVisible(WindowInsetsCompat.Type.ime())) ? false : true;
        StringBuilder sb = new StringBuilder();
        sb.append("onEditorSubUIDismissed: imeVisible=");
        sb.append(z);
        sb.append(", will reset overlay needDispatch=");
        sb.append(!z);
        Log.i("AllInOneTemplateView", sb.toString());
        if (!z) {
            MultiTouchFrameLayout multiTouchFrameLayout = this.editorOverlayLayer;
            if (multiTouchFrameLayout != null) {
                multiTouchFrameLayout.updateNeedDispatch(false);
            }
            MultiTouchFrameLayout multiTouchFrameLayout2 = (MultiTouchFrameLayout) getForeContentLayer();
            if (multiTouchFrameLayout2 != null) {
                multiTouchFrameLayout2.updateNeedDispatch(false);
            }
        }
        animateHandlesVisibility(true);
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void onEditorSubUIShown() {
        animateHandlesVisibility(false);
        clearEditeFocus();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int i = this.dragMode;
        if (i == 0) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        Log.d("AllInOneTemplateView", "onInterceptTouchEvent: intercepted, dragMode=" + i + ", action=" + (motionEvent != null ? MotionEvent.actionToString(motionEvent.getAction()) : null));
        return true;
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void onMiuiClockViewCreated(TemplateConfig templateConfig) {
        String classicSignature;
        int signatureLines;
        Editable text;
        EditFragment editFragment;
        CombineEditView combineEditView;
        HomeTemplateView homeTemplateView;
        TemplateButtonBar primaryButtonBar;
        TemplateButtonBar primaryButtonBar2;
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        MiuiClockView foreMiuiClockView = getForeMiuiClockView();
        if (foreMiuiClockView != null) {
            foreMiuiClockView.setVisibility(!foreMiuiClockView.isDualClock() ? 0 : 8);
        }
        this.shouldShowColon = getCurrentClockBean().isColonShow();
        MiuiClockView miuiClockView = getMiuiClockView();
        Boolean boolValueOf = null;
        setTextAreaView((ClassicTextAreaView) (miuiClockView != null ? miuiClockView.getIClockView(ClockViewType.TEXT_AREA) : null));
        MiuiClockView miuiClockView2 = getMiuiClockView();
        this.mTimeAreaView = miuiClockView2 != null ? miuiClockView2.getIClockView(ClockViewType.TIME_AREA) : null;
        int classicLine1 = getCurrentClockBean().getClassicLine1();
        int classicLine2 = getForeClockBean().getClassicLine1();
        String str = getTextAreaView() != null ? "OK" : "NULL";
        Log.d("AllInOneTemplateView", "onMiuiClockViewCreated classicLine: hourBean.classicLine1=" + classicLine1 + ", minuteBean.classicLine1=" + classicLine2 + ", textAreaView=" + str + ", config.classicLine1=" + templateConfig.getClockInfo().getClassicLine1());
        if (!isDualClock()) {
            Log.d("AllInOneTemplateView", "onMiuiClockViewCreated: initForeClock, hourBean.sig=" + getCurrentClockBean().getClassicSignature() + ", minuteBean.sig=" + getForeClockBean().getClassicSignature() + ", match=" + Intrinsics.areEqual(getCurrentClockBean().getClassicSignature(), getForeClockBean().getClassicSignature()));
            MiuiClockView foreMiuiClockView2 = getForeMiuiClockView();
            if (foreMiuiClockView2 != null) {
                foreMiuiClockView2.init(getForeClockBean(), false);
            }
        }
        setAllEditorColonShow(this.shouldShowColon);
        setAllEditorColonViewVisible(false);
        updateBlurContainer();
        setClickView(findViewById(R.id.click_view));
        int dimen = this.signatureLines == 2 ? DeviceConfig.getDimen(com.miui.clock.R.dimen.miui_all_in_one_signature_height_two_line, getContext()) - DeviceConfig.getDimen(R.dimen.kg_all_in_one_signature_height_two_line_offset, getContext()) : DeviceConfig.getDimen(com.miui.clock.R.dimen.miui_all_in_one_signature_height_one_line, getContext());
        int i = com.miui.clock.R.dimen.miui_all_in_one_gap_between_time_and_sigature;
        this.signatureGapAndHeight = DeviceConfig.getDimen(i, getContext()) + dimen;
        int[] screenWH = DeviceConfig.getScreenWH(getContext());
        int i2 = screenWH[0];
        int i3 = screenWH[1];
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        Rect rectCreateEditRect$default = ClockDepthAvoidRuleUtils.createEditRect$default(context, i2, i3, ClockDepthAvoidRuleUtils.DEFAULT_CONFIG, null, null, 48, null);
        this.maxSafeRect = rectCreateEditRect$default;
        Log.d("AllInOneTemplateView", "onMiuiClockViewCreated maxSafeRect=" + rectCreateEditRect$default + ", aodScreen=" + i2 + AnimatedProperty.PROPERTY_NAME_X + i3);
        updateMaxSafeFrameRect();
        this.maxDragAllowedBounds = getMaxDragAllowedRect();
        super.onMiuiClockViewCreated(templateConfig);
        int dimen2 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_toolbar_shift_down, getContext());
        BaseTemplateView baseTemplateView = getBaseTemplateView();
        if (baseTemplateView != null && (primaryButtonBar2 = baseTemplateView.getPrimaryButtonBar()) != null) {
            ViewGroup.LayoutParams layoutParams = primaryButtonBar2.getGroupContainer().getLayoutParams();
            FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
            if (layoutParams2 != null) {
                layoutParams2.bottomMargin = -dimen2;
                primaryButtonBar2.getGroupContainer().setLayoutParams(layoutParams2);
            }
        }
        BaseTemplateView baseTemplateView2 = getBaseTemplateView();
        if (baseTemplateView2 != null && (editFragment = baseTemplateView2.getEditFragment()) != null && (combineEditView = editFragment.getCombineEditView()) != null && (homeTemplateView = combineEditView.getHomeTemplateView()) != null && (primaryButtonBar = homeTemplateView.getPrimaryButtonBar()) != null) {
            ViewGroup.LayoutParams layoutParams3 = primaryButtonBar.getGroupContainer().getLayoutParams();
            FrameLayout.LayoutParams layoutParams4 = layoutParams3 instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams3 : null;
            if (layoutParams4 != null) {
                layoutParams4.bottomMargin = -dimen2;
                primaryButtonBar.getGroupContainer().setLayoutParams(layoutParams4);
            }
        }
        EditText signatureAreaView = getSignatureAreaView();
        if ((signatureAreaView == null || (text = signatureAreaView.getText()) == null || (classicSignature = text.toString()) == null) && (classicSignature = getCurrentClockBean().getClassicSignature()) == null) {
            classicSignature = "";
        }
        boolean z = classicSignature.length() > 0;
        this.signatureHasContent = z;
        if (z) {
            AllInOneUtil allInOneUtil = AllInOneUtil.INSTANCE;
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            signatureLines = allInOneUtil.getSignatureLines(context2, classicSignature);
        } else {
            signatureLines = 0;
        }
        this.signatureLines = signatureLines;
        this.signatureGapAndHeight = DeviceConfig.getDimen(i, getContext()) + (signatureLines == 2 ? DeviceConfig.getDimen(com.miui.clock.R.dimen.miui_all_in_one_signature_height_two_line, getContext()) - DeviceConfig.getDimen(R.dimen.kg_all_in_one_signature_height_two_line_offset, getContext()) : DeviceConfig.getDimen(com.miui.clock.R.dimen.miui_all_in_one_signature_height_one_line, getContext()));
        EditText signatureAreaView2 = getSignatureAreaView();
        if (signatureAreaView2 != null) {
            signatureAreaView2.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    AllInOneTemplateView.onMiuiClockViewCreated$lambda$17$lambda$16(this.f$0);
                }
            });
        }
        boolean z2 = this.signatureHasContent;
        int i4 = this.signatureLines;
        boolean z3 = this.signatureVisible;
        String hexString = Integer.toHexString(System.identityHashCode(getSignatureAreaView()));
        EditText signatureAreaView3 = getSignatureAreaView();
        if (signatureAreaView3 != null) {
            boolValueOf = Boolean.valueOf(signatureAreaView3.getVisibility() == 0);
        }
        Log.d("AllInOneTemplateView", "SignatureInit: hasContent=" + z2 + ", lines=" + i4 + ", signatureVisible=" + z3 + ", areaView@=" + hexString + ", areaViewVisible=" + boolValueOf);
        boolean successUpdateClockView = getSuccessUpdateClockView();
        StringBuilder sb = new StringBuilder();
        sb.append("onMiuiClockViewCreated: successUpdateClockView=");
        sb.append(successUpdateClockView);
        Log.d("AllInOneTemplateView", sb.toString());
        if (!getSuccessUpdateClockView()) {
            startPickColor();
            updateAvoidRectAfterDrag();
        }
        AllInOneClockStyle.Region clockDepthType = getCurrentClockBean().getClockDepthType();
        Log.d("AllInOneTemplateView", "onMiuiClockViewCreated depth: depthRegion=" + clockDepthType + ", editable=" + editable() + ", backLayer@=" + Integer.toHexString(System.identityHashCode(getBackContentLayer())) + ", foreLayer@=" + Integer.toHexString(System.identityHashCode(getForeContentLayer())) + ", hierarchyLayer@=" + Integer.toHexString(System.identityHashCode(getHierarchyLayer())));
        if (clockDepthType != null) {
            applyDepthLayerOrder(clockDepthType);
        }
    }

    /* JADX WARN: Code duplicated, block: B:260:0x0503  */
    /* JADX WARN: Code duplicated, block: B:265:0x0559 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:270:0x056d  */
    /* JADX WARN: Code duplicated, block: B:272:0x0571  */
    /* JADX WARN: Code duplicated, block: B:274:0x057a  */
    /* JADX WARN: Code duplicated, block: B:275:0x058d  */
    /* JADX WARN: Code duplicated, block: B:276:0x058f  */
    /* JADX WARN: Code duplicated, block: B:278:0x0593  */
    /* JADX WARN: Code duplicated, block: B:279:0x0596  */
    /* JADX WARN: Code duplicated, block: B:282:0x059f  */
    /* JADX WARN: Code duplicated, block: B:285:0x05bd  */
    /* JADX WARN: Code duplicated, block: B:287:0x05c7  */
    /* JADX WARN: Code duplicated, block: B:294:0x05db  */
    /* JADX WARN: Code duplicated, block: B:296:0x05df  */
    /* JADX WARN: Code duplicated, block: B:303:0x05ef  */
    /* JADX WARN: Code duplicated, block: B:307:0x05f7  */
    /* JADX WARN: Code duplicated, block: B:340:0x076a A[PHI: r1 r3 r4
  0x076a: PHI (r1v52 int) = (r1v28 int), (r1v53 int) binds: [B:370:0x07d9, B:339:0x0768] A[DONT_GENERATE, DONT_INLINE]
  0x076a: PHI (r3v22 int) = (r3v8 int), (r3v4 int) binds: [B:370:0x07d9, B:339:0x0768] A[DONT_GENERATE, DONT_INLINE]
  0x076a: PHI (r4v29 int) = (r4v18 int), (r4v14 int) binds: [B:370:0x07d9, B:339:0x0768] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:372:0x07dc  */
    /* JADX WARN: Code duplicated, block: B:377:0x07e7  */
    /* JADX WARN: Code duplicated, block: B:379:0x07ea  */
    /* JADX WARN: Code duplicated, block: B:380:0x07ef  */
    /* JADX WARN: Code duplicated, block: B:389:0x0861  */
    public final void onObservedTouchEvent(MotionEvent motionEvent) {
        ImageView imageView;
        boolean z;
        int i;
        int iMin;
        int iMin2;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        Rect rect;
        boolean z2;
        float f;
        float f2;
        float f3;
        int i8;
        int i9;
        int i10;
        int i11;
        float f4;
        float f5;
        float f6;
        boolean z3;
        float f7;
        float f8;
        Rect rect2;
        float f9;
        final AllInOneTemplateView allInOneTemplateView = this;
        if (allInOneTemplateView.dragResizeEnabled) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                allInOneTemplateView.longPressTopExceedPairBottomTarget = null;
                allInOneTemplateView.longPressBottomExceedPairTopTarget = null;
                float f10 = allInOneTemplateView.longPressOverScrollY;
                if (f10 != 0.0f) {
                    Log.d("AllInOneTemplateView", "[Drag] ACTION_DOWN: clearing stale overScrollY=" + f10 + ", dragMode=" + allInOneTemplateView.dragMode);
                    allInOneTemplateView.longPressOverScrollY = 0.0f;
                    allInOneTemplateView.applyLongPressOverScrollTranslation(0.0f);
                }
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                allInOneTemplateView.isButtonMovedByUser = false;
                allInOneTemplateView.removeCallbacks(allInOneTemplateView.longPressRunnable);
                allInOneTemplateView.pendingLongPress = false;
                allInOneTemplateView.lastTouchX = motionEvent.getRawX();
                allInOneTemplateView.lastTouchY = motionEvent.getRawY();
                int i12 = (int) x;
                int i13 = (int) y;
                if (allInOneTemplateView.cornerHandleRect.contains(i12, i13)) {
                    Log.i("AllInOneTemplateView", "[Drag] DOWN corner: frame=[" + allInOneTemplateView.currentClockFrameLeft + ac.b + allInOneTemplateView.currentClockFrameTop + ac.b + allInOneTemplateView.currentClockFrameRight + ac.b + allInOneTemplateView.currentClockFrameBottom + "]");
                    Object systemService = allInOneTemplateView.getContext().getSystemService("accessibility");
                    if (TalkBackUtil.INSTANCE.isTalkBackEnabled(systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null) && (imageView = allInOneTemplateView.cornerHandleView) != null) {
                        imageView.announceForAccessibility(allInOneTemplateView.getResources().getString(R.string.kg_talkback_slide_up_or_down));
                        Unit unit = Unit.INSTANCE;
                    }
                    allInOneTemplateView.dragMode = 1;
                    allInOneTemplateView.dragInteractionToken++;
                    allInOneTemplateView.setDragBlocking(true);
                    allInOneTemplateView.animateHandlesVisibility(true);
                    return;
                }
                if (!allInOneTemplateView.bottomHandleRect.contains(i12, i13)) {
                    if (allInOneTemplateView.isInLongPressArea(i12, i13)) {
                        allInOneTemplateView.pendingLongPress = true;
                        allInOneTemplateView.postDelayed(allInOneTemplateView.longPressRunnable, allInOneTemplateView.longPressTimeout);
                        return;
                    }
                    return;
                }
                Log.i("AllInOneTemplateView", "[Drag] DOWN bottom: frame=[" + allInOneTemplateView.currentClockFrameLeft + ac.b + allInOneTemplateView.currentClockFrameTop + ac.b + allInOneTemplateView.currentClockFrameRight + ac.b + allInOneTemplateView.currentClockFrameBottom + "]");
                Object systemService2 = allInOneTemplateView.getContext().getSystemService("accessibility");
                if (TalkBackUtil.INSTANCE.isTalkBackEnabled(systemService2 instanceof AccessibilityManager ? (AccessibilityManager) systemService2 : null)) {
                    allInOneTemplateView.announceForAccessibility(allInOneTemplateView.getResources().getString(R.string.kg_talkback_slide_up_or_down));
                }
                allInOneTemplateView.dragMode = 2;
                allInOneTemplateView.dragInteractionToken++;
                allInOneTemplateView.setDragBlocking(true);
                return;
            }
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (allInOneTemplateView.forwardingToWallpaper) {
                        return;
                    }
                    if (allInOneTemplateView.pendingLongPress) {
                        float rawX = motionEvent.getRawX() - allInOneTemplateView.lastTouchX;
                        float rawY = motionEvent.getRawY() - allInOneTemplateView.lastTouchY;
                        if (Math.abs(rawX) > allInOneTemplateView.getTouchSlop() || Math.abs(rawY) > allInOneTemplateView.getTouchSlop()) {
                            allInOneTemplateView.pendingLongPress = false;
                            Log.i("AllInOneTemplateView", "ACTION_MOVE, removeCallbacks.");
                            allInOneTemplateView.removeCallbacks(allInOneTemplateView.longPressRunnable);
                        }
                    }
                    if (allInOneTemplateView.dragMode != 0) {
                        float rawX2 = motionEvent.getRawX() - allInOneTemplateView.lastTouchX;
                        float rawY2 = motionEvent.getRawY() - allInOneTemplateView.lastTouchY;
                        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
                        Context context = allInOneTemplateView.getContext();
                        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                        float f11 = deviceUtil.isRtl(context) ? -rawX2 : rawX2;
                        if (!allInOneTemplateView.isButtonMovedByUser && (Math.abs(rawX2) > ViewConfiguration.get(allInOneTemplateView.getContext()).getScaledTouchSlop() || Math.abs(rawY2) > ViewConfiguration.get(allInOneTemplateView.getContext()).getScaledTouchSlop())) {
                            allInOneTemplateView.isButtonMovedByUser = true;
                            allInOneTemplateView.markEdited();
                            allInOneTemplateView.lastTouchX = motionEvent.getRawX();
                            allInOneTemplateView.lastTouchY = motionEvent.getRawY();
                            return;
                        }
                        float f12 = allInOneTemplateView.currentClockFrameRight + f11;
                        float f13 = allInOneTemplateView.currentClockFrameBottom + rawY2;
                        float f14 = allInOneTemplateView.currentAllVisibleFramesBottom + rawY2;
                        Rect minSafeRect = allInOneTemplateView.getMinSafeRect(allInOneTemplateView.currentClockFrameTop);
                        Rect minDragAllowedRect = allInOneTemplateView.getMinDragAllowedRect(minSafeRect);
                        float f15 = f14;
                        Rect rect3 = allInOneTemplateView.maxSafeFrameRect;
                        int i14 = rect3.bottom;
                        float f16 = i14 - f13;
                        if (allInOneTemplateView.isButtonMovedByUser) {
                            int i15 = allInOneTemplateView.dragMode;
                            if (i15 == 2) {
                                if (allInOneTemplateView.signatureHasContent || !allInOneTemplateView.signatureVisible || f16 >= allInOneTemplateView.signatureGapAndHeight) {
                                    f9 = f15;
                                } else {
                                    allInOneTemplateView.signatureVisible = false;
                                    f9 = f13;
                                }
                                if (!allInOneTemplateView.signatureVisible && f16 >= allInOneTemplateView.signatureGapAndHeight) {
                                    allInOneTemplateView.signatureVisible = true;
                                    f9 = f15;
                                }
                                boolean zIsPad = deviceUtil.isPad();
                                int iMin3 = zIsPad ? Math.min(allInOneTemplateView.maxSafeFrameRect.bottom, zIsPad ? allInOneTemplateView.getPadSafeRectByTop(allInOneTemplateView.currentClockFrameTop).bottom : Integer.MAX_VALUE) : allInOneTemplateView.maxSafeFrameRect.bottom;
                                int iMin4 = zIsPad ? Math.min(allInOneTemplateView.maxDragAllowedBounds.bottom, zIsPad ? allInOneTemplateView.getPadDragAllowedRectByTop(allInOneTemplateView.getPadSafeRectByTop(allInOneTemplateView.currentClockFrameTop)).bottom : Integer.MAX_VALUE) : allInOneTemplateView.maxDragAllowedBounds.bottom;
                                if ((rawY2 <= 0.0f || f9 <= iMin3) && (rawY2 >= 0.0f || f13 >= minSafeRect.bottom)) {
                                    f3 = rawY2;
                                } else {
                                    Log.i("AllInOneTemplateView", "[Drag] BOTTOM boundary damping: dy=" + rawY2 + ", clockFrameTop=" + allInOneTemplateView.currentClockFrameTop + ", clockFrameBottom=" + allInOneTemplateView.currentClockFrameBottom + ", judgmentBottom=" + f9 + ", maxSafeBottom=" + iMin3 + ", minSafeRect.bottom=" + minSafeRect.bottom);
                                    allInOneTemplateView = this;
                                    f3 = (((rawY2 <= 0.0f || f9 <= ((float) iMin4)) && (rawY2 >= 0.0f || f13 >= ((float) minDragAllowedRect.bottom))) ? rawY2 : 0.0f) * allInOneTemplateView.dragDampingFactor;
                                }
                                Unit unit2 = Unit.INSTANCE;
                            } else {
                                if (i15 == 1) {
                                    if (allInOneTemplateView.signatureHasContent || !allInOneTemplateView.signatureVisible || f16 >= allInOneTemplateView.signatureGapAndHeight) {
                                        f5 = f15;
                                    } else {
                                        allInOneTemplateView.signatureVisible = false;
                                        f5 = f13;
                                    }
                                    if (!allInOneTemplateView.signatureVisible && f16 >= allInOneTemplateView.signatureGapAndHeight) {
                                        allInOneTemplateView.signatureVisible = true;
                                        f5 = f15;
                                    }
                                    boolean zIsPad2 = deviceUtil.isPad();
                                    int iMin5 = zIsPad2 ? Math.min(allInOneTemplateView.maxSafeFrameRect.bottom, zIsPad2 ? allInOneTemplateView.getPadSafeRectByTop(allInOneTemplateView.currentClockFrameTop).bottom : Integer.MAX_VALUE) : allInOneTemplateView.maxSafeFrameRect.bottom;
                                    int iMin6 = zIsPad2 ? Math.min(allInOneTemplateView.maxDragAllowedBounds.bottom, zIsPad2 ? allInOneTemplateView.getPadDragAllowedRectByTop(allInOneTemplateView.getPadSafeRectByTop(allInOneTemplateView.currentClockFrameTop)).bottom : Integer.MAX_VALUE) : allInOneTemplateView.maxDragAllowedBounds.bottom;
                                    if ((rawY2 > 0.0f && f5 > iMin5) || (rawY2 < 0.0f && f13 < minSafeRect.bottom)) {
                                        Log.i("AllInOneTemplateView", "[Drag] CORNER boundary damping (vertical): dy=" + rawY2 + ", clockFrameTop=" + allInOneTemplateView.currentClockFrameTop + ", clockFrameBottom=" + allInOneTemplateView.currentClockFrameBottom + ", judgmentBottom=" + f5 + ", maxSafeBottom=" + iMin5 + ", minSafeRect.bottom=" + minSafeRect.bottom);
                                        if ((rawY2 > 0.0f && f5 > iMin6) || (rawY2 < 0.0f && f13 < minDragAllowedRect.bottom)) {
                                            rawY2 = 0.0f;
                                        }
                                        allInOneTemplateView = this;
                                        rawY2 *= allInOneTemplateView.dragDampingFactor;
                                    }
                                    Unit unit3 = Unit.INSTANCE;
                                    Context context2 = allInOneTemplateView.getContext();
                                    Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                                    if (deviceUtil.isRtl(context2)) {
                                        float f17 = allInOneTemplateView.currentClockFrameLeft + (-f6);
                                        if (f6 > 0.0f) {
                                            f6 = f11;
                                            z3 = true;
                                        } else {
                                            f6 = f11;
                                            z3 = false;
                                        }
                                        boolean zIsPad3 = deviceUtil.isPad();
                                        int iMax = zIsPad3 ? Math.max(allInOneTemplateView.maxSafeFrameRect.left, zIsPad3 ? allInOneTemplateView.getPadSafeRectByTop(allInOneTemplateView.currentClockFrameTop).left : Integer.MIN_VALUE) : allInOneTemplateView.maxSafeFrameRect.left;
                                        int iMax2 = zIsPad3 ? Math.max(allInOneTemplateView.maxDragAllowedBounds.left, zIsPad3 ? allInOneTemplateView.getPadDragAllowedRectByTop(allInOneTemplateView.getPadSafeRectByTop(allInOneTemplateView.currentClockFrameTop)).left : Integer.MIN_VALUE) : allInOneTemplateView.maxDragAllowedBounds.left;
                                        if ((z3 && f17 < iMax) || (!z3 && f17 > minSafeRect.left)) {
                                            Log.i("AllInOneTemplateView", "[Drag] CORNER boundary damping (horizontal RTL): dxUsed=" + f6 + ", clockFrameLeft=" + allInOneTemplateView.currentClockFrameLeft + ", newLeftCandidate=" + f17 + ", minSafeLeft=" + iMax + ", minSafeRect.left=" + minSafeRect.left);
                                            f7 = ((!z3 || f17 >= ((float) iMax2)) && (z3 || f17 <= ((float) minDragAllowedRect.left))) ? f6 : 0.0f;
                                            f8 = allInOneTemplateView.dragDampingFactor;
                                            f6 = f7 * f8;
                                        }
                                    } else {
                                        boolean zIsPad4 = deviceUtil.isPad();
                                        int iMin7 = zIsPad4 ? Math.min(allInOneTemplateView.maxSafeFrameRect.right, zIsPad4 ? allInOneTemplateView.getPadSafeRectByTop(allInOneTemplateView.currentClockFrameTop).right : Integer.MAX_VALUE) : allInOneTemplateView.maxSafeFrameRect.right;
                                        int iMin8 = zIsPad4 ? Math.min(allInOneTemplateView.maxDragAllowedBounds.right, zIsPad4 ? allInOneTemplateView.getPadDragAllowedRectByTop(allInOneTemplateView.getPadSafeRectByTop(allInOneTemplateView.currentClockFrameTop)).right : Integer.MAX_VALUE) : allInOneTemplateView.maxDragAllowedBounds.right;
                                        if (f11 <= 0.0f || f12 <= iMin7) {
                                            if (f11 < 0.0f) {
                                                rect2 = minSafeRect;
                                                if (f12 < rect2.right) {
                                                }
                                            }
                                            f6 = f11;
                                        } else {
                                            rect2 = minSafeRect;
                                        }
                                        int i16 = allInOneTemplateView.currentClockFrameRight;
                                        int i17 = rect2.right;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("[Drag] CORNER boundary damping (horizontal LTR): dx=");
                                        float f18 = f11;
                                        sb.append(f18);
                                        sb.append(", clockFrameRight=");
                                        sb.append(i16);
                                        sb.append(", newClockFrameRight=");
                                        sb.append(f12);
                                        sb.append(", maxSafeRight=");
                                        sb.append(iMin7);
                                        sb.append(", minSafeRect.right=");
                                        sb.append(i17);
                                        Log.i("AllInOneTemplateView", sb.toString());
                                        f7 = ((f11 <= 0.0f || f12 <= ((float) iMin8)) && (f18 >= 0.0f || f12 >= ((float) minDragAllowedRect.right))) ? f18 : 0.0f;
                                        f8 = allInOneTemplateView.dragDampingFactor;
                                        f6 = f7 * f8;
                                    }
                                    Context context3 = allInOneTemplateView.getContext();
                                    Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                                    if (deviceUtil.isRtl(context3)) {
                                        int i18 = allInOneTemplateView.currentClockFrameLeft + ((int) (-f6));
                                        allInOneTemplateView.currentClockFrameLeft = i18;
                                        float f19 = allInOneTemplateView.initialCenterX;
                                        allInOneTemplateView.currentClockFrameRight = (int) (f19 + (f19 - i18));
                                    } else {
                                        int i19 = allInOneTemplateView.currentClockFrameRight + ((int) f6);
                                        allInOneTemplateView.currentClockFrameRight = i19;
                                        float f20 = allInOneTemplateView.initialCenterX;
                                        allInOneTemplateView.currentClockFrameLeft = (int) (f20 - (i19 - f20));
                                    }
                                } else if (i15 == 3) {
                                    int i20 = allInOneTemplateView.currentAllVisibleFramesTop;
                                    float f21 = i20 + rawY2;
                                    float f22 = allInOneTemplateView.longPressOverScrollY;
                                    if (f22 == 0.0f) {
                                        if (allInOneTemplateView.signatureHasContent || !allInOneTemplateView.signatureVisible || f16 >= allInOneTemplateView.signatureGapAndHeight) {
                                            f4 = f15;
                                        } else {
                                            allInOneTemplateView.signatureVisible = false;
                                            f4 = f13;
                                        }
                                        if (allInOneTemplateView.signatureVisible || f16 < allInOneTemplateView.signatureGapAndHeight) {
                                            f15 = f4;
                                        } else {
                                            allInOneTemplateView.signatureVisible = true;
                                        }
                                    }
                                    if (rawY2 <= 0.0f || f15 <= i14) {
                                        if (rawY2 < 0.0f) {
                                            rect = rect3;
                                            if (f21 < rect.top) {
                                            }
                                            if (z2 && f22 == 0.0f) {
                                                int i21 = rect.top;
                                                Rect rect4 = allInOneTemplateView.maxSafeRect;
                                                Log.i("AllInOneTemplateView", "[Drag] LONG_PRESS boundary hit: dy=" + rawY2 + ", allVisibleTop=" + i20 + ", newAllVisibleTop=" + f21 + ", maxSafeFrameRect.top=" + i21 + ", maxSafeRect.top=" + rect4.top + ", maxSafeRect.bottom=" + rect4.bottom + ", clockFrameTop=" + allInOneTemplateView.currentClockFrameTop);
                                            }
                                            f = allInOneTemplateView.longPressOverScrollY;
                                            if (f == 0.0f || z2) {
                                                if (f == 0.0f) {
                                                    if (rawY2 < 0.0f) {
                                                        int i22 = allInOneTemplateView.maxSafeFrameRect.top;
                                                        i10 = allInOneTemplateView.currentAllVisibleFramesTop;
                                                        i11 = i22 - i10;
                                                        if (i11 < 0) {
                                                            allInOneTemplateView.currentAllVisibleFramesTop = i10 + i11;
                                                            allInOneTemplateView.currentClockFrameTop += i11;
                                                            allInOneTemplateView.currentAllVisibleFramesBottom += i11;
                                                            allInOneTemplateView.currentClockFrameBottom += i11;
                                                        }
                                                    } else if (rawY2 > 0.0f) {
                                                        if (allInOneTemplateView.signatureVisible) {
                                                            i8 = allInOneTemplateView.currentAllVisibleFramesBottom;
                                                        } else {
                                                            i8 = allInOneTemplateView.currentClockFrameBottom;
                                                        }
                                                        i9 = allInOneTemplateView.maxSafeFrameRect.bottom - i8;
                                                        if (i9 > 0) {
                                                            allInOneTemplateView.currentAllVisibleFramesTop += i9;
                                                            allInOneTemplateView.currentClockFrameTop += i9;
                                                            allInOneTemplateView.currentAllVisibleFramesBottom += i9;
                                                            allInOneTemplateView.currentClockFrameBottom += i9;
                                                        }
                                                    }
                                                }
                                                float f23 = allInOneTemplateView.currentAllVisibleFramesBottom + f;
                                                float f24 = allInOneTemplateView.currentAllVisibleFramesTop + f;
                                                if ((rawY2 <= 0.0f && f23 + rawY2 > allInOneTemplateView.maxDragAllowedBounds.bottom) || (rawY2 < 0.0f && f24 + rawY2 < allInOneTemplateView.maxDragAllowedBounds.top)) {
                                                }
                                                if ((f < 0.0f && rawY2 > 0.0f) || (f <= 0.0f && rawY2 < 0.0f)) {
                                                }
                                                f2 = rawY2 + f;
                                                if ((f > 0.0f || f2 > 0.0f) && (f >= 0.0f || f2 < 0.0f)) {
                                                    allInOneTemplateView.longPressOverScrollY = f2;
                                                    allInOneTemplateView.applyLongPressOverScrollTranslation(f2);
                                                } else {
                                                    allInOneTemplateView.longPressOverScrollY = 0.0f;
                                                    allInOneTemplateView.applyLongPressOverScrollTranslation(0.0f);
                                                }
                                                f3 = 0.0f;
                                            } else {
                                                int i23 = (int) rawY2;
                                                allInOneTemplateView.currentAllVisibleFramesTop += i23;
                                                allInOneTemplateView.currentClockFrameTop += i23;
                                            }
                                        } else {
                                            rect = rect3;
                                        }
                                        z2 = false;
                                        if (z2) {
                                            int i24 = rect.top;
                                            Rect rect5 = allInOneTemplateView.maxSafeRect;
                                            Log.i("AllInOneTemplateView", "[Drag] LONG_PRESS boundary hit: dy=" + rawY2 + ", allVisibleTop=" + i20 + ", newAllVisibleTop=" + f21 + ", maxSafeFrameRect.top=" + i24 + ", maxSafeRect.top=" + rect5.top + ", maxSafeRect.bottom=" + rect5.bottom + ", clockFrameTop=" + allInOneTemplateView.currentClockFrameTop);
                                        }
                                        f = allInOneTemplateView.longPressOverScrollY;
                                        if (f == 0.0f) {
                                        }
                                        if (f == 0.0f) {
                                            if (rawY2 < 0.0f) {
                                                int i25 = allInOneTemplateView.maxSafeFrameRect.top;
                                                i10 = allInOneTemplateView.currentAllVisibleFramesTop;
                                                i11 = i25 - i10;
                                                if (i11 < 0) {
                                                    allInOneTemplateView.currentAllVisibleFramesTop = i10 + i11;
                                                    allInOneTemplateView.currentClockFrameTop += i11;
                                                    allInOneTemplateView.currentAllVisibleFramesBottom += i11;
                                                    allInOneTemplateView.currentClockFrameBottom += i11;
                                                }
                                            } else if (rawY2 > 0.0f) {
                                                if (allInOneTemplateView.signatureVisible) {
                                                    i8 = allInOneTemplateView.currentAllVisibleFramesBottom;
                                                } else {
                                                    i8 = allInOneTemplateView.currentClockFrameBottom;
                                                }
                                                i9 = allInOneTemplateView.maxSafeFrameRect.bottom - i8;
                                                if (i9 > 0) {
                                                    allInOneTemplateView.currentAllVisibleFramesTop += i9;
                                                    allInOneTemplateView.currentClockFrameTop += i9;
                                                    allInOneTemplateView.currentAllVisibleFramesBottom += i9;
                                                    allInOneTemplateView.currentClockFrameBottom += i9;
                                                }
                                            }
                                        }
                                        float f25 = allInOneTemplateView.currentAllVisibleFramesBottom + f;
                                        float f26 = allInOneTemplateView.currentAllVisibleFramesTop + f;
                                        rawY2 = rawY2 <= 0.0f ? 0.0f : 0.0f;
                                        rawY2 = f < 0.0f ? rawY2 * allInOneTemplateView.dragDampingFactor : rawY2 * allInOneTemplateView.dragDampingFactor;
                                        f2 = rawY2 + f;
                                        if (f > 0.0f) {
                                            allInOneTemplateView.longPressOverScrollY = f2;
                                            allInOneTemplateView.applyLongPressOverScrollTranslation(f2);
                                        } else {
                                            allInOneTemplateView.longPressOverScrollY = f2;
                                            allInOneTemplateView.applyLongPressOverScrollTranslation(f2);
                                        }
                                        f3 = 0.0f;
                                    } else {
                                        rect = rect3;
                                    }
                                    z2 = true;
                                    if (z2) {
                                        int i26 = rect.top;
                                        Rect rect6 = allInOneTemplateView.maxSafeRect;
                                        Log.i("AllInOneTemplateView", "[Drag] LONG_PRESS boundary hit: dy=" + rawY2 + ", allVisibleTop=" + i20 + ", newAllVisibleTop=" + f21 + ", maxSafeFrameRect.top=" + i26 + ", maxSafeRect.top=" + rect6.top + ", maxSafeRect.bottom=" + rect6.bottom + ", clockFrameTop=" + allInOneTemplateView.currentClockFrameTop);
                                    }
                                    f = allInOneTemplateView.longPressOverScrollY;
                                    if (f == 0.0f) {
                                    }
                                    if (f == 0.0f) {
                                        if (rawY2 < 0.0f) {
                                            int i27 = allInOneTemplateView.maxSafeFrameRect.top;
                                            i10 = allInOneTemplateView.currentAllVisibleFramesTop;
                                            i11 = i27 - i10;
                                            if (i11 < 0) {
                                                allInOneTemplateView.currentAllVisibleFramesTop = i10 + i11;
                                                allInOneTemplateView.currentClockFrameTop += i11;
                                                allInOneTemplateView.currentAllVisibleFramesBottom += i11;
                                                allInOneTemplateView.currentClockFrameBottom += i11;
                                            }
                                        } else if (rawY2 > 0.0f) {
                                            if (allInOneTemplateView.signatureVisible) {
                                                i8 = allInOneTemplateView.currentAllVisibleFramesBottom;
                                            } else {
                                                i8 = allInOneTemplateView.currentClockFrameBottom;
                                            }
                                            i9 = allInOneTemplateView.maxSafeFrameRect.bottom - i8;
                                            if (i9 > 0) {
                                                allInOneTemplateView.currentAllVisibleFramesTop += i9;
                                                allInOneTemplateView.currentClockFrameTop += i9;
                                                allInOneTemplateView.currentAllVisibleFramesBottom += i9;
                                                allInOneTemplateView.currentClockFrameBottom += i9;
                                            }
                                        }
                                    }
                                    float f27 = allInOneTemplateView.currentAllVisibleFramesBottom + f;
                                    float f28 = allInOneTemplateView.currentAllVisibleFramesTop + f;
                                    if (rawY2 <= 0.0f) {
                                    }
                                    if (f < 0.0f) {
                                    }
                                    f2 = rawY2 + f;
                                    if (f > 0.0f) {
                                        allInOneTemplateView.longPressOverScrollY = f2;
                                        allInOneTemplateView.applyLongPressOverScrollTranslation(f2);
                                    } else {
                                        allInOneTemplateView.longPressOverScrollY = f2;
                                        allInOneTemplateView.applyLongPressOverScrollTranslation(f2);
                                    }
                                    f3 = 0.0f;
                                }
                                f3 = rawY2;
                            }
                            int i28 = (int) f3;
                            allInOneTemplateView.currentClockFrameBottom += i28;
                            allInOneTemplateView.currentAllVisibleFramesBottom += i28;
                            allInOneTemplateView.lastCalculatedEditFrameRect = new Rect(allInOneTemplateView.currentClockFrameLeft, allInOneTemplateView.currentClockFrameTop, allInOneTemplateView.currentClockFrameRight, allInOneTemplateView.currentClockFrameBottom);
                            allInOneTemplateView.lastTouchX = motionEvent.getRawX();
                            allInOneTemplateView.lastTouchY = motionEvent.getRawY();
                            allInOneTemplateView.updateColonShowState();
                            allInOneTemplateView.handleDragColonAndLetterSpacingAnimation();
                            Log.d("AllInOneTemplateView", "[Drag] MOVE: mode=" + allInOneTemplateView.dragMode + ", frame=[" + allInOneTemplateView.currentClockFrameLeft + ac.b + allInOneTemplateView.currentClockFrameTop + ac.b + allInOneTemplateView.currentClockFrameRight + ac.b + allInOneTemplateView.currentClockFrameBottom + "], allVisible=[" + allInOneTemplateView.currentAllVisibleFramesTop + ac.b + allInOneTemplateView.currentAllVisibleFramesBottom + "], topRatio=" + allInOneTemplateView.clockTopRatio + ", bottomRatio=" + allInOneTemplateView.clockBottomRatio + ", overScrollY=" + allInOneTemplateView.longPressOverScrollY);
                            allInOneTemplateView.generateEditFrames();
                            if (allInOneTemplateView.longPressZoomScale == 1.0f) {
                                return;
                            }
                            allInOneTemplateView.applyZoomPivot();
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (actionMasked != 3) {
                    if (actionMasked != 5) {
                        if (actionMasked == 6 && motionEvent.getPointerCount() <= 2) {
                            allInOneTemplateView.forwardingToWallpaper = false;
                            return;
                        }
                        return;
                    }
                    if (allInOneTemplateView.dragMode != 0) {
                        allInOneTemplateView.pendingLongPress = false;
                        allInOneTemplateView.removeCallbacks(allInOneTemplateView.longPressRunnable);
                        return;
                    } else {
                        if (allInOneTemplateView.pendingLongPress) {
                            allInOneTemplateView.pendingLongPress = false;
                            allInOneTemplateView.removeCallbacks(allInOneTemplateView.longPressRunnable);
                        }
                        allInOneTemplateView.forwardingToWallpaper = true;
                        return;
                    }
                }
            }
            if (allInOneTemplateView.forwardingToWallpaper) {
                z = false;
                allInOneTemplateView.forwardingToWallpaper = false;
            } else {
                z = false;
            }
            allInOneTemplateView.isButtonMovedByUser = z;
            allInOneTemplateView.pendingLongPress = z;
            allInOneTemplateView.removeCallbacks(allInOneTemplateView.longPressRunnable);
            int i29 = allInOneTemplateView.dragMode;
            if (i29 != 0) {
                if (i29 == 3) {
                    allInOneTemplateView.startLongPressZoom(z);
                }
                Log.i("AllInOneTemplateView", "[Drag] UP/CANCEL: dragMode=" + allInOneTemplateView.dragMode + ", frame=[" + allInOneTemplateView.currentClockFrameLeft + ac.b + allInOneTemplateView.currentClockFrameTop + ac.b + allInOneTemplateView.currentClockFrameRight + ac.b + allInOneTemplateView.currentClockFrameBottom + "], isDoubleRow=" + allInOneTemplateView.isDoubleRow + ", colonShow=" + allInOneTemplateView.shouldShowColon + ", sigVisible=" + allInOneTemplateView.signatureVisible + ", overScrollY=" + allInOneTemplateView.longPressOverScrollY);
                Rect minSafeRect2 = allInOneTemplateView.getMinSafeRect(allInOneTemplateView.currentClockFrameTop);
                int i30 = allInOneTemplateView.currentClockFrameTop;
                int i31 = allInOneTemplateView.currentClockFrameRight;
                int i32 = allInOneTemplateView.currentClockFrameLeft;
                int i33 = allInOneTemplateView.currentClockFrameBottom;
                int i34 = allInOneTemplateView.dragMode;
                if (i34 == 2) {
                    boolean zIsPad5 = DeviceUtil.INSTANCE.isPad();
                    int iMin9 = zIsPad5 ? Math.min(allInOneTemplateView.maxSafeFrameRect.bottom, zIsPad5 ? allInOneTemplateView.getPadSafeRectByTop(allInOneTemplateView.currentClockFrameTop).bottom : Integer.MAX_VALUE) : allInOneTemplateView.maxSafeFrameRect.bottom;
                    int i35 = allInOneTemplateView.currentClockFrameBottom;
                    i4 = minSafeRect2.bottom;
                    if (i35 < i4) {
                        i33 = i4;
                    } else {
                        int i36 = (allInOneTemplateView.signatureHasContent && allInOneTemplateView.signatureVisible) ? allInOneTemplateView.currentAllVisibleFramesBottom : i35;
                        if (i36 > iMin9) {
                            i33 = i35 - (i36 - iMin9);
                        } else {
                            i = 0;
                        }
                    }
                    i = 1;
                } else if (i34 == 1) {
                    boolean zIsPad6 = DeviceUtil.INSTANCE.isPad();
                    Rect padSafeRectByTop = zIsPad6 ? allInOneTemplateView.getPadSafeRectByTop(allInOneTemplateView.currentClockFrameTop) : null;
                    if (zIsPad6) {
                        int i37 = allInOneTemplateView.maxSafeFrameRect.right;
                        Intrinsics.checkNotNull(padSafeRectByTop);
                        iMin = Math.min(i37, padSafeRectByTop.right);
                    } else {
                        iMin = allInOneTemplateView.maxSafeFrameRect.right;
                    }
                    if (zIsPad6) {
                        int i38 = allInOneTemplateView.maxSafeFrameRect.bottom;
                        Intrinsics.checkNotNull(padSafeRectByTop);
                        iMin2 = Math.min(i38, padSafeRectByTop.bottom);
                    } else {
                        iMin2 = allInOneTemplateView.maxSafeFrameRect.bottom;
                    }
                    int i39 = allInOneTemplateView.currentClockFrameRight;
                    int i40 = minSafeRect2.right;
                    if (i39 < i40) {
                        float f29 = allInOneTemplateView.initialCenterX;
                        i32 = (int) (f29 - (i40 - f29));
                        i31 = i40;
                    } else {
                        if (i39 > iMin) {
                            float f30 = allInOneTemplateView.initialCenterX;
                            i32 = (int) (f30 - (iMin - f30));
                            i31 = iMin;
                        } else {
                            i2 = 0;
                        }
                        i3 = allInOneTemplateView.currentClockFrameBottom;
                        i4 = minSafeRect2.bottom;
                        if (i3 < i4) {
                            i33 = i4;
                        } else {
                            if (allInOneTemplateView.signatureHasContent || !allInOneTemplateView.signatureVisible) {
                                i5 = i3;
                            } else {
                                i5 = allInOneTemplateView.currentAllVisibleFramesBottom;
                            }
                            if (i5 > iMin2) {
                                i33 = i3 - (i5 - iMin2);
                            } else {
                                i = i2;
                            }
                        }
                        i = 1;
                    }
                    i2 = 1;
                    i3 = allInOneTemplateView.currentClockFrameBottom;
                    i4 = minSafeRect2.bottom;
                    if (i3 < i4) {
                        i33 = i4;
                    } else {
                        if (allInOneTemplateView.signatureHasContent) {
                            i5 = i3;
                        } else {
                            i5 = i3;
                        }
                        if (i5 > iMin2) {
                            i33 = i3 - (i5 - iMin2);
                        } else {
                            i = i2;
                        }
                    }
                    i = 1;
                } else if (i34 == 3) {
                    i = (allInOneTemplateView.longPressOverScrollY == 0.0f ? 1 : 0) ^ 1;
                    int dimen = DeviceConfig.getDimen(R.dimen.kg_all_in_one_date_frame_padding, allInOneTemplateView.getContext());
                    float f31 = allInOneTemplateView.longPressOverScrollY;
                    int i41 = allInOneTemplateView.currentAllVisibleFramesTop;
                    int i42 = allInOneTemplateView.currentClockFrameTop;
                    Rect rect7 = allInOneTemplateView.maxSafeRect;
                    Log.i("AllInOneTemplateView", "[Drag] LONG_PRESS UP: overScrollY=" + f31 + ", allVisibleTop=" + i41 + ", clockFrameTop=" + i42 + ", maxSafeRect=" + rect7 + ", maxSafeFrameRect=" + allInOneTemplateView.maxSafeFrameRect + ", topRatioWouldBe=" + (((dimen + i41) - rect7.top) / rect7.height()));
                } else {
                    i = 0;
                }
                if (DeviceUtil.INSTANCE.isPad()) {
                    Rect padSafeRectByTop2 = allInOneTemplateView.getPadSafeRectByTop(allInOneTemplateView.currentClockFrameTop);
                    int i43 = allInOneTemplateView.dragMode;
                    if (i43 != 2) {
                        i6 = 1;
                        if (i43 == 1) {
                        }
                    } else {
                        i6 = 1;
                    }
                    int i44 = padSafeRectByTop2.bottom;
                    if (i33 > i44) {
                        i33 = i44;
                        i = i6;
                    }
                    if (i43 == i6 && i31 > (i7 = padSafeRectByTop2.right)) {
                        float f32 = allInOneTemplateView.initialCenterX;
                        i32 = (int) (f32 - (i7 - f32));
                        i31 = i7;
                        i = 1;
                    }
                }
                Unit unit4 = Unit.INSTANCE;
                if (i != 0) {
                    allInOneTemplateView.longPressTopExceedPairBottomTarget = null;
                    allInOneTemplateView.longPressBottomExceedPairTopTarget = null;
                    final int i45 = allInOneTemplateView.dragInteractionToken;
                    if (allInOneTemplateView.longPressOverScrollY != 0.0f && allInOneTemplateView.dragMode == 3) {
                        Folme.useValue(allInOneTemplateView).to(allInOneTemplateView.overScrollYProperty, Float.valueOf(0.0f), new AnimConfig().setEase(FolmeEase.spring(0.9f, 0.4f)).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView.onObservedTouchEvent.4
                            @Override // miuix.animation.listener.TransitionListener
                            public void onCancel(Object obj) {
                                if (i45 != allInOneTemplateView.dragInteractionToken) {
                                    Log.w("AllInOneTemplateView", "[Drag] LONG_PRESS spring-back stale onCancel, skip");
                                    return;
                                }
                                allInOneTemplateView.longPressOverScrollY = 0.0f;
                                allInOneTemplateView.applyLongPressOverScrollTranslation(0.0f);
                                allInOneTemplateView.correctLongPressFrameTopIfExceedSafe();
                                allInOneTemplateView.finishLongPressSpringBack();
                            }

                            @Override // miuix.animation.listener.TransitionListener
                            public void onComplete(Object obj) {
                                if (i45 != allInOneTemplateView.dragInteractionToken) {
                                    Log.w("AllInOneTemplateView", "[Drag] LONG_PRESS spring-back stale onComplete, skip");
                                    return;
                                }
                                allInOneTemplateView.correctLongPressFrameTopIfExceedSafe();
                                Log.i("AllInOneTemplateView", "[Drag] LONG_PRESS spring-back DONE: overScrollY=" + allInOneTemplateView.longPressOverScrollY + ", allVisibleTop=" + allInOneTemplateView.currentAllVisibleFramesTop + ", clockFrameTop=" + allInOneTemplateView.currentClockFrameTop + ", maxSafeFrameRect.top=" + allInOneTemplateView.maxSafeFrameRect.top);
                                allInOneTemplateView.finishLongPressSpringBack();
                            }
                        }));
                        return;
                    } else {
                        allInOneTemplateView.isSpringBounceAnimRunning = true;
                        Folme.useValue(allInOneTemplateView).to(allInOneTemplateView.handleTopProperty, Integer.valueOf(i30), allInOneTemplateView.handleRightProperty, Integer.valueOf(i31), allInOneTemplateView.handleLeftProperty, Integer.valueOf(i32), allInOneTemplateView.handleBottomProperty, Integer.valueOf(i33), new AnimConfig().setEase(FolmeEase.spring(0.9f, 0.4f)).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView.onObservedTouchEvent.5
                            @Override // miuix.animation.listener.TransitionListener
                            public void onCancel(Object obj) {
                                allInOneTemplateView.isSpringBounceAnimRunning = false;
                                if (i45 != allInOneTemplateView.dragInteractionToken) {
                                    Log.w("AllInOneTemplateView", "[Drag] BOTTOM/CORNER spring-back stale onCancel, skip");
                                } else {
                                    allInOneTemplateView.updateSignatureVisibilityForCurrentFrame();
                                    allInOneTemplateView.finishLongPressSpringBack();
                                }
                            }

                            @Override // miuix.animation.listener.TransitionListener
                            public void onComplete(Object obj) {
                                allInOneTemplateView.isSpringBounceAnimRunning = false;
                                if (i45 != allInOneTemplateView.dragInteractionToken) {
                                    Log.w("AllInOneTemplateView", "[Drag] BOTTOM/CORNER spring-back stale onComplete, skip");
                                } else {
                                    allInOneTemplateView.updateSignatureVisibilityForCurrentFrame();
                                    allInOneTemplateView.finishLongPressSpringBack();
                                }
                            }

                            @Override // miuix.animation.listener.TransitionListener
                            public void onUpdate(Object obj, Collection collection) {
                                if (i45 != allInOneTemplateView.dragInteractionToken) {
                                    return;
                                }
                                allInOneTemplateView.updateSignatureVisibilityForCurrentFrame();
                                allInOneTemplateView.updateColonShowState();
                                allInOneTemplateView.handleDragColonAndLetterSpacingAnimation();
                            }
                        }));
                        return;
                    }
                }
                if (allInOneTemplateView.longPressOverScrollY != 0.0f) {
                    allInOneTemplateView.longPressOverScrollY = 0.0f;
                    allInOneTemplateView.applyLongPressOverScrollTranslation(0.0f);
                }
                allInOneTemplateView.afterDragClock();
                int i46 = allInOneTemplateView.dragMode;
                allInOneTemplateView.setWay = i46;
                boolean z4 = i46 == 3;
                allInOneTemplateView.dragMode = 0;
                if (z4) {
                    allInOneTemplateView.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.setDragBlocking(false);
                        }
                    });
                } else {
                    allInOneTemplateView.setDragBlocking(false);
                }
            }
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView, com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onPreviewChanged(boolean z, boolean z2) {
        if (!z) {
            getEditFrameController().canShowAll(true);
            super.onPreviewChanged(z, z2);
            return;
        }
        getEditFrameController().canShowAll(false);
        hideAllFrame(false);
        hideSignature(true);
        super.onPreviewChanged(z, z2);
        animateHandlesVisibility(false);
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView
    public void onSignatureEditCompleted() {
        if (isSignatureTextChanged()) {
            startPickColor();
        }
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView
    public void onSignatureNeedDispatchChanged(boolean z) {
        Log.d("AllInOneTemplateView", "[Dispatch] onSignatureNeedDispatchChanged: hasFocus=" + z + ", overlayLayer=" + (this.editorOverlayLayer != null));
        MultiTouchFrameLayout multiTouchFrameLayout = this.editorOverlayLayer;
        if (multiTouchFrameLayout != null) {
            multiTouchFrameLayout.updateNeedDispatch(z);
        }
        super.onSignatureNeedDispatchChanged(z);
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String string;
        int signatureLines;
        Pair pair;
        KgFrame mKgSignatureAreaFrame;
        super.onTextChanged(charSequence, i, i2, i3);
        String string2 = "";
        if (charSequence == null || (string = charSequence.toString()) == null) {
            string = "";
        }
        MiuiClockView miuiClockView = getMiuiClockView();
        ClockStyleInfo clockStyleInfo = miuiClockView != null ? miuiClockView.getClockStyleInfo() : null;
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = clockStyleInfo instanceof MiuiClassicClockInfoBase ? (MiuiClassicClockInfoBase) clockStyleInfo : null;
        if (miuiClassicClockInfoBase != null) {
            miuiClassicClockInfoBase.setSignature(string);
        }
        if (!StringsKt.isBlank(string)) {
            string2 = (!StringsKt.endsWith$default(string, "\n", false, 2, (Object) null) || StringsKt.isBlank(StringsKt.trimEnd(string, '\n'))) ? StringsKt.trimEnd(string).toString() : string;
        }
        EditText signatureAreaView = getSignatureAreaView();
        MiuiClockView miuiClockView2 = getMiuiClockView();
        MiuiClockView foreMiuiClockView = signatureAreaView == (miuiClockView2 != null ? miuiClockView2.getIClockView(ClockViewType.SIGNATURE_EDIT_AREA) : null) ? getForeMiuiClockView() : getMiuiClockView();
        ClockStyleInfo clockStyleInfo2 = foreMiuiClockView != null ? foreMiuiClockView.getClockStyleInfo() : null;
        MiuiClassicClockInfoBase miuiClassicClockInfoBase2 = clockStyleInfo2 instanceof MiuiClassicClockInfoBase ? (MiuiClassicClockInfoBase) clockStyleInfo2 : null;
        if (miuiClassicClockInfoBase2 != null) {
            miuiClassicClockInfoBase2.setSignature(string2);
        }
        View iClockView = foreMiuiClockView != null ? foreMiuiClockView.getIClockView(ClockViewType.SIGNATURE_AREA) : null;
        TextView textView = iClockView instanceof TextView ? (TextView) iClockView : null;
        if (textView != null) {
            CharSequence text = textView.getText();
            if (!Intrinsics.areEqual(text != null ? text.toString() : null, string2)) {
                textView.setText(string2);
            }
        }
        int i4 = this.signatureLines;
        if (StringsKt.isBlank(string2)) {
            pair = TuplesKt.to(Boolean.FALSE, 0);
        } else {
            Boolean bool = Boolean.TRUE;
            EditText signatureAreaView2 = getSignatureAreaView();
            if (signatureAreaView2 != null) {
                signatureLines = AllInOneUtil.INSTANCE.getSignatureLines(signatureAreaView2, string2);
            } else {
                AllInOneUtil allInOneUtil = AllInOneUtil.INSTANCE;
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                signatureLines = allInOneUtil.getSignatureLines(context, string2);
            }
            pair = TuplesKt.to(bool, Integer.valueOf(RangesKt.coerceAtMost(signatureLines, 2)));
        }
        boolean zBooleanValue = ((Boolean) pair.component1()).booleanValue();
        int iIntValue = ((Number) pair.component2()).intValue();
        this.signatureHasContent = zBooleanValue;
        if (iIntValue != i4) {
            this.signatureLines = iIntValue;
            KgFrame mKgSignatureAreaFrame2 = getMKgSignatureAreaFrame();
            if (mKgSignatureAreaFrame2 != null) {
                mKgSignatureAreaFrame2.setFullyRounded(this.signatureLines != 2);
            }
            if (this.signatureLines == 2 && (mKgSignatureAreaFrame = getMKgSignatureAreaFrame()) != null) {
                mKgSignatureAreaFrame.setBorderRadius(DeviceConfig.getDimen(R.dimen.kg_all_in_one_signature_frame_border_radius_two_line, getContext()));
            }
            generateEditFrames();
            if (this.isClockLayoutSwitchAnimRunning) {
                return;
            }
            updateClockParams$default(this, true, false, 2, null);
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x003e  */
    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        boolean z2;
        WindowInsetsCompat rootWindowInsets;
        Window window;
        View decorView;
        super.onWindowFocusChanged(z);
        if (z) {
            Context context = getContext();
            Activity activity = context instanceof Activity ? (Activity) context : null;
            View viewFindViewById = (activity == null || (window = activity.getWindow()) == null || (decorView = window.getDecorView()) == null) ? null : decorView.findViewById(android.R.id.content);
            if (viewFindViewById != null && (rootWindowInsets = ViewCompat.getRootWindowInsets(viewFindViewById)) != null) {
                z2 = rootWindowInsets.isVisible(WindowInsetsCompat.Type.ime());
            }
            Log.i("AllInOneTemplateView", "onWindowFocusChanged: hasWindowFocus=" + z + ", imeVisible=" + z2 + ", keyboardLiftApplied=" + this.keyboardLiftApplied);
            if (isSignatureImeVisible()) {
                animateHandlesVisibility(false);
            } else {
                if (z2 || !this.keyboardLiftApplied) {
                    return;
                }
                applyKeyboardLift$default(this, false, 0, 2, null);
            }
        }
    }

    public final void playAuxFrameAlphaAnimation(boolean z) {
        Pair pair;
        List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new KgFrame[]{this.mKgTextAreaFrame, getMKgSignatureAreaFrame()});
        if (listListOfNotNull.isEmpty()) {
            return;
        }
        if (z) {
            AnimState animState = new AnimState("hide_start");
            ViewProperty viewProperty = ViewProperty.ALPHA;
            pair = TuplesKt.to(animState.add(viewProperty, 0.3f, new long[0]), new AnimState("hide_end").add(viewProperty, 0.0f, new long[0]));
        } else {
            AnimState animState2 = new AnimState("show_start");
            ViewProperty viewProperty2 = ViewProperty.ALPHA;
            pair = TuplesKt.to(animState2.add(viewProperty2, 0.0f, new long[0]), new AnimState("show_end").add(viewProperty2, 0.3f, new long[0]));
        }
        AnimState animState3 = (AnimState) pair.component1();
        AnimState animState4 = (AnimState) pair.component2();
        AnimConfig animConfig = TransitionKt.getAnimConfig(this.LONG_PRESS_ZOOM_SPRING_STIFFNESS, this.LONG_PRESS_ZOOM_SPRING_DAMPING);
        Iterator it = listListOfNotNull.iterator();
        while (it.hasNext()) {
            Folme.useAt((KgFrame) it.next()).state().setTo(animState3).to(animState4, animConfig);
        }
    }

    public final void playZoomScaleAnimation(List list, float f, float f2, boolean z) {
        AnimState animState = new AnimState("zoom_start");
        ViewProperty viewProperty = ViewProperty.SCALE_X;
        AnimState animStateAdd = animState.add(viewProperty, f, new long[0]);
        ViewProperty viewProperty2 = ViewProperty.SCALE_Y;
        AnimState animStateAdd2 = animStateAdd.add(viewProperty2, f, new long[0]);
        AnimState animStateAdd3 = new AnimState("zoom_end").add(viewProperty, f2, new long[0]).add(viewProperty2, f2, new long[0]);
        AnimConfig animConfig = TransitionKt.getAnimConfig(this.LONG_PRESS_ZOOM_SPRING_STIFFNESS, this.LONG_PRESS_ZOOM_SPRING_DAMPING);
        if (!z) {
            animConfig = animConfig.addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$playZoomScaleAnimation$config$1
                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    if (this.this$0.getEditFrameController().getCanShowAll$keyguardeditor_release()) {
                        this.this$0.animateHandlesVisibility(true);
                    }
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    if (this.this$0.getEditFrameController().getCanShowAll$keyguardeditor_release()) {
                        this.this$0.animateHandlesVisibility(true);
                    }
                }
            });
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Folme.useAt((View) it.next()).state().setTo(animStateAdd2).to(animStateAdd3, animConfig);
        }
    }

    public final void rebindWallpaperObserverToOverlay() {
        CombinedWallpaperView combinedWallpaperView;
        final MultiTouchFrameLayout multiTouchFrameLayoutEnsureEditorOverlayLayer = ensureEditorOverlayLayer();
        ObserverGestureListener observerGestureListener = new ObserverGestureListener(multiTouchFrameLayoutEnsureEditorOverlayLayer, this) { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$rebindWallpaperObserverToOverlay$composite$1
            public final ObserverClickListener click;
            public final /* synthetic */ AllInOneTemplateView this$0;

            {
                this.this$0 = this;
                Intrinsics.checkNotNull(multiTouchFrameLayoutEnsureEditorOverlayLayer, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.ObserverClickListener");
                this.click = multiTouchFrameLayoutEnsureEditorOverlayLayer;
            }

            @Override // com.miui.keyguard.editor.edit.wallpaper.ObserverClickListener
            public boolean onObserveTouchClick(MotionEvent ev) {
                Intrinsics.checkNotNullParameter(ev, "ev");
                return this.click.onObserveTouchClick(ev);
            }

            @Override // com.miui.keyguard.editor.edit.wallpaper.ObserverGestureListener
            public void onObserveTouchEvent(MotionEvent ev) {
                Intrinsics.checkNotNullParameter(ev, "ev");
                this.this$0.onObservedTouchEvent(ev);
            }
        };
        BaseTemplateView baseTemplateView = getBaseTemplateView();
        if (baseTemplateView != null && (combinedWallpaperView = (CombinedWallpaperView) baseTemplateView.getWallpaperLayer()) != null) {
            combinedWallpaperView.setObserveTouchClickListener(observerGestureListener);
        }
        Log.i("AllInOneTemplateView", "rebindWallpaperObserverToOverlay: overlay=" + Integer.toHexString(System.identityHashCode(multiTouchFrameLayoutEnsureEditorOverlayLayer)) + " bound to CombinedWallpaperView");
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void refreshColorPickupRect() {
        Rect rectCalculateColorPickupRect;
        try {
            MiuiClockView miuiClockView = getMiuiClockView();
            if (miuiClockView == null || (rectCalculateColorPickupRect = miuiClockView.calculateColorPickupRect(Boolean.FALSE)) == null) {
                return;
            }
            this.colorPickupRect = rectCalculateColorPickupRect;
        } catch (Throwable th) {
            Log.w("AllInOneTemplateView", "refreshColorPickupRect: calculateColorPickupRect failed", th);
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x004b  */
    public final void registerOneShotKeyboardHideListener() {
        boolean z;
        Window window;
        View decorView;
        Context context = getContext();
        Activity activity = context instanceof Activity ? (Activity) context : null;
        final View viewFindViewById = (activity == null || (window = activity.getWindow()) == null || (decorView = window.getDecorView()) == null) ? null : decorView.findViewById(android.R.id.content);
        if (viewFindViewById == null) {
            return;
        }
        this.keyboardHideRoot = viewFindViewById;
        if (this.imeAnimCallback != null) {
            ViewCompat.setWindowInsetsAnimationCallback(viewFindViewById, null);
            this.imeAnimCallback = null;
        }
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(viewFindViewById);
        if (rootWindowInsets != null) {
            z = rootWindowInsets.isVisible(WindowInsetsCompat.Type.ime());
        }
        booleanRef.element = z;
        this.imeHideAnimStarted = false;
        this.imeLastBottom = null;
        this.pendingImeBottom = 0;
        final Ref.LongRef longRef = new Ref.LongRef();
        final Ref.IntRef intRef = new Ref.IntRef();
        final Ref.BooleanRef booleanRef2 = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef3 = new Ref.BooleanRef();
        ViewCompat.setOnApplyWindowInsetsListener(viewFindViewById, new OnApplyWindowInsetsListener() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda12
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return AllInOneTemplateView.registerOneShotKeyboardHideListener$lambda$47(this.f$0, view, windowInsetsCompat);
            }
        });
        WindowInsetsAnimationCompat.Callback callback = new WindowInsetsAnimationCompat.Callback() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$registerOneShotKeyboardHideListener$cb$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
            public void onEnd(WindowInsetsAnimationCompat animation) {
                Insets insets;
                Intrinsics.checkNotNullParameter(animation, "animation");
                WindowInsetsCompat rootWindowInsets2 = ViewCompat.getRootWindowInsets(viewFindViewById);
                boolean z2 = rootWindowInsets2 != null && rootWindowInsets2.isVisible(WindowInsetsCompat.Type.ime());
                long jUptimeMillis = longRef.element > 0 ? SystemClock.uptimeMillis() - longRef.element : -1L;
                Log.w("AllInOneTemplateView", "imeAnim onEnd: imeVisible=" + z2 + ", isShowAnimation=" + booleanRef3.element + ", elapsed=" + jUptimeMillis + "ms, hasSeenImeVisible=" + booleanRef.element + ", keyboardLiftApplied=" + this.keyboardLiftApplied + ", imeHideAnimStarted=" + this.imeHideAnimStarted + ", progressCount=" + intRef.element);
                if (z2 && this.pendingImeBottom > 0 && !this.keyboardLiftApplied) {
                    WindowInsetsCompat rootWindowInsets3 = ViewCompat.getRootWindowInsets(viewFindViewById);
                    int i = (rootWindowInsets3 == null || (insets = rootWindowInsets3.getInsets(WindowInsetsCompat.Type.ime())) == null) ? this.pendingImeBottom : insets.bottom;
                    if (i <= 0) {
                        i = this.pendingImeBottom;
                    }
                    this.applyKeyboardLift(true, i);
                    this.pendingImeBottom = 0;
                }
                boolean z3 = jUptimeMillis > 100 || intRef.element > 10;
                if (!z2 && booleanRef.element && !booleanRef3.element && z3) {
                    Log.w("AllInOneTemplateView", "imeAnim onEnd: TRIGGER onImeHidden! elapsed=" + jUptimeMillis + "ms, progressCount=" + intRef.element);
                    onImeHidden(viewFindViewById);
                    return;
                }
                if (!z2 && booleanRef.element && booleanRef3.element) {
                    Log.w("AllInOneTemplateView", "imeAnim onEnd: SKIP onImeHidden (show animation canceled by InsetsController), elapsed=" + jUptimeMillis + "ms");
                    return;
                }
                if (z2 || !booleanRef.element || booleanRef3.element || z3) {
                    return;
                }
                Log.w("AllInOneTemplateView", "imeAnim onEnd: SKIP onImeHidden (system cancel/restart, too short: elapsed=" + jUptimeMillis + "ms, progressCount=" + intRef.element + ")");
            }

            public final void onImeHidden(View view) {
                if (!this.isAttachedToWindow()) {
                    this.unregisterImeCallback(view);
                    return;
                }
                long jUptimeMillis = longRef.element > 0 ? SystemClock.uptimeMillis() - longRef.element : -1L;
                Log.w("AllInOneTemplateView", "onImeHidden: elapsed=" + jUptimeMillis + "ms, imeHideAnimStarted=" + this.imeHideAnimStarted);
                this.dumpEditState("onImeHidden BEFORE cleanup");
                this.clearEditeFocus();
                this.dumpEditState("onImeHidden AFTER clearEditeFocus");
                this.clearSelectState();
                this.showAllFrame();
                if (!this.imeHideAnimStarted) {
                    AllInOneTemplateView.applyKeyboardLift$default(this, false, 0, 2, null);
                    BaseTemplateView baseTemplateView = this.getBaseTemplateView();
                    if (baseTemplateView != null) {
                        baseTemplateView.onDismissBottomSheet();
                    }
                }
                this.animateHandlesVisibility(true);
                Log.i("AllInOneTemplateView", "Reset overlay needDispatch=false after IME hidden (before removing callback)");
                MultiTouchFrameLayout multiTouchFrameLayout = this.editorOverlayLayer;
                if (multiTouchFrameLayout != null) {
                    multiTouchFrameLayout.updateNeedDispatch(false);
                }
                MultiTouchFrameLayout multiTouchFrameLayout2 = (MultiTouchFrameLayout) this.getForeContentLayer();
                if (multiTouchFrameLayout2 != null) {
                    multiTouchFrameLayout2.updateNeedDispatch(false);
                }
                View clickView = this.getClickView();
                if (clickView != null) {
                    clickView.setVisibility(8);
                }
                this.controlTopButtonState(true);
                this.unregisterImeCallback(view);
            }

            public final void onImeHideStart(View view) {
                AllInOneTemplateView.applyKeyboardLift$default(this, false, 0, 2, null);
                BaseTemplateView baseTemplateView = this.getBaseTemplateView();
                if (baseTemplateView != null) {
                    baseTemplateView.onDismissBottomSheet();
                }
            }

            @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
            public WindowInsetsCompat onProgress(WindowInsetsCompat insets, List running) {
                Intrinsics.checkNotNullParameter(insets, "insets");
                Intrinsics.checkNotNullParameter(running, "running");
                boolean zIsVisible = insets.isVisible(WindowInsetsCompat.Type.ime());
                int i = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;
                intRef.element++;
                long jUptimeMillis = longRef.element > 0 ? SystemClock.uptimeMillis() - longRef.element : -1L;
                boolean z2 = booleanRef2.element;
                if (zIsVisible != z2) {
                    Log.w("AllInOneTemplateView", "imeAnim onProgress: imeVisible CHANGED " + z2 + "->" + zIsVisible + ", imeBottom=" + i + ", elapsed=" + jUptimeMillis + "ms, count=" + intRef.element + ", hasSeenImeVisible=" + booleanRef.element + ", keyboardLiftApplied=" + this.keyboardLiftApplied);
                    booleanRef2.element = zIsVisible;
                }
                Integer num = this.imeLastBottom;
                if (num != null) {
                    AllInOneTemplateView allInOneTemplateView = this;
                    Ref.BooleanRef booleanRef4 = booleanRef3;
                    View view = viewFindViewById;
                    int iIntValue = num.intValue();
                    if (!allInOneTemplateView.imeHideAnimStarted && !booleanRef4.element && zIsVisible && i < iIntValue) {
                        allInOneTemplateView.imeHideAnimStarted = true;
                        onImeHideStart(view);
                    }
                }
                this.imeLastBottom = Integer.valueOf(i);
                boolean z3 = jUptimeMillis > 100 || intRef.element > 10;
                if (!zIsVisible && booleanRef.element && !booleanRef3.element && z3) {
                    Log.w("AllInOneTemplateView", "imeAnim onProgress: TRIGGER onImeHidden! elapsed=" + jUptimeMillis + "ms, count=" + intRef.element);
                    onImeHidden(viewFindViewById);
                } else if (!zIsVisible && booleanRef.element && booleanRef3.element) {
                    Log.w("AllInOneTemplateView", "imeAnim onProgress: SKIP onImeHidden (show animation canceled), elapsed=" + jUptimeMillis + "ms");
                } else if (!zIsVisible && booleanRef.element && !booleanRef3.element && !z3) {
                    Log.w("AllInOneTemplateView", "imeAnim onProgress: SKIP onImeHidden (system cancel/restart, too short: elapsed=" + jUptimeMillis + "ms, count=" + intRef.element + ")");
                }
                if (zIsVisible) {
                    booleanRef.element = true;
                }
                return insets;
            }

            @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
            public WindowInsetsAnimationCompat.BoundsCompat onStart(WindowInsetsAnimationCompat animation, WindowInsetsAnimationCompat.BoundsCompat bounds) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                Intrinsics.checkNotNullParameter(bounds, "bounds");
                WindowInsetsCompat rootWindowInsets2 = ViewCompat.getRootWindowInsets(viewFindViewById);
                boolean z2 = rootWindowInsets2 != null && rootWindowInsets2.isVisible(WindowInsetsCompat.Type.ime());
                booleanRef3.element = z2;
                longRef.element = SystemClock.uptimeMillis();
                intRef.element = 0;
                Log.w("AllInOneTemplateView", "imeAnim onStart: imeVisible=" + z2 + ", isShowAnimation=" + booleanRef3.element + ", pendingImeBottom=" + this.pendingImeBottom + ", keyboardLiftApplied=" + this.keyboardLiftApplied + ", hasSeenImeVisible=" + booleanRef.element);
                if (z2 && this.pendingImeBottom > 0 && !this.keyboardLiftApplied) {
                    AllInOneTemplateView allInOneTemplateView = this;
                    allInOneTemplateView.applyKeyboardLift(true, allInOneTemplateView.pendingImeBottom);
                    this.pendingImeBottom = 0;
                }
                WindowInsetsAnimationCompat.BoundsCompat boundsCompatOnStart = super.onStart(animation, bounds);
                Intrinsics.checkNotNullExpressionValue(boundsCompatOnStart, "onStart(...)");
                return boundsCompatOnStart;
            }
        };
        this.imeAnimCallback = callback;
        ViewCompat.setWindowInsetsAnimationCallback(viewFindViewById, callback);
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void resetAllBottomSheet() {
        super.resetAllBottomSheet();
        KeyboardUtils keyboardUtils = KeyboardUtils.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        keyboardUtils.hideSoftInput(context);
        KgFrame mKgSignatureAreaFrame = getMKgSignatureAreaFrame();
        if (mKgSignatureAreaFrame != null) {
            mKgSignatureAreaFrame.setSelected(false);
        }
        EditText signatureAreaView = getSignatureAreaView();
        if (signatureAreaView != null) {
            signatureAreaView.clearFocus();
        }
    }

    public final void resetLiftTranslations() {
        KgFrame kgFrame = this.mKgTextAreaFrame;
        if (kgFrame != null) {
            resetLiftTranslations$reset(kgFrame);
        }
        KgFrame kgFrame2 = this.mTimeAreaFrame;
        if (kgFrame2 != null) {
            resetLiftTranslations$reset(kgFrame2);
        }
        KgFrame mKgSignatureAreaFrame = getMKgSignatureAreaFrame();
        if (mKgSignatureAreaFrame != null) {
            resetLiftTranslations$reset(mKgSignatureAreaFrame);
        }
        MiuiClockView miuiClockView = getMiuiClockView();
        if (miuiClockView == null) {
            miuiClockView = null;
        }
        if (miuiClockView != null) {
            resetLiftTranslations$reset(miuiClockView);
        }
        MiuiClockView foreClock = getForeClock();
        MiuiClockView miuiClockView2 = foreClock != null ? foreClock : null;
        if (miuiClockView2 != null) {
            resetLiftTranslations$reset(miuiClockView2);
        }
        ImageView imageView = this.cornerHandleView;
        if (imageView != null) {
            resetLiftTranslations$reset(imageView);
        }
        TextView scaleTips = getScaleTips();
        if (scaleTips != null) {
            resetLiftTranslations$reset(scaleTips);
        }
    }

    public final void resetLongPressZoomImmediate() {
        this.longPressZoomScale = 1.0f;
        for (View view : longPressZoomTargets()) {
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
            view.setPivotX(view.getWidth() / 2.0f);
            view.setPivotY(view.getHeight() / 2.0f);
        }
        Iterator it = CollectionsKt.listOfNotNull((Object[]) new KgFrame[]{this.mKgTextAreaFrame, getMKgSignatureAreaFrame()}).iterator();
        while (it.hasNext()) {
            ((KgFrame) it.next()).setAlpha(0.3f);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void restorePickPanelLift(int i) {
        Log.d("AllInOneTemplateView", "restorePickPanelLift: pending for getFramesComplete, clickedViewId=" + i);
        this.pendingPickLiftRestore = Integer.valueOf(i);
    }

    public final void setAllClockTranslationX(float f, float f2) {
        TimeView hourView = getHourView();
        if (hourView != null) {
            hourView.setTranslationX(f);
        }
        TimeView minView = getMinView();
        if (minView != null) {
            minView.setTranslationX(f2);
        }
        TimeView backMinView = getBackMinView();
        if (backMinView != null) {
            if (backMinView == getMinView()) {
                backMinView = null;
            }
            if (backMinView != null) {
                backMinView.setTranslationX(f2);
            }
        }
    }

    public final boolean shouldDoubleRow() {
        int dimen = DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding_horizontal, getContext());
        int dimen2 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding, getContext());
        int i = (this.currentClockFrameRight - this.currentClockFrameLeft) - (dimen * 2);
        boolean z = false;
        int iCoerceAtLeast = RangesKt.coerceAtLeast(i, 0);
        int iCoerceAtLeast2 = RangesKt.coerceAtLeast((this.currentClockFrameBottom - this.currentClockFrameTop) - (dimen2 * 2), 0);
        if (iCoerceAtLeast2 > 0 && iCoerceAtLeast > 0) {
            float f = iCoerceAtLeast / iCoerceAtLeast2;
            z = f < 0.75f;
            boolean z2 = this.isDoubleRow;
            if (z != z2) {
                Log.i("AllInOneTemplateView", "[LayoutSwitch] shouldDoubleRow changed: " + z2 + " -> " + z + ", ratio=" + f + ", w=" + iCoerceAtLeast + ", h=" + iCoerceAtLeast2);
                this.isDoubleRow = z;
                getCurrentClockBean().setDoubleRow(this.isDoubleRow);
            }
        }
        return z;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public void showAllFrame() {
        super.showAllFrame();
        BaseTemplateView baseTemplateView = getBaseTemplateView();
        if (baseTemplateView == null || !baseTemplateView.getApplying()) {
            animateHandlesVisibility(true);
        }
        applySignatureFrameVisibilityIfNeeded();
    }

    public final void startColonShowAnimWithFallback(final long j) {
        float colonHalfWidth = getColonHalfWidth();
        if (colonHalfWidth > 0.0f) {
            setAllClockTranslationX(colonHalfWidth, -colonHalfWidth);
            doColonTransitionAnim(false, true, j);
            return;
        }
        Runnable runnable = this.deferredColonRunnable;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
        Runnable runnable2 = new Runnable() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                AllInOneTemplateView.startColonShowAnimWithFallback$lambda$68(this.f$0, j);
            }
        };
        this.deferredColonRunnable = runnable2;
        post(runnable2);
    }

    public final void startLongPressZoom(boolean z) {
        if (z) {
            captureZoomPivot();
            applyZoomPivot();
        }
        float f = this.longPressZoomScale;
        float f2 = z ? this.LONG_PRESS_ZOOM_TARGET : 1.0f;
        this.longPressZoomScale = f2;
        List listLongPressZoomTargets = longPressZoomTargets();
        if (listLongPressZoomTargets.isEmpty()) {
            return;
        }
        playZoomScaleAnimation(listLongPressZoomTargets, f, f2, z);
        playAuxFrameAlphaAnimation(z);
        if (z) {
            animateHandlesVisibility(false);
        }
    }

    public final void startPickColor() {
        Log.d("AllInOneTemplateView", "startPickColor");
        refreshColorPickupRect();
        final Rect rect = new Rect(this.colorPickupRect);
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return AllInOneTemplateView.startPickColor$lambda$56(this.f$0);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AllInOneTemplateView.startPickColor$lambda$57(this.f$0, rect, (Bitmap) obj);
            }
        });
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Rect templateHierarchyAvoidRect() {
        Rect depthAvoidRect;
        MiuiClockView miuiClockView = getMiuiClockView();
        return (miuiClockView == null || (depthAvoidRect = miuiClockView.getDepthAvoidRect()) == null) ? new Rect() : depthAvoidRect;
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public Set unSupportHierarchyTypes() {
        return ScreenshotSourceKt.getDefaultNotSupportTypes();
    }

    public final void unregisterImeCallback(View view) {
        if (view != null) {
            ViewCompat.setOnApplyWindowInsetsListener(view, null);
            ViewCompat.setWindowInsetsAnimationCallback(view, null);
        }
        this.imeAnimCallback = null;
        this.keyboardHideRoot = null;
    }

    public final void updateAvoidRectAfterDrag() {
        if (editable()) {
            HierarchyImageView hierarchyImageView = (HierarchyImageView) getHierarchyLayer();
            if (hierarchyImageView != null) {
                Rect rectHierarchyAvoidRect = hierarchyAvoidRect();
                HierarchyImageAvoidController avoidController = hierarchyImageView.getAvoidController();
                if (avoidController != null) {
                    avoidController.resetAvoidRect(rectHierarchyAvoidRect);
                }
                HierarchyImageAvoidController avoidController2 = hierarchyImageView.getAvoidController();
                if (avoidController2 != null) {
                    avoidController2.requestCheckOnce();
                }
                Log.i("AllInOneTemplateView", "Drag end - new avoid rect: " + rectHierarchyAvoidRect);
            }
            BaseTemplateView baseTemplateView = getBaseTemplateView();
            if (baseTemplateView != null) {
                baseTemplateView.startCheckDepthVideoAvoid();
            }
        }
    }

    public final void updateBlurContainer() {
        int regionInt = AllInOneClockStyle.toRegionInt(getCurrentClockBean().getClockDepthType());
        Log.d("AllInOneTemplateView", "updateBlurContainer: regionInt=" + regionInt + ", blurOwner=" + (regionInt == 1 ? "hour(miuiClockView)" : "minute(foreMiuiClockView)"));
        if (regionInt == 1) {
            MiuiClockView foreMiuiClockView = getForeMiuiClockView();
            if (foreMiuiClockView != null) {
                foreMiuiClockView.setBackgroundBlurContainer(null);
            }
            MiuiClockView miuiClockView = getMiuiClockView();
            if (miuiClockView != null) {
                MiuiClockView foreMiuiClockView2 = getForeMiuiClockView();
                miuiClockView.setBackgroundBlurContainer(foreMiuiClockView2 != null ? foreMiuiClockView2.getBackgroundBlurContainer() : null);
                return;
            }
            return;
        }
        MiuiClockView miuiClockView2 = getMiuiClockView();
        if (miuiClockView2 != null) {
            miuiClockView2.setBackgroundBlurContainer(null);
        }
        MiuiClockView foreMiuiClockView3 = getForeMiuiClockView();
        if (foreMiuiClockView3 != null) {
            MiuiClockView miuiClockView3 = getMiuiClockView();
            foreMiuiClockView3.setBackgroundBlurContainer(miuiClockView3 != null ? miuiClockView3.getBackgroundBlurContainer() : null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView, com.miui.keyguard.editor.edit.base.BaseClockView
    public void updateClockColor(ColorData colorData) {
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        MiuiClockView miuiClockView = getMiuiClockView();
        boolean z = false;
        if (miuiClockView != null) {
            miuiClockView.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        MiuiClockView foreMiuiClockView = getForeMiuiClockView();
        if (foreMiuiClockView != null) {
            foreMiuiClockView.setClockPalette(0, colorData.getDarkClockArea(), colorData.getClockPalette());
        }
        if (getMiuiClockView() != null && getForeMiuiClockView() != null) {
            z = true;
        }
        setSuccessUpdateClockView(z);
    }

    public final void updateClockParams(boolean z, boolean z2) {
        if (z || this.dragMode != 0) {
            updateRatio();
            updateClockScale$default(this, false, z2, 1, null);
        }
    }

    public final void updateClockScale(boolean z, boolean z2) {
        ClockStyleInfo clockStyleInfo;
        ClockStyleInfo clockStyleInfo2;
        if (this.timeWidth == 0 && this.timeHeight == 0) {
            return;
        }
        AllInOneClockStyle.Region clockDepthType = null;
        Boolean boolValueOf = z ? Boolean.valueOf(this.isDoubleRow) : null;
        boolean zIsSkipScreenTransform = getCurrentClockBean().isSkipScreenTransform();
        MiuiClockView miuiClockView = getMiuiClockView();
        AllInOneClockStyle.Region clockDepthType2 = (miuiClockView == null || (clockStyleInfo2 = miuiClockView.getClockStyleInfo()) == null) ? null : clockStyleInfo2.getClockDepthType();
        if (!z2) {
            MiuiClockView miuiClockView2 = getMiuiClockView();
            if (miuiClockView2 != null) {
                MiuiClockControllerExtKt.setAllInOneClockRectParamsOptional(miuiClockView2, getCurrentClockBean().getClockDepthType(), this.clockTopRatio, this.clockBottomRatio, this.timeWidth, this.timeHeight, getCurrentClockBean().getClockWeight(), boolValueOf, Boolean.valueOf(zIsSkipScreenTransform));
            }
            MiuiClockView foreClock = getForeClock();
            if (foreClock != null) {
                MiuiClockControllerExtKt.setAllInOneClockRectParamsOptional(foreClock, getCurrentClockBean().getClockDepthType(), this.clockTopRatio, this.clockBottomRatio, this.timeWidth, this.timeHeight, getCurrentClockBean().getClockWeight(), boolValueOf, Boolean.valueOf(zIsSkipScreenTransform));
            }
        }
        MiuiClockView miuiClockView3 = getMiuiClockView();
        if (miuiClockView3 != null && (clockStyleInfo = miuiClockView3.getClockStyleInfo()) != null) {
            clockDepthType = clockStyleInfo.getClockDepthType();
        }
        if (clockDepthType2 != clockDepthType) {
            Log.d("AllInOneTemplateView", "updateClockScale: depthType changed " + clockDepthType2 + " -> " + clockDepthType + ", updating blur container");
            updateBlurContainer();
        }
        AllInOneUtil.ClockLayoutInput clockLayoutInputConvertToStorageFormat$default = AllInOneUtil.convertToStorageFormat$default(AllInOneUtil.INSTANCE, new AllInOneBaseInfo.AllInOneRectParams(getCurrentClockBean().getClockDepthType(), this.clockTopRatio, this.clockBottomRatio, this.timeWidth, this.timeHeight), getContext(), this.isDoubleRow, false, 8, null);
        float timeWidth = clockLayoutInputConvertToStorageFormat$default != null ? clockLayoutInputConvertToStorageFormat$default.getTimeWidth() : DeviceConfig.pxToDp(this.timeWidth, getContext());
        float timeHeight = clockLayoutInputConvertToStorageFormat$default != null ? clockLayoutInputConvertToStorageFormat$default.getTimeHeight() : DeviceConfig.pxToDp(this.timeHeight, getContext());
        if (!z2) {
            getCurrentClockBean().setTimeWidth(timeWidth);
            getCurrentClockBean().setTimeHeight(timeHeight);
            getCurrentClockBean().setClockTopRatio(this.clockTopRatio);
            getCurrentClockBean().setClockBottomRatio(this.clockBottomRatio);
            if (this.dragMode != 0) {
                getCurrentClockBean().setSkipScreenTransform(AllInOneUtil.ClockLayoutInput.Companion.shouldSkipTransform(this.timeWidth, this.timeHeight));
            }
            getForeClockBean().setTimeWidth(timeWidth);
            getForeClockBean().setTimeHeight(timeHeight);
            getForeClockBean().setClockTopRatio(this.clockTopRatio);
            getForeClockBean().setClockBottomRatio(this.clockBottomRatio);
            getForeClockBean().setSkipScreenTransform(getCurrentClockBean().isSkipScreenTransform());
        }
        if (z) {
            getCurrentClockBean().setDoubleRow(this.isDoubleRow);
            getCurrentClockBean().setColonShow(this.shouldShowColon);
            getForeClockBean().setDoubleRow(this.isDoubleRow);
            getForeClockBean().setColonShow(this.shouldShowColon);
        }
    }

    public final void updateColonShowState() {
        TimeView colonView;
        boolean z = !this.isDoubleRow && isLessMinHeight();
        this.shouldShowColon = z;
        if (z != getCurrentClockBean().isColonShow()) {
            Log.i("AllInOneTemplateView", "[ColonState] changed: " + getCurrentClockBean().isColonShow() + " -> " + this.shouldShowColon + ", isDoubleRow=" + this.isDoubleRow + ", aarState=" + this.colonAarState);
            if (this.shouldShowColon && this.colonAarState == ColonAarState.PENDING) {
                this.colonAarState = ColonAarState.NOTIFIED;
                setAllEditorColonViewVisible(true);
            }
            if (!this.shouldShowColon) {
                TimeView colonView2 = getColonView();
                if (!Intrinsics.areEqual(colonView2 != null ? Float.valueOf(colonView2.getAlpha()) : null, 0.0f) && (colonView = getColonView()) != null) {
                    colonView.setAlpha(0.0f);
                }
            }
            getCurrentClockBean().setColonShow(this.shouldShowColon);
            getForeClockBean().setColonShow(this.shouldShowColon);
        }
    }

    /* JADX WARN: Code duplicated, block: B:4:0x0003 A[PHI: r6
  0x0003: PHI (r6v14 java.lang.Integer) = (r6v0 java.lang.Integer), (r6v3 java.lang.Integer) binds: [B:3:0x0001, B:9:0x0018] A[DONT_GENERATE, DONT_INLINE]] */
    public final void updateHandleDrawablesForColorMode(Integer num) {
        int iIntValue;
        if (num == null) {
            BaseTemplateView baseTemplateView = getBaseTemplateView();
            num = baseTemplateView != null ? Integer.valueOf(baseTemplateView.backgroundColorMode()) : null;
            if (num != null) {
                iIntValue = num.intValue();
            } else {
                iIntValue = 0;
            }
        } else {
            iIntValue = num.intValue();
        }
        boolean z = iIntValue == 0;
        ImageView imageView = this.cornerHandleView;
        if (imageView != null) {
            imageView.setImageResource(z ? R.drawable.all_in_one_right_corner_handle_normal : R.drawable.all_in_one_right_corner_handle_for_light_wallpaper);
        }
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        float fProvideTemplateEditScale = resourcesProvider.provideTemplateEditScale(context);
        float f = fProvideTemplateEditScale > 0.0f ? 1.0f / fProvideTemplateEditScale : 1.0f;
        ImageView imageView2 = this.cornerHandleView;
        if (imageView2 != null) {
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            imageView2.setScaleX((deviceUtil.isRtl(context2) ? -1.0f : 1.0f) * f);
        }
        ImageView imageView3 = this.cornerHandleView;
        if (imageView3 != null) {
            imageView3.setScaleY(f);
        }
    }

    public final void updateMaxSafeFrameRect() {
        int dimen = DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding_horizontal, getContext());
        int dimen2 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding, getContext());
        int dimen3 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_date_frame_padding, getContext());
        Rect rect = this.maxSafeRect;
        this.maxSafeFrameRect = new Rect(rect.left - dimen, rect.top - dimen3, rect.right + dimen, rect.bottom + dimen2);
    }

    public final void updateRatio() {
        int dimen;
        int dimen2 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding_horizontal, getContext());
        int dimen3 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_time_frame_padding, getContext());
        this.timeWidth = RangesKt.coerceAtLeast((this.currentClockFrameRight - this.currentClockFrameLeft) - (dimen2 * 2), 0);
        this.timeHeight = RangesKt.coerceAtLeast((this.currentClockFrameBottom - this.currentClockFrameTop) - (dimen3 * 2), 0);
        int dimen4 = DeviceConfig.getDimen(R.dimen.kg_all_in_one_date_frame_padding, getContext());
        int i = this.currentDateFrameTop + dimen4;
        Rect rect = this.maxSafeRect;
        float fHeight = (i - rect.top) / rect.height();
        this.clockTopRatio = RangesKt.coerceIn(fHeight, 0.0f, 1.0f);
        if (fHeight < 0.0f) {
            int i2 = this.currentDateFrameTop;
            Rect rect2 = this.maxSafeRect;
            Log.w("AllInOneTemplateView", "[updateRatio] topRatio CLAMPED: raw=" + fHeight + " -> 0.0, dateFrameTop=" + i2 + ", datePadding=" + dimen4 + ", maxSafeRect.top=" + rect2.top + ", maxSafeRect.h=" + rect2.height());
        }
        if (this.signatureHasContent) {
            dimen = this.currentSignatureFrameBottom - DeviceConfig.getDimen(R.dimen.kg_all_in_one_signature_frame_padding, getContext());
        } else {
            dimen = this.currentClockFrameBottom - dimen3;
        }
        Rect rect3 = this.maxSafeRect;
        this.clockBottomRatio = RangesKt.coerceIn((rect3.bottom - dimen) / rect3.height(), 0.0f, 1.0f);
    }

    public final void updateSignatureVisibilityForCurrentFrame() {
        boolean z = this.signatureHasContent;
        if (!z) {
            int i = this.maxSafeFrameRect.bottom - this.currentClockFrameBottom;
            int i2 = this.signatureGapAndHeight;
            boolean z2 = i >= i2;
            boolean z3 = this.signatureVisible;
            if (z3 != z2) {
                Log.d("AllInOneTemplateView", "[SigVisibility] changed: " + z3 + " -> " + z2 + ", safeRemain=" + i + ", sigGapH=" + i2 + ", hasContent=" + z);
                this.signatureVisible = z2;
            }
        }
        generateEditFrames();
    }

    @Override // com.miui.keyguard.editor.edit.base.BaseClockView
    public boolean wantsDeferredVisibilityOnSwitch() {
        return editable();
    }
}
