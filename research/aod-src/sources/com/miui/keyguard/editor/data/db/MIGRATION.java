package com.miui.keyguard.editor.data.db;

import android.util.Log;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
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
/* JADX INFO: compiled from: EditorDatabase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MIGRATION {
    public static final MIGRATION INSTANCE = new MIGRATION();
    public static final Lazy migration2_3$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.db.MIGRATION$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return MIGRATION.migration2_3_delegate$lambda$0();
        }
    });
    public static final Lazy migration3_4$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.db.MIGRATION$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return MIGRATION.migration3_4_delegate$lambda$1();
        }
    });
    public static final Lazy migration4_5$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.db.MIGRATION$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return MIGRATION.migration4_5_delegate$lambda$2();
        }
    });
    public static final Lazy migration5_6$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.db.MIGRATION$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return MIGRATION.migration5_6_delegate$lambda$3();
        }
    });
    public static final Lazy migration6_7$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.db.MIGRATION$$ExternalSyntheticLambda4
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return MIGRATION.migration6_7_delegate$lambda$4();
        }
    });
    public static final Lazy migration7_8$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.db.MIGRATION$$ExternalSyntheticLambda5
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return MIGRATION.migration7_8_delegate$lambda$5();
        }
    });

    /* JADX WARN: Type inference failed for: r0v0, types: [com.miui.keyguard.editor.data.db.MIGRATION$migration2_3$2$1] */
    public static final MIGRATION$migration2_3$2$1 migration2_3_delegate$lambda$0() {
        return new Migration() { // from class: com.miui.keyguard.editor.data.db.MIGRATION$migration2_3$2$1
            @Override // androidx.room.migration.Migration
            public void migrate(SupportSQLiteDatabase db) {
                Intrinsics.checkNotNullParameter(db, "db");
                Log.i("editor", "migrate: 2 to 3");
                db.execSQL("ALTER TABLE template_history_config ADD COLUMN isCurrent INTEGER not null default 0");
                db.execSQL("ALTER TABLE template_history_config ADD COLUMN templateName TEXT");
                db.execSQL("CREATE TABLE IF NOT EXISTS `preset_templates` (`templateName` TEXT, `hideParam` INTEGER NOT NULL, `version` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
            }
        };
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.miui.keyguard.editor.data.db.MIGRATION$migration3_4$2$1] */
    public static final MIGRATION$migration3_4$2$1 migration3_4_delegate$lambda$1() {
        return new Migration() { // from class: com.miui.keyguard.editor.data.db.MIGRATION$migration3_4$2$1
            @Override // androidx.room.migration.Migration
            public void migrate(SupportSQLiteDatabase db) {
                Intrinsics.checkNotNullParameter(db, "db");
                Log.i("EditorDatabase", "migrate: 3 to 4");
                db.execSQL("ALTER TABLE template_history_config ADD COLUMN smallWallpaperScreenshotUri TEXT");
                db.execSQL("ALTER TABLE template_history_config ADD COLUMN largePortWallpaperScreenshotUri TEXT");
                db.execSQL("ALTER TABLE template_history_config ADD COLUMN largeLandWallpaperScreenshotUri TEXT");
            }
        };
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.miui.keyguard.editor.data.db.MIGRATION$migration4_5$2$1] */
    public static final MIGRATION$migration4_5$2$1 migration4_5_delegate$lambda$2() {
        return new Migration() { // from class: com.miui.keyguard.editor.data.db.MIGRATION$migration4_5$2$1
            @Override // androidx.room.migration.Migration
            public void migrate(SupportSQLiteDatabase db) {
                Intrinsics.checkNotNullParameter(db, "db");
                Log.i("EditorDatabase", "migrate: 4 to 5");
                db.execSQL("ALTER TABLE preset_templates ADD COLUMN templateId TEXT DEFAULT 'UNDEFINE'");
                db.execSQL("ALTER TABLE preset_templates ADD COLUMN systemVersion TEXT DEFAULT 'UNDEFINE'");
            }
        };
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.miui.keyguard.editor.data.db.MIGRATION$migration5_6$2$1] */
    public static final MIGRATION$migration5_6$2$1 migration5_6_delegate$lambda$3() {
        return new Migration() { // from class: com.miui.keyguard.editor.data.db.MIGRATION$migration5_6$2$1
            @Override // androidx.room.migration.Migration
            public void migrate(SupportSQLiteDatabase db) {
                Intrinsics.checkNotNullParameter(db, "db");
                Log.i("EditorDatabase", "migrate: 5 to 6");
                db.execSQL("ALTER TABLE template_history_config ADD COLUMN hideFlag INTEGER not null default 0");
            }
        };
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.miui.keyguard.editor.data.db.MIGRATION$migration6_7$2$1] */
    public static final MIGRATION$migration6_7$2$1 migration6_7_delegate$lambda$4() {
        return new Migration() { // from class: com.miui.keyguard.editor.data.db.MIGRATION$migration6_7$2$1
            @Override // androidx.room.migration.Migration
            public void migrate(SupportSQLiteDatabase db) {
                Intrinsics.checkNotNullParameter(db, "db");
                Log.i("EditorDatabase", "migrate: 6 to 7");
                db.execSQL("ALTER TABLE template_history_config ADD COLUMN magicType INTEGER not null default 0");
            }
        };
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.miui.keyguard.editor.data.db.MIGRATION$migration7_8$2$1] */
    public static final MIGRATION$migration7_8$2$1 migration7_8_delegate$lambda$5() {
        return new Migration() { // from class: com.miui.keyguard.editor.data.db.MIGRATION$migration7_8$2$1
            @Override // androidx.room.migration.Migration
            public void migrate(SupportSQLiteDatabase db) {
                Intrinsics.checkNotNullParameter(db, "db");
                Log.i("EditorDatabase", "migrate: 7 to 8");
                db.execSQL("ALTER TABLE template_history_config ADD COLUMN isDarkWallpaper INTEGER not null default 0");
                db.execSQL("ALTER TABLE template_history_config ADD COLUMN isAiGenerated INTEGER not null default 0");
            }
        };
    }

    public final Migration getMigration2_3() {
        return (Migration) migration2_3$delegate.getValue();
    }

    public final Migration getMigration3_4() {
        return (Migration) migration3_4$delegate.getValue();
    }

    public final Migration getMigration4_5() {
        return (Migration) migration4_5$delegate.getValue();
    }

    public final Migration getMigration5_6() {
        return (Migration) migration5_6$delegate.getValue();
    }

    public final Migration getMigration6_7() {
        return (Migration) migration6_7$delegate.getValue();
    }

    public final Migration getMigration7_8() {
        return (Migration) migration7_8$delegate.getValue();
    }
}
