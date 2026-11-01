package androidx.appsearch.ast.query;

import androidx.appsearch.app.ExperimentalAppSearchApi;
import androidx.appsearch.ast.FunctionNode;
import androidx.appsearch.flags.FlaggedApi;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@FlaggedApi
public final class SemanticSearchNode implements FunctionNode {
    public int mDistanceMetric;
    public float mLowerBound;
    public float mUpperBound;
    public int mVectorIndex;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && SemanticSearchNode.class == obj.getClass()) {
            SemanticSearchNode semanticSearchNode = (SemanticSearchNode) obj;
            if (this.mVectorIndex == semanticSearchNode.mVectorIndex && Float.compare(this.mLowerBound, semanticSearchNode.mLowerBound) == 0 && Float.compare(this.mUpperBound, semanticSearchNode.mUpperBound) == 0 && this.mDistanceMetric == semanticSearchNode.mDistanceMetric) {
                return true;
            }
        }
        return false;
    }

    public final void formatBound(StringBuilder sb, float f) {
        sb.append(", ");
        if (Float.isFinite(f)) {
            sb.append(f);
        } else if (f == Float.NEGATIVE_INFINITY) {
            sb.append(-3.4028235E38f);
        } else {
            sb.append(Float.MAX_VALUE);
        }
    }

    public final String getEmbeddingMetricString() {
        int i = this.mDistanceMetric;
        if (i == 1) {
            return "\"COSINE\"";
        }
        if (i == 2) {
            return "\"DOT_PRODUCT\"";
        }
        if (i == 3) {
            return "\"EUCLIDEAN\"";
        }
        throw new IllegalStateException("Invalid Metric Type");
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mVectorIndex), Float.valueOf(this.mLowerBound), Float.valueOf(this.mUpperBound), Integer.valueOf(this.mDistanceMetric));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("semanticSearch");
        sb.append("(getEmbeddingParameter(");
        sb.append(this.mVectorIndex);
        sb.append(")");
        if (this.mDistanceMetric != 0) {
            formatBound(sb, this.mLowerBound);
            formatBound(sb, this.mUpperBound);
            sb.append(", ");
            sb.append(getEmbeddingMetricString());
        } else if (this.mUpperBound != Float.POSITIVE_INFINITY) {
            formatBound(sb, this.mLowerBound);
            formatBound(sb, this.mUpperBound);
        } else {
            float f = this.mLowerBound;
            if (f != Float.NEGATIVE_INFINITY) {
                formatBound(sb, f);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
