package androidx.preference;

import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public abstract class PreferenceDataStore {
    public boolean getBoolean(String str, boolean z) {
        return z;
    }

    public int getInt(String str, int i) {
        return i;
    }

    public String getString(String str, String str2) {
        return str2;
    }

    public Set getStringSet(String str, Set set) {
        return set;
    }

    public void putBoolean(String str, boolean z) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    public void putInt(String str, int i) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    public void putString(String str, String str2) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    public void putStringSet(String str, Set set) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }
}
