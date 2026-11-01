package com.miui.keyguard.editor.edit.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.viewpager2.widget.ViewPager2;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.ActivityUIAdapter;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.TalkBackUtil;
import com.miui.keyguard.editor.utils.ViewStylerKt;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MiShadowUtils;
import miuix.miuixbasewidget.widget.FilterSortView;
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
/* JADX INFO: compiled from: EditorDialogTitleView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class EditorDialogTitleView extends LinearLayout {
    public static final Companion Companion = new Companion(null);
    public static Boolean sLastKnownLightTheme;
    public final Drawable cancelIcon;
    public final ImageView closeView;
    public ViewPager2 contentViewPager;
    public int curSelectIndex;
    public final View headerView;
    public FrameLayout mTitleFrameLayout;
    public WeakReference onCloseDialogListener;
    public ViewPager2.OnPageChangeCallback pageChangeCallback;
    public final FilterSortView slidingTitle;
    public FilterSortView.TabView[] tabViewList;

    /* JADX INFO: compiled from: EditorDialogTitleView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: EditorDialogTitleView.kt */
    @Metadata
    public interface OnCloseDialogListener {
        void closeDialog();
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.view.EditorDialogTitleView$onAttachedToWindow$1, reason: invalid class name */
    /* JADX INFO: compiled from: EditorDialogTitleView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.view.EditorDialogTitleView$onAttachedToWindow$1", f = "EditorDialogTitleView.kt", l = {257}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EditorDialogTitleView.this.new AnonymousClass1(continuation);
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
                if (DelayKt.delay(500L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            EditorDialogTitleView.this.closeView.requestFocus();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.view.EditorDialogTitleView$sendAccessibilityEvent$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EditorDialogTitleView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.view.EditorDialogTitleView$sendAccessibilityEvent$1", f = "EditorDialogTitleView.kt", l = {183}, m = "invokeSuspend")
    public static final class C01221 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01221(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EditorDialogTitleView.this.new C01221(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01221) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(500L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            ImageView imageView = EditorDialogTitleView.this.closeView;
            if (imageView != null) {
                Boxing.boxBoolean(imageView.requestFocus());
            }
            ImageView imageView2 = EditorDialogTitleView.this.closeView;
            if (imageView2 != null) {
                imageView2.sendAccessibilityEvent(128);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public EditorDialogTitleView(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public EditorDialogTitleView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.tabViewList = new FilterSortView.TabView[]{null, null};
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.WallpaperFilterView, R.attr.wallpaperFilterViewStyle, 0);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.WallpaperFilterView_wallpaperHeadCancelIcon);
        if (drawable == null) {
            drawable = getResources().getDrawable(R.drawable.miuix_action_icon_cancel_light);
            Intrinsics.checkNotNullExpressionValue(drawable, "getDrawable(...)");
        }
        this.cancelIcon = drawable;
        typedArrayObtainStyledAttributes.recycle();
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setId(R.id.kg_editor_dialog_title_container);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.kg_editor_dialog_header, (ViewGroup) frameLayout, false);
        this.headerView = viewInflate;
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.cancel_icon);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.view.EditorDialogTitleView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditorDialogTitleView.mTitleFrameLayout$lambda$3$lambda$1$lambda$0(this.f$0, view);
            }
        });
        TalkBackUtil talkBackUtil = TalkBackUtil.INSTANCE;
        Object systemService = context.getSystemService("accessibility");
        imageView.setFocusableInTouchMode(talkBackUtil.isTalkBackEnabled(systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null));
        this.closeView = imageView;
        FilterSortView filterSortView = (FilterSortView) viewInflate.findViewById(R.id.sliding_title);
        this.slidingTitle = filterSortView;
        if (!ViewStylerKt.canUseBlur(context)) {
            MiShadowUtils.setShadowConfig(filterSortView, new MaterialConfig.ShadowConfig(1493172224, 0.0f, frameLayout.getResources().getDimension(R.dimen.kg_filter_sort_view_shadow_offset_y), frameLayout.getResources().getDimension(R.dimen.kg_filter_sort_view_shadow_radius), 0.5f));
            frameLayout.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.view.EditorDialogTitleView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    EditorDialogTitleView.mTitleFrameLayout$lambda$3$lambda$2(this.f$0);
                }
            });
        }
        if (ActivityUIAdapter.Companion.isLightTheme(context)) {
            imageView.setImageDrawable(frameLayout.getResources().getDrawable(R.drawable.miuix_action_icon_cancel_light));
        } else {
            imageView.setImageDrawable(frameLayout.getResources().getDrawable(R.drawable.miuix_action_icon_cancel_dark));
        }
        frameLayout.addView(viewInflate);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        layoutParams.topMargin = (deviceUtil.isPad() || deviceUtil.isFoldLargeScreen(context)) ? getResources().getDimensionPixelSize(R.dimen.kg_editor_dialog_header_margin_top) : 0;
        frameLayout.setLayoutParams(layoutParams);
        this.mTitleFrameLayout = frameLayout;
        setOrientation(1);
        addView(this.mTitleFrameLayout);
    }

    public /* synthetic */ EditorDialogTitleView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static final void mTitleFrameLayout$lambda$3$lambda$1$lambda$0(EditorDialogTitleView editorDialogTitleView, View view) {
        OnCloseDialogListener onCloseDialogListener;
        WeakReference weakReference = editorDialogTitleView.onCloseDialogListener;
        if (weakReference == null || (onCloseDialogListener = (OnCloseDialogListener) weakReference.get()) == null) {
            return;
        }
        onCloseDialogListener.closeDialog();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x000f, code lost:
    
        r2 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void mTitleFrameLayout$lambda$3$lambda$2(com.miui.keyguard.editor.edit.view.EditorDialogTitleView r2) {
        /*
            miuix.miuixbasewidget.widget.FilterSortView r2 = r2.slidingTitle
            android.view.ViewParent r2 = r2.getParent()
            boolean r0 = r2 instanceof android.view.ViewGroup
            r1 = 0
            if (r0 == 0) goto Le
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
            goto Lf
        Le:
            r2 = r1
        Lf:
            if (r2 == 0) goto L23
            r0 = 0
            r2.setClipChildren(r0)
            r2.setClipToPadding(r0)
            android.view.ViewParent r2 = r2.getParent()
            boolean r0 = r2 instanceof android.view.ViewGroup
            if (r0 == 0) goto Le
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
            goto Lf
        L23:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.edit.view.EditorDialogTitleView.mTitleFrameLayout$lambda$3$lambda$2(com.miui.keyguard.editor.edit.view.EditorDialogTitleView):void");
    }

    public static /* synthetic */ void setTitle$default(EditorDialogTitleView editorDialogTitleView, String str, String str2, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str2 = null;
        }
        if ((i2 & 4) != 0) {
            i = 1;
        }
        editorDialogTitleView.setTitle(str, str2, i);
    }

    public static /* synthetic */ void setViewPager$default(EditorDialogTitleView editorDialogTitleView, ViewPager2 viewPager2, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 1;
        }
        editorDialogTitleView.setViewPager(viewPager2, i);
    }

    public static /* synthetic */ void switchTarget$default(EditorDialogTitleView editorDialogTitleView, int i, boolean z, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        editorDialogTitleView.switchTarget(i, z, z2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        LifecycleCoroutineScope lifecycleScope;
        super.onAttachedToWindow();
        ImageView imageView = this.closeView;
        TalkBackUtil talkBackUtil = TalkBackUtil.INSTANCE;
        Object systemService = getContext().getSystemService("accessibility");
        imageView.setFocusableInTouchMode(talkBackUtil.isTalkBackEnabled(systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null));
        if (!DeviceUtil.INSTANCE.isPhone()) {
            Context context = getContext();
            EditorActivity editorActivity = context instanceof EditorActivity ? (EditorActivity) context : null;
            if (editorActivity != null && (lifecycleScope = LifecycleOwnerKt.getLifecycleScope(editorActivity)) != null) {
                BuildersKt__Builders_commonKt.launch$default(lifecycleScope, Dispatchers.getMain(), null, new AnonymousClass1(null), 2, null);
            }
        }
        ActivityUIAdapter.Companion companion = ActivityUIAdapter.Companion;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        if (Intrinsics.areEqual(sLastKnownLightTheme, Boolean.valueOf(companion.isLightTheme(context2)))) {
            return;
        }
        refreshThemeDependentViews();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        ViewPager2 viewPager2;
        ViewPager2.OnPageChangeCallback onPageChangeCallback = this.pageChangeCallback;
        if (onPageChangeCallback != null && (viewPager2 = this.contentViewPager) != null) {
            viewPager2.unregisterOnPageChangeCallback(onPageChangeCallback);
        }
        this.pageChangeCallback = null;
        super.onDetachedFromWindow();
    }

    public final void refreshThemeDependentViews() {
        ColorStateList colorStateList;
        TextView textView;
        ActivityUIAdapter.Companion companion = ActivityUIAdapter.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        boolean zIsLightTheme = companion.isLightTheme(context);
        if (zIsLightTheme) {
            this.closeView.setImageDrawable(getResources().getDrawable(R.drawable.miuix_action_icon_cancel_light, getContext().getTheme()));
        } else {
            this.closeView.setImageDrawable(getResources().getDrawable(R.drawable.miuix_action_icon_cancel_dark, getContext().getTheme()));
        }
        TypedValue typedValue = new TypedValue();
        if (getContext().getTheme().resolveAttribute(R.attr.filterSortTabViewTextColor, typedValue, true)) {
            int i = typedValue.type;
            int defaultColor = ((i < 28 || i > 31) && (colorStateList = getResources().getColorStateList(typedValue.resourceId, getContext().getTheme())) != null) ? colorStateList.getDefaultColor() : typedValue.data;
            for (FilterSortView.TabView tabView : this.tabViewList) {
                if (tabView != null && (textView = (TextView) tabView.findViewById(android.R.id.text1)) != null) {
                    textView.setTextColor(defaultColor);
                }
            }
        }
        sLastKnownLightTheme = Boolean.valueOf(zIsLightTheme);
    }

    public final void sendAccessibilityEvent() {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (!deviceUtil.isPad()) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (!deviceUtil.isFoldLargeScreen(context)) {
                return;
            }
        }
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context2), Dispatchers.getMain(), null, new C01221(null), 2, null);
    }

    public final void setOnCloseDialogListener(@NotNull OnCloseDialogListener onCloseDialogListener) {
        Intrinsics.checkNotNullParameter(onCloseDialogListener, "onCloseDialogListener");
        this.onCloseDialogListener = new WeakReference(onCloseDialogListener);
    }

    public final void setTitle(@NotNull String nameFirst, @Nullable String str, int i) {
        Intrinsics.checkNotNullParameter(nameFirst, "nameFirst");
        this.curSelectIndex = i;
        this.headerView.setVisibility(0);
        this.slidingTitle.setVisibility(0);
        this.tabViewList[0] = this.slidingTitle.addTab(nameFirst);
        FilterSortView.TabView[] tabViewArr = this.tabViewList;
        FilterSortView filterSortView = this.slidingTitle;
        if (str == null) {
            str = getResources().getString(R.string.kg_title_font_name);
        }
        tabViewArr[1] = filterSortView.addTab(str);
        this.slidingTitle.setFilteredTab(this.curSelectIndex);
        FilterSortView.TabView tabView = this.tabViewList[0];
        if (tabView != null) {
            setupOnClickListener(tabView, 0);
        }
        FilterSortView.TabView tabView2 = this.tabViewList[1];
        if (tabView2 != null) {
            setupOnClickListener(tabView2, 1);
        }
        updateTabViewTextColor(i);
    }

    public final void setTitleVisible(boolean z) {
        if (z) {
            this.headerView.setVisibility(0);
            this.slidingTitle.setVisibility(0);
            this.closeView.setVisibility(0);
        } else {
            this.headerView.setVisibility(8);
            this.slidingTitle.setVisibility(8);
            this.closeView.setVisibility(8);
        }
    }

    public final void setViewPager(@NotNull ViewPager2 viewPager, int i) {
        Intrinsics.checkNotNullParameter(viewPager, "viewPager");
        this.contentViewPager = viewPager;
        ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.miui.keyguard.editor.edit.view.EditorDialogTitleView.setViewPager.1
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i2) {
                super.onPageSelected(i2);
                if (EditorDialogTitleView.this.curSelectIndex == i2 || i2 < 0 || i2 >= EditorDialogTitleView.this.tabViewList.length) {
                    return;
                }
                EditorDialogTitleView.this.curSelectIndex = i2;
                EditorDialogTitleView.this.updateTabViewTextColor(i2);
                EditorDialogTitleView.this.slidingTitle.setFilteredTab(i2);
            }
        };
        this.pageChangeCallback = onPageChangeCallback;
        Intrinsics.checkNotNull(onPageChangeCallback);
        viewPager.registerOnPageChangeCallback(onPageChangeCallback);
        switchTarget(i, true, false);
    }

    public final void setupOnClickListener(FilterSortView.TabView tabView, final int i) {
        tabView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.view.EditorDialogTitleView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditorDialogTitleView.switchTarget$default(this.f$0, i, false, false, 6, null);
            }
        });
    }

    public final void switchTarget(int i, boolean z, boolean z2) {
        if (this.curSelectIndex != i || z) {
            updateTabViewTextColor(i);
            this.curSelectIndex = i;
            ViewPager2 viewPager2 = this.contentViewPager;
            if (viewPager2 != null) {
                viewPager2.setCurrentItem(i, z2);
            }
        }
    }

    public final void switchTargetWithTitle(int i) {
        this.slidingTitle.setFilteredTab(i);
        if (this.curSelectIndex == i) {
            return;
        }
        switchTarget(i, true, false);
    }

    public final void updateTabViewTextColor(int i) {
        TextView textView;
        TextView textView2;
        FilterSortView.TabView tabView = this.tabViewList[this.curSelectIndex];
        if (tabView != null && (textView2 = (TextView) tabView.findViewById(android.R.id.text1)) != null) {
            textView2.setActivated(false);
        }
        FilterSortView.TabView tabView2 = this.tabViewList[i];
        if (tabView2 == null || (textView = (TextView) tabView2.findViewById(android.R.id.text1)) == null) {
            return;
        }
        textView.setActivated(true);
    }
}
