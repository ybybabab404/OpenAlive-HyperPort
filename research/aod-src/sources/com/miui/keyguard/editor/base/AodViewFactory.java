package com.miui.keyguard.editor.base;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.miui.clock.MiuiClockView;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.color.picker.ColorPickerCache;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.DualClockManager;
import com.miui.keyguard.editor.utils.ViewUtil;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AodViewFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class AodViewFactory {
    public static final AodViewFactory INSTANCE = new AodViewFactory();

    public static /* synthetic */ AodClockViews createAodViews$default(AodViewFactory aodViewFactory, Context context, TemplateConfig templateConfig, FrameLayout.LayoutParams layoutParams, String str, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            layoutParams = new FrameLayout.LayoutParams(-1, -1);
        }
        return aodViewFactory.createAodViews(context, templateConfig, layoutParams, (i & 8) != 0 ? null : str, (i & 16) != 0 ? null : function1);
    }

    public static final Unit createDoodleClockView$lambda$4(Context context, AodClockViews createAodViews) {
        Intrinsics.checkNotNullParameter(createAodViews, "$this$createAodViews");
        ViewUtil.setMarginTop((View) createAodViews.getClockViews().get(0), context.getResources().getDimensionPixelSize(R.dimen.kg_doodle_clock_aod_clock_margin_top));
        return Unit.INSTANCE;
    }

    public static final Unit createRhombusClockView$lambda$2(final Context context, AodClockViews createAodViews) {
        Intrinsics.checkNotNullParameter(createAodViews, "$this$createAodViews");
        final MiuiClockView miuiClockView = (MiuiClockView) createAodViews.getClockViews().get(0);
        View iClockView = miuiClockView.getIClockView(ClockViewType.FULL_DATE);
        if (iClockView != null) {
            iClockView.setAlpha(0.0f);
        }
        View iClockView2 = miuiClockView.getIClockView(ClockViewType.FULL_WEEK);
        if (iClockView2 != null) {
            iClockView2.setAlpha(0.0f);
        }
        miuiClockView.post(new Runnable() { // from class: com.miui.keyguard.editor.base.AodViewFactory$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                AodViewFactory.createRhombusClockView$lambda$2$lambda$1(miuiClockView, context);
            }
        });
        return Unit.INSTANCE;
    }

    public static final void createRhombusClockView$lambda$2$lambda$1(MiuiClockView miuiClockView, Context context) {
        miuiClockView.setTranslationY(context.getResources().getDimension(R.dimen.kg_rhombus_clock_aod_translate_y));
        miuiClockView.setPivotY(0.0f);
        miuiClockView.setScaleX(context.getResources().getFloat(R.dimen.kg_rhombus_clock_aod_scale));
        miuiClockView.setScaleY(miuiClockView.getScaleX());
    }

    public static final Unit createSmartFrameClockView$lambda$6(AodClockViews createAodViews) {
        Intrinsics.checkNotNullParameter(createAodViews, "$this$createAodViews");
        View rootView = createAodViews.getRootView();
        if (rootView != null) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
            layoutParams.gravity = 17;
            rootView.setLayoutParams(layoutParams);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final AodClockViews create(Context context, CommonConfig commonConfig) {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        Intrinsics.checkNotNullParameter(context, "context");
        String templateId = (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) ? null : clockInfo.getTemplateId();
        if (templateId != null) {
            switch (templateId.hashCode()) {
                case -1326135015:
                    if (templateId.equals("doodle")) {
                        return createDoodleClockView(context, commonConfig.getLockscreenInfo());
                    }
                    break;
                case -567074186:
                    if (templateId.equals("magazine_a")) {
                        return createMagazineAClockView(context, commonConfig.getLockscreenInfo());
                    }
                    break;
                case -567074185:
                    if (templateId.equals("magazine_b")) {
                        return createMagazineBClockView(context, commonConfig.getLockscreenInfo());
                    }
                    break;
                case -567074184:
                    if (templateId.equals("magazine_c")) {
                        return createMagazineCClockView(context, commonConfig.getLockscreenInfo());
                    }
                    break;
                case -159946889:
                    if (templateId.equals("smart_frame")) {
                        return createSmartFrameClockView(context, commonConfig.getLockscreenInfo());
                    }
                    break;
                case -138709961:
                    if (templateId.equals("classic_max")) {
                        return createClassicMaxClockView(context, commonConfig.getLockscreenInfo());
                    }
                    break;
                case -4941529:
                    if (templateId.equals("classic_plus")) {
                        return createClassicPlusClockView(context, commonConfig.getLockscreenInfo());
                    }
                    break;
                case 853620882:
                    if (templateId.equals("classic")) {
                        return createClassicClockView(context, commonConfig.getLockscreenInfo());
                    }
                    break;
                case 1179492204:
                    if (templateId.equals("rhombus")) {
                        return createRhombusClockView(context, commonConfig.getLockscreenInfo());
                    }
                    break;
            }
        }
        if (commonConfig != null) {
            return createAodViews$default(INSTANCE, context, commonConfig.getLockscreenInfo(), null, null, null, 28, null);
        }
        return null;
    }

    public final AodClockViews createAodViews(Context context, TemplateConfig templateConfig, FrameLayout.LayoutParams layoutParams, String str, Function1 function1) {
        MiuiClockView miuiClockView = new MiuiClockView(context);
        AodViewFactory aodViewFactory = INSTANCE;
        boolean zIsDualClock = miuiClockView.isDualClock();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        miuiClockView.setLayoutParams(aodViewFactory.getMarginLayoutParams(context, zIsDualClock, layoutParams, templateConfig.getClockInfo().getTemplateId()));
        if (str == null) {
            str = templateConfig.getClockInfo().getTemplateId();
        }
        ClockBean clockBean = new ClockBean(str);
        clockBean.setStyle(templateConfig.getClockInfo().getStyle());
        clockBean.setPrimaryColor(templateConfig.getClockInfo().getPrimaryColor());
        clockBean.setSecondaryColor(templateConfig.getClockInfo().getSecondaryColor());
        clockBean.setClassicLine1(templateConfig.getClockInfo().getClassicLine1());
        clockBean.setClassicLine2(templateConfig.getClockInfo().getClassicLine2());
        clockBean.setClassicLine3(templateConfig.getClockInfo().getClassicLine3());
        clockBean.setClassicLine4(templateConfig.getClockInfo().getClassicLine4());
        clockBean.setClassicLine5(templateConfig.getClockInfo().getClassicLine5());
        clockBean.setClassicSignature(templateConfig.getClockInfo().getClassicSignature());
        clockBean.setEnableDiffusion(templateConfig.getClockInfo().getEnableDiffusion());
        clockBean.setAutoPrimaryColor(templateConfig.getClockInfo().isAutoPrimaryColor());
        clockBean.setAutoSecondaryColor(templateConfig.getClockInfo().isAutoSecondaryColor());
        clockBean.setDiffHourMinuteColor(templateConfig.getClockInfo().isDiffHourMinuteColor());
        miuiClockView.init(clockBean, 1, false);
        ColorData colorDataAodColorData = ColorPickerCache.INSTANCE.aodColorData();
        if (colorDataAodColorData != null) {
            miuiClockView.setClockPalette(0, colorDataAodColorData.getDarkClockArea(), colorDataAodColorData.getClockPalette());
        } else {
            Log.w("AodViewFactory", "aodColorData is null");
        }
        AodClockViews aodClockViews = new AodClockViews(null, null, 3, null);
        aodClockViews.getClockViews().add(miuiClockView);
        aodClockViews.setRootView(miuiClockView);
        if (!DualClockManager.isDualClockAvailable(context) && function1 != null) {
            function1.invoke(aodClockViews);
        }
        return aodClockViews;
    }

    public final AodClockViews createClassicClockView(Context context, TemplateConfig templateConfig) {
        return createAodViews$default(this, context, templateConfig, null, null, null, 28, null);
    }

    public final AodClockViews createClassicMaxClockView(Context context, TemplateConfig templateConfig) {
        return createAodViews$default(this, context, templateConfig, null, null, null, 28, null);
    }

    public final AodClockViews createClassicPlusClockView(Context context, TemplateConfig templateConfig) {
        return createAodViews$default(this, context, templateConfig, null, null, null, 28, null);
    }

    public final AodClockViews createDoodleClockView(final Context context, TemplateConfig templateConfig) {
        return createAodViews$default(this, context, templateConfig, null, "doodle_single", new Function1() { // from class: com.miui.keyguard.editor.base.AodViewFactory$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AodViewFactory.createDoodleClockView$lambda$4(context, (AodClockViews) obj);
            }
        }, 4, null);
    }

    public final AodClockViews createMagazineAClockView(Context context, TemplateConfig templateConfig) {
        return createAodViews$default(this, context, templateConfig, null, null, null, 28, null);
    }

    public final AodClockViews createMagazineBClockView(Context context, TemplateConfig templateConfig) {
        AodClockViews aodClockViewsCreateAodViews$default = createAodViews$default(this, context, templateConfig, null, null, null, 28, null);
        ViewUtil.setMarginTop((View) aodClockViewsCreateAodViews$default.getClockViews().get(0), context.getResources().getDimensionPixelSize(R.dimen.kg_magazine_b_clock_aod_time_margin_top));
        return aodClockViewsCreateAodViews$default;
    }

    public final AodClockViews createMagazineCClockView(Context context, TemplateConfig templateConfig) {
        return createAodViews$default(this, context, templateConfig, null, "magazine_c_single", null, 20, null);
    }

    public final AodClockViews createRhombusClockView(final Context context, TemplateConfig templateConfig) {
        return createAodViews$default(this, context, templateConfig, null, "rhombus_single", new Function1() { // from class: com.miui.keyguard.editor.base.AodViewFactory$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AodViewFactory.createRhombusClockView$lambda$2(context, (AodClockViews) obj);
            }
        }, 4, null);
    }

    public final AodClockViews createSmartFrameClockView(Context context, TemplateConfig templateConfig) {
        return createAodViews$default(this, context, templateConfig, null, "smart_frame_single", new Function1() { // from class: com.miui.keyguard.editor.base.AodViewFactory$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AodViewFactory.createSmartFrameClockView$lambda$6((AodClockViews) obj);
            }
        }, 4, null);
    }

    public final ViewGroup.MarginLayoutParams getMarginLayoutParams(Context context, boolean z, ViewGroup.MarginLayoutParams marginLayoutParams, String str) {
        if (z) {
            marginLayoutParams.topMargin = 0;
            return marginLayoutParams;
        }
        if (!Intrinsics.areEqual(str, "magazine_a")) {
            if (Intrinsics.areEqual(str, "classic_max")) {
                marginLayoutParams.topMargin = ClassicClockTimeUtils.getClassicMaxClockTop(context);
                return marginLayoutParams;
            }
            marginLayoutParams.topMargin = 0;
            return marginLayoutParams;
        }
        Resources resources = context.getResources();
        if (!DeviceUtil.INSTANCE.isFold()) {
            marginLayoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.kg_magazine_a_clock_aod_top_margin);
            return marginLayoutParams;
        }
        if (resources.getConfiguration().orientation == 2) {
            marginLayoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.kg_magazine_a_clock_aod_top_margin_fold_orientation);
            return marginLayoutParams;
        }
        marginLayoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.kg_magazine_a_clock_aod_top_margin_fold_portrait);
        return marginLayoutParams;
    }
}
