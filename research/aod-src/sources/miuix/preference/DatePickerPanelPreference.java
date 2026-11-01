package miuix.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.preference.PreferenceViewHolder;
import miuix.appcompat.app.DatePickerPanel;

/* JADX INFO: loaded from: classes2.dex */
public class DatePickerPanelPreference extends BasePreference {
    public boolean mDateExpanded;
    public DatePickerPanel.OnDateChangedListener mOnDateChangedListener;
    public DatePickerPanel.OnTimeChangedListener mOnTimeChangedListener;
    public DatePickerPanel mPanel;
    public int mSavedDay;
    public int mSavedHour;
    public int mSavedMinute;
    public int mSavedMonth;
    public int mSavedYear;
    public boolean mShowDatePicker;
    public boolean mShowTimePicker;
    public boolean mTimeExpanded;
    public String mTitleText;

    public static /* synthetic */ void $r8$lambda$C6klxod5XGnIDRCac0ocNsna_r0(DatePickerPanelPreference datePickerPanelPreference, boolean z, boolean z2) {
        datePickerPanelPreference.mDateExpanded = z;
        datePickerPanelPreference.mTimeExpanded = z2;
    }

    /* JADX INFO: renamed from: $r8$lambda$M-fsB77tYffBKZfA5phnGEyAOew, reason: not valid java name */
    public static /* synthetic */ void m1347$r8$lambda$MfsB77tYffBKZfA5phnGEyAOew(DatePickerPanelPreference datePickerPanelPreference, DatePickerPanel datePickerPanel, int i, int i2) {
        datePickerPanelPreference.mSavedHour = i;
        datePickerPanelPreference.mSavedMinute = i2;
        DatePickerPanel.OnTimeChangedListener onTimeChangedListener = datePickerPanelPreference.mOnTimeChangedListener;
        if (onTimeChangedListener != null) {
            onTimeChangedListener.onTimeChanged(datePickerPanel, i, i2);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$wVGd7HyG5y5C3vt8R_s-9AAJST4, reason: not valid java name */
    public static /* synthetic */ void m1348$r8$lambda$wVGd7HyG5y5C3vt8R_s9AAJST4(DatePickerPanelPreference datePickerPanelPreference, DatePickerPanel datePickerPanel, int i, int i2, int i3) {
        datePickerPanelPreference.mSavedYear = i;
        datePickerPanelPreference.mSavedMonth = i2;
        datePickerPanelPreference.mSavedDay = i3;
        DatePickerPanel.OnDateChangedListener onDateChangedListener = datePickerPanelPreference.mOnDateChangedListener;
        if (onDateChangedListener != null) {
            onDateChangedListener.onDateChanged(datePickerPanel, i, i2, i3);
        }
    }

    public DatePickerPanelPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.datePickerPanelPreferenceStyle);
    }

    public DatePickerPanelPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShowDatePicker = true;
        this.mShowTimePicker = true;
        this.mDateExpanded = false;
        this.mTimeExpanded = false;
        this.mSavedYear = -1;
        this.mSavedMonth = -1;
        this.mSavedDay = -1;
        this.mSavedHour = -1;
        this.mSavedMinute = -1;
        setTouchAnimationEnable(false);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DatePickerPanelPreference, i, 0);
        this.mTitleText = typedArrayObtainStyledAttributes.getString(R.styleable.DatePickerPanelPreference_dpp_panel_title);
        this.mShowDatePicker = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DatePickerPanelPreference_dpp_show_date_picker, true);
        this.mShowTimePicker = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DatePickerPanelPreference_dpp_show_time_picker, true);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // miuix.preference.BasePreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        DatePickerPanel datePickerPanel = (DatePickerPanel) preferenceViewHolder.itemView;
        this.mPanel = datePickerPanel;
        datePickerPanel.resetState();
        this.mPanel.setTitleText(this.mTitleText);
        this.mPanel.setDatePickerVisible(this.mShowDatePicker);
        this.mPanel.setTimePickerVisible(this.mShowTimePicker);
        int i = this.mSavedYear;
        if (i > 0) {
            this.mPanel.setDate(i, this.mSavedMonth, this.mSavedDay);
        }
        int i2 = this.mSavedHour;
        if (i2 >= 0) {
            this.mPanel.setTime(i2, this.mSavedMinute);
        }
        this.mPanel.restoreExpandState(this.mDateExpanded, this.mTimeExpanded);
        this.mPanel.setOnDateChangedListener(new DatePickerPanel.OnDateChangedListener() { // from class: miuix.preference.DatePickerPanelPreference$$ExternalSyntheticLambda0
            @Override // miuix.appcompat.app.DatePickerPanel.OnDateChangedListener
            public final void onDateChanged(DatePickerPanel datePickerPanel2, int i3, int i4, int i5) {
                DatePickerPanelPreference.m1348$r8$lambda$wVGd7HyG5y5C3vt8R_s9AAJST4(this.f$0, datePickerPanel2, i3, i4, i5);
            }
        });
        this.mPanel.setOnTimeChangedListener(new DatePickerPanel.OnTimeChangedListener() { // from class: miuix.preference.DatePickerPanelPreference$$ExternalSyntheticLambda1
            @Override // miuix.appcompat.app.DatePickerPanel.OnTimeChangedListener
            public final void onTimeChanged(DatePickerPanel datePickerPanel2, int i3, int i4) {
                DatePickerPanelPreference.m1347$r8$lambda$MfsB77tYffBKZfA5phnGEyAOew(this.f$0, datePickerPanel2, i3, i4);
            }
        });
        this.mPanel.setOnExpandStateChangedListener(new DatePickerPanel.OnExpandStateChangedListener() { // from class: miuix.preference.DatePickerPanelPreference$$ExternalSyntheticLambda2
            @Override // miuix.appcompat.app.DatePickerPanel.OnExpandStateChangedListener
            public final void onExpandStateChanged(boolean z, boolean z2) {
                DatePickerPanelPreference.$r8$lambda$C6klxod5XGnIDRCac0ocNsna_r0(this.f$0, z, z2);
            }
        });
    }
}
