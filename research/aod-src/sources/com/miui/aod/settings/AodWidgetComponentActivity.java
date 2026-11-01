package com.miui.aod.settings;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.google.gson.Gson;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.analy.AnalyticManager;
import com.miui.aod.category.BigTimeCategoryInfo;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.widget.WidgetSwitcher$$ExternalSyntheticLambda0;
import com.miui.aod.util.ViewUtils;
import java.util.Objects;
import miuix.appcompat.app.AppCompatActivity;
import miuix.appcompat.app.floatingactivity.OnFloatingCallback;
import miuix.appcompat.app.floatingactivity.helper.BaseFloatingActivityHelper;
import miuix.appcompat.app.floatingactivity.helper.FloatingHelperFactory;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes.dex */
public class AodWidgetComponentActivity extends AppCompatActivity implements OnFloatingCallback {
    public View mAodWidgetBatteryContainer;
    public TextView mAodWidgetBatteryTitle;
    public View mAodWidgetDateContainer;
    public TextView mAodWidgetDateTitle;
    public View mAodWidgetLunaContainer;
    public TextView mAodWidgetLunaTitle;
    public View mAodWidgetNotificationContainer;
    public TextView mAodWidgetNotificationTitle;
    public BaseFloatingActivityHelper mBaseFloatingActivityHelper;
    public SlidingButton mBtnBattery;
    public SlidingButton mBtnDate;
    public SlidingButton mBtnLuna;
    public SlidingButton mBtnNotification;
    public View mRootView;

    public static /* synthetic */ void $r8$lambda$0o4djTl9HSWK5_ylFDkhMSFm3g4(AodWidgetComponentActivity aodWidgetComponentActivity, BigTimeCategoryInfo bigTimeCategoryInfo, CompoundButton compoundButton, boolean z) {
        aodWidgetComponentActivity.setStyleInfo(bigTimeCategoryInfo);
        AnalyticManager.getInstance(aodWidgetComponentActivity).trackPadAodModeStateSwitch("aod_notification_status", z);
        aodWidgetComponentActivity.updateContentDescription(aodWidgetComponentActivity);
    }

    public static /* synthetic */ void $r8$lambda$CSTa7xCVLr4xhKtiU_SzR5TDMTM(AodWidgetComponentActivity aodWidgetComponentActivity, View view) {
        SlidingButton slidingButton = aodWidgetComponentActivity.mBtnDate;
        slidingButton.setChecked(!slidingButton.isChecked());
    }

    public static /* synthetic */ void $r8$lambda$LDOlkdQ9FIkq0iWq_n4zDx0C8Ig(AodWidgetComponentActivity aodWidgetComponentActivity, View view) {
        SlidingButton slidingButton = aodWidgetComponentActivity.mBtnBattery;
        slidingButton.setChecked(!slidingButton.isChecked());
    }

    public static /* synthetic */ void $r8$lambda$N7ZJPKE0Qrrg05wfW8RiDRmjJlg(AodWidgetComponentActivity aodWidgetComponentActivity, BigTimeCategoryInfo bigTimeCategoryInfo, CompoundButton compoundButton, boolean z) {
        aodWidgetComponentActivity.setStyleInfo(bigTimeCategoryInfo);
        AnalyticManager.getInstance(aodWidgetComponentActivity).trackPadAodModeStateSwitch("aod_date_status", z);
        aodWidgetComponentActivity.updateContentDescription(aodWidgetComponentActivity);
    }

    public static /* synthetic */ void $r8$lambda$Qeop0Pn1OMoNrejh2EqmsuMbhYY(AodWidgetComponentActivity aodWidgetComponentActivity, BigTimeCategoryInfo bigTimeCategoryInfo, CompoundButton compoundButton, boolean z) {
        aodWidgetComponentActivity.setStyleInfo(bigTimeCategoryInfo);
        AnalyticManager.getInstance(aodWidgetComponentActivity).trackPadAodModeStateSwitch("lunar_calendar_status", z);
        aodWidgetComponentActivity.updateContentDescription(aodWidgetComponentActivity);
    }

    public static /* synthetic */ void $r8$lambda$RbWowXdHBKMgK8zo1GxYD5gqwcw(final AodWidgetComponentActivity aodWidgetComponentActivity) {
        View parent = aodWidgetComponentActivity.getParent(aodWidgetComponentActivity.mRootView);
        View parent2 = aodWidgetComponentActivity.getParent(parent);
        View parent3 = aodWidgetComponentActivity.getParent(parent2);
        aodWidgetComponentActivity.setTransparent(parent);
        aodWidgetComponentActivity.setTransparent(parent2);
        aodWidgetComponentActivity.setTransparent(parent3);
        if (parent != null) {
            parent.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.AodWidgetComponentActivity$$ExternalSyntheticLambda10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.finish();
                }
            });
            if (Utils.isMiuiDeskTopMode(aodWidgetComponentActivity)) {
                parent.setBackgroundColor(Color.parseColor("#B3000000"));
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$X5v9eFOswnU60ZpLLDfTTZcHaWI(AodWidgetComponentActivity aodWidgetComponentActivity, View view) {
        SlidingButton slidingButton = aodWidgetComponentActivity.mBtnNotification;
        slidingButton.setChecked(!slidingButton.isChecked());
    }

    /* JADX INFO: renamed from: $r8$lambda$aycqQTNuFZHLoX5Shb-_DTvV5oc, reason: not valid java name */
    public static /* synthetic */ void m452$r8$lambda$aycqQTNuFZHLoX5Shb_DTvV5oc(AodWidgetComponentActivity aodWidgetComponentActivity, BigTimeCategoryInfo bigTimeCategoryInfo, CompoundButton compoundButton, boolean z) {
        aodWidgetComponentActivity.setStyleInfo(bigTimeCategoryInfo);
        AnalyticManager.getInstance(aodWidgetComponentActivity).trackPadAodModeStateSwitch("aod_energy_status", z);
        aodWidgetComponentActivity.updateContentDescription(aodWidgetComponentActivity);
    }

    public static /* synthetic */ void $r8$lambda$z_d32QO8yc8H88ryY9skJBprBPg(AodWidgetComponentActivity aodWidgetComponentActivity, View view) {
        SlidingButton slidingButton = aodWidgetComponentActivity.mBtnLuna;
        slidingButton.setChecked(!slidingButton.isChecked());
    }

    private void initView() {
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.AodWidgetComponentActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.finish();
            }
        });
        this.mAodWidgetNotificationContainer = findViewById(R.id.aod_widget_notification_container);
        this.mAodWidgetBatteryContainer = findViewById(R.id.aod_widget_battery_container);
        this.mAodWidgetDateContainer = findViewById(R.id.aod_widget_date_container);
        this.mAodWidgetLunaContainer = findViewById(R.id.aod_widget_luna_container);
        this.mAodWidgetNotificationTitle = (TextView) findViewById(R.id.aod_widget_notification_title);
        this.mAodWidgetBatteryTitle = (TextView) findViewById(R.id.aod_widget_battery_title);
        this.mAodWidgetDateTitle = (TextView) findViewById(R.id.aod_widget_date_title);
        this.mAodWidgetLunaTitle = (TextView) findViewById(R.id.aod_widget_luna_title);
        this.mBtnNotification = (SlidingButton) findViewById(R.id.btn_notification);
        this.mBtnBattery = (SlidingButton) findViewById(R.id.btn_battery);
        this.mBtnDate = (SlidingButton) findViewById(R.id.btn_date);
        this.mBtnLuna = (SlidingButton) findViewById(R.id.btn_luna);
        this.mBtnNotification.setImportantForAccessibility(2);
        this.mBtnBattery.setImportantForAccessibility(2);
        this.mBtnDate.setImportantForAccessibility(2);
        this.mBtnLuna.setImportantForAccessibility(2);
        String string = getResources().getString(R.string.settings_view_switch_type);
        View view = this.mAodWidgetNotificationContainer;
        SlidingButton slidingButton = this.mBtnNotification;
        Objects.requireNonNull(slidingButton);
        ViewUtils.createAccessibilityDelegateForView(view, string, new WidgetSwitcher$$ExternalSyntheticLambda0(slidingButton));
        View view2 = this.mAodWidgetBatteryContainer;
        SlidingButton slidingButton2 = this.mBtnBattery;
        Objects.requireNonNull(slidingButton2);
        ViewUtils.createAccessibilityDelegateForView(view2, string, new WidgetSwitcher$$ExternalSyntheticLambda0(slidingButton2));
        View view3 = this.mAodWidgetDateContainer;
        SlidingButton slidingButton3 = this.mBtnDate;
        Objects.requireNonNull(slidingButton3);
        ViewUtils.createAccessibilityDelegateForView(view3, string, new WidgetSwitcher$$ExternalSyntheticLambda0(slidingButton3));
        View view4 = this.mAodWidgetLunaContainer;
        SlidingButton slidingButton4 = this.mBtnLuna;
        Objects.requireNonNull(slidingButton4);
        ViewUtils.createAccessibilityDelegateForView(view4, string, new WidgetSwitcher$$ExternalSyntheticLambda0(slidingButton4));
        final BigTimeCategoryInfo bigTimeCategoryInfo = (BigTimeCategoryInfo) new Gson().fromJson(Utils.getAodUsingCategoryParam(this, CategoryConstants.CATEGORY_BIG_TIME), BigTimeCategoryInfo.class);
        if (bigTimeCategoryInfo == null) {
            this.mBtnNotification.setChecked(false);
            this.mBtnBattery.setChecked(false);
            this.mBtnDate.setChecked(false);
            this.mBtnLuna.setChecked(false);
        } else {
            this.mBtnNotification.setChecked(bigTimeCategoryInfo.isNotificationSwitchOn());
            this.mBtnBattery.setChecked(bigTimeCategoryInfo.isBatterySwitchOn());
            this.mBtnDate.setChecked(bigTimeCategoryInfo.isDateAndTimeSwitchOn());
            this.mBtnLuna.setChecked(bigTimeCategoryInfo.isLunarSwitchOn(this));
            this.mAodWidgetLunaContainer.setVisibility(bigTimeCategoryInfo.supportLunarTime(this) ? 0 : 8);
        }
        this.mAodWidgetNotificationContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.AodWidgetComponentActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view5) {
                AodWidgetComponentActivity.$r8$lambda$X5v9eFOswnU60ZpLLDfTTZcHaWI(this.f$0, view5);
            }
        });
        this.mAodWidgetBatteryContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.AodWidgetComponentActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view5) {
                AodWidgetComponentActivity.$r8$lambda$LDOlkdQ9FIkq0iWq_n4zDx0C8Ig(this.f$0, view5);
            }
        });
        this.mAodWidgetDateContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.AodWidgetComponentActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view5) {
                AodWidgetComponentActivity.$r8$lambda$CSTa7xCVLr4xhKtiU_SzR5TDMTM(this.f$0, view5);
            }
        });
        this.mAodWidgetLunaContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.AodWidgetComponentActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view5) {
                AodWidgetComponentActivity.$r8$lambda$z_d32QO8yc8H88ryY9skJBprBPg(this.f$0, view5);
            }
        });
        this.mBtnNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.settings.AodWidgetComponentActivity$$ExternalSyntheticLambda9
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                AodWidgetComponentActivity.$r8$lambda$0o4djTl9HSWK5_ylFDkhMSFm3g4(this.f$0, bigTimeCategoryInfo, compoundButton, z);
            }
        });
        this.mBtnBattery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.settings.AodWidgetComponentActivity$$ExternalSyntheticLambda2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                AodWidgetComponentActivity.m452$r8$lambda$aycqQTNuFZHLoX5Shb_DTvV5oc(this.f$0, bigTimeCategoryInfo, compoundButton, z);
            }
        });
        this.mBtnDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.settings.AodWidgetComponentActivity$$ExternalSyntheticLambda3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                AodWidgetComponentActivity.$r8$lambda$N7ZJPKE0Qrrg05wfW8RiDRmjJlg(this.f$0, bigTimeCategoryInfo, compoundButton, z);
            }
        });
        this.mBtnLuna.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.settings.AodWidgetComponentActivity$$ExternalSyntheticLambda4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                AodWidgetComponentActivity.$r8$lambda$Qeop0Pn1OMoNrejh2EqmsuMbhYY(this.f$0, bigTimeCategoryInfo, compoundButton, z);
            }
        });
        if (getWindowInfo().windowMode == 0) {
            this.mBaseFloatingActivityHelper.hideFloatingDimBackground();
        }
        updateContentDescription(this);
    }

    private void setStyleInfo(StyleInfo styleInfo) {
        if (styleInfo != null) {
            BigTimeCategoryInfo bigTimeCategoryInfo = (BigTimeCategoryInfo) styleInfo;
            bigTimeCategoryInfo.switchOnNotificationIcon(this.mBtnNotification.isChecked());
            bigTimeCategoryInfo.switchOnBatteryIcon(this.mBtnBattery.isChecked());
            bigTimeCategoryInfo.switchOnDateAndTime(this.mBtnDate.isChecked());
            bigTimeCategoryInfo.setLunarSwitchOn(this.mBtnLuna.isChecked());
            String json = new Gson().toJson(bigTimeCategoryInfo);
            Utils.setAodUsingCategoryName(this, CategoryConstants.CATEGORY_BIG_TIME);
            Utils.setAodUsingCategory(this, CategoryConstants.CATEGORY_BIG_TIME, json);
        }
    }

    @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
    public void closeAllPage() {
    }

    public final View getParent(View view) {
        if (view != null) {
            return (View) view.getParent();
        }
        return null;
    }

    @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
    public void getSnapShotAndSetPanel(AppCompatActivity appCompatActivity) {
    }

    public final void initBg() {
        View view = this.mRootView;
        if (view != null) {
            view.post(new Runnable() { // from class: com.miui.aod.settings.AodWidgetComponentActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AodWidgetComponentActivity.$r8$lambda$RbWowXdHBKMgK8zo1GxYD5gqwcw(this.f$0);
                }
            });
        }
    }

    public final void initFloatingActivityHelper() {
        BaseFloatingActivityHelper baseFloatingActivityHelper = FloatingHelperFactory.get(this);
        this.mBaseFloatingActivityHelper = baseFloatingActivityHelper;
        baseFloatingActivityHelper.setOnFloatingCallback(this);
        this.mBaseFloatingActivityHelper.init(getWindow().getDecorView(), true);
    }

    @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
    public boolean isFirstPage() {
        return true;
    }

    @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
    public boolean isFirstPageEnterAnimExecuteEnable() {
        if (getWindowInfo().windowMode != 0) {
            return false;
        }
        this.mBaseFloatingActivityHelper.setFloatingWindowMode(true);
        return false;
    }

    @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
    public boolean isFirstPageExitAnimExecuteEnable() {
        return false;
    }

    @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
    public void markActivityOpenEnterAnimExecuted(AppCompatActivity appCompatActivity) {
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initBg();
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getAppCompatActionBar() != null) {
            getAppCompatActionBar().hide();
        }
        setContentView(R.layout.aod_widget_layout);
        this.mRootView = findViewById(R.id.root_view);
        initFloatingActivityHelper();
        initView();
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        BaseFloatingActivityHelper baseFloatingActivityHelper = this.mBaseFloatingActivityHelper;
        if (baseFloatingActivityHelper != null) {
            baseFloatingActivityHelper.setOnFloatingCallback(null);
            this.mBaseFloatingActivityHelper = null;
        }
        super.onDestroy();
    }

    @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
    public void onDragEnd() {
    }

    @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
    public void onDragStart() {
    }

    @Override // miuix.appcompat.app.floatingactivity.OnFloatingActivityCallback
    public boolean onFinish(int i) {
        return false;
    }

    @Override // miuix.appcompat.app.floatingactivity.OnFloatingCallback
    public void onHideBehindPage() {
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        initBg();
    }

    public final void setTransparent(View view) {
        if (view != null) {
            view.setBackgroundColor(0);
        }
    }

    public final void updateContentDescription(Context context) {
        String string = context.getResources().getString(R.string.settings_view_btn_check_on);
        String string2 = context.getResources().getString(R.string.settings_view_btn_check_off);
        ViewUtils.setAccessibilityForView(this.mAodWidgetNotificationContainer, this.mAodWidgetNotificationTitle.getText(), this.mBtnNotification.isChecked() ? string : string2);
        ViewUtils.setAccessibilityForView(this.mAodWidgetBatteryContainer, this.mAodWidgetBatteryTitle.getText(), this.mBtnBattery.isChecked() ? string : string2);
        ViewUtils.setAccessibilityForView(this.mAodWidgetDateContainer, this.mAodWidgetDateTitle.getText(), this.mBtnDate.isChecked() ? string : string2);
        View view = this.mAodWidgetLunaContainer;
        CharSequence text = this.mAodWidgetLunaTitle.getText();
        if (!this.mBtnLuna.isChecked()) {
            string = string2;
        }
        ViewUtils.setAccessibilityForView(view, text, string);
    }
}
