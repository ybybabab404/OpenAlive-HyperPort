package androidx.appfunctions;

import android.os.Bundle;
import com.miui.maml.folme.AnimatedPropertyType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: AppFunctionException.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public abstract class AppFunctionException extends Exception {
    public static final Companion Companion = new Companion(null);
    public final int errorCategory;
    public final String errorMessage;
    public final Bundle extras;
    public final int internalErrorCode;

    /* JADX INFO: compiled from: AppFunctionException.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final AppFunctionException fromPlatformClass(android.app.appfunctions.AppFunctionException exception) {
            Intrinsics.checkNotNullParameter(exception, "exception");
            int errorCode = exception.getErrorCode();
            if (errorCode == 2000) {
                String errorMessage = exception.getErrorMessage();
                Bundle extras = exception.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras, "getExtras(...)");
                return new AppFunctionSystemUnknownException(errorMessage, extras);
            }
            if (errorCode == 2001) {
                String errorMessage2 = exception.getErrorMessage();
                Bundle extras2 = exception.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras2, "getExtras(...)");
                return new AppFunctionCancelledException(errorMessage2, extras2);
            }
            if (errorCode == 3000) {
                String errorMessage3 = exception.getErrorMessage();
                Bundle extras3 = exception.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras3, "getExtras(...)");
                return new AppFunctionAppUnknownException(errorMessage3, extras3);
            }
            if (errorCode == 3500) {
                String errorMessage4 = exception.getErrorMessage();
                Bundle extras4 = exception.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras4, "getExtras(...)");
                return new AppFunctionPermissionRequiredException(errorMessage4, extras4);
            }
            if (errorCode == 3501) {
                String errorMessage5 = exception.getErrorMessage();
                Bundle extras5 = exception.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras5, "getExtras(...)");
                return new AppFunctionNotSupportedException(errorMessage5, extras5);
            }
            switch (errorCode) {
                case 1000:
                    String errorMessage6 = exception.getErrorMessage();
                    Bundle extras6 = exception.getExtras();
                    Intrinsics.checkNotNullExpressionValue(extras6, "getExtras(...)");
                    return new AppFunctionDeniedException(errorMessage6, extras6);
                case 1001:
                    String errorMessage7 = exception.getErrorMessage();
                    Bundle extras7 = exception.getExtras();
                    Intrinsics.checkNotNullExpressionValue(extras7, "getExtras(...)");
                    return new AppFunctionInvalidArgumentException(errorMessage7, extras7);
                case AnimatedPropertyType.TEXT_SIZE /* 1002 */:
                    String errorMessage8 = exception.getErrorMessage();
                    Bundle extras8 = exception.getExtras();
                    Intrinsics.checkNotNullExpressionValue(extras8, "getExtras(...)");
                    return new AppFunctionDisabledException(errorMessage8, extras8);
                case AnimatedPropertyType.TEXT_COLOR /* 1003 */:
                    String errorMessage9 = exception.getErrorMessage();
                    Bundle extras9 = exception.getExtras();
                    Intrinsics.checkNotNullExpressionValue(extras9, "getExtras(...)");
                    return new AppFunctionFunctionNotFoundException(errorMessage9, extras9);
                default:
                    switch (errorCode) {
                        case 1500:
                            String errorMessage10 = exception.getErrorMessage();
                            Bundle extras10 = exception.getExtras();
                            Intrinsics.checkNotNullExpressionValue(extras10, "getExtras(...)");
                            return new AppFunctionElementNotFoundException(errorMessage10, extras10);
                        case 1501:
                            String errorMessage11 = exception.getErrorMessage();
                            Bundle extras11 = exception.getExtras();
                            Intrinsics.checkNotNullExpressionValue(extras11, "getExtras(...)");
                            return new AppFunctionLimitExceededException(errorMessage11, extras11);
                        case 1502:
                            String errorMessage12 = exception.getErrorMessage();
                            Bundle extras12 = exception.getExtras();
                            Intrinsics.checkNotNullExpressionValue(extras12, "getExtras(...)");
                            return new AppFunctionElementAlreadyExistsException(errorMessage12, extras12);
                        default:
                            int errorCode2 = exception.getErrorCode();
                            String errorMessage13 = exception.getErrorMessage();
                            Bundle extras13 = exception.getExtras();
                            Intrinsics.checkNotNullExpressionValue(extras13, "getExtras(...)");
                            return new AppFunctionUnknownException(errorCode2, errorMessage13, extras13);
                    }
            }
        }

        public final AppFunctionException fromPlatformExtensionsClass(com.android.extensions.appfunctions.AppFunctionException exception) {
            Intrinsics.checkNotNullParameter(exception, "exception");
            int errorCode = exception.getErrorCode();
            if (errorCode == 2000) {
                String errorMessage = exception.getErrorMessage();
                Bundle extras = exception.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras, "getExtras(...)");
                return new AppFunctionSystemUnknownException(errorMessage, extras);
            }
            if (errorCode == 2001) {
                String errorMessage2 = exception.getErrorMessage();
                Bundle extras2 = exception.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras2, "getExtras(...)");
                return new AppFunctionCancelledException(errorMessage2, extras2);
            }
            if (errorCode == 3000) {
                String errorMessage3 = exception.getErrorMessage();
                Bundle extras3 = exception.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras3, "getExtras(...)");
                return new AppFunctionAppUnknownException(errorMessage3, extras3);
            }
            if (errorCode == 3500) {
                String errorMessage4 = exception.getErrorMessage();
                Bundle extras4 = exception.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras4, "getExtras(...)");
                return new AppFunctionPermissionRequiredException(errorMessage4, extras4);
            }
            if (errorCode == 3501) {
                String errorMessage5 = exception.getErrorMessage();
                Bundle extras5 = exception.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras5, "getExtras(...)");
                return new AppFunctionNotSupportedException(errorMessage5, extras5);
            }
            switch (errorCode) {
                case 1000:
                    String errorMessage6 = exception.getErrorMessage();
                    Bundle extras6 = exception.getExtras();
                    Intrinsics.checkNotNullExpressionValue(extras6, "getExtras(...)");
                    return new AppFunctionDeniedException(errorMessage6, extras6);
                case 1001:
                    String errorMessage7 = exception.getErrorMessage();
                    Bundle extras7 = exception.getExtras();
                    Intrinsics.checkNotNullExpressionValue(extras7, "getExtras(...)");
                    return new AppFunctionInvalidArgumentException(errorMessage7, extras7);
                case AnimatedPropertyType.TEXT_SIZE /* 1002 */:
                    String errorMessage8 = exception.getErrorMessage();
                    Bundle extras8 = exception.getExtras();
                    Intrinsics.checkNotNullExpressionValue(extras8, "getExtras(...)");
                    return new AppFunctionDisabledException(errorMessage8, extras8);
                case AnimatedPropertyType.TEXT_COLOR /* 1003 */:
                    String errorMessage9 = exception.getErrorMessage();
                    Bundle extras9 = exception.getExtras();
                    Intrinsics.checkNotNullExpressionValue(extras9, "getExtras(...)");
                    return new AppFunctionFunctionNotFoundException(errorMessage9, extras9);
                default:
                    switch (errorCode) {
                        case 1500:
                            String errorMessage10 = exception.getErrorMessage();
                            Bundle extras10 = exception.getExtras();
                            Intrinsics.checkNotNullExpressionValue(extras10, "getExtras(...)");
                            return new AppFunctionElementNotFoundException(errorMessage10, extras10);
                        case 1501:
                            String errorMessage11 = exception.getErrorMessage();
                            Bundle extras11 = exception.getExtras();
                            Intrinsics.checkNotNullExpressionValue(extras11, "getExtras(...)");
                            return new AppFunctionLimitExceededException(errorMessage11, extras11);
                        case 1502:
                            String errorMessage12 = exception.getErrorMessage();
                            Bundle extras12 = exception.getExtras();
                            Intrinsics.checkNotNullExpressionValue(extras12, "getExtras(...)");
                            return new AppFunctionElementAlreadyExistsException(errorMessage12, extras12);
                        default:
                            int errorCode2 = exception.getErrorCode();
                            String errorMessage13 = exception.getErrorMessage();
                            Bundle extras13 = exception.getExtras();
                            Intrinsics.checkNotNullExpressionValue(extras13, "getExtras(...)");
                            return new AppFunctionUnknownException(errorCode2, errorMessage13, extras13);
                    }
            }
        }
    }

    /* JADX INFO: compiled from: AppFunctionException.kt */
    @Retention(RetentionPolicy.RUNTIME)
    @Metadata
    public @interface ErrorCategory {
    }

    /* JADX INFO: compiled from: AppFunctionException.kt */
    @Retention(RetentionPolicy.RUNTIME)
    @Metadata
    public @interface ErrorCode {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppFunctionException(int i, String str, Bundle extras) {
        super(str);
        Intrinsics.checkNotNullParameter(extras, "extras");
        this.internalErrorCode = i;
        this.errorMessage = str;
        this.extras = extras;
        this.errorCategory = (1000 > i || i >= 2000) ? (2000 > i || i >= 3000) ? (3000 > i || i >= 4000) ? 0 : 3 : 2 : 1;
    }

    public final String getErrorMessage() {
        return this.errorMessage;
    }

    public final android.app.appfunctions.AppFunctionException toPlatformClass() {
        AppFunctionException$$ExternalSyntheticApiModelOutline1.m();
        return AppFunctionException$$ExternalSyntheticApiModelOutline0.m(this.internalErrorCode, this.errorMessage, this.extras);
    }

    public final com.android.extensions.appfunctions.AppFunctionException toPlatformExtensionsClass() {
        return new com.android.extensions.appfunctions.AppFunctionException(this.internalErrorCode, this.errorMessage, this.extras);
    }
}
