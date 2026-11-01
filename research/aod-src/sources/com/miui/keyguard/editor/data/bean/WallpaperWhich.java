package com.miui.keyguard.editor.data.bean;

import com.miui.miwallpaper.MiuiWallpaperManager;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

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
/* JADX INFO: compiled from: WallpaperWhich.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class WallpaperWhich {
    private final int which;

    /* JADX INFO: compiled from: WallpaperWhich.kt */
    @Metadata
    public static final class AllWhich extends WallpaperWhich {

        @NotNull
        public static final AllWhich INSTANCE = new AllWhich();

        private AllWhich() {
            super(MiuiWallpaperManager.MI_WALLPAPER_WHICH_ALL, null);
        }
    }

    /* JADX INFO: compiled from: WallpaperWhich.kt */
    @Metadata
    public static final class FlipAllWhich extends WallpaperWhich {

        @NotNull
        public static final FlipAllWhich INSTANCE = new FlipAllWhich();

        private FlipAllWhich() {
            super(3, null);
        }
    }

    /* JADX INFO: compiled from: WallpaperWhich.kt */
    @Metadata
    public static final class FoldHomeWhich extends WallpaperWhich {

        @NotNull
        public static final FoldHomeWhich INSTANCE = new FoldHomeWhich();

        private FoldHomeWhich() {
            super(5, null);
        }
    }

    /* JADX INFO: compiled from: WallpaperWhich.kt */
    @Metadata
    public static final class FoldLockWhich extends WallpaperWhich {

        @NotNull
        public static final FoldLockWhich INSTANCE = new FoldLockWhich();

        private FoldLockWhich() {
            super(10, null);
        }
    }

    /* JADX INFO: compiled from: WallpaperWhich.kt */
    @Metadata
    public static final class HomeWhich extends WallpaperWhich {

        @NotNull
        public static final HomeWhich INSTANCE = new HomeWhich();

        private HomeWhich() {
            super(1, null);
        }
    }

    /* JADX INFO: compiled from: WallpaperWhich.kt */
    @Metadata
    public static final class LockWhich extends WallpaperWhich {

        @NotNull
        public static final LockWhich INSTANCE = new LockWhich();

        private LockWhich() {
            super(2, null);
        }
    }

    /* JADX INFO: compiled from: WallpaperWhich.kt */
    @Metadata
    public static final class SmallHome extends WallpaperWhich {

        @NotNull
        public static final SmallHome INSTANCE = new SmallHome();

        private SmallHome() {
            super(4, null);
        }
    }

    /* JADX INFO: compiled from: WallpaperWhich.kt */
    @Metadata
    public static final class SmallLock extends WallpaperWhich {

        @NotNull
        public static final SmallLock INSTANCE = new SmallLock();

        private SmallLock() {
            super(8, null);
        }
    }

    private WallpaperWhich(int i) {
        this.which = i;
    }

    public /* synthetic */ WallpaperWhich(int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(i);
    }

    public final int getWhich() {
        return this.which;
    }
}
