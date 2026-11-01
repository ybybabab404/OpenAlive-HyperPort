package androidx.appsearch.ast.operators;

import android.text.TextUtils;
import androidx.appsearch.app.ExperimentalAppSearchApi;
import androidx.appsearch.ast.Node;
import androidx.appsearch.flags.FlaggedApi;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@FlaggedApi
public final class AndNode implements Node {
    public List mChildren;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AndNode.class != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mChildren, ((AndNode) obj).mChildren);
    }

    public int hashCode() {
        return Objects.hashCode(this.mChildren);
    }

    public String toString() {
        return "(" + TextUtils.join(" AND ", this.mChildren) + ")";
    }
}
