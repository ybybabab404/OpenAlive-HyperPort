package androidx.window.embedding;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import androidx.window.core.ActivityComponentInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ActivityFilter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ActivityFilter {
    public final ActivityComponentInfo activityComponentInfo;
    public final String intentAction;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ActivityFilter(ComponentName componentName, String str) {
        this(new ActivityComponentInfo(componentName), str);
        Intrinsics.checkNotNullParameter(componentName, "componentName");
    }

    public ActivityFilter(ActivityComponentInfo activityComponentInfo, String str) {
        Intrinsics.checkNotNullParameter(activityComponentInfo, "activityComponentInfo");
        this.activityComponentInfo = activityComponentInfo;
        this.intentAction = str;
        MatcherUtils.INSTANCE.validateComponentName$window_release(activityComponentInfo.getPackageName(), activityComponentInfo.getClassName());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActivityFilter)) {
            return false;
        }
        ActivityFilter activityFilter = (ActivityFilter) obj;
        return Intrinsics.areEqual(this.activityComponentInfo, activityFilter.activityComponentInfo) && Intrinsics.areEqual(this.intentAction, activityFilter.intentAction);
    }

    public int hashCode() {
        int iHashCode = this.activityComponentInfo.hashCode() * 31;
        String str = this.intentAction;
        return iHashCode + (str != null ? str.hashCode() : 0);
    }

    public final boolean matchesActivity(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (!MatcherUtils.INSTANCE.isActivityMatching$window_release(activity, this.activityComponentInfo)) {
            return false;
        }
        String str = this.intentAction;
        if (str == null) {
            return true;
        }
        Intent intent = activity.getIntent();
        return Intrinsics.areEqual(str, intent != null ? intent.getAction() : null);
    }

    public final boolean matchesIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        if (!MatcherUtils.INSTANCE.isIntentMatching$window_release(intent, this.activityComponentInfo)) {
            return false;
        }
        String str = this.intentAction;
        return str == null || Intrinsics.areEqual(str, intent.getAction());
    }

    public String toString() {
        return "ActivityFilter(componentName=" + this.activityComponentInfo + ", intentAction=" + this.intentAction + ')';
    }
}
