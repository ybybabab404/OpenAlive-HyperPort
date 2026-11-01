package androidx.appsearch.ast.query;

import androidx.appsearch.app.ExperimentalAppSearchApi;
import androidx.appsearch.ast.FunctionNode;
import androidx.appsearch.ast.Node;
import androidx.appsearch.flags.FlaggedApi;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@FlaggedApi
public final class SearchNode implements FunctionNode {
    public final List mChildren;
    public final List mPropertyPaths;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && SearchNode.class == obj.getClass()) {
            SearchNode searchNode = (SearchNode) obj;
            if (Objects.equals(this.mChildren, searchNode.mChildren) && Objects.equals(this.mPropertyPaths, searchNode.mPropertyPaths)) {
                return true;
            }
        }
        return false;
    }

    public final String escapeQuery(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '\"' || str.charAt(i) == '\\') {
                sb.append('\\');
            }
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public Node getChild() {
        return (Node) this.mChildren.get(0);
    }

    public int hashCode() {
        return Objects.hash(this.mChildren, this.mPropertyPaths);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("search");
        sb.append("(\"");
        sb.append(escapeQuery(getChild().toString()));
        sb.append("\"");
        if (!this.mPropertyPaths.isEmpty()) {
            sb.append(", createList(");
            for (int i = 0; i < this.mPropertyPaths.size() - 1; i++) {
                sb.append("\"");
                sb.append(this.mPropertyPaths.get(i));
                sb.append("\", ");
            }
            sb.append("\"");
            List list = this.mPropertyPaths;
            sb.append(list.get(list.size() - 1));
            sb.append("\")");
        }
        sb.append(")");
        return sb.toString();
    }
}
