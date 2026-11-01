package miuix.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.preference.PreferenceViewHolder;
import miuix.appcompat.app.NumberPickerPanel;

/* JADX INFO: loaded from: classes2.dex */
public class NumberPickerPanelPreference extends BasePreference {
    public int mDefaultValue;
    public boolean mExpanded;
    public int mMaxValue;
    public int mMinValue;
    public NumberPickerPanel.OnValueChangedListener mOnValueChangedListener;
    public NumberPickerPanel.OnValueConfirmedListener mOnValueConfirmedListener;
    public NumberPickerPanel mPanel;
    public String mPickerLabel;
    public int mSavedValue;
    public String mTitleText;
    public String mUnitText;

    /* JADX INFO: renamed from: $r8$lambda$1dWo5Ql-S9vELTZ4628uxmDZ-1o, reason: not valid java name */
    public static /* synthetic */ void m1349$r8$lambda$1dWo5QlS9vELTZ4628uxmDZ1o(NumberPickerPanelPreference numberPickerPanelPreference, NumberPickerPanel numberPickerPanel, int i) {
        numberPickerPanelPreference.mSavedValue = i;
        NumberPickerPanel.OnValueChangedListener onValueChangedListener = numberPickerPanelPreference.mOnValueChangedListener;
        if (onValueChangedListener != null) {
            onValueChangedListener.onValueChanged(numberPickerPanel, i);
        }
    }

    public static /* synthetic */ void $r8$lambda$IC4ZUR7Ibo6bw6TcXUtKD4RoeMQ(NumberPickerPanelPreference numberPickerPanelPreference, NumberPickerPanel numberPickerPanel, int i) {
        NumberPickerPanel.OnValueConfirmedListener onValueConfirmedListener = numberPickerPanelPreference.mOnValueConfirmedListener;
        if (onValueConfirmedListener != null) {
            onValueConfirmedListener.onValueConfirmed(numberPickerPanel, i);
        }
    }

    public NumberPickerPanelPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.numberPickerPanelPreferenceStyle);
    }

    public NumberPickerPanelPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMinValue = 1;
        this.mMaxValue = 10;
        this.mDefaultValue = 1;
        this.mExpanded = false;
        this.mSavedValue = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.NumberPickerPanelPreference, i, 0);
        this.mTitleText = typedArrayObtainStyledAttributes.getString(R.styleable.NumberPickerPanelPreference_npp_panel_title);
        this.mUnitText = typedArrayObtainStyledAttributes.getString(R.styleable.NumberPickerPanelPreference_npp_unit_text);
        this.mPickerLabel = typedArrayObtainStyledAttributes.getString(R.styleable.NumberPickerPanelPreference_npp_picker_label);
        this.mMinValue = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPickerPanelPreference_npp_min_value, 1);
        this.mMaxValue = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPickerPanelPreference_npp_max_value, 10);
        this.mDefaultValue = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPickerPanelPreference_npp_default_value, this.mMinValue);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // miuix.preference.BasePreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        NumberPickerPanel numberPickerPanel = (NumberPickerPanel) preferenceViewHolder.itemView;
        this.mPanel = numberPickerPanel;
        numberPickerPanel.resetState();
        this.mPanel.setTitleText(this.mTitleText);
        this.mPanel.setUnitText(this.mUnitText);
        this.mPanel.setPickerLabel(this.mPickerLabel);
        this.mPanel.setMinValue(this.mMinValue);
        this.mPanel.setMaxValue(this.mMaxValue);
        int i = this.mSavedValue;
        if (i >= 0) {
            this.mPanel.setValue(i);
        } else {
            this.mPanel.setValue(this.mDefaultValue);
        }
        this.mPanel.restoreExpandState(this.mExpanded);
        this.mPanel.setOnValueChangedListener(new NumberPickerPanel.OnValueChangedListener() { // from class: miuix.preference.NumberPickerPanelPreference$$ExternalSyntheticLambda0
            @Override // miuix.appcompat.app.NumberPickerPanel.OnValueChangedListener
            public final void onValueChanged(NumberPickerPanel numberPickerPanel2, int i2) {
                NumberPickerPanelPreference.m1349$r8$lambda$1dWo5QlS9vELTZ4628uxmDZ1o(this.f$0, numberPickerPanel2, i2);
            }
        });
        this.mPanel.setOnValueConfirmedListener(new NumberPickerPanel.OnValueConfirmedListener() { // from class: miuix.preference.NumberPickerPanelPreference$$ExternalSyntheticLambda1
            @Override // miuix.appcompat.app.NumberPickerPanel.OnValueConfirmedListener
            public final void onValueConfirmed(NumberPickerPanel numberPickerPanel2, int i2) {
                NumberPickerPanelPreference.$r8$lambda$IC4ZUR7Ibo6bw6TcXUtKD4RoeMQ(this.f$0, numberPickerPanel2, i2);
            }
        });
        this.mPanel.setOnExpandStateChangedListener(new NumberPickerPanel.OnExpandStateChangedListener() { // from class: miuix.preference.NumberPickerPanelPreference$$ExternalSyntheticLambda2
            @Override // miuix.appcompat.app.NumberPickerPanel.OnExpandStateChangedListener
            public final void onExpandStateChanged(boolean z) {
                this.f$0.mExpanded = z;
            }
        });
    }
}
