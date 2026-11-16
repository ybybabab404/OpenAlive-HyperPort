package org.aliveclean;

/**
 * Host-side assertions for the pure-logic scene classes.
 *
 * <p>These classes carry no Android dependency on purpose (they are only maths and state
 * machines), so they can be compiled with a plain JDK and verified without a device.
 * Runs under {@code tools/check_scene_logic.py}. Keep the assertions about <em>contracts</em>
 * (what callers rely on), not about incidental implementation details.</p>
 */
public final class SceneLogicTest {
    private static int checks;

    private static void check(boolean condition, String what) {
        if (!condition) throw new AssertionError(what);
        checks++;
    }

    public static void main(String[] args) {
        aodRegion();
        aodMotionWindow();
        sceneState();
        wallpaperTransitionTime();
        System.out.println("scene logic: " + checks + " assertions passed");
    }

    /** AOD region parsing/validation and the normalised centre it exposes to the renderer. */
    private static void aodRegion() {
        int w = 1200, h = 2608;
        check(AodRegion.create(0, w, h, 0, 0, w, h) != null, "full-screen region is valid");
        check(AodRegion.create(-1, w, h, 0, 0, w, h) == null, "negative display id rejected");
        check(AodRegion.create(0, 0, h, 0, 0, w, h) == null, "zero width rejected");
        check(AodRegion.create(0, w, h, 0, 0, w, h + 1) == null, "bottom past screen rejected");
        check(AodRegion.create(0, w, h, 10, 0, 10, h) == null, "empty horizontal span rejected");
        check(AodRegion.create(0, w, h, 0, 20, w, 10) == null, "inverted vertical span rejected");

        check(AodRegion.parse(0, w, h, null) == null, "null text rejected");
        check(AodRegion.parse(0, w, h, "1|2|3") == null, "three fields rejected");
        check(AodRegion.parse(0, w, h, "a|2|3|4") == null, "non-numeric field rejected");
        check(AodRegion.parse(0, w, h, "-1|0|10|10") == null, "invalid parsed region rejected");

        AodRegion centre = AodRegion.parse(0, w, h, "500|1104|700|1504");
        check(centre != null, "centred region parses");
        check(Math.abs(centre.sceneX()) < 1e-6f, "centred region has scene x = 0");
        check(Math.abs(centre.sceneY()) < 1e-6f, "centred region has scene y = 0");

        AodRegion right = AodRegion.parse(0, w, h, "1100|1500|1200|1700");
        check(right.sceneX() > 0f, "region on the right has positive scene x");
        check(right.sceneY() < 0f, "region above centre has negative scene y");

        check(right.fits(0, w, h), "same aspect ratio fits");
        check(!right.fits(1, w, h), "different display id does not fit");
        check(!right.fits(0, 0, h), "zero width does not fit");
        check(!right.fits(0, w / 2, h), "half-aspect panel does not fit");
    }

    /** The always-on screen speed ramp: starts at 1, decays to 0 over DURATION_MS. */
    private static void aodMotionWindow() {
        AodMotionWindow window = new AodMotionWindow();
        check(!window.running(0), "idle window is not running");
        check(window.speed(0) == 0f, "idle window has zero speed");

        long start = 1_000_000_000L; // arbitrary monotonic base
        window.enter(start);
        check(Math.abs(window.speed(start) - 1f) < 1e-6f, "speed starts at 1");
        long half = start + AodMotionWindow.DURATION_MS / 2L * 1_000_000L;
        check(Math.abs(window.speed(half) - .5f) < 1e-3f, "speed is 0.5 at half duration");
        long end = start + (long) AodMotionWindow.DURATION_MS * 1_000_000L;
        check(window.speed(end) == 0f, "speed is 0 at full duration");
        check(!window.running(end), "window stops after its duration");
        check(window.speed(start - 1_000_000L) >= 0f, "samples before the start stay non-negative");

        window.leave();
        check(!window.running(start), "leave() stops the window");
    }

    /** Scene bookkeeping: authoritative signals win, stale lock state falls back to home. */
    private static void sceneState() {
        SceneState state = new SceneState();
        check(state.mode() == 1, "defaults to the lock screen");
        check(!state.authoritative(), "starts non-authoritative");
        check(state.fallback(false, false) == 2, "unlocked fallback is home");
        check(state.fallback(true, false) == 0, "ambient fallback is the AOD");
        check(state.fallback(false, true) == 1, "locked fallback is the lock screen");

        // accept() returns "the mode changed", so acceptance is asserted through mode().
        SceneState signals = new SceneState();
        check(!signals.accept(-1, 10), "negative mode rejected");
        check(!signals.accept(3, 10), "mode above 2 rejected");
        check(!signals.accept(0, 10, -1), "negative phase rejected");
        check(!signals.accept(0, 10, 4), "phase above 3 rejected");

        // Phase 1 means "the display went ambient", so it may only carry the AOD mode.
        check(!signals.accept(1, 10, 1), "ambient phase requires the AOD mode");
        check(signals.accept(0, 10, 1), "ambient phase accepted for the AOD mode");
        check(signals.mode() == 0, "ambient phase switches to the AOD");
        check(!signals.accept(0, 20, 2), "leaving ambient may not restore the AOD mode");
        signals.accept(2, 20, 2);
        check(signals.mode() == 2, "leaving ambient lands on home");
        check(!signals.accept(1, 30, 3), "unlock phase requires the home mode");
        signals.accept(2, 30, 3);
        check(signals.mode() == 2, "unlock phase keeps home");

        // After the unlock phase only a fresh ambient signal or home may pass.
        check(!signals.accept(1, 40), "lock rejected right after the unlock phase");
        signals.accept(2, 40);
        check(!signals.accept(1, 50, 1), "lock rejected inside the ambient phase");

        // A plain (phase-less) signal is how SystemUI reports an ordinary mode change.
        SceneState plain = new SceneState();
        check(plain.accept(0, 100), "phase-less AOD signal accepted");
        check(plain.mode() == 0, "phase-less signal updates the mode");
        // Without a phase in the history nothing gates the mode: the value is taken as-is.
        plain.accept(1, 200);
        check(plain.mode() == 1, "phase-less lock accepted when no phase was ever reported");
        plain.accept(2, 300);
        check(plain.mode() == 2, "phase-less home accepted");
        check(plain.authoritative(), "an accepted signal makes the state authoritative");

        SceneState stale = new SceneState();
        check(!stale.accept(1, 100), "a lock signal on an already locked scene reports no change");
        check(stale.mode() == 1 && stale.authoritative(), "the signal still marks the state authoritative");
        check(!stale.stale(false, true), "locked scene is not stale");
        check(stale.stale(false, false), "unlocked lock scene is stale");
        check(stale.fallback(false, false) == 2, "stale scene falls back to home");
        check(!stale.authoritative(), "stale scene drops authority");

        stale.disconnect();
        check(!stale.authoritative(), "disconnect drops authority");
    }

    /** Transition clock used by the non-native renderers. */
    private static void wallpaperTransitionTime() {
        WallpaperTransitionTime time = new WallpaperTransitionTime();
        check(time.advance(0) == 0f, "first sample starts at zero");
        check(Math.abs(time.advance(16_000_000L) - 16f) < 1e-3f, "16 ms sample advances 16 ms");
        check(Math.abs(time.advance(32_000_000L) - 32f) < 1e-3f, "accumulates across samples");
        time.pause();
        check(Math.abs(time.advance(48_000_000L) - 32f) < 1e-3f, "a paused gap does not advance");
        time.restart();
        check(time.advance(96_000_000L) == 0f, "restart clears the clock");
    }
}
