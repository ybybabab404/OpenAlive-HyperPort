package miuix.appcompat.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.view.MarginLayoutParamsCompat;
import java.util.Locale;
import miuix.appcompat.R;
import miuix.pickerwidget.widget.NumberPicker;

/* JADX INFO: loaded from: classes2.dex */
public class NumberPickerPanel extends LinearLayout {
    public View headerRoot;
    public TextView headerTitle;
    public TextView headerValue;
    public boolean isExpanded;
    public Runnable mConfirmRunnable;
    public boolean mCustomHeaderTextEnabled;
    public NumberPicker numberPicker;
    public OnExpandStateChangedListener onExpandStateChangedListener;
    public OnValueChangedListener onValueChangedListener;
    public OnValueConfirmedListener onValueConfirmedListener;
    public LinearLayout pickerContainer;
    public String pickerLabel;
    public String unitText;

    public interface OnExpandStateChangedListener {
        void onExpandStateChanged(boolean z);
    }

    public interface OnValueChangedListener {
        void onValueChanged(NumberPickerPanel numberPickerPanel, int i);
    }

    public interface OnValueConfirmedListener {
        void onValueConfirmed(NumberPickerPanel numberPickerPanel, int i);
    }

    /* JADX INFO: renamed from: $r8$lambda$FfD7_ZwLZCsCY-S52QvuCMhHpZA, reason: not valid java name */
    public static /* synthetic */ void m1305$r8$lambda$FfD7_ZwLZCsCYS52QvuCMhHpZA(NumberPickerPanel numberPickerPanel) {
        OnValueConfirmedListener onValueConfirmedListener = numberPickerPanel.onValueConfirmedListener;
        if (onValueConfirmedListener != null) {
            onValueConfirmedListener.onValueConfirmed(numberPickerPanel, numberPickerPanel.numberPicker.getValue());
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$S-OQ-THzJrKAB6yzK4caX9PSFPs, reason: not valid java name */
    public static /* synthetic */ void m1306$r8$lambda$SOQTHzJrKAB6yzK4caX9PSFPs(NumberPickerPanel numberPickerPanel) {
        int width = numberPickerPanel.headerRoot.getWidth();
        int paddingStart = numberPickerPanel.headerRoot.getPaddingStart();
        int paddingEnd = numberPickerPanel.headerRoot.getPaddingEnd();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) numberPickerPanel.headerTitle.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) numberPickerPanel.headerValue.getLayoutParams();
        int marginStart = width - (((((paddingStart + paddingEnd) + MarginLayoutParamsCompat.getMarginStart(marginLayoutParams)) + MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams)) + MarginLayoutParamsCompat.getMarginStart(marginLayoutParams2)) + MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams2));
        if (marginStart <= 0) {
            return;
        }
        numberPickerPanel.headerTitle.setMaxWidth((marginStart * 2) / 3);
        numberPickerPanel.headerValue.setMaxWidth(marginStart / 3);
    }

    /* JADX INFO: renamed from: $r8$lambda$uobHtws7wokoeooiSJ0snFULu-E, reason: not valid java name */
    public static /* synthetic */ void m1307$r8$lambda$uobHtws7wokoeooiSJ0snFULuE(final NumberPickerPanel numberPickerPanel, NumberPicker numberPicker, int i, int i2) {
        numberPickerPanel.updateHeaderValueText();
        OnValueChangedListener onValueChangedListener = numberPickerPanel.onValueChangedListener;
        if (onValueChangedListener != null) {
            onValueChangedListener.onValueChanged(numberPickerPanel, i2);
        }
        if (numberPickerPanel.onValueConfirmedListener != null) {
            Runnable runnable = numberPickerPanel.mConfirmRunnable;
            if (runnable != null) {
                numberPickerPanel.numberPicker.removeCallbacks(runnable);
            }
            Runnable runnable2 = new Runnable() { // from class: miuix.appcompat.app.NumberPickerPanel$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    NumberPickerPanel.m1305$r8$lambda$FfD7_ZwLZCsCYS52QvuCMhHpZA(this.f$0);
                }
            };
            numberPickerPanel.mConfirmRunnable = runnable2;
            numberPickerPanel.numberPicker.postDelayed(runnable2, 300L);
        }
    }

    public NumberPickerPanel(Context context) {
        this(context, null);
    }

    public NumberPickerPanel(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NumberPickerPanel(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isExpanded = false;
        setOrientation(1);
        initView(context);
        initListeners();
        initAttr(context, attributeSet);
        updateHeaderValueText();
    }

    public int getValue() {
        return this.numberPicker.getValue();
    }

    public final void initAttr(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.NumberPickerPanel);
        try {
            String string = typedArrayObtainStyledAttributes.getString(R.styleable.NumberPickerPanel_nppTitleText);
            if (!TextUtils.isEmpty(string)) {
                this.headerTitle.setText(string);
            }
            this.unitText = typedArrayObtainStyledAttributes.getString(R.styleable.NumberPickerPanel_nppUnitText);
            this.pickerLabel = typedArrayObtainStyledAttributes.getString(R.styleable.NumberPickerPanel_nppPickerLabel);
            this.mCustomHeaderTextEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NumberPickerPanel_customHeaderTextEnabled, false);
            if (!TextUtils.isEmpty(this.pickerLabel)) {
                this.numberPicker.setLabel(this.pickerLabel);
            }
            int i = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPickerPanel_nppMinValue, 1);
            int i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPickerPanel_nppMaxValue, 10);
            int i3 = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPickerPanel_nppDefaultValue, i);
            this.numberPicker.setMinValue(i);
            this.numberPicker.setMaxValue(i2);
            this.numberPicker.setValue(i3);
            updateHeaderValueText();
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public final void initListeners() {
        this.headerRoot.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.NumberPickerPanel$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.toggle();
            }
        });
        this.headerValue.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.NumberPickerPanel$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.toggle();
            }
        });
        this.numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: miuix.appcompat.app.NumberPickerPanel$$ExternalSyntheticLambda2
            @Override // miuix.pickerwidget.widget.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker, int i, int i2) {
                NumberPickerPanel.m1307$r8$lambda$uobHtws7wokoeooiSJ0snFULuE(this.f$0, numberPicker, i, i2);
            }
        });
    }

    public final void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.miuix_appcompat_number_picker_panel_layout, (ViewGroup) this, true);
        this.headerRoot = findViewById(R.id.header_root);
        this.headerTitle = (TextView) findViewById(R.id.header_title);
        this.headerValue = (TextView) findViewById(R.id.header_value);
        this.pickerContainer = (LinearLayout) findViewById(R.id.picker_container);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        this.numberPicker = numberPicker;
        numberPicker.setMinValue(1);
        this.numberPicker.setMaxValue(10);
        this.pickerContainer.setVisibility(8);
        this.headerRoot.post(new Runnable() { // from class: miuix.appcompat.app.NumberPickerPanel$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateHeaderMaxWidths();
            }
        });
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateHeaderMaxWidths();
    }

    public void resetState() {
        this.isExpanded = false;
        this.headerValue.setSelected(false);
        this.pickerContainer.setVisibility(8);
    }

    public void restoreExpandState(boolean z) {
        if (z) {
            this.isExpanded = true;
            this.pickerContainer.setVisibility(0);
            this.headerValue.setSelected(true);
        }
    }

    public void setHeaderText(@Nullable String str) {
        TextView textView = this.headerValue;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setMaxValue(int i) {
        this.numberPicker.setMaxValue(i);
    }

    public void setMinValue(int i) {
        this.numberPicker.setMinValue(i);
    }

    public void setOnExpandStateChangedListener(@Nullable OnExpandStateChangedListener onExpandStateChangedListener) {
        this.onExpandStateChangedListener = onExpandStateChangedListener;
    }

    public void setOnValueChangedListener(@Nullable OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }

    public void setOnValueConfirmedListener(@Nullable OnValueConfirmedListener onValueConfirmedListener) {
        this.onValueConfirmedListener = onValueConfirmedListener;
    }

    public void setPickerLabel(@Nullable String str) {
        this.pickerLabel = str;
        this.numberPicker.setLabel(str);
    }

    public void setTitleText(@Nullable String str) {
        this.headerTitle.setText(str);
    }

    public void setUnitText(@Nullable String str) {
        this.unitText = str;
        updateHeaderValueText();
    }

    public void setValue(int i) {
        this.numberPicker.setValue(i);
        updateHeaderValueText();
    }

    public final void toggle() {
        boolean z = this.isExpanded;
        this.isExpanded = !z;
        this.pickerContainer.setVisibility(!z ? 0 : 8);
        this.headerValue.setSelected(this.isExpanded);
        OnExpandStateChangedListener onExpandStateChangedListener = this.onExpandStateChangedListener;
        if (onExpandStateChangedListener != null) {
            onExpandStateChangedListener.onExpandStateChanged(this.isExpanded);
        }
    }

    public final void updateHeaderMaxWidths() {
        this.headerTitle.setMaxWidth(Integer.MAX_VALUE);
        this.headerValue.setMaxWidth(Integer.MAX_VALUE);
        this.headerRoot.post(new Runnable() { // from class: miuix.appcompat.app.NumberPickerPanel$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                NumberPickerPanel.m1306$r8$lambda$SOQTHzJrKAB6yzK4caX9PSFPs(this.f$0);
            }
        });
    }

    public final void updateHeaderValueText() {
        if (this.mCustomHeaderTextEnabled) {
            return;
        }
        int value = this.numberPicker.getValue();
        if (TextUtils.isEmpty(this.unitText)) {
            this.headerValue.setText(String.valueOf(value));
        } else {
            this.headerValue.setText(String.format(Locale.ROOT, "%d%s", Integer.valueOf(value), this.unitText));
        }
    }
}
