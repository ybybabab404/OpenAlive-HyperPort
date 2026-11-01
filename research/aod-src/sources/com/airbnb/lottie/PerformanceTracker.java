package com.airbnb.lottie;

import androidx.collection.ArraySet;
import androidx.core.util.Pair;
import com.airbnb.lottie.utils.MeanCalculator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class PerformanceTracker {
    public boolean enabled = false;
    public final Set frameListeners = new ArraySet();
    public final Map layerRenderTimes = new HashMap();
    public final Comparator floatComparator = new Comparator<Pair<String, Float>>() { // from class: com.airbnb.lottie.PerformanceTracker.1
        /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
        public int compare2(Pair pair, Pair pair2) {
            float fFloatValue = ((Float) pair.second).floatValue();
            float fFloatValue2 = ((Float) pair2.second).floatValue();
            if (fFloatValue2 > fFloatValue) {
                return 1;
            }
            return fFloatValue > fFloatValue2 ? -1 : 0;
        }

        @Override // java.util.Comparator
        public /* bridge */ /* synthetic */ int compare(Pair<String, Float> pair, Pair<String, Float> pair2) {
            return compare2((Pair) pair, (Pair) pair2);
        }
    };

    public interface FrameListener {
        void onFrameRendered(float f);
    }

    public void recordRenderTime(String str, float f) {
        if (this.enabled) {
            MeanCalculator meanCalculator = (MeanCalculator) this.layerRenderTimes.get(str);
            if (meanCalculator == null) {
                meanCalculator = new MeanCalculator();
                this.layerRenderTimes.put(str, meanCalculator);
            }
            meanCalculator.add(f);
            if (str.equals("__container")) {
                Iterator it = this.frameListeners.iterator();
                while (it.hasNext()) {
                    ((FrameListener) it.next()).onFrameRendered(f);
                }
            }
        }
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }
}
