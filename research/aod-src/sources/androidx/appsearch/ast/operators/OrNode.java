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
public final class OrNode implements Node {
    public List mChildren;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || OrNode.class != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mChildren, ((OrNode) obj).mChildren);
    }

    public int hashCode() {
        return Objects.hashCode(this.mChildren);
    }

    public String toString() {
        return "(" + TextUtils.join(" OR ", this.mChildren) + ")";
    }
}
