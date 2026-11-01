package com.miui.aod.components.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.ViewStub;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.other.step.GoalLiveData;
import com.miui.aod.other.step.StepBean;
import com.miui.aod.other.step.StepLiveData;
import com.miui.aod.other.step.StepUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class WidgetSwitcherGroup extends LinearLayout {
    public static final int WIDGET_BATTERY = 2;
    public static final int WIDGET_DATE_TIME = 1;
    public static final int WIDGET_LUNAR = 5;
    public static final int WIDGET_NOTIFICATION = 3;
    public static final int WIDGET_STEP = 4;
    static SparseIntArray sMap;
    private TextView mStepDesc;
    private TextView mTitle;
    private final SparseArray<WidgetSwitcher> mWidgetMap;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(int i, boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WidgetType {
    }

    public static /* synthetic */ void $r8$lambda$wlnTqAie5QGEq1iZPmNZKlsJqyo(WidgetSwitcherGroup widgetSwitcherGroup, WidgetSwitcher widgetSwitcher, final int i, final OnCheckedChangeListener onCheckedChangeListener, CompoundButton compoundButton, final boolean z) {
        widgetSwitcherGroup.getClass();
        widgetSwitcher.initContentDescrition();
        if (i == 4) {
            widgetSwitcherGroup.updateStepDescState();
        }
        widgetSwitcher.post(new Runnable() { // from class: com.miui.aod.components.widget.WidgetSwitcherGroup$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                onCheckedChangeListener.onCheckedChanged(i, z);
            }
        });
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sMap = sparseIntArray;
        sparseIntArray.put(1, R.id.date_and_time_widget_switcher);
        sMap.put(2, R.id.battery_widget_switcher);
        sMap.put(3, R.id.notification_widget_switcher);
        sMap.put(5, R.id.date_lunar_widget_switcher);
        sMap.put(4, R.id.step_widget_switcher);
    }

    public WidgetSwitcherGroup(Context context) {
        super(context);
        this.mWidgetMap = new SparseArray<>();
    }

    public WidgetSwitcherGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWidgetMap = new SparseArray<>();
    }

    private void initWidget(WidgetSwitcher widgetSwitcher, int i) {
        int i2;
        int i3;
        if (i == 1) {
            i2 = R.string.component_date_time;
            i3 = R.drawable.ic_date_and_time_btn;
        } else if (i == 2) {
            i2 = R.string.component_battery;
            i3 = R.drawable.ic_battery_btn;
        } else if (i == 3) {
            i2 = R.string.component_notification;
            i3 = R.drawable.ic_notification_btn;
        } else if (i == 4) {
            i2 = R.string.component_step;
            i3 = R.drawable.ic_step_btn;
        } else if (i != 5) {
            i2 = 0;
            i3 = 0;
        } else {
            i2 = R.string.component_lunar;
            i3 = R.drawable.ic_lunar_btn;
        }
        if (widgetSwitcher == null || i2 <= 0 || i3 <= 0) {
            return;
        }
        widgetSwitcher.setTitle(getResources().getString(i2), ResourcesCompat.getDrawable(getResources(), i3, null));
    }

    private void setWidgetVisible(int i, boolean z) {
        if (this.mWidgetMap.indexOfKey(i) >= 0) {
            int i2 = 8;
            this.mWidgetMap.get(i).setVisibility(z ? 0 : 8);
            if (i == 4) {
                TextView textView = this.mStepDesc;
                if (z && GoalLiveData.getInstance().getValue() != null && StepUtils.stepActive((StepBean) StepLiveData.getInstance().getValue())) {
                    i2 = 0;
                }
                textView.setVisibility(i2);
            }
        }
    }

    public void enableByStyleInfo(StyleInfo styleInfo) {
        if (styleInfo != null) {
            if (styleInfo.supportDateAndTime()) {
                inflateWidget(1);
                setWidgetVisible(1, true);
            } else {
                setWidgetVisible(1, false);
            }
            if (styleInfo.supportBatteryIcon()) {
                inflateWidget(2);
                setWidgetVisible(2, true);
            } else {
                setWidgetVisible(2, false);
            }
            if (styleInfo.supportNotificationIcon()) {
                inflateWidget(3);
                setWidgetVisible(3, true);
            } else {
                setWidgetVisible(3, false);
            }
            if (styleInfo.supportLunarTime(getContext())) {
                inflateWidget(5);
                setWidgetVisible(5, true);
            } else {
                setWidgetVisible(5, false);
            }
            if (StepUtils.supportStepComponent() && styleInfo.supportStepComponent()) {
                inflateWidget(4);
                setWidgetVisible(4, true);
            } else {
                setWidgetVisible(4, false);
            }
            this.mTitle.setVisibility(this.mWidgetMap.size() <= 0 ? 8 : 0);
        }
    }

    public WidgetSwitcher getWidgetSwitcher(int i) {
        return this.mWidgetMap.get(i);
    }

    public void inflateWidget(int i) {
        if (this.mWidgetMap.indexOfKey(i) < 0) {
            WidgetSwitcher widgetSwitcher = (WidgetSwitcher) ((ViewStub) findViewById(sMap.get(i))).inflate();
            initWidget(widgetSwitcher, i);
            this.mWidgetMap.put(i, widgetSwitcher);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mStepDesc = (TextView) findViewById(R.id.step_desc);
        this.mTitle = (TextView) findViewById(R.id.group_title);
    }

    public void setCheckChangeListener(final OnCheckedChangeListener onCheckedChangeListener) {
        int size = this.mWidgetMap.size();
        for (int i = 0; i < size; i++) {
            final WidgetSwitcher widgetSwitcherValueAt = this.mWidgetMap.valueAt(i);
            final int iKeyAt = this.mWidgetMap.keyAt(i);
            widgetSwitcherValueAt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.components.widget.WidgetSwitcherGroup$$ExternalSyntheticLambda1
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    WidgetSwitcherGroup.$r8$lambda$wlnTqAie5QGEq1iZPmNZKlsJqyo(this.f$0, widgetSwitcherValueAt, iKeyAt, onCheckedChangeListener, compoundButton, z);
                }
            });
        }
    }

    public void setCheckedByType(int i, boolean z) {
        if (this.mWidgetMap.indexOfKey(i) >= 0) {
            this.mWidgetMap.get(i).setChecked(z);
        }
    }

    public void update(StyleInfo styleInfo) {
        setCheckedByType(1, styleInfo.isDateAndTimeSwitchOn());
        setCheckedByType(2, styleInfo.isBatterySwitchOn());
        setCheckedByType(3, styleInfo.isNotificationSwitchOn());
        setCheckedByType(5, styleInfo.isLunarSwitchOn(getContext()));
        setCheckedByType(4, styleInfo.isStepComponentSwitchOn());
    }

    public void updateStepDescState() {
        WidgetSwitcher widgetSwitcher = getWidgetSwitcher(4);
        int i = 8;
        if (widgetSwitcher == null || !widgetSwitcher.isChecked()) {
            this.mStepDesc.setVisibility(8);
            return;
        }
        TextView textView = this.mStepDesc;
        if (widgetSwitcher.getVisibility() == 0 && GoalLiveData.getInstance().getValue() != null) {
            i = 0;
        }
        textView.setVisibility(i);
    }
}
