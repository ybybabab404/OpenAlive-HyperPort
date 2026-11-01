package androidx.appsearch.app;

import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import androidx.appsearch.flags.FlaggedApi;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
@SafeParcelable.Class
public final class InternalVisibilityConfig extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.InternalVisibilityConfigCreator();
    public final boolean mIsNotDisplayedBySystem;
    public final String mSchemaType;
    public final SchemaVisibilityConfig mVisibilityConfig;
    public final List mVisibleToConfigs;

    @FlaggedApi
    public static final class Builder {
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof InternalVisibilityConfig)) {
            return false;
        }
        InternalVisibilityConfig internalVisibilityConfig = (InternalVisibilityConfig) obj;
        return this.mIsNotDisplayedBySystem == internalVisibilityConfig.mIsNotDisplayedBySystem && Objects.equals(this.mSchemaType, internalVisibilityConfig.mSchemaType) && Objects.equals(this.mVisibilityConfig, internalVisibilityConfig.mVisibilityConfig) && Objects.equals(this.mVisibleToConfigs, internalVisibilityConfig.mVisibleToConfigs);
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mIsNotDisplayedBySystem), this.mSchemaType, this.mVisibilityConfig, this.mVisibleToConfigs);
    }
}
