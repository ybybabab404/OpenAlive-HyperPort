package com.miui.keyguard.editor.data.db;

import android.content.Context;
import android.util.Log;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.miui.keyguard.editor.data.db.migrations.Migration1To2;
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
/* JADX INFO: compiled from: EditorDatabase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Database
@Metadata
public abstract class EditorDatabase extends RoomDatabase {
    public static final Companion Companion = new Companion(null);
    public static EditorDatabase INSTANCE;

    /* JADX INFO: compiled from: EditorDatabase.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final EditorDatabase getInstance(Context context) {
            EditorDatabase editorDatabase;
            Intrinsics.checkNotNullParameter(context, "context");
            EditorDatabase editorDatabase2 = EditorDatabase.INSTANCE;
            if (editorDatabase2 != null) {
                return editorDatabase2;
            }
            synchronized (this) {
                editorDatabase = EditorDatabase.INSTANCE;
                if (editorDatabase == null) {
                    RoomDatabase.Builder builderDatabaseBuilder = Room.databaseBuilder(context, EditorDatabase.class, "editor.db");
                    Migration1To2 migration1To2 = new Migration1To2();
                    MIGRATION migration = MIGRATION.INSTANCE;
                    editorDatabase = (EditorDatabase) builderDatabaseBuilder.addMigrations(migration1To2, migration.getMigration2_3(), migration.getMigration3_4(), migration.getMigration4_5(), migration.getMigration5_6(), migration.getMigration6_7(), migration.getMigration7_8()).fallbackToDestructiveMigrationOnDowngrade().addCallback(new RoomDatabase.Callback() { // from class: com.miui.keyguard.editor.data.db.EditorDatabase$Companion$getInstance$1$1
                        @Override // androidx.room.RoomDatabase.Callback
                        public void onDestructiveMigration(SupportSQLiteDatabase db) {
                            Intrinsics.checkNotNullParameter(db, "db");
                            Log.w("EditorDatabase", "Database downgrade detected ,database version is " + db.getVersion());
                        }
                    }).fallbackToDestructiveMigration().build();
                }
            }
            return editorDatabase;
        }
    }

    public abstract PresetTemplateDao getPresetTemplateDao();

    public abstract TemplateHistoryDao getTemplateHistoryDao();
}
