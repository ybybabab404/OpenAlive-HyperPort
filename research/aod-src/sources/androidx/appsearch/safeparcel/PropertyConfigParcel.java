package androidx.appsearch.safeparcel;

import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.core.util.ObjectsCompat;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
@SafeParcelable.Class
public final class PropertyConfigParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.PropertyConfigParcelCreator();
    public final int mCardinality;
    public final int mDataType;
    public final String mDescription;
    public final DocumentIndexingConfigParcel mDocumentIndexingConfigParcel;
    public final EmbeddingIndexingConfigParcel mEmbeddingIndexingConfigParcel;
    public Integer mHashCode;
    public final IntegerIndexingConfigParcel mIntegerIndexingConfigParcel;
    public final JoinableConfigParcel mJoinableConfigParcel;
    public final String mName;
    public final String mSchemaType;
    public final boolean mScoringEnabled;
    public final StringIndexingConfigParcel mStringIndexingConfigParcel;

    @SafeParcelable.Class
    public static class DocumentIndexingConfigParcel extends AbstractSafeParcelable {
        public static final Parcelable.Creator CREATOR = new StubCreators.DocumentIndexingConfigParcelCreator();
        public final boolean mIndexNestedProperties;
        public final List mIndexableNestedPropertiesList;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DocumentIndexingConfigParcel)) {
                return false;
            }
            DocumentIndexingConfigParcel documentIndexingConfigParcel = (DocumentIndexingConfigParcel) obj;
            return ObjectsCompat.equals(Boolean.valueOf(this.mIndexNestedProperties), Boolean.valueOf(documentIndexingConfigParcel.mIndexNestedProperties)) && ObjectsCompat.equals(this.mIndexableNestedPropertiesList, documentIndexingConfigParcel.mIndexableNestedPropertiesList);
        }

        public List getIndexableNestedPropertiesList() {
            return this.mIndexableNestedPropertiesList;
        }

        public int hashCode() {
            return ObjectsCompat.hash(Boolean.valueOf(this.mIndexNestedProperties), this.mIndexableNestedPropertiesList);
        }

        public boolean shouldIndexNestedProperties() {
            return this.mIndexNestedProperties;
        }

        public String toString() {
            return "{indexNestedProperties: " + this.mIndexNestedProperties + ", indexableNestedPropertiesList: " + this.mIndexableNestedPropertiesList + "}";
        }
    }

    @SafeParcelable.Class
    public static class EmbeddingIndexingConfigParcel extends AbstractSafeParcelable {
        public static final Parcelable.Creator CREATOR = new StubCreators.EmbeddingIndexingConfigParcelCreator();
        public final int mIndexingType;
        public final int mQuantizationType;

        public EmbeddingIndexingConfigParcel(int i, int i2) {
            this.mIndexingType = i;
            this.mQuantizationType = i2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EmbeddingIndexingConfigParcel)) {
                return false;
            }
            EmbeddingIndexingConfigParcel embeddingIndexingConfigParcel = (EmbeddingIndexingConfigParcel) obj;
            return this.mIndexingType == embeddingIndexingConfigParcel.mIndexingType && this.mQuantizationType == embeddingIndexingConfigParcel.mQuantizationType;
        }

        public int getIndexingType() {
            return this.mIndexingType;
        }

        public int hashCode() {
            return ObjectsCompat.hash(Integer.valueOf(this.mIndexingType), Integer.valueOf(this.mQuantizationType));
        }

        public String toString() {
            return "{indexingType: " + this.mIndexingType + ", quantizationType: " + this.mQuantizationType + "}";
        }
    }

    @SafeParcelable.Class
    public static class IntegerIndexingConfigParcel extends AbstractSafeParcelable {
        public static final Parcelable.Creator CREATOR = new StubCreators.IntegerIndexingConfigParcelCreator();
        public final int mIndexingType;

        public IntegerIndexingConfigParcel(int i) {
            this.mIndexingType = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof IntegerIndexingConfigParcel) && this.mIndexingType == ((IntegerIndexingConfigParcel) obj).mIndexingType;
        }

        public int getIndexingType() {
            return this.mIndexingType;
        }

        public int hashCode() {
            return ObjectsCompat.hashCode(Integer.valueOf(this.mIndexingType));
        }

        public String toString() {
            return "{indexingType: " + this.mIndexingType + "}";
        }
    }

    @SafeParcelable.Class
    public static class JoinableConfigParcel extends AbstractSafeParcelable {
        public static final Parcelable.Creator CREATOR = new StubCreators.JoinableConfigParcelCreator();
        public final int mDeletePropagationType;
        public final int mJoinableValueType;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof JoinableConfigParcel)) {
                return false;
            }
            JoinableConfigParcel joinableConfigParcel = (JoinableConfigParcel) obj;
            return ObjectsCompat.equals(Integer.valueOf(this.mJoinableValueType), Integer.valueOf(joinableConfigParcel.mJoinableValueType)) && this.mDeletePropagationType == joinableConfigParcel.mDeletePropagationType;
        }

        public int getDeletePropagationType() {
            return this.mDeletePropagationType;
        }

        public int getJoinableValueType() {
            return this.mJoinableValueType;
        }

        public int hashCode() {
            return ObjectsCompat.hash(Integer.valueOf(this.mJoinableValueType), Integer.valueOf(this.mDeletePropagationType));
        }

        public String toString() {
            return "{joinableValueType: " + this.mJoinableValueType + ", deletePropagationType: " + this.mDeletePropagationType + "}";
        }
    }

    @SafeParcelable.Class
    public static class StringIndexingConfigParcel extends AbstractSafeParcelable {
        public static final Parcelable.Creator CREATOR = new StubCreators.StringIndexingConfigParcelCreator();
        public final int mIndexingType;
        public final int mTokenizerType;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StringIndexingConfigParcel)) {
                return false;
            }
            StringIndexingConfigParcel stringIndexingConfigParcel = (StringIndexingConfigParcel) obj;
            return this.mIndexingType == stringIndexingConfigParcel.mIndexingType && ObjectsCompat.equals(Integer.valueOf(this.mTokenizerType), Integer.valueOf(stringIndexingConfigParcel.mTokenizerType));
        }

        public int getIndexingType() {
            return this.mIndexingType;
        }

        public int getTokenizerType() {
            return this.mTokenizerType;
        }

        public int hashCode() {
            return ObjectsCompat.hash(Integer.valueOf(this.mIndexingType), Integer.valueOf(this.mTokenizerType));
        }

        public String toString() {
            return "{indexingType: " + this.mIndexingType + ", tokenizerType: " + this.mTokenizerType + "}";
        }
    }

    public PropertyConfigParcel(String str, int i, int i2, String str2, StringIndexingConfigParcel stringIndexingConfigParcel, DocumentIndexingConfigParcel documentIndexingConfigParcel, IntegerIndexingConfigParcel integerIndexingConfigParcel, JoinableConfigParcel joinableConfigParcel, String str3, EmbeddingIndexingConfigParcel embeddingIndexingConfigParcel, boolean z) {
        Objects.requireNonNull(str);
        this.mName = str;
        this.mDataType = i;
        this.mCardinality = i2;
        this.mSchemaType = str2;
        this.mStringIndexingConfigParcel = stringIndexingConfigParcel;
        this.mDocumentIndexingConfigParcel = documentIndexingConfigParcel;
        this.mIntegerIndexingConfigParcel = integerIndexingConfigParcel;
        this.mJoinableConfigParcel = joinableConfigParcel;
        Objects.requireNonNull(str3);
        this.mDescription = str3;
        this.mEmbeddingIndexingConfigParcel = embeddingIndexingConfigParcel;
        this.mScoringEnabled = z;
    }

    public static PropertyConfigParcel createForEmbedding(String str, String str2, int i, int i2, int i3) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        return new PropertyConfigParcel(str, 7, i, null, null, null, null, null, str2, new EmbeddingIndexingConfigParcel(i2, i3), false);
    }

    public static PropertyConfigParcel createForLong(String str, String str2, int i, int i2, boolean z) {
        Objects.requireNonNull(str);
        IntegerIndexingConfigParcel integerIndexingConfigParcel = new IntegerIndexingConfigParcel(i2);
        Objects.requireNonNull(str2);
        return new PropertyConfigParcel(str, 2, i, null, null, null, integerIndexingConfigParcel, null, str2, null, z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PropertyConfigParcel)) {
            return false;
        }
        PropertyConfigParcel propertyConfigParcel = (PropertyConfigParcel) obj;
        return ObjectsCompat.equals(this.mName, propertyConfigParcel.mName) && Objects.equals(this.mDescription, propertyConfigParcel.mDescription) && ObjectsCompat.equals(Integer.valueOf(this.mDataType), Integer.valueOf(propertyConfigParcel.mDataType)) && ObjectsCompat.equals(Integer.valueOf(this.mCardinality), Integer.valueOf(propertyConfigParcel.mCardinality)) && ObjectsCompat.equals(this.mSchemaType, propertyConfigParcel.mSchemaType) && ObjectsCompat.equals(this.mStringIndexingConfigParcel, propertyConfigParcel.mStringIndexingConfigParcel) && ObjectsCompat.equals(this.mDocumentIndexingConfigParcel, propertyConfigParcel.mDocumentIndexingConfigParcel) && ObjectsCompat.equals(this.mIntegerIndexingConfigParcel, propertyConfigParcel.mIntegerIndexingConfigParcel) && ObjectsCompat.equals(this.mJoinableConfigParcel, propertyConfigParcel.mJoinableConfigParcel) && ObjectsCompat.equals(this.mEmbeddingIndexingConfigParcel, propertyConfigParcel.mEmbeddingIndexingConfigParcel) && this.mScoringEnabled == propertyConfigParcel.mScoringEnabled;
    }

    public int getCardinality() {
        return this.mCardinality;
    }

    public int getDataType() {
        return this.mDataType;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public DocumentIndexingConfigParcel getDocumentIndexingConfigParcel() {
        return this.mDocumentIndexingConfigParcel;
    }

    public EmbeddingIndexingConfigParcel getEmbeddingIndexingConfigParcel() {
        return this.mEmbeddingIndexingConfigParcel;
    }

    public IntegerIndexingConfigParcel getIntegerIndexingConfigParcel() {
        return this.mIntegerIndexingConfigParcel;
    }

    public JoinableConfigParcel getJoinableConfigParcel() {
        return this.mJoinableConfigParcel;
    }

    public String getName() {
        return this.mName;
    }

    public String getSchemaType() {
        return this.mSchemaType;
    }

    public StringIndexingConfigParcel getStringIndexingConfigParcel() {
        return this.mStringIndexingConfigParcel;
    }

    public int hashCode() {
        if (this.mHashCode == null) {
            this.mHashCode = Integer.valueOf(ObjectsCompat.hash(this.mName, this.mDescription, Integer.valueOf(this.mDataType), Integer.valueOf(this.mCardinality), this.mSchemaType, this.mStringIndexingConfigParcel, this.mDocumentIndexingConfigParcel, this.mIntegerIndexingConfigParcel, this.mJoinableConfigParcel, this.mEmbeddingIndexingConfigParcel, Boolean.valueOf(this.mScoringEnabled)));
        }
        return this.mHashCode.intValue();
    }

    public String toString() {
        return "{name: " + this.mName + ", description: " + this.mDescription + ", dataType: " + this.mDataType + ", cardinality: " + this.mCardinality + ", schemaType: " + this.mSchemaType + ", stringIndexingConfigParcel: " + this.mStringIndexingConfigParcel + ", documentIndexingConfigParcel: " + this.mDocumentIndexingConfigParcel + ", integerIndexingConfigParcel: " + this.mIntegerIndexingConfigParcel + ", joinableConfigParcel: " + this.mJoinableConfigParcel + ", embeddingIndexingConfigParcel: " + this.mEmbeddingIndexingConfigParcel + ", isScoringEnabled: " + this.mScoringEnabled + "}";
    }
}
