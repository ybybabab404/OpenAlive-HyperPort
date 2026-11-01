package androidx.appfunctions;

import android.app.appsearch.GenericDocument;
import android.content.Context;
import android.os.Bundle;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
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
/* JADX INFO: compiled from: ExecuteAppFunctionResponse.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public interface ExecuteAppFunctionResponse {

    /* JADX INFO: compiled from: ExecuteAppFunctionResponse.kt */
    @Metadata
    public static final class Error implements ExecuteAppFunctionResponse {
        public final AppFunctionException error;

        public Error(AppFunctionException error) {
            Intrinsics.checkNotNullParameter(error, "error");
            this.error = error;
        }

        public final AppFunctionException getError() {
            return this.error;
        }

        public String toString() {
            return "AppFunctionResponse.Error(error=" + this.error + ')';
        }
    }

    /* JADX INFO: compiled from: ExecuteAppFunctionResponse.kt */
    @Metadata
    public static final class Success implements ExecuteAppFunctionResponse {
        public static final Companion Companion = new Companion(null);
        public final AppFunctionData returnValue;

        /* JADX INFO: compiled from: ExecuteAppFunctionResponse.kt */
        @Metadata
        public static final class Companion {
            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final Success fromPlatformClass$appfunctions_release(android.app.appfunctions.ExecuteAppFunctionResponse response) {
                Intrinsics.checkNotNullParameter(response, "response");
                GenericDocument resultDocument = response.getResultDocument();
                Intrinsics.checkNotNullExpressionValue(resultDocument, "getResultDocument(...)");
                Bundle extras = response.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras, "getExtras(...)");
                return new Success(new AppFunctionData(resultDocument, extras));
            }

            public final Success fromPlatformExtensionClass$appfunctions_release(com.android.extensions.appfunctions.ExecuteAppFunctionResponse response) {
                Intrinsics.checkNotNullParameter(response, "response");
                GenericDocument resultDocument = response.getResultDocument();
                Intrinsics.checkNotNullExpressionValue(resultDocument, "getResultDocument(...)");
                Bundle extras = response.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras, "getExtras(...)");
                return new Success(new AppFunctionData(resultDocument, extras));
            }
        }

        public Success(AppFunctionData returnValue) {
            Intrinsics.checkNotNullParameter(returnValue, "returnValue");
            this.returnValue = returnValue;
        }

        public static final Unit grantUriAccess$lambda$0(Context context, String str, AppFunctionUriGrant uriGrant) {
            Intrinsics.checkNotNullParameter(uriGrant, "uriGrant");
            context.grantUriPermission(str, uriGrant.getUri(), uriGrant.getModeFlags());
            return Unit.INSTANCE;
        }

        public final AppFunctionData getReturnValue() {
            return this.returnValue;
        }

        public final void grantUriAccess$appfunctions_release(final Context context, final String callingPackageName) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(callingPackageName, "callingPackageName");
            this.returnValue.visitAppFunctionUriGrants(new Function1() { // from class: androidx.appfunctions.ExecuteAppFunctionResponse$Success$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ExecuteAppFunctionResponse.Success.grantUriAccess$lambda$0(context, callingPackageName, (AppFunctionUriGrant) obj);
                }
            });
        }

        public final android.app.appfunctions.ExecuteAppFunctionResponse toPlatformClass$appfunctions_release() {
            ExecuteAppFunctionResponse$Success$$ExternalSyntheticApiModelOutline1.m();
            return ExecuteAppFunctionResponse$Success$$ExternalSyntheticApiModelOutline0.m(this.returnValue.getGenericDocument$appfunctions_release(), this.returnValue.getExtras$appfunctions_release());
        }

        public final com.android.extensions.appfunctions.ExecuteAppFunctionResponse toPlatformExtensionClass$appfunctions_release() {
            return new com.android.extensions.appfunctions.ExecuteAppFunctionResponse(this.returnValue.getGenericDocument$appfunctions_release(), this.returnValue.getExtras$appfunctions_release());
        }
    }
}
