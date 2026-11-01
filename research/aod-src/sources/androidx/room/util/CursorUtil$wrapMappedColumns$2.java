package androidx.room.util;

import android.database.CursorWrapper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: CursorUtil.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class CursorUtil$wrapMappedColumns$2 extends CursorWrapper {
    public final /* synthetic */ String[] $columnNames;
    public final /* synthetic */ int[] $mapping;

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getColumnIndex(String columnName) {
        Intrinsics.checkNotNullParameter(columnName, "columnName");
        String[] strArr = this.$columnNames;
        int[] iArr = this.$mapping;
        int length = strArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i2 + 1;
            if (StringsKt.equals(strArr[i], columnName, true)) {
                return iArr[i2];
            }
            i++;
            i2 = i3;
        }
        return super.getColumnIndex(columnName);
    }
}
