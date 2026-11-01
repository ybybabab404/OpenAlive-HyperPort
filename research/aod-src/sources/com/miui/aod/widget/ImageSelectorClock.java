package com.miui.aod.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewStub;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.ImageSelectorCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.IPreAodView;
import com.miui.aod.components.view.ImageSelectorShowerView;
import com.miui.aod.icu.ICUManager;
import com.miui.aod.resource.AodDrawables;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class ImageSelectorClock implements IAodClock, IPreAodView {
    public Calendar mCal;
    public Calendar mCalDual;
    public ImageSelectorShowerView mImageSelectorShowerView;
    public final boolean mIsDual;
    public final int mSize;
    public ImageSelectorCategoryInfo mStyleInfo;
    public TimeZone mTimeZone;
    public TimeZone mTimeZone2;

    public ImageSelectorClock(int i, boolean z) {
        this.mSize = i;
        this.mIsDual = z;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        ViewStub viewStub;
        final View viewFindViewById = view.findViewById(R.id.image_selector_shower);
        if (viewFindViewById instanceof ImageSelectorShowerView) {
            this.mImageSelectorShowerView = (ImageSelectorShowerView) viewFindViewById;
            if (this.mSize == 1 && Utils.isImageShareAvailable(viewFindViewById.getContext()) && (viewStub = (ViewStub) view.getRootView().findViewById(R.id.image_share_tip)) != null) {
                MessageArrowView messageArrowView = (MessageArrowView) viewStub.inflate();
                messageArrowView.setContentDes(messageArrowView.getContext().getResources().getString(R.string.step_to_image_community));
                messageArrowView.setMarginArrowLeft(0);
                ViewUtil.setMarginTop(messageArrowView, viewFindViewById.getContext().getResources().getDimensionPixelSize(R.dimen.aod_setting_step_to_image_community_margin_top));
                messageArrowView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.widget.ImageSelectorClock$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        Utils.stepToImageCommunity(viewFindViewById.getContext());
                    }
                });
            }
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        return R.layout.image_selector_clock_layout;
    }

    public ImageSelectorShowerView getView() {
        return this.mImageSelectorShowerView;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setClockMask(int i, int i2) {
        if (this.mSize != 2 && i2 > 0) {
            ImageSelectorShowerView imageSelectorShowerView = this.mImageSelectorShowerView;
            imageSelectorShowerView.mGradientLayout.setGradientOverlayDrawable(AodDrawables.getDrawableForMask(imageSelectorShowerView.getContext(), i2));
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone2(TimeZone timeZone) {
        this.mTimeZone2 = timeZone;
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        if (styleInfo instanceof ImageSelectorCategoryInfo) {
            this.mStyleInfo = (ImageSelectorCategoryInfo) styleInfo;
            this.mImageSelectorShowerView.update(styleInfo, i);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        int iCeil;
        if (this.mSize == 2) {
            return;
        }
        Context context = this.mImageSelectorShowerView.getContext();
        TimeZone timeZone = TimeZone.getDefault();
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.mCal = Utils.refreshCalendar(this.mCal, timeZone, jCurrentTimeMillis);
        new SimpleDateFormat(Utils.getHourMinformat(context)).setTimeZone(timeZone);
        int i = this.mCal.get(18);
        if (!z && i > 12) {
            i -= 12;
        }
        int i2 = (z || i != 0) ? i : 12;
        if (this.mImageSelectorShowerView.mClockHorizontal != null) {
            String str = String.format("%d:%02d", Integer.valueOf(i2), Integer.valueOf(this.mCal.get(20)));
            this.mImageSelectorShowerView.mClockHorizontal.setText(str);
            iCeil = (int) Math.ceil(this.mImageSelectorShowerView.mClockHorizontal.getPaint().measureText(str));
        } else {
            iCeil = 0;
        }
        String str2 = this.mCal.format(context, AODSettings.getDateFormat(context, z, false));
        ImageSelectorShowerView imageSelectorShowerView = this.mImageSelectorShowerView;
        imageSelectorShowerView.setMinimumWidth(iCeil + ((int) Math.ceil(imageSelectorShowerView.mDateView.getPaint().measureText(str2))) + this.mImageSelectorShowerView.mDateView.getPaddingLeft());
        this.mImageSelectorShowerView.mDateView.setText(str2);
        if (this.mImageSelectorShowerView.mDateLunar != null) {
            if (this.mStyleInfo.isLunarSwitchOn(context)) {
                this.mImageSelectorShowerView.mDateLunar.setVisibility(this.mStyleInfo.getExtraInfoShowStyle() == 0 ? 8 : 0);
                this.mImageSelectorShowerView.mDateLunar.setText(this.mCal.format(context, AODSettings.getDateFormat(context, z, true)));
            } else {
                this.mImageSelectorShowerView.mDateLunar.setVisibility(8);
            }
        }
        if (!this.mIsDual) {
            this.mImageSelectorShowerView.mDateDualContainer.setVisibility(8);
            return;
        }
        this.mImageSelectorShowerView.mDateDualContainer.setVisibility(this.mStyleInfo.getExtraInfoShowStyle() == 0 ? 8 : 0);
        TimeZone timeZone2 = this.mTimeZone2;
        if (timeZone2 == null) {
            timeZone2 = TimeZone.getDefault();
        }
        ICUManager.INSTANCE.loadCity(this.mImageSelectorShowerView.mCity2, context, timeZone2);
        this.mCalDual = Utils.refreshCalendar(this.mCalDual, timeZone2, jCurrentTimeMillis);
        String string = context.getResources().getString(z ? R.string.aod_dual_clock_date : R.string.aod_dual_clock_date_12);
        if (AODSettings.isSameDate(this.mCal, this.mCalDual)) {
            string = context.getResources().getString(z ? R.string.aod_dual_togother : R.string.aod_dual_togother_12);
        }
        this.mImageSelectorShowerView.mDateDual.setText(this.mCalDual.format(context, string));
    }
}
