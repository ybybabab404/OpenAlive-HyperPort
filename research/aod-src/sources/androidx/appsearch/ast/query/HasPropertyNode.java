package androidx.appsearch.ast.query;

import androidx.appsearch.app.ExperimentalAppSearchApi;
import androidx.appsearch.app.PropertyPath;
import androidx.appsearch.ast.FunctionNode;
import androidx.appsearch.flags.FlaggedApi;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@FlaggedApi
public final class HasPropertyNode implements FunctionNode {
    public PropertyPath mPropertyPath;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || HasPropertyNode.class != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mPropertyPath, ((HasPropertyNode) obj).mPropertyPath);
    }

    public int hashCode() {
        return Objects.hashCode(this.mPropertyPath);
    }

    public String toString() {
        return "hasProperty(\"" + this.mPropertyPath + "\")";
    }
}
