package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miui.app.backup.FullBackupAgent;

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
/* JADX INFO: compiled from: TemplateBackupApi.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public interface TemplateBackupApi {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: TemplateBackupApi.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static volatile TemplateBackupApi INSTANCE;

        public final TemplateBackupApi getInstance(Context context) {
            TemplateBackupApi templateBackupApiImpl;
            Intrinsics.checkNotNullParameter(context, "context");
            TemplateBackupApi templateBackupApi = INSTANCE;
            if (templateBackupApi != null) {
                return templateBackupApi;
            }
            synchronized (this) {
                templateBackupApiImpl = INSTANCE;
                if (templateBackupApiImpl == null) {
                    Context applicationContext = context.getApplicationContext();
                    Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                    templateBackupApiImpl = new TemplateBackupApiImpl(applicationContext);
                    INSTANCE = templateBackupApiImpl;
                }
            }
            return templateBackupApiImpl;
        }
    }

    void backupAllTemplates(FullBackupAgent fullBackupAgent);

    void backupAllTemplatesCompleted();

    void deleteBackupZipFile(String str);

    SystemParams getBackupSettings();

    String getBackupZipFilePathByVersion(int i);

    String getOldBackupZipFilePath();

    void tryRestoreAllTemplates(String str);

    void unzipSettingBackupFile(ParcelFileDescriptor parcelFileDescriptor, String str);
}
