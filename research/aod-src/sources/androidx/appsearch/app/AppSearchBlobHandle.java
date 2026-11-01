package androidx.appsearch.app;

import android.os.Parcelable;
import androidx.appsearch.flags.FlaggedApi;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.appsearch.util.IndentingStringBuilder;
import java.util.Arrays;
import java.util.Objects;
import miuix.animation.internal.TransitionInfo;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@FlaggedApi
@SafeParcelable.Class
public final class AppSearchBlobHandle extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.AppSearchBlobHandleCreator();
    public final String mDatabaseName;
    public Integer mHashCode;
    public final String mNamespace;
    public final String mPackageName;
    public final byte[] mSha256Digest;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppSearchBlobHandle)) {
            return false;
        }
        AppSearchBlobHandle appSearchBlobHandle = (AppSearchBlobHandle) obj;
        return Arrays.equals(this.mSha256Digest, appSearchBlobHandle.mSha256Digest) && this.mPackageName.equals(appSearchBlobHandle.mPackageName) && this.mDatabaseName.equals(appSearchBlobHandle.mDatabaseName) && this.mNamespace.equals(appSearchBlobHandle.mNamespace);
    }

    public int hashCode() {
        if (this.mHashCode == null) {
            this.mHashCode = Integer.valueOf(Objects.hash(Integer.valueOf(Arrays.hashCode(this.mSha256Digest)), this.mPackageName, this.mDatabaseName, this.mNamespace));
        }
        return this.mHashCode.intValue();
    }

    public String toString() {
        IndentingStringBuilder indentingStringBuilder = new IndentingStringBuilder();
        indentingStringBuilder.append("{\n");
        indentingStringBuilder.increaseIndentLevel();
        indentingStringBuilder.append("packageName: \"").append(this.mPackageName).append("\",\n");
        indentingStringBuilder.append("databaseName: \"").append(this.mDatabaseName).append("\",\n");
        indentingStringBuilder.append("namespace: \"").append(this.mNamespace).append("\",\n");
        indentingStringBuilder.append("digest: \"");
        for (byte b : this.mSha256Digest) {
            String hexString = Integer.toHexString(b & TransitionInfo.INIT);
            if (hexString.length() == 1) {
                indentingStringBuilder.append((Object) '0');
            }
            indentingStringBuilder.append(hexString);
        }
        indentingStringBuilder.append("\",\n").decreaseIndentLevel();
        indentingStringBuilder.append("}");
        return indentingStringBuilder.toString();
    }
}
