package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintProperties;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import com.miui.aod.R;
import com.miui.aod.category.FlipLinkageClockCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.flip.AODTinyMiuiClockView;
import com.miui.aod.flip.ConstraintSetExtKt;
import com.miui.aod.flip.FlipLinkageStyleClockView;
import com.miui.aod.flip.FlipLinkageStyleController;
import com.miui.aod.flip.FlipLinkageTemplateId;
import com.miui.aod.flip.Ids;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: FlipLinkageClock.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class FlipLinkageClock implements ILinkageClock, FlipILinkageClock {
    public FrameLayout mClockContainer;
    public LinearLayout mClockInfoContainer;
    public FlipLinkageStyleClockView mClockViewContainer;
    public Context mContext;
    public Direction mDirection;
    public Guideline mGideLineHorizon;
    public Guideline mGideLineVertical;
    public final int mSize;
    public FlipLinkageClockCategoryInfo mStyleInfo;
    public FlipLinkageTemplateId templateId;
    public final Lazy mClockInfoContainerProperties$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.widget.FlipLinkageClock$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FlipLinkageClock.mClockInfoContainerProperties_delegate$lambda$0(this.f$0);
        }
    });
    public final Lazy mClockContainerProperties$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.widget.FlipLinkageClock$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FlipLinkageClock.mClockContainerProperties_delegate$lambda$1(this.f$0);
        }
    });
    public final Lazy clockView$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.widget.FlipLinkageClock$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FlipLinkageClock.clockView_delegate$lambda$2(this.f$0);
        }
    });
    public final Lazy icons$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.widget.FlipLinkageClock$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FlipLinkageClock.icons_delegate$lambda$3(this.f$0);
        }
    });
    public final Lazy focusIcons$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.widget.FlipLinkageClock$$ExternalSyntheticLambda4
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FlipLinkageClock.focusIcons_delegate$lambda$4(this.f$0);
        }
    });

    /* JADX INFO: compiled from: FlipLinkageClock.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FlipLinkageTemplateId.values().length];
            try {
                iArr[FlipLinkageTemplateId.SPLICING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FlipLinkageTemplateId.Classic.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FlipLinkageTemplateId.Doodle.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FlipLinkageTemplateId.Pets.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[FlipLinkageTemplateId.Bubble.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[FlipLinkageTemplateId.Interactive_Pets.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[FlipLinkageTemplateId.AI_Pets.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public FlipLinkageClock(int i) {
        this.mSize = i;
    }

    public static final AODTinyMiuiClockView clockView_delegate$lambda$2(FlipLinkageClock flipLinkageClock) {
        FlipLinkageStyleClockView flipLinkageStyleClockView = flipLinkageClock.mClockViewContainer;
        if (flipLinkageStyleClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            flipLinkageStyleClockView = null;
        }
        return (AODTinyMiuiClockView) flipLinkageStyleClockView.findViewById(R.id.flip_clock);
    }

    public static final LinearLayout focusIcons_delegate$lambda$4(FlipLinkageClock flipLinkageClock) {
        LinearLayout linearLayout = flipLinkageClock.mClockInfoContainer;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockInfoContainer");
            linearLayout = null;
        }
        return (LinearLayout) linearLayout.findViewById(R.id.fn_container);
    }

    public static final LinearLayout icons_delegate$lambda$3(FlipLinkageClock flipLinkageClock) {
        LinearLayout linearLayout = flipLinkageClock.mClockInfoContainer;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockInfoContainer");
            linearLayout = null;
        }
        return (LinearLayout) linearLayout.findViewById(R.id.icons);
    }

    public static final ConstraintProperties mClockContainerProperties_delegate$lambda$1(FlipLinkageClock flipLinkageClock) {
        FrameLayout frameLayout = flipLinkageClock.mClockContainer;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockContainer");
            frameLayout = null;
        }
        return new ConstraintProperties(frameLayout);
    }

    public static final ConstraintProperties mClockInfoContainerProperties_delegate$lambda$0(FlipLinkageClock flipLinkageClock) {
        LinearLayout linearLayout = flipLinkageClock.mClockInfoContainer;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockInfoContainer");
            linearLayout = null;
        }
        return new ConstraintProperties(linearLayout);
    }

    private final void updateLayout() {
        this.mDirection = FlipLinkageStyleController.INSTANCE.getClockOrientation();
        FlipLinkageStyleClockView flipLinkageStyleClockView = this.mClockViewContainer;
        Direction direction = null;
        if (flipLinkageStyleClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            flipLinkageStyleClockView = null;
        }
        Direction direction2 = this.mDirection;
        if (direction2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mDirection");
        } else {
            direction = direction2;
        }
        flipLinkageStyleClockView.changeRotation(direction);
        updateCutout();
        updateLayoutWidth();
        updateConstraint();
        updateMargin();
        updateGravityForIcons();
    }

    @Override // com.miui.aod.widget.IAodClock
    public void bindView(View clock) {
        Intrinsics.checkNotNullParameter(clock, "clock");
        this.mContext = clock.getContext();
        this.mClockViewContainer = (FlipLinkageStyleClockView) clock.findViewById(R.id.flip_linkage_clock_container);
        this.mClockContainer = (FrameLayout) clock.findViewById(R.id.flip_clock_container);
        this.mClockInfoContainer = (LinearLayout) clock.findViewById(R.id.clock_info_container);
        this.mGideLineHorizon = (Guideline) clock.findViewById(R.id.guideline_horizontal);
        this.mGideLineVertical = (Guideline) clock.findViewById(R.id.guideline_vertical);
        FlipLinkageStyleClockView flipLinkageStyleClockView = this.mClockViewContainer;
        if (flipLinkageStyleClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            flipLinkageStyleClockView = null;
        }
        flipLinkageStyleClockView.bindView();
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getClockGravity() {
        ViewGroup.LayoutParams layoutParams = getFocusIcons().getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
        return ((LinearLayout.LayoutParams) layoutParams).gravity;
    }

    public final AODTinyMiuiClockView getClockView() {
        Object value = this.clockView$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (AODTinyMiuiClockView) value;
    }

    public final LinearLayout getFocusIcons() {
        Object value = this.focusIcons$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (LinearLayout) value;
    }

    public final LinearLayout getIcons() {
        Object value = this.icons$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (LinearLayout) value;
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        return R.layout.aod_content_linkage_flip;
    }

    public final ConstraintProperties getMClockContainerProperties() {
        return (ConstraintProperties) this.mClockContainerProperties$delegate.getValue();
    }

    public final ConstraintProperties getMClockInfoContainerProperties() {
        return (ConstraintProperties) this.mClockInfoContainerProperties$delegate.getValue();
    }

    @Override // com.miui.aod.widget.IAodClock
    public void onInflateComplete() {
        updateClockColor(-1);
    }

    @Override // com.miui.aod.widget.FlipILinkageClock
    public void onRotationChanged() {
        updateLayout();
        updateClockColor(-1);
    }

    @Override // com.miui.aod.widget.ILinkageClock
    public void releaseResources() {
    }

    @Override // com.miui.aod.widget.ILinkageClock
    public void setClockViewVisible(boolean z) {
        Log.d("FlipLinkageClock", "setClockViewVisible: " + z);
        FlipLinkageStyleClockView flipLinkageStyleClockView = this.mClockViewContainer;
        if (flipLinkageStyleClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            flipLinkageStyleClockView = null;
        }
        flipLinkageStyleClockView.setClockViewVisible(z);
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        if (styleInfo == null) {
            return;
        }
        updateClockColor(this.mSize);
        this.mStyleInfo = (FlipLinkageClockCategoryInfo) styleInfo;
        FlipLinkageStyleClockView flipLinkageStyleClockView = this.mClockViewContainer;
        FlipLinkageTemplateId flipLinkageTemplateId = null;
        if (flipLinkageStyleClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            flipLinkageStyleClockView = null;
        }
        FlipLinkageClockCategoryInfo flipLinkageClockCategoryInfo = this.mStyleInfo;
        if (flipLinkageClockCategoryInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
            flipLinkageClockCategoryInfo = null;
        }
        flipLinkageStyleClockView.setStyleInfo(flipLinkageClockCategoryInfo);
        FlipLinkageClockCategoryInfo flipLinkageClockCategoryInfo2 = this.mStyleInfo;
        if (flipLinkageClockCategoryInfo2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
            flipLinkageClockCategoryInfo2 = null;
        }
        this.templateId = flipLinkageClockCategoryInfo2.getTemplate();
        FlipLinkageStyleClockView flipLinkageStyleClockView2 = this.mClockViewContainer;
        if (flipLinkageStyleClockView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            flipLinkageStyleClockView2 = null;
        }
        FlipLinkageTemplateId flipLinkageTemplateId2 = this.templateId;
        if (flipLinkageTemplateId2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templateId");
        } else {
            flipLinkageTemplateId = flipLinkageTemplateId2;
        }
        flipLinkageStyleClockView2.initClockView(flipLinkageTemplateId);
        updateLayout();
    }

    @Override // com.miui.aod.widget.OnClockColorChangeListener
    public void updateClockColor(int i) {
    }

    public void updateConstraint() {
        FlipLinkageStyleClockView flipLinkageStyleClockView = this.mClockViewContainer;
        FlipLinkageStyleClockView flipLinkageStyleClockView2 = null;
        if (flipLinkageStyleClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            flipLinkageStyleClockView = null;
        }
        int id = flipLinkageStyleClockView.getId();
        FrameLayout frameLayout = this.mClockContainer;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockContainer");
            frameLayout = null;
        }
        int id2 = frameLayout.getId();
        LinearLayout linearLayout = this.mClockInfoContainer;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockInfoContainer");
            linearLayout = null;
        }
        int id3 = linearLayout.getId();
        Guideline guideline = this.mGideLineHorizon;
        if (guideline == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mGideLineHorizon");
            guideline = null;
        }
        int id4 = guideline.getId();
        Guideline guideline2 = this.mGideLineVertical;
        if (guideline2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mGideLineVertical");
            guideline2 = null;
        }
        Ids ids = new Ids(id, id2, id3, id4, guideline2.getId());
        FlipLinkageTemplateId flipLinkageTemplateId = this.templateId;
        if (flipLinkageTemplateId == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templateId");
            flipLinkageTemplateId = null;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[flipLinkageTemplateId.ordinal()]) {
            case 1:
                ConstraintSet constraintSet = new ConstraintSet();
                Direction direction = this.mDirection;
                if (direction == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDirection");
                    direction = null;
                }
                FlipLinkageStyleClockView flipLinkageStyleClockView3 = this.mClockViewContainer;
                if (flipLinkageStyleClockView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
                } else {
                    flipLinkageStyleClockView2 = flipLinkageStyleClockView3;
                }
                ConstraintSetExtKt.splicing(constraintSet, ids, direction, flipLinkageStyleClockView2);
                break;
            case 2:
                ConstraintSet constraintSet2 = new ConstraintSet();
                Direction direction2 = this.mDirection;
                if (direction2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDirection");
                    direction2 = null;
                }
                FlipLinkageStyleClockView flipLinkageStyleClockView4 = this.mClockViewContainer;
                if (flipLinkageStyleClockView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
                } else {
                    flipLinkageStyleClockView2 = flipLinkageStyleClockView4;
                }
                ConstraintSetExtKt.classic(constraintSet2, ids, direction2, flipLinkageStyleClockView2);
                break;
            case 3:
                ConstraintSet constraintSet3 = new ConstraintSet();
                Direction direction3 = this.mDirection;
                if (direction3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDirection");
                    direction3 = null;
                }
                FlipLinkageStyleClockView flipLinkageStyleClockView5 = this.mClockViewContainer;
                if (flipLinkageStyleClockView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
                } else {
                    flipLinkageStyleClockView2 = flipLinkageStyleClockView5;
                }
                ConstraintSetExtKt.doodle(constraintSet3, ids, direction3, flipLinkageStyleClockView2);
                break;
            case 4:
                ConstraintSet constraintSet4 = new ConstraintSet();
                Direction direction4 = this.mDirection;
                if (direction4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDirection");
                    direction4 = null;
                }
                FlipLinkageStyleClockView flipLinkageStyleClockView6 = this.mClockViewContainer;
                if (flipLinkageStyleClockView6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
                } else {
                    flipLinkageStyleClockView2 = flipLinkageStyleClockView6;
                }
                ConstraintSetExtKt.pets(constraintSet4, ids, direction4, flipLinkageStyleClockView2);
                break;
            case 5:
                ConstraintSet constraintSet5 = new ConstraintSet();
                Direction direction5 = this.mDirection;
                if (direction5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDirection");
                    direction5 = null;
                }
                FlipLinkageStyleClockView flipLinkageStyleClockView7 = this.mClockViewContainer;
                if (flipLinkageStyleClockView7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
                } else {
                    flipLinkageStyleClockView2 = flipLinkageStyleClockView7;
                }
                ConstraintSetExtKt.bubble(constraintSet5, ids, direction5, flipLinkageStyleClockView2);
                break;
            case 6:
                ConstraintSet constraintSet6 = new ConstraintSet();
                Direction direction6 = this.mDirection;
                if (direction6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDirection");
                    direction6 = null;
                }
                FlipLinkageStyleClockView flipLinkageStyleClockView8 = this.mClockViewContainer;
                if (flipLinkageStyleClockView8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
                } else {
                    flipLinkageStyleClockView2 = flipLinkageStyleClockView8;
                }
                ConstraintSetExtKt.interactive_Pets(constraintSet6, ids, direction6, flipLinkageStyleClockView2);
                break;
            case 7:
                ConstraintSet constraintSet7 = new ConstraintSet();
                Direction direction7 = this.mDirection;
                if (direction7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDirection");
                    direction7 = null;
                }
                FlipLinkageStyleClockView flipLinkageStyleClockView9 = this.mClockViewContainer;
                if (flipLinkageStyleClockView9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
                } else {
                    flipLinkageStyleClockView2 = flipLinkageStyleClockView9;
                }
                ConstraintSetExtKt.ai_Pets(constraintSet7, ids, direction7, flipLinkageStyleClockView2);
                break;
        }
    }

    public void updateCutout() {
        Context context = this.mContext;
        Guideline guideline = null;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mContext");
            context = null;
        }
        Display display = context.getDisplay();
        DisplayCutout cutout = display != null ? display.getCutout() : null;
        if (cutout == null) {
            Direction direction = this.mDirection;
            if (direction == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mDirection");
                direction = null;
            }
            if (direction == Direction.CAMERA_CUTOUT_ON_THE_LEFT) {
                Guideline guideline2 = this.mGideLineVertical;
                if (guideline2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mGideLineVertical");
                    guideline2 = null;
                }
                guideline2.setGuidelineBegin(398);
                Guideline guideline3 = this.mGideLineHorizon;
                if (guideline3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mGideLineHorizon");
                } else {
                    guideline = guideline3;
                }
                guideline.setGuidelineBegin(728);
            } else {
                Direction direction2 = this.mDirection;
                if (direction2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDirection");
                    direction2 = null;
                }
                if (direction2 == Direction.CAMERA_CUTOUT_ON_THE_RIGHT) {
                    Guideline guideline4 = this.mGideLineVertical;
                    if (guideline4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mGideLineVertical");
                        guideline4 = null;
                    }
                    guideline4.setGuidelineBegin(810);
                    Guideline guideline5 = this.mGideLineHorizon;
                    if (guideline5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mGideLineHorizon");
                    } else {
                        guideline = guideline5;
                    }
                    guideline.setGuidelineBegin(664);
                }
            }
            Log.d("FlipLinkageClock", "updateCutout: the cutout is null ... ");
            return;
        }
        if (!cutout.getBoundingRectLeft().isEmpty()) {
            Rect boundingRectLeft = cutout.getBoundingRectLeft();
            Guideline guideline6 = this.mGideLineVertical;
            if (guideline6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mGideLineVertical");
                guideline6 = null;
            }
            int i = boundingRectLeft.right;
            guideline6.setGuidelineBegin(i != 0 ? i : 398);
            Guideline guideline7 = this.mGideLineHorizon;
            if (guideline7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mGideLineHorizon");
            } else {
                guideline = guideline7;
            }
            int i2 = boundingRectLeft.bottom;
            guideline.setGuidelineBegin(i2 != 0 ? i2 : 728);
        } else if (!cutout.getBoundingRectRight().isEmpty()) {
            Rect boundingRectRight = cutout.getBoundingRectRight();
            Guideline guideline8 = this.mGideLineVertical;
            if (guideline8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mGideLineVertical");
                guideline8 = null;
            }
            int i3 = boundingRectRight.left;
            guideline8.setGuidelineBegin(i3 != 0 ? i3 : 810);
            Guideline guideline9 = this.mGideLineHorizon;
            if (guideline9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mGideLineHorizon");
            } else {
                guideline = guideline9;
            }
            int i4 = boundingRectRight.top;
            guideline.setGuidelineBegin(i4 != 0 ? i4 : 664);
        }
        Log.d("FlipLinkageClock", "boundingRectLeft: " + cutout.getBoundingRectLeft());
        Log.d("FlipLinkageClock", "boundingRectRight: " + cutout.getBoundingRectRight());
    }

    public final void updateGravityForIcons() {
        FlipLinkageTemplateId flipLinkageTemplateId = this.templateId;
        Direction direction = null;
        if (flipLinkageTemplateId == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templateId");
            flipLinkageTemplateId = null;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[flipLinkageTemplateId.ordinal()]) {
            case 1:
                ViewGroup.LayoutParams layoutParams = getIcons().getLayoutParams();
                Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                ((LinearLayout.LayoutParams) layoutParams).gravity = 3;
                ViewGroup.LayoutParams layoutParams2 = getFocusIcons().getLayoutParams();
                Intrinsics.checkNotNull(layoutParams2, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                ((LinearLayout.LayoutParams) layoutParams2).gravity = 3;
                break;
            case 2:
                ViewGroup.LayoutParams layoutParams3 = getIcons().getLayoutParams();
                Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) layoutParams3;
                ViewGroup.LayoutParams layoutParams5 = getFocusIcons().getLayoutParams();
                Intrinsics.checkNotNull(layoutParams5, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) layoutParams5;
                Direction direction2 = this.mDirection;
                if (direction2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDirection");
                    direction2 = null;
                }
                if (direction2 != Direction.CAMERA_CUTOUT_ON_THE_LEFT) {
                    Direction direction3 = this.mDirection;
                    if (direction3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mDirection");
                    } else {
                        direction = direction3;
                    }
                    if (direction == Direction.CAMERA_CUTOUT_ON_THE_RIGHT) {
                        layoutParams4.gravity = 3;
                        layoutParams6.gravity = 3;
                    }
                } else {
                    layoutParams4.gravity = 5;
                    layoutParams6.gravity = 5;
                }
                break;
            case 3:
                ViewGroup.LayoutParams layoutParams7 = getIcons().getLayoutParams();
                Intrinsics.checkNotNull(layoutParams7, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                ((LinearLayout.LayoutParams) layoutParams7).gravity = 1;
                ViewGroup.LayoutParams layoutParams8 = getFocusIcons().getLayoutParams();
                Intrinsics.checkNotNull(layoutParams8, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                ((LinearLayout.LayoutParams) layoutParams8).gravity = 17;
                break;
            case 4:
                ViewGroup.LayoutParams layoutParams9 = getIcons().getLayoutParams();
                Intrinsics.checkNotNull(layoutParams9, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                LinearLayout.LayoutParams layoutParams10 = (LinearLayout.LayoutParams) layoutParams9;
                ViewGroup.LayoutParams layoutParams11 = getFocusIcons().getLayoutParams();
                Intrinsics.checkNotNull(layoutParams11, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                LinearLayout.LayoutParams layoutParams12 = (LinearLayout.LayoutParams) layoutParams11;
                Direction direction4 = this.mDirection;
                if (direction4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDirection");
                    direction4 = null;
                }
                if (direction4 != Direction.CAMERA_CUTOUT_ON_THE_LEFT) {
                    Direction direction5 = this.mDirection;
                    if (direction5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mDirection");
                    } else {
                        direction = direction5;
                    }
                    if (direction == Direction.CAMERA_CUTOUT_ON_THE_RIGHT) {
                        layoutParams10.gravity = 3;
                        layoutParams12.gravity = 3;
                    }
                } else {
                    layoutParams10.gravity = 3;
                    layoutParams12.gravity = 3;
                }
                break;
            case 5:
            case 6:
            case 7:
                ViewGroup.LayoutParams layoutParams13 = getIcons().getLayoutParams();
                Intrinsics.checkNotNull(layoutParams13, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                ((LinearLayout.LayoutParams) layoutParams13).gravity = 1;
                ViewGroup.LayoutParams layoutParams14 = getFocusIcons().getLayoutParams();
                Intrinsics.checkNotNull(layoutParams14, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                ((LinearLayout.LayoutParams) layoutParams14).gravity = 17;
                break;
        }
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0056  */
    public void updateLayoutWidth() {
        FlipLinkageTemplateId flipLinkageTemplateId = this.templateId;
        FlipLinkageTemplateId flipLinkageTemplateId2 = null;
        if (flipLinkageTemplateId == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templateId");
            flipLinkageTemplateId = null;
        }
        if (flipLinkageTemplateId == FlipLinkageTemplateId.Doodle) {
            getMClockContainerProperties().constrainWidth(-2);
        } else {
            FlipLinkageTemplateId flipLinkageTemplateId3 = this.templateId;
            if (flipLinkageTemplateId3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("templateId");
                flipLinkageTemplateId3 = null;
            }
            if (flipLinkageTemplateId3 == FlipLinkageTemplateId.Pets) {
                getMClockContainerProperties().constrainWidth(-2);
            } else {
                FlipLinkageTemplateId flipLinkageTemplateId4 = this.templateId;
                if (flipLinkageTemplateId4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("templateId");
                    flipLinkageTemplateId4 = null;
                }
                if (flipLinkageTemplateId4 == FlipLinkageTemplateId.Classic) {
                    getMClockContainerProperties().constrainWidth(-2);
                } else {
                    FlipLinkageTemplateId flipLinkageTemplateId5 = this.templateId;
                    if (flipLinkageTemplateId5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("templateId");
                        flipLinkageTemplateId5 = null;
                    }
                    if (flipLinkageTemplateId5 == FlipLinkageTemplateId.Bubble) {
                        getMClockContainerProperties().constrainWidth(-2);
                    } else {
                        FlipLinkageTemplateId flipLinkageTemplateId6 = this.templateId;
                        if (flipLinkageTemplateId6 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("templateId");
                            flipLinkageTemplateId6 = null;
                        }
                        if (flipLinkageTemplateId6 == FlipLinkageTemplateId.Interactive_Pets) {
                            getMClockContainerProperties().constrainWidth(-2);
                        } else {
                            FlipLinkageTemplateId flipLinkageTemplateId7 = this.templateId;
                            if (flipLinkageTemplateId7 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("templateId");
                                flipLinkageTemplateId7 = null;
                            }
                            if (flipLinkageTemplateId7 == FlipLinkageTemplateId.AI_Pets) {
                                getMClockContainerProperties().constrainWidth(-2);
                            } else {
                                getMClockContainerProperties().constrainWidth(-1);
                            }
                        }
                    }
                }
            }
        }
        FlipLinkageTemplateId flipLinkageTemplateId8 = this.templateId;
        if (flipLinkageTemplateId8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templateId");
        } else {
            flipLinkageTemplateId2 = flipLinkageTemplateId8;
        }
        if (flipLinkageTemplateId2 == FlipLinkageTemplateId.SPLICING) {
            getClockView().getLayoutParams().width = -1;
        } else {
            getClockView().getLayoutParams().width = -2;
        }
    }

    public void updateMargin() {
        AODTinyMiuiClockView clockView = getClockView();
        FlipLinkageTemplateId flipLinkageTemplateId = this.templateId;
        Direction direction = null;
        if (flipLinkageTemplateId == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templateId");
            flipLinkageTemplateId = null;
        }
        Direction direction2 = this.mDirection;
        if (direction2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mDirection");
        } else {
            direction = direction2;
        }
        Point margin = clockView.getMargin(flipLinkageTemplateId, direction);
        getMClockInfoContainerProperties().margin(1, margin.x);
        getMClockInfoContainerProperties().margin(2, margin.y);
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        FlipLinkageStyleClockView flipLinkageStyleClockView = this.mClockViewContainer;
        if (flipLinkageStyleClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            flipLinkageStyleClockView = null;
        }
        flipLinkageStyleClockView.updateTime();
    }
}
