package miuix.view.animation;

/* JADX INFO: loaded from: classes3.dex */
public class AnimationUtils extends android.view.animation.AnimationUtils {
    public static ThreadLocal sAnimationNanoState = new ThreadLocal<AnimationNanoState>() { // from class: miuix.view.animation.AnimationUtils.1
        @Override // java.lang.ThreadLocal
        public AnimationNanoState initialValue() {
            return new AnimationNanoState();
        }
    };

    public static class AnimationNanoState {
        public long lastReportedTimeNanos;

        public AnimationNanoState() {
        }
    }

    public static long currentAnimationTimeNanos() {
        AnimationNanoState animationNanoState = (AnimationNanoState) sAnimationNanoState.get();
        long jNanoTime = System.nanoTime();
        animationNanoState.lastReportedTimeNanos = jNanoTime;
        return jNanoTime;
    }
}
