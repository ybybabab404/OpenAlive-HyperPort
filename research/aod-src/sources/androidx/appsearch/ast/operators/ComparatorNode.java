package androidx.appsearch.ast.operators;

import androidx.annotation.RestrictTo;
import androidx.appsearch.app.ExperimentalAppSearchApi;
import androidx.appsearch.app.PropertyPath;
import androidx.appsearch.ast.Node;
import androidx.appsearch.flags.FlaggedApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@FlaggedApi
public final class ComparatorNode implements Node {
    public int mComparator;
    public PropertyPath mPropertyPath;
    public long mValue;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface Comparator {
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ComparatorNode)) {
            return false;
        }
        ComparatorNode comparatorNode = (ComparatorNode) obj;
        return this.mComparator == comparatorNode.mComparator && this.mValue == comparatorNode.mValue && Objects.equals(this.mPropertyPath, comparatorNode.mPropertyPath);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mComparator), this.mPropertyPath, Long.valueOf(this.mValue));
    }

    public String toString() {
        String str;
        int i = this.mComparator;
        if (i == 0) {
            str = "==";
        } else if (i == 1) {
            str = "<";
        } else if (i == 2) {
            str = "<=";
        } else if (i != 3) {
            str = i != 4 ? "" : ">=";
        } else {
            str = ">";
        }
        return "(" + this.mPropertyPath + " " + str + " " + this.mValue + ")";
    }
}
