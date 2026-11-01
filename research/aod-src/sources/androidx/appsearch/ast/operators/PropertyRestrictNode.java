package androidx.appsearch.ast.operators;

import androidx.appsearch.app.ExperimentalAppSearchApi;
import androidx.appsearch.app.PropertyPath;
import androidx.appsearch.ast.Node;
import androidx.appsearch.flags.FlaggedApi;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@FlaggedApi
public final class PropertyRestrictNode implements Node {
    public final List mChildren;
    public PropertyPath mPropertyPath;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PropertyRestrictNode)) {
            return false;
        }
        PropertyRestrictNode propertyRestrictNode = (PropertyRestrictNode) obj;
        return Objects.equals(this.mPropertyPath, propertyRestrictNode.mPropertyPath) && Objects.equals(this.mChildren, propertyRestrictNode.mChildren);
    }

    public Node getChild() {
        return (Node) this.mChildren.get(0);
    }

    public int hashCode() {
        return Objects.hash(this.mPropertyPath, this.mChildren);
    }

    public String toString() {
        return "(" + this.mPropertyPath + ":" + getChild() + ")";
    }
}
