package com.miui.aod.settings;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.attention.AttentionManager;
import com.miui.aod.icu.ICUManager;
import com.miui.aod.util.ActionBarHelper;
import com.miui.aod.widget.AODSettings;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.AppCompatActivity;
import miuix.appcompat.app.TimePickerDialog;
import miuix.core.util.MiuixUIUtils;
import miuix.pickerwidget.widget.TimePicker;
import miuix.preference.PreferenceFragment;
import miuix.preference.SingleChoicePreference;
import miuix.preference.TextPreference;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ShowModeFragment.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ShowModeFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    public static final Companion Companion = new Companion(null);
    public SingleChoicePreference mAodAlwaysStylePref;
    public TextPreference mAodEndPref;
    public SingleChoicePreference mAodScheduledStylePref;
    public SingleChoicePreference mAodSmartStylePref;
    public TextPreference mAodStartPref;
    public SingleChoicePreference mAodTemporaryStylePref;
    public Context mContext;
    public CheckBoxPreference mDisableAodInPowerSaveModePref;
    public int mEndTime;
    public RecyclerView.ItemAnimator mItemAnimator;
    public final TimePickerDialog.OnTimeSetListener mOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() { // from class: com.miui.aod.settings.ShowModeFragment$$ExternalSyntheticLambda0
        @Override // miuix.appcompat.app.TimePickerDialog.OnTimeSetListener
        public final void onTimeSet(TimePicker timePicker, int i, int i2) {
            ShowModeFragment.mOnTimeSetListener$lambda$2(this.f$0, timePicker, i, i2);
        }
    };
    public int mStartTime;
    public boolean mTimeFlag;
    public TimePickerDialog mTimePickerDialog;

    /* JADX INFO: compiled from: ShowModeFragment.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ShowModeFragment newInstance() {
            return new ShowModeFragment();
        }
    }

    public static final void mOnTimeSetListener$lambda$2(ShowModeFragment showModeFragment, TimePicker timePicker, int i, int i2) {
        Context context = null;
        if (showModeFragment.mTimeFlag) {
            int i3 = (i * 60) + i2;
            showModeFragment.mEndTime = i3;
            TextPreference textPreference = showModeFragment.mAodEndPref;
            if (textPreference != null) {
                Context context2 = showModeFragment.mContext;
                if (context2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mContext");
                    context2 = null;
                }
                textPreference.setText(Utils.formatTimeFromMinutes(i3, context2));
            }
            Context context3 = showModeFragment.mContext;
            if (context3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mContext");
            } else {
                context = context3;
            }
            Settings.Secure.putInt(context.getContentResolver(), "aod_end", showModeFragment.mEndTime);
            return;
        }
        int i4 = (i * 60) + i2;
        showModeFragment.mStartTime = i4;
        TextPreference textPreference2 = showModeFragment.mAodStartPref;
        if (textPreference2 != null) {
            Context context4 = showModeFragment.mContext;
            if (context4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mContext");
                context4 = null;
            }
            textPreference2.setText(Utils.formatTimeFromMinutes(i4, context4));
        }
        Context context5 = showModeFragment.mContext;
        if (context5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mContext");
        } else {
            context = context5;
        }
        Settings.Secure.putInt(context.getContentResolver(), "aod_start", showModeFragment.mStartTime);
    }

    public static final ShowModeFragment newInstance() {
        return Companion.newInstance();
    }

    public static final void onViewCreated$lambda$0(RecyclerView recyclerView, ShowModeFragment showModeFragment) {
        recyclerView.setItemAnimator(showModeFragment.mItemAnimator);
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        SingleChoicePreference singleChoicePreference;
        Context context;
        SingleChoicePreference singleChoicePreference2;
        setPreferencesFromResource(R.xml.show_mode_preferences_miuix, str);
        SingleChoicePreference singleChoicePreference3 = (SingleChoicePreference) findPreference("aod_scheduled_style");
        this.mAodScheduledStylePref = singleChoicePreference3;
        if (singleChoicePreference3 != null) {
            singleChoicePreference3.setOnPreferenceClickListener(this);
        }
        SingleChoicePreference singleChoicePreference4 = (SingleChoicePreference) findPreference("aod_always_style");
        this.mAodAlwaysStylePref = singleChoicePreference4;
        if (singleChoicePreference4 != null) {
            singleChoicePreference4.setOnPreferenceClickListener(this);
        }
        SingleChoicePreference singleChoicePreference5 = (SingleChoicePreference) findPreference("aod_temporary_style");
        this.mAodTemporaryStylePref = singleChoicePreference5;
        if (singleChoicePreference5 != null) {
            singleChoicePreference5.setOnPreferenceClickListener(this);
        }
        SingleChoicePreference singleChoicePreference6 = (SingleChoicePreference) findPreference("aod_smart_style");
        this.mAodSmartStylePref = singleChoicePreference6;
        if (singleChoicePreference6 != null) {
            singleChoicePreference6.setOnPreferenceClickListener(this);
        }
        SingleChoicePreference singleChoicePreference7 = this.mAodScheduledStylePref;
        if (singleChoicePreference7 != null) {
            singleChoicePreference7.setChecked(false);
        }
        SingleChoicePreference singleChoicePreference8 = this.mAodAlwaysStylePref;
        if (singleChoicePreference8 != null) {
            singleChoicePreference8.setChecked(false);
        }
        SingleChoicePreference singleChoicePreference9 = this.mAodTemporaryStylePref;
        if (singleChoicePreference9 != null) {
            singleChoicePreference9.setChecked(false);
        }
        SingleChoicePreference singleChoicePreference10 = this.mAodSmartStylePref;
        if (singleChoicePreference10 != null) {
            singleChoicePreference10.setChecked(false);
        }
        if (Utils.getTemporaryStyleDuration() == 5000 && (singleChoicePreference2 = this.mAodTemporaryStylePref) != null) {
            singleChoicePreference2.setTitle(requireContext().getString(R.string.temporary_style_5));
        }
        ICUManager iCUManager = ICUManager.INSTANCE;
        if (iCUManager.smartOnlySupportInnerDevice()) {
            SingleChoicePreference singleChoicePreference11 = this.mAodSmartStylePref;
            if (singleChoicePreference11 != null) {
                singleChoicePreference11.setSummary(requireContext().getString(R.string.smart_style_summary_inner));
            }
        } else if (iCUManager.smartOnlySupportOuterDevice() && (singleChoicePreference = this.mAodSmartStylePref) != null) {
            singleChoicePreference.setSummary(requireContext().getString(R.string.smart_style_summary_outer));
        }
        TextPreference textPreference = (TextPreference) findPreference("aod_mode_start_time");
        this.mAodStartPref = textPreference;
        if (textPreference != null) {
            textPreference.setOnPreferenceClickListener(this);
        }
        TextPreference textPreference2 = (TextPreference) findPreference("aod_mode_end_time");
        this.mAodEndPref = textPreference2;
        if (textPreference2 != null) {
            textPreference2.setOnPreferenceClickListener(this);
        }
        this.mContext = requireActivity();
        Context context2 = this.mContext;
        Context context3 = null;
        if (context2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mContext");
            context = null;
        } else {
            context = context2;
        }
        TimePickerDialog.OnTimeSetListener onTimeSetListener = this.mOnTimeSetListener;
        int i = this.mStartTime;
        int i2 = i / 60;
        int i3 = i % 60;
        Context context4 = this.mContext;
        if (context4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mContext");
            context4 = null;
        }
        this.mTimePickerDialog = new TimePickerDialog(context, onTimeSetListener, i2, i3, DateFormat.is24HourFormat(context4));
        Context context5 = this.mContext;
        if (context5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mContext");
            context5 = null;
        }
        this.mStartTime = Settings.Secure.getInt(context5.getContentResolver(), "aod_start", 420);
        Context context6 = this.mContext;
        if (context6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mContext");
            context6 = null;
        }
        this.mEndTime = Settings.Secure.getInt(context6.getContentResolver(), "aod_end", 1380);
        TextPreference textPreference3 = this.mAodStartPref;
        if (textPreference3 != null) {
            int i4 = this.mStartTime;
            Context context7 = this.mContext;
            if (context7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mContext");
                context7 = null;
            }
            textPreference3.setText(Utils.formatTimeFromMinutes(i4, context7));
        }
        TextPreference textPreference4 = this.mAodEndPref;
        if (textPreference4 != null) {
            int i5 = this.mEndTime;
            Context context8 = this.mContext;
            if (context8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mContext");
                context8 = null;
            }
            textPreference4.setText(Utils.formatTimeFromMinutes(i5, context8));
        }
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference("disable_aod_in_power_save_mode");
        this.mDisableAodInPowerSaveModePref = checkBoxPreference;
        if (checkBoxPreference != null) {
            checkBoxPreference.setOnPreferenceChangeListener(this);
        }
        CheckBoxPreference checkBoxPreference2 = this.mDisableAodInPowerSaveModePref;
        if (checkBoxPreference2 != null) {
            Context context9 = this.mContext;
            if (context9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mContext");
            } else {
                context3 = context9;
            }
            checkBoxPreference2.setChecked(Utils.disableAodInPowerSaveModePermitted(context3));
        }
        updateShowStyle();
    }

    @Override // miuix.preference.PreferenceFragment, androidx.preference.PreferenceFragmentCompat
    public RecyclerView onCreateRecyclerView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        Intrinsics.checkNotNullParameter(parent, "parent");
        RecyclerView recyclerViewOnCreateRecyclerView = super.onCreateRecyclerView(inflater, parent, bundle);
        Intrinsics.checkNotNullExpressionValue(recyclerViewOnCreateRecyclerView, "onCreateRecyclerView(...)");
        this.mItemAnimator = recyclerViewOnCreateRecyclerView.getItemAnimator();
        Context context = null;
        recyclerViewOnCreateRecyclerView.setItemAnimator(null);
        Context context2 = this.mContext;
        if (context2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mContext");
            context2 = null;
        }
        int iDp2px = MiuixUIUtils.dp2px(context2, 4.0f);
        Context context3 = this.mContext;
        if (context3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mContext");
        } else {
            context = context3;
        }
        recyclerViewOnCreateRecyclerView.setPadding(0, iDp2px, 0, MiuixUIUtils.dp2px(context, 4.0f));
        return recyclerViewOnCreateRecyclerView;
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        requireActivity().onBackPressed();
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        if (preference != this.mDisableAodInPowerSaveModePref) {
            return false;
        }
        Settings.Secure.putInt(requireActivity().getContentResolver(), "permit_disable_aod_in_power_save_mode", ((Boolean) newValue).booleanValue() ? 1 : 0);
        return true;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void onPreferenceChecked(String str) {
        switch (str.hashCode()) {
            case -1857658374:
                if (str.equals("aod_temporary_style")) {
                    SingleChoicePreference singleChoicePreference = this.mAodScheduledStylePref;
                    if (singleChoicePreference != null) {
                        singleChoicePreference.setChecked(false);
                    }
                    SingleChoicePreference singleChoicePreference2 = this.mAodAlwaysStylePref;
                    if (singleChoicePreference2 != null) {
                        singleChoicePreference2.setChecked(false);
                    }
                    SingleChoicePreference singleChoicePreference3 = this.mAodTemporaryStylePref;
                    if (singleChoicePreference3 != null) {
                        singleChoicePreference3.setChecked(true);
                    }
                    SingleChoicePreference singleChoicePreference4 = this.mAodSmartStylePref;
                    if (singleChoicePreference4 != null) {
                        singleChoicePreference4.setChecked(false);
                    }
                }
                break;
            case -1356501162:
                if (str.equals("aod_scheduled_style")) {
                    SingleChoicePreference singleChoicePreference5 = this.mAodScheduledStylePref;
                    if (singleChoicePreference5 != null) {
                        singleChoicePreference5.setChecked(true);
                    }
                    SingleChoicePreference singleChoicePreference6 = this.mAodAlwaysStylePref;
                    if (singleChoicePreference6 != null) {
                        singleChoicePreference6.setChecked(false);
                    }
                    SingleChoicePreference singleChoicePreference7 = this.mAodTemporaryStylePref;
                    if (singleChoicePreference7 != null) {
                        singleChoicePreference7.setChecked(false);
                    }
                    SingleChoicePreference singleChoicePreference8 = this.mAodSmartStylePref;
                    if (singleChoicePreference8 != null) {
                        singleChoicePreference8.setChecked(false);
                    }
                    break;
                }
                break;
            case -124226830:
                if (str.equals("aod_smart_style")) {
                    SingleChoicePreference singleChoicePreference9 = this.mAodScheduledStylePref;
                    if (singleChoicePreference9 != null) {
                        singleChoicePreference9.setChecked(false);
                    }
                    SingleChoicePreference singleChoicePreference10 = this.mAodAlwaysStylePref;
                    if (singleChoicePreference10 != null) {
                        singleChoicePreference10.setChecked(false);
                    }
                    SingleChoicePreference singleChoicePreference11 = this.mAodTemporaryStylePref;
                    if (singleChoicePreference11 != null) {
                        singleChoicePreference11.setChecked(false);
                    }
                    SingleChoicePreference singleChoicePreference12 = this.mAodSmartStylePref;
                    if (singleChoicePreference12 != null) {
                        singleChoicePreference12.setChecked(true);
                    }
                    break;
                }
                break;
            case 671701802:
                if (str.equals("aod_always_style")) {
                    SingleChoicePreference singleChoicePreference13 = this.mAodScheduledStylePref;
                    if (singleChoicePreference13 != null) {
                        singleChoicePreference13.setChecked(false);
                    }
                    SingleChoicePreference singleChoicePreference14 = this.mAodAlwaysStylePref;
                    if (singleChoicePreference14 != null) {
                        singleChoicePreference14.setChecked(true);
                    }
                    SingleChoicePreference singleChoicePreference15 = this.mAodTemporaryStylePref;
                    if (singleChoicePreference15 != null) {
                        singleChoicePreference15.setChecked(false);
                    }
                    SingleChoicePreference singleChoicePreference16 = this.mAodSmartStylePref;
                    if (singleChoicePreference16 != null) {
                        singleChoicePreference16.setChecked(false);
                    }
                    break;
                }
                break;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        String key = preference.getKey();
        boolean z = false;
        if (key != null) {
            Context context = null;
            Context context2 = null;
            TimePickerDialog timePickerDialog = null;
            Context context3 = null;
            Context context4 = null;
            TimePickerDialog timePickerDialog2 = null;
            switch (key.hashCode()) {
                case -1857658374:
                    if (key.equals("aod_temporary_style")) {
                        Settings.Secure.putInt(requireActivity().getContentResolver(), "aod_show_style", 0);
                        Context context5 = this.mContext;
                        if (context5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mContext");
                        } else {
                            context = context5;
                        }
                        Utils.updateTouchMode(context, false, true);
                        z = true;
                    }
                    break;
                case -1455316899:
                    if (key.equals("aod_mode_start_time")) {
                        this.mTimeFlag = false;
                        if (this.mStartTime > 0) {
                            TimePickerDialog timePickerDialog3 = this.mTimePickerDialog;
                            if (timePickerDialog3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mTimePickerDialog");
                                timePickerDialog3 = null;
                            }
                            int i = this.mStartTime;
                            timePickerDialog3.updateTime(i / 60, i % 60);
                        } else {
                            TimePickerDialog timePickerDialog4 = this.mTimePickerDialog;
                            if (timePickerDialog4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mTimePickerDialog");
                                timePickerDialog4 = null;
                            }
                            timePickerDialog4.updateTime(0, 0);
                        }
                        TimePickerDialog timePickerDialog5 = this.mTimePickerDialog;
                        if (timePickerDialog5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mTimePickerDialog");
                        } else {
                            timePickerDialog2 = timePickerDialog5;
                        }
                        timePickerDialog2.show();
                        z = true;
                    }
                    break;
                case -1356501162:
                    if (key.equals("aod_scheduled_style")) {
                        Settings.Secure.putInt(requireActivity().getContentResolver(), "need_reset_aod_time", 0);
                        Settings.Secure.putInt(requireActivity().getContentResolver(), "aod_show_style", 1);
                        Context context6 = this.mContext;
                        if (context6 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mContext");
                        } else {
                            context4 = context6;
                        }
                        Utils.updateTouchMode(context4, false, true);
                        z = true;
                    }
                    break;
                case -124226830:
                    if (key.equals("aod_smart_style")) {
                        Settings.Secure.putInt(requireActivity().getContentResolver(), "aod_show_style", 3);
                        Context context7 = this.mContext;
                        if (context7 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mContext");
                        } else {
                            context3 = context7;
                        }
                        Utils.updateTouchMode(context3, false, true);
                        z = true;
                    }
                    break;
                case 603887684:
                    if (key.equals("aod_mode_end_time")) {
                        this.mTimeFlag = true;
                        if (this.mEndTime > 0) {
                            TimePickerDialog timePickerDialog6 = this.mTimePickerDialog;
                            if (timePickerDialog6 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mTimePickerDialog");
                                timePickerDialog6 = null;
                            }
                            int i2 = this.mEndTime;
                            timePickerDialog6.updateTime(i2 / 60, i2 % 60);
                        } else {
                            TimePickerDialog timePickerDialog7 = this.mTimePickerDialog;
                            if (timePickerDialog7 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mTimePickerDialog");
                                timePickerDialog7 = null;
                            }
                            timePickerDialog7.updateTime(0, 0);
                        }
                        TimePickerDialog timePickerDialog8 = this.mTimePickerDialog;
                        if (timePickerDialog8 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mTimePickerDialog");
                        } else {
                            timePickerDialog = timePickerDialog8;
                        }
                        timePickerDialog.show();
                        z = true;
                    }
                    break;
                case 671701802:
                    if (key.equals("aod_always_style")) {
                        Settings.Secure.putInt(requireActivity().getContentResolver(), "aod_show_style", 2);
                        Context context8 = this.mContext;
                        if (context8 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mContext");
                        } else {
                            context2 = context8;
                        }
                        Utils.updateTouchMode(context2, false, true);
                        z = true;
                    }
                    break;
            }
        }
        String key2 = preference.getKey();
        Intrinsics.checkNotNullExpressionValue(key2, "getKey(...)");
        onPreferenceChecked(key2);
        return z;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        updateExtraInfoState();
    }

    @Override // miuix.preference.PreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        final RecyclerView listView = getListView();
        listView.post(new Runnable() { // from class: com.miui.aod.settings.ShowModeFragment$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ShowModeFragment.onViewCreated$lambda$0(listView, this);
            }
        });
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNull(fragmentActivityRequireActivity, "null cannot be cast to non-null type miuix.appcompat.app.AppCompatActivity");
        ActionBarHelper.setupOverlayActionBarForFragment((AppCompatActivity) fragmentActivityRequireActivity);
    }

    public final void updateExtraInfoState() {
        Context context = this.mContext;
        Context context2 = null;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mContext");
            context = null;
        }
        if (!Utils.isAodEnable(context)) {
            if (Settings.Secure.getInt(requireActivity().getContentResolver(), "need_reset_aod_time", 0) == 1) {
                Settings.Secure.putInt(requireActivity().getContentResolver(), "aod_mode_time", 1);
                Settings.Secure.putInt(requireActivity().getContentResolver(), "need_reset_aod_time", 0);
                return;
            }
            return;
        }
        ContentResolver contentResolver = requireActivity().getContentResolver();
        Context context3 = this.mContext;
        if (context3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mContext");
        } else {
            context2 = context3;
        }
        Settings.Secure.putInt(contentResolver, "aod_show_style", Utils.getShowStyle(context2));
    }

    public final void updateShowStyle() {
        SingleChoicePreference singleChoicePreference;
        SingleChoicePreference singleChoicePreference2;
        Context context = this.mContext;
        Context context2 = null;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mContext");
            context = null;
        }
        Utils.updateTouchMode(context, false, true);
        updateShowStyleForO2S();
        if (!AODSettings.isKeycodeGotoEnable()) {
            SingleChoicePreference singleChoicePreference3 = this.mAodTemporaryStylePref;
            if (singleChoicePreference3 != null) {
                singleChoicePreference3.setVisible(false);
            }
        } else if (AODSettings.onlySupportKeycodeGoto()) {
            SingleChoicePreference singleChoicePreference4 = this.mAodAlwaysStylePref;
            if (singleChoicePreference4 != null) {
                singleChoicePreference4.setVisible(false);
            }
            SingleChoicePreference singleChoicePreference5 = this.mAodScheduledStylePref;
            if (singleChoicePreference5 != null) {
                singleChoicePreference5.setVisible(false);
            }
            SingleChoicePreference singleChoicePreference6 = this.mAodTemporaryStylePref;
            if (singleChoicePreference6 != null) {
                singleChoicePreference6.setSummary(null);
            }
        }
        SingleChoicePreference singleChoicePreference7 = this.mAodSmartStylePref;
        if (singleChoicePreference7 != null && singleChoicePreference7.isVisible()) {
            Context context3 = this.mContext;
            if (context3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mContext");
                context3 = null;
            }
            if (!AttentionManager.supportAttention(context3) && (singleChoicePreference2 = this.mAodSmartStylePref) != null) {
                singleChoicePreference2.setVisible(false);
            }
        }
        Context context4 = this.mContext;
        if (context4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mContext");
        } else {
            context2 = context4;
        }
        int showStyle = Utils.getShowStyle(context2);
        if (showStyle == 0) {
            SingleChoicePreference singleChoicePreference8 = this.mAodTemporaryStylePref;
            if (singleChoicePreference8 != null) {
                singleChoicePreference8.setChecked(true);
                return;
            }
            return;
        }
        if (showStyle != 1) {
            if (showStyle != 2) {
                if (showStyle == 3 && (singleChoicePreference = this.mAodSmartStylePref) != null) {
                    singleChoicePreference.setChecked(true);
                    return;
                }
                return;
            }
            SingleChoicePreference singleChoicePreference9 = this.mAodAlwaysStylePref;
            if (singleChoicePreference9 != null) {
                singleChoicePreference9.setChecked(true);
                return;
            }
            return;
        }
        SingleChoicePreference singleChoicePreference10 = this.mAodScheduledStylePref;
        if (singleChoicePreference10 != null) {
            singleChoicePreference10.setChecked(true);
        }
        TextPreference textPreference = this.mAodStartPref;
        if (textPreference != null) {
            textPreference.setVisible(true);
        }
        TextPreference textPreference2 = this.mAodEndPref;
        if (textPreference2 != null) {
            textPreference2.setVisible(true);
        }
    }

    public final void updateShowStyleForO2S() {
        if (AODSettings.isO2SDevice()) {
            Context context = this.mContext;
            if (context == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mContext");
                context = null;
            }
            boolean zIsFsAodSameToLock = Utils.isFsAodSameToLock(context);
            SingleChoicePreference singleChoicePreference = this.mAodAlwaysStylePref;
            if (singleChoicePreference != null) {
                singleChoicePreference.setVisible(!zIsFsAodSameToLock);
            }
            SingleChoicePreference singleChoicePreference2 = this.mAodScheduledStylePref;
            if (singleChoicePreference2 != null) {
                singleChoicePreference2.setVisible(!zIsFsAodSameToLock);
            }
        }
    }
}
