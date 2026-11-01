package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public final class Score {

    public static class ScoredComparator implements Comparator<ScoredHCT> {
        @Override // java.util.Comparator
        public int compare(ScoredHCT scoredHCT, ScoredHCT scoredHCT2) {
            return Double.compare(scoredHCT2.score, scoredHCT.score);
        }
    }

    public static class ScoredHCT {
        public final double score;
    }
}
