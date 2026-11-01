package com.miui.aod.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.components.widget.WidgetSwitcher$$ExternalSyntheticLambda0;
import com.miui.aod.fullaod.FullAodStrategyKt;
import com.miui.aod.util.MiuiUtils;
import com.miui.aod.util.ToastUtils;
import com.miui.aod.util.ViewUtils;
import com.miui.aod.widget.AODSettings;
import com.xiaomi.onetrack.util.ac;
import java.util.Objects;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.widget.Spinner;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes.dex */
public class SettingsView extends LinearLayout {
    public TextView mAodModeTitle;
    public Spinner mAodSpinner;
    public SlidingButton mBtn;
    public TextView mFsAodTitle;
    public MiuiUtils mMiuiUtils;
    public SlidingButton mNotificationBtn;
    public TextView mNotificationTitle;
    public View mShowFsAodContainer;
    public TextView mShowMode;
    public View mShowModeContainer;
    public TextView mShowModeTitle;
    public View mShowModeTitleContainer;
    public View mShowNotificationContainer;
    public ToastUtils mToastUtil;

    public static /* synthetic */ void $r8$lambda$Dqo8O2VyVdIInj_IhsYckYYuCjY(final SettingsView settingsView, CompoundButton compoundButton, final boolean z) {
        settingsView.mBtn.post(new Runnable() { // from class: com.miui.aod.settings.SettingsView$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.enableShowMode(z);
            }
        });
        if (!z) {
            Utils.setAodEnable(settingsView.getContext(), false);
            Utils.updateTouchMode(settingsView.getContext(), false, true);
        } else {
            if (settingsView.isDisplayReversion()) {
                return;
            }
            Utils.setAodEnable(settingsView.getContext(), true);
            Utils.updateTouchMode(settingsView.getContext(), false, true);
        }
    }

    public static /* synthetic */ void $r8$lambda$LNoz6uTG9zlRnx8gfeAPVvgYKPg(SettingsView settingsView, Context context, View view) {
        if (!settingsView.mBtn.isChecked() || Utils.isFullAodSupport(context)) {
            settingsView.mAodSpinner.performClick();
            return;
        }
        if (settingsView.mToastUtil == null) {
            settingsView.mToastUtil = new ToastUtils();
        }
        settingsView.mToastUtil.showToast(context, FullAodStrategyKt.getToastReason(settingsView.getContext()), 0);
    }

    public static /* synthetic */ void $r8$lambda$VBQ3r9Z5F1LfXWEGDgfKbTHvSuQ(SettingsView settingsView, CompoundButton compoundButton, boolean z) {
        if (!z) {
            Utils.setAodModeUserSet(settingsView.getContext(), false);
        } else {
            if (settingsView.isDisplayReversion()) {
                return;
            }
            Utils.setAodModeUserSet(settingsView.getContext(), true);
        }
    }

    public static /* synthetic */ void $r8$lambda$pFSDLiSubRUugENnauKgKA6VnOw(SettingsView settingsView, View view) {
        SlidingButton slidingButton = settingsView.mBtn;
        slidingButton.setChecked(!slidingButton.isChecked());
        Utils.setAodModeUserSet(settingsView.getContext(), settingsView.mBtn.isChecked());
        settingsView.updateShowModeTitleContentDescription();
    }

    public static /* synthetic */ void $r8$lambda$qvvDVJj4oDOA4DxrBcNL6wnfuk4(SettingsView settingsView, DialogInterface dialogInterface, int i) {
        settingsView.getClass();
        Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
        intent.putExtra("extra_tab_position", 1);
        settingsView.getContext().startActivity(intent);
    }

    public static /* synthetic */ void $r8$lambda$wbC_cTKoQgR83WYT9_S7aNalYoo(SettingsView settingsView, View view) {
        SlidingButton slidingButton = settingsView.mNotificationBtn;
        slidingButton.setChecked(!slidingButton.isChecked());
        settingsView.updateShowNotificationContentDescription();
    }

    public SettingsView(Context context) {
        this(context, null);
    }

    public SettingsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SettingsView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private String getBtnType() {
        return getResources().getString(R.string.settings_view_switch_type);
    }

    public final void changeShowStyle() {
        if (Utils.isSupportFsAod() && Utils.getFsAodStyle(getContext()) == 1) {
            this.mShowModeContainer.setBackgroundResource(R.drawable.aod_settings_item_bg);
        } else {
            this.mShowModeContainer.setBackgroundResource(R.drawable.aod_settings_item_bg_bottom);
        }
    }

    public final void enableShowMode(boolean z) {
        boolean zIsFullAodSupport = Utils.isFullAodSupport(getContext());
        this.mShowModeContainer.setEnabled(z);
        this.mShowModeTitle.setEnabled(z);
        this.mShowMode.setEnabled(z);
        this.mShowFsAodContainer.setEnabled(z);
        TextView textView = this.mFsAodTitle;
        if (textView != null && this.mAodSpinner != null) {
            boolean z2 = false;
            textView.setEnabled(z && zIsFullAodSupport);
            Spinner spinner = this.mAodSpinner;
            if (z && zIsFullAodSupport) {
                z2 = true;
            }
            spinner.setEnabled(z2);
        }
        TextView textView2 = this.mNotificationTitle;
        if (textView2 == null || this.mNotificationBtn == null) {
            return;
        }
        textView2.setEnabled(z);
        this.mNotificationBtn.setEnabled(z);
        this.mShowNotificationContainer.setEnabled(z);
    }

    public final String getBtnState(boolean z) {
        return z ? getResources().getString(R.string.settings_view_btn_check_on) : getResources().getString(R.string.settings_view_btn_check_off);
    }

    public final boolean isDisplayReversion() {
        boolean zIsInvertColorsEnable = Utils.isInvertColorsEnable(getContext());
        if (zIsInvertColorsEnable) {
            this.mBtn.setChecked(false);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.aod_close_color_inversion);
            builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
            builder.setPositiveButton(R.string.aod_to_close, new DialogInterface.OnClickListener() { // from class: com.miui.aod.settings.SettingsView$$ExternalSyntheticLambda6
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    SettingsView.$r8$lambda$qvvDVJj4oDOA4DxrBcNL6wnfuk4(this.f$0, dialogInterface, i);
                }
            });
            builder.create().show();
        }
        return zIsInvertColorsEnable;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBtn = (SlidingButton) findViewById(R.id.slide_btn);
        this.mShowModeTitle = (TextView) findViewById(R.id.show_mode_title);
        this.mAodModeTitle = (TextView) findViewById(R.id.aod_mode_title);
        TextView textView = (TextView) findViewById(R.id.aod_mode_title_summary);
        if (Utils.isFlipDevice()) {
            textView.setVisibility(0);
            setPadding(0, getContext().getResources().getDimensionPixelSize(R.dimen.aod_setting_switch_padding_top), 0, 0);
        } else {
            textView.setVisibility(8);
        }
        String stringFromSettings = Utils.getStringFromSettings("ambient_display_screen_title");
        if (stringFromSettings != null) {
            this.mAodModeTitle.setText(stringFromSettings);
        }
        View viewFindViewById = findViewById(R.id.show_mode_title_container);
        this.mShowModeTitleContainer = viewFindViewById;
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.SettingsView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsView.$r8$lambda$pFSDLiSubRUugENnauKgKA6VnOw(this.f$0, view);
            }
        });
        View view = this.mShowModeTitleContainer;
        String btnType = getBtnType();
        SlidingButton slidingButton = this.mBtn;
        Objects.requireNonNull(slidingButton);
        ViewUtils.createAccessibilityDelegateForView(view, btnType, new WidgetSwitcher$$ExternalSyntheticLambda0(slidingButton));
        this.mShowModeContainer = findViewById(R.id.show_mode_Container);
        changeShowStyle();
        Utils.setLineSpacing("SettingsView", this, (TextView) findViewById(R.id.show_mode));
        this.mBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.settings.SettingsView$$ExternalSyntheticLambda1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsView.$r8$lambda$Dqo8O2VyVdIInj_IhsYckYYuCjY(this.f$0, compoundButton, z);
            }
        });
        this.mBtn.setOnPerformCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.settings.SettingsView$$ExternalSyntheticLambda2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsView.$r8$lambda$VBQ3r9Z5F1LfXWEGDgfKbTHvSuQ(this.f$0, compoundButton, z);
            }
        });
        this.mShowMode = (TextView) findViewById(R.id.show_mode);
        this.mShowModeContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.SettingsView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AodShowModeSettingActivity.start(this.f$0.getContext());
            }
        });
        SlidingButton slidingButton2 = this.mBtn;
        Objects.requireNonNull(slidingButton2);
        ViewUtils.createAccessibilityDelegateForView(slidingButton2, "", new WidgetSwitcher$$ExternalSyntheticLambda0(slidingButton2), false);
        this.mShowFsAodContainer = findViewById(R.id.aod_resting_screen_style);
        View viewFindViewById2 = findViewById(R.id.show_notification_container);
        this.mShowNotificationContainer = viewFindViewById2;
        viewFindViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.SettingsView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SettingsView.$r8$lambda$wbC_cTKoQgR83WYT9_S7aNalYoo(this.f$0, view2);
            }
        });
        this.mNotificationBtn = (SlidingButton) findViewById(R.id.notification_slide_btn);
        View view2 = this.mShowNotificationContainer;
        String btnType2 = getBtnType();
        SlidingButton slidingButton3 = this.mNotificationBtn;
        Objects.requireNonNull(slidingButton3);
        ViewUtils.createAccessibilityDelegateForView(view2, btnType2, new WidgetSwitcher$$ExternalSyntheticLambda0(slidingButton3));
        this.mNotificationBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.settings.SettingsView$$ExternalSyntheticLambda5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                Utils.setFullAodNotificationEnable(this.f$0.getContext(), z);
            }
        });
        this.mNotificationTitle = (TextView) findViewById(R.id.aod_notification_title);
        udpateFsAodStyleDropView();
        updateState();
        updateAodSpinnerContentDescription();
        updateShowNotificationContentDescription();
    }

    public final void udpateFsAodStyleDropView() {
        final Context context = getContext();
        this.mAodSpinner = (Spinner) findViewById(R.id.aod_resting_screen_choose);
        this.mFsAodTitle = (TextView) findViewById(R.id.aod_resting_screen_style_title);
        if (context == null || this.mAodSpinner == null || this.mShowFsAodContainer == null) {
            return;
        }
        if (this.mMiuiUtils == null) {
            this.mMiuiUtils = new MiuiUtils();
        }
        if (!Utils.isSupportFsAod()) {
            this.mShowFsAodContainer.setVisibility(8);
            return;
        }
        this.mShowFsAodContainer.setVisibility(0);
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.miuix_appcompat_simple_spinner_layout_integrated, android.R.id.text1);
        arrayAdapter.setDropDownViewResource(R.layout.miuix_appcompat_simple_spinner_dropdown_item);
        arrayAdapter.add(ContextCompat.getString(context, R.string.aod_resting_screen_define));
        arrayAdapter.add(ContextCompat.getString(context, R.string.aod_resting_screen_same_to_lock));
        this.mAodSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.mAodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.miui.aod.settings.SettingsView.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView adapterView, View view, int i, long j) {
                int displayMode;
                Utils.setFsAodStyle(context, i);
                if (AODSettings.isO2SDevice() && Utils.isFsAodSameToLock(context) && ((displayMode = Utils.getDisplayMode(context)) == 2 || displayMode == 1)) {
                    Utils.setDisplayMode(context, 0);
                }
                SettingsView.this.updateSpinnerState();
                SettingsView.this.updateAodSpinnerContentDescription();
                SettingsView.this.changeShowStyle();
                SettingsView.this.updateShowModeState();
                Utils.updateTouchMode(context, false, true);
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        this.mAodSpinner.setSelection(Utils.getFsAodStyle(context));
        this.mMiuiUtils.setSpinnerDisplayLocation(this.mAodSpinner, this.mShowFsAodContainer, ContextCompat.getColor(getContext(), R.color.bg_spinner_parent));
        this.mShowFsAodContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.SettingsView$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsView.$r8$lambda$LNoz6uTG9zlRnx8gfeAPVvgYKPg(this.f$0, context, view);
            }
        });
    }

    public final void updateAodSpinnerContentDescription() {
        if (this.mAodSpinner != null) {
            this.mShowFsAodContainer.setContentDescription(this.mFsAodTitle.getText().toString() + ac.b + this.mAodSpinner.getSelectedItem());
        }
    }

    public final void updateShowModeContentDescription() {
        this.mShowModeContainer.setContentDescription(this.mShowModeTitle.getText().toString() + ac.b + ((Object) this.mShowMode.getText()));
    }

    public final void updateShowModeState() {
        this.mShowMode.setText(SettingsProvider.getShowModeTip(getContext()));
        updateShowModeContentDescription();
    }

    public final void updateShowModeTitleContentDescription() {
        ViewUtils.setAccessibilityForView(this.mShowModeTitleContainer, this.mAodModeTitle.getText().toString(), getBtnState(this.mBtn.isChecked()));
    }

    public final void updateShowNotificationContentDescription() {
        View view = this.mShowNotificationContainer;
        if (view != null) {
            ViewUtils.setAccessibilityForView(view, this.mNotificationTitle.getText(), getBtnState(this.mNotificationBtn.isChecked()));
        }
    }

    public final void updateSpinnerState() {
        Spinner spinner = this.mAodSpinner;
        if (spinner != null && spinner.getSelectedItemPosition() != Utils.getFsAodStyle(getContext())) {
            this.mAodSpinner.setSelection(Utils.getFsAodStyle(getContext()));
        }
        View view = this.mShowNotificationContainer;
        if (view != null) {
            view.setVisibility((Utils.isSupportFsAod() && Utils.getFsAodStyle(getContext()) == 1) ? 0 : 8);
            this.mNotificationBtn.setChecked(Utils.isFullAodNotificationEnable(getContext()));
        }
    }

    public void updateState() {
        this.mBtn.setChecked(Utils.isAodEnable(getContext()));
        updateShowModeTitleContentDescription();
        enableShowMode(this.mBtn.isChecked());
        updateSpinnerState();
        updateShowModeState();
    }
}
