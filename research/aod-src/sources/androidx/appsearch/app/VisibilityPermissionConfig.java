package androidx.appsearch.app;

import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.collection.ArraySet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
@SafeParcelable.Class
public final class VisibilityPermissionConfig extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.VisibilityPermissionConfigCreator();
    public static final AppSearchSchema SCHEMA = new AppSearchSchema.Builder("VisibilityPermissionType").addProperty(new AppSearchSchema.LongPropertyConfig.Builder("allRequiredPermissions").setCardinality(1).build()).build();
    public final int[] mAllRequiredPermissions;
    public Integer mHashCode;

    public VisibilityPermissionConfig(Set set) {
        Objects.requireNonNull(set);
        this.mAllRequiredPermissions = toInts(set);
    }

    public static Set toIntegerSet(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        ArraySet arraySet = new ArraySet(iArr.length);
        for (int i : iArr) {
            arraySet.add(Integer.valueOf(i));
        }
        return arraySet;
    }

    public static int[] toInts(Set set) {
        int[] iArr = new int[set.size()];
        Iterator it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = ((Integer) it.next()).intValue();
            i++;
        }
        return iArr;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof VisibilityPermissionConfig) {
            return Arrays.equals(this.mAllRequiredPermissions, ((VisibilityPermissionConfig) obj).mAllRequiredPermissions);
        }
        return false;
    }

    public Set getAllRequiredPermissions() {
        return toIntegerSet(this.mAllRequiredPermissions);
    }

    public int hashCode() {
        if (this.mHashCode == null) {
            this.mHashCode = Integer.valueOf(Arrays.hashCode(this.mAllRequiredPermissions));
        }
        return this.mHashCode.intValue();
    }
}
