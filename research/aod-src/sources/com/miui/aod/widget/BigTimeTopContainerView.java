package com.miui.aod.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.keyguard.editor.utils.ViewUtil;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
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
/* JADX INFO: compiled from: BigTimeTopContainerView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class BigTimeTopContainerView extends LinearLayout {
    public View mBatteryContainer;
    public View mBigTimeDualContainer;
    public TextView mCityTv;
    public TextView mCityTv2;
    public TextView mDateLunarTv;
    public TextView mDateTv;
    public TextView mDateTv2;
    public ImageView mFirstIv;
    public ImageView mFourthIv;
    public ImageView mMoreIv;
    public View mNotificationWidget;
    public ImageView mSecondIv;
    public boolean mShowTop;
    public ImageView mThirdIv;
    public TextView mTimeTv;
    public TextView mTimeTv2;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public BigTimeTopContainerView(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public BigTimeTopContainerView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ BigTimeTopContainerView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public final void handleUpdateNotificationIcons(IAodView aodView, boolean z) {
        Intrinsics.checkNotNullParameter(aodView, "aodView");
        ImageView imageView = z ? this.mMoreIv : this.mFirstIv;
        ImageView imageView2 = z ? this.mFourthIv : this.mSecondIv;
        ImageView imageView3 = z ? this.mSecondIv : this.mFourthIv;
        ImageView imageView4 = z ? this.mFirstIv : this.mMoreIv;
        aodView.bindNotificationIconView(imageView, 0);
        aodView.bindNotificationIconView(imageView2, 1);
        aodView.bindNotificationIconView(this.mThirdIv, 2);
        aodView.bindNotificationIconView(imageView3, 3);
        if (aodView.getNotificationDatas().size() > 4) {
            if (imageView4 != null) {
                imageView4.setVisibility(0);
            }
        } else if (imageView4 != null) {
            imageView4.setVisibility(4);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBigTimeDualContainer = findViewById(R.id.big_time_dual_container);
        this.mCityTv = (TextView) findViewById(R.id.city);
        this.mTimeTv = (TextView) findViewById(R.id.time);
        this.mDateTv = (TextView) findViewById(R.id.date);
        this.mDateLunarTv = (TextView) findViewById(R.id.date_lunar);
        this.mCityTv2 = (TextView) findViewById(R.id.city2);
        this.mTimeTv2 = (TextView) findViewById(R.id.time2);
        this.mDateTv2 = (TextView) findViewById(R.id.date2);
        this.mBatteryContainer = findViewById(R.id.battery_container);
        this.mNotificationWidget = findViewById(R.id.aod_icons_container);
        this.mFirstIv = (ImageView) findViewById(R.id.first);
        this.mSecondIv = (ImageView) findViewById(R.id.second);
        this.mThirdIv = (ImageView) findViewById(R.id.third);
        this.mFourthIv = (ImageView) findViewById(R.id.forth);
        this.mMoreIv = (ImageView) findViewById(R.id.more_notification_icon);
    }

    public final void update(StyleInfo styleInfo, int i, boolean z) {
        Intrinsics.checkNotNullParameter(styleInfo, "styleInfo");
        boolean z2 = true;
        boolean z3 = i == 3 || i == 1;
        this.mShowTop = z3;
        setVisibility(z3 ? 0 : 8);
        boolean zIsDateAndTimeSwitchOn = styleInfo.isDateAndTimeSwitchOn();
        boolean z4 = !z && styleInfo.isLunarSwitchOn(getContext());
        TextView textView = this.mCityTv;
        if (textView != null) {
            textView.setVisibility((z && zIsDateAndTimeSwitchOn) ? 0 : 8);
        }
        TextView textView2 = this.mTimeTv;
        if (textView2 != null) {
            textView2.setVisibility((z && zIsDateAndTimeSwitchOn) ? 0 : 8);
        }
        TextView textView3 = this.mDateTv;
        if (textView3 != null) {
            textView3.setVisibility(zIsDateAndTimeSwitchOn ? 0 : 8);
        }
        TextView textView4 = this.mDateTv2;
        if (textView4 != null) {
            textView4.setVisibility(zIsDateAndTimeSwitchOn ? 0 : 8);
        }
        TextView textView5 = this.mDateLunarTv;
        if (textView5 != null) {
            textView5.setVisibility(z4 ? 0 : 8);
        }
        View view = this.mBigTimeDualContainer;
        if (view != null) {
            view.setVisibility((z && zIsDateAndTimeSwitchOn) ? 0 : 8);
        }
        View view2 = this.mBatteryContainer;
        if (view2 != null) {
            view2.setAlpha(getResources().getFloat(R.dimen.big_time_date_time_alpha));
        }
        View view3 = this.mBatteryContainer;
        if (view3 != null) {
            view3.setVisibility(styleInfo.isBatterySwitchOn() ? 0 : 8);
        }
        boolean z5 = zIsDateAndTimeSwitchOn || z4;
        View view4 = this.mBatteryContainer;
        if (view4 != null) {
            ViewUtil.setMarginTop(view4, z5 ? getResources().getDimensionPixelSize(R.dimen.big_time_battery_margin_top) : 0);
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.big_time_date_item_space_horizontal);
        TextView textView6 = this.mDateTv;
        if (textView6 != null) {
            if (!z4) {
                dimensionPixelSize = 0;
            }
            ViewUtil.setMarginEnd(textView6, dimensionPixelSize);
        }
        if (!z5 && !styleInfo.isBatterySwitchOn()) {
            z2 = false;
        }
        View view5 = this.mNotificationWidget;
        if (view5 != null) {
            ViewUtil.setMarginTop(view5, z2 ? getResources().getDimensionPixelSize(R.dimen.big_time_icons_margin_top) : 0);
        }
        View view6 = this.mNotificationWidget;
        if (view6 != null) {
            view6.setVisibility(styleInfo.isNotificationSwitchOn() ? 0 : 8);
        }
    }

    public final void updateCityText(String str) {
        TextView textView = this.mCityTv;
        if (textView != null) {
            BigTimeAodView.Companion.updateText(textView, str);
        }
    }

    public final void updateCityText2(String str) {
        TextView textView = this.mCityTv2;
        if (textView != null) {
            BigTimeAodView.Companion.updateText(textView, str);
        }
    }

    public final void updateDateLunar(String str) {
        TextView textView = this.mDateLunarTv;
        if (textView != null) {
            BigTimeAodView.Companion.updateText(textView, str);
        }
    }

    public final void updateDateText(String str) {
        TextView textView = this.mDateTv;
        if (textView != null) {
            BigTimeAodView.Companion.updateText(textView, str);
        }
    }

    public final void updateDateText2(String str) {
        TextView textView = this.mDateTv2;
        if (textView != null) {
            BigTimeAodView.Companion.updateText(textView, str);
        }
    }

    public final void updateTimeText(String str) {
        TextView textView = this.mTimeTv;
        if (textView != null) {
            BigTimeAodView.Companion.updateText(textView, str);
        }
    }

    public final void updateTimeText2(String str) {
        TextView textView = this.mTimeTv2;
        if (textView != null) {
            BigTimeAodView.Companion.updateText(textView, str);
        }
    }
}
