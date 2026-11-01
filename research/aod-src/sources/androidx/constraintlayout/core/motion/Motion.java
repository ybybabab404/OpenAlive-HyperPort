package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.utils.DifferentialInterpolator;
import androidx.constraintlayout.core.motion.utils.TypedValues;

/* JADX INFO: loaded from: classes.dex */
public class Motion implements TypedValues {
    public MotionPaths mEndMotionPath;
    public MotionPaths mStartMotionPath;

    /* JADX INFO: renamed from: androidx.constraintlayout.core.motion.Motion$1, reason: invalid class name */
    final class AnonymousClass1 implements DifferentialInterpolator {
    }

    public String toString() {
        return " start: x: " + this.mStartMotionPath.x + " y: " + this.mStartMotionPath.y + " end: x: " + this.mEndMotionPath.x + " y: " + this.mEndMotionPath.y;
    }
}
