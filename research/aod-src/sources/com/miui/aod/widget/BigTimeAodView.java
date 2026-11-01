package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.util.Consumer;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.BigTimeCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.icu.ICUManager;
import com.miui.aod.util.ClockColorManager;
import com.miui.aod.util.DeviceInfo;
import com.miui.aod.util.wakelock.DelayedWakeLock;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.CommonUtils;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.util.Arrays;
import java.util.TimeZone;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.pickerwidget.date.Calendar;
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
/* JADX INFO: compiled from: BigTimeAodView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class BigTimeAodView extends FrameLayout {
    public static final Companion Companion = new Companion(null);
    public final EaseManager.EaseStyle alphaHideEase;
    public final EaseManager.EaseStyle alphaShowEase;
    public int clockTransYStepAbs;
    public ClockSVGTimeView clockView;
    public final WakeLock drawWakeLock;
    public boolean isDual;
    public boolean isFirst;
    public boolean isShowTop;
    public Calendar mCal;
    public Calendar mCalDual;
    public int position;
    public int size;
    public StyleInfo styleInfo;
    public BigTimeTopContainerView topContainerEndView;
    public BigTimeTopContainerView topContainerStartView;
    public int topTransYStepAbs;
    public final EaseManager.EaseStyle transYEase;

    /* JADX INFO: compiled from: BigTimeAodView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void updateText(TextView textView, String str) {
            Intrinsics.checkNotNullParameter(textView, "<this>");
            textView.setText(str != null ? str : "");
            if (str == null) {
                str = "";
            }
            textView.setContentDescription(str);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public BigTimeAodView(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public BigTimeAodView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.isShowTop = true;
        this.isFirst = true;
        this.drawWakeLock = new DelayedWakeLock(new Handler(Looper.getMainLooper()), WakeLock.wrapForAnimation(context, "BigTimeAodView"));
        this.transYEase = FolmeEase.spring(1.0f, 0.8f);
        this.alphaShowEase = FolmeEase.spring(1.0f, 0.5f);
        this.alphaHideEase = FolmeEase.spring(1.0f, 0.3f);
        this.size = 2;
    }

    public /* synthetic */ BigTimeAodView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static final void updateTime$lambda$1(BigTimeAodView bigTimeAodView, String str) {
        BigTimeTopContainerView bigTimeTopContainerView = bigTimeAodView.topContainerStartView;
        if (bigTimeTopContainerView != null) {
            bigTimeTopContainerView.updateCityText(str);
        }
        BigTimeTopContainerView bigTimeTopContainerView2 = bigTimeAodView.topContainerEndView;
        if (bigTimeTopContainerView2 != null) {
            bigTimeTopContainerView2.updateCityText(str);
        }
    }

    public static final void updateTime$lambda$2(BigTimeAodView bigTimeAodView, String str) {
        BigTimeTopContainerView bigTimeTopContainerView = bigTimeAodView.topContainerStartView;
        if (bigTimeTopContainerView != null) {
            bigTimeTopContainerView.updateCityText2(str);
        }
        BigTimeTopContainerView bigTimeTopContainerView2 = bigTimeAodView.topContainerEndView;
        if (bigTimeTopContainerView2 != null) {
            bigTimeTopContainerView2.updateCityText2(str);
        }
    }

    public final void calcPositionMovePrams() {
        this.topTransYStepAbs = getResources().getDimensionPixelSize(R.dimen.big_time_top_container_margin_top_plus) - getResources().getDimensionPixelSize(R.dimen.big_time_top_container_margin_top);
        Point displaySize = DeviceInfo.getDisplaySize();
        this.clockTransYStepAbs = ((displaySize.y - getResources().getDimensionPixelSize(R.dimen.big_time_clock_height_real)) / 2) - getResources().getDimensionPixelSize(R.dimen.big_time_clock_margin_vertical_plus);
    }

    public final String getDate(Calendar calendar, boolean z, boolean z2) {
        String str = calendar.format(getContext(), AODSettings.getDateFormat(getContext(), z, z2));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    public final String getTime(Calendar calendar, boolean z) {
        int iResolveHour = CommonUtils.resolveHour(calendar.get(18), z);
        int i = calendar.get(20);
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%02d:%02d", Arrays.copyOf(new Object[]{Integer.valueOf(iResolveHour), Integer.valueOf(i)}, 2));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    public final boolean handleUpdateNotificationIcons(IAodView aodView) {
        Intrinsics.checkNotNullParameter(aodView, "aodView");
        BigTimeTopContainerView bigTimeTopContainerView = this.topContainerStartView;
        if (bigTimeTopContainerView != null) {
            bigTimeTopContainerView.handleUpdateNotificationIcons(aodView, false);
        }
        BigTimeTopContainerView bigTimeTopContainerView2 = this.topContainerEndView;
        if (bigTimeTopContainerView2 != null) {
            bigTimeTopContainerView2.handleUpdateNotificationIcons(aodView, true);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BigTimeTopContainerView bigTimeTopContainerView = this.topContainerStartView;
        if (bigTimeTopContainerView != null) {
            Folme.clean(bigTimeTopContainerView);
        }
        BigTimeTopContainerView bigTimeTopContainerView2 = this.topContainerEndView;
        if (bigTimeTopContainerView2 != null) {
            Folme.clean(bigTimeTopContainerView2);
        }
        ClockSVGTimeView clockSVGTimeView = this.clockView;
        if (clockSVGTimeView != null) {
            Folme.clean(clockSVGTimeView);
        }
        this.isFirst = true;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.topContainerStartView = (BigTimeTopContainerView) findViewById(R.id.top_container_start);
        this.topContainerEndView = (BigTimeTopContainerView) findViewById(R.id.top_container_end);
        BigTimeTopContainerView bigTimeTopContainerView = this.topContainerStartView;
        if (bigTimeTopContainerView != null) {
            bigTimeTopContainerView.setGravity(8388611);
        }
        BigTimeTopContainerView bigTimeTopContainerView2 = this.topContainerEndView;
        if (bigTimeTopContainerView2 != null) {
            bigTimeTopContainerView2.setGravity(8388613);
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.big_time_top_container_margin_top);
        BigTimeTopContainerView bigTimeTopContainerView3 = this.topContainerStartView;
        if (bigTimeTopContainerView3 != null) {
            ViewUtil.setMarginTop(bigTimeTopContainerView3, dimensionPixelSize);
        }
        BigTimeTopContainerView bigTimeTopContainerView4 = this.topContainerEndView;
        if (bigTimeTopContainerView4 != null) {
            ViewUtil.setMarginTop(bigTimeTopContainerView4, dimensionPixelSize);
        }
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.big_time_top_container_margin_horizontal);
        if (ViewUtil.INSTANCE.isRtl(this)) {
            BigTimeTopContainerView bigTimeTopContainerView5 = this.topContainerStartView;
            if (bigTimeTopContainerView5 != null) {
                bigTimeTopContainerView5.setPadding(0, 0, dimensionPixelSize2, 0);
            }
            BigTimeTopContainerView bigTimeTopContainerView6 = this.topContainerEndView;
            if (bigTimeTopContainerView6 != null) {
                bigTimeTopContainerView6.setPadding(dimensionPixelSize2, 0, 0, 0);
            }
        } else {
            BigTimeTopContainerView bigTimeTopContainerView7 = this.topContainerStartView;
            if (bigTimeTopContainerView7 != null) {
                bigTimeTopContainerView7.setPadding(dimensionPixelSize2, 0, 0, 0);
            }
            BigTimeTopContainerView bigTimeTopContainerView8 = this.topContainerEndView;
            if (bigTimeTopContainerView8 != null) {
                bigTimeTopContainerView8.setPadding(0, 0, dimensionPixelSize2, 0);
            }
        }
        this.clockView = (ClockSVGTimeView) findViewById(R.id.big_time_clock);
        calcPositionMovePrams();
    }

    public final void update(StyleInfo styleInfo, int i, boolean z) {
        if (styleInfo instanceof BigTimeCategoryInfo) {
            this.size = i;
            this.isDual = z;
            boolean z2 = true;
            if (i != 3 && i != 1) {
                z2 = false;
            }
            this.isShowTop = z2;
            this.styleInfo = styleInfo;
            BigTimeTopContainerView bigTimeTopContainerView = this.topContainerStartView;
            if (bigTimeTopContainerView != null) {
                bigTimeTopContainerView.update(styleInfo, i, z);
            }
            BigTimeTopContainerView bigTimeTopContainerView2 = this.topContainerEndView;
            if (bigTimeTopContainerView2 != null) {
                bigTimeTopContainerView2.update(styleInfo, i, z);
            }
            updateClockViewLayoutParams(i);
            calcPositionMovePrams();
        }
    }

    public final void updateClockColor() {
        ClockSVGTimeView clockSVGTimeView = this.clockView;
        if (clockSVGTimeView != null) {
            ClockColorManager clockColorManager = ClockColorManager.INSTANCE;
            clockSVGTimeView.setColor(clockColorManager.getClockTimeColor(clockColorManager.getWallpaperHue()));
        }
    }

    public final void updateClockViewLayoutParams(int i) {
        ClockSVGTimeView clockSVGTimeView = this.clockView;
        if (clockSVGTimeView != null) {
            ViewGroup.LayoutParams layoutParams = clockSVGTimeView.getLayoutParams();
            if (i == 2) {
                layoutParams.width = -1;
                layoutParams.height = -2;
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    int dimensionPixelSize = clockSVGTimeView.getResources().getDimensionPixelSize(R.dimen.big_time_clock_margin_horizontal_small);
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    marginLayoutParams.setMarginStart(dimensionPixelSize);
                    marginLayoutParams.setMarginEnd(dimensionPixelSize);
                }
            } else {
                layoutParams.width = clockSVGTimeView.getResources().getDimensionPixelSize(R.dimen.big_time_clock_width_real);
                layoutParams.height = clockSVGTimeView.getResources().getDimensionPixelSize(R.dimen.big_time_clock_height_real);
            }
            clockSVGTimeView.setLayoutParams(layoutParams);
        }
    }

    public final void updatePosition(int i, boolean z) {
        Log.i("BigTimeAodView", "updatePosition " + i + ", withAnimation:" + z + ", mIsFirst: " + this.isFirst);
        if (this.position != i || this.isFirst) {
            this.isFirst = false;
            this.position = i;
            updatePositionInner(i, z);
        }
    }

    public final void updatePositionInner(int i, boolean z) {
        float f;
        float f2;
        float f3 = 1.0f;
        if (i == 0) {
            f = 0.0f;
            f2 = 0.0f;
        } else if (i == 1) {
            f2 = -this.clockTransYStepAbs;
            f = 0.0f;
            f3 = 0.0f;
        } else if (i != 2) {
            f = 0.0f;
            f3 = 0.0f;
            f2 = 0.0f;
        } else {
            f = this.topTransYStepAbs;
            f2 = this.clockTransYStepAbs;
        }
        float f4 = 1 - f3;
        if (!z) {
            ClockSVGTimeView clockSVGTimeView = this.clockView;
            if (clockSVGTimeView != null) {
                Folme.useAt(clockSVGTimeView).state().setTo(ViewProperty.TRANSLATION_Y, Float.valueOf(f2));
            }
            BigTimeTopContainerView bigTimeTopContainerView = this.topContainerStartView;
            if (bigTimeTopContainerView != null) {
                Folme.useAt(bigTimeTopContainerView).state().setTo(ViewProperty.TRANSLATION_Y, Float.valueOf(f), ViewProperty.ALPHA, Float.valueOf(f3));
            }
            BigTimeTopContainerView bigTimeTopContainerView2 = this.topContainerEndView;
            if (bigTimeTopContainerView2 != null) {
                Folme.useAt(bigTimeTopContainerView2).state().setTo(ViewProperty.ALPHA, Float.valueOf(f4));
                return;
            }
            return;
        }
        ClockSVGTimeView clockSVGTimeView2 = this.clockView;
        if (clockSVGTimeView2 != null) {
            this.drawWakeLock.acquire("BigTimeAodView-updatePosition");
            Folme.useAt(clockSVGTimeView2).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(f2), new AnimConfig().setEase(this.transYEase).addListeners(new TransitionListener() { // from class: com.miui.aod.widget.BigTimeAodView$updatePositionInner$1$clockViewAnimConfig$1
                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    super.onComplete(obj);
                    this.this$0.drawWakeLock.release("BigTimeAodView-updatePosition");
                }
            }));
        }
        BigTimeTopContainerView bigTimeTopContainerView3 = this.topContainerStartView;
        if (bigTimeTopContainerView3 != null) {
            EaseManager.EaseStyle easeStyle = f3 > 0.0f ? this.alphaShowEase : this.alphaHideEase;
            AnimConfig animConfig = new AnimConfig();
            ViewProperty viewProperty = ViewProperty.ALPHA;
            AnimConfig special = animConfig.setSpecial(viewProperty, easeStyle, new float[0]);
            ViewProperty viewProperty2 = ViewProperty.TRANSLATION_Y;
            Folme.useAt(bigTimeTopContainerView3).state().to(viewProperty, Float.valueOf(f3), viewProperty2, Float.valueOf(f), special.setSpecial(viewProperty2, this.transYEase, new float[0]));
        }
        BigTimeTopContainerView bigTimeTopContainerView4 = this.topContainerEndView;
        if (bigTimeTopContainerView4 != null) {
            Folme.useAt(bigTimeTopContainerView4).state().to(ViewProperty.ALPHA, Float.valueOf(f4), new AnimConfig().setEase(f4 > 0.0f ? this.alphaShowEase : this.alphaHideEase));
        }
    }

    public final void updateTime(boolean z, TimeZone timeZone, TimeZone timeZone2) {
        boolean z2;
        StyleInfo styleInfo;
        Intrinsics.checkNotNullParameter(timeZone, "timeZone");
        long jCurrentTimeMillis = System.currentTimeMillis();
        Calendar calendarRefreshCalendar = Utils.refreshCalendar(this.mCal, timeZone, jCurrentTimeMillis);
        this.mCal = calendarRefreshCalendar;
        ClockSVGTimeView clockSVGTimeView = this.clockView;
        if (clockSVGTimeView != null) {
            Intrinsics.checkNotNull(calendarRefreshCalendar);
            int i = calendarRefreshCalendar.get(18);
            Calendar calendar = this.mCal;
            Intrinsics.checkNotNull(calendar);
            int i2 = calendar.get(20);
            ClockSVGTimeView.Companion companion = ClockSVGTimeView.Companion;
            StyleInfo styleInfo2 = this.styleInfo;
            String label = styleInfo2 != null ? styleInfo2.getLabel() : null;
            z2 = z;
            clockSVGTimeView.setTimeAndStyle(z2, i, i2, companion.findStyle(label), this.isShowTop);
        } else {
            z2 = z;
        }
        ClockColorManager clockColorManager = ClockColorManager.INSTANCE;
        int[] clockTimeColor = clockColorManager.getClockTimeColor(clockColorManager.getWallpaperHue());
        int[] iArr = (int[]) clockTimeColor.clone();
        if (Utils.isAodEnable(getContext()) || this.size != 2) {
            ClockSVGTimeView clockSVGTimeView2 = this.clockView;
            if (clockSVGTimeView2 != null) {
                clockSVGTimeView2.setColor(clockTimeColor);
            }
        } else {
            int length = iArr.length;
            for (int i3 = 0; i3 < length; i3++) {
                int i4 = iArr[i3];
                iArr[i3] = (i4 & 255) | (((i4 >> 16) & 255) << 16) | 1275068416 | (((i4 >> 8) & 255) << 8);
            }
            ClockSVGTimeView clockSVGTimeView3 = this.clockView;
            if (clockSVGTimeView3 != null) {
                clockSVGTimeView3.setColor(iArr);
            }
        }
        if (this.isShowTop) {
            Calendar calendar2 = this.mCal;
            Intrinsics.checkNotNull(calendar2);
            String date = getDate(calendar2, z2, false);
            BigTimeTopContainerView bigTimeTopContainerView = this.topContainerStartView;
            if (bigTimeTopContainerView != null) {
                bigTimeTopContainerView.updateDateText(date);
            }
            BigTimeTopContainerView bigTimeTopContainerView2 = this.topContainerEndView;
            if (bigTimeTopContainerView2 != null) {
                bigTimeTopContainerView2.updateDateText(date);
            }
            Calendar calendar3 = this.mCal;
            Intrinsics.checkNotNull(calendar3);
            String date2 = getDate(calendar3, z2, true);
            BigTimeTopContainerView bigTimeTopContainerView3 = this.topContainerStartView;
            if (bigTimeTopContainerView3 != null) {
                bigTimeTopContainerView3.updateDateLunar(date2);
            }
            BigTimeTopContainerView bigTimeTopContainerView4 = this.topContainerEndView;
            if (bigTimeTopContainerView4 != null) {
                bigTimeTopContainerView4.updateDateLunar(date2);
            }
            if (this.isDual && (styleInfo = this.styleInfo) != null && styleInfo.isDateAndTimeSwitchOn()) {
                this.mCalDual = Utils.refreshCalendar(this.mCalDual, timeZone2, jCurrentTimeMillis);
                ICUManager iCUManager = ICUManager.INSTANCE;
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                iCUManager.loadCity(context, timeZone, new Consumer() { // from class: com.miui.aod.widget.BigTimeAodView$$ExternalSyntheticLambda0
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj) {
                        BigTimeAodView.updateTime$lambda$1(this.f$0, (String) obj);
                    }
                });
                Context context2 = getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                iCUManager.loadCity(context2, timeZone2, new Consumer() { // from class: com.miui.aod.widget.BigTimeAodView$$ExternalSyntheticLambda1
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj) {
                        BigTimeAodView.updateTime$lambda$2(this.f$0, (String) obj);
                    }
                });
                Calendar calendar4 = this.mCal;
                Intrinsics.checkNotNull(calendar4);
                String time = getTime(calendar4, z2);
                Calendar calendar5 = this.mCalDual;
                Intrinsics.checkNotNull(calendar5);
                String time2 = getTime(calendar5, z2);
                BigTimeTopContainerView bigTimeTopContainerView5 = this.topContainerStartView;
                if (bigTimeTopContainerView5 != null) {
                    bigTimeTopContainerView5.updateTimeText(time);
                }
                BigTimeTopContainerView bigTimeTopContainerView6 = this.topContainerStartView;
                if (bigTimeTopContainerView6 != null) {
                    bigTimeTopContainerView6.updateTimeText2(time2);
                }
                BigTimeTopContainerView bigTimeTopContainerView7 = this.topContainerEndView;
                if (bigTimeTopContainerView7 != null) {
                    bigTimeTopContainerView7.updateTimeText(time);
                }
                BigTimeTopContainerView bigTimeTopContainerView8 = this.topContainerEndView;
                if (bigTimeTopContainerView8 != null) {
                    bigTimeTopContainerView8.updateTimeText2(time2);
                }
                Calendar calendar6 = this.mCalDual;
                Intrinsics.checkNotNull(calendar6);
                String date3 = getDate(calendar6, z2, false);
                BigTimeTopContainerView bigTimeTopContainerView9 = this.topContainerStartView;
                if (bigTimeTopContainerView9 != null) {
                    bigTimeTopContainerView9.updateDateText2(date3);
                }
                BigTimeTopContainerView bigTimeTopContainerView10 = this.topContainerEndView;
                if (bigTimeTopContainerView10 != null) {
                    bigTimeTopContainerView10.updateDateText2(date3);
                }
            }
        }
    }
}
