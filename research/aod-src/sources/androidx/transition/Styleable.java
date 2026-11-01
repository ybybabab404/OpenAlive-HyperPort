package androidx.transition;

import android.annotation.SuppressLint;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"InlinedApi"})
class Styleable {
    public static final int[] TRANSITION_TARGET = {android.R.attr.targetClass, android.R.attr.targetId, android.R.attr.excludeId, android.R.attr.excludeClass, android.R.attr.targetName, android.R.attr.excludeName};
    public static final int[] TRANSITION_MANAGER = {android.R.attr.fromScene, android.R.attr.toScene, android.R.attr.transition};
    public static final int[] TRANSITION = {android.R.attr.interpolator, android.R.attr.duration, android.R.attr.startDelay, android.R.attr.matchOrder};
    public static final int[] CHANGE_BOUNDS = {android.R.attr.resizeClip};
    public static final int[] VISIBILITY_TRANSITION = {android.R.attr.transitionVisibilityMode};
    public static final int[] FADE = {android.R.attr.fadingMode};
    public static final int[] CHANGE_TRANSFORM = {android.R.attr.reparent, android.R.attr.reparentWithOverlay};
    public static final int[] SLIDE = {android.R.attr.slideEdge};
    public static final int[] TRANSITION_SET = {android.R.attr.transitionOrdering};
    public static final int[] ARC_MOTION = {android.R.attr.minimumHorizontalAngle, android.R.attr.minimumVerticalAngle, android.R.attr.maximumAngle};
    public static final int[] PATTERN_PATH_MOTION = {android.R.attr.patternPathData};

    public interface ArcMotion {
    }

    public interface ChangeBounds {
    }

    public interface ChangeTransform {
    }

    public interface Fade {
    }

    public interface PatternPathMotion {
    }

    public interface Slide {
    }

    public interface Transition {
    }

    public interface TransitionManager {
    }

    public interface TransitionSet {
    }

    public interface TransitionTarget {
    }

    public interface VisibilityTransition {
    }
}
