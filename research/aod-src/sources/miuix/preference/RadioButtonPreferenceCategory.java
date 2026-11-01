package miuix.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Checkable;
import androidx.preference.Preference;

/* JADX INFO: loaded from: classes2.dex */
public class RadioButtonPreferenceCategory extends androidx.preference.PreferenceCategory {
    public SingleChoiceHelper mCheckedChoice;
    public int mCheckedPosition;
    public OnPreferenceChangeInternalListener mInternalListener;
    public boolean mIsNeedCardGroup;

    public class CategorySingleChoiceHelper extends SingleChoiceHelper {
        public RadioSetPreferenceCategory mCategory;

        public CategorySingleChoiceHelper(RadioSetPreferenceCategory radioSetPreferenceCategory) {
            super(radioSetPreferenceCategory);
            this.mCategory = radioSetPreferenceCategory;
        }

        @Override // miuix.preference.RadioButtonPreferenceCategory.SingleChoiceHelper
        public Preference getPreference() {
            return this.mCategory;
        }

        @Override // miuix.preference.RadioButtonPreferenceCategory.SingleChoiceHelper, android.widget.Checkable
        public void setChecked(boolean z) {
            super.setChecked(z);
            if (this.mCategory.getPrimaryPreference() != null) {
                this.mCategory.getPrimaryPreference().setChecked(z);
            }
        }

        @Override // miuix.preference.RadioButtonPreferenceCategory.SingleChoiceHelper
        public void setOnPreferenceChangeInternalListener(OnPreferenceChangeInternalListener onPreferenceChangeInternalListener) {
            this.mCategory.setOnPreferenceChangeInternalListener(onPreferenceChangeInternalListener);
        }
    }

    public class PreferenceSingleChoiceHelper extends SingleChoiceHelper {
        public RadioButtonPreference mPreference;

        public PreferenceSingleChoiceHelper(RadioButtonPreference radioButtonPreference) {
            super(radioButtonPreference);
            this.mPreference = radioButtonPreference;
        }

        @Override // miuix.preference.RadioButtonPreferenceCategory.SingleChoiceHelper
        public Preference getPreference() {
            return this.mPreference;
        }

        @Override // miuix.preference.RadioButtonPreferenceCategory.SingleChoiceHelper
        public void setOnPreferenceChangeInternalListener(OnPreferenceChangeInternalListener onPreferenceChangeInternalListener) {
            this.mPreference.setOnPreferenceChangeInternalListener(onPreferenceChangeInternalListener);
        }
    }

    public abstract class SingleChoiceHelper implements Checkable {
        public Checkable mCheckable;

        public SingleChoiceHelper(Checkable checkable) {
            this.mCheckable = checkable;
        }

        public abstract Preference getPreference();

        @Override // android.widget.Checkable
        public boolean isChecked() {
            return this.mCheckable.isChecked();
        }

        @Override // android.widget.Checkable
        public void setChecked(boolean z) {
            this.mCheckable.setChecked(z);
        }

        public abstract void setOnPreferenceChangeInternalListener(OnPreferenceChangeInternalListener onPreferenceChangeInternalListener);

        @Override // android.widget.Checkable
        public void toggle() {
            setChecked(!isChecked());
        }
    }

    public RadioButtonPreferenceCategory(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.preferenceCategoryRadioStyle);
    }

    public RadioButtonPreferenceCategory(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCheckedChoice = null;
        this.mCheckedPosition = -1;
        this.mInternalListener = new OnPreferenceChangeInternalListener() { // from class: miuix.preference.RadioButtonPreferenceCategory.1
            @Override // miuix.preference.OnPreferenceChangeInternalListener
            public void notifyPreferenceChangeInternal(Preference preference) {
                SingleChoiceHelper singleChoiceHelper = RadioButtonPreferenceCategory.this.parse(preference);
                RadioButtonPreferenceCategory.this.updateCheckedPreference(singleChoiceHelper);
                RadioButtonPreferenceCategory.this.updateCheckedPosition(singleChoiceHelper);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // miuix.preference.OnPreferenceChangeInternalListener
            public boolean onPreferenceChangeInternal(Preference preference, Object obj) {
                boolean zIsChecked = ((Checkable) preference).isChecked();
                Preference.OnPreferenceClickListener onPreferenceClickListener = RadioButtonPreferenceCategory.this.getOnPreferenceClickListener();
                if (onPreferenceClickListener != null) {
                    RadioButtonPreferenceCategory.this.checkPreferenceByInternal(preference, obj);
                    onPreferenceClickListener.onPreferenceClick(RadioButtonPreferenceCategory.this);
                }
                return !zIsChecked;
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RadioButtonPreferenceCategory);
        this.mIsNeedCardGroup = typedArrayObtainStyledAttributes.getBoolean(R.styleable.RadioButtonPreferenceCategory_toCardGroup, false);
        typedArrayObtainStyledAttributes.recycle();
    }

    private boolean callChangeListenerByInternal(Object obj, Preference preference) {
        return preference.getOnPreferenceChangeListener() == null || preference.getOnPreferenceChangeListener().onPreferenceChange(preference, obj);
    }

    @Override // androidx.preference.PreferenceGroup
    public boolean addPreference(Preference preference) {
        SingleChoiceHelper singleChoiceHelper = parse(preference);
        boolean zAddPreference = super.addPreference(preference);
        if (zAddPreference) {
            singleChoiceHelper.setOnPreferenceChangeInternalListener(this.mInternalListener);
        }
        if (!singleChoiceHelper.isChecked()) {
            return zAddPreference;
        }
        if (this.mCheckedChoice != null) {
            throw new IllegalStateException("Already has a checked item, please check state of new add preference");
        }
        this.mCheckedChoice = singleChoiceHelper;
        return zAddPreference;
    }

    public final void checkPreferenceByInternal(Preference preference, Object obj) {
        Preference parent = preference.getParent() instanceof RadioSetPreferenceCategory ? preference.getParent() : preference;
        SingleChoiceHelper singleChoiceHelper = this.mCheckedChoice;
        if ((singleChoiceHelper == null || parent != singleChoiceHelper.getPreference()) && callChangeListenerByInternal(obj, parent)) {
            setCheckedPreference(preference);
        }
    }

    public final void clearChecked() {
        SingleChoiceHelper singleChoiceHelper = this.mCheckedChoice;
        if (singleChoiceHelper != null) {
            singleChoiceHelper.setChecked(false);
        }
        this.mCheckedChoice = null;
        this.mCheckedPosition = -1;
    }

    public boolean isNeedCardGroup() {
        return this.mIsNeedCardGroup;
    }

    public final SingleChoiceHelper parse(Preference preference) {
        if (preference instanceof RadioButtonPreference) {
            return preference.getParent() instanceof RadioSetPreferenceCategory ? new CategorySingleChoiceHelper((RadioSetPreferenceCategory) preference.getParent()) : new PreferenceSingleChoiceHelper((RadioButtonPreference) preference);
        }
        if (preference instanceof RadioSetPreferenceCategory) {
            return new CategorySingleChoiceHelper((RadioSetPreferenceCategory) preference);
        }
        throw new IllegalArgumentException("Only RadioButtonPreference or RadioSetPreferenceCategory can be added to RadioButtonPreferenceCategory");
    }

    public void setCheckedPreference(Preference preference) {
        if (preference == null) {
            clearChecked();
            return;
        }
        SingleChoiceHelper singleChoiceHelper = parse(preference);
        if (singleChoiceHelper.isChecked()) {
            return;
        }
        setCheckedPreferenceInternal(singleChoiceHelper);
        updateCheckedPreference(singleChoiceHelper);
        updateCheckedPosition(singleChoiceHelper);
    }

    public final void setCheckedPreferenceInternal(SingleChoiceHelper singleChoiceHelper) {
        singleChoiceHelper.setChecked(true);
    }

    public final void updateCheckedPosition(SingleChoiceHelper singleChoiceHelper) {
        if (singleChoiceHelper.isChecked()) {
            int preferenceCount = getPreferenceCount();
            for (int i = 0; i < preferenceCount; i++) {
                if (getPreference(i) == singleChoiceHelper.getPreference()) {
                    this.mCheckedPosition = i;
                    return;
                }
            }
        }
    }

    public final void updateCheckedPreference(SingleChoiceHelper singleChoiceHelper) {
        if (singleChoiceHelper.isChecked()) {
            SingleChoiceHelper singleChoiceHelper2 = this.mCheckedChoice;
            if (singleChoiceHelper2 != null && singleChoiceHelper2.getPreference() != singleChoiceHelper.getPreference()) {
                this.mCheckedChoice.setChecked(false);
            }
            this.mCheckedChoice = singleChoiceHelper;
        }
    }
}
