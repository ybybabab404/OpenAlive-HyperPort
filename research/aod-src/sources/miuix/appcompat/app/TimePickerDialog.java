package miuix.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import miuix.appcompat.R;
import miuix.pickerwidget.widget.TimePicker;

/* JADX INFO: loaded from: classes2.dex */
public class TimePickerDialog extends AlertDialog {
    public final OnTimeSetListener mCallback;
    public int mInitialHourOfDay;
    public int mInitialMinute;
    public boolean mIs24HourView;
    public final TimePicker mTimePicker;

    public interface OnTimeSetListener {
        void onTimeSet(TimePicker timePicker, int i, int i2);
    }

    public TimePickerDialog(Context context, int i, OnTimeSetListener onTimeSetListener, int i2, int i3, boolean z) {
        super(context, i);
        this.mCallback = onTimeSetListener;
        this.mInitialHourOfDay = i2;
        this.mInitialMinute = i3;
        this.mIs24HourView = z;
        setIcon(0);
        setTitle(R.string.time_picker_dialog_title);
        Context context2 = getContext();
        setButton(-1, context2.getText(android.R.string.ok), new DialogInterface.OnClickListener() { // from class: miuix.appcompat.app.TimePickerDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i4) {
                TimePickerDialog.this.tryNotifyTimeSet();
            }
        });
        setButton(-2, getContext().getText(android.R.string.cancel), null);
        View viewInflate = ((LayoutInflater) context2.getSystemService("layout_inflater")).inflate(R.layout.miuix_appcompat_time_picker_dialog, (ViewGroup) null);
        setView(viewInflate);
        TimePicker timePicker = (TimePicker) viewInflate.findViewById(R.id.timePicker);
        this.mTimePicker = timePicker;
        timePicker.set24HourView(Boolean.valueOf(this.mIs24HourView));
        timePicker.setCurrentHour(Integer.valueOf(this.mInitialHourOfDay));
        timePicker.setCurrentMinute(Integer.valueOf(this.mInitialMinute));
        timePicker.setOnTimeChangedListener(null);
    }

    public TimePickerDialog(Context context, OnTimeSetListener onTimeSetListener, int i, int i2, boolean z) {
        this(context, 0, onTimeSetListener, i, i2, z);
    }

    @Override // android.app.Dialog
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        int i = bundle.getInt("miuix:hour");
        int i2 = bundle.getInt("miuix:minute");
        this.mTimePicker.set24HourView(Boolean.valueOf(bundle.getBoolean("miuix:is24hour")));
        this.mTimePicker.setCurrentHour(Integer.valueOf(i));
        this.mTimePicker.setCurrentMinute(Integer.valueOf(i2));
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    public Bundle onSaveInstanceState() {
        Bundle bundleOnSaveInstanceState = super.onSaveInstanceState();
        bundleOnSaveInstanceState.putInt("miuix:hour", this.mTimePicker.getCurrentHour().intValue());
        bundleOnSaveInstanceState.putInt("miuix:minute", this.mTimePicker.getCurrentMinute().intValue());
        bundleOnSaveInstanceState.putBoolean("miuix:is24hour", this.mTimePicker.is24HourView());
        return bundleOnSaveInstanceState;
    }

    public final void tryNotifyTimeSet() {
        if (this.mCallback != null) {
            this.mTimePicker.clearFocus();
            OnTimeSetListener onTimeSetListener = this.mCallback;
            TimePicker timePicker = this.mTimePicker;
            onTimeSetListener.onTimeSet(timePicker, timePicker.getCurrentHour().intValue(), this.mTimePicker.getCurrentMinute().intValue());
        }
    }

    public void updateTime(int i, int i2) {
        this.mTimePicker.setCurrentHour(Integer.valueOf(i));
        this.mTimePicker.setCurrentMinute(Integer.valueOf(i2));
    }
}
