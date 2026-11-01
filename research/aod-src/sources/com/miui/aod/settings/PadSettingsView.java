package com.miui.aod.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.analy.AnalyticManager;
import com.miui.aod.common.BaseActivity;
import com.miui.aod.components.widget.WidgetSwitcher$$ExternalSyntheticLambda0;
import com.miui.aod.util.ViewUtils;
import com.xiaomi.onetrack.util.ac;
import java.util.Objects;
import miuix.appcompat.app.AlertDialog;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes.dex */
public class PadSettingsView extends ConstraintLayout {
    public TextView mAodDes;
    public View mAodDesContainer;
    public TextView mAodDesTitle;
    public View mAodWidgetContainer;
    public TextView mAodWidgetTitle;
    public TextView mBatterSaveSwitcherTitleSummary;
    public SlidingButton mBatteryShowSwitcher;
    public View mShowBatterySwitcherContainer;
    public View mShowModeContainer;
    public SlidingButton mShowModeSwitcher;
    public TextView mShowModeTitle;
    public SlidingButton mShowSwitcher;
    public TextView mShowSwitcherBatterySaveTitle;
    public View mShowSwitcherContainer;
    public TextView mShowSwitcherTitle;
    public TextView mShowSwitcherTitleSummary;

    /* JADX INFO: renamed from: $r8$lambda$3LWzC6bEB_QXEJvmzy1P4E-G_FY, reason: not valid java name */
    public static /* synthetic */ void m472$r8$lambda$3LWzC6bEB_QXEJvmzy1P4EG_FY(PadSettingsView padSettingsView, View view) {
        if (padSettingsView.getContext() instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) padSettingsView.getContext();
            if (baseActivity.isFinishing() || baseActivity.isDestroyed()) {
                return;
            }
            padSettingsView.startActivity(baseActivity);
        }
    }

    public static /* synthetic */ void $r8$lambda$StFMJGym4B0_46PPn_HZurJyesQ(PadSettingsView padSettingsView, CompoundButton compoundButton, boolean z) {
        if (!z) {
            Utils.setAodModeUserSet(padSettingsView.getContext(), false);
        } else {
            if (Utils.isInvertColorsEnable(padSettingsView.getContext())) {
                return;
            }
            Utils.setAodModeUserSet(padSettingsView.getContext(), true);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$TY-XnBAJNu-vpQoIclJBH43QzsA, reason: not valid java name */
    public static /* synthetic */ void m473$r8$lambda$TYXnBAJNuvpQoIclJBH43QzsA(PadSettingsView padSettingsView, View view) {
        SlidingButton slidingButton = padSettingsView.mShowSwitcher;
        slidingButton.setChecked(!slidingButton.isChecked());
    }

    public static /* synthetic */ void $r8$lambda$bIZWbHW1MMwNZuQeZgi1ApjEMg0(PadSettingsView padSettingsView, DialogInterface dialogInterface, int i) {
        padSettingsView.getClass();
        Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
        intent.putExtra("extra_tab_position", 1);
        padSettingsView.getContext().startActivity(intent);
    }

    public static /* synthetic */ void $r8$lambda$hODEkWbnVDPkY2nxnRkpR_MPTjQ(final PadSettingsView padSettingsView, CompoundButton compoundButton, boolean z) {
        if (z && Utils.isInvertColorsEnable(padSettingsView.getContext())) {
            padSettingsView.mShowSwitcher.setChecked(false);
            AlertDialog.Builder builder = new AlertDialog.Builder(padSettingsView.getContext());
            builder.setMessage(R.string.aod_close_color_inversion);
            builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
            builder.setPositiveButton(R.string.aod_to_close, new DialogInterface.OnClickListener() { // from class: com.miui.aod.settings.PadSettingsView$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    PadSettingsView.$r8$lambda$bIZWbHW1MMwNZuQeZgi1ApjEMg0(this.f$0, dialogInterface, i);
                }
            });
            builder.create().show();
            return;
        }
        if (Utils.isPowerSaveMode(padSettingsView.getContext())) {
            padSettingsView.mBatteryShowSwitcher.setChecked(!z);
        }
        Utils.setAodEnable(padSettingsView.getContext(), z);
        padSettingsView.changeSettingState();
        padSettingsView.updateContentDescription();
        AnalyticManager.getInstance(padSettingsView.getContext()).trackPadAodModeStateSwitch("aod_on_status", z);
    }

    /* JADX INFO: renamed from: $r8$lambda$u837mSLchPOmurZyC-RM9oqmkJ0, reason: not valid java name */
    public static /* synthetic */ void m474$r8$lambda$u837mSLchPOmurZyCRM9oqmkJ0(PadSettingsView padSettingsView, View view) {
        SlidingButton slidingButton = padSettingsView.mShowModeSwitcher;
        slidingButton.setChecked(!slidingButton.isChecked());
    }

    /* JADX INFO: renamed from: $r8$lambda$x1A3498PL-KP7Qeoyb_k-ELpOWg, reason: not valid java name */
    public static /* synthetic */ void m475$r8$lambda$x1A3498PLKP7Qeoyb_kELpOWg(PadSettingsView padSettingsView, CompoundButton compoundButton, boolean z) {
        if (z) {
            Utils.setShowStyle(padSettingsView.getContext(), 2);
        } else {
            Utils.setShowStyle(padSettingsView.getContext(), 3);
        }
        padSettingsView.updateContentDescription();
        AnalyticManager.getInstance(padSettingsView.getContext()).trackPadAodModeStateSwitch("charging_aod_off", z);
    }

    public static /* synthetic */ void $r8$lambda$xIBOfbvizpTQJPyGG5zsqMvRw0I(PadSettingsView padSettingsView, View view) {
        SlidingButton slidingButton = padSettingsView.mBatteryShowSwitcher;
        slidingButton.setChecked(!slidingButton.isChecked());
    }

    public PadSettingsView(Context context) {
        this(context, null);
    }

    public PadSettingsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PadSettingsView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public final void changeSettingState() {
        boolean zIsAodEnable = Utils.isAodEnable(getContext());
        this.mShowModeContainer.setEnabled(zIsAodEnable);
        this.mShowModeSwitcher.setEnabled(zIsAodEnable);
        this.mShowBatterySwitcherContainer.setEnabled(zIsAodEnable);
        this.mBatteryShowSwitcher.setEnabled(zIsAodEnable);
        this.mAodWidgetContainer.setEnabled(zIsAodEnable);
        this.mShowModeTitle.setEnabled(zIsAodEnable);
        this.mShowSwitcherBatterySaveTitle.setEnabled(zIsAodEnable);
        this.mAodWidgetTitle.setEnabled(zIsAodEnable);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mShowSwitcherContainer = findViewById(R.id.show_switcher_container);
        this.mShowModeContainer = findViewById(R.id.show_mode_container_pad);
        this.mShowBatterySwitcherContainer = findViewById(R.id.show_switcher_battery_save_container);
        this.mAodWidgetContainer = findViewById(R.id.aod_widget_container);
        this.mShowSwitcherTitle = (TextView) findViewById(R.id.show_switcher_title);
        this.mShowSwitcherTitleSummary = (TextView) findViewById(R.id.show_switcher_title_summary);
        this.mBatterSaveSwitcherTitleSummary = (TextView) findViewById(R.id.show_switcher_battery_save_summary);
        this.mShowSwitcher = (SlidingButton) findViewById(R.id.show_switcher_btn);
        this.mShowModeSwitcher = (SlidingButton) findViewById(R.id.show_mode_btn);
        this.mShowModeTitle = (TextView) findViewById(R.id.show_mode_title);
        this.mShowSwitcherBatterySaveTitle = (TextView) findViewById(R.id.show_switcher_battery_save_title);
        this.mAodWidgetTitle = (TextView) findViewById(R.id.aod_widget_title);
        this.mBatteryShowSwitcher = (SlidingButton) findViewById(R.id.show_switcher_battery_save_btn);
        this.mAodWidgetContainer = findViewById(R.id.aod_widget_container);
        this.mAodDesContainer = findViewById(R.id.aod_des_container);
        this.mAodDesTitle = (TextView) findViewById(R.id.aod_des_title);
        this.mAodDes = (TextView) findViewById(R.id.aod_des);
        String stringFromSettings = Utils.getStringFromSettings("aod_settings_category_name");
        if (stringFromSettings != null) {
            this.mShowSwitcherTitle.setText(stringFromSettings);
        }
        setListener();
        changeSettingState();
        setupSwitchAccessibility();
        updateContentDescription();
    }

    public final void setListener() {
        this.mShowSwitcherContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.PadSettingsView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PadSettingsView.m473$r8$lambda$TYXnBAJNuvpQoIclJBH43QzsA(this.f$0, view);
            }
        });
        this.mShowModeContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.PadSettingsView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PadSettingsView.m474$r8$lambda$u837mSLchPOmurZyCRM9oqmkJ0(this.f$0, view);
            }
        });
        this.mShowBatterySwitcherContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.PadSettingsView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PadSettingsView.$r8$lambda$xIBOfbvizpTQJPyGG5zsqMvRw0I(this.f$0, view);
            }
        });
        this.mShowSwitcher.setChecked(Utils.isAodEnable(getContext()));
        this.mShowSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.settings.PadSettingsView$$ExternalSyntheticLambda3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                PadSettingsView.$r8$lambda$hODEkWbnVDPkY2nxnRkpR_MPTjQ(this.f$0, compoundButton, z);
            }
        });
        this.mShowSwitcher.setOnPerformCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.settings.PadSettingsView$$ExternalSyntheticLambda4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                PadSettingsView.$r8$lambda$StFMJGym4B0_46PPn_HZurJyesQ(this.f$0, compoundButton, z);
            }
        });
        this.mShowModeSwitcher.setChecked(Utils.getShowStyle(getContext()) == 2);
        this.mShowModeSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.settings.PadSettingsView$$ExternalSyntheticLambda5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                PadSettingsView.m475$r8$lambda$x1A3498PLKP7Qeoyb_kELpOWg(this.f$0, compoundButton, z);
            }
        });
        this.mBatteryShowSwitcher.setChecked(Utils.disableAodInPowerSaveModePermitted(getContext()));
        this.mBatteryShowSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.settings.PadSettingsView.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (Utils.isPowerSaveMode(PadSettingsView.this.getContext())) {
                    PadSettingsView.this.mShowSwitcher.setChecked(!z);
                }
                Utils.setDisableAodInPowerSaveModePermitted(PadSettingsView.this.getContext(), z);
                PadSettingsView.this.updateContentDescription();
                AnalyticManager.getInstance(PadSettingsView.this.getContext()).trackPadAodModeStateSwitch("batterysaver_aod_off", z);
            }
        });
        this.mAodWidgetContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.PadSettingsView$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PadSettingsView.m472$r8$lambda$3LWzC6bEB_QXEJvmzy1P4EG_FY(this.f$0, view);
            }
        });
    }

    public final void setupSwitchAccessibility() {
        String string = getResources().getString(R.string.settings_view_switch_type);
        View view = this.mShowSwitcherContainer;
        SlidingButton slidingButton = this.mShowSwitcher;
        Objects.requireNonNull(slidingButton);
        ViewUtils.createAccessibilityDelegateForView(view, string, new WidgetSwitcher$$ExternalSyntheticLambda0(slidingButton));
        View view2 = this.mShowModeContainer;
        SlidingButton slidingButton2 = this.mShowModeSwitcher;
        Objects.requireNonNull(slidingButton2);
        ViewUtils.createAccessibilityDelegateForView(view2, string, new WidgetSwitcher$$ExternalSyntheticLambda0(slidingButton2));
        View view3 = this.mShowBatterySwitcherContainer;
        SlidingButton slidingButton3 = this.mBatteryShowSwitcher;
        Objects.requireNonNull(slidingButton3);
        ViewUtils.createAccessibilityDelegateForView(view3, string, new WidgetSwitcher$$ExternalSyntheticLambda0(slidingButton3));
    }

    public final void startActivity(BaseActivity baseActivity) {
        baseActivity.startActivity(new Intent(baseActivity, (Class<?>) AodWidgetComponentActivity.class));
    }

    public final void updateContentDescription() {
        String string = getResources().getString(R.string.settings_view_btn_check_on);
        String string2 = getResources().getString(R.string.settings_view_btn_check_off);
        ViewUtils.setAccessibilityForView(this.mShowSwitcherContainer, ((Object) this.mShowSwitcherTitle.getText()) + ac.b + ((Object) this.mShowSwitcherTitleSummary.getText()), this.mShowSwitcher.isChecked() ? string : string2);
        ViewUtils.setAccessibilityForView(this.mShowModeContainer, this.mShowModeTitle.getText(), this.mShowModeSwitcher.isChecked() ? string : string2);
        View view = this.mShowBatterySwitcherContainer;
        String str = ((Object) this.mShowSwitcherBatterySaveTitle.getText()) + ac.b + ((Object) this.mBatterSaveSwitcherTitleSummary.getText());
        if (!this.mBatteryShowSwitcher.isChecked()) {
            string = string2;
        }
        ViewUtils.setAccessibilityForView(view, str, string);
        this.mAodWidgetContainer.setContentDescription(this.mAodWidgetTitle.getText());
        this.mAodDesContainer.setContentDescription(this.mAodDesTitle.getText().toString() + ac.b + this.mAodDes.getText().toString());
    }
}
