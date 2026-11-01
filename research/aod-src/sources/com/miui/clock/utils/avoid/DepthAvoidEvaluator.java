package com.miui.clock.utils.avoid;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.SparseArray;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

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
/* JADX INFO: compiled from: DepthAvoidEvaluator.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class DepthAvoidEvaluator {
    public static final BufferPool sharedBufferPoolForOtherBiz;
    public static final Calculator sharedCalculatorForOtherBiz;
    public static final ThreadSafeCalculator sharedThreadSafeCalculatorForOtherBiz;
    public static final DepthAvoidEvaluator INSTANCE = new DepthAvoidEvaluator();
    public static final boolean DEBUG_LOG = DepthAvoidEvaluatorDebug.DEBUG_LOG;
    public static final Threshold IMAGE_THRESHOLD = new Threshold(0.2d);
    public static final Threshold VIDEO_THRESHOLD = new Threshold(0.5d);

    /* JADX INFO: compiled from: DepthAvoidEvaluator.kt */
    @Metadata
    public enum AvoidReason {
        OK,
        INVALID_RECT,
        INVALID_SCREEN,
        BITMAP_RECYCLED,
        SAMPLE_OOB,
        SAMPLE_EXCEPTION;

        public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    }

    /* JADX INFO: compiled from: DepthAvoidEvaluator.kt */
    @Metadata
    public static final class BufferPool {
        public final SparseArray cacheMap = new SparseArray();
    }

    /* JADX INFO: compiled from: DepthAvoidEvaluator.kt */
    @Metadata
    public static final class Calculator {
        public final BufferPool bufferPool;
        public final Sampler sampler;

        /* JADX INFO: compiled from: DepthAvoidEvaluator.kt */
        @Metadata
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[WideColorPolicy.values().length];
                try {
                    iArr[WideColorPolicy.AUTO.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[WideColorPolicy.FORCE_OFF.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[WideColorPolicy.FORCE_ON.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public Calculator(BufferPool bufferPool) {
            Intrinsics.checkNotNullParameter(bufferPool, "bufferPool");
            this.bufferPool = bufferPool;
            this.sampler = new Sampler(bufferPool);
        }
    }

    /* JADX INFO: compiled from: DepthAvoidEvaluator.kt */
    @Metadata
    public static final class DebugSampleStats {
        public final int avgAlpha;
        public final int maxAlpha;
        public final int minAlpha;
        public final int nonTransparent;
        public final int threshold;
        public final int total;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DebugSampleStats)) {
                return false;
            }
            DebugSampleStats debugSampleStats = (DebugSampleStats) obj;
            return this.total == debugSampleStats.total && this.nonTransparent == debugSampleStats.nonTransparent && this.threshold == debugSampleStats.threshold && this.minAlpha == debugSampleStats.minAlpha && this.maxAlpha == debugSampleStats.maxAlpha && this.avgAlpha == debugSampleStats.avgAlpha;
        }

        public int hashCode() {
            return (((((((((Integer.hashCode(this.total) * 31) + Integer.hashCode(this.nonTransparent)) * 31) + Integer.hashCode(this.threshold)) * 31) + Integer.hashCode(this.minAlpha)) * 31) + Integer.hashCode(this.maxAlpha)) * 31) + Integer.hashCode(this.avgAlpha);
        }

        public String toString() {
            return "DebugSampleStats(total=" + this.total + ", nonTransparent=" + this.nonTransparent + ", threshold=" + this.threshold + ", minAlpha=" + this.minAlpha + ", maxAlpha=" + this.maxAlpha + ", avgAlpha=" + this.avgAlpha + ')';
        }
    }

    /* JADX INFO: compiled from: DepthAvoidEvaluator.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Sampler {
        public final Paint depthVideoPaint;
        public final Paint imagePaint;
        public final BufferPool pool;

        public Sampler(BufferPool pool) {
            Intrinsics.checkNotNullParameter(pool, "pool");
            this.pool = pool;
            this.imagePaint = new Paint();
            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(new float[]{0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f})));
            this.depthVideoPaint = paint;
        }
    }

    /* JADX INFO: compiled from: DepthAvoidEvaluator.kt */
    @Metadata
    public static final class ThreadSafeCalculator {
        public final BufferPool bufferPool;
        public final ThreadSafeSampler sampler;

        /* JADX INFO: compiled from: DepthAvoidEvaluator.kt */
        @Metadata
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[WideColorPolicy.values().length];
                try {
                    iArr[WideColorPolicy.AUTO.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[WideColorPolicy.FORCE_OFF.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[WideColorPolicy.FORCE_ON.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public ThreadSafeCalculator(BufferPool bufferPool) {
            Intrinsics.checkNotNullParameter(bufferPool, "bufferPool");
            this.bufferPool = bufferPool;
            this.sampler = new ThreadSafeSampler(bufferPool);
        }
    }

    /* JADX INFO: compiled from: DepthAvoidEvaluator.kt */
    @Metadata
    @SourceDebugExtension
    public static final class ThreadSafeSampler {
        public final Paint depthVideoPaint;
        public final Paint imagePaint;
        public final BufferPool pool;

        public ThreadSafeSampler(BufferPool pool) {
            Intrinsics.checkNotNullParameter(pool, "pool");
            this.pool = pool;
            this.imagePaint = new Paint();
            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(new float[]{0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f})));
            this.depthVideoPaint = paint;
        }
    }

    /* JADX INFO: compiled from: DepthAvoidEvaluator.kt */
    @Metadata
    public static final class Threshold {
        public final double rate;

        public Threshold(double d) {
            this.rate = d;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Threshold) && Double.compare(this.rate, ((Threshold) obj).rate) == 0;
        }

        public int hashCode() {
            return Double.hashCode(this.rate);
        }

        public String toString() {
            return "Threshold(rate=" + this.rate + ')';
        }
    }

    /* JADX INFO: compiled from: DepthAvoidEvaluator.kt */
    @Metadata
    public enum WideColorPolicy {
        AUTO,
        FORCE_OFF,
        FORCE_ON;

        public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    }

    static {
        BufferPool bufferPool = new BufferPool();
        sharedBufferPoolForOtherBiz = bufferPool;
        sharedCalculatorForOtherBiz = new Calculator(bufferPool);
        sharedThreadSafeCalculatorForOtherBiz = new ThreadSafeCalculator(bufferPool);
    }
}
