package androidx.core.app;

import android.util.SparseIntArray;
import android.view.FrameMetrics;
import android.view.Window;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import miuix.animation.internal.FolmeCore;

/* JADX INFO: loaded from: classes.dex */
public class FrameMetricsAggregator {

    @RequiresApi
    public static class FrameMetricsApi24Impl extends FrameMetricsBaseImpl {
        public SparseIntArray[] mMetrics;
        public int mTrackingFlags;

        /* JADX INFO: renamed from: androidx.core.app.FrameMetricsAggregator$FrameMetricsApi24Impl$1, reason: invalid class name */
        public class AnonymousClass1 implements Window.OnFrameMetricsAvailableListener {
            public final /* synthetic */ FrameMetricsApi24Impl this$0;

            @Override // android.view.Window.OnFrameMetricsAvailableListener
            public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int i) {
                FrameMetricsApi24Impl frameMetricsApi24Impl = this.this$0;
                if ((frameMetricsApi24Impl.mTrackingFlags & 1) != 0) {
                    frameMetricsApi24Impl.addDurationItem(frameMetricsApi24Impl.mMetrics[0], frameMetrics.getMetric(8));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl2 = this.this$0;
                if ((frameMetricsApi24Impl2.mTrackingFlags & 2) != 0) {
                    frameMetricsApi24Impl2.addDurationItem(frameMetricsApi24Impl2.mMetrics[1], frameMetrics.getMetric(1));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl3 = this.this$0;
                if ((frameMetricsApi24Impl3.mTrackingFlags & 4) != 0) {
                    frameMetricsApi24Impl3.addDurationItem(frameMetricsApi24Impl3.mMetrics[2], frameMetrics.getMetric(3));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl4 = this.this$0;
                if ((frameMetricsApi24Impl4.mTrackingFlags & 8) != 0) {
                    frameMetricsApi24Impl4.addDurationItem(frameMetricsApi24Impl4.mMetrics[3], frameMetrics.getMetric(4));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl5 = this.this$0;
                if ((frameMetricsApi24Impl5.mTrackingFlags & 16) != 0) {
                    frameMetricsApi24Impl5.addDurationItem(frameMetricsApi24Impl5.mMetrics[4], frameMetrics.getMetric(5));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl6 = this.this$0;
                if ((frameMetricsApi24Impl6.mTrackingFlags & 64) != 0) {
                    frameMetricsApi24Impl6.addDurationItem(frameMetricsApi24Impl6.mMetrics[6], frameMetrics.getMetric(7));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl7 = this.this$0;
                if ((frameMetricsApi24Impl7.mTrackingFlags & 32) != 0) {
                    frameMetricsApi24Impl7.addDurationItem(frameMetricsApi24Impl7.mMetrics[5], frameMetrics.getMetric(6));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl8 = this.this$0;
                if ((frameMetricsApi24Impl8.mTrackingFlags & 128) != 0) {
                    frameMetricsApi24Impl8.addDurationItem(frameMetricsApi24Impl8.mMetrics[7], frameMetrics.getMetric(0));
                }
                FrameMetricsApi24Impl frameMetricsApi24Impl9 = this.this$0;
                if ((frameMetricsApi24Impl9.mTrackingFlags & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0) {
                    frameMetricsApi24Impl9.addDurationItem(frameMetricsApi24Impl9.mMetrics[8], frameMetrics.getMetric(2));
                }
            }
        }

        public void addDurationItem(SparseIntArray sparseIntArray, long j) {
            if (sparseIntArray != null) {
                int i = (int) ((500000 + j) / FolmeCore.NANOS_TO_MS);
                if (j >= 0) {
                    sparseIntArray.put(i, sparseIntArray.get(i) + 1);
                }
            }
        }
    }

    public static class FrameMetricsBaseImpl {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface MetricType {
    }
}
