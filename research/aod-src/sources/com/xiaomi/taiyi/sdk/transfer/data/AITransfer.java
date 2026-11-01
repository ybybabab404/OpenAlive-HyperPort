package com.xiaomi.taiyi.sdk.transfer.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
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
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public class AITransfer implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    public AIBody body;
    public AIHeaders headers;

    @Metadata
    public static final class CREATOR implements Parcelable.Creator<AITransfer> {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AITransfer createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new AITransfer(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AITransfer[] newArray(int i) {
            return new AITransfer[i];
        }
    }

    public AITransfer() {
        setHeaders$base_release(new AIHeaders());
        setBody$base_release(new AIBody());
    }

    public AITransfer(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        setHeaders$base_release(new AIHeaders(context));
        setBody$base_release(new AIBody());
    }

    public AITransfer(Parcel parcel) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final AIBody getBody() {
        AIBody aIBody = this.body;
        if (aIBody != null) {
            return aIBody;
        }
        Intrinsics.throwUninitializedPropertyAccessException("body");
        return null;
    }

    public final AIHeaders getHeaders() {
        AIHeaders aIHeaders = this.headers;
        if (aIHeaders != null) {
            return aIHeaders;
        }
        Intrinsics.throwUninitializedPropertyAccessException("headers");
        return null;
    }

    public final void readFromParcel(Parcel parcel) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        ClassLoader classLoader = AITransfer.class.getClassLoader();
        AIHeaders aIHeaders = (AIHeaders) parcel.readParcelable(classLoader, AIHeaders.class);
        if (aIHeaders == null) {
            aIHeaders = new AIHeaders();
        }
        setHeaders$base_release(aIHeaders);
        AIBody aIBody = (AIBody) parcel.readParcelable(classLoader, AIBody.class);
        if (aIBody == null) {
            aIBody = new AIBody();
        }
        setBody$base_release(aIBody);
    }

    public final void setBody$base_release(AIBody aIBody) {
        Intrinsics.checkNotNullParameter(aIBody, "<set-?>");
        this.body = aIBody;
    }

    public final void setHeaders$base_release(AIHeaders aIHeaders) {
        Intrinsics.checkNotNullParameter(aIHeaders, "<set-?>");
        this.headers = aIHeaders;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int i) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeParcelable(getHeaders(), i);
        dest.writeParcelable(getBody(), i);
    }
}
