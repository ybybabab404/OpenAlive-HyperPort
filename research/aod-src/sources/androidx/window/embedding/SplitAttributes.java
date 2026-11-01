package androidx.window.embedding;

import androidx.window.core.SpecificationComputer;
import androidx.window.core.VerificationMode;
import com.miui.aod.components.view.BaseStyleSelectView;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: SplitAttributes.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class SplitAttributes {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = SplitAttributes.class.getSimpleName();
    public final LayoutDirection layoutDirection;
    public final SplitType splitType;

    /* JADX INFO: compiled from: SplitAttributes.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Builder {
        public SplitType splitType = SplitType.SPLIT_TYPE_EQUAL;
        public LayoutDirection layoutDirection = LayoutDirection.LOCALE;

        public final SplitAttributes build() {
            return new SplitAttributes(this.splitType, this.layoutDirection);
        }

        public final Builder setLayoutDirection(LayoutDirection layoutDirection) {
            Intrinsics.checkNotNullParameter(layoutDirection, "layoutDirection");
            this.layoutDirection = layoutDirection;
            return this;
        }

        public final Builder setSplitType(SplitType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            this.splitType = type;
            return this;
        }
    }

    /* JADX INFO: compiled from: SplitAttributes.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: SplitAttributes.kt */
    @Metadata
    public static final class LayoutDirection {
        public final String description;
        public final int value;
        public static final Companion Companion = new Companion(null);
        public static final LayoutDirection LOCALE = new LayoutDirection("LOCALE", 0);
        public static final LayoutDirection LEFT_TO_RIGHT = new LayoutDirection("LEFT_TO_RIGHT", 1);
        public static final LayoutDirection RIGHT_TO_LEFT = new LayoutDirection("RIGHT_TO_LEFT", 2);
        public static final LayoutDirection TOP_TO_BOTTOM = new LayoutDirection("TOP_TO_BOTTOM", 3);
        public static final LayoutDirection BOTTOM_TO_TOP = new LayoutDirection("BOTTOM_TO_TOP", 4);

        /* JADX INFO: compiled from: SplitAttributes.kt */
        @Metadata
        public static final class Companion {
            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public LayoutDirection(String str, int i) {
            this.description = str;
            this.value = i;
        }

        public String toString() {
            return this.description;
        }
    }

    /* JADX INFO: compiled from: SplitAttributes.kt */
    @Metadata
    public static final class SplitType {
        public static final Companion Companion;
        public static final SplitType SPLIT_TYPE_EQUAL;
        public static final SplitType SPLIT_TYPE_EXPAND;
        public static final SplitType SPLIT_TYPE_HINGE;
        public final String description;
        public final float value;

        /* JADX INFO: compiled from: SplitAttributes.kt */
        @Metadata
        public static final class Companion {
            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final SplitType buildSplitTypeFromValue$window_release(float f) {
                SplitType splitType = SplitType.SPLIT_TYPE_EXPAND;
                return f == splitType.getValue$window_release() ? splitType : ratio(f);
            }

            public final SplitType ratio(final float f) {
                SpecificationComputer.Companion companion = SpecificationComputer.Companion;
                Float fValueOf = Float.valueOf(f);
                String TAG = SplitAttributes.TAG;
                Intrinsics.checkNotNullExpressionValue(TAG, "TAG");
                Object objCompute = SpecificationComputer.Companion.startSpecification$default(companion, fValueOf, TAG, VerificationMode.STRICT, null, 4, null).require("Ratio must be in range (0.0, 1.0). Use SplitType.expandContainers() instead of 0 or 1.", new Function1<Float, Boolean>() { // from class: androidx.window.embedding.SplitAttributes$SplitType$Companion$ratio$checkedRatio$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    public final Boolean invoke(float f2) {
                        double d = f;
                        return Boolean.valueOf(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE <= d && d <= 1.0d && !ArraysKt.contains(new Float[]{Float.valueOf(0.0f), Float.valueOf(1.0f)}, Float.valueOf(f)));
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return invoke(((Number) obj).floatValue());
                    }
                }).compute();
                Intrinsics.checkNotNull(objCompute);
                float fFloatValue = ((Number) objCompute).floatValue();
                return new SplitType("ratio:" + fFloatValue, fFloatValue);
            }
        }

        static {
            Companion companion = new Companion(null);
            Companion = companion;
            SPLIT_TYPE_EXPAND = new SplitType("expandContainers", 0.0f);
            SPLIT_TYPE_EQUAL = companion.ratio(0.5f);
            SPLIT_TYPE_HINGE = new SplitType("hinge", -1.0f);
        }

        public SplitType(String description, float f) {
            Intrinsics.checkNotNullParameter(description, "description");
            this.description = description;
            this.value = f;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SplitType)) {
                return false;
            }
            SplitType splitType = (SplitType) obj;
            return this.value == splitType.value && Intrinsics.areEqual(this.description, splitType.description);
        }

        public final float getValue$window_release() {
            return this.value;
        }

        public int hashCode() {
            return this.description.hashCode() + (Float.hashCode(this.value) * 31);
        }

        public String toString() {
            return this.description;
        }
    }

    public SplitAttributes(SplitType splitType, LayoutDirection layoutDirection) {
        Intrinsics.checkNotNullParameter(splitType, "splitType");
        Intrinsics.checkNotNullParameter(layoutDirection, "layoutDirection");
        this.splitType = splitType;
        this.layoutDirection = layoutDirection;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SplitAttributes)) {
            return false;
        }
        SplitAttributes splitAttributes = (SplitAttributes) obj;
        return Intrinsics.areEqual(this.splitType, splitAttributes.splitType) && Intrinsics.areEqual(this.layoutDirection, splitAttributes.layoutDirection);
    }

    public final LayoutDirection getLayoutDirection() {
        return this.layoutDirection;
    }

    public final SplitType getSplitType() {
        return this.splitType;
    }

    public int hashCode() {
        return (this.splitType.hashCode() * 31) + this.layoutDirection.hashCode();
    }

    public String toString() {
        return SplitAttributes.class.getSimpleName() + ":{splitType=" + this.splitType + ", layoutDir=" + this.layoutDirection + " }";
    }
}
