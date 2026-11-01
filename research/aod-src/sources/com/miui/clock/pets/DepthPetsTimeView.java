package com.miui.clock.pets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import com.miui.clock.R;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.xiaomi.onetrack.h.d.a;
import java.text.SimpleDateFormat;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;
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
/* JADX INFO: compiled from: DepthPetsTimeView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public class DepthPetsTimeView extends ConstraintLayout {
    public static final Companion Companion = new Companion(null);
    public Space clockSpace;
    public Guideline guidelineV50;
    public DepthPetsClockBaseInfo mInfo;
    public TextView mMonth;
    public ConstraintLayout mTimeDesc;
    public boolean switchWeekAndMonthVisible;
    public ImageView timeDot;
    public ImageView timeHourLeft;
    public ImageView timeHourRight;
    public ImageView timeMinuteLeft;
    public ImageView timeMinuteRight;
    public ImageView weekBottom;
    public TextView weekToday;

    /* JADX INFO: compiled from: DepthPetsTimeView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DepthPetsTimeView(@Nullable Context context) {
        super(context);
        Intrinsics.checkNotNull(context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DepthPetsTimeView(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNull(context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DepthPetsTimeView(@Nullable Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNull(context);
    }

    @Nullable
    public final Space getClockSpace() {
        return this.clockSpace;
    }

    public final int getDimen(int i) {
        return DeviceConfig.getDimen(i, getContext());
    }

    @Nullable
    public final DepthPetsClockBaseInfo getMInfo() {
        return this.mInfo;
    }

    @Nullable
    public final TextView getMMonth() {
        return this.mMonth;
    }

    @Nullable
    public final ConstraintLayout getMTimeDesc() {
        return this.mTimeDesc;
    }

    @Nullable
    public final ImageView getTimeDot() {
        return this.timeDot;
    }

    @Nullable
    public final ImageView getTimeHourLeft() {
        return this.timeHourLeft;
    }

    @Nullable
    public final ImageView getTimeHourRight() {
        return this.timeHourRight;
    }

    @Nullable
    public final ImageView getTimeMinuteLeft() {
        return this.timeMinuteLeft;
    }

    @Nullable
    public final ImageView getTimeMinuteRight() {
        return this.timeMinuteRight;
    }

    @Nullable
    public final ImageView getWeekBottom() {
        return this.weekBottom;
    }

    @Nullable
    public final TextView getWeekToday() {
        return this.weekToday;
    }

    public void initDate() {
        TextView textView = this.mMonth;
        ViewGroup.LayoutParams layoutParams = textView != null ? textView.getLayoutParams() : null;
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = -2;
        layoutParams2.bottomToBottom = -1;
        layoutParams2.rightToRight = -1;
        layoutParams2.topToTop = getId();
        layoutParams2.leftToLeft = getId();
        layoutParams2.setMargins(getDimen(R.dimen.miui_depth_pets_date_margin_left), getDimen(R.dimen.miui_depth_pets_date_margin_top), 0, 0);
        TextView textView2 = this.mMonth;
        Intrinsics.checkNotNull(textView2);
        textView2.setTextSize(0, getDimen(R.dimen.miui_depth_pets_date_text_size));
        TextView textView3 = this.mMonth;
        Intrinsics.checkNotNull(textView3);
        textView3.setLayoutParams(layoutParams2);
    }

    public void initTime() {
        ImageView imageView = this.timeDot;
        ViewGroup.LayoutParams layoutParams = imageView != null ? imageView.getLayoutParams() : null;
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
        ImageView imageView2 = this.timeHourLeft;
        ViewGroup.LayoutParams layoutParams3 = imageView2 != null ? imageView2.getLayoutParams() : null;
        Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) layoutParams3;
        ImageView imageView3 = this.timeHourRight;
        ViewGroup.LayoutParams layoutParams5 = imageView3 != null ? imageView3.getLayoutParams() : null;
        Intrinsics.checkNotNull(layoutParams5, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
        ConstraintLayout.LayoutParams layoutParams6 = (ConstraintLayout.LayoutParams) layoutParams5;
        ImageView imageView4 = this.timeMinuteLeft;
        ViewGroup.LayoutParams layoutParams7 = imageView4 != null ? imageView4.getLayoutParams() : null;
        Intrinsics.checkNotNull(layoutParams7, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
        ConstraintLayout.LayoutParams layoutParams8 = (ConstraintLayout.LayoutParams) layoutParams7;
        ImageView imageView5 = this.timeMinuteRight;
        ViewGroup.LayoutParams layoutParams9 = imageView5 != null ? imageView5.getLayoutParams() : null;
        Intrinsics.checkNotNull(layoutParams9, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
        ConstraintLayout.LayoutParams layoutParams10 = (ConstraintLayout.LayoutParams) layoutParams9;
        int dimen = getDimen(R.dimen.miui_depth_pets_time_width);
        int dimen2 = getDimen(R.dimen.miui_depth_pets_time_height);
        layoutParams2.leftToLeft = getId();
        layoutParams2.topToTop = getId();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(R.dimen.miui_depth_pets_dot_width);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(R.dimen.miui_depth_pets_dot_height);
        layoutParams2.setMargins(getDimen(R.dimen.miui_depth_pets_dot_margin_left), getDimen(R.dimen.miui_depth_pets_dot_margin_top), 0, 0);
        layoutParams4.leftToLeft = getId();
        Space space = this.clockSpace;
        Intrinsics.checkNotNull(space);
        layoutParams4.topToTop = space.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams4).width = dimen;
        ((ViewGroup.MarginLayoutParams) layoutParams4).height = dimen2;
        layoutParams4.setMargins(0, 0, 0, 0);
        ImageView imageView6 = this.timeHourLeft;
        Intrinsics.checkNotNull(imageView6);
        layoutParams6.leftToLeft = imageView6.getId();
        ImageView imageView7 = this.timeHourLeft;
        Intrinsics.checkNotNull(imageView7);
        layoutParams6.topToTop = imageView7.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams6).width = dimen;
        ((ViewGroup.MarginLayoutParams) layoutParams6).height = dimen2;
        layoutParams6.setMargins(getDimen(R.dimen.miui_depth_pets_hour_right_margin_left), 0, 0, 0);
        ImageView imageView8 = this.timeHourLeft;
        Intrinsics.checkNotNull(imageView8);
        layoutParams8.leftToLeft = imageView8.getId();
        ImageView imageView9 = this.timeHourLeft;
        Intrinsics.checkNotNull(imageView9);
        layoutParams8.topToTop = imageView9.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams8).width = dimen;
        ((ViewGroup.MarginLayoutParams) layoutParams8).height = dimen2;
        layoutParams8.setMargins(getDimen(R.dimen.miui_depth_pets_minute_margin_left), getDimen(R.dimen.miui_depth_pets_minute_left_margin_top), 0, 0);
        ImageView imageView10 = this.timeMinuteLeft;
        Intrinsics.checkNotNull(imageView10);
        layoutParams10.leftToLeft = imageView10.getId();
        layoutParams10.topToTop = getId();
        ((ViewGroup.MarginLayoutParams) layoutParams10).width = dimen;
        ((ViewGroup.MarginLayoutParams) layoutParams10).height = dimen2;
        layoutParams10.setMargins(getDimen(R.dimen.miui_depth_pets_minute_right_margin_left), getDimen(R.dimen.miui_depth_pets_minute_right_margin_top), 0, 0);
        ImageView imageView11 = this.timeDot;
        Intrinsics.checkNotNull(imageView11);
        imageView11.setLayoutParams(layoutParams2);
        ImageView imageView12 = this.timeHourRight;
        Intrinsics.checkNotNull(imageView12);
        imageView12.setLayoutParams(layoutParams6);
        ImageView imageView13 = this.timeHourLeft;
        Intrinsics.checkNotNull(imageView13);
        imageView13.setLayoutParams(layoutParams4);
        ImageView imageView14 = this.timeMinuteLeft;
        Intrinsics.checkNotNull(imageView14);
        imageView14.setLayoutParams(layoutParams8);
        ImageView imageView15 = this.timeMinuteRight;
        Intrinsics.checkNotNull(imageView15);
        imageView15.setLayoutParams(layoutParams10);
    }

    public void initWeek() {
        ImageView imageView = this.weekBottom;
        ViewGroup.LayoutParams layoutParams = imageView != null ? imageView.getLayoutParams() : null;
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
        layoutParams2.topToTop = getId();
        layoutParams2.leftToLeft = getId();
        layoutParams2.rightToRight = -1;
        layoutParams2.bottomToBottom = -1;
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(R.dimen.miui_depth_pets_week_bottom_width);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(R.dimen.miui_depth_pets_week_bottom_height);
        layoutParams2.setMargins(getDimen(R.dimen.miui_depth_pets_week_margin_left), getDimen(R.dimen.miui_depth_pets_week_margin_top), 0, 0);
        TextView textView = this.weekToday;
        ViewGroup.LayoutParams layoutParams3 = textView != null ? textView.getLayoutParams() : null;
        Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) layoutParams3;
        ((ViewGroup.MarginLayoutParams) layoutParams4).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams4).height = -2;
        layoutParams4.bottomToBottom = -1;
        ImageView imageView2 = this.weekBottom;
        Intrinsics.checkNotNull(imageView2);
        layoutParams4.topToTop = imageView2.getId();
        ImageView imageView3 = this.weekBottom;
        Intrinsics.checkNotNull(imageView3);
        layoutParams4.leftToLeft = imageView3.getId();
        ImageView imageView4 = this.weekBottom;
        Intrinsics.checkNotNull(imageView4);
        layoutParams4.rightToRight = imageView4.getId();
        layoutParams4.setMargins(0, 0, 0, 0);
        TextView textView2 = this.weekToday;
        Intrinsics.checkNotNull(textView2);
        textView2.setTextSize(0, getDimen(R.dimen.miui_depth_pets_week_text_size));
        ImageView imageView5 = this.weekBottom;
        Intrinsics.checkNotNull(imageView5);
        imageView5.setLayoutParams(layoutParams2);
        TextView textView3 = this.weekToday;
        Intrinsics.checkNotNull(textView3);
        textView3.setLayoutParams(layoutParams4);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        Log.d("PetHourClockAodView", "onFinishInflate" + this);
        this.guidelineV50 = (Guideline) findViewById(R.id.guideline);
        this.clockSpace = (Space) findViewById(R.id.clock_space);
        this.weekToday = (TextView) findViewById(R.id.week);
        this.timeDot = (ImageView) findViewById(R.id.time_dot);
        this.timeHourLeft = (ImageView) findViewById(R.id.time_hour);
        this.timeHourRight = (ImageView) findViewById(R.id.time_hour_right);
        this.timeMinuteLeft = (ImageView) findViewById(R.id.time_minute);
        this.timeMinuteRight = (ImageView) findViewById(R.id.time_minute_right);
        this.mMonth = (TextView) findViewById(R.id.month);
        this.mTimeDesc = (ConstraintLayout) findViewById(R.id.time_desc);
        this.weekBottom = (ImageView) findViewById(R.id.week_todayis);
        Drawable drawable = getContext().getDrawable(R.drawable.miui_depth_pets_week_bottom);
        ImageView imageView = this.weekBottom;
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
        Drawable drawable2 = getContext().getDrawable(R.drawable.miui_depth_pets_dot);
        if (drawable2 != null) {
            drawable2.setTint(-1);
        }
        ImageView imageView2 = this.timeDot;
        Intrinsics.checkNotNull(imageView2);
        imageView2.setImageDrawable(drawable2);
    }

    public final void setClockInfo(@Nullable ClockStyleInfo clockStyleInfo) {
        Log.d("PetHourClockAodView", "change doodle clock base view");
        Intrinsics.checkNotNull(clockStyleInfo, "null cannot be cast to non-null type com.miui.clock.pets.DepthPetsClockBaseInfo");
        DepthPetsClockBaseInfo depthPetsClockBaseInfo = (DepthPetsClockBaseInfo) clockStyleInfo;
        this.mInfo = depthPetsClockBaseInfo;
        updateViewsLayoutParams();
        updateColor(depthPetsClockBaseInfo.getCurrentPrimaryColor(), depthPetsClockBaseInfo.getCurrentSecondaryColor());
    }

    public final void setClockSpace(@Nullable Space space) {
        this.clockSpace = space;
    }

    public final void setMInfo(@Nullable DepthPetsClockBaseInfo depthPetsClockBaseInfo) {
        this.mInfo = depthPetsClockBaseInfo;
    }

    public final void setMMonth(@Nullable TextView textView) {
        this.mMonth = textView;
    }

    public final void setMTimeDesc(@Nullable ConstraintLayout constraintLayout) {
        this.mTimeDesc = constraintLayout;
    }

    public final void setTimeDot(@Nullable ImageView imageView) {
        this.timeDot = imageView;
    }

    public final void setTimeHourLeft(@Nullable ImageView imageView) {
        this.timeHourLeft = imageView;
    }

    public final void setTimeHourRight(@Nullable ImageView imageView) {
        this.timeHourRight = imageView;
    }

    public final void setTimeMinuteLeft(@Nullable ImageView imageView) {
        this.timeMinuteLeft = imageView;
    }

    public final void setTimeMinuteRight(@Nullable ImageView imageView) {
        this.timeMinuteRight = imageView;
    }

    public final void setWeekBottom(@Nullable ImageView imageView) {
        this.weekBottom = imageView;
    }

    public final void setWeekToday(@Nullable TextView textView) {
        this.weekToday = textView;
    }

    public final void switchWeekAndMonth() {
        int iHashCode;
        String language = Locale.getDefault().getLanguage();
        boolean z = language == null || ((iHashCode = language.hashCode()) == 3201 ? !language.equals("de") : !(iHashCode == 3241 ? language.equals(a.r) : iHashCode == 3886 && language.equals("zh")));
        if (this.switchWeekAndMonthVisible) {
            TextView textView = this.mMonth;
            Intrinsics.checkNotNull(textView);
            int visibility = textView.getVisibility();
            TextView textView2 = this.mMonth;
            Intrinsics.checkNotNull(textView2);
            TextView textView3 = this.weekToday;
            Intrinsics.checkNotNull(textView3);
            textView2.setVisibility(textView3.getVisibility());
            TextView textView4 = this.weekToday;
            Intrinsics.checkNotNull(textView4);
            textView4.setVisibility(visibility);
            DepthPetsClockBaseInfo depthPetsClockBaseInfo = this.mInfo;
            if (depthPetsClockBaseInfo != null) {
                TextView textView5 = this.weekToday;
                if (textView5 != null) {
                    textView5.setTextColor(depthPetsClockBaseInfo.getCurrentPrimaryColor());
                }
                TextView textView6 = this.mMonth;
                Intrinsics.checkNotNull(textView6);
                textView6.setTextColor(depthPetsClockBaseInfo.getCurrentSecondaryColor());
            }
            this.switchWeekAndMonthVisible = false;
        }
        if (z) {
            TextView textView7 = this.weekToday;
            Intrinsics.checkNotNull(textView7);
            ViewGroup.LayoutParams layoutParams = textView7.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
            TextView textView8 = this.mMonth;
            Intrinsics.checkNotNull(textView8);
            ViewGroup.LayoutParams layoutParams3 = textView8.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
            ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) layoutParams3;
            DepthPetsClockBaseInfo depthPetsClockBaseInfo2 = this.mInfo;
            if (depthPetsClockBaseInfo2 != null) {
                TextView textView9 = this.weekToday;
                if (textView9 != null) {
                    textView9.setTextColor(depthPetsClockBaseInfo2.getCurrentSecondaryColor());
                }
                TextView textView10 = this.mMonth;
                Intrinsics.checkNotNull(textView10);
                textView10.setTextColor(depthPetsClockBaseInfo2.getCurrentPrimaryColor());
            }
            TextView textView11 = this.mMonth;
            Intrinsics.checkNotNull(textView11);
            int visibility2 = textView11.getVisibility();
            TextView textView12 = this.mMonth;
            Intrinsics.checkNotNull(textView12);
            TextView textView13 = this.weekToday;
            Intrinsics.checkNotNull(textView13);
            textView12.setVisibility(textView13.getVisibility());
            TextView textView14 = this.weekToday;
            Intrinsics.checkNotNull(textView14);
            textView14.setVisibility(visibility2);
            this.switchWeekAndMonthVisible = true;
            TextView textView15 = this.weekToday;
            Intrinsics.checkNotNull(textView15);
            textView15.setLayoutParams(layoutParams4);
            TextView textView16 = this.mMonth;
            Intrinsics.checkNotNull(textView16);
            textView16.setLayoutParams(layoutParams2);
        }
    }

    public final void updateColor(int i, int i2) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        Drawable drawable4;
        Drawable drawable5;
        StringBuilder sb = new StringBuilder();
        sb.append("updateColor it.currentSecondaryColor ");
        DepthPetsClockBaseInfo depthPetsClockBaseInfo = this.mInfo;
        sb.append(depthPetsClockBaseInfo != null ? Integer.valueOf(depthPetsClockBaseInfo.getCurrentSecondaryColor()) : null);
        sb.append("  currentPrimaryColor  ");
        DepthPetsClockBaseInfo depthPetsClockBaseInfo2 = this.mInfo;
        sb.append(depthPetsClockBaseInfo2 != null ? Integer.valueOf(depthPetsClockBaseInfo2.getCurrentPrimaryColor()) : null);
        sb.append(this);
        Log.d("PetHourClockAodView", sb.toString());
        DepthPetsClockBaseInfo depthPetsClockBaseInfo3 = this.mInfo;
        if (depthPetsClockBaseInfo3 != null) {
            depthPetsClockBaseInfo3.setCurrentPrimaryColor(i);
        }
        DepthPetsClockBaseInfo depthPetsClockBaseInfo4 = this.mInfo;
        if (depthPetsClockBaseInfo4 != null) {
            depthPetsClockBaseInfo4.setCurrentSecondaryColor(i2);
        }
        ImageView imageView = this.timeDot;
        if (imageView != null && (drawable5 = imageView.getDrawable()) != null) {
            drawable5.setTint(i2);
        }
        if (this.switchWeekAndMonthVisible) {
            TextView textView = this.weekToday;
            if (textView != null) {
                textView.setTextColor(i2);
            }
            TextView textView2 = this.mMonth;
            Intrinsics.checkNotNull(textView2);
            textView2.setTextColor(i);
        } else {
            TextView textView3 = this.weekToday;
            if (textView3 != null) {
                textView3.setTextColor(i);
            }
            TextView textView4 = this.mMonth;
            Intrinsics.checkNotNull(textView4);
            textView4.setTextColor(i2);
        }
        ImageView imageView2 = this.timeHourLeft;
        if (imageView2 != null && (drawable4 = imageView2.getDrawable()) != null) {
            drawable4.setTint(i);
        }
        ImageView imageView3 = this.timeHourRight;
        if (imageView3 != null && (drawable3 = imageView3.getDrawable()) != null) {
            drawable3.setTint(i);
        }
        ImageView imageView4 = this.timeMinuteLeft;
        if (imageView4 != null && (drawable2 = imageView4.getDrawable()) != null) {
            drawable2.setTint(i);
        }
        ImageView imageView5 = this.timeMinuteRight;
        if (imageView5 != null && (drawable = imageView5.getDrawable()) != null) {
            drawable.setTint(i);
        }
        ImageView imageView6 = this.timeHourLeft;
        if (imageView6 != null) {
            imageView6.setImageDrawable(imageView6 != null ? imageView6.getDrawable() : null);
        }
        ImageView imageView7 = this.timeHourRight;
        if (imageView7 != null) {
            imageView7.setImageDrawable(imageView7 != null ? imageView7.getDrawable() : null);
        }
        ImageView imageView8 = this.timeMinuteLeft;
        if (imageView8 != null) {
            imageView8.setImageDrawable(imageView8 != null ? imageView8.getDrawable() : null);
        }
        ImageView imageView9 = this.timeMinuteRight;
        if (imageView9 != null) {
            imageView9.setImageDrawable(imageView9 != null ? imageView9.getDrawable() : null);
        }
        Drawable drawable6 = getContext().getDrawable(R.drawable.miui_depth_pets_week_bottom);
        if (drawable6 != null) {
            drawable6.setTint(i);
        }
        ImageView imageView10 = this.weekBottom;
        if (imageView10 != null) {
            imageView10.setImageDrawable(drawable6);
        }
        ImageView imageView11 = this.timeDot;
        if (imageView11 != null) {
            imageView11.setImageDrawable(imageView11 != null ? imageView11.getDrawable() : null);
        }
        invalidate();
    }

    public final void updateTime(boolean z, Calendar calendar) {
        Intrinsics.checkNotNull(calendar);
        int i = calendar.get(20);
        int i2 = calendar.get(18);
        if (!z && (i2 = i2 % 12) == 0) {
            i2 = 12;
        }
        int i3 = i2 / 10;
        int i4 = i2 % 10;
        int i5 = i / 10;
        int i6 = i % 10;
        DepthPetsClockBaseInfo depthPetsClockBaseInfo = this.mInfo;
        int currentPrimaryColor = depthPetsClockBaseInfo != null ? depthPetsClockBaseInfo.getCurrentPrimaryColor() : -1;
        TextView textView = this.weekToday;
        Intrinsics.checkNotNull(textView);
        textView.setText(calendar.format(getContext(), getContext().getString(R.string.classic_calendar_day_of_week_format)));
        Context context = getContext();
        DepthPetsStyle depthPetsStyle = DepthPetsStyle.INSTANCE;
        Drawable drawable = context.getDrawable(depthPetsStyle.getMTimeHourLeftResource()[i3]);
        Drawable drawable2 = getContext().getDrawable(depthPetsStyle.getMTimeHourRightResource()[i4]);
        if (drawable != null) {
            drawable.setTint(currentPrimaryColor);
        }
        if (drawable2 != null) {
            drawable2.setTint(currentPrimaryColor);
        }
        ImageView imageView = this.timeHourLeft;
        Intrinsics.checkNotNull(imageView);
        imageView.setImageDrawable(drawable);
        ImageView imageView2 = this.timeHourRight;
        Intrinsics.checkNotNull(imageView2);
        imageView2.setImageDrawable(drawable2);
        Drawable drawable3 = getContext().getDrawable(depthPetsStyle.getMTimeMinuteLeftResource()[i5]);
        if (drawable3 != null) {
            drawable3.setTint(currentPrimaryColor);
        }
        ImageView imageView3 = this.timeMinuteLeft;
        Intrinsics.checkNotNull(imageView3);
        imageView3.setImageDrawable(drawable3);
        Drawable drawable4 = getContext().getDrawable(depthPetsStyle.getMTimeMinuteRightResource()[i6]);
        if (drawable4 != null) {
            drawable4.setTint(currentPrimaryColor);
        }
        ImageView imageView4 = this.timeMinuteRight;
        Intrinsics.checkNotNull(imageView4);
        imageView4.setImageDrawable(drawable4);
        TextView textView2 = this.mMonth;
        if (textView2 != null) {
            String time = DateFormatUtils.formatTime(getResources().getString(R.string.miui_depth_pets_moth_format));
            Intrinsics.checkNotNullExpressionValue(time, "formatTime(...)");
            Locale ROOT = Locale.ROOT;
            Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
            String upperCase = time.toUpperCase(ROOT);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            textView2.setText(upperCase);
        }
        TextView textView3 = this.mMonth;
        if (textView3 != null) {
            textView3.setContentDescription(calendar.format(getContext(), getContext().getString(R.string.miui_depth_pets_moth_format)));
        }
        int i7 = z ? 32 : 16;
        ConstraintLayout constraintLayout = this.mTimeDesc;
        Intrinsics.checkNotNull(constraintLayout);
        constraintLayout.setContentDescription(DateUtils.formatDateTime(getContext(), System.currentTimeMillis(), i7 | 76));
        String string = getResources().getString(R.string.format_full_day_of_week);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(string);
        TextView textView4 = this.weekToday;
        Intrinsics.checkNotNull(textView4);
        textView4.setContentDescription(simpleDateFormat.format(Long.valueOf(calendar.getTimeInMillis())));
    }

    public void updateViewsLayoutParams() {
        initWeek();
        initTime();
        initDate();
        switchWeekAndMonth();
    }
}
