package miuix.stretchablewidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;
import miuix.pickerwidget.widget.DateTimePicker;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes.dex */
public class StretchableDatePicker extends StretchableWidget {
    public Calendar mCalendar;
    public DateTimePicker mDateTimePicker;
    public boolean mIsLunar;
    public SlidingButton mLunarButton;
    public DateTimePicker.LunarFormatter mLunarFormatter;
    public RelativeLayout mLunarLayout;
    public String mLunarResId;
    public TextView mLunarText;
    public int mMinuteInterval;
    public OnTimeChangeListener mOnTimeChangeListener;
    public LinearLayout mPickerContainer;
    public boolean mShowLunar;
    public long mTime;
    public int pickerContainerHeight;

    public interface OnTimeChangeListener {
        long onDateTimeChanged(long j);
    }

    public static /* synthetic */ void $r8$lambda$XiKZilUB6ig4pozaCiX4sp8ciD8(StretchableDatePicker stretchableDatePicker, AccessibilityManager accessibilityManager, View view) {
        stretchableDatePicker.getClass();
        if (accessibilityManager != null && accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            stretchableDatePicker.mLunarButton.toggle();
        }
    }

    public StretchableDatePicker(Context context) {
        this(context, null);
    }

    public StretchableDatePicker(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StretchableDatePicker(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMinuteInterval = 1;
    }

    @Override // miuix.stretchablewidget.StretchableWidget
    public void afterSetView() {
        this.mHeight = this.pickerContainerHeight;
    }

    public final String formatLunarTime(long j, Context context) {
        return this.mLunarFormatter.formatDay(this.mCalendar.get(1), this.mCalendar.get(5), this.mCalendar.get(9)) + " " + DateUtils.formatDateTime(context, j, 12);
    }

    public final String formatSolorTime(long j, Context context) {
        return DateUtils.formatDateTime(context, j, 908);
    }

    public long getTime() {
        return this.mTime;
    }

    public final void init(final Context context, AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.StretchableDatePicker, i, 0);
        this.mShowLunar = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StretchableDatePicker_show_lunar, false);
        this.mLunarResId = typedArrayObtainStyledAttributes.getString(R.styleable.StretchableDatePicker_lunar_text);
        this.mMinuteInterval = typedArrayObtainStyledAttributes.getInteger(R.styleable.StretchableDatePicker_minuteInterval, 1);
        typedArrayObtainStyledAttributes.recycle();
        LinearLayout linearLayout = (LinearLayout) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.miuix_stretchable_widget_picker_part, (ViewGroup) null);
        this.mPickerContainer = linearLayout;
        this.mDateTimePicker = (DateTimePicker) linearLayout.findViewById(R.id.datetime_picker);
        this.mLunarLayout = (RelativeLayout) this.mPickerContainer.findViewById(R.id.lunar_layout);
        this.mLunarText = (TextView) this.mPickerContainer.findViewById(R.id.lunar_text);
        this.mLunarButton = (SlidingButton) this.mPickerContainer.findViewById(R.id.lunar_button);
        if (!this.mShowLunar) {
            this.mLunarLayout.setVisibility(8);
        }
        final AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        ViewCompat.setAccessibilityDelegate(this.mLunarLayout, new AccessibilityDelegateCompat() { // from class: miuix.stretchablewidget.StretchableDatePicker.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCheckable(true);
                accessibilityNodeInfoCompat.setChecked(StretchableDatePicker.this.mLunarButton.isChecked());
                accessibilityNodeInfoCompat.setClickable(true);
                accessibilityNodeInfoCompat.setClassName(Switch.class.getName());
                accessibilityNodeInfoCompat.setContentDescription(StretchableDatePicker.this.mLunarText.getText());
            }
        });
        this.mLunarLayout.setOnClickListener(new View.OnClickListener() { // from class: miuix.stretchablewidget.StretchableDatePicker$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StretchableDatePicker.$r8$lambda$XiKZilUB6ig4pozaCiX4sp8ciD8(this.f$0, accessibilityManager, view);
            }
        });
        this.mLunarButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: miuix.stretchablewidget.StretchableDatePicker.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                StretchableDatePicker.this.mDateTimePicker.setLunarMode(z);
                StretchableDatePicker.this.showTime(z, context);
                StretchableDatePicker.this.mIsLunar = z;
            }
        });
        this.mPickerContainer.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        this.pickerContainerHeight = this.mPickerContainer.getMeasuredHeight();
        setLayout(this.mPickerContainer);
        this.mCalendar = new Calendar();
        setLunarText(this.mLunarResId);
        this.mLunarFormatter = new DateTimePicker.LunarFormatter(context);
        setMinuteInterval(this.mMinuteInterval);
        showSolarTime(context);
        this.mTime = this.mCalendar.getTimeInMillis();
        this.mDateTimePicker.setOnTimeChangedListener(new DateTimePicker.OnDateTimeChangedListener() { // from class: miuix.stretchablewidget.StretchableDatePicker.3
            @Override // miuix.pickerwidget.widget.DateTimePicker.OnDateTimeChangedListener
            public void onDateTimeChanged(DateTimePicker dateTimePicker, long j) {
                StretchableDatePicker.this.mCalendar.setTimeInMillis(j);
                StretchableDatePicker stretchableDatePicker = StretchableDatePicker.this;
                stretchableDatePicker.showTime(stretchableDatePicker.mIsLunar, context);
                StretchableDatePicker.this.mTime = j;
                if (StretchableDatePicker.this.mOnTimeChangeListener != null) {
                    StretchableDatePicker.this.mOnTimeChangeListener.onDateTimeChanged(j);
                }
            }
        });
    }

    @Override // miuix.stretchablewidget.StretchableWidget
    public void preSetView(Context context, AttributeSet attributeSet, int i) {
        init(context, attributeSet, i);
    }

    public void setLunarModeOn(boolean z) {
        SlidingButton slidingButton = this.mLunarButton;
        if (slidingButton != null) {
            slidingButton.setChecked(z);
        }
    }

    public void setLunarText(String str) {
        this.mLunarText.setText(str);
    }

    public void setMinuteInterval(int i) {
        this.mDateTimePicker.setMinuteInterval(i);
    }

    public void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener) {
        this.mOnTimeChangeListener = onTimeChangeListener;
    }

    public void showLunarTime(Context context) {
        setDetailMessage(formatLunarTime(this.mCalendar.getTimeInMillis(), context));
    }

    public final void showSolarTime(Context context) {
        setDetailMessage(formatSolorTime(this.mCalendar.getTimeInMillis(), context));
    }

    public final void showTime(boolean z, Context context) {
        if (z) {
            showLunarTime(context);
        } else {
            showSolarTime(context);
        }
    }
}
