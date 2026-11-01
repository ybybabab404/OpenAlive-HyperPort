package com.miui.keyguard.editor.track;

import com.xiaomi.onetrack.api.am;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TrackConstants.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TrackValues {
    public static final Map ENTER_EDITOR_MAP;
    public static final TrackValues INSTANCE = new TrackValues();

    static {
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        mapCreateMapBuilder.put("homepage", "个性化设置_主页");
        mapCreateMapBuilder.put("my_match", "个性化设置_我的搭配");
        mapCreateMapBuilder.put("wallpaper", "个性化设置_壁纸");
        mapCreateMapBuilder.put(am.s, "个性化设置_负一卡");
        mapCreateMapBuilder.put("desktop", "桌面设置");
        mapCreateMapBuilder.put("fileexplorer", "文件管理");
        mapCreateMapBuilder.put("lock_negative_one", "十字楼_负一卡");
        mapCreateMapBuilder.put("cross_match", "十字楼_我的搭配");
        mapCreateMapBuilder.put("cross_recommend", "十字楼_推荐层");
        mapCreateMapBuilder.put("store", "主题商店");
        mapCreateMapBuilder.put("gallery", "相册");
        mapCreateMapBuilder.put("dynamic", "动态壁纸");
        ENTER_EDITOR_MAP = MapsKt.build(mapCreateMapBuilder);
    }

    public final Map getENTER_EDITOR_MAP() {
        return ENTER_EDITOR_MAP;
    }
}
