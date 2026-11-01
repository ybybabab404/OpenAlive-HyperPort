package com.miui.aod.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.util.Consumer;
import androidx.core.view.ViewKt;
import com.android.systemui.miui.statusbar.BatteryMeterIconView;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.SquareVerticalTextView;
import com.miui.aod.components.view.VerticalTextView;
import com.miui.aod.icu.ICUManager;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.util.ImageUtils;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class SignatureClock implements ISignatureAodClock {
    public Calendar mCal;
    public Calendar mCalDual;
    public TextView mCity;
    public TextView mCity2;
    public TextView mDateView;
    public TextView mDateViewDual;
    public ImageView mImageView;
    public final boolean mIsDual;
    public int mLayoutId;
    public SignatureAodView mSignatureAodView;
    public View mSignatureContainer;
    public final int mSize;
    public TextView mTimeView;
    public TimeZone mTimeZone;
    public TimeZone mTimeZoneDual;

    public static /* synthetic */ void $r8$lambda$5E_N13rdCK3lVArS1vLf84YZR2o(SignatureClock signatureClock, Context context, boolean z, String str) {
        signatureClock.getClass();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" " + signatureClock.mCal.format(context, context.getResources().getString(z ? R.string.aod_dual_togother : R.string.aod_signature_12)));
        sb.append(" " + signatureClock.mCal.format(context, context.getResources().getString(R.string.aod_lock_screen_date_signature)));
        signatureClock.mCity.setText(sb);
    }

    public static /* synthetic */ void $r8$lambda$RFc2veODrzI_RHMaxVoobEe4IEM(SignatureClock signatureClock, Context context, TextView textView) {
        signatureClock.getClass();
        textView.setText(signatureClock.mCal.format(context, context.getResources().getString(R.string.aod_lock_screen_date_signature)));
    }

    public SignatureClock(boolean z, int i) {
        this.mSize = i;
        this.mIsDual = z;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        if (this.mLayoutId == R.layout.aod_content_signature_bitmap) {
            ImageView imageView = (ImageView) view.findViewById(R.id.signature_image);
            this.mImageView = imageView;
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = Utils.getSmallViewWidth(view.getContext());
            layoutParams.height = Utils.getSmallViewHeight(this.mImageView.getContext());
            return;
        }
        View viewFindViewById = view.findViewById(R.id.signature_container);
        this.mSignatureContainer = viewFindViewById;
        if (viewFindViewById instanceof SignatureAodView) {
            SignatureAodView signatureAodView = (SignatureAodView) viewFindViewById;
            this.mSignatureAodView = signatureAodView;
            ViewGroup.LayoutParams layoutParams2 = signatureAodView.getLayoutParams();
            if ((layoutParams2 instanceof LinearLayout.LayoutParams) && this.mSize == 2) {
                ((LinearLayout.LayoutParams) layoutParams2).gravity = 8388659;
            }
            LinearLayout linearLayout = (LinearLayout) this.mSignatureAodView.findViewById(R.id.clock_horizontal_container);
            if (linearLayout != null && this.mLayoutId == R.layout.aod_content_signature_dual) {
                linearLayout.setOrientation(1);
            }
        }
        this.mTimeView = (TextView) view.findViewById(R.id.clock_horizontal);
        this.mDateView = (TextView) view.findViewById(R.id.date);
        if (this.mIsDual) {
            this.mCity = (TextView) view.findViewById(R.id.city);
            this.mCity2 = (TextView) view.findViewById(R.id.city2);
            this.mDateViewDual = (TextView) view.findViewById(R.id.date_dual);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        int i = this.mSize;
        if (2 == i) {
            if (TextUtils.isEmpty(styleInfo.getClockBgName())) {
                this.mLayoutId = R.layout.aod_content_signature_small;
            } else {
                this.mLayoutId = R.layout.aod_content_signature_bitmap;
            }
        } else if (!this.mIsDual || 4 == i) {
            this.mLayoutId = R.layout.aod_content_signature;
        } else {
            this.mLayoutId = R.layout.aod_content_signature_dual;
        }
        return this.mLayoutId;
    }

    @Override // com.miui.aod.widget.ISignatureAodClock
    public String getShowedContent() {
        SignatureAodView signatureAodView = this.mSignatureAodView;
        if (signatureAodView != null) {
            return signatureAodView.getShowContent();
        }
        return null;
    }

    @Override // com.miui.aod.widget.ISignatureAodClock
    public VerticalTextView.TextFormat getTextFormat() {
        SignatureAodView signatureAodView = this.mSignatureAodView;
        if (signatureAodView != null) {
            return signatureAodView.getShowTextFormat();
        }
        return null;
    }

    public SignatureAodView getView() {
        return this.mSignatureAodView;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void onInflateBatteryComplete() {
        View view;
        View view2 = this.mSignatureContainer;
        if (view2 != null) {
            Object parent = view2.getParent();
            while (true) {
                view = (View) parent;
                if (view.getParent() == null || !(view.getParent() instanceof View) || view.getId() == R.id.aod_content_container) {
                    break;
                } else {
                    parent = view.getParent();
                }
            }
            BatteryMeterIconView batteryMeterIconView = (BatteryMeterIconView) view.findViewById(R.id.aod_battery_icon);
            if (batteryMeterIconView != null) {
                batteryMeterIconView.setAlpha(0.6f);
            }
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void releaseDrawable() {
        ImageView imageView = this.mImageView;
        if (imageView != null) {
            imageView.setImageBitmap(null);
            this.mImageView.setImageDrawable(null);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone2(TimeZone timeZone) {
        this.mTimeZoneDual = timeZone;
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        SquareVerticalTextView signatureContent;
        SignatureAodView signatureAodView = this.mSignatureAodView;
        if (signatureAodView == null) {
            if (this.mImageView != null) {
                ImageUtils.loadIntoImage(this.mImageView, AodDrawables.getDrawableDataByName(styleInfo.getClockBgName()).mResId, 0);
                return;
            }
            return;
        }
        signatureAodView.update(styleInfo, i);
        if (this.mLayoutId == R.layout.aod_content_signature_dual && (signatureContent = this.mSignatureAodView.getSignatureContent()) != null && (styleInfo instanceof TextStyleCategoryInfo) && ((TextStyleCategoryInfo) styleInfo).isVertical()) {
            int dimensionPixelSize = this.mSignatureAodView.getResources().getDimensionPixelSize(R.dimen.signature_padding_dual);
            signatureContent.setPadding(signatureContent.getPaddingLeft() == 0 ? 0 : dimensionPixelSize, signatureContent.getPaddingTop() == 0 ? 0 : dimensionPixelSize, signatureContent.getPaddingRight() == 0 ? 0 : dimensionPixelSize, signatureContent.getPaddingBottom() != 0 ? dimensionPixelSize : 0);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(final boolean z) {
        SignatureAodView signatureAodView = this.mSignatureAodView;
        if (signatureAodView != null) {
            signatureAodView.set24GradientColor(AodDrawables.get24GradientDrawableRes());
            TimeZone timeZone = this.mTimeZone;
            if (timeZone == null) {
                timeZone = TimeZone.getDefault();
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.mCal = Utils.refreshCalendar(this.mCal, timeZone, jCurrentTimeMillis);
            final Context context = this.mSignatureAodView.getContext();
            if (!this.mIsDual) {
                ViewUtil.doIfNotNull(this.mTimeView, new Consumer() { // from class: com.miui.aod.widget.SignatureClock$$ExternalSyntheticLambda1
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj) {
                        SignatureClock signatureClock = this.f$0;
                        Context context2 = context;
                        ((TextView) obj).setText(signatureClock.mCal.format(context2, context2.getResources().getString(z ? R.string.aod_dual_togother : R.string.aod_signature_12)));
                    }
                });
                ViewUtil.doIfNotNull(this.mDateView, new Consumer() { // from class: com.miui.aod.widget.SignatureClock$$ExternalSyntheticLambda2
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj) {
                        SignatureClock.$r8$lambda$RFc2veODrzI_RHMaxVoobEe4IEM(this.f$0, context, (TextView) obj);
                    }
                });
                return;
            }
            TextView textView = this.mCity;
            if (textView != null && ViewKt.isVisible(textView)) {
                ICUManager.INSTANCE.loadCity(context, TimeZone.getDefault(), new Consumer() { // from class: com.miui.aod.widget.SignatureClock$$ExternalSyntheticLambda0
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj) {
                        SignatureClock.$r8$lambda$5E_N13rdCK3lVArS1vLf84YZR2o(this.f$0, context, z, (String) obj);
                    }
                });
            }
            TextView textView2 = this.mCity2;
            if (textView2 != null && ViewKt.isVisible(textView2)) {
                ICUManager.INSTANCE.loadCity(this.mCity2, context, this.mTimeZoneDual);
            }
            if (this.mDateViewDual != null) {
                String string = context.getResources().getString(z ? R.string.aod_dual_clock_date : R.string.aod_dual_clock_date_12);
                Calendar calendarRefreshCalendar = Utils.refreshCalendar(this.mCalDual, this.mTimeZoneDual, jCurrentTimeMillis);
                this.mCalDual = calendarRefreshCalendar;
                if (AODSettings.isSameDate(this.mCal, calendarRefreshCalendar)) {
                    string = context.getResources().getString(z ? R.string.aod_dual_togother : R.string.aod_dual_togother_12);
                }
                this.mDateViewDual.setText(this.mCalDual.format(context, string));
            }
        }
    }
}
