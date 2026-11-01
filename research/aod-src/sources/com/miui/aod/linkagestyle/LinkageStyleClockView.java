package com.miui.aod.linkagestyle;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewGroupKt;
import com.miui.aod.AODApplication;
import com.miui.aod.AODApplicationDelegate;
import com.miui.aod.ApplicationDelegate;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.AODBatteryMeterView;
import com.miui.aod.components.view.AodContainerView;
import com.miui.aod.doze.SettingsHolder;
import com.miui.aod.util.DeviceInfo;
import com.miui.aod.util.ReflectUtil;
import com.miui.aod.util.SVGUtils;
import com.miui.aod.util.ScreenResolutionUtils;
import com.miui.aod.util.WindowUtils;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.clock.MiuiClockController;
import com.miui.clock.MiuiClockView;
import com.miui.clock.allInOne.AllInOneAodLinkageUtils;
import com.miui.clock.allInOne.AllInOneBase;
import com.miui.clock.allInOne.AllInOneBaseInfo;
import com.miui.clock.allInOne.AllInOneUtil;
import com.miui.clock.eastern.c.EasternArtCBase;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.rhombus.MiuiRhombusBase;
import com.miui.clock.utils.DeviceConfig;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import miuix.appcompat.app.AppCompatActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
    */
/* JADX INFO: compiled from: LinkageStyleClockView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class LinkageStyleClockView extends ConstraintLayout {
    public final String TAG;
    public final Lazy allInOneSmallViewMoveTop$delegate;
    public final Lazy allInOneSmallViewPadding$delegate;
    public final Lazy clockFrameLayout$delegate;
    public boolean isBindView;
    public View mAllView;
    public View mBattery;
    public TextView mClockDate;
    public int mClockGravity;
    public View mClockInfoContainer;
    public boolean mClockIsChange;
    public int mClockViewStyle;
    public Context mContext;
    public String mCurrentClockTemplateId;
    public TextView mDateLunar;
    public final int mEasternClockC_Style_A;
    public final int mEasternClockC_Style_B;
    public View mFocusIcon;
    public View mIcon;
    public MiuiClockView mMiuiClockView;
    public boolean mNeedOnHourUpdateLayoutFlag;
    public float mScaleRatio;
    public int mSize;
    public View mSmartFrameBatterView;
    public StyleInfo mStyleInfo;
    public int magazineHeight;
    public final LinkageStyleClockView$miuiClockInfoListener$1 miuiClockInfoListener;
    public final MiuiClockController.TimeUpdateListener timeUpdateListener;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public LinkageStyleClockView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public LinkageStyleClockView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public LinkageStyleClockView(@NotNull final Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.clockFrameLayout$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.linkagestyle.LinkageStyleClockView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return LinkageStyleClockView.clockFrameLayout_delegate$lambda$0(this.f$0);
            }
        });
        this.TAG = "LinkageStyleClockView";
        this.mScaleRatio = 1.0f;
        this.mContext = context;
        this.mCurrentClockTemplateId = "classic";
        this.mClockGravity = Integer.MAX_VALUE;
        this.mEasternClockC_Style_A = 1;
        this.mEasternClockC_Style_B = 2;
        this.allInOneSmallViewMoveTop$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.linkagestyle.LinkageStyleClockView$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(LinkageStyleClockView.allInOneSmallViewMoveTop_delegate$lambda$1(this.f$0));
            }
        });
        this.allInOneSmallViewPadding$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.linkagestyle.LinkageStyleClockView$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(LinkageStyleClockView.allInOneSmallViewPadding_delegate$lambda$2(this.f$0));
            }
        });
        this.timeUpdateListener = new MiuiClockController.TimeUpdateListener() { // from class: com.miui.aod.linkagestyle.LinkageStyleClockView$$ExternalSyntheticLambda5
            @Override // com.miui.clock.MiuiClockController.TimeUpdateListener
            public final void update() {
                LinkageStyleClockView.timeUpdateListener$lambda$3(this.f$0, context);
            }
        };
        this.miuiClockInfoListener = new LinkageStyleClockView$miuiClockInfoListener$1(this);
    }

    public /* synthetic */ LinkageStyleClockView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public static final float allInOneSmallViewMoveTop_delegate$lambda$1(LinkageStyleClockView linkageStyleClockView) {
        return linkageStyleClockView.mContext.getResources().getDimension(R.dimen.linkage_aod_all_in_one_move_top);
    }

    public static final float allInOneSmallViewPadding_delegate$lambda$2(LinkageStyleClockView linkageStyleClockView) {
        return linkageStyleClockView.mContext.getResources().getDimension(R.dimen.linkage_aod_all_in_one_padding);
    }

    public static final FrameLayout clockFrameLayout_delegate$lambda$0(LinkageStyleClockView linkageStyleClockView) {
        return (FrameLayout) linkageStyleClockView.findViewById(R.id.clock);
    }

    private final float getAllInOneSmallViewMoveTop() {
        return ((Number) this.allInOneSmallViewMoveTop$delegate.getValue()).floatValue();
    }

    private final float getAllInOneSmallViewPadding() {
        return ((Number) this.allInOneSmallViewPadding$delegate.getValue()).floatValue();
    }

    private final FrameLayout getClockFrameLayout() {
        Object value = this.clockFrameLayout$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (FrameLayout) value;
    }

    private final void initClockView(int i) {
        ApplicationDelegate applicationDelegate;
        initContext(i);
        MiuiClockView miuiClockView = this.mMiuiClockView;
        Rect rectCalculateColorPickupRect = null;
        MiuiClockView miuiClockView2 = null;
        if (miuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        }
        if (i == 4 || i == 5 || i == 6) {
            miuiClockView.init(9, false);
        } else {
            miuiClockView.init(1, false);
        }
        this.mCurrentClockTemplateId = miuiClockView.getClockBean().getTemplateId();
        this.mClockViewStyle = miuiClockView.getClockBean().getStyle();
        if (Intrinsics.areEqual(this.mCurrentClockTemplateId, "rhombus") && !miuiClockView.isDualClock()) {
            updateSvgResource();
        }
        updateClockViewScaleRatio(i);
        miuiClockView.setMiuiClockInfoListener(this.miuiClockInfoListener);
        if (i == 2 && Utils.isFoldDevice()) {
            miuiClockView.setTimeUpdateListener(this.timeUpdateListener);
        }
        if (!Utils.isAodProcess() || (applicationDelegate = AODApplication.getApplicationDelegate()) == null) {
            return;
        }
        if (isAllInOneTemplate(this.mCurrentClockTemplateId)) {
            MiuiClockView miuiClockView3 = this.mMiuiClockView;
            if (miuiClockView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            } else {
                miuiClockView2 = miuiClockView3;
            }
            rectCalculateColorPickupRect = miuiClockView2.calculateColorPickupRect(Boolean.TRUE);
        }
        applicationDelegate.refreshAllInOneClockPalette(rectCalculateColorPickupRect);
    }

    public static final void scaleForAllInOneSmallView$lambda$13$lambda$12$lambda$11(ClockStyleInfo clockStyleInfo, LinkageStyleClockView linkageStyleClockView, View view, float f, float f2, int i, int i2) {
        AllInOneBaseInfo allInOneBaseInfo = (AllInOneBaseInfo) clockStyleInfo;
        MiuiClockView miuiClockView = linkageStyleClockView.mMiuiClockView;
        MiuiClockView miuiClockView2 = null;
        if (miuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        }
        Context context = miuiClockView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        AllInOneAodLinkageUtils.AodLinkedResult allInOneAodLinkedResult = allInOneBaseInfo.getAllInOneAodLinkedResult(context);
        AllInOneUtil allInOneUtil = AllInOneUtil.INSTANCE;
        MiuiClockView miuiClockView3 = linkageStyleClockView.mMiuiClockView;
        if (miuiClockView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView3 = null;
        }
        Context context2 = miuiClockView3.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        Object objCalcClockTransform = ClockViewTransformAlgoKt.calcClockTransform(new RectF(0.0f, 0.0f, f, f2), new RectF(0.0f, 0.0f, i, i2), allInOneAodLinkedResult.getAodClockTopPx(), new RectF(0.0f, 0.0f, Math.max(view.getWidth(), allInOneAodLinkedResult.getAodWidthPx()), allInOneAodLinkedResult.getAodHeightPx() + allInOneUtil.getDateTopToTime(context2)), linkageStyleClockView.getAllInOneSmallViewMoveTop(), linkageStyleClockView.getAllInOneSmallViewPadding());
        if (Result.m1066isSuccessimpl(objCalcClockTransform)) {
            TransformResult transformResult = (TransformResult) objCalcClockTransform;
            MiuiClockView miuiClockView4 = linkageStyleClockView.mMiuiClockView;
            if (miuiClockView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                miuiClockView4 = null;
            }
            miuiClockView4.setTranslationX(transformResult.getTranslationX());
            MiuiClockView miuiClockView5 = linkageStyleClockView.mMiuiClockView;
            if (miuiClockView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            } else {
                miuiClockView2 = miuiClockView5;
            }
            miuiClockView2.setTranslationY(transformResult.getTranslationY());
            linkageStyleClockView.scaleForClockView(transformResult.getScale());
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objCalcClockTransform);
        if (thM1063exceptionOrNullimpl != null) {
            Log.e(linkageStyleClockView.TAG, "scaleForAllInOne: transformResult error, " + thM1063exceptionOrNullimpl);
        }
    }

    public static final void timeUpdateListener$lambda$3(LinkageStyleClockView linkageStyleClockView, Context context) {
        if (Intrinsics.areEqual(linkageStyleClockView.mCurrentClockTemplateId, "eastern_c") && Utils.inLargeScreen(context)) {
            MiuiClockView miuiClockView = linkageStyleClockView.mMiuiClockView;
            MiuiClockView miuiClockView2 = null;
            if (miuiClockView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                miuiClockView = null;
            }
            ClockBean clockBean = miuiClockView.getClockBean();
            if (clockBean == null || clockBean.getStyle() != linkageStyleClockView.mEasternClockC_Style_B || linkageStyleClockView.isDualClock()) {
                return;
            }
            MiuiClockView miuiClockView3 = linkageStyleClockView.mMiuiClockView;
            if (miuiClockView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                miuiClockView3 = null;
            }
            ClockViewType clockViewType = ClockViewType.ALL_VIEW;
            if (miuiClockView3.getIClockView(clockViewType) instanceof EasternArtCBase) {
                MiuiClockView miuiClockView4 = linkageStyleClockView.mMiuiClockView;
                if (miuiClockView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                } else {
                    miuiClockView2 = miuiClockView4;
                }
                View iClockView = miuiClockView2.getIClockView(clockViewType);
                Intrinsics.checkNotNull(iClockView, "null cannot be cast to non-null type com.miui.clock.eastern.c.EasternArtCBase");
                if (((EasternArtCBase) iClockView).isOnHour()) {
                    linkageStyleClockView.mNeedOnHourUpdateLayoutFlag = true;
                    linkageStyleClockView.updateClockViewLayout(linkageStyleClockView.mSize);
                } else if (linkageStyleClockView.mNeedOnHourUpdateLayoutFlag) {
                    linkageStyleClockView.mNeedOnHourUpdateLayoutFlag = false;
                    linkageStyleClockView.updateClockViewLayout(linkageStyleClockView.mSize);
                }
            }
        }
    }

    public static final void updateClockViewLayout$lambda$6$lambda$5(LinkageStyleClockView linkageStyleClockView, View view) {
        Configuration configuration;
        MiuiClockView miuiClockView = linkageStyleClockView.mMiuiClockView;
        MiuiClockView miuiClockView2 = null;
        if (miuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        }
        View iClockView = miuiClockView.getIClockView(ClockViewType.ALL_VIEW);
        linkageStyleClockView.mAllView = iClockView;
        if (iClockView != null) {
            Intrinsics.checkNotNull(iClockView, "null cannot be cast to non-null type com.miui.clock.rhombus.MiuiRhombusBase");
            BaseFontStyle style = ((MiuiRhombusBase) iClockView).getStyle();
            MiuiClockView miuiClockView3 = linkageStyleClockView.mMiuiClockView;
            if (miuiClockView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                miuiClockView3 = null;
            }
            float clockHeight = (miuiClockView3.getClockHeight() * (1 - linkageStyleClockView.mScaleRatio)) / 2.0f;
            MiuiClockView miuiClockView4 = linkageStyleClockView.mMiuiClockView;
            if (miuiClockView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            } else {
                miuiClockView2 = miuiClockView4;
            }
            Resources resources = miuiClockView2.getResources();
            float aodDateMarginTop = (style != null ? style.getAodDateMarginTop() : 0) * (((resources == null || (configuration = resources.getConfiguration()) == null) ? 0 : configuration.densityDpi) / 160.0f);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
            ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = -(((int) aodDateMarginTop) + ((int) clockHeight));
            view.setLayoutParams(layoutParams2);
        }
    }

    public final void applyLinkageState(int i) {
        this.magazineHeight = i;
        MiuiClockView miuiClockView = this.mMiuiClockView;
        if (miuiClockView == null) {
            return;
        }
        if (miuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        }
        View iClockView = miuiClockView.getIClockView(ClockViewType.ALL_VIEW);
        AllInOneBase allInOneBase = iClockView instanceof AllInOneBase ? (AllInOneBase) iClockView : null;
        if (allInOneBase != null) {
            allInOneBase.toNotificationState(false, i);
        }
    }

    public final void bindView(int i) {
        this.mSize = i;
        initClockView(i);
        View viewFindViewById = findViewById(R.id.clock_info_container);
        this.mClockInfoContainer = viewFindViewById;
        this.mClockDate = viewFindViewById != null ? (TextView) viewFindViewById.findViewById(R.id.date) : null;
        View view = this.mClockInfoContainer;
        this.mDateLunar = view != null ? (TextView) view.findViewById(R.id.date_lunar) : null;
        this.mBattery = findViewById(R.id.battery_container);
        this.mIcon = findViewById(R.id.icons);
        this.mFocusIcon = findViewById(R.id.fn_container);
        this.isBindView = true;
    }

    public final int getClockGravity() {
        return this.mClockGravity;
    }

    public final int getCurrentClockStyle() {
        return this.mClockViewStyle;
    }

    @NotNull
    public final String getCurrentClockTemplateId() {
        return this.mCurrentClockTemplateId;
    }

    public final int getCurrentSizeCategory() {
        return this.mSize;
    }

    public final float getDimen(int i) {
        MiuiClockView miuiClockView = this.mMiuiClockView;
        MiuiClockView miuiClockView2 = null;
        if (miuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        }
        float dimensionPixelSize = miuiClockView.getResources().getDimensionPixelSize(i);
        MiuiClockView miuiClockView3 = this.mMiuiClockView;
        if (miuiClockView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
        } else {
            miuiClockView2 = miuiClockView3;
        }
        return dimensionPixelSize * DeviceConfig.calculateScale(miuiClockView2.getContext());
    }

    public final boolean getMClockIsChange() {
        return this.mClockIsChange;
    }

    @Nullable
    public final MiuiClockView getMiuiClockView() {
        MiuiClockView miuiClockView = this.mMiuiClockView;
        if (miuiClockView != null) {
            return miuiClockView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
        return null;
    }

    @Nullable
    public final View getSmartFrameBatterView() {
        return this.mSmartFrameBatterView;
    }

    @NotNull
    public final String getTAG() {
        return this.TAG;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final ViewGroup.MarginLayoutParams getViewMargin(ViewGroup.MarginLayoutParams layoutParams) {
        Intrinsics.checkNotNullParameter(layoutParams, "layoutParams");
        if (isDualClock()) {
            layoutParams.topMargin = 0;
            return layoutParams;
        }
        MiuiClockView miuiClockView = this.mMiuiClockView;
        MiuiClockView miuiClockView2 = null;
        if (miuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        }
        String templateId = miuiClockView.getClockBean().getTemplateId();
        if (templateId != null) {
            switch (templateId.hashCode()) {
                case -1326135015:
                    if (templateId.equals("doodle")) {
                        layoutParams.topMargin = DeviceConfig.getDimen(R.dimen.miui_graffiti_clock_margin_top, this.mContext);
                        return layoutParams;
                    }
                    break;
                case -567074186:
                    if (templateId.equals("magazine_a")) {
                        Rect screenRect = ScreenResolutionUtils.getInstance().getScreenRect(this.mContext);
                        MiuiClockView miuiClockView3 = this.mMiuiClockView;
                        if (miuiClockView3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                        } else {
                            miuiClockView2 = miuiClockView3;
                        }
                        layoutParams.topMargin = (int) ((screenRect.height() - getDimen(R.dimen.miui_magazine_a_clock_margin_bottom)) - miuiClockView2.getClockHeight());
                        return layoutParams;
                    }
                    break;
                case -567074185:
                    if (templateId.equals("magazine_b")) {
                        layoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.linkage_magazine_b_top_margin);
                        return layoutParams;
                    }
                    break;
                case -567074184:
                    if (templateId.equals("magazine_c")) {
                        MiuiClockView miuiClockView4 = this.mMiuiClockView;
                        if (miuiClockView4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                        } else {
                            miuiClockView2 = miuiClockView4;
                        }
                        ClockBean clockBean = miuiClockView2.getClockBean();
                        if (clockBean == null || clockBean.getStyle() != 5) {
                            layoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.linkage_magazine_c_top_margin);
                            return layoutParams;
                        }
                        layoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.linkage_magazine_c_top_margin_C_1);
                        return layoutParams;
                    }
                    break;
                case 853620882:
                    if (templateId.equals("classic")) {
                        layoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.linkage_classic_top_margin);
                        return layoutParams;
                    }
                    break;
                case 1179492204:
                    if (templateId.equals("rhombus")) {
                        layoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.linkage_rhombus_top_margin);
                        return layoutParams;
                    }
                    break;
                case 1250520234:
                    if (templateId.equals("all_in_one")) {
                        layoutParams.topMargin = 0;
                        return layoutParams;
                    }
                    break;
            }
        }
        layoutParams.topMargin = 0;
        return layoutParams;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0077  */
    public final Point getViewTopAndMargin(int i, int i2, int i3, int i4) {
        Point point;
        MiuiClockView miuiClockView = this.mMiuiClockView;
        MiuiClockView miuiClockView2 = null;
        if (miuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        }
        ClockBean clockBean = miuiClockView.getClockBean();
        String templateId = clockBean != null ? clockBean.getTemplateId() : null;
        if (Intrinsics.areEqual("rhombus", templateId)) {
            int i5 = (int) (i2 * 0.25f);
            point = new Point(Math.max(i, i + i5), i2 - i5);
        } else if (isAllInOneTemplate(templateId)) {
            MiuiClockView miuiClockView3 = this.mMiuiClockView;
            if (miuiClockView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                miuiClockView3 = null;
            }
            ClockStyleInfo clockStyleInfo = miuiClockView3.getClockStyleInfo();
            AllInOneBaseInfo allInOneBaseInfo = clockStyleInfo instanceof AllInOneBaseInfo ? (AllInOneBaseInfo) clockStyleInfo : null;
            if (allInOneBaseInfo == null) {
                point = null;
            } else {
                MiuiClockView miuiClockView4 = this.mMiuiClockView;
                if (miuiClockView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                    miuiClockView4 = null;
                }
                Context context = miuiClockView4.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                AllInOneAodLinkageUtils.AodLinkedResult allInOneAodLinkedResult = allInOneBaseInfo.getAllInOneAodLinkedResult(context);
                if (allInOneAodLinkedResult != null) {
                    point = new Point(allInOneAodLinkedResult.getAodClockTopPx() + i3, i4 - allInOneAodLinkedResult.getAodClockTopPx());
                } else {
                    point = null;
                }
            }
            if (point == null) {
                String str = this.TAG;
                MiuiClockView miuiClockView5 = this.mMiuiClockView;
                if (miuiClockView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                    miuiClockView5 = null;
                }
                Log.e(str, "getViewTopAndMargin: AllInOne template is not valid: " + miuiClockView5.getClockStyleInfo());
            }
        } else {
            point = null;
        }
        if (point != null) {
            return point;
        }
        MiuiClockView miuiClockView6 = this.mMiuiClockView;
        if (miuiClockView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView6 = null;
        }
        int topMargin = i3 + ((int) miuiClockView6.getTopMargin());
        MiuiClockView miuiClockView7 = this.mMiuiClockView;
        if (miuiClockView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
        } else {
            miuiClockView2 = miuiClockView7;
        }
        return new Point(topMargin, i4 - ((int) miuiClockView2.getTopMargin()));
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0082  */
    /* JADX WARN: Code duplicated, block: B:21:0x0088  */
    /* JADX WARN: Code duplicated, block: B:22:0x0099  */
    public final void initContext(int i) {
        Context contextCreateConfigurationContext;
        StyleInfo styleInfo = this.mStyleInfo;
        if (styleInfo != null) {
            if (styleInfo == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                styleInfo = null;
            }
            if (!styleInfo.isForAodNotification() && i == 2 && Utils.isFoldDevice() && Utils.inLargeScreen(this.mContext) && Utils.isAodProcess() && !isAllInOneTemplateFromSettings(this.mContext.getApplicationContext())) {
                Configuration configuration = this.mContext.getResources().getConfiguration();
                Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
                DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                DeviceUtil.DisplayInfo screenSizeFromDisplays = deviceUtil.getScreenSizeFromDisplays(context, DeviceUtil.ScreenType.SMALL);
                configuration.screenWidthDp = (int) (screenSizeFromDisplays.getWidth() / screenSizeFromDisplays.getDensity());
                configuration.screenHeightDp = (int) (screenSizeFromDisplays.getHeight() / screenSizeFromDisplays.getDensity());
                configuration.smallestScreenWidthDp = screenSizeFromDisplays.getSw();
                configuration.densityDpi = screenSizeFromDisplays.getDensityDpi();
                contextCreateConfigurationContext = this.mContext.createConfigurationContext(configuration);
            } else if (Utils.isAodProcess()) {
                contextCreateConfigurationContext = this.mContext.createConfigurationContext(AODApplication.aodSettingDensityContext.getResources().getConfiguration());
            } else {
                Configuration configuration2 = new Configuration();
                Configuration configuration3 = AODApplication.sysuiContext.getResources().getConfiguration();
                configuration2.densityDpi = configuration3.densityDpi;
                configuration2.fontScale = configuration3.fontScale;
                contextCreateConfigurationContext = this.mContext.createConfigurationContext(configuration2);
            }
        } else if (Utils.isAodProcess()) {
            contextCreateConfigurationContext = this.mContext.createConfigurationContext(AODApplication.aodSettingDensityContext.getResources().getConfiguration());
        } else {
            Configuration configuration4 = new Configuration();
            Configuration configuration5 = AODApplication.sysuiContext.getResources().getConfiguration();
            configuration4.densityDpi = configuration5.densityDpi;
            configuration4.fontScale = configuration5.fontScale;
            contextCreateConfigurationContext = this.mContext.createConfigurationContext(configuration4);
        }
        if (!Utils.isAodProcess()) {
            try {
                ReflectUtil.setObjectField(contextCreateConfigurationContext, "mClassLoader", this.mContext.getClassLoader());
            } catch (Exception e) {
                Log.d(this.TAG, "error:", e);
            }
        }
        LayoutInflater.from(contextCreateConfigurationContext).inflate(R.layout.aod_content_linkage_clock, (ViewGroup) getClockFrameLayout(), true);
        this.mMiuiClockView = (MiuiClockView) getClockFrameLayout().findViewById(R.id.linkage_clock);
    }

    public final boolean isAllInOneTemplate(String str) {
        return Intrinsics.areEqual(str, "all_in_one");
    }

    public final boolean isAllInOneTemplateFromSettings(Context context) {
        if (context == null) {
            Log.e(this.TAG, "isAllInOneTemplateFromSettings: appCtx is null");
            return false;
        }
        if (DeviceUtil.INSTANCE.isVersionOs4(context)) {
            return isAllInOneTemplate(AODSettings.getTemplateId(context));
        }
        return false;
    }

    public final boolean isDualClock() {
        MiuiClockView miuiClockView = this.mMiuiClockView;
        if (miuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        }
        return miuiClockView.isDualClock();
    }

    public final void moveClockView(View view) {
        if (DeviceUtil.INSTANCE.isFlip() && view != null) {
            float smallViewHeight = Utils.getSmallViewHeight(this.mContext);
            if (view.getLayoutParams() == null || view.getLayoutParams().height <= 0) {
                return;
            }
            MiuiClockView miuiClockView = this.mMiuiClockView;
            if (miuiClockView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                miuiClockView = null;
            }
            miuiClockView.setTranslationY((smallViewHeight - view.getLayoutParams().height) / 2.0f);
        }
    }

    public final boolean notSupportLunarTime() {
        return Intrinsics.areEqual(this.mCurrentClockTemplateId, "classic") || Intrinsics.areEqual(this.mCurrentClockTemplateId, "eastern_a") || Intrinsics.areEqual(this.mCurrentClockTemplateId, "eastern_b") || isAllInOneTemplate(this.mCurrentClockTemplateId);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onBatteryLevelChanged(@NotNull AODBatteryMeterView.BatteryEventType event) {
        Intrinsics.checkNotNullParameter(event, "event");
        updateClockColor(-1);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = this.mSize;
        if (i == 6 || i == 2) {
            scaleForSmallView();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    public final void scaleForAllInOneSmallView(final ClockStyleInfo clockStyleInfo, final float f, final float f2, final int i, final int i2) {
        MiuiClockView miuiClockView;
        if ((clockStyleInfo instanceof AllInOneBaseInfo ? (AllInOneBaseInfo) clockStyleInfo : null) == null) {
            Log.e(this.TAG, "scaleForAllInOne: info is not valid " + clockStyleInfo);
            return;
        }
        MiuiClockView miuiClockView2 = this.mMiuiClockView;
        if (miuiClockView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView2 = null;
        }
        final View iClockView = miuiClockView2.getIClockView(ClockViewType.TEXT_AREA);
        if (iClockView == null) {
            Log.e(this.TAG, "scaleForAllInOne: dateView is null");
            return;
        }
        MiuiClockView miuiClockView3 = this.mMiuiClockView;
        if (miuiClockView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        } else {
            miuiClockView = miuiClockView3;
        }
        miuiClockView.post(new Runnable() { // from class: com.miui.aod.linkagestyle.LinkageStyleClockView$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                LinkageStyleClockView.scaleForAllInOneSmallView$lambda$13$lambda$12$lambda$11(clockStyleInfo, this, iClockView, f, f2, i, i2);
            }
        });
    }

    public final void scaleForClockView(float f) {
        MiuiClockView miuiClockView = this.mMiuiClockView;
        if (miuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        }
        miuiClockView.setScaleX(f);
        miuiClockView.setScaleY(f);
    }

    /* JADX WARN: Code duplicated, block: B:124:0x0249  */
    /* JADX WARN: Code duplicated, block: B:126:0x024d  */
    /* JADX WARN: Code duplicated, block: B:130:0x025d  */
    /* JADX WARN: Code duplicated, block: B:133:0x0263  */
    /* JADX WARN: Code duplicated, block: B:136:0x0276  */
    /* JADX WARN: Code duplicated, block: B:147:0x0299  */
    /* JADX WARN: Code duplicated, block: B:149:0x029d  */
    /* JADX WARN: Code duplicated, block: B:153:0x02ad  */
    /* JADX WARN: Code duplicated, block: B:156:0x02b3  */
    /* JADX WARN: Code duplicated, block: B:159:0x02c8  */
    /* JADX WARN: Code duplicated, block: B:291:0x0500  */
    /* JADX WARN: Code duplicated, block: B:293:0x0504  */
    /* JADX WARN: Code duplicated, block: B:297:0x0514  */
    /* JADX WARN: Code duplicated, block: B:300:0x051a  */
    /* JADX WARN: Code duplicated, block: B:303:0x052f  */
    /* JADX WARN: Code duplicated, block: B:32:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:45:0x00fa  */
    /* JADX WARN: Code duplicated, block: B:475:0x07de  */
    /* JADX WARN: Code duplicated, block: B:477:0x07e2  */
    /* JADX WARN: Code duplicated, block: B:47:0x0106  */
    /* JADX WARN: Code duplicated, block: B:480:0x07f9  */
    /* JADX WARN: Code duplicated, block: B:481:0x0807  */
    /* JADX WARN: Code duplicated, block: B:484:0x080c  */
    /* JADX WARN: Code duplicated, block: B:48:0x0114  */
    /* JADX WARN: Code duplicated, block: B:508:0x0877  */
    /* JADX WARN: Code duplicated, block: B:510:0x087d  */
    /* JADX WARN: Code duplicated, block: B:512:0x0885  */
    /* JADX WARN: Code duplicated, block: B:514:0x088f  */
    /* JADX WARN: Code duplicated, block: B:516:0x089e  */
    /* JADX WARN: Code duplicated, block: B:518:0x08a7  */
    /* JADX WARN: Code duplicated, block: B:520:0x08b6  */
    /* JADX WARN: Code duplicated, block: B:522:0x08bf  */
    public final void scaleForSmallView() {
        int i;
        float fCoerceAtLeast;
        float f;
        float f2;
        MiuiClockView miuiClockView;
        ViewParent parent;
        MiuiClockView miuiClockView2;
        AodContainerView aodContainerView;
        MiuiClockView miuiClockView3;
        MiuiClockView miuiClockView4;
        MiuiClockView miuiClockView5;
        MiuiClockView miuiClockView6;
        StyleInfo styleInfo;
        float f3;
        float f4;
        StyleInfo styleInfo2;
        AodContainerView aodContainerView2;
        StyleInfo styleInfo3;
        AodContainerView aodContainerView3;
        float f5;
        float f6;
        MiuiClockView miuiClockView7;
        float f7;
        MiuiClockView miuiClockView8;
        MiuiClockView miuiClockView9;
        StyleInfo styleInfo4;
        StyleInfo styleInfo5;
        AodContainerView aodContainerView4;
        StyleInfo styleInfo6;
        StyleInfo styleInfo7;
        AodContainerView aodContainerView5;
        MiuiClockView miuiClockView10;
        MiuiClockView miuiClockView11;
        MiuiClockView miuiClockView12;
        AodContainerView aodContainerView6;
        MiuiClockView miuiClockView13;
        MiuiClockView miuiClockView14;
        MiuiClockView miuiClockView15;
        int i2;
        int i3;
        if (isDualClock()) {
            scaleForSmallView(0.4f);
            return;
        }
        WindowUtils windowUtils = WindowUtils.INSTANCE;
        Point windowSize = windowUtils.getWindowSize(this.mContext);
        int i4 = windowSize.x;
        int i5 = windowSize.y;
        if (Utils.isFoldDevice() && !isAllInOneTemplate(this.mCurrentClockTemplateId)) {
            i5 = 2400;
            i4 = 1080;
        }
        if (Utils.isFlipDevice()) {
            i4 = 1224;
            i5 = 2912;
        }
        if (this.mContext instanceof AppCompatActivity) {
            Context context = getContext();
            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type miuix.appcompat.app.AppCompatActivity");
            if (((AppCompatActivity) context).isInMultiWindowMode()) {
                i5 = DeviceInfo.getDefaultDisplayMetrics().heightPixels;
            }
        }
        float styleItemWidth = Utils.getStyleItemWidth(this.mContext);
        float smallViewHeight = Utils.getSmallViewHeight(this.mContext);
        float f8 = i4;
        float fCoerceAtMost = RangesKt.coerceAtMost(styleItemWidth / f8, smallViewHeight / i5);
        float f9 = (!Utils.isLayoutRtl() || this.mSize == 6) ? -(f8 - styleItemWidth) : f8 - styleItemWidth;
        StyleInfo styleInfo8 = this.mStyleInfo;
        if (styleInfo8 == null) {
            i = i5;
            fCoerceAtLeast = fCoerceAtMost;
            f = f9;
            f2 = 0.0f;
        } else {
            if (styleInfo8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                styleInfo8 = null;
            }
            if (styleInfo8.isForAodNotification()) {
                Context context2 = getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                int widthPixel = windowUtils.getWidthPixel(context2);
                Context context3 = getContext();
                Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                int i6 = windowUtils.getWindowSize(context3).y;
                float f10 = widthPixel;
                float notificationOffScreenWidth = Utils.getNotificationOffScreenWidth(this) / f10;
                float f11 = 1 - notificationOffScreenWidth;
                float f12 = 2;
                float f13 = (f10 * f11) / f12;
                float f14 = (i6 * f11) / f12;
                i4 = widthPixel;
                fCoerceAtLeast = notificationOffScreenWidth;
                f = f13;
                f2 = f14;
                i = i6;
            } else {
                i = i5;
                fCoerceAtLeast = fCoerceAtMost;
                f = f9;
                f2 = 0.0f;
            }
        }
        String str = this.mCurrentClockTemplateId;
        switch (str.hashCode()) {
            case -1326135015:
                int i7 = i4;
                if (str.equals("doodle")) {
                    StyleInfo styleInfo9 = this.mStyleInfo;
                    if (styleInfo9 != null) {
                        if (styleInfo9 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                            styleInfo9 = null;
                        }
                        if (!styleInfo9.isForAodNotification()) {
                            miuiClockView2 = this.mMiuiClockView;
                            if (miuiClockView2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView2 = null;
                            }
                            miuiClockView2.setTranslationX(f / 2.0f);
                            float f15 = styleItemWidth / i7;
                            if (getParent().getParent() instanceof AodContainerView) {
                                ViewParent parent2 = getParent().getParent();
                                Intrinsics.checkNotNull(parent2, "null cannot be cast to non-null type com.miui.aod.components.view.AodContainerView");
                                aodContainerView = (AodContainerView) parent2;
                            } else {
                                aodContainerView = null;
                            }
                            miuiClockView3 = this.mMiuiClockView;
                            if (miuiClockView3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView3 = null;
                            }
                            View iClockView = miuiClockView3.getIClockView(ClockViewType.CLOCK_CONTAINER);
                            if (aodContainerView != null) {
                                if (!Utils.isFoldDevice()) {
                                    scaleForClockView(fCoerceAtLeast * 2.0f);
                                    miuiClockView4 = this.mMiuiClockView;
                                    if (miuiClockView4 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                        miuiClockView4 = null;
                                    }
                                    miuiClockView4.setTranslationY((-(i - smallViewHeight)) / 11.0f);
                                } else if (!Utils.inLargeScreen(this.mContext)) {
                                    scaleForClockView(fCoerceAtLeast * 2.0f);
                                    miuiClockView5 = this.mMiuiClockView;
                                    if (miuiClockView5 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                        miuiClockView5 = null;
                                    }
                                    miuiClockView5.setTranslationY((-(i - smallViewHeight)) / 3.3f);
                                } else {
                                    scaleForClockView(2.5f * fCoerceAtLeast);
                                    miuiClockView6 = this.mMiuiClockView;
                                    if (miuiClockView6 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                        miuiClockView6 = null;
                                    }
                                    miuiClockView6.setTranslationY((-(i - smallViewHeight)) / 4.3f);
                                }
                            } else if (!Utils.isFoldDevice()) {
                                scaleForClockView(fCoerceAtLeast * 2.0f);
                                miuiClockView4 = this.mMiuiClockView;
                                if (miuiClockView4 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView4 = null;
                                }
                                miuiClockView4.setTranslationY((-(i - smallViewHeight)) / 11.0f);
                            } else if (!Utils.inLargeScreen(this.mContext)) {
                                scaleForClockView(fCoerceAtLeast * 2.0f);
                                miuiClockView5 = this.mMiuiClockView;
                                if (miuiClockView5 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView5 = null;
                                }
                                miuiClockView5.setTranslationY((-(i - smallViewHeight)) / 3.3f);
                            } else {
                                scaleForClockView(2.5f * fCoerceAtLeast);
                                miuiClockView6 = this.mMiuiClockView;
                                if (miuiClockView6 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView6 = null;
                                }
                                miuiClockView6.setTranslationY((-(i - smallViewHeight)) / 4.3f);
                            }
                        }
                    } else {
                        miuiClockView2 = this.mMiuiClockView;
                        if (miuiClockView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView2 = null;
                        }
                        miuiClockView2.setTranslationX(f / 2.0f);
                        float f16 = styleItemWidth / i7;
                        if (getParent().getParent() instanceof AodContainerView) {
                            ViewParent parent3 = getParent().getParent();
                            Intrinsics.checkNotNull(parent3, "null cannot be cast to non-null type com.miui.aod.components.view.AodContainerView");
                            aodContainerView = (AodContainerView) parent3;
                        } else {
                            aodContainerView = null;
                        }
                        miuiClockView3 = this.mMiuiClockView;
                        if (miuiClockView3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView3 = null;
                        }
                        View iClockView2 = miuiClockView3.getIClockView(ClockViewType.CLOCK_CONTAINER);
                        if (aodContainerView != null && iClockView2 != null) {
                            int[] iArr = new int[2];
                            aodContainerView.getLocationOnScreen(new int[2]);
                            MiuiClockView miuiClockView16 = this.mMiuiClockView;
                            if (miuiClockView16 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView16 = null;
                            }
                            miuiClockView16.getLocationOnScreen(iArr);
                            MiuiClockView miuiClockView17 = this.mMiuiClockView;
                            if (miuiClockView17 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView17 = null;
                            }
                            int height = aodContainerView.getHeight();
                            MiuiClockView miuiClockView18 = this.mMiuiClockView;
                            if (miuiClockView18 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView18 = null;
                            }
                            float height2 = (height - miuiClockView18.getHeight()) / 2;
                            MiuiClockView miuiClockView19 = this.mMiuiClockView;
                            if (miuiClockView19 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView19 = null;
                            }
                            int height3 = (miuiClockView19.getHeight() / 2) - (iClockView2.getHeight() / 2);
                            ViewGroup.LayoutParams layoutParams = iClockView2.getLayoutParams();
                            ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
                            miuiClockView17.setTranslationY(height2 + ((height3 - (marginLayoutParams != null ? marginLayoutParams.topMargin : 0)) * f16));
                            scaleForClockView(f16);
                        } else if (!Utils.isFoldDevice()) {
                            scaleForClockView(fCoerceAtLeast * 2.0f);
                            miuiClockView4 = this.mMiuiClockView;
                            if (miuiClockView4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView4 = null;
                            }
                            miuiClockView4.setTranslationY((-(i - smallViewHeight)) / 11.0f);
                        } else if (!Utils.inLargeScreen(this.mContext)) {
                            scaleForClockView(fCoerceAtLeast * 2.0f);
                            miuiClockView5 = this.mMiuiClockView;
                            if (miuiClockView5 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView5 = null;
                            }
                            miuiClockView5.setTranslationY((-(i - smallViewHeight)) / 3.3f);
                        } else {
                            scaleForClockView(2.5f * fCoerceAtLeast);
                            miuiClockView6 = this.mMiuiClockView;
                            if (miuiClockView6 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView6 = null;
                            }
                            miuiClockView6.setTranslationY((-(i - smallViewHeight)) / 4.3f);
                        }
                    }
                }
                break;
            case -567074186:
                if (str.equals("magazine_a") && (styleInfo = this.mStyleInfo) != null) {
                    if (styleInfo == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                        styleInfo = null;
                    }
                    if (!styleInfo.isForAodNotification()) {
                        MiuiClockView miuiClockView20 = this.mMiuiClockView;
                        if (miuiClockView20 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView20 = null;
                        }
                        ClockBean clockBean = miuiClockView20.getClockBean();
                        if (clockBean == null || clockBean.getStyle() != 1) {
                            MiuiClockView miuiClockView21 = this.mMiuiClockView;
                            if (miuiClockView21 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView21 = null;
                            }
                            ClockBean clockBean2 = miuiClockView21.getClockBean();
                            if (clockBean2 == null || clockBean2.getStyle() != 3) {
                                MiuiClockView miuiClockView22 = this.mMiuiClockView;
                                if (miuiClockView22 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView22 = null;
                                }
                                miuiClockView22.setTranslationX(f / 2.0f);
                            } else {
                                MiuiClockView miuiClockView23 = this.mMiuiClockView;
                                if (miuiClockView23 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView23 = null;
                                }
                                miuiClockView23.setTranslationX(f / 1.5f);
                            }
                        } else {
                            MiuiClockView miuiClockView24 = this.mMiuiClockView;
                            if (miuiClockView24 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView24 = null;
                            }
                            miuiClockView24.setTranslationX(Utils.inLargeScreen(this.mContext) ? f / 2.8f : f / 3.0f);
                        }
                        MiuiClockView miuiClockView25 = this.mMiuiClockView;
                        if (miuiClockView25 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView25 = null;
                        }
                        if (Utils.isFoldDevice()) {
                            f3 = -(i - smallViewHeight);
                            f4 = 5.0f;
                        } else {
                            f3 = -(i - smallViewHeight);
                            f4 = 17.0f;
                        }
                        miuiClockView25.setTranslationY(f3 / f4);
                        scaleForClockView(fCoerceAtLeast * 3.0f);
                    }
                }
                break;
            case -567074185:
                int i8 = i4;
                if (str.equals("magazine_b") && (styleInfo2 = this.mStyleInfo) != null) {
                    if (styleInfo2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                        styleInfo2 = null;
                    }
                    if (!styleInfo2.isForAodNotification()) {
                        MiuiClockView miuiClockView26 = this.mMiuiClockView;
                        if (miuiClockView26 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView26 = null;
                        }
                        miuiClockView26.setTranslationX(f / 2.0f);
                        if (!Utils.isFoldDevice()) {
                            MiuiClockView miuiClockView27 = this.mMiuiClockView;
                            if (miuiClockView27 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView27 = null;
                            }
                            miuiClockView27.setTranslationY(!Utils.isFoldDevice() ? (-(i - smallViewHeight)) / 8.0f : (-(i - smallViewHeight)) / 2.8f);
                            scaleForClockView(1.8f * fCoerceAtLeast);
                        } else {
                            if (getParent().getParent() instanceof AodContainerView) {
                                ViewParent parent4 = getParent().getParent();
                                Intrinsics.checkNotNull(parent4, "null cannot be cast to non-null type com.miui.aod.components.view.AodContainerView");
                                aodContainerView2 = (AodContainerView) parent4;
                            } else {
                                aodContainerView2 = null;
                            }
                            MiuiClockView miuiClockView28 = this.mMiuiClockView;
                            if (miuiClockView28 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView28 = null;
                            }
                            Object parent5 = miuiClockView28.getIClockView(ClockViewType.FULL_TIME).getParent();
                            Intrinsics.checkNotNull(parent5, "null cannot be cast to non-null type android.view.View");
                            View view = (View) parent5;
                            if (aodContainerView2 != null) {
                                fCoerceAtLeast = RangesKt.coerceAtLeast(styleItemWidth / i8, smallViewHeight / i);
                                MiuiClockView miuiClockView29 = this.mMiuiClockView;
                                if (miuiClockView29 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView29 = null;
                                }
                                float height4 = (aodContainerView2.getHeight() - i) / 2;
                                ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
                                ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : null;
                                miuiClockView29.setTranslationY((height4 - ((marginLayoutParams2 != null ? marginLayoutParams2.topMargin : 0) * fCoerceAtLeast)) + (((i - view.getHeight()) * fCoerceAtLeast) / 2));
                                scaleForClockView(fCoerceAtLeast);
                            }
                        }
                    }
                }
                break;
            case -567074184:
                int i9 = i4;
                if (str.equals("magazine_c") && (styleInfo3 = this.mStyleInfo) != null) {
                    if (styleInfo3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                        styleInfo3 = null;
                    }
                    if (!styleInfo3.isForAodNotification()) {
                        float f17 = styleItemWidth / i9;
                        if (getParent().getParent() instanceof AodContainerView) {
                            ViewParent parent6 = getParent().getParent();
                            Intrinsics.checkNotNull(parent6, "null cannot be cast to non-null type com.miui.aod.components.view.AodContainerView");
                            aodContainerView3 = (AodContainerView) parent6;
                        } else {
                            aodContainerView3 = null;
                        }
                        MiuiClockView miuiClockView30 = this.mMiuiClockView;
                        if (miuiClockView30 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView30 = null;
                        }
                        View iClockView3 = miuiClockView30.getIClockView(ClockViewType.CLOCK_CONTAINER);
                        if (aodContainerView3 == null || iClockView3 == null) {
                            MiuiClockView miuiClockView31 = this.mMiuiClockView;
                            if (miuiClockView31 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView31 = null;
                            }
                            ClockBean clockBean3 = miuiClockView31.getClockBean();
                            if (clockBean3 == null || clockBean3.getStyle() != 5) {
                                MiuiClockView miuiClockView32 = this.mMiuiClockView;
                                if (miuiClockView32 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView32 = null;
                                }
                                miuiClockView32.setTranslationX(f / 2.0f);
                            } else if (Utils.isFoldDevice()) {
                                MiuiClockView miuiClockView33 = this.mMiuiClockView;
                                if (miuiClockView33 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView33 = null;
                                }
                                miuiClockView33.setTranslationX(f / 3.0f);
                            } else {
                                MiuiClockView miuiClockView34 = this.mMiuiClockView;
                                if (miuiClockView34 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView34 = null;
                                }
                                miuiClockView34.setTranslationX(f / 3.2f);
                            }
                            MiuiClockView miuiClockView35 = this.mMiuiClockView;
                            if (miuiClockView35 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView35 = null;
                            }
                            if (Utils.isFoldDevice()) {
                                f5 = -(i - smallViewHeight);
                                f6 = 4.0f;
                            } else {
                                f5 = -(i - smallViewHeight);
                                f6 = 9.0f;
                            }
                            miuiClockView35.setTranslationY(f5 / f6);
                            scaleForClockView(fCoerceAtLeast * 3.0f);
                        } else {
                            int[] iArr2 = new int[2];
                            aodContainerView3.getLocationOnScreen(new int[2]);
                            MiuiClockView miuiClockView36 = this.mMiuiClockView;
                            if (miuiClockView36 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView36 = null;
                            }
                            miuiClockView36.getLocationOnScreen(iArr2);
                            MiuiClockView miuiClockView37 = this.mMiuiClockView;
                            if (miuiClockView37 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView37 = null;
                            }
                            miuiClockView37.setTranslationX(f / 2.0f);
                            MiuiClockView miuiClockView38 = this.mMiuiClockView;
                            if (miuiClockView38 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView38 = null;
                            }
                            int height5 = aodContainerView3.getHeight();
                            MiuiClockView miuiClockView39 = this.mMiuiClockView;
                            if (miuiClockView39 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView39 = null;
                            }
                            float height6 = (height5 - miuiClockView39.getHeight()) / 2;
                            MiuiClockView miuiClockView40 = this.mMiuiClockView;
                            if (miuiClockView40 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView40 = null;
                            }
                            int height7 = (miuiClockView40.getHeight() / 2) - (iClockView3.getHeight() / 2);
                            ViewGroup.LayoutParams layoutParams3 = iClockView3.getLayoutParams();
                            ViewGroup.MarginLayoutParams marginLayoutParams3 = layoutParams3 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams3 : null;
                            miuiClockView38.setTranslationY(height6 + ((height7 - (marginLayoutParams3 != null ? marginLayoutParams3.topMargin : 0)) * f17));
                            scaleForClockView(f17);
                        }
                    }
                }
                break;
            case 225289008:
                if (str.equals("depth_pets")) {
                    StyleInfo styleInfo10 = this.mStyleInfo;
                    if (styleInfo10 != null) {
                        if (styleInfo10 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                            styleInfo10 = null;
                        }
                        if (!styleInfo10.isForAodNotification()) {
                            miuiClockView7 = this.mMiuiClockView;
                            if (miuiClockView7 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView7 = null;
                            }
                            miuiClockView7.setTranslationX(f / 2.0f);
                            if (Utils.isFoldDevice()) {
                            }
                            miuiClockView8 = this.mMiuiClockView;
                            if (miuiClockView8 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView8 = null;
                            }
                            miuiClockView8.setTranslationY((-(i - smallViewHeight)) / f7);
                            scaleForClockView(2 * fCoerceAtLeast);
                            miuiClockView9 = this.mMiuiClockView;
                            if (miuiClockView9 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView9 = null;
                            }
                            moveClockView(miuiClockView9);
                        }
                    } else {
                        miuiClockView7 = this.mMiuiClockView;
                        if (miuiClockView7 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView7 = null;
                        }
                        miuiClockView7.setTranslationX(f / 2.0f);
                        f7 = Utils.isFoldDevice() ? 2.15f : 2.8f;
                        miuiClockView8 = this.mMiuiClockView;
                        if (miuiClockView8 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView8 = null;
                        }
                        miuiClockView8.setTranslationY((-(i - smallViewHeight)) / f7);
                        scaleForClockView(2 * fCoerceAtLeast);
                        miuiClockView9 = this.mMiuiClockView;
                        if (miuiClockView9 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView9 = null;
                        }
                        moveClockView(miuiClockView9);
                    }
                }
                break;
            case 853620882:
                if (str.equals("classic") && (styleInfo4 = this.mStyleInfo) != null) {
                    if (styleInfo4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                        styleInfo4 = null;
                    }
                    if (!styleInfo4.isForAodNotification()) {
                        MiuiClockView miuiClockView41 = this.mMiuiClockView;
                        if (miuiClockView41 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView41 = null;
                        }
                        miuiClockView41.setTranslationX(Utils.inLargeScreen(this.mContext) ? f / 2.0f : f / 2.1f);
                        MiuiClockView miuiClockView42 = this.mMiuiClockView;
                        if (miuiClockView42 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView42 = null;
                        }
                        miuiClockView42.setTranslationY(!Utils.isFoldDevice() ? (-(i - smallViewHeight)) / 6.5f : (-(i - smallViewHeight)) / 2.8f);
                        scaleForClockView(fCoerceAtLeast * 2.0f);
                    }
                }
                break;
            case 1179492204:
                if (str.equals("rhombus") && (styleInfo5 = this.mStyleInfo) != null) {
                    if (styleInfo5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                        styleInfo5 = null;
                    }
                    if (!styleInfo5.isForAodNotification()) {
                        setClockViewVisible(false);
                        MiuiClockView miuiClockView43 = this.mMiuiClockView;
                        if (miuiClockView43 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView43 = null;
                        }
                        this.mAllView = miuiClockView43.getIClockView(ClockViewType.ALL_VIEW);
                        if (getParent().getParent() instanceof AodContainerView) {
                            ViewParent parent7 = getParent().getParent();
                            Intrinsics.checkNotNull(parent7, "null cannot be cast to non-null type com.miui.aod.components.view.AodContainerView");
                            aodContainerView4 = (AodContainerView) parent7;
                        } else {
                            aodContainerView4 = null;
                        }
                        MiuiClockView miuiClockView44 = this.mMiuiClockView;
                        if (miuiClockView44 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView44 = null;
                        }
                        miuiClockView44.setTranslationX(f / 2.0f);
                        if (aodContainerView4 != null && aodContainerView4.getHeight() != 0 && this.mAllView != null) {
                            int height8 = aodContainerView4.getHeight();
                            float f18 = height8;
                            fCoerceAtLeast = f18 / i;
                            View view2 = this.mAllView;
                            Intrinsics.checkNotNull(view2);
                            if (height8 < view2.getHeight()) {
                                MiuiClockView miuiClockView45 = this.mMiuiClockView;
                                if (miuiClockView45 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView45 = null;
                                }
                                float f19 = 2;
                                View view3 = this.mAllView;
                                Intrinsics.checkNotNull(view3);
                                miuiClockView45.setTranslationY((f18 / f19) - ((view3.getHeight() * fCoerceAtLeast) / f19));
                                scaleForSmallView(fCoerceAtLeast);
                            }
                        }
                        MiuiClockView miuiClockView46 = this.mMiuiClockView;
                        if (miuiClockView46 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView46 = null;
                        }
                        miuiClockView46.post(new Runnable() { // from class: com.miui.aod.linkagestyle.LinkageStyleClockView$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.setClockViewVisible(true);
                            }
                        });
                    }
                }
                break;
            case 1250520234:
                if (str.equals("all_in_one") && (styleInfo6 = this.mStyleInfo) != null) {
                    if (styleInfo6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                        styleInfo6 = null;
                    }
                    if (!styleInfo6.isForAodNotification()) {
                        MiuiClockView miuiClockView47 = this.mMiuiClockView;
                        if (miuiClockView47 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView47 = null;
                        }
                        scaleForAllInOneSmallView(miuiClockView47.getClockStyleInfo(), styleItemWidth, smallViewHeight, i4, i);
                    }
                }
                break;
            case 1786073894:
                if (str.equals("eastern_a") && (styleInfo7 = this.mStyleInfo) != null) {
                    if (styleInfo7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                        styleInfo7 = null;
                    }
                    if (!styleInfo7.isForAodNotification()) {
                        MiuiClockView miuiClockView48 = this.mMiuiClockView;
                        if (miuiClockView48 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView48 = null;
                        }
                        miuiClockView48.setTranslationX(f / 2.0f);
                        fCoerceAtLeast = RangesKt.coerceAtLeast(styleItemWidth / i4, smallViewHeight / i);
                        MiuiClockView miuiClockView49 = this.mMiuiClockView;
                        if (miuiClockView49 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView49 = null;
                        }
                        View iClockView4 = miuiClockView49.getIClockView(ClockViewType.ALL_VIEW);
                        if (iClockView4 instanceof ViewGroup) {
                            ViewGroup viewGroup = (ViewGroup) iClockView4;
                            if (viewGroup.getChildCount() > 0) {
                                iClockView4 = ViewGroupKt.get(viewGroup, 0);
                            }
                        }
                        if (getParent().getParent() instanceof AodContainerView) {
                            ViewParent parent8 = getParent().getParent();
                            Intrinsics.checkNotNull(parent8, "null cannot be cast to non-null type com.miui.aod.components.view.AodContainerView");
                            aodContainerView5 = (AodContainerView) parent8;
                        } else {
                            aodContainerView5 = null;
                        }
                        if (aodContainerView5 != null && iClockView4 != null) {
                            if (Utils.isFoldDevice() && Utils.inLargeScreen(this.mContext)) {
                                MiuiClockView miuiClockView50 = this.mMiuiClockView;
                                if (miuiClockView50 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView50 = null;
                                }
                                float height9 = (aodContainerView5.getHeight() - i) / 2;
                                ViewGroup.LayoutParams layoutParams4 = iClockView4.getLayoutParams();
                                ViewGroup.MarginLayoutParams marginLayoutParams4 = layoutParams4 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams4 : null;
                                miuiClockView50.setTranslationY((height9 - ((marginLayoutParams4 != null ? marginLayoutParams4.topMargin : 0) * fCoerceAtLeast)) + (((i - iClockView4.getHeight()) * fCoerceAtLeast) / 2));
                            } else {
                                MiuiClockView miuiClockView51 = this.mMiuiClockView;
                                if (miuiClockView51 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView51 = null;
                                }
                                miuiClockView51.setTranslationY(0.0f);
                                int[] iArr3 = new int[2];
                                int[] iArr4 = new int[2];
                                aodContainerView5.getLocationOnScreen(iArr3);
                                iClockView4.getLocationOnScreen(iArr4);
                                MiuiClockView miuiClockView52 = this.mMiuiClockView;
                                if (miuiClockView52 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView52 = null;
                                }
                                miuiClockView52.setTranslationY((((aodContainerView5.getHeight() - (iClockView4.getHeight() * fCoerceAtLeast)) / 2) + iArr3[1]) - iArr4[1]);
                            }
                        }
                        scaleForClockView(fCoerceAtLeast);
                    }
                }
                break;
            case 1786073895:
                if (str.equals("eastern_b")) {
                    StyleInfo styleInfo11 = this.mStyleInfo;
                    if (styleInfo11 != null) {
                        if (styleInfo11 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                            styleInfo11 = null;
                        }
                        if (!styleInfo11.isForAodNotification()) {
                            miuiClockView10 = this.mMiuiClockView;
                            if (miuiClockView10 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView10 = null;
                            }
                            miuiClockView10.setTranslationX(f / 2.0f);
                            if (Utils.isFoldDevice()) {
                            }
                            miuiClockView11 = this.mMiuiClockView;
                            if (miuiClockView11 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView11 = null;
                            }
                            miuiClockView11.setTranslationY((-(i - smallViewHeight)) / f7);
                            scaleForClockView(fCoerceAtLeast * 1.5f);
                            miuiClockView12 = this.mMiuiClockView;
                            if (miuiClockView12 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView12 = null;
                            }
                            moveClockView(miuiClockView12);
                        }
                    } else {
                        miuiClockView10 = this.mMiuiClockView;
                        if (miuiClockView10 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView10 = null;
                        }
                        miuiClockView10.setTranslationX(f / 2.0f);
                        f7 = Utils.isFoldDevice() ? 2.15f : 2.8f;
                        miuiClockView11 = this.mMiuiClockView;
                        if (miuiClockView11 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView11 = null;
                        }
                        miuiClockView11.setTranslationY((-(i - smallViewHeight)) / f7);
                        scaleForClockView(fCoerceAtLeast * 1.5f);
                        miuiClockView12 = this.mMiuiClockView;
                        if (miuiClockView12 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            miuiClockView12 = null;
                        }
                        moveClockView(miuiClockView12);
                    }
                }
                break;
            case 1786073896:
                if (str.equals("eastern_c")) {
                    StyleInfo styleInfo12 = this.mStyleInfo;
                    if (styleInfo12 != null) {
                        if (styleInfo12 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                            styleInfo12 = null;
                        }
                        if (!styleInfo12.isForAodNotification()) {
                            if (getParent().getParent() instanceof AodContainerView) {
                                ViewParent parent9 = getParent().getParent();
                                Intrinsics.checkNotNull(parent9, "null cannot be cast to non-null type com.miui.aod.components.view.AodContainerView");
                                aodContainerView6 = (AodContainerView) parent9;
                            } else {
                                aodContainerView6 = null;
                            }
                            if (aodContainerView6 == null) {
                                miuiClockView13 = this.mMiuiClockView;
                                if (miuiClockView13 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView13 = null;
                                }
                                miuiClockView13.setTranslationX(f / 2.0f);
                                if (Utils.isFoldDevice()) {
                                }
                                miuiClockView14 = this.mMiuiClockView;
                                if (miuiClockView14 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView14 = null;
                                }
                                miuiClockView14.setTranslationY((-(i - smallViewHeight)) / f7);
                                scaleForClockView(fCoerceAtLeast);
                                miuiClockView15 = this.mMiuiClockView;
                                if (miuiClockView15 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView15 = null;
                                }
                                moveClockView(miuiClockView15);
                            } else {
                                miuiClockView13 = this.mMiuiClockView;
                                if (miuiClockView13 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView13 = null;
                                }
                                miuiClockView13.setTranslationX(f / 2.0f);
                                if (Utils.isFoldDevice()) {
                                }
                                miuiClockView14 = this.mMiuiClockView;
                                if (miuiClockView14 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView14 = null;
                                }
                                miuiClockView14.setTranslationY((-(i - smallViewHeight)) / f7);
                                scaleForClockView(fCoerceAtLeast);
                                miuiClockView15 = this.mMiuiClockView;
                                if (miuiClockView15 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView15 = null;
                                }
                                moveClockView(miuiClockView15);
                            }
                        }
                    } else {
                        if (getParent().getParent() instanceof AodContainerView) {
                            ViewParent parent10 = getParent().getParent();
                            Intrinsics.checkNotNull(parent10, "null cannot be cast to non-null type com.miui.aod.components.view.AodContainerView");
                            aodContainerView6 = (AodContainerView) parent10;
                        } else {
                            aodContainerView6 = null;
                        }
                        if (aodContainerView6 == null || aodContainerView6.getWidth() == 0) {
                            miuiClockView13 = this.mMiuiClockView;
                            if (miuiClockView13 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView13 = null;
                            }
                            miuiClockView13.setTranslationX(f / 2.0f);
                            f7 = Utils.isFoldDevice() ? 2.15f : 2.8f;
                            miuiClockView14 = this.mMiuiClockView;
                            if (miuiClockView14 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView14 = null;
                            }
                            miuiClockView14.setTranslationY((-(i - smallViewHeight)) / f7);
                            scaleForClockView(fCoerceAtLeast);
                            miuiClockView15 = this.mMiuiClockView;
                            if (miuiClockView15 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView15 = null;
                            }
                            moveClockView(miuiClockView15);
                        } else {
                            MiuiClockView miuiClockView53 = this.mMiuiClockView;
                            if (miuiClockView53 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView53 = null;
                            }
                            ClockViewType clockViewType = ClockViewType.ALL_VIEW;
                            this.mAllView = miuiClockView53.getIClockView(clockViewType);
                            int height10 = aodContainerView6.getHeight();
                            if (Utils.isFoldDevice()) {
                                i2 = 2400;
                                i3 = 1080;
                            } else {
                                i2 = (int) ((i4 * 180) / 108.0f);
                                i3 = i4;
                            }
                            MiuiClockView miuiClockView54 = this.mMiuiClockView;
                            if (miuiClockView54 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView54 = null;
                            }
                            this.mAllView = miuiClockView54.getIClockView(clockViewType);
                            MiuiClockView miuiClockView55 = this.mMiuiClockView;
                            if (miuiClockView55 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView55 = null;
                            }
                            View iClockView5 = miuiClockView55.getIClockView(ClockViewType.HOUR1);
                            MiuiClockView miuiClockView56 = this.mMiuiClockView;
                            if (miuiClockView56 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView56 = null;
                            }
                            View iClockView6 = miuiClockView56.getIClockView(ClockViewType.HOUR2);
                            if (Intrinsics.areEqual(this.mCurrentClockTemplateId, "eastern_c")) {
                                MiuiClockView miuiClockView57 = this.mMiuiClockView;
                                if (miuiClockView57 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView57 = null;
                                }
                                if (miuiClockView57.getClockBean().getStyle() == this.mEasternClockC_Style_B && Utils.isFoldDevice() && Utils.inLargeScreen(getContext()) && this.mSize == 2) {
                                    MiuiClockView miuiClockView58 = this.mMiuiClockView;
                                    if (miuiClockView58 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                        miuiClockView58 = null;
                                    }
                                    EasternArtCBase easternArtCBase = (EasternArtCBase) miuiClockView58.getIClockView(clockViewType);
                                    if (easternArtCBase != null && easternArtCBase.isOnHour()) {
                                        iClockView5 = iClockView6;
                                    }
                                }
                            }
                            float f20 = height10 / i2;
                            MiuiClockView miuiClockView59 = this.mMiuiClockView;
                            if (miuiClockView59 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView59 = null;
                            }
                            float f21 = 2;
                            miuiClockView59.setTranslationX((aodContainerView6.getWidth() - i3) / f21);
                            if (Utils.isLayoutRtl()) {
                                MiuiClockView miuiClockView60 = this.mMiuiClockView;
                                if (miuiClockView60 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView60 = null;
                                }
                                MiuiClockView miuiClockView61 = this.mMiuiClockView;
                                if (miuiClockView61 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    miuiClockView61 = null;
                                }
                                miuiClockView60.setTranslationX(-miuiClockView61.getTranslationX());
                            }
                            MiuiClockView miuiClockView62 = this.mMiuiClockView;
                            if (miuiClockView62 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                miuiClockView62 = null;
                            }
                            float height11 = (aodContainerView6.getHeight() - i2) / 2;
                            View view4 = this.mAllView;
                            Integer numValueOf = view4 != null ? Integer.valueOf(view4.getHeight()) : null;
                            Intrinsics.checkNotNull(numValueOf);
                            int iIntValue = i2 - numValueOf.intValue();
                            Intrinsics.checkNotNull(iClockView5);
                            ViewGroup.LayoutParams layoutParams5 = iClockView5.getLayoutParams();
                            ViewGroup.MarginLayoutParams marginLayoutParams5 = layoutParams5 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams5 : null;
                            float f22 = height11 + (((iIntValue + (marginLayoutParams5 != null ? marginLayoutParams5.topMargin : 0)) * f20) / f21);
                            ViewGroup.LayoutParams layoutParams6 = iClockView5.getLayoutParams();
                            ViewGroup.MarginLayoutParams marginLayoutParams6 = layoutParams6 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams6 : null;
                            miuiClockView62.setTranslationY(f22 - ((marginLayoutParams6 != null ? marginLayoutParams6.topMargin : 0) * f20));
                            scaleForClockView(f20);
                        }
                    }
                }
                break;
        }
        StyleInfo styleInfo13 = this.mStyleInfo;
        if (styleInfo13 != null) {
            if (styleInfo13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                styleInfo13 = null;
            }
            if (styleInfo13.isForAodNotification()) {
                ViewParent parent11 = getParent();
                ViewParent parent12 = (parent11 == null || (parent = parent11.getParent()) == null) ? null : parent.getParent();
                AodContainerView aodContainerView7 = parent12 instanceof AodContainerView ? (AodContainerView) parent12 : null;
                if (aodContainerView7 == null) {
                    return;
                }
                scaleForClockView(fCoerceAtLeast);
                MiuiClockView miuiClockView63 = this.mMiuiClockView;
                if (miuiClockView63 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                    miuiClockView63 = null;
                }
                this.mAllView = miuiClockView63.getIClockView(ClockViewType.ALL_VIEW);
                MiuiClockView miuiClockView64 = this.mMiuiClockView;
                if (miuiClockView64 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                    miuiClockView64 = null;
                }
                miuiClockView64.setTranslationX(-f);
                MiuiClockView miuiClockView65 = this.mMiuiClockView;
                if (miuiClockView65 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                    miuiClockView65 = null;
                }
                miuiClockView65.setTranslationY(-f2);
                View view5 = this.mAllView;
                Integer numValueOf2 = view5 != null ? Integer.valueOf(view5.getHeight()) : null;
                if (Intrinsics.areEqual(this.mCurrentClockTemplateId, "rhombus") && this.mAllView != null && numValueOf2 != null && numValueOf2.intValue() != 0) {
                    MiuiClockView miuiClockView66 = this.mMiuiClockView;
                    if (miuiClockView66 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                        miuiClockView66 = null;
                    }
                    MiuiClockView miuiClockView67 = this.mMiuiClockView;
                    if (miuiClockView67 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                        miuiClockView67 = null;
                    }
                    miuiClockView66.setTranslationY(((miuiClockView67.getTranslationY() + (aodContainerView7.getHeight() / 2)) - ((numValueOf2.intValue() * fCoerceAtLeast) / 2)) - DeviceInfo.dp2Px(18));
                }
                if (Utils.isLayoutRtl()) {
                    MiuiClockView miuiClockView68 = this.mMiuiClockView;
                    if (miuiClockView68 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                        miuiClockView68 = null;
                    }
                    MiuiClockView miuiClockView69 = this.mMiuiClockView;
                    if (miuiClockView69 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                        miuiClockView = null;
                    } else {
                        miuiClockView = miuiClockView69;
                    }
                    miuiClockView68.setTranslationX(-miuiClockView.getTranslationX());
                }
            }
        }
    }

    public final void scaleForSmallView(float f) {
        MiuiClockView miuiClockView = this.mMiuiClockView;
        if (miuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        }
        miuiClockView.setScaleRatio(f);
    }

    public final void setBottomAreaColor(int i, TextView textView, ImageView imageView) {
        Drawable drawable;
        TextView textView2 = this.mDateLunar;
        if (textView2 != null) {
            textView2.setTextColor(i);
        }
        TextView textView3 = this.mDateLunar;
        if (textView3 != null) {
            textView3.setAlpha(1.0f);
        }
        TextView textView4 = this.mClockDate;
        if (textView4 != null) {
            textView4.setTextColor(i);
        }
        TextView textView5 = this.mClockDate;
        if (textView5 != null) {
            textView5.setAlpha(1.0f);
        }
        if (textView != null) {
            textView.setTextColor(i);
        }
        if (imageView == null || (drawable = imageView.getDrawable()) == null) {
            return;
        }
        drawable.setAlpha(255);
        drawable.setTint(i);
    }

    public final void setClockViewVisible(boolean z) {
        MiuiClockView miuiClockView = null;
        if (!Intrinsics.areEqual(this.mCurrentClockTemplateId, "depth_pets") || isDualClock()) {
            MiuiClockView miuiClockView2 = this.mMiuiClockView;
            if (miuiClockView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            } else {
                miuiClockView = miuiClockView2;
            }
            miuiClockView.setVisibility(z ? 0 : 4);
            return;
        }
        MiuiClockView miuiClockView3 = this.mMiuiClockView;
        if (miuiClockView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
        } else {
            miuiClockView = miuiClockView3;
        }
        miuiClockView.setVisibility(0);
    }

    public final void setGravityAndMargin(int i, int i2) {
        Integer numValueOf;
        MiuiClockView miuiClockView = this.mMiuiClockView;
        if (miuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        }
        View iClockView = miuiClockView.getIClockView(ClockViewType.ALL_VIEW);
        if (iClockView != null) {
            ViewGroup.LayoutParams layoutParams = iClockView.getLayoutParams();
            numValueOf = Integer.valueOf(layoutParams instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams).getMarginStart() : 0);
        } else {
            numValueOf = null;
        }
        if (numValueOf == null || numValueOf.intValue() == 0) {
            numValueOf = Integer.valueOf(i);
        }
        TextView textView = this.mDateLunar;
        if (textView != null) {
            ViewGroup.LayoutParams layoutParams2 = textView.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams2, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) layoutParams2;
            layoutParams3.gravity = i2;
            if (i2 == 8388611) {
                layoutParams3.setMarginStart(numValueOf.intValue());
            }
            if (i2 == 8388613) {
                layoutParams3.setMarginEnd(numValueOf.intValue());
            }
            textView.setLayoutParams(layoutParams3);
        }
        TextView textView2 = this.mClockDate;
        if (textView2 != null) {
            ViewGroup.LayoutParams layoutParams4 = textView2.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams4, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
            LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) layoutParams4;
            layoutParams5.gravity = i2;
            if (i2 == 8388611) {
                layoutParams5.setMarginStart(numValueOf.intValue());
            }
            if (i2 == 8388613) {
                layoutParams5.setMarginEnd(numValueOf.intValue());
            }
            textView2.setLayoutParams(layoutParams5);
        }
        View view = this.mBattery;
        if (view != null) {
            ViewGroup.LayoutParams layoutParams6 = view.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams6, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
            LinearLayout.LayoutParams layoutParams7 = (LinearLayout.LayoutParams) layoutParams6;
            if (i2 == 8388611) {
                layoutParams7.setMarginStart(numValueOf.intValue());
            }
            if (i2 == 8388613) {
                layoutParams7.setMarginEnd(numValueOf.intValue());
            }
            layoutParams7.gravity = i2;
            view.setLayoutParams(layoutParams7);
        }
        View view2 = this.mIcon;
        ViewGroup.LayoutParams layoutParams8 = view2 != null ? view2.getLayoutParams() : null;
        LinearLayout.LayoutParams layoutParams9 = layoutParams8 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams8 : null;
        View view3 = this.mFocusIcon;
        ViewGroup.LayoutParams layoutParams10 = view3 != null ? view3.getLayoutParams() : null;
        LinearLayout.LayoutParams layoutParams11 = layoutParams10 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams10 : null;
        if (i2 == 8388611) {
            if (layoutParams9 != null) {
                layoutParams9.setMarginStart(numValueOf.intValue() + getResources().getDimensionPixelOffset(R.dimen.icons_margin_left));
            }
            if (layoutParams11 != null) {
                layoutParams11.setMarginStart(numValueOf.intValue() + getResources().getDimensionPixelOffset(R.dimen.icons_margin_left));
            }
        }
        if (i2 == 8388613) {
            if (layoutParams9 != null) {
                layoutParams9.setMarginEnd(numValueOf.intValue() + getResources().getDimensionPixelOffset(R.dimen.icons_margin_left));
            }
            if (layoutParams11 != null) {
                layoutParams11.setMarginEnd(numValueOf.intValue() + getResources().getDimensionPixelOffset(R.dimen.icons_margin_left));
            }
        }
        if (layoutParams9 != null) {
            layoutParams9.gravity = i2;
        }
        if (layoutParams11 != null) {
            layoutParams11.gravity = i2;
        }
        View view4 = this.mIcon;
        if (view4 != null) {
            view4.setLayoutParams(layoutParams9);
        }
        View view5 = this.mFocusIcon;
        if (view5 != null) {
            view5.setLayoutParams(layoutParams11);
        }
    }

    public final void setMClockIsChange(boolean z) {
        this.mClockIsChange = z;
    }

    public final void setSize(int i) {
        getResources().getDimensionPixelSize(R.dimen.super_wallpaper_time_text_size);
        float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.super_wallpaper_date_text_size);
        if (i == 2 || i == 6) {
            dimensionPixelSize *= Utils.getSmallViewWidth(getContext()) / 1080.0f;
        }
        TextView textView = this.mClockDate;
        if (textView != null) {
            textView.setTextSize(0, dimensionPixelSize);
        }
        TextView textView2 = this.mDateLunar;
        if (textView2 != null) {
            textView2.setTextSize(0, dimensionPixelSize);
        }
        updateClockViewLayout(i);
    }

    public final void setStyleInfo(@NotNull StyleInfo styleInfo) {
        Intrinsics.checkNotNullParameter(styleInfo, "styleInfo");
        this.mStyleInfo = styleInfo;
    }

    /* JADX WARN: Code duplicated, block: B:125:0x01e5  */
    /* JADX WARN: Code duplicated, block: B:146:0x0246  */
    /* JADX WARN: Code duplicated, block: B:35:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:93:0x0176  */
    /* JADX WARN: Multi-variable type inference failed */
    public final void setViewGravity() {
        View view;
        Resources resources;
        Resources resources2;
        int i = 0;
        if (isDualClock()) {
            View viewFindViewById = findViewById(R.id.clock_info_container);
            if (viewFindViewById != null) {
                ViewUtil.setMarginTop(viewFindViewById, this.mContext.getResources().getDimensionPixelSize(R.dimen.linkage_dual_clockInfo_margin));
            }
            View view2 = this.mIcon;
            if (view2 != null) {
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
                layoutParams2.setMarginStart(0);
                layoutParams2.setMarginEnd(0);
                layoutParams2.gravity = 17;
                view2.setLayoutParams(layoutParams2);
            }
            View view3 = this.mFocusIcon;
            if (view3 != null) {
                ViewGroup.LayoutParams layoutParams3 = view3.getLayoutParams();
                Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) layoutParams3;
                layoutParams4.setMarginStart(0);
                layoutParams4.setMarginEnd(0);
                layoutParams4.gravity = 17;
                view3.setLayoutParams(layoutParams4);
            }
            this.mClockGravity = 17;
            TextView textView = this.mClockDate;
            if (textView != null) {
                textView.setVisibility(8);
                return;
            }
            return;
        }
        MiuiClockView miuiClockView = null;
        MiuiClockView miuiClockView2 = null;
        MiuiClockView miuiClockView3 = null;
        MiuiClockView miuiClockView4 = null;
        numValueOf = null;
        Integer numValueOf = null;
        if (Intrinsics.areEqual("classic", this.mCurrentClockTemplateId)) {
            MiuiClockView miuiClockView5 = this.mMiuiClockView;
            if (miuiClockView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            } else {
                miuiClockView = miuiClockView5;
            }
            View iClockView = miuiClockView.getIClockView(ClockViewType.TIME_AREA);
            if (iClockView != null) {
                ViewGroup.LayoutParams layoutParams5 = iClockView.getLayoutParams();
                if ((layoutParams5 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams5).getMarginStart() : 0) == 0) {
                    setGravityAndMargin(this.mContext.getResources().getDimensionPixelSize(R.dimen.classic_area_2_3_4_margin_start_end), 8388611);
                } else {
                    ViewGroup.LayoutParams layoutParams6 = iClockView.getLayoutParams();
                    setGravityAndMargin(layoutParams6 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams6).getMarginStart() : 0, 8388611);
                }
            } else {
                setGravityAndMargin(this.mContext.getResources().getDimensionPixelSize(R.dimen.classic_area_2_3_4_margin_start_end), 8388611);
            }
            this.mClockGravity = 8388611;
            return;
        }
        if (Intrinsics.areEqual("magazine_a", this.mCurrentClockTemplateId)) {
            MiuiClockView miuiClockView6 = this.mMiuiClockView;
            if (miuiClockView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                miuiClockView6 = null;
            }
            ClockBean clockBean = miuiClockView6.getClockBean();
            if ((clockBean != null && clockBean.getStyle() == 2) == false) {
                MiuiClockView miuiClockView7 = this.mMiuiClockView;
                if (miuiClockView7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                    miuiClockView7 = null;
                }
                ClockBean clockBean2 = miuiClockView7.getClockBean();
                if ((clockBean2 != null && clockBean2.getStyle() == 1) == true) {
                    setGravityAndMargin(this.mContext.getResources().getDimensionPixelSize(R.dimen.miui_magazine_a_clock_margin_start), 8388611);
                    this.mClockGravity = 8388611;
                    return;
                }
                MiuiClockView miuiClockView8 = this.mMiuiClockView;
                if (miuiClockView8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                } else {
                    miuiClockView2 = miuiClockView8;
                }
                ClockBean clockBean3 = miuiClockView2.getClockBean();
                if (clockBean3 != null && clockBean3.getStyle() == 3) {
                    i = 1;
                }
                if (i != 0) {
                    setGravityAndMargin(this.mContext.getResources().getDimensionPixelSize(R.dimen.miui_magazine_a_clock_margin_start), 8388613);
                    this.mClockGravity = 8388613;
                    return;
                }
                return;
            }
        }
        if (Intrinsics.areEqual("magazine_b", this.mCurrentClockTemplateId)) {
            MiuiClockView miuiClockView9 = this.mMiuiClockView;
            if (miuiClockView9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            } else {
                miuiClockView3 = miuiClockView9;
            }
            View iClockView2 = miuiClockView3.getIClockView(ClockViewType.CLOCK_CONTAINER);
            if (iClockView2 != null) {
                ViewGroup.LayoutParams layoutParams7 = iClockView2.getLayoutParams();
                if ((layoutParams7 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams7).getMarginEnd() : 0) == 0) {
                    setGravityAndMargin(this.mContext.getResources().getDimensionPixelSize(R.dimen.linkage_large_magazine_b_margin), 8388613);
                } else {
                    ViewGroup.LayoutParams layoutParams8 = iClockView2.getLayoutParams();
                    setGravityAndMargin(layoutParams8 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams8).getMarginEnd() : 0, 8388613);
                }
            } else {
                setGravityAndMargin(this.mContext.getResources().getDimensionPixelSize(R.dimen.linkage_large_magazine_b_margin), 8388613);
            }
            this.mClockGravity = 8388613;
            return;
        }
        if (Intrinsics.areEqual("magazine_c", this.mCurrentClockTemplateId)) {
            MiuiClockView miuiClockView10 = this.mMiuiClockView;
            if (miuiClockView10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                miuiClockView10 = null;
            }
            ClockBean clockBean4 = miuiClockView10.getClockBean();
            if ((clockBean4 != null && clockBean4.getStyle() == 5) != false) {
                MiuiClockView miuiClockView11 = this.mMiuiClockView;
                if (miuiClockView11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                } else {
                    miuiClockView4 = miuiClockView11;
                }
                View iClockView3 = miuiClockView4.getIClockView(ClockViewType.CLOCK_CONTAINER);
                if (iClockView3 != null) {
                    ViewGroup.LayoutParams layoutParams9 = iClockView3.getLayoutParams();
                    if ((layoutParams9 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams9).getMarginStart() : 0) == 0) {
                        setGravityAndMargin(this.mContext.getResources().getDimensionPixelSize(R.dimen.miui_magazine_c_clock_style1_margin_start), 8388611);
                    } else {
                        ViewGroup.LayoutParams layoutParams10 = iClockView3.getLayoutParams();
                        setGravityAndMargin(layoutParams10 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams10).getMarginStart() : 0, 8388611);
                    }
                } else {
                    setGravityAndMargin(this.mContext.getResources().getDimensionPixelSize(R.dimen.miui_magazine_c_clock_style1_margin_start), 8388611);
                }
                TextView textView2 = this.mClockDate;
                if (textView2 != null) {
                    textView2.setVisibility(0);
                }
                this.mClockGravity = 8388611;
                return;
            }
        }
        View viewFindViewById2 = findViewById(R.id.clock_info_container);
        if (viewFindViewById2 != null) {
            ViewGroup.LayoutParams layoutParams11 = viewFindViewById2.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams11, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
            ConstraintLayout.LayoutParams layoutParams12 = (ConstraintLayout.LayoutParams) layoutParams11;
            layoutParams12.leftToLeft = 0;
            layoutParams12.rightToRight = 0;
            if (Intrinsics.areEqual("eastern_a", this.mCurrentClockTemplateId)) {
                MiuiClockView miuiClockView12 = this.mMiuiClockView;
                if (miuiClockView12 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                    miuiClockView12 = null;
                }
                ClockBean clockBean5 = miuiClockView12.getClockBean();
                if (clockBean5 != null && clockBean5.getStyle() == 1) {
                    ((ViewGroup.MarginLayoutParams) layoutParams12).topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.icon_container_margin_top);
                } else {
                    ((ViewGroup.MarginLayoutParams) layoutParams12).topMargin = 0;
                }
            } else {
                ((ViewGroup.MarginLayoutParams) layoutParams12).topMargin = 0;
            }
            viewFindViewById2.setLayoutParams(layoutParams12);
        }
        View view4 = this.mIcon;
        ViewGroup.LayoutParams layoutParams13 = view4 != null ? view4.getLayoutParams() : null;
        LinearLayout.LayoutParams layoutParams14 = layoutParams13 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams13 : null;
        if (layoutParams14 != null) {
            layoutParams14.setMarginStart(0);
        }
        if (layoutParams14 != null) {
            layoutParams14.setMarginEnd(0);
        }
        if (layoutParams14 != null) {
            layoutParams14.gravity = 17;
        }
        View view5 = this.mIcon;
        if (view5 != null) {
            view5.setLayoutParams(layoutParams14);
        }
        View view6 = this.mFocusIcon;
        ViewGroup.LayoutParams layoutParams15 = view6 != null ? view6.getLayoutParams() : null;
        LinearLayout.LayoutParams layoutParams16 = layoutParams15 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams15 : null;
        if (layoutParams16 != null) {
            layoutParams16.setMarginStart(0);
        }
        if (layoutParams16 != null) {
            layoutParams16.setMarginEnd(0);
        }
        if (layoutParams16 != null) {
            layoutParams16.gravity = 17;
        }
        View view7 = this.mFocusIcon;
        if (view7 != null) {
            view7.setLayoutParams(layoutParams16);
        }
        this.mClockGravity = 17;
        if (Intrinsics.areEqual("rhombus", this.mCurrentClockTemplateId) || Intrinsics.areEqual("magazine_c", this.mCurrentClockTemplateId)) {
            TextView textView3 = this.mClockDate;
            if (textView3 != null) {
                textView3.setVisibility(0);
            }
        } else if (Intrinsics.areEqual("depth_pets", this.mCurrentClockTemplateId)) {
            View view8 = this.mBattery;
            ViewGroup.LayoutParams layoutParams17 = view8 != null ? view8.getLayoutParams() : null;
            LinearLayout.LayoutParams layoutParams18 = layoutParams17 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams17 : null;
            if (layoutParams18 != null) {
                Context context = this.mContext;
                Integer numValueOf2 = (context == null || (resources2 = context.getResources()) == null) ? null : Integer.valueOf(resources2.getDimensionPixelSize(R.dimen.battery_top));
                Intrinsics.checkNotNull(numValueOf2);
                layoutParams18.topMargin = numValueOf2.intValue();
            }
            if (layoutParams18 != null) {
                Context context2 = this.mContext;
                if (context2 != null && (resources = context2.getResources()) != null) {
                    numValueOf = Integer.valueOf(resources.getDimensionPixelSize(R.dimen.battery_bottom));
                }
                Intrinsics.checkNotNull(numValueOf);
                layoutParams18.bottomMargin = numValueOf.intValue();
            }
        } else if (isAllInOneTemplate(this.mCurrentClockTemplateId)) {
            View view9 = this.mBattery;
            ViewGroup.LayoutParams layoutParams19 = view9 != null ? view9.getLayoutParams() : null;
            LinearLayout.LayoutParams layoutParams20 = layoutParams19 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams19 : null;
            if (layoutParams20 != null) {
                Resources resources3 = this.mContext.getResources();
                layoutParams20.bottomMargin = resources3 != null ? resources3.getDimensionPixelSize(R.dimen.battery_bottom) : 0;
            }
        }
        if (!Intrinsics.areEqual("magazine_c", this.mCurrentClockTemplateId) || (view = this.mClockInfoContainer) == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams21 = view.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams21, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
        ConstraintLayout.LayoutParams layoutParams22 = (ConstraintLayout.LayoutParams) layoutParams21;
        ((ViewGroup.MarginLayoutParams) layoutParams22).topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.linkage_large_magazine_c_top_margin_container);
        view.setLayoutParams(layoutParams22);
    }

    public final boolean shouldShowDateByTemplate() {
        if (isDualClock()) {
            return false;
        }
        String str = this.mCurrentClockTemplateId;
        return Intrinsics.areEqual(str, "magazine_c") || Intrinsics.areEqual(str, "rhombus");
    }

    public final boolean shouldShowLunarByTemplate() {
        if (isDualClock()) {
            return false;
        }
        return !notSupportLunarTime();
    }

    public final void updateBattery() {
        int i = this.mSize;
        if (i == 2 || i == 4 || i == 6) {
            View view = this.mBattery;
            if (view != null) {
                view.setVisibility(8);
                return;
            }
            return;
        }
        View view2 = this.mBattery;
        if (view2 != null) {
            StyleInfo styleInfo = this.mStyleInfo;
            if (styleInfo == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                styleInfo = null;
            }
            view2.setVisibility(styleInfo.isBatterySwitchOn() ? 0 : 8);
        }
    }

    public final void updateClockBeanChange() {
        LinkageStyleClockView$miuiClockInfoListener$1 linkageStyleClockView$miuiClockInfoListener$1 = this.miuiClockInfoListener;
        MiuiClockView miuiClockView = this.mMiuiClockView;
        if (miuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        }
        linkageStyleClockView$miuiClockInfoListener$1.onClockBeanChange(miuiClockView.getClockBean(), false);
    }

    public final void updateClockColor(int i) {
        Drawable drawable;
        MiuiClockView miuiClockView = null;
        if (Utils.isAodProcess()) {
            if (AODApplicationDelegate.mClockColorMap == null || AODApplicationDelegate.mClockStyleIsDeep == null) {
                return;
            }
            MiuiClockView miuiClockView2 = this.mMiuiClockView;
            if (miuiClockView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                miuiClockView2 = null;
            }
            miuiClockView2.setClockPalette(MiuiSettingsUtils.getIntFromSecure(this.mContext.getContentResolver(), "color_scheme", 3, UserHandleUtils.USER_CURRENT), !AODApplicationDelegate.mClockStyleIsDeep.booleanValue(), AODApplicationDelegate.mClockColorMap);
        } else {
            if (AODApplicationDelegate.mRealClockColorMap == null || AODApplicationDelegate.mRealClockStyleIsDeep == null) {
                return;
            }
            MiuiClockView miuiClockView3 = this.mMiuiClockView;
            if (miuiClockView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                miuiClockView3 = null;
            }
            miuiClockView3.setClockPalette(SettingsHolder.mColorScheme, !AODApplicationDelegate.mRealClockStyleIsDeep.booleanValue(), AODApplicationDelegate.mRealClockColorMap);
        }
        View view = this.mBattery;
        TextView textView = view != null ? (TextView) view.findViewById(R.id.aod_battery_digital) : null;
        View view2 = this.mBattery;
        ImageView imageView = view2 != null ? (ImageView) view2.findViewById(R.id.aod_battery_icon) : null;
        if (isDualClock()) {
            MiuiClockView miuiClockView4 = this.mMiuiClockView;
            if (miuiClockView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                miuiClockView4 = null;
            }
            miuiClockView4.setTextColorDark(false);
            if (imageView == null || (drawable = imageView.getDrawable()) == null) {
                return;
            }
            drawable.setAlpha(255);
            DrawableCompat.setTintList(drawable, null);
            return;
        }
        if (Intrinsics.areEqual("eastern_a", this.mCurrentClockTemplateId) || Intrinsics.areEqual("eastern_b", this.mCurrentClockTemplateId) || Intrinsics.areEqual("eastern_c", this.mCurrentClockTemplateId)) {
            MiuiClockView miuiClockView5 = this.mMiuiClockView;
            if (miuiClockView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            } else {
                miuiClockView = miuiClockView5;
            }
            ClockStyleInfo clockStyleInfo = miuiClockView.getClockStyleInfo();
            if (clockStyleInfo != null) {
                setBottomAreaColor(clockStyleInfo.getAodPrimaryColor(), textView, imageView);
                return;
            }
            return;
        }
        if (Intrinsics.areEqual("rhombus", this.mCurrentClockTemplateId)) {
            setBottomAreaColor(this.mContext.getColor(R.color.aod_linkage_clock_default_color), textView, imageView);
            return;
        }
        MiuiClockView miuiClockView6 = this.mMiuiClockView;
        if (miuiClockView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
        } else {
            miuiClockView = miuiClockView6;
        }
        ClockStyleInfo clockStyleInfo2 = miuiClockView.getClockStyleInfo();
        if (clockStyleInfo2 != null) {
            setBottomAreaColor(clockStyleInfo2.getAodPrimaryColor(), textView, imageView);
        }
    }

    public final void updateClockViewLayout(int i) {
        final View view;
        int i2;
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 6) {
                        return;
                    }
                }
            }
            if (!isDualClock()) {
                MiuiClockView miuiClockView = this.mMiuiClockView;
                MiuiClockView miuiClockView2 = null;
                if (miuiClockView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                    miuiClockView = null;
                }
                ViewGroup.LayoutParams layoutParams = miuiClockView.getLayoutParams();
                WindowUtils windowUtils = WindowUtils.INSTANCE;
                int widthPixel = windowUtils.getWidthPixel(this.mContext);
                if (isAllInOneTemplate(this.mCurrentClockTemplateId)) {
                    i2 = windowUtils.getWindowSize(this.mContext).y;
                } else if (Utils.isFoldDevice()) {
                    widthPixel = 1080;
                    i2 = 2400;
                } else if (Intrinsics.areEqual(this.mCurrentClockTemplateId, "eastern_c") || Intrinsics.areEqual(this.mCurrentClockTemplateId, "eastern_b") || Intrinsics.areEqual(this.mCurrentClockTemplateId, "eastern_a") || Intrinsics.areEqual(this.mCurrentClockTemplateId, "depth_pets")) {
                    i2 = (int) ((widthPixel * 180) / 108.0f);
                } else {
                    i2 = Intrinsics.areEqual(this.mCurrentClockTemplateId, "rhombus") ? windowUtils.getWindowSize(this.mContext).y : widthPixel;
                }
                StyleInfo styleInfo = this.mStyleInfo;
                if (styleInfo != null) {
                    if (styleInfo == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
                        styleInfo = null;
                    }
                    if (styleInfo.isForAodNotification()) {
                        Context context = getContext();
                        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                        int widthPixel2 = windowUtils.getWidthPixel(context);
                        Context context2 = getContext();
                        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                        widthPixel = widthPixel2;
                        i2 = windowUtils.getWindowSize(context2).y;
                    }
                }
                if (layoutParams != null) {
                    layoutParams.width = widthPixel;
                }
                if (layoutParams != null) {
                    layoutParams.height = i2;
                }
                MiuiClockView miuiClockView3 = this.mMiuiClockView;
                if (miuiClockView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                } else {
                    miuiClockView2 = miuiClockView3;
                }
                miuiClockView2.setLayoutParams(layoutParams);
            }
            scaleForSmallView();
            return;
        }
        if (!isDualClock()) {
            if (!Intrinsics.areEqual(this.mCurrentClockTemplateId, "rhombus") || (view = this.mClockInfoContainer) == null) {
                return;
            }
            view.post(new Runnable() { // from class: com.miui.aod.linkagestyle.LinkageStyleClockView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    LinkageStyleClockView.updateClockViewLayout$lambda$6$lambda$5(this.f$0, view);
                }
            });
            return;
        }
        if (i == 3) {
            if (this.mMiuiClockView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            }
            ViewGroup.LayoutParams layoutParams2 = getClockFrameLayout().getLayoutParams();
            Intrinsics.checkNotNull(layoutParams2, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
            getViewMargin((ViewGroup.MarginLayoutParams) layoutParams2);
        }
    }

    public final void updateClockViewScaleRatio(int i) {
        if (!isDualClock()) {
            this.mScaleRatio = (!Intrinsics.areEqual(this.mCurrentClockTemplateId, "rhombus") || i == 2 || i == 6) ? 1.0f : 0.6f;
        }
        if (i == 2 && this.mScaleRatio == 1.0f) {
            return;
        }
        scaleForClockView(this.mScaleRatio);
    }

    public final void updateSvgResource() {
        if (DeviceUtil.INSTANCE.isCpuAndGpuHighLevel()) {
            MiuiClockView miuiClockView = this.mMiuiClockView;
            MiuiClockView miuiClockView2 = null;
            if (miuiClockView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                miuiClockView = null;
            }
            View iClockView = miuiClockView.getIClockView(ClockViewType.ALL_VIEW);
            this.mAllView = iClockView;
            Intrinsics.checkNotNull(iClockView, "null cannot be cast to non-null type com.miui.clock.rhombus.MiuiRhombusBase");
            BaseFontStyle style = ((MiuiRhombusBase) iClockView).getStyle();
            MiuiClockView miuiClockView3 = this.mMiuiClockView;
            if (miuiClockView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                miuiClockView3 = null;
            }
            ClockBean clockBean = miuiClockView3.getClockBean();
            Integer numValueOf = clockBean != null ? Integer.valueOf(clockBean.getStyle()) : null;
            if (numValueOf != null && numValueOf.intValue() == 1) {
                style.setUserDefinedResource(SVGUtils.SVG_A_RESOURCE);
            } else if (numValueOf != null && numValueOf.intValue() == 2) {
                style.setUserDefinedResource(SVGUtils.SVG_B_RESOURCE);
                style.setUserDefinedColonResource(R.drawable.colon_28);
            } else if (numValueOf != null && numValueOf.intValue() == 3) {
                style.setUserDefinedResource(SVGUtils.SVG_C_RESOURCE);
            } else if (numValueOf != null && numValueOf.intValue() == 4) {
                style.setUserDefinedResource(SVGUtils.SVG_D_RESOURCE);
            } else if (numValueOf != null && numValueOf.intValue() == 7) {
                style.setUserDefinedResource(SVGUtils.SVG_G_RESOURCE);
            }
            MiuiClockView miuiClockView4 = this.mMiuiClockView;
            if (miuiClockView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            } else {
                miuiClockView2 = miuiClockView4;
            }
            miuiClockView2.updateTime();
        }
    }

    public final void updateTime() {
        MiuiClockView miuiClockView = this.mMiuiClockView;
        if (miuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            miuiClockView = null;
        }
        miuiClockView.updateTime();
    }
}
