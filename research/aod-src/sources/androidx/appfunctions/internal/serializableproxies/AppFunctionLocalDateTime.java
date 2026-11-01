package androidx.appfunctions.internal.serializableproxies;

import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appfunctions.AppFunctionSerializableProxy;
import java.time.LocalDateTime;
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
/* JADX INFO: compiled from: AppFunctionLocalDateTime.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@RequiresApi
@AppFunctionSerializableProxy
@RestrictTo
public final class AppFunctionLocalDateTime {
    public static final Companion Companion = new Companion(null);
    public final int dayOfMonth;
    public final int hour;
    public final int minute;
    public final int month;
    public final int nanoOfSecond;
    public final int second;
    public final int year;

    /* JADX INFO: compiled from: AppFunctionLocalDateTime.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final AppFunctionLocalDateTime fromLocalDateTime(LocalDateTime localDateTime) {
            Intrinsics.checkNotNullParameter(localDateTime, "localDateTime");
            return new AppFunctionLocalDateTime(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond(), localDateTime.getNano());
        }
    }

    public AppFunctionLocalDateTime(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.year = i;
        this.month = i2;
        this.dayOfMonth = i3;
        this.hour = i4;
        this.minute = i5;
        this.second = i6;
        this.nanoOfSecond = i7;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppFunctionLocalDateTime)) {
            return false;
        }
        AppFunctionLocalDateTime appFunctionLocalDateTime = (AppFunctionLocalDateTime) obj;
        return this.year == appFunctionLocalDateTime.year && this.month == appFunctionLocalDateTime.month && this.dayOfMonth == appFunctionLocalDateTime.dayOfMonth && this.hour == appFunctionLocalDateTime.hour && this.minute == appFunctionLocalDateTime.minute && this.second == appFunctionLocalDateTime.second && this.nanoOfSecond == appFunctionLocalDateTime.nanoOfSecond;
    }

    public final int getDayOfMonth() {
        return this.dayOfMonth;
    }

    public final int getHour() {
        return this.hour;
    }

    public final int getMinute() {
        return this.minute;
    }

    public final int getMonth() {
        return this.month;
    }

    public final int getNanoOfSecond() {
        return this.nanoOfSecond;
    }

    public final int getSecond() {
        return this.second;
    }

    public final int getYear() {
        return this.year;
    }

    public int hashCode() {
        return (((((((((((Integer.hashCode(this.year) * 31) + Integer.hashCode(this.month)) * 31) + Integer.hashCode(this.dayOfMonth)) * 31) + Integer.hashCode(this.hour)) * 31) + Integer.hashCode(this.minute)) * 31) + Integer.hashCode(this.second)) * 31) + Integer.hashCode(this.nanoOfSecond);
    }

    public final LocalDateTime toLocalDateTime() {
        LocalDateTime localDateTimeOf = LocalDateTime.of(this.year, this.month, this.dayOfMonth, this.hour, this.minute, this.second, this.nanoOfSecond);
        Intrinsics.checkNotNullExpressionValue(localDateTimeOf, "of(...)");
        return localDateTimeOf;
    }

    public String toString() {
        return "AppFunctionLocalDateTime(year=" + this.year + ", month=" + this.month + ", dayOfMonth=" + this.dayOfMonth + ", hour=" + this.hour + ", minute=" + this.minute + ", second=" + this.second + ", nanoOfSecond=" + this.nanoOfSecond + ')';
    }
}
