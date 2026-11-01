package androidx.appsearch.app;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.appsearch.util.BundleUtil;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@SafeParcelable.Class
public final class GetByDocumentIdRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.GetByDocumentIdRequestCreator();
    public final List mIds;
    public Set mIdsCached;
    public final String mNamespace;
    public final Bundle mTypePropertyPaths;

    public static final class Builder {
        public final String mNamespace;
        public List mIds = new ArrayList();
        public Bundle mProjectionTypePropertyPaths = new Bundle();
        public boolean mBuilt = false;

        public Builder(String str) {
            this.mNamespace = (String) Preconditions.checkNotNull(str);
        }

        public Builder addIds(Collection collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mIds.addAll(collection);
            return this;
        }

        public Builder addIds(String... strArr) {
            Preconditions.checkNotNull(strArr);
            resetIfBuilt();
            return addIds(Arrays.asList(strArr));
        }

        public GetByDocumentIdRequest build() {
            this.mBuilt = true;
            return new GetByDocumentIdRequest(this.mNamespace, this.mIds, this.mProjectionTypePropertyPaths);
        }

        public final void resetIfBuilt() {
            if (this.mBuilt) {
                this.mIds = new ArrayList(this.mIds);
                this.mProjectionTypePropertyPaths = BundleUtil.deepCopy(this.mProjectionTypePropertyPaths);
                this.mBuilt = false;
            }
        }
    }

    public GetByDocumentIdRequest(String str, List list, Bundle bundle) {
        Objects.requireNonNull(str);
        this.mNamespace = str;
        Objects.requireNonNull(list);
        this.mIds = list;
        Objects.requireNonNull(bundle);
        this.mTypePropertyPaths = bundle;
    }

    public Set getIds() {
        if (this.mIdsCached == null) {
            this.mIdsCached = Collections.unmodifiableSet(new ArraySet(this.mIds));
        }
        return this.mIdsCached;
    }

    public String getNamespace() {
        return this.mNamespace;
    }

    public Map getProjections() {
        Set<String> setKeySet = this.mTypePropertyPaths.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            ArrayList<String> stringArrayList = this.mTypePropertyPaths.getStringArrayList(str);
            if (stringArrayList != null) {
                arrayMap.put(str, Collections.unmodifiableList(stringArrayList));
            }
        }
        return arrayMap;
    }
}
