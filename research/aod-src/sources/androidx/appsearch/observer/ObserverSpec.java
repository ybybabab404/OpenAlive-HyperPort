package androidx.appsearch.observer;

import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@SafeParcelable.Class
public final class ObserverSpec extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.ObserverSpecCreator();
    public final List mFilterSchemas;
    public volatile Set mFilterSchemasCached;

    public static final class Builder {
        public ArrayList mFilterSchemas = new ArrayList();
        public boolean mBuilt = false;

        public Builder addFilterSchemas(Collection collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mFilterSchemas.addAll(collection);
            return this;
        }

        public ObserverSpec build() {
            this.mBuilt = true;
            return new ObserverSpec(this.mFilterSchemas);
        }

        public final void resetIfBuilt() {
            if (this.mBuilt) {
                this.mFilterSchemas = new ArrayList(this.mFilterSchemas);
                this.mBuilt = false;
            }
        }
    }

    public ObserverSpec(List list) {
        this.mFilterSchemas = (List) Preconditions.checkNotNull(list);
    }

    public Set getFilterSchemas() {
        if (this.mFilterSchemasCached == null) {
            List list = this.mFilterSchemas;
            if (list == null) {
                this.mFilterSchemasCached = Collections.EMPTY_SET;
            } else {
                this.mFilterSchemasCached = Collections.unmodifiableSet(new ArraySet(list));
            }
        }
        return this.mFilterSchemasCached;
    }
}
