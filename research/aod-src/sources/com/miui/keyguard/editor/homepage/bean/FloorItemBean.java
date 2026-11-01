package com.miui.keyguard.editor.homepage.bean;

import java.util.List;
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
/* JADX INFO: compiled from: FloorItemBean.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FloorItemBean {
    public final Integer desc;
    public final int floor;
    public Boolean isLoop;
    public int itemType;
    public final List items;
    public int selectPosition;
    public final Integer title;

    public FloorItemBean(int i, Integer num, Integer num2, List items) {
        Intrinsics.checkNotNullParameter(items, "items");
        this.floor = i;
        this.title = num;
        this.desc = num2;
        this.items = items;
        this.selectPosition = -1;
        this.isLoop = Boolean.TRUE;
        this.itemType = 2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FloorItemBean)) {
            return false;
        }
        FloorItemBean floorItemBean = (FloorItemBean) obj;
        return this.floor == floorItemBean.floor && Intrinsics.areEqual(this.title, floorItemBean.title) && Intrinsics.areEqual(this.desc, floorItemBean.desc) && Intrinsics.areEqual(this.items, floorItemBean.items);
    }

    public final int getFloor() {
        return this.floor;
    }

    public final int getItemType() {
        return this.itemType;
    }

    public final List getItems() {
        return this.items;
    }

    public final int getSelectPosition() {
        return this.selectPosition;
    }

    public final Integer getTitle() {
        return this.title;
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.floor) * 31;
        Integer num = this.title;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.desc;
        return ((iHashCode2 + (num2 != null ? num2.hashCode() : 0)) * 31) + this.items.hashCode();
    }

    public final void setItemType(int i) {
        this.itemType = i;
    }

    public final void setLoop(Boolean bool) {
        this.isLoop = bool;
    }

    public final void setSelectPosition(int i) {
        this.selectPosition = i;
    }

    public String toString() {
        return "FloorItemBean(floor=" + this.floor + ", title=" + this.title + ", desc=" + this.desc + ", items=" + this.items + ")";
    }
}
