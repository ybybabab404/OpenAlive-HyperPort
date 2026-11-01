package androidx.appsearch.app;

import android.os.Parcelable;
import androidx.appsearch.flags.FlaggedApi;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.core.util.Preconditions;
import java.util.Arrays;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@FlaggedApi
@SafeParcelable.Class
public final class EmbeddingVector extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.EmbeddingVectorCreator();
    public Integer mHashCode;
    public final String mModelSignature;
    public final float[] mValues;

    public EmbeddingVector(float[] fArr, String str) {
        float[] fArr2 = (float[]) Preconditions.checkNotNull(fArr);
        this.mValues = fArr2;
        if (fArr2.length == 0) {
            throw new IllegalArgumentException("Embedding values cannot be empty.");
        }
        this.mModelSignature = (String) Preconditions.checkNotNull(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EmbeddingVector)) {
            return false;
        }
        EmbeddingVector embeddingVector = (EmbeddingVector) obj;
        return Arrays.equals(this.mValues, embeddingVector.mValues) && this.mModelSignature.equals(embeddingVector.mModelSignature);
    }

    public String getModelSignature() {
        return this.mModelSignature;
    }

    public float[] getValues() {
        return this.mValues;
    }

    public int hashCode() {
        if (this.mHashCode == null) {
            this.mHashCode = Integer.valueOf(Objects.hash(Integer.valueOf(Arrays.hashCode(this.mValues)), this.mModelSignature));
        }
        return this.mHashCode.intValue();
    }
}
