package androidx.appsearch.app;

import android.os.Parcelable;
import androidx.annotation.OptIn;
import androidx.annotation.RestrictTo;
import androidx.appsearch.exceptions.IllegalSchemaException;
import androidx.appsearch.flags.FlaggedApi;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.PropertyConfigParcel;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.appsearch.util.IndentingStringBuilder;
import androidx.collection.ArraySet;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@SafeParcelable.Class
public final class AppSearchSchema extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.AppSearchSchemaCreator();
    public final String mDescription;
    public final List mParentTypes;
    public final List mPropertyConfigParcels;
    public final String mSchemaType;

    @ExperimentalAppSearchApi
    @FlaggedApi
    public static final class BlobHandlePropertyConfig extends PropertyConfig {

        @FlaggedApi
        public static final class Builder {
        }

        public BlobHandlePropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }
    }

    public static final class BooleanPropertyConfig extends PropertyConfig {

        public static final class Builder {
        }

        public BooleanPropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }
    }

    public static final class Builder {
        public String mSchemaType;
        public String mDescription = "";
        public ArrayList mPropertyConfigParcels = new ArrayList();
        public LinkedHashSet mParentTypes = new LinkedHashSet();
        public final Set mPropertyNames = new ArraySet();
        public boolean mBuilt = false;

        public Builder(String str) {
            this.mSchemaType = (String) Preconditions.checkNotNull(str);
        }

        public Builder addProperty(PropertyConfig propertyConfig) {
            Preconditions.checkNotNull(propertyConfig);
            resetIfBuilt();
            String name = propertyConfig.getName();
            if (this.mPropertyNames.add(name)) {
                this.mPropertyConfigParcels.add(propertyConfig.mPropertyConfigParcel);
                return this;
            }
            throw new IllegalSchemaException("Property defined more than once: " + name);
        }

        public AppSearchSchema build() {
            this.mBuilt = true;
            return new AppSearchSchema(this.mSchemaType, this.mPropertyConfigParcels, new ArrayList(this.mParentTypes), this.mDescription);
        }

        public final void resetIfBuilt() {
            if (this.mBuilt) {
                this.mPropertyConfigParcels = new ArrayList(this.mPropertyConfigParcels);
                this.mParentTypes = new LinkedHashSet(this.mParentTypes);
                this.mBuilt = false;
            }
        }
    }

    public static final class BytesPropertyConfig extends PropertyConfig {

        public static final class Builder {
        }

        public BytesPropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }
    }

    public static final class DocumentPropertyConfig extends PropertyConfig {

        public static final class Builder {
        }

        public DocumentPropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }

        public void appendDocumentPropertyConfigFields(IndentingStringBuilder indentingStringBuilder) {
            indentingStringBuilder.append("shouldIndexNestedProperties: ").append(Boolean.valueOf(shouldIndexNestedProperties())).append(",\n");
            indentingStringBuilder.append("indexableNestedProperties: ").append(getIndexableNestedProperties()).append(",\n");
            indentingStringBuilder.append("schemaType: \"").append(getSchemaType()).append("\",\n");
        }

        public List getIndexableNestedProperties() {
            List indexableNestedPropertiesList;
            PropertyConfigParcel.DocumentIndexingConfigParcel documentIndexingConfigParcel = this.mPropertyConfigParcel.getDocumentIndexingConfigParcel();
            if (documentIndexingConfigParcel != null && (indexableNestedPropertiesList = documentIndexingConfigParcel.getIndexableNestedPropertiesList()) != null) {
                return Collections.unmodifiableList(indexableNestedPropertiesList);
            }
            return Collections.EMPTY_LIST;
        }

        public String getSchemaType() {
            return (String) Preconditions.checkNotNull(this.mPropertyConfigParcel.getSchemaType());
        }

        public boolean shouldIndexNestedProperties() {
            PropertyConfigParcel.DocumentIndexingConfigParcel documentIndexingConfigParcel = this.mPropertyConfigParcel.getDocumentIndexingConfigParcel();
            if (documentIndexingConfigParcel == null) {
                return false;
            }
            return documentIndexingConfigParcel.shouldIndexNestedProperties();
        }
    }

    public static final class DoublePropertyConfig extends PropertyConfig {

        public static final class Builder {
        }

        public DoublePropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }
    }

    @FlaggedApi
    public static final class EmbeddingPropertyConfig extends PropertyConfig {

        @OptIn
        @FlaggedApi
        public static final class Builder {
            public final String mPropertyName;
            public String mDescription = "";
            public int mCardinality = 2;
            public int mIndexingType = 0;
            public int mQuantizationType = 0;

            public Builder(String str) {
                this.mPropertyName = (String) Preconditions.checkNotNull(str);
            }

            public EmbeddingPropertyConfig build() {
                return new EmbeddingPropertyConfig(PropertyConfigParcel.createForEmbedding(this.mPropertyName, this.mDescription, this.mCardinality, this.mIndexingType, this.mQuantizationType));
            }

            public Builder setCardinality(int i) {
                Preconditions.checkArgumentInRange(i, 1, 3, "cardinality");
                this.mCardinality = i;
                return this;
            }

            public Builder setIndexingType(int i) {
                Preconditions.checkArgumentInRange(i, 0, 1, "indexingType");
                this.mIndexingType = i;
                return this;
            }
        }

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        public @interface IndexingType {
        }

        @ExperimentalAppSearchApi
        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        public @interface QuantizationType {
        }

        public EmbeddingPropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }

        public int getIndexingType() {
            PropertyConfigParcel.EmbeddingIndexingConfigParcel embeddingIndexingConfigParcel = this.mPropertyConfigParcel.getEmbeddingIndexingConfigParcel();
            if (embeddingIndexingConfigParcel == null) {
                return 0;
            }
            return embeddingIndexingConfigParcel.getIndexingType();
        }
    }

    public static final class LongPropertyConfig extends PropertyConfig {

        public static final class Builder {
            public final String mPropertyName;
            public String mDescription = "";
            public int mCardinality = 2;
            public int mIndexingType = 0;
            public boolean mScoringEnabled = false;

            public Builder(String str) {
                this.mPropertyName = (String) Preconditions.checkNotNull(str);
            }

            public LongPropertyConfig build() {
                return new LongPropertyConfig(PropertyConfigParcel.createForLong(this.mPropertyName, this.mDescription, this.mCardinality, this.mIndexingType, this.mScoringEnabled));
            }

            public Builder setCardinality(int i) {
                Preconditions.checkArgumentInRange(i, 1, 3, "cardinality");
                this.mCardinality = i;
                return this;
            }
        }

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        public @interface IndexingType {
        }

        public LongPropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }

        public void appendLongPropertyConfigFields(IndentingStringBuilder indentingStringBuilder) {
            int indexingType = getIndexingType();
            if (indexingType == 0) {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_NONE,\n");
            } else if (indexingType != 1) {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_UNKNOWN,\n");
            } else {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_RANGE,\n");
            }
        }

        public int getIndexingType() {
            PropertyConfigParcel.IntegerIndexingConfigParcel integerIndexingConfigParcel = this.mPropertyConfigParcel.getIntegerIndexingConfigParcel();
            if (integerIndexingConfigParcel == null) {
                return 0;
            }
            return integerIndexingConfigParcel.getIndexingType();
        }
    }

    public static abstract class PropertyConfig {
        public final PropertyConfigParcel mPropertyConfigParcel;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        public @interface Cardinality {
        }

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        public @interface DataType {
        }

        public PropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            this.mPropertyConfigParcel = (PropertyConfigParcel) Preconditions.checkNotNull(propertyConfigParcel);
        }

        public static PropertyConfig fromParcel(PropertyConfigParcel propertyConfigParcel) {
            Preconditions.checkNotNull(propertyConfigParcel);
            switch (propertyConfigParcel.getDataType()) {
                case 1:
                    return new StringPropertyConfig(propertyConfigParcel);
                case 2:
                    return new LongPropertyConfig(propertyConfigParcel);
                case 3:
                    return new DoublePropertyConfig(propertyConfigParcel);
                case 4:
                    return new BooleanPropertyConfig(propertyConfigParcel);
                case 5:
                    return new BytesPropertyConfig(propertyConfigParcel);
                case 6:
                    return new DocumentPropertyConfig(propertyConfigParcel);
                case 7:
                    return new EmbeddingPropertyConfig(propertyConfigParcel);
                case 8:
                    return new BlobHandlePropertyConfig(propertyConfigParcel);
                default:
                    throw new IllegalArgumentException("Unsupported property bundle of type " + propertyConfigParcel.getDataType() + "; contents: " + propertyConfigParcel);
            }
        }

        public void appendPropertyConfigString(IndentingStringBuilder indentingStringBuilder) {
            Preconditions.checkNotNull(indentingStringBuilder);
            indentingStringBuilder.append("{\n");
            indentingStringBuilder.increaseIndentLevel();
            indentingStringBuilder.append("name: \"").append(getName()).append("\",\n");
            indentingStringBuilder.append("description: \"").append(getDescription()).append("\",\n");
            if (this instanceof StringPropertyConfig) {
                ((StringPropertyConfig) this).appendStringPropertyConfigFields(indentingStringBuilder);
            } else if (this instanceof DocumentPropertyConfig) {
                ((DocumentPropertyConfig) this).appendDocumentPropertyConfigFields(indentingStringBuilder);
            } else if (this instanceof LongPropertyConfig) {
                ((LongPropertyConfig) this).appendLongPropertyConfigFields(indentingStringBuilder);
            }
            int cardinality = getCardinality();
            if (cardinality == 1) {
                indentingStringBuilder.append("cardinality: CARDINALITY_REPEATED,\n");
            } else if (cardinality == 2) {
                indentingStringBuilder.append("cardinality: CARDINALITY_OPTIONAL,\n");
            } else if (cardinality != 3) {
                indentingStringBuilder.append("cardinality: CARDINALITY_UNKNOWN,\n");
            } else {
                indentingStringBuilder.append("cardinality: CARDINALITY_REQUIRED,\n");
            }
            switch (getDataType()) {
                case 1:
                    indentingStringBuilder.append("dataType: DATA_TYPE_STRING,\n");
                    break;
                case 2:
                    indentingStringBuilder.append("dataType: DATA_TYPE_LONG,\n");
                    break;
                case 3:
                    indentingStringBuilder.append("dataType: DATA_TYPE_DOUBLE,\n");
                    break;
                case 4:
                    indentingStringBuilder.append("dataType: DATA_TYPE_BOOLEAN,\n");
                    break;
                case 5:
                    indentingStringBuilder.append("dataType: DATA_TYPE_BYTES,\n");
                    break;
                case 6:
                    indentingStringBuilder.append("dataType: DATA_TYPE_DOCUMENT,\n");
                    break;
                case 7:
                    indentingStringBuilder.append("dataType: DATA_TYPE_EMBEDDING,\n");
                    break;
                case 8:
                    indentingStringBuilder.append("dataType: DATA_TYPE_BLOB_HANDLE,\n");
                    break;
                default:
                    indentingStringBuilder.append("dataType: DATA_TYPE_UNKNOWN,\n");
                    break;
            }
            indentingStringBuilder.decreaseIndentLevel();
            indentingStringBuilder.append("}");
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof PropertyConfig) {
                return ObjectsCompat.equals(this.mPropertyConfigParcel, ((PropertyConfig) obj).mPropertyConfigParcel);
            }
            return false;
        }

        public int getCardinality() {
            return this.mPropertyConfigParcel.getCardinality();
        }

        public int getDataType() {
            return this.mPropertyConfigParcel.getDataType();
        }

        public String getDescription() {
            return this.mPropertyConfigParcel.getDescription();
        }

        public String getName() {
            return this.mPropertyConfigParcel.getName();
        }

        public int hashCode() {
            return this.mPropertyConfigParcel.hashCode();
        }

        public String toString() {
            IndentingStringBuilder indentingStringBuilder = new IndentingStringBuilder();
            appendPropertyConfigString(indentingStringBuilder);
            return indentingStringBuilder.toString();
        }
    }

    public static final class StringPropertyConfig extends PropertyConfig {

        @OptIn
        public static final class Builder {
        }

        @ExperimentalAppSearchApi
        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        public @interface DeletePropagationType {
        }

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        public @interface IndexingType {
        }

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        public @interface JoinableValueType {
        }

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        public @interface TokenizerType {
        }

        public StringPropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }

        public void appendStringPropertyConfigFields(IndentingStringBuilder indentingStringBuilder) {
            int indexingType = getIndexingType();
            if (indexingType == 0) {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_NONE,\n");
            } else if (indexingType == 1) {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_EXACT_TERMS,\n");
            } else if (indexingType != 2) {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_UNKNOWN,\n");
            } else {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_PREFIXES,\n");
            }
            int tokenizerType = getTokenizerType();
            if (tokenizerType == 0) {
                indentingStringBuilder.append("tokenizerType: TOKENIZER_TYPE_NONE,\n");
            } else if (tokenizerType == 1) {
                indentingStringBuilder.append("tokenizerType: TOKENIZER_TYPE_PLAIN,\n");
            } else if (tokenizerType == 2) {
                indentingStringBuilder.append("tokenizerType: TOKENIZER_TYPE_VERBATIM,\n");
            } else if (tokenizerType != 3) {
                indentingStringBuilder.append("tokenizerType: TOKENIZER_TYPE_UNKNOWN,\n");
            } else {
                indentingStringBuilder.append("tokenizerType: TOKENIZER_TYPE_RFC822,\n");
            }
            int joinableValueType = getJoinableValueType();
            if (joinableValueType == 0) {
                indentingStringBuilder.append("joinableValueType: JOINABLE_VALUE_TYPE_NONE,\n");
            } else if (joinableValueType != 1) {
                indentingStringBuilder.append("joinableValueType: JOINABLE_VALUE_TYPE_UNKNOWN,\n");
            } else {
                indentingStringBuilder.append("joinableValueType: JOINABLE_VALUE_TYPE_QUALIFIED_ID,\n");
            }
            int deletePropagationType = getDeletePropagationType();
            if (deletePropagationType == 0) {
                indentingStringBuilder.append("deletePropagationType: DELETE_PROPAGATION_TYPE_NONE,\n");
            } else if (deletePropagationType != 1) {
                indentingStringBuilder.append("deletePropagationType: DELETE_PROPAGATION_TYPE_UNKNOWN,\n");
            } else {
                indentingStringBuilder.append("deletePropagationType: DELETE_PROPAGATION_TYPE_PROPAGATE_FROM,\n");
            }
        }

        public int getDeletePropagationType() {
            PropertyConfigParcel.JoinableConfigParcel joinableConfigParcel = this.mPropertyConfigParcel.getJoinableConfigParcel();
            if (joinableConfigParcel == null) {
                return 0;
            }
            return joinableConfigParcel.getDeletePropagationType();
        }

        public int getIndexingType() {
            PropertyConfigParcel.StringIndexingConfigParcel stringIndexingConfigParcel = this.mPropertyConfigParcel.getStringIndexingConfigParcel();
            if (stringIndexingConfigParcel == null) {
                return 0;
            }
            return stringIndexingConfigParcel.getIndexingType();
        }

        public int getJoinableValueType() {
            PropertyConfigParcel.JoinableConfigParcel joinableConfigParcel = this.mPropertyConfigParcel.getJoinableConfigParcel();
            if (joinableConfigParcel == null) {
                return 0;
            }
            return joinableConfigParcel.getJoinableValueType();
        }

        public int getTokenizerType() {
            PropertyConfigParcel.StringIndexingConfigParcel stringIndexingConfigParcel = this.mPropertyConfigParcel.getStringIndexingConfigParcel();
            if (stringIndexingConfigParcel == null) {
                return 0;
            }
            return stringIndexingConfigParcel.getTokenizerType();
        }
    }

    public AppSearchSchema(String str, List list, List list2, String str2) {
        this.mSchemaType = (String) Preconditions.checkNotNull(str);
        this.mPropertyConfigParcels = (List) Preconditions.checkNotNull(list);
        this.mParentTypes = (List) Preconditions.checkNotNull(list2);
        this.mDescription = (String) Preconditions.checkNotNull(str2);
    }

    public final void appendAppSearchSchemaString(IndentingStringBuilder indentingStringBuilder) {
        Preconditions.checkNotNull(indentingStringBuilder);
        indentingStringBuilder.append("{\n");
        indentingStringBuilder.increaseIndentLevel();
        indentingStringBuilder.append("schemaType: \"").append(getSchemaType()).append("\",\n");
        indentingStringBuilder.append("description: \"").append(getDescription()).append("\",\n");
        indentingStringBuilder.append("properties: [\n");
        PropertyConfig[] propertyConfigArr = (PropertyConfig[]) getProperties().toArray(new PropertyConfig[0]);
        Arrays.sort(propertyConfigArr, new Comparator() { // from class: androidx.appsearch.app.AppSearchSchema$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((AppSearchSchema.PropertyConfig) obj).getName().compareTo(((AppSearchSchema.PropertyConfig) obj2).getName());
            }
        });
        for (int i = 0; i < propertyConfigArr.length; i++) {
            PropertyConfig propertyConfig = propertyConfigArr[i];
            indentingStringBuilder.increaseIndentLevel();
            propertyConfig.appendPropertyConfigString(indentingStringBuilder);
            if (i != propertyConfigArr.length - 1) {
                indentingStringBuilder.append(",\n");
            }
            indentingStringBuilder.decreaseIndentLevel();
        }
        indentingStringBuilder.append("\n");
        indentingStringBuilder.append("]\n");
        indentingStringBuilder.decreaseIndentLevel();
        indentingStringBuilder.append("}");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppSearchSchema)) {
            return false;
        }
        AppSearchSchema appSearchSchema = (AppSearchSchema) obj;
        if (getSchemaType().equals(appSearchSchema.getSchemaType()) && getDescription().equals(appSearchSchema.getDescription()) && getParentTypes().equals(appSearchSchema.getParentTypes())) {
            return getProperties().equals(appSearchSchema.getProperties());
        }
        return false;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public List getParentTypes() {
        return Collections.unmodifiableList(this.mParentTypes);
    }

    public List getProperties() {
        if (this.mPropertyConfigParcels.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList(this.mPropertyConfigParcels.size());
        for (int i = 0; i < this.mPropertyConfigParcels.size(); i++) {
            arrayList.add(PropertyConfig.fromParcel((PropertyConfigParcel) this.mPropertyConfigParcels.get(i)));
        }
        return arrayList;
    }

    public String getSchemaType() {
        return this.mSchemaType;
    }

    public int hashCode() {
        return ObjectsCompat.hash(getSchemaType(), getProperties(), getParentTypes(), getDescription());
    }

    public String toString() {
        IndentingStringBuilder indentingStringBuilder = new IndentingStringBuilder();
        appendAppSearchSchemaString(indentingStringBuilder);
        return indentingStringBuilder.toString();
    }
}
