package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.classic.ClassicContentAreaView;
import com.miui.clock.classic.ClassicTextAreaView;
import com.miui.clock.module.HealthBean;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.clock.ClassicDataType;
import com.miui.keyguard.editor.edit.clock.ClassicSelectorBean;
import java.util.TimeZone;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import miuix.pickerwidget.date.Calendar;

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
/* JADX INFO: compiled from: ClockUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClockUtil {
    public static final ClockUtil INSTANCE = new ClockUtil();
    public static final HealthBean healthBean;
    public static final WeatherBean weatherBean;

    /* JADX INFO: compiled from: ClockUtil.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassicDataType.values().length];
            try {
                iArr[ClassicDataType.TEXT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassicDataType.WEATHER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClassicDataType.HEALTH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        WeatherBean weatherBean2 = new WeatherBean();
        weatherBean2.setWeatherType(1);
        weatherBean2.setTemperature("28℃");
        weatherBean2.setAqiLevel(56);
        weatherBean2.setSunTime(480L, 580L, 1300L, true);
        weatherBean2.setWindSpeed("15");
        weatherBean2.setWindAngel(37.2f);
        weatherBean2.setTemperatureUnit(1);
        weatherBean2.setUVILevel(11);
        weatherBean2.setHumidity(35);
        weatherBean = weatherBean2;
        HealthBean healthBean2 = new HealthBean();
        healthBean2.setCaloriesNow(320);
        healthBean2.setCaloriesTarget(640);
        healthBean2.setStepCountNow(5315);
        healthBean2.setStepCountTarget(9000);
        healthBean2.setStandCountNow(12);
        healthBean2.setStandCountTarget(30);
        healthBean2.setSleepDuration(31800000L);
        healthBean = healthBean2;
    }

    public static /* synthetic */ void setClassicClockTextAreaStyle$default(ClockUtil clockUtil, ClassicTextAreaView classicTextAreaView, int i, ClassicSelectorBean classicSelectorBean, boolean z, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z = true;
        }
        clockUtil.setClassicClockTextAreaStyle(classicTextAreaView, i, classicSelectorBean, z);
    }

    public final Calendar getMiuixCalendar() {
        return new Calendar(TimeZone.getDefault());
    }

    public final boolean is24HourFormat(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return DateFormat.is24HourFormat(context);
    }

    public final void setClassicClockTextAreaStyle(ClassicTextAreaView view, int i, ClassicSelectorBean bean, boolean z) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(bean, "bean");
        if (z) {
            view.setCalendar(getMiuixCalendar());
        }
        view.initData(i, bean.getStyle());
        Log.i("ClockUtil", "setClassicClockTextAreaStyle: style=" + bean.getStyle());
        int i2 = WhenMappings.$EnumSwitchMapping$0[bean.getDateType().ordinal()];
        if (i2 == 1) {
            view.updateTime();
            return;
        }
        if (i2 != 2) {
            if (i2 != 3) {
                throw new NoWhenBranchMatchedException();
            }
            view.updateHealth(healthBean);
        } else {
            WeatherBean weatherBean2 = weatherBean;
            weatherBean2.setDescription(view.getResources().getString(R.string.kg_classic_item_weather_cloudy));
            view.updateWeather(weatherBean2);
        }
    }

    public final void setClassicClockTimeAreaStyle(MiuiTextGlassView view, int i, ClassicSelectorBean bean, int i2, boolean z) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(bean, "bean");
        ClassicClockTimeUtils.setClassicTimeStyle(view, i, i2, bean.getStyle(), false, z);
        Context context = view.getContext();
        Calendar miuixCalendar = getMiuixCalendar();
        Context context2 = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        view.setText(ClassicClockTimeUtils.getClassicTime(context, miuixCalendar, is24HourFormat(context2), bean.getStyle(), false));
    }

    public final void setClassicContentAreaStyle(ClassicContentAreaView classicContentAreaView, int i, ClassicSelectorBean bean, int i2) {
        Intrinsics.checkNotNullParameter(bean, "bean");
        if (classicContentAreaView == null) {
            return;
        }
        classicContentAreaView.setCalendar(getMiuixCalendar());
        Log.i("ClockUtil", "setClassicContentAreaStyle: " + bean.getStyle());
        classicContentAreaView.initData(i, bean.getStyle(), i2, false);
        int i3 = WhenMappings.$EnumSwitchMapping$0[bean.getDateType().ordinal()];
        if (i3 == 1) {
            Context context = classicContentAreaView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            classicContentAreaView.updateTime(is24HourFormat(context), 5);
            classicContentAreaView.updateColor(i);
            return;
        }
        if (i3 != 2) {
            if (i3 != 3) {
                throw new NoWhenBranchMatchedException();
            }
            classicContentAreaView.updateHealth(healthBean);
            classicContentAreaView.updateColor(i);
            return;
        }
        WeatherBean weatherBean2 = weatherBean;
        weatherBean2.setDescription(classicContentAreaView.getResources().getString(R.string.kg_classic_item_weather_cloudy));
        classicContentAreaView.updateWeather(weatherBean2);
        classicContentAreaView.updateColor(i);
    }
}
