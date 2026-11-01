package com.miui.keyguard.editor.edit.clock;

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
/* JADX INFO: compiled from: ClassicSelectorBean.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClassicSelectorBean {
    public static final int CLASSIC_SELECTOR_BEAN_TYPE_NORMAL = 0;
    public final int beanType;
    public final ClassicDataType dateType;
    public int flag;
    public final int labelResId;
    public final int style;
    public static final Companion Companion = new Companion(null);
    public static final int CLASSIC_SELECTOR_BEAN_TYPE_LABEL = 1;
    public static final int CLASSIC_SELECTOR_BEAN_TYPE_ALL = 2;

    /* JADX INFO: compiled from: ClassicSelectorBean.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int getCLASSIC_SELECTOR_BEAN_TYPE_ALL() {
            return ClassicSelectorBean.CLASSIC_SELECTOR_BEAN_TYPE_ALL;
        }

        public final int getCLASSIC_SELECTOR_BEAN_TYPE_LABEL() {
            return ClassicSelectorBean.CLASSIC_SELECTOR_BEAN_TYPE_LABEL;
        }

        public final int getCLASSIC_SELECTOR_BEAN_TYPE_NORMAL() {
            return ClassicSelectorBean.CLASSIC_SELECTOR_BEAN_TYPE_NORMAL;
        }
    }

    public ClassicSelectorBean(int i, int i2, ClassicDataType dateType, int i3) {
        Intrinsics.checkNotNullParameter(dateType, "dateType");
        this.labelResId = i;
        this.style = i2;
        this.dateType = dateType;
        this.beanType = i3;
    }

    public /* synthetic */ ClassicSelectorBean(int i, int i2, ClassicDataType classicDataType, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, classicDataType, (i4 & 8) != 0 ? CLASSIC_SELECTOR_BEAN_TYPE_NORMAL : i3);
    }

    public final int getBeanType() {
        return this.beanType;
    }

    public final ClassicDataType getDateType() {
        return this.dateType;
    }

    public final int getLabelResId() {
        return this.labelResId;
    }

    public final int getStyle() {
        return this.style;
    }

    public final boolean supportGlobal() {
        return (this.flag & 1) == 0;
    }

    public final void updateFlags(boolean z, int i) {
        this.flag = z ? this.flag | i : this.flag & (~i);
    }
}
