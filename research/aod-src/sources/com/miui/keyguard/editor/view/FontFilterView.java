package com.miui.keyguard.editor.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.clock.utils.DeviceConfig;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TransitionKt;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.preset.FilterColor;
import com.miui.keyguard.editor.data.preset.FilterGroup;
import com.miui.keyguard.editor.data.preset.FilterTypeSelectInfo;
import com.miui.keyguard.editor.data.preset.FontColorInfo;
import com.miui.keyguard.editor.data.preset.FontFilter;
import com.miui.keyguard.editor.data.preset.FontFilterData;
import com.miui.keyguard.editor.data.preset.FontFilterInfo;
import com.miui.keyguard.editor.data.preset.FontFilterSelectInfo;
import com.miui.keyguard.editor.edit.color.DefaultColorProgressDrawable;
import com.miui.keyguard.editor.edit.view.EditorDialogHorizontalRecyclerView;
import com.miui.keyguard.editor.homepage.view.adapter.FontFilterColorAdapter;
import com.miui.keyguard.editor.homepage.view.adapter.FontFilterTitleAdapter;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.keyguard.editor.viewmodel.IntWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import miuix.androidbasewidget.widget.SeekBar;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.slidingwidget.widget.SlidingSwitch;
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
/* JADX INFO: compiled from: FontFilterView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class FontFilterView extends FrameLayout {
    public List colorAdapterList;
    public List colorItemGroupList;
    public final int colorLightContainerHeight;
    public List colorLightSeekBarContainerList;
    public List colorLightSeekBarList;
    public final FontFilterView$colorLightUpdateListener$1 colorLightUpdateListener;
    public List colorList;
    public ColorModificationTarget colorModificationTarget;
    public float currentGlassTransparency;
    public FontFilterSelectInfo currentSelectFilter;
    public final FontFilterViewStatus currentStatus;
    public AutoBottomSheet customColorBottomSheet;
    public List customColorViewList;
    public int defaultTargetIndex;
    public int disableTextColor;
    public RelativeLayout doodleContainer;
    public TextView doodleLabel;
    public SlidingSwitch doodleSwitch;
    public CompoundButton.OnCheckedChangeListener doodleSwitchListener;
    public FontFilterTitleAdapter filterTypeAdapter;
    public SmoothAlignHorizontalListView filterTypeList;
    public TextView filterTypeTitle;
    public LinearLayout fontFilterContainer;
    public FontFilter fontFilterData;
    public int hslPagerHeight;
    public MiuiClockViewSafeHolder miuiClockViewSafeHolder;
    public ArrayList objectResId;
    public int objectTitleColorRes;
    public Function1 onGlassTransparencyInit;
    public SeekBar seekBar;
    public RelativeLayout splitContainer;
    public SlidingSwitch splitSwitch;
    public CompoundButton.OnCheckedChangeListener splitSwitchListener;
    public TextView splitTitle;
    public List targetIndexList;
    public List targetTitleList;
    public View transparencySlider;
    public EditFragmentViewModel viewModel;

    /* JADX INFO: compiled from: FontFilterView.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ColorModificationTarget.values().length];
            try {
                iArr[ColorModificationTarget.BOTH_PARTS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ColorModificationTarget.PRIMARY_PART.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ColorModificationTarget.SECONDARY_PART.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FontFilterView(@NotNull Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FontFilterView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.fontFilterViewStyle);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FontFilterView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.colorItemGroupList = CollectionsKt.mutableListOf(null, null);
        this.targetTitleList = CollectionsKt.mutableListOf(null, null);
        this.colorList = CollectionsKt.mutableListOf(null, null);
        this.colorLightSeekBarContainerList = CollectionsKt.mutableListOf(null, null);
        this.colorLightSeekBarList = CollectionsKt.mutableListOf(null, null);
        this.splitSwitchListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                FontFilterView.splitSwitchListener$lambda$0(this.f$0, compoundButton, z);
            }
        };
        this.colorModificationTarget = ColorModificationTarget.BOTH_PARTS;
        this.colorLightContainerHeight = context.getResources().getDimensionPixelOffset(R.dimen.kg_color_value_editor_seekbar_height) + context.getResources().getDimensionPixelSize(R.dimen.kg_font_color_light_top_margin);
        this.currentStatus = new FontFilterViewStatus(0, false, false, false, 15, null);
        this.objectTitleColorRes = -16777216;
        this.disableTextColor = -7829368;
        this.objectResId = new ArrayList();
        this.colorAdapterList = CollectionsKt.mutableListOf(null, null);
        this.customColorViewList = CollectionsKt.mutableListOf(null, null);
        this.currentGlassTransparency = 0.15f;
        initView();
        initStyle(attributeSet, i);
        this.colorLightUpdateListener = new FontFilterView$colorLightUpdateListener$1(this);
    }

    public static final void dismissLightSeekBar$lambda$25$lambda$24(LinearLayout linearLayout, AnimState animState, AnimState animState2, AnimConfig animConfig) {
        Folme.use((View) linearLayout).state().setTo(animState).to(animState2, animConfig);
    }

    private final FontFilterData getPrimaryFontFilter() {
        FontFilterData primaryFontFilter;
        EditFragmentViewModel editFragmentViewModel = this.viewModel;
        return (editFragmentViewModel == null || (primaryFontFilter = editFragmentViewModel.getPrimaryFontFilter()) == null) ? new FontFilterData(0, 0, 0, false, 8, null) : primaryFontFilter;
    }

    private final FontFilterData getSecondaryFontFilter() {
        FontFilterData secondaryFontFilter;
        EditFragmentViewModel editFragmentViewModel = this.viewModel;
        return (editFragmentViewModel == null || (secondaryFontFilter = editFragmentViewModel.getSecondaryFontFilter()) == null) ? new FontFilterData(0, 0, 0, false, 8, null) : secondaryFontFilter;
    }

    public static final void initBaseConfig$lambda$12(FontFilterView fontFilterView, View view) {
        SlidingSwitch slidingSwitch = fontFilterView.splitSwitch;
        if (slidingSwitch != null) {
            slidingSwitch.performClick();
        }
    }

    public static final void initColorList$lambda$18$lambda$17(EditorDialogHorizontalRecyclerView editorDialogHorizontalRecyclerView, FontFilterView fontFilterView, int i) {
        RecyclerView.LayoutManager layoutManager = editorDialogHorizontalRecyclerView.getLayoutManager();
        FontFilterSelectInfo fontFilterSelectInfo = null;
        LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
        if (linearLayoutManager != null) {
            FontFilterSelectInfo fontFilterSelectInfo2 = fontFilterView.currentSelectFilter;
            if (fontFilterSelectInfo2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            } else {
                fontFilterSelectInfo = fontFilterSelectInfo2;
            }
            linearLayoutManager.scrollToPositionWithOffset(fontFilterSelectInfo.getCurrentColorIndex(i), 0);
        }
    }

    public static final void initDoodleSlidingButton$lambda$10(FontFilterView fontFilterView, View view) {
        SlidingSwitch slidingSwitch = fontFilterView.doodleSwitch;
        if (slidingSwitch != null) {
            slidingSwitch.performClick();
        }
    }

    public static final void initView$lambda$1(View view) {
    }

    public static final void initView$lambda$2(View view) {
    }

    public static final void initView$lambda$3(View view) {
    }

    public static final void initView$lambda$4(View view) {
    }

    public static final void initView$lambda$5(View view) {
    }

    private final void setDoodleContainerVisible(int i) {
        RelativeLayout relativeLayout = this.doodleContainer;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(i);
        }
    }

    private final void setDoodleSwitchCheckedQuiet(boolean z) {
        SlidingSwitch slidingSwitch = this.doodleSwitch;
        if (slidingSwitch != null) {
            slidingSwitch.setOnCheckedChangeListener(null);
        }
        SlidingSwitch slidingSwitch2 = this.doodleSwitch;
        if (slidingSwitch2 != null) {
            slidingSwitch2.setChecked(z);
        }
        SlidingSwitch slidingSwitch3 = this.doodleSwitch;
        if (slidingSwitch3 != null) {
            slidingSwitch3.setOnCheckedChangeListener(this.doodleSwitchListener);
        }
    }

    public static /* synthetic */ void setFontFilterData$default(FontFilterView fontFilterView, FontFilter fontFilter, FilterTypeSelectInfo[] filterTypeSelectInfoArr, MiuiClockViewSafeHolder miuiClockViewSafeHolder, boolean z, boolean z2, float f, EditFragmentViewModel editFragmentViewModel, ColorModificationTarget colorModificationTarget, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        if ((i & 16) != 0) {
            z2 = false;
        }
        if ((i & 32) != 0) {
            f = 0.15f;
        }
        fontFilterView.setFontFilterData(fontFilter, filterTypeSelectInfoArr, miuiClockViewSafeHolder, z, z2, f, editFragmentViewModel, colorModificationTarget);
    }

    private final void setSplitContainerVisible(int i) {
        RelativeLayout relativeLayout = this.splitContainer;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(i);
        }
    }

    private final void setSplitSwitchQuiet(boolean z) {
        SlidingSwitch slidingSwitch = this.splitSwitch;
        if (slidingSwitch != null) {
            slidingSwitch.setOnCheckedChangeListener(null);
        }
        SlidingSwitch slidingSwitch2 = this.splitSwitch;
        if (slidingSwitch2 != null) {
            slidingSwitch2.setChecked(z);
        }
        SlidingSwitch slidingSwitch3 = this.splitSwitch;
        if (slidingSwitch3 != null) {
            slidingSwitch3.setOnCheckedChangeListener(this.splitSwitchListener);
        }
    }

    public static final void showCustomColorBottomSheet$lambda$21$lambda$20(FontFilterView fontFilterView, View view) {
        AutoBottomSheet autoBottomSheet = fontFilterView.customColorBottomSheet;
        if (autoBottomSheet != null) {
            autoBottomSheet.dismiss();
        }
    }

    public static final void showLightSeekBar$lambda$28$lambda$27(LinearLayout linearLayout, AnimState animState, AnimState animState2, AnimConfig animConfig) {
        Folme.use((View) linearLayout).state().setTo(animState).to(animState2, animConfig);
    }

    public static final void splitSwitchListener$lambda$0(FontFilterView fontFilterView, CompoundButton compoundButton, boolean z) {
        EditFragmentViewModel editFragmentViewModel = fontFilterView.viewModel;
        if (editFragmentViewModel != null) {
            EditFragmentViewModel.updateDifferentColorStatus$default(editFragmentViewModel, z ? 1 : 2, false, 2, null);
        }
    }

    public final FontColorSelectItemCallback createColorSelectCallback(final int i) {
        return new FontColorSelectItemCallback() { // from class: com.miui.keyguard.editor.view.FontFilterView.createColorSelectCallback.1
            @Override // com.miui.keyguard.editor.view.FontColorSelectItemCallback
            public void onClickItem(int i2, ColorSelectView colorSelectView) {
                FontFilterView.this.handleColorClick(i, i2, colorSelectView);
            }

            @Override // com.miui.keyguard.editor.view.FontColorSelectItemCallback
            public void onSelectItem(int i2, ColorSelectView colorSelectView) {
                FontFilterView.this.handleColorSelect(i, i2, colorSelectView);
            }
        };
    }

    public final FontFilterSelectItemCallback createFilterSelectCallback() {
        return new FontFilterSelectItemCallback() { // from class: com.miui.keyguard.editor.view.FontFilterView.createFilterSelectCallback.1
            @Override // com.miui.keyguard.editor.view.FontFilterSelectItemCallback
            public void onSelectItem(int i, View view) {
                int i2 = FontFilterView.this.defaultTargetIndex;
                FontFilterSelectInfo fontFilterSelectInfo = FontFilterView.this.currentSelectFilter;
                FontFilterSelectInfo fontFilterSelectInfo2 = null;
                if (fontFilterSelectInfo == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                    fontFilterSelectInfo = null;
                }
                if (i != fontFilterSelectInfo.getCurrentFilterTypeIndex()) {
                    FontFilter fontFilter = FontFilterView.this.fontFilterData;
                    if (fontFilter == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("fontFilterData");
                        fontFilter = null;
                    }
                    FilterGroup[] currentFilterData = fontFilter.getCurrentFilterData(i2);
                    FontFilterSelectInfo fontFilterSelectInfo3 = FontFilterView.this.currentSelectFilter;
                    if (fontFilterSelectInfo3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                        fontFilterSelectInfo3 = null;
                    }
                    fontFilterSelectInfo3.setCurrentFilterTypeIndex(i);
                    FontFilterSelectInfo fontFilterSelectInfo4 = FontFilterView.this.currentSelectFilter;
                    if (fontFilterSelectInfo4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                        fontFilterSelectInfo4 = null;
                    }
                    fontFilterSelectInfo4.setCurrentFilterId(currentFilterData[i].getFilterId());
                    EditFragmentViewModel editFragmentViewModel = FontFilterView.this.viewModel;
                    if (editFragmentViewModel != null) {
                        editFragmentViewModel.setOldPrimaryFontFilter(null);
                    }
                    EditFragmentViewModel editFragmentViewModel2 = FontFilterView.this.viewModel;
                    if (editFragmentViewModel2 != null) {
                        editFragmentViewModel2.setOldSecondaryFontFilter(null);
                    }
                    FontFilterView fontFilterView = FontFilterView.this;
                    FontFilterSelectInfo fontFilterSelectInfo5 = fontFilterView.currentSelectFilter;
                    if (fontFilterSelectInfo5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                        fontFilterSelectInfo5 = null;
                    }
                    int currentFilterId = fontFilterSelectInfo5.getCurrentFilterId(i2);
                    FontFilterSelectInfo fontFilterSelectInfo6 = FontFilterView.this.currentSelectFilter;
                    if (fontFilterSelectInfo6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                        fontFilterSelectInfo6 = null;
                    }
                    int currentColorId = fontFilterSelectInfo6.getCurrentColorId(i2);
                    FontFilterSelectInfo fontFilterSelectInfo7 = FontFilterView.this.currentSelectFilter;
                    if (fontFilterSelectInfo7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                        fontFilterSelectInfo7 = null;
                    }
                    fontFilterView.updateFontFilter(i2, new FontFilterData(currentFilterId, currentColorId, fontFilterSelectInfo7.getCurrentARGBColor(i2), false, 8, null));
                    if (FontFilterView.this.colorModificationTarget == ColorModificationTarget.BOTH_PARTS) {
                        FontFilterView.this.updateAnotherTarget(i2);
                    }
                    FontFilterSelectInfo fontFilterSelectInfo8 = FontFilterView.this.currentSelectFilter;
                    if (fontFilterSelectInfo8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                        fontFilterSelectInfo8 = null;
                    }
                    FilterColor currentFilterColor = fontFilterSelectInfo8.getCurrentFilterColor(i2);
                    if (currentFilterColor == null || !currentFilterColor.getMutable()) {
                        FontFilterView.this.dismissLightSeekBar(i2);
                    }
                    FontFilterSelectInfo fontFilterSelectInfo9 = FontFilterView.this.currentSelectFilter;
                    if (fontFilterSelectInfo9 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                    } else {
                        fontFilterSelectInfo2 = fontFilterSelectInfo9;
                    }
                    if (fontFilterSelectInfo2.getCurrentFilterId(0) != 5) {
                        View transparencySlider = FontFilterView.this.getTransparencySlider();
                        if (transparencySlider != null) {
                            transparencySlider.setVisibility(8);
                            return;
                        }
                        return;
                    }
                    View transparencySlider2 = FontFilterView.this.getTransparencySlider();
                    if (transparencySlider2 != null) {
                        transparencySlider2.setVisibility(0);
                    }
                    float f = FontFilterView.this.currentGlassTransparency == 0.0f ? 0.15f : FontFilterView.this.currentGlassTransparency;
                    SeekBar seekBar = FontFilterView.this.getSeekBar();
                    if (seekBar != null) {
                        seekBar.setProgress(RangesKt.coerceIn((int) (100 * f), 0, 100));
                    }
                    Function1 function1 = FontFilterView.this.onGlassTransparencyInit;
                    if (function1 != null) {
                        function1.invoke(Float.valueOf(f));
                    }
                }
            }
        };
    }

    public final void dismissLightSeekBar(int i) {
        final LinearLayout linearLayout = (LinearLayout) this.colorLightSeekBarContainerList.get(i);
        int i2 = this.colorLightContainerHeight;
        if ((linearLayout == null || linearLayout.getVisibility() != 0) && this.currentStatus.getColorLightAnimStatus() == 0) {
            return;
        }
        if (this.currentStatus.getColorLightAnimStatus() != 0) {
            Folme.use((View) this).state().cancel();
            Folme.use((View) this).visible().cancel();
            Folme.useAt(linearLayout).state().cancel();
            Folme.useAt(linearLayout).visible().cancel();
            this.currentStatus.setColorLightAnimStatus(0);
        }
        AnimState animState = new AnimState("dismiss_alpha_start");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, 1.0f, new long[0]);
        AnimState animStateAdd2 = new AnimState("dismiss_alpha_end").add(viewProperty, 0.0f, new long[0]);
        AnimState animState2 = new AnimState("dismiss_height_start");
        ViewProperty viewProperty2 = ViewProperty.HEIGHT;
        final AnimState animStateAdd3 = animState2.add(viewProperty2, i2, new long[0]);
        final AnimState animStateAdd4 = new AnimState("dismiss_height_end").add(viewProperty2, 0.0f, new long[0]);
        AnimConfig animConfig = TransitionKt.getAnimConfig(1.0f, 0.25f, new TransitionListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$dismissLightSeekBar$alphaAnimConfig$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                FontFilterViewStatus fontFilterViewStatus = this.this$0.currentStatus;
                fontFilterViewStatus.setColorLightAnimStatus(fontFilterViewStatus.getColorLightAnimStatus() + 1);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                FontFilterViewStatus fontFilterViewStatus = this.this$0.currentStatus;
                fontFilterViewStatus.setColorLightAnimStatus(fontFilterViewStatus.getColorLightAnimStatus() - 1);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                FontFilterViewStatus fontFilterViewStatus = this.this$0.currentStatus;
                fontFilterViewStatus.setColorLightAnimStatus(fontFilterViewStatus.getColorLightAnimStatus() - 1);
                LinearLayout linearLayout2 = linearLayout;
                if (linearLayout2 != null) {
                    linearLayout2.setVisibility(4);
                }
            }
        });
        final AnimConfig animConfig2 = TransitionKt.getAnimConfig(0.95f, 0.3f, new TransitionListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$dismissLightSeekBar$heightAnimConfig$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                FontFilterViewStatus fontFilterViewStatus = this.this$0.currentStatus;
                fontFilterViewStatus.setColorLightAnimStatus(fontFilterViewStatus.getColorLightAnimStatus() + 1);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                FontFilterViewStatus fontFilterViewStatus = this.this$0.currentStatus;
                fontFilterViewStatus.setColorLightAnimStatus(fontFilterViewStatus.getColorLightAnimStatus() - 1);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                FontFilterViewStatus fontFilterViewStatus = this.this$0.currentStatus;
                fontFilterViewStatus.setColorLightAnimStatus(fontFilterViewStatus.getColorLightAnimStatus() - 1);
                LinearLayout linearLayout2 = linearLayout;
                if (linearLayout2 != null) {
                    linearLayout2.setVisibility(4);
                }
            }
        });
        if (linearLayout != null) {
            Folme.use((View) linearLayout).state().setTo(animStateAdd).to(animStateAdd2, animConfig);
            postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.view.FontFilterView$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    FontFilterView.dismissLightSeekBar$lambda$25$lambda$24(linearLayout, animStateAdd3, animStateAdd4, animConfig2);
                }
            }, 200L);
        }
    }

    public final void dismissSecondColorGroup() {
        final LinearLayout linearLayout = (LinearLayout) this.colorItemGroupList.get(1);
        if (linearLayout == null || linearLayout.getVisibility() != 0) {
            return;
        }
        SeekBar seekBar = (SeekBar) this.colorLightSeekBarList.get(1);
        if (seekBar != null) {
            seekBar.setEnabled(false);
        }
        AnimState animState = new AnimState("dismiss_start");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        Folme.useAt(linearLayout).state().setTo(animState.add(viewProperty, 1.0f, new long[0])).to(new AnimState("dismiss_end").add(viewProperty, 0.0f, new long[0]), TransitionKt.getAnimConfig(0.95f, 0.3f, new TransitionListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$dismissSecondColorGroup$config$1
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                LinearLayout linearLayout2 = linearLayout;
                if (linearLayout2 != null) {
                    linearLayout2.setVisibility(8);
                }
            }
        }));
    }

    @Nullable
    public final SmoothAlignHorizontalListView getFilterTypeList() {
        return this.filterTypeList;
    }

    public final int getFontFilterIndex(int i) {
        FontFilter fontFilter = this.fontFilterData;
        if (fontFilter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fontFilterData");
            fontFilter = null;
        }
        FilterGroup[] filterGroupArr = fontFilter.getFilterGroups()[this.defaultTargetIndex];
        int length = filterGroupArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (filterGroupArr[i2].getFilterId() == i) {
                return i2;
            }
        }
        return -1;
    }

    public final int getObjectTitleColorRes() {
        return this.objectTitleColorRes;
    }

    @Nullable
    public final SeekBar getSeekBar() {
        return this.seekBar;
    }

    @Nullable
    public final View getTransparencySlider() {
        return this.transparencySlider;
    }

    public final void handleColorClick(int i, int i2, ColorSelectView colorSelectView) {
        switchToTargetIndex(i);
        FontFilterSelectInfo fontFilterSelectInfo = this.currentSelectFilter;
        FontFilterSelectInfo fontFilterSelectInfo2 = null;
        if (fontFilterSelectInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo = null;
        }
        FilterColor[] currentColorData = fontFilterSelectInfo.getCurrentColorData(i);
        if (i2 >= currentColorData.length) {
            Log.d("FontFilterView", "select color error: " + currentColorData.length + " " + i2);
            return;
        }
        FontFilterSelectInfo fontFilterSelectInfo3 = this.currentSelectFilter;
        if (fontFilterSelectInfo3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo3 = null;
        }
        fontFilterSelectInfo3.setCurrentFilterColor(i, currentColorData[i2]);
        FontFilterSelectInfo fontFilterSelectInfo4 = this.currentSelectFilter;
        if (fontFilterSelectInfo4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo4 = null;
        }
        fontFilterSelectInfo4.setCurrentColor(i, i2, currentColorData[i2].getColorId());
        FontFilterSelectInfo fontFilterSelectInfo5 = this.currentSelectFilter;
        if (fontFilterSelectInfo5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo5 = null;
        }
        FilterColor currentFilterColor = fontFilterSelectInfo5.getCurrentFilterColor(i);
        Integer numValueOf = currentFilterColor != null ? Integer.valueOf(currentFilterColor.getColorType()) : null;
        if (numValueOf != null && numValueOf.intValue() == 0) {
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == 2) {
            showCustomColorBottomSheet(i);
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == 1) {
            FontFilterSelectInfo fontFilterSelectInfo6 = this.currentSelectFilter;
            if (fontFilterSelectInfo6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo6 = null;
            }
            FilterColor currentFilterColor2 = fontFilterSelectInfo6.getCurrentFilterColor(i);
            Intrinsics.checkNotNull(currentFilterColor2);
            int color = currentFilterColor2.getColor();
            FontFilterSelectInfo fontFilterSelectInfo7 = this.currentSelectFilter;
            if (fontFilterSelectInfo7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            } else {
                fontFilterSelectInfo2 = fontFilterSelectInfo7;
            }
            ColorUtils.colorToHSL(color, fontFilterSelectInfo2.getCurrentHslColor(i));
        }
    }

    public final void handleColorSelect(int i, int i2, ColorSelectView colorSelectView) {
        switchToTargetIndex(i);
        FontFilterSelectInfo fontFilterSelectInfo = this.currentSelectFilter;
        FontFilterSelectInfo fontFilterSelectInfo2 = null;
        if (fontFilterSelectInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo = null;
        }
        FilterColor[] currentColorData = fontFilterSelectInfo.getCurrentColorData(i);
        FontFilterSelectInfo fontFilterSelectInfo3 = this.currentSelectFilter;
        if (fontFilterSelectInfo3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo3 = null;
        }
        fontFilterSelectInfo3.setCurrentFilterColor(i, currentColorData[i2]);
        FontFilterSelectInfo fontFilterSelectInfo4 = this.currentSelectFilter;
        if (fontFilterSelectInfo4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo4 = null;
        }
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        fontFilterSelectInfo4.addCurrentModifyColor(i, context, currentColorData[i2]);
        FontFilterSelectInfo fontFilterSelectInfo5 = this.currentSelectFilter;
        if (fontFilterSelectInfo5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo5 = null;
        }
        int currentColorId = fontFilterSelectInfo5.getCurrentColorId(i);
        FontFilterSelectInfo fontFilterSelectInfo6 = this.currentSelectFilter;
        if (fontFilterSelectInfo6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo6 = null;
        }
        int currentColorIndex = fontFilterSelectInfo6.getCurrentColorIndex(i);
        FontFilterSelectInfo fontFilterSelectInfo7 = this.currentSelectFilter;
        if (fontFilterSelectInfo7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo7 = null;
        }
        float[] currentHslColor = fontFilterSelectInfo7.getCurrentHslColor(i);
        float[] fArr = new float[3];
        if (currentColorId != 13) {
            ColorUtils.colorToHSL(FontFilterColorAdapter.Companion.getFontFilterColorArray()[currentColorIndex].intValue(), fArr);
            FontFilterSelectInfo fontFilterSelectInfo8 = this.currentSelectFilter;
            if (fontFilterSelectInfo8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo8 = null;
            }
            FilterColor currentFilterColor = fontFilterSelectInfo8.getCurrentFilterColor(i);
            Intrinsics.checkNotNull(currentFilterColor);
            int color = currentFilterColor.getColor();
            if (color == -16777216) {
                currentHslColor[0] = fArr[0];
                currentHslColor[1] = fArr[1];
                currentHslColor[2] = 0.0f;
            } else if (color != -1) {
                FontFilterSelectInfo fontFilterSelectInfo9 = this.currentSelectFilter;
                if (fontFilterSelectInfo9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                } else {
                    fontFilterSelectInfo2 = fontFilterSelectInfo9;
                }
                FilterColor currentFilterColor2 = fontFilterSelectInfo2.getCurrentFilterColor(i);
                Intrinsics.checkNotNull(currentFilterColor2);
                ColorUtils.colorToHSL(currentFilterColor2.getColor(), currentHslColor);
            } else {
                currentHslColor[0] = fArr[0];
                currentHslColor[1] = fArr[1];
                currentHslColor[2] = 1.0f;
            }
        }
        updateColorLight(i);
        if (shouldUpdateColor(i)) {
            markUserModifiedColor(i);
            onSelectColorChange(i);
        }
    }

    public final void initBaseConfig(ColorModificationTarget colorModificationTarget, FontFilter fontFilter) {
        IntWrapper differentColorStatus;
        setColorModificationTarget(colorModificationTarget, fontFilter.getTargetTitleResId());
        String string = getContext().getString(R.string.kg_split_switch_label);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        setSplitContainer(string, this.splitSwitchListener, fontFilter.getSupportDifferentColor() ? 0 : 8);
        EditFragmentViewModel editFragmentViewModel = this.viewModel;
        updateDifferentColor((editFragmentViewModel == null || (differentColorStatus = editFragmentViewModel.getDifferentColorStatus()) == null) ? 2 : differentColorStatus.getIntValue());
        initTargetTitles(fontFilter);
        setSecondaryTargetTitleVisibility();
        setTargetEnable(0, fontFilter.getTargetMutable()[0].booleanValue());
        setTargetEnable(1, fontFilter.getTargetMutable()[1].booleanValue());
        RelativeLayout relativeLayout = this.splitContainer;
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FontFilterView.initBaseConfig$lambda$12(this.f$0, view);
                }
            });
        }
    }

    public final void initColorLightSeekBar(final int i) {
        SeekBar seekBar = (SeekBar) this.colorLightSeekBarList.get(i);
        if (seekBar != null) {
            seekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() { // from class: com.miui.keyguard.editor.view.FontFilterView.initColorLightSeekBar.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(android.widget.SeekBar seekBar2, int i2, boolean z) {
                    FontFilterView.this.colorLightUpdateListener.onColorLightChanged(i, i2, z);
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(android.widget.SeekBar seekBar2) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(android.widget.SeekBar seekBar2) {
                }
            });
        }
    }

    public final void initColorList(final int i) {
        List list = this.colorAdapterList;
        FontFilterSelectInfo fontFilterSelectInfo = this.currentSelectFilter;
        if (fontFilterSelectInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo = null;
        }
        FontFilterViewStatus fontFilterViewStatus = this.currentStatus;
        FontColorSelectItemCallback fontColorSelectItemCallbackCreateColorSelectCallback = createColorSelectCallback(i);
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        list.set(i, new FontFilterColorAdapter(i, fontFilterSelectInfo, fontFilterViewStatus, fontColorSelectItemCallbackCreateColorSelectCallback, context));
        final EditorDialogHorizontalRecyclerView editorDialogHorizontalRecyclerView = (EditorDialogHorizontalRecyclerView) this.colorList.get(i);
        if (editorDialogHorizontalRecyclerView != null) {
            if (editorDialogHorizontalRecyclerView.getLayoutManager() == null) {
                editorDialogHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(editorDialogHorizontalRecyclerView.getContext(), 0, false));
            }
            editorDialogHorizontalRecyclerView.setAdapter((RecyclerView.Adapter) this.colorAdapterList.get(i));
            editorDialogHorizontalRecyclerView.post(new Runnable() { // from class: com.miui.keyguard.editor.view.FontFilterView$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    FontFilterView.initColorList$lambda$18$lambda$17(editorDialogHorizontalRecyclerView, this, i);
                }
            });
        }
        initColorLightSeekBar(i);
    }

    public final void initColorLists() {
        List list = this.targetIndexList;
        Intrinsics.checkNotNull(list);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            initColorList(((Number) it.next()).intValue());
        }
    }

    public final void initColorSelectionInfo() {
        initPrimaryColorSelectionInfo();
        FontFilter fontFilter = this.fontFilterData;
        if (fontFilter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fontFilterData");
            fontFilter = null;
        }
        if (fontFilter.getFilterGroups().length > 1) {
            initSecondaryColorSelectionInfo(false);
        }
    }

    public final void initDoodleSlidingButton() {
        SlidingSwitch slidingSwitch = new SlidingSwitch(getContext());
        this.doodleSwitch = slidingSwitch;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(21);
        slidingSwitch.setLayoutParams(layoutParams);
        slidingSwitch.setImportantForAccessibility(2);
        RelativeLayout relativeLayout = this.doodleContainer;
        if (relativeLayout != null) {
            relativeLayout.addView(slidingSwitch);
        }
        TextView textView = this.doodleLabel;
        if (textView != null) {
            textView.setImportantForAccessibility(2);
        }
        RelativeLayout relativeLayout2 = this.doodleContainer;
        if (relativeLayout2 != null) {
            relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FontFilterView.initDoodleSlidingButton$lambda$10(this.f$0, view);
                }
            });
        }
        updateDoodleContainerContentDescription();
    }

    public final void initFilterAndColorData(FilterTypeSelectInfo[] filterTypeSelectInfoArr, boolean z, boolean z2) {
        FontFilterData primaryFontFilter = getPrimaryFontFilter();
        FontFilterData secondaryFontFilter = getSecondaryFontFilter();
        filterTypeSelectInfoArr[0].setSelectColorId(primaryFontFilter.getColorId());
        filterTypeSelectInfoArr[1].setSelectColorId(secondaryFontFilter.getColorId());
        Log.d("FontFilterView", "initFilterAndColorData: primaryFontFilter=" + primaryFontFilter + ", secondaryFontFilter=" + secondaryFontFilter);
        int i = this.defaultTargetIndex;
        FontColorInfo[] fontColorInfoArr = {new FontColorInfo(0, primaryFontFilter.getColorId(), null, null, 12, null), new FontColorInfo(1, secondaryFontFilter.getColorId(), null, null, 12, null)};
        ArrayList arrayList = new ArrayList();
        FontFilterInfo fontFilterInfo = new FontFilterInfo(primaryFontFilter.getFilterId(), getFontFilterIndex(primaryFontFilter.getFilterId()));
        MiuiClockViewSafeHolder miuiClockViewSafeHolder = this.miuiClockViewSafeHolder;
        this.currentSelectFilter = new FontFilterSelectInfo(i, fontColorInfoArr, arrayList, filterTypeSelectInfoArr, fontFilterInfo, null, miuiClockViewSafeHolder != null ? miuiClockViewSafeHolder.getTemplateId() : null, 32, null);
        initColorSelectionInfo();
        initFilterTypeList(z, z2);
        initColorLists();
    }

    public final void initFilterTypeList(boolean z, boolean z2) {
        FontFilterSelectInfo fontFilterSelectInfo = this.currentSelectFilter;
        FontFilterSelectInfo fontFilterSelectInfo2 = null;
        if (fontFilterSelectInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo = null;
        }
        List currentFilterTitleResInfo = fontFilterSelectInfo.getCurrentFilterTitleResInfo();
        if (currentFilterTitleResInfo.isEmpty() || !DeviceConfig.supportBackgroundBlur(getContext())) {
            TextView textView = this.filterTypeTitle;
            if (textView != null) {
                textView.setVisibility(8);
            }
            SmoothAlignHorizontalListView smoothAlignHorizontalListView = this.filterTypeList;
            if (smoothAlignHorizontalListView != null) {
                smoothAlignHorizontalListView.setVisibility(8);
                return;
            }
            return;
        }
        TextView textView2 = this.filterTypeTitle;
        if (textView2 != null) {
            textView2.setVisibility(0);
        }
        SmoothAlignHorizontalListView smoothAlignHorizontalListView2 = this.filterTypeList;
        if (smoothAlignHorizontalListView2 != null) {
            smoothAlignHorizontalListView2.setVisibility(0);
        }
        FontFilterSelectInfo fontFilterSelectInfo3 = this.currentSelectFilter;
        if (fontFilterSelectInfo3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo3 = null;
        }
        if (fontFilterSelectInfo3.getCurrentFilterId(0) == 5) {
            View view = this.transparencySlider;
            if (view != null) {
                view.setVisibility(0);
            }
            SeekBar seekBar = this.seekBar;
            if (seekBar != null) {
                seekBar.setProgress(RangesKt.coerceIn((int) (this.currentGlassTransparency * 100), 0, 100));
            }
        } else {
            View view2 = this.transparencySlider;
            if (view2 != null) {
                view2.setVisibility(8);
            }
        }
        SeekBar seekBar2 = this.seekBar;
        if (seekBar2 != null) {
            seekBar2.setContentDescription(getContext().getString(R.string.kg_allinone_transparency_name));
        }
        FontFilterSelectInfo fontFilterSelectInfo4 = this.currentSelectFilter;
        if (fontFilterSelectInfo4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
        } else {
            fontFilterSelectInfo2 = fontFilterSelectInfo4;
        }
        this.filterTypeAdapter = new FontFilterTitleAdapter(currentFilterTitleResInfo, fontFilterSelectInfo2.getCurrentFilterTypeIndex(), createFilterSelectCallback(), this.currentStatus, z, z2);
        SmoothAlignHorizontalListView smoothAlignHorizontalListView3 = this.filterTypeList;
        if (smoothAlignHorizontalListView3 != null) {
            smoothAlignHorizontalListView3.setLayoutManager(new LinearLayoutManager(smoothAlignHorizontalListView3.getContext(), 0, false));
            smoothAlignHorizontalListView3.setAdapter(this.filterTypeAdapter);
        }
        FontFilterTitleAdapter fontFilterTitleAdapter = this.filterTypeAdapter;
        if (fontFilterTitleAdapter != null) {
            fontFilterTitleAdapter.notifyDataSetChanged();
        }
    }

    public final void initPrimaryColorSelectionInfo() {
        FontFilterData primaryFontFilter;
        FontFilter fontFilter = this.fontFilterData;
        if (fontFilter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fontFilterData");
            fontFilter = null;
        }
        FilterGroup[] filterGroupArr = fontFilter.getFilterGroups()[0];
        int length = filterGroupArr.length;
        for (int i = 0; i < length; i++) {
            FilterGroup filterGroup = filterGroupArr[i];
            FontFilterSelectInfo fontFilterSelectInfo = this.currentSelectFilter;
            if (fontFilterSelectInfo == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo = null;
            }
            List filterIdList = fontFilterSelectInfo.getFilterIdList();
            FontFilterSelectInfo fontFilterSelectInfo2 = this.currentSelectFilter;
            if (fontFilterSelectInfo2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo2 = null;
            }
            List currentFilterTitleResInfo = fontFilterSelectInfo2.getCurrentFilterTitleResInfo();
            if (filterGroup.isSupport()) {
                filterIdList.add(Integer.valueOf(filterGroup.getFilterId()));
                currentFilterTitleResInfo.add(Integer.valueOf(filterGroup.getTitleResId()));
            }
            int filterId = filterGroup.getFilterId();
            FontFilterSelectInfo fontFilterSelectInfo3 = this.currentSelectFilter;
            if (fontFilterSelectInfo3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo3 = null;
            }
            if (filterId == fontFilterSelectInfo3.getCurrentFilterId(0)) {
                FontFilterSelectInfo fontFilterSelectInfo4 = this.currentSelectFilter;
                if (fontFilterSelectInfo4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                    fontFilterSelectInfo4 = null;
                }
                fontFilterSelectInfo4.setCurrentFilterTypeIndex(i);
                FontFilterSelectInfo fontFilterSelectInfo5 = this.currentSelectFilter;
                if (fontFilterSelectInfo5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                    fontFilterSelectInfo5 = null;
                }
                FilterColor[] currentColorData = fontFilterSelectInfo5.getCurrentColorData(0);
                int length2 = currentColorData.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    FilterColor filterColor = currentColorData[i2];
                    int colorId = filterColor.getColorId();
                    FontFilterSelectInfo fontFilterSelectInfo6 = this.currentSelectFilter;
                    if (fontFilterSelectInfo6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                        fontFilterSelectInfo6 = null;
                    }
                    if (colorId == fontFilterSelectInfo6.getCurrentColorId(0)) {
                        FontFilterSelectInfo fontFilterSelectInfo7 = this.currentSelectFilter;
                        if (fontFilterSelectInfo7 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                            fontFilterSelectInfo7 = null;
                        }
                        FilterTypeSelectInfo[] colorSelectInfo = fontFilterSelectInfo7.getColorSelectInfo();
                        colorSelectInfo[0].setCurrentColorIndex(i2);
                        colorSelectInfo[0].addModifyColor(filterColor);
                        EditFragmentViewModel editFragmentViewModel = this.viewModel;
                        filterColor.setColor((editFragmentViewModel == null || (primaryFontFilter = editFragmentViewModel.getPrimaryFontFilter()) == null) ? 0 : primaryFontFilter.getColor());
                        FontFilterSelectInfo fontFilterSelectInfo8 = this.currentSelectFilter;
                        if (fontFilterSelectInfo8 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                            fontFilterSelectInfo8 = null;
                        }
                        fontFilterSelectInfo8.setCurrentColor(0, i2, filterColor.getColorId());
                        break;
                    }
                }
            }
        }
    }

    public final void initSecondaryColorSelectionInfo(boolean z) {
        FilterGroup filterGroup;
        FontFilterData secondaryFontFilter;
        int i = !z ? 1 : 0;
        FontFilter fontFilter = this.fontFilterData;
        FontFilterSelectInfo fontFilterSelectInfo = null;
        if (fontFilter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fontFilterData");
            fontFilter = null;
        }
        FilterGroup[] filterGroupArr = fontFilter.getFilterGroups()[i];
        int length = filterGroupArr.length;
        int color = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                filterGroup = null;
                break;
            }
            filterGroup = filterGroupArr[i2];
            int filterId = filterGroup.getFilterId();
            FontFilterSelectInfo fontFilterSelectInfo2 = this.currentSelectFilter;
            if (fontFilterSelectInfo2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo2 = null;
            }
            if (filterId == fontFilterSelectInfo2.getCurrentFilterId(i)) {
                break;
            } else {
                i2++;
            }
        }
        if (filterGroup == null) {
            FontFilter fontFilter2 = this.fontFilterData;
            if (fontFilter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("fontFilterData");
                fontFilter2 = null;
            }
            filterGroup = (FilterGroup) ArraysKt.first(fontFilter2.getFilterGroups()[i]);
        }
        FontFilterSelectInfo fontFilterSelectInfo3 = this.currentSelectFilter;
        if (fontFilterSelectInfo3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo3 = null;
        }
        int currentColorId = fontFilterSelectInfo3.getCurrentColorId(i);
        int filterId2 = filterGroup.getFilterId();
        FontFilterSelectInfo fontFilterSelectInfo4 = this.currentSelectFilter;
        if (fontFilterSelectInfo4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo4 = null;
        }
        if (filterId2 == fontFilterSelectInfo4.getCurrentFilterId(1)) {
            FontFilterSelectInfo fontFilterSelectInfo5 = this.currentSelectFilter;
            if (fontFilterSelectInfo5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo5 = null;
            }
            FilterColor[] currentColorData = fontFilterSelectInfo5.getCurrentColorData(1);
            int length2 = currentColorData.length;
            for (int i3 = 0; i3 < length2; i3++) {
                FilterColor filterColor = currentColorData[i3];
                if (filterColor.getColorId() == currentColorId) {
                    FontFilterSelectInfo fontFilterSelectInfo6 = this.currentSelectFilter;
                    if (fontFilterSelectInfo6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                        fontFilterSelectInfo6 = null;
                    }
                    FilterTypeSelectInfo[] colorSelectInfo = fontFilterSelectInfo6.getColorSelectInfo();
                    colorSelectInfo[1].setCurrentColorIndex(i3);
                    colorSelectInfo[1].addModifyColor(filterColor);
                    EditFragmentViewModel editFragmentViewModel = this.viewModel;
                    if (editFragmentViewModel != null && (secondaryFontFilter = editFragmentViewModel.getSecondaryFontFilter()) != null) {
                        color = secondaryFontFilter.getColor();
                    }
                    filterColor.setColor(color);
                    break;
                }
            }
        }
        FontFilterSelectInfo fontFilterSelectInfo7 = this.currentSelectFilter;
        if (fontFilterSelectInfo7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo7 = null;
        }
        int currentColorIndex = fontFilterSelectInfo7.getCurrentColorIndex(i);
        FontFilterSelectInfo fontFilterSelectInfo8 = this.currentSelectFilter;
        if (fontFilterSelectInfo8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
        } else {
            fontFilterSelectInfo = fontFilterSelectInfo8;
        }
        fontFilterSelectInfo.setCurrentColor(1, currentColorIndex, currentColorId);
    }

    public final void initSlidingButton(String str, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        TextView textView = this.splitTitle;
        if (textView != null) {
            textView.setText(str);
        }
        SlidingSwitch slidingSwitch = new SlidingSwitch(getContext());
        this.splitSwitch = slidingSwitch;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(21);
        slidingSwitch.setLayoutParams(layoutParams);
        RelativeLayout relativeLayout = this.splitContainer;
        if (relativeLayout != null) {
            relativeLayout.addView(this.splitSwitch);
        }
        SlidingSwitch slidingSwitch2 = this.splitSwitch;
        if (slidingSwitch2 != null) {
            slidingSwitch2.setOnCheckedChangeListener(onCheckedChangeListener);
        }
        SlidingSwitch slidingSwitch3 = this.splitSwitch;
        if (slidingSwitch3 != null) {
            slidingSwitch3.setFocusable(true);
            slidingSwitch3.setFocusableInTouchMode(true);
            slidingSwitch3.setContentDescription(str);
        }
    }

    public final void initStyle(AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.FontFilterView, i, 0);
        this.objectTitleColorRes = typedArrayObtainStyledAttributes.getColor(R.styleable.FontFilterView_objectTitleColor, -16777216);
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.FontFilterView_objectSelectColor, -1);
        int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.FontFilterView_objectBackgroundColor, -7829368);
        this.disableTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.FontFilterView_disableTextColor, -7829368);
        typedArrayObtainStyledAttributes.recycle();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(new ColorStateList(new int[][]{new int[]{new int[]{android.R.attr.state_enabled}[0]}}, new int[]{color}));
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadius(getContext().getResources().getDimensionPixelSize(R.dimen.kg_font_filter_object_title_radius));
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(new ColorStateList(new int[][]{new int[]{new int[]{android.R.attr.state_enabled}[0]}}, new int[]{color2}));
        gradientDrawable2.setShape(0);
        gradientDrawable2.setCornerRadius(getContext().getResources().getDimensionPixelSize(R.dimen.kg_font_filter_object_container_radius));
    }

    public final void initTargetTitles(FontFilter fontFilter) {
        this.objectResId.clear();
        int i = WhenMappings.$EnumSwitchMapping$0[this.colorModificationTarget.ordinal()];
        if (i == 1) {
            CollectionsKt.addAll(this.objectResId, fontFilter.getTargetTitleResId());
        } else if (i == 2) {
            this.objectResId.add(fontFilter.getTargetTitleResId()[0]);
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            this.objectResId.add(fontFilter.getTargetTitleResId()[1]);
        }
        if (this.objectResId.size() <= 1) {
            TextView textView = (TextView) this.targetTitleList.get(0);
            if (textView != null) {
                textView.setText(R.string.kg_function_button_text_color);
                return;
            }
            return;
        }
        TextView textView2 = (TextView) this.targetTitleList.get(0);
        if (textView2 != null) {
            Object obj = this.objectResId.get(0);
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            textView2.setText(((Number) obj).intValue());
        }
        TextView textView3 = (TextView) this.targetTitleList.get(1);
        if (textView3 != null) {
            Object obj2 = this.objectResId.get(1);
            Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
            textView3.setText(((Number) obj2).intValue());
        }
    }

    public final void initView() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.kg_font_filter_view, (ViewGroup) this, true);
        this.fontFilterContainer = (LinearLayout) viewInflate.findViewById(R.id.font_filter_container);
        this.filterTypeTitle = (TextView) viewInflate.findViewById(R.id.filter_type_title);
        this.filterTypeList = (SmoothAlignHorizontalListView) viewInflate.findViewById(R.id.filter_type_list);
        this.transparencySlider = viewInflate.findViewById(R.id.transparency_slider_view);
        this.seekBar = (SeekBar) viewInflate.findViewById(R.id.font_transparency_seekbar);
        this.splitContainer = (RelativeLayout) viewInflate.findViewById(R.id.split_container);
        this.splitTitle = (TextView) viewInflate.findViewById(R.id.split_title);
        this.doodleContainer = (RelativeLayout) viewInflate.findViewById(R.id.doodle_container);
        this.doodleLabel = (TextView) viewInflate.findViewById(R.id.doodle_title);
        this.colorItemGroupList.set(0, viewInflate.findViewById(R.id.color_items_first_group));
        this.targetTitleList.set(0, viewInflate.findViewById(R.id.color_target_first_title));
        this.colorList.set(0, viewInflate.findViewById(R.id.color_list_first));
        this.colorLightSeekBarList.set(0, viewInflate.findViewById(R.id.color_lightness_first));
        this.colorLightSeekBarContainerList.set(0, viewInflate.findViewById(R.id.color_lightness_container_first));
        this.colorItemGroupList.set(1, viewInflate.findViewById(R.id.color_items_second_group));
        this.targetTitleList.set(1, viewInflate.findViewById(R.id.color_target_second_title));
        this.colorList.set(1, viewInflate.findViewById(R.id.color_list_second));
        this.colorLightSeekBarList.set(1, viewInflate.findViewById(R.id.color_lightness_second));
        this.colorLightSeekBarContainerList.set(1, viewInflate.findViewById(R.id.color_lightness_container_second));
        setFocusableInTouchMode(true);
        setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
        LinearLayout linearLayout = this.fontFilterContainer;
        if (linearLayout != null) {
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FontFilterView.initView$lambda$1(view);
                }
            });
        }
        View view = this.transparencySlider;
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    FontFilterView.initView$lambda$2(view2);
                }
            });
        }
        LinearLayout linearLayout2 = (LinearLayout) this.colorItemGroupList.get(0);
        if (linearLayout2 != null) {
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$$ExternalSyntheticLambda8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    FontFilterView.initView$lambda$3(view2);
                }
            });
        }
        LinearLayout linearLayout3 = (LinearLayout) this.colorItemGroupList.get(1);
        if (linearLayout3 != null) {
            linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$$ExternalSyntheticLambda9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    FontFilterView.initView$lambda$4(view2);
                }
            });
        }
        SmoothAlignHorizontalListView smoothAlignHorizontalListView = this.filterTypeList;
        if (smoothAlignHorizontalListView != null) {
            smoothAlignHorizontalListView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$$ExternalSyntheticLambda10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    FontFilterView.initView$lambda$5(view2);
                }
            });
        }
    }

    public final void markUserModifiedColor(int i) {
        TemplateConfig templateConfig;
        ClockInfo clockInfo;
        EditFragmentViewModel editFragmentViewModel = this.viewModel;
        if (editFragmentViewModel == null || (templateConfig = editFragmentViewModel.getTemplateConfig()) == null || (clockInfo = templateConfig.getClockInfo()) == null) {
            return;
        }
        if (i == 0) {
            clockInfo.setUserModifiedPrimaryColor(true);
        } else {
            clockInfo.setUserModifiedSecondaryColor(true);
        }
    }

    public final void onSelectColorChange(int i) {
        switchToTargetIndex(i);
        FontFilterSelectInfo fontFilterSelectInfo = this.currentSelectFilter;
        FontFilterSelectInfo fontFilterSelectInfo2 = null;
        if (fontFilterSelectInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo = null;
        }
        int currentFilterId = fontFilterSelectInfo.getCurrentFilterId(i);
        FontFilterSelectInfo fontFilterSelectInfo3 = this.currentSelectFilter;
        if (fontFilterSelectInfo3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo3 = null;
        }
        int currentColorId = fontFilterSelectInfo3.getCurrentColorId(i);
        FontFilterSelectInfo fontFilterSelectInfo4 = this.currentSelectFilter;
        if (fontFilterSelectInfo4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
        } else {
            fontFilterSelectInfo2 = fontFilterSelectInfo4;
        }
        updateFontFilter(i, new FontFilterData(currentFilterId, currentColorId, fontFilterSelectInfo2.getCurrentARGBColor(i), false, 8, null));
    }

    public final void refreshFilterTitle() {
        EditFragmentViewModel editFragmentViewModel = this.viewModel;
        if (editFragmentViewModel != null) {
            int fontFilterId = editFragmentViewModel.getFontFilterId();
            FontFilterSelectInfo fontFilterSelectInfo = this.currentSelectFilter;
            FontFilterSelectInfo fontFilterSelectInfo2 = null;
            if (fontFilterSelectInfo == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo = null;
            }
            fontFilterSelectInfo.setCurrentFilterId(fontFilterId);
            FontFilterSelectInfo fontFilterSelectInfo3 = this.currentSelectFilter;
            if (fontFilterSelectInfo3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo3 = null;
            }
            List filterIdList = fontFilterSelectInfo3.getFilterIdList();
            int size = filterIdList.size();
            for (int i = 0; i < size; i++) {
                if (fontFilterId == ((Number) filterIdList.get(i)).intValue()) {
                    FontFilterSelectInfo fontFilterSelectInfo4 = this.currentSelectFilter;
                    if (fontFilterSelectInfo4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                    } else {
                        fontFilterSelectInfo2 = fontFilterSelectInfo4;
                    }
                    fontFilterSelectInfo2.setCurrentFilterTypeIndex(i);
                    FontFilterTitleAdapter fontFilterTitleAdapter = this.filterTypeAdapter;
                    if (fontFilterTitleAdapter != null) {
                        fontFilterTitleAdapter.updateTitleIndex(i);
                        return;
                    }
                    return;
                }
            }
        }
    }

    public final void reset() {
        List list = this.targetIndexList;
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                int iIntValue = ((Number) it.next()).intValue();
                FontFilterSelectInfo fontFilterSelectInfo = this.currentSelectFilter;
                if (fontFilterSelectInfo == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                    fontFilterSelectInfo = null;
                }
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                fontFilterSelectInfo.resetCurrentModifyColor(context);
                FontFilterColorAdapter fontFilterColorAdapter = (FontFilterColorAdapter) this.colorAdapterList.get(iIntValue);
                if (fontFilterColorAdapter != null) {
                    fontFilterColorAdapter.notifyDataSetChanged();
                }
            }
        }
        AutoBottomSheet autoBottomSheet = this.customColorBottomSheet;
        if (autoBottomSheet != null) {
            autoBottomSheet.dismiss();
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0073  */
    /* JADX WARN: Code duplicated, block: B:28:0x0077  */
    /* JADX WARN: Code duplicated, block: B:31:0x0089  */
    public final void resetColorSelectionToAuto() {
        FontFilterSelectInfo fontFilterSelectInfo;
        FontFilterSelectInfo fontFilterSelectInfo2;
        List list = this.targetIndexList;
        if (list == null) {
            return;
        }
        refreshFilterTitle();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            FontFilterSelectInfo fontFilterSelectInfo3 = this.currentSelectFilter;
            FontFilterSelectInfo fontFilterSelectInfo4 = null;
            if (fontFilterSelectInfo3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo3 = null;
            }
            FilterColor[] currentColorData = fontFilterSelectInfo3.getCurrentColorData(iIntValue);
            if (iIntValue != 1) {
                fontFilterSelectInfo = this.currentSelectFilter;
                if (fontFilterSelectInfo == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                    fontFilterSelectInfo = null;
                }
                fontFilterSelectInfo.setCurrentColor(iIntValue, 0, currentColorData[0].getColorId());
                fontFilterSelectInfo2 = this.currentSelectFilter;
                if (fontFilterSelectInfo2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                    fontFilterSelectInfo2 = null;
                }
                fontFilterSelectInfo2.setCurrentFilterColor(iIntValue, currentColorData[0]);
            } else {
                MiuiClockViewSafeHolder miuiClockViewSafeHolder = this.miuiClockViewSafeHolder;
                if (Intrinsics.areEqual(miuiClockViewSafeHolder != null ? miuiClockViewSafeHolder.getTemplateId() : null, "doodle")) {
                    FontFilterSelectInfo fontFilterSelectInfo5 = this.currentSelectFilter;
                    if (fontFilterSelectInfo5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                        fontFilterSelectInfo5 = null;
                    }
                    fontFilterSelectInfo5.setCurrentColor(iIntValue, 13, 13);
                    FontFilterSelectInfo fontFilterSelectInfo6 = this.currentSelectFilter;
                    if (fontFilterSelectInfo6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                        fontFilterSelectInfo6 = null;
                    }
                    fontFilterSelectInfo6.setCurrentFilterColor(iIntValue, new FilterColor(13, getContext().getColor(R.color.kg_editor_template_default_background_color_doodle), 0, 0, 2, false, false, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSearchResultTitle, null));
                } else {
                    fontFilterSelectInfo = this.currentSelectFilter;
                    if (fontFilterSelectInfo == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                        fontFilterSelectInfo = null;
                    }
                    fontFilterSelectInfo.setCurrentColor(iIntValue, 0, currentColorData[0].getColorId());
                    fontFilterSelectInfo2 = this.currentSelectFilter;
                    if (fontFilterSelectInfo2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                        fontFilterSelectInfo2 = null;
                    }
                    fontFilterSelectInfo2.setCurrentFilterColor(iIntValue, currentColorData[0]);
                }
            }
            FontFilterColorAdapter fontFilterColorAdapter = (FontFilterColorAdapter) this.colorAdapterList.get(iIntValue);
            if (fontFilterColorAdapter != null) {
                FontFilterSelectInfo fontFilterSelectInfo7 = this.currentSelectFilter;
                if (fontFilterSelectInfo7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                } else {
                    fontFilterSelectInfo4 = fontFilterSelectInfo7;
                }
                fontFilterColorAdapter.notifyItemChanged(fontFilterSelectInfo4.getCurrentColorIndex(iIntValue));
            }
            onSelectColorChange(iIntValue);
        }
    }

    public final void resetFontFilterData(FontFilter fontFilter, ColorModificationTarget colorModificationTarget) {
        FontFilter fontFilter2 = this.fontFilterData;
        FontFilterSelectInfo fontFilterSelectInfo = null;
        if (fontFilter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fontFilterData");
            fontFilter2 = null;
        }
        int length = fontFilter2.getTargetTitleResId().length;
        this.fontFilterData = fontFilter;
        setColorModificationTarget(colorModificationTarget, fontFilter.getTargetTitleResId());
        initTargetTitles(fontFilter);
        if (fontFilter.getTargetTitleResId().length <= 1 || length >= 2) {
            FontFilterSelectInfo fontFilterSelectInfo2 = this.currentSelectFilter;
            if (fontFilterSelectInfo2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo2 = null;
            }
            FilterTypeSelectInfo filterTypeSelectInfo = fontFilterSelectInfo2.getColorSelectInfo()[1];
            FontFilterSelectInfo fontFilterSelectInfo3 = this.currentSelectFilter;
            if (fontFilterSelectInfo3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            } else {
                fontFilterSelectInfo = fontFilterSelectInfo3;
            }
            filterTypeSelectInfo.setCurrentColorIndex(fontFilterSelectInfo.getCurrentColorIndex(this.defaultTargetIndex));
            return;
        }
        initSecondaryColorSelectionInfo(false);
        setSecondaryTargetTitleVisibility();
        initColorList(1);
        TextView textView = (TextView) this.targetTitleList.get(0);
        if (textView != null) {
            Object obj = this.objectResId.get(0);
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            textView.setText(((Number) obj).intValue());
        }
        TextView textView2 = (TextView) this.targetTitleList.get(1);
        if (textView2 != null) {
            Object obj2 = this.objectResId.get(1);
            Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
            textView2.setText(((Number) obj2).intValue());
        }
    }

    public final void setColorModificationTarget(ColorModificationTarget colorModificationTarget, Integer[] numArr) {
        this.colorModificationTarget = colorModificationTarget;
        int i = WhenMappings.$EnumSwitchMapping$0[colorModificationTarget.ordinal()];
        if (i == 1) {
            if (numArr.length == 2) {
                this.targetIndexList = CollectionsKt.mutableListOf(0, 1);
            } else {
                this.targetIndexList = CollectionsKt.mutableListOf(0);
            }
            this.defaultTargetIndex = 0;
            return;
        }
        if (i == 2) {
            this.targetIndexList = CollectionsKt.mutableListOf(0);
            this.defaultTargetIndex = 0;
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            this.targetIndexList = CollectionsKt.mutableListOf(1);
            this.defaultTargetIndex = 1;
        }
    }

    public final void setCustomColorBottomSheet(@Nullable AutoBottomSheet autoBottomSheet) {
        Log.d("FontFilterView", "setFontColorCustomBottomSheet: fontColorCustomBottomSheet=" + autoBottomSheet);
        this.customColorBottomSheet = autoBottomSheet;
    }

    public final void setDoodleSwitchContainer(@NotNull CompoundButton.OnCheckedChangeListener listener, int i) {
        IntWrapper doodleStatus;
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.doodleSwitchListener = listener;
        initDoodleSlidingButton();
        setDoodleContainerVisible(i);
        EditFragmentViewModel editFragmentViewModel = this.viewModel;
        updateDoodle((editFragmentViewModel == null || (doodleStatus = editFragmentViewModel.getDoodleStatus()) == null) ? 2 : doodleStatus.getIntValue());
    }

    public final void setFilterTypeList(@Nullable SmoothAlignHorizontalListView smoothAlignHorizontalListView) {
        this.filterTypeList = smoothAlignHorizontalListView;
    }

    public final void setFontFilterData(@NotNull FontFilter data, @NotNull FilterTypeSelectInfo[] filterColorSelectInfo, @NotNull MiuiClockViewSafeHolder miuiClockViewSafeHolder, boolean z, boolean z2, float f, @Nullable EditFragmentViewModel editFragmentViewModel, @NotNull ColorModificationTarget colorModificationTarget) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(filterColorSelectInfo, "filterColorSelectInfo");
        Intrinsics.checkNotNullParameter(miuiClockViewSafeHolder, "miuiClockViewSafeHolder");
        Intrinsics.checkNotNullParameter(colorModificationTarget, "colorModificationTarget");
        this.miuiClockViewSafeHolder = miuiClockViewSafeHolder;
        this.viewModel = editFragmentViewModel;
        this.fontFilterData = data;
        this.currentGlassTransparency = f;
        if (data.getFilterGroups().length == 0) {
            Log.d("FontFilterView", "font filter data is empty");
        } else {
            initBaseConfig(colorModificationTarget, data);
            initFilterAndColorData(filterColorSelectInfo, z, z2);
        }
    }

    public final void setHslPagerHeight(int i) {
        this.hslPagerHeight = i;
    }

    public final void setObjectTitleColorRes(int i) {
        this.objectTitleColorRes = i;
    }

    public final void setOnGlassTransparencyInit(@Nullable Function1<? super Float, Unit> function1) {
        this.onGlassTransparencyInit = function1;
    }

    public final void setSecondaryTargetTitleVisibility() {
        TextView textView = (TextView) this.targetTitleList.get(1);
        if (textView != null) {
            textView.setVisibility(this.objectResId.size() > 1 ? 0 : 8);
        }
    }

    public final void setSeekBar(@Nullable SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public final void setSplitContainer(@NotNull String title, @NotNull CompoundButton.OnCheckedChangeListener listener, int i) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(listener, "listener");
        initSlidingButton(title, listener);
        setSplitContainerVisible(i);
    }

    public final void setTargetEnable(int i, boolean z) {
        LinearLayout linearLayout = (LinearLayout) this.colorItemGroupList.get(i);
        if (linearLayout != null) {
            linearLayout.setVisibility(!z ? 8 : 0);
        }
    }

    public final void setTransparencySlider(@Nullable View view) {
        this.transparencySlider = view;
    }

    public final boolean shouldUpdateColor(int i) {
        FontFilterData primaryFontFilter = i == 0 ? getPrimaryFontFilter() : getSecondaryFontFilter();
        FontFilterSelectInfo fontFilterSelectInfo = this.currentSelectFilter;
        FontFilterSelectInfo fontFilterSelectInfo2 = null;
        if (fontFilterSelectInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo = null;
        }
        if (fontFilterSelectInfo.getCurrentFilterId(i) != primaryFontFilter.getFilterId()) {
            return true;
        }
        FontFilterSelectInfo fontFilterSelectInfo3 = this.currentSelectFilter;
        if (fontFilterSelectInfo3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo3 = null;
        }
        if (fontFilterSelectInfo3.getCurrentColorId(i) != primaryFontFilter.getColorId()) {
            return true;
        }
        FontFilterSelectInfo fontFilterSelectInfo4 = this.currentSelectFilter;
        if (fontFilterSelectInfo4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
        } else {
            fontFilterSelectInfo2 = fontFilterSelectInfo4;
        }
        return fontFilterSelectInfo2.getCurrentARGBColor(i) != primaryFontFilter.getColor();
    }

    public final void showCustomColorBottomSheet(int i) {
        int i2;
        FontFilterSelectInfo fontFilterSelectInfo;
        Log.d("FontFilterView", "showFontColorCustomBottomSheet: fontColorCustomBottomSheet=" + this.customColorBottomSheet);
        FontFilterCustomView fontFilterCustomView = null;
        if (this.customColorViewList.get(i) == null) {
            List list = this.customColorViewList;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            FontFilterSelectInfo fontFilterSelectInfo2 = this.currentSelectFilter;
            if (fontFilterSelectInfo2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo = null;
            } else {
                fontFilterSelectInfo = fontFilterSelectInfo2;
            }
            MiuiClockViewSafeHolder miuiClockViewSafeHolder = this.miuiClockViewSafeHolder;
            Intrinsics.checkNotNull(miuiClockViewSafeHolder);
            i2 = i;
            list.set(i2, new FontFilterCustomView(context, i2, fontFilterSelectInfo, miuiClockViewSafeHolder, this.hslPagerHeight));
        } else {
            i2 = i;
        }
        FontFilterCustomView fontFilterCustomView2 = (FontFilterCustomView) this.customColorViewList.get(i2);
        if (fontFilterCustomView2 != null) {
            fontFilterCustomView2.setColorChangeHandler(new FontColorChangeHandler() { // from class: com.miui.keyguard.editor.view.FontFilterView$showCustomColorBottomSheet$1$1
                @Override // com.miui.keyguard.editor.view.FontColorChangeHandler
                public void onColorChange(int i3) {
                    FontFilterColorAdapter fontFilterColorAdapter = (FontFilterColorAdapter) this.this$0.colorAdapterList.get(i3);
                    if (fontFilterColorAdapter != null) {
                        FontFilterSelectInfo fontFilterSelectInfo3 = this.this$0.currentSelectFilter;
                        if (fontFilterSelectInfo3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                            fontFilterSelectInfo3 = null;
                        }
                        fontFilterColorAdapter.notifyItemChanged(fontFilterSelectInfo3.getCurrentColorIndex(i3));
                    }
                    this.this$0.markUserModifiedColor(i3);
                    this.this$0.onSelectColorChange(i3);
                }
            });
            fontFilterCustomView2.setDismissListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$$ExternalSyntheticLambda11
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FontFilterView.showCustomColorBottomSheet$lambda$21$lambda$20(this.f$0, view);
                }
            });
        }
        AutoBottomSheet autoBottomSheet = this.customColorBottomSheet;
        if (autoBottomSheet != null) {
            FontFilterCustomView fontFilterCustomView3 = (FontFilterCustomView) this.customColorViewList.get(i2);
            if (fontFilterCustomView3 != null) {
                fontFilterCustomView3.setCurrentColor();
                fontFilterCustomView = fontFilterCustomView3;
            }
            autoBottomSheet.setContentView(fontFilterCustomView);
        }
        AutoBottomSheet autoBottomSheet2 = this.customColorBottomSheet;
        if (autoBottomSheet2 != null) {
            autoBottomSheet2.show();
        }
    }

    public final void showLightSeekBar(int i) {
        final LinearLayout linearLayout = (LinearLayout) this.colorLightSeekBarContainerList.get(i);
        float f = this.colorLightContainerHeight + 0.0f;
        if ((linearLayout == null || linearLayout.getHeight() != 0) && this.currentStatus.getColorLightAnimStatus() == 0) {
            return;
        }
        if (this.currentStatus.getColorLightAnimStatus() > 0) {
            Folme.use((View) this).state().cancel();
            Folme.use((View) this).visible().cancel();
            if (linearLayout != null) {
                Folme.use((View) linearLayout).state().cancel();
                Folme.use((View) linearLayout).visible().cancel();
            }
            this.currentStatus.setColorLightAnimStatus(0);
        }
        AnimState animState = new AnimState("show_alpha_start");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        final AnimState animStateAdd = animState.add(viewProperty, 0.0f, new long[0]);
        final AnimState animStateAdd2 = new AnimState("show_alpha_end").add(viewProperty, 1.0f, new long[0]);
        AnimState animState2 = new AnimState("show_height_start");
        ViewProperty viewProperty2 = ViewProperty.HEIGHT;
        AnimState animStateAdd3 = animState2.add(viewProperty2, 0.0f, new long[0]);
        AnimState animStateAdd4 = new AnimState("show_height_end").add(viewProperty2, f, new long[0]);
        AnimConfig animConfig = TransitionKt.getAnimConfig(1.0f, 0.15f, new TransitionListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$showLightSeekBar$alphaAnimConfig$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                FontFilterViewStatus fontFilterViewStatus = this.this$0.currentStatus;
                fontFilterViewStatus.setColorLightAnimStatus(fontFilterViewStatus.getColorLightAnimStatus() + 1);
                LinearLayout linearLayout2 = linearLayout;
                if (linearLayout2 != null) {
                    linearLayout2.setVisibility(0);
                }
                LinearLayout linearLayout3 = linearLayout;
                if (linearLayout3 != null) {
                    linearLayout3.setAlpha(0.0f);
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                FontFilterViewStatus fontFilterViewStatus = this.this$0.currentStatus;
                fontFilterViewStatus.setColorLightAnimStatus(fontFilterViewStatus.getColorLightAnimStatus() - 1);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                FontFilterViewStatus fontFilterViewStatus = this.this$0.currentStatus;
                fontFilterViewStatus.setColorLightAnimStatus(fontFilterViewStatus.getColorLightAnimStatus() - 1);
            }
        });
        final AnimConfig animConfig2 = TransitionKt.getAnimConfig(0.95f, 0.3f, new TransitionListener() { // from class: com.miui.keyguard.editor.view.FontFilterView$showLightSeekBar$heightAnimConfig$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                FontFilterViewStatus fontFilterViewStatus = this.this$0.currentStatus;
                fontFilterViewStatus.setColorLightAnimStatus(fontFilterViewStatus.getColorLightAnimStatus() + 1);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                FontFilterViewStatus fontFilterViewStatus = this.this$0.currentStatus;
                fontFilterViewStatus.setColorLightAnimStatus(fontFilterViewStatus.getColorLightAnimStatus() - 1);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                FontFilterViewStatus fontFilterViewStatus = this.this$0.currentStatus;
                fontFilterViewStatus.setColorLightAnimStatus(fontFilterViewStatus.getColorLightAnimStatus() - 1);
            }
        });
        if (linearLayout != null) {
            Folme.use((View) linearLayout).state().setTo(animStateAdd3).to(animStateAdd4, animConfig);
            postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.view.FontFilterView$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    FontFilterView.showLightSeekBar$lambda$28$lambda$27(linearLayout, animStateAdd, animStateAdd2, animConfig2);
                }
            }, 200L);
        }
    }

    public final void showSecondColorGroup() {
        LinearLayout linearLayout = (LinearLayout) this.colorItemGroupList.get(1);
        if (linearLayout == null || linearLayout.getVisibility() != 0) {
            SeekBar seekBar = (SeekBar) this.colorLightSeekBarList.get(1);
            if (seekBar != null) {
                seekBar.setEnabled(true);
            }
            if (linearLayout != null) {
                linearLayout.setVisibility(0);
            }
            AnimState animState = new AnimState("show_start");
            ViewProperty viewProperty = ViewProperty.ALPHA;
            Folme.useAt(linearLayout).state().setTo(animState.add(viewProperty, 0.0f, new long[0])).to(new AnimState("show_end").add(viewProperty, 1.0f, new long[0]), TransitionKt.getDefaultConfig());
        }
    }

    public final void switchToTargetIndex(int i) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[this.colorModificationTarget.ordinal()];
        FontFilterSelectInfo fontFilterSelectInfo = null;
        if (i2 == 1) {
            FontFilterSelectInfo fontFilterSelectInfo2 = this.currentSelectFilter;
            if (fontFilterSelectInfo2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                fontFilterSelectInfo2 = null;
            }
            if (fontFilterSelectInfo2.getTargetIndex() != i) {
                FontFilterSelectInfo fontFilterSelectInfo3 = this.currentSelectFilter;
                if (fontFilterSelectInfo3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                } else {
                    fontFilterSelectInfo = fontFilterSelectInfo3;
                }
                fontFilterSelectInfo.setTargetIndex(i);
                return;
            }
            return;
        }
        if (i2 == 2) {
            FontFilterSelectInfo fontFilterSelectInfo4 = this.currentSelectFilter;
            if (fontFilterSelectInfo4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            } else {
                fontFilterSelectInfo = fontFilterSelectInfo4;
            }
            fontFilterSelectInfo.setTargetIndex(0);
            return;
        }
        if (i2 != 3) {
            throw new NoWhenBranchMatchedException();
        }
        FontFilterSelectInfo fontFilterSelectInfo5 = this.currentSelectFilter;
        if (fontFilterSelectInfo5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
        } else {
            fontFilterSelectInfo = fontFilterSelectInfo5;
        }
        fontFilterSelectInfo.setTargetIndex(1);
    }

    public final void updateAnotherTarget(int i) {
        int i2 = i == 0 ? 1 : 0;
        FontFilterSelectInfo fontFilterSelectInfo = null;
        if (i2 == 1) {
            FontFilter fontFilter = this.fontFilterData;
            if (fontFilter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("fontFilterData");
                fontFilter = null;
            }
            if (fontFilter.getFilterGroups().length < 2) {
                Log.d("FontFilterView", "no secondary target");
                return;
            }
        }
        FontFilterSelectInfo fontFilterSelectInfo2 = this.currentSelectFilter;
        if (fontFilterSelectInfo2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo2 = null;
        }
        int currentFilterId = fontFilterSelectInfo2.getCurrentFilterId(i2);
        FontFilterSelectInfo fontFilterSelectInfo3 = this.currentSelectFilter;
        if (fontFilterSelectInfo3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo3 = null;
        }
        int currentColorId = fontFilterSelectInfo3.getCurrentColorId(i2);
        FontFilterSelectInfo fontFilterSelectInfo4 = this.currentSelectFilter;
        if (fontFilterSelectInfo4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
        } else {
            fontFilterSelectInfo = fontFilterSelectInfo4;
        }
        updateFontFilter(i2, new FontFilterData(currentFilterId, currentColorId, fontFilterSelectInfo.getCurrentARGBColor(i2), false, 8, null));
    }

    public final void updateColorLight(int i) {
        SeekBar seekBar = (SeekBar) this.colorLightSeekBarList.get(i);
        FontFilterSelectInfo fontFilterSelectInfo = this.currentSelectFilter;
        FontFilterSelectInfo fontFilterSelectInfo2 = null;
        if (fontFilterSelectInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
            fontFilterSelectInfo = null;
        }
        FilterColor currentFilterColor = fontFilterSelectInfo.getCurrentFilterColor(i);
        Context context = getRootView().getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        DefaultColorProgressDrawable defaultColorProgressDrawable = new DefaultColorProgressDrawable(context, currentFilterColor != null ? currentFilterColor.getProgressColor() : -65536, 0, 0, 12, null);
        if (currentFilterColor != null && currentFilterColor.getMutable()) {
            if (seekBar != null) {
                seekBar.setProgressDrawable(defaultColorProgressDrawable);
            }
            if (seekBar != null) {
                FontFilterSelectInfo fontFilterSelectInfo3 = this.currentSelectFilter;
                if (fontFilterSelectInfo3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("currentSelectFilter");
                } else {
                    fontFilterSelectInfo2 = fontFilterSelectInfo3;
                }
                seekBar.setProgress((int) (fontFilterSelectInfo2.getCurrentHslColor(i)[2] * 100.0f));
            }
        }
        this.colorLightUpdateListener.onMutableChanged(i, currentFilterColor != null && currentFilterColor.getMutable());
    }

    public final void updateDifferentColor(int i) {
        if (i == 1) {
            setSplitSwitchQuiet(true);
        } else {
            if (i != 2) {
                return;
            }
            setSplitSwitchQuiet(false);
        }
    }

    public final void updateDoodle(int i) {
        if (i == 0) {
            TextView textView = this.doodleLabel;
            if (textView != null) {
                textView.setSelected(true);
            }
            SlidingSwitch slidingSwitch = this.doodleSwitch;
            if (slidingSwitch != null) {
                slidingSwitch.setSelected(true);
            }
            setDoodleSwitchCheckedQuiet(false);
        } else if (i == 1) {
            TextView textView2 = this.doodleLabel;
            if (textView2 != null) {
                textView2.setSelected(false);
            }
            SlidingSwitch slidingSwitch2 = this.doodleSwitch;
            if (slidingSwitch2 != null) {
                slidingSwitch2.setSelected(false);
            }
            setDoodleSwitchCheckedQuiet(true);
        } else if (i == 2) {
            TextView textView3 = this.doodleLabel;
            if (textView3 != null) {
                textView3.setSelected(false);
            }
            SlidingSwitch slidingSwitch3 = this.doodleSwitch;
            if (slidingSwitch3 != null) {
                slidingSwitch3.setSelected(false);
            }
            setDoodleSwitchCheckedQuiet(false);
        }
        updateDoodleContainerContentDescription();
    }

    public final void updateDoodleContainerContentDescription() {
        String string;
        CharSequence text;
        RelativeLayout relativeLayout = this.doodleContainer;
        if (relativeLayout != null) {
            SlidingSwitch slidingSwitch = this.doodleSwitch;
            String string2 = (slidingSwitch == null || !slidingSwitch.isChecked()) ? getContext().getString(R.string.kg_talkback_off) : getContext().getString(R.string.kg_talkback_on);
            TextView textView = this.doodleLabel;
            if (textView == null || (text = textView.getText()) == null || (string = text.toString()) == null) {
                string = "";
            }
            relativeLayout.setContentDescription(string2 + " " + string + " " + getContext().getString(R.string.kg_talkback_double_tap_to_activate));
        }
    }

    public final void updateFontFilter(int i, FontFilterData fontFilterData) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[this.colorModificationTarget.ordinal()];
        if (i2 == 1) {
            EditFragmentViewModel editFragmentViewModel = this.viewModel;
            if (editFragmentViewModel != null) {
                editFragmentViewModel.updateFontFilter(i, fontFilterData);
                return;
            }
            return;
        }
        if (i2 == 2) {
            EditFragmentViewModel editFragmentViewModel2 = this.viewModel;
            if (editFragmentViewModel2 != null) {
                editFragmentViewModel2.updateFontFilter(0, fontFilterData);
                return;
            }
            return;
        }
        if (i2 != 3) {
            throw new NoWhenBranchMatchedException();
        }
        EditFragmentViewModel editFragmentViewModel3 = this.viewModel;
        if (editFragmentViewModel3 != null) {
            editFragmentViewModel3.updateFontFilter(1, fontFilterData);
        }
    }

    public final void updateFontFilterData(FontFilter fontFilter, ColorModificationTarget colorModificationTarget) {
        Intrinsics.checkNotNullParameter(fontFilter, "fontFilter");
        Intrinsics.checkNotNullParameter(colorModificationTarget, "colorModificationTarget");
        resetFontFilterData(fontFilter, colorModificationTarget);
        int i = WhenMappings.$EnumSwitchMapping$0[colorModificationTarget.ordinal()];
        if (i == 1) {
            showSecondColorGroup();
        } else {
            if (i != 2) {
                return;
            }
            dismissSecondColorGroup();
        }
    }

    public final void updateGlassTransparency(float f) {
        this.currentGlassTransparency = f;
    }
}
