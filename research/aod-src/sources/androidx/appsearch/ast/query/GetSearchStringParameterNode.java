package androidx.appsearch.ast.query;

import androidx.appsearch.app.ExperimentalAppSearchApi;
import androidx.appsearch.ast.FunctionNode;
import androidx.appsearch.flags.FlaggedApi;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@FlaggedApi
public final class GetSearchStringParameterNode implements FunctionNode {
    public int mSearchStringIndex;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && GetSearchStringParameterNode.class == obj.getClass() && this.mSearchStringIndex == ((GetSearchStringParameterNode) obj).mSearchStringIndex;
    }

    public int hashCode() {
        return Integer.hashCode(this.mSearchStringIndex);
    }

    public String toString() {
        return "getSearchStringParameter(" + this.mSearchStringIndex + ")";
    }
}
