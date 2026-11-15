package android.os;

import java.util.ArrayList;
import java.util.List;

/** Deterministic main-looper queue.  Time only moves through advance(). */
public final class Handler {
    private static final class Task {
        final Runnable action;
        final long due;

        Task(Runnable action, long due) {
            this.action = action;
            this.due = due;
        }
    }

    private static final List<Task> TASKS = new ArrayList<>();
    private static long now;

    public Handler(Looper looper) {
    }

    public boolean post(Runnable action) { return postDelayed(action, 0); }

    public boolean postDelayed(Runnable action, long delay) {
        TASKS.add(new Task(action, now + delay));
        return true;
    }

    public void removeCallbacks(Runnable action) {
        TASKS.removeIf(task -> task.action == action);
    }

    public static int queued() { return TASKS.size(); }

    public static void runReady() {
        for (int loops = 0; loops < 1000; loops++) {
            Task next = null;
            for (Task task : TASKS) {
                if (task.due <= now && (next == null || task.due < next.due)) next = task;
            }
            if (next == null) return;
            TASKS.remove(next);
            next.action.run();
        }
        throw new AssertionError("handler queue did not settle");
    }

    public static void advance(long millis) {
        now += millis;
        runReady();
    }

    public static void reset() {
        TASKS.clear();
        now = 0;
    }
}
