package miuix.appcompat.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import java.util.Calendar;
import java.util.Locale;
import miuix.appcompat.R;
import miuix.pickerwidget.widget.DatePicker;
import miuix.pickerwidget.widget.TimePicker;

/* JADX INFO: loaded from: classes2.dex */
public class DatePickerPanel extends LinearLayout {
    public DatePicker datePicker;
    public LinearLayout datePickerContainer;
    public TextView headerDate;
    public View headerRoot;
    public TextView headerTime;
    public TextView headerTitle;
    public boolean isDateExpanded;
    public boolean isTimeExpanded;
    public int lastExpandedPanel;
    public OnDateChangedListener onDateChangedListener;
    public OnExpandStateChangedListener onExpandStateChangedListener;
    public OnTimeChangedListener onTimeChangedListener;
    public LinearLayout timeContainer;
    public TimePicker timePicker;

    public interface OnDateChangedListener {
        void onDateChanged(DatePickerPanel datePickerPanel, int i, int i2, int i3);
    }

    public interface OnExpandStateChangedListener {
        void onExpandStateChanged(boolean z, boolean z2);
    }

    public interface OnTimeChangedListener {
        void onTimeChanged(DatePickerPanel datePickerPanel, int i, int i2);
    }

    /* JADX INFO: renamed from: $r8$lambda$NcGbd-0TwGFmM793ENeN_vqhLjI, reason: not valid java name */
    public static /* synthetic */ void m1303$r8$lambda$NcGbd0TwGFmM793ENeN_vqhLjI(DatePickerPanel datePickerPanel, View view) {
        boolean z = datePickerPanel.isDateExpanded;
        if (z || datePickerPanel.isTimeExpanded) {
            datePickerPanel.lastExpandedPanel = z ? 1 : 2;
            datePickerPanel.collapseAll();
            return;
        }
        if (datePickerPanel.lastExpandedPanel != 2) {
            if (datePickerPanel.headerDate.getVisibility() == 0) {
                datePickerPanel.toggleDatePicker();
                return;
            } else {
                if (datePickerPanel.headerTime.getVisibility() == 0) {
                    datePickerPanel.toggleTimePicker();
                    return;
                }
                return;
            }
        }
        if (datePickerPanel.headerTime.getVisibility() == 0) {
            datePickerPanel.toggleTimePicker();
        } else if (datePickerPanel.headerDate.getVisibility() == 0) {
            datePickerPanel.toggleDatePicker();
        }
    }

    public static /* synthetic */ void $r8$lambda$proQMDAvvN6hBeiYcTdBsNP7Upk(DatePickerPanel datePickerPanel, DatePicker datePicker, int i, int i2, int i3, boolean z) {
        datePickerPanel.updateHeaderDateText(i, i2, i3);
        OnDateChangedListener onDateChangedListener = datePickerPanel.onDateChangedListener;
        if (onDateChangedListener != null) {
            onDateChangedListener.onDateChanged(datePickerPanel, i, i2, i3);
        }
    }

    public static /* synthetic */ void $r8$lambda$xldXkkND_v8MyAp3ecJAf0EnEEg(DatePickerPanel datePickerPanel, TimePicker timePicker, int i, int i2) {
        datePickerPanel.headerTime.setText(String.format(Locale.ROOT, "%02d:%02d", Integer.valueOf(i), Integer.valueOf(i2)));
        OnTimeChangedListener onTimeChangedListener = datePickerPanel.onTimeChangedListener;
        if (onTimeChangedListener != null) {
            onTimeChangedListener.onTimeChanged(datePickerPanel, i, i2);
        }
    }

    public DatePickerPanel(Context context) {
        this(context, null);
    }

    public DatePickerPanel(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DatePickerPanel(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isDateExpanded = false;
        this.isTimeExpanded = false;
        this.lastExpandedPanel = 0;
        setOrientation(1);
        initView(context);
        initListeners();
        initAttr(context, attributeSet);
        updateDateTimeViews();
    }

    public final void collapseAll() {
        if (this.isTimeExpanded && this.timePicker.isScrolling()) {
            return;
        }
        if (this.isDateExpanded) {
            this.isDateExpanded = false;
            this.headerDate.setSelected(false);
            this.datePickerContainer.setVisibility(8);
        }
        if (this.isTimeExpanded) {
            this.isTimeExpanded = false;
            this.headerTime.setSelected(false);
            this.timeContainer.setVisibility(8);
        }
        notifyExpandStateChanged();
    }

    public int getDayOfMonth() {
        return this.datePicker.getDayOfMonth();
    }

    public int getHour() {
        return this.timePicker.getCurrentHour().intValue();
    }

    public int getMinute() {
        return this.timePicker.getCurrentMinute().intValue();
    }

    public int getMonth() {
        return this.datePicker.getMonth();
    }

    public int getYear() {
        return this.datePicker.getYear();
    }

    public final void initAttr(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DatePickerPanel);
        try {
            String string = typedArrayObtainStyledAttributes.getString(R.styleable.DatePickerPanel_dppTitleText);
            if (!TextUtils.isEmpty(string)) {
                this.headerTitle.setText(string);
            }
            int i = R.styleable.DatePickerPanel_dppShowDatePicker;
            if (typedArrayObtainStyledAttributes.hasValue(i)) {
                setDatePickerVisible(typedArrayObtainStyledAttributes.getBoolean(i, true));
            }
            int i2 = R.styleable.DatePickerPanel_dppShowTimePicker;
            if (typedArrayObtainStyledAttributes.hasValue(i2)) {
                setTimePickerVisible(typedArrayObtainStyledAttributes.getBoolean(i2, true));
            }
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public final void initListeners() {
        this.headerRoot.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.DatePickerPanel$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DatePickerPanel.m1303$r8$lambda$NcGbd0TwGFmM793ENeN_vqhLjI(this.f$0, view);
            }
        });
        this.headerDate.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.DatePickerPanel$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.toggleDatePicker();
            }
        });
        this.headerTime.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.DatePickerPanel$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.toggleTimePicker();
            }
        });
        DatePicker datePicker = this.datePicker;
        datePicker.init(datePicker.getYear(), this.datePicker.getMonth(), this.datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() { // from class: miuix.appcompat.app.DatePickerPanel$$ExternalSyntheticLambda3
            @Override // miuix.pickerwidget.widget.DatePicker.OnDateChangedListener
            public final void onDateChanged(DatePicker datePicker2, int i, int i2, int i3, boolean z) {
                DatePickerPanel.$r8$lambda$proQMDAvvN6hBeiYcTdBsNP7Upk(this.f$0, datePicker2, i, i2, i3, z);
            }
        });
        this.timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() { // from class: miuix.appcompat.app.DatePickerPanel$$ExternalSyntheticLambda4
            @Override // miuix.pickerwidget.widget.TimePicker.OnTimeChangedListener
            public final void onTimeChanged(TimePicker timePicker, int i, int i2) {
                DatePickerPanel.$r8$lambda$xldXkkND_v8MyAp3ecJAf0EnEEg(this.f$0, timePicker, i, i2);
            }
        });
    }

    public final void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.miuix_appcompat_date_picker_panel_layout, (ViewGroup) this, true);
        this.headerTitle = (TextView) findViewById(R.id.header_title);
        this.headerDate = (TextView) findViewById(R.id.header_date);
        this.headerTime = (TextView) findViewById(R.id.header_time);
        this.headerRoot = findViewById(R.id.header_root);
        this.datePickerContainer = (LinearLayout) findViewById(R.id.date_picker_container);
        DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
        this.datePicker = datePicker;
        datePicker.setColumnWidthWrapContent(true);
        this.timeContainer = (LinearLayout) findViewById(R.id.time_container);
        TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
        this.timePicker = timePicker;
        timePicker.set24HourView(Boolean.TRUE);
        this.timePicker.setColumnWidthWrapContent(true);
    }

    public final void notifyExpandStateChanged() {
        OnExpandStateChangedListener onExpandStateChangedListener = this.onExpandStateChangedListener;
        if (onExpandStateChangedListener != null) {
            onExpandStateChangedListener.onExpandStateChanged(this.isDateExpanded, this.isTimeExpanded);
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            updateDateTimeViews();
        }
    }

    public void resetState() {
        if (this.isDateExpanded) {
            this.isDateExpanded = false;
            this.headerDate.setSelected(false);
            this.datePickerContainer.setVisibility(8);
        }
        if (this.isTimeExpanded) {
            this.isTimeExpanded = false;
            this.headerTime.setSelected(false);
            this.timeContainer.setVisibility(8);
        }
        this.lastExpandedPanel = 0;
    }

    public void restoreExpandState(boolean z, boolean z2) {
        if (z && this.headerDate.getVisibility() == 0) {
            this.isDateExpanded = true;
            this.headerDate.setSelected(true);
            this.datePickerContainer.setVisibility(0);
        }
        if (z2 && this.headerTime.getVisibility() == 0) {
            this.isTimeExpanded = true;
            this.headerTime.setSelected(true);
            this.timeContainer.setVisibility(0);
        }
    }

    public void setDate(int i, int i2, int i3) {
        this.datePicker.updateDate(i, i2, i3);
        updateHeaderDateText(i, i2, i3);
    }

    public void setDatePickerVisible(boolean z) {
        this.headerDate.setVisibility(z ? 0 : 8);
        if (z || !this.isDateExpanded) {
            return;
        }
        this.isDateExpanded = false;
        this.headerDate.setSelected(false);
        this.datePickerContainer.setVisibility(8);
    }

    public void setOnDateChangedListener(@Nullable OnDateChangedListener onDateChangedListener) {
        this.onDateChangedListener = onDateChangedListener;
    }

    public void setOnExpandStateChangedListener(@Nullable OnExpandStateChangedListener onExpandStateChangedListener) {
        this.onExpandStateChangedListener = onExpandStateChangedListener;
    }

    public void setOnTimeChangedListener(@Nullable OnTimeChangedListener onTimeChangedListener) {
        this.onTimeChangedListener = onTimeChangedListener;
    }

    public void setTime(int i, int i2) {
        this.timePicker.setCurrentHour(Integer.valueOf(i));
        this.timePicker.setCurrentMinute(Integer.valueOf(i2));
        this.headerTime.setText(String.format(Locale.ROOT, "%02d:%02d", Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public void setTimePickerVisible(boolean z) {
        this.headerTime.setVisibility(z ? 0 : 8);
        if (z || !this.isTimeExpanded) {
            return;
        }
        this.isTimeExpanded = false;
        this.headerTime.setSelected(false);
        this.timeContainer.setVisibility(8);
    }

    public void setTitleText(@Nullable String str) {
        this.headerTitle.setText(str);
    }

    public void toggleDatePicker() {
        if (this.isTimeExpanded && this.timePicker.isScrolling()) {
            return;
        }
        if (this.isTimeExpanded) {
            this.isTimeExpanded = false;
            this.headerTime.setSelected(false);
            this.timeContainer.setVisibility(8);
        }
        boolean z = !this.isDateExpanded;
        this.isDateExpanded = z;
        this.headerDate.setSelected(z);
        this.datePickerContainer.setVisibility(this.isDateExpanded ? 0 : 8);
        if (!this.isDateExpanded) {
            this.lastExpandedPanel = 1;
        }
        notifyExpandStateChanged();
    }

    public void toggleTimePicker() {
        if (this.isTimeExpanded && this.timePicker.isScrolling()) {
            return;
        }
        if (this.isDateExpanded) {
            this.isDateExpanded = false;
            this.headerDate.setSelected(false);
            this.datePickerContainer.setVisibility(8);
        }
        boolean z = !this.isTimeExpanded;
        this.isTimeExpanded = z;
        this.headerTime.setSelected(z);
        this.timeContainer.setVisibility(this.isTimeExpanded ? 0 : 8);
        if (!this.isTimeExpanded) {
            this.lastExpandedPanel = 2;
        }
        notifyExpandStateChanged();
    }

    public final void updateDateTimeViews() {
        updateHeaderDateText(this.datePicker.getYear(), this.datePicker.getMonth(), this.datePicker.getDayOfMonth());
        Calendar calendar = Calendar.getInstance();
        this.headerTime.setText(String.format(Locale.ROOT, "%02d:%02d", Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12))));
    }

    public final void updateHeaderDateText(int i, int i2, int i3) {
        this.headerDate.setText(String.format(Locale.ROOT, "%04d/%02d/%02d", Integer.valueOf(i), Integer.valueOf(i2 + 1), Integer.valueOf(i3)));
    }
}
