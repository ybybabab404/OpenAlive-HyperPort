package miuix.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.Checkable;
import androidx.preference.Preference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public class MultiChoicePreferenceCategory extends androidx.preference.PreferenceCategory {
    public boolean cardGroupEnabled;
    public MultiChoiceHelper mCheckedChoice;
    public Context mContext;
    public CharSequence[] mEntries;
    public CharSequence[] mEntryValues;
    public OnPreferenceChangeInternalListener mInternalListener;
    public CharSequence[] mSummaries;
    public Set mValues;

    public static abstract class MultiChoiceHelper implements Checkable {
        public Checkable mCheckable;

        public MultiChoiceHelper(Checkable checkable) {
            this.mCheckable = checkable;
        }

        public abstract Preference getPreference();

        public abstract String getValue();

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

    public static class PreferenceSingleChoiceHelper extends MultiChoiceHelper {
        public MultiChoicePreference mPreference;

        public PreferenceSingleChoiceHelper(MultiChoicePreference multiChoicePreference) {
            super(multiChoicePreference);
            this.mPreference = multiChoicePreference;
        }

        @Override // miuix.preference.MultiChoicePreferenceCategory.MultiChoiceHelper
        public Preference getPreference() {
            return this.mPreference;
        }

        @Override // miuix.preference.MultiChoicePreferenceCategory.MultiChoiceHelper
        public String getValue() {
            return this.mPreference.getValue();
        }

        @Override // miuix.preference.MultiChoicePreferenceCategory.MultiChoiceHelper
        public void setOnPreferenceChangeInternalListener(OnPreferenceChangeInternalListener onPreferenceChangeInternalListener) {
            this.mPreference.setOnPreferenceChangeInternalListener(onPreferenceChangeInternalListener);
        }
    }

    public static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: miuix.preference.MultiChoicePreferenceCategory.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public Set mValues;

        public SavedState(Parcel parcel) {
            super(parcel);
            int i = parcel.readInt();
            this.mValues = new HashSet();
            String[] strArr = new String[i];
            parcel.readStringArray(strArr);
            Collections.addAll(this.mValues, strArr);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mValues.size());
            Set set = this.mValues;
            parcel.writeStringArray((String[]) set.toArray(new String[set.size()]));
        }
    }

    public MultiChoicePreferenceCategory(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.choiceCategoryPreferenceStyle);
    }

    public MultiChoicePreferenceCategory(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MultiChoicePreferenceCategory(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mValues = new HashSet();
        this.mCheckedChoice = null;
        this.mInternalListener = new OnPreferenceChangeInternalListener() { // from class: miuix.preference.MultiChoicePreferenceCategory.1
            @Override // miuix.preference.OnPreferenceChangeInternalListener
            public void notifyPreferenceChangeInternal(Preference preference) {
                MultiChoiceHelper multiChoiceHelper = MultiChoicePreferenceCategory.this.parse(preference);
                HashSet hashSet = new HashSet(MultiChoicePreferenceCategory.this.mValues);
                if (multiChoiceHelper.isChecked()) {
                    if (hashSet.contains(multiChoiceHelper.getValue())) {
                        return;
                    } else {
                        hashSet.add(multiChoiceHelper.getValue());
                    }
                } else if (!hashSet.contains(multiChoiceHelper.getValue())) {
                    return;
                } else {
                    hashSet.remove(multiChoiceHelper.getValue());
                }
                MultiChoicePreferenceCategory.this.setValues(hashSet);
            }

            @Override // miuix.preference.OnPreferenceChangeInternalListener
            public boolean onPreferenceChangeInternal(Preference preference, Object obj) {
                Preference.OnPreferenceClickListener onPreferenceClickListener = MultiChoicePreferenceCategory.this.getOnPreferenceClickListener();
                if (onPreferenceClickListener == null) {
                    return true;
                }
                MultiChoicePreferenceCategory.this.updatePreferenceByInternal(preference, obj);
                onPreferenceClickListener.onPreferenceClick(MultiChoicePreferenceCategory.this);
                return true;
            }
        };
        this.mContext = context;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ChoicePreferenceCategory, i, i2);
        this.mEntries = typedArrayObtainStyledAttributes.getTextArray(R.styleable.ChoicePreferenceCategory_android_entries);
        this.mEntryValues = typedArrayObtainStyledAttributes.getTextArray(R.styleable.ChoicePreferenceCategory_android_entryValues);
        this.mSummaries = typedArrayObtainStyledAttributes.getTextArray(R.styleable.ChoicePreferenceCategory_summaries);
        this.cardGroupEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ChoicePreferenceCategory_cardGroupEnabled, true);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void constructEntries() {
        int length = this.mEntries.length;
        for (int i = 0; i < length; i++) {
            String str = (String) this.mEntries[i];
            String str2 = (String) this.mEntryValues[i];
            MultiChoicePreference multiChoicePreference = new MultiChoicePreference(this.mContext);
            multiChoicePreference.setTitle(str);
            multiChoicePreference.setValue(str2);
            CharSequence[] charSequenceArr = this.mSummaries;
            if (charSequenceArr != null) {
                multiChoicePreference.setSummary((String) charSequenceArr[i]);
            }
            addPreference(multiChoicePreference);
        }
    }

    @Override // androidx.preference.PreferenceGroup
    public boolean addPreference(Preference preference) {
        MultiChoiceHelper multiChoiceHelper = parse(preference);
        boolean zAddPreference = super.addPreference(preference);
        if (zAddPreference) {
            multiChoiceHelper.setOnPreferenceChangeInternalListener(this.mInternalListener);
        }
        if (this.mValues.contains(((MultiChoicePreference) preference).getValue())) {
            multiChoiceHelper.setChecked(true);
        }
        return zAddPreference;
    }

    public final boolean callChangeListenerByInternal(Object obj, Preference preference) {
        return preference.getOnPreferenceChangeListener() == null || preference.getOnPreferenceChangeListener().onPreferenceChange(preference, obj);
    }

    public boolean getCardGroupEnabled() {
        return this.cardGroupEnabled;
    }

    public Set getValues() {
        return this.mValues;
    }

    public final void init() {
        constructEntries();
    }

    @Override // androidx.preference.PreferenceGroup, androidx.preference.Preference
    public void onAttached() {
        super.onAttached();
        init();
    }

    @Override // androidx.preference.PreferenceGroup, androidx.preference.Preference
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setValues(savedState.mValues);
    }

    @Override // androidx.preference.PreferenceGroup, androidx.preference.Preference
    public Parcelable onSaveInstanceState() {
        Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return parcelableOnSaveInstanceState;
        }
        SavedState savedState = new SavedState(parcelableOnSaveInstanceState);
        savedState.mValues = getValues();
        return savedState;
    }

    @Override // androidx.preference.Preference
    public void onSetInitialValue(Object obj) {
        setValues(getPersistedStringSet((Set) obj));
    }

    public final MultiChoiceHelper parse(Preference preference) {
        if (preference instanceof MultiChoicePreference) {
            return new PreferenceSingleChoiceHelper((MultiChoicePreference) preference);
        }
        throw new IllegalArgumentException("Only SingleChoicePreference can be added to MultiChoicePreferenceCategory");
    }

    public void setValues(Set set) {
        this.mValues.clear();
        this.mValues.addAll(set);
        persistStringSet(set);
        notifyChanged();
    }

    public void updateCheckablePreference(Preference preference) {
        parse(preference).toggle();
    }

    public final void updatePreferenceByInternal(Preference preference, Object obj) {
        Preference parent = preference.getParent() instanceof RadioSetPreferenceCategory ? preference.getParent() : preference;
        MultiChoiceHelper multiChoiceHelper = this.mCheckedChoice;
        if ((multiChoiceHelper == null || parent != multiChoiceHelper.getPreference()) && callChangeListenerByInternal(obj, parent)) {
            updateCheckablePreference(preference);
        }
    }
}
