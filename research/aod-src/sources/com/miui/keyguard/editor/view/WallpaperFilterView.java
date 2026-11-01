package com.miui.keyguard.editor.view;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TransitionKt;
import com.miui.keyguard.editor.data.preset.WallpaperFilter;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.homepage.view.adapter.FilterIconAdapter;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import com.miui.keyguard.editor.viewmodel.IntWrapper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import miuix.animation.Folme;
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
/* JADX INFO: compiled from: WallpaperFilterView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SuppressLint({"ViewConstructor"})
@SourceDebugExtension
public final class WallpaperFilterView extends LinearLayout {
    public boolean changeFromOutside;
    public int currentSelectFilter;
    public int currentSelectTitle;
    public int extraFilterAreaHeight;
    public ColorStateList extraLabelColorSelector;
    public int filterAreaHeight;
    public List filterData;
    public SmoothAlignHorizontalListView filterIconsList;
    public HashMap filterIdToIndex;
    public ImageView headIcon;
    public TextView headTitle;
    public FilterIconAdapter iconAdapter;
    public View lastSelectItemView;
    public RelativeLayout matteContainer;
    public TextView matteLabel;
    public SlidingSwitch matteSwitch;
    public CompoundButton.OnCheckedChangeListener matteSwitchListener;
    public WeakReference templateView;
    public ArrayList titleResIdList;
    public EditFragmentViewModel viewModel;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.view.WallpaperFilterView$onAttachedToWindow$1, reason: invalid class name */
    /* JADX INFO: compiled from: WallpaperFilterView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.view.WallpaperFilterView$onAttachedToWindow$1", f = "WallpaperFilterView.kt", l = {205, 206}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return WallpaperFilterView.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x003e, code lost:
        
            if (r6 == r0) goto L18;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                r5 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r5.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1e
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Exception -> L5f
                goto L41
            L12:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L1a:
                kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Exception -> L5f
                goto L2c
            L1e:
                kotlin.ResultKt.throwOnFailure(r6)
                r5.label = r3     // Catch: java.lang.Exception -> L5f
                r3 = 500(0x1f4, double:2.47E-321)
                java.lang.Object r6 = kotlinx.coroutines.DelayKt.delay(r3, r5)     // Catch: java.lang.Exception -> L5f
                if (r6 != r0) goto L2c
                goto L40
            L2c:
                kotlinx.coroutines.CoroutineDispatcher r6 = kotlinx.coroutines.Dispatchers.getIO()     // Catch: java.lang.Exception -> L5f
                com.miui.keyguard.editor.view.WallpaperFilterView$onAttachedToWindow$1$talkbackEnable$1 r1 = new com.miui.keyguard.editor.view.WallpaperFilterView$onAttachedToWindow$1$talkbackEnable$1     // Catch: java.lang.Exception -> L5f
                com.miui.keyguard.editor.view.WallpaperFilterView r3 = com.miui.keyguard.editor.view.WallpaperFilterView.this     // Catch: java.lang.Exception -> L5f
                r4 = 0
                r1.<init>(r3, r4)     // Catch: java.lang.Exception -> L5f
                r5.label = r2     // Catch: java.lang.Exception -> L5f
                java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r6, r1, r5)     // Catch: java.lang.Exception -> L5f
                if (r6 != r0) goto L41
            L40:
                return r0
            L41:
                java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch: java.lang.Exception -> L5f
                boolean r6 = r6.booleanValue()     // Catch: java.lang.Exception -> L5f
                if (r6 == 0) goto L67
                com.miui.keyguard.editor.view.WallpaperFilterView r6 = com.miui.keyguard.editor.view.WallpaperFilterView.this     // Catch: java.lang.Exception -> L5f
                boolean r6 = r6.isAttachedToWindow()     // Catch: java.lang.Exception -> L5f
                if (r6 == 0) goto L67
                com.miui.keyguard.editor.view.WallpaperFilterView r5 = com.miui.keyguard.editor.view.WallpaperFilterView.this     // Catch: java.lang.Exception -> L5f
                android.widget.ImageView r5 = com.miui.keyguard.editor.view.WallpaperFilterView.access$getHeadIcon$p(r5)     // Catch: java.lang.Exception -> L5f
                if (r5 == 0) goto L67
                r6 = 8
                r5.sendAccessibilityEvent(r6)     // Catch: java.lang.Exception -> L5f
                goto L67
            L5f:
                r5 = move-exception
                java.lang.String r6 = "WallpaperFilterView"
                java.lang.String r0 = "Accessibility event error"
                android.util.Log.e(r6, r0, r5)
            L67:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.view.WallpaperFilterView.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WallpaperFilterView(@NotNull Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WallpaperFilterView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.wallpaperFilterViewStyle);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WallpaperFilterView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.titleResIdList = new ArrayList();
        this.filterIdToIndex = new HashMap();
        this.extraFilterAreaHeight = getResources().getDimensionPixelSize(R.dimen.kg_wallpaper_filter_extra_area_height);
        initView();
        initStyle(attributeSet, i);
    }

    private final String getSwitchState() {
        SlidingSwitch slidingSwitch = this.matteSwitch;
        if (slidingSwitch == null || !slidingSwitch.isChecked()) {
            String string = getContext().getString(R.string.kg_talkback_off);
            Intrinsics.checkNotNull(string);
            return string;
        }
        String string2 = getContext().getString(R.string.kg_talkback_on);
        Intrinsics.checkNotNull(string2);
        return string2;
    }

    public static final void initSlidingButton$lambda$1(WallpaperFilterView wallpaperFilterView, CompoundButton compoundButton, boolean z) {
        EditFragmentViewModel editFragmentViewModel = wallpaperFilterView.viewModel;
        if (editFragmentViewModel != null) {
            EditFragmentViewModel.updateMatteStatus$default(editFragmentViewModel, z ? 2 : 3, false, 2, null);
        }
        wallpaperFilterView.updateMatteContainerContentDescription();
    }

    public static final void initSlidingButton$lambda$2(WallpaperFilterView wallpaperFilterView, View view) {
        SlidingSwitch slidingSwitch = wallpaperFilterView.matteSwitch;
        if (slidingSwitch != null) {
            slidingSwitch.performClick();
        }
        wallpaperFilterView.updateMatteContainerContentDescription();
    }

    private final void setMatteSwitchCheckedQuiet(boolean z) {
        SlidingSwitch slidingSwitch = this.matteSwitch;
        if (slidingSwitch != null) {
            slidingSwitch.setOnCheckedChangeListener(null);
        }
        SlidingSwitch slidingSwitch2 = this.matteSwitch;
        if (slidingSwitch2 != null) {
            slidingSwitch2.setChecked(z);
        }
        SlidingSwitch slidingSwitch3 = this.matteSwitch;
        if (slidingSwitch3 != null) {
            slidingSwitch3.setOnCheckedChangeListener(this.matteSwitchListener);
        }
    }

    public static final void setWallpaperFilterData$lambda$4(WallpaperFilterView wallpaperFilterView, List list) {
        int height = wallpaperFilterView.getHeight();
        RelativeLayout relativeLayout = wallpaperFilterView.matteContainer;
        int i = height - ((relativeLayout == null || relativeLayout.getVisibility() != 0) ? 0 : wallpaperFilterView.extraFilterAreaHeight);
        wallpaperFilterView.filterAreaHeight = i;
        Log.d("WallpaperFilterView", "setWallpaperFilterData: filterAreaHeight=" + i);
        wallpaperFilterView.updateExtraFilter(((WallpaperFilter) list.get(wallpaperFilterView.currentSelectTitle)).getSupportMatte());
    }

    public final void dismissMatteContainer() {
        RelativeLayout relativeLayout = this.matteContainer;
        if (relativeLayout == null || relativeLayout.getVisibility() != 0) {
            return;
        }
        AnimState animState = new AnimState("dismiss_start");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, 1.0f, new long[0]);
        Folme.useAt(this.matteContainer).state().setTo(animStateAdd).to(new AnimState("dismiss_end").add(viewProperty, 0.0f, new long[0]), TransitionKt.getAnimConfig(0.95f, 0.3f, new TransitionListener() { // from class: com.miui.keyguard.editor.view.WallpaperFilterView$dismissMatteContainer$config$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                SlidingSwitch slidingSwitch = this.this$0.matteSwitch;
                if (slidingSwitch != null) {
                    slidingSwitch.setEnabled(false);
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                RelativeLayout relativeLayout2 = this.this$0.matteContainer;
                if (relativeLayout2 != null) {
                    relativeLayout2.setVisibility(8);
                }
                SlidingSwitch slidingSwitch = this.this$0.matteSwitch;
                if (slidingSwitch != null) {
                    slidingSwitch.setFocusableInTouchMode(false);
                }
            }
        }));
    }

    public final int getCurrentFilterGroupId() {
        return this.currentSelectTitle;
    }

    public final Pair getFilterIndexById(int i) {
        Pair pair = (Pair) this.filterIdToIndex.get(Integer.valueOf(i));
        if (pair != null) {
            return pair;
        }
        Log.d("WallpaperFilterView", "filterIndex not found:" + i);
        return new Pair(0, 0);
    }

    public final void initSlidingButton() {
        SlidingSwitch slidingSwitch = new SlidingSwitch(getContext());
        this.matteSwitch = slidingSwitch;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(21);
        slidingSwitch.setLayoutParams(layoutParams);
        RelativeLayout relativeLayout = this.matteContainer;
        if (relativeLayout != null) {
            relativeLayout.addView(this.matteSwitch);
        }
        setupAccessibilityForMatte();
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.keyguard.editor.view.WallpaperFilterView$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                WallpaperFilterView.initSlidingButton$lambda$1(this.f$0, compoundButton, z);
            }
        };
        this.matteSwitchListener = onCheckedChangeListener;
        SlidingSwitch slidingSwitch2 = this.matteSwitch;
        if (slidingSwitch2 != null) {
            slidingSwitch2.setOnCheckedChangeListener(onCheckedChangeListener);
        }
        RelativeLayout relativeLayout2 = this.matteContainer;
        if (relativeLayout2 != null) {
            relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.view.WallpaperFilterView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    WallpaperFilterView.initSlidingButton$lambda$2(this.f$0, view);
                }
            });
        }
    }

    public final void initStyle(AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.WallpaperFilterView, i, 0);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.WallpaperFilterView_wallpaperHeadCancelIcon);
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.WallpaperFilterView_wallpaperHeadTitleColor, -16777216);
        int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.WallpaperFilterView_extraFilterLabelTextColorNormal, -16777216);
        int color3 = typedArrayObtainStyledAttributes.getColor(R.styleable.WallpaperFilterView_extraFilterLabelTextColorDisable, -7829368);
        typedArrayObtainStyledAttributes.recycle();
        ImageView imageView = this.headIcon;
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
        TextView textView = this.headTitle;
        if (textView != null) {
            textView.setTextColor(color);
        }
        int[][] iArr = new int[2][];
        int i2 = 0;
        while (i2 < 2) {
            iArr[i2] = new int[]{i2 == 0 ? new int[]{-16842913}[0] : new int[]{android.R.attr.state_enabled}[0]};
            i2++;
        }
        ColorStateList colorStateList = new ColorStateList(iArr, new int[]{color2, color3});
        this.extraLabelColorSelector = colorStateList;
        TextView textView2 = this.matteLabel;
        if (textView2 != null) {
            textView2.setTextColor(colorStateList);
        }
        initSlidingButton();
    }

    public final void initView() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.kg_wallpaper_filter_view, (ViewGroup) this, true);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.head_icon);
        this.headIcon = imageView;
        if (imageView != null) {
            imageView.setVisibility(0);
        }
        this.headTitle = (TextView) viewInflate.findViewById(R.id.head_title);
        this.filterIconsList = (SmoothAlignHorizontalListView) viewInflate.findViewById(R.id.filter_icons);
        this.matteContainer = (RelativeLayout) viewInflate.findViewById(R.id.matte_container);
        this.matteLabel = (TextView) viewInflate.findViewById(R.id.matte_title);
        setFocusableInTouchMode(true);
        setOrientation(1);
        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        setPadding(0, getContext().getResources().getDimensionPixelOffset(R.dimen.kg_wallpaper_filter_view_margin_top), 0, 0);
    }

    public final boolean isTalkbackEnabled(Context context) {
        Object systemService = context.getSystemService("accessibility");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.accessibility.AccessibilityManager");
        Iterator<AccessibilityServiceInfo> it = ((AccessibilityManager) systemService).getEnabledAccessibilityServiceList(1).iterator();
        while (it.hasNext()) {
            String id = it.next().getId();
            Intrinsics.checkNotNullExpressionValue(id, "getId(...)");
            if (StringsKt.contains$default((CharSequence) id, (CharSequence) "com.google.android.marvin.talkback", false, 2, (Object) null)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new AnonymousClass1(null), 3, null);
    }

    public final void onShow() {
        if (this.changeFromOutside) {
            FilterIconAdapter filterIconAdapter = this.iconAdapter;
            if (filterIconAdapter != null) {
                filterIconAdapter.performOnSelectItem(this.currentSelectFilter, false, false);
            }
            this.changeFromOutside = false;
        }
        requestFocus();
    }

    public final void resolveFilterData(List list) {
        IntWrapper matteStatus;
        this.titleResIdList.clear();
        this.filterIdToIndex.clear();
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i + 1;
            WallpaperFilter wallpaperFilter = (WallpaperFilter) it.next();
            if (wallpaperFilter.getFilterId() != Integer.MIN_VALUE) {
                this.filterIdToIndex.put(Integer.valueOf(wallpaperFilter.getFilterId()), new Pair(Integer.valueOf(i), Integer.valueOf(i)));
                this.titleResIdList.add(Integer.valueOf(wallpaperFilter.getIconNameResId()));
            }
            i = i2;
        }
        if (this.filterIdToIndex.isEmpty()) {
            SmoothAlignHorizontalListView smoothAlignHorizontalListView = this.filterIconsList;
            if (smoothAlignHorizontalListView != null) {
                smoothAlignHorizontalListView.setVisibility(8);
            }
        } else {
            SmoothAlignHorizontalListView smoothAlignHorizontalListView2 = this.filterIconsList;
            if (smoothAlignHorizontalListView2 != null) {
                smoothAlignHorizontalListView2.setVisibility(0);
            }
        }
        EditFragmentViewModel editFragmentViewModel = this.viewModel;
        updateMatte((editFragmentViewModel == null || (matteStatus = editFragmentViewModel.getMatteStatus()) == null) ? 3 : matteStatus.getIntValue());
    }

    public final void setWallpaperFilterData(@NotNull final List<WallpaperFilter> data, @NotNull BaseTemplateView templateView, @Nullable EditFragmentViewModel editFragmentViewModel) {
        SmoothAlignHorizontalListView smoothAlignHorizontalListView;
        RecyclerView.ItemAnimator itemAnimator;
        IntWrapper wallpaperFilterId;
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.viewModel = editFragmentViewModel;
        this.filterData = data;
        this.templateView = new WeakReference(templateView);
        resolveFilterData(data);
        int iIntValue = ((Number) getFilterIndexById((editFragmentViewModel == null || (wallpaperFilterId = editFragmentViewModel.getWallpaperFilterId()) == null) ? 0 : wallpaperFilterId.getIntValue()).getSecond()).intValue();
        this.currentSelectFilter = iIntValue;
        SmoothAlignHorizontalListView smoothAlignHorizontalListView2 = this.filterIconsList;
        Intrinsics.checkNotNull(smoothAlignHorizontalListView2);
        int listSelectColor = smoothAlignHorizontalListView2.getListSelectColor();
        SmoothAlignHorizontalListView smoothAlignHorizontalListView3 = this.filterIconsList;
        Intrinsics.checkNotNull(smoothAlignHorizontalListView3);
        int listNormalColor = smoothAlignHorizontalListView3.getListNormalColor();
        WallpaperFilterSelectItemCallback wallpaperFilterSelectItemCallback = new WallpaperFilterSelectItemCallback() { // from class: com.miui.keyguard.editor.view.WallpaperFilterView.setWallpaperFilterData.1
            @Override // com.miui.keyguard.editor.view.WallpaperFilterSelectItemCallback
            public void onCreatedItem(int i, View view) {
                if (!CollectionsKt.getIndices(WallpaperFilterView.this.titleResIdList).contains(i) || view == null) {
                    return;
                }
                Resources resources = WallpaperFilterView.this.getResources();
                Object obj = WallpaperFilterView.this.titleResIdList.get(i);
                Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
                view.setContentDescription(resources.getString(((Number) obj).intValue()));
            }

            @Override // com.miui.keyguard.editor.view.WallpaperFilterSelectItemCallback
            public void onSelectItem(int i, View view, boolean z) {
                EditFragmentViewModel editFragmentViewModel2;
                WallpaperFilter wallpaperFilter;
                View view2;
                if (WallpaperFilterView.this.lastSelectItemView != null && (view2 = WallpaperFilterView.this.lastSelectItemView) != null) {
                    view2.setSelected(false);
                }
                WallpaperFilterView.this.lastSelectItemView = view;
                int i2 = 1;
                if (view != null) {
                    view.setSelected(true);
                }
                WallpaperFilterView.this.currentSelectTitle = i;
                WallpaperFilterView.this.currentSelectFilter = i;
                List list = WallpaperFilterView.this.filterData;
                boolean supportMatte = list != null ? ((WallpaperFilter) list.get(i)).getSupportMatte() : false;
                FilterIconAdapter filterIconAdapter = WallpaperFilterView.this.iconAdapter;
                int itemCount = filterIconAdapter != null ? filterIconAdapter.getItemCount() : 0;
                if (i != 0) {
                    i2 = i == itemCount + (-1) ? 2 : 0;
                }
                List list2 = WallpaperFilterView.this.filterData;
                if (list2 != null && (wallpaperFilter = (WallpaperFilter) CollectionsKt.getOrNull(list2, i)) != null) {
                    int iconNameResId = wallpaperFilter.getIconNameResId();
                    WallpaperFilterView wallpaperFilterView = WallpaperFilterView.this;
                    TextView textView = wallpaperFilterView.headTitle;
                    if (textView != null) {
                        textView.setContentDescription(wallpaperFilterView.getResources().getString(R.string.kg_talkback_effect_dialog, wallpaperFilterView.getResources().getString(iconNameResId)));
                    }
                }
                Log.d("WallpaperFilterView", "filterIconsList: " + i + " " + i + " " + supportMatte + " " + itemCount + " " + i2 + " " + i);
                List list3 = WallpaperFilterView.this.filterData;
                Intrinsics.checkNotNull(list3);
                int filterId = ((WallpaperFilter) list3.get(i)).getFilterId();
                if (view != null && (editFragmentViewModel2 = WallpaperFilterView.this.viewModel) != null) {
                    EditFragmentViewModel.updateWallpaperFilterId$default(editFragmentViewModel2, filterId, false, 2, null);
                }
                WallpaperFilterView.this.updateExtraFilter(supportMatte);
            }
        };
        TextView textView = this.headTitle;
        this.iconAdapter = new FilterIconAdapter(data, iIntValue, listSelectColor, listNormalColor, wallpaperFilterSelectItemCallback, null, null, textView != null ? textView.getText() : null, 96, null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(0);
        linearLayoutManager2.setOrientation(0);
        SmoothAlignHorizontalListView smoothAlignHorizontalListView4 = this.filterIconsList;
        if (smoothAlignHorizontalListView4 != null) {
            smoothAlignHorizontalListView4.setLayoutManager(linearLayoutManager2);
        }
        SmoothAlignHorizontalListView smoothAlignHorizontalListView5 = this.filterIconsList;
        if (smoothAlignHorizontalListView5 != null) {
            smoothAlignHorizontalListView5.setAdapter(this.iconAdapter);
        }
        SmoothAlignHorizontalListView smoothAlignHorizontalListView6 = this.filterIconsList;
        if (smoothAlignHorizontalListView6 != null && (itemAnimator = smoothAlignHorizontalListView6.getItemAnimator()) != null) {
            itemAnimator.setChangeDuration(0L);
        }
        SmoothAlignHorizontalListView smoothAlignHorizontalListView7 = this.filterIconsList;
        if (smoothAlignHorizontalListView7 != null) {
            smoothAlignHorizontalListView7.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.miui.keyguard.editor.view.WallpaperFilterView.setWallpaperFilterData.2
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    FilterIconAdapter filterIconAdapter;
                    Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
                    if (i != 1 || (filterIconAdapter = WallpaperFilterView.this.iconAdapter) == null) {
                        return;
                    }
                    filterIconAdapter.onDragList();
                }
            });
        }
        FilterIconAdapter filterIconAdapter = this.iconAdapter;
        if (filterIconAdapter != null) {
            filterIconAdapter.notifyDataSetChanged();
        }
        post(new Runnable() { // from class: com.miui.keyguard.editor.view.WallpaperFilterView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                WallpaperFilterView.setWallpaperFilterData$lambda$4(this.f$0, data);
            }
        });
        if (data.isEmpty() || (smoothAlignHorizontalListView = this.filterIconsList) == null) {
            return;
        }
        smoothAlignHorizontalListView.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.view.WallpaperFilterView$setWallpaperFilterData$$inlined$postDelayed$1
            @Override // java.lang.Runnable
            public final void run() {
                FilterIconAdapter filterIconAdapter2 = this.this$0.iconAdapter;
                if (filterIconAdapter2 != null) {
                    FilterIconAdapter.performOnSelectItem$default(filterIconAdapter2, this.this$0.currentSelectFilter, false, false, 4, null);
                }
            }
        }, 80L);
    }

    public final void setupAccessibilityForMatte() {
        SlidingSwitch slidingSwitch = this.matteSwitch;
        if (slidingSwitch != null) {
            slidingSwitch.setImportantForAccessibility(2);
        }
        updateMatteContainerContentDescription();
    }

    public final void showMatteContainer() {
        RelativeLayout relativeLayout = this.matteContainer;
        if (relativeLayout != null && relativeLayout.getVisibility() == 0) {
            RelativeLayout relativeLayout2 = this.matteContainer;
            if (Intrinsics.areEqual(relativeLayout2 != null ? Float.valueOf(relativeLayout2.getAlpha()) : null, 1.0f)) {
                return;
            }
        }
        RelativeLayout relativeLayout3 = this.matteContainer;
        if (relativeLayout3 != null) {
            relativeLayout3.setVisibility(0);
        }
        SlidingSwitch slidingSwitch = this.matteSwitch;
        if (slidingSwitch != null) {
            slidingSwitch.setFocusableInTouchMode(true);
        }
        SlidingSwitch slidingSwitch2 = this.matteSwitch;
        if (slidingSwitch2 != null) {
            slidingSwitch2.setEnabled(true);
        }
        AnimState animState = new AnimState("show_start");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        Folme.useAt(this.matteContainer).state().setTo(animState.add(viewProperty, 0.0f, new long[0])).to(new AnimState("show_end").add(viewProperty, 1.0f, new long[0]), TransitionKt.getDefaultConfig());
    }

    public final void updateExtraFilter(boolean z) {
        Log.d("WallpaperFilterView", "updateExtraFilter: " + z);
        if (z) {
            showMatteContainer();
        } else {
            dismissMatteContainer();
        }
    }

    public final void updateMatte(int i) {
        SlidingSwitch slidingSwitch;
        if (i == 0) {
            TextView textView = this.matteLabel;
            if (textView != null) {
                textView.setTextColor(ViewUtil.INSTANCE.getColor(this, R.color.kg_wallpaper_filter_text_disable_color));
            }
            SlidingSwitch slidingSwitch2 = this.matteSwitch;
            if (slidingSwitch2 != null) {
                slidingSwitch2.setEnabled(false);
                return;
            }
            return;
        }
        if (i == 1) {
            TextView textView2 = this.matteLabel;
            if (textView2 != null) {
                textView2.setTextColor(this.extraLabelColorSelector);
            }
            SlidingSwitch slidingSwitch3 = this.matteSwitch;
            if (slidingSwitch3 != null) {
                slidingSwitch3.setEnabled(true);
                slidingSwitch3.setOnCheckedChangeListener(this.matteSwitchListener);
                return;
            }
            return;
        }
        if (i != 2) {
            if (i == 3 && (slidingSwitch = this.matteSwitch) != null) {
                Intrinsics.checkNotNull(slidingSwitch);
                if (slidingSwitch.isChecked()) {
                    setMatteSwitchCheckedQuiet(false);
                    return;
                }
                return;
            }
            return;
        }
        SlidingSwitch slidingSwitch4 = this.matteSwitch;
        if (slidingSwitch4 != null) {
            Intrinsics.checkNotNull(slidingSwitch4);
            if (slidingSwitch4.isChecked()) {
                return;
            }
            setMatteSwitchCheckedQuiet(true);
        }
    }

    public final void updateMatteContainerContentDescription() {
        String string;
        CharSequence text;
        RelativeLayout relativeLayout = this.matteContainer;
        if (relativeLayout != null) {
            String switchState = getSwitchState();
            TextView textView = this.matteLabel;
            if (textView == null || (text = textView.getText()) == null || (string = text.toString()) == null) {
                string = "";
            }
            relativeLayout.setContentDescription(switchState + " " + string + " " + getContext().getString(R.string.kg_talkback_double_tap_to_activate));
        }
    }

    public final void updateWallpaperFilterId(int i) {
        WallpaperFilter wallpaperFilter;
        int iIntValue = ((Number) getFilterIndexById(i).getSecond()).intValue();
        int i2 = this.currentSelectFilter;
        if (iIntValue != i2) {
            Log.d("WallpaperFilterView", "updateWallpaperFilterId: different filter index: " + i2 + " to " + iIntValue + " filterId=" + i);
            this.changeFromOutside = true;
            this.currentSelectFilter = iIntValue;
            List list = this.filterData;
            this.currentSelectTitle = (list == null || (wallpaperFilter = (WallpaperFilter) list.get(iIntValue)) == null) ? 0 : wallpaperFilter.getFilterId();
            FilterIconAdapter filterIconAdapter = this.iconAdapter;
            if (filterIconAdapter != null) {
                filterIconAdapter.setCurrentIcon(this.currentSelectFilter);
            }
        }
    }
}
