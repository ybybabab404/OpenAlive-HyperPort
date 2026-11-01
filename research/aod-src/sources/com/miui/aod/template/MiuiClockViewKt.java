package com.miui.aod.template;

import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.view.View;
import com.miui.clock.MiuiClockNumberView;
import com.miui.clock.MiuiClockView;
import com.miui.clock.hct.Hct;
import com.miui.clock.hct.HctSolver;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.rhombus.MiuiRhombusClock;
import com.miui.clock.rhombus.MiuiRhombusClockBaseInfo;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.clock.utils.MiuiBlurUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: MiuiClockView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class MiuiClockViewKt {
    public static final boolean isRhombusTemplate(ClockBean clockBean) {
        Intrinsics.checkNotNullParameter(clockBean, "clockBean");
        return clockBean.getTemplateId() != null && clockBean.getTemplateId().equals("rhombus");
    }

    public static final boolean isRhombusTemplateB(ClockBean clockBean) {
        Intrinsics.checkNotNullParameter(clockBean, "clockBean");
        return isRhombusTemplate(clockBean) && clockBean.getStyle() == 2;
    }

    public static final boolean isRhombusTemplateExceptB(ClockBean clockBean) {
        Intrinsics.checkNotNullParameter(clockBean, "clockBean");
        return isRhombusTemplate(clockBean) && clockBean.getStyle() != 2;
    }

    public static final int transformFullAodColor(int i) {
        Hct hctFromInt = Hct.fromInt(i);
        return HctSolver.solveToInt(hctFromInt.getHue(), hctFromInt.getChroma(), hctFromInt.getTone() * 0.3d);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x002c  */
    /* JADX WARN: Code duplicated, block: B:7:0x001b  */
    /* JADX WARN: Code duplicated, block: B:9:0x0024  */
    public static final void updateClockViewColor(View view, int i, Integer num) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Drawable background;
        if (view instanceof MiuiClockNumberView) {
            MiuiClockNumberView miuiClockNumberView = (MiuiClockNumberView) view;
            if (miuiClockNumberView.getVectorDrawable() instanceof AnimatedVectorDrawable) {
                Drawable vectorDrawable = miuiClockNumberView.getVectorDrawable();
                Intrinsics.checkNotNull(vectorDrawable, "null cannot be cast to non-null type android.graphics.drawable.AnimatedVectorDrawable");
                background = (AnimatedVectorDrawable) vectorDrawable;
            } else if (view.getBackground() instanceof AnimatedVectorDrawable) {
                background = view.getBackground();
                background.setTintList(null);
            } else {
                background = null;
            }
        } else if (view.getBackground() instanceof AnimatedVectorDrawable) {
            background = view.getBackground();
            background.setTintList(null);
        } else {
            background = null;
        }
        if (background == null) {
            return;
        }
        Object objInvoke = AnimatedVectorDrawable.class.getMethod("getVectorDrawable", new Class[0]).invoke(background, new Object[0]);
        Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type android.graphics.drawable.VectorDrawable");
        VectorDrawable vectorDrawable2 = (VectorDrawable) objInvoke;
        Method declaredMethod = VectorDrawable.class.getDeclaredMethod("getTargetByName", String.class);
        declaredMethod.setAccessible(true);
        Object objInvoke2 = declaredMethod.invoke(vectorDrawable2, "_R_G_L_0_G_D_1_P_0");
        if (objInvoke2 != null) {
            Class<?> cls = objInvoke2.getClass();
            Class cls2 = Integer.TYPE;
            Method declaredMethod2 = cls.getDeclaredMethod("setStrokeColor", cls2);
            declaredMethod2.setAccessible(true);
            declaredMethod2.invoke(objInvoke2, Integer.valueOf(num != null ? num.intValue() : GlobalColorUtils.transformFullAodColor(i)));
            Object objInvoke3 = declaredMethod.invoke(vectorDrawable2, "_R_G_L_0_G_D_0_P_0");
            if (objInvoke3 != null) {
                Method declaredMethod3 = objInvoke2.getClass().getDeclaredMethod("setFillColor", cls2);
                declaredMethod3.setAccessible(true);
                declaredMethod3.invoke(objInvoke3, Integer.valueOf(transformFullAodColor(i)));
            }
        }
        view.invalidate();
    }

    public static final void updateRhombusClockEffect(MiuiClockView miuiClockView) {
        Intrinsics.checkNotNullParameter(miuiClockView, "<this>");
        if (DeviceConfig.supportBackgroundBlur(miuiClockView.getContext())) {
            if (ClockEffectUtils.isBlurMixType(miuiClockView.getClockStyleInfo().getClockEffect())) {
                MiuiBlurUtils.setMemberBlendColor(miuiClockView.getIClockView(ClockViewType.HOUR1), false, miuiClockView.getClockStyleInfo().getFullAodBlendColor());
                MiuiBlurUtils.setMemberBlendColor(miuiClockView.getIClockView(ClockViewType.HOUR2), false, miuiClockView.getClockStyleInfo().getFullAodBlendColor());
                MiuiBlurUtils.setMemberBlendColor(miuiClockView.getIClockView(ClockViewType.MIN1), false, miuiClockView.getClockStyleInfo().getFullAodBlendColor());
                MiuiBlurUtils.setMemberBlendColor(miuiClockView.getIClockView(ClockViewType.MIN2), false, miuiClockView.getClockStyleInfo().getFullAodBlendColor());
                return;
            }
            if (ClockEffectUtils.isDifferenceType(miuiClockView.getClockStyleInfo().getClockEffect())) {
                ClockBean clockBean = miuiClockView.getClockBean();
                Intrinsics.checkNotNullExpressionValue(clockBean, "getClockBean(...)");
                if (isRhombusTemplateB(clockBean)) {
                    View iClockView = miuiClockView.getIClockView(ClockViewType.ALL_VIEW);
                    MiuiRhombusClock miuiRhombusClock = iClockView instanceof MiuiRhombusClock ? (MiuiRhombusClock) iClockView : null;
                    if (miuiRhombusClock != null) {
                        miuiRhombusClock.clearColorEffect(true);
                    }
                }
            }
        }
    }

    public static final void updateSvgColor(MiuiClockView miuiClockView) {
        int[] hourAndMinuteColor;
        Intrinsics.checkNotNullParameter(miuiClockView, "<this>");
        if (miuiClockView.getClockStyleInfo() == null || miuiClockView.getClockBean() == null) {
            return;
        }
        ClockBean clockBean = miuiClockView.getClockBean();
        Intrinsics.checkNotNullExpressionValue(clockBean, "getClockBean(...)");
        if (isRhombusTemplateExceptB(clockBean) && ClockEffectUtils.isBlurMixType(miuiClockView.getClockStyleInfo().getClockEffect())) {
            updateRhombusClockEffect(miuiClockView);
            return;
        }
        ClockBean clockBean2 = miuiClockView.getClockBean();
        Intrinsics.checkNotNullExpressionValue(clockBean2, "getClockBean(...)");
        if (isRhombusTemplateB(clockBean2)) {
            ClockStyleInfo clockStyleInfo = miuiClockView.getClockStyleInfo();
            Integer numValueOf = null;
            MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = clockStyleInfo instanceof MiuiRhombusClockBaseInfo ? (MiuiRhombusClockBaseInfo) clockStyleInfo : null;
            if (miuiRhombusClockBaseInfo == null || (hourAndMinuteColor = miuiRhombusClockBaseInfo.getHourAndMinuteColor(miuiClockView.getContext())) == null) {
                return;
            }
            Integer num = (DeviceConfig.supportBackgroundBlur(miuiClockView.getContext()) && ClockEffectUtils.isBlurMixType(miuiClockView.getClockStyleInfo().getClockEffect())) ? -1 : null;
            View iClockView = miuiClockView.getIClockView(ClockViewType.HOUR1);
            Intrinsics.checkNotNullExpressionValue(iClockView, "getIClockView(...)");
            updateClockViewColor(iClockView, hourAndMinuteColor[0], num);
            View iClockView2 = miuiClockView.getIClockView(ClockViewType.HOUR2);
            Intrinsics.checkNotNullExpressionValue(iClockView2, "getIClockView(...)");
            updateClockViewColor(iClockView2, hourAndMinuteColor[0], num);
            View iClockView3 = miuiClockView.getIClockView(ClockViewType.MIN1);
            Intrinsics.checkNotNullExpressionValue(iClockView3, "getIClockView(...)");
            updateClockViewColor(iClockView3, hourAndMinuteColor[1], num);
            View iClockView4 = miuiClockView.getIClockView(ClockViewType.MIN2);
            Intrinsics.checkNotNullExpressionValue(iClockView4, "getIClockView(...)");
            updateClockViewColor(iClockView4, hourAndMinuteColor[1], num);
            if (DeviceConfig.supportBackgroundBlur(miuiClockView.getContext()) && ClockEffectUtils.isBlurMixType(miuiClockView.getClockStyleInfo().getClockEffect())) {
                numValueOf = Integer.valueOf(Color.parseColor("#FF4242"));
            }
            ClockStyleInfo clockStyleInfo2 = miuiClockView.getClockStyleInfo();
            Intrinsics.checkNotNull(clockStyleInfo2, "null cannot be cast to non-null type com.miui.clock.rhombus.MiuiRhombusClockBaseInfo");
            int secondaryColor = ((MiuiRhombusClockBaseInfo) clockStyleInfo2).getSecondaryColor();
            View iClockView5 = miuiClockView.getIClockView(ClockViewType.COLON1);
            Intrinsics.checkNotNullExpressionValue(iClockView5, "getIClockView(...)");
            updateClockViewColor(iClockView5, secondaryColor, numValueOf);
            View iClockView6 = miuiClockView.getIClockView(ClockViewType.COLON2);
            Intrinsics.checkNotNullExpressionValue(iClockView6, "getIClockView(...)");
            updateClockViewColor(iClockView6, secondaryColor, numValueOf);
            updateRhombusClockEffect(miuiClockView);
        }
    }
}
