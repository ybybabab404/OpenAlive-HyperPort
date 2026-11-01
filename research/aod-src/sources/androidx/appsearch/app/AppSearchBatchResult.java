package androidx.appsearch.app;

import androidx.collection.ArrayMap;
import androidx.core.util.Preconditions;
import java.util.Collections;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class AppSearchBatchResult<KeyType, ValueType> {
    public final Map mAll;
    public final Map mFailures;
    public final Map mSuccesses;

    public static final class Builder<KeyType, ValueType> {
        public ArrayMap mSuccesses = new ArrayMap();
        public ArrayMap mFailures = new ArrayMap();
        public ArrayMap mAll = new ArrayMap();
        public boolean mBuilt = false;

        public AppSearchBatchResult build() {
            this.mBuilt = true;
            return new AppSearchBatchResult(this.mSuccesses, this.mFailures, this.mAll);
        }

        public final void resetIfBuilt() {
            if (this.mBuilt) {
                this.mSuccesses = new ArrayMap(this.mSuccesses);
                this.mFailures = new ArrayMap(this.mFailures);
                this.mAll = new ArrayMap(this.mAll);
                this.mBuilt = false;
            }
        }

        public Builder setFailure(Object obj, int i, String str) {
            Preconditions.checkNotNull(obj);
            resetIfBuilt();
            return setResult(obj, AppSearchResult.newFailedResult(i, str));
        }

        public Builder setResult(Object obj, AppSearchResult appSearchResult) {
            Preconditions.checkNotNull(obj);
            Preconditions.checkNotNull(appSearchResult);
            resetIfBuilt();
            if (appSearchResult.isSuccess()) {
                this.mSuccesses.put(obj, appSearchResult.getResultValue());
                this.mFailures.remove(obj);
            } else {
                this.mFailures.put(obj, appSearchResult);
                this.mSuccesses.remove(obj);
            }
            this.mAll.put(obj, appSearchResult);
            return this;
        }

        public Builder setSuccess(Object obj, Object obj2) {
            Preconditions.checkNotNull(obj);
            resetIfBuilt();
            return setResult(obj, AppSearchResult.newSuccessfulResult(obj2));
        }
    }

    public AppSearchBatchResult(Map map, Map map2, Map map3) {
        this.mSuccesses = (Map) Preconditions.checkNotNull(map);
        this.mFailures = (Map) Preconditions.checkNotNull(map2);
        this.mAll = (Map) Preconditions.checkNotNull(map3);
    }

    public Map getSuccesses() {
        return Collections.unmodifiableMap(this.mSuccesses);
    }

    public String toString() {
        return "{\n  successes: " + this.mSuccesses + "\n  failures: " + this.mFailures + "\n}";
    }
}
