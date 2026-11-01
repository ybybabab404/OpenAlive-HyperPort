package androidx.appsearch.ast;

import androidx.appsearch.app.ExperimentalAppSearchApi;
import androidx.appsearch.flags.FlaggedApi;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@FlaggedApi
public final class NegationNode implements Node {
    public final List mChildren;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || NegationNode.class != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mChildren, ((NegationNode) obj).mChildren);
    }

    public Node getChild() {
        return (Node) this.mChildren.get(0);
    }

    public int hashCode() {
        return Objects.hashCode(this.mChildren);
    }

    public String toString() {
        return "NOT " + getChild();
    }
}
