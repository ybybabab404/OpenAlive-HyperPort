package com.miui.keyguard.editor.base;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
/* JADX INFO: compiled from: TemplateEnvironment.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class TemplateEnvironment {
    public final int flag;

    /* JADX INFO: compiled from: TemplateEnvironment.kt */
    @Metadata
    public static final class EditFragmentEnvironment extends TemplateEnvironment {
        public static final EditFragmentEnvironment INSTANCE = new EditFragmentEnvironment();

        public EditFragmentEnvironment() {
            super(2, null);
        }

        public boolean equals(Object obj) {
            return this == obj || (obj instanceof EditFragmentEnvironment);
        }

        public int hashCode() {
            return 1720456209;
        }

        public String toString() {
            return "EditFragmentEnvironment";
        }
    }

    /* JADX INFO: compiled from: TemplateEnvironment.kt */
    @Metadata
    public static final class MainFragmentEnvironment extends TemplateEnvironment {
        public static final MainFragmentEnvironment INSTANCE = new MainFragmentEnvironment();

        public MainFragmentEnvironment() {
            super(1, null);
        }

        public boolean equals(Object obj) {
            return this == obj || (obj instanceof MainFragmentEnvironment);
        }

        public int hashCode() {
            return -1229150206;
        }

        public String toString() {
            return "MainFragmentEnvironment";
        }
    }

    public TemplateEnvironment(int i) {
        this.flag = i;
    }

    public /* synthetic */ TemplateEnvironment(int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(i);
    }
}
