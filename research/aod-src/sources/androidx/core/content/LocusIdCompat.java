package androidx.core.content;

import android.content.LocusId;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
public final class LocusIdCompat {
    public final String mId;
    public final LocusId mWrapped;

    @RequiresApi
    public static class Api29Impl {
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || LocusIdCompat.class != obj.getClass()) {
            return false;
        }
        LocusIdCompat locusIdCompat = (LocusIdCompat) obj;
        String str = this.mId;
        if (str == null) {
            return locusIdCompat.mId == null;
        }
        return str.equals(locusIdCompat.mId);
    }

    public final String getSanitizedId() {
        return this.mId.length() + "_chars";
    }

    public int hashCode() {
        String str = this.mId;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    public LocusId toLocusId() {
        return this.mWrapped;
    }

    public String toString() {
        return "LocusIdCompat[" + getSanitizedId() + "]";
    }
}
