package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentActivity;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TemplateViewFactory;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.TemplateSource;
import com.miui.keyguard.editor.utils.ViewUtil;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;

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
public final class CombineEditAdapter {
    public static final Companion Companion = new Companion(null);
    public RectF combineScreen;
    public FollowSystemListener followSystemListener;
    public CombineEditParam param;
    public int templateGap;
    public int templateMarginOffset;
    public int templateOffsetY;
    public float templateScale;

    /* JADX INFO: compiled from: CombineEditView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public CombineEditAdapter(CombineEditParam param) {
        Intrinsics.checkNotNullParameter(param, "param");
        this.param = param;
        this.combineScreen = new RectF(0.0f, 0.0f, this.param.getScreenSize().x, this.param.getScreenSize().y);
        initDimen();
        updateCombineTemplateRect();
    }

    public static /* synthetic */ void initEditView$default(CombineEditAdapter combineEditAdapter, View view, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        combineEditAdapter.initEditView(view, z);
    }

    public static final void initializeTemplateView$lambda$14$lambda$13(BaseTemplateView baseTemplateView, CommonConfig commonConfig) {
        WallpaperEditor.Companion companion = WallpaperEditor.Companion;
        EditFragment editFragment = baseTemplateView.getEditFragment();
        Intrinsics.checkNotNull(editFragment);
        WallpaperEditor.Companion.launchThemePicker$default(companion, editFragment, commonConfig.getLockscreenInfo().getClockInfo().getTemplateId(), true, false, null, 24, null);
    }

    public static final Unit onBindViewHolder$lambda$7$lambda$6(CombineEditAdapter combineEditAdapter) {
        EditFragment editFragment = (EditFragment) combineEditAdapter.param.getEditFragmentWeak().get();
        FragmentActivity activity = editFragment != null ? editFragment.getActivity() : null;
        EditorActivity editorActivity = activity instanceof EditorActivity ? (EditorActivity) activity : null;
        if (editorActivity != null) {
            editorActivity.isDirectFinishWhenBackPress();
        }
        return Unit.INSTANCE;
    }

    public static final void onBindViewHolder$lambda$9$lambda$8(BaseTemplateView baseTemplateView) {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        WallpaperEditor.Companion companion = WallpaperEditor.Companion;
        EditFragment editFragment = baseTemplateView.getEditFragment();
        Intrinsics.checkNotNull(editFragment);
        CommonConfig templateConfig = baseTemplateView.getTemplateConfig();
        String templateId = (templateConfig == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) ? null : clockInfo.getTemplateId();
        Intrinsics.checkNotNull(templateId);
        WallpaperEditor.Companion.launchThemePicker$default(companion, editFragment, templateId, false, false, null, 24, null);
    }

    public final RectF getCombineScreen() {
        return this.combineScreen;
    }

    public final int getItemCount() {
        return 2;
    }

    public final int getItemViewType(int i) {
        return i;
    }

    public final CombineEditParam getParam() {
        return this.param;
    }

    public final int getTemplateMarginOffset() {
        return this.templateMarginOffset;
    }

    public final float getTemplateScale() {
        return this.templateScale;
    }

    public final Pair getTemplateScaleCenter() {
        return new Pair(Float.valueOf(this.param.getScreenSize().x / 2.0f), Float.valueOf((this.param.getScreenSize().y / 2.0f) + this.templateOffsetY));
    }

    public final void initDimen() {
        float f;
        Resources resources;
        Resources resources2;
        Resources resources3;
        Resources resources4;
        EditFragment editFragment = (EditFragment) this.param.getEditFragmentWeak().get();
        Context context = editFragment != null ? editFragment.getContext() : null;
        this.templateScale = context != null ? ResourcesProvider.INSTANCE.provideTemplateEditScale(context) : 1.0f;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            f = (context == null || (resources4 = context.getResources()) == null) ? 0.033755273f : resources4.getFloat(R.dimen.kg_pad_template_gap_rate);
        } else if (context == null || !deviceUtil.isFoldLargeScreen(context)) {
            f = (context == null || (resources = context.getResources()) == null) ? 0.06122449f : resources.getFloat(R.dimen.kg_phone_template_gap_rate);
        } else {
            f = (context == null || (resources2 = context.getResources()) == null) ? 0.03448276f : resources2.getFloat(R.dimen.kg_fold_large_screen_template_gap_rate);
        }
        this.templateGap = (int) (f * Math.min(this.param.getScreenSize().x, this.param.getScreenSize().y));
        this.templateOffsetY = (context == null || (resources3 = context.getResources()) == null) ? 0 : resources3.getInteger(R.integer.kg_combine_edit_template_offset_y);
        int iAbs = Math.abs(MathKt.roundToInt((-(this.param.getScreenSize().x * (1 - this.templateScale))) + this.templateGap));
        this.templateMarginOffset = iAbs;
        Log.d("CombineEditView", "CombineEditAdapter templateScale=" + this.templateScale + " templateGap=" + this.templateGap + " templateOffsetY=" + this.templateOffsetY + " templateMarginOffset=" + iAbs);
    }

    public final void initEditView(View view, boolean z) {
        if (view != null) {
            view.setLayoutParams(new FrameLayout.LayoutParams(this.param.getScreenSize().x, this.param.getScreenSize().y));
            Pair templateScaleCenter = getTemplateScaleCenter();
            view.setPivotX(((Number) templateScaleCenter.getFirst()).floatValue());
            view.setPivotY(((Number) templateScaleCenter.getSecond()).floatValue());
            if (z) {
                view.setScaleX(this.templateScale);
                view.setScaleY(this.templateScale);
            }
        }
    }

    public final void initializeTemplateView(ViewGroup viewGroup, final BaseTemplateView baseTemplateView, EditFragment.TemplateData templateData, final CommonConfig commonConfig, long j) {
        FragmentActivity activity;
        Intent intent;
        baseTemplateView.setPresetData(Boolean.valueOf(TemplateSource.INSTANCE.isFromPresetTemplate(j)));
        baseTemplateView.setSourceType(1);
        baseTemplateView.setGalleryOpened(templateData.isGalleryOpened());
        baseTemplateView.setAutoDownload(templateData.isAutoDownload());
        baseTemplateView.setApplyAnimationPerformer(baseTemplateView.getEditFragment());
        EditFragment editFragment = baseTemplateView.getEditFragment();
        baseTemplateView.isCallingFromKeyguard((editFragment == null || (activity = editFragment.getActivity()) == null || (intent = activity.getIntent()) == null || intent.getIntExtra("openSource", -1) != 0) ? false : true);
        baseTemplateView.setTemplateSource(templateData.getTemplateSource());
        baseTemplateView.setWallpaperPickerLauncher(new WallpaperEditor.WallpaperPickerLauncher() { // from class: com.miui.keyguard.editor.edit.base.CombineEditAdapter$$ExternalSyntheticLambda2
            @Override // com.miui.keyguard.editor.edit.base.WallpaperEditor.WallpaperPickerLauncher
            public final void launch() {
                CombineEditAdapter.initializeTemplateView$lambda$14$lambda$13(baseTemplateView, commonConfig);
            }
        });
        baseTemplateView.setEditModeChangedListener(baseTemplateView.getEditFragment());
        EditorApplicationProxy.Companion companion = EditorApplicationProxy.Companion;
        if (companion.getEDITOR_DEBUG()) {
            Trace.beginSection("BaseTemplateView:initializePrimaryButtonBar");
        }
        baseTemplateView.initializePrimaryButtonBar(viewGroup);
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
        }
        baseTemplateView.loadTemplate(commonConfig);
        EditFragment editFragment2 = baseTemplateView.getEditFragment();
        baseTemplateView.loadOriginTempConfig(editFragment2 != null ? editFragment2.getOriginTempConfig() : null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onBindViewHolder(ViewGroup parent, final BaseTemplateView templateView, int i) throws Throwable {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        if (i != 0) {
            if (i != 1) {
                throw new Throwable("bind error view type: " + i);
            }
            templateView.setSourceType(1);
            templateView.setWallpaperPickerLauncher(new WallpaperEditor.WallpaperPickerLauncher() { // from class: com.miui.keyguard.editor.edit.base.CombineEditAdapter$$ExternalSyntheticLambda1
                @Override // com.miui.keyguard.editor.edit.base.WallpaperEditor.WallpaperPickerLauncher
                public final void launch() {
                    CombineEditAdapter.onBindViewHolder$lambda$9$lambda$8(templateView);
                }
            });
            templateView.initializePrimaryButtonBar(parent);
            templateView.loadTemplate(this.param.getTemplateConfig());
            this.followSystemListener = templateView instanceof FollowSystemListener ? (FollowSystemListener) templateView : null;
            return;
        }
        EditFragment.TemplateData templateData = this.param.getTemplateData();
        Intrinsics.checkNotNull(templateData);
        CommonConfig templateConfig = this.param.getTemplateConfig();
        Intrinsics.checkNotNull(templateConfig);
        Long templateSource = this.param.getTemplateSource();
        Intrinsics.checkNotNull(templateSource);
        initializeTemplateView(parent, templateView, templateData, templateConfig, templateSource.longValue());
        templateView.setFollowSystemListener(this.followSystemListener);
        IWallpaperLayer wallpaperLayer = templateView.getWallpaperLayer();
        Matrix wallpaperMatrix = wallpaperLayer != null ? wallpaperLayer.getWallpaperMatrix() : null;
        CommonConfig templateConfig2 = this.param.getTemplateConfig();
        Intrinsics.checkNotNull(templateConfig2);
        WallpaperInfo wallpaperInfo = templateConfig2.getLockscreenInfo().getWallpaperInfo();
        Matrix matrixObtainGlassMatrix = templateView.obtainGlassMatrix(wallpaperMatrix, wallpaperInfo != null ? wallpaperInfo.getMagicType() : 0);
        FollowSystemListener followSystemListener = this.followSystemListener;
        if (followSystemListener != null) {
            followSystemListener.trigger(3, matrixObtainGlassMatrix);
        }
        templateView.setOnCancelDialogNegativeClick(new Function0() { // from class: com.miui.keyguard.editor.edit.base.CombineEditAdapter$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CombineEditAdapter.onBindViewHolder$lambda$7$lambda$6(this.f$0);
            }
        });
    }

    public final EditViewHolder onCreateViewHolder(ViewGroup parent, int i) throws Throwable {
        Intrinsics.checkNotNullParameter(parent, "parent");
        BaseTemplateView baseTemplateView = null;
        if (i == 0) {
            TemplateViewFactory templateViewFactory = TemplateViewFactory.INSTANCE;
            Context context = parent.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            BaseTemplateView baseTemplateViewCreate = templateViewFactory.create(context);
            if (baseTemplateViewCreate != null) {
                baseTemplateViewCreate.setEditFragment((EditFragment) this.param.getEditFragmentWeak().get());
                initEditView$default(this, baseTemplateViewCreate, false, 2, null);
                parent.addView(baseTemplateViewCreate);
                baseTemplateView = baseTemplateViewCreate;
            }
        } else {
            if (i != 1) {
                throw new Throwable("create error view type: " + i);
            }
            Context context2 = parent.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            HomeTemplateView homeTemplateView = new HomeTemplateView(context2);
            homeTemplateView.setEditFragment((EditFragment) this.param.getEditFragmentWeak().get());
            initEditView$default(this, homeTemplateView, false, 2, null);
            ViewGroup.LayoutParams layoutParams = homeTemplateView.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
            int marginStart = layoutParams2.getMarginStart();
            boolean zIsRtl = ViewUtil.INSTANCE.isRtl(homeTemplateView);
            int i2 = this.templateMarginOffset;
            if (!zIsRtl) {
                i2 = -i2;
            }
            layoutParams2.setMarginStart(marginStart + i2);
            parent.addView(homeTemplateView);
            baseTemplateView = homeTemplateView;
        }
        if (i == 0 && baseTemplateView != null) {
            baseTemplateView.initAIWallPaperManager();
        }
        Intrinsics.checkNotNull(baseTemplateView);
        return new EditViewHolder(baseTemplateView, i);
    }

    public final void onScreenSizeChanged(Point screenSize, EditViewHolder[] holders, int i) {
        Intrinsics.checkNotNullParameter(screenSize, "screenSize");
        Intrinsics.checkNotNullParameter(holders, "holders");
        this.param.setScreenSize(screenSize);
        initDimen();
        updateCombineTemplateRect();
        int length = holders.length;
        for (int i2 = 0; i2 < length; i2++) {
            EditViewHolder editViewHolder = holders[i2];
            BaseTemplateView itemView = editViewHolder != null ? editViewHolder.getItemView() : null;
            initEditView(itemView, i != 1);
            if (editViewHolder != null && editViewHolder.getViewType() == 1) {
                ViewGroup.LayoutParams layoutParams = itemView != null ? itemView.getLayoutParams() : null;
                Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
                layoutParams2.setMarginStart(layoutParams2.getMarginStart() + (ViewUtil.INSTANCE.isRtl(itemView) ? this.templateMarginOffset : -this.templateMarginOffset));
            }
        }
    }

    public final void updateCombineTemplateRect() {
        this.combineScreen.set(0.0f, 0.0f, this.param.getScreenSize().x, this.param.getScreenSize().y);
        Matrix matrix = new Matrix();
        Pair templateScaleCenter = getTemplateScaleCenter();
        float f = this.templateScale;
        matrix.postScale(f, f, ((Number) templateScaleCenter.getFirst()).floatValue(), ((Number) templateScaleCenter.getSecond()).floatValue());
        matrix.mapRect(this.combineScreen);
    }
}
